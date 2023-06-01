package es.dam.marioPerez.payAndGo.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.dam.marioPerez.payAndGo.model.ActualizacionPedido;
import es.dam.marioPerez.payAndGo.model.Pedido;
import es.dam.marioPerez.payAndGo.repository.ActualizacionPedidoRepository;
import es.dam.marioPerez.payAndGo.repository.PedidoRepository;

public class ActualizacionPedidoService {

	private static final Logger LOGGER = LogManager.getLogger(ActualizacionPedidoService.class);

	@Autowired
	private ActualizacionPedidoRepository actualizacionPedidoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public ActualizacionPedido crearActualizacionPedido(ActualizacionPedido actualizacionPedido) {
        return actualizacionPedidoRepository.save(actualizacionPedido);
	}
	
	public Page<ActualizacionPedido> obtenerTodasLasActualizacionesPedido (Pageable pageable){
		LOGGER.trace("Accediendo a la lectura de ActualizacionesaPedido");
		
		return actualizacionPedidoRepository.findAll(pageable);
	}
	
	public List<ActualizacionPedido> obtenerActualizacionesPedidoPorPedidoId (Long pedidoId){
		LOGGER.trace("Accediendo a la lectura de ActualizacionesaPedido por pedidoId");
		
		Optional<Pedido> pedidoEncontrado = pedidoRepository.findById(pedidoId);
		
		if (pedidoEncontrado.isEmpty()) {
			throw new RuntimeException("No existe el pedido");
		}
		
		return actualizacionPedidoRepository.obtenerActualizacionesPedidoPorPedidoId(pedidoEncontrado.get());
	}
	
	public Optional<ActualizacionPedido> obtenerActualizacionPedidoPorId(long id) {
		LOGGER.trace("Accediendo a la lectura de ActualizacionPedido por id");
		
		return actualizacionPedidoRepository.findById(id);
	}

}
