package com.kp.cms.handlers.admin;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.actions.DownloadAction.StreamInfo;

import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.MobNewsEventsCategory;
import com.kp.cms.bo.admin.NewsEventsDetails;
import com.kp.cms.bo.admin.NewsEventsPhoto;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.NewsEventsEntryForm;
import com.kp.cms.helpers.admin.NewsEventsDetailsHelper;
import com.kp.cms.to.admin.NewsEventsDetailsTO;
import com.kp.cms.to.admin.NewsEventsPhotosTO;
import com.kp.cms.transactions.admin.INewsEventsDetailsTransaction;
import com.kp.cms.transactionsimpl.admin.NewsEventsDetailsTransactionImpl;
import com.kp.cms.utilities.AccountComparator;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.KeyValueTOComparator;
import com.kp.cms.utilities.NewsEventsComparator;
import com.kp.cms.utilities.jms.MailTO;
import com.kp.cms.utilities.print.HtmlPrinter;

/**
 * @author admin
 *
 */
public class NewsEventsEntryHandler {
	

	private static final Logger log=Logger.getLogger(NewsEventsEntryHandler.class);	
	public static volatile NewsEventsEntryHandler newsEventsDetailsHandler = null;
	public static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	public static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	
	public static NewsEventsEntryHandler getInstance() {
		if (newsEventsDetailsHandler == null) {
			newsEventsDetailsHandler = new NewsEventsEntryHandler();
			return newsEventsDetailsHandler;
		}
		return newsEventsDetailsHandler;
	}
	
//News and Events Add method
	public boolean addMobNewsEventsDetails(
			NewsEventsEntryForm newsEventsEntryForm,HttpServletRequest request) throws Exception {
		log.info("call of NewsEventsEntryForm method in MobNewsEventsDetailsHandler class.");
		NewsEventsDetails mobNewsEventsDetails=NewsEventsDetailsHelper.getInstance().setDataToBo(newsEventsEntryForm,request);
		
		INewsEventsDetailsTransaction mobNewsEventsDetailsTransaction=NewsEventsDetailsTransactionImpl.getInstance();
	
		boolean isAdded = mobNewsEventsDetailsTransaction.addMobNewsEventDetails(mobNewsEventsDetails);
		log.info("end of NewsEventsEntryForm method in MobNewsEventsDetailsHandler class.");
		return isAdded;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public List<NewsEventsDetailsTO> getMobNewsEventsDetails(NewsEventsEntryForm newsForm)throws Exception {
		log.info("call of getMobNewsEventsDetails method in MobNewsEventsDetailsHandler class.");
		List<NewsEventsDetailsTO> MobNewsEventsDetailsList = new ArrayList<NewsEventsDetailsTO>();
		INewsEventsDetailsTransaction mobNewsEventsDetailsTransaction = NewsEventsDetailsTransactionImpl.getInstance();
		List<NewsEventsDetails> list = mobNewsEventsDetailsTransaction.getNewsEventsDetailsForEdit();
		if(list!=null){
		Iterator<NewsEventsDetails> itr = list.iterator();
		NewsEventsDetails mobNewsEventsDetails; 
		NewsEventsDetailsTO mobNewsEventsDetailsTO;
		while (itr.hasNext()) {
			mobNewsEventsDetails = (NewsEventsDetails) itr.next();
			mobNewsEventsDetailsTO = new NewsEventsDetailsTO();
			if(mobNewsEventsDetails.getId()>0)
				mobNewsEventsDetailsTO.setId(mobNewsEventsDetails.getId());
			if(mobNewsEventsDetails.getEventTitle()!=null && !mobNewsEventsDetails.getEventTitle().isEmpty()){
				mobNewsEventsDetailsTO.setEventTitle(mobNewsEventsDetails.getEventTitle());
			}
			if(mobNewsEventsDetails.getEventDescription()!=null && !mobNewsEventsDetails.getEventDescription().isEmpty()){
				mobNewsEventsDetailsTO.setEventDescription(mobNewsEventsDetails.getEventDescription());
			}
			if(mobNewsEventsDetails.getDateFrom()!= null ){
				String dateFrom = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(mobNewsEventsDetails.getDateFrom()), NewsEventsEntryHandler.SQL_DATEFORMAT,	NewsEventsEntryHandler.FROM_DATEFORMAT);
				mobNewsEventsDetailsTO.setDateFrom(dateFrom);
			}
			if(mobNewsEventsDetails.getDateTo()!= null ){
				String dateTo = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(mobNewsEventsDetails.getDateTo()), NewsEventsEntryHandler.SQL_DATEFORMAT,	NewsEventsEntryHandler.FROM_DATEFORMAT);
				mobNewsEventsDetailsTO.setDateTo(dateTo);
			}
			if(mobNewsEventsDetails.getCategoryId()!=null){
			if(mobNewsEventsDetails.getCategoryId().getCategory()!=null){
			mobNewsEventsDetailsTO.setCategory(mobNewsEventsDetails.getCategoryId().getCategory());
			}}
			
			if(mobNewsEventsDetails.getOrganizedBy()!=null && ! mobNewsEventsDetails.getOrganizedBy().isEmpty()){
				mobNewsEventsDetailsTO.setOrgBy(mobNewsEventsDetails.getOrganizedBy());
			}
			if(mobNewsEventsDetails.getDeptId()!=null && mobNewsEventsDetails.getDeptId().getId()>0){
				mobNewsEventsDetailsTO.setDepartmentName(mobNewsEventsDetails.getDeptId().getName());
			}
			if(mobNewsEventsDetails.getCourseId()!=null && mobNewsEventsDetails.getCourseId().getId()>0){
				mobNewsEventsDetailsTO.setCourseName(mobNewsEventsDetails.getCourseId().getName());
			}
			if(mobNewsEventsDetails.getSplCentre()!=null && mobNewsEventsDetails.getSplCentre().getId()>0){
				mobNewsEventsDetailsTO.setSplCentreName(mobNewsEventsDetails.getSplCentre().getName());
			}
			if(mobNewsEventsDetails.getStreamId()!=null && mobNewsEventsDetails.getStreamId().getId()>0){
				mobNewsEventsDetailsTO.setStreamName(mobNewsEventsDetails.getStreamId().getName());
			}
			if(mobNewsEventsDetails.getPreIsApproved()!=null && mobNewsEventsDetails.getPreIsApproved()){
				mobNewsEventsDetailsTO.setStatus("Approved");
			}
			else
			{
				mobNewsEventsDetailsTO.setStatus("Pending");
				if(mobNewsEventsDetails.getCreatedBy()!=null && !mobNewsEventsDetails.getCreatedBy().isEmpty())
				{
					if(mobNewsEventsDetails.getCreatedBy().equalsIgnoreCase(newsForm.getUserId())){
						mobNewsEventsDetailsTO.setIsEditable("true");
					}else
					{
						mobNewsEventsDetailsTO.setIsEditable("false");
					}
				}
			}
			MobNewsEventsDetailsList.add(mobNewsEventsDetailsTO);
			Collections.sort(MobNewsEventsDetailsList, new NewsEventsComparator());
		}
		}
		log.info("end of getMobNewsEventsDetails method in MobNewsEventsDetailsHandler class.");
		return MobNewsEventsDetailsList;
	}
	public Map<Integer, String> getCategory() throws Exception {
		INewsEventsDetailsTransaction newsEventsDetailsTxn = NewsEventsDetailsTransactionImpl.getInstance();
		List<MobNewsEventsCategory> newsEventsCategoriesList=newsEventsDetailsTxn.getCategoryList();
		Map<Integer,String> CategoryMap=coverFromBOtoTo(newsEventsCategoriesList);
		return CategoryMap;
	}
	
	private Map<Integer, String> coverFromBOtoTo( List<MobNewsEventsCategory> newsEventsCategoriesList) {
		Map<Integer, String> categoryMap = new HashMap<Integer,String>();
		if(newsEventsCategoriesList!=null && !newsEventsCategoriesList.isEmpty()){
			Iterator<MobNewsEventsCategory> iterator =newsEventsCategoriesList.iterator();
			while (iterator.hasNext()) {
				MobNewsEventsCategory mobNewsEventsCategory = (MobNewsEventsCategory) iterator .next();
				categoryMap.put(mobNewsEventsCategory.getId(), mobNewsEventsCategory.getCategory());
				
			}
		}
		categoryMap = (Map<Integer, String>) CommonUtil.sortMapByValue(categoryMap);	
		return categoryMap;
	}
	public NewsEventsDetails isMobNewsEventDetailsExist(String eventTitle,
			int titleId)throws Exception {
		log.info("call of isMobNewsEventDetailsExist method in MobNewsEventsDetailsHandler class.");
		INewsEventsDetailsTransaction mobNewsEventsDetailsTransaction=NewsEventsDetailsTransactionImpl.getInstance();
		NewsEventsDetails mobNewsEventsDetails=mobNewsEventsDetailsTransaction.isMobNewsEventDetailsExist(eventTitle,titleId);
		log.info("end of isMobNewsEventDetailsExist method in MobNewsEventsDetailsHandler class.");
		return mobNewsEventsDetails;
	}
	//End of isMobNewsEventDetails Method
	
	public NewsEventsEntryForm editMobNewsEventDetails(NewsEventsEntryForm newsEventsEntryForm, int mobNewaEventsDatailsId) throws Exception{
		log.info("end of NewsEventsEntryForm method in MobNewsEventsDetailsHandler class.");
		INewsEventsDetailsTransaction mobNewsEventsDetailsTransaction=NewsEventsDetailsTransactionImpl.getInstance();
		NewsEventsDetails mobNewsEventsDetails=mobNewsEventsDetailsTransaction.editMobNewsEventDetails(mobNewaEventsDatailsId);
	    newsEventsEntryForm=NewsEventsDetailsHelper.getInstance().convertNewsBOtoToEdit(newsEventsEntryForm,mobNewsEventsDetails);
		return newsEventsEntryForm;
	}
	
	public boolean updateMobNewsEventsDetails(NewsEventsEntryForm newsEventsEntryForm,HttpServletRequest request)throws Exception 
	{
		log.info("call of updateMobNewsEventsDetails method in MobNewsEventsDetailsHandler class.");
		
		INewsEventsDetailsTransaction mobNewsEventsDetailsTransaction=NewsEventsDetailsTransactionImpl.getInstance();
		NewsEventsDetails mobNewsEventsDetails=NewsEventsDetailsHelper.getInstance().convertNewsFormToBO(newsEventsEntryForm,request);
		boolean isUpdated = mobNewsEventsDetailsTransaction.updateMobNewsEventDetails(mobNewsEventsDetails);
		log.info("end of updateMobNewsEventDetails method in MobNewsEventsDetailsHandler class.");
		return isUpdated;
	}
	
	
	//Call to delete mobile news and events handler
	public boolean deleteMobNewsEventsDetails(int mobNewaEventsDatailsId,
			String userId)throws Exception {
		log.info("call of deleteMobNewsEventsDetails method in MobNewsEventsDetailsHandler class.");
		INewsEventsDetailsTransaction mobNewsEventsDetailsTransaction=NewsEventsDetailsTransactionImpl.getInstance();
		boolean isDeleted = mobNewsEventsDetailsTransaction.deleteNewsEventsDetails(mobNewaEventsDatailsId, userId);
		log.info("end of deleteMobNewsEventsDetails method in MobNewsEventsDetailsHandler class.");
		return isDeleted;
	}
	public boolean reActivateMobNewsEventsDetails(int dupId, String userId) throws Exception {
		

		log.info("call of reActivateMobNewsEventsDetails method in MobNewsEventsDetailsHandler class.");
		INewsEventsDetailsTransaction mobNewsEventsDetailsTransaction = NewsEventsDetailsTransactionImpl.getInstance();
		boolean isReactivated = mobNewsEventsDetailsTransaction.reActivateMobNewsEventsDetails(dupId, userId);
		log.info("end of reActivateMobNewsEventsDetails method in MobNewsEventsDetailsHandler class.");
		
		return isReactivated;
	}
	
	// End of mobile news And Events Handler 
	
	public static class ByteArrayStreamInfo implements StreamInfo {
		protected String contentType;
		protected byte[] bytes;

		public ByteArrayStreamInfo(String contentType, byte[] myDfBytes) {
			this.contentType = contentType;
			this.bytes = myDfBytes;
		}
	
		public String getContentType() {
			return contentType;
		}
	
		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(bytes);
		}
	}
	
	
	//Code for Pre-Approval Screen
	
	public boolean getNewEventsDetails(NewsEventsEntryForm newsEventsEntryForm) throws Exception {
		INewsEventsDetailsTransaction txn = NewsEventsDetailsTransactionImpl.getInstance();
		boolean flag=false;
		NewsEventsDetails newsDetails=txn.GetNewsEventsDetails(newsEventsEntryForm);
		if (newsDetails != null) {
			flag=true;
			NewsEventsDetailsHelper.getInstance().convertBoToForm(newsDetails,newsEventsEntryForm);
		}
		return flag;
	}
	
	
	public NewsEventsPhotosTO getSearchedPhoto(NewsEventsEntryForm newsEventsForm)throws Exception {
		log.info("enter getSearchedNews");
		INewsEventsDetailsTransaction txn = NewsEventsDetailsTransactionImpl.getInstance();
		NewsEventsDetailsHelper helper= NewsEventsDetailsHelper.getInstance();
		int categoryId=0;
		
		NewsEventsPhoto newslist=txn.getSerchedPhoto(newsEventsForm);
		NewsEventsPhotosTO newPhotoList = helper.convertSearchPhotoTOtoBO(newslist,newsEventsForm);
		return newPhotoList;
	}
	
	public List<NewsEventsDetailsTO> getSearchedNews(NewsEventsEntryForm newsEventsForm)throws Exception {
		log.info("enter getSearchedNews");
		INewsEventsDetailsTransaction txn = NewsEventsDetailsTransactionImpl.getInstance();
		NewsEventsDetailsHelper helper= NewsEventsDetailsHelper.getInstance();
		StringBuffer query = new StringBuffer("from NewsEventsDetails n where n.dateFrom>='"+newsEventsForm.getTodaysDate()+"' and n.preIsApproved=0 and n.isActive=1");
		List<NewsEventsDetails> newslist=txn.getSerchedNews(query);
		List<NewsEventsDetailsTO> newsToList = helper.convertNewsSearchTOtoBO(newslist,newsEventsForm);
		log.info("exit getSearchedNews");
		return newsToList;
	}

	public List<NewsEventsDetailsTO> getSearchedPost(NewsEventsEntryForm newsEventsForm)throws Exception {
		log.info("enter getSearchedNews");
		INewsEventsDetailsTransaction txn = NewsEventsDetailsTransactionImpl.getInstance();
		NewsEventsDetailsHelper helper= NewsEventsDetailsHelper.getInstance();
		StringBuffer query = new StringBuffer("from NewsEventsDetails n where n.dateFrom>='"+newsEventsForm.getTodaysDate()+"' and n.preIsApproved=1 and n.postIsApproved=0 and n.isPostDeptEntry=1 and n.isActive=1");
		List<NewsEventsDetails> newslist=txn.getSerchedNews(query);
		List<NewsEventsDetailsTO> newsToList = helper.convertNewsSearchTOtoBO(newslist,newsEventsForm);
		log.info("exit getSearchedNews");
		return newsToList;
	
	}
	public List<NewsEventsDetailsTO> getSearchedNewsAdminPost(NewsEventsEntryForm newsEventsForm)throws Exception {
		log.info("enter getSearchedNews");
		INewsEventsDetailsTransaction txn = NewsEventsDetailsTransactionImpl.getInstance();
		NewsEventsDetailsHelper helper= NewsEventsDetailsHelper.getInstance();
		StringBuffer query=null;
		if(newsEventsForm.getScreen().equalsIgnoreCase("Admin")){
			query = txn.getSerchedNewsQueryAdmin(newsEventsForm);
		}
		List<NewsEventsDetails> newslist=null;
		if(query!=null)
			newslist=txn.getSerchedNews(query);

		List<NewsEventsDetailsTO> newsToList = helper.convertNewsSearchTOtoBO(newslist,newsEventsForm);
		log.info("exit getSearchedNews");
		return newsToList;
	}

	public List<NewsEventsDetailsTO> getPostEventDeptEntry(NewsEventsEntryForm newsEventsForm)throws Exception {
		log.info("enter getSearchedNews");
		INewsEventsDetailsTransaction txn = NewsEventsDetailsTransactionImpl.getInstance();
		NewsEventsDetailsHelper helper= NewsEventsDetailsHelper.getInstance();
		StringBuffer query= new StringBuffer("from NewsEventsDetails n where n.isActive = 1 and n.postIsApproved=0 and n.preIsApproved=1 and n.dateFrom <='"+ newsEventsForm.getTodaysDate()+"'");
		List<NewsEventsDetails> newslist=null;
			newslist=txn.getSerchedNews(query);

		List<NewsEventsDetailsTO> newsToList = helper.convertNewsSearchTOtoBO(newslist, newsEventsForm);
		log.info("exit getSearchedNews");
		return newsToList;
	}
	
	public boolean getNewEventsDetailsAdmin(NewsEventsEntryForm newsEventsEntryForm) throws Exception {
		INewsEventsDetailsTransaction txn = NewsEventsDetailsTransactionImpl.getInstance();
		boolean flag=false;
		NewsEventsDetails newsDetails=txn.GetNewsEventsDetails(newsEventsEntryForm);
		if (newsDetails != null) {
			flag=true;
			NewsEventsDetailsHelper.getInstance().convertBoToFormAdmin(newsDetails,newsEventsEntryForm);
		}
		return flag;
	}
	
	public boolean updateMobNewsEventsAdmin(NewsEventsEntryForm newsEventsEntryForm,HttpServletRequest request)throws Exception 
	{
		log.info("call of updateMobNewsEventsDetails method in MobNewsEventsDetailsHandler class.");
		
		INewsEventsDetailsTransaction mobNewsEventsDetailsTransaction=NewsEventsDetailsTransactionImpl.getInstance();
		boolean isUpdated = mobNewsEventsDetailsTransaction.updateNewsEventDetailsAdmin(newsEventsEntryForm);
		log.info("end of updateMobNewsEventDetails method in MobNewsEventsDetailsHandler class.");
		return isUpdated;
	}
	public boolean updatePrePost(NewsEventsEntryForm newsEventsEntryForm,HttpServletRequest request)throws Exception 
	{
		log.info("call of updateMobNewsEventsDetails method in MobNewsEventsDetailsHandler class.");
		
		INewsEventsDetailsTransaction mobNewsEventsDetailsTransaction=NewsEventsDetailsTransactionImpl.getInstance();
		NewsEventsDetails mobNewsEventsDetails=NewsEventsDetailsHelper.getInstance().convertNewsFormToBOPrePost(newsEventsEntryForm,request);
		boolean isUpdated = mobNewsEventsDetailsTransaction.updateMobNewsEventDetails(mobNewsEventsDetails);
		log.info("end of updateMobNewsEventDetails method in MobNewsEventsDetailsHandler class.");
		return isUpdated;
	}
	/**
	 * @param photoId
	 * @param userId 
	 * @throws Exception
	 */
	public boolean deletePhoto(int photoId, String userId) throws Exception{
		INewsEventsDetailsTransaction transaction=NewsEventsDetailsTransactionImpl.getInstance();
		return transaction.deletePhoto(photoId,userId);
	}
	public boolean deleteResourse(int resourseId, String userId) throws Exception{
		INewsEventsDetailsTransaction transaction=NewsEventsDetailsTransactionImpl.getInstance();
		return transaction.deleteResourse(resourseId,userId);
	}
	public boolean deleteContact(int contactId, String userId) throws Exception{
		INewsEventsDetailsTransaction transaction=NewsEventsDetailsTransactionImpl.getInstance();
		return transaction.deleteContact(contactId,userId);
	}
	public boolean deleteParticipants(int participantId, String userId) throws Exception{
		INewsEventsDetailsTransaction transaction=NewsEventsDetailsTransactionImpl.getInstance();
		return transaction.deleteParticipants(participantId,userId);
	}
	/**
	 * @param newsEventsForm
	 * @param parseInt
	 * @throws Exception
	 */
	public void editMobNewsEventDetailsDeleteMode(
			NewsEventsEntryForm newsEventsForm, int mobNewaEventsDatailsId) throws Exception{
		INewsEventsDetailsTransaction mobNewsEventsDetailsTransaction=NewsEventsDetailsTransactionImpl.getInstance();
		NewsEventsDetails mobNewsEventsDetails=mobNewsEventsDetailsTransaction.editMobNewsEventDetails(mobNewaEventsDatailsId);
		List<NewsEventsPhotosTO> photoTOs = new ArrayList<NewsEventsPhotosTO>();
		if (mobNewsEventsDetails.getNewsEventsPhotos() != null) {
			Set<NewsEventsPhoto> photo = mobNewsEventsDetails.getNewsEventsPhotos();
			if (photo != null && !photo.isEmpty()) {
				Iterator<NewsEventsPhoto> iterator = photo.iterator();

				while (iterator.hasNext()) {
					NewsEventsPhoto photo1 = iterator.next();
					if (photo1 != null && photo1.getIsActive() != null && photo1.getIsActive()) {
						NewsEventsPhotosTO phototo = new NewsEventsPhotosTO();

						if (photo1.getId() > 0) {
							phototo.setId(photo1.getId());
						}
						if (photo1.getPhotoName()!=null && (StringUtils.isNotEmpty(photo1.getPhotoName()))) {
							phototo.setPhotoName(photo1.getPhotoName());
						}
						if (photo1.getNewsEventsId()!= null && photo1.getNewsEventsId().getId()>0) {
							phototo.setNewsEventsId(String.valueOf(photo1.getNewsEventsId().getId()));
						}
						photoTOs.add(phototo);
					}
				}
				newsEventsForm.setPhotoListSize(String.valueOf(photoTOs.size()));
				newsEventsForm.setOrgphotoListSize(String.valueOf(photoTOs.size()));
			} 
			// add 10 records to list
			for (int i = photoTOs.size(); i < 10; i++) {
				NewsEventsPhotosTO phototo = new NewsEventsPhotosTO();
				photoTOs.add(phototo);
			}
			newsEventsForm.setPhotosTO(photoTOs);
		}
	}
	
	
	public boolean sendMailToPublication(NewsEventsEntryForm newsEventsEntryForm) throws Exception {
		boolean sent=false;
		Properties prop = new Properties();
		try {
			InputStream inStr = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
		} catch (FileNotFoundException e) {
		log.error("Unable to read properties file...", e);
			return false;
		} catch (IOException e) {
			log.error("Unable to read properties file...", e);
			return false;
		}
			List<GroupTemplate> list=null;
			TemplateHandler temphandle=TemplateHandler.getInstance();
			 list= temphandle.getDuplicateCheckList(CMSConstants.NEWS_EVENTS_SUBMITTED_TEMPLATE);
			 String date = CommonUtil.getStringDate(new Date());
			if(list != null && !list.isEmpty()) {

				String desc = list.get(0).getTemplateDescription();
				String fromName = newsEventsEntryForm.getUserName();
				String fromAddress=newsEventsEntryForm.getUserEmailId();
				//String toName=prop.getProperty(CMSConstants.NEWS_EVENTS_TONAME);
				String toAddress=prop.getProperty(CMSConstants.NEWS_EVENTS_TOADDRESS);
				String subject= "News and Events Submitted";
				String message =desc;
				message = message.replace(CMSConstants.TEMPLATE_DATE,date);
				sent=sendMail(toAddress, subject, message, fromName, fromAddress);
				HtmlPrinter.printHtml(message);
				}
						 
			return sent;
	}
	
	public boolean sendMailToUser(NewsEventsEntryForm newsEventsEntryForm) throws Exception {
		boolean sent=false;
		Properties prop = new Properties();
		try {
			InputStream inStr = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
		} catch (FileNotFoundException e) {
		log.error("Unable to read properties file...", e);
			return false;
		} catch (IOException e) {
			log.error("Unable to read properties file...", e);
			return false;
		}
			List<GroupTemplate> list=null;
			TemplateHandler temphandle=TemplateHandler.getInstance();
			 list= temphandle.getDuplicateCheckList(CMSConstants.NEWS_EVENTS_SUBMITTED_USER_TEMPLATE);
			 String date = CommonUtil.getStringDate(new Date());
			if(list != null && !list.isEmpty()) {

				String desc = list.get(0).getTemplateDescription();
				String fromName = prop.getProperty(CMSConstants.NEWS_EVENTS_USER_FROMNAME);
				String fromAddress=CMSConstants.MAIL_USERID;
			//	String toName=newsEventsEntryForm.getUserName();
				String toAddress=newsEventsEntryForm.getUserEmailId();
				String subject= "News and Events has been Approved";
				String message =desc;
				message = message.replace(CMSConstants.TEMPLATE_DATE,date);
				sent=sendMail(toAddress, subject, message, fromName, fromAddress);
				HtmlPrinter.printHtml(message);
				}
						 
			return sent;
	}
	
	
	public boolean sendMailToAdmin(NewsEventsEntryForm newsEventsEntryForm) throws Exception {
		boolean sent=false;
		Properties prop = new Properties();
		try {
			InputStream inStr = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
		} catch (FileNotFoundException e) {
		log.error("Unable to read properties file...", e);
			return false;
		} catch (IOException e) {
			log.error("Unable to read properties file...", e);
			return false;
		}
			List<GroupTemplate> list=null;
			TemplateHandler temphandle=TemplateHandler.getInstance();
			 list= temphandle.getDuplicateCheckList(CMSConstants.NEWS_EVENTS_ADMIN_NOTIFICATION_MAIL);
			 String date = CommonUtil.getStringDate(new Date());
			if(list != null && !list.isEmpty()) {

				String desc = list.get(0).getTemplateDescription();
				String fromName = prop.getProperty(CMSConstants.NEWS_EVENTS_ADMIN_FROMNAME);
				String fromAddress=CMSConstants.MAIL_USERID;
			//	String toName=prop.getProperty(CMSConstants.NEWS_EVENTS_ADMIN_TONAME);
				String toAddress=prop.getProperty(CMSConstants.NEWS_EVENTS_ADMIN_TOADDRESS);
				String subject= "News and Events Approved by Publication";
				String message =desc;
				message = message.replace(CMSConstants.TEMPLATE_DATE,date);
				
				sent=sendMail(toAddress, subject, message, fromName, fromAddress);
				HtmlPrinter.printHtml(message);
				}
						 
			return sent;
	}
	/**
	 * Common Send mail
	 * @param admForm
	 * @return
	 */
	public boolean sendMail(String mailID, String sub,String message, String fromName, String fromAddress) {
			boolean sent=false;
				String toAddress=mailID;
				// MAIL TO CONSTRUCTION
				String subject=sub;
				String msg=message;
			
				MailTO mailto=new MailTO();
				mailto.setFromAddress(fromAddress);
				mailto.setToAddress(toAddress);
				mailto.setSubject(subject);
				mailto.setMessage(msg);
				mailto.setFromName(fromName);
				
				sent=CommonUtil.sendMail(mailto);
			return sent;
	}

}
