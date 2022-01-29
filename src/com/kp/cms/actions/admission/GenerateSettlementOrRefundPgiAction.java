package com.kp.cms.actions.admission;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;


import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.GenerateSettlementOrRefundPgiForm;
import com.kp.cms.handlers.admission.GenerateSettlementOrRefundPgiHandler;
import com.kp.cms.to.admin.GenerateSettlementTo;
import com.kp.cms.to.admission.GenerateRefundPgiTo;
import com.kp.cms.to.admission.GenerateSettlementOrRefundPgiTo;
import com.kp.cms.utilities.CommonUtil;

public class GenerateSettlementOrRefundPgiAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(GenerateSettlementOrRefundPgiAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward initGenerateSettlementOrRefundPgi(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		log.debug("inside initGenerateSettlementOrRefundPgi in Action");
		GenerateSettlementOrRefundPgiForm generateSettlementOrRefundPgiForm = (GenerateSettlementOrRefundPgiForm) form;
		generateSettlementOrRefundPgiForm.setFormatName(null);
		generateSettlementOrRefundPgiForm.setOpenRefundLink(false);
		generateSettlementOrRefundPgiForm.setOpensettementLink(false);
		generateSettlementOrRefundPgiForm.resetFields();
			
		log.info("Exit initGenerateSettlementOrRefundPgi - printChallan");
		return mapping.findForward(CMSConstants.Generate_Settlement_RefundForm);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward generateSettlementFiles(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		log.debug("inside GenerateSettlementOrRefundPgiAction in Action");
		GenerateSettlementOrRefundPgiForm generateSettlementOrRefundPgiForm = (GenerateSettlementOrRefundPgiForm) form;
		 ActionErrors errors=generateSettlementOrRefundPgiForm.validate(mapping, request);
		validateDate(errors,generateSettlementOrRefundPgiForm);
		generateSettlementOrRefundPgiForm.setRefundDownloadFile(false);
		generateSettlementOrRefundPgiForm.setSettlementDownloadFile(false);
		 if (errors.isEmpty()) {
				try {
					List<GenerateSettlementOrRefundPgiTo> generateSettlementOrRefundPgiTo = GenerateSettlementOrRefundPgiHandler.getInstance().generateFilesForSettlementOrRefund(generateSettlementOrRefundPgiForm);
					List<GenerateSettlementTo> toList=new ArrayList<GenerateSettlementTo>();
					if(generateSettlementOrRefundPgiTo != null && !generateSettlementOrRefundPgiTo.isEmpty()){
						Iterator<GenerateSettlementOrRefundPgiTo> itr=generateSettlementOrRefundPgiTo.iterator();
						String date1="";
						while(itr.hasNext()) {
							GenerateSettlementOrRefundPgiTo to=itr.next();
							GenerateSettlementTo to1=new GenerateSettlementTo();
							if(to.getAdmAppln() != null){
								to1.setId(to.getId());
								if(to.getTxnRefNo()!=null){
									to1.setTxnRefNo(to.getTxnRefNo());
								}
								if(to.getCandidateRefNo()!=null){
									to1.setCandidateRefNo(to.getCandidateRefNo());
								}
								if(to.getTxnAmount() != null){
									to1.setAmount(String.valueOf(to.getTxnAmount()));
								}
								if(to.getTxnDate() != null){
									date1=CommonUtil.formatSqlDate2((to.getTxnDate()).toString());
									to1.setDate(date1);
									}
								if(to.getSettlementGenerated()!=null && to.getSettlementGenerated()){
									to1.setSettlementGenerated("Yes");
								}else{
									to1.setSettlementGenerated("No");
								}
								toList.add(to1);
								}
							}
						
						generateSettlementOrRefundPgiForm.setGenerateSettlementTo(toList);
						generateSettlementOrRefundPgiForm.setGenerateRefundPgiTo(null);
						}else{
						generateSettlementOrRefundPgiForm.setOpensettementLink(false);
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.norecords"));
						saveErrors(request, errors);
						generateSettlementOrRefundPgiForm.resetFields();
					}
				} catch (Exception exception) {
					String msg = super.handleApplicationException(exception);
					generateSettlementOrRefundPgiForm.setErrorMessage(msg);
					generateSettlementOrRefundPgiForm.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			} else {
				addErrors(request, errors);
				log.info("Exit GenerateSettlementOrRefundPgiAction - printChallan errors not empty ");
				return mapping.findForward(CMSConstants.Generate_Settlement_RefundForm);
			}
			
			log.info("Exit GenerateSettlementOrRefundPgiAction - printChallan");
			return mapping.findForward(CMSConstants.Generate_Settlement_RefundForm);
		 	
	}
	
	/**
	 * @param errors
	 * @param generateSettlementOrRefundPgiForm
	 */
	public void validateDate(ActionErrors errors,GenerateSettlementOrRefundPgiForm generateSettlementOrRefundPgiForm){
		if(generateSettlementOrRefundPgiForm.getFromDate()!=null && !StringUtils.isEmpty(generateSettlementOrRefundPgiForm.getFromDate())&& !CommonUtil.isValidDate(generateSettlementOrRefundPgiForm.getFromDate())){
		if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
			errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
		}
	}
	if(generateSettlementOrRefundPgiForm.getToDate()!=null && !StringUtils.isEmpty(generateSettlementOrRefundPgiForm.getToDate())&& !CommonUtil.isValidDate(generateSettlementOrRefundPgiForm.getToDate())){
		if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
			errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
		}
	}
	if(CommonUtil.checkForEmpty(generateSettlementOrRefundPgiForm.getFromDate()) && CommonUtil.checkForEmpty(generateSettlementOrRefundPgiForm.getToDate())){
		Date startDate = CommonUtil.ConvertStringToDate(generateSettlementOrRefundPgiForm.getFromDate());
		Date endDate = CommonUtil.ConvertStringToDate(generateSettlementOrRefundPgiForm.getToDate());

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(startDate);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(endDate);
		long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
		if(daysBetween <= 0) {
			errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
			}
		}
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward generateRefundFile(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		log.debug("inside GenerateSettlementOrRefundPgiAction in Action");
		GenerateSettlementOrRefundPgiForm generateSettlementOrRefundPgiForm = (GenerateSettlementOrRefundPgiForm) form;
		 ActionErrors errors=generateSettlementOrRefundPgiForm.validate(mapping, request);
		validateDate(errors,generateSettlementOrRefundPgiForm);
		generateSettlementOrRefundPgiForm.setRefundDownloadFile(false);
		generateSettlementOrRefundPgiForm.setSettlementDownloadFile(false);
		 if (errors.isEmpty()) {
				try {
					List<GenerateSettlementOrRefundPgiTo> generateSettlementOrRefundPgiTo = GenerateSettlementOrRefundPgiHandler.getInstance().generateFilesForSettlementOrRefund(generateSettlementOrRefundPgiForm);
					List<GenerateRefundPgiTo> toList=new ArrayList<GenerateRefundPgiTo>();
					if(generateSettlementOrRefundPgiTo != null && !generateSettlementOrRefundPgiTo.isEmpty()){
						Iterator<GenerateSettlementOrRefundPgiTo> itr=generateSettlementOrRefundPgiTo.iterator();
						String date1="";
						while(itr.hasNext()) {
							GenerateSettlementOrRefundPgiTo to=itr.next();
							GenerateRefundPgiTo to1=new GenerateRefundPgiTo();
							if(to.getAdmAppln() == null){
								String value="";
								if(to.getId()!=0){
									to1.setId(to.getId());
								}
								if(to.getTxnRefNo()!=null){
									to1.setTxnRefNo(to.getTxnRefNo());
								}
								if(to.getCandidateRefNo()!=null){
									to1.setCandidateRefNo(to.getCandidateRefNo());
								}
								if(to.getTxnAmount() != null){
									BigDecimal deci = to.getTxnAmount().multiply(new BigDecimal(100));
									to1.setAmount(deci.toString().substring(0, (deci.toString().length() - 3)));
								}
								if(to.getTxnDate() != null){
									date1=CommonUtil.formatSqlDate2((to.getTxnDate()).toString());
									to1.setDate(date1);
									}
								if(to.getRefundGenerated()==true){
								to1.setRefundGenerated("Yes");
								}else{
									to1.setRefundGenerated("No");
								}
								toList.add(to1);
								}
							}
						
						generateSettlementOrRefundPgiForm.setGenerateRefundPgiTo(toList);
						generateSettlementOrRefundPgiForm.setGenerateSettlementTo(null);
						}else{
						generateSettlementOrRefundPgiForm.setOpensettementLink(false);
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.norecords"));
						saveErrors(request, errors);
						generateSettlementOrRefundPgiForm.resetFields();
					}
				} catch (Exception exception) {
					String msg = super.handleApplicationException(exception);
					generateSettlementOrRefundPgiForm.setErrorMessage(msg);
					generateSettlementOrRefundPgiForm.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			} else {
				addErrors(request, errors);
				log.info("Exit GenerateSettlementOrRefundPgiAction - printChallan errors not empty ");
				return mapping.findForward(CMSConstants.Generate_Settlement_RefundForm);
			}
			
			log.info("Exit GenerateSettlementOrRefundPgiAction - printChallan");
			return mapping.findForward(CMSConstants.Generate_Settlement_RefundForm);
		 	
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward downloadFile(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		log.debug("inside GenerateSettlementOrRefundPgiAction in Action");
		GenerateSettlementOrRefundPgiForm generateSettlementOrRefundPgiForm = (GenerateSettlementOrRefundPgiForm) form;
		 setUserId(request,generateSettlementOrRefundPgiForm);
		 try {
			 List<GenerateRefundPgiTo> refundList=new ArrayList<GenerateRefundPgiTo>();
			 List<GenerateSettlementTo> settlementList=new ArrayList<GenerateSettlementTo>();
			 List<Integer> idList=new ArrayList<Integer>();
		 String s1="";
			String s2="";
			String s3="";
			String date1="";
			Date date=new Date();
			String stringDate = "";
			Calendar calendar = Calendar.getInstance();
			if (date != null) {
				calendar.setTime(date);
				String month=String.valueOf(calendar.get(Calendar.MONTH)+1);
				if(month.length()==1)
				{
					month="0"+month;
					
				}
				String dateNew=String.valueOf(calendar.get(Calendar.DATE));
				if(dateNew.length()==1)
				{
					dateNew="0"+dateNew;
					
				}
				String hour=String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
				if(hour.length()==1)
				{
					hour="0"+hour;
					
				}
				String min=String.valueOf(calendar.get(Calendar.MINUTE));
				if(min.length()==1)
				{
					min="0"+min;
					
				}
				String sec=String.valueOf(calendar.get(Calendar.SECOND));
				if(sec.length()==1)
				{
					sec="0"+sec;
					
				}
				stringDate = String.valueOf((calendar.get(Calendar.YEAR)+""+(month)+""+
						dateNew+""+hour+""+min+""+sec));
			}
			// Create file 
			String path = request.getRealPath("")+"/TempFiles/";
			FileWriter fstream = new FileWriter(path+"CHRISTUNIV_Settlement_"+stringDate+".txt");
			BufferedWriter out = new BufferedWriter(fstream);
			FileWriter fstream1 = new FileWriter(path+"CHRISTUNIV_Refund_"+stringDate+".txt");
			BufferedWriter out1 = new BufferedWriter(fstream1);
			if(generateSettlementOrRefundPgiForm.getGenerateRefundPgiTo()!=null && !generateSettlementOrRefundPgiForm.getGenerateRefundPgiTo().isEmpty()){
			Iterator itr=generateSettlementOrRefundPgiForm.getGenerateRefundPgiTo().iterator();
			while(itr.hasNext()) {
				GenerateRefundPgiTo to=(GenerateRefundPgiTo)itr.next();
				if(to!=null){
				if(to.getChecked1()!=null && to.getChecked1().equalsIgnoreCase("on")){
					to.setChecked1(null);
					idList.add(to.getId());
					s1=to.getTxnRefNo();
					s2=to.getCandidateRefNo();
					s3=to.getAmount();
					if(to.getDate() != null)
						date1=(to.getDate());
					out1.write(s1+","+date1+","+s2+","+s3+","+s3+"\r\n");
					generateSettlementOrRefundPgiForm.setRefundDownloadFile(true);
						}
					}
				refundList.add(to);
				}
			generateSettlementOrRefundPgiForm.setGenerateRefundPgiTo(refundList);
			GenerateSettlementOrRefundPgiHandler.getInstance().updateRefundFlag(idList,generateSettlementOrRefundPgiForm,"refund");
			}
			if(generateSettlementOrRefundPgiForm.getGenerateSettlementTo()!=null && !generateSettlementOrRefundPgiForm.getGenerateSettlementTo().isEmpty()){
			Iterator<GenerateSettlementTo> itr1=generateSettlementOrRefundPgiForm.getGenerateSettlementTo().iterator();
			while(itr1.hasNext()) {
				GenerateSettlementTo to=(GenerateSettlementTo)itr1.next();
				if(to!=null){
				if(to.getChecked1()!=null && to.getChecked1().equalsIgnoreCase("on")){
					to.setChecked1(null);
					idList.add(to.getId());
					s1=to.getTxnRefNo();
					s2=to.getCandidateRefNo();
					s3=to.getAmount();
					if(to.getDate() != null)
						date1=(to.getDate());
					out.write(s1+","+s2+","+s3+","+date1+"\r\n");
					generateSettlementOrRefundPgiForm.setSettlementDownloadFile(true);
						}
					}
				settlementList.add(to);
				}
			generateSettlementOrRefundPgiForm.setGenerateSettlementTo(settlementList);
			GenerateSettlementOrRefundPgiHandler.getInstance().updateRefundFlag(idList,generateSettlementOrRefundPgiForm,"settlement");
			}
			//Close the output stream
			out.close();	  
			out1.close();
			generateSettlementOrRefundPgiForm.setRefundFileName("CHRISTUNIV_Refund_"+stringDate+".txt");
			generateSettlementOrRefundPgiForm.setSettlementFileName("CHRISTUNIV_Settlement_"+stringDate+".txt");
		 }catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				generateSettlementOrRefundPgiForm.setErrorMessage(msg);
				generateSettlementOrRefundPgiForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		 
		 return mapping.findForward(CMSConstants.Generate_Settlement_RefundForm);
	}

}
