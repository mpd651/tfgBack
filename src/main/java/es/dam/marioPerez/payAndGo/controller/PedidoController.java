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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.dam.marioPerez.payAndGo.model.Mesa;
import es.dam.marioPerez.payAndGo.model.Pedido;
import es.dam.marioPerez.payAndGo.service.PedidoService;

@RestController
@RequestMapping(path = "/api/v1/pedido")
public class PedidoController {

	private static final Logger LOGGER = LogManager.getLogger(PedidoController.class);
	
	@Autowired
	private PedidoService pedidoService;
	
	@PostMapping("/crear/usuarioId")
	public ResponseEntity<Pedido> crearPedido(@RequestParam (name = "usuarioId") Long usuarioId ,@Validated @RequestBody Pedido pedido) {
		LOGGER.trace("Accediendo al controlador de creación de un pedido nuevo");
		
		Pedido pedidoGuardado = pedidoService.crearPedido(usuarioId, pedido);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.contentType(MediaType.APPLICATION_JSON)
				.body(pedidoGuardado);
	}
	
	@GetMapping
	public ResponseEntity<List<Pedido>> obtenerTodosLosPedidos(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size){
		
		Pageable pageable = PageRequest.of(page, size);
		
		LOGGER.trace("Accediendo al controlador de obtencion de pedidos disponibles");
		
		List<Pedido> pedidos = pedidoService.obtenerTodosLosPedidos(pageable).getContent();

		return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(pedidos);
	}
	
	@GetMapping("/id")
	public ResponseEntity<Pedido> obtenerPedidoPorId(@RequestParam(name = "id") long id){
		
		LOGGER.error("Accediendo al controlador de obtencion de pedido por id");
		
		Optional<Pedido> pedidoOpt = pedidoService.obtenerPedidoPorId(id);
		
		if (!pedidoOpt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(pedidoOpt.get());
		}else {
			return null;
		}
	}
	
	@GetMapping("/list/clientes")
	public ResponseEntity<List<Pedido>> obtenerListaPedidosClientes(){
		
		LOGGER.error("Accediendo al controlador de obtencion de pedidos por clientes");
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(pedidoService.findPedidoClientes());
	}
	
	@PostMapping("/id/asignarCamarero")
	public ResponseEntity<Pedido> asignarCamarero(@RequestParam(name = "id") long id){
		LOGGER.error("Accediendo al controlador para asignar camarero a pedido de un cliente");

		return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(pedidoService.asignarCamarero(id));
	}
	
	@GetMapping("/mesa")
	public ResponseEntity<Pedido> obtenerPedidoPorMesaActivo(@RequestBody Mesa mesa){
		
		LOGGER.error("Accediendo al controlador de obtencion de pedido por mesa");
				
		return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(pedidoService.obtenerPedidoPorMesaId(mesa));
	}
	
	@PutMapping("/actualizarPedido/usuarioId/pedidoId")
	public ResponseEntity<Pedido> actualizarPedido(@RequestParam (name = "usuarioId") Long usuarioId, @RequestParam (name = "pedidoId") Long pedidoId, @RequestBody Pedido pedido){
		LOGGER.error("Accediendo al controlador de actualizacion de pedido");
		
		Pedido pedidoGuardado =  pedidoService.actualizarPedido(usuarioId, pedidoId, pedido);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.contentType(MediaType.APPLICATION_JSON)
				.body(pedidoGuardado);

	}
	
	@PutMapping("/pagarPedido/usuarioId")
	public ResponseEntity<Pedido> pagarPedido(@RequestParam (name = "usuarioId") Long usuarioId, @RequestBody Pedido pedido ){
		LOGGER.error("Accediendo al controlador de pagar pedido");
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(pedidoService.pagarPedido(usuarioId, pedido));

	}
	
	@PutMapping("/anularPedido/usuarioId/pedidoId")
	public ResponseEntity<Pedido> anularPedido(@RequestParam (name = "usuarioId") Long usuarioId, @RequestParam (name = "pedidoId") Long pedidoId ){
		LOGGER.error("Accediendo al controlador de anular pedido");
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(pedidoService.anularPedido(usuarioId, pedidoId));

	}
}
