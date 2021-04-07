package com.kp.cms.transactionsimpl.auditorium;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.auditorium.BlockDetails;
import com.kp.cms.bo.auditorium.VenueDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.auditorium.VenueDetailsForm;
import com.kp.cms.transactions.auditorium.IVenueDetailsTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class VenueDetailsTxnImpl implements IVenueDetailsTransaction{
	 public static volatile VenueDetailsTxnImpl venueDetailsTxnImpl = null;
	    private static Log log = LogFactory.getLog(VenueDetailsTxnImpl.class);
	    public static VenueDetailsTxnImpl getInstance() {
			if (venueDetailsTxnImpl == null) {
				venueDetailsTxnImpl = new VenueDetailsTxnImpl();
				return venueDetailsTxnImpl;
			}
			return venueDetailsTxnImpl;
		}
	@Override
	public Map<Integer, String> getBlockDetails() throws Exception {
		Map<Integer,String> blockMap = new HashMap<Integer,String>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			String quer= "from BlockDetails block where block.isActive=1";
			Query query = session.createQuery(quer);
			List<BlockDetails> blockList=query.list();
			Iterator<BlockDetails> itr=blockList.iterator();
			while(itr.hasNext()){
				BlockDetails block = (BlockDetails)itr.next();
				blockMap.put(block.getId(), block.getBlockName());
			}
		}catch(Exception exception){
			throw new ApplicationException();
		}
		return blockMap;
	}
	@Override
	public boolean duplicateCheck(String name, HttpSession hSession,
			ActionErrors errors, VenueDetailsForm venueDetailsForm) {
		Session session=null;
		boolean flag=false;
		try{
			session=HibernateUtil.getSession();
			String quer="from VenueDetails venue where venue.venueName=:name and venue.block.id="+venueDetailsForm.getBlockId()+" or venue.roomNo='"+venueDetailsForm.getRoomNo()+"' and venue.emailId='"+venueDetailsForm.getEmailId()+"'";
			Query query=session.createQuery(quer);
			query.setString("name", name);
			//query.setString("scales", payScaleForm.getScale());
			VenueDetails venue=(VenueDetails)query.uniqueResult();
			if(venue!=null && !venue.toString().isEmpty()){
				if(venueDetailsForm.getId()!=0){
			      if(venue.getId()==venueDetailsForm.getId()){
				    flag=false;
			      }else if(venue.getIsActive()){
				     flag=true;
			         errors.add("error", new ActionError("knowledgepro.auditorium.venue.exists"));
			       }
			      else{
					   flag=true;
					   venueDetailsForm.setId(venue.getId());
					   throw new ReActivateException(venue.getId());
				   }
				}else if(venue.getIsActive()){
					flag=true;
			        errors.add("error", new ActionError("knowledgepro.auditorium.venue.exists"));
				}
				else{
					  flag=true;
					  venueDetailsForm.setId(venue.getId());
					  throw new ReActivateException(venue.getId());
				 } 
			}else
				flag=false;
				
		}catch(Exception e){
			log.debug("Reactivate Exception", e);
			if(e instanceof ReActivateException){
				errors.add("error", new ActionError(CMSConstants.VENUE_DETAILS_REACTIVATE));
				//saveErrors(request, errors);
				hSession.setAttribute("ReactivateId", venueDetailsForm.getId());
			}
		}
		return flag;
	}
	@Override
	public boolean addVenueDetails(VenueDetails venue,String mode) throws Exception {
		Session session=null;
		Transaction transaction=null;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(mode.equalsIgnoreCase("Add"))
			    session.save(venue);
			else
				session.merge(venue);
			transaction.commit();
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}
		finally{
			session.flush();
		}
		return true;
	}
	@Override
	public VenueDetails getVenueDetailsById(int id) {
		Session session=null;
		VenueDetails venue=null;
		try{
			session=HibernateUtil.getSession();
			String str="from VenueDetails venue where venue.id="+id+" and venue.isActive=1";
			Query query=session.createQuery(str);
			venue=(VenueDetails)query.uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting PayScale by id..." , e);
			session.flush();
			session.close();
			
		}
		return venue;
	}
	@Override
	public boolean deleteVenueDetails(int id) throws Exception {
		Session session=null;
        Transaction transaction=null;
        boolean flag = false;
        try{
      	    session=InitSessionFactory.getInstance().openSession();
      	    String str="from VenueDetails venue where venue.id="+id+"and venue.isActive=1";
      	    VenueDetails venue=(VenueDetails)session.createQuery(str).uniqueResult();
      	    transaction=session.beginTransaction();
      	    venue.setIsActive(false);
      	    session.update(venue);
      	    transaction.commit();
      	    flag = true;
      	    session.close();
          }catch(Exception e){
        	  flag = false;
      	    if (transaction != null)
      		   transaction.rollback();
      	    
      	log.debug("Error during deleting Venue Details...", e);
          }
      return flag;
	}
	@Override
	public boolean reactivateVenueDetails(VenueDetailsForm venueDetailsForm)
			throws Exception {
		log.info("Entering into reactivateVenueDetails");
		//SessionFactory sessionFactory = InitSessionFactory.getInstance();		
		Session session=null;
		Transaction transaction=null;
		try{
		   session=HibernateUtil.getSession();
		   transaction=session.beginTransaction();
		   String quer="from VenueDetails venue where venue.id="+venueDetailsForm.getId();
		   Query query=session.createQuery(quer);
		   VenueDetails venue=(VenueDetails)query.uniqueResult();
		   venue.setIsActive(true);
		   venue.setModifiedBy(venueDetailsForm.getUserId());
		   venue.setLastModifiedDate(new Date());
		   session.update(venue);
		   transaction.commit();
		}catch(Exception e){
			log.debug("Exception" + e.getMessage());
			return false;
		}finally{
			if(session!=null)
				session.close();
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<VenueDetails> getVenueDetails() throws Exception {
		Session session = null;
		List<VenueDetails> venueDetails=null;
		try{
			session = HibernateUtil.getSession();
			String quer= "from VenueDetails venue where venue.isActive = 1";
			Query query = session.createQuery(quer);
			venueDetails = query.list();
			
		}catch(Exception exception){
			log.debug("Exception" + exception.getMessage());
			throw new ApplicationException();
		}
		return venueDetails;
	}

}                                                                                            
