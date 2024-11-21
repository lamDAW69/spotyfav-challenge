package com.eoi.spotyfav.usuarios;


import com.eoi.spotyfav.auth.dto.LoginDTO;
import com.eoi.spotyfav.songs.SongsRepository;
import com.eoi.spotyfav.usuarios.dto.UsuarioDTO;
import com.eoi.spotyfav.usuarios.dto.UsuarioUpdateDTO;
import com.eoi.spotyfav.utils.ImageUtils;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cascade;
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
    private final SongsRepository songsRepository;

    List<User> getAll() {
        return usuariosRepository.findAll();
    }

    public User getById(int id) {
        User u = usuariosRepository.findUsuarioById(id);
        return u;
    }


    public User insert(UsuarioDTO usuarioDTO) throws NoSuchAlgorithmException {
        usuarioDTO.setPassword(encodePassword(usuarioDTO.getPassword()));
        if (!usuarioDTO.getAvatar().isEmpty()) { // La imagen viene en Base64
            usuarioDTO.setAvatar(imageUtils.saveImageBase64("usuarios", usuarioDTO.getAvatar()));
        }
        User user = usuariosRepository.save(User.fromDTO(usuarioDTO));
        return usuariosRepository.findUsuarioById(user.getId());
    }

    public User update(int id, UsuarioUpdateDTO usuarioDTO) throws NoSuchAlgorithmException  {
        User user = usuariosRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));


        user.setNombre(usuarioDTO.getNombre());
        user.setCorreo(usuarioDTO.getCorreo());

        if (!usuarioDTO.getAvatar().isEmpty()) { // La imagen viene en Base64
            user.setAvatar(imageUtils.saveImageBase64("usuarios", usuarioDTO.getAvatar()));
        }


            boolean esMismaPass = user.getPassword().equals(encodePassword(usuarioDTO.getPassword()));
            if (!esMismaPass) {
                user.setPassword(encodePassword(usuarioDTO.getPassword()));
            }


//        User userUpdate = User.fromUpdateDTO(usuarioDTO);
//        userUpdate.setId(id);

        usuariosRepository.save(user);
        return usuariosRepository.findUsuarioById(user.getId());
    }

//    public User update(int id, UsuarioUpdateDTO usuarioDTO) {
//        User user = usuariosRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
//        try {
//            boolean esMismaPass = user.getPassword().equals(encodePassword(usuarioDTO.getPassword()));
//
//            if (esMismaPass) {
//                if (usuarioDTO.getNombre().isEmpty()) {
//                    user.setNombre(usuarioDTO.getNombre());
//                }
//                if (usuarioDTO.getCorreo().isEmpty()) {
//                    user.setCorreo(usuarioDTO.getCorreo());
//                }
//                if (usuarioDTO.getAvatar().isEmpty()) {
//                    // La imagen viene en Base64
//                    usuarioDTO.setAvatar(imageUtils.saveImageBase64("usuarios", usuarioDTO.getAvatar()));
//                    user.setAvatar(usuarioDTO.getAvatar());
//                }
//                usuariosRepository.save(user);
//
//
////                user.setPassword(encodePassword(usuarioDTO.getPassword()));
//            }
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        System.out.println("UsuarioDTO: " + usuarioDTO.getCorreo());
//
//
//        return usuariosRepository.findUsuarioById(user.getId());
//    }

    @Transactional
    public void delete(int idUsuario) {
        songsRepository.deleteByCreador(idUsuario);
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
