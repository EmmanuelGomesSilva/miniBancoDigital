package com.emmanuel.miniBancoDigital.model;

import com.emmanuel.miniBancoDigital.enums.TipoTransacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// Entidade Transacao (registra todas as operações feitas no banco)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transacoes")
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único de transação

    @ManyToOne
    @JoinColumn(name = "conta_origem_id")
    // Conta de onde saiu o dinheiro (para saque e transferência)
    private Conta contaOrigem;

    @ManyToOne
    @JoinColumn(name = "conta_destino_id")
    // Conta que recebe o dinheiro (para depósito e transferência)
    private Conta contaDestino;

    @NotNull
    private BigDecimal valor; // Valor da transação

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoTransacao tipoTransacao; // enum: DEPOSITO, SAQUE, TRASNFERENCIA


    private LocalDateTime dataHora = LocalDateTime.now(); // Registra quando a transação ocorreu

}
