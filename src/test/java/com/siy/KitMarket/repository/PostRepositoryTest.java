package com.siy.KitMarket.repository;

import com.siy.KitMarket.domain.entity.Application;
import com.siy.KitMarket.domain.entity.post.CarFull;
import com.siy.KitMarket.domain.entity.post.Post;
import com.siy.KitMarket.domain.entity.post.Study;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Commit
class PostRepositoryTest {
    @Autowired
    EntityManager em;

    @Autowired
    PostRepository postRepository;

    @Autowired
    QPostRepository qPostRepository;



    Study post1 = new Study("Study1", "I'm Study1", "study1111");
    CarFull post2 = new CarFull("CarFull1", "I'm CarFull1", "carfull");
    Study post3 = new Study("Study2", "I'm Study2", "study22222");
    CarFull post4 = new CarFull("CarFull2", "I'm CarFull2", "carfull");

    Application application1 = new Application("댓글 1입니다.", post1);
    Application application2 = new Application("댓글 2입니다.", post2);
    Application application3 = new Application("댓글 3입니다.", post3);
    Application application4 = new Application("댓글 4입니다.", post4);
    Application application5 = new Application("댓글 5입니다.", post1);

    @BeforeEach
    public void before(){
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        postRepository.save(post4);


        em.flush();
        em.clear();
    }

    /**
     * Repository save Test
     */
    @Test
    public void saveStudyTest(){
        postRepository.save(post1);
        Study findStudy = (Study)postRepository.findById(post1.getId()).get();
        assertThat(findStudy.getStudy()).isEqualTo(post1.getStudy());
        System.out.println("study = " + findStudy);
    }

    /**
     * Repository select Test
     */
    @Test
    public void selectStudyTest(){
        Study findPost = (Study)postRepository.findById(post1.getId()).get();
        assertThat(findPost.getStudy()).isEqualTo(post1.getStudy());

        System.out.println("findPost = " + findPost);
    }

    /**
     * Repository update Test
     */
    @Test
    public void updateStudyTest(){
        Study findStudy = (Study)postRepository.findById(post1.getId()).get();
        assertThat(findStudy.getStudy()).isEqualTo(post1.getStudy());
        System.out.println("study = " + findStudy);

        findStudy.setStudy("new Study !!!");
    }

    /**
     * Repository delect test
     */
    @Test
    public void deleteStudyTest(){
        Post findStudy = postRepository.findById(post1.getId()).get();
        postRepository.delete(findStudy);
    }

    /**
     * 포스트 1개 application 여러개 확인
     */
    @Test
    public void findPostWithAppById(){
        Post result = qPostRepository.findPostWithAppById(post1.getId());
        System.out.println("result = " + result);
        for (Application application : result.getApplications()) {
            System.out.println("application = " + application);
        }
    }

}