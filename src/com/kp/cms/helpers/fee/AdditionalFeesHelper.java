package com.kp.cms.helpers.fee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.AdditionalFees;
import com.kp.cms.bo.admin.FeesClassFee;
import com.kp.cms.bo.admin.FeesFeeDetails;
import com.kp.cms.to.fee.AdditionalFeesTo;
import com.kp.cms.to.fee.FeesClassFeeTo;
import com.kp.cms.to.fee.FeesDetailsFeeTo;

public class AdditionalFeesHelper {
	private static volatile AdditionalFeesHelper additionalFeesHelper = null;
	public static AdditionalFeesHelper getInstance(){
		if(additionalFeesHelper == null){
			additionalFeesHelper = new AdditionalFeesHelper();
			return additionalFeesHelper;
		}
		return additionalFeesHelper;
	}
	public List<AdditionalFees> convertToTOBo( List<AdditionalFeesTo> additionalFeesList)throws Exception {
		List<AdditionalFees> additionalFees = new ArrayList<AdditionalFees>();
		if(additionalFeesList!=null){
			Iterator<AdditionalFeesTo> iterator = additionalFeesList.iterator();
			while (iterator.hasNext()) {
				AdditionalFeesTo additionalFeesTo = (AdditionalFeesTo) iterator .next();
				AdditionalFees fees = new AdditionalFees();
				if(additionalFeesTo.getFeesCode()!=null && !additionalFeesTo.getFeesCode().isEmpty()){
					fees.setFeesCode(additionalFeesTo.getFeesCode());
				}
				if(additionalFeesTo.getFeesName()!=null && !additionalFeesTo.getFeesName().isEmpty()){
					fees.setFeesName(additionalFeesTo.getFeesName());
				}
				if(additionalFeesTo.getAccGia()!=null && !additionalFeesTo.getAccGia().isEmpty()){
					fees.setAccGia(additionalFeesTo.getAccGia());
				}
				if(additionalFeesTo.getAccNgia()!=null && !additionalFeesTo.getAccNgia().isEmpty()){
					fees.setAccNgia(additionalFeesTo.getAccNgia());
				}
				if(additionalFeesTo.getClasses()!=null && !additionalFeesTo.getClasses().isEmpty()){
					fees.setClasses(additionalFeesTo.getClasses());
				}
				if(additionalFeesTo.getAmount()!=null && !additionalFeesTo.getAmount().isEmpty()){
					fees.setAmount(Integer.parseInt(additionalFeesTo.getAmount()));
				}
				if(additionalFeesTo.getLoadToHelp()!=null && !additionalFeesTo.getLoadToHelp().isEmpty()){
					if(additionalFeesTo.getLoadToHelp().equalsIgnoreCase("TRUE")){
						fees.setLoadToHelp(true);
					}
					else{
						fees.setLoadToHelp(false);
					}
				}
				if(additionalFeesTo.getAcademicYear()!=null && !additionalFeesTo.getAcademicYear().isEmpty()){
					fees.setAcademicYear(Integer.parseInt(additionalFeesTo.getAcademicYear()));
				}
				additionalFees.add(fees);
			}
		}
		return additionalFees;
	}
	/**
	 * @param detailsFeeTos
	 * @return
	 * @throws Exception
	 */
	public List<FeesFeeDetails> populateTOToBO( List<FeesDetailsFeeTo> detailsFeeTos)throws Exception {
		List<FeesFeeDetails> feeDetails = new ArrayList<FeesFeeDetails>();
		if(detailsFeeTos!=null){
			Iterator<FeesDetailsFeeTo> iterator = detailsFeeTos.iterator();
			while (iterator.hasNext()) {
				FeesDetailsFeeTo detailsFeeTo = (FeesDetailsFeeTo) iterator .next();
				FeesFeeDetails details = new FeesFeeDetails();
				if(detailsFeeTo.getBillNo()!=null && !detailsFeeTo.getBillNo().isEmpty()){
					details.setBillNo(Integer.parseInt(detailsFeeTo.getBillNo()));
				}
				if(detailsFeeTo.getFeesCode()!=null && !detailsFeeTo.getFeesCode().isEmpty()){
					details.setFeesCode(detailsFeeTo.getFeesCode());
				}
				if(detailsFeeTo.getAddFee22()!=null && !detailsFeeTo.getAddFee22().isEmpty()){
					details.setAddFee22(Integer.parseInt(detailsFeeTo.getAddFee22()));
				}
				if(detailsFeeTo.getAddFee993()!=null && !detailsFeeTo.getAddFee993().isEmpty()){
					details.setAddFee993(Integer.parseInt(detailsFeeTo.getAddFee993()));
				}
				if(detailsFeeTo.getAcademicYear()!=null && !detailsFeeTo.getAcademicYear().isEmpty()){
					details.setAcademicYear(Integer.parseInt(detailsFeeTo.getAcademicYear()));
				}
				feeDetails.add(details);
			}
		}
		return feeDetails;
	}
	/**
	 * @param list
	 * @return
	 */
	public List<FeesClassFee> convertToTOBo1(List<FeesClassFeeTo> list) throws Exception{
		List<FeesClassFee> classFeesList = new ArrayList<FeesClassFee>();
		if(list!=null){
			Iterator<FeesClassFeeTo> iterator = list.iterator();
			while (iterator.hasNext()) {
				FeesClassFeeTo classFeeTo = (FeesClassFeeTo) iterator.next();
				FeesClassFee classFee = new FeesClassFee();
				if(classFeeTo.getClasses()!=null && !classFeeTo.getClasses().isEmpty()){
					classFee.setClasses(classFeeTo.getClasses());
				}
				if(classFeeTo.getGia()!=null && !classFeeTo.getGia().isEmpty()){
					if(classFeeTo.getGia().equalsIgnoreCase("TRUE")){
						classFee.setGia(true);
					}
					else{
						classFee.setGia(false);
					}
				}
				if(classFeeTo.getFees()!=null && !classFeeTo.getFees().isEmpty()){
					classFee.setFees(Integer.parseInt(classFeeTo.getFees()));
				}
				if(classFeeTo.getMaStringFee()!=null && !classFeeTo.getMaStringFee().isEmpty()){
					classFee.setMaintFee(Integer.parseInt(classFeeTo.getMaStringFee()));
				}
				if(classFeeTo.getOutKarFe()!=null && !classFeeTo.getOutKarFe().isEmpty()){
					if(classFeeTo.getOutKarFe().equalsIgnoreCase("TRUE")){
						classFee.setOutKarFe(true);
					}
					else{
						classFee.setOutKarFe(false);
					}
				}
				if(classFeeTo.getSelFnFee()!=null && !classFeeTo.getSelFnFee().isEmpty()){
					classFee.setSelFnFee(Integer.parseInt(classFeeTo.getSelFnFee()));
				}
				if(classFeeTo.getApplForNri()!=null && !classFeeTo.getApplForNri().isEmpty()){
					if(classFeeTo.getApplForNri().equalsIgnoreCase("TRUE")){
						classFee.setApplForNri(true);
					}else{
						classFee.setApplForNri(false);
					}
				}
				if(classFeeTo.getAplForAdad()!= null && !classFeeTo.getAplForAdad().isEmpty()){
					if(classFeeTo.getAplForAdad().equalsIgnoreCase("TRUE")){
						classFee.setAplForAdad(true);
					}
					else{
						classFee.setAplForAdad(false);
					}
				}
				if(classFeeTo.getSlfnSpFee()!=null && !classFeeTo.getSlfnSpFee().isEmpty()){
					classFee.setSlfnSpFee(Integer.parseInt(classFeeTo.getSlfnSpFee()));
				}
				if(classFeeTo.getCetFees()!=null && !classFeeTo.getCetFees().isEmpty()){
					classFee.setCetFees(Integer.parseInt(classFeeTo.getCetFees()));
				}
				if(classFeeTo.getScstbtFee()!=null && !classFeeTo.getScstbtFee().isEmpty()){
					classFee.setScstbtFee(Integer.parseInt(classFeeTo.getScstbtFee()));
				}
				if(classFeeTo.getSscstMaString()!=null && !classFeeTo.getSscstMaString().isEmpty()){
					classFee.setSscstMaint(Integer.parseInt(classFeeTo.getSscstMaString()));
				}
				if(classFeeTo.getNriFees()!=null && !classFeeTo.getNriFees().isEmpty()){
					classFee.setNriFees(Integer.parseInt(classFeeTo.getNriFees()));
				}
				if(classFeeTo.getNriMFees()!=null && !classFeeTo.getNriMFees().isEmpty()){
					classFee.setNriMFees(Integer.parseInt(classFeeTo.getNriMFees()));
				}
				if(classFeeTo.getForgnFees()!=null && !classFeeTo.getForgnFees().isEmpty()){
					classFee.setForgnFees(Integer.parseInt(classFeeTo.getForgnFees()));
				}
				if(classFeeTo.getForgnMFees()!=null && !classFeeTo.getForgnMFees().isEmpty()){
					classFee.setForgnMFees(Integer.parseInt(classFeeTo.getForgnMFees()));
				}
				if(classFeeTo.getInsSpFees()!=null && !classFeeTo.getInsSpFees().isEmpty()){
					classFee.setInsSpFees(Integer.parseInt(classFeeTo.getInsSpFees()));
				}
				if(classFeeTo.getAcademicYear()!=null && !classFeeTo.getAcademicYear().isEmpty()){
					classFee.setAcademicYear(Integer.parseInt(classFeeTo.getAcademicYear()));
				}
				classFeesList.add(classFee);
			}
		}
		return classFeesList;
	}
}
