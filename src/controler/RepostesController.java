/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Cliente;
import model.Venta;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class RepostesController implements Initializable {

    @FXML
    private TableView<Venta> tablaVentas;
    @FXML
    private JFXTimePicker fecha;
    @FXML
    private Button btnBuscarUsuario;
    @FXML
    private Spinner<Integer> spinMin;
    @FXML
    private Spinner<Integer> spinMax;
    @FXML
    private Button btnBuscarCliente;
    @FXML
    private TextField txtVendedor;
    @FXML
    private TextField txtCliente;
    @FXML
    private TableView<Cliente> tablaBuscarCliente;
    @FXML
    private JFXButton btnGeneralReporte;
    
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnActionBuscarVendedor(ActionEvent event) {
        
    }

    @FXML
    private void btnActionBuscarCliente(ActionEvent event) {
    }

    @FXML
    private void btnActionGenerarReporte(ActionEvent event) {
    }
    
    public void llenar(){
    
    
    }
    
    
    
    
    
}
