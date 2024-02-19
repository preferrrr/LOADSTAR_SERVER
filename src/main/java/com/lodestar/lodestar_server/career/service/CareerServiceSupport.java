package com.lodestar.lodestar_server.career.service;

import com.lodestar.lodestar_server.career.entity.Career;
import com.lodestar.lodestar_server.career.exception.UnauthorizedDeleteCareerException;
import com.lodestar.lodestar_server.career.repository.CareerRepository;
import com.lodestar.lodestar_server.career.exception.DuplicateCareerException;
import com.lodestar.lodestar_server.career.repository.CareerRepositoryJdbc;
import com.lodestar.lodestar_server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CareerServiceSupport {

    private final CareerRepository careerRepository;
    private final CareerRepositoryJdbc careerRepositoryJdbc;

    public void checkExistsCareerForSave(User user) {
        if (careerRepository.existsByUser(user))
            throw new DuplicateCareerException();
    }

    public void saveCareers(User user, List<Career> careers) {
        careerRepositoryJdbc.saveCareers(user.getId(), careers);
    }

    public List<Career> getCareerByUser(User user) {
        return careerRepository.findCareersByUser(user);
    }

    public List<Career> getCareersByIds(List<Long> ids) {
        return careerRepository.findAllById(ids);
    }

    public void checkIsOwnCareers(List<Career> deleteCareers, User user) {
        if(deleteCareers.stream()
                .anyMatch(career -> !career.getUser().getId().equals(user.getId())))
            throw new UnauthorizedDeleteCareerException();
    }

    public void deleteCareers(List<Career> deleteCareers) {
        careerRepository.deleteAllInBatch(deleteCareers);
    }
}
