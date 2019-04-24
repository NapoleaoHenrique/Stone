package com.stone.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stone.UsuarioService;
import com.stone.model.Usuario;
import com.stone.repository.UsuarioRepository;


@Controller
@RequestMapping("/usuarios")
public class UsuariosController {
	
	Logger logger = LogManager.getLogger(UsuariosController.class);
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private UsuarioService service;
	
	@PostMapping("/busca")
	public ModelAndView novaBusca(String cargo) {
		logger.info("Método de busca por cargo iniciado. Buscando: " + cargo);
		ModelAndView mv = new ModelAndView("/usuarios/usuarios-busca");
		mv.addObject("usuarios", repository.findByCargo(cargo));
		return mv;
	}
	
	@GetMapping("/{id}/deletar")
	public ModelAndView deletar(Usuario usuario) {
		logger.info("Método de deleção iniciado. Usuário deletado com sucesso.");
		repository.delete(usuario);
		return new ModelAndView("redirect:/usuarios");
	}
	
	@PostMapping("/{id}/editar")
	public ModelAndView atualizar(@PathVariable Long id, @Valid Usuario usuario, BindingResult br, RedirectAttributes ra) {
		logger.info("Método de edição iniciado. Usuário editado com sucesso.");
		if(br.hasErrors()) {
			return novo(usuario);
		}
		
		service.atualizar(id, usuario);
		ra.addFlashAttribute("mensagem", "Usuário atualizado com sucesso!");
		return new ModelAndView("redirect:/usuarios/novo");
	}
	
	
	@GetMapping("/{id}/editar")
	public ModelAndView editar(@PathVariable Long id) {
		logger.info("Método de edição iniciado. Buscando usuário pelo id.");
		return novo(repository.getOne(id));
	}
	
	
	@PostMapping("/novo")
	public ModelAndView criar(@Valid Usuario usuario, BindingResult br, RedirectAttributes ra) {
		logger.info("Método de cadastro iniciado.");
		if(br.hasErrors()) {
			logger.info("Cadastro contém erros, verifique e tente novamente.");
			return novo(usuario);
		}
		logger.info("Cadastro realizado com sucesso. Usuário incluído na base de dados.");
		repository.save(usuario);
		ra.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso!");
		return new ModelAndView("redirect:/usuarios/novo");
	}
	
	
	@GetMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView mv = new ModelAndView("usuarios/usuarios-cadastro");
		mv.addObject("usuario", usuario);
		return mv;
	}
	
	
	@GetMapping
	public ModelAndView listar() {
		logger.info("Lista de associados gerada com sucesso.");
		ModelAndView mv = new ModelAndView("/usuarios/usuarios-lista");
		mv.addObject("usuarios", repository.findAll());
		return mv;
	}
	
	

}
