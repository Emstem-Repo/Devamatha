package com.kp.cms.handlers.admission;

import com.kp.cms.bo.admin.StudentTCDetails;
import com.kp.cms.to.admission.TCDetailsTO;
import java.math.BigInteger;
import com.kp.cms.bo.admin.TCNumber;
import java.util.Collections;
import com.kp.cms.helpers.admission.TransferCertificateHelper;
import com.kp.cms.bo.admin.Student;
import java.util.ArrayList;
import com.kp.cms.to.admission.PrintTcDetailsTo;
import java.util.List;
import org.apache.struts.action.ActionErrors;
import com.kp.cms.forms.admission.TransferCertificateForm;
import javax.servlet.http.HttpServletRequest;
import com.kp.cms.transactionsimpl.admin.TCMasterTransactionImpl;
import com.kp.cms.transactionsimpl.admission.TransferCertificateTransactionImpl;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.transactions.admin.ITCMasterTransaction;
import com.kp.cms.transactions.admission.ITransferCertificateTransaction;
import org.apache.commons.logging.Log;

public class TransferCertificateHandler
{
    private static final Log log;
    public static volatile TransferCertificateHandler certificateHandler;
    ITransferCertificateTransaction certificateTransaction;
    ITCMasterTransaction masterTransactionImpl;
    
    static {
        log = LogFactory.getLog((Class)TransferCertificateHandler.class);
        TransferCertificateHandler.certificateHandler = null;
    }
    
    public TransferCertificateHandler() {
        this.certificateTransaction = (ITransferCertificateTransaction)new TransferCertificateTransactionImpl();
        this.masterTransactionImpl = (ITCMasterTransaction)TCMasterTransactionImpl.getInstance();
    }
    
    public static TransferCertificateHandler getInstance() {
        if (TransferCertificateHandler.certificateHandler == null) {
            TransferCertificateHandler.certificateHandler = new TransferCertificateHandler();
        }
        return TransferCertificateHandler.certificateHandler;
    }
    
    public List<PrintTcDetailsTo> getStudentsByClass(final HttpServletRequest request, final TransferCertificateForm form, final ActionErrors errors) throws Exception {
        List<PrintTcDetailsTo> studentList = new ArrayList<PrintTcDetailsTo>();
        final List<Student> studentBoList = (List<Student>)this.certificateTransaction.getStudentList(form.getClasses(), form.getFromUsn(), form.getToUsn(), form.getDuplicate(), form.getStudentId());
        if (studentBoList == null || studentBoList.isEmpty()) {
            return studentList;
        }
        if (form.getTcFor() == null || form.getTcFor().equalsIgnoreCase("undefined")) {
            form.setTcFor("St Berchmans");
        }
        final List<Integer> discontinuedStudentId = (List<Integer>)this.certificateTransaction.getDiscontinuedStudentId();
        final List<Student> studentsTakenTcList = new ArrayList<Student>();
        studentList = TransferCertificateHelper.getInstance().convertStudentBoToTo(studentBoList, studentsTakenTcList, request, form, discontinuedStudentId);
        if (studentsTakenTcList.size() != 0) {
            this.certificateTransaction.updateStudentsTcNo((List)studentsTakenTcList);
        }
        return studentList;
    }
    
    public String getOldClassNameByStudentId(final int studentId) throws Exception {
        final String oldClassName = this.certificateTransaction.getOldClassNameByStudentId(studentId);
        return oldClassName;
    }
    
    public List<PrintTcDetailsTo> getStudentsByClassForReprint(final HttpServletRequest request, final TransferCertificateForm form) throws Exception {
        final List<Integer> discontinuedStudentsList = (List<Integer>)this.certificateTransaction.getDiscontinuedStudentId();
        final List<Student> studentBo = (List<Student>)this.certificateTransaction.getStudentListForReprint(form.getClasses(), form.getFromUsn(), form.getToUsn(), form.getIncludeFail(), form.getTcType());
        final List<PrintTcDetailsTo> studentList = TransferCertificateHelper.getInstance().convertStudentBoToToForReprint(studentBo, request, discontinuedStudentsList, form);
        Collections.sort(studentList);
        return studentList;
    }
    
    public List<PrintTcDetailsTo> getStudentsByClassForCjc(final TransferCertificateForm form, final HttpServletRequest request) throws Exception {
        List<PrintTcDetailsTo> studentList = new ArrayList<PrintTcDetailsTo>();
        final List<Student> studentBoList = (List<Student>)this.certificateTransaction.getStudentListForCjc(form.getClasses(), form.getFromUsn(), form.getToUsn(), form.getTcType(), form.getIncludeFail());
        if (form.getTcType().equalsIgnoreCase("Duplicate") || form.getTcType().equalsIgnoreCase("Duplicate(Discontinued)")) {
            form.setDuplicate("yes");
        }
        else {
            form.setDuplicate("no");
        }
        final List<Integer> discontinuedStudentId = (List<Integer>)this.certificateTransaction.getDiscontinuedStudentId();
        final List<Student> studentsTakenTcList = new ArrayList<Student>();
        studentList = TransferCertificateHelper.getInstance().convertStudentBoToTo(studentBoList, studentsTakenTcList, request, form, discontinuedStudentId);
        if (studentsTakenTcList.size() != 0) {
            this.certificateTransaction.updateStudentsTcNo((List)studentsTakenTcList);
        }
        Collections.sort(studentList);
        return studentList;
    }
    
    public List<PrintTcDetailsTo> getTCForStudentsByClass(final HttpServletRequest request, final TransferCertificateForm certificateForm, final TCNumber number) throws Exception {
        final String query = TransferCertificateHelper.getInstance().getQueryForInputSearch(certificateForm);
        final List<Student> studentBoList = (List<Student>)this.certificateTransaction.getStudentListByInputQuery(query);
        return null;
    }
    
    public Integer getAdmittedSemester(final int studentId) throws Exception {
        final int oldClassId = this.certificateTransaction.getAdmittedSemester(studentId);
        return oldClassId;
    }
    
    public String getOldClassNameByStudentIdNEW(final int studentId, final int Sem) throws Exception {
        final String oldClassName = this.certificateTransaction.getOldClassNameByStudentIdNew(studentId, Sem);
        return oldClassName;
    }
    
    public String getCourseScheme(final int courseId, final Integer appliedYear) throws Exception {
        return this.certificateTransaction.getCourseSchemeId(courseId, appliedYear);
    }
    
    public List<PrintTcDetailsTo> getStudentsByClassOnlyTC(final HttpServletRequest request, final TransferCertificateForm form, final ActionErrors errors) throws Exception {
        List<PrintTcDetailsTo> studentList = new ArrayList<PrintTcDetailsTo>();
        final List<Student> studentBoList = (List<Student>)this.certificateTransaction.getStudentList(form.getClasses(), form.getFromUsn(), form.getToUsn(), form.getDuplicate(), form.getStudentId());
        if (studentBoList == null || studentBoList.isEmpty()) {
            return studentList;
        }
        if (form.getTcFor() == null || form.getTcFor().equalsIgnoreCase("undefined")) {
            form.setTcFor("DevaMatha");
        }
        final List<Student> studentsTakenTcList = new ArrayList<Student>();
        studentList = TransferCertificateHelper.getInstance().convertStudentBoToToOnlyTC(studentBoList, studentsTakenTcList, request, form);
        if (studentsTakenTcList.size() != 0 && !form.getDuplicate().equalsIgnoreCase("yes")) {
            this.certificateTransaction.updateStudentsTcNo((List)studentsTakenTcList);
        }
        Collections.sort(studentList);
        return studentList;
    }
    
    public BigInteger getRejoinYear(final int studentId) throws Exception {
        return this.certificateTransaction.getRejoinYear(studentId);
    }
    
    public List<TCDetailsTO> getStudentsByName(final String name, final HttpServletRequest request, final TransferCertificateForm certificateForm) throws Exception {
        final List<StudentTCDetails> studentBo = (List<StudentTCDetails>)this.certificateTransaction.getStudentsByName(name, certificateForm.getReprint(), certificateForm.getClasses());
        final List<TCDetailsTO> studentList = TransferCertificateHelper.getInstance().convertStudentTcDetailsBoToToForReprintOnlyTc(studentBo, request, certificateForm);
        return studentList;
    }
    
    public List<PrintTcDetailsTo> getStudentsByRegNo(final String reg, final String academicYear, final String classID, final HttpServletRequest request, final TransferCertificateForm form) throws Exception {
        final List<Student> studentBo = (List<Student>)this.certificateTransaction.getStudentsByRegNo(reg, academicYear, classID, form.getToUsn(), form.getStudentId(), form.getReprint());
        final List<Integer> discontinuedStudentsList = (List<Integer>)this.certificateTransaction.getDiscontinuedStudentId();
        final List<PrintTcDetailsTo> studentList = TransferCertificateHelper.getInstance().convertStudentBoToToForReprintOnlyTc(studentBo, request, form);
        return studentList;
    }
    
    public List<PrintTcDetailsTo> getStudentsByRegNoForReprintOnlyTc(final String reg, final String academicYear, final String classID, final HttpServletRequest request, final TransferCertificateForm certificateForm) throws Exception {
        final List<Student> studentBo = (List<Student>)this.certificateTransaction.getStudentsByRegNo(reg, academicYear, classID, certificateForm.getToUsn(), certificateForm.getStudentId(), certificateForm.getReprint());
        final List<PrintTcDetailsTo> studentList = TransferCertificateHelper.getInstance().convertStudentBoToToForReprintOnlyTc(studentBo, request, certificateForm);
        return studentList;
    }
}