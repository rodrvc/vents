/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import javafx.scene.control.Alert;

/**
 *
 * @author Rodrigo
 */
public class AlertasDelSoftware {
    public static void avisoDeIIngresoCliente(){
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Aviso de ingreso");
        alert.setContentText("Se ha realizado el ingreso de un nuevo usuario a la base de datos de clientes. "
                + "El cliente aparecer dentro del tabla cliente en caso de necesitar informacion"
                + "Los clientes incluidos cuentas con mejores promociones y descuentos segun en monto de compras que se realicen ");
        
         alert.setTitle("INGRESO DE CLIENTE");
         alert.show();
    }
    
    public static void avisoEditarTelefono(){
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Aviso de la Edicion");
        alert.setContentText("Debes ingresar un numero telefonico valido");
        
         alert.setTitle("EDICION DE TELEFONO");
         alert.show();
         
    }
    
    
    
}
