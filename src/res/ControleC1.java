package res;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * FXML Controller class
 *
 * @author nataniel
 */
public class ControleC1 implements Initializable {

	private List<TextField> lisTex = new ArrayList();
	private List<ChoiceBox<String>> lisCb = new ArrayList();
	private List<Label> lisLb = new ArrayList();
	@FXML
	private Label pre1, pre2, pre3, pre4;
	@FXML
	private Button btn_fim, btn_previa;
	@FXML
	private TextField entra1, entra2, entra3, entra4;
	@FXML
	public ChoiceBox<String> comb1, comb2, comb3, comb4;

	public void acSelec(Event evt) {
		FileChooser fch = new FileChooser();
		List<File> arquivos = fch.showOpenMultipleDialog(btn_fim.getContextMenu());
		Alert ale = null;
		try {
			ale = new Alert(AlertType.CONFIRMATION);
			ale.setTitle("Confirmando...");
			ale.setHeaderText("Foi selecionado " + arquivos.size() + " arquivos...");
			ale.setContentText("Deseja continuar?");
			ale.getButtonTypes().clear();
			ale.getButtonTypes().add(ButtonType.NO);
			ale.getButtonTypes().add(ButtonType.YES);
		} catch (Exception e) {
		}

		if (arquivos != null && ale.showAndWait().get().getText().equals("Sim")) {
			List<String> constaS = obterConst();
			List<String> numeros = obterNumero();

			for (int i = 0; i != arquivos.size(); i++) {
				File arq = arquivos.get(i);

				StringBuilder bstr = new StringBuilder();
				for (TextField tf : lisTex) {
					String tt = tf.getText();
					if (!tt.equals("")) {
						if (constaS.contains(tt)) {
							bstr.append(tt);
						} else if (numeros.contains(tt)) {
							try {
								if (numeros.contains(tt)) {
									if (tt.startsWith("0")) {
										char[] arr = tt.toCharArray();
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
									} else {
										int num = Integer.valueOf(tt) + i;
										bstr.append(num);
									}
								}
							} catch (Exception e) {
							}
						}
					}
				}
				arq.renameTo(renomear(bstr.toString(), arq));
			}
			ale = new Alert(AlertType.INFORMATION);
			ale.setTitle("Pronto");
			ale.setHeaderText("Concluido a renomeação!");
			ale.showAndWait();
		}
		for (Label lb : lisLb) {
			lb.setText("");
		}
		btn_fim.setVisible(false);
	}

	private File renomear(String nome, File antigo) {
		String path = antigo.getPath();
		String no = antigo.getName().replace(path, "");
		path = path.replace(no, "");
		String exten = no.substring(no.lastIndexOf("."));
		nome = nome.replace(exten, "");
		return new File(path.concat(nome.concat(exten)));
	}

	private List<String> obterConst() {
		List<String> constaS = new ArrayList();
		for (int i = 0; i <= 3; i++) {
			if (lisCb.get(i).getSelectionModel().getSelectedItem().equals("Constante")
					&& !lisTex.get(i).getText().equals("")) {
				constaS.add(lisTex.get(i).getText());
			}
		}
		return constaS;
	}

	private List<String> obterNumero() {
		List<String> numeros = new ArrayList();
		for (int i = 0; i <= 3; i++) {
			if (lisCb.get(i).getSelectionModel().getSelectedItem().equals("Numeral")
					&& !lisTex.get(i).getText().equals("")) {

				try {
					int val = Integer.valueOf(lisTex.get(i).getText());
					if (lisTex.get(i).getText().startsWith("0")) {
						numeros.add(lisTex.get(i).getText());
					} else {
						numeros.add(Integer.toString(val));
					}
				} catch (NumberFormatException e) {
					Alert ale = new Alert(AlertType.WARNING);
					ale.setTitle("No espaço de numeros é pra ter numeros!");
					ale.setContentText("Foi inserido outros caracteres no espaço\n"
							+ "numeros, o mesmo foi ignorado na previa ou no arquivo\n" + "final.");
					ale.showAndWait();
				}
			}
		}
		return numeros;
	}

	public void acBox(Event evt) {
		List<String> constaS = obterConst();
		List<String> numeros = obterNumero();
		int quant = 0;
		for (int i = 0; i < 4; i++) {
			StringBuilder bstr = new StringBuilder();
			for (TextField tf : lisTex) {
				String tt = tf.getText();
				if (!tt.equals("")) {
					if (constaS.contains(tt)) {
						bstr.append(tt);
					}
					try {
						if (numeros.contains(tt)) {
							// Se o texto é um numero ele passa.
							if (tt.startsWith("0")) {
								// Se o numero começa com zero passa aqui
								char[] arr = tt.toCharArray();
								// transforma a string em array de char
								int num = Integer.parseInt(Character.toString(arr[arr.length - 1]));
								// (problema) transforma o ultimo char em numero
								num += i; // aumenta mais um o numero anterior
								// fins de teste
								if (num < 10) {
									arr[arr.length - 1] = String.valueOf(num).charAt(0);
									/*
									 * e inserido novamente no array o numero transformado o numero em String para
									 * pegar o char.
									 */
									bstr.append(arr);
									// Inserido no construdor de String
								} else {
									// caso a soma resulta em um numero maior que 9 ->
									// 9 + 1
									for (int j = 0; j < arr.length - 2; j++) {
										char c = arr[j];
										// Pego todos os caracteres menos o ultimo.
										bstr.append(c);
									}
									// e finalmente adiciono o ultimo, porém ja incrementado
									bstr.append(num);
								}
							} else {
								// caso não comece com zero
								int num = Integer.valueOf(tt) + i;
								bstr.append(num);
							}
						}
					} catch (Exception e) {
					}
				}
			}
			quant = bstr.toString().length();
			lisLb.get(i).setText(bstr.toString());
		}
		if (quant != 0) {
			btn_fim.setVisible(true);
		}else {
			btn_fim.setVisible(false);
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		String[] strA = new String[] { "Desativado", "Numeral", "Constante" };
		lisCb.add(comb1);
		lisTex.add(entra1);
		lisLb.add(pre1);
		lisCb.add(comb2);
		lisTex.add(entra2);
		lisLb.add(pre2);
		lisCb.add(comb3);
		lisTex.add(entra3);
		lisLb.add(pre3);
		lisCb.add(comb4);
		lisTex.add(entra4);
		lisLb.add(pre4);
		lisCb.forEach((cb) -> {
			cb.getItems().addAll(strA);
			cb.getSelectionModel().select(0);
		});
	}

}
