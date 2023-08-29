package es.dam.marioPerez.payAndGo.utils;

import java.util.List;

import es.dam.marioPerez.payAndGo.dto.PedidoDto;
import es.dam.marioPerez.payAndGo.dto.ProductoDto;
import es.dam.marioPerez.payAndGo.dto.ProductosPedidoDto;
import es.dam.marioPerez.payAndGo.model.Pedido;
import es.dam.marioPerez.payAndGo.model.Producto;
import es.dam.marioPerez.payAndGo.model.ProductosPedido;

public class DtoTransformer {

	
	public static PedidoDto pedidoToDto(Pedido pedido) {

		PedidoDto dto = new PedidoDto();
		dto.setId(pedido.getId());
		dto.setMesa(pedido.getMesa());
		dto.setImporte(pedido.getImporte());
		dto.setFechaApertura(pedido.getFechaApertura()==null?null:pedido.getFechaApertura());
		dto.setFechaCierre(pedido.getFechaCierre()==null?null:pedido.getFechaCierre());
		dto.setPagado(pedido.isPagado());
		dto.setAnulado(pedido.isAnulado());
		dto.setAsignadoCamarero(pedido.isAsignadoCamarero());
		return dto;

	}
	
	
	public static ProductoDto productoToDto (Producto producto) {

		ProductoDto dto = new ProductoDto();
		dto.setId(producto.getId());
		dto.setNombre(producto.getNombre()==null?null:producto.getNombre());
		dto.setPrecio(producto.getPrecio());
		dto.setCategoria(producto.getCategoria()==null?null:producto.getCategoria());
		dto.setBorrado(producto.isBorrado());
		return dto;

	}
	
	public List<ProductosPedidoDto> ProductoPedidoToDto(List<ProductosPedido> productosPedido){
		return null;
	}
}
