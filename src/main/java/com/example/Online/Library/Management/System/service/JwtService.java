package com.example.Online.Library.Management.System.service;

import com.example.Online.Library.Management.System.model.Users;
import com.example.Online.Library.Management.System.repo.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtService {
    @Autowired
 private UserRepo userRepo;
    @Value("${jwt.secret}")
    private String secretKey;
    public String generateToken(String users){
       Optional<Users> username = userRepo.findByGmail(users);
       Users unpack = username.orElseThrow(()->new RuntimeException("User not found"));
        String name = unpack.getFirstName()+" "+unpack.getLastName();

        Map <String,Object> claims= new HashMap<>();
        return Jwts
                .builder()
                .claims().add(claims)
                .subject(users)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*15))
                .and()
                .signWith(getKey())
                .compact();
    }
    public SecretKey getKey(){
        byte[] key = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }

    public String extractGmail(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims,T> claimResolver) {
    final Claims claim = extractAllClaim(token);
    return claimResolver.apply(claim);
    }

    private Claims extractAllClaim(String token) {
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String extractedEmail = extractGmail(token); // this returns "mluja464@gmail.com"
        String loadedEmail = userDetails.getUsername(); // must also return "mluja464@gmail.com"

        System.out.println("Token email: " + extractedEmail);
        System.out.println("UserDetails email: " + loadedEmail); // 🔍 do these match?

        return extractedEmail.equals(loadedEmail) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token,Claims::getExpiration);
    }
}
