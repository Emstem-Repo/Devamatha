package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.bo.admin.HostelOnlineApplication;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.hostel.HostelReAdmissionForm;
import com.kp.cms.to.admin.HostelOnlineApplicationTo;
import com.kp.cms.to.hostel.HostelReAdmissionTo;
import com.kp.cms.transactions.hostel.IHostelReAdmissionTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelReAdmissionTxnImpl;

public class HostelReAdmissionHelper {
	
	private static final Log log = LogFactory.getLog(HostelReAdmissionHelper.class);
	IHostelReAdmissionTransaction transaction = HostelReAdmissionTxnImpl.getInstance();
    public static volatile HostelReAdmissionHelper hostelReAdmissionHelper = null;
    
    
    /**
     * @return
     */
    public static HostelReAdmissionHelper getInstance() {
		if (hostelReAdmissionHelper == null) {
			hostelReAdmissionHelper = new HostelReAdmissionHelper();
			return hostelReAdmissionHelper;
		}
		return hostelReAdmissionHelper;
	}


	/**
	 * @param applicationList
	 * @return
	 */
	public List<HostelReAdmissionTo> convertBotoTo(List<HostelOnlineApplication> applicationList)throws Exception {
		List<HostelReAdmissionTo> admissionToList=new ArrayList<HostelReAdmissionTo>();
		log.info("entered convertBotoTo method");
        if(applicationList!=null && !applicationList.isEmpty()){
        	for (HostelOnlineApplication hostelOnlineApplication : applicationList) {
        		HostelReAdmissionTo admissionTo=new HostelReAdmissionTo();
        		admissionTo.setHostelOnlineAppId(hostelOnlineApplication.getId());
				if(hostelOnlineApplication.getStudent().getRegisterNo()!=null
						&& !hostelOnlineApplication.getStudent().getRegisterNo().isEmpty()){
					admissionTo.setRegisterNo(hostelOnlineApplication.getStudent().getRegisterNo());
				}
				if(hostelOnlineApplication.getHlHostel()!=null && hostelOnlineApplication.getHlHostel().getId()!=0){
					admissionTo.setHostelId(hostelOnlineApplication.getHlHostel().getId());
				}
				if(hostelOnlineApplication.getApplicationNo()!=null){
					admissionTo.setApplicationNo(hostelOnlineApplication.getApplicationNo());
				}
				if(hostelOnlineApplication.getIsSelected()!=null && hostelOnlineApplication.getIsSelected()){
					admissionTo.setTempChecked("on");
					if(hostelOnlineApplication.getSelectedRoomType()!=null){
						admissionTo.setChangeRoomType(String.valueOf(hostelOnlineApplication.getSelectedRoomType().getId()));
					}
				}else{
					if(hostelOnlineApplication.getSelectedRoomType()!=null){
						admissionTo.setChangeRoomType(String.valueOf(hostelOnlineApplication.getSelectedRoomType().getId()));
					}
				}
				if(hostelOnlineApplication.getStudent()!=null){
					 admissionTo.setStudentId(hostelOnlineApplication.getStudent().getId());
					if(hostelOnlineApplication.getStudent().getAdmAppln()!=null){
						admissionTo.setStudentApplicationNo(String.valueOf(hostelOnlineApplication.getStudent().getAdmAppln().getApplnNo()));
						if(hostelOnlineApplication.getStudent().getAdmAppln().getPersonalData()!=null){
							if(hostelOnlineApplication.getStudent().getAdmAppln().getPersonalData().getFirstName()!=null
									&& !hostelOnlineApplication.getStudent().getAdmAppln().getPersonalData().getFirstName().isEmpty()){
							 admissionTo.setStudentName(hostelOnlineApplication.getStudent().getAdmAppln().getPersonalData().getFirstName());	
							}
						}
					}
					if(hostelOnlineApplication.getStudent().getClassSchemewise()!=null){
						if(hostelOnlineApplication.getStudent().getClassSchemewise().getClasses()!=null){
							if(hostelOnlineApplication.getStudent().getClassSchemewise().getClasses().getName()!=null
									&& !hostelOnlineApplication.getStudent().getClassSchemewise().getClasses().getName().isEmpty()){
							  admissionTo.setStudentClassName(hostelOnlineApplication.getStudent().getClassSchemewise().getClasses().getName());	
							}
						}
					}
				}
				if(hostelOnlineApplication.getRoomType()!=null){
					admissionTo.setAppliedRoomTypeId(hostelOnlineApplication.getRoomType().getId());
					if(hostelOnlineApplication.getRoomType().getName()!=null && !hostelOnlineApplication.getRoomType().getName().isEmpty()){
						admissionTo.setAppliedRoomType(hostelOnlineApplication.getRoomType().getName());
					}
				}
				admissionToList.add(admissionTo);
			}
        	
        }
		return admissionToList;
	}


	/**
	 * @param admissionForm
	 * @return
	 */
	public List<HostelOnlineApplication> convertTOtoBo(HostelReAdmissionForm admissionForm)throws Exception {
		IHostelReAdmissionTransaction tnx=HostelReAdmissionTxnImpl.getInstance();
		List<HostelOnlineApplication> applicationList=new ArrayList<HostelOnlineApplication>();
		List<HostelOnlineApplicationTo> updatedStudentList=new ArrayList<HostelOnlineApplicationTo>();
		 if(admissionForm.getReAdmissionToList()!=null && !admissionForm.getReAdmissionToList().isEmpty()){
			 for (HostelReAdmissionTo admissionTo : admissionForm.getReAdmissionToList()) {
                   if(admissionTo.getChecked()!=null && admissionTo.getChecked().equalsIgnoreCase("on")){
	                	  HostelOnlineApplication application=new HostelOnlineApplication();
	                	  application.setId(admissionTo.getHostelOnlineAppId());
	                	  HlRoomType hlRoomType2=new HlRoomType();
	                	  hlRoomType2.setId(admissionTo.getAppliedRoomTypeId());
	                	  application.setRoomType(hlRoomType2);
	                	  application.setAcademicYear(Integer.parseInt(admissionForm.getAcademicYear()));
	                	  Student student=new Student();
	                	  student.setId(admissionTo.getStudentId());
	                	  application.setStudent(student);
	                	  HlHostel hlHostel=new HlHostel();
	                	  hlHostel.setId(admissionTo.getHostelId());
	                	  application.setHlHostel(hlHostel);
	                	  application.setApplicationNo(admissionTo.getApplicationNo());
	                	  application.setIsActive(true);
	                	  HlRoomType hlRoomType=new HlRoomType();
	                	  if(admissionTo.getChangeRoomType()!=null && !admissionTo.getChangeRoomType().isEmpty()){
	                		  hlRoomType.setId(Integer.parseInt(admissionTo.getChangeRoomType()));
	                	  }else{
	                		  hlRoomType.setId(admissionTo.getAppliedRoomTypeId());
	                	  }
	                	  application.setSelectedRoomType(hlRoomType);
	                	  
	                	  application.setIsSelected(true);
	                	  application.setModifiedBy(admissionForm.getUserId());
	                	  application.setLastModifiedDate(new Date());
	                	  applicationList.add(application);
	                	  //code added by chandra
	                	  
	                	  HostelOnlineApplicationTo to=new HostelOnlineApplicationTo();
	                	  if(application.getStudent().getId()!=0){
	                	  Student studentBo=tnx.getStudentDetails(application.getStudent().getId());
							 to.setStudentRegNo(studentBo.getRegisterNo());
							 to.setStudentname(studentBo.getAdmAppln().getPersonalData().getFirstName());
							 to.setHlHostelName(admissionForm.getHostelMap().get(admissionTo.getHostelId()));
							 if(admissionForm.getRoomTypeMap()!=null && !admissionForm.getRoomTypeMap().isEmpty()){
								 to.setSelectedRoomType(admissionForm.getRoomTypeMap().get(Integer.parseInt(admissionTo.getChangeRoomType())));
							 }
							to.setMobileNo1(studentBo.getAdmAppln().getPersonalData().getMobileNo1());
							to.setMobileNo2(studentBo.getAdmAppln().getPersonalData().getMobileNo2());
							to.setEmail(studentBo.getAdmAppln().getPersonalData().getUniversityEmail());
	                	  
	                	 updatedStudentList.add(to);
	                	  } 
	                	  
                   }else if(admissionTo.getTempChecked()!=null && admissionTo.getTempChecked().equalsIgnoreCase("on")){
	                	  HostelOnlineApplication application=new HostelOnlineApplication();
	                 	  application.setId(admissionTo.getHostelOnlineAppId());
	                 	  HlRoomType hlRoomType2=new HlRoomType();
	                 	  hlRoomType2.setId(admissionTo.getAppliedRoomTypeId());
	                 	  application.setRoomType(hlRoomType2);
	                 	  application.setAcademicYear(Integer.parseInt(admissionForm.getAcademicYear()));
	                 	  Student student=new Student();
	                 	  student.setId(admissionTo.getStudentId());
	                 	  application.setStudent(student);
	                 	  HlHostel hlHostel=new HlHostel();
	                 	  hlHostel.setId(admissionTo.getHostelId());
	                 	  application.setHlHostel(hlHostel);
	                 	  application.setApplicationNo(admissionTo.getApplicationNo());
	                 	  application.setIsActive(true);
	                 	  HlRoomType hlRoomType=new HlRoomType();
	                 	  hlRoomType.setId(admissionTo.getAppliedRoomTypeId());
	                 	  application.setSelectedRoomType(hlRoomType);
	                 	  application.setIsSelected(false);
	                 	  application.setModifiedBy(admissionForm.getUserId());
	                 	  application.setLastModifiedDate(new Date());
	                 	  applicationList.add(application); 
                   }
			}
			 
		 }
		 if(updatedStudentList!=null && !updatedStudentList.isEmpty())
		 admissionForm.setUpdatedStudentList(updatedStudentList);
		 return applicationList;
	}
	
	
}
