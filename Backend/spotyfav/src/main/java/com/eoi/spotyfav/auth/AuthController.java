package com.eoi.spotyfav.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.eoi.spotyfav.auth.dto.TokenResponseDTO;
import com.eoi.spotyfav.usuarios.User;
import com.eoi.spotyfav.usuarios.UsuariosService;
import com.eoi.spotyfav.usuarios.dto.RespuestaUsuarioDTO;
import com.eoi.spotyfav.usuarios.dto.UsuarioDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.eoi.spotyfav.auth.dto.LoginDTO;
import java.security.NoSuchAlgorithmException;
import java.util.Date;


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
}
