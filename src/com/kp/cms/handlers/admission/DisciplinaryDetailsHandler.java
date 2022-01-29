package com.kp.cms.handlers.admission;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.swing.JApplet;

import com.ibm.icu.text.SimpleDateFormat;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlDisciplinaryDetails;
import com.kp.cms.bo.admin.HlLeave;
import com.kp.cms.bo.admin.HostelHolidaysBo;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentRemarks;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamStudentDetentionRejoinDetails;
import com.kp.cms.bo.exam.StudentPreviousClassHistory;
import com.kp.cms.bo.hostel.FineEntryBo;
import com.kp.cms.bo.hostel.HlStudentAttendance;
import com.kp.cms.bo.hostel.HostelExemptionBo;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.DisciplinaryDetailsForm;
import com.kp.cms.handlers.attendance.StudentAttendanceSummaryHandler;
import com.kp.cms.helpers.admission.DisciplinaryDetailsHelper;
import com.kp.cms.helpers.reports.StudentWiseAttendanceSummaryHelper;
import com.kp.cms.to.admin.EdnQualificationTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admission.CourseDetailsTO;
import com.kp.cms.to.admission.DisciplinaryDetailsTo;
import com.kp.cms.to.admission.RemarcksTO;
import com.kp.cms.to.attendance.PeriodTO;
import com.kp.cms.to.exam.MarksCardTO;
import com.kp.cms.to.hostel.FineEntryTo;
import com.kp.cms.to.hostel.HlDisciplinaryDetailsTO;
import com.kp.cms.to.hostel.HlStudentAttendanceTo;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.reports.StudentWiseSubjectSummaryTO;
import com.kp.cms.to.usermanagement.StudentAttendanceTO;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.reports.IStudentWiseAttendanceSummaryTransaction;
import com.kp.cms.transactionsimpl.admission.DisciplinaryDetailsImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.reports.StudentWiseAttendanceSummaryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.lowagie.tools.concat_pdf;

public class DisciplinaryDetailsHandler {
	DisciplinaryDetailsHelper helper = new DisciplinaryDetailsHelper();
	DisciplinaryDetailsImpl impl = new DisciplinaryDetailsImpl();
	private static volatile DisciplinaryDetailsHandler disciplinaryDetailsHandler = null;
	
	public static DisciplinaryDetailsHandler getInsatnce() {
		if(disciplinaryDetailsHandler == null) {
			disciplinaryDetailsHandler = new DisciplinaryDetailsHandler();
		}
		return disciplinaryDetailsHandler;
	}

	public Integer validate(String regNo) {
		return impl.getStudentId(regNo.trim());
	}

	public Integer validate1(String regNo, String AppNo) {
		return impl.getStudentId1(regNo.trim(),AppNo.trim());
	}
	public boolean checkStudentIsActive(DisciplinaryDetailsForm stForm) throws Exception{
		return impl.checkStudentIsActive(stForm);
	}
	
	
	public List<StudentTO> getSearchedStudents(DisciplinaryDetailsForm stForm)
	throws Exception {
				
		StringBuffer query = impl.getSerchedStudentsQuery(stForm);
		List<Student> studentlist=impl.getSerchedStudents(query);
		
		Integer year=0;
		if(stForm.getAcademicYear()!=null && !stForm.getAcademicYear().isEmpty())
			year=Integer.parseInt(stForm.getAcademicYear());
		
		List<ExamStudentDetentionRejoinDetails> examStudentDetentionRejoinDetails=impl.getExamStudentDetentionRejoinDetails(year);
		
		Map<String,ExamStudentDetentionRejoinDetails> detentionRejoinMap=new HashMap<String, ExamStudentDetentionRejoinDetails>();
		if(examStudentDetentionRejoinDetails!=null){
			Iterator<ExamStudentDetentionRejoinDetails> iterator=examStudentDetentionRejoinDetails.iterator();
			while(iterator.hasNext()){
				ExamStudentDetentionRejoinDetails details=iterator.next();
				if(details!=null){
					Student student=details.getStudent();
					if(student!=null && String.valueOf(student.getId())!=null &&!String.valueOf(student.getId()).isEmpty()){
						detentionRejoinMap.put(String.valueOf(student.getId()), details);
					}
				}
			}
		}
	List<Integer> studentPhotoList = impl.getSerchedStudentsPhotoList(stForm);
				

List<StudentTO> studenttoList = helper.convertStudentTOtoBO(studentlist,studentPhotoList,detentionRejoinMap,stForm);

return studenttoList;
}

	
	@SuppressWarnings({ "unchecked", "null" })
	public DisciplinaryDetailsForm getDisciplinaryDetails(
			DisciplinaryDetailsForm objForm, Integer studentId,
			HttpServletRequest request) throws Exception{
		objForm.setObjto(helper.convertBoTO(impl.getStudentDetails(studentId),request));
		objForm.setIsAddRemarks(impl.getIsRemarcks(Integer.parseInt(objForm.getUserId())));
		objForm.setListRemarcks(helper.convertBOToTORemarks(impl.getRemarcks(studentId)));
		objForm.setStudentId(Integer.toString(studentId));
		List<StudentWiseSubjectSummaryTO> summaryList=null;
		List<StudentWiseSubjectSummaryTO> summaryList2=null;
		HashMap map = impl.getClassID(Integer.parseInt(objForm.getStudentId()));
		Iterator itr = map.keySet().iterator();
		Integer key = null;
		String value = null;
		while (itr.hasNext()) {
			key = (Integer) itr.next();
			value = (String) map.get(key);
		}
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
		Map<Integer, Integer> orderMap = studentWiseAttendanceSummaryTransaction.getSubjectOrder(objForm.getObjto().getCourseId(),objForm.getObjto().getSemNo(),objForm.getObjto().getSemesterAcademicYear());
		//	summaryList = StudentAttendanceSummaryHandler.getInstance().getStudentSubjectWiseAttendanceListView(studentId,objForm,courseId);
		 summaryList = StudentAttendanceSummaryHandler.getInstance().getSubjectWiseAttendanceListView(studentId);
		 if(!CMSConstants.LINK_FOR_CJC){
			 objForm.setIsCjc(false);
			 List<StudentWiseSubjectSummaryTO> studentActivitywiseAttendanceToList=StudentAttendanceSummaryHandler.getInstance().getActivityWiseAttendanceList(studentId);
			 if(studentActivitywiseAttendanceToList!=null)
				 summaryList.addAll(studentActivitywiseAttendanceToList);
		 }
		 if(CMSConstants.LINK_FOR_CJC)
		 {
			 objForm.setIsCjc(true);
			// summaryList2 = StudentAttendanceSummaryHandler.getInstance().getStudentActivityAttendanceForStudentDetailView(studentId, objForm, String.valueOf(courseId),false);
			 summaryList2 = StudentAttendanceSummaryHandler.getInstance().getAdditionalSubjectWiseAttendanceListView(studentId);
		 }
		objForm.setListCourseDetails(helper.convertBoToCourseTO(summaryList, value, orderMap, objForm,studentId));
		if(CMSConstants.LINK_FOR_CJC)
		{
			objForm.setIsCjc(true);
			objForm.setListActivityDetails(helper.convertBoToCourseTOAdditional(summaryList2, value, orderMap, objForm,studentId));	
		}
		objForm.setListFees(helper.convertBoToFeesTO(impl.getFeeDetails(studentId)));
		List<EdnQualificationTO> eduList=helper.convertBotoTo(impl.getEducationDetailsByStudentId(studentId));
		objForm.setEduList(eduList);
		objForm.setRecommendedBy(objForm.getObjto().getRecommendedBy());
		List<ExamStudentDetentionRejoinDetails> examStudentDetentionRejoinDetails=impl.getStudentDetentionRejoinDetails(studentId);
		helper.convertBOtoForm(examStudentDetentionRejoinDetails, objForm);
		
		//raghu
		int classId = studentWiseAttendanceSummaryTransaction.getClassId(studentId);
		List dateList=impl.getDateList(studentId, classId);
		List sessionAttendanceList=impl.getSessionAttendanceList(studentId, classId);
		helper.convertBoToSessionAttendance(dateList,sessionAttendanceList,objForm);
		
		return objForm;
	}
/*
	public DisciplinaryDetailsForm getDisciplinaryDetailsSemester1(
			DisciplinaryDetailsForm objForm, Integer studentId, Integer previousSemester,
			HttpServletRequest request) throws Exception{
		
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();		
		objForm.setStudentId(Integer.toString(studentId));
			
		List<StudentWiseSubjectSummaryTO> summaryList=null;
	
		String ClassName = impl.getClassIDPrevious(studentId, previousSemester);		
		Map<Integer, Integer> orderMap = studentWiseAttendanceSummaryTransaction.getSubjectOrder(objForm.getObjto().getCourseId());
		summaryList = StudentAttendanceSummaryHandler.getInstance().getPreviousSubjectWiseAttendanceList(studentId, previousSemester);
				
		objForm.setListCourseDetails(helper.convertBoToCourseTO(summaryList, ClassName, orderMap, objForm, studentId));
	//	objForm.setPrevious("1");
		return objForm;
	}
	*/
	public List<CourseDetailsTO> getDisciplinaryDetailsSemester(
			DisciplinaryDetailsForm objForm, Integer studentId, Integer classId, Integer previousSemester,
			HttpServletRequest request) throws Exception{
		
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();		
		objForm.setStudentId(Integer.toString(studentId));
			
		List<StudentWiseSubjectSummaryTO> summaryList=null;
		String ClassName="";
		int previousSemAcademicYear=0;
		StudentPreviousClassHistory previousClassBO = impl.getClassIDPreviousView(studentId, classId);	
		//added for subject order
		if(previousClassBO!=null){
			if(previousClassBO.getClasses()!=null)
			ClassName=previousClassBO.getClasses().getName();
			
			if(previousClassBO.getAcademicYear()!=null)
				previousSemAcademicYear=previousClassBO.getAcademicYear();
		}
		
		Map<Integer, Integer> orderMap = studentWiseAttendanceSummaryTransaction.getSubjectOrder(objForm.getObjto().getCourseId(),previousSemester,previousSemAcademicYear);
		summaryList = StudentAttendanceSummaryHandler.getInstance().getPreviousSubjectWiseAttendanceList(studentId, previousSemester);
			
		//objForm.setPrevious("1");
		return (helper.convertBoToCourseTO(summaryList, ClassName, orderMap, objForm, studentId));
	}
	
	
	
	public void addRemarks(int studentId, String userid, String remarks) {
		Student objstudent = new Student();
		objstudent.setId(studentId);
		StudentRemarks objBo = new StudentRemarks(0, null, objstudent, userid,
				new Date(), userid, new Date(), remarks);
		impl.insert(objBo);

	}
	/**
	 * 
	 * @param attendanceSummaryForm
	 * @throws Exception 
	 * @returns the student absence period details information
	 */
	public List<PeriodTO> getAbsencePeriodDetails(DisciplinaryDetailsForm disciplinaryDetailsForm) throws Exception {
		boolean isSubjectAttendance = true;
		IStudentWiseAttendanceSummaryTransaction transaction = new StudentWiseAttendanceSummaryTransactionImpl();
		String absenceSearchCriteria = helper.getAbsenceSearchCriteria(disciplinaryDetailsForm);
		List<AttendanceStudent> attendanceStudentList = transaction.getAbsencePeriodDetails(absenceSearchCriteria);
		List<Integer> periodList=impl.getPeriodList(disciplinaryDetailsForm);
		return helper.populateAbsencePeriodInformations(attendanceStudentList, disciplinaryDetailsForm, isSubjectAttendance,periodList);
	}
	
	public RemarcksTO getEditRemarks(int remarksId) throws Exception{
		StudentRemarks objBo = impl.getRemarksEdit(remarksId);
		return helper.convertBOToTOEditRemarks(objBo);
	}
	
	public void updateRemarks(int remarksId, String remarksName,String userId) throws Exception {
		impl.update( remarksId,  remarksName,userId);

	}
	/**
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public List<StudentAttendanceTO> getAttendanceDataByStudent(String studentId,DisciplinaryDetailsForm objForm) throws Exception {
		String query=StudentWiseAttendanceSummaryHelper.getInsatnce().getAttendanceDataByStudentQuery(studentId);
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<AttendanceStudent> attList=transaction.getDataForQuery(query);
		String periodQuery=StudentWiseAttendanceSummaryHelper.getInsatnce().getPeriodsForStudent(studentId);
		List<String> periodList=transaction.getDataForQuery(periodQuery);
		Map<String,Integer> posMap=StudentWiseAttendanceSummaryHelper.getInsatnce().getPositionsForPeriod(periodList);
		objForm.setPeriodNameList(periodList);
		return DisciplinaryDetailsHelper.getInsatnce().convertAttendanceStudentBotoTo(attList,posMap,studentId,objForm);
	}
	/**
	 * to check whether the student is final year or not
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public boolean getStudentFinalYearStatus(DisciplinaryDetailsForm objForm)throws Exception{
		boolean finalYear=impl.getCourseCompletionStatus(objForm.getStudentId());
		return finalYear;
	}
	/**
	 * to check whether the student is completed course or not
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public boolean getCourseCompletionStatus(String studentId)throws Exception{
			String query=DisciplinaryDetailsHelper.getInsatnce().createQuery(studentId);
			boolean status=impl.getStatusOfStudent(query);
		return status;
	}
	/**
	 * get Last exam name, month and year
	 * @param SupSemList
	 * @param SemExamListTo
	 * @param objForm
	 * @throws Exception
	 */
	public void getLastExamNameAndMonthAndYear(List<MarksCardTO> SupSemList,List<MarksCardTO> SemExamListTo,DisciplinaryDetailsForm objForm)throws Exception{
		String examName=null;
		int month=0;
		int year=0;
		List<Integer> examDefIds=new ArrayList<Integer>();
		if(SupSemList!=null && !SupSemList.isEmpty()){
			Iterator<MarksCardTO> iterator =SupSemList.iterator();
			while (iterator.hasNext()) {
				MarksCardTO marksCardTO = (MarksCardTO) iterator.next();
				examDefIds.add(marksCardTO.getExamDefId());
			}
		}
		if(SemExamListTo!=null && !SemExamListTo.isEmpty()){
			Iterator<MarksCardTO> iterator =SemExamListTo.iterator();
			while (iterator.hasNext()) {
				MarksCardTO marksCardTO = (MarksCardTO) iterator.next();
				examDefIds.add(marksCardTO.getExamDefId());
			}
		}
		List<ExamDefinitionBO> examDefinitionBOs=null;
		if(examDefIds!=null && !examDefIds.isEmpty()){
			examDefinitionBOs=impl.getExamDefinationBo(examDefIds);
		}
		if(examDefinitionBOs!=null && !examDefinitionBOs.isEmpty()){
			Iterator<ExamDefinitionBO> iterator=examDefinitionBOs.iterator();
			while (iterator.hasNext()) {
				ExamDefinitionBO examDefinitionBO = (ExamDefinitionBO) iterator.next();
				int month1=Integer.parseInt(examDefinitionBO.getMonth());
				int year1=examDefinitionBO.getYear();
				if((month+(year*12))<=((month1+1)+(year1*12))){
					month=month1+1;
					year=year1;
					examName=examDefinitionBO.getName();
				}
			}
		}
		
			String monthyear=CommonUtil.getMonthForNumber(month)+"/"+year;
			objForm.setLastExamName(examName);
			objForm.setExamMonthYear(monthyear);
	}
	public void getHostelDetailsForStudent(DisciplinaryDetailsForm objForm)throws Exception{
		HlAdmissionBo hlAdmissionBo=impl.getHostelDetailsForStudent(objForm.getStudentId());
		if(hlAdmissionBo!=null){
			objForm.setCheckHostelAdmission("yes");
		
		}else{
			objForm.setCheckHostelAdmission("no");
		}
	}
	private Map<String, Set<java.sql.Date>> getHolidaysMap(	Date date, List<HostelHolidaysBo> hostelHolidaysBoList)throws Exception {
		Map<String, Set<java.sql.Date>> map=new HashMap<String, Set<java.sql.Date>>();
		java.sql.Date checkedDate=new java.sql.Date(date.getTime());
		if(hostelHolidaysBoList!=null && !hostelHolidaysBoList.isEmpty()){
			Iterator<HostelHolidaysBo> iterator=hostelHolidaysBoList.iterator();
			while (iterator.hasNext()) {
				HostelHolidaysBo hostelHolidaysBo= (HostelHolidaysBo) iterator.next();
				java.sql.Date leaveFrom=new java.sql.Date(hostelHolidaysBo.getHolidaysFrom().getTime());
				java.sql.Date leaveTo=new java.sql.Date(hostelHolidaysBo.getHolidaysTo().getTime());
				Set<java.sql.Date> list=null;
				Set<java.sql.Date> list1=null;
				if(leaveFrom.compareTo(leaveTo)==0 && leaveFrom.compareTo(checkedDate)>=0){
					if(hostelHolidaysBo.getHolidaysFromSession().equalsIgnoreCase("morning") && hostelHolidaysBo.getHolidaysToSession().equalsIgnoreCase("morning")){
						if(map.containsKey("morning")){
							list=map.remove("morning");
						}else{
							list=new HashSet<java.sql.Date>();
						}
						list.add(leaveFrom);
						map.put("morning", list);
					}else if(hostelHolidaysBo.getHolidaysFromSession().equalsIgnoreCase("evening") && hostelHolidaysBo.getHolidaysToSession().equalsIgnoreCase("evening")){
						if(map.containsKey("evening")){
							list=map.remove("evening");
						}else{
							list=new HashSet<java.sql.Date>();
						}
						list.add(leaveFrom);
						map.put("evening", list);
					}else{
						if(map.containsKey("morning")){
							list=map.remove("morning");
						}else{
							list=new HashSet<java.sql.Date>();
						}
						list.add(leaveFrom);
						map.put("morning", list);
						if(map.containsKey("evening")){
							list1=map.remove("evening");
						}else{
							list=new HashSet<java.sql.Date>();
						}
						if(leaveTo.compareTo(checkedDate)>=0){
							list1.add(leaveTo);
							map.put("evening", list1);
						}
					}
				}else{
					if(hostelHolidaysBo.getHolidaysFromSession().equalsIgnoreCase("morning")){
						if(map.containsKey("morning")){
							if(leaveFrom.compareTo(checkedDate)>=0){
								list=map.remove("morning");
								list.add(leaveFrom);
								map.put("morning", list);
							}
							
						}else{
							list=new HashSet<java.sql.Date>();
							list.add(leaveFrom);
							map.put("morning", list);
						}
						
						if(map.containsKey("evening")){
							if(leaveFrom.compareTo(checkedDate)>=0){
								list1=map.remove("evening");
								list1.add(leaveFrom);
								map.put("evening", list1);
							}
						}else{
							list1=new HashSet<java.sql.Date>();
							list1=new HashSet<java.sql.Date>();
							list1.add(leaveFrom);
							map.put("evening", list1);
						}
						
					}else{
						if(map.containsKey("evening")){
							if(leaveFrom.compareTo(checkedDate)>=0){
								list1=map.remove("evening");
								list1.add(leaveFrom);
								map.put("evening", list1);
							}
						}else{
							list1=new HashSet<java.sql.Date>();
							list1.add(leaveFrom);
							map.put("evening", list1);
						}
					}
					if(hostelHolidaysBo.getHolidaysToSession().equalsIgnoreCase("morning")){
						if(map.containsKey("morning")){
							if(leaveTo.compareTo(checkedDate)>=0){
								list=map.remove("morning");
								list.add(leaveTo);
								map.put("morning", list);
							}
						}else{
							list=new HashSet<java.sql.Date>();
							list.add(leaveTo);
							map.put("morning", list);
						}
					}else{
						if(map.containsKey("morning")){
							if(leaveTo.compareTo(checkedDate)>=0){
								list=map.remove("morning");
								list.add(leaveTo);
								map.put("morning", list);
							}
						}else{
							list=new HashSet<java.sql.Date>();
							list.add(leaveTo);
							map.put("morning", list);
						}
						if(map.containsKey("evening")){
							if(leaveTo.compareTo(checkedDate)>=0){
								list1=map.remove("evening");
								list1.add(leaveTo);
								map.put("evening", list1);
							}
						}else{
							list1=new HashSet<java.sql.Date>();
							list1.add(leaveTo);
							map.put("evening", list1);

						}
					}
					leaveFrom=new java.sql.Date(getNextDate(leaveFrom).getTime());
					while (leaveFrom.compareTo(leaveTo)<0) {

						if(map.containsKey("morning")){
							if(leaveFrom.compareTo(checkedDate)>=0){
								list=map.remove("morning");
								list.add(leaveFrom);
								map.put("morning", list);
							}
						}else{
							list=new HashSet<java.sql.Date>();
						}
						if(map.containsKey("evening")){
							if(leaveFrom.compareTo(checkedDate)>=0){
								list1=map.remove("evening");
								list1.add(leaveFrom);
								map.put("evening", list1);
							}
						}else{
							list1=new HashSet<java.sql.Date>();
							list1.add(leaveFrom);
							map.put("evening", list1);
						}
						leaveFrom=new java.sql.Date(getNextDate(leaveFrom).getTime());
					}
				}
			}
		}
		return map;
	}

	private Map<String, Set<java.sql.Date>> getLeaveMap(List<HlLeave> hlLeaves)throws Exception {
		Map<String, Set<java.sql.Date>> map=new HashMap<String, Set<java.sql.Date>>();
		if(hlLeaves!=null && !hlLeaves.isEmpty()){
			Iterator<HlLeave> iterator=hlLeaves.iterator();
			while (iterator.hasNext()) {
				HlLeave hlLeave= (HlLeave) iterator.next();
				java.sql.Date leaveFrom=new java.sql.Date(hlLeave.getLeaveFrom().getTime());
				java.sql.Date leaveTo=new java.sql.Date(hlLeave.getLeaveTo().getTime());
				Set<java.sql.Date> list=null;
				Set<java.sql.Date> list1=null;
				if(leaveFrom.compareTo(leaveTo)==0){
					if(hlLeave.getFromSession().equalsIgnoreCase("morning") && hlLeave.getToSession().equalsIgnoreCase("morning")){
						if(map.containsKey("morning")){
							list=map.remove("morning");
						}else{
							list=new HashSet<java.sql.Date>();
						}
						list.add(leaveFrom);
						map.put("morning", list);
					}else if(hlLeave.getFromSession().equalsIgnoreCase("evening") && hlLeave.getToSession().equalsIgnoreCase("evening")){
						if(map.containsKey("evening")){
							list=map.remove("evening");
						}else{
							list=new HashSet<java.sql.Date>();
						}
						list.add(leaveFrom);
						map.put("evening", list);
					}else{
						if(map.containsKey("morning")){
							list=map.remove("morning");
						}else{
							list=new HashSet<java.sql.Date>();
						}
						list.add(leaveFrom);
						map.put("morning", list);
						if(map.containsKey("evening")){
							list1=map.remove("evening");
						}else{
							list1=new HashSet<java.sql.Date>();
						}
						list1.add(leaveTo);
						map.put("evening", list1);
					}
				}else{
					if(hlLeave.getFromSession().equalsIgnoreCase("morning")){
						if(map.containsKey("morning")){
							list=map.remove("morning");
						}else{
							list=new HashSet<java.sql.Date>();
						}
						list.add(leaveFrom);
						map.put("morning", list);
						if(map.containsKey("evening")){
							list1=map.remove("evening");
						}else{
							list1=new HashSet<java.sql.Date>();
						}
						list1.add(leaveFrom);
						map.put("evening", list1);
					}else{
						if(map.containsKey("evening")){
							list1=map.remove("evening");
						}else{
							list1=new HashSet<java.sql.Date>();
						}
						list1.add(leaveFrom);
						map.put("evening", list1);
					}
					if(hlLeave.getToSession().equalsIgnoreCase("morning")){
						if(map.containsKey("morning")){
							list=map.remove("morning");
						}else{
							list=new HashSet<java.sql.Date>();
						}
						list.add(leaveTo);
						map.put("morning", list);
					}else{
						if(map.containsKey("morning")){
							list=map.remove("morning");
						}else{
							list=new HashSet<java.sql.Date>();
						}
						list.add(leaveTo);
						map.put("morning", list);
						if(map.containsKey("evening")){
							list1=map.remove("evening");
						}else{
							list1=new HashSet<java.sql.Date>();
						}
						list1.add(leaveTo);
						map.put("evening", list1);
					}
					leaveFrom=new java.sql.Date(getNextDate(leaveFrom).getTime());
					while (leaveFrom.compareTo(leaveTo)<0) {

						if(map.containsKey("morning")){
							list=map.remove("morning");
						}else{
							list=new HashSet<java.sql.Date>();
						}
						list.add(leaveFrom);
						map.put("morning", list);
						if(map.containsKey("evening")){
							list1=map.remove("evening");
						}else{
							list1=new HashSet<java.sql.Date>();
						}
						list1.add(leaveFrom);
						map.put("evening", list1);
						leaveFrom=new java.sql.Date(getNextDate(leaveFrom).getTime());
					}
				}
			}
		}
		return map;
	}

	public Date getNextDate(Date date)throws Exception{
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		c.add(Calendar.DATE, 1);
		return c.getTime();
	}
	public Date getPreviousDate(Date date)throws Exception{
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		c.add(Calendar.DATE, -1);
		return c.getTime();
	}
	
	
	private Map<String, Set<java.sql.Date>> getExemptionMap(List<HostelExemptionBo> exempList)throws Exception {
		Map<String, Set<java.sql.Date>> map=new HashMap<String, Set<java.sql.Date>>();
		if(exempList!=null && !exempList.isEmpty()){
			Iterator<HostelExemptionBo> iterator=exempList.iterator();
			while (iterator.hasNext()) {
				HostelExemptionBo bo= (HostelExemptionBo) iterator.next();
				java.sql.Date exempFrom=new java.sql.Date(bo.getFromDate().getTime());
				java.sql.Date exempTo=new java.sql.Date(bo.getToDate().getTime());
				Set<java.sql.Date> list=null;
				Set<java.sql.Date> list1=null;
				if(exempFrom.compareTo(exempTo)==0){
					if(bo.getFromSession().equalsIgnoreCase("morning") && bo.getToSession().equalsIgnoreCase("morning")){
						if(map.containsKey("morning")){
							list=map.remove("morning");
						}else{
							list=new HashSet<java.sql.Date>();
						}
						list.add(exempFrom);
						map.put("morning", list);
					}else if(bo.getFromSession().equalsIgnoreCase("evening") && bo.getToSession().equalsIgnoreCase("evening")){
						if(map.containsKey("evening")){
							list=map.remove("evening");
						}else{
							list=new HashSet<java.sql.Date>();
						}
						list.add(exempFrom);
						map.put("evening", list);
					}else{
						if(map.containsKey("morning")){
							list=map.remove("morning");
						}else{
							list=new HashSet<java.sql.Date>();
						}
						list.add(exempFrom);
						map.put("morning", list);
						if(map.containsKey("evening")){
							list1=map.remove("evening");
						}else{
							list1=new HashSet<java.sql.Date>();
						}
						list1.add(exempTo);
						map.put("evening", list1);
					}
				}else{
					if(bo.getFromSession().equalsIgnoreCase("morning")){
						if(map.containsKey("morning")){
							list=map.remove("morning");
						}else{
							list=new HashSet<java.sql.Date>();
						}
						list.add(exempFrom);
						map.put("morning", list);
						if(map.containsKey("evening")){
							list1=map.remove("evening");
						}else{
							list1=new HashSet<java.sql.Date>();
						}
						list1.add(exempFrom);
						map.put("evening", list1);
					}else{
						if(map.containsKey("evening")){
							list1=map.remove("evening");
						}else{
							list1=new HashSet<java.sql.Date>();
						}
						list1.add(exempFrom);
						map.put("evening", list1);
					}
					if(bo.getToSession().equalsIgnoreCase("morning")){
						if(map.containsKey("morning")){
							list=map.remove("morning");
						}else{
							list=new HashSet<java.sql.Date>();
						}
						list.add(exempTo);
						map.put("morning", list);
					}else{
						if(map.containsKey("morning")){
							list=map.remove("morning");
						}else{
							list=new HashSet<java.sql.Date>();
						}
						list.add(exempTo);
						map.put("morning", list);
						if(map.containsKey("evening")){
							list1=map.remove("evening");
						}else{
							list1=new HashSet<java.sql.Date>();
						}
						list1.add(exempTo);
						map.put("evening", list1);
					}
					exempFrom=new java.sql.Date(getNextDate(exempFrom).getTime());
					while (exempFrom.compareTo(exempTo)<0) {

						if(map.containsKey("morning")){
							list=map.remove("morning");
						}else{
							list=new HashSet<java.sql.Date>();
						}
						list.add(exempFrom);
						map.put("morning", list);
						if(map.containsKey("evening")){
							list1=map.remove("evening");
						}else{
							list1=new HashSet<java.sql.Date>();
						}
						list1.add(exempFrom);
						map.put("evening", list1);
						exempFrom=new java.sql.Date(getNextDate(exempFrom).getTime());
					}
				}
			}
		}
		return map;
	}
	public void getHostelDetails(String studentId,HttpServletRequest request)throws Exception{
		HlAdmissionBo hlAdmissionBo=impl.getHostelDetailsForStudent(studentId);
		String hostelId=null;
		String blockId=null;
		String unitId=null;
		DisciplinaryDetailsTo disciplinaryDetailsTo=new DisciplinaryDetailsTo();
			if(hlAdmissionBo.getRoomId()!=null){ 
				disciplinaryDetailsTo.setRoomNo(hlAdmissionBo.getRoomId().getName());
			}
			if(hlAdmissionBo.getBedId()!=null){
				disciplinaryDetailsTo.setBedNo(hlAdmissionBo.getBedId().getBedNo());
			}
			
			if(hlAdmissionBo.getRoomId()!=null && hlAdmissionBo.getRoomId().getHlBlock()!=null
					&& hlAdmissionBo.getRoomId().getHlUnit()!=null){
				blockId=String.valueOf(hlAdmissionBo.getRoomId().getHlBlock().getId());
				disciplinaryDetailsTo.setBlock( hlAdmissionBo.getRoomId().getHlBlock().getName());
				unitId=String.valueOf(hlAdmissionBo.getRoomId().getHlUnit().getId());
				disciplinaryDetailsTo.setUnit(hlAdmissionBo.getRoomId().getHlUnit().getName());
			}
			if(hlAdmissionBo.getHostelId()!=null){
				hostelId=String.valueOf(hlAdmissionBo.getHostelId().getId());
				disciplinaryDetailsTo.setHostelName(hlAdmissionBo.getHostelId().getName());
			}
			request.setAttribute("HDetails", disciplinaryDetailsTo);
			// hostel leave list
			List<HlLeave> hlLeaveList=null;
			List<HostelHolidaysBo> hostelHolidaysBoList=null;
			if(hlAdmissionBo.getCheckInDate()!=null){
				hlLeaveList=impl.getHostelLeave(hlAdmissionBo.getId(),hlAdmissionBo.getCheckInDate());
				if(hostelId!=null && blockId!=null && unitId!=null){
					hostelHolidaysBoList=impl.getHostelHolidays(hlAdmissionBo.getStudentId().getAdmAppln().getCourseBySelectedCourseId().getId(),hostelId,blockId,unitId);
				}
			}
			//leave details
			List<HlLeave> hlLeaveList1=new ArrayList<HlLeave>();
			if(hlLeaveList!=null && !hlLeaveList.isEmpty()){
				List<HostelTO> hostelTOList=new ArrayList<HostelTO>();
				Iterator<HlLeave> iterator=hlLeaveList.iterator();
				HostelTO hostelTO=null;
					while (iterator.hasNext()) {
						hostelTO=new HostelTO();
						HlLeave hlLeave = (HlLeave) iterator.next();
						if(hlLeave.getIsRejected()!=null || hlLeave.getIsApproved()!=null){
							if(hlLeave.getIsRejected()!=null && hlLeave.getIsRejected()){
								hostelTO.setStatus("Rejected");
							}else if(hlLeave.getIsApproved()!=null && hlLeave.getIsApproved()){
								hostelTO.setStatus("Approved");
								hlLeaveList1.add(hlLeave);
							}else{
								hostelTO.setStatus("Pending");
							}
						}else{
							hostelTO.setStatus("Pending");
						}
						hostelTO.setLeaveFrom(hlLeave.getLeaveFrom()+" "+hlLeave.getFromSession());
						hostelTO.setLeaveTo(hlLeave.getLeaveTo()+" "+hlLeave.getToSession());
						hostelTOList.add(hostelTO);
					}
					request.setAttribute("HLeaveDetails", hostelTOList);
			}
			//List<HlLeave> hlLeaveList1=impl.getHostelLeaveWhichApproved(hlAdmissionBo.getId(),hlAdmissionBo.getCheckInDate());
			List<HostelExemptionBo> exempList=impl.getHostelExemptionList(hlAdmissionBo.getId(),hlAdmissionBo.getCheckInDate());
			Map<String,Set<java.sql.Date>> leaveMap=getLeaveMap(hlLeaveList1);
			Map<String,Set<java.sql.Date>> exemptionMap=getExemptionMap(exempList);
			Map<String,Set<java.sql.Date>> holidaysMap=getHolidaysMap(hlAdmissionBo.getCheckInDate(),hostelHolidaysBoList);
			//hostel absence details
		Map<java.sql.Date,HlStudentAttendance> presentMap =impl.getAbscentDetails(hlAdmissionBo.getId(),hlAdmissionBo.getCheckInDate());
		List<java.sql.Date> morningAbsentDates=new ArrayList<java.sql.Date>();
		List<java.sql.Date> eveningAbsentDates=new ArrayList<java.sql.Date>();
		List<java.sql.Date> morningAbsents=new ArrayList<java.sql.Date>();
		Date currentDate=new Date();
		java.sql.Date hostelCheckInDate=new java.sql.Date(hlAdmissionBo.getCheckInDate().getTime());
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(currentDate);
		int hours=calendar.get(Calendar.HOUR_OF_DAY);
		// check this code
		
		while (CommonUtil.getDaysDiff(hostelCheckInDate, new java.sql.Date(currentDate.getTime()))>=0){
			if(presentMap.containsKey(hostelCheckInDate) && presentMap.get(hostelCheckInDate).getMorning()!=null && presentMap.get(hostelCheckInDate).getEvening()!=null){
				hostelCheckInDate=new java.sql.Date(getNextDate(hostelCheckInDate).getTime());
				continue;
			}else if(presentMap.containsKey(hostelCheckInDate) && presentMap.get(hostelCheckInDate).getMorning()!=null){
				if(CommonUtil.getDaysDiff(hostelCheckInDate, new java.sql.Date(currentDate.getTime()))==0 ){
					if(hours>=20){
						eveningAbsentDates.add(hostelCheckInDate);
					}
				}else{
					eveningAbsentDates.add(hostelCheckInDate);
				}
			}else if(presentMap.containsKey(hostelCheckInDate) && presentMap.get(hostelCheckInDate).getEvening()!=null){
				if(CommonUtil.getDaysDiff(hostelCheckInDate, new java.sql.Date(currentDate.getTime()))==0){
					if(hours>=9){
						morningAbsentDates.add(hostelCheckInDate);
					}
				}else{
					morningAbsentDates.add(hostelCheckInDate);
				}
			}
			if(presentMap.containsKey(hostelCheckInDate)){
				if(presentMap.containsKey(hostelCheckInDate) && presentMap.get(hostelCheckInDate).getMorning()==null){
					if(CommonUtil.getDaysDiff(hostelCheckInDate, new java.sql.Date(currentDate.getTime()))==0 ){
						if(hours>=20){
							morningAbsentDates.add(hostelCheckInDate);
						}
					}else{
						morningAbsentDates.add(hostelCheckInDate);
					}
				}else if(presentMap.containsKey(hostelCheckInDate) && presentMap.get(hostelCheckInDate).getEvening()==null){
					if(CommonUtil.getDaysDiff(hostelCheckInDate, new java.sql.Date(currentDate.getTime()))==0){
						if(hours>=9){
							eveningAbsentDates.add(hostelCheckInDate);
						}
					}else{
						eveningAbsentDates.add(hostelCheckInDate);
					}
				}
			}else{
				if(CommonUtil.getDaysDiff(hostelCheckInDate, new java.sql.Date(currentDate.getTime()))==0){
					if(hours>=9){
						morningAbsentDates.add(hostelCheckInDate);
					}
				}else{
					morningAbsentDates.add(hostelCheckInDate);
				}
				if(CommonUtil.getDaysDiff(hostelCheckInDate, new java.sql.Date(currentDate.getTime()))==0 ){
					if(hours>=20){
						eveningAbsentDates.add(hostelCheckInDate);
					}
				}else{
					eveningAbsentDates.add(hostelCheckInDate);
				}
			}
			hostelCheckInDate=new java.sql.Date(getNextDate(hostelCheckInDate).getTime());
		}
		//removing leaves and holidays from morning absent list
		if(morningAbsentDates!=null && !morningAbsentDates.isEmpty()){
			if(leaveMap.containsKey("morning")){
				Set<java.sql.Date> leaves=leaveMap.get("morning");
				morningAbsentDates.removeAll(leaves);
			}
			if(holidaysMap.containsKey("morning")){
				Set<java.sql.Date> holidays=holidaysMap.get("morning");
				morningAbsentDates.removeAll(holidays);
			}
			
			//  code added by chandra for if student is having hostel exemption  then he removed from absentiees list
			if(exemptionMap.containsKey("morning")){
				Set<java.sql.Date> exemption=exemptionMap.get("morning");
				morningAbsentDates.removeAll(exemption);
			}
			//  if the student hostel is having option  Punching Exemption on Sunday Morning Session then remove sunday morning session from absenties list
			if(hlAdmissionBo.getRoomId().getHlUnit().getPunchExepSundaySession() !=null && hlAdmissionBo.getRoomId().getHlUnit().getPunchExepSundaySession()){
				if(morningAbsentDates!=null && !morningAbsentDates.isEmpty()){
						Iterator<java.sql.Date> iterator=morningAbsentDates.iterator();
						while (iterator.hasNext()) {
							java.sql.Date date1= (java.sql.Date) iterator.next();
							Calendar date = Calendar.getInstance();
						    date.setTime(date1);
							if(date.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
								morningAbsents.add(date1);
							}
						}
				}
			}else{
				morningAbsents.addAll(morningAbsentDates);
			}
			// code added by chandra 
		
		}
		// removing leaves holidays from evening
		if(eveningAbsentDates!=null && !eveningAbsentDates.isEmpty()){
			if(leaveMap.containsKey("evening")){
				Set<java.sql.Date> leaves=leaveMap.get("evening");
				eveningAbsentDates.removeAll(leaves);
			}
			if(holidaysMap.containsKey("evening")){
				Set<java.sql.Date> holidays=holidaysMap.get("evening");
				eveningAbsentDates.removeAll(holidays);
			}
			//  code added by chandra for if student is having hostel exemption  then he removed from absentiees list
			if(exemptionMap.containsKey("evening")){
				Set<java.sql.Date> exemption=exemptionMap.get("evening");
				eveningAbsentDates.removeAll(exemption);
			}
			// code added by chandra 
		}
		List<HlStudentAttendanceTo> hlStudentAttendanceTos=helper.getInsatnce().getFinalHostelAbsentDetails(morningAbsents,eveningAbsentDates);
			request.setAttribute("HStudentAttendance", hlStudentAttendanceTos);
		//		display disciplinary details
		List<HlDisciplinaryDetails> hlDisciplinaryDetailsList=impl.getHlDisciplinaryDetails(hlAdmissionBo.getId());
		List<HlDisciplinaryDetailsTO> hlDisciplinaryDetailsTOList=null;
		if(hlDisciplinaryDetailsList!=null && !hlDisciplinaryDetailsList.isEmpty()){
			hlDisciplinaryDetailsTOList=new ArrayList<HlDisciplinaryDetailsTO>();
			Iterator<HlDisciplinaryDetails> iterator=hlDisciplinaryDetailsList.iterator();
			while (iterator.hasNext()) {
				HlDisciplinaryDetails hlDisciplinaryDetails = (HlDisciplinaryDetails) iterator.next();
				HlDisciplinaryDetailsTO hlDisciplinaryDetailsTO=new HlDisciplinaryDetailsTO();
				hlDisciplinaryDetailsTO.setDisciplineTypeName(hlDisciplinaryDetails.getHlDisciplinaryType().getName());
				hlDisciplinaryDetailsTO.setDate(CommonUtil.formatDates(hlDisciplinaryDetails.getDate()));
				hlDisciplinaryDetailsTO.setDescription(hlDisciplinaryDetails.getComments());
				hlDisciplinaryDetailsTOList.add(hlDisciplinaryDetailsTO);
			}
		}
		request.setAttribute("HDispDetails", hlDisciplinaryDetailsTOList);
		//		display fine details
		List<FineEntryBo> fineEntryBoList=impl.getFineDetails(hlAdmissionBo.getId());
		List<FineEntryTo> fineEntryToList=null;
		if(fineEntryBoList!=null && !fineEntryBoList.isEmpty()){
			fineEntryToList=new ArrayList<FineEntryTo>();
			Iterator<FineEntryBo> iterator=fineEntryBoList.iterator();
			while (iterator.hasNext()) {
				FineEntryBo fineEntryBo = (FineEntryBo) iterator.next();
				FineEntryTo fineEntryTo=new FineEntryTo();
				fineEntryTo.setCategory(fineEntryBo.getFineCategoryId().getName());
				fineEntryTo.setDate(CommonUtil.formatDates(fineEntryBo.getDate()));
				fineEntryTo.setAmount(fineEntryBo.getAmount());
				if(fineEntryBo.getPaid()){
					fineEntryTo.setPay("Yes");
				}else{
					fineEntryTo.setPay("No");
				}
				if(fineEntryBo.getRemarks()!=null && !fineEntryBo.getRemarks().isEmpty()){
					fineEntryTo.setRemarks(fineEntryBo.getRemarks());
				}
				fineEntryToList.add(fineEntryTo);
			}
		}
		request.setAttribute("HFineDetails", fineEntryToList);
	}
}
