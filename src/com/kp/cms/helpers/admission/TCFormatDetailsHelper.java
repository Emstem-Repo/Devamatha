package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.CharacterAndConduct;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentTCDetails;
import com.kp.cms.forms.admission.TCDetailsForm;
import com.kp.cms.forms.admission.TCFormatDetailsForm;
import com.kp.cms.to.admission.BoardDetailsTO;
import com.kp.cms.to.admission.TCDetailsTO;
import com.kp.cms.utilities.CommonUtil;

public class TCFormatDetailsHelper {
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	/**
	 * Singleton object of TCFormatDetailsHelper
	 */
	private static volatile TCFormatDetailsHelper tCFormatDetailsHelper = null;
	private static final Log log = LogFactory.getLog(TCFormatDetailsHelper.class);
	private TCFormatDetailsHelper() {
		
	}
	/**
	 * return singleton object of TCFormatDetailsHandler.
	 * @return
	 */
	public static TCFormatDetailsHelper getInstance() {
		if (tCFormatDetailsHelper == null) {
			tCFormatDetailsHelper = new TCFormatDetailsHelper();
		}
		return tCFormatDetailsHelper;
	}
	
	/**
	 * @param boardDetailsForm
	 * @return
	 * @throws Exception
	 */
	public String getSearchQuery(TCFormatDetailsForm tcFormatDetailsForm) throws Exception {
		String query="from Student s where  s.admAppln.isCancelled=0 " +
				" and s.isActive=1 and s.classSchemewise.id="+tcFormatDetailsForm.getClassId();
		if(tcFormatDetailsForm.getRegisterNo()!=null && !tcFormatDetailsForm.getRegisterNo().isEmpty()){
			query=query+" and s.registerNo='"+tcFormatDetailsForm.getRegisterNo()+"'";
		}
		return query+" order by s.admAppln.personalData.firstName";
	}
	
	/**
	 * @param studentList
	 * @return
	 */
	public List<BoardDetailsTO> convertBotoToList(List<Student> studentList) {
		List<BoardDetailsTO> boardDetailsTOs=new ArrayList<BoardDetailsTO>();
		BoardDetailsTO boardDetailsTO;
		if(studentList!=null && !studentList.isEmpty()){
			Iterator<Student> iterator=studentList.iterator();
			while (iterator.hasNext()) {
				Student student = (Student) iterator.next();
				boardDetailsTO=new BoardDetailsTO();
				boardDetailsTO.setRegisterNo(student.getRegisterNo());
				boardDetailsTO.setStudentId(student.getId());
				boardDetailsTO.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
				boardDetailsTOs.add(boardDetailsTO);
			}
		}
		return boardDetailsTOs;
	}
	/**
	 * @param tcDetailsForm
	 * @return
	 * @throws Exception
	 */
	public String getSearchQueryForTCDetails(TCFormatDetailsForm tcFormatDetailsForm) throws Exception {
		String query=" from Student s where s.id="+tcFormatDetailsForm.getStudentId();
		return query;
	}
	/**
	 * @param student
	 * @param tcDetailsForm
	 * @throws Exception
	 */
	public void convertBotoForm(Student student,TCFormatDetailsForm tcFormatDetailsForm) throws Exception {
		TCDetailsTO to=new TCDetailsTO();
		to.setRegisterNo(student.getRegisterNo());
		to.setStudentId(student.getId());
		if(student.getStudentTCDetails()!=null && !student.getStudentTCDetails().isEmpty()){
			Iterator<StudentTCDetails> itr=student.getStudentTCDetails().iterator();
			if (itr.hasNext()) {
				StudentTCDetails bo=(StudentTCDetails)itr.next();
				to.setId(bo.getId());
				to.setEligible(bo.getEligible());
				to.setDateOfLeaving(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(bo.getDateOfLeaving()),TCFormatDetailsHelper.SQL_DATEFORMAT,TCFormatDetailsHelper.FROM_DATEFORMAT));
				to.setReasonOfLeaving(bo.getReasonOfLeaving());
				if(bo.getCharacterAndConduct()!=null){
					to.setCharacterId(String.valueOf(bo.getCharacterAndConduct().getId()));
				}
				to.setSubjectStudied(bo.getSubjectStudied());
				to.setMonth(bo.getMonth());
				to.setYear(bo.getYear().toString());
				if(bo.getIsFeePaidUni()){
					to.setFeePaidUni("yes");
				}
				else{
					to.setFeePaidUni("no");	
				}
			}

			}else{
				to.setEligible("yes");
				to.setMonth(tcFormatDetailsForm.getMonth());
				to.setYear(tcFormatDetailsForm.getYear());
				to.setFeePaidUni("yes");
			}
		tcFormatDetailsForm.setTcDetailsTO(to);
	}
	/**
	 * @param tcDetailsForm
	 * @return
	 * @throws Exception
	 */
	public StudentTCDetails convertTotoBo(TCFormatDetailsForm tcFormatDetailsForm) throws Exception{
		TCDetailsTO to=tcFormatDetailsForm.getTcDetailsTO();
		StudentTCDetails bo=new StudentTCDetails();
		if(to!=null){
			if(to.getId()>0){
				bo.setId(to.getId());
				bo.setCreatedBy(tcFormatDetailsForm.getUserId());
				bo.setCreatedDate(new Date());
			}
//			bo.setPassed(to.getPassed());
//			bo.setFeePaid(to.getFeePaid());
//			bo.setScholarship(to.getScholarship());
			bo.setReasonOfLeaving(to.getReasonOfLeaving());
			bo.setModifiedBy(tcFormatDetailsForm.getUserId());
			bo.setLastModifiedDate(new Date());
			bo.setIsActive(true);
			bo.setEligible(to.getEligible());
//			bo.setDateOfApplication(CommonUtil.ConvertStringToSQLDate(to.getDateOfApplication()));
			bo.setDateOfLeaving(CommonUtil.ConvertStringToSQLDate(to.getDateOfLeaving()));
			Student student=new Student();
			student.setId(to.getStudentId());
			bo.setStudent(student);
			if(to.getCharacterId()!=null && !to.getCharacterId().isEmpty()){
				CharacterAndConduct conduct=new CharacterAndConduct();
				conduct.setId(Integer.parseInt(to.getCharacterId()));
				bo.setCharacterAndConduct(conduct);
			}
			bo.setSubjectStudied(to.getSubjectStudied());
			bo.setMonth(to.getMonth());
			bo.setYear(Integer.parseInt(to.getYear()));
			if(to.getFeePaidUni().equalsIgnoreCase("yes")){
				bo.setIsFeePaidUni(true);
			}else{
				bo.setIsFeePaidUni(false);
			}
			
		}
		return bo;
	}
	
	/**
	 * 
	 * @param tcFormatDetailsForm
	 * @param studentList
	 * @return
	 * @throws Exception
	 */
	public List<StudentTCDetails> processConvertTotoBo(TCFormatDetailsForm tcFormatDetailsForm, List<BoardDetailsTO> studentList ) throws Exception{
		TCDetailsTO to=tcFormatDetailsForm.getTcDetailsTO();
		Iterator<BoardDetailsTO> itr = studentList.iterator();
		List<StudentTCDetails> tcDetailsList = new ArrayList<StudentTCDetails>();
		while (itr.hasNext()) {
			BoardDetailsTO boardDetailsTO = (BoardDetailsTO) itr.next();
			if(to!=null){
				Student student = new Student();
				StudentTCDetails bo = new StudentTCDetails();
				student.setId(boardDetailsTO.getStudentId());
				bo.setStudent(student);
				
				bo.setCreatedBy(tcFormatDetailsForm.getUserId());
				bo.setCreatedDate(new Date());
				bo.setModifiedBy(tcFormatDetailsForm.getUserId());
				bo.setLastModifiedDate(new Date());
				bo.setReasonOfLeaving(to.getReasonOfLeaving());
				bo.setModifiedBy(tcFormatDetailsForm.getUserId());
				bo.setLastModifiedDate(new Date());
				bo.setIsActive(true);
				bo.setEligible(to.getEligible());
				bo.setDateOfLeaving(CommonUtil.ConvertStringToSQLDate(to.getDateOfLeaving()));

				if(to.getCharacterId()!=null && !to.getCharacterId().isEmpty()){
					CharacterAndConduct conduct=new CharacterAndConduct();
					conduct.setId(Integer.parseInt(to.getCharacterId()));
					bo.setCharacterAndConduct(conduct);
				}
				bo.setSubjectStudied(to.getSubjectStudied());
				bo.setMonth(to.getMonth());
				bo.setYear(Integer.parseInt(to.getYear()));
				if(to.getFeePaidUni().equalsIgnoreCase("yes")){
					bo.setIsFeePaidUni(true);
				}else{
					bo.setIsFeePaidUni(false);
				}
				tcDetailsList.add(bo);
			}
		}
		
		return tcDetailsList;
	}
}
