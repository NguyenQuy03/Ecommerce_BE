package com.ecommerce.springbootecommerce.service.impl;

import com.ecommerce.springbootecommerce.api.authenticate.payload.request.LogInRequest;
import com.ecommerce.springbootecommerce.api.authenticate.payload.request.RegisterRequest;
import com.ecommerce.springbootecommerce.config.AuthenticationConfig;
import com.ecommerce.springbootecommerce.constant.JWTConstant;
import com.ecommerce.springbootecommerce.constant.RedisConstant;
import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.entity.AccountEntity;
import com.ecommerce.springbootecommerce.entity.CartEntity;
import com.ecommerce.springbootecommerce.entity.RoleEntity;
import com.ecommerce.springbootecommerce.repository.AccountRepository;
import com.ecommerce.springbootecommerce.repository.CartRepository;
import com.ecommerce.springbootecommerce.repository.OrderRepository;
import com.ecommerce.springbootecommerce.repository.RoleRepository;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.util.JwtUtil;
import com.ecommerce.springbootecommerce.util.RedisUtil;
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

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class AccountService implements IAccountService{
    
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationConfig authenticationConfig;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public AccountDTO findByUsername(String username) {
        Optional<AccountEntity> account = accountRepository.findByUsername(username);
        if(account.isPresent())
            return modelMapper.map(account, AccountDTO.class);
        return null;
    }

    @Override
    public boolean isAccountExistByEmail(String email) {      
        return accountRepository.findOneByEmail(email).isPresent();
    }

    @Override
    public boolean isAccountExistByUsername(String username) {
        return accountRepository.findByUsername(username).isPresent();
    }
    
    @Override
    public void register(RegisterRequest request) {
        AccountEntity account = AccountEntity.builder()
                .username(request.getUsername())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .status(SystemConstant.ACTIVE_STATUS)
                .roleCodes(Collections.singleton(SystemConstant.ROLE_BUYER))
                .password(authenticationConfig.passwordEncoder().encode(request.getPassword()))
                .build();
        AccountEntity newAcc = accountRepository.save(account);

    /* Add Account to role */
        RoleEntity roleEntity = roleRepository.findOneByCode(SystemConstant.ROLE_BUYER).get();
        List<AccountEntity> accounts = roleEntity.getAccounts();
        accounts.add(newAcc);
        roleEntity.setAccounts(accounts);
        roleRepository.save(roleEntity);

    /* Add new cart */
        CartEntity cartEntity = new CartEntity();
        cartEntity.setAccount(newAcc);
        cartEntity.setStatus(SystemConstant.STRING_ACTIVE_STATUS);
        cartRepository.save(cartEntity);

        redisUtil.setHashField(RedisConstant.REDIS_USER_INFO + request.getUsername(), RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER, String.valueOf(0));
    }

    @Override
    public void registerSeller(AccountDTO accountDTO) {
        AccountEntity accountEntity = modelMapper.map(accountDTO, AccountEntity.class);
        Set<String> roleCodes = accountEntity.getRoleCodes();
        roleCodes.add(SystemConstant.ROLE_SELLER);
        accountEntity.setRoleCodes(roleCodes);

        RoleEntity roleEntity = roleRepository.findOneByCode(SystemConstant.ROLE_SELLER).get();
        List<AccountEntity> accounts = roleEntity.getAccounts();
        accounts.add(modelMapper.map(accountDTO, AccountEntity.class));

        roleRepository.save(roleEntity);
        accountRepository.save(accountEntity);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Set<String> newRoles = accountEntity.getRoleCodes();
        newRoles.add(SystemConstant.ROLE_SELLER);
        Set<GrantedAuthority> authorities = getAuthorities(newRoles);
        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(),authorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    @Override
    public void update(AccountDTO accountDTO) {
        AccountEntity accountEntity; accountDTO.setStatus(SystemConstant.ACTIVE_STATUS);
        AccountEntity preAccountEntity = accountRepository.findById(accountDTO.getId()).get();

        modelMapper.map(accountDTO, preAccountEntity);
        accountEntity = modelMapper.map(preAccountEntity, AccountEntity.class);

        accountRepository.save(accountEntity);
    }

    @Override
    public String authenticate(LogInRequest request, HttpServletRequest httpServletRequest) {
        String username = request.getUsername();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            return null;
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(),
                userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);
        revokeAllUserTokens(userDetails);
        redisUtil.setKey(RedisConstant.REDIS_JWT_BRANCH + username, refreshToken, JWTConstant.JWT_REFRESH_TOKEN_EXPIRATION);

        return accessToken;
    }

    @Override
    public AccountDTO findOneById(String id) {
        Optional<AccountEntity> account = accountRepository.findOneById(id);
        if(account.isPresent())
            return modelMapper.map(account.get(), AccountDTO.class);
        return null;
    }

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
