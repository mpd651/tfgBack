package es.dam.marioPerez.payAndGo.service;

import java.util.List;
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
import es.dam.marioPerez.payAndGo.repository.ProductoRepository;

@Service
public class ProductoService {

	private static final Logger LOGGER = LogManager.getLogger(ProductoService.class);

	@Autowired
	private ProductoRepository productoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
	}
	
	public Page<Producto> obtenerTodosLosProductos (Pageable pageable){
		LOGGER.trace("Accediendo a la lectura de productos");
		
		return productoRepository.findAll(pageable);
	}
	
	public Optional<Producto> obtenerProductoPorId(long id) {
		LOGGER.trace("Accediendo a la lectura de productos por id");
		
		return productoRepository.findById(id);
	}
	
	public Producto actualizarProducto(long id, Producto producto) {
		LOGGER.trace("Actualizado el produto");
		
		Optional<Producto> productoParaActualizar = productoRepository.findById(id);
		
		if(productoParaActualizar.isEmpty()) {
			throw new RuntimeException("No se encontro ningún producto para editar");
		}
		
		productoParaActualizar.get().setCategoria(producto.getCategoria());
		productoParaActualizar.get().setCocina(producto.isCocina());
		productoParaActualizar.get().setNombre(producto.getNombre());
		productoParaActualizar.get().setPrecio(producto.getPrecio());
		
		return productoRepository.save(productoParaActualizar.get());
	}
	
	public void eliminarProducto(long id) {
		LOGGER.trace("Eliminando el producto");
		
		productoRepository.deleteById(id);
	}
	
	public List<Producto> obtenerProductosPorCategoria(Long categoriaId){
		LOGGER.trace("Accediento a obtener productos por categoria");

		Optional<Categoria> categoriaOpt = categoriaRepository.findById(categoriaId);
		
		if (categoriaOpt.isEmpty()) {
			throw new RuntimeException("No se ha encontrado la categoria");
		}
		
		Categoria categoria = categoriaOpt.get();
		return productoRepository.findProductosByCategoria(categoria);
	
	}
}
