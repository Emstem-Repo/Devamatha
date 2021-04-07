package com.kp.cms.handlers.exam;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.ExamRevaluationAppDetails;
import com.kp.cms.bo.exam.ExamRevaluationApplication;
import com.kp.cms.bo.exam.ExamRevaluationFee;
import com.kp.cms.bo.exam.StudentFinalMarkDetails;
import com.kp.cms.forms.exam.ExamRevaluationOfflineAppForm;
import com.kp.cms.helpers.exam.ExamRevaluationOfflineAppHelper;
import com.kp.cms.to.exam.ExamRevaluationApplicationTO;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.PropertyUtil;

public class ExamRevaluationOfflineAppHandler {
	/**
	 * Singleton object of ExamRevaluationOfflineAppHandler
	 */
	private static volatile ExamRevaluationOfflineAppHandler examRevaluationOfflineAppHandler = null;
	private static final Log log = LogFactory.getLog(ExamRevaluationOfflineAppHandler.class);
	private ExamRevaluationOfflineAppHandler() {
		
	}
	/**
	 * return singleton object of ExamRevaluationOfflineAppHandler.
	 * @return
	 */
	public static ExamRevaluationOfflineAppHandler getInstance() {
		if (examRevaluationOfflineAppHandler == null) {
			examRevaluationOfflineAppHandler = new ExamRevaluationOfflineAppHandler();
		}
		return examRevaluationOfflineAppHandler;
	}
	/**
	 * @param examRevaluationOfflineAppForm
	 * @return
	 * @throws Exception
	 */
	public List<ExamRevaluationApplicationTO> getStudentSubjectsForRevApp(ExamRevaluationOfflineAppForm examRevaluationOfflineAppForm) throws Exception{
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		String query=ExamRevaluationOfflineAppHelper.getInstance().getQueryForInput(examRevaluationOfflineAppForm);
		List<StudentFinalMarkDetails> list=transaction.getDataForQuery(query);
		
		String revaluationSubjectQuery="from ExamRevaluationAppDetails e where e.examRevApp.isActive=1 and e.examRevApp.exam.id=" +examRevaluationOfflineAppForm.getExamId()+
		" and e.examRevApp.student.registerNo='"+examRevaluationOfflineAppForm.getRegisterNo()+"' and e.examRevApp.classes.termNumber="+examRevaluationOfflineAppForm.getSchemeNo();
		List<ExamRevaluationAppDetails> revaluationSubjectList=transaction.getDataForQuery(revaluationSubjectQuery);
		
		Map<Integer,Map<Integer,String>> revaluationSubjects=new HashMap<Integer,Map<Integer,String>>();
		Map<Integer,String> valueMap=null;
		if(revaluationSubjectList!=null && !revaluationSubjectList.isEmpty()){
			Iterator<ExamRevaluationAppDetails> itr=revaluationSubjectList.iterator();
			while (itr.hasNext()) {
				ExamRevaluationAppDetails bo =itr .next();
				if(revaluationSubjects.containsKey(bo.getExamRevApp().getClasses().getId()))
					valueMap=revaluationSubjects.remove(bo.getExamRevApp().getClasses().getId());
				else
					valueMap=new HashMap<Integer, String>();
				valueMap.put(bo.getSubject().getId(),bo.getStatus());
				revaluationSubjects.put(bo.getExamRevApp().getClasses().getId(),valueMap);
			}
		}
		String revaluationFeeQuery="from ExamRevaluationFee e where e.isActive=1 and e.programType.id=" +
		" (select s.admAppln.courseBySelectedCourseId.program.programType.id from Student s where registerNo='"+examRevaluationOfflineAppForm.getRegisterNo()+"')";
		List<ExamRevaluationFee> revalationList=transaction.getDataForQuery(revaluationFeeQuery);
		Map<String,Double> revalationFeeMap=new HashMap<String, Double>();
		if(revalationList!=null && !revalationList.isEmpty()){
			Iterator<ExamRevaluationFee> itr=revalationList.iterator();
			while (itr.hasNext()) {
				ExamRevaluationFee examRevaluationFee = itr .next();
				if(examRevaluationFee.getType()!=null && !revalationFeeMap.containsKey(examRevaluationFee.getType())){
					revalationFeeMap.put(examRevaluationFee.getType(), examRevaluationFee.getAmount().doubleValue());
				}
			}
		}
		examRevaluationOfflineAppForm.setRevalationFeeMap(revalationFeeMap);
		return ExamRevaluationOfflineAppHelper.getInstance().convertBotoToList(list,revaluationSubjects,examRevaluationOfflineAppForm);
	}
	/**
	 * @param examRevaluationOfflineAppForm
	 * @return
	 */
	public boolean saveRevaluationData(ExamRevaluationOfflineAppForm examRevaluationOfflineAppForm) throws Exception {
		List<ExamRevaluationApplicationTO> examRevaluationApplicationTOs=examRevaluationOfflineAppForm.getRevAppList();

		ExamRevaluationApplication bo=new ExamRevaluationApplication();
		bo.setIsActive(true);
		bo.setCreatedDate(new Date());
		bo.setLastModifiedDate(new Date());
		ExamDefinition exam=new ExamDefinition();
		exam.setId(Integer.parseInt(examRevaluationOfflineAppForm.getExamId()));
		bo.setExam(exam);
		Student student=new Student();
		student.setId(examRevaluationOfflineAppForm.getStudentId());
		bo.setStudent(student);
		Classes classes=new Classes();
		classes.setId(examRevaluationOfflineAppForm.getClassesId());
		bo.setClasses(classes);
		bo.setDdDate(CommonUtil.ConvertStringToSQLDate(examRevaluationOfflineAppForm.getDdDate()));
		bo.setDdNumber(examRevaluationOfflineAppForm.getDdNo());
		bo.setAmount(Double.parseDouble(examRevaluationOfflineAppForm.getAmount()));
		bo.setBank(examRevaluationOfflineAppForm.getBankName());
		bo.setBranchName(examRevaluationOfflineAppForm.getBranchName());
		
		Set<ExamRevaluationAppDetails> examRevaluationAppDetails =new HashSet<ExamRevaluationAppDetails>();
		
		Iterator<ExamRevaluationApplicationTO> itr=examRevaluationApplicationTOs.iterator();
		while (itr.hasNext()) {
			ExamRevaluationApplicationTO subjectTO =itr.next();
			if(subjectTO.getRevType()!=null && !subjectTO.getRevType().isEmpty()){
				ExamRevaluationAppDetails subBo=new ExamRevaluationAppDetails();
				subBo.setCreatedDate(new Date());
				subBo.setLastModifiedDate(new Date());
				subBo.setIsActive(true);
				Subject subject=new Subject();
				subject.setId(subjectTO.getSubjectId());
				subBo.setSubject(subject);
				subBo.setStatus(subjectTO.getRevType()+" Under Process");
				subBo.setType(subjectTO.getRevType());
				examRevaluationAppDetails.add(subBo);
			}
		}
		bo.setExamRevaluationAppDetails(examRevaluationAppDetails);
		return PropertyUtil.getInstance().save(bo);
	}
	
	/**
	 * @param examRevaluationOfflineAppForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkValidDDdetails(ExamRevaluationOfflineAppForm examRevaluationOfflineAppForm) throws Exception {
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		String query="from ExamRevaluationApplication e where e.isActive=1 and e.ddDate='"+CommonUtil.ConvertStringToSQLDate(examRevaluationOfflineAppForm.getDdDate())+"' and e.ddNumber='"+examRevaluationOfflineAppForm.getDdNo()+"' ";
		List list=transaction.getDataForQuery(query);
		if(list!=null && !list.isEmpty())
			return false;
		else
			return true;
	}
}
