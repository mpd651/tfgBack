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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.dam.marioPerez.payAndGo.model.Categoria;
import es.dam.marioPerez.payAndGo.service.CategoriaService;

@RestController
@RequestMapping(path = "/api/v1/categoria")
public class CategoriaController {

	private static final Logger LOGGER = LogManager.getLogger(ProductoController.class);

	@Autowired
	private CategoriaService categoriaService;
	
	@PostMapping
	public ResponseEntity<Categoria> crearCategoria(@Validated @RequestBody Categoria categoria) {
		LOGGER.trace("Accediendo al controlador de creación de un Categoria nueva");
		
		Categoria categoriaGuardado = categoriaService.crearCategoria(categoria);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.contentType(MediaType.APPLICATION_JSON)
				.body(categoriaGuardado);
	}
	
	@GetMapping
	public ResponseEntity<List<Categoria>> obtenerTodasLasCategorias(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size){
		
		Pageable pageable = PageRequest.of(page, size);
		
		LOGGER.trace("Accediendo al controlador de obtencion de Categorias disponibles");
		
		List<Categoria> categorias = categoriaService.obtenerTodasLasCategorias(pageable).getContent();

		return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(categorias);
	}
	
	@GetMapping("/id")
	public ResponseEntity<Categoria> obtenerCategoriaPorId(@RequestParam(name = "id") long id){
		
		LOGGER.error("Accediendo al controlador de obtencion de Categoria por id");
		
		Optional<Categoria> categoriaOpt = categoriaService.obtenerCategoriaPorId(id);
		
		if (!categoriaOpt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(categoriaOpt.get());
		}else {
			return null;
		}
	}
	
	@PutMapping("/modificar")
	public ResponseEntity<Categoria> actualizarCategoria(@RequestParam long id, @Validated @RequestBody Categoria categoria) {

		LOGGER.trace("Accediendo al controlador de modificación de una Categoria");

		
		Categoria categoriaModificada = categoriaService.actualizarCategoria(id, categoria);

		return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(categoriaModificada);

	}
	
	@DeleteMapping("/eliminar")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public void eliminarcategoria(@RequestParam long id) {

		LOGGER.trace("Accediendo al controlador de eliminación de una categoria");

		categoriaService.eliminarCategoria(id);
	}
}
