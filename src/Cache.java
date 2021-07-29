import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;

import java.io.Serializable;
import java.util.ArrayList;

public class Cache implements Serializable{

    private int id;
    private Utilizador owner;
    private Localizacao localizacao;
    private int dificuldade;
    private int terreno;
    private Tipo tipo;
    private ArrayList<Item> items = new ArrayList<>();
    private RedBlackBST2<Date, Log> logBook = new RedBlackBST2<>();

    public static RedBlackBST2<Integer, Cache> cachesST = new RedBlackBST2<>();

    /**
     * Retorna o ID mais baixo disponivel na ST de caches
     * @return
     */
    private int uniqueID(){
        int id = 1;
        while(cachesST.contains(id)){
            id++;
        }
        return id;
    }

    public Cache(Tipo tipo, Localizacao localizacao, int dificuldade, int terreno){
        this.tipo = tipo;
        this.localizacao = localizacao;
        this.dificuldade = dificuldade;
        this.terreno = terreno;
        if(Localizacao.regioes.contains(localizacao.getRegiao()))
            Localizacao.regioes.put(localizacao.getRegiao(), Localizacao.regioes.get(localizacao.getRegiao())+1);
        else
            Localizacao.regioes.put(localizacao.getRegiao(), 1);
    }

    /**
     * Construtor a ser usado apenas para ler do ficheiro input
     */
    public Cache(int id, Tipo tipo, Localizacao localizacao, int dificuldade, int terreno) {
        this.id = id;
        this.tipo = tipo;
        this.localizacao = localizacao;
        this.dificuldade = dificuldade;
        this.terreno = terreno;
        if(Localizacao.regioes.contains(localizacao.getRegiao()))
            Localizacao.regioes.put(localizacao.getRegiao(), Localizacao.regioes.get(localizacao.getRegiao())+1);
        else
            Localizacao.regioes.put(localizacao.getRegiao(), 1);
    }

    public int getId() { return id; }

    public Utilizador getOwner() {
        return owner;
    }

    public void setOwner(Utilizador owner){
        this.owner = owner;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public void setDificuldade(int dificuldade) {
        this.dificuldade = dificuldade;
    }

    public void setTerreno(int terreno) {
        this.terreno = terreno;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }


    public int getDificuldade() {
        return dificuldade;
    }


    public int getTerreno() {
        return terreno;
    }


    public Tipo getTipo() {
        return tipo;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void addItem(Item item){
        this.items.add(item);
    }

    /** Remover um item da cache
     * @param itemName ("travelbug" if it is a TravelBug)
     * @param travelbugID (0 if it is not a TravelBug)
     */
    public void removeItem(String itemName, int travelbugID){

        for(Item item : items){
            if(itemName.compareTo("travelbug") == 0){
                if(((TravelBug) item).getId() == travelbugID) {
                    items.remove(item);
                    return;
                }
            }else if(item.getName().compareTo(itemName) == 0) {
                items.remove(item);
                return;
            }
        }
    }

    /**
     * @return LogBook, todos os logs guardados na cache
     */
    public RedBlackBST2<Date, Log> getLogBook() {
        return logBook;
    }

    /**
     * adicionar log à cache
     * @param log
     */
    public void addLog(Log log) {
        this.logBook.put(log.getData(), log);
        Log.logsTotal.add(log);
    }


    /**
     * R8.c
     * Percorrer todos os historicos de caches visitadas dos utilizadores
     * e retornar os utilizadores que tenham visitado esta cache
     */
    public ST2<Integer, Utilizador> utilizadoresQueJaVisitaram(){
        ST2<Integer, Utilizador> utilizadores = new ST2<>();
        for(int userID : Utilizador.utilizadoresST.keys()){
            for(Date data : Utilizador.utilizadoresST.get(userID).historicoCachesVisitadas.keys()){
                if(Utilizador.utilizadoresST.get(userID).historicoCachesVisitadas.get(data).getId() == this.id)
                    utilizadores.put(userID, Utilizador.utilizadoresST.get(userID));
            }
        }
        return utilizadores;
    }

    /**
     * R8.d
     * Percorrer todas as caches guardadas e retornar apenas as de tipo PREMIUM
     */
    public static ST2<Integer, Cache> cachesPremiumComItems(){
        ST2<Integer, Cache> caches = new ST2<>();
        for(int cacheID : cachesST.keys()){
            if(cachesST.get(cacheID).tipo.equals(Tipo.PREMIUM) && cachesST.get(cacheID).items.size() > 0){
                caches.put(cacheID,cachesST.get(cacheID));
            }
        }
        return caches;
    }


    /**
     * Imprimir todas as caches guardadas na ST, na consola
     */
    public static void printCachesST() {
        System.out.println("\nRed Black de Caches:");
        for (int id : cachesST.keys()) {
            System.out.println("ID " + id + ':');
            if(cachesST.get(id).owner == null)
                System.out.println("\tOwner: UNKNOWN");
            else
                System.out.println("\tOwner: " + cachesST.get(id).owner.toString());
            System.out.println("\tTipo: " + cachesST.get(id).tipo);
            System.out.println("\tLocalizacao: " + cachesST.get(id).localizacao.toString());
            System.out.println("\tDificuldade: " + cachesST.get(id).dificuldade);
            System.out.println("\tTerreno: " + cachesST.get(id).terreno);
            if(cachesST.get(id).items.size() > 0) {
                System.out.print("\tItems: ");
                for(int i = 0; i < cachesST.get(id).items.size(); i++){
                    System.out.print(cachesST.get(id).items.get(i).getName());
                    if(i != cachesST.get(id).items.size()-1)
                        System.out.print(", ");
                }
                System.out.println();
            }
            if(cachesST.get(id).logBook.size() > 0){
                System.out.println("\tLogBook: ");
                for(Date date : cachesST.get(id).logBook.keys()){
                    System.out.println("\t\t" + cachesST.get(id).logBook.get(date).toString());
                }
            }
        }
    }

    /**
     * Inserir cache na ST estática (global), simulando um servidor
     * Dá um ID unico à cache também
     */
    public void inserirCacheST(){
        if (cachesST.contains(id)) {
            System.out.println("Ja existe uma Cache com o mesmo ID!");
            return;
        }
        else if(id <= 0){
            id = uniqueID();
        }
        cachesST.put(id, this);
    }

    /**
     * Remover cache da ST global e remove-la de qualuqer user ou travelbug a que esteja associada
     */
    public void removerCacheST(){
        if(cachesST.contains(this.id)) {
            cachesST.delete(this.id);
            for(int userID : Utilizador.utilizadoresST.keys()){
                for(Date data : Utilizador.utilizadoresST.get(userID).historicoCachesVisitadas.keys()){
                    if(Utilizador.utilizadoresST.get(userID).historicoCachesVisitadas.get(data).getId() == this.id)
                        Utilizador.utilizadoresST.get(userID).historicoCachesVisitadas.delete(data);
                }
                for(Date data : Utilizador.utilizadoresST.get(userID).historicoCachesEscondidas.keys()){
                    if(Utilizador.utilizadoresST.get(userID).historicoCachesEscondidas.get(data).getId() == this.id)
                        Utilizador.utilizadoresST.get(userID).historicoCachesEscondidas.delete(data);
                }
            }
            for(int travelbugID : TravelBug.travelBugsBST.keys()){
                if(TravelBug.travelBugsBST.get(travelbugID).getCache().getId() == this.id)
                    TravelBug.travelBugsBST.delete(travelbugID);
                else if(TravelBug.travelBugsBST.get(travelbugID).getCacheDestino().getId() == this.id)
                    TravelBug.travelBugsBST.get(travelbugID).setCacheDestino(null);
            }

            Localizacao.regioes.put(localizacao.getRegiao(), Localizacao.regioes.get(localizacao.getRegiao()) - 1);

        }else{
            System.out.println("Esta Cache nao se encontra na ST");
        }
    }


    /**
     * Edit cache attributes
     * @param ownerID (<= 0 to not change)
     * @param localizacao (== null to not change)
     * @param dificuldade (range: 0-5), anything else to not change
     * @param terreno (range: 0-5), anything else to not change
     * @param tipo (== null to not change)
     */
    public void editCache(int ownerID, Localizacao localizacao, int dificuldade, int terreno, Tipo tipo){
        if(ownerID > 0) {
            if (Utilizador.utilizadoresST.contains(ownerID))
                this.owner = Utilizador.utilizadoresST.get(ownerID);
            else
                System.out.println("Não existe um utilizador com esse ID!");
        }
        if(localizacao != null) {
            Localizacao.regioes.put(this.localizacao.getRegiao(), Localizacao.regioes.get(this.localizacao.getRegiao()) - 1);
            if(Localizacao.regioes.contains(localizacao.getRegiao()))
                Localizacao.regioes.put(localizacao.getRegiao(), Localizacao.regioes.get(localizacao.getRegiao()) + 1);
            else
                Localizacao.regioes.put(localizacao.getRegiao(), 1);
            this.localizacao = localizacao;
        }
        if(dificuldade >= 0 && dificuldade <= 5)
            this.dificuldade = dificuldade;
        if(terreno >= 0 && terreno <= 5)
            this.terreno = terreno;
        if(tipo != null)
            this.tipo = tipo;

        cachesST.put(id, this);
    }

    /*
    public static void writeCachesToTxt() {
        if(!cachesST.isEmpty()) {
            Out out = new Out(".//data//caches.txt");
            for (int id : cachesST.keys()) {
                out.println(cachesST.get(id).id + "\n"
                        + cachesST.get(id).ownerUsername + "\n"
                        + cachesST.get(id).localizacao.latitude + "@" + cachesST.get(id).localizacao.longitude + "\n"
                        + cachesST.get(id).dificuldade + "@" + cachesST.get(id).terreno + "\n"
                        + cachesST.get(id).tipo);
                out.println();  //separaçao de caches
            }

            out.close();
        }
    }
    */

    @Override
    public String toString() {
        return "Cache{" +
                "id=" + id +
                '}';
    }

}