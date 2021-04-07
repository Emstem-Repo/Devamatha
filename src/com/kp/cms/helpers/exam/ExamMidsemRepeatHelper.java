package com.kp.cms.helpers.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.actions.admission.NewStudentCertificateCourseAction;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamMidSemRepeatExemption;
import com.kp.cms.bo.exam.ExamMidsemRepeat;
import com.kp.cms.bo.exam.ExamMidsemRepeatDetails;
import com.kp.cms.bo.exam.OnlinePaymentReciepts;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.exceptions.SubjectGroupNotDefinedException;
import com.kp.cms.forms.admin.RepeatMidSemAppForm;
import com.kp.cms.forms.exam.ExamMidsemRepeatForm;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.to.exam.ExamMidsemRepeatTO;
import com.kp.cms.transactions.exam.IExamMidsemRepeatTransaction;
import com.kp.cms.transactionsimpl.exam.ExamMidsemRepeatTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.SMSUtils;
import com.sun.corba.se.impl.orbutil.threadpool.TimeoutException;

/**
 * @author dIlIp
 *
 */
public class ExamMidsemRepeatHelper {
	public static final String MSISDN = "MSISDN";
	public static final String PTRefId = "PTRefId";
	public static final String PTDateTime = "PTDateTime";
	public static final String Amount = "Amount";
	public static final String PTVendorId = "PTVendorId";
	public static final String PTOrderId = "PTOrderId";
	public static final String SPID = "SPID";
	public static final String TranType = "TranType";
	private static final Log log = LogFactory.getLog(ExamMidsemRepeatHelper.class);
	private static volatile ExamMidsemRepeatHelper examMidsemExemptionHelper = null;
	
	private ExamMidsemRepeatHelper() {
		
	}
	
	/**
	 * @return
	 */
	public static ExamMidsemRepeatHelper getInstance() {

		if (examMidsemExemptionHelper == null) {
			examMidsemExemptionHelper = new ExamMidsemRepeatHelper();
		}
		return examMidsemExemptionHelper;
	}
	IExamMidsemRepeatTransaction transaction = ExamMidsemRepeatTransactionImpl.getInstance();
	/**
	 * @param exemptionForm
	 * @return
	 * @throws SubjectGroupNotDefinedException 
	 */
	 public List<ExamMidsemRepeat> convertBOsToTOs(List<Object[]> repeatList,ExamMidsemRepeatForm exemptionForm) throws Exception {
		
		List<ExamMidsemRepeat> repeatBo= new ArrayList<ExamMidsemRepeat>();
		Map<Integer, String> map=new HashMap<Integer, String>();
		Iterator<Object[]> itr=repeatList.iterator();
	
		while (itr.hasNext()) {
			Object[] obj= (Object[]) itr.next();
			if(!map.containsKey(Integer.parseInt(obj[0].toString()))){
					 
			
			map.put(Integer.parseInt(obj[0].toString()), obj[1].toString());
			ExamMidsemRepeat ExamBo=new ExamMidsemRepeat();
			
			Student stuBo = new Student();
			stuBo.setId(Integer.parseInt(obj[0].toString()));
			ExamBo.setStudentId(stuBo);
			
			Classes classBO = new Classes();
		    classBO.setId(Integer.parseInt(obj[1].toString()));
			ExamBo.setClassId(classBO);
			
			ExamDefinitionBO midExamid = new ExamDefinitionBO();
			midExamid.setId(Integer.parseInt(obj[2].toString()));
			ExamBo.setMidsemExamId(midExamid);
			
			ExamDefinitionBO examId = new ExamDefinitionBO();
			examId.setId(Integer.parseInt(exemptionForm.getExamId()));
			ExamBo.setExamId(examId);
			ExamBo.setIsDownload(false);
			ExamBo.setIsFeePaid(false);
			ExamBo.setIsActive(true);
			ExamBo.setCreatedBy(exemptionForm.getUserId());
			ExamBo.setCreatedDate(new Date());
			ExamBo.setLastModifiedDate(new Date());
			ExamBo.setModifiedBy(exemptionForm.getUserId());
			
		Set<ExamMidsemRepeatDetails> exemptionDetails=new HashSet<ExamMidsemRepeatDetails>();
			Iterator<Object[]> itrr=repeatList.iterator();
		  while (itrr.hasNext()) {
			 Object[] objt= (Object[]) itrr.next();
			 if(obj[0].equals(objt[0])){
			  ExamMidsemRepeatDetails details = new ExamMidsemRepeatDetails();
			
			  Subject sub = new Subject();
			  sub.setId(Integer.parseInt(objt[3].toString()));					
			  details.setSubject(sub);
			  details.setExamMidsemRepeat(ExamBo);
			  details.setIsApplied(false);
			  details.setIsApproved(false);
			  details.setIsActive(true);
			  details.setCreatedBy(exemptionForm.getUserId());
			  details.setCreatedDate(new Date());
			  details.setLastModifiedDate(new Date());
			  details.setModifiedBy(exemptionForm.getUserId());
			  exemptionDetails.add(details);
			  }
			}
		    ExamBo.setExamMidsemRepeatDetails(exemptionDetails);
			repeatBo.add(ExamBo);
		  }
	    }
		return repeatBo;
	 }

	public boolean checkFoDelete(List<ExamMidsemRepeat> previousData) {
		boolean flag=false;
		if(previousData!=null && !previousData.isEmpty()){
			Iterator<ExamMidsemRepeat> itrr=previousData.iterator();
			while (itrr.hasNext()) {
				ExamMidsemRepeat examMidsem=(ExamMidsemRepeat) itrr.next();
				Iterator<ExamMidsemRepeatDetails> itr=examMidsem.getExamMidsemRepeatDetails().iterator();
				while (itr.hasNext()) {
					ExamMidsemRepeatDetails midsemRepeat = (ExamMidsemRepeatDetails) itr.next();
					if(midsemRepeat.getIsApplied()!=null && midsemRepeat.getIsApplied()){
						flag=true;
					}
				}
			}
		}
		return flag;
	}

	@SuppressWarnings({ "null", "unchecked" })
	public List<ExamMidsemRepeatTO> convertDataToForm(ExamMidsemRepeat previousData, LoginForm loginForm) {
      
		BigDecimal tam= new BigDecimal(0);
		List<ExamMidsemRepeatTO> allTos=new ArrayList<ExamMidsemRepeatTO>();
		if(previousData!=null){
			loginForm.setMidSemStudentName(previousData.getStudentId().getAdmAppln().getPersonalData().getFirstName());
			loginForm.setMidSemRepeatRegNo(previousData.getStudentId().getRegisterNo());
			loginForm.setMidSemClassName(previousData.getClassId().getName());
			loginForm.setMidSemClassId(previousData.getClassId().getId());
			loginForm.setMidSemRepeatProgram(previousData.getStudentId().getAdmAppln().getCourse().getProgram().getName());
			loginForm.setMidSemRepeatReason(previousData.getReason());
			loginForm.setMidSemFatherName(previousData.getStudentId().getAdmAppln().getPersonalData().getFatherName());
			loginForm.setMidSemGender(previousData.getStudentId().getAdmAppln().getPersonalData().getGender());
			loginForm.setIsDownloaded(previousData.getIsDownload());
			if(previousData.getIsFeePaid()!=null && previousData.getIsFeePaid()){
			loginForm.setIsFeesPaid("true");
	      	}else{
	      		loginForm.setIsFeesPaid("false");
	      	}
			loginForm.setOriginalDob(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(previousData.getStudentId().getAdmAppln().getPersonalData().getDateOfBirth()), NewStudentCertificateCourseAction.SQL_DATEFORMAT,NewStudentCertificateCourseAction.FROM_DATEFORMAT));
			List<Integer> getSubjectIds=new ArrayList<Integer>();
			Iterator<ExamMidsemRepeatDetails> itrr=previousData.getExamMidsemRepeatDetails().iterator();
			while (itrr.hasNext()) {
				ExamMidsemRepeatTO examMidsemTO = new ExamMidsemRepeatTO();
				ExamMidsemRepeatDetails examMidsemBo = (ExamMidsemRepeatDetails) itrr.next();
				examMidsemTO.setId(examMidsemBo.getId());
				examMidsemTO.setSubject(examMidsemBo.getSubject().getName());
				examMidsemTO.setSubjectId(examMidsemBo.getSubject().getId());
				examMidsemTO.setSubjectCode(examMidsemBo.getSubject().getCode());
				if(examMidsemBo.getAttenPercent()!=null && examMidsemBo.getAttenPercent()>0 ){
				examMidsemTO.setAttenPersent(examMidsemBo.getAttenPercent());
				}
				examMidsemTO.setStudentId(previousData.getStudentId().getId());
				examMidsemTO.setMidsemExamId(previousData.getMidsemExamId().getId());
				examMidsemTO.setMidsemExamName(previousData.getMidsemExamId().getName());
				examMidsemTO.setExamId(previousData.getExamId().getId());
				examMidsemTO.setExamName(previousData.getExamId().getName());
				examMidsemTO.setAmount(loginForm.getMidSemAmount());
				if(examMidsemBo.getIsApplied()!=null && examMidsemBo.getIsApplied()){
					examMidsemTO.setTempChecked("on");
				}if(examMidsemBo.getIsApproved()!=null && examMidsemBo.getIsApproved()){
					examMidsemTO.setTempCheckedApprove("on");
					tam=tam.add(loginForm.getMidSemAmount()) ;
					loginForm.setMidSemTotalAmount(tam);
				}
				if(examMidsemBo.getIsRejected()!=null && examMidsemBo.getIsRejected()){
					examMidsemTO.setTempCheckedReject("on");
				}else{
					examMidsemTO.setCheckedReject("off");
					examMidsemTO.setTempCheckedReject("off");
				}
				if(examMidsemTO.getSubjectId()>0){
					getSubjectIds.add(examMidsemTO.getSubjectId());
				}
				allTos.add(examMidsemTO);
				
			  }
			Collections.sort(allTos);
			if(getSubjectIds!=null && !getSubjectIds.isEmpty())
			loginForm.setSubjectIdList(getSubjectIds);
		}
		return allTos;
	}
	/**
	 * @param loginForm
	 * @return
	 * @throws Exception 
	 */
	public ExamMidsemRepeat convertTOsToBo(LoginForm loginForm) throws Exception {
		
		boolean notSelected=true;
		float totalClassHeld=0;
		float totalClassPresent=0;
		float percentage=0;
		Object[] aggPercentageList=transaction.getAggregatePercentage(loginForm.getMidSemStudentId(),loginForm.getMidSemClassId());
			if(aggPercentageList[0].toString()!=null){
				 totalClassHeld=Float.parseFloat(aggPercentageList[0].toString());
			}
			if(aggPercentageList[1].toString()!=null){
			   totalClassPresent=Float.parseFloat(aggPercentageList[1].toString());
			}
			 if(totalClassHeld>0 && totalClassPresent >0 && totalClassHeld>=totalClassPresent){
				  percentage=(totalClassPresent/totalClassHeld)*100;
			 }
			loginForm.setMidSemAggreagatePrint(String.valueOf(round(percentage,2)));
			ExamMidsemRepeat ExamBo=new ExamMidsemRepeat();
			ExamDefinitionBO examId = new ExamDefinitionBO();
			examId.setId(Integer.parseInt(loginForm.getMidSemRepeatExamId()));
			ExamBo.setExamId(examId);
			ExamBo.setId(Integer.parseInt(loginForm.getMidSemRepeatId()));
			ExamBo.setIsDownload(true);
			ExamBo.setIsActive(true);
			ExamBo.setAggregatePercentage(String.valueOf(round(percentage,2)));
			ExamBo.setReason(loginForm.getMidSemRepeatReason());
			ExamBo.setIsFeePaid(false);
			ExamBo.setIsFeeExempted(false);
			ExamBo.setLastModifiedDate(new Date());
			ExamBo.setModifiedBy(loginForm.getUserId());
			
		Set<ExamMidsemRepeatDetails> exemptionDetails=new HashSet<ExamMidsemRepeatDetails>();
			List<Object[]> attendanceList=transaction.getClassHeldandPresent(loginForm.getMidSemStudentId(),loginForm.getMidSemClassId(), loginForm.getSubjectIdList());
			Iterator<ExamMidsemRepeatTO> itrr=loginForm.getMidSemRepeatList().iterator();
			while (itrr.hasNext()) {
			  ExamMidsemRepeatTO tos= (ExamMidsemRepeatTO) itrr.next();
			  ExamMidsemRepeatDetails details = new ExamMidsemRepeatDetails();
			  if(attendanceList!=null){
					  Iterator<Object[]> itr=attendanceList.iterator();
					  while (itr.hasNext()) {
						  Object[] ob = (Object[]) itr.next();
						  if(Integer.parseInt(ob[2].toString())==tos.getSubjectId()){
						  float classHeld=Float.parseFloat(ob[0].toString());
						  float classPresent=Float.parseFloat(ob[1].toString());
							  if(classHeld>0 && classPresent >0 && classHeld>=classPresent){
								  Float percent=(classPresent/classHeld)*100;
								  details.setAttenPercent(round(percent,2));
								  break;
							  }
						  }
					  }
				  }
			 /* if(tos.getAttenPersent()==null || tos.getAttenPersent().toString().isEmpty()){
				  
				  float classHeld=transaction.getClassHeld(loginForm.getMidSemStudentId(),loginForm.getMidSemClassId(),tos.getSubjectId());
				  float classPresent=transaction.getClassPresent(loginForm.getMidSemStudentId(),loginForm.getMidSemClassId(),tos.getSubjectId());
				  if(classHeld>0 && classPresent >0 && classHeld>=classPresent){
					  Float percent=(classPresent/classHeld)*100;
					  details.setAttenPercent(round(percent,2));
				  }
			  }*/
			  details.setId(tos.getId());
			  if(tos.getChecked()!=null && tos.getChecked().equalsIgnoreCase("on")){
				  details.setIsApplied(true);
				  details.setAmount(loginForm.getMidSemAmount());
				  notSelected=false;
			  }else{
				  details.setIsApplied(false);
			  }
			  details.setIsApproved(false);
			  details.setIsActive(true);
			  exemptionDetails.add(details);
			}
		  if(notSelected){
			 throw new DataNotFoundException();
		  }
		    ExamBo.setExamMidsemRepeatDetails(exemptionDetails);
			return ExamBo;
	    }
	/**
	 * @param percent
	 * @param decimalPlace
	 * @return
	 */
	private Float round(Float percent, int decimalPlace) { 
	BigDecimal bd = new BigDecimal(Float.toString(percent));
    bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
    return bd.floatValue();}

	/**
	 * @param loginForm
	 * @return
	 * @throws Exception 
	 */
	public List<ExamMidsemRepeatTO> setDataToFormHallTicket(ExamMidsemRepeat previousData, LoginForm loginForm) { 
      
		BigDecimal tam= new BigDecimal(0);
		List<ExamMidsemRepeatTO> allTos=new ArrayList<ExamMidsemRepeatTO>();
		if(previousData!=null){
			loginForm.setMidSemStudentName(previousData.getStudentId().getAdmAppln().getPersonalData().getFirstName());
			loginForm.setMidSemRepeatRegNo(previousData.getStudentId().getRegisterNo());
			loginForm.setMidSemClassName(previousData.getClassId().getName());
			loginForm.setMidSemRepeatProgram(previousData.getStudentId().getAdmAppln().getCourse().getProgram().getName());
			loginForm.setMidSemFatherName(previousData.getStudentId().getAdmAppln().getPersonalData().getFatherName());
			loginForm.setMidSemGender(previousData.getStudentId().getAdmAppln().getPersonalData().getGender());
			loginForm.setMidSemRepeatReason(previousData.getReason());
			loginForm.setIsDownloaded(previousData.getIsDownload());
			loginForm.setOriginalDob(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(previousData.getStudentId().getAdmAppln().getPersonalData().getDateOfBirth()), NewStudentCertificateCourseAction.SQL_DATEFORMAT,NewStudentCertificateCourseAction.FROM_DATEFORMAT));
			loginForm.setMidSemAggreagatePrint(previousData.getAggregatePercentage());
			Iterator<ExamMidsemRepeatDetails> itrr=previousData.getExamMidsemRepeatDetails().iterator();
			while (itrr.hasNext()) {
				ExamMidsemRepeatDetails examMidsemBo = (ExamMidsemRepeatDetails) itrr.next();
			if(examMidsemBo.getIsApproved()!=null && examMidsemBo.getIsApproved()){
				ExamMidsemRepeatTO examMidsemTO = new ExamMidsemRepeatTO();
				examMidsemTO.setId(examMidsemBo.getId());
				examMidsemTO.setSubject(examMidsemBo.getSubject().getName());
				examMidsemTO.setSubjectId(examMidsemBo.getSubject().getId());
				examMidsemTO.setSubjectCode(examMidsemBo.getSubject().getCode());
				if(examMidsemBo.getAttenPercent()!=null && examMidsemBo.getAttenPercent()>0 ){
					examMidsemTO.setAttenPersent(examMidsemBo.getAttenPercent());
					}
				examMidsemTO.setStudentId(previousData.getStudentId().getId());
				examMidsemTO.setMidsemExamId(previousData.getMidsemExamId().getId());
				examMidsemTO.setMidsemExamName(previousData.getMidsemExamId().getName());
				examMidsemTO.setExamId(previousData.getExamId().getId());
				examMidsemTO.setExamName(previousData.getExamId().getName());
				examMidsemTO.setAmount(loginForm.getMidSemAmount());
				examMidsemTO.setTempChecked("on");
				tam=tam.add(loginForm.getMidSemAmount()) ;
				loginForm.setMidSemTotalAmount(tam);
				allTos.add(examMidsemTO);
				}
			  }
			Collections.sort(allTos);
		}
		return allTos;
	}

	public List<ExamMidsemRepeatTO> convertDataToFormPrints(ExamMidsemRepeat previousData, LoginForm loginForm) {
		BigDecimal tam= new BigDecimal(0);
		List<ExamMidsemRepeatTO> allTos=new ArrayList<ExamMidsemRepeatTO>();
		if(previousData!=null){
			loginForm.setMidSemStudentName(previousData.getStudentId().getAdmAppln().getPersonalData().getFirstName());
			loginForm.setMidSemRepeatRegNo(previousData.getStudentId().getRegisterNo());
			loginForm.setMidSemClassName(previousData.getClassId().getName());
			loginForm.setMidSemRepeatProgram(previousData.getStudentId().getAdmAppln().getCourse().getProgram().getName());
			loginForm.setMidSemRepeatReason(previousData.getReason());
			loginForm.setIsDownloaded(previousData.getIsDownload());
			loginForm.setOriginalDob(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(previousData.getStudentId().getAdmAppln().getPersonalData().getDateOfBirth()), NewStudentCertificateCourseAction.SQL_DATEFORMAT,NewStudentCertificateCourseAction.FROM_DATEFORMAT));
			loginForm.setMidSemAggreagatePrint(previousData.getAggregatePercentage());
			Iterator<ExamMidsemRepeatDetails> itrr=previousData.getExamMidsemRepeatDetails().iterator();
			while (itrr.hasNext()) {
				ExamMidsemRepeatDetails examMidsemBo = (ExamMidsemRepeatDetails) itrr.next();
			if(examMidsemBo.getIsApplied()!=null && examMidsemBo.getIsApplied()){
				ExamMidsemRepeatTO examMidsemTO = new ExamMidsemRepeatTO();
				examMidsemTO.setId(examMidsemBo.getId());
				examMidsemTO.setSubject(examMidsemBo.getSubject().getName());
				examMidsemTO.setSubjectId(examMidsemBo.getSubject().getId());
				if(examMidsemBo.getAttenPercent()!=null && examMidsemBo.getAttenPercent()>0 ){
					examMidsemTO.setAttenPersent(examMidsemBo.getAttenPercent());
					}
				examMidsemTO.setSubjectCode(examMidsemBo.getSubject().getCode());
				examMidsemTO.setStudentId(previousData.getStudentId().getId());
				examMidsemTO.setMidsemExamId(previousData.getMidsemExamId().getId());
				examMidsemTO.setMidsemExamName(previousData.getMidsemExamId().getName());
				examMidsemTO.setExamId(previousData.getExamId().getId());
				examMidsemTO.setExamName(previousData.getExamId().getName());
				examMidsemTO.setAmount(loginForm.getMidSemAmount());
				examMidsemTO.setTempChecked("on");
				tam=tam.add(loginForm.getMidSemAmount()) ;
				loginForm.setMidSemTotalAmount(tam);
				allTos.add(examMidsemTO);
				}
			  }
			Collections.sort(allTos);
		}
		return allTos;
	}

	public List<ExamMidsemRepeatTO> convertCoeDataToForm(List<ExamMidsemRepeat> previousData, ExamMidsemRepeatForm loginForm) {
		BigDecimal tam= new BigDecimal(0);
		List<ExamMidsemRepeatTO> allTos=new ArrayList<ExamMidsemRepeatTO>();
		Iterator<ExamMidsemRepeat> itr=previousData.iterator();
		while (itr.hasNext()) {
		ExamMidsemRepeat examMidsemRepeat = (ExamMidsemRepeat) itr.next();
		loginForm.setMidSemStudentName(examMidsemRepeat.getStudentId().getAdmAppln().getPersonalData().getFirstName());
		loginForm.setMidSemClassName(examMidsemRepeat.getClassId().getName());
		loginForm.setMidSemExamId(Integer.toString(examMidsemRepeat.getMidsemExamId().getId()));
		loginForm.setRepeatExamId(Integer.toString(examMidsemRepeat.getExamId().getId()));
		loginForm.setMidSemRepeatId(Integer.toString(examMidsemRepeat.getId()));
		loginForm.setReason(examMidsemRepeat.getReason());
		if(examMidsemRepeat.getIsFeePaid()!=null){
			if(examMidsemRepeat.getIsFeePaid()){
					loginForm.setIsFeesPaid("true");
					loginForm.setErrorsFeePaid("true");
					if(examMidsemRepeat.getOnlinePaymentReceipt()!=null && examMidsemRepeat.getOnlinePaymentReceipt().getId()>0){
					loginForm.setOnlinePaymentReceipt(String.valueOf(examMidsemRepeat.getOnlinePaymentReceipt().getId()));
					if(examMidsemRepeat.getIsPaymentFail()!=null){
						if(examMidsemRepeat.getIsPaymentFail())
								loginForm.setIsPaymentFail("true");
						else
							loginForm.setIsPaymentFail("false");
					}else
					{
						loginForm.setIsPaymentFail("false");
					}
					}
			}else{
				loginForm.setIsFeesPaid("false");
				loginForm.setErrorsFeePaid("false");
			}
		}else
		{
			loginForm.setIsFeesPaid("false");
			loginForm.setErrorsFeePaid("false");
		}
		if(examMidsemRepeat.getIsFeeExempted()!=null){
			if(examMidsemRepeat.getIsFeeExempted())
					loginForm.setIsFeeExempted("true");
			else
				loginForm.setIsFeeExempted("false");
		}else
		{
			loginForm.setIsFeeExempted("false");
		}
		Iterator<ExamMidsemRepeatDetails> itrr=examMidsemRepeat.getExamMidsemRepeatDetails().iterator();
		while (itrr.hasNext()) {
			ExamMidsemRepeatTO examMidsemTO = new ExamMidsemRepeatTO();
			ExamMidsemRepeatDetails examMidsemBo = (ExamMidsemRepeatDetails) itrr.next();
			if(examMidsemBo.getIsApplied()!=null && examMidsemBo.getIsApplied()){
			examMidsemTO.setId(examMidsemBo.getId());
			examMidsemTO.setSubject(examMidsemBo.getSubject().getName());
			examMidsemTO.setSubjectId(examMidsemBo.getSubject().getId());
			if(examMidsemBo.getAttenPercent()!=null && examMidsemBo.getAttenPercent()>0 ){
				examMidsemTO.setAttenPersent(examMidsemBo.getAttenPercent());
				}
			examMidsemTO.setSubjectCode(examMidsemBo.getSubject().getCode());
			examMidsemTO.setAmount(examMidsemBo.getAmount());
			examMidsemTO.setChecked("on");
			examMidsemTO.setTempChecked("on");
			if(examMidsemBo.getIsApproved()!=null && examMidsemBo.getIsApproved()){
				examMidsemTO.setTempCheckedApprove("on");
				tam=tam.add(examMidsemBo.getAmount()) ;
				loginForm.setTotalFees(tam);
			}else{
				examMidsemTO.setCheckedApprove("off");
				examMidsemTO.setTempCheckedApprove("off");
			}
			if(examMidsemBo.getIsRejected()!=null && examMidsemBo.getIsRejected()){
				examMidsemTO.setTempCheckedReject("on");
			}else{
				examMidsemTO.setCheckedReject("off");
				examMidsemTO.setTempCheckedReject("off");
			}
			allTos.add(examMidsemTO);
			}
		  }
		Collections.sort(allTos);
		}
		return allTos;
	}
	
	public List<ExamMidsemRepeatTO> convertOfflineDataToForm(List<ExamMidsemRepeat> previousData, ExamMidsemRepeatForm loginForm) {
		BigDecimal tam= new BigDecimal(0);
		List<ExamMidsemRepeatTO> allTos=new ArrayList<ExamMidsemRepeatTO>();
		Iterator<ExamMidsemRepeat> itr=previousData.iterator();
		while (itr.hasNext()) {
		ExamMidsemRepeat examMidsemRepeat = (ExamMidsemRepeat) itr.next();
		loginForm.setMidSemStudentName(examMidsemRepeat.getStudentId().getAdmAppln().getPersonalData().getFirstName());
		loginForm.setMidSemClassName(examMidsemRepeat.getClassId().getName());
		loginForm.setMidSemExamId(Integer.toString(examMidsemRepeat.getMidsemExamId().getId()));
		loginForm.setRepeatExamId(Integer.toString(examMidsemRepeat.getExamId().getId()));
		loginForm.setMidSemRepeatId(Integer.toString(examMidsemRepeat.getId()));
		if(examMidsemRepeat.getIsFeeExempted()!=null){
			if(examMidsemRepeat.getIsFeeExempted())
				loginForm.setIsFeeExempted("true");
			else
				loginForm.setIsFeeExempted("false");
		}else
		{
			loginForm.setIsFeeExempted("false");
		}
		if(examMidsemRepeat.getReason()!=null && !examMidsemRepeat.getReason().isEmpty())
			loginForm.setReason(examMidsemRepeat.getReason());
		if(examMidsemRepeat.getAggregatePercentage()!=null && !examMidsemRepeat.getAggregatePercentage().isEmpty())
			loginForm.setAttendancePercentage(examMidsemRepeat.getAggregatePercentage());
		if(examMidsemRepeat.getOnlinePaymentReceipt()!=null && examMidsemRepeat.getOnlinePaymentReceipt().getId()>0){
			loginForm.setOnlinePaymentReceipt(String.valueOf(examMidsemRepeat.getOnlinePaymentReceipt().getId()));
		}
		if(examMidsemRepeat.getIsApplyOnline()!=null && examMidsemRepeat.getIsApplyOnline())
			loginForm.setIsApplyOnline("true");
		else
			loginForm.setIsApplyOnline("false");
		
		if(examMidsemRepeat.getIsFeePaid()!=null && examMidsemRepeat.getIsFeePaid()){
			loginForm.setOfflineFeePaid("on");
			loginForm.setTempOfflineFeesPaid("on");
		}
		else
		{
			loginForm.setOfflineFeePaid("off");
			loginForm.setTempOfflineFeesPaid("off");
		}
		if(examMidsemRepeat.getFeeDescription()!=null && !examMidsemRepeat.getFeeDescription().isEmpty()){
			loginForm.setFeePaymentDescription(examMidsemRepeat.getFeeDescription());
		}else
		{
			loginForm.setFeePaymentDescription(null);
		}
		Iterator<ExamMidsemRepeatDetails> itrr=examMidsemRepeat.getExamMidsemRepeatDetails().iterator();
		while (itrr.hasNext()) {
			ExamMidsemRepeatTO examMidsemTO = new ExamMidsemRepeatTO();
			ExamMidsemRepeatDetails examMidsemBo = (ExamMidsemRepeatDetails) itrr.next();
			if(examMidsemBo.getIsApplied()!=null && examMidsemBo.getIsApplied()){
			examMidsemTO.setId(examMidsemBo.getId());
			examMidsemTO.setSubject(examMidsemBo.getSubject().getName());
			examMidsemTO.setSubjectId(examMidsemBo.getSubject().getId());
			if(examMidsemBo.getAttenPercent()!=null && examMidsemBo.getAttenPercent()>0 ){
				examMidsemTO.setAttenPersent(examMidsemBo.getAttenPercent());
				}
			examMidsemTO.setSubjectCode(examMidsemBo.getSubject().getCode());
			examMidsemTO.setAmount(examMidsemBo.getAmount());
			examMidsemTO.setChecked("on");
			examMidsemTO.setTempChecked("on");
			if(examMidsemBo.getIsApproved()!=null && examMidsemBo.getIsApproved()){
				examMidsemTO.setTempCheckedApprove("on");
				examMidsemTO.setCheckedApprove("on");
				tam=tam.add(examMidsemBo.getAmount()) ;
				loginForm.setTotalFees(tam);
			}else{
				examMidsemTO.setCheckedApprove("off");
				examMidsemTO.setTempCheckedApprove("off");
			}
			if(examMidsemBo.getIsRejected()!=null && examMidsemBo.getIsRejected()){
				examMidsemTO.setTempCheckedReject("on");
				examMidsemTO.setCheckedReject("on");
			}else{
				examMidsemTO.setCheckedReject("off");
				examMidsemTO.setTempCheckedReject("off");
			}
			allTos.add(examMidsemTO);
			}
		  }
		Collections.sort(allTos);
		}
		return allTos;
	}
	
	public ExamMidsemRepeat convertApprovedTOsToBo(ExamMidsemRepeatForm loginForm) throws Exception{
		    boolean notSelected=true;
		    loginForm.setDataApproved("false");
		    loginForm.setDataRejected("false");
			ExamMidsemRepeat ExamBo=new ExamMidsemRepeat();
			ExamDefinitionBO examId = new ExamDefinitionBO();
			examId.setId(Integer.parseInt(loginForm.getExamId()));
			ExamBo.setExamId(examId);
			ExamBo.setId(Integer.parseInt(loginForm.getMidSemRepeatId()));
			ExamBo.setIsDownload(true);
			ExamBo.setIsActive(true);
			ExamBo.setReason(loginForm.getReason());
			ExamBo.setLastModifiedDate(new Date());
			ExamBo.setModifiedBy(loginForm.getUserId());
			if(loginForm.getIsFeeExempted()!=null){
				if(loginForm.getIsFeeExempted().equalsIgnoreCase("true")){
					ExamBo.setIsFeeExempted(true);
					ExamBo.setIsFeePaid(true);
					ExamBo.setIsApplyOnline(false);
					ExamBo.setIsPaymentFail(false);
				}	
				else{
					ExamBo.setIsFeeExempted(false);
					ExamBo.setIsFeePaid(false);
					ExamBo.setIsApplyOnline(false);
					ExamBo.setIsPaymentFail(false);
				}
			}else{
					ExamBo.setIsFeeExempted(false);
					ExamBo.setIsFeePaid(false);
					ExamBo.setIsApplyOnline(false);
					ExamBo.setIsPaymentFail(false);
			}
		Set<ExamMidsemRepeatDetails> exemptionDetails=new HashSet<ExamMidsemRepeatDetails>();
			Iterator<ExamMidsemRepeatTO> itrr=loginForm.getMidSemRepeatList().iterator();
		  while (itrr.hasNext()) {
			  ExamMidsemRepeatTO tos= (ExamMidsemRepeatTO) itrr.next();
			  
			  ExamMidsemRepeatDetails details = new ExamMidsemRepeatDetails();
			  details.setId(tos.getId());
			  details.setLastModifiedDate(new Date());
			  details.setModifiedBy(loginForm.getUserId());
			  if(tos.getAttenPersent()!=null && tos.getAttenPersent()>0 ){
				  details.setAttenPercent(tos.getAttenPersent());
					}
			  if(tos.getChecked()!=null && tos.getChecked().equalsIgnoreCase("on")){
				  details.setIsApplied(true);
			  }else{
				  details.setIsApplied(false);
			  }
			  if(tos.getCheckedApprove()!=null && tos.getCheckedApprove().equalsIgnoreCase("on")){
				  details.setIsApproved(true);
				  loginForm.setDataApproved("true");
				  notSelected=false;
			  }else{
				  details.setIsApproved(false);
				  if(tos.getCheckedReject()!=null && tos.getCheckedReject().equalsIgnoreCase("on")){
					  details.setIsRejected(true);
					  notSelected=false;
					  loginForm.setDataRejected("true");
				  }else{
					  details.setIsRejected(false);
					  
				  }
			  }
			  details.setIsActive(true);
			  details.setAmount(tos.getAmount());
			  exemptionDetails.add(details);
			}
		   if(notSelected){
			 throw new DataNotFoundException();
		   }
		    ExamBo.setExamMidsemRepeatDetails(exemptionDetails);
			return ExamBo;
	    }
	
	public ExamMidsemRepeat convertOfflineTOsToBo(ExamMidsemRepeatForm loginForm) throws Exception{
		
		    boolean notSelected=true;
			ExamMidsemRepeat ExamBo=new ExamMidsemRepeat();
			ExamDefinitionBO examId = new ExamDefinitionBO();
			examId.setId(Integer.parseInt(loginForm.getExamId()));
			ExamBo.setExamId(examId);
			ExamBo.setId(Integer.parseInt(loginForm.getMidSemRepeatId()));
			ExamBo.setIsDownload(true);
			ExamBo.setIsActive(true);
			ExamBo.setLastModifiedDate(new Date());
			ExamBo.setModifiedBy(loginForm.getUserId());
			if(loginForm.getReason()!=null)
			ExamBo.setReason(loginForm.getReason());
			if(loginForm.getAttendancePercentage()!=null)
			ExamBo.setAggregatePercentage(loginForm.getAttendancePercentage());
			ExamBo.setFeeDescription(loginForm.getFeePaymentDescription());
			ExamBo.setTotalAmount(loginForm.getTotalFees());
			ExamBo.setIsFeeExempted(false);
			 if(loginForm.getOfflineFeePaid()!=null && loginForm.getOfflineFeePaid().equalsIgnoreCase("on")){
				 	ExamBo.setIsFeePaid(true);
				 	if(loginForm.getIsApplyOnline()!=null && loginForm.getIsApplyOnline().equalsIgnoreCase("true"))
				 		ExamBo.setIsApplyOnline(true);
				 	else
				 		ExamBo.setIsApplyOnline(false);
					ExamBo.setIsPaymentFail(false);

			  }else{
				  	ExamBo.setIsFeePaid(false);
				  	if(loginForm.getIsApplyOnline()!=null && loginForm.getIsApplyOnline().equalsIgnoreCase("true"))
				 		ExamBo.setIsApplyOnline(true);
				 	else
				 		ExamBo.setIsApplyOnline(false);
					ExamBo.setIsPaymentFail(false);
				  }
					
		Set<ExamMidsemRepeatDetails> exemptionDetails=new HashSet<ExamMidsemRepeatDetails>();
			Iterator<ExamMidsemRepeatTO> itrr=loginForm.getMidSemRepeatList().iterator();
		  while (itrr.hasNext()) {
			  ExamMidsemRepeatTO tos= (ExamMidsemRepeatTO) itrr.next();
			  
			  ExamMidsemRepeatDetails details = new ExamMidsemRepeatDetails();
			  details.setId(tos.getId());
			  details.setLastModifiedDate(new Date());
			  details.setModifiedBy(loginForm.getUserId());
			  if(tos.getAttenPersent()!=null && tos.getAttenPersent()>0 ){
				  details.setAttenPercent(tos.getAttenPersent());
					}
			  if(tos.getChecked()!=null && tos.getChecked().equalsIgnoreCase("on")){
				  details.setIsApplied(true);
			  }else{
				  details.setIsApplied(false);
			  }
			  if(tos.getCheckedApprove()!=null && tos.getCheckedApprove().equalsIgnoreCase("on")){
				  details.setIsApproved(true);
				  notSelected=false;
			  }else{
				  details.setIsApproved(false);
			  }
			  if(tos.getCheckedReject()!=null && tos.getCheckedReject().equalsIgnoreCase("on")){
				  details.setIsRejected(true);
			  }else{
				  details.setIsRejected(false);
			  }
			  details.setIsActive(true);
			  details.setAmount(tos.getAmount());
			  exemptionDetails.add(details);
			}
		   if(notSelected){
			 throw new DataNotFoundException();
		   }
		    ExamBo.setExamMidsemRepeatDetails(exemptionDetails);
			return ExamBo;
	    }

	public void dedactAmountFromAccount(OnlinePaymentReciepts paymentReciepts, LoginForm loginForm) throws Exception {
		boolean netOrIOExceptionRaised = false;
		StringBuffer response=new StringBuffer();
		try {
			String requestUrl=formatRequestForBank(loginForm.getRegNo(),paymentReciepts.getId(),loginForm.getMidSemTotalAmount().toString()).toString();
			try {
				response=SMSUtils.send_request(false,requestUrl);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}catch (TimeoutException e) {
			e.printStackTrace();
			netOrIOExceptionRaised = true;
		}catch (Exception e) {
			e.printStackTrace();
			netOrIOExceptionRaised = true;
		}
		formatResponse(paymentReciepts, response, netOrIOExceptionRaised);
		//Update the bo, after setting the response which comes from the bank. 
		PropertyUtil.getInstance().update(paymentReciepts);
	}
	public static StringBuffer formatRequestForBank(String registerNo, int id,
			String feeAmount) throws Exception {
		StringBuffer str = new StringBuffer();
		// get the bankLink from the application.properties to send the request
		// to the bank.
		str.append(CMSConstants.bankLink);
		str.append("&");
		str.append(SMSUtils.getNVP(MSISDN, "REG" + registerNo/* "9633300817" */,
				false));
		str.append(SMSUtils.getNVP(PTRefId, String.valueOf(id), false));
		str.append(SMSUtils.getNVP(PTDateTime, CommonUtil
				.formatDateToDesiredFormatString(CommonUtil.getTodayDate(),
						"dd/MM/yyyy", "dd-MM-yyyy"), false));
		str.append(SMSUtils.getNVP(Amount, String.valueOf(feeAmount)/* 1.0 */,
				false));// in real time uncomment feeAmount and remove 1.00
						// ruppee
		str.append(SMSUtils.getNVP(PTVendorId, String.valueOf("SIB"), false));
		str.append(SMSUtils.getNVP(PTOrderId, String.valueOf(id), false));
		str.append(SMSUtils.getNVP(SPID, "FEESCOLLECT", false));
		str.append(TranType + "=" + "FTD");

		return str;
}

/** method for get the response from the bank and set the information to Bo.
 * @param bo
 * @param response
 * @param netOrIOException
 */
public static void formatResponse(OnlinePaymentReciepts bo, StringBuffer response, boolean netOrIOException){
	boolean isPaymentFailed=true;
	// Process error if any
	if (netOrIOException || !response.toString().contains("MSISDN=")){
		bo.setStatus("Payment Failed Due to TimeOut");
		bo.setIsPaymentFailed(isPaymentFailed);
	}else{
		int x1=response.indexOf("MSISDN=");
		int x2 = x1 + "MSISDN=".length();
		int x3 = response.indexOf("&", x2);
		
		int x4=x3+1+"PTRefId=".length();
		int x5 = response.indexOf("&", x4);
		
		int x6=x5+1+"PTDateTime=".length();
		int x7 = response.indexOf("&", x6);
		bo.setTransactionDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToDateFormat(response.substring(x6,x7), "dd-MM-yyyy","dd/MM/yyyy")));
		
		int x8=x7+1+"status=".length();
		int x9 = response.indexOf("&", x8);
		String status=response.substring(x8,x9);
		if(status.equalsIgnoreCase("000") /*|| status.equalsIgnoreCase("412") || status.equalsIgnoreCase("414") || status.equalsIgnoreCase("409")*/){
			bo.setStatus("success");	
			isPaymentFailed=false;				
		}else if(status.equalsIgnoreCase("114")){
			bo.setStatus("Invalid Account Number");
		}else if(status.equalsIgnoreCase("116")){
			bo.setStatus("Insufficient Balance");
		}else if(status.equalsIgnoreCase("119")){
			bo.setStatus("Transaction Not allowed");
		}else if(status.equalsIgnoreCase("121")){
			bo.setStatus("with drawal limit exceeded");
		}else if(status.equalsIgnoreCase("902")){
			bo.setStatus("Invalid function code");
		}else if(status.equalsIgnoreCase("909")){
			bo.setStatus("system Malfunction");
		}else if(status.equalsIgnoreCase("913")){
			bo.setStatus("Duplicate transaction");
		}else
			bo.setStatus("Payment Failed");
			
		int x10=x9+1+"BankConfirmationId=".length();
		int x11= response.length();
		bo.setBankConfirmationId(response.substring(x10,x11));
		bo.setIsPaymentFailed(isPaymentFailed);
	}
}

public ExamMidSemRepeatExemption convertTOsToBoExemption(RepeatMidSemAppForm repeatMidSemAppForm) throws Exception {
	boolean notSelected=true;
	ExamMidSemRepeatExemption ExamBo=new ExamMidSemRepeatExemption();
	 if(repeatMidSemAppForm.getChecked()!=null && repeatMidSemAppForm.getChecked().equalsIgnoreCase("on")){
		notSelected=false;
		ExamDefinitionBO examId = new ExamDefinitionBO();
		examId.setId(Integer.parseInt(repeatMidSemAppForm.getMidSemExamId()));
		ExamBo.setMidsemExamId(examId);
		Student studId = new Student();
		studId.setId(Integer.parseInt(repeatMidSemAppForm.getStudentId()));
		ExamBo.setStudentId(studId);
		ExamBo.setIsActive(true);
		ExamBo.setCreatedBy(repeatMidSemAppForm.getUserId());
		ExamBo.setCreatedDate(new Date());
		ExamBo.setModifiedBy(repeatMidSemAppForm.getUserId());
		ExamBo.setLastModifiedDate(new Date());
	 }else
	 {
		 notSelected=true;
	 }
	 if(notSelected){
		 throw new DataNotFoundException();
	  }
		return ExamBo;
    }

}
