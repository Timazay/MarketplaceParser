package by.tima_zaytsev.MarketplaceParser.features.authorization.registration;

import by.tima_zaytsev.MarketplaceParser.features.authorization.common.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailSenderMsg {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private JwtUtil jwtUtil;
    @Value("${server.address-url}")
    private String address;

    public void execute(String mail) {
        String activationLink = address + "/api/users/activate?token=" + jwtUtil.generateEmailToken(mail);
        String sendAgain = address + "/api/users/send?email=" + mail;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setSubject("Account activation");
        message.setText("Please, activate your account: " + activationLink + "\n"
                + "Or send msg again: " + sendAgain);
        mailSender.send(message);
    }
}
