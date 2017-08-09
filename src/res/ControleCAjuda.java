/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 *
 * @author nataniel
 */
public class ControleCAjuda implements Initializable{
    @FXML
    private TextArea texto;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Scanner ent=new Scanner(getClass().getResourceAsStream("/res/txt/ajuda.txt"));
        while (ent.hasNextLine()){
            texto.setText(texto.getText()+ent.nextLine()+"\n");
        }
    }
    
}
