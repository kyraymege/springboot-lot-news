package com.leadoftoken.news.business.concretes;

import com.leadoftoken.news.business.abstracts.AuthService;
import com.leadoftoken.news.entities.concretes.Role;
import com.leadoftoken.news.entities.concretes.User;
import com.leadoftoken.news.entities.dtos.LoginDto;
import com.leadoftoken.news.entities.dtos.RegisterDto;
import com.leadoftoken.news.exceptions.NewsApiException;
import com.leadoftoken.news.repository.RoleRepository;
import com.leadoftoken.news.repository.UserRepository;
import com.leadoftoken.news.security.jwt.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthManager implements AuthService {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider provider;

    public AuthManager(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider provider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.provider = provider;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = provider.generateToken(authentication);

        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {
        //check username
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new NewsApiException(HttpStatus.BAD_REQUEST, "Username is already taken!");
        }
        //check email
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new NewsApiException(HttpStatus.BAD_REQUEST, "Email is already taken!");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully!";
    }
}
