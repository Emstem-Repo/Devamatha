package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.exam.ExamSubCoursewiseGradeDefnBO;
import com.kp.cms.forms.exam.GradeClassDefinitionEntryForm;
import com.kp.cms.helpers.exam.AttendanceMarksEntryHelper;
import com.kp.cms.helpers.exam.GradeClassDefinitionEntryHelper;
import com.kp.cms.to.exam.AttendanceMarkAndPercentageTO;
import com.kp.cms.to.exam.GradeClassDefinitionTO;
import com.kp.cms.transactions.exam.IAttendanceMarksEntryTransaction;
import com.kp.cms.transactions.exam.IGradeClassDefinitionEntryTransaction;
import com.kp.cms.transactionsimpl.exam.AttendanceMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.GradeClassDefinitionEntryTransactionImpl;

public class GradeClassDefinitionEntryHandler {

	private static volatile GradeClassDefinitionEntryHandler gradeClassDefinitionEntryHandler= null;
	private static final Log log = LogFactory.getLog(GradeClassDefinitionEntryHandler.class);
	public static GradeClassDefinitionEntryHandler getInstance()
	{
		if(gradeClassDefinitionEntryHandler== null)
		{
			GradeClassDefinitionEntryHandler gradeClassDefinitionEntryHandler = new GradeClassDefinitionEntryHandler();
			return gradeClassDefinitionEntryHandler; 
		}
		return gradeClassDefinitionEntryHandler;
	}
	public boolean addGradeClassDefinition(GradeClassDefinitionEntryForm gradeClassDefinitionEntryForm,HttpServletRequest request, ActionErrors errors) throws Exception {
		log.debug("call of addGradeClassDefinition method in GradeClassDefinitionEntryHandler.class");
		boolean isAdded = false;
		IGradeClassDefinitionEntryTransaction  transaction = new GradeClassDefinitionEntryTransactionImpl().getInstance();
		List<ExamSubCoursewiseGradeDefnBO> gradeClassBoList = new ArrayList<ExamSubCoursewiseGradeDefnBO>();
		gradeClassBoList = GradeClassDefinitionEntryHelper.getInstance().converFormTOBOList(gradeClassDefinitionEntryForm,request,errors,transaction);
		isAdded =  transaction.addGradeClassBoList(gradeClassBoList);
		log.debug("end of addGradeClassDefinition method in GradeClassDefinitionEntryHandler.class");
		return isAdded;
	}
	public List<GradeClassDefinitionTO> getGradeDefinitionList() throws Exception {
		log.debug("call of getGradeDefinitionList method in GradeClassDefinitionEntryHandler.class");
		List<GradeClassDefinitionTO> gradeFeefinitionList = new ArrayList<GradeClassDefinitionTO>();
		List<Object[]> bolists = new ArrayList<Object[]>();
		IGradeClassDefinitionEntryTransaction transaction = new GradeClassDefinitionEntryTransactionImpl().getInstance();
		bolists = transaction.getGradeClassDefinitionList();
		gradeFeefinitionList = GradeClassDefinitionEntryHelper.getInstance().convertObjectListOfGradeDefinitionToTOList(bolists);
		log.debug("end of getGradeDefinitionList method in GradeClassDefinitionEntryHandler.class");
		return gradeFeefinitionList;
	}
}
