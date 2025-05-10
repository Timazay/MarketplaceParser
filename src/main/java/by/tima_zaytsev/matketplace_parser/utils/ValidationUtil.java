package by.tima_zaytsev.matketplace_parser.utils;

import by.tima_zaytsev.matketplace_parser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ValidationUtil {
    @Autowired
    private UserRepository userRepository;

    public boolean isValidPassword(String password) {
        if (password.length() < 6) return false;
        Pattern lowerCasePattern = Pattern.compile("[a-z]");
        Pattern digitPattern = Pattern.compile("[0-9]");
        return lowerCasePattern.matcher(password).find() && digitPattern.matcher(password).find();
    }


    public void isValidAccount(String mail, String nick, int age) {

        if (userRepository.existsByEmail(mail)) {
            throw new IllegalArgumentException("Email already exist");
        }
        if (userRepository.existsByNickname(nick)) {
            throw new IllegalArgumentException("Nickname already exist");
        }
        if (nick.length() < 6) {
            throw new IllegalArgumentException("Nickname must contain min 6 symbols");
        }

        if (age < 14 || age > 100) {
            throw new IllegalArgumentException("Age must be from 14 to 100");
        }
    }
}
