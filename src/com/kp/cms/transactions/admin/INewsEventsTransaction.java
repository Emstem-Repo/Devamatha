package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.NewsEvents;
import com.kp.cms.forms.admin.NewsEventsForm;

public interface INewsEventsTransaction {
	
	public List<NewsEvents> getNewsEventsDetails(String required) throws Exception;
	
	public List<NewsEvents> getNewsEventsDetails() throws Exception;
	
	public boolean saveNewsEvents(NewsEvents newsEvents)throws Exception;
	
	public boolean checkDuplicateNewsEvents(String message) throws Exception;

	public NewsEvents getDetailsOnId(int id)throws Exception ;

	public boolean updateNewsEvents(NewsEvents newsEvents)throws Exception;

	public boolean deleteNewsEvents(int id) throws Exception;

}