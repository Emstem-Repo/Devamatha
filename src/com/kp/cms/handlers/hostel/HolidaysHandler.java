package com.kp.cms.handlers.hostel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.EvaStudentFeedbackOpenConnection;
import com.kp.cms.bo.admin.HostelHolidaysBo;
import com.kp.cms.forms.admin.EvaStudentFeedbackOpenConnectionForm;
import com.kp.cms.forms.hostel.HolidaysForm;
import com.kp.cms.helpers.admin.EvaStudentFeedbackOpenConnectionHelper;
import com.kp.cms.helpers.hostel.HostelHolidaysHelper;
import com.kp.cms.to.admin.EvaStudentFeedbackOpenConnectionTo;
import com.kp.cms.to.hostel.HostelHolidaysTo;
import com.kp.cms.transactions.ajax.ICommonAjax;
import com.kp.cms.transactions.hostel.IHolidaysTransaction;
import com.kp.cms.transactionsimpl.ajax.CommonAjaxImpl;
import com.kp.cms.transactionsimpl.hostel.HolidaysTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class HolidaysHandler {
	IHolidaysTransaction iTransaction=HolidaysTransactionImpl.getInstance();
	/**
	 * instance()
	 */
	public static volatile HolidaysHandler holidaysHandler = null;

	public static HolidaysHandler getInstance() {
		if (holidaysHandler == null) {
			holidaysHandler = new HolidaysHandler();
		}
		return holidaysHandler;
	}
	/**
	 * get programsMap
	 * @return
	 */
	public Map<String, String> getPrograms()throws Exception {
		IHolidaysTransaction holidaysTransaction = HolidaysTransactionImpl.getInstance();
		Map<String, String> programMap = holidaysTransaction.getProgramsMap();
		programMap = (HashMap<String, String>) CommonUtil.sortMapByValue(programMap);
		return programMap;
	}
	/**
	 * to save hostel holidays details
	 * @param connectionForm
	 * @return
	 * @throws Exception
	 */
	public boolean submithostelHolidaysDetails( HolidaysForm holidaysForm) throws Exception{
		boolean isAdded = false;
			List<HostelHolidaysBo> boList = HostelHolidaysHelper.getInstance().copyFromFormToBO(holidaysForm);
			isAdded = iTransaction.saveHostelHolidays(boList);
		return isAdded;
	}
	/**
	 * get the hostel Holidays list and set it to HostelHolidaysTo
	 * @return
	 * @throws Exception
	 */
	public List<HostelHolidaysTo> getDetails()throws Exception {
		List<HostelHolidaysBo> holidaysBos = iTransaction.getHostelHolidaysList();
		List<HostelHolidaysTo> hostelHolidaysTosList = HostelHolidaysHelper.getInstance().copyBOToToList(holidaysBos);
		return hostelHolidaysTosList;
	}
	/**
	 * delete Hostel Holidays Details
	 * @param connectionForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteHolidaysDtails( HolidaysForm holidaysForm) throws Exception{
		boolean isDeleted = iTransaction.deleteHolidaysDetails(holidaysForm);
		return isDeleted;
	}
	/**
	 * to get hostel holidays details for editing
	 * @param connectionForm
	 * @throws Exception
	 */
	public  void getHostelHolidaysDetails( HolidaysForm holidaysForm) throws Exception{
		HostelHolidaysBo hostelHolidaysBo = iTransaction.getHostelHolidaysDetails(holidaysForm.getId());
		if(hostelHolidaysBo!=null){
			if(hostelHolidaysBo.getProgramId()!=null && !hostelHolidaysBo.getProgramId().toString().isEmpty()){
				String progId[] =  new String[1];
				progId[0] = Integer.toString(hostelHolidaysBo.getProgramId().getId());
				holidaysForm.setProgramsId(progId);
			}
			if(hostelHolidaysBo.getCourseId()!=null && !hostelHolidaysBo.getCourseId().toString().isEmpty()){
				String courseId[] =  new String[1];
				courseId[0] = Integer.toString(hostelHolidaysBo.getCourseId().getId());
				holidaysForm.setCoursesId(courseId);
			}
			if(hostelHolidaysBo.getHolidaysFrom()!=null && !hostelHolidaysBo.getHolidaysFrom().toString().isEmpty()){
				holidaysForm.setHolidaysFrom(formatDate(hostelHolidaysBo.getHolidaysFrom()));
			}
			if(hostelHolidaysBo.getHolidaysTo()!=null && !hostelHolidaysBo.getHolidaysTo().toString().isEmpty()){
				holidaysForm.setHolidaysTo(formatDate(hostelHolidaysBo.getHolidaysTo()));
			}
			if(hostelHolidaysBo.getHolidaysFromSession()!=null && !hostelHolidaysBo.getHolidaysFromSession().isEmpty()){
				holidaysForm.setHolidaysFromSession(hostelHolidaysBo.getHolidaysFromSession());
			}
			if(hostelHolidaysBo.getHolidaysToSession()!=null && !hostelHolidaysBo.getHolidaysToSession().isEmpty()){
				holidaysForm.setHolidaysToSession(hostelHolidaysBo.getHolidaysToSession());
			}
			holidaysForm.setHolidaysOrVacation(hostelHolidaysBo.getHolidaysOrVaction());
			if(hostelHolidaysBo.getDescription()!=null && !hostelHolidaysBo.getDescription().isEmpty()){
				holidaysForm.setDescription(hostelHolidaysBo.getDescription());
			}
			if(hostelHolidaysBo.getHostelId()!=null){
				holidaysForm.setHostelId(String.valueOf(hostelHolidaysBo.getHostelId().getId()));
			}
			if(hostelHolidaysBo.getBlockId()!=null){
				holidaysForm.setBlockId(String.valueOf(hostelHolidaysBo.getBlockId().getId()));
			}
			if(hostelHolidaysBo.getUnitId()!=null){
				holidaysForm.setUnitId(String.valueOf(hostelHolidaysBo.getUnitId().getId()));
			}
			holidaysForm.setFlag(true);
		}
	}
	/**
	 * format date to string date
	 * @param date
	 * @return
	 */
	
	public String formatDate(Date date){
		DateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
		String newDate=formatter.format(date);
		return newDate;
	}
	/**
	 * get CourseMap
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getCourseMap(String id)throws Exception {
		Map<Integer, String> programMap = iTransaction.getCourseMap(id);
		programMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(programMap);
		return programMap;
	}
	/**
	 * update the hostel holidays details
	 * @param connectionForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateHostelHolidaysDetails( HolidaysForm holidaysForm)throws Exception {
		HostelHolidaysBo boList = HostelHolidaysHelper.getInstance().copyFromholidaysFormToBO(holidaysForm);
		boolean isUpdated = iTransaction.updateHostelHolidaysDetails(boList);
		return isUpdated;
	}
	/**
	 * duplicate check
	 * @param connectionForm
	 * @return
	 * @throws Exception
	 */
	public boolean duplicateCheck( HolidaysForm holidaysForm) throws Exception{
		List<HostelHolidaysBo> hList=iTransaction.checkDuplicate(holidaysForm);
		boolean isCheckDuplicate = HostelHolidaysHelper.getInstance().checkDuplicateForUpdate(hList,holidaysForm);
		return isCheckDuplicate;
	}
	public boolean duplicateCheckForAdd( HolidaysForm holidaysForm) throws Exception{
		List<HostelHolidaysBo> hList=iTransaction.getHostelHolidaysList();
		boolean isCheckDuplicate = HostelHolidaysHelper.getInstance().checkDuplicate(hList,holidaysForm);
		return isCheckDuplicate;
	}
	/**
	 * get courseMap
	 * @return
	 */
	public Map<Integer, String> courseMap()throws Exception {
		IHolidaysTransaction holidaysTransaction = HolidaysTransactionImpl.getInstance();
		Map<Integer, String> courseMap = holidaysTransaction.getCoursemap();
		courseMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(courseMap);
		return courseMap;
	}
}

