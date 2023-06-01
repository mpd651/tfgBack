package es.dam.marioPerez.payAndGo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.dam.marioPerez.payAndGo.model.ActualizacionPedido;
import es.dam.marioPerez.payAndGo.model.Pedido;

public interface ActualizacionPedidoRepository extends JpaRepository<ActualizacionPedido, Long>{

	@Query("SELECT ap FROM ActualizacionPedido ap inner join Pedido p on p.id = ap.pedido WHERE ap.pedido = :pedido")
	public List<ActualizacionPedido> obtenerActualizacionesPedidoPorPedidoId(@Param("pedido")Pedido pedido);

}
