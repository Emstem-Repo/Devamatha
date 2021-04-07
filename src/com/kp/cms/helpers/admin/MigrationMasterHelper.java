package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.StudentCertificateNumber;
import com.kp.cms.forms.admin.MigrationMasterForm;
import com.kp.cms.to.admin.MigrationNumberTO;
import com.kp.cms.utilities.CommonUtil;

/**
 * @author dIlIp
 *
 */
public class MigrationMasterHelper {
	
	private static volatile MigrationMasterHelper migrationMasterHelper = null;
	
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	
	private MigrationMasterHelper() {
		
	}
	
	public static MigrationMasterHelper getInstance() {
		if (migrationMasterHelper == null) {
			migrationMasterHelper = new MigrationMasterHelper();
		}
		return migrationMasterHelper;
	}
	
	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<MigrationNumberTO> convertBOtoTOList(List<StudentCertificateNumber> list) throws Exception {
		List<MigrationNumberTO> toList=new ArrayList<MigrationNumberTO>();
		if(list!=null && !list.isEmpty()){
			Iterator<StudentCertificateNumber> itr=list.iterator();
			while (itr.hasNext()) {
				StudentCertificateNumber bo = (StudentCertificateNumber) itr.next();
				MigrationNumberTO to=new MigrationNumberTO();
				to.setId(bo.getId());
				to.setPrefix(bo.getPrefix());
				to.setStartNo(bo.getStartNo());
				to.setType(bo.getType());
				to.setCreatedBy(bo.getCreatedBy());
				to.setCreatedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(bo.getCreatedDate()), MigrationMasterHelper.SQL_DATEFORMAT,MigrationMasterHelper.FROM_DATEFORMAT));
				to.setCurrentNo(bo.getCurrentNo().toString());
				toList.add(to);
				to.setModifiedBy(bo.getModifiedBy());
				to.setLastModifiedDate(bo.getLastModifiedDate());
				to.setIsActive(bo.getIsActive());
			}
		}
		return toList;
	}

	/**
	 * @param migrationMasterForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public StudentCertificateNumber convertFormToBo(MigrationMasterForm migrationMasterForm,String mode) throws Exception {
		
		StudentCertificateNumber bo=new StudentCertificateNumber();
		if(mode.equalsIgnoreCase("update")){
			bo.setId(migrationMasterForm.getId());
			bo.setCreatedBy(migrationMasterForm.getOrigCreatedBy());
			bo.setCreatedDate(CommonUtil.ConvertStringToSQLDate(migrationMasterForm.getOrigCreatedDate()));
		}else{
			bo.setCreatedBy(migrationMasterForm.getUserId());
			bo.setCreatedDate(new Date());
		}
		bo.setModifiedBy(migrationMasterForm.getUserId());
		bo.setLastModifiedDate(new Date());
		bo.setIsActive(true);
		bo.setPrefix(migrationMasterForm.getPrefix());
		bo.setStartNo(Integer.parseInt(migrationMasterForm.getStartNo()));
		bo.setCurrentNo(bo.getStartNo());
		bo.setType(migrationMasterForm.getType());

		
		return bo;
	}

}
