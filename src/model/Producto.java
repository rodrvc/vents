package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

public class Producto {

    private IntegerProperty idProducto;
    private StringProperty codigo;

    private StringProperty nombreProducto;
    private IntegerProperty precio;
    private StringProperty descripcion;
    private IntegerProperty stock;
    private Proveedor proveedorProducto;

    public Producto(int idProducto, String codigo, String nombreProducto, int precio,
            String descripcion, int stock, Proveedor proveedorProducto) {
        this.idProducto = new SimpleIntegerProperty(idProducto);
        this.codigo = new SimpleStringProperty(codigo);
        this.nombreProducto = new SimpleStringProperty(nombreProducto);
        this.precio = new SimpleIntegerProperty(precio);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.stock = new SimpleIntegerProperty(stock);
        this.proveedorProducto = proveedorProducto;
    }

    ///
    public int getIdProducto() {
        return idProducto.get();
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = new SimpleIntegerProperty(idProducto);
    }

    public IntegerProperty idProductoProperty() {
        return idProducto;
    }

    //Metodos atributo: codigo
    public String getCodigo() {
        return codigo.get();
    }

    public void setCodigo(String codigo) {
        this.codigo = new SimpleStringProperty(codigo);
    }

    public StringProperty CodigoProperty() {
        return codigo;
    }
    //Metodos atributo: nombreProducto

    public String getNombreProducto() {
        return nombreProducto.get();
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = new SimpleStringProperty(nombreProducto);
    }

    public StringProperty NombreProductoProperty() {
        return nombreProducto;
    }
    //Metodos atributo: precio

    public int getPrecio() {
        return precio.get();
    }

    public void setPrecio(int precio) {
        this.precio = new SimpleIntegerProperty(precio);
    }

    public IntegerProperty PrecioProperty() {
        return precio;
    }
    //Metodos atributo: descripcion

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = new SimpleStringProperty(descripcion);
    }

    public StringProperty DescripcionProperty() {
        return descripcion;
    }
    //Metodos atributo: stock

    public int getStock() {
        return stock.get();
    }

    public void setStock(int stock) {
        this.stock = new SimpleIntegerProperty(stock);
    }

    public IntegerProperty StockProperty() {
        return stock;
    }
    //Metodos atributo: proveedorProducto

    public Proveedor getProveedorProducto() {
        return proveedorProducto;
    }

    public void setProveedorProducto(Proveedor proveedorProducto) {
        this.proveedorProducto = proveedorProducto;
    }

    ///////////////////////////////////METODOS MODELO /////////////////////////////////
    public static Producto llenarListaProductos(Connection con, ObservableList<Producto> lista) {
        int autorizado = 0;
        int Paautorizado = 0;
        Producto pro = null;
        int proveedor = 0;

        try {
            Statement statement = con.createStatement();
            ResultSet result;
            result = statement.executeQuery(" SELECT  id_producto,  codigo, nombre_producto, precio , descripcion, stock, precio, fproveedor , id_proveedor , rut_proveedor, email, direccion, categoria, nombre  From  vents.producto, vents.proveedor where fproveedor = id_proveedor;");

            while (result.next()) {
                System.out.println("Exito en la autorizacion");
                //producto
                int idPro = result.getInt("id_producto");
                String codigo = result.getString("codigo");
                String nombre = result.getString("nombre_producto");
                int precio = result.getInt("precio");
                String descripcion = result.getString("descripcion");
                int stock = result.getInt("stock");
                //proveedorProducto
                int idproveedor = result.getInt("id_proveedor");
                String nombreProveedor = result.getString("nombre");
                String email = result.getString("email");
                String direccion = result.getString("direccion");
                String categoria = result.getString("categoria");
                String rut = result.getString("rut_proveedor");

                pro = new Producto(idPro, codigo, nombre, precio, descripcion, stock, new Proveedor(idproveedor, rut, nombreProveedor, email, direccion, categoria));

                lista.add(pro);
                System.out.println("Exito en la senctencia");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR");
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR EN LA CONEXION");
        }
        return pro;
    }

    public static void modificarStock(Connection con, ObservableList<Detalle> listaDetalleVentas) {
        //update producto set stock =  1  where `id_producto` =  1 
        for (Detalle listaDetalleVenta : listaDetalleVentas) {

            try {
                String sql = "update producto set stock =  ? where `id_producto` =  ?";
                CallableStatement cs = con.prepareCall(sql);
                cs.setInt(1, listaDetalleVenta.getProductoVenta().getStock());
                cs.setInt(2, listaDetalleVenta.getProductoVenta().getIdProducto());
                cs.executeUpdate();

                System.out.println("Se ha actualizado el stock del producto");

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    
    
    public void insertarProducto(Connection con) {
        try {
            //
            String sql = " insert into `vents`.`producto` (`fproveedor`, `nombre_producto` , `precio` , `descripcion`, `stock` , `codigo`) Values (? , ?  , ? , ? , ? , ? );";
            CallableStatement cs = con.prepareCall(sql);
            int pro =  proveedorProducto.getIdProveedor();
            System.out.println(pro);

            
            cs.setInt(1, pro);
            cs.setString(2, nombreProducto.get());
            cs.setInt(3, precio.get());
            cs.setString(4, descripcion.get());
            cs.setInt(5, stock.get());
            cs.setString(6, codigo.get());
            
            
            cs.executeUpdate();
            System.out.println("Se ingresa Producto");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void eliminarProducto(Connection con){
         try {
                String sql = "DELETE FROM `vents`.`producto` WHERE (`id_producto` = ? )";
                CallableStatement cs = con.prepareCall(sql);
                cs.setInt(1, idProducto.get());
                System.out.println("Se ha actualizado el stock se ha eliminado el producto");
                   cs.executeUpdate();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    
    public void modificarProducto(Connection con){
        try {
                String sql = "update producto set fproveedor =  ?, nombre_producto = ? , precio = ? , descripcion = ?  , stock = ? , codigo = ? where `id_producto` =  ?";
                CallableStatement cs = con.prepareCall(sql);
                cs.setInt(1, proveedorProducto.getIdProveedor());
                cs.setString(2, nombreProducto.get());
                cs.setInt(3, precio.get());
                cs.setString(3, descripcion.get());
                cs.setInt(4, stock.get());
                cs.setString(5, codigo.get());
                cs.setInt(6, idProducto.get());
                
                cs.executeUpdate();

                System.out.println("Se ha actualizado el stock del producto");

            } catch (Exception e) {
                System.out.println(e);
            }
        
    
    }
        
    




}

    

