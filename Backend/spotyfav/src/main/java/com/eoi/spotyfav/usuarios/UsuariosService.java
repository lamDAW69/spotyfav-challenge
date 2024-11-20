package com.eoi.spotyfav.usuarios;


import com.eoi.spotyfav.auth.dto.LoginDTO;
import com.eoi.spotyfav.usuarios.dto.UsuarioDTO;
import com.eoi.spotyfav.utils.ImageUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuariosService {
    private final UsuariosRepository usuariosRepository;
    private final @NonNull ImageUtils imageUtils;

    List<User> getAll() {
        return usuariosRepository.findAll();
    }

    public User getById(int id) {
        User u = usuariosRepository.findUsuarioById(id);
        return u;
    }



    public User insert(UsuarioDTO usuarioDTO) throws NoSuchAlgorithmException {
        usuarioDTO.setPassword(encodePassword(usuarioDTO.getPassword()));
        usuarioDTO.setAvatar(imageUtils.saveImageBase64("usuarios", usuarioDTO.getAvatar()));
        User user = usuariosRepository.save(User.fromDTO(usuarioDTO));
        return usuariosRepository.findUsuarioById(user.getId());
    }

    public User update(int id, UsuarioDTO usuarioDTO) {
        User user = usuariosRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (!usuarioDTO.getAvatar().startsWith("http")) { // La imagen viene en Base64
            usuarioDTO.setAvatar(imageUtils.saveImageBase64("usuarios", usuarioDTO.getAvatar()));
        }
        User userUpdate = User.fromDTO(usuarioDTO);
        userUpdate.setId(id);
        userUpdate.setPassword(user.getPassword());
        usuariosRepository.save(userUpdate);
        return usuariosRepository.findUsuarioById(user.getId());
    }

    public void delete(int idUsuario) {
        usuariosRepository.deleteById(idUsuario);
    }

    public User login(LoginDTO loginDTO) throws NoSuchAlgorithmException {
        return usuariosRepository.findByCorreoAndPassword(loginDTO.getCorreo(), encodePassword(loginDTO.getPassword()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no encontrado"));
    }

    private String encodePassword(String pass) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
        String encodedPass = Base64.getEncoder().encodeToString(hash);
        return encodedPass;
    }

}
