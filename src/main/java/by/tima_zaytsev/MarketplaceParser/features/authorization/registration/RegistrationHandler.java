package by.tima_zaytsev.MarketplaceParser.features.authorization.registration;

import by.tima_zaytsev.MarketplaceParser.features.authorization.common.ActivationMailSender;
import by.tima_zaytsev.MarketplaceParser.infrastracture.entity.Role;
import by.tima_zaytsev.MarketplaceParser.infrastracture.entity.User;
import by.tima_zaytsev.MarketplaceParser.infrastracture.RoleRepository;
import by.tima_zaytsev.MarketplaceParser.infrastracture.UserRepository;
import by.tima_zaytsev.MarketplaceParser.infrastracture.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationHandler {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private MinioService minioService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RegistrationValidator validator;
    @Autowired
    private ActivationMailSender senderMsg;

    public void execute(RegistrationRequest request, String activationUrl) throws Exception{
        validator.execute(request);

        String avatarUrl = null;
        User user = new User();
        if (request.getAvatar() != null && !request.getAvatar().isEmpty()) {
            avatarUrl = minioService.uploadImage(request.getAvatar(), request.getEmail());
            user.setAvatarUrl(avatarUrl);
        }
        Role role = roleRepository.findByName("USER");

        user.setEmail(request.getEmail());
        user.setNickname(request.getNickname());
        user.setAge(request.getAge());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRoles(List.of(role));

        userRepository.save(user);
        senderMsg.send(user.getEmail(), activationUrl);
    }
}
