package com.stone;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stone.model.Usuario;
import com.stone.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	public Usuario atualizar(Long id, Usuario usuario) {
		Usuario usuarioArmazenado = repository.getOne(id);
		
		BeanUtils.copyProperties(usuario, usuarioArmazenado, "id", "cadastro");
		
		repository.save(usuarioArmazenado);
		
		return repository.save(usuarioArmazenado);
	}
	
	
}
