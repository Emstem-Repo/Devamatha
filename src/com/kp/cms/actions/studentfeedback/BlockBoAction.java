package com.kp.cms.actions.studentfeedback;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.studentfeedback.BlockBoForm;
import com.kp.cms.handlers.studentfeedback.BlockBoHandler;
import com.kp.cms.to.studentfeedback.BlockBoTo;

public class BlockBoAction extends BaseDispatchAction
{

	private static final Log log=LogFactory.getLog(BlockBoAction.class);

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward initBlockBo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception{
        BlockBoForm blockBoForm = (BlockBoForm)form;
        String formName = mapping.getName();
        request.getSession().setAttribute("formName", formName);
        setEmpLocation(request);
        blockBoForm.reset(mapping, request);
        setRequestedDataToForm(blockBoForm);
        return mapping.findForward(CMSConstants.BLOCK_BO);
    }

    /**
     * @param request
     * @throws Exception
     */
    private void setEmpLocation(HttpServletRequest request)throws Exception {
        List<BlockBoTo> locationList = BlockBoHandler.getInstance().getEmpLocation();
        request.getSession().setAttribute("locationList", locationList);
    }

    /**
     * @param blockBoForm
     * @throws Exception
     */
    private void setRequestedDataToForm(BlockBoForm blockBoForm)throws Exception{
    	List<BlockBoTo> blockList = BlockBoHandler.getInstance().getBlockBoList();
        blockBoForm.setBlockBoList(blockList);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addBlockBo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception{
        log.info("call of addFeedBackQuestion method in EvaStudentFeedBackQuestionAction class.");
        BlockBoForm blockBoForm = (BlockBoForm)form;
        setUserId(request, blockBoForm);
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = blockBoForm.validate(mapping, request);
        HttpSession session = request.getSession();
        if(errors.isEmpty())
        {
            try
            {
                boolean isAdded = false;
                boolean isDuplicate = BlockBoHandler.getInstance().duplicateCheck(blockBoForm, errors, session);
                if(!isDuplicate){
                	   isAdded = BlockBoHandler.getInstance().addBlockBo(blockBoForm);
                       if(isAdded)
                       {
                           messages.add("messages", new ActionMessage("knowledgepro.hlAdmission.entry.added.success"));
                           saveMessages(request, messages);
                           setRequestedDataToForm(blockBoForm);
                           blockBoForm.reset(mapping, request);
                       } else
                       {
                           errors.add("error", new ActionError("knowledgepro.hlAdmission.entry.added.failure"));
                           addErrors(request, errors);
                           blockBoForm.reset(mapping, request);
                       }
                }else{
                    addErrors(request, errors);
                }
            }
            catch(Exception exception)
            {
                log.error("Error occured in caste Entry Action", exception);
                String msg = super.handleApplicationException(exception);
                blockBoForm.setErrorMessage(msg);
                blockBoForm.setErrorStack(exception.getMessage());
                return mapping.findForward(CMSConstants.ERROR_PAGE);
            }
        } else
        {
            saveErrors(request, errors);
            setRequestedDataToForm(blockBoForm);
            return mapping.findForward(CMSConstants.BLOCK_BO);
        }
        log.info("end of addFeedBackQuestion method in EvaStudentFeedBackQuestionAction class.");
        return mapping.findForward(CMSConstants.BLOCK_BO);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editBlockBo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
    	BlockBoForm blockBoForm = (BlockBoForm)form;
        try
        {
            BlockBoHandler.getInstance().editBlockBo(blockBoForm);
            request.setAttribute("blockBo", "edit");
        }
        catch(Exception e)
        {
            log.error("error in editing FeedBackQuestion...", e);
            String msg = super.handleApplicationException(e);
            blockBoForm.setErrorMessage(msg);
            blockBoForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        return mapping.findForward(CMSConstants.BLOCK_BO);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateBlockBo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
    	BlockBoForm blockBoForm = (BlockBoForm)form;
        HttpSession session=request.getSession();
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = blockBoForm.validate(mapping, request);
		boolean isUpdated = false;
		if(errors.isEmpty()){
			try {
				// This condition works when reset button will click in update mode
				if (isCancelled(request)) {
					blockBoForm.reset(mapping, request);
			        String formName = mapping.getName();
			        request.getSession().setAttribute("formName", formName);
			        BlockBoHandler.getInstance().editBlockBo(blockBoForm);
		            request.setAttribute("blockBo", "edit");
			        return mapping.findForward(CMSConstants.BLOCK_BO);
				}
				setUserId(request, blockBoForm);
				boolean isDuplicate = BlockBoHandler.getInstance().duplicateCheck(blockBoForm, errors, session);
				if(!isDuplicate){
						isUpdated = BlockBoHandler.getInstance().updateBlockBo(blockBoForm);
					if (isUpdated) {
	                    ActionMessage message = new ActionMessage("knowledgepro.hlAdmission.entry.update.success");
	                    messages.add("messages", message);
	                    saveMessages(request, messages);
	                    blockBoForm.reset(mapping, request);
	                } else {
	                    errors.add("error", new ActionError("knowledgepro.hlAdmission.entry.update.failure"));
	                    addErrors(request, errors);
	                    blockBoForm.reset(mapping, request);
	                }
				}else{
	                request.setAttribute("blockBo", "edit");
	                addErrors(request, errors);
	            }
			} catch (Exception e) {
	            log.error("Error occured in edit valuatorcharges", e);
	            String msg = super.handleApplicationException(e);
	            blockBoForm.setErrorMessage(msg);
	            blockBoForm.setErrorStack(e.getMessage());
	            return mapping.findForward(CMSConstants.ERROR_PAGE);
	        }}else{
				saveErrors(request, errors);
				setRequestedDataToForm(blockBoForm);
		        request.setAttribute("FeedBackQuestion", "edit");
				return mapping.findForward(CMSConstants.BLOCK_BO);
			}
		 setRequestedDataToForm(blockBoForm);
        log.debug("Exit: action class updateFeedBackQuestion");
        return mapping.findForward(CMSConstants.BLOCK_BO);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward deleteBlockBo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception {
    	log.debug("Action class. Delete valuatorCharges ");
    	BlockBoForm blockBoForm = (BlockBoForm)form;
        ActionMessages messages = new ActionMessages();
        try
        {
            boolean isDeleted = BlockBoHandler.getInstance().deleteBlockBo(blockBoForm);
            if(isDeleted)
            {
                ActionMessage message = new ActionMessage("knowledgepro.hlAdmission.entry.delete.success");
                messages.add("messages", message);
                saveMessages(request, messages);
            } else
            {
                ActionMessage message = new ActionMessage("knowledgepro.hlAdmission.entry.delete.failure");
                messages.add("messages", message);
                saveMessages(request, messages);
            }
            blockBoForm.reset(mapping, request);
            setRequestedDataToForm(blockBoForm);
        }
        catch(Exception e)
        {
            log.error("error submit valuatorCharges...", e);
            if(e instanceof ApplicationException)
            {
                String msg = super.handleApplicationException(e);
                blockBoForm.setErrorMessage(msg);
                blockBoForm.setErrorStack(e.getMessage());
                return mapping.findForward(CMSConstants.ERROR_PAGE);
            } else
            {
                String msg = super.handleApplicationException(e);
                blockBoForm.setErrorMessage(msg);
                blockBoForm.setErrorStack(e.getMessage());
                return mapping.findForward(CMSConstants.ERROR_PAGE);
            }
        }
        log.debug("Action class. Delete valuatorCharges ");
        return mapping.findForward(CMSConstants.BLOCK_BO);
    }

}
