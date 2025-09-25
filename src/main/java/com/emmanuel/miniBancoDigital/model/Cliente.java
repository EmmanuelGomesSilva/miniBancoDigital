package com.emmanuel.miniBancoDigital.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Gera getters, setters, toString, hashCode, equals
@Entity // Marca a classe como entidade JPA(representa tabela no BD)
@Table(name = "clientes") //Nome da tabela no banco
@AllArgsConstructor // Gera construtor com todos os campo
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //ID auto increment
    private Long id;

    @NotBlank(message = "O campo nome não pode ser vazio") //Validação
    private String nome;

    @NotBlank(message = "O campo cpf não pode ser vazio")
    @Column(unique = true) // Garante que não existam CPFs duplicados no banco
    private String cpf;

    @NotBlank(message = "O campo email não pode ser vazio")
    @Column(unique = true) // Garante que não existam emails duplicados no banco
    private String email;


}
