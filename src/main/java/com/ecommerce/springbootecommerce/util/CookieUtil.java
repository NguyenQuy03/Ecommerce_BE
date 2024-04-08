package com.ecommerce.springbootecommerce.util;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
    public Cookie initCookie(String key, String value, int maxAge) {
        Cookie cookie = new Cookie(key, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        return cookie;
    }

    public String getCookie(Cookie[] cookies, String key) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public void removeCookie(String key, HttpServletResponse response) throws IOException {
        Cookie cookie = initCookie(key, null, 0);
        response.addCookie(cookie);
        response.flushBuffer();
    }

    public void removeAll(Cookie[] cookies, HttpServletResponse response) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                Cookie newCookie = initCookie(cookie.getName(), null, 0);
                response.addCookie(newCookie);
            }
        }
    }
}
