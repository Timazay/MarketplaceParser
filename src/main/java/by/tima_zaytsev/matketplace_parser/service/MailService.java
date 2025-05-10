package by.tima_zaytsev.matketplace_parser.service;

import by.tima_zaytsev.matketplace_parser.entity.User;
import by.tima_zaytsev.matketplace_parser.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;

    public void sendActivationEmail(String mail) {
        String activationLink = "http://localhost:8080/api/users/activate?email=" + mail;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setSubject("Account activation");
        message.setText("Please, activate your account: " + activationLink);
        mailSender.send(message);
    }

    public void activateUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not active"));
        if (user != null) {
            user.setConfirmed(true);
            userRepository.save(user);
        }
    }
}
