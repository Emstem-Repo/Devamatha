package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.InternalMarkSupplementaryDetails;
import com.kp.cms.bo.exam.StudentOverallInternalMarkDetails;
import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.forms.exam.NewInternalMarksSupplementaryForm;
import com.kp.cms.to.exam.ExamInternalMarksSupplementaryTO;

public class NewInternalMarksSupplementaryHelper {
	/**
	 * Singleton object of NewInternalMarksSupplementaryHelper
	 */
	private static volatile NewInternalMarksSupplementaryHelper newInternalMarksSupplementaryHelper = null;
	private static final Log log = LogFactory.getLog(NewInternalMarksSupplementaryHelper.class);
	private NewInternalMarksSupplementaryHelper() {
		
	}
	/**
	 * return singleton object of NewInternalMarksSupplementaryHelper.
	 * @return
	 */
	public static NewInternalMarksSupplementaryHelper getInstance() {
		if (newInternalMarksSupplementaryHelper == null) {
			newInternalMarksSupplementaryHelper = new NewInternalMarksSupplementaryHelper();
		}
		return newInternalMarksSupplementaryHelper;
	}
	
	/**
	 * @param newInternalMarksSupplementaryForm
	 * @return
	 * @throws Exception
	 */
	public String getInternalSupplementaryQuery(NewInternalMarksSupplementaryForm newInternalMarksSupplementaryForm) throws Exception {
		String query="from InternalMarkSupplementaryDetails e where e.subject.isActive=1 and e.classes.course.id="+newInternalMarksSupplementaryForm.getCourseId()+
				" and e.classes.termNumber="+newInternalMarksSupplementaryForm.getSchemeNo()+" and e.exam.id="+newInternalMarksSupplementaryForm.getExamId()+" and e.student.id="+newInternalMarksSupplementaryForm.getStudentId();
//		if(newInternalMarksSupplementaryForm.getRegisterNo()!=null && !newInternalMarksSupplementaryForm.getRegisterNo().isEmpty()){
//			query=query+" and e.student.registerNo='"+newInternalMarksSupplementaryForm.getRegisterNo()+"'";
//		}
//		if(newInternalMarksSupplementaryForm.getRollNo()!=null && !newInternalMarksSupplementaryForm.getRollNo().isEmpty()){
//			query=query+" and e.student.rollNo='"+newInternalMarksSupplementaryForm.getRollNo()+"'";
//		}
		return query;
	}
	/**
	 * @param intList
	 * @return
	 * @throws Exception
	 */
	public void convertBOtoMap(List intList,Map<Integer,ExamInternalMarksSupplementaryTO> map) throws Exception{
		if(intList!=null && !intList.isEmpty()){
			Iterator itr=intList.iterator();
			while (itr.hasNext()) {
				InternalMarkSupplementaryDetails bo = (InternalMarkSupplementaryDetails) itr.next();
				if(!map.containsKey(bo.getSubject().getId())){
					ExamInternalMarksSupplementaryTO to=new ExamInternalMarksSupplementaryTO();
					to.setId(bo.getId());
					to.setStudentId(bo.getStudent().getId());
					to.setSubjectId(bo.getSubject().getId());
					to.setSubjectCode(bo.getSubject().getCode());
					to.setSubjectName(bo.getSubject().getName());
					to.setTheoryTotalSubInternalMarks(bo.getTheoryTotalSubInternalMarks());
					to.setPracticalTotalSubInternalMarks(bo.getPracticalTotalSubInternalMarks());
					to.setOriginalTotalSubInternalMarks(bo.getTheoryTotalSubInternalMarks());
					to.setOriginalPracticalSubInternalMarks(bo.getPracticalTotalSubInternalMarks());
					to.setIsTheoryPrac(bo.getSubject().getIsTheoryPractical());
					to.setId(bo.getId());
					to.setClassId(bo.getClasses().getId());
					to.setExamId(bo.getExam().getId());
					to.setStudentId(bo.getStudent().getId());
					to.setIsTheoryPrac(bo.getSubject().getIsTheoryPractical());
					map.put(to.getSubjectId(),to);
				}
			}
			
		}
	}
	/**
	 * @param newInternalMarksSupplementaryForm
	 * @return
	 * @throws Exception
	 */
	public String getOverAllQuery(NewInternalMarksSupplementaryForm newInternalMarksSupplementaryForm) throws Exception{
		String query="from StudentOverallInternalMarkDetails e where e.subject.isActive=1 and e.student.admAppln.courseBySelectedCourseId.id="+newInternalMarksSupplementaryForm.getCourseId()+
		" and e.classes.termNumber="+newInternalMarksSupplementaryForm.getSchemeNo()+" and e.student.id="+newInternalMarksSupplementaryForm.getStudentId();
//		if(newInternalMarksSupplementaryForm.getRegisterNo()!=null && !newInternalMarksSupplementaryForm.getRegisterNo().isEmpty()){
//			query=query+" and e.student.registerNo='"+newInternalMarksSupplementaryForm.getRegisterNo()+"'";
//		}
//		if(newInternalMarksSupplementaryForm.getRollNo()!=null && !newInternalMarksSupplementaryForm.getRollNo().isEmpty()){
//			query=query+" and e.student.rollNo='"+newInternalMarksSupplementaryForm.getRollNo()+"'";
//		}
		return query;
	}
	/**
	 * @param intMap
	 * @param overList
	 * @return
	 * @throws Exception
	 */
	public List<ExamInternalMarksSupplementaryTO> getFinalToList(Map<Integer, ExamInternalMarksSupplementaryTO> intMap, List overList) throws Exception{
		List<ExamInternalMarksSupplementaryTO> finalList=new ArrayList<ExamInternalMarksSupplementaryTO>();
		if(overList!=null && !overList.isEmpty()){
			Iterator itr=overList.iterator();
			while (itr.hasNext()) {
				StudentOverallInternalMarkDetails bo = (StudentOverallInternalMarkDetails) itr.next();
				if(intMap.containsKey(bo.getSubject().getId())){
					finalList.add(intMap.get(bo.getSubject().getId()));
				}else{
					ExamInternalMarksSupplementaryTO to=new ExamInternalMarksSupplementaryTO();
					to.setStudentId(bo.getStudent().getId());
					to.setExamId(bo.getExam().getId());
					to.setClassId(bo.getClasses().getId());
					to.setSubjectId(bo.getSubject().getId());
					to.setSubjectCode(bo.getSubject().getCode());
					to.setSubjectName(bo.getSubject().getName());
					to.setTheoryTotalSubInternalMarks(bo.getTheoryTotalSubInternalMarks());
					to.setPracticalTotalSubInternalMarks(bo.getPracticalTotalSubInternalMarks());
					to.setOriginalTotalSubInternalMarks(bo.getTheoryTotalSubInternalMarks());
					to.setOriginalPracticalSubInternalMarks(bo.getPracticalTotalSubInternalMarks());
					to.setPracticalTotalAttendenceMarks(bo.getPracticalTotalAttendenceMarks());
					to.setTheoryTotalAttendenceMarks(bo.getTheoryTotalAttendenceMarks());
					to.setTheoryMarks(bo.getTheoryTotalMarks());
					to.setPracticalMarks(bo.getPracticalTotalMarks());
					to.setPassOrFail(bo.getPassOrFail());
					to.setIsTheoryPrac(bo.getSubject().getIsTheoryPractical());
					finalList.add(to);
				}
			}
		}
		return finalList;
	}
	/**
	 * @param newInternalMarksSupplementaryForm
	 * @return
	 * @throws Exception
	 */
	public List<InternalMarkSupplementaryDetails> convertTOtoBOList(NewInternalMarksSupplementaryForm newInternalMarksSupplementaryForm) throws Exception{
		List<InternalMarkSupplementaryDetails> boList=new ArrayList<InternalMarkSupplementaryDetails>();
		List<ExamInternalMarksSupplementaryTO> toList=newInternalMarksSupplementaryForm.getStuMarkList();
		Iterator<ExamInternalMarksSupplementaryTO> itr=toList.iterator();
		boolean isAdd=false;
		while (itr.hasNext()) {
			ExamInternalMarksSupplementaryTO to = (ExamInternalMarksSupplementaryTO) itr.next();
			isAdd=false;
			if(to.getIsTheoryPrac().equalsIgnoreCase("T")){
				if(!to.getTheoryTotalSubInternalMarks().equals(to.getOriginalTotalSubInternalMarks()!=null?to.getOriginalTotalSubInternalMarks():"")){
					isAdd=true;
				}
			}else if(to.getIsTheoryPrac().equalsIgnoreCase("P")){
				if(!to.getPracticalTotalSubInternalMarks().equals(to.getOriginalPracticalSubInternalMarks()!=null?to.getOriginalPracticalSubInternalMarks():"")){
					isAdd=true;
				}
				
			}else if(to.getIsTheoryPrac().equalsIgnoreCase("B")){
				if(!to.getTheoryTotalSubInternalMarks().equals(to.getOriginalTotalSubInternalMarks()) || !to.getPracticalTotalSubInternalMarks().equals(to.getOriginalPracticalSubInternalMarks())){
					isAdd=true;
				}
			}
			if(isAdd){
				InternalMarkSupplementaryDetails bo=new InternalMarkSupplementaryDetails();
				if(to.getId()!=null && to.getId()>0){
					bo.setId(to.getId());
				}
				Student student=new Student();
				student.setId(to.getStudentId());
				bo.setStudent(student);
				Subject subject=new Subject();
				subject.setId(to.getSubjectId());
				bo.setSubject(subject);
				Classes classes=new Classes();
				classes.setId(to.getClassId());
				bo.setClasses(classes);
				ExamDefinition exam=new ExamDefinition();
				exam.setId(Integer.parseInt(newInternalMarksSupplementaryForm.getExamId()));
				bo.setExam(exam);
				
				if(to.getTheoryTotalSubInternalMarks()!=null && !to.getTheoryTotalSubInternalMarks().trim().isEmpty())
					bo.setTheoryTotalSubInternalMarks(to.getTheoryTotalSubInternalMarks());
				else
					bo.setTheoryTotalSubInternalMarks(null);
				if(to.getPracticalTotalSubInternalMarks()!=null && !to.getPracticalTotalSubInternalMarks().trim().isEmpty())
					bo.setPracticalTotalSubInternalMarks(to.getPracticalTotalSubInternalMarks());
				else
					bo.setPracticalTotalSubInternalMarks(null);
				bo.setPracticalTotalAttendenceMarks(to.getPracticalTotalAttendenceMarks());
				bo.setTheoryTotalAttendenceMarks(to.getTheoryTotalAttendenceMarks());
				bo.setTheoryTotalMarks(to.getTheoryMarks());
				bo.setPracticalTotalMarks(to.getPracticalMarks());
				bo.setPassOrFail(to.getPassOrFail());
				bo.setLastModifiedDate(new Date());
				boList.add(bo);
			}
			
		}
		return boList;
	}
	/**
	 * @param newInternalMarksSupplementaryForm
	 * @return
	 * @throws Exception
	 */
	public String getLatestInternalSupplementaryQuery(NewInternalMarksSupplementaryForm newInternalMarksSupplementaryForm) throws Exception {
		String query="from InternalMarkSupplementaryDetails e where e.subject.isActive=1 and e.student.admAppln.courseBySelectedCourseId.id="+newInternalMarksSupplementaryForm.getCourseId()+
			" and e.classes.termNumber="+newInternalMarksSupplementaryForm.getSchemeNo()+" and e.student.id="+newInternalMarksSupplementaryForm.getStudentId();
//		if(newInternalMarksSupplementaryForm.getRegisterNo()!=null && !newInternalMarksSupplementaryForm.getRegisterNo().isEmpty()){
//			query=query+" and e.student.registerNo='"+newInternalMarksSupplementaryForm.getRegisterNo()+"'";
//		}
//		if(newInternalMarksSupplementaryForm.getRollNo()!=null && !newInternalMarksSupplementaryForm.getRollNo().isEmpty()){
//			query=query+" and e.student.rollNo='"+newInternalMarksSupplementaryForm.getRollNo()+"'";
//		}
		return query+" order by e.subject.id,e.exam.year desc, e.exam.month desc";
		}
	
	/**
	 * @param intMap
	 * @param overList
	 * @return
	 * @throws Exception
	 */
	public List<ExamInternalMarksSupplementaryTO> getFinalToListBySupplementaryApplication(Map<Integer, ExamInternalMarksSupplementaryTO> intMap, List<StudentSupplementaryImprovementApplication> supList) throws Exception{
		List<ExamInternalMarksSupplementaryTO> finalList=new ArrayList<ExamInternalMarksSupplementaryTO>();
		if(supList!=null && !supList.isEmpty()){
			Iterator<StudentSupplementaryImprovementApplication> itr=supList.iterator();
			while (itr.hasNext()) {
				StudentSupplementaryImprovementApplication bo = itr.next();
				if(intMap.containsKey(bo.getSubject().getId())){
					finalList.add(intMap.get(bo.getSubject().getId()));
				}else{
					ExamInternalMarksSupplementaryTO to=new ExamInternalMarksSupplementaryTO();
					to.setStudentId(bo.getStudent().getId());
					to.setExamId(bo.getExamDefinition().getId());
					to.setClassId(bo.getClasses().getId());
					to.setSubjectId(bo.getSubject().getId());
					to.setSubjectCode(bo.getSubject().getCode());
					to.setSubjectName(bo.getSubject().getName());
					to.setIsTheoryPrac(bo.getSubject().getIsTheoryPractical());
					finalList.add(to);
				}
			}
		}
		return finalList;
	}
}