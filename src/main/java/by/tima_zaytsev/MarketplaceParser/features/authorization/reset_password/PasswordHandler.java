package by.tima_zaytsev.MarketplaceParser.features.authorization.reset_password;

import by.tima_zaytsev.MarketplaceParser.common.exceptions.JwtExpirationException;
import by.tima_zaytsev.MarketplaceParser.common.exceptions.RegValidationException;
import by.tima_zaytsev.MarketplaceParser.common.exceptions.SendMsgException;
import by.tima_zaytsev.MarketplaceParser.common.exceptions.UserNotFoundException;
import by.tima_zaytsev.MarketplaceParser.features.authorization.common.JwtUtil;
import by.tima_zaytsev.MarketplaceParser.features.authorization.common.PasswordValidator;
import by.tima_zaytsev.MarketplaceParser.infrastracture.UserRepository;
import by.tima_zaytsev.MarketplaceParser.infrastracture.entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordHandler {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordValidator passwordValidator;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    public void execute(String token, String password) throws JwtExpirationException, SendMsgException,
            RegValidationException, UserNotFoundException {
        String email = null;
        try {
            email = jwtUtil.getMail(token);
        } catch (ExpiredJwtException e){
            throw new JwtExpirationException("Try to send message again",
                    HttpStatus.GONE.value());
        }
        passwordValidator.execute(password);
        if (!jwtUtil.isContainToken(token)){
            throw new SendMsgException("Try to send message again", HttpStatus.GONE.value());
        } else{
            jwtUtil.removeToken(token);
        }
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException("There is no such user", HttpStatus.NOT_FOUND.value()));
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}
