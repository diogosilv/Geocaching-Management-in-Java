


public class TravelBug extends Item{
    private int id;
    private Utilizador owner;
    private Utilizador userMaisRecente;
    private Cache cache;
    private Cache cacheDestino;
    public RedBlackBST2<Date, Localizacao> historicoLocalizacoes = new RedBlackBST2<>();
    public RedBlackBST2<Date, Utilizador> historicoUtilizadores = new RedBlackBST2<>();

    public static RedBlackBST2<Integer, TravelBug> travelBugsBST = new RedBlackBST2<>();

    /**
     * Retorna o ID mais baixo disponivel na ST de TravelBugs
     * @return
     */
    private int uniqueID(){
        int id = 1;
        while(travelBugsBST.contains(id)){
            id++;
        }
        return id;
    }

    /**
     * Construtor a ser usado apenas para ler do ficheiro input
     */
    public TravelBug(int id, int ownerID, int cacheID, int cacheDestinoID) {
        super("travelbug");
        this.id = id;
        this.owner = Utilizador.utilizadoresST.get(ownerID);
        this.setUserMaisRecente(Utilizador.utilizadoresST.get(ownerID));
        this.cache = Cache.cachesST.get(cacheID);
        this.cacheDestino = Cache.cachesST.get(cacheDestinoID);
        this.historicoUtilizadores.put(new Date(), Utilizador.utilizadoresST.get(ownerID));
        this.historicoLocalizacoes.put(new Date(), Cache.cachesST.get(cacheID).getLocalizacao());
    }

    public TravelBug(int ownerID, int cacheID, int cacheDestinoID) {
        super("travelbug");
        this.owner = Utilizador.utilizadoresST.get(ownerID);
        this.setUserMaisRecente(Utilizador.utilizadoresST.get(ownerID));
        this.cache = Cache.cachesST.get(cacheID);
        this.cacheDestino = Cache.cachesST.get(cacheDestinoID);
        this.historicoUtilizadores.put(new Date(), Utilizador.utilizadoresST.get(ownerID));
        this.historicoLocalizacoes.put(new Date(), Cache.cachesST.get(cacheID).getLocalizacao());
    }

    public int getId() { return id; }

    public Utilizador getOwner() {
        return owner;
    }

    public Utilizador getUserMaisRecente() {
        return userMaisRecente;
    }

    /**
     * Declarar que um novo Utilizador possui este travelbug, e acrescentá-lo ao seu historico
     * @param userMaisRecente
     */
    public void setUserMaisRecente(Utilizador userMaisRecente) {
        this.userMaisRecente = userMaisRecente;
        Date today = new Date();
        historicoUtilizadores.put(today, userMaisRecente);
    }

    public void setOwner(Utilizador owner) {
        this.owner = owner;
    }

    public Cache getCache() {
        return cache;
    }

    /**
     * Mudar de cache, e atualizar o historico de localizacoes
     * @param cache
     */
    public void setCache(Cache cache) {
        this.cache = cache;
        Date today = new Date();
        historicoLocalizacoes.put(today, cache.getLocalizacao());
    }

    public Cache getCacheDestino() {
        return cacheDestino;
    }

    public void setCacheDestino(Cache cacheDestino) {
        this.cacheDestino = cacheDestino;
    }

    /**
     * Imprimir na consola, para todos os TravelBugs, a sua localização atual,
     * o ultimo utilizador que o transportou, o periodo em que foi transportado,
     * e todas as localizações percorridas
     */
    public static void now() {
        for(int id : travelBugsBST.keys()) {
            System.out.println("\n_TRAVEL BUG " + id
                    + ":\nLocalizacao Atual: " + travelBugsBST.get(id).getLocalizacao().toString()
                    + "\nUltimo Utilizador Que o Transportou: " + travelBugsBST.get(id).userMaisRecente.toString()
                    + "\nPeriodo Em Que Foi Transportado: " + travelBugsBST.get(id).historicoLocalizacoes.max().toString()
                    + "\nCaminhos Percorridos Ate Ao Momento: ");
            for(Date data : travelBugsBST.get(id).historicoLocalizacoes.keys())
            System.out.println(travelBugsBST.get(id).historicoLocalizacoes.get(data).toString());
        }
    }

   /* @Override
    public String toString() {
        return "TravelBug{" +
                "ID=" + this.id + "\n" +
                ", Owner=" + this.owner +
                ", userMaisRecente=" + this.userMaisRecente + "\n" +
                ", cache=" + this.cache +
                ", historicoCaches=" + this.historicoLocalizacoes +
                ", historicoUtilizadores=" + this.historicoUtilizadores +
                '}';
    }*/

    @Override
    public String toString() {
        return "{TravelBug: " +
                "',\n    ID: '" + this.id +
                "',\n    Owner: '" + this.owner +
                "',\n    userMaisRecente: '" + this.userMaisRecente +
                "',\n    cache: '" + this.cache +
                "',\n    historicoCaches: '" + this.historicoLocalizacoes +
                "',\n    historicoUtilizadores: '" + this.historicoUtilizadores +
                "'\n}\n";
    }



    /**
     * R8.f
     * Imprimir os Travelbugs que contêm mais localizacoes percorridas nos seus historicos
     * @param numTB (Quantos travelbugs imprimir)
     * @return
     */
    public static ST2<Integer, TravelBug> travelBugsComMaisLocalizacoesPercorridas(int numTB){
        ST2<Integer, TravelBug> travelbugs = new ST2<>();
        TravelBug tbComMenosLocalizacoes = travelBugsBST.get(travelBugsBST.min());


        for(int travelBugID : travelBugsBST.keys()){
            if(travelbugs.size() < numTB){
                travelbugs.put(travelBugID, travelBugsBST.get(travelBugID));
            }else if(travelBugsBST.get(travelBugID).historicoLocalizacoes.size() >= tbComMenosLocalizacoes.historicoLocalizacoes.size()){
                travelbugs.remove(tbComMenosLocalizacoes.getId());
                travelbugs.put(travelBugID, travelBugsBST.get(travelBugID));
            }

            for(int tbIDaux : travelbugs.keys()){
                if(tbIDaux == travelbugs.min()){
                    tbComMenosLocalizacoes = travelbugs.get(tbIDaux);
                }else{
                    if(travelbugs.get(tbIDaux).historicoLocalizacoes.size() < tbComMenosLocalizacoes.historicoLocalizacoes.size())
                        tbComMenosLocalizacoes = travelbugs.get(tbIDaux);
                }
            }
        }
        return travelbugs;

    }

    /**
     * retorna a sua localização atual
     */
    public Localizacao getLocalizacao() {
        if (cache != null)
            return cache.getLocalizacao();

        return null;
    }

    /**
     * Inserir travelbug na ST estática (global), simulando um servidor
     * Dá um ID unico ao travelbug também
     */
    public void inserirTravelBugST(){
        if (travelBugsBST.contains(id)) {
            System.out.println("Ja existe um TravelBug com o mesmo ID!");
            return;
        }
        else if(id <= 0){
            id = uniqueID();
        }
        travelBugsBST.put(id, this);
    }

    /**
     * remove o travelbug do array global, e de qualquer utilizador ou cache que o contivessem
     */
    public void removerTravelBugST(){
        if(travelBugsBST.contains(this.id)) {
            travelBugsBST.delete(this.id);
            for(int userID : Utilizador.utilizadoresST.keys()){
                if(Utilizador.utilizadoresST.get(userID).travelbugs.contains(this.id))
                    Utilizador.utilizadoresST.get(userID).travelbugs.delete(this.id);
            }
            for(int cacheID : Cache.cachesST.keys()){
                if(Cache.cachesST.get(cacheID).getItems().contains(this))
                    Cache.cachesST.get(cacheID).removeItem("travelbug", this.id);
            }
        }else{
            System.out.println("Este Travelbug nao se encontra na ST");
        }
    }


    /**
     * Edit travelbug attributes
     * @param userMaisRecente (== null to not change)
     * @param cache (== null to not change)
     * @param cacheDestino (== null to not change)
     */
    public void editTravelbug(Utilizador userMaisRecente, Cache cache, Cache cacheDestino){
        if(userMaisRecente != null && Utilizador.utilizadoresST.contains(userMaisRecente.getId())) {
            setUserMaisRecente(userMaisRecente);
        }
        if(cache != null && Cache.cachesST.contains(cache.getId())){
            setCache(cache);
        }
        if(cacheDestino != null && Cache.cachesST.contains(cacheDestino.getId())){
            this.cacheDestino = cacheDestino;
        }
        travelBugsBST.put(id, this);
    }

    /**
     * Imprime todos os travelbugs guardados na ST, na consola
     */
    public static void printTravelBugST() {
        System.out.println("\nTravelBugs:");
        for (int id : travelBugsBST.keys()) {
            System.out.printf("Id: %15s\t-\t"
                            + "Owner: %15s\t-\t"
                            + "Cache Inicial: %10s\t-\t"
                            + "Cache Destino: ",
                    travelBugsBST.get(id).id,
                    travelBugsBST.get(id).owner.toString(),
                    travelBugsBST.get(id).cache.toString());
            if(travelBugsBST.get(id).cacheDestino == null)
                System.out.println("UNKNOWN");
            else
                System.out.println(travelBugsBST.get(id).cacheDestino.toString());

        }
    }
}