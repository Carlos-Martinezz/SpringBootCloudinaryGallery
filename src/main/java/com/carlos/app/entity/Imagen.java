package com.carlos.app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Imagen {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String nombre;
	private String imagenUrl;
	private String imagenId;
	private Long idUsuario;

	public Imagen(String nombre, String imagenUrl, String imagenId, Long idUsuario) {
		this.nombre = nombre;
		this.imagenUrl = imagenUrl;
		this.imagenId = imagenId;
		this.idUsuario = idUsuario;
	}

	public Imagen() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getImagenUrl() {
		return imagenUrl;
	}

	public void setImagenUrl(String imagenUrl) {
		this.imagenUrl = imagenUrl;
	}

	public String getImagenId() {
		return imagenId;
	}

	public void setImagenId(String imagenId) {
		this.imagenId = imagenId;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
}
