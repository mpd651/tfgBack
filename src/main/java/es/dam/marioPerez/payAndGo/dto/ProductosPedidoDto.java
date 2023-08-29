package es.dam.marioPerez.payAndGo.dto;

import java.time.LocalDateTime;

public class ProductosPedidoDto {
	
    private long productoPedidoid;
    
    private long productoId;
    
    private String productoNombre;
    
    private Float productoPrecio;
        
    private boolean servido;
    
    private int cantidad;
    
    private LocalDateTime hora;

    
	public long getProductoId() {
		return productoId;
	}

	public void setProductoId(long productoId) {
		this.productoId = productoId;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public LocalDateTime getHora() {
		return hora;
	}

	public void setHora(LocalDateTime hora) {
		this.hora = hora;
	}


	public long getProductoPedidoid() {
		return productoPedidoid;
	}

	public void setProductoPedidoid(long productoPedidoid) {
		this.productoPedidoid = productoPedidoid;
	}


	public String getProductoNombre() {
		return productoNombre;
	}

	public void setProductoNombre(String productoNombre) {
		this.productoNombre = productoNombre;
	}


	public Float getProductoPrecio() {
		return productoPrecio;
	}

	public void setProductoPrecio(Float productoPrecio) {
		this.productoPrecio = productoPrecio;
	}

	public boolean isServido() {
		return servido;
	}

	public void setServido(boolean servido) {
		this.servido = servido;
	}
    
	
    
}
