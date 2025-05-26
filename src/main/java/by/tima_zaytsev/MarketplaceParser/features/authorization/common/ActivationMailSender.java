package by.tima_zaytsev.MarketplaceParser.features.authorization.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class ActivationMailSender {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private JwtUtil jwtUtil;
    @Value("${server.address-url}")
    private String address;

    public void send(String mail, String activationUrl) {
        String activationLink = address + "/api" + activationUrl +"?token=" + jwtUtil.generateEmailToken(mail);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setSubject("Account activation");
        message.setText("Please, activate your account: " + activationLink);
        mailSender.send(message);
    }
}
