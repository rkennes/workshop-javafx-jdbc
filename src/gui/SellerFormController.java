package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Department;
import model.entities.Seller;
import model.exception.ValidationException;
import model.services.DepartmentServices;
import model.services.SellerServices;

public class SellerFormController implements Initializable {
	private Seller entity;
	
	private SellerServices service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList(); 
	
	private DepartmentServices departmentService;
	
    @FXML
	private TextField txtId;
    
    @FXML
    private TextField txtName;
    
    @FXML
    private TextField txtEmail;
    
    @FXML
    private DatePicker dpBirthDate;
    
    @FXML
    private TextField txtBaseSalary;
    
    @FXML
    private Label labelError;
    
    @FXML
    private Label labelErrorEmail;
    
    @FXML
    private Label labelErrorBirthDate;
    
    @FXML
    private Label labelErrorBaseSalary;
    
    @FXML
    private Button btSave;
    
    @FXML
    private Button btCancel;
    
    @FXML
    private ComboBox<Department> comboBoxDepartment;
    
    private ObservableList<Department> obsList;
	
    public void setSeller(Seller entity) {
    	this.entity = entity;
    }
    
    public void setServices(SellerServices service, DepartmentServices departmentService) {
    	this.service = service;
    	this.departmentService = departmentService;
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
    
    private Seller getFormData() {
		Seller dep = new Seller();
		
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
		Constraints.setTextFieldMaxLength(txtName, 60);
		Constraints.setTextFieldDouble(txtBaseSalary);
		Constraints.setTextFieldMaxLength(txtEmail, 60);
		Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
		initializeComboBoxDepartment();
	}
	
	public void updateFormData() {
		if(entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		
		txtId.setText(String.valueOf(entity.getId()));
		
		txtName.setText(entity.getName());
		
		txtEmail.setText(entity.getEmail());
		
		Locale.setDefault(Locale.US);
		txtBaseSalary.setText(String.format("%.2f",entity.getBaseSalary()));
		
		//dpBirthDate.setValue(entity.getBirthDate().toInstant());
		
		if(entity.getDepartment() == null) {
			comboBoxDepartment.getSelectionModel().selectFirst();
		}else{
			comboBoxDepartment.setValue(entity.getDepartment());
		}
		
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet(); 
		
		if(fields.contains("name")) {
			this.labelError.setText(errors.get("name"));
		}
		
	}
	
	public void loadAssociatedObjects() {
		List<Department> list = departmentService.findAll();
		obsList = FXCollections.observableArrayList(list);
		comboBoxDepartment.setItems(obsList);
	}
	
	private void initializeComboBoxDepartment() {     
		Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() { 
			
			@Override 
			protected void updateItem(Department item, boolean empty) { 
				super.updateItem(item, empty);         
				setText(empty ? "" : item.getName());    
			}     
		}; 
		
		comboBoxDepartment.setCellFactory(factory); 
		comboBoxDepartment.setButtonCell(factory.call(null));        
	}
	
}
