package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.DioceseBO;
import com.kp.cms.bo.admin.ReligionSection;
import com.kp.cms.forms.admin.DioceseForm;
import com.kp.cms.to.admin.DioceseTo;
import com.kp.cms.to.admin.SubReligionTo;

public class DioceseHelper {

	public static volatile DioceseHelper dioceseHelper = null;
	private static final Log log = LogFactory.getLog(SubReligionHelper.class);
	

	public static DioceseHelper getInstance() {
		if (dioceseHelper == null) {
			dioceseHelper = new DioceseHelper();
			return dioceseHelper;
		}
		return dioceseHelper;
	}
	
	public DioceseBO convertFormToBo(DioceseForm dsForm,String mode)
	{
		DioceseBO dioceseBO=new DioceseBO();
		if("Add".equals(mode))
		{
		dioceseBO.setName(dsForm.getDioceseName());
		dioceseBO.setCreatedDate(new Date());
		dioceseBO.setIsActive(true);
		dioceseBO.setLastModifiedDate(new Date());
		ReligionSection relsec=new ReligionSection();
		relsec.setId(Integer.parseInt(dsForm.getReligionId()));
		dioceseBO.setSubReligionId(relsec);
		}else //edit
		{
			dioceseBO.setId(dsForm.getDioceseId());
			dioceseBO.setName(dsForm.getDioceseName());
			dioceseBO.setCreatedDate(new Date());
			dioceseBO.setIsActive(true);
			dioceseBO.setLastModifiedDate(new Date());
			ReligionSection relsec=new ReligionSection();
			relsec.setId(Integer.parseInt(dsForm.getReligionId()));
			dioceseBO.setSubReligionId(relsec);
		}
		return dioceseBO;
	}
	
	public List<DioceseTo> convertBOToTo(List<DioceseBO> dioceseList)
	{
		List<DioceseTo> dioceseToList = new ArrayList<DioceseTo>();
		DioceseBO dioceseBo;
		DioceseTo dioceseTo;
		SubReligionTo subreligionTO;
		Iterator<DioceseBO> iterator = dioceseList.iterator();
		while(iterator.hasNext())
		{
			dioceseBo=new DioceseBO();
			dioceseTo=new DioceseTo();
			dioceseBo=(DioceseBO)iterator.next();
			dioceseTo.setDioceseName(dioceseBo.getName());
			dioceseTo.setDioceseId(dioceseBo.getId());
			dioceseTo.setCreatedDate(new Date());
			subreligionTO=new SubReligionTo();
			subreligionTO.setSubReligionName(dioceseBo.getSubReligionId().getName());
			subreligionTO.setSubReligionId(dioceseBo.getSubReligionId().getId());
			dioceseTo.setSubReligionTo(subreligionTO);
			
			
			dioceseToList.add(dioceseTo);
			
		}
			
		
		return dioceseToList;
	}
	public List<DioceseTo> copySubReligionBosToTos(List<DioceseBO> dioceseList) {
		List<DioceseTo> dioceseToList = new ArrayList<DioceseTo>();
		Iterator<DioceseBO> iterator = dioceseList.iterator();
		DioceseBO diocese;
		DioceseTo dioceseTO;
		SubReligionTo subReligionTO;
		while (iterator.hasNext()) {
			dioceseTO = new DioceseTo();
			subReligionTO = new SubReligionTo();
			diocese = (DioceseBO) iterator.next();
			dioceseTO.setDioceseId(diocese.getId());
			dioceseTO.setDioceseName(diocese.getName());
			subReligionTO.setSubReligionId(diocese.getSubReligionId().getId());
			subReligionTO.setSubReligionName(diocese.getSubReligionId().getName());
			dioceseTO.setSubReligionTo(subReligionTO);
			dioceseToList.add(dioceseTO);
		}
		log.debug("leaving copySubReligionBosToTos in Helper");
		return dioceseToList;
	}


	
}
