package com.kp.cms.actions.admission;

import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.kp.cms.to.admission.PrintTcDetailsTo;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.TCDetailsTO;
import org.apache.struts.action.ActionMessage;
import com.kp.cms.helpers.admission.TCDetailsHelper;
import com.kp.cms.to.admission.CharacterAndConductTO;
import com.kp.cms.to.admission.BoardDetailsTO;
import java.util.List;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionError;
import java.util.Map;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.utilities.CurrentAcademicYear;
import java.util.Calendar;
import com.kp.cms.handlers.admission.TCDetailsHandler;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.TCDetailsForm;
import org.apache.struts.action.ActionForward;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.kp.cms.actions.BaseDispatchAction;

public class TCDetailsAction extends BaseDispatchAction
{
    private static final Log log;
    
    static {
        log = LogFactory.getLog((Class)BoardDetailsAction.class);
    }
    
    public ActionForward initTCDetails(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        TCDetailsAction.log.info((Object)"Entering into initTCDetails of TCDetailsAction");
        final TCDetailsForm tcDetailsForm = (TCDetailsForm)form;
        try {
            tcDetailsForm.resetFields();
            this.setClassMapToRequest(request, tcDetailsForm);
            this.setDataToForm(tcDetailsForm);
        }
        catch (Exception e) {
            TCDetailsAction.log.error((Object)"Error occured in initTCDetails of TCDetailsAction", (Throwable)e);
            final String msg = super.handleApplicationException(e);
            tcDetailsForm.setErrorMessage(msg);
            tcDetailsForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        TCDetailsAction.log.info((Object)"Leaving into initTCDetails of TCDetailsAction");
        return mapping.findForward("initTCDetails");
    }
    
    public ActionForward getDetailsForAllStudentsByClass(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final TCDetailsForm tcDetailsForm = (TCDetailsForm)form;
        tcDetailsForm.reset();
        TCDetailsHandler.getInstance().getAllStudentTCDetailsByClass(tcDetailsForm);
        this.setClassMapToRequest(request, tcDetailsForm);
        this.setDataToForm(tcDetailsForm);
        return mapping.findForward("initTCDetails");
    }
    
    public void setClassMapToRequest(final HttpServletRequest request, final TCDetailsForm tcDetailsForm) throws Exception {
        TCDetailsAction.log.info((Object)"Entering into setClassMapToRequest of TCDetailsAction");
        try {
            if (tcDetailsForm.getAcademicYear() == null || tcDetailsForm.getAcademicYear().isEmpty()) {
                final Calendar calendar = Calendar.getInstance();
                int currentYear = calendar.get(1);
                final int year = CurrentAcademicYear.getInstance().getAcademicyear();
                if (year != 0) {
                    currentYear = year;
                }
                final Map<Integer, String> classMap = (Map<Integer, String>)CommonAjaxHandler.getInstance().getClassesByYear(currentYear);
                request.setAttribute("classMap", (Object)classMap);
            }
            else {
                final int year2 = Integer.parseInt(tcDetailsForm.getAcademicYear().trim());
                final Map<Integer, String> classMap2 = (Map<Integer, String>)CommonAjaxHandler.getInstance().getClassesByYear(year2);
                request.setAttribute("classMap", (Object)classMap2);
            }
        }
        catch (Exception e) {
            TCDetailsAction.log.error((Object)"Error occured at setClassMapToRequest in  TCDetailsAction Batch Action", (Throwable)e);
            throw new ApplicationException(e);
        }
        TCDetailsAction.log.info((Object)"Leaving into setClassMapToRequest of TCDetailsAction");
    }
    
    public ActionForward getCandidates(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        TCDetailsAction.log.info((Object)"Entered BoardDetailsAction - getCandidates");
        final TCDetailsForm tcDetailsForm = (TCDetailsForm)form;
        final ActionErrors errors = tcDetailsForm.validate(mapping, request);
            if (errors.isEmpty()) {
                    try {
                        final List<BoardDetailsTO> boardList = TCDetailsHandler.getInstance().getListOfCandidates(tcDetailsForm);
                        if (boardList.isEmpty()) {
                            errors.add("error", new ActionError("knowledgepro.admission.norecordsfound"));
                            this.saveErrors(request, errors);
                            this.setClassMapToRequest(request, tcDetailsForm);
                            this.setDataToForm(tcDetailsForm);
                            TCDetailsAction.log.info((Object)"Exit getCandidates size 0");
                            return mapping.findForward("initTCDetails");
                        }
                        tcDetailsForm.setBoardList(boardList);
                    }
                    catch (Exception exception) {
                        final String msg = super.handleApplicationException(exception);
                        tcDetailsForm.setErrorMessage(msg);
                        tcDetailsForm.setErrorStack(exception.getMessage());
                        return mapping.findForward(CMSConstants.ERROR_PAGE);
                    }
                TCDetailsAction.log.info((Object)"Entered ScoreSheetAction - getCandidates");
                return mapping.findForward("tcDetailsResult");
            }
        
        this.addErrors(request, (ActionMessages)errors);
        this.setClassMapToRequest(request, tcDetailsForm);
        this.setDataToForm(tcDetailsForm);
        TCDetailsAction.log.info((Object)"Exit getCandidates errors not empty ");
        return mapping.findForward("initTCDetails");
    }
    
    public ActionForward getStudentDetails(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        TCDetailsAction.log.info((Object)"Entered BoardDetailsAction - getCandidates");
        final TCDetailsForm tcDetailsForm = (TCDetailsForm)form;
        final ActionErrors errors = tcDetailsForm.validate(mapping, request);
            if (errors.isEmpty()) {
                    try {
                        TCDetailsHandler.getInstance().checkDuplicationForTcStudent(tcDetailsForm);
                        TCDetailsHandler.getInstance().getStudentTCDetails(tcDetailsForm);
                        this.setDataToForm(tcDetailsForm);
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                        final String msg = super.handleApplicationException(exception);
                        tcDetailsForm.setErrorMessage(msg);
                        tcDetailsForm.setErrorStack(exception.getMessage());
                        return mapping.findForward(CMSConstants.ERROR_PAGE);
                    }
                TCDetailsAction.log.info((Object)"Entered ScoreSheetAction - getCandidates");
                return mapping.findForward("tcDetailsByStudent");
            }
        this.addErrors(request, (ActionMessages)errors);
        TCDetailsAction.log.info((Object)"Exit getCandidates errors not empty ");
        return mapping.findForward("tcDetailsResult");
    }
    
    private void setDataToForm(final TCDetailsForm tcDetailsForm) throws Exception {
        final List<CharacterAndConductTO> list = TCDetailsHandler.getInstance().getCharacterAndConductList();
        tcDetailsForm.setList(list);
    }
    
    public ActionForward updateStudentTCDetails(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        TCDetailsAction.log.info((Object)"Entered BoardDetailsAction - getCandidates");
        final TCDetailsForm tcDetailsForm = (TCDetailsForm)form;
        final ActionMessages messages = new ActionMessages();
        final ActionErrors errors = tcDetailsForm.validate(mapping, request);
        this.validateTCDetails(tcDetailsForm, errors);
            if (errors.isEmpty()) {
                    try {
                        if (!TCDetailsHelper.getInstance().checkForExistingTCNumbersInOtherClasses(tcDetailsForm)) {
                            errors.add("error", new ActionError("knowledgepro.admin.StudentTCNumberAlreadyExists"));
                            this.saveErrors(request, errors);
                            return mapping.findForward("tcDetailsByStudent");
                        }
                        final boolean isUpdated = TCDetailsHandler.getInstance().updateStudentTCDetails(tcDetailsForm);
                        if (isUpdated) {
                            final ActionMessage message = new ActionMessage("knowledgepro.admission.tc.details.added.successfully");
                            messages.add("messages", message);
                            this.saveMessages(request, messages);
                            final List<BoardDetailsTO> boardList = TCDetailsHandler.getInstance().getListOfCandidates(tcDetailsForm);
                            tcDetailsForm.setBoardList(boardList);
                            TCDetailsHandler.getInstance().checkDuplicationForTcStudent(tcDetailsForm);
                            return mapping.findForward("tcDetailsResult");
                        }
                        errors.add("error", new ActionError("knowledgepro.admission.tc.details.added.failure"));
                        this.saveErrors(request, errors);
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                        final String msg = super.handleApplicationException(exception);
                        tcDetailsForm.setErrorMessage(msg);
                        tcDetailsForm.setErrorStack(exception.getMessage());
                        return mapping.findForward(CMSConstants.ERROR_PAGE);
                    }
                TCDetailsAction.log.info((Object)"Entered ScoreSheetAction - getCandidates");
                return mapping.findForward("tcDetailsResult");
            }
        this.addErrors(request, (ActionMessages)errors);
        TCDetailsAction.log.info((Object)"Exit getCandidates errors not empty ");
        return mapping.findForward("tcDetailsByStudent");
    }
    
    private void validateTCDetails(final TCDetailsForm tcDetailsForm, final ActionErrors errors) throws Exception {
        final TCDetailsTO to = tcDetailsForm.getTcDetailsTO();
        if (to.getPassed() != null && !to.getPassed().isEmpty() && to.getPassed().equalsIgnoreCase("no") && (to.getSubjectPassed() == null || to.getSubjectPassed().isEmpty())) {
            errors.add("error", new ActionError("knowledgepro.admission.student.tcDetails.subjectPassed.required"));
        }
    }
    
    public ActionForward updateCandidateDetails(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        TCDetailsAction.log.info((Object)"Entered BoardDetailsAction - getCandidates");
        final TCDetailsForm tcDetailsForm = (TCDetailsForm)form;
        final ActionMessages messages = new ActionMessages();
        final ActionErrors errors = tcDetailsForm.validate(mapping, request);
        Boolean saved = false;
        this.setUserId(request, (BaseActionForm)tcDetailsForm);
        if (!errors.isEmpty()) {
            this.addErrors(request, (ActionMessages)errors);
            this.setClassMapToRequest(request, tcDetailsForm);
            this.setDataToForm(tcDetailsForm);
            tcDetailsForm.resetFields();
            TCDetailsAction.log.info((Object)"Exit getCandidates errors not empty ");
            return mapping.findForward("initTCDetails");
        }
        /*if (!TCDetailsHandler.getInstance().verifyStudentsAllSaved(tcDetailsForm)) {
            errors.add("error", new ActionError("knowledgepro.admin.StudentExist"));
            this.saveErrors(request, errors);
            tcDetailsForm.resetFields();
            return mapping.findForward("initTCDetails");
        }*/
        if (!TCDetailsHandler.getInstance().verifyStudentsPresent(tcDetailsForm)) {
            errors.add("error", new ActionError("knowledgepro.admin.StudentNotAvailable"));
            this.saveErrors(request, errors);
            tcDetailsForm.resetFields();
            return mapping.findForward("initTCDetails");
        }
        if (!TCDetailsHandler.getInstance().verifyGeneratedTCNumbers(tcDetailsForm)) {
            errors.add("error", new ActionError("knowledgepro.admin.TCNumberAlreadyExists"));
            this.saveErrors(request, errors);
            tcDetailsForm.resetFields();
            return mapping.findForward("initTCDetails");
        }
        saved = TCDetailsHandler.getInstance().getUpdateStatus(tcDetailsForm);
        if (saved) {
            final ActionMessage message = new ActionMessage("knowledgepro.admission.tc.details.added.successfully");
            messages.add("messages", message);
            this.saveMessages(request, messages);
            tcDetailsForm.resetFields();
        }
        else {
            errors.add("error", new ActionError("knowledgepro.admission.tc.details.added.failure"));
            this.saveErrors(request, errors);
        }
        this.setClassMapToRequest(request, tcDetailsForm);
        this.setDataToForm(tcDetailsForm);
        tcDetailsForm.resetFields();
        TCDetailsAction.log.info((Object)"Entered ScoreSheetAction - getCandidates");
        return mapping.findForward("initTCDetails");
    }
    
    public ActionForward printTC(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) {
        final TCDetailsForm certificateForm = (TCDetailsForm)form;
        certificateForm.setFlag(false);
        certificateForm.setReprint(false);
        try {
            final ActionErrors errors = certificateForm.validate(mapping, request);
            if (errors.isEmpty()) {
                List<PrintTcDetailsTo> studentList = null;
                studentList = TCDetailsHandler.getInstance().getStudentsByClassOnlyTC(request, certificateForm, errors);
                certificateForm.setStudentList(studentList);
                certificateForm.resetFields();
            }
            else {
                this.addErrors(request, (ActionMessages)errors);
            }
        }
        catch (Exception e) {
            final String msg = super.handleApplicationException(e);
            certificateForm.setErrorMessage(msg);
            certificateForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        this.setDate(certificateForm);
        return mapping.findForward("printTCChrist");
    }
    
    private void setDate(final TCDetailsForm certificateForm) {
        final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        final Date date = new Date();
        certificateForm.setTcDate(dateFormat.format(date));
    }
}