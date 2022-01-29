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
import com.kp.cms.forms.admission.TCDetailsClassWiseForm;
import com.kp.cms.forms.admission.TCDetailsForm;
import com.kp.cms.to.admission.BoardDetailsTO;
import com.kp.cms.to.admission.CharacterAndConductTO;
import com.kp.cms.to.admission.TCDetailsTO;
import com.kp.cms.utilities.CommonUtil;

public class TCDetailsClassWiseHelper {
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	/**
	 * Singleton object of TCDetailsHelper
	 */
	private static volatile TCDetailsClassWiseHelper tCDetailsHelper = null;
	private static final Log log = LogFactory.getLog(TCDetailsClassWiseHelper.class);
	private TCDetailsClassWiseHelper() {
		
	}
	/**
	 * return singleton object of TCDetailsHelper.
	 * @return
	 */
	public static TCDetailsClassWiseHelper getInstance() {
		if (tCDetailsHelper == null) {
			tCDetailsHelper = new TCDetailsClassWiseHelper();
		}
		return tCDetailsHelper;
	}
	
	/**
	 * @param boardDetailsForm
	 * @return
	 * @throws Exception
	 */
	public String getSearchQuery(TCDetailsClassWiseForm tcDetailsForm) throws Exception
	{
		String query="from Student s where  s.admAppln.isCancelled=0 " +
				" and s.isActive=1 and s.classSchemewise.id="+tcDetailsForm.getClassId();
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
	public String getSearchQueryForTCDetails(TCDetailsForm tcDetailsForm) throws Exception {
		String query=" from Student s where s.id="+tcDetailsForm.getStudentId();
		return query;
	}
	/**
	 * @param student
	 * @param tcDetailsForm
	 * @throws Exception
	 */
	public void convertBotoForm(Student student, TCDetailsForm tcDetailsForm) throws Exception {
		TCDetailsTO to=new TCDetailsTO();
		to.setRegisterNo(student.getRegisterNo());
		to.setStudentId(student.getId());
		if(student.getStudentTCDetails()!=null && !student.getStudentTCDetails().isEmpty()){
			Iterator<StudentTCDetails> itr=student.getStudentTCDetails().iterator();
			if (itr.hasNext()) {
				StudentTCDetails bo=(StudentTCDetails)itr.next();
				to.setId(bo.getId());
				to.setFeePaid(bo.getFeePaid());
				to.setScholarship(bo.getScholarship());
				to.setPassed(bo.getPassed());
				to.setDateOfApplication(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(bo.getDateOfApplication()),TCDetailsClassWiseHelper.SQL_DATEFORMAT,TCDetailsClassWiseHelper.FROM_DATEFORMAT));
				to.setDateOfLeaving(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(bo.getDateOfLeaving()),TCDetailsClassWiseHelper.SQL_DATEFORMAT,TCDetailsClassWiseHelper.FROM_DATEFORMAT));
				to.setReasonOfLeaving(bo.getReasonOfLeaving());
				if(bo.getCharacterAndConduct()!=null){
					to.setCharacterId(String.valueOf(bo.getCharacterAndConduct().getId()));
				}
				to.setMonth(bo.getMonth());
				to.setYear(String.valueOf(bo.getYear()));
				to.setSubjectPassed(bo.getSubjectPassed());
				}
			}else{
				to.setFeePaid("yes");
				to.setPassed("yes");
				to.setScholarship("no");
				to.setYear(tcDetailsForm.getYear());
				to.setMonth(tcDetailsForm.getMonth());
			}
		tcDetailsForm.setTcDetailsTO(to);
	}
	/**
	 * @param tcDetailsForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentTCDetails> convertTotoBo(TCDetailsClassWiseForm tcDetailsForm) throws Exception
	{
		TCDetailsTO to=tcDetailsForm.getTcDetailsTO();
		List<StudentTCDetails> studentList=new ArrayList<StudentTCDetails>();
		for(String studentId:tcDetailsForm.getPassedStudents())
		{	
			StudentTCDetails bo=new StudentTCDetails();
			if(to!=null)
			{
				bo.setCreatedBy(tcDetailsForm.getUserId());
				bo.setCreatedDate(new Date());
				bo.setPassed("yes");
				bo.setFeePaid(to.getFeePaid());
				bo.setScholarship(to.getScholarship());
				bo.setReasonOfLeaving(to.getReasonOfLeaving());
				bo.setModifiedBy(tcDetailsForm.getUserId());
				bo.setLastModifiedDate(new Date());
				bo.setIsActive(true);
				bo.setDateOfApplication(CommonUtil.ConvertStringToSQLDate(to.getDateOfApplication()));
				bo.setDateOfLeaving(CommonUtil.ConvertStringToSQLDate(to.getDateOfLeaving()));
				Student student=new Student();
				student.setId(Integer.parseInt(studentId));
				bo.setStudent(student);
				if(to.getCharacterId()!=null && !to.getCharacterId().isEmpty()){
					CharacterAndConduct conduct=new CharacterAndConduct();
					conduct.setId(Integer.parseInt(to.getCharacterId()));
					bo.setCharacterAndConduct(conduct);
				}
				bo.setMonth(to.getMonth());
				bo.setYear(Integer.parseInt(to.getYear()));
				bo.setSubjectPassed(to.getSubjectPassed());
				bo.setPublicExaminationName(to.getPublicExamName());
				bo.setShowRegNo(to.getShowRegisterNo());
				studentList.add(bo);
			}	
		}
		return studentList;
	}
	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<CharacterAndConductTO> convertBoListToTOList(List<CharacterAndConduct> list)  throws Exception{
		List<CharacterAndConductTO> finalList=new ArrayList<CharacterAndConductTO>();
		if(list!=null && !list.isEmpty()){
			Iterator<CharacterAndConduct> itr=list.iterator();
			while (itr.hasNext()) {
				CharacterAndConduct bo = (CharacterAndConduct) itr.next();
				CharacterAndConductTO to=new CharacterAndConductTO();
				to.setId(bo.getId());
				to.setName(bo.getName());
				finalList.add(to);
			}
		}
		return finalList;
	}
}
