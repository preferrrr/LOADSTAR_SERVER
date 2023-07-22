package com.lodestar.lodestar_server.controller;

import com.lodestar.lodestar_server.dto.response.CareerDtos;
import com.lodestar.lodestar_server.entity.User;
import com.lodestar.lodestar_server.service.CareerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/careers")
public class CareerController {
    private final CareerService careerService;

    /**
     * 커리어 등록(그래프 그리기)
     * /careers
     * */
    @PostMapping("")
    public ResponseEntity<?> saveCareer(@AuthenticationPrincipal User user,
                                        @RequestBody CareerDtos careerDtos) {

        careerService.saveCareer(user, careerDtos);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 커리어 조회 (그래프조회)
     * /careers
     * */
    @GetMapping("")
    public ResponseEntity<?> getCareer(@AuthenticationPrincipal User user) {

        CareerDtos dtos = new CareerDtos();
        dtos.setArr(careerService.getCareer(user));

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    /**
     * 커리어 수정 (그래프 수정)
     * /careers
     * */
    @PatchMapping("")
    public ResponseEntity<?> modifyCareer(@AuthenticationPrincipal User user,
                                          @RequestBody CareerDtos careerDtos) {

        careerService.modifyCareer(user, careerDtos);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
