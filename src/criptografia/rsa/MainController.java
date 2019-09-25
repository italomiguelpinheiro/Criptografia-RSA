/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package criptografia.rsa;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author italomcdp
 */
public class MainController implements Initializable{
    
    private int q;
    private int p;
    private static int z;
    private static int n;
    private int d;
    private int e;
    private int valE;
    
    @FXML
    private TextField textFieldNum1;
    
    @FXML
    private TextField textFieldNum2;
    
    @FXML
    private Button butonGerarCoprimos;
    
    @FXML
    private ComboBox<Integer> listaCoPrimos;
    
    @FXML
    private Label labelInversoModular;
    
    @FXML
    private TextField textFielValorK;
    
    @FXML
    private Button buttonOkValorK;
    
    @FXML
    private Label labelChavePublica;
    
    @FXML
    private Label labelChavePrivada;
    
    @FXML
    private TextField TextFieldPalavra;
    
    @FXML
    private Button buttonSelecionarPalavra;
    
    @FXML
    private Label labelRecebido;
    
    @FXML
    private Label labelDescriptografado;
    
    
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
    
    private int inversoModular(int valor, int modulo) {
    	int contador = 1;
    	while (true) {
    		if ((valor * contador) % modulo == 1) {
    			return contador;
    		} else {
    			contador += 1;
    		}
    	}
    }
    
    private static int mdc(int a, int b) {
        while (b != 0) {
            int resto = a % b;
            a = b;
            b = resto;
        }
        
        return a;
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listaCoPrimos.valueProperty().addListener((ov, oldValue, newValue) -> {
			if (!newValue.equals("")) {
				d = newValue;
				labelChavePrivada.setText("(" + d + ", " + z + ")");
				e = inversoModular(newValue, n);
				labelInversoModular.setText("Chave Privada = " + e + " + k." + n + "\ncom k"
						+ " pertencendo aos inteiros");
			} else {
				System.out.println(newValue);
			}
		});
	}
	
	@FXML
	public void onBtValorKAction(ActionEvent event) {
		int valorK = Integer.parseInt(textFielValorK.getText());
		int valorE = e + valorK*n;
		valE = valorE;
		System.out.println(d);
		System.out.println(valE);
		labelChavePublica.setText("(" + valE + ", " + z + ")");
		System.out.println(criptografando("teste"));
	}
    
	private ArrayList<Integer> codigoPalavra(String palavra) {
		ArrayList<Integer> lista = new ArrayList<Integer>();
		for (int i = 0; i < palavra.length(); i++) {
			lista.add((int) palavra.charAt(i) - 96);
		}
		
		return lista;
	}
	
	private ArrayList<Integer> criptografando(String palavra) {
		ArrayList<Integer> valores = codigoPalavra(palavra);
		ArrayList<Integer> lista = new ArrayList<Integer>();
		for (int num : valores) {
			double valor = Math.pow(num, d);
			int codigo =  (int) (valor % z);
			lista.add(codigo);
		}
		System.out.println(lista);
		return lista;
	}
	
	private String descriptografando(ArrayList<Integer> valores) {
		String palavra = "";
		for (int num : valores) {
			Long valor = (long) valE;
			System.out.println(valE);
			for (int i = 1; i <= z; i++) {
				valor = valor*valE;
			}
			System.out.println(valor);
			int codigo =  (int) (valor % z);
			System.out.println(codigo);
			palavra += (char) (codigo + 96);
		}
		return palavra;
	}
	
	@FXML
	public void onBtSelecionarPalavra(ActionEvent event) {
		String palavraString = TextFieldPalavra.getText();
		ArrayList<Integer> valoresCrptografados = criptografando(palavraString);
		String palavraDescriptografa = descriptografando(valoresCrptografados);
		
		String valoresCripString = "";
		for (int num : valoresCrptografados) {
			valoresCripString += num + " ";
		}
		
		labelRecebido.setText(valoresCripString);
		labelDescriptografado.setText(palavraDescriptografa);
	}
    
    
}


