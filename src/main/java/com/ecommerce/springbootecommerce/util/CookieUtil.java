package com.ecommerce.springbootecommerce.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class CookieUtil {
    public Cookie setCookie(String key, String value, int maxAge) {
        Cookie cookie = new Cookie(key, URLEncoder.encode(value, StandardCharsets.UTF_8));
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        return cookie;
    }

    public String getCookie(Cookie[] cookies, String key) {
        String res = "";
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals(key)){
                    res = cookie.getValue();
                    StringBuilder string = new StringBuilder(res);
                    for (int i = 0; i < string.length(); i++) {
                        if(string.charAt(i) == '+') {
                            string.setCharAt(i, ' ');
                        }
                    }
                    return string.toString();
                }
            }
        }
        return res;
    }
}
