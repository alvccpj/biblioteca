package br.recife.biblioteca.teste;

import org.junit.Test;
import static org.junit.Assert.*;

import br.recife.biblioteca.*; 
import br.recife.biblioteca.modelo.Aluno;
import br.recife.biblioteca.modelo.Capitulo;
import br.recife.biblioteca.modelo.Livro;
import br.recife.biblioteca.modelo.MidiaDigital;
import br.recife.biblioteca.modelo.Servidor;
import br.recife.biblioteca.modelo.Visitante;
import br.recife.biblioteca.servico.Biblioteca;

public class BibliotecaTest {

    @Test
    public void prazoPolimorfico() {
        assertEquals(14, new Aluno("1","A","1").prazoDiasPadrao());
        assertEquals(21, new Servidor("2","S","2").prazoDiasPadrao());
        assertEquals(7, new Visitante("3","V","3").prazoDiasPadrao());
    }

    @Test
    public void composicaoCapitulos() {
        Livro l = new Livro("L2","Livro",2021);
        l.adicionarCapitulo(new Capitulo(1,"C1",10));
        l.adicionarCapitulo(new Capitulo(2,"C2",8));
        assertEquals(2, l.getCapitulos().size());
    }

    @Test
    public void impedirEmprestarIndisponivel() {
        Biblioteca b = new Biblioteca();
        Livro l = new Livro("R1","X",2020);
        Aluno u = new Aluno("U1","Y","Z");
        b.adicionarRecurso(l);
        b.adicionarUsuario(u);
        b.emprestar("R1","U1");
        assertThrows(RuntimeException.class, () -> b.emprestar("R1","U1"));
    }

    @Test
    public void midiaDigitalPrazoLimitado() {
        MidiaDigital m = new MidiaDigital("M1","Midia",2020,10,"PDF");
        Visitante v = new Visitante("V1","Vi","333");
        assertTrue(m.emprestar(v));
        assertNotNull(m.getDataPrevistaDevolucao());
    }
}


