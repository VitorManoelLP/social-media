package com.socialmedia.main.application.resource;

import com.socialmedia.main.application.service.SearchUser;
import com.socialmedia.main.domain.payload.UsersFound;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserResource {

    private final SearchUser searchUser;

    @GetMapping
    public ResponseEntity<Page<UsersFound>> findAllUsers(@RequestParam(required = false, defaultValue = "") String search, Pageable pageable) {
        return ResponseEntity.ok(searchUser.search(search, pageable));
    }


}
