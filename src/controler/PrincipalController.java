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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
    private JFXButton btnIngresarProducto;
    @FXML
    private Label nombreUsuario;
    @FXML
    private Label nivelUsuario;
    @FXML
    private Pane Pan;
    @FXML
    private JFXButton btnProducto;
    @FXML
    private JFXButton btnIngresarProveedor;
    @FXML
    private JFXButton btnIngresarCotizacion;
    @FXML
    private JFXButton btnUsuario;
    @FXML
    private JFXButton btnRevisarBoletas;
    @FXML
    private JFXButton volverLogin;
    @FXML
    private JFXButton btnCliente;
    

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
                 
                 btnIngresarProveedor.setVisible(false);
                 btnUsuario.setVisible(false);
                
                 break;
             case 2:
                    btnIngresarCotizacion.setVisible(false);
                    btnIngresarProveedor.setVisible(false);
                 break;
             case 3: 
                 break;
           
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

    @FXML
    private void actionProveedor(ActionEvent event) throws IOException {
         Pane pane =  FXMLLoader.load(getClass().getResource("/view/ProveedorDeVenta.fxml"));
         Pan.getChildren().setAll(pane);
    }

    @FXML
    private void abrirCotizacion(ActionEvent event) throws IOException {
        Pane pane =  FXMLLoader.load(getClass().getResource("/view/Cotizacion.fxml"));
         Pan.getChildren().setAll(pane);
    }

    @FXML
    private void abrirReporte(ActionEvent event) throws IOException {
        Pane pane =  FXMLLoader.load(getClass().getResource("/view/Repostes.fxml"));
         Pan.getChildren().setAll(pane);
    }
    
    
    @FXML
    private void abrirCliente(ActionEvent event) throws IOException {
         Pane pane =  FXMLLoader.load(getClass().getResource("/view/cliente.fxml"));
         Pan.getChildren().setAll(pane);
    }

    @FXML
    private void accionVolverLoggin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
                  
                  PrincipalController control = (PrincipalController)loader.getController();
                  //Se mandan los datos a la ventana principal... 
                 Pane home_page_parent = loader.load(getClass().getResource("/view/loggin.fxml").openStream());
                  Scene home_page_scene = new Scene(home_page_parent);
                  
                  
                  

                  Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                  
                  
                  
                  app_stage.hide();
                  app_stage.setScene(home_page_scene);
                
                  app_stage.show();
        
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
