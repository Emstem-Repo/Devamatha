package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.forms.employee.EmpTypeForm;
import com.kp.cms.to.employee.EmpTypeTo;

public class EmpTypeHelper {
	
	private static volatile EmpTypeHelper instance=null;
	
	/**
	 * 
	 */
	private EmpTypeHelper(){
		
	}

	/**
	 * @return
	 */
	public static EmpTypeHelper getInstance(){
		if(instance==null){
			instance=new EmpTypeHelper();
		}
		return instance;
	}

	/**
	 * @param empTypeMap
	 * @return
	 */
	public List<EmpTypeTo> convertEmpTypeBoToempTypeTo(Map<String, EmpType> empTypeMap) {
		// TODO Auto-generated method stub
		List<EmpTypeTo> empTypeToList=new ArrayList<EmpTypeTo>();
		if(empTypeMap!=null && empTypeMap.size()!=0){
			List<EmpType> list=new ArrayList<EmpType>(empTypeMap.values());
			if(list!=null){
				Iterator<EmpType> iterator=list.iterator();
				while(iterator.hasNext()){
				EmpType empType=iterator.next();
				EmpTypeTo empTypeTo=null;
				if(empType!=null){
					empTypeTo=new EmpTypeTo();
					if(empType.getId()>0){
						empTypeTo.setId(String.valueOf(empType.getId()));
					}
					
					if(empType.getName()!=null && !empType.getName().isEmpty()){
						empTypeTo.setName(empType.getName());
					}
				}
				if(empTypeTo!=null)
				empTypeToList.add(empTypeTo);
				}
			}
		}
		return empTypeToList;
	}

	/**
	 * @param empTypeForm
	 * @param empType
	 * @return
	 */
	public EmpType convertFormToBo(EmpTypeForm empTypeForm, EmpType empType) {
//		EmpType empType1=new EmpType();
		
		if(empTypeForm!=null){
			if(empType.getId()<=0){
				empType=new EmpType();
			}
			if(empTypeForm.getId()!=null && !empTypeForm.getId().isEmpty()){
				empType.setId(Integer.parseInt(empTypeForm.getId()));
			}
			
			if(empTypeForm.getName()!=null && !empTypeForm.getName().isEmpty()){
				empType.setName(empTypeForm.getName());
			}
			
			if(empTypeForm.getTimeIn()!=null && !empTypeForm.getTimeIn().isEmpty() && empTypeForm.getTimeInMin()!=null && !empTypeForm.getTimeInMin().isEmpty()){
				empType.setTimeIn(empTypeForm.getTimeIn()+":"+empTypeForm.getTimeInMin());
			}
			
			if(empTypeForm.getTimeInEnds()!=null && !empTypeForm.getTimeInEnds().isEmpty()&& empTypeForm.getTimeInEndMIn()!=null && !empTypeForm.getTimeInEndMIn().isEmpty()){
				empType.setTimeInEnds(empTypeForm.getTimeInEnds()+":"+empTypeForm.getTimeInEndMIn());
			}
			
			if(empTypeForm.getTimeOut()!=null && !empTypeForm.getTimeOut().isEmpty() && empTypeForm.getTimeOutMin()!=null && !empTypeForm.getTimeOutMin().isEmpty()){
				empType.setTimeOut(empTypeForm.getTimeOut()+":"+empTypeForm.getTimeOutMin());
			}
			
			if(empTypeForm.getSaturdayTimeOut()!=null && !empTypeForm.getSaturdayTimeOut().isEmpty() && empTypeForm.getSaturdayTimeOutMin()!=null && !empTypeForm.getSaturdayTimeOutMin().isEmpty()){
				empType.setSaturdayTimeOut(empTypeForm.getSaturdayTimeOut()+":"+empTypeForm.getSaturdayTimeOutMin());
			}
			
			if(empTypeForm.getHalfDayStartTime()!=null && !empTypeForm.getHalfDayStartTime().isEmpty() && empTypeForm.getHalfDayStartTimeMin()!=null && !empTypeForm.getHalfDayStartTimeMin().isEmpty()){
				empType.setHalfDayStartTime(empTypeForm.getHalfDayStartTime()+":"+empTypeForm.getHalfDayStartTimeMin());
			}
			
			if(empTypeForm.getHalfDatyEndTime()!=null && !empTypeForm.getHalfDatyEndTime().isEmpty() && empTypeForm.getHalfDayEndTimeMin()!=null && !empTypeForm.getHalfDayEndTimeMin().isEmpty()){
				empType.setHalfDatyEndTime(empTypeForm.getHalfDatyEndTime()+":"+empTypeForm.getHalfDayEndTimeMin());
			}
			
			if(empTypeForm.getLeaveInitializeMonth()!=null && !empTypeForm.getLeaveInitializeMonth().isEmpty()){
				empType.setLeaveInitializeMonth(empTypeForm.getLeaveInitializeMonth());
			}
		}
		return empType;
	}

	/**
	 * @param empType
	 * @param empTypeForm
	 */
	public void convetBoToForm(EmpType empType, EmpTypeForm empTypeForm) {
		if(empType!=null && empTypeForm!=null){
			if(empType.getId()>0){
				empTypeForm.setId(String.valueOf(empType.getId()));
			}
			
			if(empType.getName()!=null && !empType.getName().isEmpty()){
				empTypeForm.setName(empType.getName());
			}
			
			if(empType.getTimeIn()!=null && !empType.getTimeIn().isEmpty()){
				empTypeForm.setTimeIn(empType.getTimeIn().substring(0,2));
				empTypeForm.setTimeInMin(empType.getTimeIn().substring(3,5));
			}
		
			if(empType.getTimeInEnds()!=null && !empType.getTimeInEnds().isEmpty()){
				empTypeForm.setTimeInEnds(empType.getTimeInEnds().substring(0,2));
				empTypeForm.setTimeInEndMIn(empType.getTimeInEnds().substring(3,5));
			}
			
			if(empType.getTimeOut()!=null && !empType.getTimeOut().isEmpty()){
				empTypeForm.setTimeOut(empType.getTimeOut().substring(0,2));
				empTypeForm.setTimeOutMin(empType.getTimeOut().substring(3,5));
			}
			
			if(empType.getSaturdayTimeOut()!=null && !empType.getSaturdayTimeOut().isEmpty()){
				empTypeForm.setSaturdayTimeOut(empType.getSaturdayTimeOut().substring(0,2));
				empTypeForm.setSaturdayTimeOutMin(empType.getSaturdayTimeOut().substring(3,5));
			}
			
			if(empType.getHalfDayStartTime()!=null && !empType.getHalfDayStartTime().isEmpty()){
				empTypeForm.setHalfDayStartTime(empType.getHalfDayStartTime().substring(0,2));
				empTypeForm.setHalfDayStartTimeMin(empType.getHalfDayStartTime().substring(3,5));
			}
			
			if(empType.getHalfDatyEndTime()!=null && !empType.getHalfDatyEndTime().isEmpty()){
				empTypeForm.setHalfDatyEndTime(empType.getHalfDatyEndTime().substring(0,2));
				empTypeForm.setHalfDayEndTimeMin(empType.getHalfDatyEndTime().substring(3,5));
			}
			
			if(empType.getLeaveInitializeMonth()!=null && !empType.getLeaveInitializeMonth().isEmpty()){
				empTypeForm.setLeaveInitializeMonth(empType.getLeaveInitializeMonth());
			}
		}
	}
}
