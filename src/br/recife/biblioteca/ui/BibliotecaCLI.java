package br.recife.biblioteca.ui;

import br.recife.biblioteca.modelo.*;
import br.recife.biblioteca.servico.Biblioteca;

import java.util.Scanner;

public class BibliotecaCLI {
    private final Biblioteca bib = new Biblioteca();
    private final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) { new BibliotecaCLI().run(); }

    private void run() {
        seed(); 
        int op;
        do {
            System.out.println("\n=== Biblioteca Cidadã ===");
            System.out.println("1) Listar disponíveis");
            System.out.println("2) Listar emprestados");
            System.out.println("3) Emprestar");
            System.out.println("4) Devolver");
            System.out.println("5) Histórico por usuário");
            System.out.println("6) Relatório: atrasados");
            // novas opções de CRUD
            System.out.println("7) Cadastrar usuário");
            System.out.println("8) Editar usuário");
            System.out.println("9) Remover usuário");
            System.out.println("10) Cadastrar recurso");
            System.out.println("11) Editar recurso");
            System.out.println("12) Remover recurso");
            System.out.println("0) Sair");
            System.out.print("Escolha: ");
            op = Integer.parseInt(sc.nextLine());
            try {
                switch (op) {
                case 1 -> doListarDisponiveis();
                case 2 -> doListarEmprestados();
                case 3 -> doEmprestar();
                case 4 -> doDevolver();
                case 5 -> doHistorico();
                case 6 -> doAtrasados();
                case 7 -> doCadastrarUsuario();
                case 8 -> doEditarUsuario();
                case 9 -> doRemoverUsuario();
                case 10 -> doCadastrarRecurso();
                case 11 -> doEditarRecurso();
                case 12 -> doRemoverRecurso();
                }
            } catch (Exception ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
        } while (op != 0);
    }

    private void doEmprestar() {
        System.out.print("ID Recurso: ");
        String idR = sc.nextLine();
        System.out.print("ID Usuário: ");
        String idU = sc.nextLine();
        var emp = bib.emprestar(idR, idU);
        System.out.println("Emprestado! Prevista: " + emp.getDataPrevista());
    }

    private void doDevolver() {
        System.out.print("ID Recurso: ");
        String idR = sc.nextLine();
        var emp = bib.devolver(idR);
        System.out.printf("Devolvido. Multa: R$ %.2f\n", emp.getMultaCalculada());
    }

    private void doHistorico() {
        System.out.print("ID Usuário: ");
        String idU = sc.nextLine();
        bib.historicoPorUsuario(idU).forEach(System.out::println);
        confirmarOuEncerrar();
    }

    private void doListarDisponiveis() {
        bib.listarDisponiveis().forEach(System.out::println);
        confirmarOuEncerrar();
    }

    private void doListarEmprestados() {
        bib.listarEmprestados().forEach(System.out::println);
        confirmarOuEncerrar();
    }

    private void doAtrasados() {
        bib.emprestimosAtrasados(java.time.LocalDate.now()).forEach(System.out::println);
        confirmarOuEncerrar();
    }

    private void doCadastrarUsuario() {
        System.out.println("Tipo de usuário (A=Aluno, S=Servidor, V=Visitante): ");
        String tipo = sc.nextLine().trim().toUpperCase();
        System.out.print("ID: "); String id = sc.nextLine().trim();
        System.out.print("Nome: "); String nome = sc.nextLine().trim();
        System.out.print("Documento: "); String doc = sc.nextLine().trim();
        Usuario u = switch (tipo) {
            case "A" -> new Aluno(id, nome, doc);
            case "S" -> new Servidor(id, nome, doc);
            case "V" -> new Visitante(id, nome, doc);
            default -> { System.out.println("Tipo inválido"); yield null; }
        };
        if (u != null) {
            bib.adicionarUsuario(u);
            System.out.println("Usuário cadastrado: " + u);
        }
        confirmarOuEncerrar();
    }

    private void doCadastrarRecurso() {
        System.out.println("Tipo de recurso (L=Livro, R=Revista, M=MidiaDigital): ");
        String tipo = sc.nextLine().trim().toUpperCase();
        System.out.print("ID: "); String id = sc.nextLine().trim();
        System.out.print("Título: "); String titulo = sc.nextLine().trim();
        System.out.print("Ano publicação: "); int ano = Integer.parseInt(sc.nextLine().trim());
        Recurso r = switch (tipo) {
            case "L" -> new Livro(id, titulo, ano);
            case "R" -> {
                System.out.print("Edição: "); String ed = sc.nextLine().trim();
                System.out.print("Periodicidade: "); String per = sc.nextLine().trim();
                yield new Revista(id, titulo, ano, ed, per);
            }
            case "M" -> {
                System.out.print("Tamanho (MB): "); double tam = Double.parseDouble(sc.nextLine().trim());
                System.out.print("Tipo de arquivo: "); String tipoArq = sc.nextLine().trim();
                yield new MidiaDigital(id, titulo, ano, tam, tipoArq);
            }
            default -> { System.out.println("Tipo inválido"); yield null; }
        };
        if (r != null) {
            bib.adicionarRecurso(r);
            System.out.println("Recurso cadastrado: " + r.getDescricao());
        }
        confirmarOuEncerrar();
    }

    private void doRemoverUsuario() {
        System.out.print("ID do usuário: ");
        String id = sc.nextLine().trim();
        bib.removerUsuario(id);
        System.out.println("Usuário removido (se existia).");
        confirmarOuEncerrar();
    }

    private void doRemoverRecurso() {
        System.out.print("ID do recurso: ");
        String id = sc.nextLine().trim();
        bib.removerRecurso(id);
        System.out.println("Recurso removido (se existia).");
        confirmarOuEncerrar();
    }

    private void doEditarUsuario() {
        System.out.print("ID do usuário: ");
        String id = sc.nextLine().trim();
        var opt = bib.buscarUsuario(id);
        if (opt.isEmpty()) {
            System.out.println("Usuário não encontrado.");
        } else {
            Usuario u = opt.get();
            System.out.print("Novo nome (enter para manter): ");
            String nome = sc.nextLine();
            if (!nome.isBlank()) u.setNome(nome);
            System.out.print("Novo documento (enter para manter): ");
            String doc = sc.nextLine();
            if (!doc.isBlank()) u.setDocumento(doc);
            System.out.println("Atualizado: " + u);
        }
        confirmarOuEncerrar();
    }

    private void doEditarRecurso() {
        System.out.print("ID do recurso: ");
        String id = sc.nextLine().trim();
        var opt = bib.buscarRecurso(id);
        if (opt.isEmpty()) {
            System.out.println("Recurso não encontrado.");
        } else {
            Recurso r = opt.get();
            System.out.print("Novo título (enter para manter): ");
            String titulo = sc.nextLine();
            if (!titulo.isBlank()) r.setTitulo(titulo);
            System.out.print("Novo ano (enter para manter): ");
            String anoStr = sc.nextLine().trim();
            if (!anoStr.isBlank()) r.setAnoPublicacao(Integer.parseInt(anoStr));
            System.out.println("Atualizado: " + r.getDescricao());
        }
        confirmarOuEncerrar();
    }

    private void confirmarOuEncerrar() {
        String resp;
        do {
            System.out.print("Deseja voltar ao menu principal? (s/n): ");
            resp = sc.nextLine().trim().toLowerCase();
        } while (!resp.equals("s") && !resp.equals("n"));
        if (resp.equals("n")) {
            System.out.println("Encerrando aplicação...");
            System.exit(0);
        }
    }

    private void seed() {
        Usuario aluno = new Aluno("U1", "Ana Aluna", "111");
        Usuario servidor = new Servidor("U2", "Sergio Servidor", "222");
        Usuario visitante = new Visitante("U3", "Vera Visitante", "333");
        bib.adicionarUsuario(aluno);
        bib.adicionarUsuario(servidor);
        bib.adicionarUsuario(visitante);

        Livro l = new Livro("R1", "POO em Java", 2023);
        l.adicionarCapitulo(new Capitulo(1, "Introdução", 12));
        l.adicionarCapitulo(new Capitulo(2, "Classes e Objetos", 30));
        l.adicionarCapitulo(new Capitulo(3, "Herança e Polimorfismo", 28));
        Revista rev = new Revista("R2", "Revista Ciência", 2024, "Edição 102", "Mensal");
        MidiaDigital mid = new MidiaDigital("R3", "Algoritmos em Áudio", 2022, 120.0, "MP3");
        bib.adicionarRecurso(l);
        bib.adicionarRecurso(rev);
        bib.adicionarRecurso(mid);

        bib.emprestar("R1", "U1");
    }
}