package es.dam.marioPerez.payAndGo.dto;

import java.time.LocalDateTime;

import es.dam.marioPerez.payAndGo.model.Mesa;

public class PedidoDto {

	private long id;
	
	private Mesa mesa;
			
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

	public boolean isAsignadoCamarero() {
		return asignadoCamarero;
	}

	public void setAsignadoCamarero(boolean asignadoCamarero) {
		this.asignadoCamarero = asignadoCamarero;
	}
	
	
	
	
	
	
	
	

}
