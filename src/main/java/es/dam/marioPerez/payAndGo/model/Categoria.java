package es.dam.marioPerez.payAndGo.model;

import javax.persistence.Entity;

@Entity
public class Categoria {

	private long id;
	
	private String nombre;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}
