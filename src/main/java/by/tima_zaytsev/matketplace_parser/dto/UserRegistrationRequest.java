package by.tima_zaytsev.matketplace_parser.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.multipart.MultipartFile;


@Data
@AllArgsConstructor
public class UserRegistrationRequest {
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
