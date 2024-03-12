package com.teste.TGID.controller;

import com.teste.TGID.dto.TransactionDTO;
import com.teste.TGID.entity.Transaction;
import com.teste.TGID.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> criaTransacao(@RequestBody TransactionDTO transactionDTO ) throws Exception{
        Transaction novaTransacao = this.transactionService.criarTransacao( transactionDTO );

        return new ResponseEntity<>( novaTransacao, HttpStatus.OK );
    }
}
