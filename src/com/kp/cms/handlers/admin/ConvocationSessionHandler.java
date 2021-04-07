package com.kp.cms.handlers.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ConvocationCourse;
import com.kp.cms.bo.admin.ConvocationSession;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.forms.admin.ConvocationSessionForm;
import com.kp.cms.handlers.employee.ModifyEmployeeLeaveHandler;
import com.kp.cms.to.admin.ConvocationSessionTo;
import com.kp.cms.transactions.admin.IConvocationSessionTransaction;
import com.kp.cms.transactionsimpl.admin.ConvocationSessionTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class ConvocationSessionHandler {
	private static final Log log = LogFactory.getLog(ConvocationSessionHandler.class);
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	public static volatile ConvocationSessionHandler handler = null;
	private ConvocationSessionHandler(){
		
	}
	public static ConvocationSessionHandler getInstance(){
		if(handler == null){
			handler = new ConvocationSessionHandler();
			return handler;
		}
		return handler;
	}
	IConvocationSessionTransaction transaction = ConvocationSessionTransactionImpl.getInstance();
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getCourseMap() throws Exception{
		return transaction.getCourseMap();
	}
	/**
	 * @param convocationSessionForm
	 * @return
	 * @throws Exception
	 */
	public boolean addSessionDetails(
			ConvocationSessionForm convocationSessionForm) throws Exception{
		ConvocationSession session = new ConvocationSession();
		session.setDate(CommonUtil.ConvertStringToSQLDate(convocationSessionForm.getDate()));
		session.setAmPM(convocationSessionForm.getAmOrpm());
		session.setMaxGuestAllowed(Integer.parseInt(convocationSessionForm.getMaxGuest()));
		session.setPassAmount(new BigDecimal(convocationSessionForm.getPassAmount()));
		session.setCreatedBy(convocationSessionForm.getUserId());
		session.setModifiedBy(convocationSessionForm.getUserId());
		session.setCreatedDate(new Date());
		session.setLastModifiedDate(new Date());
		session.setIsActive(true);
		Set<ConvocationCourse> courses = new HashSet<ConvocationCourse>();
		for (int i = 0; i < convocationSessionForm.getCourseIds().length; i++) {
			if(convocationSessionForm.getCourseIds()[i] != null && !convocationSessionForm.getCourseIds()[i].trim().isEmpty()){
				Course course = new Course();
				course.setId(Integer.parseInt(convocationSessionForm.getCourseIds()[i]));
				ConvocationCourse convocationCourse = new ConvocationCourse();
				convocationCourse.setCourse(course);
				convocationCourse.setCreatedBy(convocationSessionForm.getUserId());
				convocationCourse.setModifiedBy(convocationSessionForm.getUserId());
				convocationCourse.setCreatedDate(new Date());
				convocationCourse.setLastModifiedDate(new Date());
				convocationCourse.setIsActive(true);
				courses.add(convocationCourse);
			}
		}
		session.setCourses(courses );
		return transaction.addDetails(session);
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public List<ConvocationSessionTo> getConvocationSessionList() throws Exception{
		List<ConvocationSessionTo> list = new ArrayList<ConvocationSessionTo>();
		List<ConvocationSession> boList = transaction.getConvocationSessionList();
		if(boList != null){
			Iterator<ConvocationSession> iterator = boList.iterator();
			while (iterator.hasNext()) {
				ConvocationSession bo = (ConvocationSession) iterator
						.next();
				ConvocationSessionTo to = new ConvocationSessionTo();
				to.setDate(CommonUtil.getStringDate(bo.getDate()));
				to.setAmOrpm(bo.getAmPM());
				to.setMaxGuest(String.valueOf(bo.getMaxGuestAllowed()));
				to.setPassAmount(bo.getPassAmount().toString());
				to.setId(bo.getId());
				list.add(to);
			}
		}
		return list;
	}
	/**
	 * @param convocationSessionForm
	 * @throws Exception
	 */
	public void setEditDetailsToForm(
			ConvocationSessionForm convocationSessionForm) throws Exception{
		ConvocationSession convocationSession = transaction.getConvocationSession(convocationSessionForm.getId());
		if(convocationSession != null){
			if(convocationSession.getDate() != null){
				String startDate = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(convocationSession.getDate()), ConvocationSessionHandler.SQL_DATEFORMAT,ConvocationSessionHandler.FROM_DATEFORMAT);
				convocationSessionForm.setDate(startDate);
			}
			convocationSessionForm.setAmOrpm(convocationSession.getAmPM());
			convocationSessionForm.setPassAmount(convocationSession.getPassAmount().toString());
			convocationSessionForm.setMaxGuest(String.valueOf(convocationSession.getMaxGuestAllowed()));
			if(convocationSession.getCourses() != null){
				Iterator<ConvocationCourse> iterator = convocationSession.getCourses().iterator();
				String[] courseIds = new String[convocationSession.getCourses().size()];
				int count=0;
				while (iterator.hasNext()) {
					ConvocationCourse convocationCourse = (ConvocationCourse) iterator.next();
					courseIds[count] = String.valueOf(convocationCourse.getCourse().getId());
				}
				convocationSessionForm.setCourseIds(courseIds);
			}
		}
	}
	/**
	 * @param convocationSessionForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteConvocationSession(ConvocationSessionForm convocationSessionForm) throws Exception {
		
		return transaction.deleteConvocationSession(convocationSessionForm.getId(),convocationSessionForm.getUserId());
	}
	/**
	 * @param convocationSessionForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkDuplicate(ConvocationSessionForm convocationSessionForm) throws Exception{
		
		return transaction.checkDuplicate(convocationSessionForm);
	}
	
}
