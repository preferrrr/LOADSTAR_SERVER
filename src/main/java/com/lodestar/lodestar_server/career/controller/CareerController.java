package com.lodestar.lodestar_server.career.controller;

import com.lodestar.lodestar_server.career.dto.request.ModifyCareerRequestDto;
import com.lodestar.lodestar_server.career.dto.request.SaveCareerRequestDto;
import com.lodestar.lodestar_server.career.dto.response.CareerListDto;
import com.lodestar.lodestar_server.career.dto.response.GetMyCareersResponseDto;
import com.lodestar.lodestar_server.user.entity.User;
import com.lodestar.lodestar_server.career.service.CareerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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
     */
    @PostMapping("")
    @Operation(summary = "커리어 등록 (그래프 그리기)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "body null 존재"),
    })
    public ResponseEntity<HttpStatus> saveCareer(@AuthenticationPrincipal User user,
                                                 @RequestBody @Valid SaveCareerRequestDto saveCareerRequestDto) {

        careerService.saveCareer(user, saveCareerRequestDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 나의 커리어 조회 (그래프조회)
     * /careers
     */
    @GetMapping("")
    @Operation(summary = "커리어 조회 (그래프 조회)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<GetMyCareersResponseDto> getMyCareers(@AuthenticationPrincipal User user) {

        return new ResponseEntity<>(careerService.getMyCareers(user), HttpStatus.OK);
    }

    /**
     * 커리어 수정 (그래프 수정)
     * /careers
     */
    @PatchMapping("")
    @Operation(summary = "커리어 수정 (그래프 수정)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "body null 존재"),
    })
    public ResponseEntity<HttpStatus> modifyCareer(@AuthenticationPrincipal User user,
                                                   @RequestBody ModifyCareerRequestDto modifyCareerRequestDto) {

        careerService.modifyCareer(user, modifyCareerRequestDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
