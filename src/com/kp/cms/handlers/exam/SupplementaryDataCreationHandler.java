package com.kp.cms.handlers.exam;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.forms.exam.SupplementaryDataCreationForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.helpers.exam.SupplementaryDataCreationHelper;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.transactions.exam.ISupplementaryDataCreationTransaction;
import com.kp.cms.transactionsimpl.exam.SupplementaryDataCreationImpl;

public class SupplementaryDataCreationHandler {
	private static volatile SupplementaryDataCreationHandler handler = null;
	private static final Log log = LogFactory.getLog(SupplementaryDataCreationHandler.class);
	public static SupplementaryDataCreationHandler getInstance(){
		if(handler == null){
			handler = new SupplementaryDataCreationHandler();
			return handler;
		}
		return handler;
	}
	SupplementaryDataCreationHelper helper = SupplementaryDataCreationHelper.getInstance();
	ISupplementaryDataCreationTransaction transaction = SupplementaryDataCreationImpl.getInstance();
	/**
	 * @param academicYear
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getExamNameByProcessType(String academicYear) throws Exception {
		CommonAjaxHandler commonAjaxHandler = CommonAjaxHandler.getInstance();
		Map<Integer,String> suppExamNameMap= commonAjaxHandler.getSupplementryExamNameByAcademicYear(academicYear);
		return suppExamNameMap;
	}
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public List<ClassesTO> getClassesByExamId( SupplementaryDataCreationForm objForm) throws Exception{
		String query = helper.getQueryForSupplementryDataCreation(objForm);
		List list= transaction.getDataByQuery(query);
		return helper.convertBOListToTOList(list);
	}
	/**
	 * @param classIds
	 * @param objForm
	 * @return
	 * @throws Exception 
	 */
	public List<String> getAlreadyModifiedStudents(String classIds, SupplementaryDataCreationForm objForm) throws Exception {
		String query="select s.student.registerNo from StudentSupplementaryImprovementApplication s" +
		" where s.examDefinition.id= " +Integer.parseInt(objForm.getExamId())+
		" and s.classes.id in ("+classIds+") and (s.isAppearedPractical=1 or s.isAppearedTheory=1) group by s.student.registerNo";
		return transaction.getDataByQuery(query);
	}
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateSupplementryDataCreationProcess( SupplementaryDataCreationForm objForm) throws Exception{
		List<StudentSupplementaryImprovementApplication> boList = helper.getBoListFromToList(objForm);
		boolean isUpdated = transaction.saveSupplimentryStudentsData(boList);
		return isUpdated;
	}
}
