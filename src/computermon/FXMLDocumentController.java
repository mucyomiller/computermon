/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computermon;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;;


/**
 *
 * @author Sam
 */
public class FXMLDocumentController implements Initializable {
    
   
    private String Firstname;
    private String Lastname;
    private String Email;
    private String Password;
    private String Re_Password;
    
    
    @FXML
    JFXTextField firstname;
    @FXML
    JFXTextField lastname;
    @FXML
    JFXTextField email;
    @FXML
    JFXPasswordField password;
       @FXML
    JFXPasswordField repassword;
    
    @FXML
    private void HandleLogin(Event event) throws IOException
    {
        Firstname = firstname.getText();
        Lastname = lastname.getText();
        Email = email.getText();
        Password = password.getText();
        Re_Password = repassword.getText();
        if (!(Re_Password.equals(Password)))
        {
            Notifications notificationBuilder = Notifications.create()
                    .title("Login Error")
                    .text("Password Does not match")
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.CENTER);
            notificationBuilder.show();
             System.out.printf("Not Matching");
        }
        else{
        System.out.printf("Email & Password  %s %s",Email,Password);
        
        HandleRegister mRegister =  new HandleRegister(Firstname,Lastname,Email,Password);
        mRegister.register();
        }
        
    }
    
    @FXML
    private void HandleClear(Event event)
    {
        email.setText("");
        password.setText("");
        firstname.setText("");
        lastname.setText("");
        repassword.setText("");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
