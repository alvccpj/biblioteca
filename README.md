# Biblioteca Cidadã (Java 17)

Este projeto demonstra conceitos de OO com uma aplicação de biblioteca e uma interface de linha de comando (CLI).

## Pilares de OO implementados

- Abstração
  - `Recurso` (`src\br\recife\biblioteca\modelo\Recurso.java`) é abstrata e concentra atributos e validações comuns a qualquer item do acervo. Expõe `calcularMulta(...)`, delegando às subclasses a regra específica.
- Herança e Polimorfismo
  - `Livro`, `Revista` e `MidiaDigital` especializam `Recurso` e sobrescrevem `calcularMulta(...)`. O polimorfismo aparece no cálculo de multa e na descrição (`getDescricao()`).
  - `Usuario` é abstrata; `Aluno`, `Servidor` e `Visitante` especializam e definem `prazoDiasPadrao()` e `fatorMulta()`.
- Encapsulamento
  - Atributos privados com validações em setters/constructores (e.g., título não vazio, ano > 0).
  - Coleções expostas como imutáveis (`List`/`Collection` não modificáveis).
- Interface
  - `Emprestavel` define o contrato de empréstimo (`emprestar`, `devolver`, `getDataPrevistaDevolucao`, `isDisponivel`) e é implementada por `Livro`, `Revista` e `MidiaDigital`.
- Agregação e Composição
  - Agregação: `Biblioteca` (`src\br\recife\biblioteca\servico\Biblioteca.java`) mantém coleções de `Usuario`, `Recurso` e `Emprestimo` sem controlar o ciclo de vida dos objetos (podem existir fora da `Biblioteca`).
  - Composição: `Livro` compõe `Capitulo` — capítulos só fazem sentido no contexto de um `Livro` e têm seu ciclo de vida atrelado.

## Decisões de projeto

- Por que agregação na `Biblioteca`?
  - Usuários e recursos podem ser criados/gerenciados de forma independente e cadastrados/removidos; a `Biblioteca` apenas referencia e organiza essas entidades. Isso favorece reuso, testabilidade e menor acoplamento.
- Por que composição em `Livro -> Capitulo`?
  - Capítulos não possuem identidade útil fora do livro. A composição facilita invariantes (numeração, somatória de páginas) e simplifica o gerenciamento do conteúdo.
- Regras de negócio em pontos específicos
  - `Biblioteca.emprestar(...)` e `Biblioteca.devolver(...)` centralizam regras de disponibilidade e histórico, lançando exceções de domínio (`ItemIndisponivelException`, `EmprestimoNaoEncontradoException`).
  - Diferenças de prazo por tipo: `Livro` usa prazo do usuário, `Revista` antecipa a devolução em 2 dias, `MidiaDigital` limita a 7 dias.

## Como compilar e executar (CLI)

- Requisitos
  - JDK 17 instalado e disponível no terminal (`javac` e `java`).
  - Estar na pasta do projeto: `c:\Users\Alvinho\Desktop\biblioteca`.

- Compilar a CLI (Windows)
  - Compila a classe principal e dependências automaticamente via `-sourcepath`:
  - `javac -encoding UTF-8 -sourcepath src -d bin src\br\recife\biblioteca\ui\BibliotecaCLI.java`

- Executar a CLI
  - `java -cp bin br.recife.biblioteca.ui.BibliotecaCLI`

## Como rodar os testes (JUnit 4)

- Dependências já presentes:
  - `lib\junit-4.13.2.jar`
  - `lib\hamcrest-core-1.3.jar`

- Compilar testes (Windows)
  - `javac -d bin -cp bin;lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar test\br\recife\biblioteca\teste\BibliotecaTest.java`

- Executar testes pelo CLI
  - `java -cp bin;lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar org.junit.runner.JUnitCore br.recife.biblioteca.teste.BibliotecaTest`

- Executar pela IDE (VS Code)
  - Garanta que o `settings.json` contém `"java.project.sourcePaths": ["src", "test"]` e referencia `lib/**/*.jar`.
  - Abra `BibliotecaTest.java` e use “Run Test” (ícone acima da classe/métodos).
- Exceções customizadas para fluxos inválidos (item indisponível ou empréstimo não encontrado).

## Fluxo de Teste (CLI)

- IDs do seed: usuários `U1` (Aluno), `U2` (Servidor), `U3` (Visitante); recursos `R1` (Livro com 3 capítulos), `R2` (Revista), `R3` (MidiaDigital). O seed empresta `R1` para `U1`.
- 1) Listar disponíveis: confirma itens não emprestados.
- 3) Emprestar: `R3` para `U3` → saída “Prevista: yyyy-MM-dd”; confirme voltar ao menu.
- 2) Listar emprestados: deve mostrar `R1` e `R3` enquanto estiverem emprestados.
- 3) Emprestar: `R1` para `U1` → erro “Item já emprestado”; confirme voltar ao menu.
- 4) Devolver: `R1` → saída “Multa: R$ X.YZ”; confirme voltar ao menu.
- 6) Relatório: atrasados: lista empréstimos com atraso (geralmente vazio no mesmo dia).
- 5) Histórico por usuário: informe `U1` para ver registros.
- 7–12) CRUD: cadastre/edite/remova usuários e recursos; todas as ações perguntam se deseja voltar ao menu.
