package com.kp.cms.helpers.inventory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvAmc;
import com.kp.cms.bo.admin.InvItem;
import com.kp.cms.bo.admin.InvItemCategory;
import com.kp.cms.bo.admin.InvStockReceiptItem;
import com.kp.cms.bo.admin.InvVendor;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.inventory.AmcDetailsForm;
import com.kp.cms.forms.inventory.AmcDueIn30DaysForm;
import com.kp.cms.to.inventory.InvAmcTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.jms.MailTO;

public class AmcDetailsHelper {
	private static final Log log = LogFactory.getLog(AmcDetailsHelper.class);
	public static volatile AmcDetailsHelper amcDetailsHelper = null;
	
	public static AmcDetailsHelper getInstance() {
		if (amcDetailsHelper == null) {
			amcDetailsHelper = new AmcDetailsHelper();
			return amcDetailsHelper;
		}
		return amcDetailsHelper;
	}
	
	/**
	 * 
	 * @param amcList
	 * @return
	 */
	public List<InvAmcTO> copyBosToTos(List<InvAmc> amcList) {
		log.debug("inside copyBosToTos");
		List<InvAmcTO> invAmcTOList = new ArrayList<InvAmcTO>();
		Iterator<InvAmc> iterator = amcList.iterator();
		List<InvAmc> invAmcList = new ArrayList<InvAmc>(); 
//		Map<Integer, InvAmc> warrantyMap = new HashMap<Integer, InvAmc>();
		
		InvAmc invAmc;
//		InvAmc invAmcWarrantyDateBO;
		InvAmcTO invAmcTO;
		InvItem invItem;
		InvItemCategory invItemCategory;
		InvStockReceiptItem invStockReceiptItem;
		InvAmc amcDet; 
		
		//to remove the warranty record which is already gone to amc
		while (iterator.hasNext()){
			invAmc = (InvAmc) iterator.next();
			if(invAmc.getWarrantyAmcFlag() == 'A'){
				invAmcList.add(invAmc);
			}
			/*
			else if(invAmc.getWarrantyAmcFlag() == 'W'){
				warrantyMap.put(invAmc.getItemNo(), invAmc);
			}
			*/
		}
		Iterator<InvAmc> amcIter = invAmcList.iterator();
		iterator = amcList.iterator();
		while (iterator.hasNext()) {
			invItem = new InvItem();
			invItemCategory = new InvItemCategory();
			invStockReceiptItem = new InvStockReceiptItem();
			invAmc = (InvAmc) iterator.next();
			
			
			Boolean itemFoundInAmc = false;  //to remove the warranty record which is already gone to amc
			if(invAmc.getWarrantyAmcFlag() == 'W'){
				while (amcIter.hasNext()){
					amcDet = amcIter.next();
					if(amcDet.getInvStockReceiptItem().getId() == invAmc.getInvStockReceiptItem().getId()
						&& amcDet.getItemNo().equalsIgnoreCase(invAmc.getItemNo()) && amcDet.getInvItemCategory().getId() ==
							invAmc.getInvItemCategory().getId() && amcDet.getInvItem().getId() == invAmc.getInvItem().getId());
					 itemFoundInAmc = true;
					 break;
				}
			}
			if(itemFoundInAmc){
				continue;
			}			
			invAmcTO = new InvAmcTO();
			invAmcTO.setId(invAmc.getId());
			invAmcTO.setIsActive(invAmc.getIsActive());
			if(invAmc.getItemNo()!= null){
				invAmcTO.setItemNo(invAmc.getItemNo());
			}
			
			if(invAmc.getWarrantyStartDate()!= null){
				invAmcTO.setWarrantyStartDate(CommonUtil.formatDate(invAmc.getWarrantyStartDate(), "dd/MM/yyyy"));
			}
			if(invAmc.getWarrantyEndDate()!= null){
				invAmcTO.setWarrantyEndDate(CommonUtil.formatDate(invAmc.getWarrantyEndDate(), "dd/MM/yyyy"));
			}
			/*
			else{
				if(warrantyMap.containsKey(invAmc.getItemNo())){
					invAmcWarrantyDateBO = warrantyMap.get(invAmc.getItemNo());
					if(invAmcWarrantyDateBO.getWarrantyStartDate()!= null){
						invAmcTO.setWarrantyStartDate(CommonUtil.formatDate(invAmcWarrantyDateBO.getWarrantyStartDate(), "dd/MM/yyyy"));
					}
					if(invAmcWarrantyDateBO.getWarrantyEndDate()!= null){
						invAmcTO.setWarrantyEndDate(CommonUtil.formatDate(invAmcWarrantyDateBO.getWarrantyEndDate(), "dd/MM/yyyy"));
					}
				}
			}*/
			
			if(invAmc.getInvItem()!= null){
				invItem.setId(invAmc.getInvItem().getId());
				invItem.setCode(invAmc.getInvItem().getCode());
				invItem.setDescription(invAmc.getInvItem().getDescription());
				invItem.setName(invAmc.getInvItem().getName());
				invAmcTO.setInvItem(invItem);
			}
			if(invAmc.getInvItemCategory()!= null){
				invItemCategory.setId(invAmc.getInvItemCategory().getId());
				invItemCategory.setName(invAmc.getInvItemCategory().getName());
				invAmcTO.setInvItemCategory(invItemCategory);
			}
			if(invAmc.getInvStockReceiptItem()!= null){
				invStockReceiptItem.setId(invAmc.getInvStockReceiptItem().getId());
				invAmcTO.setInvStockReceiptItem(invStockReceiptItem);
			}
			invAmcTO.setNewWarrantyStartDate(CommonUtil.getTodayDate());
			invAmcTO.setWarrantyAmcFalg(invAmc.getWarrantyAmcFlag());
			invAmcTO.setCreatedBy(invAmc.getCreatedBy());
			invAmcTO.setCreatedDate(invAmc.getCreatedDate());
			invAmcTOList.add(invAmcTO);
		}
		log.debug("leaving copyBosToTos");
		return invAmcTOList;
	}
	
	/**
	 * copy form values to BO
	 * @param roomForm
	 * @return
	 * @throws Exception
	 */
	public List<InvAmc> populateAmcDetails(AmcDetailsForm amcDetailsForm) throws Exception {
		log.debug("populateAmcDetails");
		List<InvAmc> amcBoList = new ArrayList<InvAmc>();
		Iterator<InvAmcTO> iter = amcDetailsForm.getAmcList().iterator(); 
		InvAmcTO invAmcTO = null;
		InvVendor invVendor;
		while(iter.hasNext()){
			InvAmc invAmc = new InvAmc();
			invAmcTO = iter.next();
			if(invAmcTO.getNewWarrantyEndDate() == null || invAmcTO.getNewWarrantyEndDate().trim().isEmpty()
					|| invAmcTO.getNewWarrantyStartDate() == null || invAmcTO.getNewWarrantyEndDate().trim().isEmpty()){
				continue;
			}
			
			if(invAmcTO.getInvItem()!= null){
				invAmc.setInvItem(invAmcTO.getInvItem());
			}
			if(invAmcTO.getInvItemCategory()!= null){
				invAmc.setInvItemCategory(invAmcTO.getInvItemCategory());
			}
			if(invAmcTO.getInvStockReceiptItem()!= null){
				invAmc.setInvStockReceiptItem(invAmcTO.getInvStockReceiptItem());
			}	
			if(invAmcTO.getComments()!= null){
				invAmc.setComments(invAmcTO.getComments());
			}
			if(invAmcTO.getItemNo()!= null && !invAmcTO.getItemNo().trim().isEmpty()){
				invAmc.setItemNo(invAmcTO.getItemNo());
			}
			if(invAmcTO.getNewWarrantyStartDate()!= null){
				invAmc.setWarrantyStartDate(CommonUtil.ConvertStringToSQLDate(invAmcTO.getNewWarrantyStartDate()));
			}
			if(invAmcTO.getNewWarrantyEndDate()!= null){
				invAmc.setWarrantyEndDate(CommonUtil.ConvertStringToSQLDate(invAmcTO.getNewWarrantyEndDate()));
			}
			invVendor = new InvVendor();
			invVendor.setId(invAmcTO.getVendorId());
			invAmc.setInvVendor(invVendor);
			if(invAmcTO.getAmount()!= null && !invAmcTO.getAmount().trim().isEmpty()){
				invAmc.setAmount(BigDecimal.valueOf(Double.parseDouble(invAmcTO.getAmount())));
			}
			
			invAmc.setCreatedDate(invAmcTO.getCreatedDate());
			invAmc.setLastModifiedDate(new Date());
			invAmc.setCreatedBy(invAmcTO.getCreatedBy());
			invAmc.setModifiedBy(amcDetailsForm.getUserId());
			invAmc.setIsActive(invAmcTO.getIsActive());
			invAmc.setWarrantyAmcFlag('A');
			amcBoList.add(invAmc);
		}
		log.debug("exit populateAmcDetails");	
		return amcBoList;
		
	}	
	
	/**
	 * to show history details
	 * @param amcList
	 * @return
	 */
	public List<InvAmcTO> copyHistoryBosToTos(List<InvAmc> amcList) {
		log.debug("inside copyBosToTos");
		List<InvAmcTO> invAmcTOList = new ArrayList<InvAmcTO>();
		Iterator<InvAmc> iterator = amcList.iterator();
		
		InvAmc invAmc;
		InvAmcTO invAmcTO;
		InvItem invItem;
		InvItemCategory invItemCategory;
		InvStockReceiptItem invStockReceiptItem;
		while (iterator.hasNext()) {
			invItemCategory = new InvItemCategory();
			invStockReceiptItem = new InvStockReceiptItem();
			invAmc = (InvAmc) iterator.next();
			invAmcTO = new InvAmcTO();
			invAmcTO.setId(invAmc.getId());
			invAmcTO.setIsActive(invAmc.getIsActive());
			if(invAmc.getItemNo()!= null && !invAmc.getItemNo().trim().isEmpty()){
				invAmcTO.setItemNo(invAmc.getItemNo());
			}
			
			if(invAmc.getComments()!= null){
				invAmcTO.setComments(invAmc.getComments());
			}
			
			if(invAmc.getWarrantyStartDate()!= null){
				invAmcTO.setWarrantyStartDate(CommonUtil.formatDate(invAmc.getWarrantyStartDate(), "dd/MM/yyyy"));
			}
			if(invAmc.getWarrantyEndDate()!= null){
				invAmcTO.setWarrantyEndDate(CommonUtil.formatDate(invAmc.getWarrantyEndDate(), "dd/MM/yyyy"));
			}
			if(invAmc.getInvItem()!= null){
				invItem = new InvItem();
				invItem.setId(invAmc.getInvItem().getId());
				invItem.setCode(invAmc.getInvItem().getCode());
				invItem.setDescription(invAmc.getInvItem().getDescription());
				invItem.setName(invAmc.getInvItem().getName());
				invAmcTO.setInvItem(invItem);
			}
			if(invAmc.getInvItemCategory()!= null){
				invItemCategory.setId(invAmc.getInvItemCategory().getId());
				invItemCategory.setName(invAmc.getInvItemCategory().getName());
				invAmcTO.setInvItemCategory(invItemCategory);
			}
			if(invAmc.getInvStockReceiptItem()!= null){
				invStockReceiptItem.setId(invAmc.getInvStockReceiptItem().getId());
				invAmcTO.setInvStockReceiptItem(invStockReceiptItem);
			}
			invAmcTO.setWarrantyAmcFalg(invAmc.getWarrantyAmcFlag());
			invAmcTO.setCreatedBy(invAmc.getCreatedBy());
			invAmcTO.setCreatedDate(invAmc.getCreatedDate());
			if(invAmc.getInvStockReceiptItem()!= null && invAmc.getInvStockReceiptItem().getInvStockReceipt()!= null && invAmc.getInvStockReceiptItem().getInvStockReceipt().getInvLocation()!= null&& invAmc.getInvStockReceiptItem().getInvStockReceipt().getInvLocation().getName()!= null){
				invAmcTO.setLocationName(invAmc.getInvStockReceiptItem().getInvStockReceipt().getInvLocation().getName());
			}
			
			if(invAmc.getInvVendor()!= null && invAmc.getInvVendor().getName()!= null && !invAmc.getInvVendor().getName().trim().isEmpty()){
				invAmcTO.setVendorname(invAmc.getInvVendor().getName());
			}
			
			if(invAmc.getWarrantyAmcFlag() == 'W'){
				invAmcTO.setAmcWarranty("Warranty");
			}
			else
			{
				invAmcTO.setAmcWarranty("Amc");
			}
			invAmcTOList.add(invAmcTO);
		}
		log.debug("leaving copyBosToTos");
		return invAmcTOList;
	}
	
	/**
	 * 
	 * @param amcList
	 * @return
	 */
	public List<InvAmcTO> copyWarrantyMailBosToTos(List<InvAmc> amcList) {
		log.debug("inside copyWarrantyMailBosToTos");
		List<InvAmcTO> invAmcTOList = new ArrayList<InvAmcTO>();
		Iterator<InvAmc> iterator = amcList.iterator();
		
		InvAmc invAmc;
		InvAmcTO invAmcTO;
		InvItem invItem;
		while (iterator.hasNext()) {
			invAmc = (InvAmc) iterator.next();
			invAmcTO = new InvAmcTO();
			invAmcTO.setId(invAmc.getId());
			if(invAmc.getItemNo()!= null && !invAmc.getItemNo().trim().isEmpty()){
				invAmcTO.setItemNo(invAmc.getItemNo());
			}
			if(invAmc.getWarrantyEndDate()!= null){
				invAmcTO.setWarrantyEndDate(CommonUtil.formatDate(invAmc.getWarrantyEndDate(), "dd/MM/yyyy"));
			}
			if(invAmc.getInvItem()!= null){
				invItem = new InvItem();
				invItem.setId(invAmc.getInvItem().getId());
				invItem.setCode(invAmc.getInvItem().getCode());
				invItem.setName(invAmc.getInvItem().getName());
				invAmcTO.setInvItem(invItem);
			}
			if(invAmc.getInvStockReceiptItem()!= null && invAmc.getInvStockReceiptItem().getInvStockReceipt()!= null && invAmc.getInvStockReceiptItem().getInvStockReceipt().getInvLocation()!= null && invAmc.getInvStockReceiptItem().getInvStockReceipt().getInvLocation().getName()!= null){
				invAmcTO.setLocationName(invAmc.getInvStockReceiptItem().getInvStockReceipt().getInvLocation().getName());
			}
			if(invAmc.getInvStockReceiptItem()!= null && invAmc.getInvStockReceiptItem().getInvStockReceipt()!= null && invAmc.getInvStockReceiptItem().getInvStockReceipt().getInvLocation()!= null && invAmc.getInvStockReceiptItem().getInvStockReceipt().getInvLocation().getEmployee()!= null && invAmc.getInvStockReceiptItem().getInvStockReceipt().getInvLocation().getEmployee().getEmail()!= null){
				invAmcTO.setToMailAddress(invAmc.getInvStockReceiptItem().getInvStockReceipt().getInvLocation().getEmployee().getEmail());
			}
			if(invAmc.getInvVendor()!= null && invAmc.getInvVendor().getName()!= null && !invAmc.getInvVendor().getName().trim().isEmpty()){
				invAmcTO.setVendorname(invAmc.getInvVendor().getName());
			}
			
			invAmcTOList.add(invAmcTO);
		}
		log.debug("leaving copyBosToTos");
		return invAmcTOList;
	}
	
	/**
	 * Send the mail for selected students.
	 * @param form
	 * @throws ApplicationException
	 */
	public boolean sendWarrantyExpiryMail(AmcDueIn30DaysForm amcDueIn30DaysForm) throws ApplicationException {
		log.info("entering into sendWarrantyExpiryMail in AmcDetailsHelper class.");
		boolean mailSend = true;
		String template = "";
		String toAddress = "";
		Properties prop = new Properties();
		try {
			InputStream in = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(in);
		} catch (FileNotFoundException e) {
			throw new ApplicationException(e);

		} catch (IOException e) {
			throw new ApplicationException(e);
		}
//		String fromaddress = prop.getProperty("knowledgepro.admin.mail");
		String fromaddress = CMSConstants.MAIL_USERID;
		String fromname = prop.getProperty("knowledgepro.admin.mailfrom");
		
		if(amcDueIn30DaysForm.getTemplateDescription()!=null){
			 template = amcDueIn30DaysForm.getTemplateDescription();
		}
		if(amcDueIn30DaysForm.getAmcTOList()!=null){
			Iterator<InvAmcTO> sendMailIterator = amcDueIn30DaysForm.getAmcTOList().iterator();
			
			while (sendMailIterator.hasNext()) {
				InvAmcTO sendMailDetails = (InvAmcTO) sendMailIterator.next();
		
				if(sendMailDetails.isSendMailSelected()){
					template = template.replace(CMSConstants.TEMPLATE_ITEM_NO, sendMailDetails.getItemNo());
					if(sendMailDetails.getInvItem()!= null){
						template = template.replace(CMSConstants.TEMPLATE_ITEM_NAME, sendMailDetails.getInvItem().getName());
					}
					template = template.replace(CMSConstants.TEMPLATE_ITEM_DOE, sendMailDetails.getWarrantyEndDate());
					if(sendMailDetails.getLocationName()!= null){
						template = template.replace(CMSConstants.TEMPLATE_ITEM_INV_LOCATION, sendMailDetails.getLocationName());
					}
					if(sendMailDetails.getVendorname()!=null){
						template = template.replace(CMSConstants.TEMPLATE_ITEM_VENDOR_NAME, sendMailDetails.getVendorname());
					}
					if(sendMailDetails.getToMailAddress()!= null){
						toAddress = sendMailDetails.getToMailAddress();
					}	
					
					MailTO mailto = new MailTO();
					mailto.setFromAddress(fromaddress);
					mailto.setFromName(fromname);
					mailto.setToAddress(toAddress);
					mailto.setMessage(template);
					mailto.setSubject(CMSConstants.TEMPLATE_ITEM_MAIL_SUBJECT);
					mailSend = CommonUtil.sendMail(mailto);
					if (!mailSend) {
						String subject = "Problem occured while sending mail to "
								+ toAddress;
						String message = "There was an error while sending mail to "
								+ toAddress
								+ ", So i am afraid i couldn't send this mail.";
						MailTO errorMail = new MailTO();
						errorMail.setFromAddress(fromaddress);
						errorMail.setFromName(fromname);
						errorMail.setToAddress(fromaddress);
						errorMail.setSubject(subject);
						errorMail.setMessage(message);
						mailSend = CommonUtil.sendMail(mailto);

						mailSend = false;
					}
				}
			}
		}
		log.info("exit of sendWarrantyExpiryMail in AmcDetailsHelper class.");
		return mailSend;
	}
}