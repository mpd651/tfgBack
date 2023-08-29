package es.dam.marioPerez.payAndGo.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.dam.marioPerez.payAndGo.model.Mesa;
import es.dam.marioPerez.payAndGo.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
	
	@Query("SELECT p FROM Pedido p inner join ActualizacionPedido ap on p.id = ap.pedido.id WHERE p.asignadoCamarero = false AND p.pagado = false AND p.anulado = false AND FUNCTION('DATE', p.fechaApertura) = :today ORDER BY ap.fecha")
	public List<Pedido> findPedidosClientes(@Param("today")Date today);	

	@Query("SELECT p FROM Pedido p WHERE p.mesa = :mesa AND p.pagado = false AND p.anulado = false AND FUNCTION('DATE', p.fechaApertura) = :today ORDER BY p.fechaApertura")
	public Optional<Pedido> findByMesaActivos(@Param("mesa")Mesa mesa, @Param("today")Date today);	

	@Query("SELECT p FROM Pedido p WHERE p.anulado = false")
	public List<Pedido> findPedidosActivos();
}
