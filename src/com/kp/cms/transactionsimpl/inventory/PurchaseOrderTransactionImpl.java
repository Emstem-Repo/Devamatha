package com.kp.cms.transactionsimpl.inventory;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.InvCounter;
import com.kp.cms.bo.admin.InvItem;
import com.kp.cms.bo.admin.InvPurchaseOrder;
import com.kp.cms.bo.admin.InvPurchaseOrderItem;
import com.kp.cms.bo.admin.InvQuotation;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.inventory.PurchaseOrderForm;
import com.kp.cms.transactions.inventory.IPurchaseOrderTransaction;
import com.kp.cms.utilities.CurrentAcademicYear;
import com.kp.cms.utilities.HibernateUtil;

/**
 * Class for purchase order DB transactions
 *
 */
public class PurchaseOrderTransactionImpl implements IPurchaseOrderTransaction {
	
	private static final Log log = LogFactory.getLog(PurchaseOrderTransactionImpl.class);
	
	private static volatile PurchaseOrderTransactionImpl instance=null;
	
	public static IPurchaseOrderTransaction getInstance() {
		// TODO Auto-generated method stub
		if(instance==null)
			instance=new PurchaseOrderTransactionImpl();
		return instance;
	}
	
	
	
	
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.inventory.IPurchaseOrderTransaction#getLatestPurchaseOrderNo(java.lang.String)
	 */
	/*@Override
	public String getLatestPurchaseOrderNo(String purchaseOrderCounter , PurchaseOrderForm orderForm)
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
			 query.setString("counter", purchaseOrderCounter);

			 query.setReadOnly(true);
			 InvCounter cntr=(InvCounter)query.uniqueResult(); 
			 if(cntr!=null){
				 prefix=cntr.getPrefix();
				 startno=cntr.getStartNo();
			 }
			 String qry2="";
			 if(prefix!=null)
			 	qry2="select max(p.orderNo) from InvPurchaseOrder p where p.isActive=1 and p.prefix LIKE '"+prefix+"%'";
			 else
				 qry2="select max(p.orderNo) from InvPurchaseOrder p where p.isActive=1";
			 Query query2 = session.createQuery(qry2);
			 query2.setReadOnly(true);
			 
		
			 int maxOrder=0;
			 Integer on=(Integer)query2.uniqueResult();
			 String maxorderNo=null;
			 if(on!=null){
				 maxorderNo=String.valueOf(on);
			 }

			 if(prefix!=null && maxorderNo!=null){
//				 String tempMax=maxorderNo.substring((maxorderNo.substring(maxorderNo.lastIndexOf(prefix), prefix.length()).length()),maxorderNo.length());
//				 if(tempMax!=null && StringUtils.isNumeric(tempMax))
//				 {
//					 maxOrder=Integer.parseInt(tempMax);
//				 }
				 if(StringUtils.isNumeric(maxorderNo)){
					 maxOrder=Integer.parseInt(maxorderNo);
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
			 
			 
			 orderForm.setPrefix(prefix);
			 
			 session.flush();
			 //session.close();
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
			 session = HibernateUtil.getSession();

			 Query query = session.createQuery("from InvItem c where c.isActive=1");
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
	public boolean placeFinalPurchaseOrder(InvPurchaseOrder finalOrder,PurchaseOrderForm orderForm) throws Exception {

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
				 Query query = session.createQuery("select a from InvCounter a where a.type= :counter and a.year=:currentAcademicYear and a.isActive=1 and a.invCompany.id =:invCompanyId");
				 query.setString("counter", CMSConstants.PURCHASE_ORDER_COUNTER);
				 query.setInteger("currentAcademicYear",year);
				 query.setInteger("invCompanyId", Integer.parseInt(orderForm.getCompanyId()));
		//		 query.setLockMode("a", LockMode.UPGRADE);
				 InvCounter counter =(InvCounter) query.uniqueResult();
				// counter.setCurrentNo(finalOrder.getOrderNo()+1);
				 if(counter!=null){
					 session.save(finalOrder);
					 prefix=counter.getPrefix();
					 startno=counter.getStartNo();
					 if(counter.getCurrentNo()!=null){
						 currentNo=counter.getCurrentNo();
					 }else{
						 currentNo=startno;
					 }
					 boolean isExist=false;
						
						do{
							generatedOrderNo=prefix+currentNo;
							List<InvQuotation> bos=session.createQuery("from InvPurchaseOrder a where a.orderNo='"+generatedOrderNo+"' and a.year="+year).list();
							if(bos!=null && !bos.isEmpty()){
								isExist=true;
								currentNo=currentNo+1;
							}else{
								isExist=false;
							}
						}while(isExist);
						generatedOrderNo=prefix+currentNo;
						finalOrder.setOrderNo(generatedOrderNo);
						orderForm.setPurchaseOrderNo(generatedOrderNo);
					    session.update(finalOrder);
					    counter.setCurrentNo(currentNo+1);
						session.update(counter);
						result=true;
				}
			 } else if(orderForm.getMode()!=null && orderForm.getMode().equalsIgnoreCase("Edit")){
				    session.update(finalOrder);
				    result=true;
			 }
			 for (InvPurchaseOrderItem invPurchaseOrderItem : finalOrder.getInvPurchaseOrderItems()) {
				 if(invPurchaseOrderItem.getInvItem()!=null){
					 InvItem invItem = (InvItem) session.get(InvItem.class, invPurchaseOrderItem.getInvItem().getId());
					 invItem.setPurchaseCost(invPurchaseOrderItem.getUnitCost());
					 session.update(invItem);
				 }
			}
			    txn.commit();
				session.flush();
				session.close();
				
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
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.inventory.IPurchaseOrderTransaction#getItemsList()
	 */
	@Override
	public List<InvItem> getItemsList(int vendorId) throws Exception {
		Session session = null;

		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("select i " +
			 		"from InvVendorItemCategory cat " +
			 		"inner join cat.invVendor inv " +
			 		"inner join cat.invItemCategory item " +
			 		"inner join item.invItems i " +
			 		"where i.isActive=1 and cat.isActive=1 and inv.id="+vendorId);
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
	@Override
	public String getLatestPurchaseOrderNo(String purchaseOrderCounter , PurchaseOrderForm orderForm)
			throws Exception {
		Session session = null;
		String prefix=null;
		int startno=0;
		int currentNo=0;
		String generatedOrderNo="";
		try {
			 session = HibernateUtil.getSession();
			 int year=CurrentAcademicYear.getInstance().getAcademicyear();
			 Query query = session.createQuery("from InvCounter a where a.type= :counter and a.year=:currentAcademicYear and a.isActive=1");
			 query.setString("counter", purchaseOrderCounter);
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
		 } catch (Exception e) {
			 log.error("Error during getLatestPurchaseOrderNo...",e);
			 session.flush();
			 session.close();
			throw new ApplicationException(e);
		 }
		 return generatedOrderNo;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.inventory.IPurchaseOrderTransaction#getInvQuotation(java.lang.String)
	 */
	@Override
	public InvQuotation getInvQuotation(String quotationNo) throws Exception {

		Session session = null;
		InvQuotation invQuotation;
		try {
			 session = HibernateUtil.getSession();
			 int year=CurrentAcademicYear.getInstance().getAcademicyear();
			 Query query = session.createQuery("from InvQuotation q where q.isActive=1 and q.quoteNo=:qNo and q.year=:year");
			 query.setString("qNo",quotationNo);
			 query.setInteger("year", year);
			 invQuotation=(InvQuotation)query.uniqueResult(); 
		 } catch (Exception e) {
			 log.error("Error during getLatestPurchaseOrderNo...",e);
			 session.close();
			throw new ApplicationException(e);
		 }
		 return invQuotation;
	}


	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.inventory.IPurchaseOrderTransaction#getPOForEdit(java.lang.String)
	 */
	@Override
	public InvPurchaseOrder getPOForEdit(String purchaseOrderNo) throws Exception {
		Session session = null;
		InvPurchaseOrder bo=null;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from InvPurchaseOrder a where a.orderNo='"+purchaseOrderNo+"'");
			 bo=(InvPurchaseOrder)query.uniqueResult(); 
		 } catch (Exception e) {
			 log.error("Error during getQuotationForEdit...",e);
			 session.close();
			throw new ApplicationException(e);
		 }
		 return bo;
	}
}
