package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Designation;
import com.kp.cms.forms.admin.DesignationEntryForm;
import com.kp.cms.to.admin.DesignationEntryTo;
import com.kp.cms.transactions.admin.IDesignationEntryTransaction;
import com.kp.cms.transactionsimpl.admin.DesignationEntryTransactionImpl;


public class DesignationEntryHandler {
	
	private static final Log log = LogFactory.getLog(DesignationEntryHandler.class);
	public static volatile DesignationEntryHandler designationOrderHandler = null;

	public static DesignationEntryHandler getInstance() {
		if (designationOrderHandler == null) {
			designationOrderHandler = new DesignationEntryHandler();
			return designationOrderHandler;
		}
		return designationOrderHandler;
	}
	
	public List<DesignationEntryTo> getDesignationEntry() throws Exception {
		List<DesignationEntryTo> designationList = new ArrayList<DesignationEntryTo>();
		IDesignationEntryTransaction iDesignationOrderTransaction = DesignationEntryTransactionImpl.getInstance();
		List<Designation> list = iDesignationOrderTransaction.getDesignationEntry();
		Iterator<Designation> itr = list.iterator();
		Designation designation;
		DesignationEntryTo designationEntryTo;
		while (itr.hasNext()) {
			designation = (Designation) itr.next();
			designationEntryTo = new DesignationEntryTo();
			designationEntryTo.setId(designation.getId());
			designationEntryTo.setName(designation.getName());
			designationEntryTo.setOrder(designation.getOrder());
			designationList.add(designationEntryTo);
		}
		log.info("end of getCurrencyMasterDetails method in CurrencyMasterHandler class.");
		return designationList;
	}

	public Designation isNameExist(String name, int id) throws Exception{
		IDesignationEntryTransaction designationEntryTransaction = DesignationEntryTransactionImpl.getInstance();
		Designation designation = designationEntryTransaction.isNameExist(name, id);
		log.info("end of isNameExist method in DesignationEntryHandler class.");
		return designation;
	}

	public Designation isorderExist(String order, int id)  throws Exception{
		IDesignationEntryTransaction designationEntryTransaction = DesignationEntryTransactionImpl.getInstance();
		Designation designation = designationEntryTransaction.isorderExist(order, id);
		log.info("end of isorderExist method in DesignationEntryHandler class.");
		return designation;
	}

	public boolean addDesignationEntry(DesignationEntryForm designationEntryForm)throws Exception{
		IDesignationEntryTransaction designationEntryTransaction = DesignationEntryTransactionImpl.getInstance();
		Designation designation = new Designation();
		designation.setCreatedDate(new Date());
		designation.setOrder(designationEntryForm.getOrder());
		designation.setIsActive(Boolean.TRUE);
		designation.setName(designationEntryForm.getName());
		designation.setCreatedBy(designationEntryForm.getUserId());
		designation.setModifiedBy(designationEntryForm.getUserId());
		designation.setLastModifiedDate(new Date());
		boolean isAdded = designationEntryTransaction.addDesignationEntry(designation);
		log.info("end of addDesignationEntry method in DesignationEntryHandler class.");
		return isAdded;
	}

	public DesignationEntryTo editDesignationEntry(int id) throws Exception{
		DesignationEntryTo designationEntryTo = new DesignationEntryTo();
		IDesignationEntryTransaction designationEntryTransaction = DesignationEntryTransactionImpl.getInstance();
		Designation designation = designationEntryTransaction.editDesignationEntry(id);
		designationEntryTo.setId(designation.getId());
		designationEntryTo.setName(designation.getName());
		if(designation.getOrder()!=null)
		{
			designationEntryTo.setOrder(designation.getOrder());	
		}
	
		log.info("end of editDesignationEntry method in DesignationEntryHandler class.");
		return designationEntryTo;
	}

	public boolean updateDesignationEntry(DesignationEntryForm designationEntryForm) throws Exception {
		IDesignationEntryTransaction designationEntryTransaction = DesignationEntryTransactionImpl.getInstance();
		Designation designation = new Designation();
		designation.setId(designationEntryForm.getId());
		designation.setName(designationEntryForm.getName());
		designation.setOrder(designationEntryForm.getOrder());
		designation.setModifiedBy(designationEntryForm.getUserId());
		designation.setLastModifiedDate(new Date());
		designation.setIsActive(Boolean.TRUE);
		boolean isUpdated = designationEntryTransaction.updatedesignationEntry(designation);
		log.info("end of updateDesignationEntry method in DesignationEntryHandler class.");
		return isUpdated;
	}

	public boolean deleteDesignationEntry(int id, String UserId) throws Exception {
		IDesignationEntryTransaction designationEntryTransaction = DesignationEntryTransactionImpl.getInstance();
		boolean isDeleted = designationEntryTransaction.deleteDesignationEntry(id, UserId);
		log.info("end of deleteDesignationEntry method in DesignationEntryHandler class.");
		return isDeleted;
	}

	public boolean reActivateDesignationEntry(String name, String UserId,int id) throws Exception{
		IDesignationEntryTransaction designationEntryTransaction = DesignationEntryTransactionImpl.getInstance();
		boolean isReactivated = designationEntryTransaction.reActivateDesignationEntry(name, UserId,id);
		log.info("end of reActivateDesignationEntry method in DesignationEntryHandler class.");
		return isReactivated;
	}

	

}
