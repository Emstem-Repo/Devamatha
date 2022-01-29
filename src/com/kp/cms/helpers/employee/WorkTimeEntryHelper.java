package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpWorkTime;
import com.kp.cms.bo.admin.EmployeeTypeBO;
import com.kp.cms.forms.employee.WorkTimeEntryForm;
import com.kp.cms.to.attendance.WorkTimeTO;
import com.kp.cms.to.employee.EmpWorkTimeTO;
import com.kp.cms.utilities.CommonUtil;

public class WorkTimeEntryHelper {
	private static final Log log = LogFactory.getLog(WorkTimeEntryHelper.class);
	public static volatile WorkTimeEntryHelper workTimeEntryHelper = null;
	
	public static WorkTimeEntryHelper getInstance() {
		if (workTimeEntryHelper == null) {
			workTimeEntryHelper = new WorkTimeEntryHelper();
			return workTimeEntryHelper;
		}
		return workTimeEntryHelper;
	}	
	
	/**
	 * 
	 * @param workTimeEntryForm
	 * @return
	 * @throws Exception
	 */
	public List<EmpWorkTime> copyDataFromFormToBO(WorkTimeEntryForm workTimeForm) throws Exception{
		log.debug("inside copyDataFromFormToBO");
		
//		if(workTimeEntryForm.getId() != 0) {
//			empWorkTime.setId(workTimeEntryForm.getId());
//		}
		List<EmpWorkTime> mainList=new ArrayList<EmpWorkTime>();
		EmployeeTypeBO employeeTypeBO=new EmployeeTypeBO();
		if(workTimeForm.getName()!= null && !workTimeForm.getName().trim().isEmpty()){
			employeeTypeBO.setId(Integer.parseInt(workTimeForm.getName()));
		}
		
		String inTimeFromMins; 
		String inTimeToMins;
		String outTimeFromMins; 
		String outTimeToMins;
		List<WorkTimeTO> list=workTimeForm.getWorkList();
		Iterator<WorkTimeTO> itr=list.iterator();
		while (itr.hasNext()) {
			EmpWorkTime empWorkTime = new EmpWorkTime();
			WorkTimeTO workTimeEntryForm = (WorkTimeTO) itr.next();
			if(workTimeEntryForm.getId()>0){
				empWorkTime.setId(workTimeEntryForm.getId());
			}
			empWorkTime.setName(workTimeEntryForm.getName());
			if(workTimeEntryForm.getInTimeFromMins() != null && !workTimeEntryForm.getInTimeFromMins().isEmpty()){
				inTimeFromMins = workTimeEntryForm.getInTimeFromMins();
			}
			else{
				inTimeFromMins = "00";
			}
			if(workTimeEntryForm.getInTimeToMins() != null && !workTimeEntryForm.getInTimeToMins().isEmpty()){
				inTimeToMins = workTimeEntryForm.getInTimeToMins();
			}
			else {
				inTimeToMins = "00";
			}
			
			if(workTimeEntryForm.getOutTimeFromMins() != null && !workTimeEntryForm.getOutTimeFromMins().isEmpty()){
				outTimeFromMins = workTimeEntryForm.getOutTimeFromMins();
			}
			else{
				outTimeFromMins = "00";
			}
			if(workTimeEntryForm.getOutTimeToMins() != null && !workTimeEntryForm.getOutTimeToMins().isEmpty()){
				outTimeToMins = workTimeEntryForm.getOutTimeToMins();
			}
			else {
				outTimeToMins = "00";
			}				
			
			empWorkTime.setInTimeFrom(CommonUtil.getTime(workTimeEntryForm.getInTimeFromHr(), inTimeFromMins));
			empWorkTime.setInTimeTo(CommonUtil.getTime(workTimeEntryForm.getInTimeToHrs(), inTimeToMins));
			empWorkTime.setOutTimeFrom(CommonUtil.getTime(workTimeEntryForm.getOutTimeFromHrs(), outTimeFromMins));
			empWorkTime.setOutTimeTo(CommonUtil.getTime(workTimeEntryForm.getOutTimeToHrs(), outTimeToMins));
			if(workTimeEntryForm.isChecked()){
				empWorkTime.setIsHoliday(true);
			}else{
				empWorkTime.setIsHoliday(false);
			}
			empWorkTime.setIsActive(true);
			empWorkTime.setCreatedBy(workTimeForm.getUserId());
			empWorkTime.setModifiedBy(workTimeForm.getUserId());
			empWorkTime.setCreatedDate(new Date());
			empWorkTime.setLastModifiedDate(new Date());
			empWorkTime.setEmployeeTypeBO(employeeTypeBO);
			mainList.add(empWorkTime);
		}
		
		log.debug("leaving copyDataFromFormToBO");
		return mainList;
	}	

	/**
	 * 
	 * @param empWorkTimeTOList
	 *            this will copy the EmpWorkTime BO to TO
	 * @return empWorkTimeTOList having EmpWorkTimeTO objects.
	 */
	public List<EmpWorkTimeTO> copyWorkTimeBosToTos(List<EmpWorkTime> workTimeList) {
		log.debug("inside copyWorkTimeBosToTos");
		List<EmpWorkTimeTO> empWorkTimeTOList = new ArrayList<EmpWorkTimeTO>();
		Iterator<EmpWorkTime> iterator = workTimeList.iterator();
		EmpWorkTime empWorkTime;
		EmpWorkTimeTO empWorkTimeTO;
		while (iterator.hasNext()) {
			empWorkTimeTO = new EmpWorkTimeTO();
			empWorkTime = (EmpWorkTime) iterator.next();
			empWorkTimeTO.setId(empWorkTime.getId());
			if(empWorkTime.getName()!= null && !empWorkTime.getName().trim().isEmpty()){
				empWorkTimeTO.setName(empWorkTime.getName());
			}
			
			empWorkTimeTOList.add(empWorkTimeTO);
		}
		log.debug("leaving copyInvCounterBosToTos");
		return empWorkTimeTOList;
	}
	
	public void convertWorkTimeBosToTos(List<EmpWorkTime> workTimeList,WorkTimeEntryForm workTimeEntryForm) {
		log.debug("inside convertWorkTimeBosToTos");
		List<WorkTimeTO> workTimeTOList = new ArrayList<WorkTimeTO>();
		Iterator<EmpWorkTime> iterator = workTimeList.iterator();
		WorkTimeTO workTimeTO;
		EmpWorkTime empWorkTime;
		while(iterator.hasNext()){
			empWorkTime = (EmpWorkTime)iterator.next();
			workTimeTO=new WorkTimeTO();
			workTimeTO.setId(empWorkTime.getId());
			workTimeTO.setName(empWorkTime.getName());
			workTimeEntryForm.setName(Integer.toString(empWorkTime.getEmployeeTypeBO().getId()));
			String[] inTimeFrom=empWorkTime.getInTimeFrom().split(":");
			String[] outTimeFrom=empWorkTime.getOutTimeFrom().split(":");
			String[] inTimeTo=empWorkTime.getInTimeTo().split(":");
			String[] outTimeTo=empWorkTime.getOutTimeTo().split(":");
			if(empWorkTime.getIsHoliday()!=null && empWorkTime.getIsHoliday()){
				if(!inTimeFrom[0].equals("00"))
				workTimeTO.setInTimeFromHr(inTimeFrom[0]);
				if(!inTimeFrom[1].equals("00"))
				workTimeTO.setInTimeFromMins(inTimeFrom[1]);
				if(!outTimeFrom[0].equals("00"))
				workTimeTO.setOutTimeFromHrs(outTimeFrom[0]);
				if(!outTimeFrom[1].equals("00"))
				workTimeTO.setOutTimeFromMins(outTimeFrom[1]);
				if(!inTimeTo[0].equals("00"))
				workTimeTO.setInTimeToHrs(inTimeTo[0]);
				if(!inTimeTo[1].equals("00"))
				workTimeTO.setInTimeToMins(inTimeTo[1]);
				if(!outTimeTo[0].equals("00"))
				workTimeTO.setOutTimeToHrs(outTimeTo[0]);
				if(!outTimeTo[1].equals("00"))
				workTimeTO.setOutTimeToMins(outTimeTo[1]);
			}else{
				workTimeTO.setInTimeFromHr(inTimeFrom[0]);
				workTimeTO.setInTimeFromMins(inTimeFrom[1]);
				workTimeTO.setOutTimeFromHrs(outTimeFrom[0]);
				workTimeTO.setOutTimeFromMins(outTimeFrom[1]);
				workTimeTO.setInTimeToHrs(inTimeTo[0]);
				workTimeTO.setInTimeToMins(inTimeTo[1]);
				workTimeTO.setOutTimeToHrs(outTimeTo[0]);
				workTimeTO.setOutTimeToMins(outTimeTo[1]);
			}
//			workTimeTO.setChecked(empWorkTime.getIsHoliday());
			workTimeTO.setTempChecked(empWorkTime.getIsHoliday());
			workTimeTOList.add(workTimeTO);
		}
		workTimeEntryForm.setWorkList(workTimeTOList);
	}
	
	public List<EmpWorkTimeTO> copyEmployeeTypeBosToTos(List<EmployeeTypeBO> employeeTypeBoList) {
		log.debug("inside copyWorkTimeBosToTos");
		List<EmpWorkTimeTO> empWorkTimeTOList = new ArrayList<EmpWorkTimeTO>();
		Iterator<EmployeeTypeBO> iterator = employeeTypeBoList.iterator();
		EmployeeTypeBO empTypeBo;
		EmpWorkTimeTO empWorkTimeTO;
		while (iterator.hasNext()) {
			empWorkTimeTO = new EmpWorkTimeTO();
			empTypeBo = (EmployeeTypeBO) iterator.next();
			empWorkTimeTO.setId(empTypeBo.getId());
			if(empTypeBo.getName()!= null && !empTypeBo.getName().trim().isEmpty()){
				empWorkTimeTO.setName(empTypeBo.getName());
			}
			
			empWorkTimeTOList.add(empWorkTimeTO);
		}
		log.debug("leaving copyInvCounterBosToTos");
		return empWorkTimeTOList;
	}
}
