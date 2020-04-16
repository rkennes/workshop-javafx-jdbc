package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Department;

public class DepartmentServices {
	public List<Department> findAll(){
		List<Department> lstDep = new ArrayList();
		lstDep.add(new Department(1,"Shoes")); 
		lstDep.add(new Department(2,"Clothes"));
		lstDep.add(new Department(3,"Wardrobes"));
		
		return lstDep;
	}
}
