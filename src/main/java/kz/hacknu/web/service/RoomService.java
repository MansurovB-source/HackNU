package kz.hacknu.web.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import kz.hacknu.web.domain.QRoom;
import kz.hacknu.web.domain.Room;
import kz.hacknu.web.domain.security.User;
import kz.hacknu.web.dto.RoomNewDTO;
import kz.hacknu.web.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RoomService {

    private final RoomRepository roomRepository;


    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room create(RoomNewDTO newRoom)
    {
        Room room = new Room();
        room.setNameRoom(newRoom.getNameRoom());
        room.setDescription(newRoom.getDescription());
        room.setCounter(1);
        room.setType(newRoom.getType());
        room.setActive(true);
        return roomRepository.save(room);
    }

    public Room findOne(Long id)
    {
        return roomRepository.findById(id).orElseThrow();
    }

    public Page<Room> listPage(Predicate predicate, Pageable pageable)
    {
        final BooleanBuilder booleanBuilder = new BooleanBuilder(predicate);
        QRoom qRoom = QRoom.room;
        booleanBuilder.and(qRoom.active.eq(true));
        return roomRepository.findAll(predicate, pageable);
    }

    public Set<User> listUserSit(Long roomId, Pageable pageable)
    {
        Room room = roomRepository.findById(roomId).orElseThrow();
        return room.getSitUsers();
    }

    public Set<User> listUsersMod(Long roomId)
    {
        Room room = roomRepository.findById(roomId).orElseThrow();
        return room.getModUsers();
    }


    public Room setActiveToFalse(Long roomId)
    {
        Room room = roomRepository.findById(roomId).orElseThrow();
        room.setActive(false);
        return roomRepository.save(room);
    }





}
