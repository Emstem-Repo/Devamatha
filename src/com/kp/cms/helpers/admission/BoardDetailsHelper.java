package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admission.BoardDetailsForm;
import com.kp.cms.to.admission.BoardDetailsTO;

public class BoardDetailsHelper {
	/**
	 * Singleton object of BoardDetailsHelper
	 */
	private static volatile BoardDetailsHelper boardDetailsHelper = null;
	private static final Log log = LogFactory.getLog(BoardDetailsHelper.class);
	private BoardDetailsHelper() {
		
	}
	/**
	 * return singleton object of BoardDetailsHelper.
	 * @return
	 */
	public static BoardDetailsHelper getInstance() {
		if (boardDetailsHelper == null) {
			boardDetailsHelper = new BoardDetailsHelper();
		}
		return boardDetailsHelper;
	}
	/**
	 * @param boardDetailsForm
	 * @return
	 * @throws Exception
	 */
	public String getSearchQuery(BoardDetailsForm boardDetailsForm,String clsSchemeIds) throws Exception {
		if(clsSchemeIds==null || clsSchemeIds.isEmpty())
			clsSchemeIds="0";
		String query="from Student s where  s.admAppln.isCancelled=0 " +
				" and s.isActive=1 and s.classSchemewise.id in ("+clsSchemeIds+")";
		if(boardDetailsForm.getRegisterNo()!=null && !boardDetailsForm.getRegisterNo().isEmpty()){
			query=query+" and s.registerNo='"+boardDetailsForm.getRegisterNo()+"'";
		}
		return query+" order by s.admAppln.personalData.firstName";
	}
	/**
	 * @param studentList
	 * @return
	 */
	public List<BoardDetailsTO> convertBotoToList(List<Student> studentList,List<Integer> detainedList) {
		List<BoardDetailsTO> boardDetailsTOs=new ArrayList<BoardDetailsTO>();
		BoardDetailsTO boardDetailsTO;
		if(studentList!=null && !studentList.isEmpty()){
			Iterator<Student> iterator=studentList.iterator();
			while (iterator.hasNext()) {
				Student student = (Student) iterator.next();
				if(!detainedList.contains(student.getRegisterNo())){
				    boardDetailsTO=new BoardDetailsTO();
				    boardDetailsTO.setRegisterNo(student.getRegisterNo());
				    boardDetailsTO.setStudentId(student.getId());
				    boardDetailsTO.setExamRegNo(student.getExamRegisterNo());
				    boardDetailsTO.setStudentNo(student.getStudentNo());
				    boardDetailsTO.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
				    boardDetailsTO.setFatherName(student.getAdmAppln().getPersonalData().getFatherName());
				    boardDetailsTO.setMotherName(student.getAdmAppln().getPersonalData().getMotherName());
				    boardDetailsTOs.add(boardDetailsTO);
				}
			}
		}
		return boardDetailsTOs;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public String getExistsExamRegNoQuery() throws Exception {
		String query="select s.examRegisterNo,s.id from Student s where s.admAppln.isCancelled=0 and s.isActive=1 and s.examRegisterNo <> null";
		return query;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public String getExistsStuNoQuery() throws Exception {
		String query="select s.studentNo,s.id from Student s where s.admAppln.isCancelled=0 and s.isActive=1 and s.studentNo <> null";
		return query;
	}
	public String getSearchQueryForClassScheme(BoardDetailsForm boardDetailsForm) throws Exception {
		
		String query="select cs.id from ClassSchemewise cs where cs.classes.isActive=1 and cs.classes.termNumber="+boardDetailsForm.getYears()+" and cs.curriculumSchemeDuration.academicYear="+boardDetailsForm.getYear();
		if(boardDetailsForm.getClassId()!=null && !boardDetailsForm.getClassId().isEmpty()){		
			query = query+" and cs.classes.id="+boardDetailsForm.getClassId();
		}
		if(boardDetailsForm.getProgramId()!=null && !boardDetailsForm.getProgramId().isEmpty()){
			query = query+" and cs.classes.course.program.id="+boardDetailsForm.getProgramId();
		}
		if(boardDetailsForm.getCourseId()!=null && !boardDetailsForm.getCourseId().isEmpty()){
			query = query+ " and cs.classes.course.id="+boardDetailsForm.getCourseId();
		}
		return query;
	}
}
