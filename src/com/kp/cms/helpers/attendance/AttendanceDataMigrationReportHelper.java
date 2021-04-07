package com.kp.cms.helpers.attendance;

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
import org.apache.poi.xssf.usermodel.examples.SelectedSheet;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AttnBiodataPuc;
import com.kp.cms.bo.admin.AttnMarksUpload;
import com.kp.cms.bo.admin.AttnPucSubjects;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.InternalMarkUpload;
import com.kp.cms.bo.admission.PromoteMarks;
import com.kp.cms.bo.admission.PromoteSubjects;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.PromoteMarksUploadForm;
import com.kp.cms.forms.attendance.AttendanceDataMigrationForm;
import com.kp.cms.helpers.admission.AdmissionTcDetailsHelper;
import com.kp.cms.to.admission.AdmissionTcDetailsTo;
import com.kp.cms.to.admission.PromoteMarksUploadTo;
import com.kp.cms.to.attendance.AttendanceBioDataTo;
import com.kp.cms.to.attendance.AttnBioDataPucTo;
import com.kp.cms.to.attendance.AttnMarksUploadTo;
import com.kp.cms.transactions.attandance.IAttendanceDataMigrationReportTxn;
import com.kp.cms.transactionsimpl.attendance.AttendanceDataMigrationReportTxnImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class AttendanceDataMigrationReportHelper {
	private static volatile AttendanceDataMigrationReportHelper helper = null;
	private static final String DISPLAY = "display";
	public static AttendanceDataMigrationReportHelper getInstance(){
		if(helper == null){
			helper = new AttendanceDataMigrationReportHelper();
			return helper;
		}
		return helper;
	}
	/**
	 * @param attnDataMigrationForm
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getSelectedQuery( AttendanceDataMigrationForm attnDataMigrationForm) throws Exception{
		StringBuffer stringBuffer = new StringBuffer("from AttnBiodataPuc attnBioData where");
		if(attnDataMigrationForm.getAcademicYear()!=null && !attnDataMigrationForm.getAcademicYear().isEmpty()){
			stringBuffer = stringBuffer.append(" attnBioData.academicYear='"+attnDataMigrationForm.getAcademicYear()+"'");
		}
			Session session = null;
			Transaction tx =null;
			try{
				session = HibernateUtil.getSession();
				tx=session.beginTransaction();
				stringBuffer = stringBuffer.append(" and attnBioData.classes='"+attnDataMigrationForm.getCourseName()+"'");
				
			}catch (Exception e) {
				throw new ApplicationException(e);
				}
		return stringBuffer;
	}
	/**
	 * @param listBo
	 * @return
	 * @throws Exception
	 */
	public List<AttnBioDataPucTo> convertBOToTO(List<AttnBiodataPuc> listBo)throws Exception {
		 List<AttnBioDataPucTo> bioDataTo = new ArrayList<AttnBioDataPucTo>();
		 if(listBo!=null && !listBo.isEmpty()){
			 Iterator<AttnBiodataPuc> iterator = listBo.iterator();
			 while(iterator.hasNext()){
				 AttnBiodataPuc attnBiodataPuc = (AttnBiodataPuc)iterator.next();
				 AttnBioDataPucTo to = new AttnBioDataPucTo();
				 if(attnBiodataPuc.getAppNo()!= 0){
					to.setAppNo(String.valueOf(attnBiodataPuc.getAppNo()));
				 }
				 if(attnBiodataPuc.getPercentage()!=0){
					 to.setPercentage(String.valueOf(attnBiodataPuc.getPercentage()));
				 }
				 if(attnBiodataPuc.getName()!=null && !attnBiodataPuc.getName().isEmpty()){
					 to.setName(attnBiodataPuc.getName());
				 }
				 if(attnBiodataPuc.getRegNo()!=null && !attnBiodataPuc.getRegNo().isEmpty()){
					 to.setRegNo(attnBiodataPuc.getRegNo());
				 }
				 String str = "";
				 if(attnBiodataPuc.getClasses()!=null && !attnBiodataPuc.getClasses().isEmpty()){
					 String classes= attnBiodataPuc.getClasses();
					 str= str+classes;
				 }
				 if(attnBiodataPuc.getYear()!=null && !attnBiodataPuc.getYear().isEmpty()){
					 String year = attnBiodataPuc.getYear();
					 str= year + str;
				 }
				 if(attnBiodataPuc.getSection()!=null && !attnBiodataPuc.getSection().isEmpty()){
					 String section = attnBiodataPuc.getSection();
					 str = str + section;
				 }
				 to.setClasses(str);
				 if(attnBiodataPuc.getFatherName()!=null && !attnBiodataPuc.getFatherName().isEmpty()){
					 to.setFatherName(attnBiodataPuc.getFatherName());
				 }
				 if(attnBiodataPuc.getSecndLang()!=null && !attnBiodataPuc.getSecndLang().isEmpty()){
					 to.setSecndLang(attnBiodataPuc.getSecndLang());
				 }
				 if(attnBiodataPuc.getReligion()!=null && !attnBiodataPuc.getReligion().isEmpty()){
					 to.setReligion(attnBiodataPuc.getReligion());
				 }
				 if(attnBiodataPuc.getCaste()!=null && !attnBiodataPuc.getCaste().isEmpty()){
					 to.setCaste(attnBiodataPuc.getCaste());
				 }
				 if(attnBiodataPuc.getScstbcbt()!=null && !attnBiodataPuc.getScstbcbt().isEmpty()){
					 to.setScstbcbt(attnBiodataPuc.getScstbcbt());
				 }
				 if(attnBiodataPuc.getSex()!=null && !attnBiodataPuc.getSex().isEmpty()){
					 to.setSex(attnBiodataPuc.getSex());
				 }
				 if(attnBiodataPuc.getDob()!=null && !attnBiodataPuc.getDob().toString().isEmpty()){
					 to.setDateOfBirth(CommonUtil.getStringDate(attnBiodataPuc.getDob()));
				 }
				 if(attnBiodataPuc.getNationality()!=null && !attnBiodataPuc.getNationality().isEmpty()){
					 to.setNationality(attnBiodataPuc.getNationality());
				 }
				 if(attnBiodataPuc.getState()!=null && !attnBiodataPuc.getState().isEmpty()){
					 to.setState(attnBiodataPuc.getState());
				 }
				 if(attnBiodataPuc.getLastInst()!=null && !attnBiodataPuc.getLastInst().isEmpty()){
					 to.setLastInst(attnBiodataPuc.getLastInst());
				 }
				 if(attnBiodataPuc.getTelephone()!=null && !attnBiodataPuc.getTelephone().isEmpty()){
					 to.setTelephone(attnBiodataPuc.getTelephone());
				 }
				 String address = "";
				 if(attnBiodataPuc.getAddress1()!=null && !attnBiodataPuc.getAddress1().isEmpty()){
					 String address1 = attnBiodataPuc.getAddress1();
					 address = address + address1;
				 }
				 if(attnBiodataPuc.getAddress2()!=null && !attnBiodataPuc.getAddress2().isEmpty()){
					 String address2 = attnBiodataPuc.getAddress2();
					 address = address + address2;
				 }
				 if(attnBiodataPuc.getAddress3()!=null && !attnBiodataPuc.getAddress3().isEmpty()){
					 String address3 = attnBiodataPuc.getAddress3();
					 address = address + address3;
				 }
				 if(attnBiodataPuc.getAddress4()!=null && !attnBiodataPuc.getAddress4().isEmpty()){
					 String address4 = attnBiodataPuc.getAddress4();
					 address = address + address4;
				 }
				 to.setAddress1(address);
				 if(attnBiodataPuc.getOffRemarks()!=null && !attnBiodataPuc.getOffRemarks().isEmpty()){
					 to.setOffRemarks(attnBiodataPuc.getOffRemarks());
				 }
				if(attnBiodataPuc.getScholarship()!=null){
					if(!attnBiodataPuc.getScholarship().toString().isEmpty()){
						if(attnBiodataPuc.getScholarship()){
							to.setScholarship("TRUE");
						}
						else{
							to.setScholarship("FALSE");
						}
					}
				}
				if(attnBiodataPuc.getDateAdm()!=null && !attnBiodataPuc.getDateAdm().toString().isEmpty()){
					to.setDateAdm1(CommonUtil.getStringDate(attnBiodataPuc.getDateAdm()));
				}
				if(attnBiodataPuc.getAnnIncome()!=0){
					to.setAnnIncome(String.valueOf(attnBiodataPuc.getAnnIncome()));
				}
				if(attnBiodataPuc.getFailed()!=null){
					if(!attnBiodataPuc.getFailed().toString().isEmpty()){
						if(attnBiodataPuc.getFailed()){
							to.setFailed("TRUE");
						}
						else{
							to.setFailed("FALSE");
						}
					}
				}
				if(attnBiodataPuc.getBloodGroup()!=null && !attnBiodataPuc.getBloodGroup().isEmpty()){
					to.setBloodGroup(attnBiodataPuc.getBloodGroup());
				}
				if(attnBiodataPuc.getCetFeePaid()!=null){
					if(!attnBiodataPuc.getCetFeePaid().toString().isEmpty()){
						if(attnBiodataPuc.getCetFeePaid()){
							to.setCetFeePaid("TRUE");
						}else{
							to.setCetFeePaid("FALSE");
						}
					}
				}
				if(attnBiodataPuc.getAieeeFee()!=null){
					if(!attnBiodataPuc.getAieeeFee().toString().isEmpty()){
						if(attnBiodataPuc.getAieeeFee()){
							to.setAieeeFee("TRUE");
						}
						else{
							to.setAieeeFee("FALSE");
						}
					}
				}
				if(attnBiodataPuc.getUserCode()!=null && !attnBiodataPuc.getUserCode().isEmpty()){
					to.setUserCode(attnBiodataPuc.getUserCode());
				}
				if(attnBiodataPuc.getAcademicYear()!=0){
					to.setAcademicYear(String.valueOf(attnBiodataPuc.getAcademicYear()));
				}
			
				bioDataTo.add(to);
			 } 
		 }
		return bioDataTo;
	}
	/**
	 * @param attnDataMigrationForm
	 * @return
	 * @throws Exception
	 */
	public AttendanceBioDataTo selectedColumn( AttendanceDataMigrationForm attnDataMigrationForm) throws Exception{
		AttendanceBioDataTo bioDataTo =new AttendanceBioDataTo();
		List<String> selectedColumnsList = new ArrayList<String>();
		String[] selected = attnDataMigrationForm.getSelectedColumnsArray();
		for(int i=0 ; i < selected.length ; i++){
			selectedColumnsList.add(selected[i]);
		}
		if(selectedColumnsList!=null && !selectedColumnsList.isEmpty()){
			selectedColumnsList.add("Academic Year");
			Iterator<String> iterator = selectedColumnsList.iterator();
			int count =0;
			while (iterator.hasNext()) {
				String columnName = (String) iterator.next();
				if(columnName.equalsIgnoreCase("Appln No")){
					bioDataTo.setApplnNoDis(AttendanceDataMigrationReportHelper.DISPLAY);
					bioDataTo.setApplnNoPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Reg No")){
					bioDataTo.setRegNoDis(AttendanceDataMigrationReportHelper.DISPLAY);
					bioDataTo.setRegNoPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Class")){
					bioDataTo.setClassesDis(AttendanceDataMigrationReportHelper.DISPLAY);
					bioDataTo.setClassesPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Name")){
					bioDataTo.setNameDis(AttendanceDataMigrationReportHelper.DISPLAY);
					bioDataTo.setNamePos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Father Name")){
					bioDataTo.setFatherNameDis(AttendanceDataMigrationReportHelper.DISPLAY);
					bioDataTo.setFatherNamePos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Second Language")){
					bioDataTo.setSecondLanguageDis(AttendanceDataMigrationReportHelper.DISPLAY);
					bioDataTo.setSecondLanguagePos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Religion")){
					bioDataTo.setReligionDis(AttendanceDataMigrationReportHelper.DISPLAY);
					bioDataTo.setReligionPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Caste")){
					bioDataTo.setCasteDis(AttendanceDataMigrationReportHelper.DISPLAY);
					bioDataTo.setCastePos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Category")){
					bioDataTo.setCategoryDis(AttendanceDataMigrationReportHelper.DISPLAY);
					bioDataTo.setCategoryPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Gender")){
					bioDataTo.setGenderDis(AttendanceDataMigrationReportHelper.DISPLAY);
					bioDataTo.setGenderPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Date Of Birth")){
					bioDataTo.setDobDis(AttendanceDataMigrationReportHelper.DISPLAY);
					bioDataTo.setDobPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Nationality")){
					bioDataTo.setNationalityDis(AttendanceDataMigrationReportHelper.DISPLAY);
					bioDataTo.setNationalityPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("State")){
					bioDataTo.setStateDis(AttendanceDataMigrationReportHelper.DISPLAY);
					bioDataTo.setStatePos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Telephone")){
					bioDataTo.setTelDis(AttendanceDataMigrationReportHelper.DISPLAY);
					bioDataTo.setTelPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Address")){
					bioDataTo.setAddressDis(AttendanceDataMigrationReportHelper.DISPLAY);
					bioDataTo.setAddressPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Off Remark")){
					bioDataTo.setOffRemarksDis(AttendanceDataMigrationReportHelper.DISPLAY);
					bioDataTo.setOffRemartsPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Scholarship")){
					bioDataTo.setScholarshipDis(AttendanceDataMigrationReportHelper.DISPLAY);
					bioDataTo.setScholarshipPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Date Adm")){
					bioDataTo.setDateAdmDis(AttendanceDataMigrationReportHelper.DISPLAY);
					bioDataTo.setDateAdmPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Annual Income")){
					bioDataTo.setAnnualIncomeDis(AttendanceDataMigrationReportHelper.DISPLAY);
					bioDataTo.setAnnualIncomePos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Failed")){
					bioDataTo.setFailedDis(AttendanceDataMigrationReportHelper.DISPLAY);
					bioDataTo.setFailedPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Blood Group")){
					bioDataTo.setBloodGroupDis(AttendanceDataMigrationReportHelper.DISPLAY);
					bioDataTo.setBloodGroupPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Cet FeePaid")){
					bioDataTo.setCetFeePaidDis(AttendanceDataMigrationReportHelper.DISPLAY);
					bioDataTo.setCetFeePaidPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Aieee FeePaid")){
					bioDataTo.setAieeFeePaidDis(AttendanceDataMigrationReportHelper.DISPLAY);
					bioDataTo.setAieeFeePaidPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("User Code")){
					bioDataTo.setUserCodeDis(AttendanceDataMigrationReportHelper.DISPLAY);
					bioDataTo.setUserCodePos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Previous Qualification Details")){
					bioDataTo.setPercentageDis(AttendanceDataMigrationReportHelper.DISPLAY);
					bioDataTo.setPercentagePos((short)count++);
					bioDataTo.setLastInstrDis(AttendanceDataMigrationReportHelper.DISPLAY);
					bioDataTo.setLastInstrPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Academic Year")){
					bioDataTo.setAcademicYearDis(AttendanceDataMigrationReportHelper.DISPLAY);
					bioDataTo.setAcademicYearPos((short)count++);
					}
				}
		}
		return bioDataTo;
	}
	/**
	 * @param attBioDataTo
	 * @param attnDataMigrationForm
	 * @param request
	 * @return
	 */
	public boolean populateBOToTO(AttendanceBioDataTo attBioDataTo, AttendanceDataMigrationForm attnDataMigrationForm,
			HttpServletRequest request)throws Exception {
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
		String fileName=prop.getProperty(CMSConstants.UPLOAD_ATTN_BIODATA);
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
		
		if(attnDataMigrationForm.getAttnBioDataList()!=null){
			AttnBioDataPucTo to=(AttnBioDataPucTo)attnDataMigrationForm.getAttnBioDataList().get(0);
			int count = 0;
			Iterator<AttnBioDataPucTo> iterator = attnDataMigrationForm.getAttnBioDataList().iterator();
			try{
				wb=new XSSFWorkbook();
				XSSFCellStyle cellStyle=wb.createCellStyle();
				CreationHelper createHelper = wb.getCreationHelper();
				cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
				sheet = wb.createSheet("Attendance BioData Report");
				row = sheet.createRow(count);
				count = sheet.getFirstRowNum();
				// Create cells in the row and put some data in it.
				if(attBioDataTo.getApplnNoDis()!=null && attBioDataTo.getApplnNoDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY)){
					row.createCell((short)attBioDataTo.getApplnNoPos()).setCellValue("Appln No");
				}
				if(attBioDataTo.getRegNoDis()!=null && attBioDataTo.getRegNoDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY)){
					row.createCell((short)attBioDataTo.getRegNoPos()).setCellValue("Reg No");
				}
				if(attBioDataTo.getClassesDis()!=null && attBioDataTo.getClassesDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY)){
					row.createCell((short)attBioDataTo.getClassesPos()).setCellValue("Class");
				}
				if(attBioDataTo.getNameDis()!=null && attBioDataTo.getNameDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY)){
					row.createCell((short)attBioDataTo.getNamePos()).setCellValue("Name");
				}
				if(attBioDataTo.getFatherNameDis()!=null && attBioDataTo.getFatherNameDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY)){
					row.createCell((short)attBioDataTo.getFatherNamePos()).setCellValue("Father Name");
				}
				if(attBioDataTo.getSecondLanguageDis()!=null && attBioDataTo.getSecondLanguageDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY)){
					row.createCell((short)attBioDataTo.getSecondLanguagePos()).setCellValue("Second Language");
				}
				if(attBioDataTo.getReligionDis()!=null && attBioDataTo.getReligionDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY)){
					row.createCell((short)attBioDataTo.getReligionPos()).setCellValue("Religion");
				}
				if(attBioDataTo.getCasteDis()!=null && attBioDataTo.getCasteDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY)){
					row.createCell((short)attBioDataTo.getCastePos()).setCellValue("Caste");
				}
				if(attBioDataTo.getCategoryDis()!=null && attBioDataTo.getCategoryDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY)){
					row.createCell((short)attBioDataTo.getCategoryPos()).setCellValue("Category");
				}
				if(attBioDataTo.getGenderDis()!=null && attBioDataTo.getGenderDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY)){
					row.createCell((short)attBioDataTo.getGenderPos()).setCellValue("Gender");
				}
				if(attBioDataTo.getDobDis()!=null && attBioDataTo.getDobDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY)){
					row.createCell((short)attBioDataTo.getDobPos()).setCellValue("Date Of Birth");
				}
				if(attBioDataTo.getNationalityDis()!=null && attBioDataTo.getNationalityDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY)){
					row.createCell((short)attBioDataTo.getNationalityPos()).setCellValue("Nationality");
				}
				if(attBioDataTo.getStateDis()!=null && attBioDataTo.getStateDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY)){
					row.createCell((short)attBioDataTo.getStatePos()).setCellValue("State");
				}
				if(attBioDataTo.getLastInstrDis()!=null && attBioDataTo.getLastInstrDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY)){
					row.createCell((short)attBioDataTo.getLastInstrPos()).setCellValue("Previous Institute");
				}
				if(attBioDataTo.getPercentageDis()!=null && attBioDataTo.getPercentageDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY)){
					row.createCell((short)attBioDataTo.getPercentagePos()).setCellValue("Previous Percentage");
				}
				if(attBioDataTo.getTelDis()!=null && attBioDataTo.getTelDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY)){
					row.createCell((short)attBioDataTo.getTelPos()).setCellValue("Telephone");
				}
				if(attBioDataTo.getAddressDis()!=null && attBioDataTo.getAddressDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY)){
					row.createCell((short)attBioDataTo.getAddressPos()).setCellValue("Address");
				}
				if(attBioDataTo.getOffRemarksDis()!=null && attBioDataTo.getOffRemarksDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY)){
					row.createCell((short)attBioDataTo.getOffRemartsPos()).setCellValue("Off Remarks");
				}
				if(attBioDataTo.getScholarshipDis()!=null && attBioDataTo.getScholarshipDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY)){
					row.createCell((short)attBioDataTo.getScholarshipPos()).setCellValue("Scholarship");
				}
				if(attBioDataTo.getDateAdmDis()!=null && attBioDataTo.getDateAdmDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY)){
					row.createCell((short)attBioDataTo.getDateAdmPos()).setCellValue("Date Adm");
				}
				if(attBioDataTo.getAnnualIncomeDis()!=null && attBioDataTo.getAnnualIncomeDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY)){
					row.createCell((short)attBioDataTo.getAnnualIncomePos()).setCellValue("Annual Income");
				}
				if(attBioDataTo.getFailedDis()!=null && attBioDataTo.getFailedDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY)){
					row.createCell((short)attBioDataTo.getFailedPos()).setCellValue("Failed");
				}
				if(attBioDataTo.getBloodGroupDis()!=null && attBioDataTo.getBloodGroupDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY)){
					row.createCell((short)attBioDataTo.getBloodGroupPos()).setCellValue("Blood Group");
				}
				if(attBioDataTo.getCetFeePaidDis()!=null && attBioDataTo.getCetFeePaidDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY)){
					row.createCell((short)attBioDataTo.getCetFeePaidPos()).setCellValue("Cet FeePaid");
				}
				if(attBioDataTo.getAieeFeePaidDis()!=null && attBioDataTo.getAieeFeePaidDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY)){
					row.createCell((short)attBioDataTo.getAieeFeePaidPos()).setCellValue("Aiee FeePaid");
				}
				if(attBioDataTo.getUserCodeDis()!=null && attBioDataTo.getUserCodeDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY)){
					row.createCell((short)attBioDataTo.getUserCodePos()).setCellValue("User Code");
				}
				if(attBioDataTo.getAcademicYearDis()!=null && attBioDataTo.getAcademicYearDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY)){
					row.createCell((short)attBioDataTo.getAcademicYearPos()).setCellValue("Academic Year");
				}
				while (iterator.hasNext()) {
					AttnBioDataPucTo attnPucTo = (AttnBioDataPucTo) iterator.next();
					count = count +1;
					row = sheet.createRow(count);
					if(attBioDataTo.getNameDis()!=null && attBioDataTo.getNameDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY) && attnPucTo.getName()!=null){
						row.createCell((short)attBioDataTo.getNamePos()).setCellValue(attnPucTo.getName());
					}
					if(attBioDataTo.getApplnNoDis()!=null && attBioDataTo.getApplnNoDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY) && attnPucTo.getAppNo()!=null){
						row.createCell((short)attBioDataTo.getApplnNoPos()).setCellValue(attnPucTo.getAppNo());
					}
					if(attBioDataTo.getRegNoDis()!=null && attBioDataTo.getRegNoDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY) && attnPucTo.getRegNo()!=null){
						row.createCell((short)attBioDataTo.getRegNoPos()).setCellValue(attnPucTo.getRegNo());
					}
					if(attBioDataTo.getClassesDis()!=null && attBioDataTo.getClassesDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY) && attnPucTo.getClasses()!=null){
						row.createCell((short)attBioDataTo.getClassesPos()).setCellValue(attnPucTo.getClasses());
					}
					if(attBioDataTo.getPercentageDis()!=null && attBioDataTo.getPercentageDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY) && attnPucTo.getPercentage()!=null){
						row.createCell((short)attBioDataTo.getPercentagePos()).setCellValue(attnPucTo.getPercentage());
					}
					if(attBioDataTo.getFatherNameDis()!=null && attBioDataTo.getFatherNameDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY) && attnPucTo.getFatherName()!=null){
						row.createCell((short)attBioDataTo.getFatherNamePos()).setCellValue(attnPucTo.getFatherName());
					}
					if(attBioDataTo.getSecondLanguageDis()!=null && attBioDataTo.getSecondLanguageDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY) && attnPucTo.getSecndLang()!=null){
						row.createCell((short)attBioDataTo.getSecondLanguagePos()).setCellValue(attnPucTo.getSecndLang());
					}
					if(attBioDataTo.getReligionDis()!=null && attBioDataTo.getReligionDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY) && attnPucTo.getReligion()!=null){
						row.createCell((short)attBioDataTo.getReligionPos()).setCellValue(attnPucTo.getReligion());
					}
					if(attBioDataTo.getCasteDis()!=null && attBioDataTo.getCasteDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY) && attnPucTo.getCaste()!=null){
						row.createCell((short)attBioDataTo.getCastePos()).setCellValue(attnPucTo.getCaste());
					}
					if(attBioDataTo.getCategoryDis()!=null && attBioDataTo.getCategoryDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY) && attnPucTo.getScstbcbt()!=null){
						row.createCell((short)attBioDataTo.getCategoryPos()).setCellValue(attnPucTo.getScstbcbt());
					}
					if(attBioDataTo.getGenderDis()!=null && attBioDataTo.getGenderDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY) && attnPucTo.getSex()!=null){
						row.createCell((short)attBioDataTo.getGenderPos()).setCellValue(attnPucTo.getSex());
					}
					if(attBioDataTo.getDobDis()!=null && attBioDataTo.getDobDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY) && attnPucTo.getDateOfBirth()!=null){
						row.createCell((short)attBioDataTo.getDobPos()).setCellValue(attnPucTo.getDateOfBirth());
					}
					if(attBioDataTo.getNationalityDis()!=null && attBioDataTo.getNationalityDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY) && attnPucTo.getNationality()!=null){
						row.createCell((short)attBioDataTo.getNationalityPos()).setCellValue(attnPucTo.getNationality());
					}
					if(attBioDataTo.getStateDis()!=null && attBioDataTo.getStateDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY) && attnPucTo.getState()!=null){
						row.createCell((short)attBioDataTo.getStatePos()).setCellValue(attnPucTo.getState());
					}
					if(attBioDataTo.getLastInstrDis()!=null && attBioDataTo.getLastInstrDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY) && attnPucTo.getLastInst()!=null){
						row.createCell((short)attBioDataTo.getLastInstrPos()).setCellValue(attnPucTo.getLastInst());
					}
					if(attBioDataTo.getTelDis()!=null && attBioDataTo.getTelDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY) && attnPucTo.getTelephone()!=null){
						row.createCell((short)attBioDataTo.getTelPos()).setCellValue(attnPucTo.getTelephone());
					}
					if(attBioDataTo.getAddressDis()!=null && attBioDataTo.getAddressDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY) && attnPucTo.getAddress1()!=null){
						row.createCell((short)attBioDataTo.getAddressPos()).setCellValue(attnPucTo.getAddress1());
					}
					if(attBioDataTo.getOffRemarksDis()!=null && attBioDataTo.getOffRemarksDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY) && attnPucTo.getOffRemarks()!=null){
						row.createCell((short)attBioDataTo.getOffRemartsPos()).setCellValue(attnPucTo.getOffRemarks());
					}
					if(attBioDataTo.getScholarshipDis()!=null && attBioDataTo.getScholarshipDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY) && attnPucTo.getScholarship()!=null){
						row.createCell((short)attBioDataTo.getScholarshipPos()).setCellValue(attnPucTo.getScholarship());
					}
					if(attBioDataTo.getDateAdmDis()!=null && attBioDataTo.getDateAdmDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY) && attnPucTo.getDateAdm1()!=null){
						row.createCell((short)attBioDataTo.getDateAdmPos()).setCellValue(attnPucTo.getDateAdm1());
					}
					if(attBioDataTo.getAnnualIncomeDis()!=null && attBioDataTo.getAnnualIncomeDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY) && attnPucTo.getAnnIncome()!=null){
						row.createCell((short)attBioDataTo.getAnnualIncomePos()).setCellValue(attnPucTo.getAnnIncome());
					}
					if(attBioDataTo.getFailedDis()!=null && attBioDataTo.getFailedDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY) && attnPucTo.getFailed()!=null){
						row.createCell((short)attBioDataTo.getFailedPos()).setCellValue(attnPucTo.getFailed());
					}
					if(attBioDataTo.getBloodGroupDis()!=null && attBioDataTo.getBloodGroupDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY) && attnPucTo.getBloodGroup()!=null){
						row.createCell((short)attBioDataTo.getBloodGroupPos()).setCellValue(attnPucTo.getBloodGroup());
					}
					if(attBioDataTo.getCetFeePaidDis()!=null && attBioDataTo.getCetFeePaidDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY) && attnPucTo.getCetFeePaid()!=null){
						row.createCell((short)attBioDataTo.getCetFeePaidPos()).setCellValue(attnPucTo.getCetFeePaid());
					}
					if(attBioDataTo.getAieeFeePaidDis()!=null && attBioDataTo.getAieeFeePaidDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY) && attnPucTo.getAieeeFee()!=null){
						row.createCell((short)attBioDataTo.getAieeFeePaidPos()).setCellValue(attnPucTo.getAieeeFee());
					}
					if(attBioDataTo.getUserCodeDis()!=null && attBioDataTo.getUserCodeDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY) && attnPucTo.getUserCode()!=null){
						row.createCell((short)attBioDataTo.getUserCodePos()).setCellValue(attnPucTo.getUserCode());
					}
					if(attBioDataTo.getAcademicYearDis()!=null && attBioDataTo.getAcademicYearDis().equalsIgnoreCase(AttendanceDataMigrationReportHelper.DISPLAY) && attnPucTo.getAcademicYear()!=null){
						row.createCell((short)attBioDataTo.getAcademicYearPos()).setCellValue(attnPucTo.getAcademicYear());
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

	public List<AttnMarksUploadTo> convertAttnMarksBoToTO(List<AttnMarksUpload> attnMarksList)throws Exception{
		List<AttnMarksUploadTo> attnMarksToList=new ArrayList<AttnMarksUploadTo>();
		IAttendanceDataMigrationReportTxn transaction = AttendanceDataMigrationReportTxnImpl.getInstance();
		Iterator<AttnMarksUpload> itr=attnMarksList.iterator();
		try{
		while(itr.hasNext()){
			AttnMarksUpload attnMarks=itr.next();
			AttnMarksUploadTo attnMarksTo=new AttnMarksUploadTo();
			if(attnMarks.getRegNo()!=null){
			    attnMarksTo.setRegNo(attnMarks.getRegNo());
			    String secondLang= transaction.getSecondLang(attnMarks.getRegNo(),attnMarks.getAcademicYear());
			    attnMarksTo.setSecondLang(secondLang);
			}
			if(attnMarks.getClasses()!=null)
				attnMarksTo.setClasses(attnMarks.getClasses());
			if(attnMarks.getTestIdent()!=null)
				attnMarksTo.setTestIdent(attnMarks.getTestIdent());
			if(attnMarks.getMrkSub1()!=null)
				attnMarksTo.setMrkSub1(attnMarks.getMrkSub1());
			if(attnMarks.getMrkSub2()!=null)
				attnMarksTo.setMrkSub2(attnMarks.getMrkSub2());
			if(attnMarks.getMrkSub3()!=null)
				attnMarksTo.setMrkSub3(attnMarks.getMrkSub3());
			if(attnMarks.getMrkSub4()!=null)
				attnMarksTo.setMrkSub4(attnMarks.getMrkSub4());
			if(attnMarks.getMrkSub5()!=null)
				attnMarksTo.setMrkSub5(attnMarks.getMrkSub5());
			if(attnMarks.getMrkSub6()!=null)
				attnMarksTo.setMrkSub6(attnMarks.getMrkSub6());
			if(attnMarks.getMrkSub7()!=null)
				attnMarksTo.setMrkSub7(attnMarks.getMrkSub7());
			if(attnMarks.getMrkSub8()!=null)
				attnMarksTo.setMrkSub8(attnMarks.getMrkSub8());
			if(attnMarks.getMrkSub9()!=null)
				attnMarksTo.setMrkSub9(attnMarks.getMrkSub9());
			if(attnMarks.getMrkSub10()!=null)
				attnMarksTo.setMrkSub10(attnMarks.getMrkSub10());
			if(attnMarks.getMrkLang()!=null)
				attnMarksTo.setMrkLang(attnMarks.getMrkLang());
			if(attnMarks.getMrkPrac1()!=null)
				attnMarksTo.setMrkPrac1(attnMarks.getMrkPrac1());
			if(attnMarks.getMrkPrac2()!=null)
				attnMarksTo.setMrkPrac2(attnMarks.getMrkPrac2());
			if(attnMarks.getMrkPrac3()!=null)
				attnMarksTo.setMrkPrac3(attnMarks.getMrkPrac3());
			if(attnMarks.getMrkPrac4()!=null)
				attnMarksTo.setMrkPrac4(attnMarks.getMrkPrac4());
			if(attnMarks.getUserCode()!=null)
				attnMarksTo.setUserCode(attnMarks.getUserCode());
			attnMarksTo.setAcademicYear(attnMarks.getAcademicYear());	
			attnMarksToList.add(attnMarksTo);
		}
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return attnMarksToList;
	}
	
public void setSubjectNamesToSession(AttendanceDataMigrationForm attnDataMigrationForm,HttpSession session)throws Exception{
	IAttendanceDataMigrationReportTxn transaction = AttendanceDataMigrationReportTxnImpl.getInstance();
	AttnPucSubjects subjects=transaction.getAttnSubject(attnDataMigrationForm.getClassName());
		String practical="-PRACTICAL";
		if(subjects.getSubject1()!=null){
		    session.setAttribute("subject1", subjects.getSubject1().toUpperCase());
		    session.setAttribute("pracSubj1", subjects.getSubject1().toUpperCase()+practical);
		}
		else{
			session.setAttribute("subject1", "SUBJECT-1");
			session.setAttribute("pracSubj1", "PRACTICAL-1");
			
		}
		if(subjects.getSubject2()!=null){
		    session.setAttribute("subject2", subjects.getSubject2().toUpperCase());
		    session.setAttribute("pracSubj2", subjects.getSubject2().toUpperCase()+practical);
		}
		else{
			session.setAttribute("subject2", "SUBJECT-2");
			session.setAttribute("pracSubj2", "PRACTICAL-2");
			
		}
		if(subjects.getSubject3()!=null){
		    session.setAttribute("subject3", subjects.getSubject3().toUpperCase());
		    session.setAttribute("pracSubj3", subjects.getSubject3().toUpperCase()+practical);
		}
		else{
			session.setAttribute("subject3", "SUBJECT-3");
			session.setAttribute("pracSubj3", "PRACTICAL-3");
		}
		if(subjects.getSubject4()!=null){
		    session.setAttribute("subject4", subjects.getSubject4().toUpperCase());
		    session.setAttribute("pracSubj4", subjects.getSubject4().toUpperCase()+practical);
		}
		else{
			session.setAttribute("subject4", "SUBJECT-4");
			session.setAttribute("pracSubj4", "PRACTICAL-4");
		}
		if(subjects.getSubject5()!=null){
		    session.setAttribute("subject5", subjects.getSubject5().toUpperCase());
		}
		else{
			session.setAttribute("subject5", "SUBJECT-5");
		}
		if(subjects.getSubject6()!=null){
		    session.setAttribute("subject6", subjects.getSubject6().toUpperCase());
		}
		else{
			session.setAttribute("subject6", "SUBJECT-6");
		}
		if(subjects.getSubject7()!=null){
		    session.setAttribute("subject7", subjects.getSubject7().toUpperCase());
		}
		else{
			session.setAttribute("subject7", "SUBJECT-7");
		}
		if(subjects.getSubject8()!=null){
		    session.setAttribute("subject8", subjects.getSubject8().toUpperCase());
		}
		else{
			session.setAttribute("subject8", "SUBJECT-8");
		}
		if(subjects.getSubject9()!=null){
		    session.setAttribute("subject9", subjects.getSubject9().toUpperCase());
		}
		else{
			session.setAttribute("subject9", "SUBJECT-9");
		}
		if(subjects.getSubject10()!=null){
		    session.setAttribute("subject10", subjects.getSubject10().toUpperCase());
		}
		else{
			session.setAttribute("subject10", "SUBJECT-10");
		}
	}
public List<AttnMarksUploadTo> convertInterMarksBoToTO(	List<InternalMarkUpload> interMarksList) throws Exception{
	List<AttnMarksUploadTo> interMarksToList=new ArrayList<AttnMarksUploadTo>();
	IAttendanceDataMigrationReportTxn transaction = AttendanceDataMigrationReportTxnImpl.getInstance();
	Iterator<InternalMarkUpload> itr=interMarksList.iterator();
	try{
	while(itr.hasNext()){
		InternalMarkUpload attnMarks=itr.next();
		AttnMarksUploadTo attnMarksTo=new AttnMarksUploadTo();
		if(attnMarks.getRegNo()!=null){
		    attnMarksTo.setRegNo(attnMarks.getRegNo());
		    String secondLang= transaction.getSecondLang(attnMarks.getRegNo(),attnMarks.getAcademicYear());
		    attnMarksTo.setSecondLang(secondLang);
		}
		if(attnMarks.getClasses()!=null)
			attnMarksTo.setClasses(attnMarks.getClasses());
		if(attnMarks.getTestIdent()!=null)
			attnMarksTo.setTestIdent(attnMarks.getTestIdent());
		if(attnMarks.getMrkSub1()!=null)
			attnMarksTo.setMrkSub1(attnMarks.getMrkSub1());
		if(attnMarks.getMrkSub2()!=null)
			attnMarksTo.setMrkSub2(attnMarks.getMrkSub2());
		if(attnMarks.getMrkSub3()!=null)
			attnMarksTo.setMrkSub3(attnMarks.getMrkSub3());
		if(attnMarks.getMrkSub4()!=null)
			attnMarksTo.setMrkSub4(attnMarks.getMrkSub4());
		if(attnMarks.getMrkSub5()!=null)
			attnMarksTo.setMrkSub5(attnMarks.getMrkSub5());
		if(attnMarks.getMrkSub6()!=null)
			attnMarksTo.setMrkSub6(attnMarks.getMrkSub6());
		if(attnMarks.getMrkSub7()!=null)
			attnMarksTo.setMrkSub7(attnMarks.getMrkSub7());
		if(attnMarks.getMrkSub8()!=null)
			attnMarksTo.setMrkSub8(attnMarks.getMrkSub8());
		if(attnMarks.getMrkSub9()!=null)
			attnMarksTo.setMrkSub9(attnMarks.getMrkSub9());
		if(attnMarks.getMrkSub10()!=null)
			attnMarksTo.setMrkSub10(attnMarks.getMrkSub10());
		if(attnMarks.getMrkLang()!=null)
			attnMarksTo.setMrkLang(attnMarks.getMrkLang());
		if(attnMarks.getUserCode()!=null)
			attnMarksTo.setUserCode(attnMarks.getUserCode());
		attnMarksTo.setAcademicYear(attnMarks.getAcademicYear());	
		interMarksToList.add(attnMarksTo);
	}
	}catch(Exception e){
		
		e.printStackTrace();
	}
	return interMarksToList;
}
}
