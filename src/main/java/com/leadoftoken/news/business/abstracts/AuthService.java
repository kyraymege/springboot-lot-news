package com.leadoftoken.news.business.abstracts;

import com.leadoftoken.news.entities.dtos.LoginDto;
import com.leadoftoken.news.entities.dtos.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
