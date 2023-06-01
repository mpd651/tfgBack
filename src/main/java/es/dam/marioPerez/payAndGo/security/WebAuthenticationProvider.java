package es.dam.marioPerez.payAndGo.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import es.dam.marioPerez.payAndGo.model.Usuario;
import es.dam.marioPerez.payAndGo.repository.UsuarioRepository;

public class WebAuthenticationProvider implements AuthenticationProvider{
	
	@Autowired
    private UsuarioRepository repository;

	@Autowired
    private PasswordEncoder encoder;
    
//    public WebAuthenticationProvider(UsuarioRepository repository, PasswordEncoder encoder) {
//        this.encoder = encoder;
//        this.repository = repository;
//    }
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        

        Usuario usuario = repository.findByuserName(username);
        if (usuario == null) {
            throw new BadCredentialsException("Details not found");
        }
        if (encoder.matches(password, usuario.getPassword())) {
        	
        	SimpleGrantedAuthority authority = new SimpleGrantedAuthority(usuario.getRol().name());
            List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
            grantedAuthorityList.add(authority);       
            
            return new UsernamePasswordAuthenticationToken(username, password, grantedAuthorityList);
        } else {
            throw new BadCredentialsException("Password mismatch");
        }
    }

	@Override
	public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
}
