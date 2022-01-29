package com.kp.cms.actions.admission;
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
import com.kp.cms.bo.admission.PrerequisitsYearMonth;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.PrerequisiteYearMonthForm;
import com.kp.cms.handlers.admission.PrerequisiteYearMonthHandler;
import com.kp.cms.to.admission.PrerequisiteYearMonthTO;
	@SuppressWarnings("deprecation")
public class PrerequisiteYearMonthAction   extends BaseDispatchAction{
		
		private static final Logger log = Logger.getLogger(PrerequisiteYearMonthAction.class);

	     /**
	     * @param mapping
	     * @param form
	     * @param request
	     * @param response
	     * @return
	     * @throws Exception
	     */
	    public ActionForward initPrerequisiteYearMonthEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	        throws Exception
	    {
	        PrerequisiteYearMonthForm prerequisiteYearMonthForm = (PrerequisiteYearMonthForm)form;
	        try
	        {
	            assignAcademicYearListToForm(prerequisiteYearMonthForm);
	        }
	        catch(Exception e)
	        {
	            String msg = super.handleApplicationException(e);
	            prerequisiteYearMonthForm.setErrorMessage(msg);
	            prerequisiteYearMonthForm.setErrorStack(e.getMessage());
	            return mapping.findForward(CMSConstants.ERROR_PAGE);
	        }
	        log.info("End of initAcademicYearYearEntry in AcademicYearAction class");
	        return mapping.findForward(CMSConstants.INIT_PREREQUISIT_YEAR_MONTH);
	    }

	     /**
	     * @param mapping
	     * @param form
	     * @param request
	     * @param response
	     * @return
	     * @throws Exception
	     */
	    public ActionForward addPrereqYearMonthEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	    {
	        PrerequisiteYearMonthForm prereqYearForm = (PrerequisiteYearMonthForm)form;
	        ActionMessages messages = new ActionMessages();
	        ActionErrors errors = prereqYearForm.validate(mapping, request);
	        boolean isAcademicYearAdded = false;
	        try
	        {
	            if(errors.isEmpty())
	            {
	                setUserId(request, prereqYearForm);
	                PrerequisiteYearMonthTO preYrTO = new PrerequisiteYearMonthTO();
	                String Year=prereqYearForm.getYear();
	                String Month=prereqYearForm.getMonth();
	                preYrTO.setYear(prereqYearForm.getYear());
	                preYrTO.setMonth(prereqYearForm.getMonth());
	                preYrTO.setCreatedBy(prereqYearForm.getUserId());
	                preYrTO.setModifiedBy(prereqYearForm.getUserId());
	                
	               PrerequisitsYearMonth prYearMonth = PrerequisiteYearMonthHandler.getInstance().getPrereqYear(Year, Month);
	                
	                if(prYearMonth != null)
	                {
	                    if(prYearMonth.getIsActive())
	                    {
	                        errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_ACADEMIC_YEAR_EXISTS));
	                        assignAcademicYearListToForm(prereqYearForm);
	                        prereqYearForm.clear();
	                        saveErrors(request, errors);
	                    } else
	                    if(!prYearMonth.getIsActive())
	                    {
	                        errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_PREREQUISIT_YEAR__MONTH_REACTIVATE));
	                        assignAcademicYearListToForm(prereqYearForm);
	                        saveErrors(request, errors);
	                    }
	                } else
	                {
	                    isAcademicYearAdded = PrerequisiteYearMonthHandler.getInstance().addAcademicYear(preYrTO, errors);
	                    if(isAcademicYearAdded)
	                    {
	                        messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ADMIN_PREREQUISIT_YEAR__MONTH_ADD_SUCCESS));
	                        saveMessages(request, messages);
	                        assignAcademicYearListToForm(prereqYearForm);
	                        prereqYearForm.clear();
	                    } else
	                    {
	                        errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_PREREQUISIT_YEAR__MONTH_ADD_FAILED));
	                        assignAcademicYearListToForm(prereqYearForm);
	                        saveErrors(request, errors);
	                    }
	                }
	            } else
	            {
	                assignAcademicYearListToForm(prereqYearForm);
	                saveErrors(request, errors);
	            }
	        }
	        catch(Exception e)
	        {
	            if(e instanceof BusinessException)
	            {
	                addErrors(request, errors);
	                assignAcademicYearListToForm(prereqYearForm);
	                return mapping.findForward(CMSConstants.INIT_PREREQUISIT_YEAR_MONTH);
	            } else
	            {
	                String msg = super.handleApplicationException(e);
	                prereqYearForm.setErrorMessage(msg);
	                prereqYearForm.setErrorStack(e.getMessage());
	                return mapping.findForward(CMSConstants.ERROR_PAGE);
	            }
	        }
	        log.info("End of addAcademicYearEntry in AcademicYearAction class");
	        return mapping.findForward(CMSConstants.INIT_PREREQUISIT_YEAR_MONTH);
	    }

	    /**
	     * @param mapping
	     * @param form
	     * @param request
	     * @param response
	     * @return
	     * @throws Exception
	     */
	    public ActionForward editPrerequisiteYearDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	        throws Exception
	    {
	        PrerequisiteYearMonthForm preYearForm = (PrerequisiteYearMonthForm)form;
	        try
	        {
	            int yearId = preYearForm.getId();
	            PrerequisiteYearMonthTO preYrTO = PrerequisiteYearMonthHandler.getInstance().getYearDetailsWithId(yearId);
	            	preYearForm.setYear(preYrTO.getYear());
	               // preYearForm.setAcademicYearSel(preYrTO.getYear());
	                //code is added by sudhir
	            	preYearForm.setMonth(preYrTO.getMonth());
	               
	               // String previousYear = preYrTO.getYear();
	              //  HttpSession session = request.getSession(true);
	             //   session.setAttribute("previousYear", previousYear);
	            assignAcademicYearListToForm(preYearForm);
	            request.setAttribute(CMSConstants.OPERATION,
						CMSConstants.EDIT_OPERATION);
	        }
	        catch(Exception e)
	        {
	            String msg = super.handleApplicationException(e);
	            preYearForm.setErrorMessage(msg);
	            preYearForm.setErrorStack(e.getMessage());
	            return mapping.findForward(CMSConstants.ERROR_PAGE);
	        }
	        log.info("End of editAcademicYearDetails in AcademicYearAction class");
	        return mapping.findForward(CMSConstants.INIT_PREREQUISIT_YEAR_MONTH);
	    }


		public ActionForward updatePrereqYearMonthDetails(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			PrerequisiteYearMonthForm preYearForm = (PrerequisiteYearMonthForm)form;
			ActionErrors errors = preYearForm.validate(mapping, request);
			ActionMessages messages = new ActionMessages();

			try {
				if (isCancelled(request)) {
					int yearId = preYearForm.getId();
					PrerequisiteYearMonthTO preYearTO = PrerequisiteYearMonthHandler
							.getInstance().getYearDetailsWithId(yearId);
					preYearForm.setYear(preYearTO.getYear());
					preYearForm.setMonth(preYearTO.getMonth());
					assignAcademicYearListToForm(preYearForm);
					request.setAttribute(CMSConstants.OPERATION,
							CMSConstants.EDIT_OPERATION);
					return mapping
							.findForward(CMSConstants.INIT_PREREQUISIT_YEAR_MONTH);
				} else if (errors.isEmpty()) {
					setUserId(request, preYearForm);
					HttpSession session = request.getSession(false);
					String prevoiusYear = String.valueOf(session
							.getAttribute("previousYear"));
					String currentYear = preYearForm.getYear();
					String currentMonth = preYearForm.getMonth();
					boolean isUpdated;
					if (!prevoiusYear.equals(currentYear)) {
						PrerequisitsYearMonth prYearMonth= PrerequisiteYearMonthHandler
								.getInstance().getPrereqYear(currentYear, currentMonth);

						if (prYearMonth != null) {
							if (prYearMonth.getIsActive()) {
								errors.add(CMSConstants.ERROR, new ActionError(
										CMSConstants.ADMIN_ACADEMIC_YEAR_EXISTS));
								saveErrors(request, errors);
								request.setAttribute(CMSConstants.OPERATION,
										CMSConstants.EDIT_OPERATION);
								assignAcademicYearListToForm(preYearForm);
							}
							if (!prYearMonth.getIsActive()) {
								errors
										.add(
												"error",
												new ActionError(
														CMSConstants.ADMIN_ACADEMIC_YEAR_REACTIVATE));
								saveErrors(request, errors);
								assignAcademicYearListToForm(preYearForm);
							}
						} else {
							isUpdated = PrerequisiteYearMonthHandler.getInstance()
									.updateAcademicYear(preYearForm,errors);
							if (isUpdated) {
								messages
										.add(
												CMSConstants.MESSAGES,
												new ActionMessage(
														CMSConstants.ADMIN_ACADEMIC_YEAR_UPDATE_SUCCESS));
								saveMessages(request, messages);
								preYearForm.clear();
								assignAcademicYearListToForm(preYearForm);
							}
							if (!isUpdated) {
								errors
										.add(
												CMSConstants.ERROR,
												new ActionError(
														CMSConstants.ADMIN_ACADEMIC_YEAR_UPDATE_FAILED));
								saveErrors(request, errors);
								preYearForm.clear();
								assignAcademicYearListToForm(preYearForm);
							}
						}
					} else {
						isUpdated = PrerequisiteYearMonthHandler.getInstance()
								.updateAcademicYear(preYearForm,errors);
						if (isUpdated) {
							messages
									.add(
											CMSConstants.MESSAGES,
											new ActionMessage(
													CMSConstants.ADMIN_ACADEMIC_YEAR_UPDATE_SUCCESS));
							saveMessages(request, messages);
							preYearForm.clear();
							assignAcademicYearListToForm(preYearForm);
						} else {
							errors.add(CMSConstants.ERROR, new ActionError(
									CMSConstants.ADMIN_ACADEMIC_YEAR_UPDATE_FAILED));
							saveErrors(request, errors);
							preYearForm.clear();
							assignAcademicYearListToForm(preYearForm);
						}
					}
				} else {
					request.setAttribute(CMSConstants.OPERATION,
							CMSConstants.EDIT_OPERATION);
					saveErrors(request, errors);
					assignAcademicYearListToForm(preYearForm);
				}
			} catch (Exception e) {			
				if (e instanceof BusinessException) {
					addErrors(request, errors);
					assignAcademicYearListToForm(preYearForm);
					return mapping.findForward(CMSConstants.INIT_PREREQUISIT_YEAR_MONTH);
				} 
				else{
					String msg = super.handleApplicationException(e);
					preYearForm.setErrorMessage(msg);
					preYearForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}		
			}
			log
			.info("End of updateAcademicYearDetails in AcademicYearAction class");
			return mapping.findForward(CMSConstants.INIT_PREREQUISIT_YEAR_MONTH);
		}

	  
		public ActionForward deleteAcademicYearDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	        throws Exception
	    {
	        PrerequisiteYearMonthForm preYearForm = (PrerequisiteYearMonthForm)form;
	        ActionErrors errors = new ActionErrors();
	        ActionMessages messages = new ActionMessages();
	        try
	        {
	            setUserId(request, preYearForm);
	            String userId = preYearForm.getUserId();
	            int yearId = preYearForm.getId();
	            boolean isDeleted = PrerequisiteYearMonthHandler.getInstance().deleteAcademicYearDetails(yearId, userId);
	            if(isDeleted)
	            {
	                messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ADMIN_ACADEMIC_YEAR_DELETE_SUCCESS));
	                saveMessages(request, messages);
	                assignAcademicYearListToForm(preYearForm);
	                preYearForm.clear();
	            } else
	            {
	                errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_ACADEMIC_YEAR_DELETE_FAILED));
	                saveErrors(request, errors);
	                assignAcademicYearListToForm(preYearForm);
	            }
	        }
	        catch(Exception e)
	        {
	            String msg = super.handleApplicationException(e);
	            preYearForm.setErrorMessage(msg);
	            preYearForm.setErrorStack(e.getMessage());
	            return mapping.findForward(CMSConstants.ERROR_PAGE);
	        }
	        log.info("End of deleteAcademicYearDetails in AcademicYearAction class");
	        return mapping.findForward(CMSConstants.INIT_PREREQUISIT_YEAR_MONTH);
	    }

	    public ActionForward reActivateAcademicYear(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	        throws Exception
	    {
	        PrerequisiteYearMonthForm preYearForm = (PrerequisiteYearMonthForm)form;
	        ActionErrors errors = new ActionErrors();
	        ActionMessages messages = new ActionMessages();
	        try
	        {
	            setUserId(request, preYearForm);
	            String userId = preYearForm.getUserId();
	            String year = preYearForm.getYear();
	            String month = preYearForm.getMonth();
	            boolean isReactivate = PrerequisiteYearMonthHandler.getInstance().reActivateAcademicYear(year,month, userId);
	            if(isReactivate)
	            {
	                messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ADMIN_ACADEMIC_YEAR_REACTIVATE_SUCCESS));
	                saveMessages(request, messages);
	                preYearForm.clear();
	                assignAcademicYearListToForm(preYearForm);
	            } else
	            {
	                errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_ACADEMIC_YEAR_REACTIVATE_FAILED));
	                saveErrors(request, errors);
	                assignAcademicYearListToForm(preYearForm);
	            }
	        }
	        catch(Exception e)
	        {
	            String msg = super.handleApplicationException(e);
	            preYearForm.setErrorMessage(msg);
	            preYearForm.setErrorStack(e.getMessage());
	            return mapping.findForward(CMSConstants.ERROR_PAGE);
	        }
	        log.info("End of reActivateAcademicYear in AcademicYearAction class");
	        return mapping.findForward(CMSConstants.INIT_PREREQUISIT_YEAR_MONTH);
	    }

	    private void assignAcademicYearListToForm(PrerequisiteYearMonthForm preYearMonthForm)
	        throws Exception
	    {
	        log.info("Start of assignAcademicYearListToForm ");
	        List<PrerequisiteYearMonthTO> yearList = PrerequisiteYearMonthHandler.getInstance().getPrerequisiteYearMonthDetails();
	        
	        preYearMonthForm.setYearList(yearList);
	        log.info("End of assignAcademicYearListToForm  class");
	    }

	}



