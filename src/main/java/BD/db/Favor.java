package BD.db;

import java.util.Objects;
import com.microsoft.windowsazure.mobileservices.table.*;

public class Favor
{
    private String id;
    public String getId() { return id;}
    public final void setID(String idd){ id = idd; }

    private String nome;
    public String getNome() { return nome;}
    public final void setNome(String nomme){ nome = nomme;}

    private String descricao;
    public String getDescricao() { return descricao;}
    public final void setDescricao(String descricaoo){ descricao = descricaoo;}

    private int preco;
    public int getPreco() { return preco;}
    public final void setPreco(int precoo){ preco = precoo;}

    private String userAtivo;
    public String getUserAtivo() { return userAtivo;}
    public final void setUserAtivo(String userrAtivo){ userAtivo = userrAtivo;}

    private String userPassivo;
    public String getUserPassivo() { return userPassivo;}
    public final void setUserPassivo(String userrPassivo){ userPassivo = userrPassivo;}

    private double latitude;
    public double getLatitude() { return latitude;}
    public final void setLatitude(double latiitude){ latitude = latiitude;}

    private double longitude;
    public double getLongitude() { return longitude;}
    public final void setLongitude(double longiitude){ longitude = longiitude;}

}