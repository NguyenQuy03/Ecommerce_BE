package com.ecommerce.springbootecommerce.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Component
public class CookieUtil {
    public Cookie setCookie(String key, String value, String route) throws UnsupportedEncodingException {
        Cookie cookie = new Cookie(key, URLEncoder.encode( value, "UTF-8" ));
        cookie.setMaxAge(10);
        cookie.setHttpOnly(true);
        cookie.setPath(route);
        return cookie;
    }

}
