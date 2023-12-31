package com.challenge.challenge.service;


import com.challenge.challenge.exceptions.AuthException;
import com.challenge.challenge.exceptions.GlobalException;
import com.challenge.challenge.model.SimpleUserAuth;
import com.challenge.challenge.model.TokenResponse;
import com.challenge.challenge.model.UserCore;
import com.challenge.challenge.repository.UserRepository;
import com.challenge.challenge.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    public final static String CERT_WEBSITE_EMAIL = "CERT-WEB";
    private final static int MAX_RISK = 3;
    private final JwtService jwtService;
    private final UserRepository userCoreRepo;
    private final PasswordEncoder encoder;

    public Boolean logOutUser(String userId) {
        JwtService.JWTData data = jwtService.removeKey(userId);
        return data != null;
    }

    public TokenResponse authenticateUser(SimpleUserAuth userAuth, HttpServletRequest request) {
        UserCore userCore = userCoreRepo.findByUsername(userAuth.getUsername());
        if (userCore == null)
            throw new AuthException("User not found");

        if (!userCore.isAccountEnabled() || userCore.isAccountLocked() || userCore.isAccountExpired()) {
            throw new AuthException("The account is disabled");
        }
        if (userAuth.getPassword() == null || !encoder.matches(userAuth.getPassword(), userCore.getPassword())) {
            userCore.setRisk(userCore.getRisk() + 1);
            if (userCore.getRisk() + 1 > MAX_RISK)
                userCore.setAccountEnabled(false);
            userCoreRepo.save(userCore);
            throw new AuthException("Invalid credentials");
        }
        userCore.setRisk(0);
        userCoreRepo.save(userCore);

        jwtService.clearExpiredKeys();
        String token = jwtService.generateToken(userCore, request);
        return new TokenResponse(token,"success");
    }
}
