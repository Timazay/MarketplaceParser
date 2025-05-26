package by.tima_zaytsev.MarketplaceParser.features.authorization.login;

import by.tima_zaytsev.MarketplaceParser.common.exceptions.UnauthorizedException;
import by.tima_zaytsev.MarketplaceParser.features.authorization.common.JwtUtil;
import by.tima_zaytsev.MarketplaceParser.infrastracture.UserRepository;
import by.tima_zaytsev.MarketplaceParser.infrastracture.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginHandler {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsConverter convertToUserDetails;
    @Autowired
    private JwtUtil jwtUtil;
    private final String NO_SUCH_USER = "There is no such user";

    public LoginResponse execute(LoginRequest request) throws UnauthorizedException {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException(NO_SUCH_USER));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException(NO_SUCH_USER);
        }

        if (!user.isConfirmed()) {
            throw new UnauthorizedException("Read your email, you should confirm your email");
        }
        UserDetails userDetails = convertToUserDetails.convert(user);
        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);
        user.setToken(refreshToken);

        userRepository.save(user);
        return new LoginResponse(accessToken, refreshToken);
    }
}
