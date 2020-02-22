/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Proveedor;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class ProveedorDeVentaController implements Initializable {
    
    private ObservableList<Proveedor> proveedorList; 

    @FXML
    private TextField nombreProveedor;
    @FXML
    private TextField txtRutProveedor;
    @FXML
    private TextField txtEmailProveedor;
    @FXML
    private TextField txtDireccionProveedor;
    @FXML
    private TextField CategoriaProveedor;
    @FXML
    private JFXButton btnAgregarProveedor;
    @FXML
    private JFXButton btnEliminarProveedor;
    @FXML
    private TableView<?> tablaProveedor;
    @FXML
    private TableColumn<?, ?> clmRutProveedor;
    @FXML
    private TableColumn<?, ?> clmNombreProveedor;
    @FXML
    private TableColumn<?, ?> clmEmailProveedor;
    @FXML
    private TableColumn<?, ?> clmDireccionProv;
    @FXML
    private TableColumn<?, ?> clmCategoriaProv;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        
        
    }    
    
}
