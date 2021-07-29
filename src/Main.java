import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;

public class Main {

    public static void main(String[] args) {
        readInputFromFile();

        //UTILIZADORES:
        Utilizador utilizador1 = new Utilizador("Pessoa Teste", Tipo.ADMIN);

        //Testar obter GPS de uma nova cache (nova missao)
        if(utilizador1.obterCacheGPS() != null)
            System.out.println(utilizador1.obterCacheGPS());

        //Adicionar Caches Escondidas ao utilizador
        utilizador1.addCacheEscondida(1);
        utilizador1.addCacheEscondida(2);
        //utilizador1.addCacheEscondida(2);      //"Este utilizador ja escondeu essa cache."
        //utilizador1.addCacheEscondida(200);    //"Nao existe nenhuma cache com esse ID guardada na ST"

        //Adicionar Caches Visitadas ao utilizador
        utilizador1.addCacheVisitada(3);
        utilizador1.historicoCachesVisitadas.put(new Date(2021,1,1), Cache.cachesST.get(8));   //Neste caso adicionar duas caches ao mesmo tempo, daria override no historico,
        //utilizador1.addCacheVisitada(8);                                                                      //pois teriam a mesma data, daí colocarmos diretamente no historico para testar
        //utilizador1.addCacheVisitada(8);       //"Este utilizador ja visitou essa cache."
        //utilizador1.addCacheVisitada(800);     //"Nao existe nenhuma cache com esse ID guardada na ST"

        //Adicionar TravelBug ao utilizador
        utilizador1.addTravelbug(1);
        //utilizador1.addTravelbug(1);        //"O travelbug ja esta associado a este utilizador, ou nao existe."
        //utilizador1.addTravelbug(100);      //"O travelbug ja esta associado a este utilizador, ou nao existe."

        //Caches Visitadas Por regiao
        ST2<Integer, Cache> cachesVisitadas = new ST2<>();
        cachesVisitadas = utilizador1.cachesVisitadasPorRegiao("norte");
        System.out.println("Caches visitadas por " + utilizador1.getNome() + " na regiao norte:");
        for(int cacheID : cachesVisitadas){
            System.out.println(Cache.cachesST.get(cacheID));
        }
        cachesVisitadas = utilizador1.cachesVisitadasPorRegiao("global");
        System.out.println("Caches visitadas por " + utilizador1.getNome() + " globalmente:");
        for(int cacheID : cachesVisitadas){
            System.out.println(Cache.cachesST.get(cacheID).toString());
        }

        //Caches nao Visitadas por Regiao
        ST2<Integer, Cache> cachesNaoVisitadas = new ST2<>();
        cachesNaoVisitadas = utilizador1.cachesNaoVisitadasPorRegiao("norte");
        System.out.println("Caches NAO visitadas por " + utilizador1.getNome() + " na regiao norte:");
        for(int cacheID : cachesNaoVisitadas){
            System.out.println(Cache.cachesST.get(cacheID));
        }
        cachesNaoVisitadas = utilizador1.cachesNaoVisitadasPorRegiao("global");
        System.out.println("Caches NAO visitadas por " + utilizador1.getNome() + " globalmente:");
        for(int cacheID : cachesNaoVisitadas){
            System.out.println(Cache.cachesST.get(cacheID));
        }

        //Print Utilizadores na ST
        Utilizador.printHashSTUtilizadores();

        //Inserir Utilizador na ST
        utilizador1.inserirUtilizadorST();
        Utilizador.printHashSTUtilizadores();

        //Editar Utilizador na ST
        utilizador1.editUtilizador(null, Tipo.BASIC);
        Utilizador.printHashSTUtilizadores();

        //Remover Utilizador da ST
        utilizador1.removerUtilizadorST();
        Utilizador.printHashSTUtilizadores();


        //CACHES:
        Cache cache1 = new Cache(Tipo.PREMIUM, new Localizacao("Porto", 41.16016,-8.62689), 5, 5);

        //Adicionar e remover items
        cache1.addItem(new Item("bola"));
        cache1.addItem(new Item("Raquete"));
        cache1.removeItem("bola", 0);   //0 Porque nao é um travelbug

        //Adicionar Logs
        cache1.addLog(new Log(new Date(), "Mensagem teste"));
        RedBlackBST2<Date, Log> logbook = cache1.getLogBook();
        System.out.println("LOGBOOK da cache1:");
        for(Date data : logbook.keys())
            System.out.println(logbook.get(data));

        //Utilizadores que ja visitaram
        Utilizador utilizador2 = new Utilizador("User2", Tipo.BASIC);
        Utilizador utilizador3 = new Utilizador("User3", Tipo.PREMIUM);
        utilizador2.addCacheVisitada(8);    //Cache que tbm tinha sido visitada pelo Utilizador1, que foi apagado
        utilizador3.addCacheVisitada(8);
        utilizador2.inserirUtilizadorST();
        utilizador3.inserirUtilizadorST();
        ST2<Integer,Utilizador> utilizadorQueVisitaramCache8 = new ST2<>();
        utilizadorQueVisitaramCache8 = Cache.cachesST.get(8).utilizadoresQueJaVisitaram();
        System.out.println("Utilizadores que visitaram a cache 8:");
        for(int userID : utilizadorQueVisitaramCache8)
            System.out.println(utilizadorQueVisitaramCache8.get(userID).toString());

        //Caches Premium Com Items
        ST2<Integer,Cache> cachesPreCItem = new ST2<>();
        cachesPreCItem = Cache.cachesPremiumComItems();
        System.out.println("Caches Premium Com Items:");
        for(int cacheID : cachesPreCItem)
            System.out.println(cachesPreCItem.get(cacheID));

        //Print Caches na ST
        Cache.printCachesST();

        //Inserir Cache na ST
        cache1.inserirCacheST();
        Cache.printCachesST();

        //Editar cache na ST
        cache1.editCache(0, null, 1,1,Tipo.BASIC);
        Cache.printCachesST();

        //Remover cache da ST
        cache1.removerCacheST();
        Cache.printCachesST();


        //TRAVELBUGS
        TravelBug travelbug1 = new TravelBug(1, 1, 2);  //TESTAR OUTRA VEZ

        //Inserir novo Utilizador associado
        travelbug1.setUserMaisRecente(utilizador3);

        //Inserir Nova Cache
        travelbug1.setCache(Cache.cachesST.get(4));

        //Método now()
        TravelBug.now();

        //TravelBugs com mais localizacoes percorridas (passando quantos TBs queremos receber, por parametro)
        //RedBlackBST<Integer, TravelBug> travelBugs = new RedBlackBST<>();
        ST2<Integer, TravelBug> travelBugs = new ST2<>();
        int numTB = 2;
        travelBugs = TravelBug.travelBugsComMaisLocalizacoesPercorridas(numTB);
        System.out.println(travelBugs.size());
        System.out.println("Os " +numTB + " TravelBugs Com Mais Localizacoes Percorridas:");
        for(int tbID : travelBugs.keys())
            System.out.println("Travelbug " + tbID + " com " + travelBugs.get(tbID).historicoLocalizacoes.size() + " localizacoes percorridas");

        //Print Travelbugs na ST
        TravelBug.printTravelBugST();

        //Inserir Travelbug na ST
        travelbug1.inserirTravelBugST();
        TravelBug.printTravelBugST();

        //Editar Travelbug na ST
        travelbug1.editTravelbug(null, Cache.cachesST.get(5), null);
        TravelBug.printTravelBugST();

        //Remover TravelBug da ST
        travelbug1.removerTravelBugST();
        TravelBug.printTravelBugST();

        //writeOutputToFile();

    }


    public static void readInputFromFile(){
        String line;
        In in = new In(".//data//input.txt");
        if(!in.exists()){
            System.out.println("Failed to read file.");
        }else {
            while (in.hasNextLine()) {
                line = in.readLine();
                int numUtilizadores = Integer.parseInt(line);
                for (int i = 0; i < numUtilizadores; i++) {
                    line = in.readLine();
                    String[] utilizadorInput = line.split(", ", 3);
                    Utilizador utilizador = new Utilizador(
                            Integer.parseInt(utilizadorInput[0]),
                            utilizadorInput[1],
                            Tipo.fromString(utilizadorInput[2])
                    );
                    utilizador.inserirUtilizadorST();
                }
                line = in.readLine();
                int numRegioes = Integer.parseInt(line);
                for (int i = 0; i < numRegioes; i++) {
                    line = in.readLine();
                    String[] regiaoInput = line.split(", ", 2);
                    for (int j = 0; j < Integer.parseInt(regiaoInput[1]); j++) {
                        line = in.readLine();
                        String[] cacheInput = line.split(", ", 8);

                        Localizacao localizacao = new Localizacao(regiaoInput[0], Double.parseDouble(cacheInput[2]), Double.parseDouble(cacheInput[3]));
                        Cache cache = new Cache(
                                Integer.parseInt(cacheInput[0].replace("geocache", "")),
                                Tipo.fromString(cacheInput[1]),
                                localizacao,
                                Integer.parseInt(cacheInput[4]),
                                Integer.parseInt(cacheInput[5])
                        );
                        if (Integer.parseInt(cacheInput[6]) != 0) {
                            String[] items = cacheInput[7].split(", ", Integer.parseInt(cacheInput[6]));
                            for (String s : items) {
                                Item item = new Item(s);
                                cache.addItem(item);
                            }
                        }

                        cache.inserirCacheST();
                    }
                }
                //line = in.readLine();
                //int numLigacoes = Integer.parseInt(line);
                line = in.readLine();
                int numTravelbugs = Integer.parseInt(line);
                for (int i = 0; i < numTravelbugs; i++) {
                    line = in.readLine();
                    String[] travelbugInput = line.split(", ", 4);
                    TravelBug travelBug = new TravelBug(
                            Integer.parseInt(travelbugInput[0].replace("travelbug", "")),
                            Integer.parseInt(travelbugInput[1].replace("utilizador", "")),
                            Integer.parseInt(travelbugInput[2].replace("geocache", "")),
                            Integer.parseInt(travelbugInput[3].replace("geocache", ""))
                    );
                    travelBug.inserirTravelBugST();
                }
                line = in.readLine();
                int numLogs = Integer.parseInt(line);
                for (int i = 0; i < numLogs; i++) {
                    line = in.readLine();
                    String[] logInput = line.split(", ", 8);
                    Date data = new Date(
                            Integer.parseInt(logInput[1]),
                            Integer.parseInt(logInput[2]),
                            Integer.parseInt(logInput[3]),
                            Integer.parseInt(logInput[4]),
                            Integer.parseInt(logInput[5]),
                            Integer.parseInt(logInput[6])
                    );
                    Log log = new Log(data, logInput[7]);
                    Cache.cachesST.get(Integer.parseInt(logInput[0].replace("geocache", ""))).addLog(log);
                }
            }
        }
    }

    public static void writeOutputToFile() {
        Out out = new Out(".//data//input.txt");
        out.println(Utilizador.utilizadoresST.size());
        for(int userID : Utilizador.utilizadoresST.keys()){
            out.println(userID + ", "
                    + Utilizador.utilizadoresST.get(userID).getNome() + ", "
                    + Utilizador.utilizadoresST.get(userID).getTipo().toString());
        }
        out.println(Localizacao.regioes.size());
        for(String regiao : Localizacao.regioes.keys()){
            out.println(regiao + ", " + Localizacao.regioes.get(regiao));
            for(int cacheID : Cache.cachesST.keys()){
                if(Cache.cachesST.get(cacheID).getLocalizacao().getRegiao().compareTo(regiao) == 0){
                    out.print("geocache" + cacheID + ", "
                            + Cache.cachesST.get(cacheID).getTipo().toString() + ", "
                            + Cache.cachesST.get(cacheID).getLocalizacao().getLatitude() + ", "
                            + Cache.cachesST.get(cacheID).getLocalizacao().getLongitude() + ", "
                            + Cache.cachesST.get(cacheID).getDificuldade() + ", "
                            + Cache.cachesST.get(cacheID).getTerreno() + ", "
                            + Cache.cachesST.get(cacheID).getItems().size());
                    for(Item item : Cache.cachesST.get(cacheID).getItems()){
                        out.print(", " + item.getName());
                    }
                    out.println();
                }

            }
        }
        out.println(TravelBug.travelBugsBST.size());
        for(int travelbugID : TravelBug.travelBugsBST.keys()){
            out.println("travelbug" + travelbugID + ", "
                    + "utilizador" + TravelBug.travelBugsBST.get(travelbugID).getOwner().getId() + ", "
                    + "geocache" + TravelBug.travelBugsBST.get(travelbugID).getCache().getId() + ", "
                    + "geocache" + TravelBug.travelBugsBST.get(travelbugID).getCacheDestino().getId());
        }
        out.println(Log.logsTotal.size());
        for(int cacheID : Cache.cachesST.keys()){
            for(Date date : Cache.cachesST.get(cacheID).getLogBook().keys()){
                out.println("geocache" + Cache.cachesST.get(cacheID).getId() + ", "
                        + Cache.cachesST.get(cacheID).getLogBook().get(date).getData().getYear() + ", "
                        + Cache.cachesST.get(cacheID).getLogBook().get(date).getData().getMonth() + ", "
                        + Cache.cachesST.get(cacheID).getLogBook().get(date).getData().getDay() + ", "
                        + Cache.cachesST.get(cacheID).getLogBook().get(date).getData().getHour() + ", "
                        + Cache.cachesST.get(cacheID).getLogBook().get(date).getData().getMinute() + ", "
                        + Cache.cachesST.get(cacheID).getLogBook().get(date).getData().getSecond() + ", "
                        + Cache.cachesST.get(cacheID).getLogBook().get(date).getMensagem());
            }
        }
    }

    public static void clearEverything(){
        Log.logsTotal.clear();
        for(String regiao : Localizacao.regioes.keys())
            Localizacao.regioes.put(regiao, 0);
        for(int uID : Utilizador.utilizadoresST.keys())
            Utilizador.utilizadoresST.get(uID).removerUtilizadorST();
        for(int cID : Cache.cachesST.keys())
            Cache.cachesST.get(cID).removerCacheST();
        for(int tbID : TravelBug.travelBugsBST.keys())
            TravelBug.travelBugsBST.get(tbID).removerTravelBugST();
    }
}