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
import javafx.collections.ObservableList;

/**
 *
 * @author Rodrigo
 */


public class Proveedor{
	private IntegerProperty idProveedor;
	private StringProperty rutProveedor;
	private StringProperty emailProveedor;
	private StringProperty direccionProveedor;
	private StringProperty categoriaProveedor;
	private StringProperty nombreProveedor;

	public Proveedor(int idProveedor, String rutProveedor, String emailProveedor, 
String direccionProveedor, String categoriaProveedor, String nombreProveedor) { 
		this.idProveedor = new SimpleIntegerProperty(idProveedor);
		this.rutProveedor = new SimpleStringProperty(rutProveedor);
		this.emailProveedor = new SimpleStringProperty(emailProveedor);
		this.direccionProveedor = new SimpleStringProperty(direccionProveedor);
		this.categoriaProveedor = new SimpleStringProperty(categoriaProveedor);
		this.nombreProveedor = new SimpleStringProperty(nombreProveedor);
	}

	//Metodos atributo: idProveedor
	public int getIdProveedor() {
		return idProveedor.get();
	}
	public void setIdProveedor(int idProveedor) {
		this.idProveedor = new SimpleIntegerProperty(idProveedor);
	}
	public IntegerProperty IdProveedorProperty() {
		return idProveedor;
	}
	//Metodos atributo: rutProveedor
	public String getRutProveedor() {
		return rutProveedor.get();
	}
	public void setRutProveedor(String rutProveedor) {
		this.rutProveedor = new SimpleStringProperty(rutProveedor);
	}
	public StringProperty RutProveedorProperty() {
		return rutProveedor;
	}
	//Metodos atributo: emailProveedor
	public String getEmailProveedor() {
		return emailProveedor.get();
	}
	public void setEmailProveedor(String emailProveedor) {
		this.emailProveedor = new SimpleStringProperty(emailProveedor);
	}
	public StringProperty EmailProveedorProperty() {
		return emailProveedor;
	}
	//Metodos atributo: direccionProveedor
	public String getDireccionProveedor() {
		return direccionProveedor.get();
	}
	public void setDireccionProveedor(String direccionProveedor) {
		this.direccionProveedor = new SimpleStringProperty(direccionProveedor);
	}
	public StringProperty DireccionProveedorProperty() {
		return direccionProveedor;
	}
	//Metodos atributo: categoriaProveedor
	public String getCategoriaProveedor() {
		return categoriaProveedor.get();
	}
	public void setCategoriaProveedor(String categoriaProveedor) {
		this.categoriaProveedor = new SimpleStringProperty(categoriaProveedor);
	}
	public StringProperty CategoriaProveedorProperty() {
		return categoriaProveedor;
	}
	//Metodos atributo: nombreProveedor
	public String getNombreProveedor() {
		return nombreProveedor.get();
	}
	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = new SimpleStringProperty(nombreProveedor);
	}
	public StringProperty NombreProveedorProperty() {
		return nombreProveedor;
	}
        
                   public static void llenarInformacion(Connection con, ObservableList<Proveedor> lista){
                            try {
                                    Statement statement = con.createStatement();
                                    ResultSet resultado = statement.executeQuery(" SELECT nombre , id_proveedor , email , direccion , categoria , rut_Proveedor FROM vents.proveedor;"); 
                                    System.out.println("EXITO ");
                                    while(resultado.next( )){
                                        lista.add(new Proveedor(resultado.getInt("id_proveedor"), resultado.getString("rut_proveedor"), resultado.getString("email"), resultado.getString("direccion"), resultado.getString("categoria"), resultado.getString("nombre")));
                                    }
                            } catch (SQLException ex) {
                                    Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                   }
        
                    @Override
                    public String toString(){
                        return rutProveedor.get() + nombreProveedor.get() + emailProveedor.get() +  direccionProveedor.get();
                    }
                    
                    
                    
                    
                    
                    
}
