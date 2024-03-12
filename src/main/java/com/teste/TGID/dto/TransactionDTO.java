package com.teste.TGID.dto;

import java.math.BigDecimal;

public record TransactionDTO( Long remetenteId, Long destinatarioId, BigDecimal value, BigDecimal taxa ){

}
