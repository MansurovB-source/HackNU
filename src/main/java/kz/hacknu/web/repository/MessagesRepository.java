package kz.hacknu.web.repository;

import kz.hacknu.web.domain.Messages;
import kz.hacknu.web.domain.QMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import javax.validation.constraints.NotNull;

public interface MessagesRepository extends JpaRepository<Messages, Long>,
        QuerydslPredicateExecutor<Messages>, QuerydslBinderCustomizer<QMessages> {


    @Override
    default void customize(@NotNull QuerydslBindings bindings, @NotNull QMessages messages){

    }

    Page<Messages> findAllByRoomIdOrderByCreatedAt(Long roomId, Pageable pageable);

}
