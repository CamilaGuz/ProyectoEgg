package com.fanzin.servicios;

import com.fanzin.entidades.Imagen;
import com.fanzin.entidades.Usuario;
import com.fanzin.repositorios.UsuarioRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional(rollbackFor = Exception.class)
    public Usuario crear(String nombre, String mail, String contraseña, MultipartFile archivo) throws Exception {

        validar(nombre, mail, contraseña);

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setMail(mail);
        usuario.setContraseña(contraseña);
        Imagen imagen = imagenServicio.guardar(archivo);
        usuario.setImagen(imagen);

        return usuarioRepositorio.save(usuario);

    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(String id) throws Exception {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            return usuario;
        } else {
            throw new Exception("No se encontro el evento indicado");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Usuario modificar(String id, String nombre, String mail, String contraseña, MultipartFile archivo) throws Exception {

        validar(nombre, mail, contraseña);

        Usuario usuario = buscarPorId(id);
        usuario.setNombre(nombre);
        usuario.setMail(mail);
        usuario.setContraseña(contraseña);
        String idImagen = null;
        if (usuario.getImagen() != null) {
            idImagen = usuario.getImagen().getId();
        }
        Imagen imagen = imagenServicio.modificar(idImagen, archivo);
        usuario.setImagen(imagen);

        return usuarioRepositorio.save(usuario);
    }

    @Transactional(rollbackFor = Exception.class)
    public void Eliminar(String id) throws Exception {

        Usuario usuario = buscarPorId(id);

        usuarioRepositorio.delete(usuario);
    }

    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return usuarioRepositorio.findAll();
    }

    private void validar(String nombre, String mail, String contraseña) throws Exception {

        if (nombre == null || nombre.isEmpty()) {
            throw new Exception("Nombre vacío");
        }

        if (mail == null || mail.isEmpty()) {
            throw new Exception("E-mail vacío");
        }

        if (contraseña == null || contraseña.isEmpty()) {
            throw new Exception("Contraseña vacío");
        }
    }
   

}
