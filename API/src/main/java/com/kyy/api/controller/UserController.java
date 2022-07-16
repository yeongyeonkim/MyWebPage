package com.kyy.api.controller;

import com.kyy.api.repository.UserRepository;
import com.kyy.api.service.model.User;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_OK;

@Api
//@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository; // final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션

    @ApiOperation(value = "유저 리스트 조회")
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_OK, message = "200 ok"),
            @ApiResponse(code = HTTP_INTERNAL_ERROR, message = "500 internal error")
    })
    @GetMapping(value = "/user")
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @ApiOperation(value = "유저 생성")
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_OK, message = "200 ok"),
            @ApiResponse(code = HTTP_INTERNAL_ERROR, message = "500 internal error")
    })
    @PostMapping(value = "/user")
    public User save(
            @ApiParam(value = "회원 이름", required = true) @RequestParam String name,
            @ApiParam(value = "회원 이메일", required = true) @RequestParam String email
    ) {
        User user = User.builder()
                .email(email)
                .name(name)
                .build();
        return userRepository.save(user);
    }
}
