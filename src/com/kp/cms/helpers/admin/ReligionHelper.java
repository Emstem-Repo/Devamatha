package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.ReligionSection;
import com.kp.cms.to.admin.ReligionSectionTO;
import com.kp.cms.to.admin.ReligionTO;

public class ReligionHelper {
	
	
	public static ReligionHelper getInstance() {
		ReligionHelper religionHelper=null;
		if (religionHelper == null) {
			religionHelper = new ReligionHelper();
		}
		return religionHelper;
	}
	/**
	 * Converts Business Objects to Transaction object of the Religion.
	 * @param religionbo - Represents the Religion Business objects.
	 * @return List - Religion Transaction object.
	 */
	public static List<ReligionTO> convertBOstoTos(List<Religion> religionbolist) {
		List<ReligionTO> religionList = new ArrayList<ReligionTO>();
		if (religionbolist != null) {
			Iterator<Religion> iterator = religionbolist.iterator();
			while (iterator.hasNext()) {
				Religion religionbo = (Religion) iterator.next();
				ReligionTO religionTO = new ReligionTO();
				religionTO.setReligionId(religionbo.getId());
				religionTO.setReligionName(religionbo.getName());
				Set<ReligionSection> subreligions=religionbo.getReligionSections();
				List<ReligionSectionTO> subReligionList=new ArrayList<ReligionSectionTO>();
				if(subreligions!=null){
					Iterator<ReligionSection> subRelItr=subreligions.iterator();
					
					while (subRelItr.hasNext()) {
						ReligionSection relSec = (ReligionSection) subRelItr.next();
						if(relSec.getIsActive()!=null && relSec.getIsActive()){
						ReligionSectionTO to= new ReligionSectionTO();
						to.setId(relSec.getId());
						to.setName(relSec.getName());
						subReligionList.add(to);
						}
					}
				}
				religionTO.setSubreligions(subReligionList);
				religionList.add(religionTO);
			}
		}
		return religionList;
	}
	
	
	public Religion createReligionObject(int id,String name,String mode)
	{
		Religion religion = new Religion();
		religion.setId(id);
		religion.setName(name);
		religion.setIsActive((!mode.equalsIgnoreCase("delete")));
		religion.setCreatedDate(new Date());
		religion.setLastModifiedDate(new Date());
		return religion;
	}
	public static List<ReligionSectionTO> getReligionSectionTos(List<ReligionSection> religionbolist) {
		List<ReligionSectionTO> religionList = new ArrayList<ReligionSectionTO>();
		if (religionbolist != null) {
			Iterator<ReligionSection> iterator = religionbolist.iterator();
			while (iterator.hasNext()) {
				ReligionSection religionbo =  iterator.next();
				ReligionSectionTO religionTO = new ReligionSectionTO();
				religionTO.setId(religionbo.getId());
				religionTO.setName(religionbo.getName());
				religionList.add(religionTO);
			}
		}
		return religionList;
	}
}
