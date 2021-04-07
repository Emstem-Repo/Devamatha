package com.kp.cms.actions.admission;

import com.kp.cms.bo.admin.TCNumber;
import com.kp.cms.transactions.admission.ITransferCertificateTransaction;
import com.kp.cms.transactionsimpl.admission.TransferCertificateTransactionImpl;
import com.kp.cms.to.admission.TCDetailsTO;
import org.apache.struts.action.ActionError;
import com.kp.cms.to.admission.PrintTcDetailsTo;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessages;
import java.util.List;
import com.kp.cms.handlers.admission.TransferCertificateHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.utilities.CurrentAcademicYear;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.TransferCertificateForm;
import org.apache.struts.action.ActionForward;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.kp.cms.actions.BaseDispatchAction;

public class TransferCertificateAction extends BaseDispatchAction
{
    private static final Log log;
    
    static {
        log = LogFactory.getLog((Class)TransferCertificateAction.class);
    }
    
    public ActionForward initTransferCertificate(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) {
        final TransferCertificateForm certificateForm = (TransferCertificateForm)form;
        try {
            certificateForm.resetFields();
            certificateForm.setToCollege("Cjc");
            certificateForm.setReprint(false);
            this.initsetDataToForm(certificateForm, request);
        }
        catch (Exception e) {
            final String msg = super.handleApplicationException(e);
            certificateForm.setErrorMessage(msg);
            certificateForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        return mapping.findForward("transferCertificate");
    }
    
    private void setDate(final TransferCertificateForm certificateForm) {
        final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        final Date date = new Date();
        certificateForm.setTcDate(dateFormat.format(date));
    }
    
    private void initsetDataToForm(final TransferCertificateForm certificateForm, final HttpServletRequest request) throws Exception {
        final Map<Integer, String> classMap = this.setupClassMapToRequest(certificateForm, request);
        certificateForm.setClassMap((Map)classMap);
    }
    
    public Map<Integer, String> setupClassMapToRequest(final TransferCertificateForm certificateForm, final HttpServletRequest request) throws Exception {
        TransferCertificateAction.log.info((Object)"Entering into setpClassMapToRequest of TransferCertificateAction");
        Map<Integer, String> classMap = new HashMap<Integer, String>();
        try {
            final Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(1);
            final int year = CurrentAcademicYear.getInstance().getAcademicyear();
            if (year != 0) {
                currentYear = year;
            }
            if (certificateForm.getYear() != null && !certificateForm.getYear().isEmpty()) {
                currentYear = Integer.parseInt(certificateForm.getYear());
            }
            classMap = (Map<Integer, String>)CommonAjaxHandler.getInstance().getClassesByYear(currentYear);
            request.setAttribute("classMap", (Object)classMap);
            return classMap;
        }
        catch (Exception e) {
            TransferCertificateAction.log.debug((Object)e.getMessage());
            TransferCertificateAction.log.error((Object)"Error occured in setpClassMapToRequest of TransferCertificateAction");
            TransferCertificateAction.log.info((Object)"Leaving into setpClassMapToRequest of TransferCertificateAction");
            return classMap;
        }
    }
    
    public ActionForward printTC(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) {
        final TransferCertificateForm certificateForm = (TransferCertificateForm)form;
        certificateForm.setFlag(false);
        certificateForm.setReprint(false);
        try {
            if (certificateForm.getPrintOnlyTc() == null) {
                certificateForm.setPrintOnlyTc("false");
            }
            final ActionErrors errors = certificateForm.validate(mapping, request);
            if (errors.isEmpty()) {
                List<PrintTcDetailsTo> studentList = null;
                if (certificateForm.getPrintOnlyTc().equalsIgnoreCase("true")) {
                    studentList = TransferCertificateHandler.getInstance().getStudentsByClassOnlyTC(request, certificateForm, errors);
                }
                else {
                    studentList = TransferCertificateHandler.getInstance().getStudentsByClass(request, certificateForm, errors);
                }
                certificateForm.setStudentList((List)studentList);
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
        if (certificateForm.getToCollege() != null && certificateForm.getToCollege().equalsIgnoreCase("Cjc")) {
            this.setDate(certificateForm);
            return mapping.findForward("printTCChrist");
        }
        if (certificateForm.getPrintOnlyTc().equalsIgnoreCase("true")) {
            this.setDate(certificateForm);
            return mapping.findForward("printTCChrist");
        }
        this.setDate(certificateForm);
        return mapping.findForward("printTCChrist");
    }
    
    public ActionForward initTCMC(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) {
        final TransferCertificateForm certificateForm = (TransferCertificateForm)form;
        try {
            certificateForm.resetFields();
            certificateForm.setToCollege("Christ");
            certificateForm.setDuplicate("No");
            this.initsetDataToForm(certificateForm, request);
            this.setDate(certificateForm);
        }
        catch (Exception e) {
            final String msg = super.handleApplicationException(e);
            certificateForm.setErrorMessage(msg);
            certificateForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        return mapping.findForward("transferCertificate");
    }
    
    public ActionForward initReprintTC(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) {
        final TransferCertificateForm certificateForm = (TransferCertificateForm)form;
        try {
            certificateForm.resetFields();
            certificateForm.setToCollege("Cjc");
            this.initsetDataToForm(certificateForm, request);
        }
        catch (Exception e) {
            final String msg = super.handleApplicationException(e);
            certificateForm.setErrorMessage(msg);
            certificateForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        return mapping.findForward("initTCReprint");
    }
    
    public ActionForward initReprintTCMC(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) {
        final TransferCertificateForm certificateForm = (TransferCertificateForm)form;
        try {
            certificateForm.resetFields();
            certificateForm.setToCollege("Christ");
            this.initsetDataToForm(certificateForm, request);
            this.setDate(certificateForm);
        }
        catch (Exception e) {
            final String msg = super.handleApplicationException(e);
            certificateForm.setErrorMessage(msg);
            certificateForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        return mapping.findForward("initTCReprint");
    }
    
    public ActionForward rePrintTC(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) {
        final TransferCertificateForm certificateForm = (TransferCertificateForm)form;
        certificateForm.setFlag(false);
        try {
            if (certificateForm.getRePrintOnlyTc() == null) {
                certificateForm.setRePrintOnlyTc("false");
            }
            certificateForm.setDuplicate("no");
            certificateForm.setTcType((String)null);
            certificateForm.setReprint(true);
            final ActionErrors errors = certificateForm.validate(mapping, request);
            if (errors.isEmpty()) {
                List<PrintTcDetailsTo> studentList = null;
                if (certificateForm.getRePrintOnlyTc().equalsIgnoreCase("true")) {
                    studentList = TransferCertificateHandler.getInstance().getStudentsByRegNoForReprintOnlyTc(certificateForm.getFromUsn(), certificateForm.getAcademicYear(), certificateForm.getClasses(), request, certificateForm);
                }
                else {
                    studentList = TransferCertificateHandler.getInstance().getStudentsByRegNo(certificateForm.getFromUsn(), certificateForm.getAcademicYear(), certificateForm.getClasses(), request, certificateForm);
                }
                certificateForm.setStudentList((List)studentList);
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
        if (certificateForm.getToCollege() != null && certificateForm.getToCollege().equalsIgnoreCase("Cjc")) {
            this.setDate(certificateForm);
            return mapping.findForward("printTCChrist");
        }
        if (certificateForm.getToCollege() != null && certificateForm.getRePrintOnlyTc().equalsIgnoreCase("true")) {
            this.setDate(certificateForm);
            return mapping.findForward("printTCChrist");
        }
        this.setDate(certificateForm);
        return mapping.findForward("printTCChrist");
    }
    
    public ActionForward getCandidatesForTCPrint(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        TransferCertificateAction.log.info((Object)"Entered BoardDetailsAction - getCandidates");
        final TransferCertificateForm certificateForm = (TransferCertificateForm)form;
        certificateForm.setReprint(false);
        final ActionErrors errors = certificateForm.validate(mapping, request);
        List<TCDetailsTO> allStudents = null;
            if (errors.isEmpty()) {
                    try {
                        if (certificateForm.getClasses() == null || certificateForm.getClasses().isEmpty()) {
                            errors.add("error", new ActionError("knowledgepro.att.condon.class.required"));
                            this.saveErrors(request, errors);
                            this.initsetDataToForm(certificateForm, request);
                            return mapping.findForward("transferCertificate");
                        }
                        allStudents = TransferCertificateHandler.getInstance().getStudentsByName(certificateForm.getStudentName(), request, certificateForm);
                        if (allStudents.isEmpty()) {
                            errors.add("error", new ActionError("knowledgepro.admission.norecordsfound"));
                            this.saveErrors(request, errors);
                            this.initsetDataToForm(certificateForm, request);
                            TransferCertificateAction.log.info((Object)"Exit getCandidates size 0");
                            return mapping.findForward("transferCertificate");
                        }
                    }
                    catch (Exception exception) {
                        final String msg = super.handleApplicationException(exception);
                        certificateForm.setErrorMessage(msg);
                        certificateForm.setErrorStack(exception.getMessage());
                        return mapping.findForward(CMSConstants.ERROR_PAGE);
                    }
                
                certificateForm.setAllStudentsForTcPrint((List)allStudents);
                this.initsetDataToForm(certificateForm, request);
                TransferCertificateAction.log.info((Object)"Entered ScoreSheetAction - getCandidates");
                return mapping.findForward("transferCertificate");
            }
        this.addErrors(request, (ActionMessages)errors);
        TransferCertificateAction.log.info((Object)"Exit getCandidates errors not empty ");
        return mapping.findForward("transferCertificate");
    }
    
    public ActionForward getCandidatesForTCRePrint(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        TransferCertificateAction.log.info((Object)"Entered BoardDetailsAction - getCandidates");
        final TransferCertificateForm certificateForm = (TransferCertificateForm)form;
        certificateForm.setReprint(true);
        final ActionErrors errors = certificateForm.validate(mapping, request);
        List<TCDetailsTO> allStudents = null;
            if (errors.isEmpty()) {
                    try {
                        allStudents = TransferCertificateHandler.getInstance().getStudentsByName(certificateForm.getStudentName(), request, certificateForm);
                        if (allStudents.isEmpty()) {
                            errors.add("error", new ActionError("knowledgepro.admission.norecordsfound"));
                            this.saveErrors(request, errors);
                            TransferCertificateAction.log.info((Object)"Exit getCandidates size 0");
                            return mapping.findForward("initTCReprint");
                        }
                    }
                    catch (Exception exception) {
                        final String msg = super.handleApplicationException(exception);
                        certificateForm.setErrorMessage(msg);
                        certificateForm.setErrorStack(exception.getMessage());
                        return mapping.findForward(CMSConstants.ERROR_PAGE);
                    }
                certificateForm.setAllStudentsForTcPrint((List)allStudents);
                TransferCertificateAction.log.info((Object)"Entered ScoreSheetAction - getCandidates");
                return mapping.findForward("initTCReprint");
            }
        this.addErrors(request, (ActionMessages)errors);
        TransferCertificateAction.log.info((Object)"Exit getCandidates errors not empty ");
        return mapping.findForward("initTCReprint");
    }
    
    public ActionForward initReprintTCByClass(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) {
        final TransferCertificateForm certificateForm = (TransferCertificateForm)form;
        try {
            certificateForm.resetFields();
            certificateForm.setToCollege("Cjc");
            certificateForm.setReprint(true);
            this.initsetDataToForm(certificateForm, request);
        }
        catch (Exception e) {
            final String msg = super.handleApplicationException(e);
            certificateForm.setErrorMessage(msg);
            certificateForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        return mapping.findForward("transferCertificate");
    }
    
    public ActionForward rePrintTCByClass(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) {
        final TransferCertificateForm certificateForm = (TransferCertificateForm)form;
        certificateForm.setReprint(true);
        try {
            final ActionErrors errors = certificateForm.validate(mapping, request);
            if (errors.isEmpty()) {
                final List<PrintTcDetailsTo> studentList = TransferCertificateHandler.getInstance().getStudentsByClassForReprint(request, certificateForm);
                certificateForm.setStudentList((List)studentList);
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
        if (certificateForm.getToCollege().equalsIgnoreCase("Cjc")) {
            return mapping.findForward("printTCChrist");
        }
        return mapping.findForward("printTCChrist");
    }
    
    public ActionForward printTCForCjc(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) {
        final TransferCertificateForm certificateForm = (TransferCertificateForm)form;
        try {
            final ActionErrors errors = new ActionErrors();
            certificateForm.setReprint(false);
            if (errors.isEmpty()) {
                final List<PrintTcDetailsTo> studentList = TransferCertificateHandler.getInstance().getStudentsByClassForCjc(certificateForm, request);
                certificateForm.setStudentList((List)studentList);
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
        if (certificateForm.getToCollege() != null && certificateForm.getToCollege().equalsIgnoreCase("Cjc")) {
            return mapping.findForward("printTCChrist");
        }
        return mapping.findForward("printTCChrist");
    }
    
    public ActionForward initChristTCFormat(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) {
        TransferCertificateAction.log.info((Object)"Entered initChristTCFormat in TransferCertificate Action");
        final TransferCertificateForm certificateForm = (TransferCertificateForm)form;
        try {
            certificateForm.resetFields();
            certificateForm.setReprint(false);
            this.initsetDataToForm(certificateForm, request);
        }
        catch (Exception e) {
            final String msg = super.handleApplicationException(e);
            certificateForm.setErrorMessage(msg);
            certificateForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        TransferCertificateAction.log.info((Object)"Exit initChristTCFormat in TransferCertificate Action");
        return mapping.findForward("initTransferCertificate");
    }
    
    public ActionForward printChristTransferCertificate(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        TransferCertificateAction.log.info((Object)"Entered TransferCertificate Action- printChristTransferCertificate");
        final TransferCertificateForm certificateForm = (TransferCertificateForm)form;
        certificateForm.setReprint(false);
        final ActionErrors errors = certificateForm.validate(mapping, request);
            if (errors.isEmpty()) {
                    try {
                        final ITransferCertificateTransaction certificateTransaction = (ITransferCertificateTransaction)new TransferCertificateTransactionImpl();
                        final TCNumber number = certificateTransaction.getOnlyTCNumber(Integer.parseInt(certificateForm.getYear()), "Christ", true);
                        if (number == null) {
                            errors.add("error", new ActionError("knowledgepro.admission.tcMaster.configure"));
                            this.saveErrors(request, errors);
                            this.initsetDataToForm(certificateForm, request);
                            TransferCertificateAction.log.info((Object)"Exit TransferCertificate Action- printChristTransferCertificate size 0");
                            return mapping.findForward("initTransferCertificate");
                        }
                        TransferCertificateHandler.getInstance().getTCForStudentsByClass(request, certificateForm, number);
                    }
                    catch (Exception exception) {
                        final String msg = super.handleApplicationException(exception);
                        certificateForm.setErrorMessage(msg);
                        certificateForm.setErrorStack(exception.getMessage());
                        return mapping.findForward(CMSConstants.ERROR_PAGE);
                    }
                TransferCertificateAction.log.info((Object)"Entered TransferCertificate Action - printChristTransferCertificate");
                return mapping.findForward("initTransferCertificate");
            }
        this.addErrors(request, (ActionMessages)errors);
        this.initsetDataToForm(certificateForm, request);
        TransferCertificateAction.log.info((Object)"Exit TransferCertificate Action - printChristTransferCertificate errors not empty ");
        return mapping.findForward("initTransferCertificate");
    }
    
    public ActionForward initReprint(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) {
        final TransferCertificateForm certificateForm = (TransferCertificateForm)form;
        try {
            certificateForm.setRePrintOnlyTc("true");
            certificateForm.resetFields();
            certificateForm.setToCollege("Christ");
            this.initsetDataToForm(certificateForm, request);
        }
        catch (Exception e) {
            final String msg = super.handleApplicationException(e);
            certificateForm.setErrorMessage(msg);
            certificateForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        return mapping.findForward("initTCReprint");
    }
}