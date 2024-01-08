package com.example.demo.post.service;

import com.example.demo.post.domain.PostCreate;
import com.example.demo.post.domain.PostUpdate;
import com.example.demo.post.infrastructure.PostEntity;
import com.example.demo.post.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@SqlGroup({
    @Sql(value = "/sql/post-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class PostServiceTest {

    @Autowired
    private PostService postService;


    @Test
    void getById는_존재하는_게시물을_내려준다(){
        //given
        //when
        PostEntity result = postService.getById(1);
        //then
        assertThat(result.getContent()).isEqualTo("helloworld");
        assertThat(result.getWriter().getEmail()).isEqualTo("samryong21@gmail.com");
    }


    @Test
    void postCreateDto_를_이용하여_유저를_생설할_수_있다(){
        //given
        PostCreate postCreate = PostCreate.builder()
                .content("foobar")
                .writerId(1)
                .build();

        //when
        PostEntity result = postService.create(postCreate);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getContent()).isEqualTo("foobar");
        assertThat(result.getCreatedAt()).isGreaterThan(0);
    }

    @Test
    void postCreateDto_를_이용하여_유저를_수정할_수_있다(){
        //given
        PostUpdate postUpdate = PostUpdate
                .builder()
                .content("hello world :)")
                .build();

        //when
        postService.update(1, postUpdate);

        //then
        PostEntity postEntity = postService.getById(1);
        assertThat(postEntity.getId()).isNotNull();
        assertThat(postEntity.getContent()).isEqualTo("hello world :)");
        assertThat(postEntity.getModifiedAt()).isGreaterThan(0);
    }

}
