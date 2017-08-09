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
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;

public class ControleCena1 implements Initializable {

    @FXML
    private ChoiceBox<String> regraT;

    private List<TableColumn<ObservableList<String>, String>> colunas = new ArrayList<>();

    private ArrayList<List<String>> lista = new ArrayList<>();

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

    @FXML
    private EventHandler<ActionEvent> actAlterarTipo = (e) -> {
        MenuItem ii = (MenuItem) e.getSource();
        regraT.getSelectionModel().select(ii.getText());
    };

    private EventHandler<ActionEvent> actAddTabela = (e) -> {
        String tipo = regraT.getValue() == null ? "nulo" : regraT.getValue();
        String conteudo = regraC.getText();
        List<String> ls = null;
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
                if (statics.Subtituir.validar(conteudo)) {
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
            ale.setTitle("Versão 5.0.1 - Beta");
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
        buttonPrevia.setOnAction((e) -> {
            FileChooser fcho = new FileChooser();
            List<File> fil = fcho.showOpenMultipleDialog(buttonPrevia.getScene().getWindow());
            if (fil != null) {
                this.fil = fil;
                tpane.getTabs().get(1).setDisable(false);
                tpane.getSelectionModel().select(1);
                tpane.getTabs().get(0).setDisable(true);
                List<String> arquivoA = new ArrayList<>();
                List<String> arquivoN = new ArrayList<>();
                boolean semNumero = false;
                for (List<String> s : lista) {
                    if (s.get(1).equals("Substituir") && s.get(2).contains("$num")){
                        semNumero = false;
                    }else if (!s.get(1).equals("Numeral")) {
                        semNumero = true;
                        break;
                    } else {
                        semNumero = false;
                    }
                }
                if (semNumero) {
                    List<String> numeral = new ArrayList<>();
                    numeral.add("");
                    numeral.add("Constante");
                    numeral.add(" ");
                    lista.add(numeral);
                    numeral=new ArrayList<>();
                    numeral.add("");
                    numeral.add("Numeral");
                    numeral.add("01");
                    lista.add(numeral);
                }
                for (int i = 0; i != fil.size(); i++) {
                    File f = fil.get(i);
                    StringBuilder bu = new StringBuilder();
                    for (List<String> list : lista) {
                        String cont = list.get(2);
                        String tipo = list.get(1);
                        switch (tipo) {
                            case ("Numeral"):
                                cont = statics.Numeral.processarN(cont, i);
                                bu.append(cont);
                                break;
                            case "Constante":
                                bu.append(cont);
                                break;
                            case "Substituir":
                                //cont é a entrada
                                String[] dados = statics.Subtituir.processarN(cont);
                                String antt = renomearString(f);
                                bu.append(antt.contains(dados[0]) ? antt.replace(dados[0], dados[1]) : antt);
                                break;
                        }
                    }
                    arquivoA.add(renomearString(f));
                    arquivoN.add(bu.toString());
                }
                lista.clear();
                colunas.clear();
                criarColunas2();
                for (int i = 0; i != fil.size(); i++) {
                    List<String> cont = new ArrayList<>();
                    cont.add(arquivoA.get(i));
                    cont.add(arquivoN.get(i));
                    lista.add(cont);
                }
                atualizarTabela2(lista, colunas);
            }
        });
        voltar.setOnAction((e) -> {
            limpar();
        });
        aplicar.setOnAction((e) -> {
            limpar();
            for (int i = 0; i != fil.size(); i++) {
                File f = fil.get(i);
                f.renameTo(renomearArquivo(tablePrevia.getItems().get(i).get(1), f));
            }

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

    private File renomearArquivo(String nome, File antigo) {
        String path = antigo.getPath();
        String no = antigo.getName().replace(path, "");

        String[] aa = path.split(File.separator);
        StringBuilder bu = new StringBuilder();
        for (int i = 0; i < aa.length - 1; i++) {
            String st = aa[i];
            bu.append(st).append(File.separator);
        }
        path = bu.toString();
        String exten = no.contains(".") ? no.substring(no.lastIndexOf(".")) : "";
        return new File(path.concat(nome.concat(exten)));
    }
}
