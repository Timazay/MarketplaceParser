package by.tima_zaytsev.MarketplaceParser.features.authorization.login;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
}