/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.ChoiceBoxTreeCell;
import javafx.scene.control.cell.ComboBoxListCell;

import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import model.Cliente;
import model.Conexion;
import model.Detalle;
import model.Producto;
import model.Usuario;
import model.Venta;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class VentaController implements Initializable {

    private Conexion con;

    //private int numVenta = Venta.obtenerUltimaVenta(con.getConexion());
    private ObservableList<Producto> listaProducto;
    private ObservableList<Detalle> listaDetalle;
    private ObservableList<Usuario> listaUsuario;
    private ObservableList<Cliente> listaCliente;
    ///parametros de venta
    private Usuario usuario = null;
    private Producto producto = null;
    private Venta venta = null;
    private int indiceItemDetalle = 0;
    private Cliente clienteObtenido = null;
    int cliente = 0;
    int PrecioTotal; 

    private int cantidad = 1;
    private int total = 0;
    private int vuelto = 0 ;  

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
    private JFXButton btnRealizarVenta;
    @FXML
    private Label lblPrecio;
    private TextField nombreUsuario;
    @FXML
    private Label lblPreviewCliente;
    @FXML
    private Label lblPreviewProducto;
    @FXML
    private Spinner<Integer> spinCantidad = new Spinner<Integer>();
    @FXML
    private JFXButton btnAgregarAlaLista;
    @FXML
    private Label previewPrecio;
    @FXML
    private TableView<Detalle> carritoDeCompra;
    @FXML
    private TableColumn<Detalle, String> clmCodigoProductoCarritoCompras;
    @FXML
    private TableColumn<Detalle, String> clmNombreProductoCarritoCompra;
    @FXML
    private TableColumn<Detalle, String> clmCantidadCarritoDeCompra;
    @FXML
    private TableColumn<Detalle, Integer> clmTotalCarritoCompras;
    @FXML
    private JFXButton btnBuscarUsuario;
    @FXML
    private TextField txtBuscarUsuario;
    @FXML
    private Label lblInstanciaVenta;
    @FXML
    private TextField txtIntMontoPagoCliente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        usuario = Usuario.obtenerUsuarioEnSession();

        venta = instanciaDeVenta();
        listaDetalle = FXCollections.observableArrayList();
        listaUsuario = FXCollections.observableArrayList();
        listaCliente = FXCollections.observableArrayList();
        listaProducto = FXCollections.observableArrayList();

        llenar();
        tableEditable();
        spinereditable();
        llenarLista();
        instanciaDeVenta();
        valorSpiner();
        txtIntMontoPagoCliente.setOnKeyTyped(event -> SoloNumerosEnteros(event));
         btnRealizarVenta.setDisable(true);
        lblInstanciaVenta.setText(String.valueOf(venta.getIdVenta()));
        
        habilitarBtnRealizarVenta();
        
        
    

    }

    public void valorSpiner() {
        int spinEmpiezaEn = 1;
        SpinnerValueFactory<Integer> valueFactory
                = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, spinEmpiezaEn);
        spinCantidad.setValueFactory(valueFactory);
    }

    public void llenar() {
        con = new Conexion();

        Usuario.llenarListaUsuarios(con.getConexion(), listaUsuario);
        Cliente.llenarListaClientes(con.getConexion(), listaCliente);

        Producto.llenarListaProductos(con.getConexion(), listaProducto);

        /////////PRODUCTO ///////////////////
        tablaProductos.setItems(listaProducto);

        clmCodigoProducto.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        clmNombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        clmPrecioProducto.setCellValueFactory(new PropertyValueFactory<>("precio"));
        clmDescripcionProducto.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        clmStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        clmProveedorProducto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProveedorProducto().getNombreProveedor()));
        
        

        // agregar el valor de la multiplicacion cantidad por percio de producto
        con.cerrarConexion();
    }

    public void llenarLista() {

        carritoDeCompra.setItems(listaDetalle);

        clmCodigoProductoCarritoCompras.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductoVenta().getCodigo()));

        clmNombreProductoCarritoCompra.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductoVenta().getNombreProducto()));

        clmCantidadCarritoDeCompra.setCellValueFactory(new PropertyValueFactory("cantidad"));

        clmTotalCarritoCompras.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().CantidadProperty().get() * param.getValue().getProductoVenta().getPrecio()));

        carritoDeCompra.setPlaceholder(new Label("No hay Articulos Agregados a la compra en la venta Nº " + venta.getIdVenta()));

    }

    @FXML
    public void tableEditable() {

        tablaProductos.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Producto>() {
            @Override
            public void changed(ObservableValue<? extends Producto> observable, Producto oldValue, Producto newValue) {
                //obtendre detos desde interfaz
                int cantidad = spinCantidad.getValue();
                int precio = newValue.getPrecio();
                int total = cantidad * precio;
                producto = newValue;

                String NombreProductoLabel = newValue.getNombreProducto();

                //seteare en otras partes
                lblPreviewProducto.setText("");
                lblPreviewProducto.setText(NombreProductoLabel);
                previewPrecio.setText("");
                previewPrecio.setText(String.valueOf(total));
            }
        });
    }

    public void spinereditable() {
        spinCantidad.valueProperty().addListener(new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                //To change body of generated methods, choose Tools | Templates.
                int cantidad = newValue;
                int precioReal = 0;

                try {
                    int precio = producto.getPrecio();
                    precioReal = precio;
                } catch (Exception e) {
                    int precio = 0;
                    precioReal = precio;
                }

                int total = cantidad * precioReal;

                if (producto == null) {
                    lblPrecio.setText(String.valueOf(0));
                } else {

                    previewPrecio.setText("");
                    previewPrecio.setText(String.valueOf(total));
                }
            }
        });

    }

    @FXML
    private void onActionBtnAgregarAlaLista(ActionEvent event) {
       AgregarALaLista();
    }
    
    
    public void AgregarALaLista(){
        boolean yaEstaEnLaLista = false;
        
         if (producto == null) {     
            return;
        }
        con = new Conexion();
        indiceItemDetalle = +indiceItemDetalle + 1;
        Usuario a = null;
        cantidad = spinCantidad.getValue();

        Detalle d = new Detalle(indiceItemDetalle, venta, producto, cantidad);
     
        if (d.getCantidad() <= producto.getStock()) {
            /// verificar existencia
            for (Detalle deta : listaDetalle) {
                if (deta.getProductoVenta().getIdProducto() == producto.getIdProducto()) {
                    deta.setCantidad(deta.getCantidad() + cantidad);
                    yaEstaEnLaLista = true; 
                }
            }
            if (!yaEstaEnLaLista) {
                  listaDetalle.add(d);
            }
          

            modificarStock(producto, cantidad);

            carritoDeCompra.refresh();
            tablaProductos.refresh();
            llenarLista();
            // se acualiza el total de todas las compras 
            total = total + clmTotalCarritoCompras.getCellData(d);
            lblPrecio.setText(String.valueOf(total));

            valorSpiner();
        } else {
            Alert alertaStock = new Alert(Alert.AlertType.ERROR);
            alertaStock.setTitle("STOCK INSUFICIENTE");
            alertaStock.show();
        }

        cantidad = 1;  // se cierra 
    
    
    }

    public int modificarStock(Producto p, int cantidadComprada) {
        int stockNuevo = 0;
        int stockAnterior = p.getStock();
        stockNuevo = stockAnterior - cantidadComprada;
        p.setStock(stockNuevo);

        return stockNuevo;
    }

    @FXML
    private void btnRealizarVenta(ActionEvent event) {

        if (listaDetalle.isEmpty()) {
            Alert aler = new Alert(Alert.AlertType.ERROR);
            aler.setTitle("LISTA VACIA");
            aler.setContentText("La lista de detalles esta vacia. Por favor seleccione productos para comprar");
            aler.show();
        } else {
            /////SE REALIZA CON EXITO
            realizarVenta(); // trabaja con db
            reiniciarVenta();// ui
            instanciaDeVenta();
            lblInstanciaVenta.setText(String.valueOf(venta.getIdVenta()));
        }

    }

    public Venta instanciaDeVenta() {
        con = new Conexion();

        Venta instanciaVenta = new Venta(Venta.obtenerUltimaVenta(con.getConexion()), usuario.getIdUsuario(), cliente, cantidad, " " , 0);
        venta = instanciaVenta;
        con.cerrarConexion();

        return instanciaVenta;
    }

    public void realizarVenta() {
        //-------------------------------VENTA INGRESA A DB -------------------------
        con = new Conexion();
        Venta.insertarVenta(con.getConexion(), venta, usuario.getIdUsuario(), cliente , total); // SE REALIZA LA VENTA DB 
        venta = instanciaDeVenta();
        con.cerrarConexion();
        con = new Conexion();
        Detalle.insertarDetalle(con.getConexion(), listaDetalle);

        con.cerrarConexion();
        con = new Conexion();
        Producto.modificarStock(con.getConexion(), listaDetalle);
        alertaVentaRealizadaConExito();
        instanciaDeVenta();
       
    }

    public void reiniciarVenta() {
        indiceItemDetalle = 0;

        listaDetalle.removeAll(listaDetalle);
        carritoDeCompra.setVisible(false);
        carritoDeCompra.setVisible(true);
        carritoDeCompra.refresh();

        cantidad = 1;
        txtBuscarUsuario.setText("");
        clienteObtenido = null;
        valorSpiner();
        lblPreviewProducto.setText("");
        lblPreviewCliente.setText("");
        lblPrecio.setText("0");
        previewPrecio.setText("");
        total = 0 ; 
        txtIntMontoPagoCliente.setText("0");
             carritoDeCompra.setPlaceholder(new Label("No hay Articulos Agregados a la compra en la venta Nº " + venta.getIdVenta()));
              tablaProductos.requestFocus();
        tablaProductos.getSelectionModel().select(0);
        tablaProductos.getFocusModel().focus(0);
    }

    @FXML
    private void setUsuarioInstanciaVenta(ActionEvent event) {
        String rut = txtBuscarUsuario.getText();
        boolean seBuscaPeroNoSeEncuentra = false ; 
        if (rut.trim().equals("")) {
            AlertasGeneral(1);
            return;
        }

        for (Cliente c : listaCliente) {
         
            if (rut.equals(c.getRut_cliente())) {
                System.out.println("Se Encuentra cliente en la db");
                clienteObtenido = c;
                cliente = clienteObtenido.getIdCliente();
                System.out.println(c.getRut_cliente());
                venta.setIdCliente(c.getIdCliente());
                lblPreviewCliente.setText(c.getNombre() + "    " + c.getApellidoP());
                return;
                
            }else {
                seBuscaPeroNoSeEncuentra = false ; 
            }
        }
        
        if (!seBuscaPeroNoSeEncuentra) {
                AlertasGeneral(2);
        }
    }
    
    public void AlertasGeneral(int tipo){
        switch (tipo) {
            case 1:
                          Alert alertCampoVacio = new Alert(Alert.AlertType.INFORMATION);
                         alertCampoVacio.setHeaderText("Cliente No encontrado");
                         alertCampoVacio.setTitle("RUT INVALIDOº ");
                         alertCampoVacio.setContentText("Debes ingresar un rut valido y que este registrado en la base de datos");
                         alertCampoVacio.show();
                
                
                break;
                  case 2:
                         Alert alertNoSeEncuentraCliente= new Alert(Alert.AlertType.INFORMATION);
                         alertNoSeEncuentraCliente.setHeaderText("Cliente No encontrado");
                         alertNoSeEncuentraCliente.setTitle("Campo Vacioº ");
                         alertNoSeEncuentraCliente.setContentText("Debes ingresar un rut valido y que este registrado en la base de datos");
                         alertNoSeEncuentraCliente.show();
                break;
            default:
                throw new AssertionError();
        }
    
    }

    public void alertaVentaRealizadaConExito() {
        String msjClienteEstaOno = "";
   
        if (clienteObtenido == null) {
            msjClienteEstaOno = " no especificado";
            
           
        } else {
            msjClienteEstaOno = clienteObtenido.getNombre() + "  " + clienteObtenido.getApellidoP() + " \n,rut : " + clienteObtenido.getRut_cliente();
        }
        
          int montoPagoCliente = Integer.parseInt(txtIntMontoPagoCliente.getText());
          int precioDeLaVenta = PrecioTotal;
          
          int vuel=  montoPagoCliente - precioDeLaVenta; 

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Venta Realizada Con Exito");
        alert.setTitle("Venta Nº " + venta.getIdVenta());
        
        

        alert.setContentText("USUARIO:  " + usuario.getPrimerNom().toUpperCase() + "  " + usuario.getPrimerApe().toUpperCase()+ "\n al CLIENTE:  " + msjClienteEstaOno.toUpperCase() + "\n CAMBIO:  " + vuel);
        alert.show();
        alert.setWidth(400);
        alert.setHeight(600);
        

    }

    public void SoloNumerosEnteros(KeyEvent keyEvent) {
        
        System.out.println("");
        
        try {
            char key = keyEvent.getCharacter().charAt(0);
            if (!Character.isDigit(key)) {
                keyEvent.consume();                
            } 
        } catch (Exception ex) {
        }
    }
    
   
    
    public void habilitarBtnRealizarVenta() {
        int valorNuevo = 0;        
        int valorAntiguo = 0;        
        
        try {
            txtIntMontoPagoCliente.textProperty().addListener((observable, oldValue, newValue) -> {
               
                if (newValue.length() > 8) {
                        txtIntMontoPagoCliente.setText(oldValue);
                    }
                 if (newValue.equals("")) {
                     // no es posble hacer el calculo
                    btnRealizarVenta.setDisable(true);
                    
                } else {
                     /// es posible
                    int montoPagoCliente = Integer.parseInt(newValue);
                    int extraerPrecioDeVenta = Integer.parseInt(lblPrecio.getText());
                    int precioDeLaVenta = extraerPrecioDeVenta;
                    
                    System.out.println(montoPagoCliente);
                    System.out.println(precioDeLaVenta);
                    
                    if (montoPagoCliente >= precioDeLaVenta && precioDeLaVenta != 0  ) {
                        btnRealizarVenta.setDisable(false);
                        PrecioTotal = precioDeLaVenta;
                    }else
                    {btnRealizarVenta.setDisable(true);}
                 }
            });
        } catch (Exception e) {
            System.out.println("ERROR");
        }
    }

    @FXML
    private void onMouseClickedAgregarProductoALista(MouseEvent event) {
        if (event.getClickCount() == 2) //Checking double click
    {
            AgregarALaLista();
    }
            
    }
}







