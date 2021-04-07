package com.kp.cms.transactionsimpl.phd;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.phd.DocumentDetailsBO;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.phd.DocumentDetailsForm;
import com.kp.cms.transactions.phd.IDocumentDetails;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class DocumentDetailsTransactionImpl implements IDocumentDetails {

	@Override
	public List<DocumentDetailsBO> getDocumentDetailsList() {

		
		Session session=null;
		List<DocumentDetailsBO> documentDetailsList = null;
		try{
		String query="from DocumentDetailsBO documentbo where documentbo.isActive=1";
		session=HibernateUtil.getSession();
		
		Query query2= session.createQuery(query);
		documentDetailsList=query2.list();
		}
		catch (Exception e) {

			session.flush();
			session.close();
		}
		return documentDetailsList;
	}

	@Override
	public boolean addOrUpdateDocument(DocumentDetailsBO detailsBO,String mode) {
		Transaction transaction =null;
		Session  session=null;
try{
		session=HibernateUtil.getSession();
		
		 transaction=session.beginTransaction();
		transaction.begin();
		if(mode.equalsIgnoreCase("add"))
		{
		session.save(detailsBO);
		}
		else
		{
			session.merge(detailsBO);
		}
		transaction.commit();
	
}
catch(Exception exception){
	if (transaction != null)
		transaction.rollback();
	
}
finally{
	if(session !=null){
	session.flush();
	session.close();
	}
}
		return true;
	}

	@Override
	public boolean duplicateCheck(DocumentDetailsForm detailsForm,
			ActionErrors errors, HttpSession hsession) {
		
		Session session=null;
		boolean flag=false;
		try{
			session=HibernateUtil.getSession();
			String quer="from DocumentDetailsBO documentbo where documentbo.documentName='"+detailsForm.getDocumentName()+"' or documentbo.submissionOrder="+detailsForm.getSubmissionOrder()+" ";
			Query query=session.createQuery(quer);
			//query.setString("documentName", detailsForm.getDocumentName());
			//query.setString("submissionOrder", detailsForm.getSubmissionOrder());
			List<DocumentDetailsBO> documentDetailsBOList= query.list();
			Iterator iterator=documentDetailsBOList.iterator();
			while(iterator.hasNext())
			{
			DocumentDetailsBO documentBO=(DocumentDetailsBO) iterator.next();	
			if(documentBO!=null && !documentBO.toString().isEmpty()){
				if(detailsForm.getId()!=0){
			      if(documentBO.getId()==detailsForm.getId()){
				    flag=false;
			      }else if(documentBO.getIsActive()){
				     flag=true;
				     duplicateCheckErrMsg(detailsForm, documentBO, errors);
			       }
			      else{
					   flag=true;
					   detailsForm.setId(documentBO.getId());
					   throw new ReActivateException(documentBO.getId());
				   }
				}else if(documentBO.getIsActive()){
					flag=true;
					 duplicateCheckErrMsg(detailsForm, documentBO, errors);
				}
				else{
					  flag=true;
					  detailsForm.setId(documentBO.getId());
					  throw new ReActivateException(documentBO.getId());
				 } 
			}else
				flag=false;
			}		
		}catch(Exception e){
			
			if(e instanceof ReActivateException){
				errors.add("error", new ActionError("knowledgepro.phd.document.reactivate"));
				//saveErrors(request, errors);
				hsession.setAttribute("ReactivateId", detailsForm.getId());
			}
		}
		return flag;
		
	}
	
	
	public void duplicateCheckErrMsg(DocumentDetailsForm detailsForm,DocumentDetailsBO documentBO,ActionErrors errors)
	{
		if(detailsForm.getDocumentName().equalsIgnoreCase(documentBO.getDocumentName()) & detailsForm.getSubmissionOrder().equalsIgnoreCase(String.valueOf(documentBO.getSubmissionOrder())))
		{
     errors.add("error", new ActionError("knowledgepro.phd.document.duplicate"));     
	
		}
else if(detailsForm.getDocumentName().equalsIgnoreCase(documentBO.getDocumentName()))
{
	 errors.add("error", new ActionError("knowledgepro.phd.document.duplicate.documentName"));    
}
else if(detailsForm.getSubmissionOrder().equalsIgnoreCase(String.valueOf(documentBO.getSubmissionOrder())))
		{
	 errors.add("error", new ActionError("knowledgepro.phd.document.duplicate.submissionOrder"));  
		}
	}

	@Override
	public boolean reactivateDocument(DocumentDetailsForm detailsForm) {
		Session session=null;
		Transaction transaction=null;
		try{
		   session=HibernateUtil.getSession();
		   transaction=session.beginTransaction();
		   String quer="from DocumentDetailsBO document where document.id="+detailsForm.getId();
		   Query query=session.createQuery(quer);
		   DocumentDetailsBO detailsBO= (DocumentDetailsBO) query.uniqueResult();
		   detailsBO.setIsActive(true);
		   detailsBO.setModifiedBy(detailsForm.getUserId());
		   detailsBO.setLastModifiedDate(new Date());
		   session.update(detailsBO);
		   transaction.commit();
		}catch(Exception e){
			return false;
		}finally{
			if(session!=null)
				session.close();
		}
		return true;
	
	}

	@Override
	public DocumentDetailsBO getDocumentById(int id) {
		Session session=null;
		DocumentDetailsBO documentDetailsBO = null;
		try{
		String query="from DocumentDetailsBO documentBo where documentBo.id="+id;
		session=HibernateUtil.getSession();
		
		Query query2= session.createQuery(query);
		documentDetailsBO= (DocumentDetailsBO) query2.uniqueResult();
		session.close();
		}
		catch (Exception e) {

			session.flush();
			session.close();
		}
		return documentDetailsBO;
	}


	public boolean deleteDocument(int id) {
		Session session=null;
        Transaction transaction=null;
        try{
      	    session=InitSessionFactory.getInstance().openSession();
      	    String str="from DocumentDetailsBO document where document.id="+id;
      	    DocumentDetailsBO detailsBO= (DocumentDetailsBO) session.createQuery(str).uniqueResult();
      	    transaction=session.beginTransaction();
      	    detailsBO.setIsActive(false);
      	    session.update(detailsBO);
      	    transaction.commit();
      	    session.close();
          }catch(Exception e){
      	    if (transaction != null)
      		   transaction.rollback();
          }
          return true;
	}
}
