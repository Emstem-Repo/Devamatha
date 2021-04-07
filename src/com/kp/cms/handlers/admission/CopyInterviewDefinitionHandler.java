package com.kp.cms.handlers.admission;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.forms.admission.CopyInterviewDefinitionForm;
import com.kp.cms.helpers.admission.CopyCheckListAssignmentHelper;
import com.kp.cms.helpers.admission.CopyInterviewDefinitionHelper;
import com.kp.cms.to.admission.CheckListTO;
import com.kp.cms.to.admission.InterviewProgramCourseTO;
import com.kp.cms.transactions.admission.ICopyInterviewDefinitionTransaction;
import com.kp.cms.transactionsimpl.admission.CopyInterviewDefinitionTransactionImpl;

public class CopyInterviewDefinitionHandler {

	/**
	 * Singleton object of CopyInterviewDefinitionHandler
	 */
	private static volatile CopyInterviewDefinitionHandler copyInterviewDefinitionHandler = null;
	private static final Log log = LogFactory.getLog(CopyInterviewDefinitionHandler.class);
	ICopyInterviewDefinitionTransaction transaction= CopyInterviewDefinitionTransactionImpl.getInstance();
	private CopyInterviewDefinitionHandler() {
		
	}
	/**
	 * return singleton object of CopyInterviewDefinitionHandler.
	 * @return
	 */
	public static CopyInterviewDefinitionHandler getInstance() {
		if (copyInterviewDefinitionHandler == null) {
			copyInterviewDefinitionHandler = new CopyInterviewDefinitionHandler();
		}
		return copyInterviewDefinitionHandler;
	}
	/**
	 * getting the interview definition by year from impl
	 * @param year
	 * @param copyForm
	 * @return
	 * @throws Exception
	 */
	public List<InterviewProgramCourseTO> getInterviewDefinitionByYear(int year,CopyInterviewDefinitionForm copyForm) throws Exception {
		
		//getting the details of InterviewProgramCourse BO from Impl.. 
		List<InterviewProgramCourse> interviewProgramCourse = transaction.getInterviewDefinitionByYear(year);
		//conversion of BO to TO in helper by passing interviewProgramCourse of type InterviewProgramCourse..
		List<InterviewProgramCourseTO> interviewProgramCourseTO = CopyInterviewDefinitionHelper.getInstance().convertBOstoInterviewTOs(interviewProgramCourse);
		return interviewProgramCourseTO;
	}
	
	
	
	/**
	 * Converting the TO from form to BO to save in the database
	 * @param copyForm
	 * @return
	 * @throws Exception
	 */
	public boolean copyInterviewDefinition(CopyInterviewDefinitionForm copyForm) throws Exception {
		boolean isCopied =false;
		List<InterviewProgramCourse> intPrgCourse = CopyInterviewDefinitionHelper.getInstance().convertTOToBO(copyForm);
		if(intPrgCourse != null && !intPrgCourse.isEmpty()){
			isCopied = transaction.copyInterviewDefinition(intPrgCourse);
		}
		return isCopied;
		
	}
	

}
