package by.tima_zaytsev.MarketplaceParser.features.authorization.activation_account;

import by.tima_zaytsev.MarketplaceParser.common.exceptions.*;
import by.tima_zaytsev.MarketplaceParser.features.authorization.common.JwtUtil;
import by.tima_zaytsev.MarketplaceParser.infrastracture.UserRepository;
import by.tima_zaytsev.MarketplaceParser.infrastracture.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserActivationHandler {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    public void execute(String token) throws BadRequestException, UnauthorizedException {
        String email = null;
        email = jwtUtil.getMail(token, jwtUtil.SECRET_EMAIL);
        if (!jwtUtil.validateToken(token, jwtUtil.SECRET_EMAIL)) {
            throw new UnauthorizedException("Try to send message again");
        }
        Map<String, String> metadata = Map.of("email", email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new BadRequestException("User not found",
                metadata));
        user.setConfirmed(true);
        userRepository.save(user);
    }
}
