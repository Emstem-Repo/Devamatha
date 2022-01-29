package com.kp.cms.handlers.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.exam.ExamStudentBioDataBO;
import com.kp.cms.bo.exam.ExamStudentDetentionRejoinDetails;
import com.kp.cms.bo.exam.ExamStudentPreviousClassDetailsBO;
import com.kp.cms.bo.exam.ExamStudentSubGrpHistoryBO;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admission.StudentBiodataForm;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.handlers.exam.ExamStudentSpecializationHandler;
import com.kp.cms.handlers.usermanagement.UserInfoHandler;
import com.kp.cms.helpers.admin.SubjectGroupHelper;
import com.kp.cms.helpers.admission.StudentBiodataHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.SubjectGroupTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.exam.ExamStudentDetentionDetailsTO;
import com.kp.cms.to.exam.ExamStudentDiscontinuationDetailsTO;
import com.kp.cms.to.exam.ExamStudentPreviousClassTo;
import com.kp.cms.to.exam.ExamStudentRejoinTO;
import com.kp.cms.to.exam.ExamSubjectGroupDetailsTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.usermanagement.UserInfoTO;
import com.kp.cms.transactions.admission.IAdmissionFormTransaction;
import com.kp.cms.transactions.admission.IStudentBiodataTransaction;
import com.kp.cms.transactionsimpl.admission.AdmissionFormTransactionImpl;
import com.kp.cms.transactionsimpl.admission.StudentBiodataTransactionImpl;

public class StudentBiodataHandler {

	private static final Log log = LogFactory.getLog(StudentBiodataHandler.class);

	public static volatile StudentBiodataHandler self = null;

	public static StudentBiodataHandler getInstance() {
		if (self == null) {
			self = new StudentBiodataHandler();
		}
		return self;
	}

	private StudentBiodataHandler() {

	}

	/**
	 * FETCHES LIST OF STUDENTS
	 * 
	 * @param stForm
	 */
	public List<StudentTO> getSearchedStudents(StudentBiodataForm stForm)
			throws Exception {
		log.info("enter getSearchedStudents");
		IStudentBiodataTransaction txn = new StudentBiodataTransactionImpl();
		int courseid = 0;
		int progID = 0;
		int progtypeId = 0;
		int semNo = 0;
		if (stForm.getCourseId() != null
				&& !StringUtils.isEmpty(stForm.getCourseId().trim())
				&& StringUtils.isNumeric(stForm.getCourseId())) {
			courseid = Integer.parseInt(stForm.getCourseId());
		}
		if (stForm.getProgramId() != null
				&& !StringUtils.isEmpty(stForm.getProgramId().trim())
				&& StringUtils.isNumeric(stForm.getProgramId())) {
			progID = Integer.parseInt(stForm.getProgramId());
		}
		if (stForm.getProgramTypeId() != null
				&& !StringUtils.isEmpty(stForm.getProgramTypeId().trim())
				&& StringUtils.isNumeric(stForm.getProgramTypeId())) {
			progtypeId = Integer.parseInt(stForm.getProgramTypeId());
		}
		if (stForm.getSemister() != null
				&& !StringUtils.isEmpty(stForm.getSemister().trim())
				&& StringUtils.isNumeric(stForm.getSemister())) {
			semNo = Integer.parseInt(stForm.getSemister());
		}
		List<Student> studentlist = txn.getSerchedStudents(stForm
				.getAcademicYear(), stForm.getApplicationNo(), stForm
				.getRegNo(), stForm.getRollNo(), courseid, progID, stForm
				.getFirstName(), semNo, progtypeId);
		List<Integer> studentPhotoList = txn.getSerchedStudentsPhotoList(stForm
				.getAcademicYear(), stForm.getApplicationNo(), stForm
				.getRegNo(), stForm.getRollNo(), courseid, progID, stForm
				.getFirstName(), semNo, progtypeId);
		StudentBiodataHelper helper = StudentBiodataHelper.getInstance();
		List<StudentTO> studenttoList = helper
				.convertStudentTOtoBO(studentlist,studentPhotoList);
		log.info("exit getSearchedStudents");
		return studenttoList;
	}

	/**
	 * fetches student details
	 * 
	 * @param stForm
	 */
	public AdmApplnTO getStudentDetails(StudentBiodataForm stForm)
			throws Exception {
		log.info("enter getStudentDetails");
		IStudentBiodataTransaction txn = new StudentBiodataTransactionImpl();
		int year = Integer.parseInt(stForm.getSelectedYear());
		Student stbo = txn.getApplicantDetails(stForm.getSelectedAppNo(), year);

		StudentBiodataHelper helper = StudentBiodataHelper.getInstance();

		// to copy the BO properties to TO
		AdmApplnTO appDetails = null;
		if (stbo != null) {
			stForm.setOriginalStudent(stbo);
			stForm.setCurrentStudId(stbo.getId());
			appDetails = helper.copyPropertiesValue(stbo.getAdmAppln(),stbo);
		    //appDetails=helper.getTcDetails(stbo);
			
		
		}
		log.info("exit getStudentDetails");
		return appDetails;
	}

	/**
	 * updates student data
	 * 
	 * @param applicantDetail
	 * @param admForm
	 * @return
	 */
	public boolean updateCompleteApplication(AdmApplnTO applicantDetail,
			StudentBiodataForm admForm,boolean isPresidance) throws Exception {
		StudentBiodataHelper helper = StudentBiodataHelper.getInstance();

		Student admBO = helper.getOriginalApplicantBO(applicantDetail, admForm,isPresidance);

		List<ExamStudentBioDataBO> stu = helper.getStudentSpecSecLang(applicantDetail, admForm);
		/*ExamStudentDetentionDetailsBO detention = helper
				.getStudentDetentionDetails(applicantDetail, admForm);*/
		ExamStudentDetentionRejoinDetails detention = helper.getStudentDetentionDetails(applicantDetail, admForm);
		ExamStudentDetentionRejoinDetails discontinue = helper.getStudentDisContinueDetails(applicantDetail, admForm);
		// ExamRejoinBO rejoin =helper.getStudentRejoinDetails(applicantDetail,
		// admForm);
		IStudentBiodataTransaction txn = new StudentBiodataTransactionImpl();
		ExamStudentDetentionRejoinDetails examStudentDetentionRejoinDetails = txn.getDetentionId(admForm.getOriginalStudent().getId());
		/*ExamStudentRejoinBO rejoin = helper.getStudentRejoinDetails(
				applicantDetail, admForm);*/
		
		if(detention == null && discontinue == null && admForm.getReadmittedClass()!= null && !admForm.getReadmittedClass().isEmpty() &&
				admForm.getRejoinDetailsDate()!= null && !admForm.getRejoinDetailsDate().trim().isEmpty()){
			throw new DuplicateException();
		}
			
		ExamStudentDetentionRejoinDetails rejoin = helper.createBOforUpdateRejoin(applicantDetail, admForm, examStudentDetentionRejoinDetails);
		
		
		
		
		if(admForm.getReadmittedClass()!= null && !admForm.getReadmittedClass().isEmpty()){
			int classSchemewiseId = Integer.parseInt(admForm.getReadmittedClass());
			ClassSchemewise classSchemewise = new ClassSchemewise();
			classSchemewise.setId(classSchemewiseId);
			admBO.setClassSchemewise(classSchemewise);
		}
		if(admForm.getReadmittedClass()!= null && !admForm.getReadmittedClass().trim().isEmpty() &&  admForm.getBatch()!= null && !admForm.getBatch().trim().isEmpty()){
			AdmAppln admAppln = admBO.getAdmAppln();
			admAppln.setAppliedYear(Integer.parseInt(admForm.getBatch()));
			admBO.setAdmAppln(admAppln);
		}
			admBO.setIsHide(false);
		
		boolean updated = txn.updateCompleteApplication(admBO, stu, detention,
				discontinue, rejoin, admForm.getDetentiondetailsRadio(), admForm.getDiscontinuedDetailsRadio());

		log.info("exit updateCompleteApplication");
		return updated;
	}

	/**
	 * creates a new student record
	 * 
	 * @param applicantDetail
	 * @param admForm
	 * @return
	 */
	public boolean createStudent(AdmApplnTO applicantDetail,
			StudentBiodataForm admForm,boolean isPresidance) throws Exception {
		log.info("enter createStudent");
		StudentBiodataHelper helper = StudentBiodataHelper.getInstance();
		Student admBO = helper.getApplicantBO(applicantDetail, admForm,isPresidance);
		if (admBO.getAdmAppln() != null) {
			admBO.getAdmAppln().setIsCancelled(false);
			admBO.getAdmAppln().setIsSelected(true);
			admBO.getAdmAppln().setIsApproved(true);
			admBO.getAdmAppln().setIsBypassed(true);
			admBO.getAdmAppln().setIsPreferenceUpdated(false);
			admBO.getAdmAppln().setIsFinalMeritApproved(true);
			//added for challan verification
			admBO.getAdmAppln().setIsChallanVerified(false);
			//challan verification addition completed
			admBO.setCreatedBy(admBO.getAdmAppln().getCreatedBy());
			admBO.setCreatedDate(admBO.getAdmAppln().getCreatedDate());
			admBO.setModifiedBy(admBO.getAdmAppln().getModifiedBy());
			admBO.setLastModifiedDate(admBO.getAdmAppln().getLastModifiedDate());
			admBO.setIsSCDataDelivered(false);
			admBO.setIsSCDataGenerated(false);
			admBO.setIsHide(false);
		}

		IStudentBiodataTransaction txn = new StudentBiodataTransactionImpl();
		boolean updated = txn.createNewStudent(admBO);

		log.info("exit createStudent");

		return updated;
	}

	/**
	 * returns a blank student object
	 * 
	 * @param stForm
	 * @return
	 */
	public AdmApplnTO getNewStudent(StudentBiodataForm stForm) throws Exception {
		log.info("Enter getNewStudent ...");
		StudentBiodataHelper helper = StudentBiodataHelper.getInstance();
		log.info("Exit getNewStudent ...");
		return helper.getNewStudent(stForm.getCourseId(), stForm);
	}

	/**
	 * checks application number already exists for year and course or not
	 * 
	 * @param applicationData
	 * @param courseId
	 * @return
	 * @throws Exception
	 */
	public boolean checkApplicationNoUniqueForYear(int applnNo, int year)
			throws Exception {
		log.info("Enter checkApplicationNoUniqueForYear ...");
		boolean unique = false;
		IAdmissionFormTransaction txn = new AdmissionFormTransactionImpl();
		unique = txn.checkApplicationNoUniqueForYear(applnNo, year);
		log.info("Exit checkApplicationNoUniqueForYear ...");
		return unique;
	}

	/**
	 * get class schemes for new student
	 * 
	 * @param courseid
	 * @param year
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getClassSchemeForStudent(int courseid, int year)
			throws Exception {
		log.info("Enter getClassSchemeForStudent ...");
		IStudentBiodataTransaction txn = new StudentBiodataTransactionImpl();
		Map<Integer, String> map = new HashMap<Integer, String>();
		List<ClassSchemewise> classList = txn.getClassSchemeForStudent(
				courseid, year);
		ClassSchemewise classSchemewise;
		Iterator<ClassSchemewise> itr = classList.iterator();
		while (itr.hasNext()) {
			classSchemewise = itr.next();
			map.put(classSchemewise.getId(), classSchemewise.getClasses()
					.getName());
		}
		log.info("Exit getClassSchemeForStudent ...");
		return map;
	}

	/**
	 * reg. no. unique check for course and year
	 * 
	 * @param regNo
	 * @param appliedYear
	 * @param valueOf
	 * @return
	 */
	public boolean checkRegNoUnique(String regNo, Integer appliedYear)
			throws Exception {
		log.info("Enter checkRegNoUnique ...");
		boolean unique = false;
		IStudentBiodataTransaction txn = new StudentBiodataTransactionImpl();
		unique = txn.checkRegNoUnique(regNo, appliedYear);
		log.info("Exit checkRegNoUnique ...");
		return unique;
	}

	/**
	 * @param studId
	 * @return
	 * @throws Exception
	 */
	public boolean deleteStudentDetails(int studId) throws Exception {
		IStudentBiodataTransaction iStudentBiodataTransaction = new StudentBiodataTransactionImpl();
		return iStudentBiodataTransaction.deleteStudent(studId);

	}

	/**
	 * sets user related settings like view or enter remarks
	 * 
	 * @param stForm
	 */
	public void setUserSettings(StudentBiodataForm stForm) throws Exception {
		String currUser = stForm.getUserId();
		int currID = 0;
		if (currUser != null && !StringUtils.isEmpty(currUser)
				&& StringUtils.isNumeric(currUser)) {
			currID = Integer.parseInt(currUser);
		}
		UserInfoHandler userhandle = UserInfoHandler.getInstance();
		List<UserInfoTO> userTos = userhandle.getUserDetailsById(currID);
		UserInfoTO user = null;
		if (userTos != null && !userTos.isEmpty()) {
			user = userTos.get(0);
		}
		if (user != null) {
			// if both false
			if (user.getRemarksEntry() != null && user.getViewRemarks() != null) {
				if (!user.getRemarksEntry() && !user.getViewRemarks()) {
					stForm.setAccessRemarks(false);
				}// if edit only
				else if (user.getRemarksEntry() && !user.getViewRemarks()) {
					stForm.setAccessRemarks(true);
					stForm.setEditRemarks(true);
					stForm.setViewRemarks(false);
				}// if view only
				else if (!user.getRemarksEntry() && user.getViewRemarks()) {
					stForm.setAccessRemarks(true);
					stForm.setEditRemarks(false);
					stForm.setViewRemarks(true);
				}
			}
		}
	}

	// Below methods added by 9-elements
	public void setAllSpecialisationValues(StudentBiodataForm stForm) throws Exception {
		StudentBiodataHelper helper = StudentBiodataHelper.getInstance();
		StudentBiodataTransactionImpl impl = new StudentBiodataTransactionImpl();
		ArrayList<KeyValueTO> specialisation = new ExamStudentSpecializationHandler().getSpecializationList(stForm.getOriginalStudent().getAdmAppln().getCourseBySelectedCourseId().getId());
		stForm.setSpecialisationList(specialisation);

		List<ExamStudentBioDataBO> studentBoList=impl.studentBioData(stForm.getOriginalStudent().getId());
		stForm.setStudentBoList(studentBoList);
		// get detention details
		helper.convertBOtoDetention(impl.studentDetention(stForm.getOriginalStudent().getId()), stForm);

		helper.convertBOtoDiscontinue(impl.studentDiscontinue(stForm
				.getOriginalStudent().getId()), stForm);

		helper.convertBOtoRejoin(impl.studentRejoin(stForm.getOriginalStudent()
				.getId()), stForm);
		ExamGenHandler genHandler = new ExamGenHandler();
		HashMap<Integer, String> readmittedClass = genHandler.getClassList();
		stForm.setReadmittedClassList(readmittedClass);
		// ----------------------subjectgroupdetails------------------

		int schemeNo = getSchemeNoOfStudent(stForm.getOriginalStudent().getId());
		if (schemeNo > 1) {
			stForm.setSubjectGroupsDetailsLink("present");
		} else {
			stForm.setSubjectGroupsDetailsLink("");
		}
		// --------------studentBo---------------------
		if(studentBoList!=null && !studentBoList.isEmpty()){
			String[] ids=new String[studentBoList.size()];
			Iterator<ExamStudentBioDataBO> itr=studentBoList.iterator();
			for (int i = 0; itr.hasNext(); i++) {
				ExamStudentBioDataBO studentBo = (ExamStudentBioDataBO) itr.next();
				if (studentBo.getSpecializationId() != null) {
					ids[i] = String.valueOf(studentBo.getSpecializationId());
				}
				if(studentBo!= null && studentBo.getId()!= null){
					stForm.setExamStudentBiodataId(studentBo.getId());
				}
				if (studentBo.getConsolidatedMarksCardNo() != null) {
					stForm.setConsolidateMarksNo(studentBo.getConsolidatedMarksCardNo());
				} else {
					stForm.setConsolidateMarksNo("");
				}
				if (studentBo.getCourseNameForMarksCard() != null) {
					stForm.setCourseNameForMarkscard(studentBo
							.getCourseNameForMarksCard());
				} else {
					stForm.setCourseNameForMarkscard("");
				}
			}
			stForm.setSpecialisationId(ids);
		}else{
			stForm.setSpecialisationId(null);
			stForm.setCourseNameForMarkscard("");
			stForm.setConsolidateMarksNo("");
		}
		
//		if (studentBo.getSpecializationId() != null) {
//			stForm.setSpecialisationId(Integer.toString(studentBo
//					.getSpecializationId()));
//		} else {
//			stForm.setSpecialisationId("");
//		}
//		if (studentBo.getSecondLanguageId() != null) {
//			stForm.setSecondLanguageId(Integer.toString(studentBo
//					.getSecondLanguageId()));
//		} else {
//			stForm.setSecondLanguageId("");
//		}
		
		HashMap<Integer, String> secondLanguage = genHandler.getSecondLanguage();
		stForm.setSecondLanguageList(secondLanguage);

	}

	public ArrayList<ExamStudentDiscontinuationDetailsTO> viewHistory_Discontinuation(
			int studentId) throws BusinessException {
		StudentBiodataHelper helper = StudentBiodataHelper.getInstance();
		StudentBiodataTransactionImpl impl = new StudentBiodataTransactionImpl();
		return helper.convertBOToTO_viewHistory_Discontinuation(impl
				.viewHistory_StuDiscontinuation(studentId));

	}

	public ArrayList<ExamStudentDetentionDetailsTO> viewHistory_StuDetention(
			int studentId) throws BusinessException {
		StudentBiodataHelper helper = StudentBiodataHelper.getInstance();
		StudentBiodataTransactionImpl impl = new StudentBiodataTransactionImpl();

		return helper.convertBOToTO_viewHistory_StuDetention(impl
				.viewHistory_StuDetention(studentId));
	}

	public ArrayList<ExamStudentRejoinTO> viewHistory_StuRejoin(int studentId)
			throws BusinessException {
		StudentBiodataHelper helper = StudentBiodataHelper.getInstance();
		StudentBiodataTransactionImpl impl = new StudentBiodataTransactionImpl();
		return helper.convertBOToTO_viewHistory_StuRejoin(impl
				.viewHistory_StuRejoin(studentId));

	}

	public int getSchemeNoOfStudent(int studentId) {
		StudentBiodataTransactionImpl impl = new StudentBiodataTransactionImpl();
		return impl.getSchemeOfStudent(studentId);
	}

	public ArrayList<ExamSubjectGroupDetailsTO> viewHistory_SubjectGroupDetails(
			int studentId) {

		StudentBiodataHelper helper = StudentBiodataHelper.getInstance();
		StudentBiodataTransactionImpl impl = new StudentBiodataTransactionImpl();

		ArrayList<ExamSubjectGroupDetailsTO> listSubjGrp = helper
				.convertBOToTO_viewHistory_SubjectGroupDetails(impl
						.viewHistory_SubjectGroupDetails(studentId));
		// if (h.size() > 0) {
		// to.setSchemeNo(i);
		// to.setSubjectGroupList(h);
		// listSubjGrp.add(to);
		// }
		// }
		return listSubjGrp;
	}

	public List<SubjectGroupTO> getSubjectGroupList(int courseId, Integer year)
			throws Exception {
		IStudentBiodataTransaction transaction = new StudentBiodataTransactionImpl();
		List<SubjectGroup> boList = transaction.getSubjectGroupList(courseId,
				year);
		if (boList != null) {
			return SubjectGroupHelper.getInstance().populateSubjectGroupBOtoTo(
					boList);
		} else {
			return null;
		}
	}

	public Map<Integer, String> getClassesBySelectedCourse(int courseid,
			Integer year) throws Exception {
		IStudentBiodataTransaction txn = new StudentBiodataTransactionImpl();
		Map<Integer, String> map = new HashMap<Integer, String>();
		List<Object[]> objList = txn.getYearandTermNo(courseid, year);
		Iterator<Object[]> it = objList.iterator();
		int semNo = 0;
		int year1 = 0;
		if (objList != null) {
			while (it.hasNext()) {
				Object[] objects = (Object[]) it.next();
				if (objects[0] != null) {
					semNo = Integer.parseInt(objects[0].toString());
				}
				if (objects[1] != null) {
					year1 = Integer.parseInt(objects[1].toString());
				}
				if (semNo > 0 && year1 > 0) {
					List<ClassSchemewise> classList = txn
							.getClassSchemeByCourseId(semNo, year1, courseid);
					ClassSchemewise classSchemewise;
					Iterator<ClassSchemewise> itr = classList.iterator();
					while (itr.hasNext()) {
						classSchemewise = itr.next();
						map.put(classSchemewise.getId(), classSchemewise
								.getClasses().getName());
					}
				}
			}
		}

		log.info("Exit getClassSchemeForStudent ...");
		return map;
	}
	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ExamStudentDetentionDetailsTO> getDetentionHistory(int studentId, boolean detention) throws Exception {
		IStudentBiodataTransaction transaction = new StudentBiodataTransactionImpl();
		List<ExamStudentDetentionRejoinDetails> boList = transaction.studentDetainHistory(studentId, detention);
		if (boList != null) {
			return StudentBiodataHelper.getInstance().convertDetainHistoryToTO(boList, detention);
		} else {
			return null;
		}
	}
	public ArrayList<ExamStudentDetentionDetailsTO> studentRejoinHistory(int studentId) throws Exception {
		IStudentBiodataTransaction transaction = new StudentBiodataTransactionImpl();
		List<ExamStudentDetentionRejoinDetails> boList = transaction.studentRejoinHistory(studentId);
		if (boList != null) {
			return StudentBiodataHelper.getInstance().convertRejoinHistoryToTO(boList);
		} else {
			return null;
		}
	}
	
	
	public List<SubjectGroupTO> getSubjectGroupListBySemester(int courseId, Integer year,int semesterNo)
	throws Exception {
	IStudentBiodataTransaction transaction = new StudentBiodataTransactionImpl();
	List<SubjectGroup> boList = transaction.getSubjectGroupListBySemester(courseId,
			year,semesterNo);
	if (boList != null) {
		return SubjectGroupHelper.getInstance().populateSubjectGroupBOtoTo(
				boList);
	} else {
		return null;
	}
}

	public boolean updateSubjectHistory(StudentBiodataForm stForm,int scheme,Classes classes) throws Exception{
		
		IStudentBiodataTransaction transaction=new StudentBiodataTransactionImpl();
		
		Map<Integer,ExamStudentSubGrpHistoryBO> subHistMap=stForm.getSubjHistMap();
		
		List<ExamStudentSubGrpHistoryBO> stu= new ArrayList<ExamStudentSubGrpHistoryBO>();
		List<ExamStudentSubGrpHistoryBO> deleteList=new ArrayList<ExamStudentSubGrpHistoryBO>();
		ExamStudentSubGrpHistoryBO bo;
		if (stForm.getSubjGroupHistId()!=null && stForm.getSubjGroupHistId().length != 0) {
			String[] sgids = stForm.getSubjGroupHistId();
			for (int j = 0; j < sgids.length; j++) {
				if (subHistMap != null && subHistMap.containsKey(Integer.parseInt(sgids[j]))) {
					bo = subHistMap.get(Integer.parseInt(sgids[j]));
				} else {
					bo = new ExamStudentSubGrpHistoryBO();
				}
				bo.setStudentId(stForm.getOriginalStudent().getId());
				bo.setSchemeNo(scheme);
				bo.setSubjectGroupId(Integer.parseInt(sgids[j]));
				bo.setCreatedBy(stForm.getUserId());
				bo.setCreatedDate(new Date());
				bo.setLastModifiedDate(new Date());
				bo.setModifiedBy(stForm.getUserId());
				stu.add(bo);
			}
			
			// getting Unselected Bos
			Iterator it = subHistMap.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		        boolean isValid=false;
		        for (int j = 0; j < sgids.length; j++) {
		        	if(Integer.parseInt(sgids[j])==Integer.parseInt(pairs.getKey().toString())){
		        		isValid=true;
		        		break;
		        	}
		        }
		        if(isValid==false){
		        	ExamStudentSubGrpHistoryBO subHistBo=(ExamStudentSubGrpHistoryBO)pairs.getValue();
		        	if(subHistBo.getId()>0)
		        	deleteList.add(subHistBo);
		        }
		    }
			
		}
		ExamStudentPreviousClassDetailsBO previousClassBo=null;
		if(classes!=null){
			previousClassBo=new ExamStudentPreviousClassDetailsBO();
			if(stForm.getPreviousClassId()!=null && !stForm.getPreviousClassId().isEmpty()){
				previousClassBo.setId(Integer.parseInt(stForm.getPreviousClassId()));
			}
			previousClassBo.setStudentId(stForm.getOriginalStudent().getId());
			previousClassBo.setSchemeNo(classes.getTermNumber());
			previousClassBo.setAcademicYear(transaction.getYearForClassId(classes.getId()));
			previousClassBo.setCreatedBy(stForm.getUserId());
			previousClassBo.setCreatedDate(new Date());
			previousClassBo.setLastModifiedDate(new Date());
			previousClassBo.setModifiedBy(stForm.getUserId());
			previousClassBo.setClassId(Integer.parseInt(stForm.getClassHistId()));
		}
		
		return transaction.updateHistoryDetails(stu,deleteList,previousClassBo);
	}

	/**
	 * @param id
	 * @param scheme
	 * @return
	 * @throws Exception
	 */
	public ExamStudentPreviousClassDetailsBO getPreviousClassHistory(int studentId,int scheme) throws Exception {
		IStudentBiodataTransaction transaction=new StudentBiodataTransactionImpl();
		return transaction.getPreviousClassHistory(studentId,scheme);
	}
	
	// Making map with classid
	public Map<Integer, String> getClassesMapBySelectedCourse(int courseid,
			Integer year) throws Exception {
		IStudentBiodataTransaction txn = new StudentBiodataTransactionImpl();
		Map<Integer, String> map = new HashMap<Integer, String>();
		List<Object[]> objList = txn.getYearandTermNo(courseid, year);
		Iterator<Object[]> it = objList.iterator();
		int semNo = 0;
		int year1 = 0;
		if (objList != null) {
			while (it.hasNext()) {
				Object[] objects = (Object[]) it.next();
				if (objects[0] != null) {
					semNo = Integer.parseInt(objects[0].toString());
				}
				if (objects[1] != null) {
					year1 = Integer.parseInt(objects[1].toString());
				}
				if (semNo > 0 && year1 > 0) {
					List<ClassSchemewise> classList = txn
							.getClassSchemeByCourseId(semNo, year1, courseid);
					ClassSchemewise classSchemewise;
					Iterator<ClassSchemewise> itr = classList.iterator();
					while (itr.hasNext()) {
						classSchemewise = itr.next();
						map.put(classSchemewise.getClasses().getId(), classSchemewise
								.getClasses().getName());
					}
				}
			}
		}

		log.info("Exit getClassSchemeForStudent ...");
		return map;
	}
	public List<ExamStudentPreviousClassTo> viewHistory_ClassGroupDetails(int studentId) {
		StudentBiodataHelper helper = StudentBiodataHelper.getInstance();
		StudentBiodataTransactionImpl impl = new StudentBiodataTransactionImpl();
		//ExamStudentPreviousClassTo classDetailsTo=new ExamStudentPreviousClassTo();
		List<ExamStudentPreviousClassTo>  classDetailsTo = helper
				.convertBOToTO_viewHistory_ClassGroupDetails(impl
						.viewHistory_ClassGroupDetails(studentId));
		// if (h.size() > 0) {
		// to.setSchemeNo(i);
		// to.setSubjectGroupList(h);
		// listSubjGrp.add(to);
		// }
		// }
		return classDetailsTo;
	}

	/**
	 * Checking whether the student is canceled.
	 * @param stForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkStudentIsActive(StudentBiodataForm stForm) throws Exception{
		IStudentBiodataTransaction transaction = new StudentBiodataTransactionImpl();
		
		return transaction.checkStudentIsActive(stForm);
	}
	


}