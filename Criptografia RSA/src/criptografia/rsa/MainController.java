/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package criptografia.rsa;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 *
 * @author italomcdp
 */
public class MainController {
    
    private int q;
    private int p;
    private static int z;
    private static int n;
    
    @FXML
    private TextField textFieldNum1;
    
    @FXML
    private TextField textFieldNum2;
    
    @FXML
    private Button butonGerarCoprimos;
    
    @FXML
    private ComboBox<Integer> listaCoPrimos;
    
    @FXML
    public void GerarCoPrimosAction(ActionEvent event) {
        q = Integer.parseInt(textFieldNum1.getText());
        p = Integer.parseInt(textFieldNum2.getText());
        z = p * q;
        n = (p-1) * (q-1);
        
        ArrayList<Integer> lista = primosEntreSi(n);
        ObservableList<Integer> valores =  FXCollections.observableArrayList(lista);
        listaCoPrimos.getItems().clear();
        listaCoPrimos.getItems().addAll(valores);
    }
    
    private static ArrayList<Integer> primosEntreSi(int valor) {
        ArrayList<Integer> lista = new ArrayList<>();
        for (int i = 1; i < valor; i++) {
            if (mdc(i, n) == 1) {
                lista.add(i);
            }
        }
        
        return lista;
    }
    
    private static int mdc(int a, int b) {
        while (b != 0) {
            int resto = a % b;
            a = b;
            b = resto;
        }
        
        return a;
    }
    
    
    
}


