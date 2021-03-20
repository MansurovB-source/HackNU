package kz.hacknu.web.dto;


import kz.hacknu.web.domain.Room;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RoomDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String nameRoom;

    private String description;

    private Integer counter;

    private Integer type;

    private Boolean active;

    public RoomDTO(Room room) {
        this.id = room.getId();
        this.nameRoom = room.getNameRoom();
        this.description = room.getDescription();
        this.counter = room.getCounter();
        this.type = room.getType();
        this.active = room.getActive();
    }

}
