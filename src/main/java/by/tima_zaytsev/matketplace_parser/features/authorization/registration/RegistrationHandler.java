package by.tima_zaytsev.matketplace_parser.features.authorization.registration;

import by.tima_zaytsev.matketplace_parser.common.exceptions.RegValidationException;
import by.tima_zaytsev.matketplace_parser.infrastracture.entity.Role;
import by.tima_zaytsev.matketplace_parser.infrastracture.entity.User;
import by.tima_zaytsev.matketplace_parser.infrastracture.RoleRepository;
import by.tima_zaytsev.matketplace_parser.infrastracture.UserRepository;
import by.tima_zaytsev.matketplace_parser.infrastracture.MinioService;
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
    private RegistrationSenderMsg senderMsg;


    public void execute(RegistrationRequest request) throws RegValidationException, Exception {
        validator.execute(request);

        String avatarUrl = null;
        User user = new User();
        if (request.getAvatar() != null && !request.getAvatar().isEmpty()) {
                avatarUrl = minioService.uploadAvatarToMinio(request.getAvatar());
            user.setAvatarUrl(avatarUrl);
        }
        Role role = roleRepository.findByName("USER");

        user.setEmail(request.getEmail());
        user.setNickname(request.getNickname());
        user.setAge(request.getAge());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRoles(List.of(role));

        userRepository.save(user);
        senderMsg.execute(user.getEmail());
    }
}
