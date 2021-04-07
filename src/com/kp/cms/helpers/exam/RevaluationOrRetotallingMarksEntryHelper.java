package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.exam.ExamRevaluationAppDetails;
import com.kp.cms.bo.exam.ExamRevaluationApplication;
import com.kp.cms.forms.attendance.ChangeSubjectForm;
import com.kp.cms.forms.exam.RevaluationOrRetotallingMarksEntryForm;
import com.kp.cms.to.attendance.AttendanceTO;
import com.kp.cms.to.exam.RevaluationOrRetotallingMarksEntryTo;
import com.kp.cms.transactions.exam.IRevaluationOrRetotallingMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.RevaluationOrRetotallingMarksEntryTransactionImpl;


public class RevaluationOrRetotallingMarksEntryHelper {
	private static volatile RevaluationOrRetotallingMarksEntryHelper revaluationOrRetotallingMarksEntryHelper = null;
	private static final Log log = LogFactory.getLog(RevaluationOrRetotallingMarksEntryHelper.class);
	
	/**
	 * @return
	 */
	public static RevaluationOrRetotallingMarksEntryHelper getInstance() {
		if (revaluationOrRetotallingMarksEntryHelper == null) {
			revaluationOrRetotallingMarksEntryHelper = new RevaluationOrRetotallingMarksEntryHelper();
		}
		return revaluationOrRetotallingMarksEntryHelper;
	}
	
	/**
	 * @param boList
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public List<RevaluationOrRetotallingMarksEntryTo> convertBotoTo(List<ExamRevaluationApplication> boList,RevaluationOrRetotallingMarksEntryForm  form) throws Exception{
		List<RevaluationOrRetotallingMarksEntryTo> toList=new ArrayList<RevaluationOrRetotallingMarksEntryTo>();
		IRevaluationOrRetotallingMarksEntryTransaction transaction=RevaluationOrRetotallingMarksEntryTransactionImpl.getInstance();
		if(boList != null && !boList.isEmpty()){
			Iterator<ExamRevaluationApplication> iterator = boList.iterator();
			while (iterator.hasNext()) {
				ExamRevaluationApplication bo = (ExamRevaluationApplication) iterator.next();
				String className=bo.getClasses().getName();
				form.setCourseid(bo.getClasses().getCourse().getId());
				form.setSchemeno(bo.getClasses().getTermNumber());
				if(bo.getClasses().getClassSchemewises()!=null){
					Set<ClassSchemewise> set =bo.getClasses().getClassSchemewises();
					if(set != null && !set.isEmpty()){
							Iterator<ClassSchemewise> iterator2 = set.iterator();
							while (iterator2.hasNext()) {
								ClassSchemewise ed = (ClassSchemewise) iterator2.next();
								form.setSelectYear(ed.getCurriculumSchemeDuration().getAcademicYear());
							}
					}
				}
				Set<ExamRevaluationAppDetails> set = bo.getExamRevaluationAppDetails();
				if(set != null && !set.isEmpty()){
				int count =0;
					Iterator<ExamRevaluationAppDetails> iterator2 = set.iterator();
					while (iterator2.hasNext()) {
						ExamRevaluationAppDetails ed = (ExamRevaluationAppDetails) iterator2.next();
						RevaluationOrRetotallingMarksEntryTo to= new RevaluationOrRetotallingMarksEntryTo();
						to.setId(ed.getId());
						to.setName(ed.getSubject().getName());
						to.setCode(ed.getSubject().getCode());
						to.setClassName(className);
						to.setSubjectId(ed.getSubject().getId());
						boolean EvaluatorType=transaction.getEvaluatorType(ed.getSubject().getId(),form);
						if(EvaluatorType){
							
							List l=transaction.getEvaluatorTypes(ed);
							int evalsize=Integer.parseInt(l.get(0).toString());
							if(evalsize==3){
								to.setEvaluatorType1(true);
								to.setEvaluatorType2(true);
								to.setEvaluatorType3(true);
								to.setMarks1(ed.getMark1());
								to.setMarks2(ed.getMark2());
								to.setOldMarks1(ed.getMark1());
								to.setOldMarks2(ed.getMark2());
								//new
								to.setMarks3(ed.getMark3());
								to.setOldMarks3(ed.getMark3());
							}else if(evalsize==2){
								to.setEvaluatorType1(true);
								to.setEvaluatorType2(true);
								to.setMarks1(ed.getMark1());
								to.setMarks2(ed.getMark2());
								to.setOldMarks1(ed.getMark1());
								to.setOldMarks2(ed.getMark2());
							}else if(evalsize==1){
								to.setEvaluatorType1(true);
								to.setMarks1(ed.getMark1());
								to.setOldMarks1(ed.getMark1());
							}
							
							to.setEvaluatorTypeSize(evalsize+"");
						}else{
						to.setEvaluatorType(true);
						to.setMarks(ed.getMarks());
						to.setOldMarks(ed.getMarks());
						to.setEvaluatorTypeSize("0");
						}
						toList.add(to);
						count++;
					}
					form.setCount(count);
					
				}
				
			}
		}
		
	 return toList;
	}
}
