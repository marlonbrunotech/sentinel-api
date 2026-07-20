# Sentinel API - Asset Inventory

Sentinel API é a evolução do projeto Sentinel - Asset Inventory para uma API REST desenvolvida com Spring Boot.

O projeto começou como uma aplicação Java via terminal, utilizando JDBC e PostgreSQL, e agora está sendo migrado gradualmente para uma arquitetura backend mais próxima de aplicações reais, utilizando Spring Boot, Spring Web, Spring Data JPA, Hibernate e PostgreSQL.

Nesta fase, o objetivo principal foi transformar o CRUD de ativos de TI, antes executado via terminal, em endpoints REST acessíveis por requisições HTTP.

---

## Sobre o projeto

O Sentinel simula um sistema utilizado por um departamento de tecnologia para controlar computadores, notebooks, servidores e outros equipamentos de uma organização.

A aplicação permite cadastrar, listar, buscar, atualizar e remover ativos de TI, armazenando os dados em um banco PostgreSQL.

Esta versão mantém o mesmo contexto da fase anterior, mas substitui a interação via terminal por uma API REST, preparando o projeto para futuras integrações com frontend, autenticação, dashboards e funcionalidades relacionadas à Segurança da Informação.

---

## Relação com a versão anterior

A primeira versão do Sentinel foi desenvolvida como uma aplicação console em Java, utilizando JDBC para comunicação direta com o banco de dados.

Repositório da fase anterior:

```text
https://github.com/marlonbrunotech/asset-inventory