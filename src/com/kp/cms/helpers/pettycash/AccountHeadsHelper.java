package com.kp.cms.helpers.pettycash;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.lang.model.element.PackageElement;

import org.apache.taglibs.standard.lang.jstl.Coercions;

import com.kp.cms.bo.admin.AccountHeads;
import com.kp.cms.bo.admin.PettyCashCollection;
import com.kp.cms.bo.admin.PettyCashCollectionDetails;
import com.kp.cms.to.pettycash.AccountHeadsTo;
import com.kp.cms.to.pettycash.PettyCashCollectionDetailsTo;
import com.kp.cms.to.pettycash.PettyCashCollectionTo;

public class AccountHeadsHelper {
	private static volatile AccountHeadsHelper accountHeadsHelper = null;
	public static AccountHeadsHelper getInstance(){
		if(accountHeadsHelper == null){
			accountHeadsHelper = new AccountHeadsHelper();
			return accountHeadsHelper;
		}
		return accountHeadsHelper ;
	}
	/**
	 * @param accountHeadsList
	 * @return
	 * @throws Exception
	 */
	public List<AccountHeads> convertTOToBO(
			List<AccountHeadsTo> accountHeadsList) throws Exception {
		List<AccountHeads> accountHeads = new ArrayList<AccountHeads>();
		if (accountHeadsList != null) {
			Iterator<AccountHeadsTo> iterator = accountHeadsList.iterator();
			while (iterator.hasNext()) {
				AccountHeadsTo accountHeadsTo = (AccountHeadsTo) iterator
						.next();
				AccountHeads heads = new AccountHeads();
				if (accountHeadsTo.getAcademicYear() != null
						&& !accountHeadsTo.getAcademicYear().isEmpty()) {
					heads.setAcademicYear(Integer.parseInt(accountHeadsTo
							.getAcademicYear()));
				}
				if (accountHeadsTo.getAccCode() != null
						&& !accountHeadsTo.getAccCode().isEmpty()) {
					heads.setAccCode(accountHeadsTo.getAccCode());
				}
				if (accountHeadsTo.getAccName() != null
						&& !accountHeadsTo.getAccName().isEmpty()) {
					heads.setAccName(accountHeadsTo.getAccName());
				}
				if (accountHeadsTo.getBankAccNo() != null
						&& !accountHeadsTo.getBankAccNo().isEmpty()) {
					heads.setBankAccNo(Integer.parseInt(accountHeadsTo
							.getBankAccNo()));
				}
				if (accountHeadsTo.getAmount() != null
						&& !accountHeadsTo.getAmount().isEmpty()) {
					heads.setAmount(Float
							.parseFloat(accountHeadsTo.getAmount()));
				}
				if (accountHeadsTo.getFixedAmt() != null
						&& !accountHeadsTo.getFixedAmt().isEmpty()) {
					if (accountHeadsTo.getFixedAmt().equalsIgnoreCase("TRUE")) {
						heads.setFixedAmt(true);
					} else {
						heads.setFixedAmt(false);
					}
				}
				if (accountHeadsTo.getUserCode() != null
						&& !accountHeadsTo.getUserCode().isEmpty()) {
					heads.setUserCode(accountHeadsTo.getUserCode());
				}
				if (accountHeadsTo.getAtStation() != null
						&& !accountHeadsTo.getAtStation().isEmpty()) {
					heads.setAtStation(accountHeadsTo.getAtStation());
				}
				if (accountHeadsTo.getAtTime() != null) {
					heads.setAtTime(accountHeadsTo.getAtTime());
				}
				if (accountHeadsTo.getAtDate() != null
						&& !accountHeadsTo.getAtDate().toString().isEmpty()) {
					heads.setAtDate(accountHeadsTo.getAtDate());
				}
				accountHeads.add(heads);
			}
		}
		return accountHeads;
	}
	/**
	 * @param pettyCashCollectionList
	 * @return
	 * @throws Exception
	 */
	public List<PettyCashCollection> convertTOToBO1( List<PettyCashCollectionTo> pettyCashCollectionList) throws Exception{
		List<PettyCashCollection> collectionsList= new ArrayList<PettyCashCollection>();
		if(pettyCashCollectionList!=null){
			Iterator<PettyCashCollectionTo> iterator = pettyCashCollectionList.iterator();
			while (iterator.hasNext()) {
				PettyCashCollectionTo pettyCashCollectionTo = (PettyCashCollectionTo) iterator .next();
				PettyCashCollection collection = new PettyCashCollection();
				if(pettyCashCollectionTo.getReceiptNo()!=null && !pettyCashCollectionTo.getReceiptNo().isEmpty()){
					collection.setReceiptNo(Integer.parseInt(pettyCashCollectionTo.getReceiptNo()));
				}
				if(pettyCashCollectionTo.getAplRegNo()!=null && !pettyCashCollectionTo.getAplRegNo().isEmpty()){
					collection.setAplRegNo(pettyCashCollectionTo.getAplRegNo());
				}
				if(pettyCashCollectionTo.getUserCode()!=null && !pettyCashCollectionTo.getUserCode().isEmpty()){
					collection.setUserCode(pettyCashCollectionTo.getUserCode());
				}
				if(pettyCashCollectionTo.getName()!=null && !pettyCashCollectionTo.getName().isEmpty()){
					collection.setName(pettyCashCollectionTo.getName());
				}
				if(pettyCashCollectionTo.getDate()!=null){
					collection.setDate(pettyCashCollectionTo.getDate());
				}
				if(pettyCashCollectionTo.getTime()!=null && !pettyCashCollectionTo.getTime().isEmpty()){
					collection.setTime(pettyCashCollectionTo.getTime());
				}
				if(pettyCashCollectionTo.getAtStation()!=null && !pettyCashCollectionTo.getAtStation().isEmpty()){
					collection.setAtStation(pettyCashCollectionTo.getAtStation());
				}
				if(pettyCashCollectionTo.getAtDate()!=null){
					collection.setAtDate(pettyCashCollectionTo.getAtDate());
				}
				if(pettyCashCollectionTo.getAtTime()!=null){
					collection.setAtTime(pettyCashCollectionTo.getAtTime());
				}
				if(pettyCashCollectionTo.getAcademicYear()!=null && !pettyCashCollectionTo.getAcademicYear().isEmpty()){
					collection.setAcademicYear(Integer.parseInt(pettyCashCollectionTo.getAcademicYear()));
				}
				collectionsList.add(collection);
			}
		}
		return collectionsList;
	}
	/**
	 * @param pettyCashCollectionDetailsList
	 * @return
	 * @throws Exception
	 */
	public List<PettyCashCollectionDetails> populateToTOBo( List<PettyCashCollectionDetailsTo> pettyCashCollectionDetailsList) throws Exception{
		List<PettyCashCollectionDetails> cashCollectionDetailsList = new ArrayList<PettyCashCollectionDetails>();
		if(pettyCashCollectionDetailsList!=null){
			Iterator<PettyCashCollectionDetailsTo> iterator = pettyCashCollectionDetailsList.iterator();
			while (iterator.hasNext()) {
				PettyCashCollectionDetailsTo pettyCashCollectionDetailsTo = (PettyCashCollectionDetailsTo) iterator .next();
				PettyCashCollectionDetails cashCollectionDetails =new PettyCashCollectionDetails();
				if(pettyCashCollectionDetailsTo.getAcademicYear()!=null && !pettyCashCollectionDetailsTo.getAcademicYear().isEmpty()){
					cashCollectionDetails.setAcademicYear(Integer.parseInt(pettyCashCollectionDetailsTo.getAcademicYear()));
				}
				if(pettyCashCollectionDetailsTo.getReceiptNo()!=null && !pettyCashCollectionDetailsTo.getReceiptNo().isEmpty()){
					cashCollectionDetails.setReceiptNo(Integer.parseInt(pettyCashCollectionDetailsTo.getReceiptNo()));
				}
				if(pettyCashCollectionDetailsTo.getAccCode()!=null && !pettyCashCollectionDetailsTo.getAccCode().isEmpty()){
					cashCollectionDetails.setAccCode(pettyCashCollectionDetailsTo.getAccCode());
				}
				if(pettyCashCollectionDetailsTo.getAmount()!=null && !pettyCashCollectionDetailsTo.getAmount().isEmpty()){
					cashCollectionDetails.setAmount(Float.parseFloat(pettyCashCollectionDetailsTo.getAmount()));
				}
				if(pettyCashCollectionDetailsTo.getAplRegNo()!=null && !pettyCashCollectionDetailsTo.getAplRegNo().isEmpty()){
					cashCollectionDetails.setAplRegNo(pettyCashCollectionDetailsTo.getAplRegNo());
				}
				if(pettyCashCollectionDetailsTo.getPuDgPg()!=null && !pettyCashCollectionDetailsTo.getPuDgPg().isEmpty()){
					cashCollectionDetails.setPuDgPg(pettyCashCollectionDetailsTo.getPuDgPg());
				}
				if(pettyCashCollectionDetailsTo.getDate()!=null && !pettyCashCollectionDetailsTo.getDate().toString().isEmpty()){
					cashCollectionDetails.setDate(pettyCashCollectionDetailsTo.getDate());
				}
				if(pettyCashCollectionDetailsTo.getAtStation()!=null && !pettyCashCollectionDetailsTo.getAtStation().isEmpty()){
					cashCollectionDetails.setAtStation(pettyCashCollectionDetailsTo.getAtStation());
				}
				if(pettyCashCollectionDetailsTo.getUserCode()!=null && !pettyCashCollectionDetailsTo.getUserCode().isEmpty()){
					cashCollectionDetails.setUserCode(pettyCashCollectionDetailsTo.getUserCode());
				}
				if(pettyCashCollectionDetailsTo.getAtDate()!=null && !pettyCashCollectionDetailsTo.getAtDate().toString().isEmpty()){
					cashCollectionDetails.setAtDate(pettyCashCollectionDetailsTo.getAtDate());
				}
				if(pettyCashCollectionDetailsTo.getAtTime()!=null && !pettyCashCollectionDetailsTo.getAtTime().isEmpty()){
					cashCollectionDetails.setAtTime(pettyCashCollectionDetailsTo.getAtTime());
				}
				cashCollectionDetailsList.add(cashCollectionDetails);
			}
		}
		return cashCollectionDetailsList;
	}
}
