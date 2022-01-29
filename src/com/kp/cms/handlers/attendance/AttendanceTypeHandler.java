package com.kp.cms.handlers.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AttendanceType;
import com.kp.cms.bo.admin.AttendanceTypeMandatory;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.attendance.AttendanceEntryForm;
import com.kp.cms.forms.attendance.AttendanceTypeForm;
import com.kp.cms.helpers.attendance.AttendanceTypeHelper;
import com.kp.cms.to.attendance.AttendanceTypeMandatoryTO;
import com.kp.cms.to.attendance.AttendanceTypeTO;
import com.kp.cms.transactions.ajax.ICommonAjax;
import com.kp.cms.transactions.attandance.IAttendanceTypeTransaction;
import com.kp.cms.transactionsimpl.ajax.CommonAjaxImpl;
import com.kp.cms.transactionsimpl.attendance.AttendanceTypeTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
	
public class AttendanceTypeHandler {
	
	private static final Log log = LogFactory.getLog(AttendanceTypeHandler.class);
	
	private static volatile AttendanceTypeHandler attendanceTypeHandler = null;
	private static String NO = "no";
	
	private AttendanceTypeHandler() {
	}
	/**
	 * 
	 * @returns a single instance when called (Singleton implementation)
	 */
	public static AttendanceTypeHandler getInstance() {
		if (attendanceTypeHandler == null) {
			attendanceTypeHandler = new AttendanceTypeHandler();
		}
		return attendanceTypeHandler;
	}
	
	IAttendanceTypeTransaction transaction = new AttendanceTypeTransactionImpl();
	
	/**
	 * 
	 * @param attendanceTypeForm
	 * Used to insert an attendaceType
	 * @returns saves attendance type
	 * @throws Exception
	 */
	public boolean saveAttendanceType(AttendanceTypeForm attendanceTypeForm)throws Exception{
		log.info("Enters into saveAttendanceType of AttendanceTypeHandler");
		//Get the data from formbean and set those to BO class object
		AttendanceType attendanceType = null;
		AttendanceTypeMandatory attendanceTypeMandatory;
		if(attendanceTypeForm!=null){
			attendanceType = new AttendanceType();
			attendanceType.setName(attendanceTypeForm.getAttendanceType());
			attendanceType.setIsDefault(attendanceTypeForm.isDefaultValue());
			attendanceType.setCreatedBy(attendanceTypeForm.getUserId());
			attendanceType.setModifiedBy(attendanceTypeForm.getUserId());
			attendanceType.setCreatedDate(new Date());
			attendanceType.setLastModifiedDate(new Date());
			attendanceType.setIsActive(true);		
			Set<AttendanceTypeMandatory> attMandatorySet= new HashSet<AttendanceTypeMandatory>();			
			int mandatorySize=attendanceTypeForm.getMandatory().length;
			String mandatoryArray[]=attendanceTypeForm.getMandatory();
			//Get the mandatory names from the array and set them to AttendanceType BO object
			for (int i = 0; i < mandatorySize; i++) {				
				attendanceTypeMandatory = new AttendanceTypeMandatory();
				attendanceTypeMandatory.setName(mandatoryArray[i]);
				attendanceTypeMandatory.setCreatedBy(attendanceTypeForm.getUserId());
				attendanceTypeMandatory.setModifiedBy(attendanceTypeForm.getUserId());
				attendanceTypeMandatory.setCreatedDate(new Date());
				attendanceTypeMandatory.setLastModifiedDate(new Date());
				attendanceTypeMandatory.setIsActive(true);
				attMandatorySet.add(attendanceTypeMandatory);
				}
			attendanceType.setAttendanceTypeMandatories(attMandatorySet);
			}
		log.info("Leaving into saveAttendanceType of AttendanceTypeHandler");
		//Send the BO object to the transactionImpl class through the interface for savivg.
			return transaction.saveAttendanceType(attendanceType);
		}
	
	/**
	 * 
	 * @returns all the attendance type available in backend
	 * @throws Exception
	 */
	
	public List<AttendanceTypeTO>getAttendanceTypesAll()throws Exception{
		log.info("Enters into getAttendanceTypesAll of AttendanceTypeHandler");
		List<AttendanceType> attendanceTypeBOList = null;
		//Request the handler to get all the attendanceType
		if(transaction !=null){
			attendanceTypeBOList = transaction.getAttendanceTypesAll();
		}
		//Send the list to helper which converts BO to TO
		if(attendanceTypeBOList!=null && !attendanceTypeBOList.isEmpty()){
			return AttendanceTypeHelper.getInstance().copyAttendanceTypeBOtoTO(attendanceTypeBOList);
		}
		log.info("Leaving into getAttendanceTypesAll of AttendanceTypeHandler");
		return null;
	}
	

	public List<AttendanceTypeTO> getAttendanceType() throws Exception {
		log.debug("inside getAttendanceType");
		IAttendanceTypeTransaction iAttendanceTypeTransaction = AttendanceTypeTransactionImpl.getInstance();
		List<AttendanceType> attendanceTypeList = iAttendanceTypeTransaction.getAttendanceType(); 
		List<AttendanceTypeTO> attendanceTypeToList = AttendanceTypeHelper.getInstance().copyAttendanceTypeBosToTos(attendanceTypeList); 
		log.debug("leaving getAttendanceType");
		return attendanceTypeToList;
	}
	
	public void getAttendanceTypeMandatory(AttendanceEntryForm attendanceEntryForm,String attenType) throws Exception {
		log.debug("inside getAttendanceType");
		IAttendanceTypeTransaction iAttendanceTypeTransaction = AttendanceTypeTransactionImpl.getInstance();
		List<AttendanceType> attendanceTypeList = iAttendanceTypeTransaction.getAttendanceType(); 
		List<AttendanceTypeTO> attendanceTypeToList = AttendanceTypeHelper.getInstance().copyAttendanceTypeBosToTosWithMandatory(attendanceTypeList); 
		Map<Integer,String> attendanceMap = new HashMap<Integer, String>();
		AttendanceTypeTO attendanceTypeTO ;
		
		Iterator<AttendanceTypeTO> itr = attendanceTypeToList.iterator();
		attendanceEntryForm.setClassMandatry(NO);
		attendanceEntryForm.setSubjectMandatory(NO);
		attendanceEntryForm.setPeriodMandatory(NO);
		attendanceEntryForm.setTeacherMandatory(NO);
		attendanceEntryForm.setBatchMandatory(NO);
		attendanceEntryForm.setActivityTypeMandatory(NO);
		while(itr.hasNext()) {
			attendanceTypeTO = itr.next();
			attendanceMap.put(attendanceTypeTO.getId(), attendanceTypeTO.getAttendanceTypeName());
			if(attenType != null && attenType.equals(String.valueOf(attendanceTypeTO.getId()))){
					Iterator<AttendanceTypeMandatoryTO> itr1 = attendanceTypeTO.getAttendanceTypeMandatoryTOList().iterator();
					AttendanceTypeMandatoryTO attendanceTypeMandatoryTO;
					
					while(itr1.hasNext()) {
						attendanceTypeMandatoryTO = itr1.next();
						if(attendanceTypeMandatoryTO.isActive()) {
							if(attendanceTypeMandatoryTO.getName().equals(CMSConstants.CLASS)){
								attendanceEntryForm.setClassMandatry(CMSConstants.YES);
							} else if(attendanceTypeMandatoryTO.getName().equals(CMSConstants.SUBJECT)) {
								attendanceEntryForm.setSubjectMandatory(CMSConstants.YES);
							} else if(attendanceTypeMandatoryTO.getName().equals(CMSConstants.PERIOD)) {
								attendanceEntryForm.setPeriodMandatory(CMSConstants.YES);
							} else if(attendanceTypeMandatoryTO.getName().equals(CMSConstants.TEACHER)) {
								attendanceEntryForm.setTeacherMandatory(CMSConstants.YES);
							} else if(attendanceTypeMandatoryTO.getName().equals(CMSConstants.BATCH_NAME)) {
								attendanceEntryForm.setBatchMandatory(CMSConstants.YES);
							} else if(attendanceTypeMandatoryTO.getName().equals(CMSConstants.ACTIVITY_TYPE)) {
								attendanceEntryForm.setActivityTypeMandatory(CMSConstants.YES);
							} 
						}	
					}
			} 
			
			if(attenType == null && attendanceTypeTO.getDefaultValue().equals(CMSConstants.YES) && attendanceTypeTO.getAttendanceTypeMandatoryTOList() != null) {
					attendanceEntryForm.setAttendanceTypeId(String.valueOf(attendanceTypeTO.getId()));
					Iterator<AttendanceTypeMandatoryTO> itr1 = attendanceTypeTO.getAttendanceTypeMandatoryTOList().iterator();
					AttendanceTypeMandatoryTO attendanceTypeMandatoryTO;
					
					while(itr1.hasNext()) {
						attendanceTypeMandatoryTO = itr1.next();
						if(attendanceTypeMandatoryTO.isActive()) {
							if(attendanceTypeMandatoryTO.getName().equals(CMSConstants.CLASS)){
								attendanceEntryForm.setClassMandatry(CMSConstants.YES);
							} else if(attendanceTypeMandatoryTO.getName().equals(CMSConstants.SUBJECT)) {
								attendanceEntryForm.setSubjectMandatory(CMSConstants.YES);
							} else if(attendanceTypeMandatoryTO.getName().equals(CMSConstants.PERIOD)) {
								attendanceEntryForm.setPeriodMandatory(CMSConstants.YES);
							} else if(attendanceTypeMandatoryTO.getName().equals(CMSConstants.TEACHER)) {
								attendanceEntryForm.setTeacherMandatory(CMSConstants.YES);
							} else if(attendanceTypeMandatoryTO.getName().equals(CMSConstants.BATCH_NAME)) {
								attendanceEntryForm.setBatchMandatory(CMSConstants.YES);
							} else if(attendanceTypeMandatoryTO.getName().equals(CMSConstants.ACTIVITY_TYPE)) {
								attendanceEntryForm.setActivityTypeMandatory(CMSConstants.YES);
							} 
						}	
					}
				}
		}
		attendanceEntryForm.setAttendanceTypes(attendanceMap);
		log.debug("leaving getAttendanceType");
		return;
	}
	
	/**
	 * 
	 * @param id
	 * @returns Deletes one record based on the Id
	 * @throws Exception
	 */
	public boolean deleteAttendanceType(int id,Boolean activate,AttendanceTypeForm attendanceTypeForm)throws Exception{
		log.info("Inside of  deleteAttendanceType of AttendanceTypeHandler");
		if(transaction !=null){
			return transaction.deleteAttendanceType(id,activate,attendanceTypeForm);
		}
		log.info("End of  deleteAttendanceType of AttendanceTypeHandler");
		return false;
	}
	
	/**
	 * Used while checking duplicate 
	 * If default value is true, then don't allow
	 */
	public AttendanceType getAttendanceTypeOnDefault(boolean defaultValue)throws Exception{
		log.info("Inside of  getAttendanceTypeOnDefault of AttendanceTypeHandler");
		if(transaction != null){
			return transaction.getAttendanceTypeOnDefault(defaultValue);
		}
		log.info("End of  getAttendanceTypeOnDefault of AttendanceTypeHandler");
		return null;
	}
	
	/**
	 * Used while editing the attendanceType
	 */
	
	public void getDetailsById(AttendanceTypeForm attendanceTypeForm)throws Exception{
		log.info("Inside of  getDetailsById of AttendanceTypeHandler");
		AttendanceType attendanceType = null;
		AttendanceTypeTO attendanceTypeTO=null;
		AttendanceTypeMandatory attendanceMandatory;
		AttendanceTypeMandatoryTO attendanceTypeMandatoryTO;
		List<AttendanceTypeMandatoryTO> mandatoryTOList=new ArrayList<AttendanceTypeMandatoryTO>();
		//Gets the data from Impl class as BO object and set them to formbean
		if(transaction !=null){
			attendanceType=transaction.getDetailsById(attendanceTypeForm.getId());
		}
		if(attendanceType!=null){
			attendanceTypeForm.setId(attendanceType.getId()!=0 ? attendanceType.getId():0);
			attendanceTypeForm.setAttendanceType(attendanceType.getName()!=null ? attendanceType.getName():null);
			attendanceTypeForm.setDefaultValue(attendanceType.getIsDefault());
			//Sets the old default value and old name in form to again check in update mode
			attendanceTypeForm.setOldDefaultValue(attendanceType.getIsDefault());
			attendanceTypeForm.setOldName(attendanceType.getName());			
			Set<AttendanceTypeMandatory> mandatorySet=attendanceType.getAttendanceTypeMandatories();
			String mandatoryArray[] = new String[mandatorySet.size()];
			int mandatoryId[]=new int[mandatorySet.size()];
			
			Set<Integer> mandatoryIdSet=new HashSet<Integer>();
			int i=0;
			if(mandatorySet!=null){
				AttendanceTypeMandatory attendanceTypeMandatory;				
				Iterator<AttendanceTypeMandatory> iterator=mandatorySet.iterator();
				while (iterator.hasNext()) {				
				attendanceTypeMandatory = iterator.next();
					if(attendanceTypeMandatory.getIsActive()==true){
						mandatoryArray[i]=attendanceTypeMandatory.getName()!=null ? attendanceTypeMandatory.getName():null;
						mandatoryId[i]=attendanceTypeMandatory.getId()!= 0 ? attendanceTypeMandatory.getId():0;
						mandatoryIdSet.add(attendanceTypeMandatory.getId()!= 0 ? attendanceTypeMandatory.getId():0);
							i++;
					}	
				}
				attendanceTypeForm.setMandatoryId(mandatoryId);
				attendanceTypeForm.setMandatory(mandatoryArray);
				attendanceTypeForm.setMandatoryIdSet(mandatoryIdSet);
			}
			//Below code are used to keep all the previous records in TO object
			attendanceTypeTO = new AttendanceTypeTO();
			attendanceTypeTO.setId(attendanceType.getId());
			attendanceTypeTO.setAttendanceTypeName(attendanceType.getName());
			attendanceTypeTO.setDefaultValue(String.valueOf(attendanceType.getIsDefault()));
			Set<AttendanceTypeMandatory> mandatoryBOSet=attendanceType.getAttendanceTypeMandatories();
			if(mandatoryBOSet!=null){
				Iterator<AttendanceTypeMandatory> iterator=mandatoryBOSet.iterator();
				while (iterator.hasNext()) {
					attendanceMandatory = iterator.next();
					attendanceTypeMandatoryTO=new AttendanceTypeMandatoryTO();
					attendanceTypeMandatoryTO.setId(attendanceMandatory.getId());
					attendanceTypeMandatoryTO.setName(attendanceMandatory.getName());					
					attendanceTypeMandatoryTO.setCreatedBy(attendanceMandatory.getCreatedBy());
					attendanceTypeMandatoryTO.setCreatedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(attendanceMandatory.getCreatedDate()), "dd-MMM-yyyy","dd/MM/yyyy"));
					mandatoryTOList.add(attendanceTypeMandatoryTO);
				}
				attendanceTypeTO.setAttendanceTypeMandatoryTOList(mandatoryTOList);
			}
			log.info("End of  getDetailsById of AttendanceTypeHandler");
			attendanceTypeForm.setAttendanceTypeTO(attendanceTypeTO);
		}
	}
	
	/**
	 * 
	 * @param attendanceTypeForm
	 * @returns the updated attendaceType
	 * @throws Exception
	 */
	
	public boolean updateAttendanceType(AttendanceTypeForm attendanceTypeForm)throws Exception{
		log.info("Inside of  updateAttendanceType of AttendanceTypeHandler");
		//Gets the updated values from formbean and setting those to BO object
		String mandatoryCreatedBy=null;
		String mandatoryCreatedDate=null;
		AttendanceType attendanceType = null;
		AttendanceTypeMandatory attendanceTypeMandatory;
		AttendanceTypeMandatoryTO mandatoryTO;
		if(attendanceTypeForm!=null){
			attendanceType = new AttendanceType();
			attendanceType.setId(attendanceTypeForm.getId());
			attendanceType.setName(attendanceTypeForm.getAttendanceType());
			attendanceType.setIsDefault(attendanceTypeForm.isDefaultValue());
			attendanceType.setModifiedBy(attendanceTypeForm.getUserId());
			attendanceType.setLastModifiedDate(new Date());
			attendanceType.setIsActive(true);
			Set<AttendanceTypeMandatory> attMandatorySet= new HashSet<AttendanceTypeMandatory>();
			int mandatorySize=attendanceTypeForm.getMandatory().length;
			String mandatoryArray[]=attendanceTypeForm.getMandatory();
			AttendanceTypeTO attendanceTypeTO = attendanceTypeForm.getAttendanceTypeTO();
			List<AttendanceTypeMandatoryTO> mandatoryTOList=attendanceTypeTO.getAttendanceTypeMandatoryTOList();			
			boolean inserted = false;
			for (int i = 0; i < mandatorySize; i++) {
				attendanceTypeMandatory = new AttendanceTypeMandatory();
				Iterator<AttendanceTypeMandatoryTO> it=mandatoryTOList.iterator();
				while (it.hasNext()) {
					mandatoryTO = it.next();					
					mandatoryCreatedBy=mandatoryTO.getCreatedBy();
					mandatoryCreatedDate=mandatoryTO.getCreatedDate();					
					// if true - will not created new record keeps the previous state
					if(mandatoryTO.getName().equals(mandatoryArray[i])) {
						attendanceTypeMandatory.setId(mandatoryTO.getId());
						attendanceTypeMandatory.setName(mandatoryTO.getName());
						attendanceTypeMandatory.setIsActive(true);
						attendanceTypeMandatory.setModifiedBy(attendanceTypeForm.getUserId());
						attendanceTypeMandatory.setLastModifiedDate(new Date());
						inserted = true;	
					}
				}
				if(!inserted) {
					// this works when new mandatory added.
					attendanceTypeMandatory.setName(mandatoryArray[i]);
					attendanceTypeMandatory.setModifiedBy(attendanceTypeForm.getUserId());
					attendanceTypeMandatory.setLastModifiedDate(new Date());
					attendanceTypeMandatory.setIsActive(true);
					if(mandatoryCreatedBy!=null && mandatoryCreatedDate!=null){
					attendanceTypeMandatory.setCreatedBy(mandatoryCreatedBy);
					attendanceTypeMandatory.setCreatedDate(CommonUtil.ConvertStringToSQLDate(mandatoryCreatedDate));
					}
					else{
						attendanceTypeMandatory.setCreatedBy(attendanceTypeForm.getUserId());
						attendanceTypeMandatory.setCreatedDate(new Date());
					}
				}
				attMandatorySet.add(attendanceTypeMandatory);
				inserted = false;
				}
			boolean deleted = true;
			Iterator<AttendanceTypeMandatoryTO> it1=mandatoryTOList.iterator();
			while (it1.hasNext()) {
				mandatoryTO = it1.next();
				deleted = true;
				for (int i = 0; i < mandatorySize; i++) {
					//Checks the old and new mandatories
					if(mandatoryTO.getName().equals(mandatoryArray[i])) {
						deleted = false;
						break;
					}
				}
				// below condition checks for deletion
				if(deleted) {
					attendanceTypeMandatory = new AttendanceTypeMandatory();
					attendanceTypeMandatory.setId(mandatoryTO.getId());
					attendanceTypeMandatory.setName(mandatoryTO.getName());
					attendanceTypeMandatory.setIsActive(false);
					attendanceTypeMandatory.setModifiedBy(attendanceTypeForm.getUserId());
					attendanceTypeMandatory.setLastModifiedDate(new Date());
					attMandatorySet.add(attendanceTypeMandatory);
				}
			}	
			attendanceType.setAttendanceTypeMandatories(attMandatorySet);
			}
		log.info("End of  updateAttendanceType of AttendanceTypeHandler");
			return transaction.updateAttendanceType(attendanceType);
	}
	
	/**
	 * Check for duplicate if attendance type is already exists
	 */
	public AttendanceType getAttendanceTypeDetailsonAttendanceType(String name)throws Exception{
		log.info("Inside of  getAttendanceTypeDetailsonAttendanceType of AttendanceTypeHandler");
		if(transaction !=null){
			return transaction.getAttendanceTypeDetailsonAttendanceType(name);
		}
		log.info("End of  getAttendanceTypeDetailsonAttendanceType of AttendanceTypeHandler");
		return null;
	}
	
}
