/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.sun.javaws.Main;
import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Conexion;
import model.Proveedor;
import model.TipoUsuario;
import model.Usuario;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */


public class LogginController implements Initializable {
    
     private ObservableList <TipoUsuario>listaTipo;
    public static Usuario usuarioEnSession = null;
     
     
    
    //CONEXION
    private Conexion con;
   
    //COMPONENTES
    @FXML
    private TextField nombreUsuario;
    private PasswordField ContraseniaUsuario;
    @FXML
    private Label Aviso;
    @FXML
    private JFXButton button;
    @FXML
    private JFXPasswordField contraseniaUsuario;
    @FXML
    private AnchorPane login;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        


    }   
    
   

    @FXML
      private void handleButtonAction1(ActionEvent event) throws IOException {
  con = new Conexion();
        Usuario user = null;
        int nivelDeIngreso = 0;

        String nom = nombreUsuario.getText();
        String contra = contraseniaUsuario.getText();
        if (nom.equals("") || contra.equals("")) {
            //Se maneja el mensaje que entrega el label Aviso
            Aviso.setText("");
            Aviso.setText("Ingrese los campos solicitados");
            
        }else   {
                   //Se comprueba la existencia del usuario  
                  user =  (Usuario)Usuario.comprobarUsuario(con.getConexion(), nom, contra);
                  Usuario.setUsuarioEnSession(user);
                  if (user == null ) {
                          nivelDeIngreso = 0;
                   }else {
                      //SE OBTIONE NIVEL DE ACCESO O USUARIO TIPO
                            nivelDeIngreso = (user.getTipoUsuario().IdUsuarioProperty().get());
                            
                  }
                  ///
                 if (nivelDeIngreso > 0  ) {                      
                   FXMLLoader loader = new FXMLLoader();
                  Pane home_page_parent = loader.load(getClass().getResource("/view/Principal.fxml").openStream());
                  PrincipalController control = (PrincipalController)loader.getController();
                  //Se mandan los datos a la ventana principal... 
                   control.obtenerNivel(user.PrimerNomProperty().get(),  user.PrimerApeProperty().get() , user.getTipoUsuario().getNombreTipo() );
                   control.borrarBtn(user.getTipoUsuario().getIdUsuario()); // se refiere al id de tipo
                  Scene home_page_scene = new Scene(home_page_parent);
                  
                  Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                  
                  
                  
                  app_stage.hide();
                  app_stage.setScene(home_page_scene);
                
                  app_stage.show();
                    
                      }else{
                                Aviso.setText("Contraseña o Usuario no han sido ingresados correctamente");
                     }
        }          
}

    private void borrar(MouseEvent event) {
        Aviso.setText("");
    }

}
