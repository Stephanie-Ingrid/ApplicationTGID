package com.teste.TGID.service;


import com.teste.TGID.dto.TransactionDTO;
import com.teste.TGID.entity.Transaction;
import com.teste.TGID.entity.Usuario;
import com.teste.TGID.exception.TransacaoNaoAutorizadaException;
import com.teste.TGID.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private NotificationService notificationService;


    public TransactionService(UsuarioService usuarioService, TransactionRepository transactionRepository, NotificationService notificationService) {
        this.usuarioService = usuarioService;
        this.transactionRepository = transactionRepository;
        this.notificationService = notificationService;
    }

    public Transaction criarTransacao(TransactionDTO transactionDTO) throws Exception {
        Usuario remetente = usuarioService.findUserById(transactionDTO.remetenteId());
        Usuario destinatario = usuarioService.findUserById(transactionDTO.destinatarioId());

        usuarioService.validaTransacao(remetente, transactionDTO.value());

        if (!authorizationService.authorizeTransaction(remetente, transactionDTO.value())) {
            throw new TransacaoNaoAutorizadaException("Transação não autorizada.");
        }

        Transaction transaction = buildTransaction(remetente, destinatario, transactionDTO);
        saveTransactionAndUsers(transaction, remetente, destinatario);
        sendNotifications(remetente, transaction);

        return transaction;
    }


    private Transaction buildTransaction(Usuario remetente, Usuario destinatario, TransactionDTO transactionDTO) {
        BigDecimal valorTransferencia = transactionDTO.value();
        BigDecimal taxa = valorTransferencia.multiply(BigDecimal.valueOf(0.05));
        BigDecimal valorComTaxa = valorTransferencia.add(taxa);

        Transaction transaction = new Transaction();
        transaction.setValorTransferencia(valorComTaxa);
        transaction.setTaxa(taxa);
        transaction.setRemetente(remetente);
        transaction.setDestinatario(destinatario);
        transaction.setDataHora(LocalDateTime.now());

        remetente.setSaldo(remetente.getSaldo().subtract(valorComTaxa));
        destinatario.setSaldo(destinatario.getSaldo().add(valorTransferencia));

        return transaction;
    }

    private void saveTransactionAndUsers(Transaction transaction, Usuario remetente, Usuario destinatario) {
        transactionRepository.save(transaction);
        usuarioService.salvarUsuario(remetente);
        usuarioService.salvarUsuario(destinatario);
    }

    private void sendNotifications(Usuario empresa,Transaction transaction) throws Exception {
        notificationService.envioNotificacao(empresa, String.valueOf(transaction));
    }


}
