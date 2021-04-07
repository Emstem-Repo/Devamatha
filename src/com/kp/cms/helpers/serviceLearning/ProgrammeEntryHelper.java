package com.kp.cms.helpers.serviceLearning;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ExtraCreditActivityType;
import com.kp.cms.bo.serviceLearning.DepartmentNameEntry;
import com.kp.cms.bo.serviceLearning.ProgrammeEntryBo;
import com.kp.cms.forms.serviceLearning.DepartmentNameEntryForm;
import com.kp.cms.forms.serviceLearning.ProgrammeEntryForm;
import com.kp.cms.to.serviceLearning.DepartmentNameEntryTo;
import com.kp.cms.to.serviceLearning.ProgrammeEntryTo;
import com.kp.cms.utilities.CommonUtil;

public class ProgrammeEntryHelper {
	
	private static final Log log = LogFactory.getLog(ProgrammeEntryHelper.class);
	private static ProgrammeEntryHelper programmeEntryHelper= null;
	public static ProgrammeEntryHelper getInstance() {
		      if(programmeEntryHelper == null) {
		    	  programmeEntryHelper = new ProgrammeEntryHelper();
		    	  return programmeEntryHelper;
		      }
	return programmeEntryHelper;
	}
	
	
	public static List<ProgrammeEntryTo> convertBOsToTos(List<ProgrammeEntryBo> programmeEntryBoList)throws Exception {
		List<ProgrammeEntryTo> programmeEntryToList = new ArrayList<ProgrammeEntryTo>();
		DepartmentNameEntryTo departmentNameEntryTo;
		if (programmeEntryBoList != null) {
			Iterator<ProgrammeEntryBo> iterator = programmeEntryBoList.iterator();
			while (iterator.hasNext()) {
				ProgrammeEntryBo programmeEntryBo = (ProgrammeEntryBo) iterator.next();
				String startdate = null;
				String enddate = null;
				departmentNameEntryTo = new DepartmentNameEntryTo();
				ProgrammeEntryTo programmeEntryTo = new ProgrammeEntryTo();
				if(programmeEntryBo.getDepartmentNameEntry()!=null){
					departmentNameEntryTo.setId(String.valueOf(programmeEntryBo.getDepartmentNameEntry().getId()));
					departmentNameEntryTo.setDepartmentName(programmeEntryBo.getDepartmentNameEntry().getDepartmentName());
					programmeEntryTo.setDepartmentNameEntryTo(departmentNameEntryTo);
					programmeEntryTo.setDeptName(departmentNameEntryTo.getDepartmentName());
					
				}
				programmeEntryTo.setId(String.valueOf(programmeEntryBo.getId()));
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				if(programmeEntryBo.getStartDate()!=null){
					startdate = df.format(programmeEntryBo.getStartDate());
					programmeEntryTo.setStartDate(startdate);
				}	
				if(programmeEntryBo.getEndDate()!=null){
					enddate = df.format(programmeEntryBo.getEndDate());
					programmeEntryTo.setEndDate(enddate);
				}
				if(programmeEntryBo.getStartDate()!=null && programmeEntryBo.getEndDate()!=null){
					programmeEntryTo.setProgrammeName(programmeEntryBo.getProgrammeName()+"("+startdate+"_"+enddate+")");
				}else {
					programmeEntryTo.setProgrammeName(programmeEntryBo.getProgrammeName());
				}
				programmeEntryTo.setProgrammeCode(programmeEntryBo.getProgrammeCode());
				if(programmeEntryBo.getInchargeName()!=null)
				programmeEntryTo.setInchargeName(programmeEntryBo.getInchargeName());
				if(programmeEntryBo.getMaxHrs()!=null){
					programmeEntryTo.setMaxHrs(programmeEntryBo.getMaxHrs().toString());
				}
				programmeEntryTo.setActivityName(programmeEntryBo.getExtraCreditActivityType().getName());
				programmeEntryToList.add(programmeEntryTo);
			}
		}
		return programmeEntryToList;
	}
	
	public ProgrammeEntryBo addProgrammeNameEntry(
			ProgrammeEntryForm programmeEntryForm, String mode) {
		// TODO Auto-generated method stub
		
		ProgrammeEntryBo programmeEntryBo=new ProgrammeEntryBo();
		DepartmentNameEntry departmentNameEntry = new DepartmentNameEntry();
		ExtraCreditActivityType activityType = new ExtraCreditActivityType();
		activityType.setId(Integer.parseInt(programmeEntryForm.getExtraCreditActivityType()));
		if(programmeEntryForm.getDeptId()!=null && !programmeEntryForm.getDeptId().equalsIgnoreCase("")){
			departmentNameEntry.setId(Integer.parseInt(programmeEntryForm.getDeptId()));
			programmeEntryBo.setDepartmentNameEntry(departmentNameEntry);
		}
		programmeEntryBo.setProgrammeName(programmeEntryForm.getProgrammeName());
		programmeEntryBo.setProgrammeCode(programmeEntryForm.getProgrammeCode());
		if(programmeEntryForm.getInchargeName()!=null && !programmeEntryForm.getInchargeName().isEmpty())
		programmeEntryBo.setInchargeName(programmeEntryForm.getInchargeName());
		if(programmeEntryForm.getStartDate()!=null && !programmeEntryForm.getStartDate().isEmpty())
		programmeEntryBo.setStartDate(CommonUtil.ConvertStringToSQLDate(programmeEntryForm.getStartDate()));
		if(programmeEntryForm.getEndDate()!=null && !programmeEntryForm.getEndDate().isEmpty())
		programmeEntryBo.setEndDate(CommonUtil.ConvertStringToSQLDate(programmeEntryForm.getEndDate()));
		if(programmeEntryForm.getStartHours()!=null && !programmeEntryForm.getStartHours().isEmpty() &&
				programmeEntryForm.getStartMins()!=null && !programmeEntryForm.getStartMins().isEmpty())
		{
			programmeEntryBo.setMaxHrs(CommonUtil.getTime(programmeEntryForm.getStartHours(), programmeEntryForm.getStartMins()));
		}
		programmeEntryBo.setCreatedDate(new Date());
		programmeEntryBo.setIsActive(true);
		programmeEntryBo.setCreatedBy(programmeEntryForm.getUserId());
		programmeEntryBo.setExtraCreditActivityType(activityType);
	    if (mode.equalsIgnoreCase("Edit")) {
	    	programmeEntryBo.setLastModifiedDate(new java.util.Date());
	    	programmeEntryBo.setModifiedBy(programmeEntryForm.getUserId());
	    	programmeEntryBo.setId(Integer.parseInt(programmeEntryForm.getId()));

		}
	   return programmeEntryBo;
	}
	
	public void setProgrammeNameEntryDetails(ProgrammeEntryForm programmeEntryForm,
			ProgrammeEntryBo programmeEntryBo) {
		
		if (programmeEntryBo != null) {
			programmeEntryForm.setProgrammeName(programmeEntryBo.getProgrammeName());
			programmeEntryForm.setProgrammeCode(programmeEntryBo.getProgrammeCode());
			programmeEntryForm.setInchargeName(programmeEntryBo.getInchargeName());
			if(programmeEntryBo.getStartDate()!=null && programmeEntryBo.getEndDate()!=null){
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				String startdate = df.format(programmeEntryBo.getStartDate());
				String enddate = df.format(programmeEntryBo.getEndDate());
				programmeEntryForm.setStartDate(startdate);
				programmeEntryForm.setEndDate(enddate);
			}
			if(programmeEntryBo.getDepartmentNameEntry()!=null){
				programmeEntryForm.setDeptId(String.valueOf(programmeEntryBo.getDepartmentNameEntry().getId()));
			}
			if(programmeEntryBo.getMaxHrs()!=null){
				String time=programmeEntryBo.getMaxHrs().toString();
				String timeH=time.substring(0,2);
				String timeM=time.substring(3,5);
				programmeEntryForm.setStartHours(timeH);
				programmeEntryForm.setStartMins(timeM);
			}
			programmeEntryForm.setExtraCreditActivityType(String.valueOf(programmeEntryBo.getExtraCreditActivityType().getId()));
			
		}
		log.error("ending of setProgrammeNameEntryDetails method in ProgrammeEntryHelper");
	}

}
