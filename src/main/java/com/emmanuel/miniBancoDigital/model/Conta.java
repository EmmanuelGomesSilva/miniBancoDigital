package com.emmanuel.miniBancoDigital.model;

import com.emmanuel.miniBancoDigital.enums.TipoConta;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

// Entidade Conta (representa a tabela contas no banco de dados)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contas")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID auto increment
    private Long id;

    @NotBlank(message = "Número da conta é obrigatório")
    @Column(unique = true) // Cada número da conta é único
    private String numero;

    @NotNull
    // Inicializa com saldo em 0
    private BigDecimal saldo = BigDecimal.ZERO;

    @NotBlank(message = "Senha é obrigatório")
    private String senha;

    @NotNull(message = "Tipo de conta é obrigatório")
    @Enumerated(EnumType.STRING)
    private TipoConta tipoConta;

    // Relação ManyToOne com Cliente (uma conta pertence a um cliente)
    @ManyToOne
    @JoinColumn(name = "cliente_id") // FK do banco
    private Cliente cliente;


}
