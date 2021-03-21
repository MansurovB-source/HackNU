package kz.hacknu.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.hacknu.web.domain.Messages;
import kz.hacknu.web.dto.MessagesDTO;
import kz.hacknu.web.repository.MessagesRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MessagesService {

    private final MessagesRepository messagesRepository;

    private final RabbitTemplate rabbitTemplate;

    private final HttpServiceComponent httpServiceComponent;


    public MessagesService(MessagesRepository messagesRepository, RabbitTemplate rabbitTemplate, HttpServiceComponent httpServiceComponent) {
        this.messagesRepository = messagesRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.httpServiceComponent = httpServiceComponent;
    }

    public Messages create(MessagesDTO messagesDTO) {
        Messages messages = new Messages();
        messages.setText(messagesDTO.getText());
        messages.setRoomId(Long.parseLong(messagesDTO.getRoom()));
        messages.setUserId(Long.parseLong(messagesDTO.getUser_id()));;
        return messagesRepository.save(messages);
    }

    public Page<Messages> listMessages(Long roomId, Pageable pageable) {
        return messagesRepository.findAllByRoomIdOrderByCreatedAt(roomId, pageable);
    }

    @RabbitListener(queues = "events")
    public void listen(String in) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            httpServiceComponent.post("https://hacknu-telegram-bot.herokuapp.com/room/updates",
            new HashMap<>(), in);
            httpServiceComponent.post("https://hacknu-mail-ru-agent-bot.herokuapp.com/room/updates",
                    new HashMap<>(), in);
            httpServiceComponent.post("https://hacknu-facebook-bot.herokuapp.com/room/updates",
                    new HashMap<>(), in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Map<String, Object> map = mapper.readValue(in, Map.class);
            MessagesDTO messagesDTO = new MessagesDTO();
            messagesDTO.setRoom(((String) map.get("room")));
            messagesDTO.setText((String) map.get("text"));
            messagesDTO.setUser_id(((String) map.get("user_id")));
            create(messagesDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}



