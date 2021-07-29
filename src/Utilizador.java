
import edu.princeton.cs.algs4.SeparateChainingHashST;

import java.io.Serializable;
import java.util.ArrayList;

public class Utilizador implements Serializable {
    private int id;
    private String nome;
    private Tipo tipo;

    public RedBlackBST2<Date, Cache> historicoCachesEscondidas = new RedBlackBST2<>();
    public RedBlackBST2<Date, Cache> historicoCachesVisitadas = new RedBlackBST2<>();
    public ST2<Integer, TravelBug> travelbugs = new ST2<>();

    /**
     * Inicialmente decidimos usar uma HashTable para os utilizadores, visto que a "chave" seria um username único para cada utilizador,
     * e não seria necessário ordenar os utilizadores por username.
     * Mas mais tarde mudamos a "chave" para um ID único para podermos usar o input fornecido pelos professores.
     */
    public static SeparateChainingHashST<Integer, Utilizador> utilizadoresST = new SeparateChainingHashST<>();

    /**
     * Retorna o ID mais baixo disponivel na ST de Utilizadores
     * @return
     */
    private int uniqueID(){
        int id = 1;
        while(utilizadoresST.contains(id)){
            id++;
        }
        return id;
    }
    public Utilizador(String nome, Tipo tipo) {
        this.nome = nome;
        this.tipo = tipo;

    }

    /**
     * Construtor a ser usado apenas para ler do ficheiro input
     */
    public Utilizador(int id, String nome, Tipo tipo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
    }

    public int getId() { return id; }

    public String getNome() {
        return nome;
    }


    public Tipo getTipo() {
        return tipo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    /**
     * retorna a localizacao de uma cache nao visitada pelo utilizador
     * @return
     */
    public Localizacao obterCacheGPS() {
        if(cachesNaoVisitadasPorRegiao("global").size() > 0)
            return Cache.cachesST.get(cachesNaoVisitadasPorRegiao("global").max()).getLocalizacao();
        System.out.println("Nao ha novas caches para visitar");
        return null;
    }

    /**
     * Adiciona uma cache ao array de caches escondidas pelo utilizador
     * @param cacheID
     */
    public void addCacheEscondida(int cacheID){
        if(!Cache.cachesST.contains(cacheID))
            System.out.println("Nao existe nenhuma cache com esse ID guardada na ST");
        for(Date data : historicoCachesEscondidas.keys()){
            if(historicoCachesEscondidas.get(data).getId() == cacheID){
                System.out.println("Este utilizador ja escondeu essa cache.");
                return;
            }
        }
        historicoCachesEscondidas.put(new Date(), Cache.cachesST.get(cacheID));
    }

    /**
     * Adiciona uma cache ao array de caches visitadas pelo utilizador
     * @param cacheID
     */
    public void addCacheVisitada(int cacheID){
        if(!Cache.cachesST.contains(cacheID))
            System.out.println("Nao existe nenhuma cache com esse ID guardada na ST");
        else {
            for (Date data : historicoCachesVisitadas.keys()) {
                if (historicoCachesVisitadas.get(data).getId() == cacheID) {
                    System.out.println("Este utilizador ja visitou essa cache.");
                    return;
                }
            }
            historicoCachesVisitadas.put(new Date(), Cache.cachesST.get(cacheID));
        }
    }


    /**
     * adiciona um travelbug novo ao array de travelbugs possuidos pelo utilizador
     * @param travelbugID
     */
    public void addTravelbug(int travelbugID){
        if(!travelbugs.contains(travelbugID) && TravelBug.travelBugsBST.contains(travelbugID))
            travelbugs.put(travelbugID, TravelBug.travelBugsBST.get(travelbugID));
        else
            System.out.println("O travelbug ja esta associado a este utilizador, ou nao existe.");
    }

    /**
     * R8.a
     * retorna apenas as caches que o utilizador ja tenha visitado, de uma determinada regiao,
     * ou retorna todas as caches visitadas pelo utilizador, se a regiao for global
     */
    public ST2<Integer, Cache> cachesVisitadasPorRegiao(String regiao){
        ST2<Integer, Cache> caches = new ST2<>();
        for(Date data : historicoCachesVisitadas.keys()){
            if(historicoCachesVisitadas.get(data).getLocalizacao().getRegiao().equals(regiao) || regiao.equals("global")){
                caches.put(historicoCachesVisitadas.get(data).getId(), historicoCachesVisitadas.get(data));
            }
        }
        return caches;
    }


    /**
     * R8.b
     * Retorna todas as caches que o utilizador nao visitou numa determinada regiao,
     * ou retorna todas as caches nao visitadas pelo utilizador se a regiao for global
     */
    public ST2<Integer, Cache> cachesNaoVisitadasPorRegiao(String regiao){
        ST2<Integer, Cache> caches = new ST2<>();
        for(int cacheID : Cache.cachesST.keys()){
            if(!cachesVisitadasPorRegiao(regiao).contains(cacheID)
                    && (Cache.cachesST.get(cacheID).getLocalizacao().getRegiao().equals(regiao) || regiao.equals("global"))){
                caches.put(cacheID, Cache.cachesST.get(cacheID));
            }
        }
        return caches;
    }

    /**
     * Imprime todos os utilizadores guardados na ST, na consola
     */
    public static void printHashSTUtilizadores() {
        System.out.println("\nHash Table de Utilizadores:");
        for (int id : utilizadoresST.keys()) {
            System.out.printf("ID: %5s\t-\t"
                            + "Nome: %15s\t-\t"
                            + "Tipo: %7s\n",
                    utilizadoresST.get(id).id,
                    utilizadoresST.get(id).nome,
                    utilizadoresST.get(id).tipo);
        }
    }

    /**
     * Inserir utilizador na ST estática (global), simulando um servidor
     * Dá um ID unico ao utilizador também
     */
    public void inserirUtilizadorST() {
        if (utilizadoresST.contains(id)) {
            System.out.println("Ja existe um Utilizador com o mesmo ID na ST");
            return;
        }
        if(id <= 0){
            id = uniqueID();
        }
        utilizadoresST.put(id, this);
    }

    /**
     * remover utilizador do array global, retirando-o também de qualquer cache ou travelbug em que se encontre
     */
    public void removerUtilizadorST(){
        if(utilizadoresST.contains(this.id)) {
            utilizadoresST.delete(this.id);
            for(int cacheID : Cache.cachesST.keys()){
                if(Cache.cachesST.get(cacheID).getOwner() != null){
                    if(Cache.cachesST.get(cacheID).getOwner().getId() == this.id)
                        Cache.cachesST.get(cacheID).setOwner(null);
                }

            }
            for(int travelbugID : TravelBug.travelBugsBST.keys()){
                if(TravelBug.travelBugsBST.get(travelbugID).getOwner().getId() == this.id)
                    TravelBug.travelBugsBST.delete(travelbugID);
                else if(TravelBug.travelBugsBST.get(travelbugID).historicoUtilizadores.size() > 0){
                    Date ultimoUserData = TravelBug.travelBugsBST.get(travelbugID).historicoUtilizadores.max();
                    if(TravelBug.travelBugsBST.get(travelbugID).historicoUtilizadores.get(ultimoUserData).getId() == this.id)
                        TravelBug.travelBugsBST.get(travelbugID).historicoUtilizadores.delete(ultimoUserData);
                    if(!TravelBug.travelBugsBST.get(travelbugID).historicoUtilizadores.isEmpty()) {
                        Date novoUltimoUserData = TravelBug.travelBugsBST.get(travelbugID).historicoUtilizadores.max();
                        Utilizador novoUltimoUser = TravelBug.travelBugsBST.get(travelbugID).historicoUtilizadores.get(novoUltimoUserData);
                        if (TravelBug.travelBugsBST.get(travelbugID).getUserMaisRecente().getId() == this.id)
                            TravelBug.travelBugsBST.get(travelbugID).setUserMaisRecente(novoUltimoUser);
                    }
                }

            }
        }else{
            System.out.println("Este Utilizador nao se encontra na ST");
        }
    }


    /**
     * Edit user attributes
     * @param nome (== null to not change)
     * @param tipo (== null to not change)
     */
    public void editUtilizador(String nome, Tipo tipo){
        if(nome != null)
            this.nome = nome;
        if(tipo != null)
            this.tipo = tipo;
        utilizadoresST.put(id, this);
    }

    /*
    public static void writeUtilizadoresToTxt() {
        if(!utilizadoresST.isEmpty()) {
            Out out = new Out(".//data//utilizadores.txt");
            for (int id : utilizadoresST.keys()) {
                out.println(utilizadoresST.get(id).getName() + "\n"
                        + utilizadoresST.get(id).getUsername() + "\n"
                        + utilizadoresST.get(id).getPassword() + "\n"
                        + utilizadoresST.get(id).getTipo());
            }
            out.close();
        }
    }

    */

    @Override
    public String toString() {
        return  "{ID: " + id +
                ", Nome:\"" + nome + '\"' +
                ", Tipo:" + tipo +
                '}';
    }
}

