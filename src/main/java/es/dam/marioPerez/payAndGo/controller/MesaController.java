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

import es.dam.marioPerez.payAndGo.model.Mesa;
import es.dam.marioPerez.payAndGo.service.MesaService;

@RestController
@RequestMapping(path = "/api/v1/mesa")
public class MesaController {

	private static final Logger LOGGER = LogManager.getLogger(MesaController.class);

	@Autowired
	private MesaService mesaService;
	
	@PostMapping
	public ResponseEntity<Mesa> crearMesa(@Validated @RequestBody Mesa mesa) {
		LOGGER.trace("Accediendo al controlador de creación de una mesa nueva");
		
		Mesa mesaGuardado = mesaService.crearMesa(mesa);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.contentType(MediaType.APPLICATION_JSON)
				.body(mesaGuardado);
	}
	
	@GetMapping
	public ResponseEntity<List<Mesa>> obtenerTodasLasMesas(){
				
		LOGGER.trace("Accediendo al controlador de obtencion de mesas disponibles");
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(mesaService.obtenerTodasLasMesas());
	}
	
	@GetMapping("/id")
	public ResponseEntity<Mesa> obtenerMesaPorId(@RequestParam(name = "id") long id){
		
		LOGGER.error("Accediendo al controlador de obtencion de mesa por id");
		
		Optional<Mesa> mesaOpt = mesaService.obtenerMesaPorId(id);
		
		if (!mesaOpt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(mesaOpt.get());
		}else {
			return null;
		}
	}
	
	@PutMapping("/modificar")
	public ResponseEntity<Mesa> actualizarMesa(@RequestParam long id, @Validated @RequestBody Mesa mesa) {

		LOGGER.trace("Accediendo al controlador de modificación de una mesa");

		
		Mesa mesaModificada = mesaService.actualizarMesa(id, mesa);

		return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(mesaModificada);

	}
	
	@DeleteMapping("/eliminar")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public void eliminarMesa(@RequestParam long id) {

		LOGGER.trace("Accediendo al controlador de eliminación de una mesa");
		
		mesaService.eliminarMesa(id);
	}
}
