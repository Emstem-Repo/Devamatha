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

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admission.AdmissionTcDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.AdmissionTcDetailsForm;
import com.kp.cms.helpers.admin.AdmMeritHelper;
import com.kp.cms.to.admin.AdmMeritTO;
import com.kp.cms.to.admission.AdmissionTcDetailsReportTo;
import com.kp.cms.to.admission.AdmissionTcDetailsTo;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class AdmissionTcDetailsHelper {
	private static volatile AdmissionTcDetailsHelper admissionTcDetailsHelper = null;
	 private static final String DISPLAY = "display";
	public static AdmissionTcDetailsHelper getInstance(){
		if(admissionTcDetailsHelper == null){
			admissionTcDetailsHelper = new AdmissionTcDetailsHelper();
			return admissionTcDetailsHelper;
		}
		return admissionTcDetailsHelper;
	}
	/**
	 * @param admissionTcDetailsTo
	 * @return
	 */
	public List<AdmissionTcDetails> convertToTOBo( List<AdmissionTcDetailsTo> admissionTcDetailsTo) {
		List<AdmissionTcDetails> tcDetailsList = new ArrayList<AdmissionTcDetails>();
		if(admissionTcDetailsTo!=null){
			Iterator<AdmissionTcDetailsTo> iterator = admissionTcDetailsTo.iterator();
			while (iterator.hasNext()) {
				AdmissionTcDetailsTo admTcDetailsTo = (AdmissionTcDetailsTo) iterator .next();
				AdmissionTcDetails detailsBo = new AdmissionTcDetails();
				if(admTcDetailsTo.getRegNo()!=null && !admTcDetailsTo.getRegNo().isEmpty()){
					detailsBo.setRegNo(admTcDetailsTo.getRegNo());
				}
				if(admTcDetailsTo.getDateAdm()!=null && !admTcDetailsTo.getDateAdm().toString().isEmpty()){
					detailsBo.setDateAdm(admTcDetailsTo.getDateAdm());
				}
				if(admTcDetailsTo.getOrderNo()!= null && !admTcDetailsTo.getOrderNo().isEmpty()){
					detailsBo.setOrderNo(admTcDetailsTo.getOrderNo());
				}
				if(admTcDetailsTo.getOrderDate()!=null && !admTcDetailsTo.getOrderDate().toString().isEmpty()){
					detailsBo.setOrderDate(admTcDetailsTo.getOrderDate());
				}
				if(admTcDetailsTo.getName()!=null && !admTcDetailsTo.getName().isEmpty()){
					detailsBo.setName(admTcDetailsTo.getName());
				}
				if(admTcDetailsTo.getSex()!=null && !admTcDetailsTo.getSex().isEmpty()){
					detailsBo.setSex(admTcDetailsTo.getSex());
				}
				if(admTcDetailsTo.getFatherName()!=null && !admTcDetailsTo.getFatherName().isEmpty()){
					detailsBo.setFatherName(admTcDetailsTo.getFatherName());
				}
				if(admTcDetailsTo.getMotherName()!=null && !admTcDetailsTo.getMotherName().isEmpty()){
					detailsBo.setMotherName(admTcDetailsTo.getMotherName());
				}
				if(admTcDetailsTo.getNationality()!=null &&!admTcDetailsTo.getNationality().isEmpty()){
					detailsBo.setNationality(admTcDetailsTo.getNationality());
				}
				if(admTcDetailsTo.getReligion()!=null && !admTcDetailsTo.getReligion().isEmpty()){
					detailsBo.setReligion(admTcDetailsTo.getReligion());
				}
				if(admTcDetailsTo.getScStBcBt()!=null && !admTcDetailsTo.getScStBcBt().isEmpty()){
					detailsBo.setScStBcBt(admTcDetailsTo.getScStBcBt());
				}
				if(admTcDetailsTo.getDateOfBirth()!=null && !admTcDetailsTo.getDateOfBirth().toString().isEmpty()){
					detailsBo.setDateOfBirth(admTcDetailsTo.getDateOfBirth());
				}
				if(admTcDetailsTo.getClasses()!=null && !admTcDetailsTo.getClasses().isEmpty()){
					detailsBo.setClasses(admTcDetailsTo.getClasses());
				}
				if(admTcDetailsTo.getSection()!=null && !admTcDetailsTo.getSection().isEmpty()){
					detailsBo.setSection(admTcDetailsTo.getSection());
				}
				if(admTcDetailsTo.getYear()!=null && !admTcDetailsTo.getYear().isEmpty()){
					detailsBo.setYear(admTcDetailsTo.getYear());
				}
				if(admTcDetailsTo.getSecndLang()!=null && !admTcDetailsTo.getSecndLang().isEmpty()){
					detailsBo.setSecndLang(admTcDetailsTo.getSecndLang());
				}
				if(admTcDetailsTo.getSubject1()!=null && !admTcDetailsTo.getSubject1().isEmpty()){
					detailsBo.setSubject1(admTcDetailsTo.getSubject1());
				}
				if(admTcDetailsTo.getSubject2()!=null && !admTcDetailsTo.getSubject2().isEmpty()){
					detailsBo.setSubject2(admTcDetailsTo.getSubject2());
				}
				if(admTcDetailsTo.getSubject3()!=null && !admTcDetailsTo.getSubject3().isEmpty()){
					detailsBo.setSubject3(admTcDetailsTo.getSubject3());
				}
				if(admTcDetailsTo.getSubject4()!=null && !admTcDetailsTo.getSubject4().isEmpty()){
					detailsBo.setSubject4(admTcDetailsTo.getSubject4());
				}
				if(admTcDetailsTo.getPublicExam()!=null && !admTcDetailsTo.getPublicExam().isEmpty()){
					detailsBo.setPublicExam(admTcDetailsTo.getPublicExam());
				}
				if(admTcDetailsTo.getExmMonyr()!=null && !admTcDetailsTo.getExmMonyr().isEmpty()){
					detailsBo.setExmMonyr(admTcDetailsTo.getExmMonyr());
				}
				if(admTcDetailsTo.getExmRegNo()!=null && !admTcDetailsTo.getExmRegNo().isEmpty()){
					detailsBo.setExmRegNo(admTcDetailsTo.getExmRegNo());
				}
				if(admTcDetailsTo.getPassed()!=null && !admTcDetailsTo.getPassed().isEmpty()){
					if(admTcDetailsTo.getPassed().equalsIgnoreCase("TRUE")){
						detailsBo.setPassed(true);
					}
					else{
						detailsBo.setPassed(false);
					}
				}
				if(admTcDetailsTo.getPassedLang()!=null && !admTcDetailsTo.getPassedLang().isEmpty()){
					detailsBo.setPassedLang(admTcDetailsTo.getPassedLang());
				}
				if(admTcDetailsTo.getPassedSubj()!=null && !admTcDetailsTo.getPassedSubj().isEmpty()){
					detailsBo.setPassedSubj(admTcDetailsTo.getPassedSubj());
				}
				if(admTcDetailsTo.getGovtSchlp()!=null && !admTcDetailsTo.getGovtSchlp().isEmpty()){
					if(admTcDetailsTo.getGovtSchlp().equalsIgnoreCase("TRUE")){
						detailsBo.setGovtSchlp(true);
					}
					else{
						detailsBo.setGovtSchlp(false);
					}
				}
				if(admTcDetailsTo.getFreeShip()!=null && !admTcDetailsTo.getFreeShip().isEmpty()){
					if(admTcDetailsTo.getFreeShip().equalsIgnoreCase("TRUE")){
						detailsBo.setFreeShip(true);
					}else{
						detailsBo.setFreeShip(false);
					}
				}
				if(admTcDetailsTo.getLastAttnDt()!=null && !admTcDetailsTo.getLastAttnDt().isEmpty()){
					detailsBo.setLastAttnDt(admTcDetailsTo.getLastAttnDt());
				}
				if(admTcDetailsTo.getFeesPaid()!=null && !admTcDetailsTo.getFeesPaid().isEmpty()){
					if(admTcDetailsTo.getFeesPaid().equalsIgnoreCase("TRUE")){
						detailsBo.setFeesPaid(true);
					}else{
						detailsBo.setFeesPaid(false);
					}
				}
				if(admTcDetailsTo.getDegClass()!=null && !admTcDetailsTo.getDegClass().isEmpty()){
					detailsBo.setDegClass(admTcDetailsTo.getDegClass());
				}
				if(admTcDetailsTo.getFirstYearMrk()!=null && !admTcDetailsTo.getFirstYearMrk().isEmpty()){
					detailsBo.setFirstYearMrk(Integer.parseInt(admTcDetailsTo.getFirstYearMrk()));
				}
				if(admTcDetailsTo.getSecondYearMrk()!=null && !admTcDetailsTo.getSecondYearMrk().isEmpty()){
					detailsBo.setSecondYearMrk(Integer.parseInt(admTcDetailsTo.getSecondYearMrk()));
				}
				if(admTcDetailsTo.getThirdYearMrk()!=null && !admTcDetailsTo.getThirdYearMrk().isEmpty()){
					detailsBo.setThirdYearMrk(Integer.parseInt(admTcDetailsTo.getThirdYearMrk()));
				}
				if(admTcDetailsTo.getFourthYearMrk()!=null && !admTcDetailsTo.getFourthYearMrk().isEmpty()){
					detailsBo.setFourthYearMrk(Integer.parseInt(admTcDetailsTo.getFourthYearMrk()));
				}
				if(admTcDetailsTo.getOutOfMrk()!=null && !admTcDetailsTo.getOutOfMrk().isEmpty()){
					detailsBo.setOutOfMrk(Integer.parseInt(admTcDetailsTo.getOutOfMrk()));
				}
				if(admTcDetailsTo.getLeftStudnt()!=null && !admTcDetailsTo.getLeftStudnt().isEmpty()){
					if(admTcDetailsTo.getLeftStudnt().equalsIgnoreCase("TRUE")){
						detailsBo.setLeftStudnt(true);
					}
					else{
						detailsBo.setLeftStudnt(false);
					}
				}
				if(admTcDetailsTo.getDateLeave()!=null && !admTcDetailsTo.getDateLeave().toString().isEmpty()){
					detailsBo.setDateLeave(admTcDetailsTo.getDateLeave());
				}
				if(admTcDetailsTo.getReasonLeave()!=null && !admTcDetailsTo.getReasonLeave().isEmpty()){
					detailsBo.setReasonLeave(admTcDetailsTo.getReasonLeave());
				}
				if(admTcDetailsTo.getCaste()!=null && !admTcDetailsTo.getCaste().isEmpty()){
					detailsBo.setCaste(admTcDetailsTo.getCaste());
				}
				if(admTcDetailsTo.getTcGiven()!=null && !admTcDetailsTo.getTcGiven().isEmpty()){
					if(admTcDetailsTo.getTcGiven().equalsIgnoreCase("TRUE")){
						detailsBo.setTcGiven(true);
					}
					else{
						detailsBo.setTcGiven(false);
					}
				}
				if(admTcDetailsTo.getAcademicYear()!=null && !admTcDetailsTo.getAcademicYear().isEmpty()){
					detailsBo.setAcademicYear(admTcDetailsTo.getAcademicYear());
				}
				tcDetailsList.add(detailsBo);
			}
		} 
		return tcDetailsList;
	}
	/**
	 * @param tcDetailsForm
	 * @return
	 */
	public StringBuffer selectedQuery(AdmissionTcDetailsForm tcDetailsForm)throws Exception {
		StringBuffer stringBuffer = new StringBuffer("from AdmissionTcDetails tcDetails where");
		if(tcDetailsForm.getAcademicYear()!=null && !tcDetailsForm.getAcademicYear().isEmpty()){
			stringBuffer = stringBuffer.append(" tcDetails.academicYear='"+tcDetailsForm.getAcademicYear()+"'");
		}	
			Session session = null;
			Transaction tx =null;
			try{
				session = HibernateUtil.getSession();
				tx=session.beginTransaction();
				stringBuffer = stringBuffer.append(" and tcDetails.classes='"+tcDetailsForm.getCourseName()+"'");
			}catch (Exception e) {
				throw new ApplicationException(e);
		}
		return stringBuffer;
	}
	/**
	 * @param tcDetails
	 * @return
	 * @throws Exception
	 */
	public List<AdmissionTcDetailsTo> populateBOToTO( List<AdmissionTcDetails> tcDetails)throws Exception {
		List<AdmissionTcDetailsTo> listTo = new ArrayList<AdmissionTcDetailsTo>();
		if(tcDetails!=null){
			Iterator<AdmissionTcDetails> iterator = tcDetails.iterator();
			while (iterator.hasNext()) {
				AdmissionTcDetails admissionTcDetails = (AdmissionTcDetails) iterator .next();
				AdmissionTcDetailsTo tcDetailsTo = new AdmissionTcDetailsTo();
				if(admissionTcDetails.getRegNo()!=null && !admissionTcDetails.getRegNo().isEmpty()){
					tcDetailsTo.setRegNo(admissionTcDetails.getRegNo());
				}
				if(admissionTcDetails.getName()!=null && !admissionTcDetails.getName().isEmpty()){
					tcDetailsTo.setName(admissionTcDetails.getName());
				}
				if(admissionTcDetails.getDateAdm()!=null && !admissionTcDetails.getDateAdm().toString().isEmpty()){
					tcDetailsTo.setDateAdm1(CommonUtil.getStringDate(admissionTcDetails.getDateAdm()));
				}
				if(admissionTcDetails.getOrderNo()!=null && !admissionTcDetails.getOrderNo().isEmpty()){
					tcDetailsTo.setOrderNo(admissionTcDetails.getOrderNo());
				}
				if(admissionTcDetails.getOrderDate()!=null && !admissionTcDetails.getOrderDate().toString().isEmpty()){
					tcDetailsTo.setTcDate(CommonUtil.getStringDate(admissionTcDetails.getOrderDate()));
				}
				if(admissionTcDetails.getSex()!=null && !admissionTcDetails.getSex().isEmpty()){
					tcDetailsTo.setSex(admissionTcDetails.getSex());
				}
				if(admissionTcDetails.getFatherName()!=null && !admissionTcDetails.getFatherName().isEmpty()){
					tcDetailsTo.setFatherName(admissionTcDetails.getFatherName());
				}
				if(admissionTcDetails.getNationality()!=null &&!admissionTcDetails.getNationality().isEmpty()){
					tcDetailsTo.setNationality(admissionTcDetails.getNationality());
				}
				if(admissionTcDetails.getReligion()!=null && !admissionTcDetails.getReligion().isEmpty()){
					tcDetailsTo.setReligion(admissionTcDetails.getReligion());
				}
				if(admissionTcDetails.getScStBcBt()!=null && !admissionTcDetails.getScStBcBt().isEmpty()){
					tcDetailsTo.setScStBcBt(admissionTcDetails.getScStBcBt());
				}
				if(admissionTcDetails.getDateOfBirth()!=null && !admissionTcDetails.getDateOfBirth().toString().isEmpty()){
					tcDetailsTo.setDob1(CommonUtil.getStringDate(admissionTcDetails.getDateOfBirth()));
				}
				String classes = "";
				if(admissionTcDetails.getClasses()!=null && !admissionTcDetails.getClasses().isEmpty()){
					String cls = admissionTcDetails.getClasses();
					classes = classes + cls;
				}
				if(admissionTcDetails.getYear()!=null && !admissionTcDetails.getYear().isEmpty()){
					String year = admissionTcDetails.getYear();
					classes = year + classes;
				}
				if(admissionTcDetails.getSection()!=null && !admissionTcDetails.getSection().isEmpty()){
					String section = admissionTcDetails.getSection();
					classes = classes + section;
				}
				tcDetailsTo.setClasses(classes);
				if(admissionTcDetails.getSecndLang()!=null && !admissionTcDetails.getSecndLang().isEmpty()){
					tcDetailsTo.setSecndLang(admissionTcDetails.getSecndLang());
				}
				if(admissionTcDetails.getSubject1()!=null && !admissionTcDetails.getSubject1().isEmpty()){
					tcDetailsTo.setSubject1(admissionTcDetails.getSubject1());
				}
				if(admissionTcDetails.getSubject2()!=null && !admissionTcDetails.getSubject2().isEmpty()){
					tcDetailsTo.setSubject2(admissionTcDetails.getSubject2());
				}
				if(admissionTcDetails.getSubject3()!=null && !admissionTcDetails.getSubject3().isEmpty()){
					tcDetailsTo.setSubject3(admissionTcDetails.getSubject3());
				}
				if(admissionTcDetails.getSubject4()!=null && !admissionTcDetails.getSubject4().isEmpty()){
					tcDetailsTo.setSubject4(admissionTcDetails.getSubject4());
				}
				if(admissionTcDetails.getPublicExam()!=null && !admissionTcDetails.getPublicExam().isEmpty()){
					tcDetailsTo.setPublicExam(admissionTcDetails.getPublicExam());
				}
				if(admissionTcDetails.getExmRegNo()!=null && !admissionTcDetails.getExmRegNo().isEmpty()){
					tcDetailsTo.setExmRegNo(admissionTcDetails.getExmRegNo());
				}
				if(admissionTcDetails.getExmMonyr()!=null && !admissionTcDetails.getExmMonyr().isEmpty()){
					tcDetailsTo.setExmMonyr(admissionTcDetails.getExmMonyr());
				}
				if(admissionTcDetails.getPassed()!=null && !admissionTcDetails.getPassed().toString().isEmpty()){
					if(admissionTcDetails.getPassed()){
						tcDetailsTo.setPassed("TRUE");
					}else{
						tcDetailsTo.setPassed("FALSE");
					}
				}
				if(admissionTcDetails.getPassedLang()!=null && !admissionTcDetails.getPassedLang().isEmpty()){
					tcDetailsTo.setPassedLang(admissionTcDetails.getPassedLang());
				}
				if(admissionTcDetails.getPassedSubj()!=null && !admissionTcDetails.getPassedSubj().isEmpty()){
					tcDetailsTo.setPassedSubj(admissionTcDetails.getPassedSubj());
				}
				if(admissionTcDetails.getGovtSchlp()!=null){
					if(!admissionTcDetails.getGovtSchlp().toString().isEmpty()){
						if(admissionTcDetails.getGovtSchlp()){
							tcDetailsTo.setGovtSchlp("TRUE");
						}else{
							tcDetailsTo.setGovtSchlp("FALSE");
						}
					}
				}
				if(admissionTcDetails.getFreeShip()!=null){
					if(!admissionTcDetails.getFreeShip().toString().isEmpty()){
						if(admissionTcDetails.getFreeShip()){
							tcDetailsTo.setFreeShip("TRUE");
							}else{
								tcDetailsTo.setFreeShip("FALSE");
						}
					}
				}
				
				if(admissionTcDetails.getFeesPaid()!=null ){
					if(!admissionTcDetails.getFeesPaid().toString().isEmpty()){
						if(admissionTcDetails.getFeesPaid()){
							tcDetailsTo.setFeesPaid("TRUE");
						}else{
							tcDetailsTo.setFeesPaid("FALSE");
						}
					}
				}
				if(admissionTcDetails.getDegClass()!=null && !admissionTcDetails.getDegClass().isEmpty()){
					tcDetailsTo.setDegClass(admissionTcDetails.getDegClass());
				}
				
				if(admissionTcDetails.getDateLeave()!=null && !admissionTcDetails.getDateLeave().toString().isEmpty()){
					tcDetailsTo.setDateLeave1(CommonUtil.getStringDate(admissionTcDetails.getDateLeave()));
				}
				if(admissionTcDetails.getReasonLeave()!=null && !admissionTcDetails.getReasonLeave().isEmpty()){
					tcDetailsTo.setReasonLeave(admissionTcDetails.getReasonLeave());
				}
				if(admissionTcDetails.getCaste()!=null && !admissionTcDetails.getCaste().isEmpty()){
					tcDetailsTo.setCaste(admissionTcDetails.getCaste());
				}
				if(admissionTcDetails.getTcGiven()!=null){
					if(!admissionTcDetails.getTcGiven().toString().isEmpty()){
						if(admissionTcDetails.getTcGiven()){
							tcDetailsTo.setTcGiven("TRUE");
						}else{
							tcDetailsTo.setTcGiven("FALSE");
						}
					}
				}
				if(admissionTcDetails.getAcademicYear()!=null && !admissionTcDetails.getAcademicYear().isEmpty()){
					tcDetailsTo.setAcademicYear(admissionTcDetails.getAcademicYear());
				}
				listTo.add(tcDetailsTo);
			}
		}
		return listTo;
	}
	/**
	 * @param tcDetailsForm
	 * @return
	 * @throws Exception
	 */
	public AdmissionTcDetailsReportTo selectedColumns( AdmissionTcDetailsForm tcDetailsForm) throws Exception{
		AdmissionTcDetailsReportTo detailsReportTo = new AdmissionTcDetailsReportTo();
		List<String> selectedList = new ArrayList<String>();
		String[] selected = tcDetailsForm.getSelectedColumnsArray();
		for(int i=0;i<selected.length;i++){
			selectedList.add(selected[i]);
		}
		if(selectedList!=null){
			selectedList.add("Academic Year");
			Iterator<String> iterator = selectedList.iterator();
			int count=0;
			while (iterator.hasNext()) {
				String columnName = (String) iterator.next();
				if(columnName.equalsIgnoreCase("RegNo")){
					detailsReportTo.setRegNoDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setRegNoPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Date Adm")){
					detailsReportTo.setDateAdmDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setDateAdmPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Tc Date")){
					detailsReportTo.setTcDateDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setTcDatePos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Tc No")){
					detailsReportTo.setTcNoDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setTcNoPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Name")){
					detailsReportTo.setNameDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setNamePos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Gender")){
					detailsReportTo.setGenderDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setGenderPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Father Name")){
					detailsReportTo.setFatherNameDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setFatherNamePos((short)count++);
				}
				
				if(columnName.equalsIgnoreCase("Nationality")){
					detailsReportTo.setNationalityDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setNationalityPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Religion")){
					detailsReportTo.setReligionDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setReligionPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Category")){
					detailsReportTo.setCategoryDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setCategoryPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Date Of Birth")){
					detailsReportTo.setDobDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setDobPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Class")){
					detailsReportTo.setClassDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setClassPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Second Language")){
					detailsReportTo.setSecondLanguageDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setSecondLanguagePos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Subject1")){
					detailsReportTo.setSub1Dis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setSub1Pos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Subject2")){
					detailsReportTo.setSub2Dis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setSub2Pos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Subject3")){
					detailsReportTo.setSub3Dis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setSub3Pos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Subject4")){
					detailsReportTo.setSub4Dis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setSub4Pos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Public Exam")){
					detailsReportTo.setPublicExamDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setPublicExamPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Exam RegNo")){
					detailsReportTo.setExmRegNoDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setExmRegNoPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Exam Month Year")){
					detailsReportTo.setExmMonYrDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setExmMonYrPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Passed")){
					detailsReportTo.setPassedDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setPassedPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Passed Language")){
					detailsReportTo.setPassedLangDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setPassedLangPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Passed Subject")){
					detailsReportTo.setPassedSubjDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setPassedSubjPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Scholarship")){
					detailsReportTo.setSchlpDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setSchlpPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Free Ship")){
					detailsReportTo.setFreeShipDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setFreeShipPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Fees Paid")){
					detailsReportTo.setFeesPaidDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setFeesPaidPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Deg Class")){
					detailsReportTo.setDegClassDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setDegClassPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Date Leave")){
					detailsReportTo.setDateLeaveDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setDateLeavePos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Reason Leave")){
					detailsReportTo.setReasonLeaveDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setReasonLeavePos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Caste")){
					detailsReportTo.setCasteDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setCastePos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Tc Given")){
					detailsReportTo.setTcGivenDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setTcGivenPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Academic Year")){
					detailsReportTo.setAcademicYearDis(AdmissionTcDetailsHelper.DISPLAY);
					detailsReportTo.setAcademicYearPos((short)count++);
				}
			}
		}
		return detailsReportTo;
	}

	/**
	 * @param reportTo
	 * @param tcDetailsForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean convertToExcel(AdmissionTcDetailsReportTo reportTo,
			AdmissionTcDetailsForm tcDetailsForm, HttpServletRequest request)
			throws Exception {
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
		String fileName=prop.getProperty(CMSConstants.UPLOAD_ADM_TCDETAILS);
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
		
		if(tcDetailsForm.getAdmTcDetails()!=null){
			AdmissionTcDetailsTo to=(AdmissionTcDetailsTo)tcDetailsForm.getAdmTcDetails().get(0);
			int count = 0;
			Iterator<AdmissionTcDetailsTo> iterator = tcDetailsForm.getAdmTcDetails().iterator();
			try{
				wb=new XSSFWorkbook();
				XSSFCellStyle cellStyle=wb.createCellStyle();
				CreationHelper createHelper = wb.getCreationHelper();
				cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
				sheet = wb.createSheet("Admission TcDetails Report");
				row = sheet.createRow(count);
				count = sheet.getFirstRowNum();
				// Create cells in the row and put some data in it.
				if(reportTo.getRegNoDis()!=null && reportTo.getRegNoDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getRegNoPos()).setCellValue("Reg No");
				}
				if(reportTo.getDateAdmDis()!=null && reportTo.getDateAdmDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getDateAdmPos()).setCellValue("Date Adm");
				}
				if(reportTo.getTcNoDis()!=null && reportTo.getTcNoDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getTcNoPos()).setCellValue("Tc No");
				}
				if(reportTo.getTcDateDis()!=null && reportTo.getTcDateDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getTcDatePos()).setCellValue("Tc Date");
				}
				if(reportTo.getNameDis()!=null && reportTo.getNameDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getNamePos()).setCellValue("Name");
				}
				if(reportTo.getGenderDis()!=null && reportTo.getGenderDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getGenderPos()).setCellValue("Gender");
				}
				if(reportTo.getFatherNameDis()!=null && reportTo.getFatherNameDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getFatherNamePos()).setCellValue("Father Name");
				}
				
				if(reportTo.getNationalityDis()!=null && reportTo.getNationalityDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getNationalityPos()).setCellValue("Nationality");
				}
				if(reportTo.getReligionDis()!=null && reportTo.getReligionDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getReligionPos()).setCellValue("Religion");
				}
				if(reportTo.getCategoryDis()!=null && reportTo.getCategoryDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getCategoryPos()).setCellValue("Category");
				}
				if(reportTo.getDobDis()!=null && reportTo.getDobDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getDobPos()).setCellValue("Date Of Birth");
				}
				if(reportTo.getClassDis()!=null && reportTo.getClassDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getClassPos()).setCellValue("Class");
				}
				if(reportTo.getSecondLanguageDis()!=null && reportTo.getSecondLanguageDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getSecondLanguagePos()).setCellValue("Second Language");
				}
				if(reportTo.getSub1Dis()!=null && reportTo.getSub1Dis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getSub1Pos()).setCellValue("Subject1");
				}
				if(reportTo.getSub2Dis()!=null && reportTo.getSub2Dis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getSub2Pos()).setCellValue("Subject2");
				}
				if(reportTo.getSub3Dis()!=null && reportTo.getSub3Dis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getSub3Pos()).setCellValue("Subject3");
				}
				if(reportTo.getSub4Dis()!=null && reportTo.getSub4Dis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getSub4Pos()).setCellValue("Subject4");
				}
				if(reportTo.getPublicExamDis()!=null && reportTo.getPublicExamDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getPublicExamPos()).setCellValue("Public Exam");
				}
				if(reportTo.getExmRegNoDis()!=null && reportTo.getExmRegNoDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getExmRegNoPos()).setCellValue("Exam RegNo");
				}
				if(reportTo.getExmMonYrDis()!=null && reportTo.getExmMonYrDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getExmMonYrPos()).setCellValue("Exam Month Year");
				}
				if(reportTo.getPassedDis()!=null && reportTo.getPassedDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getPassedPos()).setCellValue("Passed");
				}
				if(reportTo.getPassedLangDis()!=null && reportTo.getPassedLangDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getPassedLangPos()).setCellValue("Passed Language");
				}
				if(reportTo.getPassedSubjDis()!=null && reportTo.getPassedSubjDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getPassedSubjPos()).setCellValue("Passed Subject");
				}
				if(reportTo.getSchlpDis()!=null && reportTo.getSchlpDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getSchlpPos()).setCellValue("ScholarShip");
				}
				if(reportTo.getFreeShipDis()!=null && reportTo.getFreeShipDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getFreeShipPos()).setCellValue("Free Ship");
				}
				if(reportTo.getFeesPaidDis()!=null && reportTo.getFeesPaidDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getFeesPaidPos()).setCellValue("Fees Paid");
				}
				if(reportTo.getDegClassDis()!=null && reportTo.getDegClassDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getDegClassPos()).setCellValue("Deg Class");
				}
				if(reportTo.getDateLeaveDis()!=null && reportTo.getDateLeaveDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getDateLeavePos()).setCellValue("Date Leave");
				}
				if(reportTo.getReasonLeaveDis()!=null && reportTo.getReasonLeaveDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getReasonLeavePos()).setCellValue("Reason Leave");
				}
				if(reportTo.getCasteDis()!=null && reportTo.getCasteDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getCastePos()).setCellValue("Caste");
				}
				if(reportTo.getTcGivenDis()!=null && reportTo.getTcGivenDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getTcGivenPos()).setCellValue("Tc Given");
				}
				if(reportTo.getAcademicYearDis()!=null && reportTo.getAcademicYearDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY)){
					row.createCell((short)reportTo.getAcademicYearPos()).setCellValue("Academic Year");
				}
				while (iterator.hasNext()) {
					AdmissionTcDetailsTo tcDetailsTo = (AdmissionTcDetailsTo) iterator.next();
					count = count +1;
					row = sheet.createRow(count);
					if(reportTo.getNameDis()!=null && reportTo.getNameDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getName()!=null){
						row.createCell((short)reportTo.getNamePos()).setCellValue(tcDetailsTo.getName());
					}
					if(reportTo.getRegNoDis()!=null && reportTo.getRegNoDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getRegNo()!=null){
						row.createCell((short)reportTo.getRegNoPos()).setCellValue(tcDetailsTo.getRegNo());
					}
					if(reportTo.getDateAdmDis()!=null && reportTo.getDateAdmDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getDateAdm1()!=null){
						row.createCell((short)reportTo.getDateAdmPos()).setCellValue(tcDetailsTo.getDateAdm1());
					}
					if(reportTo.getTcNoDis()!=null && reportTo.getTcNoDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getOrderNo()!=null){
						row.createCell((short)reportTo.getTcNoPos()).setCellValue(tcDetailsTo.getOrderNo());
					}
					if(reportTo.getTcDateDis()!=null && reportTo.getTcDateDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getTcDate()!=null){
						row.createCell((short)reportTo.getTcDatePos()).setCellValue(tcDetailsTo.getTcDate());
					}
					if(reportTo.getGenderDis()!=null && reportTo.getGenderDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getSex()!=null){
						row.createCell((short)reportTo.getGenderPos()).setCellValue(tcDetailsTo.getSex());
					}
					if(reportTo.getFatherNameDis()!=null && reportTo.getFatherNameDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getFatherName()!=null){
						row.createCell((short)reportTo.getFatherNamePos()).setCellValue(tcDetailsTo.getFatherName());
					}
					
					if(reportTo.getNationalityDis()!=null && reportTo.getNationalityDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getNationality()!=null){
						row.createCell((short)reportTo.getNationalityPos()).setCellValue(tcDetailsTo.getNationality());
					}
					if(reportTo.getReligionDis()!=null && reportTo.getReligionDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getReligion()!=null){
						row.createCell((short)reportTo.getReligionPos()).setCellValue(tcDetailsTo.getReligion());
					}
					if(reportTo.getCategoryDis()!=null && reportTo.getCategoryDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getScStBcBt()!=null){
						row.createCell((short)reportTo.getCategoryPos()).setCellValue(tcDetailsTo.getScStBcBt());
					}
					if(reportTo.getDobDis()!=null && reportTo.getDobDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getDob1()!=null){
						row.createCell((short)reportTo.getDobPos()).setCellValue(tcDetailsTo.getDob1());
					}
					if(reportTo.getClassDis()!=null && reportTo.getClassDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getClasses()!=null){
						row.createCell((short)reportTo.getClassPos()).setCellValue(tcDetailsTo.getClasses());
					}
					if(reportTo.getSecondLanguageDis()!=null && reportTo.getSecondLanguageDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getSecndLang()!=null){
						row.createCell((short)reportTo.getSecondLanguagePos()).setCellValue(tcDetailsTo.getSecndLang());
					}
					if(reportTo.getSub1Dis()!=null && reportTo.getSub1Dis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getSubject1()!=null){
						row.createCell((short)reportTo.getSub1Pos()).setCellValue(tcDetailsTo.getSubject1());
					}
					if(reportTo.getSub2Dis()!=null && reportTo.getSub2Dis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getSubject2()!=null){
						row.createCell((short)reportTo.getSub2Pos()).setCellValue(tcDetailsTo.getSubject2());
					}
					if(reportTo.getSub3Dis()!=null && reportTo.getSub3Dis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getSubject3()!=null){
						row.createCell((short)reportTo.getSub3Pos()).setCellValue(tcDetailsTo.getSubject3());
					}
					if(reportTo.getSub4Dis()!=null && reportTo.getSub4Dis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getSubject4()!=null){
						row.createCell((short)reportTo.getSub4Pos()).setCellValue(tcDetailsTo.getSubject4());
					}
					if(reportTo.getPublicExamDis()!=null && reportTo.getPublicExamDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getPublicExam()!=null){
						row.createCell((short)reportTo.getPublicExamPos()).setCellValue(tcDetailsTo.getPublicExam());
					}
					if(reportTo.getExmRegNoDis()!=null && reportTo.getExmRegNoDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getExmRegNo()!=null){
						row.createCell((short)reportTo.getExmRegNoPos()).setCellValue(tcDetailsTo.getExmRegNo());
					}
					if(reportTo.getExmMonYrDis()!=null && reportTo.getExmMonYrDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getExmMonyr()!=null){
						row.createCell((short)reportTo.getExmMonYrPos()).setCellValue(tcDetailsTo.getExmMonyr());
					}
					if(reportTo.getPassedDis()!=null && reportTo.getPassedDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getPassed()!=null){
						row.createCell((short)reportTo.getPassedPos()).setCellValue(tcDetailsTo.getPassed());
					}
					if(reportTo.getPassedLangDis()!=null && reportTo.getPassedLangDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getPassedLang()!=null){
						row.createCell((short)reportTo.getPassedLangPos()).setCellValue(tcDetailsTo.getPassedLang());
					}
					if(reportTo.getPassedSubjDis()!=null && reportTo.getPassedSubjDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getPassedSubj()!=null){
						row.createCell((short)reportTo.getPassedSubjPos()).setCellValue(tcDetailsTo.getPassedSubj());
					}
					if(reportTo.getSchlpDis()!=null && reportTo.getSchlpDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getGovtSchlp()!=null){
						row.createCell((short)reportTo.getSchlpPos()).setCellValue(tcDetailsTo.getGovtSchlp());
					}
					if(reportTo.getFreeShipDis()!=null && reportTo.getFreeShipDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getFreeShip()!=null){
						row.createCell((short)reportTo.getFreeShipPos()).setCellValue(tcDetailsTo.getFreeShip());
					}
					if(reportTo.getFeesPaidDis()!=null && reportTo.getFeesPaidDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getFeesPaid()!=null){
						row.createCell((short)reportTo.getFeesPaidPos()).setCellValue(tcDetailsTo.getFeesPaid());
					}
					if(reportTo.getDegClassDis()!=null && reportTo.getDegClassDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getDegClass()!=null){
						row.createCell((short)reportTo.getDegClassPos()).setCellValue(tcDetailsTo.getDegClass());
					}
					if(reportTo.getDateLeaveDis()!=null && reportTo.getDateLeaveDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getDateLeave1()!=null){
						row.createCell((short)reportTo.getDateLeavePos()).setCellValue(tcDetailsTo.getDateLeave1());
					}
					if(reportTo.getReasonLeaveDis()!=null && reportTo.getReasonLeaveDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getReasonLeave()!=null){
						row.createCell((short)reportTo.getReasonLeavePos()).setCellValue(tcDetailsTo.getReasonLeave());
					}
					if(reportTo.getCasteDis()!=null && reportTo.getCasteDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getCaste()!=null){
						row.createCell((short)reportTo.getCastePos()).setCellValue(tcDetailsTo.getCaste());
					}
					if(reportTo.getTcGivenDis()!=null && reportTo.getTcGivenDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getTcGiven()!=null){
						row.createCell((short)reportTo.getTcGivenPos()).setCellValue(tcDetailsTo.getTcGiven());
					}
					if(reportTo.getAcademicYearDis()!=null && reportTo.getAcademicYearDis().equalsIgnoreCase(AdmissionTcDetailsHelper.DISPLAY) && tcDetailsTo.getAcademicYear()!=null){
						row.createCell((short)reportTo.getAcademicYearPos()).setCellValue(tcDetailsTo.getAcademicYear());
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
