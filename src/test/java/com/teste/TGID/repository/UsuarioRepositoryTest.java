package com.teste.TGID.repository;


import com.teste.TGID.Enums.UserType;
import com.teste.TGID.dto.UsuarioDTO;
import com.teste.TGID.entity.Usuario;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("deve obter o usuário com sucesso do banco de dados")
    void findUserByDocumentCase1() {
        String document = "815.983.220-60";
        UsuarioDTO dados = new UsuarioDTO("Stephanie", "teste",document, new BigDecimal(100), "teste@gmail.com", "1234", UserType.Cliente);
        this.createUser(dados);

        Optional<Usuario> usuarioCriado = this.userRepository.findUserByDocument(document);

        assertThat(usuarioCriado.isPresent()).isTrue();
    }

    private Usuario createUser(UsuarioDTO usuarioDTO){
        Usuario novoUsuario = new Usuario(usuarioDTO);
        this.entityManager.persist(novoUsuario);
        return novoUsuario;
    }

    @Test
    @DisplayName("não deve obter o usuário do banco de dados quando o usuário não existe")
    void findUserByDocumentCase2() {
        String document = "815.983.220-68";

        Optional<Usuario> resultado = this.userRepository.findUserByDocument(document);

        assertThat(resultado.isEmpty()).isTrue();
    }
}