package com.lodestar.lodestar_server.service;

import com.lodestar.lodestar_server.dto.CareerDto;
import com.lodestar.lodestar_server.dto.CareerDtos;
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

    public void saveCareer(User user, CareerDtos careerRequestDto) {

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

    public CareerDtos getCareer(User user) {

        CareerDtos dtos = new CareerDtos();
        dtos.setArr(new ArrayList<>());

        List<Career> careers = careerRepository.findCareersByUser(user);

        for(Career career: careers) {
            CareerDto dto = new CareerDto();

            dto.setX(career.getX());

            List<Long> ys = new ArrayList<>();
            ys.add(career.getY1());
            ys.add(career.getY2());
            dto.setY(ys);

            dto.setRangeName(career.getRangeName());
            dtos.getArr().add(dto);
        }

        return dtos;

    }
}
