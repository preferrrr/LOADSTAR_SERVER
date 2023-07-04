package com.lodestar.lodestar_server.service;

import com.lodestar.lodestar_server.dto.CareerDto;
import com.lodestar.lodestar_server.dto.CreateCareerRequestDto;
import com.lodestar.lodestar_server.entity.Career;
import com.lodestar.lodestar_server.entity.User;
import com.lodestar.lodestar_server.repository.CareerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CareerService {

    private final CareerRepository careerRepository;

    public void saveCareer(User user, CreateCareerRequestDto careerRequestDto) {

        List<Career> careerList = new ArrayList<>();

        for(CareerDto dto : careerRequestDto.getArr()) {
            Career career = new Career();
            career.setUser(user);
            career.setX(dto.getX());
            career.setY1(dto.getY().get(0));
            career.setY2(dto.getY().get(1));
            career.setRangeName(dto.getRangeName());

            careerList.add(career);
        }

        careerRepository.saveAll(careerList);

    }
}
