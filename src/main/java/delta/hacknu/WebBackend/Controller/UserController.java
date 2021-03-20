package delta.hacknu.WebBackend.Controller;

import delta.hacknu.WebBackend.Model.User;
import delta.hacknu.WebBackend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Behruz Mansurov
 */

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")
    public ResponseEntity<?> login(Principal principal) {
        User user;

        // we don't need the if but just in case (if не нужен но на всякий случай)
        if (principal == null) {
            return new ResponseEntity<>("Wrong username or password, bitch", HttpStatus.UNAUTHORIZED);
        } else {
            // for debug
            System.out.println(principal.getName());
            user = userService.findByUsername(principal.getName());
        }
        return new ResponseEntity<>(user.getId(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        Pattern pattern = Pattern.compile(
                "[" +                   //начало списка допустимых символов
                        "а-яА-ЯёЁ" +    //буквы русского алфавита
                        "\\p{Punct}" +  //знаки пунктуации
                        "]" +                   //конец списка допустимых символов
                        "*");                   //допускается наличие указанных символов в любом количестве

        if (pattern.matcher(user.getUsername()).matches()) {
            return new ResponseEntity<>("Unacceptable symbols", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        } else {
            if (userService.findByUsername(user.getUsername()) != null) {
                // for debug
                System.out.println(user.getUsername());
                return new ResponseEntity<>("User with username: " + user.getUsername() + "already exist", HttpStatus.CONFLICT);
            }
        }

        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }
}
