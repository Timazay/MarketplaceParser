package by.tima_zaytsev.MarketplaceParser.features.authorization.registration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RegistrationRequest {
    @JsonProperty(namespace = "email")
    private String email;
    @JsonProperty(namespace = "nickname")
    private String nickname;
    @JsonProperty(namespace = "age")
    private Integer age;
    @JsonProperty(namespace = "password")
    private String password;
    @JsonProperty(namespace = "avatar")
    private MultipartFile avatar;
}
