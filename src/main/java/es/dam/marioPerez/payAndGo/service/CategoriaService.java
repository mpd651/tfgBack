package es.dam.marioPerez.payAndGo.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.dam.marioPerez.payAndGo.model.Categoria;
import es.dam.marioPerez.payAndGo.repository.CategoriaRepository;

@Service
public class CategoriaService {

	private static final Logger LOGGER = LogManager.getLogger(CategoriaService.class);

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria crearCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
	}
	
	public List<Categoria> obtenerTodasLasCategorias (){
		LOGGER.trace("Accediendo a la lectura de categorias");
		
		return categoriaRepository.findCategoriaActivas();
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
				
		Optional<Categoria> categoriaOpt = categoriaRepository.findById(id);
		
		if (categoriaOpt.isEmpty()) {
			throw new RuntimeException("La categoria no existe");
		}
		Categoria categoriaBD = categoriaOpt.get();
		categoriaBD.setBorrado(true);
		categoriaRepository.save(categoriaBD);
	}

}
