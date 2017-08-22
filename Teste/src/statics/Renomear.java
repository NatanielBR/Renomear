/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statics;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nataniel
 */
public class Renomear {

    public static Object[] criarRenomeacao(List<List<String>> lista, List<File> fil) {
        List<String> arquivoA = new ArrayList<>();
        List<String> arquivoN = new ArrayList<>();
        int ii=0;
        for (int i = 0; i != fil.size(); i++,ii++) {
            File f = fil.get(i);
            StringBuilder bu = new StringBuilder();
            for (List<String> list : lista) {
                String cont = list.get(2);
                String tipo = list.get(1);
                switch (tipo) {
                    case ("Numeral"):
                        cont = statics.Numeral.processarN(cont, ii);
                        bu.append(cont);
                        break;
                    case "Constante":
                        bu.append(cont);
                        break;
                    case "Substituir":
                        //cont é a entrada
                        String antt = renomearString(f);
                        String[] dados = statics.Substituir.processarN(cont,
                                new String[]{Integer.toString(ii),antt});
                        if (dados[0].contains("$true")){
                            bu.append(dados[1]);
                        }else{
                            bu.append(antt.contains(dados[0]) ? antt.replace(dados[0], dados[1]) : antt);
                        }
                        ii=Integer.valueOf(dados[2]);
                        
                        
                        break;
                }
            }
            arquivoA.add(renomearString(f));
            arquivoN.add(bu.toString());
        }
        verificarDuplo(arquivoN);
        return new Object[]{arquivoA, arquivoN};
    }
    private static void criarNumero(List<List<String>> lista){
        List<String> numeral = new ArrayList<>();
            numeral.add("");
            numeral.add("Constante");
            numeral.add(" ");
            lista.add(numeral);
            numeral = new ArrayList<>();
            numeral.add("");
            numeral.add("Numeral");
            numeral.add("01");
            lista.add(numeral);
    }
    private static int verificarDuplo(List<String> ob){
        int a=1;
        for (int i = 0; i < ob.size(); i++) {
            String j = ob.get(i);
            if ((i + 1) < ob.size()) {
                for (int k = i + 1; k < ob.size(); k++) {
                    if (j.equals(ob.get(k))) {
                        ob.set(k, ob.get(k).concat(" "+a));
                        a++;
                    }
                }
            }

        }
        return -1;
    }
    public static Object[] criarRenomeacaoPath(List<List<String>> lista, List<Path> fil) {
        List<String> arquivoA = new ArrayList<>();
        List<String> arquivoN = new ArrayList<>();
        
        
        for (int i = 0; i != fil.size(); i++) {
            File f = fil.get(i).toFile();
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
                        String antt = renomearString(f);
                        String[] dados = statics.Substituir.processarN(cont,
                                new String[]{Integer.toString(i),antt});
                        if (dados[0].contains("$true")){
                            bu.append(dados[1]);
                        }else{
                            bu.append(antt.contains(dados[0]) ? antt.replace(dados[0], dados[1]) : antt);
                        }
                        break;
                }
            }
            arquivoA.add(renomearString(f));
            arquivoN.add(bu.toString());
        }
        verificarDuplo(arquivoN);
        return new Object[]{arquivoA, arquivoN};
    }

    public static void Renomear(List<String> arquivoN, List<File> fil) {
        for (int i = 0; i != fil.size(); i++) {
            File f = fil.get(i);
            f.renameTo(renomearArquivo(arquivoN.get(i), f));
        }
    }

    private static File renomearArquivo(String nome, File antigo) {
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

    private static String renomearString(File antigo) {
        String path = antigo.getPath();
        String no = antigo.getName().replace(path, "");
        String exten = no.contains(".") ? no.substring(no.lastIndexOf(".")) : "";
        return no.replace(exten, "");
    }
}
