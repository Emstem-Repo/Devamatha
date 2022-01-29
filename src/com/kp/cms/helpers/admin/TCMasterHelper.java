package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.TCNumber;
import com.kp.cms.bo.admin.TCPrefix;
import com.kp.cms.forms.admin.TCMasterForm;
import com.kp.cms.to.admin.TCNumberTO;
import com.kp.cms.to.admin.TCPrefixTO;
import com.kp.cms.utilities.CommonUtil;

public class TCMasterHelper {
	/**
	 * Singleton object of TCMasterHelper
	 */
	private static volatile TCMasterHelper tCMasterHelper = null;
	private static final Log log = LogFactory.getLog(TCMasterHelper.class);
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	private TCMasterHelper() {
		
	}
	/**
	 * return singleton object of TCMasterHelper.
	 * @return
	 */
	public static TCMasterHelper getInstance() {
		if (tCMasterHelper == null) {
			tCMasterHelper = new TCMasterHelper();
		}
		return tCMasterHelper;
	}
	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<TCNumberTO> convertBOtoTOList(List<TCNumber> list) throws Exception {
		List<TCNumberTO> toList=new ArrayList<TCNumberTO>();
		if(list!=null && !list.isEmpty()){
			Iterator<TCNumber> itr=list.iterator();
			while (itr.hasNext()) {
				TCNumber bo = (TCNumber) itr.next();
				TCNumberTO to=new TCNumberTO();
				to.setId(bo.getId());
				to.setCollegeName(bo.getTcFor());
				to.setPrefix(bo.getPrefix());
				to.setStartNo(bo.getStartNo());
				to.setType(bo.getType());
				to.setYear(bo.getYear());
				to.setCreatedBy(bo.getCreatedBy());
				to.setCreatedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(bo.getCreatedDate()), TCMasterHelper.SQL_DATEFORMAT,TCMasterHelper.FROM_DATEFORMAT));
				to.setCurrentNo(bo.getCurrentNo().toString());
				to.setSlNo(bo.getSlNo().toString());
				toList.add(to);
				to.setModifiedBy(bo.getModifiedBy());
				to.setLastModifiedDate(bo.getLastModifiedDate());
				to.setIsActive(bo.getIsActive());
			}
		}
		return toList;
	}
	/**
	 * @param tcMasterForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public TCNumber convertFormToBo(TCMasterForm tcMasterForm,String mode) throws Exception {
		TCNumber bo=new TCNumber();
		if(mode.equalsIgnoreCase("update")){
			bo.setId(tcMasterForm.getId());
			bo.setCreatedBy(tcMasterForm.getOrigCreatedBy());
			bo.setCreatedDate(CommonUtil.ConvertStringToSQLDate(tcMasterForm.getOrigCreatedDate()));
		}else{
			bo.setCreatedBy(tcMasterForm.getUserId());
			bo.setCreatedDate(new Date());
		}
		bo.setTcFor(tcMasterForm.getCollegeName());
		bo.setModifiedBy(tcMasterForm.getUserId());
		bo.setLastModifiedDate(new Date());
		bo.setIsActive(true);
		bo.setPrefix(tcMasterForm.getPrefix());
		bo.setStartNo(Integer.parseInt(tcMasterForm.getStartNo()));
		bo.setType(tcMasterForm.getType());
		bo.setYear(Integer.parseInt(tcMasterForm.getYear()));
		if(tcMasterForm.getCurrentNo()==null || tcMasterForm.getCurrentNo().trim().isEmpty())
			bo.setCurrentNo(bo.getStartNo());
		else
			bo.setCurrentNo(Integer.parseInt(tcMasterForm.getCurrentNo()));
		if(tcMasterForm.getSlNo()==null || tcMasterForm.getSlNo().trim().isEmpty())
			bo.setSlNo(1);
		else
			bo.setSlNo(Integer.parseInt(tcMasterForm.getSlNo()));
		return bo;
	}
	public List<TCPrefixTO> convertTcPrefix(List<TCPrefix> tcPrefixBo) {
        List<TCPrefixTO> groupList = new ArrayList<TCPrefixTO>();
        if(tcPrefixBo != null)
        {
        	Iterator <TCPrefix> iterator=tcPrefixBo.iterator();
			while(iterator.hasNext())
			{
				TCPrefix tcPreafixBo = (TCPrefix)iterator.next();
				TCPrefixTO tcPreafixTo = new TCPrefixTO();
				tcPreafixTo.setId(tcPreafixBo.getId());
				tcPreafixTo.setName(tcPreafixBo.getName());
				tcPreafixTo.setCode(tcPreafixBo.getCode());
                groupList.add(tcPreafixTo);
            }
        }
        return groupList;
    }
}
