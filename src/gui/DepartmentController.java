package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmentController implements Initializable {
	private Department entity;
	
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
	
    public void setDepartment(Department entity) {
    	this.entity = entity;
    }
    
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
		initializeNodes();
		
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}
	
	public void updateFormData() {
		if(entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}
	
}
