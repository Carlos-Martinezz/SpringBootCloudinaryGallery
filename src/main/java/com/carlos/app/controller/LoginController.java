package com.carlos.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carlos.app.dto.Mensaje;
import com.carlos.app.entity.Usuario;
import com.carlos.app.service.LoginService;

@RestController
@RequestMapping("/cloudinary")
public class LoginController {
	
	@Autowired
	private LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Usuario usuario) {
		
		Usuario usuarioBuscado = loginService.findUsuario(usuario);
		
		if(usuarioBuscado == null) {
			return new ResponseEntity<Object>(new Mensaje("No existe un usuario con estas credenciales"), HttpStatus.NOT_FOUND);
		}
		
		String token = loginService.getJWTToken(usuarioBuscado.getNombre());
		usuarioBuscado.setToken(token);
		
		return new ResponseEntity<Object>(usuarioBuscado, HttpStatus.OK);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> signing(@RequestBody Usuario usuario) {
		
		Usuario nuevoUsuario = loginService.findUsuarioByNombre(usuario);
		
		if(nuevoUsuario == null) {
			Usuario nuevo = loginService.save(usuario);
			return new ResponseEntity<Object>(nuevo, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<Object>(new Mensaje("El usuario ya existe"), HttpStatus.CONFLICT);
	}
	
}



