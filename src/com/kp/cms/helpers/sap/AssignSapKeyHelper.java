package com.kp.cms.helpers.sap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.sap.SapRegistration;
import com.kp.cms.to.sap.SapKeysTo;
import com.kp.cms.utilities.CommonUtil;

public class AssignSapKeyHelper {
	public static volatile AssignSapKeyHelper assignSapKeyHelper = null;
	private static Log log = LogFactory.getLog(AssignSapKeyHelper.class);
	public static AssignSapKeyHelper getInstance() {
		if (assignSapKeyHelper == null) {
			assignSapKeyHelper = new AssignSapKeyHelper();
			return assignSapKeyHelper;
		}
		return assignSapKeyHelper;
	}
	public List<SapKeysTo> convertBoToTos(List<SapRegistration> list) throws Exception{
		Map<String,Map<String,SapKeysTo>> map=sortByclassAndRegNo(list);
		List<SapKeysTo> sapKeysTos=new ArrayList<SapKeysTo>();
			if(map!=null && !map.isEmpty()){
				Iterator<Entry<String, Map<String, SapKeysTo>>> iterator=map.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<java.lang.String, java.util.Map<java.lang.String, com.kp.cms.to.sap.SapKeysTo>> entry = (Map.Entry<java.lang.String, java.util.Map<java.lang.String, com.kp.cms.to.sap.SapKeysTo>>) iterator.next();
					Map<String,SapKeysTo> map1=entry.getValue();
					if(map1!=null && !map1.isEmpty()){
						map1=CommonUtil.sortMapByKey(map1);
						Iterator<Entry<String, SapKeysTo>> iterator2=map1.entrySet().iterator();
						while (iterator2.hasNext()) {
							Map.Entry<java.lang.String, com.kp.cms.to.sap.SapKeysTo> entry2 = (Map.Entry<java.lang.String, com.kp.cms.to.sap.SapKeysTo>) iterator2.next();
							sapKeysTos.add(entry2.getValue());
						}
					}
				}
			}
		
		return sapKeysTos;
	}
	private Map<String, Map<String, SapKeysTo>> sortByclassAndRegNo(
			List<SapRegistration> list) throws Exception{
		Map<String, Map<String, SapKeysTo>> map= new HashMap<String, Map<String,SapKeysTo>>();
		if(list!=null){
			Iterator<SapRegistration> iterator=list.iterator();
			SapKeysTo sapKeysTo=null;
			Map<String,SapKeysTo> map1=null;
			while (iterator.hasNext()) {
				SapRegistration sapRegistration = (SapRegistration) iterator.next();
				if(sapRegistration.getStdId()!=null){
					sapKeysTo=new SapKeysTo();
					sapKeysTo.setId(sapRegistration.getId());
					if(sapRegistration.getSapKeyId()!=null){
						sapKeysTo.setKey(sapRegistration.getSapKeyId().getKey());
					}
					if(sapRegistration.getRegisteredDate()!=null){
						sapKeysTo.setRegisterdDate(CommonUtil.formatDates(sapRegistration.getRegisteredDate()));
					}
					if(sapRegistration.getCreatedBy()!=null){
						sapKeysTo.setCreatedBy(sapRegistration.getCreatedBy());
					}
					if(sapRegistration.getCreatedDate()!=null){
						sapKeysTo.setCreatedDate(CommonUtil.formatDates(sapRegistration.getCreatedDate()));
					}
					String name="";
					if(sapRegistration.getStdId().getRegisterNo()!=null && !sapRegistration.getStdId().getRegisterNo().isEmpty()){
						sapKeysTo.setRegNo(sapRegistration.getStdId().getRegisterNo());
					}
					if(sapRegistration.getStdId().getAdmAppln().getPersonalData().getFirstName()!=null && !sapRegistration.getStdId().getAdmAppln().getPersonalData().getFirstName().isEmpty()){
						name=name+sapRegistration.getStdId().getAdmAppln().getPersonalData().getFirstName();
					}
					if(sapRegistration.getStdId().getAdmAppln().getPersonalData().getMiddleName()!=null && !sapRegistration.getStdId().getAdmAppln().getPersonalData().getMiddleName().isEmpty()){
						name=name+sapRegistration.getStdId().getAdmAppln().getPersonalData().getMiddleName();
					}
					if(sapRegistration.getStdId().getAdmAppln().getPersonalData().getLastName()!=null && !sapRegistration.getStdId().getAdmAppln().getPersonalData().getLastName().isEmpty()){
						name=name+sapRegistration.getStdId().getAdmAppln().getPersonalData().getLastName();
					}
					sapKeysTo.setName(name);
					if(sapRegistration.getStdId().getAdmAppln().getPersonalData().getUniversityEmail()!=null && !sapRegistration.getStdId().getAdmAppln().getPersonalData().getUniversityEmail().isEmpty()){
						sapKeysTo.setUniversityMail(sapRegistration.getStdId().getAdmAppln().getPersonalData().getUniversityEmail());
					}
					if(sapRegistration.getStdId()!=null){
						sapKeysTo.setStudentId(sapRegistration.getStdId().getId());
					}
					if(sapRegistration.getStdId().getClassSchemewise().getClasses().getName()!=null){
						sapKeysTo.setClassName(sapRegistration.getStdId().getClassSchemewise().getClasses().getName());
					}
					
					if(map.containsKey(sapRegistration.getStdId().getClassSchemewise().getClasses().getName())){
						map1=map.get(sapRegistration.getStdId().getClassSchemewise().getClasses().getName());
					}else{
						map1=new HashMap<String, SapKeysTo>();
					}
					map1.put(sapRegistration.getStdId().getRegisterNo(), sapKeysTo);
					map.put(sapRegistration.getStdId().getClassSchemewise().getClasses().getName(), map1);
					//sapKeysTos.add(sapKeysTo);
				}
			}
		}
		if(map!=null && !map.isEmpty()){
			map=CommonUtil.sortMapByKey(map);
		}
		return map;
	}
}
