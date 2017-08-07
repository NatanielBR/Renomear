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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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
	private Button remover;

	@FXML
	private TableView<ObservableList<String>> tabela;

	@FXML
	private Button buttonPrevia;

	@FXML
	private TableView<ObservableList<String>> tablePrevia;
	private EventHandler<ActionEvent> actAddTabela= (e)->{
		String tipo = regraT.getValue() == null ? "nulo" : regraT.getValue();
		String conteudo = regraC.getText();
		switch (tipo) {
		case "Numeral":
			if (conteudo.matches("[0-9]*") && !conteudo.equals("")) {
				List<String> ls = new ArrayList<>();
				ls.add(Integer.toString(prioridade));
				ls.add(tipo);
				ls.add(conteudo);
				lista.add(ls);
				atualizarTabela(lista, colunas);
				prioridade++;
				if (tabela.getItems().size()>0) {
					buttonPrevia.setDisable(false);
				}
			}else {
				Alert ale=new Alert(AlertType.INFORMATION);
				ale.setTitle("Aconteceu um probleminha!");
				ale.setHeaderText("Acho que vi algo que não foi numero ai!");
				ale.setContentText("No tipo \"Numeral\" não se deve colocar \nespaços ou letras.");
				ale.showAndWait();
			}
			break;
		case "Constante":
			List<String> ls = new ArrayList<>();
			ls.add(Integer.toString(prioridade));
			ls.add(tipo);
			ls.add(conteudo);
			lista.add(ls);
			atualizarTabela(lista, colunas);
			prioridade++;
			if (tabela.getItems().size()>0) {
				buttonPrevia.setDisable(false);
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
		
		String[] nomes = new String[] { "Numeral", "Constante" };
		regraT.getItems().addAll(nomes);
		criarColunas();
		criado.setOnMouseClicked((e)->{
			Alert ale=new Alert(AlertType.INFORMATION);
			ale.setTitle("Versão 4.1");
			ale.setHeaderText("Criado por nataniel!");
			ale.setContentText("Contato:\nTelegram: @Neoold\nEmail: natanieljava@gmail.com");
			ale.show();
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
			if (tabela.getItems().size()==0) {
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
				for (int i = 0; i != fil.size(); i++) {
					File f = fil.get(i);
					StringBuilder bu = new StringBuilder();
					for (List<String> list : lista) {
						String cont = list.get(2);
						if (list.get(1).equals("Numeral")) {
							if (cont.startsWith("0")) {
								StringBuilder bstr = new StringBuilder();
								char[] arr = cont.toCharArray();
								int num = Integer.parseInt(Character.toString(arr[arr.length - 1]));
								num += i;
								if (num < 10) {
									arr[arr.length - 1] = String.valueOf(num).charAt(0);
									bstr.append(arr);
								} else {
									for (int j = 0; j < arr.length - 2; j++) {
										char c = arr[j];
										bstr.append(c);
									}
									bstr.append(num);
								}
								cont = bstr.toString();
							} else {
								cont = Integer.toString(Integer.parseInt(cont) + i);
							}
						}
						bu.append(cont);
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
		regraC.setOnKeyPressed((e)->{
			if (e.getCode()==KeyCode.S && e.isControlDown()) {
				regraT.getSelectionModel().select(0);
			}else if (e.getCode()==KeyCode.D && e.isControlDown()) {
				regraT.getSelectionModel().select(1);
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
		String[] nomes = new String[] { "Prioridade", "Tipo", "Conteudo" };
		for (int i = 0; i < nomes.length; i++) {
			TableColumn<ObservableList<String>, String> col = new TableColumn<>(nomes[i]);
			colunas.add(col);
		}
		tabela.getColumns().addAll(colunas);
	}

	public void criarColunas2() {
		String[] nomes = new String[] { "Antigo nome", "Novo nome" };
		for (int i = 0; i < nomes.length; i++) {
			TableColumn<ObservableList<String>, String> col = new TableColumn<>(nomes[i]);
			colunas.add(col);
		}
		tablePrevia.getColumns().addAll(colunas);
	}

	private String renomearString(File antigo) {
		String path = antigo.getPath();
		String no = antigo.getName().replace(path, "");
		path = path.replace(no, "");
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
