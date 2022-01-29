package com.kp.cms.helpers.admin;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.AdmBioDataCJC;
import com.kp.cms.to.admin.AdmBioDataCJCTo;
import com.kp.cms.utilities.CommonUtil;

public class AdmBioDataCJCHelper {
	private static volatile AdmBioDataCJCHelper admBioDataCJCHelper = null;
	private AdmBioDataCJCHelper(){
		
	}
	public static AdmBioDataCJCHelper getInstance(){
		if(admBioDataCJCHelper == null){
			admBioDataCJCHelper = new AdmBioDataCJCHelper();
			return admBioDataCJCHelper;
		}
		return admBioDataCJCHelper;
	}
	/**
	 * @param admBioDataToLList
	 * @return
	 */
	public List<AdmBioDataCJC> populateToTOBO( List<AdmBioDataCJCTo> admBioDataToLList) {
		List<AdmBioDataCJC> admBioDataList= new ArrayList<AdmBioDataCJC>();
		if(admBioDataToLList!=null){
			Iterator<AdmBioDataCJCTo> iterator = admBioDataToLList.iterator();
			while (iterator.hasNext()) {
				AdmBioDataCJCTo admBioDataCJCTo = (AdmBioDataCJCTo) iterator .next();
				AdmBioDataCJC admBioData = new AdmBioDataCJC();
				if(admBioDataCJCTo.getApplnNo()!=null && !admBioDataCJCTo.getApplnNo().isEmpty()){
					admBioData.setApplnNo(Integer.parseInt(admBioDataCJCTo.getApplnNo()));
				}
				if(admBioDataCJCTo.getPercentage()!=null && !admBioDataCJCTo.getPercentage().isEmpty()){
					admBioData.setPercentage(Float.parseFloat(admBioDataCJCTo.getPercentage()));
				}
				if(admBioDataCJCTo.getRegNo()!=null && !admBioDataCJCTo.getRegNo().isEmpty()){
					admBioData.setRegNo(admBioDataCJCTo.getRegNo());
				}
				if(admBioDataCJCTo.getClasses()!=null && !admBioDataCJCTo.getClasses().isEmpty()){
					admBioData.setClasses(admBioDataCJCTo.getClasses());
				}
				if(admBioDataCJCTo.getName()!=null && !admBioDataCJCTo.getName().isEmpty()){
					admBioData.setName(admBioDataCJCTo.getName());
				}
				if(admBioDataCJCTo.getYear()!=null && !admBioDataCJCTo.getYear().isEmpty()){
					admBioData.setYear(admBioDataCJCTo.getYear());
				}
				if(admBioDataCJCTo.getSection()!=null && !admBioDataCJCTo.getSection().isEmpty()){
					admBioData.setSection(admBioDataCJCTo.getSection());
				}
				if(admBioDataCJCTo.getFatherName()!=null && !admBioDataCJCTo.getFatherName().isEmpty()){
					admBioData.setFatherName(admBioDataCJCTo.getFatherName());
				}
				if(admBioDataCJCTo.getSecondLanguage()!=null && !admBioDataCJCTo.getSecondLanguage().isEmpty()){
					admBioData.setSecondLanguage(admBioDataCJCTo.getSecondLanguage());
				}
				if(admBioDataCJCTo.getReligion()!=null && !admBioDataCJCTo.getReligion().isEmpty()){
					admBioData.setReligion(admBioDataCJCTo.getReligion());
				}
				if(admBioDataCJCTo.getCaste()!=null && !admBioDataCJCTo.getCaste().isEmpty()){
					admBioData.setCaste(admBioDataCJCTo.getCaste());
				}
				if(admBioDataCJCTo.getScStBcBt()!=null && !admBioDataCJCTo.getScStBcBt().isEmpty()){
					admBioData.setScStBcBt(admBioDataCJCTo.getScStBcBt());
				}
				if(admBioDataCJCTo.getSex()!=null && !admBioDataCJCTo.getSex().isEmpty()){
					admBioData.setSex(admBioDataCJCTo.getSex());
				}
				if(admBioDataCJCTo.getDob()!=null && !admBioDataCJCTo.getDob().toString().isEmpty()){
					admBioData.setDob(admBioDataCJCTo.getDob());
				}
				if(admBioDataCJCTo.getNationality()!=null && !admBioDataCJCTo.getNationality().isEmpty()){
					admBioData.setNationality(admBioDataCJCTo.getNationality());
				}
				if(admBioDataCJCTo.getState()!=null && !admBioDataCJCTo.getState().isEmpty()){
					admBioData.setState(admBioDataCJCTo.getState());
				}
				if(admBioDataCJCTo.getLastInst()!=null && !admBioDataCJCTo.getLastInst().isEmpty()){
					admBioData.setLastInst(admBioDataCJCTo.getLastInst());
				}
				if(admBioDataCJCTo.getTelephone()!=null && !admBioDataCJCTo.getTelephone().isEmpty()){
					admBioData.setTelephone(admBioDataCJCTo.getTelephone());
				}
				if(admBioDataCJCTo.getAddress1()!=null && !admBioDataCJCTo.getAddress1().isEmpty()){
					admBioData.setAddress1(admBioDataCJCTo.getAddress1());
				}
				if(admBioDataCJCTo.getAddress2()!=null && !admBioDataCJCTo.getAddress2().isEmpty()){
					admBioData.setAddress2(admBioDataCJCTo.getAddress2());
				}
				if(admBioDataCJCTo.getAddress3()!=null && !admBioDataCJCTo.getAddress3().isEmpty()){
					admBioData.setAddress3(admBioDataCJCTo.getAddress3());
				}
				if(admBioDataCJCTo.getAddress4()!=null && !admBioDataCJCTo.getAddress4().isEmpty()){
					admBioData.setAddress4(admBioDataCJCTo.getAddress4());
				}
				if(admBioDataCJCTo.getOffRemark()!=null && !admBioDataCJCTo.getOffRemark().isEmpty()){
					admBioData.setOffRemark(admBioDataCJCTo.getOffRemark());
				}
				if(admBioDataCJCTo.getPrnRemark()!=null && !admBioDataCJCTo.getPrnRemark().isEmpty()){
					admBioData.setPrnRemark(admBioDataCJCTo.getPrnRemark());
				}
				if(admBioDataCJCTo.getFeesAmt()!=null && !admBioDataCJCTo.getFeesAmt().toString().isEmpty()){
					admBioData.setFeesAmt(admBioDataCJCTo.getFeesAmt());
				}
				if(admBioDataCJCTo.getFpaiddate()!=null && !admBioDataCJCTo.getFpaiddate().toString().isEmpty()){
					admBioData.setFpaiddate(admBioDataCJCTo.getFpaiddate());
				}
				if(admBioDataCJCTo.getChalanNo()!=null && !admBioDataCJCTo.getChalanNo().isEmpty()){
					admBioData.setChalanNo(admBioDataCJCTo.getChalanNo());
				}
				if(admBioDataCJCTo.getAnnFees()!=null && !admBioDataCJCTo.getAnnFees().toString().isEmpty()){
					admBioData.setAnnFees(admBioDataCJCTo.getAnnFees());
				}
				if(admBioDataCJCTo.getAfPdDate()!=null && !admBioDataCJCTo.getAfPdDate().toString().isEmpty()){
					admBioData.setAfPdDate(admBioDataCJCTo.getAfPdDate());
				}
				if(admBioDataCJCTo.getAfChlnNo()!=null && !admBioDataCJCTo.getAfChlnNo().isEmpty()){
					admBioData.setAfChlnNo(admBioDataCJCTo.getAfChlnNo());
				}
				if(admBioDataCJCTo.getScholarship()!=null && !admBioDataCJCTo.getScholarship().isEmpty()){
					if(admBioDataCJCTo.getScholarship().equalsIgnoreCase("TRUE")){
						admBioData.setScholarship(true);
					}
					if(admBioDataCJCTo.getScholarship().equalsIgnoreCase("FALSE")){
						admBioData.setScholarship(false);
					}
				}
				if(admBioDataCJCTo.getDateAdm()!=null && !admBioDataCJCTo.getDateAdm().toString().isEmpty()){
						admBioData.setDateAdm(admBioDataCJCTo.getDateAdm());
				}
				if(admBioDataCJCTo.getTcGiven()!=null && !admBioDataCJCTo.getTcGiven().isEmpty()){
					if(admBioDataCJCTo.getTcGiven().equalsIgnoreCase("TRUE")){
						admBioData.setTcGiven(true);
					}
					if(admBioDataCJCTo.getTcGiven().equalsIgnoreCase("FALSE")){
						admBioData.setTcGiven(false);
					}
				}
				if(admBioDataCJCTo.getPassportNo()!=null && !admBioDataCJCTo.getPassportNo().isEmpty()){
					admBioData.setPassportNo(admBioDataCJCTo.getPassportNo());
				}
				if(admBioDataCJCTo.getPlaceIssued()!=null && !admBioDataCJCTo.getPlaceIssued().isEmpty()){
					admBioData.setPlaceIssued(admBioDataCJCTo.getPlaceIssued());
				}
				if(admBioDataCJCTo.getDateIssued()!=null && !admBioDataCJCTo.getDateIssued().toString().isEmpty()){
					admBioData.setDateIssued(admBioDataCJCTo.getDateIssued());
				}
				if(admBioDataCJCTo.getAnnIncome()!=null && !admBioDataCJCTo.getAnnIncome().toString().isEmpty()){
					admBioData.setAnnIncome(admBioDataCJCTo.getAnnIncome());
				}
				if(admBioDataCJCTo.getPlaceBirth()!=null && !admBioDataCJCTo.getPlaceBirth().isEmpty()){
					admBioData.setPlaceBirth(admBioDataCJCTo.getPlaceBirth());
				}
				if(admBioDataCJCTo.getStateBirth()!=null && !admBioDataCJCTo.getStateBirth().isEmpty()){
					admBioData.setStateBirth(admBioDataCJCTo.getStateBirth());
				}
				if(admBioDataCJCTo.getCountry()!=null && !admBioDataCJCTo.getCountry().isEmpty()){
					admBioData.setCountry(admBioDataCJCTo.getCountry());
				}
				if(admBioDataCJCTo.getfOccupation()!=null && !admBioDataCJCTo.getfOccupation().isEmpty()){
					admBioData.setfOccupation(admBioDataCJCTo.getfOccupation());
				}
				if(admBioDataCJCTo.getExamPassed()!=null && !admBioDataCJCTo.getExamPassed().isEmpty()){
					admBioData.setExamPassed(admBioDataCJCTo.getExamPassed());
				}
				if(admBioDataCJCTo.getExRegNo()!=null && !admBioDataCJCTo.getExRegNo().isEmpty()){
					admBioData.setExRegNo(admBioDataCJCTo.getExRegNo());
				}
				if(admBioDataCJCTo.getYearPass()!=null && !admBioDataCJCTo.getYearPass().isEmpty()){
					admBioData.setYearPass(Integer.parseInt(admBioDataCJCTo.getYearPass()));
				}
				if(admBioDataCJCTo.getMonthPass()!=null && !admBioDataCJCTo.getMonthPass().isEmpty()){
					admBioData.setMonthPass(admBioDataCJCTo.getMonthPass());
				}
				if(admBioDataCJCTo.getAdmTcNo()!=null && !admBioDataCJCTo.getAdmTcNo().isEmpty()){
					admBioData.setAdmTcNo(admBioDataCJCTo.getAdmTcNo());
				}
				if(admBioDataCJCTo.getReasonLeave()!=null && !admBioDataCJCTo.getReasonLeave().isEmpty()){
					admBioData.setReasonLeave(admBioDataCJCTo.getReasonLeave());
				}
				if(admBioDataCJCTo.getTcNo()!=null && !admBioDataCJCTo.getTcNo().isEmpty()){
					admBioData.setTcNo(admBioDataCJCTo.getTcNo());
				}
				if(admBioDataCJCTo.getExamResult()!=null && !admBioDataCJCTo.getExamResult().isEmpty()){
					admBioData.setExamResult(admBioDataCJCTo.getExamResult());
				}
				if(admBioDataCJCTo.getRemarks()!=null && !admBioDataCJCTo.getRemarks().isEmpty()){
					admBioData.setRemarks(admBioDataCJCTo.getRemarks());
				}
				if(admBioDataCJCTo.getNri()!=null && !admBioDataCJCTo.getNri().isEmpty()){
					if(admBioDataCJCTo.getNri().equalsIgnoreCase("TRUE")){
						admBioData.setNri(true);
					}
					if(admBioDataCJCTo.getNri().equalsIgnoreCase("FALSE")){
						admBioData.setNri(false);
					}
				}
				if(admBioDataCJCTo.getLig()!=null && !admBioDataCJCTo.getLig().isEmpty()){
					if(admBioDataCJCTo.getLig().equalsIgnoreCase("TRUE")){
						admBioData.setLig(true);
					}
					if(admBioDataCJCTo.getLig().equalsIgnoreCase("FALSE")){
						admBioData.setLig(false);
					}
				}
				if(admBioDataCJCTo.getReligious()!=null && !admBioDataCJCTo.getReligious().isEmpty()){
					if(admBioDataCJCTo.getReligious().equalsIgnoreCase("TRUE")){
						admBioData.setReligious(true);
					}
					if(admBioDataCJCTo.getReligious().equalsIgnoreCase("FALSE")){
						admBioData.setReligious(false);
					}
				}
				if(admBioDataCJCTo.getSelfFinan()!=null && !admBioDataCJCTo.getSelfFinan().isEmpty()){
					if(admBioDataCJCTo.getSelfFinan().equalsIgnoreCase("TRUE")){
						admBioData.setSelfFinan(true);
					}
					if(admBioDataCJCTo.getSelfFinan().equalsIgnoreCase("FALSE")){
						admBioData.setSelfFinan(false);
					}
				}
				if(admBioDataCJCTo.getsFinanCat()!=null && !admBioDataCJCTo.getsFinanCat().isEmpty()){
					admBioData.setsFinanCat(admBioDataCJCTo.getsFinanCat());
				}
				if(admBioDataCJCTo.getMotherName()!=null && !admBioDataCJCTo.getMotherName().isEmpty()){
					admBioData.setMotherName(admBioDataCJCTo.getMotherName());
				}
				if(admBioDataCJCTo.getExamRegNo()!=null && !admBioDataCJCTo.getExamRegNo().isEmpty()){
					admBioData.setExamRegNo(admBioDataCJCTo.getExamRegNo());
				}
				if(admBioDataCJCTo.getStudentNo()!=null && !admBioDataCJCTo.getStudentNo().isEmpty()){
					admBioData.setStudentNo(admBioDataCJCTo.getStudentNo());
				}
				if(admBioDataCJCTo.getCet()!=null && !admBioDataCJCTo.getCet().isEmpty()){
					if(admBioDataCJCTo.getCet().equalsIgnoreCase("TRUE")){
						admBioData.setCet(true);
					}
					if(admBioDataCJCTo.getCet().equalsIgnoreCase("FALSE")){
						admBioData.setCet(false);
					}
				}
				if(admBioDataCJCTo.getFeePayable()!=null && !admBioDataCJCTo.getFeePayable().isEmpty()){
					admBioData.setFeePayable(admBioDataCJCTo.getFeePayable());
				}
				if(admBioDataCJCTo.getForeign()!=null && !admBioDataCJCTo.getForeign().isEmpty()){
					if(admBioDataCJCTo.getForeign().equalsIgnoreCase("TRUE")){
						admBioData.setForeign(true);
					}
					if(admBioDataCJCTo.getForeign().equalsIgnoreCase("FALSE")){
						admBioData.setForeign(false);
					}
				}
				if(admBioDataCJCTo.getAdmitCat()!=null && !admBioDataCJCTo.getAdmitCat().isEmpty()){
					admBioData.setAdmitCat(admBioDataCJCTo.getAdmitCat());
				}
				if(admBioDataCJCTo.getBloodGroup()!=null && !admBioDataCJCTo.getBloodGroup().isEmpty()){
					admBioData.setBloodGroup(admBioDataCJCTo.getBloodGroup());
				}
				if(admBioDataCJCTo.getIndSpCdt()!=null && !admBioDataCJCTo.getIndSpCdt().isEmpty()){
					if(admBioDataCJCTo.getIndSpCdt().equalsIgnoreCase("TRUE")){
						admBioData.setIndSpCdt(true);
					}
					if(admBioDataCJCTo.getIndSpCdt().equalsIgnoreCase("FALSE")){
						admBioData.setIndSpCdt(false);
					}
				}
				if(admBioDataCJCTo.getPhyHandicapped()!=null && !admBioDataCJCTo.getPhyHandicapped().isEmpty()){
					admBioData.setPhyHandicapped(admBioDataCJCTo.getPhyHandicapped());
				}
				if(admBioDataCJCTo.getMedmInstr()!=null && !admBioDataCJCTo.getMedmInstr().isEmpty()){
					admBioData.setMedmInstr(admBioDataCJCTo.getMedmInstr());
				}
				if(admBioDataCJCTo.getsEvnyKar()!=null && !admBioDataCJCTo.getsEvnyKar().isEmpty()){
					if(admBioDataCJCTo.getsEvnyKar().equalsIgnoreCase("TRUE")){
						admBioData.setsEvnyKar(true);
					}
					if(admBioDataCJCTo.getsEvnyKar().equalsIgnoreCase("FALSE")){
						admBioData.setsEvnyKar(false);
					}
				}
				if(admBioDataCJCTo.getMobileNumber()!=null && !admBioDataCJCTo.getMobileNumber().isEmpty()){
					admBioData.setMobileNumber(admBioDataCJCTo.getMobileNumber().trim());
				}
				if(admBioDataCJCTo.getAdmTcDt()!=null && !admBioDataCJCTo.getAdmTcDt().toString().isEmpty()){
					admBioData.setAdmTcDt(admBioDataCJCTo.getAdmTcDt());
				}
				if(admBioDataCJCTo.getDateLeave()!=null && !admBioDataCJCTo.getDateLeave().toString().isEmpty()){
					admBioData.setDateLeave(admBioDataCJCTo.getDateLeave());
				}
				if(admBioDataCJCTo.getTcDate()!=null && !admBioDataCJCTo.getTcDate().toString().isEmpty()){
					admBioData.setTcDate(admBioDataCJCTo.getTcDate());
				}
				if(admBioDataCJCTo.getAcademicYear()!=null && !admBioDataCJCTo.getAcademicYear().isEmpty()){
					admBioData.setAcademicYear(admBioDataCJCTo.getAcademicYear());
				}
				admBioDataList.add(admBioData);
			}
		}
		return admBioDataList;
	}
	/**
	 * @param str
	 * @return
	 */
	public static java.sql.Date ConvertStringToSQLDate(String str) {
		java.sql.Date sqldate = null;
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		if(CommonUtil.isStringContainsNumbers(str)){
			String formattedDate = formatter.format(new Date(str.substring(4,6) +"/"+str.substring(6,8) +"/"+str.substring(0,4)));
			//sqldate = CommonUtil.ConvertStringToSQLDate(formattedDate);
			sqldate = CommonUtil.ConvertStringToSQLDate(formattedDate);
		}else{
			String formatDate = "";
			if (str != null & !str.isEmpty())
				formatDate = formatSqlDateTime(str);
			
			if (formatDate != null & !formatDate.isEmpty()) {
				Date date = new Date(formatDate);
				sqldate = new java.sql.Date(date.getTime());
			}
		}
		
		return sqldate;
	}
	
	/**
	 * @param dateString
	 * @return
	 */
	public static String formatSqlDateTime(String dateString) {
		String formatDate = "";
		Date date = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat pattern = new SimpleDateFormat("MM/dd/yy");
			if (dateString != null & !dateString.isEmpty()) {
				date = dateFormat.parse(dateString);
				formatDate = pattern.format(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formatDate;
	}
}
