package com.kp.cms.handlers.usermanagement;

import com.kp.cms.transactions.exam.IDownloadHallTicketTransaction;
import com.kp.cms.helpers.exam.ProgressCardHelper;
import com.kp.cms.transactionsimpl.exam.DownloadHallTicketTransactionImpl;
import com.kp.cms.to.admin.ProgressCardTo;
import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.transactions.usermanagement.IStudentLoginTransaction;
import com.kp.cms.bo.admin.EdnQualification;
import java.util.Set;
import com.kp.cms.to.admin.EdnQualificationTO;
import com.kp.cms.bo.admin.DisciplineAndAchivement;
import com.kp.cms.helpers.usermanagement.StudentLoginHelper;
import com.kp.cms.transactionsimpl.usermanagement.StudentLoginTransactionImpl;
import com.kp.cms.to.admission.DisciplineAndAchivementTo;
import org.apache.struts.upload.FormFile;
import com.kp.cms.transactions.exam.IConsolidatedSubjectStreamTransaction;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.transactionsimpl.exam.ConsolidatedSubjectStreamTransactionImpl;
import com.kp.cms.bo.admission.PublishStudentEdit;
import com.kp.cms.bo.admission.PublishStudentEditClasswise;
import com.kp.cms.bo.admin.MBAEntranceExam;
import com.kp.cms.bo.admin.Occupation;
import com.kp.cms.bo.admin.Currency;
import com.kp.cms.bo.admin.Income;
import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.ReligionSection;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.ResidentCategory;
import com.kp.cms.bo.admin.EducationStream;
import com.kp.cms.bo.admin.District;
import com.kp.cms.bo.admin.UGCoursesBO;
import com.kp.cms.bo.admin.Nationality;
import java.math.BigDecimal;
import com.kp.cms.bo.admin.State;
import javax.servlet.http.HttpServletRequest;
import com.kp.cms.to.admin.ApplnDocTO;
import java.text.DateFormat;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.bo.admin.ExtracurricularActivity;
import com.kp.cms.helpers.admission.OnlineApplicationHelper;
import com.kp.cms.to.admission.AdmApplnTO;
import java.text.SimpleDateFormat;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.admin.SubReligionHandler;
import com.kp.cms.handlers.admin.ReligionHandler;
import com.kp.cms.to.admin.CurrencyTO;
import com.kp.cms.handlers.admin.OccupationTransactionHandler;
import com.kp.cms.to.admin.DistrictTO;
import java.util.Collections;
import com.kp.cms.to.admin.StateTO;
import com.kp.cms.handlers.admin.StateHandler;
import com.kp.cms.transactionsimpl.ajax.CommonAjaxImpl;
import com.kp.cms.handlers.admin.CountryHandler;
import com.kp.cms.handlers.admission.OnlineApplicationHandler;
import java.util.Map;
import com.kp.cms.bo.admin.MotherTongue;
import java.util.HashMap;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import java.util.Collection;
import com.kp.cms.bo.admin.StudentExtracurricular;
import java.util.ArrayList;
import com.kp.cms.to.admission.PersonalDataTO;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.PersonalData;
import java.util.Calendar;
import com.kp.cms.forms.admin.SyllabusEntryForm;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.bo.admin.MobileMessaging;
import org.apache.commons.lang.StringUtils;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.actions.exam.NewStudentMarksCorrectionAction;
import com.kp.cms.utilities.jms.MailTO;
import java.io.InputStream;
import com.kp.cms.utilities.print.HtmlPrinter;
import com.kp.cms.bo.admin.GroupTemplate;
import java.util.Date;
import com.kp.cms.handlers.admin.TemplateHandler;
import java.io.IOException;
import java.io.FileNotFoundException;
import com.kp.cms.utilities.CommonUtil;
import java.util.Properties;
import com.kp.cms.bo.sap.SapRegistration;
import com.kp.cms.bo.admin.ConvocationRegistration;
import com.kp.cms.bo.admin.PeersEvaluationOpenSession;
import javax.servlet.http.HttpSession;
import com.kp.cms.bo.admin.Employee;
import java.util.Iterator;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.helpers.usermanagement.LoginTransactionHelper;
import com.kp.cms.to.usermanagement.LoginTransactionTo;
import java.util.List;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.usermanagement.ILoginTransaction;
import com.kp.cms.transactionsimpl.usermanagement.LoginTransactionImpl;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.forms.usermanagement.LoginForm;

public class LoginHandler
{
    private static volatile LoginHandler loginhandler;
    private static final String OTHER = "Other";
    private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
    private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
    
    static {
        LoginHandler.loginhandler = null;
    }
    
    private LoginHandler() {
    }
    
    public static LoginHandler getInstance() {
        if (LoginHandler.loginhandler == null) {
            LoginHandler.loginhandler = new LoginHandler();
        }
        return LoginHandler.loginhandler;
    }
    
    public Users isValiedUser(final LoginForm loginForm) throws ApplicationException {
        final ILoginTransaction loginTransaction = (ILoginTransaction)new LoginTransactionImpl();
        return loginTransaction.verifyUser(loginForm);
    }
    
    public List<LoginTransactionTo> getAccessableModules(final LoginForm loginForm) throws ApplicationException {
        final ILoginTransaction loginTransaction = (ILoginTransaction)new LoginTransactionImpl();
        final List modulesList = loginTransaction.getAccessableModules(loginForm);
        final List<LoginTransactionTo> accessableModulesList = (List<LoginTransactionTo>)LoginTransactionHelper.getInstance().getModulesFromList(modulesList, loginForm);
        return accessableModulesList;
    }
    
    public StudentLogin isValiedStudentUser(final LoginForm loginForm) throws ApplicationException {
        final ILoginTransaction loginTransaction = (ILoginTransaction)new LoginTransactionImpl();
        return loginTransaction.verifyStudentUser(loginForm);
    }
    
    public boolean updateLastLoggedIn(final int userId) throws Exception {
        final ILoginTransaction iLoginTransaction = (ILoginTransaction)new LoginTransactionImpl();
        final Boolean isUpdated = iLoginTransaction.updateLastLoggedIn(userId);
        return isUpdated;
    }
    
    public boolean checkStudentAttendancePercentage(final String studentid, final int classId) throws Exception {
        boolean isCheck = false;
        final String query = "select ac.classSchemewise.classes.name, attStu.student,  sum(a.hoursHeld), sum(case when (attStu.isPresent=1 ) then a.hoursHeld else 0 end), sum(case when (attStu.isCoCurricularLeave=1) then a.hoursHeld else 0 end),  sum(case when (attStu.isOnLeave=1) then a.hoursHeld else 0 end) from Attendance a join a.attendanceStudents attStu join a.attendanceClasses ac where ac.classSchemewise.id= attStu.student.classSchemewise.id and attStu.student.admAppln.isCancelled=0 and a.isCanceled=0 and attStu.student.isHide=0  and ac.classSchemewise.classes.id in (" + classId + ") and attStu.student.id=" + studentid + " group by attStu.student.id ";
        final INewExamMarksEntryTransaction transaction = (INewExamMarksEntryTransaction)NewExamMarksEntryTransactionImpl.getInstance();
        final List list = transaction.getDataForQuery(query);
        final Iterator<Object[]> itr = list.iterator();
        if (itr.hasNext()) {
            final Object[] obj = itr.next();
            double classHeld = 0.0;
            double classAtt = 0.0;
            if (obj[2] != null) {
                classHeld = Integer.parseInt(obj[2].toString());
            }
            if (obj[3] != null) {
                classAtt = Integer.parseInt(obj[3].toString());
            }
            if (obj[4] != null) {
                classAtt += Integer.parseInt(obj[4].toString());
            }
            double percentage = 0.0;
            if (classHeld > 0.0 && classAtt > 0.0) {
                percentage = (double)Math.round(classAtt / classHeld * 100.0);
                final double minPercentage = Double.valueOf(CMSConstants.ATTENDANCE_PERCENTAGE);
                if (percentage >= minPercentage) {
                    isCheck = true;
                }
            }
        }
        if ((list == null || list.isEmpty()) && (classId == 1870 || classId == 1897 || classId == 1875 || classId == 2021 || classId == 2031)) {
            isCheck = true;
        }
        return isCheck;
    }
    
    public boolean saveMobileNo(final String mobileNo, final String userId, final String employeeId) throws Exception {
        boolean isAdded = false;
        final ILoginTransaction iLoginTransaction = (ILoginTransaction)new LoginTransactionImpl();
        final Employee employee = iLoginTransaction.getEmployeeDetails(employeeId);
        final Employee emp = LoginTransactionHelper.getInstance().copyFormToBO(mobileNo, userId, employee);
        isAdded = iLoginTransaction.getsaveMobileNo(emp);
        return isAdded;
    }
    
    public LoginForm getMobileNo(LoginForm loginForm, final String employeeId) throws Exception {
        final ILoginTransaction iLoginTransaction = (ILoginTransaction)new LoginTransactionImpl();
        final Employee employee = iLoginTransaction.getEmployeeDetails(employeeId);
        loginForm = LoginTransactionHelper.getInstance().copyBOToForm(employee);
        return loginForm;
    }
    
    public Integer getNotifications(final String userId) throws Exception {
        final ILoginTransaction iLoginTransaction = (ILoginTransaction)new LoginTransactionImpl();
        final Integer count = iLoginTransaction.getNotificationCount(userId);
        return count;
    }
    
    public String getMarksEnteryLinks(final String userId, final HttpSession session) throws Exception {
        final ILoginTransaction iLoginTransaction = (ILoginTransaction)new LoginTransactionImpl();
        final String displayLinkExamName = iLoginTransaction.getMarksEnteryLinks(userId, session);
        return displayLinkExamName;
    }
    
    public boolean getIsPresentPeersEvaluationLink(final LoginForm loginForm, final HttpSession session) throws Exception {
        boolean isPresent = false;
        final ILoginTransaction iLoginTransaction = (ILoginTransaction)new LoginTransactionImpl();
        if (session.getAttribute("DepartmentId") != null) {
            final String departmentId = session.getAttribute("DepartmentId").toString();
            if (departmentId != null && !departmentId.isEmpty()) {
                final PeersEvaluationOpenSession openSession = iLoginTransaction.getOpenSession(departmentId);
                session.setAttribute("SessionId", (Object)null);
                if (openSession != null && !openSession.toString().isEmpty() && openSession.getPeerFeedbackSession() != null && !openSession.getPeerFeedbackSession().toString().isEmpty()) {
                    session.setAttribute("SessionId", (Object)openSession.getPeerFeedbackSession().getId());
                    isPresent = true;
                }
            }
        }
        return isPresent;
    }
    
    public List<Object> getIsResearchLink(final String userId, final HttpSession session) throws Exception {
        final ILoginTransaction iLoginTransaction = (ILoginTransaction)new LoginTransactionImpl();
        final List<Object> couseIds = (List<Object>)iLoginTransaction.getResearchLinks(userId, session);
        return couseIds;
    }
    
    public boolean checkStudentIsFinalYearOrNot(final LoginForm loginForm, final HttpSession session) throws Exception {
        final ILoginTransaction iLoginTransaction = (ILoginTransaction)new LoginTransactionImpl();
        final int studentId = iLoginTransaction.getStudentIdByUserName(loginForm);
        session.setAttribute("studentIdforConvocation", (Object)studentId);
        return iLoginTransaction.checkStudentIsFinalYearOrNot(studentId);
    }
    
    public boolean saveConvocationRegistration(final LoginForm loginForm, final HttpSession session) throws Exception {
        final ILoginTransaction iLoginTransaction = (ILoginTransaction)new LoginTransactionImpl();
        final int academicYear = iLoginTransaction.getAcademicYearByStudentRegNo(session);
        final ConvocationRegistration registration = LoginTransactionHelper.getInstance().convertFormToBO(academicYear, loginForm, session);
        return iLoginTransaction.saveConvocationRegistration(registration);
    }
    
    public void loadConvocationRegistration(final LoginForm loginForm, final HttpSession session) throws Exception {
        final ILoginTransaction iLoginTransaction = (ILoginTransaction)new LoginTransactionImpl();
        final ConvocationRegistration convocationRegistration = iLoginTransaction.loadConvocationRegistration(session);
        if (convocationRegistration != null) {
            LoginTransactionHelper.getInstance().convertBoToConvocationForm(convocationRegistration, loginForm);
        }
    }
    
    public boolean SapRegistrationLoad(final LoginForm loginForm) throws Exception {
        boolean exist = false;
        final ILoginTransaction iLoginTransaction = (ILoginTransaction)new LoginTransactionImpl();
        final SapRegistration sapRegistration = iLoginTransaction.LoadSapRegistration(loginForm);
        if (sapRegistration != null) {
            exist = true;
        }
        return exist;
    }
    
    public boolean saveSapRegistration(final LoginForm loginForm, final HttpSession session) throws Exception {
        final ILoginTransaction iLoginTransaction = (ILoginTransaction)new LoginTransactionImpl();
        final SapRegistration registration = LoginTransactionHelper.getInstance().convertFormToBOSap(loginForm);
        return iLoginTransaction.saveSapRegistration(registration);
    }
    
    public boolean sendMailToAdmin(final LoginForm loginForm) throws Exception {
        boolean sent = false;
        final Properties prop = new Properties();
        try {
            final InputStream inStr = CommonUtil.class.getClassLoader().getResourceAsStream("resources/application.properties");
            prop.load(inStr);
        }
        catch (FileNotFoundException e) {
            return false;
        }
        catch (IOException e2) {
            return false;
        }
        List<GroupTemplate> list = null;
        final TemplateHandler temphandle = TemplateHandler.getInstance();
        list = (List<GroupTemplate>)temphandle.getDuplicateCheckList("SAP Registration Admin Notification mail");
        final String date = CommonUtil.getStringDate(new Date());
        if (list != null && !list.isEmpty()) {
            final String desc = list.get(0).getTemplateDescription();
            final String fromName = prop.getProperty("knowledgepro.sap.mail.fromname");
            final String fromAddress = CMSConstants.MAIL_USERID;
            final String toAddress = prop.getProperty("knowledgepro.sap.admin.sendmail.to.id");
            final String subject = "SAP-e Course Registered by student";
            String message = desc;
            message = message.replace("[DATE]", date);
            message = message.replace("[REGISTER No]", loginForm.getRegNo());
            message = message.replace("[CLASS]", loginForm.getClassName());
            message = message.replace("[NAME]", loginForm.getStudentName());
            message = message.replace("[STUDENTEMAIL]", loginForm.getUnivEmailId());
            sent = this.sendMail(toAddress, subject, message, fromName, fromAddress);
            HtmlPrinter.printHtml(message);
        }
        return sent;
    }
    
    public boolean sendMail(final String mailID, final String sub, final String message, final String fromName, final String fromAddress) {
        boolean sent = false;
        final String toAddress = mailID;
        final String subject = sub;
        final String msg = message;
        final MailTO mailto = new MailTO();
        mailto.setFromAddress(fromAddress);
        mailto.setToAddress(toAddress);
        mailto.setSubject(subject);
        mailto.setMessage(msg);
        mailto.setFromName(fromName);
        sent = CommonUtil.sendMail(mailto);
        return sent;
    }
    
    public void sendSMSToStudent(final LoginForm crForm) throws Exception {
        final Properties prop = new Properties();
        final InputStream in1 = NewStudentMarksCorrectionAction.class.getClassLoader().getResourceAsStream("resources/sms.properties");
        prop.load(in1);
        final String senderNumber = prop.getProperty("senderNumber");
        final String senderName = prop.getProperty("senderName");
        String desc = "";
        final SMSTemplateHandler temphandle = SMSTemplateHandler.getInstance();
        final List<SMSTemplate> list = (List<SMSTemplate>)temphandle.getDuplicateCheckList(0, "SAP Registration Student Sms");
        if (list != null && !list.isEmpty()) {
            desc = list.get(0).getTemplateDescription();
        }
        String mobileNo = "";
        if (crForm.getMobileNo() != null && !crForm.getMobileNo().isEmpty()) {
            mobileNo = "91" + crForm.getMobileNo();
        }
        if (StringUtils.isNumeric(mobileNo) && mobileNo.length() == 12 && desc.length() <= 160) {
            final MobileMessaging mob = new MobileMessaging();
            mob.setDestinationNumber(mobileNo);
            mob.setMessageBody(desc);
            mob.setMessagePriority(Integer.valueOf(3));
            mob.setSenderName(senderName);
            mob.setSenderNumber(senderNumber);
            mob.setMessageEnqueueDate(new Date());
            mob.setIsMessageSent(Boolean.valueOf(false));
            PropertyUtil.getInstance().save((Object)mob);
        }
    }
    
    public List<LoginTransactionTo> invigilationDutyAllotment(final int userId) throws Exception {
        final ILoginTransaction loginTransaction = (ILoginTransaction)new LoginTransactionImpl();
        final List<Object[]> dutyAllotmentDetails = (List<Object[]>)loginTransaction.invigilationDutyAllotmentDetails(userId);
        final List<LoginTransactionTo> allotmentDetails = (List<LoginTransactionTo>)LoginTransactionHelper.getInstance().converBoToTo((List)dutyAllotmentDetails);
        return allotmentDetails;
    }
    
    public Integer checkingDutyAllotmentDetails(final int userId) throws Exception {
        final ILoginTransaction loginTransaction = (ILoginTransaction)new LoginTransactionImpl();
        final Integer dutyAllotmentDetailsSize = loginTransaction.getDutyAllotmentDetailsSize(userId);
        return dutyAllotmentDetailsSize;
    }
    
    public boolean checkForSyllabusEntryLink(final SyllabusEntryForm syllabusEntryForm) throws Exception {
        final ILoginTransaction loginTransaction = (ILoginTransaction)new LoginTransactionImpl();
        final Date currentDate = new Date();
        final Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        final boolean flag = loginTransaction.checkForSyllabusEntryOpen(syllabusEntryForm, CommonUtil.ConvertStringToSQLDate(CommonUtil.formatDates(currentDate)));
        return flag;
    }
    
    public boolean isCondonationPaid(final int studentId, final int classId) throws Exception {
        final INewExamMarksEntryTransaction transaction = (INewExamMarksEntryTransaction)NewExamMarksEntryTransactionImpl.getInstance();
        final boolean isCondonationPaid = transaction.isCondonationPaid(studentId, classId);
        return isCondonationPaid;
    }
    
    public PersonalDataTO convertBOTO(final PersonalData personalData, final LoginForm loginForm, final Student student) throws Exception {
        PersonalDataTO personalDataTO = new PersonalDataTO();
        String name = "";
        int year = 0;
        if (personalData != null) {
            personalDataTO = new PersonalDataTO();
            personalDataTO.setId(personalData.getId());
            personalDataTO.setCreatedBy(personalData.getCreatedBy());
            personalDataTO.setCreatedDate(personalData.getCreatedDate());
            personalDataTO.setIsmgquota(personalData.getIsmgquota());
            if (personalData.getFirstName() != null) {
                name = personalData.getFirstName();
            }
            if (personalData.getMiddleName() != null) {
                name = String.valueOf(name) + " " + personalData.getMiddleName();
            }
            if (personalData.getLastName() != null) {
                name = String.valueOf(name) + " " + personalData.getLastName();
            }
            personalDataTO.setFirstName(name);
            if (personalData.getDateOfBirth() != null) {
                personalDataTO.setDob(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(personalData.getDateOfBirth()), "dd-MMM-yyyy", "dd/MM/yyyy"));
            }
            personalDataTO.setBirthPlace(personalData.getBirthPlace());
            if (personalData.getIsHandicapped() != null) {
                personalDataTO.setHandicapped((boolean)personalData.getIsHandicapped());
            }
            if (personalData.getIsSportsPerson() != null) {
                personalDataTO.setSportsPerson((boolean)personalData.getIsSportsPerson());
            }
            if (personalData.getHandicappedDescription() != null) {
                personalDataTO.setHadnicappedDescription(personalData.getHandicappedDescription());
            }
            if (personalData.getSportsPersonDescription() != null) {
                personalDataTO.setSportsDescription(personalData.getSportsPersonDescription());
            }
            if (personalData.getSports() != null) {
                personalDataTO.setSports(personalData.getSports());
            }
            if (personalData.getArts() != null) {
                personalDataTO.setArts(personalData.getArts());
            }
            if (personalData.getSportsParticipate() != null) {
                personalDataTO.setSportsParticipate(personalData.getSportsParticipate());
            }
            if (personalData.getArtsParticipate() != null) {
                personalDataTO.setArtsParticipate(personalData.getArtsParticipate());
            }
            if (personalData.getFatherMobile() != null) {
                personalDataTO.setFatherMobile(personalData.getFatherMobile());
            }
            if (personalData.getMotherMobile() != null) {
                personalDataTO.setMotherMobile(personalData.getMotherMobile());
            }
            if (personalData.getIsNcccertificate() != null) {
                personalDataTO.setNcccertificate((boolean)personalData.getIsNcccertificate());
            }
            if (personalData.getIsNsscertificate() != null) {
                personalDataTO.setNsscertificate((boolean)personalData.getIsNsscertificate());
            }
            if (personalData.getIsExcervice() != null) {
                personalDataTO.setExservice((boolean)personalData.getIsExcervice());
            }
            if (personalData.getIsNcccertificate() != null && personalData.getIsNcccertificate()) {
                if (personalData.getNccgrade() != null) {
                    personalDataTO.setNccgrades(personalData.getNccgrade());
                }
                else {
                    personalDataTO.setNccgrades("");
                }
            }
            else {
                personalDataTO.setNccgrades("");
            }
            if (personalData.getIsNsscertificate() != null && personalData.getIsNsscertificate()) {
                if (personalData.getNssgrade() != null) {
                    personalDataTO.setNssgrades(personalData.getNssgrade());
                }
                else {
                    personalDataTO.setNssgrades("");
                }
            }
            else {
                personalDataTO.setNssgrades("");
            }
            if (personalData.getDioceseOthers() != null && !StringUtils.isEmpty(personalData.getDioceseOthers())) {
                personalDataTO.setDioceseOthers(personalData.getDioceseOthers());
            }
            if (personalData.getParishOthers() != null && !StringUtils.isEmpty(personalData.getParishOthers())) {
                personalDataTO.setParishOthers(personalData.getParishOthers());
            }
            if (personalData.getUgcourse() != null) {
                personalDataTO.setUgcourse(new StringBuilder(String.valueOf(personalData.getUgcourse().getId())).toString());
            }
            if (personalData.getPermanentAddressDistrcictOthers() != null && !personalData.getPermanentAddressDistrcictOthers().isEmpty()) {
                personalDataTO.setPermanentAddressDistrictOthers(personalData.getPermanentAddressDistrcictOthers());
                personalDataTO.setPermanentDistricId("Other");
            }
            else if (personalData.getStateByParentAddressDistrictId() != null) {
                personalDataTO.setPermanentDistricName(personalData.getStateByParentAddressDistrictId().getName());
                personalDataTO.setPermanentDistricId(new StringBuilder().append(personalData.getStateByParentAddressDistrictId().getId()).toString());
            }
            if (personalData.getCurrenttAddressDistrcictOthers() != null && !personalData.getCurrenttAddressDistrcictOthers().isEmpty()) {
                personalDataTO.setCurrentAddressDistrictOthers(personalData.getCurrenttAddressDistrcictOthers());
                personalDataTO.setCurrentDistricId("Other");
            }
            else if (personalData.getStateByCurrentAddressDistrictId() != null) {
                personalDataTO.setCurrentDistricName(personalData.getStateByCurrentAddressDistrictId().getName());
                personalDataTO.setCurrentDistricId(new StringBuilder().append(personalData.getStateByCurrentAddressDistrictId().getId()).toString());
            }
            if (personalData.getStream() != null) {
                personalDataTO.setStream(new StringBuilder(String.valueOf(personalData.getStream().getId())).toString());
            }
            if (personalData.getNoofenglishCoreCourses() != null) {
                personalDataTO.setNoofenglishCoreCourses(personalData.getNoofenglishCoreCourses());
            }
            if (personalData.getHeight() != null) {
                personalDataTO.setHeight(String.valueOf((int)personalData.getHeight()));
            }
            if (personalData.getWeight() != null) {
                personalDataTO.setWeight(String.valueOf(personalData.getWeight().doubleValue()));
            }
            if (personalData.getLanguageByLanguageRead() != null) {
                personalDataTO.setLanguageByLanguageRead(personalData.getLanguageByLanguageRead());
            }
            if (personalData.getLanguageByLanguageSpeak() != null) {
                personalDataTO.setLanguageByLanguageSpeak(personalData.getLanguageByLanguageSpeak());
            }
            if (personalData.getLanguageByLanguageWrite() != null) {
                personalDataTO.setLanguageByLanguageWrite(personalData.getLanguageByLanguageWrite());
            }
            if (personalData.getMotherTongue() != null) {
                personalDataTO.setMotherTongue(String.valueOf(personalData.getMotherTongue().getId()));
            }
            if (personalData.getTrainingDuration() != null) {
                personalDataTO.setTrainingDuration(String.valueOf(personalData.getTrainingDuration()));
            }
            personalDataTO.setTrainingInstAddress(personalData.getTrainingInstAddress());
            personalDataTO.setTrainingProgName(personalData.getTrainingProgName());
            personalDataTO.setTrainingPurpose(personalData.getTrainingPurpose());
            personalDataTO.setCourseKnownBy(personalData.getCourseKnownBy());
            personalDataTO.setCourseOptReason(personalData.getCourseOptReason());
            personalDataTO.setStrength(personalData.getStrength());
            personalDataTO.setWeakness(personalData.getWeakness());
            personalDataTO.setOtherAddnInfo(personalData.getOtherAddnInfo());
            personalDataTO.setSecondLanguage(personalData.getSecondLanguage());
            if (personalData.getStudentExtracurriculars() != null && !personalData.getStudentExtracurriculars().isEmpty()) {
                final Iterator<StudentExtracurricular> extrItr = personalData.getStudentExtracurriculars().iterator();
                final List<StudentExtracurricular> templist = new ArrayList<StudentExtracurricular>();
                final List<StudentExtracurricular> origlist = new ArrayList<StudentExtracurricular>();
                templist.addAll(personalData.getStudentExtracurriculars());
                final StringBuffer extrcurNames = new StringBuffer();
                final String[] extraIds = new String[templist.size()];
                int i = 0;
                while (extrItr.hasNext()) {
                    final StudentExtracurricular studentExtr = extrItr.next();
                    if (studentExtr.getExtracurricularActivity() != null && studentExtr.getIsActive()) {
                        origlist.add(studentExtr);
                        final ExtracurricularActivity bo = studentExtr.getExtracurricularActivity();
                        if (!bo.getIsActive()) {
                            continue;
                        }
                        extraIds[i] = String.valueOf(bo.getId());
                        if (i == personalData.getStudentExtracurriculars().size() - 1) {
                            extrcurNames.append(bo.getName());
                        }
                        else {
                            extrcurNames.append(bo.getName());
                            extrcurNames.append(",");
                        }
                        ++i;
                    }
                }
                personalDataTO.setStudentExtracurriculars((List)origlist);
                personalDataTO.setExtracurricularIds(extraIds);
                personalDataTO.setExtracurricularNames(extrcurNames.toString());
            }
            if (personalData.getStateOthers() != null && !personalData.getStateOthers().isEmpty()) {
                personalDataTO.setBirthState("Other");
                personalDataTO.setStateOthers(personalData.getStateOthers());
                personalDataTO.setStateOfBirth(personalData.getStateOthers());
            }
            else if (personalData.getStateByStateId() != null) {
                personalDataTO.setStateOfBirth(personalData.getStateByStateId().getName());
                personalDataTO.setBirthState(String.valueOf(personalData.getStateByStateId().getId()));
            }
            if (personalData.getCountryOthers() != null && !personalData.getCountryOthers().isEmpty()) {
                personalDataTO.setCountryOfBirth(personalData.getCountryOthers());
            }
            else if (personalData.getCountryByCountryId() != null) {
                personalDataTO.setCountryOfBirth(personalData.getCountryByCountryId().getName());
                personalDataTO.setBirthCountry(String.valueOf(personalData.getCountryByCountryId().getId()));
            }
            if (personalData.getNationalityOthers() != null && !personalData.getNationalityOthers().isEmpty()) {
                personalDataTO.setCitizenship(personalData.getNationalityOthers());
                personalDataTO.setNationalityOthers(personalData.getNationalityOthers());
            }
            else if (personalData.getNationality() != null) {
                personalDataTO.setCitizenship(personalData.getNationality().getName());
                personalDataTO.setNationality(String.valueOf(personalData.getNationality().getId()));
            }
            if (personalData.getResidentCategory() != null) {
                personalDataTO.setResidentCategoryName(personalData.getResidentCategory().getName());
                personalDataTO.setResidentCategory(String.valueOf(personalData.getResidentCategory().getId()));
            }
            if (personalData.getReligionOthers() != null && !personalData.getReligionOthers().isEmpty()) {
                personalDataTO.setReligionId("Other");
                personalDataTO.setReligionOthers(personalData.getReligionOthers());
                personalDataTO.setReligionName(personalData.getReligionOthers());
            }
            else if (personalData.getReligion() != null) {
                personalDataTO.setReligionName(personalData.getReligion().getName());
                personalDataTO.setReligionId(String.valueOf(personalData.getReligion().getId()));
            }
            if (personalData.getReligionSectionOthers() != null && !personalData.getReligionSectionOthers().isEmpty()) {
                personalDataTO.setSubReligionId("Other");
                personalDataTO.setReligionSectionOthers(personalData.getReligionSectionOthers());
                personalDataTO.setSubregligionName(personalData.getReligionSectionOthers());
            }
            else if (personalData.getReligionSection() != null) {
                personalDataTO.setSubregligionName(personalData.getReligionSection().getName());
                personalDataTO.setSubReligionId(String.valueOf(personalData.getReligionSection().getId()));
                if (personalData.getReligionSection().getName().equalsIgnoreCase("SEBC")) {
                    personalDataTO.setReservation("Applicable");
                }
                else {
                    personalDataTO.setReservation("Not Applicable");
                }
                if (personalData.getReligionSection().getName().equalsIgnoreCase("OEC")) {
                    personalDataTO.setReservation1("Applicable");
                }
                else {
                    personalDataTO.setReservation1("Not Applicable");
                }
            }
            if (personalData.getCasteOthers() != null && !personalData.getCasteOthers().isEmpty()) {
                personalDataTO.setCasteCategory(personalData.getCasteOthers());
                personalDataTO.setCasteOthers(personalData.getCasteOthers());
                personalDataTO.setCasteId("Other");
            }
            else if (personalData.getCaste() != null) {
                personalDataTO.setCasteCategory(personalData.getCaste().getName());
                personalDataTO.setCasteId(String.valueOf(personalData.getCaste().getId()));
            }
            if (personalData.getRuralUrban() != null) {
                personalDataTO.setRuralUrban((char)personalData.getRuralUrban());
                personalDataTO.setAreaType((char)personalData.getRuralUrban());
            }
            personalDataTO.setGender(personalData.getGender());
            personalDataTO.setBloodGroup(personalData.getBloodGroup());
            personalDataTO.setPhNo1(personalData.getPhNo1());
            personalDataTO.setPhNo2(personalData.getPhNo2());
            personalDataTO.setPhNo3(personalData.getPhNo3());
            personalDataTO.setMobileNo1(personalData.getMobileNo1());
            personalDataTO.setMobileNo2(personalData.getMobileNo2());
            personalDataTO.setMobileNo3(personalData.getMobileNo3());
            if (personalData.getPhNo1() != null && personalData.getPhNo2() != null && personalData.getPhNo3() != null) {
                personalDataTO.setLandlineNo(String.valueOf(personalData.getPhNo1()) + " " + personalData.getPhNo2() + " " + personalData.getPhNo3());
            }
            else if (personalData.getPhNo2() != null && personalData.getPhNo3() != null) {
                personalDataTO.setLandlineNo(String.valueOf(personalData.getPhNo2()) + " " + personalData.getPhNo3());
            }
            else if (personalData.getPhNo3() != null) {
                personalDataTO.setLandlineNo(personalData.getPhNo3());
            }
            personalDataTO.setMobileNo(String.valueOf(personalData.getMobileNo1()) + " " + personalData.getMobileNo2());
            personalDataTO.setEmail(personalData.getEmail());
            personalDataTO.setPassportNo(personalData.getPassportNo());
            personalDataTO.setResidentPermitNo(personalData.getResidentPermitNo());
            if (personalData.getCountryByPassportCountryId() != null) {
                personalDataTO.setPassportCountry(personalData.getCountryByPassportCountryId().getId());
                personalDataTO.setPassportIssuingCountry(personalData.getCountryByPassportCountryId().getName());
            }
            if (personalData.getPassportValidity() != null) {
                personalDataTO.setPassportValidity(CommonUtil.getStringDate(personalData.getPassportValidity()));
            }
            if (personalData.getResidentPermitDate() != null) {
                personalDataTO.setResidentPermitDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(personalData.getResidentPermitDate()), "dd-MMM-yyyy", "dd/MM/yyyy"));
            }
            personalDataTO.setPermanentAddressLine1(personalData.getPermanentAddressLine1());
            personalDataTO.setPermanentAddressLine2(personalData.getPermanentAddressLine2());
            if (personalData.getCityByPermanentAddressCityId() != null) {
                personalDataTO.setPermanentCityName(personalData.getCityByPermanentAddressCityId());
            }
            if (personalData.getPermanentAddressStateOthers() != null && !personalData.getPermanentAddressStateOthers().isEmpty()) {
                personalDataTO.setPermanentStateName(personalData.getPermanentAddressStateOthers());
                personalDataTO.setPermanentAddressStateOthers(personalData.getPermanentAddressStateOthers());
                personalDataTO.setPermanentStateId("Other");
            }
            else if (personalData.getStateByPermanentAddressStateId() != null) {
                personalDataTO.setPermanentStateName(personalData.getStateByPermanentAddressStateId().getName());
                personalDataTO.setPermanentStateId(String.valueOf(personalData.getStateByPermanentAddressStateId().getId()));
            }
            if (personalData.getPermanentAddressCountryOthers() != null && !personalData.getPermanentAddressCountryOthers().isEmpty()) {
                personalDataTO.setPermanentCountryName(personalData.getPermanentAddressCountryOthers());
            }
            if (personalData.getCountryByPermanentAddressCountryId() != null) {
                personalDataTO.setPermanentCountryName(personalData.getCountryByPermanentAddressCountryId().getName());
                personalDataTO.setPermanentCountryId(personalData.getCountryByPermanentAddressCountryId().getId());
            }
            personalDataTO.setPermanentAddressZipCode(personalData.getPermanentAddressZipCode());
            personalDataTO.setCurrentAddressLine1(personalData.getCurrentAddressLine1());
            personalDataTO.setCurrentAddressLine2(personalData.getCurrentAddressLine2());
            if (personalData.getCityByCurrentAddressCityId() != null) {
                personalDataTO.setCurrentCityName(personalData.getCityByCurrentAddressCityId());
            }
            if (personalData.getCurrentAddressStateOthers() != null && !personalData.getCurrentAddressStateOthers().isEmpty()) {
                personalDataTO.setCurrentStateName(personalData.getCurrentAddressStateOthers());
                personalDataTO.setCurrentAddressStateOthers(personalData.getCurrentAddressStateOthers());
                personalDataTO.setCurrentStateId("Other");
            }
            else if (personalData.getStateByCurrentAddressStateId() != null) {
                personalDataTO.setCurrentStateName(personalData.getStateByCurrentAddressStateId().getName());
                personalDataTO.setCurrentStateId(String.valueOf(personalData.getStateByCurrentAddressStateId().getId()));
            }
            if (personalData.getCurrentAddressCountryOthers() != null && !personalData.getCurrentAddressCountryOthers().isEmpty()) {
                personalDataTO.setCurrentCountryName(personalData.getCurrentAddressCountryOthers());
            }
            else if (personalData.getCountryByCurrentAddressCountryId() != null) {
                personalDataTO.setCurrentCountryName(personalData.getCountryByCurrentAddressCountryId().getName());
                personalDataTO.setCurrentCountryId(personalData.getCountryByCurrentAddressCountryId().getId());
            }
            personalDataTO.setCurrentAddressZipCode(personalData.getCurrentAddressZipCode());
            if (personalData.getCurrentStreet() != null && !personalData.getCurrentStreet().isEmpty()) {
                personalDataTO.setCurrentStreet(personalData.getCurrentStreet());
            }
            if (personalData.getPermanentStreet() != null && !personalData.getPermanentStreet().isEmpty()) {
                personalDataTO.setPermanentStreet(personalData.getPermanentStreet());
            }
            if (personalData.getFatherOccupationAddress() != null && !personalData.getFatherOccupationAddress().isEmpty()) {
                personalDataTO.setFatherOccupationAddress(personalData.getFatherOccupationAddress());
            }
            if (personalData.getMotherOccupationAddress() != null && !personalData.getMotherOccupationAddress().isEmpty()) {
                personalDataTO.setMotherOccupationAddress(personalData.getMotherOccupationAddress());
            }
            personalDataTO.setFatherName(personalData.getFatherName());
            personalDataTO.setFatherEducation(personalData.getFatherEducation());
            if (personalData.getIncomeByFatherIncomeId() != null && personalData.getIncomeByFatherIncomeId() != null) {
                if (personalData.getCurrencyByFatherIncomeCurrencyId() != null) {
                    personalDataTO.setFatherIncome(String.valueOf(personalData.getCurrencyByFatherIncomeCurrencyId().getCurrencyCode()) + personalData.getIncomeByFatherIncomeId().getIncomeRange());
                    personalDataTO.setFatherCurrencyId(String.valueOf(personalData.getCurrencyByFatherIncomeCurrencyId().getId()));
                    personalDataTO.setFatherCurrency(personalData.getCurrencyByFatherIncomeCurrencyId().getName());
                }
                else {
                    personalDataTO.setFatherIncome(personalData.getIncomeByFatherIncomeId().getIncomeRange());
                }
                personalDataTO.setFatherIncomeRange(personalData.getIncomeByFatherIncomeId().getIncomeRange());
                personalDataTO.setFatherIncomeId(String.valueOf(personalData.getIncomeByFatherIncomeId().getId()));
            }
            if (personalData.getOccupationByFatherOccupationId() != null) {
                personalDataTO.setFatherOccupation(personalData.getOccupationByFatherOccupationId().getName());
                personalDataTO.setFatherOccupationId(String.valueOf(personalData.getOccupationByFatherOccupationId().getId()));
            }
            else if (personalData.getOtherOccupationFather() != null) {
                personalDataTO.setFatherOccupationId("Other");
                personalDataTO.setOtherOccupationFather(personalData.getOtherOccupationFather());
            }
            personalDataTO.setFatherEmail(personalData.getFatherEmail());
            personalDataTO.setMotherName(personalData.getMotherName());
            personalDataTO.setMotherEducation(personalData.getMotherEducation());
            if (personalData.getIncomeByMotherIncomeId() != null) {
                if (personalData.getCurrencyByMotherIncomeCurrencyId() != null) {
                    personalDataTO.setMotherIncome(String.valueOf(personalData.getCurrencyByMotherIncomeCurrencyId().getCurrencyCode()) + personalData.getIncomeByMotherIncomeId().getIncomeRange());
                }
                else {
                    personalDataTO.setMotherIncome(personalData.getIncomeByMotherIncomeId().getIncomeRange());
                }
                personalDataTO.setMotherIncomeRange(personalData.getIncomeByMotherIncomeId().getIncomeRange());
                personalDataTO.setMotherIncomeId(String.valueOf(personalData.getIncomeByMotherIncomeId().getId()));
            }
            if (personalData.getIncomeByFatherIncomeId() != null && personalData.getCurrencyByFatherIncomeCurrencyId() == null) {
                personalDataTO.setFatherIncomeId(String.valueOf(personalData.getIncomeByFatherIncomeId().getId()));
            }
            if (personalData.getIncomeByMotherIncomeId() != null && personalData.getCurrencyByMotherIncomeCurrencyId() == null) {
                personalDataTO.setMotherIncomeId(String.valueOf(personalData.getIncomeByMotherIncomeId().getId()));
            }
            if (personalData.getCurrencyByMotherIncomeCurrencyId() != null) {
                personalDataTO.setMotherCurrencyId(String.valueOf(personalData.getCurrencyByMotherIncomeCurrencyId().getId()));
                personalDataTO.setMotherCurrency(personalData.getCurrencyByMotherIncomeCurrencyId().getName());
            }
            if (personalData.getCurrencyByFatherIncomeCurrencyId() != null) {
                personalDataTO.setFatherCurrencyId(String.valueOf(personalData.getCurrencyByFatherIncomeCurrencyId().getId()));
                personalDataTO.setFatherCurrency(personalData.getCurrencyByFatherIncomeCurrencyId().getName());
            }
            if (personalData.getOccupationByMotherOccupationId() != null) {
                personalDataTO.setMotherOccupation(personalData.getOccupationByMotherOccupationId().getName());
                personalDataTO.setMotherOccupationId(String.valueOf(personalData.getOccupationByMotherOccupationId().getId()));
            }
            else if (personalData.getOtherOccupationMother() != null) {
                personalDataTO.setMotherOccupationId("Other");
                personalDataTO.setOtherOccupationMother(personalData.getOtherOccupationMother());
            }
            personalDataTO.setMotherEmail(personalData.getMotherEmail());
            personalDataTO.setParentAddressLine1(personalData.getParentAddressLine1());
            personalDataTO.setParentAddressLine2(personalData.getParentAddressLine2());
            personalDataTO.setParentAddressLine3(personalData.getParentAddressLine3());
            if (personalData.getCityByParentAddressCityId() != null) {
                personalDataTO.setParentCityName(personalData.getCityByParentAddressCityId());
            }
            if (personalData.getParentAddressStateOthers() != null && !personalData.getParentAddressStateOthers().isEmpty()) {
                personalDataTO.setParentStateName(personalData.getParentAddressStateOthers());
                personalDataTO.setParentAddressStateOthers(personalData.getParentAddressStateOthers());
                personalDataTO.setParentStateId("Other");
            }
            else if (personalData.getStateByParentAddressStateId() != null) {
                personalDataTO.setParentStateName(personalData.getStateByParentAddressStateId().getName());
                personalDataTO.setParentStateId(String.valueOf(personalData.getStateByParentAddressStateId().getId()));
            }
            if (personalData.getParentAddressCountryOthers() != null && !personalData.getParentAddressCountryOthers().isEmpty()) {
                personalDataTO.setParentCountryName(personalData.getParentAddressCountryOthers());
            }
            else if (personalData.getCountryByParentAddressCountryId() != null) {
                personalDataTO.setParentCountryName(personalData.getCountryByParentAddressCountryId().getName());
                personalDataTO.setParentCountryId(personalData.getCountryByParentAddressCountryId().getId());
            }
            personalDataTO.setParentAddressZipCode(personalData.getParentAddressZipCode());
            personalDataTO.setParentPhone(String.valueOf(personalData.getParentPh1()) + " " + personalData.getParentPh2() + " " + personalData.getParentPh3());
            personalDataTO.setParentPh1(personalData.getParentPh1());
            personalDataTO.setParentPh2(personalData.getParentPh2());
            personalDataTO.setParentPh3(personalData.getParentPh3());
            personalDataTO.setParentMobile(String.valueOf(personalData.getParentMob1()) + " " + personalData.getParentMob2() + " " + personalData.getParentMob3());
            personalDataTO.setParentMob1(personalData.getParentMob1());
            personalDataTO.setParentMob2(personalData.getParentMob2());
            personalDataTO.setParentMob3(personalData.getParentMob3());
            personalDataTO.setGuardianAddressLine1(personalData.getGuardianAddressLine1());
            personalDataTO.setGuardianAddressLine2(personalData.getGuardianAddressLine2());
            personalDataTO.setGuardianAddressLine3(personalData.getGuardianAddressLine3());
            if (personalData.getCityByGuardianAddressCityId() != null) {
                personalDataTO.setCityByGuardianAddressCityId(personalData.getCityByGuardianAddressCityId());
            }
            if (personalData.getGuardianAddressStateOthers() != null && !personalData.getGuardianAddressStateOthers().isEmpty()) {
                personalDataTO.setGuardianAddressStateOthers(personalData.getGuardianAddressStateOthers());
                personalDataTO.setGuardianStateName(personalData.getGuardianAddressStateOthers());
                personalDataTO.setStateByGuardianAddressStateId("Other");
            }
            else if (personalData.getStateByGuardianAddressStateId() != null) {
                personalDataTO.setGuardianStateName(personalData.getStateByGuardianAddressStateId().getName());
                personalDataTO.setStateByGuardianAddressStateId(String.valueOf(personalData.getStateByGuardianAddressStateId().getId()));
            }
            if (personalData.getCountryByGuardianAddressCountryId() != null) {
                personalDataTO.setCountryByGuardianAddressCountryId(personalData.getCountryByGuardianAddressCountryId().getId());
                personalDataTO.setGuardianCountryName(personalData.getCountryByGuardianAddressCountryId().getName());
            }
            personalDataTO.setGuardianAddressZipCode(personalData.getGuardianAddressZipCode());
            personalDataTO.setGuardianPh1(personalData.getGuardianPh1());
            personalDataTO.setGuardianPh2(personalData.getGuardianPh2());
            personalDataTO.setGuardianPh3(personalData.getGuardianPh3());
            personalDataTO.setGuardianMob1(personalData.getGuardianMob1());
            personalDataTO.setGuardianMob2(personalData.getGuardianMob2());
            personalDataTO.setGuardianMob3(personalData.getGuardianMob3());
            personalDataTO.setBrotherName(personalData.getBrotherName());
            personalDataTO.setBrotherEducation(personalData.getBrotherEducation());
            personalDataTO.setBrotherOccupation(personalData.getBrotherOccupation());
            personalDataTO.setBrotherIncome(personalData.getBrotherIncome());
            personalDataTO.setBrotherAge(personalData.getBrotherAge());
            personalDataTO.setSisterName(personalData.getSisterName());
            personalDataTO.setGuardianName(personalData.getGuardianName());
            personalDataTO.setSisterEducation(personalData.getSisterEducation());
            personalDataTO.setSisterOccupation(personalData.getSisterOccupation());
            personalDataTO.setSisterIncome(personalData.getSisterIncome());
            personalDataTO.setSisterAge(personalData.getSisterAge());
            if (personalData.getUniversityEmail() != null && !personalData.getUniversityEmail().isEmpty()) {
                personalDataTO.setUniversityEmail(personalData.getUniversityEmail());
            }
            if (personalData.getAadharCardNumber() != null && !personalData.getAadharCardNumber().isEmpty()) {
                personalDataTO.setAadhaarCardNumber(personalData.getAadharCardNumber());
            }
            if (personalData.getMotherTongue() != null) {
                personalDataTO.setMotherTongueId(String.valueOf(personalData.getMotherTongue().getId()));
            }
            if (personalData.getFatherPANNumber() != null && !personalData.getFatherPANNumber().isEmpty()) {
                personalDataTO.setFatherPANNumber(personalData.getFatherPANNumber());
            }
            if (personalData.getFatherAadhaarNumber() != null && !personalData.getFatherAadhaarNumber().isEmpty()) {
                personalDataTO.setFatherAadhaarNumber(personalData.getFatherAadhaarNumber());
            }
            if (personalData.getIsmgquota() != null) {
                personalDataTO.setIsmgquota(personalData.getIsmgquota());
            }
            if (personalData.getRecommentedBy() != null && !personalData.getRecommentedBy().isEmpty()) {
                personalDataTO.setRecommentedBy(personalData.getRecommentedBy());
            }
            if (personalData.getRecommendedBy() != null && !personalData.getRecommendedBy().isEmpty()) {
                personalDataTO.setRecommendDeatails(personalData.getRecommendedBy());
            }
            if (personalData.getRecommentedPersonDesignation() != null && !personalData.getRecommentedPersonDesignation().isEmpty()) {
                personalDataTO.setRecommentedPersonDesignation(personalData.getRecommentedPersonDesignation());
            }
            if (personalData.getRecommentedPersonMobile() != null && !personalData.getRecommentedPersonMobile().isEmpty()) {
                personalDataTO.setRecommentedPersonMobile(personalData.getRecommentedPersonMobile());
            }
            if (personalData.getPreferenceNoCAP() != null) {
                personalDataTO.setPreferenceNoCAP(personalData.getPreferenceNoCAP());
            }
            loginForm.setPDataEdited(personalData.getIsPDataEdited());
            final ISingleFieldMasterTransaction singleFieldTx = (ISingleFieldMasterTransaction)SingleFieldMasterTransactionImpl.getInstance();
            final List<MotherTongue> motherTongueList = (List<MotherTongue>)singleFieldTx.getMotherTongueFields();
            final Map<Integer, String> motherTongues = new HashMap<Integer, String>();
            for (final MotherTongue motherTongue : motherTongueList) {
                motherTongues.put(motherTongue.getId(), motherTongue.getName());
            }
            personalDataTO.setMotherTongues((Map)motherTongues);
            loginForm.setResidentTypes(OnlineApplicationHandler.getInstance().getResidentTypes());
            loginForm.setNationalities(OnlineApplicationHandler.getInstance().getNationalities());
            loginForm.setCountries(CountryHandler.getInstance().getCountries());
            Map<Integer, String> stateMap = new HashMap<Integer, String>();
            if (loginForm.getBirthCountryId() != 0) {
                stateMap = (Map<Integer, String>)CommonAjaxImpl.getInstance().getStates(loginForm.getBirthCountryId());
            }
            else {
                stateMap = (Map<Integer, String>)CommonAjaxImpl.getInstance().getStates(0);
            }
            loginForm.setStateMap((Map)stateMap);
            Map<Integer, String> curAddrStateMap = new HashMap<Integer, String>();
            if (loginForm.getCurAddrCountyId() != 0) {
                curAddrStateMap = (Map<Integer, String>)CommonAjaxImpl.getInstance().getStates(loginForm.getCurAddrCountyId());
            }
            else {
                curAddrStateMap = (Map<Integer, String>)CommonAjaxImpl.getInstance().getStates(0);
            }
            loginForm.setCurAddrStateMap((Map)curAddrStateMap);
            Map<Integer, String> perAddrStateMap = new HashMap<Integer, String>();
            if (loginForm.getPerAddrCountyId() != 0) {
                perAddrStateMap = (Map<Integer, String>)CommonAjaxImpl.getInstance().getStates(loginForm.getPerAddrCountyId());
            }
            else {
                perAddrStateMap = (Map<Integer, String>)CommonAjaxImpl.getInstance().getStates(0);
            }
            loginForm.setPerAddrStateMap((Map)perAddrStateMap);
            Map<Integer, String> parentAddstateMap = new HashMap<Integer, String>();
            if (loginForm.getParentAddrCountyId() != 0) {
                parentAddstateMap = (Map<Integer, String>)CommonAjaxImpl.getInstance().getStates(loginForm.getParentAddrCountyId());
            }
            else {
                parentAddstateMap = (Map<Integer, String>)CommonAjaxImpl.getInstance().getStates(0);
            }
            loginForm.setParentStateMap((Map)parentAddstateMap);
            Map<Integer, String> guardianAddstateMap = new HashMap<Integer, String>();
            if (loginForm.getGuardianAddrCountyId() != 0) {
                guardianAddstateMap = (Map<Integer, String>)CommonAjaxImpl.getInstance().getStates(loginForm.getGuardianAddrCountyId());
            }
            else {
                guardianAddstateMap = (Map<Integer, String>)CommonAjaxImpl.getInstance().getStates(0);
            }
            loginForm.setGuardianStateMap((Map)guardianAddstateMap);
            final List<StateTO> permanentStates = (List<StateTO>)StateHandler.getInstance().getStates();
            if (permanentStates != null) {
                for (final StateTO stateTO : permanentStates) {
                    if (personalData.getStateByPermanentAddressStateId() != null) {
                        if (stateTO.getId() != personalData.getStateByPermanentAddressStateId().getId()) {
                            continue;
                        }
                        final List<DistrictTO> districtList = (List<DistrictTO>)stateTO.getDistrictList();
                        Collections.sort(districtList);
                        loginForm.setEditPermanentDistrict((List)districtList);
                    }
                    else {
                        final List<DistrictTO> districtList = new ArrayList<DistrictTO>();
                        loginForm.setEditPermanentDistrict((List)districtList);
                    }
                }
            }
            final List<StateTO> currentStates = (List<StateTO>)StateHandler.getInstance().getStates();
            if (currentStates != null) {
                for (final StateTO stateTO2 : currentStates) {
                    if (personalData.getStateByCurrentAddressStateId() != null) {
                        if (stateTO2.getId() != personalData.getStateByCurrentAddressStateId().getId()) {
                            continue;
                        }
                        final List<DistrictTO> districtList2 = (List<DistrictTO>)stateTO2.getDistrictList();
                        Collections.sort(districtList2);
                        loginForm.setEditCurrentDistrict((List)districtList2);
                    }
                    else {
                        final List<DistrictTO> districtList2 = new ArrayList<DistrictTO>();
                        loginForm.setEditCurrentDistrict((List)districtList2);
                    }
                }
            }
            loginForm.setOccupations(OccupationTransactionHandler.getInstance().getAllOccupation());
            loginForm.setIncomeList(OnlineApplicationHandler.getInstance().getIncomes());
            final List<CurrencyTO> currencyList = (List<CurrencyTO>)OnlineApplicationHandler.getInstance().getCurrencies();
            final Map<Integer, String> currencyMap = new HashMap<Integer, String>();
            if (currencyList != null && currencyList.size() > 0) {
                for (final CurrencyTO curTo : currencyList) {
                    if (curTo != null) {
                        currencyMap.put(curTo.getId(), curTo.getName());
                    }
                }
            }
            loginForm.setCurrencyList((List)currencyList);
            loginForm.setCurrencyMap((Map)currencyMap);
            loginForm.setReligions(ReligionHandler.getInstance().getReligion());
            loginForm.setSubReligions(SubReligionHandler.getInstance().getSubReligion());
            loginForm.setSubReligionMap(CommonAjaxHandler.getInstance().getSubReligion());
            if (personalData.getReligion() != null) {
                loginForm.setSubCasteMap(CommonAjaxHandler.getInstance().getSubCasteByReligion(personalData.getReligion().getId()));
            }
            final ExamGenHandler genHandler = new ExamGenHandler();
            final HashMap<Integer, String> secondLanguage = (HashMap<Integer, String>)genHandler.getSecondLanguage();
            loginForm.setSecondLanguageList((HashMap)secondLanguage);
            loginForm.setCheckReligionId(Integer.valueOf(CMSConstants.RELIGION_CHRISTIAN_TYPE));
            final DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
            final long startTime = fmt.parse(CMSConstants.RADIOS_HIDE_DATE_SPORTS).getTime();
            final long presentTime = Calendar.getInstance().getTime().getTime();
            if (presentTime > startTime) {
                loginForm.setDateExpired(true);
            }
            else {
                loginForm.setDateExpired(false);
            }
            final AdmApplnTO applnTO = new AdmApplnTO();
            if (student.getAdmAppln().getAdmissionNumber() != null) {
                applnTO.setAdmissionNumber(student.getAdmAppln().getAdmissionNumber());
            }
            if (student.getAdmAppln().getAdmittedThrough() != null) {
                applnTO.setAdmittedThroughId(String.valueOf(student.getAdmAppln().getAdmittedThrough().getId()));
            }
            year = student.getAdmAppln().getAppliedYear();
            if (student.getAdmAppln().getApplnDocs() != null && student.getAdmAppln().getApplnDocs().size() != 0) {
                final List<ApplnDocTO> editDocuments = (List<ApplnDocTO>)OnlineApplicationHelper.getInstance().copyPropertiesEditDocValue(student.getAdmAppln(), Integer.parseInt(loginForm.getCourseId()), applnTO, year);
                applnTO.setEditDocuments((List)editDocuments);
            }
            else {
                final List<ApplnDocTO> reqList = (List<ApplnDocTO>)OnlineApplicationHandler.getInstance().getRequiredDocList(String.valueOf(loginForm.getCourseId()), year);
                applnTO.setEditDocuments((List)reqList);
            }
            loginForm.setApplicantDetails(applnTO);
            if (personalData.getMbaEntranceExam() != null) {
                personalDataTO.setMbaEntranceExamId(String.valueOf(personalData.getMbaEntranceExam().getId()));
                personalDataTO.setEntranceMarksSecured(String.valueOf(personalData.getEntranceMarksSecured()));
            }
        }
        return personalDataTO;
    }
    
    public Student getStudentObj(final String studentid) throws Exception {
        final ILoginTransaction transaction = (ILoginTransaction)new LoginTransactionImpl();
        final Student student = transaction.getStudentObj(studentid);
        return student;
    }
    
    public boolean savePersonalData(final LoginForm loginForm, final PersonalDataTO dataTO, final PersonalData data, final Student student, final HttpServletRequest request) throws Exception {
        if (loginForm.getPersonalData() != null) {
            final PersonalDataTO dataTo = loginForm.getPersonalData();
            data.setModifiedBy(loginForm.getUserId());
            data.setLastModifiedDate(new Date());
            data.setFirstName(dataTo.getFirstName().toUpperCase());
            data.setMiddleName(dataTo.getMiddleName());
            data.setLastName(dataTo.getLastName());
            if (dataTo.getDob() != null) {
                data.setDateOfBirth((Date)CommonUtil.ConvertStringToSQLDate(dataTo.getDob()));
            }
            if (dataTo.getBirthPlace() != null && !dataTo.getBirthPlace().isEmpty()) {
                data.setBirthPlace(dataTo.getBirthPlace());
            }
            if (dataTo.getBirthState() != null && !StringUtils.isEmpty(dataTo.getBirthState()) && StringUtils.isNumeric(dataTo.getBirthState())) {
                final State birthState = new State();
                birthState.setId(Integer.parseInt(dataTo.getBirthState()));
                data.setStateByStateId(birthState);
                data.setStateOthers((String)null);
            }
            else if (dataTo.getStateOthers() != null && !dataTo.getStateOthers().isEmpty()) {
                data.setStateByStateId((State)null);
                data.setStateOthers(dataTo.getStateOthers());
            }
            if (dataTo.getHeight() != null && !StringUtils.isEmpty(dataTo.getHeight()) && StringUtils.isNumeric(dataTo.getHeight())) {
                data.setHeight(Integer.valueOf(Integer.parseInt(dataTo.getHeight())));
            }
            if (dataTo.getWeight() != null && !StringUtils.isEmpty(dataTo.getWeight()) && CommonUtil.isValidDecimal(dataTo.getWeight())) {
                data.setWeight(new BigDecimal(dataTo.getWeight()));
            }
            if (dataTo.getLanguageByLanguageRead() != null && !StringUtils.isEmpty(dataTo.getLanguageByLanguageRead())) {
                data.setLanguageByLanguageRead(dataTo.getLanguageByLanguageRead());
            }
            else {
                data.setLanguageByLanguageRead((String)null);
            }
            if (dataTo.getLanguageByLanguageWrite() != null && !StringUtils.isEmpty(dataTo.getLanguageByLanguageWrite())) {
                data.setLanguageByLanguageWrite(dataTo.getLanguageByLanguageWrite());
            }
            else {
                data.setLanguageByLanguageWrite((String)null);
            }
            if (dataTo.getLanguageByLanguageSpeak() != null && !StringUtils.isEmpty(dataTo.getLanguageByLanguageSpeak())) {
                data.setLanguageByLanguageSpeak(dataTo.getLanguageByLanguageSpeak());
            }
            else {
                data.setLanguageByLanguageSpeak((String)null);
            }
            if (dataTo.getMotherTongue() != null && !StringUtils.isEmpty(dataTo.getMotherTongue()) && StringUtils.isNumeric(dataTo.getMotherTongue())) {
                final MotherTongue readlang = new MotherTongue();
                readlang.setId(Integer.parseInt(dataTo.getMotherTongue()));
                data.setMotherTongue(readlang);
            }
            else {
                data.setMotherTongue((MotherTongue)null);
            }
            if (dataTo.getTrainingDuration() != null && !StringUtils.isEmpty(dataTo.getTrainingDuration()) && StringUtils.isNumeric(dataTo.getTrainingDuration())) {
                data.setTrainingDuration(Integer.valueOf(Integer.parseInt(dataTo.getTrainingDuration())));
            }
            else {
                data.setTrainingDuration(Integer.valueOf(0));
            }
            if (dataTo.getTrainingInstAddress() != null && !dataTo.getTrainingInstAddress().isEmpty()) {
                data.setTrainingInstAddress(dataTo.getTrainingInstAddress());
            }
            if (dataTo.getTrainingProgName() != null && !dataTo.getTrainingProgName().isEmpty()) {
                data.setTrainingProgName(dataTo.getTrainingProgName());
            }
            if (dataTo.getTrainingPurpose() != null && !dataTo.getTrainingPurpose().isEmpty()) {
                data.setTrainingPurpose(dataTo.getTrainingPurpose());
            }
            if (dataTo.getCourseKnownBy() != null && !dataTo.getCourseKnownBy().isEmpty()) {
                data.setCourseKnownBy(dataTo.getCourseKnownBy());
            }
            if (dataTo.getCourseOptReason() != null && !dataTo.getCourseOptReason().isEmpty()) {
                data.setCourseOptReason(dataTo.getCourseOptReason());
            }
            if (dataTo.getStrength() != null && !dataTo.getStrength().isEmpty()) {
                data.setStrength(dataTo.getStrength());
            }
            if (dataTo.getWeakness() != null && !dataTo.getWeakness().isEmpty()) {
                data.setWeakness(dataTo.getWeakness());
            }
            if (dataTo.getOtherAddnInfo() != null && !dataTo.getOtherAddnInfo().isEmpty()) {
                data.setOtherAddnInfo(dataTo.getOtherAddnInfo());
            }
            if (dataTo.getSecondLanguage() != null && !dataTo.getSecondLanguage().isEmpty()) {
                data.setSecondLanguage(dataTo.getSecondLanguage());
            }
            if (dataTo.getNationality() != null && !StringUtils.isEmpty(dataTo.getNationality()) && StringUtils.isNumeric(dataTo.getNationality())) {
                final Nationality nat = new Nationality();
                nat.setId(Integer.parseInt(dataTo.getNationality()));
                data.setNationality(nat);
            }
            if (dataTo.getBloodGroup() != null && !dataTo.getBloodGroup().isEmpty()) {
                data.setBloodGroup(dataTo.getBloodGroup().toUpperCase());
            }
            if (dataTo.getEmail() != null && !dataTo.getEmail().isEmpty()) {
                data.setEmail(dataTo.getEmail());
            }
            if (dataTo.getGender() != null && !dataTo.getGender().isEmpty()) {
                data.setGender(dataTo.getGender().toUpperCase());
            }
            data.setIsHandicapped(Boolean.valueOf(dataTo.isHandicapped()));
            data.setIsSportsPerson(Boolean.valueOf(dataTo.isSportsPerson()));
            if (dataTo.getSportsDescription() != null && !dataTo.getSportsDescription().isEmpty()) {
                data.setSportsPersonDescription(dataTo.getSportsDescription());
            }
            if (dataTo.getHadnicappedDescription() != null && !dataTo.getHadnicappedDescription().isEmpty()) {
                data.setHandicappedDescription(dataTo.getHadnicappedDescription());
            }
            if (dataTo.getUgcourse() != null && !StringUtils.isEmpty(dataTo.getUgcourse()) && StringUtils.isNumeric(dataTo.getUgcourse())) {
                final UGCoursesBO ug = new UGCoursesBO();
                ug.setId(Integer.parseInt(dataTo.getUgcourse()));
                data.setUgcourse(ug);
            }
            data.setSports(dataTo.getSports());
            data.setArts(dataTo.getArts());
            data.setSportsParticipate(dataTo.getSportsParticipate());
            data.setArtsParticipate(dataTo.getArtsParticipate());
            data.setFatherMobile(dataTo.getFatherMobile());
            data.setMotherMobile(dataTo.getMotherMobile());
            data.setIsNcccertificate(Boolean.valueOf(dataTo.isNcccertificate()));
            data.setIsNsscertificate(Boolean.valueOf(dataTo.isNsscertificate()));
            data.setIsExcervice(Boolean.valueOf(dataTo.isExservice()));
            if (dataTo.isNcccertificate()) {
                data.setNccgrade(dataTo.getNccgrades());
            }
            else {
                data.setNccgrade((String)null);
            }
            if (dataTo.isNsscertificate()) {
                data.setNssgrade(dataTo.getNssgrades());
            }
            else {
                data.setNssgrade((String)null);
            }
            if (dataTo.getDioceseOthers() != null && !StringUtils.isEmpty(dataTo.getDioceseOthers())) {
                data.setDioceseOthers(dataTo.getDioceseOthers());
            }
            else {
                data.setDioceseOthers((String)null);
            }
            if (dataTo.getParishOthers() != null && !StringUtils.isEmpty(dataTo.getParishOthers())) {
                data.setParishOthers(dataTo.getParishOthers());
            }
            else {
                data.setParishOthers((String)null);
            }
            if (loginForm.isSameTempAddr()) {
                if (dataTo.getCurrentDistricId() != null && !StringUtils.isEmpty(dataTo.getCurrentDistricId()) && StringUtils.isNumeric(dataTo.getCurrentDistricId()) && Integer.parseInt(dataTo.getCurrentDistricId()) != 0) {
                    final District permState = new District();
                    permState.setId(Integer.parseInt(dataTo.getCurrentDistricId()));
                    data.setStateByParentAddressDistrictId(permState);
                    data.setPermanentAddressDistrcictOthers((String)null);
                }
            }
            else if (dataTo.getPermanentDistricId() != null && !StringUtils.isEmpty(dataTo.getPermanentDistricId()) && StringUtils.isNumeric(dataTo.getPermanentDistricId())) {
                if (Integer.parseInt(dataTo.getPermanentDistricId()) != 0) {
                    final District permState = new District();
                    permState.setId(Integer.parseInt(dataTo.getPermanentDistricId()));
                    data.setStateByParentAddressDistrictId(permState);
                    data.setPermanentAddressDistrcictOthers((String)null);
                }
            }
            else {
                data.setStateByParentAddressDistrictId((District)null);
                data.setPermanentAddressDistrcictOthers(dataTo.getPermanentAddressDistrictOthers());
            }
            if (dataTo.getCurrentDistricId() != null && !StringUtils.isEmpty(dataTo.getCurrentDistricId()) && StringUtils.isNumeric(dataTo.getCurrentDistricId())) {
                if (Integer.parseInt(dataTo.getCurrentDistricId()) != 0) {
                    final District currState = new District();
                    currState.setId(Integer.parseInt(dataTo.getCurrentDistricId()));
                    data.setStateByCurrentAddressDistrictId(currState);
                    data.setCurrenttAddressDistrcictOthers((String)null);
                }
            }
            else {
                data.setStateByCurrentAddressDistrictId((District)null);
                data.setCurrenttAddressDistrcictOthers(dataTo.getCurrentAddressDistrictOthers());
            }
            if (dataTo.getStream() != null && !StringUtils.isEmpty(dataTo.getStream()) && StringUtils.isNumeric(dataTo.getStream())) {
                final EducationStream stream = new EducationStream();
                stream.setId(Integer.parseInt(dataTo.getStream()));
                data.setStream(stream);
            }
            if (loginForm.getCourseId() != null && loginForm.getCourseId().equalsIgnoreCase("18")) {
                if (dataTo.getNoofenglishCoreCourses() != null) {
                    data.setNoofenglishCoreCourses(dataTo.getNoofenglishCoreCourses());
                }
            }
            else {
                data.setNoofenglishCoreCourses((String)null);
            }
            ResidentCategory res_cat = null;
            if (dataTo.getResidentCategory() != null && !StringUtils.isEmpty(dataTo.getResidentCategory()) && StringUtils.isNumeric(dataTo.getResidentCategory())) {
                res_cat = new ResidentCategory();
                res_cat.setId(Integer.parseInt(dataTo.getResidentCategory()));
                data.setResidentCategory(res_cat);
            }
            data.setRuralUrban(Character.valueOf(dataTo.getAreaType()));
            if (dataTo.getPhNo1() != null && !dataTo.getPhNo1().isEmpty()) {
                data.setPhNo1(dataTo.getPhNo1());
            }
            if (dataTo.getPhNo2() != null && !dataTo.getPhNo2().isEmpty()) {
                data.setPhNo2(dataTo.getPhNo2());
            }
            if (dataTo.getPhNo3() != null && !dataTo.getPhNo3().isEmpty()) {
                data.setPhNo3(dataTo.getPhNo3());
            }
            if (dataTo.getMobileNo1() != null && !dataTo.getMobileNo1().isEmpty()) {
                data.setMobileNo1(dataTo.getMobileNo1());
            }
            if (dataTo.getMobileNo2() != null && !dataTo.getMobileNo2().isEmpty()) {
                data.setMobileNo2(dataTo.getMobileNo2());
            }
            if (dataTo.getMobileNo3() != null && !dataTo.getMobileNo3().isEmpty()) {
                data.setMobileNo3(dataTo.getMobileNo3());
            }
            Religion religionbo = null;
            if (dataTo.getReligionId() != null && !StringUtils.isEmpty(dataTo.getReligionId()) && StringUtils.isNumeric(dataTo.getReligionId())) {
                religionbo = new Religion();
                religionbo.setId(Integer.parseInt(dataTo.getReligionId()));
                if (dataTo.getSubReligionId() != null && !StringUtils.isEmpty(dataTo.getSubReligionId()) && StringUtils.isNumeric(dataTo.getSubReligionId())) {
                    final ReligionSection subreligionBO = new ReligionSection();
                    subreligionBO.setId(Integer.parseInt(dataTo.getSubReligionId()));
                    subreligionBO.setReligion(religionbo);
                    data.setReligionSection(subreligionBO);
                    data.setReligionSectionOthers((String)null);
                }
                else {
                    data.setReligionSection((ReligionSection)null);
                    data.setReligionSectionOthers(dataTo.getReligionSectionOthers());
                }
                data.setReligion(religionbo);
                data.setReligionOthers((String)null);
            }
            else {
                data.setReligion((Religion)null);
                data.setReligionOthers(dataTo.getReligionOthers());
                if (dataTo.getSubReligionId() != null && !StringUtils.isEmpty(dataTo.getSubReligionId()) && StringUtils.isNumeric(dataTo.getSubReligionId())) {
                    final ReligionSection subreligionBO = new ReligionSection();
                    subreligionBO.setId(Integer.parseInt(dataTo.getSubReligionId()));
                    subreligionBO.setReligion(religionbo);
                    data.setReligionSection(subreligionBO);
                    data.setReligionSectionOthers((String)null);
                }
                else {
                    data.setReligionSection((ReligionSection)null);
                    data.setReligionSectionOthers(dataTo.getReligionSectionOthers());
                }
            }
            if (dataTo.getCasteId() != null && !StringUtils.isEmpty(dataTo.getCasteId()) && StringUtils.isNumeric(dataTo.getCasteId())) {
                final Caste casteBO = new Caste();
                casteBO.setId(Integer.parseInt(dataTo.getCasteId()));
                data.setCaste(casteBO);
                data.setCasteOthers((String)null);
            }
            else {
                data.setCaste((Caste)null);
                data.setCasteOthers(dataTo.getCasteOthers());
            }
            if (dataTo.getPassportNo() != null && !dataTo.getPassportNo().isEmpty()) {
                data.setPassportNo(dataTo.getPassportNo());
            }
            if (dataTo.getResidentPermitNo() != null && !dataTo.getResidentPermitNo().isEmpty()) {
                data.setResidentPermitNo(dataTo.getResidentPermitNo());
            }
            if (dataTo.getPassportValidity() != null && !StringUtils.isEmpty(dataTo.getPassportValidity())) {
                data.setPassportValidity((Date)CommonUtil.ConvertStringToSQLDate(dataTo.getPassportValidity()));
            }
            if (dataTo.getResidentPermitDate() != null && !StringUtils.isEmpty(dataTo.getResidentPermitDate())) {
                data.setResidentPermitDate((Date)CommonUtil.ConvertStringToSQLDate(dataTo.getResidentPermitDate()));
            }
            if (dataTo.getPassportCountry() != 0) {
                final Country passportcnt = new Country();
                passportcnt.setId(dataTo.getPassportCountry());
                data.setCountryByPassportCountryId(passportcnt);
            }
            if (dataTo.getBirthCountry() != null && !StringUtils.isEmpty(dataTo.getBirthCountry().trim()) && StringUtils.isNumeric(dataTo.getBirthCountry())) {
                final Country ownCnt = new Country();
                ownCnt.setId(Integer.parseInt(dataTo.getBirthCountry()));
                data.setCountryByCountryId(ownCnt);
                loginForm.setBirthCountryId(Integer.parseInt(dataTo.getBirthCountry()));
            }
            if (loginForm.isSameTempAddr()) {
                if (dataTo.getCurrentAddressLine1() != null && !dataTo.getCurrentAddressLine1().isEmpty()) {
                    data.setPermanentAddressLine1(dataTo.getCurrentAddressLine1());
                }
                if (dataTo.getCurrentAddressLine2() != null && !dataTo.getCurrentAddressLine2().isEmpty()) {
                    data.setPermanentAddressLine2(dataTo.getCurrentAddressLine2());
                }
                if (dataTo.getCurrentAddressZipCode() != null && !dataTo.getCurrentAddressZipCode().isEmpty()) {
                    data.setPermanentAddressZipCode(dataTo.getCurrentAddressZipCode());
                }
                if (dataTo.getCurrentCityName() != null && !dataTo.getCurrentCityName().isEmpty()) {
                    data.setCityByPermanentAddressCityId(dataTo.getCurrentCityName());
                }
                if (dataTo.getCurrentStateId() != null && !StringUtils.isEmpty(dataTo.getCurrentStateId()) && StringUtils.isNumeric(dataTo.getCurrentStateId())) {
                    if (Integer.parseInt(dataTo.getCurrentStateId()) != 0) {
                        final State currState2 = new State();
                        currState2.setId(Integer.parseInt(dataTo.getCurrentStateId()));
                        data.setStateByPermanentAddressStateId(currState2);
                        data.setPermanentAddressStateOthers((String)null);
                    }
                }
                else {
                    data.setStateByPermanentAddressStateId((State)null);
                    data.setPermanentAddressStateOthers(dataTo.getCurrentAddressStateOthers());
                }
                if (dataTo.getCurrentCountryId() > 0) {
                    final Country currCnt = new Country();
                    currCnt.setId(dataTo.getCurrentCountryId());
                    data.setCountryByPermanentAddressCountryId(currCnt);
                    loginForm.setPerAddrCountyId(dataTo.getCurrentCountryId());
                }
                if (dataTo.getCurrentStreet() != null && !dataTo.getCurrentStreet().isEmpty()) {
                    data.setPermanentStreet(dataTo.getCurrentStreet());
                }
            }
            else {
                if (dataTo.getPermanentAddressLine1() != null && !dataTo.getPermanentAddressLine1().isEmpty()) {
                    data.setPermanentAddressLine1(dataTo.getPermanentAddressLine1());
                }
                if (dataTo.getPermanentAddressLine2() != null && !dataTo.getPermanentAddressLine2().isEmpty()) {
                    data.setPermanentAddressLine2(dataTo.getPermanentAddressLine2());
                }
                if (dataTo.getPermanentAddressZipCode() != null && !dataTo.getPermanentAddressZipCode().isEmpty()) {
                    data.setPermanentAddressZipCode(dataTo.getPermanentAddressZipCode());
                }
                if (dataTo.getPermanentCityName() != null && !dataTo.getPermanentCityName().isEmpty()) {
                    data.setCityByPermanentAddressCityId(dataTo.getPermanentCityName());
                }
                if (dataTo.getPermanentStreet() != null && !dataTo.getPermanentStreet().isEmpty()) {
                    data.setPermanentStreet(dataTo.getPermanentStreet());
                }
                if (dataTo.getPermanentStateId() != null && !StringUtils.isEmpty(dataTo.getPermanentStateId()) && StringUtils.isNumeric(dataTo.getPermanentStateId())) {
                    if (Integer.parseInt(dataTo.getPermanentStateId()) != 0) {
                        final State permState2 = new State();
                        permState2.setId(Integer.parseInt(dataTo.getPermanentStateId()));
                        data.setStateByPermanentAddressStateId(permState2);
                        data.setPermanentAddressStateOthers((String)null);
                    }
                }
                else {
                    data.setStateByPermanentAddressStateId((State)null);
                    data.setPermanentAddressStateOthers(dataTo.getPermanentAddressStateOthers());
                }
                if (dataTo.getPermanentCountryId() != 0) {
                    final Country permCnt = new Country();
                    permCnt.setId(dataTo.getPermanentCountryId());
                    data.setCountryByPermanentAddressCountryId(permCnt);
                    loginForm.setPerAddrCountyId(dataTo.getPermanentCountryId());
                }
            }
            if (dataTo.getCurrentAddressLine1() != null && !dataTo.getCurrentAddressLine1().isEmpty()) {
                data.setCurrentAddressLine1(dataTo.getCurrentAddressLine1());
            }
            if (dataTo.getCurrentAddressLine2() != null && !dataTo.getCurrentAddressLine2().isEmpty()) {
                data.setCurrentAddressLine2(dataTo.getCurrentAddressLine2());
            }
            if (dataTo.getCurrentAddressZipCode() != null && !dataTo.getCurrentAddressZipCode().isEmpty()) {
                data.setCurrentAddressZipCode(dataTo.getCurrentAddressZipCode());
            }
            if (dataTo.getCurrentCityName() != null && !dataTo.getCurrentCityName().isEmpty()) {
                data.setCityByCurrentAddressCityId(dataTo.getCurrentCityName());
            }
            if (dataTo.getCurrentStateId() != null && !StringUtils.isEmpty(dataTo.getCurrentStateId()) && StringUtils.isNumeric(dataTo.getCurrentStateId())) {
                if (Integer.parseInt(dataTo.getCurrentStateId()) != 0) {
                    final State currState2 = new State();
                    currState2.setId(Integer.parseInt(dataTo.getCurrentStateId()));
                    data.setStateByCurrentAddressStateId(currState2);
                    data.setCurrentAddressStateOthers((String)null);
                }
            }
            else {
                data.setStateByCurrentAddressStateId((State)null);
                data.setCurrentAddressStateOthers(dataTo.getCurrentAddressStateOthers());
            }
            if (dataTo.getCurrentCountryId() > 0) {
                final Country currCnt = new Country();
                currCnt.setId(dataTo.getCurrentCountryId());
                data.setCountryByCurrentAddressCountryId(currCnt);
                loginForm.setCurAddrCountyId(dataTo.getCurrentCountryId());
            }
            if (dataTo.getCurrentStreet() != null && !dataTo.getCurrentStreet().isEmpty()) {
                data.setCurrentStreet(dataTo.getCurrentStreet());
            }
            if (dataTo.getFatherOccupationAddress() != null && !dataTo.getFatherOccupationAddress().isEmpty()) {
                data.setFatherOccupationAddress(dataTo.getFatherOccupationAddress());
            }
            if (dataTo.getMotherOccupationAddress() != null && !dataTo.getMotherOccupationAddress().isEmpty()) {
                data.setMotherOccupationAddress(dataTo.getMotherOccupationAddress());
            }
            if (dataTo.getFatherEducation() != null && !dataTo.getFatherEducation().isEmpty()) {
                data.setFatherEducation(dataTo.getFatherEducation());
            }
            if (dataTo.getMotherEducation() != null && !dataTo.getMotherEducation().isEmpty()) {
                data.setMotherEducation(dataTo.getMotherEducation());
            }
            if (dataTo.getFatherName() != null && !dataTo.getFatherName().isEmpty()) {
                data.setFatherName(dataTo.getFatherName());
            }
            if (dataTo.getMotherName() != null && !dataTo.getMotherName().isEmpty()) {
                data.setMotherName(dataTo.getMotherName());
            }
            if (dataTo.getFatherEmail() != null && !dataTo.getFatherEmail().isEmpty()) {
                data.setFatherEmail(dataTo.getFatherEmail());
            }
            if (dataTo.getMotherEmail() != null && !dataTo.getMotherEmail().isEmpty()) {
                data.setMotherEmail(dataTo.getMotherEmail());
            }
            if (dataTo.getFatherIncomeId() != null && !StringUtils.isEmpty(dataTo.getFatherIncomeId()) && StringUtils.isNumeric(dataTo.getFatherIncomeId())) {
                final Income fatherIncome = new Income();
                if (dataTo.getFatherCurrencyId() != null && !StringUtils.isEmpty(dataTo.getFatherCurrencyId()) && StringUtils.isNumeric(dataTo.getFatherCurrencyId())) {
                    final Currency fatherCurrency = new Currency();
                    fatherCurrency.setId(Integer.parseInt(dataTo.getFatherCurrencyId()));
                    fatherIncome.setCurrency(fatherCurrency);
                    data.setCurrencyByFatherIncomeCurrencyId(fatherCurrency);
                }
                else {
                    fatherIncome.setCurrency((Currency)null);
                    data.setCurrencyByFatherIncomeCurrencyId((Currency)null);
                }
                fatherIncome.setId(Integer.parseInt(dataTo.getFatherIncomeId()));
                data.setIncomeByFatherIncomeId(fatherIncome);
            }
            else {
                data.setIncomeByFatherIncomeId((Income)null);
                if (dataTo.getFatherCurrencyId() != null && !StringUtils.isEmpty(dataTo.getFatherCurrencyId()) && StringUtils.isNumeric(dataTo.getFatherCurrencyId())) {
                    final Currency fatherCurrency2 = new Currency();
                    fatherCurrency2.setId(Integer.parseInt(dataTo.getFatherCurrencyId()));
                    data.setCurrencyByFatherIncomeCurrencyId(fatherCurrency2);
                }
                else {
                    data.setCurrencyByFatherIncomeCurrencyId((Currency)null);
                }
            }
            if (dataTo.getMotherIncomeId() != null && !StringUtils.isEmpty(dataTo.getMotherIncomeId()) && StringUtils.isNumeric(dataTo.getMotherIncomeId())) {
                final Income motherIncome = new Income();
                if (dataTo.getMotherCurrencyId() != null && !StringUtils.isEmpty(dataTo.getMotherCurrencyId()) && StringUtils.isNumeric(dataTo.getMotherCurrencyId())) {
                    final Currency motherCurrency = new Currency();
                    motherCurrency.setId(Integer.parseInt(dataTo.getMotherCurrencyId()));
                    motherIncome.setCurrency(motherCurrency);
                    data.setCurrencyByMotherIncomeCurrencyId(motherCurrency);
                }
                else {
                    motherIncome.setCurrency((Currency)null);
                    data.setCurrencyByMotherIncomeCurrencyId((Currency)null);
                }
                motherIncome.setId(Integer.parseInt(dataTo.getMotherIncomeId()));
                data.setIncomeByMotherIncomeId(motherIncome);
            }
            else {
                data.setIncomeByMotherIncomeId((Income)null);
                if (dataTo.getMotherCurrencyId() != null && !StringUtils.isEmpty(dataTo.getMotherCurrencyId()) && StringUtils.isNumeric(dataTo.getMotherCurrencyId())) {
                    final Currency motherCurrency2 = new Currency();
                    motherCurrency2.setId(Integer.parseInt(dataTo.getMotherCurrencyId()));
                    data.setCurrencyByMotherIncomeCurrencyId(motherCurrency2);
                }
                else {
                    data.setCurrencyByMotherIncomeCurrencyId((Currency)null);
                }
            }
            if (dataTo.getFatherOccupationId() != null && !StringUtils.isEmpty(dataTo.getFatherOccupationId()) && StringUtils.isNumeric(dataTo.getFatherOccupationId()) && !dataTo.getFatherOccupationId().equalsIgnoreCase("other")) {
                final Occupation fatherOccupation = new Occupation();
                fatherOccupation.setId(Integer.parseInt(dataTo.getFatherOccupationId()));
                data.setOccupationByFatherOccupationId(fatherOccupation);
                data.setOtherOccupationFather((String)null);
            }
            else if (dataTo.getFatherOccupationId() != null && !StringUtils.isEmpty(dataTo.getFatherOccupationId()) && dataTo.getFatherOccupationId().equalsIgnoreCase("Other") && dataTo.getOtherOccupationFather() != null && !StringUtils.isEmpty(dataTo.getOtherOccupationFather())) {
                data.setOtherOccupationFather(dataTo.getOtherOccupationFather());
                data.setOccupationByFatherOccupationId((Occupation)null);
            }
            else {
                data.setOccupationByFatherOccupationId((Occupation)null);
            }
            if (dataTo.getMotherOccupationId() != null && !StringUtils.isEmpty(dataTo.getMotherOccupationId()) && StringUtils.isNumeric(dataTo.getMotherOccupationId()) && !dataTo.getMotherOccupationId().equalsIgnoreCase("other")) {
                final Occupation motherOccupation = new Occupation();
                motherOccupation.setId(Integer.parseInt(dataTo.getMotherOccupationId()));
                data.setOccupationByMotherOccupationId(motherOccupation);
                data.setOtherOccupationMother((String)null);
            }
            else if (dataTo.getMotherOccupationId() != null && !StringUtils.isEmpty(dataTo.getMotherOccupationId()) && dataTo.getMotherOccupationId().equalsIgnoreCase("Other") && dataTo.getOtherOccupationMother() != null && !StringUtils.isEmpty(dataTo.getOtherOccupationMother())) {
                data.setOtherOccupationMother(dataTo.getOtherOccupationMother());
                data.setOccupationByMotherOccupationId((Occupation)null);
            }
            else {
                data.setOccupationByMotherOccupationId((Occupation)null);
            }
            if (loginForm.isSameParentAddr()) {
                if (dataTo.getCurrentAddressLine1() != null && !dataTo.getCurrentAddressLine1().isEmpty()) {
                    data.setParentAddressLine1(dataTo.getCurrentAddressLine1());
                }
                if (dataTo.getCurrentAddressLine2() != null && !dataTo.getCurrentAddressLine2().isEmpty()) {
                    data.setParentAddressLine2(dataTo.getCurrentAddressLine2());
                }
                if (dataTo.getCurrentAddressZipCode() != null && !dataTo.getCurrentAddressZipCode().isEmpty()) {
                    data.setParentAddressZipCode(dataTo.getCurrentAddressZipCode());
                }
                if (dataTo.getCurrentCityName() != null && !dataTo.getCurrentCityName().isEmpty()) {
                    data.setCityByParentAddressCityId(dataTo.getCurrentCityName());
                }
                if (dataTo.getCurrentStateId() != null && !StringUtils.isEmpty(dataTo.getCurrentStateId()) && StringUtils.isNumeric(dataTo.getCurrentStateId())) {
                    if (Integer.parseInt(dataTo.getCurrentStateId()) != 0) {
                        final State currState2 = new State();
                        currState2.setId(Integer.parseInt(dataTo.getCurrentStateId()));
                        data.setStateByParentAddressStateId(currState2);
                        data.setParentAddressStateOthers((String)null);
                    }
                }
                else {
                    data.setStateByParentAddressStateId((State)null);
                    data.setParentAddressStateOthers(dataTo.getCurrentAddressStateOthers());
                }
                if (dataTo.getCurrentCountryId() > 0) {
                    final Country currCnt = new Country();
                    currCnt.setId(dataTo.getCurrentCountryId());
                    data.setCountryByParentAddressCountryId(currCnt);
                    loginForm.setParentAddrCountyId(dataTo.getCurrentCountryId());
                }
            }
            else {
                if (dataTo.getParentAddressLine1() != null && !dataTo.getParentAddressLine1().isEmpty()) {
                    data.setParentAddressLine1(dataTo.getParentAddressLine1());
                }
                if (dataTo.getParentAddressLine2() != null && !dataTo.getParentAddressLine2().isEmpty()) {
                    data.setParentAddressLine2(dataTo.getParentAddressLine2());
                }
                if (dataTo.getParentAddressLine3() != null && !dataTo.getParentAddressLine3().isEmpty()) {
                    data.setParentAddressLine3(dataTo.getParentAddressLine3());
                }
                if (dataTo.getParentAddressZipCode() != null && !dataTo.getParentAddressZipCode().isEmpty()) {
                    data.setParentAddressZipCode(dataTo.getParentAddressZipCode());
                }
                if (dataTo.getParentCountryId() != 0) {
                    final Country parentcountry = new Country();
                    parentcountry.setId(dataTo.getParentCountryId());
                    data.setCountryByParentAddressCountryId(parentcountry);
                    loginForm.setParentAddrCountyId(dataTo.getParentCountryId());
                }
                else {
                    data.setCountryByParentAddressCountryId((Country)null);
                }
                if (dataTo.getParentStateId() != null && !StringUtils.isEmpty(dataTo.getParentStateId()) && StringUtils.isNumeric(dataTo.getParentStateId())) {
                    final State parentState = new State();
                    parentState.setId(Integer.parseInt(dataTo.getParentStateId()));
                    data.setStateByParentAddressStateId(parentState);
                    data.setParentAddressStateOthers((String)null);
                }
                else {
                    data.setStateByParentAddressStateId((State)null);
                    data.setParentAddressStateOthers(dataTo.getParentAddressStateOthers());
                }
                if (dataTo.getParentCityName() != null && !dataTo.getParentCityName().isEmpty()) {
                    data.setCityByParentAddressCityId(dataTo.getParentCityName());
                }
            }
            if (dataTo.getParentPh1() != null && !dataTo.getParentPh1().isEmpty()) {
                data.setParentPh1(dataTo.getParentPh1());
            }
            if (dataTo.getParentPh2() != null && !dataTo.getParentPh2().isEmpty()) {
                data.setParentPh2(dataTo.getParentPh2());
            }
            if (dataTo.getParentPh3() != null && !dataTo.getParentPh3().isEmpty()) {
                data.setParentPh3(dataTo.getParentPh3());
            }
            if (dataTo.getParentMob1() != null && !dataTo.getParentMob1().isEmpty()) {
                data.setParentMob1(dataTo.getParentMob1());
            }
            if (dataTo.getParentMob2() != null && !dataTo.getParentMob2().isEmpty()) {
                data.setParentMob2(dataTo.getParentMob2());
            }
            if (dataTo.getParentMob3() != null && !dataTo.getParentMob3().isEmpty()) {
                data.setParentMob3(dataTo.getParentMob3());
            }
            if (dataTo.getGuardianAddressLine1() != null && !dataTo.getGuardianAddressLine1().isEmpty()) {
                data.setGuardianAddressLine1(dataTo.getGuardianAddressLine1());
            }
            if (dataTo.getGuardianAddressLine2() != null && !dataTo.getGuardianAddressLine2().isEmpty()) {
                data.setGuardianAddressLine2(dataTo.getGuardianAddressLine2());
            }
            if (dataTo.getGuardianAddressLine3() != null && !dataTo.getGuardianAddressLine3().isEmpty()) {
                data.setGuardianAddressLine3(dataTo.getGuardianAddressLine3());
            }
            if (dataTo.getGuardianAddressZipCode() != null && !dataTo.getGuardianAddressZipCode().isEmpty()) {
                data.setGuardianAddressZipCode(dataTo.getGuardianAddressZipCode());
            }
            if (dataTo.getCountryByGuardianAddressCountryId() != 0) {
                final Country parentcountry = new Country();
                parentcountry.setId(dataTo.getCountryByGuardianAddressCountryId());
                data.setCountryByGuardianAddressCountryId(parentcountry);
                loginForm.setGuardianAddrCountyId(dataTo.getCountryByGuardianAddressCountryId());
            }
            else {
                data.setCountryByGuardianAddressCountryId((Country)null);
            }
            if (dataTo.getStateByGuardianAddressStateId() != null && !StringUtils.isEmpty(dataTo.getStateByGuardianAddressStateId()) && StringUtils.isNumeric(dataTo.getStateByGuardianAddressStateId())) {
                final State parentState = new State();
                parentState.setId(Integer.parseInt(dataTo.getStateByGuardianAddressStateId()));
                data.setStateByGuardianAddressStateId(parentState);
            }
            else {
                data.setStateByGuardianAddressStateId((State)null);
                data.setGuardianAddressStateOthers(dataTo.getGuardianAddressStateOthers());
            }
            if (dataTo.getCityByGuardianAddressCityId() != null && !dataTo.getCityByGuardianAddressCityId().isEmpty()) {
                data.setCityByGuardianAddressCityId(dataTo.getCityByGuardianAddressCityId());
            }
            if (dataTo.getGuardianPh1() != null && !dataTo.getGuardianPh1().isEmpty()) {
                data.setGuardianPh1(dataTo.getGuardianPh1());
            }
            if (dataTo.getGuardianPh2() != null && !dataTo.getGuardianPh2().isEmpty()) {
                data.setGuardianPh2(dataTo.getGuardianPh2());
            }
            if (dataTo.getGuardianPh3() != null && !dataTo.getGuardianPh3().isEmpty()) {
                data.setGuardianPh3(dataTo.getGuardianPh3());
            }
            if (dataTo.getGuardianMob1() != null && !dataTo.getGuardianMob1().isEmpty()) {
                data.setGuardianMob1(dataTo.getGuardianMob1());
            }
            if (dataTo.getGuardianMob2() != null && !dataTo.getGuardianMob2().isEmpty()) {
                data.setGuardianMob2(dataTo.getGuardianMob2());
            }
            if (dataTo.getGuardianMob3() != null && !dataTo.getGuardianMob3().isEmpty()) {
                data.setGuardianMob3(dataTo.getGuardianMob3());
            }
            if (dataTo.getBrotherName() != null && !dataTo.getBrotherName().isEmpty()) {
                data.setBrotherName(dataTo.getBrotherName());
            }
            if (dataTo.getBrotherEducation() != null && !dataTo.getBrotherEducation().isEmpty()) {
                data.setBrotherEducation(dataTo.getBrotherEducation());
            }
            if (dataTo.getBrotherOccupation() != null && !dataTo.getBrotherOccupation().isEmpty()) {
                data.setBrotherOccupation(dataTo.getBrotherOccupation());
            }
            if (dataTo.getBrotherIncome() != null && !dataTo.getBrotherIncome().isEmpty()) {
                data.setBrotherIncome(dataTo.getBrotherIncome());
            }
            if (dataTo.getBrotherAge() != null && !dataTo.getBrotherAge().isEmpty()) {
                data.setBrotherAge(dataTo.getBrotherAge());
            }
            if (dataTo.getGuardianName() != null && !dataTo.getGuardianName().isEmpty()) {
                data.setGuardianName(dataTo.getGuardianName());
            }
            if (dataTo.getSisterName() != null && !dataTo.getSisterName().isEmpty()) {
                data.setSisterName(dataTo.getSisterName());
            }
            if (dataTo.getSisterEducation() != null && !dataTo.getSisterEducation().isEmpty()) {
                data.setSisterEducation(dataTo.getSisterEducation());
            }
            if (dataTo.getSisterOccupation() != null && !dataTo.getSisterOccupation().isEmpty()) {
                data.setSisterOccupation(dataTo.getSisterOccupation());
            }
            if (dataTo.getSisterIncome() != null && !dataTo.getSisterIncome().isEmpty()) {
                data.setSisterIncome(dataTo.getSisterIncome());
            }
            if (dataTo.getSisterAge() != null && !dataTo.getSisterAge().isEmpty()) {
                data.setSisterAge(dataTo.getSisterAge());
            }
            if (loginForm.getRecomendedBy() != null && !loginForm.getRecomendedBy().isEmpty()) {
                data.setRecommendedBy(loginForm.getRecomendedBy());
            }
            if (dataTo.getUniversityEmail() != null && !dataTo.getUniversityEmail().isEmpty()) {
                data.setUniversityEmail(dataTo.getUniversityEmail());
            }
            if (dataTo.getAadhaarCardNumber() != null && !dataTo.getAadhaarCardNumber().isEmpty()) {
                data.setAadharCardNumber(dataTo.getAadhaarCardNumber());
            }
            if (dataTo.getMotherTongueId() != null && !dataTo.getMotherTongueId().isEmpty()) {
                final MotherTongue motherTongue = new MotherTongue();
                motherTongue.setId(Integer.parseInt(dataTo.getMotherTongueId()));
                data.setMotherTongue(motherTongue);
            }
            if (dataTo.getFatherPANNumber() != null && !dataTo.getFatherPANNumber().isEmpty()) {
                data.setFatherPANNumber(dataTo.getFatherPANNumber().toUpperCase());
            }
            if (dataTo.getFatherAadhaarNumber() != null && !dataTo.getFatherAadhaarNumber().isEmpty()) {
                data.setFatherAadhaarNumber(dataTo.getFatherAadhaarNumber());
            }
            if (dataTo.getIsmgquota() != null) {
                data.setIsmgquota(dataTo.getIsmgquota());
            }
            if (dataTo.getRecommentedBy() != null && !dataTo.getRecommentedBy().isEmpty()) {
                data.setRecommentedBy(dataTo.getRecommentedBy());
            }
            if (dataTo.getRecommendDeatails() != null && !dataTo.getRecommendDeatails().isEmpty()) {
                data.setRecommendedBy(dataTo.getRecommendDeatails());
            }
            if (dataTo.getRecommentedPersonDesignation() != null && !dataTo.getRecommentedPersonDesignation().isEmpty()) {
                data.setRecommentedPersonDesignation(dataTo.getRecommentedPersonDesignation());
            }
            if (dataTo.getRecommentedPersonMobile() != null && !dataTo.getRecommentedPersonMobile().isEmpty()) {
                data.setRecommentedPersonMobile(dataTo.getRecommentedPersonMobile());
            }
            if (dataTo.getPreferenceNoCAP() != null) {
                data.setPreferenceNoCAP(dataTo.getPreferenceNoCAP());
            }
            if (dataTo.getMbaEntranceExamId() != null && !dataTo.getMbaEntranceExamId().isEmpty()) {
                final MBAEntranceExam entranceExam = new MBAEntranceExam();
                entranceExam.setId(Integer.parseInt(dataTo.getMbaEntranceExamId()));
                data.setMbaEntranceExam(entranceExam);
            }
            if (dataTo.getEntranceMarksSecured() != null && !dataTo.getEntranceMarksSecured().isEmpty()) {
                data.setEntranceMarksSecured(Double.parseDouble(dataTo.getEntranceMarksSecured()));
            }
            data.setIsPDataEdited(true);
        }
        boolean isUpdated = false;
        final ILoginTransaction loginTransaction = (ILoginTransaction)new LoginTransactionImpl();
        if (data != null && !data.toString().isEmpty()) {
            isUpdated = loginTransaction.savePersonalData(data);
        }
        return isUpdated;
    }
    
    public boolean checkPublish(final int classId, final String studentid) throws Exception {
        boolean isPublished = false;
        final ILoginTransaction loginTransaction = (ILoginTransaction)new LoginTransactionImpl();
        final PublishStudentEditClasswise classwise = loginTransaction.getClasswise(classId);
        final PublishStudentEdit edit = loginTransaction.getStudentWise(studentid);
        if ((classwise != null && !classwise.toString().isEmpty()) || (edit != null && !edit.toString().isEmpty())) {
            isPublished = true;
        }
        return isPublished;
    }
    
    public boolean uploadPhoto(final LoginForm loginForm, final Student student) throws Exception {
        final IConsolidatedSubjectStreamTransaction tx = (IConsolidatedSubjectStreamTransaction)ConsolidatedSubjectStreamTransactionImpl.getInstance();
        final ILoginTransaction transaction = (ILoginTransaction)new LoginTransactionImpl();
        ApplnDoc doc = tx.getStudentPhoto(student.getAdmAppln().getId());
        if (doc != null && !doc.toString().isEmpty()) {
            final FormFile editDoc = loginForm.getStudentPhoto();
            doc.setDocument(editDoc.getFileData());
            doc.setModifiedBy(loginForm.getUserId());
            doc.setLastModifiedDate(new Date());
            doc.setName(editDoc.getFileName());
            doc.setContentType(editDoc.getContentType());
        }
        else if (doc == null || doc.toString().isEmpty()) {
            doc = new ApplnDoc();
            final AdmAppln appln = new AdmAppln();
            final FormFile editDoc2 = loginForm.getStudentPhoto();
            doc.setDocument(editDoc2.getFileData());
            doc.setCreatedBy(loginForm.getUserId());
            doc.setCreatedDate(new Date());
            doc.setIsPhoto(Boolean.valueOf(true));
            doc.setIsMngQuotaForm(false);
            appln.setId(student.getAdmAppln().getId());
            doc.setAdmAppln(appln);
            doc.setName(editDoc2.getFileName());
            doc.setContentType(editDoc2.getContentType());
        }
        return transaction.saveStudentPhoto(doc);
    }
    
    public PersonalDataTO convertBOTONew(final PersonalData personalData, final LoginForm loginForm) throws Exception {
        PersonalDataTO personalDataTO = new PersonalDataTO();
        String name = "";
        final int year = 0;
        if (personalData != null) {
            personalDataTO = new PersonalDataTO();
            personalDataTO.setId(personalData.getId());
            personalDataTO.setCreatedBy(personalData.getCreatedBy());
            personalDataTO.setCreatedDate(personalData.getCreatedDate());
            personalDataTO.setIsmgquota(personalData.getIsmgquota());
            if (personalData.getFirstName() != null) {
                name = personalData.getFirstName();
            }
            if (personalData.getMiddleName() != null) {
                name = String.valueOf(name) + " " + personalData.getMiddleName();
            }
            if (personalData.getLastName() != null) {
                name = String.valueOf(name) + " " + personalData.getLastName();
            }
            personalDataTO.setFirstName(name);
            if (personalData.getDateOfBirth() != null) {
                personalDataTO.setDob(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(personalData.getDateOfBirth()), "dd-MMM-yyyy", "dd/MM/yyyy"));
            }
            personalDataTO.setBirthPlace(personalData.getBirthPlace());
            if (personalData.getIsHandicapped() != null) {
                personalDataTO.setHandicapped((boolean)personalData.getIsHandicapped());
            }
            if (personalData.getIsSportsPerson() != null) {
                personalDataTO.setSportsPerson((boolean)personalData.getIsSportsPerson());
            }
            if (personalData.getHandicappedDescription() != null) {
                personalDataTO.setHadnicappedDescription(personalData.getHandicappedDescription());
            }
            if (personalData.getSportsPersonDescription() != null) {
                personalDataTO.setSportsDescription(personalData.getSportsPersonDescription());
            }
            if (personalData.getSports() != null) {
                personalDataTO.setSports(personalData.getSports());
            }
            if (personalData.getArts() != null) {
                personalDataTO.setArts(personalData.getArts());
            }
            if (personalData.getSportsParticipate() != null) {
                personalDataTO.setSportsParticipate(personalData.getSportsParticipate());
            }
            if (personalData.getArtsParticipate() != null) {
                personalDataTO.setArtsParticipate(personalData.getArtsParticipate());
            }
            if (personalData.getFatherMobile() != null) {
                personalDataTO.setFatherMobile(personalData.getFatherMobile());
            }
            if (personalData.getMotherMobile() != null) {
                personalDataTO.setMotherMobile(personalData.getMotherMobile());
            }
            if (personalData.getIsNcccertificate() != null) {
                personalDataTO.setNcccertificate((boolean)personalData.getIsNcccertificate());
            }
            if (personalData.getIsNsscertificate() != null) {
                personalDataTO.setNsscertificate((boolean)personalData.getIsNsscertificate());
            }
            if (personalData.getIsExcervice() != null) {
                personalDataTO.setExservice((boolean)personalData.getIsExcervice());
            }
            if (personalData.getIsNcccertificate() != null && personalData.getIsNcccertificate()) {
                if (personalData.getNccgrade() != null) {
                    personalDataTO.setNccgrades(personalData.getNccgrade());
                }
                else {
                    personalDataTO.setNccgrades("");
                }
            }
            else {
                personalDataTO.setNccgrades("");
            }
            if (personalData.getIsNsscertificate() != null && personalData.getIsNsscertificate()) {
                if (personalData.getNssgrade() != null) {
                    personalDataTO.setNssgrades(personalData.getNssgrade());
                }
                else {
                    personalDataTO.setNssgrades("");
                }
            }
            else {
                personalDataTO.setNssgrades("");
            }
            if (personalData.getDioceseOthers() != null && !StringUtils.isEmpty(personalData.getDioceseOthers())) {
                personalDataTO.setDioceseOthers(personalData.getDioceseOthers());
            }
            if (personalData.getParishOthers() != null && !StringUtils.isEmpty(personalData.getParishOthers())) {
                personalDataTO.setParishOthers(personalData.getParishOthers());
            }
            if (personalData.getUgcourse() != null) {
                personalDataTO.setUgcourse(new StringBuilder(String.valueOf(personalData.getUgcourse().getId())).toString());
            }
            if (personalData.getPermanentAddressDistrcictOthers() != null && !personalData.getPermanentAddressDistrcictOthers().isEmpty()) {
                personalDataTO.setPermanentAddressDistrictOthers(personalData.getPermanentAddressDistrcictOthers());
                personalDataTO.setPermanentDistricId("Other");
            }
            else if (personalData.getStateByParentAddressDistrictId() != null) {
                personalDataTO.setPermanentDistricName(personalData.getStateByParentAddressDistrictId().getName());
                personalDataTO.setPermanentDistricId(new StringBuilder().append(personalData.getStateByParentAddressDistrictId().getId()).toString());
            }
            if (personalData.getCurrenttAddressDistrcictOthers() != null && !personalData.getCurrenttAddressDistrcictOthers().isEmpty()) {
                personalDataTO.setCurrentAddressDistrictOthers(personalData.getCurrenttAddressDistrcictOthers());
                personalDataTO.setCurrentDistricId("Other");
            }
            else if (personalData.getStateByCurrentAddressDistrictId() != null) {
                personalDataTO.setCurrentDistricName(personalData.getStateByCurrentAddressDistrictId().getName());
                personalDataTO.setCurrentDistricId(new StringBuilder().append(personalData.getStateByCurrentAddressDistrictId().getId()).toString());
            }
            if (personalData.getStream() != null) {
                personalDataTO.setStream(new StringBuilder(String.valueOf(personalData.getStream().getId())).toString());
            }
            if (personalData.getNoofenglishCoreCourses() != null) {
                personalDataTO.setNoofenglishCoreCourses(personalData.getNoofenglishCoreCourses());
            }
            if (personalData.getHeight() != null) {
                personalDataTO.setHeight(String.valueOf((int)personalData.getHeight()));
            }
            if (personalData.getWeight() != null) {
                personalDataTO.setWeight(String.valueOf(personalData.getWeight().doubleValue()));
            }
            if (personalData.getLanguageByLanguageRead() != null) {
                personalDataTO.setLanguageByLanguageRead(personalData.getLanguageByLanguageRead());
            }
            if (personalData.getLanguageByLanguageSpeak() != null) {
                personalDataTO.setLanguageByLanguageSpeak(personalData.getLanguageByLanguageSpeak());
            }
            if (personalData.getLanguageByLanguageWrite() != null) {
                personalDataTO.setLanguageByLanguageWrite(personalData.getLanguageByLanguageWrite());
            }
            if (personalData.getMotherTongue() != null) {
                personalDataTO.setMotherTongue(String.valueOf(personalData.getMotherTongue().getId()));
            }
            if (personalData.getTrainingDuration() != null) {
                personalDataTO.setTrainingDuration(String.valueOf(personalData.getTrainingDuration()));
            }
            personalDataTO.setTrainingInstAddress(personalData.getTrainingInstAddress());
            personalDataTO.setTrainingProgName(personalData.getTrainingProgName());
            personalDataTO.setTrainingPurpose(personalData.getTrainingPurpose());
            personalDataTO.setCourseKnownBy(personalData.getCourseKnownBy());
            personalDataTO.setCourseOptReason(personalData.getCourseOptReason());
            personalDataTO.setStrength(personalData.getStrength());
            personalDataTO.setWeakness(personalData.getWeakness());
            personalDataTO.setOtherAddnInfo(personalData.getOtherAddnInfo());
            personalDataTO.setSecondLanguage(personalData.getSecondLanguage());
            if (personalData.getStateByStateId() != null) {
                personalDataTO.setStateOfBirth(personalData.getStateByStateId().getName());
                personalDataTO.setBirthState(String.valueOf(personalData.getStateByStateId().getId()));
            }
            if (personalData.getCountryOthers() != null && !personalData.getCountryOthers().isEmpty()) {
                personalDataTO.setCountryOfBirth(personalData.getCountryOthers());
            }
            else if (personalData.getCountryByCountryId() != null) {
                personalDataTO.setCountryOfBirth(personalData.getCountryByCountryId().getName());
                personalDataTO.setBirthCountry(String.valueOf(personalData.getCountryByCountryId().getId()));
            }
            if (personalData.getNationalityOthers() != null && !personalData.getNationalityOthers().isEmpty()) {
                personalDataTO.setCitizenship(personalData.getNationalityOthers());
                personalDataTO.setNationalityOthers(personalData.getNationalityOthers());
            }
            else if (personalData.getNationality() != null) {
                personalDataTO.setCitizenship(personalData.getNationality().getName());
                personalDataTO.setNationality(String.valueOf(personalData.getNationality().getId()));
            }
            if (personalData.getResidentCategory() != null) {
                personalDataTO.setResidentCategoryName(personalData.getResidentCategory().getName());
                personalDataTO.setResidentCategory(String.valueOf(personalData.getResidentCategory().getId()));
            }
            if (personalData.getReligionOthers() != null && !personalData.getReligionOthers().isEmpty()) {
                personalDataTO.setReligionId("Other");
                personalDataTO.setReligionOthers(personalData.getReligionOthers());
                personalDataTO.setReligionName(personalData.getReligionOthers());
            }
            else if (personalData.getReligion() != null) {
                personalDataTO.setReligionName(personalData.getReligion().getName());
                personalDataTO.setReligionId(String.valueOf(personalData.getReligion().getId()));
            }
            if (personalData.getReligionSectionOthers() != null && !personalData.getReligionSectionOthers().isEmpty()) {
                personalDataTO.setSubReligionId("Other");
                personalDataTO.setReligionSectionOthers(personalData.getReligionSectionOthers());
                personalDataTO.setSubregligionName(personalData.getReligionSectionOthers());
            }
            else if (personalData.getReligionSection() != null) {
                personalDataTO.setSubregligionName(personalData.getReligionSection().getName());
                personalDataTO.setSubReligionId(String.valueOf(personalData.getReligionSection().getId()));
                if (personalData.getReligionSection().getName().equalsIgnoreCase("SEBC")) {
                    personalDataTO.setReservation("Applicable");
                }
                else {
                    personalDataTO.setReservation("Not Applicable");
                }
                if (personalData.getReligionSection().getName().equalsIgnoreCase("OEC")) {
                    personalDataTO.setReservation1("Applicable");
                }
                else {
                    personalDataTO.setReservation1("Not Applicable");
                }
            }
            if (personalData.getCasteOthers() != null && !personalData.getCasteOthers().isEmpty()) {
                personalDataTO.setCasteCategory(personalData.getCasteOthers());
                personalDataTO.setCasteOthers(personalData.getCasteOthers());
                personalDataTO.setCasteId("Other");
            }
            else if (personalData.getCaste() != null) {
                personalDataTO.setCasteCategory(personalData.getCaste().getName());
                personalDataTO.setCasteId(String.valueOf(personalData.getCaste().getId()));
            }
            if (personalData.getRuralUrban() != null) {
                personalDataTO.setRuralUrban((char)personalData.getRuralUrban());
                personalDataTO.setAreaType((char)personalData.getRuralUrban());
            }
            personalDataTO.setGender(personalData.getGender());
            personalDataTO.setBloodGroup(personalData.getBloodGroup());
            personalDataTO.setPhNo1(personalData.getPhNo1());
            personalDataTO.setPhNo2(personalData.getPhNo2());
            personalDataTO.setPhNo3(personalData.getPhNo3());
            personalDataTO.setMobileNo1(personalData.getMobileNo1());
            personalDataTO.setMobileNo2(personalData.getMobileNo2());
            personalDataTO.setMobileNo3(personalData.getMobileNo3());
            if (personalData.getPhNo1() != null && personalData.getPhNo2() != null && personalData.getPhNo3() != null) {
                personalDataTO.setLandlineNo(String.valueOf(personalData.getPhNo1()) + " " + personalData.getPhNo2() + " " + personalData.getPhNo3());
            }
            else if (personalData.getPhNo2() != null && personalData.getPhNo3() != null) {
                personalDataTO.setLandlineNo(String.valueOf(personalData.getPhNo2()) + " " + personalData.getPhNo3());
            }
            else if (personalData.getPhNo3() != null) {
                personalDataTO.setLandlineNo(personalData.getPhNo3());
            }
            personalDataTO.setMobileNo(String.valueOf(personalData.getMobileNo1()) + " " + personalData.getMobileNo2());
            personalDataTO.setEmail(personalData.getEmail());
            personalDataTO.setPassportNo(personalData.getPassportNo());
            personalDataTO.setResidentPermitNo(personalData.getResidentPermitNo());
            if (personalData.getCountryByPassportCountryId() != null) {
                personalDataTO.setPassportCountry(personalData.getCountryByPassportCountryId().getId());
                personalDataTO.setPassportIssuingCountry(personalData.getCountryByPassportCountryId().getName());
            }
            if (personalData.getPassportValidity() != null) {
                personalDataTO.setPassportValidity(CommonUtil.getStringDate(personalData.getPassportValidity()));
            }
            if (personalData.getResidentPermitDate() != null) {
                personalDataTO.setResidentPermitDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(personalData.getResidentPermitDate()), "dd-MMM-yyyy", "dd/MM/yyyy"));
            }
            personalDataTO.setPermanentAddressLine1(personalData.getPermanentAddressLine1());
            personalDataTO.setPermanentAddressLine2(personalData.getPermanentAddressLine2());
            if (personalData.getCityByPermanentAddressCityId() != null) {
                personalDataTO.setPermanentCityName(personalData.getCityByPermanentAddressCityId());
            }
            if (personalData.getPermanentAddressStateOthers() != null && !personalData.getPermanentAddressStateOthers().isEmpty()) {
                personalDataTO.setPermanentStateName(personalData.getPermanentAddressStateOthers());
                personalDataTO.setPermanentAddressStateOthers(personalData.getPermanentAddressStateOthers());
                personalDataTO.setPermanentStateId("Other");
            }
            else if (personalData.getStateByPermanentAddressStateId() != null) {
                personalDataTO.setPermanentStateName(personalData.getStateByPermanentAddressStateId().getName());
                personalDataTO.setPermanentStateId(String.valueOf(personalData.getStateByPermanentAddressStateId().getId()));
            }
            if (personalData.getPermanentAddressCountryOthers() != null && !personalData.getPermanentAddressCountryOthers().isEmpty()) {
                personalDataTO.setPermanentCountryName(personalData.getPermanentAddressCountryOthers());
            }
            if (personalData.getCountryByPermanentAddressCountryId() != null) {
                personalDataTO.setPermanentCountryName(personalData.getCountryByPermanentAddressCountryId().getName());
                personalDataTO.setPermanentCountryId(personalData.getCountryByPermanentAddressCountryId().getId());
            }
            personalDataTO.setPermanentAddressZipCode(personalData.getPermanentAddressZipCode());
            personalDataTO.setCurrentAddressLine1(personalData.getCurrentAddressLine1());
            personalDataTO.setCurrentAddressLine2(personalData.getCurrentAddressLine2());
            if (personalData.getCityByCurrentAddressCityId() != null) {
                personalDataTO.setCurrentCityName(personalData.getCityByCurrentAddressCityId());
            }
            if (personalData.getCurrentAddressStateOthers() != null && !personalData.getCurrentAddressStateOthers().isEmpty()) {
                personalDataTO.setCurrentStateName(personalData.getCurrentAddressStateOthers());
                personalDataTO.setCurrentAddressStateOthers(personalData.getCurrentAddressStateOthers());
                personalDataTO.setCurrentStateId("Other");
            }
            else if (personalData.getStateByCurrentAddressStateId() != null) {
                personalDataTO.setCurrentStateName(personalData.getStateByCurrentAddressStateId().getName());
                personalDataTO.setCurrentStateId(String.valueOf(personalData.getStateByCurrentAddressStateId().getId()));
            }
            if (personalData.getCurrentAddressCountryOthers() != null && !personalData.getCurrentAddressCountryOthers().isEmpty()) {
                personalDataTO.setCurrentCountryName(personalData.getCurrentAddressCountryOthers());
            }
            else if (personalData.getCountryByCurrentAddressCountryId() != null) {
                personalDataTO.setCurrentCountryName(personalData.getCountryByCurrentAddressCountryId().getName());
                personalDataTO.setCurrentCountryId(personalData.getCountryByCurrentAddressCountryId().getId());
            }
            personalDataTO.setCurrentAddressZipCode(personalData.getCurrentAddressZipCode());
            if (personalData.getCurrentStreet() != null && !personalData.getCurrentStreet().isEmpty()) {
                personalDataTO.setCurrentStreet(personalData.getCurrentStreet());
            }
            if (personalData.getPermanentStreet() != null && !personalData.getPermanentStreet().isEmpty()) {
                personalDataTO.setPermanentStreet(personalData.getPermanentStreet());
            }
            if (personalData.getFatherOccupationAddress() != null && !personalData.getFatherOccupationAddress().isEmpty()) {
                personalDataTO.setFatherOccupationAddress(personalData.getFatherOccupationAddress());
            }
            if (personalData.getMotherOccupationAddress() != null && !personalData.getMotherOccupationAddress().isEmpty()) {
                personalDataTO.setMotherOccupationAddress(personalData.getMotherOccupationAddress());
            }
            personalDataTO.setFatherName(personalData.getFatherName());
            personalDataTO.setFatherEducation(personalData.getFatherEducation());
            if (personalData.getIncomeByFatherIncomeId() != null && personalData.getIncomeByFatherIncomeId() != null) {
                if (personalData.getCurrencyByFatherIncomeCurrencyId() != null) {
                    personalDataTO.setFatherIncome(String.valueOf(personalData.getCurrencyByFatherIncomeCurrencyId().getCurrencyCode()) + personalData.getIncomeByFatherIncomeId().getIncomeRange());
                    personalDataTO.setFatherCurrencyId(String.valueOf(personalData.getCurrencyByFatherIncomeCurrencyId().getId()));
                    personalDataTO.setFatherCurrency(personalData.getCurrencyByFatherIncomeCurrencyId().getName());
                }
                else {
                    personalDataTO.setFatherIncome(personalData.getIncomeByFatherIncomeId().getIncomeRange());
                }
                personalDataTO.setFatherIncomeRange(personalData.getIncomeByFatherIncomeId().getIncomeRange());
                personalDataTO.setFatherIncomeId(String.valueOf(personalData.getIncomeByFatherIncomeId().getId()));
            }
            if (personalData.getOccupationByFatherOccupationId() != null) {
                personalDataTO.setFatherOccupation(personalData.getOccupationByFatherOccupationId().getName());
                personalDataTO.setFatherOccupationId(String.valueOf(personalData.getOccupationByFatherOccupationId().getId()));
            }
            else if (personalData.getOtherOccupationFather() != null) {
                personalDataTO.setFatherOccupationId("Other");
                personalDataTO.setOtherOccupationFather(personalData.getOtherOccupationFather());
            }
            personalDataTO.setFatherEmail(personalData.getFatherEmail());
            personalDataTO.setMotherName(personalData.getMotherName());
            personalDataTO.setMotherEducation(personalData.getMotherEducation());
            if (personalData.getIncomeByMotherIncomeId() != null) {
                if (personalData.getCurrencyByMotherIncomeCurrencyId() != null) {
                    personalDataTO.setMotherIncome(String.valueOf(personalData.getCurrencyByMotherIncomeCurrencyId().getCurrencyCode()) + personalData.getIncomeByMotherIncomeId().getIncomeRange());
                }
                else {
                    personalDataTO.setMotherIncome(personalData.getIncomeByMotherIncomeId().getIncomeRange());
                }
                personalDataTO.setMotherIncomeRange(personalData.getIncomeByMotherIncomeId().getIncomeRange());
                personalDataTO.setMotherIncomeId(String.valueOf(personalData.getIncomeByMotherIncomeId().getId()));
            }
            if (personalData.getIncomeByFatherIncomeId() != null && personalData.getCurrencyByFatherIncomeCurrencyId() == null) {
                personalDataTO.setFatherIncomeId(String.valueOf(personalData.getIncomeByFatherIncomeId().getId()));
            }
            if (personalData.getIncomeByMotherIncomeId() != null && personalData.getCurrencyByMotherIncomeCurrencyId() == null) {
                personalDataTO.setMotherIncomeId(String.valueOf(personalData.getIncomeByMotherIncomeId().getId()));
            }
            if (personalData.getCurrencyByMotherIncomeCurrencyId() != null) {
                personalDataTO.setMotherCurrencyId(String.valueOf(personalData.getCurrencyByMotherIncomeCurrencyId().getId()));
                personalDataTO.setMotherCurrency(personalData.getCurrencyByMotherIncomeCurrencyId().getName());
            }
            if (personalData.getCurrencyByFatherIncomeCurrencyId() != null) {
                personalDataTO.setFatherCurrencyId(String.valueOf(personalData.getCurrencyByFatherIncomeCurrencyId().getId()));
                personalDataTO.setFatherCurrency(personalData.getCurrencyByFatherIncomeCurrencyId().getName());
            }
            if (personalData.getOccupationByMotherOccupationId() != null) {
                personalDataTO.setMotherOccupation(personalData.getOccupationByMotherOccupationId().getName());
                personalDataTO.setMotherOccupationId(String.valueOf(personalData.getOccupationByMotherOccupationId().getId()));
            }
            else if (personalData.getOtherOccupationMother() != null) {
                personalDataTO.setMotherOccupationId("Other");
                personalDataTO.setOtherOccupationMother(personalData.getOtherOccupationMother());
            }
            personalDataTO.setMotherEmail(personalData.getMotherEmail());
            personalDataTO.setParentAddressLine1(personalData.getParentAddressLine1());
            personalDataTO.setParentAddressLine2(personalData.getParentAddressLine2());
            personalDataTO.setParentAddressLine3(personalData.getParentAddressLine3());
            if (personalData.getCityByParentAddressCityId() != null) {
                personalDataTO.setParentCityName(personalData.getCityByParentAddressCityId());
            }
            if (personalData.getParentAddressStateOthers() != null && !personalData.getParentAddressStateOthers().isEmpty()) {
                personalDataTO.setParentStateName(personalData.getParentAddressStateOthers());
                personalDataTO.setParentAddressStateOthers(personalData.getParentAddressStateOthers());
                personalDataTO.setParentStateId("Other");
            }
            else if (personalData.getStateByParentAddressStateId() != null) {
                personalDataTO.setParentStateName(personalData.getStateByParentAddressStateId().getName());
                personalDataTO.setParentStateId(String.valueOf(personalData.getStateByParentAddressStateId().getId()));
            }
            if (personalData.getParentAddressCountryOthers() != null && !personalData.getParentAddressCountryOthers().isEmpty()) {
                personalDataTO.setParentCountryName(personalData.getParentAddressCountryOthers());
            }
            else if (personalData.getCountryByParentAddressCountryId() != null) {
                personalDataTO.setParentCountryName(personalData.getCountryByParentAddressCountryId().getName());
                personalDataTO.setParentCountryId(personalData.getCountryByParentAddressCountryId().getId());
            }
            personalDataTO.setParentAddressZipCode(personalData.getParentAddressZipCode());
            personalDataTO.setParentPhone(String.valueOf(personalData.getParentPh1()) + " " + personalData.getParentPh2() + " " + personalData.getParentPh3());
            personalDataTO.setParentPh1(personalData.getParentPh1());
            personalDataTO.setParentPh2(personalData.getParentPh2());
            personalDataTO.setParentPh3(personalData.getParentPh3());
            personalDataTO.setParentMobile(String.valueOf(personalData.getParentMob1()) + " " + personalData.getParentMob2() + " " + personalData.getParentMob3());
            personalDataTO.setParentMob1(personalData.getParentMob1());
            personalDataTO.setParentMob2(personalData.getParentMob2());
            personalDataTO.setParentMob3(personalData.getParentMob3());
            personalDataTO.setGuardianAddressLine1(personalData.getGuardianAddressLine1());
            personalDataTO.setGuardianAddressLine2(personalData.getGuardianAddressLine2());
            personalDataTO.setGuardianAddressLine3(personalData.getGuardianAddressLine3());
            if (personalData.getCityByGuardianAddressCityId() != null) {
                personalDataTO.setCityByGuardianAddressCityId(personalData.getCityByGuardianAddressCityId());
            }
            if (personalData.getGuardianAddressStateOthers() != null && !personalData.getGuardianAddressStateOthers().isEmpty()) {
                personalDataTO.setGuardianAddressStateOthers(personalData.getGuardianAddressStateOthers());
                personalDataTO.setGuardianStateName(personalData.getGuardianAddressStateOthers());
                personalDataTO.setStateByGuardianAddressStateId("Other");
            }
            else if (personalData.getStateByGuardianAddressStateId() != null) {
                personalDataTO.setGuardianStateName(personalData.getStateByGuardianAddressStateId().getName());
                personalDataTO.setStateByGuardianAddressStateId(String.valueOf(personalData.getStateByGuardianAddressStateId().getId()));
            }
            if (personalData.getCountryByGuardianAddressCountryId() != null) {
                personalDataTO.setCountryByGuardianAddressCountryId(personalData.getCountryByGuardianAddressCountryId().getId());
                personalDataTO.setGuardianCountryName(personalData.getCountryByGuardianAddressCountryId().getName());
            }
            personalDataTO.setGuardianAddressZipCode(personalData.getGuardianAddressZipCode());
            personalDataTO.setGuardianPh1(personalData.getGuardianPh1());
            personalDataTO.setGuardianPh2(personalData.getGuardianPh2());
            personalDataTO.setGuardianPh3(personalData.getGuardianPh3());
            personalDataTO.setGuardianMob1(personalData.getGuardianMob1());
            personalDataTO.setGuardianMob2(personalData.getGuardianMob2());
            personalDataTO.setGuardianMob3(personalData.getGuardianMob3());
            personalDataTO.setBrotherName(personalData.getBrotherName());
            personalDataTO.setBrotherEducation(personalData.getBrotherEducation());
            personalDataTO.setBrotherOccupation(personalData.getBrotherOccupation());
            personalDataTO.setBrotherIncome(personalData.getBrotherIncome());
            personalDataTO.setBrotherAge(personalData.getBrotherAge());
            personalDataTO.setSisterName(personalData.getSisterName());
            personalDataTO.setGuardianName(personalData.getGuardianName());
            personalDataTO.setSisterEducation(personalData.getSisterEducation());
            personalDataTO.setSisterOccupation(personalData.getSisterOccupation());
            personalDataTO.setSisterIncome(personalData.getSisterIncome());
            personalDataTO.setSisterAge(personalData.getSisterAge());
            if (personalData.getUniversityEmail() != null && !personalData.getUniversityEmail().isEmpty()) {
                personalDataTO.setUniversityEmail(personalData.getUniversityEmail());
            }
            if (personalData.getAadharCardNumber() != null && !personalData.getAadharCardNumber().isEmpty()) {
                personalDataTO.setAadhaarCardNumber(personalData.getAadharCardNumber());
            }
            if (personalData.getMotherTongue() != null) {
                personalDataTO.setMotherTongueId(String.valueOf(personalData.getMotherTongue().getId()));
            }
            if (personalData.getFatherPANNumber() != null && !personalData.getFatherPANNumber().isEmpty()) {
                personalDataTO.setFatherPANNumber(personalData.getFatherPANNumber());
            }
            if (personalData.getFatherAadhaarNumber() != null && !personalData.getFatherAadhaarNumber().isEmpty()) {
                personalDataTO.setFatherAadhaarNumber(personalData.getFatherAadhaarNumber());
            }
            if (personalData.getIsmgquota() != null) {
                personalDataTO.setIsmgquota(personalData.getIsmgquota());
            }
            if (personalData.getRecommentedBy() != null && !personalData.getRecommentedBy().isEmpty()) {
                personalDataTO.setRecommentedBy(personalData.getRecommentedBy());
            }
            if (personalData.getRecommendedBy() != null && !personalData.getRecommendedBy().isEmpty()) {
                personalDataTO.setRecommendDeatails(personalData.getRecommendedBy());
            }
            if (personalData.getRecommentedPersonDesignation() != null && !personalData.getRecommentedPersonDesignation().isEmpty()) {
                personalDataTO.setRecommentedPersonDesignation(personalData.getRecommentedPersonDesignation());
            }
            if (personalData.getRecommentedPersonMobile() != null && !personalData.getRecommentedPersonMobile().isEmpty()) {
                personalDataTO.setRecommentedPersonMobile(personalData.getRecommentedPersonMobile());
            }
            if (personalData.getPreferenceNoCAP() != null) {
                personalDataTO.setPreferenceNoCAP(personalData.getPreferenceNoCAP());
            }
            if (personalData.getMbaEntranceExam() != null) {
                personalDataTO.setMbaEntranceExamId(String.valueOf(personalData.getMbaEntranceExam().getId()));
                personalDataTO.setEntranceMarksSecured(String.valueOf(personalData.getEntranceMarksSecured()));
            }
        }
        return personalDataTO;
    }
    
    public List<DisciplineAndAchivementTo> getdisciplineAndAchievement(final String studentid, final String type) throws Exception {
        String query = null;
        List<DisciplineAndAchivement> boList = null;
        query = "from DisciplineAndAchivement dis where dis.type='" + type + "'  and dis.active=true and dis.student.id=" + studentid;
        boList = (List<DisciplineAndAchivement>)StudentLoginTransactionImpl.getInstance().getDataForQuery(query);
        final List<DisciplineAndAchivementTo> toList = StudentLoginHelper.getInstance().convertDispBoTo(boList);
        return toList;
    }
    
    public EdnQualificationTO getEdnQualificationTO(final PersonalDataTO personalDataTO, final LoginForm loginForm, final Student student) throws Exception {
        final IStudentLoginTransaction txn = (IStudentLoginTransaction)new StudentLoginTransactionImpl();
        Map<Integer, String> stateMap = new HashMap<Integer, String>();
        final List<DocChecklist> exambos = (List<DocChecklist>)txn.getExamtypes(student.getAdmAppln().getAdmittedCourseId().getId(), (int)student.getAdmAppln().getAppliedYear());
        loginForm.setDocCheckList((List)exambos);
        final EdnQualificationTO ednQualificationList = StudentLoginHelper.getInstance().prepareEduQualificationMap(student.getAdmAppln().getPersonalData().getEdnQualifications(), loginForm);
        stateMap = (Map<Integer, String>)CommonAjaxImpl.getInstance().getStates(0);
        loginForm.setStateMap((Map)stateMap);
        return ednQualificationList;
    }
    
    public Set<EdnQualification> updatePlusTwoMarks(final Student student, final LoginForm loginForm, final PersonalData personalData) throws Exception {
        AdmAppln appBO = null;
        final boolean result = false;
        Set<EdnQualification> qualificationSet = null;
        final IStudentLoginTransaction txn = (IStudentLoginTransaction)new StudentLoginTransactionImpl();
        appBO = student.getAdmAppln();
        qualificationSet = StudentLoginHelper.getInstance().createMarksBo(appBO, loginForm);
        personalData.setEdnQualifications(appBO.getPersonalData().getEdnQualifications());
        return qualificationSet;
    }
    
    public List<ProgressCardTo> getProgressCard(final LoginForm loginForm) throws Exception {
        final IDownloadHallTicketTransaction transaction1 = (IDownloadHallTicketTransaction)DownloadHallTicketTransactionImpl.getInstance();
        final int academicYear = transaction1.getAcademicYear();
        loginForm.setAcademicYear(String.valueOf(academicYear));
        List<ProgressCardTo> progressCardList = new ArrayList<ProgressCardTo>();
        final String marksCardQuery = ProgressCardHelper.getInstance().getQueryForUGStudentMarksCard(Integer.parseInt(loginForm.getClassId()), loginForm.getStudentId(), academicYear);
        final List<Object[]> ugMarksCardData = (List<Object[]>)transaction1.getStudentHallTicket(marksCardQuery);
        if (ugMarksCardData.size() > 0) {
            progressCardList = (List<ProgressCardTo>)ProgressCardHelper.getInstance().getMarksCardForUg((List)ugMarksCardData, loginForm.getStudentId());
        }
        return progressCardList;
    }
}