import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    public static final double GROUP_MARGIN = 30.0;
    private static final double radius = 20.0;
    private static final int START = 25;
    private static final int FINAL = 600;

    //GRAFO
    public Group graphGroup;
    public Group graphGroupPremium;
    public Group graphGroupBasic;
    public TextField addFromField;
    public TextField addToField;
    public TextField addWeightField;
    public TextField addTimeField;
    public TextField removeFromField;
    public TextField removeToField;
    public TextField editFromField;
    public TextField editToField;
    public TextField editWeightField;
    public TextField editTimeField;
    public TextField shortestPathFromField;
    public TextField shortestPathToField;
    public TextField addFromPremiumField;
    public TextField addToPremiumField;
    public TextField addWeightPremiumField;
    public TextField addTimePremiumField;
    public TextField removeFromPremiumField;
    public TextField removeToPremiumField;
    public TextField editFromPremiumField;
    public TextField editToPremiumField;
    public TextField editWeightPremiumField;
    public TextField editTimePremiumField;
    public TextField shortestPathFromPremiumField;
    public TextField shortestPathToPremiumField;
    public TextField addFromBasicField;
    public TextField addToBasicField;
    public TextField addWeightBasicField;
    public TextField addTimeBasicField;
    public TextField removeFromBasicField;
    public TextField removeToBasicField;
    public TextField editFromBasicField;
    public TextField editToBasicField;
    public TextField editWeightBasicField;
    public TextField editTimeBasicField;
    public TextField shortestPathFromBasicField;
    public TextField shortestPathToBasicField;

    //UTILIZADORES
    public TableView<Utilizador> utilizadorTable;
    public TableColumn<Utilizador, String> nameCol;
    public TableColumn<Utilizador, String> tipoCol;
    public TextField nameField;
    public TextField tipoField;

    //CACHES
    public TableView<Cache> cacheTable;
    public TableColumn<Cache, String> idCol;
    public TableColumn<Cache, String> localizacaoCol;
    public TableColumn<Cache, String> dificuldadeCol;
    public TableColumn<Cache, String> terrenoCol;
    public TableColumn<Cache, String> tipeCol;
    public TextField userOwnerField;
    public TextField dificuldadeField;
    public TextField terrenoField;
    public TextField tipeField;
    public TextField regiaoField;
    public TextField latitudeField;
    public TextField longitudeField;

    //TRAVELBUGS
    public TableView<TravelBug> travelBugTable;
    public TableColumn<TravelBug, String> ownerCol;
    public TableColumn<TravelBug, String> tbIDCol;
    public TableColumn<TravelBug, String> cacheCol;
    public TableColumn<TravelBug, String> cacheDestinoCol;
    public TableColumn<TravelBug, String> itemCol;
    public TextField ownerField;
    public TextField cacheField;
    public TextField cacheDestinoField;
    public TextField itemField;

    //ITEMS
  /*  public TableView<TravelBug> itemTable;
    public TableColumn<TravelBug, String> ownerCol;
    public TableColumn<TravelBug, String> tbIDCol;
    public TableColumn<TravelBug, String> cacheCol;
    public TableColumn<TravelBug, String> cacheDestinoCol;
    public TextField ownerField;
    public TextField cacheField;
    public TextField cacheDestinoField;*/

    private static final String PATH_BINUtilizador = ".//data//inputUtilizadores.bin";
    private static final String PATH_BINCaches = ".//data//inputCaches.bin";
    private static final String PATH_BINTravelBugs = ".//data//inputTravelBugs.bin";
    private static final String PATH_GRAPHtoFile = ".//data//grafo.txt";
    private static final String PATH_GRAPHtoFileBIN = ".//data//grafoBin.bin";
    private static final String PATH_GRAPHPremiumToFile = ".//data//grafoPremium.txt";
    private static final String PATH_GRAPHPremiumToFileBIN = ".//data//grafoPremiumBin.bin";
    private static final String PATH_GRAPHNBasicToFile = ".//data//grafoBasic.txt";
    private static final String PATH_GRAPHBasicToFileBIN = ".//data//grafoBasicBin.bin";


    ObservableList<Utilizador> utilizadores = FXCollections.observableArrayList();
    ObservableList<Cache> caches = FXCollections.observableArrayList();
    ObservableList<TravelBug> travelBugs = FXCollections.observableArrayList();
    ObservableList<Localizacao> regioes = FXCollections.observableArrayList();
    ArrayList<Utilizador> inputUtilizador = new ArrayList<>(utilizadores);
    ArrayList<Cache> inputCaches = new ArrayList<>(caches);
    ArrayList<TravelBug> inputTravelBugs = new ArrayList<>(travelBugs);
    public static EdgeWeightedDigraphNew desenhoGrafo = null;
    public static EdgeWeightedDigraphNew desenhoGrafoPremium = null;
    public static EdgeWeightedDigraphNew desenhoGrafoBasic = null;
    public static ST2<Integer, Cache> cachesColor = new ST2<>();
    ArrayList<Integer> arrAvoid = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        utilizadorTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);//escolher multiplas linhas
        cacheTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        travelBugTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Utilizadores
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        tipoCol.setCellValueFactory(new PropertyValueFactory<>("Tipo"));
        utilizadorTable.setEditable(true);

        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());// editar
        //tipoCol.setCellFactory(TextFieldTableCell.forTableColumn());

        //Caches
        idCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        localizacaoCol.setCellValueFactory(new PropertyValueFactory<>("Localizacao"));
        dificuldadeCol.setCellValueFactory(new PropertyValueFactory<>("Dificuldade"));
        terrenoCol.setCellValueFactory(new PropertyValueFactory<>("Terreno"));
        tipeCol.setCellValueFactory(new PropertyValueFactory<>("Tipo"));
        itemCol.setCellValueFactory(new PropertyValueFactory<>("Items"));
        cacheTable.setEditable(true);

        // userOwnerCol.setCellFactory(TextFieldTableCell.forTableColumn());// editar
        // localizacaoCol.setCellFactory(TextFieldTableCell.forTableColumn());
        //dificuldadeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        //terrenoCol.setCellFactory(TextFieldTableCell.forTableColumn());
        //tipeCol.setCellFactory(TextFieldTableCell.forTableColumn());


        //TravelBugs
        tbIDCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        ownerCol.setCellValueFactory(new PropertyValueFactory<>("Owner"));
        cacheCol.setCellValueFactory(new PropertyValueFactory<>("Cache"));
        cacheDestinoCol.setCellValueFactory(new PropertyValueFactory<>("CacheDestino"));
        travelBugTable.setEditable(true);

       /* ownerCol.setCellFactory(TextFieldTableCell.forTableColumn());// editar
        cacheCol.setCellFactory(TextFieldTableCell.forTableColumn());
        cacheDestinoCol.setCellFactory(TextFieldTableCell.forTableColumn());*/


        Main.readInputFromFile();

        SymbolDigraphNew sd = new SymbolDigraphNew("data/grafoIDs.txt", ";");
        desenhoGrafo = sd.digraph(); //poe grafo na variavel
        System.out.println("Grafo Geral: \n" + desenhoGrafo);

        SymbolDigraphNew sdPremium = new SymbolDigraphNew("data/grafoIDsPremium.txt", ";");
        desenhoGrafoPremium = sdPremium.digraph();
        System.out.println("Grafo Premium: \n" + desenhoGrafoPremium);

        SymbolDigraphNew sdBasic = new SymbolDigraphNew("data/grafoIDsBasic.txt", ";");
        desenhoGrafoBasic = sdBasic.digraph();
        System.out.println("Grafo Basic: \n" + desenhoGrafoBasic);

        //cachesColor = Grafo.;
        //cachesPremium();
    }

    public static EdgeWeightedDigraphNew grafoPremium() {
        RedBlackBST2<Integer, Cache> caches = new RedBlackBST2<>();

        for (int cacheID : Cache.cachesST.keys()) {
            if (Cache.cachesST.get(cacheID).getTipo().equals(Tipo.PREMIUM)) {
                caches.put(cacheID, Cache.cachesST.get(cacheID));
            }
        }

        SymbolDigraphNew sd = new SymbolDigraphNew("data/grafoIDs.txt", ";");
        EdgeWeightedDigraphNew graphTeste = sd.digraph();
        System.out.println(graphTeste);

        return graphTeste;
    }

    /**
     * Handler para acção do botão de encerramento da aplicação
     * @param actionEvent
     */
    public void handleExitAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Handler para açao do botão de informação acerca de quem realizou o projeto
     * @param actionEvent
     */
    public void handleAboutAction(ActionEvent actionEvent) {
        final Stage dialog = new Stage();
        dialog.setTitle("About US");
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVbox = new VBox(0);
        dialogVbox.getChildren().add(new Text("Trabalho realizado por:\nDiogo Silva - 38716\nVasco Silva - 38292\n"));
        dialogVbox.setAlignment(Pos.CENTER);
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    /**
     * Obtém informação acerca das caches
     * @return lista de caches
     * @throws IOException
     */
    private ObservableList<Cache> getInputCacheFromFile() throws IOException {
        for (Integer i : Cache.cachesST.keys()) {
            caches.add(Cache.cachesST.get(i));
            System.out.println(Cache.cachesST.get(i));
        }
        return caches;
    }

    /**
     * Obtém informação acerca dos utilizadores
     * @return lista de utilizadores
     * @throws IOException
     */
    private ObservableList<Utilizador> getInputUtilizadorFromFile() throws IOException {
        for (Integer i : Utilizador.utilizadoresST.keys()) {
            utilizadores.add(Utilizador.utilizadoresST.get(i));
            System.out.println(Utilizador.utilizadoresST.get(i));
        }
        System.out.println(utilizadores);
        return utilizadores;
    }

    /**
     * Obtém informação acerca dos travelBugs
     * @return lista de travelBugs
     * @throws IOException
     */
    private ObservableList<TravelBug> getInputTravelBugFromFile() throws IOException {
        for (Integer i : TravelBug.travelBugsBST.keys()) {
            travelBugs.add(TravelBug.travelBugsBST.get(i));
            System.out.println(TravelBug.travelBugsBST.get(i));
        }
        return travelBugs;
    }

    /**
     * Handler para acção do botão de abertura do ficheiro de texto
     * @param actionEvent
     */
    public void handleReadFileAction(ActionEvent actionEvent){
        //Main.clearEverything();
        //Main.readInputFromFile();
        cacheTable.getItems().clear();
        caches.clear();
        utilizadorTable.getItems().clear();
        utilizadores.clear();
        travelBugTable.getItems().clear();
        travelBugs.clear();

        try {
            cacheTable.getItems().addAll(getInputCacheFromFile());
            utilizadorTable.getItems().addAll(getInputUtilizadorFromFile());
            travelBugTable.getItems().addAll(getInputTravelBugFromFile());
            readGrafo();
        } catch (IOException e) {
            e.printStackTrace();
        }

        createGraph();
        createGraphPremium();
        createGraphBasic();
        System.out.println("Leu do Ficheiro de Texto \n");
    }

    /**
     * Handler para acção do botão de armanezamento para ficheiro de texto
     * @param actionEvent
     */
    public void handleSaveFileAction(ActionEvent actionEvent) {
        System.out.println(utilizadores);
        System.out.println(caches);
        System.out.println(travelBugs);
        for (int i : Utilizador.utilizadoresST.keys()) {
            System.out.println("\t\t" + Utilizador.utilizadoresST.get(i));

        }
        Main.writeOutputToFile();
        saveGrafo();

        System.out.println("Guardou para Ficheiro de Texto \n");
    }

    /**
     * Handler para acção do botão de armazenamento de dados num ficheiro binário
     * @param actionEvent
     */
    public void handleSaveBinFileAction(ActionEvent actionEvent){
        saveToBinFile();
        System.out.println("Guardou para Ficheiro Binário");
    }

    /**
     * Método para efectuar o armazenamento dos dados num ficheiro binário
     */
    private void saveToBinFile() {
        ArrayList<Utilizador> inputUtilizador = new ArrayList<>(utilizadores);
        ArrayList<Cache> inputCaches = new ArrayList<>(caches);
        ArrayList<TravelBug> inputTravelBugs = new ArrayList<>(travelBugs);

        //UTILIZADORES
        File f = new File(PATH_BINUtilizador);
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(inputUtilizador);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //CACHES
        File c = new File(PATH_BINCaches);
        try {
            FileOutputStream foc = new FileOutputStream(c);
            ObjectOutputStream ooc = new ObjectOutputStream(foc);
            ooc.writeObject(inputCaches);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //TRAVELBUGS
        File t = new File(PATH_BINTravelBugs);
        try {
            FileOutputStream fot = new FileOutputStream(t);
            ObjectOutputStream oot = new ObjectOutputStream(fot);
            oot.writeObject(inputTravelBugs);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //GRAFO
        File g = new File(PATH_GRAPHtoFileBIN);
        try {
            FileOutputStream fog = new FileOutputStream(g);
            ObjectOutputStream oog = new ObjectOutputStream(fog);
            oog.writeObject(desenhoGrafo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //GRAFO PREMIUM
        File gP = new File(PATH_GRAPHPremiumToFileBIN);
        try {
            FileOutputStream fogP = new FileOutputStream(gP);
            ObjectOutputStream oogP = new ObjectOutputStream(fogP);
            oogP.writeObject(desenhoGrafoPremium);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //GRAFO BASIC
        File gB = new File(PATH_GRAPHBasicToFileBIN);
        try {
            FileOutputStream fogB = new FileOutputStream(gB);
            ObjectOutputStream oogB = new ObjectOutputStream(fogB);
            oogB.writeObject(desenhoGrafoBasic);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handler para acção do botão de abertura do ficheiro binário
     * @param actionEvent
     */
    public void handleReadBinFileAction(ActionEvent actionEvent) {
        utilizadorTable.getItems().clear(); //para n duplicar dados se carregar 2x
        utilizadores.clear();
        cacheTable.getItems().clear();
        caches.clear();
        travelBugTable.getItems().clear();
        travelBugs.clear();

        readFromBinFile();

        utilizadorTable.getItems().addAll(utilizadores);
        cacheTable.getItems().addAll(caches);
        travelBugTable.getItems().addAll(travelBugs);
        createGraph();
        createGraphPremium();
        createGraphBasic();

        System.out.println("Leu de Ficheiro Binário");
    }

    /**
     * Método para leitura do ficheiro binário, no path indicado
     */
    private void readFromBinFile() {
        //UTILIZADOR
        File f = new File(PATH_BINUtilizador);
        try {
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            inputUtilizador = (ArrayList<Utilizador>) ois.readObject();
            utilizadorTable.getItems().addAll(inputUtilizador);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //CACHES
        File c = new File(PATH_BINCaches);
        try {
            FileInputStream fic = new FileInputStream(c);
            ObjectInputStream oic = new ObjectInputStream(fic);
            inputCaches = (ArrayList<Cache>) oic.readObject();
            cacheTable.getItems().addAll(inputCaches);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //TRAVELBUG
        File t = new File(PATH_BINTravelBugs);
        try {
            FileInputStream fit = new FileInputStream(t);
            ObjectInputStream oit = new ObjectInputStream(fit);
            inputTravelBugs = (ArrayList<TravelBug>) oit.readObject();
            travelBugTable.getItems().addAll(inputTravelBugs);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //GRAFO GERAL
        File g = new File(PATH_GRAPHtoFileBIN);
        try {
            FileInputStream fig = new FileInputStream(g);
            ObjectInputStream oig = new ObjectInputStream(fig);
            desenhoGrafo = (EdgeWeightedDigraphNew) oig.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //GRAFO PREMIUM
        File gP = new File(PATH_GRAPHPremiumToFileBIN);
        try {
            FileInputStream figP = new FileInputStream(gP);
            ObjectInputStream oigP = new ObjectInputStream(figP);
            desenhoGrafoPremium = (EdgeWeightedDigraphNew) oigP.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //GRAFO BASIC
        File gB = new File(PATH_GRAPHBasicToFileBIN);
        try {
            FileInputStream figB = new FileInputStream(gB);
            ObjectInputStream oigB = new ObjectInputStream(figB);
            desenhoGrafoBasic = (EdgeWeightedDigraphNew) oigB.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para escrito do ficheiro de texto, no path indicado
     * @param path
     * @return
     */
    public static PrintWriter openPrintWriter(String path) {
        try {
            FileWriter fw = new FileWriter(path);
            PrintWriter pw = new PrintWriter(fw);
            return pw;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Método para leitura do ficheiro de texto, no path indicado
     * @param path
     * @return
     */
    public static BufferedReader openBufferedReader(String path) {
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            return br;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Handler para acção do botão Add, responsável pela inserção de um utilizador na utilizadorTable
     * @param actionEvent
     */
    public void handleAddUtilizadorAction(ActionEvent actionEvent) {

        Utilizador u = new Utilizador(nameField.getText(), Tipo.fromString(tipoField.getText()));
        u.inserirUtilizadorST();
        utilizadorTable.getItems().add(u);
        utilizadores.add(u);
        nameField.setText("");
        tipoField.setText("");
    }

    /**
     * Handler para acção do botão Add, responsável pela inserção de uma cache na cacheTable
     * @param actionEvent
     */
    public void handleAddCacheAction(ActionEvent actionEvent) {

        Localizacao l = new Localizacao(regiaoField.getText(), Double.parseDouble(latitudeField.getText()), Double.parseDouble(longitudeField.getText()));
        System.out.println(tipeField.getText());
        System.out.println(dificuldadeField);
        System.out.println(terrenoField);
        Cache c = new Cache(Tipo.fromString(tipeField.getText()), l, Integer.parseInt(dificuldadeField.getText()), Integer.parseInt(terrenoField.getText()));

        c.inserirCacheST();
        cacheTable.getItems().add(c);
        caches.add(c);
        tipeField.setText("");
        dificuldadeField.setText("");
        terrenoField.setText("");
        regiaoField.setText("");
        latitudeField.setText("");
        longitudeField.setText("");
    }

    /**
     * Handler para acção do botão Add, responsável pela inserção de um travelBug na travelBugTable
     * @param actionEvent
     */
    public void handleAddTravelBugAction(ActionEvent actionEvent) {

        TravelBug u = new TravelBug(Integer.parseInt(ownerField.getText()), Integer.parseInt(cacheField.getText()), Integer.parseInt(cacheDestinoField.getText()));
        u.inserirTravelBugST();
        travelBugTable.getItems().add(u);
        travelBugs.add(u);
        ownerField.setText("");
        cacheField.setText("");
        cacheDestinoField.setText("");
    }

    /**
     * Handler para acção do botão Remove, responsável pela remoção de um utilizador na utilizadorTable
     * @param actionEvent
     */
    public void handleDeleteUtilizadorAction(ActionEvent actionEvent) {
        ObservableList<Utilizador> vs = utilizadorTable.getSelectionModel().getSelectedItems();
        for (Utilizador utilizador : vs){
            Utilizador.utilizadoresST.get(utilizador.getId()).removerUtilizadorST();
        }
        utilizadorTable.getItems().removeAll(vs);
        utilizadores.removeAll(vs);
    }

    /**
     * Handler para acção do botão Remove, responsável pela remoção de uma cache na cacheTable
     * @param actionEvent
     */
    public void handleDeleteCacheAction(ActionEvent actionEvent) {
        ObservableList<Cache> vs = cacheTable.getSelectionModel().getSelectedItems();
        for (Cache cache : vs){
            Cache.cachesST.get(cache.getId()).removerCacheST();
        }
        cacheTable.getItems().removeAll(vs);
        caches.removeAll(vs);
    }

    /**
     * Handler para acção do botão Remove, responsável pela remoção de um travelBug na travelBugTable
     * @param actionEvent
     */
    public void handleDeleteTravelBugAction(ActionEvent actionEvent) {
        ObservableList<TravelBug> vs = travelBugTable.getSelectionModel().getSelectedItems();
        for (TravelBug travelBug : vs){
            TravelBug.travelBugsBST.get(travelBug.getId()).removerTravelBugST();
        }
        travelBugTable.getItems().removeAll(vs);
        travelBugs.removeAll(vs);
    }

    /**
     * Handler para acção de edição dos dados dos utilizadores na vehiclesTable
     * @param utilizadorStringCellEditEvent
     */
    public void handleEditUtilizadorAction(TableColumn.CellEditEvent<Utilizador, String> utilizadorStringCellEditEvent) {

        Utilizador ut = utilizadorTable.getSelectionModel().getSelectedItem();
        ut.editUtilizador(utilizadorStringCellEditEvent.getNewValue(), null);
    }

    /**
     * Handler para acção de edição dos dados das caches na vehiclesTable
     * @param cacheStringCellEditEvent
     */
    public void handleEditCacheAction(TableColumn.CellEditEvent<Cache, String> cacheStringCellEditEvent) {
      /*  Cache c = cacheTable.getSelectionModel().getSelectedItem();
        //c.editCache((Integer.parseInt(cacheStringCellEditEvent.getNewValue())),null, (Integer.parseInt(cacheStringCellEditEvent.getNewValue())), (Integer.parseInt(cacheStringCellEditEvent.getNewValue())), null);

        int col = cacheStringCellEditEvent.getTablePosition().getColumn();//seleciona coluna
        switch (col){//seleciona linha
            case 0:
                c.setTerreno((Integer.parseInt(cacheStringCellEditEvent.getNewValue())));
                break;
            case 1:
                c.setDificuldade((Integer.parseInt(cacheStringCellEditEvent.getNewValue())));
                break;
        }*/
    }

    /**
     * Handler para acção de edição dos dados dos travelBugs na travelBugTable
     * @param travelBugStringCellEditEvent
     */
    public void handleEditTravelBugAction(TableColumn.CellEditEvent<TravelBug, String> travelBugStringCellEditEvent) {
     /*   int col = travelBugStringCellEditEvent.getTablePosition().getColumn();//seleciona coluna
        switch (col){//seleciona linha
            case 0:
                travelBugStringCellEditEvent.getRowValue().setOwner(travelBugStringCellEditEvent.getNewValue());
                break;
            case 1:
                travelBugStringCellEditEvent.getRowValue().setUserMaisRecente(travelBugStringCellEditEvent.getNewValue());
                break;
            case 2:
                travelBugStringCellEditEvent.getRowValue().setCache(Cache(travelBugStringCellEditEvent.getNewValue()));
                break;
            case 3:
                travelBugStringCellEditEvent.getRowValue().setCacheDestino(Integer.parseInt(travelBugStringCellEditEvent.getNewValue()));
                break;
        }*/
    }

    /**
     * Método para salvar grafo para o path especificado
     */
    public static void saveGrafo() {
        //GRAFO GERAL
        PrintWriter pw = openPrintWriter(PATH_GRAPHtoFile);
        if (pw != null) {
            pw.write(String.valueOf(desenhoGrafo.V()));
            pw.println();
            for (DirectedEdgeNew edge : desenhoGrafo.edges()) {
                pw.write(edge.from() + ";" + edge.to() + ";" + edge.weight() + ";" + edge.time());
                pw.println();
            }
            pw.close();
        }

        //GRAFO PREMIUM
        PrintWriter pwP = openPrintWriter(PATH_GRAPHPremiumToFile);
        if (pwP != null) {
            pwP.write(String.valueOf(desenhoGrafoPremium.V()));
            pwP.println();
            for (DirectedEdgeNew edge : desenhoGrafoPremium.edges()) {
                pwP.write(edge.from() + ";" + edge.to() + ";" + edge.weight() + ";" + edge.time());
                pwP.println();
            }
            pwP.close();
        }

        //GRAFO BASIC
        PrintWriter pwB = openPrintWriter(PATH_GRAPHNBasicToFile);
        if (pwB != null) {
            pwB.write(String.valueOf(desenhoGrafoBasic.V()));
            pwB.println();
            for (DirectedEdgeNew edge : desenhoGrafoBasic.edges()) {
                pwB.write(edge.from() + ";" + edge.to() + ";" + edge.weight() + ";" + edge.time());
                pwB.println();
            }
            pwB.close();
        }
    }

    /**
     * Método para ler grafo do path especificado
     * @throws IOException
     */
    public static void readGrafo() throws IOException {
        //GRAFO GERAL
        BufferedReader br = openBufferedReader(PATH_GRAPHtoFile);
        if (br != null) {
            String v = br.readLine();
            desenhoGrafo = new EdgeWeightedDigraphNew(Integer.parseInt(v));
            String line = br.readLine();
            while (line != null) {
                String[] dFields = line.split(";");
                desenhoGrafo.addEdge(new DirectedEdgeNew(Integer.parseInt(dFields[0]), Integer.parseInt(dFields[1]), Double.parseDouble(dFields[2]), Double.parseDouble(dFields[3])));
                line = br.readLine();
            }
            br.close();
        }

        //GRAFO PREMIUM
        BufferedReader brP = openBufferedReader(PATH_GRAPHPremiumToFile);
        if (brP != null) {
            String v = brP.readLine();
            desenhoGrafoPremium = new EdgeWeightedDigraphNew(Integer.parseInt(v));
            String line = brP.readLine();
            while (line != null) {
                String[] dFields = line.split(";");
                desenhoGrafoPremium.addEdge(new DirectedEdgeNew(Integer.parseInt(dFields[0]), Integer.parseInt(dFields[1]), Double.parseDouble(dFields[2]), Double.parseDouble(dFields[3])));
                line = brP.readLine();
            }
            brP.close();
        }

        //GRAFO BASIC
        BufferedReader brB = openBufferedReader(PATH_GRAPHNBasicToFile);
        if (brB != null) {
            String v = brB.readLine();
            desenhoGrafoBasic = new EdgeWeightedDigraphNew(Integer.parseInt(v));
            String line = brB.readLine();
            while (line != null) {
                String[] dFields = line.split(";");
                desenhoGrafoBasic.addEdge(new DirectedEdgeNew(Integer.parseInt(dFields[0]), Integer.parseInt(dFields[1]), Double.parseDouble(dFields[2]), Double.parseDouble(dFields[3])));
                line = brB.readLine();
            }
            brB.close();
        }
    }

    /**
     * Método para criar grafo
     */
    public void createGraph() {
        graphGroup.getChildren().clear();

        for (int i = 0; i < desenhoGrafo.V(); i++) { //ciclo para criar os circulos
            Random rnd = new Random();
            double X =GROUP_MARGIN + rnd.nextDouble() * (800 - GROUP_MARGIN * 2);  //rangeMin + r.nextDouble() * (rangeMax - rangeMin)
            double Y =GROUP_MARGIN + rnd.nextDouble() * (400 - GROUP_MARGIN * 2);

            Circle c = new Circle(X, Y, radius);
            c.setId("" + i);
            c.setFill(Color.GREEN.brighter());
            //c.setFill(graphColors(cachesColor));
            Text text = new Text("" + i); //por ser string
            StackPane stackPane = new StackPane();
            stackPane.setLayoutX(X - radius);
            stackPane.setLayoutY(Y - radius);
            stackPane.getChildren().addAll(c, text);
            graphGroup.getChildren().add(stackPane);
        }

        for (DirectedEdgeNew de : desenhoGrafo.edges()) {
            int from = de.from();
            int to = de.to();
            StackPane spFrom = (StackPane) graphGroup.getChildren().get(from);
            Circle cFrom = (Circle) spFrom.getChildren().get(0);
            StackPane spTo = (StackPane) graphGroup.getChildren().get(to);
            Circle cTo = (Circle) spTo.getChildren().get(0);
            Line line = new Line(cFrom.getCenterX(), cFrom.getCenterY(), cTo.getCenterX(), cTo.getCenterY());
            graphGroup.getChildren().add(line);
        }
    }

    /**
     * Método para criar grafo de caches premium
     */
    public void createGraphPremium() {
        graphGroupPremium.getChildren().clear();

        for (int i = 0; i < desenhoGrafoPremium.V(); i++) { //ciclo para criar os circulos
            Random rnd = new Random();
            double X =GROUP_MARGIN + rnd.nextDouble() * (800 - GROUP_MARGIN * 2);  //rangeMin + r.nextDouble() * (rangeMax - rangeMin)
            double Y =GROUP_MARGIN + rnd.nextDouble() * (400 - GROUP_MARGIN * 2);

            Circle c = new Circle(X, Y, radius);
            c.setId("" + i);
            c.setFill(Color.GREEN.brighter());
            //System.out.println(cachesColor.keys());
            //c.setFill(graphColors(cachesColor));
            Text text = new Text("" + i); //por ser string
            StackPane stackPane = new StackPane();
            stackPane.setLayoutX(X - radius);
            stackPane.setLayoutY(Y - radius);
            stackPane.getChildren().addAll(c, text);
            graphGroupPremium.getChildren().add(stackPane);
        }

        for (DirectedEdgeNew de : desenhoGrafoPremium.edges()) {
            int from = de.from();
            int to = de.to();
            StackPane spFrom = (StackPane) graphGroupPremium.getChildren().get(from);
            Circle cFrom = (Circle) spFrom.getChildren().get(0);
            StackPane spTo = (StackPane) graphGroupPremium.getChildren().get(to);
            Circle cTo = (Circle) spTo.getChildren().get(0);
            Line line = new Line(cFrom.getCenterX(), cFrom.getCenterY(), cTo.getCenterX(), cTo.getCenterY());
            graphGroupPremium.getChildren().add(line);
        }
    }

    /**
     * Método para criar grafo de caches basic
     */
    public void createGraphBasic() {
        graphGroupBasic.getChildren().clear();

        for (int i = 0; i < desenhoGrafoBasic.V(); i++) { //ciclo para criar os circulos
            Random rnd = new Random();
            double X =GROUP_MARGIN + rnd.nextDouble() * (800 - GROUP_MARGIN * 2);  //rangeMin + r.nextDouble() * (rangeMax - rangeMin)
            double Y =GROUP_MARGIN + rnd.nextDouble() * (400 - GROUP_MARGIN * 2);

            Circle c = new Circle(X, Y, radius);
            c.setId("" + i);
            c.setFill(Color.GREEN.brighter());
            //System.out.println(cachesColor.keys());
            //c.setFill(graphColors(cachesColor));
            Text text = new Text("" + i); //por ser string
            StackPane stackPane = new StackPane();
            stackPane.setLayoutX(X - radius);
            stackPane.setLayoutY(Y - radius);
            stackPane.getChildren().addAll(c, text);
            graphGroupBasic.getChildren().add(stackPane);
        }

        for (DirectedEdgeNew de : desenhoGrafoBasic.edges()) {
            int from = de.from();
            int to = de.to();
            StackPane spFrom = (StackPane) graphGroupBasic.getChildren().get(from);
            Circle cFrom = (Circle) spFrom.getChildren().get(0);
            StackPane spTo = (StackPane) graphGroupBasic.getChildren().get(to);
            Circle cTo = (Circle) spTo.getChildren().get(0);
            Line line = new Line(cFrom.getCenterX(), cFrom.getCenterY(), cTo.getCenterX(), cTo.getCenterY());
            graphGroupBasic.getChildren().add(line);
        }
    }

    public static ST2<Integer, Cache> cachesPremium(){
        for(int cacheID : Cache.cachesST.keys()){
            if(Cache.cachesST.get(cacheID).getTipo().equals(Tipo.PREMIUM)){
                cachesColor.put(cacheID,Cache.cachesST.get(cacheID));
            }
        }
        return cachesColor; //5 10 16
    }

    /**
     * Método para criar as cores dos circulos do grafo dependendo do seu tipo
     * @param color
     * @return
     */
    private Color graphColors(ST2<Integer, Cache> color) {

        cachesPremium();

        System.out.println("hhhhh"+ color.keys());
        for (Integer c : color){
            if (color.get(c).getTipo() == (Tipo.PREMIUM)){
                System.out.println("!!!!!!!\t\t" + c);
                return Color.RED;
            }

            if (color.get(c).getTipo() == (Tipo.BASIC)) return Color.GREEN.brighter();
        }
        return null;
    }

    /**
     * Handler para acção do botão AddEdges, responsável pela inserção de edges ao grafo
     * @param actionEvent
     */
    public void handleAddEdgesToGraphAction(ActionEvent actionEvent){
        desenhoGrafo.addEdge(new DirectedEdgeNew(Integer.parseInt(addFromField.getText()), Integer.parseInt(addToField.getText()), Integer.parseInt(addWeightField.getText()), Integer.parseInt(addTimeField.getText())));
        createGraph();

        addFromField.setText("");
        addToField.setText("");
        addWeightField.setText("");
        addTimeField.setText("");
    }

    /**
     * Handler para acção do botão RemoveEdges, responsável pela remoção de edges do grafo
     * @param actionEvent
     */
    public void handleRemoveEdgesToGraphAction(ActionEvent actionEvent){
        desenhoGrafo.removeEdge(new DirectedEdgeNew(Integer.parseInt(removeFromField.getText()), Integer.parseInt(removeToField.getText()), 0, 0));
        createGraph();

        removeFromField.setText("");
        removeToField.setText("");
    }

    /**
     * Handler para acção do botão EditEdges, responsável pela edição de edges do grafo
     * @param actionEvent
     */
    public void handleEditEdgesToGraphAction(ActionEvent actionEvent){
        for (DirectedEdgeNew de : desenhoGrafo.edges()){
            if ((de.from() == Integer.parseInt(editFromField.getText()) && de.to() == Integer.parseInt(editToField.getText()))){
                desenhoGrafo.removeEdge(new DirectedEdgeNew(Integer.parseInt(editFromField.getText()), Integer.parseInt(editToField.getText()), 0, 0));
                desenhoGrafo.addEdge(new DirectedEdgeNew(Integer.parseInt(editFromField.getText()), Integer.parseInt(editToField.getText()), Integer.parseInt(editWeightField.getText()), Integer.parseInt(editTimeField.getText())));
            }
        }
        createGraph();

        editFromField.setText("");
        editToField.setText("");
        editWeightField.setText("");
        editTimeField.setText("");
    }

    /**
     * Handler para acção do botão ShortestPath, responsável pelo calculo
     * do caminho mais curto de um vertice ao outro
     * @param actionEvent
     */
    public void handleShortestPathAction(ActionEvent actionEvent){
        int from = Integer.parseInt(shortestPathFromField.getText());
        int to = Integer.parseInt(shortestPathToField.getText());

        Grafo.shortestPath(from, to, desenhoGrafo);

        shortestPathFromField.setText("");
        shortestPathToField.setText("");
    }

    /**
     * Handler para acção do botão AddEdges, responsável pela Adição de edges ao grafo premiumn
     * @param actionEvent
     */
    public void handleAddEdgesToGraphPremiumAction(ActionEvent actionEvent){
        desenhoGrafoPremium.addEdge(new DirectedEdgeNew(Integer.parseInt(addFromPremiumField.getText()), Integer.parseInt(addToPremiumField.getText()), Integer.parseInt(addWeightPremiumField.getText()), Integer.parseInt(addTimePremiumField.getText())));
        createGraphPremium();

        addFromPremiumField.setText("");
        addToPremiumField.setText("");
        addWeightPremiumField.setText("");
        addTimePremiumField.setText("");
    }

    /**
     * Handler para acção do botão RemoveEdges, responsável pela remoção de edges do grafo premium
     * @param actionEvent
     */
    public void handleRemoveEdgesToGraphPremiumAction(ActionEvent actionEvent){
        desenhoGrafoPremium.removeEdge(new DirectedEdgeNew(Integer.parseInt(removeFromPremiumField.getText()), Integer.parseInt(removeToPremiumField.getText()), 0, 0));
        createGraphPremium();

        removeFromPremiumField.setText("");
        removeToPremiumField.setText("");
    }

    /**
     * Handler para acção do botão EditEdges, responsável pela edição de edges do grafo premium
     * @param actionEvent
     */
    public void handleEditEdgesToGraphPremiumAction(ActionEvent actionEvent){
        for (DirectedEdgeNew de : desenhoGrafoPremium.edges()){
            if ((de.from() == Integer.parseInt(editFromPremiumField.getText()) && de.to() == Integer.parseInt(editToPremiumField.getText()))){
                desenhoGrafoPremium.removeEdge(new DirectedEdgeNew(Integer.parseInt(editFromPremiumField.getText()), Integer.parseInt(editToPremiumField.getText()), 0, 0));
                desenhoGrafoPremium.addEdge(new DirectedEdgeNew(Integer.parseInt(editFromPremiumField.getText()), Integer.parseInt(editToPremiumField.getText()), Integer.parseInt(editWeightPremiumField.getText()), Integer.parseInt(editTimePremiumField.getText())));
            }
        }
        createGraphPremium();

        editFromPremiumField.setText("");
        editToPremiumField.setText("");
        editWeightPremiumField.setText("");
        editTimePremiumField.setText("");
    }

    /**
     * Handler para acção do botão shortestPath, responsável pelo calculo
     * do caminho mais curto de um vertice ao outro no grafo premium
     * @param actionEvent
     */
    public void handleShortestPathPremiumAction(ActionEvent actionEvent){
        int from = Integer.parseInt(shortestPathFromPremiumField.getText());
        int to = Integer.parseInt(shortestPathToPremiumField.getText());

        Grafo.shortestPath(from, to, desenhoGrafoPremium);

        shortestPathFromPremiumField.setText("");
        shortestPathToPremiumField.setText("");
    }

    /**
     * Handler para acção do botão AddEdges, responsável pela adição de edges ao grafo basic
     * @param actionEvent
     */
    public void handleAddEdgesToGraphBasicAction(ActionEvent actionEvent){
        desenhoGrafoBasic.addEdge(new DirectedEdgeNew(Integer.parseInt(addFromBasicField.getText()), Integer.parseInt(addToBasicField.getText()), Integer.parseInt(addWeightBasicField.getText()), Integer.parseInt(addTimeBasicField.getText())));
        createGraphBasic();

        addFromBasicField.setText("");
        addToBasicField.setText("");
        addWeightBasicField.setText("");
        addTimeBasicField.setText("");
    }

    /**
     * Handler para acção do botão RemoveEdges, responsável pela remoçao de edges do grafo basic
     * @param actionEvent
     */
    public void handleRemoveEdgesToGraphBasicAction(ActionEvent actionEvent){
        desenhoGrafoBasic.removeEdge(new DirectedEdgeNew(Integer.parseInt(removeFromBasicField.getText()), Integer.parseInt(removeToBasicField.getText()), 0, 0));
        createGraphBasic();

        removeFromBasicField.setText("");
        removeToBasicField.setText("");
    }

    /**
     * Handler para acção do botão EditEdges, responsável pela edição de edges do grafo basic
     * @param actionEvent
     */
    public void handleEditEdgesToGraphBasicAction(ActionEvent actionEvent){
        for (DirectedEdgeNew de : desenhoGrafoBasic.edges()){
            if ((de.from() == Integer.parseInt(editFromBasicField.getText()) && de.to() == Integer.parseInt(editToBasicField.getText()))){
                desenhoGrafoBasic.removeEdge(new DirectedEdgeNew(Integer.parseInt(editFromBasicField.getText()), Integer.parseInt(editToBasicField.getText()), 0, 0));
                desenhoGrafoBasic.addEdge(new DirectedEdgeNew(Integer.parseInt(editFromBasicField.getText()), Integer.parseInt(editToBasicField.getText()), Integer.parseInt(editWeightBasicField.getText()), Integer.parseInt(editTimeBasicField.getText())));
            }
        }
        createGraphBasic();

        editFromBasicField.setText("");
        editToBasicField.setText("");
        editWeightBasicField.setText("");
        editTimeBasicField.setText("");
    }

    /**
     * Handler para acção do botão shortestPath, responsável pelo calculo
     * do caminho mais curto de um vertice ao outro no grafo basic
     * @param actionEvent
     */
    public void handleShortestPathBasicAction(ActionEvent actionEvent){
        int from = Integer.parseInt(shortestPathFromBasicField.getText());
        int to = Integer.parseInt(shortestPathToBasicField.getText());

        Grafo.shortestPath(from, to, desenhoGrafoBasic);

        shortestPathFromBasicField.setText("");
        shortestPathToBasicField.setText("");
    }
}