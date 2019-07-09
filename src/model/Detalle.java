/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;





public class Detalle{
	private IntegerProperty idDetalle;
	private Venta ventaDetalle;
	private Producto productoVenta;
	private IntegerProperty cantidad;

	public Detalle(int idDetalle, Venta ventaDetalle, Producto productoVenta, 
int cantidad) { 
		this.idDetalle = new SimpleIntegerProperty(idDetalle);
		this.ventaDetalle = ventaDetalle;
		this.productoVenta = productoVenta;
		this.cantidad = new SimpleIntegerProperty(cantidad);
	}

	//Metodos atributo: idDetalle
	public int getIdDetalle() {
		return idDetalle.get();
	}
	public void setIdDetalle(int idDetalle) {
		this.idDetalle = new SimpleIntegerProperty(idDetalle);
	}
	public IntegerProperty IdDetalleProperty() {
		return idDetalle;
	}
	//Metodos atributo: ventaDetalle
	public Venta getVentaDetalle() {
		return ventaDetalle;
	}
	public void setVentaDetalle(Venta ventaDetalle) {
		this.ventaDetalle = ventaDetalle;
	}
	//Metodos atributo: productoVenta
	public Producto getProductoVenta() {
		return productoVenta;
	}
	public void setProductoVenta(Producto productoVenta) {
		this.productoVenta = productoVenta;
	}
	//Metodos atributo: cantidad
	public int getCantidad() {
		return cantidad.get();
	}
	public void setCantidad(int cantidad) {
		this.cantidad = new SimpleIntegerProperty(cantidad);
	}
	public IntegerProperty CantidadProperty() {
		return cantidad;
	}
        
        
        
     public static void insertarDetalle(Connection con, ObservableList<Detalle> listaDetalleVentas) {
        
            
            for (Detalle listaDetalleVenta : listaDetalleVentas) {
                String sql = "INSERT INTO `vents`.`detalle` (`pk_detalle`, `fk_venta`, `fk_producto`, cantidad ) VALUES (?, ?, ?, ?);";
                try {
                    CallableStatement cs = con.prepareCall(sql);
                    cs.setInt(1, listaDetalleVenta.getIdDetalle());  //// MODIFICAR
                    cs.setInt(2, listaDetalleVenta.getVentaDetalle().getIdVenta());  //// MODIFICAR
                    cs.setInt(3, listaDetalleVenta.getProductoVenta().getIdProducto());  //// MODIFICAR
                    cs.setInt(4, listaDetalleVenta.getCantidad());
                    System.out.println("Se ingresa correctamente");                    
                    cs.executeUpdate();
                    
                } catch (SQLException ex) {
                    Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("RROR");
                }
                
            }
         
    }
                   
            
                   
        
        
        
        
        
        
        
}


	

