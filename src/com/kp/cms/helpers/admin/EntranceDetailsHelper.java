package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Entrance;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.forms.admin.EntranceDetailsForm;
import com.kp.cms.handlers.admin.EntranceDetailsHandler;
import com.kp.cms.to.admin.EntrancedetailsTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;

public class EntranceDetailsHelper {
	public static volatile EntranceDetailsHelper entranceDetailsHelper = null;
    public static final Log log = LogFactory.getLog(EntranceDetailsHelper.class);
	public static EntranceDetailsHelper getInstance() {
		if (entranceDetailsHelper == null) {
			entranceDetailsHelper = new EntranceDetailsHelper();
			return entranceDetailsHelper;
		}
		return entranceDetailsHelper;
	}
	
	/**
	 * copying Bo values to TO for UI display
	 * @param termsConditionList
	 * @param id
	 * @return
	 */
	public List<EntrancedetailsTO> copyEntranceDetailsBosToTos(List<Entrance> entranceList) {
		List<EntrancedetailsTO> entranceToList = new ArrayList<EntrancedetailsTO>();
		Iterator<Entrance> iterator = entranceList.iterator();
		Entrance entrance;
		EntrancedetailsTO entrancedetailsTO;
		ProgramTypeTO programTypeTo;
		ProgramTO programTo;

		while (iterator.hasNext()) {
			//entrance = new Entrance();
			entrancedetailsTO = new EntrancedetailsTO();
			programTo = new ProgramTO();
			programTypeTo = new ProgramTypeTO();
			entrance = iterator.next();
			
			programTypeTo.setProgramTypeId(entrance.getProgram().getProgramType().getId());
			programTypeTo.setProgramTypeName(entrance.getProgram().getProgramType().getName());
			programTo.setProgramTypeTo(programTypeTo);
			programTo.setId(entrance.getProgram().getId());
			programTo.setName(entrance.getProgram().getName());
			entrancedetailsTO.setProgramTO(programTo);
			entrancedetailsTO.setName(entrance.getName());
			entrancedetailsTO.setId(entrance.getId());

			
			entranceToList.add(entrancedetailsTO);

		}
		log.error("ending of copyEntranceDetailsBosToTos method in EntranceDetailsHelper");
		return entranceToList;
	}

	/**
	 * setting form values to BO
	 * @param termsConditionForm
	 * @return
	 * @throws Exception
	 */
	public Entrance populateEntranceDetails(EntranceDetailsForm enForm) throws Exception {
		Entrance entrance = new Entrance ();
		entrance.setId(enForm.getId());
		if(enForm.getProgramId()!= null && !enForm.getProgramId().trim().isEmpty()){
			Program program = new Program();
			program.setId(Integer.parseInt(enForm.getProgramId()));
			entrance.setProgram(program);
		}
		entrance.setName(enForm.getName());
		entrance.setId(enForm.getId());
		entrance.setIsActive(true);
		entrance.setCreatedDate(new Date());
		entrance.setLastModifiedDate(new Date());
		entrance.setCreatedBy(enForm.getUserId());
		entrance.setModifiedBy(enForm.getUserId());
		log.error("ending of populateEntranceDetails method in EntranceDetailsHelper");
		return entrance;
	}

	
}
