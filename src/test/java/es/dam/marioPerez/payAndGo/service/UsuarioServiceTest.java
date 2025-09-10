package es.dam.marioPerez.payAndGo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.dam.marioPerez.payAndGo.model.Usuario;
import es.dam.marioPerez.payAndGo.repository.UsuarioRepository;
import es.dam.marioPerez.payAndGo.utils.JWTUtil;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
	
	@Mock
	private UsuarioRepository usuarioRepository;
	
    @Mock
    private JWTUtil jwtUtil;
	
	@InjectMocks
	UsuarioService usuarioService = new UsuarioService();

	@Test
	public void testObtenerUsuarioPorUsername_Success() {
		// Datos de prueba
        String token = "tokenValido";
        String usernameBuscado = "juan";
        String usernameLogin = "admin";
        String idLogin = "1";

        Usuario usuarioLogeado = new Usuario();
        usuarioLogeado.setId(1L);
        usuarioLogeado.setUserName(usernameLogin);

        Usuario usuarioBuscado = new Usuario();
        usuarioBuscado.setId(2L);
        usuarioBuscado.setUserName(usernameBuscado);

        // Mock behavior
        when(jwtUtil.getValue(token)).thenReturn(usernameLogin);
        when(jwtUtil.getKey(token)).thenReturn(idLogin);
        when(usuarioRepository.findByuserName(usernameLogin)).thenReturn(usuarioLogeado);
        when(usuarioRepository.findByuserName(usernameBuscado)).thenReturn(usuarioBuscado);

        // Llamada al método
        Usuario result = usuarioService.obtenerUsuarioPorUsername(usernameBuscado, token);

        // Asserts
        assertNotNull(result);
        assertEquals(usernameBuscado, result.getUserName());
	}
	
	 @Test
	 public void testObtenerUsuarioPorUsername_TokenInvalido_NullValues() {
	        String token = "tokenInvalido";

	        when(jwtUtil.getValue(token)).thenReturn(null);
	        when(jwtUtil.getKey(token)).thenReturn(null);

	        RuntimeException exception = assertThrows(RuntimeException.class, () ->
	                usuarioService.obtenerUsuarioPorUsername("juan", token)
	        );

	        assertEquals("Token inválido o sin clave", exception.getMessage());
	    }

	    @Test
	    void testObtenerUsuarioPorUsername_UsuarioLogeadoNoExiste() {
	        String token = "tokenValido";
	        String usernameLogin = "admin";
	        String idLogin = "1";

	        when(jwtUtil.getValue(token)).thenReturn(usernameLogin);
	        when(jwtUtil.getKey(token)).thenReturn(idLogin);
	        when(usuarioRepository.findByuserName(usernameLogin)).thenReturn(null);

	        RuntimeException exception = assertThrows(RuntimeException.class, () ->
	                usuarioService.obtenerUsuarioPorUsername("juan", token)
	        );

	        assertEquals("Token invalido", exception.getMessage());
	    }

	    @Test
	    void testObtenerUsuarioPorUsername_IdLoginNoCoincide() {
	        String token = "tokenValido";
	        String usernameLogin = "admin";
	        String idLogin = "99"; // Diferente del ID del usuario logeado

	        Usuario usuarioLogeado = new Usuario();
	        usuarioLogeado.setId(1L);
	        usuarioLogeado.setUserName(usernameLogin);

	        when(jwtUtil.getValue(token)).thenReturn(usernameLogin);
	        when(jwtUtil.getKey(token)).thenReturn(idLogin);
	        when(usuarioRepository.findByuserName(usernameLogin)).thenReturn(usuarioLogeado);

	        RuntimeException exception = assertThrows(RuntimeException.class, () ->
	                usuarioService.obtenerUsuarioPorUsername("juan", token)
	        );

	        assertEquals("Login invalido", exception.getMessage());
	    }
}
