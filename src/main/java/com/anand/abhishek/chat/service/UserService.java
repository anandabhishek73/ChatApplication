package com.anand.abhishek.chat.service;

import com.anand.abhishek.chat.db.entity.ChatUser;

import java.util.List;

public interface UserService {
    ChatUser createUser(ChatUser chatUser);

    ChatUser getUserById(Long userId);

    List<ChatUser> getAllUsers();

    ChatUser updateUser(ChatUser chatUser);

    void deleteUser(Long userId);
}
