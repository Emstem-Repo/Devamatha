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
import com.kp.cms.bo.employee.EmployeeSettings;
import com.kp.cms.bo.employee.Holidays;
import com.kp.cms.forms.employee.EmployeeSettingsForm;
import com.kp.cms.forms.employee.HolidayDetailsForm;
import com.kp.cms.to.employee.EmployeeSettingsTO;
import com.kp.cms.to.employee.HolidayDetailsTO;
import com.kp.cms.utilities.CommonUtil;

public class EmployeeSettingsHelper {
	private static final Log log = LogFactory.getLog(EmployeeSettingsHelper.class);
	public static volatile EmployeeSettingsHelper empSettingsHelper = null;

	public static EmployeeSettingsHelper getInstance() {
		if (empSettingsHelper == null) {
			empSettingsHelper = new EmployeeSettingsHelper();
			return empSettingsHelper;
		}
		return empSettingsHelper;
	}
	public List<EmployeeSettingsTO> convertBosToTOs(List<EmployeeSettings> employeeSettings ){
    	List<EmployeeSettingsTO> empSettingsTO=new ArrayList<EmployeeSettingsTO>();
    	if(employeeSettings!=null){
		Iterator itr=employeeSettings.iterator();
		while(itr.hasNext()){
			EmployeeSettings empSett=(EmployeeSettings)itr.next();
			EmployeeSettingsTO empSettTO=new EmployeeSettingsTO();
			if(empSett.getAbsenceChecking()!=null)
			empSettTO.setAbsenceChecking(empSett.getAbsenceChecking()? "Yes" : "No");
			if(empSett.getAgeOfRetirement()!=null)
			empSettTO.setAgeOfRetirement(empSett.getAgeOfRetirement());
			if(empSett.getSmsAlert()!=null)
			empSettTO.setSmsAlert(empSett.getSmsAlert()? "ON" : "OFF");
			empSettTO.setId(empSett.getId());
			empSettingsTO.add(empSettTO);
		}}
		return empSettingsTO;
	}
	public Map<Integer,String> setToMap(List<EmpLeaveType> empLeave){
        Map<Integer,String> leaveMap=new HashMap<Integer,String>();
		Iterator itr=empLeave.iterator();
        while(itr.hasNext()){
        	EmpLeaveType employLeave=(EmpLeaveType)itr.next();
        	leaveMap.put(employLeave.getId(), employLeave.getName());
        }
		return leaveMap;
	}
	public void setBotoForm(EmployeeSettingsForm empSettingsForm,EmployeeSettings empSettings){
		if(empSettings!=null){
			if(empSettings.getSmsAlert()!=null){
			empSettingsForm.setSmsAlert((empSettings.getSmsAlert()) ? "on" : "off");
			empSettingsForm.setOrigSmsAlert((empSettings.getSmsAlert()) ? "on" : "off");
			}
			if(empSettings.getAbsenceChecking()!=null){
			empSettingsForm.setAbsenceChecking((empSettings.getAbsenceChecking())? "yes" : "no");
			empSettingsForm.setOrigAbsenceCheck((empSettings.getAbsenceChecking())? "yes" : "no");
			}
			if(empSettings.getAgeOfRetirement()!=0){
			empSettingsForm.setAgeOfRetirement(Integer.toString(empSettings.getAgeOfRetirement()));
			empSettingsForm.setOrigAgeOfRetire(Integer.toString(empSettings.getAgeOfRetirement()));
			}
			if(empSettings.getCurrentApplicationNo()!=null){
				empSettingsForm.setCurrentApplicationNo(empSettings.getCurrentApplicationNo());
				empSettingsForm.setOrigAppNo(empSettings.getCurrentApplicationNo());
			}
			if(empSettings.getAccumulateLeaveType()!=null){
				empSettingsForm.setAccumulateLeaveType(
						String.valueOf(empSettings.getAccumulateLeaveType().getId()));
				empSettingsForm.setOrigEmpLeave(String.valueOf(empSettings.getAccumulateLeaveType().getId()));
			}
			if(empSettings.getMachineIdForOfflineINPunch()!=null && empSettings.getMachineIdForOfflineINPunch()>=0){
				empSettingsForm.setMachineIdForOfflineINPunch(String.valueOf(empSettings.getMachineIdForOfflineINPunch()));
			}
			if(empSettings.getMachineIdForOfflineOUTPunch()!=null && empSettings.getMachineIdForOfflineOUTPunch()>=0){
				empSettingsForm.setMachineIdForOfflineOUTPunch(String.valueOf(empSettings.getMachineIdForOfflineOUTPunch()));
			}
		}
	}
	public boolean checkApplicationNo(Integer curentAppNo,EmployeeSettingsForm empSettingsForm){
		boolean isCheck=false;
		if(curentAppNo!=null){
			int appNo=Integer.parseInt(empSettingsForm.getCurrentApplicationNo());
			if(appNo>curentAppNo || appNo==curentAppNo){
				isCheck=true;
			}
		}else{
			if(curentAppNo==null){
				isCheck=true;
			}
		}
		return isCheck;
	}
	public EmployeeSettings convertFormToBo(EmployeeSettingsForm empSettingsForm){
		EmployeeSettings empSettings=new EmployeeSettings();
		empSettings.setSmsAlert(empSettingsForm.getSmsAlert()
				.equalsIgnoreCase("ON") ? true : false);
		empSettings.setAbsenceChecking((empSettingsForm.getAbsenceChecking()
				.equalsIgnoreCase("yes") ? true : false));
		empSettings.setAgeOfRetirement(Integer.parseInt(empSettingsForm.getAgeOfRetirement()));
		if(empSettingsForm.getCurrentApplicationNo()!=null && !empSettingsForm.getCurrentApplicationNo().isEmpty()){
			empSettings.setCurrentApplicationNo(empSettingsForm.getCurrentApplicationNo());
		}
		if(empSettingsForm.getAccumulateLeaveType()!=null && !empSettingsForm.getAccumulateLeaveType().isEmpty()){
			EmpLeaveType leaveType=new EmpLeaveType();
			leaveType.setId(Integer.parseInt(empSettingsForm.getAccumulateLeaveType()));
			empSettings.setAccumulateLeaveType(leaveType);
		}
		empSettings.setModifiedBy(empSettingsForm.getUserId());
		empSettings.setLastModifiedDate(new Date());
		empSettings.setId(empSettingsForm.getId());
		if(empSettingsForm.getMachineIdForOfflineINPunch()!=null && !empSettingsForm.getMachineIdForOfflineINPunch().isEmpty()){
			empSettings.setMachineIdForOfflineINPunch(Integer.parseInt(empSettingsForm.getMachineIdForOfflineINPunch()));
		}
		if(empSettingsForm.getMachineIdForOfflineOUTPunch()!=null && !empSettingsForm.getMachineIdForOfflineOUTPunch().isEmpty()){
			empSettings.setMachineIdForOfflineOUTPunch(Integer.parseInt(empSettingsForm.getMachineIdForOfflineOUTPunch()));
		}
		return empSettings;
		
	}
}
