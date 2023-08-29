package es.dam.marioPerez.payAndGo.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.dam.marioPerez.payAndGo.dto.ProductoDto;
import es.dam.marioPerez.payAndGo.model.Producto;
import es.dam.marioPerez.payAndGo.service.ProductoService;

@RestController
@RequestMapping(path = "/api/v1/producto")
public class ProductoController {

	private static final Logger LOGGER = LogManager.getLogger(ProductoController.class);
	
	@Autowired
	private ProductoService productoService;
	
	@PostMapping
	public void crearProducto(@Validated @RequestBody Producto producto) {
		LOGGER.trace("Accediendo al controlador de creación de un producto nuevo");
		
		productoService.crearProducto(producto);
	}
	
	@GetMapping
	public ResponseEntity<List<ProductoDto>> obtenerTodosLosProductos(){
				
		LOGGER.trace("Accediendo al controlador de obtencion de productos disponibles");
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(productoService.obtenerTodosLosProductos());
	}
	
	@GetMapping("/id")
	public ResponseEntity<ProductoDto> obtenerProductoPorId(@RequestParam(name = "id") long id){
		
		LOGGER.error("Accediendo al controlador de obtencion de producto por id");
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(productoService.obtenerProductoPorId(id));

	}
	
	@PutMapping("/modificar")
	public void actualizarProducto(@RequestParam long id, @Validated @RequestBody Producto producto) {

		LOGGER.trace("Accediendo al controlador de modificación de un Producto");

		
		productoService.actualizarProducto(id, producto);

	}
	
	@DeleteMapping("/eliminar")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public void eliminarProducto(@RequestParam long id) {

		LOGGER.trace("Accediendo al controlador de eliminación de un Producto");

		productoService.eliminarProducto(id);
	}
	
	@GetMapping("/categoria/categoriaId")
	public ResponseEntity<List<ProductoDto>> obtenerProductosPorCategoria(@RequestParam(name = "categoriaId") long categoriaId){
				
		LOGGER.trace("Accediendo al controlador de obtencion de productos por categoria");
		
		List<ProductoDto> productosDto = productoService.obtenerProductosPorCategoria(categoriaId);

		return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(productosDto);
	}
	
}
