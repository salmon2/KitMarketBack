package com.siy.KitMarket.repository;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.siy.KitMarket.domain.dto.post.PostDto;
import com.siy.KitMarket.domain.dto.post.QPostDto;
import com.siy.KitMarket.domain.dto.post.StudyDto;
import com.siy.KitMarket.domain.entity.Application;
import com.siy.KitMarket.domain.entity.post.CarFull;
import com.siy.KitMarket.domain.entity.post.Post;
import com.siy.KitMarket.domain.entity.post.Study;
import com.siy.KitMarket.repository.PostRepository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static com.siy.KitMarket.domain.entity.post.QPost.post;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PostRepositoryTest {
    @Autowired
    EntityManager em;

    @Autowired
    PostRepository postRepository;

    @Autowired
    JPAQueryFactory queryFactory;

    @Autowired
    ObjectMapper mapper;


    Study post1 = new Study("Study1", "I'm Study1", "study1111");
    CarFull post2 = new CarFull("CarFull1", "I'm CarFull1", "carfull");
    Study post3 = new Study("Study2", "I'm Study2", "study22222");
    CarFull post4 = new CarFull("CarFull2", "I'm CarFull2", "carfull");

    Application application1 = new Application("댓글 1입니다.", post1);
    Application application2 = new Application("댓글 2입니다.", post2);
    Application application3 = new Application("댓글 3입니다.", post3);
    Application application4 = new Application("댓글 4입니다.", post4);
    Application application5 = new Application("댓글 5입니다.", post1);


    /**
     * Repository save Test
     */
    @Test
    public void saveStudyTest(){
        Post post = postRepository.saveAndFlush(post1);
        Study findStudy = postRepository.findById(post.getId());
        assertThat(findStudy.getCategory()).isEqualTo(post1.getCategory());
        System.out.println("study = " + findStudy);
    }

    /**
     * Repository select Test
     */
    @Test
    public void selectStudyTest(){
        Study findPost = (Study)postRepository.findById(post1.getId());
        assertThat(findPost.getCategory()).isEqualTo(post1.getCategory());

        System.out.println("findPost = " + findPost);
    }

    /**
     * Repository update Test
     */
    @Test
    public void updateStudyTest(){
        Study findStudy = (Study)postRepository.findById(post1.getId());
        assertThat(findStudy.getCategory()).isEqualTo(post1.getCategory());
        System.out.println("study = " + findStudy);

        findStudy.setCategory("new Study !!!");
    }

    /**
     * Repository delect test
     */
    @Test
    public void deleteStudyTest(){
        Post findStudy = postRepository.findById(post1.getId());
        postRepository.delete(findStudy);
    }


    /**
     * 포스트 1개 application 여러개 확인
     */
//    @Test
//    public void findPostWithAppById(){
//        Post result = postRepository.findPostWithAppById(post1.getId());
//        System.out.println("result = " + result);
//        for (Application application : result.getApplications()) {
//            System.out.println("application = " + application);
//        }
//    }

    @Test
    public void searchPageTest(){
        PageRequest page = PageRequest.of(0, 50);
        Page<PostDto> postList = postRepository.findPostListWithPaging(page);

        System.out.println("postList.getSize() = " + postList.getSize());
        System.out.println("postList = " + postList.getTotalPages());
        System.out.println("postList.getContent() = " + postList.getContent());
    }
    @Test
    public void findStudyListWithPagingTest(){
        PageRequest page = PageRequest.of(0, 10);
        Page<StudyDto> studyList = postRepository.findStudyListWithPaging(page);

        System.out.println("postList.getSize() = " + studyList.getSize());
        System.out.println("postList = " + studyList.getTotalPages());
        System.out.println("postList.getContent() = " + studyList.getContent());
    }


    @Test
    void findAllPostList() throws JsonProcessingException {
        PageRequest page = PageRequest.of(0, 8);

        List<PostDto> content = queryFactory
                .select(new QPostDto(
                        post.id.as("id"),
                        post.writer,
                        post.title,
                        post.content,
                        post.createdAt,
                        post.maxNumber,
                        post.currentNumber,
                        post.deadLine,
                        post.category
                ))
                .from(post)
                .offset(page.getOffset())
                .limit(page.getPageSize())
                .fetch();

        JPAQuery<Post> countQuery = queryFactory
                .selectFrom(post);

        Page<PostDto> result = PageableExecutionUtils.getPage(content, page, countQuery::fetchCount);

        System.out.println(mapper.writeValueAsString(result));
    }

}