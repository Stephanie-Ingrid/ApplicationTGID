package com.teste.TGID.entity;


import com.teste.TGID.Enums.UserType;
import com.teste.TGID.dto.UsuarioDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;

@Entity(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
@EqualsAndHashCode (of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String sobrenome;

    @CPF
    @Column(unique = true)
    private String document;

    @Column(unique = true)
    private String email;

    @Column(unique = false)
    private String senha;

    private BigDecimal saldo;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public Usuario (UsuarioDTO usuarioDTO){
        this.nome = usuarioDTO.nome();
        this.sobrenome = usuarioDTO.sobrenome();
        this.document = usuarioDTO.document();
        this.email = usuarioDTO.email();
        this.senha = usuarioDTO.senha();
        this.saldo = usuarioDTO.saldo();
        this.userType = usuarioDTO.type();
    }
}
