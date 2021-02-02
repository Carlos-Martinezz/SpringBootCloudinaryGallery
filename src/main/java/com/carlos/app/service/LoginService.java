package com.carlos.app.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carlos.app.entity.Usuario;
import com.carlos.app.repository.LoginRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author Carlos Martínez
 * @version 1.0
 * 
 * Clase @Service para obtener autenticación
 */
@Service
public class LoginService {
	
	@Autowired
	private LoginRepository loginRepository;
	
	public static String SECRET_KEY;
	
	@Value("${jwt.expirationDateInMs}")
	private int jwtExpirationInMs;
	
	@Value("${jwt.refreshExpirationDateInMs}")
	private int refreshExpirationDateInMs;
	
	@SuppressWarnings("static-access")
	@Value("${jwt.secret}")
	public void setSecret(String SECRET_KEY) {
		this.SECRET_KEY = SECRET_KEY;
	}
	
	/**
	 * Genera el Token JWT para el usuario autenticado
	 * @return String token;
	 */
	public String getJWTToken(String username) {
		String secretKey = SECRET_KEY;
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("carlosJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 3600000)) //TOKEN será válido por una hora 
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();
		
		return "Bearer " + token;
	}
	
	@Transactional( readOnly = true )
	public Usuario findUsuario(Usuario usuario) {
		System.out.println("Buscar usuario: " + usuario.getContrasena());
		return loginRepository.findUsuarioByNombreAndContrasena(usuario.getNombre(), usuario.getContrasena());
	}
	
	@Transactional
	public Usuario save(Usuario usuario) {
		System.out.println("Crear usuario: " + usuario.getContrasena());
		return loginRepository.save(usuario);
	}
	
	@Transactional( readOnly = true )
	public Usuario findUsuarioByNombre(Usuario usuario) {
		return loginRepository.findUsuarioByNombre(usuario.getNombre());
	}

}
