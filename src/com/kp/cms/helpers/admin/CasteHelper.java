package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.forms.admin.CasteForm;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.ReligionTO;

public class CasteHelper {
	/**
	 * Converts Business Objects to Transaction object of the Caste.
	 * @param castebo - Represents the Caste Business objects.
	 * @return List - Caste Transaction object.
	 */
	public static List<CasteTO> convertBOsToTos(List<Caste> casteBOList) {
		List<CasteTO> casteList = new ArrayList<CasteTO>();
		ReligionTO religionTO;
		if (casteBOList != null) {

			Iterator<Caste> iterator = casteBOList.iterator();
			while (iterator.hasNext()) {
				Caste castebo = (Caste) iterator.next();
				religionTO = new ReligionTO();
				CasteTO casteTO = new CasteTO();
				casteTO.setCasteId(castebo.getId());
				religionTO.setReligionId(castebo.getReligion().getId());
				religionTO.setReligionName(castebo.getReligion().getName());
				casteTO.setCasteName(castebo.getName());
				casteTO.setReligionto(religionTO);
				boolean feeExcemption=castebo.getIsFeeExcemption();
				if(feeExcemption){
					casteTO.setIsFeeExcemption("yes");
				}else{
					casteTO.setIsFeeExcemption("no");
				}
				casteList.add(casteTO);
			}
		}
		return casteList;
	}
	public static Caste convertTOtoBO(CasteForm casteForm,String mode) throws Exception{
		Caste caste=new Caste();
		Religion religion = new Religion();
		religion.setId(Integer.parseInt(casteForm.getReligionId()));
		caste.setId(casteForm.getCasteId());
		if(mode.equals("Update")){
			caste.setModifiedBy(casteForm.getUserId());
			caste.setLastModifiedDate(new Date());
		}else if(mode.equals("Add")){
		caste.setCreatedBy(casteForm.getUserId());
		caste.setCreatedDate(new Date());
		}
		caste.setName(casteForm.getCasteName());
		caste.setIsActive(true);
		String feeExemption=casteForm.getFeeExemption();
		if(feeExemption.equals("yes")){
			caste.setIsFeeExcemption(true);
		}else if(feeExemption.equals("no")){
			caste.setIsFeeExcemption(false);
		}
		caste.setReligion(religion);
		return caste;
	}
}
