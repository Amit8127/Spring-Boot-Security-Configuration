package com.driver.SpringSecurity.controllers;

import com.driver.SpringSecurity.models.UserInfo;
import com.driver.SpringSecurity.repositories.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('NORMAL')") // TO SET WHO CAN ACCESS
    @GetMapping("/normal")
    public ResponseEntity<String> normalUser() {
        return new ResponseEntity<>("Yess, I am normal User", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')") // TO SET WHO CAN ACCESS
    @GetMapping("/admin")
    public ResponseEntity<String> adminUser() {
        return new ResponseEntity<>("Yess, I am admin User", HttpStatus.OK);
    }

    @GetMapping("/public")
    public ResponseEntity<String> publicUser() {
        return new ResponseEntity<>("Yess, I am public User", HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody UserInfo userInfo)  {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));

        userInfoRepository.save(userInfo);

        return new ResponseEntity<>("User has been saved", HttpStatus.CREATED);
    }
}
