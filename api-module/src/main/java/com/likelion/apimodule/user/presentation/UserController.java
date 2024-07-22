package com.likelion.apimodule.user.presentation;

import com.likelion.apimodule.user.application.LoginUseCase;
import com.likelion.apimodule.user.dto.KakaoLoginRequest;
import com.likelion.commonmodule.exception.common.ApplicationResponse;
import com.likelion.commonmodule.security.util.AuthConsts;
import com.likelion.coremodule.user.dto.LoginAddResponse;
import com.likelion.coremodule.user.dto.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ApplicationResponse<LoginAddResponse> kakaoLogin(@Valid @RequestBody KakaoLoginRequest kakaoLoginRequest) {
        LoginAddResponse response = loginUseCase.kakaoLogin(kakaoLoginRequest);
        return ApplicationResponse.ok(response);
    }

    @GetMapping("/reissue")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "토큰 재발급 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "토큰 재발급  API", description = "토큰 재발급 API입니다.")
    public ApplicationResponse<LoginResponse> reissue(@RequestHeader(AuthConsts.REFRESH_TOKEN_HEADER) String refreshToken) {
        LoginResponse response = loginUseCase.reissueToken(refreshToken);
        return ApplicationResponse.ok(response);
    }

    @DeleteMapping("/logout")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "로그아웃 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "로그아웃 API", description = "로그아웃 API입니다.")
    public ApplicationResponse<String> logout(@RequestHeader(AuthConsts.REFRESH_TOKEN_HEADER) String refreshToken,
                                              @RequestParam String nickname) {

        loginUseCase.logout(refreshToken, nickname);
        return ApplicationResponse.ok("로그아웃 되었습니다.");
    }
}
