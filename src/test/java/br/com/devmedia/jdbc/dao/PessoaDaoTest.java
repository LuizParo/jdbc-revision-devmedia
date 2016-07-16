package br.com.devmedia.jdbc.dao;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

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
        this.dao.removeAll();
        JDBCConnection.close(this.connection);
    }
    
    private Pessoa createPerson(String name) {
        try {
            Pessoa person = new Pessoa();
            person.setNome(name);
            person.setProfissao("Administrator");
            person.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse("02/10/1985"));
            return person;
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Test
    public void shouldPersistPersonInDatabase() {
        Pessoa person = this.createPerson("João da Silva");
        
        int generatedId = this.dao.save(person);
        Assert.assertTrue(generatedId > 0);
        Assert.assertNotNull(person.getId());
    }
    
    @Test
    public void shouldFindPersonById() {
        Pessoa person = this.createPerson("João da Silva");
        int generatedId = this.dao.save(person);
        
        Pessoa personRecovered = this.dao.findById(generatedId);
        Assert.assertEquals(Integer.valueOf(generatedId), personRecovered.getId());
    }
    
    @Test
    public void shouldFindPersonByNome() {
        Pessoa person = this.createPerson("João da Silva");
        this.dao.save(person);
        
        Pessoa personRecovered = this.dao.findByNome(person.getNome());
        Assert.assertEquals(person.getNome(), personRecovered.getNome());
    }
    
    @Test
    public void shouldFindAllPeople() {
        this.dao.save(this.createPerson("João da Silva"));
        this.dao.save(this.createPerson("Maria da Silva"));
        this.dao.save(this.createPerson("Fernando da Silva"));
        
        List<Pessoa> people = this.dao.findAll();
        Assert.assertNotNull(people);
        Assert.assertFalse(people.isEmpty());
        Assert.assertEquals(3, people.size());
    }
    
    @Test
    public void shouldFindAllPeopleByProfissao() {
        this.dao.save(this.createPerson("João da Silva"));
        this.dao.save(this.createPerson("Maria da Silva"));
        this.dao.save(this.createPerson("Fernando da Silva"));
        
        List<Pessoa> people = this.dao.findAllByProfissao("Administrator");
        Assert.assertNotNull(people);
        Assert.assertFalse(people.isEmpty());
        Assert.assertEquals(3, people.size());
        for (Pessoa person : people) {
            Assert.assertEquals("Administrator", person.getProfissao());
        }
    }
    
    @Test
    public void shouldUpdatePerson() throws ParseException {
        Pessoa person = this.createPerson("João da Silva");
        this.dao.save(person);
        
        Pessoa personToUpdate = new Pessoa();
        personToUpdate.setId(person.getId());
        personToUpdate.setNome("Pedro Rosa");
        personToUpdate.setProfissao("Systems Analyst");
        personToUpdate.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse("08/06/1989"));
        int update = this.dao.update(personToUpdate);
        
        Pessoa personRecovered = this.dao.findById(person.getId());
        Assert.assertEquals(1, update);
        Assert.assertNotEquals(person.getNome(), personRecovered.getNome());
        Assert.assertNotEquals(person.getProfissao(), personRecovered.getProfissao());
        Assert.assertNotEquals(person.getDataNascimento(), personRecovered.getDataNascimento());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldDeletePerson() {
        Pessoa person = this.createPerson("João da Silva");
        this.dao.save(person);
        
        int rowsRemoved = this.dao.remove(person.getId());
        Assert.assertEquals(1, rowsRemoved);
        Assert.assertNull(this.dao.findById(person.getId()));
    }
}