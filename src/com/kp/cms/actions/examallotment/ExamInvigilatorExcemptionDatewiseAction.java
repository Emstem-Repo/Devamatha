package com.kp.cms.actions.examallotment;
	

	import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
	import java.util.Calendar;
	import java.util.Date;
import java.util.GregorianCalendar;
	import java.util.Iterator;
	import java.util.List;
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
import com.kp.cms.bo.examallotment.ExamInviligatorExemptionDatewise;
	import com.kp.cms.constants.CMSConstants;
	import com.kp.cms.exceptions.ApplicationException;
	import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.DepartmentEntryForm;
import com.kp.cms.forms.employee.EmployeeInfoEditForm;
	import com.kp.cms.forms.exam.ExamValuationRemuPaymentForm;
import com.kp.cms.forms.examallotment.ExamInvigilatorExcemptionDatewiseForm;
	import com.kp.cms.forms.examallotment.ExamInvigilatorExcemptionDatewiseForm;
import com.kp.cms.handlers.admin.DepartmentEntryHandler;
import com.kp.cms.handlers.examallotment.ExamInvigilatorExcemptionDatewiseHandler;
	import com.kp.cms.handlers.examallotment.InvigilatorsForExamHandler;
	import com.kp.cms.to.exam.ExamValuationRemuPaymentTo;
import com.kp.cms.to.examallotment.ExamInvigilatorExcemptionDatewiseTO;
import com.kp.cms.to.examallotment.ExamInvigilatorExcemptionDatewiseTO;
import com.kp.cms.utilities.CommonUtil;

	public class ExamInvigilatorExcemptionDatewiseAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(InvigilatorsForExamAction.class);
	ExamInvigilatorExcemptionDatewiseHandler handler=ExamInvigilatorExcemptionDatewiseHandler.getInstance();

		
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward initInvigilatorsDatewise(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			ExamInvigilatorExcemptionDatewiseForm invigilatorsForExamForm=(ExamInvigilatorExcemptionDatewiseForm)form;
			reset(invigilatorsForExamForm);
			setRequiredDataToForm(invigilatorsForExamForm);
			return mapping.findForward(CMSConstants.INIT_INVIGILATORS_DATEWISE_EXCEMPTION);
		}

		private void reset(ExamInvigilatorExcemptionDatewiseForm invigilatorsForExamForm) throws Exception{
			invigilatorsForExamForm.setDeptId(null);
			invigilatorsForExamForm.setLocationId(null);
			invigilatorsForExamForm.setList(null);
			invigilatorsForExamForm.setExamId(null);
			invigilatorsForExamForm.setFromDate(null);
			invigilatorsForExamForm.setToDate(null);
			invigilatorsForExamForm.setSession(null);
			invigilatorsForExamForm.setDeanaryId(null);
			invigilatorsForExamForm.setIsAdd("1");
		}

		private void setRequiredDataToForm(ExamInvigilatorExcemptionDatewiseForm invigilatorsForExamForm) throws Exception{
			//set exammap,deanarymap and department map
			handler.getAllMaps(invigilatorsForExamForm);
			
		}
		/**
		 * get invigilators list
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward getInvigilatorsList(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			ExamInvigilatorExcemptionDatewiseForm invigilatorsForExamForm=(ExamInvigilatorExcemptionDatewiseForm)form;
			invigilatorsForExamForm.setList(null);
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = invigilatorsForExamForm.validate(mapping, request);
			validateMandatory(invigilatorsForExamForm,errors);
			try {
				if(!errors.isEmpty() && errors!=null){
					saveErrors(request, errors);
				}else{
					//List<ExamInvigilatorExcemptionDatewiseTO> listOfTeachersExemptedAlready=handler.getInvListAlreadyExempted(invigilatorsForExamForm);
					List<ExamInvigilatorExcemptionDatewiseTO> list=handler.getInvigilatorsList(invigilatorsForExamForm);
					if(list!=null && !list.isEmpty()){
						invigilatorsForExamForm.setList(list);
						return mapping .findForward(CMSConstants.INVIGILATORS_DATEWISE_EXCEMPTION_LIST);
					}else{
						errors.add("error", new ActionError("knowledgepro.norecords"));
						saveErrors(request, errors);
					}
				}
			}catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				invigilatorsForExamForm.setErrorMessage(msg);
				invigilatorsForExamForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward(CMSConstants.INIT_INVIGILATORS_DATEWISE_EXCEMPTION);
		}
		
		public ActionForward getInvigilatorsListExempted(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			ExamInvigilatorExcemptionDatewiseForm invigilatorsForExamForm=(ExamInvigilatorExcemptionDatewiseForm)form;
			invigilatorsForExamForm.setList(null);
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = invigilatorsForExamForm.validate(mapping, request);
			try {
				if(!errors.isEmpty() && errors!=null){
					saveErrors(request, errors);
				}else{
					List<ExamInvigilatorExcemptionDatewiseTO> listOfTeachersExemptedAlready=handler.getInvListAlreadyExemptedEdit(invigilatorsForExamForm);
					if(listOfTeachersExemptedAlready!=null && !listOfTeachersExemptedAlready.isEmpty()){
						invigilatorsForExamForm.setAlreadyExemptedlist(listOfTeachersExemptedAlready);
						return mapping .findForward(CMSConstants.INV_DATEWISE_ALREADYEXCEMPTED_LIST);
					}else{
						errors.add("error", new ActionError("knowledgepro.norecords"));
						saveErrors(request, errors);
					}
				}
			}catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				invigilatorsForExamForm.setErrorMessage(msg);
				invigilatorsForExamForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward(CMSConstants.INIT_INVIGILATORS_DATEWISE_EXCEMPTION);
		}
		/**
		 * update the examId and users in 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward addInvigilators(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			ExamInvigilatorExcemptionDatewiseForm invigilatorsForExamForm=(ExamInvigilatorExcemptionDatewiseForm)form;
			ActionMessages messages = new ActionMessages();
			ActionErrors errors=invigilatorsForExamForm.validate(mapping, request);
			
			try{
				
				List<Date> newDates=getDates(invigilatorsForExamForm.getFromDate(), invigilatorsForExamForm.getToDate());
					List<ExamInvigilatorExcemptionDatewiseTO> list=new ArrayList<ExamInvigilatorExcemptionDatewiseTO>();
					List<ExamInvigilatorExcemptionDatewiseTO> invigilatorsForExamTos=invigilatorsForExamForm.getList();
					Iterator<ExamInvigilatorExcemptionDatewiseTO> iterator=invigilatorsForExamTos.iterator();
					while (iterator.hasNext()) {
						ExamInvigilatorExcemptionDatewiseTO invigilatorsForExamTo = (ExamInvigilatorExcemptionDatewiseTO) iterator.next();
						if(invigilatorsForExamTo.getChecked()!=null && !invigilatorsForExamTo.getChecked().isEmpty() && invigilatorsForExamTo.getChecked().equalsIgnoreCase("on")){
							list.add(invigilatorsForExamTo);
							invigilatorsForExamTo.setChecked(null);
						}
					}
					setUserId(request, invigilatorsForExamForm); 
					boolean flag=false;
					if(list!=null && !list.isEmpty()){
						if(newDates!=null && !newDates.isEmpty()){
							Iterator<Date> itr=newDates.iterator();
									while (itr.hasNext()) {
										Date dt = (Date) itr.next();
										Map<Integer,ExamInviligatorExemptionDatewise> listOfTeachersExemptedAlready=handler.getInvListAlreadyExempted(invigilatorsForExamForm, dt);
										flag=handler.addInvigilators(list,invigilatorsForExamForm,dt,listOfTeachersExemptedAlready);
									}
						
						}
						if(flag){
							reset(invigilatorsForExamForm);
							Calendar calendar = Calendar.getInstance();   // This gets the current date and time.
							int year=calendar.get(Calendar.YEAR); 
							invigilatorsForExamForm.setAcademicYear(String.valueOf(year));
							ActionMessage message = new ActionMessage("knowledgepro.exam.allotment.invigilators.add.success");
							messages.add("messages", message);
							saveMessages(request, messages);
							return mapping.findForward(CMSConstants.INIT_INVIGILATORS_DATEWISE_EXCEMPTION);
						}else{
							errors.add("error", new ActionError("knowledgepro.exam.allotment.invigilators.Datewise.add.fail"));
							saveErrors(request, errors);
						}
					}else{
						errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.allotment.select.records.add"));
						saveErrors(request, errors);
					}
			}catch (Exception e) {
				e.printStackTrace();
				if (e instanceof BusinessException) {
					String msgKey = super.handleBusinessException(e);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add("messages", message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					invigilatorsForExamForm.setErrorMessage(msg);
					invigilatorsForExamForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}
			return mapping .findForward(CMSConstants.INVIGILATORS_DATEWISE_EXCEMPTION_LIST);
		}
		
		@SuppressWarnings({ "unchecked", "null" })
		private static List<Date> getDatesBetweenFromToDates(final Date date1, final Date date2) {
			List<Date> dates = new ArrayList<Date>();
		    Calendar c1 = new GregorianCalendar();
		    c1.setTime(date1);
		    Calendar c2 = new GregorianCalendar();
		    c2.setTime(date2);
		    int a = c1.get(Calendar.DATE);
		    int b = c2.get(Calendar.DATE);
		    while ((c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR)) || (c1.get(Calendar.MONTH) != c2.get(Calendar.MONTH)) || (c1.get(Calendar.DATE) != c2.get(Calendar.DATE))) {
		        c1.add(Calendar.DATE, 1);
		        dates.add(new Date(c1.getTimeInMillis()));
		    }
		    return dates;
		}
		
		private static List<Date> getDates(String date1, String date2) {
			List<Date> dates = new ArrayList<Date>();
	
			String str_date =date1;
			String end_date =date2;
	
			DateFormat formatter ; 
	
			formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date startDate;
			try {
				startDate = (Date)formatter.parse(str_date);
			
				Date  endDate = (Date)formatter.parse(end_date);
				long interval = 24*1000 * 60 * 60; // 1 hour in millis
				long endTime =endDate.getTime() ; // create your endtime here, possibly using Calendar or Date
				long curTime = startDate.getTime();
				while (curTime <= endTime) {
				    dates.add(new Date(curTime));
				    
				    curTime += interval;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return dates;
		}
		/**
		 * get invigilators list
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward searchTheInvigilators(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			ExamInvigilatorExcemptionDatewiseForm invigilatorsForExamForm=(ExamInvigilatorExcemptionDatewiseForm)form;
			invigilatorsForExamForm.setList(null);
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = invigilatorsForExamForm.validate(mapping, request);
			validateMandatory(invigilatorsForExamForm,errors);
			try {
				if(!errors.isEmpty() && errors!=null){
					saveErrors(request, errors);
				}else{
					List<ExamInvigilatorExcemptionDatewiseTO> list=handler.searchTheInvigilators(invigilatorsForExamForm);
					if(list!=null && !list.isEmpty()){
						invigilatorsForExamForm.setList(list);
						return mapping .findForward(CMSConstants.INV_DATEWISE_ALREADYEXCEMPTED_LIST);
					}else{
						errors.add("error", new ActionError("knowledgepro.norecords"));
						saveErrors(request, errors);
					}
				}
			}catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				invigilatorsForExamForm.setErrorMessage(msg);
				invigilatorsForExamForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward(CMSConstants.INIT_INVIGILATORS_DATEWISE_EXCEMPTION);
		}
		/**
		 * update the examId and users in 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward updateInvigilators(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			ExamInvigilatorExcemptionDatewiseForm invigilatorsForExamForm=(ExamInvigilatorExcemptionDatewiseForm)form;
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = new ActionErrors();
			try{
					List<Integer> list=new ArrayList<Integer>();
					List<ExamInvigilatorExcemptionDatewiseTO> invigilatorsForExamTos=invigilatorsForExamForm.getList();
					Iterator<ExamInvigilatorExcemptionDatewiseTO> iterator=invigilatorsForExamTos.iterator();
					while (iterator.hasNext()) {
						ExamInvigilatorExcemptionDatewiseTO invigilatorsForExamTo = (ExamInvigilatorExcemptionDatewiseTO) iterator.next();
						if(invigilatorsForExamTo.getChecked()==null){
							list.add(invigilatorsForExamTo.getId());
						}
					}
					setUserId(request, invigilatorsForExamForm); 
					if(list!=null && !list.isEmpty()){
						boolean flag=handler.updateInvigilators(list);
						if(flag){
							reset(invigilatorsForExamForm);
							Calendar calendar = Calendar.getInstance();   // This gets the current date and time.
							int year=calendar.get(Calendar.YEAR); 
							invigilatorsForExamForm.setAcademicYear(String.valueOf(year));
							ActionMessage message = new ActionMessage("knowledgepro.exam.icmr.updated");
							messages.add("messages", message);
							saveMessages(request, messages);
							return mapping.findForward(CMSConstants.INIT_INVIGILATORS_DATEWISE_EXCEMPTION);
						}else{
							errors.add("error", new ActionError("knowledgepro.phd.Setting.update.failed"));
							saveErrors(request, errors);
						}
					}else{
						errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.allotment.deselect.records.update"));
						saveErrors(request, errors);
					}
			}catch (Exception e) {
				e.printStackTrace();
				if (e instanceof BusinessException) {
					String msgKey = super.handleBusinessException(e);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add("messages", message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					invigilatorsForExamForm.setErrorMessage(msg);
					invigilatorsForExamForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}
			return mapping .findForward(CMSConstants.INV_DATEWISE_ALREADYEXCEMPTED_LIST);
		}
		
		public ActionForward deleteEntry(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			ExamInvigilatorExcemptionDatewiseForm invigilatorsForExamForm=(ExamInvigilatorExcemptionDatewiseForm)form;
			setUserId(request, invigilatorsForExamForm);
			ActionErrors errors = new ActionErrors();
			ActionMessages messages = new ActionMessages();
			boolean isDeleted=false;
			try{
				if(invigilatorsForExamForm.getSelectedId()!=null){
						int exemptId=Integer.parseInt(invigilatorsForExamForm.getSelectedId());
						isDeleted=handler.deleteExmptEntry(exemptId,false,invigilatorsForExamForm);
				}
			}catch (Exception e) {
				if (e instanceof BusinessException) {
					String msgKey = super.handleBusinessException(e);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add("messages", message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					invigilatorsForExamForm.setErrorMessage(msg);
					invigilatorsForExamForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}
			if(isDeleted){
				List<ExamInvigilatorExcemptionDatewiseTO> list=handler.searchTheInvigilators(invigilatorsForExamForm);
				if(list!=null && !list.isEmpty()){
					invigilatorsForExamForm.setList(list);
				
				
				
			/*	List<ExamInvigilatorExcemptionDatewiseTO> list=handler.getInvigilatorsList(invigilatorsForExamForm);
				if(list!=null && !list.isEmpty()){
					invigilatorsForExamForm.setList(list);*/
				}
				ActionMessage message = new ActionMessage("knowledgepro.admin.Datewise.Exemption.deletesuccess");
				messages.add("messages",message);
				saveMessages(request, messages);
				invigilatorsForExamForm.reset(mapping, request);
				
			}else{
				errors.add("error",new ActionError("knowledgepro.admin.Datewise.Exemption.deletefailure"));
				saveErrors(request, errors);
			}
			return mapping.findForward(CMSConstants.INV_DATEWISE_ALREADYEXCEMPTED_LIST);
		}
		
		 public void validateMandatory(ExamInvigilatorExcemptionDatewiseForm invForm, ActionErrors errors){
			
		 if(invForm.getIsAdd()!=null && !invForm.getIsAdd().isEmpty()){
			if(invForm.getIsAdd().equalsIgnoreCase("1"))
			{
				if(invForm.getAcademicYear()==null || invForm.getAcademicYear().isEmpty())
					{			
						if(errors.get(CMSConstants.FEE_ACADEMICYEAR_REQUIRED)!=null && !errors.get(CMSConstants.FEE_ACADEMICYEAR_REQUIRED).hasNext()){									
							errors.add(CMSConstants.FEE_ACADEMICYEAR_REQUIRED,new ActionError(CMSConstants.FEE_ACADEMICYEAR_REQUIRED));
						}
					}	
				if(invForm.getExamId()==null || invForm.getExamId().isEmpty())
				{			
					if(errors.get(CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED).hasNext()){									
						errors.add(CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED,new ActionError(CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED));
					}
				}	
				if(invForm.getFromDate()==null || invForm.getFromDate().isEmpty())
				{			
					if(errors.get(CMSConstants.INVIGILATORS_DATEWISE_FROM_DATE_REQUIRED_LIST)!=null && !errors.get(CMSConstants.INVIGILATORS_DATEWISE_FROM_DATE_REQUIRED_LIST).hasNext()){									
						errors.add(CMSConstants.INVIGILATORS_DATEWISE_FROM_DATE_REQUIRED_LIST,new ActionError(CMSConstants.INVIGILATORS_DATEWISE_FROM_DATE_REQUIRED_LIST));
					}
				}
				if(invForm.getToDate()==null || invForm.getToDate().isEmpty())
				{			
					if(errors.get(CMSConstants.INVIGILATORS_DATEWISE_TO_DATE_REQUIRED_LIST)!=null && !errors.get(CMSConstants.INVIGILATORS_DATEWISE_TO_DATE_REQUIRED_LIST).hasNext()){									
						errors.add(CMSConstants.INVIGILATORS_DATEWISE_TO_DATE_REQUIRED_LIST,new ActionError(CMSConstants.INVIGILATORS_DATEWISE_TO_DATE_REQUIRED_LIST));
					}
				}
				if(invForm.getSession()==null || invForm.getSession().isEmpty())
				{			
					if(errors.get(CMSConstants.INVIGILATORS_DATEWISE_SESSION_REQUIRED)!=null && !errors.get(CMSConstants.INVIGILATORS_DATEWISE_SESSION_REQUIRED).hasNext()){									
						errors.add(CMSConstants.INVIGILATORS_DATEWISE_SESSION_REQUIRED,new ActionError(CMSConstants.INVIGILATORS_DATEWISE_SESSION_REQUIRED));
					}
				}
				if(invForm.getLocationId()==null || invForm.getLocationId().isEmpty())
				{			
					if(errors.get(CMSConstants.INVIGILATORS_DATEWISE_CAMPUS_REQUIRED)!=null && !errors.get(CMSConstants.INVIGILATORS_DATEWISE_CAMPUS_REQUIRED).hasNext()){									
						errors.add(CMSConstants.INVIGILATORS_DATEWISE_CAMPUS_REQUIRED,new ActionError(CMSConstants.INVIGILATORS_DATEWISE_CAMPUS_REQUIRED));
					}
				}
				
			}else
			{
				if(invForm.getAcademicYear()==null || invForm.getAcademicYear().isEmpty())
				{			
					if(errors.get(CMSConstants.FEE_ACADEMICYEAR_REQUIRED)!=null && !errors.get(CMSConstants.FEE_ACADEMICYEAR_REQUIRED).hasNext()){									
						errors.add(CMSConstants.FEE_ACADEMICYEAR_REQUIRED,new ActionError(CMSConstants.FEE_ACADEMICYEAR_REQUIRED));
					}
				}	
			
			if(invForm.getExamId()==null || invForm.getExamId().isEmpty())
			{			
				if(errors.get(CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED).hasNext()){									
					errors.add(CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED,new ActionError(CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED));
				}
			}	
			
			if(invForm.getSession()==null && invForm.getSession().isEmpty())
			{			
				if(errors.get(CMSConstants.INVIGILATORS_DATEWISE_SESSION_REQUIRED)!=null && !errors.get(CMSConstants.INVIGILATORS_DATEWISE_SESSION_REQUIRED).hasNext()){									
					errors.add(CMSConstants.INVIGILATORS_DATEWISE_SESSION_REQUIRED,new ActionError(CMSConstants.INVIGILATORS_DATEWISE_SESSION_REQUIRED));
				}
			}
			if(invForm.getLocationId()==null || invForm.getLocationId().isEmpty())
			{			
				if(errors.get(CMSConstants.INVIGILATORS_DATEWISE_CAMPUS_REQUIRED)!=null && !errors.get(CMSConstants.INVIGILATORS_DATEWISE_CAMPUS_REQUIRED).hasNext()){									
					errors.add(CMSConstants.INVIGILATORS_DATEWISE_CAMPUS_REQUIRED,new ActionError(CMSConstants.INVIGILATORS_DATEWISE_CAMPUS_REQUIRED));
				}
			}
			}
		}else
		{
			if(invForm.getIsAdd()==null || invForm.getIsAdd().isEmpty())
			{			
				if(errors.get(CMSConstants.INVIGILATORS_DATEWISE_ISADD_REQUIRED)!=null && !errors.get(CMSConstants.INVIGILATORS_DATEWISE_ISADD_REQUIRED).hasNext()){									
					errors.add(CMSConstants.INVIGILATORS_DATEWISE_ISADD_REQUIRED,new ActionError(CMSConstants.INVIGILATORS_DATEWISE_ISADD_REQUIRED));
				}
			}
		}
		
	}
		 
		/* public ActionForward EditInvigilators(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				ExamInvigilatorExcemptionDatewiseForm invigilatorsForExamForm=(ExamInvigilatorExcemptionDatewiseForm)form;
				ActionMessages messages = new ActionMessages();
				ActionErrors errors=invigilatorsForExamForm.validate(mapping, request);
				
				try{
						List<ExamInvigilatorExcemptionDatewiseTO> list=new ArrayList<ExamInvigilatorExcemptionDatewiseTO>();
						List<ExamInvigilatorExcemptionDatewiseTO> invigilatorsForExamTos=invigilatorsForExamForm.getList();
						Iterator<ExamInvigilatorExcemptionDatewiseTO> iterator=invigilatorsForExamTos.iterator();
					
						setUserId(request, invigilatorsForExamForm); 
						boolean flag=false;
						if(list!=null && !list.isEmpty()){
										//	Map<Integer,ExamInviligatorExemptionDatewise> listOfTeachersExemptedAlready=handler.getInvListAlreadyExempted(invigilatorsForExamForm);
										//	flag=handler.EditInvigilators(invigilatorsForExamForm,listOfTeachersExemptedAlready);
							
							
							if(flag){
								reset(invigilatorsForExamForm);
								Calendar calendar = Calendar.getInstance();   // This gets the current date and time.
								int year=calendar.get(Calendar.YEAR); 
								invigilatorsForExamForm.setAcademicYear(String.valueOf(year));
								ActionMessage message = new ActionMessage("knowledgepro.exam.allotment.invigilators.add.success");
								messages.add("messages", message);
								saveMessages(request, messages);
								return mapping.findForward(CMSConstants.INIT_INVIGILATORS_DATEWISE_EXCEMPTION);
							}else{
								errors.add("error", new ActionError("knowledgepro.exam.allotment.invigilators.add.fail"));
								saveErrors(request, errors);
							}
						}else{
							errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.allotment.select.records.add"));
							saveErrors(request, errors);
						}
				}catch (Exception e) {
					e.printStackTrace();
					if (e instanceof BusinessException) {
						String msgKey = super.handleBusinessException(e);
						ActionMessage message = new ActionMessage(msgKey);
						messages.add("messages", message);
						return mapping.findForward(CMSConstants.ERROR_PAGE);
					} else if (e instanceof ApplicationException) {
						String msg = super.handleApplicationException(e);
						invigilatorsForExamForm.setErrorMessage(msg);
						invigilatorsForExamForm.setErrorStack(e.getMessage());
						return mapping.findForward(CMSConstants.ERROR_PAGE);
					} else {
						throw e;
					}
				}
				return mapping .findForward(CMSConstants.INVIGILATORS_DATEWISE_EXCEMPTION_LIST);
			}*/
	}
