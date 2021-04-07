package com.kp.cms.helpers.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.exam.ValuatorChargesBo;
import com.kp.cms.forms.exam.ValuatorChargesForm;
import com.kp.cms.to.exam.ValuatorChargeTo;

public class ValuatorChargesHelper {

	private static final Log log=LogFactory.getLog(ValuatorChargesHelper.class);
	public static volatile ValuatorChargesHelper valuatorChargesHelper = null;

	public static ValuatorChargesHelper getInstance() {
		if (valuatorChargesHelper == null) {
			valuatorChargesHelper = new ValuatorChargesHelper();
			return valuatorChargesHelper;
		}
		return valuatorChargesHelper;
	}

	public List<ValuatorChargeTo> convertBosToTOs(List<ValuatorChargesBo> valuatorBoList) {
		List<ValuatorChargeTo> ValuatorChargeToList=new ArrayList<ValuatorChargeTo>();
		Iterator itr=valuatorBoList.iterator();
		while (itr.hasNext()) {
			ValuatorChargesBo valuatorBo=(ValuatorChargesBo)itr.next();
			ValuatorChargeTo  valuatorTo=new ValuatorChargeTo();
			
			valuatorTo.setId(valuatorBo.getId());
			valuatorTo.setProgramTypeId(String.valueOf(valuatorBo.getProgramType().getName()));
			if(valuatorBo.getValuatorcharge()!=null)
			{
			valuatorTo.setValuatorcharge(valuatorBo.getValuatorcharge().toString());
			}if(valuatorBo.getReviewercharge()!=null)
			{
			valuatorTo.setReviewercharge(valuatorBo.getReviewercharge().toString());
			}if(valuatorBo.getProjectevaluationminor()!=null)
			{
			valuatorTo.setProjectevaluationminor(valuatorBo.getProjectevaluationminor().toString());
			}if(valuatorBo.getProjectevaluationmajor()!=null)
			{
			valuatorTo.setProjectevaluationmajor(valuatorBo.getProjectevaluationmajor().toString());
			}
			if(valuatorBo.getBoardMeetingCharge() != null){
				valuatorTo.setBoardMeetingCharge(valuatorBo.getBoardMeetingCharge().toString());
			}
			ValuatorChargeToList.add(valuatorTo);
		}
		return ValuatorChargeToList;
	}

	public ValuatorChargesBo convertFormTOBO(ValuatorChargesForm valuatorChargesForm) {
		ValuatorChargesBo valuatorBo=new ValuatorChargesBo();
		ProgramType type =new ProgramType();
		type.setId(Integer.parseInt(valuatorChargesForm.getProgramTypeId()));
		valuatorBo.setProgramType(type);
		if(valuatorChargesForm.getValuatorcharge()!=null && !valuatorChargesForm.getValuatorcharge().equalsIgnoreCase(""))
		{
		valuatorBo.setValuatorcharge(new BigDecimal(valuatorChargesForm.getValuatorcharge()));
		}if(valuatorChargesForm.getReviewercharge()!=null && !valuatorChargesForm.getReviewercharge().equalsIgnoreCase(""))
		{
		valuatorBo.setReviewercharge(new BigDecimal(valuatorChargesForm.getReviewercharge()));
		}if(valuatorChargesForm.getProjectevaluationminor()!=null && !valuatorChargesForm.getProjectevaluationminor().isEmpty())
		{
		valuatorBo.setProjectevaluationminor(new BigDecimal(valuatorChargesForm.getProjectevaluationminor()));
		}if(valuatorChargesForm.getProjectevaluationmajor()!=null && !valuatorChargesForm.getProjectevaluationmajor().equalsIgnoreCase(""))
		{
		valuatorBo.setProjectevaluationmajor(new BigDecimal(valuatorChargesForm.getProjectevaluationmajor()));
		}
		valuatorBo.setCreatedby(valuatorChargesForm.getUserId());
		valuatorBo.setCreatedDate(new Date());
		valuatorBo.setLastModifiedDate(new Date());
		valuatorBo.setModifiedBy(valuatorChargesForm.getUserId());
		valuatorBo.setIsActive(true);
		if(valuatorChargesForm.getBoardMeetingCharge() != null && !valuatorChargesForm.getBoardMeetingCharge().trim().isEmpty()){
			valuatorBo.setBoardMeetingCharge(new BigDecimal(valuatorChargesForm.getBoardMeetingCharge()));
		}
		if(valuatorChargesForm.getMinimumScripts() != null && !valuatorChargesForm.getMinimumScripts().trim().isEmpty()){
			valuatorBo.setMinimumScripts(Integer.parseInt(valuatorChargesForm.getMinimumScripts()));
		}
		if(valuatorChargesForm.getMinimumprojectevaluationmajor() != null && !valuatorChargesForm.getMinimumprojectevaluationmajor().trim().isEmpty()){
			valuatorBo.setMinimumprojectevaluationmajor(new BigDecimal(valuatorChargesForm.getMinimumprojectevaluationmajor()));
		}
		if(valuatorChargesForm.getMinimumprojectevaluationminor() != null && !valuatorChargesForm.getMinimumprojectevaluationminor().trim().isEmpty()){
			valuatorBo.setMinimumprojectevaluationminor(new BigDecimal(valuatorChargesForm.getMinimumprojectevaluationminor()));
		}
		if(valuatorChargesForm.getMinimumreviewercharge() != null && !valuatorChargesForm.getMinimumreviewercharge().trim().isEmpty()){
			valuatorBo.setMinimumreviewercharge(new BigDecimal(valuatorChargesForm.getMinimumreviewercharge()));
		}
		if(valuatorChargesForm.getMinimumvaluatorcharge() != null && !valuatorChargesForm.getMinimumvaluatorcharge().trim().isEmpty()){
			valuatorBo.setMinimumvaluatorcharge(new BigDecimal(valuatorChargesForm.getMinimumvaluatorcharge()));
		}
		return valuatorBo;
	}

	public void setBotoForm(ValuatorChargesForm valuatorChargesForm,ValuatorChargesBo valuatorBo) 
	{
		if(valuatorBo!=null){
			valuatorChargesForm.setProgramTypeId(String.valueOf(valuatorBo.getProgramType().getId()));
			if(valuatorBo.getValuatorcharge()!=null)
			{
			valuatorChargesForm.setValuatorcharge(String.valueOf(valuatorBo.getValuatorcharge()));
			}if(valuatorBo.getReviewercharge()!=null)
			{
			valuatorChargesForm.setReviewercharge(String.valueOf(valuatorBo.getReviewercharge()));
			}if(valuatorBo.getProjectevaluationminor()!=null)
			{
			valuatorChargesForm.setProjectevaluationminor(String.valueOf(valuatorBo.getProjectevaluationminor()));
			}if(valuatorBo.getProjectevaluationmajor()!=null)
			{
			valuatorChargesForm.setProjectevaluationmajor(String.valueOf(valuatorBo.getProjectevaluationmajor()));
			}
			if(valuatorBo.getBoardMeetingCharge()!=null){
				valuatorChargesForm.setBoardMeetingCharge(String.valueOf(valuatorBo.getBoardMeetingCharge()));
			}else{
				valuatorChargesForm.setBoardMeetingCharge(null);
			}
			
			if(valuatorBo.getMinimumScripts() != null && valuatorBo.getMinimumScripts() != 0){
				valuatorChargesForm.setMinimumScripts(String.valueOf(valuatorBo.getMinimumScripts()));
			}
			if(valuatorBo.getMinimumprojectevaluationmajor() != null){
				valuatorChargesForm.setMinimumprojectevaluationmajor(String.valueOf(valuatorBo.getMinimumprojectevaluationmajor()));
			}
			if(valuatorBo.getMinimumprojectevaluationminor() != null){
				valuatorChargesForm.setMinimumprojectevaluationminor(String.valueOf(valuatorBo.getMinimumprojectevaluationminor()));
			}
			if(valuatorBo.getMinimumreviewercharge() != null){
				valuatorChargesForm.setMinimumreviewercharge(String.valueOf(valuatorBo.getMinimumreviewercharge()));
			}
			if(valuatorBo.getMinimumvaluatorcharge() != null){
				valuatorChargesForm.setMinimumvaluatorcharge(String.valueOf(valuatorBo.getMinimumvaluatorcharge()));
			}
			
		}
	}

	public ValuatorChargesBo convertFormToBO(ValuatorChargesBo valuatorBo, ValuatorChargesForm valuatorChargesForm) {
		ProgramType type =new ProgramType();
		type.setId(Integer.parseInt(valuatorChargesForm.getProgramTypeId()));
		valuatorBo.setProgramType(type);
		if(valuatorChargesForm.getValuatorcharge()!=null && !valuatorChargesForm.getValuatorcharge().equalsIgnoreCase(""))
		{
		valuatorBo.setValuatorcharge(new BigDecimal(valuatorChargesForm.getValuatorcharge()));
		}if(valuatorChargesForm.getReviewercharge()!=null && !valuatorChargesForm.getReviewercharge().equalsIgnoreCase(""))
		{
		valuatorBo.setReviewercharge(new BigDecimal(valuatorChargesForm.getReviewercharge()));
		}if(valuatorChargesForm.getProjectevaluationminor()!=null && !valuatorChargesForm.getProjectevaluationminor().isEmpty())
		{
		valuatorBo.setProjectevaluationminor(new BigDecimal(valuatorChargesForm.getProjectevaluationminor()));
		}if(valuatorChargesForm.getProjectevaluationmajor()!=null && !valuatorChargesForm.getProjectevaluationmajor().equalsIgnoreCase(""))
		{
		valuatorBo.setProjectevaluationmajor(new BigDecimal(valuatorChargesForm.getProjectevaluationmajor()));
		}
		if(valuatorChargesForm.getBoardMeetingCharge() != null){
			valuatorBo.setBoardMeetingCharge(new BigDecimal(valuatorChargesForm.getBoardMeetingCharge()));
		}
		if(valuatorChargesForm.getMinimumScripts() != null && !valuatorChargesForm.getMinimumScripts().trim().isEmpty()){
			valuatorBo.setMinimumScripts(Integer.parseInt(valuatorChargesForm.getMinimumScripts()));
		}
		if(valuatorChargesForm.getMinimumprojectevaluationmajor() != null && !valuatorChargesForm.getMinimumprojectevaluationmajor().trim().isEmpty()){
			valuatorBo.setMinimumprojectevaluationmajor(new BigDecimal(valuatorChargesForm.getMinimumprojectevaluationmajor()));
		}
		if(valuatorChargesForm.getMinimumprojectevaluationminor() != null && !valuatorChargesForm.getMinimumprojectevaluationminor().trim().isEmpty()){
			valuatorBo.setMinimumprojectevaluationminor(new BigDecimal(valuatorChargesForm.getMinimumprojectevaluationminor()));
		}
		if(valuatorChargesForm.getMinimumreviewercharge() != null && !valuatorChargesForm.getMinimumreviewercharge().trim().isEmpty()){
			valuatorBo.setMinimumreviewercharge(new BigDecimal(valuatorChargesForm.getMinimumreviewercharge()));
		}
		if(valuatorChargesForm.getMinimumvaluatorcharge() != null && !valuatorChargesForm.getMinimumvaluatorcharge().trim().isEmpty()){
			valuatorBo.setMinimumvaluatorcharge(new BigDecimal(valuatorChargesForm.getMinimumvaluatorcharge()));
		}
		valuatorBo.setLastModifiedDate(new Date());
		valuatorBo.setModifiedBy(valuatorChargesForm.getUserId());
		valuatorBo.setIsActive(true);
		return valuatorBo;
	}

}
