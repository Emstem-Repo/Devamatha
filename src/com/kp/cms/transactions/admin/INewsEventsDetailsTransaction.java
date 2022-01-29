package com.kp.cms.transactions.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Currency;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.MobNewsEventsCategory;
import com.kp.cms.bo.admin.NewsEventsDetails;
import com.kp.cms.bo.admin.NewsEventsPhoto;
import com.kp.cms.forms.admin.NewsEventsEntryForm;
import com.kp.cms.forms.employee.EmpResPubDetailsForm;

public interface INewsEventsDetailsTransaction {
	
	Map<String, String> getDepartmentMap()throws Exception;

	Map<String, String> getCourseMap()throws Exception;

	Map<String, String> getStreamMap()throws Exception;
	
	Map<String, String> getSplCenterMap()throws Exception;

	public List<NewsEventsDetails> getMobNewsEventsDetails() throws Exception;

	public List<MobNewsEventsCategory> getCategoryList()throws Exception;

	NewsEventsDetails isMobNewsEventDetailsExist(String eventTitle,	int titleId)throws Exception;
	
	public boolean addMobNewsEventDetails(NewsEventsDetails mobNewsEventsDetails) throws Exception;
	
	public NewsEventsDetails editMobNewsEventDetails(int id) throws Exception;
	
	public boolean updateMobNewsEventDetails(NewsEventsDetails mobNewsEventsDetails) throws Exception;
	
	public boolean reActivateMobNewsEventDetails(String eventTitle, String userId) throws Exception;

	public boolean deleteNewsEventsDetails(int mobNewaEventsDatailsId,String userId) throws Exception;
	
	public Employee getEmployeeIdFromUserId (NewsEventsEntryForm newsForm)throws Exception;


	public boolean reActivateMobNewsEventsDetails(int dupId, String userId)throws Exception;
	
	public NewsEventsDetails GetNewsEventsDetails(NewsEventsEntryForm newsEventsForm)throws Exception;
	
	public StringBuffer getSerchedNewsQuery(int categoryId, String participants, Date dateFrom, 
			Date dateTo, String eventTitle, String academicYear, String organizedById) throws Exception;
	
	public List<NewsEventsDetails> getSerchedNews(StringBuffer query) throws Exception;
	
	public NewsEventsPhoto getSerchedPhoto(NewsEventsEntryForm newsEventsForm)throws Exception;
	
	public StringBuffer getSerchedNewsQueryAdmin(NewsEventsEntryForm newsEventsForm)throws Exception;
	
	//public StringBuffer getSerchedNewsQueryPost(NewsEventsEntryForm newsEventsForm)throws Exception;
			
	//public StringBuffer getSerchedNewsPostEventDept(NewsEventsEntryForm newsEventsForm)throws Exception;
	
	public boolean updateNewsEventDetailsAdmin(NewsEventsEntryForm newsEventsForm)throws Exception;

	boolean deletePhoto(int photoId, String userId) throws Exception;

	public List<NewsEventsDetails> getNewsEventsDetailsForEdit() throws Exception;
	
	boolean deleteResourse(int resourseId, String userId) throws Exception;
	boolean deleteContact(int contactId, String userId) throws Exception;
	boolean deleteParticipants(int partId, String userId) throws Exception;
	

	

	
	
}
