package com.lodestar.lodestar_server.career.service;

import com.lodestar.lodestar_server.career.dto.response.CareerDto;
import com.lodestar.lodestar_server.career.entity.Career;
import com.lodestar.lodestar_server.career.repository.CareerRepository;
import com.lodestar.lodestar_server.career.exception.DuplicateCareerException;
import com.lodestar.lodestar_server.career.repository.CareerRepositoryJdbc;
import com.lodestar.lodestar_server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CareerServiceSupport {

    private final CareerRepository careerRepository;
    private final CareerRepositoryJdbc careerRepositoryJdbc;

    @Transactional(readOnly = true)
    public List<CareerDto> getCareerDtoList(User user) {

        List<Career> careers = careerRepository.findCareersByUser(user);

        return careers.stream()
                .map(career -> career.createDto())
                .collect(Collectors.toList());
    }

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
}
