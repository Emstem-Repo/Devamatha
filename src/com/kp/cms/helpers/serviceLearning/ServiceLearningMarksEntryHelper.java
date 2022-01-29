package com.kp.cms.helpers.serviceLearning;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.serviceLearning.DepartmentNameEntry;
import com.kp.cms.bo.serviceLearning.ServiceLearningActivityBo;
import com.kp.cms.bo.serviceLearning.ServiceLearningMarksEntryBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.to.serviceLearning.DepartmentNameEntryTo;
import com.kp.cms.to.serviceLearning.ProgrammeEntryTo;
import com.kp.cms.to.serviceLearning.ServiceLearningActivityTo;
import com.kp.cms.transactions.serviceLearning.IServiceLearningMarksEntryTransaction;
import com.kp.cms.transactionsimpl.serviceLearning.ServiceLearningMarksEntryTransactionImpl;

public class ServiceLearningMarksEntryHelper {
	
	private static final Log log = LogFactory.getLog(ServiceLearningMarksEntryHelper.class);
	
	private static ServiceLearningMarksEntryHelper serviceLearningMarksEntryHelper= null;
	public static ServiceLearningMarksEntryHelper getInstance() {
		      if(serviceLearningMarksEntryHelper == null) {
		    	  serviceLearningMarksEntryHelper = new ServiceLearningMarksEntryHelper();
		    	  return serviceLearningMarksEntryHelper;
		      }
	return serviceLearningMarksEntryHelper;
	}
	
	public static List<ServiceLearningActivityTo> convertBOsToTos(List<ServiceLearningActivityBo> serviceLearningActivityBoList) throws ApplicationException {
		List<ServiceLearningActivityTo> serviceLearningActivityToList = new ArrayList<ServiceLearningActivityTo>();
		IServiceLearningMarksEntryTransaction transaction = new ServiceLearningMarksEntryTransactionImpl();
		if (serviceLearningActivityBoList != null) {

			Iterator<ServiceLearningActivityBo> iterator = serviceLearningActivityBoList.iterator();
			while (iterator.hasNext()) {
				ServiceLearningActivityBo serviceLearningActivityBo = (ServiceLearningActivityBo) iterator.next();
				DepartmentNameEntryTo departmentNameEntryTo = new DepartmentNameEntryTo();
				ProgrammeEntryTo programmeEntryTo = new ProgrammeEntryTo();
				ServiceLearningActivityTo serviceLearningActivityTo = new ServiceLearningActivityTo();
				ClassesTO classesTO = new ClassesTO();
				serviceLearningActivityTo.setLearningAndActivity(serviceLearningActivityBo.getLearningAndActivity());
				serviceLearningActivityTo.setStudentId(String.valueOf(serviceLearningActivityBo.getStudent().getId()));
				serviceLearningActivityTo.setStudentName(serviceLearningActivityBo.getStudent().getAdmAppln().getPersonalData().getFirstName());
				serviceLearningActivityTo.setRegisterNo(serviceLearningActivityBo.getStudent().getRegisterNo());
				programmeEntryTo.setProgrammeName(serviceLearningActivityBo.getProgrammeEntryBo().getProgrammeName());
				programmeEntryTo.setId(String.valueOf(serviceLearningActivityBo.getProgrammeEntryBo().getId()));
				serviceLearningActivityTo.setProgrammeEntryTo(programmeEntryTo);
				departmentNameEntryTo.setDepartmentName(serviceLearningActivityBo.getDepartmentNameEntry().getDepartmentName());
				departmentNameEntryTo.setId(String.valueOf(serviceLearningActivityBo.getDepartmentNameEntry().getId()));
				serviceLearningActivityTo.setDepartmentNameEntryTo(departmentNameEntryTo);
				serviceLearningActivityTo.setAttendedHours(serviceLearningActivityBo.getAttendedHours());
				
				serviceLearningActivityTo.setClassname(serviceLearningActivityBo.getClasses().getCourse().getName()+"_Sem"+serviceLearningActivityBo.getClasses().getTermNumber());
				ServiceLearningMarksEntryBo serviceLearningMarksEntryBo = transaction.getServiceLearningmark(serviceLearningActivityBo.getStudent().getId(),serviceLearningActivityBo.getProgrammeEntryBo().getId(),serviceLearningActivityBo.getDepartmentNameEntry().getId());
				if(serviceLearningMarksEntryBo!=null){
					serviceLearningActivityTo.setMark(serviceLearningMarksEntryBo.getMark());
					serviceLearningActivityTo.setRemark(serviceLearningMarksEntryBo.getRemark());
					serviceLearningActivityTo.setId(String.valueOf(serviceLearningMarksEntryBo.getId()));
				}
				serviceLearningActivityToList.add(serviceLearningActivityTo);
				

			}
		}
		return serviceLearningActivityToList;
	}

}
