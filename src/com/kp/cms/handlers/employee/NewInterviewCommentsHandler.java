	package com.kp.cms.handlers.employee;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.employee.InterviewRatingFactor;
import com.kp.cms.bo.employee.NewInterviewComments;
import com.kp.cms.bo.employee.NewInterviewCommentsDetails;
import com.kp.cms.forms.employee.NewInterviewCommentsForm;
import com.kp.cms.helpers.employee.NewInterviewCommentsHelper;
import com.kp.cms.to.admin.EmpAcheivementTO;
import com.kp.cms.to.employee.EmpEducationalDetailsTO;
import com.kp.cms.to.employee.EmpOnlineResumeTO;
import com.kp.cms.to.employee.InterviewRatingFactorTO;
import com.kp.cms.to.employee.NewInterviewCommentsDetailsTo;
import com.kp.cms.to.employee.NewInterviewCommentsTO;
import com.kp.cms.transactions.employee.INewInterviewCommentsTransaction;
import com.kp.cms.transactionsimpl.employee.NewInterviewCommentsTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
	
	public class NewInterviewCommentsHandler {
		
	 public static volatile NewInterviewCommentsHandler interviewCommentsHandler=null;
	 
	 public static NewInterviewCommentsHandler getInstance(){
		 if(interviewCommentsHandler == null){
			 interviewCommentsHandler = new NewInterviewCommentsHandler();
			 return interviewCommentsHandler;
		 }
		return interviewCommentsHandler;
	 }
	
	/**
	 * @param interviewCommentsForm
	 * @return
	 * @throws Exception 
	 */
	public boolean addInterviewComments(NewInterviewCommentsForm interviewCommentsForm) throws Exception {
		boolean isAdded=false;
		INewInterviewCommentsTransaction transaction = new NewInterviewCommentsTransactionImpl();
		NewInterviewComments newInterviewComments=NewInterviewCommentsHelper.getInstance().copyDataFromFormToBO(interviewCommentsForm);
		isAdded=transaction.addInterviewComments(newInterviewComments,interviewCommentsForm);
		if(isAdded){
			if(!interviewCommentsForm.getDepartment().equalsIgnoreCase(interviewCommentsForm.getDepartmentId())){
				transaction.updateDepartmentIdInEmpOnlineResume(interviewCommentsForm);
			}else if(interviewCommentsForm.getDepartment()!=null && (interviewCommentsForm.getDepartmentId()==null ||interviewCommentsForm.getDepartmentId().isEmpty())){
				transaction.updateDepartmentIdInEmpOnlineResume(interviewCommentsForm);
			}else {
				interviewCommentsForm.setDepartment(interviewCommentsForm.getDepartmentId());
			}
		}
		return isAdded;
	}

	
	/**
	 * @return
	 * @throws Exception 
	 */
	public List<NewInterviewCommentsTO> getSearchList() throws Exception {
		INewInterviewCommentsTransaction transaction = new NewInterviewCommentsTransactionImpl();
		List<Object[]> comments = transaction.getEmpOnlineResumeList();
		List<NewInterviewCommentsTO> commentsTOs = NewInterviewCommentsHelper.getInstance().copyOnlineResumeListToTo(comments);
		return commentsTOs;
	}
	
	
	/**
	 * @param interviewCommentsForm
	 * @return
	 * @throws Exception 
	 */
	public List<NewInterviewCommentsTO> getSearchList1(NewInterviewCommentsForm interviewCommentsForm) throws Exception {
		INewInterviewCommentsTransaction transaction = new NewInterviewCommentsTransactionImpl();
		List<Object[]> onlineResume=transaction.getEmpOnlineResumeList1(interviewCommentsForm);
		List<NewInterviewCommentsTO> tos=NewInterviewCommentsHelper.getInstance().copyOnlineResumeListToTo(onlineResume);
		return tos;
	}
	
	/**
	 * @param interviewCommentsForm
	 * @return
	 * @throws Exception 
	 */
	public NewInterviewCommentsForm getInterviewCommentDetails(NewInterviewCommentsForm interviewCommentsForm) throws Exception {
		INewInterviewCommentsTransaction transaction = new NewInterviewCommentsTransactionImpl();
		NewInterviewComments interviewComments=transaction.getEmpInfo(interviewCommentsForm);
		interviewCommentsForm = NewInterviewCommentsHelper.getInstance().copyEmpInfoBOtoTO(interviewComments,interviewCommentsForm);
		return interviewCommentsForm;
	}
	/**
	 * @param interviewCommentsForm
	 * @return
	 * @throws Exception 
	 */
	public List<InterviewRatingFactorTO> getRatingFactorDetails(NewInterviewCommentsForm interviewCommentsForm) throws Exception {
		INewInterviewCommentsTransaction transaction = new NewInterviewCommentsTransactionImpl();
		NewInterviewComments interviewComments=transaction.getEmpInfo(interviewCommentsForm);
		List<InterviewRatingFactor> ratingFactorList=transaction.getInterviewRatingFactor(interviewCommentsForm);
		
		List<InterviewRatingFactorTO> ratingFactorTOs = NewInterviewCommentsHelper.getInstance().copyRatingBOtoTO(interviewComments,ratingFactorList,interviewCommentsForm);
		return ratingFactorTOs;
	}
	/**
	 * @param interviewCommentsForm
	 * @param request
	 * @throws Exception 
	 */
	public List<NewInterviewCommentsTO> getPrintDetailsList(NewInterviewCommentsForm interviewCommentsForm,HttpServletRequest request) throws Exception {
		INewInterviewCommentsTransaction transaction = new NewInterviewCommentsTransactionImpl();
		List<NewInterviewComments> onlineResumes = transaction.getPrintDetailsList(interviewCommentsForm);
		List<NewInterviewCommentsTO> list = NewInterviewCommentsHelper.getInstance().copyInterviewCommentsBOToTO(onlineResumes);
		return list;
	}
	/**
	 * @param interviewCommentsForm
	 * @return
	 * @throws Exception 
	 */
	public List<NewInterviewCommentsDetailsTo> getPrintCommentsDetails(NewInterviewCommentsForm interviewCommentsForm) throws Exception {
		INewInterviewCommentsTransaction transaction = new NewInterviewCommentsTransactionImpl();
		List<NewInterviewCommentsDetails> commentsDetails = transaction.getPrintCommentsDetailsList(interviewCommentsForm);
		List<NewInterviewCommentsDetailsTo> list = NewInterviewCommentsHelper.getInstance().copyCommentDetailsBOToTO(commentsDetails,interviewCommentsForm);
		return list;
	}
	/**
	 * @param interviewCommentsForm
	 * @return
	 * @throws Exception 
	 */
	public List<EmpEducationalDetailsTO> getPrintEmpEduDetails(NewInterviewCommentsForm interviewCommentsForm) throws Exception {
		INewInterviewCommentsTransaction transaction = NewInterviewCommentsTransactionImpl.getInstance();
//		List<EmpOnlineResume> onlineResumes = transaction.getEmpInfoDetails(interviewCommentsForm);
//		List<EmpEducationalDetailsTO>  educationalDetailsTOs = NewInterviewCommentsHelper.getInstance().copyEmpEduDetailsBoToTO(onlineResumes);
		List<Object[]> onlineResumes = transaction.getEmpOnlineEducationalList(interviewCommentsForm);
		List<EmpEducationalDetailsTO>  educationalDetailsTOs = NewInterviewCommentsHelper.getInstance().copyEmpOnlineEducationalBoTOTo(onlineResumes);
		return educationalDetailsTOs;
	}
	/**
	 * @param interviewCommentsForm
	 * @return
	 * @throws Exception 
	 */
	public List<NewInterviewCommentsTO> getEmpDetails(NewInterviewCommentsForm interviewCommentsForm) throws Exception {
		INewInterviewCommentsTransaction transaction = NewInterviewCommentsTransactionImpl.getInstance();
		Object[] resume= transaction.getEmpDetails(interviewCommentsForm);
		List<NewInterviewCommentsTO> tos =NewInterviewCommentsHelper.getInstance().copyEmpDetailsToTO(resume,interviewCommentsForm);
		return tos;
	}

	/**
	 * @return
	 * @throws Exception 
	 */
	public List<EmpOnlineResumeTO> getViewEmpInfoDetails(NewInterviewCommentsForm interviewCommentsForm) throws Exception {
		INewInterviewCommentsTransaction transaction = NewInterviewCommentsTransactionImpl.getInstance();
		List<EmpOnlineResume> onlineResumes = transaction.getEmpInfoDetails(interviewCommentsForm);
		List<EmpOnlineResumeTO> resumeTOs = NewInterviewCommentsHelper.getInstance().copyEmpResumeDetailsToTO(onlineResumes);
		return resumeTOs;
	}

	public NewInterviewCommentsForm getLogo(NewInterviewCommentsForm interviewCommentsForm,HttpServletRequest request) throws Exception {
		
		interviewCommentsForm = NewInterviewCommentsHelper.getInstance().copyLogoFromBoToTO(interviewCommentsForm,request);
		
		return interviewCommentsForm;
	}

	/**
	 * @param interviewCommentsForm
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, Integer> getNoOfInterviewers(NewInterviewCommentsForm interviewCommentsForm) throws Exception{
		INewInterviewCommentsTransaction transaction = NewInterviewCommentsTransactionImpl.getInstance();
		Object[] objects=transaction.getNoOfInterviewers(interviewCommentsForm);
		Map<Integer, Integer> map=NewInterviewCommentsHelper.getInstance().copyNoOfInterviewersToTO(objects);
		return map;
	}

	/**
	 * @param interviewCommentsForm
	 * @return
	 */
	public List<EmpOnlineResumeTO> getEmpInfo(NewInterviewCommentsForm interviewCommentsForm) throws Exception{
		INewInterviewCommentsTransaction transaction = NewInterviewCommentsTransactionImpl.getInstance();
		List<EmpOnlineResume> onlineResumes = transaction.getEmpInfoDetails(interviewCommentsForm);
		List<EmpOnlineResumeTO> empOnlineResumeTOs=NewInterviewCommentsHelper.getInstance().copyEmpInfoBOToTO(onlineResumes);
		return empOnlineResumeTOs;
	}

	/**
	 * @param interviewCommentsForm
	 * @return
	 * @throws Exception 
	 */
	public List<EmpEducationalDetailsTO> getEmpEducationalDetails(NewInterviewCommentsForm interviewCommentsForm) throws Exception {
		INewInterviewCommentsTransaction transaction = NewInterviewCommentsTransactionImpl.getInstance();
//		List<EmpOnlineResume> onlineResumes = transaction.getEmpInfoDetails(interviewCommentsForm);
//		List<EmpEducationalDetailsTO>  educationalDetailsTOs = NewInterviewCommentsHelper.getInstance().copyEmpEduDetailsBoToTO(onlineResumes);
		List<Object[]> onlineResumes = transaction.getEmpOnlineEducationalList(interviewCommentsForm);
		List<EmpEducationalDetailsTO>  educationalDetailsTOs = NewInterviewCommentsHelper.getInstance().copyEmpOnlineEducationalBoTOTo(onlineResumes);
		return educationalDetailsTOs;
	}

	/**
	 * @param interviewCommentsForm
	 * @return
	 * @throws Exception 
	 */
	public List<EmpAcheivementTO> getEmpAcheivementDetails(NewInterviewCommentsForm interviewCommentsForm) throws Exception {
		INewInterviewCommentsTransaction transaction = NewInterviewCommentsTransactionImpl.getInstance();
		List<EmpOnlineResume> onlineResumes = transaction.getEmpInfoDetails(interviewCommentsForm);
		List<EmpAcheivementTO> empAcheivementTOs= NewInterviewCommentsHelper.getInstance().copyEmpAcheivementDetailsToTO(onlineResumes);
		return empAcheivementTOs;
	}

	/**
	 * @param interviewCommentsForm
	 * @param empOnlineResumeTOs
	 * @throws Exception
	 */
	//code added by sudhir
	public void AgeCalculation(NewInterviewCommentsForm interviewCommentsForm, List<EmpOnlineResumeTO> empOnlineResumeTOs)throws Exception {
		List<EmpOnlineResumeTO> list =new ArrayList<EmpOnlineResumeTO>();
		  Calendar cal1 = new GregorianCalendar();
	      Calendar cal2 = new GregorianCalendar();
	      int age = 0;
	      int factor = 0; 
	      Date today= new Date();
	      if(empOnlineResumeTOs!=null){
	    	  Iterator<EmpOnlineResumeTO> iterator = empOnlineResumeTOs.iterator();
	    	  while (iterator.hasNext()) {
				EmpOnlineResumeTO empOnlineResumeTO = (EmpOnlineResumeTO) iterator .next();
				if(empOnlineResumeTO.getDateofBirth()!=null && !empOnlineResumeTO.getDateofBirth().isEmpty()){
				      Date dateOfBirth;
				      dateOfBirth= CommonUtil.ConvertStringToDate(empOnlineResumeTO.getDateofBirth());
				    //  Date date1 = new SimpleDateFormat("MM-dd-yyyy").parse(dateOfBirth);
				      
				   //   Date date2 = new SimpleDateFormat("MM-dd-yyyy").parse(String.valueOf(today));
				      
				      cal1.setTime(dateOfBirth);
				      cal2.setTime(today);
				      if(cal2.get(Calendar.DAY_OF_YEAR) < cal1.get(Calendar.DAY_OF_YEAR)) {
				            factor = -1; 
				      }
				      age = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR) + factor;
				     String Age=String.valueOf(age);
				     empOnlineResumeTO.setAge1(Age);
				     }
				list.add(empOnlineResumeTO);
			}
	      }
	     
}
}
