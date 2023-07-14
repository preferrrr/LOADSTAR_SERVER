package com.lodestar.lodestar_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@DynamicInsert
@Getter @Setter
@Entity
@Table(name = "board")
public class Board extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    //해시태그는 조회 기능에만 쓰임.
    //delim = '#'으로 구분할 예정
    //그럼 조회할 때 join보다는 성능이 좋을거같음.
    //해시태그로 조회할 때 like '%hashtag%'
    //String hashtag;

    //해시태그가 인스타처럼 새롭게 만들 수 있는게 아니라, 개수가 제한적인데
    //확장성과 조회 성능을 고려하면 해시태그 테이블을 따로 만드는게 나을것 같기도....

    @Column(columnDefinition = "INT default 0")
    private Integer view;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notice> notices = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardHashtag> hashtag = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();



}
