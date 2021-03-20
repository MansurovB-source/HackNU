package kz.hacknu.web.service;

import kz.hacknu.web.domain.Messages;
import kz.hacknu.web.domain.security.User;
import kz.hacknu.web.dto.MessagesDTO;
import kz.hacknu.web.repository.MessagesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessagesService {

    private final MessagesRepository messagesRepository;


    public MessagesService(MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    public Messages create(MessagesDTO messagesDTO, User user) {
        Messages messages = new Messages();
        messages.setText(messagesDTO.getText());
        messages.setRoomId(messagesDTO.getRoomId());

        // TODO how to get a userID
        messages.setUserId(user.getId());
//        messages.setUserId(messagesDTO.getUserId());
        return messagesRepository.save(messages);
    }

    public Page<Messages> listMessages(Long roomId, Pageable pageable) {
        return messagesRepository.findAllByRoomIdOrderByCreatedAt(roomId, pageable);
    }
}



