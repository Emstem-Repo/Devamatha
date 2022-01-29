package com.kp.cms.helpers.hostel;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.actions.admin.StudentUploadPhotoAction;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlFeePayment;
import com.kp.cms.bo.admin.HlRoom;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.bo.admin.HlStatus;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.hostel.HostelReservationForm;
import com.kp.cms.to.hostel.HostelReservationTO;
import com.kp.cms.utilities.CommonUtil;

public class HostelReservationHelper {
	
	private static Log log = LogFactory.getLog(HostelReservationHelper.class);
//	static int billNo=151;
	
	/**
	 * 
	 * @param hostelReservationForm
	 * @return
	 */
	private String commonSearch(HostelReservationForm hostelReservationForm) {
		
		log.info("entered commonSearch..");
		String searchCriteria = "";
		
		if (hostelReservationForm.getHostelId()!=null && hostelReservationForm.getHostelId().trim().length() > 0) {
			String appliedHostel = "hlApplicationForm.hlHostelByHlApprovedHostelId.id = "
					+ hostelReservationForm.getHostelId();
			searchCriteria = searchCriteria + appliedHostel;
		}
		
		if (hostelReservationForm.getRequisitionNo()!=null && hostelReservationForm.getRequisitionNo().trim().length() > 0) {
			String requisitionNo = " and hlApplicationForm.requisitionNo = "+ Integer.valueOf(Integer.parseInt(hostelReservationForm.getRequisitionNo()));
			searchCriteria = searchCriteria + requisitionNo;
		}
		
		log.info("exit commonSearch..");
		return searchCriteria;
	}
	
	/**
	 * 
	 * @param hostelReservationForm
	 * @return
	 */
	public String getSearchCriteria(
			HostelReservationForm hostelReservationForm) {
		
		log.info("Entered getSelectionSearchCriteria..");
		String statusCriteria = commonSearch(hostelReservationForm);
		String searchCriteria= "";
		String id=null;
		Properties prop = new Properties();
		try {
			InputStream in = HostelReservationHelper.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(in);
			id=prop.getProperty("knowledgepro.hostel.status.approved.id");
		} catch (IOException e) {			
			log.info("The Errors is--->"+e.getMessage());
		}
		searchCriteria = " from HlApplicationForm as hlApplicationForm " +
				" where " +statusCriteria+
				" and hlApplicationForm.isActive = 1 and hlApplicationForm.hlStatus.id ="+Integer.parseInt(id);
 		
		log.info("Exit getSelectionSearchCriteria..");
		return searchCriteria;
	}
	
	public HostelReservationTO convertBOtoTO(HlApplicationForm applicantHostelDetails){
		HostelReservationTO applicantHostelDetailsTO = null;
		
		if(applicantHostelDetails != null){
			applicantHostelDetailsTO = new HostelReservationTO();
			
			if(applicantHostelDetails.getHlHostelByHlApprovedHostelId()!=null){
				applicantHostelDetailsTO.setHostelId(Integer.toString(applicantHostelDetails.getHlHostelByHlApprovedHostelId().getId()));
				applicantHostelDetailsTO.setHostelName(applicantHostelDetails.getHlHostelByHlApprovedHostelId().getName());
			}
			if(applicantHostelDetails.getHlStatus()!=null){
				if(applicantHostelDetails.getHlStatus().getId()>0){
					applicantHostelDetailsTO.setHlStatusId(applicantHostelDetails.getHlStatus().getId());
				}
			}
			if(applicantHostelDetails.getId()>0){
				applicantHostelDetailsTO.setHlAppFormId(applicantHostelDetails.getId());
			}
		/*	if(applicantHostelDetails.getBillNo()>0){
				applicantHostelDetailsTO.setBillNo(String.valueOf(applicantHostelDetails.getBillNo()));
			}*/
			if(applicantHostelDetails.getHlRoomTypeByHlApprovedRoomTypeId()!=null){
				if(applicantHostelDetails.getHlRoomTypeByHlApprovedRoomTypeId().getId() > 0){
					applicantHostelDetailsTO.setRoomTypeId(applicantHostelDetails.getHlRoomTypeByHlApprovedRoomTypeId().getId());
					if(applicantHostelDetails.getHlRoomTypeByHlApprovedRoomTypeId().getName()!=null){
						applicantHostelDetailsTO.setRoomTypeName(applicantHostelDetails.getHlRoomTypeByHlApprovedRoomTypeId().getName());
					}
				}
			}
			if(applicantHostelDetails.getRequisitionNo()!= null){
				applicantHostelDetailsTO.setRequisitionNo(applicantHostelDetails.getRequisitionNo().toString());
			}
			if(applicantHostelDetails.getAdmAppln()!=null && applicantHostelDetails.getAdmAppln().getPersonalData()!=null){
				applicantHostelDetailsTO.setIsStaff("false");
				applicantHostelDetailsTO.setApplicationId(applicantHostelDetails.getAdmAppln().getId());
				applicantHostelDetailsTO.setApplicationNumber(""+applicantHostelDetails.getAdmAppln().getApplnNo());
				if(applicantHostelDetails.getAdmAppln().getPersonalData().getFirstName()!=null && applicantHostelDetails.getAdmAppln().getPersonalData().getMiddleName()!=null && applicantHostelDetails.getAdmAppln().getPersonalData().getLastName()!=null && !applicantHostelDetails.getAdmAppln().getPersonalData().getFirstName().isEmpty() && !applicantHostelDetails.getAdmAppln().getPersonalData().getMiddleName().isEmpty() && !applicantHostelDetails.getAdmAppln().getPersonalData().getLastName().isEmpty()){
					applicantHostelDetailsTO.setApplicantName(applicantHostelDetails.getAdmAppln().getPersonalData().getFirstName()+" "+applicantHostelDetails.getAdmAppln().getPersonalData().getMiddleName()+" "+applicantHostelDetails.getAdmAppln().getPersonalData().getLastName());
				}else if(applicantHostelDetails.getAdmAppln().getPersonalData().getFirstName()!=null && applicantHostelDetails.getAdmAppln().getPersonalData().getMiddleName()!=null && !applicantHostelDetails.getAdmAppln().getPersonalData().getFirstName().isEmpty() && !applicantHostelDetails.getAdmAppln().getPersonalData().getMiddleName().isEmpty()){
					applicantHostelDetailsTO.setApplicantName(applicantHostelDetails.getAdmAppln().getPersonalData().getFirstName()+" "+applicantHostelDetails.getAdmAppln().getPersonalData().getMiddleName());
				}else if(applicantHostelDetails.getAdmAppln().getPersonalData().getFirstName()!=null && !applicantHostelDetails.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
					applicantHostelDetailsTO.setApplicantName(applicantHostelDetails.getAdmAppln().getPersonalData().getFirstName());
				}
				Iterator<Student> itr = applicantHostelDetails.getAdmAppln().getStudents().iterator();
				StringBuffer appRegNoString = new StringBuffer();
				while (itr.hasNext()){
					Student student = itr.next();
					appRegNoString.append(student.getRegisterNo());
					if(student.getRegisterNo()!= null && !student.getRegisterNo().trim().isEmpty() && student.getRollNo()!= null && !student.getRollNo().trim().isEmpty()){
						appRegNoString.append("/");
					}
						appRegNoString.append(student.getRollNo());
				}
				if(applicantHostelDetails.getAdmAppln().getCourseBySelectedCourseId()!= null){
					applicantHostelDetailsTO.setCourse(applicantHostelDetails.getAdmAppln().getCourseBySelectedCourseId().getCode());
				}
				
				if(applicantHostelDetails.getAdmAppln().getPersonalData().getDateOfBirth()!=null){
					applicantHostelDetailsTO.setDateOfBirth(CommonUtil.getStringDate(applicantHostelDetails.getAdmAppln().getPersonalData().getDateOfBirth()));
				}
				
				if(applicantHostelDetails.getAdmAppln().getPersonalData().getNationalityOthers()!=null && !applicantHostelDetails.getAdmAppln().getPersonalData().getNationalityOthers().isEmpty()){
					applicantHostelDetailsTO.setNationality(applicantHostelDetails.getAdmAppln().getPersonalData().getNationalityOthers());
				}else if(applicantHostelDetails.getAdmAppln().getPersonalData().getNationality()!=null){
					applicantHostelDetailsTO.setNationality(applicantHostelDetails.getAdmAppln().getPersonalData().getNationality().getName());
				}
				
				if(applicantHostelDetails.getAdmAppln().getPersonalData().getCasteOthers()!=null && !applicantHostelDetails.getAdmAppln().getPersonalData().getCasteOthers().isEmpty()){
					applicantHostelDetailsTO.setCaste(applicantHostelDetails.getAdmAppln().getPersonalData().getCasteOthers());
				}else if(applicantHostelDetails.getAdmAppln().getPersonalData().getCaste()!=null){
					applicantHostelDetailsTO.setCaste(applicantHostelDetails.getAdmAppln().getPersonalData().getCaste().getName());
				}
				
				if(applicantHostelDetails.getAdmAppln().getPersonalData().getGender()!=null){
					applicantHostelDetailsTO.setGender(applicantHostelDetails.getAdmAppln().getPersonalData().getGender());
				}
				if(applicantHostelDetails.getAdmAppln().getPersonalData().getCurrentAddressLine1()!=null){
					applicantHostelDetailsTO.setAddressLine1(applicantHostelDetails.getAdmAppln().getPersonalData().getCurrentAddressLine1());
				}
				if(applicantHostelDetails.getAdmAppln().getPersonalData().getCurrentAddressLine2()!=null){
					applicantHostelDetailsTO.setAddressLine2(applicantHostelDetails.getAdmAppln().getPersonalData().getCurrentAddressLine2());
				}
				if(applicantHostelDetails.getAdmAppln().getPersonalData().getCurrentAddressStateOthers()!=null){
					applicantHostelDetailsTO.setState(applicantHostelDetails.getAdmAppln().getPersonalData().getCurrentAddressStateOthers());
				}else if(applicantHostelDetails.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId()!=null && applicantHostelDetails.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName()!=null){
					applicantHostelDetailsTO.setState(applicantHostelDetails.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName());
				}
				if(applicantHostelDetails.getAdmAppln().getPersonalData().getCurrentAddressCountryOthers()!=null){
					applicantHostelDetailsTO.setCountry(applicantHostelDetails.getAdmAppln().getPersonalData().getCurrentAddressCountryOthers());
				}else if(applicantHostelDetails.getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId()!=null && applicantHostelDetails.getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId().getName()!=null){
					applicantHostelDetailsTO.setCountry(applicantHostelDetails.getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId().getName());
				}
				if(applicantHostelDetails.getAdmAppln().getPersonalData().getCityByCurrentAddressCityId()!=null){
					applicantHostelDetailsTO.setCity(applicantHostelDetails.getAdmAppln().getPersonalData().getCityByCurrentAddressCityId());
				}
				if(applicantHostelDetails.getAdmAppln().getPersonalData().getCurrentAddressZipCode()!=null){
					applicantHostelDetailsTO.setZipCode(applicantHostelDetails.getAdmAppln().getPersonalData().getCurrentAddressZipCode());
				}
				if(applicantHostelDetails.getAdmAppln().getPersonalData().getEmail()!=null){
					applicantHostelDetailsTO.setEmail(applicantHostelDetails.getAdmAppln().getPersonalData().getEmail());
				}
				if(applicantHostelDetails.getAdmAppln().getPersonalData().getPhNo1()!=null && applicantHostelDetails.getAdmAppln().getPersonalData().getPhNo2()!=null && applicantHostelDetails.getAdmAppln().getPersonalData().getPhNo3()!=null && !applicantHostelDetails.getAdmAppln().getPersonalData().getPhNo1().isEmpty() && !applicantHostelDetails.getAdmAppln().getPersonalData().getPhNo2().isEmpty() && !applicantHostelDetails.getAdmAppln().getPersonalData().getPhNo3().isEmpty()){
					applicantHostelDetailsTO.setPhone(applicantHostelDetails.getAdmAppln().getPersonalData().getPhNo1()+" "+applicantHostelDetails.getAdmAppln().getPersonalData().getPhNo2()+" "+applicantHostelDetails.getAdmAppln().getPersonalData().getPhNo3());
				}else if(applicantHostelDetails.getAdmAppln().getPersonalData().getPhNo1()!=null && applicantHostelDetails.getAdmAppln().getPersonalData().getPhNo2()!=null && !applicantHostelDetails.getAdmAppln().getPersonalData().getPhNo1().isEmpty() && !applicantHostelDetails.getAdmAppln().getPersonalData().getPhNo2().isEmpty()){
					applicantHostelDetailsTO.setPhone(applicantHostelDetails.getAdmAppln().getPersonalData().getPhNo1()+" "+applicantHostelDetails.getAdmAppln().getPersonalData().getPhNo2());
				}else if(applicantHostelDetails.getAdmAppln().getPersonalData().getPhNo1()!=null && !applicantHostelDetails.getAdmAppln().getPersonalData().getPhNo1().isEmpty()){
					applicantHostelDetailsTO.setPhone(applicantHostelDetails.getAdmAppln().getPersonalData().getPhNo1());
				}
			}
			else
			{
				applicantHostelDetailsTO.setIsStaff("true");
				applicantHostelDetailsTO.setApplicationId(applicantHostelDetails.getEmployee().getId());
				applicantHostelDetailsTO.setApplicationNumber(applicantHostelDetails.getEmployee().getCode());
				if(applicantHostelDetails.getEmployee().getDob()!= null){
					applicantHostelDetailsTO.setDateOfBirth(applicantHostelDetails.getEmployee().getDob().toString());
				}
				
				String name="";
				if(applicantHostelDetails.getEmployee().getFirstName()!=null){
					name=name+applicantHostelDetails.getEmployee().getFirstName();
				}
				
				applicantHostelDetailsTO.setApplicantName(name);
				if(applicantHostelDetails.getEmployee().getEmail() != null){
					applicantHostelDetailsTO.setEmail(applicantHostelDetails.getEmployee().getEmail());
				}
				if(applicantHostelDetails.getEmployee().getPermanentAddressZip() != null){
					applicantHostelDetailsTO.setZipCode(applicantHostelDetails.getEmployee().getPermanentAddressZip());
				}
				if(applicantHostelDetails.getEmployee().getCommunicationAddressLine1() != null){
					applicantHostelDetailsTO.setAddressLine1(applicantHostelDetails.getEmployee().getCommunicationAddressLine1());
					}
				
				if(applicantHostelDetails.getEmployee().getCommunicationAddressLine2()!= null){
					applicantHostelDetailsTO.setAddressLine2(applicantHostelDetails.getEmployee().getCommunicationAddressLine2());
					}
								if(applicantHostelDetails.getEmployee().getCommunicationAddressCity() != null){
					applicantHostelDetailsTO.setCity(applicantHostelDetails.getEmployee().getCommunicationAddressCity());
					}
				if(applicantHostelDetails.getEmployee().getStateByCommunicationAddressStateId() != null){
					applicantHostelDetailsTO.setState(applicantHostelDetails.getEmployee().getStateByCommunicationAddressStateId().getName());
					}
				if(applicantHostelDetails.getEmployee().getCountryByCommunicationAddressCountryId()!= null){
					applicantHostelDetailsTO.setCountry(applicantHostelDetails.getEmployee().getCountryByCommunicationAddressCountryId().getName());
					}
				if(applicantHostelDetails.getEmployee().getGender()!=null)
				{
					applicantHostelDetailsTO.setGender(applicantHostelDetails.getEmployee().getGender());
				}
				if(applicantHostelDetails.getEmployee().getNationality()!=null)
				{
					applicantHostelDetailsTO.setNationality(applicantHostelDetails.getEmployee().getNationality().getName());
				}
			}
			if(applicantHostelDetails.getIsVeg()!=null){
				applicantHostelDetailsTO.setVeg(applicantHostelDetails.getIsVeg());
			}
			
			if(applicantHostelDetails.getHlFeePayments()!=null){
				Iterator<HlFeePayment> feePaymentsItr = applicantHostelDetails.getHlFeePayments().iterator();
				
				while (feePaymentsItr.hasNext()) {
					HlFeePayment hlFeePayment = (HlFeePayment) feePaymentsItr.next();
					
					if(hlFeePayment.getIsActive()){
						if(hlFeePayment.getBank()!=null){
							applicantHostelDetailsTO.setBankName(hlFeePayment.getBank());
						}
						if(hlFeePayment.getBankBranch()!=null){
							applicantHostelDetailsTO.setBranchName(hlFeePayment.getBankBranch());
						}
						if(hlFeePayment.getVoucherNo()!=null){
							applicantHostelDetailsTO.setVoucherNo(hlFeePayment.getVoucherNo());
						}
						if(hlFeePayment.getJournalNo()!=null){
							applicantHostelDetailsTO.setJournalNo(hlFeePayment.getJournalNo());
						}
						if(hlFeePayment.getRemarks()!=null){
							applicantHostelDetailsTO.setRemarks(hlFeePayment.getRemarks());
						}
						if(hlFeePayment.getAmount()!=null){
							applicantHostelDetailsTO.setAmount(String.valueOf(hlFeePayment.getAmount()));
						}
					}
				}
			}
		}
		return applicantHostelDetailsTO;
	}
	
	public HlRoomTransaction saveReservationDetails(HostelReservationForm hostelReservationForm,int count,int currentOccupantCount) throws Exception {
		String id="";
		Properties prop = new Properties();
		try {
			InputStream in = HostelReservationHelper.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(in);
			id=prop.getProperty("knowledgepro.hostel.status.reservation.id");
		} catch (IOException e) {			
			log.info("The Errors is--->"+e.getMessage());
		}
        if(!id.isEmpty()){
        	
        	HlRoomTransaction hlRoomTransaction = new HlRoomTransaction();		
        	
        	HlStatus stat=new HlStatus();		
        	stat.setId(Integer.parseInt(id));
        	
        	HlRoom room=new HlRoom();
        	room.setId(Integer.parseInt(hostelReservationForm.getRoomId()));
        	
        	if(hostelReservationForm.getIsStaff().equalsIgnoreCase("true"))
        	{
        		Employee emp=new Employee();
        		emp.setId(Integer.parseInt(hostelReservationForm.getApplicationId()));
        		hlRoomTransaction.setEmployee(emp);
        	}
        	else
        	{
        		AdmAppln admAppln = new AdmAppln();
            	admAppln.setId(Integer.parseInt(hostelReservationForm.getApplicationId()));
            	hlRoomTransaction.setAdmAppln(admAppln);
        	}
        	HlApplicationForm appForm=new HlApplicationForm();
        	appForm.setId(Integer.parseInt(hostelReservationForm.getHlAppFormId()));
        	hlRoomTransaction.setHlRoom(room);
        	hlRoomTransaction.setHlStatus(stat);
        	hlRoomTransaction.setHlApplicationForm(appForm);
        	
        	hlRoomTransaction.setCreatedBy(hostelReservationForm.getUserId());
        	hlRoomTransaction.setCreatedDate(new Date());
        	hlRoomTransaction.setIsActive(true);
        	hlRoomTransaction.setLastModifiedDate(new Date());
        	hlRoomTransaction.setModifiedBy(hostelReservationForm.getUserId());
        	if(hostelReservationForm.getRemarks()!=null && !hostelReservationForm.getRemarks().isEmpty()){
        		hlRoomTransaction.setRemarks(hostelReservationForm.getRemarks());
        	}
        	if(hostelReservationForm.getReservationDate()!=null && !hostelReservationForm.getReservationDate().isEmpty()){
        		Calendar cal=Calendar.getInstance();
        		String finalTime = hostelReservationForm.getReservationDate()+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
        		hlRoomTransaction.setTxnDate(CommonUtil.ConvertStringToSQLDateTime(finalTime));
        	}
        	hlRoomTransaction.setCurrentReservationCount(count+1);
        	hlRoomTransaction.setCurrentOccupantsCount(currentOccupantCount);
        	return hlRoomTransaction;
        }
        return null;
	}
}
