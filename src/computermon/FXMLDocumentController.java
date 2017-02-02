/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computermon;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Sam
 */
public class FXMLDocumentController implements Initializable {
    
   
    private String Email;
    private String Password;
    @FXML
    JFXTextField email;
    
    @FXML
    JFXPasswordField password;
    
    @FXML
    private void HandleLogin(Event event)
    {
        Email = email.getText();
        Password = password.getText();
        
        System.out.printf("Email & Password  %s %s",Email,Password);
        
    }
    
    @FXML
    private void HandleClear(Event event)
    {
        email.setText("");
        password.setText("");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
