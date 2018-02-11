
package Controllers;

import Models.DatosDocumentacion;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javax.swing.JOptionPane;

public class DocumentacionTodoController implements Initializable {

    DatosDocumentacion datosDocumentacion = new DatosDocumentacion();
    
    @FXML
    private Button btnGenerarDocumentacion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void MouseMano(MouseEvent event) {
        btnGenerarDocumentacion.setCursor(Cursor.HAND);
    }

    @FXML
    private void GenerarDocumentacion(ActionEvent event) {
        btnGenerarDocumentacion.setDisable(true);
        DirectoryChooser fileChooser = new DirectoryChooser();
        File selectedFile = fileChooser.showDialog(null);
        if (selectedFile != null) {
           if(datosDocumentacion.TodoDocumentacion(selectedFile.getPath()+"/MundoDelCalzado2.xls")){
               JOptionPane.showMessageDialog(null, "Documentacion generado correctamente");
           }else{
               JOptionPane.showMessageDialog(null, "Error al generar Documentacion");
           }
        }
        btnGenerarDocumentacion.setDisable(false);
    }
    
}