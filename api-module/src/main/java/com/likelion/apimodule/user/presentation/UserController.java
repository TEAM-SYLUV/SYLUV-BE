package com.likelion.apimodule.user.presentation;

import com.likelion.apimodule.user.application.LoginUseCase;
import com.likelion.apimodule.user.dto.KakaoLoginRequest;
import com.likelion.coremodule.user.dto.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
@Validated
@Tag(name = "User", description = "User 관련 API")
public class UserController {

    private final LoginUseCase loginUseCase;

    @PostMapping("/login/kakao")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "카카오 로그인 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "카카오 로그인 API", description = "카카오 로그인 API입니다.")
    public LoginResponse kakaoLogin(@Valid @RequestBody KakaoLoginRequest kakaoLoginRequest) {
        return loginUseCase.kakaoLogin(kakaoLoginRequest);
    }
}
