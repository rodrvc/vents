/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import static jdk.nashorn.internal.runtime.Context.printStackTrace;
import model.Cliente;
import model.Conexion;
import model.Usuario;
import model.Venta;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class RepostesController implements Initializable {
    
    private Conexion con;
    
    private ObservableList<Cliente> listaCliente;
    private ObservableList<Usuario> listaUsuario;
    private ObservableList<Venta> listaVenta;
    private ObservableList<Venta> listaVentaFiltrada;
    

    @FXML
    private TableView<Venta> tablaVentas;
    @FXML
    private TableView<Cliente> tablaBuscarCliente;
    @FXML
    private JFXButton btnGeneralReporte;
    @FXML
    private TableColumn<Venta, String> clmIdVenta;
    @FXML
    private TableColumn<Venta, String> clmIdUsuario;
    @FXML
    private TableColumn<Venta, String> clmClienteTbVenta;
    @FXML
    private TableColumn<Venta, String> clmFecha;
    @FXML
    private TableColumn<Venta, String> clmTotal;
    @FXML
    private TableColumn<Cliente, String> clmRutClienteTbCliente;
    @FXML
    private TableColumn<Cliente, String> clmNombreTbCliente;
    @FXML
    private TableColumn<Cliente, String> clmApellidoTbCliente;
    @FXML
    private Label idTotal;
    @FXML
    private ChoiceBox<Usuario> idListVendedores;
    @FXML
    private JFXDatePicker datePickerInicio;
    @FXML
    private JFXDatePicker datePickerFinal;
    
    
    
    
    
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        view();
        llenar();
    }    



    @FXML
    private void btnActionGenerarReporte(ActionEvent event) {
        generarReporte();
    }
    
    public void view(){
        listaCliente = FXCollections.observableArrayList();
        listaUsuario = FXCollections.observableArrayList();
        listaVenta= FXCollections.observableArrayList();
        listaVentaFiltrada= FXCollections.observableArrayList();
    }
    
    public void llenar() {
        con = new Conexion();

        Venta.llenarListaVenta(con.getConexion(), listaVenta);
        Cliente.llenarListaClientes(con.getConexion(), listaCliente);
        Usuario.llenarListaUsuarios(con.getConexion(), listaUsuario);

        //=========Tabla Venta ===============// 
        tablaVentas.setItems(listaVenta);

        clmIdVenta.setCellValueFactory(new PropertyValueFactory("idVenta"));
        clmIdUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(Venta.obtenerNombreUsuario(listaUsuario, cellData.getValue())));
        clmTotal.setCellValueFactory(new PropertyValueFactory("total"));

        clmFecha.setCellValueFactory(new PropertyValueFactory("fechaDeVenta"));

        clmClienteTbVenta.setCellValueFactory(cellData -> new SimpleStringProperty(Venta.obtenerNombreCliente(listaCliente, cellData.getValue())));

        //=========END TABLA VENTA================// 
        //===========TABLA Cliente ================//
        tablaBuscarCliente.setItems(listaCliente);

        clmRutClienteTbCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRut_cliente()));
        clmNombreTbCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        clmApellidoTbCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellidoP()));

        //     //=========Lista Usuario ================// 
        idListVendedores.setItems(listaUsuario);
        

        LocalDate a = LocalDate.of(2019, Month.MARCH, 5);

        for (Venta cliente : listaVenta) {
            String[] fecha = cliente.getFechaDeVenta().split(" ");

            String fecha1 = fecha[0];
            System.out.println(fecha1);
            LocalDate e = LocalDate.parse(fecha1);
            System.out.println(e);
            System.out.println(a);
        }
           //=========Data picker================// 
           datePickerFinal.setValue(LocalDate.now());
           datePickerInicio.setValue(LocalDate.now());
           
        LocalDate minDate = LocalDate.of(1989, 4, 16);
        LocalDate maxDateParaInicio = datePickerFinal.getValue();
        LocalDate maxDate = LocalDate.now();
        
        
        
        datePickerFinal.setDayCellFactory(d
                -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
                if (datePickerInicio.getValue().isAfter(datePickerFinal.getValue())) {
                    datePickerInicio.setValue(datePickerFinal.getValue());
                }
            }
        });

        
        
        
        
        datePickerInicio.setDayCellFactory(d
                -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isAfter(datePickerFinal.getValue()) || item.isBefore(minDate));
            }
        });
    }
    
    
    public void generarReporte(){
        
        listaVentaFiltrada.removeAll(listaVentaFiltrada);
    
       
        
       // for (Venta venta : listaVenta) {
        //    if (venta.getTotal() > 6000) {
      //          listaVentaFiltrada.add(venta);
      //      }
     //   }
        Cliente clienteFiltro = null ; 
        int total = 0 ;
      
        for (Venta venta : listaVenta) {
            String[] soloFecha = venta.getFechaDeVenta().split(" ");
      // && LocalDate.parse(soloFecha[0]).isEqual(datePickerFinal.getValue() )
     
       
           
            try { // isEqual(datePickerInicio.getValue()) // 
                 if (datePickerInicio.getValue().isBefore(LocalDate.parse(soloFecha[0])) || datePickerInicio.getValue().isEqual(LocalDate.parse(soloFecha[0])) ) {
                        
                     if (datePickerFinal.getValue().isAfter(LocalDate.parse(soloFecha[0])) || datePickerFinal.getValue().isEqual(LocalDate.parse(soloFecha[0]))) {
                         if (idListVendedores.getValue() == null) {
                             listaVentaFiltrada.add(venta);     
                            total = total + venta.getTotal() ; 
                         } else if (venta.getIdUsuario() == idListVendedores.getValue().getIdUsuario()) {
                              listaVentaFiltrada.add(venta);
                               total = total + venta.getTotal() ; 
                         }
                     }
                   
            }
            } catch (Exception e) {
                System.out.println(e);
                printStackTrace(e);
            }
            
            
            
            
           
           
        }
        
        
        
        
        idTotal.setText(String.valueOf(total));
        tablaVentas.setItems(listaVentaFiltrada);
         tablaVentas.refresh();
        tablaVentas.setVisible(false);
        tablaVentas.setVisible(true);
        tablaVentas.refresh();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    

}
