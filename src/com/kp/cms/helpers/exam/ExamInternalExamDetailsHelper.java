package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.List;

import com.kp.cms.bo.exam.ExamInternalExamDetailsBO;
import com.kp.cms.to.exam.ExamInternalExamDetailsTO;

public class ExamInternalExamDetailsHelper {
	/**
	 * Dec 24, 2009
	 * Created By 9Elements Team
	 */
	
	public List<ExamInternalExamDetailsTO> convertBOToTO(List<ExamInternalExamDetailsBO> listBO) {
		
		ArrayList<ExamInternalExamDetailsTO> listTO = new ArrayList<ExamInternalExamDetailsTO>();
		
		ExamInternalExamDetailsTO eTO;
		
		for (ExamInternalExamDetailsBO eBO : listBO) {

			// eTO = new ExamInternalExamDetailsTO();
			// eTO.setId(eBO.getId());
			// eTO.setExamId(eBO.getExamId());
			// eTO.setInternalExamNameId(eBO.getInternalExamNameId());
			// eTO.setCreatedBy(eBO.getCreatedBy());
			// eTO.setCreatedDate(eBO.getCreatedDate());
			// eTO.setModifiedBy(eBO.getModifiedBy());
			// eTO.setLastModifiedDate(eBO.getLastModifiedDate());
			
		}
		return listTO;
	}
}
