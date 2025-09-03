package es.dam.marioPerez.payAndGo.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import es.dam.marioPerez.payAndGo.model.Usuario;
import es.dam.marioPerez.payAndGo.service.UsuarioService;
import es.dam.marioPerez.payAndGo.utils.JWTUtil;
import es.dam.marioPerez.payAndGo.utils.UsuarioRol;

@RestController
@RequestMapping(path = "/api/v1/usuario")
public class UsuarioController {
	
	private static final Logger LOGGER = LogManager.getLogger(UsuarioController.class);

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	
    
    @PostMapping("/registrarPassword")
    public ResponseEntity<Usuario> registrarPasswordAdministrador(@RequestBody Usuario usuario) {    
    	try{
    		usuarioService.registrarPassword(usuario);
    		
    		return ResponseEntity
    				.status(HttpStatus.CREATED)
    				.contentType(MediaType.APPLICATION_JSON)
    				.body(usuario);
    		
    	}catch (BadCredentialsException ex) {
    		throw new ResponseStatusException(
    		           HttpStatus.NOT_FOUND, ex.getMessage(), ex);
    	}
    	
    	

    }
    
    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
    	Usuario usuario1 = new Usuario();
    	usuario1.setUserName(usuario.getUserName());
    	usuario1.setRol(usuario.getRol());
    	usuario1.setPassword(usuario.getPassword());
    	usuario1.setNombre(usuario.getNombre());
    	usuario1.setApellidos(usuario.getApellidos());
    	
		Usuario usuarioBD = usuarioService.registrar(usuario1);
    	
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.contentType(MediaType.APPLICATION_JSON)
				.body(usuarioBD);
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
    	
    	try {
    		
    		return ResponseEntity
				.status(HttpStatus.CREATED)
				.contentType(MediaType.APPLICATION_JSON)
				.body(usuarioService.login(usuario));
    	
    	
    	}catch (RuntimeException ex) {
    		throw new ResponseStatusException(
 		           HttpStatus.NOT_FOUND, ex.getMessage(), ex);
    	}
    	
    }
    
	@GetMapping("/id")
	public ResponseEntity<Usuario> obtenerUsuarioPorId(@RequestParam(name = "id") long id){
		
		LOGGER.error("Accediendo al controlador de obtencion de Usuario por id");
		
		Optional<Usuario> usuarioOpt = usuarioService.obtenerUsuarioPorId(id);
		
		if (!usuarioOpt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(usuarioOpt.get());
		}else {
			return null;
		}
	}
	
	@GetMapping("/username")
	public ResponseEntity<Usuario> obtenerUsuarioPorUsername(@RequestParam(name = "username") String username, @RequestHeader(value="Authorization") String token){
		
		LOGGER.error("Accediendo al controlador de obtencion de Usuario por username");
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(usuarioService.obtenerUsuarioPorUsername(username, token));
	}
	
    
	@GetMapping("/lista")
	public ResponseEntity<List<Usuario>> obtenerUsuariosPorRol(@RequestParam String rol){
				
		LOGGER.trace("Accediendo al controlador de obtencion de usuarios disponibles por rol");
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(usuarioService.obtenerUsuariosPorRol(rol));
	}
	
	@PutMapping("/modificar")
	public ResponseEntity<Usuario> actualizarUsuario(@RequestParam long id, @Validated @RequestBody Usuario usuario) {

		LOGGER.trace("Accediendo al controlador de modificación de un usuario");

		
		Usuario usuarioModificado = usuarioService.actualizarUsuario(id, usuario);

		return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(usuarioModificado);

	}
	
	@DeleteMapping("/eliminar")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public void eliminarUsuario(@RequestParam long id) {

		LOGGER.trace("Accediendo al controlador de eliminación de un usuario");

		usuarioService.eliminarUsuario(id);
	}
	
	
    
}
