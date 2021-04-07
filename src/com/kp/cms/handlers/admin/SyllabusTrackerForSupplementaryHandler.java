package com.kp.cms.handlers.admin;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.admin.SyllabusTrackerForSupplementaryForm;
import com.kp.cms.forms.studentLogin.SyllabusDisplayForStudentForm;
import com.kp.cms.helpers.admin.SyllabusTrackerForSupplementaryHelper;
import com.kp.cms.helpers.studentLogin.SyllabusDisplayForStudentHelper;
import com.kp.cms.to.admin.SyllabusTrackerForSupplementaryTo;
import com.kp.cms.to.studentLogin.SyllabusDisplayForStudentTo;
import com.kp.cms.transactions.admin.ISyllabusTrackerForSupplementaryTransaction;
import com.kp.cms.transactions.studentLogin.ISyllabusDisplayForStudentTransaction;
import com.kp.cms.transactionsimpl.admin.SyllabusTrackerForSupplementaryTransactionImpl;
import com.kp.cms.transactionsimpl.studentLogin.SyllabusDisplayForStudentTransactionImpl;


public class SyllabusTrackerForSupplementaryHandler {
	private static final Log log = LogFactory.getLog(SyllabusTrackerForSupplementaryHandler.class);	
	public static volatile SyllabusTrackerForSupplementaryHandler syllabusTrackerForSupplementaryHandler = null;
	
	public static SyllabusTrackerForSupplementaryHandler getInstance() {
		if (syllabusTrackerForSupplementaryHandler == null) {
			syllabusTrackerForSupplementaryHandler = new SyllabusTrackerForSupplementaryHandler();
		}
		return syllabusTrackerForSupplementaryHandler;
	}
	public Map<String, String>  getDeanaryList()throws Exception{
		ISyllabusTrackerForSupplementaryTransaction transaction=SyllabusTrackerForSupplementaryTransactionImpl.getInstance();
		Map<String, String>  deanaryList=transaction.gettingDeanaryList();
		return deanaryList;
	}
	
	public List<SyllabusTrackerForSupplementaryTo> getStudentBacklocs(SyllabusTrackerForSupplementaryForm form)throws Exception{
		ISyllabusTrackerForSupplementaryTransaction transaction=SyllabusTrackerForSupplementaryTransactionImpl.getInstance();
		List<Object[]> boList=transaction.getStudentDetails(form);
		List<SyllabusTrackerForSupplementaryTo> toList=SyllabusTrackerForSupplementaryHelper.getInstance().convertBotoTo(boList,form);
		Collections.sort(toList);
		return toList;
		
	}
	public List<Object[]> getCurrentStudyingBatchjoiningYear(SyllabusTrackerForSupplementaryForm form)throws Exception{
		ISyllabusTrackerForSupplementaryTransaction transaction=SyllabusTrackerForSupplementaryTransactionImpl.getInstance();
		List<Object[]> admYear=transaction.getCurrentStudyingBatchjoiningYear(form);
		return admYear;
	}

	public String getcurrentStudyingBatchSubjectName(int csdId,SyllabusTrackerForSupplementaryForm form)throws Exception{
		ISyllabusTrackerForSupplementaryTransaction transaction=SyllabusTrackerForSupplementaryTransactionImpl.getInstance();
		String subjectName=transaction.getSubjectName(csdId,form);
		return subjectName;
	}
}
