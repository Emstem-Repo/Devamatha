package com.kp.cms.helpers.smartcard;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.smartcard.ScLostCorrection;
import com.kp.cms.forms.smartcard.ScLostCorrectionProcessedForm;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.smartcard.ScLostCorrectionProcessedTO;
import com.kp.cms.utilities.CommonUtil;

/**
 * @author dIlIp
 *
 */
public class ScLostCorrectionProcessedHelper {
	
	private static final Log log = LogFactory.getLog(ScLostCorrectionProcessedHelper.class);
	private static volatile ScLostCorrectionProcessedHelper scLostCorrectionProcessedHelper = null;
	
	private ScLostCorrectionProcessedHelper() {
		
	}
	
	public static ScLostCorrectionProcessedHelper getInstance() {

		if (scLostCorrectionProcessedHelper == null) {
			scLostCorrectionProcessedHelper = new ScLostCorrectionProcessedHelper();
		}
		return scLostCorrectionProcessedHelper;
	}
	
	/**
	 * @param scList
	 * @param scForm 
	 * @return
	 * @throws Exception
	 */
	public List<ScLostCorrectionProcessedTO> pupulateScProcessedBOtoTO(List<ScLostCorrection> scList, ScLostCorrectionProcessedForm scForm)
	throws Exception {
		
		ScLostCorrectionProcessedTO scProcessedTO = null;
		List<ScLostCorrectionProcessedTO> newScList = new ArrayList<ScLostCorrectionProcessedTO>();
		
		if (scList != null && !scList.isEmpty()) {
			Iterator<ScLostCorrection> iterator = scList.iterator();
			if(scForm.getIsEmployee().equalsIgnoreCase("Student")){
			while (iterator.hasNext()) {
				ScLostCorrection scLostCorrection = iterator.next();
				scProcessedTO = new ScLostCorrectionProcessedTO();
				if (scLostCorrection.getId() != 0) {
					scProcessedTO.setId(scLostCorrection.getId());
					scProcessedTO.setCardType(scLostCorrection.getCardReason());
					scProcessedTO.setAppliedDate(CommonUtil.formatDates(scLostCorrection.getDateOfSubmission()));
					scProcessedTO.setRegNo(scLostCorrection.getStudentId().getRegisterNo());
					scProcessedTO.setStudentName(scLostCorrection.getStudentId().getAdmAppln().getPersonalData().getFirstName().toUpperCase());
					scProcessedTO.setStudentCourse(scLostCorrection.getStudentId().getAdmAppln().getCourseBySelectedCourseId().getName());
					if(scLostCorrection.getNewSmartCardNum()!=null && !scLostCorrection.getNewSmartCardNum().isEmpty())
						scProcessedTO.setNewSmartCardNo(scLostCorrection.getNewSmartCardNum());
					if(scLostCorrection.getRemarks()!=null && !scLostCorrection.getRemarks().isEmpty())
						scProcessedTO.setRemarks(scLostCorrection.getRemarks());
					StudentTO stu = new StudentTO();
					stu.setId(scLostCorrection.getStudentId().getId());
					if(scLostCorrection.getStudentId().getAdmAppln().getPersonalData().getMobileNo3()!=null
							&& scLostCorrection.getStudentId().getAdmAppln().getPersonalData().getMobileNo3().length() == 10)
						stu.setMobileNo2("91"+scLostCorrection.getStudentId().getAdmAppln().getPersonalData().getMobileNo3());
					else
						stu.setMobileNo2("91"+scLostCorrection.getStudentId().getAdmAppln().getPersonalData().getMobileNo2());
					if(scLostCorrection.getStudentId().getAdmAppln().getPersonalData().getUniversityEmail()!=null && 
							!scLostCorrection.getStudentId().getAdmAppln().getPersonalData().getUniversityEmail().isEmpty())
						stu.setEmail(scLostCorrection.getStudentId().getAdmAppln().getPersonalData().getUniversityEmail());
					scProcessedTO.setStudentTO(stu);
					if(scLostCorrection.getRemarks()!=null && !scLostCorrection.getRemarks().isEmpty())
						scProcessedTO.setRemarks(scLostCorrection.getRemarks());
					if(scLostCorrection.getReasonForRejection()!=null && !scLostCorrection.getReasonForRejection().isEmpty())
						scProcessedTO.setReasonForRejection(scLostCorrection.getReasonForRejection());
					scProcessedTO.setChecked(null);
					if(scLostCorrection.getStatus().equalsIgnoreCase("Processed")){
						scProcessedTO.setProcessedDate(CommonUtil.formatDates(scLostCorrection.getLastModifiedDate()));
						scProcessedTO.setReceivedDate(null);
						scProcessedTO.setIssuedDate(null);
						scProcessedTO.setRejectedDate(null);
						scProcessedTO.setApprovedDate(null);
					}
					else if(scLostCorrection.getStatus().equalsIgnoreCase("Approved")){
						scProcessedTO.setApprovedDate(CommonUtil.formatDates(scLostCorrection.getLastModifiedDate()));
						scProcessedTO.setProcessedDate(null);
						scProcessedTO.setReceivedDate(null);
						scProcessedTO.setIssuedDate(null);
						scProcessedTO.setProcessedDate(null);
					}
					else if(scLostCorrection.getStatus().equalsIgnoreCase("Received")){
						scProcessedTO.setReceivedDate(CommonUtil.formatDates(scLostCorrection.getLastModifiedDate()));
						scProcessedTO.setProcessedDate(null);
						scProcessedTO.setIssuedDate(null);
						scProcessedTO.setRejectedDate(null);
						scProcessedTO.setApprovedDate(null);
					}
					else if(scLostCorrection.getStatus().equalsIgnoreCase("Issued")){
						scProcessedTO.setIssuedDate(CommonUtil.formatDates(scLostCorrection.getLastModifiedDate()));
						scProcessedTO.setProcessedDate(null);
						scProcessedTO.setReceivedDate(null);
						scProcessedTO.setRejectedDate(null);
						scProcessedTO.setApprovedDate(null);
					}
					else if(scLostCorrection.getStatus().equalsIgnoreCase("Rejected")){
						scProcessedTO.setRejectedDate(CommonUtil.formatDates(scLostCorrection.getLastModifiedDate()));
						scProcessedTO.setProcessedDate(null);
						scProcessedTO.setReceivedDate(null);
						scProcessedTO.setIssuedDate(null);
						scProcessedTO.setApprovedDate(null);
					}
					
					
					newScList.add(scProcessedTO);
				}
			}
		}
			else{
				while (iterator.hasNext()) {
					ScLostCorrection scLostCorrection = iterator.next();
					scProcessedTO = new ScLostCorrectionProcessedTO();
					if (scLostCorrection.getId() != 0) {
						scProcessedTO.setId(scLostCorrection.getId());
						scProcessedTO.setCardType(scLostCorrection.getCardReason());
						scProcessedTO.setAppliedDate(CommonUtil.formatDates(scLostCorrection.getDateOfSubmission()));
						scProcessedTO.setEmpId(scLostCorrection.getEmployeeId().getFingerPrintId());
						scProcessedTO.setEmployeeName(scLostCorrection.getEmployeeId().getFirstName());
						scProcessedTO.setEmployeeDepartment(scLostCorrection.getEmployeeId().getDepartment().getName());
						if(scLostCorrection.getNewSmartCardNum()!=null && !scLostCorrection.getNewSmartCardNum().isEmpty())
							scProcessedTO.setNewSmartCardNo(scLostCorrection.getNewSmartCardNum());
						if(scLostCorrection.getRemarks()!=null && !scLostCorrection.getRemarks().isEmpty())
							scProcessedTO.setRemarks(scLostCorrection.getRemarks());
						
						EmployeeTO empTO = new EmployeeTO();
						empTO.setId(scLostCorrection.getEmployeeId().getId());
						if(scLostCorrection.getEmployeeId().getCurrentAddressMobile1()!=null
							&& scLostCorrection.getEmployeeId().getCurrentAddressMobile1().length() == 10)
								empTO.setMobile("91"+scLostCorrection.getEmployeeId().getCurrentAddressMobile1());
						else if(scLostCorrection.getEmployeeId().getCurrentAddressMobile1().length() == 12)
							empTO.setMobile(scLostCorrection.getEmployeeId().getCurrentAddressMobile1());
						else
							empTO.setMobile(scLostCorrection.getEmployeeId().getEmergencyMobile());
							
						if(scLostCorrection.getEmployeeId().getWorkEmail()!=null && 
								!scLostCorrection.getEmployeeId().getWorkEmail().isEmpty())
							empTO.setEmail(scLostCorrection.getEmployeeId().getWorkEmail());
						scProcessedTO.setEmployeeTO(empTO);
						if(scLostCorrection.getRemarks()!=null && !scLostCorrection.getRemarks().isEmpty())
							scProcessedTO.setRemarks(scLostCorrection.getRemarks());
						if(scLostCorrection.getReasonForRejection()!=null && !scLostCorrection.getReasonForRejection().isEmpty())
							scProcessedTO.setReasonForRejection(scLostCorrection.getReasonForRejection());
						scProcessedTO.setChecked(null);
						if(scLostCorrection.getStatus().equalsIgnoreCase("Processed")){
							scProcessedTO.setProcessedDate(CommonUtil.formatDates(scLostCorrection.getLastModifiedDate()));
							scProcessedTO.setReceivedDate(null);
							scProcessedTO.setIssuedDate(null);
							scProcessedTO.setRejectedDate(null);
							scProcessedTO.setApprovedDate(null);
						}
						else if(scLostCorrection.getStatus().equalsIgnoreCase("Approved")){
							scProcessedTO.setApprovedDate(CommonUtil.formatDates(scLostCorrection.getLastModifiedDate()));
							scProcessedTO.setProcessedDate(null);
							scProcessedTO.setReceivedDate(null);
							scProcessedTO.setIssuedDate(null);
							scProcessedTO.setProcessedDate(null);
						}
						else if(scLostCorrection.getStatus().equalsIgnoreCase("Received")){
							scProcessedTO.setReceivedDate(CommonUtil.formatDates(scLostCorrection.getLastModifiedDate()));
							scProcessedTO.setProcessedDate(null);
							scProcessedTO.setIssuedDate(null);
							scProcessedTO.setRejectedDate(null);
							scProcessedTO.setApprovedDate(null);
						}
						else if(scLostCorrection.getStatus().equalsIgnoreCase("Issued")){
							scProcessedTO.setIssuedDate(CommonUtil.formatDates(scLostCorrection.getLastModifiedDate()));
							scProcessedTO.setProcessedDate(null);
							scProcessedTO.setReceivedDate(null);
							scProcessedTO.setRejectedDate(null);
							scProcessedTO.setApprovedDate(null);
						}
						else if(scLostCorrection.getStatus().equalsIgnoreCase("Rejected")){
							scProcessedTO.setRejectedDate(CommonUtil.formatDates(scLostCorrection.getLastModifiedDate()));
							scProcessedTO.setProcessedDate(null);
							scProcessedTO.setReceivedDate(null);
							scProcessedTO.setIssuedDate(null);
							scProcessedTO.setApprovedDate(null);
						}
						
						newScList.add(scProcessedTO);
					}
				}
			
			}
		}
		return newScList;
	}

}
