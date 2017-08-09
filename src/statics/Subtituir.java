package statics;

import expr.Parser;
import expr.SyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Subtituir {

    public static String[] processarN(String a) {
        Map<String, String> constantes = new HashMap<>();
        int ccond = a.indexOf("**");
        String cond = a.substring(0, ccond);
        constantes.put("$num", "0");
        constantes.put("$cond", cond);

        String caso = a.substring(ccond + 2);
        String[] casos = caso.split(" ");
        Object[] sett = constantes.keySet().toArray();
        int ii = 0;
        String ant = "";
        for (Object o : sett) {
            String st = (String) o;
            for (int i = ii; i != casos.length; i++) {
                String s = casos[i];
                if (s.contains(st)) {
                    ii = i + 1;
                    String ss = (s.replace(st, constantes.get(st)));
                    caso = caso.replace(s, ss);
                }

            }
        }
        casos = caso.split(" ");
        for (String s : casos) {
            if (s.matches("[0-9/s+*/]*")) {
                ant = s;
                String ssres = "";
                try {
                    ssres = Double.toString(Parser.parse(s).value());
                    ssres = ssres.substring(0, ssres.indexOf("."));
                } catch (SyntaxException ex) {
                    Logger.getLogger(Subtituir.class.getName()).log(Level.SEVERE, null, ex);
                }
                caso = caso.replace(ant, ssres);
            }
            if (s.contains("$") && !s.equals(ant)) {
                ant = s;
                s = s.replace("$", "");
                String ssres = "";
                try {
                    ssres = Double.toString(Parser.parse(s).value());
                    ssres = ssres.substring(0, ssres.indexOf("."));
                } catch (SyntaxException ex) {
                    Logger.getLogger(Subtituir.class.getName()).log(Level.SEVERE, null, ex);
                }
                constantes.put("$num", ssres);
                caso = caso.replace(ant, ssres);
            }
        }
        //System.out.println("num "+constantes.get("$num"));
        return new String[]{cond, caso};
    }

    public static boolean validar(String a) {
        try {
            String cont = a.substring(0,a.indexOf("**"));
            return !cont.contains("$");
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * Objetivo de validar a string
     *
     * @param a
     * @return
     * @throws Exception
     * @deprecated
     */
    @Deprecated
    private static String processarT(String a) throws Exception {
        Map<String, String> constantes = new HashMap<>();
        int ccond = a.indexOf("**");
        String cond = a.substring(0, ccond);
        constantes.put("$num", "0");
        constantes.put("$cond", cond);

        String caso = a.substring(ccond + 2);
        String[] casos = caso.split(" ");
        Object[] sett = constantes.keySet().toArray();
        int ii = 0;
        String ant = "";
        for (Object o : sett) {
            String st = (String) o;
            for (int i = ii; i != casos.length; i++) {
                String s = casos[i];
                if (s.equals(st)) {
                    ii = i + 1;
                    String ss = (s.replace(st, constantes.get(st)));
                    caso = caso.replace(s, ss);
                }

            }
        }
        casos = caso.split(" ");
        for (String s : casos) {
            if (s.contains("$") && !s.equals(ant)) {
                ant = s;
                s = s.replace("$", "");
                String[] som = s.split("\\+");
                int sres = 0;
                for (String ss : som) {
                    sres = Integer.parseInt(ss);

                }
                String ssres = Integer.toString(sres);
                constantes.put("$num", ssres);
                caso = caso.replace(ant, ssres);
            }
        }
        return caso;
    }
}
