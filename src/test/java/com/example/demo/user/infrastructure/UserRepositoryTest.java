package com.example.demo.user.infrastructure;

import com.example.demo.DemoApplication;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = true)
@ContextConfiguration(classes = DemoApplication.class)
@Sql("/sql/user-repository-test-data.sql")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByIdAndStatus_로_유저_데이터를_찾아올_수_있다(){
        //given
        // when
        Optional<UserEntity> result = userRepository.findByIdAndStatus(1, UserStatus.ACTIVE);

        // then
        assertThat(result.isPresent()).isTrue();

    }

    @Test
    void findByIdAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다(){
        //given
        // when
        Optional<UserEntity> result = userRepository.findByIdAndStatus(1, UserStatus.PENDING);

        // then
        assertThat(result.isEmpty()).isTrue();

    }

    @Test
    void findByEmailAndStatus_로_유저_데이터를_찾아올_수_있다(){
        //given
        // when
        Optional<UserEntity> result = userRepository.findByEmailAndStatus("samryong21@gmail.com", UserStatus.ACTIVE);

        // then
        assertThat(result.isPresent()).isTrue();

    }

    @Test
    void findByEmailAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다(){
        //given
        // when
        Optional<UserEntity> result = userRepository.findByEmailAndStatus("abc@gmail.com", UserStatus.PENDING);

        // then
        assertThat(result.isEmpty()).isTrue();

    }

}
