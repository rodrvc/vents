/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author Rodrigo
 */




public class Venta{
	private IntegerProperty idVenta;
	private IntegerProperty idUsuario;
	private IntegerProperty idCliente;
	private IntegerProperty modePago;
	private StringProperty fechaDeVenta;
                   private IntegerProperty total; 
                   private Cliente cliente; 

	public Venta(int idVenta, int idUsuario, int idCliente, 
int modePago, String fechaDeVenta , int total) { 
		this.idVenta = new SimpleIntegerProperty(idVenta);
		this.idUsuario = new SimpleIntegerProperty(idUsuario);
		this.idCliente = new SimpleIntegerProperty(idCliente);
		this.modePago = new SimpleIntegerProperty(modePago);
		this.fechaDeVenta = new SimpleStringProperty(fechaDeVenta);
                                      this.total = new SimpleIntegerProperty(total);
	}

	//Metodos atributo: idVenta
	public int getIdVenta() {
		return idVenta.get();
	}
	public void setIdVenta(int idVenta) {
		this.idVenta = new SimpleIntegerProperty(idVenta);
	}
	public IntegerProperty IdVentaProperty() {
		return idVenta;
	}
	//Metodos atributo: idUsuario
	public int getIdUsuario() {
		return idUsuario.get();
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = new SimpleIntegerProperty(idUsuario);
	}
	public IntegerProperty IdUsuarioProperty() {
		return idUsuario;
	}
	//Metodos atributo: idCliente
	public int getIdCliente() {
		return idCliente.get();
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = new SimpleIntegerProperty(idCliente);
	}
	public IntegerProperty IdClienteProperty() {
		return idCliente;
	}
	//Metodos atributo: modePago
	public int getModePago() {
		return modePago.get();
	}
	public void setModePago(int modePago) {
		this.modePago = new SimpleIntegerProperty(modePago);
	}
	public IntegerProperty ModePagoProperty() {
		return modePago;
	}
	//Metodos atributo: fechaDeVenta
	public String getFechaDeVenta() {
		return fechaDeVenta.get();
	}
	public void setFechaDeVenta(String fechaDeVenta) {
		this.fechaDeVenta = new SimpleStringProperty(fechaDeVenta);
	}
	public StringProperty FechaDeVentaProperty() {
		return fechaDeVenta;
	}
        
        public int getTotal() {
		return total.get();
	}
	public void setTotal(int total) {
		this.total = new SimpleIntegerProperty(total);
	}
	public IntegerProperty totalProperty() {
		return total;
	}
        
        
        ////////////////////////////METODOS DE CLASE//////////////////////////////////////////////////
        
        
     /* ////////////////////////////////SENTENCIAS ///////////////////////////////////////
 * obtener listaventas "select pk_venta , fk_usuario , fk_cliente , fecha ,  cotizacion from vents.venta"; 
 *  obtener ultima venta "select pk_venta , fk_usuario , fk_cliente , fecha ,  cotizacion from venta where pk_venta = (SELECT MAX(pk_venta)from vents.venta)  "; 
 * 
     */ ///////////////////////////////////////////////////////////////////////////////////
        public static void insertarVenta(Connection con , Venta venta , int usuario , int cliente , int total ) {
        String sql = "INSERT INTO `vents`.`venta` (`pk_venta`, `fk_usuario`, `fk_cliente`, `modo_pago`, `fecha`, `total`) VALUES (?, ? , ?  , '1', now(), ?);";
        try {
           
            CallableStatement cs = con.prepareCall(sql);
            cs.setInt(1, venta.getIdVenta());  //// MODIFICAR
            cs.setInt(2, usuario);  //// MODIFICAR
            if (cliente == 0 ) {
                cs.setNull(3, 3);
            }else cs.setInt(3, cliente);
           cs.setInt(4, total);
            
    
            
            System.out.println("Se ingresa correctamente");     
            cs.executeUpdate();
            

        } catch (SQLException ex) {
            Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("RROR");
        }
    }
            
            
      public static int obtenerUltimaVenta(Connection con) {
       
        int VentaNumero = 0;

        try {
             ResultSet result;
            System.out.println("se ejecuta query");
            Statement statement = con.createStatement();
            result = statement.executeQuery("select `pk_venta`  from venta where `pk_venta` = (SELECT MAX(`pk_venta`)from `venta`)  ");
            while (result.next()) {
                  VentaNumero = result.getInt("pk_venta");
                  VentaNumero = VentaNumero + 1;
            }
            System.out.println(VentaNumero);

        } catch (SQLException ex) {
            Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return VentaNumero++;
    }
      
      
          public static Producto llenarListaVenta(Connection con, ObservableList<Venta> lista) {
        int autorizado = 0;
        int Paautorizado = 0;
        Producto pro = null;
        int proveedor = 0;

        try {
            Statement statement = con.createStatement();
            ResultSet result;
            result = statement.executeQuery(" SELECT  pk_venta,  fk_usuario, fk_cliente, modo_pago , fecha , total  From  vents.venta");

            while (result.next()) {
                System.out.println("Exito en la autorizacion");
                //producto
                int idPro = result.getInt("pk_venta");
                int usuario = result.getInt("fk_usuario");
                int nombre = result.getInt("fk_cliente");
                int modoPago = result.getInt("modo_pago");
                String fecha = result.getString("fecha");
                int total = result.getInt("total");
                //proveedorProducto
               

                Venta venta = new Venta(idPro, usuario, nombre, modoPago, fecha , total);

                lista.add(venta);
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
          
          
          public static String obtenerNombreCliente(ObservableList<Cliente> listaClientes , Venta venta){
              String nombreCliente = "No especificado";
              int hola = 1;
              for (Cliente cliente : listaClientes) {
                  System.out.println(cliente.getIdCliente() + " " + venta.getIdCliente());
                  System.out.println(cliente.NombreSegundoProperty().get() + " " + venta.getIdCliente());
                  hola++;
                  if (cliente.getIdCliente() ==  venta.getIdCliente()) {
                      nombreCliente = cliente.getNombre() + " " + cliente.getApellidoP();
                      
                  }
              }
          return nombreCliente;
          }
          
           public static String obtenerNombreUsuario(ObservableList<Usuario> listaUsuario , Venta venta){
              String nombreCliente = "No especificado";
              int hola = 1;
              for (Usuario us : listaUsuario) {
                  System.out.println(us.getIdUsuario() + " " + venta.getIdUsuario());
                  
                  hola++;
                  if (us.getIdUsuario() ==  venta.getIdUsuario()) {
                      nombreCliente = us.getPrimerNom() + " " + us.getPrimerApe();
                      
                  }
              }
          return nombreCliente;
          }
          
          
          
          
      
      
      
 
                    
       
        
}