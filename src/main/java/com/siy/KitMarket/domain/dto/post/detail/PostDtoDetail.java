package com.siy.KitMarket.domain.dto.post.detail;

import com.siy.KitMarket.domain.dto.account.AccountDto;
import com.siy.KitMarket.domain.dto.post.ApplicationDto;
import com.siy.KitMarket.domain.entity.post.CarFull;
import com.siy.KitMarket.domain.entity.post.Contest;
import com.siy.KitMarket.domain.entity.post.Post;
import com.siy.KitMarket.domain.entity.post.Study;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static java.time.temporal.ChronoUnit.DAYS;

@Getter
@Setter
@NoArgsConstructor
public class PostDtoDetail {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private Integer deadLine;

    private String createdAt;
    private Integer maxNum;
    private Integer curNum;

    private String category;

    private Set<AccountDto> participants = new HashSet<>();
    private Set<ApplicationDto> applications = new HashSet<>();

    public Integer calDeadLine(LocalDate deadLine){
        LocalDate currentDay = LocalDate.now();

        long between = DAYS.between(currentDay, deadLine);

        return (int)between;
    }

    public String settingCategory(Post post){
        if(post instanceof Study){
            return "study";
        }
        else if( post instanceof CarFull){
            return "carFool";
        }
        else if (post instanceof Contest){
            return "contest";
        }
        return null;
    }

    public PostDtoDetail(Post post, Set<AccountDto> participants, Set<ApplicationDto> applications) {
        this.id = post.getId();
        this.writer = post.getWriter();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.deadLine = calDeadLine(post.getDeadLine());
        this.createdAt = post.getCreatedAt().toString();
        this.maxNum = post.getMaxNumber();
        this.curNum = post.getCurrentNumber();
        this.category = settingCategory(post);

        this.participants = participants;
        this.applications = applications;
    }
}