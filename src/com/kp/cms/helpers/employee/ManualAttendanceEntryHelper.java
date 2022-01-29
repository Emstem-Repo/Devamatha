package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import com.kp.cms.bo.admin.EmpAttendance;
import com.kp.cms.bo.admin.EmpAttendanceBc;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.forms.employee.ManualAttendanceEntryForm;
import com.kp.cms.to.employee.ManualAttendanceEntryTO;
import com.kp.cms.utilities.CommonUtil;

public class ManualAttendanceEntryHelper {

	/**
	 * 
	 * @param manualAttendanceEntryList
	 * @param entered
	 * @return
	 * @throws Exception
	 */
	public List<ManualAttendanceEntryTO> convertBOstoTOs(List<EmpAttendance> manualAttendanceEntryList,ManualAttendanceEntryForm manualAttendanceEntryForm)
			throws Exception {

		List<ManualAttendanceEntryTO> manualAttendanceEntryTOList = new ArrayList<ManualAttendanceEntryTO>();
		ManualAttendanceEntryTO manualAttendanceEntryTO = null;
		if (manualAttendanceEntryList != null) 
		{
			Iterator<EmpAttendance> itr = manualAttendanceEntryList.iterator();
			while (itr.hasNext()) 
			{
				manualAttendanceEntryTO = new ManualAttendanceEntryTO();
				EmpAttendance empAttendance =(EmpAttendance) itr.next();
				if(empAttendance.getInTime()!=null && !empAttendance.getInTime().isEmpty())
				{
					manualAttendanceEntryTO.setInTime(empAttendance.getInTime());
				}
				else
				{
					manualAttendanceEntryTO.setInTime("");
				}
				
				if(empAttendance.getOutTime()!=null && !empAttendance.getOutTime().isEmpty())
				{
					manualAttendanceEntryTO.setOutTime(empAttendance.getOutTime());
				}
				else
				{
					manualAttendanceEntryTO.setOutTime("");
				}
				
				if(empAttendance.getDate()!=null)
				{
					manualAttendanceEntryTO.setAttendanceDate(CommonUtil.formatDateToDesiredFormatString(CommonUtil.getStringDate(empAttendance.getDate()),"dd-MMM-yyyy","dd/MM/yyyy"));
				}
				manualAttendanceEntryTO.setId(empAttendance.getId());
				manualAttendanceEntryTOList.add(manualAttendanceEntryTO);
			}	

		}

		return manualAttendanceEntryTOList;
	}

	/**
	 * 
	 * @param manualAttendanceEntryForm
	 * @return
	 * @throws Exception
	 */
	public EmpAttendance convertFormToBOs(ManualAttendanceEntryForm manualAttendanceEntryForm)throws Exception 
	{
		EmpAttendance empAttendance = new EmpAttendance();
		Employee employee = new Employee();
		employee.setId(Integer.parseInt(manualAttendanceEntryForm.getEmployeeId()));
		empAttendance.setEmployee(employee);
		empAttendance.setDate(CommonUtil.ConvertStringToSQLDateTime(manualAttendanceEntryForm.getAttendanceDate()+" 0:00:00"));
		empAttendance.setInTime(manualAttendanceEntryForm.getInTimeHrs()+":"+manualAttendanceEntryForm.getInTimeMins());
		empAttendance.setOutTime(manualAttendanceEntryForm.getOutTimeHrs()+":"+manualAttendanceEntryForm.getOutTimeMins());
		empAttendance.setCreatedBy(manualAttendanceEntryForm.getUserId());
		empAttendance.setCreatedDate(new Date());
		empAttendance.setModifiedBy(manualAttendanceEntryForm.getUserId());
		empAttendance.setLastModifiedDate(new Date());
		empAttendance.setIsActive(true);
		return empAttendance;
	}

	public String getAttendanceListQuery(ManualAttendanceEntryForm manualAttendanceEntryForm) {
		
		String Query="";
		Query="select e from EmpAttendance e" +
		" where e.employee.id=" +manualAttendanceEntryForm.getEmployeeId()+
		" and e.date>='"+CommonUtil.ConvertStringToDateFormat(manualAttendanceEntryForm.getStartDate(), "dd/mm/yyyy","yyyy-mm-dd")+"'" +
		" and e.date<='"+CommonUtil.ConvertStringToDateFormat(manualAttendanceEntryForm.getEndDate(), "dd/mm/yyyy","yyyy-mm-dd")+"'" +
		" and e.isActive=1 order by e.date";
		return Query;
	}

	public void convertBotoTo(ManualAttendanceEntryForm manualAttendanceEntryForm,EmpAttendance empAttendance) 
	{
		manualAttendanceEntryForm.setAttendanceDate(CommonUtil.formatDateToDesiredFormatString(CommonUtil.getStringDate(empAttendance.getDate()),"dd-MMM-yyyy","dd/MM/yyyy"));
		StringTokenizer str=new StringTokenizer(empAttendance.getInTime(),":");
		manualAttendanceEntryForm.setInTimeHrs(str.nextToken());
		manualAttendanceEntryForm.setInTimeMins(str.nextToken());
		str=new StringTokenizer(empAttendance.getOutTime(),":");
		manualAttendanceEntryForm.setOutTimeHrs(str.nextToken());
		manualAttendanceEntryForm.setOutTimeMins(str.nextToken());
	}

	public EmpAttendance convertFormToBOForEdit(ManualAttendanceEntryForm manualAttendanceEntryForm,EmpAttendance empAttendance) {
		empAttendance.setDate(CommonUtil.ConvertStringToSQLDateTime(manualAttendanceEntryForm.getAttendanceDate()+" 0:00:00"));
		empAttendance.setInTime(manualAttendanceEntryForm.getInTimeHrs()+":"+manualAttendanceEntryForm.getInTimeMins());
		empAttendance.setOutTime(manualAttendanceEntryForm.getOutTimeHrs()+":"+manualAttendanceEntryForm.getOutTimeMins());
		empAttendance.setModifiedBy(manualAttendanceEntryForm.getUserId());
		empAttendance.setLastModifiedDate(new Date());
		empAttendance.setIsActive(true);
		return empAttendance;
	}

	public String getAttendanceExistsQuery(ManualAttendanceEntryForm manualAttendanceEntryForm) 
	{
		String Query="";
		Query="select e from EmpAttendance e" +
		" where e.date='"+CommonUtil.ConvertStringToDateFormat(manualAttendanceEntryForm.getAttendanceDate(), "dd/mm/yyyy","yyyy-mm-dd")+"'" +
		" and e.isActive=1";
		return Query;
	}

	public EmpAttendanceBc createBackUp(EmpAttendance manualAttendance,String action,ManualAttendanceEntryForm formObj) 
	{
		EmpAttendanceBc empAttendanceBc=new EmpAttendanceBc();
		empAttendanceBc.setEmployee(manualAttendance.getEmployee());
		empAttendanceBc.setDate(manualAttendance.getDate());
		if(action.equalsIgnoreCase("A"))
		{	
			empAttendanceBc.setCreatedBy(manualAttendance.getCreatedBy());
			empAttendanceBc.setCreatedDate(manualAttendance.getCreatedDate());
			empAttendanceBc.setModifiedBy(manualAttendance.getModifiedBy());
			empAttendanceBc.setLastModifiedDate(manualAttendance.getLastModifiedDate());
		}
		else
		{
			empAttendanceBc.setCreatedBy(formObj.getUserId());
			empAttendanceBc.setCreatedDate(new Date());
			empAttendanceBc.setModifiedBy(formObj.getUserId());
			empAttendanceBc.setLastModifiedDate(new Date());
		}
		empAttendanceBc.setIsActive(true);
		empAttendanceBc.setInTime(manualAttendance.getInTime());
		empAttendanceBc.setOutTime(manualAttendance.getOutTime());
		empAttendanceBc.setAction(action);
		return empAttendanceBc;
	}
}