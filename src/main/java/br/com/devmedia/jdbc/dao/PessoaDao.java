package br.com.devmedia.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.devmedia.jdbc.entity.Pessoa;

public class PessoaDao {
    private Connection connection;

    public PessoaDao(Connection connection) {
        this.connection = connection;
    }
    
    public int save(Pessoa pessoa) {
        try {
            StringBuilder sql = new StringBuilder("INSERT INTO pessoa (nome, profissao, data_nascimento) VALUES (?, ?, ?)");
            
            PreparedStatement statement = this.connection.prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, pessoa.getNome());
            statement.setString(2, pessoa.getProfissao());
            statement.setDate(3, new Date(pessoa.getDataNascimento().getTime()));
            statement.execute();
            
            ResultSet resultSet = statement.getGeneratedKeys();
            while(resultSet.next()) {
                return resultSet.getInt(1);
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
                Pessoa pessoa = new Pessoa();
                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setProfissao(rs.getString("profissao"));
                pessoa.setDataNascimento(rs.getDate("data_nascimento"));
                
                return pessoa;
            }
            throw new IllegalArgumentException("No records found for: " + id);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    public Pessoa findByNome(String nome) {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM pessoa WHERE nome = ?");
            PreparedStatement statement = this.connection.prepareStatement(sql.toString());
            statement.setString(1, nome);
            
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setProfissao(rs.getString("profissao"));
                pessoa.setDataNascimento(rs.getDate("data_nascimento"));
                
                return pessoa;
            }
            throw new IllegalArgumentException("No records found for: " + nome);
        } catch(SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}