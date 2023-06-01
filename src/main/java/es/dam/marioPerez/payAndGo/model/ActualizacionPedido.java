package es.dam.marioPerez.payAndGo.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import es.dam.marioPerez.payAndGo.utils.ActualizacionEnum;

@Entity
@Table(name="actualizacion_pedido")
public class ActualizacionPedido {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
	private long id;
    
    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    private LocalDateTime fecha;
    
    private ActualizacionEnum motivo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}


	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public ActualizacionEnum getMotivo() {
		return motivo;
	}

	public void setMotivo(ActualizacionEnum motivo) {
		this.motivo = motivo;
	}


     
}
