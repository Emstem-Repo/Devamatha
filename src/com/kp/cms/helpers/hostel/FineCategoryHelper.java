package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.hostel.FineCategoryBo;
import com.kp.cms.forms.hostel.FineCategoryForm;
import com.kp.cms.to.hostel.FineCategoryTo;

public class FineCategoryHelper {
	public static volatile FineCategoryHelper fineCategoryHelper = null;
	private static Log log = LogFactory.getLog(FineCategoryHelper.class);

	public static FineCategoryHelper getInstance() {
		if (fineCategoryHelper == null) {
			fineCategoryHelper = new FineCategoryHelper();
			return fineCategoryHelper;
		}
		return fineCategoryHelper;
	}
	public List<FineCategoryTo> ConvertBosListToTosList(List<FineCategoryBo> fineCategoryBos)throws Exception{
		List<FineCategoryTo> fineCategoryTosList=new ArrayList<FineCategoryTo>();
		FineCategoryTo fineCategoryTo=null;
		Iterator<FineCategoryBo> iterator=fineCategoryBos.iterator();
		while (iterator.hasNext()) {
			fineCategoryTo=new FineCategoryTo();
			FineCategoryBo fineCategoryBo = (FineCategoryBo) iterator.next();
			fineCategoryTo.setId(fineCategoryBo.getId());
			fineCategoryTo.setName(fineCategoryBo.getName());
			fineCategoryTo.setAmount(fineCategoryBo.getAmount());
			fineCategoryTosList.add(fineCategoryTo);
			
		}
		return fineCategoryTosList;
	}
	public FineCategoryBo populateFormToFineCategoryBo(FineCategoryForm fineCategoryForm) throws Exception {
		FineCategoryBo fineCategoryBo=new FineCategoryBo();
		log.debug("inside populateFormToCourseScheme");
			fineCategoryBo.setName(fineCategoryForm.getName().trim());
			fineCategoryBo.setAmount(fineCategoryForm.getAmount());
			fineCategoryBo.setIsActive(true); 
			fineCategoryBo.setCreatedDate(new Date());
			fineCategoryBo.setLastModifiedDate(new Date());
			fineCategoryBo.setModifiedBy(fineCategoryForm.getUserId());
			fineCategoryBo.setCreatedBy(fineCategoryForm.getUserId());
			log.debug("leaving populateFormToCourseScheme");
		return fineCategoryBo;
	}
}
