package kz.hacknu.web.repository;

import kz.hacknu.web.domain.QRoom;
import kz.hacknu.web.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import javax.validation.constraints.NotNull;

public interface RoomRepository extends JpaRepository<Room, Long>,
        QuerydslPredicateExecutor<Room>, QuerydslBinderCustomizer<QRoom> {


    @Override
    default void customize(@NotNull QuerydslBindings bindings, @NotNull QRoom qRoom){

    }

}
