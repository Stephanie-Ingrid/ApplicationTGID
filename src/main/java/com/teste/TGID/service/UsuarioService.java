package com.teste.TGID.service;


import com.teste.TGID.Enums.UserType;
import com.teste.TGID.dto.UsuarioDTO;
import com.teste.TGID.entity.Usuario;
import com.teste.TGID.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public void validaTransacao(Usuario remetente, BigDecimal valorTransacao ) throws Exception {
        if(remetente.getUserType() == UserType.Empresa){
            throw new Exception( "Usuário do tipo Administrador não autorizado para essa transação." );
        }

        if ( remetente.getSaldo().compareTo(valorTransacao) < 0 ){
            throw new Exception("Saldo insuficiente.");
        }
        }
    public Usuario findUserById(Long id) throws Exception{
        return this.usuarioRepository.findUserById(id).orElseThrow(() ->new Exception( "Usuário não encontrado." ));

    }

    public void salvarUsuario (Usuario usuario){
        this.usuarioRepository.save(usuario);
    }

    public Usuario cadastrarUsuario( UsuarioDTO usuarioDTO ){
        Usuario novoUsuario = new Usuario( usuarioDTO );
        this.salvarUsuario(novoUsuario);
        return novoUsuario;
    }

    public List<Usuario> getAllUsers(){
       return this.usuarioRepository.findAll();
    }
}

