package com.kp.cms.actions.hostel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.VisitorInfoForm;
import com.kp.cms.handlers.hostel.VisitorInfoHandler;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.VisitorInfoTO;
import com.kp.cms.utilities.CommonUtil;

public class VisitorInfoAction extends BaseDispatchAction
{

    private static final Log log = LogFactory.getLog(VisitorInfoAction.class);
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward initVisitorInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        log.info("Entered initVisitorInfo in VisitorInfoAction");
        VisitorInfoForm visitorInfoForm = (VisitorInfoForm)form;
        visitorInfoForm.resetFields();
        getHostelEntries(request);
        log.info("Exit initVisitorInfo in VisitorInfoAction");
        return mapping.findForward(CMSConstants.INIT_VISITOR_INFO);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward getHostelerDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        log.info("Entered getHostelerDetails in VisitorInfoAction");
        VisitorInfoForm visitorInfoForm = (VisitorInfoForm)form;
         ActionErrors errors = visitorInfoForm.validate(mapping, request);
        validateForm(visitorInfoForm, errors);
        try
        {
        	 if(!errors.isEmpty())
             {
                 saveErrors(request, errors);
                 getHostelEntries(request);
                 return mapping.findForward(CMSConstants.INIT_VISITOR_INFO);
             }
             List<VisitorInfoTO> detailList= VisitorInfoHandler.getInstance().getHostelerDetails(visitorInfoForm);
             if(detailList.isEmpty())
             {
                 errors.add("error", new ActionError("knowledgepro.admission.norecordsfound"));
                 saveErrors(request, errors);
                 getHostelEntries(request);
                 return mapping.findForward(CMSConstants.INIT_VISITOR_INFO);
             }
            visitorInfoForm.setList(detailList);
        }
        catch(Exception exception)
        {
            String msg = super.handleApplicationException(exception);
            visitorInfoForm.setErrorMessage(msg);
            visitorInfoForm.setErrorStack(exception.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        log.info("Exit getHostelerDetails in VisitorInfoAction");
        return mapping.findForward(CMSConstants.VISITOR_PUT_DETAILS);
    }

    /**
     * @param visitorInfoForm
     * @param errors
     * @throws Exception
     */
    private void validateForm(VisitorInfoForm visitorInfoForm, ActionErrors errors)
        throws Exception
    {
        if((visitorInfoForm.getRegisterNo().trim() == null || StringUtils.isEmpty(visitorInfoForm.getRegisterNo().trim())) && (visitorInfoForm.getStaffId().trim() == null || StringUtils.isEmpty(visitorInfoForm.getStaffId().trim())) && (visitorInfoForm.getName().trim() == null || StringUtils.isEmpty(visitorInfoForm.getName().trim())) && errors.get("knowledgepro.hostel.visitor.registerNo.staff") != null && !errors.get("knowledgepro.hostel.visitor.registerNo.staff").hasNext())
        {
            errors.add("knowledgepro.hostel.visitor.registerNo.staff", new ActionError("knowledgepro.hostel.visitor.registerNo.staff"));
        }
        if((visitorInfoForm.getVisitorFor() == null || visitorInfoForm.getVisitorFor().isEmpty()) && errors.get("knowledgepro.hostel.visitor.required") != null && !errors.get("knowledgepro.hostel.visitor.required").hasNext())
        {
            errors.add("knowledgepro.hostel.visitor.required", new ActionError("knowledgepro.hostel.visitor.required"));
        }
        if(visitorInfoForm.getVisitorFor() != null)
        {
            if(visitorInfoForm.getVisitorFor().equals("1"))
            {
                if((visitorInfoForm.getRegisterNo().trim() == null || StringUtils.isEmpty(visitorInfoForm.getRegisterNo().trim())) && (visitorInfoForm.getName().trim() == null || StringUtils.isEmpty(visitorInfoForm.getName().trim())) && errors.get("knowledgepro.hostel.register.name.required") != null && !errors.get("knowledgepro.hostel.register.name.required").hasNext())
                {
                    errors.add("knowledgepro.hostel.register.name.required", new ActionError("knowledgepro.hostel.register.name.required"));
                }
            } else
            if(visitorInfoForm.getVisitorFor().equals("2") && (visitorInfoForm.getStaffId().trim() == null || StringUtils.isEmpty(visitorInfoForm.getStaffId().trim())) && (visitorInfoForm.getName().trim() == null || StringUtils.isEmpty(visitorInfoForm.getName().trim())) && errors.get("knowledgepro.hostel.staff.name.required") != null && !errors.get("knowledgepro.hostel.staff.name.required").hasNext())
            {
                errors.add("knowledgepro.hostel.staff.name.required", new ActionError("knowledgepro.hostel.staff.name.required"));
            }
        }
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward getSelectedMember(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        log.info("Entered submitDetails in VisitorInfoAction");
        VisitorInfoForm visitorInfoForm = (VisitorInfoForm)form;
        ActionErrors errors = new ActionErrors();
        try
        {
            List<VisitorInfoTO> visitorList = visitorInfoForm.getList();
            Iterator<VisitorInfoTO> Itr = visitorList.iterator();
            VisitorInfoTO vto = null;
            while(Itr.hasNext()) 
            {
                VisitorInfoTO visitorInfoTO = (VisitorInfoTO)Itr.next();
                if(visitorInfoTO.isSelected())
                {
                    vto = visitorInfoTO;
                }
            }
            if(vto==null){
            	errors.add("knowledgepro.hostel.visitor.select", new ActionError("knowledgepro.hostel.visitor.select"));
            	saveErrors(request, errors);
            	 return mapping.findForward(CMSConstants.VISITOR_PUT_DETAILS);
            }
            visitorInfoForm.setVto(vto);
        }
        catch(Exception exception)
        {
            String msg = super.handleApplicationException(exception);
            visitorInfoForm.setErrorMessage(msg);
            visitorInfoForm.setErrorStack(exception.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        log.info("Exit submitDetails in VisitorInfoAction");
        visitorInfoForm.clear();
        return mapping.findForward(CMSConstants.VISITOR_SUBMIT);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward submitDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        boolean isAdded;
        log.info("Entered submitDetails in VisitorInfoAction");
        VisitorInfoForm visitorInfoForm = (VisitorInfoForm)form;
         ActionErrors errors = visitorInfoForm.validate(mapping, request);
        ActionMessages messages = new ActionMessages();
        isAdded = false;
        validateDate(visitorInfoForm, errors);
        validateImageSize(visitorInfoForm, errors, request);
        validateFormSpecialCharacter(visitorInfoForm, errors, request);
        try
        {
        	if(!errors.isEmpty())
        	{
        		saveErrors(request, errors);
        		return mapping.findForward(CMSConstants.VISITOR_SUBMIT);
        	}
            if(errors.isEmpty())
	            {
                setUserId(request, visitorInfoForm);
                isAdded = VisitorInfoHandler.getInstance().submitVisitorDetails(visitorInfoForm);
	            if(isAdded)
	            {
	                messages.add("messages", new ActionMessage("knowledgepro.hostel.visitor.success"));
	                saveMessages(request, messages);
	            } else
	            {
	                errors.add("error", new ActionError("knowledgepro.hostel.visitor.failure"));
	                addErrors(request, errors);
	            }
	            }
        }
        catch(Exception exception)
        {
            String msg = super.handleApplicationException(exception);
            visitorInfoForm.setErrorMessage(msg);
            visitorInfoForm.setErrorStack(exception.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        log.info("Exit submitDetails in VisitorInfoAction");
        visitorInfoForm.resetFields();
        getHostelEntries(request);
        return mapping.findForward(CMSConstants.INIT_VISITOR_INFO);
    }

    /**
     * @param visitorInfoForm
     * @param errors
     * @throws Exception
     */
    private void validateDate(VisitorInfoForm visitorInfoForm, ActionErrors errors)
        throws Exception
    {
        if(visitorInfoForm.getDateIn() != null && !StringUtils.isEmpty(visitorInfoForm.getDateIn()) && !CommonUtil.isValidDate(visitorInfoForm.getDateIn()) && errors.get("knowledgepro.attendanceentry.dateinvalid") != null && !errors.get("knowledgepro.attendanceentry.dateinvalid").hasNext())
        {
            errors.add("knowledgepro.attendanceentry.dateinvalid", new ActionError("knowledgepro.attendanceentry.dateinvalid"));
        }
        if(visitorInfoForm.getDateOut() != null && !StringUtils.isEmpty(visitorInfoForm.getDateOut()) && !CommonUtil.isValidDate(visitorInfoForm.getDateOut()) && errors.get("knowledgepro.attendanceentry.dateinvalid") != null && !errors.get("knowledgepro.attendanceentry.dateinvalid").hasNext())
        {
            errors.add("knowledgepro.attendanceentry.dateinvalid", new ActionError("knowledgepro.attendanceentry.dateinvalid"));
        }
        if(CommonUtil.checkForEmpty(visitorInfoForm.getDateIn()) && CommonUtil.checkForEmpty(visitorInfoForm.getDateOut()) && CommonUtil.isValidDate(visitorInfoForm.getDateOut()) && CommonUtil.isValidDate(visitorInfoForm.getDateIn()))
        {
            java.util.Date startDate = CommonUtil.ConvertStringToDate(visitorInfoForm.getDateIn());
            java.util.Date endDate = CommonUtil.ConvertStringToDate(visitorInfoForm.getDateOut());
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(startDate);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(endDate);
            long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
            if(daysBetween <= 0L)
            {
                errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
            }
        }
    }

    /**
     * @param visitorInfoForm
     * @param errors
     * @param request
     * @return
     * @throws Exception
     */
    private ActionErrors validateImageSize(VisitorInfoForm visitorInfoForm, ActionErrors errors, HttpServletRequest request)
        throws Exception
    {
        log.info("Entering into validateImageSize of RomType Action");
        FormFile theFile = null;
        if(visitorInfoForm.getPhotoFile() != null)
        {
            theFile = visitorInfoForm.getPhotoFile();
        }
        InputStream propStream = VisitorInfoAction.class.getClassLoader().getResourceAsStream("resources/application.properties");
        int maXSize = 0;
        Properties prop = new Properties();
        try
        {
            prop.load(propStream);
            maXSize = Integer.parseInt(prop.getProperty("knowledgepro.upload.maxPhotoSize"));
        }
        catch(IOException e)
        {
            log.error("Error occured in validateImageSize RomType Action", e);
            throw new ApplicationException();
        }
        if(theFile != null && maXSize < theFile.getFileSize())
        {
            errors.add("error", new ActionError("admissionFormForm.attachment.maxPhotoSize"));
            addErrors(request, errors);
        }
        log.info("Leaving into validateImageSize of RomType Action");
        return errors;
    }
    /**
     * @param visitorInfoForm
     * @param errors
     * @param request
     * @return
     * @throws Exception
     */
    private void validateFormSpecialCharacter(VisitorInfoForm visitorInfoForm, ActionErrors errors, HttpServletRequest request)
        throws Exception{
            if(visitorInfoForm.getFirstName() != null && !StringUtils.isEmpty(visitorInfoForm.getFirstName()) && nameValidate(visitorInfoForm.getFirstName()))
            {
            	errors.add("error", new ActionError("knowledgepro.hostel.vistorinfo.specialCharacter","First Name"));
            }
            if(visitorInfoForm.getLastName() != null && !StringUtils.isEmpty(visitorInfoForm.getLastName()) && nameValidate(visitorInfoForm.getLastName()))
            {
            	errors.add("error", new ActionError("knowledgepro.hostel.vistorinfo.specialCharacter","Last Name"));
            }
            if(visitorInfoForm.getVisitorId() != null && !StringUtils.isEmpty(visitorInfoForm.getVisitorId()) && nameValidate(visitorInfoForm.getVisitorId()))
            {
            	errors.add("error", new ActionError("knowledgepro.hostel.vistorinfo.specialCharacter","Visitor Id"));
            }
            if(visitorInfoForm.getRelationShip() != null && !StringUtils.isEmpty(visitorInfoForm.getRelationShip()) && nameValidate(visitorInfoForm.getRelationShip()))
            {
            	errors.add("error", new ActionError("knowledgepro.hostel.vistorinfo.specialCharacter","RelationShip"));
            }
        }
    /**
	 * special character validation
	 * 
	 * @param name
	 * @return
	 */
	private boolean nameValidate(String name) {
		boolean result = false;
		Pattern pattern = Pattern.compile("[^A-Za-z0-9 \\. \\s \t \\/ \\( \\) ]+");

		Matcher matcher = pattern.matcher(name);
		result = matcher.find();
		return result;

	}
	
	public void getHostelEntries(HttpServletRequest request) throws Exception{
		log.debug("Entering getHostelEntries HostelCheckinAction");
		List<HostelTO> hostelList = VisitorInfoHandler.getInstance().getHostelDetails();
		request.setAttribute("hostelList", hostelList);
		log.debug("Exiting getHostelEntries of HostelCheckinAction ");
	}
}
