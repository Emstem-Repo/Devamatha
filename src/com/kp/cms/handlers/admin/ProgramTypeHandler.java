package com.kp.cms.handlers.admin;

import java.util.List;

import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.admin.UGCoursesBO;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.ProgramTypeForm;
import com.kp.cms.helpers.admin.ActivityLearningHelper;
import com.kp.cms.helpers.admin.ProgramTypeHelper;
import com.kp.cms.to.admin.ExtraCreditActivityTypeTo;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.UGCoursesTO;
import com.kp.cms.transactions.admin.IActivityLearningTransaction;
import com.kp.cms.transactions.admin.IProgramTypeTransaction;
import com.kp.cms.transactionsimpl.admin.ActivityLearningImpl;
import com.kp.cms.transactionsimpl.admin.ProgramTypeTransactionImpl;

/**
 * Manages the operations of add, edit, delete of Program Type.
 * @author 
 * 
 */
public class ProgramTypeHandler {
	private static volatile ProgramTypeHandler programTypeHandler = null;

	private ProgramTypeHandler() {
	}

	public static ProgramTypeHandler getInstance() {
		if (programTypeHandler == null) {
			programTypeHandler = new ProgramTypeHandler();
		}
		return programTypeHandler;
	}
	
	/**
	 * Add Program Type to the database.
	 * @param programTypeName - Program Type Name to be added.
	 * @return - true for successful addition, false otherwise.
	 */
	public boolean addProgramType(ProgramTypeForm programTypeForm, String mode) throws Exception{
		IProgramTypeTransaction iProgramTypeTransaction = new ProgramTypeTransactionImpl();
		boolean isProgramAdded = false;
		
		ProgramType duplProgramType = iProgramTypeTransaction.existanceCheck(programTypeForm.getProgramTypeName(), programTypeForm.getId());  
		
		if (duplProgramType != null && duplProgramType.getIsActive()) {
			throw new DuplicateException();
		}
		else if (duplProgramType != null && !duplProgramType.getIsActive())
		{
			programTypeForm.setDuplId(duplProgramType.getId());
			throw new ReActivateException();
		}				
		ProgramType programtype=ProgramTypeHelper.getInstance().createProgramTypeObject(programTypeForm);
		if (programtype != null) {
			isProgramAdded = iProgramTypeTransaction.addProgramType(programtype, mode);
		}
		return isProgramAdded;
	}

	/**
	 * Get all Program Type list from the database.
	 * @return List - Program Type transaction List object
	 */
	public List<ProgramTypeTO> getProgramType() throws Exception{
		IProgramTypeTransaction programTypeIntf = new ProgramTypeTransactionImpl();
		List<ProgramType> programBoList = programTypeIntf.getProgramType();
		List<ProgramTypeTO> getProgramList = ProgramTypeHelper
				.convertBOstoTos(programBoList);
		return getProgramList;
	}

	/**
	 * Updates the Program Type.
	 * @param programTypeId - Program Type Id to be edited.
	 * @param programTypeName - Program Type Name to be edited.
	 * @return - true , if the data is updated successfully in the database , false otherwise.
	 */
	public boolean editProgramType(int programTypeId, String programTypeName)throws Exception {
		IProgramTypeTransaction programTypeImpl = new ProgramTypeTransactionImpl();
		boolean isProgramEdited = false;
		if (programTypeImpl != null) {
			isProgramEdited = programTypeImpl.editProgramType(programTypeId,
					programTypeName);
		}
		return isProgramEdited;
	}

	/**
	 * Deletes the Program Type from the database.
	 * @param programTypeId - Program Type Id to be deleted.
	 * @return - true, if the Program Type is deleted successfully, false otherwise.
	 */
	public boolean deleteProgramType(int programTypeId, ProgramTypeForm programTypeForm, Boolean activate)throws Exception {
		IProgramTypeTransaction programTypeImpl = new ProgramTypeTransactionImpl();
		boolean isProgramDeleted = false;
		if (programTypeImpl != null) {
			isProgramDeleted = programTypeImpl.deleteProgramType(programTypeId,programTypeForm, activate);
		}
		return isProgramDeleted;
	}

	public List<ProgramTypeTO> getProgramTypeOnlineOpen() throws Exception {
		IProgramTypeTransaction programTypeIntf = new ProgramTypeTransactionImpl();
		List<ProgramType> programBoList = programTypeIntf.getProgramTypeOnlineOpen();
		List<ProgramTypeTO> getProgramList = ProgramTypeHelper
				.convertBOstoTos(programBoList);
		return getProgramList;
	}
	
	/*
	public boolean existanceCheck(String name) throws Exception
	{
	    boolean isExisting= false;
	    IProgramTypeTransaction programTypeImpl = new ProgramTypeTransactionImpl();
		ProgramType programtype=ProgramTypeHelper.getInstance().createProgramTypeObject(name);
		isExisting=programTypeImpl.existanceCheck(programtype);
		return isExisting;
	}
	*/
	
	public List<UGCoursesTO> getUgCourses() throws Exception{
		IProgramTypeTransaction programTypeIntf = new ProgramTypeTransactionImpl();
		List<UGCoursesBO> courselist = programTypeIntf.getUgCourses();
		List<UGCoursesTO> subjecttolist = ProgramTypeHelper.convertBotoTos(courselist);
		return subjecttolist;
	}

	public List<ExtraCreditActivityTypeTo> getactivityLearning()throws Exception {
		IActivityLearningTransaction transaction = ActivityLearningImpl.getInstance();
		List<ExtraCreditActivityTypeTo> list = ActivityLearningHelper.convertBotoTos(transaction.getActivities());
		return list;
	}
}
