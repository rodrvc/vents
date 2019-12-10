/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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




public class Cotizacion{
	private IntegerProperty idCotizacion;
	private IntegerProperty fkCliente;
	private IntegerProperty fkUsuario;
	private StringProperty fkDate;
	private IntegerProperty fkPrecio;

	public Cotizacion(int idCotizacion, int fkCliente, int fkUsuario, 
String fkDate, int fkPrecio) { 
		this.idCotizacion = new SimpleIntegerProperty(idCotizacion);
		this.fkCliente = new SimpleIntegerProperty(fkCliente);
		this.fkUsuario = new SimpleIntegerProperty(fkUsuario);
		this.fkDate = new SimpleStringProperty(fkDate);
		this.fkPrecio = new SimpleIntegerProperty(fkPrecio);
	}

	//Metodos atributo: idCotizacion
	public int getIdCotizacion() {
		return idCotizacion.get();
	}
	public void setIdCotizacion(int idCotizacion) {
		this.idCotizacion = new SimpleIntegerProperty(idCotizacion);
	}
	public IntegerProperty IdCotizacionProperty() {
		return idCotizacion;
	}
	//Metodos atributo: fkCliente
	public int getFkCliente() {
		return fkCliente.get();
	}
	public void setFkCliente(int fkCliente) {
		this.fkCliente = new SimpleIntegerProperty(fkCliente);
	}
	public IntegerProperty FkClienteProperty() {
		return fkCliente;
	}
	//Metodos atributo: fkUsuario
	public int getFkUsuario() {
		return fkUsuario.get();
	}
	public void setFkUsuario(int fkUsuario) {
		this.fkUsuario = new SimpleIntegerProperty(fkUsuario);
	}
	public IntegerProperty FkUsuarioProperty() {
		return fkUsuario;
	}
	//Metodos atributo: fkDate
	public String getFkDate() {
		return fkDate.get();
	}
	public void setFkDate(String fkDate) {
		this.fkDate = new SimpleStringProperty(fkDate);
	}
	public StringProperty FkDateProperty() {
		return fkDate;
	}
	//Metodos atributo: fkPrecio
	public int getFkPrecio() {
		return fkPrecio.get();
	}
	public void setFkPrecio(int fkPrecio) {
		this.fkPrecio = new SimpleIntegerProperty(fkPrecio);
	}
	public IntegerProperty FkPrecioProperty() {
		return fkPrecio;
	}
        
          public static int obtenerUltimaCotizacion(Connection con) {
       
        int cotizacionNumero = 0;

        try {
             ResultSet result;
            Statement statement = con.createStatement();
            result = statement.executeQuery("select `id_cotizacion`  from cotizacion where `id_cotizacion` = (SELECT MAX(`id_cotizacion`)from `venta`)  ");
            while (result.next()) {
                  cotizacionNumero = result.getInt("id_cotizacion");
                  cotizacionNumero = cotizacionNumero + 1;
            }
            System.out.println(cotizacionNumero);

        } catch (SQLException ex) {
            Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cotizacionNumero++;
    }
          
          
          
          
                public static void insertarCotizacion(Connection con , Cotizacion cotizacion , int cliente , int usuario , int precio ) {
        String sql = "INSERT INTO `vents`.`cotizacion` (`id_cotizacion`, `fk_cliente`, `fk_usuario`, `fecha`, `descuento`) VALUES ( 0 , ? , ?  , now(), ?);";
        try {
           
            CallableStatement cs = con.prepareCall(sql);
           // cs.setInt(1, cotizacion.getIdCotizacion()+1);  //// MODIFICAR
            cs.setInt(1, cliente);  //// MODIFICAR
            cs.setInt(2, usuario);
            cs.setInt(3, precio);
        
            
    
            
            System.out.println("Se ingresa cotizacion");     
            cs.executeUpdate();
            

        } catch (SQLException ex) {
            Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("RROR");
        }
    }
        
        
        
        
        
}
    
    
    
    
    
  
    
    
    

