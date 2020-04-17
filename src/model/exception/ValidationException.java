/**
 * 
 */
package model.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kennes
 *
 */
public class ValidationException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String,String> errors = new HashMap();
	
	public ValidationException(String msg) {
		super(msg);
	}
	
	public Map<String,String> getErros(){
		return this.errors;
	}
	
	public void addErrors(String fieldName, String errorMessage) {
		this.errors.put(fieldName, errorMessage);
	}
	
}
