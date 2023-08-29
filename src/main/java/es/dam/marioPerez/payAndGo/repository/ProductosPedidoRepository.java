package es.dam.marioPerez.payAndGo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.dam.marioPerez.payAndGo.model.Categoria;
import es.dam.marioPerez.payAndGo.model.ProductosPedido;

public interface ProductosPedidoRepository extends JpaRepository<ProductosPedido, Long> {

	@Query("SELECT pp FROM ProductosPedido pp WHERE pp.pedido.id = :pedidoId")
	public List<ProductosPedido> obtenerTodosLosProductosPedidoPorPedido(@Param("pedidoId")long pedidoId);
}