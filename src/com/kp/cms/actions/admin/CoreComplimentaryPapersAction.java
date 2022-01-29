/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  com.kp.cms.actions.BaseDispatchAction
 *  com.kp.cms.bo.admin.CoreComplimentaryPapers
 *  com.kp.cms.constants.CMSConstants
 *  com.kp.cms.exceptions.DuplicateException
 *  com.kp.cms.exceptions.ReActivateException
 *  com.kp.cms.forms.BaseActionForm
 *  com.kp.cms.forms.admin.CoreComplimentaryPapersForm
 *  com.kp.cms.handlers.admin.CasteHandler
 *  com.kp.cms.handlers.admin.CoreComplimentaryPapersHandler
 *  com.kp.cms.handlers.admin.CourseHandler
 *  com.kp.cms.to.admin.CourseTO
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.servlet.http.HttpSession
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.apache.struts.action.ActionError
 *  org.apache.struts.action.ActionErrors
 *  org.apache.struts.action.ActionForm
 *  org.apache.struts.action.ActionForward
 *  org.apache.struts.action.ActionMapping
 *  org.apache.struts.action.ActionMessage
 *  org.apache.struts.action.ActionMessages
 */
package com.kp.cms.actions.admin;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.CoreComplimentaryPapers;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.admin.CoreComplimentaryPapersForm;
import com.kp.cms.handlers.admin.CasteHandler;
import com.kp.cms.handlers.admin.CoreComplimentaryPapersHandler;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.to.admin.CoreComplimentaryPapersTo;
import com.kp.cms.to.admin.CourseTO;
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

public class CoreComplimentaryPapersAction
extends BaseDispatchAction {
    private static final Log log = LogFactory.getLog(CoreComplimentaryPapersAction.class);

    public ActionForward initCoreComplimentaryPapers(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CoreComplimentaryPapersForm corecompForm = (CoreComplimentaryPapersForm)form;
        HttpSession session = request.getSession();
        session.setAttribute("field", (Object)"CoreComplimentaryPapers");
        try {
            this.setCourseListToRequest(request);
            List<CoreComplimentaryPapersTo> corecompList = CoreComplimentaryPapersHandler.getInstance().getCoreComplimentaryPapers();
            corecompForm.setCorecompList(corecompList);
        }
        catch (Exception e) {
            log.error((Object)"Error occured in caste Entry Action", (Throwable)e);
            String msg = super.handleApplicationException(e);
            corecompForm.setErrorMessage(msg);
            corecompForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        return mapping.findForward("coreComplimentary");
    }

    public ActionForward addCoreComplimentaryPapers(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CoreComplimentaryPapersForm corecompForm = (CoreComplimentaryPapersForm)form;
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = corecompForm.validate(mapping, request);
        String courseName = "";
        boolean isCoreComplimentaryPapersAdded = false;
        if (errors.isEmpty()) {
            String coreName = corecompForm.getCoreSubject();
            try {
                this.setUserId(request, (BaseActionForm)corecompForm);
                for (CourseTO courseTo : this.setCourseListToRequest(request)) {
                    if (courseTo.getId() != Integer.parseInt(corecompForm.getCourseId())) continue;
                    courseName = courseTo.getName();
                }
                isCoreComplimentaryPapersAdded = CoreComplimentaryPapersHandler.getInstance().addCoreComplimentaryPapers(corecompForm, request);
            }
            catch (Exception e) {
                if (e instanceof DuplicateException) {
                    errors.add("error", new ActionError("knowledgepro.admin.CoreComp.name.exists", (Object)courseName));
                    this.saveErrors(request, errors);
                    List<CoreComplimentaryPapersTo> corecompList = CoreComplimentaryPapersHandler.getInstance().getCoreComplimentaryPapers();
                    corecompForm.setCorecompList(corecompList);
                    return mapping.findForward("coreComplimentary");
                }
                if (e instanceof ReActivateException) {
                    errors.add("error", new ActionError("knowledgepro.admin.CoreComp.addfailure.alreadyexist.reactivate", (Object)courseName));
                    this.saveErrors(request, errors);
                    this.setCourseListToRequest(request);
                    List<CoreComplimentaryPapersTo> corecompList = CoreComplimentaryPapersHandler.getInstance().getCoreComplimentaryPapers();
                    corecompForm.setCorecompList(corecompList);
                    return mapping.findForward("coreComplimentary");
                }
                log.error((Object)"Error occured in caste Entry Action", (Throwable)e);
                String msg = super.handleApplicationException(e);
                corecompForm.setErrorMessage(msg);
                corecompForm.setErrorStack(e.getMessage());
                return mapping.findForward(CMSConstants.ERROR_PAGE);
            }
            if (isCoreComplimentaryPapersAdded) {
                ActionMessage message = new ActionMessage("knowledgepro.admin.CoreComp.addsuccess", (Object)courseName);
                messages.add("messages", message);
                this.saveMessages(request, messages);
                corecompForm.reset(mapping, request);
                this.setCourseListToRequest(request);
                List<CoreComplimentaryPapersTo> corecompList = CoreComplimentaryPapersHandler.getInstance().getCoreComplimentaryPapers();
                corecompForm.setCorecompList(corecompList);
            } else {
                errors.add("error", new ActionError("knowledgepro.admin.CoreComp.addfailure"));
                this.saveErrors(request, errors);
                this.setCourseListToRequest(request);
                List corecompList = CoreComplimentaryPapersHandler.getInstance().getCoreComplimentaryPapers();
                corecompForm.setCorecompList(corecompList);
            }
        } else {
            this.saveErrors(request, errors);
            this.setCourseListToRequest(request);
            List corecompList = CoreComplimentaryPapersHandler.getInstance().getCoreComplimentaryPapers();
            corecompForm.setCorecompList(corecompList);
            return mapping.findForward("coreComplimentary");
        }
        return mapping.findForward("coreComplimentary");
    }

    public ActionForward updateCoreComplimentaryPapers(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CoreComplimentaryPapersForm corecompForm = (CoreComplimentaryPapersForm)form;
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = corecompForm.validate(mapping, request);
        String coreName = corecompForm.getCoreSubject();
        String courseName = "";
        boolean isCoreComplimentaryPapersEdited = false;
        if (errors.isEmpty()) {
            try {
                this.setUserId(request, (BaseActionForm)corecompForm);
                for (CourseTO courseTo : this.setCourseListToRequest(request)) {
                    if (courseTo.getId() != Integer.parseInt(corecompForm.getCourseId())) continue;
                    courseName = courseTo.getName();
                }
                isCoreComplimentaryPapersEdited = CoreComplimentaryPapersHandler.getInstance().updateCoreComplimentaryPapers(corecompForm, request);
            }
            catch (Exception e) {
                if (e instanceof DuplicateException) {
                    errors.add("error", new ActionError("knowledgepro.admin.CoreComp.name.exists", (Object)courseName));
                    this.saveErrors(request, errors);
                    this.setCourseListToRequest(request);
                    List corecompList = CoreComplimentaryPapersHandler.getInstance().getCoreComplimentaryPapers();
                    corecompForm.setCorecompList(corecompList);
                    return mapping.findForward("coreComplimentary");
                }
                if (e instanceof ReActivateException) {
                    errors.add("error", new ActionError("knowledgepro.admin.CoreComp.addfailure.alreadyexist.reactivate", (Object)courseName));
                    this.saveErrors(request, errors);
                    this.setCourseListToRequest(request);
                    List corecompList = CoreComplimentaryPapersHandler.getInstance().getCoreComplimentaryPapers();
                    corecompForm.setCorecompList(corecompList);
                    return mapping.findForward("coreComplimentary");
                }
                log.error((Object)"Error occured in caste Entry Action", (Throwable)e);
                String msg = super.handleApplicationException(e);
                corecompForm.setErrorMessage(msg);
                corecompForm.setErrorStack(e.getMessage());
                return mapping.findForward(CMSConstants.ERROR_PAGE);
            }
        } else {
            this.saveErrors(request, errors);
            request.setAttribute("operation", (Object)"edit");
            this.setCourseListToRequest(request);
            List<CoreComplimentaryPapersTo> corecompList = CoreComplimentaryPapersHandler.getInstance().getCoreComplimentaryPapers();
            corecompForm.setCorecompList(corecompList);
            return mapping.findForward("coreComplimentary");
        }
        if (isCoreComplimentaryPapersEdited) {
            ActionMessage message = new ActionMessage("knowledgepro.admin.CoreComp.updatesuccess", (Object)courseName);
            messages.add("messages", message);
            this.saveMessages(request, messages);
            corecompForm.reset(mapping, request);
            this.setCourseListToRequest(request);
            List<CoreComplimentaryPapersTo> corecompList = CoreComplimentaryPapersHandler.getInstance().getCoreComplimentaryPapers();
            corecompForm.setCorecompList(corecompList);
        } else {
            errors.add("error", new ActionError("knowledgepro.admin.CoreComp.updatefailure"));
            this.saveErrors(request, errors);
            this.setCourseListToRequest(request);
            List<CoreComplimentaryPapersTo> corecompList = CoreComplimentaryPapersHandler.getInstance().getCoreComplimentaryPapers();
            corecompForm.setCorecompList(corecompList);
        }
        return mapping.findForward("coreComplimentary");
    }

    public ActionForward deleteCoreComplimentaryPapers(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	List<CoreComplimentaryPapersTo> corecompList;
        CoreComplimentaryPapersForm corecompForm = (CoreComplimentaryPapersForm)form;
        ActionMessages messages = new ActionMessages();
        String courseName = "";
        ActionErrors errors = corecompForm.validate(mapping, request);
        boolean isCoreComplimentaryPapersDeleted = false;
        try {
            this.setUserId(request, (BaseActionForm)corecompForm);
            for (CourseTO courseTo : this.setCourseListToRequest(request)) {
                if (courseTo.getId() != Integer.parseInt(corecompForm.getCourseId())) continue;
                courseName = courseTo.getName();
            }
            isCoreComplimentaryPapersDeleted = CoreComplimentaryPapersHandler.getInstance().deleteCoreComplimentaryPapers(corecompForm.getCorecompId(), corecompForm.getUserId());
        }
        catch (Exception e) {
            log.error((Object)"Error occured in caste Entry Action", (Throwable)e);
            String msg = super.handleApplicationException(e);
            corecompForm.setErrorMessage(msg);
            corecompForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        if (isCoreComplimentaryPapersDeleted) {
            ActionMessage message = new ActionMessage("knowledgepro.admin.CoreComp.deletesuccess", (Object)courseName);
            messages.add("messages", message);
            this.saveMessages(request, messages);
            corecompForm.reset(mapping, request);
            this.setCourseListToRequest(request);
            corecompList = CoreComplimentaryPapersHandler.getInstance().getCoreComplimentaryPapers();
            corecompForm.setCorecompList(corecompList);
        } else {
            errors.add("error", new ActionError("knowledgepro.admin.CoreComp.deletefailure"));
            this.saveErrors(request, errors);
            corecompList = CoreComplimentaryPapersHandler.getInstance().getCoreComplimentaryPapers();
            corecompForm.setCorecompList(corecompList);
        }
        return mapping.findForward("coreComplimentary");
    }

    public ActionForward reActivateCoreComplimentaryPapers(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CoreComplimentaryPapersForm corecompForm = (CoreComplimentaryPapersForm)form;
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = corecompForm.validate(mapping, request);
        CoreComplimentaryPapers corecomp = (CoreComplimentaryPapers)request.getSession().getAttribute("CoreComplimentaryPapers");
        boolean isActivateCoreComplimentaryPapersReActivate = false;
        try {
            this.setUserId(request, (BaseActionForm)corecompForm);
            isActivateCoreComplimentaryPapersReActivate = CoreComplimentaryPapersHandler.getInstance().reCoreComplimentaryPapers(corecomp, corecompForm.getUserId());
        }
        catch (Exception e) {
            log.error((Object)"Error occured in caste Entry Action", (Throwable)e);
            String msg = super.handleApplicationException(e);
            corecompForm.setErrorMessage(msg);
            corecompForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        if (isActivateCoreComplimentaryPapersReActivate) {
            ActionMessage message = new ActionMessage("knowledgepro.admin.CoreComp.activate");
            messages.add("messages", message);
            this.saveMessages(request, messages);
            corecompForm.reset(mapping, request);
            this.setCourseListToRequest(request);
            List<CoreComplimentaryPapersTo> corecompList = CoreComplimentaryPapersHandler.getInstance().getCoreComplimentaryPapers();
            corecompForm.setCorecompList(corecompList);
        } else {
            errors.add("error", new ActionError("knowledgepro.admin.CoreComp.activatefailure"));
            this.saveErrors(request, errors);
            this.setCourseListToRequest(request);
            List<CoreComplimentaryPapersTo> corecompList = CoreComplimentaryPapersHandler.getInstance().getCoreComplimentaryPapers();
            corecompForm.setCorecompList(corecompList);
        }
        request.getSession().removeAttribute("CoreComplimentaryPapers");
        return mapping.findForward("coreComplimentary");
    }

    public List<CourseTO> setCourseListToRequest(HttpServletRequest request) throws Exception {
        List<CourseTO> courseList = CourseHandler.getInstance().getCourses();
        request.setAttribute("CourseList", (Object)courseList);
        log.debug((Object)"leaving setCourseListToRequest in Action");
        return courseList;
    }
}