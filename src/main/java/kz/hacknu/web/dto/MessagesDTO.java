package kz.hacknu.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Behruz Mansurov
 */
@Getter
@Setter
public class MessagesDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String text;

    private String name;

    private String room;

    private String user_id;


    public MessagesDTO(){

    }

    @Override
    public String toString() {
        return "{" +
                "\"text\":\"" + text +
                "\", \"name\":\"" + name +
                "\", \"room\":\"" + room +
                "\", \"user_id\":\"" + user_id +
                "\"}";
    }
}
