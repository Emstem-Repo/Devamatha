package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.forms.employee.EmpLeaveTypeForm;
import com.kp.cms.to.employee.EmpLeaveTypeTo;

public class EmpLeaveTypeHelper {
	private static volatile EmpLeaveTypeHelper instance=null;
	private EmpLeaveTypeHelper(){}
	public static EmpLeaveTypeHelper getInstance() {
		if(instance==null)
			instance=new EmpLeaveTypeHelper();
		return instance;
	}
	public List<EmpLeaveTypeTo> convertMapToTo(Map<String, EmpLeaveType> leaveMap) {
		List<EmpLeaveTypeTo> list=new ArrayList<EmpLeaveTypeTo>();
		if(leaveMap!=null){
			List<EmpLeaveType> leaveList=new ArrayList<EmpLeaveType>(leaveMap.values());
			Iterator<EmpLeaveType> iterator=leaveList.iterator();
			while(iterator.hasNext()){
				EmpLeaveType emp=iterator.next();
				if(emp!=null){
					EmpLeaveTypeTo empLeaveTypeTo=new EmpLeaveTypeTo();
					if(emp.getId()>0){
						empLeaveTypeTo.setId(String.valueOf(emp.getId()));
					}
					if(emp.getName()!=null && !emp.getName().isEmpty()){
						empLeaveTypeTo.setLeaveType(emp.getName());
					}
					if(emp.getIsLeave()!=null)
						if(emp.getIsLeave()){
							empLeaveTypeTo.setIsLeave("YES");
						}else{
							empLeaveTypeTo.setIsLeave("NO");
						}
					if(emp.getisExemption()!=null)
						if(emp.getisExemption()){
							empLeaveTypeTo.setIsExemption("YES");
						}else{
							empLeaveTypeTo.setIsExemption("NO");
						}
					if(emp.getContinuousdays()!=null)
						if(emp.getContinuousdays()){
							empLeaveTypeTo.setContinuousdays("YES");
						}else{
							empLeaveTypeTo.setContinuousdays("NO");
						}
					if(emp.getCanapplyonline()!=null)
						if(emp.getCanapplyonline()){
							empLeaveTypeTo.setCanapplyonline("YES");
						}else{
							empLeaveTypeTo.setCanapplyonline("NO");
						}
					if(emp.getCode()!=null && !emp.getCode().isEmpty()){
						empLeaveTypeTo.setCode(emp.getCode());
					}
					list.add(empLeaveTypeTo);
				}
			}
		}
		return list;
	}
	public EmpLeaveType convertFormToBo(EmpLeaveTypeForm empLeaveTypeForm)throws Exception {
		EmpLeaveType empLeaveType=null;
		if(empLeaveTypeForm.getId()!=null && !empLeaveTypeForm.getId().isEmpty() && empLeaveTypeForm.getLeaveMap()!=null){
			empLeaveType=empLeaveTypeForm.getLeaveMap().get(empLeaveTypeForm.getId());
		}else
			empLeaveType=new EmpLeaveType();
		
		if(empLeaveTypeForm.getLeaveType()!=null && !empLeaveTypeForm.getLeaveType().isEmpty()){
			empLeaveType.setName(empLeaveTypeForm.getLeaveType());
		}
		if(empLeaveTypeForm.getIsLeave()!=null && !empLeaveTypeForm.getIsLeave().isEmpty()){
			if(empLeaveTypeForm.getIsLeave().equalsIgnoreCase("yes")){
				empLeaveType.setIsLeave(true);
			}else{
				empLeaveType.setIsLeave(false);
			}
		}
		if(empLeaveTypeForm.getIsException()!=null && !empLeaveTypeForm.getIsException().isEmpty()){
			if(empLeaveTypeForm.getIsException().equalsIgnoreCase("yes")){
				empLeaveType.setisExemption(true);
			}else{
				empLeaveType.setisExemption(false);
			}
		}
		if(empLeaveTypeForm.getCode()!=null && !empLeaveTypeForm.getCode().isEmpty()){
			empLeaveType.setCode(empLeaveTypeForm.getCode());
		}

		if(empLeaveTypeForm.getContinuousdays().equalsIgnoreCase("true")){
			empLeaveType.setContinuousdays(true);
		}else{
			empLeaveType.setContinuousdays(false);
		}
		if(empLeaveTypeForm.getCanapplyonline().equalsIgnoreCase("true")){
			empLeaveType.setCanapplyonline(true);
		}else{
			empLeaveType.setCanapplyonline(false);
		}
		empLeaveType.setIsActive(true);
		if(empLeaveTypeForm.getUserId()!=null && !empLeaveTypeForm.getUserId().isEmpty()){
			empLeaveType.setCreatedBy(empLeaveTypeForm.getUserId());
			empLeaveType.setModifiedBy(empLeaveTypeForm.getUserId());
		}
		empLeaveType.setCreatedDate(new Date());
		empLeaveType.setLastModifiedDate(new Date());
		
		return empLeaveType;
	}
	public void convertBoToForm(EmpLeaveType empLeaveType,EmpLeaveTypeForm empLeaveTypeForm) {
		if(empLeaveType!=null){
			if(empLeaveType.getName()!=null && !empLeaveType.getName().isEmpty()){
				empLeaveTypeForm.setLeaveType(empLeaveType.getName());
			}
			
			if(empLeaveType.getIsLeave()!=null && empLeaveType.getIsLeave()){
				empLeaveTypeForm.setIsLeave("yes");
			}else{
				empLeaveTypeForm.setIsLeave("no");
			}
			
			if(empLeaveType.getisExemption()!=null && empLeaveType.getisExemption()){
				empLeaveTypeForm.setIsException("yes");
			}else{
				empLeaveTypeForm.setIsException("no");
			}
			if(empLeaveType.getContinuousdays()!=null && empLeaveType.getContinuousdays()){
				empLeaveTypeForm.setContinuousdays("true");
			}else{
				empLeaveTypeForm.setContinuousdays("false");
			}
			if(empLeaveType.getCanapplyonline()!=null && empLeaveType.getCanapplyonline()){
				empLeaveTypeForm.setCanapplyonline("true");
			}else{
				empLeaveTypeForm.setCanapplyonline("false");
			}
			if(empLeaveType.getCode()!=null && !empLeaveType.getCode().isEmpty()){
				empLeaveTypeForm.setCode(empLeaveType.getCode());
			}
			empLeaveTypeForm.setEditOrReset("edit");
		}
	}

}
