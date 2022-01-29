package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.admin.SubjectType;
import com.kp.cms.bo.exam.ExamSecondLanguageMasterBO;
import com.kp.cms.forms.admin.SubjectGroupEntryForm;
import com.kp.cms.helpers.admin.SubjectGroupHelper;
import com.kp.cms.helpers.admin.SubjectHelper;
import com.kp.cms.to.admin.SubjectGroupTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactions.admin.ISubjectGroupTransaction;
import com.kp.cms.transactionsimpl.admin.SubjectGroupTransactionImpl;
import com.kp.cms.transactionsimpl.admin.SubjectTransactionImp;

/**
 * @author kshirod.k
 * 
 */

public class SubjectGroupHandler {
	
	private static final Logger log=Logger.getLogger(SubjectGroupHandler.class);	
	public static volatile SubjectGroupHandler subjectGroupHandler = null;

	/**
	 * This method is used to create a single instance of SubjectHandler.
	 * @return unique instance of SubjectHandler.
	 */
	
	public static SubjectGroupHandler getInstance() {
		if (subjectGroupHandler == null) {
			subjectGroupHandler = new SubjectGroupHandler();
			return subjectGroupHandler;
		}
		return subjectGroupHandler;
	}

	/**
	 * This method is used to get the SubjectGroup details from SubjectGroupTransactionImpl based on courseId.
	 * Returns list of type SubjectGroupTO from SubjectGroup BO.
	 * @throws Exception
	 */
	
	public List<SubjectGroupTO> getSubjectGroupDetails(int courseId) throws Exception{
		ISubjectGroupTransaction subjectGroupTransaction = new SubjectGroupTransactionImpl();
		//call of SubjectGroupTransactionImpl and getting list of type SubjectGroup BO.
		List<SubjectGroup> subjectGroupList = subjectGroupTransaction.getSubjectGroupDetails(courseId);
		//call of SubjectGroupHelper and returns list of type SubjectGroupTO.
		List<SubjectGroupTO> newList = SubjectGroupHelper.getInstance().populateSubjectGroupBOtoTo(subjectGroupList);
		log.info("end of getSubjectGroupDetails in SubjectGroupHandler class.");
		return newList;
	}
	
	/**
	 * This method is used to get the Subject details from SubjectGroupTransactionImpl.
	 * @return list of type SubjectTO
	 * @throws Exception
	 */
	
	public List<SubjectTO> getSubjectDetails() throws Exception{
		ISubjectGroupTransaction subjectGroupTransaction = new SubjectGroupTransactionImpl();
		//call of SubjectGroupTransactionImpl and returns list of type Subject BO.
		List<Subject> sublist=subjectGroupTransaction.getSubjectDetails();
		//call of SubjectGroupHelper and return list of type SubjectTO.
		List<SubjectTO> subjectList =SubjectGroupHelper.getInstance().getSubjectDetails(sublist);
		log.info("end of getSubjectDetails in SubjectGroupHandler class.");
		return subjectList;
	}
	
	
	/**
	 * This method is used to get SubjectGroupEntry details from SubjectGroupTransactionImpl.
	 * @return list of type SubjectGroupTO to display data in jsp.
	 * @throws Exception
	 */
	
	public List<SubjectGroupTO> getSubjectGroupEntry() throws Exception{
		ISubjectGroupTransaction subjectGroupTransaction = new SubjectGroupTransactionImpl();
		//call of SubjectGroupTransactionImpl and returns list of type SubjectGroup.
		List<SubjectGroup> subGrouplist=subjectGroupTransaction.getSubjectGroup();
		//call of SubjectGroupHelper and returns list of type SubjectGroupTO.
		List<SubjectGroupTO> subgrouplist=SubjectGroupHelper.getInstance().convertBOtoTO(subGrouplist);
		log.info("end of getSubjectGroupEntry in SubjectGroupHandler class.");
		return subgrouplist;
	}
	
	/**
	 * This method is used for duplicate entry check.
	 * @param courseId
	 * @param subjectGroupName
	 * @return SubjectGroup BO instance.
	 * @throws Exception
	 */
	
	public SubjectGroup isSubjectGroupNameExist(int courseId, String subjectGroupName) throws Exception{
		ISubjectGroupTransaction subjectGroupTransaction = new SubjectGroupTransactionImpl();
		//call of SubjectGroupTransactionImpl and returns SubjectGroup instance based on courseId and subjectGroupName.
		SubjectGroup subjectGroup=subjectGroupTransaction.subjectGroupNameExist(courseId, subjectGroupName);
		log.info("end of isSubjectGroupNameExist in SubjectGroupHandler class.");
		return subjectGroup;
	}
	
	/**
	 * This method is used for saving the data to database by calling SubjectGroupTransactionImpl.
	 * @param subjectGroupEntryForm
	 * @return boolean value.
	 * @throws Exception
	 */
	
	public boolean setSubjectGroupEntry(SubjectGroupEntryForm subjectGroupEntryForm) throws Exception{
		//call of SubjectGroupHelper for converting FormtoBO and returns SubjectGroup instance.
		SubjectGroup subjectGroup=SubjectGroupHelper.getInstance().convertFormtoBO(subjectGroupEntryForm);
		ISubjectGroupTransaction subjectGroupTransaction = new SubjectGroupTransactionImpl();
		//call of SubjectGroupTransactionImpl for adding a record to database and returns boolean value.
		boolean isadded=subjectGroupTransaction.addSubjectGroupEntry(subjectGroup);
		log.info("end of setSubjectGroupEntry in SubjectGroupHandler class.");
		return isadded;
	}
	
	/**
	 * This method is used for editing based on subjectGroupId and setting to Form.
	 * @param subjectGroupId
	 * @param subjectGroupEntryForm
	 * @throws Exception
	 */
	
	public void getEditSubjectGroupEntry(int subjectGroupId,SubjectGroupEntryForm subjectGroupEntryForm) throws Exception{
		ISubjectGroupTransaction subjectGroupTransaction = new SubjectGroupTransactionImpl();
		//call of SubjectGroupTransactionImpl by passing subjectGroupId.
		SubjectGroup subjectGroup=subjectGroupTransaction.getSubjectGroupEntry(subjectGroupId);
		//call of SubjectGroupHelper and setting to form.
		SubjectGroupHelper.getInstance().editSubjectGroupEntry(subjectGroup, subjectGroupEntryForm);
		log.info("end of getEditSubjectGroupEntry in SubjectGroupHandler class.");
	}
	
	/**
	 * This method is used for updating a record in database 
	 * @param subjectGroupEntryForm
	 * @return boolean value.
	 * @throws Exception
	 */
	
	public boolean updateSubjectGroupEntry(SubjectGroupEntryForm subjectGroupEntryForm)throws Exception{
		//call of SubjectGroupHelper and returns SubjectGroup BO instance.
		SubjectGroup subjectGroup=SubjectGroupHelper.getInstance().updateFormtoBO(subjectGroupEntryForm);
		subjectGroup.setId(subjectGroupEntryForm.getSelectedSubjectGroupEntryId());
		ISubjectGroupTransaction subjectGroupTransaction = new SubjectGroupTransactionImpl();
		//call of SubjectGroupTransactionImpl by passing SubjectGroup BO instance and returns boolean value.
		boolean isUpdated=subjectGroupTransaction.updateSubjectGroupEntry(subjectGroup);
		log.info("end of updateSubjectGroupEntry in SubjectGroupHandler class.");
		return isUpdated;
	}
	
	/**
	 * This method is used for deleting a record in database and setting isActive = false.
	 * @param id
	 * @return boolean value.
	 * @throws Exception
	 */
	
	public boolean deleteSubjectGroupEntry(int subGroupId, String userId) throws Exception{
		ISubjectGroupTransaction subjectGroupTransaction = new SubjectGroupTransactionImpl();
		//call of SubjectGroupTransactionImpl and returns boolean value.
		boolean isdelete=subjectGroupTransaction.deleteSubjectGroupEntry(subGroupId, userId);
		log.info("end of deleteSubjectGroupEntry in SubjectGroupHandler class.");
		return isdelete;
	}
	
	/**
	 * This method is used for restoring a record from database by passing subjectGroupName.
	 * @param subjectGroupName
	 * @throws Exception
	 */
	
	public void reActivateSubjectGroupEntry(SubjectGroupEntryForm subjectGroupEntryForm) throws Exception{
		ISubjectGroupTransaction subjectGroupTransaction = new SubjectGroupTransactionImpl();
		//call of SubjectGroupTransactionImpl by passing subjectGroupName.
		subjectGroupTransaction.reActivateSubjectGroupEntry(subjectGroupEntryForm);
		log.info("end of reActivateSubjectGroupEntry in SubjectGroupHandler class.");
	}
	
	
	//Added by Shwetha 9Elements
	//To get second language list
	public ArrayList<KeyValueTO> getListSecondLanguage() {
		SubjectGroupTransactionImpl impl = new SubjectGroupTransactionImpl();
		ArrayList<ExamSecondLanguageMasterBO> lBO = new ArrayList(impl
				.select_ActiveOnly(ExamSecondLanguageMasterBO.class));
		return SubjectGroupHelper.getInstance().convertBOtoTO_SecondLanguage(lBO);
		
	}
	
	// Added by Balaji Sunku
	// To Retrive the subject group accourding to term no and course id and year
	/**
	 * This method is used to get the SubjectGroup details from SubjectGroupTransactionImpl based on courseId.
	 * Returns list of type SubjectGroupTO from SubjectGroup BO.
	 * @throws Exception
	 */
	
	public List<SubjectGroupTO> getSubjectGroupDetailsByCourseAndTermNo(int courseId,int year,int semesterNo) throws Exception{
		ISubjectGroupTransaction subjectGroupTransaction = new SubjectGroupTransactionImpl();
		//call of SubjectGroupTransactionImpl and getting list of type SubjectGroup BO.
		List<SubjectGroup> subjectGroupList = subjectGroupTransaction.getSubjectGroupDetailsByCourseAndTermNo(courseId,year,semesterNo);
		//call of SubjectGroupHelper and returns list of type SubjectGroupTO.
		List<SubjectGroupTO> newList = SubjectGroupHelper.getInstance().populateSubjectGroupBOtoTo(subjectGroupList);
		log.info("end of getSubjectGroupDetails in SubjectGroupHandler class.");
		return newList;
	}
	
	/**
	 * @param programTypeId
	 * @param ProgramId
	 * @return
	 * @throws Exception
	 */
	public List<SubjectGroupTO> getSubjectGroup(String programTypeId , String programId) throws Exception{
		ISubjectGroupTransaction subjectGroupTransaction = new SubjectGroupTransactionImpl();
		//call of SubjectGroupTransactionImpl and returns list of type SubjectGroup.
		List<SubjectGroup> subGrouplist=subjectGroupTransaction.getSubjectGroupList(programTypeId,programId);
		//call of SubjectGroupHelper and returns list of type SubjectGroupTO.
		List<SubjectGroupTO> subgrouplist=SubjectGroupHelper.getInstance().convertBOtoTO(subGrouplist);
		log.info("end of getSubjectGroupEntry in SubjectGroupHandler class.");
		return subgrouplist;
	}
}
