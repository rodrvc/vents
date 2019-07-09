    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.StageStyle;
import model.TipoUsuario;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class PrincipalController implements Initializable {

    @FXML
    private JFXButton salir;
    @FXML
    private JFXButton btnVender;
    @FXML
    private JFXButton btnIngresarProducto;
    @FXML
    private Label nombreUsuario;
    @FXML
    private Label nivelUsuario;
    @FXML
    private JFXButton btnIngresarProducto1;
    @FXML
    private JFXButton btnIngresarProducto11;
    @FXML
    private JFXButton btnIngresarProducto1111;
    @FXML
    private Pane Pan;
    @FXML
    private JFXButton btnProducto;
    

    /**
     * Initializes the controller class.
     */
    
    
    
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        
    }    
    
     
    
     
    
    

    @FXML
    private void salir(ActionEvent event) {
        System.exit(0);
    }
    
    ///METODOS
    
     public void obtenerNivel(String nom, String ape , String nivel ){
        nivelUsuario.setText(String.valueOf(nivel).toUpperCase());
        nombreUsuario.setText(nom.toUpperCase()+" " + ape.toUpperCase() );
    }
     
     public void borrarBtn(int bo){
         switch (bo) {
             case 1:
                 btnIngresarProducto.setVisible(false);
                 break;
             case 2:
                 
             default:
                 System.out.println("Acceso");
         }
     }

    @FXML
    private void abrirVentas(ActionEvent event) throws IOException {
          Pane pane =  FXMLLoader.load(getClass().getResource("/view/Venta.fxml"));
         Pan.getChildren().setAll(pane);
       
    }

    @FXML
    private void abrirProducto(ActionEvent event) throws IOException {
            Pane pane =  FXMLLoader.load(getClass().getResource("/view/Producto.fxml"));
         Pan.getChildren().setAll(pane);
        
    }

    @FXML
    private void abrirUsuarios(ActionEvent event) throws IOException {
         Pane pane =  FXMLLoader.load(getClass().getResource("/view/Usuarios.fxml"));
         Pan.getChildren().setAll(pane);
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
