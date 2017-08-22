
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author nataniel
 */
public class main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        String ent = "$true**$Pnum";;
        List<List<String>> aa = new ArrayList<>();
        List<String> a = new ArrayList<>();
        a.add("0");
        a.add("Substituir");
        a.add(ent);
        aa.add(a);
        List<Path> pp = Files.list(Paths.get(new File("/home/nataniel/a/").toURI())).collect(Collectors.toList());
        if (statics.Substituir.validar(ent)) {
            Object[] b = statics.Renomear.criarRenomeacaoPath(aa, pp);
            a = (List<String>) b[1];
            a.sort(Comparator.comparing(String::toString));
            for (String s : a) {
                System.out.println(s);
            }
        }
    }

}
