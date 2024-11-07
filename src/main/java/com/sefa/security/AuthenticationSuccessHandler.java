package com.sefa.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
    	
    	boolean isAdmin = authentication.getAuthorities().stream()
    			.anyMatch(grantedAuthorities -> grantedAuthorities.getAuthority().equals("ROLE_ADMIN"));
    	if(isAdmin) {
    		setDefaultTargetUrl("/admin/home");
    	}else {
    		setDefaultTargetUrl("/user/home");
    	}
    	

        // Başarılı giriş sonrası varsayılan davranışı sürdür
        super.onAuthenticationSuccess(request, response, authentication);
    }
	
}
