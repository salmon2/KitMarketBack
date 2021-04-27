package com.siy.siyresource.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.siy.siyresource.domain.entity.account.Account;
import com.siy.siyresource.domain.entity.post.Post;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(of = {"content"})
@Table(name = "APPLICATION")
@EntityListeners(AuditingEntityListener.class)
public class Application {
    @Id
    @GeneratedValue
    @Column(name = "application_id")
    private Long id;

    private String content;

    @CreatedDate
    private LocalDateTime chatDate; //채팅 시간

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;


    public Application(String content, Post post) {
        this.content = content;
        this.post = post;
        post.getApplications().add(this);
    }


    // 작성자 선택할수 있게
    // 신청!! 이름 내용 신청(bool)enum "TRUE", "FALSE"


}
