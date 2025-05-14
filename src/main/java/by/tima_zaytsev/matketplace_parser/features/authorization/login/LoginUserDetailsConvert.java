package by.tima_zaytsev.matketplace_parser.features.authorization.login;

import by.tima_zaytsev.matketplace_parser.infrastracture.entity.Role;
import by.tima_zaytsev.matketplace_parser.infrastracture.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoginUserDetailsConvert {
    public UserDetails execute(User user){
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getNickname(),
                user.getPassword(),
                authorities);
    }
}
