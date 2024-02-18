package com.lodestar.lodestar_server.career.entity;

import com.lodestar.lodestar_server.career.dto.response.CareerDto;
import com.lodestar.lodestar_server.config.BaseEntity;
import com.lodestar.lodestar_server.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "career")
@DynamicInsert
@NoArgsConstructor
public class Career extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "career_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    private String x;

    private Long y1;
    private Long y2;

    @Column(unique = true)
    private String rangeName;


    public CareerDto createDto() {
        List<Long> y = new ArrayList<>();
        y.add(this.y1);
        y.add(this.y2);

        CareerDto dto = CareerDto.builder()
                .x(this.x)
                .y(y)
                .rangeName(this.rangeName)
                .build();

        return dto;
    }

    @Builder
    public Career(User user, String x, Long y1, Long y2, String rangeName) {
        this.user = user;
        this.x = x;
        this.y1= y1;
        this.y2 = y2;
        this.rangeName = rangeName;
    }


}
