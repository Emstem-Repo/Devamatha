package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.StudentFinalMarkDetails;
import com.kp.cms.forms.exam.ExamRevaluationOfflineAppForm;
import com.kp.cms.to.exam.ExamRevaluationApplicationTO;

public class ExamRevaluationOfflineAppHelper {
	/**
	 * Singleton object of ExamRevaluationOfflineAppHelper
	 */
	private static volatile ExamRevaluationOfflineAppHelper examRevaluationOfflineAppHelper = null;
	private static final Log log = LogFactory.getLog(ExamRevaluationOfflineAppHelper.class);
	private ExamRevaluationOfflineAppHelper() {
		
	}
	/**
	 * return singleton object of ExamRevaluationOfflineAppHelper.
	 * @return
	 */
	public static ExamRevaluationOfflineAppHelper getInstance() {
		if (examRevaluationOfflineAppHelper == null) {
			examRevaluationOfflineAppHelper = new ExamRevaluationOfflineAppHelper();
		}
		return examRevaluationOfflineAppHelper;
	}
	/**
	 * @param examRevaluationOfflineAppForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForInput( ExamRevaluationOfflineAppForm examRevaluationOfflineAppForm) throws Exception {
		StringBuffer sf=new StringBuffer("from StudentFinalMarkDetails s where s.student.isActive=1 and (s.student.isHide=0 or s.student.isHide is null) and s.student.admAppln.isCancelled=0 and (s.subject.isTheoryPractical='T' or s.subject.isTheoryPractical='B') and s.studentTheoryMarks is not null and  s.studentTheoryMarks!='' and s.exam.id=");
		sf.append(examRevaluationOfflineAppForm.getExamId());
		sf.append(" and s.student.registerNo='");
		sf.append(examRevaluationOfflineAppForm.getRegisterNo());
		sf.append("'");
		sf.append(" and s.classes.termNumber=");
		sf.append(examRevaluationOfflineAppForm.getSchemeNo());
		return sf.toString();
	}
	/**
	 * @param list
	 * @param revaluationSubjects
	 * @return
	 * @throws Exception
	 */
	public List<ExamRevaluationApplicationTO> convertBotoToList( List<StudentFinalMarkDetails> list, Map<Integer, Map<Integer, String>> revaluationSubjects
			,ExamRevaluationOfflineAppForm examRevaluationOfflineAppForm) throws Exception {
		List<ExamRevaluationApplicationTO> toList=new ArrayList<ExamRevaluationApplicationTO>();
		if(list!=null && !list.isEmpty()){
			Iterator<StudentFinalMarkDetails> itr=list.iterator();
			int count=0;
			while (itr.hasNext()) {
				StudentFinalMarkDetails studentFinalMarkDetails = (StudentFinalMarkDetails) itr.next();
				if(count==0){
					examRevaluationOfflineAppForm.setStudentId(studentFinalMarkDetails.getStudent().getId());
					examRevaluationOfflineAppForm.setClassesId(studentFinalMarkDetails.getClasses().getId());
					count++;
				}
				ExamRevaluationApplicationTO to=new ExamRevaluationApplicationTO();
				if(revaluationSubjects!=null && !revaluationSubjects.isEmpty() && revaluationSubjects.containsKey(studentFinalMarkDetails.getClasses().getId())){
					Map<Integer,String> valueMap=revaluationSubjects.get(studentFinalMarkDetails.getClasses().getId());
					
					
					if(valueMap.containsKey(studentFinalMarkDetails.getSubject().getId())){
						to.setStatus(valueMap.get(studentFinalMarkDetails.getSubject().getId()));
						to.setRevaluationReq(false);
					}else{
//						if(studentFinalMarkDetails.get!=null && !subto.getEseMaxMarks().equalsIgnoreCase("-"))
							to.setRevaluationReq(true);
					}
				}else{
//					if(studentFinalMarkDetails.get!=null && !subto.getEseMaxMarks().equalsIgnoreCase("-"))
					to.setRevaluationReq(true);
			}
				to.setSubjectCode(studentFinalMarkDetails.getSubject().getCode());
				to.setSubjectName(studentFinalMarkDetails.getSubject().getName());
				to.setSubjectId(studentFinalMarkDetails.getSubject().getId());
				to.setExamId(studentFinalMarkDetails.getExam().getId());
				to.setClassId(studentFinalMarkDetails.getClasses().getId());
				to.setClassName(studentFinalMarkDetails.getClasses().getName());
				to.setStudentId(studentFinalMarkDetails.getStudent().getId());
				examRevaluationOfflineAppForm.setStudentName(studentFinalMarkDetails.getStudent().getAdmAppln().getPersonalData().getFirstName());
				toList.add(to);
			}
		}
		return toList;
	}
}
