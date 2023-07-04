package com.lodestar.lodestar_server.controller;

import com.lodestar.lodestar_server.dto.CreateCareerRequestDto;
import com.lodestar.lodestar_server.entity.User;
import com.lodestar.lodestar_server.service.CareerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/careers")
public class CareerController {
    private final CareerService careerService;

    @PostMapping("")
    public ResponseEntity<?> saveCareer(@AuthenticationPrincipal User user, @RequestBody CreateCareerRequestDto careerRequestDto) {

        careerService.saveCareer(user, careerRequestDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
