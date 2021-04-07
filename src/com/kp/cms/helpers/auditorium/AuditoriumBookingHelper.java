package com.kp.cms.helpers.auditorium;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.auditorium.AuditoriumBooking;
import com.kp.cms.bo.auditorium.BlockDetails;
import com.kp.cms.bo.auditorium.BookingRequirements;
import com.kp.cms.bo.auditorium.VenueDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.auditorium.AuditoriumBookingForm;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.to.auditorium.AuditoriumBookingTo;
import com.kp.cms.to.auditorium.BlockDetailsTO;
import com.kp.cms.to.auditorium.VenueDetailsTO;
import com.kp.cms.transactions.auditorium.IAuditoriumBookingTransaction;
import com.kp.cms.transactionsimpl.auditorium.AuditoriumBookingTxnImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.jms.MailTO;

public class AuditoriumBookingHelper {
	
	private static final Log log=LogFactory.getLog(AuditoriumBookingHelper.class);
	public static volatile AuditoriumBookingHelper auditoriumHelper = null;
	public IAuditoriumBookingTransaction transaction = AuditoriumBookingTxnImpl.getInstance();
	public static AuditoriumBookingHelper getInstance() {
		if (auditoriumHelper == null) {
			auditoriumHelper = new AuditoriumBookingHelper();
		}
		return auditoriumHelper;
	}
	/**
	 * @param blockList
	 * @return
	 */
	public List<BlockDetailsTO> convertBoToToBlockList(List<BlockDetails> blockList){
		List<BlockDetailsTO> blockToList= new ArrayList<BlockDetailsTO>();
		Iterator<BlockDetails> itr = blockList.iterator();
		while(itr.hasNext()){
			BlockDetails block = itr.next();
			BlockDetailsTO blockTo = new BlockDetailsTO();
		    if(block.getBlockName()!=null)
		    	blockTo.setBlockName(block.getBlockName());
		    blockTo.setId(block.getId());
		    blockToList.add(blockTo);
		}
		return blockToList;
	}
	/**
	 * @param venueList
	 * @return
	 */
	public List<VenueDetailsTO> convertBoToToVenueList(List<VenueDetails> venueList){
		List<VenueDetailsTO> venueToList = new ArrayList<VenueDetailsTO>();
		Iterator<VenueDetails> itr = venueList.iterator();
		while(itr.hasNext()){
			VenueDetails venue = itr.next();
			VenueDetailsTO venueTo = new VenueDetailsTO();
			venueTo.setId(venue.getId());
			if(venue.getBlock()!=null)
				venueTo.setBlockName(venue.getBlock().getBlockName());
			if(venue.getVenueName()!=null)
				venueTo.setVenueName(venue.getVenueName());
			if(venue.getRoomNo()!=null)
				venueTo.setRoomNo(venue.getRoomNo());
			venueToList.add(venueTo);
		}
		return venueToList;
	}
	/**
	 * @param venueList
	 * @return
	 */
	public static Map<String,List<String>> setBlockAndVenueToMap(List<VenueDetails> venueList){
		Map<String,List<String>> blockAndVenueMap = new HashMap<String, List<String>>();
		Iterator<VenueDetails> itr = venueList.iterator();
		while(itr.hasNext()){
			VenueDetails venue = (VenueDetails)itr.next();
			if(venue!=null && venue.getBlock()!=null && venue.getBlock().getBlockName()!=null){
				if(blockAndVenueMap.containsKey(venue.getBlock().getBlockName())){
				    List<String> list = blockAndVenueMap.get(venue.getBlock().getBlockName());
				    list.add(venue.getVenueName());
				    blockAndVenueMap.put(venue.getBlock().getBlockName(), list);
				}else{
					List<String> venues = new ArrayList<String>();
					venues.add(venue.getVenueName());
					blockAndVenueMap.put(venue.getBlock().getBlockName(), venues);
				}
			}
		}
		return blockAndVenueMap;
	}
	/**
	 * @param blockDetails
	 * @return
	 */
	public Map<Integer,String> setBlockMap(List<BlockDetails> blockDetails){
		Map<Integer,String> blockMap = new HashMap<Integer, String>();
		if (blockDetails != null) {
			Iterator<BlockDetails> itr = blockDetails.iterator();
			while (itr.hasNext()) {
				BlockDetails block = itr.next();
				if (block != null) {
					blockMap.put(block.getId(), block.getBlockName());
				}
			}
		}
		return blockMap;
	}
	/**
	 * @param requirements
	 * @return
	 */
	public Map<Integer,String> convertListToMap(List<BookingRequirements> requirements){
		Map<Integer,String> requirementsMap = new HashMap<Integer, String>();
		if (requirements != null && !requirements.isEmpty()) {
			Iterator<BookingRequirements> itr = requirements.iterator();
			while (itr.hasNext()) {
				BookingRequirements booking = itr.next();
				if (booking != null) {
					requirementsMap.put(booking.getId(), booking.getName());
				}
			}
		} 
		return requirementsMap;  
	}
	/**
	 * @param auditoriumBookingForm
	 * @return
	 * @throws Exception
	 */
	public AuditoriumBooking convertFormToAuditoriumBo(AuditoriumBookingForm auditoriumBookingForm) throws Exception{
		AuditoriumBooking audiBooking = new AuditoriumBooking();
		Set<BookingRequirements> bookingRequirementSet=new HashSet<BookingRequirements>();
		if(auditoriumBookingForm.getBlockId()!=null && !auditoriumBookingForm.getBlockId().isEmpty()){
			BlockDetails block = new BlockDetails();
			if(auditoriumBookingForm.getBlockId()!=null && !auditoriumBookingForm.getBlockId().isEmpty()){
			block.setId(Integer.parseInt(auditoriumBookingForm.getBlockId()));
			audiBooking.setBlock(block);
			}
		}
		if(auditoriumBookingForm.getVenueId()!=null && !auditoriumBookingForm.getVenueId().isEmpty()){
			VenueDetails venue = new VenueDetails();
			venue.setId(Integer.parseInt(auditoriumBookingForm.getVenueId()));
			audiBooking.setVenue(venue);
		} 
		if(auditoriumBookingForm.getMultipleCheckValues()!=null && !auditoriumBookingForm.getMultipleCheckValues().isEmpty())
		{
		String bookingRequir[]=auditoriumBookingForm.getMultipleCheckValues().split(",");
		for (String bookedRequirId : bookingRequir) {
            BookingRequirements requirements=transaction.getBookingRequirById(Integer.parseInt(bookedRequirId)) ;
			bookingRequirementSet.add(requirements);
		}
		audiBooking.setBookingRequirements(bookingRequirementSet);
		}
		
		if(auditoriumBookingForm.getStartTime()!=null && !auditoriumBookingForm.getStartTime().isEmpty()){
			audiBooking.setStartTime(auditoriumBookingForm.getStartTime());
		}
		if(auditoriumBookingForm.getEndTime()!=null && !auditoriumBookingForm.getEndTime().isEmpty()){
			audiBooking.setEndTime(auditoriumBookingForm.getEndTime());
		}
		audiBooking.setBookedBy(auditoriumBookingForm.getUserId());
		audiBooking.setCreatedBy(auditoriumBookingForm.getUserId());
		audiBooking.setCreatedDate(new Date());
		audiBooking.setLastModifiedDate(new Date());
		audiBooking.setModifiedBy(auditoriumBookingForm.getUserId());
		if(auditoriumBookingForm.getRemarks()!=null && !auditoriumBookingForm.getRemarks().isEmpty()){
			audiBooking.setRemarks(auditoriumBookingForm.getRemarks());
		}
			//Long totalEventId=transaction.getTotalCalendarEventId();
			//audiBooking.setEventId(totalEventId.intValue()+1);
		if(auditoriumBookingForm.getDate()!=null){
			audiBooking.setDate(CommonUtil.ConvertStringToDateForAuditorium(auditoriumBookingForm.getDate()));
		}
		audiBooking.setIsApproved(false);
		audiBooking.setRejected(false);
		audiBooking.setEventCanceled(false);
		return audiBooking;
	}
	/**
	 * @param auditoriumBookingForm
	 * @return
	 * @throws Exception
	 */
	public JSONArray getSavedEvents(AuditoriumBookingForm auditoriumBookingForm)throws Exception{
		IAuditoriumBookingTransaction transaction=new AuditoriumBookingTxnImpl();
		 JSONArray jsonArray=new JSONArray();
		try{
			List<AuditoriumBooking> eventsBoList=null;
			if(auditoriumBookingForm.getCheckDuplicateCalendarEvents()==null){
			if(auditoriumBookingForm.getBlockId()!=null && auditoriumBookingForm.getVenueId()!=null && !auditoriumBookingForm.getBlockId().isEmpty() && !auditoriumBookingForm.getVenueId().isEmpty()){
				eventsBoList = transaction.getCalendarEventsByBlockAndVenue(auditoriumBookingForm);
			}else{
				eventsBoList = transaction.getCalendarEventsData();
			}
			}else{
				eventsBoList = transaction.getCalendarEventsData();
			}
			//Long totalEventId=transaction.getTotalCalendarEventId();
		  List<Integer> requirIDList=transaction.getRequirementsId();
		  if(eventsBoList!=null && !eventsBoList.isEmpty())
		  {
		  Iterator iterator=eventsBoList.iterator();
		  while (iterator.hasNext()) {
			AuditoriumBooking bookedEvents = (AuditoriumBooking) iterator.next();
			if(bookedEvents.getRejected())
			{
				if(bookedEvents.getCreatedBy().equals(auditoriumBookingForm.getUserId()))
				{
					String mode="rejected";
				   JSONObject jsonObject= setEventsToJson(bookedEvents,mode,auditoriumBookingForm,requirIDList);
				  // jsonObject.put("totalEventId", totalEventId);
				    jsonArray.add(jsonObject);
				}
			}
			else{
				String mode="";
				JSONObject jsonObject= setEventsToJson(bookedEvents,mode,auditoriumBookingForm,requirIDList);
				//jsonObject.put("totalEventId", totalEventId);
			    jsonArray.add(jsonObject);
		}
		  }
		  }
		  else{
			  JSONObject jsonObject=new JSONObject();
			  jsonObject.put("allRequirIdList", requirIDList);
			 // jsonObject.put("totalEventId", totalEventId);
			  jsonArray.add(jsonObject);
		  }
		  }catch(Exception exception){
			  exception.printStackTrace();
		  }
		   return jsonArray;
		   
	   }
	/**
	 * @param time
	 * @return
	 */
	public String[] timeFormats(String time){
		String time1= time.substring(0, 5);
		String times[]=time1.split(":");
		String times1[]=time.split(" ");
		String hoursMins[]=new String[2];
		hoursMins[1]=times[1];
		if(( Integer.parseInt(times[0])>=1 && Integer.parseInt(times[0])<12 ) && times1[1].equalsIgnoreCase("pm") )
		{
			int afterHours=Integer.parseInt(times[0])+12;
			hoursMins[0]=String.valueOf(afterHours);
		}else{
			if(Integer.parseInt(times[0])==12 && times1[1].equalsIgnoreCase("am") ){
				hoursMins[0]="0";
			}else{
			hoursMins[0]=times[0];
			}
		}
		return hoursMins;
	}
	/**
	 * @param requirementsList
	 * @return
	 */
	public Map<Integer,String> setRequirementsMap(List<BookingRequirements> requirementsList){
		Map<Integer,String> requirementMap = new HashMap<Integer, String>();
		if (requirementsList != null) {
			Iterator<BookingRequirements> itr = requirementsList.iterator();
			while (itr.hasNext()) {
				BookingRequirements requirements = itr.next();
				if (requirements != null) {
					requirementMap.put(requirements.getId(), requirements.getName());
				}
			}
		}
		return requirementMap;
	}
	
	/**
	 * @param auditoriumBookingForm
	 * @return
	 * @throws Exception
	 */
	public boolean sendMailAndSmS(AuditoriumBookingForm auditoriumBookingForm) throws Exception
	{
		boolean sent=false;
		boolean sentSms=false;
		if(auditoriumBookingForm.getAuditoriumBooking()!=null)
		{
			Properties prop = new Properties();
			try {
				InputStream inStr = CommonUtil.class.getClassLoader()
						.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				prop.load(inStr);
			} catch (FileNotFoundException e) {
			log.error("Unable to read properties file...", e);
			} catch (IOException e) {
				log.error("Unable to read properties file...", e);
			}
			if(auditoriumBookingForm.getMode().equalsIgnoreCase("Save")|| auditoriumBookingForm.getMode().equalsIgnoreCase("UserUpdate")|| auditoriumBookingForm.getMode().equalsIgnoreCase("Deleted")|| auditoriumBookingForm.getMode().equalsIgnoreCase("UserDropAndResize")|| auditoriumBookingForm.getMode().equalsIgnoreCase("CancelEventByUser"))
			{
				if(auditoriumBookingForm.getMode().equalsIgnoreCase("Save"))
				{
				transaction.setUserMailIdAndMobileNoByUserIdToForm(Integer.parseInt(auditoriumBookingForm.getUserId()), auditoriumBookingForm);
				}
				if(auditoriumBookingForm.getMailId()!=null && !auditoriumBookingForm.getMailId().isEmpty())
				{
					String desc="";
					List<GroupTemplate> list=null;
					//get template and replace dynamic data
					TemplateHandler temphandle=TemplateHandler.getInstance();
					 list= temphandle.getDuplicateCheckList(CMSConstants.AUDITORIUM_BOOKING_EVENTS_TEMPLATE);
					
					if(list != null && !list.isEmpty()) {

						desc = list.get(0).getTemplateDescription();
					}
					desc=setMailDescription(auditoriumBookingForm.getAuditoriumBooking(),desc);
					desc=desc.replace(CMSConstants.TEMPLATE_USERNAME,auditoriumBookingForm.getUserName());
					if(auditoriumBookingForm.getMode().equalsIgnoreCase("Save"))
					{
						desc=desc.replace(CMSConstants.TEMPLATE_MODE,"Event Generated");	
					}else if(auditoriumBookingForm.getMode().equalsIgnoreCase("UserUpdate")){
						desc=desc.replace(CMSConstants.TEMPLATE_MODE,"Event Updated");
					}
					else if(auditoriumBookingForm.getMode().equalsIgnoreCase("Deleted")){
						desc=desc.replace(CMSConstants.TEMPLATE_MODE,"Event Deleted");
					}
					else if(auditoriumBookingForm.getMode().equalsIgnoreCase("UserDrop"))
					{
						desc=desc.replace(CMSConstants.TEMPLATE_MODE,"Event Dragged");
					}else if(auditoriumBookingForm.getMode().equalsIgnoreCase("CancelEventByUser")){
						desc=desc.replace(CMSConstants.TEMPLATE_MODE,"Event Canceled");
					}
					String venue=auditoriumBookingForm.getAuditoriumBooking().getVenue().getVenueName();
					int blockId=auditoriumBookingForm.getAuditoriumBooking().getVenue().getBlock().getId();
					String emailID=transaction.getAdminMailIdByVenueAndBlock(venue,blockId);
					String fromName = prop.getProperty(CMSConstants.STUDENTLOGIN_CERTIFICATE_REQUEST_FROMNAME);
					String fromAddress=auditoriumBookingForm.getMailId();
					String subject="Auditorium Booking Status";
					sent=sendMail(emailID, subject, desc, fromName, fromAddress);
			}
			
		}
			else if(auditoriumBookingForm.getMode().equalsIgnoreCase("AdminApproved")|| auditoriumBookingForm.getMode().equalsIgnoreCase("AdminModify")|| auditoriumBookingForm.getMode().equalsIgnoreCase("Rejected")||auditoriumBookingForm.getMode().equalsIgnoreCase("AdminDrop"))
			{
				if(auditoriumBookingForm.getMailId()!=null && !auditoriumBookingForm.getMailId().isEmpty())
				{
					String desc="";
					String userName="";
					Users users=transaction.getUserName(auditoriumBookingForm.getAuditoriumBooking().getCreatedBy());
					if(users!=null){
						if(users.getEmployee()!=null){
							if(users.getEmployee().getFirstName()!=null && !users.getEmployee().getFirstName().isEmpty()){
								auditoriumBookingForm.setUserName(users.getEmployee().getFirstName());	
							}
						}else if(users.getDepartment()!=null && users.getUserName()!=null ){
							auditoriumBookingForm.setUserName(users.getUserName());
						}
					}
					List<GroupTemplate> list=null;
					//get template and replace dynamic data
					TemplateHandler temphandle=TemplateHandler.getInstance();
					 list= temphandle.getDuplicateCheckList(CMSConstants.AUDITORIUM_APPROVING_EVENTS_TEMPLATE);
					
					if(list != null && !list.isEmpty()) {

						desc = list.get(0).getTemplateDescription();
					}
					desc=setMailDescription(auditoriumBookingForm.getAuditoriumBooking(),desc);
					desc=desc.replace(CMSConstants.TEMPLATE_USERNAME,userName);
					if(auditoriumBookingForm.getMode().equalsIgnoreCase("AdminApproved"))
					{
						desc=desc.replace(CMSConstants.SMS_TEMPLATE_STATUS,"Approved");	
					}else if(auditoriumBookingForm.getMode().equalsIgnoreCase("AdminModify")){
						desc=desc.replace(CMSConstants.SMS_TEMPLATE_STATUS,"Modified and Approved");
					}
					else if(auditoriumBookingForm.getMode().equalsIgnoreCase("Rejected"))
					{
						desc=desc.replace(CMSConstants.SMS_TEMPLATE_STATUS,"Rejected");
					}
					else if(auditoriumBookingForm.getMode().equalsIgnoreCase("AdminDrop"))
					{
						desc=desc.replace(CMSConstants.SMS_TEMPLATE_STATUS,"Modified and Approved");
					}
					String venue=auditoriumBookingForm.getAuditoriumBooking().getVenue().getVenueName();
					int blockId=auditoriumBookingForm.getAuditoriumBooking().getVenue().getBlock().getId();
					String emailID=transaction.getAdminMailIdByVenueAndBlock(venue,blockId);
					String fromName = prop.getProperty(CMSConstants.STUDENTLOGIN_CERTIFICATE_REQUEST_FROMNAME);
					String fromAddress=auditoriumBookingForm.getMailId();
					String subject="Auditorium Booking Status";
					sent=sendMail(fromAddress, subject, desc, fromName,emailID);
			}
				if(auditoriumBookingForm.getMobileNo()!=null && !auditoriumBookingForm.getMobileNo().isEmpty())
				{
					String desc="";
					String userName="";
					Users users=transaction.getUserName(auditoriumBookingForm.getAuditoriumBooking().getCreatedBy());
					if(users!=null){
						if(users.getEmployee()!=null){
							if(users.getEmployee().getFirstName()!=null && !users.getEmployee().getFirstName().isEmpty()){
								userName=users.getEmployee().getFirstName();	
							}
						}else if(users.getDepartment()!=null && users.getUserName()!=null ){
							userName=users.getUserName();
						}
					}
					String adminUserName="";
					 Users users2 =transaction.getUserName(auditoriumBookingForm.getAuditoriumBooking().getAuthorizedBy());
					 if(users2!=null){
							if(users2.getEmployee()!=null){
								if(users2.getEmployee().getFirstName()!=null && !users2.getEmployee().getFirstName().isEmpty()){
									adminUserName=users2.getEmployee().getFirstName();	
								}
							}else if(users2.getDepartment()!=null && users2.getUserName()!=null ){
								adminUserName=users2.getUserName();
							}
						}
					String mobileNo="";
					if(auditoriumBookingForm.getMobileNo().trim().equals("0091") || auditoriumBookingForm.getMobileNo().trim().equals("+91")
							|| auditoriumBookingForm.getMobileNo().trim().equals("091") || auditoriumBookingForm.getMobileNo().trim().equals("0"))
						mobileNo = "91";
					if(auditoriumBookingForm.getMobileNo()!=null && !auditoriumBookingForm.getMobileNo().isEmpty())
					{
						mobileNo=mobileNo+auditoriumBookingForm.getMobileNo();	
					}
					Properties properties = new Properties();
			        InputStream in1 = AuditoriumBookingHelper.class.getClassLoader().getResourceAsStream(CMSConstants.SMS_FILE_CFG);
			        properties.load(in1);
					String senderNumber=properties.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
					String senderName=properties.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
					SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
					List<SMSTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.AUDITORIUM_APPROVING_EVENTS_SMS_TEMPLATE);
					if(list != null && !list.isEmpty()) {
						desc = list.get(0).getTemplateDescription();
					}
					desc=desc.replace(CMSConstants.TEMPLATE_USERNAME,userName);
					desc=desc.replace(CMSConstants.TEMPLATE_DATE,CommonUtil.formatDates(auditoriumBookingForm.getAuditoriumBooking().getDate()));
					desc=desc.replace(CMSConstants.TEMPLATE_AUDITORIUM_VENUE,auditoriumBookingForm.getAuditoriumBooking().getVenue().getVenueName());
					if(auditoriumBookingForm.getMode().equalsIgnoreCase("AdminApproved"))
					{
						desc=desc.replace(CMSConstants.SMS_TEMPLATE_STATUS,"Approved by "+adminUserName);	
					}else if(auditoriumBookingForm.getMode().equalsIgnoreCase("AdminModify")){
						desc=desc.replace(CMSConstants.SMS_TEMPLATE_STATUS,"Modified and Approved by "+adminUserName);
					}
					else if(auditoriumBookingForm.getMode().equalsIgnoreCase("Rejected"))
					{
						desc=desc.replace(CMSConstants.SMS_TEMPLATE_STATUS,"Rejected by "+adminUserName);
					}
					else if(auditoriumBookingForm.getMode().equalsIgnoreCase("AdminDrop"))
					{
						desc=desc.replace(CMSConstants.SMS_TEMPLATE_STATUS,"Modified and Approved by "+adminUserName);
					}
					if(StringUtils.isNumeric(mobileNo) && (mobileNo.length()==12 && desc.length()<=160)){
						MobileMessaging mob=new MobileMessaging();
						mob.setDestinationNumber(mobileNo);
						mob.setMessageBody(desc);
						mob.setMessagePriority(3);
						mob.setSenderName(senderName);
						mob.setSenderNumber(senderNumber);
						mob.setMessageEnqueueDate(new Date());
						mob.setIsMessageSent(false);
						sentSms=PropertyUtil.getInstance().save(mob);
				}
				
			}
			}
			
		}
		if(sent==true && sentSms==true)
			return sent;
		else 
			return sent;
		
	}

	/**
	 * @param mailID
	 * @param sub
	 * @param message
	 * @param fromName
	 * @param fromAddress
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
	/**
	 * @param auditoriumBooking
	 * @param desc
	 * @return
	 */
	public String setMailDescription(AuditoriumBooking auditoriumBooking,String desc)
	{
		desc=desc.replace(CMSConstants.TEMPLATE_DATE,CommonUtil.formatDates(auditoriumBooking.getDate()));
		
		desc=desc.replace(CMSConstants.TEMPLATE_AUDITORIUM_BLOCK,auditoriumBooking.getBlock().getBlockName());
		desc=desc.replace(CMSConstants.TEMPLATE_AUDITORIUM_VENUE,auditoriumBooking.getVenue().getVenueName());
		desc=desc.replace(CMSConstants.TEMPLATE_AUDITORIUM_FROM_TIME,auditoriumBooking.getStartTime());
		return desc=desc.replace(CMSConstants.TEMPLATE_AUDITORIUM_TO_TIME,auditoriumBooking.getEndTime());
	}

	/**
	 * @param bookedEvents
	 * @param mode
	 * @param auditoriumBookingForm
	 * @param requirIDList
	 * @return
	 * @throws Exception
	 */
	public JSONObject setEventsToJson(AuditoriumBooking bookedEvents,String mode,AuditoriumBookingForm auditoriumBookingForm,List<Integer> requirIDList) throws Exception {
		JSONObject jsonObject=new JSONObject();
		String startTime=bookedEvents.getStartTime();
		String endTime = bookedEvents.getEndTime();
		Date date = bookedEvents.getDate();
		String stringDate=CommonUtil.formatDates(bookedEvents.getDate());
		String sTime[]=timeFormats(startTime);
		String eTime[]=timeFormats(endTime);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		jsonObject.put("day", calendar.get(Calendar.DATE));
		jsonObject.put("year", calendar.get(Calendar.YEAR));
		jsonObject.put("month", calendar.get(Calendar.MONTH));
		jsonObject.put("startHours", sTime[0]);
		jsonObject.put("startMinutes", sTime[1]);
		jsonObject.put("endHours", eTime[0]);
		jsonObject.put("endMinutes", eTime[1]);
		jsonObject.put("id", bookedEvents.getId());
		jsonObject.put("bookedUser", bookedEvents.getCreatedBy());
		jsonObject.put("currentUser", auditoriumBookingForm.getUserId());
		jsonObject.put("blockId", bookedEvents.getBlock().getId());
		jsonObject.put("venueId", bookedEvents.getVenue().getId());
		jsonObject.put("blockName", bookedEvents.getBlock().getBlockName());
		jsonObject.put("venueName", bookedEvents.getVenue().getVenueName());
		Map<Integer, String> venueMap=transaction.getVenuesByBlockDetails(bookedEvents.getBlock().getId());
		jsonObject.put("venueMap", venueMap);
		String venueName="";
		if(bookedEvents.getVenue()!=null){
		venueName = bookedEvents.getVenue().getVenueName();
		}
		if(mode.equalsIgnoreCase("rejected"))
		{
		Users users=transaction.getUserName(bookedEvents.getRejectedBy());
		if(users!=null){
			if(users.getEmployee()!=null){
				if(users.getEmployee().getFirstName()!=null &&!users.getEmployee().getFirstName().isEmpty()){
					jsonObject.put("bookedBy"," Rejected By "+users.getEmployee().getFirstName());
				}if(users.getEmployee().getDepartment()!=null){
					if(users.getEmployee().getDepartment().getName()!=null){
						jsonObject.put("department",users.getEmployee().getDepartment().getName());
					}
				}
			}else if(users.getDepartment()!=null && users.getUserName()!=null){
				if(users.getDepartment().getName()!=null){
					jsonObject.put("department",users.getDepartment().getName());
				}
				jsonObject.put("bookedBy"," Rejected By "+users.getUserName());
			}
		}
		jsonObject.put("rejected", bookedEvents.getRejected());
		}else {
			Users users =transaction.getUserName(bookedEvents.getCreatedBy()); 
			jsonObject.put("title",venueName);
			if(users!=null){
				if(users.getEmployee()!=null){
					if(users.getEmployee().getFirstName()!=null &&!users.getEmployee().getFirstName().isEmpty()){
						jsonObject.put("bookedBy", " Booked By "+users.getEmployee().getFirstName());
					}if(users.getEmployee().getDepartment()!=null){
						if(users.getEmployee().getDepartment().getName()!=null){
							jsonObject.put("department",users.getEmployee().getDepartment().getName());
						}
					}
				}else if(users.getDepartment()!=null && users.getUserName()!=null){
					if(users.getDepartment().getName()!=null){
						jsonObject.put("department",users.getDepartment().getName());
					}
					jsonObject.put("bookedBy", " Booked By "+users.getUserName());
				}
			}
		}
		 List<Integer> list=new ArrayList<Integer>();
		 StringBuffer requireName=new StringBuffer();
			for (BookingRequirements requirements2 : bookedEvents.getBookingRequirements()) {
				list.add(requirements2.getId());
				requireName=requireName.append(", ").append(requirements2.getName());
			}
			jsonObject.put("requirementName", requireName.toString());
			jsonObject.put("requireList", list);
			jsonObject.put("allRequirIdList", requirIDList);
			jsonObject.put("approved", bookedEvents.getIsApproved());
			if(bookedEvents.getRemarks()!=null && !bookedEvents.getRemarks().isEmpty()){
				jsonObject.put("remarks", bookedEvents.getRemarks());	
			}else{
				jsonObject.put("remarks", " ");
			}
			jsonObject.put("dateForDup", CommonUtil.ConvertStringToDateForAuditorium1(stringDate));
			jsonObject.put("adminRemarks", bookedEvents.getAdminRemarks());
		   return jsonObject;
	}
	/**
	 * @param auditoriumBookingList
	 * @throws Exception 
	 */
	public Map<String, List<AuditoriumBookingTo>> setApprovedEventsToMap(List<AuditoriumBooking> auditoriumBookingList) throws Exception{
		Map<String, List<AuditoriumBookingTo>> auditoriumBookingMap= new LinkedHashMap<String, List<AuditoriumBookingTo>>();
		for (AuditoriumBooking auditoriumBooking : auditoriumBookingList) {
			List<AuditoriumBookingTo> auditoriumBookingToList=null;
               if(auditoriumBookingMap.containsKey(CommonUtil.formatDates(auditoriumBooking.getDate()))){
            	   AuditoriumBookingTo auditoriumBookingTo= settingBoToTos(auditoriumBooking);
            	   auditoriumBookingToList=auditoriumBookingMap.get(CommonUtil.formatDates(auditoriumBooking.getDate()));
            	   auditoriumBookingToList.add(auditoriumBookingTo);
            	   auditoriumBookingMap.put(CommonUtil.formatDates(auditoriumBooking.getDate()), auditoriumBookingToList);
               }else{
            	   auditoriumBookingToList=new ArrayList<AuditoriumBookingTo>();
            	   AuditoriumBookingTo auditoriumBookingTo=settingBoToTos(auditoriumBooking);
            	   auditoriumBookingToList.add(auditoriumBookingTo);
            	   auditoriumBookingMap.put(CommonUtil.formatDates(auditoriumBooking.getDate()), auditoriumBookingToList);
               }
		}  
		return auditoriumBookingMap;
		
	}
	/**
	 * @param auditoriumBooking
	 * @return
	 * @throws Exception
	 */
	public AuditoriumBookingTo settingBoToTos(AuditoriumBooking auditoriumBooking) throws Exception{
		 AuditoriumBookingTo auditoriumBookingTo=new AuditoriumBookingTo();
		   if(auditoriumBooking.getBlock().getBlockName()!=null){
			auditoriumBookingTo.setBlockName(auditoriumBooking.getBlock().getBlockName());	            		   
			 }if(auditoriumBooking.getVenue().getVenueName()!=null){
				auditoriumBookingTo.setVenueName(auditoriumBooking.getVenue().getVenueName()); 
			 }if(auditoriumBooking.getBookingRequirements()!=null && !auditoriumBooking.getBookingRequirements().isEmpty()){
				 StringBuffer requireName=new StringBuffer();
					for (BookingRequirements requirements : auditoriumBooking.getBookingRequirements()) {
						requireName=requireName.append(", ").append(requirements.getName());
					}
					auditoriumBookingTo.setBookingRequirements(requireName.toString());
			 }else{
				 auditoriumBookingTo.setBookingRequirements("No Requirements"); 
			 }if(auditoriumBooking.getStartTime()!=null){
				 auditoriumBookingTo.setStartTime(auditoriumBooking.getStartTime());
			 }if(auditoriumBooking.getEndTime()!=null){
				 auditoriumBookingTo.setEndTime(auditoriumBooking.getEndTime());
			 }if(auditoriumBooking.getCreatedBy()!=null){
				 Users users =transaction.getUserName(auditoriumBooking.getCreatedBy());
				 if(users!=null){
					 if(users.getEmployee()!=null){
						 if(users.getEmployee().getFirstName()!=null && !users.getEmployee().getFirstName().isEmpty()){
							 auditoriumBookingTo.setBookedBy(users.getEmployee().getFirstName()); 
						 }if(users.getEmployee().getDepartment()!=null){
							 if(users.getEmployee().getDepartment().getName()!=null){
								 auditoriumBookingTo.setDepartment(users.getEmployee().getDepartment().getName());
							 }
						 }
					 }else if(users.getDepartment()!=null && users.getUserName()!=null){
						 if(users.getDepartment().getName()!=null && !users.getDepartment().getName().isEmpty()){
							 auditoriumBookingTo.setDepartment(users.getDepartment().getName());
						 }
						 auditoriumBookingTo.setBookedBy(users.getUserName());
					 }
				 }
			 }
		return auditoriumBookingTo;
	}
	
	/**
	 * @param auditoriumBookingForm
	 * @param venueDetails
	 * @return
	 */
	public String getFacilityAvailableSetting(AuditoriumBookingForm auditoriumBookingForm,VenueDetails venueDetails){
		String facilityAvailable=null;
		if(venueDetails!=null){
			if(venueDetails.getBlock().getId()==Integer.parseInt(auditoriumBookingForm.getBlockId())){
				if(venueDetails.getFacilityAvailable()!=null && !venueDetails.getFacilityAvailable().isEmpty()){
					facilityAvailable=venueDetails.getFacilityAvailable();
				}
			}
		}
		return facilityAvailable;
	}
}
