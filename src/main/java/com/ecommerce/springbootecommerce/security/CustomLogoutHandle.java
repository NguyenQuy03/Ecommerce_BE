package com.ecommerce.springbootecommerce.security;

import java.io.IOException;

import com.ecommerce.springbootecommerce.constant.RedisConstant;
import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.util.CookieUtil;
import com.ecommerce.springbootecommerce.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CustomLogoutHandle implements LogoutHandler{
	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private CookieUtil cookieUtil;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response,
					   Authentication authentication) {
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			redisUtil.removeKey(RedisConstant.REDIS_JWT_BRANCH + username);
			Cookie Jwt = cookieUtil.initCookie("Jwt", "", 0);
			Cookie JSESSIONID = cookieUtil.initCookie("JSESSIONID", "", 0);
			response.addCookie(Jwt);
			response.addCookie(JSESSIONID);

			new SecurityContextLogoutHandler().logout(request, response, authentication);
			response.sendRedirect("/home");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
