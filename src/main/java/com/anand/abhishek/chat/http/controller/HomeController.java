package com.anand.abhishek.chat.http.controller;

import com.anand.abhishek.chat.http.model.ApiResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping(
            path = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse getHomePage(
    ) {
        log.debug("Got a request on home controller");
        return new ApiResponse("success");
    }
}
