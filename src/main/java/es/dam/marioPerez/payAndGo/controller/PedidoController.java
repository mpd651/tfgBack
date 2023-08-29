package es.dam.marioPerez.payAndGo.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

import es.dam.marioPerez.payAndGo.dto.PedidoDto;
import es.dam.marioPerez.payAndGo.model.Pedido;
import es.dam.marioPerez.payAndGo.service.PedidoService;

@RestController
@RequestMapping(path = "/api/v1/pedido")
public class PedidoController {

	private static final Logger LOGGER = LogManager.getLogger(PedidoController.class);
	
	@Autowired
	private PedidoService pedidoService;
	
	@PostMapping("/crear/usuarioId")
	public ResponseEntity<Long> crearPedido(@RequestParam (name = "usuarioId") Long usuarioId ,@Validated @RequestBody Pedido pedido) {
		LOGGER.trace("Accediendo al controlador de creación de un pedido nuevo");
		
		Pedido p = pedidoService.crearPedido(usuarioId, pedido);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.contentType(MediaType.APPLICATION_JSON)
				.body(p.getId());
	}
	
	@GetMapping
	public ResponseEntity<List<PedidoDto>> obtenerTodosLosPedidos(){
				
		LOGGER.trace("Accediendo al controlador de obtencion de pedidos disponibles");
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(pedidoService.obtenerTodosLosPedidos());
	}
	
	@GetMapping("/id")
	public ResponseEntity<PedidoDto> obtenerPedidoPorId(@RequestParam(name = "id") long id){
		
		LOGGER.error("Accediendo al controlador de obtencion de pedido por id");
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(pedidoService.obtenerPedidoPorId(id));
	}
	
	@GetMapping("/list/clientes")
	public ResponseEntity<List<PedidoDto>> obtenerListaPedidosClientes(){
		
		LOGGER.error("Accediendo al controlador de obtencion de pedidos por clientes");
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(pedidoService.findPedidoClientes());
	}
	
	@PutMapping("/pedidoId/asignarCamarero")
	public void asignarCamarero(@RequestParam(name = "pedidoId") long id){
		LOGGER.error("Accediendo al controlador para asignar camarero a pedido de un cliente");
		pedidoService.asignarCamarero(id);
	}
	
	@GetMapping("/mesa")
	public ResponseEntity<PedidoDto> obtenerPedidoPorMesaActivo(@RequestParam (name="mesaId")long mesaId){
		
		LOGGER.error("Accediendo al controlador de obtencion de pedido por mesa");
				
		return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(pedidoService.obtenerPedidoPorMesaId(mesaId));
	}
	
	@PutMapping("/actualizarPedido/usuarioId/pedidoId")
	public ResponseEntity<Long> actualizarPedido(@RequestParam (name = "usuarioId") Long usuarioId, @RequestParam (name = "pedidoId") Long pedidoId, @RequestBody Pedido pedido){
		LOGGER.error("Accediendo al controlador de actualizacion de pedido");
		
		Pedido pedidoBD = pedidoService.actualizarPedido(usuarioId, pedidoId, pedido);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.contentType(MediaType.APPLICATION_JSON)
				.body(pedidoBD.getId());

	}
	
	
	@PutMapping("/pagarPedido/usuarioId")
	public void pagarPedido(@RequestParam (name = "usuarioId") Long usuarioId, @RequestBody Pedido pedido ){
		LOGGER.error("Accediendo al controlador de pagar pedido");
		
		pedidoService.pagarPedido(usuarioId, pedido);

	}
	
	@PutMapping("/anularPedido/usuarioId/pedidoId")
	public void anularPedido(@RequestParam (name = "usuarioId") Long usuarioId, @RequestParam (name = "pedidoId") Long pedidoId ){
		LOGGER.error("Accediendo al controlador de anular pedido");
		
		pedidoService.anularPedido(usuarioId, pedidoId);

	}
}
