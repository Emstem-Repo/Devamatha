package com.kp.cms.helpers.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.MobileSMSCriteriaBO;
import com.kp.cms.forms.attendance.MobileSmsCriteriaForm;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.helpers.admission.StudentEditHelper;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.attendance.MobileSMSCriteriaTO;
import com.kp.cms.utilities.CommonUtil;

public class MobileSMSCriteriaHelper {
	private static Log log = LogFactory.getLog(MobileSMSCriteriaHelper.class);
	public static volatile MobileSMSCriteriaHelper mobileSMSCriteriaHelper = null;
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	public static MobileSMSCriteriaHelper getInstance() {
		if (mobileSMSCriteriaHelper == null) {
			mobileSMSCriteriaHelper = new MobileSMSCriteriaHelper();
			return mobileSMSCriteriaHelper;
		}
		return mobileSMSCriteriaHelper;
	}
	public MobileSMSCriteriaBO copyFormToBO(MobileSmsCriteriaForm mobileSmsCriteriaForm) throws Exception {
        log.debug("call of copyFormToBO method in mobileSMSCriteriaHelper.class");
		MobileSMSCriteriaBO mobileSMSCriteriaBO= new MobileSMSCriteriaBO();
		try 
		{
			if(mobileSmsCriteriaForm.getYear()!=null && !mobileSmsCriteriaForm.getYear().isEmpty())
			{
				mobileSMSCriteriaBO.setYear(mobileSmsCriteriaForm.getYear());
			}
			if(mobileSmsCriteriaForm.getClassSchemWiseID()!=null && !mobileSmsCriteriaForm.getClassSchemWiseID().isEmpty())
			{
				ClassSchemewise classSchemewise= new ClassSchemewise();
				classSchemewise.setId(Integer.parseInt(mobileSmsCriteriaForm.getClassSchemWiseID()));
				mobileSMSCriteriaBO.setClassSchemewise(classSchemewise);
			}
			if(mobileSmsCriteriaForm.getStartHours()!=null && !mobileSmsCriteriaForm.getStartHours().isEmpty() &&
					mobileSmsCriteriaForm.getStartMins()!=null && !mobileSmsCriteriaForm.getStartMins().isEmpty())
			{
				mobileSMSCriteriaBO.setSmsTime(CommonUtil.getTime(mobileSmsCriteriaForm.getStartHours(), mobileSmsCriteriaForm.getStartMins()));
			}
			if(mobileSmsCriteriaForm.getDisableSMS().equalsIgnoreCase("false"))
			{
				mobileSMSCriteriaBO.setIsSmsBlocked(Boolean.FALSE);
			}
			if(mobileSmsCriteriaForm.getDisableSMS().equalsIgnoreCase("true"))
			{
				mobileSMSCriteriaBO.setIsSmsBlocked(Boolean.TRUE);
				mobileSMSCriteriaBO.setSmsBlockStartDate
				(CommonUtil.ConvertStringToSQLDate(mobileSmsCriteriaForm.getFromDate()));
				mobileSMSCriteriaBO.setSmsBlockEndDate
				(CommonUtil.ConvertStringToSQLDate(mobileSmsCriteriaForm.getToDate()));
				
			}
			mobileSMSCriteriaBO.setCreatedBy(mobileSmsCriteriaForm.getUserId());
			mobileSMSCriteriaBO.setCreatedDate(new Date());
			mobileSMSCriteriaBO.setIsActive(Boolean.TRUE);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error in copyFormToBO in mobileSMSCriteriaHelper.class");
			log.error("Error is "+e.toString());
		}
        log.debug("end of copyFormToBO method in mobileSMSCriteriaHelper.class");
		return mobileSMSCriteriaBO;
	}
	public List<MobileSMSCriteriaTO> convertBOtoTO(
			List<MobileSMSCriteriaBO> boCriteriaBOlist) throws Exception{
		log.debug("call of convertBOtoTO method in mobileSMSCriteriaHelper.class");
		List<MobileSMSCriteriaTO> mobileSMSCriteriaTOList= new ArrayList<MobileSMSCriteriaTO>();
		try
		{
			Iterator iterator=boCriteriaBOlist.iterator();
			while (iterator.hasNext()) {
				MobileSMSCriteriaBO bo=(MobileSMSCriteriaBO) iterator.next();
				MobileSMSCriteriaTO to= new MobileSMSCriteriaTO();
				to.setId(bo.getId());
				to.setYear(bo.getYear());
				to.setCourseName(bo.getClassSchemewise().getClasses().getCourse().getName());
				to.setClassName(bo.getClassSchemewise().getClasses().getName());
				if(bo.getIsSmsBlocked())
				{
					to.setDisableSMS("true");
					to.setFromDate(CommonUtil.ConvertStringToDateFormat(
							CommonUtil.getStringDate(bo.getSmsBlockStartDate()),
							MobileSMSCriteriaHelper.SQL_DATEFORMAT,
							MobileSMSCriteriaHelper.FROM_DATEFORMAT));
					to.setToDate(CommonUtil.ConvertStringToDateFormat(
							CommonUtil.getStringDate(bo.getSmsBlockEndDate()),
							MobileSMSCriteriaHelper.SQL_DATEFORMAT,
							MobileSMSCriteriaHelper.FROM_DATEFORMAT));
					
				}
				else
				{
					to.setDisableSMS("false");
				}
				to.setSmsTime(bo.getSmsTime().toString());
				mobileSMSCriteriaTOList.add(to);
				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("Error in convertBOtoTO method in mobileSMSCriteriaHelper.class");
		}
		log.debug("end of convertBOtoTO method in mobileSMSCriteriaHelper.class");
		return mobileSMSCriteriaTOList;
	}
	public MobileSmsCriteriaForm populateDatetoForm(MobileSMSCriteriaBO criteriaBO,
			MobileSmsCriteriaForm mobileSmsCriteriaForm)throws Exception {
		log.debug("call of populateDatetoForm method in mobileSMSCriteriaHelper.class");
		try
		{
			mobileSmsCriteriaForm.setId(criteriaBO.getId());
			mobileSmsCriteriaForm.setYear(criteriaBO.getYear());
			List<CourseTO> courseList = CourseHandler.getInstance()
			.getCourses();
			mobileSmsCriteriaForm.setCourseList(courseList);
			String courseID=Integer.toString(criteriaBO.getClassSchemewise().getClasses().getCourse().getId());
			mobileSmsCriteriaForm.setCourseId(courseID);
			Map<Integer, String> classMap= new HashMap<Integer, String>();
			classMap = CommonAjaxHandler.getInstance().getClassesBySelectedCourse1(Integer.parseInt(courseID), Integer.parseInt(criteriaBO.getYear()));
			mobileSmsCriteriaForm.setClassMap(classMap);
			mobileSmsCriteriaForm.setClassSchemWiseID(Integer.toString(criteriaBO.getClassSchemewise().getId()));
			
			
			String time=criteriaBO.getSmsTime().toString();
			String timeH=time.substring(0,2);
			String timeM=time.substring(3,5);
			mobileSmsCriteriaForm.setStartHours(timeH);
			mobileSmsCriteriaForm.setStartMins(timeM);
			if(criteriaBO.getIsSmsBlocked())
			{
				mobileSmsCriteriaForm.setDisableSMS("true");
				mobileSmsCriteriaForm.setFromDate(CommonUtil.ConvertStringToDateFormat(
						CommonUtil.getStringDate(criteriaBO.getSmsBlockStartDate()),
						MobileSMSCriteriaHelper.SQL_DATEFORMAT,
						MobileSMSCriteriaHelper.FROM_DATEFORMAT));
				mobileSmsCriteriaForm.setToDate(CommonUtil.ConvertStringToDateFormat(
						CommonUtil.getStringDate(criteriaBO.getSmsBlockEndDate()),
						MobileSMSCriteriaHelper.SQL_DATEFORMAT,
						MobileSMSCriteriaHelper.FROM_DATEFORMAT));
			}
			else
			{
				mobileSmsCriteriaForm.setDisableSMS("false");
				
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("Error in populateDatetoForm method in mobileSMSCriteriaHelper.class");
		}
		log.debug("end of populateDatetoForm method in mobileSMSCriteriaHelper.class");
		return mobileSmsCriteriaForm;
	}
	public MobileSMSCriteriaBO copyFormToBOForUpdate(
			MobileSmsCriteriaForm mobileSmsCriteriaForm) throws Exception {
		log.debug("call of copyFormToBOForUpdate mehtod in mobileSMSCriteriaHelper.class");
		MobileSMSCriteriaBO criteriaBO=new MobileSMSCriteriaBO();
		try
		{
			if(mobileSmsCriteriaForm.getYear()!=null && !mobileSmsCriteriaForm.getYear().isEmpty())
			{
				criteriaBO.setYear(mobileSmsCriteriaForm.getYear());
			}
			if(mobileSmsCriteriaForm.getClassSchemWiseID()!=null && !mobileSmsCriteriaForm.getClassSchemWiseID().isEmpty())
			{
				ClassSchemewise classSchemewise= new ClassSchemewise();
				classSchemewise.setId(Integer.parseInt(mobileSmsCriteriaForm.getClassSchemWiseID()));
				criteriaBO.setClassSchemewise(classSchemewise);
			}
			if(mobileSmsCriteriaForm.getStartHours()!=null && !mobileSmsCriteriaForm.getStartHours().isEmpty() &&
					mobileSmsCriteriaForm.getStartMins()!=null && !mobileSmsCriteriaForm.getStartMins().isEmpty())
			{
				criteriaBO.setSmsTime(CommonUtil.getTime(mobileSmsCriteriaForm.getStartHours(), mobileSmsCriteriaForm.getStartMins()));
			}
			if(mobileSmsCriteriaForm.getDisableSMS().equalsIgnoreCase("false"))
			{
				criteriaBO.setIsSmsBlocked(Boolean.FALSE);
			}
			if(mobileSmsCriteriaForm.getDisableSMS().equalsIgnoreCase("true"))
			{
				criteriaBO.setIsSmsBlocked(Boolean.TRUE);
				criteriaBO.setSmsBlockStartDate
				(CommonUtil.ConvertStringToSQLDate(mobileSmsCriteriaForm.getFromDate()));
				criteriaBO.setSmsBlockEndDate
				(CommonUtil.ConvertStringToSQLDate(mobileSmsCriteriaForm.getToDate()));
				
			}
			if(mobileSmsCriteriaForm.getSmsCriteriaId()!=null && !mobileSmsCriteriaForm.getSmsCriteriaId().isEmpty())
			{
				criteriaBO.setId(Integer.parseInt(mobileSmsCriteriaForm.getSmsCriteriaId()));
			}
			criteriaBO.setIsActive(Boolean.TRUE);
			criteriaBO.setModifiedBy(mobileSmsCriteriaForm.getUserId());
			criteriaBO.setLastModifiedDate(new Date());
			
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("Error in copyFormToBOForUpdate mehtod in mobileSMSCriteriaHelper.class");
		}
		log.debug("end of copyFormToBOForUpdate mehtod in mobileSMSCriteriaHelper.class");
		
		return criteriaBO;
	}
	public List<MobileSMSCriteriaBO> copyAllClasstoBO(
			MobileSmsCriteriaForm mobileSmsCriteriaForm) throws Exception {
		log.debug("call of copyAllClasstoBO mehtod in mobileSMSCriteriaHelper.class"); 
		List<MobileSMSCriteriaBO> bolist= new ArrayList<MobileSMSCriteriaBO>();
		Map<Integer, String> classMap=new HashMap<Integer, String>();
		classMap=mobileSmsCriteriaForm.getClassMapforAll();
		Iterator iterator=classMap.entrySet().iterator();
		while(iterator.hasNext())
		{
			Map.Entry mapEntry = (Map.Entry) iterator.next();
			MobileSMSCriteriaBO criteriaBO=new MobileSMSCriteriaBO();
			ClassSchemewise classSchemewise= new ClassSchemewise();
			classSchemewise.setId(Integer.parseInt(mapEntry.getKey().toString()));
			criteriaBO.setClassSchemewise(classSchemewise);
			if(mobileSmsCriteriaForm.getYear()!=null && !mobileSmsCriteriaForm.getYear().isEmpty())
			{
				criteriaBO.setYear(mobileSmsCriteriaForm.getYear());
			}
			if(mobileSmsCriteriaForm.getStartHours()!=null && !mobileSmsCriteriaForm.getStartHours().isEmpty() &&
					mobileSmsCriteriaForm.getStartMins()!=null && !mobileSmsCriteriaForm.getStartMins().isEmpty())
			{
				criteriaBO.setSmsTime(CommonUtil.getTime(mobileSmsCriteriaForm.getStartHours(), mobileSmsCriteriaForm.getStartMins()));
			}
			if(mobileSmsCriteriaForm.getDisableSMS().equalsIgnoreCase("false"))
			{
				criteriaBO.setIsSmsBlocked(Boolean.FALSE);
			}
			if(mobileSmsCriteriaForm.getDisableSMS().equalsIgnoreCase("true"))
			{
				criteriaBO.setIsSmsBlocked(Boolean.TRUE);
				criteriaBO.setSmsBlockStartDate
				(CommonUtil.ConvertStringToSQLDate(mobileSmsCriteriaForm.getFromDate()));
				criteriaBO.setSmsBlockEndDate
				(CommonUtil.ConvertStringToSQLDate(mobileSmsCriteriaForm.getToDate()));
				
			}
			criteriaBO.setCreatedBy(mobileSmsCriteriaForm.getUserId());
			criteriaBO.setCreatedDate(new Date());
			criteriaBO.setIsActive(Boolean.TRUE);
			bolist.add(criteriaBO);
		}
		
		log.debug("end of copyAllClasstoBO mehtod in mobileSMSCriteriaHelper.class"); 
		
		
		return bolist;
	}
    
	
	
}
