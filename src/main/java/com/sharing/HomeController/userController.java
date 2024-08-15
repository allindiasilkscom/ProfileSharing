package com.sharing.HomeController;

import com.sharing.Service.UserService;
import com.sharing.model.User;

import com.sharing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

public class userController {
    @Autowired
    private UserService userService;

    @GetMapping("/api/user/profile")
    public User findUserByJwt(@RequestHeader("Authorization") String jwt) throws Exception {

        User users =  userService.finduserByJwt(jwt);
        return users;

    }





}
