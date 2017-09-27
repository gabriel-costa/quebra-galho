package BD.db;

import android.os.Build;
import java.util.*;
import com.microsoft.windowsazure.mobileservices.table.*;

public class Usuario {

        private String id;
        public String getId() { return id;}
        public final void setID(String idd){ id = idd; }

        private String email;
        public String getEmail() { return email;}
        public final void setEmail(String emmail){ email = emmail;}

        private int creditos;
        public int getCreditos() { return creditos;}
        public final void setCreditos(int crreditos){ creditos = crreditos;}

        private int reputacao;
        public int getReputacao() { return reputacao;}
        public final void setReputacao(int rreputacao){ reputacao = rreputacao;}

        private String descricao;
        public String getDescricao() { return descricao;}
        public final void setDescricao(String descricaoo){ descricao = descricaoo;}
}