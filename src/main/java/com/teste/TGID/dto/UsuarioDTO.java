package com.teste.TGID.dto;

import com.teste.TGID.Enums.UserType;

import java.math.BigDecimal;

public record UsuarioDTO(String nome, String sobrenome, String document, BigDecimal saldo, String email, String senha, UserType type) {

}
