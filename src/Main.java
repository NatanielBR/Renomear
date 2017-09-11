
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import res.ControleCena1;

/**
 *
 * @author nataniel
 */
public class Main extends Application {

    private static Supplier<Stream<Path>> lista = null;
    private static boolean bo = false;
    private static boolean fim = false;
    private static boolean regra = false;
    private static List<String> arquivoA = null;
    private static List<String> arquivoN = null;

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
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("gm")) {
            launch(args);
        } else if (args.length > 0 && args[0].equals("tm")) {
            try {
                File f = new File(System.getProperty("user.dir").concat("/renomear_nataniel_A05.temp"));
                if (!f.exists()) {
                    f.createNewFile();
                    System.out.println("**********************************************");
                    System.out.println("* Bem vindo ao meu programa em modo de texto *");
                    System.out.println("* Essa é a versão: 5.0                       *");
                    System.out.println("* Criado por nataniel.			     *");
                    System.out.println("* Telegram: @Neoold                          *");
                    System.out.println("* Email: natanieljava@gmail.                 *");
                    System.out.println("* Log (Somente do modo de texto)             *");
                    System.out.println("*                                            *");
                    System.out.println("* #5.1 -> Regra Substituir implementada.     *");
                    System.out.println("*                                            *");
                    System.out.println("* #5.0 -> Regra Substituir incluido.         *");
                    System.out.println("*                                            *");
                    System.out.println("* #4.0 -> Nova organização dos comandos de   *");
                    System.out.println("* renomeação.				     *");
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
                            List<List<String>> regras = new ArrayList<>();
                            while (!regra) {
                                if (regras.size() > 0) {
                                    String[] st = new String[]{"Tipo", "Conteudo"};
                                    String[] tp = new String[]{"Numeral", "Constante", "Substituição"};
                                    StringBuilder bu = new StringBuilder();
                                    for (String s : st) {
                                        bu.append(s).append("\t\t\t");
                                    }
                                    bu.append("\n\n");
                                    regras.stream().map((s) -> {
                                        String tipo = s.get(1);
                                        String cont = s.get(2);
                                        bu.append(tipo).append("\t\t").append(cont);
                                        return s;
                                    }).forEachOrdered((_item) -> {
                                        bu.append("\n");
                                    });
                                    System.out.println(bu);
                                }
                                System.out.println("Agora informe a regra:");
                                System.out.println(
                                        "1-Numeral"
                                        + "\n2-Constante"
                                        + "\n3-Substituir"
                                        + "\n4-parar e aplicar regra"
                                        + "\n5-parar, limpar regra(s) antiga(s) e adicionar nova(s) regra(s)");
                                String nuu = ent.nextLine();
                                switch (nuu) {
                                    case "1":
                                        System.out.println("Informe o numero:");
                                        while (true) {
                                            String num = ent.nextLine();
                                            if (num.equals("esc")) {
                                                break;
                                            } else if (statics.Numeral.validar(num)) {
                                                List<String> dados = new ArrayList<>();
                                                dados.add("");
                                                dados.add("Numeral");
                                                dados.add(num);
                                                regras.add(dados);
                                                System.out.println("Adicionado o numero \"" + num + "\"");
                                                break;
                                            } else {
                                                System.out
                                                        .println("Informe somente numeros, use 'constante' para por espacos ");
                                                System.out.println("ou use 'esc' para voltar.");
                                            }
                                        }
                                        break;
                                    case "2":
                                        System.out.println("Informe a constante,");
                                        System.out.println("Pode usar 'esc' para sair ou '*esc' para escrever o mesmo:");
                                        String num = ent.nextLine();
                                        if (num.equals("esc")) {
                                            break;
                                        } else if (num.equals("*esc")) {
                                            num = num.replaceFirst("*", "");
                                            List<String> dados = new ArrayList<>();
                                            dados.add("");
                                            dados.add("Constante");
                                            dados.add(num);
                                            regras.add(dados);
                                        } else {
                                            List<String> dados = new ArrayList<>();
                                            dados.add("");
                                            dados.add("Constante");
                                            dados.add(num);
                                            regras.add(dados);
                                        }
                                        break;
                                    case "3":
                                        System.out.println("Informe a regra pra substituir");
                                        while (true) {
                                            String con = ent.nextLine();
                                            if (con.equals("esc")) {
                                                break;
                                            } else if (statics.Substituir.validar(con)) {
                                                List<String> dados = new ArrayList<>();
                                                dados.add("");
                                                dados.add("Substituir");
                                                dados.add(con);
                                                regras.add(dados);
                                                System.out.println("Adicionado a regra \"" + con + "\"");
                                                break;
                                            } else {
                                                System.out
                                                        .println("A regra que voce escreveu é invalida.\n Veja o guia para aprender a escrever uma regra de Substituição");
                                                System.out.println("ou use 'esc' para voltar.");
                                            }
                                        }
                                        break;
                                    case "4":
                                        if (!regras.isEmpty()) {
                                            bo = true;
                                        } else {
                                            System.out.println("Ops, voce não informou regra alguma.");
                                        }
                                        break;
                                    case "5":
                                        regras.clear();
                                        System.out.println("Regras limpa.");
                                        break;
                                    default:
                                        System.out.println("Opa não entendi, escreva somente oque é pedido.");
                                        break;
                                }

                                while (bo) {
                                    System.out.println("Deseja ver uma previa?" + "\n1->Sim, com tres (3) arquivos"
                                            + "\n2->Sim, com todos os arquivos" + "\n3->Não, voltar pra regra"
                                            + "\n4->Não, executar regra" + "\n5->Não, executar regra com log");
                                    String ee = ent.nextLine();
                                    Object[] a = null;
                                    List<Path> e = null;
                                    switch (ee) {
                                        case "1":
                                            e = lista.get().collect(Collectors.toList());
                                            a = statics.Renomear.criarRenomeacaoPath(regras, e);
                                            arquivoA = (List<String>) a[0];
                                            arquivoN = (List<String>) a[1];
                                            renomear(e, 1);
                                            break;
                                        case "2":
                                            e = lista.get().collect(Collectors.toList());
                                            a = statics.Renomear.criarRenomeacaoPath(regras, e);
                                            arquivoA = (List<String>) a[0];
                                            arquivoN = (List<String>) a[1];
                                            renomear(e, 3);
                                            break;
                                        case "3":
                                            bo = false;
                                            regras.clear();
                                            break;
                                        case "4":
                                            if (arquivoN == null) {
                                                e = lista.get().collect(Collectors.toList());
                                                a = statics.Renomear.criarRenomeacaoPath(regras, e);
                                                arquivoA = (List<String>) a[0];
                                                arquivoN = (List<String>) a[1];
                                            }
                                            long tm = System.currentTimeMillis();
                                            renomear(e, 0);
                                            System.out.println("Demorou " + Long.toString(System.currentTimeMillis() - tm)
                                                    + " milissegundos");
                                            bo = false;
                                            regra = true;
                                            fim = true;
                                            break;
                                        case "5":
                                            e = lista.get().collect(Collectors.toList());
                                            if (arquivoN == null) {
                                                a = statics.Renomear.criarRenomeacaoPath(regras, e);
                                                arquivoA = (List<String>) a[0];
                                                arquivoN = (List<String>) a[1];
                                            }
                                            tm = System.currentTimeMillis();
                                            renomear(e, 2);
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
                    e.printStackTrace();

                }
            }
            ent.close();
            System.exit(0);

        } else {
            launch(args);
        }
    }
    private static void renomear(List<Path> file, int mod) {
        arquivoN.sort(Comparator.comparing(String::toString));
        arquivoA.sort(Comparator.comparing(String::toString));
        file.sort(Comparator.comparing(Path::getFileName));
        
        if (mod==1){
            for (int i = 0; i < 3; i++) {
                System.out.println("Previa nº "+(i+1)+" -> "+arquivoN.get(i));
            }
            return;
        }
        
        for (int i = 0; i != file.size(); i++) {
            File e = file.get(i).toFile();
            switch (mod) {
                case 0:
                    e.renameTo(renomear(arquivoN.get(i), e));
                    break;
                case 2:
                    String aNome = e.toString();
                    if (aNome.contains(".")) {
                        System.out.println("Renomeando arquivo \"" + arquivoA.get(i) + "\" para \""
                                + arquivoN.get(i).concat(
                                        arquivoN.get(i).substring(aNome.lastIndexOf(".")))
                                + "\"");
                    } else {
                        System.out.println("Renomeando arquivo \"" + arquivoA.get(i) + "\" para \""
                                + arquivoN.get(i)
                                + "\"");
                    }

                    e.renameTo(renomear(arquivoN.get(i), e));
                    break;
                case 3:
                    System.out.println("Previa " + (i+1) + "º \"" + arquivoN.get(i) + "\"");
                    break;
            }

        }
    }

    private static File renomear(String nome, File antigo) {
        String path = antigo.getPath();
        String no = antigo.getName().replace(path, "");
        path = path.replace(no, "");
        String exten = no.contains(".") ? no.substring(no.lastIndexOf(".")) : "";
        nome = nome.replace(exten, "");
        return new File(path.concat(nome.concat(exten)));
    }

    private static String renomearString(File antigo) {
        String path = antigo.getPath();
        String no = antigo.getName().replace(path, "");
        String exten = no.contains(".") ? no.substring(no.lastIndexOf(".")) : "";

        return no.replace(exten, "");
    }
}
