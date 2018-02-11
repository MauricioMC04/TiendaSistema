
package Main;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class MundoDelCalzado2 extends Application {
    
    public void mostrarVentanaPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(MundoDelCalzado2.class.getResource("/Views/Principal.fxml"));
            AnchorPane rootPane = loader.load();
            Scene scene = new Scene(rootPane);
            Stage stagePrincipal = new Stage();
            stagePrincipal.setTitle("Mundo Del Calzado II");
            stagePrincipal.setScene(scene);
            stagePrincipal.resizableProperty().setValue(Boolean.FALSE);
            stagePrincipal.show();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
    }
    
    @Override
    public void start(Stage primaryStage) {
        mostrarVentanaPrincipal();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
