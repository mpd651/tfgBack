package es.dam.marioPerez.payAndGo.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.dam.marioPerez.payAndGo.model.Categoria;
import es.dam.marioPerez.payAndGo.model.Usuario;
import es.dam.marioPerez.payAndGo.repository.UsuarioRepository;
import es.dam.marioPerez.payAndGo.utils.UsuarioRol;

@Service
public class UsuarioService {

	private static final Logger LOGGER = LogManager.getLogger(CategoriaService.class);

    private UsuarioRepository usuarioRepository;
	
    private PasswordEncoder encoder;
    
    public UsuarioService (UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
    }
    
    
    public Usuario registrar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    
    public Usuario registrarPassword(Usuario usuario) {
    	Usuario usuarioDB = usuarioRepository.findByuserName(usuario.getUserName());
    	
    	if (usuarioDB == null) {
    		throw new BadCredentialsException("Usuario no encontrado");
    	}
    	
    	if (StringUtils.isNotBlank(usuarioDB.getPassword() )){
    		throw new BadCredentialsException("El usuario ya tiene contrasena");
    	}
    	
    	if (!usuarioDB.getRol().equals(usuario.getRol())) {
    		throw new RuntimeException("Perfil de usuario distinto");
    	}
    	
    	usuarioDB.setPassword(encoder.encode(usuario.getPassword()));
    	
        return usuarioRepository.save(usuarioDB);
    }
    
    public boolean login(Usuario usuario) {
    	Usuario usuarioDB = usuarioRepository.findByuserName(usuario.getUserName());
    	
    	if (usuarioDB == null) {
    		return false;
    	}
    	
        if (encoder.matches(usuario.getPassword(), usuarioDB.getPassword()) && usuario.getRol().equals(usuarioDB.getRol())) {
            return true;
        }
        return false;
    	   
    }
    
	public Optional<Usuario> obtenerUsuarioPorId(long id) {
		LOGGER.trace("Accediendo a la lectura de usuario por id");
		
		return usuarioRepository.findById(id);
	}
    
    
	public Page<Usuario> obtenerUsuariosPorRol (String rol, Pageable pageable){
		LOGGER.trace("Accediendo a la lectura de usuarios por Rol");
		
		
		return usuarioRepository.findUsuariosByRol(rol, pageable);
	}

	public Usuario actualizarUsuario(long id, Usuario usuario) {
		LOGGER.trace("Actualizado el usuario");
		
		Optional<Usuario> usuarioParaActualizar = usuarioRepository.findById(id);
		
		if(usuarioParaActualizar.isEmpty()) {
			throw new RuntimeException("No se encontro ningún usuario para editar");
		}
		
		usuarioParaActualizar.get().setNombre(usuario.getNombre());
		usuarioParaActualizar.get().setApellidos(usuario.getApellidos());
		usuarioParaActualizar.get().setUserName(usuario.getUserName());
		
		if (StringUtils.isNotBlank(usuario.getPassword())) {
			usuarioParaActualizar.get().setPassword(null);
		}

		
		return usuarioRepository.save(usuarioParaActualizar.get());
	}
	
	public void eliminarUsuario(long id) {
		LOGGER.trace("Eliminando el usuario");
		
		usuarioRepository.deleteById(id);
	}

    
    
    
}
