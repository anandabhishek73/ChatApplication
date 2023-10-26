package com.anand.abhishek.chat.service;

import com.anand.abhishek.chat.db.entity.ChatUser;
import com.anand.abhishek.chat.db.repo.ChatUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private ChatUserRepository userRepository;

    @Override
    public ChatUser createUser(ChatUser chatUser) {
        return userRepository.save(chatUser);
    }

    @Override
    public ChatUser getUserById(Long userId) {
        Optional<ChatUser> optionalUser = userRepository.findById(userId);
        return optionalUser.get();
    }

    @Override
    public List<ChatUser> getAllUsers() {
        return (List<ChatUser>) userRepository.findAll();
    }

    @Override
    public ChatUser updateUser(ChatUser chatUser) {
        ChatUser existingUser = userRepository.findById(chatUser.getId()).get();
        existingUser.setFirstName(chatUser.getFirstName());
        existingUser.setLastName(chatUser.getLastName());
        ChatUser updatedUser = userRepository.save(existingUser);
        return updatedUser;
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}