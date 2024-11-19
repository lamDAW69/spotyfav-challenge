package com.example.eventos.auth;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.eventos.auth.dto.LoginDTO;
import com.example.eventos.auth.dto.TokenResponseDTO;
import com.example.eventos.usuarios.User;
import com.example.eventos.usuarios.UsuariosService;
import com.example.eventos.usuarios.dto.RespuestaUsuarioDTO;
import com.example.eventos.usuarios.dto.UsuarioDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuariosService usuariosService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @PostMapping("login")
    public TokenResponseDTO login(@RequestBody @Valid LoginDTO loginDTO) throws NoSuchAlgorithmException {
        User user = usuariosService.login(loginDTO);
        return new TokenResponseDTO(getToken(user));
    }

    @PostMapping("registro")
    @ResponseStatus(HttpStatus.CREATED)
    public RespuestaUsuarioDTO registro(@RequestBody @Valid UsuarioDTO u) throws NoSuchAlgorithmException {       
        return new RespuestaUsuarioDTO(usuariosService.insert(u));
    }

    private String getToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        String token = JWT.create()
                .withIssuer("arturober")
                .withClaim("id", user.getId())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000))) // Caduca en una semana
                .sign(algorithm);
        return token;
    }


    public Integer getUserIdFromToken(String token) {

        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("id").asInt();
    }

}
