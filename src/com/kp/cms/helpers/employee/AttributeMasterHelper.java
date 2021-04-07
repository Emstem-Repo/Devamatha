package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpAttribute;
import com.kp.cms.forms.employee.AttributeMasterForm;
import com.kp.cms.to.employee.EmpAttributeTO;

public class AttributeMasterHelper {
		private static final Log log = LogFactory.getLog(AttributeMasterHelper.class);
		public static volatile AttributeMasterHelper attributeMasterHelper = null;
		
		public static AttributeMasterHelper getInstance() {
			if (attributeMasterHelper == null) {
				attributeMasterHelper = new AttributeMasterHelper();
				return attributeMasterHelper;
			}
			return attributeMasterHelper;
		}
		
		/**
		 * 
		 * @param attributeList
		 *            this will copy the EmpAttribute BO to TO
		 * @return attributeTOList having EmpAttributeTO objects.
		 */
		public List<EmpAttributeTO> copyAttributeBosToTos(List<EmpAttribute> attributeList) { 
			log.debug("inside copyAttributeBosToTos");
			List<EmpAttributeTO> attributeTOList = new ArrayList<EmpAttributeTO>();
			Iterator<EmpAttribute> iterator = attributeList.iterator();
			EmpAttribute empAttribute;
			EmpAttributeTO attributeTO;
			while (iterator.hasNext()) {
				attributeTO = new EmpAttributeTO();
				empAttribute = (EmpAttribute) iterator.next();
				attributeTO.setId(empAttribute.getId());
				if(empAttribute.getName()!= null && !empAttribute.getName().trim().isEmpty()){
					attributeTO.setName(empAttribute.getName());
				}
				if(empAttribute.getIsEmployee()!= null){
					attributeTO.setIsEmployee(empAttribute.getIsEmployee());
					if(attributeTO.getIsEmployee()){
						attributeTO.setValue("Employee");
					}
					else
					{
						attributeTO.setValue("Student");
					}
				}
				
				attributeTOList.add(attributeTO);
			}
			log.debug("leaving copyAttributeBosToTos");
			return attributeTOList;
		}
		/**
		 * 
		 * @param attributeMasterForm
		 * @return
		 * @throws Exception
		 */
		public EmpAttribute copyDataFromFormToBO(AttributeMasterForm attributeMasterForm) throws Exception{
			log.debug("inside copyDataFromFormToBO");
			EmpAttribute empAttribute = new EmpAttribute();
			if(attributeMasterForm.getId() != 0) {
				empAttribute.setId(attributeMasterForm.getId());
			}
			
			if(attributeMasterForm.getName()!= null && !attributeMasterForm.getName().trim().isEmpty()){
				empAttribute.setName(attributeMasterForm.getName());
			}
			if(attributeMasterForm.getIsEmployee()!= null && !attributeMasterForm.getIsEmployee().trim().isEmpty()){
				empAttribute.setIsEmployee(Boolean.valueOf(attributeMasterForm.getIsEmployee()));
			}
			empAttribute.setIsActive(true);
			empAttribute.setCreatedBy(attributeMasterForm.getUserId());
			empAttribute.setModifiedBy(attributeMasterForm.getUserId());
			empAttribute.setCreatedDate(new Date());
			empAttribute.setLastModifiedDate(new Date());
			log.debug("leaving copyDataFromFormToBO");
			return empAttribute;
		}				
}
