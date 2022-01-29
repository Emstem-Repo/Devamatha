package com.kp.cms.helpers.admin;


import java.util.ArrayList;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


import com.kp.cms.bo.admin.DioceseBO;
import com.kp.cms.bo.admin.ParishBO;
import com.kp.cms.forms.admin.ParishForm;
import com.kp.cms.forms.admin.SubReligionForm;
import com.kp.cms.to.admin.DioceseTo;
import com.kp.cms.to.admin.ParishTo;



import common.Logger;

public class ParishHelper {

	private static final Logger log = Logger.getLogger(ParishHelper.class);
	private static volatile ParishHelper ParishHelper = null;
	public static ParishHelper getInstance()
    {
        if(ParishHelper == null)
        {
        	ParishHelper = new ParishHelper();
        }
        return ParishHelper;
    }
	
	public List<ParishTo> convertBOToTo(List<ParishBO> parishList)
	{
		List<ParishTo> parishToList = new ArrayList<ParishTo>();
		ParishBO parishBo;
		ParishTo parishTo;
		DioceseTo dioceseTO;
		Iterator<ParishBO> iterator = parishList.iterator();
		while(iterator.hasNext())
		{
			parishBo=new ParishBO();
			parishTo=new ParishTo();
			parishBo=(ParishBO)iterator.next();
			parishTo.setParishName(parishBo.getName());
			parishTo.setParishId(parishBo.getId());
			parishTo.setCreatedDate(new Date());
			dioceseTO=new DioceseTo();
			dioceseTO.setDioceseName(parishBo.getDioceseId().getName());
			dioceseTO.setDioceseId(parishBo.getDioceseId().getId());
			parishTo.setDioceseTo(dioceseTO);
			
			
			parishToList.add(parishTo);
			
		}
			
		
		return parishToList;
	}
	
	public ParishBO convertFormToBo(ParishForm dsForm,String mode)
	{
		ParishBO parishBO=new ParishBO();
		SubReligionForm sdsform=new SubReligionForm();
		if("Add".equals(mode))
		{
			parishBO.setName(dsForm.getParishName());
			parishBO.setCreatedDate(new Date());
			parishBO.setIsActive(true);
			parishBO.setLastModifiedDate(new Date());
		DioceseBO diocese=new DioceseBO();
		diocese.setId(dsForm.getDioceseId());
		parishBO.setDioceseId(diocese);
		}else //edit
		{
			parishBO.setId(dsForm.getParishId());
			parishBO.setName(dsForm.getParishName());
			parishBO.setCreatedDate(new Date());
			parishBO.setIsActive(true);
			parishBO.setLastModifiedDate(new Date());
			DioceseBO diocese=new DioceseBO();
			diocese.setId(dsForm.getDioceseId());
			parishBO.setDioceseId(diocese);
		
	}
		return parishBO;
}
	public List<ParishTo> copyParishBosToTos(List<ParishBO> parishList) {
		List<ParishTo> parishToList = new ArrayList<ParishTo>();
		Iterator<ParishBO> iterator = parishList.iterator();
		ParishBO parish;
		ParishTo parishTO;
		DioceseTo dioceseTO;
		while (iterator.hasNext()) {
			parishTO = new ParishTo();
			dioceseTO = new DioceseTo();
			parish = (ParishBO) iterator.next();
			parishTO.setParishId(parish.getId());
			parishTO.setParishName(parish.getName());
			dioceseTO.setDioceseId(parish.getDioceseId().getId());
			dioceseTO.setDioceseName(parish.getDioceseId().getName());
			parishTO.setDioceseTo(dioceseTO);
			parishToList.add(parishTO);
		}
		log.debug("leaving copySubReligionBosToTos in Helper");
		return parishToList;
	}

	
}
