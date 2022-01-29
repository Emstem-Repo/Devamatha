package com.kp.cms.actions.pettycash;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.pettycash.PettyCashAccountHeadsForm;
import com.kp.cms.handlers.pettycash.PettyCashAccountHeadsReportHandler;
import com.kp.cms.to.pettycash.PettyCashAccountHeadsTO;

public class PettyCashAccountHeadsReportAction extends BaseDispatchAction {
	public static Log log = LogFactory.getLog(PettyCashAccountHeadsReportAction.class);
	private PettyCashAccountHeadsReportHandler pcAccountHeadsReportHandler=PettyCashAccountHeadsReportHandler.getInstance();
	
	
	public ActionForward getAllAccountHeadsReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("entering into getAllAccountHeadsReport method in PettyCashAccountHeadsReportAction class..");
			PettyCashAccountHeadsForm pcAccountHeadsForm = (PettyCashAccountHeadsForm)form;
			try
			{
				if(request.getSession().getAttribute("pcAccountHeadsListTO")!=null)
				{
					request.getSession().removeAttribute("pcAccountHeadsListTO");
				}
				setListToRequest(request,pcAccountHeadsForm);
			}
			catch(Exception e)
			{
				String msg = super.handleApplicationException(e);
				pcAccountHeadsForm.setErrorMessage(msg);
				pcAccountHeadsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
			if(request.getParameter("print")!=null)
			{
				if(request.getParameter("print").equals("print"))
				{
				
					return mapping.findForward(CMSConstants.GET_ALL_ACCHEADS_PRINT_REPORT);
				}
			}
			log.info("leaving from getAllAccountHeadsReport method in PettyCashAccountHeadsReportAction class..");
			return mapping.findForward(CMSConstants.GET_ALL_ACCHEADS_REPORT);
			
			
	}
	
	public void setListToRequest(HttpServletRequest request,ActionForm accountForm)throws Exception
	{
		
		List<PettyCashAccountHeadsTO>  pcAccountHeadsListTO=pcAccountHeadsReportHandler.getAccountHeadsReportList();
		
		if(pcAccountHeadsListTO!=null && !pcAccountHeadsListTO.isEmpty())
		{
			HttpSession session=request.getSession(false);
			session.setAttribute("pcAccountHeadsListTO", pcAccountHeadsListTO);
			
		}
	}
	
	

}
