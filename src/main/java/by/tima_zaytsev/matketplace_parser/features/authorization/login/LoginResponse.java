package by.tima_zaytsev.matketplace_parser.features.authorization.login;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginResponse {
    private String access;
    private String refresh;
}
