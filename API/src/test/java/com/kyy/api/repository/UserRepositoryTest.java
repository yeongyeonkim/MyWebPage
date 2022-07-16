package com.kyy.api.repository;

import com.kyy.api.service.model.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @After
    public void clear() {
        userRepository.deleteAll();
    }

    @Test
    public void saveAndLoad() {
        userRepository.save(User.builder()
                .name("yeongyeon")
                .email("dudtbd111@naver.com")
                .build());
        List<User> userList = userRepository.findAll();
        System.out.println(userList.size());
    }
}
