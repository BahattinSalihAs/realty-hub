package realtyhub.common.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public final class JwtService {

    @Value("${spring.security.jwt.secret}")
    private String SECRET_KEY;

    public final String findUserEmail(
            final String token
    ) {
        return exportToken(token, Claims::getSubject);
    }

    private final <T> T exportToken(final String token,
                                    final Function<Claims, T> claimsTFunction
    ) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build().parseClaimsJws(token).getBody();

        return claimsTFunction.apply(claims);
    }

    private final Key getKey() {
        byte[] key = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);
    }

    public final boolean tokenControl(final String jwt,
                                      final UserDetails userDetails
    ) {
        final String email = findUserEmail(jwt);

        return (email.equals(userDetails.getUsername()) && !exportToken(jwt, Claims::getExpiration).before(new Date()));
    }

    public final String generateToken(final UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 24 * 30)))  // 30 days to valid
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }


}
