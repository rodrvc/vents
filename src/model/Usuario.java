/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.mysql.jdbc.PreparedStatement;
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
public class Usuario {
   

    private IntegerProperty idUsuario;
    private TipoUsuario tipoUsuario;
    private StringProperty primerNom;
    public StringProperty rut;
    private StringProperty primerApe;
    private StringProperty segunApe;
    private StringProperty direccion;
    private StringProperty correo;
    private StringProperty nombreUsuario;
    private StringProperty contraseniaUsuario;
    public static  Usuario usuarioSession;

    public Usuario() {
        
    }

    public Usuario(int idUsuario, TipoUsuario tipoUsuario, String primerNom,
            String rut, String primerApe, String segunApe,
            String direccion, String correo, String nombreUsuario,
            String contraseniaUsuario) {
        this.idUsuario = new SimpleIntegerProperty(idUsuario);
        this.tipoUsuario = tipoUsuario;
        this.primerNom = new SimpleStringProperty(primerNom);
        this.rut = new SimpleStringProperty(rut);
        this.primerApe = new SimpleStringProperty(primerApe);
        this.segunApe = new SimpleStringProperty(segunApe);
        this.direccion = new SimpleStringProperty(direccion);
        this.correo = new SimpleStringProperty(correo);
        this.nombreUsuario = new SimpleStringProperty(nombreUsuario);
        this.contraseniaUsuario = new SimpleStringProperty(contraseniaUsuario);
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
    //Metodos atributo: tipoUsuario

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    //Metodos atributo: primerNom

    public String getPrimerNom() {
        return primerNom.get();
    }

    public void setPrimerNom(String primerNom) {
        this.primerNom = new SimpleStringProperty(primerNom);
    }

    public StringProperty PrimerNomProperty() {
        return primerNom;
    }
    //Metodos atributo: segunNombre

    public String getRut() {
        return rut.get();
    }

    public void setRut(String rut) {
        this.rut = new SimpleStringProperty(rut);
    }

    public StringProperty rutProperty() {
        return rut;
    }
    //Metodos atributo: primerApe

    public String getPrimerApe() {
        return primerApe.get();
    }

    public void setPrimerApe(String primerApe) {
        this.primerApe = new SimpleStringProperty(primerApe);
    }

    public StringProperty PrimerApeProperty() {
        return primerApe;
    }
    //Metodos atributo: segunApe

    public String getSegunApe() {
        return segunApe.get();
    }

    public void setSegunApe(String segunApe) {
        this.segunApe = new SimpleStringProperty(segunApe);
    }

    public StringProperty SegunApeProperty() {
        return segunApe;
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
    //Metodos atributo: nombreUsuario

    public String getNombreUsuario() {
        return nombreUsuario.get();
    }

    public void setNombreUsuario(String nombreUsuario) {
        
        this.nombreUsuario = new SimpleStringProperty(nombreUsuario);
        System.out.println(this.nombreUsuario.toString());
    }

    public StringProperty NombreUsuarioProperty() {
        return nombreUsuario;
    }
    //Metodos atributo: contraseniaUsuario

    public String getContraseniaUsuario() {
        return contraseniaUsuario.get();
    }

    public void setContraseniaUsuario(String contraseniaUsuario) {
        this.contraseniaUsuario = new SimpleStringProperty(contraseniaUsuario);
    }

    public StringProperty ContraseniaUsuarioProperty() {
        return contraseniaUsuario;
    }
    
    public static TipoUsuario u(TipoUsuario p){
        return p;
    }

    public static Usuario comprobarUsuario(Connection con, String nombreUsuario, String Contrasenia) {
        int autorizado = 0;
        int Paautorizado = 0;
        Usuario user = null;
        try {
            Statement statement = con.createStatement();
            ResultSet result;
            result = statement.executeQuery(" SELECT nombre_usuario, contrasenia_usuario, pk_usuario , fk_tipo, nombre , rut, apellido_p, apellido_m, direccion, correo , pk_tipo , nombre_tipo From vents.usuario, vents.tipo where fk_tipo = pk_tipo");
            System.out.println("Exito en la autorizacion");
            while(result.next()){
                String nom = result.getString("nombre_usuario" );
                String contra = result.getString("contrasenia_usuario" );
               
                if (nom.equals(nombreUsuario) && contra.equals(Contrasenia)) {
                   Paautorizado = result.getInt("fk_tipo");
                   autorizado= Paautorizado;
                   int id = result.getInt("pk_usuario");
                   String nombre = result.getString("nombre");
                   String rut  = result.getString("rut");
                   String apellido  = result.getString("apellido_p");
                   String apellido2  = result.getString("apellido_m");
                   String direccion  = result.getString("direccion");
                   String correo  = result.getString("correo");
                   int IDCargo = result.getInt("pk_tipo");
                    String nombreTipo = result.getString("nombre_tipo");
                   
                  
                   user = new Usuario(id, new TipoUsuario(IDCargo, nombreTipo), nombre, rut, apellido, apellido2, direccion, correo,  nombreUsuario, contra);
                }
            }
        } catch (SQLException ex) {
            System.out.println("ERROR");
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
    
    
    public static Usuario llenarListaUsuarios(Connection con, ObservableList<Usuario> lista) {
        int autorizado = 0;
        int Paautorizado = 0;
        Usuario user = null;
        try {
            Statement statement = con.createStatement();
            ResultSet result;
            result = statement.executeQuery(" SELECT nombre_usuario, contrasenia_usuario, pk_usuario , fk_tipo, nombre , rut, apellido_p, apellido_m, direccion, correo , nombre_usuario, pk_tipo , nombre_tipo From vents.usuario, vents.tipo where fk_tipo = pk_tipo");
            System.out.println("Exito en la autorizacion");
            while(result.next()){
                String nom = result.getString("nombre_usuario" );
                String contra = result.getString("contrasenia_usuario" );
                
                   Paautorizado = result.getInt("fk_tipo");
                   autorizado= Paautorizado;
                   int id = result.getInt("pk_usuario");
                   String nombre = result.getString("nombre");
                   String rut  = result.getString("rut");
                   String apellido  = result.getString("apellido_p");
                   String apellido2  = result.getString("apellido_m");
                   String direccion  = result.getString("direccion");
                   String correo  = result.getString("correo");
              
                   int IDCargo = result.getInt("pk_tipo");
                    String nombreTipo = result.getString("nombre_tipo");
                   
                  
                   user = new Usuario(id, new TipoUsuario(IDCargo, nombreTipo), nombre, rut, apellido, apellido2, direccion, correo,  nom, contra);
                   lista.add(user);
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
        return user;
    }
    
    
    public void insertarUsuario(Connection con){
        try {
            String sql = "insert into usuario values(0,  ?, ?, ? , ? , ? , ? , ? , ? , ? )";
            CallableStatement cs = con.prepareCall(sql);
            
            cs.setInt(1, tipoUsuario.getIdUsuario());
             cs.setString(2, primerNom.get());
             cs.setString(3, rut.get());
             cs.setString(4, primerApe.get());
             cs.setString(5, segunApe.get());
             cs.setString(6, direccion.get());
             cs.setString(7, correo.get());
             cs.setString(8, nombreUsuario.get());
             cs.setString(9, contraseniaUsuario.get());
             cs.executeUpdate(); 
             System.out.println("Operacion Exitosa");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void editarNombre(String nombre, Connection con){
        int id = idUsuario.get();
         
        
        try {
            String sql  = "update usuario set `nombre` =  ?  where `pk_usuario` =  ? " ;
            CallableStatement cs = con.prepareCall(sql);
            cs.setString(1, nombre);
            cs.setInt(2, id);
            cs.executeUpdate();
            
            System.out.println("Exitoso" + nombre + "fue cambiado por " + primerNom.get());
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
        
        
        public void editarApellido(String apellido, Connection con){
        int id = idUsuario.get();
        
        try {
            String sql  = "update usuario set apellido_p =  ?  where `pk_usuario` =  ? " ;
            CallableStatement cs = con.prepareCall(sql);
            cs.setString(1, apellido);
            cs.setInt(2, id);
            cs.executeUpdate();
            
            System.out.println("Exitoso" + primerApe.get() + "fue cambiado por " + apellido);
            
        } catch (Exception e) {
            System.out.println(e);
        }

        
    }
        
        
         public static void editarTipo(String tipo, int idUsuario, Connection con){
             
            int puesto = 0;
        
             switch (tipo) {
                 case "cajero":
                     puesto = 1;
                     break;
                  case "vendedor":
                     puesto = 2;
                     break;
                   case "administrador":
                     puesto = 3;
                     break;
             }
        
        
        try {
            String sql  =  "update `vents`.`usuario` SET `fk_tipo` = ?  where (`pk_usuario` = ?)";
            CallableStatement cs = con.prepareCall(sql);
            cs.setInt(1, puesto);
            cs.setInt(2, idUsuario);
            cs.executeUpdate();
            
            System.out.println("Exitoso cambio de tipo " + tipo );
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
        
        //////////////////////////////VARIABLES DE SESION/////////////////////////////////
        
        public static void setUsuarioEnSession(Usuario user){
            usuarioSession = user;
        }
        public static Usuario obtenerUsuarioEnSession(){
            return usuarioSession;
        }
        public static void borrarSession(){
            usuarioSession = null ; 
        }
    
    
   
    
        
    
   

}
