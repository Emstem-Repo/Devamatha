package com.kp.cms.helpers.admission;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kp.cms.actions.admin.StudentUploadPhotoAction;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.AdmapplnAdditionalInfo;
import com.kp.cms.bo.admin.ApplicantWorkExperience;
import com.kp.cms.bo.admin.CandidatePreference;
import com.kp.cms.bo.admin.CandidatePrerequisiteMarks;
import com.kp.cms.bo.admin.ConfigReportsColumn;
import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewResult;
import com.kp.cms.bo.admin.InterviewResultDetail;
import com.kp.cms.bo.admin.InterviewSubRounds;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.bo.admin.StudentSpecializationPrefered;
import com.kp.cms.bo.exam.ExamStudentDetentionRejoinDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.CandidateSearchForm;
import com.kp.cms.to.admission.CandidateSearchTO;
import com.kp.cms.to.admission.InterviewResultReportTO;
import com.kp.cms.to.reports.ConfigureColumnForReportTO;
import com.kp.cms.transactions.admission.ICandidateSearchTxnImpl;
import com.kp.cms.transactions.admission.IInterviewDefinitionTransaction;
import com.kp.cms.transactionsimpl.admission.CandidateSearchTxnImpl;
import com.kp.cms.transactionsimpl.admission.InterviewDefinitionTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.EncryptUtil;

/**
 * @author kalyan.c
 * This is Helper class for Admission Report
 */
@SuppressWarnings("deprecation")

public class CandidateHelper {
	
//	modifications done by priyatham	
	private static final Log log = LogFactory.getLog(CandidateHelper.class);
	
	private static Map<Integer, String> monthMap = null;
	
	static {
		monthMap = new HashMap<Integer, String>();
		monthMap.put(1, "JANUARY");
		monthMap.put(2, "FEBRUARY");
		monthMap.put(3, "MARCH");
		monthMap.put(4, "APRIL");
		monthMap.put(5, "MAY");
		monthMap.put(6, "JUNE");
		monthMap.put(7, "JULY");
		monthMap.put(8, "AUGUST");
		monthMap.put(9, "SEPTEMBER");
		monthMap.put(10, "OCTOBER");
		monthMap.put(11, "NOVEMBER");
		monthMap.put(12, "DECEMBER");
		
	}
	
	
	private static final String DISPLAY = "display";
	public static void exportTOExcel(List candidateSearchTOList,CandidateSearchTO candidateSearchTO ,HttpServletRequest request) throws Exception{
		// File test = null;
		int interviewResultDispCount=0;
		int interviewResultCommentDispCount=0;
		int preReqDispCount=0;
		int prvExamDetDispCounter=0;
		int dateTimeDispCount=0;
		Properties prop = new Properties();
		try {
			InputStream inputStream = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inputStream);
		} 
		catch (IOException e) {
			log.error("Error occured at exportTOExcel of CandidateHelper ",e);
			throw new IOException(e);
		}
		String fileName=prop.getProperty(CMSConstants.EXCEL_DESTINATION);
		File excelFile = new File(request.getRealPath("")+ "//TempFiles//"+fileName);
		if(excelFile.exists()){
			excelFile.delete();
		}
//		String contentType = response.getContentType();
//		String fileName = "AdmissionReport.xls";
		FileOutputStream fos = null;
		ByteArrayOutputStream bos=null;
		/*HSSFWorkbook wb = null;
		HSSFSheet sheet =null;
		HSSFRow row = null;
		HSSFCell cell = null;*/
		
//		changed HSSFWorkbook to XSSFWorkbook by priyatham --start--
		XSSFWorkbook wb=null;
		XSSFSheet sheet=null;
		XSSFRow row=null;
		XSSFCell cell=null;
//		--end--
		if(candidateSearchTOList!=null){
			
			
		int count = 0;
		Iterator iterator = candidateSearchTOList.iterator();
		
		try	{
//			wb = new HSSFWorkbook();
			wb=new XSSFWorkbook();
//			HSSFCellStyle cellStyle = wb.createCellStyle();
			XSSFCellStyle cellStyle=wb.createCellStyle();
			CreationHelper createHelper = wb.getCreationHelper();

//			cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("dd/MM/yyyy"));
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

			sheet = wb.createSheet("Admission Report");
			row = sheet.createRow(count);
			count = sheet.getFirstRowNum();
			// Create cells in the row and put some data in it.
			if(candidateSearchTO.getRegNoDisp() != null && candidateSearchTO.getRegNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getRegNoPois()).setCellValue(CMSConstants.REGNO);
			}
			if(candidateSearchTO.getRollNoDisp() != null && candidateSearchTO.getRollNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getRollNoPois()).setCellValue(CMSConstants.ROLLNO);
			}
			if(candidateSearchTO.getClassNameDisp() != null && candidateSearchTO.getClassNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getClassNamePois()).setCellValue(CMSConstants.CLASS_NAME);
			}
			if(candidateSearchTO.getAdmissionDateDisp() != null && candidateSearchTO.getAdmissionDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getAdmissionDatePois()).setCellValue(CMSConstants.ADMISSION_DATE);
			}
			if(candidateSearchTO.getSecondLanguageDisp() != null && candidateSearchTO.getSecondLanguageDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getSecondLanguagePois()).setCellValue(CMSConstants.SECOND_LANGUAG);
			}
			if(candidateSearchTO.getProgramTypeNameDisp() != null && candidateSearchTO.getProgramTypeNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getProgramTypeNamePois()).setCellValue(CMSConstants.PROGTYPE_NAME);
			}
			if(candidateSearchTO.getProgramNameDisp() != null && candidateSearchTO.getProgramNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getProgramNamePois()).setCellValue(CMSConstants.PROG_NAME);
			}
			if(candidateSearchTO.getCourseNameDisp() != null && candidateSearchTO.getCourseNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getCourseNamePois()).setCellValue(CMSConstants.COURSE_NAME);
			}
			if(candidateSearchTO.getJournalNoDisp() != null && candidateSearchTO.getJournalNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getJournalNoPois()).setCellValue(CMSConstants.JOURNAL_NO);
			}
			if(candidateSearchTO.getAcedamicYearDisp() != null && candidateSearchTO.getAcedamicYearDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getAcedamicYearPois()).setCellValue(CMSConstants.ACEDAMIC_YEAR);
			}
			if(candidateSearchTO.getAdmittedThroughDisp() != null && candidateSearchTO.getAdmittedThroughDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getAdmittedThroughPois()).setCellValue(CMSConstants.ADMITTED_THROUG);
			}
			if(candidateSearchTO.getResidentCategoryDisp() != null && candidateSearchTO.getResidentCategoryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getResidentCategoryPois()).setCellValue(CMSConstants.RESIDENT_CATEGORY);
			}
			if(candidateSearchTO.getReligionDisp() != null && candidateSearchTO.getReligionDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getReligionPois()).setCellValue(CMSConstants.RELIGIN);
			}
			if(candidateSearchTO.getReligionOtherDisp() != null && candidateSearchTO.getReligionOtherDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getReligionOtherPois()).setCellValue(CMSConstants.RELIGION_OTHER);
			}
			if(candidateSearchTO.getSubReligionDisp() != null && candidateSearchTO.getSubReligionDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getSubReligionPois()).setCellValue(CMSConstants.SUB_RELIGION);
			}
			if(candidateSearchTO.getSubReligionOtherDisp() != null && candidateSearchTO.getSubReligionOtherDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getSubReligionOtherPois()).setCellValue(CMSConstants.SUB_RELIGION_OTHER);
			}
			if(candidateSearchTO.getCastCategoryDisp() != null && candidateSearchTO.getCastCategoryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getCastCategoryPois()).setCellValue(CMSConstants.CAST_CATEGORY);
			}
			if(candidateSearchTO.getCastCategoryOtherDisp() != null && candidateSearchTO.getCastCategoryOtherDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getCastCategoryOtherPois()).setCellValue(CMSConstants.CAST_CATEGORY_OTHER);
			}
			if(candidateSearchTO.getPlaceOfBirthDisp() != null && candidateSearchTO.getPlaceOfBirthDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getPlaceOfBirthPois()).setCellValue(CMSConstants.PLACE_OF_BIRTH);
			}
			if(candidateSearchTO.getStateOfBirthDisp() != null && candidateSearchTO.getStateOfBirthDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getStateOfBirthPois()).setCellValue(CMSConstants.STATE_OF_BIRTH);
			}
			if(candidateSearchTO.getStateOfBirthOtherDisp() != null && candidateSearchTO.getStateOfBirthOtherDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getStateOfBirthOtherPois()).setCellValue(CMSConstants.STATE_OF_BIRTH_OTHER);
			}
			if(candidateSearchTO.getCountryOfBirthDisp() != null && candidateSearchTO.getCountryOfBirthDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getCountryOfBirthPois()).setCellValue(CMSConstants.COUNTRY_OF_BIRTH);
			}
			if(candidateSearchTO.getBelongsToDisp() != null && candidateSearchTO.getBelongsToDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getBelongsToPois()).setCellValue(CMSConstants.BELONGS_TO);
			}
			if(candidateSearchTO.getNationalityDisp() != null && candidateSearchTO.getNationalityDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getNationalityPois()).setCellValue(CMSConstants.NATIONALIT);
			}
			if(candidateSearchTO.getStudentPhoneNumberDisp() != null && candidateSearchTO.getStudentPhoneNumberDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getStudentPhoneNumberPois()).setCellValue(CMSConstants.STUDENT_PHONE_NUMBER);
			}
			if(candidateSearchTO.getStudentMobileNumberDisp() != null && candidateSearchTO.getStudentMobileNumberDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getStudentMobileNumberPois()).setCellValue(CMSConstants.STUDENT_MOBILE_NUMBER);
			}
			if(candidateSearchTO.getPassportNumberDisp() != null && candidateSearchTO.getPassportNumberDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getPassportNumberPois()).setCellValue(CMSConstants.PASSPORT_NUMBER);
			}
			if(candidateSearchTO.getPassportIssuingCountryDisp() != null && candidateSearchTO.getPassportIssuingCountryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getPassportIssuingCountryPois()).setCellValue(CMSConstants.PASSPORT_ISSUING_COUNTRY);
			}
			if(candidateSearchTO.getPassportValidUpToDisp() != null && candidateSearchTO.getPassportValidUpToDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getPassportValidUpToPois()).setCellValue(CMSConstants.PASSPORT_VALIDUPTO);
			}
			if(candidateSearchTO.getFirstPreferenceProgramTypeDisp() != null && candidateSearchTO.getFirstPreferenceProgramTypeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFirstPreferenceProgramTypePois()).setCellValue(CMSConstants.FIRST_PRE_PROGTYPE);
			}
			if(candidateSearchTO.getFirstPreferenceProgramDisp() != null && candidateSearchTO.getFirstPreferenceProgramDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFirstPreferenceProgramPois()).setCellValue(CMSConstants.FIRST_PRE_PROG);
			}
			if(candidateSearchTO.getFirstPreferenceCourseDisp() != null && candidateSearchTO.getFirstPreferenceCourseDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFirstPreferenceCoursePois()).setCellValue(CMSConstants.FIRST_PRE_COURSE);
			}
			if(candidateSearchTO.getSecondPreferenceProgramTypeDisp() != null && candidateSearchTO.getSecondPreferenceProgramTypeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getSecondPreferenceProgramTypePois()).setCellValue(CMSConstants.SECOND_PRE_PROGTYPE);
			}
			if(candidateSearchTO.getSecondPreferenceProgramDisp() != null && candidateSearchTO.getSecondPreferenceProgramDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getSecondPreferenceProgramPois()).setCellValue(CMSConstants.SECOND_PRE_PROG);
			}
			if(candidateSearchTO.getSecondPreferenceCourseDisp() != null && candidateSearchTO.getSecondPreferenceCourseDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getSecondPreferenceCoursePois()).setCellValue(CMSConstants.SECOND_PRE_COURSE);
			}
			if(candidateSearchTO.getThirdPreferenceProgramTypeDisp() != null && candidateSearchTO.getThirdPreferenceProgramTypeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getThirdPreferenceProgramTypePois()).setCellValue(CMSConstants.THIRD_PRE_PROGTYPE);
			}
			if(candidateSearchTO.getThirdPreferenceProgramDisp() != null && candidateSearchTO.getThirdPreferenceProgramDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getThirdPreferenceProgramPois()).setCellValue(CMSConstants.THIRD_PRE_PROG);
			}
			if(candidateSearchTO.getThirdPreferenceCourseDisp() != null && candidateSearchTO.getThirdPreferenceCourseDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getThirdPreferenceCoursePois()).setCellValue(CMSConstants.THIRD_PRE_COURSE);
			}
			if(candidateSearchTO.getFirstNameofOrganisationDisp() != null && candidateSearchTO.getFirstNameofOrganisationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFirstNameofOrganisationPois()).setCellValue(CMSConstants.FIRST_NAME_OF_ORG);
			}
			if(candidateSearchTO.getFirstDesignationDisp() != null && candidateSearchTO.getFirstDesignationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFirstDesignationPois()).setCellValue(CMSConstants.FIRST_DESIG);
			}
			if(candidateSearchTO.getFirstFromDateDisp() != null && candidateSearchTO.getFirstFromDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFirstFromDatePois()).setCellValue(CMSConstants.FIRST_FROM_DATE);
			}
			if(candidateSearchTO.getFirstToDateDisp() != null && candidateSearchTO.getFirstToDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFirstToDatePois()).setCellValue(CMSConstants.FIRST_TO_DATE);
			}
			if(candidateSearchTO.getSecondNameofOrganisationDisp() != null && candidateSearchTO.getSecondNameofOrganisationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getSecondNameofOrganisationPois()).setCellValue(CMSConstants.SECOND_NAME_OF_ORG);
			}
			if(candidateSearchTO.getSecondDesignationDisp() != null && candidateSearchTO.getSecondDesignationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getSecondDesignationPois()).setCellValue(CMSConstants.SECOND_DESIG);
			}
			if(candidateSearchTO.getSecondFromDateDisp() != null && candidateSearchTO.getSecondFromDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getSecondFromDatePois()).setCellValue(CMSConstants.SECOND_FROM_DATE);
			}
			if(candidateSearchTO.getSecondToDateDisp() != null && candidateSearchTO.getSecondToDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getSecondToDatePois()).setCellValue(CMSConstants.SECOND_TO_DATE);
			}
			if(candidateSearchTO.getThirdNameofOrganisationDisp() != null && candidateSearchTO.getThirdNameofOrganisationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getThirdNameofOrganisationPois()).setCellValue(CMSConstants.THIRD_NAME_OF_ORG);
			}
			if(candidateSearchTO.getThirdDesignationDisp() != null && candidateSearchTO.getThirdDesignationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getThirdDesignationPois()).setCellValue(CMSConstants.THIRD_DESIG);
			}
			if(candidateSearchTO.getThirdFromDateDisp() != null && candidateSearchTO.getThirdFromDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getThirdFromDatePois()).setCellValue(CMSConstants.THIRD_FROM_DATE);
			}
			if(candidateSearchTO.getThirdToDateDisp() != null && candidateSearchTO.getThirdToDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getThirdToDatePois()).setCellValue(CMSConstants.THIRD_TO_DATE);
			}
			if(candidateSearchTO.getPermanentAddressLine1Disp() != null && candidateSearchTO.getPermanentAddressLine1Disp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getPermanentAddressLine1Pois()).setCellValue(CMSConstants.PERMANENT_ADDR_LINE1);
			}
			if(candidateSearchTO.getPermanentAddressLine2Disp() != null && candidateSearchTO.getPermanentAddressLine2Disp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getPermanentAddressLine2Pois()).setCellValue(CMSConstants.PERMANENT_ADDR_LINE2);
			}
			if(candidateSearchTO.getPermanentStateDisp() != null && candidateSearchTO.getPermanentStateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getPermanentStatePois()).setCellValue(CMSConstants.PERMANENT_STATE);
			}
			if(candidateSearchTO.getPermanentStateOthersDisp() != null && candidateSearchTO.getPermanentStateOthersDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getPermanentStateOthersPois()).setCellValue(CMSConstants.PERMANENT_STATE_OTHERS);
			}
			if(candidateSearchTO.getPermanentCityDisp() != null && candidateSearchTO.getPermanentCityDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getPermanentCityPois()).setCellValue(CMSConstants.PERMANENT_CITY);
			}
			if(candidateSearchTO.getPermanentCountryDisp() != null && candidateSearchTO.getPermanentCountryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getPermanentCountryPois()).setCellValue(CMSConstants.PERMANENT_COUNTRY);
			}
			if(candidateSearchTO.getPermanentZipCodeDisp() != null && candidateSearchTO.getPermanentZipCodeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getPermanentZipCodePois()).setCellValue(CMSConstants.PERMANENT_ZIP_CODE);
			}
			if(candidateSearchTO.getCurrentAddressLine1Disp() != null && candidateSearchTO.getCurrentAddressLine1Disp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getCurrentAddressLine1Pois()).setCellValue(CMSConstants.CURRENT_ADDR_LINE1);
			}
			if(candidateSearchTO.getCurrentAddressLine2Disp() != null && candidateSearchTO.getCurrentAddressLine2Disp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getCurrentAddressLine2Pois()).setCellValue(CMSConstants.CURRENT_ADDR_LINE2);
			}
			if(candidateSearchTO.getCurrentStateDisp() != null && candidateSearchTO.getCurrentStateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getCurrentStatePois()).setCellValue(CMSConstants.CURRENT_STATE);
			}
			if(candidateSearchTO.getCurrentStateOthersDisp() != null && candidateSearchTO.getCurrentStateOthersDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getCurrentStateOthersPois()).setCellValue(CMSConstants.CURRENT_STATE_OTHERS);
			}
			if(candidateSearchTO.getCurrentCityDisp() != null && candidateSearchTO.getCurrentCityDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getCurrentCityPois()).setCellValue(CMSConstants.CURRENT_CITY);
			}
			if(candidateSearchTO.getCurrentCountryDisp() != null && candidateSearchTO.getCurrentCountryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getCurrentCountryPois()).setCellValue(CMSConstants.CURRENT_COUNTRY);
			}
			if(candidateSearchTO.getCurrentZipCodeDisp() != null && candidateSearchTO.getCurrentZipCodeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getCurrentZipCodePois()).setCellValue(CMSConstants.CURRENT_ZIP_CODE);
			}
			if(candidateSearchTO.getFathersNameDisp() != null && candidateSearchTO.getFathersNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFathersNamePois()).setCellValue(CMSConstants.FATHERS_NAME);
			}
			if(candidateSearchTO.getFathersEducationDisp() != null && candidateSearchTO.getFathersEducationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFathersEducationPois()).setCellValue(CMSConstants.FATHERS_EDUCATION);
			}
			if(candidateSearchTO.getFathersIncomeDisp() != null && candidateSearchTO.getFathersIncomeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFathersIncomePois()).setCellValue(CMSConstants.FATHERS_INCOME);
			}
			if(candidateSearchTO.getFathersCurrencyDisp() != null && candidateSearchTO.getFathersCurrencyDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFathersCurrencyPois()).setCellValue(CMSConstants.FATHERS_CURRENCY);
			}
			if(candidateSearchTO.getFathersOccupationDisp() != null && candidateSearchTO.getFathersOccupationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFathersOccupationPois()).setCellValue(CMSConstants.FATHERS_OCCUPATION);
			}
			if(candidateSearchTO.getFathersEmailDisp() != null && candidateSearchTO.getFathersEmailDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFathersEmailPois()).setCellValue(CMSConstants.FATHERS_EMAIL);
			}
			if(candidateSearchTO.getMothersNameDisp() != null && candidateSearchTO.getMothersNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getMothersNamePois()).setCellValue(CMSConstants.MOTHERS_NAME);
			}
			if(candidateSearchTO.getMothersEducationDisp() != null && candidateSearchTO.getMothersEducationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getMothersEducationPois()).setCellValue(CMSConstants.MOTHERS_EDUCATION);
			}
			if(candidateSearchTO.getMothersIncomeDisp() != null && candidateSearchTO.getMothersIncomeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getMothersIncomePois()).setCellValue(CMSConstants.MOTHERS_INCOME);
			}
			if(candidateSearchTO.getMothersCurrencyDisp() != null && candidateSearchTO.getMothersCurrencyDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getMothersCurrencyPois()).setCellValue(CMSConstants.MOTHERS_CURRENCY);
			}
			if(candidateSearchTO.getMothersOccupationDisp() != null && candidateSearchTO.getMothersOccupationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getMothersOccupationPois()).setCellValue(CMSConstants.MOTHERS_OCCUPATION);
			}
			if(candidateSearchTO.getMothersEmailDisp() != null && candidateSearchTO.getMothersEmailDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getMothersEmailPois()).setCellValue(CMSConstants.MOTHERS_EMAIL);
			}
			if(candidateSearchTO.getParentAdressLine1Disp() != null && candidateSearchTO.getParentAdressLine1Disp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getParentAdressLine1Pois()).setCellValue(CMSConstants.PARENT_ADDR_LINE1);
			}
			if(candidateSearchTO.getParentAdressLine2Disp() != null && candidateSearchTO.getParentAdressLine2Disp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getParentAdressLine2Pois()).setCellValue(CMSConstants.PARENT_ADDR_LINE2);
			}
			if(candidateSearchTO.getParentAdressLine3Disp() != null && candidateSearchTO.getParentAdressLine3Disp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getParentAdressLine3Pois()).setCellValue(CMSConstants.PARENT_ADDR_LINE3);
			}
			if(candidateSearchTO.getParentCityDisp() != null && candidateSearchTO.getParentCityDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getParentCityPois()).setCellValue(CMSConstants.PARENT_CITY);
			}
			if(candidateSearchTO.getParentStateDisp() != null && candidateSearchTO.getParentStateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getParentStatePois()).setCellValue(CMSConstants.PARENT_STATE);
			}
			if(candidateSearchTO.getParentStateOtherDisp() != null && candidateSearchTO.getParentStateOtherDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getParentStateOtherPois()).setCellValue(CMSConstants.PARENT_STATE_OTHER);
			}
			if(candidateSearchTO.getParentCountryDisp() != null && candidateSearchTO.getParentCountryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getParentCountryPois()).setCellValue(CMSConstants.PARENT_COUNTRY);
			}
			if(candidateSearchTO.getParentZipCodeDisp() != null && candidateSearchTO.getParentZipCodeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getParentZipCodePois()).setCellValue(CMSConstants.PARENT_ZIP_CODE);
			}
			if(candidateSearchTO.getParentPhoneDisp() != null && candidateSearchTO.getParentPhoneDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getParentPhonePois()).setCellValue(CMSConstants.PARENT_PHONE);
			}
			if(candidateSearchTO.getParentMobileNoDisp() != null && candidateSearchTO.getParentMobileNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getParentMobileNoPois()).setCellValue(CMSConstants.PARENT_MOBILE_NUMBER);
			}
			if(candidateSearchTO.getApplnNoDisp() != null && candidateSearchTO.getApplnNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getApplnNoPois()).setCellValue(CMSConstants.APPLICATION_NUM);
			}
			if(candidateSearchTO.getNameDisp() != null && candidateSearchTO.getNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getNamePois()).setCellValue(CMSConstants.STUDENT_NAME);
			}
			if(candidateSearchTO.getDateOfBirthDisp() != null && candidateSearchTO.getDateOfBirthDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getDateOfBirthPois()).setCellValue(CMSConstants.DATE_OF_BIRTH);
			}
			if(candidateSearchTO.getGenderDisp() != null && candidateSearchTO.getGenderDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getGenderPois()).setCellValue(CMSConstants.GENDER);
			}
			if(candidateSearchTO.getChallanNoDisp() != null && candidateSearchTO.getChallanNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getChallanNoPois()).setCellValue(CMSConstants.CHALLAN_NUMBER);
			}
			if(candidateSearchTO.getTotalWeightageDisp() != null && candidateSearchTO.getTotalWeightageDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getTotalWeightagePois()).setCellValue(CMSConstants.TOTAL_WEIGHTAGE);
			}
			if(candidateSearchTO.getEmailDisp() != null && candidateSearchTO.getEmailDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getEmailPois()).setCellValue(CMSConstants.EMAIL);
			}
			if(candidateSearchTO.getBloodGroupDisp() != null && candidateSearchTO.getBloodGroupDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getBloodGroupPois()).setCellValue(CMSConstants.BLOOD_GROUP);
			}
			if(candidateSearchTO.getSeatNoDisp() != null && candidateSearchTO.getSeatNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getSeatNoPois()).setCellValue(CMSConstants.REPORT_SEAT_NO);
			}
			if(candidateSearchTO.getExamCenterDisp() != null && candidateSearchTO.getExamCenterDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getExamCenterPois()).setCellValue(CMSConstants.REPORT_EXAM_CENTER);
			}
			if(candidateSearchTO.getMotherTongueDisp() != null && candidateSearchTO.getMotherTongueDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getMotherTonguePois()).setCellValue(CMSConstants.MOTHERTONGUE);
			}
			if(candidateSearchTO.getAdmissionStatusDisp()!= null && candidateSearchTO.getAdmissionStatusDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) ){
				row.createCell((short)candidateSearchTO.getAdmissionStatusPois()).setCellValue(CMSConstants.REPORT_ADDMISSION_STATUS);
			}
			if(candidateSearchTO.getIsAdmittedDisp()!= null && candidateSearchTO.getIsAdmittedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) ){
				row.createCell((short)candidateSearchTO.getIsAdmittedPois()).setCellValue(CMSConstants.IS_ADMITTED);
			}
			if(candidateSearchTO.getCurrentClassDisp()!= null && candidateSearchTO.getCurrentClassDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getCurrentClassPois()).setCellValue(CMSConstants.CURRENT_CLASS);
			}
//			changes made by priyatham --start--
			if(candidateSearchTO.getIsSelectedDisp()!=null && candidateSearchTO.getIsSelectedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getIsSelectedPois()).setCellValue(CMSConstants.IS_SELECTED);
			}
			if(candidateSearchTO.getIsFinalMeritApprovedDisp()!=null && candidateSearchTO.getIsFinalMeritApprovedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getIsFinalMeritApprovedPois()).setCellValue(CMSConstants.IS_FINAL_MERIT_APPROVED);
			}
			if(candidateSearchTO.getUserNameDisp()!=null && candidateSearchTO.getUserNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getUserNamePois()).setCellValue(CMSConstants.USER_NAME);
			}
			if(candidateSearchTO.getPasswordDisp()!=null && candidateSearchTO.getPasswordDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getPasswordPois()).setCellValue(CMSConstants.PASSWORD);
			}
//			--end--
			
//Mary Addition 
			
			if(candidateSearchTO.getAppliedDateDisp()!= null && candidateSearchTO.getAppliedDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getAppliedDatePois()).setCellValue(CMSConstants.APPLIED_DATE);
			}
			if(candidateSearchTO.getChallanPaymentDateDisp()!= null && candidateSearchTO.getChallanPaymentDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getChallanPaymentDatePois()).setCellValue(CMSConstants.CHALLAN_PAYMENT_DATE);
			}
			if(candidateSearchTO.getFeeChallanDateDisp()!= null && candidateSearchTO.getFeeChallanDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFeeChallanDatePois()).setCellValue(CMSConstants.FEE_CHALLAN_DATE);
			}
			if(candidateSearchTO.getFeeChallanNoDisp()!= null && candidateSearchTO.getFeeChallanNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFeeChallanNoPois()).setCellValue(CMSConstants.FEE_CHALLAN_NO);
			}
			if(candidateSearchTO.getTotalFeePaidDisp()!= null && candidateSearchTO.getTotalFeePaidDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getTotalFeePaidPois()).setCellValue(CMSConstants.TOTAL_FEES_PAID);
			}
			if(candidateSearchTO.getIsHandicapedDisp()!= null && candidateSearchTO.getIsHandicapedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getIsHandicapedPois()).setCellValue(CMSConstants.IS_HANDICAPED);
			}
			if(candidateSearchTO.getHandicapDetailsDisp()!= null && candidateSearchTO.getHandicapDetailsDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getHandicapDetailsPois()).setCellValue(CMSConstants.HANDICAP_DETAILS);
			}
			if(candidateSearchTO.getTotalExpYearDisp()!= null && candidateSearchTO.getTotalExpYearDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getTotalExpYearPois()).setCellValue(CMSConstants.TOTAL_EXPERIENCE);
			}
	//Ends
			//added for challan verification display
			if(candidateSearchTO.getChallanVerfiedDisp()!= null && candidateSearchTO.getChallanVerfiedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getChallanVerifiedPois()).setCellValue(CMSConstants.CHALLAN_VERIFICATION);
			}
		//challan verification addition completed	
			
			//added for photo status display
			if(candidateSearchTO.getPhotoDisp()!= null && candidateSearchTO.getPhotoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getPhotoPois()).setCellValue(CMSConstants.PHOTO);
			}
		//photo status display addition completed	
			//added for specialization prefered and backlogs display
			if(candidateSearchTO.getSpecializationPreferedDisp()!=null && candidateSearchTO.getSpecializationPreferedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getSpecializationPreferedPois()).setCellValue(CMSConstants.SPECIALIZATION_PREFERED);
			}
			if(candidateSearchTO.getBackLogsDisp()!=null && candidateSearchTO.getBackLogsDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getBackLogsPois()).setCellValue(CMSConstants.BACK_LOGS);
			}
			//Canceled Status
			if(candidateSearchTO.getCanceledDisp()!=null && candidateSearchTO.getCanceledDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getCanceledPois()).setCellValue(CMSConstants.IS_CANCELED);
			}
			if(candidateSearchTO.getNewbackLogsDisp()!=null && candidateSearchTO.getNewbackLogsDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getNewbackLogsPois()).setCellValue(CMSConstants.BACK_LOGS_NEW);
			}
			// added for Applicant Feedback by sudhir
			if(candidateSearchTO.getApplicantFeedbackDisp()!=null && candidateSearchTO.getApplicantFeedbackDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getApplicantFeedbackPois()).setCellValue(CMSConstants.APPLICANT_FEEDBACK);
			}
			// added for University Email by Cimi
			if(candidateSearchTO.getUniversityEmailDisp()!=null && candidateSearchTO.getUniversityEmailDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getUniversityEmailPois()).setCellValue(CMSConstants.UNIVERSITY_EMAIL);
			}
			// added for Applicant Feedback by chandra
			if(candidateSearchTO.getComedkDisp()!=null && candidateSearchTO.getComedkDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getComedkPois()).setCellValue(CMSConstants.COMED_K);
			}
			// added by Manu
			if(candidateSearchTO.getRemarksDisp()!=null && candidateSearchTO.getRemarksDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getRemarkPois()).setCellValue(CMSConstants.ADMISSION_REMARKS);
			}
			
			// added by Mary
			if(candidateSearchTO.getSelectionProcessDateDisp()!=null && candidateSearchTO.getSelectionProcessDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getSelectionProcessDatePois()).setCellValue(CMSConstants.ADMISSION_SELECTION_PROCESS_DATE);
			}
			// added by giri
			if(candidateSearchTO.getPhotoUploadedDisp()!=null && candidateSearchTO.getPhotoUploadedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getPhotoUploadedPois()).setCellValue(CMSConstants.PHOTO_UPLOADED);
			}
			//end by giri
			ICandidateSearchTxnImpl iCandidateSearchTxnImpl = new CandidateSearchTxnImpl();
			
			short maxNo = iCandidateSearchTxnImpl.getMaxColumnNo().shortValue();
			
			maxNo = (short) (maxNo + 1);
			short counter = 0;
			if(candidateSearchTO.isDispStatus()){
				row.createCell(maxNo).setCellValue(CMSConstants.REPORT_ADMISSION_OTHER_THAN_SELECTED);
				counter = (short) (counter + 1);
				row.createCell((short) (maxNo + counter) ).setCellValue("");
				counter = (short) (counter + 1);
			}
			
//		modified by priyatham --start--	
		if(candidateSearchTO.getInterviewResultDisp() != null && candidateSearchTO.getInterviewResultDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
		Iterator csIterator=candidateSearchTOList.iterator();
		while(csIterator.hasNext()){
				CandidateSearchTO canSearchTO=(CandidateSearchTO)csIterator.next();
				if(interviewResultDispCount==0){
				TreeMap<String, String> gradeMap = canSearchTO.getGradeMap();
				if(gradeMap!= null && gradeMap.size() > 0){
					Iterator<String> grItr = gradeMap.keySet().iterator();
					while (grItr.hasNext()) {
						row.createCell((short) (maxNo + counter) ).setCellValue(grItr.next());
						counter++;
						interviewResultDispCount++;
					}
				}
				if(interviewResultDispCount!=0){
					break;
				}
				}
			}
		}
		//added for comments display
		if(candidateSearchTO.getInterviewResultDisp() != null && candidateSearchTO.getInterviewResultDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
		Iterator csToIterator=candidateSearchTOList.iterator();
		CandidateSearchTO canSearchTO=null;
		while(csToIterator.hasNext()){
				canSearchTO=(CandidateSearchTO)csToIterator.next();
				if( interviewResultCommentDispCount==0){
				if(canSearchTO.getCommentsMap()!= null && canSearchTO.getCommentsMap().size() > 0){
					Iterator<String> comItr = canSearchTO.getCommentsMap().keySet().iterator();
					while (comItr.hasNext()) {
						row.createCell((short) (maxNo + counter) ).setCellValue(comItr.next());
						counter++;
						interviewResultCommentDispCount++;
					}
				}
				if(interviewResultDispCount!=0){
					break;
				}
				}
			}
		}

	if(candidateSearchTO.getPrvExamDetDisp() != null && candidateSearchTO.getPrvExamDetDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
		Iterator csIterator3=candidateSearchTOList.iterator();
		CandidateSearchTO canSearchTO=null;
		while(csIterator3.hasNext()){
			canSearchTO=(CandidateSearchTO)csIterator3.next();
			if( prvExamDetDispCounter==0){
				int prvExamDetDisp=0;
				if(canSearchTO!= null && canSearchTO.getDocTypePerMap()!= null){
					Iterator<String> perItr =  canSearchTO.getDocTypePerMap().keySet().iterator();
					while (perItr.hasNext()) {
						row.createCell((short) (maxNo + counter) ).setCellValue(perItr.next() + "%");
						counter++;
						prvExamDetDisp++;
					}
				}
				prvExamDetDispCounter=(prvExamDetDisp>prvExamDetDispCounter)?prvExamDetDisp:prvExamDetDispCounter;
				prvExamDetDisp=0;
				if(canSearchTO!= null && canSearchTO.getDocTypeExamNameMap()!= null){
					Iterator<String> perItr = canSearchTO.getDocTypeExamNameMap().keySet().iterator();
					while (perItr.hasNext()) {
						row.createCell((short) (maxNo + counter) ).setCellValue( perItr.next() + " Exam Name");
						counter++;
						prvExamDetDisp++;
					}
				}
				prvExamDetDispCounter=(prvExamDetDisp>prvExamDetDispCounter)?prvExamDetDisp:prvExamDetDispCounter;
				prvExamDetDisp=0;
				if(canSearchTO!= null && canSearchTO.getUniMap()!= null){
					Iterator<String> uniItr = canSearchTO.getUniMap().keySet().iterator();
					while (uniItr.hasNext()) {
						row.createCell((short) (maxNo + counter) ).setCellValue(uniItr.next() + " Uni");
						counter++;
						prvExamDetDisp++;
					}
				}
				prvExamDetDispCounter=(prvExamDetDisp>prvExamDetDispCounter)?prvExamDetDisp:prvExamDetDispCounter;
				prvExamDetDisp=0;
				if(canSearchTO!= null && canSearchTO.getInstMap()!= null){
					
					Iterator<String> iniItr = canSearchTO.getInstMap().keySet().iterator();
					while (iniItr.hasNext()) {
						row.createCell((short) (maxNo + counter) ).setCellValue(iniItr.next() + " Inst");
						counter++;
						prvExamDetDisp++;
					}
				}
				prvExamDetDispCounter=(prvExamDetDisp>prvExamDetDispCounter)?prvExamDetDisp:prvExamDetDispCounter;
				prvExamDetDisp=0;
				if(canSearchTO!= null && canSearchTO.getYearMap()!= null){
					Iterator<String> iniItr = canSearchTO.getYearMap().keySet().iterator();
					while (iniItr.hasNext()) {
						row.createCell((short) (maxNo + counter) ).setCellValue(iniItr.next() + " YOP");
						counter++;
						prvExamDetDisp++;
					}
				}
				prvExamDetDispCounter=(prvExamDetDisp>prvExamDetDispCounter)?prvExamDetDisp:prvExamDetDispCounter;
				if(prvExamDetDispCounter!=0){
					break;
				}
			}
		}
		}	
	if(candidateSearchTO.getIntDateTimeDisp() != null && candidateSearchTO.getIntDateTimeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
			Iterator csIterator2=candidateSearchTOList.iterator();
			CandidateSearchTO canSearchTO=null;
			while(csIterator2.hasNext()){
			canSearchTO=(CandidateSearchTO)csIterator2.next();
			if(dateTimeDispCount==0){
				int dateTimeDisp=0;
				if(canSearchTO!=null && canSearchTO.getIntDateMap()!=null){
					Iterator<String> iniItr = canSearchTO.getIntDateMap().keySet().iterator();
					while (iniItr.hasNext()) {
						row.createCell((short) (maxNo + counter) ).setCellValue(iniItr.next() + " Date");
						counter++;
						dateTimeDisp++;
					}
				}
				dateTimeDispCount=(dateTimeDisp>dateTimeDispCount)?dateTimeDisp:dateTimeDispCount;
				dateTimeDisp=0;
				if(canSearchTO!=null && canSearchTO.getIntTimeMap()!=null){
					Iterator<String> iniItr =  canSearchTO.getIntTimeMap().keySet().iterator();
					while (iniItr.hasNext()) {
						row.createCell((short) (maxNo + counter) ).setCellValue(iniItr.next() + " Time");
						counter++;
						dateTimeDisp++;
					}
				}
				dateTimeDispCount=(dateTimeDisp>dateTimeDispCount)?dateTimeDisp:dateTimeDispCount;
				if(dateTimeDispCount!=0){
					break;
				}
			}
			}
		}
			if(candidateSearchTO.getPreReqDisp() != null && candidateSearchTO.getPreReqDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				Iterator csIterator1=candidateSearchTOList.iterator();
				CandidateSearchTO canSearchTO=null;
				while(csIterator1.hasNext()){
					canSearchTO=(CandidateSearchTO)csIterator1.next();
				
				if( preReqDispCount==0){
					int preReqCounter=0;
					if(canSearchTO!= null && canSearchTO.getpExamMap()!= null){
						Iterator<String> perItr = canSearchTO.getpExamMap().keySet().iterator();
						while (perItr.hasNext()) {
							row.createCell((short) (maxNo + counter) ).setCellValue(perItr.next() + " Obtained Marks");
							counter++;
							preReqCounter++;
						}
					}
					preReqDispCount=(preReqCounter>preReqDispCount)?preReqCounter:preReqDispCount;
					preReqCounter=0;
					if(canSearchTO!= null && canSearchTO.getpRollMap()!= null){
						Iterator<String> uniItr = canSearchTO.getpRollMap().keySet().iterator();
						while (uniItr.hasNext()) {
							row.createCell((short) (maxNo + counter) ).setCellValue(uniItr.next() + " RollNo");
							counter++;
							preReqCounter++;
						}
					}
					preReqDispCount=(preReqCounter>preReqDispCount)?preReqCounter:preReqDispCount;
					preReqCounter=0;
					if(canSearchTO!= null && canSearchTO.getpMopMap()!= null){
						Iterator<String> iniItr = canSearchTO.getpMopMap().keySet().iterator();
						while (iniItr.hasNext()) {
							row.createCell((short) (maxNo + counter) ).setCellValue( iniItr.next() + " Passing Month");
							counter++;
							preReqCounter++;
						}
					}
					preReqDispCount=(preReqCounter>preReqDispCount)?preReqCounter:preReqDispCount;
					preReqCounter=0;
					if(canSearchTO!= null && canSearchTO.getpYopMap()!= null){
						Iterator<String> iniItr = canSearchTO.getpYopMap().keySet().iterator();
						while (iniItr.hasNext()) {
							row.createCell((short) (maxNo + counter) ).setCellValue( iniItr.next() + " YOP");
							counter++;
							preReqCounter++;
						}
					}
					preReqDispCount=(preReqCounter>preReqDispCount)?preReqCounter:preReqDispCount;
					if(preReqDispCount!=0){
						break;
					}
				}
				
			}
		}
//			--end--
//			row.createCell((short) (maxNo + counter) ).setCellValue(" ");
			while (iterator.hasNext()) {
				CandidateSearchTO candidateSearch = (CandidateSearchTO) iterator.next();
//				System.out.println(candidateSearch.getApplnNo());
//				if(candidateSearch.getApplnNo()==12550152){// remove this
				count = count +1;
				row = sheet.createRow(count);				
				if(candidateSearchTO.getRegNoDisp() != null && candidateSearchTO.getRegNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getRegNo()!=null){
					row.createCell((short)candidateSearchTO.getRegNoPois()).setCellValue(candidateSearch.getRegNo());
				}	
				if(candidateSearchTO.getRollNoDisp() != null && candidateSearchTO.getRollNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getRollNo()!=null){
					row.createCell((short)candidateSearchTO.getRollNoPois()).setCellValue(candidateSearch.getRollNo());
				}
				if(candidateSearchTO.getClassNameDisp() != null && candidateSearchTO.getClassNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getClassStudent()!=null){
					row.createCell((short)candidateSearchTO.getClassNamePois()).setCellValue(candidateSearch.getClassStudent());
				}
				if(candidateSearchTO.getAdmissionDateDisp() != null && candidateSearchTO.getAdmissionDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getAdmissionDate()!=null){
					row.createCell((short)candidateSearchTO.getAdmissionDatePois()).setCellValue(candidateSearch.getAdmissionDate());
				}
				if(candidateSearchTO.getSecondLanguageDisp() != null && candidateSearchTO.getSecondLanguageDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getSecondLanguage()!=null){
					row.createCell((short)candidateSearchTO.getSecondLanguagePois()).setCellValue(candidateSearch.getSecondLanguage());
				}
				if(candidateSearchTO.getProgramTypeNameDisp() != null && candidateSearchTO.getProgramTypeNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getProgramTypeName()!=null){
					row.createCell((short)candidateSearchTO.getProgramTypeNamePois()).setCellValue(candidateSearch.getProgramTypeName());
				}
				if(candidateSearchTO.getProgramNameDisp() != null && candidateSearchTO.getProgramNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getProgramName()!=null){
					row.createCell((short)candidateSearchTO.getProgramNamePois()).setCellValue(candidateSearch.getProgramName());
				}
				if(candidateSearchTO.getCourseNameDisp() != null && candidateSearchTO.getCourseNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getCourseName()!=null){
					row.createCell((short)candidateSearchTO.getCourseNamePois()).setCellValue(candidateSearch.getCourseName());
				}
				if(candidateSearchTO.getJournalNoDisp() != null && candidateSearchTO.getJournalNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getJournalNo()!=null){
					row.createCell((short)candidateSearchTO.getJournalNoPois()).setCellValue(candidateSearch.getJournalNo());
				}
				if(candidateSearchTO.getAcedamicYearDisp() != null && candidateSearchTO.getAcedamicYearDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getAppliedYear()!=null){
					row.createCell((short)candidateSearchTO.getAcedamicYearPois()).setCellValue(candidateSearch.getAppliedYear());
				}
				if(candidateSearchTO.getAdmittedThroughDisp() != null && candidateSearchTO.getAdmittedThroughDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getAdmittedThrough()!=null){
					row.createCell((short)candidateSearchTO.getAdmittedThroughPois()).setCellValue(candidateSearch.getAdmittedThrough());
				}
				if(candidateSearchTO.getResidentCategoryDisp() != null && candidateSearchTO.getResidentCategoryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getResidentCategory()!=null){
					row.createCell((short)candidateSearchTO.getResidentCategoryPois()).setCellValue(candidateSearch.getResidentCategory());
				}
				if(candidateSearchTO.getReligionDisp() != null && candidateSearchTO.getReligionDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getReligion()!=null){
					row.createCell((short)candidateSearchTO.getReligionPois()).setCellValue(candidateSearch.getReligion());
				}
				if(candidateSearchTO.getReligionOtherDisp() != null && candidateSearchTO.getReligionOtherDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getReligionOther()!=null){
					row.createCell((short)candidateSearchTO.getReligionOtherPois()).setCellValue(candidateSearch.getReligionOther());
				}
				if(candidateSearchTO.getSubReligionDisp() != null && candidateSearchTO.getSubReligionDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getSubReligion()!=null){
					row.createCell((short)candidateSearchTO.getSubReligionPois()).setCellValue(candidateSearch.getSubReligion());
				}
				if(candidateSearchTO.getSubReligionOtherDisp() != null && candidateSearchTO.getSubReligionOtherDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getSubReligionOther()!=null){
					row.createCell((short)candidateSearchTO.getSubReligionOtherPois()).setCellValue(candidateSearch.getSubReligionOther());
				}
				if(candidateSearchTO.getCastCategoryDisp() != null && candidateSearchTO.getCastCategoryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getCastCategory()!=null){
					row.createCell((short)candidateSearchTO.getCastCategoryPois()).setCellValue(candidateSearch.getCastCategory());
				}
				if(candidateSearchTO.getCastCategoryOtherDisp() != null && candidateSearchTO.getCastCategoryOtherDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getCastCategoryOther()!=null){
					row.createCell((short)candidateSearchTO.getCastCategoryOtherPois()).setCellValue(candidateSearch.getCastCategoryOther());
				}
				if(candidateSearchTO.getPlaceOfBirthDisp() != null && candidateSearchTO.getPlaceOfBirthDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getPlaceOfBirth()!=null){
					row.createCell((short)candidateSearchTO.getPlaceOfBirthPois()).setCellValue(candidateSearch.getPlaceOfBirth());
				}
				if(candidateSearchTO.getStateOfBirthDisp() != null && candidateSearchTO.getStateOfBirthDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getStateOfBirth()!=null){
					row.createCell((short)candidateSearchTO.getStateOfBirthPois()).setCellValue(candidateSearch.getStateOfBirth());
				}
				if(candidateSearchTO.getStateOfBirthOtherDisp() != null && candidateSearchTO.getStateOfBirthOtherDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getStateOfBirthOther()!=null){
					row.createCell((short)candidateSearchTO.getStateOfBirthOtherPois()).setCellValue(candidateSearch.getStateOfBirthOther());
				}
				if(candidateSearchTO.getCountryOfBirthDisp() != null && candidateSearchTO.getCountryOfBirthDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getCountryOfBirth()!=null){
					row.createCell((short)candidateSearchTO.getCountryOfBirthPois()).setCellValue(candidateSearch.getCountryOfBirth());
				}
				if(candidateSearchTO.getBelongsToDisp() != null && candidateSearchTO.getBelongsToDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getBelongsTo()!=null){
					row.createCell((short)candidateSearchTO.getBelongsToPois()).setCellValue(candidateSearch.getBelongsTo());
				}
				if(candidateSearchTO.getNationalityDisp() != null && candidateSearchTO.getNationalityDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getNationality()!=null){
					row.createCell((short)candidateSearchTO.getNationalityPois()).setCellValue(candidateSearch.getNationality());
				}
				if(candidateSearchTO.getStudentPhoneNumberDisp() != null && candidateSearchTO.getStudentPhoneNumberDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getStudentPhoneNo()!=null){
					cell = row.createCell((short)candidateSearchTO.getStudentPhoneNumberPois());
					cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(candidateSearch.getStudentPhoneNo());
				}
				if(candidateSearchTO.getStudentMobileNumberDisp() != null && candidateSearchTO.getStudentMobileNumberDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getStudentMobileNo()!=null){
					cell = row.createCell((short)candidateSearchTO.getStudentMobileNumberPois());
					cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(candidateSearch.getStudentMobileNo());
				}
				if(candidateSearchTO.getPassportNumberDisp() != null && candidateSearchTO.getPassportNumberDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getPassportNo()!=null){
					row.createCell((short)candidateSearchTO.getPassportNumberPois()).setCellValue(candidateSearch.getPassportNo());
				}
				if(candidateSearchTO.getPassportIssuingCountryDisp() != null && candidateSearchTO.getPassportIssuingCountryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getPassportIssuingCountry()!=null){
					row.createCell((short)candidateSearchTO.getPassportIssuingCountryPois()).setCellValue(candidateSearch.getPassportIssuingCountry());
				}
				if(candidateSearchTO.getPassportValidUpToDisp() != null && candidateSearchTO.getPassportValidUpToDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getPassportValidUpTo()!=null){
					row.createCell((short)candidateSearchTO.getPassportValidUpToPois()).setCellValue(candidateSearch.getPassportValidUpTo());
				}
				if(candidateSearchTO.getFirstPreferenceProgramTypeDisp() != null && candidateSearchTO.getFirstPreferenceProgramTypeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getFirstPrePT()!=null){
					row.createCell((short)candidateSearchTO.getFirstPreferenceProgramTypePois()).setCellValue(candidateSearch.getFirstPrePT());
				}
				if(candidateSearchTO.getFirstPreferenceProgramDisp() != null && candidateSearchTO.getFirstPreferenceProgramDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getFirstPreProgram()!=null){
					row.createCell((short)candidateSearchTO.getFirstPreferenceProgramPois()).setCellValue(candidateSearch.getFirstPreProgram());
				}
				if(candidateSearchTO.getFirstPreferenceCourseDisp() != null && candidateSearchTO.getFirstPreferenceCourseDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getFirstCourse()!=null){
					row.createCell((short)candidateSearchTO.getFirstPreferenceCoursePois()).setCellValue(candidateSearch.getFirstCourse());
				}
				if(candidateSearchTO.getSecondPreferenceProgramTypeDisp() != null && candidateSearchTO.getSecondPreferenceProgramTypeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getSecondPrePT()!=null){
					row.createCell((short)candidateSearchTO.getSecondPreferenceProgramTypePois()).setCellValue(candidateSearch.getSecondPrePT());
				}
				if(candidateSearchTO.getSecondPreferenceProgramDisp() != null && candidateSearchTO.getSecondPreferenceProgramDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getSecondPreProgram()!=null){
					row.createCell((short)candidateSearchTO.getSecondPreferenceProgramPois()).setCellValue(candidateSearch.getSecondPreProgram());
				}
				if(candidateSearchTO.getSecondPreferenceCourseDisp() != null && candidateSearchTO.getSecondPreferenceCourseDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getSecondCourse()!=null){
					row.createCell((short)candidateSearchTO.getSecondPreferenceCoursePois()).setCellValue(candidateSearch.getSecondCourse());
				}
				if(candidateSearchTO.getThirdPreferenceProgramTypeDisp() != null && candidateSearchTO.getThirdPreferenceProgramTypeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getThirdPrePT()!=null){
					row.createCell((short)candidateSearchTO.getThirdPreferenceProgramTypePois()).setCellValue(candidateSearch.getThirdPrePT());
				}
				if(candidateSearchTO.getThirdPreferenceProgramDisp() != null && candidateSearchTO.getThirdPreferenceProgramDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getThirdPreProgram()!=null){
					row.createCell((short)candidateSearchTO.getThirdPreferenceProgramPois()).setCellValue(candidateSearch.getThirdPreProgram());
				}
				if(candidateSearchTO.getThirdPreferenceCourseDisp() != null && candidateSearchTO.getThirdPreferenceCourseDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getThirdCourse()!=null){
					row.createCell((short)candidateSearchTO.getThirdPreferenceCoursePois()).setCellValue(candidateSearch.getThirdCourse());
				}
				if(candidateSearchTO.getFirstNameofOrganisationDisp() != null && candidateSearchTO.getFirstNameofOrganisationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getFirstNameOfOra()!=null){
					row.createCell((short)candidateSearchTO.getFirstNameofOrganisationPois()).setCellValue(candidateSearch.getFirstNameOfOra());
				}
				if(candidateSearchTO.getFirstDesignationDisp() != null && candidateSearchTO.getFirstDesignationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getFirstDesignation()!=null){
					row.createCell((short)candidateSearchTO.getFirstDesignationPois()).setCellValue(candidateSearch.getFirstDesignation());
				}
				if(candidateSearchTO.getFirstFromDateDisp() != null && candidateSearchTO.getFirstFromDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getFirstFromDate()!=null){
					row.createCell((short)candidateSearchTO.getFirstFromDatePois()).setCellValue(candidateSearch.getFirstFromDate());
				}
				if(candidateSearchTO.getFirstToDateDisp() != null && candidateSearchTO.getFirstToDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getFirstTODate()!=null){
					row.createCell((short)candidateSearchTO.getFirstToDatePois()).setCellValue(candidateSearch.getFirstTODate());
				}
				if(candidateSearchTO.getSecondNameofOrganisationDisp() != null && candidateSearchTO.getSecondNameofOrganisationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getSecondNameOfOra()!=null){
					row.createCell((short)candidateSearchTO.getSecondNameofOrganisationPois()).setCellValue(candidateSearch.getSecondNameOfOra());
				}
				if(candidateSearchTO.getSecondDesignationDisp() != null && candidateSearchTO.getSecondDesignationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getSecondDesignation()!=null){
					row.createCell((short)candidateSearchTO.getSecondDesignationPois()).setCellValue(candidateSearch.getSecondDesignation());
				}
				if(candidateSearchTO.getSecondFromDateDisp() != null && candidateSearchTO.getSecondFromDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getSecondFromDate()!=null){
					row.createCell((short)candidateSearchTO.getSecondFromDatePois()).setCellValue(candidateSearch.getSecondFromDate());
				}
				if(candidateSearchTO.getSecondToDateDisp() != null && candidateSearchTO.getSecondToDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getSecondTODate()!=null){
					row.createCell((short)candidateSearchTO.getSecondToDatePois()).setCellValue(candidateSearch.getSecondTODate());
				}
				if(candidateSearchTO.getThirdNameofOrganisationDisp() != null && candidateSearchTO.getThirdNameofOrganisationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getThirdNameOfOra()!=null){
					row.createCell((short)candidateSearchTO.getThirdNameofOrganisationPois()).setCellValue(candidateSearch.getThirdNameOfOra());
				}
				if(candidateSearchTO.getThirdDesignationDisp() != null && candidateSearchTO.getThirdDesignationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getThirdDesignation()!=null){
					row.createCell((short)candidateSearchTO.getThirdDesignationPois()).setCellValue(candidateSearch.getThirdDesignation());
				}
				if(candidateSearchTO.getThirdFromDateDisp() != null && candidateSearchTO.getThirdFromDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getThirdFromDate()!=null){
					row.createCell((short)candidateSearchTO.getThirdFromDatePois()).setCellValue(candidateSearch.getThirdFromDate());
				}
				if(candidateSearchTO.getThirdToDateDisp() != null && candidateSearchTO.getThirdToDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getThirdTODate()!=null){
					row.createCell((short)candidateSearchTO.getThirdToDatePois()).setCellValue(candidateSearch.getThirdTODate());
				}
				if(candidateSearchTO.getPermanentAddressLine1Disp() != null && candidateSearchTO.getPermanentAddressLine1Disp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getPermanentAddressline1()!=null){
					row.createCell((short)candidateSearchTO.getPermanentAddressLine1Pois()).setCellValue(candidateSearch.getPermanentAddressline1());
				}
				if(candidateSearchTO.getPermanentAddressLine2Disp() != null && candidateSearchTO.getPermanentAddressLine2Disp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getPermanentAddressline2()!=null){
					row.createCell((short)candidateSearchTO.getPermanentAddressLine2Pois()).setCellValue(candidateSearch.getPermanentAddressline2());
				}
				if(candidateSearchTO.getPermanentStateDisp() != null && candidateSearchTO.getPermanentStateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getPermanentState()!=null){
					row.createCell((short)candidateSearchTO.getPermanentStatePois()).setCellValue(candidateSearch.getPermanentState());
				}
				if(candidateSearchTO.getPermanentStateOthersDisp() != null && candidateSearchTO.getPermanentStateOthersDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getPermanentStateOther()!=null){
					row.createCell((short)candidateSearchTO.getPermanentStateOthersPois()).setCellValue(candidateSearch.getPermanentStateOther());
				}
				if(candidateSearchTO.getPermanentCityDisp() != null && candidateSearchTO.getPermanentCityDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getPermanentCity()!=null){
					row.createCell((short)candidateSearchTO.getPermanentCityPois()).setCellValue(candidateSearch.getPermanentCity());
				}
				if(candidateSearchTO.getPermanentCountryDisp() != null && candidateSearchTO.getPermanentCountryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getPermanentCountry()!=null){
					row.createCell((short)candidateSearchTO.getPermanentCountryPois()).setCellValue(candidateSearch.getPermanentCountry());
				}
				if(candidateSearchTO.getPermanentZipCodeDisp() != null && candidateSearchTO.getPermanentZipCodeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getPermanentZipCode()!=null){
					row.createCell((short)candidateSearchTO.getPermanentZipCodePois()).setCellValue(candidateSearch.getPermanentZipCode());
				}
				if(candidateSearchTO.getCurrentAddressLine1Disp() != null && candidateSearchTO.getCurrentAddressLine1Disp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getCurrentAddressline1()!=null){
					row.createCell((short)candidateSearchTO.getCurrentAddressLine1Pois()).setCellValue(candidateSearch.getCurrentAddressline1());
				}
				if(candidateSearchTO.getCurrentAddressLine2Disp() != null && candidateSearchTO.getCurrentAddressLine2Disp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getCurrentAddressline2()!=null){
					row.createCell((short)candidateSearchTO.getCurrentAddressLine2Pois()).setCellValue(candidateSearch.getCurrentAddressline2());
				}
				if(candidateSearchTO.getCurrentStateDisp() != null && candidateSearchTO.getCurrentStateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getCurrentState()!=null){
					row.createCell((short)candidateSearchTO.getCurrentStatePois()).setCellValue(candidateSearch.getCurrentState());
				}
				if(candidateSearchTO.getCurrentStateOthersDisp() != null && candidateSearchTO.getCurrentStateOthersDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getCurrentStateOther()!=null){
					row.createCell((short)candidateSearchTO.getCurrentStateOthersPois()).setCellValue(candidateSearch.getCurrentStateOther());
				}
				if(candidateSearchTO.getCurrentCityDisp() != null && candidateSearchTO.getCurrentCityDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getCurrentCity()!=null){
					row.createCell((short)candidateSearchTO.getCurrentCityPois()).setCellValue(candidateSearch.getCurrentCity());
				}
				if(candidateSearchTO.getCurrentCountryDisp() != null && candidateSearchTO.getCurrentCountryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getCurrentCountry()!=null){
					row.createCell((short)candidateSearchTO.getCurrentCountryPois()).setCellValue(candidateSearch.getCurrentCountry());
				}
				if(candidateSearchTO.getCurrentZipCodeDisp() != null && candidateSearchTO.getCurrentZipCodeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getCurrentZipCode()!=null){
					row.createCell((short)candidateSearchTO.getCurrentZipCodePois()).setCellValue(candidateSearch.getCurrentZipCode());
				}
				if(candidateSearchTO.getFathersNameDisp() != null && candidateSearchTO.getFathersNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getFatherName()!=null){
					row.createCell((short)candidateSearchTO.getFathersNamePois()).setCellValue(candidateSearch.getFatherName());
				}
				if(candidateSearchTO.getFathersEducationDisp() != null && candidateSearchTO.getFathersEducationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getFatherEducation()!=null){
					row.createCell((short)candidateSearchTO.getFathersEducationPois()).setCellValue(candidateSearch.getFatherEducation());
				}
				if(candidateSearchTO.getFathersIncomeDisp() != null && candidateSearchTO.getFathersIncomeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getFatherIncome()!=null){
					row.createCell((short)candidateSearchTO.getFathersIncomePois()).setCellValue(candidateSearch.getFatherIncome());
				}
				if(candidateSearchTO.getFathersCurrencyDisp() != null && candidateSearchTO.getFathersCurrencyDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getFatherIncomeCurrency()!=null){
					row.createCell((short)candidateSearchTO.getFathersCurrencyPois()).setCellValue(candidateSearch.getFatherIncomeCurrency());
				}
				if(candidateSearchTO.getFathersOccupationDisp() != null && candidateSearchTO.getFathersOccupationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getFatherOccupation()!=null){
					row.createCell((short)candidateSearchTO.getFathersOccupationPois()).setCellValue(candidateSearch.getFatherOccupation());
				}
				if(candidateSearchTO.getFathersEmailDisp() != null && candidateSearchTO.getFathersEmailDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getFatherEmail()!=null){
					row.createCell((short)candidateSearchTO.getFathersEmailPois()).setCellValue(candidateSearch.getFatherEmail());
				}
				if(candidateSearchTO.getMothersNameDisp() != null && candidateSearchTO.getMothersNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getMotherName()!=null){
					row.createCell((short)candidateSearchTO.getMothersNamePois()).setCellValue(candidateSearch.getMotherName());
				}
				if(candidateSearchTO.getMothersEducationDisp() != null && candidateSearchTO.getMothersEducationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getMotherEducation()!=null){
					row.createCell((short)candidateSearchTO.getMothersEducationPois()).setCellValue(candidateSearch.getMotherEducation());
				}
				if(candidateSearchTO.getMothersIncomeDisp() != null && candidateSearchTO.getMothersIncomeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getMotherIncome()!=null){
					row.createCell((short)candidateSearchTO.getMothersIncomePois()).setCellValue(candidateSearch.getMotherIncome());
				}
				if(candidateSearchTO.getMothersCurrencyDisp() != null && candidateSearchTO.getMothersCurrencyDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getMotherIncomeCurrency()!=null){
					row.createCell((short)candidateSearchTO.getMothersCurrencyPois()).setCellValue(candidateSearch.getMotherIncomeCurrency());
				}
				if(candidateSearchTO.getMothersOccupationDisp() != null && candidateSearchTO.getMothersOccupationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getMotherOccupation()!=null){
					row.createCell((short)candidateSearchTO.getMothersOccupationPois()).setCellValue(candidateSearch.getMotherOccupation());
				}
				if(candidateSearchTO.getMothersEmailDisp() != null && candidateSearchTO.getMothersEmailDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getMotherEmail()!=null){
					row.createCell((short)candidateSearchTO.getMothersEmailPois()).setCellValue(candidateSearch.getMotherEmail());
				}
				if(candidateSearchTO.getParentAdressLine1Disp() != null && candidateSearchTO.getParentAdressLine1Disp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getParentAdressLine1()!=null){
					row.createCell((short)candidateSearchTO.getParentAdressLine1Pois()).setCellValue(candidateSearch.getParentAdressLine1());
				}
				if(candidateSearchTO.getParentAdressLine2Disp() != null && candidateSearchTO.getParentAdressLine2Disp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getParentAdressLine2()!=null){
					row.createCell((short)candidateSearchTO.getParentAdressLine2Pois()).setCellValue(candidateSearch.getParentAdressLine2());
				}
				if(candidateSearchTO.getParentAdressLine3Disp() != null && candidateSearchTO.getParentAdressLine3Disp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getParentAdressLine3()!=null){
					row.createCell((short)candidateSearchTO.getParentAdressLine3Pois()).setCellValue(candidateSearch.getParentAdressLine3());
				}
				if(candidateSearchTO.getParentCityDisp() != null && candidateSearchTO.getParentCityDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getParentCity()!=null){
					row.createCell((short)candidateSearchTO.getParentCityPois()).setCellValue(candidateSearch.getParentCity());
				}
				if(candidateSearchTO.getParentStateDisp() != null && candidateSearchTO.getParentStateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getParentState()!=null){
					row.createCell((short)candidateSearchTO.getParentStatePois()).setCellValue(candidateSearch.getParentState());
				}
				if(candidateSearchTO.getParentStateOtherDisp() != null && candidateSearchTO.getParentStateOtherDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getParentStateOther()!=null){
					row.createCell((short)candidateSearchTO.getParentStateOtherPois()).setCellValue(candidateSearch.getParentStateOther());
				}
				if(candidateSearchTO.getParentCountryDisp() != null && candidateSearchTO.getParentCountryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getParentCountry()!=null){
					row.createCell((short)candidateSearchTO.getParentCountryPois()).setCellValue(candidateSearch.getParentCountry());
				}
				if(candidateSearchTO.getParentZipCodeDisp() != null && candidateSearchTO.getParentZipCodeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getParentZipCode()!=null){
					row.createCell((short)candidateSearchTO.getParentZipCodePois()).setCellValue(candidateSearch.getParentZipCode());
				}
				if(candidateSearchTO.getParentPhoneDisp() != null && candidateSearchTO.getParentPhoneDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getParentPhone()!=null){
					cell = row.createCell((short)candidateSearchTO.getParentPhonePois());
					cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(candidateSearch.getParentPhone());
				}
				if(candidateSearchTO.getParentMobileNoDisp() != null && candidateSearchTO.getParentMobileNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getParentMobileNo()!=null){
					cell = row.createCell((short)candidateSearchTO.getParentMobileNoPois());
					cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(candidateSearch.getParentMobileNo());
				}
				if(candidateSearchTO.getApplnNoDisp() != null && candidateSearchTO.getApplnNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getApplnNo()!= 0){
					row.createCell((short)candidateSearchTO.getApplnNoPois()).setCellValue(candidateSearch.getApplnNo());
				}
				if(candidateSearchTO.getNameDisp() != null && candidateSearchTO.getNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getName()!= null){
					row.createCell((short)candidateSearchTO.getNamePois()).setCellValue(candidateSearch.getName());
				}
				if(candidateSearchTO.getDateOfBirthDisp() != null && candidateSearchTO.getDateOfBirthDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getDateOfBirth()!= null){
				    cell = row.createCell((short)candidateSearchTO.getDateOfBirthPois());
				    cell.setCellValue(candidateSearch.getDateOfBirth());
				    cell.setCellStyle(cellStyle);
				}
				if(candidateSearchTO.getGenderDisp() != null && candidateSearchTO.getGenderDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getGender()!= null){
					row.createCell((short)candidateSearchTO.getGenderPois()).setCellValue(candidateSearch.getGender());
				}
				if(candidateSearchTO.getChallanNoDisp() != null && candidateSearchTO.getChallanNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getChallanNo()!= null){
					row.createCell((short)candidateSearchTO.getChallanNoPois()).setCellValue(candidateSearch.getChallanNo());
				}
				if(candidateSearchTO.getTotalWeightageDisp() != null && candidateSearchTO.getTotalWeightageDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getTotalWeightage()!= 0){
					row.createCell((short)candidateSearchTO.getTotalWeightagePois()).setCellValue(candidateSearch.getTotalWeightage());
				}
				if(candidateSearchTO.getEmailDisp() != null && candidateSearchTO.getEmailDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getEmail()!= null){
					row.createCell((short)candidateSearchTO.getEmailPois()).setCellValue(candidateSearch.getEmail());
				}
				if(candidateSearchTO.getBloodGroupDisp() != null && candidateSearchTO.getBloodGroupDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getBloodGroup()!= null){
					row.createCell((short)candidateSearchTO.getBloodGroupPois()).setCellValue(candidateSearch.getBloodGroup());
				}
				if(candidateSearchTO.getSeatNoDisp() != null && candidateSearchTO.getSeatNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getSeatNo()!= null){
					row.createCell((short)candidateSearchTO.getSeatNoPois()).setCellValue(candidateSearch.getSeatNo());
				}
				if(candidateSearchTO.getExamCenterDisp() != null && candidateSearchTO.getExamCenterDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getExamCenter()!= null){
					row.createCell((short)candidateSearchTO.getExamCenterPois()).setCellValue(candidateSearch.getExamCenter());
				}	
				if(candidateSearchTO.getMotherTongueDisp() != null && candidateSearchTO.getMotherTongueDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getMotherTongue()!= null){
					row.createCell((short)candidateSearchTO.getMotherTonguePois()).setCellValue(candidateSearch.getMotherTongue());
				}	
				if(candidateSearchTO.getAdmissionStatusDisp()!= null && candidateSearchTO.getAdmissionStatusDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getAdmissionStatus()!= null){
					row.createCell((short)candidateSearchTO.getAdmissionStatusPois()).setCellValue(candidateSearch.getAdmissionStatus());
				}
				if(candidateSearchTO.getIsAdmittedDisp()!= null && candidateSearchTO.getIsAdmittedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getIsAdmitted()!= null){
					row.createCell((short)candidateSearchTO.getIsAdmittedPois()).setCellValue(candidateSearch.getIsAdmitted());
				}
				if(candidateSearchTO.getCurrentClassDisp()!= null && candidateSearchTO.getCurrentClassDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getCurrentClass()!= null){
					row.createCell((short)candidateSearchTO.getCurrentClassPois()).setCellValue(candidateSearch.getCurrentClass());
				}
				if(candidateSearchTO.getIsSelectedDisp()!=null && candidateSearchTO.getIsSelectedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
					row.createCell((short)candidateSearchTO.getIsSelectedPois()).setCellValue(candidateSearch.getIsSelected());
				}
				if(candidateSearchTO.getIsFinalMeritApprovedDisp()!=null && candidateSearchTO.getIsFinalMeritApprovedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
					row.createCell((short)candidateSearchTO.getIsFinalMeritApprovedPois()).setCellValue(candidateSearch.getIsFinalMeritApproved());
				}
				if(candidateSearchTO.getUserNameDisp()!=null && candidateSearchTO.getUserNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
					row.createCell((short)candidateSearchTO.getUserNamePois()).setCellValue(candidateSearch.getUserName());
				}
				if(candidateSearchTO.getPasswordDisp()!=null && candidateSearchTO.getPasswordDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
					row.createCell((short)candidateSearchTO.getPasswordPois()).setCellValue(candidateSearch.getPassword());
				}
				//
				//Mary Addition 
				
				if(candidateSearchTO.getAppliedDateDisp() != null && candidateSearchTO.getAppliedDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)&& candidateSearch.getAppliedDate()!= null){
					row.createCell((short)candidateSearchTO.getAppliedDatePois()).setCellValue(candidateSearch.getAppliedDate());
				}
				if(candidateSearchTO.getChallanPaymentDateDisp() != null && candidateSearchTO.getChallanPaymentDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)&& candidateSearch.getChallanPaymentDate()!= null){
					row.createCell((short)candidateSearchTO.getChallanPaymentDatePois()).setCellValue(candidateSearch.getChallanPaymentDate());
				}
				if(candidateSearchTO.getFeeChallanDateDisp() != null && candidateSearchTO.getFeeChallanDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getChallanDate()!= null) {
					row.createCell((short)candidateSearchTO.getFeeChallanDatePois()).setCellValue(candidateSearch.getChallanDate());
				}
				if(candidateSearchTO.getFeeChallanNoDisp() != null && candidateSearchTO.getFeeChallanNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)&& candidateSearch.getChallanNo()!= null){
					row.createCell((short)candidateSearchTO.getFeeChallanNoPois()).setCellValue(candidateSearch.getChallanNo());
				}
				if(candidateSearchTO.getTotalFeePaidDisp() != null && candidateSearchTO.getTotalFeePaidDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getTotalFeePaid()!= null){
					row.createCell((short)candidateSearchTO.getTotalFeePaidPois()).setCellValue(candidateSearch.getTotalFeePaid());
				}
				if(candidateSearchTO.getIsHandicapedDisp()!= null && candidateSearchTO.getIsHandicapedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getIsHandicaped()!= null){
					row.createCell((short)candidateSearchTO.getIsHandicapedPois()).setCellValue(candidateSearch.getIsHandicaped());
				}
				if(candidateSearchTO.getHandicapDetailsDisp()!= null && candidateSearchTO.getHandicapDetailsDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getHandicapDetails()!= null ) {
					row.createCell((short)candidateSearchTO.getHandicapDetailsPois()).setCellValue(candidateSearch.getHandicapDetails());
				}
				
				if(candidateSearchTO.getTotalExpYearDisp()!= null && candidateSearchTO.getTotalExpYearDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getTotalExpYear()!= null ) {
					row.createCell((short)candidateSearchTO.getTotalExpYearPois()).setCellValue(candidateSearch.getTotalExpYear());
				}
				//ends
				//added for challan verification display
				if(candidateSearchTO.getChallanVerfiedDisp()!= null && candidateSearchTO.getChallanVerfiedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getIsChallanVerified()!= null ) {
					row.createCell((short)candidateSearchTO.getChallanVerifiedPois()).setCellValue(candidateSearch.getIsChallanVerified());
				}
				//addition for challan verification completed
				
				//added for photo status display
				if(candidateSearchTO.getPhotoDisp()!= null && candidateSearchTO.getPhotoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getPhoto()!= null ) {
					row.createCell((short)candidateSearchTO.getPhotoPois()).setCellValue(candidateSearch.getPhoto());
				}
				//addition for photo status completed
				// added for specialization prefered and backlogs display
				if(candidateSearchTO.getSpecializationPreferedDisp()!= null && candidateSearchTO.getSpecializationPreferedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getSpecializationPrefered()!= null ) {
					row.createCell((short)candidateSearchTO.getSpecializationPreferedPois()).setCellValue(candidateSearch.getSpecializationPrefered());
				}
				if(candidateSearchTO.getBackLogsDisp()!= null && candidateSearchTO.getBackLogsDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getBackLogs()!= null ) {
					row.createCell((short)candidateSearchTO.getBackLogsPois()).setCellValue(candidateSearch.getBackLogs());
				}
				if(candidateSearchTO.getNewbackLogsDisp()!= null && candidateSearchTO.getNewbackLogsDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getNewbackLogs()!= null ) {
					row.createCell((short)candidateSearchTO.getNewbackLogsPois()).setCellValue(candidateSearch.getNewbackLogs());
				}
				if(candidateSearchTO.getCanceledDisp()!= null && candidateSearchTO.getCanceledDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getCanceled()!= null ) {
					row.createCell((short)candidateSearchTO.getCanceledPois()).setCellValue(candidateSearch.getCanceled());
				}
				// newly added by sudhir
				if(candidateSearchTO.getApplicantFeedbackDisp()!=null && candidateSearchTO.getApplicantFeedbackDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getApplicantFeedback()!=null){
					row.createCell((short)candidateSearchTO.getApplicantFeedbackPois()).setCellValue(candidateSearch.getApplicantFeedback());
				}
				
				//Added by Cimi for University Email
				if(candidateSearchTO.getUniversityEmailDisp()!=null && candidateSearchTO.getUniversityEmailDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getUniversityEmail()!=null){
					row.createCell((short)candidateSearchTO.getUniversityEmailPois()).setCellValue(candidateSearch.getUniversityEmail());
				}
				// added by chandra
				if(candidateSearchTO.getComedkDisp()!= null && candidateSearchTO.getComedkDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getComedk()!= null ) {
					row.createCell((short)candidateSearchTO.getComedkPois()).setCellValue(candidateSearch.getComedk());
				}
				// added by Manu
				if(candidateSearchTO.getRemarksDisp()!= null && candidateSearchTO.getRemarksDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getRemarks()!= null ) {
					row.createCell((short)candidateSearchTO.getRemarkPois()).setCellValue(candidateSearch.getRemarks());
				}
				//by giri
				if(candidateSearchTO.getPhotoUploadedDisp()!= null && candidateSearchTO.getPhotoUploadedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getPhotoUploaded()!= null ) {
					row.createCell((short)candidateSearchTO.getPhotoUploadedPois()).setCellValue(candidateSearch.getPhotoUploaded());
				}
				//end by giri
				// added by Mary
				if(candidateSearchTO.getSelectionProcessDateDisp()!= null && candidateSearchTO.getSelectionProcessDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getSelectionProcessDate()!= null ) {
					row.createCell((short)candidateSearchTO.getSelectionProcessDatePois()).setCellValue(candidateSearch.getSelectionProcessDate());
				}
				
				short counter1 = 0;
				
				if(candidateSearchTO.isDispStatus()){
					row.createCell((short) (maxNo + counter1) ).setCellValue(candidateSearch.getAdmStatus());
					counter1 = (short) (counter1 +1);
					row.createCell((short) (maxNo + counter1) ).setCellValue("");
					counter1 = (short) (counter1 +1);
				}
				
				if(candidateSearchTO.getInterviewResultDisp() != null && candidateSearchTO.getInterviewResultDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
					TreeMap<String, String> gradeMap = candidateSearch.getGradeMap();
					if(gradeMap!= null && gradeMap.size() > 0){
						Iterator<String> grItr = gradeMap.keySet().iterator();
						while (grItr.hasNext()) {
							row.createCell((short) (maxNo + counter1) ).setCellValue(gradeMap.get(grItr.next()));
							counter1++;
						}
					}else{   //modified by priyatham --start--
						for(int i=0;i<interviewResultDispCount;i++){
							row.createCell((short) (maxNo + counter1) ).setCellValue(" ");
							counter1++;
						}
					}//--end--
				}
// added for comments display
				if(candidateSearchTO.getInterviewResultDisp() != null && candidateSearchTO.getInterviewResultDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
					TreeMap<String, String> commentMap = candidateSearch.getCommentsMap();
					if(commentMap!= null && commentMap.size() > 0){
						Iterator<String> comItr = commentMap.keySet().iterator();
						while (comItr.hasNext()) {
							row.createCell((short) (maxNo + counter1) ).setCellValue(commentMap.get(comItr.next()));
							counter1++;
						}
					}else{   //modified by priyatham --start--
						for(int i=0;i<interviewResultCommentDispCount;i++){
							row.createCell((short) (maxNo + counter1) ).setCellValue(" ");
							counter1++;
						}
					}//--end--
				}
				
				
				
				if(candidateSearchTO.getPrvExamDetDisp() != null && candidateSearchTO.getPrvExamDetDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
					TreeMap<String, String> perMap = candidateSearch.getDocTypePerMap();
					if(perMap!= null && perMap.size() > 0){
						Iterator<String> grItr = perMap.keySet().iterator();
						while (grItr.hasNext()) {
							row.createCell((short) (maxNo + counter1) ).setCellValue(perMap.get(grItr.next()));
							counter1++;
							
						}
					}else{   //modified by priyatham --start--
						for(int i=0;i<prvExamDetDispCounter;i++){
							row.createCell((short) (maxNo + counter1) ).setCellValue(" ");
							counter1++;
						}
					}//--end--
					TreeMap<String, String> examMap = candidateSearch.getDocTypeExamNameMap();
					if(examMap!= null && examMap.size() > 0){
						Iterator<String> grItr = examMap.keySet().iterator();
						while (grItr.hasNext()) {
							row.createCell((short) (maxNo + counter1) ).setCellValue(examMap.get(grItr.next()));
							counter1++;
							
						}
					}else{   //modified by priyatham --start--
						for(int i=0;i<prvExamDetDispCounter;i++){
							row.createCell((short) (maxNo + counter1) ).setCellValue(" ");
							counter1++;
						}
					}//--end--		
					TreeMap<String, String> uniMap = candidateSearch.getUniMap();
					if(uniMap!= null && uniMap.size() > 0){
						Iterator<String> uItr = uniMap.keySet().iterator();
						while (uItr.hasNext()) {
							row.createCell((short) (maxNo + counter1) ).setCellValue(uniMap.get(uItr.next()));
							counter1++;
							
						}
					}else{   //modified by priyatham --start--
						for(int i=0;i<prvExamDetDispCounter;i++){
							row.createCell((short) (maxNo + counter1) ).setCellValue(" ");
							counter1++;
						}
					}//--end--
					TreeMap<String, String> instMap = candidateSearch.getInstMap();
					if(instMap!= null && instMap.size() > 0){
						Iterator<String> iItr = instMap.keySet().iterator();
						while (iItr.hasNext()) {
							row.createCell((short) (maxNo + counter1) ).setCellValue(instMap.get(iItr.next()));
							counter1++;
						}
					}else{   //modified by priyatham --start--
						for(int i=0;i<prvExamDetDispCounter;i++){
							row.createCell((short) (maxNo + counter1) ).setCellValue(" ");
							counter1++;
						}
					}//--end--
					TreeMap<String, String> yearMap = candidateSearch.getYearMap();
					if(yearMap!= null && yearMap.size() > 0){
						Iterator<String> yItr = yearMap.keySet().iterator();
						while (yItr.hasNext()) {
							row.createCell((short) (maxNo + counter1) ).setCellValue(yearMap.get(yItr.next()));
							counter1++;
							
						}
					}else{   //modified by priyatham --start--
						for(int i=0;i<prvExamDetDispCounter;i++){
							row.createCell((short) (maxNo + counter1) ).setCellValue(" ");
							counter1++;
						}
					}//--end--	
				}

				if(candidateSearchTO.getIntDateTimeDisp() != null && candidateSearchTO.getIntDateTimeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
					TreeMap<String, String> intDateMap = candidateSearch.getIntDateMap();
					if(intDateMap!= null && intDateMap.size() > 0){
						Iterator<String> iItr = intDateMap.keySet().iterator();
						while (iItr.hasNext()) {
							row.createCell((short) (maxNo + counter1) ).setCellValue(intDateMap.get(iItr.next()));
							counter1++;
						}
					}else{   //modified by priyatham --start--
						for(int i=0;i<dateTimeDispCount;i++){
							row.createCell((short) (maxNo + counter1) ).setCellValue(" ");
							counter1++;
						}
					}//--end--		
					
					TreeMap<String, String> intTimeMap = candidateSearch.getIntTimeMap();
					if(intTimeMap!= null && intTimeMap.size() > 0){
						Iterator<String> iItr = intTimeMap.keySet().iterator();
						while (iItr.hasNext()) {
							row.createCell((short) (maxNo + counter1) ).setCellValue(intTimeMap.get(iItr.next()));
							counter1++;
							
						}
					}else{   //modified by priyatham --start--
						for(int i=0;i<dateTimeDispCount;i++){
							row.createCell((short) (maxNo + counter1) ).setCellValue(" ");
							counter1++;
						}
					}//--end--		
				}
				
				if(candidateSearchTO.getPreReqDisp() != null && candidateSearchTO.getPreReqDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
					TreeMap<String, String> pObMarMap = candidateSearch.getpExamMap();
					if(pObMarMap!= null && pObMarMap.size() > 0){
						Iterator<String> grItr = pObMarMap.keySet().iterator();
						while (grItr.hasNext()) {
							row.createCell((short) (maxNo + counter1) ).setCellValue(pObMarMap.get( grItr.next()));
							counter1++;
							
						}
					}else{   //modified by priyatham --start--
						for(int i=0;i<preReqDispCount;i++){
							row.createCell((short) (maxNo + counter1) ).setCellValue(" ");
							counter1++;
						}
					}//--end--			
					TreeMap<String, String> pRollMap = candidateSearch.getpRollMap();
					if(pRollMap!= null && pRollMap.size() > 0){
						Iterator<String> uItr = pRollMap.keySet().iterator();
						while (uItr.hasNext()) {
							row.createCell((short) (maxNo + counter1) ).setCellValue(pRollMap.get(uItr.next()));
							counter1++;
							
						}
					}else{   //modified by priyatham --start--
						for(int i=0;i<preReqDispCount;i++){
							row.createCell((short) (maxNo + counter1) ).setCellValue(" ");
							counter1++;
						}
					}//--end--
					TreeMap<String, String> mopMap = candidateSearch.getpMopMap();
					if(mopMap!= null && mopMap.size() > 0){
						Iterator<String> iItr = mopMap.keySet().iterator();
						while (iItr.hasNext()) {
							row.createCell((short) (maxNo + counter1) ).setCellValue(mopMap.get(iItr.next()));
							counter1++;
							
						}
					}else{   //modified by priyatham --start--
						for(int i=0;i<preReqDispCount;i++){
							row.createCell((short) (maxNo + counter1) ).setCellValue(" ");
							counter1++;
						}
					}//--end--
					if(candidateSearch.getpYopMap()!= null && candidateSearch.getpYopMap().size() > 0){
						Iterator<String> yItr = candidateSearch.getpYopMap().values().iterator();
						while (yItr.hasNext()) {
							row.createCell((short) (maxNo + counter1) ).setCellValue(yItr.next());
							counter1++;
						
						}
					}else{   //modified by priyatham --start--
						for(int i=0;i<preReqDispCount;i++){
							row.createCell((short) (maxNo + counter1) ).setCellValue(" ");
							counter1++;
						}
					}//--end--	
					
				}
//			}// remove this
			}
			
			/*fos = new FileOutputStream(excelFile,true);
			wb.write(fos);*/
			bos=new ByteArrayOutputStream();
			wb.write(bos);
			HttpSession session = request.getSession();
			
		
			session.setAttribute(CMSConstants.EXCEL_BYTES,bos.toByteArray());
//			session.setAttribute(CMSConstants.EXCEL_BYTES,wb.getBytes());
			fos.flush();
			fos.close();
		}catch(Exception e){
			//log

		}
		}
	}  
	
	
	public static void exportTOCSV(List candidateSearchTOList,CandidateSearchTO candidateSearchTO ,HttpServletRequest request) throws Exception{
		// File test = null;
		int interviewResultDispCount=0;
		int interviewResultCommentDispCount=0;
		int preReqDispCount=0;
		int prvExamDetDispCounter=0;
		int dateTimeDispCount=0;
		Properties prop = new Properties();
		try {
			InputStream inputStream = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inputStream);
		} 
		catch (IOException e) {
			log.error("Error occured at exportTOExcel of CandidateHelper ",e);
			throw new IOException(e);
		}
		String destination=prop.getProperty(CMSConstants.EXCEL_DESTINATION);
		File excelFile = new File(request.getRealPath("")+ "//TempFiles//"+destination);	
		if(excelFile.exists()){
			excelFile.delete();
		}
//		String contentType = response.getContentType();
//		String fileName = "AdmissionReport.xls";
		FileOutputStream fos = null;
		HSSFWorkbook wb=null;
		HSSFSheet sheet=null;
		HSSFRow row=null;
		HSSFCell cell=null;
		/*XSSFWorkbook wb = null;
		XSSFSheet sheet =null;
		XSSFRow row = null;
		XSSFCell cell = null;*/
		if(candidateSearchTOList!=null){
			CandidateSearchTO headigSearchTO = (CandidateSearchTO) candidateSearchTOList.get(0);
		int count = 0;
		Iterator iterator = candidateSearchTOList.iterator();
		
		try	{
			wb=new HSSFWorkbook();
//			wb = new XSSFWorkbook();
			HSSFCellStyle cellStyle=wb.createCellStyle();
//			XSSFCellStyle cellStyle = wb.createCellStyle();
//			CreationHelper createHelper = wb.getCreationHelper();
//			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
			cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("dd/MM/yyyy"));
			sheet = wb.createSheet("Admission Report");
			row = sheet.createRow(count);
			count = sheet.getFirstRowNum();
			// Create cells in the row and put some data in it.
			
			//Added By Mary
			
			if(candidateSearchTO.getSelectionProcessDateDisp() != null && candidateSearchTO.getSelectionProcessDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getSelectionProcessDatePois()).setCellValue(CMSConstants.ADMISSION_SELECTION_PROCESS_DATE);
			}
			//Added By Mary--ends

			if(candidateSearchTO.getRegNoDisp() != null && candidateSearchTO.getRegNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getRegNoPois()).setCellValue(CMSConstants.REGNO);
			}
			if(candidateSearchTO.getRollNoDisp() != null && candidateSearchTO.getRollNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getRollNoPois()).setCellValue(CMSConstants.ROLLNO);
			}
			if(candidateSearchTO.getClassNameDisp() != null && candidateSearchTO.getClassNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getClassNamePois()).setCellValue(CMSConstants.CLASS_NAME);
			}
			if(candidateSearchTO.getAdmissionDateDisp() != null && candidateSearchTO.getAdmissionDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getAdmissionDatePois()).setCellValue(CMSConstants.ADMISSION_DATE);
			}
			if(candidateSearchTO.getSecondLanguageDisp() != null && candidateSearchTO.getSecondLanguageDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getSecondLanguagePois()).setCellValue(CMSConstants.SECOND_LANGUAG);
			}
			if(candidateSearchTO.getProgramTypeNameDisp() != null && candidateSearchTO.getProgramTypeNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getProgramTypeNamePois()).setCellValue(CMSConstants.PROGTYPE_NAME);
			}
			if(candidateSearchTO.getProgramNameDisp() != null && candidateSearchTO.getProgramNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getProgramNamePois()).setCellValue(CMSConstants.PROG_NAME);
			}
			if(candidateSearchTO.getCourseNameDisp() != null && candidateSearchTO.getCourseNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getCourseNamePois()).setCellValue(CMSConstants.COURSE_NAME);
			}
			if(candidateSearchTO.getJournalNoDisp() != null && candidateSearchTO.getJournalNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getJournalNoPois()).setCellValue(CMSConstants.JOURNAL_NO);
			}
			if(candidateSearchTO.getAcedamicYearDisp() != null && candidateSearchTO.getAcedamicYearDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getAcedamicYearPois()).setCellValue(CMSConstants.ACEDAMIC_YEAR);
			}
			if(candidateSearchTO.getAdmittedThroughDisp() != null && candidateSearchTO.getAdmittedThroughDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getAdmittedThroughPois()).setCellValue(CMSConstants.ADMITTED_THROUG);
			}
			if(candidateSearchTO.getResidentCategoryDisp() != null && candidateSearchTO.getResidentCategoryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getResidentCategoryPois()).setCellValue(CMSConstants.RESIDENT_CATEGORY);
			}
			if(candidateSearchTO.getReligionDisp() != null && candidateSearchTO.getReligionDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getReligionPois()).setCellValue(CMSConstants.RELIGIN);
			}
			if(candidateSearchTO.getReligionOtherDisp() != null && candidateSearchTO.getReligionOtherDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getReligionOtherPois()).setCellValue(CMSConstants.RELIGION_OTHER);
			}
			if(candidateSearchTO.getSubReligionDisp() != null && candidateSearchTO.getSubReligionDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getSubReligionPois()).setCellValue(CMSConstants.SUB_RELIGION);
			}
			if(candidateSearchTO.getSubReligionOtherDisp() != null && candidateSearchTO.getSubReligionOtherDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getSubReligionOtherPois()).setCellValue(CMSConstants.SUB_RELIGION_OTHER);
			}
			if(candidateSearchTO.getCastCategoryDisp() != null && candidateSearchTO.getCastCategoryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getCastCategoryPois()).setCellValue(CMSConstants.CAST_CATEGORY);
			}
			if(candidateSearchTO.getCastCategoryOtherDisp() != null && candidateSearchTO.getCastCategoryOtherDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getCastCategoryOtherPois()).setCellValue(CMSConstants.CAST_CATEGORY_OTHER);
			}
			if(candidateSearchTO.getPlaceOfBirthDisp() != null && candidateSearchTO.getPlaceOfBirthDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getPlaceOfBirthPois()).setCellValue(CMSConstants.PLACE_OF_BIRTH);
			}
			if(candidateSearchTO.getStateOfBirthDisp() != null && candidateSearchTO.getStateOfBirthDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getStateOfBirthPois()).setCellValue(CMSConstants.STATE_OF_BIRTH);
			}
			if(candidateSearchTO.getStateOfBirthOtherDisp() != null && candidateSearchTO.getStateOfBirthOtherDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getStateOfBirthOtherPois()).setCellValue(CMSConstants.STATE_OF_BIRTH_OTHER);
			}
			if(candidateSearchTO.getCountryOfBirthDisp() != null && candidateSearchTO.getCountryOfBirthDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getCountryOfBirthPois()).setCellValue(CMSConstants.COUNTRY_OF_BIRTH);
			}
			if(candidateSearchTO.getBelongsToDisp() != null && candidateSearchTO.getBelongsToDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getBelongsToPois()).setCellValue(CMSConstants.BELONGS_TO);
			}
			if(candidateSearchTO.getNationalityDisp() != null && candidateSearchTO.getNationalityDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getNationalityPois()).setCellValue(CMSConstants.NATIONALIT);
			}
			if(candidateSearchTO.getStudentPhoneNumberDisp() != null && candidateSearchTO.getStudentPhoneNumberDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getStudentPhoneNumberPois()).setCellValue(CMSConstants.STUDENT_PHONE_NUMBER);
			}
			if(candidateSearchTO.getStudentMobileNumberDisp() != null && candidateSearchTO.getStudentMobileNumberDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getStudentMobileNumberPois()).setCellValue(CMSConstants.STUDENT_MOBILE_NUMBER);
			}
			if(candidateSearchTO.getPassportNumberDisp() != null && candidateSearchTO.getPassportNumberDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getPassportNumberPois()).setCellValue(CMSConstants.PASSPORT_NUMBER);
			}
			if(candidateSearchTO.getPassportIssuingCountryDisp() != null && candidateSearchTO.getPassportIssuingCountryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getPassportIssuingCountryPois()).setCellValue(CMSConstants.PASSPORT_ISSUING_COUNTRY);
			}
			if(candidateSearchTO.getPassportValidUpToDisp() != null && candidateSearchTO.getPassportValidUpToDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getPassportValidUpToPois()).setCellValue(CMSConstants.PASSPORT_VALIDUPTO);
			}
			if(candidateSearchTO.getFirstPreferenceProgramTypeDisp() != null && candidateSearchTO.getFirstPreferenceProgramTypeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFirstPreferenceProgramTypePois()).setCellValue(CMSConstants.FIRST_PRE_PROGTYPE);
			}
			if(candidateSearchTO.getFirstPreferenceProgramDisp() != null && candidateSearchTO.getFirstPreferenceProgramDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFirstPreferenceProgramPois()).setCellValue(CMSConstants.FIRST_PRE_PROG);
			}
			if(candidateSearchTO.getFirstPreferenceCourseDisp() != null && candidateSearchTO.getFirstPreferenceCourseDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFirstPreferenceCoursePois()).setCellValue(CMSConstants.FIRST_PRE_COURSE);
			}
			if(candidateSearchTO.getSecondPreferenceProgramTypeDisp() != null && candidateSearchTO.getSecondPreferenceProgramTypeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getSecondPreferenceProgramTypePois()).setCellValue(CMSConstants.SECOND_PRE_PROGTYPE);
			}
			if(candidateSearchTO.getSecondPreferenceProgramDisp() != null && candidateSearchTO.getSecondPreferenceProgramDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getSecondPreferenceProgramPois()).setCellValue(CMSConstants.SECOND_PRE_PROG);
			}
			if(candidateSearchTO.getSecondPreferenceCourseDisp() != null && candidateSearchTO.getSecondPreferenceCourseDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getSecondPreferenceCoursePois()).setCellValue(CMSConstants.SECOND_PRE_COURSE);
			}
			if(candidateSearchTO.getThirdPreferenceProgramTypeDisp() != null && candidateSearchTO.getThirdPreferenceProgramTypeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getThirdPreferenceProgramTypePois()).setCellValue(CMSConstants.THIRD_PRE_PROGTYPE);
			}
			if(candidateSearchTO.getThirdPreferenceProgramDisp() != null && candidateSearchTO.getThirdPreferenceProgramDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getThirdPreferenceProgramPois()).setCellValue(CMSConstants.THIRD_PRE_PROG);
			}
			if(candidateSearchTO.getThirdPreferenceCourseDisp() != null && candidateSearchTO.getThirdPreferenceCourseDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getThirdPreferenceCoursePois()).setCellValue(CMSConstants.THIRD_PRE_COURSE);
			}
			if(candidateSearchTO.getFirstNameofOrganisationDisp() != null && candidateSearchTO.getFirstNameofOrganisationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFirstNameofOrganisationPois()).setCellValue(CMSConstants.FIRST_NAME_OF_ORG);
			}
			if(candidateSearchTO.getFirstDesignationDisp() != null && candidateSearchTO.getFirstDesignationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFirstDesignationPois()).setCellValue(CMSConstants.FIRST_DESIG);
			}
			if(candidateSearchTO.getFirstFromDateDisp() != null && candidateSearchTO.getFirstFromDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFirstFromDatePois()).setCellValue(CMSConstants.FIRST_FROM_DATE);
			}
			if(candidateSearchTO.getFirstToDateDisp() != null && candidateSearchTO.getFirstToDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFirstToDatePois()).setCellValue(CMSConstants.FIRST_TO_DATE);
			}
			if(candidateSearchTO.getSecondNameofOrganisationDisp() != null && candidateSearchTO.getSecondNameofOrganisationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getSecondNameofOrganisationPois()).setCellValue(CMSConstants.SECOND_NAME_OF_ORG);
			}
			if(candidateSearchTO.getSecondDesignationDisp() != null && candidateSearchTO.getSecondDesignationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getSecondDesignationPois()).setCellValue(CMSConstants.SECOND_DESIG);
			}
			if(candidateSearchTO.getSecondFromDateDisp() != null && candidateSearchTO.getSecondFromDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getSecondFromDatePois()).setCellValue(CMSConstants.SECOND_FROM_DATE);
			}
			if(candidateSearchTO.getSecondToDateDisp() != null && candidateSearchTO.getSecondToDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getSecondToDatePois()).setCellValue(CMSConstants.SECOND_TO_DATE);
			}
			if(candidateSearchTO.getThirdNameofOrganisationDisp() != null && candidateSearchTO.getThirdNameofOrganisationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getThirdNameofOrganisationPois()).setCellValue(CMSConstants.THIRD_NAME_OF_ORG);
			}
			if(candidateSearchTO.getThirdDesignationDisp() != null && candidateSearchTO.getThirdDesignationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getThirdDesignationPois()).setCellValue(CMSConstants.THIRD_DESIG);
			}
			if(candidateSearchTO.getThirdFromDateDisp() != null && candidateSearchTO.getThirdFromDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getThirdFromDatePois()).setCellValue(CMSConstants.THIRD_FROM_DATE);
			}
			if(candidateSearchTO.getThirdToDateDisp() != null && candidateSearchTO.getThirdToDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getThirdToDatePois()).setCellValue(CMSConstants.THIRD_TO_DATE);
			}
			if(candidateSearchTO.getPermanentAddressLine1Disp() != null && candidateSearchTO.getPermanentAddressLine1Disp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getPermanentAddressLine1Pois()).setCellValue(CMSConstants.PERMANENT_ADDR_LINE1);
			}
			if(candidateSearchTO.getPermanentAddressLine2Disp() != null && candidateSearchTO.getPermanentAddressLine2Disp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getPermanentAddressLine2Pois()).setCellValue(CMSConstants.PERMANENT_ADDR_LINE2);
			}
			if(candidateSearchTO.getPermanentStateDisp() != null && candidateSearchTO.getPermanentStateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getPermanentStatePois()).setCellValue(CMSConstants.PERMANENT_STATE);
			}
			if(candidateSearchTO.getPermanentStateOthersDisp() != null && candidateSearchTO.getPermanentStateOthersDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getPermanentStateOthersPois()).setCellValue(CMSConstants.PERMANENT_STATE_OTHERS);
			}
			if(candidateSearchTO.getPermanentCityDisp() != null && candidateSearchTO.getPermanentCityDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getPermanentCityPois()).setCellValue(CMSConstants.PERMANENT_CITY);
			}
			if(candidateSearchTO.getPermanentCountryDisp() != null && candidateSearchTO.getPermanentCountryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getPermanentCountryPois()).setCellValue(CMSConstants.PERMANENT_COUNTRY);
			}
			if(candidateSearchTO.getPermanentZipCodeDisp() != null && candidateSearchTO.getPermanentZipCodeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getPermanentZipCodePois()).setCellValue(CMSConstants.PERMANENT_ZIP_CODE);
			}
			if(candidateSearchTO.getCurrentAddressLine1Disp() != null && candidateSearchTO.getCurrentAddressLine1Disp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getCurrentAddressLine1Pois()).setCellValue(CMSConstants.CURRENT_ADDR_LINE1);
			}
			if(candidateSearchTO.getCurrentAddressLine2Disp() != null && candidateSearchTO.getCurrentAddressLine2Disp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getCurrentAddressLine2Pois()).setCellValue(CMSConstants.CURRENT_ADDR_LINE2);
			}
			if(candidateSearchTO.getCurrentStateDisp() != null && candidateSearchTO.getCurrentStateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getCurrentStatePois()).setCellValue(CMSConstants.CURRENT_STATE);
			}
			if(candidateSearchTO.getCurrentStateOthersDisp() != null && candidateSearchTO.getCurrentStateOthersDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getCurrentStateOthersPois()).setCellValue(CMSConstants.CURRENT_STATE_OTHERS);
			}
			if(candidateSearchTO.getCurrentCityDisp() != null && candidateSearchTO.getCurrentCityDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getCurrentCityPois()).setCellValue(CMSConstants.CURRENT_CITY);
			}
			if(candidateSearchTO.getCurrentCountryDisp() != null && candidateSearchTO.getCurrentCountryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getCurrentCountryPois()).setCellValue(CMSConstants.CURRENT_COUNTRY);
			}
			if(candidateSearchTO.getCurrentZipCodeDisp() != null && candidateSearchTO.getCurrentZipCodeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getCurrentZipCodePois()).setCellValue(CMSConstants.CURRENT_ZIP_CODE);
			}
			if(candidateSearchTO.getFathersNameDisp() != null && candidateSearchTO.getFathersNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFathersNamePois()).setCellValue(CMSConstants.FATHERS_NAME);
			}
			if(candidateSearchTO.getFathersEducationDisp() != null && candidateSearchTO.getFathersEducationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFathersEducationPois()).setCellValue(CMSConstants.FATHERS_EDUCATION);
			}
			if(candidateSearchTO.getFathersIncomeDisp() != null && candidateSearchTO.getFathersIncomeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFathersIncomePois()).setCellValue(CMSConstants.FATHERS_INCOME);
			}
			if(candidateSearchTO.getFathersCurrencyDisp() != null && candidateSearchTO.getFathersCurrencyDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFathersCurrencyPois()).setCellValue(CMSConstants.FATHERS_CURRENCY);
			}
			if(candidateSearchTO.getFathersOccupationDisp() != null && candidateSearchTO.getFathersOccupationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFathersOccupationPois()).setCellValue(CMSConstants.FATHERS_OCCUPATION);
			}
			if(candidateSearchTO.getFathersEmailDisp() != null && candidateSearchTO.getFathersEmailDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFathersEmailPois()).setCellValue(CMSConstants.FATHERS_EMAIL);
			}
			if(candidateSearchTO.getMothersNameDisp() != null && candidateSearchTO.getMothersNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getMothersNamePois()).setCellValue(CMSConstants.MOTHERS_NAME);
			}
			if(candidateSearchTO.getMothersEducationDisp() != null && candidateSearchTO.getMothersEducationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getMothersEducationPois()).setCellValue(CMSConstants.MOTHERS_EDUCATION);
			}
			if(candidateSearchTO.getMothersIncomeDisp() != null && candidateSearchTO.getMothersIncomeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getMothersIncomePois()).setCellValue(CMSConstants.MOTHERS_INCOME);
			}
			if(candidateSearchTO.getMothersCurrencyDisp() != null && candidateSearchTO.getMothersCurrencyDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getMothersCurrencyPois()).setCellValue(CMSConstants.MOTHERS_CURRENCY);
			}
			if(candidateSearchTO.getMothersOccupationDisp() != null && candidateSearchTO.getMothersOccupationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getMothersOccupationPois()).setCellValue(CMSConstants.MOTHERS_OCCUPATION);
			}
			if(candidateSearchTO.getMothersEmailDisp() != null && candidateSearchTO.getMothersEmailDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getMothersEmailPois()).setCellValue(CMSConstants.MOTHERS_EMAIL);
			}
			if(candidateSearchTO.getParentAdressLine1Disp() != null && candidateSearchTO.getParentAdressLine1Disp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getParentAdressLine1Pois()).setCellValue(CMSConstants.PARENT_ADDR_LINE1);
			}
			if(candidateSearchTO.getParentAdressLine2Disp() != null && candidateSearchTO.getParentAdressLine2Disp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getParentAdressLine2Pois()).setCellValue(CMSConstants.PARENT_ADDR_LINE2);
			}
			if(candidateSearchTO.getParentAdressLine3Disp() != null && candidateSearchTO.getParentAdressLine3Disp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getParentAdressLine3Pois()).setCellValue(CMSConstants.PARENT_ADDR_LINE3);
			}
			if(candidateSearchTO.getParentCityDisp() != null && candidateSearchTO.getParentCityDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getParentCityPois()).setCellValue(CMSConstants.PARENT_CITY);
			}
			if(candidateSearchTO.getParentStateDisp() != null && candidateSearchTO.getParentStateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getParentStatePois()).setCellValue(CMSConstants.PARENT_STATE);
			}
			if(candidateSearchTO.getParentStateOtherDisp() != null && candidateSearchTO.getParentStateOtherDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getParentStateOtherPois()).setCellValue(CMSConstants.PARENT_STATE_OTHER);
			}
			if(candidateSearchTO.getParentCountryDisp() != null && candidateSearchTO.getParentCountryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getParentCountryPois()).setCellValue(CMSConstants.PARENT_COUNTRY);
			}
			if(candidateSearchTO.getParentZipCodeDisp() != null && candidateSearchTO.getParentZipCodeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getParentZipCodePois()).setCellValue(CMSConstants.PARENT_ZIP_CODE);
			}
			if(candidateSearchTO.getParentPhoneDisp() != null && candidateSearchTO.getParentPhoneDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getParentPhonePois()).setCellValue(CMSConstants.PARENT_PHONE);
			}
			if(candidateSearchTO.getParentMobileNoDisp() != null && candidateSearchTO.getParentMobileNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getParentMobileNoPois()).setCellValue(CMSConstants.PARENT_MOBILE_NUMBER);
			}
			if(candidateSearchTO.getApplnNoDisp() != null && candidateSearchTO.getApplnNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getApplnNoPois()).setCellValue(CMSConstants.APPLICATION_NUM);
			}
			if(candidateSearchTO.getNameDisp() != null && candidateSearchTO.getNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getNamePois()).setCellValue(CMSConstants.STUDENT_NAME);
			}
			if(candidateSearchTO.getDateOfBirthDisp() != null && candidateSearchTO.getDateOfBirthDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getDateOfBirthPois()).setCellValue(CMSConstants.DATE_OF_BIRTH);
			}
			if(candidateSearchTO.getGenderDisp() != null && candidateSearchTO.getGenderDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getGenderPois()).setCellValue(CMSConstants.GENDER);
			}
			if(candidateSearchTO.getChallanNoDisp() != null && candidateSearchTO.getChallanNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getChallanNoPois()).setCellValue(CMSConstants.CHALLAN_NUMBER);
			}
			if(candidateSearchTO.getTotalWeightageDisp() != null && candidateSearchTO.getTotalWeightageDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getTotalWeightagePois()).setCellValue(CMSConstants.TOTAL_WEIGHTAGE);
			}
			if(candidateSearchTO.getEmailDisp() != null && candidateSearchTO.getEmailDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getEmailPois()).setCellValue(CMSConstants.EMAIL);
			}
			if(candidateSearchTO.getBloodGroupDisp() != null && candidateSearchTO.getBloodGroupDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getBloodGroupPois()).setCellValue(CMSConstants.BLOOD_GROUP);
			}
			if(candidateSearchTO.getSeatNoDisp() != null && candidateSearchTO.getSeatNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getSeatNoPois()).setCellValue(CMSConstants.REPORT_SEAT_NO);
			}
			if(candidateSearchTO.getExamCenterDisp() != null && candidateSearchTO.getExamCenterDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getExamCenterPois()).setCellValue(CMSConstants.REPORT_EXAM_CENTER);
			}
			if(candidateSearchTO.getMotherTongueDisp() != null && candidateSearchTO.getMotherTongueDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getMotherTonguePois()).setCellValue(CMSConstants.MOTHERTONGUE);
			}
			if(candidateSearchTO.getAdmissionStatusDisp()!= null && candidateSearchTO.getAdmissionStatusDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) ){
				row.createCell((short)candidateSearchTO.getAdmissionStatusPois()).setCellValue(CMSConstants.REPORT_ADDMISSION_STATUS);
			}
			if(candidateSearchTO.getIsAdmittedDisp()!= null && candidateSearchTO.getIsAdmittedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) ){
				row.createCell((short)candidateSearchTO.getIsAdmittedPois()).setCellValue(CMSConstants.IS_ADMITTED);
			}
			if(candidateSearchTO.getCurrentClassDisp()!= null && candidateSearchTO.getCurrentClassDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getCurrentClassPois()).setCellValue(CMSConstants.CURRENT_CLASS);
			}
//			changes made by priyatham  --start--
			if(candidateSearchTO.getIsSelectedDisp()!=null && candidateSearchTO.getIsSelectedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getIsSelectedPois()).setCellValue(CMSConstants.IS_SELECTED);
			}
			if(candidateSearchTO.getIsFinalMeritApprovedDisp()!=null && candidateSearchTO.getIsFinalMeritApprovedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getIsFinalMeritApprovedPois()).setCellValue(CMSConstants.IS_FINAL_MERIT_APPROVED);
			}
			if(candidateSearchTO.getUserNameDisp()!=null && candidateSearchTO.getUserNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getUserNamePois()).setCellValue(CMSConstants.USER_NAME);
			}
			if(candidateSearchTO.getPasswordDisp()!=null && candidateSearchTO.getPasswordDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getPasswordPois()).setCellValue(CMSConstants.PASSWORD);
			}
//			--end--
			
			//Mary Addition 
			
			if(candidateSearchTO.getAppliedDateDisp() != null && candidateSearchTO.getAppliedDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getAppliedDatePois()).setCellValue(CMSConstants.APPLIED_DATE);
			}
			if(candidateSearchTO.getChallanPaymentDateDisp() != null && candidateSearchTO.getChallanPaymentDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getChallanPaymentDatePois()).setCellValue(CMSConstants.CHALLAN_PAYMENT_DATE);
			}
			if(candidateSearchTO.getFeeChallanDateDisp() != null && candidateSearchTO.getFeeChallanDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFeeChallanDatePois()).setCellValue(CMSConstants.FEE_CHALLAN_DATE);
			}
			if(candidateSearchTO.getFeeChallanNoDisp() != null && candidateSearchTO.getFeeChallanNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getFeeChallanNoPois()).setCellValue(CMSConstants.FEE_CHALLAN_NO);
			}
			if(candidateSearchTO.getTotalFeePaidDisp() != null && candidateSearchTO.getTotalFeePaidDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getTotalFeePaidPois()).setCellValue(CMSConstants.TOTAL_FEES_PAID);
			}
			if(candidateSearchTO.getIsHandicapedDisp()!= null && candidateSearchTO.getIsHandicapedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getIsHandicapedPois()).setCellValue(CMSConstants.IS_HANDICAPED);
			}
			if(candidateSearchTO.getHandicapDetailsDisp()!= null && candidateSearchTO.getHandicapDetailsDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) ){
				row.createCell((short)candidateSearchTO.getHandicapDetailsPois()).setCellValue(CMSConstants.HANDICAP_DETAILS);
			}
			if(candidateSearchTO.getTotalExpYearDisp()!= null && candidateSearchTO.getTotalExpYearDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) ){
				row.createCell((short)candidateSearchTO.getTotalExpYearPois()).setCellValue(CMSConstants.TOTAL_EXPERIENCE);
			}		
			
			//mary addition ends
		   //added for challan verification
			if(candidateSearchTO.getChallanVerfiedDisp()!= null && candidateSearchTO.getChallanVerfiedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) ){
				row.createCell((short)candidateSearchTO.getChallanVerifiedPois()).setCellValue(CMSConstants.CHALLAN_VERIFICATION);
			}		
			//addition for challan verification completed
			

			//added for photo status display
			if(candidateSearchTO.getPhotoDisp()!= null && candidateSearchTO.getPhotoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getPhotoPois()).setCellValue(CMSConstants.PHOTO);
			}
		//photo status display addition completed	
			//added for specialization prefered and backlogs display
			if(candidateSearchTO.getSpecializationPreferedDisp()!=null && candidateSearchTO.getSpecializationPreferedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getSpecializationPreferedPois()).setCellValue(CMSConstants.SPECIALIZATION_PREFERED);
			}
			if(candidateSearchTO.getBackLogsDisp()!=null && candidateSearchTO.getBackLogsDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getBackLogsPois()).setCellValue(CMSConstants.BACK_LOGS);
			}
			if(candidateSearchTO.getNewbackLogsDisp()!=null && candidateSearchTO.getNewbackLogsDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getNewbackLogsPois()).setCellValue(CMSConstants.BACK_LOGS_NEW);
			}
			//Added By Manu
			if(candidateSearchTO.getRemarksDisp() != null && candidateSearchTO.getRemarksDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getRemarkPois()).setCellValue(CMSConstants.ADMISSION_REMARKS);
			}
			ICandidateSearchTxnImpl iCandidateSearchTxnImpl = new CandidateSearchTxnImpl();
			
			short maxNo = iCandidateSearchTxnImpl.getMaxColumnNo().shortValue();
			
			maxNo = (short) (maxNo + 1);
			short counter = 0;
			
			
			Iterator csIterator=candidateSearchTOList.iterator();
			while(csIterator.hasNext()){
				CandidateSearchTO canSearchTO=(CandidateSearchTO)csIterator.next();
			if(candidateSearchTO.getInterviewResultDisp() != null && candidateSearchTO.getInterviewResultDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && interviewResultDispCount==0){
				TreeMap<String, String> gradeMap = canSearchTO.getGradeMap();
				if(gradeMap!= null && gradeMap.size() > 0){
					Iterator grItr = gradeMap.keySet().iterator();
					while (grItr.hasNext()) {
						String round = (String) grItr.next();
						row.createCell((short) (maxNo + counter) ).setCellValue(round);
						counter++;
						interviewResultDispCount++;
					}
				}
			}
			}
			//added fro comments display
			Iterator csToIterator=candidateSearchTOList.iterator();
			while(csToIterator.hasNext()){
					CandidateSearchTO canSearchTO=(CandidateSearchTO)csToIterator.next();
				if(candidateSearchTO.getInterviewResultDisp() != null && candidateSearchTO.getInterviewResultDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && interviewResultCommentDispCount==0){
					TreeMap<String, String> commentMap = canSearchTO.getCommentsMap();
					if(commentMap!= null && commentMap.size() > 0){
						Iterator comItr = commentMap.keySet().iterator();
						while (comItr.hasNext()) {
							String commentsLabel = (String) comItr.next();
							row.createCell((short) (maxNo + counter) ).setCellValue(commentsLabel);
							counter++;
							interviewResultCommentDispCount++;
						}
					}
				}
			}
			

			Iterator csIterator3=candidateSearchTOList.iterator();
			while(csIterator3.hasNext()){
					CandidateSearchTO canSearchTO=(CandidateSearchTO)csIterator3.next();
				if(candidateSearchTO.getPrvExamDetDisp() != null && candidateSearchTO.getPrvExamDetDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)&& prvExamDetDispCounter==0){
					int prvExamDetDisp=0;
					if(canSearchTO!= null && canSearchTO.getDocTypePerMap()!= null){
						TreeMap<String, String> perHeadMap = canSearchTO.getDocTypePerMap();
						
						Iterator perItr = perHeadMap.keySet().iterator();
						while (perItr.hasNext()) {
							String headPer = (String) perItr.next();
							row.createCell((short) (maxNo + counter) ).setCellValue(headPer + "%");
							counter++;
							prvExamDetDisp++;
						}
					}
					prvExamDetDispCounter=(prvExamDetDisp>prvExamDetDispCounter)?prvExamDetDisp:prvExamDetDispCounter;
					prvExamDetDisp=0;
					if(canSearchTO!= null && canSearchTO.getDocTypeExamNameMap()!= null){
						TreeMap<String, String> nameHeadMap = canSearchTO.getDocTypeExamNameMap();
						
						Iterator perItr = nameHeadMap.keySet().iterator();
						while (perItr.hasNext()) {
							String examHead = (String) perItr.next();
							row.createCell((short) (maxNo + counter) ).setCellValue(examHead + " Exam Name");
							counter++;
							prvExamDetDisp++;
						}
					}
					prvExamDetDispCounter=(prvExamDetDisp>prvExamDetDispCounter)?prvExamDetDisp:prvExamDetDispCounter;
					prvExamDetDisp=0;
					if(canSearchTO!= null && canSearchTO.getUniMap()!= null){
						TreeMap<String, String> uniHeadMap = canSearchTO.getUniMap();
						
						Iterator uniItr = uniHeadMap.keySet().iterator();
						while (uniItr.hasNext()) {
							String uniHead = (String) uniItr.next();
							row.createCell((short) (maxNo + counter) ).setCellValue(uniHead + " Uni");
							counter++;
							prvExamDetDisp++;
						}
					}
					prvExamDetDispCounter=(prvExamDetDisp>prvExamDetDispCounter)?prvExamDetDisp:prvExamDetDispCounter;
					prvExamDetDisp=0;
					if(canSearchTO!= null && canSearchTO.getInstMap()!= null){
						TreeMap<String, String> instHeadMap = canSearchTO.getInstMap();
						
						Iterator iniItr = instHeadMap.keySet().iterator();
						while (iniItr.hasNext()) {
							String instHead = (String) iniItr.next();
							row.createCell((short) (maxNo + counter) ).setCellValue(instHead + " Inst");
							counter++;
							prvExamDetDisp++;
						}
					}
					prvExamDetDispCounter=(prvExamDetDisp>prvExamDetDispCounter)?prvExamDetDisp:prvExamDetDispCounter;
					prvExamDetDisp=0;
					if(canSearchTO!= null && canSearchTO.getYearMap()!= null){
						TreeMap<String, String> yearHeadMap = canSearchTO.getYearMap();
						
						Iterator iniItr = yearHeadMap.keySet().iterator();
						while (iniItr.hasNext()) {
							String yearHead = (String) iniItr.next();
							row.createCell((short) (maxNo + counter) ).setCellValue(yearHead + " YOP");
							counter++;
							prvExamDetDisp++;
						}
					}
					prvExamDetDispCounter=(prvExamDetDisp>prvExamDetDispCounter)?prvExamDetDisp:prvExamDetDispCounter;
				}
			}
				
				Iterator csIterator2=candidateSearchTOList.iterator();
				while(csIterator2.hasNext()){
						CandidateSearchTO canSearchTO=(CandidateSearchTO)csIterator2.next();
				if(candidateSearchTO.getIntDateTimeDisp() != null && candidateSearchTO.getIntDateTimeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)&& dateTimeDispCount==0){
					int dateTimeDisp=0;
					if(canSearchTO!=null && canSearchTO.getIntDateMap()!=null){
						TreeMap<String, String> intDateMap = canSearchTO.getIntDateMap();
						Iterator iniItr = intDateMap.keySet().iterator();
						while (iniItr.hasNext()) {
							String instHead = (String) iniItr.next();
							row.createCell((short) (maxNo + counter) ).setCellValue(instHead + " Date");
							counter++;
							dateTimeDisp++;
						}
					}
					dateTimeDispCount=(dateTimeDisp>dateTimeDispCount)?dateTimeDisp:dateTimeDispCount;
					dateTimeDisp=0;
					if(canSearchTO!=null && canSearchTO.getIntTimeMap()!=null){
						TreeMap<String, String> intTimeMap = canSearchTO.getIntTimeMap();
						Iterator iniItr = intTimeMap.keySet().iterator();
						while (iniItr.hasNext()) {
							String instHead = (String) iniItr.next();
							row.createCell((short) (maxNo + counter) ).setCellValue(instHead + " Time");
							counter++;
							dateTimeDisp++;
						}
					}
					dateTimeDispCount=(dateTimeDisp>dateTimeDispCount)?dateTimeDisp:dateTimeDispCount;
				}
				}
				
				Iterator csIterator1=candidateSearchTOList.iterator();
				while(csIterator1.hasNext()){
						CandidateSearchTO canSearchTO=(CandidateSearchTO)csIterator1.next();
				
				if(candidateSearchTO.getPreReqDisp() != null && candidateSearchTO.getPreReqDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)&& preReqDispCount==0){
					int preReqCounter=0;
					if(canSearchTO!= null && canSearchTO.getpExamMap()!= null){
						TreeMap<String, String> nameHeadMap = canSearchTO.getpExamMap();
						
						Iterator perItr = nameHeadMap.keySet().iterator();
						while (perItr.hasNext()) {
							String examHead = (String) perItr.next();
							row.createCell((short) (maxNo + counter) ).setCellValue(examHead + " Obtained Marks");
							counter++;
							preReqCounter++;
						}
					}
					preReqDispCount=(preReqCounter>preReqDispCount)?preReqCounter:preReqDispCount;
					preReqCounter=0;
					if(canSearchTO!= null && canSearchTO.getpRollMap()!= null){
						TreeMap<String, String> uniHeadMap = canSearchTO.getpRollMap();
						
						Iterator uniItr = uniHeadMap.keySet().iterator();
						while (uniItr.hasNext()) {
							String uniHead = (String) uniItr.next();
							row.createCell((short) (maxNo + counter) ).setCellValue(uniHead + " RollNo");
							counter++;
							preReqCounter++;
						}
					}
					preReqDispCount=(preReqCounter>preReqDispCount)?preReqCounter:preReqDispCount;
					preReqCounter=0;
					if(canSearchTO!= null && canSearchTO.getpMopMap()!= null){
						TreeMap<String, String> instHeadMap = canSearchTO.getpMopMap();
						
						Iterator iniItr = instHeadMap.keySet().iterator();
						while (iniItr.hasNext()) {
							String instHead = (String) iniItr.next();
							row.createCell((short) (maxNo + counter) ).setCellValue(instHead + " Passing Month");
							counter++;
							preReqCounter++;
						}
					}
					preReqDispCount=(preReqCounter>preReqDispCount)?preReqCounter:preReqDispCount;
					preReqCounter=0;
					if(canSearchTO!= null && canSearchTO.getpYopMap()!= null){
						TreeMap<String, String> yearHeadMap = canSearchTO.getpYopMap();
						
						Iterator iniItr = yearHeadMap.keySet().iterator();
						while (iniItr.hasNext()) {
							String yearHead = (String) iniItr.next();
							row.createCell((short) (maxNo + counter) ).setCellValue(yearHead + " YOP");
							counter++;
							preReqCounter++;
						}
					}
					preReqDispCount=(preReqCounter>preReqDispCount)?preReqCounter:preReqDispCount;
				}
				
			}
			
			
			/*if(candidateSearchTO.getPrvExamDetDisp() != null && candidateSearchTO.getPrvExamDetDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				if(headigSearchTO!= null && headigSearchTO.getDocTypePerMap()!= null){
					TreeMap<String, String> perHeadMap = headigSearchTO.getDocTypePerMap();
					
					Iterator perItr = perHeadMap.keySet().iterator();
					while (perItr.hasNext()) {
						String headPer = (String) perItr.next();
						row.createCell((short) (maxNo + counter) ).setCellValue(headPer + "%");
						counter++;
						
					}
				}
				if(headigSearchTO!= null && headigSearchTO.getDocTypeExamNameMap()!= null){
					TreeMap<String, String> nameHeadMap = headigSearchTO.getDocTypeExamNameMap();
					
					Iterator perItr = nameHeadMap.keySet().iterator();
					while (perItr.hasNext()) {
						String examHead = (String) perItr.next();
						row.createCell((short) (maxNo + counter) ).setCellValue(examHead + " Exam Name");
						counter++;
						
					}
				}
				if(headigSearchTO!= null && headigSearchTO.getUniMap()!= null){
					TreeMap<String, String> uniHeadMap = headigSearchTO.getUniMap();
					
					Iterator uniItr = uniHeadMap.keySet().iterator();
					while (uniItr.hasNext()) {
						String uniHead = (String) uniItr.next();
						row.createCell((short) (maxNo + counter) ).setCellValue(uniHead + " Uni");
						counter++;
						
					}
				}
				if(headigSearchTO!= null && headigSearchTO.getInstMap()!= null){
					TreeMap<String, String> instHeadMap = headigSearchTO.getInstMap();
					
					Iterator iniItr = instHeadMap.keySet().iterator();
					while (iniItr.hasNext()) {
						String instHead = (String) iniItr.next();
						row.createCell((short) (maxNo + counter) ).setCellValue(instHead + " Inst");
						counter++;
						
					}
				}			
			}*/
			
//			changes done by priyatham --start--
			/*if(candidateSearchTO.getPreReqDisp() != null && candidateSearchTO.getPreReqDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){

				if(headigSearchTO!= null && headigSearchTO.getpExamMap()!= null){
					TreeMap<String, String> nameHeadMap = headigSearchTO.getpExamMap();
					
					Iterator perItr = nameHeadMap.keySet().iterator();
					while (perItr.hasNext()) {
						String examHead = (String) perItr.next();
						row.createCell((short) (maxNo + counter) ).setCellValue(examHead + " Obtained Marks");
						counter++;
						
					}
				}
				if(headigSearchTO!= null && headigSearchTO.getpRollMap()!= null){
					TreeMap<String, String> uniHeadMap = headigSearchTO.getpRollMap();
					
					Iterator uniItr = uniHeadMap.keySet().iterator();
					while (uniItr.hasNext()) {
						String uniHead = (String) uniItr.next();
						row.createCell((short) (maxNo + counter) ).setCellValue(uniHead + " RollNo");
						counter++;
						
					}
				}
				if(headigSearchTO!= null && headigSearchTO.getpMopMap()!= null){
					TreeMap<String, String> instHeadMap = headigSearchTO.getpMopMap();
					
					Iterator iniItr = instHeadMap.keySet().iterator();
					while (iniItr.hasNext()) {
						String instHead = (String) iniItr.next();
						row.createCell((short) (maxNo + counter) ).setCellValue(instHead + " Passing Month");
						counter++;
						
					}
				}
				if(headigSearchTO!= null && headigSearchTO.getpYopMap()!= null){
					TreeMap<String, String> yearHeadMap = headigSearchTO.getpYopMap();
					
					Iterator iniItr = yearHeadMap.keySet().iterator();
					while (iniItr.hasNext()) {
						String yearHead = (String) iniItr.next();
						row.createCell((short) (maxNo + counter) ).setCellValue(yearHead + " YOP");
						counter++;
						
					}
				}
			}
			*/
//			priyatham --end--
			
			row.createCell((short) (maxNo + counter) ).setCellValue(" ");
			while (iterator.hasNext()) {
				CandidateSearchTO candidateSearch = (CandidateSearchTO) iterator.next();
				count = count +1;
				row = sheet.createRow(count);				
				if(candidateSearchTO.getRegNoDisp() != null && candidateSearchTO.getRegNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getRegNo()!=null){
					row.createCell((short)candidateSearchTO.getRegNoPois()).setCellValue(candidateSearch.getRegNo());
				}	
				if(candidateSearchTO.getRollNoDisp() != null && candidateSearchTO.getRollNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getRollNo()!=null){
					row.createCell((short)candidateSearchTO.getRollNoPois()).setCellValue(candidateSearch.getRollNo());
				}
				if(candidateSearchTO.getClassNameDisp() != null && candidateSearchTO.getClassNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getClassStudent()!=null){
					row.createCell((short)candidateSearchTO.getClassNamePois()).setCellValue(candidateSearch.getClassStudent());
				}
				if(candidateSearchTO.getAdmissionDateDisp() != null && candidateSearchTO.getAdmissionDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getAdmissionDate()!=null){
					row.createCell((short)candidateSearchTO.getAdmissionDatePois()).setCellValue(candidateSearch.getAdmissionDate());
				}
				if(candidateSearchTO.getSecondLanguageDisp() != null && candidateSearchTO.getSecondLanguageDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getSecondLanguage()!=null){
					row.createCell((short)candidateSearchTO.getSecondLanguagePois()).setCellValue(candidateSearch.getSecondLanguage());
				}
				if(candidateSearchTO.getProgramTypeNameDisp() != null && candidateSearchTO.getProgramTypeNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getProgramTypeName()!=null){
					row.createCell((short)candidateSearchTO.getProgramTypeNamePois()).setCellValue(candidateSearch.getProgramTypeName());
				}
				if(candidateSearchTO.getProgramNameDisp() != null && candidateSearchTO.getProgramNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getProgramName()!=null){
					row.createCell((short)candidateSearchTO.getProgramNamePois()).setCellValue(candidateSearch.getProgramName());
				}
				if(candidateSearchTO.getCourseNameDisp() != null && candidateSearchTO.getCourseNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getCourseName()!=null){
					row.createCell((short)candidateSearchTO.getCourseNamePois()).setCellValue(candidateSearch.getCourseName());
				}
				if(candidateSearchTO.getJournalNoDisp() != null && candidateSearchTO.getJournalNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getJournalNo()!=null){
					row.createCell((short)candidateSearchTO.getJournalNoPois()).setCellValue(candidateSearch.getJournalNo());
				}
				if(candidateSearchTO.getAcedamicYearDisp() != null && candidateSearchTO.getAcedamicYearDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getAppliedYear()!=null){
					row.createCell((short)candidateSearchTO.getAcedamicYearPois()).setCellValue(candidateSearch.getAppliedYear());
				}
				if(candidateSearchTO.getAdmittedThroughDisp() != null && candidateSearchTO.getAdmittedThroughDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getAdmittedThrough()!=null){
					row.createCell((short)candidateSearchTO.getAdmittedThroughPois()).setCellValue(candidateSearch.getAdmittedThrough());
				}
				if(candidateSearchTO.getResidentCategoryDisp() != null && candidateSearchTO.getResidentCategoryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getResidentCategory()!=null){
					row.createCell((short)candidateSearchTO.getResidentCategoryPois()).setCellValue(candidateSearch.getResidentCategory());
				}
				if(candidateSearchTO.getReligionDisp() != null && candidateSearchTO.getReligionDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getReligion()!=null){
					row.createCell((short)candidateSearchTO.getReligionPois()).setCellValue(candidateSearch.getReligion());
				}
				if(candidateSearchTO.getReligionOtherDisp() != null && candidateSearchTO.getReligionOtherDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getReligionOther()!=null){
					row.createCell((short)candidateSearchTO.getReligionOtherPois()).setCellValue(candidateSearch.getReligionOther());
				}
				if(candidateSearchTO.getSubReligionDisp() != null && candidateSearchTO.getSubReligionDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getSubReligion()!=null){
					row.createCell((short)candidateSearchTO.getSubReligionPois()).setCellValue(candidateSearch.getSubReligion());
				}
				if(candidateSearchTO.getSubReligionOtherDisp() != null && candidateSearchTO.getSubReligionOtherDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getSubReligionOther()!=null){
					row.createCell((short)candidateSearchTO.getSubReligionOtherPois()).setCellValue(candidateSearch.getSubReligionOther());
				}
				if(candidateSearchTO.getCastCategoryDisp() != null && candidateSearchTO.getCastCategoryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getCastCategory()!=null){
					row.createCell((short)candidateSearchTO.getCastCategoryPois()).setCellValue(candidateSearch.getCastCategory());
				}
				if(candidateSearchTO.getCastCategoryOtherDisp() != null && candidateSearchTO.getCastCategoryOtherDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getCastCategoryOther()!=null){
					row.createCell((short)candidateSearchTO.getCastCategoryOtherPois()).setCellValue(candidateSearch.getCastCategoryOther());
				}
				if(candidateSearchTO.getPlaceOfBirthDisp() != null && candidateSearchTO.getPlaceOfBirthDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getPlaceOfBirth()!=null){
					row.createCell((short)candidateSearchTO.getPlaceOfBirthPois()).setCellValue(candidateSearch.getPlaceOfBirth());
				}
				if(candidateSearchTO.getStateOfBirthDisp() != null && candidateSearchTO.getStateOfBirthDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getStateOfBirth()!=null){
					row.createCell((short)candidateSearchTO.getStateOfBirthPois()).setCellValue(candidateSearch.getStateOfBirth());
				}
				if(candidateSearchTO.getStateOfBirthOtherDisp() != null && candidateSearchTO.getStateOfBirthOtherDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getStateOfBirthOther()!=null){
					row.createCell((short)candidateSearchTO.getStateOfBirthOtherPois()).setCellValue(candidateSearch.getStateOfBirthOther());
				}
				if(candidateSearchTO.getCountryOfBirthDisp() != null && candidateSearchTO.getCountryOfBirthDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getCountryOfBirth()!=null){
					row.createCell((short)candidateSearchTO.getCountryOfBirthPois()).setCellValue(candidateSearch.getCountryOfBirth());
				}
				if(candidateSearchTO.getBelongsToDisp() != null && candidateSearchTO.getBelongsToDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getBelongsTo()!=null){
					row.createCell((short)candidateSearchTO.getBelongsToPois()).setCellValue(candidateSearch.getBelongsTo());
				}
				if(candidateSearchTO.getNationalityDisp() != null && candidateSearchTO.getNationalityDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getNationality()!=null){
					row.createCell((short)candidateSearchTO.getNationalityPois()).setCellValue(candidateSearch.getNationality());
				}
				if(candidateSearchTO.getStudentPhoneNumberDisp() != null && candidateSearchTO.getStudentPhoneNumberDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getStudentPhoneNo()!=null){
					cell = row.createCell((short)candidateSearchTO.getStudentPhoneNumberPois());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(candidateSearch.getStudentPhoneNo());
				}
				if(candidateSearchTO.getStudentMobileNumberDisp() != null && candidateSearchTO.getStudentMobileNumberDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getStudentMobileNo()!=null){
					cell = row.createCell((short)candidateSearchTO.getStudentMobileNumberPois());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(candidateSearch.getStudentMobileNo());
				}
				if(candidateSearchTO.getPassportNumberDisp() != null && candidateSearchTO.getPassportNumberDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getPassportNo()!=null){
					row.createCell((short)candidateSearchTO.getPassportNumberPois()).setCellValue(candidateSearch.getPassportNo());
				}
				if(candidateSearchTO.getPassportIssuingCountryDisp() != null && candidateSearchTO.getPassportIssuingCountryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getPassportIssuingCountry()!=null){
					row.createCell((short)candidateSearchTO.getPassportIssuingCountryPois()).setCellValue(candidateSearch.getPassportIssuingCountry());
				}
				if(candidateSearchTO.getPassportValidUpToDisp() != null && candidateSearchTO.getPassportValidUpToDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getPassportValidUpTo()!=null){
					row.createCell((short)candidateSearchTO.getPassportValidUpToPois()).setCellValue(candidateSearch.getPassportValidUpTo());
				}
				if(candidateSearchTO.getFirstPreferenceProgramTypeDisp() != null && candidateSearchTO.getFirstPreferenceProgramTypeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getFirstPrePT()!=null){
					row.createCell((short)candidateSearchTO.getFirstPreferenceProgramTypePois()).setCellValue(candidateSearch.getFirstPrePT());
				}
				if(candidateSearchTO.getFirstPreferenceProgramDisp() != null && candidateSearchTO.getFirstPreferenceProgramDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getFirstPreProgram()!=null){
					row.createCell((short)candidateSearchTO.getFirstPreferenceProgramPois()).setCellValue(candidateSearch.getFirstPreProgram());
				}
				if(candidateSearchTO.getFirstPreferenceCourseDisp() != null && candidateSearchTO.getFirstPreferenceCourseDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getFirstCourse()!=null){
					row.createCell((short)candidateSearchTO.getFirstPreferenceCoursePois()).setCellValue(candidateSearch.getFirstCourse());
				}
				if(candidateSearchTO.getSecondPreferenceProgramTypeDisp() != null && candidateSearchTO.getSecondPreferenceProgramTypeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getSecondPrePT()!=null){
					row.createCell((short)candidateSearchTO.getSecondPreferenceProgramTypePois()).setCellValue(candidateSearch.getSecondPrePT());
				}
				if(candidateSearchTO.getSecondPreferenceProgramDisp() != null && candidateSearchTO.getSecondPreferenceProgramDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getSecondPreProgram()!=null){
					row.createCell((short)candidateSearchTO.getSecondPreferenceProgramPois()).setCellValue(candidateSearch.getSecondPreProgram());
				}
				if(candidateSearchTO.getSecondPreferenceCourseDisp() != null && candidateSearchTO.getSecondPreferenceCourseDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getSecondCourse()!=null){
					row.createCell((short)candidateSearchTO.getSecondPreferenceCoursePois()).setCellValue(candidateSearch.getSecondCourse());
				}
				if(candidateSearchTO.getThirdPreferenceProgramTypeDisp() != null && candidateSearchTO.getThirdPreferenceProgramTypeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getThirdPrePT()!=null){
					row.createCell((short)candidateSearchTO.getThirdPreferenceProgramTypePois()).setCellValue(candidateSearch.getThirdPrePT());
				}
				if(candidateSearchTO.getThirdPreferenceProgramDisp() != null && candidateSearchTO.getThirdPreferenceProgramDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getThirdPreProgram()!=null){
					row.createCell((short)candidateSearchTO.getThirdPreferenceProgramPois()).setCellValue(candidateSearch.getThirdPreProgram());
				}
				if(candidateSearchTO.getThirdPreferenceCourseDisp() != null && candidateSearchTO.getThirdPreferenceCourseDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getThirdCourse()!=null){
					row.createCell((short)candidateSearchTO.getThirdPreferenceCoursePois()).setCellValue(candidateSearch.getThirdCourse());
				}
				if(candidateSearchTO.getFirstNameofOrganisationDisp() != null && candidateSearchTO.getFirstNameofOrganisationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getFirstNameOfOra()!=null){
					row.createCell((short)candidateSearchTO.getFirstNameofOrganisationPois()).setCellValue(candidateSearch.getFirstNameOfOra());
				}
				if(candidateSearchTO.getFirstDesignationDisp() != null && candidateSearchTO.getFirstDesignationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getFirstDesignation()!=null){
					row.createCell((short)candidateSearchTO.getFirstDesignationPois()).setCellValue(candidateSearch.getFirstDesignation());
				}
				if(candidateSearchTO.getFirstFromDateDisp() != null && candidateSearchTO.getFirstFromDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getFirstFromDate()!=null){
					row.createCell((short)candidateSearchTO.getFirstFromDatePois()).setCellValue(candidateSearch.getFirstFromDate());
				}
				if(candidateSearchTO.getFirstToDateDisp() != null && candidateSearchTO.getFirstToDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getFirstTODate()!=null){
					row.createCell((short)candidateSearchTO.getFirstToDatePois()).setCellValue(candidateSearch.getFirstTODate());
				}
				if(candidateSearchTO.getSecondNameofOrganisationDisp() != null && candidateSearchTO.getSecondNameofOrganisationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getSecondNameOfOra()!=null){
					row.createCell((short)candidateSearchTO.getSecondNameofOrganisationPois()).setCellValue(candidateSearch.getSecondNameOfOra());
				}
				if(candidateSearchTO.getSecondDesignationDisp() != null && candidateSearchTO.getSecondDesignationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getSecondDesignation()!=null){
					row.createCell((short)candidateSearchTO.getSecondDesignationPois()).setCellValue(candidateSearch.getSecondDesignation());
				}
				if(candidateSearchTO.getSecondFromDateDisp() != null && candidateSearchTO.getSecondFromDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getSecondFromDate()!=null){
					row.createCell((short)candidateSearchTO.getSecondFromDatePois()).setCellValue(candidateSearch.getSecondFromDate());
				}
				if(candidateSearchTO.getSecondToDateDisp() != null && candidateSearchTO.getSecondToDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getSecondTODate()!=null){
					row.createCell((short)candidateSearchTO.getSecondToDatePois()).setCellValue(candidateSearch.getSecondTODate());
				}
				if(candidateSearchTO.getThirdNameofOrganisationDisp() != null && candidateSearchTO.getThirdNameofOrganisationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getThirdNameOfOra()!=null){
					row.createCell((short)candidateSearchTO.getThirdNameofOrganisationPois()).setCellValue(candidateSearch.getThirdNameOfOra());
				}
				if(candidateSearchTO.getThirdDesignationDisp() != null && candidateSearchTO.getThirdDesignationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getThirdDesignation()!=null){
					row.createCell((short)candidateSearchTO.getThirdDesignationPois()).setCellValue(candidateSearch.getThirdDesignation());
				}
				if(candidateSearchTO.getThirdFromDateDisp() != null && candidateSearchTO.getThirdFromDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getThirdFromDate()!=null){
					row.createCell((short)candidateSearchTO.getThirdFromDatePois()).setCellValue(candidateSearch.getThirdFromDate());
				}
				if(candidateSearchTO.getThirdToDateDisp() != null && candidateSearchTO.getThirdToDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getThirdTODate()!=null){
					row.createCell((short)candidateSearchTO.getThirdToDatePois()).setCellValue(candidateSearch.getThirdTODate());
				}
				if(candidateSearchTO.getPermanentAddressLine1Disp() != null && candidateSearchTO.getPermanentAddressLine1Disp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getPermanentAddressline1()!=null){
					row.createCell((short)candidateSearchTO.getPermanentAddressLine1Pois()).setCellValue(candidateSearch.getPermanentAddressline1());
				}
				if(candidateSearchTO.getPermanentAddressLine2Disp() != null && candidateSearchTO.getPermanentAddressLine2Disp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getPermanentAddressline2()!=null){
					row.createCell((short)candidateSearchTO.getPermanentAddressLine2Pois()).setCellValue(candidateSearch.getParentAdressLine2());
				}
				if(candidateSearchTO.getPermanentStateDisp() != null && candidateSearchTO.getPermanentStateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getPermanentState()!=null){
					row.createCell((short)candidateSearchTO.getPermanentStatePois()).setCellValue(candidateSearch.getPermanentState());
				}
				if(candidateSearchTO.getPermanentStateOthersDisp() != null && candidateSearchTO.getPermanentStateOthersDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getPermanentStateOther()!=null){
					row.createCell((short)candidateSearchTO.getPermanentStateOthersPois()).setCellValue(candidateSearch.getPermanentStateOther());
				}
				if(candidateSearchTO.getPermanentCityDisp() != null && candidateSearchTO.getPermanentCityDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getPermanentCity()!=null){
					row.createCell((short)candidateSearchTO.getPermanentCityPois()).setCellValue(candidateSearch.getPermanentCity());
				}
				if(candidateSearchTO.getPermanentCountryDisp() != null && candidateSearchTO.getPermanentCountryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getPermanentCountry()!=null){
					row.createCell((short)candidateSearchTO.getPermanentCountryPois()).setCellValue(candidateSearch.getPermanentCountry());
				}
				if(candidateSearchTO.getPermanentZipCodeDisp() != null && candidateSearchTO.getPermanentZipCodeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getPermanentZipCode()!=null){
					row.createCell((short)candidateSearchTO.getPermanentZipCodePois()).setCellValue(candidateSearch.getPermanentZipCode());
				}
				if(candidateSearchTO.getCurrentAddressLine1Disp() != null && candidateSearchTO.getCurrentAddressLine1Disp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getCurrentAddressline1()!=null){
					row.createCell((short)candidateSearchTO.getCurrentAddressLine1Pois()).setCellValue(candidateSearch.getCurrentAddressline1());
				}
				if(candidateSearchTO.getCurrentAddressLine2Disp() != null && candidateSearchTO.getCurrentAddressLine2Disp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getCurrentAddressline2()!=null){
					row.createCell((short)candidateSearchTO.getCurrentAddressLine2Pois()).setCellValue(candidateSearch.getCurrentAddressline2());
				}
				if(candidateSearchTO.getCurrentStateDisp() != null && candidateSearchTO.getCurrentStateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getCurrentState()!=null){
					row.createCell((short)candidateSearchTO.getCurrentStatePois()).setCellValue(candidateSearch.getCurrentState());
				}
				if(candidateSearchTO.getCurrentStateOthersDisp() != null && candidateSearchTO.getCurrentStateOthersDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getCurrentStateOther()!=null){
					row.createCell((short)candidateSearchTO.getCurrentStateOthersPois()).setCellValue(candidateSearch.getCurrentStateOther());
				}
				if(candidateSearchTO.getCurrentCityDisp() != null && candidateSearchTO.getCurrentCityDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getCurrentCity()!=null){
					row.createCell((short)candidateSearchTO.getCurrentCityPois()).setCellValue(candidateSearch.getCurrentCity());
				}
				if(candidateSearchTO.getCurrentCountryDisp() != null && candidateSearchTO.getCurrentCountryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getCurrentCountry()!=null){
					row.createCell((short)candidateSearchTO.getCurrentCountryPois()).setCellValue(candidateSearch.getCurrentCountry());
				}
				if(candidateSearchTO.getCurrentZipCodeDisp() != null && candidateSearchTO.getCurrentZipCodeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getCurrentZipCode()!=null){
					row.createCell((short)candidateSearchTO.getCurrentZipCodePois()).setCellValue(candidateSearch.getCurrentZipCode());
				}
				if(candidateSearchTO.getFathersNameDisp() != null && candidateSearchTO.getFathersNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getFatherName()!=null){
					row.createCell((short)candidateSearchTO.getFathersNamePois()).setCellValue(candidateSearch.getFatherName());
				}
				if(candidateSearchTO.getFathersEducationDisp() != null && candidateSearchTO.getFathersEducationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getFatherEducation()!=null){
					row.createCell((short)candidateSearchTO.getFathersEducationPois()).setCellValue(candidateSearch.getFatherEducation());
				}
				if(candidateSearchTO.getFathersIncomeDisp() != null && candidateSearchTO.getFathersIncomeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getFatherIncome()!=null){
					row.createCell((short)candidateSearchTO.getFathersIncomePois()).setCellValue(candidateSearch.getFatherIncome());
				}
				if(candidateSearchTO.getFathersCurrencyDisp() != null && candidateSearchTO.getFathersCurrencyDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getFatherIncomeCurrency()!=null){
					row.createCell((short)candidateSearchTO.getFathersCurrencyPois()).setCellValue(candidateSearch.getFatherIncomeCurrency());
				}
				if(candidateSearchTO.getFathersOccupationDisp() != null && candidateSearchTO.getFathersOccupationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getFatherOccupation()!=null){
					row.createCell((short)candidateSearchTO.getFathersOccupationPois()).setCellValue(candidateSearch.getFatherOccupation());
				}
				if(candidateSearchTO.getFathersEmailDisp() != null && candidateSearchTO.getFathersEmailDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getFatherEmail()!=null){
					row.createCell((short)candidateSearchTO.getFathersEmailPois()).setCellValue(candidateSearch.getFatherEmail());
				}
				if(candidateSearchTO.getMothersNameDisp() != null && candidateSearchTO.getMothersNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getMotherName()!=null){
					row.createCell((short)candidateSearchTO.getMothersNamePois()).setCellValue(candidateSearch.getMotherName());
				}
				if(candidateSearchTO.getMothersEducationDisp() != null && candidateSearchTO.getMothersEducationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getMotherEducation()!=null){
					row.createCell((short)candidateSearchTO.getMothersEducationPois()).setCellValue(candidateSearch.getMotherEducation());
				}
				if(candidateSearchTO.getMothersIncomeDisp() != null && candidateSearchTO.getMothersIncomeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getMotherIncome()!=null){
					row.createCell((short)candidateSearchTO.getMothersIncomePois()).setCellValue(candidateSearch.getMotherIncome());
				}
				if(candidateSearchTO.getMothersCurrencyDisp() != null && candidateSearchTO.getMothersCurrencyDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getMotherIncomeCurrency()!=null){
					row.createCell((short)candidateSearchTO.getMothersCurrencyPois()).setCellValue(candidateSearch.getMotherIncomeCurrency());
				}
				if(candidateSearchTO.getMothersOccupationDisp() != null && candidateSearchTO.getMothersOccupationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getMotherOccupation()!=null){
					row.createCell((short)candidateSearchTO.getMothersOccupationPois()).setCellValue(candidateSearch.getMotherOccupation());
				}
				if(candidateSearchTO.getMothersEmailDisp() != null && candidateSearchTO.getMothersEmailDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getMotherEmail()!=null){
					row.createCell((short)candidateSearchTO.getMothersEmailPois()).setCellValue(candidateSearch.getMotherEmail());
				}
				if(candidateSearchTO.getParentAdressLine1Disp() != null && candidateSearchTO.getParentAdressLine1Disp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getParentAdressLine1()!=null){
					row.createCell((short)candidateSearchTO.getParentAdressLine1Pois()).setCellValue(candidateSearch.getParentAdressLine1());
				}
				if(candidateSearchTO.getParentAdressLine2Disp() != null && candidateSearchTO.getParentAdressLine2Disp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getParentAdressLine2()!=null){
					row.createCell((short)candidateSearchTO.getParentAdressLine2Pois()).setCellValue(candidateSearch.getParentAdressLine2());
				}
				if(candidateSearchTO.getParentAdressLine3Disp() != null && candidateSearchTO.getParentAdressLine3Disp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getParentAdressLine3()!=null){
					row.createCell((short)candidateSearchTO.getParentAdressLine3Pois()).setCellValue(candidateSearch.getParentAdressLine3());
				}
				if(candidateSearchTO.getParentCityDisp() != null && candidateSearchTO.getParentCityDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getParentCity()!=null){
					row.createCell((short)candidateSearchTO.getParentCityPois()).setCellValue(candidateSearch.getParentCity());
				}
				if(candidateSearchTO.getParentStateDisp() != null && candidateSearchTO.getParentStateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getParentState()!=null){
					row.createCell((short)candidateSearchTO.getParentStatePois()).setCellValue(candidateSearch.getParentState());
				}
				if(candidateSearchTO.getParentStateOtherDisp() != null && candidateSearchTO.getParentStateOtherDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getParentStateOther()!=null){
					row.createCell((short)candidateSearchTO.getParentStateOtherPois()).setCellValue(candidateSearch.getParentStateOther());
				}
				if(candidateSearchTO.getParentCountryDisp() != null && candidateSearchTO.getParentCountryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getParentCountry()!=null){
					row.createCell((short)candidateSearchTO.getParentCountryPois()).setCellValue(candidateSearch.getParentCountry());
				}
				if(candidateSearchTO.getParentZipCodeDisp() != null && candidateSearchTO.getParentZipCodeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getParentZipCode()!=null){
					row.createCell((short)candidateSearchTO.getParentZipCodePois()).setCellValue(candidateSearch.getParentZipCode());
				}
				if(candidateSearchTO.getParentPhoneDisp() != null && candidateSearchTO.getParentPhoneDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getParentPhone()!=null){
					cell = row.createCell((short)candidateSearchTO.getParentPhonePois());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(candidateSearch.getParentPhone());
				}
				if(candidateSearchTO.getParentMobileNoDisp() != null && candidateSearchTO.getParentMobileNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getParentMobileNo()!=null){
					cell = row.createCell((short)candidateSearchTO.getParentMobileNoPois());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(candidateSearch.getParentMobileNo());
				}
				if(candidateSearchTO.getApplnNoDisp() != null && candidateSearchTO.getApplnNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getApplnNo()!= 0){
					row.createCell((short)candidateSearchTO.getApplnNoPois()).setCellValue(candidateSearch.getApplnNo());
				}
				if(candidateSearchTO.getNameDisp() != null && candidateSearchTO.getNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getName()!= null){
					row.createCell((short)candidateSearchTO.getNamePois()).setCellValue(candidateSearch.getName());
				}
				if(candidateSearchTO.getDateOfBirthDisp() != null && candidateSearchTO.getDateOfBirthDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getDateOfBirth()!= null){
					cell = row.createCell((short)candidateSearchTO.getDateOfBirthPois());
				    cell.setCellValue(candidateSearch.getDateOfBirth());
				    cell.setCellStyle(cellStyle);
				}
				if(candidateSearchTO.getGenderDisp() != null && candidateSearchTO.getGenderDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getGender()!= null){
					row.createCell((short)candidateSearchTO.getGenderPois()).setCellValue(candidateSearch.getGender());
				}
				if(candidateSearchTO.getChallanNoDisp() != null && candidateSearchTO.getChallanNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getChallanNo()!= null){
					row.createCell((short)candidateSearchTO.getChallanNoPois()).setCellValue(candidateSearch.getChallanNo());
				}
				if(candidateSearchTO.getTotalWeightageDisp() != null && candidateSearchTO.getTotalWeightageDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getTotalWeightage()!= 0){
					row.createCell((short)candidateSearchTO.getTotalWeightagePois()).setCellValue(candidateSearch.getTotalWeightage());
				}
				if(candidateSearchTO.getEmailDisp() != null && candidateSearchTO.getEmailDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getEmail()!= null){
					row.createCell((short)candidateSearchTO.getEmailPois()).setCellValue(candidateSearch.getEmail());
				}
				if(candidateSearchTO.getBloodGroupDisp() != null && candidateSearchTO.getBloodGroupDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getBloodGroup()!= null){
					row.createCell((short)candidateSearchTO.getBloodGroupPois()).setCellValue(candidateSearch.getBloodGroup());
				}
				if(candidateSearchTO.getExamCenterDisp() != null && candidateSearchTO.getExamCenterDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getExamCenter()!= null){
					row.createCell((short)candidateSearchTO.getExamCenterPois()).setCellValue(candidateSearch.getExamCenter());
				}
				if(candidateSearchTO.getSeatNoDisp()!= null && candidateSearchTO.getSeatNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getSeatNo()!= null){
					row.createCell((short)candidateSearchTO.getSeatNoPois()).setCellValue(candidateSearch.getSeatNo());
				}
				if(candidateSearchTO.getMotherTongueDisp()!= null && candidateSearchTO.getMotherTongueDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getMotherTongue()!= null){
					row.createCell((short)candidateSearchTO.getMotherTonguePois()).setCellValue(candidateSearch.getMotherTongue());
				}
				if(candidateSearchTO.getAdmissionStatusDisp()!= null && candidateSearchTO.getAdmissionStatusDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getAdmissionStatus()!= null){
					row.createCell((short)candidateSearchTO.getAdmissionStatusPois()).setCellValue(candidateSearch.getAdmissionStatus());
				}
				if(candidateSearchTO.getIsAdmittedDisp()!= null && candidateSearchTO.getIsAdmittedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getIsAdmitted()!= null){
					row.createCell((short)candidateSearchTO.getIsAdmittedPois()).setCellValue(candidateSearch.getIsAdmitted());
				}
				if(candidateSearchTO.getCurrentClassDisp()!= null && candidateSearchTO.getCurrentClassDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getCurrentClass()!= null){
					row.createCell((short)candidateSearchTO.getCurrentClassPois()).setCellValue(candidateSearch.getCurrentClass());
				}
				if(candidateSearchTO.getIsSelectedDisp()!= null && candidateSearchTO.getIsSelectedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getIsSelected()!= null){
					row.createCell((short)candidateSearchTO.getIsSelectedPois()).setCellValue(candidateSearch.getIsSelected());
				}
				if(candidateSearchTO.getIsFinalMeritApprovedDisp()!= null && candidateSearchTO.getIsFinalMeritApprovedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getIsFinalMeritApproved()!= null){
					row.createCell((short)candidateSearchTO.getIsFinalMeritApprovedPois()).setCellValue(candidateSearch.getIsFinalMeritApproved());
				}
				if(candidateSearchTO.getUserNameDisp()!=null && candidateSearchTO.getUserNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
					row.createCell((short)candidateSearchTO.getUserNamePois()).setCellValue(candidateSearch.getUserName());
				}
				if(candidateSearchTO.getPasswordDisp()!=null && candidateSearchTO.getPasswordDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
					row.createCell((short)candidateSearchTO.getPasswordPois()).setCellValue(candidateSearch.getPassword());
				}
				//Mary Addition 
				
				if(candidateSearchTO.getAppliedDateDisp() != null && candidateSearchTO.getAppliedDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)&& candidateSearch.getAppliedDate() != null){
					row.createCell((short)candidateSearchTO.getAppliedDatePois()).setCellValue(candidateSearch.getAppliedDate());
				}
				if(candidateSearchTO.getChallanPaymentDateDisp() != null && candidateSearchTO.getChallanPaymentDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getChallanPaymentDate() != null){
					row.createCell((short)candidateSearchTO.getChallanPaymentDatePois()).setCellValue(candidateSearch.getChallanPaymentDate());
				}
				if(candidateSearchTO.getFeeChallanDateDisp() != null && candidateSearchTO.getFeeChallanDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getChallanDate() != null){
					row.createCell((short)candidateSearchTO.getFeeChallanDatePois()).setCellValue(candidateSearch.getChallanDate());
				}
				if(candidateSearchTO.getFeeChallanNoDisp() != null && candidateSearchTO.getFeeChallanNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getChallanNo() != null){
					row.createCell((short)candidateSearchTO.getFeeChallanNoPois()).setCellValue(candidateSearch.getChallanNo());
				}
				if(candidateSearchTO.getTotalFeePaidDisp() != null && candidateSearchTO.getTotalFeePaidDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getTotalFeePaid() != null){
					row.createCell((short)candidateSearchTO.getTotalFeePaidPois()).setCellValue(candidateSearch.getTotalFeePaid());
				}
				if(candidateSearchTO.getIsHandicapedDisp()!= null && candidateSearchTO.getIsHandicapedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getIsHandicaped() != null){
					row.createCell((short)candidateSearchTO.getIsHandicapedPois()).setCellValue(candidateSearch.getIsHandicaped());
				}
				if(candidateSearchTO.getHandicapDetailsDisp()!= null && candidateSearchTO.getHandicapDetailsDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getHandicapDetails() != null){
					row.createCell((short)candidateSearchTO.getHandicapDetailsPois()).setCellValue(candidateSearch.getHandicapDetails());
				}
				if(candidateSearchTO.getTotalExpYearDisp()!= null && candidateSearchTO.getTotalExpYearDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getTotalExpYear() != null){
					row.createCell((short)candidateSearchTO.getTotalExpYearPois()).setCellValue(candidateSearch.getTotalExpYear());
				}
				//ends
				//added for challan verification
				if(candidateSearchTO.getChallanVerfiedDisp()!= null && candidateSearchTO.getChallanVerfiedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getIsChallanVerified() != null){
					row.createCell((short)candidateSearchTO.getChallanVerifiedPois()).setCellValue(candidateSearch.getIsChallanVerified());
				}
				//ends
				//added for photo status display
				if(candidateSearchTO.getPhotoDisp()!= null && candidateSearchTO.getPhotoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getPhoto()!= null ) {
					row.createCell((short)candidateSearchTO.getPhotoPois()).setCellValue(candidateSearch.getPhoto());
				}
				//addition for photo status completed
				// added for specialization prefered and backlogs display
				if(candidateSearchTO.getSpecializationPreferedDisp()!= null && candidateSearchTO.getSpecializationPreferedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getSpecializationPrefered()!= null ) {
					row.createCell((short)candidateSearchTO.getSpecializationPreferedPois()).setCellValue(candidateSearch.getSpecializationPrefered());
				}
				if(candidateSearchTO.getBackLogsDisp()!= null && candidateSearchTO.getBackLogsDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getBackLogs()!= null ) {
					row.createCell((short)candidateSearchTO.getBackLogsPois()).setCellValue(candidateSearch.getBackLogs());
				}
				if(candidateSearchTO.getNewbackLogsDisp()!= null && candidateSearchTO.getNewbackLogsDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getNewbackLogs()!= null ) {
					row.createCell((short)candidateSearchTO.getNewbackLogsPois()).setCellValue(candidateSearch.getNewbackLogs());
				}
				//Added By manu
				if(candidateSearchTO.getRemarksDisp()!= null && candidateSearchTO.getRemarksDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getRemarks()!= null ) {
					row.createCell((short)candidateSearchTO.getRemarkPois()).setCellValue(candidateSearch.getRemarks());
				}
				//Added By Mary
				if(candidateSearchTO.getSelectionProcessDateDisp()!= null && candidateSearchTO.getSelectionProcessDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY) && candidateSearch.getSelectionProcessDate()!= null ) {
					row.createCell((short)candidateSearchTO.getSelectionProcessDatePois()).setCellValue(candidateSearch.getSelectionProcessDate());
				}
				short counter1 = 0;
				if(candidateSearchTO.getInterviewResultDisp() != null && candidateSearchTO.getInterviewResultDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
					TreeMap<String, String> gradeMap = candidateSearch.getGradeMap();
					if(gradeMap!= null && gradeMap.size() > 0){
						Iterator grItr = gradeMap.keySet().iterator();
						while (grItr.hasNext()) {
							String id = (String) grItr.next();
							row.createCell((short) (maxNo + counter1) ).setCellValue(gradeMap.get(id));
							counter1++;
						}
					}else{   //modified by priyatham --start--
						for(int i=0;i<interviewResultDispCount;i++){
							row.createCell((short) (maxNo + counter1) ).setCellValue(" ");
							counter1++;
						}
					}//--end--
				}
				// added for comments display
				if(candidateSearchTO.getInterviewResultDisp() != null && candidateSearchTO.getInterviewResultDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
					TreeMap<String, String> commentMap = candidateSearch.getCommentsMap();
					if(commentMap!= null && commentMap.size() > 0){
						Iterator comItr = commentMap.keySet().iterator();
						while (comItr.hasNext()) {
							String id = (String) comItr.next();
							row.createCell((short) (maxNo + counter1) ).setCellValue(commentMap.get(id));
							counter1++;
						}
					}else{   //modified by priyatham --start--
						for(int i=0;i<interviewResultCommentDispCount;i++){
							row.createCell((short) (maxNo + counter1) ).setCellValue(" ");
							counter1++;
						}
					}//--end--
				}
				
				
				
				if(candidateSearchTO.getPrvExamDetDisp() != null && candidateSearchTO.getPrvExamDetDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
					TreeMap<String, String> perMap = candidateSearch.getDocTypePerMap();
					if(perMap!= null && perMap.size() > 0){
						Iterator grItr = perMap.keySet().iterator();
						while (grItr.hasNext()) {
							String id = (String) grItr.next();
							row.createCell((short) (maxNo + counter1) ).setCellValue(perMap.get(id));
							counter1++;
						}
					}else{   //modified by priyatham --start--
						for(int i=0;i<prvExamDetDispCounter;i++){
							row.createCell((short) (maxNo + counter1) ).setCellValue(" ");
							counter1++;
						}
					}//--end--
					TreeMap<String, String> examMap = candidateSearch.getDocTypeExamNameMap();
					if(examMap!= null && examMap.size() > 0){
						Iterator grItr = examMap.keySet().iterator();
						while (grItr.hasNext()) {
							String id = (String) grItr.next();
							row.createCell((short) (maxNo + counter1) ).setCellValue(examMap.get(id));
							counter1++;
							
						}
					}else{   //modified by priyatham --start--
						for(int i=0;i<prvExamDetDispCounter;i++){
							row.createCell((short) (maxNo + counter1) ).setCellValue(" ");
							counter1++;
						}
					}//--end--		
					TreeMap<String, String> uniMap = candidateSearch.getUniMap();
					if(uniMap!= null && uniMap.size() > 0){
						Iterator uItr = uniMap.keySet().iterator();
						while (uItr.hasNext()) {
							String id = (String) uItr.next();
							row.createCell((short) (maxNo + counter1) ).setCellValue(uniMap.get(id));
							counter1++;
							
						}
					}else{   //modified by priyatham --start--
						for(int i=0;i<prvExamDetDispCounter;i++){
							row.createCell((short) (maxNo + counter1) ).setCellValue(" ");
							counter1++;
						}
					}//--end--
					TreeMap<String, String> instMap = candidateSearch.getInstMap();
					if(instMap!= null && instMap.size() > 0){
						Iterator iItr = instMap.keySet().iterator();
						while (iItr.hasNext()) {
							String id = (String) iItr.next();
							row.createCell((short) (maxNo + counter1) ).setCellValue(instMap.get(id));
							counter1++;
							
						}
					}else{   //modified by priyatham --start--
						for(int i=0;i<prvExamDetDispCounter;i++){
							row.createCell((short) (maxNo + counter1) ).setCellValue(" ");
							counter1++;
						}
					}//--end--
					TreeMap<String, String> yearMap = candidateSearch.getYearMap();
					if(yearMap!= null && yearMap.size() > 0){
						Iterator yItr = yearMap.keySet().iterator();
						while (yItr.hasNext()) {
							String id = (String) yItr.next();
							row.createCell((short) (maxNo + counter1) ).setCellValue(yearMap.get(id));
							counter1++;
							
						}
					}else{   //modified by priyatham --start--
						for(int i=0;i<prvExamDetDispCounter;i++){
							row.createCell((short) (maxNo + counter1) ).setCellValue(" ");
							counter1++;
						}
					}//--end--	
				}

				if(candidateSearchTO.getIntDateTimeDisp() != null && candidateSearchTO.getIntDateTimeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
					TreeMap<String, String> intDateMap = candidateSearch.getIntDateMap();
					if(intDateMap!= null && intDateMap.size() > 0){
						Iterator iItr = intDateMap.keySet().iterator();
						while (iItr.hasNext()) {
							String id = (String) iItr.next();
							row.createCell((short) (maxNo + counter1) ).setCellValue(intDateMap.get(id));
							counter1++;
						}
					}else{   //modified by priyatham --start--
						for(int i=0;i<dateTimeDispCount;i++){
							row.createCell((short) (maxNo + counter1) ).setCellValue(" ");
							counter1++;
						}
					}//--end--		
					
					TreeMap<String, String> intTimeMap = candidateSearch.getIntTimeMap();
					if(intTimeMap!= null && intTimeMap.size() > 0){
						Iterator iItr = intTimeMap.keySet().iterator();
						while (iItr.hasNext()) {
							String id = (String) iItr.next();
							row.createCell((short) (maxNo + counter1) ).setCellValue(intTimeMap.get(id));
							counter1++;
							
						}
					}else{   //modified by priyatham --start--
						for(int i=0;i<dateTimeDispCount;i++){
							row.createCell((short) (maxNo + counter1) ).setCellValue(" ");
							counter1++;
						}
					}//--end--		
				}
				
				if(candidateSearchTO.getPreReqDisp() != null && candidateSearchTO.getPreReqDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
					TreeMap<String, String> pObMarMap = candidateSearch.getpExamMap();
					if(pObMarMap!= null && pObMarMap.size() > 0){
						Iterator grItr = pObMarMap.keySet().iterator();
						while (grItr.hasNext()) {
							String id = (String) grItr.next();
							row.createCell((short) (maxNo + counter1) ).setCellValue(pObMarMap.get(id));
							counter1++;
							
						}
					}else{   //modified by priyatham --start--
						for(int i=0;i<preReqDispCount;i++){
							row.createCell((short) (maxNo + counter1) ).setCellValue(" ");
							counter1++;
						}
					}//--end--			
					TreeMap<String, String> pRollMap = candidateSearch.getpRollMap();
					if(pRollMap!= null && pRollMap.size() > 0){
						Iterator uItr = pRollMap.keySet().iterator();
						while (uItr.hasNext()) {
							String id = (String) uItr.next();
							row.createCell((short) (maxNo + counter1) ).setCellValue(pRollMap.get(id));
							counter1++;
							
						}
					}else{   //modified by priyatham --start--
						for(int i=0;i<preReqDispCount;i++){
							row.createCell((short) (maxNo + counter1) ).setCellValue(" ");
							counter1++;
						}
					}//--end--
					TreeMap<String, String> mopMap = candidateSearch.getpMopMap();
					if(mopMap!= null && mopMap.size() > 0){
						Iterator iItr = mopMap.keySet().iterator();
						while (iItr.hasNext()) {
							String id = (String) iItr.next();
							row.createCell((short) (maxNo + counter1) ).setCellValue(mopMap.get(id));
							counter1++;
							
						}
					}else{   //modified by priyatham --start--
						for(int i=0;i<preReqDispCount;i++){
							row.createCell((short) (maxNo + counter1) ).setCellValue(" ");
							counter1++;
						}
					}//--end--
					TreeMap<String, String> yopMap = candidateSearch.getpYopMap();
					if(yopMap!= null && yopMap.size() > 0){
						Iterator yItr = yopMap.keySet().iterator();
						while (yItr.hasNext()) {
							String id = (String) yItr.next();
							row.createCell((short) (maxNo + counter1) ).setCellValue(yopMap.get(id));
							counter1++;
						}
					}else{   //modified by priyatham --start--
						for(int i=0;i<preReqDispCount;i++){
							row.createCell((short) (maxNo + counter1) ).setCellValue(" ");
							counter1++;
						}
					}//--end--	
				}
				
				/*if(candidateSearchTO.getPrvExamDetDisp() != null && candidateSearchTO.getPrvExamDetDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
					TreeMap<String, String> perMap = candidateSearch.getDocTypePerMap();
					if(perMap!= null && perMap.size() > 0){
						Iterator grItr = perMap.keySet().iterator();
						while (grItr.hasNext()) {
							String id = (String) grItr.next();
							row.createCell((short) (maxNo + counter1) ).setCellValue(perMap.get(id));
							counter1++;
							
						}
					}
					TreeMap<String, String> examMap = candidateSearch.getDocTypeExamNameMap();
					if(examMap!= null && examMap.size() > 0){
						Iterator grItr = examMap.keySet().iterator();
						while (grItr.hasNext()) {
							String id = (String) grItr.next();
							row.createCell((short) (maxNo + counter1) ).setCellValue(examMap.get(id));
							counter1++;
							
						}
					}		
					TreeMap<String, String> uniMap = candidateSearch.getUniMap();
					if(uniMap!= null && uniMap.size() > 0){
						Iterator uItr = uniMap.keySet().iterator();
						while (uItr.hasNext()) {
							String id = (String) uItr.next();
							row.createCell((short) (maxNo + counter1) ).setCellValue(uniMap.get(id));
							counter1++;
							
						}
					}
					TreeMap<String, String> instMap = candidateSearch.getInstMap();
					if(instMap!= null && instMap.size() > 0){
						Iterator iItr = instMap.keySet().iterator();
						while (iItr.hasNext()) {
							String id = (String) iItr.next();
							row.createCell((short) (maxNo + counter1) ).setCellValue(instMap.get(id));
							counter1++;
							
						}
					}		
				}*/
//				changes made by priyatham --start--
				
				/*if(candidateSearchTO.getPreReqDisp() != null && candidateSearchTO.getPreReqDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
					TreeMap<String, String> pObMarMap = candidateSearch.getpExamMap();
					if(pObMarMap!= null && pObMarMap.size() > 0){
						Iterator grItr = pObMarMap.keySet().iterator();
						while (grItr.hasNext()) {
							String id = (String) grItr.next();
							row.createCell((short) (maxNo + counter1) ).setCellValue(pObMarMap.get(id));
							counter1++;
							
						}
					}		
					TreeMap<String, String> pRollMap = candidateSearch.getpRollMap();
					if(pRollMap!= null && pRollMap.size() > 0){
						Iterator uItr = pRollMap.keySet().iterator();
						while (uItr.hasNext()) {
							String id = (String) uItr.next();
							row.createCell((short) (maxNo + counter1) ).setCellValue(pRollMap.get(id));
							counter1++;
							
						}
					}
					TreeMap<String, String> mopMap = candidateSearch.getpMopMap();
					if(mopMap!= null && mopMap.size() > 0){
						Iterator iItr = mopMap.keySet().iterator();
						while (iItr.hasNext()) {
							String id = (String) iItr.next();
							row.createCell((short) (maxNo + counter1) ).setCellValue(mopMap.get(id));
							counter1++;
							
						}
					}
					TreeMap<String, String> yopMap = candidateSearch.getpYopMap();
					if(yopMap!= null && yopMap.size() > 0){
						Iterator yItr = yopMap.keySet().iterator();
						while (yItr.hasNext()) {
							String id = (String) yItr.next();
							row.createCell((short) (maxNo + counter1) ).setCellValue(yopMap.get(id));
							counter1++;
						}
					}	
				}
				*/
//				priyatham --end--
				row.createCell((short) (maxNo + counter1) ).setCellValue(" ");
			}
			fos = new FileOutputStream(excelFile,true);
			wb.write(fos);
			fos.flush();
			fos.close();
			
			String csvdestination = prop.getProperty(CMSConstants.CSV_DESTINATION);
			//File to store data in form of CSV
			File fCSV = new File(request.getRealPath("")+ "//TempFiles//"+csvdestination);

			OutputStream os = (OutputStream)new FileOutputStream(fCSV);
			
			String encoding = "UTF8";
			OutputStreamWriter osw = new OutputStreamWriter(os, encoding);
			BufferedWriter bWriter = new BufferedWriter(osw);

			//Excel document to be imported
//			String excelfilename = "c:/admissionReport.xls";
			WorkbookSettings ws = new WorkbookSettings();
			ws.setLocale(new Locale("en", "EN"));
			Workbook w = Workbook.getWorkbook(new File(request.getRealPath("")+ "//TempFiles//"+destination),ws);

			// Gets the sheets from workbook
			for (int scount = 0; scount < w.getNumberOfSheets(); scount++)
			{
			Sheet s = w.getSheet(scount);

//			bw.write(s.getName());
//			bw.newLine();

			Cell[] rowcsv = null;

			// Gets the cells from sheet
			for (int i = 0 ; i < s.getRows() ; i++)
			{
				rowcsv = s.getRow(i);

			if (rowcsv.length >= 0)
			{
//				bWriter.write(rowcsv[0].getContents());
			for (int j = 0; j < rowcsv.length; j++)
			{
			
				bWriter.write(rowcsv[j].getContents());
				bWriter.write(',');
			}
			}
			bWriter.newLine();
			}
			}
			
			bWriter.flush();
			bWriter.close();	
			
//			 response.setContentType("application/msexcel");
//		      response.setHeader("Content-Disposition",
//		      "attachment;filename="+csvdestination);
		      File file1 = new File(request.getRealPath("")+ "//TempFiles//"+csvdestination);
		      FileInputStream fileIn = new FileInputStream(file1);		
		      
//		      ServletOutputStream out = response.getOutputStream();
		       
		      byte[] outputByte = new byte[fileIn.available()];
		      fileIn.read(outputByte);
		      HttpSession session = request.getSession();
		      session.setAttribute(CMSConstants.CSV_BYTES, outputByte);
//		      while(fileIn.read(outputByte, 0, 4096) != -1)
//		      {
//		      	out.write(outputByte, 0, 4096);
//		      }		      
//		      fileIn.
//		      fileIn.close();		   
//		      out.flush();
//		      out.close();	
	
		}catch(Exception e){
			
			//log

		}
		}
		
	}
	
	/**
	 * @param studentSearchBo
	 * @return
	 * This classes is used to convert BO's to TO's
	 * @throws Exception 
	 */
	public static List<CandidateSearchTO> convertBoToTo(List studentSearchBo) throws Exception {

		List<CandidateSearchTO> candidateSearchTOList = new ArrayList<CandidateSearchTO>();
		if (studentSearchBo != null) {
			Iterator itrstudentBO = studentSearchBo.iterator();		
			int admId=0;
			Set<Integer> idSet=new HashSet<Integer>();
			while (itrstudentBO.hasNext()) {
				Object[] object = (Object[]) itrstudentBO.next();
				AdmAppln admAppln = (AdmAppln)object[0];
				
//				Student student = (Student)object[2];
				CandidateSearchTO candidateSearchTO = new CandidateSearchTO();	
				admId=admAppln.getId();
				if(admAppln.getApplnNo()!=0){
					candidateSearchTO.setApplnNo(admAppln.getApplnNo());					
				}
				
				if(admAppln.getTotalWeightage()!=null){
					candidateSearchTO.setTotalWeightage(admAppln.getTotalWeightage().doubleValue());				
				}
				if(admAppln.getPersonalData()!=null && admAppln.getPersonalData().getFirstName()!=null){
					candidateSearchTO.setName(admAppln.getPersonalData().getFirstName());
					if(admAppln.getPersonalData()!=null && admAppln.getPersonalData().getMiddleName()!=null){
						candidateSearchTO.setName(admAppln.getPersonalData().getFirstName()+" "+admAppln.getPersonalData().getMiddleName());
					}
					if(admAppln.getPersonalData()!=null && admAppln.getPersonalData().getLastName()!=null){
						candidateSearchTO.setName(admAppln.getPersonalData().getFirstName()+" "+admAppln.getPersonalData().getLastName());
					}
					if(admAppln.getPersonalData()!=null && admAppln.getPersonalData().getMiddleName()!=null && admAppln.getPersonalData().getLastName()!=null){
						candidateSearchTO.setName(admAppln.getPersonalData().getFirstName()+" "+admAppln.getPersonalData().getMiddleName()+" "+admAppln.getPersonalData().getLastName());
					}

				}
				if(admAppln.getPersonalData()!=null && admAppln.getPersonalData().getDateOfBirth()!=null){
					candidateSearchTO.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(admAppln.getPersonalData().getDateOfBirth().toString(), "yyyy-mm-dd", "dd-mm-yyyy"));
				}
				if(admAppln.getPersonalData()!=null && admAppln.getPersonalData().getGender()!=null){
					candidateSearchTO.setGender(admAppln.getPersonalData().getGender());
				}
				if(admAppln.getPersonalData()!=null && admAppln.getPersonalData().getBloodGroup()!=null){
					candidateSearchTO.setBloodGroup(admAppln.getPersonalData().getBloodGroup());
				}
				if(admAppln.getChallanRefNo()!=null){
					candidateSearchTO.setChallanNo(admAppln.getChallanRefNo());
				}
				if(admAppln.getPersonalData()!=null && admAppln.getPersonalData().getEmail()!=null){
					candidateSearchTO.setEmail(admAppln.getPersonalData().getEmail());
				}
				//mary Modifications.
				if(admAppln.getPersonalData()!=null && admAppln.getPersonalData().getIsHandicapped()!=null){
					candidateSearchTO.setIsHandicaped(admAppln.getPersonalData().getIsHandicapped());
				}
				if(admAppln.getPersonalData()!=null && admAppln.getPersonalData().getHandicappedDescription()!=null){
					candidateSearchTO.setHandicapDetails(admAppln.getPersonalData().getHandicappedDescription());
				}
				if(admAppln.getCreatedDate()!=null ){
					candidateSearchTO.setAppliedDate(CommonUtil.ConvertStringToDateFormat(admAppln.getCreatedDate().toString(), "yyyy-mm-dd", "dd-mm-yyyy"));
				}
				if(admAppln.getDate()!=null ){
					candidateSearchTO.setChallanPaymentDate(CommonUtil.ConvertStringToDateFormat(admAppln.getDate().toString(), "yyyy-mm-dd", "dd-mm-yyyy"));
				}
				if(object.length>3 && (object[3] instanceof FeePayment)){
					FeePayment feepay = (FeePayment)object[3];
					if(feepay.getBillNo() != null ){
						candidateSearchTO.setChallanNo(feepay.getBillNo());
					}
					if(feepay.getFeePaidDate() != null ){
						candidateSearchTO.setFeeChallanDate(CommonUtil.ConvertStringToDateFormat(feepay.getFeePaidDate().toString(), "yyyy-mm-dd", "dd-mm-yyyy"));
					}
					if(feepay.getTotalFeePaid() != null ){
						candidateSearchTO.setTotalFeePaid(String.valueOf(feepay.getTotalFeePaid()));
					}
				}
				
				
				
				
				
				
				
				
				//mary code ends
				if(admAppln.getStudents()!= null && admAppln.getStudents().size() > 0){
					Iterator<Student> stItr = admAppln.getStudents().iterator();
					while(stItr.hasNext()){
						Student student = stItr.next();
						candidateSearchTO.setRollNo(student.getRollNo());
						candidateSearchTO.setRegNo(student.getRegisterNo());
					}
				}
				if(!idSet.contains(admId)){
				candidateSearchTOList.add(candidateSearchTO);
				idSet.add(admId);
				}
			}
		}
		return candidateSearchTOList;
	
	}
	/**
	 * @param studentSearchBo
	 * @return
	 * This classes is used to convert BO's to TO's
	 * @throws Exception 
	 */
	public static List<CandidateSearchTO> convertBoToToExcel(List studentSearchBo, CandidateSearchForm StudentSearchForm,List<Integer> admIds,CandidateSearchTO displayTo) throws Exception {
		//by giri
		List<Integer> studentIds=null;
		if(displayTo.getPhotoUploadedDisp() != null && displayTo.getPhotoUploadedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
			studentIds=new ArrayList<Integer>();
			File folder = new File("/opt/PHOTOS/StudentPhotos");
			File[] listOfFiles = folder.listFiles();
			if(listOfFiles!=null){
				  for (int i = 0; i < listOfFiles.length; i++) {
				      if (listOfFiles[i].isFile()) {
				    	  studentIds.add(Integer.parseInt(listOfFiles[i].getName().trim().substring(0, listOfFiles[i].getName().trim().indexOf("."))));
				      } 
				    }
			}
		}
		//end by giri
		IInterviewDefinitionTransaction defTransaction = new InterviewDefinitionTransactionImpl();
		int pid=0;
		if(StudentSearchForm.getProgramId()!=null && !StudentSearchForm.getProgramId().isEmpty()){
			pid=Integer.parseInt(StudentSearchForm.getProgramId());
		}
		
		TreeMap<String, String> subRoundMap = new TreeMap<String, String>();
		TreeMap<String, String> mainRoundMap = new TreeMap<String, String>();
		TreeMap<String, String> docTypeHeadMap =new TreeMap<String, String>();
		TreeMap<String,String> intTypeHeadMap=new TreeMap<String, String>();
		TreeMap<String,String> preReqMap=new TreeMap<String, String>();
		List<String> commentsNameList=new ArrayList<String>();
		if(displayTo.getInterviewResultDisp() != null && displayTo.getInterviewResultDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
		InterviewResultReportTO interviewResultReportTOs;
		List<InterviewProgramCourse> intDefCourses = defTransaction.getInterviewTypebyProgram(pid, Integer.parseInt(StudentSearchForm.getYear()));
		Iterator<InterviewProgramCourse> prgCrsItr = intDefCourses.iterator();
		while (prgCrsItr.hasNext()) {
			InterviewProgramCourse interviewProgramCourse = (InterviewProgramCourse) prgCrsItr.next();
			if(interviewProgramCourse.getInterviewSubRoundses()!= null && interviewProgramCourse.getInterviewSubRoundses().size() > 0){
				Iterator<InterviewSubRounds> subItr = interviewProgramCourse.getInterviewSubRoundses().iterator();
				while (subItr.hasNext()) {
					InterviewSubRounds interviewSubRounds = (InterviewSubRounds) subItr.next();
					interviewResultReportTOs = new InterviewResultReportTO();
					for(int i=1;i<=interviewSubRounds.getNoOfInterviewsPerPanel();i++){
					if(!subRoundMap.containsKey(interviewSubRounds.getName().trim()+String.valueOf(i))){
						subRoundMap.put(interviewSubRounds.getName().trim()+String.valueOf(i), Integer.toString(interviewProgramCourse.getNoOfInterviewsPerPanel()));
						}
					if(!commentsNameList.contains(interviewSubRounds.getName().trim()+"Comments"+String.valueOf(i))){
						commentsNameList.add(interviewSubRounds.getName().trim()+"Comments"+String.valueOf(i));
					}
					}
					interviewResultReportTOs.setInterviewRound(interviewSubRounds.getName().trim());
					
				}
				
			}
			else
			{
				interviewResultReportTOs = new InterviewResultReportTO();
				for(int i=1;i<=interviewProgramCourse.getNoOfInterviewsPerPanel();i++){
				if(!mainRoundMap.containsKey(interviewProgramCourse.getName().trim()+String.valueOf(i))){
					mainRoundMap.put(interviewProgramCourse.getName().trim()+String.valueOf(i), Integer.toString(interviewProgramCourse.getNoOfInterviewsPerPanel()));
				}
				if(!commentsNameList.contains(interviewProgramCourse.getName().trim()+"Comments"+String.valueOf(i))){
					commentsNameList.add(interviewProgramCourse.getName().trim()+"Comments"+String.valueOf(i));
				}
				}
				interviewResultReportTOs.setInterviewRound(interviewProgramCourse.getName().trim());
			}
		}
		}
		ICandidateSearchTxnImpl transaction=CandidateSearchTxnImpl.getInstance();
		if((displayTo.getPreReqDisp() != null && displayTo.getPreReqDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)) || 
				(displayTo.getPreReqDisp() != null && displayTo.getPreReqDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))){
//		IDocumentTypeTransaction iDTransaction = new DocumentTypeTransactionImpl();
		String query=getDocTypeHeadingMapquery(StudentSearchForm);
		docTypeHeadMap =  transaction.getDocTypesMap(query);
		}
		if(displayTo.getIntDateTimeDisp() != null && displayTo.getIntDateTimeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
		String intQuery=getInterviewTypeHeadingMapquery(StudentSearchForm);
		intTypeHeadMap=transaction.getInterviewTypeHeading(intQuery);
		}
		if(displayTo.getPreReqDisp() != null && displayTo.getPreReqDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
			String preReqQuery=getPreRequisteQuery(StudentSearchForm);
			preReqMap=transaction.getPreRequisteMap(preReqQuery);
		}
		TreeMap<String, String> intTimeMap =null;
		TreeMap<String, String> intDateMap =null;
		TreeMap<String, String> docTypePerMap =null;
		TreeMap<String, String> docTypeExamNameMap =null;
		TreeMap<String, String> uniMap =null;
		TreeMap<String, String> instMap =null;
		TreeMap<String,String> yearMap =null;
		TreeMap<String, String> pExamMap =null;
		TreeMap<String, String> pHeadMap =null;
		TreeMap<String, String> pRollMap =null;
		TreeMap<String,String> pYopMap =null;
		TreeMap<String,String> pMopMap =null;
		TreeMap<String, String> resultMap =null;
		TreeMap<String, String> commentsMap=null;
		
		
		
		
		List<CandidateSearchTO> candidateSearchTOList = new ArrayList<CandidateSearchTO>();
		CandidateSearchTO candidateSearchTO=null;
		AdmAppln admAppln=null;
		PersonalData pd=null;
		Student student=null;
		FeePayment feepay=null;
		if (studentSearchBo != null) {
			Iterator itrstudentBO = studentSearchBo.iterator();
			int admId=0;
			Set<Integer> idSet=new HashSet<Integer>();
			while (itrstudentBO.hasNext()) {
   		Object[] object = (Object[]) itrstudentBO.next();
			admAppln = (AdmAppln)object[0];
//			if(admAppln.getApplnNo()==12550152){// remove this
			admId=admAppln.getId();
			pd=admAppln.getPersonalData();
			student = (Student)object[2];
			
			candidateSearchTO = new CandidateSearchTO();
			if(displayTo.getRollNoDisp() != null && displayTo.getRollNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if( student.getRollNo()!=null){
				candidateSearchTO.setRollNo(student.getRollNo());
			}
			if(displayTo.getRegNoDisp() != null && displayTo.getRegNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if( student.getRegisterNo()!=null){
				candidateSearchTO.setRegNo(student.getRegisterNo());
			}
			if(displayTo.getIsAdmittedDisp()!= null && displayTo.getIsAdmittedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if(student.getIsAdmitted()!=null){
				if(student.getIsAdmitted()){
					candidateSearchTO.setIsAdmitted("YES");
				}else{
					candidateSearchTO.setIsAdmitted("NO");
				}
			}
			if(displayTo.getCurrentClassDisp()!= null && displayTo.getCurrentClassDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if( student.getClassSchemewise()!=null && student.getClassSchemewise().getClasses()!=null){
				candidateSearchTO.setCurrentClass(student.getClassSchemewise().getClasses().getName());
			}
			
			if(((displayTo.getUserNameDisp()!=null && displayTo.getUserNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)) || (displayTo.getPasswordDisp()!=null && displayTo.getPasswordDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)) ) && student.getStudentLogins()!=null){
				Iterator<StudentLogin> slIterator=student.getStudentLogins().iterator();
				StudentLogin studentLogin=null;
				if(slIterator.hasNext()){
					studentLogin=slIterator.next();
					if(studentLogin!=null){
						candidateSearchTO.setUserName(studentLogin.getUserName());
						if(studentLogin.getPassword()!=null && !studentLogin.getPassword().isEmpty()){
							candidateSearchTO.setPassword(EncryptUtil.getInstance().decryptDES(studentLogin.getPassword()));
						}else{
							candidateSearchTO.setPassword("");
						}
					}
				}
			}
			//Added By Manu Change admission status and add remarks
			if(displayTo.getAdmissionStatusDisp()!= null && displayTo.getAdmissionStatusDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if(admAppln.getIsCancelled()!=null && admAppln.getIsCancelled()){
				candidateSearchTO.setAdmissionStatus("Cancelled");
			}else if(admAppln.getStudents()!=null){
				Iterator<Student> itr=admAppln.getStudents().iterator();
				while (itr.hasNext()) {
					Student stu = (Student) itr.next();
					if(stu.getIsHide()!=null && stu.getIsHide()){
					  candidateSearchTO.setAdmissionStatus("Hidden");
					}
					else if(stu.getExamStudentDetentionRejoinDetails()!=null){
						Iterator<ExamStudentDetentionRejoinDetails> itrr=stu.getExamStudentDetentionRejoinDetails().iterator();
						while (itrr.hasNext()) {
							ExamStudentDetentionRejoinDetails examStu = (ExamStudentDetentionRejoinDetails) itrr.next();
							if(examStu.getDetain()!=null  && examStu.getDiscontinued()!=null && examStu.getDetain() && examStu.getDiscontinued()){
								candidateSearchTO.setAdmissionStatus("Detained,Discontinued");
							}
							else if(examStu.getDetain()!=null && examStu.getDetain()){
								candidateSearchTO.setAdmissionStatus("Detained");
							}
							else if(examStu.getDiscontinued()!=null && examStu.getDiscontinued()){
								candidateSearchTO.setAdmissionStatus("Discontinued");
							}
						}
						if(candidateSearchTO.getAdmissionStatus()==null || candidateSearchTO.getAdmissionStatus().isEmpty()){
							 if(stu.getIsAdmitted()!=null && stu.getIsAdmitted()){
								candidateSearchTO.setAdmissionStatus("Admitted");
							}
						}
					}
				}
			}
			if(candidateSearchTO.getAdmissionStatus()==null || candidateSearchTO.getAdmissionStatus().isEmpty()){
			  if(admAppln.getIsWaiting()!=null && admAppln.getIsWaiting()){
				candidateSearchTO.setAdmissionStatus("Watilisted");
			  }else if(admAppln.getNotSelected()!=null && admAppln.getNotSelected()){
				candidateSearchTO.setAdmissionStatus("Not Selected");
			  }else if(admAppln.getIsSelected()!=null && admAppln.getIsSelected()){
				candidateSearchTO.setAdmissionStatus("Selected");
			  }
			}
			//Added By Manu
			if(displayTo.getRemarksDisp()!= null && displayTo.getRemarksDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( admAppln.getAdmStatus()!=null){
					candidateSearchTO.setRemarks(admAppln.getAdmStatus());
				}
			//End Manu Code
			if(displayTo.getAdmissionDateDisp() != null && displayTo.getAdmissionDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if(admAppln.getAdmissionDate()!=null){
				candidateSearchTO.setAdmissionDate(CommonUtil.getStringDate(admAppln.getAdmissionDate()));
			}
			if(displayTo.getClassNameDisp() != null && displayTo.getClassNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if( student.getClassSchemewise()!=null && student.getClassSchemewise().getClasses()!=null && student.getClassSchemewise().getClasses().getName()!=null){
				candidateSearchTO.setClassStudent(student.getClassSchemewise().getClasses().getName());
			}
			if(displayTo.getApplnNoDisp() != null && displayTo.getApplnNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if(admAppln.getApplnNo()!=0){
				candidateSearchTO.setApplnNo(admAppln.getApplnNo());					
			}
			if(displayTo.getCastCategoryDisp() != null && displayTo.getCastCategoryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if( pd.getCaste()!=null && pd.getCaste().getName()!=null){
				candidateSearchTO.setCastCategory(pd.getCaste().getName());				
			}
			
			if(displayTo.getCastCategoryOtherDisp() != null && displayTo.getCastCategoryOtherDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if( pd.getCasteOthers()!=null){
				candidateSearchTO.setCastCategoryOther(pd.getCasteOthers());				
			}
			
			if(displayTo.getMotherTongueDisp() != null && displayTo.getMotherTongueDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if(pd.getMotherTongue()!=null && pd.getMotherTongue().getName()!=null){
				candidateSearchTO.setMotherTongue(pd.getMotherTongue().getName());
			}
			
			if(displayTo.getSecondLanguageDisp() != null && displayTo.getSecondLanguageDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if( pd.getSecondLanguage()!=null){
				candidateSearchTO.setSecondLanguage(pd.getSecondLanguage());
			}
			
			if(displayTo.getCourseNameDisp() != null && displayTo.getCourseNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if(admAppln.getCourseBySelectedCourseId()!=null){
				candidateSearchTO.setCourseName(admAppln.getCourseBySelectedCourseId().getName());
			}
			
			if(displayTo.getProgramNameDisp() != null && displayTo.getProgramNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if(admAppln.getCourseBySelectedCourseId()!=null && admAppln.getCourseBySelectedCourseId().getProgram()!=null && admAppln.getCourseBySelectedCourseId().getProgram().getName()!=null){
				candidateSearchTO.setProgramName(admAppln.getCourseBySelectedCourseId().getProgram().getName());					
			}
			
			if(displayTo.getProgramTypeNameDisp() != null && displayTo.getProgramTypeNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if(admAppln.getCourseBySelectedCourseId()!=null && admAppln.getCourseBySelectedCourseId().getProgram()!=null && admAppln.getCourseBySelectedCourseId().getProgram().getProgramType()!=null && admAppln.getCourseBySelectedCourseId().getProgram().getProgramType().getName()!=null){
				candidateSearchTO.setProgramTypeName(admAppln.getCourseBySelectedCourseId().getProgram().getProgramType().getName());					
			}
			
			if(displayTo.getJournalNoDisp() != null && displayTo.getJournalNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if(admAppln.getJournalNo()!=null){
				candidateSearchTO.setJournalNo(admAppln.getJournalNo());					
			}
			
			if(displayTo.getAcedamicYearDisp() != null && displayTo.getAcedamicYearDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if(admAppln.getAppliedYear()!=null){
				candidateSearchTO.setAppliedYear(String.valueOf(admAppln.getAppliedYear())+"-"+String.valueOf(admAppln.getAppliedYear()+1));					
			}
			
			if(displayTo.getAdmittedThroughDisp() != null && displayTo.getAdmittedThroughDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if(admAppln.getAdmittedThrough()!=null && admAppln.getAdmittedThrough().getName()!=null){
				candidateSearchTO.setAdmittedThrough(admAppln.getAdmittedThrough().getName());					
			}
			
			if(displayTo.getTotalWeightageDisp() != null && displayTo.getTotalWeightageDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if(admAppln.getTotalWeightage()!=null){
				candidateSearchTO.setTotalWeightage(admAppln.getTotalWeightage().doubleValue());				
			}
			
			if(displayTo.getNameDisp() != null && displayTo.getNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if( pd.getFirstName()!=null){
				candidateSearchTO.setName(pd.getFirstName()+" "+(pd.getMiddleName()!=null?pd.getMiddleName():"")+" "+(pd.getLastName()!=null?pd.getLastName():""));
			}
			
			if(displayTo.getDateOfBirthDisp() != null && displayTo.getDateOfBirthDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if( pd.getDateOfBirth()!=null){
				candidateSearchTO.setDateOfBirth(pd.getDateOfBirth().toString());
			}
			
			if(displayTo.getGenderDisp() != null && displayTo.getGenderDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if( pd.getGender()!=null){
				candidateSearchTO.setGender(pd.getGender());
			}
			
			if(displayTo.getBloodGroupDisp() != null && displayTo.getBloodGroupDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if( pd.getBloodGroup()!=null){
				candidateSearchTO.setBloodGroup(pd.getBloodGroup());
			}
			
			if(displayTo.getChallanNoDisp() != null && displayTo.getChallanNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if(admAppln.getChallanRefNo()!=null){
				candidateSearchTO.setChallanNo(admAppln.getChallanRefNo());
			}
			
			if(displayTo.getEmailDisp() != null && displayTo.getEmailDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if( pd.getEmail()!=null){
				candidateSearchTO.setEmail(pd.getEmail());
			}
			if(displayTo.getFeeChallanDateDisp() != null && displayTo.getFeeChallanDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if(admAppln.getDate()!=null){
				candidateSearchTO.setChallanDate(CommonUtil.getStringDate(admAppln.getDate()));
			}
			
			if(admAppln.getAmount()!=null){
				candidateSearchTO.setChallanAmount(String.valueOf(admAppln.getAmount()));					
			}
			
			if(displayTo.getResidentCategoryDisp() != null && displayTo.getResidentCategoryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if( pd.getResidentCategory()!=null && pd.getResidentCategory().getName()!=null){
				candidateSearchTO.setResidentCategory(pd.getResidentCategory().getName());
			}
			
			if(displayTo.getReligionDisp() != null && displayTo.getReligionDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if( pd.getReligion()!=null && pd.getReligion().getName()!=null){
				candidateSearchTO.setReligion(pd.getReligion().getName());
			}
			
			if(displayTo.getReligionOtherDisp() != null && displayTo.getReligionOtherDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if( pd.getReligionOthers()!=null){
				candidateSearchTO.setReligionOther(pd.getReligionOthers());
			}
			
			if(displayTo.getSubReligionDisp() != null && displayTo.getSubReligionDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if( pd.getReligionSection()!=null && pd.getReligionSection().getName()!=null){
				candidateSearchTO.setSubReligion(pd.getReligionSection().getName());
			}
			
			if(displayTo.getSubReligionOtherDisp() != null && displayTo.getSubReligionOtherDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if( pd.getReligionSectionOthers()!=null){
				candidateSearchTO.setSubReligionOther(pd.getReligionSectionOthers());
			}
			
			if(displayTo.getPlaceOfBirthDisp() != null && displayTo.getPlaceOfBirthDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if( pd.getBirthPlace()!=null){
				candidateSearchTO.setPlaceOfBirth(pd.getBirthPlace());
			}
			
			if(displayTo.getStateOfBirthDisp() != null && displayTo.getStateOfBirthDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if( pd.getStateByStateId()!=null && pd.getStateByStateId().getName()!=null){
				candidateSearchTO.setStateOfBirth(pd.getStateByStateId().getName());
			}
			
			if(displayTo.getStateOfBirthOtherDisp() != null && displayTo.getStateOfBirthOtherDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if( pd.getStateOthers()!=null){
				candidateSearchTO.setStateOfBirthOther(pd.getStateOthers());
			}
			
			if(displayTo.getBelongsToDisp() != null && displayTo.getBelongsToDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if( pd.getRuralUrban()!=null){
				candidateSearchTO.setBelongsTo(pd.getRuralUrban().toString());
			}
			
			if(displayTo.getCountryOfBirthDisp() != null && displayTo.getCountryOfBirthDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if( pd.getCountryByCountryId()!=null && pd.getCountryByCountryId().getName()!=null){
				candidateSearchTO.setCountryOfBirth(pd.getCountryByCountryId().getName());
			}
			
			if(displayTo.getNationalityDisp() != null && displayTo.getNationalityDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if( pd.getNationality()!=null){
				candidateSearchTO.setNationality(pd.getNationality().getName());
			}
			
			if(displayTo.getStudentPhoneNumberDisp() != null && displayTo.getStudentPhoneNumberDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				candidateSearchTO.setStudentPhoneNo((pd.getPhNo1()!=null?pd.getPhNo1():"")+(pd.getPhNo2()!=null?pd.getPhNo2():"")+(pd.getPhNo3()!=null?pd.getPhNo3():""));
			
			if(displayTo.getStudentMobileNumberDisp() != null && displayTo.getStudentMobileNumberDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				candidateSearchTO.setStudentMobileNo((pd.getMobileNo1()!=null?pd.getMobileNo1():"")+(pd.getMobileNo2()!=null?pd.getMobileNo2():"")+(pd.getMobileNo3()!=null?pd.getMobileNo3():""));
			
			if(displayTo.getPassportNumberDisp() != null && displayTo.getPassportNumberDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if( pd.getPassportNo()!=null){
				candidateSearchTO.setPassportNo(pd.getPassportNo());
			}
			
			if(displayTo.getPassportIssuingCountryDisp() != null && displayTo.getPassportIssuingCountryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if( pd.getCountryByPassportCountryId()!=null&& pd.getCountryByPassportCountryId().getName()!=null){
				candidateSearchTO.setPassportIssuingCountry(pd.getCountryByPassportCountryId().getName());
			}
			
			if(displayTo.getPassportValidUpToDisp() != null && displayTo.getPassportValidUpToDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if( pd.getPassportValidity()!=null){
				candidateSearchTO.setPassportValidUpTo(CommonUtil.getStringDate(pd.getPassportValidity()));
			}
			
			if(displayTo.getSeatNoDisp() != null && displayTo.getSeatNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if(admAppln.getSeatNo()!= null && !admAppln.getSeatNo().trim().isEmpty()){
				if(admAppln.getExamCenter()!=null){
					if(admAppln.getExamCenter().getSeatNoPrefix()!=null){
						candidateSearchTO.setSeatNo(admAppln.getExamCenter().getSeatNoPrefix()+String.format("%04d",Integer.parseInt(admAppln.getSeatNo())));
					}else{
						candidateSearchTO.setSeatNo(String.format("%04d", Integer.parseInt(admAppln.getSeatNo())));
					}
				}else{
						candidateSearchTO.setSeatNo(String.format("%04d", Integer.parseInt(admAppln.getSeatNo())));
				}
			}
			
			
			if( admAppln.getCandidatePreferences()!=null){
				CandidatePreference cPre= null;
				Iterator itr =  admAppln.getCandidatePreferences().iterator();
				while(itr.hasNext()){
					cPre = (CandidatePreference)itr.next();
					if(cPre.getPrefNo()==1){
						if(displayTo.getFirstPreferenceProgramTypeDisp() != null && displayTo.getFirstPreferenceProgramTypeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
							if(cPre.getCourse().getProgram().getProgramType().getName()!=null){
								candidateSearchTO.setFirstPrePT(cPre.getCourse().getProgram().getProgramType().getName());
							}
						if(displayTo.getFirstPreferenceProgramDisp() != null && displayTo.getFirstPreferenceProgramDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
							if(cPre.getCourse().getProgram().getName()!=null){
								candidateSearchTO.setFirstPreProgram(cPre.getCourse().getProgram().getName());
							}
						if(displayTo.getFirstPreferenceCourseDisp() != null && displayTo.getFirstPreferenceCourseDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
							if(cPre.getCourse().getName()!=null){
								candidateSearchTO.setFirstCourse(cPre.getCourse().getName());
							}
					}
					if(cPre.getPrefNo()==2){
						if(displayTo.getSecondPreferenceProgramTypeDisp() != null && displayTo.getSecondPreferenceProgramTypeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
							if(cPre.getCourse().getProgram().getProgramType().getName()!=null){
								candidateSearchTO.setSecondPrePT(cPre.getCourse().getProgram().getProgramType().getName());
							}
						if(displayTo.getSecondPreferenceProgramDisp() != null && displayTo.getSecondPreferenceProgramDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
							if(cPre.getCourse().getProgram().getName()!=null){
								candidateSearchTO.setSecondPreProgram(cPre.getCourse().getProgram().getName());
							}
						if(displayTo.getSecondPreferenceCourseDisp() != null && displayTo.getSecondPreferenceCourseDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
							if(cPre.getCourse().getName()!=null){
								candidateSearchTO.setSecondCourse(cPre.getCourse().getName());
							}
					}
					if(cPre.getPrefNo()==3){
						if(displayTo.getThirdPreferenceProgramTypeDisp() != null && displayTo.getThirdPreferenceProgramTypeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
							if(cPre.getCourse().getProgram().getProgramType().getName()!=null){
								candidateSearchTO.setThirdPrePT(cPre.getCourse().getProgram().getProgramType().getName());
							}
						if(displayTo.getThirdPreferenceProgramDisp() != null && displayTo.getThirdPreferenceProgramDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
							if(cPre.getCourse().getProgram().getName()!=null){
								candidateSearchTO.setThirdPreProgram(cPre.getCourse().getProgram().getName());
							}
						if(displayTo.getThirdPreferenceCourseDisp() != null && displayTo.getThirdPreferenceCourseDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
							if(cPre.getCourse().getName()!=null){
								candidateSearchTO.setThirdCourse(cPre.getCourse().getName());
							}	
					}
				}					
			}
			if( admAppln.getApplicantWorkExperiences()!=null){
				/*code modified by sudhir*/
				getTotalYearsOfExperience(admAppln.getApplicantWorkExperiences(),candidateSearchTO,displayTo);
				/*code modified by sudhir*/
			}	
			if(displayTo.getPermanentAddressLine1Disp() != null && displayTo.getPermanentAddressLine1Disp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getPermanentAddressLine1()!=null){
					candidateSearchTO.setPermanentAddressline1(pd.getPermanentAddressLine1());					
				}
			if(displayTo.getPermanentAddressLine2Disp() != null && displayTo.getPermanentAddressLine2Disp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getPermanentAddressLine2()!=null){
					candidateSearchTO.setPermanentAddressline2(pd.getPermanentAddressLine2());					
				}				
			if(displayTo.getPermanentCityDisp() != null && displayTo.getPermanentCityDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getCityByPermanentAddressCityId()!=null){
					candidateSearchTO.setPermanentCity(pd.getCityByPermanentAddressCityId());					
				}				
			if(displayTo.getPermanentStateDisp() != null && displayTo.getPermanentStateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getStateByPermanentAddressStateId()!=null && pd.getStateByPermanentAddressStateId().getName()!=null){
					candidateSearchTO.setPermanentState(pd.getStateByPermanentAddressStateId().getName());					
				}
			if(displayTo.getPermanentStateOthersDisp() != null && displayTo.getPermanentStateOthersDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getPermanentAddressStateOthers()!=null){
					candidateSearchTO.setPermanentStateOther(pd.getPermanentAddressStateOthers());					
				}				
			if(displayTo.getPermanentCountryDisp() != null && displayTo.getPermanentCountryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getCountryByPermanentAddressCountryId()!=null && pd.getCountryByPermanentAddressCountryId().getName()!=null){
					candidateSearchTO.setPermanentCountry(pd.getCountryByPermanentAddressCountryId().getName());					
				}
			if(displayTo.getPermanentZipCodeDisp() != null && displayTo.getPermanentZipCodeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getPermanentAddressZipCode()!=null){
					candidateSearchTO.setPermanentZipCode(pd.getPermanentAddressZipCode());					
				}				
			if(displayTo.getCurrentAddressLine1Disp() != null && displayTo.getCurrentAddressLine1Disp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getCurrentAddressLine1()!=null){
					candidateSearchTO.setCurrentAddressline1(pd.getCurrentAddressLine1());					
				}
			if(displayTo.getCurrentAddressLine2Disp() != null && displayTo.getCurrentAddressLine2Disp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getCurrentAddressLine2()!=null){
					candidateSearchTO.setCurrentAddressline2(pd.getCurrentAddressLine2());					
				}				
			if(displayTo.getCurrentCityDisp() != null && displayTo.getCurrentCityDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getCityByCurrentAddressCityId()!=null){
					candidateSearchTO.setCurrentCity(pd.getCityByCurrentAddressCityId());					
				}
			if(displayTo.getCurrentStateDisp() != null && displayTo.getCurrentStateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getStateByCurrentAddressStateId()!=null && pd.getStateByCurrentAddressStateId().getName()!=null){
					candidateSearchTO.setCurrentState(pd.getStateByCurrentAddressStateId().getName());					
				}	
			if(displayTo.getCurrentStateOthersDisp() != null && displayTo.getCurrentStateOthersDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getCurrentAddressStateOthers()!=null){
					candidateSearchTO.setCurrentStateOther(pd.getCurrentAddressStateOthers());					
				}				
			if(displayTo.getCurrentCountryDisp() != null && displayTo.getCurrentCountryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getCountryByCurrentAddressCountryId()!=null && pd.getCountryByCurrentAddressCountryId().getName()!=null){
					candidateSearchTO.setCurrentCountry(pd.getCountryByCurrentAddressCountryId().getName());					
				}
			if(displayTo.getCurrentZipCodeDisp() != null && displayTo.getCurrentZipCodeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getCurrentAddressZipCode()!=null){
					candidateSearchTO.setCurrentZipCode(pd.getCurrentAddressZipCode());					
				}
			if(displayTo.getFathersNameDisp() != null && displayTo.getFathersNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getFatherName()!=null){
					candidateSearchTO.setFatherName(pd.getFatherName());					
				}			
			if(displayTo.getFathersEducationDisp() != null && displayTo.getFathersEducationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getFatherEducation()!=null){
					candidateSearchTO.setFatherEducation(pd.getFatherEducation());					
				}
			if(displayTo.getFathersIncomeDisp() != null && displayTo.getFathersIncomeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getIncomeByFatherIncomeId()!=null){
					candidateSearchTO.setFatherIncome(pd.getIncomeByFatherIncomeId().getIncomeRange());					
				}				
			if(displayTo.getFathersCurrencyDisp() != null && displayTo.getFathersCurrencyDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getCurrencyByFatherIncomeCurrencyId()!=null){
					candidateSearchTO.setFatherIncomeCurrency(pd.getCurrencyByFatherIncomeCurrencyId().getName());					
				}				
			if(displayTo.getFathersOccupationDisp() != null && displayTo.getFathersOccupationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getOccupationByFatherOccupationId()!=null){
					candidateSearchTO.setFatherOccupation(pd.getOccupationByFatherOccupationId().getName());					
				}else if(pd.getOtherOccupationFather() != null){
					candidateSearchTO.setFatherOccupation(pd.getOtherOccupationFather());
				}
			if(displayTo.getFathersEmailDisp() != null && displayTo.getFathersEmailDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getFatherEmail()!=null){
					candidateSearchTO.setFatherEmail(pd.getFatherEmail());					
				}				
			
			if(displayTo.getMothersNameDisp() != null && displayTo.getMothersNameDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getMotherName()!=null){
					candidateSearchTO.setMotherName(pd.getMotherName());					
				}				
			if(displayTo.getMothersEducationDisp() != null && displayTo.getMothersEducationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getMotherEducation()!=null){
					candidateSearchTO.setMotherEducation(pd.getMotherEducation());					
				}				
			if(displayTo.getMothersIncomeDisp() != null && displayTo.getMothersIncomeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getIncomeByMotherIncomeId()!=null){
					candidateSearchTO.setMotherIncome(pd.getIncomeByMotherIncomeId().getIncomeRange());					
				}
			if(displayTo.getMothersCurrencyDisp() != null && displayTo.getMothersCurrencyDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getCurrencyByMotherIncomeCurrencyId()!=null){
					candidateSearchTO.setMotherIncomeCurrency(pd.getCurrencyByMotherIncomeCurrencyId().getName());					
				}				
			if(displayTo.getMothersOccupationDisp() != null && displayTo.getMothersOccupationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getOccupationByMotherOccupationId()!=null){
					candidateSearchTO.setMotherOccupation(pd.getOccupationByMotherOccupationId().getName());					
				}else if(pd.getOtherOccupationMother() != null){
					candidateSearchTO.setMotherOccupation(pd.getOtherOccupationMother());
				}
			if(displayTo.getMothersEmailDisp() != null && displayTo.getMothersEmailDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getMotherEmail()!=null){
					candidateSearchTO.setMotherEmail(pd.getMotherEmail());					
				}				
			if(displayTo.getParentAdressLine1Disp() != null && displayTo.getParentAdressLine1Disp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getParentAddressLine1()!=null){
					candidateSearchTO.setParentAdressLine1(pd.getParentAddressLine1());					
				}
			if(displayTo.getParentAdressLine2Disp() != null && displayTo.getParentAdressLine2Disp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getParentAddressLine2()!=null){
					candidateSearchTO.setParentAdressLine2(pd.getParentAddressLine2());					
				}
			if(displayTo.getParentAdressLine3Disp() != null && displayTo.getParentAdressLine3Disp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getParentAddressLine3()!=null){
					candidateSearchTO.setParentAdressLine3(pd.getParentAddressLine3());					
				}
			if(displayTo.getParentCityDisp() != null && displayTo.getParentCityDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getCityByParentAddressCityId()!=null){
					candidateSearchTO.setParentCity(pd.getCityByParentAddressCityId());					
				}				
			if(displayTo.getParentStateDisp() != null && displayTo.getParentStateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getStateByParentAddressStateId()!=null && pd.getStateByParentAddressStateId().getName()!=null){
					candidateSearchTO.setParentState(pd.getStateByParentAddressStateId().getName());			
				}
			if(displayTo.getParentStateOtherDisp() != null && displayTo.getParentStateOtherDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getParentAddressStateOthers()!=null){
					candidateSearchTO.setParentStateOther(pd.getParentAddressStateOthers());			
				}
			if(displayTo.getParentCountryDisp() != null && displayTo.getParentCountryDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getCountryByParentAddressCountryId()!=null && pd.getCountryByParentAddressCountryId().getName()!=null){
					candidateSearchTO.setParentCountry(pd.getCountryByParentAddressCountryId().getName());			
				}
			if(displayTo.getParentZipCodeDisp() != null && displayTo.getParentZipCodeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getParentAddressZipCode()!=null){
					candidateSearchTO.setParentZipCode(pd.getParentAddressZipCode());			
				}
			if(displayTo.getParentPhoneDisp() != null && displayTo.getParentPhoneDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				candidateSearchTO.setParentPhone((pd.getParentPh1()!=null?pd.getParentPh1():"")+(pd.getParentPh2()!=null?pd.getParentPh2():"")+(pd.getParentPh3()!=null?pd.getParentPh3():""));
			if(displayTo.getParentMobileNoDisp() != null && displayTo.getParentMobileNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				candidateSearchTO.setParentMobileNo((pd.getParentMob1()!=null?pd.getParentMob1():"")+(pd.getParentMob2()!=null?pd.getParentMob2():"")+(pd.getParentMob3()!=null?pd.getParentMob3():""));
			if(displayTo.getExamCenterDisp() != null && displayTo.getExamCenterDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if(admAppln.getExamCenter()!=null && admAppln.getExamCenter().getCenter()!=null){
						candidateSearchTO.setExamCenter(admAppln.getExamCenter().getCenter());
				}
			if(displayTo.getIsHandicapedDisp()!= null && displayTo.getIsHandicapedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getIsHandicapped()!=null)
					candidateSearchTO.setIsHandicaped(pd.getIsHandicapped());
			if(displayTo.getHandicapDetailsDisp()!= null && displayTo.getHandicapDetailsDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getHandicappedDescription()!=null){
					candidateSearchTO.setHandicapDetails(pd.getHandicappedDescription());
				}
			if(displayTo.getAppliedDateDisp()!= null && displayTo.getAppliedDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if(admAppln.getCreatedDate()!=null ){
					candidateSearchTO.setAppliedDate(CommonUtil.ConvertStringToDateFormat(admAppln.getCreatedDate().toString(), "yyyy-mm-dd", "dd-mm-yyyy"));
				}
			if(displayTo.getChallanPaymentDateDisp()!= null && displayTo.getChallanPaymentDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if(admAppln.getDate()!=null ){
					candidateSearchTO.setChallanPaymentDate(CommonUtil.ConvertStringToDateFormat(admAppln.getDate().toString(), "yyyy-mm-dd", "dd-mm-yyyy"));
				}
			
			if(object.length>4){
				feepay= (FeePayment)object[3];
				if(displayTo.getFeeChallanNoDisp()!= null && displayTo.getFeeChallanNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
					if(feepay.getBillNo()!=null ){
						candidateSearchTO.setChallanNo(feepay.getBillNo());
					}
				if(displayTo.getFeeChallanDateDisp()!= null && displayTo.getFeeChallanDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
					if(feepay.getFeePaidDate()!=null ){
						candidateSearchTO.setChallanDate(CommonUtil.ConvertStringToDateFormat(feepay.getFeePaidDate().toString(), "yyyy-mm-dd", "dd-mm-yyyy"));
					}
				if(displayTo.getTotalFeePaidDisp()!= null && displayTo.getTotalFeePaidDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
					if(feepay.getTotalFeePaid()!=null ){
						candidateSearchTO.setTotalFeePaid(String.valueOf(feepay.getTotalFeePaid()));
					}
			}
			//mary code ends
			
			if(displayTo.getIsSelectedDisp()!=null && displayTo.getIsSelectedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))			
				if(admAppln.getIsSelected()!=null){
					if(admAppln.getIsSelected()){
						candidateSearchTO.setIsSelected("YES");
					}else
						candidateSearchTO.setIsSelected("NO");
				}
			if(displayTo.getIsFinalMeritApprovedDisp()!=null && displayTo.getIsFinalMeritApprovedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if(admAppln.getIsFinalMeritApproved()!=null){
					if(admAppln.getIsFinalMeritApproved()){
						candidateSearchTO.setIsFinalMeritApproved("YES");
					}else
						candidateSearchTO.setIsFinalMeritApproved("NO");
				}
			
			//added for challan Verification display
			if(displayTo.getChallanVerfiedDisp()!= null && displayTo.getChallanVerfiedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if(admAppln.getIsChallanVerified()!=null){
					if(admAppln.getIsChallanVerified()){
						candidateSearchTO.setIsChallanVerified("YES");
					}else
						candidateSearchTO.setIsChallanVerified("NO");
				}

			//added for photo status display
			if(displayTo.getPhotoDisp()!= null && displayTo.getPhotoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				if(admIds.contains(admAppln.getId())){
					candidateSearchTO.setPhoto("YES");
				}
				else
					candidateSearchTO.setPhoto("NO");
			}
			
			//added for display of specialization prefered and back logs
			if((displayTo.getSpecializationPreferedDisp()!=null && displayTo.getSpecializationPreferedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)) || ((displayTo.getBackLogsDisp()!=null && displayTo.getBackLogsDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))))
			if(admAppln.getStudentSpecializationPrefered()!=null && !admAppln.getStudentSpecializationPrefered().isEmpty()){
				Iterator<StudentSpecializationPrefered> itrSpecialization=admAppln.getStudentSpecializationPrefered().iterator();
				StudentSpecializationPrefered studentSpecializationPrefered=null;
				while (itrSpecialization.hasNext()) {
					studentSpecializationPrefered = (StudentSpecializationPrefered) itrSpecialization.next();
					if(displayTo.getSpecializationPreferedDisp()!=null && displayTo.getSpecializationPreferedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
						if(studentSpecializationPrefered.getSpecializationPrefered()!=null){
							candidateSearchTO.setSpecializationPrefered(studentSpecializationPrefered.getSpecializationPrefered());
						}
					if(displayTo.getBackLogsDisp()!=null && displayTo.getBackLogsDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
						if(studentSpecializationPrefered.getBackLogs()!=null){
							if(studentSpecializationPrefered.getBackLogs())
								candidateSearchTO.setBackLogs("YES");
							else 
								candidateSearchTO.setBackLogs("NO");
						}
				}
			}
			if(displayTo.getNewbackLogsDisp()!=null && displayTo.getNewbackLogsDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				if(admAppln.getAdmapplnAdditionalInfo() != null && !admAppln.getAdmapplnAdditionalInfo().isEmpty()){
					List<AdmapplnAdditionalInfo> list = new ArrayList<AdmapplnAdditionalInfo>(admAppln.getAdmapplnAdditionalInfo());
					AdmapplnAdditionalInfo info = list.get(0);
					if(info.getBackLogs() != null){
						if(info.getBackLogs()){
							candidateSearchTO.setNewbackLogs("YES");
						}else{
							candidateSearchTO.setNewbackLogs("NO");
						}
					}
				}
			}
			//added for Canceled Status display
			if(displayTo.getCanceledDisp()!= null && displayTo.getCanceledDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if(admAppln.getIsCancelled()!=null){
					if(admAppln.getIsCancelled()){
						candidateSearchTO.setCanceled("YES");
					}else
						candidateSearchTO.setCanceled("NO");
				}
			
			candidateSearchTO.setAdmStatus(admAppln.getAdmStatus());
			
			// Modification Reqiured from here
			if(displayTo.getInterviewResultDisp() != null && displayTo.getInterviewResultDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				resultMap = new TreeMap<String, String>();
				commentsMap=new TreeMap<String, String>();
				if(admAppln.getInterviewResults()!= null){
					InterviewResultReportTO interviewResultReportTO;
					
					Iterator<InterviewResult> itr = admAppln.getInterviewResults().iterator();
					InterviewResult interviewResult=null;
					while (itr.hasNext()) {
						int count=1;
						interviewResult = (InterviewResult) itr.next();
						Iterator<InterviewResultDetail> detItr = interviewResult.getInterviewResultDetails().iterator();
						int resultId = interviewResult.getId();
						while (detItr.hasNext()) {
							InterviewResultDetail interviewResultDetail = (InterviewResultDetail) detItr
									.next();
							interviewResultReportTO = new InterviewResultReportTO();
							if(interviewResult.getInterviewSubRounds()!= null && interviewResult.getInterviewSubRounds().getName()!= null){
								interviewResultReportTO.setInterviewRound(interviewResult.getInterviewSubRounds().getName());
							}else
							{
								if(interviewResult.getInterviewProgramCourse() != null){
									interviewResultReportTO.setInterviewRound(interviewResult.getInterviewProgramCourse().getName());
								}
								else {
									continue;
								}
							}
							if(interviewResultDetail.getGrade()!= null){
								interviewResultReportTO.setGrade(interviewResultDetail.getGrade().getGrade());
							}
							else if(interviewResultDetail.getPercentage()!= null)
							{
								interviewResultReportTO.setGrade(interviewResultDetail.getPercentage().toString());
							}
							interviewResultReportTO.setInterviewId(resultId);
								resultMap.put(interviewResultReportTO.getInterviewRound().trim()+String.valueOf(count), interviewResultReportTO.getGrade());
								if(interviewResultDetail.getComments()!=null){
								commentsMap.put(interviewResultReportTO.getInterviewRound().trim()+"Comments"+String.valueOf(count), interviewResultDetail.getComments());
								}
								count++;
								
						}
					}
				}
				Iterator mainItr = mainRoundMap.keySet().iterator();
				while (mainItr.hasNext()) {
					String round = (String) mainItr.next();
					if(!resultMap.containsKey(round)){
						resultMap.put(round, null);
					}
				}
				
				Iterator subItr = subRoundMap.keySet().iterator();
				while (subItr.hasNext()) {
					String round = (String) subItr.next();
					if(!resultMap.containsKey(round)){
						resultMap.put(round, null);
					}
				}
				
				Iterator commentsItr = commentsNameList.iterator();
				while (commentsItr.hasNext()) {
					String commentsName = (String) commentsItr.next();
					if(!commentsMap.containsKey(commentsName)){
						commentsMap.put(commentsName, null);
					}
				}
				
				candidateSearchTO.setGradeMap(resultMap);
				candidateSearchTO.setCommentsMap(commentsMap);
			}
			
			if(displayTo.getPreReqDisp() != null && displayTo.getPreReqDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
			pExamMap = new TreeMap<String, String>();
			pHeadMap = new TreeMap<String, String>();
			pRollMap = new TreeMap<String, String>();
			pYopMap =new TreeMap<String, String>();
			pMopMap =new TreeMap<String, String>();
			
			Iterator pItr =  preReqMap.keySet().iterator();
			String id=null;
			while (pItr.hasNext()) {
				 id= (String) pItr.next();
				pExamMap.put(id, docTypeHeadMap.get(id));
				pHeadMap.put(id, preReqMap.get(id));
				pRollMap.put(id, "");
				pYopMap.put(id, "");
				pMopMap.put(id,"");
			}
			
			Iterator<CandidatePrerequisiteMarks> pitr=admAppln.getCandidatePrerequisiteMarks().iterator();
			CandidatePrerequisiteMarks bo=null;
			while (pitr.hasNext()) {
				bo = (CandidatePrerequisiteMarks) pitr.next();
				String preType="";
				if(bo.getPrerequisite()!=null && bo.getPrerequisite().getName()!=null ){
					preType=bo.getPrerequisite().getName().trim();
				}
				if(!preType.isEmpty()){
					if(bo.getExamMonth()!=null){
						pMopMap.put(preType,monthMap.get(bo.getExamMonth()));
					}
					if(bo.getExamYear()!=null){
						pYopMap.put(preType, bo.getExamYear().toString());
					}
					if(bo.getRollNo()!=null){
						pRollMap.put(preType, bo.getRollNo());
					}
					if(bo.getPrerequisiteMarksObtained()!=null){
						pExamMap.put(preType, bo.getPrerequisiteMarksObtained().toString());
					}
				}
			}
			
			candidateSearchTO.setpMopMap(pMopMap);
			candidateSearchTO.setpYopMap(pYopMap);
			candidateSearchTO.setpRollMap(pRollMap);
			candidateSearchTO.setpExamMap(pExamMap);
			candidateSearchTO.setpHeadMap(pHeadMap);
			}
			if(displayTo.getPrvExamDetDisp() != null && displayTo.getPrvExamDetDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
			docTypePerMap = new TreeMap<String, String>();
			docTypeExamNameMap = new TreeMap<String, String>();
			uniMap = new TreeMap<String, String>();
			instMap = new TreeMap<String, String>();
			yearMap =new TreeMap<String, String>();
			
			Iterator<String> mItr =  docTypeHeadMap.keySet().iterator();
			String id=null;
			while (mItr.hasNext()) {
				id = (String) mItr.next();
				docTypePerMap.put(id, docTypeHeadMap.get(id));
				docTypeExamNameMap.put(id, docTypeHeadMap.get(id));
				uniMap.put(id, "");
				instMap.put(id, "");
				yearMap.put(id,"");
			}
			
			Iterator<EdnQualification> setItr = pd.getEdnQualifications().iterator();
			EdnQualification ednQualification =null;
			while (setItr.hasNext()) {
				ednQualification = (EdnQualification) setItr .next();
				String docType= "";
				if(ednQualification.getDocChecklist()!= null && ednQualification.getDocChecklist().getDocType()!= null && ednQualification.getDocChecklist().getDocType().getName()!= null && ednQualification.getDocChecklist().getIsMarksCard()){
					docType = ednQualification.getDocChecklist().getDocType().getName().trim(); 
				}
				if(docType!= null && !docType.trim().isEmpty()){
					if(ednQualification.getPercentage()!= null && !ednQualification.getPercentage().toString().isEmpty()){
						docTypePerMap.put(docType, ednQualification.getPercentage().toString());
					}
					
					if(ednQualification.getDocTypeExams()!= null && ednQualification.getDocTypeExams().getName() != null){
						docTypeExamNameMap.put(docType, ednQualification.getDocTypeExams().getName());
					}
					else if(ednQualification.getDocTypeExams() == null && ednQualification.getDocChecklist()!= null && ednQualification.getDocChecklist().getDocType()!= null){
						docTypeExamNameMap.put(docType, "");
					}
					if(ednQualification.getDocChecklist().getDocType()!= null && ednQualification.getDocChecklist().getDocType().getName()!= null){
						uniMap.put(ednQualification.getDocChecklist().getDocType().getName().trim(), ((ednQualification.getUniversityOthers()!= null && !ednQualification.getUniversityOthers().trim().isEmpty())?ednQualification.getUniversityOthers():(ednQualification.getUniversity()!=null?ednQualification.getUniversity().getName():"")));
						instMap.put(ednQualification.getDocChecklist().getDocType().getName().trim(), ((ednQualification.getInstitutionNameOthers()!= null && !ednQualification.getInstitutionNameOthers().trim().isEmpty())?ednQualification.getInstitutionNameOthers():(ednQualification.getCollege()!=null?ednQualification.getCollege().getName():"")));
					}
					if(ednQualification.getYearPassing()!=null && ednQualification.getYearPassing()>0){
						yearMap.put(ednQualification.getDocChecklist().getDocType().getName().trim(), String.valueOf(ednQualification.getYearPassing()));
					}
				}
			}
			candidateSearchTO.setDocTypePerMap(docTypePerMap);
			candidateSearchTO.setDocTypeExamNameMap(docTypeExamNameMap);
			candidateSearchTO.setUniMap(uniMap);
			candidateSearchTO.setInstMap(instMap);
			candidateSearchTO.setYearMap(yearMap);
			}
			if(displayTo.getIntDateTimeDisp() != null && displayTo.getIntDateTimeDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			if(admAppln.getInterviewCards()!=null){
				intDateMap = new TreeMap<String, String>();
				intTimeMap = new TreeMap<String, String>();
				
				Iterator<String> itr =  intTypeHeadMap.keySet().iterator();
				String id=null;
				while (itr.hasNext()) {
					id=(String)itr.next();
					intDateMap.put(id, "");
					intTimeMap.put(id, "");
				}
				Iterator<InterviewCard> ctr=admAppln.getInterviewCards().iterator();
				InterviewCard interviewCard=null;
				while (ctr.hasNext()) {
					interviewCard = (InterviewCard) ctr.next();
					if(interviewCard.getInterview()!=null){
						String type=interviewCard.getInterview().getInterview().getInterviewProgramCourse().getName();
						if(type!=null && !type.isEmpty()){
							if(interviewCard.getTime()!=null){
								intTimeMap.put(type, interviewCard.getTime());
							}
							if(interviewCard.getInterview().getDate()!=null){
								intDateMap.put(type,CommonUtil.getStringDate(interviewCard.getInterview().getDate()));
							}
						}
					}
				}
				candidateSearchTO.setIntDateMap(intDateMap);
				candidateSearchTO.setIntTimeMap(intTimeMap);
			}
			/* code added by sudhir */
			if(displayTo.getApplicantFeedbackDisp()!=null && displayTo.getApplicantFeedbackDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				if(admAppln.getAdmapplnAdditionalInfo()!=null){
					Iterator<AdmapplnAdditionalInfo> iterator = admAppln.getAdmapplnAdditionalInfo().iterator();
					while (iterator.hasNext()) {
						AdmapplnAdditionalInfo admapplnAdditionalInfo = (AdmapplnAdditionalInfo) iterator .next();
						if(admapplnAdditionalInfo.getApplicantFeedback()!=null && !admapplnAdditionalInfo.getApplicantFeedback().toString().isEmpty()){
							candidateSearchTO.setApplicantFeedback(admapplnAdditionalInfo.getApplicantFeedback().getName());
						}
					}
				}
			}
			
			/* code added by sudhir */
			
			/* code added by chandra */
			if(displayTo.getComedkDisp()!=null && displayTo.getComedkDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				if(admAppln.getAdmapplnAdditionalInfo()!=null){
					Iterator<AdmapplnAdditionalInfo> iterator = admAppln.getAdmapplnAdditionalInfo().iterator();
					while (iterator.hasNext()) {
						AdmapplnAdditionalInfo admapplnAdditionalInfo = (AdmapplnAdditionalInfo) iterator .next();
						if(admapplnAdditionalInfo.getIsComedk()){
							candidateSearchTO.setComedk("yes");
						}
					}
				}
			}
			
			/* code added by chandra */
			
			//Added by Cimi
			if(displayTo.getUniversityEmailDisp() != null && displayTo.getUniversityEmailDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( pd.getUniversityEmail()!=null){
					candidateSearchTO.setUniversityEmail(pd.getUniversityEmail());
				}
			//start by giri
			if(displayTo.getPhotoUploadedDisp() != null && displayTo.getPhotoUploadedDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if( studentIds.contains(student.getId())){
					candidateSearchTO.setPhotoUploaded("Yes");
				}else{
					candidateSearchTO.setPhotoUploaded("No");
				}
			//end by giri
			//Added by Mary
			if(displayTo.getSelectionProcessDateDisp() != null && displayTo.getSelectionProcessDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
				if(admAppln.getInterScheduleSelection()!=null && admAppln.getInterScheduleSelection().getSelectionProcessDate()!=null){
					candidateSearchTO.setSelectionProcessDate(CommonUtil.getStringDate(admAppln.getInterScheduleSelection().getSelectionProcessDate()));
				}
			
			
			//			candidateSearchTOList.add(candidateSearchTO);
			if(!idSet.contains(admId)){
				candidateSearchTOList.add(candidateSearchTO);
				idSet.add(admId);
			}
//			}// remove this
		}
		}
		return candidateSearchTOList;
	}
	/**
	 * @param studentSearchForm
	 * @return
	 * @throws Exception
	 */
	private static String getPreRequisteQuery(CandidateSearchForm studentSearchForm) throws Exception {
		String query="select c.prerequisite" +
				" from CoursePrerequisite c " +
				" where c.isActive=1" +
				" and c.prerequisite.isActive=1";
		if(studentSearchForm.getProgramId()!=null && !studentSearchForm.getProgramId().isEmpty())
				query=query+" and c.course.program.id="+studentSearchForm.getProgramId();
		if(studentSearchForm.getCourseId()!=null && !studentSearchForm.getCourseId().isEmpty()){
			query=query+" and c.course.id="+studentSearchForm.getCourseId();
		}
		return query;
	}


	public static CandidateSearchTO getSelectedColumns(
			List<ConfigureColumnForReportTO> configRepprtList) {
		CandidateSearchTO candidateSearchTO = new CandidateSearchTO();
		if(configRepprtList!=null){
			Iterator<ConfigureColumnForReportTO> itrConfig = configRepprtList.iterator();

			while(itrConfig.hasNext()){		
					
				ConfigureColumnForReportTO configReportsColumn = itrConfig.next();
				String columnName = configReportsColumn.getColumnName().trim();
				boolean flag = configReportsColumn.getShowColumn();				
					short position = (short)configReportsColumn.getPosition().shortValue();	
					
					
				if(flag){
					if(columnName.equalsIgnoreCase(CMSConstants.ROLLNO)){
						candidateSearchTO.setRollNoDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setRollNoPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.REGNO)){
						candidateSearchTO.setRegNoDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setRegNoPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.ADMISSION_DATE)){
						candidateSearchTO.setAdmissionDateDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setAdmissionDatePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.CLASS_NAME)){
						candidateSearchTO.setClassNameDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setClassNamePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.SECOND_LANGUAG)){
						candidateSearchTO.setSecondLanguageDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setSecondLanguagePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.PROGTYPE_NAME)){
						candidateSearchTO.setProgramTypeNameDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setProgramTypeNamePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.PROG_NAME)){
						candidateSearchTO.setProgramNameDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setProgramNamePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.COURSE_NAME)){
						candidateSearchTO.setCourseNameDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setCourseNamePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.JOURNAL_NO)){
						candidateSearchTO.setJournalNoDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setJournalNoPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.ACEDAMIC_YEAR)){
						candidateSearchTO.setAcedamicYearDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setAcedamicYearPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.ADMITTED_THROUG)){
						candidateSearchTO.setAdmittedThroughDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setAdmittedThroughPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.RESIDENT_CATEGORY)){
						candidateSearchTO.setResidentCategoryDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setResidentCategoryPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.RELIGIN)){
						candidateSearchTO.setReligionDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setReligionPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.RELIGION_OTHER)){
						candidateSearchTO.setReligionOtherDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setReligionOtherPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.SUB_RELIGION)){
						candidateSearchTO.setSubReligionDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setSubReligionPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.SUB_RELIGION_OTHER)){
						candidateSearchTO.setSubReligionOtherDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setSubReligionOtherPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.CAST_CATEGORY)){
						candidateSearchTO.setCastCategoryDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setCastCategoryPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.CAST_CATEGORY_OTHER)){
						candidateSearchTO.setCastCategoryOtherDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setCastCategoryOtherPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.PLACE_OF_BIRTH)){
						candidateSearchTO.setPlaceOfBirthDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setPlaceOfBirthPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.STATE_OF_BIRTH)){
						candidateSearchTO.setStateOfBirthDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setStateOfBirthPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.STATE_OF_BIRTH_OTHER)){
						candidateSearchTO.setStateOfBirthOtherDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setStateOfBirthOtherPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.COUNTRY_OF_BIRTH)){
						candidateSearchTO.setCountryOfBirthDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setCountryOfBirthPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.BELONGS_TO)){
						candidateSearchTO.setBelongsToDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setBelongsToPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.NATIONALIT)){
						candidateSearchTO.setNationalityDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setNationalityPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.STUDENT_PHONE_NUMBER)){
						candidateSearchTO.setStudentPhoneNumberDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setStudentPhoneNumberPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.STUDENT_MOBILE_NUMBER)){
						candidateSearchTO.setStudentMobileNumberDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setStudentMobileNumberPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.PASSPORT_NUMBER)){
						candidateSearchTO.setPassportNumberDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setPassportNumberPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.PASSPORT_ISSUING_COUNTRY)){
						candidateSearchTO.setPassportIssuingCountryDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setPassportIssuingCountryPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.PASSPORT_VALIDUPTO)){
						candidateSearchTO.setPassportValidUpToDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setPassportValidUpToPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.FIRST_PRE_PROGTYPE)){
						candidateSearchTO.setFirstPreferenceProgramTypeDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setFirstPreferenceProgramTypePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.FIRST_PRE_PROG)){
						candidateSearchTO.setFirstPreferenceProgramDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setFirstPreferenceProgramPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.FIRST_PRE_COURSE)){
						candidateSearchTO.setFirstPreferenceCourseDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setFirstPreferenceCoursePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.SECOND_PRE_PROGTYPE)){
						candidateSearchTO.setSecondPreferenceProgramTypeDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setSecondPreferenceProgramTypePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.SECOND_PRE_PROG)){
						candidateSearchTO.setSecondPreferenceProgramDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setSecondPreferenceProgramPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.SECOND_PRE_COURSE)){
						candidateSearchTO.setSecondPreferenceCourseDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setSecondPreferenceCoursePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.THIRD_PRE_PROGTYPE)){
						candidateSearchTO.setThirdPreferenceProgramTypeDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setThirdPreferenceProgramTypePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.THIRD_PRE_PROG)){
						candidateSearchTO.setThirdPreferenceProgramDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setThirdPreferenceProgramPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.THIRD_PRE_COURSE)){
						candidateSearchTO.setThirdPreferenceCourseDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setThirdPreferenceCoursePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.FIRST_NAME_OF_ORG)){
						candidateSearchTO.setFirstNameofOrganisationDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setFirstNameofOrganisationPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.FIRST_DESIG)){
						candidateSearchTO.setFirstDesignationDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setFirstDesignationPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.FIRST_FROM_DATE)){
						candidateSearchTO.setFirstFromDateDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setFirstFromDatePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.FIRST_TO_DATE)){
						candidateSearchTO.setFirstToDateDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setFirstToDatePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.SECOND_NAME_OF_ORG)){
						candidateSearchTO.setSecondNameofOrganisationDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setSecondNameofOrganisationPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.SECOND_DESIG)){
						candidateSearchTO.setSecondDesignationDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setSecondDesignationPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.SECOND_FROM_DATE)){
						candidateSearchTO.setSecondFromDateDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setSecondFromDatePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.SECOND_TO_DATE)){
						candidateSearchTO.setSecondToDateDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setSecondToDatePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.THIRD_NAME_OF_ORG)){
						candidateSearchTO.setThirdNameofOrganisationDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setThirdNameofOrganisationPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.THIRD_DESIG)){
						candidateSearchTO.setThirdDesignationDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setThirdDesignationPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.THIRD_FROM_DATE)){
						candidateSearchTO.setThirdFromDateDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setThirdFromDatePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.THIRD_TO_DATE)){
						candidateSearchTO.setThirdToDateDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setThirdToDatePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.PERMANENT_ADDR_LINE1)){
						candidateSearchTO.setPermanentAddressLine1Disp(CandidateHelper.DISPLAY);
						candidateSearchTO.setPermanentAddressLine1Pois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.PERMANENT_ADDR_LINE2)){
						candidateSearchTO.setPermanentAddressLine2Disp(CandidateHelper.DISPLAY);
						candidateSearchTO.setPermanentAddressLine2Pois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.PERMANENT_STATE)){
						candidateSearchTO.setPermanentStateDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setPermanentStatePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.PERMANENT_STATE_OTHERS)){
						candidateSearchTO.setPermanentStateOthersDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setPermanentStateOthersPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.PERMANENT_CITY)){
						candidateSearchTO.setPermanentCityDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setPermanentCityPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.PERMANENT_COUNTRY)){
						candidateSearchTO.setPermanentCountryDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setPermanentCountryPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.PERMANENT_ZIP_CODE)){
						candidateSearchTO.setPermanentZipCodeDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setPermanentZipCodePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.CURRENT_ADDR_LINE1)){
						candidateSearchTO.setCurrentAddressLine1Disp(CandidateHelper.DISPLAY);
						candidateSearchTO.setCurrentAddressLine1Pois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.CURRENT_ADDR_LINE2)){
						candidateSearchTO.setCurrentAddressLine2Disp(CandidateHelper.DISPLAY);
						candidateSearchTO.setCurrentAddressLine2Pois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.CURRENT_STATE)){
						candidateSearchTO.setCurrentStateDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setCurrentStatePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.CURRENT_STATE_OTHERS)){
						candidateSearchTO.setCurrentStateOthersDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setCurrentStateOthersPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.CURRENT_CITY)){
						candidateSearchTO.setCurrentCityDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setCurrentCityPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.CURRENT_COUNTRY)){
						candidateSearchTO.setCurrentCountryDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setCurrentCountryPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.CURRENT_ZIP_CODE)){
						candidateSearchTO.setCurrentZipCodeDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setCurrentZipCodePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.FATHERS_NAME)){
						candidateSearchTO.setFathersNameDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setFathersNamePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.FATHERS_EDUCATION)){
						candidateSearchTO.setFathersEducationDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setFathersEducationPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.FATHERS_INCOME)){
						candidateSearchTO.setFathersIncomeDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setFathersIncomePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.FATHERS_CURRENCY)){
						candidateSearchTO.setFathersCurrencyDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setFathersCurrencyPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.FATHERS_OCCUPATION)){
						candidateSearchTO.setFathersOccupationDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setFathersOccupationPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.FATHERS_EMAIL)){
						candidateSearchTO.setFathersEmailDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setFathersEmailPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.MOTHERS_NAME)){
						candidateSearchTO.setMothersNameDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setMothersNamePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.MOTHERS_EDUCATION)){
						candidateSearchTO.setMothersEducationDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setMothersEducationPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.MOTHERS_INCOME)){
						candidateSearchTO.setMothersIncomeDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setMothersIncomePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.MOTHERS_CURRENCY)){
						candidateSearchTO.setMothersCurrencyDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setMothersCurrencyPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.MOTHERS_OCCUPATION)){
						candidateSearchTO.setMothersOccupationDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setMothersOccupationPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.MOTHERS_EMAIL)){
						candidateSearchTO.setMothersEmailDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setMothersEmailPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.PARENT_ADDR_LINE1)){
						candidateSearchTO.setParentAdressLine1Disp(CandidateHelper.DISPLAY);
						candidateSearchTO.setParentAdressLine1Pois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.PARENT_ADDR_LINE2)){
						candidateSearchTO.setParentAdressLine2Disp(CandidateHelper.DISPLAY);
						candidateSearchTO.setParentAdressLine2Pois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.PARENT_ADDR_LINE3)){
						candidateSearchTO.setParentAdressLine3Disp(CandidateHelper.DISPLAY);
						candidateSearchTO.setParentAdressLine3Pois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.PARENT_CITY)){
						candidateSearchTO.setParentCityDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setParentCityPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.PARENT_STATE)){
						candidateSearchTO.setParentStateDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setParentStatePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.PARENT_STATE_OTHER)){
						candidateSearchTO.setParentStateOtherDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setParentStateOtherPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.PARENT_COUNTRY)){
						candidateSearchTO.setParentCountryDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setParentCountryPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.PARENT_ZIP_CODE)){
						candidateSearchTO.setParentZipCodeDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setParentZipCodePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.PARENT_PHONE)){
						candidateSearchTO.setParentPhoneDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setParentPhonePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.PARENT_MOBILE_NUMBER)){
						candidateSearchTO.setParentMobileNoDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setParentMobileNoPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.APPLICATION_NUM)){
						candidateSearchTO.setApplnNoDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setApplnNoPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.STUDENT_NAME)){
						candidateSearchTO.setNameDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setNamePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.DATE_OF_BIRTH)){
						candidateSearchTO.setDateOfBirthDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setDateOfBirthPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.GENDER)){
						candidateSearchTO.setGenderDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setGenderPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.CHALLAN_NUMBER)){
						candidateSearchTO.setChallanNoDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setChallanNoPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.TOTAL_WEIGHTAGE)){
						candidateSearchTO.setTotalWeightageDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setTotalWeightagePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.EMAIL)){
						candidateSearchTO.setEmailDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setEmailPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.BLOOD_GROUP)){
						candidateSearchTO.setBloodGroupDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setBloodGroupPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.REPORT_SEAT_NO)){
						candidateSearchTO.setSeatNoDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setSeatNoPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.REPORT_EXAM_CENTER)){
						candidateSearchTO.setExamCenterDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setExamCenterPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.ADM_REPORT_INTERVIEW_RESULT)){
						candidateSearchTO.setInterviewResultDisp(CandidateHelper.DISPLAY);
					}	
					if(columnName.equalsIgnoreCase(CMSConstants.ADM_REPORT_PREVIOUS_EDU_DET)){
						candidateSearchTO.setPrvExamDetDisp(CandidateHelper.DISPLAY);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.INTERVIEW_DATE_TIME)){
						candidateSearchTO.setIntDateTimeDisp(CandidateHelper.DISPLAY);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.ADM_REPORT_PRE_REQ)){
						candidateSearchTO.setPreReqDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setPreReqDispPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.MOTHERTONGUE)){
						candidateSearchTO.setMotherTongueDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setMotherTonguePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.REPORT_ADDMISSION_STATUS)){
						candidateSearchTO.setAdmissionStatusDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setAdmissionStatusPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.IS_ADMITTED)){
						candidateSearchTO.setIsAdmittedDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setIsAdmittedPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.CURRENT_CLASS)){
						candidateSearchTO.setCurrentClassDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setCurrentClassPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.IS_SELECTED)){
						candidateSearchTO.setIsSelectedDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setIsSelectedPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.IS_FINAL_MERIT_APPROVED)){
						candidateSearchTO.setIsFinalMeritApprovedDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setIsFinalMeritApprovedPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.USER_NAME)){
						candidateSearchTO.setUserNameDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setUserNamePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.PASSWORD)){
						candidateSearchTO.setPasswordDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setPasswordPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.APPLIED_DATE)){
						candidateSearchTO.setAppliedDateDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setAppliedDatePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.CHALLAN_PAYMENT_DATE)){
						candidateSearchTO.setChallanPaymentDateDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setChallanPaymentDatePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.FEE_CHALLAN_DATE)){
						candidateSearchTO.setFeeChallanDateDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setFeeChallanDatePois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.FEE_CHALLAN_NO)){
						candidateSearchTO.setFeeChallanNoDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setFeeChallanNoPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.TOTAL_FEES_PAID)){
						candidateSearchTO.setTotalFeePaidDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setTotalFeePaidPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.IS_HANDICAPED)){
						candidateSearchTO.setIsHandicapedDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setIsHandicapedPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.HANDICAP_DETAILS)){
						candidateSearchTO.setHandicapDetailsDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setHandicapDetailsPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.TOTAL_EXPERIENCE)){
						candidateSearchTO.setTotalExpYearDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setTotalExpYearPois(position);
					}
					//added for challan verification
					if(columnName.equalsIgnoreCase(CMSConstants.CHALLAN_VERIFICATION)){
						candidateSearchTO.setChallanVerfiedDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setChallanVerifiedPois(position);
					}
					//addition for challan verification completed
					
					//added for photo status display
					if(columnName.equalsIgnoreCase(CMSConstants.PHOTO)){
						candidateSearchTO.setPhotoDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setPhotoPois(position);
					}
					//addition for photo status display completed
					
					//added for specialization prefered and backlogs display
					if(columnName.equalsIgnoreCase(CMSConstants.SPECIALIZATION_PREFERED)){
						candidateSearchTO.setSpecializationPreferedDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setSpecializationPreferedPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.BACK_LOGS)){
						candidateSearchTO.setBackLogsDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setBackLogsPois(position);
					}
					//added to display the canceled status of student
					if(columnName.equalsIgnoreCase(CMSConstants.IS_CANCELED)){
						candidateSearchTO.setCanceledDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setCanceledPois(position);
					}
					if(columnName.equalsIgnoreCase(CMSConstants.BACK_LOGS_NEW)){
						candidateSearchTO.setNewbackLogsDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setNewbackLogsPois(position);
					}
					// newly added by sudhir
					if(columnName.equalsIgnoreCase(CMSConstants.APPLICANT_FEEDBACK)){
						candidateSearchTO.setApplicantFeedbackDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setApplicantFeedbackPois(position);
					}
					
					//added by Cimi
					if(columnName.equalsIgnoreCase(CMSConstants.UNIVERSITY_EMAIL)){
						candidateSearchTO.setUniversityEmailDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setUniversityEmailPois(position);
					}
									
					// added by chandra
					if(columnName.equalsIgnoreCase(CMSConstants.COMED_K)){
						candidateSearchTO.setComedkDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setComedkPois(position);
					}
					// added by Manu
					if(columnName.equalsIgnoreCase(CMSConstants.ADMISSION_REMARKS)){
						candidateSearchTO.setRemarksDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setRemarkPois(position);
					}
					// added by Mary
					if(columnName.equalsIgnoreCase(CMSConstants.ADMISSION_SELECTION_PROCESS_DATE)){
						candidateSearchTO.setSelectionProcessDateDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setSelectionProcessDatePois(position);
					}
					// added by giri
					if(columnName.equalsIgnoreCase(CMSConstants.PHOTO_UPLOADED)){
						candidateSearchTO.setPhotoUploadedDisp(CandidateHelper.DISPLAY);
						candidateSearchTO.setPhotoUploadedPois(position);
					}
					//end by giri
				}					
			}
		}
		return candidateSearchTO;
	}
	
	
	public static void convertColumnsBOtoTO(List<ConfigReportsColumn> list,CandidateSearchForm candidateSearchForm){
		ConfigureColumnForReportTO selectedreportTO;
		ConfigureColumnForReportTO unSelectedreportTO;
		List<ConfigureColumnForReportTO> selectedList = new ArrayList<ConfigureColumnForReportTO>();
		List<ConfigureColumnForReportTO> unSelectedList = new ArrayList<ConfigureColumnForReportTO>();
		List<ConfigureColumnForReportTO> fullList = new ArrayList<ConfigureColumnForReportTO>();
		if(list != null && !list.isEmpty()){
			Iterator<ConfigReportsColumn> iterator = list.iterator();
			while (iterator.hasNext()) {
				selectedreportTO = new ConfigureColumnForReportTO();
				unSelectedreportTO = new ConfigureColumnForReportTO();
				ConfigReportsColumn reportsColumn = (ConfigReportsColumn) iterator.next();
				if(candidateSearchForm.getProgramId()!=null && candidateSearchForm.getProgramId().isEmpty() 
					&& reportsColumn.getColumnName()!=null && (!reportsColumn.getColumnName().equalsIgnoreCase("Interview Details") 
						&& !(reportsColumn.getColumnName().equalsIgnoreCase("Previous Educational Details")) 
						&& !(reportsColumn.getColumnName().equalsIgnoreCase("InterviewDateTime"))
						&& !(reportsColumn.getColumnName().equalsIgnoreCase("PreRequisite")))){
				if(reportsColumn.getShowColumn()){
					selectedreportTO.setId(reportsColumn.getId());
					selectedreportTO.setColumnName(reportsColumn.getColumnName());
					selectedreportTO.setReportName(reportsColumn.getReportName());
					selectedreportTO.setShowColumn(Boolean.TRUE);
					selectedList.add(selectedreportTO);
					fullList.add(selectedreportTO);
				}else{
					unSelectedreportTO.setId(reportsColumn.getId());
					unSelectedreportTO.setColumnName(reportsColumn.getColumnName());
					unSelectedreportTO.setReportName(reportsColumn.getReportName());
					unSelectedreportTO.setShowColumn(Boolean.FALSE);
					unSelectedList.add(unSelectedreportTO);
					fullList.add(unSelectedreportTO);
				}
			}else if(candidateSearchForm.getProgramId()!=null && !candidateSearchForm.getProgramId().isEmpty()){
				if(reportsColumn.getShowColumn()){
					selectedreportTO.setId(reportsColumn.getId());
					selectedreportTO.setColumnName(reportsColumn.getColumnName());
					selectedreportTO.setReportName(reportsColumn.getReportName());
					selectedreportTO.setShowColumn(Boolean.TRUE);
					selectedList.add(selectedreportTO);
					fullList.add(selectedreportTO);
				}else{
					unSelectedreportTO.setId(reportsColumn.getId());
					unSelectedreportTO.setColumnName(reportsColumn.getColumnName());
					unSelectedreportTO.setReportName(reportsColumn.getReportName());
					unSelectedreportTO.setShowColumn(Boolean.FALSE);
					unSelectedList.add(unSelectedreportTO);
					fullList.add(unSelectedreportTO);
				}
			}
			}
			candidateSearchForm.setFullList(fullList);
			candidateSearchForm.setSelectedColumnsList(selectedList);
			candidateSearchForm.setUnselectedColumnsList(unSelectedList);
		}
	}	
	
	public static List<ConfigReportsColumn> convertListToBo(CandidateSearchForm studentSearchForm,List originalSelectedList) {
		List<ConfigReportsColumn> newList = new ArrayList<ConfigReportsColumn>();
		ConfigReportsColumn reportsColumn = null;
		if(studentSearchForm.getSelectedColumnsList() != null && !studentSearchForm.getSelectedColumnsList().isEmpty()){
			List originalListArray =  new ArrayList();
			Iterator iterator = studentSearchForm.getSelectedColumnsList().iterator();
			while (iterator.hasNext()) {
				reportsColumn = new ConfigReportsColumn();
				ConfigureColumnForReportTO reportTO = (ConfigureColumnForReportTO) iterator.next();
				if(reportTO!=null){
					reportsColumn.setId(reportTO.getId());
					if(reportTO.getColumnName() != null && !reportTO.getColumnName().isEmpty()){
						reportsColumn.setColumnName(reportTO.getColumnName());
					}
					if(reportTO.getReportName() != null){
						reportsColumn.setReportName(reportTO.getReportName());
					}
					if(reportTO.getShowColumn()){
						reportsColumn.setShowColumn(Boolean.TRUE);
						reportsColumn.setPosition(reportTO.getPosition());
					}	
					reportsColumn.setLastModifiedDate(new Date());

				}
				originalListArray.add(reportTO.getId());
				newList.add(reportsColumn);
			}
			if(studentSearchForm.getUnselectedColumnsList() != null && !studentSearchForm.getUnselectedColumnsList().isEmpty()){				
				Iterator iterator1 = studentSearchForm.getUnselectedColumnsList().iterator();
				while (iterator1.hasNext()) {					
					reportsColumn = new ConfigReportsColumn();
					ConfigureColumnForReportTO reportTO = (ConfigureColumnForReportTO) iterator1.next();
					
					if(reportTO!=null){
						if(!originalListArray.contains(reportTO.getId())){
							reportsColumn.setId(reportTO.getId());
							if(reportTO.getColumnName() != null && !reportTO.getColumnName().isEmpty()){
								reportsColumn.setColumnName(reportTO.getColumnName());
							}
							if(reportTO.getReportName() != null){
								reportsColumn.setReportName(reportTO.getReportName());
							}
							if(!reportTO.getShowColumn()){
								reportsColumn.setShowColumn(Boolean.FALSE);
								reportsColumn.setPosition(0);
							}	
							reportsColumn.setLastModifiedDate(new Date());
							newList.add(reportsColumn);
						}
						
					}
				}				
			}	
			if(originalSelectedList != null && !originalSelectedList.isEmpty()){				
				Iterator iterator2 = originalSelectedList.iterator();
				while (iterator2.hasNext()) {
					
					ConfigureColumnForReportTO reportTO = (ConfigureColumnForReportTO) iterator2.next();
					if(reportTO!=null){
						if(!originalListArray.contains(reportTO.getId())){
							reportsColumn = new ConfigReportsColumn();
							reportsColumn.setId(reportTO.getId());
							if(reportTO.getColumnName() != null && !reportTO.getColumnName().isEmpty()){
								reportsColumn.setColumnName(reportTO.getColumnName());
							}
							if(reportTO.getReportName() != null){
								reportsColumn.setReportName(reportTO.getReportName());
							}							
								reportsColumn.setShowColumn(Boolean.FALSE);
								reportsColumn.setPosition(0);
								
							reportsColumn.setLastModifiedDate(new Date());						
						newList.add(reportsColumn);
						}
					}
				}				
			}

			
		}
		return newList;
	}
	
	
	/**
	 * @param studentSearchForm
	 * @return
	 * This method will build dynamic query
	 */
	private static String commonSearch(CandidateSearchForm studentSearchForm) {

		String searchCriteria = "";
		boolean containSearchCriteria = false;
		if (studentSearchForm.getProgramTypeId().trim().length() > 0) {
			containSearchCriteria=true;
			String course = "  admAppln.courseBySelectedCourseId.program.programType.id = "
					+ studentSearchForm.getProgramTypeId();
			searchCriteria = searchCriteria + course;
		}
		
		if (studentSearchForm.getProgramId().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			containSearchCriteria=true;
			String course = "  admAppln.courseBySelectedCourseId.program.id = "
					+ studentSearchForm.getProgramId();
			searchCriteria = searchCriteria + course;
		}
		if (studentSearchForm.getCourseId().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			containSearchCriteria=true;
			String course = " admAppln.courseBySelectedCourseId.id = "
					+ studentSearchForm.getCourseId();
			searchCriteria = searchCriteria + course;
		}
		if (studentSearchForm.getYear().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String appliedYear = "admAppln.appliedYear = "
					+ studentSearchForm.getYear();
			searchCriteria = searchCriteria + appliedYear;
		}
		if (studentSearchForm.getNationalityId().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String nationality = "admAppln.personalData.nationality.name like"
					+ "'" + studentSearchForm.getNationalityId() + "%" + "'";
			searchCriteria = searchCriteria + nationality;
		}
		if (studentSearchForm.getBirthCountry().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String birthCountry = "admAppln.personalData.countryByCountryId.name like"
					+ "'" + studentSearchForm.getBirthCountry() + "%" + "'";
			searchCriteria = searchCriteria + birthCountry;
		}
		if (studentSearchForm.getBirthState().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String birthState = "admAppln.personalData.stateByStateId.name like"
					+ "'" + studentSearchForm.getBirthState() + "%" + "'";
			searchCriteria = searchCriteria + birthState;
		}
		if (studentSearchForm.getResidentCategoryId().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String residentCategory = "admAppln.personalData.residentCategory.name like "
					+ "'"
					+ studentSearchForm.getResidentCategoryId()
					+ "%"
					+ "'";
			searchCriteria = searchCriteria + residentCategory;
		}
		if (studentSearchForm.getReligionId().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String religionName = "admAppln.personalData.religion.name like "
					+ "'" + studentSearchForm.getReligionId() + "%" + "'";
			searchCriteria = searchCriteria + religionName;
		}
		if (studentSearchForm.getSubReligionId().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String subReligionName = "admAppln.personalData.religionSection.name like "
					+ "'" + studentSearchForm.getSubReligionId() + "%" + "'";
			searchCriteria = searchCriteria + subReligionName;
		}
		if (studentSearchForm.getCasteCategoryId().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String casteCategory = "admAppln.personalData.caste.name like "
					+ "'" + studentSearchForm.getCasteCategoryId() + "%" + "'";
			searchCriteria = searchCriteria + casteCategory;
		}
		if (studentSearchForm.getBelongsTo().equals('R')
				|| studentSearchForm.getBelongsTo().equals('U')) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String belongsTo = "admAppln.personalData.ruralUrban = " + "'"
					+ studentSearchForm.getBelongsTo() + "'";
			searchCriteria = searchCriteria + belongsTo;
		}
		if (studentSearchForm.getGender().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String gender = "admAppln.personalData.gender = " + "'"
					+ studentSearchForm.getGender() + "'";
			searchCriteria = searchCriteria + gender;
		}
		if (studentSearchForm.getBloodGroup().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String blodGroup = "admAppln.personalData.bloodGroup = " + "'"
					+ studentSearchForm.getBloodGroup() + "'";
			searchCriteria = searchCriteria + blodGroup;
		}
		if (studentSearchForm.getWeightage().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String weightage = "admAppln.totalWeightage >= "
					+ new BigDecimal(studentSearchForm.getWeightage());
			searchCriteria = searchCriteria + weightage;
		}
		
		if (studentSearchForm.getWeightageTO().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String weightage = "admAppln.totalWeightage <= "
					+ new BigDecimal(studentSearchForm.getWeightageTO());
			searchCriteria = searchCriteria + weightage;
		}
		
		if (studentSearchForm.getApplicantName().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String applicantName = "admAppln.personalData.firstName  like  "
					+ "'" + studentSearchForm.getApplicantName() + "%" + "'";
			searchCriteria = searchCriteria + applicantName;
		}
		if (studentSearchForm.getUniversity().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String university = "educationQualification.university.name like "
					+ "'" + studentSearchForm.getUniversity() + "%" + "'";
			searchCriteria = searchCriteria + university;

		}
		if (studentSearchForm.getInstitute().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String institute = "";

			institute = "educationQualification.college.name like "
					+ "'" + studentSearchForm.getInstitute() + "%" + "'";

			searchCriteria = searchCriteria + institute;
		}
		if (studentSearchForm.getMarksObtained().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			BigDecimal percentageValue = BigDecimal.valueOf(Float
					.valueOf(studentSearchForm.getMarksObtained()));
			String percentage = "";
			percentage = "educationQualification.percentage >= "
					+ percentageValue;
			searchCriteria = searchCriteria + percentage;
		}

		if (studentSearchForm.getMarksObtainedTO().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			BigDecimal percentageValue = BigDecimal.valueOf(Float
					.valueOf(studentSearchForm.getMarksObtainedTO()));
			String percentage = "";
			percentage = "educationQualification.percentage <= "
					+ percentageValue;
			searchCriteria = searchCriteria + percentage;
		}

		
		if (studentSearchForm.getInterviewType().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			
			containSearchCriteria=true;
			String interviewType = "  interviewselected.interviewProgramCourse.id = "
					+ studentSearchForm.getInterviewType();
			searchCriteria = searchCriteria + interviewType;
		}
		
		if (studentSearchForm.getInterviewResult().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			containSearchCriteria=true;
			String interviewType = " interviewResults.interviewStatus.id = "
					+ studentSearchForm.getInterviewResult();
			String interviewResult = " and interviewResults.interviewProgramCourse.id = "
				+ studentSearchForm.getInterviewType();
			searchCriteria = searchCriteria + interviewType + interviewResult;
		}
		
		if (studentSearchForm.getInterviewStartDate().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			containSearchCriteria=true;
			String interviewStartDate = " interviewCards.interview.date >=' "
					+ CommonUtil.ConvertStringToSQLDate(studentSearchForm.getInterviewStartDate())+"'";
			searchCriteria = searchCriteria + interviewStartDate;
		}
		if (studentSearchForm.getInterviewEndDate().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			containSearchCriteria=true;
			String interviewEndDate = " interviewCards.interview.date <=' "
					+ CommonUtil.ConvertStringToSQLDate(studentSearchForm.getInterviewEndDate())+"'";
			searchCriteria = searchCriteria + interviewEndDate;
		}
		
		if (studentSearchForm.getAdmissionDateFrom().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			containSearchCriteria=true;
			String admissionDateFrom = " admAppln.admissionDate >='"
					+ CommonUtil.ConvertStringToSQLDate(studentSearchForm.getAdmissionDateFrom())+"'";
			searchCriteria = searchCriteria + admissionDateFrom;
		}
		if (studentSearchForm.getAdmissionDateTo().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			containSearchCriteria=true;
			String admissionDateTo = " admAppln.admissionDate <='"
					+ CommonUtil.ConvertStringToSQLDate(studentSearchForm.getAdmissionDateTo())+"'";
			searchCriteria = searchCriteria + admissionDateTo;
		}		
		
		if(studentSearchForm.getStatus().trim().length() > 0){
			if(studentSearchForm.getStatus().equals("selected")) {
				if(containSearchCriteria) {
					searchCriteria = searchCriteria + "  and  ";
				}

				searchCriteria = searchCriteria +" admAppln.isSelected = true  and admAppln.isApproved = true";			
			}
			else if(studentSearchForm.getStatus().equals("rejected")) {
				if(containSearchCriteria) {
					searchCriteria = searchCriteria + "  and  ";
				}

				searchCriteria = searchCriteria +" admAppln.isSelected = false ";			
			} else if(studentSearchForm.getStatus().equals("canceled")){
				if(containSearchCriteria) {
					searchCriteria = searchCriteria + "  and  ";
				}

				searchCriteria = searchCriteria +" admAppln.isCancelled = true ";
			}else if(studentSearchForm.getStatus().equals("admitted")){
				if(containSearchCriteria) {
					searchCriteria = searchCriteria + "  and  ";
				}

				searchCriteria = searchCriteria +" students.isAdmitted = true ";
			}
			else if(studentSearchForm.getStatus().equals("other")){
				if(containSearchCriteria) {
					searchCriteria = searchCriteria + "  and ";
				}

				searchCriteria = searchCriteria +" admAppln.admStatus is not null ";
			}
		}
		
		if(studentSearchForm.getFeeStatus()!= null && studentSearchForm.getFeeStatus().equals("1")){
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			searchCriteria = searchCriteria + "  feePayments.isFeePaid = 1 ";
		}
		else if(studentSearchForm.getFeeStatus()!= null && studentSearchForm.getFeeStatus().equals("2")){
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			searchCriteria = searchCriteria + " feePayments.isChallenPrinted = 1";
		}
		
		if(studentSearchForm.getStatus() == null || !studentSearchForm.getStatus().equals("canceled")){
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			searchCriteria = searchCriteria +" admAppln.isCancelled = 0 ";
		}
		if(studentSearchForm.getSemister()!= null && !studentSearchForm.getSemister().trim().isEmpty()){
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			searchCriteria = searchCriteria + " students.classSchemewise.curriculumSchemeDuration.semesterYearNo=" + studentSearchForm.getSemister();
		}
		/*if (studentSearchForm.getProgramId().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			containSearchCriteria=true;
			String course = "  admAppln.courseBySelectedCourseId.program.id = "
					+ studentSearchForm.getProgramId();
			searchCriteria = searchCriteria + course;
		}*/
		if (studentSearchForm.getAppliedDateFrom().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			containSearchCriteria=true;
			String admissionDateFrom = " admAppln.createdDate>='"
					+ CommonUtil.ConvertStringToSQLDate(studentSearchForm.getAppliedDateFrom())+"'";
			searchCriteria = searchCriteria + admissionDateFrom;
		}
		if (studentSearchForm.getAppliedDateTo().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			containSearchCriteria=true;
			String admissionDateTo = " admAppln.createdDate <='"
					+ CommonUtil.ConvertStringToSQLDate(studentSearchForm.getAppliedDateTo())+"'";
			searchCriteria = searchCriteria + admissionDateTo;
		}
		return searchCriteria;
	
	}
	
	/**
	 * @param studentSearchForm
	 * @return
	 * This method will build final query 
	 */
	public static String getSelectionSearchCriteria(
			CandidateSearchForm studentSearchForm) {

		String statusCriteria = commonSearch(studentSearchForm);
		
		String searchCriteria;
		
		String ednJoin = "";
		if (studentSearchForm.getUniversity().trim().length() > 0
				|| studentSearchForm.getInstitute().trim().length() > 0
				|| studentSearchForm.getMarksObtained().trim().length() > 0) {

			ednJoin = "	join personalData.ednQualifications educationQualification ";
			statusCriteria = statusCriteria
					+ "and educationQualification.docChecklist.isPreviousExam = true ";
		}
		
		if (studentSearchForm.getInterviewType().trim().length() > 0) {
			ednJoin = ednJoin + "  inner join admAppln.interviewSelecteds interviewselected "; 
			if (studentSearchForm.getInterviewResult().trim().length() == 0) {
				ednJoin = ednJoin +	" left join admAppln.interviewResults interviewResults "; 
			}
			
		}
		if (studentSearchForm.getInterviewStartDate().trim().length() > 0 | studentSearchForm.getInterviewEndDate().trim().length() > 0) {
			ednJoin = ednJoin + "  inner join admAppln.interviewCards interviewCards "; 
		}
		
		if (studentSearchForm.getInterviewResult().trim().length() > 0) {
			ednJoin = ednJoin + "  inner join admAppln.interviewResults interviewResults "; 
		}
		if (studentSearchForm.getInterviewResult().trim().length() > 0) {
			ednJoin = ednJoin + "  inner join admAppln.interviewResults interviewResults "; 
		}
		
		if(studentSearchForm.getFeeStatus()!= null && (studentSearchForm.getFeeStatus().equals("1") || studentSearchForm.getFeeStatus().equals("2"))){
			searchCriteria = " from AdmAppln admAppln inner join admAppln.personalData personalData inner join admAppln.students students " +
			" inner join students.feePayments feePayments" +
			"  inner join feePayments.feePaymentApplicantDetailses feeapp" +ednJoin;
			if(statusCriteria!=null && !statusCriteria.isEmpty()){
				statusCriteria=statusCriteria+"and feeapp.feePayment.id = feePayments.id  and feePayments.isCancelChallan=0 and feeapp.feeGroup.isOptional=0 and feeapp.semesterNo = 1"; 
			}else{
				statusCriteria=statusCriteria+"feeapp.feePayment.id = feePayments.id and feePayments.isCancelChallan=0 and feeapp.feeGroup.isOptional=0 and feeapp.semesterNo = 1";
			}
		}
		else{
			searchCriteria = " from AdmAppln admAppln inner join admAppln.personalData personalData inner join admAppln.students students" + ednJoin;
		}
		
	
		
		
		if(!StringUtils.isEmpty(statusCriteria)) {
			searchCriteria = searchCriteria + " where " + statusCriteria;
		}
		searchCriteria = searchCriteria + " order by admAppln.applnNo";
		return searchCriteria;

	}

	
	/**
	 * @param studentSearchForm
	 * @return
	 * This method will build dynamic query
	 */
	private static String sqlcommonSearch(CandidateSearchForm studentSearchForm) {

		String searchCriteria = "";
		boolean containSearchCriteria = false;
		if (studentSearchForm.getProgramTypeId().trim().length() > 0) {
			containSearchCriteria=true;
			String course = "   program_type.id = "
					+ studentSearchForm.getProgramTypeId();
			searchCriteria = searchCriteria + course;
		}
		
		if (studentSearchForm.getProgramId().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			containSearchCriteria=true;
			String course = "  program.id = "
					+ studentSearchForm.getProgramId();
			searchCriteria = searchCriteria + course;
		}
		if (studentSearchForm.getCourseId().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			containSearchCriteria=true;
			String course = " course.id = "
					+ studentSearchForm.getCourseId();
			searchCriteria = searchCriteria + course;
		}
		if (studentSearchForm.getYear().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String appliedYear = "appln.applied_year = "
					+ studentSearchForm.getYear();
			searchCriteria = searchCriteria + appliedYear;
		}
		if (studentSearchForm.getNationalityId().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String nationality = "nationality.name like"
					+ "'" + studentSearchForm.getNationalityId() + "%" + "'";
			searchCriteria = searchCriteria + nationality;
		}
		if (studentSearchForm.getBirthCountry().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String birthCountry = "country.name like"
					+ "'" + studentSearchForm.getBirthCountry() + "%" + "'";
			searchCriteria = searchCriteria + birthCountry;
		}
		if (studentSearchForm.getBirthState().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String birthState = "state.name like"
					+ "'" + studentSearchForm.getBirthState() + "%" + "'";
			searchCriteria = searchCriteria + birthState;
		}
		if (studentSearchForm.getResidentCategoryId().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String residentCategory = "resident_category.name like "
					+ "'"
					+ studentSearchForm.getResidentCategoryId()
					+ "%"
					+ "'";
			searchCriteria = searchCriteria + residentCategory;
		}
		if (studentSearchForm.getReligionId().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String religionName = " religion.name like "
					+ "'" + studentSearchForm.getReligionId() + "%" + "'";
			searchCriteria = searchCriteria + religionName;
		}
		if (studentSearchForm.getSubReligionId().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String subReligionName = "religion_section.name like "
					+ "'" + studentSearchForm.getSubReligionId() + "%" + "'";
			searchCriteria = searchCriteria + subReligionName;
		}
		if (studentSearchForm.getCasteCategoryId().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String casteCategory = "caste.name like "
					+ "'" + studentSearchForm.getCasteCategoryId() + "%" + "'";
			searchCriteria = searchCriteria + casteCategory;
		}
		if (studentSearchForm.getBelongsTo().equals('R')
				|| studentSearchForm.getBelongsTo().equals('U')) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String belongsTo = "personal_data.rural_urban = " + "'"
					+ studentSearchForm.getBelongsTo() + "'";
			searchCriteria = searchCriteria + belongsTo;
		}
		if (studentSearchForm.getGender().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String gender = "personal_data.gender = " + "'"
					+ studentSearchForm.getGender() + "'";
			searchCriteria = searchCriteria + gender;
		}
		if (studentSearchForm.getBloodGroup().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String blodGroup = "personal_data.blood_group = " + "'"
					+ studentSearchForm.getBloodGroup() + "'";
			searchCriteria = searchCriteria + blodGroup;
		}
		if (studentSearchForm.getWeightage().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String weightage = "appln.total_weightage >= "
					+ new BigDecimal(studentSearchForm.getWeightage());
			searchCriteria = searchCriteria + weightage;
		}
		
		if (studentSearchForm.getWeightageTO().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String weightage = "appln.total_weightage <= "
					+ new BigDecimal(studentSearchForm.getWeightageTO());
			searchCriteria = searchCriteria + weightage;
		}
		
		if (studentSearchForm.getApplicantName().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String applicantName = "personal_data.first_name  like  "
					+ "'" + studentSearchForm.getApplicantName() + "%" + "'";
			searchCriteria = searchCriteria + applicantName;
		}
		
		
		if (studentSearchForm.getUniversity().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String university = "university.name like "
					+ "'" + studentSearchForm.getUniversity() + "%" + "'";
			searchCriteria = searchCriteria + university;

		}
		
		if (studentSearchForm.getInstitute().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String institute = "";

			institute = "college.name like "
					+ "'" + studentSearchForm.getInstitute() + "%" + "'";

			searchCriteria = searchCriteria + institute;
		}
		
		if (studentSearchForm.getMarksObtained().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			BigDecimal percentageValue = BigDecimal.valueOf(Float
					.valueOf(studentSearchForm.getMarksObtained()));
			String percentage = "";
			percentage = "edn_qualification.percentage >= "
					+ percentageValue;
			searchCriteria = searchCriteria + percentage;
		}

		if (studentSearchForm.getMarksObtainedTO().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			BigDecimal percentageValue = BigDecimal.valueOf(Float
					.valueOf(studentSearchForm.getMarksObtainedTO()));
			String percentage = "";
			percentage = "edn_qualification.percentage <= "
					+ percentageValue;
			searchCriteria = searchCriteria + percentage;
		}

		
		if (studentSearchForm.getInterviewType().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			
			containSearchCriteria=true;
			String interviewType = "  interview_program_course.id = "
					+ studentSearchForm.getInterviewType();
			searchCriteria = searchCriteria + interviewType;
		}
		
		if (studentSearchForm.getInterviewResult().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			containSearchCriteria=true;
			String interviewType = " interview_result.interview_status_id = "
					+ studentSearchForm.getInterviewResult();
			String interviewResult = " and resltCourse.id = "
				+ studentSearchForm.getInterviewType();
			searchCriteria = searchCriteria + interviewType + interviewResult;
		}
		
		if (studentSearchForm.getInterviewStartDate().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			containSearchCriteria=true;
			String interviewStartDate = " interview_schedule.date >=' "
					+ CommonUtil.ConvertStringToSQLDate(studentSearchForm.getInterviewStartDate())+"'";
			searchCriteria = searchCriteria + interviewStartDate;
		}
		if (studentSearchForm.getInterviewEndDate().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			containSearchCriteria=true;
			String interviewEndDate = " interview_schedule.date <=' "
					+ CommonUtil.ConvertStringToSQLDate(studentSearchForm.getInterviewEndDate())+"'";
			searchCriteria = searchCriteria + interviewEndDate;
		}
		
		if (studentSearchForm.getAdmissionDateFrom().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			containSearchCriteria=true;
			String admissionDateFrom = " appln.date >=' "
					+ CommonUtil.ConvertStringToSQLDate(studentSearchForm.getAdmissionDateFrom())+"'";
			searchCriteria = searchCriteria + admissionDateFrom;
		}
		if (studentSearchForm.getAdmissionDateTo().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			containSearchCriteria=true;
			String admissionDateTo = " appln.date <=' "
					+ CommonUtil.ConvertStringToSQLDate(studentSearchForm.getAdmissionDateFrom())+"'";
			searchCriteria = searchCriteria + admissionDateTo;
		}		
		
		if(studentSearchForm.getStatus().trim().length() > 0){
			if(studentSearchForm.getStatus().equals("selected")) {
				if(containSearchCriteria) {
					searchCriteria = searchCriteria + "  and  ";
				}

				searchCriteria = searchCriteria +" appln.is_selected = true  and appln.is_approved = true";			
			}
			else if(studentSearchForm.getStatus().equals("rejected")) {
				if(containSearchCriteria) {
					searchCriteria = searchCriteria + "  and  ";
				}

				searchCriteria = searchCriteria +" appln.is_selected = false ";			
			} else if(studentSearchForm.getStatus().equals("canceled")){
				if(containSearchCriteria) {
					searchCriteria = searchCriteria + "  and  ";
				}

				searchCriteria = searchCriteria +" appln.is_cancelled = true ";
			}else if(studentSearchForm.getStatus().equals("admitted")){
				if(containSearchCriteria) {
					searchCriteria = searchCriteria + "  and  ";
				}

				searchCriteria = searchCriteria +" students.is_admitted = true ";
			}
		}
		

		
		if(studentSearchForm.getSemister()!= null && !studentSearchForm.getSemister().trim().isEmpty()){
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			searchCriteria = searchCriteria + " curriculum_scheme_duration.semester_year_no=" + studentSearchForm.getSemister();
		}
		return searchCriteria;
	
	}	

	
	public static String sqlgetSelectionSearchCriteria(
			CandidateSearchForm studentSearchForm) {

		String statusCriteria = sqlcommonSearch(studentSearchForm);
		
		String searchCriteria;
		
		
		String ednJoin ="";
		if (studentSearchForm.getUniversity().trim().length() > 0
				|| studentSearchForm.getInstitute().trim().length() > 0
				|| studentSearchForm.getMarksObtained().trim().length() > 0) {

			ednJoin = "	join edn_qualification on edn_qualification.personal_data_id = personal_data.id" +
						 " left join university ON edn_qualification.university_id = university.id "+
						 " left join doc_checklist ON edn_qualification.doc_checklist_id = doc_checklist.id "+
						 " left join college on edn_qualification.institution_id = college.id ";
			statusCriteria = statusCriteria
					+ " and doc_checklist.is_previous_exam = 1 ";
		}
		
		if (studentSearchForm.getInterviewType().trim().length() > 0) {
			ednJoin = ednJoin + "   inner join interview_selected on interview_selected.adm_appln_id = appln.id " +
								" left join interview_program_course ON " +
								" interview_selected.interview_program_course_id = interview_program_course.id "; 
			/*if (studentSearchForm.getInterviewResult().trim().length() == 0) {
				ednJoin = ednJoin +	" left join interview_result on interview_result.adm_appln_id = appln.id " +
							" left join interview_program_course as resltCourse on " +
							" interview_result.interview_program_course_id = interview_program_course.id "; 
			}*/
			
		}
		if (studentSearchForm.getInterviewStartDate().trim().length() > 0 | studentSearchForm.getInterviewEndDate().trim().length() > 0) {
			ednJoin = ednJoin + "  inner join interview_card on interview_card.adm_appln_id = appln.id " +
								" join interview_schedule on interview_card.interview_schedule_id = interview_schedule.id "; 
		}
		
		if (studentSearchForm.getInterviewResult().trim().length() > 0) {
			ednJoin = ednJoin +	" left join interview_result on interview_result.adm_appln_id = appln.id " +
			" left join interview_program_course as resltCourse on " +
			" interview_result.interview_program_course_id = interview_program_course.id "; 
		}
		
		
			searchCriteria = " select appln.appln_no,appln.total_weightage,personal_data.first_name," +
				"personal_data.middle_name,personal_data.last_name,personal_data.date_of_birth," +
				"personal_data.gender,personal_data.blood_group,appln.challan_ref_no," +
				"personal_data.email,student.register_no,student.roll_no," +
				"appln.admission_date,classes.name,caste.name, "+
				"personal_data.caste_others,personal_data.second_language," +
				"course.name,program.name,program_type.name,appln.journal_no," +
				"appln.applied_year,admitted_through.name," +
				"appln.date,appln.amount,resident_category.name,religion.name," +
				"personal_data.religion_others,religion_section.name," +
				"personal_data.religion_section_others,personal_data.birth_place," +
				"state.name,personal_data.state_others," +
				"personal_data.rural_urban,country.name," +
				"nationality.name,personal_data.ph_no_1,personal_data.ph_no_2," +
				"personal_data.ph_no_3,personal_data.mobile_no_1,personal_data.mobile_no_2," +
				"personal_data.mobile_no_3,personal_data.passport_no," +
				"CountryByPassportCountry.name,personal_data.passport_validity," +
				"personal_data.permanent_address_line_1,personal_data.permanent_address_line_2," +
				"personal_data.permanent_address_city,PermanentAddressState.name," +
				"personal_data.permanent_address_state_others,CountryByPermentCountry.name," +
				"personal_data.permanent_address_zip_code,personal_data.current_address_line_1," +
				"personal_data.current_address_line_2,personal_data.current_address_city," +
				"CurrentAddressState.name,personal_data.current_address_state_others," +
				"CountryByCurrentCountry.name,personal_data.current_address_zip_code," +
				"personal_data.father_name,personal_data.father_education,fatherIncome.income_range," +
				"fatherCurrency.name,fOccupation.name,personal_data.father_email,personal_data.mother_name," +
				"personal_data.mother_education,mIncome.income_range,mCurrency.name," +
				"mOccupation.name,personal_data.mother_email,personal_data.parent_address_line_1," +
				"personal_data.parent_address_line_2,personal_data.parent_address_line_3," +
				"personal_data.parent_address_city,ParentAddressState.name," +
				"personal_data.parent_address_state_others,ParentCountry.name," +
				"personal_data.parent_address_zip_code,personal_data.parent_ph_1," +
				"personal_data.parent_ph_2,personal_data.parent_ph_3,personal_data.parent_mob_1," +
				"personal_data.parent_mob_2,personal_data.parent_mob_3, appln.id" +
					" from adm_appln appln "+
					 " inner join personal_data ON appln.personal_data_id = personal_data.id "+
					 " inner join student on student.adm_appln_id = appln.id "+
					 " left join course ON appln.selected_course_id = course.id "+
					 " left join program ON course.program_id = program.id "+
					 " left join  program_type ON program.program_type_id = program_type.id "+
					 " left join nationality ON personal_data.nationality_id = nationality.id "+
					 " left join country on personal_data.country_id = country.id "+
					 " left join state ON personal_data.state_id = state.id "+
					 " left join resident_category ON personal_data.resident_category_id = resident_category.id "+
					 " left join religion on personal_data.religion_id = religion.id "+
					 " left join religion_section on  personal_data.religion_section = religion_section.id "+
					 " left join caste on personal_data.caste_id = caste.id "+
					 " left join class_schemewise on student.class_schemewise_id = class_schemewise.id "+
					 " left join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id " +
					 " left join classes ON class_schemewise.class_id = classes.id " +
					 " left join admitted_through on appln.admitted_through_id = admitted_through.id " +
					 " left join country as CountryByPassportCountry on personal_data.passport_country_id = CountryByPassportCountry.id" +
					 " left join state as PermanentAddressState ON personal_data.permanent_address_state_id = PermanentAddressState.id" +
					 " left join country as CountryByPermentCountry on personal_data.permanent_address_country_id = CountryByPermentCountry.id" +
					 " left join state as CurrentAddressState ON personal_data.current_address_state_id = CurrentAddressState.id" +
					 " left join country as CountryByCurrentCountry on personal_data.current_address_country_id = CountryByCurrentCountry.id" +
					 " left join income as fatherIncome ON personal_data.father_income_id = fatherIncome.id" +
					 " left join currency as fatherCurrency on personal_data.father_income_currency_id = fatherCurrency.id" +
					 " left join occupation as fOccupation on personal_data.father_occupation_id = fOccupation.id" +
					 " left join income as mIncome ON personal_data.mother_income_id = mIncome.id" +
					 " left join currency as mCurrency on personal_data.mother_income_currency_id = mCurrency.id" +
					 " left join occupation as mOccupation on personal_data.mother_occupation_id = mOccupation.id" +
					 " left join state as ParentAddressState on personal_data.parent_address_state_id = ParentAddressState.id" +
					 " left join country as ParentCountry on personal_data.parent_address_country_id = ParentCountry.id" +
					  ednJoin;
		
		if(!StringUtils.isEmpty(statusCriteria)) {
			searchCriteria = searchCriteria + " where " + statusCriteria + " order by appln.appln_no ";
		}
		
		
		
		return searchCriteria;

	}
	
	/**
	 * @param studentSearchBo
	 * @return
	 * This classes is used to convert BO's to TO's
	 * @throws Exception 
	 */
	public static List<CandidateSearchTO> sqlconvertBoToTo(List<Object[]> recordList) throws Exception {

		List<CandidateSearchTO> candidateSearchTOList = new ArrayList<CandidateSearchTO>();
		if (recordList != null) {
			Iterator itrstudentBO = recordList.iterator();		
			while (itrstudentBO.hasNext()) {
				Object[] object = (Object[]) itrstudentBO.next();
//				AdmAppln admAppln = (AdmAppln)object[0];
//				Student student = (Student)object[2];
				CandidateSearchTO candidateSearchTO = new CandidateSearchTO();		
				if(object[0]!= null){
					candidateSearchTO.setApplnNo(Integer.parseInt(object[0].toString()));					
				}
				
				if(object[1]!= null){
					candidateSearchTO.setTotalWeightage(Double.parseDouble(object[1].toString()));				
				}
				if(object[2]!= null){
					candidateSearchTO.setName(object[2].toString());
					if(object[3]!= null){
						candidateSearchTO.setName(object[2].toString()+" "+object[3].toString());
					}
					if(object[4]!= null){
						candidateSearchTO.setName(object[2].toString()+" "+object[4].toString());
					}
					if( object[3]!= null && object[4]!= null){
						candidateSearchTO.setName(object[2].toString()+" "+object[3].toString()+" "+object[4].toString());
					}

				}
				if(object[5]!= null){
					candidateSearchTO.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(object[5].toString(), "yyyy-mm-dd", "dd-mm-yyyy"));
				}
				if(object[6]!= null){
					candidateSearchTO.setGender(object[6].toString());
				}
				if(object[7]!= null){
					candidateSearchTO.setBloodGroup(object[7].toString());
				}
				if(object[8]!= null){
					candidateSearchTO.setChallanNo(object[8].toString());
				}
				if(object[9]!= null){
					candidateSearchTO.setEmail(object[9].toString());
				}
			
				if(object[10]!= null){
					candidateSearchTO.setRegNo(object[10].toString());
				}
				if(object[11]!= null){
					candidateSearchTO.setRollNo(object[11].toString());
				}
				candidateSearchTOList.add(candidateSearchTO);
			}
		}
		return candidateSearchTOList;
	
	}
	
	
	/**
	 * @param studentSearchBo
	 * @return
	 * This classes is used to convert BO's to TO's
	 * @throws Exception 
	 */
	public static List<CandidateSearchTO> sqlconvertBoToToExcel(List<Object[]> studentSearchBo) throws Exception {
		ICandidateSearchTxnImpl transaction=CandidateSearchTxnImpl.getInstance();
		List<CandidateSearchTO> candidateSearchTOList = new ArrayList<CandidateSearchTO>();
		Properties prop = new Properties();
		InputStream in = StudentUploadPhotoAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
        prop.load(in);
        //String path=prop.getProperty("knowledgepro.admin.student.photosource");
        String userName =prop.getProperty("jdbc.username");
        String password = prop.getProperty("jdbc.password");
        String url = prop.getProperty("jdbc.url");
        Class.forName ("com.mysql.jdbc.Driver").newInstance ();
		Connection conn = null;
	    conn = DriverManager.getConnection (url, userName, password);
		if (studentSearchBo != null) {
			Iterator itrstudentBO = studentSearchBo.iterator();
			

			while (itrstudentBO.hasNext()) {
			Object[] object = (Object[]) itrstudentBO.next();
			//AdmAppln admAppln = (AdmAppln)object[0];
//			Student student = (Student)object[2];
			CandidateSearchTO candidateSearchTO = new CandidateSearchTO();	
			if(object[0]!= null){
				candidateSearchTO.setApplnNo(Integer.parseInt(object[0].toString()));					
			}
			
			if(object[1]!= null){
				candidateSearchTO.setTotalWeightage(Double.parseDouble(object[1].toString()));				
			}
			if(object[2]!= null){
				candidateSearchTO.setName(object[2].toString());
				if(object[3]!= null){
					candidateSearchTO.setName(object[2].toString()+" "+object[3].toString());
				}
				if(object[4]!= null){
					candidateSearchTO.setName(object[2].toString()+" "+object[4].toString());
				}
				if( object[3]!= null && object[4]!= null){
					candidateSearchTO.setName(object[2].toString()+" "+object[3].toString()+" "+object[4].toString());
				}

			}
			if(object[5]!= null){
				candidateSearchTO.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(object[5].toString(), "yyyy-mm-dd", "dd-mm-yyyy"));
			}
			if(object[6]!= null){
				candidateSearchTO.setGender(object[6].toString());
			}
			if(object[7]!= null){
				candidateSearchTO.setBloodGroup(object[7].toString());
			}
			if(object[8]!= null){
				candidateSearchTO.setChallanNo(object[8].toString());
			}
			if(object[9]!= null){
				candidateSearchTO.setEmail(object[9].toString());
			}
		
			if(object[10]!= null){
				candidateSearchTO.setRegNo(object[10].toString());
			}
			if(object[11]!= null){
				candidateSearchTO.setRollNo(object[11].toString());
			}
			if(object[12]!= null){
				candidateSearchTO.setAdmissionDate(object[12].toString());
			}
			if(object[13]!= null){
				candidateSearchTO.setClassStudent(object[13].toString());
			}
			
			if(object[14]!= null){
				candidateSearchTO.setCastCategory(object[14].toString());				
			}
			if(object[15]!= null){
				candidateSearchTO.setCastCategoryOther(object[15].toString());				
			}
			
			if(object[16]!= null){
				candidateSearchTO.setSecondLanguage(object[16].toString());
			}
			if(object[17]!= null){
				candidateSearchTO.setCourseName(object[17].toString());					
			}
			if(object[18]!= null){
				candidateSearchTO.setProgramName(object[18].toString());					
			}
			if(object[19]!= null){
				candidateSearchTO.setProgramTypeName(object[19].toString());					
			}
			if(object[20]!= null){
				candidateSearchTO.setJournalNo(object[20].toString());					
			}
			if(object[21]!= null){
				int year=Integer.parseInt(object[21].toString());
				candidateSearchTO.setAppliedYear(object[21].toString()+"-"+String.valueOf(year+1));					
			}
			if(object[22]!= null){
				candidateSearchTO.setAdmittedThrough(object[22].toString());					
			}
			
			if(object[23]!= null){
				candidateSearchTO.setChallanDate(object[23].toString());
			}
			if(object[24]!= null){
				candidateSearchTO.setChallanAmount(object[24].toString());					
			}
			if(object[25]!= null){
				candidateSearchTO.setResidentCategory(object[25].toString());
			}
			if(object[26]!= null){
				candidateSearchTO.setReligion(object[26].toString());
			}
			if(object[27]!= null){
				candidateSearchTO.setReligionOther(object[27].toString());
			}
			if(object[28]!= null){
				candidateSearchTO.setSubReligion(object[28].toString());
			}
			if(object[29]!= null){
				candidateSearchTO.setSubReligionOther(object[29].toString());
			}
			if(object[30]!= null){
				candidateSearchTO.setPlaceOfBirth(object[30].toString());
			}
			if(object[31]!= null){
				candidateSearchTO.setStateOfBirth(object[31].toString());
			}
			if(object[32]!= null){
				candidateSearchTO.setStateOfBirthOther(object[32].toString());
			}
			if(object[33]!= null){
				candidateSearchTO.setBelongsTo(object[33].toString());
			}
			if(object[34]!= null){
				candidateSearchTO.setCountryOfBirth(object[34].toString());
			}
			if(object[35]!= null){
				candidateSearchTO.setNationality(object[35].toString());
			}
			String studentPhoneNo = "";
			if(object[36]!= null){
				studentPhoneNo = object[36].toString();
			}
			if(object[37]!= null){
				studentPhoneNo = studentPhoneNo + object[37].toString();
			}
			if(object[38]!= null){
				studentPhoneNo = studentPhoneNo + object[38].toString();
			}
			candidateSearchTO.setStudentPhoneNo(studentPhoneNo);
			String studentMobileNo = "";
			if(object[39]!= null){
				studentMobileNo = object[39].toString();
			}
			if(object[40]!= null){
				studentMobileNo = studentMobileNo + object[39].toString();
			}
			if(object[41]!= null){
				studentMobileNo = studentMobileNo + object[41].toString();
			}
			
			candidateSearchTO.setStudentMobileNo(studentMobileNo);
			
			if(object[42]!= null){
				candidateSearchTO.setPassportNo(object[42].toString());
			}
			if(object[43]!= null){
				candidateSearchTO.setPassportIssuingCountry(object[43].toString());
			}
			if(object[44]!= null){
				candidateSearchTO.setPassportValidUpTo(object[44].toString());
			}
			
			/*if(admAppln.getPersonalData()!=null && admAppln.getCandidatePreferences()!=null){
				Set candidatePreferenceSet = admAppln.getCandidatePreferences();
				
				CandidatePreference cPre= new CandidatePreference();
				Iterator itr = candidatePreferenceSet.iterator();
				while(itr.hasNext()){
					cPre = (CandidatePreference)itr.next();
					if(cPre.getPrefNo()==1){
						if(cPre.getCourse().getProgram().getProgramType().getName()!=null){
							candidateSearchTO.setFirstPrePT(cPre.getCourse().getProgram().getProgramType().getName());
						}
						if(cPre.getCourse().getProgram().getName()!=null){
							candidateSearchTO.setFirstPreProgram(cPre.getCourse().getProgram().getName());
						}
						if(cPre.getCourse().getName()!=null){
							candidateSearchTO.setFirstCourse(cPre.getCourse().getName());
						}
					}
					if(cPre.getPrefNo()==2){
						if(cPre.getCourse().getProgram().getProgramType().getName()!=null){
							candidateSearchTO.setSecondPrePT(cPre.getCourse().getProgram().getProgramType().getName());
						}
						if(cPre.getCourse().getProgram().getName()!=null){
							candidateSearchTO.setSecondPreProgram(cPre.getCourse().getProgram().getName());
						}
						if(cPre.getCourse().getName()!=null){
							candidateSearchTO.setSecondCourse(cPre.getCourse().getName());
						}
					}
					if(cPre.getPrefNo()==3){
						if(cPre.getCourse().getProgram().getProgramType().getName()!=null){
							candidateSearchTO.setThirdPrePT(cPre.getCourse().getProgram().getProgramType().getName());
						}
						if(cPre.getCourse().getProgram().getName()!=null){
							candidateSearchTO.setThirdPreProgram(cPre.getCourse().getProgram().getName());
						}
						if(cPre.getCourse().getName()!=null){
							candidateSearchTO.setThirdCourse(cPre.getCourse().getName());
						}	
					}
				}					
			}
			if(admAppln.getPersonalData()!=null && admAppln.getApplicantWorkExperiences()!=null){
				int count= 1;
				Set applnWorkExperienceSet = admAppln.getApplicantWorkExperiences();
				ApplicantWorkExperience applnWorkExperience = new ApplicantWorkExperience();
				Iterator itr = applnWorkExperienceSet.iterator();
				while(itr.hasNext()){
					applnWorkExperience = (ApplicantWorkExperience)itr.next();
					if(count==1){
						candidateSearchTO.setFirstNameOfOra(applnWorkExperience.getOrganization());
						candidateSearchTO.setFirstDesignation(applnWorkExperience.getPosition());
						candidateSearchTO.setFirstFromDate(CommonUtil.getStringDate(applnWorkExperience.getFromDate()));
						candidateSearchTO.setFirstTODate(CommonUtil.getStringDate(applnWorkExperience.getToDate()));
					}
					if(count==2){
						candidateSearchTO.setSecondNameOfOra(applnWorkExperience.getOrganization());
						candidateSearchTO.setSecondDesignation(applnWorkExperience.getPosition());
						candidateSearchTO.setSecondFromDate(CommonUtil.getStringDate(applnWorkExperience.getFromDate()));
						candidateSearchTO.setSecondTODate(CommonUtil.getStringDate(applnWorkExperience.getToDate()));
						
					}
					if(count==3){
						candidateSearchTO.setThirdNameOfOra(applnWorkExperience.getOrganization());
						candidateSearchTO.setThirdDesignation(applnWorkExperience.getPosition());
						candidateSearchTO.setThirdFromDate(CommonUtil.getStringDate(applnWorkExperience.getFromDate()));
						candidateSearchTO.setThirdTODate(CommonUtil.getStringDate(applnWorkExperience.getToDate()));
						
					}
					count++;
				}
				
			}	*/
			if(object[45]!= null){
				candidateSearchTO.setPermanentAddressline1(object[45].toString());					
			}
			if(object[46]!= null){
				candidateSearchTO.setPermanentAddressline2(object[46].toString());					
			}				
			if(object[47]!= null){
				candidateSearchTO.setPermanentCity(object[47].toString());					
			}				
			if(object[48]!= null){
				candidateSearchTO.setPermanentState(object[48].toString());					
			}
			if(object[49]!= null){
				candidateSearchTO.setPermanentStateOther(object[49].toString());					
			}				
			if(object[50]!= null){
				candidateSearchTO.setPermanentCountry(object[50].toString());					
			}				
			if(object[51]!= null){
				candidateSearchTO.setPermanentZipCode(object[51].toString());					
			}				

			if(object[52]!= null){
				candidateSearchTO.setCurrentAddressline1(object[52].toString());					
			}
			if(object[53]!= null){
				candidateSearchTO.setCurrentAddressline2(object[53].toString());					
			}				
			if(object[54]!= null){
				candidateSearchTO.setCurrentCity(object[54].toString());					
			}				
			if(object[55]!= null){
				candidateSearchTO.setCurrentState(object[55].toString());					
			}	
			if(object[56]!= null){
				candidateSearchTO.setCurrentStateOther(object[56].toString());					
			}				
			
			if(object[57]!= null){
				candidateSearchTO.setCurrentCountry(object[57].toString());					
			}				
			if(object[58]!= null){
				candidateSearchTO.setCurrentZipCode(object[58].toString());					
			}				

			if(object[59]!= null){
				candidateSearchTO.setFatherName(object[59].toString());					
			}				
			if(object[60]!= null){
				candidateSearchTO.setFatherEducation(object[60].toString());					
			}				
			if(object[61]!= null){
				candidateSearchTO.setFatherIncome(object[61].toString());					
			}				
			if(object[62]!= null){
				candidateSearchTO.setFatherIncomeCurrency(object[62].toString());					
			}				
			if(object[63]!= null){
				candidateSearchTO.setFatherOccupation(object[63].toString());					
			}				
			if(object[64]!= null){
				candidateSearchTO.setFatherEmail(object[64].toString());					
			}				

			if(object[65]!= null){
				candidateSearchTO.setMotherName(object[65].toString());					
			}				
			if(object[66]!= null){
				candidateSearchTO.setMotherEducation(object[66].toString());					
			}				
			if(object[67]!= null){
				candidateSearchTO.setMotherIncome(object[67].toString());					
			}				
			if(object[68]!= null){
				candidateSearchTO.setMotherIncomeCurrency(object[68].toString());					
			}				
			if(object[69]!= null){
				candidateSearchTO.setMotherOccupation(object[69].toString());					
			}				
			if(object[70]!= null){
				candidateSearchTO.setMotherEmail(object[70].toString());					
			}				

			if(object[71]!= null){
				candidateSearchTO.setParentAdressLine1(object[71].toString());					
			}				
			if(object[72]!= null){
				candidateSearchTO.setParentAdressLine2(object[72].toString());					
			}				
			if(object[73]!= null){
				candidateSearchTO.setParentAdressLine3(object[73].toString());					
			}				
			if(object[74]!= null){
				candidateSearchTO.setParentCity(object[74].toString());					
			}				
			if(object[75]!= null){
				candidateSearchTO.setParentState(object[75].toString());			
			}
			if(object[76]!= null){
				candidateSearchTO.setParentStateOther(object[76].toString());			
			}				
			if(object[77]!= null){
				candidateSearchTO.setParentCountry(object[77].toString());			
			}				
			if(object[78]!= null){
				candidateSearchTO.setParentZipCode(object[78].toString());			
			}	
			String parentPhoneNo = "";
			if(object[79]!= null){
				parentPhoneNo = object[79].toString();			
			}
			if(object[80]!= null){
				parentPhoneNo = parentPhoneNo +object[80].toString();			
			}
			if(object[81]!= null){
				parentPhoneNo = parentPhoneNo +object[81].toString();			
			}
			
			candidateSearchTO.setParentPhone(parentPhoneNo);
			
			String parentMobileNo = "";
			if(object[82]!= null){
				parentMobileNo = object[82].toString();			
			}
			if(object[83]!= null){
				parentMobileNo = parentMobileNo + object[83].toString();				
			}
			if(object[84]!= null){
				parentMobileNo= parentMobileNo + object[84].toString();				
			}
			
			candidateSearchTO.setParentMobileNo(parentMobileNo);
			if(object[85]!= null){
				int appId=Integer.parseInt(object[85].toString());
				String searchCriteria="select candidate_preference.pref_no,program_type.name,program.name,course.name from candidate_preference " +
						"join adm_appln ON candidate_preference.adm_appln_id = adm_appln.id " +
						" left join course ON candidate_preference.course_id = course.id" +
						" left join program ON course.program_id = program.id " +
						"left join program_type ON program.program_type_id = program_type.id where adm_appln.id ="+appId;
			List<Object[]> candidatePreferenceSet = transaction.executeAdmissionReport(searchCriteria,conn);
			if(candidatePreferenceSet!=null && !candidatePreferenceSet.isEmpty()){
				//CandidatePreference cPre= new CandidatePreference();
				Iterator<Object[]> itr = candidatePreferenceSet.iterator();
				while (itr.hasNext()) {
					Object[] prefObject = (Object[]) itr.next();
					if(prefObject[0]!=null){
						int prefNo=Integer.parseInt(prefObject[0].toString());
						if(prefNo==1){
							if(prefObject[1]!=null){
								candidateSearchTO.setFirstPrePT(prefObject[1].toString());
							}
							if(prefObject[2]!=null){
								candidateSearchTO.setFirstPreProgram(prefObject[2].toString());
							}
							if(prefObject[3]!=null){
								candidateSearchTO.setFirstCourse(prefObject[3].toString());
							}
						}
						if(prefNo==2){
							if(prefObject[1]!=null){
								candidateSearchTO.setSecondPrePT(prefObject[1].toString());
							}
							if(prefObject[2]!=null){
								candidateSearchTO.setSecondPreProgram(prefObject[2].toString());
							}
							if(prefObject[3]!=null){
								candidateSearchTO.setSecondCourse(prefObject[3].toString());
							}
						}
						if(prefNo==3){
							if(prefObject[1]!=null){
								candidateSearchTO.setThirdPrePT(prefObject[1].toString());
							}
							if(prefObject[2]!=null){
								candidateSearchTO.setThirdPreProgram(prefObject[2].toString());
							}
							if(prefObject[3]!=null){
								candidateSearchTO.setThirdCourse(prefObject[3].toString());
							}	
						}
					}
				
					
				}
			}
			if(appId>0){
				int count= 1;
				String query="select experience.organization, experience.position,experience.from_date, experience.to_date " +
						"from applicant_work_experience experience" +
						" join adm_appln ON experience.adm_appln_id = adm_appln.id where adm_appln.id = " + appId ;
				List<Object[]> applnWorkExperienceSet = transaction.executeAdmissionReport(query,conn);
				Iterator<Object[]> itr1 = applnWorkExperienceSet.iterator();
				while (itr1.hasNext()) {
					Object[] object2 = (Object[]) itr1.next();
					if(count==1){
						if(object2[0]!=null){
							candidateSearchTO.setFirstNameOfOra(object2[0].toString());	
						}
						if(object2[1]!=null){
							candidateSearchTO.setFirstDesignation(object2[1].toString());
						}
						if(object2[2]!=null){
							candidateSearchTO.setFirstFromDate(object2[2].toString());
						}
						if(object2[3]!=null){
							candidateSearchTO.setFirstTODate(object2[3].toString());
						}
						
						
						
					}
					if(count==2){
						if(object2[0]!=null){
						candidateSearchTO.setSecondNameOfOra(object2[0].toString());
						}
						if(object2[1]!=null){
						candidateSearchTO.setSecondDesignation(object2[1].toString());
						}
						if(object2[2]!=null){
						candidateSearchTO.setSecondFromDate(object2[2].toString());
						}
						if(object2[3]!=null){
						candidateSearchTO.setSecondTODate(object2[3].toString());
						}
						
					}
					if(count==3){
						if(object2[0]!=null){
						candidateSearchTO.setThirdNameOfOra(object2[0].toString());
						}
						if(object2[1]!=null){
						candidateSearchTO.setThirdDesignation(object2[1].toString());
						}
						if(object2[2]!=null){
						candidateSearchTO.setThirdFromDate(object2[2].toString());
						}
						if(object2[3]!=null){
						candidateSearchTO.setThirdTODate(object2[3].toString());
						}
						
					}
					count++;
				}
			}	
		}
		/*if(admAppln.getPersonalData()!=null && admAppln.getApplicantWorkExperiences()!=null){
			int count= 1;
			Set applnWorkExperienceSet = admAppln.getApplicantWorkExperiences();
			ApplicantWorkExperience applnWorkExperience = new ApplicantWorkExperience();
			Iterator itr = applnWorkExperienceSet.iterator();
			while(itr.hasNext()){
				applnWorkExperience = (ApplicantWorkExperience)itr.next();
				if(count==1){
					candidateSearchTO.setFirstNameOfOra(applnWorkExperience.getOrganization());
					candidateSearchTO.setFirstDesignation(applnWorkExperience.getPosition());
					candidateSearchTO.setFirstFromDate(CommonUtil.getStringDate(applnWorkExperience.getFromDate()));
					candidateSearchTO.setFirstTODate(CommonUtil.getStringDate(applnWorkExperience.getToDate()));
				}
				if(count==2){
					candidateSearchTO.setSecondNameOfOra(applnWorkExperience.getOrganization());
					candidateSearchTO.setSecondDesignation(applnWorkExperience.getPosition());
					candidateSearchTO.setSecondFromDate(CommonUtil.getStringDate(applnWorkExperience.getFromDate()));
					candidateSearchTO.setSecondTODate(CommonUtil.getStringDate(applnWorkExperience.getToDate()));
					
				}
				if(count==3){
					candidateSearchTO.setThirdNameOfOra(applnWorkExperience.getOrganization());
					candidateSearchTO.setThirdDesignation(applnWorkExperience.getPosition());
					candidateSearchTO.setThirdFromDate(CommonUtil.getStringDate(applnWorkExperience.getFromDate()));
					candidateSearchTO.setThirdTODate(CommonUtil.getStringDate(applnWorkExperience.getToDate()));
					
				}
				count++;
			}
		}	*/
			candidateSearchTOList.add(candidateSearchTO);
		}
		}
		if (conn != null)
        {
            try
            {
                conn.close ();
            }
            catch (Exception e) { /* ignore close errors */ }
        }
		return candidateSearchTOList;
	}
	
	
	private static String getDocTypeHeadingMapquery(CandidateSearchForm studentSearchForm) throws Exception{
		String query ="select d.docType " +
				" from DocChecklist d" +
				" where d.isMarksCard=1" +
				" and d.isActive=1 and d.docType.isActive=1 and d.year="+studentSearchForm.getYear();
		if(studentSearchForm.getCourseId()!=null && !studentSearchForm.getCourseId().isEmpty()){
			query=query+" and d.course.id="+studentSearchForm.getCourseId();
		}else{
			if(studentSearchForm.getProgramId()!=null && !studentSearchForm.getProgramId().isEmpty())
			query=query+" and d.program.id="+studentSearchForm.getProgramId();
		}
		return query;
	}
	
	private static String getInterviewTypeHeadingMapquery(CandidateSearchForm studentSearchForm) throws Exception{
		String query ="from InterviewProgramCourse i" +
				" where i.isActive=1 and i.year="+studentSearchForm.getYear();
		if(studentSearchForm.getCourseId()!=null && !studentSearchForm.getCourseId().isEmpty()){
			query=query+" and i.course.id="+studentSearchForm.getCourseId();
		}else{
			if(studentSearchForm.getProgramId()!=null && !studentSearchForm.getProgramId().isEmpty())
			query=query+" and i.program.id="+studentSearchForm.getProgramId();
		}
		return query;
	}

// Code Rewritten By Balaji
	
	/**
	 * @param studentSearchForm
	 * @return
	 * @throws Exception
	 */
	public static String getSearchQueryForInput( CandidateSearchForm studentSearchForm,String mode) throws Exception {
		StringBuffer query=new StringBuffer();
		query.append(" from Student s ");
		StringBuffer joinQuery=new StringBuffer();
		StringBuffer whereQuery=new StringBuffer();
		StringBuffer selectQuery=null;
		if(mode.equalsIgnoreCase("export"))
			selectQuery=new StringBuffer(" select s.admAppln,s.admAppln.personalData,s ");
		else
			selectQuery=new StringBuffer("select s.admAppln.id, s.id, s.admAppln.applnNo, s.admAppln.personalData.firstName, s.admAppln.personalData.middleName, s.admAppln.personalData.lastName, s.admAppln.totalWeightage, s.admAppln.personalData.dateOfBirth, s.admAppln.personalData.gender, s.rollNo, s.registerNo ");
			
		if (studentSearchForm.getProgramTypeId().trim().length() > 0) {
			whereQuery.append(" where s.admAppln.courseBySelectedCourseId.program.programType.id="+studentSearchForm.getProgramTypeId().trim());
		}
		if(studentSearchForm.getFeeStatus()!= null && !studentSearchForm.getFeeStatus().equalsIgnoreCase("3") /*Second condition added to avoid the joining of Fee tables in case of "All" -Smitha */ ){
			if(mode.equalsIgnoreCase("export"))
				selectQuery.append(" ,f,feeapp ");
			
			joinQuery.append(" join s.feePayments f join f.feePaymentApplicantDetailses feeapp ");
			
			if(studentSearchForm.getFeeStatus().equals("1"))
				whereQuery.append(" and f.isFeePaid = 1");
			if(studentSearchForm.getFeeStatus().equals("2"))
				whereQuery.append(" and f.isChallenPrinted = 1");
			whereQuery.append(" and f.isCancelChallan=0 and feeapp.feeGroup.isOptional=0 and feeapp.semesterNo = 1");
		}
		if (studentSearchForm.getProgramId().trim().length() > 0) {
			whereQuery.append(" and s.admAppln.courseBySelectedCourseId.program.id="+studentSearchForm.getProgramId().trim());
		}
		if (studentSearchForm.getCourseId().trim().length() > 0) {
			whereQuery.append(" and s.admAppln.courseBySelectedCourseId.id="+studentSearchForm.getCourseId().trim());
		}
		if (studentSearchForm.getYear().trim().length() > 0) {
			whereQuery.append(" and s.admAppln.appliedYear="+studentSearchForm.getYear().trim());
		}
		if (studentSearchForm.getNationalityId().trim().length() > 0) {
			joinQuery.append(" left join s.admAppln.personalData.nationality n ");
			whereQuery.append(" and (n.name like '"+studentSearchForm.getNationalityId().trim()+"%' or s.admAppln.personalData.nationalityOthers like '"+studentSearchForm.getNationalityId().trim()+"%')");
		}
		if (studentSearchForm.getBirthCountry().trim().length() > 0) {
			joinQuery.append(" left join s.admAppln.personalData.countryByCountryId c ");
			whereQuery.append(" and (c.name like '"+studentSearchForm.getBirthCountry().trim()+"%' or s.admAppln.personalData.countryOthers like '"+studentSearchForm.getBirthCountry().trim()+"%') ");
		}
		if (studentSearchForm.getBirthState().trim().length() > 0) {
			joinQuery.append(" left join s.admAppln.personalData.stateByStateId st ");
			whereQuery.append(" and (st.name like '"+studentSearchForm.getBirthState().trim()+"%' or s.admAppln.personalData.stateOthers like '"+studentSearchForm.getBirthState().trim()+"%') ");
		}
		if (studentSearchForm.getResidentCategoryId().trim().length() > 0) {
			whereQuery.append(" and s.admAppln.personalData.residentCategory.name like '"+studentSearchForm.getResidentCategoryId().trim()+"%'");
		}
		if (studentSearchForm.getReligionId().trim().length() > 0) {
			joinQuery.append(" left join s.admAppln.personalData.religion r ");
			whereQuery.append(" and (r.name like '"+studentSearchForm.getReligionId().trim()+"%' or s.admAppln.personalData.religionOthers like '"+studentSearchForm.getReligionId().trim()+"%') ");
		}
		if (studentSearchForm.getSubReligionId().trim().length() > 0) {
			joinQuery.append(" left join s.admAppln.personalData.religionSection rs ");
			whereQuery.append(" and (rs.name like '"+studentSearchForm.getSubReligionId().trim()+"%' or s.admAppln.personalData.religionSectionOthers like '"+studentSearchForm.getSubReligionId().trim()+"%') ");
		}
		if (studentSearchForm.getCasteCategoryId().trim().length() > 0) {
			joinQuery.append(" left join s.admAppln.personalData.caste caste ");
			whereQuery.append(" and (caste.name like '"+studentSearchForm.getCasteCategoryId().trim()+"%' or s.admAppln.personalData.casteOthers like '"+studentSearchForm.getCasteCategoryId().trim()+"%') ");
		}
		if (studentSearchForm.getBelongsTo().equals('R')
				|| studentSearchForm.getBelongsTo().equals('U')) {
			whereQuery.append(" and s.admAppln.personalData.ruralUrban='"+studentSearchForm.getBelongsTo()+"'");
		}
		if (studentSearchForm.getGender().trim().length() > 0) {
			whereQuery.append(" and s.admAppln.personalData.gender='"+studentSearchForm.getBelongsTo()+"'");
		}
		if (studentSearchForm.getBloodGroup().trim().length() > 0) {
			whereQuery.append(" and s.admAppln.personalData.bloodGroup='"+studentSearchForm.getBloodGroup().trim()+"'");
		}
		if (studentSearchForm.getWeightage().trim().length() > 0) {
			whereQuery.append(" and s.admAppln.totalWeightage >="+studentSearchForm.getWeightage().trim());
		}
		
		if (studentSearchForm.getWeightageTO().trim().length() > 0) {
			whereQuery.append(" and s.admAppln.totalWeightage <="+studentSearchForm.getWeightage().trim());
		}
		
		if (studentSearchForm.getApplicantName().trim().length() > 0) {
			whereQuery.append(" and s.admAppln.personalData.firstName like '"+studentSearchForm.getApplicantName().trim()+"%'");
		}
		if (studentSearchForm.getUniversity().trim().length() > 0) {
			joinQuery.append(" join s.admAppln.personalData.ednQualifications edn ");
			joinQuery.append(" left join edn.university u ");
			whereQuery.append(" and edn.docChecklist.isPreviousExam=1 ");
			whereQuery.append("  and (u.name like '"+studentSearchForm.getUniversity().trim()+"%' or edn.universityOthers like '"+studentSearchForm.getUniversity().trim()+"%') ");
		}
		if (studentSearchForm.getInstitute().trim().length() > 0) {
			if (studentSearchForm.getUniversity().trim().length() == 0) {
				joinQuery.append(" join s.admAppln.personalData.ednQualifications edn ");
				whereQuery.append(" and edn.docChecklist.isPreviousExam=1 ");
			}
			joinQuery.append(" left join edn.college col ");
			whereQuery.append(" and (col.name like '"+studentSearchForm.getInstitute().trim()+"%' or edn.institutionNameOthers like '"+studentSearchForm.getInstitute().trim()+"%') ");
		}
		if (studentSearchForm.getMarksObtained().trim().length() > 0) {
			if (studentSearchForm.getUniversity().trim().length() == 0 && studentSearchForm.getInstitute().trim().length() == 0) {
				joinQuery.append(" join s.admAppln.personalData.ednQualifications edn ");
				whereQuery.append(" and edn.docChecklist.isPreviousExam=1 ");
			}
			whereQuery.append(" and edn.percentage >="+studentSearchForm.getMarksObtained().trim());
		}

		if (studentSearchForm.getMarksObtainedTO().trim().length() > 0) {
			if (studentSearchForm.getUniversity().trim().length() == 0 && studentSearchForm.getInstitute().trim().length() == 0 && studentSearchForm.getMarksObtained().trim().length() == 0) {
				joinQuery.append(" join s.admAppln.personalData.ednQualifications edn ");
			}
			whereQuery.append(" and edn.percentage <="+studentSearchForm.getMarksObtained().trim());
		}
		
		if (studentSearchForm.getAdmissionDateFrom().trim().length() > 0) {
			whereQuery.append(" and s.admAppln.admissionDate >= '"+CommonUtil.ConvertStringToSQLDate(studentSearchForm.getAdmissionDateFrom().trim())+"'");
		}
		
		if (studentSearchForm.getAdmissionDateTo().trim().length() > 0) {
			whereQuery.append(" and s.admAppln.admissionDate <= '"+CommonUtil.ConvertStringToSQLDate(studentSearchForm.getAdmissionDateTo().trim())+"'");
		}
		
		if (studentSearchForm.getAppliedDateFrom().trim().length() > 0) {
			whereQuery.append(" and s.admAppln.createdDate >= '"+CommonUtil.ConvertStringToSQLDate(studentSearchForm.getAppliedDateFrom().trim())+"'");
		}
		if (studentSearchForm.getAppliedDateTo().trim().length() > 0) {
			whereQuery.append(" and s.admAppln.createdDate <= '"+CommonUtil.ConvertStringToSQLDate(studentSearchForm.getAppliedDateTo().trim())+"'");
		}
		
		
		if (studentSearchForm.getInterviewType().trim().length() > 0) {
			joinQuery.append(" join s.admAppln.interviewSelecteds intSelected ");
			whereQuery.append(" and intSelected.interviewProgramCourse.id="+studentSearchForm.getInterviewType().trim());
		}
		
		if (studentSearchForm.getInterviewResult().trim().length() > 0) {
			joinQuery.append(" join s.admAppln.interviewResults ir ");
			whereQuery.append(" and ir.interviewProgramCourse.id="+studentSearchForm.getInterviewType().trim()+" and ir.interviewStatus.id="+studentSearchForm.getInterviewResult().trim());
		}
		
		if (studentSearchForm.getInterviewStartDate().trim().length() > 0) {
			joinQuery.append(" join s.admAppln.interviewCards ic ");
			whereQuery.append("  and ic.interview.date >='"+CommonUtil.ConvertStringToSQLDate(studentSearchForm.getInterviewStartDate().trim())+"'");
		}
		if (studentSearchForm.getInterviewEndDate().trim().length() > 0) {
			if (studentSearchForm.getInterviewStartDate().trim().length() == 0) 
				joinQuery.append(" join s.admAppln.interviewCards ic ");
			whereQuery.append("  and ic.interview.date <='"+CommonUtil.ConvertStringToSQLDate(studentSearchForm.getInterviewEndDate().trim())+"'");
		}
		if(studentSearchForm.getSemister()!= null && !studentSearchForm.getSemister().trim().isEmpty()){
			whereQuery.append(" and s.classSchemewise.curriculumSchemeDuration.semesterYearNo= "+studentSearchForm.getSemister());
		}
		if(studentSearchForm.getStatus().trim().length() > 0){
			if(studentSearchForm.getStatus().equals("selected")) {
				whereQuery.append("and s.admAppln.isSelected=1 and s.admAppln.isApproved=1");
			}
			else if(studentSearchForm.getStatus().equals("rejected")) {
				whereQuery.append("and s.admAppln.isSelected=0");			
			} else if(studentSearchForm.getStatus().equals("canceled")){
				whereQuery.append(" and s.admAppln.isCancelled=1");			
			}else if(studentSearchForm.getStatus().equals("admitted")){
				whereQuery.append(" and s.isAdmitted=1");			
			}
			else if(studentSearchForm.getStatus().equals("other")){
				whereQuery.append(" and s.admAppln.admStatus is not null");			
			}
			if(!studentSearchForm.getStatus().equals("canceled"))
				whereQuery.append(" and s.admAppln.isCancelled=0");
		}/*else{     // Commented this code as in normal report for all students, even the canceled students should be listed-Smitha
			whereQuery.append(" and s.admAppln.isCancelled=0");
		}*/
		if(studentSearchForm.getCenterId()!=null && !studentSearchForm.getCenterId().trim().isEmpty())
			whereQuery.append(" and s.admAppln.examCenter.id="+studentSearchForm.getCenterId());
		
		return selectQuery.toString()+query.toString()+joinQuery.toString()+whereQuery.toString()+" group by s.admAppln.id";
	}


	/**
	 * @param list
	 * @return
	 */
	public static List<CandidateSearchTO> convertBoListToToList( List<Object[]> list) throws Exception {
		List<CandidateSearchTO> toList=new ArrayList<CandidateSearchTO>();
		if(list!=null && !list.isEmpty()){
			Iterator<Object[]> itr=list.iterator();
			CandidateSearchTO to=null;
			while (itr.hasNext()) {
				Object[] obj = itr.next();
				to=new CandidateSearchTO();
				if(obj[2]!=null && !obj[2].toString().isEmpty()){
					to.setApplnNo(Integer.parseInt(obj[2].toString()));					
				}
				if(obj[6]!=null && !obj[6].toString().isEmpty()){
					to.setTotalWeightage(Double.parseDouble(obj[6].toString()));					
				}
				to.setName(obj[3]!=null?obj[3].toString():"" +obj[4]!=null?obj[4].toString():"" +obj[5]!=null?obj[5].toString():"" );
				if(obj[7]!=null && !obj[7].toString().isEmpty()){
					to.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(obj[7].toString(), "yyyy-mm-dd", "dd-mm-yyyy"));
				}
				if(obj[8]!=null && !obj[8].toString().isEmpty()){
					to.setGender(obj[8].toString());
				}
				if(obj[9]!=null && !obj[9].toString().isEmpty()){
					to.setRollNo(obj[9].toString());
				}
				if(obj[10]!=null && !obj[10].toString().isEmpty()){
					to.setRegNo(obj[10].toString());
				}
				toList.add(to);
			}
		}
		return toList;
	}
	/**
	 * @param applnWorkExperience
	 * @param months2 
	 * @return
	 */
	private static float totalExpYears(ApplicantWorkExperience applnWorkExperience, float yrs) {
		Calendar c1 = new GregorianCalendar();
		Calendar c2 = new GregorianCalendar();	
	
	int totalDays=0;
	if(applnWorkExperience.getFromDate()!=null)
	{
		Date startDate=applnWorkExperience.getFromDate();
		c1.setTime(startDate);
	}
	if(applnWorkExperience.getToDate()!=null)
	{
	  Date	endDate=applnWorkExperience.getToDate();
		 c2.setTime(endDate);
	}
   
    if(c1.after(c2))
    {
        throw new RuntimeException("End Date earlier than Start Date");
    }
    totalDays += c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
    int y2 = c2.get(Calendar.YEAR);

    while (c1.get(Calendar.YEAR) != y2)
    {
    	totalDays += c1.getActualMaximum(Calendar.DAY_OF_YEAR);
        c1.add(Calendar.YEAR, 1);
    }

    double noOfYear=(double)totalDays/365;
    BigDecimal bd = new BigDecimal(Double.toString(noOfYear));
	bd = bd.setScale(1, BigDecimal.ROUND_UP);
	 yrs = yrs+Float.parseFloat(bd.toString());
	
	return yrs;
    
}
	
	/**Code added by Sudhir
	 * @param applicantWorkExperiences
	 * @param candidateSearchTO
	 * @param displayTo
	 * @throws Exception
	 */
	private static void getTotalYearsOfExperience(Set<ApplicantWorkExperience> applicantWorkExperiences, 
			CandidateSearchTO candidateSearchTO, CandidateSearchTO displayTo)throws Exception {

		int count= 1;
		String years = null;
		int totalDays=0;
		ApplicantWorkExperience applnWorkExperience = null;
		Iterator<ApplicantWorkExperience> itr = applicantWorkExperiences.iterator();
		while(itr.hasNext()){
			applnWorkExperience = (ApplicantWorkExperience)itr.next();
			if(count==1){
				if(displayTo.getFirstNameofOrganisationDisp() != null && displayTo.getFirstNameofOrganisationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
					candidateSearchTO.setFirstNameOfOra(applnWorkExperience.getOrganization());
				if(displayTo.getFirstDesignationDisp() != null && displayTo.getFirstDesignationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
					candidateSearchTO.setFirstDesignation(applnWorkExperience.getPosition());
				if(displayTo.getFirstFromDateDisp() != null && displayTo.getFirstFromDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
					candidateSearchTO.setFirstFromDate(CommonUtil.getStringDate(applnWorkExperience.getFromDate()));
				if(displayTo.getFirstToDateDisp() != null && displayTo.getFirstToDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
					candidateSearchTO.setFirstTODate(CommonUtil.getStringDate(applnWorkExperience.getToDate()));
				
				if(applnWorkExperience.getToDate()!=null && applnWorkExperience.getFromDate()!=null ){
				Calendar c1 = new GregorianCalendar();
		        Calendar c2 = new GregorianCalendar();				
				
				if(applnWorkExperience.getFromDate()!=null)
				{
					Date startDate=applnWorkExperience.getFromDate();
					c1.setTime(startDate);
				}
				if(applnWorkExperience.getToDate()!=null)
				{
				  Date	endDate= applnWorkExperience.getToDate();
					 c2.setTime(endDate);
				}
		       
		        if(c1.after(c2))
		        {
		            throw new RuntimeException("End Date earlier than Start Date");
		        }
		        totalDays += c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
		        int y2 = c2.get(Calendar.YEAR);

		        while (c1.get(Calendar.YEAR) != y2)
		        {
		        	totalDays += c1.getActualMaximum(Calendar.DAY_OF_YEAR);
		            c1.add(Calendar.YEAR, 1);
		        }
				
			}
			}
			if(count==2){
				if(displayTo.getSecondNameofOrganisationDisp() != null && displayTo.getSecondNameofOrganisationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
					candidateSearchTO.setSecondNameOfOra(applnWorkExperience.getOrganization());
				if(displayTo.getSecondDesignationDisp() != null && displayTo.getSecondDesignationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
					candidateSearchTO.setSecondDesignation(applnWorkExperience.getPosition());
				if(displayTo.getSecondFromDateDisp() != null && displayTo.getSecondFromDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
					candidateSearchTO.setSecondFromDate(CommonUtil.getStringDate(applnWorkExperience.getFromDate()));
				if(displayTo.getSecondToDateDisp() != null && displayTo.getSecondToDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
					candidateSearchTO.setSecondTODate(CommonUtil.getStringDate(applnWorkExperience.getToDate()));
						if(applnWorkExperience.getToDate()!=null && applnWorkExperience.getFromDate()!=null ){
		
						Calendar c1 = new GregorianCalendar();
				        Calendar c2 = new GregorianCalendar();				
						
						if(applnWorkExperience.getFromDate()!=null)
						{
							Date startDate=applnWorkExperience.getFromDate();
							c1.setTime(startDate);
						}
						if(applnWorkExperience.getToDate()!=null)
						{
						  Date	endDate= applnWorkExperience.getToDate();
							 c2.setTime(endDate);
						}
				       
				        if(c1.after(c2))
				        {
				            throw new RuntimeException("End Date earlier than Start Date");
				        }
				        totalDays += c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
				        int y2 = c2.get(Calendar.YEAR);
		
				        while (c1.get(Calendar.YEAR) != y2)
				        {
				        	totalDays += c1.getActualMaximum(Calendar.DAY_OF_YEAR);
				            c1.add(Calendar.YEAR, 1);
				        }
					}
			}
			if(count==3){
				if(displayTo.getThirdNameofOrganisationDisp() != null && displayTo.getThirdNameofOrganisationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
					candidateSearchTO.setThirdNameOfOra(applnWorkExperience.getOrganization());
				if(displayTo.getThirdDesignationDisp() != null && displayTo.getThirdDesignationDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
					candidateSearchTO.setThirdDesignation(applnWorkExperience.getPosition());
				if(displayTo.getThirdFromDateDisp() != null && displayTo.getThirdFromDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
					candidateSearchTO.setThirdFromDate(CommonUtil.getStringDate(applnWorkExperience.getFromDate()));
				if(displayTo.getThirdToDateDisp() != null && displayTo.getThirdToDateDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
					candidateSearchTO.setThirdTODate(CommonUtil.getStringDate(applnWorkExperience.getToDate()));
				
				if(applnWorkExperience.getToDate()!=null && applnWorkExperience.getFromDate()!=null ){

					Calendar c1 = new GregorianCalendar();
			        Calendar c2 = new GregorianCalendar();				
					
					if(applnWorkExperience.getFromDate()!=null)
					{
						Date startDate=applnWorkExperience.getFromDate();
						c1.setTime(startDate);
					}
					if(applnWorkExperience.getToDate()!=null)
					{
					  Date	endDate= applnWorkExperience.getToDate();
						 c2.setTime(endDate);
					}
			       
			        if(c1.after(c2))
			        {
			            throw new RuntimeException("End Date earlier than Start Date");
			        }
			        totalDays += c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
			        int y2 = c2.get(Calendar.YEAR);

			        while (c1.get(Calendar.YEAR) != y2)
			        {
			        	totalDays += c1.getActualMaximum(Calendar.DAY_OF_YEAR);
			            c1.add(Calendar.YEAR, 1);
			        }
				}
			}
			count++;
		}
		double noOfYear=(double)totalDays/365;
		BigDecimal bd = new BigDecimal(Double.toString(noOfYear));
		bd = bd.setScale(1, BigDecimal.ROUND_UP);
		years= bd.toString();
		if(displayTo.getTotalExpYearDisp()!= null && displayTo.getTotalExpYearDisp().equalsIgnoreCase(CandidateHelper.DISPLAY))
			candidateSearchTO.setTotalExpYear(years);
		
	}

}