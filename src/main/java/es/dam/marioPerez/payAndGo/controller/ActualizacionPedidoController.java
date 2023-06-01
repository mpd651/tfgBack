package es.dam.marioPerez.payAndGo.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.dam.marioPerez.payAndGo.model.ActualizacionPedido;
import es.dam.marioPerez.payAndGo.service.ActualizacionPedidoService;

@RestController
@RequestMapping(path = "/api/v1/actualizacionPedido")
public class ActualizacionPedidoController {


	private static final Logger LOGGER = LogManager.getLogger(ActualizacionPedidoController.class);
	
	@Autowired
	private ActualizacionPedidoService actualizacionPedidoService;
	
	@GetMapping
	public ResponseEntity<List<ActualizacionPedido>> obtenerTodasLasActualizacionesPedido(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size){
		
		Pageable pageable = PageRequest.of(page, size);
		
		LOGGER.trace("Accediendo al controlador de obtencion de actualizaciones de pedidos disponibles");
		
		List<ActualizacionPedido> pedidos = actualizacionPedidoService.obtenerTodasLasActualizacionesPedido(pageable).getContent();

		return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(pedidos);
	}
	
	@GetMapping("/lista/pedidoId")
	public ResponseEntity<List<ActualizacionPedido>> obtenerActualizacionesPedidoPorPedidoId(@RequestParam(name = "pedidoId") long id){
				
		LOGGER.trace("Accediendo al controlador de obtencion de actualizaciones de pedidos por pedidoId");
		
		List<ActualizacionPedido> pedidos = actualizacionPedidoService.obtenerActualizacionesPedidoPorPedidoId(id);

		return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(pedidos);
	}
	
	@GetMapping("/id")
	public ResponseEntity<ActualizacionPedido> obtenerActualizacionPedidoPorId(@RequestParam(name = "id") long id){
		
		LOGGER.error("Accediendo al controlador de obtencion de actualizaciones pedido por id");
		
		Optional<ActualizacionPedido> actualizacionPedidoOpt = actualizacionPedidoService.obtenerActualizacionPedidoPorId(id);
		
		if (!actualizacionPedidoOpt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(actualizacionPedidoOpt.get());
		}else {
			return null;
		}
	}
	


}
