package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.TCNumber;
import com.kp.cms.bo.exam.GrievanceNumber;
import com.kp.cms.forms.admin.TCMasterForm;
import com.kp.cms.forms.exam.GrievanceMasterForm;
import com.kp.cms.to.exam.GrievanceMasterTo;
import com.kp.cms.utilities.CommonUtil;

public class GrievanceMasterHelper {
	private static volatile GrievanceMasterHelper grievanceMasterHelper = null;
	private static final Log log = LogFactory.getLog(GrievanceMasterHelper.class);
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	private GrievanceMasterHelper() {
		
	}
	/**
	 * return singleton object of GrievanceMasterHelper.
	 * @return
	 */
	public static GrievanceMasterHelper getInstance() {
		if (grievanceMasterHelper == null) {
			grievanceMasterHelper = new GrievanceMasterHelper();
		}
		return grievanceMasterHelper;
	}
	
	public List<GrievanceMasterTo> convertBOtoTOList(List<GrievanceNumber> list) throws Exception {
		List<GrievanceMasterTo> toList=new ArrayList<GrievanceMasterTo>();
		if(list!=null && !list.isEmpty()){
			Iterator<GrievanceNumber> itr=list.iterator();
			while (itr.hasNext()) {
				GrievanceNumber bo = (GrievanceNumber) itr.next();
				GrievanceMasterTo to=new GrievanceMasterTo();
				to.setId(bo.getId());
				to.setPrefix(bo.getPrefix());
				to.setStartNo(bo.getStartNo());
				to.setYear(bo.getYear());
				to.setCreatedBy(bo.getCreatedBy());
				to.setCreatedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(bo.getCreatedDate()), GrievanceMasterHelper.SQL_DATEFORMAT,GrievanceMasterHelper.FROM_DATEFORMAT));
				to.setCurrentNo(bo.getCurrentNo().toString());
				to.setSlNo(bo.getSlNo().toString());
				toList.add(to);
				to.setModifiedBy(bo.getModifiedBy());
				to.setLastModifiedDate(bo.getLastModifiedDate());
				to.setIsActive(bo.getIsActive());
			}
		}
		return toList;
	}
	public GrievanceNumber convertFormToBo(GrievanceMasterForm grievanceMasterForm,String mode) throws Exception {
		GrievanceNumber bo=new GrievanceNumber();
		if(mode.equalsIgnoreCase("update")){
			bo.setId(grievanceMasterForm.getId());
			bo.setCreatedBy(grievanceMasterForm.getOrigCreatedBy());
			bo.setCreatedDate(CommonUtil.ConvertStringToSQLDate(grievanceMasterForm.getOrigCreatedDate()));
		}else{
			bo.setCreatedBy(grievanceMasterForm.getUserId());
			bo.setCreatedDate(new Date());
		}
		bo.setModifiedBy(grievanceMasterForm.getUserId());
		bo.setLastModifiedDate(new Date());
		bo.setIsActive(true);
		bo.setPrefix(grievanceMasterForm.getPrefix());
		bo.setStartNo(Integer.parseInt(grievanceMasterForm.getStartNo()));
		bo.setYear(Integer.parseInt(grievanceMasterForm.getYear()));
		if(grievanceMasterForm.getCurrentNo()==null || grievanceMasterForm.getCurrentNo().trim().isEmpty())
			bo.setCurrentNo(bo.getStartNo());
		else
			bo.setCurrentNo(Integer.parseInt(grievanceMasterForm.getCurrentNo()));
		if(grievanceMasterForm.getSlNo()==null || grievanceMasterForm.getSlNo().trim().isEmpty())
			bo.setSlNo(1);
		else
			bo.setSlNo(Integer.parseInt(grievanceMasterForm.getSlNo()));
		return bo;
	}

}
