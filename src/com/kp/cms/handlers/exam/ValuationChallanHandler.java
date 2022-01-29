package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.icu.util.StringTokenizer;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.ExamRemunerationDetails;
import com.kp.cms.bo.exam.ExamValuationAnswerScript;
import com.kp.cms.bo.exam.ExamValuationRemuneration;
import com.kp.cms.bo.exam.ExamValuators;
import com.kp.cms.bo.exam.ValuatorChargesBo;
import com.kp.cms.bo.exam.ValuatorMeetingChargesBo;
import com.kp.cms.forms.exam.ValuationChallanForm;
import com.kp.cms.helpers.exam.ValuationChallanHelper;
import com.kp.cms.to.exam.ExamValuationStatusTO;
import com.kp.cms.to.exam.ValuationChallanDetailsTO;
import com.kp.cms.to.exam.ValuationChallanTO;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.exam.IValuationChallanTransaction;
import com.kp.cms.transactions.exam.IValuationStatisticsTransaction;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.ValuationChallanTxImpl;
import com.kp.cms.transactionsimpl.exam.ValuationStatisticsTxImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class ValuationChallanHandler {
	/**
	 * Singleton object of NewSecuredMarksEntryHandler
	 */
	private static volatile ValuationChallanHandler handler = null;
	private static final Log log = LogFactory.getLog(ValuationChallanHandler.class);
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	private ValuationChallanHandler() {
		
	}
	/**
	 * return singleton object of newSecuredMarksEntryHandler.
	 * @return
	 */
	public static ValuationChallanHandler getInstance() {
		if (handler == null) {
			handler = new ValuationChallanHandler();
		}
		return handler;
	}
	IValuationChallanTransaction transaction = ValuationChallanTxImpl.getInstance();
	
	public void searchValuationDetails(ValuationChallanForm valuationChallanForm)  throws Exception{ 
		INewExamMarksEntryTransaction transaction1=NewExamMarksEntryTransactionImpl.getInstance();

		String query="from ExamValuationAnswerScript e where e.isActive=1 and e.challanGenerated=0";
		if(valuationChallanForm.getEmployeeId() != null && !valuationChallanForm.getEmployeeId().isEmpty() && !valuationChallanForm.getEmployeeId().equalsIgnoreCase("Other")){
			query = query + " and e.validationDetailsId.users.id="+valuationChallanForm.getEmployeeId();
		}
		if(valuationChallanForm.getOtherEmpId() != null && !valuationChallanForm.getOtherEmpId().isEmpty()){
			query = query +" and e.validationDetailsId.examValuators.id=" +valuationChallanForm.getOtherEmpId();
		}
		if(valuationChallanForm.getAcademicYear() != null && !valuationChallanForm.getAcademicYear().isEmpty()){
			query = query +" and e.validationDetailsId.exam.academicYear=" +valuationChallanForm.getAcademicYear();
		}
		
		List<ExamValuationAnswerScript> list=transaction1.getDataForQuery(query);
		valuationChallanForm.setAnswerScripts(list);
		if(list!=null && !list.isEmpty()){
			Iterator<ExamValuationAnswerScript> itr=list.iterator();
			ExamValuationAnswerScript examValuationAnswerScript=null;
			Map<String,Map<Integer, ValuationChallanTO>> map=new HashMap<String,Map<Integer, ValuationChallanTO>>();
			Map<Integer, ValuationChallanTO> subMap = new HashMap<Integer,  ValuationChallanTO>();
			Map<Integer,List<Integer>> examMap=new HashMap<Integer,List<Integer>>();
			List<Integer> sublectList=new ArrayList<Integer>();
			ValuationChallanTO to = null;
			int totalScripts=0;
			int totalAmount=0;
			int totalBoardMeetings=0;
			double totalBoardMeetingCharges =0.0;
			List<Integer> subjectList = new ArrayList<Integer>();
			while (itr.hasNext()) {
				examValuationAnswerScript =itr .next();
				if(examValuationAnswerScript.getValidationDetailsId()!=null){
					int amount=0;
					if(examValuationAnswerScript.getValidationDetailsId() != null && examValuationAnswerScript.getValidationDetailsId().getSubject() != null && examValuationAnswerScript.getValidationDetailsId().getSubject().getName() != null && examValuationAnswerScript.getValidationDetailsId().getSubject().getCode() != null){
						ValuatorChargesBo chargesBo=new ValuatorChargesBo();
						int programTypeId = transaction.getSubjectGroupForSubject(examValuationAnswerScript.getValidationDetailsId().getSubject().getId());
						if(programTypeId !=0){
							chargesBo = transaction.getValuatorChargerPerScript(programTypeId);
						}
						valuationChallanForm.setBoardMeeetingRate(chargesBo.getBoardMeetingCharge().toString());
						if(map.containsKey(examValuationAnswerScript.getValidationDetailsId().getIsValuator())){
							subMap = map.remove(examValuationAnswerScript.getValidationDetailsId().getIsValuator());
						}else{
							subMap = new HashMap<Integer, ValuationChallanTO>();
						}
						if(examValuationAnswerScript.getValidationDetailsId() != null && examValuationAnswerScript.getValidationDetailsId().getSubject() != null && examValuationAnswerScript.getValidationDetailsId().getSubject().getId() != 0){
							if(subMap.containsKey(examValuationAnswerScript.getValidationDetailsId().getSubject().getId())){
								to = subMap.remove(examValuationAnswerScript.getValidationDetailsId().getSubject().getId());
							}else{
								to = new ValuationChallanTO();
							}
						}
						// /*code added by chandra
						if(examMap.containsKey(examValuationAnswerScript.getValidationDetailsId().getExam().getId())){
							sublectList = examMap.remove(examValuationAnswerScript.getValidationDetailsId().getExam().getId());
						}else{
							sublectList = new ArrayList<Integer>();
						}
						sublectList.add(examValuationAnswerScript.getValidationDetailsId().getSubject().getId());
						examMap.put(examValuationAnswerScript.getValidationDetailsId().getExam().getId(), sublectList);
						// */code added by chandra
						if(!subjectList.contains(examValuationAnswerScript.getValidationDetailsId().getSubject().getId())){
							totalBoardMeetings = totalBoardMeetings +1;
							if(chargesBo.getBoardMeetingCharge() != null ){
								totalBoardMeetingCharges = totalBoardMeetingCharges + chargesBo.getBoardMeetingCharge().intValue();
							}
						}
						to.setBoardMeetingChargePerSub(chargesBo.getBoardMeetingCharge().intValue());
						subjectList.add(examValuationAnswerScript.getValidationDetailsId().getSubject().getId());
						if(examValuationAnswerScript.getNumberOfAnswerScripts()>0){
							to.setAnswerScripts(to.getAnswerScripts()+examValuationAnswerScript.getNumberOfAnswerScripts());
							totalScripts = totalScripts + examValuationAnswerScript.getNumberOfAnswerScripts();
						}	
						to.setSubjectName(examValuationAnswerScript.getValidationDetailsId().getSubject().getName()+"("+examValuationAnswerScript.getValidationDetailsId().getSubject().getCode()+")");
						to.setSubjectId(examValuationAnswerScript.getValidationDetailsId().getSubject().getId());
						if(examValuationAnswerScript.getValidationDetailsId().getIsValuator().equalsIgnoreCase("Valuator1") || examValuationAnswerScript.getValidationDetailsId().getIsValuator().equalsIgnoreCase("Valuator2") || examValuationAnswerScript.getValidationDetailsId().getIsValuator().equalsIgnoreCase("Re-Valuator")){
							if(chargesBo.getMinimumScripts() != null && chargesBo.getMinimumScripts()>0 && examValuationAnswerScript.getNumberOfAnswerScripts()<=chargesBo.getMinimumScripts() && chargesBo.getMinimumvaluatorcharge() != null){
								amount = chargesBo.getMinimumvaluatorcharge().intValue();
								to.setTotalAmount(String.valueOf(chargesBo.getMinimumvaluatorcharge().intValue()));
								to.setRate(String.valueOf(chargesBo.getMinimumvaluatorcharge().intValue()));
							}else if(chargesBo.getValuatorcharge() != null){
								amount = examValuationAnswerScript.getNumberOfAnswerScripts() * chargesBo.getValuatorcharge().intValue();
								to.setTotalAmount(String.valueOf(to.getAnswerScripts()*chargesBo.getValuatorcharge().intValue()));
								to.setRate(String.valueOf(chargesBo.getValuatorcharge().intValue()));
							}
						}
						if(examValuationAnswerScript.getValidationDetailsId().getIsValuator().equalsIgnoreCase("Reviewer")){
							if(chargesBo.getMinimumScripts() != null && chargesBo.getMinimumScripts()>0 && examValuationAnswerScript.getNumberOfAnswerScripts()<=chargesBo.getMinimumScripts() && chargesBo.getMinimumreviewercharge() != null){
								amount = chargesBo.getMinimumreviewercharge().intValue();
								to.setTotalAmount(String.valueOf(chargesBo.getMinimumreviewercharge().intValue()));
								to.setRate(String.valueOf(chargesBo.getMinimumreviewercharge().intValue()));
							}else if(chargesBo.getReviewercharge() != null){
								amount = examValuationAnswerScript.getNumberOfAnswerScripts() * chargesBo.getReviewercharge().intValue();
								to.setTotalAmount(String.valueOf(to.getAnswerScripts()*chargesBo.getReviewercharge().intValue()));
								to.setRate(String.valueOf(chargesBo.getReviewercharge().intValue()));
							}
						}
						if(examValuationAnswerScript.getValidationDetailsId().getIsValuator().equalsIgnoreCase("Project Minor")){
							if(chargesBo.getMinimumScripts() != null && chargesBo.getMinimumScripts()>0 && examValuationAnswerScript.getNumberOfAnswerScripts()<=chargesBo.getMinimumScripts() && chargesBo.getMinimumprojectevaluationminor() != null){
								amount = chargesBo.getMinimumprojectevaluationminor().intValue();
								to.setTotalAmount(String.valueOf(chargesBo.getMinimumprojectevaluationminor().intValue()));
								to.setRate(String.valueOf(chargesBo.getMinimumprojectevaluationminor().intValue()));
							}else if(chargesBo.getProjectevaluationminor() != null){
								amount = examValuationAnswerScript.getNumberOfAnswerScripts() * chargesBo.getProjectevaluationminor().intValue();
								to.setTotalAmount(String.valueOf(to.getAnswerScripts()*chargesBo.getProjectevaluationminor().intValue()));
								to.setRate(String.valueOf(chargesBo.getProjectevaluationminor().intValue()));
							}
						}
						if(examValuationAnswerScript.getValidationDetailsId().getIsValuator().equalsIgnoreCase("Project Major")){
							if(chargesBo.getMinimumScripts() != null && chargesBo.getMinimumScripts()>0 && examValuationAnswerScript.getNumberOfAnswerScripts()<=chargesBo.getMinimumScripts() && chargesBo.getMinimumprojectevaluationmajor() != null){
								amount = chargesBo.getMinimumprojectevaluationmajor().intValue();
								to.setTotalAmount(String.valueOf(chargesBo.getMinimumprojectevaluationmajor().intValue()));
								to.setRate(String.valueOf(chargesBo.getMinimumprojectevaluationmajor().intValue()));
							}else if(chargesBo.getProjectevaluationmajor() != null){
								amount = examValuationAnswerScript.getNumberOfAnswerScripts() * chargesBo.getProjectevaluationmajor().intValue();
								to.setTotalAmount(String.valueOf(to.getAnswerScripts()*chargesBo.getProjectevaluationmajor().intValue()));
								to.setRate(String.valueOf(chargesBo.getProjectevaluationmajor().intValue()));
							}
						}
						to.setIsBoardMeetingApplicable("on");
						subMap.put(examValuationAnswerScript.getValidationDetailsId().getSubject().getId(), to);
						map.put(examValuationAnswerScript.getValidationDetailsId().getIsValuator(), subMap);
						valuationChallanForm.setViewFields(true);
						totalAmount = totalAmount + amount;
					}
				}
			}
			valuationChallanForm.setTotalScriptsAmount(String.valueOf(totalAmount));
			valuationChallanForm.setTotalScripts(totalScripts);
			valuationChallanForm.setTotalBoardMeetings(String.valueOf(totalBoardMeetings));
			ValuatorMeetingChargesBo meetingChargesBo =  transaction.getValuatorMeetingCharges();
			if(meetingChargesBo != null){
				valuationChallanForm.setTotalBoardMeetingCharge(String.valueOf(totalBoardMeetingCharges));
				int totalConveyanceCharge = 0;
				if(valuationChallanForm.getOtherEmpId() != null && !valuationChallanForm.getOtherEmpId().isEmpty()){
					totalConveyanceCharge = totalScripts / 20;
					int conveny = totalScripts % 20;
					if(conveny >=10){
						totalConveyanceCharge = totalConveyanceCharge +1;
					}
					valuationChallanForm.setTotalNoOfConveyance(String.valueOf(totalConveyanceCharge));
					totalConveyanceCharge = totalConveyanceCharge * meetingChargesBo.getConveyanceCharge().intValue();
					valuationChallanForm.setTotalConveyanceCharge(String.valueOf(totalConveyanceCharge));
				}else{
					boolean isGuest=transaction.getGuestId(valuationChallanForm.getEmployeeId());
					if(isGuest){
						totalConveyanceCharge = totalScripts / 20;
						int conveny = totalScripts % 20;
						if(conveny >=10){
							totalConveyanceCharge = totalConveyanceCharge +1;
						}
						valuationChallanForm.setTotalNoOfConveyance(String.valueOf(totalConveyanceCharge));
						totalConveyanceCharge = totalConveyanceCharge * meetingChargesBo.getConveyanceCharge().intValue();
						valuationChallanForm.setTotalConveyanceCharge(String.valueOf(totalConveyanceCharge));
					}
				}
				valuationChallanForm.setConveyanceCharge(meetingChargesBo.getConveyanceCharge().toString());
				Double grandTotal = 0.0;
				grandTotal = grandTotal + totalAmount;
				grandTotal = grandTotal + totalConveyanceCharge ;
				grandTotal = grandTotal + totalBoardMeetingCharges;
				valuationChallanForm.setGrandTotal(String.valueOf(grandTotal));
			}
			valuationChallanForm.setMap(map);
			valuationChallanForm.setExamMap(examMap);
		// /* code added by chandra	
			
			List<ValuationChallanDetailsTO> valuationDetailsList=new ArrayList<ValuationChallanDetailsTO>();
			Map<String,Map<Integer, ValuationChallanTO>> map1 = valuationChallanForm.getMap();
			if(map1 != null && !map1.isEmpty()){
				Iterator<Entry<String, Map<Integer, ValuationChallanTO>>> iterator = map1.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<String, Map<Integer, ValuationChallanTO>> entry = (Map.Entry<String, Map<Integer, ValuationChallanTO>>) iterator.next();
					 Map<Integer, ValuationChallanTO> subMap1 = entry.getValue();
					 int count=0;
					 if(subMap1 != null && !subMap1.isEmpty()){
						 Iterator<Entry<Integer, ValuationChallanTO>> iterator2 = subMap1.entrySet().iterator();
						 while (iterator2.hasNext()) {
							Map.Entry<Integer, ValuationChallanTO> entry2 = (Map.Entry<Integer, ValuationChallanTO>) iterator2.next();
							ValuationChallanTO valuationChallanTO = entry2.getValue();
							ValuationChallanDetailsTO valuationDetailTo=new ValuationChallanDetailsTO();
							valuationDetailTo.setValuatorType(entry.getKey());
							valuationDetailTo.setValuatorsTypeCount(count);
							valuationDetailTo.setSubjectId(valuationChallanTO.getSubjectId());
							valuationDetailTo.setSubjectName(valuationChallanTO.getSubjectName());
							valuationDetailTo.setAnswerScripts(valuationChallanTO.getAnswerScripts());
							valuationDetailTo.setTotalAmount(valuationChallanTO.getTotalAmount());
							valuationDetailTo.setIsBoardMeetingApplicable(valuationChallanTO.getIsBoardMeetingApplicable());
							valuationDetailTo.setBoardMeetingChargePerSub(valuationChallanTO.getBoardMeetingChargePerSub());
							valuationDetailsList.add(valuationDetailTo);
							count ++;
						 }
					 }
				}
			}
			valuationChallanForm.setValuationDetailsList(valuationDetailsList);
		// */ code added by achandra	
		}
	
	}
	
	/**
	 * @param session
	 * @throws Exception
	 */
	public void getEmployeeList(HttpSession session) throws Exception{
		transaction.getEmployeeMap(session);
	}
	/**
	 * @param valuationChallanForm
	 * @throws Exception
	 */
	public boolean saveDetails(ValuationChallanForm valuationChallanForm) throws Exception{
		ExamValuationRemuneration remuneration= ValuationChallanHelper.getInstance().convertFormTOBO(valuationChallanForm);
		boolean save = transaction.saveDetails(remuneration, valuationChallanForm);
		getDetailsForChallanPrint(valuationChallanForm,remuneration);
		return save;
	}
	/**
	 * @param valuationChallanForm
	 * @throws Exception
	 */
	public void searchValuationDetailsForInput( ValuationChallanForm valuationChallanForm)  throws Exception{ 
		INewExamMarksEntryTransaction transaction1=NewExamMarksEntryTransactionImpl.getInstance();
		String hqlQuery = "from ExamRemunerationDetails e where e.isActive=1 and e.remuneration.isActive=1";
		if(valuationChallanForm.getEmployeeId() != null && !valuationChallanForm.getEmployeeId().isEmpty() && !valuationChallanForm.getEmployeeId().equalsIgnoreCase("Other")){
			hqlQuery = hqlQuery + " and e.remuneration.users.id="+valuationChallanForm.getEmployeeId();
		}
		if(valuationChallanForm.getOtherEmpId() != null && !valuationChallanForm.getOtherEmpId().isEmpty()){
			hqlQuery = hqlQuery +" and e.remuneration.examValuators.id=" +valuationChallanForm.getOtherEmpId();
		}
		List<ExamRemunerationDetails> list1=transaction1.getDataForQuery(hqlQuery);
		if(list1 != null && !list1.isEmpty()){
			Iterator<ExamRemunerationDetails> itr=list1.iterator();
			ExamRemunerationDetails examRemunerationDetails=null;
			Map<String,Map<Integer, ValuationChallanTO>> map=new HashMap<String,Map<Integer, ValuationChallanTO>>();
			Map<Integer, ValuationChallanTO> subMap = new HashMap<Integer, ValuationChallanTO>();
			List<Integer> subjectList = new ArrayList<Integer>();
			ValuationChallanTO to = null;
			int totalScripts=0;
			int totalAmount=0;
			int totalBoardMeetings=0;
			double totalBoardMeetingCharges =0.0;
			while (itr.hasNext()) {
				examRemunerationDetails =itr .next();
				if(examRemunerationDetails.getRemuneration()!=null){
					
					int amount=0;
					if(examRemunerationDetails.getSubject() != null && examRemunerationDetails.getSubject().getName() != null && examRemunerationDetails.getSubject().getCode() != null){
						ValuatorChargesBo chargesBo=new ValuatorChargesBo();
						int programTypeId = transaction.getSubjectGroupForSubject(examRemunerationDetails.getSubject().getId());
						if(programTypeId !=0){
							chargesBo = transaction.getValuatorChargerPerScript(programTypeId);
						}
						valuationChallanForm.setBoardMeeetingRate(chargesBo.getBoardMeetingCharge().toString());
						if(map.containsKey(examRemunerationDetails.getType())){
							subMap = map.remove(examRemunerationDetails.getType());
						}else{
							subMap = new HashMap<Integer, ValuationChallanTO>();
						}
						if(examRemunerationDetails.getSubject() != null && examRemunerationDetails.getSubject().getId() != 0){
							if(subMap.containsKey(examRemunerationDetails.getSubject().getId())){
								to = subMap.remove(examRemunerationDetails.getSubject().getId());
							}else{
								to = new ValuationChallanTO();
							}
						}
						if(!subjectList.contains(examRemunerationDetails.getSubject().getId())){
							totalBoardMeetings = totalBoardMeetings +1;
							if(chargesBo.getBoardMeetingCharge() != null ){
								totalBoardMeetingCharges = totalBoardMeetingCharges + chargesBo.getBoardMeetingCharge().intValue();
							}
						}
						subjectList.add(examRemunerationDetails.getSubject().getId());
						to.setAnswerScripts(to.getAnswerScripts()+examRemunerationDetails.getTotalScripts());
						totalScripts = totalScripts + examRemunerationDetails.getTotalScripts();
						to.setSubjectName(examRemunerationDetails.getSubject().getName()+"("+examRemunerationDetails.getSubject().getCode()+")");
						to.setSubjectId(examRemunerationDetails.getSubject().getId());
						if(examRemunerationDetails.getType().equalsIgnoreCase("Valuator") || examRemunerationDetails.getType().equalsIgnoreCase("Re-Valuator")){
							if(chargesBo.getValuatorcharge() != null){
								amount = examRemunerationDetails.getTotalScripts() * chargesBo.getValuatorcharge().intValue();
								to.setTotalAmount(String.valueOf(to.getAnswerScripts()*chargesBo.getValuatorcharge().intValue()));
								to.setRate(String.valueOf(chargesBo.getValuatorcharge().intValue()));
							}
						}
						if(examRemunerationDetails.getType().equalsIgnoreCase("Reviewer")){
							if(chargesBo.getReviewercharge() != null){
								amount = examRemunerationDetails.getTotalScripts() * chargesBo.getReviewercharge().intValue();
								to.setTotalAmount(String.valueOf(to.getAnswerScripts()*chargesBo.getReviewercharge().intValue()));
								to.setRate(String.valueOf(chargesBo.getReviewercharge().intValue()));
							}
						}
						if(examRemunerationDetails.getType().equalsIgnoreCase("Project Minor")){
							if(chargesBo.getProjectevaluationminor() != null){
								amount = examRemunerationDetails.getTotalScripts() * chargesBo.getProjectevaluationminor().intValue();
								to.setTotalAmount(String.valueOf(to.getAnswerScripts()*chargesBo.getProjectevaluationminor().intValue()));
								to.setRate(String.valueOf(chargesBo.getProjectevaluationminor().intValue()));
							}
						}
						if(examRemunerationDetails.getType().equalsIgnoreCase("Project Major")){
							if(chargesBo.getProjectevaluationmajor() != null){
								amount = examRemunerationDetails.getTotalScripts() * chargesBo.getProjectevaluationmajor().intValue();
								to.setTotalAmount(String.valueOf(to.getAnswerScripts()*chargesBo.getProjectevaluationmajor().intValue()));
								to.setRate(String.valueOf(chargesBo.getProjectevaluationmajor().intValue()));
							}
						}
						subMap.put(examRemunerationDetails.getSubject().getId(), to);   
						map.put(examRemunerationDetails.getType(), subMap);
						valuationChallanForm.setViewFields(true);
						totalAmount = totalAmount + amount;
					}
				}
				if(examRemunerationDetails.getRemuneration() != null){
					valuationChallanForm.setTotalBoardMeetings(String.valueOf(examRemunerationDetails.getRemuneration().getBoardMeetings()));
					valuationChallanForm.setTotalNoOfConveyance(String.valueOf(examRemunerationDetails.getRemuneration().getConveyanceCharges()));
					totalBoardMeetings = examRemunerationDetails.getRemuneration().getBoardMeetings();
				}
				if(examRemunerationDetails.getRemuneration() != null && examRemunerationDetails.getRemuneration().getDa() != null){
					valuationChallanForm.setDa(String.valueOf(examRemunerationDetails.getRemuneration().getDa()));
				}
				if(examRemunerationDetails.getRemuneration() != null && examRemunerationDetails.getRemuneration().getTa() != null){
					valuationChallanForm.setTa(String.valueOf(examRemunerationDetails.getRemuneration().getTa()));
				}
				if(examRemunerationDetails.getRemuneration() != null && examRemunerationDetails.getRemuneration().getAnyOther() != null){
					valuationChallanForm.setAnyOther(examRemunerationDetails.getRemuneration().getAnyOther());
				}
				if(examRemunerationDetails.getRemuneration() != null && examRemunerationDetails.getRemuneration().getAnyOtherCost() != null){
					valuationChallanForm.setOtherCost(String.valueOf(examRemunerationDetails.getRemuneration().getAnyOtherCost()));
				}
			}
			valuationChallanForm.setTotalScriptsAmount(String.valueOf(totalAmount));
			valuationChallanForm.setTotalScripts(totalScripts);
			valuationChallanForm.setTotalBoardMeetings(String.valueOf(totalBoardMeetings));
			ValuatorMeetingChargesBo meetingChargesBo =  transaction.getValuatorMeetingCharges();
			if(meetingChargesBo != null){
				valuationChallanForm.setTotalBoardMeetingCharge(String.valueOf(totalBoardMeetingCharges));
				int totalConveyanceCharge = 0;
				if(valuationChallanForm.getOtherEmpId() != null && !valuationChallanForm.getOtherEmpId().isEmpty()){
					if(valuationChallanForm.getTotalNoOfConveyance() != null && !valuationChallanForm.getTotalNoOfConveyance().trim().isEmpty()){
						totalConveyanceCharge = Integer.parseInt(valuationChallanForm.getTotalNoOfConveyance());
						totalConveyanceCharge = totalConveyanceCharge * meetingChargesBo.getConveyanceCharge().intValue();
					}
				}
				else
				{
					if(valuationChallanForm.getTotalNoOfConveyance() != null && !valuationChallanForm.getTotalNoOfConveyance().trim().isEmpty()){
						totalConveyanceCharge = Integer.parseInt(valuationChallanForm.getTotalNoOfConveyance());
						totalConveyanceCharge = totalConveyanceCharge * meetingChargesBo.getConveyanceCharge().intValue();
					}	
				}
				valuationChallanForm.setTotalConveyanceCharge(String.valueOf(totalConveyanceCharge));
				valuationChallanForm.setConveyanceCharge(meetingChargesBo.getConveyanceCharge().toString());
				Double grandTotal = 0.0;
				grandTotal = grandTotal + totalAmount;
				grandTotal = grandTotal + totalConveyanceCharge ;
				grandTotal = grandTotal + totalBoardMeetingCharges;
				if(valuationChallanForm.getTa() != null && !valuationChallanForm.getTa().trim().isEmpty()){
					grandTotal = grandTotal + Integer.parseInt(valuationChallanForm.getTa());
				}
				if(valuationChallanForm.getDa() != null && !valuationChallanForm.getDa().trim().isEmpty()){
					grandTotal = grandTotal + Integer.parseInt(valuationChallanForm.getDa());
				}
				if(valuationChallanForm.getOtherCost() != null && !valuationChallanForm.getOtherCost().trim().isEmpty()){
					grandTotal = grandTotal + Integer.parseInt(valuationChallanForm.getOtherCost());
				}
				valuationChallanForm.setGrandTotal(String.valueOf(grandTotal));
			}
			valuationChallanForm.setMap(map);
			valuationChallanForm.setRePrintDisplay(true);
		}else{
			String query="from ExamValuationAnswerScript e where e.isActive=1 ";
			if(valuationChallanForm.getEmployeeId() != null && !valuationChallanForm.getEmployeeId().isEmpty() && !valuationChallanForm.getEmployeeId().equalsIgnoreCase("Other")){
				query = query + " and e.validationDetailsId.users.id="+valuationChallanForm.getEmployeeId();
			}
			if(valuationChallanForm.getOtherEmpId() != null && !valuationChallanForm.getOtherEmpId().isEmpty()){
				query = query +"and e.validationDetailsId.examValuators.id=" +valuationChallanForm.getOtherEmpId();
			}
			if(valuationChallanForm.getExamId() != null && !valuationChallanForm.getExamId().isEmpty()){
				query = query +"and e.validationDetailsId.exam.id=" +valuationChallanForm.getExamId();
			}
			if(valuationChallanForm.getStartDate() != null && !valuationChallanForm.getStartDate().isEmpty() && valuationChallanForm.getEndDate() != null && !valuationChallanForm.getEndDate().isEmpty()){
				query = query + " and e.issueDate between '"+CommonUtil.ConvertStringToSQLDate(valuationChallanForm.getStartDate())+"' and '"+CommonUtil.ConvertStringToSQLDate(valuationChallanForm.getEndDate())+"'";
			}
			
			List<ExamValuationAnswerScript> list=transaction1.getDataForQuery(query);
			if(list!=null && !list.isEmpty()){
				Iterator<ExamValuationAnswerScript> itr=list.iterator();
				ExamValuationAnswerScript examValuationAnswerScript=null;
				Map<String,Map<Integer, ValuationChallanTO>> map=new HashMap<String,Map<Integer, ValuationChallanTO>>();
				Map<Integer, ValuationChallanTO> subMap = new HashMap<Integer,  ValuationChallanTO>();
				ValuationChallanTO to = null;
				int totalScripts=0;
				int totalAmount=0;
				int totalBoardMeetings=0;
				double totalBoardMeetingCharges =0.0;
				List<Integer> subjectList = new ArrayList<Integer>();
				while (itr.hasNext()) {
					examValuationAnswerScript =itr .next();
					if(examValuationAnswerScript.getValidationDetailsId()!=null){
						int amount=0;
						if(examValuationAnswerScript.getValidationDetailsId() != null && examValuationAnswerScript.getValidationDetailsId().getSubject() != null && examValuationAnswerScript.getValidationDetailsId().getSubject().getName() != null && examValuationAnswerScript.getValidationDetailsId().getSubject().getCode() != null){
							ValuatorChargesBo chargesBo=new ValuatorChargesBo();
							int programTypeId = transaction.getSubjectGroupForSubject(examValuationAnswerScript.getValidationDetailsId().getSubject().getId());
							if(programTypeId !=0){
								chargesBo = transaction.getValuatorChargerPerScript(programTypeId);
							}
							valuationChallanForm.setBoardMeeetingRate(chargesBo.getBoardMeetingCharge().toString());
							if(map.containsKey(examValuationAnswerScript.getValidationDetailsId().getIsValuator())){
								subMap = map.remove(examValuationAnswerScript.getValidationDetailsId().getIsValuator());
							}else{
								subMap = new HashMap<Integer, ValuationChallanTO>();
							}
							if(examValuationAnswerScript.getValidationDetailsId() != null && examValuationAnswerScript.getValidationDetailsId().getSubject() != null && examValuationAnswerScript.getValidationDetailsId().getSubject().getId() != 0){
								if(subMap.containsKey(examValuationAnswerScript.getValidationDetailsId().getSubject().getId())){
									to = subMap.remove(examValuationAnswerScript.getValidationDetailsId().getSubject().getId());
								}else{
									to = new ValuationChallanTO();
								}
							}
							if(!subjectList.contains(examValuationAnswerScript.getValidationDetailsId().getSubject().getId())){
								totalBoardMeetings = totalBoardMeetings +1;
								if(chargesBo.getBoardMeetingCharge() != null ){
									totalBoardMeetingCharges = totalBoardMeetingCharges + chargesBo.getBoardMeetingCharge().intValue();
								}
							}
							subjectList.add(examValuationAnswerScript.getValidationDetailsId().getSubject().getId());
							if(examValuationAnswerScript.getNumberOfAnswerScripts()>0){
								to.setAnswerScripts(to.getAnswerScripts()+examValuationAnswerScript.getNumberOfAnswerScripts());
								totalScripts = totalScripts + examValuationAnswerScript.getNumberOfAnswerScripts();
							}	
							to.setSubjectName(examValuationAnswerScript.getValidationDetailsId().getSubject().getName()+"("+examValuationAnswerScript.getValidationDetailsId().getSubject().getCode()+")");
							to.setSubjectId(examValuationAnswerScript.getValidationDetailsId().getSubject().getId());
							if(examValuationAnswerScript.getValidationDetailsId().getIsValuator().equalsIgnoreCase("Valuator1")|| examValuationAnswerScript.getValidationDetailsId().getIsValuator().equalsIgnoreCase("Valuator2") || examValuationAnswerScript.getValidationDetailsId().getIsValuator().equalsIgnoreCase("Re-Valuator")){
								if(chargesBo.getValuatorcharge() != null){
									amount = examValuationAnswerScript.getNumberOfAnswerScripts() * chargesBo.getValuatorcharge().intValue();
									to.setTotalAmount(String.valueOf(to.getAnswerScripts()*chargesBo.getValuatorcharge().intValue()));
									to.setRate(String.valueOf(chargesBo.getValuatorcharge().intValue()));
								}
							}
							if(examValuationAnswerScript.getValidationDetailsId().getIsValuator().equalsIgnoreCase("Reviewer")){
								if(chargesBo.getReviewercharge() != null){
									amount = examValuationAnswerScript.getNumberOfAnswerScripts() * chargesBo.getReviewercharge().intValue();
									to.setTotalAmount(String.valueOf(to.getAnswerScripts()*chargesBo.getReviewercharge().intValue()));
									to.setRate(String.valueOf(chargesBo.getReviewercharge().intValue()));
								}
							}
							if(examValuationAnswerScript.getValidationDetailsId().getIsValuator().equalsIgnoreCase("Project Minor")){
								if(chargesBo.getProjectevaluationminor() != null){
									amount = examValuationAnswerScript.getNumberOfAnswerScripts() * chargesBo.getProjectevaluationminor().intValue();
									to.setTotalAmount(String.valueOf(to.getAnswerScripts()*chargesBo.getProjectevaluationminor().intValue()));
									to.setRate(String.valueOf(chargesBo.getProjectevaluationminor().intValue()));
								}
							}
							if(examValuationAnswerScript.getValidationDetailsId().getIsValuator().equalsIgnoreCase("Project Major")){
								if(chargesBo.getProjectevaluationmajor() != null){
									amount = examValuationAnswerScript.getNumberOfAnswerScripts() * chargesBo.getProjectevaluationmajor().intValue();
									to.setTotalAmount(String.valueOf(to.getAnswerScripts()*chargesBo.getProjectevaluationmajor().intValue()));
									to.setRate(String.valueOf(chargesBo.getProjectevaluationmajor().intValue()));
								}
							}
							subMap.put(examValuationAnswerScript.getValidationDetailsId().getSubject().getId(), to);
							map.put(examValuationAnswerScript.getValidationDetailsId().getIsValuator(), subMap);
							valuationChallanForm.setViewFields(true);
							totalAmount = totalAmount + amount;
						}
					}
				}
				valuationChallanForm.setTotalScriptsAmount(String.valueOf(totalAmount));
				valuationChallanForm.setTotalScripts(totalScripts);
				valuationChallanForm.setTotalBoardMeetings(String.valueOf(totalBoardMeetings));
				ValuatorMeetingChargesBo meetingChargesBo =  transaction.getValuatorMeetingCharges();
				if(meetingChargesBo != null){
					valuationChallanForm.setTotalBoardMeetingCharge(String.valueOf(totalBoardMeetingCharges));
					int totalConveyanceCharge = 0;
					if(valuationChallanForm.getOtherEmpId() != null && !valuationChallanForm.getOtherEmpId().isEmpty()){
						totalConveyanceCharge = totalScripts / 20;
						int conveny = totalScripts % 20;
						if(conveny >=10){
							totalConveyanceCharge = totalConveyanceCharge +1;
						}
						valuationChallanForm.setTotalNoOfConveyance(String.valueOf(totalConveyanceCharge));
						totalConveyanceCharge = totalConveyanceCharge * meetingChargesBo.getConveyanceCharge().intValue();
						valuationChallanForm.setTotalConveyanceCharge(String.valueOf(totalConveyanceCharge));
					}
					valuationChallanForm.setConveyanceCharge(meetingChargesBo.getConveyanceCharge().toString());
					Double grandTotal = 0.0;
					grandTotal = grandTotal + totalAmount;
					grandTotal = grandTotal + totalConveyanceCharge ;
					grandTotal = grandTotal + totalBoardMeetingCharges;
					valuationChallanForm.setGrandTotal(String.valueOf(grandTotal));
				}
				valuationChallanForm.setMap(map);
			}
		}
	}
	/**
	 * @param valuationChallanForm
	 * @param request
	 * @throws Exception
	 */
	public void getDetailsForChallanPrint(ValuationChallanForm valuationChallanForm, HttpServletRequest request) throws Exception{
		double totalConveyanceCharge = 0.0;
		if(valuationChallanForm.getOtherEmpId() != null && !valuationChallanForm.getOtherEmpId().isEmpty()){
			if(valuationChallanForm.getTotalNoOfConveyance() != null && !valuationChallanForm.getTotalNoOfConveyance().trim().isEmpty()){
				totalConveyanceCharge = Double.parseDouble(valuationChallanForm.getTotalNoOfConveyance());
			}
			StringTokenizer st = new StringTokenizer(valuationChallanForm.getConveyanceCharge());
			totalConveyanceCharge = totalConveyanceCharge * Integer.parseInt(st.nextToken("."));
			valuationChallanForm.setTotalConveyanceCharge(String.valueOf(totalConveyanceCharge));
			valuationChallanForm.setPrintExternal(true);
		}else{
			if(valuationChallanForm.getTotalNoOfConveyance() != null && !valuationChallanForm.getTotalNoOfConveyance().trim().isEmpty()){
				totalConveyanceCharge = Double.parseDouble(valuationChallanForm.getTotalNoOfConveyance());
			}
			StringTokenizer st = new StringTokenizer(valuationChallanForm.getConveyanceCharge());
			totalConveyanceCharge = totalConveyanceCharge * Integer.parseInt(st.nextToken("."));
			valuationChallanForm.setTotalConveyanceCharge(String.valueOf(totalConveyanceCharge));
			valuationChallanForm.setPrintExternal(false);
		}
		Double grandTotal = 0.0;
		Double totalScriptsAmount = 0.0;
		Double totalBoardMeetingCharges=0.0;
		if(valuationChallanForm.getTotalScriptsAmount() != null && !valuationChallanForm.getTotalScriptsAmount().isEmpty()){
			totalScriptsAmount = Double.parseDouble(valuationChallanForm.getTotalScriptsAmount());
		}
		if(valuationChallanForm.getTotalBoardMeetingCharge() != null && !valuationChallanForm.getTotalBoardMeetingCharge().isEmpty()){
			totalBoardMeetingCharges = Double.parseDouble(valuationChallanForm.getTotalBoardMeetingCharge());
		}
		grandTotal = grandTotal + totalScriptsAmount ;
		grandTotal = grandTotal + totalConveyanceCharge ;
		grandTotal = grandTotal + totalBoardMeetingCharges;
		if(valuationChallanForm.getTa() != null && !valuationChallanForm.getTa().isEmpty()){
			grandTotal = grandTotal + Double.parseDouble(valuationChallanForm.getTa());
		}
		if(valuationChallanForm.getDa() != null && !valuationChallanForm.getDa().isEmpty()){
			grandTotal = grandTotal + Double.parseDouble(valuationChallanForm.getDa());
		}
		if(valuationChallanForm.getOtherCost() != null && !valuationChallanForm.getOtherCost().isEmpty()){
			grandTotal = grandTotal + Double.parseDouble(valuationChallanForm.getOtherCost());
		}
		valuationChallanForm.setGrandTotal(String.valueOf(grandTotal));
		valuationChallanForm.setTotalInWords(CommonUtil.numberToWord1(grandTotal.intValue()));
		ExamValuationRemuneration remuneration = transaction.getRemunerationDetails(valuationChallanForm);
		if(remuneration != null){
			if(valuationChallanForm.getOtherEmpId() != null && !valuationChallanForm.getOtherEmpId().trim().isEmpty()){
				if(remuneration.getBillNumber() != 0){
					valuationChallanForm.setChallanNo(String.valueOf(remuneration.getBillNumber()));
				}
				if(remuneration.getExamValuators() != null && remuneration.getExamValuators().getName() != null){
					valuationChallanForm.setEmployeeName(remuneration.getExamValuators().getName().toUpperCase());
				}
				if(remuneration.getExamValuators() != null && remuneration.getExamValuators().getPan() != null){
					valuationChallanForm.setPanNo(remuneration.getExamValuators().getPan());
				}
				if(remuneration.getExamValuators() != null && remuneration.getExamValuators().getBankAccNo() != null){
					valuationChallanForm.setAccountNo(remuneration.getExamValuators().getBankAccNo());
				}
				if(remuneration.getExamValuators() != null && remuneration.getExamValuators().getBankName() != null){
					valuationChallanForm.setBankName(remuneration.getExamValuators().getBankName());
				}
				if(remuneration.getExamValuators() != null && remuneration.getExamValuators().getBankBranch() != null){
					valuationChallanForm.setBankBranch(remuneration.getExamValuators().getBankBranch());
				}
				if(remuneration.getExamValuators() != null && remuneration.getExamValuators().getDepartment() != null){
					valuationChallanForm.setDepartmentName(remuneration.getExamValuators().getDepartment());
				}
				/*code added by sudhir */
				if(remuneration.getExamValuators() !=null && remuneration.getExamValuators().getBankIfscCode() !=null){
					valuationChallanForm.setBankIfscCode(remuneration.getExamValuators().getBankIfscCode());
				}
				/*code added by sudhir ended here */
				String address="";
				if(remuneration.getExamValuators() != null && remuneration.getExamValuators().getAddressLine1() != null){
					address = address + remuneration.getExamValuators().getAddressLine1();
					if(remuneration.getExamValuators().getAddressLine2() != null && !remuneration.getExamValuators().getAddressLine2().trim().isEmpty()){
						address = address +", "+ remuneration.getExamValuators().getAddressLine2();
					}
					if(remuneration.getExamValuators().getCity() != null && !remuneration.getExamValuators().getCity().trim().isEmpty()){
						address = address +", "+ remuneration.getExamValuators().getCity();
					}
					valuationChallanForm.setAddress(address);
				}
				
			}else{
				if(remuneration.getBillNumber() != 0){
					valuationChallanForm.setChallanNo(String.valueOf(remuneration.getBillNumber()));
				}
				if(remuneration.getUsers() != null && remuneration.getUsers().getEmployee() != null && remuneration.getUsers().getEmployee().getFirstName() != null){
					valuationChallanForm.setEmployeeName(remuneration.getUsers().getEmployee().getFirstName().toUpperCase());	
					valuationChallanForm.setPrintExternal(false);
					valuationChallanForm.setDisplayGuest("User");
				}// /*code added by chandra
				else if(remuneration.getUsers() != null && remuneration.getUsers().getGuest() != null && remuneration.getUsers().getGuest().getFirstName() != null){
					valuationChallanForm.setEmployeeName(remuneration.getUsers().getGuest().getFirstName().toUpperCase());	
					valuationChallanForm.setPrintExternal(false);
					valuationChallanForm.setDisplayGuest("Guest");
				}// */
				else if(remuneration.getUsers() != null && remuneration.getUsers().getUserName() != null ){
					valuationChallanForm.setEmployeeName(remuneration.getUsers().getUserName().toUpperCase());
					valuationChallanForm.setDisplayGuest("User");
				}
				else if(remuneration.getExamValuators() != null && remuneration.getExamValuators().getName() != null){
					valuationChallanForm.setEmployeeName(remuneration.getExamValuators().getName().toUpperCase());
					valuationChallanForm.setPrintExternal(true);
				}
				if(remuneration.getUsers() != null && remuneration.getUsers().getEmployee() != null && remuneration.getUsers().getEmployee().getDepartment() != null && remuneration.getUsers().getEmployee().getDepartment().getName() != null){
					valuationChallanForm.setDepartmentName(remuneration.getUsers().getEmployee().getDepartment().getName());
				}else if(remuneration.getUsers() != null && remuneration.getUsers().getDepartment() != null && remuneration.getUsers().getDepartment().getName() != null){
					valuationChallanForm.setDepartmentName(remuneration.getUsers().getDepartment().getName());
				}
				if(remuneration.getUsers() != null && remuneration.getUsers().getEmployee() != null && remuneration.getUsers().getEmployee().getPanNo() != null){
					valuationChallanForm.setPanNo(remuneration.getUsers().getEmployee().getPanNo());
				}
				if(remuneration.getUsers() != null && remuneration.getUsers().getEmployee() != null && remuneration.getUsers().getEmployee().getBankAccNo() != null){
					valuationChallanForm.setAccountNo(remuneration.getUsers().getEmployee().getBankAccNo());
				}
				if(remuneration.getUsers() != null && remuneration.getUsers().getEmployee() != null && remuneration.getUsers().getEmployee().getFingerPrintId() != null){
					valuationChallanForm.setFingerPrintId(remuneration.getUsers().getEmployee().getFingerPrintId());
				}
			
				// added by dilip
				if(remuneration.getUsers() != null && remuneration.getUsers().getGuest() != null){
					if(remuneration.getUsers().getGuest().getBankAccNo() != null){
						valuationChallanForm.setAccountNo(remuneration.getUsers().getGuest().getBankAccNo());
					}
					if(remuneration.getUsers().getGuest().getBankBranch() != null){
						valuationChallanForm.setBankBranch(remuneration.getUsers().getGuest().getBankBranch());
					}
					if(remuneration.getUsers().getGuest().getBankIfscCode() != null){
						valuationChallanForm.setBankIfscCode(remuneration.getUsers().getGuest().getBankIfscCode());
					}
					String address="";
					if(remuneration.getUsers().getGuest().getCommunicationAddressLine1() != null){
						address = remuneration.getUsers().getGuest().getCommunicationAddressLine1();
						if(remuneration.getUsers().getGuest().getCommunicationAddressLine2() != null && !remuneration.getUsers().getGuest().getCommunicationAddressLine2().trim().isEmpty()){
							address = address +", "+ remuneration.getUsers().getGuest().getCommunicationAddressLine2();
						}
						if(remuneration.getUsers().getGuest().getCommunicationAddressCity() != null && !remuneration.getUsers().getGuest().getCommunicationAddressCity().trim().isEmpty()){
							address = address +", "+ remuneration.getUsers().getGuest().getCommunicationAddressCity();
						}
						valuationChallanForm.setAddress(address);
					}
					if(remuneration.getUsers().getGuest().getDepartment() != null){
						valuationChallanForm.setDepartmentName(remuneration.getUsers().getGuest().getDepartment().getName());
					}
					if(remuneration.getUsers().getGuest().getPanNo() != null){
						valuationChallanForm.setPanNo(remuneration.getUsers().getGuest().getPanNo());
					
					}
				}// end by dilip
			}
		}
		valuationChallanForm.setCurrentDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(new Date()), ValuationChallanHandler.SQL_DATEFORMAT,	ValuationChallanHandler.FROM_DATEFORMAT));
		if(valuationChallanForm.getTotalConveyanceCharge() == null || valuationChallanForm.getTotalConveyanceCharge().isEmpty() || valuationChallanForm.getTotalNoOfConveyance() == null || valuationChallanForm.getTotalNoOfConveyance().isEmpty()){
			valuationChallanForm.setPrintconveyence(false);
		}else{
			if(Integer.parseInt(valuationChallanForm.getTotalNoOfConveyance()) == 0){
				valuationChallanForm.setPrintconveyence(false);
			}
		}
		if(valuationChallanForm.getTa() == null || valuationChallanForm.getTa().isEmpty()){
			valuationChallanForm.setPrintTa(false);
		}
		if(valuationChallanForm.getDa() == null || valuationChallanForm.getDa().isEmpty()){
			valuationChallanForm.setPrintDa(false);
		}
		if(valuationChallanForm.getOtherCost() == null || valuationChallanForm.getOtherCost().isEmpty()){
			valuationChallanForm.setPrintOther(false);
		}
	}
	/**
	 * @param valuationChallanForm
	 * @throws Exception
	 */
	public void getGeneratedChallanDetailsForInput(ValuationChallanForm valuationChallanForm) throws Exception{
		INewExamMarksEntryTransaction transaction1=NewExamMarksEntryTransactionImpl.getInstance();
		String hqlQuery = "from ExamValuationRemuneration e where e.isActive=1";
		if(valuationChallanForm.getEmployeeId() != null && !valuationChallanForm.getEmployeeId().isEmpty() && !valuationChallanForm.getEmployeeId().equalsIgnoreCase("Other")){
			hqlQuery = hqlQuery + " and e.users.id="+valuationChallanForm.getEmployeeId();
			valuationChallanForm.setPrintExternal(false);
		}
		if(valuationChallanForm.getOtherEmpId() != null && !valuationChallanForm.getOtherEmpId().isEmpty()){
			hqlQuery = hqlQuery +"and e.examValuators.id=" +valuationChallanForm.getOtherEmpId();
			valuationChallanForm.setPrintExternal(true);
		}
		
		List<ExamValuationRemuneration> list1=transaction1.getDataForQuery(hqlQuery);
		List<ValuationChallanTO> tos = new ArrayList<ValuationChallanTO>();
		if(list1 != null){
			Iterator<ExamValuationRemuneration> iterator = list1.iterator();
			while (iterator.hasNext()) {
				ExamValuationRemuneration examValuationRemuneration = (ExamValuationRemuneration) iterator.next();
				ValuationChallanTO to = new ValuationChallanTO();
				if(examValuationRemuneration.getExamValuators() != null && examValuationRemuneration.getExamValuators().getName() != null){
					to.setEmployeeName(examValuationRemuneration.getExamValuators().getName().toUpperCase());
				}// /*code added by chandra
				else if(examValuationRemuneration.getUsers().getEmployee() != null && examValuationRemuneration.getUsers().getEmployee().getFirstName() != null){
					to.setEmployeeName(examValuationRemuneration.getUsers().getEmployee().getFirstName().toUpperCase());
				}else if(examValuationRemuneration.getUsers().getGuest() != null && examValuationRemuneration.getUsers().getGuest().getFirstName() != null){
					to.setEmployeeName(examValuationRemuneration.getUsers().getGuest().getFirstName().toUpperCase());
				}// */
				else if(examValuationRemuneration.getUsers() != null && examValuationRemuneration.getUsers().getUserName() != null){
					to.setEmployeeName(examValuationRemuneration.getUsers().getUserName().toUpperCase());
				}
				
				if(examValuationRemuneration.getBillNumber() != 0){
					to.setBillNo(String.valueOf(examValuationRemuneration.getBillNumber()));
				}
				if(examValuationRemuneration.getId() !=0){
					to.setId(examValuationRemuneration.getId());
				}
				if(examValuationRemuneration.getCreatedDate() != null){
					to.setStartDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(examValuationRemuneration.getCreatedDate()), ValuationChallanHandler.SQL_DATEFORMAT,	ValuationChallanHandler.FROM_DATEFORMAT));
				}
				if(examValuationRemuneration.getIsPaid() != null && examValuationRemuneration.getIsPaid()){
					to.setIsPaid("true");
				}else
				{
					to.setIsPaid("false");
				}
				tos.add(to);
			}
		}
		valuationChallanForm.setGeneratedChallnList(tos);
	}
	/**
	 * @param valuationChallanForm
	 * @throws Exception
	 */
	public void getValuationDetailsForRePrint(ValuationChallanForm valuationChallanForm) throws Exception{ 
		INewExamMarksEntryTransaction transaction1=NewExamMarksEntryTransactionImpl.getInstance();
		String hqlQuery = "from ExamRemunerationDetails e where e.isActive=1";
	
		if(valuationChallanForm.getRemunerationId() != 0){
			hqlQuery = hqlQuery +" and e.remuneration.id=" +valuationChallanForm.getRemunerationId();
		}
		List<ExamRemunerationDetails> list1=transaction1.getDataForQuery(hqlQuery);
		if(list1 != null && !list1.isEmpty()){
			Iterator<ExamRemunerationDetails> itr=list1.iterator();
			ExamRemunerationDetails examRemunerationDetails=null;
			Map<String,Map<Integer, ValuationChallanTO>> map=new HashMap<String,Map<Integer, ValuationChallanTO>>();
			Map<Integer, ValuationChallanTO> subMap = new HashMap<Integer, ValuationChallanTO>();
			List<Integer> subjectList = new ArrayList<Integer>();
			ValuationChallanTO to = null;
			int totalScripts=0;
			int totalAmount=0;
			int totalBoardMeetings=0;
			double totalBoardMeetingCharges =0.0;
			while (itr.hasNext()) {
				examRemunerationDetails =itr .next();
				if(examRemunerationDetails.getRemuneration()!=null){
					
					int amount=0;
					if(examRemunerationDetails.getSubject() != null && examRemunerationDetails.getSubject().getName() != null && examRemunerationDetails.getSubject().getCode() != null){
						ValuatorChargesBo chargesBo=new ValuatorChargesBo();
						int programTypeId = transaction.getSubjectGroupForSubject(examRemunerationDetails.getSubject().getId());
						if(programTypeId !=0){
							chargesBo = transaction.getValuatorChargerPerScript(programTypeId);
						}
						valuationChallanForm.setBoardMeeetingRate(chargesBo.getBoardMeetingCharge().toString());
						if(map.containsKey(examRemunerationDetails.getType())){
							subMap = map.remove(examRemunerationDetails.getType());
						}else{
							subMap = new HashMap<Integer, ValuationChallanTO>();
						}
						if(examRemunerationDetails.getSubject() != null && examRemunerationDetails.getSubject().getId() != 0){
							if(subMap.containsKey(examRemunerationDetails.getSubject().getId())){
								to = subMap.remove(examRemunerationDetails.getSubject().getId());
							}else{
								to = new ValuationChallanTO();
							}
						}
						if(!subjectList.contains(examRemunerationDetails.getSubject().getId()) && examRemunerationDetails.getTotalScripts() != 0){
							totalBoardMeetings = totalBoardMeetings +1;
							if(examRemunerationDetails.getIsBoardMeetingApplicable()!=null && (examRemunerationDetails.getIsBoardMeetingApplicable())){
								if(chargesBo.getBoardMeetingCharge() != null ){
									totalBoardMeetingCharges = totalBoardMeetingCharges + chargesBo.getBoardMeetingCharge().intValue();
								}
							}
							
						}
						to.setBoardMeetingChargePerSub(chargesBo.getBoardMeetingCharge().intValue());
						subjectList.add(examRemunerationDetails.getSubject().getId());
						to.setAnswerScripts(to.getAnswerScripts()+examRemunerationDetails.getTotalScripts());
						totalScripts = totalScripts + examRemunerationDetails.getTotalScripts();
						to.setSubjectName(examRemunerationDetails.getSubject().getName()+"("+examRemunerationDetails.getSubject().getCode()+")");
						to.setSubjectId(examRemunerationDetails.getSubject().getId());
						if(examRemunerationDetails.getType().equalsIgnoreCase("Valuator") || examRemunerationDetails.getType().equalsIgnoreCase("Re-Valuator")){
							if(chargesBo.getMinimumScripts() != null && chargesBo.getMinimumScripts()>0 && examRemunerationDetails.getTotalScripts() <=chargesBo.getMinimumScripts() && chargesBo.getMinimumvaluatorcharge() != null){
								amount = chargesBo.getMinimumvaluatorcharge().intValue();
								to.setTotalAmount(String.valueOf(chargesBo.getMinimumvaluatorcharge().intValue()));
								to.setRate(String.valueOf(chargesBo.getMinimumvaluatorcharge().intValue()));
							}else if(chargesBo.getValuatorcharge() != null){
								amount = examRemunerationDetails.getTotalScripts() * chargesBo.getValuatorcharge().intValue();
								to.setTotalAmount(String.valueOf(to.getAnswerScripts()*chargesBo.getValuatorcharge().intValue()));
								to.setRate(String.valueOf(chargesBo.getValuatorcharge().intValue()));
							}
						}
						if(examRemunerationDetails.getType().equalsIgnoreCase("Reviewer")){
							if(chargesBo.getMinimumScripts() != null && chargesBo.getMinimumScripts()>0 && examRemunerationDetails.getTotalScripts() <=chargesBo.getMinimumScripts() && chargesBo.getMinimumreviewercharge() != null){
								amount = chargesBo.getMinimumreviewercharge().intValue();
								to.setTotalAmount(String.valueOf(chargesBo.getMinimumreviewercharge().intValue()));
								to.setRate(String.valueOf(chargesBo.getMinimumreviewercharge().intValue()));
							}else if(chargesBo.getReviewercharge() != null){
								amount = examRemunerationDetails.getTotalScripts() * chargesBo.getReviewercharge().intValue();
								to.setTotalAmount(String.valueOf(to.getAnswerScripts()*chargesBo.getReviewercharge().intValue()));
								to.setRate(String.valueOf(chargesBo.getReviewercharge().intValue()));
							}
						}
						if(examRemunerationDetails.getType().equalsIgnoreCase("Project Minor")){
							if(chargesBo.getMinimumScripts() != null && chargesBo.getMinimumScripts()>0 && examRemunerationDetails.getTotalScripts() <=chargesBo.getMinimumScripts() && chargesBo.getMinimumprojectevaluationminor() != null){
								amount = chargesBo.getMinimumprojectevaluationminor().intValue();
								to.setTotalAmount(String.valueOf(chargesBo.getMinimumprojectevaluationminor().intValue()));
								to.setRate(String.valueOf(chargesBo.getMinimumprojectevaluationminor().intValue()));
							}else if(chargesBo.getProjectevaluationminor() != null){
								amount = examRemunerationDetails.getTotalScripts() * chargesBo.getProjectevaluationminor().intValue();
								to.setTotalAmount(String.valueOf(to.getAnswerScripts()*chargesBo.getProjectevaluationminor().intValue()));
								to.setRate(String.valueOf(chargesBo.getProjectevaluationminor().intValue()));
							}
						}
						if(examRemunerationDetails.getType().equalsIgnoreCase("Project Major")){
							if(chargesBo.getMinimumScripts() != null && chargesBo.getMinimumScripts()>0 && examRemunerationDetails.getTotalScripts() <=chargesBo.getMinimumScripts() && chargesBo.getMinimumprojectevaluationmajor() != null){
								amount = chargesBo.getMinimumprojectevaluationmajor().intValue();
								to.setTotalAmount(String.valueOf(chargesBo.getMinimumprojectevaluationmajor().intValue()));
								to.setRate(String.valueOf(chargesBo.getMinimumprojectevaluationmajor().intValue()));
							}else if(chargesBo.getProjectevaluationmajor() != null){
								amount = examRemunerationDetails.getTotalScripts() * chargesBo.getProjectevaluationmajor().intValue();
								to.setTotalAmount(String.valueOf(to.getAnswerScripts()*chargesBo.getProjectevaluationmajor().intValue()));
								to.setRate(String.valueOf(chargesBo.getProjectevaluationmajor().intValue()));
							}
						}
						  // /* code added by chandra for Board Meetings
						if(examRemunerationDetails.getIsBoardMeetingApplicable() !=null){
							if(examRemunerationDetails.getIsBoardMeetingApplicable()){
								to.setIsBoardMeetingApplicable("on");
							}else{
								to.setIsBoardMeetingApplicable("off");
							}
						}else{
							to.setIsBoardMeetingApplicable("off");
						}
						to.setRemunerationDetailId(examRemunerationDetails.getId());
						// *///code added by chandra
						subMap.put(examRemunerationDetails.getSubject().getId(), to);   
						map.put(examRemunerationDetails.getType(), subMap);
						valuationChallanForm.setViewFields(true);
						totalAmount = totalAmount + amount;
					}
				}
				if(examRemunerationDetails.getRemuneration() != null){
					valuationChallanForm.setTotalBoardMeetings(String.valueOf(examRemunerationDetails.getRemuneration().getBoardMeetings()));
					valuationChallanForm.setTotalNoOfConveyance(String.valueOf(examRemunerationDetails.getRemuneration().getConveyanceCharges()));
					totalBoardMeetings = examRemunerationDetails.getRemuneration().getBoardMeetings();
				}
				if(examRemunerationDetails.getRemuneration() != null && examRemunerationDetails.getRemuneration().getDa() != null){
					valuationChallanForm.setDa(String.valueOf(examRemunerationDetails.getRemuneration().getDa()));
				}
				if(examRemunerationDetails.getRemuneration() != null && examRemunerationDetails.getRemuneration().getTa() != null){
					valuationChallanForm.setTa(String.valueOf(examRemunerationDetails.getRemuneration().getTa()));
				}
				if(examRemunerationDetails.getRemuneration() != null && examRemunerationDetails.getRemuneration().getAnyOther() != null){
					valuationChallanForm.setAnyOther(examRemunerationDetails.getRemuneration().getAnyOther());
				}
				if(examRemunerationDetails.getRemuneration() != null && examRemunerationDetails.getRemuneration().getAnyOtherCost() != null){
					valuationChallanForm.setOtherCost(String.valueOf(examRemunerationDetails.getRemuneration().getAnyOtherCost()));
				}
			}
			valuationChallanForm.setTotalScriptsAmount(String.valueOf(totalAmount));
			valuationChallanForm.setTotalScripts(totalScripts);
			valuationChallanForm.setTotalBoardMeetings(String.valueOf(totalBoardMeetings));
			ValuatorMeetingChargesBo meetingChargesBo =  transaction.getValuatorMeetingCharges();
			if(meetingChargesBo != null){
				int totalConveyanceCharge = 0;
				if(valuationChallanForm.getOtherEmpId() != null && !valuationChallanForm.getOtherEmpId().isEmpty()){
					if(valuationChallanForm.getTotalNoOfConveyance() != null && !valuationChallanForm.getTotalNoOfConveyance().trim().isEmpty()){
						totalConveyanceCharge = Integer.parseInt(valuationChallanForm.getTotalNoOfConveyance());
						totalConveyanceCharge = totalConveyanceCharge * meetingChargesBo.getConveyanceCharge().intValue();
					}
				}
				else
				{
					if(valuationChallanForm.getTotalNoOfConveyance() != null && !valuationChallanForm.getTotalNoOfConveyance().trim().isEmpty()){
						totalConveyanceCharge = Integer.parseInt(valuationChallanForm.getTotalNoOfConveyance());
						totalConveyanceCharge = totalConveyanceCharge * meetingChargesBo.getConveyanceCharge().intValue();
					}	
				}
				/*if(valuationChallanForm.getTotalBoardMeetings() != null && !valuationChallanForm.getTotalBoardMeetings().trim().isEmpty()){
					totalBoardMeetingCharges=0.0;
					totalBoardMeetings = Integer.parseInt(valuationChallanForm.getTotalBoardMeetings());
					totalBoardMeetingCharges = totalBoardMeetings * Double.parseDouble(valuationChallanForm.getBoardMeeetingRate()) ;
				}*/
				valuationChallanForm.setTotalBoardMeetingCharge(String.valueOf(totalBoardMeetingCharges));
				valuationChallanForm.setTotalConveyanceCharge(String.valueOf(totalConveyanceCharge));
				valuationChallanForm.setConveyanceCharge(meetingChargesBo.getConveyanceCharge().toString());
				Double grandTotal = 0.0;
				grandTotal = grandTotal + totalAmount;
				grandTotal = grandTotal + totalConveyanceCharge ;
				grandTotal = grandTotal + totalBoardMeetingCharges;
				if(valuationChallanForm.getTa() != null && !valuationChallanForm.getTa().trim().isEmpty()){
					grandTotal = grandTotal + Integer.parseInt(valuationChallanForm.getTa());
				}
				if(valuationChallanForm.getDa() != null && !valuationChallanForm.getDa().trim().isEmpty()){
					grandTotal = grandTotal + Integer.parseInt(valuationChallanForm.getDa());
				}
				if(valuationChallanForm.getOtherCost() != null && !valuationChallanForm.getOtherCost().trim().isEmpty()){
					grandTotal = grandTotal + Integer.parseInt(valuationChallanForm.getOtherCost());
				}
				valuationChallanForm.setGrandTotal(String.valueOf(grandTotal));
			}
			valuationChallanForm.setMap(map);
			
			// /* code added by chandra	
			
			List<ValuationChallanDetailsTO> valuationDetailsList=new ArrayList<ValuationChallanDetailsTO>();
			Map<String,Map<Integer, ValuationChallanTO>> map1 = map;
			if(map1 != null && !map1.isEmpty()){
				Iterator<Entry<String, Map<Integer, ValuationChallanTO>>> iterator = map1.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<String, Map<Integer, ValuationChallanTO>> entry = (Map.Entry<String, Map<Integer, ValuationChallanTO>>) iterator.next();
					 Map<Integer, ValuationChallanTO> subMap1 = entry.getValue();
					 int count=0;
					 if(subMap1 != null && !subMap1.isEmpty()){
						 Iterator<Entry<Integer, ValuationChallanTO>> iterator2 = subMap1.entrySet().iterator();
						 while (iterator2.hasNext()) {
							Map.Entry<Integer, ValuationChallanTO> entry2 = (Map.Entry<Integer, ValuationChallanTO>) iterator2.next();
							ValuationChallanTO valuationChallanTO = entry2.getValue();
							ValuationChallanDetailsTO valuationDetailTo=new ValuationChallanDetailsTO();
							valuationDetailTo.setValuatorType(entry.getKey());
							valuationDetailTo.setValuatorsTypeCount(count);
							valuationDetailTo.setSubjectId(valuationChallanTO.getSubjectId());
							valuationDetailTo.setSubjectName(valuationChallanTO.getSubjectName());
							valuationDetailTo.setAnswerScripts(valuationChallanTO.getAnswerScripts());
							valuationDetailTo.setTotalAmount(valuationChallanTO.getTotalAmount());
							valuationDetailTo.setIsBoardMeetingApplicable(valuationChallanTO.getIsBoardMeetingApplicable());
							valuationDetailTo.setRemunerationDetailId(valuationChallanTO.getRemunerationDetailId());
							valuationDetailTo.setBoardMeetingChargePerSub(valuationChallanTO.getBoardMeetingChargePerSub());
							valuationDetailsList.add(valuationDetailTo);
							count ++;
						 }
					 }
				}
			}
			valuationChallanForm.setValuationDetailsList(valuationDetailsList);
		// */ code added by achandra
		}
	}
	/**
	 * @param valuationChallanForm
	 * @param request
	 * @throws Exception
	 */
	public void getDetailsForChallanPrint(ValuationChallanForm valuationChallanForm, ExamValuationRemuneration remuneration) throws Exception{
		double totalConveyanceCharge = 0.0;
		if(valuationChallanForm.getOtherEmpId() != null && !valuationChallanForm.getOtherEmpId().isEmpty()){
			if(valuationChallanForm.getTotalNoOfConveyance() != null && !valuationChallanForm.getTotalNoOfConveyance().trim().isEmpty()){
				totalConveyanceCharge = Double.parseDouble(valuationChallanForm.getTotalNoOfConveyance());
			}
			StringTokenizer st = new StringTokenizer(valuationChallanForm.getConveyanceCharge());
			totalConveyanceCharge = totalConveyanceCharge * Integer.parseInt(st.nextToken("."));
			valuationChallanForm.setTotalConveyanceCharge(String.valueOf(totalConveyanceCharge));
			valuationChallanForm.setPrintExternal(true);
		}else{
			if(valuationChallanForm.getTotalNoOfConveyance() != null && !valuationChallanForm.getTotalNoOfConveyance().trim().isEmpty()){
				totalConveyanceCharge = Double.parseDouble(valuationChallanForm.getTotalNoOfConveyance());
			}
			StringTokenizer st = new StringTokenizer(valuationChallanForm.getConveyanceCharge());
			totalConveyanceCharge = totalConveyanceCharge * Integer.parseInt(st.nextToken("."));
			valuationChallanForm.setTotalConveyanceCharge(String.valueOf(totalConveyanceCharge));
			valuationChallanForm.setPrintExternal(false);
		}
		Double grandTotal = 0.0;
		Double totalScriptsAmount = 0.0;
		Double totalBoardMeetingCharges=0.0;
		if(valuationChallanForm.getTotalScriptsAmount() != null && !valuationChallanForm.getTotalScriptsAmount().isEmpty()){
			totalScriptsAmount = Double.parseDouble(valuationChallanForm.getTotalScriptsAmount());
		}
		if(valuationChallanForm.getTotalBoardMeetingCharge() != null && !valuationChallanForm.getTotalBoardMeetingCharge().isEmpty()){
			totalBoardMeetingCharges = Double.parseDouble(valuationChallanForm.getTotalBoardMeetingCharge());
		}
		grandTotal = grandTotal + totalScriptsAmount ;
		grandTotal = grandTotal + totalConveyanceCharge ;
		grandTotal = grandTotal + totalBoardMeetingCharges;
		if(valuationChallanForm.getTa() != null && !valuationChallanForm.getTa().isEmpty()){
			grandTotal = grandTotal + Double.parseDouble(valuationChallanForm.getTa());
		}
		if(valuationChallanForm.getDa() != null && !valuationChallanForm.getDa().isEmpty()){
			grandTotal = grandTotal + Double.parseDouble(valuationChallanForm.getDa());
		}
		if(valuationChallanForm.getOtherCost() != null && !valuationChallanForm.getOtherCost().isEmpty()){
			grandTotal = grandTotal + Double.parseDouble(valuationChallanForm.getOtherCost());
		}
		valuationChallanForm.setGrandTotal(String.valueOf(grandTotal));
		valuationChallanForm.setTotalInWords(CommonUtil.numberToWord1(grandTotal.intValue()));
		if(remuneration != null){
			if(valuationChallanForm.getOtherEmpId() != null && !valuationChallanForm.getOtherEmpId().trim().isEmpty()){
				ExamValuators valuators = transaction.getExamValuator(valuationChallanForm.getOtherEmpId());
				if(valuators != null && valuators.getName() != null){
					valuationChallanForm.setEmployeeName(valuators.getName().toUpperCase());
				}
				if(valuators != null && valuators.getPan() != null){
					valuationChallanForm.setPanNo(valuators.getPan());
				}
				if(valuators != null && valuators.getBankAccNo() != null){
					valuationChallanForm.setAccountNo(valuators.getBankAccNo());
				}
				if(valuators != null && valuators.getBankName() != null){
					valuationChallanForm.setBankName(valuators.getBankName());
				}
				if(valuators != null && valuators.getBankBranch() != null){
					valuationChallanForm.setBankBranch(valuators.getBankBranch());
				}
				if(valuators != null && valuators.getDepartment() != null){
					valuationChallanForm.setDepartmentName(valuators.getDepartment());
				}
				if(valuators !=null && valuators.getBankIfscCode()!=null){
					valuationChallanForm.setBankIfscCode(valuators.getBankIfscCode());
				}
				String address="";
				if(valuators != null && valuators.getAddressLine1() != null){
					address = address + valuators.getAddressLine1();
					if(valuators.getAddressLine2() != null && !valuators.getAddressLine2().trim().isEmpty()){
						address = address +", "+ valuators.getAddressLine2();
					}
					if(valuators.getCity() != null && !valuators.getCity().trim().isEmpty()){
						address = address +", "+ valuators.getCity();
					}
					valuationChallanForm.setAddress(address);
				}
			}else{
				Users users = transaction.getUser(remuneration.getUsers().getId());
				
				if(users != null && users.getEmployee() != null && users.getEmployee().getFirstName() != null){
					valuationChallanForm.setEmployeeName(users.getEmployee().getFirstName().toUpperCase());	
					valuationChallanForm.setPrintExternal(false);
					valuationChallanForm.setDisplayGuest("User");
				}// /* code added by chandra
				else if(users != null && users.getGuest() != null && users.getGuest().getFirstName() != null){
					valuationChallanForm.setEmployeeName(users.getGuest().getFirstName().toUpperCase());	
					valuationChallanForm.setPrintExternal(false);
					valuationChallanForm.setDisplayGuest("Guest");
				}// */
				else if(users != null && users.getUserName() != null ){
					valuationChallanForm.setEmployeeName(users.getUserName().toUpperCase());
					valuationChallanForm.setDisplayGuest("User");
				}				
				if(users != null && users.getEmployee() != null && users.getEmployee().getDepartment() != null && users.getEmployee().getDepartment().getName() != null){
					valuationChallanForm.setDepartmentName(users.getEmployee().getDepartment().getName());
				}else if(users.getDepartment() != null && users.getDepartment().getName() != null){
					valuationChallanForm.setDepartmentName(users.getDepartment().getName());
				}
				if(users != null && users.getEmployee() != null && users.getEmployee().getPanNo() != null){
					valuationChallanForm.setPanNo(users.getEmployee().getPanNo());
				}
				if(users != null && users.getEmployee() != null && users.getEmployee().getBankAccNo() != null){
					valuationChallanForm.setAccountNo(users.getEmployee().getBankAccNo());
				}
				if(users != null && users.getEmployee() != null && users.getEmployee().getFingerPrintId() != null){
					valuationChallanForm.setFingerPrintId(users.getEmployee().getFingerPrintId());
				}
				// added by dilip
				if(users != null && users.getGuest() != null){
					if(users.getGuest().getBankAccNo() != null){
						valuationChallanForm.setAccountNo(users.getGuest().getBankAccNo());
					}
					if(users.getGuest().getBankBranch() != null){
						valuationChallanForm.setBankBranch(users.getGuest().getBankBranch());
					}
					if(users.getGuest().getBankIfscCode() != null){
						valuationChallanForm.setBankIfscCode(users.getGuest().getBankIfscCode());
					}
					String address="";
					if(users.getGuest().getCommunicationAddressLine1() != null){
						address = users.getGuest().getCommunicationAddressLine1();
						if(users.getGuest().getCommunicationAddressLine2() != null && !users.getGuest().getCommunicationAddressLine2().trim().isEmpty()){
							address = address +", "+ users.getGuest().getCommunicationAddressLine2();
						}
						if(users.getGuest().getCommunicationAddressCity() != null && !users.getGuest().getCommunicationAddressCity().trim().isEmpty()){
							address = address +", "+ users.getGuest().getCommunicationAddressCity();
						}
						valuationChallanForm.setAddress(address);
					}
					if(users.getGuest().getDepartment() != null){
						valuationChallanForm.setDepartmentName(users.getGuest().getDepartment().getName());
					}
					if(users.getGuest().getPanNo() != null){
						valuationChallanForm.setPanNo(users.getGuest().getPanNo());
					}
				}// end by dilip
			}
		}
		valuationChallanForm.setCurrentDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(new Date()), ValuationChallanHandler.SQL_DATEFORMAT,	ValuationChallanHandler.FROM_DATEFORMAT));
		if(valuationChallanForm.getTotalConveyanceCharge() == null || valuationChallanForm.getTotalConveyanceCharge().isEmpty() || valuationChallanForm.getTotalNoOfConveyance() == null || valuationChallanForm.getTotalNoOfConveyance().isEmpty()){
			valuationChallanForm.setPrintconveyence(false);
		}else{
			if(Integer.parseInt(valuationChallanForm.getTotalNoOfConveyance()) == 0){
				valuationChallanForm.setPrintconveyence(false);
			}
		}
		if(valuationChallanForm.getTa() == null || valuationChallanForm.getTa().isEmpty()){
			valuationChallanForm.setPrintTa(false);
		}
		if(valuationChallanForm.getDa() == null || valuationChallanForm.getDa().isEmpty()){
			valuationChallanForm.setPrintDa(false);
		}
		if(valuationChallanForm.getOtherCost() == null || valuationChallanForm.getOtherCost().isEmpty()){
			valuationChallanForm.setPrintOther(false);
		}
	}
	/**
	 * @param valuationChallanForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveChallanDetails(ValuationChallanForm valuationChallanForm) throws Exception{
		boolean save = transaction.saveChallanDetails(valuationChallanForm);
		return save;
	}
	
	
	public List<ExamValuationStatusTO> getStatisticsSubjectWise(List<Integer> subList,int examId) throws Exception {
				List<ExamValuationStatusTO> valuationStatusSubjectWise = getSubjectWiseValuationStatistics(subList,examId);
				//valuationStatisticsForm.setValuationStatusSubjectWise(valuationStatusSubjectWise);
		
		return valuationStatusSubjectWise;
	}
	
	private List<ExamValuationStatusTO> getSubjectWiseValuationStatistics(List<Integer> subList,int examId) throws Exception{
		List<ExamValuationStatusTO> list=new ArrayList<ExamValuationStatusTO>();
		List<Integer> schemeNoList=transaction.getSemisternumbers(examId);
		String examType=transaction.getExamTypeByExamId(examId);
		IValuationStatisticsTransaction transaction1 = ValuationStatisticsTxImpl.getInstance();
		
		List<Object[]> details=null;
		if(examType!=null && !examType.isEmpty()){
			if(examType.equalsIgnoreCase("Regular")|| examType.equalsIgnoreCase("Regular & Supplementary")){
				details = transaction1.getTotalVerfiedStudent(String.valueOf(examId),schemeNoList,subList,false,examType);
			}else if(examType.equalsIgnoreCase("Supplementary") || examType.equalsIgnoreCase("Special Supplementary")){
				details = transaction1.getTotalVerfiedStudentForSupplementary(String.valueOf(examId),schemeNoList,subList,false);
			}
		}
		
			
		
		if(details != null){
			Iterator<Object[]> iterator = details.iterator();
			
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				if(objects[1] != null && objects[1].toString() != null){
					ExamValuationStatusTO to = new ExamValuationStatusTO();
					
					if(objects[0] != null && objects[0].toString() != null){
						to.setSubjectName(objects[0].toString());
					}
					if(objects[8] != null && objects[8].toString() != null){
						to.setSubjectCode(objects[8].toString());
					}
					if(objects[1] != null && objects[1].toString() != null){
						to.setSubjectId(Integer.parseInt(objects[1].toString()));
					}
					
					if(objects[2] != null && objects[2].toString() != null){
						to.setTotalStudent(Integer.parseInt(objects[2].toString()));
					}
					if(objects[3] != null && objects[3].toString() != null){
						to.setTotalEntered(Integer.parseInt(objects[3].toString()));
					}
					
					if(objects[5] != null && objects[5].toString() != null){
						to.setEvaluatorTypeId(objects[5].toString());
					}
					
					if(objects[14] != null && objects[14].toString() != null){
						to.setTermNumber(Integer.parseInt(objects[14].toString()));
					}
					list.add(to);
				}
			}
			
		}
		HibernateUtil.closeSession();
		return list;
	}
}
