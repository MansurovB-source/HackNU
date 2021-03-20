package kz.hacknu.web.service.exception;

public class UsernameAlreadyUsedException extends Exception {

    private static final long serialVersionUID = 1L;

    public UsernameAlreadyUsedException() {
        super("Login name already used!");
    }

}
