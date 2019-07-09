/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Conexion;
import model.Producto;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */




public class ProductoController implements Initializable {
  
    private Conexion con;
    
    private ObservableList<Producto> listaProducto;
    
    
    
    @FXML
    private TableView<Producto> tablaProductos;
    @FXML
    private TableColumn<Producto, String> clmNombreProducto;
    @FXML
    private TableColumn<Producto, Integer> clmPrecioProducto;
    @FXML
    private TableColumn<Producto, String> clmDescripcionProducto;
    @FXML
    private TableColumn<Producto, Integer> clmProveedorProducto;
    @FXML
    private TableColumn<Producto, String> clmCodigoProducto;
    @FXML
    private TableColumn<Producto, Integer> clmStock;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        llenar();
        
    }    
    
    public void llenar(){
        con = new Conexion();
        
        listaProducto = FXCollections.observableArrayList();
        Producto.llenarListaProductos(con.getConexion(), listaProducto);
        con.cerrarConexion();
        
        tablaProductos.setItems(listaProducto);
        
        clmCodigoProducto.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        clmNombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmPrecioProducto.setCellValueFactory(new PropertyValueFactory<>("precio"));
        clmDescripcionProducto.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        clmStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        clmProveedorProducto.setCellValueFactory(new PropertyValueFactory<>("fkProveedor"));
    }
    
    
    
    
    
}
