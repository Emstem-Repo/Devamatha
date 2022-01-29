package com.kp.cms.forms.admission;

import org.apache.struts.action.ActionErrors;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import com.kp.cms.to.admission.PrintTcDetailsTo;
import com.kp.cms.to.admission.CharacterAndConductTO;
import com.kp.cms.to.admission.TCDetailsTO;
import com.kp.cms.to.admission.BoardDetailsTO;
import java.util.List;
import com.kp.cms.forms.BaseActionForm;

public class TCDetailsForm extends BaseActionForm
{
    private static final long serialVersionUID = 1L;
    private String className;
    List<BoardDetailsTO> boardList;
    private String registerNo;
    private String studentId;
    private TCDetailsTO tcDetailsTO;
    List<CharacterAndConductTO> list;
    private String month;
    private String dateOfApplication;
    private String feePaid;
    private String dateOfLeaving;
    private String reasonOfLeaving;
    private String characterId;
    private String scholarship;
    private String publicExamName;
    private String showRegisterNo;
    private String passed;
    private String prefixForTC;
    private String startingNumber;
    private boolean applied;
    private boolean flag;
    private boolean reprint;
    private String printOnlyTc;
    List<PrintTcDetailsTo> studentList;
    private String toCollege;
    private String classes;
    private String fromUsn;
    private String toUsn;
    private String duplicate;
    private String tcFor;
    private String tcDate;
    private String tcType;
    private boolean isAided;
    private String dateOfIssue;
    private String admissionDate;
    private String subCode;
    
    public TCDetailsForm() {
        this.duplicate = "No";
    }
    
    public String getAdmissionDate() {
        return this.admissionDate;
    }
    
    public void setAdmissionDate(final String admissionDate) {
        this.admissionDate = admissionDate;
    }
    
    public String getClassName() {
        return this.className;
    }
    
    public void setClassName(final String className) {
        this.className = className;
    }
    
    public List<BoardDetailsTO> getBoardList() {
        return this.boardList;
    }
    
    public void setBoardList(final List<BoardDetailsTO> boardList) {
        this.boardList = boardList;
    }
    
    public String getRegisterNo() {
        return this.registerNo;
    }
    
    public void setRegisterNo(final String registerNo) {
        this.registerNo = registerNo;
    }
    
    public String getStudentId() {
        return this.studentId;
    }
    
    public void setStudentId(final String studentId) {
        this.studentId = studentId;
    }
    
    public TCDetailsTO getTcDetailsTO() {
        return this.tcDetailsTO;
    }
    
    public void setTcDetailsTO(final TCDetailsTO tcDetailsTO) {
        this.tcDetailsTO = tcDetailsTO;
    }
    
    public List<CharacterAndConductTO> getList() {
        return this.list;
    }
    
    public void setList(final List<CharacterAndConductTO> list) {
        this.list = list;
    }
    
    public String getMonth() {
        return this.month;
    }
    
    public void setMonth(final String month) {
        this.month = month;
    }
    
    public String getDateOfApplication() {
        return this.dateOfApplication;
    }
    
    public void setDateOfApplication(final String dateOfApplication) {
        this.dateOfApplication = dateOfApplication;
    }
    
    public String getFeePaid() {
        return this.feePaid;
    }
    
    public void setFeePaid(final String feePaid) {
        this.feePaid = feePaid;
    }
    
    public String getDateOfLeaving() {
        return this.dateOfLeaving;
    }
    
    public void setDateOfLeaving(final String dateOfLeaving) {
        this.dateOfLeaving = dateOfLeaving;
    }
    
    public String getReasonOfLeaving() {
        return this.reasonOfLeaving;
    }
    
    public void setReasonOfLeaving(final String reasonOfLeaving) {
        this.reasonOfLeaving = reasonOfLeaving;
    }
    
    public String getCharacterId() {
        return this.characterId;
    }
    
    public void setCharacterId(final String characterId) {
        this.characterId = characterId;
    }
    
    public String getScholarship() {
        return this.scholarship;
    }
    
    public void setScholarship(final String scholarship) {
        this.scholarship = scholarship;
    }
    
    public String getPublicExamName() {
        return this.publicExamName;
    }
    
    public void setPublicExamName(final String publicExamName) {
        this.publicExamName = publicExamName;
    }
    
    public String getShowRegisterNo() {
        return this.showRegisterNo;
    }
    
    public void setShowRegisterNo(final String showRegisterNo) {
        this.showRegisterNo = showRegisterNo;
    }
    
    public String getPassed() {
        return this.passed;
    }
    
    public void setPassed(final String passed) {
        this.passed = passed;
    }
    
    public String getPrefixForTC() {
        return this.prefixForTC;
    }
    
    public void setPrefixForTC(final String prefixForTC) {
        this.prefixForTC = prefixForTC;
    }
    
    public String getStartingNumber() {
        return this.startingNumber;
    }
    
    public void setStartingNumber(final String startingNumber) {
        this.startingNumber = startingNumber;
    }
    
    public boolean getApplied() {
        return this.applied;
    }
    
    public void setApplied(final boolean applied) {
        this.applied = applied;
    }
    
    public boolean isFlag() {
        return this.flag;
    }
    
    public void setFlag(final boolean flag) {
        this.flag = flag;
    }
    
    public boolean isReprint() {
        return this.reprint;
    }
    
    public void setReprint(final boolean reprint) {
        this.reprint = reprint;
    }
    
    public String getPrintOnlyTc() {
        return this.printOnlyTc;
    }
    
    public void setPrintOnlyTc(final String printOnlyTc) {
        this.printOnlyTc = printOnlyTc;
    }
    
    public List<PrintTcDetailsTo> getStudentList() {
        return this.studentList;
    }
    
    public void setStudentList(final List<PrintTcDetailsTo> studentList) {
        this.studentList = studentList;
    }
    
    public String getToCollege() {
        return this.toCollege;
    }
    
    public void setToCollege(final String toCollege) {
        this.toCollege = toCollege;
    }
    
    public String getClasses() {
        return this.classes;
    }
    
    public void setClasses(final String classes) {
        this.classes = classes;
    }
    
    public String getFromUsn() {
        return this.fromUsn;
    }
    
    public void setFromUsn(final String fromUsn) {
        this.fromUsn = fromUsn;
    }
    
    public String getToUsn() {
        return this.toUsn;
    }
    
    public void setToUsn(final String toUsn) {
        this.toUsn = toUsn;
    }
    
    public String getDuplicate() {
        return this.duplicate;
    }
    
    public void setDuplicate(final String duplicate) {
        this.duplicate = duplicate;
    }
    
    public String getTcFor() {
        return this.tcFor;
    }
    
    public void setTcFor(final String tcFor) {
        this.tcFor = tcFor;
    }
    
    public String getTcDate() {
        return this.tcDate;
    }
    
    public void setTcDate(final String tcDate) {
        this.tcDate = tcDate;
    }
    
    public String getTcType() {
        return this.tcType;
    }
    
    public void setTcType(final String tcType) {
        this.tcType = tcType;
    }
    
    public void resetFields() {
        super.setYear((String)null);
        super.setAcademicYear((String)null);
        this.registerNo = null;
        this.studentId = null;
        this.tcDetailsTO = new TCDetailsTO();
        this.month = null;
        this.dateOfApplication = null;
        this.dateOfLeaving = null;
        this.feePaid = "yes";
        this.reasonOfLeaving = null;
        this.characterId = null;
        this.passed = "yes";
        this.scholarship = "no";
        this.publicExamName = null;
        this.showRegisterNo = "yes";
        this.prefixForTC = null;
        this.startingNumber = null;
        this.isAided = true;
        this.dateOfIssue = null;
    }
    
    public void reset() {
        this.publicExamName = null;
        this.tcDetailsTO = new TCDetailsTO();
    }
    
    public ActionErrors validate(final ActionMapping mapping, final HttpServletRequest request) {
        final String formName = request.getParameter("formName");
        final ActionErrors actionErrors = super.validate(mapping, request, formName);
        return actionErrors;
    }
    
    public boolean getIsAided() {
        return this.isAided;
    }
    
    public void setIsAided(final boolean isAided) {
        this.isAided = isAided;
    }
    
    public String getDateOfIssue() {
        return this.dateOfIssue;
    }
    
    public void setDateOfIssue(final String dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }
    
    public String getSubCode() {
        return this.subCode;
    }
    
    public void setSubCode(final String subCode) {
        this.subCode = subCode;
    }
}