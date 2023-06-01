package es.dam.marioPerez.payAndGo.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.dam.marioPerez.payAndGo.model.Mesa;
import es.dam.marioPerez.payAndGo.repository.MesaRepository;

@Service
public class MesaService {

	private static final Logger LOGGER = LogManager.getLogger(MesaService.class);

	@Autowired
	private MesaRepository mesaRepository;
	
	public Mesa crearMesa(Mesa mesa) {
        return mesaRepository.save(mesa);
	}
	
	public Page<Mesa> obtenerTodasLasMesas (Pageable pageable){
		LOGGER.trace("Accediendo a la lectura de mesas");
		
		return mesaRepository.findAll(pageable);
	}
	
	public Optional<Mesa> obtenerMesaPorId(long id) {
		LOGGER.trace("Accediendo a la lectura de mesa por id");
		
		return mesaRepository.findById(id);
	}
	
	public Mesa actualizarMesa(long id, Mesa mesa) {
		LOGGER.trace("Actualizado la mesa");
		
		Optional<Mesa> mesaParaActualizar = mesaRepository.findById(id);
		
		if(mesaParaActualizar.isEmpty()) {
			throw new RuntimeException("No se encontro ninguna mesa para editar");
		}
		
		mesaParaActualizar.get().setNumeroMesa(mesa.getNumeroMesa());
		
		return mesaRepository.save(mesaParaActualizar.get());
	}
	
	public void eliminarMesa(long id) {
		LOGGER.trace("Eliminando la mesa");
		
		mesaRepository.deleteById(id);
	}
}
