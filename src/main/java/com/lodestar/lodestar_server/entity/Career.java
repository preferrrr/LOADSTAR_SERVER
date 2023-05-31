package com.lodestar.lodestar_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Entity
@Getter @Setter
@Table(name = "career")
public class Career extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "career_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //enum 타입으로 한다면 추후에 질문을 추가할 때 확장성 측면에서 문제가 됨.
    //@Enumerated(EnumType.STRING)
    //Kind kind;
    private String kind;
    //PROGRAMMING, CS, ALGORITHM, PROJECT, BOOTCAMP,
    //ACTIVITY, LICENSE, LANGUAGE



    @Column(name = "kind_number")
    private Integer kindNumber;

    // 공부했던것은 시작일과 종료일을 년과 월만 받을거니까, 0000-00 문자열로 받아서
    // Service에서 비즈니스 로직으로 처리해줌.
    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @ColumnDefault("n")
    private String highlight;

    @OneToOne(mappedBy = "career")
    private Explain explain;

    @OneToOne(mappedBy = "career")
    private Select select;



}
