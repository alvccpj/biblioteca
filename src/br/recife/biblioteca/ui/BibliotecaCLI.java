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
            System.out.println("5) Relatório: atrasados");
            System.out.println("6) Histórico por usuário");
            System.out.println("0) Sair");
            System.out.print("Escolha: ");
            op = Integer.parseInt(sc.nextLine());
            try {
                switch (op) {
                case 1 -> doListarDisponiveis();
                case 2 -> doListarEmprestados();
                case 3 -> doEmprestar();
                case 4 -> doDevolver();
                case 5 -> doAtrasados();
                case 6 -> doHistorico();
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