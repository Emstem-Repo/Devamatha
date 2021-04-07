package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.ApplicationFee;
import com.kp.cms.bo.admin.EducationStream;
import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.admin.ReligionSection;
import com.kp.cms.forms.admin.ApplicationFeeForm;
import com.kp.cms.forms.admin.EducationStreamForm;
import com.kp.cms.to.admin.ApplicationFeeTO;
import com.kp.cms.to.admin.EducationStreamTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.ReligionSectionTO;

public class EducationStreamHelper 
{
	
	public static List<EducationStreamTO> convertBOsToTos(List<EducationStream> educstreamBOList)
	{
		List<EducationStreamTO> educstreamList = new ArrayList<EducationStreamTO>();
		if (educstreamBOList != null)
		{
			Iterator<EducationStream> iterator = educstreamBOList.iterator();
			while (iterator.hasNext())
			{
				EducationStream educstreambo = (EducationStream) iterator.next();
				EducationStreamTO educstreamTO = new EducationStreamTO();
				educstreamTO.setId(educstreambo.getId());
				educstreamTO.setName(educstreambo.getName());
				educstreamList.add(educstreamTO);
			}
		}
		return educstreamList;
	}
	
	
	public static EducationStream convertTOtoBO(EducationStreamForm educstreamform,String mode) throws Exception
	{
		EducationStream educstream=new EducationStream();
		if(mode.equals("Update"))
		{
			educstream.setId(educstreamform.getEducstreamId());
			educstream.setModifiedBy(educstreamform.getUserId());
			educstream.setLastModifiedDate(new Date());
		}
		else if(mode.equals("Add"))
		{
			educstream.setModifiedBy(educstreamform.getUserId());
			educstream.setLastModifiedDate(new Date());
			educstream.setCreatedBy(educstreamform.getUserId());
			educstream.setCreatedDate(new Date());
			//appFee.setIsActive(true);			
		}
		educstream.setIsActive(true);
		educstream.setName(educstreamform.getEducstreamName());
		return educstream;
	}

}
