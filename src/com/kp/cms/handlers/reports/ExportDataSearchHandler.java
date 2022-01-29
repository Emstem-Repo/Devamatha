package com.kp.cms.handlers.reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentQualifyexamDetail;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.reports.ExportDataSearchForm;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.helpers.admission.CandidateHelper;
import com.kp.cms.to.reports.ExportDataSearchTO;
import com.kp.cms.transactions.reports.IExportDataSearchTransaction;
import com.kp.cms.transactionsimpl.reports.ExportDataSearchTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class ExportDataSearchHandler {

private static final Log log = LogFactory.getLog(ExportDataSearchHandler.class);

	private static final String DISPLAY = "display";
	
	public static volatile ExportDataSearchHandler dataSearchHandler = null;
	
	/**
	 * This method is used to create a sin
	 * gle instance of ExportDataSearchHandler.
	 * @return unique instance of ExportDataSearchHandler class.
	 */
	
	public static ExportDataSearchHandler getInstance() {
		if (dataSearchHandler == null) {
			dataSearchHandler = new ExportDataSearchHandler();
			return dataSearchHandler;
		}
		return dataSearchHandler;
	}
	
	IExportDataSearchTransaction searchTransaction = ExportDataSearchTransactionImpl.getInstance();
	
	
	public List<ExportDataSearchTO> getAdmApplnDetails(ExportDataSearchForm dataSearchForm) throws Exception {
		
		List<Student> studList = searchTransaction.getSearchCriteria(getSelectionSearchCriteria(dataSearchForm));
		List<ExportDataSearchTO> list = convertBOtoTO(studList, dataSearchForm);
		return list;
	}
	
	
	/**
	 * @param dataSearchForm
	 * @return
	 * This method will build dynamic query
	 */

	private String commonSearch(ExportDataSearchForm dataSearchForm) {
		log.info("entered commonSearch method in ExportDataSearchHandler class");
		
		String query = "";
		
		if (dataSearchForm.getProgramTypeId().trim().length() > 0 && !StringUtils.isEmpty(dataSearchForm.getProgramTypeId())) {
			String programType = " stud.admAppln.courseBySelectedCourseId.program.programType.id = "+ dataSearchForm.getProgramTypeId();
				query = query + programType;
		}
		if(dataSearchForm.getProgramId().trim().length() > 0 && !StringUtils.isEmpty(dataSearchForm.getProgramId())){
			String program = " and stud.admAppln.courseBySelectedCourseId.program.id = "+ dataSearchForm.getProgramId();	
				query = query + program;
		}	
		if(dataSearchForm.getSemister().trim().length() > 0 && !StringUtils.isEmpty(dataSearchForm.getSemister())){
			String semister = " and stud.classSchemewise.classes.termNumber = "+ dataSearchForm.getSemister();
				query = query + semister;
		}
		if(dataSearchForm.getCourseNames() != null) {
			String [] tempCoursesArray = dataSearchForm.getCourseNames();
			StringBuilder courses=new StringBuilder();
			for(int i=0;i<tempCoursesArray.length;i++){
				courses.append(tempCoursesArray[i]);
				 if(i<(tempCoursesArray.length-1)){
					 courses.append(",");
				 }
			}
		String course = " and stud.admAppln.courseBySelectedCourseId.id in ("+ courses +")";
		query = query + course;
		}
		if (dataSearchForm.getAcademicYear().trim().length() > 0) {
			String appliedYear = " stud.admAppln.appliedYear = "+ dataSearchForm.getAcademicYear();
			if(query.isEmpty()){
				query = query + appliedYear;
			}else{
				query = query +" and "+appliedYear;
			}
			
		}
		if(dataSearchForm.getApplicationNo()!=null && !dataSearchForm.getApplicationNo().isEmpty()){
			query=query+" and stud.admAppln.applnNo="+dataSearchForm.getApplicationNo();
		}
		if(dataSearchForm.getRegNo()!=null && !dataSearchForm.getRegNo().isEmpty()){
			query=query+" and stud.registerNo='"+dataSearchForm.getRegNo()+"'";
		}
		if(dataSearchForm.getRollNo()!=null && !dataSearchForm.getRollNo().isEmpty()){
			query=query+" and stud.rollNo='"+dataSearchForm.getRollNo()+"'";
		}
		query = query + " and stud.isAdmitted = 1 and stud.admAppln.isCancelled=0 and (stud.isHide = 0 or stud.isHide is null) ";		
		log.info("exit of commonSearch method in ExportDataSearchHandler class");
		return query;
	}

	/**
	 * @param dataSearchForm
	 * @return value of type string.
	 * This method will give final query
	 */
	public String getSelectionSearchCriteria(ExportDataSearchForm dataSearchForm) {
		log.info("entered getSelectionSearchCriteria method in ExportDataSearchHandler class");
		String statusCriteria = commonSearch(dataSearchForm);
		String searchCriteria= " from Student stud"
				+ " where"+ statusCriteria;					
		log.info("exit of getSelectionSearchCriteria method in ExportDataSearchHandler class");
		return searchCriteria;

	}
	
	public List<Integer>getDetainedOrDiscontinuedStudents()throws Exception
	{
		return searchTransaction.getDetainedOrDiscontinuedStudents();
	}
	
	public List<ExportDataSearchTO> convertBOtoTO(List<Student> list, ExportDataSearchForm dataSearchForm) throws Exception{
		log.info("entered convertBOtoTO method in ExportDataSearchHandler class");
		List<ExportDataSearchTO> candidateList = new ArrayList<ExportDataSearchTO>();
		List<Integer> listOfDetainedStudents =getDetainedOrDiscontinuedStudents();
		ExportDataSearchTO exportDataSearchTO = null;
		Iterator<Student> iterator = list.iterator();
		if(list != null && list.size() != 0){
		while (iterator.hasNext()) {
			Student student = (Student) iterator.next();
			exportDataSearchTO = new ExportDataSearchTO();
			exportDataSearchTO.setChecked(true);
			exportDataSearchTO.setUpdatedChecked(false);
			
			Set<FeePayment> feePaymentSet = student.getFeePayments();
			boolean feePaid = false;
			if(feePaymentSet!= null){
				Iterator<FeePayment> feeItr = feePaymentSet.iterator();
				while (feeItr.hasNext()) {
					FeePayment feePayment = (FeePayment) feeItr.next();
					if(feePayment.getIsFeePaid()){
						feePaid = true;
						break;
					}else
					{
						feePaid = false;
					}
					
				}
			}
			if(!feePaid){
				continue;
			}
			if(!listOfDetainedStudents.contains(student.getId())){
			if(student != null && student.getAdmAppln() != null){
				exportDataSearchTO.setApplnNo(student.getAdmAppln().getApplnNo());
			}
			if(student != null && student.getAdmAppln() != null 
					&& student.getAdmAppln().getCourseBySelectedCourseId() != null){
				exportDataSearchTO.setCourseCode(student.getAdmAppln().getCourseBySelectedCourseId().getCode());
				exportDataSearchTO.setCourseName(student.getAdmAppln().getCourseBySelectedCourseId().getName());
			}
			
			if(student != null && student.getAdmAppln() != null 
					&& student.getAdmAppln().getAppliedYear() != null){
				dataSearchForm.setAcademicYear(String.valueOf(student.getAdmAppln().getAppliedYear()));
				exportDataSearchTO.setAcedamicYear(String.valueOf(student.getAdmAppln().getAppliedYear())+"-"+String.valueOf(student.getAdmAppln().getAppliedYear()+1));
			}
			
			if(student != null && student.getAdmAppln() != null 
					&& student.getAdmAppln().getCourseBySelectedCourseId().getProgram() != null){
				dataSearchForm.setProgramName(student.getAdmAppln().getCourseBySelectedCourseId().getProgram().getName());
			}
			if(student != null && student.getAdmAppln() != null 
					&& student.getAdmAppln().getCourseBySelectedCourseId().getProgram() != null 
					&& student.getAdmAppln().getCourseBySelectedCourseId().getProgram().getProgramType() != null){
				dataSearchForm.setProgramTypeName(student.getAdmAppln().getCourseBySelectedCourseId().getProgram().getProgramType().getName());
			}
			if(student != null && student.getAdmAppln() != null && student.getClassSchemewise() != null 
					&& student.getClassSchemewise().getClasses() != null 
					&& student.getClassSchemewise().getClasses().getTermNumber() != null){
				exportDataSearchTO.setSemesterNo(String.valueOf(student.getClassSchemewise().getClasses().getTermNumber()));
			}
			else{
				exportDataSearchTO.setSemesterNo("1");
			}
			
			if(student != null && student.getAdmAppln() != null && student.getClassSchemewise() != null 
					&& student.getClassSchemewise().getClasses() != null 
					&& student.getClassSchemewise().getClasses().getSectionName() != null){
				exportDataSearchTO.setSemesterName(String.valueOf(student.getClassSchemewise().getClasses().getSectionName()));
			}
			
			String applicantName = "";
			if (student.getAdmAppln().getPersonalData().getFirstName() != null) {
				applicantName = applicantName
						+ student.getAdmAppln().getPersonalData().getFirstName()
						+ " ";
			}
			if (student.getAdmAppln().getPersonalData().getMiddleName() != null) {
				applicantName = applicantName
						+ student.getAdmAppln().getPersonalData().getMiddleName()
						+ " ";
			}
			if (student.getAdmAppln().getPersonalData().getLastName() != null) {
				applicantName = applicantName
						+ student.getAdmAppln().getPersonalData().getLastName()
						+ " ";
			}
			exportDataSearchTO.setName(applicantName);
			
			if(student != null && student.getRegisterNo() != null){
				exportDataSearchTO.setRegNo(student.getRegisterNo());
			}
			if(student != null && student.getRollNo() != null){
				exportDataSearchTO.setRollNo(student.getRollNo());
			}
			if(student != null && student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null 
					&& student.getAdmAppln().getPersonalData().getFatherName() != null){
				exportDataSearchTO.setFatherName(student.getAdmAppln().getPersonalData().getFatherName());
			}
			if(student != null && student.getAdmAppln()!= null && student.getAdmAppln().getPersonalData() != null 
					&& student.getAdmAppln().getPersonalData().getMotherName() != null){
				exportDataSearchTO.setMotherName(student.getAdmAppln().getPersonalData().getMotherName());
			}
			if(student != null && student.getAdmAppln() != null && student.getAdmAppln().getStudentQualifyexamDetails() != null){
				Set<StudentQualifyexamDetail> set = student.getAdmAppln().getStudentQualifyexamDetails();
				if(set != null && set.size() != 0){
					Iterator<StudentQualifyexamDetail> iterator2 = set.iterator();
					while (iterator2.hasNext()) {
						StudentQualifyexamDetail qualifyexamDetail = (StudentQualifyexamDetail) iterator2.next();
						if(qualifyexamDetail != null){
							exportDataSearchTO.setSecondLanguage(qualifyexamDetail.getSecondLanguage());
						}
					}
				}
			}
			if(student != null && student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null 
					&& student.getAdmAppln().getPersonalData().getRegion() != null){
				exportDataSearchTO.setReligion(student.getAdmAppln().getPersonalData().getReligion().getName());
			}
			if(student != null && student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null 
					&& student.getAdmAppln().getPersonalData().getGender() != null){
				exportDataSearchTO.setGender(student.getAdmAppln().getPersonalData().getGender());
			}
			if(student != null && student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null 
					&& student.getAdmAppln().getPersonalData().getNationality() != null){
				exportDataSearchTO.setNationality(student.getAdmAppln().getPersonalData().getNationality().getName());
			}
			if(student != null && student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null
					&& student.getAdmAppln().getPersonalData().getDateOfBirth() != null){
				exportDataSearchTO.setDateOfBirth(CommonUtil.formatDate(student.getAdmAppln().getPersonalData().getDateOfBirth(), "dd-MM-yyyy"));
			}
			String parentPhone = "";
			if (student.getAdmAppln().getPersonalData().getPhNo1() != null) {
				parentPhone = parentPhone
						+ student.getAdmAppln().getPersonalData().getPhNo1()
						+ " ";
			}
			if (student.getAdmAppln().getPersonalData().getPhNo2() != null) {
				parentPhone = parentPhone
						+ student.getAdmAppln().getPersonalData().getPhNo2()
						+ " ";
			}
			if (student.getAdmAppln().getPersonalData().getPhNo3() != null) {
				parentPhone = parentPhone
						+ student.getAdmAppln().getPersonalData().getPhNo3()
						+ " ";
			}
			if(parentPhone != null){
				exportDataSearchTO.setParentPhone(parentPhone);
			}
			
			String parentMobile = "";
			if (student.getAdmAppln().getPersonalData().getParentMob1() != null) {
				parentMobile = parentMobile
						+ student.getAdmAppln().getPersonalData().getParentMob1()
						+ " ";
			}
			if (student.getAdmAppln().getPersonalData().getParentMob2() != null) {
				parentMobile = parentMobile
						+ student.getAdmAppln().getPersonalData().getParentMob2()
						+ " ";
			}
			if (student.getAdmAppln().getPersonalData().getParentMob3() != null) {
				parentMobile = parentMobile
						+ student.getAdmAppln().getPersonalData().getParentMob3()
						+ " ";
			}
			if(parentMobile != null){
				exportDataSearchTO.setParentMobileNo(parentMobile);
			}
			
			if (student.getAdmAppln().getPersonalData().getCurrentAddressLine1() != null) {
					exportDataSearchTO.setCurrentAddressline1(student.getAdmAppln().getPersonalData().getCurrentAddressLine1());
			}
			if (student.getAdmAppln().getPersonalData().getCurrentAddressLine2() != null) {
				exportDataSearchTO.setCurrentAddressline2(student.getAdmAppln().getPersonalData().getCurrentAddressLine2());
			}
			
			if (student.getAdmAppln().getPersonalData().getPermanentAddressLine1() != null) {
				exportDataSearchTO.setPermanentAddressline1(student.getAdmAppln().getPersonalData().getPermanentAddressLine1());
			}
			if (student.getAdmAppln().getPersonalData().getPermanentAddressLine2() != null) {
				exportDataSearchTO.setPermanentAddressline2(student.getAdmAppln().getPersonalData().getPermanentAddressLine2());
						 
			}
			
			if(student != null && student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null
					&& student.getAdmAppln().getPersonalData().getBloodGroup() != null){
				exportDataSearchTO.setBloodGroup(student.getAdmAppln().getPersonalData().getBloodGroup());
			}	
			StringBuffer phString = new StringBuffer();
			if(student.getAdmAppln().getPersonalData().getPhNo1()!= null){
				phString.append(student.getAdmAppln().getPersonalData().getPhNo1().trim());
			}
			if(student.getAdmAppln().getPersonalData().getPhNo2()!= null){
				phString.append(student.getAdmAppln().getPersonalData().getPhNo2().trim());
			}
			if(student.getAdmAppln().getPersonalData().getPhNo3()!= null){
				phString.append(student.getAdmAppln().getPersonalData().getPhNo3().trim());
			}
			exportDataSearchTO.setPhoneNo(phString.toString());
			
			StringBuffer mobString = new StringBuffer();
			if(student.getAdmAppln().getPersonalData().getMobileNo1()!= null){
				mobString.append(student.getAdmAppln().getPersonalData().getMobileNo1().trim());
			}
			if(student.getAdmAppln().getPersonalData().getMobileNo2()!= null){
				mobString.append(student.getAdmAppln().getPersonalData().getMobileNo2().trim());
			}
			if(student.getAdmAppln().getPersonalData().getMobileNo3()!= null){
				mobString.append(student.getAdmAppln().getPersonalData().getMobileNo3() .trim());
			}
			exportDataSearchTO.setMobileNo(mobString.toString());
			
			exportDataSearchTO.setCurAddressCity(student.getAdmAppln().getPersonalData().getCityByCurrentAddressCityId());
			if(student.getAdmAppln().getPersonalData().getCountryByCountryId()!= null){
				exportDataSearchTO.setCurAddressCountry(student.getAdmAppln().getPersonalData().getCountryByCountryId().getName());
			}
			if(student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId()!= null){
				exportDataSearchTO.setCurAddressState(student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName());
			}
			exportDataSearchTO.setCurAddressZip(student.getAdmAppln().getPersonalData().getCurrentAddressZipCode());
			

			exportDataSearchTO.setPerAddressCity(student.getAdmAppln().getPersonalData().getCityByPermanentAddressCityId());
			if(student.getAdmAppln().getPersonalData().getCountryByPermanentAddressCountryId()!= null){
				exportDataSearchTO.setPerAddressCountry(student.getAdmAppln().getPersonalData().getCountryByPermanentAddressCountryId().getName());
			}
			if(student.getAdmAppln().getPersonalData().getStateByPermanentAddressStateId()!= null){
				exportDataSearchTO.setPerAddressState(student.getAdmAppln().getPersonalData().getStateByPermanentAddressStateId().getName());
			}
			exportDataSearchTO.setPerAddressZip(student.getAdmAppln().getPersonalData().getPermanentAddressZipCode());
			
			
			if(student.getClassSchemewise()!= null && student.getClassSchemewise().getClasses()!= null){
				exportDataSearchTO.setClassCode(student.getClassSchemewise().getClasses().getName());
			}
			
			candidateList.add(exportDataSearchTO);
		}
		}
		}
		log.info("exit of convertBOtoTO method in ExportDataSearchHandler class");
		return candidateList;
	}
	
	/**
	 * This method is used to set the headers in first row and then data in the excel file.
	 * @param dataSearchTO
	 * @param candidateList
	 * @param request
	 * @throws Exception
	 */
	
	@SuppressWarnings("deprecation")
	public void convertListToExcel(ExportDataSearchTO dataSearchTO, List<ExportDataSearchTO> candidateList, HttpServletRequest request, String validTill) throws Exception {
		log.info("entered convertListToExcel method in ExportDataSearchHandler class");
		Properties prop = new Properties();
		try {
			InputStream inputStream = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inputStream);
		} 
		catch (IOException e) {
			log.error("Error occured at exportTOExcel of ExportDataSearchHandler ",e);
			throw new IOException(e);
		}
		String fileName=prop.getProperty(CMSConstants.PATH_FOR_ID_CARD);
		File excelFile = new File(request.getRealPath("")+ "//TempFiles//"+fileName);	
		if(excelFile.exists()){
			excelFile.delete();
		}
		//-----new Code-------------------------
		
		FileOutputStream fos = null;
		ByteArrayOutputStream bos=null;
		
		XSSFWorkbook wb=null;
		XSSFSheet sheet=null;
		XSSFRow row=null;
		XSSFCell cell=null;
		try	{
		if(candidateList!=null){
			
			
		int count = 0;
		Iterator<ExportDataSearchTO> iterator = candidateList.iterator();
		
		
			wb=new XSSFWorkbook();
			XSSFCellStyle cellStyle=wb.createCellStyle();
			CreationHelper createHelper = wb.getCreationHelper();

			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

			sheet = wb.createSheet("SendDataForIdCard");
			row = sheet.createRow(count);
			count = sheet.getFirstRowNum();
		
			//-----new Code-------------------------
		
		
		
	/*	FileOutputStream fos = null;
		HSSFWorkbook wb = null;
		HSSFSheet sheet =null;
		HSSFRow row = null;
		HSSFCell cell = null;*/
		/*try	{
		if(candidateList!=null){
		int count = 0;
		Iterator iterator = candidateList.iterator();
		
			wb = new HSSFWorkbook();
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("MMM/dd/yyyy"));
			sheet = wb.createSheet("SendDataForIdCard");
			row = sheet.createRow(count);
			count = sheet.getFirstRowNum();*/
			// Create cells in the row and put some data in it.
			/*if(candidateSearchTO.getRegNoDisp() != null && candidateSearchTO.getRegNoDisp().equalsIgnoreCase(CandidateHelper.DISPLAY)){
				row.createCell((short)candidateSearchTO.getRegNoPois()).setCellValue(CMSConstants.REGNO);
			}*/
			if(dataSearchTO.getRegNoDisp() != null && dataSearchTO.getRegNoDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getRegNoPois()).setCellValue(CMSConstants.REGNO);
			}
			if(dataSearchTO.getRollNoDisp() != null && dataSearchTO.getRollNoDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getRollNoPois()).setCellValue(CMSConstants.ROLLNO);
			}
			if(dataSearchTO.getApplnNoDisp() != null && dataSearchTO.getApplnNoDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getApplnNoPois()).setCellValue(CMSConstants.APPLICATION_NUM);
			}
			if(dataSearchTO.getCourseCodeDisp() != null && dataSearchTO.getCourseCodeDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getCourseCodePois()).setCellValue(CMSConstants.COURSE_CODE);
			}
			if(dataSearchTO.getCourseNameDisp() != null && dataSearchTO.getCourseNameDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getCourseNamePois()).setCellValue(CMSConstants.COURSE_NAME);
			}
			if(dataSearchTO.getNameDisp() != null && dataSearchTO.getNameDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getNamePois()).setCellValue(CMSConstants.STUDENT_NAME);
			}
			if(dataSearchTO.getAcedamicYearDisp() != null && dataSearchTO.getAcedamicYearDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getAcedamicYearPois()).setCellValue(CMSConstants.ACEDAMIC_YEAR);
			}
			if(dataSearchTO.getSemesterNameDisp() != null && dataSearchTO.getSemesterNameDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getSemesterNamePois()).setCellValue(CMSConstants.SEMESTER_NAME);
			}
			if(dataSearchTO.getFathersNameDisp() != null && dataSearchTO.getFathersNameDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getFathersNamePois()).setCellValue(CMSConstants.FATHERS_NAME);
			}
			if(dataSearchTO.getMothersNameDisp() != null && dataSearchTO.getMothersNameDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getMothersNamePois()).setCellValue(CMSConstants.MOTHERS_NAME);
			}
			if(dataSearchTO.getSecondLanguageDisp() != null && dataSearchTO.getSecondLanguageDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getSecondLanguagePois()).setCellValue(CMSConstants.SECOND_LANGUAG);
			}
			if(dataSearchTO.getReligionDisp() != null && dataSearchTO.getReligionDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getReligionPois()).setCellValue(CMSConstants.RELIGIN);
			}
			if(dataSearchTO.getGenderDisp() != null && dataSearchTO.getGenderDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getGenderPois()).setCellValue(CMSConstants.GENDER);
			}
			if(dataSearchTO.getNationalityDisp() != null && dataSearchTO.getNationalityDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getNationalityPois()).setCellValue(CMSConstants.NATIONALIT);
			}
			if(dataSearchTO.getParentPhoneDisp() != null && dataSearchTO.getParentPhoneDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getParentPhonePois()).setCellValue(CMSConstants.PARENT_PHONE);
			}
			if(dataSearchTO.getParentMobileNoDisp() != null && dataSearchTO.getParentMobileNoDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getParentMobileNoPois()).setCellValue(CMSConstants.PARENT_MOBILE_NUMBER);
			}
			if(dataSearchTO.getPermanentAddressLine1Disp() != null && dataSearchTO.getPermanentAddressLine1Disp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getPermanentAddressLine1Pois()).setCellValue(CMSConstants.PERMANENT_ADDR_LINE1);
			}
			if(dataSearchTO.getPermanentAddressLine2Disp() != null && dataSearchTO.getPermanentAddressLine2Disp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getPermanentAddressLine2Pois()).setCellValue(CMSConstants.PERMANENT_ADDR_LINE2);
			}
			if(dataSearchTO.getPerAddressCityDisp() != null && dataSearchTO.getPerAddressCityDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getPerAddressCityPois()).setCellValue(CMSConstants.PER_ADDRESS_CITY);
			}
			
			if(dataSearchTO.getPerAddressCountryDisp() != null && dataSearchTO.getPerAddressCountryDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getPerAddressCountryPois()).setCellValue(CMSConstants.PER_ADDRESS_COUNTRY);
			}
			
			if(dataSearchTO.getPerAddressStateDisp() != null && dataSearchTO.getPerAddressStateDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getPerAddressStatePois()).setCellValue(CMSConstants.PER_ADDRESS_STATE);
			}
			
			if(dataSearchTO.getPerAddressZipDisp() != null && dataSearchTO.getPerAddressZipDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getPerAddressZipPois()).setCellValue(CMSConstants.PER_ADDRESS_ZIP);
			}
			
			if(dataSearchTO.getCurrentAddressLine1Disp() != null && dataSearchTO.getCurrentAddressLine1Disp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getCurrentAddressLine1Pois()).setCellValue(CMSConstants.CURRENT_ADDR_LINE1);
			}
			if(dataSearchTO.getCurrentAddressLine2Disp() != null && dataSearchTO.getCurrentAddressLine2Disp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getCurrentAddressLine2Pois()).setCellValue(CMSConstants.CURRENT_ADDR_LINE2);
			}
			if(dataSearchTO.getCurAddressCityDisp() != null && dataSearchTO.getCurAddressCityDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getCurAddressCityPois()).setCellValue(CMSConstants.CURRENT_ADDRESS_CITY);
			}
			
			if(dataSearchTO.getCurAddressCountryDisp() != null && dataSearchTO.getCurAddressCountryDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getCurAddressCountryPois()).setCellValue(CMSConstants.CURRENT_ADDRESS_COUNTRY);
			}
			
			if(dataSearchTO.getCurAddressStateDisp() != null && dataSearchTO.getCurAddressStateDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getCurAddressStatePois()).setCellValue(CMSConstants.CURRENT_ADDRESS_STATE);
			}
			
			if(dataSearchTO.getCurAddressZipDisp() != null && dataSearchTO.getCurAddressZipDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getCurAddressZipPois()).setCellValue(CMSConstants.CURRENT_ADDRESS_ZIP);
			}			
			
			/*if(dataSearchTO.getParentAdressLine1Disp() != null && dataSearchTO.getParentAdressLine1Disp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getParentAdressLine1Pois()).setCellValue(CMSConstants.PARENT_ADDR_LINE1);
			}
			if(dataSearchTO.getParentAdressLine2Disp() != null && dataSearchTO.getParentAdressLine2Disp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getParentAdressLine2Pois()).setCellValue(CMSConstants.PARENT_ADDR_LINE2);
			}
			if(dataSearchTO.getParentAdressLine3Disp() != null && dataSearchTO.getParentAdressLine3Disp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getParentAdressLine3Pois()).setCellValue(CMSConstants.PARENT_ADDR_LINE3);
			}			*/
			if(dataSearchTO.getDateOfBirthDisp() != null && dataSearchTO.getDateOfBirthDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getDateOfBirthPois()).setCellValue(CMSConstants.DATE_OF_BIRTH);
			}			
			if(dataSearchTO.getBloodGroupDisp() != null && dataSearchTO.getBloodGroupDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getBloodGroupPois()).setCellValue(CMSConstants.BLOOD_GROUP);
			}
			if(dataSearchTO.getPhoneNoDisp() != null && dataSearchTO.getPhoneNoDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getPhoneNoPois()).setCellValue(CMSConstants.PHONE_NO);
			}
			if(dataSearchTO.getMobileNoDisp() != null && dataSearchTO.getMobileNoDisp().equalsIgnoreCase(DISPLAY)){
				row.createCell((short)dataSearchTO.getMobileNoPois()).setCellValue(CMSConstants.MOBILE_NO);
			}
			row.createCell((short)dataSearchTO.getClassCodePois()).setCellValue(CMSConstants.CLASS_CODE);
			row.createCell((short)dataSearchTO.getValidTillPos()).setCellValue(CMSConstants.VALID_TILL);
			row.createCell((short)34).setCellValue("");
			
			
			
			
			while (iterator.hasNext()) {
				ExportDataSearchTO exportDataSearchTO = (ExportDataSearchTO) iterator.next();
				//check for selected candidates
				if(exportDataSearchTO != null && exportDataSearchTO.isUpdatedChecked()){
					count = count +1;
					row = sheet.createRow(count);				
					if(dataSearchTO.getRegNoDisp() != null && dataSearchTO.getRegNoDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getRegNo()!=null){
						row.createCell((short)dataSearchTO.getRegNoPois()).setCellValue(exportDataSearchTO.getRegNo());
					}	
					if(dataSearchTO.getRollNoDisp() != null && dataSearchTO.getRollNoDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getRollNo()!=null){
						row.createCell((short)dataSearchTO.getRollNoPois()).setCellValue(exportDataSearchTO.getRollNo());
					}
					if(dataSearchTO.getApplnNoDisp() != null && dataSearchTO.getApplnNoDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getApplnNo()!= 0){
						row.createCell((short)dataSearchTO.getApplnNoPois()).setCellValue(exportDataSearchTO.getApplnNo());
					}
					if(dataSearchTO.getCourseCodeDisp() != null && dataSearchTO.getCourseCodeDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getCourseCode()!=null){
						row.createCell((short)dataSearchTO.getCourseCodePois()).setCellValue(exportDataSearchTO.getCourseCode());
					}
					if(dataSearchTO.getCourseNameDisp() != null && dataSearchTO.getCourseNameDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getCourseName()!=null){
						row.createCell((short)dataSearchTO.getCourseNamePois()).setCellValue(exportDataSearchTO.getCourseName());
					}
					if(dataSearchTO.getNameDisp() != null && dataSearchTO.getNameDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getName()!= null){
						row.createCell((short)dataSearchTO.getNamePois()).setCellValue(exportDataSearchTO.getName());
					}
					if(dataSearchTO.getAcedamicYearDisp() != null && dataSearchTO.getAcedamicYearDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getAcedamicYear()!=null){
						row.createCell((short)dataSearchTO.getAcedamicYearPois()).setCellValue(exportDataSearchTO.getAcedamicYear());
					}
/*					if(dataSearchTO.getSemesterNameDisp() != null && dataSearchTO.getSemesterNameDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getSemesterName()!=null){
						row.createCell((short)dataSearchTO.getSemesterNamePois()).setCellValue(exportDataSearchTO.getSemesterName());
					}
*/
					if(dataSearchTO.getSemesterNameDisp() != null && dataSearchTO.getSemesterNameDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getSemesterNo()!=null){
						row.createCell((short)dataSearchTO.getSemesterNamePois()).setCellValue(exportDataSearchTO.getSemesterNo());
					}

					if(dataSearchTO.getFathersNameDisp() != null && dataSearchTO.getFathersNameDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getFatherName()!=null){
						row.createCell((short)dataSearchTO.getFathersNamePois()).setCellValue(exportDataSearchTO.getFatherName());
					}
					if(dataSearchTO.getMothersNameDisp() != null && dataSearchTO.getMothersNameDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getMotherName()!=null){
						row.createCell((short)dataSearchTO.getMothersNamePois()).setCellValue(exportDataSearchTO.getMotherName());
					}
					if(dataSearchTO.getSecondLanguageDisp() != null && dataSearchTO.getSecondLanguageDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getSecondLanguage()!=null){
						row.createCell((short)dataSearchTO.getSecondLanguagePois()).setCellValue(exportDataSearchTO.getSecondLanguage());
					}
					if(dataSearchTO.getReligionDisp() != null && dataSearchTO.getReligionDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getReligion()!=null){
						row.createCell((short)dataSearchTO.getReligionPois()).setCellValue(exportDataSearchTO.getReligion());
					}
					if(dataSearchTO.getGenderDisp() != null && dataSearchTO.getGenderDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getGender()!= null){
						row.createCell((short)dataSearchTO.getGenderPois()).setCellValue(exportDataSearchTO.getGender());
					}
					if(dataSearchTO.getNationalityDisp() != null && dataSearchTO.getNationalityDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getNationality()!=null){
						row.createCell((short)dataSearchTO.getNationalityPois()).setCellValue(exportDataSearchTO.getNationality());
					}
					if(dataSearchTO.getParentPhoneDisp() != null && dataSearchTO.getParentPhoneDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getParentPhone()!=null){
						cell = row.createCell((short)dataSearchTO.getParentPhonePois());
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(exportDataSearchTO.getParentPhone());
					}
					if(dataSearchTO.getParentMobileNoDisp() != null && dataSearchTO.getParentMobileNoDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getParentMobileNo()!=null){
						cell = row.createCell((short)dataSearchTO.getParentMobileNoPois());
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(exportDataSearchTO.getParentMobileNo());
					}
					
					
					if(dataSearchTO.getPermanentAddressLine1Disp() != null && dataSearchTO.getPermanentAddressLine1Disp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getPermanentAddressline1()!=null){
						row.createCell((short)dataSearchTO.getPermanentAddressLine1Pois()).setCellValue(exportDataSearchTO.getPermanentAddressline1());
					}
					if(dataSearchTO.getPermanentAddressLine2Disp() != null && dataSearchTO.getPermanentAddressLine2Disp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getPermanentAddressline2()!=null){
						row.createCell((short)dataSearchTO.getPermanentAddressLine2Pois()).setCellValue(exportDataSearchTO.getPermanentAddressline2());
					}
					if(dataSearchTO.getPerAddressCityDisp() != null && dataSearchTO.getPerAddressCityDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getPerAddressCity()!= null){
						row.createCell((short)dataSearchTO.getPerAddressCityPois()).setCellValue(exportDataSearchTO.getPerAddressCity());
					}
					
					if(dataSearchTO.getPerAddressCountryDisp() != null && dataSearchTO.getPerAddressCountryDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getPerAddressCountry()!= null){
						row.createCell((short)dataSearchTO.getPerAddressCountryPois()).setCellValue(exportDataSearchTO.getPerAddressCountry());
					}
					
					if(dataSearchTO.getPerAddressStateDisp() != null && dataSearchTO.getPerAddressStateDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getPerAddressState()!= null){
						row.createCell((short)dataSearchTO.getPerAddressStatePois()).setCellValue(exportDataSearchTO.getPerAddressState());
					}
					
					if(dataSearchTO.getPerAddressZipDisp() != null && dataSearchTO.getPerAddressZipDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getPerAddressZip()!= null){
						row.createCell((short)dataSearchTO.getPerAddressZipPois()).setCellValue(exportDataSearchTO.getPerAddressZip());
					}								
						
		
					if(dataSearchTO.getCurrentAddressLine1Disp() != null && dataSearchTO.getCurrentAddressLine1Disp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getCurrentAddressline1()!=null){
						row.createCell((short)dataSearchTO.getCurrentAddressLine1Pois()).setCellValue(exportDataSearchTO.getCurrentAddressline1());
					}
					if(dataSearchTO.getCurrentAddressLine2Disp() != null && dataSearchTO.getCurrentAddressLine2Disp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getCurrentAddressline2()!=null){
						row.createCell((short)dataSearchTO.getCurrentAddressLine2Pois()).setCellValue(exportDataSearchTO.getCurrentAddressline2());
					}
					if(dataSearchTO.getCurAddressCityDisp() != null && dataSearchTO.getCurAddressCityDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getCurAddressCity()!= null){
						row.createCell((short)dataSearchTO.getCurAddressCityPois()).setCellValue(exportDataSearchTO.getCurAddressCity());
					}
					
					if(dataSearchTO.getCurAddressCountryDisp() != null && dataSearchTO.getCurAddressCountryDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getCurAddressCountry()!= null){
						row.createCell((short)dataSearchTO.getCurAddressCountryPois()).setCellValue(exportDataSearchTO.getCurAddressCountry());
					}
					
					if(dataSearchTO.getCurAddressStateDisp() != null && dataSearchTO.getCurAddressStateDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getCurAddressState()!= null){
						row.createCell((short)dataSearchTO.getCurAddressStatePois()).setCellValue(exportDataSearchTO.getCurAddressState());
					}
					
					if(dataSearchTO.getCurAddressZipDisp() != null && dataSearchTO.getCurAddressZipDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getCurAddressZip()!= null){
						row.createCell((short)dataSearchTO.getCurAddressZipPois()).setCellValue(exportDataSearchTO.getCurAddressZip());
					}					
					
					
					if(dataSearchTO.getDateOfBirthDisp() != null && dataSearchTO.getDateOfBirthDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getDateOfBirth()!= null){
					    cell = row.createCell((short)dataSearchTO.getDateOfBirthPois());
					    cell.setCellValue(exportDataSearchTO.getDateOfBirth());
					    cell.setCellStyle(cellStyle);
					}
					if(dataSearchTO.getBloodGroupDisp() != null && dataSearchTO.getBloodGroupDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getBloodGroup()!= null){
						row.createCell((short)dataSearchTO.getBloodGroupPois()).setCellValue(exportDataSearchTO.getBloodGroup());
					}
					if(dataSearchTO.getPhoneNoDisp() != null && dataSearchTO.getPhoneNoDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getPhoneNo()!= null){
						row.createCell((short)dataSearchTO.getPhoneNoPois()).setCellValue(exportDataSearchTO.getPhoneNo());
					}
					if(dataSearchTO.getMobileNoDisp() != null && dataSearchTO.getMobileNoDisp().equalsIgnoreCase(DISPLAY) && exportDataSearchTO.getMobileNo()!= null){
						row.createCell((short)dataSearchTO.getMobileNoPois()).setCellValue(exportDataSearchTO.getMobileNo());
					}
					
					if(exportDataSearchTO.getClassCode()!= null){
						row.createCell((short)dataSearchTO.getClassCodePois()).setCellValue(exportDataSearchTO.getClassCode());
					}
					row.createCell((short)dataSearchTO.getValidTillPos()).setCellValue(validTill);
					row.createCell((short)34).setCellValue("");
					
					
				
				}//end for checking for selected or unselected
			
				}
			bos=new ByteArrayOutputStream();
			wb.write(bos);
			HttpSession session = request.getSession();
			
		
			session.setAttribute(CMSConstants.ID_DATA_ATTR,bos.toByteArray());
			fos.flush();
			fos.close();
		}
		}catch(Exception e){
			
				/*fos = new FileOutputStream(excelFile,true);
				wb.write(fos);
				HttpSession session = request.getSession(false);
				if(session != null){
					session.setAttribute(CMSConstants.ID_DATA_ATTR, wb.getBytes());
				}
				fos.flush();
				fos.close();
			}
		}catch(Exception e){*/
			
		}
		
		log.info("exit of convertListToExcel method in ExportDataSearchHandler class");
		}
	
	/**
	 * This method is used to set the column display and position in TO.
	 * @return
	 * @throws Exception
	 */
	
	public ExportDataSearchTO setDisplayForFields() throws Exception{
		log.info("entering of setDisplayForFields method in ExportDataSearchHandler class");
		ExportDataSearchTO exportDataSearchTO = new ExportDataSearchTO();
		
			exportDataSearchTO.setRegNoDisp(DISPLAY);
			exportDataSearchTO.setRegNoPois((short)0);
			exportDataSearchTO.setRollNoDisp(DISPLAY);
			exportDataSearchTO.setRollNoPois((short)1);
			exportDataSearchTO.setApplnNoDisp(DISPLAY);
			exportDataSearchTO.setApplnNoPois((short)2);
			exportDataSearchTO.setCourseCodeDisp(DISPLAY);
			exportDataSearchTO.setCourseCodePois((short)3);
			exportDataSearchTO.setCourseNameDisp(DISPLAY);
			exportDataSearchTO.setCourseNamePois((short)4);
			exportDataSearchTO.setNameDisp(DISPLAY);
			exportDataSearchTO.setNamePois((short)5);
			exportDataSearchTO.setAcedamicYearDisp(DISPLAY);
			exportDataSearchTO.setAcedamicYearPois((short)6);
			exportDataSearchTO.setSemesterNameDisp(DISPLAY);
			exportDataSearchTO.setSemesterNamePois((short)7);
			exportDataSearchTO.setFathersNameDisp(DISPLAY);
			exportDataSearchTO.setFathersNamePois((short)8);
			exportDataSearchTO.setMothersNameDisp(DISPLAY);
			exportDataSearchTO.setMothersNamePois((short)9);
			exportDataSearchTO.setSecondLanguageDisp(DISPLAY);
			exportDataSearchTO.setSecondLanguagePois((short)10);
			exportDataSearchTO.setReligionDisp(DISPLAY);
			exportDataSearchTO.setReligionPois((short)11);
			exportDataSearchTO.setGenderDisp(DISPLAY);
			exportDataSearchTO.setGenderPois((short)12);
			exportDataSearchTO.setNationalityDisp(DISPLAY);
			exportDataSearchTO.setNationalityPois((short)13);
			exportDataSearchTO.setParentPhoneDisp(DISPLAY);
			exportDataSearchTO.setParentPhonePois((short)14);
			exportDataSearchTO.setParentMobileNoDisp(DISPLAY);
			exportDataSearchTO.setParentMobileNoPois((short)15);

			exportDataSearchTO.setCurrentAddressLine1Disp(DISPLAY);
			exportDataSearchTO.setCurrentAddressLine1Pois((short)16);
			exportDataSearchTO.setCurrentAddressLine2Disp(DISPLAY);
			exportDataSearchTO.setCurrentAddressLine2Pois((short)17);
			
			exportDataSearchTO.setCurAddressCityDisp(DISPLAY);
			exportDataSearchTO.setCurAddressCityPois((short)18);
			exportDataSearchTO.setCurAddressCountryDisp(DISPLAY);
			exportDataSearchTO.setCurAddressCountryPois((short)19);
			exportDataSearchTO.setCurAddressStateDisp(DISPLAY);
			exportDataSearchTO.setCurAddressStatePois((short)20);
			exportDataSearchTO.setCurAddressZipDisp(DISPLAY);
			exportDataSearchTO.setCurAddressZipPois((short)21);			
			
			
			exportDataSearchTO.setPermanentAddressLine1Disp(DISPLAY);
			exportDataSearchTO.setPermanentAddressLine1Pois((short)22);
			exportDataSearchTO.setPermanentAddressLine2Disp(DISPLAY);
			exportDataSearchTO.setPermanentAddressLine2Pois((short)23);
			
			exportDataSearchTO.setPerAddressCityDisp(DISPLAY);
			exportDataSearchTO.setPerAddressCityPois((short)24);
			exportDataSearchTO.setPerAddressCountryDisp(DISPLAY);
			exportDataSearchTO.setPerAddressCountryPois((short)25);
			exportDataSearchTO.setPerAddressStateDisp(DISPLAY);
			exportDataSearchTO.setPerAddressStatePois((short)26);
			exportDataSearchTO.setPerAddressZipDisp(DISPLAY);
			exportDataSearchTO.setPerAddressZipPois((short)27);
			
			exportDataSearchTO.setDateOfBirthDisp(DISPLAY);
			exportDataSearchTO.setDateOfBirthPois((short)28);
			exportDataSearchTO.setBloodGroupDisp(DISPLAY);
			exportDataSearchTO.setBloodGroupPois((short)29);
			exportDataSearchTO.setPhoneNoDisp(DISPLAY);
			exportDataSearchTO.setPhoneNoPois((short)30);
			exportDataSearchTO.setMobileNoDisp(DISPLAY);
			exportDataSearchTO.setMobileNoPois((short)31);
			exportDataSearchTO.setClassCodePois((short)32);
			exportDataSearchTO.setValidTillPos((short)33);
			
			
		log.info("exit of setDisplayForFields method in ExportDataSearchHandler class");
		return exportDataSearchTO;
	}
}