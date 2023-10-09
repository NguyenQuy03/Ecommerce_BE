package com.ecommerce.springbootecommerce.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.api.authenticate.payload.request.LogInRequest;
import com.ecommerce.springbootecommerce.api.authenticate.payload.request.RegisterRequest;
import com.ecommerce.springbootecommerce.config.AuthenticationConfig;
import com.ecommerce.springbootecommerce.constant.JWTConstant;
import com.ecommerce.springbootecommerce.constant.RedisConstant;
import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.CustomUserDetails;
import com.ecommerce.springbootecommerce.entity.AccountEntity;
import com.ecommerce.springbootecommerce.entity.AccountRoleEntity;
import com.ecommerce.springbootecommerce.entity.CartEntity;
import com.ecommerce.springbootecommerce.entity.RoleEntity;
import com.ecommerce.springbootecommerce.repository.AccountRepository;
import com.ecommerce.springbootecommerce.repository.AccountRoleRepository;
import com.ecommerce.springbootecommerce.repository.CartRepository;
import com.ecommerce.springbootecommerce.repository.RoleRepository;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.util.JwtUtil;
import com.ecommerce.springbootecommerce.util.RedisUtil;
import com.ecommerce.springbootecommerce.util.converter.AccountConverter;

@Service
public class AccountService implements IAccountService{

    @Autowired
    private AuthenticationConfig authenticationConfig;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private AccountRoleRepository accountRoleRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AccountConverter accountConverter;

    @Override
    public AccountDTO findByUsername(String username) {
        Optional<AccountEntity> account = accountRepo.findByUsername(username);
        if(account.isPresent())
            return modelMapper.map(account, AccountDTO.class);
        return null;
    }

    @Override
    public boolean isAccountExistByEmail(String email) {      
        return accountRepo.findOneByEmail(email).isPresent();
    }

    @Override
    public boolean isAccountExistByUsername(String username) {
        return accountRepo.findByUsername(username).isPresent();
    }
    
    @Override
    public void register(RegisterRequest request) {
        CartEntity cart = cartRepo.save(new CartEntity());
        var account = AccountEntity.builder()
            .username(request.getUsername())
            .fullName(request.getFullName())
            .email(request.getEmail())
            .status(SystemConstant.ACTIVE_STATUS)
            .password(authenticationConfig.passwordEncoder().encode(request.getPassword()))
            .build();

        try {
            AccountEntity newAcc = accountRepo.save(account);

        /* Save Account_Role */
            RoleEntity roleEntity = roleRepo.findOneByCode(SystemConstant.ROLE_BUYER).get();
            AccountRoleEntity accountRoleEntity = new AccountRoleEntity();
            accountRoleEntity.setAccount(newAcc);
            accountRoleEntity.setRole(roleEntity);
            accountRoleRepo.save(accountRoleEntity);

        /* Add new cart */
            cart.setAccount(newAcc);
            cartRepo.save(cart);
            redisUtil.setHashField(RedisConstant.REDIS_USER_INFO + request.getUsername(), RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER, String.valueOf(0));
        } catch (Exception e) {
            cartRepo.delete(cart);
            accountRepo.delete(account);
        }
    }
    @Override
    public void registerSeller(AccountDTO accountDTO) {

        /* Save Account_Role */
        RoleEntity roleEntity = roleRepo.findOneByCode(SystemConstant.ROLE_SELLER).get();
        AccountRoleEntity accountRoleEntity = new AccountRoleEntity();
        accountRoleEntity.setAccount(accountConverter.toEntity(accountDTO));
        accountRoleEntity.setRole(roleEntity);
        accountRoleRepo.save(accountRoleEntity);

        /* Add new role for session */
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = new HashSet<>();
        for (GrantedAuthority authority : auth.getAuthorities()) {
            roles.add(authority.getAuthority());
        }
        roles.add(SystemConstant.ROLE_SELLER);
        Set<GrantedAuthority> authorities = getAuthorities(roles);
        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(),authorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    @Override
    public void update(AccountDTO accountDTO) {
        accountDTO.setStatus(SystemConstant.ACTIVE_STATUS);
        AccountEntity preAccountEntity = accountRepo.findById(accountDTO.getId())
            .orElseThrow(() -> new IllegalArgumentException("Account is not exist"));

        modelMapper.map(accountDTO, preAccountEntity);
        preAccountEntity = modelMapper.map(preAccountEntity, AccountEntity.class);

        accountRepo.save(preAccountEntity);
    }

    @Override
    public String authenticate(LogInRequest request, HttpServletRequest httpServletRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            return null;
        }

        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUsername());
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(),
                userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);
        revokeAllUserTokens(userDetails);
        redisUtil.setKey(RedisConstant.REDIS_JWT_BRANCH + request.getUsername(), refreshToken, JWTConstant.JWT_REFRESH_TOKEN_EXPIRATION);

        return accessToken;
    }

    @Override
    public AccountDTO findById(Long id) {
        Optional<AccountEntity> accountEntity = accountRepo.findById(id);
        return accountEntity.map(entity -> modelMapper.map(entity, AccountDTO.class)).orElse(null);
    }

// REUSE
    private void revokeAllUserTokens(UserDetails account) {
        redisUtil.removeKey("Jwt:" + account.getUsername());
    }

    private Set<GrantedAuthority> getAuthorities(Set<String> roles) {
        Set<GrantedAuthority> auths = new HashSet<>();
        if (!roles.isEmpty()) {
            for (String role : roles) {
                auths.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
        }
        return auths;
    }
}
