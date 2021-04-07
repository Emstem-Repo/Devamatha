package com.kp.cms.helpers.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmittedThrough;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CourseToDepartment;
import com.kp.cms.bo.admin.Currency;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.SeatAllocation;
import com.kp.cms.bo.admin.TermsConditionChecklist;
import com.kp.cms.forms.admin.CourseForm;
import com.kp.cms.to.admin.AdmittedThroughTO;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.CurrencyMasterTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.SeatAllocationTO;
import com.kp.cms.to.admin.TermsConditionChecklistTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.CommonUtil;

public class CourseHelper {
	public static volatile CourseHelper courseHelper = null;
	public static final Log log = LogFactory.getLog(CourseHelper.class);
	public static CourseHelper getInstance() {
		if (courseHelper == null) {
			courseHelper = new CourseHelper();
			return courseHelper;
		}
		return courseHelper;
	}

	/**
	 * 
	 * @param CourseForm
	 *            creates BO from courseForm
	 * courseBO object is used to save data to table
	 * @return Course BO object
	 */

	public Course populateCourseDataFormForm(CourseForm courseForm)	throws Exception {
		Course course = new Course();
		Program program = new Program();
		if (!courseForm.getCourseId().equals("")) {
			course.setId(Integer.parseInt(courseForm.getCourseId()));
		}

		course.setName(courseForm.getCourseName().trim());
		course.setCode(courseForm.getCourseCode().trim());
		if(courseForm.getDateTime()!=null)
			course.setDateTime(courseForm.getDateTime());
		if(course.getGeneralFee()!=null)
			course.setGeneralFee(courseForm.getGeneralFee());
		if(course.getCasteFee()!=null)
			course.setCasteFee(courseForm.getCasteFee());

		course.setCertificateCourseName(courseForm.getCertificateCourseName().trim());
		course.setCommencementDate(CommonUtil.ConvertStringToDate(courseForm.getCommencementDate()));
		Currency cur=new Currency();
		cur.setId(Integer.parseInt(courseForm.getCurrencyId()));
		course.setCurrencyId(cur);
		if ((courseForm.getIntApplicationFees() != null) && (!courseForm.getIntApplicationFees().equals(""))) {
			course.setApplicationFees(BigDecimal.valueOf(Double.parseDouble(courseForm.getIntApplicationFees())));
		}
		if(courseForm.getCourseMarksCard()!=null && !courseForm.getCourseMarksCard().isEmpty())
		{
			course.setCourseMarksCard(courseForm.getCourseMarksCard());
		}
		if(courseForm.getBankName()!=null && !courseForm.getBankName().isEmpty())
		{
			course.setBankName(courseForm.getBankName());
		}
		if(courseForm.getBankNameFull()!=null && !courseForm.getBankNameFull().isEmpty())
		{
			course.setBankNameFull(courseForm.getBankNameFull());
		}
		course.setIsActive(true);
		if(courseForm.getPayCode() != null && !courseForm.getPayCode().equals("")){
			course.setPayCode(courseForm.getPayCode().trim());
		}
		if ((courseForm.getAmount() != null) && (!courseForm.getAmount().equals(""))) {
			course.setAmount(BigDecimal.valueOf(Double.parseDouble(courseForm.getAmount())));
		}

		if ((courseForm.getIsAutonomous() == null) || (courseForm.getIsAutonomous().equals(""))){
			course.setIsAutonomous(false);
		}else {
			if (courseForm.getIsAutonomous().equalsIgnoreCase("yes")) {
				course.setIsAutonomous(true);
			} else {
				course.setIsAutonomous(false);
			}
		}

		if ((courseForm.getIsWorkExperienceRequired() == null)
				|| (courseForm.getIsWorkExperienceRequired().equals(""))){
			course.setIsWorkExperienceRequired(false);
		}else {
			if (courseForm.getIsWorkExperienceRequired()
					.equalsIgnoreCase("yes")) {
				course.setIsWorkExperienceRequired(true);
			} else {
				course.setIsWorkExperienceRequired(false);
			}
		}
		if ((courseForm.getIsWorkExpMandatory() == null)
				|| (courseForm.getIsWorkExpMandatory().equals(""))){
			course.setIsWorkExperienceMandatory(false);
		}else {
			if (courseForm.getIsWorkExpMandatory()
					.equalsIgnoreCase("true")) {
				course.setIsWorkExperienceMandatory(true);
			} else {
				course.setIsWorkExperienceMandatory(false);
			}
		}
		if ((courseForm.getBankIncludeSection() == null) || (courseForm.getBankIncludeSection().equals("")))
		{
			course.setBankIncludeSection(false);
		}else {
			if (courseForm.getBankIncludeSection().equalsIgnoreCase("true")) {
				course.setBankIncludeSection(true);
			} else {
				course.setBankIncludeSection(false);
			}
		}
		if ((courseForm.getIsAppearInOnline() == null)
				|| (courseForm.getIsAppearInOnline().equals(""))){
			course.setIsAppearInOnline(false);
		}else {
			if (courseForm.getIsAppearInOnline()
					.equalsIgnoreCase("true")) {
				course.setIsAppearInOnline(true);
			} else {
				course.setIsAppearInOnline(false);
			}
		}
		//added by smitha
		if ((courseForm.getIsApplicationProcessSms() == null)
				|| (courseForm.getIsApplicationProcessSms().equals(""))){
			course.setIsApplicationProcessSms(false);
		}else {
			if (courseForm.getIsApplicationProcessSms()
					.equalsIgnoreCase("true")) {
				course.setIsApplicationProcessSms(true);
			} else {
				course.setIsApplicationProcessSms(false);
			}
		}
		if ((courseForm.getIsDetailMarkPresent() == null)
				|| (courseForm.getIsDetailMarkPresent().equals(""))){
			course.setIsDetailMarksPrint(false);
		}else {
			if (courseForm.getIsDetailMarkPresent()
					.equalsIgnoreCase("yes")) {
				course.setIsDetailMarksPrint(true);
			} else {
				course.setIsDetailMarksPrint(false);
			}
		}
		
		if ((courseForm.getOnlyForApplication() == null)
				|| (courseForm.getOnlyForApplication().equals(""))){
			course.setOnlyForApplication(false);
		}else {
			if (courseForm.getOnlyForApplication()
					.equalsIgnoreCase("true")) {
				course.setOnlyForApplication(true);
			} else {
				course.setOnlyForApplication(false);
			}
		}

		if ((courseForm.getMaxIntake() == null)	|| (courseForm.getMaxIntake().equals(""))) {
			course.setMaxIntake(0);
		} else {
			course.setMaxIntake(Integer.parseInt(courseForm.getMaxIntake()));
		}
		course.setIsAided(Boolean.parseBoolean(courseForm.getAided()));
		List<SeatAllocationTO> list = courseForm.getSeatAllocationList();
		if(courseForm.getTotal() <= 0){  //seat allocation is not mandatory
			list.clear();
		}
		Iterator<SeatAllocationTO> iterator = list.iterator();

		Set<SeatAllocation> seatAllocSet = new HashSet<SeatAllocation>();

		while (iterator.hasNext()) {
			SeatAllocation seatAllocation = new SeatAllocation();
			AdmittedThrough admittedThrough = new AdmittedThrough();
			SeatAllocationTO seatalloc = (SeatAllocationTO) iterator.next();
			seatAllocation.setId(seatalloc.getId());
			seatAllocation.setNoOfSeats(seatalloc.getNoofSeats());
			admittedThrough.setId(seatalloc.getAdmittedThroughId());
			seatAllocation.setAdmittedThrough(admittedThrough);
			seatAllocSet.add(seatAllocation);
		}
		course.setSeatAllocations(seatAllocSet);
		program.setId(Integer.parseInt(courseForm.getProgramId()));
		course.setProgram(program);
		//added by giri
		EmployeeWorkLocationBO employeeWorkLocationBO=new EmployeeWorkLocationBO();
		employeeWorkLocationBO.setId(Integer.parseInt(courseForm.getLocationId()));
		course.setWorkLocation(employeeWorkLocationBO);
		if(courseForm.getNoOfMidSemAttempts()!=null && !courseForm.getNoOfMidSemAttempts().isEmpty()){
			course.setNoOfAttemtsMidSem(Integer.parseInt(courseForm.getNoOfMidSemAttempts()));
		}
		//end by giri
		log.error("ending of populateCourseDataFormForm method in CourseHelper");
		return course;
	}
	/**
	 * copying BO to TO for UI display
	 * @param courseList
	 * @param id
	 * @return
	 */
	public List<CourseTO> copyCourseBosToTos(List<Course> courseList, int id) {
		List<CourseTO> courseToList = new ArrayList<CourseTO>();
		Iterator<Course> iterator = courseList.iterator();
		Course course;
		CourseTO courseTO;

		while (iterator.hasNext()) {
			courseTO = new CourseTO();
			ProgramTO programTO = new ProgramTO();
			ProgramTypeTO programTypeTO = new ProgramTypeTO();
			CurrencyMasterTO currenctTo=new CurrencyMasterTO();
			course = (Course) iterator.next();
			courseTO.setLocationId(course.getWorkLocation().getId());
			courseTO.setId(course.getId());
			if (id == 0) {
				courseTO.setName(splitString(course.getName()));
			} else {
				courseTO.setName(course.getName());
			}
			if(course.getDateTime()!=null)
				courseTO.setDateTime(course.getDateTime());
			if(course.getGeneralFee()!=null)
				courseTO.setGeneralFee(course.getGeneralFee());
			if(course.getCasteFee()!=null)
				courseTO.setCasteFee(course.getCasteFee());

			courseTO.setCode(course.getCode());
			courseTO.setIsActive(course.getIsActive());
			courseTO.setAmount(course.getAmount());
			courseTO.setPayCode(course.getPayCode());
			courseTO.setWorkExpMandatory(course.getIsWorkExperienceMandatory());
			if(course.getBankIncludeSection()!=null)
			{
			courseTO.setBankIncludeSection(course.getBankIncludeSection());
			}if(course.getCommencementDate()!=null){
			courseTO.setCommencementDate(CommonUtil.formatDate(course.getCommencementDate(),"dd/MM/yyyy"));
			}if(course.getCurrencyId()!=null){
			currenctTo.setId(course.getCurrencyId().getId());
			courseTO.setCurrencyId(currenctTo);
			}if(course.getApplicationFees()!=null){
				courseTO.setIntApplicationFees(course.getApplicationFees());
			}
			courseTO.setAppearInOnline(course.getIsAppearInOnline());
			courseTO.setApplicationProcessSms(course.getIsApplicationProcessSms());
			if(course.getOnlyForApplication()!=null)
			courseTO.setOnlyForApplication(course.getOnlyForApplication());
			programTypeTO.setProgramTypeId(course.getProgram().getProgramType().getId());
			programTypeTO.setProgramTypeName(course.getProgram().getProgramType().getName());
			
			programTypeTO.setAgeFrom(String.valueOf(course.getProgram().getProgramType().getAgeFrom()));
			programTypeTO.setAgeTo(String.valueOf(course.getProgram().getProgramType().getAgeTo()));

			programTO.setId(course.getProgram().getId());
			programTO.setName(course.getProgram().getName());
			programTO.setIsMotherTongue(course.getProgram().getIsMotherTongue());
			programTO.setIsDisplayLanguageKnown(course.getProgram().getIsDisplayLanguageKnown());
			programTO.setIsHeightWeight(course.getProgram().getIsHeightWeight());
			programTO.setIsDisplayTrainingCourse(course.getProgram().getIsDisplayTrainingCourse());
			programTO.setIsAdditionalInfo(course.getProgram().getIsAdditionalInfo());
			programTO.setIsExtraDetails(course.getProgram().getIsExtraDetails());
			programTO.setIsSecondLanguage(course.getProgram().getIsSecondLanguage());
			programTO.setIsFamilyBackground(course.getProgram().getIsFamilyBackground());
			programTO.setIsLateralDetails(course.getProgram().getIsLateralDetails());
			programTO.setIsTransferCourse(course.getProgram().getIsTransferCourse());
			programTO.setIsEntranceDetails(course.getProgram().getIsEntranceDetails());
			programTO.setIsTCDetails(course.getProgram().getIsTCDetails());
			programTO.setIsExamCenterRequired(course.getProgram().getIsExamCenterRequired());
			programTO.setProgramTypeTo(programTypeTO);
			

			courseTO.setProgramTo(programTO);
			courseTO.setMaxInTake(course.getMaxIntake());
			if (course.getIsAutonomous().equals(true)) {
				courseTO.setIsAutonomous("Yes");
			} else {
				courseTO.setIsAutonomous("No");
			}
			if (course.getIsWorkExperienceRequired().equals(true)) {
				courseTO.setIsWorkExperienceRequired("Yes");
			} else {
				courseTO.setIsWorkExperienceRequired("No");
			}
			if (course.getIsDetailMarksPrint().equals(true)) {
				courseTO.setIsDetailMarkPrint("Yes");
			} else {
				courseTO.setIsDetailMarkPrint("No");
			}
			courseTO.setAided(String.valueOf(course.getIsAided()));
			Set<SeatAllocation> seatAllocSet = course.getSeatAllocations();
			Iterator<SeatAllocation> it = seatAllocSet.iterator();
			List<SeatAllocationTO> tempList = new ArrayList<SeatAllocationTO>();
			while (it.hasNext()) {
				SeatAllocationTO seatAllocationTo = new SeatAllocationTO();
				AdmittedThroughTO admittedThroughTO = new AdmittedThroughTO();
				SeatAllocation seatAlloc = (SeatAllocation) it.next();
				seatAllocationTo.setId(seatAlloc.getId());

				admittedThroughTO.setId(seatAlloc.getAdmittedThrough().getId());
				admittedThroughTO.setName(seatAlloc.getAdmittedThrough()
						.getName());
				seatAllocationTo.setAdmittedThroughTO(admittedThroughTO);

				seatAllocationTo.setAdmittedThroughId(seatAlloc
						.getAdmittedThrough().getId());
				seatAllocationTo.setCourseId(seatAlloc.getCourse().getId());
				seatAllocationTo.setNoofSeats(seatAlloc.getNoOfSeats());
				tempList.add(seatAllocationTo);
			}
			courseTO.setSeatAllocation(tempList);
			courseTO.setCertificateCourseName(course.getCertificateCourseName());
			if(course.getBankName()!=null && !course.getBankName().isEmpty()){
				courseTO.setBankName(course.getBankName());
			}
			if(course.getBankNameFull()!=null && !course.getBankNameFull().isEmpty()){
				courseTO.setBankNameFull(course.getBankNameFull());
			}
			if(course.getCourseMarksCard()!=null && !course.getCourseMarksCard().isEmpty()){
				courseTO.setCourseMarksCard(course.getCourseMarksCard());
			}
			if(course.getNoOfAttemtsMidSem()!=null && course.getNoOfAttemtsMidSem()!=0){
				courseTO.setNoOfMidsemAttempts(course.getNoOfAttemtsMidSem());
			}
			courseToList.add(courseTO);
		}
		log.error("ending of copyCourseBosToTos method in CourseHelper");
		return courseToList;
	}

	/**
	 * 
	 * @param This
	 *            method is used to split the string if it is long (For keeping
	 *            50 aplhabets)
	 * @return
	 */

	public String splitString(String value) {
		StringBuffer appendedvalue = new StringBuffer("");
		int length = value.length();

		String[] temp = new String[length];
		int begindex = 0, endindex = 20;
		int count = 0;

		while (true) {
			if (endindex < length) {
				temp[count] = value.substring(begindex, endindex);
				begindex = begindex + 20;
				endindex = endindex + 20;
				appendedvalue = appendedvalue.append(temp[count] + " ");

			} else {
				if (count == 0)
					temp[count] = value.substring(0, length);
				temp[count] = value.substring(begindex, length);
				appendedvalue = appendedvalue.append(temp[count]);
				break;
			}
			count++;
		}
		log.error("ending of splitString method in CourseHelper");
		return appendedvalue.toString();
	}
	
	/**
	 * Populates all the active course Bos to TO
	 */
	
	public List<CourseTO> populateCourseBOtoTO(List<Course> courseList){
		CourseTO courseTO;
		List<CourseTO> courseToList = new ArrayList<CourseTO>();
		Iterator<Course> iterator=courseList.iterator();
		while (iterator.hasNext()) {
			Course course = iterator.next();
			courseTO = new CourseTO();
			courseTO.setId(course.getId());
			courseTO.setName(course.getName()+"("+course.getCode()+")");
			courseToList.add(courseTO);
		}
		return courseToList;		
	}
	public List<KeyValueTO> populateCourseBOtoTOKey(List<Course> courseList){
		KeyValueTO courseTO;
		List<KeyValueTO> courseToList = new ArrayList<KeyValueTO>();
		Iterator<Course> iterator=courseList.iterator();
		while (iterator.hasNext()) {
			Course course = iterator.next();
			courseTO = new KeyValueTO();
			courseTO.setId(course.getId());
			courseTO.setDisplay(course.getName()+"("+course.getCode()+")");
			courseToList.add(courseTO);
		}
		return courseToList;		
	}

	/**
	 * @param courseList
	 * @param courseId
	 * @param year 
	 * @return
	 */
	public List<TermsConditionChecklistTO> getTermsChekclists(
			List<Course> courseList, int courseId, int year) {
		List<TermsConditionChecklistTO> checklistTos= new ArrayList<TermsConditionChecklistTO>();
		if(courseList!=null){		
			Iterator<Course> iterator=courseList.iterator();
			while (iterator.hasNext()) {
				Course course = iterator.next();
				// get all checklist entries
				checklistTos=getChecklistEntries(course,checklistTos,year);
			}
		}
		Collections.sort(checklistTos);
		return checklistTos;	
	}

	
	/**
	 * sets all dependent checklists
	 * @param course
	 * @param checklistTos
	 * @param year 
	 */
	private List<TermsConditionChecklistTO> getChecklistEntries(Course course,
			List<TermsConditionChecklistTO> checklistTos, int year) {
		if(course!= null && course.getTermsConditionChecklists()!=null){
			
			Iterator<TermsConditionChecklist> chkItr=course.getTermsConditionChecklists().iterator();
			while (chkItr.hasNext()) {
				TermsConditionChecklist checklist = (TermsConditionChecklist) chkItr.next();
				if(checklist.getIsActive()!=null && checklist.getIsActive()
						/*&& checklist.getYear()==year */){
					TermsConditionChecklistTO checkTo= new TermsConditionChecklistTO();
					checkTo.setId(checklist.getId());
					checkTo.setCourseId(course.getId());
					
					checkTo.setMandatory(checklist.getIsMandatory());
					checkTo.setActive(checklist.getIsActive());
					checkTo.setChecklistDescription(checklist.getChecklistDescription());
					checkTo.setCreatedBy(checklist.getCreatedBy());
					checkTo.setCreatedDate(checklist.getCreatedDate());
					checkTo.setModifiedBy(checklist.getModifiedBy());
					checkTo.setLastModifiedDate(checklist.getLastModifiedDate());
					checklistTos.add(checkTo);
				}
			}
			
		}
		return checklistTos;
	}
	public List<CourseTO> convertCourseBotoTo(List<Department> departmentList,Map<Integer,Integer> cdMap){
		CourseTO courseTO=null;
		List<CourseTO> courseToList = new ArrayList<CourseTO>();
		Iterator<Department> iterator=departmentList.iterator();
		while (iterator.hasNext()) {
			Department dept = iterator.next();
			courseTO = new CourseTO();
			if(cdMap.containsKey(dept.getId())){
			courseTO.setDeptId(dept.getId());
			courseTO.setDeptName(dept.getName());
			courseTO.setTempChecked1(true);
			courseTO.setAssignedDeptId(cdMap.get(dept.getId()));
			courseToList.add(courseTO);
			}else{
				courseTO.setDeptId(dept.getId());
				courseTO.setDeptName(dept.getName());
				courseToList.add(courseTO);
			}
		}
		return courseToList;		
	}
	
	public List<CourseToDepartment> convertFormToBos(CourseForm courseForm){
		CourseToDepartment bo=null;
		boolean isSelected=false;
		List<CourseToDepartment> boList = new ArrayList<CourseToDepartment>();
		Iterator<CourseTO> iterator=courseForm.getDeptList().iterator();
		while (iterator.hasNext()) {
			CourseTO to = iterator.next();
			bo = new CourseToDepartment();
			if(to.getChecked()!=null && to.getChecked().equalsIgnoreCase("on")){
				if(to.getAssignedDeptId()>0){
						bo.setId(to.getAssignedDeptId());
					}
					Department dept=new Department();
					Course c=new Course();
					c.setId(Integer.parseInt(courseForm.getCourseid()));
					dept.setId(to.getDeptId());
					bo.setCourse(c);
					bo.setDepartment(dept);
					bo.setCreatedBy(courseForm.getUserId());
					bo.setCreatedDate(new Date());
					bo.setIsActive(true);
					bo.setLastModifiedDate(new Date());
					bo.setModifiedBy(courseForm.getUserId());
					boList.add(bo);
					isSelected=true;
				
			}else if(to.getChecked()==null  && to.isTempChecked1()== true){
				if(to.getAssignedDeptId()>0){
					bo.setId(to.getAssignedDeptId());
				}
				Department dept=new Department();
				Course c=new Course();
				c.setId(Integer.parseInt(courseForm.getCourseid()));
				dept.setId(to.getDeptId());
				bo.setCourse(c);
				bo.setDepartment(dept);
				bo.setIsActive(false);
				bo.setLastModifiedDate(new Date());
				bo.setModifiedBy(courseForm.getUserId());
				boList.add(bo);
				isSelected=true;
		}
			if(isSelected==false){
				courseForm.setSelected(false);
			}
		}
		log.error("ending of convertFormToBos method in CourseHelper");
		return boList;		
	}
	
	public Map<Integer, String> getCourseMap(List<Course> courses) throws Exception {
		Map<Integer, String> courseMap = new HashMap<Integer, String>();
		Iterator<Course> it = courses.iterator();
		while(it.hasNext()) {
			Course course = it.next();
			courseMap.put(course.getId(), course.getName());
		}
		return courseMap;
	}
}
