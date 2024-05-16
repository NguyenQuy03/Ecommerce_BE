package com.ecommerce.springbootecommerce.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.api.buyer.OrderAPI;
import com.ecommerce.springbootecommerce.api.common.authenticate.payload.request.LogInRequest;
import com.ecommerce.springbootecommerce.api.common.authenticate.payload.request.RegisterRequest;
import com.ecommerce.springbootecommerce.api.common.authenticate.payload.response.AuthResponse;
import com.ecommerce.springbootecommerce.config.AuthenticationConfig;
import com.ecommerce.springbootecommerce.constant.TokenConstant;
import com.ecommerce.springbootecommerce.constant.RedisConstant;
import com.ecommerce.springbootecommerce.constant.StatusConstant;
import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.CustomUserDetails;
import com.ecommerce.springbootecommerce.entity.AccountEntity;
import com.ecommerce.springbootecommerce.entity.AccountRoleEntity;
import com.ecommerce.springbootecommerce.entity.RoleEntity;
import com.ecommerce.springbootecommerce.exception.CustomException;
import com.ecommerce.springbootecommerce.repository.AccountRepository;
import com.ecommerce.springbootecommerce.repository.AccountRoleRepository;
import com.ecommerce.springbootecommerce.repository.RoleRepository;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.util.JwtUtil;
import com.ecommerce.springbootecommerce.util.RedisUtil;
import com.ecommerce.springbootecommerce.util.converter.account_role.AccountConverter;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AuthenticationConfig authenticationConfig;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AccountConverter accountConverter;

    private final AccountRepository accountRepo;

    private final RoleRepository roleRepo;

    private final AccountRoleRepository accountRoleRepo;

    private final JwtUtil jwtUtil;

    private final RedisUtil redisUtil;

    private final UserDetailsService userDetailService;

    public AccountService(AccountRepository accountRepo, RoleRepository roleRepo,
            AccountRoleRepository accountRoleRepo, JwtUtil jwtUtil, RedisUtil redisUtil,
            UserDetailsService userDetailService) {
        this.accountRepo = accountRepo;
        this.roleRepo = roleRepo;
        this.accountRoleRepo = accountRoleRepo;
        this.jwtUtil = jwtUtil;
        this.redisUtil = redisUtil;
        this.userDetailService = userDetailService;
    }

    @Override
    public AccountDTO findByUsername(String username) {
        Optional<AccountEntity> account = accountRepo.findByUsername(username);
        if (account.isPresent())
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
        var account = AccountEntity.builder()
                .username(request.getUsername())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .isActive(request.isActive() || true)
                .password(authenticationConfig.passwordEncoder().encode(request.getPassword()))
                .build();

        try {
            AccountEntity newAcc = accountRepo.save(account);

            RoleEntity roleEntity;
            if (request.getRole() != null) {
                roleEntity = roleRepo.findOneByCode(request.getRole()).get();
            } else {
                roleEntity = roleRepo.findOneByCode(SystemConstant.ROLE_BUYER).get();
            }

            AccountRoleEntity accountRoleEntity = new AccountRoleEntity();
            accountRoleEntity.setAccount(newAcc);
            accountRoleEntity.setRole(roleEntity);
            accountRoleRepo.save(accountRoleEntity);
            redisUtil.setHashField(RedisConstant.REDIS_USER_INFO + request.getUsername(),
                    RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER, String.valueOf(0));
        } catch (Exception e) {
            accountRepo.delete(account);
            throw new RuntimeException("Error register");
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
        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(),
                authorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    @Override
    public void update(AccountDTO accountDTO) {
        accountDTO.setActive(StatusConstant.BOOLEAN_ACTIVE_STATUS);
        AccountEntity preAccountEntity = accountRepo.findById(accountDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Account is not exist"));

        modelMapper.map(accountDTO, preAccountEntity);
        preAccountEntity = modelMapper.map(preAccountEntity, AccountEntity.class);

        accountRepo.save(preAccountEntity);
    }

    @Override
    public AuthResponse authenticate(LogInRequest request, HttpServletRequest httpServletRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()));
        } catch (Exception e) {
            return null;
        }

        CustomUserDetails userDetails = (CustomUserDetails) userDetailService.loadUserByUsername(request.getUsername());
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(),
                userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);
        revokeAllUserTokens(userDetails);
        redisUtil.setKey(RedisConstant.REDIS_JWT_BRANCH + request.getUsername(), refreshToken,
                TokenConstant.JWT_REFRESH_TOKEN_EXPIRATION);

        AuthResponse authResponse = AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();

        return authResponse;
    }

    @Override
    public AccountDTO findById(Long id) {
        Optional<AccountEntity> accountEntity = accountRepo.findById(id);
        return accountEntity.map(entity -> modelMapper.map(entity, AccountDTO.class)).orElse(null);
    }

    @Override
    public void changeAccountStatus(long[] ids, String action) {
        for (long id : ids) {
            Optional<AccountEntity> entity = accountRepo.findById(id);
            if (entity.isPresent()) {
                entity.get().setActive("active".equals(action));
                accountRepo.save(entity.get());
            } else {
                throw new CustomException("Account does not Exist", HttpStatus.BAD_REQUEST);
            }
        }
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