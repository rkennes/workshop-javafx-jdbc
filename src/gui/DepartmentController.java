package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartmentController implements Initializable {
    @FXML
	private TextField txtId;
    
    @FXML
    private TextField txtName;
    
    @FXML
    private Label labelError; 
    
    @FXML
    private Button btSave;
    
    @FXML
    private Button btCancel;
	
    @FXML
    public void onBtSaveAction() {
    	System.out.println("SAve");
    }
    
    @FXML
    public void onBtCancelAction() {
    	System.out.println("Cancel");
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}
}
