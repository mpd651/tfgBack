package es.dam.marioPerez.payAndGo.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductosPedidoKey implements Serializable{

    @Column(name = "pedidoId")
	Long pedidoId;
	
    @Column(name = "productoId")
	Long productoId;
    
	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public Long getProductoId() {
		return productoId;
	}

	public void setProductoId(Long productoId) {
		this.productoId = productoId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(pedidoId, productoId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductosPedidoKey other = (ProductosPedidoKey) obj;
		return Objects.equals(pedidoId, other.pedidoId) && Objects.equals(productoId, other.productoId);
	}



    
    
	
}
