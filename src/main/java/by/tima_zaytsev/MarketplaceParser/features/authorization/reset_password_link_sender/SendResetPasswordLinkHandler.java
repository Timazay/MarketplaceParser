package by.tima_zaytsev.MarketplaceParser.features.authorization.reset_password_link_sender;

import by.tima_zaytsev.MarketplaceParser.common.exceptions.BadRequestException;
import by.tima_zaytsev.MarketplaceParser.features.authorization.common.EmailValidator;
import by.tima_zaytsev.MarketplaceParser.features.authorization.common.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
public class SendResetPasswordLinkHandler {
    @Autowired
    private MailSender mailSender;
    @Value("${server.address-url}")
    private String address;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private EmailValidator emailValidator;

    public void execute(String email) throws BadRequestException, UnsupportedEncodingException {
        emailValidator.validateEmail(email);
        String emailLink = address + "/user/" + email +"/reset-password?token="
                + jwtUtil.generateEmailToken(email);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Reset Password");
        message.setText("To reset password go through the link: " + emailLink);
        mailSender.send(message);
    }
}
