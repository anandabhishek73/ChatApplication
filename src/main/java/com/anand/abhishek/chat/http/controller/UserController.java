package com.anand.abhishek.chat.http.controller;

import com.anand.abhishek.chat.db.entity.ChatUser;
import com.anand.abhishek.chat.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    // build create User REST API
    @PostMapping
    public ResponseEntity<ChatUser> createUser(@RequestBody ChatUser user){
        ChatUser savedUser = userService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // build get user by id REST API
    // http://localhost:8080/user/1
    @GetMapping("{id}")
    public ResponseEntity<ChatUser> getUserById(@PathVariable("id") Long userId){
        ChatUser user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Build Get All Users REST API
    // http://localhost:8080/user
    @GetMapping
    public ResponseEntity<List<ChatUser>> getAllUsers(){
        List<ChatUser> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Build Update User REST API
    @PutMapping("{id}")
    // http://localhost:8080/user/1
    public ResponseEntity<ChatUser> updateUser(@PathVariable("id") Long userId,
                                           @RequestBody ChatUser user){
        user.setId(userId);
        ChatUser updatedUser = userService.updateUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // Build Delete User REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
    }
}
