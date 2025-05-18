package by.tima_zaytsev.MarketplaceParser.features.authorization.registration;

import by.tima_zaytsev.MarketplaceParser.common.exceptions.JwtExpirationException;
import by.tima_zaytsev.MarketplaceParser.common.exceptions.SendMsgException;
import by.tima_zaytsev.MarketplaceParser.common.exceptions.UserNotFoundException;
import by.tima_zaytsev.MarketplaceParser.features.authorization.common.JwtUtil;
import by.tima_zaytsev.MarketplaceParser.infrastracture.UserRepository;
import by.tima_zaytsev.MarketplaceParser.infrastracture.entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RegistrationActivation {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    public void execute(String token) throws UserNotFoundException, SendMsgException, JwtExpirationException {
        String email = null;
        try {
        email = jwtUtil.getMail(token);
        } catch (ExpiredJwtException e){
            throw new JwtExpirationException("Try to send message again", HttpStatus.UNAUTHORIZED.value());
        }
        if (!jwtUtil.isContainToken(token)){
            throw new SendMsgException("Try to send message again", HttpStatus.UNAUTHORIZED.value());
        }
        Map<String, String> metadata = Map.of("email", email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found",
                HttpStatus.BAD_REQUEST.value(),
                metadata));
        user.setConfirmed(true);
        userRepository.save(user);
    }
}
