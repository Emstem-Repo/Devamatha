package com.kp.cms.helpers.inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.InvItemCategory;
import com.kp.cms.bo.admin.InvVendor;
import com.kp.cms.bo.admin.InvVendorItemCategory;
import com.kp.cms.bo.admin.State;
import com.kp.cms.forms.inventory.VendorForm;
import com.kp.cms.to.inventory.VendorTO;

public class VendorHelper {
	public static Log log = LogFactory.getLog(VendorHelper.class);
	public static volatile VendorHelper vendorHelper;
	
	public static VendorHelper getInstance(){
		if(vendorHelper == null){
			vendorHelper = new VendorHelper();
			return vendorHelper;
		}
		return vendorHelper;
	}
	
		
	/**
	 * 
	 * @param vendorForm
	 * @return
	 * @throws Exception
	 */
	public InvVendor copyFromFormToBO(VendorForm vendorForm) throws Exception {
		log.debug("inside copyFromFormToBO");
		InvVendor invVendor = new InvVendor();
		String selectedCategory[] = vendorForm.getSelectedCategory(); 
		
		Set<InvVendorItemCategory> invVendorItemCategorySet = new HashSet<InvVendorItemCategory>();
		if(selectedCategory != null){
			for (int x = 0; x < selectedCategory.length; x++){
				InvVendorItemCategory invVendorItemCategory = new InvVendorItemCategory();
				InvItemCategory invItemCategory = new InvItemCategory();
				invItemCategory.setId(Integer.parseInt(selectedCategory[x]));
				invVendorItemCategory.setInvItemCategory(invItemCategory);
				invVendorItemCategory.setIsActive(true);
				invVendorItemCategory.setCreatedBy(vendorForm.getUserId());
				invVendorItemCategory.setModifiedBy(vendorForm.getUserId());
				invVendorItemCategory.setCreatedDate(new Date());
				invVendorItemCategory.setLastModifiedDate(new Date());					
				invVendorItemCategorySet.add(invVendorItemCategory);
			}
		}
		invVendor.setInvVendorItemCategories(invVendorItemCategorySet);
		invVendor.setAddressLine1(vendorForm.getAddressLine1());
		if(vendorForm.getAddressLine2()!= null){
			invVendor.setAddressLine2(vendorForm.getAddressLine2());
		}
		if(vendorForm.getBankAcNo()!= null){
			invVendor.setBankAccountNo(vendorForm.getBankAcNo());
		}
		if(vendorForm.getBankBranch()!= null){
			invVendor.setBankBranch(vendorForm.getBankBranch());
		}
		invVendor.setCity(vendorForm.getCity());
		invVendor.setContactPerson(vendorForm.getContactPerson());
		if(vendorForm.getCountryId()!= null && !vendorForm.getCountryId().trim().isEmpty()){
			Country country = new Country();
			country.setId(Integer.parseInt(vendorForm.getCountryId()));
			invVendor.setCountry(country);
		}
		if(vendorForm.getDeliverySchedule()!= null){
			invVendor.setDeliverySchedule(vendorForm.getDeliverySchedule());
		}
		if(vendorForm.getEmailId()!= null){
			invVendor.setEmail(vendorForm.getEmailId());
		}
		if(vendorForm.getFax()!= null){
			invVendor.setFaxNo(vendorForm.getFax());
		}
		invVendor.setIsActive(true);
		invVendor.setIsAuthorized(Boolean.valueOf(vendorForm.getAuthorisedVendor()));
		if(vendorForm.getMobile()!= null){
			invVendor.setMobile(vendorForm.getMobile());
		}
		if(vendorForm.getMobileCountryCode()!=null && !vendorForm.getMobileCountryCode().isEmpty()){
			invVendor.setMobile1(vendorForm.getMobileCountryCode());
		}
		if(vendorForm.getPhoneCountryCode()!=null && !vendorForm.getPhoneCountryCode().isEmpty()){
			invVendor.setPhone1(vendorForm.getPhoneCountryCode());
		}
		if(vendorForm.getPhoneStateCode()!=null && !vendorForm.getPhoneStateCode().isEmpty()){
			invVendor.setPhone2(vendorForm.getPhoneStateCode());
		}
		invVendor.setName(vendorForm.getVendorName());
		if(vendorForm.getPan()!= null){
			invVendor.setPanNo(vendorForm.getPan());
		}
		if(vendorForm.getPaymentMode()!= null){
			invVendor.setPaymentMode(vendorForm.getPaymentMode());
		}
		if(vendorForm.getPaymentTerms()!= null){
			invVendor.setPaymentTerms(vendorForm.getPaymentTerms());
		}
		if(vendorForm.getPhone()!= null){
			invVendor.setPhone(vendorForm.getPhone());
		}
		if(vendorForm.getPin()!= null){
			invVendor.setPin(vendorForm.getPin());
		}
		if(vendorForm.getRemarks()!=null){
			invVendor.setRemarks(vendorForm.getRemarks());
		}
		if(vendorForm.getStateId().equalsIgnoreCase("other")){
			invVendor.setStateOthers(vendorForm.getStateOthers());
		}else{
			if(vendorForm.getStateId()!= null && !vendorForm.getStateId().trim().isEmpty()){
				State state = new State();
				state.setId(Integer.parseInt(vendorForm.getStateId()));
				invVendor.setState(state);
			}
		}
		if(vendorForm.getTan()!= null){
			invVendor.setTanNo(vendorForm.getTan());
		}
		if(vendorForm.getVat()!= null){
			invVendor.setVat(vendorForm.getVat());
		}
		if(vendorForm.getZipCode()!= null && !vendorForm.getZipCode().trim().isEmpty()){
			invVendor.setZipCode(Integer.parseInt(vendorForm.getZipCode()));
		}
		invVendor.setCreatedBy(vendorForm.getUserId());
		invVendor.setModifiedBy(vendorForm.getUserId());
		invVendor.setCreatedDate(new Date());
		invVendor.setLastModifiedDate(new Date());		
	
		log.debug("leaving copyFromFormToBO");
		return invVendor;
		
	}
	
	/**
	 * copying BO values to TO
	 * @param vendorTOList
	 * @return
	 */
	public List<VendorTO> copyVendorBosToTos(List<InvVendor> vendorList) {
		log.debug("inside copyVendorBosToTos");
		List<VendorTO> vendorTOList = new ArrayList<VendorTO>();
		Iterator<InvVendor> iterator = vendorList.iterator();
		VendorTO vendorTO;
		InvVendor invVendor;
		while (iterator.hasNext()) {
			vendorTO = new VendorTO();
			invVendor = iterator.next();
			vendorTO.setId(invVendor.getId());
			vendorTO.setContactPerson(invVendor.getContactPerson());
			vendorTO.setVendorAddressLine1(invVendor.getAddressLine1());
			if(invVendor.getAddressLine2()!= null){
				vendorTO.setVendorAddressLine2(invVendor.getAddressLine2());
			}
			vendorTO.setVendorName(invVendor.getName());
			vendorTOList.add(vendorTO);
		}
		Collections.sort(vendorTOList);
		return vendorTOList;
	}
	
	/**
	 * 
	 * @param vendorForm
	 * @return
	 * @throws Exception
	 */
	public InvVendor copyFromFormToBOForUpdate(VendorForm vendorForm)throws Exception{
		log.info("Inside CopyFormToBOForUpdate");
		//Gets the updated values from formbean and setting those to BO object
		InvVendor invVendor = new InvVendor();
		InvVendorItemCategory invVendorItemCategory = null;
		InvVendorItemCategory invVendorItemCategoryBo = null;
		InvItemCategory invItemCategory = null;
		
		invVendor.setId(vendorForm.getId());
		invVendor.setAddressLine1(vendorForm.getAddressLine1());
		if(vendorForm.getMobileCountryCode()!=null && !vendorForm.getMobileCountryCode().isEmpty()){
			invVendor.setMobile1(vendorForm.getMobileCountryCode());
		}
		if(vendorForm.getPhoneCountryCode()!=null && !vendorForm.getPhoneCountryCode().isEmpty()){
			invVendor.setPhone1(vendorForm.getPhoneCountryCode());
		}
		if(vendorForm.getPhoneStateCode()!=null && !vendorForm.getPhoneStateCode().isEmpty()){
			invVendor.setPhone2(vendorForm.getPhoneStateCode());
		}
		if(vendorForm.getAddressLine2()!= null){
			invVendor.setAddressLine2(vendorForm.getAddressLine2());
		}
		if(vendorForm.getBankAcNo()!= null){
			invVendor.setBankAccountNo(vendorForm.getBankAcNo());
		}
		if(vendorForm.getBankBranch()!= null){
			invVendor.setBankBranch(vendorForm.getBankBranch());
		}
		if(vendorForm.getRemarks()!=null){
			invVendor.setRemarks(vendorForm.getRemarks());
		}
		invVendor.setCity(vendorForm.getCity());
		invVendor.setContactPerson(vendorForm.getContactPerson());
		if(vendorForm.getCountryId()!= null && !vendorForm.getCountryId().trim().isEmpty()){
			Country country = new Country();
			country.setId(Integer.parseInt(vendorForm.getCountryId()));
			invVendor.setCountry(country);
		}
		if(vendorForm.getDeliverySchedule()!= null){
			invVendor.setDeliverySchedule(vendorForm.getDeliverySchedule());
		}
		if(vendorForm.getEmailId()!= null){
			invVendor.setEmail(vendorForm.getEmailId());
		}
		if(vendorForm.getFax()!= null){
			invVendor.setFaxNo(vendorForm.getFax());
		}
		invVendor.setIsActive(true);
		invVendor.setIsAuthorized(Boolean.valueOf(vendorForm.getAuthorisedVendor()));
		if(vendorForm.getMobile()!= null){
			invVendor.setMobile(vendorForm.getMobile());
		}
		invVendor.setName(vendorForm.getVendorName());
		if(vendorForm.getPan()!= null){
			invVendor.setPanNo(vendorForm.getPan());
		}
		if(vendorForm.getPaymentMode()!= null){
			invVendor.setPaymentMode(vendorForm.getPaymentMode());
		}
		if(vendorForm.getPaymentTerms()!= null){
			invVendor.setPaymentTerms(vendorForm.getPaymentTerms());
		}
		if(vendorForm.getPhone()!= null){
			invVendor.setPhone(vendorForm.getPhone());
		}
		if(vendorForm.getPin()!= null){
			invVendor.setPin(vendorForm.getPin());
		}
		if(vendorForm.getStateId().equalsIgnoreCase("other")){
			invVendor.setStateOthers(vendorForm.getStateOthers());
		}else{
			if(vendorForm.getStateId()!= null && !vendorForm.getStateId().trim().isEmpty()){
				State state = new State();
				state.setId(Integer.parseInt(vendorForm.getStateId()));
				invVendor.setState(state);
			}
		}
		if(vendorForm.getTan()!= null){
			invVendor.setTanNo(vendorForm.getTan());
		}
		if(vendorForm.getVat()!= null){
			invVendor.setVat(vendorForm.getVat());
		}
		if(vendorForm.getZipCode()!= null && !vendorForm.getZipCode().trim().isEmpty()){
			invVendor.setZipCode(Integer.parseInt(vendorForm.getZipCode()));
		}
		invVendor.setCreatedBy(vendorForm.getUserId());
		invVendor.setModifiedBy(vendorForm.getUserId());
		invVendor.setCreatedDate(new Date());
		invVendor.setLastModifiedDate(new Date());		
		
		Set<InvVendorItemCategory> invVendorItemCategorySet= new HashSet<InvVendorItemCategory>();
		int categorySize = 0;
		if(vendorForm.getSelectedCategory() != null ){
			categorySize = vendorForm.getSelectedCategory().length;
		}
		
		String categoryArray[] = vendorForm.getSelectedCategory(); 
		InvVendor inVendor = vendorForm.getInvVendor();
		
		Set<InvVendorItemCategory> invVendorSet= inVendor.getInvVendorItemCategories();
		boolean inserted = false;
		for (int i = 0; i < categorySize; i++) {
			invVendorItemCategoryBo = new InvVendorItemCategory();
			invItemCategory = new InvItemCategory();
			Iterator<InvVendorItemCategory> iterator = invVendorSet.iterator();
			while (iterator.hasNext()) {
				invVendorItemCategory = iterator.next();					
				// if true - will not created new record keeps the previous state
				if(invVendorItemCategory.getInvItemCategory().getId() == Integer.parseInt(categoryArray[i])) {
					invVendorItemCategoryBo.setId(invVendorItemCategory.getId());
					invItemCategory.setId(invVendorItemCategory.getInvItemCategory().getId());
					invVendorItemCategoryBo.setInvItemCategory(invItemCategory);
					invVendorItemCategoryBo.setIsActive(true);
					invVendorItemCategoryBo.setCreatedBy(vendorForm.getUserId());
					invVendorItemCategoryBo.setModifiedBy(vendorForm.getUserId());
					invVendorItemCategoryBo.setCreatedDate(new Date());
					invVendorItemCategoryBo.setLastModifiedDate(new Date());		
					inserted = true;	
				}
			}
			if(!inserted) {
				// this works when new category added.
				invItemCategory.setId(Integer.parseInt(categoryArray[i]));
				invVendorItemCategoryBo.setInvItemCategory(invItemCategory);
				invVendorItemCategoryBo.setCreatedBy(vendorForm.getUserId());
				invVendorItemCategoryBo.setModifiedBy(vendorForm.getUserId());
				invVendorItemCategoryBo.setCreatedDate(new Date());
				invVendorItemCategoryBo.setLastModifiedDate(new Date());		
				invVendorItemCategoryBo.setIsActive(true);
			}
			invVendorItemCategorySet.add(invVendorItemCategoryBo);
			inserted = false;
			}
		boolean deleted = true;
		Iterator<InvVendorItemCategory> it1=invVendorSet.iterator();
		while (it1.hasNext()) {
			invVendorItemCategory = it1.next();
			deleted = true;
			for (int i = 0; i < categorySize; i++) {
				//Checks the old and new ctageory
				if(invVendorItemCategory.getInvItemCategory().getId() == Integer.parseInt(categoryArray[i])) {
					deleted = false;
					break;
				}
			}
			// below condition checks for deletion
			if(deleted) {
				invVendorItemCategoryBo = new InvVendorItemCategory();
				invItemCategory = new InvItemCategory();
				invVendorItemCategoryBo.setId(invVendorItemCategory.getId());
				invItemCategory.setId(invVendorItemCategory.getInvItemCategory().getId());
				invVendorItemCategoryBo.setInvItemCategory(invItemCategory);
				invVendorItemCategoryBo.setCreatedBy(vendorForm.getUserId());
				invVendorItemCategoryBo.setModifiedBy(vendorForm.getUserId());
				invVendorItemCategoryBo.setCreatedDate(new Date());
				invVendorItemCategoryBo.setLastModifiedDate(new Date());						
				invVendorItemCategoryBo.setIsActive(false);
				
				invVendorItemCategorySet.add(invVendorItemCategoryBo);
			}
		}	
		invVendor.setInvVendorItemCategories(invVendorItemCategorySet);
		log.info("leaving CopyFormToBOForUpdate in helper");
		return invVendor;
	}	
}
