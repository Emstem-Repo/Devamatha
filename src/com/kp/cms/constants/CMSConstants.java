package com.kp.cms.constants;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.utilities.CommonUtil;

public class CMSConstants extends CMSExamConstants {
	private static Log log = LogFactory.getLog(CMSConstants.class);
	public static List<Integer> INDIAN_RESIDENT_LIST = new ArrayList<Integer>();
	public static String INDIAN_RESIDENT_ID = "";
	public static List<Integer> ADMIN_LIST = new ArrayList<Integer>();
	public static int MAX_CANDIDATE_SUBJECTS = 0;
	public static int MAX_CANDIDATE_SEMESTERS = 0;
	public static int MAX_CANDIDATE_LATERALS = 0;
	public static int MAX_CANDIDATE_TRANSFERS = 0;
	public static int MAX_CANDIDATE_WORKEXP = 0;
	public static int STUDENT_ROLE_ID = 0;
	public static final String MAIL_FILE_CFG = "resources/mail.properties";
	public static String RECIEVER_PROVIDER_URL = "";
	public static String RECIEVER_INITIAL_CONTEXT_FACTORY = "";
	public static String SENDER_PROVIDER_URL = "";
	public static String SENDER_INITIAL_CONTEXT_FACTORY = "";
	public static String SENDER_QUEUE = "";
	public static String SENDER_CONNECTION_FACTORY = "";
	public static String RECIEVER_QUEUE = "";
	public static String RECIEVER_CONNECTION_FACTORY = "";
	public static String MAIL_HOST = "";
	public static String MAIL_USERID= "";
	public static String MAIL_PASSWORD = "";
	//start by giri
	public static String MAIL_USERID1= "";
	public static String MAIL_PASSWORD1 = "";
	public static String MAIL_USERID2= "";
	public static String MAIL_PASSWORD2 = "";
	public static String MAIL_USERID3= "";
	public static String MAIL_PASSWORD3 = "";
	public static String MAIL_USERID4= "";
	public static String MAIL_PASSWORD4 = "";
	//end by giri
	public static String MAIL_CARRER_USERID= "";
	public static String MAIL_CARRER_PASSWORD = "";
	public static String MAIL_PORT = "";
	public static String ONLINE_USERID = "";
	public static boolean PRINTER_ENABLED = false;
	public static boolean CASTE_ENABLED = true;

	public static final String RECEIPT_TX_TYPE = "R";
	public static final String ISSUE_TX_TYPE = "I";
	public static final String RETURN_TX_TYPE = "PR";
	public static final String SALVAGE_TX_TYPE = "SA";

	public static final String STOCKTRANSFER_ISSUE_TX_TYPE = "SI";
	public static final String STOCKTRANSFER_RECEIVE_TX_TYPE = "SR";
	// Constants for Hosteler Database
	public static final String HOSTEL_INIT_HOSTLER_DATABASE = "inithostelerdatabase";
	public static final String HOSTEL_HOSTLER_DATABASE_RESULT = "hostelerdatabaseresult";
	public static String HOSTEL_HOSTLER_DATABASE_REGNO = "";
	public static String HOSTEL_HOSTLER_DATABASE_ROOMNO = "";
	public static String HOSTEL_HOSTLER_DATABASE_STAFFID = "";
	public static String HOSTEL_HOSTLER_DATABASE_NAME = "";
	public static String HOSTEL_HOSTLER_DATABASE_ROOMTYPE = "";

	// Custom values for Search Employee
	public static String SEARCH_EMP_DEPARTMENT = "";
	public static String SEARCH_EMP_EMPID = "";
	public static String SEARCH_EMP_EMPNAME = "";

	// Constants for Inventory Counter
	public static String INVENTORY_COUNTER_TYPE = "";
	public static String PURCHASE_ORDER_COUNTER = "";
	public static String QUOTATION_COUNTER = "";
	// templates
	public static String E_ADMITCARD_TEMPLATE = "Admit Card";
	public static String E_ADMISSIONCARD_TEMPLATE = "Admission Card";
	public static String FINALMERITlIST_TEMPLATE = "Admission Mail";
	public static String INTERVIEW_SCHEDULE_TEMPLATE = "Selection Process Mail";
	public static String PASSWORD_TEMPLATE = "Password";
	public static String APPLICANT_MAIL_TEMPLATE = "Submission Mail";
	public static String TERMS_AND_CONDITION_TEMPLATE = "Terms And Condition";
	public static String GUIDELINES_TEMPLATE = "Guidelines";
	public static String APPLN_INSTR_TEMPLATE_NAME = "Application Instruction";
	public static String APPLN_PENDING_TEMPLATE_NAME = "Pending Document";
	public static String OFFICE_INSTR_TEMPLATE_NAME = "Application Print";
	public static String AMC_WARRANTY_DUE = "AMC/Warranty Due";
	public static String INTERVIEW_NOT_SELECTED = "Interview Rejected";

	public static String DEFAULT_PHOTO_PATH = "resources/def_photo.gif";
	public static String PRINT_PHOTO_PATH = "resources/photoblank.gif";
	public static String PRINT_CHALLANLINK = "<a href='#' onclick='downloadChallan()'>Print Challan</a>";
	public static int MAX_SESSION_INACTIVE_TIME = 0;// for hour 3600
	public static int MAX_SESSION_INACTIVE_TIME_FOR_EMPLOYEE = 0;// for hour 3600

	public static int KP_DEFAULT_NATIONALITY_ID = 0;
	public static int KP_DEFAULT_COUNTRY_ID = 0;
	public static int KP_DEFAULT_CURRENCY_ID = 0;
	public static int INTERVIEW_FAIL_ID = 2;

	// concession slip books
	public static String CONCESSION_TYPE = "";
	public static String INSTALLMENT_TYPE = "";
	public static String SCHOLARSHIP_TYPE = "";
	public static List<Integer> ATTENDANCE_DISP_COURSE_LIST = new ArrayList<Integer>();
	public static List<Integer> UG_ELIGIBLE_COURSE_LIST_FOR_MSC = new ArrayList<Integer>();
	public static List<Integer> UG_ELIGIBLE_COURSE_LIST_FOR_BCOMMODI = new ArrayList<Integer>();
	public static List<Integer> UG_ELIGIBLE_COURSE_LIST_FOR_BCOMMODII = new ArrayList<Integer>();
	public static List<Integer> UG_ID=new ArrayList<Integer>();
	public static List<Integer> PG_ID=new ArrayList<Integer>();	
	
	public static List<Integer> SELF_FINANCE_COURSE = new ArrayList<Integer>();
	public static String SHOW_LINK="";
	public static String birtLink="";
	public static  List<String> EXAM_ABSCENT_CODE = new ArrayList<String>();
	public static BigDecimal EXAM_DIFF_PERCENTAGE = null;
	public static String barbecueLink = "";
	public static boolean LINK_FOR_CJC=false;
	public static String STUDENT_PHOTO_FOLDER_PATH = "";
	public static String EMPLOYEE_PHOTO_FOLDER_PATH = "";
	public static String STUDENT_PHOTO_PATH = "";
	public static String COMMON_MSG_ADM_STATUS="";
	public static String smartCardPaymentMode="";
	public static boolean LINK_FOR_CHALLAN_PRINT = false;
	public static boolean LINK_FOR_CONSOLIDATE_MARKS_CARD= false;
	public static boolean SECOND_PAGE_DISPLAY=false;
	public static String ADM_STATUS_OFFLINE_APPLN="";
	public static boolean mailSend=true;
	public static String isSmsRequired="false";
	public static boolean CERTIFICATE_NEXT_SEM=false;
	public static String bankLink="";
	public static String ATTENDANCE_PERCENTAGE = "";
	public static String STUDENTMAIL_FROMNAME="";
	public static String SMS_SENDER_NUMBER="";
	public static String SMS_SENDER_NAME="";
	public static String EXCHANGE_RATE_API_KEY="";
	public static String pgiLink="";
	public static String MIN_PERCENTAGE_OF_PART_SUBJECTS = "";
	public static boolean VALUATION_SCHEDULE_REMAINDER_SMS_SENT = false;
	public static String HOSTEL_LEAVE_APPROVAL_MAIL_FROM_NAME="";
	public static String MAXIMUM_CAR_PASS_AVAILABILITY ="";
	public static String SMARTCARD_MAIL_FROM_NAME="";
	public static String ENGINEERING_PROGRAM_ID="";
	public static String GET_PC_ACCOUNT_HEAD_ID = "";
	public static String SAP_EXAM_MIN_MARKS = "";
	public static String UPLOAD_SAP_MARKS_UPLOAD_SUBJECT_ID = "";
	public static int MID_SEM_LESS_WEIGHTAGE = 0;
	public static String SCHEDULED_EMAIL_NO_OF_DAYS2 = "";
	public static String SCHEDULED_EMAIL_FOR_EMPLOYEE2 = "";
	public static String SCHEDULED_EMAIL_NO_OF_DAYS1 = "";
	public static String SCHEDULED_EMAIL_FOR_EMPLOYEE1 = "";
	public static String FINAL_YEAR_STUDENT_PHOTO_FOLDER_PATH = "";
	public static String LOGO_URL = "";
	public static String COLOUR_LOGO_URL = "";
	public static List<Integer> ACCESS_ROLE_ID=new ArrayList<Integer>();
	//raghu new admission form
	public static int MAX_CANDIDATE_CLASS12_SUBJECTS = 0;
	public static int MAX_CANDIDATE_DEGREE_CBCSS_SUBJECTS = 0;
	public static int MAX_CANDIDATE_DEGREE_PRECBCSS_SUBJECTS = 0;
	public static int MAX_CANDIDATE_PREFERENCES = 0;
	public static int CLASS10_DOCTYPEID = 0;
	public static int CLASS12_DOCTYPEID = 0;
	public static int DEGREE_DOCTYPEID = 0;
	public static String COLLEGE_CODE = "";
	public static String COLLEGE_NAME = "";
	public static String PAYUMONEY_SUCCESSURL="";
	public static String PAYUMONEY_FAILUREURL="";
	public static String FEDERAL_SUCCESSURL="";
	public static String PAYUMONEY_FAILUREURLL="";
	public static String COMPANY_URL="";
	public static String COLLEGE_URL="";
	public static String TIEUPBANK_ACNO="";
	public static String OTHERBANK_ACNO="";
	public static String CLOSE_DATE="";
	public static String IFSC_CODE="";
	public static String BANK_NAME="";
	public static String BANK_BRANCH="";
	public static String GENERAL_FEE="";
	public static String SCST_FEE="";
	public static int MAX_CANDIDATE_PREFERENCES_PG = 0;
	public static String GENERALPG_FEE="";
	public static String SCSTPG_FEE="";
	public static String COMMUNITYPG_FEE="";
	public static String COMMUNITYUG_FEE="";
	//IPADDRESS
	public static List<String> IPADDRESSLIST=new ArrayList<String>();
	public static int FACULTY_ID=0;
	public static double SC_REGULAR_SUPPLY_FEE = 0;
	
	public static List<Integer> CLASS_IDS_2014 = new ArrayList<Integer>();
	public static String VERIFICATION_URL = "";
	public static List<Integer> ACTIVITY_SUBJECT_IDS=new ArrayList<Integer>();
	
	public static double AIDED_FEE;
	public static double UN_AIDED_FEE;
	public static String RADIOS_HIDE_DATE_SPORTS;
	
	public static String STUDENT_MANAGEMENT_QUOTA_FORM_FOLDER_PATH = "";
	
	public static String PROFESSOR_AMOUNT="";
	
	public static String PROFESSOR_APPLICATION_SERVLET="";
	
	public static String PROFESSOR_ONLINE_APPLICATION_IMAGE_PATH="";
	
	public static String ONINE_APPLICATION_UG_FEE="";
	public static String ONINE_APPLICATION_PG_FEE="";
	public static List<Integer> EDIT_PROFILE_ROLE_ID_LIST = new ArrayList<Integer>();
	public static List<Integer> INTERNAL_MARKSCARD_MARKSENTER_LIST = new ArrayList<Integer>();
	public static List<Integer>  ROLE_ID_TEACHER = new ArrayList<Integer>();
	public static List<Integer> INTERNAL_EXAM_IDS=new ArrayList<Integer>();
	public static String EMPLOYEE_ACHIEVEMENT_FILE_FOLDER_PATH = "";
	public static String ATTENDANCE_TIME = "0";
	//public static List<Integer> ACADEMIC_YEAR_OLD_SEM=new ArrayList<Integer>();
	

	static {
		Properties prop = new Properties();
		try {
			InputStream in = CommonUtil.class.getClassLoader()
					.getResourceAsStream("resources/application.properties");
			prop.load(in);
		} catch (FileNotFoundException e) {
			log.error("Unable to read properties file...", e);

		} catch (IOException e) {
			log.error("Unable to read properties file...", e);

		}
		String nativeCountry = prop
				.getProperty("knowledgepro.nativeCountry.Id");
		INDIAN_RESIDENT_ID = nativeCountry;
		if (nativeCountry != null && !StringUtils.isEmpty(nativeCountry)) {
			StringTokenizer tokenizer = new StringTokenizer(nativeCountry, ",");
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				if (token != null && !StringUtils.isEmpty(token)
						&& StringUtils.isNumeric(token)) {
					int tok = Integer.parseInt(token);
					INDIAN_RESIDENT_LIST.add(Integer.valueOf(tok));
				}
			}
		}

		String adminids = prop.getProperty("knowledgepro.admin.role.id");
		if (adminids != null && !StringUtils.isEmpty(adminids)) {
			StringTokenizer tokenizer = new StringTokenizer(adminids, ",");
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				if (token != null && !StringUtils.isEmpty(token)
						&& StringUtils.isNumeric(token)) {
					int tok = Integer.parseInt(token);
					ADMIN_LIST.add(Integer.valueOf(tok));
				}
			}
		}
		birtLink=prop.getProperty("knowledgepro.birt.link");
		pgiLink=prop.getProperty("knowledgepro.pgi.link");
		smartCardPaymentMode=prop.getProperty("knowledgepro.fee.smartCardPaymentMode.Id");
		barbecueLink = prop.getProperty("knowledgepro.barbecue.link");
		String ugId = prop.getProperty("knowledgepro.birt.report.ug.program.id");
		if (ugId != null && !StringUtils.isEmpty(ugId)) {
			StringTokenizer tokenizer = new StringTokenizer(ugId, ",");
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				if (token != null && !StringUtils.isEmpty(token)
						&& StringUtils.isNumeric(token)) {
					int tok = Integer.parseInt(token);
					UG_ID.add(Integer.valueOf(tok));
				}
			}
		}
		String admStatusOfflineAppln=prop.getProperty("knowledgepro.admission.offlineAppln.admStatus");
		if(admStatusOfflineAppln!=null && !admStatusOfflineAppln.isEmpty()){
			ADM_STATUS_OFFLINE_APPLN=admStatusOfflineAppln;
		}
		String pgId = prop.getProperty("knowledgepro.birt.report.pg.program.id");
		if (pgId != null && !StringUtils.isEmpty(pgId)) {
			StringTokenizer tokenizer = new StringTokenizer(pgId, ",");
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				if (token != null && !StringUtils.isEmpty(token)
						&& StringUtils.isNumeric(token)) {
					int tok = Integer.parseInt(token);
					PG_ID.add(Integer.valueOf(tok));
				}
			}
		}
		
		String sl = prop.getProperty("knowledgepro.student.login.link.display");
		if (sl != null && !StringUtils.isEmpty(sl)) {
			SHOW_LINK =sl;
		}
		
		String usrssn = prop.getProperty("knowledgepro.usersession.activetime");
		if (usrssn != null && !StringUtils.isEmpty(usrssn)
				&& StringUtils.isNumeric(usrssn)) {
			MAX_SESSION_INACTIVE_TIME = Integer.parseInt(usrssn);

		}
		String userSessionForEmployee = prop.getProperty("knowledgepro.usersession.activetime.For.EmployeeAndAdmin");
		if (userSessionForEmployee != null && !StringUtils.isEmpty(userSessionForEmployee)
				&& StringUtils.isNumeric(userSessionForEmployee)) {
			MAX_SESSION_INACTIVE_TIME_FOR_EMPLOYEE = Integer.parseInt(userSessionForEmployee);

		}

		String defCnt = prop.getProperty("knowledgepro.defaultCountry.Id");
		if (defCnt != null && !StringUtils.isEmpty(defCnt)
				&& StringUtils.isNumeric(defCnt)) {
			KP_DEFAULT_COUNTRY_ID = Integer.parseInt(defCnt);
		}
		String defNat = prop.getProperty("knowledgepro.defaultNationality.Id");
		if (defNat != null && !StringUtils.isEmpty(defNat)
				&& StringUtils.isNumeric(defNat)) {
			KP_DEFAULT_NATIONALITY_ID = Integer.parseInt(defNat);
		}
		String defCur = prop.getProperty("knowledgepro.defaultCurrency.Id");
		if (defCur != null && !StringUtils.isEmpty(defCur)
				&& StringUtils.isNumeric(defCur)) {
			KP_DEFAULT_CURRENCY_ID = Integer.parseInt(defCur);
		}
        // added by vishnu
		String logoUrl = prop.getProperty("knowledgepro.print.logo.url").trim();
		if (logoUrl != null && !StringUtils.isEmpty(logoUrl)) {
			LOGO_URL = logoUrl;
		}
		String colorlogoUrl = prop.getProperty("knowledgepro.print.logo.color.url").trim();
		if (colorlogoUrl != null && !StringUtils.isEmpty(colorlogoUrl)) {
			COLOUR_LOGO_URL = colorlogoUrl;
		}
		// String applnInstrtemplate=prop.getProperty(
		// "KnowledgePro.template.applnInstr.name");
		// if(applnInstrtemplate!=null &&
		// !StringUtils.isEmpty(applnInstrtemplate))
		// {
		// APPLN_INSTR_TEMPLATE_NAME=applnInstrtemplate;
		// }

		String maxSubjects = prop
				.getProperty("knowledgepro.admission.maxcandidatesubjects");
		if (maxSubjects != null && !StringUtils.isEmpty(maxSubjects)
				&& StringUtils.isNumeric(maxSubjects)) {
			MAX_CANDIDATE_SUBJECTS = Integer.parseInt(maxSubjects);
		}
		String maxLaterals = prop
				.getProperty("knowledgepro.admission.maxlateralsemesters");
		if (maxLaterals != null && !StringUtils.isEmpty(maxLaterals)
				&& StringUtils.isNumeric(maxLaterals)) {
			MAX_CANDIDATE_LATERALS = Integer.parseInt(maxLaterals);
		}
		String maxtransferss = prop
				.getProperty("knowledgepro.admission.maxtransfersemesters");
		if (maxtransferss != null && !StringUtils.isEmpty(maxtransferss)
				&& StringUtils.isNumeric(maxtransferss)) {
			MAX_CANDIDATE_TRANSFERS = Integer.parseInt(maxtransferss);
		}

		String maxSemesters = prop
				.getProperty("knowledgepro.admission.maxcandidatesemesters");
		if (maxSemesters != null && !StringUtils.isEmpty(maxSemesters)
				&& StringUtils.isNumeric(maxSemesters)) {
			MAX_CANDIDATE_SEMESTERS = Integer.parseInt(maxSemesters);
		}
		String maxWorkExp = prop
				.getProperty("knowledgepro.admission.maxcandidateworkexp");
		if (maxWorkExp != null && !StringUtils.isEmpty(maxWorkExp)
				&& StringUtils.isNumeric(maxWorkExp)) {
			MAX_CANDIDATE_WORKEXP = Integer.parseInt(maxWorkExp);
		}

		String studentrole = prop
				.getProperty("knowledgepro.admin.studentrole.id");
		if (studentrole != null && !StringUtils.isEmpty(studentrole)
				&& StringUtils.isNumeric(studentrole)) {
			STUDENT_ROLE_ID = Integer.parseInt(studentrole);
		}

		String onlinerole = prop
				.getProperty("knowledgepro.admin.onlinerole.id");
		if (onlinerole != null && !StringUtils.isEmpty(onlinerole)) {
			ONLINE_USERID = onlinerole;
		}

		String printeraccess = prop.getProperty("knowledgepro.printer.enable");
		if (printeraccess != null && !StringUtils.isEmpty(printeraccess)
				&& "TRUE".equalsIgnoreCase(printeraccess)) {
			PRINTER_ENABLED = Boolean.valueOf(printeraccess);
		}

		String casteaccess = prop.getProperty("knowledgepro.caste.inclusion");
		if (casteaccess != null && !StringUtils.isEmpty(casteaccess)
				&& "FALSE".equalsIgnoreCase(casteaccess)) {
			CASTE_ENABLED = Boolean.valueOf(casteaccess);
		}
		// Below properties are used for hosteler database screen search by
		// dropdown
		String regNo = prop
				.getProperty("knowledgepro.hostel.hostelerdatabase.regno");
		if (regNo != null && !StringUtils.isEmpty(regNo)) {
			HOSTEL_HOSTLER_DATABASE_REGNO = regNo;
		}
		String roomNo = prop
				.getProperty("knowledgepro.hostel.hostelerdatabase.roomno");
		if (roomNo != null && !StringUtils.isEmpty(roomNo)) {
			HOSTEL_HOSTLER_DATABASE_ROOMNO = roomNo;
		}

		// Used for counter contants
		String stocktransfer = prop
				.getProperty("knowledgepro.counter.stockTransferCounter");
		if (stocktransfer != null && !StringUtils.isEmpty(stocktransfer)) {
			INVENTORY_COUNTER_TYPE = stocktransfer;
		}
		String purchanseourder = prop
				.getProperty("knowledgepro.counter.purchaseorderCounter");
		if (purchanseourder != null && !StringUtils.isEmpty(purchanseourder)) {
			PURCHASE_ORDER_COUNTER = purchanseourder;
		}

		String quotecnt = prop
				.getProperty("knowledgepro.counter.quotationCounter");
		if (quotecnt != null && !StringUtils.isEmpty(quotecnt)) {
			QUOTATION_COUNTER = quotecnt;
		}
		String staffId = prop
				.getProperty("knowledgepro.hostel.hostelerdatabase.staffid");
		if (staffId != null && !StringUtils.isEmpty(staffId)) {
			HOSTEL_HOSTLER_DATABASE_STAFFID = staffId;
		}
		String name = prop
				.getProperty("knowledgepro.hostel.hostelerdatabase.name");
		if (name != null && !StringUtils.isEmpty(name)) {
			HOSTEL_HOSTLER_DATABASE_NAME = name;
		}
		String roomType = prop
				.getProperty("knowledgepro.hostel.hostelerdatabase.roomtype");
		if (roomType != null && !StringUtils.isEmpty(roomType)) {
			HOSTEL_HOSTLER_DATABASE_ROOMTYPE = roomType;
		}

		// Below properties are used for Search Employee
		String deptName = prop
				.getProperty("knowledgepro.admin.department.report");
		if (deptName != null && !StringUtils.isEmpty(deptName)) {
			SEARCH_EMP_DEPARTMENT = deptName;
		}
		String empId = prop.getProperty("employee.info.reportto.empid");
		if (empId != null && !StringUtils.isEmpty(empId)) {
			SEARCH_EMP_EMPID = empId;
		}
		String empName = prop.getProperty("employee.info.reportto.empnm");
		if (empName != null && !StringUtils.isEmpty(empName)) {
			SEARCH_EMP_EMPNAME = empName;
		}
		// concession slip books
		String concType = prop
				.getProperty("knowledgepro.fee.concession.slip.book.conc.type");
		if (concType != null && !StringUtils.isEmpty(concType)) {
			CONCESSION_TYPE = concType;
		}
		String instType = prop
				.getProperty("knowledgepro.fee.concession.slip.installment.type");
		if (instType != null && !StringUtils.isEmpty(instType)) {
			INSTALLMENT_TYPE = instType;
		}

		String schType = prop
				.getProperty("knowledgepro.fee.concession.slip.scholarship.type");
		if (schType != null && !StringUtils.isEmpty(schType)) {
			SCHOLARSHIP_TYPE = schType;
		}

		Properties mailProp = new Properties();
		try {
			InputStream in = CommonUtil.class.getClassLoader()
					.getResourceAsStream(MAIL_FILE_CFG);
			mailProp.load(in);
			EXAM_ABSCENT_CODE = NewSecuredMarksEntryHandler.getInstance().getExamAbscentCode();
			EXAM_DIFF_PERCENTAGE=NewSecuredMarksEntryHandler.getInstance().getExamDiffPercentage();
		} catch (FileNotFoundException e) {
			log.error("Unable to read properties file...", e);

		} catch (IOException e) {
			log.error("Unable to read properties file...", e);

		}catch (Exception e) {
			log.error("Unable to read properties file...", e);

		}
		String reciveUrl = mailProp.getProperty("RECIEVER_PROVIDER_URL");
		if (reciveUrl != null && !StringUtils.isEmpty(reciveUrl))
			RECIEVER_PROVIDER_URL = reciveUrl;
		String recivecontxt = mailProp
				.getProperty("RECIEVER_INITIAL_CONTEXT_FACTORY");
		if (recivecontxt != null && !StringUtils.isEmpty(recivecontxt))
			RECIEVER_INITIAL_CONTEXT_FACTORY = recivecontxt;

		String sendUrl = mailProp.getProperty("SENDER_PROVIDER_URL");
		if (sendUrl != null && !StringUtils.isEmpty(sendUrl))
			SENDER_PROVIDER_URL = sendUrl;
		String sendcontxt = mailProp
				.getProperty("SENDER_INITIAL_CONTEXT_FACTORY");
		if (sendcontxt != null && !StringUtils.isEmpty(sendcontxt))
			SENDER_INITIAL_CONTEXT_FACTORY = sendcontxt;

		String recQue = mailProp.getProperty("RECIEVER_QUEUE");
		if (recQue != null && !StringUtils.isEmpty(recQue))
			RECIEVER_QUEUE = recQue;
		String recvctx = mailProp.getProperty("RECIEVER_CONNECTION_FACTORY");
		if (recvctx != null && !StringUtils.isEmpty(recvctx))
			RECIEVER_CONNECTION_FACTORY = recvctx;
		String sendQueue = mailProp.getProperty("SENDER_QUEUE");
		if (sendQueue != null && !StringUtils.isEmpty(sendQueue))
			SENDER_QUEUE = sendQueue;
		String sendctx = mailProp.getProperty("SENDER_CONNECTION_FACTORY");
		if (sendctx != null && !StringUtils.isEmpty(sendctx))
			SENDER_CONNECTION_FACTORY = sendctx;
		if (mailProp.getProperty("mailServer") != null && !StringUtils.isEmpty(mailProp.getProperty("mailServer")))
			MAIL_HOST=mailProp.getProperty("mailServer");
		if (mailProp.getProperty("UserName") != null && !StringUtils.isEmpty(mailProp.getProperty("UserName")))
			MAIL_USERID=mailProp.getProperty("UserName");
		if (mailProp.getProperty("password") != null && !StringUtils.isEmpty(mailProp.getProperty("password")))
			MAIL_PASSWORD=mailProp.getProperty("password");
		//start by giri
		if (mailProp.getProperty("UserName1") != null && !StringUtils.isEmpty(mailProp.getProperty("UserName1")))
			MAIL_USERID1=mailProp.getProperty("UserName1");
		if (mailProp.getProperty("password1") != null && !StringUtils.isEmpty(mailProp.getProperty("password1")))
			MAIL_PASSWORD1=mailProp.getProperty("password1");
		if (mailProp.getProperty("UserName2") != null && !StringUtils.isEmpty(mailProp.getProperty("UserName2")))
			MAIL_USERID2=mailProp.getProperty("UserName2");
		if (mailProp.getProperty("password2") != null && !StringUtils.isEmpty(mailProp.getProperty("password2")))
			MAIL_PASSWORD2=mailProp.getProperty("password2");
		if (mailProp.getProperty("UserName3") != null && !StringUtils.isEmpty(mailProp.getProperty("UserName3")))
			MAIL_USERID3=mailProp.getProperty("UserName3");
		if (mailProp.getProperty("password3") != null && !StringUtils.isEmpty(mailProp.getProperty("password3")))
			MAIL_PASSWORD3=mailProp.getProperty("password3");
		if (mailProp.getProperty("UserName4") != null && !StringUtils.isEmpty(mailProp.getProperty("UserName4")))
			MAIL_USERID4=mailProp.getProperty("UserName4");
		if (mailProp.getProperty("password4") != null && !StringUtils.isEmpty(mailProp.getProperty("password4")))
			MAIL_PASSWORD4=mailProp.getProperty("password4");
		//end by giri
		if (mailProp.getProperty("CareerUserName") != null && !StringUtils.isEmpty(mailProp.getProperty("CareerUserName")))
			MAIL_CARRER_USERID=mailProp.getProperty("CareerUserName");
		if (mailProp.getProperty("CareerPassword") != null && !StringUtils.isEmpty(mailProp.getProperty("CareerPassword")))
			MAIL_CARRER_PASSWORD=mailProp.getProperty("CareerPassword");
		if (mailProp.getProperty("port") != null && !StringUtils.isEmpty(mailProp.getProperty("port")))
			MAIL_PORT=mailProp.getProperty("port");
		
		String attDispCourse = prop.getProperty("knowledgepro.course.id.for.attendance.disp");
		if (attDispCourse != null && !StringUtils.isEmpty(attDispCourse)) {
			StringTokenizer tokenizer = new StringTokenizer(attDispCourse, ",");
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				if (token != null && !StringUtils.isEmpty(token)
						&& StringUtils.isNumeric(token)) {
					int tok = Integer.parseInt(token);
					ATTENDANCE_DISP_COURSE_LIST.add(Integer.valueOf(tok));
				}
			}
		}
		
		String ugCourse = prop.getProperty("knowledgepro.ugcourse.id.for.admission.eligibility");
		if (ugCourse != null && !StringUtils.isEmpty(ugCourse)) {
			StringTokenizer tokenizer = new StringTokenizer(ugCourse, ",");
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				if (token != null && !StringUtils.isEmpty(token)
						&& StringUtils.isNumeric(token)) {
					int tok = Integer.parseInt(token);
					UG_ELIGIBLE_COURSE_LIST_FOR_MSC.add(Integer.valueOf(tok));
				}
			}
		}
		String ugCourseBCOMI = prop.getProperty("knowledgepro.ugcourseBCOMI.id.for.admission.eligibility");
		if (ugCourseBCOMI != null && !StringUtils.isEmpty(ugCourseBCOMI)) {
			StringTokenizer tokenizer = new StringTokenizer(ugCourseBCOMI, ",");
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				if (token != null && !StringUtils.isEmpty(token)
						&& StringUtils.isNumeric(token)) {
					int tok = Integer.parseInt(token);
					UG_ELIGIBLE_COURSE_LIST_FOR_BCOMMODI.add(Integer.valueOf(tok));
				}
			}
		}
		String ugCourseBCOMII = prop.getProperty("knowledgepro.ugcourseBCOMII.id.for.admission.eligibility");
		if (ugCourseBCOMII != null && !StringUtils.isEmpty(ugCourseBCOMII)) {
			StringTokenizer tokenizer = new StringTokenizer(ugCourseBCOMII, ",");
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				if (token != null && !StringUtils.isEmpty(token)
						&& StringUtils.isNumeric(token)) {
					int tok = Integer.parseInt(token);
					UG_ELIGIBLE_COURSE_LIST_FOR_BCOMMODII.add(Integer.valueOf(tok));
				}
			}
		}
		
		
		String checkCjc = prop.getProperty("knowledgepro.birt.report.linkforcjc");
		if (checkCjc != null && !StringUtils.isEmpty(checkCjc) && checkCjc.equalsIgnoreCase("TRUE")) {
			LINK_FOR_CJC=true;
		}									
		String comMSg = prop.getProperty("knowledgepro.admission.common.msg.admissionStatus");
		if (comMSg != null && !StringUtils.isEmpty(comMSg)) {
			COMMON_MSG_ADM_STATUS=comMSg;
		}									
		String chechkPrintChallan = prop.getProperty("knowledgepro.studentLogin.print.challan.link");
		if (chechkPrintChallan != null && !StringUtils.isEmpty(chechkPrintChallan) && chechkPrintChallan.equalsIgnoreCase("TRUE")) {
			LINK_FOR_CHALLAN_PRINT=true;
		}	
		String check = prop.getProperty("knowledgepro.consolidateMarksCard.display");
		if (check != null && !StringUtils.isEmpty(check) && check.equalsIgnoreCase("TRUE")) {
			LINK_FOR_CONSOLIDATE_MARKS_CARD=true;
		}	
		String isSecondPage = prop.getProperty("knowledgepro.login.secondpage.display");
		if (isSecondPage != null && !StringUtils.isEmpty(isSecondPage) && isSecondPage.equalsIgnoreCase("true")) {
			SECOND_PAGE_DISPLAY=true;
		}		
		String isMailSend = prop.getProperty("knowledgepro.mail.send");
		if (isMailSend != null && !StringUtils.isEmpty(isMailSend) && isMailSend.equalsIgnoreCase("true")) {
			mailSend=true;
		}		

		String isSMS= prop.getProperty("knowledgepro.attendance.sms.required");
		if (isSMS != null && !StringUtils.isEmpty(isSMS) && isSMS.equalsIgnoreCase("true")) {
			isSmsRequired="true";
		}
		String isNextSem = prop.getProperty("knowledgepro.certificate.course.next.sem");
		if (isNextSem != null && !StringUtils.isEmpty(isNextSem) && isNextSem.equalsIgnoreCase("true")) {
			CERTIFICATE_NEXT_SEM=true;
		}		
		String bank = prop.getProperty("knowledgepro.bank.url");
		if (bank != null && !StringUtils.isEmpty(bank)) {
			bankLink=bank;
		}	
		// added by sudhir
		String attendancePercentage = prop.getProperty("knowledgepro.student.attendance.percentage");
		if(attendancePercentage !=null && !StringUtils.isEmpty(attendancePercentage)){
			ATTENDANCE_PERCENTAGE=attendancePercentage;
		}
		//
		//added by smitha - for mail from name -(Upload InterviewStatus And Schedule Interview).
		String studentMailFromName=prop.getProperty("knowledgepro.admission.studentmail.fromName");
		if(studentMailFromName!=null && !studentMailFromName.isEmpty()){
			STUDENTMAIL_FROMNAME=studentMailFromName;
		}
		
		//added by mary
		String time=prop.getProperty("knowledgepro.studentlogin.online.supp.exam.time");
		if(time!=null && !time.isEmpty()){
			STUDENTMAIL_FROMNAME=time;
		}
		
		String venue=prop.getProperty("knowledgepro.studentlogin.online.supp.exam.venue");
		if(venue!=null && !venue.isEmpty()){
			STUDENTMAIL_FROMNAME=time;
		}
		// added by sudhir
		String supplementryDataCreationMinPer = prop.getProperty ("knowledgepro.exam.supplementryDataCreation.min.percentage");
		if(supplementryDataCreationMinPer !=null && ! StringUtils.isEmpty(supplementryDataCreationMinPer)){ 
			MIN_PERCENTAGE_OF_PART_SUBJECTS=supplementryDataCreationMinPer;
		}
		String valuationScheduleRemainderSms = prop.getProperty("knowledgepro.valuation.schedule.remainder.sms");
		if(valuationScheduleRemainderSms!=null && !StringUtils.isEmpty(valuationScheduleRemainderSms) && valuationScheduleRemainderSms.equalsIgnoreCase("true")){
			VALUATION_SCHEDULE_REMAINDER_SMS_SENT= true;
		}
		String hostelLeaveApprovalFromName = prop.getProperty("knowledgepro.hostel.mail.fromName");
		if(hostelLeaveApprovalFromName!=null && !StringUtils.isEmpty(hostelLeaveApprovalFromName)){
			HOSTEL_LEAVE_APPROVAL_MAIL_FROM_NAME = hostelLeaveApprovalFromName;
		}
		//added by mahi
		String maxCarPassAvail=prop.getProperty("knowledgepro.maximum.car.pass.available");
		if(venue!=null && !venue.isEmpty()){
			MAXIMUM_CAR_PASS_AVAILABILITY=maxCarPassAvail;
		}
		String smartCardFromName = prop.getProperty("knowledgepro.smartcard.mail.fromName");
		if(smartCardFromName!=null && !StringUtils.isEmpty(smartCardFromName)){
			SMARTCARD_MAIL_FROM_NAME = smartCardFromName;
		}
		
		if (prop.getProperty("knowledgepro.student.image.path") != null && !StringUtils.isEmpty(prop.getProperty("knowledgepro.student.image.path")) ) {
			STUDENT_PHOTO_FOLDER_PATH=prop.getProperty("knowledgepro.student.image.path");
		}
		if(prop.getProperty("knowledgepro.employee.image.path") != null && !StringUtils.isEmpty(prop.getProperty("knowledgepro.employee.image.path")) ){
			EMPLOYEE_PHOTO_FOLDER_PATH = prop.getProperty("knowledgepro.employee.image.path");
		}
		if (prop.getProperty("knowledgepro.final.year.student.image.path") != null && !StringUtils.isEmpty(prop.getProperty("knowledgepro.final.year.student.image.path")) ) {
			FINAL_YEAR_STUDENT_PHOTO_FOLDER_PATH=prop.getProperty("knowledgepro.final.year.student.image.path");
		}
		Properties smsProp = new Properties();
    	try {
			InputStream in = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.SMS_FILE_CFG);
			smsProp.load(in);
			
		} catch (FileNotFoundException e) {
			log.error("Unable to read properties file...", e);

		} catch (IOException e) {
			log.error("Unable to read properties file...", e);

		}catch (Exception e) {
			log.error("Unable to read properties file...", e);

		}
        String smsSenderNumber=smsProp.getProperty("senderNumber");
        if(smsSenderNumber!=null && !smsSenderNumber.isEmpty()){
        	SMS_SENDER_NUMBER=smsSenderNumber;
        }
        String smsSenderName=smsProp.getProperty("senderName");
        if(smsSenderName!=null && !smsSenderName.isEmpty()){
        	SMS_SENDER_NAME=smsSenderName;
        }
        
        String exchangeRateApiKey=prop.getProperty("knowledgepro.admission.exchange.rate.api.key");
		if(exchangeRateApiKey!=null && !exchangeRateApiKey.isEmpty()){
			EXCHANGE_RATE_API_KEY=exchangeRateApiKey;
		}
		String engineeringProgramId = prop.getProperty("knowledgepro.engineering.program.id");
		if(engineeringProgramId!=null && !engineeringProgramId.isEmpty()){
			ENGINEERING_PROGRAM_ID=engineeringProgramId;
		}
		String sapPcAccountHeadId = prop.getProperty("knowledgepro.sap.pc.account.head.id");
		if(sapPcAccountHeadId!=null && !sapPcAccountHeadId.isEmpty()){
			GET_PC_ACCOUNT_HEAD_ID=sapPcAccountHeadId;
		}
		String sapExamMinMarks = prop.getProperty("knowledge.sap.exam.min.marks");
		if(sapExamMinMarks!=null && !sapExamMinMarks.isEmpty()){
			SAP_EXAM_MIN_MARKS = sapExamMinMarks;
		}
		String scheduledEmailDays2= prop.getProperty("knowledge.support.request.secheduled.email.days2");
		if(scheduledEmailDays2!=null && !scheduledEmailDays2.isEmpty()){
			SCHEDULED_EMAIL_NO_OF_DAYS2 = scheduledEmailDays2;
		}
		String scheduledEmployeeMails2 = prop.getProperty("knowledge.support.request.Escalation.email2");
		if(scheduledEmployeeMails2!=null && !scheduledEmployeeMails2.isEmpty()){
			SCHEDULED_EMAIL_FOR_EMPLOYEE2 = scheduledEmployeeMails2;
		}
		String sapSubjectId = prop.getProperty("knowledgepro.sap.marks.upload.subject.id");
		if(sapSubjectId!=null && !sapSubjectId.isEmpty()){
			UPLOAD_SAP_MARKS_UPLOAD_SUBJECT_ID = sapSubjectId;
		}
		String midSemLessWeigtage = prop.getProperty("knowledgepro.mid.sem.less.weightage");
		if(midSemLessWeigtage!=null && !midSemLessWeigtage.isEmpty()){
			MID_SEM_LESS_WEIGHTAGE=Integer.parseInt(midSemLessWeigtage);
		}
		String scheduledEmailDays1= prop.getProperty("knowledge.support.request.secheduled.email.days1");
		if(scheduledEmailDays1!=null && !scheduledEmailDays1.isEmpty()){
			SCHEDULED_EMAIL_NO_OF_DAYS1 = scheduledEmailDays1;
		}
		String scheduledEmployeeMails1 = prop.getProperty("knowledge.support.request.Escalation.email1");
		if(scheduledEmployeeMails1!=null && !scheduledEmployeeMails1.isEmpty()){
			SCHEDULED_EMAIL_FOR_EMPLOYEE1 = scheduledEmployeeMails1;
		}
		String roleId = prop.getProperty("knowledgepro.attendanceentry.access.roleId");
		if (roleId != null && !StringUtils.isEmpty(roleId)) {
			StringTokenizer tokenizer = new StringTokenizer(roleId, ",");
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				if (token != null && !StringUtils.isEmpty(token)
						&& StringUtils.isNumeric(token)) {
					int tok = Integer.parseInt(token);
					ACCESS_ROLE_ID.add(Integer.valueOf(tok));
				}
			}
		}
		
		

		//raghu
				String class12maxSubjects = prop
				.getProperty("knowledgepro.admission.class12.maxcandidatesubjects");
				if (class12maxSubjects != null && !StringUtils.isEmpty(class12maxSubjects)
				&& StringUtils.isNumeric(class12maxSubjects)) {
					MAX_CANDIDATE_CLASS12_SUBJECTS = Integer.parseInt(class12maxSubjects);
				}
				
				
				String degreecbcssmaxSubjects = prop
				.getProperty("knowledgepro.admission.degree.cbcss.maxcandidatesubjects");
				if (degreecbcssmaxSubjects != null && !StringUtils.isEmpty(degreecbcssmaxSubjects)
				&& StringUtils.isNumeric(degreecbcssmaxSubjects)) {
					MAX_CANDIDATE_DEGREE_CBCSS_SUBJECTS = Integer.parseInt(degreecbcssmaxSubjects);
				}
				
				String degreeprecbcssmaxSubjects = prop
				.getProperty("knowledgepro.admission.degree.precbcss.maxcandidatesubjects");
				if (degreeprecbcssmaxSubjects != null && !StringUtils.isEmpty(degreeprecbcssmaxSubjects)
				&& StringUtils.isNumeric(degreeprecbcssmaxSubjects)) {
					MAX_CANDIDATE_DEGREE_PRECBCSS_SUBJECTS = Integer.parseInt(degreeprecbcssmaxSubjects);
				}
				
				String maxpreferences = prop
				.getProperty("admissionFormForm.course.maxpreferences");
				if (maxpreferences != null && !StringUtils.isEmpty(maxpreferences)
				&& StringUtils.isNumeric(maxpreferences)) {
					MAX_CANDIDATE_PREFERENCES = Integer.parseInt(maxpreferences);
				}
				
				
				String class10Doctypeid = prop
				.getProperty("admissionFormForm.class10.doctypeid");
				if (class10Doctypeid != null && !StringUtils.isEmpty(class10Doctypeid)
				&& StringUtils.isNumeric(class10Doctypeid)) {
					CLASS10_DOCTYPEID = Integer.parseInt(class10Doctypeid);
				}
				
				String class12Doctypeid = prop
				.getProperty("admissionFormForm.class12.doctypeid");
				if (class12Doctypeid != null && !StringUtils.isEmpty(class12Doctypeid)
				&& StringUtils.isNumeric(class12Doctypeid)) {
					CLASS12_DOCTYPEID = Integer.parseInt(class12Doctypeid);
				}
				
				String degreeDoctypeid = prop
				.getProperty("admissionFormForm.degree.doctypeid");
				if (degreeDoctypeid != null && !StringUtils.isEmpty(degreeDoctypeid)
				&& StringUtils.isNumeric(degreeDoctypeid)) {
					DEGREE_DOCTYPEID = Integer.parseInt(degreeDoctypeid);
				}
				
				
				String collegeCode = prop
				.getProperty("admissionFormForm.college.code");
				if (collegeCode != null && !StringUtils.isEmpty(collegeCode)) {
					COLLEGE_CODE = collegeCode;
				}
				
				String collegeName = prop
				.getProperty("knowledgepro.interview.college");
				if (collegeName != null && !StringUtils.isEmpty(collegeName)) {
					COLLEGE_NAME = collegeName;
				}
				
				String surl = prop
				.getProperty("admissionFormForm.payumoney.surl");
				if (surl != null && !StringUtils.isEmpty(surl)) {
					PAYUMONEY_SUCCESSURL = surl;
				}
				
				String furl = prop
				.getProperty("admissionFormForm.payumoney.furl");
				if (furl != null && !StringUtils.isEmpty(furl)) {
					PAYUMONEY_FAILUREURL = furl;
				}
				
				String surll = prop
				.getProperty("admissionFormForm.federal.surl");
				if (surll != null && !StringUtils.isEmpty(surll)) {
					FEDERAL_SUCCESSURL = surll;
				}
				
				String furll = prop
				.getProperty("admissionFormForm.federal.furl");
				if (furll != null && !StringUtils.isEmpty(furll)) {
					PAYUMONEY_FAILUREURLL = furll;
				}
				
				String companyurl = prop
				.getProperty("knowledgepro.company.url");
				if (companyurl != null && !StringUtils.isEmpty(companyurl)) {
					COMPANY_URL = companyurl;
				}
				
				String collegeurl = prop
				.getProperty("knowledgepro.college.url");
				if (collegeurl != null && !StringUtils.isEmpty(collegeurl)) {
					COLLEGE_URL = collegeurl;
				}


				String tieupbank = prop
				.getProperty("knowledgepro.admin.tieupbank.accountno");
				if (tieupbank != null && !StringUtils.isEmpty(tieupbank)) {
					TIEUPBANK_ACNO = tieupbank;
				}
				
				String otherbank = prop
				.getProperty("knowledgepro.admin.otherbank.accountno");
				if (otherbank != null && !StringUtils.isEmpty(otherbank)) {
					OTHERBANK_ACNO = otherbank;
				}

				String closeDate = prop
				.getProperty("knowledgepro.admission.closing.date");
				if (closeDate != null && !StringUtils.isEmpty(closeDate)) {
					CLOSE_DATE = closeDate;
				}

				String ifsccode = prop
				.getProperty("knowledgepro.admin.ifsccode");
				if (ifsccode != null && !StringUtils.isEmpty(ifsccode)) {
					IFSC_CODE = ifsccode;
				}
			
				String bankname = prop
				.getProperty("knowledgepro.admin.bankname");
				if (bankname != null && !StringUtils.isEmpty(bankname)) {
					BANK_NAME = bankname;
				}
			
				String bankbranch = prop
				.getProperty("knowledgepro.admin.bankbranch");
				if (bankbranch != null && !StringUtils.isEmpty(bankbranch)) {
					BANK_BRANCH = bankbranch;
				}
			
				String generalfee = prop
				.getProperty("knowledgepro.admission.general.fee");
				if (generalfee != null && !StringUtils.isEmpty(generalfee)) {
					GENERAL_FEE = generalfee;
				}
			
				String scstfee = prop
				.getProperty("knowledgepro.admission.scst.fee");
				if (scstfee != null && !StringUtils.isEmpty(scstfee)) {
					SCST_FEE = scstfee;
				}
				
				String pgpreferences = prop
				.getProperty("admissionFormForm.course.pg.maxpreferences");
				if (pgpreferences != null && !StringUtils.isEmpty(pgpreferences)) {
					MAX_CANDIDATE_PREFERENCES_PG = Integer.parseInt(pgpreferences);
				}
				
				String generalpgfee = prop
				.getProperty("knowledgepro.admission.generalpg.fee");
				if (generalpgfee != null && !StringUtils.isEmpty(generalpgfee)) {
					GENERALPG_FEE = generalpgfee;
				}
			
				String scstpgfee = prop
				.getProperty("knowledgepro.admission.scstpg.fee");
				if (scstpgfee != null && !StringUtils.isEmpty(scstpgfee)) {
					SCSTPG_FEE = scstpgfee;
				}
				
				String ipaddress = prop.getProperty("knowledgepro.ipaddress");
				if (ipaddress != null && !StringUtils.isEmpty(ipaddress)) {
					StringTokenizer tokenizer = new StringTokenizer(ipaddress, ",");
					while (tokenizer.hasMoreTokens()) {
						String token = tokenizer.nextToken();
						if (token != null && !StringUtils.isEmpty(token)) {
							IPADDRESSLIST.add(token);
						}
					}
				}
				
				String facultyId = prop
				.getProperty("knowledgepro.attendance.faculty.id");
				if (facultyId != null && !StringUtils.isEmpty(facultyId)) {
					FACULTY_ID = Integer.parseInt(facultyId);
				}
				
				String scRegSupplFee = prop
				.getProperty("knowledge.sc.regular.fee.supplementary");
		if (scRegSupplFee != null && !StringUtils.isEmpty(scRegSupplFee)
				&& StringUtils.isNumeric(scRegSupplFee)) {
			SC_REGULAR_SUPPLY_FEE = Integer.parseInt(scRegSupplFee);
		}
		String selffinancecourse = prop.getProperty("knowledgepro.course.id.for.selffinanace");
		if (selffinancecourse != null && !StringUtils.isEmpty(selffinancecourse)) {
			StringTokenizer tokenizer = new StringTokenizer(selffinancecourse, ",");
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				if (token != null && !StringUtils.isEmpty(token)
						&& StringUtils.isNumeric(token)) {
					int tok = Integer.parseInt(token);
					SELF_FINANCE_COURSE.add(Integer.valueOf(tok));
				}
			}
		}
		
		String classIdsFor2014Batch = prop.getProperty("knowledgepro.exam.update.process.classids.2014");
		if (classIdsFor2014Batch != null && !StringUtils.isEmpty(classIdsFor2014Batch)) {
			StringTokenizer tokenizer = new StringTokenizer(classIdsFor2014Batch, ",");
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				if (token != null && !StringUtils.isEmpty(token)
						&& StringUtils.isNumeric(token)) {
					int tok = Integer.parseInt(token);
					CLASS_IDS_2014.add(tok);
				}
			}
		}
		
		String verificationURLForFedAPI = prop.getProperty("knowledgepro.admission.offlineAPIURL");
		if (scRegSupplFee != null && !StringUtils.isEmpty(scRegSupplFee)) {			
			VERIFICATION_URL = verificationURLForFedAPI;
		}
		
		String activitySubjectIds = prop.getProperty("knowledgepro.activity.subject.ids");
		if (activitySubjectIds != null && !StringUtils.isEmpty(activitySubjectIds)) {
			StringTokenizer tokenizer = new StringTokenizer(activitySubjectIds, ",");
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				if (token != null && !StringUtils.isEmpty(token)
						&& StringUtils.isNumeric(token)) {
					int tok = Integer.parseInt(token);
					ACTIVITY_SUBJECT_IDS.add(Integer.valueOf(tok));
				}
			}
		}
		
		String aidedFeeForAdmission = prop.getProperty("knowledgepro.admission.aidedFee");
		if (aidedFeeForAdmission != null && !StringUtils.isEmpty(aidedFeeForAdmission)) {			
			AIDED_FEE = Double.parseDouble(aidedFeeForAdmission);
		}
		String unAidedFeeForAdmission = prop.getProperty("knowledgepro.admission.unAidedFee");
		if (unAidedFeeForAdmission != null && !StringUtils.isEmpty(unAidedFeeForAdmission)) {			
			UN_AIDED_FEE = Double.parseDouble(unAidedFeeForAdmission);
		}
		String dateForHidingRadiosForSportsPerson = prop.getProperty("knowledgepro.admission.dateHideRadiosForSports");
		if (dateForHidingRadiosForSportsPerson != null && !StringUtils.isEmpty(dateForHidingRadiosForSportsPerson)) {			
			RADIOS_HIDE_DATE_SPORTS = dateForHidingRadiosForSportsPerson;
		}
		String studentManagementQuotaFormFolder = prop.getProperty("knowledgepro.admission.dateHideRadiosForSports");
		if (studentManagementQuotaFormFolder != null) {			
			STUDENT_MANAGEMENT_QUOTA_FORM_FOLDER_PATH = studentManagementQuotaFormFolder;
		}
		
		String professorAmount = prop.getProperty("knowledgepro.professore.fee");
		if (professorAmount != null) {			
			PROFESSOR_AMOUNT = professorAmount;
		}
		
		String professorUrlServlet = prop.getProperty("admissionFormForm.federal.surl.professore");
		if (professorUrlServlet != null) {			
			PROFESSOR_APPLICATION_SERVLET = professorUrlServlet;
		}
		
		String professorImagePath = prop.getProperty("knowledgepro.employee.online.application.image.path");
		if (professorUrlServlet != null) {			
			PROFESSOR_ONLINE_APPLICATION_IMAGE_PATH = professorImagePath;
		}
		
		String onlineApplicationUgFee = prop.getProperty("knowledgepro.admission.onlineApplicationFee.UG");
		if (onlineApplicationUgFee != null) {			
			ONINE_APPLICATION_UG_FEE = onlineApplicationUgFee;
		}
		String onlineApplicationPgFee = prop.getProperty("knowledgepro.admission.onlineApplicationFee.PG");
		if (onlineApplicationPgFee != null) {			
			ONINE_APPLICATION_PG_FEE = onlineApplicationPgFee;
		}

		String communityAmountUg = prop.getProperty("knowledgepro.admission.onlineApplicationFee.community.UG");
		if (communityAmountUg != null) {			
			COMMUNITYUG_FEE = communityAmountUg;
		}
		String communityAmountPg = prop.getProperty("knowledgepro.admission.onlineApplicationFee.community.PG");
		if (communityAmountPg != null) {			
			COMMUNITYPG_FEE = communityAmountPg;
		}
		
		String editProfileEmpIds = prop.getProperty("knowledgepro.employee.directPage.roleId");
		if (editProfileEmpIds != null && !StringUtils.isEmpty(editProfileEmpIds)) {
			StringTokenizer tokenizer = new StringTokenizer(editProfileEmpIds, ",");
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				if (token != null && !StringUtils.isEmpty(token)
						&& StringUtils.isNumeric(token)) {
					int tok = Integer.parseInt(token);
					EDIT_PROFILE_ROLE_ID_LIST.add(Integer.valueOf(tok));
				}
			}
		}
		String internalMarksEntry = prop.getProperty("knowledgePro.internal.markscard.role.ids");
		if (internalMarksEntry != null && !StringUtils.isEmpty(internalMarksEntry)) {
			StringTokenizer tokenizer = new StringTokenizer(internalMarksEntry, ",");
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				if (token != null && !StringUtils.isEmpty(token)
						&& StringUtils.isNumeric(token)) {
					int tok = Integer.parseInt(token);
					INTERNAL_MARKSCARD_MARKSENTER_LIST.add(Integer.valueOf(tok));
				}
			}
		}
		
		String teacherId = prop.getProperty("knowledgepro.markEntryForInternal.roleIdForTeacher");
		if (teacherId != null && !StringUtils.isEmpty(teacherId)) {
			StringTokenizer tokenizer = new StringTokenizer(teacherId, ",");
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				if (token != null && !StringUtils.isEmpty(token)
						&& StringUtils.isNumeric(token)) {
					int tok = Integer.parseInt(token);
					ROLE_ID_TEACHER.add(tok);
				}
			}
		}
		
		String internalExamIds=prop.getProperty("knowledgePro.internal.exam.ids");
		if(internalExamIds!=null && !StringUtils.isEmpty(internalExamIds)){
			StringTokenizer tokenizer = new StringTokenizer(internalExamIds,",");
			while(tokenizer.hasMoreElements()){
				String token=tokenizer.nextToken();
				if(token!=null && !StringUtils.isEmpty(token)
							&& StringUtils.isNumeric(token)){
					int tok=Integer.parseInt(token);
					INTERNAL_EXAM_IDS.add(Integer.valueOf(tok));
				}
			}
		}
		/*String semNos=prop.getProperty("knowledgePro.academic.year.sem.old");
		if(semNos!=null && !StringUtils.isEmpty(semNos)){
			StringTokenizer tokenizer = new StringTokenizer(semNos,",");
			while(tokenizer.hasMoreElements()){
				String token=tokenizer.nextToken();
				if(token!=null && !StringUtils.isEmpty(token)
							&& StringUtils.isNumeric(token)){
					int tok=Integer.parseInt(token);
					ACADEMIC_YEAR_OLD_SEM.add(Integer.valueOf(tok));
				}
			}
		}*/
		
		if(prop.getProperty("knowledgepro.employee.achieve.file.path") != null && !StringUtils.isEmpty(prop.getProperty("knowledgepro.employee.image.path")) ){
			EMPLOYEE_ACHIEVEMENT_FILE_FOLDER_PATH = prop.getProperty("knowledgepro.employee.achieve.file.path");
		}
		String attendanceTime = prop.getProperty("knowledgepro.attendance.time");
		if(attendanceTime!=null && !attendanceTime.isEmpty()){
			ATTENDANCE_TIME = attendanceTime;
		}
		
	}

	
	public static final String LOGIN_PAGE = "loginPage";
	public static final String LOGIN_SUCCESS = "loginSuccess";

	// ################## CMS Constants start here ##################
	// testing123
	public static final String ENCRYPTION_ALGORITHM = "MD5";

	public static final String ENCODING_TYPE = "UTF-8";
	public static final String NEW_LINE = "\n";

	public static final String JASPER_RESOURCES = "resources/application.properties";
	public static final String PAGETYPE = "pageType";
	public static final String FORMNAME = "formName";
	public static final int CHALLAN_REF_ID = 1;
	public static final String OUTSIDE_INDIA = "OUTSIDEINDIA";

	// public static final int PERMANENT_ADDR_ID = 1;
	// public static final int COMUNICATION_ADDR_ID = 2;

	// ################## CMS Constants end here ###################

	// ################## CMS Forwards start here ##################

	public static final String ERRORS = "errors";
	public static final String EDITOR_DEFAULT = "EditorDefault";
	public static final String OPERATION = "operation";
	public static final String EDIT_OPERATION = "edit";
	public static final String OPTION_MAP = "optionMap";
	public static final String FEE_OPERATION = "feeOperation";
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final String ADD = "add";
	public static final String UPDATE = "update";
	public static final String NA = "NA";

	// Forward path for caste entry action
	public static final String CASTE_ENTRY = "casteEntry";
	// Forward path for religion entry action
	public static final String RELIGION_ENTRY = "religionEntry";
	// Forward path for program type entry action
	public static final String PROGRAM_TYPE_ENTRY = "programTypeEntry";
	// Forward path for occupation entry entry action
	public static final String OCCUPATION_ENTRY = "occupationEntry";
	// Forward path for merit set entry entry action
	public static final String MERIT_SET_ENTRY = "meritSetEntry";
	// Forward path for merit set entry entry action
	public static final String LANGUAGE_ENTRY = "languageEntry";
	// Forward path for final merit list entry entry action
	public static final String FINAL_MERIT_LIST = "finalMeritListEntry";

	// Forward path for final merit list entry entry action
	public static final String INIT_MERIT_LIST_APPROVE = "initfinalMeritListApprove";

	// Forward path for final merit list entry entry action
	public static final String FINAL_MERIT_LIST_SEARCH = "finalMeritListSearchEntry";
	// Forward path for final merit list selected candidates
	public static final String FINAL_MERIT_LIST_SELECTED = "finalMeritListSelected";
	public static final String FINAL_MERIT_LIST_APPROVE = "approveMeritListSelected";
	public static final String COURSE_INTAKE_EXCEEDS_LIST = "knowledgepro.finalmeritlist.courseintake.exceeds";

	public static final String FEE_DIVISION_ENTRY = "feeDivisionEntry";
	public static final String FEE_GROUP_ENTRY = "feeGroupEntry";
	public static final String INIT_FEEBILL_ENTRY = "initFeeBillNo";

	public static final String BULK_MAIL_SEARCH = "search";
	public static final String BULK_MAIL_SEARCH_RESULTS = "searchResult";
	public static final String BULK_MAIL_SUBMITTEDSTUDENTS = "submittedstudents";
	public static final String BULK_MAIL_MAIL_DESCRIPTION = "mailDescription";

	public static final String STUDENT_SEARCH_ENTRY = "studentSearchEntry";
	public static final String STUDENT_SEARCH_RESULTS = "studentSearchResults";

	public static final String INIT_ACTIVITY_ATTENDANCE = "initAttendance";
	public static final String INIT_MODIFY_ACTIVITY_ATTENDANCE = "initModifyAttendance";
	public static final String EDIT_ACTIVITY_ATTENDANCE = "editActivityAttendance";

	public static final String PUBLISH_ADMIT_CARD = "publishAdmitCardEntry";
	public static final String CHANGE_STUDENT_PASSWORD_FAILED = "knowledgepro.student.changepassword.failed";
	// Forward path for interview result entry action
	public static final String INTERVIEW_RESULT_ENTRY = "interviewResultEntry";
	public static final String SELECT_PREFERENCE = "selectPreference";
	public static final String INTERVIEW_RESULT_SEMESTER_MARK_PAGE = "viewdetailSemesterPage";
	public static final String INTERVIEW_RESULT_DETAIL_MARK_PAGE = "viewdetailMarkPage";
	public static final String INTERVIEW_RESULT_LATERAL_DETAIL_PAGE = "viewLaterDetailPage";
	public static final String INTERVIEW_RESULT_TRANSFER_DETAIL_PAGE = "viewTransferDetailPage";
	// Forward path for interview result entry success action
	public static final String INTERVIEW_PROCESSING = "interviewProcessing";
	// Forward path for interview definition
	public static final String INTERVIEW_DEFINITION = "interviewDefinition";
	public static final String VIEW_INTERVIEW_CARD_CONTENT = "viewIntContent";

	// Forward path for interview batch update input
	public static final String INTERVIEW_BATCH_INPUT = "interviewBatchInput";
	// Forward path for interview batch update
	public static final String INTERVIEW_BATCH_UPDATE = "interviewBatchUpdate";

	// Forward path for interview not selected input
	public static final String INTERVIEW_NOTSELECTED_INPUT = "interviewNotselectedInput";
	// Forward path for interview not selected update
	public static final String INTERVIEW_NOTSELECTED_UPDATE = "interviewNotselectedUpdate";

	// Forward path for user creation
	public static final String USER_INPUT = "userInput";
	public static final String EDIT_USER_INFO = "editUser";
	public static final String USER_INFO_ADDSUCCESS = "knowledgepro.admission.userinfoadded";
	public static final String USER_INFO_USEREXIST = "knowledgepro.usermanagement.userinfo.userexist";

	// Forward path for CurriculumSchemeAction
	public static final String INIT_CURRICULUMSCHEME = "initCurriculumScheme";
	public static final String SHOW_CURRICULUMSCHEME_ON_COURSE = "showCurriculumSchemeOnCourse";
	public static final String DISPLAY_CURRICULUMSCHEME_DETAILS = "curriculumschemedetails";
	public static final String UPDATE_CURRICULUMSCHEME = "updateCurriculumScheme";

	// Forward path for currency master entry.
	public static final String Currency_Master_Entry = "currencyME";
	// Forward path for Subject Group Entry

	public static final String SUBJECT_GROUP_ENTRY = "subjectGroup";

	// Detailed subject Entry
	public static final String DETAILED_SUBJECT_ENTRY = "detailedSubjectEntry";

	// class entry page.
	public static final String CLASSES_ENTRY = "classEntry";

	public static final String[] MANDATORY_FIELDS = { "Class", "Subject",
			"Period", "Teacher", "Batch name", "Activity Type" };
	// attendance entry

	public static final String ATTENDANCE_ENTRY_FIRST = "attendanceEntryFirst";
	public static final String ATTENDANCE_ENTRY_SECOND = "attendanceEntrySecond";
	public static final String ATTENDANCE_ENTRY_THIRD = "attendanceEntryThird";
	public static final String ATTENDANCE_ENTRY_INITTHIRD = "attendanceEntryInitThird";
	public static final String ATTENDANCE_ENTRY_SECONDPAGE = "isAttendanceEntrySecondPage";
	public static final String MODIFY_ATTENDANCE = "modifyAttendanceFirst";
	public static final String MODIFY_ATTENDANCE_SECONDPAGE = "modifyAttendanceSecond";
	public static final String CANCEL_ATTENDANCE = "cancelAttendanceFirst";
	public static final String CANCEL_ATTENDANCE_SECOND = "cancelAttendanceSecond";
	// Forward path for AttendanceTypeEntry

	// 
	public static final String MODIFY_LEAVE_FIRST = "modifyLeaveFirst";
	public static final String MODIFY_LEAVE_SECOND = "modifyLeaveSecond";
	public static final String LEAVE_DELETE_SUCCESS = "knowledgepro.attendance.leaveentry.deletesuccess";
	public static final String LEAVE_DELETE_FAILURE = "knowledgepro.attendance.leaveentry.deletefailure";
	public static final String LEAVE_LOAD_FAILURE = "knowledgepro.attendance.leaveentry.loadfailure";
	public static final String ATTENDANCE_LEAVE_MODIFYSUCCESS = "knowledgepro.attendance.leavemodify.updatesuccess";
	public static final String ATTENDANCE_LEAVE_MODIFYFAILURE = "knowledgepro.attendance.leavemodify.updatefailure";

	// checklist related properties.
	public static final String KNOWLEDGEPRO_CHECKLISTENTRY_DUPLICATE = "knowledgepro.checklist.courseExist";
	public static final String KNOWLEDGEPRO_CHECKLISTENTRY_REACTIVATE = "knowledgepro.checklist.reactivate";

	public static final String INIT_ATTENDANCETYPE = "initAttendanceType";

	// Forward path for FeeHeadings

	public static final String FEE_HEADINGS_ENTRY = "feeHeadings";

	// Forward path for AdmissionStatusAction
	public static final String ADMISSION_STATUS = "getAdmissionStatus";
	public static final String ADMIT_CARD_PRINT = "admitCardPrint";
	public static final String ADMISSION_CARD_PRINT = "admissionCardPrint";

	// Forward path for Guidelines Entry Action
	public static final String INIT_GUIDELINESENTRY = "initguidelinesentry";
	public static final String GET_GUIDELINESENTRY = "getguidelinesentry";

	// Forward path for Application Report Action
	public static final String INIT_APPLICATIONREPORT = "initapplicationReport";
	public static final String VIEW_APPLICATIONREPORT = "viewapplicationReport";

	// Forward path for UploadInterviewResult

	public static final String UPLOAD_INTERVIEW_RESULT = "Upload_Interview_Result";

	// Forward path for UploadBypass Interview Result
	public static final String UPLOAD_BYPASS_INTERVIEW_RESULT = "upload_bypass_interview";
	// Attendance Reports
	public static final String INIT_ATTENDENCE_SUMMARY_REPORT = "initAttendSummaryReport";
	public static final String SUBMIT_ATTENDENCE_SUMMARY_REPORT = "submitAttendSummaryReport";

	public static final String INIT_MONTH_ATTENDENCE_SUMMARY_REPORT = "initMonthlyAttendSummaryReport";
	public static final String SUBMIT_MONTH_ATTENDENCE_SUMMARY_REPORT = "submitMonthlyAttendSummaryReport";

	public static final String ATTENDENCE_SUMMARY_REPORT = "attendanceSummaryReport";
	public static final String MONTHLY_ATTENDENCE_SUMMARY_REPORT = "monthlyattendanceSummaryReport";
	public static final String ATTENDENCE_TEACHER_REPORT = "attendanceTeacherReport";
	public static final String MONTHLY_ATTENDENCE_TEACHER_REPORT = "monthlyattendanceTeacherReport";
	public static final String INIT_ATTENDENCE_TEACHER_REPORT = "initTeacherReport";
	public static final String SUBMIT_ATTENDENCE_TEACHER_REPORT = "submitTeacherReport";
	public static final String INIT_ATTENDENCE_MONTHLY_TEACHER_REPORT = "initMonthlyTeacherReport";
	public static final String SUBMIT_ATTENDENCE_MONTHLY_TEACHER_REPORT = "submitMonthlyTeacherReport";

	public static final String INIT_FEE_REPORT = "initFeeReport";
	public static final String SUBMIT_FEE_REPORT = "submitFeeReport";
	public static final String FEE_REPORT = "studentSearch";

	public static final String SOURCE_DATE = "dd-MMM-yyyy";
	public static final String DEST_DATE = "dd/MM/yyyy";
	public static final String DEST_DATE_MM_DD_YYYY = "MM/dd/yyyy";
	public static final String KNOWLEDGEPRO_FEE_APPLNO_REPORT = "knowledgepro.applno.integer";
	public static final String KNOWLEDGEPRO_FEE_REGNO_REPORT = "knowledgepro.regno.integer";
	public static final String KNOWLEDGEPRO_FEE_APPREGNO_REPORT = "interviewProcessForm.appregno.required";
	public static final String KNOWLEDGEPRO_FEE_APPLIYEAR_REPORT = "admissionFormForm.applicationYear.required";
	public static final String KNOWLEDGEPRO_FEE_SEMESTER_REPORT = "knowledgepro.admission.semester.required";

	public static final String INIT_ABSENCE_INFORMATION_SUMMARY = "initAbsenceInformationSummary";
	public static final String INIT_MONTHLY_ABSENCE_INFORMATION_SUMMARY = "initMonthlyAbsenceInformationSummary";
	public static final String GET_ABSENCE_INFORMATION_SUMMARY = "getAbsenceInformationSummary";
	public static final String GET_MONTHLY_ABSENCE_INFORMATION_SUMMARY = "getMonthlyAbsenceInformationSummary";
	public static final String INIT_STUDENT_WISE_ATTENDANCE_SUMMARY = "initStudentWiseAttendanceSummary";

	public static final String GET_STUDENT_WISE_ATTENDANCE_SUMMARY = "getStudentWiseAttendanceSummary";

	// Forward path for Menu Screen Master
	public static final String MENU_SCREEN = "module_screen_master";
	public static final String MENU_SCREEN_ASSIGN_ROLE = "menu_screen_assign_role";
	public static final String REPORT_MENU_SCREEN_ASSIGN_ROLE = "report_menu_screen_assign_role";
	// Forward path for RecommendedBy Action
	public static final String INIT_RECOMMENDEDBY = "initrecommendedby";
	public static final String GET_RECOMMENDEDBY = "getrecommendedby";
	public static final String RESET_ALL = "resetAll";
	// Forward path for Organization Action
	public static final String INIT_ORGANIZATION = "initorganization";
	// Forward path for Privilege Action
	public static final String INIT_PRIVILEGE = "initprivilege";
	public static final String PRIVILEGE_VIEW = "viewmodulemenu";

	public static final String INIT_WEIGHTAGE_ENTRY = "WeightageEntry";
	public static final String INIT_WEIGHTAGE_DEFENITION = "WeightageDefenition";
	public static final String INIT_EDUCATION_WEIGHTAGE_DEFENITION = "EducationWeightageDefenition";
	public static final String INIT_INTERVIEW_WEIGHTAGE_DEFENITION = "InterviewWeightageDefenition";
	public static final String INIT_GENERAL_WEIGHTAGE_DEFENITION = "GeneralWeightageDefenition";
	public static final String INIT_PREREQUISITE_WEIGHTAGE_DEFENITION = "PrerequisiteWeightageDefenition";
	public static final String WEIGHTAGE_DEFINITION_UPDATE_SUCCESS = "knowledgepro.admission.weightagedefenition.updatesuccess";
	public static final String WEIGHTAGE_DEFENITION_REQUIRED = "knowledgepro.admission.weightagedefenition.required";
	public static final String INIT_BYPASS_INTERVIEW_STUDENT_SEARCH = "BypassInterviewSearch";

	public static final String INIT_ROOM_TYPE = "roomTypeSearch";

	public static final String INIT_ADMITTED_STUDENTS_REPORT = "initAdmittedStudents";
	public static final String GET_ADMITTED_STUDENTS_REPORT = "getAdmittedStudents";

	public static final String GET_BYPASS_INTERVIEW_STUDENT_SEARCH = "BypassInterviewResults";

	public static final String GET_SELECTED_BYPASS_INTERVIEW_STUDENT_SEARCH = "BypassInterviewSelected";

	public static final String ADMITTED_THROUGH_ENTRY = "admittedThrough";
	public static final String AMITTED_THROUGH_EXIST_REACTIVATE = "knowledgepro.admin.admitted.addfailure.alreadyexist.reactivate";
	public static final String ADMITTED_ACTIVATE_SUCCESS = "knowledgepro.admitted.through.activatesuccess";
	public static final String ADMITTED_ACTIVATE_FAILURE = "knowledgepro.admitted.through.activatefailure";

	public static final String REGION_CATEGORY = "regionCategory";
	public static final String REGION_EXIST_REACTIVATE = "knowledgepro.admin.region.addfailure.alreadyexist.reactivate";
	public static final String REGION_ACTIVATE_SUCCESS = "knowledgepro.region.activatesuccess";
	public static final String REGION_ACTIVATE_FAILURE = "knowledgepro.region.activatefailure";
	public static final String RREGION_EXIST = "knowledgepro.region.exist";

	public static final String PROGRAM_ENTRY = "programEntry";
	public static final String VIEW_PROGRAM = "viewProgram";
	public static final String PROGRAM_ENTRY_REACTIVATE = "knowledgepro.admin.program.addfailure.alreadyexist.reactivate";
	public static final String PROGRAM_ACTIVATE_SUCCESS = "knowledgepro.program.activatesuccess";
	public static final String PROGRAM_ACTIVATE_FAILURE = "knowledgepro.program.activatefailure";

	public static final String STATE_EXIST_REACTIVATE = "knowledgepro.admin.state.addfailure.alreadyexist.reactivate";
	public static final String STATE_ACTIVATE_SUCCESS = "knowledgepro.state.activatesuccess";
	public static final String STATE_ACTIVATE_FAILURE = "knowledgepro.state.activatefailure";
	public static final String STATE_ENTRY = "stateEntry";

	public static final String INSTITUTE_ENTRY = "instituteEntry";
	public static final String INSTITUTE_ENTRY_EXIST_REACTIVATE = "knowledgepro.admin.institute.alreadyexist.reactivate";
	public static final String INSTITUTE_ACTIVATE_SUCCESS = "knowledgepro.institute.activatesuccess";
	public static final String INSTITUTE_ACTIVATE_FAILURE = "knowledgepro.institute.activatefailure";

	public static final String ACTIVITY_ENTRY = "activityEntry";
	public static final String ACTIVITY_ENTRY_ACTIVATE_FAILURE = "knowledgepro.attn.activity.activatefailure ";
	public static final String ACTIVITY_ENTRY_ACTIVATE_SUCCESS = "knowledgepro.attn.activity.activatesuccess";
	public static final String ACTIVITY_EXIST_REACTIVATE = "knowledgepro.attn.activity.alreadyexist.reactivate";

	public static final String EXTRA_CURRICULAR_ACTIVITY_ENTRY = "extraCurricularActivityEntry";
	public static final String ACTIVITY_ENTRY_EXIST_REACTIVATE = "knowledgepro.admin.extra.cur.act.alreadyexist.reactivate";
	public static final String ACTIVITY_ENTRY_FAILURE = "knowledgepro.extra.cur.act.activatefailure";
	public static final String ACTIVITY_ACTIVATE_SUCCESS = "knowledgepro.extra.cur.act.activatesuccess";

	public static final String MODULE_ENTRY = "moduleEntry";
	public static final String MODULE_DELETE_FAILURE = "knowledgepro.usermanagement.module.deletefailure";

	public static final String MODULE_ENTRY_REACTIVATE = "knowledgepro.admin.module.addfailure.alreadyexist.reactivate";
	public static final String MODULE_ACTIVATE_SUCCESS = "knowledgepro.modules.activatesuccess";
	public static final String MODULE_ACTIVATE_FAILURE = "knowledgepro.modules.activatefailure";

	public static final String COURSE_ENTRY = "courseEntry";
	public static final String COURSE_ENTRY_EXIST_REACTIVATE = "knowledgepro.admin.course.addfailure.alreadyexist.reactivate";
	public static final String COURSE_ACTIVATE_FAILURE = "knowledgepro.course.activatefailure";
	public static final String COURSE_ACTIVATE_SUCCESS = "knowledgepro.course.activatesuccess";

	public static final String TERMS_AND_CONDITIONS_ENTRY = "gettermscondition";
	public static final String VIEW_TERMS_CONDITIONS = "viewtermscondition";

	public static final String APPLICATION_NUMBER_ENTRY = "applicationNumberEntry";

	public static final String TERMS_CONDITIONS_EXIST_REACTIVATE = "knowledgepro.admin.terms.conditions.addfailure.alreadyexist.reactivate";
	public static final String TERMS_CONDITIONS_ACTIVATE_SUCCESS = "knowledgepro.terms.conditions.activatesuccess";
	public static final String TERMS_CONDITIONS_ACTIVATE_FAILURE = "knowledgepro.terms.conditions.activatefailure";

	public static final String ATTENDANCE_CLASS_REQUIRED = "knowledgepro.attendanceentry.class.required";
	public static final String ATTENDANCE_SUBJECT_REQUIRED = "knowledgepro.attendanceentry.subject.required";
	public static final String ATTENDANCE_TEACHER_REQUIRED = "knowledgepro.attendanceentry.teacher.required";
	public static final String ATTENDANCE_PERIOD_REQUIRED = "knowledgepro.attendanceentry.period.required";
	public static final String ATTENDANCE_BATCH_REQUIRED = "knowledgepro.attendanceentry.batch.required";
	public static final String ATTENDANCE_ACTIVITY_REQUIRED = "knowledgepro.attendanceentry.activity.required";
	public static final String ATTENDANCE_HOURSHELD_NOTZERO = "knowledgepro.attendanceentry.hournotzero";
	public static final String ATTENDANCE_ATTENDANCE_DATEINVALID = "knowledgepro.attendanceentry.dateinvalid";
	public static final String ATTENDANCE_ATTENDANCE_BOTHATTENSUB_NOTALLOWED = "knowledgepro.attendanceentry.bothnotallowed";
	public static final String ATTENDANCE_ATTENDANCE_FROMDATREINVALID = "knowledgepro.attendanceentry.fromdateinvalid";
	public static final String ATTENDANCE_ATTENDANCE_TODATEINVALID = "knowledgepro.attendanceentry.todateinvalid";
	public static final String ATTENDANCE_ADDED_SUCCESSFULLY = "knowledgepro.attendanceentry.added.successfully";
	public static final String ATTENDANCE_ADDING_FAILED = "knowledgepro.attendanceentry.addfailure";
	public static final String ATTENDANCE_MODIFY_SUCCESSFULLY = "knowledgepro.attendanceentry.modify.successfully";
	public static final String ATTENDANCE_MODIFY_FAILED = "knowledgepro.attendanceentry.modifyfailure";
	public static final String ATTENDANCE_ENTRY_NOTPRESENT = "knowledgepro.attendanceentry.notpresent";
	public static final String ATTENDANCE_ENTRY_EXIST = "knowledgepro.attendanceentry.exist";
	public static final String ATTENDANCE_ENTRY_EXIST_BATCH = "knowledgepro.attendanceentry.batch.exist";
	public static final String ATTENDANCE_ENTRY_NORECORD = "knowledgepro.cancelattendance.norecord";
	public static final String ATTENDANCE_ENTRY_FAILTOLOAD = "knowledgepro.cancelattendance.failedtoload";
	public static final String ATTENDANCE_CANCELED_SUCCESSFULLY = "knowledgepro.attendance.canceled.successfully";
	public static final String ATTENDANCE_CANCEL_FAILED = "knowledgepro.attendanceentry.cancelfailed";
	public static final String ATTENDANCE_CANCEL_SELECTONE = "knowledgepro.attendanceentry.selectone";
	public static final String ATTENDANCE_ENTRY_UNABLETOPROCEED1 = "knowledgepro.attendanceentry.unabletoproceed1";
	public static final String ATTENDANCE_ENTRY_UNABLETOPROCEED2 = "knowledgepro.attendanceentry.unabletoproceed2";
	public static final String ATTENDANCE_ENTRY_UNABLETOPROCEED3 = "knowledgepro.attendanceentry.unabletoproceed3";
	
	public static final String INIT_PREFERENCES = "initPreferenceMaster";
	public static final String SHOW_PREFERENCES_ON_COURSE = "submitPreferenceMaster";

	// Template related constants.
	public static final String TEMPLATE_ADDSUCCESS = "knowledgepro.emailtemplate.add.success";
	public static final String TEMPLATE_ADDFAILED = "knowledgepro.emailtemplate.add.failure";
	public static final String TEMPLATE_UPDATESUCCESS = "knowledgepro.emailtemplate.update.success";
	public static final String TEMPLATE_UPDATEFAILED = "knowledgepro.emailtemplate.update.failure";
	public static final String TEMPLATE_CANNOTBEEMPTY = "knowledgepro.emailtemplate.cannotbeempty";
	public static final String TEMPLATE_CANNOTBE_MORETHANONE = "knowledgepro.emailtemplate.cannotmore";
	public static final String TEMPLATE_DELETESUCCESS = "knowledgepro.emailtemplate.delete.success";
	public static final String TEMPLATE_DELETEFAILED = "knowledgepro.emailtemplate.delete.failure";
	public static final String TEMPLATE_ALREADYEXIST = "knowledgepro.template.alreadyexist";
	public static final String TEMPLATE_INITTEMPLATE = "initPasswordTemplate";
	public static final String TEMPLATE_INITAPPLNTEMPLATE = "initApplnTemplate";
	public static final String TEMPLATE_INITADMITCARDTEMPLATE = "initAdmitCardTemplate";
	public static final String TEMPLATE_INITADMISSIONCARDTEMPLATE = "initAdmissionCardTemplate";
	public static final String VIEW_TEMPLATE_DESCRIPTION = "viewTemplateDescription";
	public static final String TEMPLATE_INITGROUPTEMPLATE = "initGroupTemplate";
	public static final String TEMPLATE_HELPTEMPLATE = "helpTemplate";

	public static final String GRADES_ENTRY = "gradesEntry";

	public static final String ADMISSIONFORM_STUDENTPAGE = "admissionFormStudentPage";
	public static final String ADMISSIONFORM_ONLINE_STUDENTPAGE = "onlineadmissionFormStudentPage";
	public static final String INIT_PREFERENCE_MASTER = "initPreferenceMaster";
	public static final String INIT_CANDIDATE_SEARCH = "initCandidateSearch";
	public static final String SUBMIT_CANDIDATE_SEARCH = "submitCandidateSearch";
	public static final String INIT_EXCEL = "initexcel";
	public static final String INIt_CSV = "initcsv";
	public static final String SELECTED_CANDIDATE_RESULT = "selectedCandidateResults";
	public static final String SUBMIT_PREFERENCE_MASTER = "submitPreferenceMaster";
	public static final String KNOWLEDGEPRO_HOMEPAGE = "homePage";
	public static final String KNOWLEDGEPRO_LANDINGPAGE = "loginSuccess";
	public static final String ADMISSIONFORM_INIT_EDUCATION_PAGE = "initAdmissionFormEducationPage";
	public static final String ADMISSIONFORM_EDUCATION_PAGE = "admissionFormEducationPage";
	public static final String ADMISSIONFORM_ONLINE_EDUCATION_PAGE = "onlineadmissionFormEducationPage";
	public static final String ADMISSIONFORM_PARENT_PAGE = "admissionFormParentPage";
	public static final String ADMISSIONFORM_ONLINE_PARENT_PAGE = "onlineadmissionFormParentPage";
	public static final String ADMISSIONFORM_ATTACHMENT_PAGE = "admissionFormAttachmentPage";
	public static final String ADMISSIONFORM_ONLINE_ATTACHMENT_PAGE = "onlineadmissionFormAttachmentPage";
	public static final String ADMISSIONFORM_INIT_PARENT_PAGE = "initFormParentPage";
	public static final String ADMISSIONFORM_APPLICATIONDETAIL_PAGE = "applicationDetailPage";
	public static final String ADMISSIONFORM_ONLINE_APPLICATIONDETAIL_PAGE = "OnlineapplicationDetailPage";
	public static final String ADMISSIONFORM_COURSE_SELCTION_PAGE = "offlineCourseSelctionPage";
	public static final String ADMISSIONFORM_FIRST_PAGE = "applicationSearchDetail";
	public static final String ADMISSIONFORM_INIT_STUDENT_PAGE = "initAdmissionFormStudentPage";
	public static final String ADMISSIONFORM_INIT_ATTACHMENT_PAGE = "initFormAttachmentPage";
	public static final String ADMISSIONFORM_DETAIL_MARK_PAGE = "detailMarkPage";
	public static final String ADMISSIONFORM_ONLINE_DETAIL_MARK_PAGE = "onlinedetailMarkPage";
	public static final String ADMISSIONFORM_ONLINEAPPLY_PAGE = "onlineApplyPage";
	public static final String ADMISSIONFORM_INITEDIT_PAGE = "initApplicationEditPage";
	public static final String ADMISSIONFORM_INITMODIFY_PAGE = "initApplicationModifyPage";
	public static final String ADMISSIONFORM_DETAILEDIT_PAGE = "detailApplicationEditPage";
	public static final String ADMISSIONFORM_DETAILMODIFY_PAGE = "detailApplicationModifyPage";
	public static final String ADMISSIONFORM_CONFIRMSUBMIT_PAGE = "applicationConfirmPage";
	public static final String ADMISSIONFORM_ONLINE_CONFIRMSUBMIT_PAGE = "onlineapplicationConfirmPage";
	public static final String ADMISSIONFORM_CONFIRMPRINT_PAGE = "applicationConfirmPrintPage";
	public static final String ADMISSIONFORM_ONLINE_CONFIRMPRINT_PAGE = "onlineapplicationConfirmPrintPage";
	public static final String ADMISSIONFORM_INITSTUDENTPRINT_PAGE = "initstudentApplicationPrintPage";
	public static final String ADMISSIONFORM_STUDENTPRINT_NORECORD = "studentapplicationnorecord";

	public static final String ADMISSIONFORM_PRINT_PAGE = "applicationPrintPage";
	public static final String ADMISSIONFORM_STUDENTAPPLNPRINT_PAGE = "studentapplicationPrintPage";
	public static final String ADMISSIONFORM_QUALIFY_PRINT_PAGE = "qualifyexamPrintPage";
	public static final String ADMISSIONFORM_CHALLAN_PAGE = "forwardChallanTemplate";
	public static final String ADMISSIONFORM_ONLINELINKS_PAGE = "onlinelinksPage";
	public static final String ADMISSIONFORM_INITGUIDELINE_PAGE = "initonlinelinksPage";
	public static final String ADMISSIONFORM_PREREQUISITE_PAGE = "prerequisitePage";
	public static final String ADMISSIONFORM_OFFLINE_PREREQUISITE_PAGE = "offlinePrerequisitePage";
	public static final String ADMISSIONFORM_INIT_OFFLINE_PREREQUISITE_PAGE = "offlinePrerequisitePage";
	public static final String ADMISSIONFORM_DETAIL_SEMESTER_PAGE = "detailSemesterPage";
	public static final String ADMISSIONFORM_ONLINE_DETAIL_SEMESTER_PAGE = "onlinedetailSemesterPage";
	public static final String ADMISSIONFORM_LATERAL_SEMESTER_PAGE = "lateralSemesterPage";
	public static final String ADMISSIONFORM_ONLINE_LATERAL_SEMESTER_PAGE = "onlinelateralSemesterPage";
	public static final String ADMISSIONFORM_TRANSFER_SEMESTER_PAGE = "transferSemesterPage";
	public static final String ADMISSIONFORM_ONLINE_TRANSFER_SEMESTER_PAGE = "onlinetransferSemesterPage";
	public static final String ADMISSIONFORM_LATERAL_SEMESTEREDIT_PAGE = "lateralSemesterEditPage";
	public static final String ADMISSIONFORM_TRANSFER_SEMESTEREDIT_PAGE = "transferSemesterEditPage";
	public static final String ADMISSIONFORM_LATERAL_SEMESTERVIEW_PAGE = "lateralSemesterViewPage";
	public static final String ADMISSIONFORM_TRANSFER_SEMESTERVIEW_PAGE = "transferSemesterViewPage";
	public static final String ADMISSIONFORM_TERMCONDITION_PAGE = "termConditionPage";
	public static final String ADMISSIONFORM_INIT_TERMCONDITION_PAGE = "initTermConditionPage";
	public static final String ADMISSIONFORM_INIT_PREREQUISITE_PAGE = "initPrerequisitePage";
	public static final String ADMISSIONFORM_EDIT_DETAIL_MARK_PAGE = "editdetailMarkPage";
	public static final String ADMISSIONFORM_REVIEW_SEM_MARK_PAGE = "reviewSemesterPage";
	public static final String ADMISSIONFORM_REVIEW_DET_MARK_PAGE = "reviewDetailMarkPage";
	public static final String ADMISSIONFORM_ONLINE_REVIEW_SEM_MARK_PAGE = "onlineReviewSemesterPage";
	public static final String ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE = "onlineReviewDetailMarkPage";
	public static final String ADMISSIONFORM_REVIEW_LATERAL_PAGE = "reviewLateralPage";
	public static final String ADMISSIONFORM_REVIEW_TRANSFER_PAGE = "reviewTransferPage";

	public static final String ADMISSIONFORM_ONLINE_REVIEW_LATERAL_PAGE = "onlineReviewLateralPage";
	public static final String ADMISSIONFORM_ONLINE_REVIEW_TRANSFER_PAGE = "onlineReviewTransferPage";

	public static final String ADMISSIONFORM_VIEW_DETAIL_MARK_PAGE = "viewdetailMarkPage";
	public static final String ADMISSIONFORM_EDIT_SEMESTER_MARK_PAGE = "editdetailSemesterPage";
	public static final String ADMISSIONFORM_VIEW_SEMESTER_MARK_PAGE = "viewdetailSemesterPage";
	public static final String ADMISSIONFORM_EDIT_CONFIRM_PAGE = "editconfirmPage";
	public static final String ADMISSIONFORM_MODIFY_CONFIRM_PAGE = "modifyconfirmPage";
	public static final String ADMISSIONFORM_ORIGINAL_DOCLIST_PAGE = "originaldoclistPage";
	public static final String ADMISSIONFORM_CANCELLATION = "cancel_admission";
	public static final String ADMISSIONFORM_INITAPPROVAL = "initApprovalPage";
	public static final String ADMISSIONFORM_APPROVALDETAILS = "approvalDetailsPage";
	public static final String ADMISSIONFORM_APPROVALCONFIRM = "approvalConfirmPage";
	public static final String ADMISSIONFORM_NOTELIGIBLE = "ADMISSIONFORMNOTELIGIBLE";

	public static final String STUDENTEDIT_INITEDITPAGE = "initStudentEditPage";
	public static final String STUDENTEDIT_EDITLISTPAGE = "studentEditListPage";
	public static final String STUDENTEDIT_VIEWPAGE = "studentViewPage";
	public static final String STUDENTEDIT_EDITPAGE = "studentEditPage";
	public static final String STUDENTEDIT_VIEW_DETAIL_MARK_PAGE = "viewStudentdetailMarkPage";
	public static final String STUDENTEDIT_VIEW_SEM_MARK_PAGE = "viewStudentSemMarkPage";
	public static final String STUDENTEDIT_VIEW_LATERAL_MARK_PAGE = "viewStudentLateralMarkPage";
	public static final String STUDENTEDIT_VIEW_TRANSFER_MARK_PAGE = "viewStudentTransferMarkPage";
	public static final String STUDENTEDIT_CONFIRM_PAGE = "editStudentconfirmPage";
	public static final String STUDENTEDIT_REMARK_PAGE = "studentRemarkPage";

	public static final String STUDENTEDIT_DETAIL_MARK_PAGE = "editStudentdetailMarkPage";
	public static final String STUDENTEDIT_SEM_MARK_PAGE = "editStudentSemesterMarkPage";
	public static final String STUDENTEDIT_LATERAL_MARK_PAGE = "editStudentLateralPage";
	public static final String STUDENTEDIT_TRANSFER_PAGE = "editStudentTransferPage";

    public static final String STUDENTCREATEBIODATA_INIT_PAGE = "initCreateStudentBiodataPage";
	public static final String STUDENTCREATEBIODATA_DETAIL_PAGE = "detailCreateStudentBiodataPage";
	public static final String STUDENTCREATEBIODATA_DETAIL_MARK_PAGE = "createStudentBiodataDetailMarkPage";
	public static final String STUDENTCREATEBIODATA_DETAIL_SEMESTER_PAGE = "createStudentBiodataSemesterPage";

	
	
	public static final String STUDENTCREATE_INIT_PAGE = "initCreateStudentPage";
	public static final String INIT_APPLICANT_SINGLE_PAGE = "initApplicationSinglePage";
	public static final String DETAIL_APPLICANT_SINGLE_PAGE = "detailApplicationSinglePage";
	public static final String APPLICANT_SINGLE_CONFIRM_PAGE = "confirmApplicantPage";
	public static final String STUDENTCREATE_DETAIL_PAGE = "detailCreateStudentPage";
	public static final String STUDENTCREATE_CONFIRM_PAGE = "confirmCreateStudentPage";
	public static final String STUDENTCREATE_DETAIL_SEMESTER_PAGE = "createStudentSemesterPage";
	public static final String STUDENTCREATE_DETAIL_MARK_PAGE = "createStudentDetailMarkPage";

	public static final String STUDENTCREATE_LATERAL_PAGE = "detailCreateStudentLateralPage";
	public static final String STUDENTCREATE_TRANSFER_PAGE = "detailCreateStudentTransferPage";

	public static final String INIT_MONTHLY_ATTENDANCE = "initMonthlyAttendanceEntry";
	public static final String GET_MONTHLY_ATTENDANCE = "getMonthlyAttendanceList";
	public static final String UPDATE_MONTHLY_ATTENDANCE = "updateMonthlyAttendanceList";
	public static final String CANCEL_MONTHLY_ATTENDANCE = "cancelMonthlyAttendanceList";

	public static final String INIT_ATTENDANCE_FINAL_SUMMARY = "initAttendanceFinalSummary";

	public static final String GET_ATTENDANCE_FINAL_SUMMARY = "getAttendanceFinalSummary";
	public static final String INIT_MONTHLY_ATTENDANCE_FINAL_SUMMARY = "initMonthlyAttendanceFinalSummary";
	public static final String GET_MONTHLY_ATTENDANCE_FINAL_SUMMARY = "getMonthlyAttendanceFinalSummary";

	// ################## CMS Forwards end here ###################

	// ################## CMS Form names start here ##################
	public static final String MERIT_SET_FORM = "Merit set";

	public static final String OCCUPATION_FORM = "Occupation";

	public static final String MOTHER_TONGUE_FORM = "Mother tongue";
	public static final String ACTIVITY_ATTEN = "Activity Attendance";
	public static final String CLASS_ATTEN = "Class Attendance";
	// ################## CMS Form names ends here ##################
	// ################## CustomTag Keys######
	public static final String NEWEVENTS_FUTUREYEAR_MINRANGE_KEY = "knowledgepro.newsevents.futureyear.minrange";
	public static final String NEWEVENTS_FUTUREYEAR_MAXRANGE_KEY = "knowledgepro.newsevents.futureyear.maxrange";
	public static final String RESEARCH_FUTUREYEAR_MINRANGE_KEY = "knowledgepro.research.futureyear.minrange";
	public static final String RESEARCH_FUTUREYEAR_MAXRANGE_KEY = "knowledgepro.research.futureyear.maxrange";
	public static final String YEAR_MAXRANGE_KEY = "knowledgepro.year.maxrange";
	public static final String YEAR_MINRANGE_KEY = "knowledgepro.year.minrange";
	public static final String FUTUREYEAR_MINRANGE_KEY = "knowledgepro.futureyear.minrange";
	public static final String FUTUREYEAR_MAXRANGE_KEY = "knowledgepro.futureyear.maxrange";
	public static final String SCHEME_MINRANGE_KEY = "knowledgepro.scheme.minrange";
	public static final String SCHEME_MAXRANGE_KEY = "knowledgepro.scheme.maxrange";
	public static final String MAX_UPLOAD_FILESIZE_KEY = "knowledgepro.upload.maxSize";
	public static final String MAX_UPLOAD_PHOTOSIZE_KEY = "knowledgepro.upload.maxPhotoSize";
	public static final String ORGANIZATION_MAX_UPLOAD_FILESIZE_KEY = "knowledgepro.admin.organization.upload.maxSize";

	// ################## CustomTag Keys######

	public static final int PERMANENT_ADDR_ID = 1;
	public static final int COMM_ADDR_ID = 2;

	public static String APPLICATION_DATA = "APPLICATIONDATA";
	public static String STUDENT_PERSONAL_DATA = "STUDENTPERSONALDATA";
	public static String STUDENT_PERMANENT_ADDRESS = "STUDENT_PERMANENT_ADDRESS";
	public static String STUDENT_COMM_ADDRESS = "STUDENT_COMM_ADDRESS";
	public static String STUDENT_PARENT_ADDRESS = "STUDENT_PARENT_ADDRESS";
	public static String STUDENT_PREFERENCES = "STUDENTPREFERENCES";
	public static String STUDENT_EDUCATION_DETAILS = "STUDENTEDUCATIONDETAILS";
	public static String STUDENT_DOCUMENT_DETAILS = "STUDENTDOCUMENTDETAILS";
	public static String STUDENT_PREREQUISITES = "STUDENTPREREQUISITES";
	public static String STUDENT_ENTRANCEDETAILS = "STUDENTENTRANCEDETAILS";
	public static String STUDENT_LATERALDETAILS = "STUDENTLATERALDETAILS";
	public static String STUDENT_TRANSFERDETAILS = "STUDENTTRANSFERDETAILS";

	public static String COURSE_PREFERENCES = "preferences";

	public static String ERROR_PAGE = "errorpage";
	public static String KNOWLEDGEPRO_NORECORDS = "knowledgepro.norecords";
	public static String KNOWLEDGEPRO_STARTDATE_CONNOTBELESS = "knowledgepro.startdate.connotbeless";
	public static final String SECOND_LANGUAGE = "(Secound Language)";
	// Fee Module related forward constants
	// Fee assignment
	public static final String FEE_ASSIGNMENT_ENTRY = "feeAssignmentEntry";
	public static final String FEE_ASSIGNMENT_ACCOUNT_ENTRY = "feeAssignmentAccountEntry";
	public static final String FEE_VIEW_ASSIGNMENT = "viewFeeAssignment";
	// Fee payment
	public static final String FEE_PAYMENT_SEARCH = "feePaymentSearch";
	public static final String FEE_PAYMENT_VIEW = "feePaymentView";
	public static final String FEE_PAYMENT_ENTRY = "feePaymentEntry";
	public static final String FEE_AJAX_RESPONSE_SEMESTER = "ajaxResponseSemisters";
	public static final String FEE_PAYMENT_FARWARD_SECOND_SEARCH = "feePaymentSecondSearch";
	public static final String FEE_PAYMENT_VOUCHER_VALIDATION = "knowledgepro.fee.voucherValidation";

	// Fee Module related Action errors constants
	// Fee assignment
	public static final String FEE_ADD_SUCCESS = "knowledgepro.fee.addsuccess";
	public static final String FEE_ADD_FAILURE = "knowledgepro.fee.addfailure";
	public static final String FEE_DELETE_SUCCESS = "knowledgepro.fee.deletesuccess";
	public static final String FEE_DELETE_FAILURE = "knowledgepro.fee.deletefailure";
	public static final String FEE_UPDATE_SUCCESS = "knowledgepro.fee.updatesuccess";
	public static final String FEE_UPDATE_FAILURE = "knowledgepro.fee.updatefailure";
	public static final String FEE_ADD_EXIST = "knowledgepro.fee.addfailure.alreadyexist";
	public static final String FEE_ADD_EXIST_REACTIVATE = "knowledgepro.fee.addfailure.alreadyexist.reactivate";
	public static final String FEE_ACTIVATE_SUCCESS = "knowledgepro.fee.activatesuccess";
	public static final String FEE_ACTIVATE_FAILURE = "knowledgepro.fee.activatefailure";
	public static final String FEE_LOADING_FAILURE = "knowledgepro.fee.loadingfailure";

	// Fee payment
	public static final String FEE_APPORREGI_REQUIRED = "knowledgepro.fee.apporregi.required";
	public static final String FEE_BOTH_NOTREQUIRED = "knowledgepro.fee.apporregi.both.notrequired";
	public static final String FEE_SEMESTER_REQUIRED = "knowledgepro.fee.semister.required";
	public static final String FEE_DETAIL_ERROR = "knowledgepro.fee.view.feedetails.error";
	public static final String FEE_PAYMENT_SUCCESS = "knowledgepro.fee.payment.success";
	public static final String FEE_PAYMENT_FAILURE = "knowledgepro.fee.payment.failure";
	public static final String FEE_CHALLAN_SUCCESS = "knowledgepro.fee.challon.success";
	public static final String FEE_CHALLAN_FAILURE = "knowledgepro.fee.challon.failure";
	public static final String FEE_ACADEMICYEAR_REQUIRED = "knowledgepro.fee.academicyear.required";
	public static final String FEE_APPLICATIONNO_NOTPRESENT = "knowledgepro.fee.application.notpresent";
	public static final String FEE_REGISTERNNO_NOTPRESENT = "knowledgepro.fee.register.notpresent";
	public static final String FEE_APPLICATIONNO_FREESHIP = "knowledgepro.fee.application.freeship";
	public static final String FEE_FEEDEFINITION_NOTPRESENT = "knowledgepro.fee.definition.notpresent";
	public static final String FEE_CHALLAN_PRINTED_ALREADY = "knowledgepro.challan.printed.already";
	public static final String FEE_APPLICATION_SUBGROUP_NOTPRESENT = "knowledgepro.fee.application.subjectgroup.notpresent";
	public static final String FEE_APPLICATION_CURRICULUM_NOTPRESENT = "knowledgepro.fee.application.curriculum.notpresent";
	public static final String FEE_APPLICATION_FEEGROUP_REQUIRED = "knowledgepro.feegroup.reuqired";
	public static final String FEE_PRINTCHANNEL_BILLGEN_FAILURE = "knowledgepro.printchallen.billgen.failure";
	public static final String FEE_NO_FUTURE_DATE_ = "knowledgepro.nofuture.date";

	public static final String FEE_PAYMENT_CONCESSION = "knowledgepro.fee.feePaymentConcession";
	public static final String FEE_PAYMENT_INSTALLMENT = "knowledgepro.fee.feePaymentInstallment";
	public static final String FEE_PAYMENT_SCHOLARSHIP = "knowledgepro.fee.feePaymentScholarship";

	// Payment made successfully;
	public static final String FEE_PAYS_SUCCESS = "knowledgepro.feepays.success";
	public static final String FEE_PAYS_FAILURE = "knowledgepro.feepays.failure";
	public static final String FEE_CANCELCHALLAN_SUCCESS = "knowledgepro.cancelchallan.success";
	public static final String FEE_CANCELCHALLAN_FAILURE = "knowledgepro.cancelchallan.failure";
	public static final String FEE_PAYS_INITFEEPAIDSEARCH = "initFeePaidSearch";
	public static final String FEE_PAYS_INITFEECHALLANSEARCH = "initFeeChallanSearch";
	public static final String FEE_PAYMENT_PRINTCHALLAN = "printChallen";
	public static final String FEE_PAYMENT_REPRINTCHALLAN = "initReprintChallan";
	public static final String FEE_PAYMENT_EDIT = "feePaymentEditSearch";
	public static final String FEE_PAYMENT_EDIT_DETAILS = "feePaymentEditDetails";
	public static final String FEE_PAYMENT_EDIT_SUCCESS = "knowledgepro.feepays.edit.success";
	// Fee additional
	public static final String FEE_ADDITIONAL_ADD_SUCCESS = "knowledgepro.feeadditional.addsuccess";
	public static final String FEE_ADDITIONAL_ADD_FAILURE = "knowledgepro.feeadditional.addfailure";
	public static final String FEE_ADDITIONAL_DELETE_SUCCESS = "knowledgepro.feeadditional.deletesuccess";
	public static final String FEE_ADDITIONAL_DELETE_FAILURE = "knowledgepro.feeadditional.deletefailure";
	public static final String FEE_ADDITIONAL_UPDATE_SUCCESS = "knowledgepro.feeadditional.updatesuccess";
	public static final String FEE_ADDITIONAL_UPDATE_FAILURE = "knowledgepro.feeadditional.updatefailure";
	public static final String FEE_ADDITIONAL_ACTIVATE_SUCCESS = "knowledgepro.feeadditional.activatesuccess";
	public static final String FEE_ADDITIONAL_ACTIVATE_FAILURE = "knowledgepro.feeadditional.activatefailure";
	public static final String FEE_ADDITIONAL_LOADING_FAILURE = "knowledgepro.feeadditional.loadingfailure";
	public static final String FEE_ADDITIONAL_ADD_EXIST_REACTIVATE = "knowledgepro.feeadditional.addfailure.alreadyexist.reactivate";
	public static final String FEE_ADDITIONAL_ADD_EXIST = "knowledgepro.feeadditional.addfailure.alreadyexist";
	public static final String FEE_ADDITIONAL_ASSIGNMENTENTRY = "feeAdditionalAssignmentEntry";
	public static final String FEE_ADDITIONAL_ACCOUNT_ASSIGNMENTENTRY = "feeAdditionalAssignmentAccountEntry";
	public static final String FEE_ADDITIONAL_VIEW_ASSIGNMENT = "viewFeeAdditional";
	public static final String FEE_ADDITIONAL_LOAD_FAILURE = "knowledgepro.fee.loadfailure";
	//
	public static final String FEE_BILLNO_NOTPRESENT = "knowledgepro.fee.billno.notpresent";

	// INTERVIEW PROCESS
	public static final String KNOWLEDGEPRO_INTERVIEW_STUDENTS_PER_GROUP = "knowledgepro.interview.studentspergroup";
	public static final String KNOWLEDGEPRO_INTERVIEW_PERCENTNUMARIC = "knowledgepro.interview.percent";
	public static final String KNOWLEDGEPRO_INTERVIEW_WEIGHTNUMARIC = "knowledgepro.interview.weight";
	public static final String KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC = "knowledgepro.interview.StartTime";
	public static final String KNOWLEDGEPRO_INTERVIEW_TIMEINTERVAL = "interviewProcessForm.timeinterval.required";
	public static final String KNOWLEDGEPRO_INTERVIEW_STARTTIME = "knowledgepro.attn.period.startHours";
	public static final String KNOWLEDGEPRO_INTERVIEW_ENDTIME = "knowledgepro.attn.period.endHours";
	public static final String KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY = "knowledgepro.interview.eligibility";
	public static final String KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND = "knowledgepro.interview.timestartend";
	public static final String INTERVIEWPROCESSFORM_BREAKTIME_REQUIRED = "interviewProcessForm.breakTime.required";
	public static final String INTERVIEWPROCESSFORM_PROGRAMTYPE_REQUIRED = "interviewProcessForm.programType.required";
	public static final String INTERVIEWPROCESSFORM_INTERVIEWTYPE_REQUIRED = "interviewProcessForm.interviewType.required";

	public static final String INTERVIEWPROCESSFORM_PROGRAM_REQUIRED = "interviewProcessForm.program.required";
	public static final String INTERVIEWPROCESSFORM_COURSE_REQUIRED = "interviewProcessForm.courseId.required";
	public static final String KNOWLEDGEPRO_INTERVIEW_NORECORDS = "knowledgepro.interview.norecords";
	public static final String KNOWLEDGEPRO_INTERVIEW_ADDSUCCESS = "knowledgepro.interview.addsuccess";
	public static final String KNOWLEDGEPRO_INTERVIEW_ADDFAILURE = "knowledgepro.interview.addfailure";
	public static final String INIT_INTERVIEW_PROCESS = "initInterviewProcess";
	public static final String INIT_INTERVIEW_PROCESS_GENERATE = "initInterviewProcessGenerate";
	public static final String INIT_PRINT_INTERVIEW_PROCESS = "initPrintInterviewProcess";
	public static final String SUBMIT_PRINT_INTERVIEW_PROCESS = "submitPrintInterviewProcess";
	public static final String INTERVIEW_CARD = "printInterviewProcess";
	public static final String INTERVIEW_CARD_NOTICE = "printInterviewNotice";
	public static final String KNOWLEDGEPRO_INTERVIEW_NOCANDIDATES = "knowledgepro.interview.nocandidates";

	public static final String INTERVIEWPROCESSFORM_CHECKBOX_SELECT = "interviewProcessForm.checkbox.select";
	public static final String INTERVIEWPROCESSFORM_CHECKBOX_SELECTONE = "interviewProcessForm.checkbox.selectone";

	public static final String KNOWLEDGEPRO_INTERVIEW_TIMEFORMAT = "knowledgepro.interview.timeformat";
	public static final String KNOWLEDGEPRO_INTERVIEW_PASTDATE = "interviewProcessForm.interviewType.pastdate";
	public static final String KNOWLEDGEPRO_TRUE = "true";
	public static final String KNOWLEDGEPRO_FALSE = "false";
	public static final String KNOWLEDGEPRO_LOGO = "LogoBytes";
	public static final String KNOWLEDGEPRO_TOPBAR = "TopBar";
	public static final String KNOWLEDGEPRO_LOGO1 = "LogoBytes";

	// ####validation keys

	public static final String ADMISSIONFORM_APPLICATIONNO_NOTUNIQUE = "admissionForm.applicationNo.NotUnique";
	public static final String ADMISSIONFORM_APPLICATIONNO_NOTINRANGE = "admissionForm.applicationNo.NotInrange";
	public static final String ADMISSIONFORM_APPLICATIONNO_OFFLINE_INVALID = "admissionForm.applicationNo.Offline.Invalid";
	public static final String ADMISSIONFORM_APPLICATIONNO_NOTCONFIGURED = "admissionForm.applicationNo.NotConfigured";
	public static final String ADDSTUDENT_REGNO_NOTUNIQUE = "studenteditform.regno.notunique";
	public static final String ADMISSIONFORM_APPLICATIONNO_OFFLINEDATA_NOTEXIST = "admissionForm.applicationNo.offlinedata.notexist";
	public static final String ADMISSIONFORM_APPLICATIONNO_ONLINERANGE_FULL = "admissionForm.applicationNo.onlinerange.full";
	public static final String ADMISSIONFORM_PHONE_REQUIRED = "admissionForm.Phone.Required";
	public static final String ADMISSIONFORM_PHONE_INVALID = "admissionForm.Phone.Invalid";
	public static final String ADMISSIONFORM_MOBILE_INVALID = "admissionForm.Mobile.Invalid";
	public static final String ADMISSIONFORM_COMM_ADDRESS1_REQUIRED = "admissionForm.CommAddress.Address1.Required";
	public static final String ADMISSIONFORM_COMM_CITY_REQUIRED = "admissionForm.CommAddress.City.Required";
	public static final String ADMISSIONFORM_COMM_STATE_REQUIRED = "admissionForm.CommAddress.State.Required";
	public static final String ADMISSIONFORM_COMM_COUNTRY_REQUIRED = "admissionForm.CommAddress.Country.Required";
	public static final String ADMISSIONFORM_COMM_ZIP_REQUIRED = "admissionForm.CommAddress.PinCode.Required";
	public static final String ADMISSIONFORM_PERM_ZIP_INVALID = "admissionFormForm.permAddr.pinCode.invalid";
	public static final String ADMISSIONFORM_PASSPORTNO_REQUIRED = "admissionForm.passportNo.Required";
	public static final String ADMISSIONFORM_PASSPORT_COUNTRY_REQUIRED = "admissionForm.passportCountry.Required";
	public static final String ADMISSIONFORM_PASSPORT_VALIDITY_REQUIRED = "admissionForm.passportValidity.Required";
	public static final String ADMISSIONFORM_COMM_ADDRESS_PINCODE_INVALID = "admissionForm.CommAddress.PinCode.invalid";
	public static final String ADMISSIONFORM_PREREQUISITE_INVALID = "admissionForm.prerequisite.Invalid";
	public static final String ADMISSIONFORM_TERMCHKLIST_NOTCHECKED = "admissionForm.termchecklist.mandatory.notchecked";
	public static final String ADMISSIONFORM_GUIDELINES_NOTCHECKED = "admissionForm.guidechecklist.notChecked";
	public static final String GENERATEPASSWORD_STUDENTUSERNAME_SELECTONE = "admin.generatepassword.selectone.studentusernametype";
	public static final String GENERATEPASSWORD_SEND_MAIL_FOR = "admin.generatepassword.selectone.mail";
	public static final String GENERATEPASSWORD_STUDENTUSERNAME_SELECT = "admin.generatepassword.select.studentusername";
	public static final String ADMISSIONFORM_PREREQUISITE_DUPLICATE = "admissionForm.prerequisite.duplicate";
	public static final String ADMISSIONFORM_PREREQUISITE_REQUIRED = "admissionForm.prerequisite.required";

	public static final String ADMISSIONFORM_PREREQUISITE_MONTH_REQUIRED = "admissionForm.prerequisite.month.required";
	public static final String ADMISSIONFORM_PREREQUISITE_YEAR_REQUIRED = "admissionForm.prerequisite.year.required";
	public static final String ADMISSIONFORM_PREREQUISITE_YEAR_FUTURE = "admissionForm.prerequisite.year.future";
	public static final String ADMISSIONFORM_PREREQUISITE_MONTH_FUTURE = "admissionForm.prerequisite.month.future";
	public static final String ADMISSIONFORM_PREREQUISITE_ROLL_REQUIRED = "admissionForm.prerequisite.rollno.required";
	public static final String ADMISSIONFORM_PREREQUISITE_USERMARK_LARGER = "admissionForm.prerequisite.usermark.larger";

	public static final String ADMISSIONFORM_ATTACHMENT_FILENAME = "admissionFormForm.attachment.filename.large";
	public static final String ADMISSIONFORM_OMR_FILETYPEERROR = "admissionFormForm.omr.filetype.invalid";
	public static final String ADMISSIONFORM_ATTACHMENT_MAXSIZE = "admissionFormForm.attachment.maxfileSize";
	public static final String ADMISSIONFORM_PHOTO_MAXSIZE = "admissionFormForm.attachment.maxPhotoSize";
	public static final String ADMISSIONFORM_PROGRAMTYPE_REQUIRED = "admissionFormForm.programTypeId.required";
	public static final String ADMISSIONFORM_PROGRAM_REQUIRED = "admissionFormForm.programId.required";
	public static final String ADMISSIONFORM_COURSE_REQUIRED = "admissionFormForm.courseId.required";
	public static final String ADMISSIONFORM_FIRSTPREF_REQUIRED = "admissionFormForm.firstPref.courseId.required";
	public static final String ADMISSIONFORM_OBTAINMARK_LARGE = "admissionFormForm.education.obtainMark.larger";
	public static final String ADMISSIONFORM_ENT_OBTAINMARK_LARGE = "admissionFormForm.entrance.obtainMark.larger";
	public static final String ADMISSIONFORM_UNIVERSITY_REQUIRED = "admissionFormForm.education.university.required";
	public static final String ADMISSIONFORM_INSTITUTE_REQUIRED = "admissionFormForm.education.instiute.required";
	public static final String ADMISSIONFORM_STATE_REQUIRED = "admissionFormForm.education.state.required";
	public static final String ADMISSIONFORM_EDN_EXAM_REQUIRED = "admissionFormForm.education.exam.required";
	public static final String ADMISSIONFORM_PASSYEAR_REQUIRED = "admissionFormForm.education.passYear.required";
	public static final String ADMISSIONFORM_PASSYEAR_FUTURE = "admissionFormForm.education.passYear.future";
	public static final String ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE = "admissionFormForm.education.passYear.bithyear.future";
	public static final String ADMISSIONFORM_ENT_PASSYEAR_BIRTHYR_FUTURE = "admissionFormForm.entrance.passYear.bithyear.future";
	public static final String ADMISSIONFORM_ENT_PASSYEAR_TODAY_FUTURE = "admissionFormForm.entrance.passYear.today.future";
	public static final String ADMISSIONFORM_PASSMONTH_REQUIRED = "admissionFormForm.education.passMonth.required";
	public static final String ADMISSIONFORM_REGNO_REQUIRED = "admissionFormForm.education.regNO.required";
	public static final String ADMISSIONFORM_NOATTEMPT_REQUIRED = "admissionFormForm.education.noOfAttempt.required";
	public static final String ADMISSIONFORM_TOTALMARK_REQUIRED = "admissionFormForm.education.totalMark.required";
	public static final String ADMISSIONFORM_OBTAINMARK_REQUIRED = "admissionFormForm.education.obtainMark.required";
	public static final String ADMISSIONFORM_DETAILMARK_REQUIRED = "admissionFormForm.education.detailMark.required";
	public static final String ADMISSIONFORM_SEMESTERMARK_REQUIRED = "admissionFormForm.education.semesterMark.required";
	public static final String ADMISSIONFORM_PARENTPHONE_REQUIRED = "admissionFormForm.parentPhone.required";
	public static final String ADMISSIONFORM_PARENTPHONE_INVALID = "admissionFormForm.parentPhone.invalid";
	public static final String ADMISSIONFORM_GUARDIANPHONE_INVALID = "admissionFormForm.guardianPhone.invalid";
	public static final String ADMISSIONFORM_PARENTMOBILE_INVALID = "admissionFormForm.parentMobile.invalid";
	public static final String ADMISSIONFORM_GUARDIANMOBILE_INVALID = "admissionFormForm.guardianMobile.invalid";
	public static final String ADMISSIONFORM_BIRTHSTATE_REQUIRED = "admissionFormForm.birthState.required";
	public static final String ADMISSIONFORM_BIRTHCNT_REQUIRED = "admissionFormForm.birthcountry.select";

	public static final String ADMISSIONFORM_FATHERNAME_REQUIRED = "admissionFormForm.fathername.required";
	public static final String ADMISSIONFORM_MOTHERNAME_REQUIRED = "admissionFormForm.mothername.required";
	public static final String ADMISSIONFORM_FATHERINCOME_REQUIRED = "admissionFormForm.fatherIncome.required";
	public static final String ADMISSIONFORM_MOTHERINCOME_REQUIRED = "admissionFormForm.motherIncome.required";

	public static final String ADMISSIONFORM_LATERAL_MAXMARK_INVALID = "admissionFormForm.lateral.maxMark.notNumeric";
	public static final String ADMISSIONFORM_LATERAL_OBTAINMARK_INVALID = "admissionFormForm.lateral.obtainMark.notNumeric";
	public static final String ADMISSIONFORM_LATERAL_MINMARK_INVALID = "admissionFormForm.lateral.minMark.notNumeric";
	public static final String ADMISSIONFORM_LATERAL_OBTAINMARK_LARGER = "admissionFormForm.lateral.obtainMark.larger";
	public static final String ADMISSIONFORM_LATERAL_MINMARK_LARGER = "admissionFormForm.lateral.minMark.larger";
	public static final String ADMISSIONFORM_LATERAL_YEAR_FUTURE = "admissionForm.lateral.year.future";
	public static final String ADMISSIONFORM_LATERAL_MONTH_FUTURE = "admissionForm.lateral.month.future";

	public static final String ADMISSIONFORM_TCDATE_INVALID = "admissionFormForm.tcdate.invalid";
	public static final String ADMISSIONFORM_MARKCARDDATE_INVALID = "admissionFormForm.markcarddate.invalid";
	public static final String ADMISSIONFORM_ENTRANCEDATE_INVALID = "admissionFormForm.entranceallotmentdate.invalid";

	public static final String ADMISSIONFORM_TCDATE_FUTURE = "admissionFormForm.tcdate.future";
	public static final String ADMISSIONFORM_MARKCARDDATE_FUTURE = "admissionFormForm.markcarddate.future";
	public static final String ADMISSIONFORM_ENTRANCEDATE_FUTURE = "admissionFormForm.entranceallotmentdate.future";

	public static final String ADMISSIONFORM_BIRTHPLACE_REQUIRED = "admissionFormForm.birthPlace.required";
	public static final String ADMISSIONFORM_BLOODGROUP_REQUIRED = "admissionFormForm.bloodGroup.required";
	public static final String ADMISSIONFORM_EMAILID_REQUIRED = "admissionFormForm.emailId.required";
	public static final String ADMISSIONFORM_AREATYPE_REQUIRED = "admissionFormForm.areaType.required";

	public static final String ADMISSIONFORM_PERM_ADDRESS1_REQUIRED = "admissionFormForm.permAddr.addrLine1.required";
	public static final String ADMISSIONFORM_PERM__CITY_REQUIRED = "admissionFormForm.permAddr.cityId.required";
	public static final String ADMISSIONFORM_PERM_COUNTRY_REQUIRED = "admissionFormForm.permAddr.countryId.required";
	public static final String ADMISSIONFORM_PERM_ZIP_REQUIRED = "admissionFormForm.permAddr.pinCode.required";

	public static final String ADMISSIONFORM_RELIGION_REQUIRED = "admissionFormForm.religionId.required";
	public static final String ADMISSIONFORM_SUBRELIGION_REQUIRED = "admissionFormForm.subreligion.required";
	public static final String ADMISSIONFORM_CASTE_REQUIRED = "admissionFormForm.castCategory.required";
	public static final String ADMISSIONFORM_PERMSTATE_REQUIRED = "admissionFormForm.permAddr.stateId.required";
	public static final String ADMISSIONFORM_PARENTSTATE_REQUIRED = "admissionFormForm.parentAddress.state.required";
	public static final String ADMISSIONFORM_DETAILMARK_MANDATORY = "admissionFormForm.education.detailMark.mandatory";
	public static final String ADMISSIONFORM_OBTAINMARK_NOTINTEGER = "admissionFormForm.education.obtainMark.notNumeric";
	public static final String ADMISSIONFORM_TOTALMARK_NOTINTEGER = "admissionFormForm.education.totalMark.notNumeric";
	public static final String ADMISSIONFORM_ENTRANCE_TOTALMARK_NOTINTEGER = "admissionFormForm.entrance.totalMark.notNumeric";
	public static final String ADMISSIONFORM_ENTRANCE_OBTAINMARK_NOTINTEGER = "admissionFormForm.entrance.obtainMark.notNumeric";
	public static final String ADMISSIONFORM_SUBJECTGROUP_REQUIRED = "admissionFormForm.applicantDetails.subjectGroupId.required";
	public static final String ADMISSIONFORM_ADMITTEDTHROUGH_REQUIRED = "admissionFormForm.applicantDetails.admittedThroughId.required";
	public static final String ADMISSIONFORM_FREESHIP_REQUIRED = "admissionFormForm.isFreeShip.required";
	public static final String ADMISSIONFORM_LIG_REQUIRED = "admissionFormForm.isLig.required";
	public static final String ADMISSIONFORM_WORKEXP_STARTDATE_INVALID = "admissionFormForm.workExperience.startdate.Invalid";
	public static final String ADMISSIONFORM_WORKEXP_STARTDATE_ENDDATE = "admissionFormForm.workExperience.startdate.enddate";
	public static final String ADMISSIONFORM_WORKEXP_SALARY_INVALID = "admissionFormForm.workExperience.salary.Invalid";
	public static final String ADMISSIONFORM_PASSPORT_INVALID = "admissionFormForm.passportvalidity.past";
	public static final String ADMISSIONFORM_PASSPORT_VALIDITY_INVALID = "admissionFormForm.passportvalidity.Invalid";
	public static final String ADMISSIONFORM_OMR_REQUIRED = "admissionFormForm.omr.filetype.required";
	public static final String KNOWLEDGEPRO_SUCCESS_STATUS = "knowledgepro.admissionForm.addsuccess";
	public static final String RESETPWD_SUCCESS_STATUS = "knowledgepro.admin.resetpassword.success";
	public static final String RESETPWD_FAILURE_STATUS = "knowledgepro.admin.resetpassword.failure";
	public static final String RESETPWD_USER_NOTEXIST = "knowledgepro.admin.resetpassword.user.notexist";
	public static final String ADMISSIONFORM_APPLICATIONYEAR_REQUIRED = "admissionFormForm.applicationYear.required";
	public static final String ADMISSIONFORM_APPLICATIONNO_INVALID = "admissionFormForm.applicationNumber.invalid";
	public static final String ADMISSIONFORM_APPLICATIONDT_FUTURE = "admissionFormForm.applicationdate.future";
	public static final String ADMISSIONFORM_APPLICATIONDT_INVALID = "admissionFormForm.applicationDate.invalid";
	public static final String ADMISSIONFORM_ADMISSIONDT_FUTURE = "admissionFormForm.admissiondate.future";
	public static final String ADMISSIONFORM_ADMISSIONDT_INVALID = "admissionFormForm.admissionDate.invalid";
	public static final String ADMISSIONFORM_ADMISSIONDT_REQUIRED = "admissionFormForm.admissionDate.required";
	public static final String ADMISSIONFORM_APPLICATIONAMNT_INVALID = "admissionFormForm.applicationAmount.invalid";
	public static final String ADMISSIONFORM_CHALLANNO_REQUIRED = "admissionFormForm.challanNo.required";
	public static final String ADMISSIONFORM_JOURNALNO_REQUIRED = "admissionFormForm.journalNo.required";
	public static final String ADMISSIONFORM_CONFIRM_CHALLANNO_REQUIRED = "admissionFormForm.confirm.challanNo.required";
	public static final String ADMISSIONFORM_CONFIRM_JOURNALNO_REQUIRED = "admissionFormForm.confirm.journalNo.required";
	public static final String ADMISSIONFORM_APPLICATIONDT_REQUIRED = "admissionFormForm.applicationDate.required";
	public static final String ADMISSIONFORM_APPLICATIONAMNT_REQUIRED = "admissionFormForm.applicationAmount.required";
	public static final String ADMISSIONFORM_DOB_LARGE = "admissionFormForm.dob.large";
	public static final String ADMISSIONFORM_DOJ_LARGE = "admissionFormForm.doj.large";
	public static final String ADMISSIONFORM_DOB_INVAID = "admissionFormForm.dateOfBirth.invalid";
	public static final String ADMISSIONFORM_PERMITDT_INVAID = "admissionFormForm.permitDate.Invalid";
	public static final String ADMISSIONFORM_SUBMITDT_PAST = "admissionFormForm.submitdate.past";
	public static final String ADMISSIONFORM_SUBMITDT_INVALID = "admissionFormForm.submitdate.invalid";
	public static final String ADMISSIONFORM_DURATION_INVALID = "admissionFormForm.trainingDuration.invalid";
	public static final String ADMISSIONFORM_HEIGHT_INVALID = "admissionFormForm.height.Invalid";
	public static final String ADMISSIONFORM_WEIGHT_INVALID = "admissionFormForm.weight.Invalid";
	public static final String ADMISSIONFORM_EDIT_WARN = "knowledgepro.admissionForm.editWarning";
	public static final String APPLICATION_REVIEW_WARN = "knowledgepro.admissionForm.reviewWarning";
	public static final String ADMISSIONFORM_ELIGIBILITY_WARN = "knowledgepro.admissionForm.eligibility.fail";
	public static final String ADMISSIONFORM_ADMISSION_DONE = "knowledgepro.admissionForm.alreadyadmitted";
	public static final String ADMISSIONFORM_EDIT_SUCCESS = "knowledgepro.admissionForm.editsuccess";
	public static final String ADMISSIONFORM_CSVUPLOADPAGE = "csvUploadPage";
	public static final String ADMISSIONFORM_CSVUPLOADCONFIRMPAGE = "csvUploadConfirmPage";
	public static final String ADMISSIONFORM_NOAPPROVAL_PENDING = "knowledgepro.admission.noapprovalFound";
	public static final String ADMISSIONFORM_APPROVAL_FAIL = "knowledgepro.admissionForm.approveFail";
	public static final String ADMISSIONFORM_CLASS_REQUIRED = "admissionFormForm.class.required";
	public static final String ADMISSIONFORM_EXP_DATESAME = "admissionFormForm.workExperience.startdate.enddate.same";
	public static final String ADMISSIONFORM_APPPLNNO_REQUIRED = "admissionFormForm.applicationNumber.required";
	public static final String ADMISSIONFORM_STUDENT_CREATED = "knowledgepro.studentEditForm.creationSuccess";
	public static final String ADMISSIONFORM_PAYMENT_DUPLICATE = "admissionFormForm.paymentDetails.duplicate";
	public static final String ADMISSIONFORM_DOB_EXCEEDS = "admissionFormForm.doblimit.larger";
	public static final String ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL = "admissionFormForm.emailconfirm.failure";

	// Admission Data Upload
	public static final String ADMISSIONFORM_CSVUPLOADCONFIRM = "admissionForm.csvupload.success";
	public static final String ADMISSIONFORM_CSVADMISSIONUPLOADPAGE = "csvAdmissionUploadPage";
	public static final String ADMISSIONFORM_APPLICATION_NUMBER = "Application Number";
	public static final String ADMISSIONFORM_REGISTER_NUMBER = "Register Number";
	public static final String ADMISSIONFORM_ROLL_NUMBER = "Roll Number";

	// MAIL RELATED
//	public static final String KNOWLEDGEPRO_ADMIN_MAIL = "knowledgepro.admin.mail";
	public static int empid = 1;
	public static final String GENERATE_PASSWORD_MAIL_TEMPLATE = "knowledgepro.generatepassword.mailtemplate.path";

	// validation keys for Currency Master Entry

	public static final String CURRENCY_NAME_EXIST = "knowledgepro.admin.currencyNameExist";
	public static final String CURRENCY_SHORT_NAME_EXIST = "knowledgepro.admin.currencyShortNameExist";
	public static final String CURRENCY_NAME_REACTIVATE = "knowledgepro.admin.currencyMasterEntry.reactivate";
	public static final String CURRENCY_ADD_SUCCESS = "knowledgepro.admin.currencyMaster.addsuccess";
	public static final String CURRENCY_ADD_FAILURE = "knowledgepro.admin.currencyMaster.addfailure";
	public static final String CURRENCY_UPDATE_SUCCESS = "knowledgepro.admin.currencyMaster.updatesuccess";
	public static final String CURRENCY_UPDATE_FAILURE = "knowledgepro.admin.currencyMaster.updatefailure";
	public static final String CURRENCY_DELETE_SUCCESS = "knowledgepro.admin.currencyMaster.deletesuccess";
	public static final String CURRENCY_DELETE_FAILURE = "knowledgepro.admin.currencyMaster.deletefailure";
	public static final String CURRENCY_REACTIVATE_SUCCESS = "knowledgrpro.admin.currencyMasterEntry.reactivateSuccess";
	public static final String CURRENCY_REACTIVATE_FAILURE = "knowledgepro.admin.currencyMasterEntry.reactivateFailure";

	// validation keys for Cancel Admission

	public static final String ADMISSION_CANCEL_SUCCESS = "knowledgepro.admissionForm.cancelSuccess";
	public static final String ADMISSION_CANCEL_FAILURE = "knowledgepro.admissionForm.cancelFailure";
	public static final String ADMISSION_CANCEL_APPNUMBER_INT = "knowledgepro.admission.applicationnumber.invalid";
	public static final String APPLICATION_REGISTRATION_ROLL_NUMBER_REQUIRE = "knowledgepro.admission.cancellation.appregroll.required";
	public static final String APPLICATION_REGISTRATION_ROLL_NUMBER_NOTEXIST = "knowledgepro.admissionForm.noApplicationNumber";

	// validation keys for Subject Group Entry

	public static final String SUBJECT_GROUP_NAME_EXIST = "knowledgepro.admin.subjectGroupNameExist";
	public static final String SUBJECT_GROUP_NAME_ADDSUCCESS = "knowledgepro.admin.record.added";
	public static final String SUBJECT_GROUP_NAME_ADDFAILURE = "knowledgepro.admin.record.addedfailure";
	public static final String SUBJECT_GROUP_NAME_DELETESUCCESS = "knowledgepro.admin.record.deleted";
	public static final String SUBJECT_GROUP_NAME_DELETEFAILURE = "knowledgepro.admin.record.deletedfailure";
	public static final String SUBJECT_GROUP_NAME_UPDATESUCCESS = "knowledgepro.admin.record.updated";
	public static final String SUBJECT_GROUP_NAME_UPDATEFAILURE = "knowledgepro.admin.record.updatedFailure";
	public static final String SUBJECT_GROUP_REACTIVATE = "knowledgepro.admin.subjectGroupName.alreadyexist.reactivate";
	public static final String SUBJECT_GROUP_REACTIVATE_SUCCESS = "knowledgepro.admin.subjectGroupName.record.activated";

	// validation keys for Fee Headings

	public static final String FEE_HEADING_ADDSUCCESS = "knowledgepro.fee.record.added";
	public static final String FEE_HEADING_ADDFAILURE = "knowledgepro.fee.record.addedfailure";
	public static final String FEE_HEADING_NAME_EXIST = "knowledgepro.fee.feesNameExist";
	public static final String FEE_HEADING_REACTIVATE = "knowledgepro.fee.feesName.alreadyexist.reactivate";
	public static final String FEE_HEADING_DELETESUCCESS = "knowledgepro.fee.record.deleted";
	public static final String FEE_HEADING_DELETEFAILURE = "knowledgepro.fee.record.deletedfailure";
	public static final String FEE_HEADING_UPDATESUCCESS = "knowledgepro.fee.record.updated";
	public static final String FEE_HEADING_UPDATEFAILURE = "knowledgepro.fee.record.updatedfailure";
	public static final String FEE_HEADING_REACTIVATESUCCESS = "knowledgepro.fee.record.record.activated";

	// validation keys for Menu Screen Master
	public static final String MENU_SCREEN_MASTER_SEQUENCE = "knowledgepro.usermanagement.sequenceValue";
	public static final String MENU_SCREEN_MASTER_EXIST = "knowledgepro.usermanagement.menuNameExist";
	public static final String MENU_SCREEN_MASTER_ADDSUCCESS = "knowledgepro.usermanagement.menuAdded";
	public static final String MENU_SCREEN_MASTER_ADDFAILURE = "knowledgepro.usermanagement.menuAddFailure";
	public static final String MENU_SCREEN_MASTER_UPDATESUCCESS = "knowledgepro.usermanagement.menu.updateSuccess";
	public static final String MENU_SCREEN_MASTER_UPDATEFAILURE = "knowledgepro.usermanagement.menu.updateFailure";
	public static final String MENU_SCREEN_MASTER_DELETESUCCESS = "knowledgepro.usermanagement.menu.deleteSuccess";
	public static final String MENU_SCREEN_MASTER_DELETEFAILURE = "knowledgepro.usermanagement.menu.deleteFailure";
	public static final String MENU_SCREEN_MASTER_REACTIVATE = "knowledgepro.usermanagement.menu.reactivate";
	public static final String MENU_SCREEN_MASTER_REACTIVATESUCCESS = "knowledgepro.usermanagement.menu.restoreSuccess";

	// Validation keys for Assign Privilege
	public static final String USERMANAGEMENT_PRIVILEGE_EXIST = "knowledgepro.usermanagement.privilege.exist";
	public static final String USERMANAGEMENT_PRIVILEGE_REACTIVATE = "knowledgepro.usermanagement.privilege.reactivate";
	public static final String USERMANAGEMENT_SELECT_ANY = "knowledgepro.usermanagement.select.any";
	public static final String USERMANAGEMENT_ADD_SUCCESS = "knowledgepro.usermanagement.add.success";
	public static final String USERMANAGEMENT_ADD_FAILED = "knowledgepro.usermanagement.add.failed";
	public static final String USERMANAGEMENT_DELETE_SUCCESS = "knowledgepro.usermanagement.delete.success";
	public static final String USERMANAGEMENT_DELETE_FAILED = "knowledgepro.usermanagement.delete.failed";
	public static final String USERMANAGEMENT_REACTIVATE_SUCCESS = "knowledgepro.usermanagement.privilege.reactivate.success";
	public static final String USERMANAGEMENT_REACTIVATE_FAILED = "knowledgepro.usermanagement.privilege.reactivate.failed";
	public static final String USERMANAGEMENT_UPDATE_SUCCESS = "knowledgepro.usermanagement.privilege.update.success";
	public static final String USERMANAGEMENT_UPDATE_FAILED = "knowledgepro.usermanagement.privilege.update.failure";

	// Validation keys for FeeBillNumber
	public static final String FEE_BILLNUMBER_EXISTS = "knowledgepro.fee.feebillnumber.exists";
	public static final String FEE_BILLNUMBER_REACTIVATE = "knowledgepro.fee.feebillnumber.exists.reactivate";
	public static final String FEE_BILLNUMBER_ADD_SUCCESS = "knowledgepro.fee.feebillnumber.added";
	public static final String FEE_BILLNUMBER_ADD_FAILED = "knowledgepro.fee.feebillnumber.add.failed";
	public static final String FEE_BILLNUMBER_DELETE_SUCCESS = "knowledgepro.fee.feebillnumber.deleted";
	public static final String FEE_BILLNUMBER_DELETE_FAILED = "knowledgepro.fee.feebillnumber.delete.failed";
	public static final String FEE_BILLNUMBER_REACTIVATE_SUCCESS = "knowledgepro.fee.feebillnumber.reactivated";
	public static final String FEE_BILLNUMBER_REACTIVATE_FAILED = "knowledgepro.fee.feebillnumber.reactivate.failed";
	public static final String FEE_BILLNUMBER_UPDATE_SUCCESS = "knowledgepro.fee.feebillnumber.updated";
	public static final String FEE_BILLNUMBER_UPDATE_FAILED = "knowledgepro.fee.feebillnumber.update.failed";

	// Validation keys for Organization Details
	public static final String ADMIN_ORGANIZATION_DELETE_EXISTING = "knowledgepro.admin.organizationdetails.deleteexisting";
	public static final String ADMIN_ORGANIZATION_ADD_SUCCESS = "knowledgepro.admin.organizationdetails.added";
	public static final String ADMIN_ORGANIZATION_ADD_FAILED = "knowledgepro.admin.organizationdetails.addfailed";
	public static final String ADMIN_ORGANIZATION_DELETE_SUCCESS = "knowledgepro.admin.organizationdetails.deleted";
	public static final String ADMIN_ORGANIZATION_DELETE_FAILED = "knowledgepro.admin.organizationdetails.deletefailed";
	public static final String ADMIN_ORGANIZATION_ATTACHMENT_MAXSIZE = "knowledgepro.admin.organization.attachment.maxSize";

	// keys for detailedSubject entry.
	public static final String ADMIN_DETAILSUBJECT_ADD_SUCCESS = "knowledgepro.admin.detailsubject.added";
	public static final String ADMIN_DETAILSUBJECT_ADD_FAILURE = "knowledgepro.admin.detailsubject.addfailure";
	public static final String ADMIN_DETAILEDSUBJET_DELETE_SUCCESS = "knowledgepro.admin.detailsubject.deletesuccess";
	public static final String ADMIN_DETAILEDSUBJET_DELETE_FAILURE = "knowledgepro.admin.detailsubject.deletefailure";
	public static final String ADMIN_DETAILEDSUBJET_UPDATE_SUCCESS = "knowledgepro.admin.detailsubject.updatesuccess";
	public static final String ADMIN_DETAILEDSUBJET_UPDATE_FAILURE = "knowledgepro.admin.detailsubject.updatefailure";
	public static final String ADMIN_DETAILEDSUBJET_ACTIVATE_SUCCESS = "knowledgepro.admin.detailsubject.activatesuccess";
	public static final String ADMIN_DETAILEDSUBJET_ACTIVATE_FAILURE = "knowledgepro.admin.detailsubject.activatefailure";
	public static final String ADMIN_DETAILEDSUBJET_REACTIVATE = "knowledgepro.admin.detailsubject.reactivate";
	public static final String ADMIN_DETAILEDSUBJET_DUPLICATE = "knowledgepro.admin.detailsubject.duplicate";
	public static final String ADMIN_DETAILEDSUBJET_LOADERROR = "knowledgepro.admin.detailsubject.loaderror";

	// keys for Class entry.
	public static final String ATTEDANCE_CLASSES_ADD_SUCCESS = "knowledgepro.attendance.classentry.added";
	public static final String ATTEDANCE_CLASSES_ADD_FAILURE = "knowledgepro.attendance.classentry.addfailure";
	public static final String ATTEDANCE_CLASSES_DELETE_SUCCESS = "knowledgepro.attendance.classentry.deletesuccess";
	public static final String ATTEDANCE_CLASSES_DELETE_FAILURE = "knowledgepro.attendance.classentry.deletefailure";
	public static final String ATTEDANCE_CLASSES_UPDATE_SUCCESS = "knowledgepro.attendance.classentry.updatesuccess";
	public static final String ATTEDANCE_CLASSES_UPDATE_FAILURE = "knowledgepro.attendance.classentry.updatefailure";
	public static final String ATTEDANCE_CLASSES_ACTIVATE_SUCCESS = "knowledgepro.attendance.classentry.activatesuccess";
	public static final String ATTEDANCE_CLASSES_ACTIVATE_FAILURE = "knowledgepro.attendance.classentry.activatefailure";
	public static final String ATTEDANCE_CLASSES_ACTIVATE_REACTIVATE = "knowledgepro.attendance.classentry.reactivate";
	public static final String ATTEDANCE_CLASSES_DUPLICATE = "knowledgepro.attendance.classentry.duplicate";
	public static final String ATTEDANCE_CLASSES_FIELD1_DUPLICATE = "knowledgepro.attendance.classentry.field1duplicate";
	public static final String ATTEDANCE_CLASSES_FIELD2_DUPLICATE = "knowledgepro.attendance.classentry.field2duplicate";
	public static final String ATTEDANCE_CLASSES_LOADERROR = "knowledgepro.attendance.classentry.loaderror";

	// Validation keys for RecommendedBy
	public static final String ADMIN_RECOMMENDEDBY_EXISTS = "knowledgepro.admin.recommendedby.exists";
	public static final String ADMIN_RECOMMENDEDBY_REACTIVATE = "knowledgepro.admin.recommendedby.exists.reactivate";
	public static final String ADMIN_RECOMMENDEDBY_ADD_SUCCESS = "knowledgepro.admin.recommendedby.added";
	public static final String ADMIN_RECOMMENDEDBY_ADD_FAILED = "knowledgepro.admin.recommendedby.addfailed";
	public static final String ADMIN_RECOMMENDEDBY_DELETE_SUCCESS = "knowledgepro.admin.recommendedby.deleted";
	public static final String ADMIN_RECOMMENDEDBY_DELETE_FAILED = "knowledgepro.admin.recommendedby.deletefailed";
	public static final String ADMIN_RECOMMENDEDBY_UPDATE_SUCCESS = "knowledgepro.admin.recommendedby.updated";
	public static final String ADMIN_RECOMMENDEDBY_UPDATE_FAILED = "knowledgepro.admin.recommendedby.updatefailed";
	public static final String ADMIN_RECOMMENDEDBY_REACTIVATE_SUCCESS = "knowledgepro.admin.recommendedby.reactivated";
	public static final String ADMIN_RECOMMENDEDBY_REACTIVATE_FAILED = "knowledgepro.admin.recommendedby.reactivate.failed";
	public static final String ADMIN_RECOMMENDEDBY_REQUIRED ="knowledgepro.admission.recommendedByRequired";
	public static final String ADMIN_RECOMMENDED_DESIGNATION_REQUIRED ="knowledgepro.admission.recommendedPersonDesigRequired";
	// Validation keys for Guidelines Entry
	public static final String ADMIN_GUIDELINESENTRY_EXISTS = "knowledgepro.admin.guidelinesentry.alreadyexists";
	public static final String ADMIN_GUIDELINESENTRY_REACTIVATE = "knowledgepro.admin.guidelinesentry.reactivate";
	public static final String ADMIN_GUIDELINESENTRY_ADD_SUCCESS = "knowledgepro.admin.guidelinesentry.added";
	public static final String ADMIN_GUIDELINESENTRY_ADD_FAILED = "knowledgepro.admin.guidelinesentry.addfailed";
	public static final String ADMIN_GUIDELINESENTRY_DELETE_SUCCESS = "knowledgepro.admin.guidelinesentry.deleted";
	public static final String ADMIN_GUIDELINESENTRY_DELETE_FAILED = "knowledgepro.admin.guidelinesentry.deletfailed";
	public static final String ADMIN_GUIDELINESENTRY_UPDATE_SUCCESS = "knowledgepro.admin.guidelinesentry.updated";
	public static final String ADMIN_GUIDELINESENTRY_UPDATE_FAILED = "knowledgepro.admin.guidelinesentry.updatefailed";
	public static final String ADMIN_GUIDELINESENTRY_REACTIVATE_SUCCESS = "knowledgepro.admin.guidelinesentry.reactivate.success";
	public static final String ADMIN_GUIDELINESENTRY_REACTIVATE_FAILED = "knowledgepro.admin.guidelinesentry.reactivate.failed";

	// //Validation keys for CurriculumScheme
	public static final String ADMISSION_CURRICULUMSCHEME_EXISTS = "knowledgepro.admission.curriculumscheme.courseidexist";
	public static final String ADMISSION_CURRICULUMSCHEME_SUBJECTGROUP_EMPTY = "knowledgepro.admission.curriculumscheme.subjectgroupempty";
	public static final String ADMISSION_CURRICULUMSCHEME_VALIDATE_DURATIONDATE = "knowledgepro.admission.curriculumscheme.validdatedurationdate";
	public static final String ADMISSION_CURRICULUMSCHEME_EMPTY = "knowledgepro.admission.curriculumscheme.empty";
	public static final String ADMISSION_CURRICULUMSCHEME_DATE_EMPTY = "knowledgepro.admission.curriculumscheme.dateempty";
	public static final String ADMISSION_CURRICULUMSCHEME_STDATE_SUBJECT_EMPTY = "knowledgepro.admission.curriculumscheme.startdate&subject";
	public static final String ADMISSION_CURRICULUMSCHEME_ENDDATE_SUBJECT_EMPTY = "knowledgepro.admission.curriculumscheme.enddate&subject";
	public static final String ADMISSION_CURRICULUMSCHEME_DATESAME = "knowledgepro.admission.curriculumscheme.datesame";
	public static final String ADMISSION_CURRICULUMSCHEME_STARTDATE = "knowledgepro.admission.curriculumscheme.startdate";
	public static final String ADMISSION_CURRICULUMSCHEME_ENDDATE = "knowledgepro.admission.curriculumscheme.enddate";
	public static final String ADMISSION_CURRICULUMSCHEME_SUBJECTEMPTY = "knowledgepro.admission.curriculumscheme.subjectempty";
	public static final String ADMISSION_CURRICULUMSCHEME_DATECOMPARE = "knowledgepro.admission.curriculumscheme.datecompare";
	public static final String ADMISSION_CURRICULUMSCHEME_SEMESTERDATE = "knowledgepro.admission.curriculumscheme.semesterdate";
	public static final String ADMISSION_CURRICULUMSCHEME_SEMESTERDATESAME = "knowledgepro.admission.curriculumscheme.semesterdatesame";
	public static final String ADMISSION_CURRICULUMSCHEME_ADD_SUCCESS = "knowledgepro.admission.curriculumscheme.added";
	public static final String ADMISSION_CURRICULUMSCHEME_ADD_FAILED = "knowledgepro.admission.curriculumscheme.addfailed";
	public static final String ADMISSION_CURRICULUMSCHEME_DELETE_SUCCESS = "knowledgepro.admission.curriculumscheme.deleted";
	public static final String ADMISSION_CURRICULUMSCHEME_DELETE_FAILED = "knowledgepro.admission.curriculumscheme.deletefailed";
	public static final String ADMISSION_CURRICULUMSCHEME_UPDATE_SUCCESS = "knowledgepro.admission.curriculumscheme.updated";
	public static final String ADMISSION_CURRICULUMSCHEME_UPDATE_FAILED = "knowledgepro.admission.curriculumscheme.updatefailed";
	public static final String ADMISSION_CURRICULUMSCHEME_YEAR_COMPARE = "knowledgepro.admission.curriculumscheme.yearcompare";
	public static final String ADMISSION_CURRICULUMSCHEME_SDATEEDATE_SUBGROUP = "knowledgepro.admission.curriculumscheme.sdateedate&subgroup";
	public static final String ADMISSION_CURRICULUMSCHEME_BOTHDATE = "knowledgepro.admission.curriculumscheme.bothdate";
	public static final String ADMISSION_CURRICULUMSCHEME_STARTDATE_YEAR = "knowledgepro.admission.curriculumscheme.startdate&year";
	public static final String ADMISSION_CURRICULUMSCHEME_ENDDATE_YEAR = "knowledgepro.admission.curriculumscheme.enddate&year";
	public static final String ADMISSION_CURRICULUMSCHEME_YEAR_SUBJECT = "knowledgepro.admission.curriculumscheme.year&subject";
	public static final String ADMISSION_CURRICULUMSCHEME_YEAR = "knowledgepro.admission.curriculumscheme.year";
	public static final String ADMISSION_CURRICULUMSCHEME_ACADEMICYEAR_COMPARE = "knowledgepro.admission.curriculumscheme.academicyearcompare";
	public static final String ADMISSION_CURRICULUMSCHEME_ACADEMICYEAR = "knowledgepro.admission.curriculumscheme.academicyear";
	public static final String ADMISSION_CURRICULUMSCHEME_ACADEMICYEAR_VALID = "knowledgepro.admission.curriculumscheme.academicyear.valid";
	public static final String ADMISSION_CURRICULUMSCHEME_DATEFORMAT_INVALID = "knowledgepro.admission.curriculumscheme.dateformat.invalid";
	public static final String ADMISSION_CURRICULUMSCHEME_COURSE_INVALID = "knowledgepro.admin.course.invalid";
	public static final String ADMISSION_CURRICULUMSCHEME_COURSESCHEME_INVALID = "knowledgepro.admin.CourseScheme.invalid";
	public static final String ADMISSION_CURRICULUMSCHEME_COURSESCHEME_EDYEARSG = "knowledgepro.admission.curriculumscheme.edyearsg";

	// VALIDATION KEYS FOR ADMISSION STATUS
	public static final String ADMISSION_ADMISSIONSTATUS_ICARD_NOTEXISTS = "knowledgepro.admission.admissionstatus.icardnotexist";
	public static final String ADMISSION_ADMISSIONSTATUS_APPNO_INVALID = "knowledgepro.admission.admissionstatus.invalidappno";
	public static final String ADMISSION_ADMISSIONSTATUS_INVALID_DATA = "knowledgepro.admission.admissionstatus.invaliddata";
	public static final String ADMISSION_ADMISSIONSTATUS_UNDER_PROCESS = "Application Submitted Online - Send the Application and Supporting Documents to Office of Admissions";
	public static final String ADMISSION_ADMISSIONSTATUS_NULL = "knowledgepro.admission.admissionstatus.nullfields";
	public static final String ADMISSION_ADMISSIONSTATUS_ON_PROCESS = "knowledgepro.admission.admissionstatus.underprocess";
	public static final String ADMISSION_ADMISSIONSTATUS_INVALID_DOB = "knowledgepro.admission.admissionstatus.invaliddob";
	public static final String ADMISSION_ADMISSIONSTATUS_DOB_INVALID = "knowledgepro.admission.offlinedetails.dob.invalid";
	public static final String ADMISSION_ADMISSIONSTATUS_APPLICATION_CANCELLED = "Application Cancelled";
	public static final String ADMISSION_ADMISSIONSTATUS_APPLICATION_UNDERREVIEW = "Application Submitted Online - Send the Application and Supporting Documents to Office of Admissions";
	public static final String ADMISSION_INIT_APPLICATIONSTATUS = "initapplicationstatus";
	// Validation keys for Interview Result Entry
	public static final String KNOWLEDGEPRO_ADMISSION_IOD_FUTUREDATE = "knowledgepro.admission.iod.futuredate";
	public static final String KNOWLEDGEPRO_ADMISSION_IOD_INVALID = "knowledgepro.admission.iod.invalid";
	public static final String KNOWLEDGEPRO_ADMIN_TIMEFORMAT = "knowledgepro.admin.timeformat";
	public static final String KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND = "knowledgepro.admission.norecordsfound";
	public static final String KNOWLEDGEPRO_ADMISSION_BATCHRESULTADDED = "knowledgepro.admission.batchresultadded";
	public static final String KNOWLEDGEPRO_ADMISSION_INTERVIEWADDED = "knowledgepro.admission.interviewadded";
	public static final String KNOWLEDGEPRO_ADMISSION_CARDNOTGENERATED = "knowledgepro.admission.cardnotgenerated";
	public static final String KNOWLEDGEPRO_ADMISSION_INTERVIEWRESULTADDED = "knowledgepro.admission.interviewresultadded";

	// Forward path for Offline Details Entry
	public static final String OFFLINE_DETAILS = "offlinedetails";

	// Validation keys for Offline Details Entry
	public static final String KNOWLEDGEPRO_ADMISSION_OFFLINEDETAILS_EXIST = "knowledgepro.admission.offlinedetails.exists";
	public static final String KNOWLEDGEPRO_ADMISSION_OFFLINEDETAILS_ADD_SUCCESS = "knowledgepro.admission.offlinedetails.add.success";
	public static final String KNOWLEDGEPRO_ADMISSION_OFFLINEDETAILS_ADD_FAILURE = "knowledgepro.admission.offlinedetails.add.failure";
	public static final String KNOWLEDGEPRO_ADMISSION_OFFLINEDETAILS_DELETE_SUCCESS = "knowledgepro.admission.offlinedetails.delete.success";
	public static final String KNOWLEDGEPRO_ADMISSION_OFFLINEDETAILS_DELETE_FAILURE = "knowledgepro.admission.offlinedetails.delete.failure";
	public static final String KNOWLEDGEPRO_ADMISSION_OFFLINEDETAILS_UPDATE_SUCCESS = "knowledgepro.admission.offlinedetails.update.success";
	public static final String KNOWLEDGEPRO_ADMISSION_OFFLINEDETAILS_UPDATE_FAILURE = "knowledgepro.admission.offlinedetails.update.failure";
	public static final String KNOWLEDGEPRO_ADMISSION_OFFLINEDETAILS_DATE_INVALID = "knowledgepro.admission.offlinedetails.date.invalid";

	// Validation keys for Interview Definition
	public static final String KNOWLEDGEPRO_INTERVIEWDEFINITION_CONTENTFAIL = "knowledgepro.interviewdefinition.content.fail";
	public static final String KNOWLEDGEPRO_INTERVIEWDEFINITION_ADDSUCCESS = "knowledgepro.admin.interviewdefinition.addsuccess";
	public static final String KNOWLEDGEPRO_INTERVIEWDEFINITION_ADDFAILURE = "knowledgepro.admin.interviewdefinition.addfailure";
	public static final String KNOWLEDGEPRO_INTERVIEWDEFINITION_ADDEXIST = "knowledgepro.admin.interviewdefinition.addexist";
	public static final String KNOWLEDGEPRO_INTERVIEWDEFINITION_REACTIVATE = "knowledgepro.admin.interviewdefinition.addfailure.alreadyexist.reactivate";
	public static final String KNOWLEDGEPRO_INTERVIEWDEFINITION_EDITSUCCESS = "knowledgepro.admin.interviewdefinition.editsuccess";
	public static final String KNOWLEDGEPRO_INTERVIEWDEFINITION_EDITFAILURE = "knowledgepro.admin.interviewdefinition.editfailure";
	public static final String KNOWLEDGEPRO_INTERVIEWDEFINITION_DELETESUCCESS = "knowledgepro.admin.interviewdefinition.deletesuccess";
	public static final String KNOWLEDGEPRO_INTERVIEWDEFINITION_DELETEFAILURE = "knowledgepro.admin.interviewdefinition.deletefailure";
	public static final String KNOWLEDGEPRO_INTERVIEWDEFINITION_ACTIVATESUCCESS = "knowledgepro.interviewdefinition.activatesuccess";
	public static final String KNOWLEDGEPRO_INTERVIEWDEFINITION_ACTIVATEFAILURE = "knowledgepro.interviewdefinition.activatefailure";

	// Validation keys for Interview Definition Subround
	public static final String KNOWLEDGEPRO_INTERVIEWSUBROUND = "interviewSubrounds";
	public static final String KNOWLEDGEPRO_INTERVIEWSUBROUND_ADDSUCCESS = "knowledgepro.admin.interviewsubrounds.addsuccess";
	public static final String KNOWLEDGEPRO_INTERVIEWSUBROUND_ADDFAILURE = "knowledgepro.admin.interviewsubrounds.addfailure";
	public static final String KNOWLEDGEPRO_INTERVIEWSUBROUND_ADDEXIST = "knowledgepro.admin.interviewsubrounds.addexist";
	public static final String KNOWLEDGEPRO_INTERVIEWSUBROUND_REACTIVATE = "knowledgepro.admin.interviewsubrounds.addfailure.alreadyexist.reactivate";
	public static final String KNOWLEDGEPRO_INTERVIEWSUBROUND_EDITSUCCESS = "knowledgepro.admin.interviewsubrounds.editsuccess";
	public static final String KNOWLEDGEPRO_INTERVIEWSUBROUND_EDITFAILURE = "knowledgepro.admin.interviewsubrounds.editfailure";
	public static final String KNOWLEDGEPRO_INTERVIEWSUBROUND_DELETESUCCESS = "knowledgepro.admin.interviewsubrounds.deletesuccess";
	public static final String KNOWLEDGEPRO_INTERVIEWSUBROUND_DELETEFAILURE = "knowledgepro.admin.interviewsubrounds.deletefailure";
	public static final String KNOWLEDGEPRO_INTERVIEWSUBROUND_ACTIVATESUCCESS = "knowledgepro.interviewsubrounds.activatesuccess";
	public static final String KNOWLEDGEPRO_INTERVIEWSUBROUND_ACTIVATEFAILURE = "knowledgepro.interviewdefinition.activatefailure";
	public static final String KNOWLEDGEPRO_INTERVIEWSUBROUND_REQUIRED = "knowledgepro.admin.interviewsubround.subround.required";

	// validation keys for UploadInterviewResult
	public static final String UPLOAD_INTERVIEW_SUBROUND = "knowledgepro.admin.interviewsubround.subround.required";
	public static final String UPLOAD_INTERVIEW = "knowledgepro.admission.excelupload";
	public static final String UPLOAD_BYPASS_INTERVIEW = "knowledgepro.admission.exceluploadbypass";
	public static final String UPLOAD_INTERVIEW_SUCCESS = "knowledgepro.admissionForm.upload.success";
	public static final String UPLOAD_INTERVIEW_FAILURE = "knowledgepro.admissionForm.upload.failure";
	public static final String UPLOAD_INTERVIEW_CSV = "knowledgepro.admission.csvupload";
	public static final String UPLOAD_BYPASS_INTERVIEW_CSV = "knowledgepro.admission.csvbypassupload";
	public static final String UPLOAD_INTERVIEW_DOCUMENT = "knowledgepro.admissionForm.upload.excel";

	// validation keys for Excel Data Upload
	public static final String UPLOAD_EXCEL = "knowledgepro.admin.uploadExcelData";
	public static final String UPLOAD_CSV = "knowledgepro.admin.uploadCSVData";
	public static final String UPLOAD_DOC = "knowledgepro.admin.uploadExcelFile";
	public static final String UPLOAD_DOC_SUCCESS = "knowledgepro.admin.uploadSuccess";
	public static final String UPLOAD_DOC_FAILURE = "knowledgepro.admin.uploadFailure";

	// Validation keys for AttendanceType Entry
	public static final String ATTANDANCE_TYPE_NAME_EXISTS = "knowledgepro.attendance.attendancetype.name.exists";
	public static final String ATTANDANCE_TYPE_EXISTS = "knowledgepro.attendance.attendancetype.exists";
	public static final String ATTANDANCE_TYPE_ADD_SUCCESS = "knowledgepro.attendance.attendancetype.add.success";
	public static final String ATTANDANCE_TYPE_ADD_FAILURE = "knowledgepro.attendance.attendancetype.add.failure";
	public static final String ATTANDANCE_TYPE_DELETE_SUCCESS = "knowledgepro.attendance.attendancetype.delete.success";
	public static final String ATTANDANCE_TYPE_DELETE_FAILURE = "knowledgepro.attendance.attendancetype.delete.failure";
	public static final String ATTANDANCE_TYPE_UPDATE_SUCCESS = "knowledgepro.attendance.attendancetype.update.success";
	public static final String ATTANDANCE_REPORT_STARTDATE_INVALID = "knowledgepro.attendance.startdate";
	public static final String ATTANDANCE_REPORT_ENDDATE_INVALID = "knowledgepro.attendance.enddate";
	public static final String PERIOD_ENTRY = "periodEntry";
	// Forward & Validation kets for Create Practical Batch
	public static final String ATTENDANCE_CREATE_PRACTICAL_INIT = "initcreatepracticalbatch";
	public static final String ATTENDANCE_CREATE_PRACTICAL_CREATE = "createpracticalbatch";
	public static final String ATTENDANCE_CREATE_PRACTICAL_REGDNO_TYPE = "knowledgepro.attendance.regdnos.type";
	public static final String ATTENDANCE_CREATE_PRACTICAL_SEARCH_FAILED = "knowledgepro.attendance.practicalbatch.search.failed";
	public static final String ATTENDANCE_CREATE_PRACTICAL_STUDENTS_NOTFOUND = "knowledgepro.attendance.practicalbatch.student.notfound";
	public static final String ATTENDANCE_CREATE_PRACTICAL_BATCH_EMPTY = "knowledgepro.attendance.practicalbatch.batchname.empty";
	public static final String ATTENDANCE_CREATE_PRACTICAL_BATCHNAME_INVALID = "knowledgepro.attendance.practicalbatch.batchname.invalid";
	public static final String ATTENDANCE_CREATE_PRACTICAL_BATCH_EXIST = "knowledgepro.attendance.practicalbatch.exist";
	public static final String ATTENDANCE_CREATE_PRACTICAL_BATCH_ADDED = "knowledgepro.attendance.practicalbatch.added";
	public static final String ATTENDANCE_CREATE_PRACTICAL_BATCH_ADD_FAILED = "knowledgepro.attendance.practicalbatch.add.failed";
	public static final String ATTENDANCE_CREATE_PRACTICAL_BATCH_SELECTSTUDENT = "knowledgepro.attendance.practicalbatch.selectstudent";
	public static final String ATTENDANCE_CREATE_PRACTICAL_BATCH_STUDENTEXISTS = "knowledgepro.attendance.practicalbatch.studentexists";
	public static final String ELIGIBILITY_CRITERIA = "eligibilityCriteria";
	public static final String ATTENDANCE_CREATE_PRACTICAL_ROLLNO_TYPE = "knowledgepro.attendance.rollnos.type";

	public static final String SUBREL_EXIST_REACTIVATE = "knowledgepro.admin.subrel.alreadyexist.reactivate";
	public static final String SUBREL_ACTIVATE_SUCCESS = "knowledgepro.subrel.activatesuccess";
	public static final String SUBREL_ACTIVATE_FAILURE = "knowledgepro.subrel.activatefailure";
	public static final String DOC_TYPE_ENTRY = "docTypeEntry";

	// Keys for Practical Batch
	public static final String ATTENDANCE_CREATE_PRACTICAL_BATCH_DELETE_SUCCESS = "knowledgepro.attendance.practicalbatch.delete.success";
	public static final String ATTENDANCE_CREATE_PRACTICAL_BATCH_DELETE_FAILED = "knowledgepro.attendance.practicalbatch.delete.failed";
	public static final String ATTENDANCE_CREATE_PRACTICAL_BATCH_REACTIVATE_SUCCESS = "knowledgepro.attendance.practicalbatch.reactivate.success";
	public static final String ATTENDANCE_CREATE_PRACTICAL_BATCH_REACTIVATE_FAILED = "knowledgepro.attendance.practicalbatch.reactivate.failed";
	public static final String ATTENDANCE_CREATE_PRACTICAL_BATCH_REACTIVATE = "knowledgepro.attendance.practicalbatch.reactivate";
	public static final String ATTENDANCE_CREATE_PRACTICAL_BATCH_UPDATE_SUCCESS = "knowledgepro.attendance.practicalbatch.update.success";
	public static final String ATTENDANCE_CREATE_PRACTICAL_BATCH_UPDATE_FAILED = "knowledgepro.attendance.practicalbatch.update.failed";

	public static final String ATTENDANCE_CREATE_PRACTICAL_BATCH_DUPLICATE = "is already assigned";
	public static final String ATTENDANCE_CREATE_PRACTICAL_BATCH_DUPLICATE_ALL = "are already assigned";

	public static final String ADMISSIONDATAUPLOAD_FAILURE = "knowledgepro.admissiondataupload.already.present";

	// Students Attendance Report
	public static final String STUDENTS_ATTENDANCE_REPORT = "studentsAttendanceReport";
	public static final String STUDENTS_ATTENDANCE_REPORT_RESULT = "studentsAttendanceReportResult";
	public static final String STUDENTS_ATTENDANCE_DETAILS = "studentsAttendanceDetails";
	public static final String STUDENTS_ATTENDANCE_DETAILS_RESULT = "studentsAttendanceDetailsResult";
	public static final String MONTHLY_STUDENTS_ATTENDANCE_REPORT = "monthlyStudentsAttendanceReport";
	public static final String PERFORMA_III_INPUT = "performaIIIInput";
	public static final String PERFORMA_III_REPORT = "performaIIIReport";

	public static final String DATABASE_PAGE_VIEW = "view_page";
	public static final String NEWS_EVENTS_VIEW = "news_events";
	public static final String EXCEL_UPLOAD = "excel_upload";

	// Student Login
	public static final String STUDENTLOGIN_SUCCESS = "loginSuccess";
	public static final String STUDENTLOGIN_FAILURE = "loginFailure";
	public static final String STUDENTLOGIN_SUCCESS_PASSCHANGE = "loginSuccessPassChange";
	public static final String STUDENTLOGIN_HOME = "displayStudentLogin";
	public static final String STUDENTLOGOUT_SUCCESS = "logoutSuccess";
	// Forwards for Check List

	public static final String CHECK_LIST = "check_List";
	public static final String CHECK_DOCS = "check_docs";
	public static final String VIEW_CHECK_LIST = "viewCheckList";
	public static final String CHANGE_PASSWORD = "changePassword";
	public static final String CHANGE_STUDENT_PASSWORD = "changeStudentPassword";
	public static final String CHANGE_STUDENT_PASSWORD_LOGIN = "changeStudentPasswordLogin";
	public static final String GENERATE_PASSWORD = "generatePassword";
	public static final String GENERATE_PASSWORD_CONFIRM = "generatePasswordConfirm";
	public static final String RESET_PASSWORD = "resetPassword";
	public static final String USER_REPORT = "userReport";
	public static final String SUBMIT_USER_REPORT = "submitUserReport";
	public static final String SHOW_PRIVILEGE = "showPrivilege";

	// Forwards for News & Events.
	public static final String LOGIN_DISPLAY = "displayLogin";

	// Forwards for leave report.
	public static final String LEAVE_REPORT = "leaveReport";
	public static final String LEAVE_REPORT_SUBMIT = "leave_Report_Result";
	// Forwards for Below Required Percentage Report
	public static final String INIT_BELOWREQUIRED_PERCENTAGE = "initbelowRequiredPercentage";
	public static final String SUBMIT_BELOWREQUIRED_PERCENTAGE = "submitbelowRequiredPercentage";

	public static final String MAIL_SUBJECT = "Login details";
	public static final String APPLN_MAIL_SUBJECT = "Application submitted successfully";
	public static final String APPLN_MAIL_SUBJECT_TEMPLATE = "Application for [PROGRAM] [ACADEMIC_YEAR] submitted successfully";
	public static final String MAIL_STUDENT_MATTER1 = "Here with we are sending you the login name and password to monitor the academic and attendance progress.<br><br><br>You can see the Attendance and Progress of ";
	public static final String MAIL_MATTER1 = "Here with we are sending you the login name and password of your ward to monitor his/her academic and attendance progress.<br><br><br>You can see the Attendance and Progress of ";
	public static final String MAIL_MATTER2 = " on our Web Site. It will be updated every week. Detailed attendance of previous week will also be available.<br><br><br> The Website can be accessed by following these steps:<br>1. Login to the University website www.christuniversity.in<br>2. Click on the option Attendance And Progress Reports.<br>3. Enter the student's Register Number and Password.4. Click Submit.<br><br><br>Note:Attendance percentage and corresponding marks will be available in the progress report for each student after termly exams.<br><br><br>If there is any change in the Address, Phone number, etc.,you may inform the office through your ward in writing.<br><br><br>The Login Name and password is:<br>";
	public static final String MAIL_MATTER3 = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;For Christ Universtiy<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Prof.J.Subramanian<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;REGISTRAR";
	public static final String MAIL_LOGINNAME = "Login Name : ";
	public static final String MAIL_LOGINPASSWORD = "Password : ";
	public static final String MAIL_DATE = "Date : ";
	public static final String MAIL_PLACE = "Bangalore";
	public static final String FINAL_MERIT_LIST_MAIL_SUBJECT = "Admission for [PROGRAM] [ACADEMIC_YEAR]";
	public static final String INTERVIEW_PROCESSING_MAIL_SUBJECT = "[INTERVIEW_TYPE] for [PROGRAM] [ACADEMIC_YEAR]";

	public static final String REMARK_TYPE_ENTRY = "remarkTypeEntry";
	public static final String REMARK_TYPE_EXIST_REACTIVATE = "knowledgepro.admin.remark.type.addfailure.alreadyexist.reactivate";
	public static final String REMARK_TYPE_ACTIVATE_SUCCESS = "knowledgepro.remark.type.activatesuccess";
	public static final String REMARK_TYPE_ACTIVATE_FAILURE = "knowledgepro.remark.type.activatefailure";
	public static final String ORGANIZATION_UPDATE_SUCCESS = "knowledgepro.admin.organization.update.success";
	public static final String ORGANIZATION_UPDATE_FAILED = "knowledgepro.admin.organization.update.failed";

	// CONSTANTS AFTER PMD CHECK
	public static final String CONDITIONS_OPERATION = "conditionsOperation";
	public static final String ERROR = "error";
	public static final String MESSAGES = "messages";
	public static final String MESSAGES1 = "messages1";
	public static final String INTERVIEW = "interview";
	public static final String SELECTED_FOR_ADMISSION = "You are Selected for Admission";
	public static final String NOT_SELECTED_FOR_ADMISSION = "Not selected for admission";
	public static final String ADMISSION = "admission";
	public static final String APPLICATION_PROPERTIES = "resources/application.properties";
	public static final String APPLICATION_PROPERTIES_1 = "/resources/application.properties";
	public static final String PDF_SOURCE = "knowledgepro.admin.pdfsource";
	public static final String PDF_DESTINATION = "knowledgepro.admin.pdfdestination";
	public static final String EXCEL_DESTINATION = "knowledgepro.admin.exceldestionation";
	public static final String CSV_DESTINATION = "knowledgepro.admin.csvdestionation";
	public static final String PDF_SOURCE_AC = "knowledgepro.admin.pdfsource.ac";
	public static final String YES = "yes";
	public static final String NO = "no";
	public static final String CLASS = "Class";
	public static final String SUBJECT = "Subject";
	public static final String PERIOD = "Period";
	public static final String TEACHER = "Teacher";
	public static final String BATCH_NAME = "Batch name";
	public static final String ACTIVITY_TYPE = "Activity Type";
	public static final String AMOUNT_INVALID = "admissionFormForm.Amount.invalid";
	public static final String CASTE = "Caste";
	public static final String COUNTRY = "Country";
	public static final String INTERVIEW_STATUS = "InterviewStatus";
	public static final String COURSE_SCHEME = "CourseScheme";
	public static final String RELIGION = "Religion";
	public static final String PREREQUISITE = "Prerequisite";
	public static final String ADMITTED_THROUGH = "AdmittedThrough";
	public static final String PROGRAM_TYPE = "ProgramType";
	public static final String OCCUPATION = "Occupation";
	public static final String RESIDENT_CATEGORY = "Resident Category";
	public static final String REGION = "Region";
	public static final String UNIVERSITY = "University";
	public static final String DEPARTMENT = "Department";
	public static final String ROLES = "Roles";
	public static final String EMPLOYEE_CATEGORY = "EmployeeCategory";
	public static final String LEAVE_TYPE = "LeaveType";
	public static final String FEE_DIVISION = "FeeDivision";
	public static final String FEE_PAYMENT_MODE = "FeePaymentMode";
	public static final String DESIGNATION = "Designation";
	public static final String PREFERENCES = "Preferences";
	public static final String CURRENCY = "Currency";
	public static final String RELIGION_SECTION = "ReligionSection";
	public static final String STATE = "State";
	public static final String COURSE_PREREQUISITE = "CoursePrerequisite";
	public static final String PROGRAM = "Program";
	public static final String COURSE = "Course";
	public static final String GUIDELINESDOC = "GuidelinesDoc";
	public static final String TERMS_CONDITIONS = "TermsConditions";
	public static final String CLASSES = "Classes";
	public static final String DOCTYPE = "DocType";
	public static final String COLLEGE = "College";
	public static final String GRADE = "Grade";
	public static final String RECOMMENDOR = "Recommendor";
	public static final String SUBJECT_GROUP = "SubjectGroup";
	public static final String DETAILED_SUBJECTS = "DetailedSubjects";
	public static final String ELIGIBILITY = "EligibilityCriteria";
	public static final String NEWS_EVENTS = "NewsEvents";
	public static final String EXTRA_CURRICULAR_ACTIVITY = "ExtracurricularActivity";
	public static final String FEE_BILL_NUMBER = "FeeBillNumber";
	public static final String FEE_GROUP = "FeeGroup";
	public static final String FEE_HEADING = "FeeHeading";
	public static final String FEE_ADDITIONAL = "FeeAdditional";
	public static final String FEE_ACCOUNT = "FeeAccount";
	public static final String INTERVIEW_SUB_ROUNDS = "InterviewSubRounds";
	public static final String APPLICATION_NUMBER = "ApplicationNumber";
	public static final String ACTIVITY = "Activity";
	public static final String ATTENDANCE_TYPE = "AttendanceType";
	public static final String MODULES = "Modules";
	public static final String REMARK_TYPE = "RemarkType";
	public static final String MENUS = "Menus";
	public static final String NATIONALITY = "Nationality";
	public static final String INTERVIEW_PROGRAM_COURSE = "InterviewProgramCourse";
	public static final String GROUP = "group";

	public static final String DOC_TYPE_REACTIVATE = "knowledgepro.admin.DocType.addfailure.alreadyexist.reactivate";
	public static final String DOC_TYPE_ACTIVATE_SUCCESS = "knowledgepro.admin.DocType.activate";
	public static final String DOC_TYPE_ACTIVATE_FAILURE = "knowledgepro.admin.DocType.activate.failure";
	public static final String INIT_MONTHLY_BELOW_PERCENTAGE = "initMonthlyBelowRequiredPercentage";
	public static final String SUBMIT_MONTHLY_BELOW_PERCENTAGE = "submitMonthlyBelowRequiredPercentage";
	public static final String PRINT_PASSWORD = "printPassword";
	public static final String INIT_CATEGORY_WISE = "initcategorywise";
	public static final String SUBMIT_CATEGORY_WISE = "submitcategorywise";

	// // List of 1st Year Students.
	public static final String LIST_STUDENTS = "students_list";
	public static final String STUDENTS_REPORT_LIST = "students_report_list";

	// Category Wise Students Intake
	public static final String CANDIDATE_INTAKE = "initcategorywiseintake";
	public static final String CANDIDATE_INTAKE_RESULT = "category_wise_intake_result";

	public static final String LIST_OF_SUBJECT_GROUPS_REPORT = "listofSubGroup";
	public static final String SUBMIT_SUBJECT_GROUP_REPORT = "submitListofSubGroup";
	public static final String SEARCH_CANCELLED_ADMISSIONS_REPORT = "searchCancelledAdmissions";
	public static final String SUBMIT_CANCELLED_ADMISSIONS_REPORT = "submitCancelledAdmissions";
	public static final String VIEW_APPLICATION = "viewapplication";
	public static final String CALL_FOR = "You are invited for ";
	public static final String RESULT_UNDER_PROCESS = "Results under process";
	public static final String DETAILED_TC = "detailedTC";
	public static final String DETAILED_TC_DESC = "detailedTCDesc";
	public static final String VIEW_TERMS_CONDITIONS_CHECK_LIST = "viewtermsconditionCheckList";

	// Admission Report Column Names

	public static final String REGNO = "Reg No";
	public static final String ROLLNO = "Roll No";
	public static final String CLASS_NAME = "Class Name";
	public static final String ADMISSION_DATE = "Admission Date";
	public static final String SECOND_LANGUAG = "Second Language";
	public static final String PROGTYPE_NAME = "Program Type Name";
	public static final String PROG_NAME = "Program Name";
	public static final String COURSE_NAME = "Course Name";
	public static final String JOURNAL_NO = "Journal No";
	public static final String ACEDAMIC_YEAR = "Acedamic Year";
	public static final String ADMITTED_THROUG = "Admitted Through";
	public static final String RESEIDENT_CATEGORY = "Resident Category";
	public static final String RELIGIN = "Religion";
	public static final String RELIGION_OTHER = "Religion Other";
	public static final String SUB_RELIGION = "Sub Religion";
	public static final String SUB_RELIGION_OTHER = "Sub Religion Other";
	public static final String CAST_CATEGORY = "Cast Category";
	public static final String CAST_CATEGORY_OTHER = "Cast Category Other";
	public static final String PLACE_OF_BIRTH = "Place Of Birth";
	public static final String STATE_OF_BIRTH = "State Of Birth";
	public static final String STATE_OF_BIRTH_OTHER = "State Of Birth Other";
	public static final String COUNTRY_OF_BIRTH = "Country Of Birth";
	public static final String BELONGS_TO = "Belongs To";
	public static final String NATIONALIT = "Nationality";
	public static final String STUDENT_PHONE_NUMBER = "Student Phone Number";
	public static final String STUDENT_MOBILE_NUMBER = "Student Mobile Number";
	public static final String PASSPORT_NUMBER = "Passport Number";
	public static final String PASSPORT_ISSUING_COUNTRY = "Passport Issuing Country";
	public static final String PASSPORT_VALIDUPTO = "Passport Valid Up To";
	public static final String FIRST_PRE_PROGTYPE = "First Preference Program Type";
	public static final String FIRST_PRE_PROG = "First Preference Program";
	public static final String FIRST_PRE_COURSE = "First Preference Course";
	public static final String SECOND_PRE_PROGTYPE = "Second Preference Program Type";
	public static final String SECOND_PRE_PROG = "Second Preference Program";
	public static final String SECOND_PRE_COURSE = "Second Preference Course";
	public static final String THIRD_PRE_PROGTYPE = "Third Preference Program Type";
	public static final String THIRD_PRE_PROG = "Third Preference Program";
	public static final String THIRD_PRE_COURSE = "Third Preference Course";
	public static final String FIRST_NAME_OF_ORG = "First Name of Organisation";
	public static final String FIRST_DESIG = "First Designation";
	public static final String FIRST_FROM_DATE = "First From Date";
	public static final String FIRST_TO_DATE = "First To Date";
	public static final String SECOND_NAME_OF_ORG = "Second Name of Organisation";
	public static final String SECOND_DESIG = "Second Designation";
	public static final String SECOND_FROM_DATE = "Second From Date";
	public static final String SECOND_TO_DATE = "Second To Date";
	public static final String THIRD_NAME_OF_ORG = "Third Name of Organisation";
	public static final String THIRD_DESIG = "Third Designation";
	public static final String THIRD_FROM_DATE = "Third From Date";
	public static final String THIRD_TO_DATE = "Third To Date";
	public static final String PERMANENT_ADDR_LINE1 = "Permanent Address Line1";
	public static final String PERMANENT_ADDR_LINE2 = "Permanent Address Line2";
	public static final String PERMANENT_STATE = "Permanent State";
	public static final String PERMANENT_STATE_OTHERS = "Permanent State Others";
	public static final String PERMANENT_CITY = "Permanent City";
	public static final String PERMANENT_COUNTRY = "Permanent Country";
	public static final String PERMANENT_ZIP_CODE = "Permanent Zip Code";
	public static final String CURRENT_ADDR_LINE1 = "Current Address Line1";
	public static final String CURRENT_ADDR_LINE2 = "Current Address Line2";
	public static final String CURRENT_STATE = "Current State";
	public static final String CURRENT_STATE_OTHERS = "Current State Others";
	public static final String CURRENT_CITY = "Current City";
	public static final String CURRENT_COUNTRY = "Current Country";
	public static final String CURRENT_ZIP_CODE = "Current Zip Code";
	public static final String FATHERS_NAME = "Father's Name";
	public static final String FATHERS_EDUCATION = "Father's Education";
	public static final String FATHERS_INCOME = "Father's Income";
	public static final String FATHERS_CURRENCY = "Father's Currency";
	public static final String FATHERS_OCCUPATION = "Father's Occupation";
	public static final String FATHERS_EMAIL = "Father's Email";
	public static final String MOTHERS_NAME = "Mother Name";
	public static final String MOTHERS_EDUCATION = "Mother Education";
	public static final String MOTHERS_INCOME = "Mother's Income";
	public static final String MOTHERS_CURRENCY = "Mother's Currency";
	public static final String MOTHERS_OCCUPATION = "Mother's Occupation";
	public static final String MOTHERS_EMAIL = "Mother's Email";
	public static final String PARENT_ADDR_LINE1 = "Parent Adress Line1";
	public static final String PARENT_ADDR_LINE2 = "Parent Adress Line2";
	public static final String PARENT_ADDR_LINE3 = "Parent Adress Line3";
	public static final String PARENT_CITY = "Parent City";
	public static final String PARENT_STATE = "Parent State";
	public static final String PARENT_STATE_OTHER = "Parent State Other";
	public static final String PARENT_COUNTRY = "Parent Country";
	public static final String PARENT_ZIP_CODE = "Parent Zip Code";
	public static final String PARENT_PHONE = "Parent Phone";
	public static final String PARENT_MOBILE_NUMBER = "Parent Mobile No";
	public static final String APPLICATION_NUM = "Application Number";
	public static final String STUDENT_NAME = "Student Name";
	public static final String BLOOD_GROUP = "Blood Group";
	public static final String EMAIL = "Email";
	public static final String TOTAL_WEIGHTAGE = "TotalWeightage";
	public static final String DATE_OF_BIRTH = "Date Of Birth";
	public static final String CHALLAN_NUMBER = "Challan Number";
	public static final String GENDER = "Gender";

	public static final String INIT_CONFIG_REPORT = "initconfigreport";
	public static final String VIEW_CONFIG_REPORT_DETAILS = "viewconfigreportdetails";
	public static final String CONFIG_UPDATE_SUCCESS = "knowledgepro.details.update.success";
	public static final String CONFIG_UPDATE_FAILURE = "knowledgepro.details.update.failure";
	public static final String COLUMN_DISPLAY = "column_display";
	public static final String CONFIG_POSITION_REQUIRED = "knowledgepro.config.report.position.required";
	public static final String CONFIG_POSITION_NUMERIC = "knowledgepro.config.report.position.numeric";
	public static final String CONFIG_POSITION_DUPLICATE = "knowledgepro.config.report.position.duplicate";
	public static final String CONFIG_POSITIOND_ZERO = "knowledgepro.config.report.position.zero.notallowed";
	// Template related constants
	public static final String TEMPLATE_INSTITUTION_NAME = "[INSTITUTIONNAME]";
	public static final String TEMPLATE_CANDIDATE_NAME = "[NAME]";
	public static final String TEMPLATE_PROGRAM = "[PROGRAM]";
	public static final String TEMPLATE_COURSE = "[COURSE]";
	public static final String TEMPLATE_COURSE_CODE = "[COURSECODE]";
	public static final String TEMPLATE_PENDING_DOCUMENTS = "[PENDINGDOCUMENTSNAME]";
	public static final String TEMPLATE_SUBMITTED_DOCUMENTS = "[SUBMITTEDDOCUMENTSNAME]";
	public static final String TEMPLATE_SUBMITTED_DATE = "[SUBMISSIONDATE]";
	public static final String TEMPLATE_CURRENT_DATE = "[CURRENTDATE]";
	public static final String TEMPLATE_SELECTED_COURSE = "[SELECTEDCOURSE]";
	public static final String TEMPLATE_APPLICATION_NO = "[APPLICATIONNO]";
	public static final String TEMPLATE_INTERVIEW_TYPE = "[INTERVIEWTYPE]";
	public static final String TEMPLATE_APPLICANT_NAME = "[APPLICANTNAME]";
	public static final String TEMPLATE_BARCODE = "[BARCODE]";
	public static final String TEMPLATE_DOB = "[DOB]";
	public static final String TEMPLATE_POB = "[POB]";
	public static final String TEMPLATE_EMAIL = "[EMAIL]";
	public static final String TEMPLATE_NATIONALITY = "[NATIONALITY]";
	public static final String TEMPLATE_GENDER = "[GENDER]";
	public static final String TEMPLATE_SUBRELIGION = "[SUB-RELIGION]";
	public static final String TEMPLATE_RESIDENTCATEGORY = "[RESIDENTCATEGORY]";
	public static final String TEMPLATE_RELIGION = "[RELIGION]";
	public static final String TEMPLATE_CASTE = "[CASTE]";
	public static final String TEMPLATE_INTERVIEW_DATE = "[INTERVIEWDATE]";
	public static final String TEMPLATE_INTERVIEW_TIME = "[INTERVIEWTIME]";
	public static final String TEMPLATE_INTERVIEW_VENUE = "[VENUE]";
	public static final String TEMPLATE_PHOTO = "[PHOTO]";
	public static final String TEMPLATE_LOGO = "[LOGO]";
	public static final String TEMPLATE_LOGO1 = "[LOGO1]";
	public static final String TEMPLATE_ACADEMIC_YEAR = "[ACADEMICYEAR]";
	public static final String TEMPLATE_CURRENT_ADDRESS = "[STUDENT_CURRENT_ADDRESS]";
	public static final String TEMPLATE_PERMANENT_ADDRESS = "[STUDENT_PERMANENT_ADDRESS]";
	public static final String TEMPLATE_FATHER_NAME = "[FATHERNAME]";
	public static final String TEMPLATE_ADDRESS = "[ADDRESS]";
	public static final String TEMPLATE_PINCODE = "[PINCODE]";
	public static final String TEMPLATE_USERNAME = "[USERNAME]";
	public static final String TEMPLATE_PASSWORD = "[PASSWORD]";
	public static final String TEMPLATE_DATE = "[DATE]";
	public static final String TEMPLATE_PRINT_CHALLAN = "[PRINT_CHALLAN]";
	public static final String TEMPLATE_ITEM_NO = "[ITEM_NO]";
	public static final String TEMPLATE_ITEM_NAME = "[ITEM_NAME]";
	public static final String TEMPLATE_ITEM_INV_LOCATION = "[LOCATION_NAME]";
	public static final String TEMPLATE_ITEM_DOE = "[DATE_OF_EXPIRY]";
	public static final String TEMPLATE_ITEM_VENDOR_NAME = "[VENDOR_NAME]";
	public static final String TEMPLATE_ITEM_MAIL_SUBJECT = "AMC/Warranty Expiry Notification Mail";
	public static final String TEMPLATE_INTERVIEW_MAIL_SUBJECT = "Interview Result";

	public static final String ADMISSION_ABSTRACT_REPORT_SEARCH = "admissionAbstractSearch";
	public static final String ADMISSION_ABSTRACT_REPORT_SUBMIT = "admissionAbstractSubmit";
	public static final String DISCIPLINARY_TYPE_ENTRY = "disciplinaryTypeEntry";
	public static final String DISCIPLINARY_TYPE_EXIST_REACTIVATE = "knowledgepro.admin.remark.type.addfailure.alreadyexist.reactivate";
	public static final String DISCIPLINARY_TYPE_ACTIVATE_SUCCESS = "knowledgepro.disciplinary.type.activatesuccess";
	public static final String DISCIPLINARY_TYPE_ACTIVATE_FAILURE = "knowledgepro.disciplinary.type.activatefailure";

	public static final String PROGRAM_TYPE_EXIST_REACTIVATE = "knowledgepro.admin.program.type.addfailure.alreadyexist.reactivate";
	public static final String PROGRAM_TYPE_ACTIVATE_SUCCESS = "knowledgepro.program.type.activatesuccess";
	public static final String PROGRAM_TYPE_ACTIVATE_FAILURE = "knowledgepro.program.type.activatefailure";

	public static final String GUIDELINE_CHECKLIST_ENTRY = "guidelineCheckListEntry";
	public static final String GUIDELINE_CHECKLIST_EXIST_REACTIVATE = "knowledgepro.admin.guideline.checklist.alreadyexist.reactivate";
	public static final String GUIDELINE_ACTIVATE_SUCCESS = "knowledgepro.admin.guideline.checklist.activatesuccess";
	public static final String GUIDELINE_ACTIVATE_FAILURE = "knowledgepro.admin.guideline.checklist.activatefailure";

	public static final String MISCOMPARISION_SEARCH = "searchMISComparision";
	public static final String MISCOMPARISION_REPORT = "reportMISComparision";
	public static final String TRANSACTION_DATE_INVALID = "knowledgepro.report.transactionDate.invalid";
	public static final String EXCEL_BYTES = "excelbytes";
	public static final String CSV_BYTES = "csvBytes";
	public static final String HOSTEL_ENTRY = "hostelEntry";
	public static final String HOSTEL_ENTRY_EXIST_REACTIVATE = "knowledgepro.hostel.entry.addfailure.alreadyexist.reactivate";
	public static final String HOSTEL_ENTRY_ACTIVATE_SUCCESS = "knowledgepro.hostel.entry.activatesuccess";
	public static final String HOSTEL_ENTRY_ACTIVATE_FAILURE = "knowledgepro.hostel.entry.activatefailure";
	public static final String HOSTEL_RESERVATION1 = "hostelReservationPage1";
	public static final String HOSTEL_RESERVATION2 = "hostelReservationPage2";
	// Hostel Application student : constants

	public static final String INIT_HOSTEL_STUDENT = "inithostelstudent";
	public static final String HOSTEL_REQUISITION_TO_APPLY = "requisitiontoapply";
	public static final String HOSTEL_GET_ACKNOWLEDGEMENT = "getacknowledgement";
	public static final String HOSTEL_VIEW_TERMS_CONDITION = "viewtermscondition";
	public static final String HOSTEL_APP_SUBMIT_FAILED = "knowledgepro.hostel.application.submit.failed";
	public static final String HOSTEL_ROOMTYPE_UNAVAILABLE = "knowledgepro.hostel.application.roomtype.unavailable";
	public static final String HOSTEL_APPLICATION_ACCEPT = "knowledgepro.hostel.application.accept";

	public static final String ASSIGN_ROOM_MASTER = "assignRoomMaster";
	public static final String SUBMIT_ROOM_MASTER = "submitRoomMaster";
	public static final String NO_RECORD_PASSWORD = "noRecord";
	public static final String SHOW_PASSWORD = "showPassword";

	// Keys for Bank MIS
	public static final String BANKMISVIEW = "bankmisview";
	public static final String SUBMITBANKMISVIEW = "submitBankMISView";
	public static final String UPLOADEXCELFILEREQ = "knowledgepro.admin.uploadexcelfilerequired";
	public static final String BANKEXCELFILE = "knowledgepro.admin.bankexcel";

	// Forward for StudentReqStatus

	public static final String STUD_REQ_STATUS = "streqstatus";

	// Constants for RoomType
	public static final String HOSTEL_ROOMTYPE_DUPLICATE = "knowledgepro.hostel.roomtype.duplicate";
	public static final String HOSTEL_ROOMTYPE_REACTIVATE = "knowledgepro.hostel.roomtype.reactivate";
	public static final String HOSTEL_ROOMTYPE_ADD_SUCCESS = "knowledgepro.roomtype.add.success";
	public static final String HOSTEL_ROOMTYPE_ADD_FAILED = "knowledgepro.roomtype.add.failure";
	public static final String HOSTEL_ROOMTYPE_DELETE_SUCCESS = "knowledgepro.roomtype.delete.success";
	public static final String HOSTEL_ROOMTYPE_DELETE_FAILED = "knowledgepro.roomtype.delete.failure";
	public static final String HOSTEL_ROOMTYPE_QUANTITY_REQUIRED = "knowledgepro.hostel.quantity.required";
	public static final String HOSTEL_ROOMTYPE_QUANTITY_INTEGER = "knowledgepro.hostel.quantity.integer";
	public static final String HOSTEL_ROOMTYPE_REACTIVATE_SUCCESS = "knowledgepro.roomtype.reactivate.success";
	public static final String HOSTEL_ROOMTYPE_REACTIVATE_FAILED = "knowledgepro.roomtype.reactivate.failed";
	public static final String HOSTEL_ROOMTYPE_UPDATE_SUCCESS = "knowledgepro.roomtype.update.success";
	public static final String HOSTEL_ROOMTYPE_UPDATE_FAILED = "knowledgepro.roomtype.update.failed";
	public static final String HOSTEL_ROOMTYPE_REQUIRED = "knowledgepro.hostel.roomtype.empty";
	public static final String HOSTEL_ROOMTYPE_ADD_COMPLETE = "knowledgepro.room.type.add.complete";
	public static final String HOSTEL_ROOMTYPE_IMAGE_DELETE_SUCCESS = "knowledgepro.roomtype.image.delete.success";
	public static final String HOSTEL_ROOMTYPE_IMAGE_DELETE_FAILED = "knowledgepro.roomtype.image.delete.failed";

	// Forwards for Hostel Leave
	public static final String HOSTEL_LEAVE = "hostelLeave";
	public static final String HOSTEL_LEAVE_SUCCESS = "knowledgepro.hostel.leaveAddSuccess";
	public static final String HOSTEL_LEAVE_FAILURE = "knowledgepro.hostel.leaveAddFailure";

	public static final String HOSTEL_GROUP = "hostelGroupEntry";
	public static final String HOSTEL_GROUP_EXIST_REACTIVATE = "knowledgepro.hostel.group.addfailure.alreadyexist.reactivate";
	public static final String HOSTEL_GROUP_ACTIVATE_FAILURE = "knowledgepro.hostel.group.activatefailure";
	public static final String HOSTEL_GROUP_ACTIVATE_SUCCESS = "knowledgepro.hostel.group.activatesuccess";
	public static final String HOSTEL_ATTENDANCE = "hostelAttendanceEntry";
	public static final String HOSTEL_ATTENDANCE_SUBMIT = "submitHostelAttendance";
	public static final String ENTRANCE_DETAILS_ENTRY = "entranceDet";

	public static final String ENTRANCE_DETAILS_ACTIVATE_FAILURE = "knowledgepro.entrance.details.activatefailure";
	public static final String ENTRANCE_DETAILS_ACTIVATE_SUCCESS = "knowledgepro.entrance.details.activatesuccess";
	public static final String ENTRANCE_DETAILS_EXIST_REACTIVATE = "knowledgepro.admin.entrance.details.addfailure.alreadyexist.reactivate";
	public static final String HOSTEL_ATT_DATE_LARGE = "hostel.att.date.large";

	// Keys for attendance check for a student
	public static final String GET_INDIVIDUAL_STUDENTATTENDANCE_ADMIN = "getIndividualStudentWiseAttendanceSummaryAdmin";
	public static final String GET_INDIVIDUAL_STUDENTATTENDANCE_ADMIN_RESULT = "getIndividualStudentWiseAttendanceSummaryResult";
	public static final String ATTENDANCE_NO_RESULTS_FOUND = "knowledgepro.admission.noresultsfound";
	public static final String KNOWLEDGEPRO_TOPBAR_DIMENSION = "knowledgepro.topbar.dimension.size";

	// keys for Complaints in hostel module.
	public static final String HOSTEL_COMPLAINTS = "complaints_leave";
	public static final String HOSTEL_COMPLAINTS_SUCCESS = "knowledgepro.hostel.complaints.success";
	public static final String HOSTEL_COMPLAINTS_FAILURE = "knowledgepro.hostel.complaints.failure";

	// Keys for Hostel Status
	public static final String KNOWLEDGEPRO_HOSTEL_STATUS_CHECKEDIN = "CheckedIn";

	// Keys for Stock Transfer
	public static final String INVENTORY_INIT_STOCK_TRANSFER = "initstocktransfer";
	public static final String INVENTORY_INIT_STOCK_TRANSFER_ITEM = "initstocktransferitem";
	public static final String INVENTORY_STOCK_TRANSFER_FUTURE_DATE = "knowledgepro.inventory.stocktransfer.transfer.future";
	public static final String STOCK_TRANSFER_NO_NOT_FOUND = "knowledgepro.inventory.transferno.not.found";
	public static final String STOCK_TRANSFER_SUCCESS = "knowledgepro.inventory.stock.transfer.success";
	public static final String STOCK_TRANSFER_BOTH_INVENTORY_EQUAL = "knowledgepro.inventory.stock.transfer.from.to.inventory.equal";
	public static final String STOCK_TRANSFER_ITEM_DUPLICATE = "knowledgepro.inventory.stock.transfer.item.duplicate";
	public static final String STOCK_TRANSFER_ITEM_DELETE_SUCCESS = "knowledgepro.inventory.stock.transfer.item.delete.success";
	public static final String STOCK_TRANSFER_ITEM_EMPTY = "knowledgepro.inventory.stock.transfer.transferitems.empty";
	public static final String STOCK_TRANSFER_STOCK_EMPTY = "knowledgepro.inventory.stock.transfer.stock.empty";
	public static final String STOCK_TRANSFER_STOCK_UNAVAILABLE_IN_ITEM_STOCK = "knowledgepro.inventory.stock.transfer.stock.unavailable";
	public static final String STOCK_TRANSFER_STOCK_AVAILABLE = "knowledgepro.inventory.stock.transfer.item.avialable";
	public static final String STOCK_TRANSFER_STOCK_UNAVAILABLE = "knowledgepro.inventory.stock.transfer.item.unavialable";
	// Constants for Inventory Counter

	// Constants for Item Master
	public static final String INVENTORY_ITEM = "initItemMaster";
	public static final String KNOWLEDGEPRO_ITEM_ADDSUCCESS = "knowledgepro.admin.item.addsuccess";
	public static final String KNOWLEDGEPRO_ITEM_ADDFAILURE = "knowledgepro.admin.item.addfailure";
	public static final String KNOWLEDGEPRO_ITEM_ADDEXIST = "knowledgepro.admin.item.addexist";
	public static final String KNOWLEDGEPRO_ITEM_REACTIVATE = "knowledgepro.admin.item.addfailure.alreadyexist.reactivate";
	public static final String KNOWLEDGEPRO_ITEM_EDITSUCCESS = "knowledgepro.admin.item.editsuccess";
	public static final String KNOWLEDGEPRO_ITEM_EDITFAILURE = "knowledgepro.admin.item.editfailure";
	public static final String KNOWLEDGEPRO_ITEM_DELETESUCCESS = "knowledgepro.admin.item.deletesuccess";
	public static final String KNOWLEDGEPRO_ITEM_DELETEFAILURE = "knowledgepro.admin.item.deletefailure";
	public static final String KNOWLEDGEPRO_ITEM_ACTIVATESUCCESS = "knowledgepro.item.activatesuccess";
	public static final String KNOWLEDGEPRO_ITEM_ACTIVATEFAILURE = "knowledgepro.item.activatefailure";

	// Constants for Inventory Request
	public static final String INVENTORY_REQUEST = "initInventoryRequest";
	public static final String INVENTORY_REQUEST_RESULT = "inventoryRequestResult";

	// Constants for Request Vs Issue
	public static final String REQUEST_VS_ISSUE = "initRequestIssue";
	public static final String REQUEST_VS_ISSUE_RESULT = "requestIssueResult";
	public static final String BOTH_DATES_REQUIRED = "knowledgepro.inventory.bothdatesrequired";

	// Constants for Cash Purchase
	public static final String CASH_PURCHASE = "initCashPurchase";
	public static final String KNOWLEDGEPRO_CASHPURCHASE_ADDSUCCESS = "knowledgepro.inventory.cashpurchase.addsuccess";
	public static final String KNOWLEDGEPRO_CASHPURCHASE_QUANTITY = "knowledgepro.inventory.cashpurchase.quantity";

	// Keys for Purchase order
	public static final String INIT_PURCHASE_ORDER_PAGE = "initPurchaseOrderPage";
	public static final String PURCHASE_ORDER_MAIN_PAGE = "purchaseOrderMainPage";
	public static final String PURCHASE_ORDER_CONFIRM_PAGE = "purchaseOrderConfirmPage";
	public static final String PURCHASEORDERDATE_INVALID = "inventory.purchaseorder.orderdt.invalid";

	public static final String PURCHASEORDER_TC_LARGE = "inventory.purchaseorder.t&c.length.large";
	public static final String PURCHASEORDER_REMARK_LARGE = "inventory.purchaseorder.remarks.length.large";
	public static final String PURCHASEORDER_SITEDELIVERY_LARGE = "inventory.purchaseorder.sitedelivery.length.large";
	public static final String PURCHASEORDER_TOTAL_COST_INVALID = "inventory.purchaseorder.totalcost.invalid";
	public static final String PURCHASEORDER_ADDNCOST_INVALID = "inventory.purchaseorder.addncost.invalid";
	public static final String PURCHASEORDER_SUBMIT_FAILURE = "inventory.purchaseorder.submit.failure";

	// Keys for Quotation
	public static final String INIT_QUOTATION_PAGE = "initQuotationPage";
	public static final String QUOTATION_MAIN_PAGE = "quotationMainPage";
	public static final String QUOTATION_CONFIRM_PAGE = "quotationConfirmPage";
	public static final String QUOTATION_SUBMIT_FAILURE = "inventory.quotation.submit.failure";
	public static final String QUOTATION_ITEMS_EMPTY = "inventory.quotation.items.empty";
	// Keys For Purchase Return
	public static final String INIT_PURCHASE_RETURN_PAGE = "initPurchaseReturnPage";
	public static final String PURCHASE_RETURN_MAIN_PAGE = "purchaseReturnMainPage";
	public static final String PURCHASE_RETURN_CONFIRM_PAGE = "purchaseReturnConfirmPage";
	public static final String PURCHASERETURNDATE_INVALID = "inventory.purchasereturn.rtndt.invalid";
	public static final String PURCHASERETURN_BILLDATE_INVALID = "inventory.purchasereturn.vnddt.invalid";
	public static final String PURCHASERETURN_BILLDATE_PAST = "inventory.purchasereturn.vnddt.past";
	public static final String PURCHASERETURN_RTNQTY_LARGE = "inventory.purchasereturn.rtnqty.large";
	public static final String PURCHASERETURN_RTNQTY_NOTALLOWED = "inventory.purchasereturn.rtnqty.max";
	public static final String PURCHASERETURN_RTNQTY_INVALID = "inventory.purchasereturn.rtnqty.invalid";
	public static final String PURCHASERETURN_REASON_LARGE = "inventory.purchasereturn.reason.large";
	public static final String PURCHASERETURN_SUBMIT_FAILURE = "inventory.purchasereturn.submit.failure";

	// Keys For Opening Balance
	public static final String INIT_OPENINGBALANCE_PAGE = "openingBalancePage";
	public static final String OPENINGBALANCE_CONFIRM_PAGE = "openingBalanceConfirmPage";
	public static final String OPENINGBALANCE_QUANTITY_INVALID = "inventory.purchaseorder.selectedItemQty.invalid";

	// Keys For Stock Receipt
	public static final String INIT_STOCK_RECEIPT_PAGE = "initStockReceiptPage";
	public static final String STOCK_RECEIPT_MAIN_PAGE = "stockReceiptMainPage";
	public static final String STOCK_RECEIPT_CONFIRM_PAGE = "stockReceiptConfirmPage";
	public static final String STOCK_RECEIPT_AMC_PAGE = "amcEntryPage";
	public static final String STOCK_RECEIPT_SUBMIT_FAILURE = "inventory.stockReceipt.submit.failure";
	public static final String OPENINGBALANCE_SUBMIT_FAILURE = "inventory.openingbalance.submit.failure";
	public static final String AMC_ITEMNO_REQUIRED = "inventory.stockReceipt.amc.itemNo.required";
	public static final String AMC_ITEMNO_INVALID = "inventory.stockReceipt.amc.itemNo.invalid";
	public static final String AMC_ITEMNO_DUPLICATE = "inventory.stockReceipt.amc.itemNo.duplicate";
	public static final String AMC_ITEMNO_PRESENT = "inventory.stockReceipt.amc.itemNo.present";
	public static final String AMC_STARTDATE_REQUIRED = "inventory.stockReceipt.amc.stDt.required";
	public static final String AMC_STARTDATE_INVALID = "inventory.stockReceipt.amc.stDt.invalid";
	public static final String AMC_ENDDATE_REQUIRED = "inventory.stockReceipt.amc.endDt.required";
	public static final String AMC_ENDDATE_INVALID = "inventory.stockReceipt.amc.endDt.invalid";
	public static final String STOCK_RECEIPT_DATE_INVALID = "inventory.stockReceipt.receiptDate.invalid";
	public static final String STOCK_RECEIPT_ITEMPRICE_INVALID = "inventory.stockReceipt.rcvPchPrc.invalid";
	public static final String STOCK_RECEIPT_ITEMPRICE_LARGE = "inventory.stockReceipt.rcvPchPrc.large";

	public static final String STOCK_RECEIPT_ITEMQTY_INVALID = "inventory.stockReceipt.itemQty.invalid";
	public static final String STOCK_RECEIPT_ITEMQTY_LARGE = "inventory.stockReceipt.itemQty.large";
	public static final String STOCK_RECEIPT_RCVQTY_NOTALLOWED = "inventory.stockreceipt.rcvqty.max";

	// Keys For Stock Report
	public static final String INIT_STOCK_REPORT_PAGE = "initStockReport";
	public static final String STOCK_REPORT_PAGE = "stockReportPage";
	public static final String STOCK_DETAIL_TXN__PAGE = "stockTxnDetailPage";
	public static final String STOCK_REPORT_DATE_INVALID = "inventory.stockreport.date.invalid";
	public static final String STOCK_REPORT_LOCATION_INVALID = "inventory.stockreport.location.invalid";

	// Keys For Stock Report Details
	public static final String INIT_STOCK_REPORT_DETAIL = "initStockReportDetail";
	public static final String STOCK_REPORT_DETAILS = "stockReportDetailPage";

	public static final String VENDOR_ENTRY = "vendorEntry";
	public static final String VIEW_VENDOR = "viewVendor";
	public static final String VENDOR_MASTER_ACTIVATE_FAILURE = "knowledgepro.vendor.master.activatefailure";
	public static final String VENDOR_MASTER_ACTIVATE_SUCCESS = "knowledgepro.vendor.master.activate.success";
	public static final String VENDOR_MASTER_EXIST_REACTIVATE = "knowledgepro.vendor.master.addfailure.alreadyexist.reactivate";

	// keys for Salvage Item
	public static final String DISPLAY_SALVAGE = "display_salvageItem";
	public static final String SALVAGE_SUCCESS = "knowledgepro.inventory.salvageitem.success";
	public static final String ITEM_REQUIRE = "knowledgepro.inventory.item.required";
	public static final String DATE_REQUIRE = "knowledgepro.inventory.date.required";
	public static final String REMARKS_REQUIRE = "knowledgepro.inventory.remarks.required";
	public static final String DATE_INVALID = "admissionFormForm.applicationDate.invalid";
	public static final String INVENTORY_LOCATION_REQUIRED = "knowledgepro.admin.InvLocation.required";
	public static final String INVENTORY_LOCTION_ITEM_REQUIRE = "knowledgepro.inventory.locationoritem.require";

	// Keys for Item Issue
	public static final String DISPLAY_ITEM_ISSUE = "display_item_issue";
	public static final String ISSUED_TO_REQUIRE = "knowledgepro.inventory.itemissue.issuedto";
	public static final String ITEM_ISSUE_SUCCESS = "knowledgepro.inventory.itemissue.success";

	// Keys for SalvageReport
	public static final String INIT_SALVAGE_REPORT = "initSalvageReport";
	public static final String SALVAGE_REPORT_RESULT = "salvageReportResult";

	public static final String COUNTER_MASTER = "counterMaster";
	public static final String COUNTER_MASTER_ACTIVATE_FAILURE = "knowledgepro.counter.master.activatefailure";
	public static final String COUNTER_MASTER_ACTIVATE_SUCCESS = "knowledgepro.counter.master.activate.success";
	public static final String COUNTER_MASTER_EXIST_REACTIVATE = "knowledgepro.counter.master.addfailure.alreadyexist.reactivate";

	// Constant for Issue for the Material
	public static final String INVENTORY_STATUS_APPROVE = "Approved";
	public static final String INVENTORY_INIT_ISSUE_MATERIAL = "initIssueMaterial";
	public static final String INVENTORY_ISSUE_DETAILS = "issueDetails";
	public static final String INVENTORY_ISSUE_SUCCESS = "knowledgepro.inventory.issued.success";
	public static final String INVENTORY_ISSUE_QUANTITY_INTEGER = "knowledgepro.inventory.issuedquantity.integer";
	public static final String INVENTORY_ISSUE_QUANTITY_GREATER = "knowledgepro.inventory.issuedquantity.greater";
	public static final String INVENTORY_ISSUE_QUANTITY_SELECT_ANY = "knowledgepro.inventory.issuedquantity.selectany";
	public static final String INVENTORY_ISSUE_ITEM_QUANTITY_EXCEEDS = "knowledgepro.inventory.issued.item.quantity.exceeds";

	public static final String INVENTORY_VENDERWISE_PO = "initVendorwisePO";
	public static final String INVENTORY_VENDERWISE_PO_RESULT = "vendorwiseresult";
	public static final String INVENTORY_VENDERWISE_PO_DETAILS = "vendorwisedetails";;
	public static final String INVENTORY_VENDERWISE_PO_RETURNS = "venderwiseporeturns";
	public static final String INVENTORY_VENDERWISE_PO_RETURNS_RESULT = "venderwiseporeturnsresult";
	public static final String INVENTORY_VENDERWISE_PO_RETURNS_DETAILS = "venderwiseporeturnsdetails";

	public static final String AMC_DETAILS_ENTRY = "amcDetailsSearch";
	public static final String SUBMIT_AMC_DETAILS_ENTRY = "amcDetailsSubmit";
	public static final String VIEW_AMC_HISTORY = "viewHistory";
	public static final String AMC_DUE_REPORT = "amcDuewReport";
	public static final String SUBMIT_AMC_DUE_REPORT = "amcDuewReportSubmit";
	public static final String AMC_WARRANTY_DUE_MAIL_SEARCH = "amcWarrantyDueMailSearch";
	public static final String AMC_WARRANTY_DUE_MAIL = "amcWarrantyDueMail";

	public static final String WARRANTY_ITEMS_REPORT_SEARCH = "warrantyItemReportSearch";
	public static final String WARRANTY_ITEMS_REPORT_SUBMIT = "warrantyItemReportSubmit";

	public static final String QUOTATION_REPORT_SEARCH = "quotationReportSearch";
	public static final String QUOTATION_REPORT_SUBMIT = "quotationReportSubmit";
	public static final String QUOTATION_ITEM_VIEW = "quotationItemView";
	public static final String VENDOR_FAX_INVALID = "vendor.fax.Invalid";

	public static final String UPDATE_WARRANTY = "initupdateWarranty";
	public static final String SUBMIT_UPDATE_WARRANTY = "updateWarrantySearch";
	public static final String UPDATE_WARRANTY_AMC_PAGE = "updateWarrantyAmc";

	// Keys For Employee Manual Attendance Entry
	public static final String INIT_EMPLOYEE_MANUAL_ATTENDANCE_ENTRY = "initEmployeeAttendanceEntry";
	public static final String EMPLOYEE_MANUAL_ATTENDANCE_ENTRY = "employeeManualAttendanceEntry";
	public static final String EMPLOYEE_MANUAL_ATTENDANCE_TAKEN = "knowledgepro.employee.attendance.attendencetaken";

	// Keys for Principal Appraisal
	public static final String INIT_PRINCIPAL_APPRAISAL_PAGE = "initprincipalappraisal";
	public static final String PRINCIAPL_APPRAISAL_COMPLETED_SUCCESS = "knowledgepro.employee.principalappraisal.success";
	public static final String PRINCIAPL_APPRAISAL_COMPLETED_FAILED = "knowledgepro.employee.principalappraisal.failure";
	public static final String PRINCIAPL_APPRAISAL_ATTRIBUTE_VALUE_REQUIRED = "knowledgepro.employee.attribute.value.required";
	public static final String PRINCIAPL_APPRAISAL_ATTRIBUTE_EMPTY = "knowledgepro.employee.attribute.empty";
	public static final String INIT_HOD_APPRAISAL_PAGE = "inithodappraisal";
	public static final String INIT_STUDENT_APPRAISAL_PAGE = "initstudentappraisal";

	// Keys For Employee Info
	public static final String INIT_EMPLOYEE_INFO_PAGE = "initEmployeeInfoPage";
	public static final String EMPLOYEE_CONTACT_PAGE = "forwardEmplContactPge";
	public static final String EMPLOYEE_DEPENDENT_PAGE = "forwardEmpldependentPge";
	public static final String EMPLOYEE_EMERG_CONT_PAGE = "forwardEmplemergContPge";
	public static final String EMPLOYEE_EXPERIENCE_PAGE = "forwardEmplExperiencePge";
	public static final String EMPLOYEE_IMMIGRATION_PAGE = "forwardEmplImmigrationPge";
	public static final String EMPLOYEE_EDUCATION_PAGE = "forwardEmplEducationPge";
	public static final String EMPLOYEE_JOB_PAGE = "forwardEmplJobPge";
	public static final String EMPLOYEE_LANGUAGE_PAGE = "forwardEmplLangugaePage";
	public static final String EMPLOYEE_ACHIEVEMENT_PAGE = "forwardEmplAchievePge";
	public static final String EMPLOYEE_SKILLS_PAGE = "forwardEmplSkillsPage";
	public static final String EMPLOYEE_REPORTTO_PAGE = "forwardEmplReportToPage";
	public static final String EMPLOYEE_PERSONAL_SUBMIT_SUCCESS = "employee.info.personalinfo.update.success";
	public static final String EMPLOYEE_CONTACT_SUBMIT_SUCCESS = "employee.info.contact.update.success";
	public static final String EMPLOYEE_EMERGENCY_SUBMIT_SUCCESS = "employee.info.emergency.update.success";
	public static final String EMPLOYEE_SKILL_SUBMIT_SUCCESS = "employee.info.skill.update.success";
	public static final String EMPLOYEE_IMMIGRATION_SUBMIT_SUCCESS = "employee.info.immigration.update.success";
	public static final String EMPLOYEE_EDUCATION_SUBMIT_SUCCESS = "employee.info.education.update.success";
	public static final String EMPLOYEE_EXPERIENCE_SUBMIT_SUCCESS = "employee.info.experience.update.success";
	public static final String EMPLOYEE_LANGUAGE_SUBMIT_SUCCESS = "employee.info.language.update.success";
	public static final String EMPLOYEE_JOB_SUBMIT_SUCCESS = "employee.info.job.update.success";
	public static final String EMPLOYEE_DEPENDENT_SUBMIT_SUCCESS = "employee.info.dependent.update.success";
	public static final String EMPLOYEE_REPORTTO_SUBMIT_SUCCESS = "employee.info.reportto.update.success";

	public static final String EMPLOYEE_EDUCATION_GPA_INVALID = "employee.info.education.gpa.invalid";
	public static final String EMPLOYEE_EDUCATION_YEAR_INVALID = "employee.info.education.year.invalid";
	public static final String EMPLOYEE_EDUCATION_STDT_INVALID = "employee.info.education.startdate.invalid";
	public static final String EMPLOYEE_EDUCATION_ENDDT_INVALID = "employee.info.education.enddate.invalid";
	public static final String EMPLOYEE_EDUCATION_STDT_ENDDT_LARGE = "employee.info.education.startdate.enddate.large";
	public static final String EMPLOYEE_EDUCATION_STDT_ENDDT_SAME = "employee.info.education.startdate.enddate.same";

	public static final String EMPLOYEE_IMMIGRATION_PASSPORT_ISSUEDT_INVALID = "employee.info.immigration.passportissuedate.invalid";
	public static final String EMPLOYEE_IMMIGRATION_PASSPORT_EXPDT_INVALID = "employee.info.immigration.passportexpdate.invalid";
	public static final String EMPLOYEE_IMMIGRATION_PISSUEDT_PEXPDT_LARGE = "employee.info.immigration.pexpdate.pissuedate.large";
	public static final String EMPLOYEE_IMMIGRATION_PISSUEDT_PEXPDT_SAME = "employee.info.immigration.pexpdate.pissuedate.same";
	public static final String EMPLOYEE_IMMIGRATION_VISA_ISSUEDT_INVALID = "employee.info.immigration.visaissuedate.invalid";
	public static final String EMPLOYEE_IMMIGRATION_VISA_EXPDT_INVALID = "employee.info.immigration.visaexpdate.invalid";

	public static final String EMPLOYEE_DEPENDENT_CHILD1_DOB_INVALID = "employee.info.dependent.child1.dob.invalid";
	public static final String EMPLOYEE_DEPENDENT_CHILD2_DOB_INVALID = "employee.info.dependent.child2.dob.invalid";

	public static final String EMPLOYEE_JOB_JOINDT_INVALID = "employee.info.job.joindate.invalid";
	public static final String EMPLOYEE_JOB_RETIREDT_INVALID = "employee.info.job.retiredate.invalid";
	public static final String EMPLOYEE_JOB_JOINDT_RETIREDT_LARGE = "employee.info.job.joindate.retiredate.large";
	public static final String EMPLOYEE_JOB_JOINDT_RETIREDT_SAME = "employee.info.job.joindate.retiredate.same";
	public static final String EMPLOYEE_JOB_RESIGNDT_INVALID = "employee.info.job.resigndate.invalid";
	public static final String EMPLOYEE_JOB_LEAVEDT_INVALID = "employee.info.job.leavedate.invalid";
	public static final String EMPLOYEE_JOB_BASIC_INVALID = "employee.info.job.basicpay.invalid";
	public static final String EMPLOYEE_JOB_GROSS_INVALID = "employee.info.job.grosspay.invalid";
	public static final String EMPLOYEE_JOB_FINANCE_LARGE = "employee.info.job.finance.large";
	public static final String EMPLOYEE_JOB_ALLOWANCE_AMOUNT_INVALID = "employee.info.job.allowance.amount.invalid";
	public static final String EMPLOYEE_JOB_LEAVE_ALLOCATED_INVALID = "employee.info.job.leave.allocated.invalid";
	public static final String EMPLOYEE_JOB_LEAVE_REMAINING_INVALID = "employee.info.job.leave.remaining.invalid";
	public static final String EMPLOYEE_JOB_LEAVE_SANCTIONED_INVALID = "employee.info.job.leave.sanctioned.invalid";
	public static final String EMPLOYEE_JOB_LEAVE_ALLOW_REMAIN_LARGE = "employee.info.job.leave.allowd.remain.large";
	public static final String EMPLOYEE_JOB_LEAVE_ALLOW_SANCTION_LARGE = "employee.info.job.leave.allowd.sanction.large";

	public static final String EDUCATION_MASTER = "educationMaster";
	public static final String EDUCATION_MASTER_EXIST_REACTIVATE = "knowledgepro.employee.master.addfailure.alreadyexist.reactivate";
	public static final String EDUCATION_MASTER_ACTIVATE_SUCCESS = "knowledgepro.employee.education.master.activate.success";
	public static final String EDUCATION_MASTER_ACTIVATE_FAILURE = "knowledgepro.employee.education.master.activatefailure";

	public static final String ATTRIBUTE_MASTER = "attributeMaster";
	public static final String ATTRIBUTE_MASTER_EXIST_REACTIVATE = "knowledgepro.employee.attribute.master.addfailure.alreadyexist.reactivate";
	public static final String ATTRIBUTE_MASTER_ACTIVATE_FAILURE = "knowledgepro.employee.attribute.master.activatefailure";
	public static final String ATTRIBUTE_MASTER_ACTIVATE_SUCCESS = "knowledgepro.employee.attribute.master.activate.success";

	// Keys for HR Policy Master
	public static final String POLICY_ADD_SUCCESS = "knowledgepro.employee.hrpolicy.addsuccess";
	public static final String POLICY_DELETE_SUCCESS = "knowledgepro.employee.hrpolicy.deletesuccess";
	public static final String POLICY_PATH = "initHRPolicy";
	public static final String POLICIES_LIST_PATH = "policiesListPath";
	public static final String WORK_TIME_ENTRY = "workTimeEntry";

	// Returns for Employee Search
	public static final String EMP_SEARCH = "emp_search";
	public static final String EMP_SEARCH_RESULT = "emp_search_result";

	public static final String INV_LOCATION = "invLocationEntry";
	public static final String INV_LOCATION_EXIST_REACTIVATE = "knowledgepro.inventory.location.addfailure.alreadyexist.reactivate";
	public static final String INV_LOCATION_ACTIVATE_SUCCESS = "knowledgepro.inventory.location.activate.success";
	public static final String INV_LOCATION_ACTIVATE_FAILURE = "knowledgepro.inventory.location.activatefailure";

	// Keys for student upload photos
	public static final String UPLOAD_PHOTOS = "uploadStudentPhotos";

	// columns for Send Data For Id Card

	public static final String COURSE_CODE = "Course Code";
	public static final String SEMESTER_NAME = "Semister Name";
	public static final String PATH_FOR_ID_CARD = "knowledgepro.reports.senddataforIdcard";
	public static final String ID_DATA_ATTR = "SendDataForID";
	public static final String EXPORT_SEARCH = "export_search";
	public static final String EXPORT_DATA_DOWNLOAD = "export_search_download";
	public static final String EXPORT_SEARCH_RES = "export_search_result";
	public static final String EXPORT_RECORDS_REQUIRE = "knowledgepro.admission.selectcandidate";

	public static final String ATTENDANCE_ABSENCE_PERIOD_DETAILS = "absenceperioddetails";
	public static final String ATTENDANCE_ABSENCE_PERIOD_DETAILS_AM = "absenceperioddetailsam";
	public static final String ATTENDANCE_ABSENCE_PERIOD_DETAILS_PM = "absenceperioddetailspm";
	public static final String ATTENDANCE_ABSENCE_PERIOD_DETAILS_AM1 = "absenceperioddetailsam1";
	public static final String ATTENDANCE_ABSENCE_PERIOD_DETAILS_PM1 = "absenceperioddetailspm1";
	
	public static final String WORK_DIARY_FIRST_PAGE = "workDiaryFirst";
	public static final String WORK_DIARY_SEC_PAGE = "workDiarySecond";
	public static final String ACTIVITY_ATTENDANCE_SUMMARY = "getIndividualStudentWiseActivityAttendanceSummary";

	public static final String INIT_CERTIFICATE_DUE = "initcertificatedue";
	public static final String CERTIFICATE_DUE_RESULT = "certificateduelist";

	// forwards for student category wise report
	public static final String INIT_STUDENT_CATEGORY_REPORT = "initStudentCategoryReport";
	public static final String STUDENT_CATEGORY_REPORT = "studentCategoryReport";

	public static final String CASHPURCHASE_REPORT = "cashpurchReport";
	public static final String CASHPURCHASE_REPORT_RESULT = "cashpurchReportResult";
	public static final String INIT_FRESHERS_LIST = "initfresherslist";
	public static final String FRESHERS_LIST_RESULT = "freshersListResult";

	// Forwards for Candidates Recommended By
	public static final String RECOMMENDED_CANDIDATES_INPUT = "initRecommended";
	public static final String RECOMMENDED_CANDIDATES_OUTPUT = "submitRecommendedBy";
	public static final String INIT_INCOMPLETE_ADMISSION = "initadmissionincomplete";
	public static final String INCOMPLETE_ADMISSION_RESULT = "admissionincompleteresult";
	// Keys for Score Sheet Report
	public static final String SCORE_SHEET_INPUT = "scoreSheetReport";
	public static final String SCORE_SHEET_RESULT = "scoreSheetResult";
	public static final String SCORE_SHEET_Print = "printScoreSheetResult";

	public static final String DEG_STUD_LIST_REPORT_INPUT = "deglistStudentInput";
	public static final String DEG_STUD_LIST_REPORT_RESULT = "deglistStudentResult";

	public static final String CASTE_GENERAL = "knowledgepro.caste.general";
	public static final String RESIDENT_CATEGORY_NRI = "knowledgepro.nri.student.category.name";

	public static final String STUD_LIST_FOR_SIGN_SEARCH = "studListForSignSearch";
	public static final String STUD_LIST_FOR_SIGN_RESULT = "studListForSignResult";
	public static final String STUD_LIST_FOR_SIGN_PRINT = "studListForSignPrint";
	// Constants for SecondLanguage/Admitted Report
	public static final String INIT_SECOND_LANGUAGE = "initsecondlanguage";
	public static final String SECOND_LANGUAGE_RESULT = "secondlanguageresult";

	public static final String INIT_APPLICATION_RECEIVED = "initApplicationReceived";
	public static final String APPLICATION_RECEIVED_RESULT = "applicationReceivedResult";

	public static final String PRINT_REG_NO = "printRegNo";
	public static final String SHOW_REG_NO_ROLL_NO = "showRegNoRollNo";
	public static final String NO_RECORD_REG_ROLL = "noRecordReg";

	// Constants for offline student Report
	public static final String OFFLINE_Report = "initofflineStudentsReport";
	public static final String OFFLINE_Report_Result = "ListOfOfflineReport";

	public static final String INTERVIEW_SELECTION_INTERVIEWTYPE_REQUIRED = "knowledgepro.admission.interviewtype.required";
	public static final String LIST_ROLL_REG_NO = "listRollRegNo";
	public static final String SHOW_LIST_ROLL_REG_NO = "showListRollRegNo";
	// Constants for Students For University
	public static final String INIT_STUDENT_UNIVERSITY = "initStudentsForUniversity";
	public static final String STUDENT_UNIVERSITY_Report = "StudentsForUniversityReportResult";

	// Constants for second language abstract
	public static final String INIT_SEC_LAN_ABSTRACT = "initSecLanAbstract";
	public static final String SHOW_SEC_LAN_ABSTRACT = "showSecLanAbstract";
	// forwards for Address report
	public static final String STUDENT_ADDRESS_REPORT = "initAddressReport";
	public static final String STUDENT_ADDRESS_REPORT_PRINT = "printAddressReport";

	// constants for student list report
	public static final String CLASSWISE_STUDENT_LIST_SEARCH = "classStudentListSearch";
	public static final String CLASSWISE_STUDENT_LIST_RESULT = "classStudentListResult";

	public static final String ONLINE_RECEIVED_APPLICATIONS = "Online Received Application List";
	public static final String OFFLINE_RECEIVED_APPLICATIONS = "Offline Received Application List";

	// concession slip books entry
	public static final String CONCESSION_SLIP_BOOKS = "slipBooks";
	public static final String CONCESSION_SLIP_BOOKS_ACTIVATE_FAILURE = "knowledgepro.fee.concession.slip.activatefailure";
	public static final String CONCESSION_SLIP_BOOKS_ACTIVATE_SUCCESS = "knowledgepro.fee.concession.slip.activate.success";
	public static final String CONCESSION_SLIP_BOOKS_EXIST_REACTIVATE = "knowledgepro.fee.concession.slip.addfailure.alreadyexist.reactivate";

	// FEE ASSIGNMENT VALIDATION
	public static final String FEEASSIGNMENT_CURRENCY_REQUIRED = "feeassignment.currency.required";

	public static final String INIT_STUDENTS_COLLECTION_REPORT = "initStudentReport";
	public static final String SUBMIT_STUDENT_COLLECTION_REPORT = "submitStudentReport";
	public static final String STUDENT_COLLECTION_REPORT = "studentCollectionReport";
	public static final String STUDENT_COLLECTION_REPORT_STARTDATE_INVALID = "knowledgepro.petticash.startdateinvalid";
	public static final String STUDENT_COLLECTION_REPORT_ENDDATE_INVALID = "knowledgepro.petticash.enddateinvalid";
	public static final String LAST_RECEIPT_NUMBER_DISPLAY = "displayLastReceiptNumber";
	public static final String DISPLAY_CASH_COLLECTION = "displayCashCollection";
	public static final String INIT_MODIFY_CASH_COLLECTION = "initModifyCashCollection";
	public static final String PETTICASH_CASHCOLLECTION_DELETE_SUCCESS = "knowledgepro.petticash.receiptNumber.deleted";
	public static final String PETTICASH_CASHCOLLECTION_DELETE_FAILED = "knowledgepro.petticash.delete.failed";
	// fee challan cancel
	public static final String FEE_PAYS_FEECHALLANSEARCH = "feeChallanSearch";
	// Constants for Installment Payment
	public static final String FEE_INSTALLMENT_APPLICATION_NO_INTEGER = "knowledgepro.fee.installment.applicationNo.integer";
	public static final String FEE_INSTALLMENT_PAYMENT = "initinstallmentpayment";
	public static final String FEE_INSTALLMENT_PAYMENT_DETAILS = "installmentpaymentdetails";
	public static final String FEE_INSTALLMENT_PAYMENT_SUCCESS = "knowledgepro.fee.installment.success";
	public static final String FEE_INSTALLMENT_PAYMENT_FAILURE = "knowledgepro.fee.installment.failure";
	public static final String FEE_INSTALLMENT_PAYMENT_DATE = "knowledgepro.fee.installment.date";
	public static final String FEE_INSTALLMENT_PAYMENT_TYPE = "knowledgepro.fee.installment.payment.type";
	public static final String FEE_INSTALLMENT_FINANCIAL_YEAR = "knowledgepro.fee.installment.financlial.year";
	public static final String FEE_INSTALLMENT_REFERENCE_NO = "knowledgepro.fee.installment.reference.no";
	public static final String FEE_INSTALLMENT_REFERENCE_NO_ALPHANUMERIC = "knowledgepro.fee.installment.reference.no.alphanumeric";
	public static final String FEE_INSTALLMENT_TOTAL_AMOUNT = "knowledgepro.fee.installment.totalamount";
	public static final String FEE_INSTALLMENT_TOTAL_AMOUNT_INTEGER = "knowledgepro.fee.installment.totalamount.integer";
	public static final String FEE_INSTALLMENT_TOTAL_AMOUNT_GREATER = "knowledgepro.fee.installment.totalamount.greater";
	public static final String FEE_INSTALLMENT_PAID_AMOUNT = "knowledgepro.fee.installment.paidamount";
	public static final String FEE_INSTALLMENT_PAID_AMOUNT_INTEGER = "knowledgepro.fee.installment.paidamount.integer";
	public static final String FEE_INSTALLMENT_PAID_AMOUNT_GREATER = "knowledgepro.fee.installment.paidamount.greater";
	public static final String FEE_INSTALLMENT_TOTAL_AMOUNT_INVALID = "knowledgepro.fee.installment.totalamount.invalid";
	public static final String FEE_PAYMENT_SINGLE_PRINTCHALLAN = "singlePrintChallen";
	public static final String PETTICASH_CASHCOLLECTION_SAVE_SUCCESS = "knowledgepro.petticash.save.success";
	public static final String PETTICASH_CASHCOLLECTION_SAVE_FAILED = "knowledgepro.petticash.save.failed";
	public static final String LASTRCEIPTNUMBER_EXIST_REACTIVATE = "knowledgepro.petticash.alreadyexist.reactivate ";
	public static final String INIT_CONSOLIDATED_COLLECTION_REPORT = "initConsolidatedReport";
	public static final String SUBMIT_CONSOLIDATED_COLLECTION_REPORT = "submitConsolidatedReport";
	public static final String CONSOLIDATED_COLLECTION_REPORT = "consolidatedCollectionReport";
	public static final String GET_CASH_COLLECTION_FOR_EDIT = "submitModifyCashCollection";
	public static final String PETTYCASH_CASHCOLLECTION_UPDATE_SUCCESS = "knowledgepro.pettycash.receiptNumber.updated";
	public static final String PETTYCASH_CASHCOLLECTION_UPDATE_FAILED = "knowledgepro.pettycash.receiptNumber.failed";
	public static final String GET_CONSOLIDATED_COLLECTION_REPORT = "getConsolidatedReport";
	public static final String PETTYCASH_THERE_IS_NO_ACTIVE_RECORDS = "knowledgepro.pettycash.no.active.record";
	public static final String PETTYCASH_NAME_CODE_EMPTY = "knowledgepro.pettycash.accountCodeandNamelistempty";
	public static final String PETTICASH_SELECT_ACCOUNTCODE = "knowledgepro.pettycash.amountfeildEmty";
	public static final String PETTICASH_NODTAILS_SELECTED = "knowledgepro.pettycash.nodetailsselected";
	public static final String PETTYCASH_STUDENT_NAME_REQUIRED = "knowledgepro.pettycash.namerequired";
	public static final String PETTYCASH_RECEIPTNUMBERNOTVALID = "knowledgepro.pettycash.numbeNotvalid";
	public static final String PETTYCASH_NOT_VALID_APPROLLREGNO = "knowledgepro.pettycash.notvalidappregrollno";

	public static final String INIT_COLLECTION_LEDGER = "initCollectionLedger";
	public static final String PC_ACCOUNT_REQUIRED = "knowledgepro.petticash.collectionLedger.account.Required";
	public static final String PC_ACCOUNT_CODE_REQUIRED = "knowledgepro.petticash.collectionLedger.accountCode.Required";
	public static final String PC_USERTYPE_REQUIRED = "knowledgepro.petticash.collectionLedger.userType.Required";
	public static final String PC_OTHER_NAME_REQUIRED = "knowlegepro.petticash.collectionLedger.OtherName.Required";
	public static final String PC_USER_NAME_NOT_VALID = "knowlegepro.petticash.collectionLedger.OtherName.NotValid";
	public static final String PC_GROUP_CODE_NOT_VALID = "knowlegepro.petticash.collectionLedger.groupCode.NotValid";
	public static final String COLLECTION_LEDGER_REPORT = "collectionLedgerResult";
	public static final String PETTYCASH_NOVALIDDATA = "knowledgepro.pettycash.novaliddata";
	public static final String PETTICASH_TRYING_ALREADY_EXISTING = "knowledgepro.pettycash_accountdetails_already_exists";
	public static final String PETTICASH_NOAMOUNT = "knowledgepro.pettycash.noamount";

	public static final String PC_ACCOUNT_NO_REQUIRED = "knowledgepro.pettycash.accNo.Required";
	public static final String KNOWLEDGEPRO_FEE_DESCRIPTION1_CONTENTFAIL = "knowledgepro.fee.description1.contentFail";
	public static final String KNOWLEDGEPRO_BANK_INFORMATION_CONTENTFAIL = "knowledgepro.fee.bankInfo.contentFail";
	public static final String KNOWLEDGEPRO_FEE_DESCRIPTION2_CONTENTFAIL = "knowledgepro.fee.description2.contentFail";
	public static final String COLLECTIONS_FOR_THE_DAY_OTHERNAME = "knowledgepro.pettycash.otherName.required";
	public static final String collections_Print = "collectionsPrint";
	// Admin message
	public static final String INIT_HOSTEL_ADMN_MESSAGE = "initHostelAdminMessage";
	public static final String GET_LEAVE_STATUS_LIST = "getLeaveStatusList";
	public static final String GET_HOSTEL_ADMNMESSAGE_LIST = "getHostelAdminMessageList";
	public static final String INIT_HOSTEL_STUDENT_VIEW_MESSAGE = "initHostelStudentViewMessage";
	public static final String GET_STUDENT_VIEW_MESSAGE_LIST = "getStudentViewMessageList";
	public static final String GET_STUDENT_LEAVE_STATUS = "getStudentLeaveStatus";

	// ------------------------Admin --Academic year
	public static final String INIT_ACADEMIC_YEAR = "initAcademicYear";
	public static final String ADMIN_ACADEMIC_YEAR_EXISTS = "knowledgepro.admin.academicyear.exists";
	public static final String ADMIN_ACADEMIC_YEAR_REACTIVATE = "knowledgepro.admin.academicyear.exists.reactivate";
	public static final String ADMIN_ACADEMIC_YEAR_MORETHAN_ONE = "knowledgepro.admin.academicyear.morethanone";
	public static final String ADMIN_ACADEMIC_YEAR_ADD_SUCCESS = "knowledgepro.admin.academicyear.added";
	public static final String ADMIN_ACADEMIC_YEAR_ADD_FAILED = "knowledgepro.admin.academicyear.add.failed";
	public static final String ADMIN_ACADEMIC_YEAR_IS_ALREADY_CURRENT = "knowledgepro.admin.academicyear..alreadycurrent";
	public static final String ADMIN_ACADEMIC_YEAR_UPDATE_SUCCESS = "knowledgepro.admin.academicyear.updated";
	public static final String ADMIN_ACADEMIC_YEAR_UPDATE_FAILED = "knowledgepro.admin.academicyear.failed";
	public static final String ADMIN_ACADEMIC_YEAR_DELETE_SUCCESS = "knowledgepro.admin.academicyear.deleted";
	public static final String ADMIN_ACADEMIC_YEAR_DELETE_FAILED = "knowledgepro.admin.academicyear.deleted.failed";
	public static final String ADMIN_ACADEMIC_YEAR_REACTIVATE_SUCCESS = "knowledgepro.admin.academicyear.reactivated";
	public static final String ADMIN_ACADEMIC_YEAR_REACTIVATE_FAILED = "knowledgepro.admin.academicyear.reactivated.failed";
	// fee financial year entries
	public static final String FEE_FINANCIAL_YEAR_EXISTS = "knowledgepro.fee.financialyear.exists";
	public static final String FEE_FINANCIAL_YEAR_REACTIVATE = "knowledgepro.fee.financialyear.exists.reactivate";
	public static final String FEE_FINANCIAL_YEAR_ADD_SUCCESS = "knowledgepro.fee.financialyear.added";
	public static final String FEE_FINANCIAL_YEAR_ADD_FAILED = "knowledgepro.fee.financialyear.add.failed";
	public static final String FEE_FINANCIAL_YEAR_UPDATE_SUCCESS = "knowledgepro.fee.financialyear.updated";
	public static final String FEE_FINANCIAL_YEAR_UPDATE_FAILED = "knowledgepro.fee.financialyear.update.failed";
	public static final String FEE_FINANCIAL_YEAR_DELETE_SUCCESS = "knowledgepro.fee.financialyear.deleted";
	public static final String FEE_FINANCIAL_YEAR_DELETE_FAILED = "knowledgepro.fee.financialyear.delete.failed";
	public static final String FEE_FINANCIAL_YEAR_REACTIVATE_SUCCESS = "knowledgepro.fee.financialyear.reactivated";
	public static final String FEE_FINANCIAL_YEAR_REACTIVATE_FAILED = "knowledgepro.fee.financialyear.reactivate.failed";
	public static final String FEE_FINANCIAL_YEAR_NOT_CURRENT = "knowledgepro.fee.financialyear.notcurrent";
	public static final String FEE_FINANCIAL_YEAR_ALREADY_STORED = "knowledgepro.fee.financialyear.alreadystored";
	public static final String FEE_FINANCIAL_YEAR_MORETHAN_ONE = "knowledgepro.fee.financialyear.morethanone";
	public static final String FEE_FINANCIAL_YEAR_ENTRY = "feeFinancialYearEntry";
	public static final String FEE_FINANCIAL_YEAR_IS_ALREADY_CURRENT = "knowledgepro.fee.financial.year.alreadycurrent";

	public static final String PETTYCASH_RECEIPTNUMBER_REACTIVATE = "knowledgepro.pettycash.lastreceiptnumber.exists.reactivate";
	public static final String PETTYCASH_RECEIPTNUMBER_EXISTS = "knowledgepro.pettycash.receiptno.exists";
	public static final String PETTYCASH_RECEIPTNUMBER_ADD_SUCCESS = "knowledgepro.pettycash.receiptno.added";
	public static final String PETTYCASH_RECEIPTNUMBER_ADD_FAILED = "knowledgepro.pettycash.receiptno.failed";
	public static final String PETTYCASH_RECEIPTNUMBER_DELETE_SUCCESS = "knowledgepro.pettycash.receiptno.deleted";
	public static final String PETTYCASH_RECEIPTNUMBER_DELETE_FAILED = "knowledgepro.pettycash.receiptno.delete.failed";

	public static final String PETTYCASH_RECEIPTNUMBER_REACTIVATE_SUCCESS = "knowledgepro.pettycash.receiptno.reactivated";
	public static final String PETTYCASH_RECEIPTNUMBER_REACTIVATE_FAILED = "knowledgepro.pettycash.receiptno.reactivate.failed";
	public static final String PETTYCASH_RECEIPTNUMBER_UPDATE_SUCCESS = "knowledgepro.pettycash.receiptno.updated";
	public static final String PETTYCASH_RECEIPTNUMBER_UPDATE_FAILED = "knowledgepro.pettycash.receiptno.update.failed";

	public static final String PETTYCASH_REPRINTRECEIPT = "initReprintReceipt";
	public static final String PETTYCASH_NORECORD = "knowledgepro.pettycash.norecord";
	public static final String PRINT_RECEIPT = "printReceipt";
	public static final String COLLECTIONS_FOR_THE_DAY = "collectionsfortheday";
	public static final String COLLECTIONS_FOR_THE_DAY_SHOW = "showcollections";

	public static final String INIT_GET_DOCUMENT_EXAM = "initGetDocumentExam";
	public static final String DOC_TYPE_EXAM_DUPLICATE = "knowledgepro.admin.docTypeExam.Duplicate";
	public static final String DOC_TYPE_EXAM_REACTIVATE = "knowledgepro.admin.docTypeExam.reactivate";
	public static final String DOC_TYPE_EXAM_ADD_SUCCESS = "knowledgepro.admin.docTypeExam.add.success";
	public static final String DOC_TYPE_EXAM_ADD_FAILED = "knowledgepro.admin.docTypeExam.add.failure";
	public static final String DOC_TYPE_EXAM_DELETE_SUCCESS = "knowledgepro.admin.docTypeExam.delete.success";
	public static final String DOC_TYPE_EXAM_DELETE_FAILED = "knowledgepro.admin.docTypeExam.delete.failure";
	public static final String DOC_TYPE_EXAM_UPDATE_SUCCESS = "knowledgepro.admin.docTypeExam.update.success";
	public static final String DOC_TYPE_EXAM__UPDATE_FAILED = "knowledgepro.admin.docTypeExam.update.failed";
	public static final String DOC_TYPE_EXAM_REACTIVATE_SUCCESS = "knowledgepro.admin.docTypeExam.reactivate.success";
	public static final String DOC_TYPE_EXAM_REACTIVATE_FAILURE = "knowledgepro.admin.docTypeExam.reactivate.failure";
	public static final String FEE_CONCESSION_REPORT = "concessionReport";
	public static final String FEE_CONCESSION_REPORT_RESULT = "concessionReportResult";

	public static final String PRINT_RECEIPT_FOR_MODIFY = "printReceiptForModify";
	public static final String CONCESSION_SLIP_BOOK_REPORT = "concessionSlipBookReport";
	public static final String CONCESSION_SLIP_BOOK_REPORT_RESULT = "concessionSlipBookReportResult";

	public static final String PRINT_STUDENT_RESULT = "printStudentResult";
	// pettycash constants

	public static final String PETTYCASH_INIT_CANCEL_CASHCOLLECTION = "initCancelCashCollection";
	public static final String PETTYCASH_RETRIEVE_CANCEL_CASHCOLLECTION = "retrieveCashCollection";
	public static final String PETTYCASH_INIT_ACCHEAD_GROUP_CODE = "initAccountHeadGroupCode";
	public static final String GET_ALL_ACCHEADS_REPORT = "getAllAccountHeadsReport";
	public static final String GET_ALL_ACCHEADS_PRINT_REPORT = "getAllAccountHeadsPrintReport";
	public static final String INIT_ACC_HEAD_GROUP = "initAccountHeadGroup";

	public static final String PC_GROUP_CODE_REQUIRED = "knowlegepro.petticash.collectionLedger.groupCode.Required";
	public static final String INIT_SLIP_REGISTER = "slipRegister";

	public static final String DIVISION_REPORT_SEARCH = "divisionReportSearch";
	public static final String DIVISION_REPORT_RESULT = "divisionReportResult";
	public static final String FEE_PAYMENT_REG_SPECIAL_NOT_ALLOWED = "knowledgepro.fee.fee.payment.reg.special.not.allowed";
	public static final String FEE_PAYMENT_ROLL_SPECIAL_NOT_ALLOWED = "knowledgepro.fee.fee.payment.reg.special.not.allowed";
	// Hostel Allocation constants

	public static final String INIT_HOSTEL_ALLOCATION = "initAllocation";
	public static final String ROOM_TYPE_DESC_MORE = "knowledgepro.hostel.room.type.desc.max";
	public static final String DISPLAY_HOSTEL_ALLOCATION = "displayAllocation";
	public static final String HOSTEL_ALLOCATION_SPECIAL_NOT_ALLOWED = "knowledgepro.special.not.allowed";
	public static final String HOSTEL_ALLOCATION_RESERVERD_ROOM = "Reserved room cannot be allocated";
	public static final String HOSTEL_ALLOCATION_RESERVERD_ROOM_FULL = "Requested room is full";
	public static final String HOSTEL_ALLOCATION_SUCCESS = "Room allocation is  successfull";
	public static final String HOSTEL_ALLOCATION_ALLREADY_ALLOTTED = "Allready Allotted";
	public static final String HOSTEL_ALLOCATION_BED_ALLREADY_ALLOTTED = "Requested Bed is Allready Occupied";

	public static final String HOSTEL_FEES_REACTIVATE = "knowledgepro.hostel.fees.exists.reactivate";
	public static final String HOSTEL_FEES_REACTIVATE_SUCCESS = "knowledgepro.hostel.fees.exists.reactivated";
	public static final String HOSTEL_FEES_REACTIVATE_FAILED = "knowledgepro.hostel.fees.exists.reactivate.failed";
	public static final String HOSTEL_FEES_SAVE_SUCCESS = "knowledgepro.hostel.fees.save.success";
	public static final String HOSTEL_FEES_HOSTELWITHROOMTYPE_EXIST = "knowledgepro.hostel.fees.hostelwithroomtype.exist";
	public static final String HOSTEL_FEES_SAVE_FAILED = "knowledgepro.hostel.fees.save.failed";
	public static final String HOSTEL_FEES_TRUE = "true";
	public static final String HOSTEL_FEES_FALSE = "false";
	public static final String HOSTEL_FEES_NONACTIVEEXISTS = "nonactiveexists";
	public static final String HOSTEL_FEES_ACTIVEEXISTS = "activeexists";
	public static final String HOSTEL_FEES_AtLEASTONE_REQUIRED = "knowledgepro.hostel.fees.enter.atleastone";
	public static final String HOSTEL_FEES_NAMEANDROOMTYPE_REQUIRED = "knowledgepro.hostel.fees.hostelandroomtype.required";
	public static final String HOSTEL_FEES_NAME_REQUIRED = "knowledgepro.hostel.fees.hostel.required";
	public static final String HOSTEL_FEES_ROOMTYPE_REQUIRED = "knowledgepro.hostel.fees.roomtyperequired";

	public static final String USER_NOT_FOUND_IN_STUDENT_LOGIN_INFO = "knowledgepro.hostel.application.student.id.not.found";
	public static final String HEALTH_ISSUES_MAX_LEN = "knowledgepro.hostel.application.student.health.issues.max.len";
	public static final String SICKNESS_MAX_LEN = "knowledgepro.hostel.application.student.sickness.max.len";
	public static final String HOSTEL_RESERVATION_SEARCH_NOT_VALID = "knowledgepro.hostel.reservation.search.notvalid";
	public static final String HOSTEL_RESERVATION_PRINT = "hostelReservationPrint";
	public static final String HOSTEL_RESERVATION_VALID_DATE = "knowledgepro.hostel.reservation.from.date.cannot.greater";

	public static final String INIT_RESIDENT_STUDENT = "initResidentStudent";
	public static final String SEARCH_RESIDENT_STUDENT = "searchResidentStudent";
	public static final String STUDENT_ID_NAME = "knowledgepro.hostel.student.id.name";
	public static final String STUDENT_ID_NUMBER = "knowledgepro.hostel.student.id.numberFormat";
	public static final String SEARCH_BY_NAME = "residentSearchByName";
	public static final String VIEW_RESIDENT_STUDENT_INFO = "viewResidentStudentInfo";
	public static int HOSTEL_STATUS_APPLIED_ID = 4;

	// Constants for Hostel VIEW_REQUISITIONS
	public static String VIEW_REQUISITIONS = "initViewRequisitions";
	public static String SHOW_REQUISITIONS = "showRequisitions";
	public static String HOSTEL_APPROVE__FAILURE = "Failure in getting approved";
	public static String HOSTEL_APPROVE_SUCCESS = "Failure in getting approved";
	public static String REQUISITION_POPUP_RESULT = "popUpResults";
	public static String HOSTEL_APPROVED = "Approved";
	public static String HOSTEL_REJECTED = "Reject";
	public static String HOSTEL_PENDING = "Pending";
	public static String ALL = "All";
	public static String EMPLOYEE_APPROVE_LEAVE = "employeeApproveLeave";
	public static String EMPLOYEE_DISP_AVAILABLE_LEAVE = "dispAvailableLeave";
	

	public static final String UPDATED_SUCCESS = "knowledgepro.hostel.status.updated";

	// Hostel Disciplinary Details constansts
	public static final String HOSTEL_DISCIPLINARY_DETAILS_PAGE = "disciplinaryDetailsEntry";
	public static final String DISCIPLINARY_DETAILS_ADD_SUCCESS = "knowledgepro.hostel.disciplinary.details.add.success";
	public static final String DISCIPLINARY_DETAILS_ADD_FAILED = "knowledgepro.hostel.disciplinary.details.add.failed";
	public static final String DISCIPLINARY_DETAILS_DELETE_SUCCESS = "knowledgepro.hostel.disciplinary.details.delete.success";
	public static final String DISCIPLINARY_DETAILS_DELETE_FAILED = "knowledgepro.hostel.disciplinary.details.delete.failed";
	public static final String HOSTEL_DISCIPLINARY_DETAILS_UPDATE_SUCCESS = "knowledgepro.hostel.disciplinary.details.update.success";
	public static final String HOSTEL_DISCIPLINARY_DETAILS_UPDATE_FAILED = "knowledgepro.hostel.disciplinary.details.update.failed";
	public static final String DISCIPLINARY_DETAILS_INVALID_APPLICATION_NUMBER = "knowledgepro.hostel.disciplinary.details.invalidApplicationNo";
	public static final String DISCIPLINARY_DETAILS_INVALID_STAFF_ID = "knowledgepro.hostel.disciplinary.details.invalidStaffId";
	public static final String DISCIPLINARY_DETAILS_INVALID_REGISTER_NUMBER = "knowledgepro.hostel.disciplinary.details.invalidRegisterNo";
	public static final String HOSTEL_APPLICATION_EXISTS = "knowledgepro.hostel.application.form.already.exists";
	public static final String DISCIPLINARY_DETAILS_ATLEAST_ONE_FIELD = "knowledgepro.hostel.disciplinary.details.onefield";

	public static final String EMP_APPLY_LEAVE = "initEmpApplyLeave";
	public static final String KNOWLEDGEPRO_EMP_LEAVE = "knowledgepro.emp.leave.maxRange";
	public static final String KNOWLEDGEPRO_INTERVIEW_COMMENTS = "knowledgepro.interview.comments.contentFail";
	public static final String KNOWLEDGEPRO_ITEM_DESCRIPTION1_CONTENTFAIL = "knowledgepro.item.description.fail";

	// Keys for Hostel Damage Entry
	public static final String HOSTEL_DAMAGE_ENTRY_INIT_PAGE = "hostelDamageEntryInitPage";
	public static final String HOSTEL_DAMAGE_ENTRY_PAGE = "hostelDamageEntryResult";
	public static final String APPLICATION_REG_ROLLNO_STAFF = "knowledgepro.application.reg.staff.required";
	public static final String HOSTEL_DAMAGE_ENTRY_SUCCESS = "knowledgepro.hostel.damageEntry.AddedSuccess";
	public static final String HOSTEL_DAMAGE_ENTRY_DELETE_SUCCESS = "knowledgepro.hostel.damageEntry.DeletedSuccess";
	public static final String HOSTEL_DAMAGE_ENTRY_DELETE_FAILED = "knowledgepro.hostel.damageEntry.DeletedFailed";
	public static final String KNOWLEDGEPRO_INTERVIEW_SELECT = "knowledgepro.interview.selectCheck";

	// Hostel Fees related entries
	public static final String INIT_HOSTEL_FEES = "initHostelFees";
	public static final String VIEW_HOSTEL_FEES = "hostelFeesView";
	public static final String HOSTEL_ALLOCATION_APPREGROLL_REQUIRED = "knowledgepro.hostel.allocation.apporregiorrollorstaff.required";
	public static final String HOSTEL_ALLOCATION_BOTH_NOTREQUIRED = "knowledgepro.fee.apporregi.all.notrequired";

	// VIEW_REQSTATUS CONSTANTS
	public static final String VIEW_REQSTATUS = "viewreqstatus";
	public static final String VIEW_REQ_STATUS_RESULT = "viewReqStatusResults";
	public static final String PRINT_REQ_STATUS_RESULT = "printReqStatusResults";

	// Hostel Checkin ConstantsCMSConstants

	public static final String INIT_HOSTEL_CHECKIN = "initCheckin";
	public static final String HOSTEL_CHECKIN_SPECIAL_NOT_ALLOWED = "knowledgepro.special.not.allowed";
	public static final String HOSTEL_CHECKIN_BOTH_NOTREQUIRED = "knowledgepro.fee.apporregi.both.notrequired";
	public static final String HOSTEL_CHECKIN_APPREGROLL_REQUIRED = "knowledgepro.hostel.checkin.apporregi.required";
	public static final String DISPLAY_HOSTEL_CHECKIN = "displayCheckin";
	public static final String HOSTEL_CHECKIN_CHECKIN_DATE_REQUIRED = "knowledgepro.hostel.checkin.checkindate.required";
	public static final String HOSTEL_CHECKIN_DETAILS_SUCCESS = "knowledgepro.hostel.checkin.details.saved.successfully";
	public static final String HOSTEL_CHECKIN_PERSON_ALREDY_CHECKIN = "knowledgepro.hostel.checkin.person.checkin";
	public static final String HOSTEL_CHECKIN_ACTIVEEXISTS = "activeexists";
	public static final String HOSTEL_CHECKIN_NONACTIVEEXISTS = "nonactiveexists";
	public static final String HOSTEL_CHECKIN_TRUE = "true";
	public static final String HOSTEL_CHECKIN_HOSTELWITHROOMTYPE_EXIST = "knowledgepro.hostel.Checkin.hostelwithroomtype.exist";
	public static final String HOSTEL_CHECKIN_REACTIVATE = "knowledgepro.hostel.Checkin.exists.reactivate";
	public static final String HOSTEL_CHECKIN_REACTIVATE_SUCCESS = "knowledgepro.hostel.Checkin.exists.reactivated";
	public static final String HOSTEL_CHECKIN_REACTIVATE_FAILED = "knowledgepro.hostel.Checkin.exists.reactivate.failed";

	// Hostel Checkout ConstantsCMSConstants.
	public static final String INIT_HOSTEL_CHECKOUT = "initCheckout";
	public static final String DISPLAY_HOSTEL_CHECKOUT = "displayCheckout";
	public static final String HOSTEL_CHECKOUT_PERSON_ALREDY_CHECKOUT = "knowledgepro.hostel.Checkout.person.Checkout";
	public static final String HOSTEL_CHECKOUT_SPECIAL_NOT_ALLOWED = "knowledgepro.special.not.allowed";
	public static final String HOSTEL_CHECKOUT_APPREGROLL_REQUIRED = "knowledgepro.hostel.Checkout.apporregi.required";
	public static final String HOSTEL_CHECKOUT_BOTH_NOTREQUIRED = "knowledgepro.fee.apporregi.both.notrequired";
	public static final String HOSTEL_CHECKOUT_DETAILS_SUCCESS = "knowledgepro.hostel.Checkout.details.saved.successfully";
	public static final String HOSTEL_CHECKOUT_TRUE = "true";
	public static final String HOSTEL_CHECKOUT_DETAILS_FAIL = "knowledgepro.hostel.Checkout.details.not.saved";
	public static final String VIEW_FINE_Details = "viewFine";

	public static final String INIT_VISITOR_INFO = "initVisitorInfo";
	public static final String VISITOR_FOR_REQUIRED = "knowledgepro.hostel.visitor.required";
	public static final String VISITOR_REQUIRED = "knowledgepro.hostel.visitor.registerNo.staff";
	public static final String REGISTER_NAME_REQUIRED = "knowledgepro.hostel.register.name.required";
	public static final String STAFF_NAME_REQUIRED = "knowledgepro.hostel.staff.name.required";
	public static final String VISITOR_PUT_DETAILS = "visitorInfoSearch";
	public static final String VISITOR_SUBMIT = "visitorSubmit";
	public static final String VISITOR_ADDED = "knowledgepro.hostel.visitor.success";
	public static final String VISITOR_failure = "knowledgepro.hostel.visitor.failure";
	/**
	 * student inout entries
	 */
	public static final String STUDENT_IN_OUT = "studInout";
	public static final String DISPLAY_STUDENT_IN_OUT = "displayStud";
	public static final String HOSTEL_STUDENT_SPECIAL_NOT_ALLOWED = "knowledgepro.special.not.allowed";
	public static final String HOSTEL_STUDENT_IDORNAME_REQUIRED = "knowledgepro.hostel.student.idname.required";
	public static final String HOSTEL_STUDENT_BOTH_NOTREQUIRED = "knowledgepro.hostel.student.both.notrequired";
	public static final String HOSTEL_STUDENT_DETAILS_SUCCESS = "knowledgepro.hostel.student.details.saved.successfully";
	public static final String HOSTEL_STUDENT_TRUE = "true";
	public static final String HOSTEL_STUDENT_DETAILS_FAIL = "knowledgepro.hostel.student.details.not.saved";
	public static final String HOSTEL_STUDENT_DATE_REQUIRED = "knowledgepro.hostel.student.any.one.required";
	public static final String HOSTEL_STUDENT_ENTER_CORRECT_TIME = "knowledgepro.hostel.student.timein.greater.timeout";

	// Hostel reqreport Constants

	public static final String INIT_HOSTEL_REQREPORT = "initReqReport";
	public static final String SECOND_HOSTEL_REQREPORT = "secondReqReport";
	public static final String HOSTEL_REQREPORT_SPECIAL_NOT_ALLOWED = "knowledgepro.special.not.allowed";
	public static final String HOSTEL_REQREPORT_HOSTEL_NAME_REQUIRED = "knowledgepro.hostel.name.required";
	public static final String HOSTEL_REQREPORT_APPLIED_DATE_REQUIRED = "knowledgepro.hostel.applied.date.required";

	// Hostel Daily report Constants

	public static final String INIT_HOSTEL_DAILY_REPORT = "initDailyReport";
	public static final String DISPLAY_HOSTEL_DAILY_REPORT = "secondDailyReport";

	// Hostel Absenties report Constants

	public static final String INIT_HOSTEL_ABSENT_REPORT = "initAbsentReport";
	public static final String DISPLAY_HOSTEL_ABSENT_REPORT = "secondAbsentReport";
	public static final String PRINT_HOSTEL_ABSENT_REPORT = "printAbsentReport";
	public static final String DISPLAY_HOSTEL_ABSENT_HOSTELID = "Hostel Id Required";
	public static final String DISPLAY_HOSTEL_ABSENT_GROUPID = "Group Id Required";

	// Hostel Student Extract Constants

	public static final String INIT_HOSTEL_EXTRACT = "initStudentExtract";
	public static final String SECOND_HOSTEL_EXTRACT = "secondStudentExtract";
	public static final String HOSTEL_STUDENT_EXTRACT_FROM_DATE_REQUIRED = "knowledgepro.hostel.student.from.date.required";
	public static final String HOSTEL_STUDENT_EXTRACT_TO_DATE_REQUIRED = "knowledgepro.hostel.student.to.date.required";
	public static final String TO_DATE_GREATER_TO_FROM_DATE = "knowledgepro.to.date.greater.form.date";

	public static final String SELECT_ATLEAST_ONE = "knowledgepro.hostel.status.select";
	public static final String PETTICASH_SELECTED_FINANCIALYEAR = "knowledgepro.pettycash.select.financialYear";

	public static final String PC_FINANCIAL_YEAR_ENTRY = "pcFinancialYearEntry";
	public static final String PC_FINANCIAL_YEAR_EXISTS = "knowledgepro.pc.financialyear.exists";
	public static final String PC_FINANCIAL_YEAR_REACTIVATE = "knowledgepro.pc.financialyear.exists.reactivate";
	public static final String PC_FINANCIAL_YEAR_ADD_SUCCESS = "knowledgepro.pc.financialyear.added";
	public static final String PC_FINANCIAL_YEAR_ADD_FAILED = "knowledgepro.pc.financialyear.add.failed";
	public static final String PC_FINANCIAL_YEAR_UPDATE_SUCCESS = "knowledgepro.pc.financialyear.updated";
	public static final String PC_FINANCIAL_YEAR_UPDATE_FAILED = "knowledgepro.pc.financialyear.update.failed";
	public static final String PC_FINANCIAL_YEAR_DELETE_SUCCESS = "knowledgepro.pc.financialyear.deleted";
	public static final String PC_FINANCIAL_YEAR_DELETE_FAILED = "knowledgepro.pc.financialyear.delete.failed";
	public static final String PC_FINANCIAL_YEAR_REACTIVATE_SUCCESS = "knowledgepro.pc.financialyear.reactivated";
	public static final String PC_FINANCIAL_YEAR_REACTIVATE_FAILED = "knowledgepro.pc.financialyear.reactivate.failed";
	public static final String PC_FINANCIAL_YEAR_NOT_CURRENT = "knowledgepro.pc.financialyear.notcurrent";
	public static final String PC_FINANCIAL_YEAR_ALREADY_STORED = "knowledgepro.pc.financialyear.alreadystored";
	public static final String PC_FINANCIAL_YEAR_MORETHAN_ONE = "knowledgepro.pc.financialyear.morethanone";
	public static final String PC_FINANCIAL_YEAR_IS_ALREADY_CURRENT = "knowlegepro.pc.financial.year.alreadycurrent";

	public static final String INTERVIEW_SEQUENCE = "knowledgepro.interviewBatchUpdate.sequence.invalid";
	public static final String INTERVIEW_SUBROUND = "knowledgepro.interviewBatchUpdate.subround.invalid";
	public static final String INTERVIEW_NO_OF_INTERVIEWERS = "knowledgepro.interviewBatchUpdate.noOfInterviewers.invalid";

	public static final String KNOWLEDGEPRO_INTERVIEWTYPE_REQUIRED = "interviewProcessForm.interviewType.required";

	public static final String NO_OF_CANDIDATES_INVALID = "knowledgepro.admission.noOfCandidates.invalid";
	public static final String NO_OF_CANDIDATES_ZERO = "knowledgepro.admission.noOfCandidates.zero";

	// keys for Hostel Payment Slip
	public static final String HOSTEL_PAYMENT_SLIP_INIT_PAGE = "hostelPaymentSlipInitPage";
	public static final String HOSTEL_PAYMENT_SLIP_PAGE = "hostelPaymentSlipPage";
	public static final String HOSTEL_PAYMENT_MARK_FOR_PAID_INIT = "hostelPaymentMarkForPaidInitPage";
	public static final String HOSTEL_PAYMENT_MARK_FOR_PAID = "hostelPaymentMarkForPaidPage";
	public static final String HOSTEL_PAYMENT_SLIP_FEE_FINE = "knowledgepro.hostel.Fee.Or.Fine.Select";
	public static final String HOSTEL_PAYMENT_MARK_AS_PAID = "knowledgepro.hostel.payment.markPaid";
	public static final String HOSTEL_PAYMENT_ALREADY_MARK_AS_PAID = "knowledgepro.hostel.payment.alreadyMarkPaid";
	public static final String HOSTEL_PAYMENT_PRINT_CHALLAN_SUCCESS = "knowledgepro.hostel.paymentSlip.printChallanSuccess";
	public static final String HOSTEL_PAYMENT_PRINT_CHALLAN = "printChallan";
	public static final String HOSTEL_PAYMENT_BILL_NO_INVALID = "knowledgepro.hostel.payment.billNo.invalid";
	public static final String HOSTEL_PAYMENT_NO_RECORD_FOUND = "knowledgepro.hostel.payment.noRecordFound";
	public static final String HOSTEL_FEE_PAYMENT_SAVED_SUCCESS = "nowledgepro.hostel.paymentSlip.feePayment.savedSuccess";

	public static final String STOCK_TX_ISSUED = "SI";
	public static final String STOCK_TX_RECIEVED = "SR";

	public static final String POPUP_STUDENTS_DATABASE = "popUpStudentDetails";
	public static final String HOSTEL_RESERV_CANCEL = "hostelReservCancel";
	public static final String KNOWLEDGEPRO_ADMISSION_GRADE_REQUIRED = "Atleast one grade has to be selected";

	public static final String INIT_HOSTEL_ALLOCATION_REPORT = "initAllocationReport";
	public static final String HOSTEL_ALLOCATION_REPORT_RESULTS = "resultsAllocationReport";
	public static final String BIRT_FEE_REPORT = "birtFeeBirt";

	// Keys for hostel application by admin

	public static final String HOSTEL_APPLICATION_BY_ADMIN_INIT = "hostelApplicationByAminInitPage";
	public static final String HOSTEL_APPLICATION_BY_ADMIN = "hostelApplicationByAminPage";
	public static final String HOSTELAPPLICATION_SAVE_SUCCESS = "knowledgepro.hostel.hlApplicationByAdmin.saveSuccess";
	public static final String HOSTELAPPLICATION_TERMS_CONDITIONS = "termsAndConditionsPage";
	public static final String HOSTELAPPLICATION_PERSONAL_DETAILS = "personalDetailsPage";
	public static final String STUDENT_STAFF_ALREADY_APPLY = "knowledgepro.hostel.hlApplicationByAdmin.studentOrStaffAlreadyApply";
	public static final String HOSTELAPPLICATION_REQUISITION_PAGE = "hostelApplicationRequisition";

	// Hostel Fees Type Entries
	public static final String HOSTEL_FEES_TYPE_ENTRY = "hostelFeesTypeEntry";
	public static final String HOSTEL_FEES_TYPE_ADD_SUCCESS = "knowledgepro.hostel.hostelfeestype.add.success";
	public static final String HOSTEL_FEES_TYPE_ADD_FAIL = "knowledgepro.hostel.hostelfeestype.add.fail";
	public static final String HOSTEL_FEES_TYPE_DELETE_SUCCESS = "knowledgepro.hostel.hostelfeestype.delete.success";
	public static final String HOSTEL_FEES_TYPE_DELETE_FAILED = "knowledgepro.hostel.hostelfeestype.delete.failed";
	public static final String HOSTEL_FEES_TYPE_UPDATE_SUCCESS = "knowledgepro.hostel.hostelfeestype.update.success";
	public static final String HOSTEL_FEES_TYPE_UPDATE_FAILED = "knowledgepro.hostel.hostelfeestype.update.fail";
	public static final String HOSTEL_FEES_TYPE_EXISTS = "knowledgepro.hostel.hostelfeestype.exists";
	public static final String HOSTEL_FEES_TYPE_REACTIVATE = "knowledgepro.hostel.hostelfeestype.exists.reactivate";
	public static final String HOSTEL_FEES_TYPE_REACTIVATE_SUCCESS = "knowledgepro.hostel.hostelfeestype.reactivate.success";
	public static final String HOSTEL_FEES_TYPE_REACTIVATE_FAILED = "knowledgepro.hostel.hostelfeestype.reactivate.fail";

	// Hostel ---ListOfOccupancyRegister keys
	public static final String HOSTEL_INIT_LIST_OF_OCCUPANCY_REGISTER = "initListOfOccupancyRegister";
	public static final String HOSTEL_OCCUPANCY_REGISTER_SEARCH = "hostelOccupancyRegisterPage";

	// Hostel ---Hostel Status Info Form
	public static final String HOSTEL_STATUS_INFO_PAGE = "hostelStatusPage";

	// Hostel ---Hostel ActionForm
	public static final String HOSTEL_ACTION_REPORT_PAGE = "initHostelActionReportPage";
	public static final String HOSTEL_ACTION_REPORT_DETIALS_PAGE = "hostelActionReportDetailsPage";
	public static final String HOSTEL_ACTION_REPORT_DETIALS_PRINT_PAGE = "hostelActionReportPrintPage";
	public static final String BIRT_FEE_REPORT_RESULT = "birtFeeBirtResults";

	public static final String Attendance_Required = "knowledgepro.attendance.attendancetype.required";
	public static final String CANCEL_REQUISITION = "knowledgepro.hostel.req_cancelled";
	public static final String HOSTEL_RESERVATION_SLIP = "hostelReservationSlip";
	public static final String TEACHER_REPORT_PRINT = "teacherReportPrint";
	public static final String STUDENT_REPORT_PRINT = "studentReportPrint";
	public static final String STUDENT_ABSENCE_REPORT_PRINT = "studentAbsenceReportPrint";

	// keys for Hostel Student Evaluation

	public static final String HOSTEL_STUDENT_EVALUATION_INIT = "hostelStudentEvaluationInit";
	public static final String HOSTEL_STUDENT_EVALUATION = "hostelStudentEvaluation";
	public static final String CheckedIn = "CheckedIn";
	public static final String CheckedOut = "CheckedOut";
	public static final String HOSTEL_STUDENT_EVALUATION_FROMDATE_INVALID = "knowledgepro.hostel.studentEvaluation.fromDateInvalid";
	public static final String HOSTEL_STUDENT_EVALUATION_TODATE_INVALID = "knowledgepro.hostel.studentEvaluation.toDateInvalid";
	public static final String ABSENCE_REPORT_PRINT = "printabsenceReport";
	public static final String MONTHLY_ABSENCE_REPORT_PRINT = "printMonthlyabsenceReport";

	public static final String PRINT_BELOW_REQUIRED_PERCENTAGE = "printBelowRequiredPercentage";
	public static final String BIRT_EXAM_REPORT = "birtExamBirt";
	public static final String BIRT_EXAM_REPORT_RESULT = "birtExamBirtResults";
	public static final String TEMPLATE_EXAM_CENTER_SEAT_NO = "[EXAM_CENTER_SEAT_NO]";
	public static final String TEMPLATE_EXAM_CENTER_NAME = "[EXAM_CENTER_NAME]";
	public static final String TEMPLATE_EXAM_CENTER_SEAT_NO_PREFIX = "[SEAT_NO_PREFIX]";
	public static final String REPORT_SEAT_NO = "Seat No";
	public static final String BIRT_ADMISSION_REPORT = "birtAdmissionRep";
	public static final String INIT_COURSE_CHANGE_PAGE = "initCourseChange";
	public static final String BIRT_ADMISSION_RESULT = "birtAdmissionReportResult";
	public static final String INIT_COURSE_CHANGE_UPDATE = "courseChangeUpdate";

	public static final String UPLOAD_ENTRANCE_RESULT = "Upload_Entrance_Result";
	public static final String UPLOAD_ENTRANCE_SUCCESS = "knowledgepro.admissionForm.upload.entrance.success";
	public static final String UPLOAD_ENTRANCE_FAILURE = "knowledgepro.admissionForm.entrance.upload.failure";

	public static final String ADMISSION_ADMISSIONSTATUS_IS_CANCELLED = "knowledgepro.admission.admissionstatus.is.cancelled";
	public static final String ADMISSION_ADMISSIONSTATUS_ADMITTED = "knowledgepro.admission.admissionstatus.is.admitted";

	public static final String REPORT_EXAM_CENTER = "Exam Center";

	public static final String INIT_FINAL_MERIT_LIST_UPLOAD = "initFinalMeritUpload";
	public static final String UPLOAD_FINAL_MERIT_LIST = "knowledgepro.admission.excelupload.final.merit.list";
	public static final String UPLOAD_FINAL_MERIT_LIST_UPLOAD_SUCCESS = "knowledgepro.admission.final.merit.list.upload.success";
	public static final String UPLOAD_FINAL_MERIT_LIST_UPLOAD_FAILURE = "knowledgepro.admission.final.merit.list.upload.failure";

	public static final String INIT_PCACCOUNT_ENTRY = "pcAccountEntry";
	public static final String ADM_REPORT_INTERVIEW_RESULT = "Interview Details";
	public static final String ADM_REPORT_PREVIOUS_EDU_DET = "Previous Educational Details";
	public static final String FEE_PAYMENT_PUC_PRINTCHALLAN = "pucFeePrint";
	public static final String INIT_FEE_CRITERIA = "initFeeCriteria";
	// below constants added by 9Elements
	public static final String VIEW_HISTORY_DISCONTINUE_PAGE = "viewHistoryDiscontinuePage";
	public static final String VIEW_HISTORY_REJOIN_PAGE = "viewHistoryRejoinPage";
	public static final String VIEW_HISTORY_DETENTION_PAGE = "viewHistoryDetentionPage";
	public static final String VIEW_HISTORY_SUBJECT_GROUP_PAGE = "viewHistorySubjectGroupPage";
	// above constants added by 9Elements
	public static final String PHONE_NO = "Phone No.";
	public static final String MOBILE_NO = "Mobile No.";
	public static final String CURRENT_ADDRESS_CITY = "Current Address City";
	public static final String CURRENT_ADDRESS_COUNTRY = "Current Address Country";
	public static final String CURRENT_ADDRESS_STATE = "Current Address State";
	public static final String CURRENT_ADDRESS_ZIP = "Current Address Zip";

	public static final String PER_ADDRESS_CITY = "Permanent Address City";
	public static final String PER_ADDRESS_COUNTRY = "Permanent Address Country";
	public static final String PER_ADDRESS_STATE = "Permanent Address State";
	public static final String PER_ADDRESS_ZIP = "Permanent Address Zip";
	public static final String CLASS_CODE = "Class Code";
	public static final String VALID_TILL = "Valid Till";

	public static final String DESCIPLINARY_DETAILS = "disciplinaryDetails";
	public static final String DESCIPLINARY_DETAILS_DISPLAY = "disciplinaryDetailsDisplay";
	public static final String DESCIPLINARY_DETAILS_ADD_REMARKS = "disciplinaryDetailsDisplayAddRemarks";
	public static final String PHOTOBYTES = "PhotoBytes";
	public static final String EXPORT_PHOTOS = "ExportPhotos";
	public static final String PRINT_PURCHASE_ORDER = "printPurchaseOrder";
	public static final String REPORT_ADMISSION_OTHER_THAN_SELECTED = "Other Than Selected";
	public static final String PRINT_PURCHASE_ORDER_DETAILS = "printPurchaseOrderDetails";
	public static final String GENERATE_PASSWORD_MAIL_CONFIRM = "generatePasswordMailConfirm";
	public static final String ADMISSIONFORM_CANCELLATION_NEW = "cancel_admission_new";
	public static final String SUBMIT_ATTENDENCE_SUMMARY_REPORT_KJC = "submitAttendSummaryReportForKJC";
	public static final String TEACHER_CLASSES_NUMERICCODE_DUPLICATE = "knowledgepro.teacher.classentry.duplicate.numericCode";
	public static final String EMPLOYEE_INFO_DETAILS_DISPLAY = "employeeInformationDetails";
	public static final String INIT_MANAGE_HOLIDAY_LIST = "initManageHolidayList";
	public static final String INIT_MANAGE_WORKING_DAYS = "initManageWorkingDays";
	public static final String INIT_STAFF_ALLOCATION = "initStaffAllocation";
	public static final String INIT_DURATION_ALLOCATION = "initDurationAllocation";
	public static final String DURATION_ALLOCATION_ENTRY = "durationAllocationEntry";
	public static final String INIT_TIME_ALLOCATION = "initTimeAllocation";
	public static final String TIME_ALLOCATION_ENTRY = "timeAllocationEntry";
	public static final String TEACHER_CLASSES_ENTRY_DUPLICATE = "knowledgepro.teacher.classentry.duplicate.entry";
	public static final String RESET_STUDENT_PASSWORD = "resetStudentPassword";
	public static final String RESET_PASSWORD_CONFIRM = "resetPasswordConfirm";
	public static final String RESET_PASSWORD_MAIL_CONFIRM = "resetPasswordMailConfirm";
	public static final String EMPLOYEE_USER_INFO_PAGE = "employeeUserInfo";

	public static final String ASSIGN_SECOND_LANGUAGE = "assignSecondLanguage";
	public static final String UPLOAD_SECOND_LANGUAGE = "uploadSecondLanguage";
	public static final String UPLOAD_SECONDLANGUAGE = "knowledgepro.admission.excelupload.secondLanguage";
	public static final String UPLOAD_SECONDLANGUAGE_SUCCESS = "knowledgepro.admissionForm.upload.secondLanguage.success";
	public static final String UPLOAD_SECONDLANGUAGE_FAILURE = "knowledgepro.admissionForm.upload.secondLanguage.failure";
	public static final String UPLOAD_SECONDLANGUAGE_CSV = "knowledgepro.admission.secondLanguage.csvupload";
	public static final String INIT_SECOND_LANGUAGE_MASTER = "secondLanguage";

	public static final String VEW_EMPLOYEE_LEAVE = "ViewEmployeeLeave";
	public static final String VEW_EMPLOYEE_LEAVE_DETAILS = "ViewEmployeeLeaveDetails";
	public static final String VEW_MY_LEAVE = "ViewMyLeaves";
	public static final String INIT_GENERATE_TIME_TABLE = "initGenerateTimeTable";
	public static final String GENERATED_TIME_TABLE_DISPLAY = "generatedTimeTableDisplay";
	public static final String VEW_ONLINE_RESUME_SUBMISSION = "onlineResumerSubmission";

	public static final String ATTENDANCE_ENTRY_SLIPNOEXIST = "knowledgepro.attendanceentry.slipNo.Exist";
	public static final String ATTENDANCE_RE_ENTRY_FIRST = "attendanceReEntry";

	public static final String INTERVIEW_COMMENTS = "InterviewComments";
	public static final String INTERVIEW_COMMENTS_DETAILS = "InterviewCommentsDetails";
	public static final String INTERVIEW_COMMENTS_DETAILS_ENTRY = "InterviewCommentsDetailsEntry";
	public static final String INTERVIEW_COMMENTS_DISPLAY = "InterviewCommentsDisplay";
	public static final String INIT_STAFF_WISE_TIME_TABLE = "initStaffWiseTimeTableView";
	public static final String STAFF_WISE_TIME_TABLE_DISPLAY = "staffWiseTimeTableDisplay";
	public static final String READ_ATTENDANCE_FILE = "ReadAttendanceFile";
	public static final String BIOMETRIC_LOG_SETUP = "BiometricLogSetup";
	public static final String ATTENDANCE_RE_ENTRY_SECOND = "attendanceReEntrySecond";
	public static final String INIT_MANAGE_TIME_TABLE = "initManageTimeTable";
	public static final String MANAGE_TIME_TABLE_ENTRY = "manageTimeTable";
	public static final String UPLOAD_ATTENDANCE_FILE = "knowledgepro.employee.excelupload.attendanceFile";
	public static final String EXCEPTION_TYPES = "ExceptionTypes";
	public static final String EXCEPTION_DETAILS = "exceptionDetails";
	public static final String ADMISSIONFORM_CSVADMISSION_RE_UPLOADPAGE = "csvAdmissionReUploadPage";
	public static final String LAST_SLIP_NUMBER_DISPLAY = "displayLastSlipNumber";
	public static final String ATTENDANCE_SLIPNUMBER_EXISTS = "knowledgepro.attendance.slipno.exists";
	public static final String ATTENDANCE_SLIPNUMBER_REACTIVATE = "knowledgepro.attendance.slipno.reActivate";
	public static final String ATTENDANCE_SLIPNUMBER_ADD_SUCCESS = "knowledgepro.attendance.slipno.added";
	public static final String ATTENDANCE_SLIPNUMBER_ADD_FAILED = "knowledgepro.attendance.slipno.failed";
	public static final String ATTENDANCE_SLIPNUMBER_DELETED_SUCCESS = "knowledgepro.attendance.slipno.deleted";
	public static final String ATTENDANCE_SLIPNUMBER_DELETE_FAILED = "knowledgepro.attendance.slipno.delete.failed";
	public static final String ATTENDANCE_SLIPNUMBER_REACTIVATE_SUCCESS = "knowledgepro.attendance.slipno.reActivate";
	public static final String ATTENDANCE_SLIPNUMBER_REACTIVATE_FAILED = "knowledgepro.attendance.slipno.reActivate.failed";
	public static final String ATTENDANCE_SLIPNUMBER_UPDATE_SUCCESS = "knowledgepro.attendance.slipno.updated";
	public static final String ATTENDANCE_SLIPNUMBER_UPDATE_FAILED = "knowledgepro.attendance.slipno.updated.failed";
	public static final String ATTENDANCE_SLIPNUMBER_CONFIGURE = "knowledgepro.attendance.slipno.configure";
	public static final String INIT_CLASS_WISE_TIME_TABLE = "initClassWiseTimeTableView";
	public static final String CLASS_WISE_TIME_TABLE_DISPLAY = "classWiseTimeTableDisplay";
	public static final String KNOWLEDGEPRO_VIEWTIMETABLE_NORECORDSFOUND = "knowledgepro.viewTimeTable.norecordsfound";
	public static final String INIT_SUBJECT_WISE_TIME_TABLE = "initSubjectWiseTimeTableView";
	public static final String SUBJECT_WISE_TIME_TABLE_DISPLAY = "subjectWiseTimeTableDisplay";
	public static final String EXCEPTION_DETAILS_DELETED_SUCCESS = "knowledgepro.employee.exceptionDetails.Delete.Success";
	public static final String EXCEPTION_DETAILS_DELETED_FAILED = "knowledgepro.employee.exceptionDetails.Delete.failed";
	public static final String EXCEPTION_DETAILS_REACTIVATE = "knowledgepro.employee.exceptionDetails.reactivate.success";
	public static final String EXCEPTION_DETAILS_REACTIVATE_FAILED = "knowledgepro.employee.exceptionDetails.reactivate.failed";
	public static final String EXCEPTION_DETAILS_UPDATED_SUCCESS = "knowledgepro.employee.exceptionDetails.Update.Success";
	public static final String EXCEPTION_DETAILS_UPDATED_FAILED = "knowledgepro.employee.exceptionDetails.Update.Failed";
	public static final String EXCEPTION_DETAILS_EXISTS = "knowledgepro.employee.exceptionDetails.exists";
	public static final String EXCEPTION_DETAILS_ADDED_SUCCESS = "knowledgepro.employee.exceptionDetails.added.Success";
	public static final String EXCEPTION_DETAILS_ADDED_FAILED = "knowledgepro.employee.exceptionDetails.added.failed";
	
	public static final String HOSTEL_COMPLAINTS_REQUISITION_NOTPROPER = "knowledgepro.hostel.complaints.reqno.notproper";
	public static final String COMPLAINTS_DETAILS = "ComplaintsDetails";
	public static final String COMPLAINT_DETAILS_DELETED_FAILED = "knowledgepro.employee.complaintsDetails.Delete.failed";
	public static final String COMPLAINT_DETAILS_DELETED_SUCCESS = "knowledgepro.employee.complaintsDetails.Delete.success";
	
	public static final String INIT_HOSTEL_GROUP = "initHostelGroup";
	public static final String HOSTEL_STUDENT_IN_OUT_ALREADY_ENTERED = "knowledgepro.hostel.student.in.out.already.entered";
	
	public static final String HOSTEL_LEAVE_REGISTRATIONNO_REQUIRED = "knowledgepro.hostel.leave.registerNo.required";
	public static final String HOSTEL_LEAVE_REGISTERNO_NOTPROPER = "knowledgepro.hostel.leave.register.required";
	public static final String UPLOAD_HOSTEL_STUDENTS = "uploadHostelStudents";
	public static final String HOSTEL_ALLOCATION_VALID_DATE = "knowledgepro.hostel.allocation.reservation.date.greater";
	public static final String PRINT_SHORTAGE_INPUT = "printShortageInput";
	public static final String PRINT_SHORTAGE_RESULT="printShortageResult";
	public static final String ATTENDANCE_SHORTAGE_TEMPLATE="Attendance SHORTAGE TEMPLATE";
	public static final String TEMPLATE_REGISTER_NO="[RegisterNo]";
	public static final String TEMPLATE_PERCENTAGE="[Percentage]";

	public static final String STUDENTLOGIN_AGREEMENT = "stLoginAgreement";
	public static final String TEACHER_ATTENDANCE_PERIOD_DETAILS = "teacherAttPeriodDetails";
	public static final String ASSIGN_ROOM_DETAILS_DELETED_SUCCESS = "knowledgepro.hostel.assignRoomDetails.Delete.Success";
	public static final String ASSIGN_ROOM_DETAILS_DELETED_FAILED = "knowledgepro.hostel.assignRoomDetails.Delete.Failed";
	public static final String ASSIGN_ROOM_DETAILS_UPDATED_SUCCESS = "knowledgepro.hostel.assignRoomDetails.update.Success";
	public static final String ASSIGN_ROOM_DETAILS_UPDATED_FAILED = "knowledgepro.hostel.assignRoomDetails.update.Failed";
	public static final String HOSTEL_FEES_UPDATE_SUCCESS = "knowledgepro.hostel.fees.update.Success";
	public static final String HOSTEL_FEES_UPDATE_FAILED = "knowledgepro.hostel.fees.update.Failed";
	public static final String COMPLAINT_DETAILS_EXISTS = "knowledgepro.employee.complaintsDetails.Exists";
	public static final String HOSTEL_LEAVE_DETAILS_EXISTS = "knowledgepro.hostel.leave.Exists";
	public static final String RESIDENT_STUDENT_HOSTEL_REQUIRED = "knowledgepro.hostel.fees.hostel.required";
	
	public static final String BIRT_FEE_REPORT_RESULT_STUDENT = "birtFeeBirtResultsForStudent";
	public static final String EDIT_ROOM_MASTER = "editRoomMaster";
	public static String STUDENT_ERROR_PAGE = "studentErrorpage";
	
	public static final String UPLOAD_HOSTELSTUDENT = "knowledgepro.admission.excelupload.hostelStudent";
	public static final String UPLOAD_HOSTELSTUDENT_SUCCESS = "knowledgepro.admissionForm.upload.hostelStudent.success";
	public static final String UPLOAD_HOSTELSTUDENT_FAILURE = "knowledgepro.admissionForm.upload.hostelStudent.failure";
	public static final String HOSTEL_DISCIPLINARY_DETAILS_EXISTS = "knowledgepro.hostel.DisciplinaryDetails.Exists";
	public static final String EMPLOYEE_JOB_REJOIN_INVALID = "employee.info.job.rejoindate.invalid";
	public static final String DISPLAY_HOSTEL_RESERVED="displayHostelReserved";
	public static final String HOSTEL_ALLOCATION_SELECT_ONE_HOSTEL="knowledgepro.hostel.allocation.hostel.required";
	public static final String DISPLAY_HOSTEL_ALLOTED="displayHostelAlloted";
	public static final String STUD_REQ_STATUS_SEARCH = "studReqStatusSearch";
	public static final String BIRT_FACULTY_REPORT = "birtFacultyReport";
	public static final String BIRT_FACULTY_REPORT_RESULT = "birtFacultyReportResult";
	public static final String HOSTEL_PAYMENT_BILL_NUMBER_PAGE = "displayBillNumbers";
	public static final String INIT_STUDENT_FEEDBACK = "initStudentFeedback";
	public static final String STUDENT_FEEDBACK_ADDSUCCESS = "knowledgepro.student.feedback.add.success";
	public static final String STUDENT_FEEDBACK_ADDFAILURE = "knowledgepro.student.feedback.add.failure";
	public static final String HOSTEL_FEES_AMOUNT_NOTVALID = "knowledgepro.hostel.feesamount.notvalid";
	public static final String EMPLOYEE_INITALIZE_LEAVE = "employeeInitalizeLeave";
	public static final String INIT_RESET_LEAVES = "initResetLeaves";
	public static final String ATTENDANCE_DATE_REQUIRED="knowledgepro.employee.manualAttendanceEntry.attendanceDate.required";
	public static final String ATTENDANCE_DATE_IS_INVALID="knowledgepro.employee.manualAttendanceEntry.attendanceDate.invalid";
	public static final String IN_TIME_REQUIRED="knowledgepro.employee.manualAttendanceEntry.inTime.required";
	public static final String OUT_TIME_REQUIRED="knowledgepro.employee.manualAttendanceEntry.outTime.required";
	public static final String OUT_TIME_CAN_NOT_BE_LESS_THAN_IN_TIME="knowledgepro.employee.manualAttendanceEntry.outTime.required";
	public static final String ATTENDANCE_DELETED_SUCCESSFULLY = "knowledgepro.attendanceentry.deleted.successfully";
	public static final String ATTENDANCE_UPDATED_SUCCESSFULLY = "knowledgepro.attendanceentry.updated.successfully";
	// SMS Properties
	public static final String TEMPLATE_SMS_REGISTERNO="[REGISTER No]";
	public static final String TEMPLATE_SMS_SUBJECT="[SUBJECT]";
	public static final String TEMPLATE_SMS_DATE="[DATE]";
	public static final String TEMPLATE_SMS_PERIOD="[PERIOD]";
	public static final String TEMPLATE_SUBMISSION_SMS="Submission SMS";
	public static final String TEMPLATE_ATTENDANCE_ENTRY="Attendance Entry SMS";
	public static final String TEMPLATE_ATTENDANCE_CORRECTION ="Attendance Correction SMS";
	public static final String SMS_FILE_CFG = "resources/sms.properties";
	public static final String KNOWLEDGEPRO_SENDER_NUMBER = "senderNumber";
	public static final String KNOWLEDGEPRO_SENDER_NAME = "senderName";
	public static final String TEMPLATE_INITSMSTEMPLATE = "initSMSTemplate";
	public static final String ATTENDANCE_FILE_FOLDER="knowledgepro.attendanceentry.attendance.file.folder";
	// Mobile No upload
	public static final String UPLOAD_MOBILE_NUMBER = "uploadMobileNumber";
	public static final String UPLOAD_MOBILENUMBER = "knowledgepro.admission.excelupload.MobileNumber";
	public static final String UPLOAD_MOBILENUMBER_SUCCESS = "knowledgepro.admissionForm.upload.MobileNumber.success";
	public static final String UPLOAD_MOBILENUMBER_FAILURE = "knowledgepro.admissionForm.upload.MobileNumber.failure";
	public static final String UPLOAD_MOBILENUMBER_CSV = "knowledgepro.admission.MobileNumber.csvupload";
	public static final String INIT_VIEW_ACTIVITY_ATTENDANCE = "initViewActivityAttendance";
	public static final String VIEW_ACTIVITY_ATTENDANCE = "viewActivityAttendance";
	public static final String INIT_SMS_TO_CLASS = "initSmsToClass";
	public static final String SMS_SENT_SUCCESSFULLY = "knowledgepro.admission.smsSent.successfully";
	public static final String SMS_SENT_FAILED = "knowledgepro.admission.smsSent.failure";
	public static final String FORGOT_PASSWORD = "forgotPassword";
	public static final String STUDENTLOGIN_MARKS_CARD_AGREEMENT = "marksCardAgreement";
	public static final String STUDENTLOGIN_HALL_TICKET_BLOCK_MES = "hallTicketBlockMes";
	public static final String STUDENTLOGIN_MARKS_CARD_BLOCK_MES = "marksCardBlockMes";
	public static final String INIT_BOARD_DETAILS = "initBoardDetails";
	public static final String BOARD_DETAILS_RESULT = "boardDetailsResult";
	public static final String RE_GENERATE_PASSWORD = "reGeneratePassword";
	//  Special Achivements
	public static final String SPECIAL_ACHIVEMENTS = "specialAchivementspage";
	public static final String ACHIVEMENTS_ADDED_SUCCESS = "knowledgepro.admission.added.success";
	public static final String ACHIVEMENTS_ADDED_FAILED = "knowledgepro.admission.added.failed";
	public static final String REGISTERNO_INVALID = "knowledgepro.admission.regno.invalid";
	//Exam Center
	public static final String EXAM_CENTER = "examCenter";
	public static final String UNIVERSITY_ENTRY = "universityPage";
	public static final String TRANSFER_CERTIFICATE = "transferCertificate";
	public static final String TRANSFER_CERTIFICATE_PRINT = "transferCertficatePrint";
	public static final String FEE_ASSIGNMENT_COPY = "feeAssignmentCopy";
	public static final String DEMAND_SLIP_INST = "demandSlip";
	public static final String FEE_COPY_SUCCESS="knowledgepro.fee.feeAssignment.copy.semester.success";
	public static final String FEE_COPY_FAILED="knowledgepro.fee.feeAssignment.copy.semester.failure";
	public static final String FEE_COPY_NO_RECOREDS="knowledgepro.fee.feeAssignment.copy.no.records.found";
	public static final String FEE_COPY_ALREADY_EXISTS="knowledgepro.fee.feeAssignment.copy.already.exist";
	public static final String ADMISSIONFORM_PHOTO_FILETYPEERROR = "admissionFormForm.online.photo.invalid";
	public static final String ADMISSIONFORM_PHOTO_DIMENSION = "knowledgepro.online.photo.dimension.size";
	
	public static final String COPY_CLASSES = "copyClasses";
	public static final String TEMPLATE_INITINTERVIEWTEMPLATE = "initInterviewTemplate";
	public static final String COPY_CHECKLIST = "copyCheckList";
	public static final String TEMPLATE_PROGRAM_TYPE="[PROGRAMTYPE]";
	public static final String BIRT_PG_MARKS_CARD = "pgMarksCard";
	public static final String BIRT_SUPPLIMENTARYREPORTS = "birtSupplementaryReports";
	public static final String BIRT_SUPPLIMENTARYREPORT_RESULT = "birtSupplementaryReportResults";
	public static final String INIT_UPLOAD_FORMAT = "uploadFormats";
	public static final String UPLOAD_EXCEL_DESTINATION = "knowledgepro.report.exceldestionation";
	public static final String UPDATE_PUBLISH_HALL_TICKET = "updatePublishHallTicket";
	public static final String UPLOAD_INTERNAL_OVER_ALL = "uploadInternalOverAll";
	
	public static final String UPLOAD_EXAM_INTENAL_OVERALL_SUCCESS = "knowledgepro.exam.internal.overall.upload.success";
	public static final String UPLOAD_EXAM_INTENAL_OVERALL_FAILURE = "knowledgepro.exam.internal.overall.upload.failure";
	public static final String UPLOAD_EXAM_INTERNAL = "knowledgepro.exam.internal.overall.excelupload";
	
	public static final String UPLOAD_BIODATA_INFO = "uploadBioData";
	public static final String UPLOAD_BIODATA_INFO_LINK = "exportBioData";
	public static final String UPLOADBIODATA_EXCEL_DESTINATION = "knowledgepro.admission.exceldestionation";
	public static final String STUDENTS_ATTENDANCE_DETAILS_LINK = "studentsAttendanceDetailsLink";
	public static final String BIRT_PRE_EXAM_REPORT = "preExamReport";
	public static final String UPLOAD_INTERNAL_OVER_ALL_ATTENDANCE = "uploadInternalOverAllAttendance";
	public static final String UPLOAD_EXAM_INTENAL_OVERALL_ATTENDANCE_SUCCESS = "knowledgepro.exam.internal.attendance.upload.success";
	public static final String UPLOAD_EXAM_INTENAL_OVERALL_ATTENDANCE_FAILURE = "knowledgepro.exam.internal.attendance.upload.failure";
	
	public static final String UPLOAD_EXAM_FINAL_MARKS = "uploadExamFinalMarks";
	public static final String UPLOAD_PREVIOUSCLASS_INFO = "uploadPreviousClass";
	public static final String UPLOAD_PREVIOUS_CLASS_EXCEL_DESTINATION = "knowledgepro.admission.preclass.exceldestionation";
	public static final String UPLOAD_PREVIOUS_CLASS_LINK = "uploadPreviousClassLink";
	public static final String UPLOAD_STUDENT_SUBJECT_GROUP = "uploadExamStudentSubjectGroup";
	public static final String UPLOAD_SUPPLEMENTARY_INFO_LINK = "uploadSupplementaryLink";
	public static final String UPLOAD_SUPPLEMENTARY_INFO = "uploadSupplementaryInfo";
	public static final String UPLOAD_SUPPLEMENTARY_INFO_EXCEL_DESTINATION = "knowledgepro.admission.sup.exceldestionation";
	public static final String UPLOAD_SUPPLEMENTARY_INFO_CSV_DESTINATION = "knowledgepro.admission.sup.csvdestionation";
	
	public static final String UPLOAD_INTERNAL_SUPPLEMENTARY_INFO = "uploadInternalMarkSupplementaryInfo";
	public static final String UPLOAD_EXAM_INTERNAL_SUPPLEMENTARY = "knowledgepro.exam.internal.supplementary.exceldestionation";
	public static final String UPLOAD_EXAM_INTENAL_SUPPLEMENTARY_SUCCESS = "knowledgepro.exam.internal.supplementary.upload.success";
	public static final String UPLOAD_EXAM_INTENAL_SUPPLEMENTARY_FAILURE = "knowledgepro.exam.internal.supplementary.upload.failure";
	public static final String UPLOAD_INTERNAL_SUPPLEMENTARY_CSV = "knowledgepro.exam.internal.supplementary.csvdestionation";
	
	public static final String BIRT_UG_MARKS_CARD = "ugMarksCard";
	public static final String UPLOAD_EXAM_LINKS_PAGE="uploadLinksPage";
	
	public static final String UPLOAD_EXAM_SUBJECT_GRP_HISTORY_SUCCESS = "knowledgepro.exam.upload.student.subjectgroup.success";
	public static final String UPLOAD_EXAM_SUBJECT_GRP_HISTORY_FAILURE = "knowledgepro.exam.upload.student.subjectgroup.failure";
	
	public static final String UPLOADBIODATA_CSV_DESTINATION = "knowledgepro.exam.biodata.csvdestionation";
	public static final String CIA_OVER_ALL_REPORT = "cialOverAllReport";
	public static final String INIT_GENERATE_PROCESS = "initGenerateProcess";
	public static final String UPLOAD_PREVIOUS_CLASS_CSV_DESTINATION = "knowledgepro.exam.previousClass.csvdestionation";
	public static final String ADMISSION_APPLICATIONSTATUS = "applicationstatus";
	public static final String INIT_MARK_UPLOAD="initMarksUpload";
	public static final String TC_MASTER ="tcMaster";
	public static final String TC_MASTER_EXIST_REACTIVATE = "knowledgepro.admin.tcmaster.alreadyexist.reactivate";
	public static final String TC_MASTER_ACTIVATE_FAILURE = "knowledgepro.admin.tcmaster.activatefailure";
	public static final String TC_MASTER_ACTIVATE_SUCCESS = "knowledgepro.admin.tcmaster.activate.success";
	
	public static final String INIT_TC_DETAILS = "initTCDetails";
	public static final String TC_DETAILS_RESULT = "tcDetailsResult";
	public static final String TC_DETAILS = "tcDetailsByStudent";
	
	public static final String VIEW_SUBJECT_GROUP_HISTORY_DETAIL_PAGE = "viewSubjectGroupHistoryDetailsPage";
	
	public static final String INIT_FORMAT_TC_DETAILS = "initTCFormatDetails";
	public static final String TC_FORMAT_DETAILS_RESULT = "tcFormatDetailsResult";
	public static final String TC_FORMAT_DETAILS = "tcFormatDetailsByStudent";
	public static final String BIRT_SUP_HALL_TICKET = "birtSupHallTicket";
	public static final String STUDENTLOGIN_SUP_HALL_AGREEMENT = "stLoginSupHTAgreement";
	public static final String TRANSFER_CERTIFICATE_PRINT_CHRIST = "transferCertficatePrintChrist";
	public static final String INIT_SPECIAL_PROMOTION = "initSpecialPromotion";
	public static final String SPECIAL_PROMOTION_RESULT="specialPromotionResult";
	public static final String TRANSFER_CERTIFICATE_REPRINT = "initTCReprint";
	public static final String INIT_TC_DETAILS_CLASS_WISE = "initTcDetailsClassWise";
	public static final String TC_DETAILS_RESULT_CLASS_WISE = "tcDetailsClassWiseResult";
	public static final String TC_DETAILS_CLASS_WISE = "tcDetailsClassWise";
	public static final String KNOWLEDGEPRO_ADMISSION_SELECT_STUDENTS = "knowledgepro.admission.select.students";
	public static final String UPDATE_STUDENT_SGPA = "updateStudentSGPA";
	public static final String FEE_ASSIGNMENT_COPY_YEAR_WISE = "feeAssignmentCopyYearWise";
	
	public static final String DOWNLOAD_FORMAT_INIT="initDownloadFormat";
	public static final String INIT_REPRINT_PURCHASE_ORDER = "initRePrintPurchaseOrder";
	
	public static final String EDIT_TERMS_CONDITIONS_CHECK_LIST = "edittermsconditionCheckList";
	public static final String INTERVIEW_DATE_TIME ="InterviewDateTime";
	public static final String INIT_STUDENT_STATUS = "initStudentStatus";
	public static final String DISPLAY_CASH_COLLECTION_NEW = "displayCashCollectionNew";
	public static final String FINAL_APPROVE_DATE="[approved_date]";
	
	public static final String ATTENDANCE_ENTRY_FOR_STAFF_FIRST = "attendanceEntryForStaffFirst";
	public static final String ATTENDANCE_ENTRY_FOR_STAFF_SECOND = "attendanceEntryForStaffSecond";
	public static final String ATTENDANCE_ENTRY_FOR_STAFF_THIRD = "attendanceEntryForStaffThird";
	public static final String ATTENDANCE_ENTRY_FOR_STAFF_INITTHIRD = "attendanceEntryForStaffInitThird";
	public static final String MODIFY_ATTENDANCE_FOR_STAFF = "modifyAttendanceForStaffFirst";
	public static final String MODIFY_ATTENDANCE_FOR_STAFF_SECONDPAGE = "modifyAttendanceForStaffSecond";
	public static final String CANCEL_ATTENDANCE_FOR_STAFF = "cancelAttendanceForStaffFirst";
	public static final String ATTENDANCE_ENTRY_UNABLETOPROCEED4 = "knowledgepro.attendanceentry.unabletoproceed4";
	
	public static final String CERTIFICATE_COURSE_ENTRY = "certificateCourseEntry";
	public static final String CERTIFICATE_COURSE_ACTIVATE_FAILURE = "knowledgepro.admin.certificate.course.activate.failure";
	public static final String CERTIFICATE_COURSE_ACTIVATE_SUCCESS = "knowledgepro.admin.certificate.course.activate";
	public static final String CERTIFICATE_COURSE_REACTIVATE = "knowledgepro.admin.certificate.course.addfailure.alreadyexist.reactivate";	
	
	public static final String EMP_HOLIDAYS = "empHolidays";
	public static final String HOLIDAYS_DETAILS_UPDATED_SUCCESS = "knowledgepro.emp.holidays.update.success";
	public static final String HOLIDAYS_DETAILS_UPDATED_FAILED = "knowledgepro.emp.holidays.update.failure";
	public static final String KNOWLEDGEPRO_HOLIDAYS_ADDEXIST = "knowledgepro.emp.holidays.add.exist";
	public static final String KNOWLEDGEPRO_HOLIDAYS_REACTIVATE = "knowledgepro.emp.holidays.alreadyexist.reactivate";
	public static final String KNOWLEDGEPRO_HOLIDAYS_DELETESUCCESS = "knowledgepro.emp.holidays.delete.success";
	public static final String KNOWLEDGEPRO_HOLIDAYS_DELETEFAILURE = "knowledgepro.emp.holidays.delete.failure";
	public static final String KNOWLEDGEPRO_HOLIDAYS_ACTIVATESUCCESS = "knowledgepro.emp.holidays.activate.success";
	public static final String KNOWLEDGEPRO_HOLIDAYS_ACTIVATEFAILURE = "knowledgepro.emp.holidays.activate.failure";
	public static final String EMP_CANCEL_LEAVE = "initCancelLeave";
	public static final String UPDATE_PASS_FAIL = "updateStudentPassFail";
	public static final String BIRT_COUNSELLOR_REPORT = "counsellorReport";
	public static final String EMPLOYEE_APPROVE_CANCEL_LEAVE = "employeeApproveCancellationLeave";
	public static final String EXAM_SUBJECTRULE_SETTINGS_COPY = "subjectRuleSettingsCopy";
	public static final String INIT_CERTIFICATE_COURSE_TEACHER_ENTRY = "initCertificateCourseTeacher";
	public static final String CERTIFICATE_COURSE_TEACHER_ENTRY = "certificateCourseTeacherEntry";
	public static final String EMPLOYEE_MANUAL_ATTENDANCE_REACTIVATE = "knowledgepro.employee.attendence.reactivate";
	public static final String ATTENDANCE_DETAILS_REACTIVATE = "knowledgepro.employee.attendence.reactivate.sucess";
	public static final String ATTENDANCE_DETAILS_REACTIVATE_FAILED = "knowledgepro.employee.attendence.reactivate.failure";
	public static final String INIT_CERTIFICATE_COURSE = "initCertificateCourse";
	public static final String SHOW_CERTIFICATE_COURSE_DETAILS = "showCertificateCourseDetails";
	public static final String CERTIFICATE_COURSE_PRINT = "certficateCoursePrint";
	public static final String BIRT_SUP_MARKS_CARD = "supMarksCard";
	public static final String STUDENTLOGIN_SUPPLY_MARKS_CARD_BLOCK_MES = "supplyMarksCardBlockMes";
	
	public static final String INIT_APPLN_MANDATORY_FIELD_ENTRY = "initApplnFormMandatory";
	public static final String ADMISSIONFORM_PRESIDANCE_ONLINE_APPLICATIONDETAIL_PAGE = "OnlinePresidanceapplicationDetailPage";
	public static final String HONORS_ENTRY = "honorsEntry";
	public static final String PRINT_CERTIFICATE_COURSE = "printCertificateCourse";
	public static final String UPDATE_PASS_FAIL_CJC = "updatePassFailForCjc";
	public static final String EXAM_BLOCK_UNBLOCK_RESULT_EDIT = "blockUnblockEdit";
	
	public static final String PRC_ADMISSIONFORM_ONLINEAPPLY_PAGE = "prconlineApplyPage";
	public static final String PRC_ADMISSIONFORM_INITGUIDELINE_PAGE = "prcinitonlinelinksPage";
	public static final String PRC_ADMISSIONFORM_INIT_TERMCONDITION_PAGE = "prcinitTermConditionPage";
	public static final String PRC_ADMISSIONFORM_ONLINELINKS_PAGE = "prconlinelinksPage";
	public static final String PRC_ADMISSIONFORM_INIT_PREREQUISITE_PAGE = "prcinitPrerequisitePage";
	public static final String PRC_ADMISSIONFORM_TERMCONDITION_PAGE = "prctermConditionPage";
	public static final String PRC_ADMISSIONFORM_PREREQUISITE_PAGE = "prcprerequisitePage";
	public static final String PRC_ADMISSIONFORM_PRESIDANCE_ONLINE_APPLICATIONDETAIL_PAGE = "prcOnlinePresidanceapplicationDetailPage";
	public static final String PRC_INIT_APPLICANT_SINGLE_PAGE = "prcinitApplicationSinglePage";
	public static final String PRC_DETAIL_APPLICANT_SINGLE_PAGE = "prcdetailApplicationSinglePage";
	public static final String PRC_ADMISSIONFORM_INITMODIFY_PAGE = "prcinitApplicationModifyPage";
	public static final String PRC_ADMISSIONFORM_DETAILMODIFY_PAGE = "prcdetailApplicationModifyPage";
	public static final String PRC_ADMISSIONFORM_DETAILEDIT_PAGE = "prcdetailApplicationEditPage";
	public static final String PRC_ADMISSIONFORM_MODIFY_CONFIRM_PAGE = "prcmodifyconfirmPage";
	public static final String PRC_ADMISSIONFORM_EDIT_CONFIRM_PAGE = "prceditconfirmPage";
	public static final String PRC_ADMISSIONFORM_INITEDIT_PAGE = "prcinitApplicationEditPage";
	public static final String PRC_APPLICANT_SINGLE_CONFIRM_PAGE = "prcconfirmApplicantPage";
	public static final String PRC_ADMISSIONFORM_ONLINE_REVIEW_SEM_MARK_PAGE = "prconlineReviewSemesterPage";
	public static final String PRC_ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE = "prconlineReviewDetailMarkPage";
	public static final String PRC_ADMISSIONFORM_EDIT_SEMESTER_MARK_PAGE = "prceditdetailSemesterPage";
	public static final String PRC_ADMISSIONFORM_EDIT_DETAIL_MARK_PAGE = "prceditdetailMarkPage";
	
	public static final String PRC_VIEW_SUBJECT_GROUP_HISTORY_DETAIL_PAGE = "prcviewSubjectGroupHistoryDetailsPage";
	public static final String PRC_STUDENTEDIT_INITEDITPAGE = "prcinitStudentEditPage";
	public static final String PRC_STUDENTEDIT_EDITLISTPAGE = "prcstudentEditListPage";
	public static final String PRC_STUDENTEDIT_VIEWPAGE = "prcstudentViewPage";
	public static final String PRC_STUDENTEDIT_CONFIRM_PAGE = "prceditStudentconfirmPage";
	public static final String PRC_STUDENTEDIT_EDITPAGE = "prcstudentEditPage";
	public static final String PRC_STUDENTEDIT_REMARK_PAGE = "prcstudentRemarkPage";
	
	public static final String PRC_STUDENTEDIT_DETAIL_MARK_PAGE = "prceditStudentdetailMarkPage";
	public static final String PRC_STUDENTEDIT_SEM_MARK_PAGE = "prceditStudentSemesterMarkPage";
	public static final String PRC_STUDENTEDIT_LATERAL_MARK_PAGE = "prceditStudentLateralPage";
	public static final String PRC_STUDENTEDIT_TRANSFER_PAGE = "prceditStudentTransferPage";
	public static final String PRC_STUDENTCREATE_INIT_PAGE = "prcinitCreateStudentPage";
	public static final String PRC_STUDENTCREATE_DETAIL_PAGE = "prcdetailCreateStudentPage";
	public static final String PRC_STUDENTCREATE_DETAIL_SEMESTER_PAGE = "prccreateStudentSemesterPage";
	public static final String PRC_STUDENTCREATE_DETAIL_MARK_PAGE = "prccreateStudentDetailMarkPage";
	
	public static final String PRC_STUDENTCREATE_LATERAL_PAGE = "prcdetailCreateStudentLateralPage";
	public static final String PRC_STUDENTCREATE_TRANSFER_PAGE = "prcdetailCreateStudentTransferPage";
	public static final String PRC_STUDENTCREATE_CONFIRM_PAGE = "prcconfirmCreateStudentPage";
	
	public static final String PRC_ADMISSIONFORM_FIRST_PAGE = "prcapplicationSearchDetail";
	public static final String PRC_ADMISSIONFORM_COURSE_SELCTION_PAGE = "prcofflineCourseSelctionPage";
	public static final String PRC_ADMISSIONFORM_OFFLINE_PREREQUISITE_PAGE = "prcofflinePrerequisitePage";
	public static final String PRC_ADMISSIONFORM_APPLICATIONDETAIL_PAGE = "prcapplicationDetailPage";
	public static final String PRC_ADMISSIONFORM_INIT_STUDENT_PAGE = "prcinitAdmissionFormStudentPage";
	public static final String PRC_ADMISSIONFORM_STUDENTPAGE = "prcadmissionFormStudentPage";
	public static final String PRC_ADMISSIONFORM_INIT_EDUCATION_PAGE = "prcinitAdmissionFormEducationPage";
	public static final String PRC_ADMISSIONFORM_EDUCATION_PAGE = "prcadmissionFormEducationPage";
	public static final String PRC_ADMISSIONFORM_INIT_ATTACHMENT_PAGE = "prcinitFormAttachmentPage";
	public static final String PRC_ADMISSIONFORM_ATTACHMENT_PAGE = "prcadmissionFormAttachmentPage";
	public static final String PRC_ADMISSIONFORM_INIT_PARENT_PAGE = "prcinitFormParentPage";
	public static final String PRC_ADMISSIONFORM_PARENT_PAGE = "prcadmissionFormParentPage";
	public static final String PRC_ADMISSIONFORM_CONFIRMSUBMIT_PAGE = "prcapplicationConfirmPage";
	public static final String PRC_ADMISSIONFORM_CONFIRMPRINT_PAGE = "prcapplicationConfirmPrintPage";
	public static final String EXAM_CONSOLIDATE_MARKSCARD = "consolidateMarksCard";
	public static final String KNOWLEDGEPRO_CONSOLIDATE_MARKSCARD_ADD_SUCCESS = "knowledgepro.exam.consolidate.markscard.added.success";
	public static final String KNOWLEDGEPRO_CONSOLIDATE_MARKSCARD_ADD_FAILURE = "knowledgepro.exam.consolidate.markscard.added.failure";
	
	public static final String ATTENDANCE_REMOVE = "attendanceRemove";
	public static final String UPLOAD_STUDENT_BLOCK = "uploadStudentBlock";
	public static final String ATTENDANCE_REMOVE_SECOND = "attendanceRemoveSecond";
	public static final String FEE_PAYMENT_PRINTCHALLAN_CJC = "printChallenCJC";
	
	public static final String BIRT_MARKS_VERIFICATION_REPORT = "birtMarksVerificationReport";
	public static final String BIRT_MARKS_VERIFICATION_REPORT_RESULT = "birtMarksVerificationReportResult";
	public static final String BULK_CHALLAN_PRINT="bulkChallanPrint";
	public static final String ADM_REPORT_PRE_REQ = "PreRequisite";
	public static final String STUDENTLOGIN_HALL_TICKET = "HallTicket";
	public static final String STUDENTLOGIN_HALL_TICKET_PRINT = "HallTicketPrint";
	public static final String STUDENTLOGIN_UG_MARKS_CARD = "slUgMarksCard";
	public static final String STUDENTLOGIN_PG_MARKS_CARD = "slPgMarksCard";
	public static final String PRINT_PG_MARKS_CARD = "printPgMarksCard";
	public static final String PRINT_UG_MARKS_CARD = "printUgMarksCard";
	public static final String STUDENTLOGIN_SUP_HALL_TICKET = "suppHallTicket";
	public static final String STUDENTLOGIN_SUP_HALL_TICKET_PRINT = "printSuppHallTicket";
	public static final String STUDENTLOGIN_SUP_UG_MARKS_CARD = "supUgMarksCard";
	public static final String STUDENTLOGIN_SUP_PG_MARKS_CARD = "supPgMarksCard";
	public static final String PRINT_SUP_UG_MARKS_CARD = "printSupUgMarksCard";
	public static final String PRINT_SUP_PG_MARKS_CARD = "printSupPgMarksCard";
	public static final String MARKS_CARD_DESCRIPTION="1)CIA-Continuous Internal Assessment 2) ESE-End Semester Exam 3)ATT-Attendance 4)* Failed to attend the regular Mid-Semester Examination <br/> 5)** Failed to  attened the regular and repeat Mid-Semester Examination 6) Candidates with more than 75% attendance only will be awarded marks for attendance. 4) Details of Grade - 90-Above (A, 4.0)   85-89 (BA-, 3.5)   80-84 (B, 3.0)   75-79 (CB, 2.5)   70-74 (C, 2.0)   65-69 (DC, 1.5)  " +
			" 60-64 (D, 1.0) 0-59 (E, 0.0)  <br/> <br/> <br/> <br/> Disclaimer : This is a computer generated marks card for the information of the examinee. Neither the Controller of Examination nor the University is responsible for any inadvertent error that might have crept in the results published on the University website.  " +
			" <br/>This cannot be treated as original marks card. Consolidated Marks card/Transcript in original will be provided on successful completion of the course.";
	public static final String INIT_STUDENT_PROMOTION = "initStudentPromotion";
	public static final String STUDENT_PROMOTION_RESULT = "studentPromotionResult";
	public static final String INIT_TRANSFER_CERTIFICATE = "initTransferCertificate";
	public static final String INIT_UPDATE_TC_FORMAT_DETAILS = "initUpdateTCDetails";
	public static final String PRINT_TC_CHRIST = "printTCChrist";
	
	public static final String MOTHERTONGUE = "MotherTongue";
	public static final String VIEW_APPRAISAL_DETAILS="viewAppraisalDetails";
	public static final String INIT_VIEW_APPRAISAL="initViewAppraisal";
	public static final String INIT_CERTIFICATE_COURSE_STUDENT = "initCertificateCourseStudent";
	public static final String INIT_CERTIFICATE_COURSE_PAGE1 = "CertificateCourseStudent1";
	public static final String PRINT_CERTIFICATE_COURSE_STUDENT = "printCertificateCourseStudent";
	
	public static final String INIT_REPORT = "initReport";
	public static final String VIEW_ATTENDANCE = "viewAttendance";
	public static final String VIEW_ATTENDANCE_SECONDPAGE = "viewAttendanceSearch";
	public static final String REPORT_ADDMISSION_STATUS = "Admission Status";
	public static final String IS_ADMITTED = "Is Admitted";
	public static final String CURRENT_CLASS = "Current Class";
	public static final String VIEW_HISTORY_CLASS_GROUP_PAGE="viewClassGroupHistory";
	public static final String BIRT_EXAM_REPORT_CJC = "birtExamReportCJC";
	public static final String BIRT_ADMISSION_REPORT_CJC="birtAdmissionReportCJC";
	public static final String BIRT_FACULTY_REPORT_CJC ="birtFacultyReportCJC";
	public static final String BIRT_FEE_REPORT_CJC = "birtFeeReportCJC";
	public static final String BIRT_SUPPLIMENTARYREPORTS_CJC = "birtSupplimentayReportsCJC";
	public static final String BIRT_PRE_EXAM_REPORT_CJC = "birtPreExamReportCJC";
	public static final String BIRT_MARKS_VERIFICATION_REPORT_CJC = "birtMarksVerificationReportCJC";
	public static final String KNOWLEDGEPRO_ADMISSION_STUDENT_ISACTIVE = "knowledgepro.admission.student.canceled";
	public static final String ASSIGN_CERTIFICATE_COURSE = "assignCertificateCourse";
	public static final String INIT_CANCELLED_CERTIFICATE_COURSE_STUDENT = "initCancelledStudentCertificateCourse";
	public static final String INIT_CANCELLED_CERTIFICATE_COURSE_STUDENT_RESULT = "initCancelledStudentCertificateCourseResult";
	public static final String PRINT_ADDRESS = "initPrintAddress";
	public static final String ADDRESS_TEMPLATE = "Address Certificate";
	public static final String SHOW_ADDRESS = "showAddress";
	public static final String NO_RECORD_ADDRESS = "noRecord";
	public static final String EXAM_MARKS_ENTRY_INPUT = "initMarksEntry";
	public static final String EXAM_MARKS_ENTRY_RESULT = "examMarksEntryResult";
	public static final String ATTENDANCE_SLIP_DETAILS="attendanceSlipDetails";
	public static final String PRINT_ATTENDANCE_SLIPDETAILS="printAttendanceSlipDetails";
	public static final String TEMPLATE_ACADEMICYEAR = "[ACADEMIC_YEAR]";
	public static final String INIT_PRINTDOB = "initPrintDOB";
	public static final String SHOW_DOB = "showPrintDOB";
	public static final String DOB_TEMPLATE = "Date Of Birth";
	public static final String TEMPLATE_FIRST_YEAR = "[FIRST_YEAR]";
	public static final String TEMPLATE_LAST_YEAR = "[LAST_YEAR]";
	public static final String TEMPLATE_DOB_REGISTERNO = "[REGISTER_NO]";
	public static final String INIT_CERTIFICATE = "initCertificate";
	public static final String SHOW_CERTIFICATE = "showCertificate";
	public static final String TEMPLATE_CLASS = "[CLASS]";
	public static final String TEMPLATE_CHARACTER = "[CHARACTER]";
	public static final String TEMPLATE_STREAM = "[STREAM]";
	public static final String TEMPLATE_SEMISTER = "[SEM]";
	public static final String TEMPLATE_SEMYEAR = "[SEMYEAR]";
	public static final String CHARACTER_TEMPLATE = "Character Certificate";
	public static final String INIT_COMMONTEMPLATE = "initCommonTemplate";
	public static final String TEMPLATE_NODUE = "No Due";
	public static final String TEMPLATE_FATHERNAME = "[FATHERNAME]";
	public static final String TEMPLATE_SONOF = "[S/O]";
	public static final String SHOW_NODUE = "showNoDue";
	public static final String TEMPLATE_VISALETTER = "Visa Letter";
	public static final String INIT_VISALETTER = "initVisaLetter";
	public static final String TEMPLATE_PASSPORTNO = "[PASSPORT_NO]";
	public static final String INIT_COMMONTEMPLATE_ATTEMPT = "initAttemptCertificate";
	public static final String TEMPLATE_ATTEMPTCERTIFICATE = "Attempt Certificate";
	public static final String TEMPLATE_NO_ATTEMPT = "[NO_OF_ATTEMPTS]";
	public static final String TEMPLATE_EXAM_YEAR = "[EXAMYEAR]";
	public static final String SHOW_ATTEMPTCERTIFICATE = "showAttemptCertificate";
	public static final String INIT_PRINT_COMMONTEMPLATE = "initPrintCommonTemplate";
	public static final String INIT_COMMONTEMPLATE_FEE = "initFeeDetails";
	public static final String TOTAL_FEE_PAID = "[FEEPAID]";
	public static final String FEE_RECEIPTNO = "[RECEIPT_NO]";
	public static final String TEMPLATE_FEE_DATE = "[FEEDATE]";
	public static final String TEMPLATE_FEEDETAILS = "Fee Details";
	public static final String INIT_COMMONTEMPLATE_SCHOLARSHIP = "initScholarship";
	public static final String TEMPLATE_SCHOLARSHIP = "Scholarship Details";
	public static final String INIT_COMMONTEMPLATE_MEDIUM = "initMediumTemplate";
	public static final String TEMPLATE_MEDIUM = "Medium Of Instruction";
	public static final String PRINT_COMMONTEMPLATE = "printTemplate";
	public static final String TEMPLATE_EXAM_REG_NO = "[EXAM_REG_NO]";
	public static final String INIT_COMMONTEMPLATE_SPORTS = "initSportsPage";
	public static final String TEMPLATE_SPORTS = "Sports Template";
	public static final String INIT_COMMONTEMPLATE_FOREIGN = "initForeignTemplate";
	public static final String TEMPLATE_FOREIGN = "Foreign NOC";
	public static final String TEMPLATE_GENDER1 = "[HE/SHE]";
	public static final String TEMPLATE_START_DATE = "[START_DATE]";
	public static final String TEMPLATE_END_DATE = "[END_DATE]";
	public static final String TEMPLATE_COLLEGE_OPENDATE = "[REOPEN_DATE]";
	public static final String INIT_COMMONTEMPLATE_TUTIONFEE = "initTutionFee";
	public static final String TEMPLATE_TUTIONFEE = "Tuition Fee";
	public static final String TEMPLATE_SPORTS_NAMES = "[SPORTS]";
	public static final String IS_SELECTED = "Is Selected";
	public static final String IS_FINAL_MERIT_APPROVED = "Is Final Merit Approved";
	public static final String SUBJECT_GROUP_DETAILS_INIT = "subjectGroupDetailsInit";
	public static final String BIRT_ATTENDANCE_REPORT_CJC = "birtAttendanceReportCJC";
	public static final String BIRT_ATTENDANCE_REPORT = "birtAttendanceReport";
	public static final String SECURED_MARKS_ENTRY_INPUT = "initSecuredMarksEntry";
	public static final String SECURED_MARKS_ENTRY_RESULT = "securedMarksEntryResult";
	public static final String SECURED_MARKS_ENTRY_AJAX = "ajaxStudentRespose";
	public static final String TUITION_FEE = "[TUITION_FEE]"; 
	public static final String FEE_INWORDS = "[FEE_INWORDS]"; 
	public static final String FEE_GROUPS = "[FEEGROUP]";
	public static final String INIT_COMMONTEMPLATE_TRANSCRIPTPU_ONE = "initTranscriptFirstPU";
	public static final String EMPLATE_MARKTRANSCRIPT_ONE = "MARK TRANSCRIPT- I PU";
	public static final String EMPLATE_MARKTRANSCRIPT_TWO = "MARK TRANSCRIPT - II PU";
	public static final String EMPLATE_MARKTRANSCRIPT_ONE_AND_TWO = "MARK TRANSCRIPT - I and II PU";
	public static final String SECURED_MARKS_ENTRY_MARKS_AJAX = "ajaxMarksResponse";
	public static final String ATTENDANCE_TEACHER_SUMMARY_REPORT= "attendanceTeacherSummaryReport";
	public static final String NO_RECORDS = "No Records";
	public static final String ADMISSIONFORM_HIDEDT_FUTURE = "knowledgepro.admission.studentedit.hide.date.futuredate";
	public static final String ADMISSIONFORM_HIDEDT_INVALID = "knowledgepro.admission.studentedit.hide.date.Invalid";
	public static final String ADMISSIONFORM_HIDESTUDENTDATE_REQUIRED = "knowledgepro.admission.studentedit.hide.date";
	public static final String ADMISSIONFORM_HIDESTUDENTREASON_REQUIRED = "knowledgepro.admission.studentedit.hide.reason";
	public static final String ADMISSIONFORM_HIDESTUDENT_CANNOTDETAIN="knowledgepro.admission.studentedit.hide.cannotdetain";
	public static final String UPDATE_PROCESS_INIT = "initUpdateProcess";
	public static final String UPDATE_PROCESS_RESULT = "updateProcessResult";
	public static final String USER_NAME = "User Name";
	public static final String PASSWORD = "Password";
	public static final String INTERNAL_MARKS_SUPPLEMENTARY = "internalMarksSupplementary";
	public static final String INTERNAL_MARKS_SUPPLEMENTARY_RESULT = "internalMarksSupplementaryResult";
	public static final String TEMPLATE_GENDER2="[his/her]";
	public static final String TEMPLATE_MARKSHEET ="[MARK_SHEET]";
	public static final String ADMISSION_TC_DETAILS_OLD_STUDENTS = "tcDetailsOldStudents";
	public static final String ADMISSION_TC_DETAILS_ADD_OLD_STUDENTS="tcDetailsAddOldStudents";
	public static final String TEMPLATE_RESULT = "[RESULT]";
	public static final String STUDENT_MARKS_CORRECTION = "studentMarksCorrection";
	public static final String STUDENT_MODERATION = "initModeration";
	public static final String STUDENT_MARKS_CORRECTION_RESULT = "studentMarksCorrectionResult";
	public static final String STUDENT_MODERATION_SELECT = "selectModerationStudent";
	public static final String ADMISSION_TC_DETAILS_ADDED_OLD_STUDENTS="knowledgepro.admin.studenttcadded";
	public static final String ADMISSION_TC_DETAILS_CONFIGURE_TC_MASTER="knowledgepro.admin.configuretcmaster";
	public static final String ADMISSION_TC_DETAILS_EDIT_OLD_STUDENTS="tcDetailsEditOldStudents";
	public static final String ADMISSION_TC_DETAILS_PRINT_OLD_STUDENTS="printTcDetailsOldStudents";
	public static final String ADMISSION_TC_DETAILS_INVALID_DATEOFISSUE="knowledgepro.admin.dateofissueinvalid";
	public static final String ADMISSION_TC_DETAILS_INVALID_DATEOFAPP="knowledgepro.admin.dateofAppinvalid";
	public static final String ADMISSION_TC_DETAILS_ADLELD="knowledgepro.admin.admdatele";
	public static final String ADMISSION_TC_DETAILS_LDLEAD="knowledgepro.admin.ldlead";
	public static final String ADMISSION_TC_DETAILS_ADLEID="knowledgepro.admin.adleid";
	public static final String ADMISSION_TC_DETAILS_REL_REQUIRED="knowledgepro.admin.religion.other";
	public static final String ADMISSION_TC_DETAILS_NATIONALITY_REQUIRED="knowledgepro.admin.nationality.other";
	public static final String ADMISSION_TC_DETAILS_CASTE_REQUIRED="knowledgepro.admin.caste.other";
	public static final String ADMISSION_TC_DETAILS_SUBJECT_PASSED="knowledgepro.admin.subject.passed.required";
	public static final String ADMISSION_TC_DETAILS_DNGEYEAT="knowledgepro.admin.dob.cge.acayear";
	public static final String ADMISSION_TC_DETAILS_YLEAY="knowledgepro.admin.year.cle.acayear";
	public static final String STUDENT_MARKS_CORRECTION_VIEW ="studentMarksCorrectionView";
	public static final String INIT_ASSIGN_CLASS_TEACHER = "initAssignClassToTeacher";
	public static final String ENTRY_DUPLICATE="knowledgepro.atten.duplicate.entry";
	public static final String STUDENT_ATTENDANCE_FOR_EXAM_INPUT = "studentAttendanceForExamInput";
	public static final String EMP_RESUME = "employeeResume";
	public static final String EMP_LEAVEENTERY = "employeeLeaveEntry";
	public static final String EMP_TYPE = "employeeType";
	public static final String EXAM_EVENT ="examEvent";
	public static final String EXC_DETAILS ="ExceptioinDetails";
	public static final String VOC_DETAILS = "vocationalDetails";
	public static final String ATTENDANCE_STUDENT_ABSENTS= "attendanceStudentAbsenties";
	public static final String ADMISSION_REGNO_EXISTS="knowledgepro.admission.regno.exists";
	public static final String EDIT_SUBJECT_GROUP_HISTORY_DETAIL_PAGE = "editSubjectGroupHistoryDetailsPage";
	public static final String PREVIOUS_ATTENDANCE_DISPLAY= "previousAttendanceDisplay";
	public static final String EMP_INFORMATION = "empInformation";
	public static final String EMP_LEAVEDETAILS= "leaveDetails";
	public static final String MODIFY_LEAVE="modifyLeave";
	public static final String ADD_EMP_INFO = "addEmployeeInfo";
	public static final String SEARCH_EMP_INFO ="searchEmpInfo";
	public static final String LEAVE_ALLOTMENT = "leaveAllotment";
	public static final String DOWNLOAD_RESUME ="downloadResume";
	public static final String EMP_LEAVE_DETAILS ="employeeLeaveDetails";
	public static final String INIT_EMP_LEAVE_DETAILS ="initemployeeLeaveDetails";
	public static final String EMP_INTERVIEW = "initInterviewComments";
	public static final String EMP_INTERVIEW_COMMENTS = "employeeInterviewComments";
	public static final String EMP_INTERVIEW_COMMENTS_UPDATE = "employeeInterviewCommentsUpdate";
	public static final String EMP_INTERVIEW_COMMENTS_EDIT = "employeeInterviewCommentsEdit";
	public static final String TEMPLATE_GENDER_HIS = "[HIS/HER]";
	public static final String ATTENDANCE_INCHARGE_TEACHER_DETAILS= "attendanceInchargeTeacher";
	public static final String INIT_ATTENDANCE_BATCH = "initAttendanceBatch";
	public static final String INIT_ATTENDANCE_BATCH_RESULT = "initAttendanceBatchResult";
	public static final String ADMISSIONFORM_DISCONTINUEDSTUDENTDATE_REQUIRED = "knowledgepro.DiscontinuedDateRequired";
	public static final String ADMISSIONFORM_DISCONTINUEDSTUDENTREASON_REQUIRED = "knowledgepro.DiscontinuedReasonIsRequired";
	public static final String ADMISSIONFORM_DETENTIONSTUDENTDATE_REQUIRED = "knowledgepro.DetentionDateisrequired";
	public static final String ADMISSIONFORM_DETENTIONSTUDENTREASON_REQUIRED = "knowledgepro.DetentionReasonisrequired";
	public static final String STUDENTFEEDBACK_MOBILENO = "knowledgepro.studentfeedback.mobileno.invalid";
	public static final String STUDENT_FEEDBACK_MAIL_ID = "knowledgepro.studentfeedback.mail.id";
	public static final String STUDENT_FEEDBACK_MAIL_SENT = "knowledgepro.studentfeedback.mail.sent";
	public static final String STUDENT_FEEDBACK_MAIL_SENT_FAILED ="knowledgepro.studentfeedback.mail.sent.failed";
	public static final String LOGIN_STUDENT="STUDENT";
	public static final String LOGIN_ADMIN="ADMIN";
	public static final String ADMISSION_STUDENT_DETAINED="DETAINED";
	public static final String ADMISSION_STUDENT_DISCONTINUED="DISCONTINUED";
	public static final String ADMISSION_STUDENT_HIDDEN="HIDDEN";
	public static final String SECURED_MARKS_VERIFICATION_INPUT = "initSecuredMarksVerification";
	public static final String SECURED_MARKS_VERIFICATION_RESULT = "initSecuredMarksVerificationResult";
	public static final String SECURED_MARKS_VERIFICATION_AJAX = "ajaxStudentVerficationRespose";
	public static final String EMP_INIT_HOLIDAYS = "holidays";
	public static final String EMP_INTERVIEW_ATTRIBUTES = "initInterviewAttributes";
	public static final String EMP_ADD_INTERVIEW_COMMENTS = "initAddInterviewComments";
	public static final String FEE_PAYMENT_CHALLENPRINT = "Challan canceled for this bill No";
	public static final String STUDENT_ABSENCE_PERIOD_DETAILS = "studentAbsencePeriodDetails";
	public static final String TC_OLD_STUDENT_PRINT="initTcOldStudentPrint";
	public static final String STUDENT_MIDSEMRESULT = "showMidSemResult";
	public static final String INIT_TEACHER_DEPARTMENT = "initTeacherDepartment";
	public static final String DUPLICATE_RECORDS = "This entry is alredy exists please verify it";
	public static final String STUDENTDETAILVIEW_SUP_PG_MARKS_CARD = "supPgMarksCardView";
	public static final String REPORT_MODULE_ENTRY = "reportModuleEntry";
	public static final String INIT_REPORT_PRIVILEGE = "initreportprivilege";
	public static final String REPORT_PRIVILEGE_VIEW ="reportprivilegeview";
	public static final String REPORT_MENU_SCREEN = "module_report_screen_master";
	public static final String FEE_BILLNO_CANCELED = "knowledgepro.feepayment.challan.print.billno";
	public static final String BIRT_REPORT_LINK = "birtFeeLink";
	public static final String MARKS_CARD_DISPLAY_AJAX = "ajaxMarksCardDisplay";
	public static final String SUP_MARKS_CARD_DISPLAY_AJAX = "ajaxSupMarksCardDisplay";
	public static final String EMP_VIEWLEAVE = "initViewEmployeeLeave";
	public static final String EMP_PAYSCALE = "initPayScale";
	public static final String EMP_VIEWLEAVE_DETAILS = "initViewLeaveDetails";
	public static final String VIEW_EMPINFO = "initViewEmployeeInfo";
	public static final String AJAX_PREVIOUSATTENDANCE_DISPLAY = "ajaxPreviousAttendanceDisplay";
	public static final String QUALIFICATION_LEVEL = "qualificationLevel";
	public static final String EMP_EVENT_VACATION="empEventVacation";
	public static final String TODATE_CANNOTLESS_FROMDATE="knowledgepro.attendance.activityattendence.greaterenddate";
	public static final String QUALIFICATION_LEVEL_REACTIVATE = "knowledgepro.employee.qualification.level.addfailure.alreadyexist.reactivate";
	public static final String QUALIFICATION_LEVEL_ACTIVATE_FAILURE = "knowledgepro.employee.qualification.level.activatefailure";
	public static final String QUALIFICATION_LEVEL_ACTIVATE_SUCCESS = "knowledgepro.employee.qualification.level.activate.success";
	public static final String TEMPLATE_GENDER_HE = "[HE/SHE]";
	public static final String INIT_COMMONTEMPLATE_ADDRESS_DOB = "initAddressAndDOB";
	public static final String ADDRESS_AND_DOB_TEMPLATE = "Address And Date Of Birth";
	public static final String UPLOAD_EMPLOYEE_DETAILS="uploademployeedetails";
	public static final String UPLOAD_EMPLOYEE_EXCEL="knowledgepro.employee.employee.upload";
	public static final String UPLOAD_EMPLOYEE_SUCCESS="knowledgepro.employee.uploaded.success";
	public static final String UPLOAD_EMPLOYEE_FAILED="knowledgepro.employee.uploaded.failure";
	public static final String INIT_SUPPL_IMP_APP = "initSupplementaryImprovementApplication";
	public static final String INIT_SUPPL_IMP_APP_RESULT = "supplementaryImpApplResult";
	public static final String INIT_SUPPL_IMP_APP_STUDENT = "supplementaryImpApplStudent";
	public static final String INTERVIEW_RATING_FACTOR = "initInterviewRatingFactor";
	public static final String INIT_EMP_TYPE="initEmpType";
	public static final String EMP_TYPE_EXIST="knowledgepro.emp.type.exist";
	public static final String EMP_TYPE_ADDED="knowledgepro.emp.added.success";
	public static final String EMP_TYPE_UPDATE="knowledgepro.emp.update.success";
	public static final String EMP_TYPE_DELETE="knowledgepro.emp.delete.success";
	public static final String EMP_TYPE_TIME_IN="knowledgepro.empType.timein.notvalid";
	public static final String EMP_TYPE_TIME_IN_ENDS="knowledgepro.empType.timeinEnd.notvalid";
	public static final String EMP_TYPE_TIME_OUT="knowledgepro.empType.timeout.notvalid";
	public static final String EMP_TYPE_SATURDAY_TIME_OUT="knowledgepro.empType.sattimeout.notvalid";
	public static final String EMP_TYPE_TIME_IN_HALFDAY="knowledgepro.empType.halfdaysttime.notvalid";
	public static final String EMP_TYPE_TIME_OUT_HALFDAY="knowledgepro.empType.halfdayendtime.notvalid";
	public static final String EMP_TYPE_TIME_OUT_HALFDAY_LESS="knowledgepro.timein.less.timin.halfday";
	public static final String EMP_TYPE_TIME_OUT_LESS="knowledgepro.timein.less.timin";
	public static final String EMPLOYEE_TYPE_REACTIVATE="knowledgepro.employee.type.reactivate";
	public static final String EMPLOYEE_TIMEIN_TIMEINEND="knowledgepro.employee.type.timein.end.less";
	public static final String EMPLOYEE_TIMEIN_SATTIMEOUT="knowledgepro.employee.type.timein.satout.less";
	public static final String EMPLOYEE_TYPE_RESTORE="knowledgepro.employee.type.restore";
	public static final String EMP_TYPE_NOT_RESTORE="knowledgepro.employee.type.not.restore";
	public static final String INTERVIEW_RATING_FACTOR_REACTIVATE = "knowledgepro.employee.interview.rating.factor.addfailure.alreadyexist.reactivate";
	public static final String INTERVIEW_RATING_FACTOR_ACTIVATE_FAILURE = "Knowledgepro.employee.interview.rating.factor.activate.failure";
	public static final String INTERVIEW_RATING_FACTOR_ACTIVATE="knowledgepro.employee.interview.rating.factor.activate";
	public static final String EMPLOYEE_SETTINGS="initEmpSettings";
	public static final String INIT_SMS_TO_STUDENT = "initSmsStudent";
	public static final String INITSHUDULEDSMS = "initSheduledSMS";
	public static final String EMP_ONLINE_RESUME="initEmpOnlineResume";
	
	public static final String INIT_BLOCK_HALL_TICKET_PROCESS ="initBlockHallTicket";
	public static final String BLOCK_HALL_TICKET_PROCESS_RESULT ="BlockHallTicketResult";
	public static final String INIT_EMP_INTERVIEW_COMMENTS = "initInterviewComments";
	public static final String INIT_EMP_INTERVIEW_COMMENTS_SEARCH = "searchInterviewComments";
	public static final String INIT_EMP_INTERVIEW_COMMENTS_ADD = "addInterviewComments";
	public static final String INIT_COMMONTEMPLATE_TRANSCRIPTPU_TWO = "initTranscriptSecondPU";
	public static final String INIT_TEACHER_VIEW_ATTENDANCE_SUMMARY = "initTeacherViewSummary";
	public static final String INIT_COMMONTEMPLATE_TRANSCRIPTPU_ONE_TWO = "initTranscriptFirstAndSecondPU";
	public static final String Certificate_Subject_Group_Name="certificateSubjectGroupname";
	public static final String Certificate_Subject_Group_Name_Inserted = "certificateSubjectGroupnameinserted";
	public static final String EMPLOYEE_RESUME_SUBMIT="knowledgepro.employee.resume.submit";
	public static final String EMPLOYEE_RESUME_NOT_SUBMIT="knowledgepro.employee.resume.not.submit";
	public static final String HALLTICKET_CLEARANCE_CERTIFICATE = "hallTicketClearanceCertificate";
	public static final String HALLTICKET_CLEARANCE_CERTIFICATE_PRINT = "hallTicketClearanceCertificatePrint";
	public static final String TEMPLATE_TOTAL_FEE_PAID = "[TOTAL_AMOUNT]";
	
	public static final String APPLIED_DATE = "Applied Date";
	public static final String CHALLAN_PAYMENT_DATE = "Challan Payment Date (Application)";
	public static final String FEE_CHALLAN_DATE = "Fee Challan Date";
	public static final String FEE_CHALLAN_NO = "Fee Challan No";
	public static final String TOTAL_FEES_PAID = "Total Fee Paid";
	public static final String IS_HANDICAPED = "Handicaped";
	public static final String HANDICAP_DETAILS = "Handicap Details";
	public static final String EMP_LEAVE_ALLOTMENT = "initLeaveAllotment";
	public static final String INIT_COMMONTEMPLATE_NCC = "initNccPage";
	public static final String INIT_COMMONTEMPLATE_MOTHER_TONGUE = "initMotherTongue";
	public static final String TEMPLATE_NCC = "NCC";
	public static final String TEMPLATE_NCC_CAMPS = "[NCC_CAMPS]";
	public static final String TEMPLATE_MOTHER_TONGUE = "Mother Tongue";
	public static final String TEMPLATE_ADMITTEDCLASS = "[ADMITTED_CLASS]";
	public static final String TEMPLATE_CURRENTCLASS = "[CURRENT_CLASS]";
	public static final String TEMPLATE_ADMITTEDYEAR = "[ADMITTED_YEAR]";
	public static final String TEMPLATE_CURRENTYEAR = "[CURRENT_YEAR]";
	public static final String TEMPLATE_MOTHER_TONGUE_NAME = "[MOTHER_TONGUE]";
	public static final String INIT_EMPLOYEE_LEAVE = "initEmployeeLeave";
	public static final String EMPLOYEE_INFO_SUBMISSION= "EmployeeInfoDetailsDisplay";
	public static final String SUBJECTGROUP_DUPLICATION="Subject group name already exists";
	public static final String EMPLOYEE_LEAVE_RESULT = "employeeLeaveResult";
	public static final String EMPLOYEE_RESUME_SUBMIT_SUCCESS="knowledgepro.employeeResume.addsuccess";
	public static final String EMPLOYEE_RESUME_CONFIRM="employeeResumeConfirm";
	public static final String INIT_LEAVE_INITIALIZE = "initLeaveInitialize";
	public static final String INDUSTRY_EXP_NOT_EMPTY="knowledgepro.employee.industry.exp.yrs";
	public static final String INDUSTRY_EXP_MON_NOT_EMPTY="knowledgepro.employee.industry.exp.months";
	public static final String INDUSTRY_EXP_DESIGNATION_EMPTY="knowledgepro.employee.industry.designation";
	public static final String INDUSTRY_EXP_ORGANIZATION_EMPTY="knowledgepro.employee.industry.organization";
	public static final String TEACHING_EXP_NOT_EMPTY="knowledgepro.employee.teaching.exp.yrs";
	public static final String TEACHING_EXP_MON_NOT_EMPTY="knowledgepro.employee.teaching.exp.months";
	public static final String TEACHING_EXP_DESIGNATION_EMPTY="knowledgepro.employee.teaching.designation";
	public static final String TEACHING_EXP_ORGANIZATION_EMPTY="knowledgepro.employee.teaching.organization";
	public static final String EMPLOYEE_COURSE_REQUIRED="knowledgepro.employee.course.required";
	public static final String EMPLOYEE_SPECIALIZATION_REQUIRED="knowledgepro.employee.specialization.required";
	public static final String EMPLOYEE_YEARCOMPREQUIRED="knowledgepro.employee.yearofcomp.required";
	public static final String EMPLOYEE_GRADE_REQUIRED="knowledgepro.employee.grade.required";
	public static final String EMPLOYEE_INS_UNI_REQUIRED="knowledgepro.employee.ins.uni.required";
	public static final String EMPLOYEE_QUAL_LEVEL_REQ="knowledgepro.employee.qual.level.required";
	public static final String INIT_EMP_INTERVIEW_COMMENTS_VIEW = "viewInterviewComments";
	public static final String MODIFY_EMPLOYEE_LEAVE = "initModifyEmployeeLeave";
	public static final String VIEW_MY_LEAVES = "viewMyLeave";
	public static final String EMPLOYEE_EMP_APPLICANT_MAIL="Employee Application Submission Mail_Applicant";
	public static final String EMPLOYEE_EMP_ADMIN_MAIL="Employee Application Submission Mail_Admin";
	public static final String EMPLOYEE_TO_ADDRESS="knowledgepro.employee.mail.toaddress";
	public static final String EMPLOYEE_DEPARTMENT_TEMPLATE="knowledgepro.employee.department.name";
	public static final String EMPLOYEE_POST_APPLIED_TEMP="knowledgepro.employee.post.appl";
	public static final String EMPLOYEE_QUALIFICATION_LEVEL="knowledgepro.employee.qualification.level";
	public static final String SHOW_INTERVIEW_COMMENTS = "showInterviewComments";
	public static final String EDIT_EMPLOYEE_LEAVE = "editEmployeeLeave";
	public static final String EMPLOYEE_EVENT_REACTIVE="knowledgepro.employee.event.reactivate";
	public static final String VIEW_EMPLOYEE_LEAVES = "viewEmployeeLeaves";
	public static final String CANCEL_EMPLOYEE_LEAVE = "initCancelEmployeeLeave";
	public static final String CANCEL_EMPLOYEE_LEAVE_PAGE = "cancelLeave";
	public static final String EMPLOYEE_PERMANENT_ADDRESS="knowledgepro.employee.address.line1";
	public static final String EMPLOYEE_PERMANENT_COUNTRY="knowledgepro.employee.permanent.country";
	public static final String EMPLOYEE_PERMANENT_STATE="knowledgepro.employee.permanent.state";
	public static final String EMPLOYEE_PERMANENT_CITY="knowledgepro.employee.permanent.city";
	public static final String INIT_UPLOAD_EMP_ATTENDANCE = "initUploadEmpAttendance";
	public static final String UPLOAD_EMPATT_CSV = "knowledgepro.employee.attendance.csvupload";
	public static final String INIT_CREATE_USER = "initCreateUser";
	public static final String DOWNLOAD_EMPLOYEE_RESUME = "initDownloadEmployeeResume";
	public static final String EMPLOYEE_LEAVE_TYPE="empLeaveTypePage";
	public static final String EDIT_USERS="editUsers";
	public static final String UPDATE_USER = "updateUsers";
	public static final String EMP_INFO_SUBMIT_CONFIRM="initEmpInfoSubmitConfirm";
	public static final String PRINT_EMP_RESUME = "printEmpResume";
	public static final String EMPLOYEE_ONLINE_RESUME_FROM_MAIL = "knowledgepro.employee.onlineresume.sendmail.id";
	public static final String EMPLOYEE_LEAVE_REACTIVE="knowledgepro.employee.leavetype.reactivate";
	public static final String EMPLOYEE_LEAVE_ADDED="knowledgepor.employee.leave.added";
	public static final String EMPLOYEE_LEAVE_UPDATE="knowledgepor.employee.leave.edit";
	public static final String EMPLOYEE_LEAVE_DELETE="knowledgepor.employee.leave.delete";
	public static final String EMPLOYEE_LEAVE_RESTORE="knowledgepor.employee.leave.restore";
	public static final String EMPLOYEE_LEAVE_EXIST="knowledgepor.employee.leave.exists";
	public static final String EMPLOYEE_BIOMETRIC_DETAILS="biometricDetails";
	public static final String EMPLOYEE_MOBILE_NO_WRONG="knowledgepro.inventory.mobileno.not.valid";
  
    public static final String EMPLOYEE_INFO_EDIT_SUBMISSION= "EmployeeInfoEditDetailsDisplay";
	public static final String EMPLOYEE_INFO_EDIT= "EmployeeInfoEditDetails";
	
	public static final String EMPLOYEE_LOANDATE_INVALID= "employee.loanDate.invalid";
	public static final String EMPLOYEE_FEECONCESSIONDATE_INVALID= "employee.feeConcessionDate.invalid";
	public static final String EMPLOYEE_FINANCIALDATE_INVALID= "employee.FinancialDate.invalid";
	public static final String EMPLOYEE_INCENTIVESDATE_INVALID= "employee.IncentivesDate.invalid";
	public static final String INIT_PROMOTE_SUPLIMARKS="initSupliMarksReport";
	public static final String INIT_PROMOTE_SUPLIMARKS_RESULT="supliMarksReportResult";
	
	public static final String EMPLOYEE_JOINDATE_INVALID= "employee.joinDate.invalid";
	public static final String EMPLOYEE_LEAVINGDATE_INVALID= "employee.leavingDate.invalid";
	public static final String EMPLOYEE_RESIGNATIONDATE_INVALID= "employee.ResignationDate.invalid";
	public static final String EMPLOYEE_DEPENDANTDOBDATE_INVALID= "employee.DependentDOBDate.invalid"; 
	public static final String EMPLOYEE_PASSEXPDATE_INVALID= "employee.passExpDate.invalid";
	public static final String EMPLOYEE_PASSISSUEDATE_INVALID= "employee.passIssueDate.invalid";
	public static final String EMPLOYEE_REJOINDATE_INVALID= "employee.rejoinDate.invalid";
	public static final String EMPLOYEE_REMARKDATE_INVALID= "employee.remarkDate.invalid";
	public static final String EMPLOYEE_RETIREMENTDATE_INVALID= "employee.retirementDate.invalid";
	public static final String EMPLOYEE_VISAEXPDATE_INVALID= "employee.visaExpDate.invalid";
	public static final String EMPLOYEE_VISSAISSUEDATE_INVALID= "employee.visaIssueDate.invalid";
	public static final String EMP_INFO_ERRORSUBMIT_CONFIRM= "EmployeeNotSubmitted";
	public static final String DISPLAY_ORDER_REACTIVATE = "knowledgepro.employee.display.order.addfailure.alreadyexist.reactivate";
	public static final String INTERVIEW_RATING_FACTOR_DISPLAY_ORDER_REACTIVATE = "knowledgepro.employee.interview.rating.factor.display.order.addfailure.alreadyexist.reactivate";
	public static final String EXPORT_TO_EXCEL = "exportToExcelEmployeeDetails";
	public static final String EMP_EVENT_VACATION_EXISTS="knowledgepro.employee.event.exists";
	public static final String EMP_ONLINE_CURRENTLY_ORG="knowledgepro.employee.current.organization";
	public static final String EMP_ONLINE_CURRENT_DESIG="knowledgepro.employee.current.designation";
	public static final String EMP_CURRENT_OTHER_STATE="knowledgepro.employee.current.other.state";
	public static final String EMP_PERMANENT_OTHER_STATE="knowledgepro.employee.permanent.other.state";
	public static final String EMP_CODE_EXISTS="knowledgepro.employee.code.exists";
	
	public static final String MARKS_CARD_CLEARANCE_CERTIFICATE = "marksCardClearanceCertificate";
	public static final String MARKS_CARD_CLEARANCE_CERTIFICATE_PRINT = "marksCardClearanceCertificatePrint";
	public static final String EMPLOYEE_INFO_APPLICATIONNO="initEmployeeInfo";
	public static final String EMPLOYEE_NOT_VALID_APPLICATIONNO="knowledgepro.employee.InvalidApplicationNo";
	public static final String EMPLOYEE_INFO_EMPLOYEEID="initEditEmployeeInfo";
	public static final String EMPLOYEE_NOT_VALID="knowledgepro.employee.EmployeeId";
	public static final String DOWNLOAD_EMPLOYEE_RESUME_SENDMAIL = "initSendMail";
	public static final String EMPLOYEE_LEAVEALLOTMENT_REACTIVATE = "knowledgepro.employee.leaveAllot.reactivate";
	public static final String VIEW_DEPARTMENT_EMPLOYEE_LEAVES = "viewDepartmentEmpLeaves";
	public static final String UPLOAD_EMP_PHOTOS = "EmployeeUploadPhotos";
	public static final String INIT_ADMIN_MARKS_CARD = "initAdminMarksCard";
	public static final String ADMIN_MARKS_CARD_RESULT = "adminMarksCardResult";
	public static final String EMPLOYEE_QUALIFICATION_LEVEL_TAG="knowledgepro.employee.qualification.level.tag";
	public static final String EMPLOYEE_ONLINE_FROM_ADDRESS="knowledgepro.employee.mail.fromaddress";
	public static final String ADMISSIONFORM_UPLOADCJCDATA = "csvUploadForCjc";
	public static final String TEACHERCLASSENTRY_REACTIVATE = "knowledgepro.attendance.teacherclassentry.reactivate";
	public static final String TEACHER_CLASS_SUBJECT_ACTIVATE_FAILURE = "knowledgepro.attendance.teacherclassentry.reactivate.failure";
	public static final String TEACHER_CLASS_SUBJECT_ACTIVATE_SUCESS = "knowledgepro.attendance.teacherclassentry.reactivate.sucess";
	public static final String BIRT_OVERALL_REPORT = "birtOverAllReport";
	public static final String TEMPLATE_EMPLOYEE_ATTENDANCE_REMAINDER = "Employee Attendance Remainder SMS";
	public static String ERROR_PAGE1 = "errorpage1";
	public static String EMPLOYEE_EDITLISTPAGE="employeeEditListPage";
	public static String EMPLOYEE_CODE_INVALID="knowledgepro.employee.Code.Limit";

	// SMS Template related constants
	public static final String TEMPLATE_EMPLOYEE_NAME = "[EMPLOYEENAME]";
	public static final String TEMPLATE_FINGER_PRINTID = "[FINGERPRINTID]";
	public static String EMPLOYEE_MOBILE_INVALID="knowledgepro.employee.Mobile.Limit";
	public static String EMPLOYEE_HOME_INVALID="knowledgepro.employee.Home.Limit";
	public static String EMPLOYEE_HOME_STATE_INVALID="knowledgepro.employee.Home.state.Limit";
	public static String EMPLOYEE_HOME_COUNTRY_INVALID="knowledgepro.employee.Home.country.Limit";
	public static String EMPLOYEE_WORK_STATE_INVALID="knowledgepro.employee.Work.state.Limit";
	public static String EMPLOYEE_WORK_COUNTRY_INVALID="knowledgepro.employee.Work.country.Limit";
	public static String EMPLOYEE_WORK_INVALID="knowledgepro.employee.Work.Limit";
	public static String EMPLOYEE_EMERGENCY_WORK_INVALID="knowledgepro.employee.Emergency.Work.Limit";
	public static String EMPLOYEE_EMERGENCY_HOME_INVALID="knowledgepro.employee.Emergency.Home.Limit";
    public static String EMPLOYEE_EMERGENCY_MOBILE_INVALID="knowledgepro.employee.Emergency.Mobile.Limit"; 
	public static String EMPLOYEE_UID_INVALID="knowledgepro.employee.UID.Limit";
	public static String EMPLOYEE_PFNO_INVALID="knowledgepro.employee.PfNo.Limit";
	public static String EMPLOYEE_FINGERPRINTID_INVALID="knowledgepro.employee.fingerprintID.Limit";
	public static String EMPLOYEE_PANNO_INVALID="knowledgepro.employee.pannno.Limit";
	public static String EMPLOYEE_OTHERINFO_INVALID="knowledgepro.employee.OtherInfo.Limit";
	public static final String APPLICATION_STATUS_UPDATE = "applicationStatusUpdate";
	public static final String ADDEMPLOYEE_UID_NOTUNIQUE = "employeeeditform.uid.notunique";
	public static final String ADDEMPLOYEE_FINGERPRINTID_NOTUNIQUE = "employeeeditform.fingerprintid.notunique";
	public static final String ADDEMPLOYEE_CODE_NOTUNIQUE = "employeeeditform.code.notunique";
	public static final String UPLOAD_SMART_CARD_NO = "smartCardNoUpload";
	public static final String UPLOAD_SMART_CARD_NUMBER = "knowledgepro.admission.smartcardNoUpload";
	public static final String UPLOAD_SMARTCARD_SUCCESS = "knowledgepro.admission.upload.smartcard.success";
	public static final String UPLOAD_SMARTCARD_FAILURE = "knowledgepro.admission.upload.smartcard.failure";
	public static final String EXCEL_FILE_REQUIRED = "knowledgepro.admission.excelfile.required";
	public static final String DEPENDENTDOB_FUTURE_DATE ="knowledgepro.employee.dependantdob.futuredate";
	public static final String GEN_SMART_CARD_DATA_INIT = "genSmartCardDataInit";
	public static final String STUDENTLOGIN_HELP = "help";
	public static final String TEMPLATE_GENDER_HE_SMALL = "[he/she]";
	public static final String PLEASE_UPLOAD_SMARTCARD_XLS = "knowledgepro.admission.upload.smartcard.xls";
	public static final String EMP_PAYSCALE_REACTIVATE= "knowledgepro.employee.payscale.reactivate";
	public static final String EMP_HOLIDAY_REACTIVATE= "knowledgepro.employee.holidays.reactivate";
	public static final String UNCHECK_GEN_SMART_CARD="uncheckGenSmartCardDataInit";
	public static final String UNCHECK_GEN_SMART_CARD_LIST = "uncheckGenSmartCardDataList";
	public static final String SELECTION_PROCESS_STATUS= "selectionProcessStatus";
	public static final String DEPARTMENT_ENTRY = "departmentEntry";
	public static final String ADMISSIONFORM_OFFLINE_FIRST_PAGE = "newOfflineApplicationSearch";
	public static final String ADMISSIONFORM_OFFLINE_COURSE_SELCTION_PAGE = "newOfflineCourseSelectionPage";
	public static final String OFFLINE_PREREQUISITE_PAGE = "newOfflinePreRequisitePage";
	public static final String DETAIL_APPLICATION_PAGE = "detailApplicationPage";
	public static final String STUDENTCREATE_BIODATA_CONFIRM_PAGE = "createStudentBiodataConfirmPage";
	public static final String UPLOAD_LEAVE = "empUploadLeave";
	public static final String DOWNLOAD_FORMATS_INIT = "downloadFormatsInit";
	public static final String APP_STATUS_ENTRY = "appStatusEntry";
	public static final String ADMISSIONFORM_MAXMARKS_REQUIRED = "knowledgepro.admission.maxMark.required";
	public static final String BIRT_ABSENCE_REPORT = "birtAbsenceReport";
	public static final String INIT_ADMIN_HALL_TICKET = "initAdminHallTicket";
	public static final String ADMIN_HALL_TICKET_RESULT = "adminHallTicketResult";
	public static final String USER_DEPARTMENT_REQUIRED = "knowledge.user.EmployeeOrDepartmentRequired";
	public static final String INIT_ADMIN_CLEARANCECERTIFICATE = "initAdminClearanceCertificate";
	public static final String ADMIN_CLEARANCE_CERTIFICATE_RESULT = "adminClearanceCertificateResult";
	public static final String DOWNLOAD_ZIP_FILE = "initDownloadFile";
	public static final String TEMPLATE_APPLICATION_STATUS_MAIL = "Application Status Update Mail";
	public static final String STUDENT_CLASS_SUBJECT_DETAILS_INIT = "studentClassSubjectDetailsInit";
	public static final String COPY_INTERVIEW_DEFINITION = "copyInterviewDefinitionInit";
	public static final String APPLICATION_STATUS = "[APPLICATIONSTATUS]";
	public static final String ADMIN_ACADEMIC_YEAR_IS_ALREADY_CURRENT_FOR_ADMISSION = "knowledge.admin.academicyear.already.exists.admission";
	public static final String ADMIN_ACADEMIC_YEAR_MORETHAN_ONE_FOR_ADMISSION = "knowledgepro.admin.academicyear.morethanone.admission";
	public static final String TOTAL_EXPERIENCE = "Total Work Experience";
	
	public static final String STUDENT_MARKS_CORRECTION_VERIFICATION = "studentMarksCorrectionVerification";
	public static final String STUDENT_MARKS_CORRECTION_VERIFICATION_RESULT = "studentMarksCorrectionVerificationResult";
	public static final String SMS_TEMPLATE_VERIFY = "Student Marks Correction Password";
	public static final String SMS_TEMPLATE_VERIFY_PASSWORD = "[VERIFYPASSWORD]";
	public static final String TEMP_PROGRAM = "[PROGRAM]";
	public static final String EMPLOYEE_INFO_VIEW= "employeeViewPage";
	public static final String EMPLOYEE_INFO_VIEW_SEARCH="initViewEmployeeInfo";
	public static final String EMPLOYEE_VIEW_LISTPAGE= "employeeViewList";
	public static final String VIEW_MY_EMPLOYEE_INFORMATION= "MyEmployeeInformView";
	public static final String KNOWLEDGEPRO_COPY_INTERVIEWSUBROUND = "copyInterviewSubRoundInit";
	public static final String GEN_SMART_CARD_DATA_EMP_INIT = "genSmartCardDataEmployee";
	public static final String INIT_ASSIGN_CLASS_STUDENT = "initStudentPage";
	public static final String ASSIGN_CLASS_STUDENT = "displayStudentDetails";
	public static final String TEMPLATE_SIGNATURE = "[SIGNATURE]";
	public static final String INIT_CANCEL_PROMOTION = "initCancelPromotion";
	public static final String TEMPLATE_APPLICATION_RECIEVED_MAIL = "Application Received Mail";
	public static final String EMPLOYEE_ADDRESSLINE1_INVALID= "employee.addressline1.exceeds.size";
	public static final String EMPLOYEE_ADDRESSLINE2_INVALID= "employee.addressline2.exceeds.size";
	public static final String EMPLOYEE_CURRENTADDRESSLINE1_INVALID= "employee.current.addressline1.exceeds.size";
	public static final String EMPLOYEE_CURRENTADDRESSLINE2_INVALID= "employee.current.addressline2.exceeds.size";
	public static final String UNCHECK_GEN_SMART_CARD_EMP = "uncheckGenSmartCardEmpInit";
	public static final String UNCHECK_GEN_SMART_CARD_EMP_LIST = "uncheckGenSmartCardEmpList";
	public static final String CHALLAN_VERIFICATION_INIT = "challanVerificationInit";
	public static final String EMP_SMART_CARD_UPLOAD_NO = "initEmpSmartCardUpload";
	
	public static final String SMS_TEMPLATE_ONLINE_SUBMISSION = "Online Application Submission";
	public static final String SMS_TEMPLATE_MISSING_DOCUMENT = "Missing Documents";
	public static final String SMS_TEMPLATE_APPLICATION_RECIEVED= "Application Received";
	public static final String SMS_TEMPLATE_E_ADMITCARD= "E Admit Card";
	public static final String SMS_TEMPLATE_INTERVIEW_STATUS= "Interview Status for not selected";
	public static final String SMS_TEMPLATE_E_ADMISSION_CARD= "E Admission Card";
	public static final String SMS_TEMPLATE_ADMISSION_CONFIRMATION= "Admission Confirmation";
	public static final String SMS_TEMPLATE_ADMISSION_CANCELLATION= "Admission Cancellation";
	public static final String SMS_TEMPLATE_FINAL_MERIT_LIST_NOT_SELECTED= "Final Merit List Upload Not Selected";
	public static final String SMS_TEMPLATE_STATUS="[STATUS]";
	public static final String CHALLAN_VERIFICATION="Challan Verified";
	public static final String PHOTO = "Photo";
	public static final CharSequence TEMPLATE_MODE = "[MODE]";
	public static final String VIEW_MY_ATTENDANCE = "viewMyAttendance";
	public static final String ADMISSIONFORM_MODE_REQUIRED = "admissionFormForm.mode.required";
	public static final String STAFF_ATTENDANCE_ENTRY_FIRST = "initStaffAttendance";
	public static final String PRINT_ATT_SHORTAGE = "printAttShortage";
	public static final String STUDENT_VIEW_LIST ="studentViewListPage";
	public static final String ASSIGN_SUBJECT_GROUP_HISTORY_INIT = "initSubjectGroupHistory";
	public static final String STUDENTLOGIN_PRINTCHALLAN_LIST = "studentLoginPrintChallanList";
	public static final String STUDENT_LOGIN_SINGLE_PRINTCHALLAN = "studentLoginSingleChallanPrint";
	public static final String STUDENT_LOGIN_PRINTCHALLAN = "studentLoginPrintChallan";
	public static final String BULK_CHALLAN_PRINT_INIT = "bulkChallanPrintInit";
	public static final String FEE_CHALLAN__SAVE_SUCCESS = "knowledgepro.fee.challan.success.save";
	public static final String EMPLOYEE_EXPORT_PHOTOS = "EmployeeExportPhotos";
	public static final String NEW_CONSOLIDATED_MARKS_CARD = "newConsolidatedMarksCard";
	public static final String BULK_CHALLAN_PRINT_POPUP = "bulkChallanPrintPopUp";
	public static final String BULK_CHALLAN_PRINT_SINGLE_CHALLAN_POPUP = "bulkPrintSingleChallanPopUp";
	public static final String UPLOAD_PAY_SCALE_GRADE = "uploadPayScaleGrade";
	public static final String EDIT_MY_PROFILE= "EditMyProfile";
	public static final String EDIT_INFO_SUBMIT= "editInofSubmit";
	public static final String INIT_SHIFT_TYPE = "initShiftEntry";
	public static final String VIEW_MY_ATTENDANCE_PREVIOUS = "viewMyPreviousAttendance";
	public static final String VIEW_EMP_ATTENDANCE_INIT = "viewEmpAttendanceInit";
	public static final String KNOWLEDGEPRO_EMPLOYEE_TYPECHANGE = "knowledgepro.employee.emptype.change.not.permitted";
	public static final String ADMIN_FORGOT_PASSWORD = "adminForgetPassword";
	public static final String CERTIFICATE_MARKS_CARD = "certificateMarksCard";
	public static final String EMPLOYEE_INFO_EDIT_TEACHING_AJAX = "ajaxTeachingExpAddMore";
	public static final String INIT_EMPLOYEE_REPORT = "initEmployeeReport";
	public static final String SEARCH_EMPLOYEE_REPORT = "searchEmployeeDetails";
	public static final String INIT_EXCEL_EMP_REPORT = "initExcelEmpReport";
	public static final String EMP_EXCEL_REPORT = "KnowledgePro.employee.empexcelreport";
	public static final String INIT_EMP_ALLOWANCE = "initEmpAllowance";
	public static final String STUDENTLOGIN_CERTIFICATE_MARKS_CARD_PRINT = "printCertificateMarksCard";
	public static final String CERTIFICATE_MARKS_CARD_NOT_AVALIABLE = "certificateMarksCardNotAvaliable";
	public static final String INIT_ADMIN_CERITIFICATE_MARKS_CARD = "initAdminCertificateMarksCard";
	public static final String ADMIN_CERITIFICATE_MARKS_CARD_RESULT = "printAdminCertificateMarksCard";
	public static final String EMP_ALLOWANCE_TYPE= "knowledgepro.employee.allowance.reactivate";
	public static final String SPECIALIZATION_PREFERED = "Specialization Prefered";
	public static final String BACK_LOGS = "Back Logs (During Interview result)";
	public static final String GENERATE_PASSWORD_EMPLOYEE = "generatePasswordEmployeeInit";
	public static final String GENERATEPASSWORD_EMPLOYEE_SELECT = "admin.generatepassword.employee.select.empType";
	public static final String DESIGNATION_ENTRY="designation_entry";
	public static final String DESIGNATION_NAME_EXIST="knowledgepro.admin.designationnameexit";
	public static final String DESIGNATION_ORDER_EXIST="knowledgepro.admin.designationOrderexit";
	public static final String DESIGNATION_NAME_REACTIVATE="knowledgepro.admin.designationnamereactivate";
	public static final String DESIGNATION_ADD_SUCCESS="knowledgepro.admin.designationaddsuccess";
	public static final String DESIGNATION_ADD_FAILURE="knowledgepro.admin.designationaddfailure";
	public static final String DESIGNATION_UPDATE_SUCCESS="knowledgepro.admin.designationupdatesuccess";
	public static final String DESIGNATION_UPDATE_FAILURE="knowledgepro.admin.designationupdatefailure";
	public static final String DESIGNATION_DELETE_SUCCESS="knowledgepro.admin.designationdeletesuccess";
	public static final String DESIGNATION_DELETE_FAILURE="knowledgepro.admin.designationdeletefailure";
	public static final String DESIGNATION_REACTIVATE_SUCCESS="knowledgepro.admin.designationreactivatesuccess";
	public static final String DESIGNATION_REACTIVATE_FAILURE="knowledgepro.admin.designationreactivatefailure";
	public static final String DESIGNATION_ORDER_REACTIVATE="knowledgepro.admin.designationorderreactivate";
	public static final String EMPLOYEE_DEPARTMENTID_REQUIRED="knowledgepro.employee.department.required";
	public static final String INIT_ADM_BIO_DATA = "initAdmBioData";
	public static final String UPLOAD_ADM_BIODATA = "knowledgepro.adm.biodata.excelupload";
	public static final String ADM_BIO_DATA_ENTRY = "admBioDataEntry";
	public static final String UPLOAD_ADM_BIODATA_SUCCESS = "knowledgepro.admin.biodata.excelupload.success";
	public static final String UPLOAD_ADM_BIODATA_FAILURE = "knowledgepro.admin.biodata.excelupload.failure";
	public static final String UPLOAD_UNIVERSITY_EMAIL_INIT = "uploadUniversityEmailInit";
	public static final String UPLOAD_MERITLIST = "initAdmMeritListUpload";
	public static final String UPLOAD_MERITLIST1 = "knowledgepro.admin.upload.meritlis";
	public static final String CANCELLED_ADMISSION_REPAYMENT_INIT = "cancelledAdmissionRepaymentInit";
	public static final String INIT_PREREQUISIT_YEAR_MONTH = "prerequisitYearMonth";
	public static final String ADMIN_PREREQUISIT_YEAR__MONTH_REACTIVATE = "knowledgepro.admin.prerequisitsyear.Reactivate";
	public static final String ADMIN_PREREQUISIT_YEAR__MONTH_ADD_SUCCESS = "knowledgepro.admin.prerequisitsyear.added.success";
	public static final String ADMIN_PREREQUISIT_YEAR__MONTH_ADD_FAILED = "knowledgepro.admin.prerequisitsyear.addition.failed";
	public static final String ADMIN_PREREQUISIT_YEAR__MONTH_EXISTS = "knowledgepro.admin.prerequisitsyear.exists";
	public static String EDIT_INTERVIEW_SCHEDULE_TEMPLATE = "Edit Selection Process Mail";
	public static final String SMS_TEMPLATE_EDIT_E_ADMITCARD= "Edit E Admit Card";
	public static final String INIT_ATTN_MARKS_UPLOAD = "initAttnMarksUpload";
	public static final String UPLOAD_ATTN_MARKS = "knowledgepro.attn.uploadAttnMarks";
	public static final String UPLOAD_ATTN_MARKS_SUCCESS = "knowledgepro.attendance.attnMarks.excelupload.success";
	public static final String UPLOAD_ATTN_MARKS_FAILURE = "knowledgepro.attendance.attnMarks.excelupload.failure";
	public static final String INIT_COPY_PERIOD = "copyPeriodsInit";
	public static final String COPY_PERIOD_SUCCESS = "knowledgepro.attendance.copyperiods.success";
	public static final String COPY_PERIOD_FAILURE = "knowledgepro.attendance.copyperiods.failure";
	public static final String STUDENTVIEW_INITVIEWPAGE = "initViewStudentInfo";
	public static final String STUDENTVIEW_VIEWLISTPAGE = "studentViewListPage1";
	public static final String PREVIOUS_ATTENDANCE_TEACHER_SUMMARY_REPORT = "previousAttendanceTeacher";
	public static final String INIT_ADM_TC_DETAILS = "initAdmTcDetails";
	public static final String UPLOAD_ADM_TCDETAILS = "knowledgepro.admission.admTcDetails";
	public static final String UPLOAD_FEEMAIN = "initFeemain";
	public static final String UPLOAD_FEEMAIN1 = "knowledgepro.admin.upload.feemain";
	public static final String TEACHER_ATTENDANCE_SUMMARY_REPORT="teacherAttendanceSummaryReport";
	public static final String ATTENDENCE_TEACHER_PREVIOUS_REPORT = "attendanceTeacherPreviousReport";
	public static final String STUDENT_DETAIL_VIEW = "studentDetailsView";
	public static final String INIT_PREVIOUS_ATTENDANCE_TEACHER_SUMMARY_REPORT = "initPreviousAttendanceTeacherSummaryReport";
	public static final String INIT_PROMOTE_BIODATA = "initPromoteBioData";
	public static final String UPLOAD_PROMOTE_BIODATA = "knowledgepro.admission.promote.biodata";
	public static final String INIT_PROMOTE_MARKS_UPLOAD = "initPromoteMarksUpload";
	public static final String UPLOAD_PROMOTE_MARKS = "knowledgepro.admission.promote.marks";
	public static final String ATTENDANCE_PREVIOUS_STUDENT_ABSENTS = "attendancepreviousAbsenties";
	public static final String TEACHER_ATTENDANCE_PREVIOUS_PERIOD_DETAILS = "teacherAttendancePrevious";
	public static final String TEACHER_REPORT_SUMMARY_PRINT = "teacherReportSummaryPrint";
	public static final String TEACHER_ATTENDANCE_STUDENT_ABSENTS= "teacherAttendanceStudentAbsenties";
	public static final String TEACHER_CLASS_ATTENDANCE_PERIOD_DETAILS = "teacherClassAttPeriodDetails";
	public static final String TEACHER_PREVIOUS_REPORT_SUMMARY_PRINT = "teacherPreviousPrint";
	public static final String UPLOAD_SECONDLANG = "initsecondlangupload";
	public static final String UPLOAD_SECOND_LANG = "knowledgepro.admission.upload.secondLanguage";
	public static final String UPLOAD_SUPLI_MARKS = "initSupliMarks";
	public static final String UPLOAD_SUPLI = "knowledgepro.admission.upload.suplimarks";
	public static final String ATTENDANCE_ENTRY_FIRST_NEW = "newAttendanceEntry";
	public static final String ATTENDANCE_ENTRY_INITTHIRD_NEW = "attendanceEntryInitThirdNew";
	public static final String INIT_PROMOTE_SUBJECTS_UPLOAD = "initPromoteSubjectsUpload";
	public static final String UPLOAD_PROMOTE_SUBJECTS = "knowledgepro.admission.promote.subjects.excelfile";
	public static final String INIT_PUCTTN_ATTENDANCE_UPLOAD = "initPucttnAttendance";
	public static final String UPLOAD_PUCTTN_ATTN = "knowledgepro.attendance.pucttn.attendance.excelfile";
	public static final String UPLOAD_PUC_CLASSHELD = "initUploadClassHeld";
	public static final String UPLOAD_CLASS_HELD = "knowledgepro.attendance.upload.classheld";
	public static final String UPLOAD_PUC_DEFINE_SUBJECTS = "initUploadDefineSubjects";
	public static final String ATTENDANCE_ENTRY_THIRD_NEW = "attendanceEntryThirdNew";
	public static final String UPLOAD_CLASS_SUBJECT_DEFINE = "knowledgepro.attendance.pucttn.defineclasssubject.excelfile";
	public static final String FEED_BACK_INIT = "initFeedBack";
	public static final String FEED_BACK_INIT1 = "initFeedBack1";
	public static final String INIT_ATTN_SUBJECT_UPLOAD = "initUploadSubjects";
	public static final String UPLOAD_ATTN_SUBJECTS = "knowledgepro.attendance.upload.subjects";
	public static final String UPLOAD_PUC_APPROVED_LEAVES = "initApprovedLeaves";
	public static final String UPLOAD_PUCATTN_APPROVED_LEAVES = "knowledgepro.attendance.upload.approved.leaves";
	public static final String INIT_ADDITIONAL_FEES = "initAdditionalFees";
	public static final String UPLOAD_ADDITIONAL_FEES = "knowledgepro.fees.additionalfees";
	public static final String INIT_FEES_DETAILS = "initFeesDetails";
	public static final String UPLOAD_FEES_DETAILS = "Knowledgepro.fees.feeDetails";
	public static final String INIT_CLASS_FEES = "initClassfees";
	public static final String UPLOAD_CLASS_FEES = "knowledgepro.fees.classfees";
	public static final String ELIGIBILITY_TEST_OTHER_REQD = "knowledgepro.employee.online.resume.eligibilityTest.other.required";
	public static final String TEACHERLOGIN_SUCCESS = "teachersHome";
	public static final String TEACHERLOGIN_VIEW = "teachersAttendanceView";
	public static final String INIT_ACCOUNT_HEADS_UPLOAD = "initAccountHeads";
	public static final String UPLOAD_ACCOUNT_HEADS = "knowledgepro.pettycash.account.heads.excelfile";
	public static final String INIT_PETTY_CASH_COLLECTION = "initPettycashCollection";
	public static final String UPLOAD_PETTYCASH_COLLECTION = "knowledgepro.pettycash.collection.excelfile";
	public static final String INIT_PETTY_CASH_COLLECTION_DETAILS = "initCollectionDetails";
	public static final String UPLOAD_PETTYCASH_COLLECTION_DETAILS = "knowledgepro.pettycash.collectiondetails.excelfile";
	public static final String EMPLOYEE_QUAL_REQ="knowledgepro.employee.qualification.required";
	public static final String EDUCATION_DETAILS_MIN_ONE_ROW_REQD = "knowledgepro.employee.min.education.details.required";
	public static final String VIEW_INTERNAL_MARKS_DETAILS = "viewInternalMarksDetails";
	public static final String INIT_VIEW_INTERNAL_MARKS_DETAILS = "initViewInternalMarks";
	public static final String TEACHER_INTERNAL_MARKS_REPORT = "teacherInternalMarks";
	public static final String VIEW_INTERNAL_MARKS_DETAILS_PRINT = "viewInternalMarksPrint";
	public static final String INIT_ATTN_BIODATA = "initAttnBiodata";
	public static final String UPLOAD_ATTN_BIODATA = "knowledgepro.attendance.attnbiodata.excelfile";
	public static final String EMPLOYEE_EMAIL_REQUIRED = "knowledgepro.resume.email.required";
	public static final String INIT_TIME_TABLE = "initTimeTable";
	public static final String TIME_TABLE_PERIOD = "timeTablePeriod";
	public static final String INIT_ADM_SUBJECT = "initAdmissionSubject";
	public static final String UPLOAD_ADM_SUBJECT = "knowledgepro.admission.subject";
	public static final String ROOMNO_REQUIRED = "knowledgepro.hostel.room.required";
	public static final String SUBJECT_REQUIRED = "admissionFormForm.subject.required";
	public static final String COMMON_SEC_HELD = "knowledgepro.timetable.common.sec.subject";
	public static final String ELEC_SEC_HELD = "knowledgepro.timetable.elec.sec.subject";
	public static final String ACCOUNT_HEAD_REPORT = "initaccountHeadReport";
	public static final String EMPLOYEE_ELIGIBLITY_REQUIRED = "knowledgepro.resume.eligiblity.required";
	public static final String EMPLOYEE_ACHIEVEMENT_INVALID = "knowledgepro.employee.achievement.required";;
	public static final String INIT_DATA_MIGRATION = "initDataMigration";
	public static final String INIT_DATA_MIGRATION_REPORT = "dataMigrationReport";
	public static final String INIT_DATA_MIGRATION_FIRST_PAGE = "initDataMigrationFirstPage";
	public static final String INIT_DATA_MIGRATION_SECOND_PAGE = "initDataMigrationSecondPage";
	public static final String INIT_EXCEL_ADM_BIODATA_REPORT = "admissionBioDataExcel";
	public static final String EMPLOYEE_CURRENTADDRESSLINE1_ABOVELIMIT ="employee.current.addressline1.exceeds.sizeLimit"; 
	public static final String EMPLOYEE_ADDRESSLINE1_ABOVELIMIT= "employee.addressline1.exceeds.sizeLimit";
	public static final String EMPLOYEE_ADDRESSLINE2_ABOVELIMIT= "employee.addressline2.exceeds.sizeLimit";
	public static final String EMPLOYEE_CURRENTADDRESSLINE2_ABOVELIMIT= "employee.current.addressline2.exceeds.sizeLimit";
	public static final String INIT_ADM_MERITLIST_REPORT = "initAdmMeritListReport";
	public static final String INIT_ADM_MERITLIST_SECOND_PAGE = "admMeritListReportSndPage";
	public static final String INIT_EXCEL_ADM_MERITLIST_REPORT = "admMeritListColumnPage";
	public static final String INIT_PETTY_CASH = "initPettyCash";
	public static final String SUBMIT_ACCOUNT_HEAD_REPORT = "accountHeadReport";
	public static final String INIT_COLLECTION_REPORT = "initCollection";
	public static final String SUBMIT_COLLECTION_REPORT = "getCollectionDetails";
	public static final String ADMISSION_SCHEDULED_DATE = "[ADMISSION_SCHEDULED_DATE]";
	public static final String ADMISSION_SCHEDULED_TIME="[ADMISSION_SCHEDULED_TIME]";
	public static final String INIT_TCDETAILS_REPORT = "initadmissionTcDetailsReport";
	public static final String TCDETAILS_SEARCH = "searchAdmTcDetails";
	public static final String INIT_EXCEL_ADM_TCDETAILS_REPORT = "initExcelAdmTcDetailsReport";
	public static final String VIEW_GENERAL_EMPLOYEE_INFORMATION = "viewGeneralEmpInfo";
	public static final String EMPLOYEE_INFO_GENERAL_VIEW_SEARCH = "empViewGenSearch";
	public static final String EMPLOYEE_INFO_GENERAL_VIEW_LIST = "empViewGenList";
	public static final String PLEASE_SELECT_YEAR="knowledgepro.admission.select.year";
	public static final String INIT_FEEMAIN_REPORT="initFeeMainReport";
	public static final String INIT_FEEMAIN_DETAILS="initFeemainDetails";
	public static final String FEEMAIN_DETAILS_RESULT="feeMainDetailsResult";
	public static final String INIT_ATTENDANCE_DATA_MIGRATION = "initAttnDataMigration";
	public static final String ATTN_BIO_DATA_FIRST_PAGE = "attnBioDataFirstPage";
	public static final String ATTN_BIO_DATA_SECOND_PAGE = "attnBioDataMigrationSecondPage";
	public static final String INIT_EXCEL_ATTENDANCE_BIODATA = "initExcelAttendanceBioData";
	
	public static final String TT_SAME_TEACHER_ERROR ="knowledgepro.timetable.same.teacher.cannot.assign"; 
	public static final String INIT_PROMOTE="initPromoteExam";
	public static final String INIT_EXAM="initExam";
	public static final String PROMOTE_MARKS_RESULT="promoteMarksReportResult";
	public static String APPLICANT_MAIL_TEMPLATE_OFFLINE = "Submission Mail-Offline";
	public static String INIT_ATTENDANCE_MARKS = "initAttendanceMarks";
	public static String ATTENDANCE_MARKS_REPORT = "attnMarksReport";
	public static final String TIME_TABLE_HISTORY = "timeTableHistory";
	public static final String INIT_CET_MARKS_UPLOAD = "initCetMarksUpload";
	public static final String UPLOAD_ATTN_CET_MARKS = "knowledgepro.attendance.upload.cet.marks";
	public static final String INIT_CET_MARKS_REPORT="initCetMarksReport";
	public static final String INIT_CET_MARKS_REPORT_RESULT="CetMarksResult";
	public static final String INIT_DEFINE_RANGE_UPLOAD = "initDefineRangeUpload";
	public static final String UPLOAD_ATTN_DEFINE_RANGE = "knowledgepro.attendance.upload.defineRange";
	public static final String BULK_CHALLAN_REPRINT = "bulkChallanReprint";
	public static final String INIT_PROMOTE_MARKS_FIRST_PAGE = "initpromoteBioDataFirstPage";
	public static final String INIT_PROMOTE_MARKS_SECOND_PAGE ="initpromoteBioDataSecondPage";
	public static final String VALUATION_CHALLAN = "initValuationChallan";
	public static final String INIT_EMP_RES_PUB= "initEmpResearchPublicDetails";
	public static final String APPLICATIONEDIT_INIT_PAGE = "initApplicationModify";
	public static final String ADMISSIONFORM_DETAILMODIFY_PAGE_NEW = "detailApplicationModifyPageNew";
	public static final String ADMISSIONFORM_MODIFY_CONFIRM_PAGE_NEW = "modifyconfirmPageNew";
	public static final String ADMISSIONFORM_EDIT_SEMESTER_MARK_PAGE_NEW = "editdetailSemesterPageNew";
	public static final String ADMISSIONFORM_EDIT_DETAIL_MARK_PAGE_NEW = "editdetailMarkPageNew";
	public static final String ADMISSIONFORM_TRANSFER_SEMESTEREDIT_PAGE_NEW = "transferSemesterEditPageNew";
	public static final String ADMISSIONFORM_LATERAL_SEMESTEREDIT_PAGE_NEW = "lateralSemesterEditPageNew";
	public static final String EXAM_VALUATOR_CHARGE ="initValuatorCharge";
	public static final String EXAM_Valuator_Charge_REACTIVATE= "knowledgepro.exam.valuatorCharge.reactivate";
	public static final String EXAM_VALUATORMEETING_CHARGE ="initValuatorMeetingCharge";
	
	public static final String ELEC_CERTIFICATE_HELD = "knowledgepro.timetable.elec.certificate.subject";
	public static final String CERTIFICATE_SEC_HELD = "knowledgepro.timetable.certificate.sec.subject";
	public static final String VALUATION_CHALLAN_PRINT = "valuationChallanPrint";
	public static final String EMP_RESEARCH_SUBMIT_CONFIRM="initEmpResPubSubmitConfirm";
	public static final String FAILURE_DISPLAY = "failureDisplay";
	public static final String EMPLOYEE_ONLINE_RESUME_FROMNAME = "knowledgepro.employee.downloadresume.fromName";
	public static final String FEE_DATA_NOT_FOUND = "knowledgepro.fee.no.record.found";
	public static final String INIT_REVALUATION_FEE = "initRevaluationFee";
	public static final String EXAM_REVALUATION_FEE_ACTIVE= "knowledgepro.exam.Revaluation.reactivate";
	public static final String EMPLOYEE_RESEARCH_PUBLIC_MASTER= "initEmpResearchPublicMaster";
	public static final String STUDENTLOGIN_REVALUATION_PAGE = "studentRevaluationPage";
	public static final String EXAM_REV_STATUS_UPDATE_INIT = "initExamRevStatusUpdate";
	
	public static final String TEMPLATE_SEM_TYPE = "[SEMTYPE]";
	public static final String TEMPLATE_TERM_NO = "[TERMNO]";
	public static final String TEMPLATE_DD_NO = "[DDNO]";
	public static final String TEMPLATE_DD_DATE = "[DD_DATE]";
	public static final String TEMPLATE_BANK = "[BANK]";
	public static final String TEMPLATE_BRANCH = "[BRANCH]";
	public static final String TEMPLATE_STUDENT_NAME = "[STUDENTNAME]";
	public static final String TEMPLATE_MONTH_YEAR = "[MONTH_YEAR]";
	public static final String TEMPLATE_AMOUNT= "[AMOUNT]";
	
	
	public static String REVALUATION_TEMPLATE = "Revaluation / Re-totaling Mail";
	public static String SMS_REVALUATION_TEMPLATE = "Revaluation / Re-totaling SMS";
	public static final String KNOWLEDGEPRO_REVAPP_STATUS_LENGTH = "knowledgepro.exam.revappstatusupdate.lengthcheck";
	public static final String KNOWLEDGEPRO_REVAPP_STATUS_REQD = "knowledgepro.exam.revappstatusupdate.status.required";
	public static final String KNOWLEDGEPRO_REVAPP_STATUS_UPDATE_SUCCESS = "knowledgepro.exam.revappstatusupdate.success";
	public static final String KNOWLEDGEPRO_REVAPP_STATUS_UPDATE_FAILURE = "knowledgepro.exam.revappstatusupdate.failure";
	public static final String EXAM_REV_OFFLINE_APP_INIT = "initExamRevaluationOfflineApp";
	public static final String EXAM_REV_OFFLINE_RESULT = "examRevaluationOfflineAppResult";
	public static final String INIT_STATUS_CHANGE= "initStatusChange";
	public static String EMPLOYEE_RESEARCH_TEMPLATE = "Employee-Research and Publication Mail";
	public static final String EMPLOYEE_RESEARCH_PUBLICATION_TOID="knowledgepro.employee.research.publication.sendmail.to.id";
	public static final String EMPLOYEE_RESEARCH_PUBLICATION_FROMNAME="knowledgepro.employee.research.publication.fromName";
	public static final String EMPLOYEE_RESEARCH_PUBLICATION_FROMID="knowledgepro.employee.research.publication.sendmail.id";
	public static final String UPLOAD_ATTN_INTERNAL_MARKS = "knowledgepro.attn.uploadAttnInternalMarks";
	public static final String EMP_RES_PUB_PENDING_LIST = "initEmpResPendSearch";
	public static final String EMP_RES_PUB_DETAILS_APPROVAL= "initEmpResPubPendApproval"; 
	public static final String EMP_RES_PUB_APPROVED="initEmpResPubApproved";
	public static final String INIT_ATTN_INTERNAL_MARKS_UPLOAD = "initAttnInternalMarksUpload";
	public static final String INIT_EMPLOYEE_ONLINE_LEAVE = "empOnlineLeave";
	public static final String VIEW_MY_ONLINE_LEAVES = "viewOnlineLeave";
	public static final String APPROVE_EMPLOYEE_LEAVES = "approveLeaves";
	public static final String EMPLOYEE_LEAVE_MAIL = "Employee-Online Leave Application";
	public static final CharSequence TEMPLATE_MAIL_MESSAGE = "[MESSAGE]";
	public static final String INVENTORY_SUB_CATEGORY="initsubcategory";
	public static final String INVENTORY_NAME_EXIST="knowledgepro.inventory.nameexit";
	public static final String INVENTORY_NAME_REACTIVATE="knowledgepro.inventory.reactivate";
	public static final String EMP_RES_PUB_APPROVED_LIST="initEmpResApprovedSearch";
	public static String REVALUATION_STATUS_TEMPLATE = "Revaluation / Re-totaling Status Update Mail";
	public static String SMS_REVALUATION_STATUS_TEMPLATE = "Revaluation / Re-totaling Status Update SMS";
	public static final String STUDENT_SPECIALIZATION_PREFERED="[STUDENT_SPECIALIZATION_PREFERED]";
	public static final String EMP_RES_APPROVAL_CONFIRM="ResPubPendSubmitConfirm";
	public static String INIT_INTERNAL_MARKS = "initInternalMarks";
	public static String INTERNAL_MARKS_REPORT = "InternalMarksReports";
	public static final String FROWARD_EMPLOYEE_LEAVES = "forwardEmployeeLeaves";
	public static final CharSequence TEMPLATE_EMPLOYEE_FINGETPRINTID = "[EMPLOYEEID]";
	public static final String EMPLOYEE_APPLY_LEAVE_MAIL = "Employee-Approve Leave";
	public static final String EMPLOYEE_RETURN_LEAVE_MAIL = "Employee-Return/Clarify Leave";
	public static final String TEMPLATE_REASON = "[REASON]";
	public static final String INIT_EMPLOYEE_LEAVE_APPROVER = "employeeApprover";
	public static final String GUEST_FACULTY_DETAILS = "guestFacultyDetails";
	public static final String GUEST_SUBMIT_SUCCESS = "guestInfoSubmitConfirm";
	public static final CharSequence TEMPLATE_EMPLOYEE_LEAVETYPE = "[EMPLOYEE_LEAVETYPE]";
	public static final String ADMISSIONFORM_SEMESTERMAXMARKWITHLAN_REQUIRED = "knowledgepro.admission.semmaxmarkwithlan.reqd";
	public static final String ADMISSIONFORM_SEMESTERMAXMARK_REQUIRED =  "knowledgepro.admission.semmaxmark.reqd";
	public static final String ADMISSIONFORM_MAXMARKSGRT_REQUIRED = "knowledgepro.admission.maxmark.greater.reqd";
	public static final String ADMISSIONFORM_SEMESTERTOTALMARK_REQUIRED = "knowledgepro.admission.semTotalmark.greater.reqd";
	public static final String ADMISSIONFORM_TOTALMARK_GRTZERO = "knowledgepro.admission.detailTotalmark.greater.reqd";
	public static final String GUEST_EDITLISTPAGE = "guestListForEdit";
	public static final String INIT_GUEST_INFO_ADD = "guestAddFirstPage";
	public static final String INIT_GUEST_INFO_EDIT = "guestEditFirstPage";
	public static final String GUEST_FACULTY_PRINT_DETAILS = "guestFacultyPrintDetails";
	public static final String EMPLOYEE_VALID_EMAIL = "knowledgepro.resume.valid.email.required";
	
	public static final String INIT_GET_GROUPS = "initGetGroups";
	public static final String INIT_GET_GROUP_COURSE = "initGetGroupCourse";
	public static final String MAX_INTAKE_VALIDDECIMAL="knowledgepro.admin.maxIntake.decimal";
	public static final String INIT_DISABLE_STU_LOGIN = "initDisableStuLogin";
	public static final String STUDENT_CERTIFICATE_COURSE_INIT = "studentCertificateCourseSearch";
	public static final String STUDENT_CERTIFICATE_COURSE_AJAX_RESPONSE = "studentCertificateCourseAjaxResponse";
	public static final String PURCHASEORDER_TOTALDISC_INVALID = "knowledgepro.inv.quotation.totalDisc.invalid";
	public static final String EMP_RESEARCH_APPROVED_SUBMIT = "ResPubApprovedSubmitConfirm";
	
	public static final String STUDENT_CERTIFICATE_COURSE_RESULT = "studentCertificateCourseResult";
	public static final String ATTENDANCE_ENTRY_BY_TIMETABLE = "attendanceEntryByTimeTable";
	public static final String ATTENDANCE_ENTRY_INITTHIRD_FOR_TIMETABLE = "attendanceEntryThirdPageByTimeTable";
	public static final String ATTENDANCE_ENTRY_THIRD_BY_TIMETABLE = "attendanceEntryStudents";
	public static final String GUEST_EDIT_SUBMIT_SUCCESS = "guestEditSubmitConfirm";
	public static final String GUEST_FACULTY_EDIT = "guestFacultyDetailsEdit";
	public static final String CJC_MARKS_CARD_DISPLAY_AJAX = "ajaxCjcMarksCardDisplay";
	public static final String AUTHORIZATION_EMPLOYEE_LEAVES = "authorizationLeaves";
	public static final String EMPLOYEE_APPLY_AUTHORIZED_LEAVE_MAIL = "Employee-Authorization Leave";
	public static final String EMPLOYEE_AUTHORIZATION_RETURN_LEAVE_MAIL = "Employee-Authorization Return Leave";
	public static final String EMPLOYEE_AUTHORIZATION_REQUEST_DOC_LEAVE_MAIL = "Employee-Authorization Request Doc Leave";
	public static final String VIEW_RETURNED_EMPLOYEE_LEAVES = "viewReturnedEmpLeaves";
	public static final String ATTENDANCE_NO_PERIOD_DEFINE = "knowledgepro.attendanceentry.noperiod.define";
	public static final String ATTENDANCE_ENTRY_THIRD_BY_MANUAL = "attendanceEntryManualStudents";
	public static final String LEAVE_APPLICATION_INSTR = "initLeaveApplicationInst";
	public static final String EMPLOYEE_REQUIRED = "knowledgepro.userinfo.employeeRequired";
	public static final String GUEST_REQUIRED = "knowledgepro.userinfo.guestRequired" ;
	public static final String INIT_STUDENT_CERTIFICATE_COURSE_STUDENT_LOGIN = "initStudentCertificateCourseForLogin";
	public static final String STUDENT_LOGIN_CERTIFICATE_COURSE_RESULT = "studentLoginCertificateCourseResult";
	public static final String INIT_VIEW_MYATTN_LEAVE = "initViewMyAttendanceLeave";
	public static final String GUEST_NOT_VALID_APPLICATIONNO="knowledgepro.guest.InvalidApplicationNo";
	public static final String REDIRECT_TO_PGI_PAGE = "redirectToPgi";
	public static final String STUDENT_CERTIFICATE_SMS_TEMPLATE="Student Certificate Course Submission";
	public static final String GUEST_STARTDATE_INVALID = "knowledgepro.guest.InvalidStartDate";
	public static final String GUEST_ENDDATE_INVALID = "knowledgepro.guest.InvalidEndDate";
	public static final String GUEST_STARTDATE_REQUIRED = "knowledgepro.guest.requiredStartDate";
	public static final String GUEST_ENDDATE_REQUIRED = "knowledgepro.guest.requiredEndDate";
	public static final String INIT_RECEIVED_THROUGH = "initReceivedThrough";
	public static final String RECEIVED_THROUGH_REACTIVATE= "knowledgepro.admission.receivedThrough.reactivate";
	public static final String INIT_APPLN_ACKNOWLEDGEMENT = "initAcknowledgement";
	public static final String PRINT_SLIP = "printSlip";
	public static final String ATTENDANCE_ENTRY_BATCH_REQUIRED = "knowledgepro.attendance.batch.required";
	public static final String PRINT_MYATTN_LEAVE = "printMyAttnLeave";
	public static final String VIEW_EMPLOYEE_ATTN_LEAVE = "initViewEmployeeAttnLeave";
	
	public static final String STUDENT_LOGIN_SMART_CARD_ENQUIRY = "studentLoginSmartCardEnquiry";
	public static final String AUDITORIUM_ALLOCATION = "initAuditoriumAllocation";
	public static final String CALENDAR = "initCalendar";
	public static final String DESCIPLINARY_DETAILS_DISPLAY_CJC = "disciplinaryDetailsDisplayCjc";
	public static final String PURCHASEORDER_UNIT_COST_REQD = "knowledgepro.inv.purchase.order.unit.cost.reqd";
	public static final String PURCHASEORDER_QTY_REQD = "knowledgepro.inventory.quantity.required";
	public static final String PURCHASE_ORDER_QUOTAION_NOT_EXIST = "knowledgepro.inv.purchase.order.quotation.does.not.exist";
	public static final String STUDENT_LOGIN_SMART_CARD_ENQUIRY_1 = "studentLoginSmartCardEnquiry1";
	public static final String VENUE_DETAILS_REACTIVATE= "knowledgepro.auditorium.venue.reactivate";
	public static final String INIT_VENUE_DETAILS= "initVenueDetails";
	public static final String STUDENT_FEEDBACK_GROUP="evaStudentFeedBackGroup";
	public static final String FEEDBACK_GROUP_ORDER_EXIST="knowledgepro.studentFeedBack.grouporderexit";
	public static final String FEEDBACK_GROUP_EXIST="knowledgepro.studentFeedBack.groupnameorderexit";
	public static final String FEEDBACK_GROUP_REACTIVATE="knowledgepro.studentFeedBack.groupreactivate";
	public static final String FEEDBACK_GROUP_ADD_SUCCESS="knowledgepro.studentFeedBack.groupaddsuccess";
	public static final String FEEDBACK_GROUP_ADD_FAILURE="knowledgepro.studentFeedBack.groupaddfailure";
	public static final String FEEDBACK_GROUP_UPDATE_SUCCESS="knowledgepro.studentFeedBack.groupupdatesuccess";
	public static final String FEEDBACK_GROUP_UPDATE_FAILURE="knowledgepro.studentFeedBack.groupupdatefailure"; 
	public static final String FEEDBACK_GROUP_DELETE_SUCCESS="knowledgepro.studentFeedBack.groupdeletesuccess";
	public static final String FEEDBACK_GROUP_DELETE_FAILURE="knowledgepro.studentFeedBack.groupdeletefailure";
	public static final String FEEDBACK_GROUP_REACTIVATE_SUCCESS="knowledgepro.studentFeedBack.groupreactivatesuccess";
	public static final String FEEDBACK_GROUP_REACTIVATE_FAILURE="knowledgepro.studentFeedBack.groupreactivatefailure";
	public static final String FEEDBACK_GROUPAQ_EXIST="knowledgepro.studentFeedBack.groupnameorderexit";
	public static final String STUDENT_FEEDBACK_QUESTION="studentFeedBackQuestion";
	public static final String STUDENT_FEEDBACK_REACTIVATE="knowledgepro.studentFeedBack.reactivate";
	public static final String INIT_STU_FEEDBACK_INST = "initStuFeedbackInst";
	public static final String INIT_EVAL_STU_FEEDBACK = "initEvaluationStudentFeeback";
	public static final String INIT_EVAL_STU_FEEDBACK_SESSION = "intEvaluationStudentFeedbackSession";
	public static final String VIEW_RETURNED_EMPLOYEE_LEAVES_FROM_APPROVAL ="returnedEmpLeavesFromApproval";
	public static final String EMPLOYEE_APPLY_APPROVED_LEAVE_MAIL = "Employee-Approved Leave";
	public static final String FACULTY_FEEDBACK_GROUP="evaFacultyFeedBackGroup";
	public static final String FACULTY_FEEDBACK_QUESTION="facultyFeedBackQuestion";
	public static final String EMPLOYEE_STATUS_REQUIRED = "Knowlwdge.employee.info.job.empStatus.required";
	public static final String EMPLOYEE_SALARY_LAKHS_REQUIRED="employee.info.salary.lakhs.required";
	public static final String EMPLOYEE_SALARY_THOUSANDS_REQUIRED="employee.info.salary.thousand.required";
	public static final String EMPLOYEE_JOB_TYPE_REQUIRED="knowledgepro.employee.job.type";
	public static final String STUDENT_ATTENDANCE_FOR_EXAM_SCHEME_REQD = "knowledgepro.student.exam.attendance.scheme.no.reqd";
	public static final String INIT_SUPPLEMENTARY_FEES = "initSupplementaryFees";
	public static final String INIT_PUBLISH_SUPPLEMENTARY = "initPublishSupplementary";
	public static final String INIT_SUPPL_IMP_APP_STUDENT_RESULT = "initSupplImpAppStudentResult";
	public static final String INIT_SUPPL_IMP_APP_STUDENT_VEIRFY_SMART = "verifySmartCard";
	public static final String INIT_SUPPL_IMP_APP_STUDENT_VEIRFY_SMART_1 = "verifySmartCard1";
	public static final String SUPPLEMENTARY_APPLICATION_SMS_TEMPLATE="Supplementary Application Submission";
	public static final String TEMPLATE_SUPPLEMENTARY_APPLICATION_PRINT="Online payment Receipts";
	public static final String TEMPLATE_AMOUNT_IN_WORDS= "[AMOUNT_IN_WORDS]";
	public static final String INTERNAL_MARKS_ENTRY = "internalMark";
	public static final String CERTIFICATE_DETAILS="certificateDetail";
	public static final String ASSIGN_TO_ROLES="assignToRoles";
	public static final String CERTIFICATE_ADDED_SUCCESSFULLY = "knowledgepro.certificate.added.successfully";
	public static final String CERTIFICATE_ADDING_FAILED = "knowledgepro.certificate.added.failure";
	public static final String INTERNAL_MARKS_ENTRY_RESULT = "internalMarkEntryResult";
	public static final String AUDITORIUM_BOOKING = "auditoriumBooking";
	public static final String OPEN_EXAM_FRIST = "initExamPage";
	public static final String CERTIFICATE_REMOVE_SUCCESSFULLY = "knowledgepro.certificate.remove.successfully";
	public static final String NOT_SELECTED_ASSIGNROLE="knowledgepro.certificate.assign.role";
	public static final String INTERNAL_MARKS_REPORT_PRINT = "printInternalMarkReport";
	public static final String ADMISSIONFORM_FEEPAY_TYPE_REQUIRED = "knowledgepro.admission.online.appln.fee.type.reqd";
	public static final String ADMISSIONFORM_DDNO_REQUIRED="knowledgepro.admission.online.appln.ddNo.reqd";
	public static final String ADMISSIONFORM_DD_DRAWN_ON_REQUIRED = "knowledgepro.admission.online.appln.dd.drawn.on.reqd";
	public static final String ADMISSIONFORM_DD_ISSUING_BANK_REQUIRED = "knowledgepro.admission.online.appln.dd.issuing.bank.reqd";
	public static final String ADMISSIONFORM_CHALLAN_NO_MAX_LEN_REQUIRED="knowledgepro.admission.challan.no.less.length.reqd";
	public static final String ADMISSIONFORM_MOBILE_NO_MAX_LEN_REQUIRED="knowledgepro.admission.mobile.no.less.length.reqd";
	public static final String INIT_PO_TERMS_CONDITIONS = "initPOTermsConditions";
	public static final String PO_TC_DESC_REQD = "knowledgepro.inv.poTCEntry.desc.reqd";
	public static final String PO_TC_DESC_ALREADY_EXISTS = "knowledgepro.inv.poTCEntry.desc.exists";
	public static final String PO_TC_ADDED_SUCCESSFULLY = "knowledgepro.admin.termsandconditions.addsuccess";
	public static final String HIDE_SUBJECT_TEACHER="hiddenSubjectTeacher";
	public static final String INIT_ONLINE_RECIEPTS = "initOnlineReciepts";
	public static final String PRINT_ONLINE_RECIEPTS = "printOnlineReciept";
	public static final CharSequence TEMPLATE_APPLICATION_FOR = "[APPLICATION_FOR]";
	public static final String INIT_CERTIFICATE_REQUEST_ONLINE = "initCertificateRequest";
	public static final String INIT_CERT_REQ_STUDENT_VEIRFY_SMART = "verifySmartCardCertificate1";
	public static final String INIT_CERT_REQ_STUDENT_VEIRFY_SMART_1 = "verifySmartCardCertificate2";
	public static final String TEMPLATE_CERTIFICATE_APPLICATION_PRINT="Certificate application receipt print";
	public static final String STUDENT_PENDING_LIST_SEARCH = "initAdminPendingCertificateRequest";
	public static final String STUDENT_PENDING_LIST_RESULT="PendingCertRequestResult";
	public static final String CERTIFICATE_APPLICATION_SMS_TEMPLATE="Certificate Request Completion SMS";
	public static final String COMPLETED_APPLICATION_SMS_TEMPLATE = "Certificate Request Completed SMS";
	public static final String PURCHASEORDER_ADDNDISCOUNT_INVALID = "knowledgepro.inventory.addn.discount.invalid";
	public static final String INIT_DOCUMENTS_ENTRY = "documentsEntry";
	public static final String CERT_SUBMIT_CONFIRM= "submitConfirm";
	public static final String MARKSCARD_MONTH_REQUIRED= "knowledgepro.StudentLogin.certificate.month.Required";
	public static final String MARKSCARD_YEAR_REQUIRED= "knowledgepro.StudentLogin.certificate.year.Required";
	public static final String MARKSCARD_EXAM_TYPE_REQUIRED= "knowledgepro.StudentLogin.certificate.examType.Required";
	public static final String MARKSCARD_SEMESTER_REQUIRED= "knowledgepro.StudentLogin.certificate.semester.Required";
	public static final String IS_CANCELED = "Is Canceled";
	public static final String EVAL_STU_FEEDBACK_DETAILS = "getTeachersAndSubjectDetails";
	public static final String FACULTY_EVAL_ANSWERING_CHECK = "facultyEvaluationAnsweringCheck";
	public static final String EVAL_STU_FEEDBACK_SUCCESSFUL = "facultyEvaluationSuccessful";
	public static final String INIT_EVAL_STU_FEEDBACK_ALREADY_EXIST = "facultyEvaluationIsAlreadyExist";
	public static final String INIT_EVAL_STU_FEEDBACK_ATTENDANCE_SHORTAGE = "studentAttendanceShortage";
	public static final String STUDENT_FEEDBACK_OPEN_CONNECTION = "studentFeedbackOpenConnection";
	public static final String CALCULATE_ATTENDANCEMARK_INIT = "initCalculateAttMarksCjc";
	public static final String CALCULATE_ATTENDANCEMARK_RESULT = "CalculateMarksResult";
	public static final String CERTIFICATE_DETAILS_AMOUNT_INTEGER = "knowledgepro.certificate.details.paidamount.integer";
	public static final String TEACHER_TO_GROUP="teacherToGroup";
	public static final String CIA_TEACHER_MARK_ENTRY = "CIAteacherMarksEntry";
	public static final String OFFLINE_APPLN_ACKNOWLEDGEMENT_SMS = "Offline Application Acknowledgement SMS";
	public static final String UPLOAD_FINAL_MERIT_LIST_NOT_UPLOADED_NUMS = "knowledgepro.upload.final.merit.not.uploaded.list";
	public static final String KNOWLEDGEPRO_CERTIFICATE_NORECORD_SELECTED = "knowledgepro.certificate.request.message";
	public static final String ADMIN_HALL_TICKET_RESULT_SUPP = "supHallTicket";
	public static final String REGISTER_NO_FROM="Knowledge.register.No.From.required";
	public static final String REGISTER_NO_TO="Knowledge.register.No.To.required";
	public static final String CLASS_REQUIRED="Knowledge.class.required";
	public static final String TEMPLATE_CERTIFICATENAME="[CERTIFICATE_NAMES]";
	public static final String INIT_PEERS_FEEDBACK_SESSION = "initPeersFeedbackSession";
	public static final String INIT_PEERS_EVALUATION_OPEN_SESSION = "initPeersEvaluationOpenSession";
	public static final String INIT_PEERS_EVAL_GROUPS = "initPeersEvaluationGroups";
	public static final String INIT_DOWNLOAD_UPLOADED_FILES = "initDownloadUploadedFiles";
	public static final String INIT_UPLOAD_FILES = "initUploadFiles";
	public static final String COURSE_COMMENCEMENT_DATE="[COURSE_COMMENCEMENT_DATE]";
	public static final String INIT_ASSIGN_PEERS_GROUPS = "initAssignPeersGroups";
	public static final String VIEW_DEPT_WISE_EMPLOYEE_INFORMATION = "viewDeptwiseEmpInfo";
	public static final String EMPLOYEE_INFO_DEPT_VIEW_LIST = "empViewDepList";
	public static final String Exam_End_Date="examEndDate";
	public static final String INIT_HIDE_TEACHERS_PEERS_EVALUATION = "initHideTeachersPeersEvaluation";
	public static final String VALUATION_CHALLAN_EXTERNAL_PRINT = "valuationChallanExternalPrint";
	public static final String EMPLOYEE_DESIGNATION_STAFF_ALBUM="knowledgepro.employee.designation.album.required";
	public static final String BIRT_VIEW_STUDENT_FEEDBACK = "birtViewStudentFeedBack";
	public static final String STUDENT_LOGIN_MARKSCARD = "initMarksCard";
	public static final String STUDENTLOGIN_MARKSCARD_BLOCK_ERROR = "knowledgepro.student.login.block.message";
	public static final String STUDENTLOGIN_MARKS_CARD_AGREEMENT_NEW =  "marksCardAgreementNew";
	public static final String STUDENTLOGIN_MARKS_CARD_AGREEMENT_SUPP = "marksCardAgreementSupp";
	public static final String GUIDELINES_NRI_TEMPLATE = "Guidelines For NRI";
	public static final String REVALUATION_STUDENT_VEIRFY_SMART = "verifySmartCardRevaluation";
	public static final String REVALUATION_STUDENT_VEIRFY_SMART_1 = "verifySmartCardRevaluation1";
	public static final String KNOWLEDGEPRO_REVALUATION_NORECORD_SELECTED = "knowledgepro.revaluation.noselected.message";
	public static final String INIT_PEERS_EVAL_FEEDBACK = "initPeersEvaluationFeedback";
	public static final String ALREADY_EXIST_OR_SUBMITTED_SUCCESSFULLY = "initPeersEvaAlreadyExistOrSubmitSuccessfully";
	public static final String SELECT_PEERS_EVAL_FEEDBACK = "selectionPeersEvaluationFeedback";
	public static final String START_PEERS_EVAL_FEEDBACK = "startPeersEvaluationFeedback";
	public static final String INIT_PEERS_INSTRUCTIONS = "initPeersInstructions";
	public static final String ASSIGN_PURPOSE="assignPurposes";
	public static final String CERTIFICATE_PURPOSE_REMOVE_SUCCESSFULLY = "knowledgepro.certificate.purpose.remove.successfully";
	public static final String NOT_SELECTED_ASSIGN_PURPOSE="knowledgepro.certificate.assign.purpose";
	public static final String CERTIFICATE_PURPOSE_ADDED_SUCCESSFULLY = "knowledgepro.certificate.purpose.added.successfully";
	public static final String CERTIFICATE_PURPOSE_ADDING_FAILED = "knowledgepro.certificate.purpose.added.failure";
	public static final String BACK_LOGS_NEW = "Back Logs";
	public static final String ADMISSIONFORM_APPLICATIONDETAIL_PAGE_CJC = "cjcApplicationalPage";
	public static final String Generate_Settlement_RefundForm = "GenerateSettlementOrRefund";
	public static final String INIT_VALUATION_STATUS_SUBJECT_WISE = "initValuationStatusSubjectWise";
	public static final String CERTIFICATE_PURPOSE = "certificatePurpose";
	public static final String UPLOAD_EXAM_BLOCK_UNBLOCK="uplaodExamBlockUnblock";
	public static final String Upload_Block_Hallticket_Markscard="knowledgepro.exam.upload.block.hallticket.markscard";
	public static final String Upload_Except_Reg_Numbers= "knowledgepro.admission.except.reg.numbers";
	public static final String INIT_HOD_LEAVE_APPROVER="hodApprover";
	public static String EMPLOYEE_RESEARCH_APPROVER_MAIL_TEMPLATE = "Research and Publication Mail For Approver";
	public static String EMPLOYEE_RESEARCH_SUBMIT_EMPLOYEE_TEMPLATE = "Research and Publication Mail For Employee";
	public static String EMPLOYEE_RESEARCH_APPROVED_TEMPLATE= "Research and Publication Approved Mail For Employee";
	public static final String INIT_EXTERNALEVALUATORS = "initexternalevaluators";
	public static final String EXIT_EVALUATION = "exitEvaluation";
	public static final String EXIT_EVAL_ANSWERING_CHECK = "exitEvaluationAnsweringCheck";
	// Validation keys for ExternalEvaluator
	public static final String INIT_EXTERNALEVALUATOR = "initexternalevaluator";
	public static final String EXAM_EXTERNALEVALUATOR_EXISTS = "knowledgepro.exam.externalevaluator.exists";
	public static final String EXAM_EXTERNALEVALUATOR_REACTIVATE = "knowledgepro.exam.externalevaluator.exists.reactivate";
	public static final String EXAM_EXTERNALEVALUATOR_ADD_SUCCESS = "knowledgepro.exam.externalevaluator.added";
	public static final String EXAM_EXTERNALEVALUATOR_ADD_FAILED = "knowledgepro.exam.externalevaluator.addfailed";
	public static final String EXAM_EXTERNALEVALUATOR_DELETE_SUCCESS = "knowledgepro.exam.externalevaluator.deleted";
	public static final String EXAM_EXTERNALEVALUATOR_DELETE_FAILED = "knowledgepro.exam.externalevaluator.deletefailed";
	public static final String EXAM_EXTERNALEVALUATOR_UPDATE_SUCCESS = "knowledgepro.exam.externalevaluator.updated";
	public static final String EXAM_EXTERNALEVALUATOR_UPDATE_FAILED = "knowledgepro.exam.externalevaluator.updatefailed";
	public static final String EXAM_EXTERNALEVALUATOR_REACTIVATE_SUCCESS = "knowledgepro.exam.externalevaluator.reactivated";
	public static final String EXAM_EXTERNALEVALUATOR_REACTIVATE_FAILED = "knowledgepro.exam.externalevaluator.reactivate.failed";
	public static final String PHD_DOCUMENT="documentSuccess";
	public static final String PHD_SYNOPSIS_DEFENCE="phdSynopsisDefenses";
	public static final String SYNOPSIS_DEFENSE_REACTIVATE="knowledgepro.phd.SynopsisDefense.reactivate";
	public static final String CERTIFICATE_COURSE_COPY="certificateCourseCopy";
	public static final String ADMIN_NEWSEVENTS_UPDATE_SUCCESS = "knowledgepro.admin.newsevents.updated";
	public static final String ADMIN_NEWSEVENTS_UPDATE_FAILED = "knowledgepro.admin.newsevents.failed";
	public static final String ADMIN_NEWSEVENTS_EXIST = "knowledgepro.admin.newsEventsExist";
	public static final String VIEW_ACKNOWLEDGEMENT = "viewAcknowledgement";
	public static final String CERTIFICATE_APPLICATION_REJECTED_SMS_TEMPLATE="Certificate Request Rejected SMS";
	public static final String CERTIFICATE_APPLICATION_REJECTED_MAIL_TEMPLATE = "Certificate Request Rejected Mail for student";
	public static final String CERTIFICATE_APPLICATION_MAIL_TEMPLATE = "Certificate Request Completed Mail for student";
	public static final String CERTIFICATE_NAME = "[CERTIFICATENAMES]";
	public static final String EMP_RES_PUB_APPROVED_NEW="initEmpResPubApprovedNew";
	// Validation keys for FacultyGrades
	public static final String INIT_FACULTYGRADES = "initfacultygrades";
	public static final String STUDENTFEEDBACK_FACULTYGRADES_EXISTS = "knowledgepro.studentfeedback.facultygrades.exists";
	public static final String STUDENTFEEDBACK_FACULTYGRADES_REACTIVATE = "knowledgepro.studentfeedback.facultygrades.exists.reactivate";
	public static final String STUDENTFEEDBACK_FACULTYGRADES_ADD_SUCCESS = "knowledgepro.studentfeedback.facultygrades.added";
	public static final String STUDENTFEEDBACK_FACULTYGRADES_ADD_FAILED = "knowledgepro.studentfeedback.facultygrades.addfailed";
	public static final String STUDENTFEEDBACK_FACULTYGRADES_DELETE_SUCCESS = "knowledgepro.studentfeedback.facultygrades.deleted";
	public static final String STUDENTFEEDBACK_FACULTYGRADES_DELETE_FAILED = "knowledgepro.studentfeedback.facultygrades.deletefailed";
	public static final String STUDENTFEEDBACK_FACULTYGRADES_UPDATE_SUCCESS = "knowledgepro.studentfeedback.facultygrades.updated";
	public static final String STUDENTFEEDBACK_FACULTYGRADES_UPDATE_FAILED = "knowledgepro.studentfeedback.facultygrades.updatefailed";
	public static final String STUDENTFEEDBACK_FACULTYGRADES_REACTIVATE_SUCCESS = "knowledgepro.studentfeedback.facultygrades.reactivated";
	public static final String STUDENTFEEDBACK_FACULTYGRADES_REACTIVATE_FAILED = "knowledgepro.studentfeedback.facultygrades.reactivate.failed";
	public static final String STUDENTFEEDBACK_FACULTYGRADES_SCALENOTCORRECT = "knowledgepro.studentfeedback.facultygrades.scalenotcorrect";
	public static final String COPY_CLASS_TEACHER="copyclassTeacher";
	public static final String Exam_MarksVerification_Correction="verificationMarksCorrection";
	public static final String STUDENTLOGIN_CERTIFICATE_REQUEST_FROMNAME="knowledgepro.studentlogin.certificate.request.fromName";
	public static final String STUDENT_CERTIFICATE_DESCRIPTION="CertificateRequetsDescription";
	public static final String CERTIFICATE_TEMPLATE_HELPTEMPLATE = "CertificateHelpTemplate";
	public static String EMPLOYEE_RESEARCH_REJECTED_TEMPLATE= "Research and Publication Rejected Mail For Employee";
	public static final String PHD_DOCUMENT_SUBMISSION_SCHEDULE = "documentSubmissoinSchedule";
	public static final String CIA_MARK_HOD_VIEW = "hodView";
	public static final String INTERNAL_MARKS_ENTRY_HOD_VIEW = "HODMarksView";
	public static final String PHD_DOCUMENT_SUBMISSION_STUDENT="documentSubmission";
	public static final String TEMPLATE_REJECTED_REASON = "[REJECTED_REASON]";
	public static final String EMP_RES_PUB_APPROVAL_PENDING_NEW="initEmpResPubApprovalPendingNew";
	public static final String CERTIFICATE_TEMPLATE_ADD_SUCCESSFULLY = "knowledgepro.certificate.template.add.successfully";
	public static final String CERTIFICATE_TEMPLATE_REMOVE_SUCCESSFULLY = "knowledgepro.certificate.template.remove.successfully";
	public static final String CERTIFICATE_TEMPLATE_ADDING_FAILED = "knowledgepro.certificate.template.added.failure";
	public static final String PHD_GUIDE_DETAILS="guideDetails";
	public static final String PHD_GUIDE_DETAILS_REACTIVATE="knowledgepro.phd.guidedetails.reactivate";
	public static final String Change_Subject="changeSubject";
	public static final String KNOWLEDGEPRO_FROMDATE_CONNOTBELESS="knowledgepro.from.date.connotbeless";
	public static final String APPLICANT_FEEDBACK = "Applicant Feedback";
	public static final String UNIVERSITY_EMAIL = "University Email";
	public static final String PHD_STUDY_AGGREMENT="studyAggrement";
	public static final String PHD_STUDY_AGREEMENT="knowledgepro.phd.studyAgreement.reactivate";
	public static final String PHD_GUIDE_REMENDERATION="guideRemenderation";
	public static final String TEMPLET_GUIDE_REMENDERATION="Phd- Generate Guides Remuneration Advice";
	public static final String SUBMITTED_DATE = "[SUBMITTED_DATE]";
	public static final String DOCUMENT_NAME = "[DOCUMENT_NAME]";
	public static final String TEMPLATE_GUIDE = "[GUIDE]";
	public static final String TEMPLATE_COGUIDE = "[COGUIDE]";
	public static final String PRINT_GUIDE_REMENDERATION="printDuideRemenderation";
	public static final String VIEW_TEMPLATE_DESCRIPTION_CERT = "viewTemplateDescriptionCertificate";
	public static final String REVALUTION_RETOTALING_MARKS="RevaluationOrRetotalling";
	public static final String INIT_CONSOLIDATED_COLLECTION = "initConsolidatedCollectionLedger";
	public static final String CONSOLIDATED_COLLECTION_RESULTS ="consolidatedCollectionLedger";
	public static final String INIT_HONOURS_COURSE_ENTRY = "initHonoursCourseEntry";
	public static final String PHD_SETTINGS ="initPhdSettings";
	public static final String PHD_SETTING_REACTIVATE="knowledgepro.phd.setting.reactivate";
	public static final String KNOWLEDGEPRO_PHD_SETTINGS = "knowledgepro.phd.settings.date";
	public static final String TEMPLET_REMINDER_MAILS="Document pending Reminder mails";
	public static final String TEMPLET_DUE_MAILS="Document Pending Due mails";
	public static final String TEMPLET_BATCH = "[BATCH]";
	public static final String SUBMISSION_DATE = "[SUBMISSION_DATE]";
	public static final String TEMPLATE_CERTIFICATE_NAME="[Certificate_Name]";
	public static final String CERTIFICATE_TEMPLATE_PRINT="printCertificateTemplate";
	public static final String PENDIND_DOCUMENT_SEARCH="documentPending";
	public static final String CERTIFICATE_APPLICATION_SUBMITTED_SUCESS_TEMPLATE = "Certificate Request Submit Success Mail for student";
	public static final String STUDENT_CERTIFICATE_TEMPLATE="CertificateDetailsTemplate";
	public static final String FEEPAYMENT_FOR_ACADEMICYEAR = "[FEEPAYMENT_FOR_ACADEMICYEAR]";
	public static final String PHD_FEE_PAYMENT_STATUS = "feePaymentStatus";
	public static final String HONOURS_COURSE = "honoursCourse";
	public static final String HONOURS_COURSE_DETAILS = "honoursCoursesDetails";
	public static final String HONOURS_COURSE_PRINT = "printApplication";
	public static final String CERTIFICATE_STATUS="certificateStatus";
	public static final String KNOWLEDGEPRO_NOTEMPLET="knowledgepro.no.template.found";
	public static final String TEMPLATE_INTERVIEW_CARD_CREATED_DATE = "[INTERVIEW_CARD_CREATED_DATE]";
	public static final String TEMPLATE_INTERVIEW_CARD_CREATED_TIME = "[INTERVIEW_CARD_CREATED_TIME]";
	public static final String TEMPLATE_INTERVIEW_SCHEDULED_HISTORY = "[INTERVIEW_SCHEDULED_HISTORY]";
	public static final String TEMPLATE_INTERVIEW_SCHEDULED_COUNT = "[INTERVIEW_SCHEDULED_COUNT]";
	public static final String INIT_HOSTEL_ADMISSION="initHostelAdmission";
	public static final String CONSOLIDATED_COLLECTION_LEDGER = "knowledgepro.adm.Consolidated.Collection.Ledger";
	public static final String COLLECTION_LEDGER_REPORT_EXCEL ="knowledgepro.Collection.Ledger.excel";
	public static final String HONOURS_COURSE_LIST = "honoursCourseList";
	public static final String HOSTEL_ADMISSION_REACTIVATE="knowledgepro.Hostel.Admission.reactivate";
	public static final String INIT_TELEPHONE_DIRECTORY = "initTelephoneDirectory";
	public static final String EMP_RES_PUB_REJECTED= "initResPubRejected";
	public static final String EMP_JOURNAL_TYPE_REQUIRED ="knowledgepro.employee.journal.type.required";
	public static final String EMP_CHAPTERS_COAUTHORED_REQUIRED ="knowledgepro.employee.chapters.coauthored.required";
	public static final String EMP_PERIODICALS_TYPE_REQUIRED ="knowledgepro.employee.periodicals.type.required";
	public static final String EMP_CONFERENCE_PRESENTATION_TYPE_REQUIRED ="knowledgepro.employee.conference.presentation.type.required";
	public static final String EMP_INVITEDTALK_TYPE_REQUIRED ="knowledgepro.employee.invitedtalk.type.required";
	public static final String EMP_PHD_GUIDED_REQUIRED ="knowledgepro.employee.Phd.guided.required";
	public static final String EMP_PHD_TYPE_REQUIRED ="knowledgepro.employee.Phd.type.required";
	public static final String EMP_OWN_PHD_TYPE_REQUIRED ="knowledgepro.employee.own.Phd.type.required";
	public static final String EMP_OWN_PHD_SUBMITTED_DATE_REQUIRED ="knowledgepro.employee.own.Phd.submitted.date.required";
	public static final String EMP_SEMINARS_ATTENDED_TYPE_REQUIRED ="knowledgepro.employee.seminarsattended.type.required";
	public static final String EMP_WORKSHOPS_TYPE_REQUIRED ="knowledgepro.employee.workshops.type.required";
	public static final String EMP_AWARDS_ORGANISATION_AWARDED_REQUIRED ="knowledgepro.employee.awards.organisation.awarded.required";
	public static final String EMP_BOOKSMONOGRAPHS_COAUTHORED_REQUIRED ="knowledgepro.employee.books.coauthored.required";
	public static final String EMP_RESEARCH_TYPE_REQUIRED ="knowledgepro.employee.research.type.required";
	public static final String EMP_RESEARCH_AMOUNTGRANTED_REQUIRED ="knowledgepro.employee.research.amountgranted.required";
	public static final String REJECTED_APPLICATION_SMS_TEMPLATE = "Rejected Certificate Application";
	public static final String RESEARCH_EMPLOYEE_EDITLISTPAGE = "editResearchAdminList";
	public static final String INIT_RESEARCH_DETAILS = "initResearchAdminSearch";
	public static final String LOAD_RESEARCH_DETAILS_ADMIN_EDIT = "ResearchDetailsAdminEdit";
	public static final String INIT_HOSTEL_TRANSACTION="initHostelTransaction";
	public static final String INIT_HOSTEL_TRANSACTION_IMAGES="initHostelTransactionImages";
	public static final String INIT_ASSIGN_FEES="initAssignFees";
	public static final String ADMISSIONFORM_PARENTGUARDIAN_CONTACT_NUMBER_REQUIRED = "admissionFormForm.parentguardian.contact.required";
	public static final String HOLIDAYS_INIT="holidaysInit";
	public static final String HOSTEL_HOLIDAYS_DATE = "knowledgepro.hostel.holidaysdate";
	public static final String INIT_HOSTELBLOCKS = "inithostelblocks";
	public static final String HOSTEL_HOSTELBLOCKS_EXISTS = "knowledgepro.hostel.blocks.exists";
	public static final String HOSTEL_HOSTELBLOCKS_REACTIVATE = "knowledgepro.hostel.blocks.exists.reactivate";
	public static final String HOSTEL_HOSTELBLOCKS_ADD_SUCCESS = "knowledgepro.hostel.blocks.added";
	public static final String HOSTEL_HOSTELBLOCKS_ADD_FAILED = "knowledgepro.hostel.blocks.addfailed";
	public static final String HOSTEL_HOSTELBLOCKS_DELETE_SUCCESS = "knowledgepro.hostel.blocks.deleted";
	public static final String HOSTEL_HOSTELBLOCKS_DELETE_FAILED = "knowledgepro.hostel.blocks.deletefailed";
	public static final String HOSTEL_HOSTELBLOCKS_UPDATE_SUCCESS = "knowledgepro.hostel.blocks.updated";
	public static final String HOSTEL_HOSTELBLOCKS_UPDATE_FAILED = "knowledgepro.hostel.blocks.updatefailed";
	public static final String HOSTEL_HOSTELBLOCKS_REACTIVATE_SUCCESS = "knowledgepro.hostel.blocks.reactivated";
	public static final String HOSTEL_HOSTELBLOCKS_REACTIVATE_FAILED = "knowledgepro.hostel.blocks.reactivate.failed";
	public static final String INIT_HOSTELUNITS = "inithostelunits";
	public static final String HOSTEL_HOSTELUNITS_EXISTS = "knowledgepro.hostel.units.exists";
	public static final String HOSTEL_HOSTELUNITS_REACTIVATE = "knowledgepro.hostel.units.exists.reactivate";
	public static final String HOSTEL_HOSTELUNITS_ADD_SUCCESS = "knowledgepro.hostel.units.added";
	public static final String HOSTEL_HOSTELUNITS_ADD_FAILED = "knowledgepro.hostel.units.addfailed";
	public static final String HOSTEL_HOSTELUNITS_DELETE_SUCCESS = "knowledgepro.hostel.units.deleted";
	public static final String HOSTEL_HOSTELUNITS_DELETE_FAILED = "knowledgepro.hostel.units.deletefailed";
	public static final String HOSTEL_HOSTELUNITS_UPDATE_SUCCESS = "knowledgepro.hostel.units.updated";
	public static final String HOSTEL_HOSTELUNITS_UPDATE_FAILED = "knowledgepro.hostel.units.updatefailed";
	public static final String HOSTEL_HOSTELUNITS_REACTIVATE_SUCCESS = "knowledgepro.hostel.units.reactivated";
	public static final String HOSTEL_HOSTELUNITS_REACTIVATE_FAILED = "knowledgepro.hostel.units.reactivate.failed";
	public static final String AVAILABLE_SEATS="availableSeats";
	public static final String HOSTEL_CANCEl_LEAVE = "hostelCancelLeave";
	public static final String HOSTEL_EDIT_STUDENT_LEAVE = "hostelEditStudentLeave";
	public static final String HOSTEL_CANCEl_LEAVE_FOR_REASON = "hostelCancelLeaveForReason";
	public static final String INIT_AUDITORIUM_TIME = "initAuditoriumTime";
	public static final String KNOWLEDGEPRO_PLEASESELECT="knowledgepro.select.atlist.onerecord";
	public static final String INIT_STUDENT_CHECK_IN="initHlStudentCheckIn";
	public static final String BIRT_VIEW_STUDENT_FEEDBACK_BY_DEPARTMENT = "birtViewStudentFeedBackByDepartment";
	public static final String REVALUATION_RETOTALLING_MARKS_ENTRY_FOR_SUBJECT_WISE ="revaluationOrRetotalingForSubject";
	public static final String REVALUATION_RETOTALLING_MARKS_ENTRY_FOR_SUBJECT_WISE_RESULT="revaluationOrRetotalingForSubjectResult";
	public static final String REVALUATION_RETOTALLING_MARKS_ENTRY_SUBJECTWISE_AJAX="ajaxRevaluationOrRetotalingMarksEntryForSubjectWise";
	public static final String REVALUATION_RETOTALLING_MARKS_ENTRY_SUBJECTWISE_FOR_MARKS_AJAX="ajaxRevaluationOrRetotalingMarksEntryForSubjectWiseForMarks";
	public static final String PHD_COMPLETION_DETAILS="completionDetails";
	public static final String INIT_ASSIGN_DEPARTMENT="initAssignDepartment";
	public static final String INIT_UPLOAD_ROOM_DETAILS="initUploadRoomDetails";
	public static final String UPLOAD_ROOM_DETAILS_FILE="knowledgepro.hostel.UploadRoomDetails";
	public static final String UPLOAD_ROOM_DETAILS_SUCCESS="knowledgepro.hostel.UploadRoomDetails.success";
	public static final String UPLOAD_ROOM_DETAILS_FAILURE="knowledgepro.hostel.UploadRoomDetails.fail";
	public static final String UPLOAD_ROOM_DETAILS_XLS="knowledgepro.hostel.UploadRoomDetails.Xlsheet";
	public static final String INIT_SUPP_DATA_CREATION = "initSuppDataCreation"; 
	public static final String SUPP_DATA_CREATION_UPDATE = "supplementryDataCreationUpdate";
	public static final String ADM_LOAN_LETTER = "admLoanLetter";
	public static final String INIT_CONVOCATION_REGISTRATION="initConvocationRegistration";
	public static final String STUDENT_REMARKS_REQUIRED= "knowledgepro.StudentLogin.student.remarks.Required";
	public static final String INIT_TELEPHONE_DIRECTORY_RESTRICTED = "initTelephoneDirectoryRestricted";
	public static final String SUCCESS_CONVOCATION_REGISTRATION="successConvocationRegistration";
	public static final String BIRT_CIAMARKS_HOD= "birtCiaMarksHod";
	public static final String INIT_BIOMETRIC="initBiometric";
	public static final String ASSIGN_EXTERNAL_TO_DEPARTMENT="AssignExternalTodepartment";
	public static final String VALUATION_CHALLAN_REPRINT = "initReprint";
	public static final String VALUATION_CHALLAN_REPRINT_DETAILS = "reprintDetails";
	public static final String PHD_QUALIFICATION_LEVEL="phdqualificationLevel";
	public static final String HOSTEL_STUDENT_DETAILS="initHostelStudentDetails";
	public static final String HOSTEL_STUDENT_VIEW_LIST ="hostelStudentViewListPage";
	public static final String HOSTEL_STUDENT_VIEW_DETAILS_LIST ="hostelStudentDetailsDisplay";
	public static final String STUDENTEDIT_VIEWPAGE_PAGE = "studentViewForCertificateRequest";
	public static final String INIT_SUPP_EXAM_ONLINE = "initSuppExamOnline";
	public static final String INIT_SUPP_EXAM_ONLINE_VEIRFY_SMART = "verifySmartCardSuppExamOnline1";
	public static final String INIT_SUPP_EXAM_ONLINE_VEIRFY_SMART_1 = "verifySmartCardSuppExamOnline2";
	public static final String INIT_SUPP_EXAM_ONLINE_PRINT_DATA= "printDetailsSuppExamOnline";
	public static final String TEMPLATE_SUPP_EXAM_ONLINE_PRINT="Supplementary application receipt print";
	public static final String LINK_FOR_FEE = "knowledgepro.studentlogin.online.supp.exam.fee";
	public static final String LINK_FOR_VENUE = "knowledgepro.studentlogin.online.supp.exam.venue";
	public static final String LINK_FOR_DATE_OF_EXAM="knowledgepro.studentlogin.online.supp.exam.date";
	public static final String LINK_FOR_VENUE1 = "knowledgepro.studentlogin.online.supp.exam.venue1";
	public static final String LINK_FOR_DATE_OF_EXAM1="knowledgepro.studentlogin.online.supp.exam.date1";
	public static final String TEMPLATE_ONLINE_SUPPLEMENTARY_APPLICATION_PRINT="Holistic/Indian Constitution Repeat Exam Application";
	public static final String TEMPLATE_VENUE= "[VENUE]";
	public static final String TEMPLATE_TIME= "[TIME]";
	public static final String TEMPLATE_EXAM_DATE= "[EXAM_DATE]";
	public static final String LINK_FOR_DATE_OF_EXAM3 ="knowledgepro.studentlogin.online.supp.exam.date3";
	public static final String LINK_FOR_DATE_OF_EXAMPREV15="knowledgepro.studentlogin.online.supp.exam.datePrev15";
	public static final String LINK_FOR_DATE_OF_EXAMPREV16="knowledgepro.studentlogin.online.supp.exam.datePrev16";
	public static final String LINK_FOR_DATE_OF_EXAMPREV18="knowledgepro.studentlogin.online.supp.exam.datePrev18";
	public static final String INIT_CONVOCATION_ONLINE_PAYMENT = "convocationOnlinePage";
	public static final String CONVOCATION_ONLINE_PAYMENT_CONFIRM = "convocationOnlineConfirm";
	public static final String CONVOCATION_REGISTRATION_STATUS_ACTION="convocationRegistrationStatus";
	public static final String INIT_Session_ENTRY = "initSessionEntry";
	public static final String INIT_VALUATION_STATISTICS = "initValuationStatistics";
	public static final String INIT_HOSTEL_WAITINGLISTVIEW = "hostelWaitingListView";
	public static final String HOSTEL_WAITINGLISTVIEW_SEARCH_STUDENTS = "hostelWaitingListViewSearchStudents";
	public static final String HOSTEL_WAITING_LIST_INTIMATION_TEMPLATE="Hostel waiting list intimation";
	public static final String TEMPLATE_HOSTEL_APPLICATIONNO="[HOSTEL APPLICATION NUMBER]";
	public static final String TEMPLATE_HOSTEL_NAME="[HOSTEL NAME]";
	public static final String TEMPLATE_HOSTEL_ROOMTYPE="[ROOMTYPE]";
	public static final String TEMPLATE_HOSTEL_PRIORITYNO="[PRIORITY NUMBER]";
	public static final String BIRT_ABSENCE_REPORT_LINK_STUDENT_DETAILS="BirtAbsenceReportsLinkForStudentDetails";
	public static final String INIT_HOSTEL_VISITORS_INFO="initHostelVisitorsInfo";
	public static final String EDIT_VISITORS_INFO="editVisitorsInfo";
	public static final String BIRT_VIEW_PEER_FEEDBACK= "birtViewPeerFeedback";
	public static final String INIT_TEMPHALLTICKET_IDCARD="initTempHallTicketOrIDCard";
	public static final String PRINT_HALLTICKET="printHallTicket";
	public static final String PRINT_IDCARD="printIDCard";
	public static final String EMPLOYEE_RESUMEFROMDATE_INVALID= "employee.resumeFromDate.invalid"; 
	public static final String EMPLOYEE_RESUMETODATE_INVALID= "employee.resumeToDate.invalid"; 
	public static final String EMPLOYEE_RESUMEFROMDATE_LARGE="employee.resumeFromDate.future";
	public static final String EMPLOYEE_RESUMETODATE_LARGE="employee.resumeToDate.future";
	public static final String INIT_VALUATION_SCHEDULE = "initValuationSchedule";
	
	// Forward path for MobNewsEventsDetailsAction master entry.
	public static final String MobNews_Events_Category_Entry = "MobNewsEventsCategoryEntryME";
	public static final String MobNews_Category_EXIST = "knowledgepro.admin.mobNewsEventsCategoryExist";
	public static final String MobNews_Category_REACTIVATE = "knowledgepro.admin.mobNewsEventsCategoryEntry.reactivate";
	public static final String MobNews_Category_ADD_SUCCESS = "knowledgepro.admin.mobNewsEventsCategory.addsuccess";
	public static final String MobNews_Category_ADD_FAILURE = "knowledgepro.admin.mobNewsEventsCategory.addfailure";
	public static final String MobNews_Category_UPDATE_SUCCESS = "knowledgepro.admin.mobNewsEventsCategory.updatesuccess";
	public static final String MobNews_Category_UPDATE_FAILURE = "knowledgepro.admin.mobNewsEventsCategory.updatefailure";
	public static final String MobNews_Category_DELETE_SUCCESS = "knowledgepro.admin.mobNewsEventsCategory.deletesuccess";
	public static final String MobNews_Category_DELETE_FAILURE = "knowledgepro.admin.mobNewsEventsCategory.deletefailure";
	public static final String MobNews_Category_REACTIVATE_SUCCESS = "knowledgrpro.admin.mobNewsEventsCategory.reactivateSuccess";
	public static final String MobNews_Category_REACTIVATE_FAILURE = "knowledgepro.admin.mobNewsEventsCategory.reactivateFailure";

	// Forward path for MobNewsEventsDetailsAction master entry.
	public static final String MobNews_Events_Details_Entry = "NewsEventsEntry";
	public static final String MobNews_Events_Details_EXIST = "knowledgepro.admin.mobNewsEventsDetailsExist";
	public static final String MobNews_Events_Details_REACTIVATE = "knowledgepro.admin.mobNewsEventsDetailsEntry.reactivate";
	public static final String MobNews_Events_Details_ADD_SUCCESS = "knowledgepro.admin.mobNewsEventsDetailsEntry.addsuccess";
	public static final String MobNews_Events_Details_ADD_FAILURE = "knowledgepro.admin.mobNewsEventsDetailsEntry.addfailure";
	public static final String MobNews_Events_Details_UPDATE_SUCCESS = "knowledgepro.admin.mobNewsEventsDetailsEntry.updatesuccess";
	public static final String MobNews_Events_Details_UPDATE_FAILURE = "knowledgepro.admin.mobNewsEventsDetailsEntry.updatefailure";
	public static final String MobNews_Events_Details_DELETE_SUCCESS = "knowledgepro.admin.mobNewsEventsDetailsEntry.deletesuccess";
	public static final String MobNews_Events_Details_DELETE_FAILURE = "knowledgepro.admin.mobNewsEventsDetailsEntry.deletefailure";
	public static final String MobNews_Events_Details_REACTIVATE_SUCCESS = "knowledgrpro.admin.mobNewsEventsDetailsEntry.reactivateSuccess";
	public static final String MobNews_Events_Details_REACTIVATE_FAILURE = "knowledgepro.admin.mobNewsEventsDetailsEntry.reactivateFailure";

	public static final String PHD_INFO_APPLICATIONFORM="initPhdApplicationForm";
	public static final String PHD_INFO_APPLICATIONFORM_SEARCH="initPhdemployeeSearch";
	public static final String PHD_AWARD_DATE_LARGE = "KnowledgePro.award.date.large";
	public static final String PHD_AWARD_DATE_INVALID = "KnowledgePro.award.date.invalid";
	public static final String PHD_BANKACCOUNTNO_INVALID = "KnowledgePro.phd.bankacc.invalid";
	public static final String PHD_CADDRESS_PIN = "KnowledgePro.phd.caddress.invalid";
	public static final String PHD_PADDRESS_PIN = "KnowledgePro.phd.paddress.invalid";
	public static final String PHD_INFO_SUBMIT_CONFIRM = "KnowledgePro.phd.address.successfully";
	public static String PHD_EMPLOYEE_MOBILE_INVALID="knowledgepro.phd.Mobile.Limit";
	public static final String PHD_DOCUMENT_SUBMISSION="initPhdDocumetSubmit";
	public static final String REVALUATION_RETOTALLING_MARKS_ENTRY_FOR_THIRDEVALUATION="revaluationOrRetotalingForThirdevaluation";
	public static final String VALUATION_SCHEDULE_REMAINDER = "Valuation Schedule Remainder";
	public static final String EXAM_DATE="[EXAM_DATE]";
	public static final String SUBJECT_DETAILS="[SUBJECT_DETAILS]";
	public static final String PRINT_FINE_ENTRY_DETAILS="printFineEntryDetails";
	public static final String VALUATION_STATISTICS = "valuationStatistics";
	public static final String VALUATION_STATISTICS_SUBJECTS = "subjectStatistics";
	public static final String VALUATION_STATISTICS_STUDENTS = "studentsList";
	public static final String DISPLAY_ABSCENT_DETAILS="displayAbscentDetails";
	public static final String VALUATION_STATISTICS_DEAN = "deanValuationStatistics";
	public static final String EMP_RES_ACADEMIC_YEAR_REQUIRED="KnowledgePro.academic.year.Required";
	public static final String EMP_RES_TYPEOFPGM_REQUIRED="knowledgepro.type.of.pgm.required";
	public static final String PHD_INTERNAL_GUIDE="initInternalGuide";
	public static final String ADMISSIONFORM_PREREQUISITE_REQUIRED_EITHER_ONE = "admissionForm.prerequisite.required.either.one";
	public static final String PHD_VOUCHER_NUMBER="initPhdVoucherNumber";
	public static final String PHD_FINANCIAL_YEAR_REACTIVATE = "knowledgepro.phd.financialyear.exists.reactivate";
	public static final String STUDENT_HOSTEL_LEAVE = "studentHostelLeave";
	public static final String NEWS_EVENTS_PRE_APPROVAL = "NewsEventPreApproval";
	public static final String NEWS_EVENTS_POST_APPROVAL = "NewsEventPostApproval";
	public static final String INIT_NEWS_EVENTS_SEARCH_APPROVAL = "InitSearchNewsApproval";
	public static final String LIST_NEWS_EVENTS_SEARCH_APPROVAL= "ListSearchNewsApproval";
	public static final String EMPLOYEE_RESUME_INDUSTRY_DESIGNATION="employee.resume.industry.designation.required";
	public static final String EMPLOYEE_RESUME_INDUSTRY_INSITUTION="employee.resume.industry.institution.required";
	public static final String EMPLOYEE_RESUME_TEACHING_DESIGNATION="employee.resume.teaching.designation.required";
	public static final String EMPLOYEE_RESUME_TEACHING_INSITUTION="employee.resume.teaching.instution.required";
	public static final String STUDENT_HOSTEL_LEAVE_VIEW = "studentHostelLeaves";
	public static final String STUDENT_HOSTEL_LEAVE_TEMPLATE = "Hostel Leave - Parent";
	public static final String INIT_DOWNLOAD_APPLICATION = "initDownloadApplication";
	public static final String PRINT_RESUME = "printDownloadApplicationResume";
	public static final String DOWNLOAD_APP_EXPORT_TO_EXCEL = "downloadAppExportTOExcel";
	public static final String INIT_HOSTEL_STUDENT_CHECK_OUT="initHostelStudentCheckOut";
	public static final String INIT_HOSTEL_STUDENT_CHECK_OUT_DETAILS="HostelStudentCheckOutDetails";
	public static final String HOSTEL_STUDENT_CHECK_OUT_ADD_SUCCESS = "knowledgepro.hostel.student.checkout.added.success";
	public static final String HOSTEL_STUDENT_CHECK_OUT_ADD_FAILED = "knowledgepro.hostel.student.checkout.added.failed";
	public static final String GENERATED_NO = "[VOUCHER_NO]";
	public static final String TOTAL_AMOUNT = "[TOTAL_AMOUNT]";
    public static final String PHD_STUDENT_REMENDERATION="initStudentReminderation";
	public static final String PRINT_STUDENT_REMENDERATION="printStudentRemenderation";
	public static final String PHD_GUIDE_FEES_PAYMENT="initGuideFeesPayment";
	public static final String EVENT_LOCATION_BIOMETRIC_DETAILS="eventLoactionBiometricDetails";
	public static final String EVENT_SCHEDULE_FOR_ATTENDANCE="eventScheduleForAttendance";
	public static final String DOWNLOAD_PDF_FILE = "initDownloadPurchaseRequest";
	public static final String INIT_HOSTEL_LEAVE_APPROVAL = "initHostelLeaveApproval";
	public static final String APPROVING_EVENTS = "initApproveEvents";
	public static final String AUDITORIUM_BOOKING_EVENTS_TEMPLATE="Auditorium Booking Events";
	public static final String AUDITORIUM_APPROVING_EVENTS_TEMPLATE="Auditorium Approving Events";
	public static final String TEMPLATE_AUDITORIUM_BLOCK = "[BLOCK]";
	public static final String TEMPLATE_AUDITORIUM_VENUE = "[VENUE]";
	public static final String TEMPLATE_AUDITORIUM_FROM_TIME = "[FROM TIME]";
	public static final String TEMPLATE_AUDITORIUM_TO_TIME = "[TO TIME]";
	public static final String AUDITORIUM_APPROVING_EVENTS_SMS_TEMPLATE="Auditorium Booking Status SMS";
	public static final String AUDITORIUM_ADMIN_MAIL_ID="knowledgepro.auditorium.admin.mail.id";
	public static final String VIEW_APPROVAL_LEAVE = "viewLeaveApprovalDetails";
	public static final String HOSTEL_LEAVE_APPROVAL = "Hostel Leave - Student";
	public static final String TEMPLATE_HOSTEL_LEAVE_APPROVAL_FROM_DATE = "[FROM DATE]";
	public static final String TEMPLATE_HOSTEL_LEAVE_APPROVAL_TO_DATE = "[TO DATE]";
	public static final String HOSTEL_LEAVE_REJECT = "Hostel Leave Reject";
	public static final String INIT_SC_LOST_CORRECTION="initScLostCorrection";
	public static final String INIT_SC_LOST_CORRECTION_DETAILS="ScLostCorrectionDetails";
	public static final String SC_LOST_CORRECTION_ADD_SUCCESS="knowledgepro.smartcard.lostcorrection.added.success";
	public static final String SC_LOST_CORRECTION_ADD_FAIL="knowledgepro.smartcard.lostcorrection.added.fail";
	public static final String VIEW_PHOTO_NEWS_EVENTS = "viewPhotoNewsAndEvents";
	public static final String NEWS_EVENTS_ADMIN = "NewsEventAdmin";
	public static final String INIT_NEWS_EVENTS_SEARCH_ADMIN = "InitSearchNewsAdmin";
	public static final String INIT_NEWS_EVENTS_SEARCH_POST= "InitSearchNewsPost";
	public static final String LIST_NEWS_EVENTS_SEARCH_ADMIN= "ListSearchNewsAdmin";
	public static final String INIT_NEWS_EVENTS_SEARCH_POST_DEPARTMENT="initPostEventDeptEntry";
	public static final String NEWS_EVENTS_SEARCH_POST_EVENT="NewsPostEvent"; 
	public static final String HOSTEL_STATUS_PAGE= "hostelStatus";
	public static final String INIT_HOSTEL_EXPORT_PHOTOS = "initHostelExportPhotos";
	public static final String NEWS_EVENTS_SEARCH_POST_EVENT_APPROVAL="NewsPostEventApproval";
	public static final String TEMPLATE_GENERATED = "[GENERATED_DATE]";
	public static final String VIEW_PREVIOUS_LEAVE_DETAILS = "viewPreviousLeaveDetails";
	public static final String NEWS_EVENTS_SUBMITTED_TEMPLATE="News and Events Submitted";
	public static final String NEWS_EVENTS_ADMIN_NOTIFICATION_MAIL="News and Events Admin Notification mail";
	public static final String NEWS_EVENTS_TONAME="knowledgepro.news.events.sendmail.to.name";
	public static final String NEWS_EVENTS_FROMNAME="knowledgepro.news.events.sendmail.fromName";
	public static final String NEWS_EVENTS_TOADDRESS="knowledgepro.news.events.sendmail.to.id";
	public static final String NEWS_EVENTS_ADMIN_TONAME="knowledgepro.news.events.sendmail.admin.to.name";
	public static final String NEWS_EVENTS_ADMIN_FROMNAME="knowledgepro.news.events.sendmail.admin.fromName";
	public static final String NEWS_EVENTS_ADMIN_TOADDRESS="knowledgepro.news.events.sendmail.admin.to.id";
	public static final String LOGIN_DISPLAY_NEW = "displayLoginNew";
	public static final String LOGIN_PAGE_NEW = "loginPageNew";
	public static final String EMPLOYEE_NAMENOMINEE_INVALID="knowledgepro.employee.NameNominee.Limit";
	public static final String EMPLOYEE_NAMEGUARDIAN_INVALID="knowledgepro.employee.NameGuardian.Limit";
	public static final String NEWS_EVENTS_SUBMIT_CONFIRM="newsEventsSubmitConfirm";
	public static final String HOSTEL_ABSENT_MAIL_FOR_STUDENT="Hostel Absent Mail For Student";
	public static final String TEMPLATE_UNIT = "[UNIT]";
	public static final String TEMPLATE_ROOM = "[ROOM]";
	public static final String TEMPLATE_BED = "[BED]";
	public static final String TEMPLATE_SESSION = "[SESSION]";
	public static final String TEMPLATE_ABSENT_DETAILS = "[ABSENTDETAILS]";
	public static final String HOSTEL_ABSENT_MAIL_FOR_PARENT="Hostel Absent Mail For parent";
	public static final String TEMPLATE_PARENT_NAME = "[PARENTNAME]";
	public static final String HOSTEL_ABSENT_SMS_FOR_STUDENT="Hostel Absent Sms For Student";
	public static final String HOSTEL_ABSENT_SMS_FOR_PARENT="Hostel Absent Sms For Parent";
	public static final String VIEW_HOSTEL_DISCIPLINARY_DETAILS_PAGE = "viewHostelDisciplinaryDetailsPage";
	public static final String INIT_EDIT_DISCIPLINARY_ACTION = "initEditDisciplinaryDetails";
	public static final String INIT_SC_LOST_CORRECTION_VIEW="initScLostCorrectionView";
	public static final String INIT_SC_LOST_CORRECTION_VIEW_DETAILS="ScLostCorrectionViewDetails";
	public static final String SC_LOST_CORRECTION_VIEW_ADD_SUCCESS = "knowledgepro.smartcard.lostcorrection.view.added.success";
	public static final String SC_LOST_CORRECTION_VIEW_ADD_FAIL="knowledgepro.smartcard.lostcorrection.view.added.fail";
	public static final String HOSTEL_Exemption= "initHostelExemption";
	public static final String INIT_STUDENT_CARPASS = "initStudentCarPass";
	public static final String STUDENT_CARPASS_APPLICATION = "StudentCarPassApplication";
	public static final String PRINT_STUDENT_CAR_PASS = "printStudentCarPass";
	public static final String DISPLAY_AUDITORIUM_APPROVED_AJAX = "displayApprovedEventsAgendaAjax";
	
	
	public static final String NEWS_EVENTS_SUBMITTED_USER_TEMPLATE="News and Events Sender Notification mail";
	public static final String NEWS_EVENTS_USER_TONAME="knowledgepro.news.events.user.mail.to.name";
	public static final String NEWS_EVENTS_USER_FROMNAME="knowledgepro.news.events.sendmail.user.fromName";
	public static final String INIT_SC_LOST_CORRECTION_PROCESS="initScLostCorrectionProcess";
	public static final String INIT_SC_LOST_CORRECTION_PROCESS_DETAILS="ScLostCorrectionProcessDetails";
	public static final String SC_LOST_CORRECTION_PROCESS_ADD_SUCCESS = "knowledgepro.smartcard.lostcorrection.process.added.success";
	public static final String SC_LOST_CORRECTION_PROCESS_ADD_FAIL="knowledgepro.smartcard.lostcorrection.view.added.fail";
	public static final String INIT_SC_LOST_CORRECTION_PROCESSED="initScLostCorrectionProcessed";
	public static final String INIT_SC_LOST_CORRECTION_PROCESSED_DETAILS="ScLostCorrectionProcessedDetails";
	public static final String SC_LOST_CORRECTION_PROCESSED_ADD_SUCCESS="knowledgepro.smartcard.lostcorrection.processed.added.success";
	public static final String SC_LOST_CORRECTION_PROCESSED_ADD_FAIL="knowledgepro.smartcard.lostcorrection.processed.added.fail";
	public static final String SC_LOST_CORRECTION_PROCESSED_RECEIVED_ADD_SUCCESS="knowledgepro.smartcard.lostcorrection.processed.received.added.success";
	public static final String SC_LOST_CORRECTION_PROCESSED_RECEIVED_ADD_FAIL="knowledgepro.smartcard.lostcorrection.processed.received.added.fail";
	public static final String SC_LOST_CORRECTION_SMS="Smart Card Sms";
	public static final String TEMPLATE_SMARTCARD_TYPE = "[SMARTCARDTYPE]";
	public static final String NEWS_EVENTS_USER_TO_EMAIL_ID="knowledgepro.news.events.sendmail.user.emailId";
	public static String KNOWLEDGEPRO_PHD_STARTDATE_CONNOTBELESS = "knowledgepro.phd.conference.startdate.connotbeless";
	public static final String AJAX_HOSTEL_DETAILS = "ajaxHostelDetailsDisplay";
	public static final String PHD_NAMEADDRESS_INVALID="knowledgepro.phd.NameAddress.Limit";
	public static final String PHD_PRESENT_ADDRESS_INVALID="knowledgepro.phd.presentAddress.Limit";
	public static final String KNOWLEDGEPRO_PHD_VIVATITLE_LIMIT="knowledgepro.phd.viva.title.Limit";
	public static final String KNOWLEDGEPRO_PHD_RESEARCH_DESCRIPTION="knowledgepro.phd.research.description.Limit";
	public static final String INIT_CATEGORY = "initcategory";
	public static final String SC_LOST_CORRECTION_PROCESSED_RESEND_SUCCESS="knowledgepro.smartcard.lostcorrection.processed.resend.success";
	public static final String SMARTCARD_STATUS = "Smart Card Status";
	public static final String INIT_SC_LOST_CORRECTION_CANCEL="initScLostCorrectionCancel";
	public static final String INIT_SC_LOST_CORRECTION_CANCEL_DETAILS="ScLostCorrectionCancelDetails";
	public static final String SC_LOST_CORRECTION_CANCEL_SUCCESS="knowledgepro.smartcard.lostcorrection.cancel.success";
	public static final String SC_LOST_CORRECTION_CANCEL_FAIL="knowledgepro.smartcard.lostcorrection.cancel.fail";
	public static final String GUEST_FACULTY_DEPT="knowledgepro.employee.validate.department";
	public static final String GUEST_WORK_LOCATION_REQUIRED="knowledgepro.admin.EmployeeWorkLocation.required";
	public static String NEWS_DESCRIPTION = "";
	public static String STUDENT_CHANGE_PASSWORD = "";
	public static byte[] LOGIN_LOGO;
	public static boolean OPEN_HONOURSCOURS_LINK = false;
	public static boolean CONVOCATION_REGISTRATION = false;
	public static final String BLOCK_BO = "blockBo";
	public static final String ROOM_MASTER = "roomMaster";
	public static final String ROOM_MASTER_SEARCH="roomMasterSearch";
	public static final String INIT_GUEST_FACULTY_EXCEL_REPORT = "initGuestFacultyExcelReport";
	public static final String SEARCH_GUEST_REPORT="guestFacultySearch";
	public static final String INIT_EXCEL_GUEST_REPORT="initExcelGuestReport";
	public static final String EMP_GUEST_EXCEL_REPORT="knowledgepro.guest.faculty.report";
	public static final String INIT_SAPVENUE = "initSapVenue";
	public static final String VIEW_EXTENDION_NUMBERS="viewExtensionNumbers";
	public static final String INIT_EXAM_REGISTRATION_DETAILS = "initExamRegistrationDetails";
	public static final String PHD_PERMANENT_ADDRESS_INVALID="knowledgepro.phd.permanentAddress.Limit";
	public static final String INIT_EXAM_HALL_ENTRY="initExamHallEntry";
	public static final String STUDENT_ATTENDANCE_REGULAR_ABSENT_SMS_FOR_PARENT="Student Attendance Regular Absent Sms For Parent";
	public static final String STUDENT_ATTENDANCE_INTERNAL_ABSENT_SMS_FOR_PARENT="Student Attendance Internal Absent Sms For Parent";
	public static final String TEMPLATE_SMS_SUBJECT_CODE="[SUBJECTCODES]";
	public static final String TEMPLATE_SMS_SUBJECT_NAME="[STUDENTNAMES]";
	public static final String EXAM_REGISTRATION_PROCEED_WITH_SMARTCARD_DETAILS = "examRegisProceedwithSmartCardDetails";
	public static final String EXAM_REGISTRATION_SMART_CARD_ENQUIRY = "examRegisSmartCardEnquiry";
	public static final String DOWNLOAD_HALLTICKET = "downloadHallTicket";
	public static final String DISPLAY_HALLTICKET_LINK = "displayHallTicketLink";
	public static final String INIT_EXAM_REGISTRATION_DETAILS_ALREADY_EXIST = "examRegistrationAlreadyExist";
	public static final String INIT_EXAM_SCHEDULE="initExamSchedule";
	public static final String EXAM_SCHEDULE="ExamSchedule";
	public static final String COMED_K = "COMEDK";
	public static final String INIT_SAP_MARK_UPLOAD="initSapMarksUpload";
	public static final String UPLOAD_SAP_MARKS_INTERNAL = "knowledgepro.sap.marksexam.internal.overall.excelupload";
	public static final String UPLOAD_SAP_MAX_MARKS_UPLOAD = "knowledgepro.sap.marksexam.maximum.marks.forSap";
	public static final String INIT_SAP_REGISTRATION = "initSapRegistration";
	public static final String SAP_ADMIN_NOTIFICATION_MAIL="SAP Registration Admin Notification mail";
	public static final String SAP_ADMIN_NOTIFICATION_MAIL_ID="knowledgepro.sap.admin.sendmail.to.id";
	public static final String SAP_ADMIN_NOTIFICATION_MAIL_NAME="knowledgepro.sap.admin.sendmail.to.name";
	public static final String SAP_STUDENT_SMS="SAP Registration Student Sms";
	public static final String SAP_ADMIN_MAIL_FROMNAME="knowledgepro.sap.mail.fromname";
	public static final String TEMPLATE_STUDENTEMAIL_SAP = "[STUDENTEMAIL]";
	public static final String INIT_SC_VIEW_STUDENT_DETAILS="initScViewStudentDetails";
	public static final String SC_VIEW_STUDENT_DETAILS="ScViewStudentDetailsView";
	public static final String TEMPLATE_SAP_KEY = "[KEY]";
	public static final String SAP_KEY_MAIL_FOR_STUDENT="SAP Keys For Student mail";
	public static final String INIT_EXAM_REGISTRATION_DETAILS_OFFLINE = "initOfflineSAPExamRegistration";
	public static final String EXAM_REGISTRATION_DETAILS_WITH_OFFLINE = "offlineSAPExamRegistration";
	public static final String STUDENT_DETAILS_DISPLAY="studentDetailsDisplay";
	public static final String STUDENT_LOGIN_FAILURE_CJC="loginFailureCjc";
	public static final String STUDENT_LOGIN_LOGOUT_SUCCESS_CJC="logoutSuccessCjc";
	public static final String STUDENT_LOGIN_CJC="displayLoginCjc";
	public static final String EXAM_REGISTRATION_DETAILS_RECEIPT_NUMBER = "sapRegistrationReceiptNumber";
	public static final String STUDENT_LOGIN_SUCCESS_CJC="loginSuccessCJC";
	public static final String INIT_EXAM_MIDSEM_EXEMPTION="initExamMidsemExemption";
	public static final String INIT_EXAM_MIDSEM_EXEMPTION_DETAILS="initExamMidsemExemptionDetails";
	public static final String LINK_FOR_FEE_FINE = "knowledgepro.studentlogin.online.supp.exam.fine.fee";
	public static final String SMARTCARD_LOST_CORRECTION_EXCEL_REPORT = "knowledgepro.smartcard.lostcorrection.view.excelreport";
	public static final String INIT_SYLLABUS_DISPLAY_FOR_STUDENT="initSyllabusDisplayForStudent";
	public static String STUDENT_IMAGE="";
	public static String EMOLOYEE_IMAGE="";
	public static final String INIT_STUDENT_DETAILS_REPORT = "initStudentDetailsReport";
	public static final String STUDENT_DETAILS_REPORT = "studentDetailsReport";
	public static final String INIT_EXAM_SUPPLY_IMPROV_APPL_DATE="initExtendSupplyImprvApplDate";
	public static final String INIT_STUDENT_DETAILS_REPORT_EXCEL = "initStudentDetailsReportExcel";
	public static final String INIT_EXAM_INVIGILATOR_ALLOTMENT = "initExamInvigilatorAllotment";
	public static final String EXAM_INVIGILATOR_ALLOTMENT = "examInvigilatorAllotment";
	public static final String INIT_SYLLABUS_DISPLAY_FOR_SUPPLIMENTORY="initSyllabusDisplayForSupplimentory";
	public static final String SUPPORT_REQUEST_STATUS_STAFF_MAIL="Support Request Status Mail For Staff";
	public static final String SUPPORT_REQUEST_STATUS_STUDENT_MAIL="Support Request Status Mail For Student";
	public static final String SUPPORT_REQUEST_STATUS_STAFF_SMS="Support Request Status SMS For Staff";
	public static final String SUPPORT_REQUEST_STATUS_STUDENT_SMS="Support Request Status SMS For Student";
	public static final String TEMPLATE_CATEGORY_NAME = "[CATEGORY]";
	public static final String TEMPLATE_EMPLOYEE_ID = "[EMPLOYEE_ID]";
	public static final String INIT_ROOM_ALLOTMENT = "initRoomAllotment";
	public static final String INIT_FREE_FOOD_ISSUE="initFreeFoodIssue";
	public static final String STUDENT_DETAILS_REPORT_EXCEL = "KnowledgePro.student.excelreport";
	public static final String SCHEDULED_EMAIL_FOR_USER2="Support Request Escalation2";
	public static final String SCHEDULED_EMAIL_NO_OF_ISSUES_OPEN = "[NO_OF_OPEN_ISSUES]";
	public static final String INIT_EXAM_ROOM_SPECIALIZATION = "initExamRoomSpecialization";
	public static final String INIT_EXAM_ROOM_AVAILABILITY = "initExamRoomAvailability";
	public static final String INIT_SINGLE_FIELD_POOL_CREATION = "initExamPoolCreationSingleField";
	public static final String POOL_NAME_DETAILS_REACTIVATE= "knowledgepro.exam.allotment.pool.reactivate";
	public static final String INIT_EXAM_ROOM_ALLOTMENT_POOL_SETTING = "initExamRoomAllotmentPoolSetting";
	public static final String INIT_EXAM_ROOM_ALLOTMENT_CYCLE = "initExamRoomAllotmentCycle";
	public static final String REQUEST_ID = "[REQUESTID]";
	public static final String INIT_ROOM_CYCLE_ENTRY = "initRoomAllotmentCycleEntry";
	public static final String INIT_STUDENTS_CLASS_GROUP = "initStudentsClassGroup";
	public static final String VALUATION_REMUNIRATION_PAYMENT_SMS="Valuation Remuneration Payment SMS";
	public static final String VALUATION_DATE="[VOCHER_DATE]";
	public static final String INVILIGATOR_TEACHER_ALLOTMENT_SUCCESS = "knowledgepro.teacher.allotment.success";
	public static final String INVILIGATOR_TEACHER_ALLOTMENT_FAILURE = "knowledgepro.teacher.allotment.failure";
	public static final String INVIGILATORS_LIST="invigilatorsListForExamAssign";
	public static final String MODE_OF_PAYMENT="[MODE_OF_PAYMENT]";	
	public static final String SEARCHED_INVIGILATORS_LIST="searchedInvigilatorsListForExam";
	public static final String INIT_ROOM_ALLOTMENT_STATUS = "initRoomAllotmentStatus";
	public static final String INIT_EXAM_ROOM_ALLOTMENT_GROUP_WISE = "initExamRoomAllotmentGroupWise";
	public static final String EXAM_INVIGILATION_DUTY_SETTING ="examInvigilationDutySetting";
	public static final String TEMPLATE_SMS_NUMBER = "[NUMBER]";
	public static final String EXAM_INVIGILATORS_DUTY_EXEMPTION_LIST="examInvigilatorsDutyExemptionList";
	public static final String INIT_INVIGILATORS_DATEWISE_EXCEMPTION="InitInvExemptionDatewise";
	public static final String INVIGILATORS_DATEWISE_EXCEMPTION_LIST="ExemptionDatewiseList";	
	public static final String SEARCHED_INVIGILATORS_DATEWISE_EXCEMPTION_LIST="searchInvListDatewise";
	public static final String CATEGORY_MAIL_SUPPORT_REQUEST="Support Request Submission Email";
	public static final String TEMPLATE_DESCRIPTION = "[DESCRIPTION]";
	public static final String SCHEDULED_EMAIL_FOR_USER1="Support Request Escalation1";
	public static final String ADMISSION_REMARKS="Remarks";
	public static final String INV_DATEWISE_ALREADYEXCEMPTED_LIST="examInvDatewiseExemptedlist";
	public static final String INVIGILATORS_DATEWISE_FROM_DATE_REQUIRED_LIST="knowledge.inv.datewise.from.date.required";	
	public static final String INVIGILATORS_DATEWISE_TO_DATE_REQUIRED_LIST="knowledge.inv.datewise.to.date.required";
	public static final String INVIGILATORS_DATEWISE_SESSION_REQUIRED="knowledge.inv.datewise.session.required";
	public static final String INVIGILATORS_DATEWISE_CAMPUS_REQUIRED="knowledge.inv.datewise.workLocation.required";
	public static final String INVIGILATORS_DATEWISE_ISADD_REQUIRED="knowledge.inv.datewise.isAdd.required";
	public static final String EXAM_INVIGILATORS_DUTY_EDIT_LIST="examInvigilatorsDutyEditList";
	public static final String UPLOAD_SAP_MARKS="initUploadSAPMarks";
	public static final String Upload_SAP_MARKS_XLS="knowledgepro.upload.sap.marks";
	public static final String STUDENT_SAP_EXAM_RESULS="displaySAPExamResuls";
	public static final String GUEST_FACULTY_BANK_DETAILS_EDIT = "guestFacultyBankDetailsEdit";
	public static final String VERIFY_TEACHER_AVAILABLE="TeacherAvailableList";
	public static final String VERIFY_TEACHER_EXEMPTION="TeacherExemptionList";
	public static final String VERIFY_TEACHER_EXEMPTION_DATEWISE="TeacherDatewiseExemptionList";
	public static final String TEMPLATE_CAMPUS="[CAMPUS]";
	public static final String PHOTO_UPLOADED="Photo Uploaded";
	public static final String PAYMENT_CONFIRMATION_TEMPLATE = "Online payment confirmation-Billdesk";
	public static final String TEMPLATE_APPLIED_COURSE="[APPLIED_COURSE]";
	public static final String TEMPLATE_STUDENT_CATEGORY="[STUDENT_CATEGORY]";
	public static final String TEMPLATE_MOBILENUMBER_WITH_COUNTRYCODE="[MOBILENUMBER_WITH_COUNTRYCODE]";
	public static final String TEMPLATE_CANDIDATE_REFNUMBER="[CANDIDATE_REF_NUMBER]";
	public static final String TEMPLATE_TRANSACTION_REFNUMBER="[TRANSACTION_REF_NUMBER]";
	public static final String ATTENDANCE_ENTRY_SELECT_ONE_BATCH = "knowledgepro.attendanceentry.selectone.batch";
	public static final String NEW_INTERNAL_MARKS_ENTRY = "newInternalMarksEntry";
	public static final String NEW_INTERNAL_MARKS_ENTRY_RESULT = "newInternalMarksEntryResult";
	public static final String PRINT_REPORT_INTERNAL_MARKS = "printReportInternalMarks";
	public static final String INIT_EXAM_ROOM_ALLOTMENT_STATUS = "initExamRoomAllotmentStatus";
	public static final String EMPLOYEE_SUBJECT_REQUIRED = "knowledgepro.admin.EmployeeSubject.required";
	public static final String EMPLOYEE_PHOTO_REQUIRED = "knowledgepro.admin.onlineresume.photo.required";
	public static final String SMS_TEMPLATE_INTERVIEW_CARD_DATE_CHANGE = "Interview Card Date Modification SMS";
	public static final String SMS_TEMPLATE_INTERVIEW_CARD_SELECTION_DATE="knowledgepro.admin.template.SelectionDate";
	public static final String EXAM_CENTRE_VALIDATION_AJAX = "AjaxExamCenter";
	public static final String INIT_ROOM_ALLOTMENT_STUDENTS = "initRoomAllotmentForStudents";
	public static final String CERTIFICATE_COURSE_STATUS="certificateCourseStatus";
	public static final String PUBLIS_REGULAR_EXAM_HALL_TICKET="Publish Regular Exam Hall Ticket";
	public static final String PUBLIS_REGULAR_EXAM_MARKS_CARD="Publish Regular Exam Marks card";
	public static final String PUBLIS_SUPPLEMENTARY_EXAM_MARKS_CARD="Publish Supplementary Exam Marks card";
	public static final String INIT_MAINTENANACE_ALERT = "initMaintenanceAlert";
	public static final String ADMISSION_SELECTION_PROCESS_DATE="Selected date for Interview";
	public static final String INIT_EXAM_MIDSEM_REPEAT="initExamMidsemRepeat";
	public static final String INIT_REPER_EXAM_APPLICATION="initRepeatExamApplication"; 
	public static final String MID_SEM_REPEAT_EXAM_SMS= "SMS For Repeat Mid Sem Exam Application Submit";
	public static final String MID_SEM_REPEAT_EXAM_MAIL= "Mail For Repeat Mid Sem Exam Application Submit";
	public static final String REPER_EXAM_APPLICATION_PRINT= "repeatExamApplicationPrint";
	public static final String REPER_EXAM_HALL_TICKET_PRINT= "repeatExamHallTicketPrint";
	public static final String COE_APPROVE_APPLICATION="coeApproveMidsemApplication";
    public static final String MID_SEM_REPEAT_EXAM_APPROVE_SMS= "SMS For Repeat Mid Sem Exam Application Approved";
    public static final String MID_SEM_REPEAT_EXAM_APPROVE_MAIL= "Mail For Repeat Mid Sem Exam Application Approved";
    public static final String INIT_MIDSEM_REPEAT_EXAM_FEEPAYMENT="initRepeatExamFeePayment";
    public static final String MIDSEM_REPEAT_EXAM_FEEPAYMENT_PROCESS="repeatExamFeePaymentProcess";
    public static final String MIDSEM_REPEAT_EXAM_FEEPAYMENT = "repeatExamFeePayment";
    public static final String MID_SEM_REPEAT_EXAM_FEEPAYMENT_REMINDER_SMS= "Reminder Sms For Mid Semester Repeat Exam Fee Payment";
	public static final String MID_SEM_REPEAT_EXAM_FEEPAYMENT_REMINDER_MAIL= "Reminder Mail For Mid Semester Repeat Exam Fee Payment";
	public static final String INIT_REPER_EXAM_APPLICATION_SUCCESS="applicationsuccesssubmit";
	public static final String INIT_MIDSEM_REPEAT_EXAM_FEEPAYMENT_SUCCESS="repeatExamFeePaymentSuccess";
	public static final String MID_SEM_REASON_REQUIRED = "knowledgepro.midsem.reason.required";
	public static final String REPEAT_MID_SEM_EXEMPTION="midsemRepaetExemption";
	public static final String INIT_SYLLABUS_UPLOAD = "initSyllabusUpload";
	public static final String SYLLABUS_UPLOAD="knowledgepro.upload.syllabus";
	public static final String INIT_FEE_REFUND = "initFeeRefund";	
	public static final String Upload_OFFLINE_APPLICATION_XLS="knowledgepro.upload.offline.application";
	public static final String INTERNAL_MARKS_REPORT_PRINT_AFTER_CONFIRM = "internalMarksReportPrintAfterConfirm";
	public static final String TEMPLATE_FEE_END_DATE= "[FEESDATE]";
	public static final String TRANSCRIPT_DESCRIPTION_UG = "1)CIA - Continuous Internal Assessment 2)ATT - Attendance 3)Candidates with more than 75% attendance only will be awarded attendance";
	public static final String TRANSCRIPT_DESCRIPTION_PG = "1)CIA - Continuous Internal Assessment 2)To pass a minimum of 40% per subject and an aggregate of 50% is required";
	public static final String UPLOAD_READMISSION_SELECTION_XLS="knowledgepro.upload.readmission.selection";
	public static final String INIT_HOSTEL_READMISSION_SELECTION = "initHostelReAdmissionSelection";
	public static final String DESCIPLINARY_DETAILS_DISPLAY_FOR_HOSTEL_READMISSION = "disciplinaryDetailsDisplayForHostelReAdmission";
	public static final String TEMPLATE_SUBJECT_NAME="[SUBJECTNAMES]";
	public static final String INIT_EXAM_ROOM_ALLOTMENT_FOR_CJC = "initExamRoomAllotmentForCJC";
	public static final String EXAM_ROOM_ALLOTMENT_FOR_CJC_SECOND_PAGE = "examRoomAllotmentForCJCSecondPage";
	public static final String INIT_MID_SEM_REPEAT_SETTING = "initExamMidSemRepeatSetting";
	public static final String REPEAT_OFFLINE_PAYMENT="offlinePayment";
	public static final String MID_SEM_REPEAT_EXAM_FEEPAYMENT_EXEMPTION_MAIL= "Mail For Mid Semester Repeat Exam Fee Exemption";
	public static final String MID_SEM_REPEAT_EXAM_FEEPAYMENT_EXEMPTION_SMS= "SMS For Mid Semester Repeat Exam Fee Exemption";
	public static final String Upload_Except_CANDIDATE_REF_NOS= "knowledgepro.admission.except.reg.numbers";
	public static final String UPLOAD_SETTLEMENT_EXCEPTION_XLS="knowledgepro.upload.settlement.exception";
	public static final String REGISTER_NO_REQUIRED="knowledgepro.admission.register.no.required";
	public static final String MIDSEMESTER_EXAM_REQUIRED="knowledgepro.mid.semester.exam";
	public static final String MID_SEM_REPEAT_EXAM_REJECTED_MAIL= "Mail For Repeat Mid Sem Exam Application Rejected";
	public static final String MID_SEM_REPEAT_EXAM_REJECTED_SMS= "SMS For Repeat Mid Sem Exam Application Rejected";
	public static final String INIT_HOSTEL_APPLICATION_NUMBER_ENTRY="hostelApplNo";
	public static final String UPLOAD_FINAL_MERIT_LIST_STATUS="knowledgepro.upload.final.merit.status";
	public static final String UPLOAD_FINAL_MERIT_LIST_REMARKS="knowledgepro.upload.final.merit.remarks";
	public static final String SEND_SMS_REMINDER_FEE_PAYMENT="initFeePaymentReminder";
	public static final String INIT_SERVICES_DOWN_TRACKER = "initServicesDownTracker";
	public static final String UPLOAD_FINAL_YEAR_STUDENT_PHOTOS = "uploadFinalYearStudentPhoto";
	public static final String INIT_FINAL_YEAR_STUDENT_PHOTO_DISPLAY = "initFinalYearStudentPhotoDisplay";
	public static final String INIT_ADM_SELECTION_CJC = "initAdmSelectionCjc";
	public static final String HOSTEL_READMISSION_SELECTION_SMS="Hostel Re-Admission Selection";
	public static final String FEE_PREVIOUS_AMOUNT_AVAILABLE = "knowledgepro.fee.previous.balance.available";
	public static final String FEE_PAYMENT_PAID_AMT_GREATER = "knowledgepro.fee.payment.paidamount.greater";
	public static String PROGRAM_TYPE_UG = "UG";
	public static String PROGRAM_TYPE_PG = "PG";
	public static final String FEE_ASSIGNMENT_COPY_COURSE = "feeAssignmentCopyCourse";
	public static final String PRG_TYPE="knowledgepro.admin.programtype.id";
	public static final String RELIGION_TYPE="knowledgepro.admin.religion.id";
	public static int RELIGION_CHRISTIAN_TYPE=3;

	public static final String DIOCESE_ENTRY="dioceseList";
	public static final String DIOCESE_ACTIVATE_FAILURE="Diocese activation fail";
	public static final String DIOCESE_ACTIVATE_SUCCESS="Diocese activation Successfully";
	public static final String DIOCESE_EXIST_REACTIVATE = "knowledgepro.admin.diocese.alreadyexist.reactivate";
	public static final String PARISH_ENTRY="parishList";
	public static final String PARISH_EXIST_REACTIVATE = "knowledgepro.admin.parish.alreadyexist.reactivate";
	public static final String PARISH_ACTIVATE_FAILURE="Parish activation fail";
	public static final String PARISH_ACTIVATE_SUCCESS="Parish activation Successfully";
	
	public static final String STUDENTBIODATA_PRINT_PAGE="biodataprint";
	
	public static final String DISTRICT_EXIST_REACTIVATE = "knowledgepro.admin.state.addfailure.alreadyexist.reactivate";
	public static final String DISTRICT_ACTIVATE_SUCCESS = "knowledgepro.state.activatesuccess";
	public static final String DISTRICT_ACTIVATE_FAILURE = "knowledgepro.state.activatefailure";
	public static final String DISTRICT_ENTRY = "districtEntry";

	public static final String ROLE_ID_PRINCIPLE="knowledgepro.principle.role.id";
	public static final String ROLE_ID_ADMIN="knowledgepro.admin.role.id";
	public static final String ROLE_ID_TEST="knowledgepro.test.role.id";
	
	public static final String EXAM_GRADEPOINTRANGE="GradePointDefinition";
	public static final String EXAM_GRADEPOINTRANGE_ADD="GradePointDefinitionAdd";
	
	public static final String EXAM_GRADEPOINT = "GradePointDefinition";
	public static final String EXAM_GRADEPOINT_ADD = "GradePointDefinitionAdd";
	
	
	
	public static final String ATTENDANCE_ACTIVITY_REQ = "knowledgepro.attendanceentry.activity.required";
	public static final String COCURRICULAR_ATTENDANCE_ENTRY = "cocurricularAttendanceEntry";
	public static final String COCURRICULAR_ATTENDANCE_ENTRY_SECOND = "cocurricularAttendanceEntrySecondPage";
	public static final String ASSIGN_COCURRICULAR_SUBJECT_TO_TEACHER_ENTRY ="assignCocurricularSubjectTeacherEntry";
	public static final String EXTRA_COCURRICULAR_ATTENDANCE_ENTRY ="extraCocurricularAttendaceEntry";
	public static final String EXTRA_COCURRICULAR_ATTENDANCE_ENTRY_SUCCES = "extraCocurricularAttendaceEntryRedirect";
	public static final String APPORVE_COCURRICULAR_ATTENDACE_ENTRY = "approveCocurricularLeaveEntry";
	public static final String APPORVE_COCURRICULAR_ATTENDACE_ENTRY_SECOND = "approveCocurricularLeaveEntrySecondPage";
	public static final String AUTHORIZE_COCURRICULAR_ATTENDACE_ENTRY = "authorizeCocurricularApplications";
	public static final String AUTHORIZE_COCURRICULAR_ATTENDACE_ENTRY_SECOND = "authorizeCocurricularApplicationsEntrySecondPage";
	public static final String EXTRA_COCURRICULAR_ATTENDANCE_REDIRECT = "extraCocurricularAttendaceEntryRedirectToHome";
	public static final String MODIFY_ACTIVITY_ATTENDANCE_SECONDPAGE= "modifyActivityAttendanceSecond";
	public static final String ACTIVITY_ATTENDANCE="activityAttendanceEntry";
	public static final String TRANSACTION_FAILED="knowledgepro.admin.template.transaction.failed";
	


	public static final String ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FOR12TH = "onlineReviewDetailMarkPagefor12th";
	public static final String ADMISSIONFORM_COMM_DISTRICT_REQUIRED = "admissionForm.CommAddress.District.Required";
	public static final String ADMISSIONFORM_RANK_CONFIRM_PAGE_NEW = "rankconfirmPageNew";
	public static final String ADMISSIONFORM_INDEXMARK_CONFIRM_PAGE_NEW = "indexMarkconfirmPageNew";
	public static final String ADMISSIONFORM_COURSEALLOTMENT_CONFIRM_PAGE_NEW = "courseAllotmentconfirmPageNew";
	public static final String ADMISSIONFORM_ASSIGNCOURSEALLOTMENT_CONFIRM_PAGE_NEW = "courseAssignmentconfirmPageNew";
	public static final String ADMISSIONFORM_INDEXMARK = "initIndexMark";
	public static final String ADMISSIONFORM_RANK = "initStudentRank";
	public static final String ADMISSIONFORM_COURSE_ALLOTEMENT = "initCourseAllotement";
	public static final String ADMISSIONFORM_ASSIGNCOURSE_ALLOTEMENT = "initAssignCourseAllotement";
	public static final String ADMISSIONFORM_PREFERENCE_DETAILS_PAGE="preferencedetails";
	public static final String ADMISSIONFORM_PREFERENCE_PAGE="preferencedetails";
	public static final String SUBJECT_FOR_RANK="admsubject";
	public static final String ADMISSIONFORM_COURSEALLOTMENT_MULTIPLE_PAGE = "courseAllotmentMultiplePage";
	public static final String ADMISSIONFORM_ASSIGNCOURSE_ALLOTEMENT_MULTIPLE = "initAssignCourseAllotementMultiple";
	public static final String ADMISSIONFORM_ASSIGNSTUDENTS_EXAMALLOTEMENT = "initAssignStudentsToEntranceExam";
	public static final String ADMISSIONFORM_ASSIGNSTUDENTS_EXAMALLOTEMENT_CONFIRM_PAGE_NEW = "assignStudentsToEntranceExamconfirmPageNew";
	public static final String ADMISSIONFORM_ASSIGNSTUDENTS_EXAMMARKSALLOTEMENT = "initAssignStudentsToEntranceExamMarks";
	public static final String ADMISSIONFORM_ASSIGNSTUDENTS_EXAMMARKSALLOTEMENT_CONFIRM_PAGE_NEW = "assignStudentsToEntranceExamMarksconfirmPageNew";
	public static final String ADMISSIONFORM_EDITPREFERENCE_PAGE="editpreferencedetails";
	public static final String ADMISSIONFORM_DETAILMARK_VALID = "admissionFormForm.education.detailMark.valid";
	public static final String ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FOR12TH_FOREDIT="onlineReviewDetailMarkPagefor12thforedit";
		
			


	// rank
	public static final String INIT_UPLOAD_DDSTATUS = "initUploadDDStatus";
	public static final String INIT_DD_STATUS = "initDDStatus";
	public static final String RECIEVED_DD_NO="[Recieved_DD_No]";
	public static final String RECIEVED_DD_DATE="[Recieved_DD_Date]";
	public static final String ACCEPTANCE_MAIL_TEMPLATE = "Acceptance Mail";
	public static final String INIT_CHALLAN_STATUS = "initChallanStatus";
	public static final String RECIEVED_CHALLAN_NO="[Recieved_Challan_No]";
	public static final String RECIEVED_CHALLAN_DATE="[Recieved_Challan_Date]";
	public static final String ADMISSIONFORM_PARISHANDDIOESE_REQUIRED="parish.diocese.required";
	public static final String ADMISSIONFORM_RELIGIONSECTION_REQUIRED = "admissionFormForm.castCategory.required";
	public static final String NEW_ERROR_PAGE = "newerrorpage";
	public static final String ADMISSIONFORM_COURSE_INVALID="admissionFormForm.course.invalid";
	public static final String ADMISSIONFORM_COURSEPREF_DUP_INVALID="admissionFormForm.coursepref.dup.invalid";
	public static final String ADMISSIONFORM_COURSE_EMPTY_INVALID="admissionFormForm.courseempty.dup.invalid";
	public static final String ADMISSIONFORM_COURSE_PREFSIZE_INVALID="admissionFormForm.courseprefsize.exceeds";
	public static final String ADMISSIONFORM_PERMDISTRICT_REQUIRED = "admissionFormForm.permAddr.districtId.required";
	public static final String ADMISSIONFORM_CURRDISTRICT_REQUIRED = "admissionFormForm.currAddr.districtId.required";	
	public static final String ADMISSIONFORM_PARENT_ADDRESS_REQUIRED="parent.address.required";	
	public static final String INIT_CHALLAN_STATUS_ONCOURSE = "initChallanStatusOnCourse";
	public static final String INIT_CHALLAN_STATUS_ONCOURSE_CONFIRM_PAGE_NEW = "initChallanStatusOnCourseconfirmPageNew";
	public static final String ADMISSIONFORM_PHOTO_UPLOAD = "admissionFormForm.attachment.maxPhotouUpload";
	public static final String VIEW_ALLOTMENT_MEMO = "viewallotmentmemo";
	public static final String INIT_DD_STATUS_ONCOURSE = "initDDStatusOnCourse";
	public static final String INIT_DD_STATUS_ONCOURSE_CONFIRM_PAGE_NEW = "initDDStatusOnCourseconfirmPageNew";
		
	public static final String ADMISSIONFORM_ONLINEAPPLY_PAGE_DATE = "onlineApplyPageDate";
	
	public static final String UGCOURSES_FORM = "UG Courses";
	public static final String UGCOURSES_ENTRY = "ugCoursesEntry";
	public static final String UG_COURSES_ENTRY = "ugCourses";
	public static final String UG_COURSES_EXIST_REACTIVATE = "knowledgepro.ug.admitted.addfailure.alreadyexist.reactivate";
	public static final String UG_COURSES_ACTIVATE_SUCCESS = "knowledgepro.ug.courses.activatesuccess";
	public static final String UG_COURSES_ACTIVATE_FAILURE = "knowledgepro.ug.courses.activatefailure";
	
	public static final String ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FORDEG="onlineReviewDetailMarkPagefordeg";
	public static final String OPENCOURSE_FOR_RANK="Pgadmsubject";
	public static final String INIT_EXPORT_IDCARD_PHOTOS="initeExportIDcardPhoto";
	public static final String ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FORUG_FOREDIT="onlineReviewDetailMarkPagefordegforEdit";
	
	public static final String ATTENDANCE_ENTRY_FIRST1 = "attendanceEntryFirst1";
	public static final String ATTENDANCE_ENTRY_THIRD1 = "attendanceEntryThird1";
	public static final String VIEW_ATTENDANCE_SECONDPAGE1 = "viewAttendanceSearch1";
	public static final String MODIFY_ATTENDANCE_SECONDPAGE1 = "modifyAttendanceSecond1";
	
	// new SMS
	public static final String NEW_ATTENDANCE_SMS = "newAttSms";
	public static final String NEW_ATTENDANCE_SMS_ABSENTEES = "newAttSmsAbsebtees";
	public static final String NEW_SMS_SEND_FAILED = "knowledgepro.new.attendance.new.sms.sending.failed";
	public static final String NEW_SMS_SEND_SUCCESS = "knowledgepro.new.attendance.new.sms.sending.success";
	public static final String SMS_ATTENDANCE_PERCENTAGE = "smsAttendancePercentageEntry";
	public static final String SMS_ATTENDANCE_PERCENTAGE_SECOND ="smsAttendancePercentageEntrySecond";
	public static final String SMS_EXAM_MARKS_TO_STUDENTS = "smsExamMarkstoStudentEntry";
	public static final String SMS_EXAM_MARKS_TO_STUDENTS_LIST = "smsExamMarkstoStudentEntrySecondPage";
	// bulk sms
	public static final String BULK_SMS_TO_STUDENTS_FIRST_PAGE = "bulkSmsTOStudentsFirstPage";
	public static final String BULK_SMS_TO_STUDENTS_SECOND_PAGE = "bulkSmsTOStudentsSecondPage";
	// bulk sms employee
	public static final String BULK_SMS_TO_EMPLOYEE = "bulkSMStoEmployeeFirstPage";
	public static final String BULK_SMS_TO_EMPLOYEE_SECOND_PAGE ="bulkSMStoEmployeeSecondPage";

	// Grade Class Definition For Exam
	public static final String GRADE_CLASS_DEFINITION_ENTRY = "gradeClassDefinitionEntry";
	
	// Attendance Marks Entry for Exam
	public static final String ATTENDACE_MARK_ENTRY_FOR_EXAM = "attendanceMarkEntry";

	// Conduct Certificate
	public static final String PRINT_CONDUCT_CERTIFICATE = "printConductCertificate";
	public static final String CONDUCT_CERTIFICATE = "conductCertificate";
	// Faculty Evaluation Report
	public static final String FACULTY_EVALUATION_REPORT = "facultyEvaluationReport";
	public static final String FACULTY_EVALUATION_REPORT_PRINT = "printFacultyEvaluationReport";
	
	// Faculty Evaluation Report Principal
	public static final String FACULTY_EVALUATION_REPORT_PRINCIPAL = "facultyEvaluationReportPrincipal";
	public static final String FACULTY_EVALUATION_REPORT_PRINCIPAL_PRINT = "printFacultyEvaluationReportPrincipal";
	
	// Evaluate Faculty Feedback
	public static final String EVALUATE_FACULTY_FEEDBACK = "evaluateFacultyFeedback";
	
	public static final String ADMISSIONFORM_STUDENTMEMOPRINT_PAGE = "studentmemoPrintPage";
	
	//for regular/supp raghu
	public static final String INIT_SUPPL_IMP_APP_STUDENT_VEIRFY_SMART_2 = "printLinkSupply";
	public static final String INIT_SUPPL_IMP_APP_ALL = "initSupplementaryImprovementApplicationForAll";
	public static final String INIT_REGULAR_APP_STUDENT_RESULTUG = "initRegularAppStudentResultUG";
	public static final String INIT_REGULAR_APP_STUDENT_RESULTPG = "initRegularAppStudentResultPG";
	
	//mobile sms criteria
	public static final String ATTENDANCE_MOBILE_CRITERIA="mobileSmsCriteria";
	public static final String ATTENDANCE_TIME_RESTRICTED="knowledgepro.attendanceentry.access.restriction";	
	
	//test key
	//public static final String PGI_MERCHANT_ID = "gtKFFx";
	//test salt
	//public static final String PGI_SECURITY_ID = "eCwWELxi";
	//public static final String PGI_CHECKSUM_KEY = "eCwWELxi";
	//SelfFinancing key
	public static final String PGI_MERCHANT_ID = "lrTJ5a";
	//SelfFinancing salt
	public static final String PGI_SECURITY_ID = "yT1Fbmcx";
	public static final String PGI_CHECKSUM_KEY = "yT1Fbmcx";
	
	//Aided key
	public static final String PGI_MERCHANT_ID_AIDED = "HumZ1q";//HumZ1q
	//Aided salt
	public static final String PGI_SECURITY_ID_AIDED = "oybzjhAw";//oybzjhAw
	public static final String PGI_CHECKSUM_KEY_AIDED = "oybzjhAw";//oybzjhAw

	public static final String ADMISSIONFORM_TRANSACTIONDATE_INVALID = "admissionFormForm.transactiondate.invalid";
	
	
	
	//original key
	public static final String PGI_MERCHANT_ID_FEDERAL = "BMCSL";
	//original salt
	public static final String PGI_SECURITY_ID_FEDERAL = "BMCS1469#";
	//original key
	public static final String PGI_CHECKSUM_KEY_FEDERAL = "B184M9847C987S6L";
	
	
	//original key for mba
	public static final String PGI_MERCHANT_ID_FEDERAL_MBA = "BMIML";
	//original salt
	public static final String PGI_SECURITY_ID_FEDERAL_MBA = "BMIM1469#";
	public static final String PGI_CHECKSUM_KEY_FEDERAL_MBA = "B11M98I45M65826L";
	
	//admission work start here

	public static String ORGANISATION_COLLEGE_NAME = "St. Teresa's College";
	public static byte[] LOGIN_LOGO1;
	public static byte[] TOP_BAR;
	
	public static final String SMS_PROPERTIES = "resources/sms.properties";
	public static final String ONLINE_APPLICATION_CHALLAN_PAGENEW = "OnlineForwardChallanTemplateNew";
	public static final String PGI_MERCHANT_ID_REQUIRED = "admissionFormForm.merchant.id.required";
	public static final String PGI_SECURITY_ID_REQUIRED = "admissionFormForm.security.id.required";
	public static final String PGI_CHECKSUM_KEY_REQUIRED = "admissionFormForm.checksum.key.required";
	
	public static final String ADMISSIONFORM_PERCENTAGE_REQUIRED = "admissionFormForm.education.percentage.required";
	public static final String ADMISSIONFORM_PERCENTAGE_NOTINTEGER = "admissionFormForm.education.percentage.notNumeric";
	public static final String APP_FEE = "appFee";
	public static final String EDUC_STREAM = "educstream";
	public static final String CHALLAN_UPLOAD = "challan_upload";
	public static final String INIT_CHALLAN_UPLOAD_PROCESS = "initChallanUploadProcess";
	
	public static final String ONLINE_APPLICATION_CHALLAN_TIEUPBANK = "OnlineForwardChallanTemplateTieupBank";
	public static final String ONLINE_APPLICATION_CHALLAN_OTHERBANK = "OnlineForwardChallanTemplateOtherBank";
	public static final String ADMISSIONFORM_COMM_Street_REQUIRED = "admissionForm.CommAddress.Street.Required";
	public static final String ADMISSIONFORM_PERM__Street_REQUIRED="admissionFormForm.permAddr.Street.required";
	public static final String ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FOR12TH_FOREDIT_NEW="onlineReviewDetailMarkPagefor12thforeditNew";
	public static final String ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE_FORUG_FOREDIT_NEW="onlineReviewDetailMarkPagefordegforEditNew";
	public static final String UPDATE_COURSEWISE_CCPA = "updateCoursewiseCCPA";
	
	// vinodha
	public static final String PRINT_ALLOTMENT_MEMO = "printallotmentmemo";
	public static final String INIT_PRINT_ALLOTMENT_MEMO = "initPrintallotmentmemo";	
	public static final String ADMISSIONFORM_ADMYEAR_REQUIRED = "admissionFormForm.admissionYear.required";
	public static final String INIT_ENTER_OTP = "initEnterOtp";
	
	//basim
	public static final String INIT_CONSOLIDATE_MARKS_SI_NO = "initconsolidateSINO";
	public static final String INIT_CONSOLIDATE_MARKS_CARD_GENERATE = "consolidatemarksCardGen";
	public static final String ALREADY_AVAILABLE = "knowledgepro.admin.markscard.no.available";
	public static final String ENTER_ONLY_NUMERIC = "knowledgepro.admin.markscard.enter.numeric";
	public static final String INIT_MARKS_CARD_GENERATE = "marksCardGen";
	public static final String MARKS_CARD_GENERATED_ALREADY = "knowledgepro.admin.markscard.no.generate.already";
	public static final String MARKSCARD_STUDENT_NOT_PRESENT = "knowledgepro.exam.markscard.student.not.present";
	public static final String MARKSCARD_SI_NOT_AVAILABLE = "knowledgepro.admin.markscard.si.not.availabe";
	public static final String MARKS_CARD_GENERATED_ALREADY_REG = "knowledgepro.admin.markscard.no.generate.already.reg";
	public static final String INIT_SI_NO = "initSINO";
	public static final String MARKS_CARD_NOTGENERATED_REG = "knowledgepro.admin.markscard.no.notgenerate.reg";
	public static final String INIT_MARKS_CARD_GENERATE1 = "marksCardGen1";
	public static final String INIT_ASSIGN_GRACING = "initAssignGracing";
	public static final String INIT_PROCESS_GRACING = "initProcessGracing";
	public static final String STUDENT_LOGIN_INTERNAL_MARK = "initInternalMark";
	public static final String STUDENT_LOGIN_INTERNAL_MARK_RESULT = "internalMarkResult";
	public static final String INIT_ATTENDANCE_RESTRICTION_CLASS = "initAttendanceRestrictionClass";	
	public static final String FACULTY_EVALUATION_REPORT_CALCULATION = "facultyEvaluationReportCalculation";

	public static final String EXAM_SUBJECT_DEFINITION_COURSEWISE_COPY = "subjectDefinitionCourseWiseCopy";
	public static final String INIT_REGULAR_FEES = "initRegularFees";
	public static final String CHALLAN_UPLOAD_EXAM = "challan_upload_exam";
	
	public static final String INIT_CHALLAN_STATUS_ONCOURSE_FOR_EXAM = "initChallanStatusOnCourseForExam";
	public static final String LINK_FOR_REG_FEE_FINE = "knowledgepro.studentlogin.online.reg.exam.fine.fee";
	
	
	public static final String INIT_CHALLAN_STATUS_ONCOURSE_CONFIRM_PAGE_NEW_FOR_EXAM = "initChallanStatusOnCourseconfirmPageNewForExam";
	public static final String INIT_CHALLAN_UPLOAD_PROCESS_FOR_EXAM = "initChallanUploadProcessForExam";
	public static final String STUDENTS_IMPROVEMENT_EXAM_DETAILS = "studentImprovementExamDetails";
	public static final String INIT_REVALUATION_APP_STUDENT_RESULT = "initRevaluationAppStudentResult";
	public static final String INIT_REVALUATION_APP_STUDENT_RESULT_FIRST_PAGE = "initRevaluationAppStudentResultFirstPage";
	public static final String INIT_REVALUATION_FEES = "initRevaluationFees";
	public static final String CONSOLIDATED_SUBJECT_STREAM_MASTER = "initConsolidatedSubjectStream";
	public static final String INIT_REVALUATION_APP = "initRevaluationApplication";
	public static final String INIT_REVALUATION_APP_RESULT = "revaluationAppResult";
	public static final String INIT_REVALUATION_APP_STUDENT = "revaluationApplStudent";
	public static final String EXAM_REVALUATION_MARKS_ENTRY_INPUT = "initRevaluationMarksEntry";
	public static final String EXAM_REVALUATION_MARKS_ENTRY_RESULT = "examRevaluationMarksEntryResult";
	public static final String EXAM_REVALUATION_MARKS_ENTRY_PRINT = "examRevaluationMarksEntryPrint";

	public static final String FEE_PAYMENT_STUDENTS_SEARCH = "feePaymentStudentsSearch";
	public static final String CHALLAN_UPLOAD_FEE = "challan_upload_fee";
	public static final String INIT_CHALLAN_UPLOAD_PROCESS_FOR_FEE = "initChallanUploadProcessForFee";
	public static final String GRIEVANCE_MASTER ="grievanceMaster";
	public static final String GRIEVANCE_MASTER_ACTIVATE_SUCCESS = "knowledgepro.admin.grievancemaster.activate.success";
	public static final String GRIEVANCE_MASTER_EXIST_REACTIVATE = "knowledgepro.admin.grievancemaster.alreadyexist.reactivate";
	public static final String GRIEVANCE_MASTER_ACTIVATE_FAILURE = "knowledgepro.admin.grievancemaster.activatefailure";
	
	public static final String STUDENT_LOGIN_GRIEVANCE_STATUS = "initGrievanceStatus";
	public static final String INIT_GRIEVANCE_STUDENT = "initStudentGrievance";
	public static final String GRIEVANCE_STUDENT_LIST = "studentGrievanceList";
	public static final String GRIEVANCE_STUDENT_RESULT = "studentGrievanceResult";
	public static final String KNOWLEDGEPRO_GRIEVANCE_NORECORDSFOUND = "knowledgepro.grievance.norecordsfound";
	
	public static final String STUDENT_LOGIN_GRIEVANCE_STATUS_PRINT = "printGrievanceForm";
	public static final String KNOWLEDGEPRO_ADMISSION_GRIEVANCE_ADDED_SUCCESSFULLY = "knowledgepro.admin.grievancemaster.added.success";
	public static final String FEE_CATEGORY = "initFeeCategory";	
	public static final String KNOWLEDGEPRO_ADMISSION_GRIEVANCE_ADDED_FAILED = "knowledgepro.admin.grievancemaster.addfailure";
	
	public static final String CERTIFICATE_MARKS_CARD_NEW = "certificateMarksCardnew";
	public static final String CERTIFICATE_MARKS_CARD_RESULT = "certificateMarksCardResult";
	
	public static final String CERTIFICATE_MARKS_CARD_RESULT_PROGRAM_PART = "certificateMarksCardResultProgramPart";
	public static final String PRINT_PROGRAMPART_RESULT_CERTIFICATE_MARKS_CARD = "printProgramPartResult";
	public static final String PRINT_SEMESTER_RESULT_MARKS_CARD = "printSemesterResult";
	public static final String SEND_BULK_SMS = "sendBulkSmsToStudentsParents";
	public static final String KNOWLEDGEPRO_SENDER_FOOTER ="klowledgepro.sms.footer";
	public static int CASTE_RCSC_TYPE=160;
	public static int CATEGORY_RCSC_TYPE=11;

	public static final String STUDENT_LOGIN_INTERNAL_MARK_GRIEVANCE = "initInternalMarkGrievance";
	public static final String STUDENT_LOGIN_INTERNAL_MARK_RESULT_GRIEVANCE = "internalMarkResultGrievance";
	
	public static final String STUDENT_LOGIN_MARKSCARD_GRIEVANCE = "initGrievanceMarksCard";
	
	public static final String TEACHER_FEEDBACK_GROUP="evaTeacherFeedBackGroup";
	public static final String TEACHER_FEEDBACK_QUESTION="teacherFeedBackQuestion";
	public static final String INIT_TEACHER_FEEDBACK_INST = "initTeacherFeedbackInst";
	public static final String INIT_EVAL_TEACHER_FEEDBACK_SESSION = "intEvaluationTeacherFeedbackSession";
	public static final String TEACHER_FEEDBACK_OPEN_CONNECTION = "teacherFeedbackOpenConnection";
	public static final String INIT_EVAL_TEACHER_FEEDBACK = "initEvaluationTeacherFeeback";
	public static final String INIT_EVAL_TEACHER_FEEDBACK_INSTS = "initEvaluationTeacherFeebackInstructions";
	public static final String EVAL_TEACHER_FEEDBACK_STUDENTS = "teacherEvaluationFeedbackStudents";
	public static final String START_STUDENTS_EVAL_FEEDBACK = "startStudentsEvaluationFeedback";
	public static final String ALREADY_EXIST_OR_SUBMITTED_SUCCESSFULLY_TEACHERS_EVALUATION = "initPeersEvaAlreadyExistOrSubmitSuccessfully";
	
	public static final String DEPARTMENT_NAME_ENTRY = "departmentNameEntry";
	public static final String PROGRAMME_NAME_ENTRY = "programmeEntry";
	
	public static final String STUDENT_LOGIN_SERVICE_LEARNING_ACTIVITY = "initServiceLearningActivity";
	public static final String SERVICE_LEARNING_MARKS_ENTRY = "initServiceLearningMarksEntry";
	public static final String SERVICE_LEARNING_MARKS_ENTRY_RESULT = "serviceLearningMarksEntryResult";
	public static final String SERVICE_LEARNING_MARKS_ENTRY_OVERALL_RESULT = "serviceLearningMarksEntryOverallResult";
	public static final String PRINT_SERVICE_LEARNING_MARKS_ENTRY_OVERALL_RESULT = "printserviceLearningMarksEntryOverallResult";
	public static final String PRINT_SERVICE_LEARNING_MARKS_ENTRY_RESULT = "printserviceLearningMarksEntryResult";
	public static final String SHOW_SERVICE_LEARNING_MARKS_ENTRY_OVERALL_RESULT = "showServiceLearningMarksEntryOverallResult";
	public static final String STUDENT_LOGIN_SERVICE_LEARNING_RECORD = "serviceLearningRecord";
	public static final String PRINT_STUDENT_LOGIN_SERVICE_LEARNING_RECORD = "printserviceLearningRecord";
	public static final String SERVICE_LEARNING_MARKS_ENTRY_OVERALL_RESULT_ADMIN = "serviceLearningMarksEntryAdminResult";
	public static final String SERVICE_LEARNING_MARKS_ENTRY_OVERALL_RESULT_ADMIN1 = "serviceLearningMarksEntryOverallResult1";
	public static final String INIT_INTERNAL_REDO_FEES = "initInternalRedoFees";
	public static final String STUDENT_INTERNAL_REDO_APP_PAGE = "initInternalRedoImpAppStudentResult";
	public static final String STUDENT_INTERNAL_REDO_SUBMIT = "printLinkIntRedo";
	public static final String INIT_EVAL_TEACHER_FEEDBACK_REPORT = "initEvaluationTeacherFeebackReport";
	public static final String EVAL_TEACHER_FEEDBACK_REPORT = "evaluationTeacherFeedackReport";
	public static final String EVAL_TEACHER_FEEDBACK__COMAPARISION_REPORT = "evaluationTeacherFeedackComparisionReport";
	
	public static final String ACTIVITY_TYPE_ENTRY="activityType";
	
	public static final String ACTIVITY_LEARNING="activityLearning";
	public static final String PROGRAMME_NAME_ENTRY_INTERN_WORK = "programmeEntryForWork";
	public static final String EXIT_EVAL_TOPIC = "exitevaltopic";
	public static final String EXIT_EVAL_QUES = "exitevalques";
	
	// validation keys for Exit Evaluation Question

	public static final String EXIT_EVAL_QUES_ADDSUCCESS = "knowledgepro.exitevalques.record.added";
	public static final String EXIT_EVAL_QUES_ADDFAILURE = "knowledgepro.exitevalques.record.addedfailure";
	public static final String EXIT_EVAL_QUES_NAME_EXIST = "knowledgepro.exitevalques.feesNameExist";
	public static final String EXIT_EVAL_QUES_REACTIVATE = "knowledgepro.exitevalques.feesName.alreadyexist.reactivate";
	public static final String EXIT_EVAL_QUES_DELETESUCCESS = "knowledgepro.exitevalques.record.deleted";
	public static final String EXIT_EVAL_QUES_DELETEFAILURE = "knowledgepro.exitevalques.record.deletedfailure";
	public static final String EXIT_EVAL_QUES_UPDATESUCCESS = "knowledgepro.exitevalques.record.updated";
	public static final String EXIT_EVAL_QUES_UPDATEFAILURE = "knowledgepro.exitevalques.record.updatedfailure";
	public static final String EXIT_EVAL_QUES_REACTIVATESUCCESS = "knowledgepro.exitevalques.record.record.activated";
	public static final String INDIVIDUAL_EMPLOYEE_INFO_EDIT= "IndividualEmployeeInfoEditDetails";
	public static final String ATTENDANCE_ENTRY_DAY = "attendanceEntryDay";
	public static final String PERSONAL_DATA_EDIT= "personalDataEdit";
	public static final String PERSONAL_DATA_UPDATED= "personalDataUpdated";
	public static final String PERSONAL_DATA_PHOTO = "admissionFormForm.attachment.photoupload";
	public static final String INIT_PUBLISH_EDIT="initpublishedit";
	public static final String STUDENT_DETAILS_PHOTO_EDIT="studenteditphoto";
	public static final String EDIT_STUDENT_PHOTO="editstudentphoto";
	
	public static final String HOLIDAY_DATA="holiday";
	
	public static final String STUDENT_TIME_TABLE="studenttimetable";
	public static final String INIT_MBA_EXAMS="mbaExamsInit";
	public static final String EMPLOYEE_EDITLISTPAGE_NEW = "employeeEditListNew";
	public static final String PERSONAL_DATA_VIEW= "personalDataView";
	public static final String EMPLOYEE_INFO_EDIT_NEW = "employeeInfoEditNew";
	public static final String EMP_INFO_SUBMIT_CONFIRM_NEW = "empInfoSubmitConfirmNew";
	public static final String INDIVIDUAL_EMPLOYEE_INFO_EDIT_NEW = "individualEmployeeInfoEditNew";
	public static final String CLASSWISE_ATTENDANCE_STATUS="initClassWiseAttendanceStatus";
	public static final String EVAL_STU_SUBWISE_FEEDBACK="facultyEvaluationSubjectWiseAnsweringCheck";
	public static final String EVAL_STU_SUBWISE_FEEDBACK_DETAILS="getSubjectDetails";
	public static final String UPLOAD_UNIVERSITY_REG_NO = "uploadUniversityRegNo";
	public static final String PAYMENT_STUDENT_LOGIN = "paymentDetailsStudent";
	public static final String REDIRECT_TO_PGI_PAGE_STUDENT_LOGIN = "redirectToPgiStudentLogin";
	public static final String SEMESTER_WISE_COURSE_ENTRY = "semesterWiseCourseEntry";
	public static final String REDIRECT_TO_PGI_PAGE_STUDENT_LOGIN_MBA = "redirectToPgiStudentLoginMba";	
	public static final String INIT_UPDATE_STUDENT_DATA = "initUpdateStudentData";
	public static final String INIT_UPDATE_STUDENT_DATA_RESULTS = "initUpdateStudentDataResults";
	public static final String FEE_PAYMENT_PAID_AMT_SMALLER = "knowledgepro.fee.payment.paidamount.smaller";
	
	public static final String REDIRECT_TO_PGI_PAGE_FOR_PROFESSOR = "redirectToPgiProfessor";
	public static final String REDIRECT_TO_PGI_PAGE_PROFESSOER_APPLICATION="redirectToPgiProfessorApplication";
	
	public static final String INIT_CC_DETAILS = "initCCDetails";
	public static final String CC_DETAILS_RESULT = "ccDetailsResult";
	public static final String CC_DETAILS = "ccDetailsByStudent";
	public static final String PRINT_CC = "printcc";
	
	//test salt key for wls
	/*public static final String PGI_WLS_MERCHANT_ID = "WL0000000027698";
	public static final String PGI_WLS_ENCRIPTION_KEY = "6375b97b954b37f956966977e5753ee6";*/
	
	//production salt key for wls
	public static final String PGI_WLS_MERCHANT_ID = "WL0000000009188";
	public static final String PGI_WLS_ENCRIPTION_KEY = "54bf42cb4a94501d1f7dee47585ca642";
	public static final String ADMISSIONFORM_PENDING_ONLINE_APP = "initPendingOnlineApp";
	public static final String EXAM_MARKS_ENTRY_INPUT_INTERNAL_ALL = "initMarksEntryInternalAll";
	public static final String EXAM_MARKS_ENTRY_RESULT_INTERNAL_ALL = "examMarksEntryResultInternalAll";
	public static String STUDENT_DISCIPLINE_INITEDITPAGE="studentdisciplineInit";
	public static String STUDENT_DISCIPLINE_EDIT_PAGE="studentDisciplineEditPage";
	public static final String STUDENT_DISCIPLINE_EDITLISTPAGE = "studentDisciplineListPage";
	public static final String DISCIPLINE_AND_ACHIEVEMENT= "studentDisciplineView";
	public static final String INIT_ADMIN_PROGRESS_CARD = "initAdminProgrsCard";
	public static final String PROGRESS_CARD_STUDENTS_SEARCH = "progressCardStudentsSearch";
	public static final String ADMIN_PROGRESS_CARD = "adminProgressCard";
	public static final String SLOGIN_PROGRESS_CARD = "sLoginProgressCard";
	public static final String SLOGIN_PRINT_PROGRESS_CARD = "sLoginPrintProgressCard";
}