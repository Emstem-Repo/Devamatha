package com.kp.cms.transactionsimpl.inventory;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.InvCompany;
import com.kp.cms.bo.admin.InvCounter;
import com.kp.cms.bo.admin.InvItem;
import com.kp.cms.bo.admin.InvQuotation;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.inventory.QuotationForm;
import com.kp.cms.transactions.inventory.IQuotationTransaction;
import com.kp.cms.utilities.CurrentAcademicYear;
import com.kp.cms.utilities.HibernateUtil;

/**
 * Class for quotation DB transactions
 *
 */
public class QuotationTransactionImpl implements IQuotationTransaction {
	
	/**
	 * Singleton object of QuotationTransactionImpl
	 */
	private static volatile QuotationTransactionImpl quotationTransactionImpl = null;
	private static final Log log = LogFactory.getLog(QuotationTransactionImpl.class);
	private QuotationTransactionImpl() {
		
	}
	/**
	 * return singleton object of QuotationTransactionImpl.
	 * @return
	 */
	public static QuotationTransactionImpl getInstance() {
		if (quotationTransactionImpl == null) {
			quotationTransactionImpl = new QuotationTransactionImpl();
		}
		return quotationTransactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.inventory.IPurchaseOrderTransaction#getLatestPurchaseOrderNo(java.lang.String)
	 */
	/*@Override
	public String getLatestQuoteNo(String quotationCounter)
			throws Exception {
		Session session = null;
		int generatedNo=0;
		String prefix=null;
		int startno=0;
		String generatedOrderNo=null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();

			 Query query = session.createQuery("from InvCounter a where a.type= :counter and a.isActive=1");
			 query.setString("counter", quotationCounter);

			 query.setReadOnly(true);
			 InvCounter cntr=(InvCounter)query.uniqueResult(); 
			if(cntr!=null){
			 prefix=cntr.getPrefix();
			 startno=cntr.getStartNo();
			}
			 String qry2="";
			 if(prefix!=null)
			 	qry2="select max(p.quoteNo) from InvQuotation p where p.isActive=1 and p.quoteNo LIKE '"+prefix+"%'";
			 else
				 qry2="select max(p.quoteNo) from InvQuotation p where p.isActive=1";
			 Query query2 = session.createQuery(qry2);
			 query2.setReadOnly(true);
			 
		
			 int maxOrder=0;
			String maxorderNo=(String)query2.uniqueResult(); 
			 

			 if(prefix!=null && maxorderNo!=null && maxorderNo.lastIndexOf(prefix)!=-1){
				 String tempMax=maxorderNo.substring((maxorderNo.substring(maxorderNo.lastIndexOf(prefix), prefix.length()).length()),maxorderNo.length());
				 if(tempMax!=null && StringUtils.isNumeric(tempMax))
				 {
					 maxOrder=Integer.parseInt(tempMax);
				 }
			 }
			
			 if(maxOrder==0 || maxOrder<startno)
			 {
				 generatedNo=startno;
			 }
			 else{
				
				 generatedNo=++maxOrder;
			 }
			 if(prefix!=null && generatedNo!=0)
				 generatedOrderNo=prefix+generatedNo;
			 
			 session.flush();
			 session.close();
			 //sessionFactory.close();
			
		 } catch (Exception e) {
			 log.error("Error during getLatestPurchaseOrderNo...",e);
			
			 session.flush();
			 session.close();
			throw new ApplicationException(e);
		 }
		 return generatedOrderNo;
	}*/
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.inventory.IPurchaseOrderTransaction#getItemsList()
	 */
	@Override
	public List<InvItem> getItemsList() throws Exception {
		Session session = null;

		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session  = HibernateUtil.getSession(); 

//			 Query query = session.createQuery("from InvItem c where c.isActive=1");
			 Query query=session.createQuery("from InvItem invItem where invItem.isActive = 1");
			 query.setReadOnly(true);
			 List<InvItem> list = query.list();

			 session.flush();
			 //session.close();
			 //sessionFactory.close();
			 return list;
		 } catch (Exception e) {
			 log.error("Error during getting InvItems...",e);

			 session.flush();
			 session.close();
			throw new ApplicationException(e);
		 }
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.inventory.IPurchaseOrderTransaction#placeFinalPurchaseOrder(com.kp.cms.bo.admin.InvPurchaseOrder)
	 */
	@Override
	public boolean placeFinalQuotation(InvQuotation finalOrder,QuotationForm orderForm) throws Exception {

		boolean result= false;
		Session session = null;
		Transaction txn=null;
		String prefix=null;
		int startno=0;
		int currentNo=0;
		String generatedOrderNo=null;
		try {
			 session = HibernateUtil.getSession();
			 txn= session.beginTransaction();
			 if(orderForm.getMode()!=null && orderForm.getMode().equalsIgnoreCase("Add")){
				 int year=CurrentAcademicYear.getInstance().getAcademicyear();
				 Query query = session.createQuery("from InvCounter a where a.type= :counter and a.year=:currentAcademicYear and a.isActive=1");
				 query.setString("counter", CMSConstants.QUOTATION_COUNTER);
				 query.setInteger("currentAcademicYear",year);
				 InvCounter cntr=(InvCounter)query.uniqueResult(); 
				if(cntr!=null){
					session.save(finalOrder);
					 prefix=cntr.getPrefix();
					 startno=cntr.getStartNo();
					 if(cntr.getCurrentNo()!=null){
						 currentNo=cntr.getCurrentNo();
					 }else{
						 currentNo=startno;
					 }
					 boolean isExist=false;
						
						do{
							generatedOrderNo=prefix+currentNo;
							List<InvQuotation> bos=session.createQuery("from InvQuotation a where a.quoteNo='"+generatedOrderNo+"' and a.year="+year).list();
							if(bos!=null && !bos.isEmpty()){
								isExist=true;
								currentNo=currentNo+1;
							}else{
								isExist=false;
							}
						}while(isExist);
						generatedOrderNo=prefix+currentNo;
						finalOrder.setQuoteNo(generatedOrderNo);
						orderForm.setQuotationNo(generatedOrderNo);
					    session.update(finalOrder);
					    cntr.setCurrentNo(currentNo+1);
					    session.update(cntr);
						txn.commit();
						session.flush();
						session.close();
						result=true; 
				}
			 }
			 else if(orderForm.getMode()!=null && orderForm.getMode().equalsIgnoreCase("Edit")){
				    session.update(finalOrder);
				    txn.commit();
					session.flush();
					session.close();
					result=true; 
			 }
		 }catch (ConstraintViolationException e) {
			 	txn.rollback();
				log.error("Error in placeFinalPurchaseOrder...",e);
				 throw new BusinessException(e);
		}catch (Exception e) {
			 txn.rollback();
			log.error("Error in placeFinalPurchaseOrder...",e);
			 throw new ApplicationException(e);
		 }
		return result;
	
	}
	@Override
	public String getLatestQuoteNo(String quotationCounter,QuotationForm orderForm)
			throws Exception {
		Session session = null;
		String prefix=null;
		int startno=0;
		String generatedOrderNo=null;
		int currentNo=0;
		try {
			 session = HibernateUtil.getSession();
			 int year=CurrentAcademicYear.getInstance().getAcademicyear();
			 Query query = session.createQuery("from InvCounter a where a.type= :counter and a.year=:currentAcademicYear and a.isActive=1");
			 query.setString("counter", quotationCounter);
			 query.setInteger("currentAcademicYear",year);
			 query.setReadOnly(true);
			 InvCounter cntr=(InvCounter)query.uniqueResult(); 
			if(cntr!=null){
				 prefix=cntr.getPrefix();
				 startno=cntr.getStartNo();
				 if(cntr.getCurrentNo()!=null){
					 currentNo=cntr.getCurrentNo();
				 }else{
					 currentNo=startno;
				 }
			}
			 generatedOrderNo=prefix+currentNo;
			 orderForm.setPrefix(prefix);
			 session.flush();
			 session.close();
		 } catch (Exception e) {
			 log.error("Error during getLatestPurchaseOrderNo...",e);
			 session.flush();
			 session.close();
			throw new ApplicationException(e);
		 }
		 return generatedOrderNo;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.inventory.IQuotationTransaction#getQuotationForEdit(java.lang.String)
	 */
	@Override
	public InvQuotation getQuotationForEdit(String quotationNo)	throws Exception {
		Session session = null;
		InvQuotation quotationBo=null;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from InvQuotation a where a.quoteNo='"+quotationNo+"'");
			 quotationBo=(InvQuotation)query.uniqueResult(); 
		 } catch (Exception e) {
			 log.error("Error during getQuotationForEdit...",e);
			 session.close();
			throw new ApplicationException(e);
		 }
		 return quotationBo;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.inventory.IQuotationTransaction#getCompanyList()
	 */
	@Override
	public List<InvCompany> getCompanyList() throws Exception {
		Session session = null;

		try {
			 session  = HibernateUtil.getSession(); 
			 Query query=session.createQuery("from InvCompany i where i.isActive = 1");
			 query.setReadOnly(true);
			 List<InvCompany> list = query.list();
			 return list;
		 } catch (Exception e) {
			 log.error("Error during getting InvCompany...",e);

			 session.flush();
			 session.close();
			throw new ApplicationException(e);
		 }
	}
}
