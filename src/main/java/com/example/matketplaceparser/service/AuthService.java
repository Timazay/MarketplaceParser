package com.example.matketplaceparser.service;

import com.example.matketplaceparser.dto.JwtRefreshAndAccess;
import com.example.matketplaceparser.dto.JwtRequest;
import com.example.matketplaceparser.entity.User;
import com.example.matketplaceparser.utils.JwtUtil;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
        @Autowired
        private UserService userService;
        @Autowired
        private PasswordEncoder passwordEncoder;
        @Autowired
        private JwtUtil jwtUtil;


        public JwtRefreshAndAccess login(@NonNull JwtRequest authRequest) throws AuthException {
            User user = userService.getUserByEmail(authRequest.getEmail());

            if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {

                if (!user.isConfirmed()){
                    throw new AuthException("Read your email");
                }
                UserDetails userDetails = userService.convertToUserDetails(user);
                String accessToken = jwtUtil.generateAccessToken(userDetails);
                String refreshToken = jwtUtil.generateRefreshToken(userDetails);
                user.setToken(refreshToken);

                userService.saveUser(user);
                return new JwtRefreshAndAccess(accessToken, refreshToken);
            } else {
                throw new AuthException("Wrong password");
            }
        }

}
