package com.kp.cms.handlers.admin;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.ConvocationRegistration;
import com.kp.cms.bo.admin.ConvocationSession;
import com.kp.cms.bo.admin.OnlineExamSupplementaryApplication;
import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.OnlinePaymentReciepts;
import com.kp.cms.forms.admin.OnlineExamSuppApplicationForm;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.helpers.admin.OnlineExamSuppApplicationHelper;
import com.kp.cms.to.admission.OnlinePaymentRecieptsTo;
import com.kp.cms.transactions.admin.IOnlineExamSuppApplicationTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.usermanagement.ILoginTransaction;
import com.kp.cms.transactionsimpl.admin.OnlineExamSuppApplicationImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.usermanagement.LoginTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.OnlinePaymentUtils;
import com.kp.cms.utilities.PropertyUtil;
import common.Logger;

public class OnlineExamSuppApplicationHandler {
	
 private static final Logger log = Logger.getLogger(OnlineExamSuppApplicationHandler.class);
	 
	 private static volatile OnlineExamSuppApplicationHandler handler = null;
	 IOnlineExamSuppApplicationTransaction txn;
	 OnlineExamSuppApplicationHelper helper= OnlineExamSuppApplicationHelper.getInstance(); 
	    
	    private OnlineExamSuppApplicationHandler(){
	    	txn = new OnlineExamSuppApplicationImpl();
	    }

	    public static OnlineExamSuppApplicationHandler getInstance()
	    {
	        if(handler == null){
	        	handler = new OnlineExamSuppApplicationHandler();
	        	}
	        return handler;
	    }
	       
	    public boolean verifySmartCard(String smartCardNo, int studentId) throws Exception {
			String query="select s.smartCardNo from Student s where s.id="+studentId+" and s.smartCardNo like '%"+smartCardNo+"'";
			INewExamMarksEntryTransaction txn=NewExamMarksEntryTransactionImpl.getInstance();
			List list=txn.getDataForQuery(query);
			if(list!=null && !list.isEmpty()){
				return true;
			}else{
				return false;
			}
		}
	    
	    public boolean saveSupplementaryApplication(OnlineExamSuppApplicationForm crForm) throws Exception {
			OnlinePaymentReciepts onlinePaymentReciepts=new OnlinePaymentReciepts();
			PcFinancialYear pcFinancialYear=new PcFinancialYear();
			pcFinancialYear.setId(crForm.getFinId());
			onlinePaymentReciepts.setPcFinancialYear(pcFinancialYear);
			Student student = new Student();
			student.setId(crForm.getStudentId());
			onlinePaymentReciepts.setStudent(student);
			onlinePaymentReciepts.setTotalAmount(new BigDecimal(crForm.getTotalFees()));
			onlinePaymentReciepts.setApplicationFor("Holistic Education/Indian Constitution Repeat Examination");
			onlinePaymentReciepts.setCreatedBy(crForm.getUserId());
			onlinePaymentReciepts.setCreatedDate(new Date());
			onlinePaymentReciepts.setLastModifiedDate(new Date());
			onlinePaymentReciepts.setModifiedBy(crForm.getUserId());
			onlinePaymentReciepts.setTransactionDate(new Date());
			onlinePaymentReciepts.setIsActive(true);
			PropertyUtil.getInstance().save(onlinePaymentReciepts);
			boolean isPaymentSuccess=false;
			if(onlinePaymentReciepts.getId()>0){
				crForm.setOnlinePaymentId(onlinePaymentReciepts.getId());
				crForm.setTempOnlinePayId(onlinePaymentReciepts.getId());
				String registerNo=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(crForm.getStudentId(),"Student",true,"registerNo");
				OnlinePaymentUtils.dedactAmountFromAccount(registerNo,onlinePaymentReciepts.getId(), crForm.getTotalFees(), onlinePaymentReciepts);
				if(!onlinePaymentReciepts.getIsPaymentFailed()/*true*/){
					isPaymentSuccess=true;
					crForm.setPrintReceipt(true);
					txn.updateAndGenerateRecieptNoOnlinePaymentReciept(onlinePaymentReciepts);
				}else
					crForm.setMsg(onlinePaymentReciepts.getStatus());
					
			}
			
			if(isPaymentSuccess){
				if(crForm.getProgramId().equalsIgnoreCase("4") || crForm.getProgramId().equalsIgnoreCase("94") 
			            || crForm.getProgramId().equalsIgnoreCase("81") || crForm.getProgramId().equalsIgnoreCase("82") 
			            || crForm.getProgramId().equalsIgnoreCase("74"))
				{
									
					int count=txn.GetOnlineExamSuppCount(crForm.getAcademicYear(), crForm.getVenueEngg(), crForm.getDateOfExamEngg());
									if(count<50)
									{
										crForm.setTime("10:00 - 11:00");
										crForm.setDateOfExam(crForm.getDateOfExamEngg());
										crForm.setVenue(crForm.getVenueEngg());
									}
									else if(count>=50 && count<100){
										crForm.setTime("11:00 - 12:00");
										crForm.setDateOfExam(crForm.getDateOfExamEngg());
										crForm.setVenue(crForm.getVenueEngg());
									}
									else if(count>=100 && count<150){
										crForm.setTime("12:00 - 13:00");
										crForm.setDateOfExam(crForm.getDateOfExamEngg());
										crForm.setVenue(crForm.getVenueEngg());
									}
									
									else if(count>=150 && count<200){
										crForm.setTime("14:00 - 15:00");
										crForm.setDateOfExam(crForm.getDateOfExamEngg());
										crForm.setVenue(crForm.getVenueEngg());
									}
									else if(count>=200 && count<250){
										crForm.setTime("15:00 - 16:00");
										crForm.setDateOfExam(crForm.getDateOfExamEngg());
										crForm.setVenue(crForm.getVenueEngg());
									}
									else if(count>=250 && count<300){
										crForm.setTime("16:00 - 17:00");
										crForm.setDateOfExam(crForm.getDateOfExamEngg());
										crForm.setVenue(crForm.getVenueEngg());
									}
									else if(count>=300){
										crForm.setTime("17:00 - 18:00");
										crForm.setDateOfExam(crForm.getDateOfExamEngg());
										crForm.setVenue(crForm.getVenueEngg());
									}
				}if(crForm.getProgramId().equalsIgnoreCase("3")){
	            	if(crForm.getCourseId().equalsIgnoreCase("1")){
	            		crForm.setVenue("Block I, Room No 1");
	            	}
	            	else
	            	{
	            		crForm.setVenue("Central Block, Room No C-217");
	            	}
	            	crForm.setTime("16:00 - 17:00");
					crForm.setDateOfExam(crForm.getDateOfExamMBA());
				}
				else
				{
				
				int count=txn.GetOnlineExamSuppCount(crForm.getAcademicYear(), crForm.getVenueOther(), crForm.getDateOfExamPrev19());
				
				if(count<75){
					crForm.setTime("09:00 - 10:00");
					crForm.setDateOfExam(crForm.getDateOfExamPrev19());
					crForm.setVenue(crForm.getVenueOther());
				}
				else if(count>=75 && count<150){
					crForm.setTime("10:00 - 11:00");
					crForm.setDateOfExam(crForm.getDateOfExamPrev19());
					crForm.setVenue(crForm.getVenueOther());
				}
				else if(count>=150 && count<225){
					crForm.setTime("11:00 - 12:00");
					crForm.setDateOfExam(crForm.getDateOfExamPrev19());
					crForm.setVenue(crForm.getVenueOther());
				}
				else if(count>=225 && count<300){
					crForm.setTime("12:00 - 13:00");
					crForm.setDateOfExam(crForm.getDateOfExamPrev19());
					crForm.setVenue(crForm.getVenueOther());
				}
				else if(count>=300 && count<375){
					crForm.setTime("13:00 - 14:00");
					crForm.setDateOfExam(crForm.getDateOfExamPrev19());
					crForm.setVenue(crForm.getVenueOther());
				}
				else if(count>=375 && count<450){
					crForm.setTime("14:00 - 15:00");
					crForm.setDateOfExam(crForm.getDateOfExamPrev19());
					crForm.setVenue(crForm.getVenueOther());
				}
				else if(count>=450 && count<525){
					crForm.setTime("15:00 - 16:00");
					crForm.setDateOfExam(crForm.getDateOfExamPrev19());
					crForm.setVenue(crForm.getVenueOther());
				}
				else if(count>=525 && count<600){
					crForm.setTime("16:00 - 17:00");
					crForm.setDateOfExam(crForm.getDateOfExamPrev19());
					crForm.setVenue(crForm.getVenueOther());
				}
				else if(count>=600){
					crForm.setTime("17:00 - 18:00");
					crForm.setDateOfExam(crForm.getDateOfExamPrev19());
					crForm.setVenue(crForm.getVenueOther());
				}
			
				else if(count>=600)
				{
				int count2=txn.GetOnlineExamSuppCount(crForm.getAcademicYear(), crForm.getVenueOther(), crForm.getDateOfExamPrev16());
				if(count2<75){
					crForm.setTime("09:00 - 10:00");
					crForm.setDateOfExam(crForm.getDateOfExamPrev16());
					crForm.setVenue(crForm.getVenueOther());
				}
				else if(count2>=75 && count2<150){
					crForm.setTime("10:00 - 11:00");
					crForm.setDateOfExam(crForm.getDateOfExamPrev16());
					crForm.setVenue(crForm.getVenueOther());
				}
				else if(count2>=150 && count2<225){
					crForm.setTime("11:00 - 12:00");
					crForm.setDateOfExam(crForm.getDateOfExamPrev16());
					crForm.setVenue(crForm.getVenueOther());
				}
				else if(count2>=225 && count2<300){
					crForm.setTime("12:00 - 13:00");
					crForm.setDateOfExam(crForm.getDateOfExamPrev16());
					crForm.setVenue(crForm.getVenueOther());
				}
				else if(count2>=300 && count2<375){
					crForm.setTime("13:00 - 14:00");
					crForm.setDateOfExam(crForm.getDateOfExamPrev16());
					crForm.setVenue(crForm.getVenueOther());
				}
				else if(count2>=375 && count2<450){
					crForm.setTime("14:00 - 15:00");
					crForm.setDateOfExam(crForm.getDateOfExamPrev16());
					crForm.setVenue(crForm.getVenueOther());
				}
				else if(count2>=450 && count2<525){
					crForm.setTime("15:00 - 16:00");
					crForm.setDateOfExam(crForm.getDateOfExamPrev16());
					crForm.setVenue(crForm.getVenueOther());
				}
				else if(count2>=525 && count2<600){
					crForm.setTime("16:00 - 17:00");
					crForm.setDateOfExam(crForm.getDateOfExamPrev16());
					crForm.setVenue(crForm.getVenueOther());
				}
				else if(count2>=600){
					crForm.setTime("17:00 - 18:00");
					crForm.setDateOfExam(crForm.getDateOfExamPrev16());
					crForm.setVenue(crForm.getVenueOther());
				}
				else if(count2>=600)
						{
						int count3=txn.GetOnlineExamSuppCount(crForm.getAcademicYear(), crForm.getVenueOther(), crForm.getDateOfExamPrev18());
							if(count3<75){
								crForm.setTime("09:00 - 10:00");
								crForm.setDateOfExam(crForm.getDateOfExamPrev18());
								crForm.setVenue(crForm.getVenueOther());
							}
							else if(count3>=75 && count3<150){
								crForm.setTime("10:00 - 11:00");
								crForm.setDateOfExam(crForm.getDateOfExamPrev18());
								crForm.setVenue(crForm.getVenueOther());
							}
							else if(count3>=150 && count3<225){
								crForm.setTime("11:00 - 12:00");
								crForm.setDateOfExam(crForm.getDateOfExamPrev18());
								crForm.setVenue(crForm.getVenueOther());
							}
							else if(count3>=225 && count3<300){
								crForm.setTime("12:00 - 13:00");
								crForm.setDateOfExam(crForm.getDateOfExamPrev18());
								crForm.setVenue(crForm.getVenueOther());
							}
							else if(count3>=300 && count3<375){
								crForm.setTime("13:00 - 14:00");
								crForm.setDateOfExam(crForm.getDateOfExamPrev18());
								crForm.setVenue(crForm.getVenueOther());
							}
							else if(count3>=375 && count3<450){
								crForm.setTime("14:00 - 15:00");
								crForm.setDateOfExam(crForm.getDateOfExamPrev16());
								crForm.setVenue(crForm.getVenueOther());
							}
							else if(count3>=450 && count3<525){
								crForm.setTime("15:00 - 16:00");
								crForm.setDateOfExam(crForm.getDateOfExamPrev16());
								crForm.setVenue(crForm.getVenueOther());
							}
							else if(count3>=525 && count3<600){
								crForm.setTime("16:00 - 17:00");
								crForm.setDateOfExam(crForm.getDateOfExamPrev16());
								crForm.setVenue(crForm.getVenueOther());
							}
							else if(count3>=600){
								crForm.setTime("17:00 - 18:00");
								crForm.setDateOfExam(crForm.getDateOfExamPrev16());
								crForm.setVenue(crForm.getVenueOther());
							}
						
							/*else if(count3>=300){
								int count4=txn.GetOnlineExamSuppCount(crForm.getAcademicYear(), crForm.getVenueOther(), crForm.getDateOfExamPrev19());
								
								if(count4<75){
									crForm.setTime("09:00 - 10:00");
									crForm.setDateOfExam(crForm.getDateOfExamPrev19());
									crForm.setVenue(crForm.getVenueOther());
								}
								else if(count4>=75 && count4<150){
									crForm.setTime("10:00 - 11:00");
									crForm.setDateOfExam(crForm.getDateOfExamPrev19());
									crForm.setVenue(crForm.getVenueOther());
								}
								else if(count4>=150 && count4<225){
									crForm.setTime("11:00 - 12:00");
									crForm.setDateOfExam(crForm.getDateOfExamPrev19());
									crForm.setVenue(crForm.getVenueOther());
								}
								else if(count4>=225 && count4<300){
									crForm.setTime("12:00 - 13:00");
									crForm.setDateOfExam(crForm.getDateOfExamPrev19());
									crForm.setVenue(crForm.getVenueOther());
								}
								else if(count4>=300 && count4<375){
									crForm.setTime("14:00 - 15:00");
									crForm.setDateOfExam(crForm.getDateOfExamPrev19());
									crForm.setVenue(crForm.getVenueOther());
								}
								else if(count4>=375 && count4<450){
									crForm.setTime("15:00 - 16:00");
									crForm.setDateOfExam(crForm.getDateOfExamPrev19());
									crForm.setVenue(crForm.getVenueOther());
								}
								else if(count4>=450 && count4<525){
									crForm.setTime("16:00 - 17:00");
									crForm.setDateOfExam(crForm.getDateOfExamPrev19());
									crForm.setVenue(crForm.getVenueOther());
								}
								else if(count4>=525){
									crForm.setTime("17:00 - 18:00");
									crForm.setDateOfExam(crForm.getDateOfExamPrev19());
									crForm.setVenue(crForm.getVenueOther());
								}
							}*/
					}}
				}	
			List<OnlineExamSupplementaryApplication> boList=new ArrayList<OnlineExamSupplementaryApplication>();
			OnlineExamSupplementaryApplication cert=new OnlineExamSupplementaryApplication();
				
				cert.setStudentId(student);
				Classes cs=new Classes();
				cs.setId(crForm.getStdClassId());
				cert.setStdClassId(cs);
				cert.setVenue(crForm.getVenue());
				cert.setTime(crForm.getTime());
				cert.setDateOfExam(CommonUtil.ConvertStringToDate(crForm.getDateOfExam()));
				cert.setAppliedDate(new Date());
				cert.setAcademicYear(crForm.getAcademicYear());
				cert.setCreatedBy(crForm.getUserId());
				cert.setCreatedDate(new Date());
				cert.setModifiedBy(crForm.getUserId());
				cert.setLastModifiedDate(new Date());
				cert.setAppliedDate(new Date());
				cert.setIsActive(true);
				boList.add(cert);
			return txn.saveCertificate(boList);
			
			}else
				return false;
		}
	    
	    
	    public List<OnlinePaymentRecieptsTo> getOnlinePaymentReciepts(int onlinePaymentId, int studentId,HttpServletRequest request) throws Exception {
			List<OnlinePaymentReciepts> list=OnlineExamSuppApplicationHelper.getInstance().getOnlineReciepts(onlinePaymentId,studentId);
			return OnlineExamSuppApplicationHelper.getInstance().convertOnlineRecieptToToList(list,request);
		}
		/**
		 * @param crForm
		 * @param session
		 * @throws Exception
		 */
		public ConvocationRegistration loadConvocationRegistration(HttpSession session) throws Exception {
			  ILoginTransaction iLoginTransaction = new LoginTransactionImpl();
			return iLoginTransaction.loadConvocationRegistration(session);
		}

		/**
		 * @param crForm
		 * @param session
		 * @return
		 * @throws Exception
		 */
		public boolean saveConvocationRegistration(
				OnlineExamSuppApplicationForm crForm, HttpSession session) throws Exception{
			   ILoginTransaction iLoginTransaction = new LoginTransactionImpl();
			   int academicYear=iLoginTransaction.getAcademicYearByStudentRegNo(session);
			   ConvocationRegistration registration=OnlineExamSuppApplicationHelper.getInstance().convertFormToBO(academicYear,crForm,session);
			   return iLoginTransaction.saveConvocationRegistration(registration);
		   }

		/**
		 * @param crForm
		 * @param studentId 
		 * @throws Exception
		 */
		public void setDataToForm(OnlineExamSuppApplicationForm crForm, String courseId) throws Exception{
			ConvocationSession convocationSession =  txn.getConvocationDetails(courseId);
			if(convocationSession != null){
				crForm.setConvocationSessionId(convocationSession.getId());
				crForm.setGuestAmount(convocationSession.getPassAmount().doubleValue());
				String startDate = CommonUtil.getStringDate(convocationSession.getDate());
				if(convocationSession.getAmPM().equalsIgnoreCase("AM")){
					crForm.setConvocationDate("Your Convocation will be held on: "+startDate+", Time: 11 a.m" + " (Reporting time: 9 a.m.)");
				}else{
					crForm.setConvocationDate("Your Convocation will be held on: "+startDate+" Time: 5 p.m" + " (Reporting time: 3 p.m.)");
				}
				boolean available = txn.checkMaxGuestAvailable(convocationSession.getId(),convocationSession.getMaxGuestAllowed(),crForm);
				
			}
		}
		 /**
		 * @param crForm
		 * @param session 
		 * @return
		 * @throws Exception
		 */
		public boolean saveConvocationDetails(OnlineExamSuppApplicationForm crForm, HttpSession session) throws Exception {
				OnlinePaymentReciepts onlinePaymentReciepts=new OnlinePaymentReciepts();
				PcFinancialYear pcFinancialYear=new PcFinancialYear();
				pcFinancialYear.setId(crForm.getFinId());
				onlinePaymentReciepts.setPcFinancialYear(pcFinancialYear);
				Student student = new Student();
				student.setId(crForm.getStudentId());
				onlinePaymentReciepts.setStudent(student);
				onlinePaymentReciepts.setTotalAmount(new BigDecimal(crForm.getTotalFees()));
				onlinePaymentReciepts.setApplicationFor("Convocation Guest Pass");
				onlinePaymentReciepts.setCreatedBy(crForm.getUserId());
				onlinePaymentReciepts.setCreatedDate(new Date());
				onlinePaymentReciepts.setLastModifiedDate(new Date());
				onlinePaymentReciepts.setModifiedBy(crForm.getUserId());
				onlinePaymentReciepts.setTransactionDate(new Date());
				onlinePaymentReciepts.setIsActive(true);
				PropertyUtil.getInstance().save(onlinePaymentReciepts);
				boolean isPaymentSuccess=false;
				if(onlinePaymentReciepts.getId()>0){
					crForm.setOnlinePaymentId(onlinePaymentReciepts.getId());
					String registerNo=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(crForm.getStudentId(),"Student",true,"registerNo");
					OnlinePaymentUtils.dedactAmountFromAccount(registerNo,onlinePaymentReciepts.getId(), crForm.getTotalFees(), onlinePaymentReciepts);
					if(!onlinePaymentReciepts.getIsPaymentFailed()){
						isPaymentSuccess=true;
						crForm.setPrintReceipt(true);
						txn.updateAndGenerateRecieptNoOnlinePaymentReciept(onlinePaymentReciepts);
					}else
						crForm.setMsg(onlinePaymentReciepts.getStatus());
						
				}
				if(isPaymentSuccess){
					return saveConvocationRegistration(crForm, session);
				}else{
					return false;	
				}
		}

		public void checkForAvailablePasses(OnlineExamSuppApplicationForm crForm, String courseId) throws Exception {
			ConvocationSession convocationSession =  txn.getConvocationDetails(courseId);
			if(convocationSession != null){
				crForm.setConvocationSessionId(convocationSession.getId());
				crForm.setGuestAmount(convocationSession.getPassAmount().doubleValue());
				String startDate = CommonUtil.getStringDate(convocationSession.getDate());
				if(convocationSession.getAmPM().equalsIgnoreCase("AM")){
					crForm.setConvocationDate("Your Convocation will be held on: "+startDate+", Time: 11 a.m" + " (Reporting time: 9 a.m.)");
				}else{
					crForm.setConvocationDate("Your Convocation will be held on: "+startDate+" Time: 5 p.m" + " (Reporting time: 3 p.m.)");
				}
				boolean available = txn.checkMaxGuestAvailable(convocationSession.getId(),convocationSession.getMaxGuestAllowed(),crForm);
				
			}
		}

}
