package com.kp.cms.helpers.reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamStudentDetentionRejoinDetails;
import com.kp.cms.bo.exam.StudentPreviousClassHistory;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.reports.StudentDetailsReportForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.reports.StudentDetailsReportTO;
import com.kp.cms.utilities.CommonUtil;

/**
 * @author dIlIp
 *
 */
public class StudentDetailsReportHelper {
	
	private static final Log log = LogFactory.getLog(StudentDetailsReportHelper.class);
	private static volatile StudentDetailsReportHelper studentDetailsReportHelper = null;
	private static final String DISPLAY = "display";
	
	public static StudentDetailsReportHelper getInstance(){
		if(studentDetailsReportHelper == null){
			studentDetailsReportHelper = new StudentDetailsReportHelper();
			return studentDetailsReportHelper;
		}
		return studentDetailsReportHelper;
	}
	
	private static final String FROM_DATEFORMAT="dd-MM-yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";

	/**
	 * @param deanery
	 * @param progTypeId
	 * @param progId
	 * @param tempClassArray
	 * @param courseId
	 * @param status
	 * @param academicYear
	 * @param termNo 
	 * @param isFinalYearStudents 
	 * @return
	 */
	public static StringBuffer getSelectionSearchCriteria(StudentDetailsReportForm stForm) {
		
		StringBuffer stringBuffer = new StringBuffer("select st from Student st ");
		
		if(stForm.getIsCurrentYear().equalsIgnoreCase("previous"))
			stringBuffer = stringBuffer.append(" inner join st.studentPreviousClassesHistory sp where st.isActive=1 and sp.academicYear="+Integer.valueOf(stForm.getAcademicYear()));
		else
			stringBuffer.append(" where st.isActive=1 and st.classSchemewise.curriculumSchemeDuration.academicYear="+Integer.valueOf(stForm.getAcademicYear()));
		
		if(stForm.getCourseId()!=null && !stForm.getCourseId().isEmpty())
			stringBuffer = stringBuffer.append(" and st.admAppln.courseBySelectedCourseId.id="+Integer.parseInt(stForm.getCourseId()));
		
		if (stForm.getProgramId()!=null && !stForm.getProgramId().isEmpty())
			stringBuffer = stringBuffer.append(" and st.admAppln.courseBySelectedCourseId.program.id="+Integer.parseInt(stForm.getProgramId()));
		
		if(stForm.getDeaneryName()!=null && !stForm.getDeaneryName().isEmpty())
			stringBuffer = stringBuffer.append(" and st.admAppln.courseBySelectedCourseId.program.stream like '"+stForm.getDeaneryName()+"'");
		
		if (stForm.getProgramTypeId()!=null && !stForm.getProgramTypeId().isEmpty())
			stringBuffer = stringBuffer.append(" and st.admAppln.courseBySelectedCourseId.program.programType.id="+Integer.parseInt(stForm.getProgramTypeId()));
		
		if (stForm.getSelectedClass()!=null && !stForm.getSelectedClass().toString().isEmpty()) {
			StringBuffer buff = new StringBuffer();
			for(int i=0;i<stForm.getSelectedClass().length;i++){
				buff = buff.append(stForm.getSelectedClass()[i]);
				if(i<(stForm.getSelectedClass().length-1)){
					buff = buff.append(",");
				}
			}
			stringBuffer = stringBuffer.append(" and st.classSchemewise.classes.id IN ("+buff+")");
		}
		if(stForm.getSemester()!=null && !stForm.getSemester().isEmpty())
			stringBuffer = stringBuffer.append(" and st.classSchemewise.classes.termNumber="+Integer.parseInt(stForm.getSemester()));
		
		if(stForm.getStatus()!=null && !stForm.getStatus().isEmpty() && stForm.getStatus().equalsIgnoreCase("Cancelled"))
			stringBuffer = stringBuffer.append(" and st.admAppln.isCancelled=1");
		
		else if(stForm.getStatus()!=null && !stForm.getStatus().isEmpty() && stForm.getStatus().equalsIgnoreCase("Hidden"))
			stringBuffer = stringBuffer.append(" and st.isHide=1");
		
		else if(stForm.getStatus().isEmpty())
			stringBuffer = stringBuffer.append(" and st.admAppln.isCancelled=0 and (st.isHide=0 or st.isHide is null) and st.id not in (select ex.student.id from ExamStudentDetentionRejoinDetails ex where (ex.detain=1 or ex.discontinued=1) and (ex.rejoin is null or ex.rejoin = 0))");
		
		if(stForm.getIsFinalYrStudents()!=null && stForm.getIsFinalYrStudents().equalsIgnoreCase("Yes"))
			stringBuffer = stringBuffer.append(" and st.classSchemewise.curriculumSchemeDuration.curriculumScheme.noScheme=st.classSchemewise.classes.termNumber");
		
		if(stForm.getIsCurrentYear().equalsIgnoreCase("previous"))
			stringBuffer = stringBuffer.append(" group by st.id");
		
		return stringBuffer;
		
	}

	
	/**
	 * @param studentList
	 * @param examStudentDetentionRejoinDetails
	 * @return
	 */
	public static List<StudentTO> convertStudentBoTOTo(List<Student> studentList, List<ExamStudentDetentionRejoinDetails> examStudentDetentionRejoinDetails, String previousYears) {
		
		List<StudentTO> studentTOs = new ArrayList<StudentTO>();
		
		if(studentList!=null && !studentList.isEmpty()){
			Iterator<Student> iterator=studentList.iterator();
			
			while (iterator.hasNext()) {
				Student stu = (Student) iterator.next();
				StudentTO stuTO = new StudentTO();
				
				stuTO.setRegisterNo(stu.getRegisterNo()!=null?stu.getRegisterNo():"");
				stuTO.setStudentName(stu.getAdmAppln().getPersonalData().getFirstName()!=null?stu.getAdmAppln().getPersonalData().getFirstName():"");
				stuTO.setGender(stu.getAdmAppln().getPersonalData().getGender()!=null?stu.getAdmAppln().getPersonalData().getGender():"");
				/*if(stu.getClassSchemewise()!=null)
					stuTO.setClassName(stu.getClassSchemewise().getClasses()!=null?stu.getClassSchemewise().getClasses().getName():"");*/
				
				if(previousYears!=null && !previousYears.equalsIgnoreCase("0")){
					Set<StudentPreviousClassHistory> stuClassHistory =stu.getStudentPreviousClassesHistory();
					if(stuClassHistory!=null && !stuClassHistory.isEmpty()){
						List<StudentPreviousClassHistory> studentPreviousList =new ArrayList<StudentPreviousClassHistory>();
						studentPreviousList.addAll(stuClassHistory);
						Collections.sort(studentPreviousList);
						Iterator<StudentPreviousClassHistory> itrClass = studentPreviousList.iterator();
						while (itrClass.hasNext()) {
							StudentPreviousClassHistory studentPreviousClassHistory = (StudentPreviousClassHistory) itrClass.next();
							if(Integer.parseInt(previousYears) == studentPreviousClassHistory.getAcademicYear()){
								if(stu.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() == studentPreviousClassHistory.getAcademicYear()){
									stuTO.setClassName(stu.getClassSchemewise().getClasses()!=null?stu.getClassSchemewise().getClasses().getName():"");
									break;
								}
								else{
									stuTO.setClassName(studentPreviousClassHistory.getClasses()!=null?studentPreviousClassHistory.getClasses().getName():"");
									//break;
								}
							}
						}
					}
				}else if(stu.getClassSchemewise()!=null)
					stuTO.setClassName(stu.getClassSchemewise().getClasses()!=null?stu.getClassSchemewise().getClasses().getName():"");
				
				studentTOs.add(stuTO);
			}
		}
		else if(examStudentDetentionRejoinDetails!=null && !examStudentDetentionRejoinDetails.isEmpty()){

			Iterator<ExamStudentDetentionRejoinDetails> iterator=examStudentDetentionRejoinDetails.iterator();
			
			while (iterator.hasNext()) {
				ExamStudentDetentionRejoinDetails ex = (ExamStudentDetentionRejoinDetails) iterator.next();
				StudentTO stuTO = new StudentTO();
				
				stuTO.setRegisterNo(ex.getStudent().getRegisterNo()!=null?ex.getStudent().getRegisterNo():"");
				stuTO.setStudentName(ex.getStudent().getAdmAppln().getPersonalData().getFirstName()!=null?ex.getStudent().getAdmAppln().getPersonalData().getFirstName():"");
				stuTO.setGender(ex.getStudent().getAdmAppln().getPersonalData().getGender()!=null?ex.getStudent().getAdmAppln().getPersonalData().getGender():"");
				/*if(ex.getStudent().getClassSchemewise()!=null)
					stuTO.setClassName(ex.getStudent().getClassSchemewise().getClasses()!=null?ex.getStudent().getClassSchemewise().getClasses().getName():"");*/
				
				if(previousYears!=null && !previousYears.equalsIgnoreCase("0")){
					Set<StudentPreviousClassHistory> stuClassHistory =ex.getStudent().getStudentPreviousClassesHistory();
					if(stuClassHistory!=null && !stuClassHistory.isEmpty()){
						List<StudentPreviousClassHistory> studentPreviousList =new ArrayList<StudentPreviousClassHistory>();
						studentPreviousList.addAll(stuClassHistory);
						Collections.sort(studentPreviousList);
						Iterator<StudentPreviousClassHistory> itrClass = studentPreviousList.iterator();
						while (itrClass.hasNext()) {
							StudentPreviousClassHistory studentPreviousClassHistory = (StudentPreviousClassHistory) itrClass.next();
							if(Integer.parseInt(previousYears) == studentPreviousClassHistory.getAcademicYear()){
								if(ex.getStudent().getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() == studentPreviousClassHistory.getAcademicYear()){
									stuTO.setClassName(ex.getStudent().getClassSchemewise().getClasses()!=null?ex.getStudent().getClassSchemewise().getClasses().getName():"");
									break;
								}
								else{
									stuTO.setClassName(studentPreviousClassHistory.getClasses()!=null?studentPreviousClassHistory.getClasses().getName():"");
									//break;
								}
							}
						}
					}
				}else if(ex.getStudent().getClassSchemewise()!=null)
					stuTO.setClassName(ex.getStudent().getClassSchemewise().getClasses()!=null?ex.getStudent().getClassSchemewise().getClasses().getName():"");
				
				studentTOs.add(stuTO);
			}
		
		}
		return studentTOs;
	}
	
	/**
	 * @param studentList
	 * @param examStudentDetentionRejoinDetails
	 * @return
	 */
	public static List<StudentTO> convertStudentBoTOToFull(List<Student> studentList, List<ExamStudentDetentionRejoinDetails> examStudentDetentionRejoinDetails, String previousYears) {
		
		List<StudentTO> studentTOs = new ArrayList<StudentTO>();
		
		if(studentList!=null && !studentList.isEmpty()){
			Iterator<Student> iterator=studentList.iterator();
			
			while (iterator.hasNext()) {
				Student stu = (Student) iterator.next();
				StudentTO stuTO = new StudentTO();
				
				//stuTO.setStudentId(stu.getId());
				stuTO.setRegisterNo(stu.getRegisterNo()!=null?stu.getRegisterNo():"");
				stuTO.setStudentName(stu.getAdmAppln().getPersonalData().getFirstName()!=null?stu.getAdmAppln().getPersonalData().getFirstName():"");
				stuTO.setApplicationNo(stu.getAdmAppln().getApplnNo()>0?stu.getAdmAppln().getApplnNo():0);
				stuTO.setDob(stu.getAdmAppln().getPersonalData().getDateOfBirth()!=null?
						CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(stu.getAdmAppln().getPersonalData().getDateOfBirth()), StudentDetailsReportHelper.SQL_DATEFORMAT,StudentDetailsReportHelper.FROM_DATEFORMAT):"");
				/*if(stu.getClassSchemewise()!=null)
					stuTO.setClassName(stu.getClassSchemewise().getClasses()!=null?stu.getClassSchemewise().getClasses().getName():"");*/
				if(previousYears!=null && !previousYears.equalsIgnoreCase("0")){
					Set<StudentPreviousClassHistory> stuClassHistory = stu.getStudentPreviousClassesHistory();
					if(stuClassHistory!=null && !stuClassHistory.isEmpty()){
						List<StudentPreviousClassHistory> studentPreviousList =new ArrayList<StudentPreviousClassHistory>();
						studentPreviousList.addAll(stuClassHistory);
						Collections.sort(studentPreviousList);
						Iterator<StudentPreviousClassHistory> itrClass = studentPreviousList.iterator();
						while (itrClass.hasNext()) {
							StudentPreviousClassHistory studentPreviousClassHistory = (StudentPreviousClassHistory) itrClass.next();
							if(Integer.parseInt(previousYears) == studentPreviousClassHistory.getAcademicYear()){
								if(stu.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() == studentPreviousClassHistory.getAcademicYear()){
									stuTO.setClassName(stu.getClassSchemewise().getClasses()!=null?stu.getClassSchemewise().getClasses().getName():"");
									break;
								}
								else{
									stuTO.setClassName(studentPreviousClassHistory.getClasses()!=null?studentPreviousClassHistory.getClasses().getName():"");
									//break;
								}
							}
						}
					}
				}else if(stu.getClassSchemewise()!=null)
					stuTO.setClassName(stu.getClassSchemewise().getClasses()!=null?stu.getClassSchemewise().getClasses().getName():"");
				
				stuTO.setBankAccNo(stu.getBankAccNo()!=null?stu.getBankAccNo():"");
				stuTO.setSmartCardNo(stu.getSmartCardNo()!=null?stu.getSmartCardNo():"");
				stuTO.setGender(stu.getAdmAppln().getPersonalData().getGender()!=null?stu.getAdmAppln().getPersonalData().getGender():"");
				stuTO.setReligion(stu.getAdmAppln().getPersonalData().getReligion()!=null?
						stu.getAdmAppln().getPersonalData().getReligion().getName():
							(stu.getAdmAppln().getPersonalData().getReligionOthers()!=null?stu.getAdmAppln().getPersonalData().getReligionOthers():""));				
				stuTO.setMotherTongue(stu.getAdmAppln().getPersonalData().getMotherTongue()!=null?stu.getAdmAppln().getPersonalData().getMotherTongue().getName():"");
				stuTO.setBirthPlace(stu.getAdmAppln().getPersonalData().getBirthPlace()!=null?stu.getAdmAppln().getPersonalData().getBirthPlace():"");
				stuTO.setBloodGrp(stu.getAdmAppln().getPersonalData().getBloodGroup()!=null?stu.getAdmAppln().getPersonalData().getBloodGroup():"");
				if(stu.getAdmAppln().getPersonalData().getMobileNo2()!=null && !stu.getAdmAppln().getPersonalData().getMobileNo2().isEmpty()){
					stuTO.setMobileNo1(stu.getAdmAppln().getPersonalData().getMobileNo2());
				}else{
					stuTO.setMobileNo1(stu.getAdmAppln().getPersonalData().getPhNo1()!=null?stu.getAdmAppln().getPersonalData().getPhNo1():""
							+stu.getAdmAppln().getPersonalData().getPhNo2()!=null?stu.getAdmAppln().getPersonalData().getPhNo2():""
								+stu.getAdmAppln().getPersonalData().getPhNo3()!=null?stu.getAdmAppln().getPersonalData().getPhNo3():"");
				}
				stuTO.setPassportNo(stu.getAdmAppln().getPersonalData().getPassportNo());
				stuTO.setPassportValidity(stu.getAdmAppln().getPersonalData().getPassportValidity()!=null?
						CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(stu.getAdmAppln().getPersonalData().getPassportValidity()), StudentDetailsReportHelper.SQL_DATEFORMAT,StudentDetailsReportHelper.FROM_DATEFORMAT):"");
				stuTO.setEmail(stu.getAdmAppln().getPersonalData().getEmail());
				stuTO.setUniversityEmail(stu.getAdmAppln().getPersonalData().getUniversityEmail());
				stuTO.setNationality(stu.getAdmAppln().getPersonalData().getNationality()!=null?
						stu.getAdmAppln().getPersonalData().getNationality().getName():
							(stu.getAdmAppln().getPersonalData().getNationalityOthers()!=null?stu.getAdmAppln().getPersonalData().getNationalityOthers():""));
				stuTO.setCaste(stu.getAdmAppln().getPersonalData().getCaste()!=null?
						stu.getAdmAppln().getPersonalData().getCaste().getName():
							(stu.getAdmAppln().getPersonalData().getCasteOthers()!=null?stu.getAdmAppln().getPersonalData().getCasteOthers():""));
				stuTO.setCurrentAddress1(stu.getAdmAppln().getPersonalData().getCurrentAddressLine1()!=null?stu.getAdmAppln().getPersonalData().getCurrentAddressLine1():"");
				stuTO.setCurrentAddress2(stu.getAdmAppln().getPersonalData().getCurrentAddressLine2()!=null?stu.getAdmAppln().getPersonalData().getCurrentAddressLine2():"");
				stuTO.setCurrentAddressCity(stu.getAdmAppln().getPersonalData().getCityByCurrentAddressCityId()!=null?stu.getAdmAppln().getPersonalData().getCityByCurrentAddressCityId():"");
				stuTO.setCurrentAddressState(stu.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId()!=null?
						stu.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName().toUpperCase():
							(stu.getAdmAppln().getPersonalData().getCurrentAddressStateOthers()!=null?stu.getAdmAppln().getPersonalData().getCurrentAddressStateOthers().toUpperCase():""));
				stuTO.setCurrentAddressCountry(stu.getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId()!=null?
						stu.getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId().getName():stu.getAdmAppln().getPersonalData().getCurrentAddressCountryOthers());
				stuTO.setCurrentAddressZip(stu.getAdmAppln().getPersonalData().getCurrentAddressZipCode()!=null?stu.getAdmAppln().getPersonalData().getCurrentAddressZipCode():"");
				stuTO.setPermAddress1(stu.getAdmAppln().getPersonalData().getPermanentAddressLine1()!=null?stu.getAdmAppln().getPersonalData().getPermanentAddressLine1():"");
				stuTO.setPermAddress2(stu.getAdmAppln().getPersonalData().getPermanentAddressLine2()!=null?stu.getAdmAppln().getPersonalData().getPermanentAddressLine2():"");
				stuTO.setPermAddressCity(stu.getAdmAppln().getPersonalData().getCityByPermanentAddressCityId());
				stuTO.setPermAddressState(stu.getAdmAppln().getPersonalData().getStateByPermanentAddressStateId()!=null?
						stu.getAdmAppln().getPersonalData().getStateByPermanentAddressStateId().getName().toUpperCase():
							(stu.getAdmAppln().getPersonalData().getPermanentAddressStateOthers()!=null?stu.getAdmAppln().getPersonalData().getPermanentAddressStateOthers().toUpperCase():""));
				stuTO.setPermAddressCountry(stu.getAdmAppln().getPersonalData().getCountryByPermanentAddressCountryId()!=null?
						stu.getAdmAppln().getPersonalData().getCountryByPermanentAddressCountryId().getName().toUpperCase():
							(stu.getAdmAppln().getPersonalData().getPermanentAddressCountryOthers()!=null?stu.getAdmAppln().getPersonalData().getPermanentAddressCountryOthers().toUpperCase():""));
				stuTO.setPermAddressZip(stu.getAdmAppln().getPersonalData().getPermanentAddressZipCode()!=null?stu.getAdmAppln().getPersonalData().getPermanentAddressZipCode():"");
				if(stu.getAdmAppln().getPersonalData().getFatherName()!=null && !stu.getAdmAppln().getPersonalData().getFatherName().contains("Mr. ")){
					stuTO.setFatherName("Mr. "+stu.getAdmAppln().getPersonalData().getFatherName());
				}else{
					stuTO.setFatherName(stu.getAdmAppln().getPersonalData().getFatherName()!=null?stu.getAdmAppln().getPersonalData().getFatherName():"");
				}
				if(stu.getAdmAppln().getPersonalData().getMotherName()!=null && !stu.getAdmAppln().getPersonalData().getMotherName().contains("Mrs. ")){
					stuTO.setMotherName("Mrs. "+stu.getAdmAppln().getPersonalData().getMotherName());
				}else{
					stuTO.setMotherName(stu.getAdmAppln().getPersonalData().getMotherName()!=null?stu.getAdmAppln().getPersonalData().getMotherName():"");
				}
				stuTO.setGuardianAddress1(stu.getAdmAppln().getPersonalData().getGuardianAddressLine1()!=null?stu.getAdmAppln().getPersonalData().getGuardianAddressLine1():"");
				stuTO.setGuardianAddress2(stu.getAdmAppln().getPersonalData().getGuardianAddressLine2()!=null?stu.getAdmAppln().getPersonalData().getGuardianAddressLine2():"");
				stuTO.setGuardianAddressCity(stu.getAdmAppln().getPersonalData().getCityByGuardianAddressCityId());
				stuTO.setGuardianAddressCountry(stu.getAdmAppln().getPersonalData().getCountryByGuardianAddressCountryId()!=null?
						stu.getAdmAppln().getPersonalData().getCountryByGuardianAddressCountryId().getName().toUpperCase():
							(stu.getAdmAppln().getPersonalData().getGuardianAddressCountryOthers()!=null?stu.getAdmAppln().getPersonalData().getGuardianAddressCountryOthers().toUpperCase():""));
				stuTO.setGuardianAddressState(stu.getAdmAppln().getPersonalData().getStateByGuardianAddressStateId()!=null?
						stu.getAdmAppln().getPersonalData().getStateByGuardianAddressStateId().getName().toUpperCase():
							(stu.getAdmAppln().getPersonalData().getGuardianAddressStateOthers()!=null?stu.getAdmAppln().getPersonalData().getGuardianAddressStateOthers().toUpperCase():""));
				stuTO.setGuardianAddressZip(stu.getAdmAppln().getPersonalData().getGuardianAddressZipCode()!=null?stu.getAdmAppln().getPersonalData().getGuardianAddressZipCode():"");
				if(stu.getAdmAppln().getPersonalData().getParentMob2()!=null && !stu.getAdmAppln().getPersonalData().getParentMob2().isEmpty()){
					stuTO.setParentNo(stu.getAdmAppln().getPersonalData().getParentMob2());
				}
				else{
					stuTO.setParentNo(stu.getAdmAppln().getPersonalData().getParentPh1()!=null?stu.getAdmAppln().getPersonalData().getParentPh1():""
							+stu.getAdmAppln().getPersonalData().getParentPh2()!=null?stu.getAdmAppln().getPersonalData().getParentPh2():""
								+stu.getAdmAppln().getPersonalData().getParentPh3()!=null?stu.getAdmAppln().getPersonalData().getParentPh3():"");
				}
				stuTO.setSecondLanguage(stu.getAdmAppln().getPersonalData().getSecondLanguage()!=null?stu.getAdmAppln().getPersonalData().getSecondLanguage():"");
				if(stu.getAdmAppln().getCourseBySelectedCourseId()!=null){
					stuTO.setCourseName(stu.getAdmAppln().getCourseBySelectedCourseId().getName());
				}
				stuTO.setIsHandicapped(stu.getAdmAppln().getPersonalData().getIsHandicapped()?"Yes":"No");
				stuTO.setHandicappedDescription(stu.getAdmAppln().getPersonalData().getHandicappedDescription()!=null?stu.getAdmAppln().getPersonalData().getHandicappedDescription():"");
				
				studentTOs.add(stuTO);
			}
		}
		else if(examStudentDetentionRejoinDetails!=null && !examStudentDetentionRejoinDetails.isEmpty()){

			Iterator<ExamStudentDetentionRejoinDetails> iterator=examStudentDetentionRejoinDetails.iterator();
			
			while (iterator.hasNext()) {
				ExamStudentDetentionRejoinDetails ex = (ExamStudentDetentionRejoinDetails) iterator.next();
				StudentTO stuTO = new StudentTO();
				
				//stuTO.setStudentId(ex.getStudent().getId());
				stuTO.setRegisterNo(ex.getStudent().getRegisterNo()!=null?ex.getStudent().getRegisterNo():"");
				stuTO.setStudentName(ex.getStudent().getAdmAppln().getPersonalData().getFirstName()!=null?ex.getStudent().getAdmAppln().getPersonalData().getFirstName():"");
				stuTO.setApplicationNo(ex.getStudent().getAdmAppln().getApplnNo()>0?ex.getStudent().getAdmAppln().getApplnNo():0);
				stuTO.setDob(ex.getStudent().getAdmAppln().getPersonalData().getDateOfBirth()!=null?
						CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(ex.getStudent().getAdmAppln().getPersonalData().getDateOfBirth()), StudentDetailsReportHelper.SQL_DATEFORMAT,StudentDetailsReportHelper.FROM_DATEFORMAT):"");
				/*if(ex.getStudent().getClassSchemewise()!=null)
					stuTO.setClassName(ex.getStudent().getClassSchemewise().getClasses()!=null?ex.getStudent().getClassSchemewise().getClasses().getName():"");*/
				if(previousYears!=null && !previousYears.equalsIgnoreCase("0")){
					Set<StudentPreviousClassHistory> stuClassHistory =ex.getStudent().getStudentPreviousClassesHistory();
					if(stuClassHistory!=null && !stuClassHistory.isEmpty()){
						List<StudentPreviousClassHistory> studentPreviousList =new ArrayList<StudentPreviousClassHistory>();
						studentPreviousList.addAll(stuClassHistory);
						Collections.sort(studentPreviousList);
						Iterator<StudentPreviousClassHistory> itrClass = studentPreviousList.iterator();
						while (itrClass.hasNext()) {
							StudentPreviousClassHistory studentPreviousClassHistory = (StudentPreviousClassHistory) itrClass.next();
							if(Integer.parseInt(previousYears) == studentPreviousClassHistory.getAcademicYear()){
								if(ex.getStudent().getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() == studentPreviousClassHistory.getAcademicYear()){
									stuTO.setClassName(ex.getStudent().getClassSchemewise().getClasses()!=null?ex.getStudent().getClassSchemewise().getClasses().getName():"");
									break;
								}
								else{
									stuTO.setClassName(studentPreviousClassHistory.getClasses()!=null?studentPreviousClassHistory.getClasses().getName():"");
									//break;
								}
							}
						}
					}
				}else if(ex.getStudent().getClassSchemewise()!=null)
					stuTO.setClassName(ex.getStudent().getClassSchemewise().getClasses()!=null?ex.getStudent().getClassSchemewise().getClasses().getName():"");
				
				stuTO.setBankAccNo(ex.getStudent().getBankAccNo()!=null?ex.getStudent().getBankAccNo():"");
				stuTO.setSmartCardNo(ex.getStudent().getSmartCardNo()!=null?ex.getStudent().getSmartCardNo():"");
				stuTO.setGender(ex.getStudent().getAdmAppln().getPersonalData().getGender()!=null?ex.getStudent().getAdmAppln().getPersonalData().getGender():"");
				stuTO.setReligion(ex.getStudent().getAdmAppln().getPersonalData().getReligion()!=null?
						ex.getStudent().getAdmAppln().getPersonalData().getReligion().getName():
							(ex.getStudent().getAdmAppln().getPersonalData().getReligionOthers()!=null?ex.getStudent().getAdmAppln().getPersonalData().getReligionOthers():""));				
				stuTO.setMotherTongue(ex.getStudent().getAdmAppln().getPersonalData().getMotherTongue()!=null?ex.getStudent().getAdmAppln().getPersonalData().getMotherTongue().getName():"");
				stuTO.setBirthPlace(ex.getStudent().getAdmAppln().getPersonalData().getBirthPlace()!=null?ex.getStudent().getAdmAppln().getPersonalData().getBirthPlace():"");
				stuTO.setBloodGrp(ex.getStudent().getAdmAppln().getPersonalData().getBloodGroup()!=null?ex.getStudent().getAdmAppln().getPersonalData().getBloodGroup():"");
				if(ex.getStudent().getAdmAppln().getPersonalData().getMobileNo2()!=null && !ex.getStudent().getAdmAppln().getPersonalData().getMobileNo2().isEmpty()){
					stuTO.setMobileNo1(ex.getStudent().getAdmAppln().getPersonalData().getMobileNo2());
				}else{
					stuTO.setMobileNo1(ex.getStudent().getAdmAppln().getPersonalData().getPhNo1()!=null?ex.getStudent().getAdmAppln().getPersonalData().getPhNo1():""
							+ex.getStudent().getAdmAppln().getPersonalData().getPhNo2()!=null?ex.getStudent().getAdmAppln().getPersonalData().getPhNo2():""
								+ex.getStudent().getAdmAppln().getPersonalData().getPhNo3()!=null?ex.getStudent().getAdmAppln().getPersonalData().getPhNo3():"");
				}
				stuTO.setPassportNo(ex.getStudent().getAdmAppln().getPersonalData().getPassportNo()!=null?ex.getStudent().getAdmAppln().getPersonalData().getPassportNo():"");
				stuTO.setPassportValidity(ex.getStudent().getAdmAppln().getPersonalData().getPassportValidity()!=null?
						CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(ex.getStudent().getAdmAppln().getPersonalData().getPassportValidity()), StudentDetailsReportHelper.SQL_DATEFORMAT,StudentDetailsReportHelper.FROM_DATEFORMAT):"");
				stuTO.setEmail(ex.getStudent().getAdmAppln().getPersonalData().getEmail());
				stuTO.setUniversityEmail(ex.getStudent().getAdmAppln().getPersonalData().getUniversityEmail());
				stuTO.setNationality(ex.getStudent().getAdmAppln().getPersonalData().getNationality()!=null?
						ex.getStudent().getAdmAppln().getPersonalData().getNationality().getName():
							(ex.getStudent().getAdmAppln().getPersonalData().getNationalityOthers()!=null?ex.getStudent().getAdmAppln().getPersonalData().getNationalityOthers():""));
				stuTO.setCaste(ex.getStudent().getAdmAppln().getPersonalData().getCaste()!=null?
						ex.getStudent().getAdmAppln().getPersonalData().getCaste().getName():
							(ex.getStudent().getAdmAppln().getPersonalData().getCasteOthers()!=null?ex.getStudent().getAdmAppln().getPersonalData().getCasteOthers():""));
				stuTO.setCurrentAddress1(ex.getStudent().getAdmAppln().getPersonalData().getCurrentAddressLine1()!=null?ex.getStudent().getAdmAppln().getPersonalData().getCurrentAddressLine1():"");
				stuTO.setCurrentAddress2(ex.getStudent().getAdmAppln().getPersonalData().getCurrentAddressLine2()!=null?ex.getStudent().getAdmAppln().getPersonalData().getCurrentAddressLine2():"");
				stuTO.setCurrentAddressCity(ex.getStudent().getAdmAppln().getPersonalData().getCityByCurrentAddressCityId()!=null?ex.getStudent().getAdmAppln().getPersonalData().getCityByCurrentAddressCityId():"");
				stuTO.setCurrentAddressState(ex.getStudent().getAdmAppln().getPersonalData().getStateByCurrentAddressStateId()!=null?
						ex.getStudent().getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName().toUpperCase():
							(ex.getStudent().getAdmAppln().getPersonalData().getCurrentAddressStateOthers()!=null?ex.getStudent().getAdmAppln().getPersonalData().getCurrentAddressStateOthers().toUpperCase():""));
				stuTO.setCurrentAddressCountry(ex.getStudent().getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId()!=null?
						ex.getStudent().getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId().getName():ex.getStudent().getAdmAppln().getPersonalData().getCurrentAddressCountryOthers());
				stuTO.setCurrentAddressZip(ex.getStudent().getAdmAppln().getPersonalData().getCurrentAddressZipCode()!=null?ex.getStudent().getAdmAppln().getPersonalData().getCurrentAddressZipCode():"");
				stuTO.setPermAddress1(ex.getStudent().getAdmAppln().getPersonalData().getPermanentAddressLine1()!=null?ex.getStudent().getAdmAppln().getPersonalData().getPermanentAddressLine1():"");
				stuTO.setPermAddress2(ex.getStudent().getAdmAppln().getPersonalData().getPermanentAddressLine2()!=null?ex.getStudent().getAdmAppln().getPersonalData().getPermanentAddressLine2():"");
				stuTO.setPermAddressCity(ex.getStudent().getAdmAppln().getPersonalData().getCityByPermanentAddressCityId());
				stuTO.setPermAddressState(ex.getStudent().getAdmAppln().getPersonalData().getStateByPermanentAddressStateId()!=null?
						ex.getStudent().getAdmAppln().getPersonalData().getStateByPermanentAddressStateId().getName().toUpperCase():
							(ex.getStudent().getAdmAppln().getPersonalData().getPermanentAddressStateOthers()!=null?ex.getStudent().getAdmAppln().getPersonalData().getPermanentAddressStateOthers().toUpperCase():""));
				stuTO.setPermAddressCountry(ex.getStudent().getAdmAppln().getPersonalData().getCountryByPermanentAddressCountryId()!=null?
						ex.getStudent().getAdmAppln().getPersonalData().getCountryByPermanentAddressCountryId().getName().toUpperCase():
							(ex.getStudent().getAdmAppln().getPersonalData().getPermanentAddressCountryOthers()!=null?ex.getStudent().getAdmAppln().getPersonalData().getPermanentAddressCountryOthers().toUpperCase():""));
				stuTO.setPermAddressZip(ex.getStudent().getAdmAppln().getPersonalData().getPermanentAddressZipCode()!=null?ex.getStudent().getAdmAppln().getPersonalData().getPermanentAddressZipCode():"");
				if(ex.getStudent().getAdmAppln().getPersonalData().getFatherName()!=null && !ex.getStudent().getAdmAppln().getPersonalData().getFatherName().contains("Mr. ")){
					stuTO.setFatherName("Mr. "+ex.getStudent().getAdmAppln().getPersonalData().getFatherName());
				}else{
					stuTO.setFatherName(ex.getStudent().getAdmAppln().getPersonalData().getFatherName()!=null?ex.getStudent().getAdmAppln().getPersonalData().getFatherName():"");
				}
				if(ex.getStudent().getAdmAppln().getPersonalData().getMotherName()!=null && !ex.getStudent().getAdmAppln().getPersonalData().getMotherName().contains("Mrs. ")){
					stuTO.setMotherName("Mrs. "+ex.getStudent().getAdmAppln().getPersonalData().getMotherName());
				}else{
					stuTO.setMotherName(ex.getStudent().getAdmAppln().getPersonalData().getMotherName()!=null?ex.getStudent().getAdmAppln().getPersonalData().getMotherName():"");
				}
				stuTO.setGuardianAddress1(ex.getStudent().getAdmAppln().getPersonalData().getGuardianAddressLine1()!=null?ex.getStudent().getAdmAppln().getPersonalData().getGuardianAddressLine1():"");
				stuTO.setGuardianAddress2(ex.getStudent().getAdmAppln().getPersonalData().getGuardianAddressLine2()!=null?ex.getStudent().getAdmAppln().getPersonalData().getGuardianAddressLine2():"");
				stuTO.setGuardianAddressCity(ex.getStudent().getAdmAppln().getPersonalData().getCityByGuardianAddressCityId());
				stuTO.setGuardianAddressCountry(ex.getStudent().getAdmAppln().getPersonalData().getCountryByGuardianAddressCountryId()!=null?
						ex.getStudent().getAdmAppln().getPersonalData().getCountryByGuardianAddressCountryId().getName().toUpperCase():
							(ex.getStudent().getAdmAppln().getPersonalData().getGuardianAddressCountryOthers()!=null?ex.getStudent().getAdmAppln().getPersonalData().getGuardianAddressCountryOthers().toUpperCase():""));
				stuTO.setGuardianAddressState(ex.getStudent().getAdmAppln().getPersonalData().getStateByGuardianAddressStateId()!=null?
						ex.getStudent().getAdmAppln().getPersonalData().getStateByGuardianAddressStateId().getName().toUpperCase():
							(ex.getStudent().getAdmAppln().getPersonalData().getGuardianAddressStateOthers()!=null?ex.getStudent().getAdmAppln().getPersonalData().getGuardianAddressStateOthers().toUpperCase():""));
				stuTO.setGuardianAddressZip(ex.getStudent().getAdmAppln().getPersonalData().getGuardianAddressZipCode()!=null?ex.getStudent().getAdmAppln().getPersonalData().getGuardianAddressZipCode():"");
				if(ex.getStudent().getAdmAppln().getPersonalData().getParentMob2()!=null && !ex.getStudent().getAdmAppln().getPersonalData().getParentMob2().isEmpty()){
					stuTO.setParentNo(ex.getStudent().getAdmAppln().getPersonalData().getParentMob2());
				}
				else{
					stuTO.setParentNo(ex.getStudent().getAdmAppln().getPersonalData().getParentPh1()!=null?ex.getStudent().getAdmAppln().getPersonalData().getParentPh1():""
							+ex.getStudent().getAdmAppln().getPersonalData().getParentPh2()!=null?ex.getStudent().getAdmAppln().getPersonalData().getParentPh2():""
								+ex.getStudent().getAdmAppln().getPersonalData().getParentPh3()!=null?ex.getStudent().getAdmAppln().getPersonalData().getParentPh3():"");
				}
				stuTO.setSecondLanguage(ex.getStudent().getAdmAppln().getPersonalData().getSecondLanguage()!=null?ex.getStudent().getAdmAppln().getPersonalData().getSecondLanguage():"");
				if(ex.getStudent().getAdmAppln().getCourseBySelectedCourseId()!=null){
					stuTO.setCourseName(ex.getStudent().getAdmAppln().getCourseBySelectedCourseId().getName());
				}
				stuTO.setIsHandicapped(ex.getStudent().getAdmAppln().getPersonalData().getIsHandicapped()?"Yes":"No");
				stuTO.setHandicappedDescription(ex.getStudent().getAdmAppln().getPersonalData().getHandicappedDescription()!=null?ex.getStudent().getAdmAppln().getPersonalData().getHandicappedDescription():"");
				
				studentTOs.add(stuTO);
			}
		
		}
		return studentTOs;
	}
	
		
	/**
	 * @param stForm
	 * @return
	 */
	public StudentDetailsReportTO getSelectedColumns(StudentDetailsReportForm stForm) {
		
		StudentDetailsReportTO reportTO = new StudentDetailsReportTO();

		List<String> selectedColumnsList = new ArrayList<String>();
		String[] selected = stForm.getSelectedColumnsArray();
		for (int i = 0; i < selected.length; i++) {
			selectedColumnsList.add(selected[i]);
		}
		
		if (selectedColumnsList != null) {
			Iterator<String> iterator = selectedColumnsList.iterator();
			int count = 0;
			while (iterator.hasNext()) {

				String columnName = iterator.next();
				
				if (columnName.equalsIgnoreCase("Register No")) {
					reportTO.setRegisterNoDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setRegisterNoPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Student Name")) {
					reportTO.setStudentNameDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setStudentNamePosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Application No")) {
					reportTO .setApplicationNoDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setApplicationNoPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Class Name")) {
					reportTO.setClassNameDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setClassNamePosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Date Of Birth")) {
					reportTO.setDobDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setDobPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Gender")) {
					reportTO.setGenderDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setGenderPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Bank Account No")) {
					reportTO.setBankAccNoDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setBankAccNoPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Smart Card No")) {
					reportTO.setSmartCardNoDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setSmartCardNoPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Mobile No")) {
					reportTO.setMobileNo1Display(StudentDetailsReportHelper.DISPLAY);
					reportTO.setMobileNo1Position((short) count++);
				}
				if (columnName.equalsIgnoreCase("E-Mail")) {
					reportTO.setEmailDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setEmailPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("University E-Mail")) {
					reportTO.setUniversityEmailDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setUniversityEmailPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Religion")) {
					reportTO.setReligionDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setReligionPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Mother Tongue")) {
					reportTO.setMotherTongueDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setMotherTonguePosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Birth Place")) {
					reportTO .setBirthPlaceDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setBirthPlacePosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Blood Group")) {
					reportTO.setBloodGrpDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setBloodGrpPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Passport No")) {
					reportTO.setPassportNoDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setPassportNoPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Passport Validity")) {
					reportTO.setPassportValidityDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setPassportValidityPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Nationality")) {
					reportTO.setNationalityDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setNationalityPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Caste")) {
					reportTO.setCasteDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setCastePosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Current Address Line1")) {
					reportTO.setCurrentAddress1Display(StudentDetailsReportHelper.DISPLAY);
					reportTO.setCurrentAddress1Position((short) count++);
				}
				if (columnName.equalsIgnoreCase("Current Address Line2")) {
					reportTO.setCurrentAddress2Display(StudentDetailsReportHelper.DISPLAY);
					reportTO.setCurrentAddress2Position((short) count++);
				}
				if (columnName.equalsIgnoreCase("Current City")) {
					reportTO.setCurrentAddressCityDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setCurrentAddressCityPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Current State")) {
					reportTO.setCurrentAddressStateDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setCurrentAddressStatePosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Current Country")) {
					reportTO.setCurrentAddressCountryDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setCurrentAddressCountryPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Current PinCode")) {
					reportTO.setCurrentAddressZipDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setCurrentAddressZipPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Permanent Address Line1")) {
					reportTO.setPermAddress1Display(StudentDetailsReportHelper.DISPLAY);
					reportTO.setPermAddress1Position((short) count++);
				}
				if (columnName.equalsIgnoreCase("Permanent Address Line2")) {
					reportTO.setPermAddress2Display(StudentDetailsReportHelper.DISPLAY);
					reportTO.setPermAddress2Position((short) count++);
				}
				if (columnName.equalsIgnoreCase("Permanent City")) {
					reportTO.setPermanantCityCodeDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setPermanantCityCodePosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Permanent State")) {
					reportTO.setPermanantStateIdDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setPermanantStateIdPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Permanent Country")) {
					reportTO.setPermAddressCountryDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setPermAddressCountryPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Permanent PinCode")) {
					reportTO.setPermAddressZipDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setPermAddressZipPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Father Name")) {
					reportTO.setFatherNameDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setFatherNamePosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Mother Name")) {
					reportTO.setMotherNameDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setMotherNamePosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Guardian Address Line1")) {
					reportTO.setGuardianAddress1Display(StudentDetailsReportHelper.DISPLAY);
					reportTO.setGuardianAddress1Position((short) count++);
				}
				if (columnName.equalsIgnoreCase("Guardian Address Line2")) {
					reportTO.setGuardianAddress2Display(StudentDetailsReportHelper.DISPLAY);
					reportTO.setGuardianAddress2Position((short) count++);
				}
				if (columnName.equalsIgnoreCase("Guardian City")) {
					reportTO.setGuardianAddressCityDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setGuardianAddressCityPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Guardian State")) {
					reportTO.setGuardianAddressStateDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setGuardianAddressStatePosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Guardian Country")) {
					reportTO.setGuardianAddressCountryDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setGuardianAddressCountryPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Guardian PinCode")) {
					reportTO.setGuardianAddressZipDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setGuardianAddressZipPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Parent Mobile No")) {
					reportTO.setParentNoDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setParentNoPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Second Language")) {
					reportTO.setSecondLanguageDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setSecondLanguagePosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Course")) {
					reportTO.setCourseDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setCoursePosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Handicapped")) {
					reportTO.setIsHandicappedDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setIsHandicappedPosition((short) count++);
				}
				if (columnName.equalsIgnoreCase("Handicapped Description")) {
					reportTO.setHandicappedDescriptionDisplay(StudentDetailsReportHelper.DISPLAY);
					reportTO.setHandicappedDescriptionPosition((short) count++);
				}
				
			}
		}
		return reportTO;
	
		
	}

	
	/**
	 * @param studentTOs
	 * @param stuReportTo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@SuppressWarnings({ "deprecation", "null" })
	public boolean convertToExcel(List<StudentTO> studentTOs, StudentDetailsReportTO stuReportTo, HttpServletRequest request) throws Exception {
		
		boolean isUpdated=false;
		Properties prop = new Properties();
		try {
			InputStream inputStream = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inputStream);
		} 
		catch (IOException e) {
			log.error("Error occured at convertToExcel of studentDetailsReportHelper ",e);
			throw new IOException(e);
		}
		String fileName=prop.getProperty(CMSConstants.STUDENT_DETAILS_REPORT_EXCEL);
		File excelFile = new File(request.getRealPath("")+ "//TempFiles//"+fileName);
		if(excelFile.exists()){
			excelFile.delete();
		}
		FileOutputStream fos = null;
		ByteArrayOutputStream bos=null;
		XSSFWorkbook wb=null;
		XSSFSheet sheet=null;
		XSSFRow row=null;
		//XSSFCell cell=null;
		
		if(studentTOs!=null && !studentTOs.isEmpty()){
			
			int count = 0;
			Iterator<StudentTO> iterator = studentTOs.iterator();
			try{
				wb=new XSSFWorkbook();
				XSSFCellStyle cellStyle=wb.createCellStyle();
				CreationHelper createHelper = wb.getCreationHelper();
				cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
				sheet = wb.createSheet("Student Report");
				row = sheet.createRow(count);
				count = sheet.getFirstRowNum();
				// Create cells in the row and put some data in it.
				if(stuReportTo.getRegisterNoDisplay()!=null && stuReportTo.getRegisterNoDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getRegisterNoPosition()).setCellValue("Register No");
				}
				if(stuReportTo.getStudentNameDisplay()!=null && stuReportTo.getStudentNameDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getStudentNamePosition()).setCellValue("Student Name");
				}
				if(stuReportTo.getApplicationNoDisplay()!=null && stuReportTo.getApplicationNoDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getApplicationNoPosition()).setCellValue("Application No");
				}
				if(stuReportTo.getClassNameDisplay()!=null && stuReportTo.getClassNameDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getClassNamePosition()).setCellValue("Class Name");
				}
				if(stuReportTo.getDobDisplay()!=null && stuReportTo.getDobDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getDobPosition()).setCellValue("Date Of Birth");
				}
				if(stuReportTo.getGenderDisplay()!=null && stuReportTo.getGenderDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getGenderPosition()).setCellValue("Gender");
				}
				if(stuReportTo.getBankAccNoDisplay()!=null && stuReportTo.getBankAccNoDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getBankAccNoPosition()).setCellValue("Bank Account No");
				}
				if(stuReportTo.getSmartCardNoDisplay()!=null && stuReportTo.getSmartCardNoDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getSmartCardNoPosition()).setCellValue("Smart Card No");
				}
				if(stuReportTo.getMobileNo1Display()!=null && stuReportTo.getMobileNo1Display().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getMobileNo1Position()).setCellValue("Mobile No");
				}
				if(stuReportTo.getEmailDisplay()!=null && stuReportTo.getEmailDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getEmailPosition()).setCellValue("E-Mail");
				}
				if(stuReportTo.getUniversityEmailDisplay()!=null && stuReportTo.getUniversityEmailDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getUniversityEmailPosition()).setCellValue("University E-Mail");
				}
				if(stuReportTo.getReligionDisplay()!=null && stuReportTo.getReligionDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getReligionPosition()).setCellValue("Religion");
				}
				if(stuReportTo.getMotherTongueDisplay()!=null && stuReportTo.getMotherTongueDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getMotherTonguePosition()).setCellValue("Mother Tongue");
				}
				if(stuReportTo.getBirthPlaceDisplay()!=null && stuReportTo.getBirthPlaceDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getBirthPlacePosition()).setCellValue("Birth Place");
				}
				if(stuReportTo.getBloodGrpDisplay()!=null && stuReportTo.getBloodGrpDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getBloodGrpPosition()).setCellValue("Blood Group");
				}
				if(stuReportTo.getPassportNoDisplay()!=null && stuReportTo.getPassportNoDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getPassportNoPosition()).setCellValue("Passport No");
				}
				if(stuReportTo.getPassportValidityDisplay()!=null && stuReportTo.getPassportValidityDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getPassportValidityPosition()).setCellValue("Passport Validity");
				}
				if(stuReportTo.getNationalityDisplay()!=null && stuReportTo.getNationalityDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getNationalityPosition()).setCellValue("Nationality");
				}
				if(stuReportTo.getCasteDisplay()!=null && stuReportTo.getCasteDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getCastePosition()).setCellValue("Caste");
				}
				if(stuReportTo.getCurrentAddress1Display()!=null && stuReportTo.getCurrentAddress1Display().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getCurrentAddress1Position()).setCellValue("Current Address Line1");
				}
				if(stuReportTo.getCurrentAddress2Display()!=null && stuReportTo.getCurrentAddress2Display().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getCurrentAddress2Position()).setCellValue("Current Address Line2");
				}
				if(stuReportTo.getCurrentAddressCityDisplay()!=null && stuReportTo.getCurrentAddressCityDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getCurrentAddressCityPosition()).setCellValue("Current City");
				}
				if(stuReportTo.getCurrentAddressStateDisplay()!=null && stuReportTo.getCurrentAddressStateDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getCurrentAddressStatePosition()).setCellValue("Current State");
				}
				if(stuReportTo.getCurrentAddressCountryDisplay()!=null && stuReportTo.getCurrentAddressCountryDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getCurrentAddressCountryPosition()).setCellValue("Current Country");
				}
				if(stuReportTo.getCurrentAddressZipDisplay()!=null && stuReportTo.getCurrentAddressZipDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getCurrentAddressZipPosition()).setCellValue("Current PinCode");
				}
				if(stuReportTo.getPermAddress1Display()!=null && stuReportTo.getPermAddress1Display().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getPermAddress1Position()).setCellValue("Permanent Address Line1");
				}
				if(stuReportTo.getPermAddress2Display()!=null && stuReportTo.getPermAddress2Display().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getPermAddress2Position()).setCellValue("Permanent Address Line2");
				}
				if(stuReportTo.getPermanantCityCodeDisplay()!=null && stuReportTo.getPermanantCityCodeDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getPermanantCityCodePosition()).setCellValue("Permanent City");
				}
				if(stuReportTo.getPermanantStateIdDisplay()!=null && stuReportTo.getPermanantStateIdDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getPermanantStateIdPosition()).setCellValue("Permanent State");
				}
				if(stuReportTo.getPermAddressCountryDisplay()!=null && stuReportTo.getPermAddressCountryDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getPermAddressCountryPosition()).setCellValue("Permanent Country");
				}
				if(stuReportTo.getPermAddressZipDisplay()!=null && stuReportTo.getPermAddressZipDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getPermAddressZipPosition()).setCellValue("Permanent PinCode");
				}
				if(stuReportTo.getFatherNameDisplay()!=null && stuReportTo.getFatherNameDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getFatherNamePosition()).setCellValue("Father Name");
				}
				if(stuReportTo.getMotherNameDisplay()!=null && stuReportTo.getMotherNameDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getMotherNamePosition()).setCellValue("Mother Name");
				}
				if(stuReportTo.getGuardianAddress1Display()!=null && stuReportTo.getGuardianAddress1Display().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getGuardianAddress1Position()).setCellValue("Guardian Address Line1");
				}
				if(stuReportTo.getGuardianAddress2Display()!=null && stuReportTo.getGuardianAddress2Display().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getGuardianAddress2Position()).setCellValue("Guardian Address Line2");
				}
				if(stuReportTo.getGuardianAddressCityDisplay()!=null && stuReportTo.getGuardianAddressCityDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getGuardianAddressCityPosition()).setCellValue("Guardian City");
				}
				if(stuReportTo.getGuardianAddressStateDisplay()!=null && stuReportTo.getGuardianAddressStateDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getGuardianAddressStatePosition()).setCellValue("Guardian State");
				}
				if(stuReportTo.getGuardianAddressCountryDisplay()!=null && stuReportTo.getGuardianAddressCountryDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getGuardianAddressCountryPosition()).setCellValue("Guardian Country");
				}
				if(stuReportTo.getGuardianAddressZipDisplay()!=null && stuReportTo.getGuardianAddressZipDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getGuardianAddressZipPosition()).setCellValue("Guardian PinCode");
				}
				if(stuReportTo.getParentNoDisplay()!=null && stuReportTo.getParentNoDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getParentNoPosition()).setCellValue("Parent Mobile No");
				}
				if(stuReportTo.getSecondLanguageDisplay()!=null && stuReportTo.getSecondLanguageDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getSecondLanguagePosition()).setCellValue("Second Language");
				}
				if(stuReportTo.getCourseDisplay()!=null && stuReportTo.getCourseDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getCoursePosition()).setCellValue("Course");
				}
				if(stuReportTo.getIsHandicappedDisplay()!=null && stuReportTo.getIsHandicappedDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getIsHandicappedPosition()).setCellValue("Handicapped");
				}
				if(stuReportTo.getHandicappedDescriptionDisplay()!=null && stuReportTo.getHandicappedDescriptionDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
					row.createCell((short)stuReportTo.getHandicappedDescriptionPosition()).setCellValue("Handicapped Description");
				}
				
				while (iterator.hasNext()) {
					StudentTO stuTO = (StudentTO) iterator.next();
					count = count +1;
					row = sheet.createRow(count);
					if(stuReportTo.getRegisterNoDisplay()!=null && stuReportTo.getRegisterNoDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getRegisterNoPosition()).setCellValue(stuTO.getRegisterNo());
					}
					if(stuReportTo.getStudentNameDisplay()!=null && stuReportTo.getStudentNameDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getStudentNamePosition()).setCellValue(stuTO.getStudentName());
					}
					if(stuReportTo.getApplicationNoDisplay()!=null && stuReportTo.getApplicationNoDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getApplicationNoPosition()).setCellValue(stuTO.getApplicationNo());
					}
					if(stuReportTo.getClassNameDisplay()!=null && stuReportTo.getClassNameDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getClassNamePosition()).setCellValue(stuTO.getClassName());
					}
					if(stuReportTo.getDobDisplay()!=null && stuReportTo.getDobDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getDobPosition()).setCellValue(stuTO.getDob());
					}
					if(stuReportTo.getGenderDisplay()!=null && stuReportTo.getGenderDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getGenderPosition()).setCellValue(stuTO.getGender());
					}
					if(stuReportTo.getBankAccNoDisplay()!=null && stuReportTo.getBankAccNoDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getBankAccNoPosition()).setCellValue(stuTO.getBankAccNo());
					}
					if(stuReportTo.getSmartCardNoDisplay()!=null && stuReportTo.getSmartCardNoDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getSmartCardNoPosition()).setCellValue(stuTO.getSmartCardNo());
					}
					if(stuReportTo.getMobileNo1Display()!=null && stuReportTo.getMobileNo1Display().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getMobileNo1Position()).setCellValue(stuTO.getMobileNo1());
					}
					if(stuReportTo.getEmailDisplay()!=null && stuReportTo.getEmailDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getEmailPosition()).setCellValue(stuTO.getEmail());
					}
					if(stuReportTo.getUniversityEmailDisplay()!=null && stuReportTo.getUniversityEmailDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getUniversityEmailPosition()).setCellValue(stuTO.getUniversityEmail());
					}
					if(stuReportTo.getReligionDisplay()!=null && stuReportTo.getReligionDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getReligionPosition()).setCellValue(stuTO.getReligion());
					}
					if(stuReportTo.getMotherTongueDisplay()!=null && stuReportTo.getMotherTongueDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getMotherTonguePosition()).setCellValue(stuTO.getMotherTongue());
					}
					if(stuReportTo.getBirthPlaceDisplay()!=null && stuReportTo.getBirthPlaceDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getBirthPlacePosition()).setCellValue(stuTO.getBirthPlace());
					}
					if(stuReportTo.getBloodGrpDisplay()!=null && stuReportTo.getBloodGrpDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getBloodGrpPosition()).setCellValue(stuTO.getBloodGrp());
					}
					if(stuReportTo.getPassportNoDisplay()!=null && stuReportTo.getPassportNoDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getPassportNoPosition()).setCellValue(stuTO.getPassportNo());
					}
					if(stuReportTo.getPassportValidityDisplay()!=null && stuReportTo.getPassportValidityDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getPassportValidityPosition()).setCellValue(stuTO.getPassportValidity());
					}
					if(stuReportTo.getNationalityDisplay()!=null && stuReportTo.getNationalityDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getNationalityPosition()).setCellValue(stuTO.getNationality());
					}
					if(stuReportTo.getCasteDisplay()!=null && stuReportTo.getCasteDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getCastePosition()).setCellValue(stuTO.getCaste());
					}
					if(stuReportTo.getCurrentAddress1Display()!=null && stuReportTo.getCurrentAddress1Display().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getCurrentAddress1Position()).setCellValue(stuTO.getCurrentAddress1());
					}
					if(stuReportTo.getCurrentAddress2Display()!=null && stuReportTo.getCurrentAddress2Display().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getCurrentAddress2Position()).setCellValue(stuTO.getCurrentAddress2());
					}
					if(stuReportTo.getCurrentAddressCityDisplay()!=null && stuReportTo.getCurrentAddressCityDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getCurrentAddressCityPosition()).setCellValue(stuTO.getCurrentAddressCity());
					}
					if(stuReportTo.getCurrentAddressStateDisplay()!=null && stuReportTo.getCurrentAddressStateDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getCurrentAddressStatePosition()).setCellValue(stuTO.getCurrentAddressState());
					}
					if(stuReportTo.getCurrentAddressCountryDisplay()!=null && stuReportTo.getCurrentAddressCountryDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getCurrentAddressCountryPosition()).setCellValue(stuTO.getCurrentAddressCountry());
					}
					if(stuReportTo.getCurrentAddressZipDisplay()!=null && stuReportTo.getCurrentAddressZipDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getCurrentAddressZipPosition()).setCellValue(stuTO.getCurrentAddressZip());
					}
					if(stuReportTo.getPermAddress1Display()!=null && stuReportTo.getPermAddress1Display().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getPermAddress1Position()).setCellValue(stuTO.getPermAddress1());
					}
					if(stuReportTo.getPermAddress2Display()!=null && stuReportTo.getPermAddress2Display().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getPermAddress2Position()).setCellValue(stuTO.getPermAddress2());
					}
					if(stuReportTo.getPermanantCityCodeDisplay()!=null && stuReportTo.getPermanantCityCodeDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getPermanantCityCodePosition()).setCellValue(stuTO.getPermAddressCity());
					}
					if(stuReportTo.getPermanantStateIdDisplay()!=null && stuReportTo.getPermanantStateIdDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getPermanantStateIdPosition()).setCellValue(stuTO.getPermAddressState());
					}
					if(stuReportTo.getPermAddressCountryDisplay()!=null && stuReportTo.getPermAddressCountryDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getPermAddressCountryPosition()).setCellValue(stuTO.getPermAddressCountry());
					}
					if(stuReportTo.getPermAddressZipDisplay()!=null && stuReportTo.getPermAddressZipDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getPermAddressZipPosition()).setCellValue(stuTO.getPermAddressZip());
					}
					if(stuReportTo.getFatherNameDisplay()!=null && stuReportTo.getFatherNameDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getFatherNamePosition()).setCellValue(stuTO.getFatherName());
					}
					if(stuReportTo.getMotherNameDisplay()!=null && stuReportTo.getMotherNameDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getMotherNamePosition()).setCellValue(stuTO.getMotherName());
					}
					if(stuReportTo.getGuardianAddress1Display()!=null && stuReportTo.getGuardianAddress1Display().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getGuardianAddress1Position()).setCellValue(stuTO.getGuardianAddress1());
					}
					if(stuReportTo.getGuardianAddress2Display()!=null && stuReportTo.getGuardianAddress2Display().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getGuardianAddress2Position()).setCellValue(stuTO.getGuardianAddress2());
					}
					if(stuReportTo.getGuardianAddressCityDisplay()!=null && stuReportTo.getGuardianAddressCityDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getGuardianAddressCityPosition()).setCellValue(stuTO.getGuardianAddressCity());
					}
					if(stuReportTo.getGuardianAddressStateDisplay()!=null && stuReportTo.getGuardianAddressStateDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getGuardianAddressStatePosition()).setCellValue(stuTO.getGuardianAddressState());
					}
					if(stuReportTo.getGuardianAddressCountryDisplay()!=null && stuReportTo.getGuardianAddressCountryDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getGuardianAddressCountryPosition()).setCellValue(stuTO.getGuardianAddressCountry());
					}
					if(stuReportTo.getGuardianAddressZipDisplay()!=null && stuReportTo.getGuardianAddressZipDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getGuardianAddressZipPosition()).setCellValue(stuTO.getGuardianAddressZip());
					}
					if(stuReportTo.getParentNoDisplay()!=null && stuReportTo.getParentNoDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getParentNoPosition()).setCellValue(stuTO.getParentNo());
					}
					if(stuReportTo.getSecondLanguageDisplay()!=null && stuReportTo.getSecondLanguageDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getSecondLanguagePosition()).setCellValue(stuTO.getSecondLanguage());
					}
					if(stuReportTo.getCourseDisplay()!=null && stuReportTo.getCourseDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getCoursePosition()).setCellValue(stuTO.getCourseName());
					}
					if(stuReportTo.getIsHandicappedDisplay()!=null && stuReportTo.getIsHandicappedDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getIsHandicappedPosition()).setCellValue(stuTO.getIsHandicapped());
					}
					if(stuReportTo.getHandicappedDescriptionDisplay()!=null && stuReportTo.getHandicappedDescriptionDisplay().equalsIgnoreCase(StudentDetailsReportHelper.DISPLAY)){
						row.createCell((short)stuReportTo.getHandicappedDescriptionPosition()).setCellValue(stuTO.getHandicappedDescription());
					}
					
				}
				bos=new ByteArrayOutputStream();
				wb.write(bos);
				HttpSession session = request.getSession();
				session.setAttribute(CMSConstants.EXCEL_BYTES,bos.toByteArray());
				isUpdated=true;
				fos.flush();
				fos.close();
				
			}catch (Exception e) {
				//throw new ApplicationException();
				// TODO: handle exception
				
			}
		}
		return isUpdated;

		
	}

	

}
