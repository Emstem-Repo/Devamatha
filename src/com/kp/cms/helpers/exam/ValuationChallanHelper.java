package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.ExamRemunerationDetails;
import com.kp.cms.bo.exam.ExamValidationDetails;
import com.kp.cms.bo.exam.ExamValuationAnswerScript;
import com.kp.cms.bo.exam.ExamValuationRemuneration;
import com.kp.cms.bo.exam.ExamValuators;
import com.kp.cms.bo.exam.ValuatorChargesBo;
import com.kp.cms.forms.exam.ValuationChallanForm;
import com.kp.cms.to.exam.ValuationChallanDetailsTO;
import com.kp.cms.to.exam.ValuationChallanTO;
import com.kp.cms.transactions.exam.IValuationChallanTransaction;
import com.kp.cms.transactionsimpl.exam.ValuationChallanTxImpl;
import com.kp.cms.utilities.CommonUtil;

public class ValuationChallanHelper {
	/**
	 * Singleton object of NewSecuredMarksEntryHelper
	 */
	private static volatile ValuationChallanHelper helper = null;
	private static final Log log = LogFactory.getLog(ValuationChallanHelper.class);
	
	private ValuationChallanHelper() {
		
	}
	
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	/**
	 * return singleton object of newSecuredMarksEntryHandler.
	 * @return
	 */
	public static ValuationChallanHelper getInstance() {
		if (helper == null) {
			helper = new ValuationChallanHelper();
		}
		return helper;
	}
	IValuationChallanTransaction transaction = ValuationChallanTxImpl.getInstance();
	/**
	 * @param valuatorList
	 * @param type
	 * @param valuationChallanForm 
	 * @param totalScripts 
	 * @param totalScripts 
	 * @param totalScriptsAmount 
	 * @return
	 * @throws Exception 
	 */
	public List<ValuationChallanTO> ConvertBOTOTO(List<ExamValidationDetails> valuatorList, String type, ValuationChallanForm valuationChallanForm, HttpServletRequest request, int totalScripts, double totalScriptsAmount) throws Exception {
		List<ValuationChallanTO> tos = new ArrayList<ValuationChallanTO>();
		if(valuatorList != null){
			Iterator<ExamValidationDetails> iterator = valuatorList.iterator();
			while (iterator.hasNext()) {
				ExamValidationDetails examValidationDetails = (ExamValidationDetails) iterator.next();
				Set<ExamValuationAnswerScript> set = examValidationDetails.getAnswerScripts();
				boolean add = false;
				if(set != null){
					Iterator<ExamValuationAnswerScript> iterator2 = set.iterator();
					while (iterator2.hasNext()) {
						ExamValuationAnswerScript examValuationAnswerScript = (ExamValuationAnswerScript) iterator2.next();
						if(examValuationAnswerScript.getIsActive()){
							add=true;
						}
					}
				}
				if(add){
					if(examValidationDetails.getIsValuator().equalsIgnoreCase(type)){
						ValuationChallanTO to = new ValuationChallanTO();
						int charge=0;
						int count = 0; 
						if(examValidationDetails.getSubject() != null && examValidationDetails.getSubject().getName() != null){
							to.setSubjectName(examValidationDetails.getSubject().getName()+"("+examValidationDetails.getSubject().getCode()+")");
							to.setSubjectId(examValidationDetails.getSubject().getId());
							ValuatorChargesBo chargesBo=new ValuatorChargesBo();
							int programTypeId = transaction.getSubjectGroupForSubject(examValidationDetails.getSubject().getId());
							if(programTypeId !=0){
								chargesBo = transaction.getValuatorChargerPerScript(programTypeId);
							}
							if(type.equalsIgnoreCase("Valuator1") || type.equalsIgnoreCase("Valuator2")){
								if(chargesBo.getValuatorcharge() != null){
									charge = chargesBo.getValuatorcharge().intValue();
									if(examValidationDetails.getAnswerScripts() != null){
										Set<ExamValuationAnswerScript> answerScript = examValidationDetails.getAnswerScripts();
										Iterator<ExamValuationAnswerScript> iterator2 = answerScript.iterator();
										while (iterator2.hasNext()) {
											ExamValuationAnswerScript examValuationAnswerScript = (ExamValuationAnswerScript) iterator2.next();
											if((examValuationAnswerScript.getValidationDetailsId().getIsValuator().equalsIgnoreCase("Valuator1")|| examValuationAnswerScript.getValidationDetailsId().getIsValuator().equalsIgnoreCase("Valuator2"))&& examValuationAnswerScript.getNumberOfAnswerScripts() != 0){
												count = count + examValuationAnswerScript.getNumberOfAnswerScripts();
											}
										}
									}
								}
							}
							if(type.equalsIgnoreCase("Reviewer")){
								if(chargesBo.getReviewercharge() != null){
									charge = chargesBo.getReviewercharge().intValue();
									if(examValidationDetails.getAnswerScripts() != null){
										Set<ExamValuationAnswerScript> answerScript = examValidationDetails.getAnswerScripts();
										Iterator<ExamValuationAnswerScript> iterator2 = answerScript.iterator();
										while (iterator2.hasNext()) {
											ExamValuationAnswerScript examValuationAnswerScript = (ExamValuationAnswerScript) iterator2.next();
											if( examValuationAnswerScript.getValidationDetailsId().getIsValuator().equalsIgnoreCase("Reviewer") && examValuationAnswerScript.getNumberOfAnswerScripts() != 0){
												count = count + examValuationAnswerScript.getNumberOfAnswerScripts();
											}
										}
									}
								}
							}
							if(type.equalsIgnoreCase("Project Minor")){
								if(chargesBo.getProjectevaluationminor() != null){
									charge = chargesBo.getProjectevaluationminor().intValue();
									if(examValidationDetails.getAnswerScripts() != null){
										Set<ExamValuationAnswerScript> answerScript = examValidationDetails.getAnswerScripts();
										Iterator<ExamValuationAnswerScript> iterator2 = answerScript.iterator();
										while (iterator2.hasNext()) {
											ExamValuationAnswerScript examValuationAnswerScript = (ExamValuationAnswerScript) iterator2.next();
											if( examValuationAnswerScript.getValidationDetailsId().getIsValuator().equalsIgnoreCase("Project Minor") && examValuationAnswerScript.getNumberOfAnswerScripts() != 0){
												count = count + examValuationAnswerScript.getNumberOfAnswerScripts();
											}
										}
									}
								}
							}
							if(type.equalsIgnoreCase("Project Major")){
								if(chargesBo.getProjectevaluationmajor() != null){
									charge = chargesBo.getProjectevaluationmajor().intValue();
									if(examValidationDetails.getAnswerScripts() != null){
										Set<ExamValuationAnswerScript> answerScript = examValidationDetails.getAnswerScripts();
										Iterator<ExamValuationAnswerScript> iterator2 = answerScript.iterator();
										while (iterator2.hasNext()) {
											ExamValuationAnswerScript examValuationAnswerScript = (ExamValuationAnswerScript) iterator2.next();
											if( examValuationAnswerScript.getValidationDetailsId().getIsValuator().equalsIgnoreCase("Project Major") && examValuationAnswerScript.getNumberOfAnswerScripts() != 0){
												count = count + examValuationAnswerScript.getNumberOfAnswerScripts();
											}
										}
									}
								}
							}
						}
						
						double totalAmount = count * charge;
						to.setTotalAmount(String.valueOf(totalAmount));
						to.setAnswerScripts(count);
						to.setRate(String.valueOf(charge));
						totalScripts = totalScripts + count;
						totalScriptsAmount = totalScriptsAmount + totalAmount;
						tos.add(to);
						valuationChallanForm.setViewFields(true);
					}
				}
			}
			request.setAttribute("TOTALVAL", totalScripts);
			request.setAttribute("TOTALAMOUNT", totalScriptsAmount);
		}
		return tos;
	}
	/**
	 * @param valuationChallanForm
	 * @throws Exception
	 */
	public ExamValuationRemuneration convertFormTOBO(ValuationChallanForm valuationChallanForm) throws Exception{
		
		ExamValuationRemuneration remuneration = new ExamValuationRemuneration();
		if(valuationChallanForm.getEmployeeId() != null && !valuationChallanForm.getEmployeeId().isEmpty() && !valuationChallanForm.getEmployeeId().equalsIgnoreCase("Other")){
			Users users = new Users();
			users.setId(Integer.parseInt(valuationChallanForm.getEmployeeId()));
			remuneration.setUsers(users);
		}else if(valuationChallanForm.getOtherEmpId() != null && !valuationChallanForm.getOtherEmpId().isEmpty()){
			ExamValuators valuators = new ExamValuators();
			valuators.setId(Integer.parseInt(valuationChallanForm.getOtherEmpId()));
			remuneration.setExamValuators(valuators);
		}
		if(valuationChallanForm.getStartDate() != null && !valuationChallanForm.getStartDate().trim().isEmpty()){
			remuneration.setStartDate(CommonUtil.ConvertStringToSQLDate(valuationChallanForm.getStartDate()));
		}
		if(valuationChallanForm.getEndDate() != null && !valuationChallanForm.getEndDate().trim().isEmpty()){
			remuneration.setEndDate(CommonUtil.ConvertStringToSQLDate(valuationChallanForm.getEndDate()));
		}
		if(valuationChallanForm.getTotalBoardMeetings() != null && !valuationChallanForm.getTotalBoardMeetings().trim().isEmpty()){
			remuneration.setBoardMeetings(Integer.parseInt(valuationChallanForm.getTotalBoardMeetings()));
		}
		if(valuationChallanForm.getTotalNoOfConveyance() != null && !valuationChallanForm.getTotalNoOfConveyance().trim().isEmpty()){
			remuneration.setConveyanceCharges(Integer.parseInt(valuationChallanForm.getTotalNoOfConveyance()));
		}
		remuneration.setAnyOther(valuationChallanForm.getAnyOther());
		if(valuationChallanForm.getOtherCost() != null && !valuationChallanForm.getOtherCost().trim().isEmpty()){
			remuneration.setAnyOtherCost(new java.math.BigDecimal(valuationChallanForm.getOtherCost()));
		}
		if(valuationChallanForm.getTa() != null && !valuationChallanForm.getTa().trim().isEmpty()){
			remuneration.setTa(new java.math.BigDecimal(valuationChallanForm.getTa()));
		}
		if(valuationChallanForm.getDa() != null && !valuationChallanForm.getDa().trim().isEmpty()){
			remuneration.setDa(new java.math.BigDecimal(valuationChallanForm.getDa()));
		}
		remuneration.setIsPaid(false);
		remuneration.setCreatedBy(valuationChallanForm.getUserId());
		remuneration.setCreatedDate(new Date());
		remuneration.setLastModifiedDate(new Date());
		remuneration.setModifiedBy(valuationChallanForm.getUserId());
		remuneration.setIsActive(true);
		
		
		Set<ExamRemunerationDetails> examRemunerationDetails = new HashSet<ExamRemunerationDetails>();
		//code commented by chandra
		/*Map<String,Map<Integer, ValuationChallanTO>> map = valuationChallanForm.getMap();
		if(map != null && !map.isEmpty()){
			Iterator<Entry<String, Map<Integer, ValuationChallanTO>>> iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Map<Integer, ValuationChallanTO>> entry = (Map.Entry<String, Map<Integer, ValuationChallanTO>>) iterator.next();
				 Map<Integer, ValuationChallanTO> subMap = entry.getValue();
				 if(subMap != null && !subMap.isEmpty()){
					 Iterator<Entry<Integer, ValuationChallanTO>> iterator2 = subMap.entrySet().iterator();
					 while (iterator2.hasNext()) {
						Map.Entry<Integer, ValuationChallanTO> entry2 = (Map.Entry<Integer, ValuationChallanTO>) iterator2.next();
						ValuationChallanTO valuationChallanTO = entry2.getValue();
						ExamRemunerationDetails bo = new ExamRemunerationDetails();
						Subject subject = new Subject();
						subject.setId(valuationChallanTO.getSubjectId());
						bo.setSubject(subject);
						bo.setTotalScripts(valuationChallanTO.getAnswerScripts());
						if((entry.getKey().equalsIgnoreCase("Valuator1"))|| (entry.getKey().equalsIgnoreCase("Valuator2"))){
							bo.setType("Valuator");
						}else{
							bo.setType(entry.getKey());
						}
						
						bo.setCreatedBy(valuationChallanForm.getUserId());
						bo.setCreatedDate(new Date());
						bo.setLastModifiedDate(new Date());
						bo.setModifiedBy(valuationChallanForm.getUserId());
						bo.setIsActive(true);
						examRemunerationDetails.add(bo);
					}
				 }
			}
		}*/
		
		// code added by chandra
		List<ValuationChallanDetailsTO> valuationDetailsList=valuationChallanForm.getValuationDetailsList();
		if(valuationDetailsList!=null && !valuationDetailsList.isEmpty()){
			Iterator<ValuationChallanDetailsTO> iterator1 = valuationDetailsList.iterator();
			while (iterator1.hasNext()) {
				ValuationChallanDetailsTO valuationChallanTO = (ValuationChallanDetailsTO)iterator1.next();
				ExamRemunerationDetails bo = new ExamRemunerationDetails();
				Subject subject = new Subject();
				subject.setId(valuationChallanTO.getSubjectId());
				bo.setSubject(subject);
				bo.setTotalScripts(valuationChallanTO.getAnswerScripts());
				// /*code added by chandra
				if((valuationChallanTO.getValuatorType().equalsIgnoreCase("Valuator1"))|| (valuationChallanTO.getValuatorType().equalsIgnoreCase("Valuator2"))){
					bo.setType("Valuator");
				}else{
					bo.setType(valuationChallanTO.getValuatorType());
				}
				// */
				bo.setCreatedBy(valuationChallanForm.getUserId());
				bo.setCreatedDate(new Date());
				bo.setLastModifiedDate(new Date());
				bo.setModifiedBy(valuationChallanForm.getUserId());
				bo.setIsActive(true);
			if(valuationChallanTO.getChecked()==null || !valuationChallanTO.getChecked().equalsIgnoreCase("on")){
				bo.setIsBoardMeetingApplicable(false);
			}else{
				bo.setIsBoardMeetingApplicable(true);
			}
			examRemunerationDetails.add(bo);
		}
		}
		//
		remuneration.setExamRemunerationDetails(examRemunerationDetails);
		return remuneration;
		
	}
}
