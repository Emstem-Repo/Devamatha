package com.kp.cms.handlers.attendance;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.actions.admin.StudentUploadPhotoAction;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.StuCocurrLeave;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.ExtraCocurricularLeaveEntryForm;
import com.kp.cms.helpers.attendance.CocurricularAttendnaceEntryHelper;
import com.kp.cms.helpers.attendance.ExtraCocurricularLeaveEntryHelper;
import com.kp.cms.helpers.attendance.PeriodHelper;
import com.kp.cms.to.attendance.ApproveLeaveTO;
import com.kp.cms.to.attendance.CocurricularAttendnaceEntryTo;
import com.kp.cms.to.attendance.PeriodTO;
import com.kp.cms.transactions.attandance.IExtraCocurricularLeaveEntryTransaction;
import com.kp.cms.transactionsimpl.attendance.ExtraCocurricularLeaveEntryTransactionImpl;
import com.kp.cms.utilities.CurrentAcademicYear;

public class ExtraCocurricularLeaveEntryHandler {
	private static volatile ExtraCocurricularLeaveEntryHandler extraCocurricularLeaveEntryHandler= null;
	private static final Log log = LogFactory.getLog(ExtraCocurricularLeaveEntryHandler.class);
	public static ExtraCocurricularLeaveEntryHandler getInstance()
	{
		if(extraCocurricularLeaveEntryHandler==null)
		{
			extraCocurricularLeaveEntryHandler = new ExtraCocurricularLeaveEntryHandler();
		}
		return extraCocurricularLeaveEntryHandler;
	}
	public Map<Date, CocurricularAttendnaceEntryTo> getCocurricularAttendanceMap(ExtraCocurricularLeaveEntryForm extraCocurricularLeaveEntryForm, String studentId, String courseId) throws Exception {
	    log.debug("call of getCocurricularAttendanceMap method in ExtraCocurricularLeaveEntryHandler.class");
	    IExtraCocurricularLeaveEntryTransaction transaction = new   ExtraCocurricularLeaveEntryTransactionImpl().getInstance();
	    Map<Date, CocurricularAttendnaceEntryTo> attendacePeriodMap = new HashMap<Date, CocurricularAttendnaceEntryTo>();
	    Student student = transaction.getStudentByStudentId(studentId,courseId); // get student with student Id
	    List<Period> periodList =  transaction.getPeriodListByClass(student.getClassSchemewise().getId()); 
	    List<PeriodTO> periodTOList = PeriodHelper.getInstance().copyPeriodBOToTO(periodList);
	    List<Object[]> attendanceObjectsList = new ArrayList<Object[]>();
		List<Object[]> duplicateList = new ArrayList<Object[]>();
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		String applicationLimit= "";
		try
		{
			Properties prop = new Properties();
			InputStream in = StudentUploadPhotoAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
	        prop.load(in);
	        applicationLimit=prop.getProperty("knowledgepro.extracurricular.attendace.application.attendace.date.limit");
		}
		catch (Exception e) {
			throw new ApplicationException(e);
			// TODO: handle exception
		}
		subjectMap  = transaction.getSubjectMap();
	    if(student!=null)
	    {
	    	Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			 if(year!=0){
				currentYear=year;
			 }
	    	String attendanceDuplicateQuery = ExtraCocurricularLeaveEntryHelper.getInstance().getAttendanceDuplicateQuery(extraCocurricularLeaveEntryForm,student.getId(),student.getClassSchemewise().getId(),applicationLimit);
			duplicateList = transaction.getDuplicateRecords(attendanceDuplicateQuery);
			String attendanceQuery =  ExtraCocurricularLeaveEntryHelper.getInstance().getAttendanceQuery(extraCocurricularLeaveEntryForm,student.getId(),student.getClassSchemewise().getId(),currentYear,applicationLimit);
			attendanceObjectsList = transaction.getAttendnaceListByDates(attendanceQuery);
			//list = CocurricularAttendnaceEntryHelper.getInstance().convertBolistTOtoList(attendanceObjectsList,cocurricularAttendnaceEntryForm,periodTOList,duplicateList);
			attendacePeriodMap= ExtraCocurricularLeaveEntryHelper.getInstance().getAttendancePeriodMap(attendanceObjectsList,extraCocurricularLeaveEntryForm,periodTOList,duplicateList,subjectMap);
	    }
	    log.debug("end of getCocurricularAttendanceMap method in ExtraCocurricularLeaveEntryHandler.class");
		return attendacePeriodMap;
	}
	public boolean saveCocurricularDetails(ExtraCocurricularLeaveEntryForm extraCocurricularLeaveEntryForm) throws Exception{
		log.debug("call of saveCocurricularDetails method in ExtraCocurricularLeaveEntryHandler.class");
		boolean isAdded = false;
		IExtraCocurricularLeaveEntryTransaction transaction = new   ExtraCocurricularLeaveEntryTransactionImpl().getInstance();
		List<StuCocurrLeave> cocurricularList = ExtraCocurricularLeaveEntryHelper.getInstance().createBoObject(extraCocurricularLeaveEntryForm);
		List<StuCocurrLeave> cocurricularListNew = new ArrayList<StuCocurrLeave>();
		if(extraCocurricularLeaveEntryForm.getApproveLeaveTOs()!=null)
		{
			List<ApproveLeaveTO> approveLeaveTOs = extraCocurricularLeaveEntryForm.getApproveLeaveTOs();
			Iterator<StuCocurrLeave> iterator = cocurricularList.iterator();
			List<Integer> dupCheckLis = new ArrayList<Integer>();
			/*
			 * checking duplicate id in StuCocurrLeave object and removing the duplicate objects
			 * */
			while(iterator.hasNext())
			{
				StuCocurrLeave cocurrLeave = iterator.next();
				if(!dupCheckLis.contains(cocurrLeave.getId()))
				{
					if(cocurrLeave.getId()!=0) // for new record id will be 0 no need of duplicate check
					{
						dupCheckLis.add(cocurrLeave.getId());
						
					}
					cocurricularListNew.add(cocurrLeave); // adding to new list without having any duplicate object
				}
			}
			
			isAdded = transaction.saveCocurricularLeaveDetails(cocurricularListNew,approveLeaveTOs,extraCocurricularLeaveEntryForm);
		}
		log.debug("end of saveCocurricularDetails method in ExtraCocurricularLeaveEntryHandler.class");
		return isAdded;
	}

}
