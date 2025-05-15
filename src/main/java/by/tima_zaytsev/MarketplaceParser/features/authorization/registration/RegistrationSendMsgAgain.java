package by.tima_zaytsev.MarketplaceParser.features.authorization.registration;

import by.tima_zaytsev.MarketplaceParser.common.exceptions.RegistrationSendMsgException;
import by.tima_zaytsev.MarketplaceParser.common.exceptions.UserNotFoundException;
import by.tima_zaytsev.MarketplaceParser.infrastracture.UserRepository;
import by.tima_zaytsev.MarketplaceParser.infrastracture.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RegistrationSendMsgAgain {
    @Autowired
    private UserRepository repository;
    @Autowired
    private MailSenderMsg senderMsg;

    public void execute(String email) throws UserNotFoundException, RegistrationSendMsgException {
        User user = repository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found",
                HttpStatus.BAD_REQUEST.value(),
                Map.of("email", email)));
        if (!user.isConfirmed()){
            senderMsg.execute(email);
        } else {
            throw new RegistrationSendMsgException("User is already confirmed!", HttpStatus.BAD_REQUEST.value());
        }
    }
}
