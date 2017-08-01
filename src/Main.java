
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import res.ControleCena1;

/**
 *
 * @author nataniel
 */
public class Main extends Application {
	private static long index = 0;
	private static Supplier<Stream<Path>> lista = null;
	private static boolean bo = false;
	private static boolean fim = false;
	private static boolean regra = false;

	@Override
	public void start(Stage ps) {
		Parent par = null;
		FXMLLoader lod = new FXMLLoader(getClass().getResource("/res/cena1.fxml"));
		try {
			par = lod.load();
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
		ControleCena1 contro = (ControleCena1) lod.getController();
		Scene sc = new Scene(par);
		ps.setScene(sc);
		ps.setResizable(false);
		ps.setTitle("Renomear");
		ps.show();
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		if (args.length > 0 && args[0].equals("gm")) {
			launch(args);
		} else if (args.length > 0 && args[0].equals("tm")) {
			try {
				File f = new File(System.getProperty("user.dir").concat("/renomear_nataniel_A03.temp"));
				if (!f.exists()) {
					f.createNewFile();
					System.out.println("**********************************************");
					System.out.println("* Bem vindo ao meu programa em modo de texto *");
					System.out.println("* Essa é a versão: ALFA_03                   *");
					System.out.println("* Criado por nataniel.						 *");
					System.out.println("* Telegram: @Neoold							 *");
					System.out.println("* Email: natanieljava@gmail.com				 *");
					System.out.println("* Log (Somente do modo de texto)             *");
					System.out.println("*                                            *");
					System.out.println("* #ALFA_03 -> Inserido o modo de texto com   *");
					System.out.println("* os mesmos recursos que a versão grafica    *");
					System.out.println("* possui.                                    *");
					System.out.println("**********************************************");
				}
			} catch (IOException ex) {
			}
			Scanner ent = new Scanner(System.in);
			while (!fim) {
				System.out.println("Informe o local da pasta");
				String cam = ent.nextLine();
				try {
					File f = new File(cam);
					Files.list(Paths.get(new File(cam).toURI()));
					lista = () -> {
						try {
							return Files.list(Paths.get(new File(cam).toURI()));
						} catch (IOException e) {

						}
						return null;
					};
					System.out.println("Existe " + lista.get().count() + " arquivo(s), deseja continuar?");
					System.out.println("1->Sim\n2->Não");
					switch (ent.nextLine()) {
					case "1":
						List<String> regras = new ArrayList<>();
						while (!regra) {
							System.out.println("Agora informe a regra:");
							System.out.println("1-Numeral" + "\n2-Constante" + "\n3-parar e aplicar regra"
									+ "\n4-parar, limpar regra(s) antiga(s) e adicionar nova(s) regra(s)");
							String nuu = ent.nextLine();
							switch (nuu) {
							case "1":
								System.out.println("Informe o numero:");
								while (true) {
									String num = ent.nextLine();
									if (num.matches("\\S[0-9]*")) {
										regras.add(num.concat("_"));
										System.out.println("Adicionado o numero \"" + num + "\"");
										break;
									} else {
										System.out
												.println("Informe somente numeros, use 'constante' para por espacos.");
									}
								}
								break;
							case "2":
								System.out.println("Informe a constante:");
								regras.add(ent.nextLine().concat("\""));
								break;
							case "3":
								if (!regras.isEmpty()) {
									bo = true;
								} else {
									System.out.println("Ops, voce não informou regra alguma.");
								}
								break;
							case "4":
								regras.clear();
								System.out.println("Regras limpa.");
								break;
							default:
								System.out.println("Opa não entendi, escreva somente oque é pedido.");
								break;
							}

							while (bo) {
								index = 0;
								System.out.println("Deseja ver uma previa?" + "\n1->Sim, com tres (3) arquivos"
										+ "\n2->Sim, com todos os arquivos" + "\n3->Não, voltar pra regra"
										+ "\n4->Não, executar regra" + "\n5->Não, executar regra com log");
								String ee = ent.nextLine();
								switch (ee) {
								case "1":
									renomear(lista, regras, 1);
									break;
								case "2":
									renomear(lista, regras, 3);
									break;
								case "3":
									bo = false;
									regras.clear();
									break;
								case "4":
									long tm = System.currentTimeMillis();
									renomear(lista, regras, 0);
									System.out.println("Demorou " + Long.toString(System.currentTimeMillis() - tm)
											+ " milissegundos");
									bo = false;
									regra = true;
									fim = true;
									break;
								case "5":
									tm = System.currentTimeMillis();
									renomear(lista, regras, 2);
									System.out.println("Demorou " + Long.toString(System.currentTimeMillis() - tm)
											+ " milissegundos");
									bo = false;
									regra = true;
									fim = true;
									break;
								default:
									System.out.println("Opa não entendi, escreva somente oque é pedido.");
									break;

								}
							}
						}
						break;
					}
				} catch (Exception e) {
					System.out.println(
							"Opa, parece que deu um erro no caminho da pasta, olha... eu recebi isso:\n" + cam);
					System.out.println("Informe outro caminho ou revise o mesmo.");
				}
			}
			ent.close();
			System.exit(0);

		} else

		{
			launch(args);
		}
	}

	/**
	 * Metodo para renomear o arquivo em loop
	 * 
	 * @param lista
	 *            Lista contendo os diretorios do arquivo
	 * @param regras
	 *            Lista contendo as regras
	 * @param modo
	 *            Regra 0-> Renomear sem log Regra 1-> Previa com tres itens Regra
	 *            2-> Renomar com log. Regra 3-> Previa com todos os itens
	 */
	private static void renomear(Supplier<Stream<Path>> lista, List<String> regras, int modo) {
		if (modo == 1) {
			for (int i = 0; i != 3; i++) {
				StringBuilder bu = new StringBuilder();
				for (int ii = 0; ii != regras.size(); ii++) {
					String num = regras.get(ii);
					if (num.contains("\"")) {
						bu.append(num.replaceAll("\"", ""));
					} else if (num.contains("_")) {
						num = num.replaceAll("_", "");
						if (num.startsWith("0")) {
							char[] arr = num.toCharArray();
							int numer = Integer.parseInt(Character.toString(arr[arr.length - 1]));
							numer += index;
							if (numer < 10) {
								arr[arr.length - 1] = String.valueOf(numer).charAt(0);
								bu.append(arr);
							} else {
								for (int j = 0; j < arr.length - 2; j++) {
									char c = arr[j];
									bu.append(c);
								}
								bu.append(numer);
							}

						}
					}
				}
				index++;
				System.out.println("Previa " + index + "º " + bu);
			}
		}
		lista.get().forEach((e) -> {
			StringBuilder bu = new StringBuilder();
			for (int ii = 0; ii != regras.size(); ii++) {
				String num = regras.get(ii);
				if (num.contains("\"")) {
					bu.append(num.replaceAll("\"", ""));
				} else if (num.contains("_")) {
					num = num.replaceAll("_", "");
					if (num.startsWith("0")) {
						char[] arr = num.toCharArray();
						int numer = Integer.parseInt(Character.toString(arr[arr.length - 1]));
						numer += index;
						if (numer < 10) {
							arr[arr.length - 1] = String.valueOf(numer).charAt(0);
							bu.append(arr);
						} else {
							for (int j = 0; j < arr.length - 2; j++) {
								char c = arr[j];
								bu.append(c);
							}
							bu.append(numer);
						}

					}
				}

			}
			index++;
			switch (modo) {
			case 0:
				e.toFile().renameTo(renomear(bu.toString(), e.toFile()));
				break;
			case 2:
				System.out.println("Renomeando arquivo \"" + e.getFileName() + "\" para \""
						+ bu.toString().concat(
								e.getFileName().toString().substring(e.getFileName().toString().lastIndexOf(".")))
						+ "\"");
				e.toFile().renameTo(renomear(bu.toString(), e.toFile()));
				break;
			case 3:
				System.out.println("Previa " + index + "º \"" + bu + "\"");
			}
		});
	}

	private static File renomear(String nome, File antigo) {
		String path = antigo.getPath();
		String no = antigo.getName().replace(path, "");
		path = path.replace(no, "");
		String exten = no.substring(no.lastIndexOf("."));
		nome = nome.replace(exten, "");
		return new File(path.concat(nome.concat(exten)));
	}

}
