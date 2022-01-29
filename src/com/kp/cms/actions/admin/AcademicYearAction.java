package com.kp.cms.actions.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.AcademicYear;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.AcademicYearForm;

import com.kp.cms.handlers.admin.AcademicYearHandler;

import com.kp.cms.to.admin.AcademicYearTO;
@SuppressWarnings("deprecation")
public class AcademicYearAction  extends BaseDispatchAction{
	
	private static final Logger log = Logger.getLogger(AcademicYearAction.class);

     public ActionForward initAcademicYearEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        AcademicYearForm academicYearForm = (AcademicYearForm)form;
        try
        {
            assignAcademicYearListToForm(academicYearForm);
        }
        catch(Exception e)
        {
            String msg = super.handleApplicationException(e);
            academicYearForm.setErrorMessage(msg);
            academicYearForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        log.info("End of initAcademicYearYearEntry in AcademicYearAction class");
        return mapping.findForward(CMSConstants.INIT_ACADEMIC_YEAR);
    }

    public ActionForward addAcademicYearEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        AcademicYearForm academicYearForm = (AcademicYearForm)form;
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = academicYearForm.validate(mapping, request);
        boolean isAcademicYearAdded = false;
        try
        {
            if(errors.isEmpty())
            {
                String academicYearValue = null;
                setUserId(request, academicYearForm);
                academicYearValue = academicYearForm.getAcademicYear();
                AcademicYearTO academicYrTO = new AcademicYearTO();
                academicYrTO.setYear(academicYearValue);
                academicYrTO.setIsCurrent(academicYearForm.getIsCurrent());
                academicYrTO.setId(academicYearForm.getId());
                academicYrTO.setAcademicYearSel(academicYearForm.getAcademicYearSel());
                academicYrTO.setCreatedBy(academicYearForm.getUserId());
                academicYrTO.setModifiedBy(academicYearForm.getUserId());
                //code added by sudhir
                academicYrTO.setIsCurrentForAdmission(academicYearForm.getIsCurrentForAdmission());
                //
                AcademicYear academicYear = AcademicYearHandler.getInstance().getAcademicYear(academicYearValue);
                
                if(academicYear != null)
                {
                    if(academicYear.getIsActive())
                    {
                        errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_ACADEMIC_YEAR_EXISTS));
                        assignAcademicYearListToForm(academicYearForm);
                        academicYearForm.clear();
                        saveErrors(request, errors);
                    } else
                    if(!academicYear.getIsActive())
                    {
                        errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_ACADEMIC_YEAR_REACTIVATE));
                        assignAcademicYearListToForm(academicYearForm);
                        saveErrors(request, errors);
                    }
                } else
                {
                    isAcademicYearAdded = AcademicYearHandler.getInstance().addAcademicYear(academicYrTO, errors);
                    if(isAcademicYearAdded)
                    {
                        messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ADMIN_ACADEMIC_YEAR_ADD_SUCCESS));
                        saveMessages(request, messages);
                        assignAcademicYearListToForm(academicYearForm);
                        academicYearForm.clear();
                    } else
                    {
                        errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_ACADEMIC_YEAR_ADD_FAILED));
                        assignAcademicYearListToForm(academicYearForm);
                        saveErrors(request, errors);
                    }
                }
            } else
            {
                assignAcademicYearListToForm(academicYearForm);
                saveErrors(request, errors);
            }
        }
        catch(Exception e)
        {
            if(e instanceof BusinessException)
            {
                addErrors(request, errors);
                assignAcademicYearListToForm(academicYearForm);
                return mapping.findForward(CMSConstants.INIT_ACADEMIC_YEAR);
            } else
            {
                String msg = super.handleApplicationException(e);
                academicYearForm.setErrorMessage(msg);
                academicYearForm.setErrorStack(e.getMessage());
                return mapping.findForward(CMSConstants.ERROR_PAGE);
            }
        }
        log.info("End of addAcademicYearEntry in AcademicYearAction class");
        return mapping.findForward(CMSConstants.INIT_ACADEMIC_YEAR);
    }

    public ActionForward editAcademicYearDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        AcademicYearForm academicYearForm = (AcademicYearForm)form;
        try
        {
            int yearId = academicYearForm.getId();
            AcademicYearTO academicYrTO = AcademicYearHandler.getInstance().getAcademicYearDetailsWithId(yearId);
            if(academicYrTO != null)
            {
                academicYearForm.setIsCurrent(academicYrTO.getIsCurrent());
                academicYearForm.setAcademicYearSel(academicYrTO.getYear());
                //code is added by sudhir
                academicYearForm.setIsCurrentForAdmission(academicYrTO.getIsCurrentForAdmission());
                //
                String previousYear = academicYrTO.getYear();
                HttpSession session = request.getSession(true);
                session.setAttribute("previousYear", previousYear);
            }
            assignAcademicYearListToForm(academicYearForm);
            request.setAttribute(CMSConstants.OPERATION,
					CMSConstants.EDIT_OPERATION);
        }
        catch(Exception e)
        {
            String msg = super.handleApplicationException(e);
            academicYearForm.setErrorMessage(msg);
            academicYearForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        log.info("End of editAcademicYearDetails in AcademicYearAction class");
        return mapping.findForward(CMSConstants.INIT_ACADEMIC_YEAR);
    }


	public ActionForward updateAcademicYearDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AcademicYearForm academicYearForm = (AcademicYearForm)form;
		ActionErrors errors = academicYearForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();

		try {
			if (isCancelled(request)) {
				int yearId = academicYearForm.getId();
				AcademicYearTO academicYearTO = AcademicYearHandler
						.getInstance().getAcademicYearDetailsWithId(yearId);
				if (academicYearTO != null) {
					academicYearForm.setIsCurrent(academicYearTO
							.getIsCurrent());
					academicYearForm
							.setAcademicYear(academicYearTO.getYear());
					//code added by sudhir	
					academicYearForm.setIsCurrentForAdmission(academicYearTO.getIsCurrentForAdmission());
					//
				}
				assignAcademicYearListToForm(academicYearForm);
				request.setAttribute(CMSConstants.OPERATION,
						CMSConstants.EDIT_OPERATION);
				return mapping
						.findForward(CMSConstants.INIT_ACADEMIC_YEAR);
			} else if (errors.isEmpty()) {
				setUserId(request, academicYearForm);
				HttpSession session = request.getSession(false);
				String prevoiusYear = String.valueOf(session
						.getAttribute("previousYear"));
				String currentYear = academicYearForm
						.getAcademicYear();
				boolean isUpdated;
				if (!prevoiusYear.equals(currentYear)) {
					AcademicYear academicYear = AcademicYearHandler
							.getInstance().getAcademicYear(currentYear);

					if (academicYear != null) {
						if (academicYear.getIsActive()) {
							errors.add(CMSConstants.ERROR, new ActionError(
									CMSConstants.ADMIN_ACADEMIC_YEAR_EXISTS));
							saveErrors(request, errors);
							request.setAttribute(CMSConstants.OPERATION,
									CMSConstants.EDIT_OPERATION);
							assignAcademicYearListToForm(academicYearForm);
						}
						if (!academicYear.getIsActive()) {
							errors
									.add(
											"error",
											new ActionError(
													CMSConstants.ADMIN_ACADEMIC_YEAR_REACTIVATE));
							saveErrors(request, errors);
							assignAcademicYearListToForm(academicYearForm);
						}
					} else {
						isUpdated = AcademicYearHandler.getInstance()
								.updateAcademicYear(academicYearForm,errors);
						if (isUpdated) {
							messages
									.add(
											CMSConstants.MESSAGES,
											new ActionMessage(
													CMSConstants.ADMIN_ACADEMIC_YEAR_UPDATE_SUCCESS));
							saveMessages(request, messages);
							academicYearForm.clear();
							assignAcademicYearListToForm(academicYearForm);
						}
						if (!isUpdated) {
							errors
									.add(
											CMSConstants.ERROR,
											new ActionError(
													CMSConstants.ADMIN_ACADEMIC_YEAR_UPDATE_FAILED));
							saveErrors(request, errors);
							academicYearForm.clear();
							assignAcademicYearListToForm(academicYearForm);
						}
					}
				} else {
					isUpdated = AcademicYearHandler.getInstance()
							.updateAcademicYear(academicYearForm,errors);
					if (isUpdated) {
						messages
								.add(
										CMSConstants.MESSAGES,
										new ActionMessage(
												CMSConstants.ADMIN_ACADEMIC_YEAR_UPDATE_SUCCESS));
						saveMessages(request, messages);
						academicYearForm.clear();
						assignAcademicYearListToForm(academicYearForm);
					} else {
						errors.add(CMSConstants.ERROR, new ActionError(
								CMSConstants.ADMIN_ACADEMIC_YEAR_UPDATE_FAILED));
						saveErrors(request, errors);
						academicYearForm.clear();
						assignAcademicYearListToForm(academicYearForm);
					}
				}
			} else {
				request.setAttribute(CMSConstants.OPERATION,
						CMSConstants.EDIT_OPERATION);
				saveErrors(request, errors);
				assignAcademicYearListToForm(academicYearForm);
			}
		} catch (Exception e) {			
			if (e instanceof BusinessException) {
				addErrors(request, errors);
				assignAcademicYearListToForm(academicYearForm);
				return mapping.findForward(CMSConstants.INIT_ACADEMIC_YEAR);
			} 
			else{
				String msg = super.handleApplicationException(e);
				academicYearForm.setErrorMessage(msg);
				academicYearForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}		
		}
		log
		.info("End of updateAcademicYearDetails in AcademicYearAction class");
		return mapping.findForward(CMSConstants.INIT_ACADEMIC_YEAR);
	}

  
	public ActionForward deleteAcademicYearDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        AcademicYearForm academicYearForm = (AcademicYearForm)form;
        ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        try
        {
            setUserId(request, academicYearForm);
            String userId = academicYearForm.getUserId();
            int yearId = academicYearForm.getId();
            boolean isDeleted = AcademicYearHandler.getInstance().deleteAcademicYearDetails(yearId, userId);
            if(isDeleted)
            {
                messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ADMIN_ACADEMIC_YEAR_DELETE_SUCCESS));
                saveMessages(request, messages);
                assignAcademicYearListToForm(academicYearForm);
                academicYearForm.clear();
            } else
            {
                errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_ACADEMIC_YEAR_DELETE_FAILED));
                saveErrors(request, errors);
                assignAcademicYearListToForm(academicYearForm);
            }
        }
        catch(Exception e)
        {
            String msg = super.handleApplicationException(e);
            academicYearForm.setErrorMessage(msg);
            academicYearForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        log.info("End of deleteAcademicYearDetails in AcademicYearAction class");
        return mapping.findForward(CMSConstants.INIT_ACADEMIC_YEAR);
    }

    public ActionForward reActivateAcademicYear(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        AcademicYearForm academicYearForm = (AcademicYearForm)form;
        ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        try
        {
            setUserId(request, academicYearForm);
            String userId = academicYearForm.getUserId();
            String year = academicYearForm.getAcademicYear();
            boolean isReactivate = AcademicYearHandler.getInstance().reActivateAcademicYear(year, userId);
            if(isReactivate)
            {
                messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ADMIN_ACADEMIC_YEAR_REACTIVATE_SUCCESS));
                saveMessages(request, messages);
                academicYearForm.clear();
                assignAcademicYearListToForm(academicYearForm);
            } else
            {
                errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_ACADEMIC_YEAR_REACTIVATE_FAILED));
                saveErrors(request, errors);
                assignAcademicYearListToForm(academicYearForm);
            }
        }
        catch(Exception e)
        {
            String msg = super.handleApplicationException(e);
            academicYearForm.setErrorMessage(msg);
            academicYearForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        log.info("End of reActivateAcademicYear in AcademicYearAction class");
        return mapping.findForward(CMSConstants.INIT_ACADEMIC_YEAR);
    }

    private void assignAcademicYearListToForm(AcademicYearForm academicYearForm)
        throws Exception
    {
        List<AcademicYearTO> academicYearDetailsList = AcademicYearHandler.getInstance().getAcademicYearDetails();
        academicYearForm.setAcademicYearList(academicYearDetailsList);
        log.info("End of assignAcademicYearListToForm in AcademicYearAction class");
    }

}
