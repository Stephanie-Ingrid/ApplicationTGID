package com.teste.TGID.service;


import com.teste.TGID.Enums.UserType;
import com.teste.TGID.dto.TransactionDTO;
import com.teste.TGID.entity.Usuario;
import com.teste.TGID.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DataJpaTest
class TransactionServiceTest {

    @Mock
    private UsuarioService usuarioService;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AuthorizationService authService;
    @Mock
    private NotificationService notificationService;

    @Autowired
    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName( "Deve criar Transação se tudo estiver correto" )
    void validarTransacaoCase1() throws Exception {
        Usuario remetente = new Usuario(1L, "Maria", "Silva", "815.983.220-68", "mariateste@gmail.com", "12345", new BigDecimal(100), UserType.Comum);
        Usuario destinatario = new Usuario(1L, "João", "Silva", "815.983.220-69", "joaoteste@gmail.com", "12345", new BigDecimal(100), UserType.Comum);

        when(usuarioService.findUserById(1L)).thenReturn(remetente);
        when(usuarioService.findUserById(2L)).thenReturn(destinatario);

        when(authService.authorizeTransaction(any(), any())).thenReturn(true);

        TransactionDTO request = new TransactionDTO(1L, 2L, new BigDecimal(50), new BigDecimal(5));
        transactionService.criarTransacao(request);

        verify(transactionRepository, times(1)).save(any());

        remetente.setSaldo(new BigDecimal(0));
        verify(usuarioService, times(1)).salvarUsuario(remetente);

        destinatario.setSaldo(new BigDecimal(20));
        verify(usuarioService, times(1)).salvarUsuario(destinatario);

        verify(notificationService, times(1)).envioNotificacao(remetente,"Transação realizada com sucesso.");
        verify(notificationService, times(1)).envioNotificacao(destinatario,"Transação realizada com sucesso.");
    }
    @Test
    @DisplayName( "Deve lançar uma exception quando a transação não é premetida" )
    void criarTransacaoCase2() throws Exception {

        Usuario remetente = new Usuario(1L, "Maria", "Silva", "815.983.220-68", "mariateste@gmail.com", "12345", new BigDecimal(100), UserType.Cliente);
        Usuario destinatario = new Usuario(1L, "João", "Silva", "815.983.220-69", "joaoteste@gmail.com", "12345", new BigDecimal(100), UserType.Cliente);

        when(usuarioService.findUserById(1L)).thenReturn(remetente);
        when(usuarioService.findUserById(2L)).thenReturn(destinatario);

        when(authService.authorizeTransaction(any(), any())).thenReturn(false);

        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            TransactionDTO request = new TransactionDTO(1L, 2L, new BigDecimal(50), new BigDecimal(5) );
            transactionService.criarTransacao(request);

        });

        Assertions.assertEquals("Transação não autorizada", thrown.getMessage());


    }
}