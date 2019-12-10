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
public class Cliente{

    
	private IntegerProperty idCliente;
	private StringProperty nombre;
	private StringProperty nombreSegundo;
	private StringProperty apellidoP;
	private StringProperty apellidoM;
	private StringProperty direccion;
	private StringProperty correo;
	private StringProperty fecha_nac;
	private StringProperty rut_cliente;
	private StringProperty telefono;

	public Cliente(int idCliente, String nombre, String nombreSegundo, 
String apellidoP, String apellidoM, String direccion, 
String correo, String fecha_nac, String rut_cliente, 
String telefono) { 
		this.idCliente = new SimpleIntegerProperty(idCliente);
		this.nombre = new SimpleStringProperty(nombre);
		this.nombreSegundo = new SimpleStringProperty(nombreSegundo);
		this.apellidoP = new SimpleStringProperty(apellidoP);
		this.apellidoM = new SimpleStringProperty(apellidoM);
		this.direccion = new SimpleStringProperty(direccion);
		this.correo = new SimpleStringProperty(correo);
		this.fecha_nac = new SimpleStringProperty(fecha_nac);
		this.rut_cliente = new SimpleStringProperty(rut_cliente);
		this.telefono = new SimpleStringProperty(telefono);
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
	//Metodos atributo: nombre
	public String getNombre() {
		return nombre.get();
	}
	public void setNombre(String nombre) {
		this.nombre = new SimpleStringProperty(nombre);
	}
	public StringProperty NombreProperty() {
		return nombre;
	}
	//Metodos atributo: nombreSegundo
	public String getNombreSegundo() {
		return nombreSegundo.get();
	}
	public void setNombreSegundo(String nombreSegundo) {
		this.nombreSegundo = new SimpleStringProperty(nombreSegundo);
	}
	public StringProperty NombreSegundoProperty() {
		return nombreSegundo;
	}
	//Metodos atributo: apellidoP
	public String getApellidoP() {
		return apellidoP.get();
	}
	public void setApellidoP(String apellidoP) {
		this.apellidoP = new SimpleStringProperty(apellidoP);
	}
	public StringProperty ApellidoPProperty() {
		return apellidoP;
	}
	//Metodos atributo: apellidoM
	public String getApellidoM() {
		return apellidoM.get();
	}
	public void setApellidoM(String apellidoM) {
		this.apellidoM = new SimpleStringProperty(apellidoM);
	}
	public StringProperty ApellidoMProperty() {
		return apellidoM;
	}
	//Metodos atributo: direccion
	public String getDireccion() {
		return direccion.get();
	}
	public void setDireccion(String direccion) {
		this.direccion = new SimpleStringProperty(direccion);
	}
	public StringProperty DireccionProperty() {
		return direccion;
	}
	//Metodos atributo: correo
	public String getCorreo() {
		return correo.get();
	}
	public void setCorreo(String correo) {
		this.correo = new SimpleStringProperty(correo);
	}
	public StringProperty CorreoProperty() {
		return correo;
	}
	//Metodos atributo: fecha_nac
	public String getFecha_nac() {
		return fecha_nac.get();
	}
	public void setFecha_nac(String fecha_nac) {
		this.fecha_nac = new SimpleStringProperty(fecha_nac);
	}
	public StringProperty Fecha_nacProperty() {
		return fecha_nac;
	}
	//Metodos atributo: rut_cliente
	public String getRut_cliente() {
		return rut_cliente.get();
	}
	public void setRut_cliente(String rut_cliente) {
		this.rut_cliente = new SimpleStringProperty(rut_cliente);
	}
	public StringProperty Rut_clienteProperty() {
		return rut_cliente;
	}
	//Metodos atributo: telefono
	public String getTelefono() {
		return telefono.get();
	}
	public void setTelefono(String telefono) {
		this.telefono = new SimpleStringProperty(telefono);
	}
	public StringProperty TelefonoProperty() {
		return telefono;
	}
        
        
        public static Cliente llenarListaClientes(Connection con, ObservableList<Cliente> lista) {
        
        Cliente cliente = null;
        try {
            Statement statement = con.createStatement();
            ResultSet result;
            result = statement.executeQuery(" select id_cliente , nombre , nombreseg , apellido_p , apellido_m , direccion , correo , fecha_nac , rut_cliente , telefono from cliente;");
            System.out.println("Exito en la autorizacion");
            while(result.next()){
                    int idCliente = result.getInt("id_cliente");
                    String nombre = result.getString("nombre");
                    String nombreSegundo = result.getString("nombreseg");
                    String apellidoPaterno = result.getString("apellido_p");
                    String apellidoMaterno = result.getString("apellido_m");
                    String direccion = result.getString("direccion");
                    String correo = result.getString("correo");
                    String fechadenacimiento = result.getString("fecha_nac");
                    String rut = result.getString("rut_cliente");
                    String telefono = result.getString("telefono");
              
                   
                  
                   cliente = new Cliente(idCliente, nombre, nombreSegundo, apellidoPaterno, apellidoMaterno, direccion, correo, fechadenacimiento, rut, telefono);
                   lista.add(cliente);
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
        return cliente;
    }
        
        
        
        public  void agregarCliente(Connection con){
            
            
             try {
                 //INSERT INTO `vents`.`cliente` (`id_cliente`, `nombre`, `nombreseg`, `apellido_p`, `apellido_m`, `direccion`, `correo`, `fecha_nac`, `rut_cliente`, `telefono`) VALUES ('12', 'david', 'fernando', 'dorado', 'arias', 'av. lillo', 'ddorado@inversionesgold.cl', '1992-07-22', '19999999-9', '99898778');x`
            String sql = "INSERT INTO `vents`.`cliente` (`id_cliente`, `nombre`, `nombreseg`, `apellido_p`, `apellido_m`, `direccion`, `correo`, `fecha_nac`, `rut_cliente`, `telefono`) VALUES ( 0 , ?, ? , ? , ? , ? , ?, ? , ? , ?);`";
            CallableStatement cs = con.prepareCall(sql);
            
            cs.setInt(1, 0) ;
             cs.setString(2, nombre.get());
             cs.setString(2, nombreSegundo.get());
             cs.setString(3, apellidoP.get());
             cs.setString(4, apellidoM.get());
             cs.setString(5, direccion.get());
             cs.setString(6, correo.get());
             cs.setString(7, fecha_nac.get());
             cs.setString(8, rut_cliente.get());
             cs.setString(9, telefono.get());
             cs.executeUpdate(); 
             System.out.println("Operacion Exitosa");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
            
        }
        
                    
        
        
        
        
        

