package res;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import sun.audio.AudioPlayer;

public class ControleCena1 implements Initializable {

    @FXML
    private ChoiceBox<String> regraT;

    private List<TableColumn<ObservableList<String>, String>> colunas = new ArrayList<>();

    private ArrayList<List<String>> lista = new ArrayList<>();
    private ArrayList<List<String>> listaF = new ArrayList<>();

    private List<File> fil = null;

    private int prioridade = 1;

    @FXML
    private TextField regraC;

    @FXML
    private TabPane tpane;

    @FXML
    private Label criado;

    @FXML
    private Button adicionar;

    @FXML
    private MenuItem miC;

    @FXML
    private MenuItem miS;

    @FXML
    private MenuItem miN;

    @FXML
    private MenuItem miSobre;

    @FXML
    private MenuItem miComo;

    @FXML
    private Button remover;

    @FXML
    private MenuItem miSair;

    @FXML
    private TableView<ObservableList<String>> tabela;

    @FXML
    private Button buttonPrevia;

    @FXML
    private TableView<ObservableList<String>> tablePrevia;

    private EventHandler<ActionEvent> actAlterarTipo = (e) -> {
        MenuItem ii = (MenuItem) e.getSource();
        regraT.getSelectionModel().select(ii.getText());
    };

    private EventHandler<ActionEvent> actAddTabela = (e) -> {
        String tipo = regraT.getValue() == null ? "nulo" : regraT.getValue();
        String conteudo = regraC.getText();
        criarBackup(lista);
        adicionarLista(tipo, conteudo);
    };
    private EventHandler<ActionEvent> actPreviaDois = (e) ->{
        Alert ale=new Alert(AlertType.INFORMATION);
        ale.setTitle("Aguarde");
        ale.setContentText("Aguarde, pois a renomeação ainda não acabou.");
        ale.showAndWait();
    };
    private EventHandler<ActionEvent> actPreviaUm = (e) -> {
        FileChooser fcho = new FileChooser();
        List<File> fil = fcho.showOpenMultipleDialog(buttonPrevia.getScene().getWindow());
        if (fil != null) {
            this.fil = fil;
            tpane.getTabs().get(1).setDisable(false);
            tpane.getSelectionModel().select(1);
            tpane.getTabs().get(0).setDisable(true);
            Object[] arquivos = statics.Renomear.criarRenomeacao(lista, fil);
            List<String> arquivoA = (List<String>) arquivos[0];
            List<String> arquivoN = (List<String>) arquivos[1];
            listaF = new ArrayList<>();
            colunas.clear();
            criarColunas2();
            for (int i = 0; i != fil.size(); i++) {
                List<String> cont = new ArrayList<>();
                cont.add(arquivoA.get(i));
                cont.add(arquivoN.get(i));
                listaF.add(cont);
            }
            atualizarTabela2(listaF, colunas);
        }
    };
    @FXML
    private Button aplicar;

    @FXML
    private Button voltar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String[] nomes = new String[]{"Constante", "Numeral", "Substituir"};
        regraT.getItems().addAll(nomes);
        criarColunas();
        miC.setOnAction(actAlterarTipo);
        miS.setOnAction(actAlterarTipo);
        miN.setOnAction(actAlterarTipo);

        miSobre.setOnAction((e) -> {
            Alert ale = new Alert(AlertType.INFORMATION);
            ale.setTitle("Versão 5.2 - Estavel");
            ale.setHeaderText("Criado por nataniel!");
            ale.setContentText("Contato:\nTelegram: @Neoold\nEmail: natanieljava@gmail.com");
            ale.show();
        });

        miSair.setOnAction((e) -> {
            System.exit(0);
        });
        miComo.setOnAction((e) -> {
            Alert ale = new Alert(AlertType.NONE);
            FXMLLoader lod = new FXMLLoader();
            try {
                ale.getDialogPane().setContent(lod.load(getClass().getResource("/res/cenaAjuda.fxml").openStream()));
                ale.setTitle("Um guia basico sobre o Renomear");
                ale.setWidth(656);
                ale.setHeight(400);
                ale.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                ale.showAndWait();
            } catch (Exception ee) {
                ee.printStackTrace();
            }

        });

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, old, neew) -> {
            if (neew != null) {
                remover.setDisable(false);
                remover.setTooltip(new Tooltip(neew.get(2)));
            }
        });
        remover.setOnAction((e) -> {
            ObservableList<String> st = tabela.getSelectionModel().getSelectedItem();
            int ii = -1;
            for (int i = 0; i != lista.size(); i++) {
                List<String> list = lista.get(i);
                if (Arrays.equals(st.toArray(), list.toArray())) {
                    ii = i;
                }
            }
            lista.remove(ii);
            prioridade = 1;
            for (int i = 0; i != lista.size(); i++) {
                List<String> list = lista.get(i);
                list.set(0, Integer.toString(prioridade));
                prioridade++;
            }
            atualizarTabela(lista, colunas);
            if (tabela.getItems().isEmpty()) {
                buttonPrevia.setDisable(true);
            }
            remover.setDisable(true);
        });
        buttonPrevia.setOnAction(actPreviaUm);
        voltar.setOnAction((e) -> {
            tpane.getTabs().get(0).setDisable(false);
            tpane.getSelectionModel().select(0);
            tpane.getTabs().get(1).setDisable(true);
            colunas.clear();
            criarColunas();
            atualizarTabela(lista, colunas);
        });
        aplicar.setOnAction((ActionEvent e) -> {
            limpar();
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    ObservableList<ObservableList<String>> l = tablePrevia.getItems();
                    long a=System.currentTimeMillis();
                    buttonPrevia.setOnAction(actPreviaDois);
                    buttonPrevia.setText("Aguarde");
                    System.out.println("Aguarde");
                    int i = 0;
                    for (File f : fil) {
                        f.renameTo(renomearArquivo(l.get(i).get(1), f));
                        System.out.println(i);
                        i++;
                    }
                    System.out.println("fim");
                    buttonPrevia.setText("Ver Previa");
                    buttonPrevia.setOnAction(actPreviaUm);
                    a=System.currentTimeMillis()-a;
                    Alert alert=new Alert(AlertType.INFORMATION);
                    alert.setContentText("Demorou "+a+" milisegundos para renomear "+fil.size()+" arquivos");
                    
                    alert.show();
                    return null;
                }
            };
            task.run();
        });
        adicionar.setOnAction(actAddTabela);
        regraC.setOnAction(actAddTabela);
        regraC.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.ENTER && e.isControlDown()) {
                int a = regraT.getSelectionModel().getSelectedIndex();
                if (regraT.getItems().size() == a + 1) {
                    a = 0;
                } else {
                    a++;
                }
                regraC.setText("");
                regraT.getSelectionModel().select(a);
            }
        });
    }

    private void limpar() {
        tpane.getTabs().get(0).setDisable(false);
        tpane.getSelectionModel().select(0);
        tpane.getTabs().get(1).setDisable(true);
        tabela.getItems().clear();
        tabela.getColumns().clear();
        prioridade = 1;
        colunas.clear();
        criarColunas();
        lista.clear();
        regraC.setText("");
        regraT.getSelectionModel().clearSelection();
    }

    private void criarBackup(ArrayList<List<String>> back) {
        listaF = back;
    }

    private void atualizarTabela(ArrayList<List<String>> conteudo,
            List<TableColumn<ObservableList<String>, String>> tbc) {
        tabela.getColumns().clear();
        tabela.getItems().clear();
        for (int i = 0; i < tbc.size(); i++) {
            int ss = i;
            TableColumn<ObservableList<String>, String> Col = tbc.get(i);
            Col.setCellValueFactory((param) -> {
                StringProperty spt;
                if (ss >= param.getValue().size()) {
                    spt = new SimpleStringProperty("");
                } else {
                    spt = new SimpleStringProperty(param.getValue().get(ss));
                }
                return spt;
            });
            tabela.getColumns().add(Col);
        }
        conteudo.forEach((e) -> {
            tabela.getItems().add(FXCollections.observableArrayList(e));
        });
    }

    private void atualizarTabela2(ArrayList<List<String>> conteudo,
            List<TableColumn<ObservableList<String>, String>> tbc) {
        tablePrevia.getColumns().clear();
        tablePrevia.getItems().clear();
        regraC.setText("");
        for (int i = 0; i < tbc.size(); i++) {
            int ss = i;
            TableColumn<ObservableList<String>, String> Col = tbc.get(i);
            Col.setCellValueFactory((param) -> {
                StringProperty spt;
                if (ss >= param.getValue().size()) {
                    spt = new SimpleStringProperty("");
                } else {
                    spt = new SimpleStringProperty(param.getValue().get(ss));
                }
                return spt;
            });
            tablePrevia.getColumns().add(Col);
        }
        conteudo.forEach((e) -> {
            tablePrevia.getItems().add(FXCollections.observableArrayList(e));
        });
    }

    public void criarColunas() {
        String[] nomes = new String[]{"Prioridade", "Tipo", "Conteudo"};
        for (int i = 0; i < nomes.length; i++) {
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(nomes[i]);
            if (i == 2) {
                col.setCellFactory(TextFieldTableCell.forTableColumn());
                col.setOnEditCommit((e) -> {
                    ObservableList<String> arr = e.getRowValue();
                    for (int j = 0; j != lista.size(); j++) {
                        List<String> ll = lista.get(j);
                        if (ll.equals(arr)) {
                            String tipo = arr.get(1);
                            String conteudo = e.getNewValue();
                            adicionarListaIndex(tipo, conteudo, j);
                            criarBackup(lista);
                            atualizarTabela(lista, colunas);
                        }
                    }

                });
            }
            colunas.add(col);
        }
        tabela.getColumns().addAll(colunas);
    }

    public void criarColunas2() {
        String[] nomes = new String[]{"Antigo nome", "Novo nome"};
        for (int i = 0; i < nomes.length; i++) {
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(nomes[i]);
            colunas.add(col);
        }
        tablePrevia.getColumns().addAll(colunas);
    }

    private String renomearString(File antigo) {
        String path = antigo.getPath();
        String no = antigo.getName().replace(path, "");
        String exten = no.contains(".") ? no.substring(no.lastIndexOf(".")) : "";
        return no.replace(exten, "");
    }

    private void adicionarListaIndex(String tipo, String conteudo, int j) {
        switch (tipo) {
            case "Numeral":
                if (statics.Numeral.validar(conteudo)) {
                    List<String> a = lista.get(j);
                    a.set(2, conteudo);
                    lista.set(j, a);
                }
                break;
            case "Constante":
                lista.get(j).remove(2);
                lista.get(j).add(conteudo);
                break;
            case "Substituir":
                if (statics.Substituir.validar(conteudo)) {
                    lista.get(j).remove(2);
                    lista.get(j).add(conteudo);

                }
                break;
        }
    }

    private void adicionarLista(String tipo, String conteudo) {
        List<String> ls;
        switch (tipo) {
            case "Numeral":
                if (statics.Numeral.validar(conteudo)) {
                    ls = new ArrayList<>();
                    ls.add(Integer.toString(prioridade));
                    ls.add(tipo);
                    ls.add(conteudo);
                    lista.add(ls);
                    atualizarTabela(lista, colunas);
                    prioridade++;
                    if (tabela.getItems().size() > 0) {
                        buttonPrevia.setDisable(false);
                    }
                } else {
                    Alert ale = new Alert(AlertType.INFORMATION);
                    ale.setTitle("Aconteceu um probleminha!");
                    ale.setHeaderText("Acho que vi algo que não foi numero ai!");
                    ale.setContentText("No tipo \"Numeral\" não se deve colocar \nespaços ou letras.");
                    ale.showAndWait();
                }
                break;
            case "Constante":
                ls = new ArrayList<>();
                ls.add(Integer.toString(prioridade));
                ls.add(tipo);
                ls.add(conteudo);
                lista.add(ls);
                atualizarTabela(lista, colunas);
                prioridade++;
                if (tabela.getItems().size() > 0) {
                    buttonPrevia.setDisable(false);
                }
                break;
            case "Substituir":
                if (statics.Substituir.validar(conteudo)) {
                    ls = new ArrayList<>();
                    ls.add(Integer.toString(prioridade));
                    ls.add(tipo);
                    ls.add(conteudo);
                    lista.add(ls);
                    atualizarTabela(lista, colunas);
                    prioridade++;
                    if (tabela.getItems().size() > 0) {
                        buttonPrevia.setDisable(false);
                    }
                }
                break;
        }
    }

    private File renomearArquivo(String nome, File antigo) {
        String path = antigo.getPath();
        String no = antigo.getName().replace(path, "");
        path = antigo.getAbsolutePath().replace(no, "");
        String exten = no.contains(".") ? no.substring(no.lastIndexOf(".")) : "";
        return new File(path.concat(nome.concat(exten)));
    }
}
