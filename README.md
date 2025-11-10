/*
# Biblioteca Cidadã (Java 17)


## Como compilar e executar (CLI)
```bash
javac -d out $(find src/main/java -name "*.java")
java -cp out br.recife.biblioteca.ui.BibliotecaCLI
```


## Testes (JUnit 5)
Use sua IDE ou um build simples com javac adicionando o jar do JUnit 5 no classpath.
Exemplo (ajuste caminhos):
```bash
# baixar junit-platform-console-standalone-x.y.z.jar
javac -cp junit-platform-console-standalone-1.11.0.jar:out -d out_test $(find src/test/java -name "*.java")
java -jar junit-platform-console-standalone-1.11.0.jar -cp out:out_test --scan-classpath
```


## Pilares de OO
- **Abstração**: `Recurso` (método abstrato `calcularMulta`) e `Emprestavel` (interface).
- **Herança/Polimorfismo**: `Livro`, `Revista`, `MidiaDigital` especializam `Recurso` e sobrescrevem `calcularMulta`.
- **Encapsulamento**: atributos privados, validações em setters/constructors, coleções expostas como `unmodifiable`.
- **Agregação**: `Biblioteca` mantém coleções de `Usuario` e `Recurso`. `Emprestimo` agrega `Usuario` e `Recurso`.
- **Composição**: `Livro` compõe `Capitulo`.


## Decisões de projeto
- `Recurso` implementa apenas a regra de multa; Estado de empréstimo é tratado pelas classes concretas via `Emprestavel`.
- Exceções customizadas para fluxos inválidos (item indisponível ou empréstimo não encontrado).


## Casos de uso mínimos
- Seed no CLI cria 3 usuários (Aluno, Servidor, Visitante) e 3 recursos (Livro com 3 capítulos, Revista, MidiaDigital).
- Um empréstimo inicial é efetuado (Livro para Aluno). Use o menu para devolver e ver multa ao atrasar (simule alterando datas no código, se necessário).
*/