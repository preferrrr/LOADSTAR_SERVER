package com.lodestar.lodestar_server.career.service;

import com.lodestar.lodestar_server.career.dto.request.SaveCareerRequestDto;
import com.lodestar.lodestar_server.career.dto.response.CareerDto;
import com.lodestar.lodestar_server.career.dto.response.CareerListDto;
import com.lodestar.lodestar_server.career.dto.response.GetMyCareersResponseDto;
import com.lodestar.lodestar_server.career.entity.Career;
import com.lodestar.lodestar_server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CareerService {

    private final CareerServiceSupport careerServiceSupport;

    @Transactional(readOnly = false)
    public void saveCareer(User user, SaveCareerRequestDto saveCareerRequestDto) {

        //이미 저장해둔 커리어가 있는지 확인
        careerServiceSupport.checkExistsCareerForSave(user);

        //커리어 저장
        careerServiceSupport.saveCareers(user, saveCareerRequestDto.toEntities(user));
    }

    @Transactional(readOnly = true)
    public GetMyCareersResponseDto getMyCareers(User user) {

        List<Career> careers = careerServiceSupport.getCareerByUser(user);

        return GetMyCareersResponseDto.of(careers);
    }


    public void modifyCareer(User user, CareerListDto careerListDto) {

        List<Career> careers = careerServiceSupport.getCareerByUser(user);

        List<String> rangeNames1 = new ArrayList<>();
        for(Career career : careers) {
            rangeNames1.add(career.getRangeName()); // 추가된 커리어를 저장햐기 위함
        }

        List<CareerDto> addCareers = new ArrayList<>();

        for(CareerDto careerDto : careerListDto.getArr()) { //추가된 커리어를 저장하기 위함
            if(!rangeNames1.contains(careerDto.getRangeName())) {
                addCareers.add(careerDto);
            }
        }

        List<String> rangeName2 = new ArrayList<>();

        for(CareerDto careerDto : careerListDto.getArr()) {
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
