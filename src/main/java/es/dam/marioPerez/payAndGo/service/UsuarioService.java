package es.dam.marioPerez.payAndGo.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import es.dam.marioPerez.payAndGo.model.Usuario;
import es.dam.marioPerez.payAndGo.repository.UsuarioRepository;
import es.dam.marioPerez.payAndGo.utils.JWTUtil;
import es.dam.marioPerez.payAndGo.utils.UsuarioRol;

@Service
public class UsuarioService {

	private static final Logger LOGGER = LogManager.getLogger(CategoriaService.class);

	@Autowired
    private UsuarioRepository usuarioRepository;
	
    @Autowired
	private PasswordEncoder encoder;
    
	@Autowired
	private JWTUtil jwtUtil;
    
    
    
    public Usuario registrar(Usuario usuario) {
    	if (usuario.getRol()==UsuarioRol.CLIENTE) {
    		Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
    		String hash = argon2.hash(1, 1024, 1, usuario.getPassword());
        	usuario.setPassword(hash);
    	}

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
    	Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
		String hash = argon2.hash(1, 1024, 1, usuario.getPassword());
    	usuario.setPassword(hash);
    	
        return usuarioRepository.save(usuarioDB);
    }
    
    public String login(Usuario usuario) {
    	Usuario usuarioDB = usuarioRepository.findByuserName(usuario.getUserName());
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

    	if (usuarioDB == null) {
    		throw new RuntimeException("Usuario no encontrado");
    	}
    	
    	if (!usuarioDB.getRol().equals(usuario.getRol())) {
    		throw new RuntimeException("Perfil de usuario distinto");
    	}
    	
        if (!argon2.verify(usuarioDB.getPassword(), usuario.getPassword())) {
        	throw new RuntimeException("Password incorrecta");
        }
        
        String token = jwtUtil.create(String.valueOf(usuarioDB.getId()), usuarioDB.getUserName());
                
        return token;
    }
    
	public Optional<Usuario> obtenerUsuarioPorId(long id) {
		LOGGER.trace("Accediendo a la lectura de usuario por id");
		
		return usuarioRepository.findById(id);
	}
	
	public Usuario obtenerUsuarioPorUsername(String username, String token) {
		LOGGER.trace("Accediendo a la lectura de usuario por username");
		
		String userNameLogin = jwtUtil.getValue(token);
		String idLogin = jwtUtil.getKey(token);
		
		if (idLogin == null || userNameLogin == null) {
		    throw new RuntimeException("Token inválido o sin clave");
		}
		
		Usuario usuarioLogeado = usuarioRepository.findByuserName(userNameLogin);
		if (usuarioLogeado == null) {
			throw new RuntimeException("Token invalido");
		}
		if (usuarioLogeado.getId() != Long.parseLong(idLogin)) {
			throw new RuntimeException("Login invalido");
		}
		
		return usuarioRepository.findByuserName(username);
	}
    
    
	public List<Usuario> obtenerUsuariosPorRol (String rol){
		LOGGER.trace("Accediendo a la lectura de usuarios por Rol");		
		
		return usuarioRepository.findUsuariosByRol(UsuarioRol.valueOf(rol));
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
		if (usuario.getPassword()==null) {
			usuarioParaActualizar.get().setPassword(null);
		}
		
		if (StringUtils.isNotBlank(usuario.getPassword())) {
			usuarioParaActualizar.get().setPassword(null);
		}

		
		return usuarioRepository.save(usuarioParaActualizar.get());
	}
	
	public void eliminarUsuario(long id) {
		LOGGER.trace("Eliminando el usuario");
		
		Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
		
		if (usuarioOpt.isEmpty()) {
			throw new RuntimeException("El usuario no existe");
		}
		Usuario usuarioDB = usuarioOpt.get();
		usuarioDB.setBorrado(true);
		usuarioRepository.save(usuarioDB);
	}

    
    
    
}
