package com.kp.cms.helpers.exam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.UGConsolidateMarksCardBO;
import com.kp.cms.forms.exam.ExamConsolidateMarksCardForm;

public class ExamConsolidateMarksCardHelper {
	
	private static final Log log = LogFactory.getLog(ExamConsolidateMarksCardHelper.class);
	private static volatile ExamConsolidateMarksCardHelper examConsolidateMarksCardHelper= null;
	
	/*
	 *  returns the singleton object of ExamConsolidateMarksCardHelper.
	 */
	public static ExamConsolidateMarksCardHelper getInstance() {
	      if(examConsolidateMarksCardHelper == null) {
	    	  examConsolidateMarksCardHelper = new ExamConsolidateMarksCardHelper();
	      }
	      return examConsolidateMarksCardHelper;
	}

	/*public UGConsolidateMarksCardBO convertFormToBO(ExamConsolidateMarksCardForm examConsolidateMarksCardForm) {
		
		int courseId=0,year=0;
		if(examConsolidateMarksCardForm.getCourseId()!=null && !examConsolidateMarksCardForm.getCourseId().isEmpty()){
			 courseId=Integer.parseInt(examConsolidateMarksCardForm.getCourseId().trim());
		}
		if(examConsolidateMarksCardForm.getYear()!=null && !examConsolidateMarksCardForm.getYear().isEmpty()){
			 year = Integer.parseInt(examConsolidateMarksCardForm.getYear().trim());
		}
		//UGConsolidateMarksCardBO ugConsolidateMarksCardBO = Exam
		return null;
	}*/

}
