package com.sefa.security.webtoken;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	//"anahtar123" Base64 ile kodlanmış hali
    private String SECRET_KEY = "ZTYzY2NhZDk0ODlmZTgyZDZkOGY0ZjFkNmFkYzc5NzVhZWRmMTY4MjViZTEzMjYyYmFkMTgzMzc0ZGFiYzM=";
	private static final long EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(45); // 45dk 
	
	//Her bir user için özel token üreten fonksiyonumuz 
	//(metot'dumuzun return type'ı String çünkü jwt tokenın veri tipi de String)
	public String generateToken(UserDetails userDetails) {
		SecretKey key = getSigningKey();
		//Her bir claim, token içinde key-value şeklinde saklanır.
		Map<String,String> claims = new HashMap<>();
		claims.put("iss", "https://secure.genuinecoder.com");
		//claims.put("name", "Bob");
		return Jwts.builder()
				.claims(claims)
				.subject(userDetails.getUsername()) //Kullanıcı adı
				.issuedAt(Date.from(Instant.now())) //Token'ın oluşturulma zamanı
				.expiration(Date.from(Instant.now().plusMillis(EXPIRATION_TIME))) //geçerlilik süresi
				.signWith(key) //HMAC-SHA256 (varsayılan) ile imzalama
				.compact(); //Token'ı oluştur
	}
	
	// SecretKey oluşturma metodu
    private SecretKey getSigningKey() {
        // Base64 ile kodlanmış secret keyi çözüp byte dizisine çeviriyoruz.
        byte[] keyBytes = Base64.getDecoder().decode(this.SECRET_KEY);
        //Bu byte dizisini HMAC-SHA algoritması ile kullanılabilecek bir "SecretKey" nesnesine dönüştürüyoruz
        return Keys.hmacShaKeyFor(keyBytes);
    }

	public String extractUsername(String jwt) {
		//usernami aldık
		Claims claims = extractAllClaims(jwt);
		return claims.getSubject();
	}

	public boolean isTokenExpired(String jwt) {
		Claims claims = extractAllClaims(jwt);
		return claims.getExpiration().after(Date.from(Instant.now()));
	}
	
    //Token'dan tüm claim bilgilerini çıkar (Yukarıda generate ettiğimiz tokeni burada parse ediyoruz)
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
    }

}
