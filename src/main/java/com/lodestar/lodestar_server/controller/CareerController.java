package com.lodestar.lodestar_server.controller;

import com.lodestar.lodestar_server.dto.CareerDtos;
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

    @PostMapping("")
    public ResponseEntity<?> saveCareer(@AuthenticationPrincipal User user, @RequestBody CareerDtos careerRequestDto) {

        careerService.saveCareer(user, careerRequestDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getCareer(@AuthenticationPrincipal User user) {

        CareerDtos dtos = careerService.getCareer(user);

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}
