package com.kp.cms.handlers.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Program;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.ProgramForm;
import com.kp.cms.helpers.admin.ProgramHelper;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.transactions.admin.IProgramTransaction;
import com.kp.cms.transactionsimpl.admin.ProgramTransactionImpl;

public class ProgramHandler {
	private static Log log = LogFactory.getLog(ProgramHandler.class);
	public static volatile ProgramHandler programHandler = null;

	public static ProgramHandler getInstance() {
		if (programHandler == null) {
			programHandler = new ProgramHandler();
			return programHandler;
		}
		return programHandler;
	}

	/**
	 * 
	 * @return list of programTO objects, will be used in UI to dispaly.
	 * @throws Exception
	 */
	public List<ProgramTO> getProgram() throws Exception {
		log.debug("inside getProgram");
		IProgramTransaction iProgramTransaction = ProgramTransactionImpl.getInstance();
		List<Program> programList = iProgramTransaction.getPrograme();
		List<ProgramTO> programToList = ProgramHelper.getInstance().copyProgramBosToTos(programList);
		log.debug("leaving getProgram");
		return programToList;
	}
	public Map<Integer,String> getProgramMap() throws Exception {
		log.debug("inside getProgram");
		IProgramTransaction iProgramTransaction = ProgramTransactionImpl.getInstance();
		List<Program> programList = iProgramTransaction.getPrograme();
		Map<Integer,String> programMap = ProgramHelper.getInstance().copyProgramBosToMap(programList);
		log.debug("leaving getProgram");
		return programMap;
	}

	/**
	 * 
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 */
	public boolean addprogram(ProgramForm programForm, String mode) throws Exception {
		log.debug("inside addprogram Action");
		IProgramTransaction iProgramTransaction = ProgramTransactionImpl.getInstance();
		//original changed variables are using for update operation. while updating no need to check the loaded record for duplication
		//so if any changes done then, checking for duplicate
		Boolean originalProgCodeNotChanged = false;
		Boolean originalProgNameNotChanged = false;
		Boolean origProgIdNotChanged = false;

		String programname = "";
		String programCode = "";
		String origProgramCode = "";
		String origProgramName = "";
		int origProgType = 0;
		int progTypeId = 0;
		
		programname = programForm.getName();
		programCode = programForm.getProgramCode();
		origProgramCode = programForm.getOrigProgramCode();
		origProgramName = programForm.getOrigProgramName();
		if(programForm.getOrigprogramTypeId() != null && !programForm.getOrigprogramTypeId().isEmpty()){
			origProgType = Integer.parseInt(programForm.getOrigprogramTypeId());
		}
				if(programForm.getProgramTypeId() != null && !programForm.getProgramTypeId().isEmpty()){
			progTypeId = Integer.parseInt(programForm.getProgramTypeId());
		}

		if(origProgType == progTypeId){
			origProgIdNotChanged = true;
		}
		
		if (programCode.trim().equalsIgnoreCase(origProgramCode.trim())) {
			originalProgCodeNotChanged = true;
		}
		if (programname.trim().equalsIgnoreCase(origProgramName.trim())) {
			originalProgNameNotChanged = true;
		}
		
		if (mode.equalsIgnoreCase("Add")) {
			originalProgNameNotChanged = false;
			originalProgCodeNotChanged = false;
			origProgIdNotChanged = false;
		}

		if (!originalProgCodeNotChanged) {
			boolean ProgramCodeExists = ProgramHandler.getInstance().isProgramCodeDuplicated(programForm.getProgramCode());
			if (ProgramCodeExists) {
				throw new DuplicateException();
			}
		}

		if (!originalProgNameNotChanged || !origProgIdNotChanged) {
			boolean ProgramNameExists = ProgramHandler.getInstance().isProgramNameDuplicated(programForm);
			if (ProgramNameExists) {
				throw new DuplicateException();
			}
		}
		Program duplprogram = iProgramTransaction.isProgramToBeActivated(programForm);
		if(duplprogram.getId()!= 0){
			programForm.setDuplPgmId(duplprogram.getId());
			throw new ReActivateException();	
		}
		
		boolean isAdded = false;
		Program program = ProgramHelper.getInstance().populateProgramDataFormForm(programForm, mode);
	
		if ("Add".equalsIgnoreCase(mode)) {
			program.setCreatedBy(programForm.getUserId());
			program.setModifiedBy(programForm.getUserId());
			program.setCreatedDate(new Date());
			program.setLastModifiedDate(new Date());
		} else // edit
		{
			program.setModifiedBy(programForm.getUserId());
			program.setLastModifiedDate(new Date());

		}
		program.setStream(programForm.getStream());
		isAdded = iProgramTransaction.addProgram(program, mode);
		log.debug("leaving addprogram");
		return isAdded;
	}

	/**
	 * handler class method for delete
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 * @throws Exception
	 */

	public boolean deleteProgram(int id, Boolean activate, ProgramForm programForm) throws Exception {
		log.debug("inside deleteProgram");
		IProgramTransaction iProgramTransaction = ProgramTransactionImpl.getInstance();
		boolean result = iProgramTransaction.deleteProgram(id, activate, programForm );
		log.debug("leaving deleteProgram");
		return result;
	}

	/**
	 * handler class method for program name duplication checking
	 * @param programForm
	 * @return true/false 
	 * @throws Exception
	 */
	
	public boolean isProgramNameDuplicated(ProgramForm programForm) throws Exception {
		log.debug("isProgramNameDuplicated");
		IProgramTransaction iProgramTransaction = ProgramTransactionImpl.getInstance();
		boolean result = iProgramTransaction.isProgramNameDuplcated(programForm);
		log.debug("leaving isProgramNameDuplicated");
		return result;
	}

	/**
	 * handler class method for program code duplication checking
	 * @param programCode
	 * @return true/false
	 * @throws Exception
	 */
	
	public boolean isProgramCodeDuplicated(String programCode) throws Exception {
		log.debug("inside isProgramCodeDuplicated");
		IProgramTransaction iProgramTransaction = ProgramTransactionImpl.getInstance();
		boolean result = iProgramTransaction.isProgramCodeDuplcated(programCode);
		log.debug("leaving isProgramCodeDuplicated");
		return result;
	}
	/**
	 * setting values to form from table(BO)
	 * @param programForm
	 * @throws Exception
	 */
	public void getProgramDetails(ProgramForm programForm) throws Exception {
		log.debug("inside getProgramDetails");
		IProgramTransaction iProgramTransaction = ProgramTransactionImpl.getInstance();
		Program program = iProgramTransaction.getProgramDetails(Integer.parseInt(programForm.getProgramId()));
		ProgramHelper.getInstance().copyBosToForm(programForm,program);
		
		log.debug("leaving getProgramDetails");
	}
	/**this method is used for view option
	 */
	public void getProgramDetailsForView(ProgramForm programForm) throws Exception {
		log.debug("inside getProgramDetailsForView");
		IProgramTransaction iProgramTransaction = ProgramTransactionImpl.getInstance();
		Program program = iProgramTransaction.getProgramDetails(Integer.parseInt(programForm.getProgramId()));
		ProgramHelper.getInstance().copyBosToFormForView(programForm,program);
		
		log.debug("leaving getProgramDetailsForView");
	}
	public ProgramTO getProgramDetails(int programId) throws Exception {
		log.debug("inside getProgramDetailsForView");
		IProgramTransaction iProgramTransaction = ProgramTransactionImpl.getInstance();
		Program program = iProgramTransaction.getProgramDetails(programId);
		ProgramTO pgmTo=ProgramHelper.getInstance().copyBosToForm(program);
		return pgmTo;
	}
	

}
