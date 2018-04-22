
package Controllers;

import DataBase.DatosAbonosReparacion;
import Models.Abono;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

public class ConsultaAbonosReparacionController implements Initializable {

    private final DatosAbonosReparacion datosAbonosReparacion = new DatosAbonosReparacion();
    
    @FXML
    private TextField txtBusqueda;
    @FXML
    private TableView<Abono> tblAbonosReparacion;
    @FXML
    private Button btnEliminar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tblAbonosReparacion.setCursor(Cursor.CROSSHAIR);
        CargarAbonosReparaciones("Ninguna");
        CargarColumnasAbonosReparacion();
        btnEliminar.setCursor(Cursor.HAND);
        btnEliminar.setVisible(false);
    }    

    @FXML
    private void Buscar(KeyEvent event) {
        if(txtBusqueda.getText().equals("")){
            CargarAbonosReparaciones("Ninguna");
        }else{
            CargarAbonosReparaciones(txtBusqueda.getText());
        }
    }
    
    private void CargarAbonosReparaciones(String busqueda){
        tblAbonosReparacion.getItems().clear();
        tblAbonosReparacion.setItems(datosAbonosReparacion.AbonosReparacion(busqueda));
    }
    
    private void CargarColumnasAbonosReparacion() {
        TableColumn tblCIdAbono = new TableColumn("IdAbono");
        tblCIdAbono.setCellValueFactory(new PropertyValueFactory<>("idAbono"));
        tblCIdAbono.setMinWidth(163);
        TableColumn tblCCodigoFactura = new TableColumn("Codigo Factura");
        tblCCodigoFactura.setCellValueFactory(new PropertyValueFactory<>("CodigoFactura"));
        tblCCodigoFactura.setMinWidth(163);
        TableColumn tblCMonto = new TableColumn("Monto");
        tblCMonto.setCellValueFactory(new PropertyValueFactory<>("Monto"));
        tblCMonto.setMinWidth(163);
        TableColumn tblCFecha = new TableColumn("Fecha");
        tblCFecha.setCellValueFactory(new PropertyValueFactory<>("Fecha"));
        tblCFecha.setMinWidth(163);
        TableColumn tblCIdTipoDePago = new TableColumn("Tipo Pago");
        tblCIdTipoDePago.setCellValueFactory(new PropertyValueFactory<>("idTipoDePago"));
        tblCIdTipoDePago.setMinWidth(163);
        tblAbonosReparacion.getColumns().addAll(tblCIdAbono, tblCCodigoFactura, tblCMonto, tblCFecha, tblCIdTipoDePago);
    }

    @FXML
    private void Seleccionar(MouseEvent event) {
        Abono abono = tblAbonosReparacion.getSelectionModel().getSelectedItem();
        if(abono != null){
            btnEliminar.setVisible(true);
        }else{
            btnEliminar.setVisible(false);
        }
    }

    @FXML
    private void Eliminar(ActionEvent event) {
        Abono abono = tblAbonosReparacion.getSelectionModel().getSelectedItem();
        if(abono != null){
            Object[] options = { "Eliminar", "Cancelar" };
            int choice = JOptionPane.showOptionDialog(null,
                "Desea eliminar este Abono\nEsta accion NO podra revertirse", 
                "Eliminar Abono Confirmacion", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                options, 
                options[0]);
            if (choice == JOptionPane.YES_OPTION){
                if(datosAbonosReparacion.EliminarAbono(abono)){
                    btnEliminar.setVisible(false);
                    if(txtBusqueda.getText().equals("")){
                        CargarAbonosReparaciones("Ninguna");
                    }else{
                        CargarAbonosReparaciones(txtBusqueda.getText());
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Error al Eliminar el abono " + abono.getIdAbono());
                }
            } 
        }
    }
}