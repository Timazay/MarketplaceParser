package by.tima_zaytsev.MarketplaceParser.features.authorization.reset_password;

import by.tima_zaytsev.MarketplaceParser.common.exceptions.*;
import by.tima_zaytsev.MarketplaceParser.features.authorization.common.JwtUtil;
import by.tima_zaytsev.MarketplaceParser.features.authorization.common.PasswordValidator;
import by.tima_zaytsev.MarketplaceParser.infrastracture.UserRepository;
import by.tima_zaytsev.MarketplaceParser.infrastracture.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordHandler {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordValidator passwordValidator;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public void execute(String email, String token, String password) throws NotFoundException, BadRequestException {
        passwordValidator.execute(password);
        if (!jwtUtil.validateToken(token, jwtUtil.SECRET_EMAIL)
                && !jwtUtil.getMail(token, jwtUtil.SECRET_EMAIL).equals(email))
            throw new BadRequestException("Try to send message again");
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new NotFoundException("There is no such user"));
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}
