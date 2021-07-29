import java.io.Serializable;
import java.util.ArrayList;

public class Log implements Serializable {
    private Date data;
    private String mensagem;

    public static ArrayList<Log> logsTotal = new ArrayList<>();

    public Log(Date data, String mensagem) {
        this.data = data;
        this.mensagem = mensagem;
    }

    public Date getData() {
        return data;
    }

    public String getMensagem() {
        return mensagem;
    }

    @Override
    public String toString() {
        return  "{Data: " + data +
                ", Mensagem: \"" + mensagem + '\"' +
                '}';
    }
}