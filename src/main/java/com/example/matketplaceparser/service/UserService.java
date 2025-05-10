package com.example.matketplaceparser.service;

import com.example.matketplaceparser.dto.UserRegistrationRequest;
import com.example.matketplaceparser.entity.Role;
import com.example.matketplaceparser.entity.User;
import com.example.matketplaceparser.repository.RoleRepository;
import com.example.matketplaceparser.repository.UserRepository;
import com.example.matketplaceparser.utils.ValidationUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private MinioService minioService;
    @Autowired
    private ValidationUtil isValidAccount;
    @Autowired
    private RoleRepository roleRepository;


    public User registerUser(UserRegistrationRequest request) throws Exception {
        isValidAccount.isValidAccount(request.getEmail(), request.getNickname(), request.getAge());

        String password = request.getPassword();
        if (!isValidAccount.isValidPassword(password)) {
            throw new IllegalArgumentException("Password must be at least 6 characters long including at least one lowercase letter and one number");
        }

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
        user.setPassword(encoder.encode(password));
        user.setRoles(List.of(role));

        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("There is no such user"));
    }
    public User saveUser(User user) {
        return userRepository.save(user);
    }


    @Transactional
    public UserDetails convertToUserDetails(User user) throws UsernameNotFoundException {
        return new org.springframework.security.core.userdetails.User(
                user.getNickname(),
                user.getPassword(),
                getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }

        return authorities;
    }

}
