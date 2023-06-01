package es.dam.marioPerez.payAndGo.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.dam.marioPerez.payAndGo.model.Categoria;
import es.dam.marioPerez.payAndGo.model.Producto;
import es.dam.marioPerez.payAndGo.repository.CategoriaRepository;

@Service
public class CategoriaService {

	private static final Logger LOGGER = LogManager.getLogger(CategoriaService.class);

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria crearCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
	}
	
	public Page<Categoria> obtenerTodasLasCategorias (Pageable pageable){
		LOGGER.trace("Accediendo a la lectura de categorias");
		
		return categoriaRepository.findAll(pageable);
	}
	
	public Optional<Categoria> obtenerCategoriaPorId(long id) {
		LOGGER.trace("Accediendo a la lectura de categoria por id");
		
		return categoriaRepository.findById(id);
	}
	
	public Categoria actualizarCategoria(long id, Categoria categoria) {
		LOGGER.trace("Actualizado la categoria");
		
		Optional<Categoria> categoriaParaActualizar = categoriaRepository.findById(id);
		
		if(categoriaParaActualizar.isEmpty()) {
			throw new RuntimeException("No se encontro ningún categoria para editar");
		}
		
		categoriaParaActualizar.get().setNombre(categoria.getNombre());
		
		return categoriaRepository.save(categoriaParaActualizar.get());
	}
	
	public void eliminarCategoria(long id) {
		LOGGER.trace("Eliminando la categoria");
		
		categoriaRepository.deleteById(id);
	}
}
