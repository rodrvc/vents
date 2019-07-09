/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.ChoiceBoxTreeCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.input.TouchEvent;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.rgb;
import javafx.scene.paint.Paint;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import jdk.nashorn.internal.runtime.UserAccessorProperty;
import model.Conexion;
import model.TipoUsuario;
import model.Usuario;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class UsuariosController implements Initializable {
    
    //conexion
    private Conexion con;
    
    
    private ObservableList<Usuario>listaUsuario;
    private ObservableList<TipoUsuario> listaTipo;

    @FXML
    private TableView<Usuario> tablaUsuarios;
    @FXML
    private TableColumn<Usuario, String> clmRut;
    @FXML
    private TableColumn<Usuario, String> clmNombre;
    @FXML
    private TableColumn<Usuario, String> clmApellido;
    @FXML
    private TableColumn<Usuario, String> clmMApellido;
    @FXML
    private TableColumn<Usuario, String> clmTipo;
    @FXML
    private TableColumn<Usuario, String> clmUsuario;
     @FXML
    private TableColumn<Usuario, String> clmCorreo;
     @FXML
    private TableColumn<Usuario, String> clmDireccion;
     
 
    
    @FXML
    private Button btnAgregar;
    
    

    @FXML
    private JFXTextField txtRut;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtApellido;
    @FXML
    private JFXTextField txtSegApellido;
    @FXML
    private JFXTextField txtCorreoUsuario;
    @FXML
    private JFXTextField txtDireccionUsuario;
    @FXML
    private JFXTextField txtNombreUsuario;
    @FXML
    private JFXComboBox<TipoUsuario> cbCargo;
    @FXML
    private JFXTextField txtContrasenia;
    @FXML
    private JFXTextField txtConfirContrasenia;
    @FXML
    private JFXTextField txtNombre2;
    @FXML
    private Label labelErrores;
    

    
  
    
    
    
    /**
     * Initializes the controller class.
     */
    
    
    
    
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        llenar();

    }    
    
    
    public void actualizarTabla(){
        con = new Conexion();
        listaUsuario.clear();
        Usuario.llenarListaUsuarios(con.getConexion(), listaUsuario);
    }
    
    public void llenar(){
          con = new Conexion();
        // se llenan listas 
        listaUsuario = FXCollections.observableArrayList();
        Usuario.llenarListaUsuarios(con.getConexion(), listaUsuario);
        
        listaTipo = FXCollections.observableArrayList();
        TipoUsuario.informacion(con.getConexion(), listaTipo);
        
        con.cerrarConexion();
        
        
        tablaUsuarios.setItems(listaUsuario);
        
         
          clmRut.setCellValueFactory(
                new PropertyValueFactory<>("rut")
        );
        
        clmNombre.setCellValueFactory(
                new PropertyValueFactory<>("primerNom")
        );
        
        clmApellido.setCellValueFactory(
                new PropertyValueFactory<>("primerApe")
        );
        clmMApellido.setCellValueFactory(
                new PropertyValueFactory<>("segunApe")
        );
      
        clmTipo.setCellValueFactory( cellData -> new SimpleStringProperty(cellData.getValue().getTipoUsuario().getNombreTipo())) ;
       
       onEditTipoUsuario();
        
        
      
        
     
        
                
        
        clmUsuario.setCellValueFactory(
                new PropertyValueFactory<>("nombreUsuario")
        );
        
        clmDireccion.setCellValueFactory(
                new PropertyValueFactory<>("direccion")
        );  
        
        clmCorreo.setCellValueFactory(
                new PropertyValueFactory<>("correo")
        );
        

         
        clmCorreo.setCellFactory(TextFieldTableCell.forTableColumn() );
        clmNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        clmApellido.setCellFactory(TextFieldTableCell.forTableColumn());
        clmTipo.setCellFactory(ComboBoxTableCell.forTableColumn("cajero", "vendedor","administracion"));
        
        
        

        
        //////combo box
        cbCargo.setItems(listaTipo);
        
        
        ////
          
              validarTextField();
    }

    
    @FXML
    private void agregarUsuario(ActionEvent event) {
     
       
       String rut = txtRut.getText();
       String nombre = txtNombre.getText();
   
       String ape = txtApellido.getText();
       String ape2 = txtSegApellido.getText();
       String direccion = txtDireccionUsuario.getText();
       String correo = txtCorreoUsuario.getText();
       String usuario = txtCorreoUsuario.getText(); 
       String contra = txtContrasenia.getText();
       String contra2 = txtConfirContrasenia.getText();
       TipoUsuario t = cbCargo.getValue();
       
       Boolean camposLlenos = false ; 
       
       // ///////////////////////////VALIDACIONES ///////////////////////////////////////////
       
       // Usar Alert 
       // no deben haber campos vacios
          camposLlenos = camposVacios(rut , nombre, ape, ape2 , direccion , correo , usuario , contra );
       // las contrasenias deben coincidir

       //algunos textos solo ingresan texto

       // ///////////////////////////INGRESO DE DATOS ///////////////////////////////////////////
       
        if (camposLlenos == true) {
            Usuario u = new Usuario(0, t, nombre, rut, ape, ape2, direccion, correo, usuario, contra);

            u.insertarUsuario(con.getConexion()); // se ingresa a db
            listaUsuario.add(u); // se ingresa a la observableList
            
        } 
       
    }
    
    
    
    public void nombrarlabel(String txts){
        labelErrores.setText(txts);
    }
    
   
    public  boolean camposVacios(String rut , String nombre ,  String ape, String ape2 , String dir ,  String cor, String nomu , String contra ) {
         String txts = "";
         
         if (!rut.matches("(((\\d{1})|(\\d{2}))\\.(\\d{3})(\\.)(\\d{3})(-)((k|K)|(\\d))|((\\d{1})|(\\d{2}))(\\d{3})(\\d{3})(-)((k|K)|(\\d)))")) {
            labelErrores.setText("INGRESAR RUT VALIDO");
            txtRut.setFocusColor(RED);
            txtRut.requestFocus();
            return false;
        }
        
        if (rut.isEmpty()) {
            txts += "rut";
            txtRut.requestFocus();
            nombrarlabel(txts);
            return false; 
        }
        
        if (nombre.isEmpty()) {
            txts += "Nombre se encuentra vacio\n"; 
            txtNombre.requestFocus();
            nombrarlabel(txts);
            return false; 
        }
        
        if (ape.isEmpty()) {
            txts += "apellido\n"; 
            txtApellido.requestFocus();
             nombrarlabel(txts);
            return false; 
        }
        
        if (ape2.isEmpty()) { 
            txts += "ingresa un segundo apellido\n" ; 
            txtSegApellido.requestFocus();
             nombrarlabel(txts);
            return false; 
        }
        
        if (dir.isEmpty()) {
            txts += "ingresa una direccion\n" ; 
            txtDireccionUsuario.requestFocus();
             nombrarlabel(txts);
            return false; 
        }
        
        if (cor.isEmpty()) {
            txts += " ingresa un correo \n";
            txtCorreoUsuario.requestFocus();
             nombrarlabel(txts);
            return false; 
        }
        
        if (contra.isEmpty()) { 
            txts += "debes ingresar una contraseña \n";
            txtContrasenia.requestFocus();
             nombrarlabel(txts);
            return false; 
        }
        return true;
    }
    
    //elemento para editar la tabla 
    public void tableEditable() {
        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Usuario>() {
            @Override
            public void changed(ObservableValue<? extends Usuario> observable, Usuario oldValue, Usuario newValue) {
                String valor = newValue.getNombreUsuario();
                newValue.setNombreUsuario(valor);//se obtiene elemento seleccionado
            }
        });
    }
    
    public void validarTextField() {
        txtRut.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    txtRut.setFocusColor(rgb(20, 20, 20));
                }
            }

        });

    }

    @FXML
    private void txtReleased(TouchEvent event) {

    }

    public Usuario valorUsuario(Usuario valor) {
        return valor;
    }
    
      @FXML
    private void editNombre(TableColumn.CellEditEvent<Usuario, String> event) {
        con = new Conexion();
        // se busca el usuario 
        String data = event.getNewValue();
        

        //se comprueba ingreso correcto 
        
        if (validarString(data)) {
            event.getTableView().getColumns().get(1).setVisible(false);
            event.getTableView().getColumns().get(1).setVisible(true);
            System.out.println("datos mal ingresados");
        } else {
            
            //ALERTA CONFIRMACION
           
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setContentText("Presiona aceptar para modificar, esta propiedad sera modificada");
            alert.setHeaderText("MODIFICAR ATRIBUTO");
            alert.setTitle("Propiedad Modificada");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Se modifica atributo en la base de datos
                event.getTableView().getItems().get(event.getTablePosition().getRow()).editarNombre(event.getNewValue(), con.getConexion());
                System.out.println("se modifica");  

            } else {
                // se cancela 
                event.getTableView().getColumns().get(1).setVisible(false);
                event.getTableView().getColumns().get(1).setVisible(true);
            }
        }
        actualizarTabla();
        tablaUsuarios.refresh();
    }

    
      @FXML
    private void onEditPApellido(TableColumn.CellEditEvent<Usuario, String> event) {
         con = new Conexion();
        // se busca el usuario 
        String data = event.getNewValue();
        

        //se comprueba ingreso correcto 
        
        if (validarString(data)) {
            event.getTableView().getColumns().get(1).setVisible(false);
            event.getTableView().getColumns().get(1).setVisible(true);
            System.out.println("datos mal ingresados");
        } else {
            
            //ALERTA CONFIRMACION
           
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setContentText("Presiona aceptar para modificar, esta propiedad sera modificada");
            alert.setHeaderText("MODIFICAR ATRIBUTO");
            alert.setTitle("Propiedad Modificada");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Se modifica atributo en la base de datos
                
                event.getTableView().getItems().get(event.getTablePosition().getRow()).editarApellido(event.getNewValue(), con.getConexion());
                System.out.println("se modifica");  

            } else {
                // se cancela 
                event.getTableView().getColumns().get(1).setVisible(false);
                event.getTableView().getColumns().get(1).setVisible(true);
            }
        }
        actualizarTabla();
        tablaUsuarios.refresh();

    }


    private void onEditTipoUsuario() {
        con = new Conexion();
         clmTipo.setOnEditCommit( ( TableColumn.CellEditEvent<Usuario, String> e ) ->
        {
            // new value coming from combobox
            String newValue = e.getNewValue();

            // index of editing person in the tableview
            int index = e.getTablePosition().getRow();

            // person currently being edited
            Usuario person = ( Usuario ) e.getTableView().getItems().get( index );

            // Now you have all necessary info, decide where to set new value 
            // to the person or not.
          
                Usuario.editarTipo(newValue, person.getIdUsuario(), con.getConexion());
                System.out.println(newValue);
                   
                  actualizarTabla();
                 tablaUsuarios.refresh();
                
                 e.getTableView().getColumns().get(1).setVisible(false);
                   e.getTableView().getColumns().get(1).setVisible(true);
                    } );
                // se cancela 
             
            
        
      
       
    }
    
    
    
    
    
    public void funcioneventoguardada() {
        clmNombre.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Usuario, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Usuario, String> t) {
                if (t.getNewValue().equals("") || t.getNewValue().length() < 3) {
                    System.out.println("hola");

                    // workaround for refreshing rendered values
                    t.getTableView().getColumns().get(0).setVisible(false);
                    t.getTableView().getColumns().get(0).setVisible(true);

                    // set the old value or do not update the cell.
                    return;
                }
                ((Usuario) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setNombreUsuario(t.getNewValue());
            }
        }
        );
    }
    
    public boolean validarString(String data){
       Boolean validacionDeData = false;
        if (data.equals("") || !data.matches("[A-Za-z]*")) {
            return validacionDeData = true;  
        }
        return validacionDeData;
}

 

    @FXML
    private void onEditMApellido(TableColumn.CellEditEvent<Usuario, String> event) {
    }

    @FXML
    private void onEditDireccion(TableColumn.CellEditEvent<Usuario, String> event) {
    }

    @FXML
    private void onEditCorreo(TableColumn.CellEditEvent<Usuario, String> event) {
    }

    @FXML
   private void onEditUsuario(TableColumn.CellEditEvent<Usuario, String> event) {
    }
    
    
    
    
    
}



    
    
    
    
    
    
    
    

    


//FALTA APLICAR VALIDACION AUTOMATICA EN CADA CAMPO
