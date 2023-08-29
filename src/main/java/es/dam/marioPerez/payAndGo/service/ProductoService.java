package es.dam.marioPerez.payAndGo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.dam.marioPerez.payAndGo.dto.ProductoDto;
import es.dam.marioPerez.payAndGo.model.Categoria;
import es.dam.marioPerez.payAndGo.model.Producto;
import es.dam.marioPerez.payAndGo.repository.CategoriaRepository;
import es.dam.marioPerez.payAndGo.repository.ProductoRepository;
import es.dam.marioPerez.payAndGo.utils.DtoTransformer;

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
	
	public List<ProductoDto> obtenerTodosLosProductos (){
		LOGGER.trace("Accediendo a la lectura de productos");
		
		List<Producto> productos = productoRepository.findProductosActivo();
		List<ProductoDto> dtos = new ArrayList<ProductoDto>();
		
		for (Producto prod: productos) {
			dtos.add(DtoTransformer.productoToDto(prod));
		}
		return dtos;
	}
	
	public ProductoDto obtenerProductoPorId(long id) {
		LOGGER.trace("Accediendo a la lectura de productos por id");
		
		Optional<Producto> productoOpt = productoRepository.findById(id);
		
		if (!productoOpt.isEmpty()) {
			 return DtoTransformer.productoToDto(productoRepository.findById(id).get());
		}else {
			return null;
		}		
	}
	
	public Producto actualizarProducto(long id, Producto producto) {
		LOGGER.trace("Actualizado el produto");
		
		Optional<Producto> productoParaActualizar = productoRepository.findById(id);
		
		if(productoParaActualizar.isEmpty()) {
			throw new RuntimeException("No se encontro ningún producto para editar");
		}
		
		productoParaActualizar.get().setCategoria(producto.getCategoria());
		productoParaActualizar.get().setNombre(producto.getNombre());
		productoParaActualizar.get().setPrecio(producto.getPrecio());
		
		return productoRepository.save(productoParaActualizar.get());
		
	}
	
	public void eliminarProducto(long id) {
		LOGGER.trace("Eliminando el producto");
		
		Optional<Producto> productoOpt = productoRepository.findById(id);
		
		if (productoOpt.isEmpty()) {
			throw new RuntimeException("El producto no existe");
		}
		Producto productoBD = productoOpt.get();
		productoBD.setBorrado(true);
		productoRepository.save(productoBD);
	}
	
	public List<ProductoDto> obtenerProductosPorCategoria(Long categoriaId){
		LOGGER.trace("Accediento a obtener productos por categoria");

		Optional<Categoria> categoriaOpt = categoriaRepository.findById(categoriaId);
		
		if (categoriaOpt.isEmpty()) {
			throw new RuntimeException("No se ha encontrado la categoria");
		}
		
		Categoria categoria = categoriaOpt.get();
		
		List<Producto> productos = productoRepository.findProductosByCategoria(categoria);
		List<ProductoDto> dtos = new ArrayList<ProductoDto>();
		
		for (Producto prod: productos) {
			dtos.add(DtoTransformer.productoToDto(prod));
		}
		return dtos;
	
	}

	public int obtenerNumeroProductosPorCategoria(long id) {
		LOGGER.trace("Accediento a obtener numero de productos por categoria");

		Optional<Categoria> categoriaOpt = categoriaRepository.findById(id);
		
		if (categoriaOpt.isEmpty()) {
			throw new RuntimeException("No se ha encontrado la categoria");
		}
		
		Categoria categoria = categoriaOpt.get();
		return productoRepository.findProductosPorCategoria(categoria);
	}
}
