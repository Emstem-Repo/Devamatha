package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.exam.ConsolidatedSubjectStream;
import com.kp.cms.forms.exam.ConsolidatedSubjectStreamForm;
import com.kp.cms.to.exam.ConsolidatedSubjectStreamTO;

public class ConsolidatedSubjectStreamHelper 
{
	private static volatile ConsolidatedSubjectStreamHelper obj;
	
	public static ConsolidatedSubjectStreamHelper getInstance()
	{
		if(obj == null)
		{
			obj = new ConsolidatedSubjectStreamHelper();
		}
		return obj;
	}
	
	public List<ConsolidatedSubjectStreamTO> convertBOToTO(List<ConsolidatedSubjectStream> consolidatedSubjectStreams) throws Exception
	{
		Iterator<ConsolidatedSubjectStream> it = consolidatedSubjectStreams.iterator();
		List<ConsolidatedSubjectStreamTO> streamNamesTO = new ArrayList<ConsolidatedSubjectStreamTO>();
		
		while(it.hasNext())
		{
			ConsolidatedSubjectStreamTO obj_TO = new ConsolidatedSubjectStreamTO();
			ConsolidatedSubjectStream obj_BO = it.next();
			obj_TO.setId(obj_BO.getId());
			obj_TO.setStreamName(obj_BO.getStreamName());			
			streamNamesTO.add(obj_TO);
		}
		return streamNamesTO;
	}
	
	public ConsolidatedSubjectStream convertFormToBO(ConsolidatedSubjectStreamForm consolidatedSubjectStreamForm) throws Exception
	{
		ConsolidatedSubjectStream consolidatedSubjectStream = new ConsolidatedSubjectStream();
		consolidatedSubjectStream.setId(consolidatedSubjectStreamForm.getId());
		consolidatedSubjectStream.setStreamName(consolidatedSubjectStreamForm.getStreamName());
		consolidatedSubjectStream.setIsActive(true);
		
		return consolidatedSubjectStream;
	}
}
