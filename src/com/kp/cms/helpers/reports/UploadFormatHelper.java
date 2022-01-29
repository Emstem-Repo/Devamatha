package com.kp.cms.helpers.reports;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.reports.UploadFormatForm;
import com.kp.cms.utilities.CommonUtil;

	public class UploadFormatHelper {
		private static Log log = LogFactory.getLog(UploadFormatHelper.class);
		
		public static volatile UploadFormatHelper uploadFormatHelper=null;
		/**
		 * @return
		 * This method will return instance of this classes
		 */
		public static UploadFormatHelper getInstance(){
			if(uploadFormatHelper==null)
				uploadFormatHelper= new UploadFormatHelper();
			return uploadFormatHelper;
		}
		private UploadFormatHelper(){
			
		}
		
		public static void exportTOExcel(UploadFormatForm uploadFormatForm ,HttpServletRequest request) throws Exception{
			// File test = null;
			Properties prop = new Properties();
			try {
				InputStream inputStream = CommonUtil.class.getClassLoader()
						.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				prop.load(inputStream);
			} 
			catch (IOException e) {
				log.error("Error occured at exportTOExcel of UploadFormatHelper ",e);
				throw new IOException(e);
			}
			String fileName=prop.getProperty(CMSConstants.UPLOAD_EXCEL_DESTINATION);
			File excelFile = new File(request.getRealPath("")+ "//TempFiles//"+fileName);	
			if(excelFile.exists()){
				excelFile.delete();
			}
			FileOutputStream fos = null;
			HSSFWorkbook wb = null;
			HSSFSheet sheet =null;
			HSSFRow row = null;
			HSSFCell cell = null;
				
			int count = 0;
			
			try	{
				wb = new HSSFWorkbook();
				HSSFCellStyle cellStyle = wb.createCellStyle();
				cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("dd/MM/yyyy"));
				sheet = wb.createSheet(uploadFormatForm.getUploadFormatFile());
				row = sheet.createRow(count);
				count = sheet.getFirstRowNum();
				// Create cells in the row and put some data in it.
				if(uploadFormatForm.getUploadFormatFile().equalsIgnoreCase("mobileFormat")){
					uploadFormatForm.setFileNo("1");
					row.createCell((short)0).setCellValue(CMSConstants.ROLLNO);
					row.createCell((short)1).setCellValue("MobileCountryCode");
					row.createCell((short)2).setCellValue("Mobile");
					row.createCell((short)3).setCellValue("ParentMobileCountryCode");
					row.createCell((short)4).setCellValue("ParentMobile");
					row.createCell((short)5).setCellValue("PhoneCountryCode");
					row.createCell((short)6).setCellValue("PhoneAreaCode");
					row.createCell((short)7).setCellValue("Phone");
					row.createCell((short)8).setCellValue("ParentPhoneCountryCode");
					row.createCell((short)9).setCellValue("ParentPhoneAreaCode");
					row.createCell((short)10).setCellValue(CMSConstants.PARENT_PHONE);
				}
				if(uploadFormatForm.getUploadFormatFile().equalsIgnoreCase("secondLanguage")){
						uploadFormatForm.setFileNo("2");
						row.createCell((short)0).setCellValue("RegisterNo");
						row.createCell((short)1).setCellValue("SecondLanguage");
					}
				if(uploadFormatForm.getUploadFormatFile().equalsIgnoreCase("RegisterNoUpload")){
					uploadFormatForm.setFileNo("3");
					row.createCell((short)0).setCellValue("ApplicationNumber");
					row.createCell((short)1).setCellValue("RegisterNumber");
					row.createCell((short)2).setCellValue("ClassName");
					row.createCell((short)3).setCellValue("RollNumber");
				}
				if(uploadFormatForm.getUploadFormatFile().equalsIgnoreCase("OMRUpload")){
					uploadFormatForm.setFileNo("4");
					row.createCell((short)1).setCellValue("Challan_NO");
					row.createCell((short)2).setCellValue("journal_No");
					row.createCell((short)3).setCellValue("Challan_date");
					row.createCell((short)4).setCellValue("Challan_Amount");
					row.createCell((short)5).setCellValue("Bank_BranchCode");
					row.createCell((short)6).setCellValue("Application_Number");
					row.createCell((short)7).setCellValue("Academic_year");
					row.createCell((short)8).setCellValue("Name");
					row.createCell((short)9).setCellValue("Date_of_Birth");
					row.createCell((short)10).setCellValue("Place_of_Birth");
					row.createCell((short)11).setCellValue("Country_of_Birth");
					row.createCell((short)12).setCellValue("State_of_Birth");
					row.createCell((short)13).setCellValue("Nationality");
					row.createCell((short)14).setCellValue("Gender");
					row.createCell((short)15).setCellValue("Is_SportsPerson");
					row.createCell((short)16).setCellValue("Is_Handicapped");
					row.createCell((short)17).setCellValue("Blood_group");
					row.createCell((short)18).setCellValue("resident_category");
					row.createCell((short)19).setCellValue("Religion");
					row.createCell((short)20).setCellValue("Sub-religion");
					row.createCell((short)21).setCellValue("Caste");
					row.createCell((short)22).setCellValue("Area_type");
					row.createCell((short)23).setCellValue("PhoneCountryCode");
					row.createCell((short)24).setCellValue("PhoneAreaCode");
					row.createCell((short)25).setCellValue("Phone");
					row.createCell((short)26).setCellValue("MobileCountryCode");
					row.createCell((short)27).setCellValue("Mobile");
					row.createCell((short)28).setCellValue("email");
					row.createCell((short)29).setCellValue("passportno");
					row.createCell((short)30).setCellValue("passport_country");
					row.createCell((short)31).setCellValue("passport_validity");
					row.createCell((short)32).setCellValue("permanent_Adderss1");
					row.createCell((short)33).setCellValue("permanent_Adderss2");
					row.createCell((short)34).setCellValue("Permanent_address_city");
					row.createCell((short)35).setCellValue("permanent_address_state");
					row.createCell((short)36).setCellValue("permanent_address_country");
					row.createCell((short)37).setCellValue("permanent_address_pincode");
					row.createCell((short)38).setCellValue("current_Adderss1");
					row.createCell((short)39).setCellValue("current_Adderss2");
					row.createCell((short)40).setCellValue("current_address_city");
					row.createCell((short)41).setCellValue("current_address_state");
					row.createCell((short)42).setCellValue("current_address_country");
					row.createCell((short)43).setCellValue("current_address_pincode");
					row.createCell((short)44).setCellValue("University-board");
					row.createCell((short)45).setCellValue("Institute_Name");
					row.createCell((short)46).setCellValue("Max_Marks");
					row.createCell((short)47).setCellValue("obtained_Marks");
					row.createCell((short)48).setCellValue("Year_Of_passing");
					row.createCell((short)49).setCellValue("No_Of_attempts");
					row.createCell((short)50).setCellValue("Exam_Name");
					row.createCell((short)51).setCellValue("Father_name");
					row.createCell((short)52).setCellValue("Father_Education");
					row.createCell((short)53).setCellValue("father_Income");
					row.createCell((short)54).setCellValue("Father_Occupation");
					row.createCell((short)55).setCellValue("Father_Income_Currencytype");
					row.createCell((short)56).setCellValue("father_email");
					row.createCell((short)57).setCellValue("Mother_name");
					row.createCell((short)58).setCellValue("Mother_Education");
					row.createCell((short)59).setCellValue("Mother_Income");
					row.createCell((short)60).setCellValue("mother_Occupation");
					row.createCell((short)61).setCellValue("Mother_Income_Currencytype");
					row.createCell((short)62).setCellValue("Mother_email");
					row.createCell((short)63).setCellValue("parent_Adderss1");
					row.createCell((short)64).setCellValue("parent_Adderss2");
					row.createCell((short)65).setCellValue("parent_Adderss3");
					row.createCell((short)66).setCellValue("parent_address_city");
					row.createCell((short)67).setCellValue("Parent_address_state");
					row.createCell((short)68).setCellValue("parent_address_country");
					row.createCell((short)69).setCellValue("parent_address_pincode");
					row.createCell((short)70).setCellValue("Parent_PhoneCountryCode");
					row.createCell((short)71).setCellValue("Parent_PhoneAreaCode");
					row.createCell((short)72).setCellValue("Parent_Phone");
					row.createCell((short)73).setCellValue("parent_MobileCountryCode");
					row.createCell((short)74).setCellValue("parent_Mobile");
				}
				if(uploadFormatForm.getUploadFormatFile().equalsIgnoreCase("admissionDataUpload")){
					uploadFormatForm.setFileNo("5");
					row.createCell((short)1).setCellValue("Challan_NO");
					row.createCell((short)2).setCellValue("journal_No");
					row.createCell((short)3).setCellValue("Challan_date");
					row.createCell((short)4).setCellValue("Challan_Amount");
					row.createCell((short)5).setCellValue("Bank_BranchCode");
					row.createCell((short)6).setCellValue("Application_Number");
					row.createCell((short)7).setCellValue("Academic_year");
					row.createCell((short)8).setCellValue("Name");
					row.createCell((short)9).setCellValue("Date_of_Birth");
					row.createCell((short)10).setCellValue("Place_of_Birth");
					row.createCell((short)11).setCellValue("Country_of_Birth");
					row.createCell((short)12).setCellValue("State_of_Birth");
					row.createCell((short)13).setCellValue("Nationality");
					row.createCell((short)14).setCellValue("Gender");
					row.createCell((short)15).setCellValue("Is_SportsPerson");
					row.createCell((short)16).setCellValue("Is_Handicapped");
					row.createCell((short)17).setCellValue("Blood_group");
					row.createCell((short)18).setCellValue("resident_category");
					row.createCell((short)19).setCellValue("Religion");
					row.createCell((short)20).setCellValue("Sub-religion");
					row.createCell((short)21).setCellValue("Caste");
					row.createCell((short)22).setCellValue("Area_type");
					row.createCell((short)23).setCellValue("PhoneCountryCode");
					row.createCell((short)24).setCellValue("PhoneAreaCode");
					row.createCell((short)25).setCellValue("Phone");
					row.createCell((short)26).setCellValue("MobileCountryCode");
					row.createCell((short)27).setCellValue("Mobile");
					row.createCell((short)28).setCellValue("email");
					row.createCell((short)29).setCellValue("passportno");
					row.createCell((short)30).setCellValue("passport_country");
					row.createCell((short)31).setCellValue("passport_validity");
					row.createCell((short)32).setCellValue("permanent_Adderss1");
					row.createCell((short)33).setCellValue("permanent_Adderss2");
					row.createCell((short)34).setCellValue("Permanent_address_city");
					row.createCell((short)35).setCellValue("permanent_address_state");
					row.createCell((short)36).setCellValue("permanent_address_country");
					row.createCell((short)37).setCellValue("permanent_address_pincode");
					row.createCell((short)38).setCellValue("current_Adderss1");
					row.createCell((short)39).setCellValue("current_Adderss2");
					row.createCell((short)40).setCellValue("current_address_city");
					row.createCell((short)41).setCellValue("current_address_state");
					row.createCell((short)42).setCellValue("current_address_country");
					row.createCell((short)43).setCellValue("current_address_pincode");
					row.createCell((short)44).setCellValue("University-board");
					row.createCell((short)45).setCellValue("Institute_Name");
					row.createCell((short)46).setCellValue("Max_Marks");
					row.createCell((short)47).setCellValue("obtained_Marks");
					row.createCell((short)48).setCellValue("Year_Of_passing");
					row.createCell((short)49).setCellValue("month_of_passing");
					row.createCell((short)50).setCellValue("No_Of_attempts");
					row.createCell((short)51).setCellValue("Exam_Name");
					row.createCell((short)52).setCellValue("Father_name");
					row.createCell((short)53).setCellValue("Father_Education");
					row.createCell((short)54).setCellValue("father_Income");
					row.createCell((short)55).setCellValue("Father_Occupation");
					row.createCell((short)56).setCellValue("Father_Income_Currencytype");
					row.createCell((short)57).setCellValue("father_email");
					row.createCell((short)58).setCellValue("Mother_name");
					row.createCell((short)59).setCellValue("Mother_Education");
					row.createCell((short)60).setCellValue("Mother_Income");
					row.createCell((short)61).setCellValue("mother_Occupation");
					row.createCell((short)62).setCellValue("Mother_Income_Currencytype");
					row.createCell((short)63).setCellValue("Mother_email");
					row.createCell((short)64).setCellValue("parent_Adderss1");
					row.createCell((short)65).setCellValue("parent_Adderss2");
					row.createCell((short)66).setCellValue("parent_Adderss3");
					row.createCell((short)67).setCellValue("parent_address_city");
					row.createCell((short)68).setCellValue("Parent_address_state");
					row.createCell((short)69).setCellValue("parent_address_country");
					row.createCell((short)70).setCellValue("parent_address_pincode");
					row.createCell((short)71).setCellValue("Parent_PhoneCountryCode");
					row.createCell((short)72).setCellValue("Parent_PhoneAreaCode");
					row.createCell((short)73).setCellValue("Parent_Phone");
					row.createCell((short)74).setCellValue("parent_MobileCountryCode");
					row.createCell((short)75).setCellValue("parent_Mobile");
					row.createCell((short)76).setCellValue("Registration_Number");
					row.createCell((short)77).setCellValue("Roll_No");
					row.createCell((short)78).setCellValue("Class_Name");
					row.createCell((short)79).setCellValue("admitted_through");
					row.createCell((short)80).setCellValue("Is_Freeship");
					row.createCell((short)81).setCellValue("Subject_group1");
					row.createCell((short)82).setCellValue("Subject_group2");
					row.createCell((short)83).setCellValue("Subject_group3");
					row.createCell((short)84).setCellValue("Subject_group4");
					row.createCell((short)85).setCellValue("Subject_group5");
					row.createCell((short)86).setCellValue("Admission_date");
					row.createCell((short)87).setCellValue("Second_Language");
				}
				fos = new FileOutputStream(excelFile,true);
				wb.write(fos);
				HttpSession session = request.getSession();
				session.setAttribute(CMSConstants.EXCEL_BYTES, wb.getBytes());
				fos.flush();
				fos.close();
			}catch(Exception e){
				//log

			}
			
		}
		public static void exportTOCSV(UploadFormatForm uploadFormatForm,HttpServletRequest request) throws Exception{
			Properties prop = new Properties();
			try {
				InputStream inputStream = CommonUtil.class.getClassLoader()
						.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				prop.load(inputStream);
			} 
			catch (IOException e) {
				log.error("Error occured at exportTOCSV of UploadFormatHelper ",e);
				throw new IOException(e);
			}
			String destination=prop.getProperty(CMSConstants.UPLOAD_EXCEL_DESTINATION);
			File excelFile = new File(request.getRealPath("")+ "//TempFiles//"+destination);	
			if(excelFile.exists()){
				excelFile.delete();
			}
			FileOutputStream fos = null;
			HSSFWorkbook wb = null;
			HSSFSheet sheet =null;
			HSSFRow row = null;
			HSSFCell cell = null;
			int count = 0;
			
			try	{
				wb = new HSSFWorkbook();
				HSSFCellStyle cellStyle = wb.createCellStyle();
				cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("dd/MM/yyyy"));
				sheet = wb.createSheet(uploadFormatForm.getUploadFormatFile());
				row = sheet.createRow(count);
				count = sheet.getFirstRowNum();
				if(uploadFormatForm.getUploadFormatFile().equalsIgnoreCase("mobileFormat")){
						uploadFormatForm.setFileNo("1");
						row.createCell((short)0).setCellValue(CMSConstants.ROLLNO);
						row.createCell((short)1).setCellValue("MobileCountryCode");
						row.createCell((short)2).setCellValue("Mobile");
						row.createCell((short)3).setCellValue("ParentMobileCountryCode");
						row.createCell((short)4).setCellValue("ParentMobile");
						row.createCell((short)5).setCellValue("PhoneCountryCode");
						row.createCell((short)6).setCellValue("PhoneAreaCode");
						row.createCell((short)7).setCellValue("Phone");
						row.createCell((short)8).setCellValue("ParentPhoneCountryCode");
						row.createCell((short)9).setCellValue("ParentPhoneAreaCode");
						row.createCell((short)10).setCellValue(CMSConstants.PARENT_PHONE);
					}
				if(uploadFormatForm.getUploadFormatFile().equalsIgnoreCase("secondLanguage")){
						uploadFormatForm.setFileNo("2");
						row.createCell((short)0).setCellValue("RegisterNo");
						row.createCell((short)1).setCellValue("SecondLanguage");
					}
				if(uploadFormatForm.getUploadFormatFile().equalsIgnoreCase("RegisterNoUpload")){
					uploadFormatForm.setFileNo("3");
					row.createCell((short)0).setCellValue("ApplicationNumber");
					row.createCell((short)1).setCellValue("RegisterNumber");
					row.createCell((short)2).setCellValue("ClassName");
					row.createCell((short)3).setCellValue("RollNumber");
				}
				if(uploadFormatForm.getUploadFormatFile().equalsIgnoreCase("OMRUpload")){
					uploadFormatForm.setFileNo("4");
					row.createCell((short)1).setCellValue("Challan_NO");
					row.createCell((short)2).setCellValue("journal_No");
					row.createCell((short)3).setCellValue("Challan_date");
					row.createCell((short)4).setCellValue("Challan_Amount");
					row.createCell((short)5).setCellValue("Bank_BranchCode");
					row.createCell((short)6).setCellValue("Application_Number");
					row.createCell((short)7).setCellValue("Academic_year");
					row.createCell((short)8).setCellValue("Name");
					row.createCell((short)9).setCellValue("Date_of_Birth");
					row.createCell((short)10).setCellValue("Place_of_Birth");
					row.createCell((short)11).setCellValue("Country_of_Birth");
					row.createCell((short)12).setCellValue("State_of_Birth");
					row.createCell((short)13).setCellValue("Nationality");
					row.createCell((short)14).setCellValue("Gender");
					row.createCell((short)15).setCellValue("Is_SportsPerson");
					row.createCell((short)16).setCellValue("Is_Handicapped");
					row.createCell((short)17).setCellValue("Blood_group");
					row.createCell((short)18).setCellValue("resident_category");
					row.createCell((short)19).setCellValue("Religion");
					row.createCell((short)20).setCellValue("Sub-religion");
					row.createCell((short)21).setCellValue("Caste");
					row.createCell((short)22).setCellValue("Area_type");
					row.createCell((short)23).setCellValue("PhoneCountryCode");
					row.createCell((short)24).setCellValue("PhoneAreaCode");
					row.createCell((short)25).setCellValue("Phone");
					row.createCell((short)26).setCellValue("MobileCountryCode");
					row.createCell((short)27).setCellValue("Mobile");
					row.createCell((short)28).setCellValue("email");
					row.createCell((short)29).setCellValue("passportno");
					row.createCell((short)30).setCellValue("passport_country");
					row.createCell((short)31).setCellValue("passport_validity");
					row.createCell((short)32).setCellValue("permanent_Adderss1");
					row.createCell((short)33).setCellValue("permanent_Adderss2");
					row.createCell((short)34).setCellValue("Permanent_address_city");
					row.createCell((short)35).setCellValue("permanent_address_state");
					row.createCell((short)36).setCellValue("permanent_address_country");
					row.createCell((short)37).setCellValue("permanent_address_pincode");
					row.createCell((short)38).setCellValue("current_Adderss1");
					row.createCell((short)39).setCellValue("current_Adderss2");
					row.createCell((short)40).setCellValue("current_address_city");
					row.createCell((short)41).setCellValue("current_address_state");
					row.createCell((short)42).setCellValue("current_address_country");
					row.createCell((short)43).setCellValue("current_address_pincode");
					row.createCell((short)44).setCellValue("University-board");
					row.createCell((short)45).setCellValue("Institute_Name");
					row.createCell((short)46).setCellValue("Max_Marks");
					row.createCell((short)47).setCellValue("obtained_Marks");
					row.createCell((short)48).setCellValue("Year_Of_passing");
					row.createCell((short)49).setCellValue("No_Of_attempts");
					row.createCell((short)50).setCellValue("Exam_Name");
					row.createCell((short)51).setCellValue("Father_name");
					row.createCell((short)52).setCellValue("Father_Education");
					row.createCell((short)53).setCellValue("father_Income");
					row.createCell((short)54).setCellValue("Father_Occupation");
					row.createCell((short)55).setCellValue("Father_Income_Currencytype");
					row.createCell((short)56).setCellValue("father_email");
					row.createCell((short)57).setCellValue("Mother_name");
					row.createCell((short)58).setCellValue("Mother_Education");
					row.createCell((short)59).setCellValue("Mother_Income");
					row.createCell((short)60).setCellValue("mother_Occupation");
					row.createCell((short)61).setCellValue("Mother_Income_Currencytype");
					row.createCell((short)62).setCellValue("Mother_email");
					row.createCell((short)63).setCellValue("parent_Adderss1");
					row.createCell((short)64).setCellValue("parent_Adderss2");
					row.createCell((short)65).setCellValue("parent_Adderss3");
					row.createCell((short)66).setCellValue("parent_address_city");
					row.createCell((short)67).setCellValue("Parent_address_state");
					row.createCell((short)68).setCellValue("parent_address_country");
					row.createCell((short)69).setCellValue("parent_address_pincode");
					row.createCell((short)70).setCellValue("Parent_PhoneCountryCode");
					row.createCell((short)71).setCellValue("Parent_PhoneAreaCode");
					row.createCell((short)72).setCellValue("Parent_Phone");
					row.createCell((short)73).setCellValue("parent_MobileCountryCode");
					row.createCell((short)74).setCellValue("parent_Mobile");
				}
				if(uploadFormatForm.getUploadFormatFile().equalsIgnoreCase("admissionDataUpload")){
					uploadFormatForm.setFileNo("5");
					row.createCell((short)1).setCellValue("Challan_NO");
					row.createCell((short)2).setCellValue("journal_No");
					row.createCell((short)3).setCellValue("Challan_date");
					row.createCell((short)4).setCellValue("Challan_Amount");
					row.createCell((short)5).setCellValue("Bank_BranchCode");
					row.createCell((short)6).setCellValue("Application_Number");
					row.createCell((short)7).setCellValue("Academic_year");
					row.createCell((short)8).setCellValue("Name");
					row.createCell((short)9).setCellValue("Date_of_Birth");
					row.createCell((short)10).setCellValue("Place_of_Birth");
					row.createCell((short)11).setCellValue("Country_of_Birth");
					row.createCell((short)12).setCellValue("State_of_Birth");
					row.createCell((short)13).setCellValue("Nationality");
					row.createCell((short)14).setCellValue("Gender");
					row.createCell((short)15).setCellValue("Is_SportsPerson");
					row.createCell((short)16).setCellValue("Is_Handicapped");
					row.createCell((short)17).setCellValue("Blood_group");
					row.createCell((short)18).setCellValue("resident_category");
					row.createCell((short)19).setCellValue("Religion");
					row.createCell((short)20).setCellValue("Sub-religion");
					row.createCell((short)21).setCellValue("Caste");
					row.createCell((short)22).setCellValue("Area_type");
					row.createCell((short)23).setCellValue("PhoneCountryCode");
					row.createCell((short)24).setCellValue("PhoneAreaCode");
					row.createCell((short)25).setCellValue("Phone");
					row.createCell((short)26).setCellValue("MobileCountryCode");
					row.createCell((short)27).setCellValue("Mobile");
					row.createCell((short)28).setCellValue("email");
					row.createCell((short)29).setCellValue("passportno");
					row.createCell((short)30).setCellValue("passport_country");
					row.createCell((short)31).setCellValue("passport_validity");
					row.createCell((short)32).setCellValue("permanent_Adderss1");
					row.createCell((short)33).setCellValue("permanent_Adderss2");
					row.createCell((short)34).setCellValue("Permanent_address_city");
					row.createCell((short)35).setCellValue("permanent_address_state");
					row.createCell((short)36).setCellValue("permanent_address_country");
					row.createCell((short)37).setCellValue("permanent_address_pincode");
					row.createCell((short)38).setCellValue("current_Adderss1");
					row.createCell((short)39).setCellValue("current_Adderss2");
					row.createCell((short)40).setCellValue("current_address_city");
					row.createCell((short)41).setCellValue("current_address_state");
					row.createCell((short)42).setCellValue("current_address_country");
					row.createCell((short)43).setCellValue("current_address_pincode");
					row.createCell((short)44).setCellValue("University-board");
					row.createCell((short)45).setCellValue("Institute_Name");
					row.createCell((short)46).setCellValue("Max_Marks");
					row.createCell((short)47).setCellValue("obtained_Marks");
					row.createCell((short)48).setCellValue("Year_Of_passing");
					row.createCell((short)49).setCellValue("month_of_passing");
					row.createCell((short)50).setCellValue("No_Of_attempts");
					row.createCell((short)51).setCellValue("Exam_Name");
					row.createCell((short)52).setCellValue("Father_name");
					row.createCell((short)53).setCellValue("Father_Education");
					row.createCell((short)54).setCellValue("father_Income");
					row.createCell((short)55).setCellValue("Father_Occupation");
					row.createCell((short)56).setCellValue("Father_Income_Currencytype");
					row.createCell((short)57).setCellValue("father_email");
					row.createCell((short)58).setCellValue("Mother_name");
					row.createCell((short)59).setCellValue("Mother_Education");
					row.createCell((short)60).setCellValue("Mother_Income");
					row.createCell((short)61).setCellValue("mother_Occupation");
					row.createCell((short)62).setCellValue("Mother_Income_Currencytype");
					row.createCell((short)63).setCellValue("Mother_email");
					row.createCell((short)64).setCellValue("parent_Adderss1");
					row.createCell((short)65).setCellValue("parent_Adderss2");
					row.createCell((short)66).setCellValue("parent_Adderss3");
					row.createCell((short)67).setCellValue("parent_address_city");
					row.createCell((short)68).setCellValue("Parent_address_state");
					row.createCell((short)69).setCellValue("parent_address_country");
					row.createCell((short)70).setCellValue("parent_address_pincode");
					row.createCell((short)71).setCellValue("Parent_PhoneCountryCode");
					row.createCell((short)72).setCellValue("Parent_PhoneAreaCode");
					row.createCell((short)73).setCellValue("Parent_Phone");
					row.createCell((short)74).setCellValue("parent_MobileCountryCode");
					row.createCell((short)75).setCellValue("parent_Mobile");
					row.createCell((short)76).setCellValue("Registration_Number");
					row.createCell((short)77).setCellValue("Roll_No");
					row.createCell((short)78).setCellValue("Class_Name");
					row.createCell((short)79).setCellValue("admitted_through");
					row.createCell((short)80).setCellValue("Is_Freeship");
					row.createCell((short)81).setCellValue("Subject_group1");
					row.createCell((short)82).setCellValue("Subject_group2");
					row.createCell((short)83).setCellValue("Subject_group3");
					row.createCell((short)84).setCellValue("Subject_group4");
					row.createCell((short)85).setCellValue("Subject_group5");
					row.createCell((short)86).setCellValue("Admission_date");
					row.createCell((short)87).setCellValue("Second_Language");
				}
			fos = new FileOutputStream(excelFile,true);
			wb.write(fos);
			fos.flush();
			fos.close();
			
			String csvdestination = prop.getProperty("knowledgepro.report.csvdestionation");
			//File to store data in form of CSV
			File fCSV = new File(request.getRealPath("")+ "//TempFiles//"+csvdestination);

			OutputStream os = (OutputStream)new FileOutputStream(fCSV);
			
			String encoding = "UTF8";
			OutputStreamWriter osw = new OutputStreamWriter(os, encoding);
			BufferedWriter bWriter = new BufferedWriter(osw);

			//Excel document to be imported
			WorkbookSettings ws = new WorkbookSettings();
			ws.setLocale(new Locale("en", "EN"));
			Workbook w = Workbook.getWorkbook(new File(request.getRealPath("")+ "//TempFiles//"+destination),ws);

			// Gets the sheets from workbook
			for (int scount = 0; scount < w.getNumberOfSheets(); scount++)
			{
			Sheet s = w.getSheet(scount);


			Cell[] rowcsv = null;

			// Gets the cells from sheet
			for (int i = 0 ; i < s.getRows() ; i++)
			{
				rowcsv = s.getRow(i);

			if (rowcsv.length >= 0)
			{
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
			
		      File file1 = new File(request.getRealPath("")+ "//TempFiles//"+csvdestination);
		      FileInputStream fileIn = new FileInputStream(file1);		
		      byte[] outputByte = new byte[fileIn.available()];
		      fileIn.read(outputByte);
		      HttpSession session = request.getSession();
		      session.setAttribute(CMSConstants.CSV_BYTES, outputByte);
	
		}catch(Exception e){
			
			//log

		}
	}
		 
}
