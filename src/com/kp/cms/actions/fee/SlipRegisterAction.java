package com.kp.cms.actions.fee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.fee.SlipRegisterForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.fee.FeeDivisionHandler;
import com.kp.cms.handlers.fee.SlipRegisterHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.fee.FeeDivisionTO;
import com.kp.cms.to.fee.SlipRegisterTO;

public class SlipRegisterAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(SlipRegisterAction.class);

	public ActionForward initSlipRegister(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		log.info("entering into initSlipRegister of SlipRegisterAction class.");
		System.out.println("hi123342442323");
		SlipRegisterForm slipRegisterForm = (SlipRegisterForm) form;
		slipRegisterForm.resetFields();
		setRequiredDataToForm(slipRegisterForm, request);
		HttpSession session = request.getSession(false);
		session.removeAttribute("slipRegisterReport");
		log.info("exit of initSlipRegister of SlipRegisterAction class.");
		//return mapping.findForward(CMSConstants.INIT_SLIP_REGISTER);
	
		return mapping.findForward("slipRegister");
	}
	
	public ActionForward slipRegisterRecords(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into slipRegisterRecords ofslipRegisterAction class.");
		SlipRegisterForm slipRegisterForm = (SlipRegisterForm) form;
		
		HttpSession session = request.getSession(false);
		if(session.getAttribute("slipRegisterReport")==null){
			try {
				ActionErrors errors = new ActionErrors();
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				errors =slipRegisterForm.validate(mapping, request);
				if (errors.isEmpty()) {
					List<SlipRegisterTO> slipRegisterReport = SlipRegisterHandler.getInstance().getSlipRegisterTOList(slipRegisterForm);
					
					if(slipRegisterReport.isEmpty() ) {
						message = new ActionMessage("knowledgepro.norecords");
						messages.add("messages", message);
						saveMessages(request, messages);
						setRequiredDataToForm(slipRegisterForm, request);
						log.info("exit of slipRegisterRecords of slipRegisterAction class.");
						//return mapping.findForward(CMSConstants.INIT_SLIP_REGISTER);
						
						return mapping.findForward("slipRegister");
					} else {
						session.setAttribute("slipRegisterReport", slipRegisterReport);
						log.info("exit of slipRegisterRecords of AslipRegisterAction class.");
					}
					
				} else {
					addErrors(request, errors);
					setRequiredDataToForm(slipRegisterForm, request);
					   //return mapping.findForward(CMSConstants.INIT_SLIP_REGISTER);
					return mapping.findForward("slipRegister");
				}
			
			} catch (Exception e) {
				
				log.error("Error while initializing slipRegisterRcords"+e.getMessage());
				String msg = super.handleApplicationException(e);
				slipRegisterForm.setErrorMessage(msg);
				slipRegisterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		return mapping.findForward("");
	}
	
	
	
	public void setRequiredDataToForm(SlipRegisterForm slipRegisterForm,HttpServletRequest request) throws Exception {
	
		
		log.info("entering into setRequiredDataToForm of slipRegisterActionclass.");
		// setting programList to Request
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		request.setAttribute("programTypeList", programTypeList);
		
		List<FeeDivisionTO> feeDivisionList = FeeDivisionHandler.getInstance().getFeeDivisionList();
		request.setAttribute("feeDivisionList", feeDivisionList);
		
		slipRegisterForm.setFeeDivList(feeDivisionList);
	//	Map<Integer,String> feeDivisionMap = FeeDivisionHandler.getInstance().getFeeDivisionMap();
	//	request.setAttribute("feeDivisionMap", feeDivisionMap);		
		// Getting the class map current academic year.
     	//	Map<Integer, String> classMap = setpClassMapToRequest();
		//attendanceSummaryReportForm.setClassMap(classMap);

		log.info("exit of setRequiredDataToForm of slipRegisterActionclass");
	}
	

}
