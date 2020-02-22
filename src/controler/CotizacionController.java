/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import static com.itextpdf.kernel.pdf.PdfName.C;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.pdfa.PdfADocument;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Callback;
import model.Cliente;
import model.Conexion;
import model.Cotizacion;
import model.Detalle;
import model.Producto;
import model.Usuario;
import model.Venta;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class CotizacionController implements Initializable {

    private Conexion con;
    public static String data = " "; 

    //private int numVenta = Venta.obtenerUltimaVenta(con.getConexion());
    private ObservableList<Producto> listaProducto;
    private ObservableList<Detalle> listaDetalle;
    private ObservableList<Usuario> listaUsuario;
    private ObservableList<Cliente> listaCliente;
    ///parametros de venta
    private Usuario usuario = null;
    private Producto producto = null;
    private Cotizacion cotizacion = null;
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
    private TextField txtIntMontoPagoCliente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        usuario = Usuario.obtenerUsuarioEnSession();

        cotizacion = instanciaDeCotizacion();
        listaDetalle = FXCollections.observableArrayList();
        listaUsuario = FXCollections.observableArrayList();
        listaCliente = FXCollections.observableArrayList();
        listaProducto = FXCollections.observableArrayList();

        llenar();
        tableEditable();
        spinereditable();
        llenarLista();
        instanciaDeCotizacion();
        valorSpiner();
        //txtIntMontoPagoCliente.setOnKeyTyped(event -> SoloNumerosEnteros(event));
      
        lblInstanciaVenta.setText(String.valueOf(cotizacion.getIdCotizacion()));
        
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

        carritoDeCompra.setPlaceholder(new Label("No hay Articulos Agregados a la compra en la venta Nº " + cotizacion.getIdCotizacion()));
        
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
        if (producto == null) {
            return;
        }
        con = new Conexion();
        indiceItemDetalle = +indiceItemDetalle + 1;
        Usuario a = null;
        cantidad = spinCantidad.getValue();
        Venta Ficticia = new Venta(1, 1, 10, 1, "" , 0 );
        

        Detalle d = new Detalle(indiceItemDetalle, Ficticia, producto, cantidad);

        if (d.getCantidad() >= producto.getStock()) {
            Alert alertaStock = new Alert(Alert.AlertType.WARNING);
            alertaStock.setTitle("STOCK INSUFICIENTE");
            alertaStock.setContentText("No hay unidades disponibles de este preducto, de todas formas se realizara la cotizacion, se sugiere recargar STOCK!");
            alertaStock.show();

        }

        listaDetalle.add(d);
        carritoDeCompra.refresh();
        tablaProductos.refresh();
        llenarLista();
        // se acualiza el total de todas las compras 
        total = total + clmTotalCarritoCompras.getCellData(d);
        lblPrecio.setText(String.valueOf(total));

        valorSpiner();
        cantidad = 1;  // se cierra 

    }

    //public int modificarStock(Producto p, int cantidadComprada) {
      //  int stockNuevo = 0;
       // int stockAnterior = p.getStock();
        //stockNuevo = stockAnterior - cantidadComprada;
        //p.setStock(stockNuevo);

       // return stockNuevo;
    //}

    @FXML
    private void btnRealizarVenta(ActionEvent event) throws FileNotFoundException {
        Boolean condicionesParaRealizarCotizacion = true; 
        if (listaDetalle.isEmpty()) {
            condicionesParaRealizarCotizacion = false;
            Alert aler = new Alert(Alert.AlertType.WARNING);
            aler.setTitle("LISTA VACIA");
            aler.setContentText("La lista de detalles esta vacia. Por favor seleccione productos para comprar");
            aler.show();
            return;
        } 
        
        if (clienteObtenido == null ) {
            condicionesParaRealizarCotizacion = false;
            Alert aler = new Alert(Alert.AlertType.ERROR);
            aler.setTitle("NO SE ENCUENTRA CLIENTE");
            aler.setContentText("Se debe seleccionar un Cliente");
            txtBuscarUsuario.setFocusTraversable(true);
            aler.show();
            return;
        }
        if (condicionesParaRealizarCotizacion) 
         {
            /////SE REALIZA CON EXITO
            realizarCotizacion(); // trabaja con db
            reiniciarVenta();// ui
            instanciaDeCotizacion();
            lblInstanciaVenta.setText(String.valueOf(cotizacion.getIdCotizacion()));
        }

    }

    public Cotizacion instanciaDeCotizacion() {
        con = new Conexion();

        Cotizacion instanciaCotizacion = new Cotizacion(Cotizacion.obtenerUltimaCotizacion(con.getConexion()), usuario.getIdUsuario(), cliente, " ", 1);
        cotizacion = instanciaCotizacion;
        con.cerrarConexion();
        carritoDeCompra.setPlaceholder(new Label("No hay Articulos Agregados a la compra en la venta Nº " + cotizacion.getIdCotizacion()));
        return instanciaCotizacion;
    }

    public void realizarCotizacion() throws FileNotFoundException {
        con = new Conexion();

        FileChooser fileChooser = new FileChooser();

        Window stage = tablaProductos.getScene().getWindow();
        fileChooser.setTitle("Guardar Cotizacion");
        fileChooser.setInitialFileName("Cotizacion" + cotizacion.getIdCotizacion());
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("textFile", "*.pdf"));
        
        try {
            File file = fileChooser.showSaveDialog(stage); // se buscar directorio
            fileChooser.setInitialDirectory(file.getParentFile());
            String ruta = file.getAbsolutePath();

            PdfWriter writer = new PdfWriter(ruta);
            PdfDocument pdf = new PdfDocument(writer);

            Document document = new Document(pdf);
            document.add(new Paragraph("VENTS APLICATION COTIZACION").setBold().setMargin(30));
            document.add(new Paragraph("Empresa: InacapCorps\nDireccion: Matteo Zambrano #3355\n Fecha:" + LocalDate.now() + "\nCliente: " + clienteObtenido.getNombre() + "  " + clienteObtenido.getApellidoP()));
            document.add(new Paragraph(""));
            
            //----------------Table PDF ----------------------------
            
            float[] pointColumnWidths = {90F, 90F, 90F, 90F, 90F,};
            Table table = new Table(pointColumnWidths).setMarginRight(30);

            // Adding cells to the table       
            table.addHeaderCell("Codigo");
            table.addHeaderCell("Producto");
            table.addHeaderCell("Precio");
            table.addHeaderCell("Cantidad");
            table.addHeaderCell("Total");
            
            int totalCotizacion = 0;
            
            for (Detalle detalle : listaDetalle) {
                table.addCell(detalle.getProductoVenta().getCodigo());
                table.addCell(detalle.getProductoVenta().getNombreProducto());
                table.addCell(String.valueOf(detalle.getProductoVenta().getPrecio()));
                table.addCell(String.valueOf(detalle.getCantidad()));
                int totalPrecio = detalle.getCantidad() * detalle.getProductoVenta().getPrecio();
                table.addCell(String.valueOf(totalPrecio));
                totalCotizacion = totalCotizacion + totalPrecio;
            }

            //String imageFile = "D:\\'OneDrive - Universidad Tecnologica de Chile INACAP'\\Attachments\\tercer semestre\\diseño-de-aplicaciones\\imgVents.presentaci.jpg"; 
            //ImageData data = ImageDataFactory.create(imageFile);
            //Image img = new Image(data); 
            // img.setFixedPosition(100, 250);
            // document.add(img);
            document.add(table);
            
            //---------------END TABLE----------------
            
            //Agradar al documento.. 
            document.add(new Paragraph("TOTAL COTIZACION:      " + String.valueOf(totalCotizacion)).setBold().setMargin(60));
            document.add(new Paragraph("Esta Cotizacion tiene una validez de 30 dias").setMarginLeft(20));

            document.close();
            Cotizacion.insertarCotizacion(con.getConexion(), cotizacion, cliente, usuario.getIdUsuario(), Integer.parseInt(lblPrecio.getText()));
            cotizacion = instanciaDeCotizacion();
            con.cerrarConexion();

            alertaCotizacionImpresaConExito();
            instanciaDeCotizacion();
            

        } catch (Exception e) {
            System.out.println("no pude ser posible");
        }

        // System.out.println(directorioSeleccionado.getAbsolutePath());
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

    }

    @FXML
    private void setUsuarioInstanciaVenta(ActionEvent event) {
        String rut = txtBuscarUsuario.getText();
        boolean clienteEncontrado = false ; 
        
        if (rut.trim().equals("")) {
            AlertaCotizacionBtnBuscarCliente(1);
            return;
        }

        for (Cliente c : listaCliente) {
          
            if (rut.equals(c.getRut_cliente())) {
                clienteObtenido = c;
                cliente = clienteObtenido.getIdCliente();
                System.out.println(c.getRut_cliente());
                cotizacion.setFkCliente(c.getIdCliente());
                lblPreviewCliente.setText(c.getNombre() + "    " + c.getApellidoP());
                clienteEncontrado = true;
                return;
            }
        }
        if (!clienteEncontrado) {
             AlertaCotizacionBtnBuscarCliente(2);
        }
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
    
    
    
    
    
    
    //**==================ALERTAS=======================**// 
    
    
     public void AlertaCotizacionBtnBuscarCliente(int causa){
        switch (causa) {
            case 1:
                Alert alertaCampoVacioCleinte = new Alert(Alert.AlertType.ERROR);
                alertaCampoVacioCleinte.setTitle("Campo sin Datos");
                alertaCampoVacioCleinte.setContentText("Por favor ingresa el rut del cliente");
                alertaCampoVacioCleinte.show();
                
                break;
             case 2:
                 Alert alertaNoSeEncuentraCliente = new Alert(Alert.AlertType.ERROR);
                alertaNoSeEncuentraCliente.setTitle("No se encuentra cliente");
                alertaNoSeEncuentraCliente.setContentText("Asegurate de digitar correctamente el rut del cliente");
                alertaNoSeEncuentraCliente.show();
                lblPreviewCliente.setText("Cliente No especificado");
                txtBuscarUsuario.setFocusTraversable(true);
                 txtBuscarUsuario.setText("");
                break;     
           
        }
    }
     
     
     public void alertaCotizacionImpresaConExito() {
        String msjClienteEstaOno = "";
   
        if (clienteObtenido == null) {
            msjClienteEstaOno = " Cliente no especificado";
        } else {
            msjClienteEstaOno = clienteObtenido.getNombre().toUpperCase() + "  " + clienteObtenido.getApellidoP().toUpperCase() + " \nRut : " + clienteObtenido.getRut_cliente();
        }
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Cotizacion Realizada Con Exito");
        alert.setTitle("Cotizacion Nº " + cotizacion.getIdCotizacion());
        
        

        alert.setContentText("USUARIO:  " + usuario.getPrimerNom().toUpperCase() + "  " + usuario.getPrimerApe().toUpperCase()+ "\n al CLIENTE:  " + msjClienteEstaOno.toUpperCase());
        alert.show();
        alert.setWidth(550);
        alert.setHeight(600);
    }

}







