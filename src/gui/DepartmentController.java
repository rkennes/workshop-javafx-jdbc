package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.exception.ValidationException;
import model.services.DepartmentServices;

public class DepartmentController implements Initializable {
	private Department entity;
	
	private DepartmentServices service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList(); 
	
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
    
    public void setDepartmentServices(DepartmentServices service) {
    	this.service = service;
    }
    
    public void subscribeDataChangeListener(DataChangeListener listener) {
    	this.dataChangeListeners.add(listener);
    }
    
    private void notifyDataChangeListeners() {
    	for(DataChangeListener listener : dataChangeListeners) {
    		listener.onDataChanged();
    	}
    }
    
    @FXML
    public void onBtSaveAction(ActionEvent event) {
    	if(entity == null) {
    		throw new IllegalStateException("Entity was null");
    	}
    	
    	if(service == null) {
    		throw new IllegalStateException("Service was null");
    	}
        
    	try {
    		this.entity = getFormData();
    		this.service.saveOrUpdate(entity);
    		notifyDataChangeListeners();
    		Utils.currentStage(event).close();
    	}catch(DbException e) {
    		Alerts.showAlert("Error Savings Object", null, e.getMessage(), AlertType.ERROR);
    	}catch(ValidationException e) {
    		setErrorMessages(e.getErros());
    	}
    }
    
    private Department getFormData() {
		Department dep = new Department();
		
		ValidationException exceptions = new ValidationException("Validation Exception");
		
		dep.setId(Utils.tryParseToInt(this.txtId.getText()));
		
		if(this.txtName.getText() == null || this.txtName.getText().trim().equals("") ) {
			exceptions.addErrors("name", "Field can't be empty");
		}
		dep.setName(this.txtName.getText());
		
		if(exceptions.getErros().size() > 0) {
			throw exceptions;
		}
		
		return dep;
	}

	@FXML
    public void onBtCancelAction(ActionEvent event) {
   		Utils.currentStage(event).close();
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
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet(); 
		
		if(fields.contains("name")) {
			this.labelError.setText(errors.get("name"));
		}
		
	}
	
}
