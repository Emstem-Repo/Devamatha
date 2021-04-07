package com.kp.cms.handlers.admin;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.StudentCertificateNumber;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.MigrationMasterForm;
import com.kp.cms.helpers.admin.MigrationMasterHelper;
import com.kp.cms.to.admin.MigrationNumberTO;
import com.kp.cms.transactions.admin.IMigrationMasterTransaction;
import com.kp.cms.transactionsimpl.admin.MigrationMasterTransactionImpl;

/**
 * @author dIlIp
 *
 */
public class MigrationMasterHandler {
	
private static volatile MigrationMasterHandler migrationMasterHandler = null;
	
	IMigrationMasterTransaction transaction=MigrationMasterTransactionImpl.getInstance();
		
	private MigrationMasterHandler() {
		
	}
	
	public static MigrationMasterHandler getInstance() {
		if (migrationMasterHandler == null) {
			migrationMasterHandler = new MigrationMasterHandler();
		}
		return migrationMasterHandler;
	}
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<MigrationNumberTO> getAllMigrationNumber() throws Exception {
		List<StudentCertificateNumber> list=transaction.getAllMigrationNumber();
		return MigrationMasterHelper.getInstance().convertBOtoTOList(list);
	}
	
	
	
	/**
	 * @param migrationMasterForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addMigrationMaster(MigrationMasterForm migrationMasterForm,String mode) throws Exception {
		
		StudentCertificateNumber duplMigrationNumber = transaction.isMigrationNumberDuplcated(migrationMasterForm);  
		if(migrationMasterForm.getId()!=0 && migrationMasterForm.getId()!=duplMigrationNumber.getId()){
			if (duplMigrationNumber != null && duplMigrationNumber.getIsActive()) {
				throw new DuplicateException();
			}
			else if (duplMigrationNumber != null && !duplMigrationNumber.getIsActive())
			{
				migrationMasterForm.setDuplId(duplMigrationNumber.getId());
				throw new ReActivateException();
			}		
		}else{
			if(mode.equalsIgnoreCase("add")){
				if (duplMigrationNumber != null && duplMigrationNumber.getIsActive()) {
					throw new DuplicateException();
				}
				else if (duplMigrationNumber != null && !duplMigrationNumber.getIsActive())
				{
					migrationMasterForm.setDuplId(duplMigrationNumber.getId());
					throw new ReActivateException();
				}	
			}
		}
		StudentCertificateNumber bo=MigrationMasterHelper.getInstance().convertFormToBo(migrationMasterForm,mode);
		
		return transaction.addMigrationMaster(bo,mode);
	}
	
	/**
	 * @param id
	 * @param activate
	 * @param migrationMasterForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteMigrationMaster(int id, Boolean activate, MigrationMasterForm migrationMasterForm) throws Exception {
		
		return transaction.deleteMigrationMaster(id, activate, migrationMasterForm);
		
	}
	

}
