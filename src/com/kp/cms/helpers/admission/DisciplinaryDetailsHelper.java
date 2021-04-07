package com.kp.cms.helpers.admission;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.AdmapplnAdditionalInfo;
import com.kp.cms.bo.admin.ApplicantMarksDetails;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.AttendancePeriod;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.CandidateMarks;
import com.kp.cms.bo.admin.CurriculumSchemeDuration;
import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.bo.admin.HlLeave;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentRemarks;
import com.kp.cms.bo.exam.ExamStudentDetentionRejoinDetails;
import com.kp.cms.bo.hostel.HlStudentAttendance;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.DisciplinaryDetailsForm;
import com.kp.cms.handlers.admin.CandidateMarkTO;
import com.kp.cms.handlers.admin.UniversityHandler;
import com.kp.cms.handlers.admission.AdmissionFormHandler;
import com.kp.cms.handlers.admission.CurriculumSchemeHandler;
import com.kp.cms.handlers.attendance.StudentAttendanceSummaryHandler;
import com.kp.cms.to.admin.ApplicantMarkDetailsTO;
import com.kp.cms.to.admin.DetailedSubjectsTO;
import com.kp.cms.to.admin.DocTypeExamsTO;
import com.kp.cms.to.admin.EdnQualificationTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.UniversityTO;
import com.kp.cms.to.admission.CourseDetailsTO;
import com.kp.cms.to.admission.DisciplinaryDetailsTo;
import com.kp.cms.to.admission.FeesTO;
import com.kp.cms.to.admission.RemarcksTO;
import com.kp.cms.to.admission.SessionAttendnceToAm;
import com.kp.cms.to.admission.SessionAttendnceToPm;
import com.kp.cms.to.attendance.PeriodTO;
import com.kp.cms.to.exam.ExamMarksEntryDetailsTO;
import com.kp.cms.to.hostel.HlStudentAttendanceTo;
import com.kp.cms.to.reports.StudentWiseSubjectSummaryTO;
import com.kp.cms.to.usermanagement.StudentAttendanceTO;
import com.kp.cms.transactions.reports.IStudentWiseAttendanceSummaryTransaction;
import com.kp.cms.transactionsimpl.admission.DisciplinaryDetailsImpl;
import com.kp.cms.transactionsimpl.reports.StudentWiseAttendanceSummaryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.TeacherReportComparator;

@SuppressWarnings("unchecked")
public class DisciplinaryDetailsHelper {
	DisciplinaryDetailsImpl impl = new DisciplinaryDetailsImpl();
	private static final String OTHER = "Other";
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	private static volatile DisciplinaryDetailsHelper disciplinaryDetailsHelper = null;
	
	public static DisciplinaryDetailsHelper getInsatnce() {
		if(disciplinaryDetailsHelper == null) {
			disciplinaryDetailsHelper = new DisciplinaryDetailsHelper();
		}
		return disciplinaryDetailsHelper;
	}
	/**
	 * @param objBO
	 * @param request
	 * @return
	 */
	public DisciplinaryDetailsTo convertBoTO(Student objBO,
			HttpServletRequest request) {
		DisciplinaryDetailsTo objTo = new DisciplinaryDetailsTo();

		Set<ApplnDoc> docSet = objBO.getAdmAppln().getApplnDocs();
		Iterator<ApplnDoc> itr = docSet.iterator();
		while (itr.hasNext()) {
			ApplnDoc applnDoc = (ApplnDoc) itr.next();
			if (applnDoc.getIsPhoto() != null)
				if (applnDoc.getIsPhoto() && applnDoc.getDocument() != null) {

					request.getSession().setAttribute("DisplinaryPhotoBytes",
							applnDoc.getDocument());
				}

		}
		objTo.setRegRollNo(checkNull(objBO.getRegisterNo()));
		//added for subject order
		if(objBO.getClassSchemewise()!=null && objBO.getClassSchemewise().getClasses()!=null)
		objTo.setSemNo(objBO.getClassSchemewise().getClasses().getTermNumber());
		if(objBO.getClassSchemewise()!=null && objBO.getClassSchemewise().getCurriculumSchemeDuration()!=null)
			objTo.setSemesterAcademicYear(objBO.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear());
		
		//objTo.setCourse(checkNull(objBO.getAdmAppln().getCourse().getName()));
		objTo.setCourse(checkNull(objBO.getAdmAppln().getCourseBySelectedCourseId().getName()));
		objTo.setCourseId(objBO.getAdmAppln().getCourseBySelectedCourseId().getId());
		String Name = checkNull(objBO.getAdmAppln().getPersonalData()
				.getFirstName())
				+ " "
				+ checkNull(objBO.getAdmAppln().getPersonalData()
						.getMiddleName())
				+ " "
				+ checkNull(objBO.getAdmAppln().getPersonalData().getLastName());
		objTo.setName(Name);

		if (objBO.getAdmAppln().getPersonalData().getDateOfBirth() != null) {
			objTo.setDateOfBirth(CommonUtil.getStringDate(objBO.getAdmAppln()
					.getPersonalData().getDateOfBirth()));
		}
		objTo.setGender(checkNull(objBO.getAdmAppln().getPersonalData()
				.getGender()));
		objTo.seteMail(checkNull(objBO.getAdmAppln().getPersonalData()
				.getEmail()));
		
		if(checkNull(objBO.getAdmAppln().getPersonalData().getPhNo1())!="" 
			&& checkNull(objBO.getAdmAppln().getPersonalData().getPhNo2())!="" 
				&& checkNull(objBO.getAdmAppln().getPersonalData().getPhNo3())!="" ){
			objTo.setPhoneNo(objBO.getAdmAppln().getPersonalData()
				.getPhNo1()+"-"+objBO.getAdmAppln().getPersonalData()
						.getPhNo2()+"-"+objBO.getAdmAppln().getPersonalData()
								.getPhNo3());
		}
		else{
			objTo.setPhoneNo(objBO.getAdmAppln().getPersonalData().getPhNo3());
		}
		objTo.setMobNumber(checkNull(objBO.getAdmAppln().getPersonalData()
				.getMobileNo1())+"-"+checkNull(objBO.getAdmAppln().getPersonalData()
						.getMobileNo2()));
		objTo.setNationality(checkNull(objBO.getAdmAppln().getPersonalData()
				.getNationality().getName()));
		if (objBO.getAdmAppln().getDate() != null) {
			objTo.setDateOfAddmission(CommonUtil.getStringDate(objBO
					.getAdmAppln().getDate()));
		}
		String currentAddress = checkNull(objBO.getAdmAppln().getPersonalData()
				.getCurrentAddressLine1())
				+ " "
				+ checkNull(objBO.getAdmAppln().getPersonalData()
						.getCurrentAddressLine2())
				+ " "
				+ checkNull(objBO.getAdmAppln().getPersonalData()
						.getCityByCurrentAddressCityId()) + " ";
		if (objBO.getAdmAppln().getPersonalData()
				.getStateByCurrentAddressStateId() != null) {
			currentAddress = currentAddress
					+ checkNull(objBO.getAdmAppln().getPersonalData()
							.getStateByCurrentAddressStateId().getName());
		}
		currentAddress = currentAddress
				+ ""
				+ checkNull(objBO.getAdmAppln().getPersonalData()
						.getCurrentAddressZipCode());

		objTo.setCurrentAddress(currentAddress);
		String permanentAddress = checkNull(objBO.getAdmAppln()
				.getPersonalData().getPermanentAddressLine1())
				+ " "
				+ checkNull(objBO.getAdmAppln().getPersonalData()
						.getPermanentAddressLine2())
				+ " "
				+ checkNull(objBO.getAdmAppln().getPersonalData()
						.getCityByPermanentAddressCityId()) + " ";
		if (objBO.getAdmAppln().getPersonalData()
				.getStateByPermanentAddressStateId() != null) {
			permanentAddress = permanentAddress
					+ checkNull(objBO.getAdmAppln().getPersonalData()
							.getStateByPermanentAddressStateId().getName());
		}
		if (objBO.getAdmAppln().getPersonalData().getCountryByCountryId() != null) {
			permanentAddress = permanentAddress
					+ " "
					+ checkNull(objBO.getAdmAppln().getPersonalData()
							.getCountryByCountryId().getName());
		}

		permanentAddress = permanentAddress
				+ ""
				+ checkNull(objBO.getAdmAppln().getPersonalData()
						.getPermanentAddressZipCode());

		objTo.setPermanentAddress(permanentAddress);
		objTo.setPassportNumber(checkNull(objBO.getAdmAppln().getPersonalData()
				.getPassportNo()));
		if (objBO.getAdmAppln().getPersonalData()
				.getCountryByPassportCountryId() != null) {
			objTo.setIssueCountry(objBO.getAdmAppln().getPersonalData()
					.getCountryByPassportCountryId().getName());
		}
		if (objBO.getAdmAppln().getPersonalData().getPassportValidity() != null) {
			objTo.setValidUpTo(CommonUtil.getStringDate(objBO.getAdmAppln()
					.getPersonalData().getPassportValidity()));
		}
		objTo.setResedentPermitNo(checkNull(objBO.getAdmAppln()
				.getPersonalData().getResidentPermitNo()));
		objTo.setObtainedDate(CommonUtil.getStringDate(objBO.getAdmAppln()
				.getPersonalData().getResidentPermitDate()));
		// Father
		objTo.setFatherName(checkNull(objBO.getAdmAppln().getPersonalData()
				.getFatherName()));
		objTo.setFatherEducation(checkNull(objBO.getAdmAppln()
				.getPersonalData().getFatherEducation()));
		if (objBO.getAdmAppln().getPersonalData()
				.getCurrencyByFatherIncomeCurrencyId() != null) {
			objTo.setFatherIncomeCurrency(checkNull(objBO.getAdmAppln()
					.getPersonalData().getCurrencyByFatherIncomeCurrencyId()
					.getCurrencyCode()));
		}
		if (objBO.getAdmAppln().getPersonalData().getIncomeByFatherIncomeId() != null) {
			objTo.setFatherIncome(checkNull(objBO.getAdmAppln()
					.getPersonalData().getIncomeByFatherIncomeId()
					.getIncomeRange()));
		}
		if (objBO.getAdmAppln().getPersonalData()
				.getOccupationByFatherOccupationId() != null) {
			objTo.setFatherOccupaction(checkNull(objBO.getAdmAppln()
					.getPersonalData().getOccupationByFatherOccupationId()
					.getName()));
		}
		objTo.setFatheremail(checkNull(objBO.getAdmAppln().getPersonalData()
				.getFatherEmail()));
		
		/*if(checkNull(objBO.getAdmAppln().getPersonalData().getParentPh1())!=""
			&& checkNull(objBO.getAdmAppln().getPersonalData().getParentPh2())!=""
				&& checkNull(objBO.getAdmAppln().getPersonalData().getParentPh3())!=""
					&& checkNull(objBO.getAdmAppln().getPersonalData().getParentMob1())!=""
						&& checkNull(objBO.getAdmAppln().getPersonalData().getParentMob2())!=""){
			objTo.setFatherPhone(objBO.getAdmAppln().getPersonalData()
					.getParentPh1()+"-"+objBO.getAdmAppln().getPersonalData()
							.getParentPh2()+"-"+objBO.getAdmAppln().getPersonalData()
									.getParentPh3()
					+ "/"
					+ objBO.getAdmAppln().getPersonalData()
							.getParentMob1()+"-"+objBO.getAdmAppln().getPersonalData()
									.getParentMob2()+objBO.getAdmAppln().getPersonalData().getParentMob3());
		}
		else if(checkNull(objBO.getAdmAppln().getPersonalData().getParentPh1())==""
			&& checkNull(objBO.getAdmAppln().getPersonalData().getParentPh2())==""
				&& checkNull(objBO.getAdmAppln().getPersonalData().getParentPh3())==""){
			objTo.setFatherPhone(objBO.getAdmAppln().getPersonalData()
					.getParentMob1()+"-"+objBO.getAdmAppln().getPersonalData()
							.getParentMob2());
		}
		else{
			objTo.setFatherPhone(checkNull(objBO.getAdmAppln().getPersonalData()
					.getParentPh1())+"-"+checkNull(objBO.getAdmAppln().getPersonalData()
							.getParentPh2())+"-"+checkNull(objBO.getAdmAppln().getPersonalData()
									.getParentPh3()));
		}
		*/
	String fatherPHone="";
		if(objBO.getAdmAppln().getPersonalData().getParentPh1()!=null && !objBO.getAdmAppln().getPersonalData().getParentPh1().isEmpty()
			&&((objBO.getAdmAppln().getPersonalData().getParentPh2()!=null && !objBO.getAdmAppln().getPersonalData().getParentPh2().isEmpty())
					|| objBO.getAdmAppln().getPersonalData().getParentPh3()!=null && !objBO.getAdmAppln().getPersonalData().getParentPh3().isEmpty())){
			fatherPHone=fatherPHone+objBO.getAdmAppln().getPersonalData().getParentPh1()+"-";
		}else{
			fatherPHone=fatherPHone+objBO.getAdmAppln().getPersonalData().getParentPh1();
		}
		if(objBO.getAdmAppln().getPersonalData().getParentPh2()!=null && !objBO.getAdmAppln().getPersonalData().getParentPh2().isEmpty()
			&& objBO.getAdmAppln().getPersonalData().getParentPh3()!=null && !objBO.getAdmAppln().getPersonalData().getParentPh3().isEmpty()){
			fatherPHone=fatherPHone+objBO.getAdmAppln().getPersonalData().getParentPh2()+"-";
		}else{
			fatherPHone=fatherPHone+objBO.getAdmAppln().getPersonalData().getParentPh2();
		}
		if(objBO.getAdmAppln().getPersonalData().getParentPh3()!=null && !objBO.getAdmAppln().getPersonalData().getParentPh3().isEmpty()){
			fatherPHone=fatherPHone+objBO.getAdmAppln().getPersonalData().getParentPh3();
		}
		if((objBO.getAdmAppln().getPersonalData().getParentPh1()!=null && !objBO.getAdmAppln().getPersonalData().getParentPh1().isEmpty())
				|| (objBO.getAdmAppln().getPersonalData().getParentPh2()!=null && !objBO.getAdmAppln().getPersonalData().getParentPh2().isEmpty())
				|| (objBO.getAdmAppln().getPersonalData().getParentPh3()!=null && !objBO.getAdmAppln().getPersonalData().getParentPh3().isEmpty())){
			fatherPHone=fatherPHone+"/";
		}
		if(objBO.getAdmAppln().getPersonalData().getParentMob1()!=null && !objBO.getAdmAppln().getPersonalData().getParentMob1().isEmpty()
				&&((objBO.getAdmAppln().getPersonalData().getParentMob2()!=null && !objBO.getAdmAppln().getPersonalData().getParentMob2().isEmpty())
					||(objBO.getAdmAppln().getPersonalData().getParentMob3()!=null && !objBO.getAdmAppln().getPersonalData().getParentMob3().isEmpty()))){
			fatherPHone=fatherPHone+objBO.getAdmAppln().getPersonalData().getParentMob1()+"-";
		}else{
			fatherPHone=fatherPHone+objBO.getAdmAppln().getPersonalData().getParentMob1();
		}
		if(objBO.getAdmAppln().getPersonalData().getParentMob2()!=null && !objBO.getAdmAppln().getPersonalData().getParentMob2().isEmpty() && objBO.getAdmAppln().getPersonalData().getParentMob2().length()>=10
				&& objBO.getAdmAppln().getPersonalData().getParentMob3()!=null && !objBO.getAdmAppln().getPersonalData().getParentMob3().isEmpty()){
			fatherPHone=fatherPHone+objBO.getAdmAppln().getPersonalData().getParentMob2()+"/";
		}else{
			fatherPHone=fatherPHone+objBO.getAdmAppln().getPersonalData().getParentMob2();
		}
		if(objBO.getAdmAppln().getPersonalData().getParentMob3()!=null && !objBO.getAdmAppln().getPersonalData().getParentMob3().isEmpty()){
			fatherPHone=fatherPHone+objBO.getAdmAppln().getPersonalData().getParentMob3();
		}
		
		objTo.setFatherPhone(fatherPHone.trim());
		// Mother
		objTo.setMotherName(checkNull(objBO.getAdmAppln().getPersonalData()
				.getMotherName()));
		objTo.setMotherEducation(checkNull(objBO.getAdmAppln()
				.getPersonalData().getMotherEducation()));
		if (objBO.getAdmAppln().getPersonalData()
				.getCurrencyByMotherIncomeCurrencyId() != null) {
			objTo.setMotherincomeCurrency(checkNull(objBO.getAdmAppln()
					.getPersonalData().getCurrencyByMotherIncomeCurrencyId()
					.getCurrencyCode()));
		}
		if (objBO.getAdmAppln().getPersonalData().getIncomeByMotherIncomeId() != null) {
			objTo.setMotherIncome(checkNull(objBO.getAdmAppln()
					.getPersonalData().getIncomeByMotherIncomeId()
					.getIncomeRange()));
		}
		if (objBO.getAdmAppln().getPersonalData()
				.getOccupationByMotherOccupationId() != null) {
			objTo.setMotheroccupaction(checkNull(objBO.getAdmAppln()
					.getPersonalData().getOccupationByMotherOccupationId()
					.getName()));
		}
		objTo.setMotherEmail(checkNull(objBO.getAdmAppln().getPersonalData()
				.getMotherEmail()));
		/*if(checkNull(objBO.getAdmAppln().getPersonalData().getParentPh1())!=""
			&& checkNull(objBO.getAdmAppln().getPersonalData().getParentPh2())!=""
				&& checkNull(objBO.getAdmAppln().getPersonalData().getParentPh3())!=""
					&& checkNull(objBO.getAdmAppln().getPersonalData().getParentMob1())!=""
						&& checkNull(objBO.getAdmAppln().getPersonalData().getParentMob2())!=""){
			objTo.setMotherPhone(objBO.getAdmAppln().getPersonalData()
					.getParentPh1()+"-"+objBO.getAdmAppln().getPersonalData()
							.getParentPh2()+"-"+objBO.getAdmAppln().getPersonalData()
									.getParentPh3()
					+ "/"
					+ objBO.getAdmAppln().getPersonalData()
							.getParentMob1()+"-"+objBO.getAdmAppln().getPersonalData()
									.getParentMob2()+objBO.getAdmAppln().getPersonalData().getParentMob3());
		}
		else if(checkNull(objBO.getAdmAppln().getPersonalData().getParentPh1())==""
			&& checkNull(objBO.getAdmAppln().getPersonalData().getParentPh2())==""
				&& checkNull(objBO.getAdmAppln().getPersonalData().getParentPh3())==""){
			objTo.setMotherPhone(objBO.getAdmAppln().getPersonalData()
					.getParentMob1()+"-"+objBO.getAdmAppln().getPersonalData()
							.getParentMob2());
		}
		else{
			objTo.setMotherPhone(checkNull(objBO.getAdmAppln().getPersonalData()
					.getParentPh1())+"-"+checkNull(objBO.getAdmAppln().getPersonalData()
							.getParentPh2())+"-"+checkNull(objBO.getAdmAppln().getPersonalData()
									.getParentPh3()));
		}*/
		
		objTo.setMotherPhone(fatherPHone);
		
		if(!objTo.getFatherName().contains("Mr") || !objTo.getFatherName().contains("MR")){
		if(objBO.getAdmAppln().getAdmapplnAdditionalInfo()!=null && !objBO.getAdmAppln().getAdmapplnAdditionalInfo().isEmpty()){
			Iterator<AdmapplnAdditionalInfo> iterator = objBO.getAdmAppln().getAdmapplnAdditionalInfo().iterator();
			while (iterator.hasNext()) {
				AdmapplnAdditionalInfo admapplnAdditionalInfo = (AdmapplnAdditionalInfo) iterator .next();
				String fatherName = objTo.getFatherName();
				if(admapplnAdditionalInfo.getTitleFather()!=null && !admapplnAdditionalInfo.getTitleFather().isEmpty())
					objTo.setFatherName(admapplnAdditionalInfo.getTitleFather()+". "+fatherName);
				else
					objTo.setFatherName(fatherName);
			}
			}
		}
		if(!objTo.getMotherName().contains("Mrs") || !objTo.getMotherName().contains("MRS")){
			if(objBO.getAdmAppln().getAdmapplnAdditionalInfo()!=null && !objBO.getAdmAppln().getAdmapplnAdditionalInfo().isEmpty()){
				Iterator<AdmapplnAdditionalInfo> iterator = objBO.getAdmAppln().getAdmapplnAdditionalInfo().iterator();
				while (iterator.hasNext()) {
					AdmapplnAdditionalInfo admapplnAdditionalInfo = (AdmapplnAdditionalInfo) iterator .next();
					String motherName = objTo.getMotherName();
					if(admapplnAdditionalInfo.getTitleMother()!=null && !admapplnAdditionalInfo.getTitleMother().isEmpty())
						objTo.setMotherName(admapplnAdditionalInfo.getTitleMother()+". "+motherName);
					else
						objTo.setMotherName(motherName);
				}
				}
			}
		objTo.setRecommendedBy(objBO.getAdmAppln().getPersonalData().getRecommendedBy());
		return objTo;
	}

	private String checkNull(String value) {
		if (value != null && value.trim().length() > 0) {
			return value;
		}
		return "";
	}

	
	public List<RemarcksTO> convertBOToTORemarks(List remarcks) {
		ArrayList<RemarcksTO> list = new ArrayList<RemarcksTO>();
		RemarcksTO objTo = null;
		Iterator<Object> itr = remarcks.iterator();
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			objTo = new RemarcksTO();
			if (obj[0] != null) {
				objTo.setDate(CommonUtil.getStringDate(CommonUtil
						.ConvertStringToSQLDateValue((obj[0].toString()))));
			}
			if (obj[1] != null) {
				objTo.setDetails(obj[1].toString());
			}
			if (obj[2] != null) {
				objTo.setEnteredBy(obj[2].toString());
			}else{
				objTo.setEnteredBy(obj[4].toString());
			}
			if (obj[3] != null) {
				objTo.setId(Integer.parseInt(obj[3].toString()));
			}
			list.add(objTo);
		}
		return list;
	}

	public List<CourseDetailsTO> convertBoToCourseTO(List<StudentWiseSubjectSummaryTO> summaryList, String Course, Map<Integer,Integer> orderMap, DisciplinaryDetailsForm objForm,Integer studentId) throws Exception{
		ArrayList<CourseDetailsTO> list = new ArrayList<CourseDetailsTO>();
		boolean isSortRequired = true;
		float totalPresent = 0;
		float totalConducted = 0;
		float totalLeaveApproved = 0;
		float totalCoCurricularLeave = 0;
		float totalAggrPer = 0;
		//start by giri
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
		int classId = studentWiseAttendanceSummaryTransaction.getClassId(studentId);
		List<Integer> examIdList = studentWiseAttendanceSummaryTransaction.getExamPublishedIds(classId);
		//end by giri
		for (StudentWiseSubjectSummaryTO objto : summaryList) {
			/*float percentage = ((objto.getClassesPresent()
					+ objto.getLeaveApproved() + objto.getCocurricularLeave()) * 100)
					/ objto.getConductedClasses();*/
			float present = objto.getClassesPresent();
			float conducted = objto.getConductedClasses();
//			float lApproved = objto.getLeaveApproved();
			float lApproved =0;
			float clApproved=0;
			clApproved = objto.getCocurricularLeave();
			float absent=conducted-(present + lApproved + clApproved);
			float percentage = ((present + lApproved + clApproved) * 100)/ conducted;
			percentage = CommonUtil.roundToDecimal(percentage, 2);
			
			if(objto.getSubjectID()!=null && orderMap.get(Integer.parseInt(objto.getSubjectID()))!= null){
				objto.setOrder(orderMap.get(Integer.parseInt(objto.getSubjectID())));
			}
			boolean flag=false;
			String subjectId=objto.getSubjectID();
			String subjectName=objto.getSubjectName();
			if(objto.getSubjectID()==null){
				flag=true;
				subjectId=objto.getActivityID();
				subjectName=objto.getAttendanceTypeName();
			}
			//start by giri
			List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOs=new ArrayList<ExamMarksEntryDetailsTO>();
				if(objto.getExamMarksEntryDetailsTOList()!=null){
				List<ExamMarksEntryDetailsTO> list1=objto.getExamMarksEntryDetailsTOList();
				Iterator<ExamMarksEntryDetailsTO> iterator=list1.iterator();
				while (iterator.hasNext()) {
					ExamMarksEntryDetailsTO examMarksEntryDetailsTO = (ExamMarksEntryDetailsTO) iterator.next();
					if(examIdList.contains(examMarksEntryDetailsTO.getExamId())){
						examMarksEntryDetailsTOs.add(examMarksEntryDetailsTO);
					}
				}
			}
			//end by giri
			list.add(new CourseDetailsTO(Course, subjectName,
					percentage, 00.00f, examMarksEntryDetailsTOs, objto.getOrder(),absent,objto.getAttendanceTypeID(),objto.getAttendanceID(),subjectId,String.valueOf(studentId),flag,clApproved));
			if(objto.getSubjectID()!=null && (orderMap.get(Integer.parseInt(objto.getSubjectID())) == null || 
				orderMap.get(Integer.parseInt(objto.getSubjectID())) == 0)){
				isSortRequired = false;
			}
			totalPresent = totalPresent + objto.getClassesPresent();
			totalConducted = totalConducted + objto.getConductedClasses();
//			totalLeaveApproved = totalLeaveApproved + objto.getLeaveApproved();
			totalCoCurricularLeave = totalCoCurricularLeave + objto.getCocurricularLeave();
		}
		if(isSortRequired){
			Collections.sort(list);
		}
	/*	totalAggrPer = ((totalPresent
				+ totalLeaveApproved + totalCoCurricularLeave) * 100)
				/ totalConducted;*/
		totalAggrPer = ((totalPresent + totalCoCurricularLeave) * 100) / totalConducted;
		
		objForm.setTotalAggrPer(CommonUtil.roundToDecimal(totalAggrPer, 2));
		objForm.setTotalPresent(totalPresent+totalLeaveApproved+totalCoCurricularLeave);
		objForm.setTotalConducted(totalConducted);
		return list;
	}

	public List<FeesTO> convertBoToFeesTO(List feeDetails)throws Exception {
		ArrayList<FeesTO> list = new ArrayList<FeesTO>();
		if (feeDetails != null && feeDetails.size() > 0) { 
			Iterator<Object> itr = feeDetails.iterator();
			FeesTO objTo = null;
			while (itr.hasNext()) {
				objTo = new FeesTO();
				Object[] obj = (Object[]) itr.next();
				Double totalFee=0.00;
				Double feePaid=0.00;
				Double concessionAmount=0.00;
				Double installmentAmountPaid=0.00;
				if (obj[0] != null) {
					objTo.setSchmeNo(obj[0].toString());
				}

				/*if (obj[0] != null) {
					objTo.setFeesPaid(obj[1].toString());
				}

				if (obj[0] != null) {
					objTo.setFeesPending(obj[2].toString());
				}*/
				if (obj[0] != null) {
					totalFee=Double.valueOf(obj[1].toString());
					objTo.setTotalFee(obj[1].toString());
				}

				if (obj[0] != null) {
					feePaid=Double.valueOf(obj[2].toString());
					objTo.setFeesPaid(obj[2].toString());
				}
				if (obj[0] != null) {
					concessionAmount=Double.parseDouble(obj[3].toString());
					objTo.setConcessionGiven(obj[3].toString());
				}
				if (obj[0] != null) {
					objTo.setInstallmentGiven(obj[4].toString());
				}
				if (obj[0] != null) {
					String feePaymentId=obj[5].toString();
					List amountList=impl.getInstallmentAmount(feePaymentId);
					Iterator iterator = amountList.iterator();
					while (iterator.hasNext()) {
						Object object = (Object) iterator.next();
						installmentAmountPaid=installmentAmountPaid+Double.parseDouble(object.toString());
					}
					objTo.setInstallmentPaid(String.valueOf(installmentAmountPaid));
				}
				objTo.setBalanceAmount(String.valueOf(totalFee-(feePaid+concessionAmount+installmentAmountPaid)));
				list.add(objTo);
			}
		}
		
		return list;
	}

	public List<EdnQualificationTO> convertBotoTo(
			List<EdnQualification> educationDetailsByStudentId) throws Exception{
		List<EdnQualificationTO> finalList=new ArrayList<EdnQualificationTO>();
		if(educationDetailsByStudentId!=null && !educationDetailsByStudentId.isEmpty()){
			Iterator<EdnQualification> itr=educationDetailsByStudentId.iterator();
			List<Integer> presentIds = new ArrayList<Integer>();
			while (itr.hasNext()) {
				EdnQualification ednQualificationBO = itr.next();

				EdnQualificationTO ednQualificationTO = new EdnQualificationTO();
				ednQualificationTO.setId(ednQualificationBO.getId());
				ednQualificationTO.setCreatedBy(ednQualificationBO
						.getCreatedBy());
				ednQualificationTO.setCreatedDate(ednQualificationBO
						.getCreatedDate());
				if (ednQualificationBO.getState() != null) {
					ednQualificationTO.setStateId(String
							.valueOf(ednQualificationBO.getState().getId()));
					ednQualificationTO.setStateName(ednQualificationBO
							.getState().getName());
				} else if (ednQualificationBO.getIsOutsideIndia() != null
						&& ednQualificationBO.getIsOutsideIndia()) {
					ednQualificationTO.setStateId(CMSConstants.OUTSIDE_INDIA);
					ednQualificationTO.setStateName(CMSConstants.OUTSIDE_INDIA);
				}
				// copy doc type exam
				if (ednQualificationBO.getDocTypeExams() != null) {
					ednQualificationTO.setSelectedExamId(String
							.valueOf(ednQualificationBO.getDocTypeExams()
									.getId()));
					if (ednQualificationBO.getDocTypeExams().getName() != null)
						ednQualificationTO.setSelectedExamName(String
								.valueOf(ednQualificationBO.getDocTypeExams()
										.getName()));
				}
				if (ednQualificationBO.getDocChecklist() != null
						&& ednQualificationBO.getDocChecklist().getDocType() != null) {
					AdmissionFormHandler handler = AdmissionFormHandler
							.getInstance();
					List<DocTypeExamsTO> examtos = handler
							.getDocExamsByID(ednQualificationBO
									.getDocChecklist().getDocType().getId());
					ednQualificationTO.setExamTos(examtos);
					if (examtos != null && !examtos.isEmpty())
						ednQualificationTO.setExamRequired(true);
				}

				if (ednQualificationBO.getDocChecklist() != null
						&& ednQualificationBO.getDocChecklist()
								.getIsPreviousExam())
					ednQualificationTO.setLastExam(true);
				if (ednQualificationBO.getDocChecklist() != null
						&& ednQualificationBO.getDocChecklist()
								.getIsExamRequired())
					ednQualificationTO.setExamConfigured(true);
				if (ednQualificationBO.getDocChecklist() != null
						&& ednQualificationBO.getDocChecklist().getDocType() != null) {
					ednQualificationTO.setDocCheckListId(ednQualificationBO
							.getDocChecklist().getId());
					presentIds.add(ednQualificationBO.getDocChecklist()
							.getDocType().getId());
					ednQualificationTO.setDisplayOrder(ednQualificationBO.getDocChecklist() .getDocType().getDisplayOrder());
					ednQualificationTO.setDocName(ednQualificationBO
							.getDocChecklist().getDocType().getName());
					ednQualificationTO.setSemesterWise(false);
					ednQualificationTO.setConsolidated(true);
					if (ednQualificationBO.getDocChecklist() != null
							&& ednQualificationBO.getDocChecklist()
									.getIsActive()
							&& ednQualificationBO.getDocChecklist()
									.getIsMarksCard()
							&& !ednQualificationBO.getDocChecklist()
									.getIsConsolidatedMarks()
							&& ednQualificationBO.getDocChecklist()
									.getIsSemesterWise()) {
						ednQualificationTO.setLanguage(ednQualificationBO
								.getDocChecklist().getIsIncludeLanguage());
						ednQualificationTO.setSemesterWise(true);
						ednQualificationTO.setConsolidated(false);
						Set<ApplicantMarksDetails> detailMarks = ednQualificationBO
								.getApplicantMarksDetailses();
						if (detailMarks != null && !detailMarks.isEmpty()) {
							Set<ApplicantMarkDetailsTO> markdetails = new HashSet<ApplicantMarkDetailsTO>();
							ApplicantMarksDetails detailMarkBO = null;
							Iterator<ApplicantMarksDetails> markItr = detailMarks
									.iterator();
							while (markItr.hasNext()) {
								detailMarkBO = (ApplicantMarksDetails) markItr
										.next();
								ApplicantMarkDetailsTO markTO = new ApplicantMarkDetailsTO();
								convertApplicantMarkBOtoTO(detailMarkBO,
										markTO, ednQualificationTO.isLastExam());
								markdetails.add(markTO);
							}
							if (markdetails != null && !markdetails.isEmpty()) {
								ednQualificationTO.setSemesterList(markdetails);
							} else {

								for (int i = 1; i <= CMSConstants.MAX_CANDIDATE_SEMESTERS; i++) {
									ApplicantMarkDetailsTO to = new ApplicantMarkDetailsTO();
									to.setSemesterNo(i);
									to.setLastExam(ednQualificationTO
											.isLastExam());
									markdetails.add(to);
								}
								ednQualificationTO.setSemesterList(markdetails);

							}
						} else {
							Set<ApplicantMarkDetailsTO> markdetails = new HashSet<ApplicantMarkDetailsTO>();
							for (int i = 1; i <= CMSConstants.MAX_CANDIDATE_SEMESTERS; i++) {
								ApplicantMarkDetailsTO to = new ApplicantMarkDetailsTO();
								to.setSemesterNo(i);
								to.setSemesterName("Semester" + i);
								to.setLastExam(ednQualificationTO.isLastExam());
								markdetails.add(to);
							}
							ednQualificationTO.setSemesterList(markdetails);
						}
					} else if (ednQualificationBO.getDocChecklist() != null
							&& ednQualificationBO.getDocChecklist()
									.getIsActive()
							&& ednQualificationBO.getDocChecklist()
									.getIsMarksCard()
							&& !ednQualificationBO.getDocChecklist()
									.getIsConsolidatedMarks()
							&& !ednQualificationBO.getDocChecklist()
									.getIsSemesterWise()) {
						ednQualificationTO.setConsolidated(false);
						Set<CandidateMarks> detailMarks = ednQualificationBO
								.getCandidateMarkses();
						if (detailMarks != null && !detailMarks.isEmpty()) {
							CandidateMarks detailMarkBO = null;
							Iterator<CandidateMarks> markItr = detailMarks
									.iterator();
							while (markItr.hasNext()) {
								detailMarkBO = (CandidateMarks) markItr.next();
								CandidateMarkTO markTO = new CandidateMarkTO();
								convertDetailMarkBOtoTO(detailMarkBO, markTO);
								ednQualificationTO.setDetailmark(markTO);
							}
						} else {
							ednQualificationTO.setDetailmark(null);
						}
					}
				}
				if (ednQualificationBO.getUniversityOthers() != null
						&& !ednQualificationBO.getUniversityOthers().isEmpty()) {
					ednQualificationTO.setUniversityId(DisciplinaryDetailsHelper.OTHER);
					ednQualificationTO.setUniversityOthers(ednQualificationBO
							.getUniversityOthers());
					ednQualificationTO.setUniversityName(ednQualificationBO
							.getUniversityOthers());
				} else if (ednQualificationBO.getUniversity() != null
						&& ednQualificationBO.getUniversity().getId() != 0) {
					ednQualificationTO
							.setUniversityId(String.valueOf(ednQualificationBO
									.getUniversity().getId()));
					ednQualificationTO.setUniversityName(ednQualificationBO
							.getUniversity().getName());
				}
				if (ednQualificationBO.getInstitutionNameOthers() != null
						&& !ednQualificationBO.getInstitutionNameOthers()
								.isEmpty()) {
					ednQualificationTO
							.setInstitutionId(DisciplinaryDetailsHelper.OTHER);
					ednQualificationTO.setInstitutionName(ednQualificationBO
							.getInstitutionNameOthers());
					ednQualificationTO.setOtherInstitute(ednQualificationBO
							.getInstitutionNameOthers());
				} else if (ednQualificationBO.getCollege() != null
						&& ednQualificationBO.getCollege().getId() != 0) {
					ednQualificationTO.setInstitutionId(String
							.valueOf(ednQualificationBO.getCollege().getId()));
					ednQualificationTO.setInstitutionName(ednQualificationBO
							.getCollege().getName());
				}
				if (ednQualificationBO.getYearPassing() != null)
					ednQualificationTO.setYearPassing(ednQualificationBO
							.getYearPassing());
				if (ednQualificationBO.getMonthPassing() != null)
					ednQualificationTO.setMonthPassing(ednQualificationBO
							.getMonthPassing());
				ednQualificationTO.setPreviousRegNo(ednQualificationBO
						.getPreviousRegNo());
				if (ednQualificationBO.getMarksObtained() != null) {
					ednQualificationTO.setMarksObtained(String.valueOf(String
							.valueOf(ednQualificationBO.getMarksObtained()
									.doubleValue())));
				}
				if (ednQualificationBO.getTotalMarks() != null) {
					ednQualificationTO.setTotalMarks(String.valueOf(String
							.valueOf(ednQualificationBO.getTotalMarks()
									.doubleValue())));
				}
				ednQualificationTO.setNoOfAttempts(ednQualificationBO
						.getNoOfAttempts());

				if (UniversityHandler.getInstance().getUniversity() != null) {
					List<UniversityTO> universityList = UniversityHandler
							.getInstance().getUniversity();
					if (universityList != null
							&& ednQualificationTO.getUniversityId() != null
							&& !ednQualificationTO.getUniversityId()
									.equalsIgnoreCase(DisciplinaryDetailsHelper.OTHER)) {
						ednQualificationTO.setUniversityList(universityList);
						if (ednQualificationTO.getInstitutionId() != null
								&& !ednQualificationTO.getInstitutionId()
										.equalsIgnoreCase(
												DisciplinaryDetailsHelper.OTHER)) {
							Iterator<UniversityTO> colItr = universityList
									.iterator();
							while (colItr.hasNext()) {
								UniversityTO unTO = (UniversityTO) colItr
										.next();
								if (unTO.getId() == Integer
										.parseInt(ednQualificationTO
												.getUniversityId())) {
									ednQualificationTO.setInstituteList(unTO
											.getCollegeTos());
								}
							}
						}
					}

				}
				finalList.add(ednQualificationTO);
			}
		}
		Collections.sort(finalList);
		return finalList;
	}
	/**
	 * @param detailMarkBO
	 * @param markTO
	 * @param lastExam
	 */
	private void convertApplicantMarkBOtoTO(ApplicantMarksDetails detailMarkBO,
			ApplicantMarkDetailsTO markTO, boolean lastExam) {
		if (detailMarkBO != null) {
			markTO.setId(detailMarkBO.getId());
			markTO.setEdnQualificationId(detailMarkBO.getEdnQualification()
					.getId());
			markTO.setLastExam(lastExam);
			markTO.setMarksObtained(String.valueOf(detailMarkBO
					.getMarksObtained()));
			markTO.setMaxMarks(String.valueOf(detailMarkBO.getMaxMarks()));
			markTO.setSemesterName(detailMarkBO.getSemesterName());
			markTO.setSemesterNo(detailMarkBO.getSemesterNo());
			if (detailMarkBO.getMarksObtainedLanguagewise() != null)
				markTO.setMarksObtained_languagewise(String
						.valueOf(detailMarkBO.getMarksObtainedLanguagewise()));
			if (detailMarkBO.getMaxMarksLanguagewise() != null)
				markTO.setMaxMarks_languagewise(String.valueOf(detailMarkBO
						.getMaxMarksLanguagewise()));
		}
	}
	/**
	 * @param detailMarkBO
	 * @param markTO
	 */
	private void convertDetailMarkBOtoTO(CandidateMarks detailMarkBO,
			CandidateMarkTO markTO) {
		if (detailMarkBO != null) {

			if (detailMarkBO.getDetailedSubjects1() != null) {
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects1()
						.getId());
				markTO.setSubject1(detailMarkBO.getDetailedSubjects1()
						.getSubjectName());
				markTO.setSubject1Mandatory(true);
				markTO.setDetailedSubjects1(detailedSubjectsTO);
			} else if (detailMarkBO.getSubject1() != null
					&& detailMarkBO.getSubject1().length() != 0) {
				// setting others.
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(-1);
				markTO.setDetailedSubjects1(detailedSubjectsTO);
				markTO.setSubject1(detailMarkBO.getSubject1());
			}

			if (detailMarkBO.getDetailedSubjects2() != null) {
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects2()
						.getId());
				markTO.setSubject2(detailMarkBO.getDetailedSubjects2()
						.getSubjectName());
				markTO.setSubject2Mandatory(true);
				markTO.setDetailedSubjects2(detailedSubjectsTO);
			} else if (detailMarkBO.getSubject2() != null
					&& detailMarkBO.getSubject2().length() != 0) {
				// setting others.
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(-1);
				markTO.setDetailedSubjects2(detailedSubjectsTO);
				markTO.setSubject2(detailMarkBO.getSubject2());
			}

			if (detailMarkBO.getDetailedSubjects3() != null) {
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects3()
						.getId());
				markTO.setSubject3(detailMarkBO.getDetailedSubjects3()
						.getSubjectName());
				markTO.setSubject3Mandatory(true);
				markTO.setDetailedSubjects3(detailedSubjectsTO);
			} else if (detailMarkBO.getSubject3() != null
					&& detailMarkBO.getSubject3().length() != 0) {
				// setting others.
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(-1);
				markTO.setDetailedSubjects3(detailedSubjectsTO);
				markTO.setSubject3(detailMarkBO.getSubject3());
			}

			if (detailMarkBO.getDetailedSubjects4() != null) {
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects4()
						.getId());
				markTO.setSubject4(detailMarkBO.getDetailedSubjects4()
						.getSubjectName());
				markTO.setSubject4Mandatory(true);
				markTO.setDetailedSubjects4(detailedSubjectsTO);
			} else if (detailMarkBO.getSubject4() != null
					&& detailMarkBO.getSubject4().length() != 0) {
				// setting others.
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(-1);
				markTO.setDetailedSubjects4(detailedSubjectsTO);
				markTO.setSubject4(detailMarkBO.getSubject4());
			}

			if (detailMarkBO.getDetailedSubjects5() != null) {
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects5()
						.getId());
				markTO.setSubject5(detailMarkBO.getDetailedSubjects5()
						.getSubjectName());
				markTO.setSubject5Mandatory(true);
				markTO.setDetailedSubjects5(detailedSubjectsTO);
			} else if (detailMarkBO.getSubject5() != null
					&& detailMarkBO.getSubject5().length() != 0) {
				// setting others.
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(-1);
				markTO.setDetailedSubjects5(detailedSubjectsTO);
				markTO.setSubject5(detailMarkBO.getSubject5());
			}

			if (detailMarkBO.getDetailedSubjects6() != null) {
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects6()
						.getId());
				markTO.setSubject6(detailMarkBO.getDetailedSubjects6()
						.getSubjectName());
				markTO.setSubject6Mandatory(true);
				markTO.setDetailedSubjects6(detailedSubjectsTO);
			} else if (detailMarkBO.getSubject6() != null
					&& detailMarkBO.getSubject6().length() != 0) {
				// setting others.
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(-1);
				markTO.setDetailedSubjects6(detailedSubjectsTO);
				markTO.setSubject6(detailMarkBO.getSubject6());
			}

			if (detailMarkBO.getDetailedSubjects7() != null) {
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects7()
						.getId());
				markTO.setSubject7(detailMarkBO.getDetailedSubjects7()
						.getSubjectName());
				markTO.setSubject7Mandatory(true);
				markTO.setDetailedSubjects7(detailedSubjectsTO);
			} else if (detailMarkBO.getSubject7() != null
					&& detailMarkBO.getSubject7().length() != 0) {
				// setting others.
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(-1);
				markTO.setDetailedSubjects7(detailedSubjectsTO);
				markTO.setSubject7(detailMarkBO.getSubject7());
			}

			if (detailMarkBO.getDetailedSubjects8() != null) {
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects8()
						.getId());
				markTO.setSubject8(detailMarkBO.getDetailedSubjects8()
						.getSubjectName());
				markTO.setSubject8Mandatory(true);
				markTO.setDetailedSubjects8(detailedSubjectsTO);
			} else if (detailMarkBO.getSubject8() != null
					&& detailMarkBO.getSubject8().length() != 0) {
				// setting others.
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(-1);
				markTO.setDetailedSubjects8(detailedSubjectsTO);
				markTO.setSubject8(detailMarkBO.getSubject8());
			}

			if (detailMarkBO.getDetailedSubjects9() != null) {
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects9()
						.getId());
				markTO.setSubject9(detailMarkBO.getDetailedSubjects9()
						.getSubjectName());
				markTO.setSubject9Mandatory(true);
				markTO.setDetailedSubjects9(detailedSubjectsTO);
			} else if (detailMarkBO.getSubject9() != null
					&& detailMarkBO.getSubject9().length() != 0) {
				// setting others.
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(-1);
				markTO.setDetailedSubjects9(detailedSubjectsTO);
				markTO.setSubject9(detailMarkBO.getSubject9());
			}

			if (detailMarkBO.getDetailedSubjects10() != null) {
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects10()
						.getId());
				markTO.setSubject10(detailMarkBO.getDetailedSubjects10()
						.getSubjectName());
				markTO.setSubject10Mandatory(true);
				markTO.setDetailedSubjects10(detailedSubjectsTO);
			} else if (detailMarkBO.getSubject10() != null
					&& detailMarkBO.getSubject10().length() != 0) {
				// setting others.
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(-1);
				markTO.setDetailedSubjects10(detailedSubjectsTO);
				markTO.setSubject10(detailMarkBO.getSubject10());
			}

			if (detailMarkBO.getDetailedSubjects11() != null) {
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects11()
						.getId());
				markTO.setSubject11(detailMarkBO.getDetailedSubjects11()
						.getSubjectName());
				markTO.setSubject11Mandatory(true);
				markTO.setDetailedSubjects11(detailedSubjectsTO);
			} else if (detailMarkBO.getSubject11() != null
					&& detailMarkBO.getSubject11().length() != 0) {
				// setting others.
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(-1);
				markTO.setDetailedSubjects11(detailedSubjectsTO);
				markTO.setSubject11(detailMarkBO.getSubject11());
			}

			if (detailMarkBO.getDetailedSubjects12() != null) {
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects12()
						.getId());
				markTO.setSubject12(detailMarkBO.getDetailedSubjects12()
						.getSubjectName());
				markTO.setSubject12Mandatory(true);
				markTO.setDetailedSubjects12(detailedSubjectsTO);
			} else if (detailMarkBO.getSubject12() != null
					&& detailMarkBO.getSubject12().length() != 0) {
				// setting others.
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(-1);
				markTO.setDetailedSubjects12(detailedSubjectsTO);
				markTO.setSubject12(detailMarkBO.getSubject12());
			}

			if (detailMarkBO.getDetailedSubjects13() != null) {
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects13()
						.getId());
				markTO.setSubject13(detailMarkBO.getDetailedSubjects13()
						.getSubjectName());
				markTO.setSubject13Mandatory(true);
				markTO.setDetailedSubjects13(detailedSubjectsTO);
			} else if (detailMarkBO.getSubject13() != null
					&& detailMarkBO.getSubject13().length() != 0) {
				// setting others.
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(-1);
				markTO.setDetailedSubjects13(detailedSubjectsTO);
				markTO.setSubject13(detailMarkBO.getSubject13());
			}

			if (detailMarkBO.getDetailedSubjects14() != null) {
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects14()
						.getId());
				markTO.setSubject14(detailMarkBO.getDetailedSubjects14()
						.getSubjectName());
				markTO.setSubject14Mandatory(true);
				markTO.setDetailedSubjects14(detailedSubjectsTO);
			} else if (detailMarkBO.getSubject14() != null
					&& detailMarkBO.getSubject14().length() != 0) {
				// setting others.
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(-1);
				markTO.setDetailedSubjects14(detailedSubjectsTO);
				markTO.setSubject14(detailMarkBO.getSubject14());
			}

			if (detailMarkBO.getDetailedSubjects15() != null) {
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects15()
						.getId());
				markTO.setSubject15(detailMarkBO.getDetailedSubjects15()
						.getSubjectName());
				markTO.setSubject15Mandatory(true);
				markTO.setDetailedSubjects15(detailedSubjectsTO);
			} else if (detailMarkBO.getSubject15() != null
					&& detailMarkBO.getSubject15().length() != 0) {
				// setting others.
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(-1);
				markTO.setDetailedSubjects15(detailedSubjectsTO);
				markTO.setSubject15(detailMarkBO.getSubject15());
			}

			if (detailMarkBO.getDetailedSubjects16() != null) {
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects16()
						.getId());
				markTO.setSubject16(detailMarkBO.getDetailedSubjects16()
						.getSubjectName());
				markTO.setSubject16Mandatory(true);
				markTO.setDetailedSubjects16(detailedSubjectsTO);
			} else if (detailMarkBO.getSubject16() != null
					&& detailMarkBO.getSubject16().length() != 0) {
				// setting others.
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(-1);
				markTO.setDetailedSubjects16(detailedSubjectsTO);
				markTO.setSubject16(detailMarkBO.getSubject16());
			}

			if (detailMarkBO.getDetailedSubjects17() != null) {
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects17()
						.getId());
				markTO.setSubject17(detailMarkBO.getDetailedSubjects17()
						.getSubjectName());
				markTO.setSubject17Mandatory(true);
				markTO.setDetailedSubjects17(detailedSubjectsTO);
			} else if (detailMarkBO.getSubject17() != null
					&& detailMarkBO.getSubject17().length() != 0) {
				// setting others.
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(-1);
				markTO.setDetailedSubjects17(detailedSubjectsTO);
				markTO.setSubject17(detailMarkBO.getSubject17());
			}

			if (detailMarkBO.getDetailedSubjects18() != null) {
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects18()
						.getId());
				markTO.setSubject18(detailMarkBO.getDetailedSubjects18()
						.getSubjectName());
				markTO.setSubject18Mandatory(true);
				markTO.setDetailedSubjects18(detailedSubjectsTO);
			} else if (detailMarkBO.getSubject18() != null
					&& detailMarkBO.getSubject18().length() != 0) {
				// setting others.
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(-1);
				markTO.setDetailedSubjects18(detailedSubjectsTO);
				markTO.setSubject18(detailMarkBO.getSubject18());
			}

			if (detailMarkBO.getDetailedSubjects19() != null) {
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects19()
						.getId());
				markTO.setSubject19(detailMarkBO.getDetailedSubjects19()
						.getSubjectName());
				markTO.setSubject19Mandatory(true);
				markTO.setDetailedSubjects19(detailedSubjectsTO);
			} else if (detailMarkBO.getSubject19() != null
					&& detailMarkBO.getSubject19().length() != 0) {
				// setting others.
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(-1);
				markTO.setDetailedSubjects19(detailedSubjectsTO);
				markTO.setSubject19(detailMarkBO.getSubject19());
			}

			if (detailMarkBO.getDetailedSubjects20() != null) {
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects20()
						.getId());
				markTO.setSubject20(detailMarkBO.getDetailedSubjects20()
						.getSubjectName());
				markTO.setSubject20Mandatory(true);
				markTO.setDetailedSubjects20(detailedSubjectsTO);
			} else if (detailMarkBO.getSubject20() != null
					&& detailMarkBO.getSubject20().length() != 0) {
				// setting others.
				DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
				detailedSubjectsTO.setId(-1);
				markTO.setDetailedSubjects20(detailedSubjectsTO);
				markTO.setSubject20(detailMarkBO.getSubject20());
			}

			markTO.setId(detailMarkBO.getId());
			markTO.setCreatedBy(detailMarkBO.getCreatedBy());
			markTO.setCreatedDate(detailMarkBO.getCreatedDate());
			markTO.setSubject1(detailMarkBO.getSubject1());
			markTO.setSubject2(detailMarkBO.getSubject2());
			markTO.setSubject3(detailMarkBO.getSubject3());
			markTO.setSubject4(detailMarkBO.getSubject4());
			markTO.setSubject5(detailMarkBO.getSubject5());
			markTO.setSubject6(detailMarkBO.getSubject6());
			markTO.setSubject7(detailMarkBO.getSubject7());
			markTO.setSubject8(detailMarkBO.getSubject8());
			markTO.setSubject9(detailMarkBO.getSubject9());
			markTO.setSubject10(detailMarkBO.getSubject10());
			markTO.setSubject11(detailMarkBO.getSubject11());
			markTO.setSubject12(detailMarkBO.getSubject12());
			markTO.setSubject13(detailMarkBO.getSubject13());
			markTO.setSubject14(detailMarkBO.getSubject14());
			markTO.setSubject15(detailMarkBO.getSubject15());
			markTO.setSubject16(detailMarkBO.getSubject16());
			markTO.setSubject17(detailMarkBO.getSubject17());
			markTO.setSubject18(detailMarkBO.getSubject18());
			markTO.setSubject19(detailMarkBO.getSubject19());
			markTO.setSubject20(detailMarkBO.getSubject20());

			if (detailMarkBO.getSubject1TotalMarks() != null
					&& detailMarkBO.getSubject1TotalMarks().intValue() != 0)
				markTO.setSubject1TotalMarks(String.valueOf(detailMarkBO
						.getSubject1TotalMarks().intValue()));
			if (detailMarkBO.getSubject2TotalMarks() != null
					&& detailMarkBO.getSubject2TotalMarks().intValue() != 0)
				markTO.setSubject2TotalMarks(String.valueOf(detailMarkBO
						.getSubject2TotalMarks().intValue()));
			if (detailMarkBO.getSubject3TotalMarks() != null
					&& detailMarkBO.getSubject3TotalMarks().intValue() != 0)
				markTO.setSubject3TotalMarks(String.valueOf(detailMarkBO
						.getSubject3TotalMarks().intValue()));
			if (detailMarkBO.getSubject4TotalMarks() != null
					&& detailMarkBO.getSubject4TotalMarks().intValue() != 0)
				markTO.setSubject4TotalMarks(String.valueOf(detailMarkBO
						.getSubject4TotalMarks().intValue()));
			if (detailMarkBO.getSubject5TotalMarks() != null
					&& detailMarkBO.getSubject5TotalMarks().intValue() != 0)
				markTO.setSubject5TotalMarks(String.valueOf(detailMarkBO
						.getSubject5TotalMarks().intValue()));
			if (detailMarkBO.getSubject6TotalMarks() != null
					&& detailMarkBO.getSubject6TotalMarks().intValue() != 0)
				markTO.setSubject6TotalMarks(String.valueOf(detailMarkBO
						.getSubject6TotalMarks().intValue()));
			if (detailMarkBO.getSubject7TotalMarks() != null
					&& detailMarkBO.getSubject7TotalMarks().intValue() != 0)
				markTO.setSubject7TotalMarks(String.valueOf(detailMarkBO
						.getSubject7TotalMarks().intValue()));
			if (detailMarkBO.getSubject8TotalMarks() != null
					&& detailMarkBO.getSubject8TotalMarks().intValue() != 0)
				markTO.setSubject8TotalMarks(String.valueOf(detailMarkBO
						.getSubject8TotalMarks().intValue()));
			if (detailMarkBO.getSubject9TotalMarks() != null
					&& detailMarkBO.getSubject9TotalMarks().intValue() != 0)
				markTO.setSubject9TotalMarks(String.valueOf(detailMarkBO
						.getSubject9TotalMarks().intValue()));
			if (detailMarkBO.getSubject10TotalMarks() != null
					&& detailMarkBO.getSubject10TotalMarks().intValue() != 0)
				markTO.setSubject10TotalMarks(String.valueOf(detailMarkBO
						.getSubject10TotalMarks().intValue()));
			if (detailMarkBO.getSubject11TotalMarks() != null
					&& detailMarkBO.getSubject11TotalMarks().intValue() != 0)
				markTO.setSubject11TotalMarks(String.valueOf(detailMarkBO
						.getSubject11TotalMarks().intValue()));
			if (detailMarkBO.getSubject12TotalMarks() != null
					&& detailMarkBO.getSubject12TotalMarks().intValue() != 0)
				markTO.setSubject12TotalMarks(String.valueOf(detailMarkBO
						.getSubject12TotalMarks().intValue()));
			if (detailMarkBO.getSubject13TotalMarks() != null
					&& detailMarkBO.getSubject13TotalMarks().intValue() != 0)
				markTO.setSubject13TotalMarks(String.valueOf(detailMarkBO
						.getSubject13TotalMarks().intValue()));
			if (detailMarkBO.getSubject14TotalMarks() != null
					&& detailMarkBO.getSubject14TotalMarks().intValue() != 0)
				markTO.setSubject14TotalMarks(String.valueOf(detailMarkBO
						.getSubject14TotalMarks().intValue()));
			if (detailMarkBO.getSubject15TotalMarks() != null
					&& detailMarkBO.getSubject15TotalMarks().intValue() != 0)
				markTO.setSubject15TotalMarks(String.valueOf(detailMarkBO
						.getSubject15TotalMarks().intValue()));
			if (detailMarkBO.getSubject16TotalMarks() != null
					&& detailMarkBO.getSubject16TotalMarks().intValue() != 0)
				markTO.setSubject16TotalMarks(String.valueOf(detailMarkBO
						.getSubject16TotalMarks().intValue()));
			if (detailMarkBO.getSubject17TotalMarks() != null
					&& detailMarkBO.getSubject17TotalMarks().intValue() != 0)
				markTO.setSubject17TotalMarks(String.valueOf(detailMarkBO
						.getSubject17TotalMarks().intValue()));
			if (detailMarkBO.getSubject18TotalMarks() != null
					&& detailMarkBO.getSubject18TotalMarks().intValue() != 0)
				markTO.setSubject18TotalMarks(String.valueOf(detailMarkBO
						.getSubject18TotalMarks().intValue()));
			if (detailMarkBO.getSubject19TotalMarks() != null
					&& detailMarkBO.getSubject19TotalMarks().intValue() != 0)
				markTO.setSubject19TotalMarks(String.valueOf(detailMarkBO
						.getSubject19TotalMarks().intValue()));
			if (detailMarkBO.getSubject20TotalMarks() != null
					&& detailMarkBO.getSubject20TotalMarks().intValue() != 0)
				markTO.setSubject20TotalMarks(String.valueOf(detailMarkBO
						.getSubject20TotalMarks().intValue()));

			if (detailMarkBO.getSubject1ObtainedMarks() != null
					&& detailMarkBO.getSubject1ObtainedMarks().intValue() != 0)
				markTO.setSubject1ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject1ObtainedMarks().intValue()));
			if (detailMarkBO.getSubject2ObtainedMarks() != null
					&& detailMarkBO.getSubject2ObtainedMarks().intValue() != 0)
				markTO.setSubject2ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject2ObtainedMarks().intValue()));
			if (detailMarkBO.getSubject3ObtainedMarks() != null
					&& detailMarkBO.getSubject3ObtainedMarks().intValue() != 0)
				markTO.setSubject3ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject3ObtainedMarks().intValue()));
			if (detailMarkBO.getSubject4ObtainedMarks() != null
					&& detailMarkBO.getSubject4ObtainedMarks().intValue() != 0)
				markTO.setSubject4ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject4ObtainedMarks().intValue()));
			if (detailMarkBO.getSubject5ObtainedMarks() != null
					&& detailMarkBO.getSubject5ObtainedMarks().intValue() != 0)
				markTO.setSubject5ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject5ObtainedMarks().intValue()));
			if (detailMarkBO.getSubject6ObtainedMarks() != null
					&& detailMarkBO.getSubject6ObtainedMarks().intValue() != 0)
				markTO.setSubject6ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject6ObtainedMarks().intValue()));
			if (detailMarkBO.getSubject7ObtainedMarks() != null
					&& detailMarkBO.getSubject7ObtainedMarks().intValue() != 0)
				markTO.setSubject7ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject7ObtainedMarks().intValue()));
			if (detailMarkBO.getSubject8ObtainedMarks() != null
					&& detailMarkBO.getSubject8ObtainedMarks().intValue() != 0)
				markTO.setSubject8ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject8ObtainedMarks().intValue()));
			if (detailMarkBO.getSubject9ObtainedMarks() != null
					&& detailMarkBO.getSubject9ObtainedMarks().intValue() != 0)
				markTO.setSubject9ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject9ObtainedMarks().intValue()));
			if (detailMarkBO.getSubject10ObtainedMarks() != null
					&& detailMarkBO.getSubject10ObtainedMarks().intValue() != 0)
				markTO.setSubject10ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject10ObtainedMarks().intValue()));
			if (detailMarkBO.getSubject11ObtainedMarks() != null
					&& detailMarkBO.getSubject11ObtainedMarks().intValue() != 0)
				markTO.setSubject11ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject11ObtainedMarks().intValue()));
			if (detailMarkBO.getSubject12ObtainedMarks() != null
					&& detailMarkBO.getSubject12ObtainedMarks().intValue() != 0)
				markTO.setSubject12ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject12ObtainedMarks().intValue()));
			if (detailMarkBO.getSubject13ObtainedMarks() != null
					&& detailMarkBO.getSubject13ObtainedMarks().intValue() != 0)
				markTO.setSubject13ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject13ObtainedMarks().intValue()));
			if (detailMarkBO.getSubject14ObtainedMarks() != null
					&& detailMarkBO.getSubject14ObtainedMarks().intValue() != 0)
				markTO.setSubject14ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject14ObtainedMarks().intValue()));
			if (detailMarkBO.getSubject15ObtainedMarks() != null
					&& detailMarkBO.getSubject15ObtainedMarks().intValue() != 0)
				markTO.setSubject15ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject15ObtainedMarks().intValue()));
			if (detailMarkBO.getSubject16ObtainedMarks() != null
					&& detailMarkBO.getSubject16ObtainedMarks().intValue() != 0)
				markTO.setSubject16ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject16ObtainedMarks().intValue()));
			if (detailMarkBO.getSubject17ObtainedMarks() != null
					&& detailMarkBO.getSubject17ObtainedMarks().intValue() != 0)
				markTO.setSubject17ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject17ObtainedMarks().intValue()));
			if (detailMarkBO.getSubject18ObtainedMarks() != null
					&& detailMarkBO.getSubject18ObtainedMarks().intValue() != 0)
				markTO.setSubject18ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject18ObtainedMarks().intValue()));
			if (detailMarkBO.getSubject19ObtainedMarks() != null
					&& detailMarkBO.getSubject19ObtainedMarks().intValue() != 0)
				markTO.setSubject19ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject19ObtainedMarks().intValue()));
			if (detailMarkBO.getSubject20ObtainedMarks() != null
					&& detailMarkBO.getSubject20ObtainedMarks().intValue() != 0)
				markTO.setSubject20ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject20ObtainedMarks().intValue()));
			if (detailMarkBO.getTotalMarks() != null
					&& detailMarkBO.getTotalMarks().intValue() != 0)
				markTO.setTotalMarks(String.valueOf(detailMarkBO
						.getTotalMarks().intValue()));
			if (detailMarkBO.getTotalObtainedMarks() != null
					&& detailMarkBO.getTotalObtainedMarks().intValue() != 0)
				markTO.setTotalObtainedMarks(String.valueOf(detailMarkBO
						.getTotalObtainedMarks().intValue()));
		}
	}
	
	/**
	 * 
	 * @param attendanceSummaryForm
	 * @returns search criteria for student absence period details
	 */
	public String getAbsenceSearchCriteria(
			DisciplinaryDetailsForm disciplinaryDetailsForm) {
		StringBuffer absenceSearchCriteria = new StringBuffer("from AttendanceStudent attendancestudent " 
		+ " where attendancestudent.attendance.subject.id = " + Integer.parseInt(disciplinaryDetailsForm.getSubjectId())
		+ " and attendancestudent.student.id = " + Integer.parseInt(disciplinaryDetailsForm.getStudentID()) 
		+ " and attendancestudent.isPresent = 0 and  attendancestudent.attendance.isCanceled = 0");
//		if(attendanceSummaryForm.getAttendanceTypeId()!= null && !attendanceSummaryForm.getAttendanceTypeId().trim().isEmpty()){
//			absenceSearchCriteria.append(" and attendancestudent.attendance.attendanceType.id = " + Integer.parseInt(attendanceSummaryForm.getAttendanceTypeId())); 
//		}
		absenceSearchCriteria.append(" order by attendancestudent.attendance.attendanceDate");
		return absenceSearchCriteria.toString();
	}
	/**
	 * 
	 * @param attendanceStudentList
	 * @returns absence period informations
	 */
	
	public List<PeriodTO> populateAbsencePeriodInformations(
			List<AttendanceStudent> attendanceStudentList, DisciplinaryDetailsForm disciplinaryDetailsForm, boolean isSubjectAttendance,List<Integer> period) throws Exception{
			//log.info("Entering into populateAbsencePeriodInformations of StudentAttendanceSummaryHelper");
		Map<Date, PeriodTO> periodMap = new LinkedHashMap<Date, PeriodTO>();
		PeriodTO periodTO = null;
		if(attendanceStudentList!=null){
			Iterator<AttendanceStudent> iterator = attendanceStudentList.iterator();
			while (iterator.hasNext()) {
				AttendanceStudent attendanceStudent = iterator.next();
				if(attendanceStudent.getAttendance()!=null){
					if(isSubjectAttendance){
						if(attendanceStudent.getAttendance().getSubject()!=null && attendanceStudent.getAttendance().getSubject().getName()!=null){
							disciplinaryDetailsForm.setSubjectName(attendanceStudent.getAttendance().getSubject().getName());
						}
					}
					else{
						if(attendanceStudent.getAttendance().getActivity()!=null && attendanceStudent.getAttendance().getActivity().getName()!=null){
							disciplinaryDetailsForm.setActivityName(attendanceStudent.getAttendance().getActivity().getName());
						}
					}
					periodTO = new PeriodTO();
					periodTO.setDate((CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate
					(attendanceStudent.getAttendance().getAttendanceDate()!=null ? 
					attendanceStudent.getAttendance().getAttendanceDate():null), "dd-MMM-yyyy","dd-MMM-yyyy")));;

					if(attendanceStudent.getAttendance().getAttendancePeriods()!=null){
						Iterator<AttendancePeriod> periodIterator = attendanceStudent.getAttendance().getAttendancePeriods().iterator();
						while (periodIterator.hasNext()) {
							AttendancePeriod attendancePeriod = periodIterator.next();
							if(attendancePeriod.getPeriod()!=null && attendancePeriod.getPeriod().getPeriodName()!=null){
								if(period.contains(attendancePeriod.getPeriod().getId())){
								if(attendanceStudent.getAttendance().getAttendanceDate()!=null){
									if(!periodMap.containsKey(attendanceStudent.getAttendance().getAttendanceDate())){
										periodTO.setPeriodName(attendancePeriod.getPeriod().getPeriodName());
										if(attendanceStudent.getIsCoCurricularLeave()!=null && attendanceStudent.getIsCoCurricularLeave()){
											periodTO.setName("Co-CurricularLeave");
										}else if(attendanceStudent.getIsOnLeave()!=null && attendanceStudent.getIsOnLeave()){
											periodTO.setName("Leave");
										}else{
											periodTO.setName("-");
										}
										periodMap.put(attendanceStudent.getAttendance().getAttendanceDate(), periodTO);
									}
									else{
										periodTO = periodMap.get(attendanceStudent.getAttendance().getAttendanceDate());
										periodTO.setPeriodName(periodTO.getPeriodName()+", "+attendancePeriod.getPeriod().getPeriodName());
										if(attendanceStudent.getIsCoCurricularLeave()!=null && attendanceStudent.getIsCoCurricularLeave()){
											periodTO.setName(periodTO.getName()+",Co-CurricularLeave");
										}else if(attendanceStudent.getIsOnLeave()!=null && attendanceStudent.getIsOnLeave()){
											periodTO.setName(periodTO.getName()+",Leave");
										}else{
											periodTO.setName(periodTO.getName()+",-");
										}
										periodMap.put(attendanceStudent.getAttendance().getAttendanceDate(), periodTO);
									}	
								}
								}
							}
							
						}
					}
					periodMap.put(attendanceStudent.getAttendance().getAttendanceDate(), periodTO);
				}
			}
		}
		List<PeriodTO> periodList = new ArrayList<PeriodTO>(); 
		periodList.addAll(periodMap.values());
		//log.info("Leaving into populateAbsencePeriodInformations of StudentAttendanceSummaryHelper");
		return periodList;
	}

	
	
	
	
	
	/*public List<PeriodTO> populateAbsencePeriodInformations(
			List<AttendanceStudent> attendanceStudentList, DisciplinaryDetailsForm disciplinaryDetailsForm, boolean isSubjectAttendance,List<Integer> period) throws Exception{
		Map<Date, PeriodTO> periodMap = new LinkedHashMap<Date, PeriodTO>();
		PeriodTO periodTO = null;
		if(attendanceStudentList!=null){
			Iterator<AttendanceStudent> iterator = attendanceStudentList.iterator();
			while (iterator.hasNext()) {
				AttendanceStudent attendanceStudent = iterator.next();
				if(attendanceStudent.getAttendance()!=null){
					if(isSubjectAttendance){
						if(attendanceStudent.getAttendance().getSubject()!=null && attendanceStudent.getAttendance().getSubject().getName()!=null){
							disciplinaryDetailsForm.setSubjectName(attendanceStudent.getAttendance().getSubject().getName());
						}
					}
					else{
						if(attendanceStudent.getAttendance().getActivity()!=null && attendanceStudent.getAttendance().getActivity().getName()!=null){
							disciplinaryDetailsForm.setActivityName(attendanceStudent.getAttendance().getActivity().getName());
						}
					}
					periodTO = new PeriodTO();
					periodTO.setDate((CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate
					(attendanceStudent.getAttendance().getAttendanceDate()!=null ? 
					attendanceStudent.getAttendance().getAttendanceDate():null), "dd-MMM-yyyy","dd-MMM-yyyy")));;

					if(attendanceStudent.getAttendance().getAttendancePeriods()!=null){
						Iterator<AttendancePeriod> periodIterator = attendanceStudent.getAttendance().getAttendancePeriods().iterator();
						while (periodIterator.hasNext()) {
							AttendancePeriod attendancePeriod = periodIterator.next();
							if(attendancePeriod.getPeriod()!=null && attendancePeriod.getPeriod().getPeriodName()!=null){
								if(period.contains(attendancePeriod.getPeriod().getId())){
								if(attendanceStudent.getAttendance().getAttendanceDate()!=null){
									if(!periodMap.containsKey(attendanceStudent.getAttendance().getAttendanceDate())){
										periodTO.setPeriodName(attendancePeriod.getPeriod().getPeriodName());
										periodMap.put(attendanceStudent.getAttendance().getAttendanceDate(), periodTO);
									}
									else{
										periodTO = periodMap.get(attendanceStudent.getAttendance().getAttendanceDate());
										periodTO.setPeriodName(periodTO.getPeriodName()+", "+attendancePeriod.getPeriod().getPeriodName());
										periodMap.put(attendanceStudent.getAttendance().getAttendanceDate(), periodTO);
									}	
								}
								}
							}
							
						}
					}
					periodMap.put(attendanceStudent.getAttendance().getAttendanceDate(), periodTO);
				}
			}
		}
		List<PeriodTO> periodList = new ArrayList<PeriodTO>(); 
		periodList.addAll(periodMap.values());
		return periodList;
	}*/
	public void convertBOtoForm(List<ExamStudentDetentionRejoinDetails> examStudentDetentionRejoinDetail,DisciplinaryDetailsForm objForm){
		DateFormat format=new SimpleDateFormat("dd-MMM-yyyy");
		String dates=null;
		String regNo="";
		objForm.setOldRegNo("");
		Iterator it=examStudentDetentionRejoinDetail.iterator();
		while(it.hasNext()){
			ExamStudentDetentionRejoinDetails examStudentDetentionRejoinDetails=(ExamStudentDetentionRejoinDetails)it.next();
		if(examStudentDetentionRejoinDetails!=null){
			 Boolean isDiscontinued=examStudentDetentionRejoinDetails.getDiscontinued();
			    Boolean isDetention=examStudentDetentionRejoinDetails.getDetain();
			    Boolean isRejoin=examStudentDetentionRejoinDetails.getRejoin();
			if(!regNo.equalsIgnoreCase(examStudentDetentionRejoinDetails.getRegisterNo()))
			{
				regNo=examStudentDetentionRejoinDetails.getRegisterNo();
				objForm.setOldRegNo(regNo);
			    if(isDiscontinued!=null && isDiscontinued==true && isRejoin!=null && isRejoin==true){
				if(examStudentDetentionRejoinDetails.getDiscontinuedDate()!=null){
				 dates=format.format(examStudentDetentionRejoinDetails.getDiscontinuedDate());
				objForm.setDiscontinuedDate(dates);
				}
				 objForm.setDiscontinuedReason(examStudentDetentionRejoinDetails.getDiscontinueReason());
			     if(examStudentDetentionRejoinDetails.getRejoinDate()!=null){
				 dates=format.format(examStudentDetentionRejoinDetails.getRejoinDate());
				 objForm.setRejoinDate(dates);
			    }objForm.setRejoinReason(examStudentDetentionRejoinDetails.getRejoinReason());
				
			    objForm.setDetentionDate("");
				objForm.setDetentionReason("");
			   }else if(isDiscontinued!=null && isDiscontinued==true){
				    if(examStudentDetentionRejoinDetails.getDiscontinuedDate()!=null){
					 dates=format.format(examStudentDetentionRejoinDetails.getDiscontinuedDate());
					 objForm.setDiscontinuedDate(dates);
					 }
				objForm.setDiscontinuedReason(examStudentDetentionRejoinDetails.getDiscontinueReason());
				
				objForm.setDetentionDate("");
				objForm.setDetentionReason("");
				objForm.setRejoinDate("");
				objForm.setRejoinReason("");
			   }
			   if((isDetention!=null && isDetention==true)  && (isRejoin!=null && isRejoin==true)){
				if(examStudentDetentionRejoinDetails.getDetentionDate()!=null && examStudentDetentionRejoinDetails.getRejoinDate()!=null){
				dates=format.format(examStudentDetentionRejoinDetails.getDetentionDate());
				objForm.setDetentionDate(dates);
				//dates=null;
				dates=format.format(examStudentDetentionRejoinDetails.getRejoinDate());
				objForm.setRejoinDate(dates);}
				objForm.setRejoinReason(examStudentDetentionRejoinDetails.getRejoinReason());
				objForm.setDetentionReason(examStudentDetentionRejoinDetails.getDetentionReason());
				objForm.setDiscontinuedDate("");
				objForm.setDiscontinuedReason("");
			   }
			   else if(isDetention!=null && isDetention==true){
				if(examStudentDetentionRejoinDetails.getDetentionDate()!=null){
				dates=format.format(examStudentDetentionRejoinDetails.getDetentionDate());
				objForm.setDetentionDate(dates);}
				objForm.setDetentionReason(examStudentDetentionRejoinDetails.getDetentionReason());
			
				objForm.setDiscontinuedDate("");
				objForm.setDiscontinuedReason("");
				objForm.setRejoinDate("");
				objForm.setRejoinReason("");
			   }
			}
			
			else{
				String detentionDate="";
				String detentionReson="";
				String discontinuedDate="";
				String discontReason="";
				String rejoinDate="";
				String rejoinReason="";
				
				if(!objForm.getDetentionDate().isEmpty() && examStudentDetentionRejoinDetails.getDetentionDate()!=null && !objForm.getDetentionReason().isEmpty() && !examStudentDetentionRejoinDetails.getDetentionReason().isEmpty() && !objForm.getDiscontinuedDate().isEmpty() && examStudentDetentionRejoinDetails.getDiscontinuedDate()!=null && !objForm.getDiscontinuedReason().isEmpty() && !examStudentDetentionRejoinDetails.getDiscontinueReason().isEmpty() && !objForm.getRejoinDate().isEmpty() && examStudentDetentionRejoinDetails.getRejoinDate()!=null && !objForm.getRejoinReason().isEmpty() && !examStudentDetentionRejoinDetails.getRejoinReason().isEmpty()){
					detentionDate=objForm.getDetentionDate();
					
					detentionDate=detentionDate+" , "+format.format(examStudentDetentionRejoinDetails.getDetentionDate());
					detentionReson=objForm.getDetentionReason();
					detentionReson=detentionReson+" , "+examStudentDetentionRejoinDetails.getDetentionReason();
					discontinuedDate=objForm.getDiscontinuedDate();
					discontinuedDate=discontinuedDate+" , "+format.format(examStudentDetentionRejoinDetails.getDiscontinuedDate());
					discontReason=objForm.getDiscontinuedReason();
					discontReason=discontReason+" , "+examStudentDetentionRejoinDetails.getDiscontinueReason();
					rejoinDate=objForm.getRejoinDate();
					rejoinDate=rejoinDate+" , "+format.format(examStudentDetentionRejoinDetails.getRejoinDate());
					rejoinReason=objForm.getRejoinReason();
					rejoinReason=rejoinReason+" , "+examStudentDetentionRejoinDetails.getRejoinReason();
					objForm.setDetentionDate(detentionDate);
					objForm.setDetentionReason(detentionReson);
					objForm.setDiscontinuedDate(discontinuedDate);
					objForm.setDiscontinuedReason(discontReason);
					objForm.setRejoinDate(rejoinDate);
					objForm.setRejoinReason(rejoinReason);
				}else if(!objForm.getDetentionDate().isEmpty() && examStudentDetentionRejoinDetails.getDetentionDate()!=null && !objForm.getDetentionReason().isEmpty() && !examStudentDetentionRejoinDetails.getDetentionReason().isEmpty() &&  !objForm.getRejoinDate().isEmpty() && examStudentDetentionRejoinDetails.getRejoinDate()!=null && !objForm.getRejoinReason().isEmpty() && !examStudentDetentionRejoinDetails.getRejoinReason().isEmpty()){
					detentionDate=objForm.getDetentionDate();
					detentionDate=detentionDate+" , "+format.format(examStudentDetentionRejoinDetails.getDetentionDate());
					detentionReson=objForm.getDetentionReason();
					detentionReson=detentionReson+" , "+examStudentDetentionRejoinDetails.getDetentionReason();
					rejoinDate=objForm.getRejoinDate();
					rejoinDate=rejoinDate+" , "+format.format(examStudentDetentionRejoinDetails.getRejoinDate());
					rejoinReason=objForm.getRejoinReason();
					rejoinReason=rejoinReason+" , "+examStudentDetentionRejoinDetails.getRejoinReason();
					objForm.setDetentionDate(detentionDate);
					objForm.setDetentionReason(detentionReson);
					objForm.setRejoinDate(rejoinDate);
					objForm.setRejoinReason(rejoinReason);
				}else if(!objForm.getDiscontinuedDate().isEmpty() && examStudentDetentionRejoinDetails.getDiscontinuedDate()!=null && !objForm.getDiscontinuedReason().isEmpty() && !examStudentDetentionRejoinDetails.getDiscontinueReason().isEmpty() &&  !objForm.getRejoinDate().isEmpty() && examStudentDetentionRejoinDetails.getRejoinDate()!=null && !objForm.getRejoinReason().isEmpty() && !examStudentDetentionRejoinDetails.getRejoinReason().isEmpty()){
					discontinuedDate=objForm.getDiscontinuedDate();
					discontinuedDate=discontinuedDate+" , "+format.format(examStudentDetentionRejoinDetails.getDiscontinuedDate());
					discontReason=objForm.getDiscontinuedReason();
					discontReason=discontReason+" , "+examStudentDetentionRejoinDetails.getDiscontinueReason();
					rejoinDate=objForm.getRejoinDate();
					rejoinDate=rejoinDate+" , "+format.format(examStudentDetentionRejoinDetails.getRejoinDate());
					rejoinReason=objForm.getRejoinReason();
					rejoinReason=rejoinReason+" , "+examStudentDetentionRejoinDetails.getRejoinReason();
					objForm.setDiscontinuedDate(discontinuedDate);
					objForm.setDiscontinuedReason(discontReason);
					objForm.setRejoinDate(rejoinDate);
					objForm.setRejoinReason(rejoinReason);
				}
				else if(!objForm.getDetentionDate().isEmpty() && examStudentDetentionRejoinDetails.getDetentionDate()!=null && !objForm.getDetentionReason().isEmpty() && !examStudentDetentionRejoinDetails.getDetentionReason().isEmpty()){
					detentionDate=objForm.getDetentionDate();
					detentionDate=detentionDate+" , "+format.format(examStudentDetentionRejoinDetails.getDetentionDate());
					detentionReson=objForm.getDetentionReason();
					detentionReson=detentionReson+" , "+examStudentDetentionRejoinDetails.getDetentionReason();
					objForm.setDetentionDate(detentionDate);
					objForm.setDetentionReason(detentionReson);
				}
				else if(!objForm.getDiscontinuedDate().isEmpty() && examStudentDetentionRejoinDetails.getDiscontinuedDate()!=null && !objForm.getDiscontinuedReason().isEmpty() && !examStudentDetentionRejoinDetails.getDiscontinueReason().isEmpty()){
					discontinuedDate=objForm.getDiscontinuedDate();
					discontinuedDate=discontinuedDate+" , "+format.format(examStudentDetentionRejoinDetails.getDiscontinuedDate());
					discontReason=objForm.getDiscontinuedReason();
					discontReason=discontReason+" , "+examStudentDetentionRejoinDetails.getDiscontinueReason();
					objForm.setDiscontinuedDate(discontinuedDate);
					objForm.setDiscontinuedReason(discontReason);
				}
				if(!objForm.getDiscontinuedDate().isEmpty() &&  !objForm.getDiscontinuedReason().isEmpty()  &&  !objForm.getRejoinDate().isEmpty() &&  !objForm.getRejoinReason().isEmpty() &&objForm.getDetentionDate().isEmpty()&& objForm.getDetentionReason().isEmpty()&& isDetention!=null && isDetention==true && isRejoin!=null && isRejoin==true){
					if(examStudentDetentionRejoinDetails.getDetentionDate()!=null){
						dates=format.format(examStudentDetentionRejoinDetails.getDetentionDate());
						objForm.setDetentionDate(dates);
						}
						objForm.setDetentionReason(examStudentDetentionRejoinDetails.getDetentionReason());
						rejoinDate=objForm.getRejoinDate();
						rejoinDate=rejoinDate+" , "+format.format(examStudentDetentionRejoinDetails.getRejoinDate());
						rejoinReason=objForm.getRejoinReason();
						rejoinReason=rejoinReason+" , "+examStudentDetentionRejoinDetails.getRejoinReason();
						objForm.setRejoinDate(rejoinDate);
						objForm.setRejoinReason(rejoinReason);
				}else if(!objForm.getDiscontinuedDate().isEmpty() && !objForm.getDiscontinuedReason().isEmpty()  && !objForm.getRejoinDate().isEmpty() && !objForm.getRejoinReason().isEmpty() && (isDiscontinued==null || isDiscontinued==false) && isDetention!=null && isDetention==true && (isRejoin==null || isRejoin==false) ){
					if(examStudentDetentionRejoinDetails.getDetentionDate()!=null){
						dates=format.format(examStudentDetentionRejoinDetails.getDetentionDate());
						objForm.setDetentionDate(dates);
						}
						objForm.setDetentionReason(examStudentDetentionRejoinDetails.getDetentionReason());
				}else if(!objForm.getDetentionDate().isEmpty()  && !objForm.getDetentionReason().isEmpty() &&  !objForm.getRejoinDate().isEmpty() &&  !objForm.getRejoinReason().isEmpty() && objForm.getDiscontinuedDate().isEmpty() && objForm.getDiscontinuedReason().isEmpty() && isDiscontinued!=null && isDiscontinued==true && isRejoin!=null && isRejoin==true){
					if(examStudentDetentionRejoinDetails.getDiscontinuedDate()!=null){
						 dates=format.format(examStudentDetentionRejoinDetails.getDiscontinuedDate());
						objForm.setDiscontinuedDate(dates);
						}
						 objForm.setDiscontinuedReason(examStudentDetentionRejoinDetails.getDiscontinueReason());
						 rejoinDate=objForm.getRejoinDate();
							rejoinDate=rejoinDate+" , "+format.format(examStudentDetentionRejoinDetails.getRejoinDate());
							rejoinReason=objForm.getRejoinReason();
							rejoinReason=rejoinReason+" , "+examStudentDetentionRejoinDetails.getRejoinReason();
							objForm.setRejoinDate(rejoinDate);
							objForm.setRejoinReason(rejoinReason);
				}else if(!objForm.getDetentionDate().isEmpty()  && !objForm.getDetentionReason().isEmpty() &&  !objForm.getRejoinDate().isEmpty() &&  !objForm.getRejoinReason().isEmpty() && objForm.getDiscontinuedDate().isEmpty() && objForm.getDiscontinuedReason().isEmpty() && isDiscontinued!=null && isDiscontinued==true && (isRejoin==null || isRejoin==false) && (isDetention==null && isDetention==false )){
					if(examStudentDetentionRejoinDetails.getDiscontinuedDate()!=null){
						 dates=format.format(examStudentDetentionRejoinDetails.getDiscontinuedDate());
						objForm.setDiscontinuedDate(dates);
						}
						 objForm.setDiscontinuedReason(examStudentDetentionRejoinDetails.getDiscontinueReason());
				}
			}
		}
		else{
			objForm.setDetentionDate("");
			objForm.setDetentionReason("");
			objForm.setDiscontinuedDate("");
			objForm.setDiscontinuedReason("");
			objForm.setRejoinDate("");
			objForm.setRejoinReason("");
		}
		}
		//return objForm;
	}
	
	public List<StudentTO> convertStudentTOtoBO(List<Student> studentlist, List<Integer> studentPhotoList, Map<String, ExamStudentDetentionRejoinDetails> detentionRejoinMap, DisciplinaryDetailsForm stForm) throws ApplicationException, Exception {
		
		List<StudentTO> studentTos = new ArrayList<StudentTO>();
		DisciplinaryDetailsImpl impl = new DisciplinaryDetailsImpl();
		String name = "";
		if (studentlist != null) {
			Iterator<Student> stItr = studentlist.iterator();
			while (stItr.hasNext()) {
				name = "";
				Student student=(Student)stItr.next();
				StudentTO stto = new StudentTO();
				if (student.getAdmAppln() != null) {
					stto.setApplicationNo(student.getAdmAppln().getApplnNo());
					stto.setAppliedYear(student.getAdmAppln().getAppliedYear());
					if (student.getAdmAppln().getPersonalData() != null) {
						if (student.getAdmAppln().getPersonalData()
								.getFirstName() != null) {
							name = name
									+ student.getAdmAppln().getPersonalData()
											.getFirstName();
						}
						if (student.getAdmAppln().getPersonalData()
								.getMiddleName() != null) {
							name = name
									+ " "
									+ student.getAdmAppln().getPersonalData()
											.getMiddleName();
						}
						if (student.getAdmAppln().getPersonalData()
								.getLastName() != null) {
							name = name
									+ " "
									+ student.getAdmAppln().getPersonalData()
											.getLastName();
						}
						stto.setStudentName(name);
					}
					if(studentPhotoList.contains(student.getId()))
					stto.setIsPhoto(true);
				}
				stto.setRegisterNo(student.getRegisterNo());
				stto.setRollNo(student.getRollNo());
				stto.setId(student.getId()); // added for delete option
				

				if(student.getClassSchemewise()!=null)
					stto.setClassName(student.getClassSchemewise().getClasses().getName());
				
				
				if(student.getIsHide()!=null && student.getIsHide()==true){
					stto.setStatus(CMSConstants.ADMISSION_STUDENT_HIDDEN);
				}else 
				if(detentionRejoinMap.containsKey(String.valueOf(student.getId()))){
					ExamStudentDetentionRejoinDetails examStudentDetentionRejoinDetails=detentionRejoinMap.get(String.valueOf(student.getId()));
				if(examStudentDetentionRejoinDetails!=null){
					if(examStudentDetentionRejoinDetails.getDetain()!=null && examStudentDetentionRejoinDetails.getDetain()==true){
						if( examStudentDetentionRejoinDetails.getRejoin()!=null){
							if(examStudentDetentionRejoinDetails.getRejoin()!=true)
							stto.setStatus(CMSConstants.ADMISSION_STUDENT_DETAINED);
							}else 
							stto.setStatus(CMSConstants.ADMISSION_STUDENT_DETAINED);
						}else if(examStudentDetentionRejoinDetails.getDiscontinued()!=null && examStudentDetentionRejoinDetails.getDiscontinued()==true){ 
							if( examStudentDetentionRejoinDetails.getRejoin()!=null){
								if(examStudentDetentionRejoinDetails.getRejoin()!=true)
									stto.setStatus(CMSConstants.ADMISSION_STUDENT_DISCONTINUED);
								}else 
									stto.setStatus(CMSConstants.ADMISSION_STUDENT_DISCONTINUED);
							}else{
									stto.setStatus("");
							}
				}else{
					stto.setStatus("");
				}
				}else
					stto.setStatus("");
				
				//dIlIp code starts
		/*		if(!stForm.getRollRegNo().isEmpty() && stForm.getTempName().isEmpty()){
					List<StudentWiseSubjectSummaryTO> summaryList = StudentAttendanceSummaryHandler.getInstance().getSubjectWiseAttendanceListView(student.getId());
					if(!CMSConstants.LINK_FOR_CJC){
						 List<StudentWiseSubjectSummaryTO> studentActivitywiseAttendanceToList=StudentAttendanceSummaryHandler.getInstance().getActivityWiseAttendanceList(student.getId());
						 if(studentActivitywiseAttendanceToList!=null)
							 summaryList.addAll(studentActivitywiseAttendanceToList);
					 }
					
					float totalPresent = 0;
					float totalConducted = 0;
					float totalCoCurricularLeave = 0;
					float totalAggrPer = 0;
					for (StudentWiseSubjectSummaryTO objto : summaryList) {
						totalPresent = totalPresent + objto.getClassesPresent();
						totalConducted = totalConducted + objto.getConductedClasses();
						totalCoCurricularLeave = totalCoCurricularLeave + objto.getCocurricularLeave();
					}
					totalAggrPer = ((totalPresent + totalCoCurricularLeave) * 100) / totalConducted;
					stto.setTotalAggrPer(CommonUtil.roundToDecimal(totalAggrPer, 2));
					stForm.setDisplayPercentage("YES");
				}
				else
					stForm.setDisplayPercentage(null);
				//dIlIp code ends */
				
				// Bhargav code for showing Attendance Percentage 
				float totalWorkingDays = 0;
				float toatalPresentDays = 0;
				float totalAbsentDays = 0;
				float totalPercentages = 0f;
				
				int	studentId=impl.getStudentId(stForm.getRollRegNo());
			    int classId=impl.getClassId(studentId);
			    CurriculumSchemeDuration currBo = CurriculumSchemeHandler.getInstance().getCurriculumSchemeDurationByClassId(classId);
			    int acadamicYear = currBo.getAcademicYear();
			    int courseId=impl.getCourseId(classId);
			    List<Object[]> totalAttendence = impl.getAttendenceRecord(studentId,courseId,classId,acadamicYear);
			    if(totalAttendence != null){
					totalWorkingDays = DisciplinaryDetailsHelper.getInsatnce().getTotalWorkingDays(totalAttendence);
					toatalPresentDays = DisciplinaryDetailsHelper.getInsatnce().getTotalPresentDays(totalAttendence);
					totalAbsentDays = totalWorkingDays - toatalPresentDays;
					if(totalWorkingDays != 0 || toatalPresentDays != 0 || totalAbsentDays != 0){
						totalPercentages = CommonUtil.roundToDecimal(((toatalPresentDays/totalWorkingDays)*100), 2);
						stForm.setDisplayPercentage("YES");
						}else{
							stForm.setDisplayPercentage(null);
						}
					}
			    stto.setTotalAggrPer(totalPercentages);
				studentTos.add(stto);
				
			}
		}
		
		return studentTos;
	}

	
	public List<CourseDetailsTO> convertBoToCourseTOAdditional(List<StudentWiseSubjectSummaryTO> summaryList, String Course, Map<Integer,Integer> orderMap, DisciplinaryDetailsForm objForm,Integer studentId) {
		ArrayList<CourseDetailsTO> list = new ArrayList<CourseDetailsTO>();
		boolean isSortRequired = true;
		float totalPresent = 0;
		float totalConducted = 0;
		float totalLeaveApproved = 0;
		float totalCoCurricularLeave = 0;
		float totalAggrPer = 0;
		
		for (StudentWiseSubjectSummaryTO objto : summaryList) {
			/*float percentage = ((objto.getClassesPresent()
					+ objto.getLeaveApproved() + objto.getCocurricularLeave()) * 100)
					/ objto.getConductedClasses();*/
			float present = objto.getClassesPresent();
			float conducted = objto.getConductedClasses();
//			float lApproved = objto.getLeaveApproved();
			float lApproved =0;
			float clApproved =0;
			clApproved=objto.getCocurricularLeave();
			float absent=conducted-(present + lApproved + clApproved);
			float percentage = ((present + lApproved + clApproved) * 100)/ conducted;

			percentage = CommonUtil.roundToDecimal(percentage, 2);
			
			if(objto.getSubjectID()!=null && orderMap.get(Integer.parseInt(objto.getSubjectID()))!= null){
				objto.setOrder(orderMap.get(Integer.parseInt(objto.getSubjectID())));
			}
			boolean flag=false;
			String subjectId=objto.getSubjectID();
			String subjectName=objto.getSubjectName();
			if(objto.getSubjectID()==null){
				flag=true;
				subjectId=objto.getActivityID();
				subjectName=objto.getAttendanceTypeName();
			}
			list.add(new CourseDetailsTO(Course, subjectName,
					percentage, 00.00f, objto.getExamMarksEntryDetailsTOList(), objto.getOrder(),absent,objto.getAttendanceTypeID(),objto.getAttendanceID(),subjectId,String.valueOf(studentId),flag,clApproved));
			if(objto.getSubjectID()!=null && (orderMap.get(Integer.parseInt(objto.getSubjectID())) == null || 
				orderMap.get(Integer.parseInt(objto.getSubjectID())) == 0)){
				isSortRequired = false;
			}
			totalPresent = totalPresent + objto.getClassesPresent();
			totalConducted = totalConducted + objto.getConductedClasses();
//			totalLeaveApproved = totalLeaveApproved + objto.getLeaveApproved();
			totalCoCurricularLeave = totalCoCurricularLeave + objto.getCocurricularLeave();
		}
		if(isSortRequired){
			Collections.sort(list);
		}
		totalAggrPer = ((totalPresent
				+ totalLeaveApproved + totalCoCurricularLeave) * 100)
				/ totalConducted;
		//objForm.setTotalAggrPer(CommonUtil.roundToDecimal(totalAggrPer, 2));
		//objForm.setTotalPresent(totalPresent+totalLeaveApproved+totalCoCurricularLeave);
		//objForm.setTotalConducted(totalConducted);
		return list;
	}

	public RemarcksTO convertBOToTOEditRemarks(StudentRemarks studentRemarks) {
		RemarcksTO remarcksTO = new RemarcksTO();
		remarcksTO.setEnteredBy(studentRemarks.getCreatedBy());
		remarcksTO.setDetails(studentRemarks.getComments());
		
		return remarcksTO;
	}
	/**
	 * @param attList
	 * @return
	 */
	public List<StudentAttendanceTO> convertAttendanceStudentBotoTo(List<AttendanceStudent> attList,Map<String,Integer> posMap,String studentId,DisciplinaryDetailsForm objForm) throws Exception{
		Map<String,StudentAttendanceTO> stuMap=new HashMap<String, StudentAttendanceTO>();
		Map<String,Integer> subMap=new HashMap<String, Integer>();
		int totalCoLeave=0;
		int totalAbscent=0;
		if(attList!=null && !attList.isEmpty()){
			Iterator<AttendanceStudent> itr=attList.iterator();
			while (itr.hasNext()) {
				AttendanceStudent bo = (AttendanceStudent) itr.next();
				int hoursHeld=bo.getAttendance().getHoursHeld();
				String sub="";
				if(bo.getAttendance().getSubject()!=null){
					sub=bo.getAttendance().getSubject().getName();
					if(bo.getAttendance().getSubject().getCode()!=null)
						sub=sub+"("+bo.getAttendance().getSubject().getCode()+")";
				}else if(bo.getAttendance().getAttendanceType()!=null){
					sub=bo.getAttendance().getAttendanceType().getName();
				}
				
				if(stuMap.containsKey(bo.getAttendance().getAttendanceDate().toString())){
					StudentAttendanceTO to=stuMap.get(bo.getAttendance().getAttendanceDate().toString());
					boolean isCoLeave=false;
					if(bo.getIsCoCurricularLeave()!=null && bo.getIsCoCurricularLeave())
						isCoLeave=true;
					
					if(!isCoLeave){
						if(!subMap.containsKey(sub)){
							subMap.put(sub,hoursHeld);
						}else{
							int subHeld=subMap.remove(sub)+hoursHeld;
							subMap.put(sub, subHeld);
						}
					}
					
					if(isCoLeave)
						totalCoLeave=totalCoLeave+hoursHeld;
					else
						totalAbscent=totalAbscent+hoursHeld;
					
					
					List<PeriodTO> periodList=to.getPeriodList();
					List<Integer> toPosList=to.getToPosList();
					Set<AttendancePeriod> appPeriod=bo.getAttendance().getAttendancePeriods();
					Iterator<AttendancePeriod> appItr=appPeriod.iterator();
					while (appItr.hasNext()) {
						AttendancePeriod attendancePeriod = (AttendancePeriod) appItr.next();
						if(attendancePeriod.getPeriod().getClassSchemewise().getId()==bo.getStudent().getClassSchemewise().getId()){
							PeriodTO pto=new PeriodTO();
							if(attendancePeriod.getAttendance().getSubject()!=null)
								pto.setPeriodName(attendancePeriod.getAttendance().getSubject().getCode());
							else if(attendancePeriod.getAttendance().getAttendanceType()!=null)
								pto.setPeriodName(attendancePeriod.getAttendance().getAttendanceType().getName());
							pto.setCoLeave(isCoLeave);
							pto.setPosition(posMap.get(attendancePeriod.getPeriod().getPeriodName()));
							toPosList.add(posMap.get(attendancePeriod.getPeriod().getPeriodName()));
							periodList.add(pto);
						}
					}
					to.setPeriodList(periodList);
					to.setToPosList(toPosList);
					to.setHoursHeldByDay(to.getHoursHeldByDay()+hoursHeld);
					stuMap.put(bo.getAttendance().getAttendanceDate().toString(),to);
				}else{
					StudentAttendanceTO to=new StudentAttendanceTO();
					to.setDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(bo.getAttendance().getAttendanceDate()), DisciplinaryDetailsHelper.SQL_DATEFORMAT,DisciplinaryDetailsHelper.FROM_DATEFORMAT));
					to.setDay(CommonUtil.sayDayName(bo.getAttendance().getAttendanceDate()));
					boolean isCoLeave=false;
					if(bo.getIsCoCurricularLeave()!=null && bo.getIsCoCurricularLeave())
						isCoLeave=true;
					if(!isCoLeave){
						if(!subMap.containsKey(sub)){
							subMap.put(sub,hoursHeld);
						}else{
							int subHeld=subMap.remove(sub)+hoursHeld;
							subMap.put(sub, subHeld);
						}
					}
					if(isCoLeave)
						totalCoLeave=totalCoLeave+hoursHeld;
					else
						totalAbscent=totalAbscent+hoursHeld;
					List<PeriodTO> periodList=new ArrayList<PeriodTO>();
					List<Integer> toPosList=new ArrayList<Integer>();
					Set<AttendancePeriod> appPeriod=bo.getAttendance().getAttendancePeriods();
					Iterator<AttendancePeriod> appItr=appPeriod.iterator();
					while (appItr.hasNext()) {
						AttendancePeriod attendancePeriod = (AttendancePeriod) appItr.next();
						if(attendancePeriod.getPeriod().getClassSchemewise().getId()==bo.getStudent().getClassSchemewise().getId()){
							PeriodTO pto=new PeriodTO();
							if(attendancePeriod.getAttendance().getSubject()!=null)
								pto.setPeriodName(attendancePeriod.getAttendance().getSubject().getCode());
							else if(attendancePeriod.getAttendance().getAttendanceType()!=null)
								pto.setPeriodName(attendancePeriod.getAttendance().getAttendanceType().getName());
							pto.setCoLeave(isCoLeave);
							pto.setPosition(posMap.get(attendancePeriod.getPeriod().getPeriodName()));
							toPosList.add(posMap.get(attendancePeriod.getPeriod().getPeriodName()));
							periodList.add(pto);
						}
					}
					to.setPeriodList(periodList);
					to.setHoursHeldByDay(hoursHeld);
					to.setToPosList(toPosList);
					stuMap.put(bo.getAttendance().getAttendanceDate().toString(),to);
				}
				
			}
		}
		objForm.setTotalCoLeave(totalCoLeave);
		objForm.setAbscent(totalAbscent);
		objForm.setTotal(totalAbscent+totalCoLeave);
		
		List<StudentAttendanceTO> mainList=new ArrayList<StudentAttendanceTO>(stuMap.values());
		List<StudentAttendanceTO> finalList=new ArrayList<StudentAttendanceTO>();
		List<Integer> posList=new ArrayList<Integer>(posMap.values());
		if(!mainList.isEmpty()){
			Iterator<StudentAttendanceTO> itr=mainList.iterator();
			while (itr.hasNext()) {
				StudentAttendanceTO to = itr.next();
				List<PeriodTO> periodList=to.getPeriodList();
				List<Integer> positions=to.getToPosList();
				Iterator<Integer> pItr=posList.iterator();
				while (pItr.hasNext()) {
					Integer pos =pItr.next();
					if(!positions.contains(pos)){
						PeriodTO pto=new PeriodTO();
						pto.setPeriodName("");
						pto.setPosition(pos);
						periodList.add(pto);
					}
				}
				TeacherReportComparator comparator=new TeacherReportComparator();
				comparator.setCompare(1);
				Collections.sort(periodList,comparator);
				to.setPeriodList(periodList);
				finalList.add(to);
			}
		}
		Collections.sort(finalList);
		objForm.setSubMap(subMap);
		return finalList;
	}
	public String createQuery(String studentId)throws Exception{
		String query="select c.register_no, sum(if(c.pass_fail='Fail'and c.dont_consider_failure_total_result=0,1,0))as passfail," +
				" aggr_fail as aggregate_fail from consolidated_marks_card c left join student ON c.student_id = student.id left join (select sum(aggr_fail) as aggr_fail, acstudent_id as aggrstudent_id" +
				" from (select if(EXAM_exam_settings_course.aggregate_pass_prcntg >((sum(if(ac.section_id!=1 && ac.section_id!=3 && dont_add_in_total=0 && subType='Theory',ac.theoryObtain,0)+ " +
				" (if(ac.section_id!=1 && ac.section_id!=3 && dont_add_in_total=0 && subType='Practical',ac.practicalObtain,0)))/(sum(if(ac.section_id!=1 && ac.section_id!=3 && dont_add_in_total=0 && subType='Theory',ac.theoryMax,0)+ " +
				" (if(ac.section_id!=1 && ac.section_id!=3 && dont_add_in_total=0 && subType='Practical',ac.practicalMax,0)))))*100), 1, 0) as aggr_fail,ac.student_id as acstudent_id" +
				" from consolidated_marks_card ac inner join EXAM_exam_settings_course on ac.course_id=EXAM_exam_settings_course.course_id" +
				" inner join student s ON ac.student_id = s.id inner join class_schemewise csw ON s.class_schemewise_id = csw.id" +
				" inner join classes cls ON csw.class_id = cls.id inner join curriculum_scheme_duration csd ON csw.curriculum_scheme_duration_id = csd.id" +
				" left join curriculum_scheme csch ON csd.curriculum_scheme_id = csch.id inner join course cou on cou.id=cls.course_id" +
				" inner join program prg on cou.program_id=prg.id where section is not null and ac.student_id="+studentId+
				" group by ac.register_no, ac.class_id) as ag group by acstudent_id) as aggr on aggrstudent_id=c.student_id" +
				" where c.student_id not in (select student_id from EXAM_student_detention_rejoin_details where ((detain=1) or (discontinued=1)) and ((rejoin=0) or (rejoin is null)))" +
				" and c.student_id not in (select student_id from EXAM_update_exclude_withheld where (withheld=1 or exclude_from_results=1))" +
				" and c.student_id not in (select student_id from EXAM_block_unblock_hall_tkt_marks_card where hall_tkt_or_marks_card='M')" +
				" and section is not null and (student.is_hide is null or student.is_hide=0)and c.student_id="+studentId+
				" group by c.register_no";
		return query;
	}
	public void convertHlStudentAttendanceBoToTos(Map<Date,HlStudentAttendance> map3,DisciplinaryDetailsForm objForm)throws Exception{
		List<HlStudentAttendanceTo> hlStudentAttendanceToList=new ArrayList<HlStudentAttendanceTo>();
		Set<Date> dates=map3.keySet();
		Iterator<Date> iterator=dates.iterator();
		while (iterator.hasNext()) {
			Date date = (Date) iterator.next();
			HlStudentAttendanceTo hlStudentAttendanceTo=null;
			HlStudentAttendance hlStudentAttendance=map3.get(date);
			if(hlStudentAttendance.getMorning()!=null && !hlStudentAttendance.getMorning().isEmpty()){
				hlStudentAttendanceTo=new HlStudentAttendanceTo();
				hlStudentAttendanceTo.setDate(CommonUtil.formatDates(date));
				hlStudentAttendanceTo.setSession("Evening");
			}else if(hlStudentAttendance.getEvening()!=null && !hlStudentAttendance.getEvening().isEmpty()){
				hlStudentAttendanceTo=new HlStudentAttendanceTo();
				hlStudentAttendanceTo.setDate(CommonUtil.formatDates(date));
				hlStudentAttendanceTo.setSession("Morning");
			}else{
				hlStudentAttendanceTo=new HlStudentAttendanceTo();
				hlStudentAttendanceTo.setDate(CommonUtil.formatDates(date));
				hlStudentAttendanceTo.setSession("Morning,Evening");
			}
			hlStudentAttendanceToList.add(hlStudentAttendanceTo);
		}
		if(hlStudentAttendanceToList!=null && !hlStudentAttendanceToList.isEmpty()){
			objForm.setHlStudentAttendanceToList(hlStudentAttendanceToList);
		}
	}
	/**
	 * hostel absenties days
	 * @param morningAbsentDates
	 * @param eveningAbsentDates
	 * @param objForm
	 */
	public List<HlStudentAttendanceTo> getFinalHostelAbsentDetails(List<java.sql.Date> morningAbsentDates,
			List<java.sql.Date> eveningAbsentDates)throws Exception {
		List<HlStudentAttendanceTo> hlStudentAttendanceToList=new ArrayList<HlStudentAttendanceTo>();
		Map<java.sql.Date,HlStudentAttendanceTo> map=new HashMap<java.sql.Date, HlStudentAttendanceTo>();
		List<java.sql.Date> evengList=new ArrayList<java.sql.Date>();
		evengList.addAll(eveningAbsentDates);
		HlStudentAttendanceTo hlStudentAttendanceTo=null;
		if(morningAbsentDates!=null && !morningAbsentDates.isEmpty()){
			Iterator<java.sql.Date> iterator=morningAbsentDates.iterator();
			while (iterator.hasNext()) {
				java.sql.Date date = (java.sql.Date) iterator.next();
				hlStudentAttendanceTo=new HlStudentAttendanceTo();
				if(evengList.contains(date)){
					hlStudentAttendanceTo.setDate(CommonUtil.formatDates(date));
					hlStudentAttendanceTo.setSession("Morning,Evening");
					evengList.remove(date);
				}else{
					hlStudentAttendanceTo.setDate(CommonUtil.formatDates(date));
					hlStudentAttendanceTo.setSession("Morning");
				}
				map.put(date, hlStudentAttendanceTo);
			}
		}
		if(evengList!=null && !evengList.isEmpty()){
			Iterator<java.sql.Date> iterator=evengList.iterator();
			while (iterator.hasNext()) {
				java.sql.Date date = (java.sql.Date) iterator.next();
					hlStudentAttendanceTo=new HlStudentAttendanceTo();
					hlStudentAttendanceTo.setDate(CommonUtil.formatDates(date));
					hlStudentAttendanceTo.setSession("Evening");
					map.put(date, hlStudentAttendanceTo);
			}
		}
		map=CommonUtil.sortMapByKey(map);
		Set<java.sql.Date> keys=map.keySet();
		Iterator<java.sql.Date> iterator=keys.iterator();
		while (iterator.hasNext()) {
			Date date = (Date) iterator.next();
			HlStudentAttendanceTo hlStudentAttendanceTo2=map.get(date);
			hlStudentAttendanceToList.add(hlStudentAttendanceTo2);
		}
		return hlStudentAttendanceToList;
	}
	
	
	public void convertBoToSessionAttendance(List dateList,List sessionAttendanceList,DisciplinaryDetailsForm objForm)throws Exception {
		
		if (dateList != null && dateList.size() > 0 && sessionAttendanceList != null && sessionAttendanceList.size() > 0) { 
			
			int amsestot=0;
			int pmsestot=0;
			int amsesheld=0;
			int pmsesheld=0;
			int amattabs=0;
			int pmattabs=0;
			int amattabsheld=0;
			int pmattabsheld=0;
			
			int amattpr=0;
			int pmattpr=0;
			
			List<SessionAttendnceToAm> lam=new LinkedList<SessionAttendnceToAm>();
			List<SessionAttendnceToPm> lpm=new LinkedList<SessionAttendnceToPm>();
			
			String periodsam="";
			String leavesam="";
			String periodspm="";
			String leavespm="";
			
			Iterator itr=dateList.iterator();
			while(itr.hasNext()){
				
				
				Object[] dtobj = (Object[]) itr.next();
				String date=dtobj[0].toString();
				amsesheld=0;
				pmsesheld=0;
				amattabsheld=0;
				pmattabsheld=0;
				
				
				SessionAttendnceToAm toam=new SessionAttendnceToAm();
				toam.setAttdate(date);
				
				List<String> pnamesam=new LinkedList<String>();
				List<String> coLeavesam=new LinkedList<String>();
				
				SessionAttendnceToPm topm=new SessionAttendnceToPm();
				topm.setAttdate(date);
				
				List<String> pnamespm=new LinkedList<String>();
				List<String> coLeavespm=new LinkedList<String>();
				
				
				
				Iterator i=sessionAttendanceList.iterator();
				while(i.hasNext()){
					
				
					Object[] attobj = (Object[]) i.next();
					
					objForm.setClassName(attobj[8].toString());
					
					if(date.equalsIgnoreCase(attobj[0].toString())){
						
						if(attobj[4].toString().equalsIgnoreCase("am")){
							amsesheld=1;
							
							if((Boolean)attobj[5]){
								
							}else if((Boolean)attobj[6]||(Boolean)attobj[7]){
								
								if((Boolean)attobj[7]){
									String l="Co-CurricullarLeave";
									coLeavesam.add(l);
									String p=attobj[2].toString();
									pnamesam.add(p);
									
									periodsam=periodsam+p+",";
									leavesam=leavesam+"Co-CurricullarLeave,";
								}
								if((Boolean)attobj[6]){
									String l="On-Leave";
									coLeavesam.add(l);
									String p=attobj[2].toString();
									pnamesam.add(p);
									
									periodsam=periodsam+p+",";
									leavesam=leavesam+"On-Leave,";
								}
								
								
							}else{
								amattabsheld=1;
								System.out.println("-----absent am----- ");
								System.out.println(attobj[0].toString());
								
								String p=attobj[2].toString();
								pnamesam.add(p);
								periodsam=periodsam+p+",";
								leavesam=leavesam+"-,";
							}
							
							
						}else{
							pmsesheld=1;
							
								if((Boolean)attobj[5]){
									
								}else if((Boolean)attobj[6]||(Boolean)attobj[7]){
									
									if((Boolean)attobj[7]){
										String l="CoCurricullarLeave";
										coLeavespm.add(l);
										String p=attobj[2].toString();
										pnamespm.add(p);
										
										periodspm=periodspm+p+",";
										leavespm=leavespm+"Co-CurricullarLeave,";
									}
									if((Boolean)attobj[6]){
										String l="On-Leave";
										coLeavespm.add(l);
										String p=attobj[2].toString();
										pnamespm.add(p);
										
										periodspm=periodspm+p+",";
										leavespm=leavespm+"On-Leave,";
									}
								}else{
									pmattabsheld=1;
									System.out.println("-----absent pm----- ");
									System.out.println(attobj[0].toString());
									
									String p=attobj[2].toString();
									pnamespm.add(p);
									
									periodspm=periodspm+p+",";
									leavespm=leavespm+"-,";
								}
								
						}
						
						
							if(pnamesam.size()!=0)
							toam.setPnames(pnamesam);
							if(coLeavesam.size()!=0)
							toam.setCoLeaves(coLeavesam);
							if(pnamespm.size()!=0)
							topm.setPnames(pnamespm);
							if(coLeavespm.size()!=0)
							topm.setCoLeaves(coLeavespm);
							
							if(!periodsam.equalsIgnoreCase(""))
							toam.setPeriods(periodsam);
							if(!periodspm.equalsIgnoreCase(""))
							topm.setPeriods(periodspm);
							if(!leavesam.equalsIgnoreCase(""))
							toam.setLeaves(leavesam);
							if(!periodspm.equalsIgnoreCase(""))
							topm.setLeaves(leavespm);
					}
				
				}
				
				//raghu write newly if they didnot take attendance for any session we consider student was present for that session
				
				/*if(amsesheld!=1){
					amsesheld=1;
				}
				if(pmsesheld!=1){
					pmsesheld=1;
				}*/ 
				
				amattabs=amattabs+amattabsheld;
				pmattabs=pmattabs+pmattabsheld;
				amsestot=amsestot+amsesheld;
				pmsestot=pmsestot+pmsesheld;
				
				if((toam.getPnames()!=null && toam.getPnames().size()!=0 )|| (toam.getCoLeaves()!=null && toam.getCoLeaves().size()!=0))
					lam.add(toam);
				if((topm.getPnames()!=null && topm.getPnames().size()!=0 )|| (topm.getCoLeaves()!=null && topm.getCoLeaves().size()!=0))
					lpm.add(topm);
				
				periodsam="";
				leavesam="";
				periodspm="";
				leavespm="";
			}
			
			amattpr=amsestot-amattabs;
			pmattpr=pmsestot-pmattabs;
			float amper=CommonUtil.roundToDecimal((((float)amattpr/amsestot)*100),2);
			float pmper=CommonUtil.roundToDecimal((((float)pmattpr/pmsestot)*100),2);
			float totper=CommonUtil.roundToDecimal((amper+pmper)/2,2);
			float totper1=CommonUtil.roundToDecimal(((float)(amattpr+pmattpr)/(amsestot+pmsestot))*100, 2);
			
		System.out.println(amsestot+"-----tot----- "+pmsestot);
		System.out.println(amattpr+"-----pr----- "+pmattpr);
		System.out.println(amattabs+"----abs------ "+pmattabs);
		System.out.println((amper)+"----per------ "+(pmper));
		System.out.println((totper)+"----totper------ "+(totper1));
		
		objForm.setTotalAggrPer(totper1);
		objForm.setTotalPresent(amattpr+pmattpr);
		objForm.setTotalConducted(amsestot+pmsestot);
		
		objForm.setAmList(lam);
		objForm.setPmList(lpm);
		objForm.setTotpmattabs(new Integer(amattabs).toString());
		objForm.setTotamattabs(new Integer(pmattabs).toString());
		objForm.setTotamattper(new Float(amper).toString());
		objForm.setTotpmattper(new Float(pmper).toString());
		objForm.setAm("Morning Session");
		objForm.setPm("AfterNoon Session");
		}
		
		
		
	}
	
	public float getTotalWorkingDays(List<Object[]> totalAttendence) throws Exception{
		float totalWorkingDays = 0;
		Iterator<Object[]> itr = totalAttendence.iterator();
		while(itr.hasNext()){
			Object[] data = itr.next();
			if(data[5]!= null && !data[5].toString().isEmpty()){
				totalWorkingDays = totalWorkingDays + Float.parseFloat(data[5].toString());
			}
		}
		return totalWorkingDays;
	}
	
	public float getTotalPresentDays(List<Object[]> totalAttendence) throws Exception{
		float totalPresentDays = 0;
		float presentDays = 0;
		float halfDays = 0;
		Iterator<Object[]> itr = totalAttendence.iterator();
		while(itr.hasNext()){
			Object[] data = itr.next();
			if(data[22] != null && !data[22].toString().isEmpty()){
				presentDays = presentDays + Float.parseFloat(data[22].toString());
			}
			if(data[23] != null && !data[23].toString().isEmpty()){
				halfDays = halfDays + Float.parseFloat(data[23].toString());
			}
			totalPresentDays = presentDays + halfDays;
			
		}
		return totalPresentDays;
		
	}

	
}