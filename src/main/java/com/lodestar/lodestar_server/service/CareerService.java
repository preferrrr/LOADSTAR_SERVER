package com.lodestar.lodestar_server.service;

import com.lodestar.lodestar_server.dto.response.CareerDto;
import com.lodestar.lodestar_server.dto.response.CareerDtos;
import com.lodestar.lodestar_server.entity.Career;
import com.lodestar.lodestar_server.entity.User;
import com.lodestar.lodestar_server.exception.DuplicateCareerException;
import com.lodestar.lodestar_server.repository.CareerRepository;
import com.lodestar.lodestar_server.repository.CareerRepositoryJdbc;
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
    private final CareerRepositoryJdbc careerRepositoryJdbc;

    public void saveCareer(User user, CareerDtos careerRequestDto) {

        if(careerRepository.existsByUser(user))
            throw new DuplicateCareerException("userId : " + user.getId());

//        List<Career> careerList = new ArrayList<>();
//
//        for(CareerDto dto : careerRequestDto.getArr()) {
//            Career career = Career.builder()
//                    .user(user)
//                    .x(dto.getX())
//                    .y1(dto.getY().get(0))
//                    .y2(dto.getY().get(1))
//                    .rangeName(dto.getRangeName())
//                    .build();
//            careerList.add(career);
//        }
//
//        careerRepository.saveAll(careerList);
        careerRepositoryJdbc.saveCareers(user.getId(), careerRequestDto.getArr());

    }

    @Transactional(readOnly = true)
    public List<CareerDto> getCareer(User user) {

        List<CareerDto> careerDtos = new ArrayList<>();

        List<Career> careers = careerRepository.findCareersByUser(user);

        for(Career career: careers) {
            careerDtos.add(career.createDto());
        }

        return careerDtos;

    }


    public void modifyCareer(User user, CareerDtos careerDtos) {
        //TODO : 삭제할 커리어 id와 추가할 커리어 dto를 따로 받아서 처리하도록. 그러면 내가 커리어를 줄 때 id까지 같이 줘야함.
        List<Career> careers = careerRepository.findCareersByUser(user); // 원래 저장되어 있던 것들

        List<String> rangeNames1 = new ArrayList<>();
        for(Career career : careers) {
            rangeNames1.add(career.getRangeName()); // 추가된 커리어를 저장햐기 위함
        }

        List<CareerDto> addCareers = new ArrayList<>();

        for(CareerDto careerDto : careerDtos.getArr()) { //추가된 커리어를 저장하기 위함
            if(!rangeNames1.contains(careerDto.getRangeName())) {
                addCareers.add(careerDto);
            }
        }

        List<String> rangeName2 = new ArrayList<>();

        for(CareerDto careerDto : careerDtos.getArr()) {
            rangeName2.add(careerDto.getRangeName());
        }
        List<Career> deleteCareers = new ArrayList<>();

        for(Career career : careers) {
            if (!rangeName2.contains(career.getRangeName()))
                deleteCareers.add(career);
        }

        careerRepositoryJdbc.saveCareers(user.getId(), addCareers);
        careerRepository.deleteAllInBatch(deleteCareers);

    }
}
