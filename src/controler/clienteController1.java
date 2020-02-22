/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;


import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
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
import model.Cliente;
import model.Conexion;
import model.TipoUsuario;
import model.Usuario;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class clienteController1 implements Initializable {
    
    //conexion
    private Conexion con;

    private ObservableList<Cliente>listaCliente;
    private ObservableList<TipoUsuario> listaTipo;

    @FXML
    private TableView<Cliente> tablaUsuarios;
    @FXML
    private TableColumn<Cliente, String> clmRut;
    @FXML
    private TableColumn<Cliente, String> clmNombre;
    @FXML
    private TableColumn<Cliente, String> clmApellido;
    @FXML
    private TableColumn<Cliente, String> clmMApellido;
    private TableColumn<Cliente, String> clmTipo;
    private TableColumn<Cliente, String> clmUsuario;
     @FXML
        private TableColumn<Cliente, String> clmCorreo;
     @FXML
    private TableColumn<Cliente, String> clmDireccion;
    
     @FXML
     private TableColumn<Cliente, String> clmFechaNac;
     
 
    
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

    private JFXTextField txtContrasenia;
    private JFXTextField txtConfirContrasenia;
    @FXML
    private JFXTextField txtNombre2;
    @FXML
    private Label labelErrores;
    @FXML
    private DatePicker UidataPicker;
    @FXML
    private TableColumn<Cliente, String> clmTelefono;
    @FXML
    private TableColumn<Cliente, String> clmSegundoNombre;
    @FXML
    private JFXTextField txtTelefono;
    

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
        listaCliente.clear();
        Cliente.llenarListaClientes(con.getConexion(), listaCliente);
    }
    
    public void llenar(){
          con = new Conexion();
        // se llenan listas 
        listaCliente = FXCollections.observableArrayList();
        Cliente.llenarListaClientes(con.getConexion(), listaCliente);
        
        listaTipo = FXCollections.observableArrayList();
        TipoUsuario.informacion(con.getConexion(), listaTipo);
        
        con.cerrarConexion();
        
        
        tablaUsuarios.setItems(listaCliente);
        
         
          clmRut.setCellValueFactory(
                new PropertyValueFactory<>("rut_cliente")
        );
        
        clmNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre")
        );
        
        clmApellido.setCellValueFactory(
                new PropertyValueFactory<>("apellidoP")
        );
        clmMApellido.setCellValueFactory(
                new PropertyValueFactory<>("apellidoM")
        );
        
        clmSegundoNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombreSegundo")
        );
        
        clmFechaNac.setCellValueFactory(
                new PropertyValueFactory<>("fecha_nac")
        );
        

        clmTelefono.setCellValueFactory(
                new PropertyValueFactory<>("telefono")
        );
        
        clmDireccion.setCellValueFactory(
                new PropertyValueFactory<>("direccion")
        );  
        
        clmCorreo.setCellValueFactory(
                new PropertyValueFactory<>("correo")
        );
        

         //
        
        clmNombre.setCellFactory(TextFieldTableCell.forTableColumn());
         clmSegundoNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        clmApellido.setCellFactory(TextFieldTableCell.forTableColumn());
        clmMApellido.setCellFactory(TextFieldTableCell.forTableColumn());
        clmCorreo.setCellFactory(TextFieldTableCell.forTableColumn() );
        clmDireccion.setCellFactory(TextFieldTableCell.forTableColumn() );
        clmTelefono.setCellFactory(TextFieldTableCell.forTableColumn() );
     
        

        

          
              validarTextField();
    }

    
    @FXML
    private void agregarUsuario(ActionEvent event) {
       String rut = txtRut.getText();
       String nombre = txtNombre.getText();
       String segundoNombre = txtNombre2.getText();
       String ape = txtApellido.getText();
       String ape2 = txtSegApellido.getText();
       String direccion = txtDireccionUsuario.getText();
       String correo = txtCorreoUsuario.getText();
       LocalDate fechaSinConvertir = UidataPicker.getValue(); // convertir
      String fecha = String.valueOf(fechaSinConvertir); // se obtiene fecha
       String telefono = txtTelefono.getText();
      
       
       Boolean camposLlenos = false ; 
       
       // ///////////////////////////VALIDACIONES ///////////////////////////////////////////
       
       // Usar Alert 
       // no deben haber campos vacios
          camposLlenos = camposVacios(rut , nombre, ape, ape2 , direccion , correo , fecha , telefono );
       // las contrasenias deben coincidir

       //algunos textos solo ingresan texto

       // ///////////////////////////INGRESO DE DATOS ///////////////////////////////////////////
        if (camposLlenos == true) {
            Cliente u = new Cliente(0, nombre, segundoNombre , ape, ape2, direccion, correo, fecha, rut, telefono);
            
            AlertasDelSoftware.avisoDeIIngresoCliente();
            
             u.agregarCliente(con.getConexion()); // se ingresa a db
            listaCliente.add(u); // se ingresa a la observableList
            resetForm();
        }   
    }
    
    public void resetForm(){
        txtRut.setText("");
       txtNombre.setText("");
       txtNombre2.setText("");
       txtApellido.setText("");
      txtSegApellido.setText("");
      txtDireccionUsuario.setText("");
        txtCorreoUsuario.setText("");
        txtTelefono.setText("");
        UidataPicker.setValue(null); // convertir
    }
    
    public void nombrarlabel(String txts){
        labelErrores.setText(txts);
    }
    
   
    public  boolean camposVacios(String rut , String nombre ,  String ape, String ape2 , String dir ,  String cor, String fecha , String contra ) {
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
        
            
        if (fecha.equals("") || fecha.equals("null") || fecha == null ) { 
            txts += "debes ingresar una fecha de nacimiento \n";
            UidataPicker.requestFocus();
             nombrarlabel(txts);
             System.out.println(fecha);
            return false; 
        }
        
        if (contra.isEmpty() ) { 
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
                new ChangeListener<Cliente>() {
            @Override
            public void changed(ObservableValue<? extends Cliente> observable, Cliente oldValue, Cliente newValue) {
                String valor = newValue.getNombre();
                newValue.setNombre(valor);//se obtiene elemento seleccionado
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
    private void editNombre(TableColumn.CellEditEvent<Cliente, String> event) {
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
    private void onEditSegundoNombre(TableColumn.CellEditEvent<Cliente, String> event) {
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
                event.getTableView().getItems().get(event.getTablePosition().getRow()).editarSegundoNombre(event.getNewValue(), con.getConexion());
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
    private void onEditPApellido(TableColumn.CellEditEvent<Cliente, String> event) {
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
    
        @FXML
    private void onEditMApellido(TableColumn.CellEditEvent<Cliente, String> event) {
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
                
                event.getTableView().getItems().get(event.getTablePosition().getRow()).editarSegundoApellido(event.getNewValue(), con.getConexion());
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
    private void onEditDireccion(TableColumn.CellEditEvent<Cliente, String> event) {
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
                
                event.getTableView().getItems().get(event.getTablePosition().getRow()).editarDireccion(event.getNewValue(), con.getConexion());
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
    private void onEditCorreo(TableColumn.CellEditEvent<Cliente, String> event) {
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
                
                event.getTableView().getItems().get(event.getTablePosition().getRow()).editarDireccion(event.getNewValue(), con.getConexion());
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
    private void onEditTelefono(TableColumn.CellEditEvent<Cliente, String> event) {
         con = new Conexion();
        // se busca el usuario 
        String data = event.getNewValue();
        

        //se comprueba ingreso correcto 
        
        if(!data.matches("^\\s*(?:\\+?(\\d{1,3}))?([-. (]*(\\d{3})[-. )]*)?((\\d{3})[-. ]*(\\d{2,4})(?:[-.x ]*(\\d+))?)\\s*$")){
            event.getTableView().getColumns().get(1).setVisible(false);
            event.getTableView().getColumns().get(1).setVisible(true);
            AlertasDelSoftware.avisoEditarTelefono();
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
                
                event.getTableView().getItems().get(event.getTablePosition().getRow()).editarTelefono(event.getNewValue(), con.getConexion());
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

    /*
    @FXML
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
    
    */
    
    
    //Para conservar cambios despues del commit, sin tener que recargar ventana
    public void funcioneventoguardada() {
        clmNombre.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Cliente, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Cliente, String> t) {
                if (t.getNewValue().equals("") || t.getNewValue().length() < 3) {
                    System.out.println("hola");

                    // workaround for refreshing rendered values
                    t.getTableView().getColumns().get(0).setVisible(false);
                    t.getTableView().getColumns().get(0).setVisible(true);

                    // set the old value or do not update the cell.
                    return;
                }
                ((Cliente) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setNombre(t.getNewValue());
            }
        }
        );
        
        clmSegundoNombre.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Cliente, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Cliente, String> t) {
                if (t.getNewValue().equals("") || t.getNewValue().length() < 3) {
                    System.out.println("hola");

                    // workaround for refreshing rendered values
                    t.getTableView().getColumns().get(0).setVisible(false);
                    t.getTableView().getColumns().get(0).setVisible(true);

                    // set the old value or do not update the cell.
                    return;
                }
                ((Cliente) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setNombreSegundo(t.getNewValue());
            }
        }
        );
        
        clmApellido.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Cliente, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Cliente, String> t) {
                if (t.getNewValue().equals("") || t.getNewValue().length() < 3) {
                    System.out.println("hola");

                    // workaround for refreshing rendered values
                    t.getTableView().getColumns().get(0).setVisible(false);
                    t.getTableView().getColumns().get(0).setVisible(true);

                    // set the old value or do not update the cell.
                    return;
                }
                ((Cliente) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setApellidoP(t.getNewValue());
            }
        }
        );
        
        clmMApellido.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Cliente, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Cliente, String> t) {
                if (t.getNewValue().equals("") || t.getNewValue().length() < 3) {
                    System.out.println("hola");

                    // workaround for refreshing rendered values
                    t.getTableView().getColumns().get(0).setVisible(false);
                    t.getTableView().getColumns().get(0).setVisible(true);

                    // set the old value or do not update the cell.
                    return;
                }
                ((Cliente) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setApellidoM(t.getNewValue());
            }
        }
        );
        
        clmDireccion.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Cliente, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Cliente, String> t) {
                if (t.getNewValue().equals("") || t.getNewValue().length() < 3) {
                    System.out.println("hola");

                    // workaround for refreshing rendered values
                    t.getTableView().getColumns().get(0).setVisible(false);
                    t.getTableView().getColumns().get(0).setVisible(true);

                    // set the old value or do not update the cell.
                    return;
                }
                ((Cliente) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setDireccion(t.getNewValue());
            }
        }
        );
        
        clmCorreo.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Cliente, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Cliente, String> t) {
                if (t.getNewValue().equals("") || t.getNewValue().length() < 3) {
                    System.out.println("hola");

                    // workaround for refreshing rendered values
                    t.getTableView().getColumns().get(0).setVisible(false);
                    t.getTableView().getColumns().get(0).setVisible(true);

                    // set the old value or do not update the cell.
                    return;
                }
                ((Cliente) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setCorreo(t.getNewValue());
            }
        }
        );
        
        clmTelefono.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Cliente, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Cliente, String> t) {
                if (t.getNewValue().equals("") || t.getNewValue().length() < 3) {
                    System.out.println("hola");

                    // workaround for refreshing rendered values
                    t.getTableView().getColumns().get(0).setVisible(false);
                    t.getTableView().getColumns().get(0).setVisible(true);

                    // set the old value or do not update the cell.
                    return;
                }
                ((Cliente) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setTelefono(t.getNewValue());
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
}




    
    
    
    
    
    
    
    

    


//FALTA APLICAR VALIDACION AUTOMATICA EN CADA CAMPO
