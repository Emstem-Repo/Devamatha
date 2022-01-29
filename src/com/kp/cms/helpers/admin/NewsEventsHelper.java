package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.bo.admin.NewsEvents;
import com.kp.cms.bo.admin.State;
import com.kp.cms.bo.exam.ExamValuators;
import com.kp.cms.forms.admin.DepartmentEntryForm;
import com.kp.cms.forms.admin.NewsEventsForm;
import com.kp.cms.helpers.admin.NewsEventsHelper;
import com.kp.cms.to.admin.NewsEventsTO;
import com.kp.cms.to.exam.ExternalEvaluatorTO;

public class NewsEventsHelper {
	
	private static final Log log = LogFactory.getLog(NewsEventsHelper.class);
	private static volatile NewsEventsHelper newsEventsHelper = null;
	
	private NewsEventsHelper() {
	}
	
	public static NewsEventsHelper getInstance() {

		if (newsEventsHelper == null) {
			newsEventsHelper = new NewsEventsHelper();
		}
		return newsEventsHelper;
	}
	
	public NewsEventsTO populateBotoToEdit(NewsEvents newsEvents)throws Exception {
		log.info("Entering into populateBotoToEdit of NewsEventsHelper");
		NewsEventsTO newsEventsTO = new NewsEventsTO();
		if (newsEvents != null) {
			newsEventsTO.setId(newsEvents.getId());
			if (newsEvents.getDescription() != null && !newsEvents.getDescription().isEmpty()) {
			newsEventsTO.setName(newsEvents.getDescription());
			}
			if (newsEvents.getRequired() != null && !newsEvents.getRequired().isEmpty()) {
				newsEventsTO.setRequired(newsEvents.getRequired());
			}

		}
			return newsEventsTO;
	}
	
	public NewsEvents populateTotoBoUpdate(NewsEventsTO byTO) throws Exception{
		log.info("Entering into populateTotoBoUpdate of NewsEventsHelper");
		if (byTO != null) {
			NewsEvents newsEvents = new NewsEvents();
			newsEvents.setId(byTO.getId());
			newsEvents.setDescription(byTO.getName());
			newsEvents.setRequired(byTO.getRequired());
			newsEvents.setLastModifiedDate(new Date());
			newsEvents.setModifiedBy(byTO.getModifiedBy());
			
			if (newsEvents != null) {
				return newsEvents;
			}
		}
		log.info("Leaving from populateTotoBoUpdate of ExternalEvaluatorHelper");
		return null;
	}
	
	public NewsEvents populateNewsDateFromFrom(NewsEventsForm newsEventsForm) {
		NewsEvents newsEvents= new NewsEvents();
			newsEvents.setDescription(newsEventsForm.getDescription());
			newsEvents.setRequired(newsEventsForm.getRequired());

		return newsEvents;
	}

}
