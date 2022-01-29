package com.kp.cms.helpers.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PayScaleBO;
import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.exam.ExamRevaluationFee;
import com.kp.cms.forms.exam.ExamRevaluationFeeForm;
import com.kp.cms.to.employee.PayScaleTO;
import com.kp.cms.to.exam.ExamRevaluationFeeTO;

public class ExamRevaluationFeeHelper {
	private static final Log log = LogFactory.getLog(ExamRevaluationFeeHelper.class);
	public static volatile ExamRevaluationFeeHelper examRevaluation = null;

	public static ExamRevaluationFeeHelper getInstance() {
		if (examRevaluation == null) {
			examRevaluation = new ExamRevaluationFeeHelper();
			return examRevaluation;
		}
		return examRevaluation;
	}
	/**
	 * @param examRevaluationFeeForm
	 * @param mode
	 * @return
	 */
	public ExamRevaluationFee convertFormTOBO(ExamRevaluationFeeForm examRevaluationFeeForm,String mode){
		ExamRevaluationFee revaluation=new ExamRevaluationFee();
		if(examRevaluationFeeForm.getProgramTypeId()!=null){
			ProgramType pType=new ProgramType();
			pType.setId(Integer.parseInt(examRevaluationFeeForm.getProgramTypeId()));
			revaluation.setProgramType(pType);
		}
		if(examRevaluationFeeForm.getType()!=null)
			revaluation.setType(examRevaluationFeeForm.getType());
		if(examRevaluationFeeForm.getAmount()!=null){
			BigDecimal amount=new BigDecimal(examRevaluationFeeForm.getAmount());
			revaluation.setAmount(amount);
		}
		revaluation.setIsActive(true);
		if(mode.equalsIgnoreCase("add")){
			revaluation.setCreatedBy(examRevaluationFeeForm.getUserId());
			revaluation.setCreatedDate(new Date());
		}else{
			revaluation.setId(examRevaluationFeeForm.getId());
			revaluation.setModifiedBy(examRevaluationFeeForm.getUserId());
			revaluation.setLastModifiedDate(new Date());
		}
			
	return revaluation;
	}
	 /**
	 * @param examRevaluationFeeForm
	 * @param revaluation
	 */
	public void setBotoForm(ExamRevaluationFeeForm examRevaluationFeeForm,ExamRevaluationFee revaluation){
	    	if(revaluation!=null){
	    		examRevaluationFeeForm.setProgramTypeId(String.valueOf(revaluation.getProgramType().getId()));
	    		examRevaluationFeeForm.setType(revaluation.getType());
	    		examRevaluationFeeForm.setAmount(revaluation.getAmount().toString());
	    	}
	    }
	 /**
	 * @param revaluationFee
	 * @return
	 */
	public List<ExamRevaluationFeeTO> convertBosToTOs(List<ExamRevaluationFee> revaluationFee){
		 List<ExamRevaluationFeeTO> revaluationToList=new ArrayList<ExamRevaluationFeeTO>();
			Iterator itr=revaluationFee.iterator();
			while(itr.hasNext()){
				ExamRevaluationFee revaluation = (ExamRevaluationFee)itr.next();
				ExamRevaluationFeeTO revaluationTO=new ExamRevaluationFeeTO();
				if(revaluation.getProgramType().getId()!=0)
					revaluationTO.setProgramType(revaluation.getProgramType().getName());
				if(revaluation.getType()!=null)
					revaluationTO.setType(revaluation.getType());
				if(revaluation.getAmount()!=null)
					revaluationTO.setAmount(revaluation.getAmount().toString());
				revaluationTO.setId(revaluation.getId());
				revaluationToList.add(revaluationTO);
			}
			return revaluationToList;
		}
}
