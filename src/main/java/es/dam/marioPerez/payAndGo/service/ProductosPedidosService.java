package es.dam.marioPerez.payAndGo.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.dam.marioPerez.payAndGo.dto.ProductosPedidoDto;
import es.dam.marioPerez.payAndGo.model.ProductosPedido;
import es.dam.marioPerez.payAndGo.repository.ProductosPedidoRepository;

@Service
public class ProductosPedidosService {

	private static final Logger LOGGER = LogManager.getLogger(ProductosPedidosService.class);

	@Autowired
	private ProductosPedidoRepository productosPedidosRepository;
	
	public List<ProductosPedidoDto> obtenerTodosLosProductosPedidoPorPedido (long pedidoId){
		LOGGER.trace("Accediendo a la lectura de productosPedidoPorPedido");
		
		List<ProductosPedido> prodPedidoLista = productosPedidosRepository.obtenerTodosLosProductosPedidoPorPedido(pedidoId);
		List<ProductosPedidoDto> dtos= new ArrayList<ProductosPedidoDto>();
		for (ProductosPedido pp:prodPedidoLista) {
			ProductosPedidoDto ppdto=new ProductosPedidoDto();
			ppdto.setProductoId(pp.getProducto().getId());
			ppdto.setCantidad(pp.getCantidad());
			ppdto.setHora(pp.getHora());
			ppdto.setProductoPedidoid(pp.getProductoPedidoid());
			ppdto.setProductoNombre(pp.getProducto().getNombre());
			ppdto.setProductoPrecio(pp.getProducto().getPrecio());
			dtos.add(ppdto);
		}
		return dtos;
	}
}
