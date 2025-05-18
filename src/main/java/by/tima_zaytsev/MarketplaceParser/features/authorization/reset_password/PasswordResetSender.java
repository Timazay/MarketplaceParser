package by.tima_zaytsev.MarketplaceParser.features.authorization.reset_password;

import by.tima_zaytsev.MarketplaceParser.features.authorization.common.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetSender {
    @Autowired
    private MailSender mailSender;
    @Value("${server.address-url}")
    private String address;
    @Autowired
    private JwtUtil jwtUtil;
    public void execute(String email){
        String emailLink = address + "/user/reset-password/"
                + jwtUtil.generateEmailToken(email);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Reset Password");
        message.setText("To reset password go through the link: " + emailLink);
        mailSender.send(message);
    }
}
