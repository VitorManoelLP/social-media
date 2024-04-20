package com.socialmedia.main.infra.resource;

import com.socialmedia.main.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserResource {


    @GetMapping
    public ResponseEntity<Page<User>> findAllUsers(@RequestParam(required = false, defaultValue = "") String search, Pageable pageable) {
        return ResponseEntity.ok().build();
    }


}
