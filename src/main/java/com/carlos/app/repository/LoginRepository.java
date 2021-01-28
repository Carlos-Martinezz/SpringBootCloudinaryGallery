package com.carlos.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carlos.app.entity.Usuario;

@Repository
public interface LoginRepository extends JpaRepository<Usuario, Long> {
	
	public Usuario findUsuarioByNombre(String nombre);
	
}
