package com.teste.TGID.controller;

import com.teste.TGID.dto.UsuarioDTO;
import com.teste.TGID.entity.Usuario;
import com.teste.TGID.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario( @RequestBody UsuarioDTO usuarioDTO ){
        Usuario novoUsuario = usuarioService.cadastrarUsuario(usuarioDTO);
        return new ResponseEntity<>( novoUsuario, HttpStatus.CREATED );

    }

    @GetMapping
    public  ResponseEntity<List<Usuario>> getAllUsers(){
        List<Usuario> usuarios = this.usuarioService.getAllUsers();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

}
