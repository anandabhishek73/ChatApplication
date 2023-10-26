package com.anand.abhishek.chat.db.repo;

import com.anand.abhishek.chat.db.entity.ChatUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatUserRepository extends CrudRepository<ChatUser, Long> {

    List<ChatUser> findByLastName(String lastName);

    ChatUser findById(long id);
}