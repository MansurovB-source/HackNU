package kz.hacknu.web.resource.rest;


import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kz.hacknu.web.domain.Room;
import kz.hacknu.web.domain.security.User;
import kz.hacknu.web.dto.RoomDTO;
import kz.hacknu.web.dto.RoomNewDTO;
import kz.hacknu.web.dto.UserDTO;
import kz.hacknu.web.service.RoomService;
import kz.hacknu.web.service.annotation.GetUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/room")
@Api(tags = "room resource:", description = " room create, get room, set Active")
public class RoomResource {

    private final RoomService roomService;


    public RoomResource(RoomService roomService) {
        this.roomService = roomService;
    }


    @ApiOperation(value = "Request create room; access: ANY USERS")
    @PostMapping(path ="/create")
    public RoomDTO createRoom(
            @ApiIgnore
            @GetUser User user,
            @RequestBody RoomNewDTO roomNewDTO) {

        return new RoomDTO(roomService.create(roomNewDTO, user));
    }


    @ApiOperation(value = "Get list of room with parameters and pageable")
    @GetMapping(path = "/rooms")
    public Page<RoomDTO> getList(
            @QuerydslPredicate(root = Room.class) Predicate predicate,
            Pageable pageable
    ) {
        return roomService.listPage(predicate, pageable).map(RoomDTO::new);
    }


    @ApiOperation(value = "Get list of active users in room")
    @GetMapping("/room-users")
    public Set<UserDTO> getUserSitInRoom(
            @RequestParam Long roomId
    ){
        return roomService.listUserSit(roomId).stream().map(UserDTO::new).collect(Collectors.toSet());
    }

    @ApiOperation(value = "Get moderators of room")
    @GetMapping("/room-mod")
    public Set<UserDTO> getUserModerator(
            @RequestParam Long roomId
    ) {
        return roomService.listUsersMod(roomId).stream().map(UserDTO::new).collect(Collectors.toSet());
    }

    @ApiOperation(value = "Set room inactive")
    @GetMapping("/room-set-inactive")
    public RoomDTO setInactive(
            @RequestParam Long roomId
    ) {
        return new RoomDTO(roomService.setActiveToFalse(roomId));
    }

    @ApiOperation(value = "Joining to room")
    @GetMapping("/join")
    public RoomDTO joinToRoom(
            @ApiIgnore
            @GetUser User user,
            @RequestParam Long roomId
    ){
        return new RoomDTO(roomService.join(roomId, user));
    }
}
