package kz.hacknu.web.resource.rest;

import kz.hacknu.web.dto.MessagesDTO;
import kz.hacknu.web.service.HttpServiceComponent;
import kz.hacknu.web.service.MessagesService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/api/bot")
public class BotResource {

    private final MessagesService messagesService;

    private final HttpServiceComponent httpServiceComponent;

    private final RabbitTemplate rabbitTemplate;
    public BotResource(MessagesService messagesService, HttpServiceComponent httpServiceComponent, RabbitTemplate rabbitTemplate) {
        this.messagesService = messagesService;
        this.httpServiceComponent = httpServiceComponent;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping()
     public ResponseEntity<String> create(@RequestBody MessagesDTO messagesDTO) {
        System.out.println(messagesDTO.toString());
        try {
            httpServiceComponent.post("https://hacknu-telegram-bot.herokuapp.com/room/updates",
                    new HashMap<>(), messagesDTO.toString());
            httpServiceComponent.post("https://hacknu-mail-ru-agent-bot.herokuapp.com/room/updates",
                    new HashMap<>(), messagesDTO.toString());
            httpServiceComponent.post("https://hacknu-facebook-bot.herokuapp.com/room/updates",
                    new HashMap<>(), messagesDTO.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        rabbitTemplate.convertAndSend("sockets", messagesDTO.toString());
        try {
            messagesService.create(messagesDTO);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return ResponseEntity.ok("success");
    }



}
