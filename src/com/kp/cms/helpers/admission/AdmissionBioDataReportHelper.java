package com.kp.cms.helpers.admission;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AdmBioDataCJC;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.AdmissionBioDataReportForm;
import com.kp.cms.to.admin.AdmBioDataCJCTo;
import com.kp.cms.to.admission.AdmissionBioDataReportTo;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class AdmissionBioDataReportHelper {
	private static volatile AdmissionBioDataReportHelper admissionBioDataReportHelper = null;
	private static final String DISPLAY = "display";
	public static AdmissionBioDataReportHelper getInstance(){
		if(admissionBioDataReportHelper == null){
			admissionBioDataReportHelper = new AdmissionBioDataReportHelper();
			return admissionBioDataReportHelper;
		}
		return admissionBioDataReportHelper;
		
	}
	/**
	 * @param dataMigrationForm
	 * @return
	 * @throws Exception
	 */
	public StringBuffer selectedQuery( AdmissionBioDataReportForm dataMigrationForm) throws Exception{
		StringBuffer stringBuffer = new StringBuffer("from AdmBioDataCJC admBio where");
		if(dataMigrationForm.getAcademicYear()!=null && !dataMigrationForm.getAcademicYear().isEmpty()){
			stringBuffer = stringBuffer.append(" admBio.academicYear='"+dataMigrationForm.getAcademicYear()+"'");
		}
			Session session = null;
			Transaction tx =null;
			try{
				session = HibernateUtil.getSession();
				tx=session.beginTransaction();
				stringBuffer = stringBuffer.append(" and admBio.classes='"+dataMigrationForm.getCourseName()+"'");
			
			}catch (Exception e) {
				throw new ApplicationException(e);
			
		}
		return stringBuffer;
	}
	/**
	 * @param admBioDataCJCList
	 * @return
	 * @throws Exception
	 */
	public List<AdmBioDataCJCTo> populateBOToTO( List<AdmBioDataCJC> admBioDataCJCList) throws Exception{
		List<AdmBioDataCJCTo> admBioToList = new ArrayList<AdmBioDataCJCTo>();
		if(admBioDataCJCList!=null && !admBioDataCJCList.isEmpty()){
			Iterator<AdmBioDataCJC> iterator = admBioDataCJCList.iterator();
			while (iterator.hasNext()) {
				AdmBioDataCJC admBioData = (AdmBioDataCJC) iterator.next();
				AdmBioDataCJCTo admBioDataCJCTo = new AdmBioDataCJCTo();
				if(admBioData.getApplnNo()>0){
					admBioDataCJCTo.setApplnNo(String.valueOf(admBioData.getApplnNo()));
				}
				if(admBioData.getRegNo()!=null && !admBioData.getRegNo().isEmpty()){
					admBioDataCJCTo.setRegNo(admBioData.getRegNo());
				}
				if(admBioData.getName()!=null && !admBioData.getName().isEmpty()){
					admBioDataCJCTo.setName(admBioData.getName());
				}
				String classes = "";
				if(admBioData.getClasses()!=null && !admBioData.getClasses().isEmpty()){
					String cls = admBioData.getClasses();
					classes = classes + cls;
					if(admBioData.getYear()!=null && !admBioData.getYear().isEmpty()){
						String year = admBioData.getYear();
						classes = year + classes; 
					}
					if(admBioData.getSection()!=null && !admBioData.getSection().isEmpty()){
						String section = admBioData.getSection();
						classes = classes + section;
					}
					admBioDataCJCTo.setClasses(admBioData.getClasses());
				}
				if(admBioData.getFatherName()!=null && !admBioData.getFatherName().isEmpty()){
					admBioDataCJCTo.setFatherName(admBioData.getFatherName());
				}
				if(admBioData.getReligion()!=null && !admBioData.getReligion().isEmpty()){
					admBioDataCJCTo.setReligion(admBioData.getReligion());
				}
				if(admBioData.getCaste()!=null && !admBioData.getCaste().isEmpty()){
					admBioDataCJCTo.setCaste(admBioData.getCaste());
				}
				if(admBioData.getScStBcBt()!=null && !admBioData.getScStBcBt().isEmpty()){
					admBioDataCJCTo.setScStBcBt(admBioData.getScStBcBt());
				}
				if(admBioData.getDob()!=null && !admBioData.getDob().toString().isEmpty()){
					admBioDataCJCTo.setDateOfBirth(CommonUtil.getStringDate(admBioData.getDob()));
				}
				if(admBioData.getSex()!=null && !admBioData.getSex().isEmpty()){
					admBioDataCJCTo.setSex(admBioData.getSex());
				}
				if(admBioData.getNationality()!=null && !admBioData.getNationality().isEmpty()){
					admBioDataCJCTo.setNationality(admBioData.getNationality());
				}
				if(admBioData.getState()!=null && !admBioData.getState().isEmpty()){
					admBioDataCJCTo.setState(admBioData.getState());
				}
				if(admBioData.getPercentage()!=null && !admBioData.getPercentage().toString().isEmpty()){
					admBioDataCJCTo.setPercentage(String.valueOf(admBioData.getPercentage()));
				}
				String str= "";
				if(admBioData.getAddress1()!=null && !admBioData.getAddress1().isEmpty()){
					String str1= admBioData.getAddress1();
					str= str+str1+",";
				}
				if(admBioData.getAddress2()!=null && !admBioData.getAddress2().isEmpty()){
					String str2 = admBioData.getAddress2();
					str= str + str2+",";
				}
				if(admBioData.getAddress3()!=null && !admBioData.getAddress3().isEmpty()){
					String str3= admBioData.getAddress3();
					str= str + str3 +",";
				}
				if(admBioData.getAddress4()!=null && !admBioData.getAddress4().isEmpty()){
					String str4 =admBioData.getAddress4();
					str =str + str4 ;
				}
				admBioDataCJCTo.setAddress1(str);
				if(admBioData.getTelephone()!=null && !admBioData.getTelephone().isEmpty()){
					admBioDataCJCTo.setTelephone(admBioData.getTelephone());
				}
				if(admBioData.getDateAdm()!=null && !admBioData.getDateAdm().toString().isEmpty()){
					admBioDataCJCTo.setAdmDate(CommonUtil.getStringDate(admBioData.getDateAdm()));
				}
				if(admBioData.getPlaceBirth()!=null && !admBioData.getPlaceBirth().isEmpty()){
					admBioDataCJCTo.setPlaceBirth(admBioData.getPlaceBirth());
				}
				if(admBioData.getStateBirth()!= null && !admBioData.getStateBirth().isEmpty()){
					admBioDataCJCTo.setStateBirth(admBioData.getStateBirth());
				}
				if(admBioData.getCountry()!=null && !admBioData.getCountry().isEmpty()){
					admBioDataCJCTo.setCountry(admBioData.getCountry());
				}
				if(admBioData.getAnnIncome()!=null && !admBioData.getAnnIncome().toString().isEmpty()){
					admBioDataCJCTo.setAnnIncome(admBioData.getAnnIncome());
				}
				if(admBioData.getPassportNo()!=null && !admBioData.getPassportNo().isEmpty()){
					admBioDataCJCTo.setPassportNo(admBioData.getPassportNo());
				}
				if(admBioData.getPlaceIssued()!=null && !admBioData.getPlaceIssued().isEmpty()){
					admBioDataCJCTo.setPlaceIssued(admBioData.getPlaceIssued());
				}
				if(admBioData.getAdmitCat()!=null && !admBioData.getAdmitCat().isEmpty()){
					admBioDataCJCTo.setAdmitCat(admBioData.getAdmitCat());
				}
				if(admBioData.isForeign() && admBioData.isForeign()!=null){
					admBioDataCJCTo.setForeign("TRUE");
				}else{
					admBioDataCJCTo.setForeign("FALSE");
				}
				if(admBioData.isNri() && admBioData.isNri()!=null){
					admBioDataCJCTo.setNri("TRUE");
				}else{
					admBioDataCJCTo.setNri("FALSE");
				}
				if(admBioData.getStudentNo()!=null && !admBioData.getStudentNo().isEmpty()){
					admBioDataCJCTo.setStudentNo(admBioData.getStudentNo());
				}
				if(admBioData.getExamRegNo()!=null && !admBioData.getExamRegNo().isEmpty()){
					admBioDataCJCTo.setExamRegNo(admBioData.getExamRegNo());
				}
				if(admBioData.getBloodGroup()!=null && !admBioData.getBloodGroup().isEmpty()){
					admBioDataCJCTo.setBloodGroup(admBioData.getBloodGroup());
				}
				
				if(admBioData.issEvnyKar()!=null){
					if( !admBioData.issEvnyKar().toString().isEmpty()){
						if(admBioData.issEvnyKar()){
							admBioDataCJCTo.setsEvnyKar("TRUE");
						}else{
							admBioDataCJCTo.setsEvnyKar("FALSE");
						}
					}
				}else{
					
					admBioDataCJCTo.setsEvnyKar(" ");
				}
				
				if(admBioData.getMedmInstr()!=null && !admBioData.getMedmInstr().isEmpty()){
					admBioDataCJCTo.setMedmInstr(admBioData.getMedmInstr());
				}
				if(admBioData.getMobileNumber()!=null && !admBioData.getMobileNumber().isEmpty()){
					admBioDataCJCTo.setMobileNumber(admBioData.getMobileNumber());
				}
				if(admBioData.getSecondLanguage()!=null && !admBioData.getSecondLanguage().isEmpty()){
					admBioDataCJCTo.setSecondLanguage(admBioData.getSecondLanguage());
				}
				if(admBioData.getLastInst()!=null && !admBioData.getLastInst().isEmpty()){
					admBioDataCJCTo.setLastInst(admBioData.getLastInst());
				}
				if(admBioData.getExamPassed()!=null && !admBioData.getExamPassed().isEmpty()){
					admBioDataCJCTo.setExamPassed(admBioData.getExamPassed());
				}
				if(admBioData.getYearPass()!=0){
					admBioDataCJCTo.setYearPass(String.valueOf(admBioData.getYearPass()));
				}
				if(admBioData.getMonthPass()!=null && !admBioData.getMonthPass().isEmpty()){
					admBioDataCJCTo.setMonthPass(admBioData.getMonthPass());
				}
				if(admBioData.getExRegNo()!=null && !admBioData.getExRegNo().isEmpty()){
					admBioDataCJCTo.setExRegNo(admBioData.getExRegNo());
				}
				if(admBioData.getAdmTcNo()!=null && !admBioData.getAdmTcNo().isEmpty()){
					admBioDataCJCTo.setAdmTcNo(admBioData.getAdmTcNo());
				}
				if(admBioData.getAdmTcDt()!=null && !admBioData.getAdmTcDt().toString().isEmpty()){
					admBioDataCJCTo.setAdmTcDt1(CommonUtil.getStringDate(admBioData.getAdmTcDt()));
				}
				if(admBioData.getfOccupation()!=null && !admBioData.getfOccupation().isEmpty()){
					admBioDataCJCTo.setfOccupation(admBioData.getfOccupation());
				}
				if(admBioData.getAcademicYear()!=null && !admBioData.getAcademicYear().isEmpty()){
					admBioDataCJCTo.setAcademicYear(admBioData.getAcademicYear());
				}
				admBioToList.add(admBioDataCJCTo);
			}
		}
		return admBioToList;
	}
	/**
	 * @param dataMigrationForm
	 * @return
	 */
	public AdmissionBioDataReportTo selectedColumns( AdmissionBioDataReportForm dataMigrationForm) throws Exception{
		AdmissionBioDataReportTo reportTO = new AdmissionBioDataReportTo();

		List<String> selectedColumnsList = new ArrayList<String>();
		String[] selected = dataMigrationForm.getSelectedColumnsArray();
		for (int i = 0; i < selected.length; i++) {
			selectedColumnsList.add(selected[i]);
		}
		if (selectedColumnsList != null) {
			selectedColumnsList.add("Academic Year");
			Iterator<String> iterator = selectedColumnsList.iterator();
			int count = 0;
			while (iterator.hasNext()) {

				String columnName = iterator.next();
				if (columnName.equalsIgnoreCase("Appln No")) {
					reportTO.setApplnNoDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setApplnNoPos((short)count++);
				}
				if (columnName.equalsIgnoreCase("Name")) {
					reportTO.setNameDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setNamePos((short)count++);
				}
				if (columnName.equalsIgnoreCase("Reg No")) {
					reportTO.setRegNoDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setRegNoPos((short)count++);
				}
				if (columnName.equalsIgnoreCase("Class")) {
					reportTO.setClassDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setClassPos((short)count++);
				}
				if (columnName.equalsIgnoreCase("Father Name")) {
					reportTO.setFatherNameDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setFatherNamePos((short)count++);
				}
				if (columnName.equalsIgnoreCase("Religion")) {
					reportTO.setReligionDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setReligionPos((short)count++);
				}
				if (columnName.equalsIgnoreCase("Caste")) {
					reportTO .setCasteDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setCastePos((short)count++);
				}
				if (columnName.equalsIgnoreCase("Category")) {
					reportTO.setCategoryDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setCategoryPos((short)count++);
				}
				if (columnName.equalsIgnoreCase("Date of Birth")) {
					reportTO.setDobDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setDobPos((short)count++);
				}
				if (columnName.equalsIgnoreCase("Gender")) {
					reportTO.setGenderDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setGenderPos((short)count++);
				}
				if (columnName.equalsIgnoreCase("Nationality")) {
					reportTO.setNationalityDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setNationalityPos((short)count++);
				}
				if (columnName.equalsIgnoreCase("State")) {
					reportTO .setStateDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setStatePos((short)count++);
				}
				if (columnName.equalsIgnoreCase("Percentage")) {
					reportTO.setPercentDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setPercentPos((short)count++);
				}
				if (columnName.equalsIgnoreCase("Address")) {
					reportTO.setAddressDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setAddressPos((short)count++);
				}
				if (columnName.equalsIgnoreCase("Telephone")) {
					reportTO.setTelphoneDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setTelphonePos((short)count++);
				}
				if (columnName.equalsIgnoreCase("Adm Date")) {
					reportTO.setDateAdmDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setDateAdmPos((short)count++);
				}
				if (columnName.equalsIgnoreCase("Place Birth")) {
					reportTO.setPlaceBirthDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setPlaceBirthPos((short)count++);
				}
				if (columnName.equalsIgnoreCase("State Birth")) {
					reportTO.setStateBirthDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setStateBirthPos((short)count++);
				}
				if (columnName.equalsIgnoreCase("Country")) {
					reportTO.setCountryDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setCountryPos((short)count++);
				}
				if (columnName.equalsIgnoreCase("Annual Income")) {
					reportTO.setAnnIncomeDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setAnnIncomePos((short)count++);
				}
				if (columnName.equalsIgnoreCase("PassPort No")) {
					reportTO .setPassPortNoDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setPassPortNoPos((short)count++);
				}
				if (columnName.equalsIgnoreCase("Place Issue")) {
					reportTO .setPlaceIssueDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setPlaceIssuePos((short)count++);
				}
				if (columnName.equalsIgnoreCase("Admt Cat")) {
					reportTO .setAdmtCatDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO .setAdmtCatPos((short)count++);
					
				}
				if (columnName.equalsIgnoreCase("Foreign Student")) {
					reportTO .setForeignDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setForeignPos((short)count++);
				}
				if (columnName.equalsIgnoreCase("NRI Student")) {
					reportTO.setNriDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setNriPos((short)count++);
				}
				if (columnName.equalsIgnoreCase("Student No")) {
					reportTO.setStuNoDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setStuNoPos((short)count++);
				}
				if (columnName.equalsIgnoreCase("Exam RegNo")) {
					reportTO.setExamRegNoDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setExamRegNoPos((short)count++);
				}
				if (columnName.equalsIgnoreCase("Blood Group")) {
					reportTO.setBloodGroupDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setBloodGroupPos((short)count++);
				}
				if (columnName.equalsIgnoreCase("Seven years of Karnataka")) {
					reportTO.setSvnYrKarDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setSvnYrKarPos((short)count++);
				}
				if (columnName.equalsIgnoreCase("Medm Instr")) {
					reportTO.setMedmInstrDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setMedmInstrPos((short)count++);
				}
				if (columnName.equalsIgnoreCase("Mobile No")) {
					reportTO.setMobNoDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setMobNoPos((short)count++);
				}
				if (columnName.equalsIgnoreCase("Second Language")) {
					reportTO.setSecondLanDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setSecondLanPos((short)count++);
				}
				if (columnName.equalsIgnoreCase("Previous Qualification Details")) {
					reportTO.setLastInstDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setLastInstPos((short)count++);
					reportTO.setExamRegNoDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setExamRegNoPos((short)count++);
					reportTO.setExamPassedDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setExamPassedPos((short)count++);
					reportTO.setYearPassDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setYearPassPos((short)count++);
					reportTO.setMonthPassDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setMonthPassPos((short)count++);
					reportTO.setTcNoDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setTcNoPos((short)count++);
					reportTO.setTcDateDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setTcDatePos((short)count++);
					reportTO.setfOccupationDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setfOccupationPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Academic Year")){
					reportTO.setAcademicYearDis(AdmissionBioDataReportHelper.DISPLAY);
					reportTO.setAcademicYearPos((short)count++);
				}
			}
		}
		return reportTO;
	}
	/**
	 * @param admBioTo
	 * @param dataMigrationForm
	 * @param request 
	 * @return
	 * @throws IOException 
	 */
	public boolean convertToTOExcel(AdmissionBioDataReportTo admBioTo, AdmissionBioDataReportForm dataMigrationForm, HttpServletRequest request) throws IOException {
		boolean isUpdated=false;
		Properties prop = new Properties();
		try {
			InputStream inputStream = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inputStream);
		} 
		catch (IOException e) {
			throw new IOException(e);
		}
		String fileName=prop.getProperty(CMSConstants.UPLOAD_ADM_BIODATA);
		File excelFile = new File(request.getRealPath("")+ "//TempFiles//"+fileName);
		if(excelFile.exists()){
			excelFile.delete();
		}
		FileOutputStream fos = null;
		ByteArrayOutputStream bos=null;
		XSSFWorkbook wb=null;
		XSSFSheet sheet=null;
		XSSFRow row=null;
		XSSFCell cell=null;
		
		if(dataMigrationForm.getAdmBioDataList()!=null){
			AdmBioDataCJCTo to=(AdmBioDataCJCTo)dataMigrationForm.getAdmBioDataList().get(0);
			int count = 0;
			Iterator<AdmBioDataCJCTo> iterator = dataMigrationForm.getAdmBioDataList().iterator();
			try{
				wb=new XSSFWorkbook();
				XSSFCellStyle cellStyle=wb.createCellStyle();
				CreationHelper createHelper = wb.getCreationHelper();
				cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
				sheet = wb.createSheet("Admission BioData Report");
				row = sheet.createRow(count);
				count = sheet.getFirstRowNum();
				// Create cells in the row and put some data in it.
				if(admBioTo.getApplnNoDis()!=null && admBioTo.getApplnNoDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getApplnNoPos()).setCellValue("Appln No");
				}
				if(admBioTo.getRegNoDis()!=null && admBioTo.getRegNoDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getRegNoPos()).setCellValue("Reg No");
				}
				if(admBioTo.getNameDis()!=null && admBioTo.getNameDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getNamePos()).setCellValue("Name");
				}
				if(admBioTo.getClassDis()!=null && admBioTo.getClassDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getClassPos()).setCellValue("Class");
				}
				if(admBioTo.getFatherNameDis()!=null && admBioTo.getFatherNameDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getFatherNamePos()).setCellValue("Father Name");
				}
				if(admBioTo.getReligionDis()!=null && admBioTo.getReligionDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getReligionPos()).setCellValue("Religion");
				}
				if(admBioTo.getCasteDis()!=null && admBioTo.getCasteDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getCastePos()).setCellValue("Caste");
				}
				if(admBioTo.getCategoryDis()!=null && admBioTo.getCategoryDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getCategoryPos()).setCellValue("Category");
				}
				if(admBioTo.getDobDis()!=null && admBioTo.getDobDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getDobPos()).setCellValue("Date of Birth");
				}
				if(admBioTo.getGenderDis()!=null && admBioTo.getGenderDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getGenderPos()).setCellValue("Gender");
				}
				if(admBioTo.getNationalityDis()!=null && admBioTo.getNationalityDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getNationalityPos()).setCellValue("Nationality");
				}
				if(admBioTo.getStateDis()!=null && admBioTo.getStateDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getStatePos()).setCellValue("State");
				}
				if(admBioTo.getPercentDis()!=null && admBioTo.getPercentDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getPercentPos()).setCellValue("Percentage");
				}
				if(admBioTo.getAddressDis()!=null && admBioTo.getAddressDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getAddressPos()).setCellValue("Address");
				}
				if(admBioTo.getTelphoneDis()!=null && admBioTo.getTelphoneDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getTelphonePos()).setCellValue("Telephone");
				}
				if(admBioTo.getDateAdmDis()!=null && admBioTo.getDateAdmDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getDateAdmPos()).setCellValue("Date Adm");
				}
				if(admBioTo.getPlaceBirthDis()!=null && admBioTo.getPlaceBirthDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getPlaceBirthPos()).setCellValue("Place Birth");
				}
				if(admBioTo.getStateBirthDis()!=null && admBioTo.getStateBirthDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getStateBirthPos()).setCellValue("State Birth");
				}
				if(admBioTo.getCountryDis()!=null && admBioTo.getCountryDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getCountryPos()).setCellValue("Country");
				}
				if(admBioTo.getAnnIncomeDis()!=null && admBioTo.getAnnIncomeDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getAnnIncomePos()).setCellValue("Annual Income");
				}
				if(admBioTo.getPassPortNoDis()!=null && admBioTo.getPassPortNoDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getPassPortNoPos()).setCellValue("Passport No");
				}
				if(admBioTo.getPlaceIssueDis()!=null && admBioTo.getPlaceIssueDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getPlaceIssuePos()).setCellValue("Place Issue");
				}
				if(admBioTo.getAdmtCatDis()!=null && admBioTo.getAdmtCatDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getAdmtCatPos()).setCellValue("Admt Cat");
				}
				if(admBioTo.getForeignDis()!=null && admBioTo.getForeignDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getForeignPos()).setCellValue("Foreign Student");
				}
				if(admBioTo.getNriDis()!=null && admBioTo.getNriDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getNriPos()).setCellValue("NRI Student");
				}
				if(admBioTo.getStuNoDis()!=null && admBioTo.getStuNoDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getStuNoPos()).setCellValue("Student No");
				}
				if(admBioTo.getBloodGroupDis()!=null && admBioTo.getBloodGroupDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getBloodGroupPos()).setCellValue("Blood Group");
				}
				if(admBioTo.getSvnYrKarDis()!=null && admBioTo.getSvnYrKarDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getSvnYrKarPos()).setCellValue("SvnYrKar");
				}
				if(admBioTo.getMedmInstrDis()!=null && admBioTo.getMedmInstrDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getMedmInstrPos()).setCellValue("Medm Instr");
				}
				if(admBioTo.getMobNoDis()!=null && admBioTo.getMobNoDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getMobNoPos()).setCellValue("Mobile No");
				}
				if(admBioTo.getSecondLanDis()!=null && admBioTo.getSecondLanDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getSecondLanPos()).setCellValue("Second Language");
				}
				if(admBioTo.getLastInstDis()!=null && admBioTo.getLastInstDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getLastInstPos()).setCellValue("Previous Institute");
				}
				if(admBioTo.getExamPassedDis()!=null && admBioTo.getExamPassedDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getExamPassedPos()).setCellValue("Previous Exam Passed");
				}
				if(admBioTo.getExamRegNoDis()!=null && admBioTo.getExamRegNoDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getExamRegNoPos()).setCellValue("Exam RegNo(10th)");
				}
				if(admBioTo.getYearPassDis()!=null && admBioTo.getYearPassDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getYearPassPos()).setCellValue("Year Pass");
				}
				if(admBioTo.getMonthPassDis()!=null && admBioTo.getMonthPassDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getMonthPassPos()).setCellValue("Month Pass");
				}
				if(admBioTo.getTcNoDis()!=null && admBioTo.getTcNoDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getTcNoPos()).setCellValue("Tc No(10th)");
				}
				if(admBioTo.getTcDateDis()!=null && admBioTo.getTcDateDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getTcDatePos()).setCellValue("Tc Date(10th)");
				}
				if(admBioTo.getfOccupationDis()!=null && admBioTo.getfOccupationDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getfOccupationPos()).setCellValue("Father Occupation");
				}
				if(admBioTo.getAcademicYearDis()!=null && admBioTo.getAcademicYearDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY)){
					row.createCell((short)admBioTo.getAcademicYearPos()).setCellValue("Academic Year");
				}
				while (iterator.hasNext()) {
					AdmBioDataCJCTo dataTo = (AdmBioDataCJCTo) iterator.next();
					count = count +1;
					row = sheet.createRow(count);
					if(admBioTo.getApplnNoDis()!=null && admBioTo.getApplnNoDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getApplnNo()!=null){
						row.createCell((short)admBioTo.getApplnNoPos()).setCellValue(dataTo.getApplnNo());
					}
					if(admBioTo.getRegNoDis()!=null && admBioTo.getRegNoDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getRegNo()!=null){
						row.createCell((short)admBioTo.getRegNoPos()).setCellValue(dataTo.getRegNo());
					}
					if(admBioTo.getNameDis()!=null && admBioTo.getNameDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getName()!=null){
						row.createCell((short)admBioTo.getNamePos()).setCellValue(dataTo.getName());
					}
					if(admBioTo.getClassDis()!=null && admBioTo.getClassDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getClasses()!=null){
						row.createCell((short)admBioTo.getClassPos()).setCellValue(dataTo.getClasses());
					}
					if(admBioTo.getFatherNameDis()!=null && admBioTo.getFatherNameDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getFatherName()!=null){
						row.createCell((short)admBioTo.getFatherNamePos()).setCellValue(dataTo.getFatherName());
					}
					if(admBioTo.getReligionDis()!=null && admBioTo.getReligionDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getReligion()!=null){
						row.createCell((short)admBioTo.getReligionPos()).setCellValue(dataTo.getReligion());
					}
					if(admBioTo.getCasteDis()!=null && admBioTo.getCasteDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getCaste()!=null){
						row.createCell((short)admBioTo.getCastePos()).setCellValue(dataTo.getCaste());
					}
					if(admBioTo.getCategoryDis()!=null && admBioTo.getCategoryDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getScStBcBt()!=null){
						row.createCell((short)admBioTo.getCategoryPos()).setCellValue(dataTo.getScStBcBt());
					}
					if(admBioTo.getDobDis()!=null && admBioTo.getDobDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getDateOfBirth()!=null){
						row.createCell((short)admBioTo.getDobPos()).setCellValue(dataTo.getDateOfBirth());
					}
					if(admBioTo.getGenderDis()!=null && admBioTo.getGenderDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getSex()!=null){
						row.createCell((short)admBioTo.getGenderPos()).setCellValue(dataTo.getSex());
					}
					if(admBioTo.getNationalityDis()!=null && admBioTo.getNationalityDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getNationality()!=null){
						row.createCell((short)admBioTo.getNationalityPos()).setCellValue(dataTo.getNationality());
					}
					if(admBioTo.getNationalityDis()!=null && admBioTo.getNationalityDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getNationality()!=null){
						row.createCell((short)admBioTo.getNationalityPos()).setCellValue(dataTo.getNationality());
					}
					if(admBioTo.getStateDis()!=null && admBioTo.getStateDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getState()!=null){
						row.createCell((short)admBioTo.getStatePos()).setCellValue(dataTo.getState());
					}
					if(admBioTo.getPercentDis()!=null && admBioTo.getPercentDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getPercentage()!=null){
						row.createCell((short)admBioTo.getPercentPos()).setCellValue(dataTo.getPercentage());
					}
					if(admBioTo.getAddressDis()!=null && admBioTo.getAddressDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getAddress1()!=null){
						row.createCell((short)admBioTo.getAddressPos()).setCellValue(dataTo.getAddress1());
					}
					if(admBioTo.getTelphoneDis()!=null && admBioTo.getTelphoneDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getTelephone()!=null){
						row.createCell((short)admBioTo.getTelphonePos()).setCellValue(dataTo.getTelephone());
					}
					if(admBioTo.getDateAdmDis()!=null && admBioTo.getDateAdmDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getAdmDate()!=null){
						row.createCell((short)admBioTo.getDateAdmPos()).setCellValue(dataTo.getAdmDate());
					}
					if(admBioTo.getPlaceBirthDis()!=null && admBioTo.getPlaceBirthDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getPlaceBirth()!=null){
						row.createCell((short)admBioTo.getPlaceBirthPos()).setCellValue(dataTo.getPlaceBirth());
					}
					if(admBioTo.getStateBirthDis()!=null && admBioTo.getStateBirthDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getStateBirth()!=null){
						row.createCell((short)admBioTo.getStateBirthPos()).setCellValue(dataTo.getStateBirth());
					}
					if(admBioTo.getCountryDis()!=null && admBioTo.getCountryDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getCountry()!=null){
						row.createCell((short)admBioTo.getCountryPos()).setCellValue(dataTo.getCountry());
					}
					if(admBioTo.getAnnIncomeDis()!=null && admBioTo.getAnnIncomeDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getAnnIncome()!=null){
						row.createCell((short)admBioTo.getAnnIncomePos()).setCellValue(dataTo.getAnnIncome());
					}
					if(admBioTo.getPassPortNoDis()!=null && admBioTo.getPassPortNoDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getPassportNo()!=null){
						row.createCell((short)admBioTo.getPassPortNoPos()).setCellValue(dataTo.getPassportNo());
					}
					if(admBioTo.getPlaceIssueDis()!=null && admBioTo.getPlaceIssueDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getPlaceIssued()!=null){
						row.createCell((short)admBioTo.getPlaceIssuePos()).setCellValue(dataTo.getPlaceIssued());
					}
					if(admBioTo.getAdmtCatDis()!=null && admBioTo.getAdmtCatDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getAdmitCat()!=null){
						row.createCell((short)admBioTo.getAdmtCatPos()).setCellValue(dataTo.getAdmitCat());
					}
					if(admBioTo.getForeignDis()!=null && admBioTo.getForeignDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getForeign()!=null){
						row.createCell((short)admBioTo.getForeignPos()).setCellValue(dataTo.getForeign());
					}
					if(admBioTo.getNriDis()!=null && admBioTo.getNriDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getNri()!=null){
						row.createCell((short)admBioTo.getNriPos()).setCellValue(dataTo.getNri());
					}
					if(admBioTo.getStuNoDis()!=null && admBioTo.getStuNoDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getStudentNo()!=null){
						row.createCell((short)admBioTo.getStuNoPos()).setCellValue(dataTo.getStudentNo());
					}
					if(admBioTo.getBloodGroupDis()!=null && admBioTo.getBloodGroupDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getBloodGroup()!=null){
						row.createCell((short)admBioTo.getBloodGroupPos()).setCellValue(dataTo.getBloodGroup());
					}
					if(admBioTo.getSvnYrKarDis()!=null && admBioTo.getSvnYrKarDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getsEvnyKar()!=null){
						row.createCell((short)admBioTo.getSvnYrKarPos()).setCellValue(dataTo.getsEvnyKar());
					}
					if(admBioTo.getMedmInstrDis()!=null && admBioTo.getMedmInstrDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getMedmInstr()!=null){
						row.createCell((short)admBioTo.getMedmInstrPos()).setCellValue(dataTo.getMedmInstr());
					}
					if(admBioTo.getMobNoDis()!=null && admBioTo.getMobNoDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getMobileNumber()!=null){
						row.createCell((short)admBioTo.getMobNoPos()).setCellValue(dataTo.getMobileNumber());
					}
					if(admBioTo.getSecondLanDis()!=null && admBioTo.getSecondLanDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getSecondLanguage()!=null){
						row.createCell((short)admBioTo.getSecondLanPos()).setCellValue(dataTo.getSecondLanguage());
					}
					if(admBioTo.getLastInstDis()!=null && admBioTo.getLastInstDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getLastInst()!=null){
						row.createCell((short)admBioTo.getLastInstPos()).setCellValue(dataTo.getLastInst());
					}
					if(admBioTo.getExamPassedDis()!=null && admBioTo.getExamPassedDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getExamPassed()!=null){
						row.createCell((short)admBioTo.getExamPassedPos()).setCellValue(dataTo.getExamPassed());
					}
					if(admBioTo.getExamRegNoDis()!=null && admBioTo.getExamRegNoDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getExRegNo()!=null){
						row.createCell((short)admBioTo.getExamRegNoPos()).setCellValue(dataTo.getExRegNo());
					}
					if(admBioTo.getYearPassDis()!=null && admBioTo.getYearPassDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getYearPass()!=null){
						row.createCell((short)admBioTo.getYearPassPos()).setCellValue(dataTo.getYearPass());
					}
					if(admBioTo.getMonthPassDis()!=null && admBioTo.getMonthPassDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getMonthPass()!=null){
						row.createCell((short)admBioTo.getMonthPassPos()).setCellValue(dataTo.getMonthPass());
					}
					if(admBioTo.getTcNoDis()!=null && admBioTo.getTcNoDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getAdmTcNo()!=null){
						row.createCell((short)admBioTo.getTcNoPos()).setCellValue(dataTo.getAdmTcNo());
					}
					if(admBioTo.getTcDateDis()!=null && admBioTo.getTcDateDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getAdmTcDt1()!=null){
						row.createCell((short)admBioTo.getTcDatePos()).setCellValue(dataTo.getAdmTcDt1());
					}
					if(admBioTo.getfOccupationDis()!=null && admBioTo.getfOccupationDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getfOccupation()!=null){
						row.createCell((short)admBioTo.getfOccupationPos()).setCellValue(dataTo.getfOccupation());
					}
					if(admBioTo.getAcademicYearDis()!=null && admBioTo.getAcademicYearDis().equalsIgnoreCase(AdmissionBioDataReportHelper.DISPLAY) && dataTo.getAcademicYear()!=null){
						row.createCell((short)admBioTo.getAcademicYearPos()).setCellValue(dataTo.getAcademicYear());
					}
				}
				bos=new ByteArrayOutputStream();
				wb.write(bos);
				HttpSession session = request.getSession();
				session.setAttribute(CMSConstants.EXCEL_BYTES,bos.toByteArray());
				isUpdated=true;
				fos.flush();
				fos.close();
				
			}catch (Exception e) {
				//throw new ApplicationException();
				// TODO: handle exception
			}
		}
		return isUpdated;
}
}
