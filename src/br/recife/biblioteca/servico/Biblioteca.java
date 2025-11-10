package br.recife.biblioteca.servico;

import br.recife.biblioteca.excecao.EmprestimoNaoEncontradoException;
import br.recife.biblioteca.excecao.ItemIndisponivelException;
import br.recife.biblioteca.modelo.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Biblioteca {
    private final Map<String, Usuario> usuarios = new HashMap<>();
    private final Map<String, Recurso> recursos = new HashMap<>();
    private final List<Emprestimo> emprestimos = new ArrayList<>();

    public void adicionarUsuario(Usuario u) { usuarios.put(u.getId(), u); }
    public void removerUsuario(String id) { usuarios.remove(id); }
    public Optional<Usuario> buscarUsuario(String id) { return Optional.ofNullable(usuarios.get(id)); }

    public void adicionarRecurso(Recurso r) { recursos.put(r.getId(), r); }
    public void removerRecurso(String id) { recursos.remove(id); }
    public Optional<Recurso> buscarRecurso(String id) { return Optional.ofNullable(recursos.get(id)); }

    public List<Recurso> listarDisponiveis() {
        return recursos.values().stream()
            .filter(r -> (r instanceof Emprestavel e) && e.isDisponivel())
            .collect(Collectors.toUnmodifiableList());
    }

    public List<Recurso> listarEmprestados() {
        return recursos.values().stream()
            .filter(r -> (r instanceof Emprestavel e) && !e.isDisponivel())
            .collect(Collectors.toUnmodifiableList());
    }

    public Emprestimo emprestar(String idRecurso, String idUsuario) {
        Recurso r = recursos.get(idRecurso);
        Usuario u = usuarios.get(idUsuario);
        if (r == null || u == null) throw new IllegalArgumentException("recurso/usuario inexistente");
        if (!(r instanceof Emprestavel e)) throw new IllegalArgumentException("recurso não emprestável");
        if (!e.isDisponivel()) throw new ItemIndisponivelException("Item já emprestado");

        boolean ok = e.emprestar(u);
        if (!ok) throw new ItemIndisponivelException("Falha no empréstimo");
        Emprestimo emp = new Emprestimo(r, u, LocalDate.now(), e.getDataPrevistaDevolucao());
        emprestimos.add(emp);
        return emp;
    }

    public Emprestimo devolver(String idRecurso) {
        Recurso r = recursos.get(idRecurso);
        if (r == null) throw new IllegalArgumentException("recurso inexistente");
        if (!(r instanceof Emprestavel e)) throw new IllegalArgumentException("recurso não emprestável");

        Emprestimo emp = emprestimos.stream()
        .filter(x -> x.getRecurso().equals(r) && x.getStatus()== Emprestimo.Status.ATIVO)
        .findFirst().orElseThrow(() -> new EmprestimoNaoEncontradoException("Empréstimo ativo não encontrado"));

         e.devolver(LocalDate.now());
        emp.finalizarDevolucao(LocalDate.now());
        return emp;
    }

    public List<Emprestimo> emprestimosAtrasados(LocalDate dataBase) {
        return emprestimos.stream()
            .filter(e -> e.getStatus()== Emprestimo.Status.ATIVO && e.getDataPrevista().isBefore(dataBase))
            .collect(Collectors.toUnmodifiableList());
    }

    public List<Emprestimo> historicoPorUsuario(String idUsuario) {
        return emprestimos.stream()
            .filter(e -> e.getUsuario().getId().equals(idUsuario))
            .collect(Collectors.toUnmodifiableList());
    }

    public List<Emprestimo> getEmprestimos() { return Collections.unmodifiableList(emprestimos); }
    public Collection<Usuario> getUsuarios() { return Collections.unmodifiableCollection(usuarios.values()); }
    public Collection<Recurso> getRecursos() { return Collections.unmodifiableCollection(recursos.values()); }
}