package by.tima_zaytsev.MarketplaceParser.features.authorization.login;

import by.tima_zaytsev.MarketplaceParser.common.exceptions.UserNotFoundException;
import by.tima_zaytsev.MarketplaceParser.features.authorization.common.JwtUtil;
import by.tima_zaytsev.MarketplaceParser.infrastracture.UserRepository;
import by.tima_zaytsev.MarketplaceParser.infrastracture.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private LoginUserDetailsConvert convertToUserDetails;
    @Autowired
    private JwtUtil jwtUtil;
    private final String NO_SUCH_USER = "There is no such user";

    public LoginResponse execute(LoginRequest request) throws UserNotFoundException {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(NO_SUCH_USER, HttpStatus.UNAUTHORIZED.value()));

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {

            if (!user.isConfirmed()) {
                throw new UserNotFoundException("Read your email", HttpStatus.UNAUTHORIZED.value());
            }
            UserDetails userDetails = convertToUserDetails.execute(user);
            String accessToken = jwtUtil.generateAccessToken(userDetails);
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);
            user.setToken(refreshToken);

            userRepository.save(user);
            return new LoginResponse(accessToken, refreshToken);
        } else {
            throw new UserNotFoundException(NO_SUCH_USER, HttpStatus.UNAUTHORIZED.value());
        }
    }
}
