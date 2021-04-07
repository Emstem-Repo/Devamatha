package com.kp.cms.helpers.pettycash;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PcAccountHead;
import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.bo.admin.PcReceipts;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.forms.pettycash.ModifyCashCollectionForm;
import com.kp.cms.to.pettycash.AccountHeadTO;
import com.kp.cms.to.pettycash.CashCollectionTO;
import com.kp.cms.transactions.pettycash.ICashCollectionTransaction;
import com.kp.cms.transactions.pettycash.IModifyCashCollectionTransaction;
import com.kp.cms.transactionsimpl.pettycash.CashCollectionTransactionImpl;
import com.kp.cms.transactionsimpl.pettycash.ModifyCashCollectionTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class ModifyCashCollectionHelper {
	private static Log log = LogFactory
			.getLog(ModifyCashCollectionHelper.class);
	public static volatile ModifyCashCollectionHelper modifyCashCollectionHelper = null;

	public static ModifyCashCollectionHelper getInstance() {
		if (modifyCashCollectionHelper == null) {
			modifyCashCollectionHelper = new ModifyCashCollectionHelper();
			return modifyCashCollectionHelper;
		}
		return modifyCashCollectionHelper;
	}

	public List<CashCollectionTO> covertBoListToToList(
			List<PcReceipts> pcReceiptlist) throws Exception {
		List<CashCollectionTO> cashCollectionTolist = new ArrayList<CashCollectionTO>();
		double total=0.0;
		if (pcReceiptlist != null && !pcReceiptlist.isEmpty()) {
			Iterator<PcReceipts> pcit = pcReceiptlist.iterator();
			while (pcit.hasNext()) {
				PcReceipts pcReceipts = null;
				pcReceipts = pcit.next();
				CashCollectionTO cashCollectionTo = new CashCollectionTO();
				cashCollectionTo.setId(pcReceipts.getId());
				cashCollectionTo.setNumber(String.valueOf(pcReceipts.getNumber()));
				cashCollectionTo.setPaidDateTime(pcReceipts.getPaidDate()
						.toString());
				cashCollectionTo.setRefNo(pcReceipts.getRefNo());
				cashCollectionTo.setReftype(pcReceipts.getRefType());
				cashCollectionTo.setName(pcReceipts.getName());
				cashCollectionTo.setAcademicYear(pcReceipts.getAcademicYear()
						.toString());
				if(pcReceipts.getStudent()!= null){
				cashCollectionTo.setStudent(pcReceipts.getStudent());}
				if(pcReceipts.getPcAccountHead()!= null){
				cashCollectionTo.setAccountCode(pcReceipts.getPcAccountHead().getAccCode());
				cashCollectionTo.setAccName(pcReceipts.getPcAccountHead().getAccName());
				cashCollectionTo.setPcAccountHead(pcReceipts.getPcAccountHead());
				}
				if(pcReceipts.getAmount()!=null){
					cashCollectionTo.setAmount(pcReceipts.getAmount().toString());
					total = total + Double.valueOf(pcReceipts.getAmount().doubleValue());
				}
				cashCollectionTo.setDetails(pcReceipts.getDetails());
				cashCollectionTo.setTotal(String.valueOf(total));
				if(pcReceipts.getPcFinancialYear()!=null)
				{
				cashCollectionTo.setPcFinancialYear(pcReceipts
						.getPcFinancialYear());
				}
				
				cashCollectionTo.setIsCancelled(pcReceipts.getIsCancelled()
						.toString());
				cashCollectionTolist.add(cashCollectionTo);
			}
		}
		return cashCollectionTolist;
	}

	public List<AccountHeadTO> getAccountHeadList(
			List<CashCollectionTO> toList,ModifyCashCollectionForm form) throws Exception {
		List<PcAccountHead> accountList = new ArrayList<PcAccountHead>();
		Map<Integer, AccountHeadTO> accountHeadMap = new HashMap<Integer, AccountHeadTO>();
		Set<Integer> accIDSet = new HashSet<Integer>();

		List<AccountHeadTO> accountHeadlist = new ArrayList<AccountHeadTO>();
		
		if (toList != null && !toList.isEmpty()) {
			Iterator<CashCollectionTO> toIt = toList.iterator();
			while (toIt.hasNext()) {
				CashCollectionTO collectionto = toIt.next();	
					AccountHeadTO accountTo = new AccountHeadTO();
					accountTo.setPcReceiptId(String.valueOf(collectionto.getId()));
					
					if(collectionto.getPcAccountHead()!=null){						
						accountTo.setAccid(collectionto.getPcAccountHead().getId());
						if(collectionto.getPcAccountHead().getAccCode()!=null){
							accountTo.setAccCode(collectionto.getPcAccountHead().getAccCode());
						}
						if(collectionto.getPcAccountHead().getAccName()!=null){
						accountTo.setAccName(collectionto.getPcAccountHead().getAccName());
						}
						/*if(collectionto.getPcAccountHead().getAmount()!=null){
							accountTo.setAmount(collectionto.getPcAccountHead().getAmount().toString());
						}*/
						if(collectionto.getAmount()!=null){
						accountTo.setAmount(collectionto.getAmount());
						}
						accountTo.setDetails(collectionto.getDetails());
						accountHeadMap.put(collectionto.getPcAccountHead().getId(), accountTo);
						accIDSet.add(collectionto.getPcAccountHead().getId());
					}
					
					accountHeadlist.add(accountTo);
					
					
				PcAccountHead accountHead = collectionto.getPcAccountHead();
				accountList.add(accountHead);
			}
		}
		form.setAccIDSet(accIDSet);
		form.setAccountHeadMap(accountHeadMap);
		return accountHeadlist;
	}

	public ModifyCashCollectionForm setAllDataToForm(
			ModifyCashCollectionForm form, List<CashCollectionTO> toList)
			throws Exception {

		form.setAccountHeadListToDisplay(getAccountHeadList(toList,form));
		int count=0;
		
		Iterator<CashCollectionTO> toit = toList.iterator();
		Set<Integer> idSet = new HashSet<Integer>();
		while (toit.hasNext()) {
			CashCollectionTO cashCollectionTo = toit.next();
			idSet.add(cashCollectionTo.getId());
			form.setRecNoResult(cashCollectionTo.getNumber());
			form.setRecId(cashCollectionTo.getId());
			String dateString = cashCollectionTo.getPaidDateTime().substring(0, 10);
			String inputDateFormat = "yyyy-mm-dd";
			String outPutdateFormat = "dd/mm/yyyy";
			form.setPaidDate(CommonUtil.ConvertStringToDateFormat(dateString, inputDateFormat, outPutdateFormat));
			form.setHours(cashCollectionTo.getPaidDateTime().substring(11, 13));
			form.setMinutes(cashCollectionTo.getPaidDateTime().substring(14,16));
			form.setAppRegRollno(cashCollectionTo.getRefNo());
			form.setAppNo(cashCollectionTo.getReftype());
			form.setAcademicYear(cashCollectionTo.getAcademicYear());
			form.setNameOfStudent(cashCollectionTo.getName());
			form.setTotal(cashCollectionTo.getTotal());
			if(count==0 && cashCollectionTo.getPcFinancialYear()!=null)
				form.setFinancialYearId(cashCollectionTo.getPcFinancialYear().getId());
			
			count++;	
		}
		form.setIdSet(idSet);
		return form;
	}

	public List<CashCollectionTO> convertAccDetailsToTo(
			List<PcAccountHead> accDetilasBoList,
			ModifyCashCollectionForm modifyCashCollectionform) throws Exception {
		Map<Integer, CashCollectionTO> accountMap = new HashMap<Integer, CashCollectionTO>();
		List<CashCollectionTO> accNameWithCodeToList = new ArrayList<CashCollectionTO>();
		if (accDetilasBoList != null && !accDetilasBoList.isEmpty()) {
			CashCollectionTO cashCollectionTo = null;
			Iterator<PcAccountHead> accIt = accDetilasBoList.iterator();
			while (accIt.hasNext()) {
				PcAccountHead pcAccountHead = (PcAccountHead) accIt.next();
				cashCollectionTo = new CashCollectionTO();
				cashCollectionTo.setPcAccountHead(pcAccountHead);
				cashCollectionTo.setAccountCode(pcAccountHead.getAccCode());
				cashCollectionTo.setAccName(pcAccountHead.getAccName());
				cashCollectionTo.setAmount(String.valueOf(pcAccountHead
						.getAmount()));
				cashCollectionTo.setId(pcAccountHead.getId());
				cashCollectionTo.setNameWithCode(pcAccountHead.getAccName()
						+ "(" + pcAccountHead.getAccCode() + ")");
				accNameWithCodeToList.add(cashCollectionTo);
				accountMap.put(pcAccountHead.getId(), cashCollectionTo);
			}

		}
		modifyCashCollectionform.setAccountMap(accountMap);

		return accNameWithCodeToList;
	}

	public CashCollectionTO getAccnameAndCode(
			ModifyCashCollectionForm modifyCashCollectionForm) throws Exception {

		CashCollectionTO collectionTo = new CashCollectionTO();
		if (modifyCashCollectionForm != null) {
			collectionTo.setAccId(modifyCashCollectionForm.getAccountId());
		}
		return collectionTo;
	}
	
	public List<PcReceipts> prepareBoObjectsToSaveAndUpdate(ModifyCashCollectionForm modifyCashcollectionForm) throws Exception {
		String studentName=null;
		List<PcReceipts> updatedList = new ArrayList<PcReceipts>();
		log.info("inside prepareBoObjectsToSaveAndUpdate");
		String hour = modifyCashcollectionForm.getHours();
		String minutes = modifyCashcollectionForm.getMinutes().trim();
		/*int last_ofM = modifyCashcollectionForm.getMinutes().lastIndexOf("M");
		String am_pm = modifyCashcollectionForm.getMinutes().substring(last_ofM - 1, last_ofM + 1);*/
		String date = modifyCashcollectionForm.getPaidDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(CommonUtil.ConvertStringToSQLDate(date));
		cal.set(Calendar.HOUR_OF_DAY,Integer.valueOf(hour));
		cal.set(Calendar.MINUTE, Integer.valueOf(minutes));
		cal.set(Calendar.SECOND, 0);
		/*if(am_pm.equalsIgnoreCase("AM"))
			cal.set(Calendar.AM_PM,0);
		else if(am_pm.equalsIgnoreCase("PM"))
			cal.set(Calendar.AM_PM,1);*/
		java.util.Date paidDate = cal.getTime();
		
		ICashCollectionTransaction cashCollectionTransaction = CashCollectionTransactionImpl.getInstance();
		//PcFinancialYear pcfinancialYear = cashCollectionTransaction.getFinancialyearId(modifyCashcollectionForm.getAcademicYear());
		
		Users user = cashCollectionTransaction.getUserFromUserId(modifyCashcollectionForm.getUserId());
		Student studentBo = null;
		if(modifyCashcollectionForm.getAppNo()==null || modifyCashcollectionForm.getAppRegRollno()==null||modifyCashcollectionForm.getAppNo().equalsIgnoreCase("not entered") ||modifyCashcollectionForm.getAppRegRollno().equalsIgnoreCase("not entered") )
		{
			studentName = modifyCashcollectionForm.getNameOfStudent();
		}
		else	
		{
		String queryString = commonSearch(modifyCashcollectionForm);
		if(queryString != null){
			studentBo = cashCollectionTransaction.getStudentBo(queryString);
		}
		}
		List<AccountHeadTO> newPCReceiptList = modifyCashcollectionForm.getAccountHeadListToDisplay();
		
			if(newPCReceiptList!=null){
				Iterator<AccountHeadTO> iterator = newPCReceiptList.iterator();
				AccountHeadTO accountHeadTO;
				PcAccountHead pcAccountHead = null;
				PcReceipts pcReceipts = null;
				while (iterator.hasNext()) {
					 accountHeadTO =  iterator.next();
					 pcReceipts = new PcReceipts();
					 if(accountHeadTO.getPcReceiptId()!=null && !accountHeadTO.getPcReceiptId().isEmpty()){
						 pcReceipts.setId(Integer.valueOf(accountHeadTO.getPcReceiptId()));
					 }
					 if(modifyCashcollectionForm.getRecNoResult()!=null){
						 pcReceipts.setNumber(Integer.parseInt(modifyCashcollectionForm.getRecNoResult()));
					 }
					/* if(modifyCashcollectionForm.getPaidDate()!=null){
						 pcReceipts.setPaidDate(CommonUtil.ConvertStringToSQLDate(modifyCashcollectionForm.getPaidDate()));
					 }*/
					 pcReceipts.setPaidDate(paidDate);
					 pcReceipts.setDetails(accountHeadTO.getDetails());
					 if(modifyCashcollectionForm.getAppNo()!=null){
					 	pcReceipts.setRefType(modifyCashcollectionForm.getAppNo());
					 }
					 if(modifyCashcollectionForm.getAppRegRollno()!=null){
					 	pcReceipts.setRefNo(modifyCashcollectionForm.getAppRegRollno());
					 }
					 if(modifyCashcollectionForm.getNameOfStudent()!=null){
					 	pcReceipts.setName(modifyCashcollectionForm.getNameOfStudent().trim());
					 }
					 if(modifyCashcollectionForm.getAcademicYear()!=null){
					 pcReceipts.setAcademicYear(Integer.parseInt(modifyCashcollectionForm.getAcademicYear()));}
					
					 pcAccountHead = new PcAccountHead();
					 if(accountHeadTO.getAccid()!=0){
						 pcAccountHead.setId(accountHeadTO.getAccid());
					 }
					 if(accountHeadTO.getAmount()!=null){
							pcReceipts.setAmount(new BigDecimal(accountHeadTO.getAmount()));
					}
					 pcReceipts.setPcAccountHead(pcAccountHead);
					 
//					 if(pcfinancialYear!=null){
//						pcReceipts.setPcFinancialYear(pcfinancialYear);
//					 }
					 if(modifyCashcollectionForm.getFinancialYearId()>0){
						 PcFinancialYear financialYear=new PcFinancialYear();
						 financialYear.setId(modifyCashcollectionForm.getFinancialYearId());
						 pcReceipts.setPcFinancialYear(financialYear);
					 }
						if(studentBo!=null){
							pcReceipts.setStudent(studentBo);}
						if(user!=null){
							pcReceipts.setUsers(user);
						}
						pcReceipts.setCreatedDate(new Date());
						pcReceipts.setLastModifiedDate(new Date());
						pcReceipts.setCreatedBy(modifyCashcollectionForm.getUserId());
						pcReceipts.setModifiedBy(modifyCashcollectionForm.getUserId());
						
						pcReceipts.setIsCancelled(false);
						pcReceipts.setCancelComments("not cancelled");
						pcReceipts.setIsActive(true);
					 
					 updatedList.add(pcReceipts);
				}
			}
			log.info("leaving prepareBoObjectsToSaveAndUpdate");
		return updatedList;
	
	}
	
	public  String commonSearch(ModifyCashCollectionForm modifyCashCollectionForm) {
		log.info("entered commonSearch method ");
		String query=null;
		String number=modifyCashCollectionForm.getAppNo();
		
		if(number.equals("appNo"))
		{
			query="from Student st where st.admAppln.appliedYear ="+modifyCashCollectionForm.getAcademicYear()+" and st.admAppln.applnNo ="+modifyCashCollectionForm.getAppRegRollno();
			return query;
		}
		else if(number.equals("regNo"))
		{	if(modifyCashCollectionForm.getAppRegRollno() != null && !modifyCashCollectionForm.getAppRegRollno().trim().isEmpty()){
				query="from Student st where st.registerNo='"+modifyCashCollectionForm.getAppRegRollno().trim()+"'";	
				return query;
			}
		}
		else if(number.equals("rollNo"))
		{
			query="from Student st where st.rollNo='"+modifyCashCollectionForm.getAppRegRollno().trim()+"'";	
			return query;
		}
		
		return query;
	}
	
	public AccountHeadTO getAccountheadtoAdd(String id) throws Exception
	{
		IModifyCashCollectionTransaction modifyTransactionImp = ModifyCashCollectionTransactionImpl.getInstance();
		PcAccountHead accountHead = modifyTransactionImp.getAccountheadtoAdd(Integer.valueOf(id));
		AccountHeadTO accountHeadTo = new AccountHeadTO();
		accountHeadTo.setAccid(accountHead.getId());
		if(accountHead.getAccCode()!=null && !accountHead.getAccCode().isEmpty()){
		accountHeadTo.setAccCode(accountHead.getAccCode());
		}
		if(!accountHead.getAccCode().isEmpty() && accountHead.getAccName()!=null)
		{
			accountHeadTo.setAccName(accountHead.getAccName());
		}
		if(accountHead.getAmount()!=null && accountHead.getAmount().intValue()!=' '){
		accountHeadTo.setAmount(accountHead.getAmount().toString());
	}
		
		return accountHeadTo;
	}
	public void setReceiptNumberDatatoForm(List<PcReceipts> pcReceiptList,ModifyCashCollectionForm cashCollectionForm) throws Exception
	{
		
		log.info("Start of setReceiptNumberDatatoForm of CashCollectionHelper");
		cashCollectionForm.setAccountList(null);
		List<CashCollectionTO> cashCollectionToList = new ArrayList<CashCollectionTO>();
		double total=0.0;
		String time="";
		int size=0;
		Iterator<PcReceipts> it = pcReceiptList.iterator();
			while(it.hasNext())	
			{
				PcReceipts pcReceipts=it.next();
				CashCollectionTO collectionTo = new CashCollectionTO();
				collectionTo.setId(pcReceipts.getId());
				cashCollectionForm.setId(Integer.toString(pcReceipts.getId()));
				
				collectionTo.setNumber(String.valueOf(pcReceipts.getNumber()));
				cashCollectionForm.setRecNoResult(String.valueOf(pcReceipts.getNumber()));
				
				collectionTo.setName(pcReceipts.getName());
				cashCollectionForm.setNameOfStudent(pcReceipts.getName());
				collectionTo.setPaidDate(String.valueOf(pcReceipts.getPaidDate()));
				if(pcReceipts.getPaidDate()!=null){
					time = String.valueOf(pcReceipts.getPaidDate());
				}
				collectionTo.setRefNo(pcReceipts.getRefNo());
				cashCollectionForm.setRefNo(pcReceipts.getRefNo());
				
				collectionTo.setReftype(pcReceipts.getRefType());
				cashCollectionForm.setReftype(pcReceipts.getRefType());
				
				if(pcReceipts.getPcAccountHead()!=null && pcReceipts.getPcAccountHead().getAccName()!=null){
				collectionTo.setAccName(pcReceipts.getPcAccountHead().getAccName());
				cashCollectionForm.setAccName(pcReceipts.getPcAccountHead().getAccName());
				}
				if(pcReceipts.getPcAccountHead()!=null && pcReceipts.getPcAccountHead().getAccCode()!=null){
					collectionTo.setAccountCode(pcReceipts.getPcAccountHead().getAccCode());
					cashCollectionForm.setAccCode(pcReceipts.getPcAccountHead().getAccName());
				}
				if(pcReceipts.getAmount()!=null){
					collectionTo.setAmount(pcReceipts.getAmount().toString());
					total = total + Double.valueOf(pcReceipts.getAmount().doubleValue());
				}
				
				if(pcReceipts.getUsers()!=null && pcReceipts.getUsers().getUserName()!=null){
					collectionTo.setUserName(pcReceipts.getUsers().getUserName());
					cashCollectionForm.setUserName(pcReceipts.getUsers().getUserName());
					}
				if(pcReceipts.getPcAccountHead().getPcBankAccNumber()!=null){
					if(size==0 && pcReceipts.getPcAccountHead().getPcBankAccNumber().getLogo()!=null){
						cashCollectionForm.setLogo(pcReceipts.getPcAccountHead().getPcBankAccNumber().getLogo());
						size=1;
					}
				}
				collectionTo.setDetails(pcReceipts.getDetails());
				cashCollectionToList.add(collectionTo);
			}
			if(!time.isEmpty()){
			String dateString = time.substring(0, 10);
			String inputDateFormat = "yyyy-mm-dd";
			String outPutdateFormat = "dd/mm/yyyy";
			cashCollectionForm.setPaidDate(CommonUtil.ConvertStringToDateFormat(dateString, inputDateFormat, outPutdateFormat));
			String hour = time.substring(11, 13);
			String minute = time.substring(14, 16);
			cashCollectionForm.setTime(hour+":"+minute);
			}
		cashCollectionForm.setTotal(String.valueOf(total)+"0");
		cashCollectionForm.setRupeesInWords(CommonUtil.numberToWord(Double.valueOf(total).intValue()));
		cashCollectionForm.setAccountList(cashCollectionToList);
		log.info("Existing setReceiptNumberDatatoForm of CashCollectionHelper");		
	}	
}
