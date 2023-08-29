package es.dam.marioPerez.payAndGo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.dam.marioPerez.payAndGo.model.Mesa;

public interface MesaRepository extends JpaRepository<Mesa, Long> {

	@Query("SELECT m FROM Mesa m WHERE m.borrado = false")
	public List<Mesa> findMesasActivas();
}
