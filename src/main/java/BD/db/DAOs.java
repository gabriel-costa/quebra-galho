package BD.db;

import BD.core.*;

public class DAOs
{
    private static MeuPreparedStatement bd;

    private static Usuarios us;
    private static Favores fs;

    static
    {
        try
        {
            DAOs.bd = new MeuPreparedStatement (
                      "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                      "jdbc:sqlserver://regulus:1433;databasename=BD15174",
                      "BD15174", "BD15174");

            DAOs.us = new Usuarios();
            DAOs.fs = new Favores();
        }
        catch (Exception erro)
        {
            System.err.println("Problemas de conexao com o BD");
            System.exit(0);
        }
    }

    public static MeuPreparedStatement getBD ()
    {
        return DAOs.bd;
    }

    public static Usuarios getUsuarios()
    {
        return DAOs.us;
    }

    public static Favores getFavores()
    {
        return DAOs.fs;
    }
}