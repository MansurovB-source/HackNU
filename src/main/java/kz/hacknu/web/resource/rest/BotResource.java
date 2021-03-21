package kz.hacknu.web.resource.rest;

import kz.hacknu.web.dto.MessagesDTO;
import kz.hacknu.web.service.MessagesService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/bot")
public class BotResource {

    private final MessagesService messagesService;

    private final RabbitTemplate rabbitTemplate;
    public BotResource(MessagesService messagesService, RabbitTemplate rabbitTemplate) {
        this.messagesService = messagesService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping()
     public ResponseEntity<String> create(@RequestBody MessagesDTO messagesDTO) {
        System.out.println(messagesDTO.toString());
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
