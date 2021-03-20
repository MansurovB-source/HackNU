package kz.hacknu.web.resource.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import kz.hacknu.web.domain.security.User;
import kz.hacknu.web.dto.NewUserDTO;
import kz.hacknu.web.resource.vm.KeyAndPasswordVM;
import kz.hacknu.web.service.UserService;
import kz.hacknu.web.service.exception.EmailAlreadyUsedException;
import kz.hacknu.web.service.exception.UsernameAlreadyUsedException;
import kz.hacknu.web.service.impl.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@RestController
@RequestMapping(value = "/api/account")
@Api(tags = "account resource:", description = " password recover, request password reset, check is authenticated.")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserService userService;

    private final MailService mailService;

    public AccountResource(UserService userService, MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }

    @ApiOperation(value = "Check is authenticated or not; access: ANY",
        notes = "if authenticated it will return username")
    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        return request.getRemoteUser();
    }

    @ApiOperation(value = "Request create account; access: ANY",
            notes = "if registrated it will return")

    @PostMapping(path ="/create")
    public ResponseEntity<?> create(@RequestBody NewUserDTO newUserDTO) {
        try {
            User user = userService.addUser(newUserDTO, new User());
        } catch (EmailAlreadyUsedException | UsernameAlreadyUsedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("User created", HttpStatus.CREATED);
    }

    @ApiOperation(value = "Recovery password with giving key; access: ANY",
            notes = "You will get key after request password reset or in creating account.")
    @PostMapping(path = "/reset-password/finish")
    public void finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) throws Exception {

        Optional<User> user =
                userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());

        if (user.isEmpty()) {
            throw new Exception("No user was found for this reset key");
        }
    }

    @ApiOperation(value = "Request password reset; access: ANY",
            notes = "After request in email you will get message with key to recover password.")
    @PostMapping(path = "/reset-password/init")
    public void requestPasswordReset(@RequestBody String email) throws NotFoundException {
        Optional<User> user = userService.requestPasswordReset(email);
        if (user.isPresent()) {
            mailService.sendPasswordResetMail(user.get());
        } else {
            log.warn("Password reset requested for non existing mail");
            throw new NotFoundException("Email not found");
        }
    }

}
