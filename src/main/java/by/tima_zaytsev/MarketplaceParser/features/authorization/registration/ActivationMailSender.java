package by.tima_zaytsev.MarketplaceParser.features.authorization.registration;

import by.tima_zaytsev.MarketplaceParser.features.authorization.common.JwtUtil;
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

    public void execute(String mail) {
        String activationLink = address + "/api/confirmation?token=" + jwtUtil.generateEmailToken(mail);
        String sendAgain = address + "/api/confirmation/" + mail + "/resend-confirmation";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setSubject("Account activation");
        message.setText("Please, activate your account: " + activationLink + "\n"
                + "or send message again: " + sendAgain);
        mailSender.send(message);
    }
}
