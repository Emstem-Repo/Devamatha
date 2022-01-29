package com.kp.cms.actions.reports;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.reports.AddressReportForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.reports.AddressReportHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.reports.StudentAddressTo;
import java.util.*;
import javax.servlet.http.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;

public class AddressReportAction extends BaseDispatchAction
{

    private static final Log log = LogFactory.getLog(AddressReportAction.class);

    
    public ActionForward initStudentAddressReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        log.info("entering into initStudentAddressReport. of AddressReportAction");
        AddressReportForm addressReportForm = (AddressReportForm)form;
        try
        {
            HttpSession session = request.getSession(false);
            session.removeAttribute("address");
            addressReportForm.resetFields();
            setRequiredDataToForm(addressReportForm, request);
        }
        catch(Exception e)
        {
            log.error("Error in initStudentAddressReport in AddressReportAction", e);
            String msg = super.handleApplicationException(e);
            addressReportForm.setErrorMessage(msg);
            addressReportForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        log.info("leaving into initStudentAddressReport. of AddressReportAction");
        return mapping.findForward("initAddressReport");
    }

    public ActionForward submitAddressReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into submitAddressReport. of submitAddressReportAction");	
		AddressReportForm addressForm = (AddressReportForm)form;
		
		HttpSession session = request.getSession(false);
		 ActionMessages errors = addressForm.validate(mapping, request);
		if(session.getAttribute("address")==null){
			try {
				if(errors.isEmpty()){
					List<StudentAddressTo> list = AddressReportHandler.getInstance().getAddressDetails(addressForm);
					if(list!=null && !list.isEmpty()){
						session.setAttribute("address",list);
						list.size();
					}
				}
				else{
					addErrors(request, errors);
					//Sets programType and program to formbean
				    setRequiredDataToForm(addressForm, request);
					return mapping.findForward(CMSConstants.STUDENT_ADDRESS_REPORT);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Error in submitAddressReport in AddressReportAction",e);
				String msg = super.handleApplicationException(e);
				addressForm.setErrorMessage(msg);
				addressForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}	
		log.info("Leaving into submitAddressReport. of AddressReportAction");
		return mapping.findForward("addressReportSubmit");
	}	
    @SuppressWarnings("unchecked")
	public ActionForward printAddressReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
    	log.info("entering into submitAddressReport. of submitAddressReportAction");	
		AddressReportForm addressReportForm = (AddressReportForm)form;
		HttpSession session = request.getSession(false);
		ActionMessages errors = new ActionErrors();
		try {
        if(errors.isEmpty())
        {
            if(session != null && !session.isNew() && session.getAttribute("address") != null)
            {
                List<StudentAddressTo> listOfStudentsAddr = (List<StudentAddressTo>)session.getAttribute("address");
                if(listOfStudentsAddr.size() != 0)
                {
                    listOfStudentsAddr = prepareListForPrint(listOfStudentsAddr);
                    addressReportForm.setStudentAddressList(listOfStudentsAddr);
                }
            }
           
        }
        else{
			addErrors(request, errors);
			//Sets programType and program to formbean
		    setRequiredDataToForm(addressReportForm, request);
			return mapping.findForward(CMSConstants.STUDENT_ADDRESS_REPORT);
		}
        
    } catch (Exception e) {
		log.error("Error in submitAddressReport in AddressReportAction",e);
		String msg = super.handleApplicationException(e);
		addressReportForm.setErrorMessage(msg);
		addressReportForm.setErrorStack(e.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	addressReportForm.setMethod(null);	
    log.info("Leaving into printAddressReport. of AddressReportAction");
        return mapping.findForward(CMSConstants.STUDENT_ADDRESS_REPORT_PRINT);
    }

    private List<StudentAddressTo> prepareListForPrint(List<StudentAddressTo> listOfStudentsAddr)
    {
        List<StudentAddressTo> finalList = new ArrayList<StudentAddressTo>();
        for(Iterator<StudentAddressTo> iterator = listOfStudentsAddr.iterator(); iterator.hasNext();)
        {
            StudentAddressTo studTO = (StudentAddressTo)iterator.next();
            StudentAddressTo studentTO = new StudentAddressTo();
            if(studTO != null)
            {
                StringBuilder sbuf1 = new StringBuilder();
                StringBuilder sbuf2 = new StringBuilder();
                StringBuilder sbuf3 = new StringBuilder();
                studentTO.setTotalContent1(studTO.getTotalContent1() == null || studTO.getTotalContent1().trim().length() <= 0 ? " " : studTO.getTotalContent1().replaceAll("<br/>", "\n"));
                studentTO.setTotalContent2(studTO.getTotalContent2() == null || studTO.getTotalContent2().trim().length() <= 0 ? " " : studTO.getTotalContent2().replaceAll("<br/>", "\n"));
                studentTO.setTotalContent3(studTO.getTotalContent3() == null || studTO.getTotalContent3().trim().length() <= 0 ? " " : studTO.getTotalContent3().replaceAll("<br/>", "\n"));
                sbuf2.append((new StringBuilder(String.valueOf(studentTO.getTotalContent2()))).append("\n").toString());
                sbuf1.append((new StringBuilder(String.valueOf(studentTO.getTotalContent1()))).append("\n").toString());
                sbuf3.append((new StringBuilder(String.valueOf(studentTO.getTotalContent3()))).append("\n").toString());
                studentTO.setTotalContent1(sbuf1.toString());
                studentTO.setTotalContent2(sbuf2.toString());
                studentTO.setTotalContent3(sbuf3.toString());
                finalList.add(studentTO);
            }
        }

        return finalList;
    }

    public void setRequiredDataToForm(AddressReportForm addressForm,HttpServletRequest request) throws Exception{
		log.info("entered setRequiredDataToForm. of AddressReportAction");	
		    //setting programTypeList to Request
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			request.setAttribute("programTypeList", programTypeList);
			
			log.info("Exit setRequiredDataToForm.AddressReport Action");	
	}

}
