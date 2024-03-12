package com.teste.TGID.repository;

import com.teste.TGID.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Long>{

    Optional<Usuario> findUserByDocument(String document);

    Optional<Usuario> findUserById(Long id);


}
