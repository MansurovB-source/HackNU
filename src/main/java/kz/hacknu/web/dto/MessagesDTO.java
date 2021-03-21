package kz.hacknu.web.dto;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Behruz Mansurov
 */
public class MessagesDTO {

    private String text;

    private Long roomId;

    public String getText() {
        return text;
    }

    public Long getRoomId() {
        return roomId;
    }
}
