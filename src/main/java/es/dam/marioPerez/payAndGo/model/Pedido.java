package es.dam.marioPerez.payAndGo.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="pedidos")
public class Pedido {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
    @JoinColumn(name="mesa_id", nullable=false)
	private Mesa mesa;
	
	@OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<ActualizacionPedido> actualizaciones;
	
	@OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
	List<ProductosPedido> productosPedidos;
	
	private float importe;
	
	private LocalDateTime fechaApertura;
	
	private LocalDateTime fechaCierre;
	
	private boolean pagado;
	
	private boolean anulado;
		
	private boolean asignadoCamarero;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}

	public float getImporte() {
		return importe;
	}

	public void setImporte(float importe) {
		this.importe = importe;
	}

	public List<ActualizacionPedido> getActualizaciones() {
		return actualizaciones;
	}

	public void setActualizaciones(List<ActualizacionPedido> actualizaciones) {
		this.actualizaciones = actualizaciones;
	}

	public LocalDateTime getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(LocalDateTime fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public LocalDateTime getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(LocalDateTime fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public boolean isPagado() {
		return pagado;
	}

	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}

	public boolean isAnulado() {
		return anulado;
	}

	public void setAnulado(boolean anulado) {
		this.anulado = anulado;
	}

	public List<ProductosPedido> getProductosPedidos() {
		return productosPedidos;
	}

	public void setProductosPedidos(List<ProductosPedido> productosPedidos) {
		this.productosPedidos = productosPedidos;
	}

	public boolean isAsignadoCamarero() {
		return asignadoCamarero;
	}

	public void setAsignadoCamarero(boolean asignadoCamarero) {
		this.asignadoCamarero = asignadoCamarero;
	}
	
	
	
	
	
	
	
	

}
