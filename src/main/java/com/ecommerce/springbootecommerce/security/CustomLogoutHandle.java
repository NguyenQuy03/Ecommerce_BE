package com.ecommerce.springbootecommerce.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutHandle implements LogoutHandler{

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response,
					   Authentication authentication) {        
		try {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
			response.sendRedirect("/home");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
