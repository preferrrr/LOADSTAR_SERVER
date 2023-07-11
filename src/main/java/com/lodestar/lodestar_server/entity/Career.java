package com.lodestar.lodestar_server.entity;

import com.lodestar.lodestar_server.dto.CareerDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "career")
@DynamicInsert
public class Career extends BaseEntity{

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
        CareerDto dto = new CareerDto();
        dto.setX(this.x);
        List<Long> y = new ArrayList<>();
        y.add(this.y1);
        y.add(this.y2);
        dto.setY(y);
        dto.setRangeName(this.rangeName);

        return dto;
    }

    public static Career createCareer(User user, CareerDto careerDto) {
        Career career = new Career();
        career.user = user;
        career.x = careerDto.getX();
        career.y1= careerDto.getY().get(0);
        career.y2 = careerDto.getY().get(1);
        career.rangeName = careerDto.getRangeName();

        return career;
    }


}
