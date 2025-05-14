package by.tima_zaytsev.matketplace_parser.features.authorization.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class RegistrationSenderMsg {
    @Autowired
    private JavaMailSender mailSender;

    public void execute(String mail) {
        String activationLink = "http://localhost:8080/api/users/activate?email=" + mail;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setSubject("Account activation");
        message.setText("Please, activate your account: " + activationLink);
            mailSender.send(message);
    }
}
