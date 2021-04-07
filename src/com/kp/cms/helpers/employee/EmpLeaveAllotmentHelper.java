package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.admin.PayScaleBO;
import com.kp.cms.bo.employee.EmpLeaveAllotment;
import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.forms.employee.EmpLeaveAllotmentForm;
import com.kp.cms.forms.employee.PayScaleDetailsForm;
import com.kp.cms.handlers.employee.EmpLeaveAllotmentHandler;
import com.kp.cms.to.employee.EmpLeaveAllotTO;
import com.kp.cms.to.employee.PayScaleTO;

public class EmpLeaveAllotmentHelper {
	private static final Log log = LogFactory.getLog(EmpLeaveAllotmentHelper.class);
	public static volatile EmpLeaveAllotmentHelper empLeaveHelper=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static EmpLeaveAllotmentHelper getInstance(){
		if(empLeaveHelper==null){
			empLeaveHelper= new EmpLeaveAllotmentHelper();}
		return empLeaveHelper;
	}
	public List<EmpLeaveAllotTO> convertBosToTOs(List<EmpLeaveAllotment> leaveAllotment){
    	List<EmpLeaveAllotTO> leaveAllotTo=new ArrayList<EmpLeaveAllotTO>();
		Iterator itr=leaveAllotment.iterator();
		while(itr.hasNext()){
			EmpLeaveAllotment lAllotment=(EmpLeaveAllotment)itr.next();
			EmpLeaveAllotTO allotmentTO=new EmpLeaveAllotTO();
			allotmentTO.setId(lAllotment.getId());
			
			allotmentTO.setLeaveType(lAllotment.getEmpLeaveType().getName());
			allotmentTO.setEmpType(lAllotment.getEmpType().getName());
			
			allotmentTO.setAllottedLeave(lAllotment.getAllottedLeave().toString());
			allotmentTO.setInitRequired(lAllotment.getInitRequired()? "Yes":"No");
			leaveAllotTo.add(allotmentTO);
		}
		return leaveAllotTo;
	}
	public Map<Integer,String> setToMap(List<EmpType> empType){
        Map<Integer,String> typeMap=new HashMap<Integer,String>();
		Iterator itr=empType.iterator();
        while(itr.hasNext()){
        	EmpType employType=(EmpType)itr.next();
        	typeMap.put(employType.getId(), employType.getName());
        }
		return typeMap;
	}
	public EmpLeaveAllotment convertFormTOBO(EmpLeaveAllotmentForm empLeaveAllotForm){
		EmpLeaveAllotment leaveAllot=new EmpLeaveAllotment();
		EmpType empType=new EmpType();
		empType.setId(Integer.parseInt(empLeaveAllotForm.getEmpType()));
		EmpLeaveType leaveType=new EmpLeaveType();
		leaveType.setId(Integer.parseInt(empLeaveAllotForm.getLeaveType()));
		leaveAllot.setEmpType(empType);
		leaveAllot.setEmpLeaveType(leaveType);
		leaveAllot.setAllottedLeave(Integer.parseInt(empLeaveAllotForm.getAllottedLeave()));
		
		leaveAllot.setInitRequired(empLeaveAllotForm.getInitializeRequired().equalsIgnoreCase("Yes") ? true
				: false);
		if(empLeaveAllotForm.getNoOfAccumulatedLeave()!=null && !empLeaveAllotForm.getNoOfAccumulatedLeave().isEmpty()){
			leaveAllot.setNoOfAccumulatedLeave(Integer.parseInt(empLeaveAllotForm.getNoOfAccumulatedLeave()));
		}else
		{
			leaveAllot.setNoOfAccumulatedLeave(0);
		}
		leaveAllot.setCreatedBy(empLeaveAllotForm.getUserId());
		leaveAllot.setCreatedDate(new Date());
		leaveAllot.setIsActive(true);
		return leaveAllot;
	}
	public void setBotoForm(EmpLeaveAllotmentForm empLeaveAllotForm,EmpLeaveAllotment leaveAllot){
    	if(leaveAllot!=null){
    		empLeaveAllotForm.setEmpType(String.valueOf(leaveAllot.getEmpType().getId()));
    		empLeaveAllotForm.setLeaveType(String.valueOf(leaveAllot.getEmpLeaveType().getId()));
    		empLeaveAllotForm.setAllottedLeave(String.valueOf(leaveAllot.getAllottedLeave()));
    		empLeaveAllotForm.setInitializeRequired(leaveAllot.getInitRequired()? "Yes":"No");
    		if(leaveAllot.getNoOfAccumulatedLeave()!=null && leaveAllot.getNoOfAccumulatedLeave()!=0){
    			empLeaveAllotForm.setNoOfAccumulatedLeave(String.valueOf(leaveAllot.getNoOfAccumulatedLeave()));
    			empLeaveAllotForm.setOrigAccumulatedLeave(String.valueOf(leaveAllot.getNoOfAccumulatedLeave()));
    		}else{
    			empLeaveAllotForm.setNoOfAccumulatedLeave("");
    			empLeaveAllotForm.setOrigAccumulatedLeave("");
    		}
    		empLeaveAllotForm.setOrigAllottedLeave(String.valueOf(leaveAllot.getAllottedLeave()));
    		empLeaveAllotForm.setOrigEmpType(String.valueOf(leaveAllot.getEmpType().getId()));
    	empLeaveAllotForm.setOrigLeaveType(String.valueOf(leaveAllot.getEmpLeaveType().getId()));
    	empLeaveAllotForm.setOrigInitRequired(leaveAllot.getInitRequired()? "Yes":"No");
    	}
    }
	public EmpLeaveAllotment convertFormToBo(EmpLeaveAllotmentForm empLeaveAllotForm){
		EmpLeaveAllotment leaveAllotment=new EmpLeaveAllotment();
    	leaveAllotment.setId(empLeaveAllotForm.getId());
    	EmpType empType=new EmpType();
    	empType.setId(Integer.parseInt(empLeaveAllotForm.getEmpType()));
    	leaveAllotment.setEmpType(empType);
    	EmpLeaveType leave=new EmpLeaveType();
    	leave.setId(Integer.parseInt(empLeaveAllotForm.getLeaveType()));
    	leaveAllotment.setEmpLeaveType(leave);
    	leaveAllotment.setAllottedLeave(Integer.parseInt(empLeaveAllotForm.getAllottedLeave()));
    	leaveAllotment.setInitRequired(empLeaveAllotForm.getInitializeRequired().equalsIgnoreCase("Yes")?true : false);
    	if(empLeaveAllotForm.getNoOfAccumulatedLeave()!=null && !empLeaveAllotForm.getNoOfAccumulatedLeave().isEmpty()){
    		leaveAllotment.setNoOfAccumulatedLeave(Integer.parseInt(empLeaveAllotForm.getNoOfAccumulatedLeave()));
    	}else{
    		leaveAllotment.setNoOfAccumulatedLeave(0);
    	}
    	leaveAllotment.setIsActive(true);
    	leaveAllotment.setModifiedBy(empLeaveAllotForm.getUserId());
    	leaveAllotment.setLastModifiedDate(new Date());
    	return leaveAllotment;
    }
}
