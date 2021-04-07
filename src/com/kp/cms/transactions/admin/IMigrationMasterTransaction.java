package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.StudentCertificateNumber;
import com.kp.cms.forms.admin.MigrationMasterForm;

public interface IMigrationMasterTransaction {
	
	List<StudentCertificateNumber> getAllMigrationNumber() throws Exception;
	
	boolean addMigrationMaster(StudentCertificateNumber bo,String mode) throws Exception;
	
	StudentCertificateNumber isMigrationNumberDuplcated(MigrationMasterForm migrationMasterForm) throws Exception;
	
	boolean deleteMigrationMaster(int id, Boolean activate, MigrationMasterForm migrationMasterForm) throws Exception;

}
