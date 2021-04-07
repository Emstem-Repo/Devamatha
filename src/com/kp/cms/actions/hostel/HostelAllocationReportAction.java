package com.kp.cms.actions.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.hostel.HostelAllocationReportForm;
import com.kp.cms.handlers.hostel.HostelAllocationReportHandler;
import com.kp.cms.to.hostel.HostelAllocationReportTO;
import com.kp.cms.utilities.CommonUtil;

public class HostelAllocationReportAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(HostelAllocationReportAction.class);
	
	public ActionForward initAllocationReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		log.debug("Entering initAllocation of HostelAllocationReportAction");
		HostelAllocationReportForm hostelAllocationReportForm=(HostelAllocationReportForm)form;
		hostelAllocationReportForm.resetFields(mapping, request);
		log.debug("Exiting initAllocation of HostelAllocationReportAction");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_ALLOCATION_REPORT);
	}
	
	public ActionForward submitAllocationReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
	

				log.info("entering into submitAllocationReport. of submitAllocationReport");	
				HostelAllocationReportForm hostelAllocationReportForm=(HostelAllocationReportForm)form;
				//HttpSession session = request.getSession(false);
				ActionMessages errors = new ActionErrors();
				
			
				if(validateAttendanceDate(hostelAllocationReportForm, errors))
				{
					errors = hostelAllocationReportForm.validate(mapping, request);	
				}
					try {
						if(errors.isEmpty()){
							
							List<HostelAllocationReportTO> applicantHostelDetailsTO  =HostelAllocationReportHandler.getInstance().getAllocationDetails(hostelAllocationReportForm,request);
							if(applicantHostelDetailsTO.size()>0)
							{	
								hostelAllocationReportForm.setAllocationList(applicantHostelDetailsTO);
									return mapping.findForward(CMSConstants.HOSTEL_ALLOCATION_REPORT_RESULTS);
							}
							else{
								errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
								saveErrors(request, errors);
								hostelAllocationReportForm.resetFields(mapping, request);
								return mapping.findForward(CMSConstants.INIT_HOSTEL_ALLOCATION_REPORT);	
							}
						}
						else{
							addErrors(request, errors);
							//Sets programType and program to form bean
							return mapping.findForward(CMSConstants.INIT_HOSTEL_ALLOCATION_REPORT);
						}
						}
					catch (Exception e) {
						e.printStackTrace();
						log.error("Error in showRequisitions in submitAllocationReport",e);
						//String msg = super.handleApplicationException(e);
						return mapping.findForward(CMSConstants.ERROR_PAGE);
					}
				
								
	}
	private boolean validateAttendanceDate(HostelAllocationReportForm hostelAllocationReportForm, ActionMessages errors) {
		if(hostelAllocationReportForm.getFromDate()!=null && !StringUtils.isEmpty(hostelAllocationReportForm.getFromDate())&& !CommonUtil.isValidDate(hostelAllocationReportForm.getFromDate())){
			errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.ATTANDANCE_REPORT_STARTDATE_INVALID));
		}
		if(hostelAllocationReportForm.getToDate()!=null && !StringUtils.isEmpty(hostelAllocationReportForm.getToDate())&& !CommonUtil.isValidDate(hostelAllocationReportForm.getToDate())){
			errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.ATTANDANCE_REPORT_ENDDATE_INVALID));
		}
		if(errors.size()>0)
		{return false;
			}
		else 
		return true;	
		}
	
	
	
	
}
