# Biblioteca Cidadã (Java 17)

Este projeto demonstra conceitos de OO com uma aplicação de biblioteca e uma interface de linha de comando (CLI).

## Executar a BibliotecaCLI

- Requisitos
  - JDK instalado e disponível no terminal (`javac` e `java`).
  - Estar na pasta do projeto: `c:\Users\Suporte\OneDrive\Área de Trabalho\Biblioteca`.

- Compilar (Windows)
  - `javac -encoding UTF-8 -sourcepath src -d bin src\br\recife\biblioteca\ui\BibliotecaCLI.java`

- Executar
  - `java -cp bin br.recife.biblioteca.ui.BibliotecaCLI`

- Executar pela interface da IDE (Trae)
  - Abra `src\br\recife\biblioteca\ui\BibliotecaCLI.java` e execute a classe diretamente pela IDE.
  - O menu interativo será exibido; escolha as opções 1–6 ou `0` para sair.

## Testes (JUnit 5)

- Este repositório inclui testes em `test\br\recife\biblioteca\teste` usando JUnit 5.
- Para executá-los, adicione os JARs do JUnit 5 ao classpath ou configure Maven/Gradle. Se desejar, podemos fornecer os comandos com JUnit standalone.

## Pilares de OO

- Abstração: `Recurso` (método abstrato `calcularMulta`) e `Emprestavel` (interface).
- Herança/Polimorfismo: `Livro`, `Revista`, `MidiaDigital` especializam `Recurso` e sobrescrevem `calcularMulta`.
- Encapsulamento: atributos privados, validações em setters/constructors, coleções expostas como imutáveis.
- Agregação: `Biblioteca` mantém coleções de `Usuario` e `Recurso`. `Emprestimo` agrega `Usuario` e `Recurso`.
- Composição: `Livro` compõe `Capitulo`.

## Decisões de projeto

- `Recurso` concentra regras de descrição e multa; estado de empréstimo é tratado pelas classes concretas via `Emprestavel`.
- Exceções customizadas para fluxos inválidos (item indisponível ou empréstimo não encontrado).

## Casos de uso mínimos

- O seed do CLI cria 3 usuários (Aluno, Servidor, Visitante) e 3 recursos (Livro com 3 capítulos, Revista, MidiaDigital).
- Um empréstimo inicial é efetuado (Livro para Aluno). Use o menu para devolver e ver multa quando houver atraso.