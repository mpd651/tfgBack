package es.dam.marioPerez.payAndGo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.dam.marioPerez.payAndGo.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

	@Query("SELECT c FROM Categoria c WHERE c.borrado = false")
	public List<Categoria> findCategoriaActivas();
}
