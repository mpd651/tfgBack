package es.dam.marioPerez.payAndGo.model;

import java.time.LocalDateTime;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class ProductosPedido {

    @EmbeddedId
    ProductosPedidoKey id;
    
    @ManyToOne
    @MapsId("productoId")
    @JoinColumn(name = "productoId")
    private Producto producto;
    
    @ManyToOne
    @MapsId("pedidoId")
    @JoinColumn(name = "pedidoId")
    private Pedido pedido;
    
    private boolean servido;
    
    private int cantidad;
    
    private LocalDateTime hora;

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

	public ProductosPedidoKey getId() {
		return id;
	}

	public void setId(ProductosPedidoKey id) {
		this.id = id;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public boolean isServido() {
		return servido;
	}

	public void setServido(boolean servido) {
		this.servido = servido;
	}
    
	
    
}
