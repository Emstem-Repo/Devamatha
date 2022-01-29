package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlDisciplinaryType;
import com.kp.cms.forms.hostel.DisciplinaryTypeForm;
import com.kp.cms.to.hostel.DisciplinaryTypeTO;

public class DisciplinaryTypeHelper {
	private static final Log log = LogFactory.getLog(DisciplinaryTypeHelper.class);
	public static volatile DisciplinaryTypeHelper disciplinaryTypeHelper = null;
	public static DisciplinaryTypeHelper getInstance() {
	      if(disciplinaryTypeHelper == null) {
	    	  disciplinaryTypeHelper = new DisciplinaryTypeHelper();
	    	  return disciplinaryTypeHelper;
	      }
	      return disciplinaryTypeHelper;
	}

	/**
	 * 
	 * @param diList
	 *            this will copy the HlDisciplinaryType BO to TO
	 * @return disTypeTOList having DisciplinaryTypeTO objects.
	 */
	public List<DisciplinaryTypeTO> copyDisciplinaryTypeBosToTos(List<HlDisciplinaryType> diList) {
		log.debug("inside copyDisciplinaryTypeBosToTos");
		List<DisciplinaryTypeTO> disTypeTOList = new ArrayList<DisciplinaryTypeTO>();
		Iterator<HlDisciplinaryType> iterator = diList.iterator();
		HlDisciplinaryType disciplinaryType;
		DisciplinaryTypeTO disciplinaryTypeTO;
		while (iterator.hasNext()) {
			disciplinaryTypeTO = new DisciplinaryTypeTO();
			disciplinaryType = (HlDisciplinaryType) iterator.next();
			disciplinaryTypeTO.setId(disciplinaryType.getId());
			disciplinaryTypeTO.setName(disciplinaryType.getName());
			disTypeTOList.add(disciplinaryTypeTO);
		}
		log.debug("leaving copyDisciplinaryTypeBosToTos");
		return disTypeTOList;
	}
	/**
	 * 
	 * @param disForm
	 *        Creates HlDisciplinaryType BO from disForm.
	 * @return HlDisciplinaryType BO Object.
	 * @throws Exception may throw Number format Exception while copying.
	 */
	public HlDisciplinaryType copyDataFromFormToBO(DisciplinaryTypeForm disForm) throws Exception{
		log.debug("inside copyDataFromFormToBO");
		HlDisciplinaryType disType = new HlDisciplinaryType();
		if(disForm.getId() != 0) {
			disType.setId(disForm.getId());
		}
		disType.setName(disForm.getName());
		
		disType.setIsActive(true);
		disType.setCreatedBy(disForm.getUserId());
		disType.setModifiedBy(disForm.getUserId());
		disType.setCreatedDate(new Date());
		disType.setLastModifiedDate(new Date());
		log.debug("leaving copyDataFromFormToBO");
		return disType;
		}

}
