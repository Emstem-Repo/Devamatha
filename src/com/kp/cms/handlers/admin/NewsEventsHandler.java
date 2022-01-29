package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.NewsEvents;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.NewsEventsForm;
import com.kp.cms.helpers.admin.DepartmentEntryHelper;
import com.kp.cms.helpers.admin.NewsEventsHelper;
import com.kp.cms.to.admin.NewsEventsTO;
import com.kp.cms.transactions.admin.IDepartmentEntryTransaction;
import com.kp.cms.transactions.admin.INewsEventsTransaction;
import com.kp.cms.transactionsimpl.admin.DepartmentEntryTransactionImpl;
import com.kp.cms.transactionsimpl.admin.NewsEventsTransactionImpl;

public class NewsEventsHandler {
	
	private static final Logger log = Logger.getLogger(NewsEventsHandler.class);	
	public static volatile NewsEventsHandler newsEventsHandler = null;

	/**
	 * This method is used to create a single instance of NewsEventsHandler.
	 * @return unique instance of NewsEventsHandler class.
	 */
	
	public static NewsEventsHandler getInstance() {
		if (newsEventsHandler == null) {
			newsEventsHandler = new NewsEventsHandler();
			return newsEventsHandler;
		}
		return newsEventsHandler;
	}
	INewsEventsTransaction eventsTransaction = NewsEventsTransactionImpl.getInstance();
	
	/**
	 * This method is used to display all the news & events from database to UI.
	 * @param required 
	 * @return List of type NewsEventsTO.
	 * @throws Exception
	 */
	public List<NewsEventsTO> getNewsEvents(String required) throws Exception {
		List<NewsEventsTO> list = new ArrayList<NewsEventsTO>();
		List<NewsEvents> newsList = eventsTransaction.getNewsEventsDetails(required);
		NewsEventsTO eventsTO;
		if(newsList != null && !newsList.isEmpty()){
			Iterator<NewsEvents> iterator = newsList.iterator();
			while (iterator.hasNext()) {
				NewsEvents newsEvents = (NewsEvents) iterator.next();
				eventsTO = new NewsEventsTO();
				eventsTO.setId(newsEvents.getId());
				eventsTO.setName(newsEvents.getDescription());
				eventsTO.setRequired(newsEvents.getRequired());
				list.add(eventsTO);
			}
		}
		log.info("end of getNewsEvents method in NewsEventsHandler class.");
		return list;
	}
	
	/**
	 * This method will add news & events to database.
	 * @param newsEventsForm
	 * @return boolean value.
	 * @throws Exception
	 */
	
	public boolean addNewsEvents(NewsEventsForm newsEventsForm) throws Exception {
		NewsEvents newsEvents = new NewsEvents();
		if(newsEventsForm.getDescription() != null && !newsEventsForm.getDescription().isEmpty()){
			newsEvents.setDescription(newsEventsForm.getDescription());
			newsEvents.setCreatedBy(newsEventsForm.getUserId());
			newsEvents.setCreatedDate(new Date());
			newsEvents.setRequired(newsEventsForm.getRequired());
			newsEvents.setModifiedBy(newsEventsForm.getUserId());
			newsEvents.setLastModifiedDate(new Date());
			
		}
		boolean isAdded = eventsTransaction.saveNewsEvents(newsEvents);
		log.info("end of addNewsEvents method in NewsEventsHandler class.");
		return isAdded;
	}
	
	/**
	 * This method is used to check the duplicate news & events in database.
	 * @param message
	 * @return boolean value.
	 * @throws Exception
	 */
	
	public boolean checkDuplicateNewsEvents(String message, NewsEventsForm newsEventsForm) throws Exception {
		boolean isExist = eventsTransaction.checkDuplicateNewsEvents(message);
		log.info("end of checkDuplicateNewsEvents method in NewsEventsHandler class.");
		return isExist;

	}
	
	/**
	 * This method is used to delete the news & events from database.
	 * @param id
	 * @return boolean value.
	 * @throws Exception
	 */
	public boolean deleteNewsEvents(int newsEventsId) throws Exception {
		boolean isDeleted=false;
		INewsEventsTransaction newsEventsTransaction=NewsEventsTransactionImpl.getInstance();
		isDeleted=newsEventsTransaction.deleteNewsEvents(newsEventsId);
		
		log.info("end of deleteNewsEvents method in NewsEventsHandler class.");
		return isDeleted;
	}

	/**
	 * This method is used to display all the news & events from database to UI.
	 * @param required 
	 * @return List of type NewsEventsTO.
	 * @throws Exception
	 */
	public List<NewsEventsTO> getNewsEvents() throws Exception {
		// TODO Auto-generated method stub
		log.info("call of getNewsEvents method in NewsEventsHandler class.");
		List<NewsEventsTO> list = new ArrayList<NewsEventsTO>();
		List<NewsEvents> newsList = eventsTransaction.getNewsEventsDetails();
		NewsEventsTO eventsTO;
		if(newsList != null && !newsList.isEmpty()){
			Iterator<NewsEvents> iterator = newsList.iterator();
			while (iterator.hasNext()) {
				NewsEvents newsEvents = (NewsEvents) iterator.next();
				eventsTO = new NewsEventsTO();
				eventsTO.setId(newsEvents.getId());
				eventsTO.setName(newsEvents.getDescription());
				eventsTO.setRequired(newsEvents.getRequired());
				list.add(eventsTO);
			}
		}
		return list;
	}
	
	public boolean updateNewsEvents(NewsEventsForm byForm)throws Exception
	{
		NewsEventsTO byTO=new NewsEventsTO();
		
		if(byForm!=null){
			byTO.setId(byForm.getNewsEventsId());
			byTO.setName(byForm.getDescription());
			byTO.setRequired(byForm.getRequired());
			byTO.setModifiedBy(byForm.getUserId());
			
		}
			NewsEvents newsEvents=NewsEventsHelper.getInstance().populateTotoBoUpdate(byTO);
			if(eventsTransaction!=null){
				return eventsTransaction.updateNewsEvents(newsEvents);				
			}
		return false;		
	}
	
	public NewsEventsTO getDetailsOnId(int recId) throws Exception{
		NewsEvents newsEvents=eventsTransaction.getDetailsOnId(recId);
		return NewsEventsHelper.getInstance().populateBotoToEdit(newsEvents);
	}
}