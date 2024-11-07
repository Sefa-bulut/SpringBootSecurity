package com.sefa.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sefa.security.webtoken.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtService jwtService;
	@Autowired
	private MyUserDetailService myUserDetailService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Authorization header'ını alıyoruz (tokenimiz bu başlık altında geliyor)
        String authHeader = request.getHeader("Authorization");
		
        // Token varsa ve "Bearer " ile başlıyorsa, işlem yapıyoruz
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
        	//"Bearer " kısmını kesiyoruz
        	String jwt = authHeader.substring(7); 
        	//Token'dan kullanıcı adını alıyoruz
        	String username = jwtService.extractUsername(jwt); 
        	//kullanıcı adı boş değilse ve hali hazırda zaten log in olmadıysa doğrulama yapıyoruz
        	if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        		UserDetails userDetails = myUserDetailService.loadUserByUsername(username);
        		//böyle bir kullanıcı varsa (userdetail service db den bakıyor) ve token validse
        		if(userDetails != null && jwtService.isTokenExpired(jwt)) {
        			// Kullanıcı adı ve şifreyi doğruladıktan sonra bir Authentication token'ı oluşturuyoruz
        			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        					username, userDetails.getPassword(), userDetails.getAuthorities());
        			//İstek üzerinden web tabanlı detayları alıyoruz(ip adres gibi)
        			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        			// SecurityContext'e ekleyerek kullanıcının kimliğini doğrulamış oluyoruz
        		    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        		}
        	}
        	//diğer filtrelere devam et
        	filterChain.doFilter(request, response);
        	
        } else {
        	//diğer filtrelere devam et
        	filterChain.doFilter(request, response);
        	return;
        }
	}

}
