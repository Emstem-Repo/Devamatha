package com.kp.cms.actions.sap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.sap.ExamScheduleForm;
import com.kp.cms.handlers.sap.ExamScheduleHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.TeacherDepartmentEntryHandler;
import com.kp.cms.to.sap.ExamScheduleTo;
import com.kp.cms.utilities.CommonUtil;

public class ExamScheduleAction extends BaseDispatchAction{

	private static final Log log = LogFactory.getLog(ExamScheduleAction.class);
		
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward initExamSchedul(ActionMapping mapping,ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			ExamScheduleForm examScheduleForm=(ExamScheduleForm)form;
			examScheduleForm.clear();
				Calendar now = Calendar.getInstance();
				int month = now.get(Calendar.MONTH);
				examScheduleForm.setMonth(String.valueOf(month+1));
				Map<Integer,String> teachersMap =TeacherDepartmentEntryHandler.getInstance().getTeacherDepartmentsName();
				examScheduleForm.setMainTeachersMap(teachersMap);
				Map<Integer,String> workLocationMap=ExamScheduleHandler.getInstance().getWorkLocation();
				examScheduleForm.setWorkLocationMap(workLocationMap);
			return mapping.findForward(CMSConstants.INIT_EXAM_SCHEDULE);
		}
		/** Add venue and Invigilators 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward assignVenueAndInvigilator(ActionMapping mapping,ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			ExamScheduleForm examScheduleForm=(ExamScheduleForm)form;
				examScheduleForm.clear();
				setDefaultValues(examScheduleForm);
			return mapping.findForward(CMSConstants.EXAM_SCHEDULE);
		}
		/**
		 * @param examScheduleForm
		 * @throws Exception
		 */
		private void setDefaultValues(ExamScheduleForm examScheduleForm)throws Exception{
			List<ExamScheduleTo> examschList=new ArrayList();
			ExamScheduleTo to=new ExamScheduleTo();
			to.setTeachersMap(examScheduleForm.getMainTeachersMap());
			to.setWorkLocationMap(examScheduleForm.getWorkLocationMap());
			to.setIsActive(true);
			examschList.add(to);
			examScheduleForm.setExamSchToList(examschList);
			examScheduleForm.setExamScheduleToListSize("1");
			examScheduleForm.setMode("add");
			checkSizeOfExamToList(examschList,examScheduleForm);
		}
		
		/** checking ToList size for  delete button if ToList size is more than one to display the delete button
		 * @param examschList
		 * @param examScheduleForm
		 * @throws Exception
		 */
		private void checkSizeOfExamToList(List<ExamScheduleTo> examschList, ExamScheduleForm examScheduleForm) throws Exception {
			
			if(examschList!=null  && !examschList.isEmpty()){
				Iterator<ExamScheduleTo> itr=examschList.iterator();
				int count=0;
				while (itr.hasNext()) {
					ExamScheduleTo to = itr.next();
					if(to.getIsActive()!=null && to.getIsActive())
						count++;
				}
				if(count>1)
					examScheduleForm.setExamSchTo(true);
				else
					examScheduleForm.setExamSchTo(false);
			}
				
		}
		
		/** add more venue and invigilators
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward addMoreVenueAndInvigilator(ActionMapping mapping,ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			ExamScheduleForm examScheduleForm=(ExamScheduleForm)form;
			ActionErrors errors = examScheduleForm.validate(mapping, request);
				int size=Integer.parseInt(examScheduleForm.getExamScheduleToListSize());
				List<ExamScheduleTo> examschList=examScheduleForm.getExamSchToList();
				Map<Integer,String> mainMap=examScheduleForm.getMainTeachersMap();
				int count=0;
				
					if(errors.isEmpty()){
							ListIterator<ExamScheduleTo> itr=examschList.listIterator();
							while (itr.hasNext()) {
								ExamScheduleTo to=itr.next();
								boolean isError=false;
								Map<Integer,String> venueMap=null;
								
								if((to.getWorkLocation()==null || to.getWorkLocation().isEmpty()) || (to.getVenue()==null || to.getVenue().isEmpty())
										|| (to.getPriority()==null || to.getPriority().isEmpty())){
									if((to.getWorkLocation()==null || to.getWorkLocation().isEmpty())){
										errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admin.examSchedule.error.worklocation"));
										isError=true;
									}
									if(to.getVenue()==null || to.getVenue().isEmpty()){
										errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admin.examSchedule.error.venue"));
										isError=true;
									}
									if(to.getPriority()==null || to.getPriority().isEmpty()){
										errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admin.examSchedule.error.priority"));
										isError=true;
									}
									
									if(isError){
										if((to.getWorkLocation()!=null && !to.getWorkLocation().isEmpty())){
											venueMap=CommonAjaxHandler.getInstance().getVenueByWorkLocation(Integer.parseInt(to.getWorkLocation()));
											to.setVenueMap(venueMap);
										}
										if(to.getTeachesFrom()!=null){
											String[] str = to.getTeachesFrom();
											Map<Integer,String> fromTeachersMap=new HashMap<Integer,String>();
											for (int x = 0; x < str.length; x++) {
												if (str[x] != null && str[x].length() > 0) {
													if(mainMap.containsKey(Integer.parseInt(str[x]))){
														String name=mainMap.get(Integer.parseInt(str[x]));
														fromTeachersMap.put(Integer.parseInt(str[x]),name);
													}
												}
											}
											fromTeachersMap=CommonUtil.sortMapByValue(fromTeachersMap);
											to.setTeachersMap(fromTeachersMap);
										}
										
										if(to.getTeachesTo()!=null){	
											String[] str1 = to.getTeachesTo();
											Map<Integer,String> toTeachersMap=new HashMap<Integer,String>();
											for (int x = 0; x < str1.length; x++) {
												if (str1[x] != null && str1[x].length() > 0) {
													if(mainMap.containsKey(Integer.parseInt(str1[x]))){
														String name=mainMap.get(Integer.parseInt(str1[x]));
														
														toTeachersMap.put(Integer.parseInt(str1[x]),name);
													}
												}
											}
											toTeachersMap=CommonUtil.sortMapByValue(toTeachersMap);
											to.setSelectedTeachersMap(toTeachersMap);
											}
										
									}
									
								}
								
								if(errors.isEmpty()){
									count=count+1;
									to.setVenueMap(CommonAjaxHandler.getInstance().getVenueByWorkLocation(Integer.parseInt(to.getWorkLocation())));
									if(to.getTeachesFrom()!=null){
									String[] str = to.getTeachesFrom();
									Map<Integer,String> fromTeachersMap=new HashMap<Integer,String>();
									for (int x = 0; x < str.length; x++) {
										if (str[x] != null && str[x].length() > 0) {
											if(mainMap.containsKey(Integer.parseInt(str[x]))){
												String name=mainMap.get(Integer.parseInt(str[x]));
												fromTeachersMap.put(Integer.parseInt(str[x]),name);
											}
										}
									}
									fromTeachersMap=CommonUtil.sortMapByValue(fromTeachersMap);
									to.setTeachersMap(fromTeachersMap);
								}
									
								if(to.getTeachesTo()!=null){	
									String[] str1 = to.getTeachesTo();
									Map<Integer,String> toTeachersMap=new HashMap<Integer,String>();
									for (int x = 0; x < str1.length; x++) {
										if (str1[x] != null && str1[x].length() > 0) {
											if(mainMap.containsKey(Integer.parseInt(str1[x]))){
												String name=mainMap.get(Integer.parseInt(str1[x]));
												
												toTeachersMap.put(Integer.parseInt(str1[x]),name);
											}
										}
									}
									toTeachersMap=CommonUtil.sortMapByValue(toTeachersMap);
									to.setSelectedTeachersMap(toTeachersMap);
									}
								
									to.setDeleteCount(count);
								}else{
									saveErrors(request, errors);
									if(examScheduleForm.getMode().equalsIgnoreCase("edit")){
										request.setAttribute("Operation", "edit");
									}
									checkSizeOfExamToList(examschList,examScheduleForm);
									return mapping.findForward(CMSConstants.EXAM_SCHEDULE);
								}
								
							}
							
							ExamScheduleTo to=new ExamScheduleTo();
							to.setTeachersMap(examScheduleForm.getMainTeachersMap());
							to.setWorkLocationMap(examScheduleForm.getWorkLocationMap());
							to.setDeleteCount(examschList.size()+1);
							to.setIsActive(true);
							examschList.add(to);
							size=size+1;
							examScheduleForm.setExamSchToList(examschList);
							examScheduleForm.setExamScheduleToListSize(String.valueOf(size));
							
							
					}else{
						
						ListIterator<ExamScheduleTo> itr=examschList.listIterator();
						while (itr.hasNext()) {
							ExamScheduleTo to=itr.next();
							Map<Integer,String> venueMap=null;
							
									if((to.getWorkLocation()!=null && !to.getWorkLocation().isEmpty())){
										venueMap=CommonAjaxHandler.getInstance().getVenueByWorkLocation(Integer.parseInt(to.getWorkLocation()));
										to.setVenueMap(venueMap);
									}
									if(to.getTeachesFrom()!=null){
										String[] str = to.getTeachesFrom();
										Map<Integer,String> fromTeachersMap=new HashMap<Integer,String>();
										for (int x = 0; x < str.length; x++) {
											if (str[x] != null && str[x].length() > 0) {
												if(mainMap.containsKey(Integer.parseInt(str[x]))){
													String name=mainMap.get(Integer.parseInt(str[x]));
													fromTeachersMap.put(Integer.parseInt(str[x]),name);
												}
											}
										}
										fromTeachersMap=CommonUtil.sortMapByValue(fromTeachersMap);
										to.setTeachersMap(fromTeachersMap);
									}
									
									if(to.getTeachesTo()!=null){	
										String[] str1 = to.getTeachesTo();
										Map<Integer,String> toTeachersMap=new HashMap<Integer,String>();
										for (int x = 0; x < str1.length; x++) {
											if (str1[x] != null && str1[x].length() > 0) {
												if(mainMap.containsKey(Integer.parseInt(str1[x]))){
													String name=mainMap.get(Integer.parseInt(str1[x]));
													
													toTeachersMap.put(Integer.parseInt(str1[x]),name);
												}
											}
										}
										toTeachersMap=CommonUtil.sortMapByValue(toTeachersMap);
										to.setSelectedTeachersMap(toTeachersMap);
										}
							}
						
						saveErrors(request, errors);
					}
					
					if(examScheduleForm.getMode().equalsIgnoreCase("edit")){
						request.setAttribute("Operation", "edit");
					}
					checkSizeOfExamToList(examschList,examScheduleForm);
			return mapping.findForward(CMSConstants.EXAM_SCHEDULE);
		}
		/** save venue and Invigilators Details
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward saveVenueAndInvigilator(ActionMapping mapping,ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			ExamScheduleForm examScheduleForm=(ExamScheduleForm)form;
			ActionMessages messages=new ActionMessages();
			ActionErrors errors = examScheduleForm.validate(mapping, request);
			setUserId(request, examScheduleForm);
			Map<Integer,String> mainMap=examScheduleForm.getMainTeachersMap();
			List<ExamScheduleTo> examschList=examScheduleForm.getExamSchToList();
			if(errors.isEmpty()){
					
					validateWorkLocationAndVenueAndPriority(examScheduleForm,errors,request,mainMap);
					if(errors.isEmpty()){	
						try {
							if(examschList!=null){
								boolean isSaved=ExamScheduleHandler.getInstance().saveVenueDetails(examschList,examScheduleForm,request);
								if(isSaved){
									examScheduleForm.setExamSchToList(null);
									ActionMessage message = new ActionMessage("knowledgepro.admin.sap.exam.schedule.sava.success");
									messages.add("messages", message);
									saveMessages(request, messages);
									
									if((examScheduleForm.getMonth()!=null && !examScheduleForm.getMonth().isEmpty()) 
											&& (examScheduleForm.getYear()!=null && !examScheduleForm.getYear().isEmpty())){
										List<ExamScheduleTo> toList=ExamScheduleHandler.getInstance().getVenueAndInvigilatorDetails(examScheduleForm);
										if(toList!=null && !toList.isEmpty()){
											examScheduleForm.setSearchExamSchToList(toList);
										}
									}
									
								}else{
									ActionMessage message = new ActionMessage("knowledgepro.admin.sap.exam.schedule.sava.failed");
									messages.add("messages", message);
									saveMessages(request, messages);
								}
							}
						}catch(DuplicateException e) {
							if(request.getAttribute("isVenue")!=null && request.getAttribute("isVenue").equals("true")){
								errors.add(CMSConstants.ERRORS,new ActionError("knowledgepro.sap.duplicate.worklocation.venue"));
							}else if(request.getAttribute("isPriority")!=null && request.getAttribute("isPriority").equals("true")){
								errors.add(CMSConstants.ERRORS,new ActionError("knowledgepro.sap.duplicate.prioritynumber"));
							}else if(request.getAttribute("isSession")!=null && request.getAttribute("isSession").equals("true")){
								errors.add(CMSConstants.ERRORS,new ActionError("knowledgepro.sap.duplicate.session"));
							}else if(request.getAttribute("isSessionOrder")!=null && request.getAttribute("isSessionOrder").equals("true")){
								errors.add(CMSConstants.ERRORS,new ActionError("knowledgepro.sap.duplicate.sessionorder"));
							}
							saveErrors(request,errors);
							return mapping.findForward(CMSConstants.EXAM_SCHEDULE);
						}catch (Exception exception) {
							String msg = super.handleApplicationException(exception);
							examScheduleForm.setErrorMessage(msg);
							examScheduleForm.setErrorStack(exception.getMessage());
							return mapping.findForward(CMSConstants.ERROR_PAGE);
						}
					}else{
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.EXAM_SCHEDULE);
					}
			}else{

				
				ListIterator<ExamScheduleTo> itr=examschList.listIterator();
				while (itr.hasNext()) {
					ExamScheduleTo to=itr.next();
					Map<Integer,String> venueMap=null;
					
							if((to.getWorkLocation()!=null && !to.getWorkLocation().isEmpty())){
								venueMap=CommonAjaxHandler.getInstance().getVenueByWorkLocation(Integer.parseInt(to.getWorkLocation()));
								to.setVenueMap(venueMap);
							}
							if(to.getTeachesFrom()!=null){
								String[] str = to.getTeachesFrom();
								Map<Integer,String> fromTeachersMap=new HashMap<Integer,String>();
								for (int x = 0; x < str.length; x++) {
									if (str[x] != null && str[x].length() > 0) {
										if(mainMap.containsKey(Integer.parseInt(str[x]))){
											String name=mainMap.get(Integer.parseInt(str[x]));
											fromTeachersMap.put(Integer.parseInt(str[x]),name);
										}
									}
								}
								fromTeachersMap=CommonUtil.sortMapByValue(fromTeachersMap);
								to.setTeachersMap(fromTeachersMap);
							}
							
							if(to.getTeachesTo()!=null){	
								String[] str1 = to.getTeachesTo();
								Map<Integer,String> toTeachersMap=new HashMap<Integer,String>();
								for (int x = 0; x < str1.length; x++) {
									if (str1[x] != null && str1[x].length() > 0) {
										if(mainMap.containsKey(Integer.parseInt(str1[x]))){
											String name=mainMap.get(Integer.parseInt(str1[x]));
											
											toTeachersMap.put(Integer.parseInt(str1[x]),name);
										}
									}
								}
								toTeachersMap=CommonUtil.sortMapByValue(toTeachersMap);
								to.setSelectedTeachersMap(toTeachersMap);
								}
					}
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.EXAM_SCHEDULE);
			}
			
			return mapping.findForward(CMSConstants.INIT_EXAM_SCHEDULE);
		}
		
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward searchVenueAndInvigilatorDetails(ActionMapping mapping,ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			ExamScheduleForm examScheduleForm=(ExamScheduleForm)form;
			ActionErrors errors = new ActionErrors();
			
					try {
						
						List<ExamScheduleTo> toList=ExamScheduleHandler.getInstance().getVenueAndInvigilatorDetails(examScheduleForm);
						if(toList!=null && !toList.isEmpty()){
							examScheduleForm.setSearchExamSchToList(toList);
						}else{
							examScheduleForm.clear();
							errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.norecords"));
							saveErrors(request, errors);
						}
						}catch (Exception exception) {
							String msg = super.handleApplicationException(exception);
							examScheduleForm.setErrorMessage(msg);
							examScheduleForm.setErrorStack(exception.getMessage());
							return mapping.findForward(CMSConstants.ERROR_PAGE);
						}
			
			
			return mapping.findForward(CMSConstants.INIT_EXAM_SCHEDULE);
		}
		
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward editExamVenueAndInvigilatorDetails(ActionMapping mapping,ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			ExamScheduleForm examScheduleForm=(ExamScheduleForm)form;
			ActionErrors errors = new ActionErrors();
			
			if(errors.isEmpty()){
					try {
						
						List<ExamScheduleTo> toList=ExamScheduleHandler.getInstance().editVenueAndInvigilatorDetails(examScheduleForm);
						checkSizeOfExamToList(toList,examScheduleForm);
						if(toList!=null && !toList.isEmpty()){
							examScheduleForm.setExamSchToList(toList);
							request.setAttribute("Operation", "edit");
							examScheduleForm.setMode("edit");
							
						}else{
							errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.norecords"));
							saveErrors(request, errors);
							return mapping.findForward(CMSConstants.INIT_EXAM_SCHEDULE);
						}
						}catch (Exception exception) {
							String msg = super.handleApplicationException(exception);
							examScheduleForm.setErrorMessage(msg);
							examScheduleForm.setErrorStack(exception.getMessage());
							return mapping.findForward(CMSConstants.ERROR_PAGE);
						}
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_EXAM_SCHEDULE);
			}
			
			return mapping.findForward(CMSConstants.EXAM_SCHEDULE);
		}
		
		
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward updateVenueAndInvigilatorDetails(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			ExamScheduleForm examScheduleForm=(ExamScheduleForm)form;
			ActionMessages messages=new ActionMessages();
			ActionErrors errors = examScheduleForm.validate(mapping, request);
			Map<Integer,String> mainMap=examScheduleForm.getMainTeachersMap();
			List<ExamScheduleTo> examschList=examScheduleForm.getExamSchToList();
			setUserId(request, examScheduleForm);
			if(errors.isEmpty()){
					
					validateWorkLocationAndVenueAndPriority(examScheduleForm,errors,request,mainMap);
					if(errors.isEmpty()){
						try {
							if(examschList!=null){
								boolean isUpdate=ExamScheduleHandler.getInstance().updateVenueDetails(examschList,examScheduleForm,request);
								if(isUpdate){
									examScheduleForm.setExamSchToList(null);
									ActionMessage message = new ActionMessage("knowledgepro.admin.sap.exam.schedule.update.success");
									messages.add("messages", message);
									saveMessages(request, messages);
									if((examScheduleForm.getMonth()!=null && !examScheduleForm.getMonth().isEmpty()) 
											&& (examScheduleForm.getYear()!=null && !examScheduleForm.getYear().isEmpty())){
										List<ExamScheduleTo> toList=ExamScheduleHandler.getInstance().getVenueAndInvigilatorDetails(examScheduleForm);
										if(toList!=null && !toList.isEmpty()){
											examScheduleForm.setSearchExamSchToList(toList);
										}
									}
									
								}else{
									ActionMessage message = new ActionMessage("knowledgepro.admin.sap.exam.schedule.update.failed");
									messages.add("messages", message);
									saveMessages(request, messages);
								}
							}
						}catch(DuplicateException e) {
							if(request.getAttribute("isVenueInUpdateMode")!=null && request.getAttribute("isVenueInUpdateMode").equals("true")){
								errors.add(CMSConstants.ERRORS,new ActionError("knowledgepro.sap.duplicate.worklocation.venue"));
							}else if(request.getAttribute("isPriorityInUpdate")!=null && request.getAttribute("isPriorityInUpdate").equals("true")){
								errors.add(CMSConstants.ERRORS,new ActionError("knowledgepro.sap.duplicate.prioritynumber"));
							}else if(request.getAttribute("isSession")!=null && request.getAttribute("isSession").equals("true")){
								errors.add(CMSConstants.ERRORS,new ActionError("knowledgepro.sap.duplicate.session"));
							}else if(request.getAttribute("isSessionOrder")!=null && request.getAttribute("isSessionOrder").equals("true")){
								errors.add(CMSConstants.ERRORS,new ActionError("knowledgepro.sap.duplicate.sessionorder"));
							}
							saveErrors(request,errors);
							request.setAttribute("Operation", "edit");
							return mapping.findForward(CMSConstants.EXAM_SCHEDULE);
						}catch (Exception exception) {
							String msg = super.handleApplicationException(exception);
							examScheduleForm.setErrorMessage(msg);
							examScheduleForm.setErrorStack(exception.getMessage());
							return mapping.findForward(CMSConstants.ERROR_PAGE);
						}
					}else{
						request.setAttribute("Operation", "edit");
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.EXAM_SCHEDULE);
					}
			}else{
				
				ListIterator<ExamScheduleTo> itr=examschList.listIterator();
				while (itr.hasNext()) {
					ExamScheduleTo to=itr.next();
					Map<Integer,String> venueMap=null;
					
							if((to.getWorkLocation()!=null && !to.getWorkLocation().isEmpty())){
								venueMap=CommonAjaxHandler.getInstance().getVenueByWorkLocation(Integer.parseInt(to.getWorkLocation()));
								to.setVenueMap(venueMap);
							}
							if(to.getTeachesFrom()!=null){
								String[] str = to.getTeachesFrom();
								Map<Integer,String> fromTeachersMap=new HashMap<Integer,String>();
								for (int x = 0; x < str.length; x++) {
									if (str[x] != null && str[x].length() > 0) {
										if(mainMap.containsKey(Integer.parseInt(str[x]))){
											String name=mainMap.get(Integer.parseInt(str[x]));
											fromTeachersMap.put(Integer.parseInt(str[x]),name);
										}
									}
								}
								fromTeachersMap=CommonUtil.sortMapByValue(fromTeachersMap);
								to.setTeachersMap(fromTeachersMap);
							}
							
							if(to.getTeachesTo()!=null){	
								String[] str1 = to.getTeachesTo();
								Map<Integer,String> toTeachersMap=new HashMap<Integer,String>();
								for (int x = 0; x < str1.length; x++) {
									if (str1[x] != null && str1[x].length() > 0) {
										if(mainMap.containsKey(Integer.parseInt(str1[x]))){
											String name=mainMap.get(Integer.parseInt(str1[x]));
											
											toTeachersMap.put(Integer.parseInt(str1[x]),name);
										}
									}
								}
								toTeachersMap=CommonUtil.sortMapByValue(toTeachersMap);
								to.setSelectedTeachersMap(toTeachersMap);
								}
					}
				
				
				request.setAttribute("Operation", "edit");
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.EXAM_SCHEDULE);
			}
			
			return mapping.findForward(CMSConstants.INIT_EXAM_SCHEDULE);
		}
		
		
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward deleteExamVenueAndInvigilatorDetails(ActionMapping mapping,ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			ExamScheduleForm examScheduleForm=(ExamScheduleForm)form;
			ActionMessages messages=new ActionMessages();
			
					try {
						
					boolean isDeleted=ExamScheduleHandler.getInstance().deleteVenueAndInvigilatorDetails(examScheduleForm);
						
							if(isDeleted){
								examScheduleForm.setExamSchToList(null);
								ActionMessage message = new ActionMessage("knowledgepro.admin.sap.exam.schedule.delete.success");
								messages.add("messages", message);
								saveMessages(request, messages);
								List<ExamScheduleTo> toList=ExamScheduleHandler.getInstance().getVenueAndInvigilatorDetails(examScheduleForm);
									examScheduleForm.setSearchExamSchToList(toList);
								
							}else{
								ActionMessage message = new ActionMessage("knowledgepro.admin.sap.exam.schedule.delete.failed");
								messages.add("messages", message);
								saveMessages(request, messages);
							}
						}catch (Exception exception) {
							String msg = super.handleApplicationException(exception);
							examScheduleForm.setErrorMessage(msg);
							examScheduleForm.setErrorStack(exception.getMessage());
							return mapping.findForward(CMSConstants.ERROR_PAGE);
						}
			
			return mapping.findForward(CMSConstants.INIT_EXAM_SCHEDULE);
		}
		
		
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward removeToFromToList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			log.info("Entered TimeTableForClassAction - getPeriods");
			
			ExamScheduleForm examScheduleForm=(ExamScheduleForm)form;
			List<ExamScheduleTo> examschList=examScheduleForm.getExamSchToList();
			List<ExamScheduleTo> newExamschList=ExamScheduleHandler.getInstance().removeSelectedToFromTolist(examschList,examScheduleForm);
			
			if(examScheduleForm.getMode().equalsIgnoreCase("edit")){
				request.setAttribute("Operation", "edit");
			}
			checkSizeOfExamToList(newExamschList,examScheduleForm);
			log.info("Entered TimeTableForClassAction - getPeriods");
			return mapping.findForward(CMSConstants.EXAM_SCHEDULE);
		}
		
		/**
		 * @param examScheduleForm
		 * @param errors
		 * @param request
		 * @throws Exception
		 */
		private void validateWorkLocationAndVenueAndPriority(ExamScheduleForm examScheduleForm, ActionErrors errors,
				HttpServletRequest request,Map<Integer,String> mainMap)  throws Exception{
			List<ExamScheduleTo> examschList=examScheduleForm.getExamSchToList();
			ListIterator<ExamScheduleTo> itr=examschList.listIterator();
			while (itr.hasNext()) {
			ExamScheduleTo to=itr.next();
			if((to.getWorkLocation()==null || to.getWorkLocation().isEmpty()) || (to.getVenue()==null || to.getVenue().isEmpty())
					|| (to.getPriority()==null || to.getPriority().isEmpty())){
				boolean isError=false;
				Map<Integer,String> venueMap=null;
					if((to.getWorkLocation()==null || to.getWorkLocation().isEmpty())){
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admin.examSchedule.error.worklocation"));
						isError=true;
					}
					if(to.getVenue()==null || to.getVenue().isEmpty()){
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admin.examSchedule.error.venue"));
						isError=true;
					}
					if(to.getPriority()==null || to.getPriority().isEmpty()){
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admin.examSchedule.error.priority"));
						isError=true;
						}
					if(isError){
						if((to.getWorkLocation()!=null && !to.getWorkLocation().isEmpty())){
							venueMap=CommonAjaxHandler.getInstance().getVenueByWorkLocation(Integer.parseInt(to.getWorkLocation()));
							to.setVenueMap(venueMap);
						}
						if(to.getTeachesFrom()!=null){
							String[] str = to.getTeachesFrom();
							Map<Integer,String> fromTeachersMap=new HashMap<Integer,String>();
							for (int x = 0; x < str.length; x++) {
								if (str[x] != null && str[x].length() > 0) {
									if(mainMap.containsKey(Integer.parseInt(str[x]))){
										String name=mainMap.get(Integer.parseInt(str[x]));
										fromTeachersMap.put(Integer.parseInt(str[x]),name);
									}
								}
							}
							fromTeachersMap=CommonUtil.sortMapByValue(fromTeachersMap);
							to.setTeachersMap(fromTeachersMap);
						}
						
						if(to.getTeachesTo()!=null){	
							String[] str1 = to.getTeachesTo();
							Map<Integer,String> toTeachersMap=new HashMap<Integer,String>();
							for (int x = 0; x < str1.length; x++) {
								if (str1[x] != null && str1[x].length() > 0) {
									if(mainMap.containsKey(Integer.parseInt(str1[x]))){
										String name=mainMap.get(Integer.parseInt(str1[x]));
										
										toTeachersMap.put(Integer.parseInt(str1[x]),name);
									}
								}
							}
							toTeachersMap=CommonUtil.sortMapByValue(toTeachersMap);
							to.setSelectedTeachersMap(toTeachersMap);
							}
					}
						saveErrors(request, errors);
				}
			}
		}


}
