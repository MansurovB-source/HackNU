package kz.hacknu.web.resource.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kz.hacknu.web.domain.Messages;
import kz.hacknu.web.domain.security.User;
import kz.hacknu.web.dto.MessagesDTO;
import kz.hacknu.web.service.MessagesService;
import kz.hacknu.web.service.annotation.GetUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Behruz Mansurov
 */

@RestController
@RequestMapping(value = "api/messages")
@Api(tags = "messages resources")
public class MessagesResource {

    private final MessagesService messagesService;

    public MessagesResource(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

//    @ApiOperation(value = "Request create message; access: ANY USERS")
//    @PostMapping("/create")
//    public Messages create(@GetUser User user,
//                           @RequestBody MessagesDTO messagesDTO) {
//       return messagesService.create(messagesDTO, user);
//    }

    @ApiOperation(value = "Reques to get messages: access: ANY USERS")
    @GetMapping("/{roomId}")
    public Page<Messages> getMessages(@PathVariable("roomId") Long roomId, Pageable pageable) {
        return messagesService.listMessages(roomId, pageable);
    }

}
