package com.carlos.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carlos.app.entity.Imagen;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Integer>{
	
	public List<Imagen> findByOrderById();
	
	public List<Imagen> findByIdUsuario(Long idUsuario);

}
