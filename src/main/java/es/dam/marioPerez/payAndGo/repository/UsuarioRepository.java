package es.dam.marioPerez.payAndGo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.dam.marioPerez.payAndGo.model.Usuario;
import es.dam.marioPerez.payAndGo.utils.UsuarioRol;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
	@Query("SELECT u FROM Usuario u WHERE u.userName = :userName AND u.borrado = false")
	public Usuario findByuserName(String userName);
	
	public Boolean existsByUserName(String userName);
	
	@Query("SELECT u FROM Usuario u WHERE u.rol = :rol AND u.borrado = false")
	public List<Usuario> findUsuariosByRol(@Param("rol")UsuarioRol rol);

}
