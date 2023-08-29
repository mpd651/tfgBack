package es.dam.marioPerez.payAndGo.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="productos")
public class Producto {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
	private long id;
	
	private String nombre;
	
	private float precio;
		
	@ManyToOne
    @JoinColumn(name="categoria_id", nullable=false)
	private Categoria categoria;
	
	@OneToMany(mappedBy = "producto")
	List<ProductosPedido> productosPedidos;
	
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

	public List<ProductosPedido> getProductosPedidos() {
		return productosPedidos;
	}

	public void setProductosPedidos(List<ProductosPedido> productosPedidos) {
		this.productosPedidos = productosPedidos;
	}

	public boolean isBorrado() {
		return borrado;
	}

	public void setBorrado(boolean borrado) {
		this.borrado = borrado;
	}
	
	
	
	
}
