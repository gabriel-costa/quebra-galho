package BD.db;

import java.sql.*;
import BD.core.*;

public class Usuarios
{
    public boolean existeUsuario(String email) throws Exception
    {
        boolean retorno = false;

        try
        {
            String sql;

            sql = "select * from usuarios where emailUser=?";

            DAOs.getBD().prepareStatement (sql);

            DAOs.getBD().setString(1, email);

            MeuResultSet resultado = (MeuResultSet)DAOs.getBD().executeQuery();

            retorno = resultado.first();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao ver se o usu�rio existe!");
        }

        return retorno;
    }

    public void cadastrar(Usuario u) throws Exception
    {
        if (u==null)
            throw new Exception ("Usu�rio n�o fornecido!");

        try
        {
            String sql;

            sql = "insert into usuarios values(?,?,?,?)";

            DAOs.getBD().prepareStatement (sql);

            DAOs.getBD().setString (1, u.getEmail());
            DAOs.getBD().setInt    (2, u.getCreditos());
            DAOs.getBD().setInt    (3, u.getReputacao());
            DAOs.getBD().setString (4, u.getDescricao());

            DAOs.getBD().executeUpdate();
            DAOs.getBD().commit();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao cadastrar usu�rio!");
        }
    }

    public void excluir(String email) throws Exception
    {
        if (!existeUsuario(email))
            throw new Exception ("Usu�rio n�o cadastrado!");

        try
        {
            String sql;

            sql = "delete from usuarios where emailUser=?";

            DAOs.getBD().prepareStatement (sql);

            DAOs.getBD().setString(1, email);

            DAOs.getBD().executeUpdate ();
            DAOs.getBD().commit        ();        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao excluir usu�rio!");
        }
    }

    public void alterar(Usuario u) throws Exception
    {
        if (u==null)
            throw new Exception ("Usu�rio n�o fornecido!");

        if (!existeUsuario(u.getEmail()))
            throw new Exception ("Usu�rio n�o cadastrado!");

        try
        {
            String sql;

            sql = "update usuarios set creditos=?, reputacao=?, descricao=? where emailUser";

            DAOs.getBD().prepareStatement (sql);

            DAOs.getBD().setInt    (1, u.getCreditos());
            DAOs.getBD().setInt    (2, u.getReputacao());
            DAOs.getBD().setString (3, u.getDescricao());
            DAOs.getBD().setString (4, u.getEmail());

            DAOs.getBD().executeUpdate();
            DAOs.getBD().commit();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao alterar usu�rio!");
        }
    }

    public Usuario getUsuario(String email) throws Exception
    {
        Usuario u = null;

        try
        {
            String sql;

            sql = "select * from usuarios where emailUser=?";

            DAOs.getBD().prepareStatement (sql);

            DAOs.getBD().setString(1, email);

            MeuResultSet resultado = (MeuResultSet)DAOs.getBD().executeQuery();

            if (!resultado.first())
                throw new Exception ("N�o cadastrado");

            u = new Usuario();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao procurar usu�rio");
        }

        return u;
    }

    public MeuResultSet getUsuarios() throws Exception
    {
        MeuResultSet resultado = null;

        try
        {
            String sql;

            sql = "select * from usuarios";

            DAOs.getBD().prepareStatement (sql);

            resultado = (MeuResultSet)DAOs.getBD().executeQuery ();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao recuperar usu�rios");
        }

        return resultado;
    }
}