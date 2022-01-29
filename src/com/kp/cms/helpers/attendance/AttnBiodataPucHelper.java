package com.kp.cms.helpers.attendance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ibm.icu.math.BigDecimal;
import com.kp.cms.bo.admin.AttnBiodataPuc;
import com.kp.cms.to.attendance.AttnBioDataPucTo;

public class AttnBiodataPucHelper {
	private static volatile AttnBiodataPucHelper attnBiodataPucHelper = null;
	public static AttnBiodataPucHelper getInstance(){
		if(attnBiodataPucHelper == null){
			attnBiodataPucHelper = new  AttnBiodataPucHelper();
			return attnBiodataPucHelper;
		}
		return attnBiodataPucHelper;
	}
	/**
	 * @param bioDataList
	 * @return
	 * @throws Exception
	 */
	public List<AttnBiodataPuc> convertToTOBo(List<AttnBioDataPucTo> bioDataList)throws Exception {
		List<AttnBiodataPuc> list = new ArrayList<AttnBiodataPuc>();
		if(bioDataList!=null){
			Iterator<AttnBioDataPucTo> iterator = bioDataList.iterator();
			while (iterator.hasNext()) {
				AttnBioDataPucTo attnBioDataPucTo = (AttnBioDataPucTo) iterator .next();
				AttnBiodataPuc attnBiodataPuc = new AttnBiodataPuc();
				if(attnBioDataPucTo.getAppNo()!=null && !attnBioDataPucTo.getAppNo().isEmpty()){
					attnBiodataPuc.setAppNo(Integer.parseInt(attnBioDataPucTo.getAppNo()));
				}
				if(attnBioDataPucTo.getPercentage()!=null && !attnBioDataPucTo.getPercentage().isEmpty()){
					attnBiodataPuc.setPercentage(Integer.parseInt(attnBioDataPucTo.getPercentage()));
				}
				if(attnBioDataPucTo.getRegNo()!=null && !attnBioDataPucTo.getRegNo().isEmpty()){
					attnBiodataPuc.setRegNo(attnBioDataPucTo.getRegNo());
				}
				if(attnBioDataPucTo.getClasses()!=null && !attnBioDataPucTo.getClasses().isEmpty()){
					attnBiodataPuc.setClasses(attnBioDataPucTo.getClasses());
				}
				if(attnBioDataPucTo.getName()!=null && !attnBioDataPucTo.getName().isEmpty()){
					attnBiodataPuc.setName(attnBioDataPucTo.getName());
				}
				if(attnBioDataPucTo.getYear()!=null && !attnBioDataPucTo.getYear().isEmpty()){
					attnBiodataPuc.setYear(attnBioDataPucTo.getYear());
				}
				if(attnBioDataPucTo.getSection()!=null && !attnBioDataPucTo.getSection().isEmpty()){
					attnBiodataPuc.setSection(attnBioDataPucTo.getSection());
				}
				if(attnBioDataPucTo.getFatherName()!=null && !attnBioDataPucTo.getFatherName().isEmpty()){
					attnBiodataPuc.setFatherName(attnBioDataPucTo.getFatherName());
				}
				if(attnBioDataPucTo.getSecndLang()!=null && !attnBioDataPucTo.getSecndLang().isEmpty()){
					attnBiodataPuc.setSecndLang(attnBioDataPucTo.getSecndLang());
				}
				if(attnBioDataPucTo.getReligion()!=null && !attnBioDataPucTo.getReligion().isEmpty()){
					attnBiodataPuc.setReligion(attnBioDataPucTo.getReligion());
				}
				if(attnBioDataPucTo.getCaste()!=null && !attnBioDataPucTo.getCaste().isEmpty()){
					attnBiodataPuc.setCaste(attnBioDataPucTo.getCaste());
				}
				if(attnBioDataPucTo.getScstbcbt()!=null && !attnBioDataPucTo.getScstbcbt().isEmpty()){
					attnBiodataPuc.setScstbcbt(attnBioDataPucTo.getScstbcbt());
				}
				if(attnBioDataPucTo.getSex()!=null && !attnBioDataPucTo.getSex().isEmpty()){
					attnBiodataPuc.setSex(attnBioDataPucTo.getSex());
				}
				if(attnBioDataPucTo.getDob()!=null && !attnBioDataPucTo.getDob().toString().isEmpty()){
					attnBiodataPuc.setDob(attnBioDataPucTo.getDob());
				}
				if(attnBioDataPucTo.getNationality()!=null && !attnBioDataPucTo.getNationality().isEmpty()){
					attnBiodataPuc.setNationality(attnBioDataPucTo.getNationality());
				}
				if(attnBioDataPucTo.getState()!=null && !attnBioDataPucTo.getState().isEmpty()){
					attnBiodataPuc.setState(attnBioDataPucTo.getState());
				}
				if(attnBioDataPucTo.getLastInst()!=null && !attnBioDataPucTo.getLastInst().isEmpty()){
					attnBiodataPuc.setLastInst(attnBioDataPucTo.getLastInst());
				}
				if(attnBioDataPucTo.getTelephone()!=null && !attnBioDataPucTo.getTelephone().isEmpty()){
					attnBiodataPuc.setTelephone(attnBioDataPucTo.getTelephone());
				}
				if(attnBioDataPucTo.getAddress1()!=null && !attnBioDataPucTo.getAddress1().isEmpty()){
					attnBiodataPuc.setAddress1(attnBioDataPucTo.getAddress1());
				}
				if(attnBioDataPucTo.getAddress2()!=null && !attnBioDataPucTo.getAddress2().isEmpty()){
					attnBiodataPuc.setAddress2(attnBioDataPucTo.getAddress2());
				}
				if(attnBioDataPucTo.getAddress3()!=null && !attnBioDataPucTo.getAddress3().isEmpty()){
					attnBiodataPuc.setAddress3(attnBioDataPucTo.getAddress3());
				}
				if(attnBioDataPucTo.getAddress4()!=null && !attnBioDataPucTo.getAddress4().isEmpty()){
					attnBiodataPuc.setAddress4(attnBioDataPucTo.getAddress4());
				}
				if(attnBioDataPucTo.getOffRemarks()!=null && !attnBioDataPucTo.getOffRemarks().isEmpty()){
					attnBiodataPuc.setOffRemarks(attnBioDataPucTo.getOffRemarks());
				}
				if(attnBioDataPucTo.getPrnRemarks()!=null && !attnBioDataPucTo.getPrnRemarks().isEmpty()){
					attnBiodataPuc.setPrnRemarks(attnBioDataPucTo.getPrnRemarks());
				}
				if(attnBioDataPucTo.getScholarship()!=null && !attnBioDataPucTo.getScholarship().isEmpty()){
					if(attnBioDataPucTo.getScholarship().equalsIgnoreCase("TRUE")){
						attnBiodataPuc.setScholarship(true);
					}
					else{
						attnBiodataPuc.setScholarship(false);
					}
				}
				if(attnBioDataPucTo.getDateAdm()!=null && !attnBioDataPucTo.getDateAdm().toString().isEmpty()){
					attnBiodataPuc.setDateAdm(attnBioDataPucTo.getDateAdm());
				}
				if(attnBioDataPucTo.getAnnIncome()!=null && !attnBioDataPucTo.getAnnIncome().isEmpty()){
					attnBiodataPuc.setAnnIncome(new BigDecimal(attnBioDataPucTo.getAnnIncome()).intValue());
				}
				if(attnBioDataPucTo.getMaintFees()!=null && !attnBioDataPucTo.getMaintFees().isEmpty()){
					attnBiodataPuc.setMaintFees(Float.parseFloat(attnBioDataPucTo.getMaintFees()));
				}
				if(attnBioDataPucTo.getFailed()!=null && !attnBioDataPucTo.getFailed().isEmpty()){
					if(attnBioDataPucTo.getFailed().equalsIgnoreCase("TRUE")){
						attnBiodataPuc.setFailed(true);
					}else{
						attnBiodataPuc.setFailed(false);
					}
				}
				if(attnBioDataPucTo.getDmmyNotUsd()!=null && !attnBioDataPucTo.getDmmyNotUsd().isEmpty()){
					attnBiodataPuc.setDmmyNotUsd(attnBioDataPucTo.getDmmyNotUsd());
				}
				if(attnBioDataPucTo.getDmmyNotUsd1()!=null && !attnBioDataPucTo.getDmmyNotUsd1().isEmpty()){
					attnBiodataPuc.setDmmyNotUsd1(attnBioDataPucTo.getDmmyNotUsd1());
				}
				if(attnBioDataPucTo.getElecCode1()!=null && !attnBioDataPucTo.getElecCode1().isEmpty()){
					attnBiodataPuc.setElecCode1(attnBioDataPucTo.getElecCode1());
				}
				if(attnBioDataPucTo.getElecCode2()!=null && !attnBioDataPucTo.getElecCode2().isEmpty()){
					attnBiodataPuc.setElecCode2(attnBioDataPucTo.getElecCode2());
				}
				if(attnBioDataPucTo.getElecCode3()!=null && !attnBioDataPucTo.getElecCode3().isEmpty()){
					attnBiodataPuc.setElecCode3(attnBioDataPucTo.getElecCode3());
				}
				if(attnBioDataPucTo.getElecCode4()!=null && !attnBioDataPucTo.getElecCode4().isEmpty()){
					attnBiodataPuc.setElecCode4(attnBioDataPucTo.getElecCode4());
				}
				if(attnBioDataPucTo.getElecCode5()!=null && !attnBioDataPucTo.getElecCode5().isEmpty()){
					attnBiodataPuc.setElecCode5(attnBioDataPucTo.getElecCode5());
				}
				if(attnBioDataPucTo.getElecCode6()!=null && !attnBioDataPucTo.getElecCode6().isEmpty()){
					attnBiodataPuc.setElecCode6(attnBioDataPucTo.getElecCode6());
				}
				if(attnBioDataPucTo.getElecCode7()!=null && !attnBioDataPucTo.getElecCode7().isEmpty()){
					attnBiodataPuc.setElecCode7(attnBioDataPucTo.getElecCode7());
				}
				if(attnBioDataPucTo.getElecCode8()!=null && !attnBioDataPucTo.getElecCode8().isEmpty()){
					attnBiodataPuc.setElecCode8(attnBioDataPucTo.getElecCode8());
				}
				if(attnBioDataPucTo.getElecPos1()!=null && !attnBioDataPucTo.getElecPos1().isEmpty()){
					attnBiodataPuc.setElecPos1(Integer.parseInt(attnBioDataPucTo.getElecPos1()));
				}
				if(attnBioDataPucTo.getElecPos2()!=null && !attnBioDataPucTo.getElecPos2().isEmpty()){
					attnBiodataPuc.setElecPos2(Integer.parseInt(attnBioDataPucTo.getElecPos2()));
				}
				if(attnBioDataPucTo.getElecPos3()!=null && !attnBioDataPucTo.getElecPos3().isEmpty()){
					attnBiodataPuc.setElecPos3(Integer.parseInt(attnBioDataPucTo.getElecPos3()));
				}
				if(attnBioDataPucTo.getElecPos4()!=null && !attnBioDataPucTo.getElecPos4().isEmpty()){
					attnBiodataPuc.setElecPos4(Integer.parseInt(attnBioDataPucTo.getElecPos4()));
				}
				if(attnBioDataPucTo.getElecPos5()!=null && !attnBioDataPucTo.getElecPos5().isEmpty()){
					attnBiodataPuc.setElecPos5(Integer.parseInt(attnBioDataPucTo.getElecPos5()));
				}
				if(attnBioDataPucTo.getElecPos6()!=null && !attnBioDataPucTo.getElecPos6().isEmpty()){
					attnBiodataPuc.setElecPos6(Integer.parseInt(attnBioDataPucTo.getElecPos6()));
				}
				if(attnBioDataPucTo.getElecPos7()!=null && !attnBioDataPucTo.getElecPos7().isEmpty()){
					attnBiodataPuc.setElecPos7(Integer.parseInt(attnBioDataPucTo.getElecPos7()));
				}
				if(attnBioDataPucTo.getElecPos8()!=null && !attnBioDataPucTo.getElecPos8().isEmpty()){
					attnBiodataPuc.setElecPos8(Integer.parseInt(attnBioDataPucTo.getElecPos8()));
				}
				if(attnBioDataPucTo.getBloodGroup()!=null && !attnBioDataPucTo.getBloodGroup().isEmpty()){
					attnBiodataPuc.setBloodGroup(attnBioDataPucTo.getBloodGroup());
				}
				if(attnBioDataPucTo.getUserCode()!=null && !attnBioDataPucTo.getUserCode().isEmpty()){
					attnBiodataPuc.setUserCode(attnBioDataPucTo.getUserCode());
				}
				if(attnBioDataPucTo.getCetFeePaid()!=null && !attnBioDataPucTo.getCetFeePaid().isEmpty()){
					if(attnBioDataPucTo.getCetFeePaid().equalsIgnoreCase("TRUE")){
						attnBiodataPuc.setCetFeePaid(true);
					}else{
						attnBiodataPuc.setCetFeePaid(false);
					}
				}
				if(attnBioDataPucTo.getAieeeFee()!=null && !attnBioDataPucTo.getAieeeFee().isEmpty()){
					if(attnBioDataPucTo.getAieeeFee().equalsIgnoreCase("TRUE")){
						attnBiodataPuc.setAieeeFee(true);
					}else{
						attnBiodataPuc.setAieeeFee(false);
					}
				}
				if(attnBioDataPucTo.getAcademicYear()!=null && !attnBioDataPucTo.getAcademicYear().isEmpty()){
					attnBiodataPuc.setAcademicYear(Integer.parseInt(attnBioDataPucTo.getAcademicYear()));
				}
				list.add(attnBiodataPuc);
			}
		}
		return list;
	}
}
