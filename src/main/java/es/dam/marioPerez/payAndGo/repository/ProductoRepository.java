package es.dam.marioPerez.payAndGo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.dam.marioPerez.payAndGo.model.Categoria;
import es.dam.marioPerez.payAndGo.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
	
	@Query("SELECT p FROM Producto p WHERE p.categoria = :categoria")
	public List<Producto> findProductosByCategoria(@Param("categoria")Categoria categoria);	
	
	@Query("SELECT count(p) FROM Producto p WHERE p.categoria = :categoria")
	public Integer findProductosPorCategoria(@Param("categoria")Categoria categoria);	
	
	@Query("SELECT p FROM Producto p WHERE p.borrado = false")
	public List<Producto> findProductosActivo();

}
