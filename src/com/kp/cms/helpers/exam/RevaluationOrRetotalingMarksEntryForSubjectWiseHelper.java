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
import com.kp.cms.forms.exam.RevaluationOrRetotalingMarksEntryForSubjectWiseForm;
import com.kp.cms.to.exam.RevaluationOrRetotalingMarksEntryForSubjectWiseTo;
import com.kp.cms.transactions.exam.IRevaluationOrRetotallingMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.RevaluationOrRetotallingMarksEntryTransactionImpl;



public class RevaluationOrRetotalingMarksEntryForSubjectWiseHelper {

	private static volatile RevaluationOrRetotalingMarksEntryForSubjectWiseHelper revaluationOrRetotalingMarksEntryForSubjectWiseHelper = null;
	private static final Log log = LogFactory.getLog(RevaluationOrRetotalingMarksEntryForSubjectWiseHelper.class);
	
	/**
	 * @return
	 */
	public static RevaluationOrRetotalingMarksEntryForSubjectWiseHelper getInstance() {
		if (revaluationOrRetotalingMarksEntryForSubjectWiseHelper == null) {
			revaluationOrRetotalingMarksEntryForSubjectWiseHelper = new RevaluationOrRetotalingMarksEntryForSubjectWiseHelper();
		}
		return revaluationOrRetotalingMarksEntryForSubjectWiseHelper;
	}
	
	
	
	public List<RevaluationOrRetotalingMarksEntryForSubjectWiseTo> convertBotoTo(List<Object> boList,RevaluationOrRetotalingMarksEntryForSubjectWiseForm form) throws Exception{
		List<RevaluationOrRetotalingMarksEntryForSubjectWiseTo> toList=new ArrayList<RevaluationOrRetotalingMarksEntryForSubjectWiseTo>();
		//IRevaluationOrRetotallingMarksEntryTransaction transaction=RevaluationOrRetotallingMarksEntryTransactionImpl.getInstance();
		if(boList != null && !boList.isEmpty()){
			Iterator<Object> iterator = boList.iterator();
			while (iterator.hasNext()) {
				Object[] bo = (Object[]) iterator.next();
				RevaluationOrRetotalingMarksEntryForSubjectWiseTo to=new RevaluationOrRetotalingMarksEntryForSubjectWiseTo();
				to.setStudentId(Integer.parseInt(bo[0].toString()));
				to.setRegisterNo(bo[1].toString());
				to.setClassName(bo[2].toString());
				to.setClassId(Integer.parseInt(bo[3].toString()));
				to.setExamRevaluationAppId(Integer.parseInt(bo[4].toString()));
				to.setExamRevaluationDetailsId(Integer.parseInt(bo[5].toString()));
				to.setId(Integer.parseInt(bo[5].toString()));
				if(bo[6]!=null )
				to.setCourseId(Integer.parseInt(bo[6].toString()));
				if(bo[7]!=null )
				to.setSchemeNo(Integer.parseInt(bo[7].toString()));
				to.setYear(Integer.parseInt(bo[8].toString()));
				if(bo[9]!=null )
				to.setMarks(bo[9].toString());
				if(bo[10]!=null )
				to.setMarks1(bo[10].toString());
				if(bo[11]!=null )
				to.setMarks2(bo[11].toString());
				if(bo[12]!=null )
				to.setThirdEvlMarks(bo[12].toString());
				
				toList.add(to);
			}
		}
		
	 return toList;
	}
}
