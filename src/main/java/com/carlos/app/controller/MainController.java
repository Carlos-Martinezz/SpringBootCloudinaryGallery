package com.carlos.app.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.carlos.app.dto.Mensaje;
import com.carlos.app.entity.Imagen;
import com.carlos.app.service.CloudinaryService;
import com.carlos.app.service.ImagenService;

@RestController
@RequestMapping("/cloudinary")
@CrossOrigin(origins = "*")
public class MainController {
	
	@Autowired
	CloudinaryService cloudinaryService;
	
	@Autowired
	ImagenService imagenService;
	
	@GetMapping("/list")
	public ResponseEntity<List<Imagen>> list() {
		List<Imagen> list = imagenService.list();
		return new ResponseEntity<List<Imagen>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/myList/{idUsuario}")
	public ResponseEntity<List<Imagen>> myList(@PathVariable Long idUsuario) {
		List<Imagen> list = imagenService.myList(idUsuario);
		return new ResponseEntity<List<Imagen>>(list, HttpStatus.OK);
	}
	
	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestParam MultipartFile multipartFile, @RequestParam Long idUsuario) throws IOException {
		
		BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
		
		if(bi == null) {
			return new ResponseEntity<Object>(new Mensaje("Imagen no valida"), HttpStatus.BAD_REQUEST);
		}
		
		Map result = cloudinaryService.upload(multipartFile);
		Imagen imagen = new Imagen(
									(String) result.get("original_filename"), 
									(String) result.get("url"), 
									(String) result.get("public_id"),
									idUsuario
								  );
		
		imagenService.save(imagen);
		
		return new ResponseEntity<Object>(new Mensaje("Imagen subida"), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id) throws IOException {
		
		if(!imagenService.exists(id)) 
			return new ResponseEntity<Object>(new Mensaje("No existe la imagen"), HttpStatus.NOT_FOUND);
		
		Imagen imagen = imagenService.getOne(id).get();
		
		Map result = cloudinaryService.delete(imagen.getImagenId());
		imagenService.delete(id);
		
		return new ResponseEntity<Object>(new Mensaje("Imagen eliminada"), HttpStatus.OK);
	}

}
