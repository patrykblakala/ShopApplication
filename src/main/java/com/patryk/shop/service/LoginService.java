package com.patryk.shop.service;

import com.patryk.shop.domain.dto.LoginDto;
import com.patryk.shop.domain.dto.LoginResponseDto;

public interface LoginService {
    LoginResponseDto login(LoginDto loginDto);
}
