package com.carlos.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.carlos.app.entity.Usuario;

@Repository
public interface LoginRepository extends CrudRepository<Usuario, Long> {
	
	public Usuario findUsuarioByNombre(String nombre);
	
	public Usuario findUsuarioByNombreAndContrasena(String nombre, String contrasena);
	
}
