package kz.hacknu.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class RoomNewDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nameRoom;

    private String description;

    private Integer type;


    public RoomNewDTO()
    { }

}
