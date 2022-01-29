package com.kp.cms.handlers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.forms.employee.EmpLeaveTypeForm;
import com.kp.cms.helpers.employee.EmpLeaveTypeHelper;
import com.kp.cms.to.employee.EmpLeaveTypeTo;
import com.kp.cms.transactions.employee.IEmpLeaveType;
import com.kp.cms.transactionsimpl.employee.EmpLeaveTypeTransaction;

public class EmpLeaveTypeHandler {
	private static volatile EmpLeaveTypeHandler instance=null;
	IEmpLeaveType leaveTrans=EmpLeaveTypeTransaction.getInsatance();
	
	
	private EmpLeaveTypeHandler() {
	}

	/**
	 * @return
	 */
	public static EmpLeaveTypeHandler getInstance(){
		if(instance==null){
			instance=new EmpLeaveTypeHandler();
		}
		return instance;
	}

	/**
	 * @param empLeaveTypeForm
	 * @throws Exception
	 */
	public void getEmpLeaveTypeData(EmpLeaveTypeForm empLeaveTypeForm)throws Exception {
		List<EmpLeaveTypeTo> list=new ArrayList<EmpLeaveTypeTo>();
		Map<String,EmpLeaveType> leaveMap=leaveTrans.getEmpLeaveDetails();
		if(leaveMap!=null){
			empLeaveTypeForm.setLeaveMap(leaveMap);
			list=EmpLeaveTypeHelper.getInstance().convertMapToTo(leaveMap);
		}
		if(list!=null){
			empLeaveTypeForm.setEmpLeaveTypeTo(list);
		}
	}

	/**
	 * @param empLeaveTypeForm
	 * @return
	 * @throws Exception
	 */
	public Map<String, EmpLeaveType> getEmpLeaveTypeNames(EmpLeaveTypeForm empLeaveTypeForm)throws Exception {
		Map<String,String> codeMap=new HashMap<String, String>();
		Map<String ,EmpLeaveType> namesMap=leaveTrans.getEmpLeaveNamesMap();
		if(namesMap!=null ){
			Iterator<EmpLeaveType> iterator=new ArrayList(namesMap.values()).iterator();
			while(iterator.hasNext()){
				EmpLeaveType type=iterator.next();
				if(type!=null && type.getCode()!=null && !type.getCode().isEmpty() && type.getId()>0){
					codeMap.put(String.valueOf(type.getId()),type.getCode());
				}
			}
			if(codeMap!=null)
				empLeaveTypeForm.setCodeMap(codeMap);
		}
		return namesMap;
	}

	/**
	 * @param empLeaveTypeForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveLeaveType(EmpLeaveTypeForm empLeaveTypeForm)throws Exception {
		EmpLeaveType empLeaveType=null;
		boolean flag=false;
		if(empLeaveTypeForm.getEditOrReset()!=null && !empLeaveTypeForm.getEditOrReset().isEmpty()&& empLeaveTypeForm.getEditOrReset().equalsIgnoreCase("edit")){
				empLeaveType=EmpLeaveTypeHelper.getInstance().convertFormToBo(empLeaveTypeForm);
				flag=leaveTrans.saveLeaveType(empLeaveType);
			}else if(empLeaveTypeForm.getEditOrReset()!=null && !empLeaveTypeForm.getEditOrReset().isEmpty() && empLeaveTypeForm.getEditOrReset().equalsIgnoreCase("reset")){
				if(empLeaveTypeForm.getEmpLeaveType()!=null){
					empLeaveType=empLeaveTypeForm.getEmpLeaveType();
					empLeaveType.setIsActive(true);
					flag=leaveTrans.saveLeaveType(empLeaveType);
				}
			}
		else{
			empLeaveType=EmpLeaveTypeHelper.getInstance().convertFormToBo(empLeaveTypeForm);
			if(empLeaveType!=null)
				flag=leaveTrans.saveLeaveType(empLeaveType);
			}
		return flag;
	}

	/**
	 * @param empLeaveTypeForm
	 * @throws Exception
	 */
	public void getEmpLeaveEdit(EmpLeaveTypeForm empLeaveTypeForm)throws Exception {
		EmpLeaveType empLeaveType=null;
		if(empLeaveTypeForm.getLeaveMap()!=null && empLeaveTypeForm.getId()!=null && !empLeaveTypeForm.getId().isEmpty()){
			if(empLeaveTypeForm.getLeaveMap().containsKey(empLeaveTypeForm.getId()))
				empLeaveType=empLeaveTypeForm.getLeaveMap().get(empLeaveTypeForm.getId());
				EmpLeaveTypeHelper.getInstance().convertBoToForm(empLeaveType,empLeaveTypeForm);
		}
		
	}

	/**
	 * @param empLeaveTypeForm
	 * @throws Exception
	 */
	public boolean getEmpLeaveDelete(EmpLeaveTypeForm empLeaveTypeForm)throws Exception {
		EmpLeaveType empLeaveType=null;
		boolean flag=false;
		if(empLeaveTypeForm.getLeaveMap()!=null && empLeaveTypeForm.getId()!=null && !empLeaveTypeForm.getId().isEmpty()){
			if(empLeaveTypeForm.getLeaveMap().containsKey(empLeaveTypeForm.getId()))
				empLeaveType=empLeaveTypeForm.getLeaveMap().get(empLeaveTypeForm.getId());
				empLeaveType.setIsActive(false);
				if(empLeaveTypeForm.getUserId()!=null && !empLeaveTypeForm.getUserId().isEmpty()){
					empLeaveType.setCreatedBy(empLeaveTypeForm.getUserId());
					empLeaveType.setModifiedBy(empLeaveTypeForm.getUserId());
				}
				empLeaveType.setCreatedDate(new Date());
				empLeaveType.setLastModifiedDate(new Date());
				flag=leaveTrans.saveLeaveType(empLeaveType);
		}
		return flag;
	}
}
