/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
import javafx.scene.control.ComboBox;

/**
 *
 * @author Rodrigo
 */
public class TipoUsuario{
	private IntegerProperty idUsuario;
	private StringProperty nombreTipo;
                   private ComboBox comboTipo;

	public TipoUsuario(int idUsuario, String nombreTipo) { 
		this.idUsuario = new SimpleIntegerProperty(idUsuario);
		this.nombreTipo = new SimpleStringProperty(nombreTipo);
                                     this.comboTipo = new ComboBox();
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
	//Metodos atributo: nombreTipo
	public String getNombreTipo() {
		return nombreTipo.get();
	}
	public void setNombreTipo(String nombreTipo) {
		this.nombreTipo = new SimpleStringProperty(nombreTipo);
	}
	public StringProperty NombreTipoProperty() {
		return nombreTipo;
	}
        
        
        
                   public static void informacion(Connection con, ObservableList<TipoUsuario> lista){
                            try {
                                    Statement statement = con.createStatement();
                                    ResultSet resultado = statement.executeQuery(" SELECT  pk_tipo , nombre_tipo FROM vents.tipo"); 
                                    
                                    while(resultado.next( )){
                                        lista.add(new TipoUsuario(resultado.getInt("pk_tipo"), resultado.getString("nombre_tipo")));
                                    }
                            } catch (SQLException ex) {
                                    Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                   }
                   
                    @Override
                    public String toString(){
                        return nombreTipo.get();
                    }  
}
