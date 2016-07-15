package br.com.devmedia.jdbc.dao;

import java.sql.Connection;
import java.util.Calendar;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.devmedia.jdbc.connection.JDBCConnection;
import br.com.devmedia.jdbc.entity.Pessoa;

public class PessoaDaoTest {
    private Connection connection;
    private PessoaDao dao;

    @Before
    public void setUp() {
        this.connection = JDBCConnection.getConnection();
        this.dao = new PessoaDao(this.connection);
    }
    
    @After
    public void tearDown() {
        JDBCConnection.close(this.connection);
    }
    
    private Pessoa createPerson() {
        Calendar dataNascimentoCalendar = Calendar.getInstance();
        dataNascimentoCalendar.set(Calendar.DAY_OF_MONTH, 2);
        dataNascimentoCalendar.set(Calendar.MONTH, Calendar.OCTOBER);
        dataNascimentoCalendar.set(Calendar.YEAR, 1985);
        
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("João da Silva");
        pessoa.setProfissao("Administrator");
        pessoa.setDataNascimento(dataNascimentoCalendar.getTime());
        return pessoa;
    }

    @Test
    public void shouldPersistPersonInDatabase() {
        Pessoa pessoa = this.createPerson();
        
        int generatedId = this.dao.save(pessoa);
        Assert.assertTrue(generatedId > 0);
    }
    
    @Test
    public void shouldFindPersonById() {
        Pessoa pessoa = this.createPerson();
        int generatedId = this.dao.save(pessoa);
        
        Pessoa pessoaRecuperada = this.dao.findById(generatedId);
        Assert.assertEquals(Integer.valueOf(generatedId), pessoaRecuperada.getId());
    }
    
    @Test
    public void shouldFindPersonByNome() {
        Pessoa pessoa = this.createPerson();
        this.dao.save(pessoa);
        
        Pessoa pessoaRecuperada = this.dao.findByNome(pessoa.getNome());
        Assert.assertEquals(pessoa.getNome(), pessoaRecuperada.getNome());
    }
}