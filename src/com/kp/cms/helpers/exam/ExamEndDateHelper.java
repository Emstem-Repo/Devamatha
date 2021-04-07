package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamEndDate;
import com.kp.cms.to.exam.ExamEndDateTo;
import com.kp.cms.utilities.CommonUtil;

public class ExamEndDateHelper {
	private static volatile ExamEndDateHelper examEndDateHelper = null;
	private static final Log log = LogFactory.getLog(ExamEndDateHelper.class);
	private ExamEndDateHelper() {
		
	}
	/**
	 * return singleton object of PublishSupplementaryImpApplicationHelper.
	 * @return
	 */
	public static ExamEndDateHelper getInstance() {
		if (examEndDateHelper == null) {
			examEndDateHelper = new ExamEndDateHelper();
		}
		return examEndDateHelper;
	}
public List<ExamEndDateTo> convertBotoToList( List<ExamEndDate> boList) throws Exception {
		
		log.info("Entered into convertBotoToList");
		List<ExamEndDateTo> toList=new ArrayList<ExamEndDateTo>();
		ExamEndDateTo to=null;
		for (ExamEndDate bo : boList) {
			to=new ExamEndDateTo();
			to.setId(bo.getId());
			to.setExamName(bo.getExam().getName());
			if(bo.getEndDate()!=null)
			to.setEndDate(CommonUtil.formatSqlDate1(bo.getEndDate().toString()));
			toList.add(to);
		}
		log.info("Exit From convertBotoToList");
		return toList;
	}

}
