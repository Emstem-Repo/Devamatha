package com.kp.cms.handlers.admission;

import java.util.Collections;
import java.util.ArrayList;
import com.kp.cms.to.admission.PrintTcDetailsTo;
import org.apache.struts.action.ActionErrors;
import javax.servlet.http.HttpServletRequest;
import com.kp.cms.bo.admin.CharacterAndConduct;
import com.kp.cms.to.admission.CharacterAndConductTO;
import com.kp.cms.to.admission.TCDetailsTO;
import com.kp.cms.bo.admin.StudentTCDetails;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.transactions.admission.ITCDetailsTransaction;
import com.kp.cms.helpers.admission.TCDetailsHelper;
import com.kp.cms.transactionsimpl.admission.TCDetailsTransactionImpl;
import com.kp.cms.to.admission.BoardDetailsTO;
import java.util.List;
import com.kp.cms.forms.admission.TCDetailsForm;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class TCDetailsHandler
{
    private static volatile TCDetailsHandler tCDetailsHandler;
    private static final Log log;
    
    static {
        TCDetailsHandler.tCDetailsHandler = null;
        log = LogFactory.getLog((Class)TCDetailsHandler.class);
    }
    
    private TCDetailsHandler() {
    }
    
    public static TCDetailsHandler getInstance() {
        if (TCDetailsHandler.tCDetailsHandler == null) {
            TCDetailsHandler.tCDetailsHandler = new TCDetailsHandler();
        }
        return TCDetailsHandler.tCDetailsHandler;
    }
    
    public List<BoardDetailsTO> getListOfCandidates(final TCDetailsForm tcDetailsForm) throws Exception {
        TCDetailsHandler.log.info((Object)"Entered into getListOfCandidates- TCDetailsHandler");
        final ITCDetailsTransaction transaction = TCDetailsTransactionImpl.getInstance();
        final String query = TCDetailsHelper.getInstance().getSearchQuery(tcDetailsForm);
        final List<Student> studentList = transaction.getStudentDetails(query);
        TCDetailsHandler.log.info((Object)"Exit from getListOfCandidates- TCDetailsHandler");
        return TCDetailsHelper.getInstance().convertBotoToList(studentList);
    }
    
    public void getStudentTCDetails(final TCDetailsForm tcDetailsForm) throws Exception {
        TCDetailsHandler.log.info((Object)"Entered into getStudentTCDetails- BoardDetailsHandler");
        final String query = TCDetailsHelper.getInstance().getSearchQueryForTCDetails(tcDetailsForm);
        final ITCDetailsTransaction transaction = TCDetailsTransactionImpl.getInstance();
        final Student student = transaction.getStudentTCDetails(query);
        TCDetailsHelper.getInstance().convertBotoForm(student, tcDetailsForm);
        TCDetailsHandler.log.info((Object)"Exit from getStudentTCDetails- TCDetailsHandler");
    }
    
    public void getAllStudentTCDetailsByClass(final TCDetailsForm tcDetailsForm) throws Exception {
        TCDetailsHandler.log.info((Object)"Entered into getStudentTCDetails- BoardDetailsHandler");
        final String subCode = null;
        final ITCDetailsTransaction transaction = TCDetailsTransactionImpl.getInstance();
        String subjects = transaction.getSubjectsForAllStudentsByClass(tcDetailsForm);
        subjects = transaction.getStudentSubjectCode(tcDetailsForm.getClassId());
        final ExamDefinitionBO bo = transaction.getExamForAllStudentsByClass(tcDetailsForm);
        final  StudentTCDetails tcdetails=transaction.getStudentTCDetailsByClass(tcDetailsForm.getClassId());
        TCDetailsHelper.getInstance().convertBotoFormAllStudentsByClass(subjects, bo, tcDetailsForm,tcdetails);
        tcDetailsForm.setSubCode(subjects);
        TCDetailsHandler.log.info((Object)"Exit from getStudentTCDetails- TCDetailsHandler");
    }
    
    public boolean updateStudentTCDetails(final TCDetailsForm tcDetailsForm) throws Exception {
        final ITCDetailsTransaction transaction = TCDetailsTransactionImpl.getInstance();
        final StudentTCDetails tcDetails = transaction.getStudentFromTcDetails(tcDetailsForm);
        final TCDetailsTO to = tcDetailsForm.getTcDetailsTO();
        if (to != null && tcDetails != null && to.getId() <= 0 && tcDetails.getId() > 0) {
            to.setId(tcDetails.getId());
            tcDetailsForm.setTcDetailsTO(to);
        }
        final StudentTCDetails bo = TCDetailsHelper.getInstance().convertTotoBo(tcDetailsForm);
        return transaction.saveStudentTCDetails(bo);
    }
    
    public List<CharacterAndConductTO> getCharacterAndConductList() throws Exception {
        final ITCDetailsTransaction transaction = TCDetailsTransactionImpl.getInstance();
        final List<CharacterAndConduct> list = transaction.getAllCharacterAndConduct();
        return TCDetailsHelper.getInstance().convertBoListToTOList(list);
    }
    
    public Boolean getUpdateStatus(final TCDetailsForm tcDetailsForm) throws Exception {
        final ITCDetailsTransaction transaction = TCDetailsTransactionImpl.getInstance();
        return transaction.updateStudentTCDetails(tcDetailsForm);
    }
    
    public boolean verifyGeneratedTCNumbers(final TCDetailsForm tcDetailsForm) throws Exception {
        final ITCDetailsTransaction transaction = TCDetailsTransactionImpl.getInstance();
        final String query = TCDetailsHelper.getInstance().getSearchQuery(tcDetailsForm);
        final List<Student> studentOfThatClass = transaction.getStudentDetails(query);
        return TCDetailsHelper.getInstance().checkForExistingTCNumbersInOtherClasses(tcDetailsForm.getPrefixForTC(), Integer.parseInt(tcDetailsForm.getStartingNumber()), studentOfThatClass,tcDetailsForm);
    }
    
    public boolean verifyStudentsPresent(final TCDetailsForm tcDetailsForm) throws Exception {
        final ITCDetailsTransaction transaction = TCDetailsTransactionImpl.getInstance();
        final String query = TCDetailsHelper.getInstance().getSearchQuery(tcDetailsForm);
        final List<Student> studentOfThatClass = transaction.getStudentDetails(query);
        return !studentOfThatClass.isEmpty() || studentOfThatClass.size() != 0;
    }
    
    public boolean verifyStudentsAllSaved(final TCDetailsForm tcDetailsForm) throws Exception {
        final ITCDetailsTransaction transaction = TCDetailsTransactionImpl.getInstance();
        final List<Integer> studentList = (List<Integer>)transaction.getStudentFromTC(Integer.parseInt(tcDetailsForm.getClassId()));
        final String query = TCDetailsHelper.getInstance().getSearchQuery(tcDetailsForm);
        final List<Student> studentOfThatClass = transaction.getStudentDetails(query);
        return studentOfThatClass.size() <= 0 || studentList.size() != studentOfThatClass.size();
    }
    
    public void checkDuplicationForTcStudent(final TCDetailsForm tcDetailsForm) throws Exception {
        TCDetailsHandler.log.info((Object)"Entered into getStudentTCDetails- BoardDetailsHandler");
        final ITCDetailsTransaction transaction = TCDetailsTransactionImpl.getInstance();
        final boolean applied = transaction.checkDuplicationOfTc(tcDetailsForm);
        tcDetailsForm.setApplied(applied);
        TCDetailsHandler.log.info((Object)"Exit from getStudentTCDetails- TCDetailsHandler");
    }
    
    public List<PrintTcDetailsTo> getStudentsByClassOnlyTC(final HttpServletRequest request, final TCDetailsForm form, final ActionErrors errors) throws Exception {
        List<PrintTcDetailsTo> studentList = new ArrayList<PrintTcDetailsTo>();
        final ITCDetailsTransaction transaction = TCDetailsTransactionImpl.getInstance();
        final Student studentBoList = transaction.getStudentList(form.getStudentId());
        form.setToCollege("Christ");
        if (studentBoList == null) {
            return studentList;
        }
        if (form.getTcFor() == null || form.getTcFor().equalsIgnoreCase("undefined")) {
            form.setTcFor("St Berchmans");
        }
        final List<Student> studentsTakenTcList = new ArrayList<Student>();
        studentList = TCDetailsHelper.getInstance().convertStudentBoToToOnlyTC(studentBoList, studentsTakenTcList, request, form);
        transaction.updateStudentsTcNo(studentsTakenTcList);
        Collections.sort(studentList);
        return studentList;
    }
    
    public List<PrintTcDetailsTo> getStudentsByClass(final HttpServletRequest request, final TCDetailsForm form, final ActionErrors errors) throws Exception {
        List<PrintTcDetailsTo> studentList = new ArrayList<PrintTcDetailsTo>();
        final ITCDetailsTransaction transaction = TCDetailsTransactionImpl.getInstance();
        final Student studentBo = transaction.getStudentList(form.getStudentId());
        if (studentBo == null) {
            return studentList;
        }
        if (form.getTcFor() == null || form.getTcFor().equalsIgnoreCase("undefined")) {
            form.setTcFor("St Berchmans");
        }
        final List<Integer> discontinuedStudentId = transaction.getDiscontinuedStudentId();
        final List<Student> studentsTakenTcList = new ArrayList<Student>();
        studentList = TCDetailsHelper.getInstance().convertStudentBoToTo(studentBo, studentsTakenTcList, request, form, discontinuedStudentId);
        if (studentsTakenTcList.size() != 0) {
            transaction.updateStudentsTcNo(studentsTakenTcList);
        }
        return studentList;
    }
}