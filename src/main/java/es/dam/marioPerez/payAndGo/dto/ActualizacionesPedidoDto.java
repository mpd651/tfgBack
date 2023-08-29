package es.dam.marioPerez.payAndGo.dto;

import java.time.LocalDateTime;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.dam.marioPerez.payAndGo.model.Pedido;
import es.dam.marioPerez.payAndGo.model.Usuario;
import es.dam.marioPerez.payAndGo.utils.ActualizacionEnum;

public class ActualizacionesPedidoDto {

	private long id;
        
    private Usuario usuario;
    
    private LocalDateTime fecha;
    
    private ActualizacionEnum motivo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
