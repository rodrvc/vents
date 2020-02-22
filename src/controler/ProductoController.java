/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.ComboBoxTableCell;

import javafx.scene.control.cell.PropertyValueFactory;
import model.Conexion;
import model.Producto;
import model.Proveedor;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */




public class ProductoController implements Initializable {
  
    private Conexion con;
    
    private ObservableList<Producto> listaProducto;
    private ObservableList<Proveedor> listaProveedor;
    
    
    
    @FXML
    private TableView<Producto> tablaProductos;
    @FXML
    private TableColumn<Producto, String> clmNombreProducto;
    @FXML
    private TableColumn<Producto, Integer> clmPrecioProducto;
    @FXML
    private TableColumn<Producto, String> clmDescripcionProducto;
    @FXML
    private TableColumn<Producto, String> clmProveedorProducto;
    @FXML
    private TableColumn<Producto, String> clmCodigoProducto;
    @FXML
    private TableColumn<Producto, Integer> clmStock;
    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private JFXComboBox<Proveedor> cbProveedor;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtStock;
    @FXML
    private JFXButton btnAgregarProducto;
    @FXML
    private JFXButton btnEliminarProducto;
    @FXML
    private JFXButton btnLimpiar;
    @FXML
    private JFXButton btnModificar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        llenar();
         tableEditable();
        
    }    
    
    public void llenar(){
        
        
        listaProducto = FXCollections.observableArrayList();
        listaProveedor= FXCollections.observableArrayList();
        
        con = new Conexion();
        Producto.llenarListaProductos(con.getConexion(), listaProducto);
        con.cerrarConexion();
        
        con = new Conexion();
        Proveedor.llenarInformacion(con.getConexion(), listaProveedor);
        con.cerrarConexion();
        
        tablaProductos.setItems(listaProducto);
        cbProveedor.setItems(listaProveedor);
        
        
        clmCodigoProducto.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        clmNombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmPrecioProducto.setCellValueFactory(new PropertyValueFactory<>("precio"));
        clmDescripcionProducto.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        clmStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        clmProveedorProducto.setCellValueFactory( cellData -> new SimpleStringProperty(cellData.getValue().getProveedorProducto().EmailProveedorProperty().get()));
        
        
        
    }

    @FXML
    private void actionButtonAgregarProducto(ActionEvent event) {
        
        
        Proveedor pro = cbProveedor.getValue();
        String nombre = txtNombre.getText();
        int precio = Integer.parseInt(txtPrecio.getText());
        String descripcion = txtDescripcion.getText();
        int stock = Integer.parseInt(txtStock.getText());
        String codigo = txtCodigo.getText();
        
        Producto nuevoProducto = new Producto(0 , codigo, nombre, precio, descripcion, stock, pro);
       
    
        
        nuevoProducto.insertarProducto(con.getConexion());
        con.cerrarConexion();
        
        listaProducto.add(nuevoProducto);
        tablaProductos.setVisible(false);
        tablaProductos.setVisible(true);
        tablaProductos.refresh();
        tablaProductos.setVisible(false);
        tablaProductos.setVisible(true);
        
    }

    @FXML
    private void actionEliminarProducto(ActionEvent event) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setContentText("Presiona aceptar para ELIMINAR, esta propiedad sera modificada");
            alert.setHeaderText("PELIGRO ");
            alert.setTitle("ELIMINAR PRODUCTO");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Se modifica atributo en la base de datos
                
                Producto p = tablaProductos.getSelectionModel().getSelectedItem();
                
                p.eliminarProducto(con.getConexion());
                
                con.cerrarConexion();
                listaProducto.remove(p);
                
                tablaProductos.setVisible(false);
                tablaProductos.setVisible(true);
                tablaProductos.refresh();
                tablaProductos.setVisible(false);
                tablaProductos.setVisible(true);
                
                System.out.println("se elimino producto");  

            } 
    }
    
    
    public void modificarProducto(){
        Producto productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        Producto Modificado = null ; 
      
       
        
        txtCodigo.setText(productoSeleccionado.getCodigo());
        txtDescripcion.setText(productoSeleccionado.getDescripcion());
        txtNombre.setText(productoSeleccionado.getNombreProducto());
        txtPrecio.setText(String.valueOf(productoSeleccionado.getPrecio()));
        txtStock.setText(String.valueOf(productoSeleccionado.getPrecio()));
        
        cbProveedor.getValue().getRutProveedor();
        
        //String codigo = txtCodigo.getText();
        //String descripcion = txtDescripcion.getText();
        //String nombre = txtNombre.getText();
        //int precio = Integer.parseInt(txtPrecio.getText());
        //int stock = Integer.parseInt(txtStock.getText());
        
        
       
  
      }
    
    
    public void seleccionarProducto(){
        Producto productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        Producto Modificado = null ; 
        String cod = productoSeleccionado.getCodigo();
        txtCodigo.setText(cod);
        txtDescripcion.setText(productoSeleccionado.getDescripcion());
        txtNombre.setText(productoSeleccionado.getNombreProducto());
        txtPrecio.setText(String.valueOf(productoSeleccionado.getPrecio()));
        txtStock.setText(String.valueOf(productoSeleccionado.getPrecio()));
        
        cbProveedor.setValue(productoSeleccionado.getProveedorProducto());
        
        txtCodigo.setDisable(true);
    }
    
    
    
    public void tableEditable() {
        tablaProductos.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Producto>() {
            @Override
            public void changed(ObservableValue<? extends Producto> observable, Producto oldValue, Producto newValue) {
                //se obtiene elemento seleccionado
                seleccionarProducto();
            }
        });
    }
    
    
    public void limpiarCampos(){
        txtCodigo.setText("");
        txtDescripcion.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
        txtCodigo.setDisable(false);
    }

    @FXML
    private void ActionLimpiarCampos(ActionEvent event) {
        limpiarCampos();
    }

    @FXML
    private void accionModificar(ActionEvent event) {
        
    }
}



