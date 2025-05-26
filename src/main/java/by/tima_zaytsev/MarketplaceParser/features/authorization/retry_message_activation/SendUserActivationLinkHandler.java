package by.tima_zaytsev.MarketplaceParser.features.authorization.retry_message_activation;

import by.tima_zaytsev.MarketplaceParser.common.exceptions.BadRequestException;
import by.tima_zaytsev.MarketplaceParser.features.authorization.common.ActivationMailSender;
import by.tima_zaytsev.MarketplaceParser.infrastracture.UserRepository;
import by.tima_zaytsev.MarketplaceParser.infrastracture.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SendUserActivationLinkHandler {
    @Autowired
    private UserRepository repository;
    @Autowired
    private ActivationMailSender senderMsg;

    public void execute(String email, String activationUrl) throws BadRequestException {
        User user = repository.findByEmail(email).orElseThrow(() -> new BadRequestException("There is no such user",
                Map.of("email", email)));
        if (user.isConfirmed()) {
            throw new BadRequestException("User is already confirmed!");
        }
        senderMsg.send(email, activationUrl);
    }
}
