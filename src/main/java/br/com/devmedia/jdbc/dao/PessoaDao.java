package br.com.devmedia.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.devmedia.jdbc.entity.Pessoa;

public class PessoaDao {
    private Connection connection;

    public PessoaDao(Connection connection) {
        this.connection = connection;
    }
    
    public int save(Pessoa person) {
        try {
            StringBuilder sql = new StringBuilder("INSERT INTO pessoa (nome, profissao, data_nascimento) VALUES (?, ?, ?)");
            
            PreparedStatement statement = this.connection.prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, person.getNome());
            statement.setString(2, person.getProfissao());
            statement.setDate(3, new Date(person.getDataNascimento().getTime()));
            statement.execute();
            
            ResultSet resultSet = statement.getGeneratedKeys();
            while(resultSet.next()) {
                person.setId(resultSet.getInt(1));
                return person.getId();
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    public Pessoa findById(Integer id) {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM pessoa WHERE id = ?");
            PreparedStatement statement = this.connection.prepareStatement(sql.toString());
            statement.setInt(1, id);
            
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                Pessoa person = new Pessoa();
                person.setId(rs.getInt("id"));
                person.setNome(rs.getString("nome"));
                person.setProfissao(rs.getString("profissao"));
                person.setDataNascimento(rs.getDate("data_nascimento"));
                
                return person;
            }
            throw new IllegalArgumentException("No records found for: " + id);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    public Pessoa findByNome(String name) {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM pessoa WHERE nome = ?");
            PreparedStatement statement = this.connection.prepareStatement(sql.toString());
            statement.setString(1, name);
            
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                Pessoa person = new Pessoa();
                person.setId(rs.getInt("id"));
                person.setNome(rs.getString("nome"));
                person.setProfissao(rs.getString("profissao"));
                person.setDataNascimento(rs.getDate("data_nascimento"));
                
                return person;
            }
            throw new IllegalArgumentException("No records found for: " + name);
        } catch(SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    public List<Pessoa> findAll() {
        try {
            List<Pessoa> people = new ArrayList<>();
            StringBuilder sql = new StringBuilder("SELECT * FROM pessoa");
            
            PreparedStatement statement = this.connection.prepareStatement(sql.toString());
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Pessoa person = new Pessoa();
                person.setId(rs.getInt("id"));
                person.setNome(rs.getString("nome"));
                person.setProfissao(rs.getString("profissao"));
                person.setDataNascimento(rs.getDate("data_nascimento"));
                
                people.add(person);
            }
            
            return people;
        } catch(SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public List<Pessoa> findAllByProfissao(String profession) {
        try {
            List<Pessoa> people = new ArrayList<>();
            StringBuilder sql = new StringBuilder("SELECT * FROM pessoa WHERE profissao = ?");
            
            PreparedStatement statement = this.connection.prepareStatement(sql.toString());
            statement.setString(1, profession);
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Pessoa person = new Pessoa();
                person.setId(rs.getInt("id"));
                person.setNome(rs.getString("nome"));
                person.setProfissao(rs.getString("profissao"));
                person.setDataNascimento(rs.getDate("data_nascimento"));
                
                people.add(person);
            }
            
            return people;
        } catch(SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    public int update(Pessoa person) {
        try {
            StringBuilder sql = new StringBuilder("UPDATE pessoa SET nome = ?, profissao = ?, data_nascimento = ? WHERE id = ?");
            PreparedStatement statement = this.connection.prepareStatement(sql.toString());
            statement.setString(1, person.getNome());
            statement.setString(2, person.getProfissao());
            statement.setDate(3, new Date(person.getDataNascimento().getTime()));
            statement.setInt(4, person.getId());
            
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    public int remove(Integer id) {
        StringBuilder sql = new StringBuilder("DELETE FROM pessoa WHERE id = ?");
        try {
            PreparedStatement statement = this.connection.prepareStatement(sql.toString());
            statement.setInt(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    public int removeAll() {
        StringBuilder sql = new StringBuilder("DELETE FROM pessoa");
        try {
            return this.connection.createStatement().executeUpdate(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}