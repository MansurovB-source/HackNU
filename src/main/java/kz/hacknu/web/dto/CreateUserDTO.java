package kz.hacknu.web.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Behruz Mansurov
 */
public class CreateUserDTO {

    @Size(min = 1, max = 50)
    private String login;

    @Size(min = 60, max = 60)
    private String password;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
