package com.siy.siyresource.service;
import com.siy.siyresource.domain.condition.PostSearchCondition;
import com.siy.siyresource.domain.entity.Application;
import com.siy.siyresource.domain.entity.post.Post;
import com.siy.siyresource.repository.ApplicationRepositoy.ApplicationRepository;
import com.siy.siyresource.repository.PostRepository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final PostRepository postRepository;

    public Application findByUsernameAndPostId(String username, Long postId){
        return applicationRepository.findByUserName(username, postId);
    }

    public Application findById(Long Id){
        Application findApp = applicationRepository.findByAppId(Id);
        return findApp;
    }


    @Transactional
    public Long save(Application application){
        Application save = applicationRepository.save(application);

        return save.getId();
    }


    @Transactional
    public void delete(Application application){
        applicationRepository.delete(application);
    }

    @Transactional
    public void deleteById(Long id){
        applicationRepository.deleteById(id);
    }

    @Transactional
    public void updateApp(Application findApp, String request) {
        findApp.setContent(request);
    }

    @Transactional
    public void deleteByPostIdAndUserName(Long id, String username){
        Application findApp = applicationRepository.findByUserName(username, id);
        System.out.println("findApp = " + findApp);

        applicationRepository.delete(findApp);
    }

    public String permitApp(Long appId, String hostName) {
        Application findApp = findById(appId);
        Post findPost = findApp.getPost();

        findPost.plusParticipants(findApp.getUsername());
        findPost.plusCurrentNumber();
        deleteById(appId);

        return "redirect/";
    }
}
