package BD.db;

import java.sql.*;
import BD.core.*;

public class Favores
{
    public boolean existeFavor(int codFavor) throws Exception
    {
        boolean retorno = false;

        try
        {
            String sql;

            sql = "select * from favores where codFavor=?";

            DAOs.getBD().prepareStatement (sql);

            DAOs.getBD().setInt(1, codFavor);

            MeuResultSet resultado = (MeuResultSet)DAOs.getBD().executeQuery();

            retorno = resultado.first();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao verificar se o favor existe!");
        }

        return retorno;
    }

    public void cadastrar(Favor f) throws Exception
    {
        if (f==null)
            throw new Exception ("Favor n�o fornecido!");

        try
        {
            String sql;

            sql = "insert into favores values(?,?,?,?,?,?,?,?)";

            DAOs.getBD().prepareStatement (sql);

            /*DAOs.getBD().setInt    (1, f.getCodFavor());
            DAOs.getBD().setString (2, f.getNome());
            DAOs.getBD().setString (3, f.getPreco());
            DAOs.getBD().setString (5, f.getDescricao());
            DAOs.getBD().setInt    (4, f.getUserAtivo());
            DAOs.getBD().setString (6, f.getUserPassivo());
            DAOs.getBD().setFloat  (7, f.getLatitude());
            DAOs.getBD().setFloat  (8, f.getLongitude());*/

            DAOs.getBD().executeUpdate();
            DAOs.getBD().commit();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao cadastrar favor!");
        }
    }

    public void excluir(int codFavor) throws Exception
    {
        if (!existeFavor(codFavor))
            throw new Exception ("Favor n�o cadastrado!");

        try
        {
            String sql;

            sql = "delete from Favores where codFavor=?";

            DAOs.getBD().prepareStatement (sql);

            DAOs.getBD().setInt(1, codFavor);

            DAOs.getBD().executeUpdate ();
            DAOs.getBD().commit        ();        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao excluir favor!");
        }
    }

    public void alterar(Favor f) throws Exception
    {
        if (f==null)
            throw new Exception ("Favor n�o fornecido!");

       /* if (!existeFavor(f.getCodFavor()))
            throw new Exception ("Favor n�o cadastrado!");*/

        try
        {
            String sql;

            sql = "update Favores set nome=?, descricao=?, preco=?, userAtivo=?, userPassivo=?, latitude=?, longitude=? where codFavor=?";

            DAOs.getBD().prepareStatement (sql);

            /*DAOs.getBD().setString (1, f.getNome());
            DAOs.getBD().setString (2, f.getDescricao());
            DAOs.getBD().setInt    (3, f.getPreco());
            DAOs.getBD().setString (4, f.getUserAtivo());
            DAOs.getBD().setString (5, f.getUserPassivo());
            DAOs.getBD().setFloat  (6, f.getLatitude());
            DAOs.getBD().setFloat  (7, f.getLongitude());
            DAOs.getBD().setInt    (8, f.getId());*/

            DAOs.getBD().executeUpdate();
            DAOs.getBD().commit();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao alterar favor!");
        }
    }

    public Favor getFavor(int codFavor) throws Exception
    {
        Favor f = null;

        try
        {
            String sql;

            sql = "select * from Favores where codFavor=?";

            DAOs.getBD().prepareStatement (sql);

            DAOs.getBD().setInt(1, codFavor);

            MeuResultSet resultado = (MeuResultSet)DAOs.getBD().executeQuery();

            if (!resultado.first())
                throw new Exception ("N�o cadastrado");

            f = new Favor();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao procurar favor");
        }

        return f;
    }

    public MeuResultSet getFavores() throws Exception
    {
        MeuResultSet resultado = null;

        try
        {
            String sql;

            sql = "select * from Favores";

            DAOs.getBD().prepareStatement (sql);

            resultado = (MeuResultSet)DAOs.getBD().executeQuery ();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao recuperar favores");
        }

        return resultado;
    }
}