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

	public Venta(int idVenta, int idUsuario, int idCliente, 
int modePago, String fechaDeVenta) { 
		this.idVenta = new SimpleIntegerProperty(idVenta);
		this.idUsuario = new SimpleIntegerProperty(idUsuario);
		this.idCliente = new SimpleIntegerProperty(idCliente);
		this.modePago = new SimpleIntegerProperty(modePago);
		this.fechaDeVenta = new SimpleStringProperty(fechaDeVenta);
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
        
        
        ////////////////////////////METODOS DE CLASE//////////////////////////////////////////////////
        
        
     /* ////////////////////////////////SENTENCIAS ///////////////////////////////////////
 * obtener listaventas "select pk_venta , fk_usuario , fk_cliente , fecha ,  cotizacion from vents.venta"; 
 *  obtener ultima venta "select pk_venta , fk_usuario , fk_cliente , fecha ,  cotizacion from venta where pk_venta = (SELECT MAX(pk_venta)from vents.venta)  "; 
 * 
     */ ///////////////////////////////////////////////////////////////////////////////////
        public static void insertarVenta(Connection con , Venta venta , int usuario , int cliente ) {
        String sql = "INSERT INTO `vents`.`venta` (`pk_venta`, `fk_usuario`, `fk_cliente`, `modo_pago`, `fecha`) VALUES (?, ? , ?  , '1', now());";
        try {
           
            CallableStatement cs = con.prepareCall(sql);
            cs.setInt(1, venta.getIdVenta());  //// MODIFICAR
            cs.setInt(2, usuario);  //// MODIFICAR
            if (cliente == 0 ) {
                cs.setNull(3, 3);
            }else cs.setInt(3, cliente);
            
    
            
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
      
      
      
 
                    
       
        
}