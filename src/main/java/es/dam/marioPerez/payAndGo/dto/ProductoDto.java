package es.dam.marioPerez.payAndGo.dto;

import es.dam.marioPerez.payAndGo.model.Categoria;

public class ProductoDto {

	private long id;
	
	private String nombre;
	
	private float precio;
		
	private Categoria categoria;
		
	private boolean borrado;

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

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}


	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public boolean isBorrado() {
		return borrado;
	}

	public void setBorrado(boolean borrado) {
		this.borrado = borrado;
	}
	
	
	
	
}
