package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.itextpdf.text.pdf.codec.TiffWriter.FieldShort;
import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.employee.GuestFaculty;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.ExamValuationRemuPaymentForm;
import com.kp.cms.handlers.exam.ExamValuationRemuPaymentHandler;
import com.kp.cms.to.exam.ExamValuationRemuPaymentTo;
import com.mysql.jdbc.Field;

public class ExamValuationRemuPaymentAction extends BaseDispatchAction{
	ExamValuationRemuPaymentHandler examValuationRemuPaymentHandler=ExamValuationRemuPaymentHandler.getInstance();
	/**
	 * initial page for student
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initValuationRemuPayment(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		//ExamValuationRemuPaymentForm examValuationRemuPaymentForm = (ExamValuationRemuPaymentForm) form;
		return mapping.findForward("initExamValuationRemuPayment");
	}
	/**
	 * search the valuators list
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchTheValuatorsList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamValuationRemuPaymentForm examValuationRemuPaymentForm = (ExamValuationRemuPaymentForm) form;
		ActionErrors errors=new ActionErrors();
		try{
			if(examValuationRemuPaymentForm.getValuatorsType()!=null && !examValuationRemuPaymentForm.getValuatorsType().isEmpty()){
			List<ExamValuationRemuPaymentTo> list=examValuationRemuPaymentHandler.searchTheValuatorsList(examValuationRemuPaymentForm);
				if(list!=null && !list.isEmpty()){
					if(examValuationRemuPaymentForm.getPaidStauts().equalsIgnoreCase("paid")){
						request.setAttribute("paidStatus", "paid");
					}
					examValuationRemuPaymentForm.setList(list);
					return mapping.findForward("examvaluatorsList");
				}else{
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.norecords"));
					saveErrors(request, errors);
				}
			}else{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.remuniration.payment.valuatortype.rquired"));
				saveErrors(request, errors);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			examValuationRemuPaymentForm.setErrorMessage(msg);
			examValuationRemuPaymentForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("initExamValuationRemuPayment");
	}
	/**
	 * display valuators details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward displayValuatorDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamValuationRemuPaymentForm examValuationRemuPaymentForm = (ExamValuationRemuPaymentForm) form;
		reset(examValuationRemuPaymentForm);
		try{
			nullifyTheFields(examValuationRemuPaymentForm);
			setFormFields(examValuationRemuPaymentForm);
			examValuationRemuPaymentHandler.displayValuatorDetails(examValuationRemuPaymentForm);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			examValuationRemuPaymentForm.setErrorMessage(msg);
			examValuationRemuPaymentForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		if(examValuationRemuPaymentForm.getOtherEmpId()!=null){
			return mapping.findForward("examExternalValuatorsDetails");
		}else{
			return mapping.findForward("examInternalValuatorsDetails");
		}
	}
	private void nullifyTheFields(
			ExamValuationRemuPaymentForm examValuationRemuPaymentForm) throws Exception{
		examValuationRemuPaymentForm.setTa(null);
		examValuationRemuPaymentForm.setDa(null);
		examValuationRemuPaymentForm.setTotalNoOfConveyance(null); 
		examValuationRemuPaymentForm.setTotalBoardMeetings(null); 
		examValuationRemuPaymentForm.setBoardMeeetingRate(null); 
		examValuationRemuPaymentForm.setOtherCost(null); 
		examValuationRemuPaymentForm.setOtherEmpId(null); 
		examValuationRemuPaymentForm.setDepartmentName(null); 
		examValuationRemuPaymentForm.setAccountNo(null); 
		examValuationRemuPaymentForm.setPanNo(null); 
		examValuationRemuPaymentForm.setCurrentDate(null); 
		examValuationRemuPaymentForm.setEmployeeName(null); 
		examValuationRemuPaymentForm.setBankName(null);
		examValuationRemuPaymentForm.setBankIfscCode(null);
		examValuationRemuPaymentForm.setAddress(null);
	}
	private void setFormFields(
			ExamValuationRemuPaymentForm examValuationRemuPaymentForm) throws Exception{
		Map<Integer,ExamValuationRemuPaymentTo> map=examValuationRemuPaymentForm.getTosMap();
		ExamValuationRemuPaymentTo examValuationRemuPaymentTo=map.get(examValuationRemuPaymentForm.getId());
		
		// added by dilip
		if(examValuationRemuPaymentTo.getOtherEmpId()==null){
			GuestFaculty guestFaculty =  examValuationRemuPaymentHandler.getApplicantDetails(examValuationRemuPaymentTo.getName());
			if(guestFaculty!=null){
				examValuationRemuPaymentForm.setDisplayGuest("Guest");
				
				if(guestFaculty.getDepartment()!=null)
					examValuationRemuPaymentForm.setDepartmentName(guestFaculty.getDepartment().getName());
				if(guestFaculty.getBankBranch()!=null)
					examValuationRemuPaymentForm.setBankName(guestFaculty.getBankBranch());
				if(guestFaculty.getBankIfscCode()!=null)
					examValuationRemuPaymentForm.setBankIfscCode(guestFaculty.getBankIfscCode());
				if(guestFaculty.getPanNo()!=null)
					examValuationRemuPaymentForm.setPanNo(guestFaculty.getPanNo());
				StringBuilder addr = new StringBuilder();
				if(guestFaculty.getCommunicationAddressLine1()!=null && !guestFaculty.getCommunicationAddressLine1().isEmpty())
					addr = addr.append(guestFaculty.getCommunicationAddressLine1());
				addr = addr.append(",");
				if(guestFaculty.getCommunicationAddressLine2()!=null)
					addr = addr.append(guestFaculty.getCommunicationAddressLine2());
				addr = addr.append(",");
				if(guestFaculty.getCommunicationAddressCity()!=null)
					addr = addr.append(guestFaculty.getCommunicationAddressCity());
				addr = addr.append(",");
				if(guestFaculty.getStateByCommunicationAddressStateId()!=null)
					addr = addr.append(guestFaculty.getStateByCommunicationAddressStateId().getName());
				addr = addr.append(",");
				if(guestFaculty.getCountryByCommunicationAddressCountryId()!=null)
					addr = addr.append(guestFaculty.getCountryByCommunicationAddressCountryId().getName());
				addr = addr.append("-");
				if(guestFaculty.getCommunicationAddressZip()!=null)
					addr = addr.append(guestFaculty.getCommunicationAddressZip());
				examValuationRemuPaymentForm.setAddress(addr.toString());
			}else{
				examValuationRemuPaymentForm.setDisplayGuest("User");
			}
		}
		// add end by dilip
		
		if(examValuationRemuPaymentTo.getTa()!=null){
			examValuationRemuPaymentForm.setTa(examValuationRemuPaymentTo.getTa());
		}
		if(examValuationRemuPaymentTo.getDa()!=null){
			examValuationRemuPaymentForm.setDa(examValuationRemuPaymentTo.getDa());
		} 
		if(examValuationRemuPaymentTo.getTotalBoardMeetings()!=null){
			examValuationRemuPaymentForm.setTotalNoOfConveyance(examValuationRemuPaymentTo.getTotalBoardMeetings()); 
		} 
		if(examValuationRemuPaymentTo.getTotalBoardMeetings()!=null){
			examValuationRemuPaymentForm.setTotalBoardMeetings(examValuationRemuPaymentTo.getTotalBoardMeetings()); 
		} 
		if(examValuationRemuPaymentTo.getBoardMeeetingRate()!=null){
			examValuationRemuPaymentForm.setBoardMeeetingRate(examValuationRemuPaymentTo.getBoardMeeetingRate()); 
		} 
		if(examValuationRemuPaymentTo.getOtherCost()!=null){
			examValuationRemuPaymentForm.setOtherCost(examValuationRemuPaymentTo.getOtherCost()); 
		} 
		if(examValuationRemuPaymentTo.getOtherEmpId()!=null){
			examValuationRemuPaymentForm.setOtherEmpId(examValuationRemuPaymentTo.getOtherEmpId()); 
			if(examValuationRemuPaymentTo.getBankName()!=null){
				examValuationRemuPaymentForm.setBankName(examValuationRemuPaymentTo.getBankName());
			}
			if(examValuationRemuPaymentTo.getIfscCode()!=null){
				examValuationRemuPaymentForm.setBankIfscCode(examValuationRemuPaymentTo.getIfscCode());
			}
			if(examValuationRemuPaymentTo.getAddress()!=null){
				examValuationRemuPaymentForm.setAddress(examValuationRemuPaymentTo.getAddress());
			}
		} 
		if(examValuationRemuPaymentTo.getDepartment()!=null){
			examValuationRemuPaymentForm.setDepartmentName(examValuationRemuPaymentTo.getDepartment()); 
		} 
		if(examValuationRemuPaymentTo.getAccountNo()!=null){
			examValuationRemuPaymentForm.setAccountNo(examValuationRemuPaymentTo.getAccountNo()); 
		} 
		if(examValuationRemuPaymentTo.getDate()!=null){
			examValuationRemuPaymentForm.setCurrentDate(examValuationRemuPaymentTo.getDate()); 
		} 
		if(examValuationRemuPaymentTo.getName()!=null){
			examValuationRemuPaymentForm.setEmployeeName(examValuationRemuPaymentTo.getName()); 
		} 
		if(examValuationRemuPaymentTo.getPanNo()!=null){
			examValuationRemuPaymentForm.setPanNo(examValuationRemuPaymentTo.getPanNo()); 
		}
		examValuationRemuPaymentForm.setChallanNo(String.valueOf(examValuationRemuPaymentTo.getVocherNo()));
		
	}
	private void reset(ExamValuationRemuPaymentForm examValuationRemuPaymentForm) throws Exception{
		examValuationRemuPaymentForm.setNoOfBoardMeetings(null);
		examValuationRemuPaymentForm.setTotalBoardMeetingRate(null);
		examValuationRemuPaymentForm.setViewFields(false);
		examValuationRemuPaymentForm.setAnyOther(null);
		examValuationRemuPaymentForm.setTotalScripts(0);
		examValuationRemuPaymentForm.setTotalScriptsAmount(null);
		examValuationRemuPaymentForm.setTotalBoardMeetingCharge(null);
		examValuationRemuPaymentForm.setTotalConveyanceCharge(null);
		examValuationRemuPaymentForm.setConveyanceCharge(null);
		examValuationRemuPaymentForm.setGrandTotal(null);
		examValuationRemuPaymentForm.setMap(null);
		examValuationRemuPaymentForm.setChallanNo(null);
		examValuationRemuPaymentForm.setPrintTa(false);
		examValuationRemuPaymentForm.setPrintDa(false);
		examValuationRemuPaymentForm.setPrintOther(false);
		examValuationRemuPaymentForm.setPrintconveyence(false);
		examValuationRemuPaymentForm.setTotalInWords(null);
	}
	/**
	 * for selected students the keys are updated in sap registration table
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updatePaidAndModeOfPayment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamValuationRemuPaymentForm examValuationRemuPaymentForm = (ExamValuationRemuPaymentForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		try{
				List<ExamValuationRemuPaymentTo> list=new ArrayList<ExamValuationRemuPaymentTo>();
				List<ExamValuationRemuPaymentTo> list2=new ArrayList<ExamValuationRemuPaymentTo>();
				List<Integer> integers=new ArrayList<Integer>();
				List<ExamValuationRemuPaymentTo> examValuationRemuPaymentTos=examValuationRemuPaymentForm.getList();
				Iterator<ExamValuationRemuPaymentTo> iterator=examValuationRemuPaymentTos.iterator();
				while (iterator.hasNext()) {
					ExamValuationRemuPaymentTo examValuationRemuPaymentTo = (ExamValuationRemuPaymentTo) iterator.next();
					if(examValuationRemuPaymentTo.getChecked()!=null && !examValuationRemuPaymentTo.getChecked().isEmpty() && examValuationRemuPaymentTo.getChecked().equalsIgnoreCase("on")){
						list.add(examValuationRemuPaymentTo);
						integers.add(examValuationRemuPaymentTo.getId());
						examValuationRemuPaymentTo.setChecked(null);
					}else{
						list2.add(examValuationRemuPaymentTo);
					}
				}
				setUserId(request, examValuationRemuPaymentForm); 
				if(list!=null && !list.isEmpty()){
					boolean flag=examValuationRemuPaymentHandler.updatePaidAndModeOfPayment(list,examValuationRemuPaymentForm.getUserId(),integers);
					if(flag){
						examValuationRemuPaymentForm.setList(null);
						examValuationRemuPaymentForm.setList(list2);
						ActionMessage message = new ActionMessage("knowledgepro.exam.attendanceMarks.updated");
						messages.add("messages", message);
						saveMessages(request, messages);
					}else{
						errors.add("error", new ActionError("knowledgepro.exam.ExamMarksEntry.Students.Fail"));
						saveErrors(request, errors);
					}
					if(list2==null && list2.isEmpty()){
						errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.norecords"));
						saveErrors(request, errors);
						return mapping.findForward("initExamValuationRemuPayment");
					}
				}else{
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.remuniration.payment.select.records.update"));
					saveErrors(request, errors);
				}
		}catch (Exception e) {
			e.printStackTrace();
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				examValuationRemuPaymentForm.setErrorMessage(msg);
				examValuationRemuPaymentForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward("examvaluatorsList");
	}
}
