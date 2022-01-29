package com.kp.cms.handlers.exam;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.bo.employee.GuestFaculty;
import com.kp.cms.bo.exam.ExamRemunerationDetails;
import com.kp.cms.bo.exam.ExamValuationRemuneration;
import com.kp.cms.bo.exam.ValuatorChargesBo;
import com.kp.cms.bo.exam.ValuatorMeetingChargesBo;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.ExamValuationRemuPaymentForm;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.handlers.hostel.AbsentiesListHandler;
import com.kp.cms.helpers.exam.ExamValuationRemuPaymentHelper;
import com.kp.cms.to.exam.ExamValuationRemuPaymentTo;
import com.kp.cms.to.exam.ValuationChallanTO;
import com.kp.cms.transactions.exam.IExamValuationRemuPaymentTrans;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.exam.IValuationChallanTransaction;
import com.kp.cms.transactionsimpl.exam.ExamValuationRemuPaymentTransImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.ValuationChallanTxImpl;
import com.kp.cms.utilities.PropertyUtil;


public class ExamValuationRemuPaymentHandler {
	ExamValuationRemuPaymentHelper helper=ExamValuationRemuPaymentHelper.getInstance();
	IExamValuationRemuPaymentTrans transactions=ExamValuationRemuPaymentTransImpl.getInstance();
	IValuationChallanTransaction transaction = ValuationChallanTxImpl.getInstance();
	public static volatile ExamValuationRemuPaymentHandler examValuationRemuPaymentHandler = null;
	
	private ExamValuationRemuPaymentHandler(){
		
	}
	
	public static ExamValuationRemuPaymentHandler getInstance() {
		if (examValuationRemuPaymentHandler == null) {
			examValuationRemuPaymentHandler = new ExamValuationRemuPaymentHandler();
			return examValuationRemuPaymentHandler;
		}
		return examValuationRemuPaymentHandler;
	}
	/**
	 * @param examValuationRemuPaymentForm
	 * @return
	 * @throws Exception
	 */
	public List<ExamValuationRemuPaymentTo> searchTheValuatorsList(
			ExamValuationRemuPaymentForm examValuationRemuPaymentForm) throws Exception{
		List<ExamValuationRemuPaymentTo> list=null;
		String query=helper.getQuery(examValuationRemuPaymentForm);
		List<Object[]> objects=transactions.getListOfSearchedValuators(query);
		List<Object[]> boardMeetingCharges = transactions.getBoardMeetingCharges(examValuationRemuPaymentForm);
		Map<Integer, Map<Integer, Double>> boardCharge = createChargesMap(boardMeetingCharges,examValuationRemuPaymentForm);
		list=helper.convertObjectsToTos(objects,examValuationRemuPaymentForm,boardCharge);
		return list;
	}
	/**
	 * @param boardMeetingCharges
	 * @param examValuationRemuPaymentForm
	 * @return
	 */
	private Map<Integer, Map<Integer, Double>> createChargesMap(
			List<Object[]> boardMeetingCharges,
			ExamValuationRemuPaymentForm examValuationRemuPaymentForm) {
		Map<Integer, Map<Integer, Double>> map = new HashMap<Integer, Map<Integer,Double>>();
		Map<Integer, Double> submap = new HashMap<Integer, Double>();
		if(boardMeetingCharges != null && !boardMeetingCharges.isEmpty()){
			for (Object[] objects : boardMeetingCharges) {
				if(examValuationRemuPaymentForm.getValuatorsType().equalsIgnoreCase("I")){
					if(objects[2] != null && objects[0] != null && objects[1] != null){
						if(map.containsKey(Integer.parseInt(objects[2].toString()))){
							submap = map.get(Integer.parseInt(objects[2].toString()));
						}
						Double amount = 0.0;
						if(submap.containsKey(Integer.parseInt(objects[1].toString()))){
							amount = amount + submap.get(Integer.parseInt(objects[1].toString()));
						}
						amount = amount + Double.valueOf(objects[0].toString());
						submap.put(Integer.parseInt(objects[1].toString()), amount);
						map.put(Integer.parseInt(objects[2].toString()), submap);
					}
				}else if(examValuationRemuPaymentForm.getValuatorsType().equalsIgnoreCase("E")){
					if(objects[4] != null && objects[0] != null && objects[1] != null){
						if(map.containsKey(Integer.parseInt(objects[4].toString()))){
							submap = map.get(Integer.parseInt(objects[4].toString()));
						}
						Double amount = 0.0;
						if(submap.containsKey(Integer.parseInt(objects[1].toString()))){
							amount = amount + submap.get(Integer.parseInt(objects[1].toString()));
						}
						amount = amount + Double.valueOf(objects[0].toString());
						submap.put(Integer.parseInt(objects[1].toString()), amount);
						map.put(Integer.parseInt(objects[4].toString()), submap);
					}
				}else if(examValuationRemuPaymentForm.getValuatorsType().equalsIgnoreCase("B")){
					if(objects[2] != null && objects[0] != null && objects[1] != null){
						if(map.containsKey(Integer.parseInt(objects[2].toString()))){
							submap = map.get(Integer.parseInt(objects[2].toString()));
						}
						Double amount = 0.0;
						if(submap.containsKey(Integer.parseInt(objects[1].toString()))){
							amount = amount + submap.get(Integer.parseInt(objects[1].toString()));
						}
						amount = amount + Double.valueOf(objects[0].toString());
						submap.put(Integer.parseInt(objects[1].toString()), amount);
						map.put(Integer.parseInt(objects[2].toString()), submap);
					}else if(objects[4] != null && objects[0] != null && objects[1] != null){
						if(map.containsKey(Integer.parseInt(objects[4].toString()))){
							submap = map.get(Integer.parseInt(objects[4].toString()));
						}
						Double amount = 0.0;
						if(submap.containsKey(Integer.parseInt(objects[1].toString()))){
							amount = amount + submap.get(Integer.parseInt(objects[1].toString()));
						}
						amount = amount + Double.valueOf(objects[0].toString());
						submap.put(Integer.parseInt(objects[1].toString()), amount);
						map.put(Integer.parseInt(objects[4].toString()), submap);
					}
				}
			}
		}
		return map;
	}

	public void displayValuatorDetails(
			ExamValuationRemuPaymentForm examValuationRemuPaymentForm) throws Exception{ 
		INewExamMarksEntryTransaction transaction1=NewExamMarksEntryTransactionImpl.getInstance();
		String hqlQuery = "from ExamRemunerationDetails e where e.isActive=1 and e.remuneration.id=" +examValuationRemuPaymentForm.getId();
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
						examValuationRemuPaymentForm.setBoardMeeetingRate(chargesBo.getBoardMeetingCharge().toString());
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
						if(!subjectList.contains(examRemunerationDetails.getSubject().getId()) && examRemunerationDetails.getTotalScripts() !=0){
							totalBoardMeetings = totalBoardMeetings +1;
							if(examRemunerationDetails.getIsBoardMeetingApplicable()!=null && (examRemunerationDetails.getIsBoardMeetingApplicable())){
								if(chargesBo.getBoardMeetingCharge() != null ){
									totalBoardMeetingCharges = totalBoardMeetingCharges + chargesBo.getBoardMeetingCharge().intValue();
								}
							}
						}
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
						subMap.put(examRemunerationDetails.getSubject().getId(), to);   
						map.put(examRemunerationDetails.getType(), subMap);
						examValuationRemuPaymentForm.setViewFields(true);
						totalAmount = totalAmount + amount;
					}
				}
				if(examRemunerationDetails.getRemuneration() != null){
					examValuationRemuPaymentForm.setTotalBoardMeetings(String.valueOf(examRemunerationDetails.getRemuneration().getBoardMeetings()));
					examValuationRemuPaymentForm.setTotalNoOfConveyance(String.valueOf(examRemunerationDetails.getRemuneration().getConveyanceCharges()));
					totalBoardMeetings = examRemunerationDetails.getRemuneration().getBoardMeetings();
				}
				if(examRemunerationDetails.getRemuneration() != null && examRemunerationDetails.getRemuneration().getDa() != null){
					examValuationRemuPaymentForm.setDa(String.valueOf(examRemunerationDetails.getRemuneration().getDa()));
				}
				if(examRemunerationDetails.getRemuneration() != null && examRemunerationDetails.getRemuneration().getTa() != null){
					examValuationRemuPaymentForm.setTa(String.valueOf(examRemunerationDetails.getRemuneration().getTa()));
				}
				if(examRemunerationDetails.getRemuneration() != null && examRemunerationDetails.getRemuneration().getAnyOther() != null){
					examValuationRemuPaymentForm.setAnyOther(examRemunerationDetails.getRemuneration().getAnyOther());
				}
				if(examRemunerationDetails.getRemuneration() != null && examRemunerationDetails.getRemuneration().getAnyOtherCost() != null){
					examValuationRemuPaymentForm.setOtherCost(String.valueOf(examRemunerationDetails.getRemuneration().getAnyOtherCost()));
				}
			}
			examValuationRemuPaymentForm.setTotalScriptsAmount(String.valueOf(totalAmount));
			examValuationRemuPaymentForm.setTotalScripts(totalScripts);
			examValuationRemuPaymentForm.setTotalBoardMeetings(String.valueOf(totalBoardMeetings));
			ValuatorMeetingChargesBo meetingChargesBo =  transaction.getValuatorMeetingCharges();
			if(meetingChargesBo != null){
				int totalConveyanceCharge = 0;
				if(examValuationRemuPaymentForm.getOtherEmpId() != null && !examValuationRemuPaymentForm.getOtherEmpId().isEmpty()){
					if(examValuationRemuPaymentForm.getTotalNoOfConveyance() != null && !examValuationRemuPaymentForm.getTotalNoOfConveyance().trim().isEmpty()){
						totalConveyanceCharge = Integer.parseInt(examValuationRemuPaymentForm.getTotalNoOfConveyance());
						totalConveyanceCharge = totalConveyanceCharge * meetingChargesBo.getConveyanceCharge().intValue();
					}
				}
				else
				{
					if(examValuationRemuPaymentForm.getTotalNoOfConveyance() != null && !examValuationRemuPaymentForm.getTotalNoOfConveyance().trim().isEmpty()){
						totalConveyanceCharge = Integer.parseInt(examValuationRemuPaymentForm.getTotalNoOfConveyance());
						totalConveyanceCharge = totalConveyanceCharge * meetingChargesBo.getConveyanceCharge().intValue();
					}	
				}
				/*if(examValuationRemuPaymentForm.getTotalBoardMeetings() != null && !examValuationRemuPaymentForm.getTotalBoardMeetings().trim().isEmpty()){
					totalBoardMeetingCharges=0.0;
					totalBoardMeetings = Integer.parseInt(examValuationRemuPaymentForm.getTotalBoardMeetings());
					totalBoardMeetingCharges = totalBoardMeetings * Double.parseDouble(examValuationRemuPaymentForm.getBoardMeeetingRate()) ;
				}*/
				examValuationRemuPaymentForm.setTotalBoardMeetingCharge(String.valueOf(totalBoardMeetingCharges));
				examValuationRemuPaymentForm.setTotalConveyanceCharge(String.valueOf(totalConveyanceCharge));
				examValuationRemuPaymentForm.setConveyanceCharge(meetingChargesBo.getConveyanceCharge().toString());
				Double grandTotal = 0.0;
				grandTotal = grandTotal + totalAmount;
				grandTotal = grandTotal + totalConveyanceCharge ;
				grandTotal = grandTotal + totalBoardMeetingCharges;
				if(examValuationRemuPaymentForm.getTa() != null && !examValuationRemuPaymentForm.getTa().trim().isEmpty()){
					grandTotal = grandTotal + Integer.parseInt(examValuationRemuPaymentForm.getTa());
				}
				if(examValuationRemuPaymentForm.getDa() != null && !examValuationRemuPaymentForm.getDa().trim().isEmpty()){
					grandTotal = grandTotal + Integer.parseInt(examValuationRemuPaymentForm.getDa());
				}
				if(examValuationRemuPaymentForm.getOtherCost() != null && !examValuationRemuPaymentForm.getOtherCost().trim().isEmpty()){
					grandTotal = grandTotal + Integer.parseInt(examValuationRemuPaymentForm.getOtherCost());
				}
//				examValuationRemuPaymentForm.setChallanNo(String.valueOf(examValuationRemuPaymentForm.getVocherId()));
				examValuationRemuPaymentForm.setPrintDa(false);
				examValuationRemuPaymentForm.setPrintTa(false);
				examValuationRemuPaymentForm.setPrintOther(false);
				examValuationRemuPaymentForm.setPrintconveyence(true);
				examValuationRemuPaymentForm.setGrandTotal(String.valueOf(grandTotal));
			}
			examValuationRemuPaymentForm.setMap(map);
		}
	}
	public boolean updatePaidAndModeOfPayment(
			List<ExamValuationRemuPaymentTo> list, String userId, List<Integer> integers) throws Exception{
		List<ExamValuationRemuneration> examList=transactions.getExamValuationRemunirations(integers);
		Map<Integer,ExamValuationRemuneration> map=helper.convertListToMap(examList);
		List<ExamValuationRemuneration> examValuationRemunerations=helper.convertToToBos(list,userId,map);
		boolean flag=transactions.update(examValuationRemunerations);
		if(flag){
			sendSMSForValuators(list);
		}
		return flag;
	}
	private void sendSMSForValuators(List<ExamValuationRemuPaymentTo> list) throws Exception{
		Properties prop = new Properties();
        InputStream in1 = AbsentiesListHandler.class.getClassLoader().getResourceAsStream(CMSConstants.SMS_FILE_CFG);
        prop.load(in1);
		String senderNumber=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
		String senderName=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
		String desc="";
		SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
		List<SMSTemplate> list1= temphandle.getDuplicateCheckList(0,CMSConstants.VALUATION_REMUNIRATION_PAYMENT_SMS);
		if(list1 != null && !list1.isEmpty()) {
			Iterator<ExamValuationRemuPaymentTo> iterator=list.iterator();
			while (iterator.hasNext()) {
				ExamValuationRemuPaymentTo examValuationRemuPaymentTo = (ExamValuationRemuPaymentTo) iterator.next();
				desc = list1.get(0).getTemplateDescription();
				desc=desc.replace(CMSConstants.GENERATED_NO,String.valueOf(examValuationRemuPaymentTo.getVocherNo()));
				desc=desc.replace(CMSConstants.TOTAL_AMOUNT,examValuationRemuPaymentTo.getTotalAmount());
				desc=desc.replace(CMSConstants.VALUATION_DATE,examValuationRemuPaymentTo.getDate());
				desc=desc.replace(CMSConstants.MODE_OF_PAYMENT,examValuationRemuPaymentTo.getModeOfPayment());
				sendSMS("91"+examValuationRemuPaymentTo.getMobileNo(), desc, senderNumber, senderName);
			}
		}
	}
	private boolean sendSMS(String phoneNum, String desc,String senderNumber,String senderName)throws Exception {
		boolean sentSms=false;
		if(StringUtils.isNumeric(phoneNum) && (phoneNum.length()==12 && desc.length()<=160)){
			MobileMessaging mob=new MobileMessaging();
			mob.setDestinationNumber(phoneNum);
			mob.setMessageBody(desc);
			mob.setMessagePriority(3);
			mob.setSenderName(senderName);
			mob.setSenderNumber(senderNumber);
			mob.setMessageEnqueueDate(new Date());
			mob.setIsMessageSent(false);
			sentSms=PropertyUtil.getInstance().save(mob);
		}
		return sentSms;
	}
	public GuestFaculty getApplicantDetails(String name) throws Exception {
		// TODO Auto-generated method stub
		return transactions.getGuestFaculty(name);
	}
}
