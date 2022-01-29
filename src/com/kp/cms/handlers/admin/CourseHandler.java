/**
 * 
 */
package com.kp.cms.handlers.admin;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CourseToDepartment;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.FeeGroup;
import com.kp.cms.bo.hostel.HlRoomTypeFees;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.CourseForm;
import com.kp.cms.forms.hostel.RoomTypeForm;
import com.kp.cms.helpers.admin.CourseHelper;
import com.kp.cms.helpers.hostel.RoomTypeHelper;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.TermsConditionChecklistTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.fee.FeeGroupTO;
import com.kp.cms.transactions.admin.ICourseTransaction;
import com.kp.cms.transactions.hostel.IRoomTypeTransaction;
import com.kp.cms.transactionsimpl.admin.CourseTransactionImpl;
import com.kp.cms.transactionsimpl.hostel.RoomTypeTransactionImpl;
import com.kp.cms.transactionsimpl.studentfeedback.BlockBoImpl;
import com.kp.cms.utilities.CourseComparator;

public class CourseHandler {

	public static volatile CourseHandler courseHandler = null;
	public static final Log log = LogFactory.getLog(CourseHandler.class);

	public static CourseHandler getInstance() {
		if (courseHandler == null) {
			courseHandler = new CourseHandler();
			return courseHandler;
		}
		return courseHandler;
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map getSubjectGroupsByCourse() throws Exception {
		Map subjectGroupMap = new HashMap();
		log.error("ending of getSubjectGroupsByCourse method in CourseHandler");
		return subjectGroupMap;
	}

	/**
	 * add course method
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 * @throws Exception
	 */
	public boolean addCourse(CourseForm courseForm, String mode) throws Exception {
		ICourseTransaction iCourseTransaction = CourseTransactionImpl.getInstance();

		//original changed variables are used for edit option. in edit if the data is changed then, checking for duplicate
		
		Boolean originalCourseCodeNotChanged = false;
		Boolean originalCoursenameNotChanged = false;

		String courseName = "";
		String courseCode = "";
		String origCourseCode = "";
		String origCourseName = "";
		int origPogTypeId = 0;
		int origProgId = 0;
		int progTypeId = 0;
		int progId = 0;

		if(courseForm.getOrigProgTypeId()!= null && !courseForm.getOrigProgTypeId().isEmpty())
		{
			origPogTypeId = Integer.parseInt(courseForm.getOrigProgTypeId());
		}
		
		if(courseForm.getOrigProgId()!= null && !courseForm.getOrigProgId().isEmpty())
		{
			origProgId = Integer.parseInt(courseForm.getOrigProgId());
		}
		

		if(courseForm.getProgramId()!= null && !courseForm.getProgramId().isEmpty())
		{
			progId = Integer.parseInt(courseForm.getProgramId());
		}
		
		if(courseForm.getProgramTypeId()!= null && !courseForm.getProgramTypeId().isEmpty())
		{
			progTypeId = Integer.parseInt(courseForm.getProgramTypeId());
		}
		
		
		if(courseForm.getCourseName()!= null && !courseForm.getCourseName().isEmpty())
		{
			courseName = courseForm.getCourseName().trim();
		}
		if(courseForm.getCourseCode()!= null && !courseForm.getCourseCode().equals("")){
			courseCode = courseForm.getCourseCode().trim();
		}
		if(courseForm.getOrigCourseCode()!= null && !courseForm.getOrigCourseCode().equals("")){
			origCourseCode = courseForm.getOrigCourseCode().trim();
		}
		if(courseForm.getOrigCourseName()!= null && !courseForm.getOrigCourseName().equals("")){
			origCourseName = courseForm.getOrigCourseName().trim();
		}
		if (courseCode.equalsIgnoreCase(origCourseCode)) {
			originalCourseCodeNotChanged = true;
		}
		if ((courseName.equalsIgnoreCase(origCourseName)) && (progTypeId == origPogTypeId) &&  (progId == origProgId) ) {
			originalCoursenameNotChanged = true;
		}
		if (mode.equalsIgnoreCase("Add")) {
			originalCourseCodeNotChanged = false;
			originalCoursenameNotChanged = false;
		}
		
		//originalchanged variables are using for checking the duplication in edit. in edit no need to check th current record  
		
		if (!originalCoursenameNotChanged) {
			boolean courseNameExists = CourseHandler.getInstance().isCourseNameDuplicated(courseForm);
			if (courseNameExists) {
				throw new DuplicateException();
			}
		}
		if (!originalCourseCodeNotChanged) {
			boolean courseCodeExists = CourseHandler.getInstance().isCourseCodeDuplicated(courseForm.getCourseCode());
			if (courseCodeExists) {
				throw new DuplicateException();
			}
		}
		Course duplCourse = iCourseTransaction.isCourseToBeActivated(courseForm);
		if (duplCourse.getId() != 0) {
			courseForm.setDuplId(duplCourse.getId());
			throw new ReActivateException();
		}

		boolean isAdded = false;
		Course course = CourseHelper.getInstance().populateCourseDataFormForm(
				courseForm);
		
		if (mode.equals("Add")) {
			course.setCreatedDate(new Date());
			course.setLastModifiedDate(new Date());
			course.setCreatedBy(courseForm.getUserId());
			course.setModifiedBy(courseForm.getUserId());
		} else // edit
		{
			course.setLastModifiedDate(new Date());
			course.setModifiedBy(courseForm.getUserId());

		}

		isAdded = iCourseTransaction.addCourse(course, mode);
		log.error("ending of addCourse method in CourseHandler");
		return isAdded;
	}

	/**
	 * 
	 * @return list of courseTO objects, will be used in UI to dispaly.
	 * @throws Exception
	 */
	public List<CourseTO> getCourse(int id) throws Exception {
		ICourseTransaction iCourseTransaction = CourseTransactionImpl.getInstance();
		List<Course> courseList = iCourseTransaction.getCourse(id);
		List<CourseTO> courseToList = CourseHelper.getInstance().copyCourseBosToTos(courseList, id);
	//	Collections.sort(courseToList,new CourseComparator());
		log.error("ending of getCourse method in CourseHandler");
		return courseToList;
	}
	
	

	/**
	 * handler class method for delete
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 * @throws Exception
	 */

	public boolean deleteCourse(int id, Boolean activate, CourseForm courseForm) throws Exception {
		ICourseTransaction iCourseTransaction = CourseTransactionImpl.getInstance();
		boolean result = iCourseTransaction.deleteCourse(id, activate, courseForm);
		log.error("ending of deleteCourse method in CourseHandler");
		return result;
	}

	/**
	 * handler class method for delete
	 * @param courseForm
	 * @return
	 * @throws Exception
	 */
	public boolean isCourseNameDuplicated(CourseForm courseForm) throws Exception {
		ICourseTransaction iCourseTransaction = CourseTransactionImpl.getInstance();
		boolean result = iCourseTransaction.isCourseNameDuplcated(courseForm);
		log.error("ending of deleteCourse method in CourseHandler");
		return result;
	}

	/**
	 * course code duplication checking
	 * @param courseCode
	 * @return
	 * @throws Exception
	 */
	public boolean isCourseCodeDuplicated(String courseCode) throws Exception {
		ICourseTransaction iCourseTransaction = CourseTransactionImpl.getInstance();
		boolean result = iCourseTransaction.isCourseCodeDuplcated(courseCode);
		log.error("ending of deleteCourse method in CourseHandler");
		return result;
	}
	
	/**
	 * Used to get all the active courses
	 */
	public List<CourseTO> getActiveCourses()throws Exception{
		ICourseTransaction iCourseTransaction = CourseTransactionImpl.getInstance();
		List<Course> courseBoList=iCourseTransaction.getActiveCourses();
		if(courseBoList != null && !courseBoList.isEmpty() ){
			return CourseHelper.getInstance().populateCourseBOtoTO(courseBoList);
		}
		return null;
	}
	/**
	 * Used to get all the courses except admission related 'any'
	 */
	public List<CourseTO> getCourses()throws Exception{
		ICourseTransaction iCourseTransaction = CourseTransactionImpl.getInstance();
		List<Course> courseBoList=iCourseTransaction.getCourses();
		if(courseBoList != null && !courseBoList.isEmpty() ){
			return CourseHelper.getInstance().populateCourseBOtoTO(courseBoList);
		}
		return null;
	}
	public List<KeyValueTO> getCoursesKey()throws Exception{
		ICourseTransaction iCourseTransaction = CourseTransactionImpl.getInstance();
		List<Course> courseBoList=iCourseTransaction.getCourses();
		if(courseBoList != null && !courseBoList.isEmpty() ){
			return CourseHelper.getInstance().populateCourseBOtoTOKey(courseBoList);
		}
		return null;
	}
	
	/**
	 * @param parseInt
	 * @param year
	 * @return
	 */
	public List<TermsConditionChecklistTO> getTermsConditionCheckLists(
			int courseId, int year) throws Exception {
		ICourseTransaction iCourseTransaction = CourseTransactionImpl.getInstance();
		List<Course> courseList = iCourseTransaction.getCourse(courseId);
		List<TermsConditionChecklistTO> checkList = CourseHelper.getInstance().getTermsChekclists(courseList, courseId,year);
		return checkList;
	}
	
	public  List<CourseTO> getDepartments(CourseForm courseForm) throws Exception{
		ICourseTransaction iCourseTransaction = CourseTransactionImpl.getInstance();
		List<Department> courseList = iCourseTransaction.getDepartment();
		Map<Integer,Integer> cdMap=iCourseTransaction.getCourseDepartmentList(courseForm);
		List<CourseTO> deptlist=CourseHelper.getInstance().convertCourseBotoTo(courseList,cdMap);
	     return deptlist;
	}
	public boolean addAssignDept(CourseForm courseForm ) throws Exception{
		ICourseTransaction iCourseTransaction = CourseTransactionImpl.getInstance();
		boolean isAdded=false;
    	List<CourseToDepartment> bolist = CourseHelper.getInstance().convertFormToBos(courseForm);
    	if(!bolist.isEmpty()){
        isAdded = iCourseTransaction.saveDepts(bolist);
    	 }
        return isAdded;
    }
	public Map<Integer, String> getWorkLocationMap() throws Exception{
		List<EmployeeWorkLocationBO> locationBo = BlockBoImpl.getInstance().getEmpLocation();
		Map<Integer,String> map=null;
		if(locationBo!=null){
			map=new HashMap<Integer, String>();
			Iterator<EmployeeWorkLocationBO> iterator=locationBo.iterator();
			while (iterator.hasNext()) {
				EmployeeWorkLocationBO employeeWorkLocationBO = (EmployeeWorkLocationBO) iterator.next();
				map.put(employeeWorkLocationBO.getId(), employeeWorkLocationBO.getName());
			}
		}
		return map;
	}
}
