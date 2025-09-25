# Mini Banco Digital

CRUD de clientes para um mini banco digital, feito com Spring Boot, JPA e H2.

## Tecnologias
- Java 21
- Spring Boot (Web, JPA, Validation)
- Lombok
- H2 Database

## Funcionalidades
- Criar cliente (com validação de campos obrigatórios)
- Listar clientes (ordenados pelo nome)
- Atualizar cliente (verifica existência e duplicidade de CPF/email)
- Deletar cliente (verifica existência)
- Tratamento global de erros com mensagens amigáveis

## Como rodar
1. Clonar o repositório
2. Importar no IntelliJ ou Eclipse
3. Rodar a aplicação (`MiniBancoDigitalApplication`)
4. Testar endpoints usando Postman ou Insomnia

