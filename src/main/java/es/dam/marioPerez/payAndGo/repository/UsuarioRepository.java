package es.dam.marioPerez.payAndGo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.dam.marioPerez.payAndGo.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
	public Usuario findByuserName(String userName);
	
	public Boolean existsByUserName(String userName);
	
	@Query("SELECT u FROM Usuario u WHERE u.rol = :rol")
	public Page<Usuario> findUsuariosByRol(@Param("rol")String rol, Pageable pageable);
	
}
