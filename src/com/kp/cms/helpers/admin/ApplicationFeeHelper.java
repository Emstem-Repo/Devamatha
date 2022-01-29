package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.ApplicationFee;
import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.admin.ReligionSection;
import com.kp.cms.forms.admin.ApplicationFeeForm;
import com.kp.cms.forms.admin.CasteForm;
import com.kp.cms.to.admin.ApplicationFeeTO;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.ReligionSectionTO;
import com.kp.cms.to.admin.ReligionTO;
import com.kp.cms.utilities.HibernateUtil;


public class ApplicationFeeHelper
{
	public static List<ApplicationFeeTO> convertBOsToTos(List<ApplicationFee> appfeeBOList)
	{
		List<ApplicationFeeTO> appfeeList = new ArrayList<ApplicationFeeTO>();
		ReligionSectionTO religionSectionTO;
		ProgramTypeTO programTypeTO;
		if (appfeeBOList != null)
		{
			Iterator<ApplicationFee> iterator = appfeeBOList.iterator();
			while (iterator.hasNext())
			{
				ApplicationFee appfeebo = (ApplicationFee) iterator.next();
				religionSectionTO = new ReligionSectionTO();
				programTypeTO=new ProgramTypeTO();
				ApplicationFeeTO appfeeTO = new ApplicationFeeTO();
				appfeeTO.setAppfeeId(appfeebo.getId());
				religionSectionTO.setId(appfeebo.getReligionSection().getId());
				religionSectionTO.setName(appfeebo.getReligionSection().getName());
				programTypeTO.setProgramTypeId(appfeebo.getProgramType().getId());
				programTypeTO.setProgramTypeName(appfeebo.getProgramType().getName());
				appfeeTO.setYear(appfeebo.getAccademicYear());
				appfeeTO.setAmount(appfeebo.getAmount());
				appfeeTO.setReligionSectionTO(religionSectionTO);
				appfeeTO.setProgramType(programTypeTO);
				appfeeList.add(appfeeTO);
			}
		}
		return appfeeList;
	}
	
	
	public static ApplicationFee convertTOtoBO(ApplicationFeeForm appFeeForm,String mode) throws Exception
	{
		ApplicationFee appFee=new ApplicationFee();
		ReligionSection relsec=new ReligionSection();
		ProgramType programType=new ProgramType();
		relsec.setId(Integer.parseInt(appFeeForm.getSubReligionId()));
		programType.setId(Integer.parseInt(appFeeForm.getProgramTypeId()));
		if(mode.equals("Update"))
		{
			appFee.setId(appFeeForm.getAppfeeId());
			appFee.setModifiedBy(appFeeForm.getUserId());
			appFee.setLastModifiedDate(new Date());
		}
		else if(mode.equals("Add"))
		{
			appFee.setModifiedBy(appFeeForm.getUserId());
			appFee.setLastModifiedDate(new Date());
			appFee.setCreatedBy(appFeeForm.getUserId());
			appFee.setCreatedDate(new Date());
			//appFee.setIsActive(true);			
		}
		appFee.setIsActive(true);
		appFee.setAmount(appFeeForm.getAmount());
		appFee.setAccademicYear(appFeeForm.getYear());
		appFee.setProgramType(programType);
		appFee.setReligionSection(relsec);
		return appFee;
	}
	

}
