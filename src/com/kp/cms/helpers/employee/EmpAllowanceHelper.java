package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.EmpAllowance;
import com.kp.cms.bo.admin.PayScaleBO;
import com.kp.cms.forms.employee.EmpAllowanceForm;
import com.kp.cms.forms.employee.PayScaleDetailsForm;
import com.kp.cms.to.admin.EmpAllowanceTO;

public class EmpAllowanceHelper {
	private static volatile EmpAllowanceHelper instance=null;
    private EmpAllowanceHelper(){
		
	}
    public static EmpAllowanceHelper getInstance(){
	   if(instance==null){
		  instance=new EmpAllowanceHelper();
	   }
	   return instance;
    }
    public List<EmpAllowanceTO> convertBOtoTO(List<EmpAllowance> allowance){
    	List<EmpAllowanceTO> allowanceTO=new ArrayList<EmpAllowanceTO>();
    	Iterator<EmpAllowance> itr=allowance.iterator();
    	while(itr.hasNext()){
    		EmpAllowance alowance=itr.next();
    		EmpAllowanceTO alowanceTO=new EmpAllowanceTO();
    		if(alowance.getName()!=null)
    			alowanceTO.setAllowanceName(alowance.getName());
    		if(alowance.getDisplayOrder()!=null)
    			alowanceTO.setDisplayOrder(alowance.getDisplayOrder());
    		alowanceTO.setId(alowance.getId());
    		allowanceTO.add(alowanceTO);
    	}
    	return allowanceTO;
    }
    public EmpAllowance convertFormTOBO(EmpAllowanceForm empAllowanceForm,String mode){
		EmpAllowance allowance=new EmpAllowance();
		if(mode.equalsIgnoreCase("add")){
			allowance.setCreatedBy(empAllowanceForm.getUserId());
			allowance.setCreatedDate(new Date());
		}else
			allowance.setId(empAllowanceForm.getId());
		if(empAllowanceForm.getAllowanceType()!=null)
			allowance.setName(empAllowanceForm.getAllowanceType());
		if(empAllowanceForm.getDisplayOrder()!=null && !empAllowanceForm.getDisplayOrder().isEmpty())
			allowance.setDisplayOrder(Integer.parseInt(empAllowanceForm.getDisplayOrder()));
		allowance.setIsActive(true);
		allowance.setModifiedBy(empAllowanceForm.getUserId());
		allowance.setLastModifiedDate(new Date());
		return allowance;
	}
    public void setBotoForm(EmpAllowanceForm empAllowanceForm,EmpAllowance allowance){
    	if(allowance!=null){
    		empAllowanceForm.setAllowanceType(allowance.getName());
    		if(allowance.getDisplayOrder()!=null)
    		empAllowanceForm.setDisplayOrder(String.valueOf(allowance.getDisplayOrder()));
    		empAllowanceForm.setOrigAllowance(allowance.getName());
    		empAllowanceForm.setOrigDisplayOrder(String.valueOf(allowance.getDisplayOrder()));
    	}
    }
    
}
