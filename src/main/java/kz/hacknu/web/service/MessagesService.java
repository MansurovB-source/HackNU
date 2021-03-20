package kz.hacknu.web.service;

import kz.hacknu.web.repository.MessagesRepository;
import org.springframework.stereotype.Service;

@Service
public class MessagesService {

    private final MessagesRepository messagesRepository;


    public MessagesService(MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }
}



