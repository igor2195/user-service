//package ru.test_app.user_service.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import ru.test_app.user_service.model.LoginRequestDto;
//import ru.test_app.user_service.model.LoginResponseDto;
//import ru.test_app.user_service.security.JwtService;
//
//@RestController
//@RequestMapping("/auth")
//@RequiredArgsConstructor
//public class AuthController {
//
//    private final AuthenticationManager authenticationManager;
//    private final JwtService jwtService;
//
//    @PostMapping("/login")
//    public LoginResponseDto login(@RequestBody LoginRequestDto request) {
//
//        Authentication auth = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getUsername(),
//                        request.getPassword()
//                )
//        );
//
//        UserDetails user = (UserDetails) auth.getPrincipal();
//
//        String token = jwtService.generateToken(user);
//
//        return new LoginResponseDto(token);
//    }
//}
