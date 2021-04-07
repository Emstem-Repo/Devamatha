package com.kp.cms.handlers.reports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.forms.reports.ViewInternalMarksForm;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.helpers.reports.ViewInternalMarkHelper;
import com.kp.cms.to.exam.ExamMarksEntryDetailsTO;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.reports.IViewInternalMarksTxn;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.reports.ViewInternalMarksTxnImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.ExamCodeComparator;


public class ViewInternalMarkHandler {
	private static final Log log = LogFactory.getLog(ViewInternalMarkHandler.class);
	IViewInternalMarksTxn iViewInternalMarks = ViewInternalMarksTxnImpl.getInstance();
	public static volatile ViewInternalMarkHandler self=null;
	/**
	 * @return
	 */
	public static ViewInternalMarkHandler getInstance(){
		if(self==null){
			self= new ViewInternalMarkHandler();
		}
		return self;
	}
	private ViewInternalMarkHandler(){}
	
	
	
	/**
	 * @param year
	 * @param teacherId
	 * @return
	 */
	public Map<Integer, String> getClassByYear(int year, int teacherId) {
		log.debug("Entered getClassByYear");
		Map<Integer, String> classMap = iViewInternalMarks.getClassesByYear(year,teacherId);
		classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
		log.debug("Exit getClassByYear");
		return classMap;
	}

	/**
	 * @param year
	 * @param classId
	 * @param teacherId
	 * @return
	 */
	public Map<Integer, String> getSubjectByClassYear(int year, int classId, int teacherId) {
		Map<Integer, String> subjectMap = iViewInternalMarks.getSubjectByYear(year,classId, teacherId);
		subjectMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(subjectMap);
		return subjectMap;
	}
	/**
	 * @param viewInternalMarksForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public ViewInternalMarksForm getStudentInternalMarks(ViewInternalMarksForm viewInternalMarksForm, HttpServletRequest request) throws Exception{
		List<ExamMarksEntryDetailsTO> summaryList=null;
		 summaryList = getStudentWiseInternalMarks(viewInternalMarksForm);
		 viewInternalMarksForm.setListCourseDetails(summaryList);
		return viewInternalMarksForm;
	}
	
	/**
	 * @param viewInternalMarksForm
	 * @return
	 * @throws Exception
	 */
	public List<ExamMarksEntryDetailsTO> getStudentWiseInternalMarks(ViewInternalMarksForm viewInternalMarksForm) throws Exception {
		List<MarksEntryDetails> examMarksEntryDetailsBOList =iViewInternalMarks.getStudentWiseExamMarkDetailsView(Integer.parseInt(viewInternalMarksForm.getSubjectId()),Integer.parseInt(viewInternalMarksForm.getClassId()),Integer.parseInt(viewInternalMarksForm.getYear()),Integer.parseInt(viewInternalMarksForm.getUserId()));
		String intExamQuery=ViewInternalMarkHelper.getInstance().getInternalExamIdsQuery(viewInternalMarksForm);
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<Integer> intExamIds=transaction.getDataForQuery(intExamQuery);
		List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList =  ViewInternalMarkHelper.getInstance().convertMarkList(examMarksEntryDetailsBOList,intExamIds,viewInternalMarksForm);
		ExamCodeComparator comparator=new ExamCodeComparator();
		comparator.setCompare(2);
		Collections.sort(examMarksEntryDetailsTOList,comparator);
		List<String> examNames=new ArrayList<String>();
		Iterator<Integer> itr=intExamIds.iterator();
		while (itr.hasNext()) {
			Integer id =itr.next();
			examNames.add(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(id,"ExamDefinitionBO",true,"name"));
		}
		
		viewInternalMarksForm.setExamNames(examNames);
		return examMarksEntryDetailsTOList;
	}
	
}
