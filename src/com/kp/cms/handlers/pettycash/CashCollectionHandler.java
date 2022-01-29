package com.kp.cms.handlers.pettycash;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PcAccountHead;
import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.bo.admin.PcReceipts;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.pettycash.CashCollectionForm;
import com.kp.cms.forms.pettycash.ModifyCashCollectionForm;
import com.kp.cms.helpers.pettycash.CashCollectionHelper;
import com.kp.cms.to.pettycash.AccountHeadTO;
import com.kp.cms.to.pettycash.CashCollectionTO;
import com.kp.cms.to.pettycash.FinancialYearTO;
import com.kp.cms.transactions.pettycash.ICashCollectionTransaction;
import com.kp.cms.transactionsimpl.pettycash.CashCollectionTransactionImpl;

public class CashCollectionHandler {
	
	/**
	 * @Date 03 Nov 2009
	 * This handler class for Cash Collection Management
	 *
	 */
	private static final Log log = LogFactory.getLog(CashCollectionHandler.class);
		
		private static volatile CashCollectionHandler cashCollectionHandler;
		
		
		public static CashCollectionHandler getinstance()
		{
			if(cashCollectionHandler==null)
			{
				cashCollectionHandler = new CashCollectionHandler();
				return cashCollectionHandler;
			}
			return cashCollectionHandler;
			
		}
		
		public Integer getLastReceiptNumber() throws Exception
		{
			ICashCollectionTransaction cashCollectiontransaction = CashCollectionTransactionImpl.getInstance();

			int receiptNumber = cashCollectiontransaction.getLastReceiptNumberAndUpdate();
			return receiptNumber;
		}

			
			public List<CashCollectionTO> getAccountNameWithCodeToList(CashCollectionForm cashCollectionform)throws Exception
			{
				
				log.info("entering into getAccountNameWithCodeToList in CashCollectionHandler class..");
				ICashCollectionTransaction cashCollectionTransaction = CashCollectionTransactionImpl.getInstance();
				
				List<PcAccountHead> pcAccountHeadBoList = cashCollectionTransaction.getAccountNameWithCodeToList(cashCollectionform.getIsPettyCash());

				List<CashCollectionTO> pcAccountHeadTo = CashCollectionHelper.getInstance().convertAccDetailsToTo(pcAccountHeadBoList, cashCollectionform);
				
				log.info("the list after getting years is in handler after coverting to to");
				
				log.info("leaving from getAccountNameWithCodeToList in CashCollectionHandler class..");
				return pcAccountHeadTo;
			}

			public AccountHeadTO getAmount(CashCollectionForm cashcollectionForm)throws Exception
			{
				
				log.info("entering into getAmount in CashCollectionHandler class..");
				CashCollectionTO accNameAndCode=null;
				accNameAndCode = CashCollectionHelper.getInstance().getAccnameAndCode(cashcollectionForm);
				String accId = accNameAndCode.getAccId();
				ICashCollectionTransaction cashCollectionTransaction = CashCollectionTransactionImpl.getInstance();
				List amountList = cashCollectionTransaction.getAmount(accId);
				
				AccountHeadTO accTo=CashCollectionHelper.getInstance().convertAmountsListToTo(amountList);
				
				
				
				log.info("leaving from getFineDetails in CashCollectionHandler class..");
				return accTo;
			}
			
			
			
			
			public String getStudentName(CashCollectionForm cashCollectionForm)throws Exception
			{
				
				log.info("entering into getStudentName in CashCollectionHandler class..");
				ICashCollectionTransaction cashCollectionTransaction = CashCollectionTransactionImpl.getInstance();
				
				String query = CashCollectionHelper.getInstance().commonSearch(cashCollectionForm);
				List<PersonalData> personneldataBo = cashCollectionTransaction.getStudentName(query);
				String studentName = CashCollectionHelper.getInstance().convertStudentnameBoToTo(personneldataBo);
				
				
				
				log.info("leaving from getFineDetails in CashCollectionHandler class..");
				return studentName;
			}
			
			public String getTotalAmount(List<AccountHeadTO> accountList)
			{
				
				Double totalAmount=0.0;
				if(accountList!=null && !accountList.isEmpty())
				{
					Iterator<AccountHeadTO> toIt = accountList.iterator();
					while(toIt.hasNext())
				{
					AccountHeadTO collectionTo = toIt.next();
					if(collectionTo.getAmount()!=null && !collectionTo.getAmount().isEmpty())
					totalAmount = totalAmount+Double.parseDouble(collectionTo.getAmount());
					else
						totalAmount=totalAmount + 0.0;
				}
				}
				
				return totalAmount.toString();
			}
			public String getTotalAmountForcashCollection(List<CashCollectionTO> accountList)
			{
				
				Double totalAmount=0.0;
				if(accountList!=null && !accountList.isEmpty())
				{
					Iterator<CashCollectionTO> toIt = accountList.iterator();
					while(toIt.hasNext())
				{
						CashCollectionTO collectionTo = toIt.next();
					totalAmount = totalAmount+Double.parseDouble(collectionTo.getAmount());
				}
				}
				
				return totalAmount.toString();
			}
			
			
			public List<AccountHeadTO> deleteReceiptNumber(String userId, String accId,List<AccountHeadTO> collectionTOList, int pcReceiptId) throws Exception
			{
			
				ICashCollectionTransaction cashCollectionTransaction = CashCollectionTransactionImpl.getInstance();
				cashCollectionTransaction.deletePcReceipt(pcReceiptId,accId, userId);
				
			boolean privilage = CashCollectionHelper.getInstance().getUserPrivilage(userId);
			if(privilage==true)
			{
				Iterator<AccountHeadTO> collectionIt = collectionTOList.iterator();
				while(collectionIt.hasNext())
				{
					AccountHeadTO collectionTo =collectionIt.next();
					if(Integer.parseInt(accId) == collectionTo.getAccid())
					{
						collectionIt.remove();
						
						break;
					}
				}
				
			}
				return collectionTOList;
			}

			

			public List<CashCollectionTO> deleteReceiptNumberForCashCollection(String userId, String accId,List<CashCollectionTO> collectionTOList) throws Exception
			{
			boolean privilage = CashCollectionHelper.getInstance().getUserPrivilage(userId);
			if(privilage==true)
			{
				Iterator<CashCollectionTO> collectionIt = collectionTOList.iterator();
				while(collectionIt.hasNext())
				{
					CashCollectionTO collectionTo =collectionIt.next();
					if(accId.equals(collectionTo.getId().toString()))
					{
						collectionIt.remove();
						break;
					}
				}
				
			}
				return collectionTOList;
			}
		/*
		 * this method will store all the collection for a student to the database.
		 */
			public Integer saveCashCollection(CashCollectionForm cashCollectionForm)throws Exception
			{
				Integer recNo=0;
				log.info("entering into getStudentName in CashCollectionHandler class..");
				ICashCollectionTransaction cashCollectionTransaction = CashCollectionTransactionImpl.getInstance();
				 
				PcFinancialYear pcfinancialYear = cashCollectionTransaction.getFinancialyearId(null);
				
				Users user = cashCollectionTransaction.getUserFromUserId(cashCollectionForm.getUserId());
				
				List<PcReceipts> cashCollectionReceiptsList=null;
				int receiptNumber = cashCollectionTransaction.getLastReceiptNumberAndUpdate();
				if(receiptNumber!= 0 && receiptNumber > Integer.valueOf(cashCollectionForm.getNumber()))	
				{
					cashCollectionForm.setNumber(String.valueOf(receiptNumber));
				}
				if(cashCollectionForm.getAppNo()==null || cashCollectionForm.getAppRegRollno()==null ||cashCollectionForm.getAppRegRollno().isEmpty())
				{
					String studentName = cashCollectionForm.getName();
					cashCollectionReceiptsList = CashCollectionHelper.getInstance().prepareBoObjectsToStore(cashCollectionForm,pcfinancialYear,studentName,user);	
				}
				else	
				{
				String queryString = CashCollectionHelper.getInstance().commonSearch(cashCollectionForm);
				Student studentBo = cashCollectionTransaction.getStudentBo(queryString);
				cashCollectionReceiptsList = CashCollectionHelper.getInstance().prepareBoObjectsToStore(cashCollectionForm,pcfinancialYear,studentBo,user);
				}
				if(!cashCollectionReceiptsList.isEmpty())
				{
					recNo = cashCollectionTransaction.saveCashCollection(cashCollectionReceiptsList,cashCollectionForm,pcfinancialYear);
				}
			
				
				
				
				log.info("leaving from getFineDetails in CashCollectionHandler class..");
				return recNo;
			}
			//reprint of receipt
			 public List<FinancialYearTO> getFinancialYearList() throws Exception
				{
				 log.info("Start of getFinancialYearList of CashCollectionHandler");
				 ICashCollectionTransaction cashCollectionTransaction = CashCollectionTransactionImpl.getInstance();
					List<PcFinancialYear> finanicalYearListBo = cashCollectionTransaction.getFinancialYearList();
					if(finanicalYearListBo!=null)
					{
						return CashCollectionHelper.getInstance().convertToListToBo(finanicalYearListBo);		
					}
					log.info("Existing getFinancialYearList of CashCollectionHandler");
					return new ArrayList<FinancialYearTO>();	
				}	 
			 
			 
			 
			 public void getReceiptDetails(CashCollectionForm cashCollectionForm,HttpServletRequest request) throws DataNotFoundException,Exception {
					log.debug("Entering the getReceiptDetails");
					ICashCollectionTransaction cashCollectionTransaction = CashCollectionTransactionImpl.getInstance();
					//int finYearIdEntered = Integer.valueOf(cashCollectionForm.getFinancialYearId());
					//int financialYear = cashCollectionTransaction.getFinancialYear();
					List<PcReceipts> pcReceiptsList = null;
					int finacialYearId = Integer.parseInt(cashCollectionForm.getFinYearId());
					pcReceiptsList = cashCollectionTransaction.getReceiptDetailsForEdit(Integer.parseInt(cashCollectionForm.getNumber()),finacialYearId);
					
					if(pcReceiptsList == null || pcReceiptsList.isEmpty()) {
						throw new DataNotFoundException();
					}
					CashCollectionHelper.getInstance().setReceiptNumberDatatoForm(pcReceiptsList,cashCollectionForm);
					log.debug("Leaving the getReceiptDetails");
				}

			public void getPrintWhileSave(
					CashCollectionForm cashCollectionForm,
					HttpServletRequest request) throws Exception{
				ICashCollectionTransaction cashCollectionTransaction = CashCollectionTransactionImpl.getInstance();
				//int finYearIdEntered = Integer.valueOf(cashCollectionForm.getFinancialYearId());
				int finacialYearId = cashCollectionTransaction.getFinancialYear();
				List<PcReceipts> pcReceiptsList = null;
				//int finacialYearId = Integer.parseInt(cashCollectionForm.getFinYearId());
				pcReceiptsList = cashCollectionTransaction.getReceiptDetailsForEdit(Integer.parseInt(cashCollectionForm.getOldRecNo()),finacialYearId);
				
				if(pcReceiptsList == null || pcReceiptsList.isEmpty()) {
					throw new DataNotFoundException();
				}
				CashCollectionHelper.getInstance().setReceiptNumberDatatoForm(pcReceiptsList,cashCollectionForm);
				log.debug("Leaving the getReceiptDetails");
				
			}

			public int getCurrentFinancialYear() throws Exception{
				ICashCollectionTransaction cashCollectionTransaction = CashCollectionTransactionImpl.getInstance();
				return cashCollectionTransaction.getCurrentFinancialYear();
			}
			
			
		
}
