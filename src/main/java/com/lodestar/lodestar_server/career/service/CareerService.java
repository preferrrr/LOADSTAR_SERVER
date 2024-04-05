package com.lodestar.lodestar_server.career.service;

import com.lodestar.lodestar_server.career.dto.request.ModifyCareerRequestDto;
import com.lodestar.lodestar_server.career.dto.request.SaveCareerRequestDto;
import com.lodestar.lodestar_server.career.dto.response.GetMyCareersResponseDto;
import com.lodestar.lodestar_server.career.entity.Career;
import com.lodestar.lodestar_server.common.util.CurrentUserGetter;
import com.lodestar.lodestar_server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CareerService {

    private final CareerServiceSupport careerServiceSupport;
    private final CurrentUserGetter currentUserGetter;

    @Transactional(readOnly = false)
    public void saveCareer(SaveCareerRequestDto saveCareerRequestDto) {

        User user = currentUserGetter.getCurrentUser();

        //이미 저장해둔 커리어가 있는지 확인
        careerServiceSupport.checkExistsCareerForSave(user);

        //커리어 저장
        careerServiceSupport.saveCareers(user, saveCareerRequestDto.toEntities(user));
    }

    public GetMyCareersResponseDto getMyCareers() {

        User user = currentUserGetter.getCurrentUser();

        return GetMyCareersResponseDto.of(
                careerServiceSupport.getCareerByUser(user)
        );
    }


    @Transactional(readOnly = false)
    public void modifyCareer(ModifyCareerRequestDto modifyCareerRequestDto) {

        User user = currentUserGetter.getCurrentUser();

        //삭제할 커리어 조회
        List<Career> deleteCareers = careerServiceSupport.getCareersByIds(modifyCareerRequestDto.getDeleteCareers());

        //자신의 커리어가 맞는지 확인
        careerServiceSupport.checkIsOwnCareers(deleteCareers, user);

        //커리어 삭제
        careerServiceSupport.deleteCareers(deleteCareers);

        //추가할 커리어 저장
        careerServiceSupport.saveCareers(user, modifyCareerRequestDto.newCareerToEntity(user));
    }
}
