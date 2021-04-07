package com.kp.cms.helpers.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.forms.attendance.PeriodForm;
import com.kp.cms.to.admission.CurriculumSchemeDurationTO;
import com.kp.cms.to.attendance.ClassSchemewiseTO;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.to.attendance.PeriodTO;
import com.kp.cms.utilities.CommonUtil;
public class PeriodHelper {
	
	private static Map<Integer, String> pMap = null;
	static {
		pMap = new HashMap<Integer, String>();
		pMap.put(13, "01");
		pMap.put(14, "02");
		pMap.put(15, "03");
		pMap.put(16, "04");
		pMap.put(17, "05");
		pMap.put(18, "06");
		pMap.put(19, "07");
		pMap.put(20, "08");
		pMap.put(21, "09");
		pMap.put(22, "10");
		pMap.put(23, "11");
		pMap.put(24, "12");
	}
	
	public static volatile PeriodHelper periodHelper = null;
	public static PeriodHelper getInstance() {
		if (periodHelper == null) {
			periodHelper = new PeriodHelper();
			return periodHelper;
		}
		return periodHelper;
	}

	/**
	 * creating BO object from form
	 * @param periodForm
	 * @return
	 * @throws Exception
	 */
	public List<Period> copyFromFormToBO(PeriodForm periodForm) throws Exception {
		String selectedClass[] = periodForm.getSelectedClasses();
		List<Period> periodList = new ArrayList<Period>();
		
		for (int x = 0; x < selectedClass.length; x++){
			Period period = new Period();
			ClassSchemewise classSchemewise = new ClassSchemewise();
			if(selectedClass[x]!= null && !selectedClass[x].isEmpty()){
				classSchemewise.setId(Integer.parseInt(selectedClass[x]));
			}
			if(x == 0){   //in edit option loading only one record. so one record should get updated and rest should be inserted in the table.   
				period.setId(periodForm.getId());
			}
			String startMins; 
			String endMins;
			if(periodForm.getStartMins() != null && !periodForm.getStartMins().isEmpty()){
				startMins = periodForm.getStartMins();
			}
			else{
				startMins = "00";
			}
			if(periodForm.getEndMins() != null && !periodForm.getEndMins().isEmpty()){
				endMins = periodForm.getEndMins();
			}
			else{
				endMins = "00";
			}			

			period.setStartTime(CommonUtil.getTime(periodForm.getStartHours(), startMins));
			period.setEndTime(CommonUtil.getTime(periodForm.getEndHours(), endMins));
			period.setPeriodName(periodForm.getPeriodName().trim());
			period.setIsActive(true);
			period.setClassSchemewise(classSchemewise);
			period.setCreatedBy(periodForm.getUserId());
			period.setCreatedDate(new Date());
			period.setModifiedBy(periodForm.getUserId());
			period.setLastModifiedDate(new Date());
			period.setSession(periodForm.getSession());
			periodList.add(period);
			
		}
		return periodList;
		
	}
	/**
	 * copying BO to TO for UI display
	 * @param periodList
	 * @return
	 * @throws Exception
	 */
	public List<PeriodTO> copyPeriodBOToTO(List<Period> periodList) throws Exception{
		List<PeriodTO> periodTOList = new ArrayList<PeriodTO>();
		PeriodTO periodTO;
		Period period;
		ClassSchemewiseTO classSchemewiseTO;
		CurriculumSchemeDurationTO curriculumSchemeDurationTO;
		ClassesTO classesTO;
		Iterator<Period> it = periodList.iterator();
		while(it.hasNext()){
			period = it.next();
			periodTO = new PeriodTO();
			classesTO = new ClassesTO();
			classSchemewiseTO = new ClassSchemewiseTO();
			curriculumSchemeDurationTO = new CurriculumSchemeDurationTO();
			periodTO.setId(period.getId());
			periodTO.setPeriodName(period.getPeriodName());
			if(period.getStartTime()!=null && !period.getStartTime().isEmpty() && period.getEndTime()!=null && !period.getEndTime().isEmpty()){
				periodTO.setName(period.getPeriodName()+"("+period.getStartTime().substring(0,5)+"-"+period.getEndTime().substring(0,5)+")");
				/*int st=Integer.parseInt(period.getStartTime().substring(0,2));
				int et=Integer.parseInt(period.getEndTime().substring(0,2));
				if(st<=12){
				}else{
					periodTO.setName(period.getPeriodName()+"("+period.getStartTime().substring(2,5)+"-"+period.getEndTime().substring(2,5)+")");
				}*/
			}
			if(period.getSession()!=null && !period.getSession().isEmpty()){
				periodTO.setSession(period.getSession()); 
			}
			periodTO.setStartTime(period.getStartTime().substring(0, 5));
			periodTO.setEndTime(period.getEndTime().substring(0, 5));
			if(period.getSession()!=null && period.getSession().equals("am")){
				periodTO.setSession("morning");
			}
			else if(period.getSession()!=null && period.getSession().equals("pm"))
				periodTO.setSession("afternoon");
			
			classSchemewiseTO.setId(period.getClassSchemewise().getId());
			classesTO.setClassName(period.getClassSchemewise().getClasses().getName());
			curriculumSchemeDurationTO.setAcademicYear(period.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear());
			classSchemewiseTO.setClassesTo(classesTO);
			classSchemewiseTO.setCurriculumSchemeDurationTO(curriculumSchemeDurationTO);
			periodTO.setClassSchemewiseTO(classSchemewiseTO);
			periodTOList.add(periodTO);
		}
		return periodTOList;
	}
		
}
