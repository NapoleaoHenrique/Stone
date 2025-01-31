package com.stone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stone.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	public List<Usuario>findByCargo(String cargo);
	
}
