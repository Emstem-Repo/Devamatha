package com.kp.cms.handlers.attendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.exam.ExamSpecializationBO;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.attendance.SubjectGroupDetailsForm;
import com.kp.cms.helpers.attendance.SubjectGroupDetailsHelper;
import com.kp.cms.to.attendance.SubjectGroupDetailsTo;
import com.kp.cms.to.exam.ExamSpecializationTO;
import com.kp.cms.transactions.attandance.ISubjectGroupDetailsTransaction;
import com.kp.cms.transactionsimpl.attendance.SubjectGroupDetailsTransactionImpl;

public class SubjectGroupDetailsHandler {
  public static volatile SubjectGroupDetailsHandler subjectGroupDetailshandler=null;
  private static final Log log = LogFactory.getLog(SubjectGroupDetailsHandler.class);
  static{
	  subjectGroupDetailshandler=new SubjectGroupDetailsHandler();
	  
  }
 public static SubjectGroupDetailsHandler getInstance(){
	 if(subjectGroupDetailshandler == null) {
		 subjectGroupDetailshandler = new SubjectGroupDetailsHandler();
   	  return subjectGroupDetailshandler;
     }
	 return subjectGroupDetailshandler;
 }
 /**This method is used to get the Student Details from the database.
 * @param subjectGroupDetailsForm
 * @return
 * @throws Exception
 */
public List<SubjectGroupDetailsTo> getStudentDetails(SubjectGroupDetailsForm subjectGroupDetailsForm) throws Exception{
	 ISubjectGroupDetailsTransaction subjectTxImpl=new SubjectGroupDetailsTransactionImpl();
	 SubjectGroupDetailsHelper subjectDetailsHelper=SubjectGroupDetailsHelper.getInstance();
	 List<Student> studentList=subjectTxImpl.getStudentDetails(subjectGroupDetailsForm);
	 Map<Integer,String> studentWiseSpecializationMap = subjectTxImpl.getstudentWiseSpecialization(studentList);
	 List<SubjectGroupDetailsTo> studentGroupList= subjectDetailsHelper.convertBoToTO(studentList,new HashMap<Integer, Integer>(),studentWiseSpecializationMap);
	 int halfLength = 0;
		int totLength = studentGroupList.size();
		if (totLength % 2 == 0) {
			halfLength = totLength / 2;
		} else {
			halfLength = (totLength / 2) + 1;
		}
		subjectGroupDetailsForm.setHalfLength(halfLength);
		
	 return studentGroupList;
	 
 }
 
 /**
  * This method is used to add Subject Group details to database.
  * @param SubjectGroupDetailsForm
  * @throws DuplicateException
  * @throws ReActivateException
  * @throws Exception
  */
 
 public boolean addSubjectGroups(SubjectGroupDetailsForm subjectGroupDetailsForm)
	throws DuplicateException, ReActivateException, Exception {
	   log.info("call of addSubjectGroups method in SubjectGroupDetailsHandler class.");
	   boolean isAdded=false;
	   ISubjectGroupDetailsTransaction subjectTxImpl =new SubjectGroupDetailsTransactionImpl();
		isAdded=subjectTxImpl.addSubjectGroups(subjectGroupDetailsForm);
	 log.info("end of addSubjectGroups method in SubjectGroupDetailsHandler class.");
	return isAdded;
  }
 
	/**This method is used to get the SubjectGroups from the database.
	 * @param subjectGroupDetailsForm
	 * @return
	 * @throws Exception
	 */
	public List<SubjectGroupDetailsTo> getSubjectGroups(SubjectGroupDetailsForm subjectGroupDetailsForm) throws Exception {
		log.info("call of getSubjectGroups method in SubjectGroupDetailsHandler class.");
		ISubjectGroupDetailsTransaction subjectGroupTransaction = new SubjectGroupDetailsTransactionImpl();
		List<SubjectGroupDetailsTo> subjectGroupList = SubjectGroupDetailsHelper
				.convertBoToTo(subjectGroupTransaction.getSubjectGroups(subjectGroupDetailsForm));
		log.info("end of getSubjectGroups method in SubjectGroupDetailsHandler class.");
		return subjectGroupList;
	}
	/**This method is used to Edit the Subject Group Details 
	 * @param subjectGroupDetailsForm
	 * @return
	 * @throws Exception
	 */
	public List<SubjectGroupDetailsTo> getEditSubjectGroups (SubjectGroupDetailsForm subjectGroupDetailsForm) throws Exception{
		log.info("call of getSubjectGroups method in SubjectGroupDetailsHandler class.");
		ISubjectGroupDetailsTransaction subjectGroupTransaction = new SubjectGroupDetailsTransactionImpl();
		 List<Student> studentList=subjectGroupTransaction.getStudentDetails(subjectGroupDetailsForm);
		 Map<Integer,Integer> map=subjectGroupTransaction.getEditSubjectGroup(subjectGroupDetailsForm);
		 Map<Integer,String> studentWiseSpecializationMap = subjectGroupTransaction.getstudentWiseSpecialization(studentList);
		List<SubjectGroupDetailsTo> studentDetails=SubjectGroupDetailsHelper.getInstance().convertBoToTO(studentList,map,studentWiseSpecializationMap);
		//List<SubjectGroupDetailsTo> subjectGroupList=subjectGroupTransaction.getEditSubjectGroup();
		return studentDetails;
		
	}
	/**This method is used to get the SubjectGroup Names from the database.
	 * @param subjectGroupDetailsForm
	 * @return
	 * @throws Exception
	 */
	public List<SubjectGroupDetailsTo> getSubjectGroupNames(
			SubjectGroupDetailsForm subjectGroupDetailsForm) throws Exception{
		ISubjectGroupDetailsTransaction subjectGroupTransaction = new SubjectGroupDetailsTransactionImpl();
		List<SubjectGroupDetailsTo> subjectGroupNames=SubjectGroupDetailsHelper.convertBoToTo1(subjectGroupTransaction.getSubjectGroupNames(subjectGroupDetailsForm));
		return subjectGroupNames;
	}
	/**This method is used to update the Subject Groups.
	 * @param subjectGroupDetailsForm
	 * @return
	 * @throws Exception
	 */
	 public boolean updateSubjectGroups(SubjectGroupDetailsForm subjectGroupDetailsForm)
		throws DuplicateException, ReActivateException, Exception {
		   log.info("call of updateSubjectGroups method in SubjectGroupDetailsHandler class.");
		   boolean isUpdate=false;
		   ISubjectGroupDetailsTransaction subjectTxImpl =new SubjectGroupDetailsTransactionImpl();
		   isUpdate=subjectTxImpl.updateSubjectGroups(subjectGroupDetailsForm);
		 log.info("end of addSubjectGroups method in SubjectGroupDetailsHandler class.");
		return isUpdate;
	  }
	/**
	 * @param subjectGroupDetailsForm
	 * @return
	 * @throws Exception
	 */
	public List<SubjectGroupDetailsTo> getSubjectGroupsId(SubjectGroupDetailsForm subjectGroupDetailsForm) throws Exception {
		ISubjectGroupDetailsTransaction subjectGroupTransaction = new SubjectGroupDetailsTransactionImpl();
		 Map<Integer,SubjectGroup> group=subjectGroupTransaction.getsubjectGroupsId(subjectGroupDetailsForm);
		 List<SubjectGroupDetailsTo> subjectGroupMap=SubjectGroupDetailsHelper.convertBOToTO(group,subjectGroupDetailsForm);
	
		return subjectGroupMap;
	}
	/**
	 * @param subjectGroupDetailsForm 
	 * @return
	 * @throws Exception
	 */
	public List<ExamSpecializationTO> getSpecializationData(SubjectGroupDetailsForm subjectGroupDetailsForm)throws Exception {
		ISubjectGroupDetailsTransaction subjectTxImpl =new SubjectGroupDetailsTransactionImpl();
		int classSchemeWiseId = Integer.parseInt(subjectGroupDetailsForm.getClassSchemewiseId());
		List<ExamSpecializationBO> specializationBOs = subjectTxImpl.getSpecializationData(classSchemeWiseId);
		List<ExamSpecializationTO> tos = SubjectGroupDetailsHelper.convertExamSpecializationBOToTO(specializationBOs);
		return tos;
	}
	
}
