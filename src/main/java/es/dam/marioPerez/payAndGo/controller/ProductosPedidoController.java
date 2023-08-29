package es.dam.marioPerez.payAndGo.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.dam.marioPerez.payAndGo.dto.ProductosPedidoDto;
import es.dam.marioPerez.payAndGo.service.ProductosPedidosService;

@RestController
@RequestMapping(path = "/api/v1/productosPedido")
public class ProductosPedidoController {
	
	private static final Logger LOGGER = LogManager.getLogger(ProductosPedidoController.class);

	@Autowired
	private ProductosPedidosService productosPedidoService;
	
	@GetMapping
	public ResponseEntity<List<ProductosPedidoDto>> obtenerTodosLosProductosPedidoPorPedido(@RequestParam(name="pedidoId") long pedidoId){
		
		LOGGER.trace("Accediendo al controlador de obtencion de productos pedidos disponibles");
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(productosPedidoService.obtenerTodosLosProductosPedidoPorPedido(pedidoId));
	}
}
