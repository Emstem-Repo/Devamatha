package com.kp.cms.handlers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.forms.employee.EmpTypeForm;
import com.kp.cms.helpers.employee.EmpTypeHelper;
import com.kp.cms.to.employee.EmpTypeTo;
import com.kp.cms.transactions.employee.IEmpTypeTransaction;
import com.kp.cms.transactionsimpl.employee.EmpTypeTransaction;

public class EmpTypeHandler {
	
	private static volatile EmpTypeHandler instance=null;
//	IEmpTypeTransaction empTypeTransaction=EmpTypeTransaction.getInstance();
	IEmpTypeTransaction empTypeTransaction=EmpTypeTransaction.getInstance();
	
	/**
	 * 
	 */
	private EmpTypeHandler(){
		
	}
	
	/**
	 * @return
	 */
	public static EmpTypeHandler getInstance(){
		if(instance==null){
			instance=new EmpTypeHandler();
		}
		return instance;
	}

	/**
	 * @param empTypeForm
	 * @return
	 * @throws Exception
	 */
	public Boolean addEmpType(EmpTypeForm empTypeForm)throws Exception {
		Boolean flag=false;
		EmpType empType=null;
		if(empTypeForm.getId()==null){
			if(empTypeForm.getName()!=null ){
				 if(!empTypeForm.getName().isEmpty() && empTypeForm.getEmpTypeList()!=null){
					if(!empTypeForm.getEmpTypeList().containsKey(empTypeForm.getName()))
						empType=EmpTypeHelper.getInstance().convertFormToBo(empTypeForm,new EmpType());
				}else{
					empType=EmpTypeHelper.getInstance().convertFormToBo(empTypeForm,new EmpType());
				}
			}
		}else if(empTypeForm.getId()!=null && !empTypeForm.getId().isEmpty()){
			if(empTypeForm.getEmpTypeMaps()!=null && empTypeForm.getEmpTypeMaps().containsKey(empTypeForm.getId())){
				empType=empTypeForm.getEmpTypeMaps().get(empTypeForm.getId());
				empType=EmpTypeHelper.getInstance().convertFormToBo(empTypeForm,empType);
				empType.setIsActive(true);
			}else if(empTypeForm.getResetMap()!=null && empTypeForm.getResetMap().containsKey(empTypeForm.getId())){
				empType=empTypeForm.getResetMap().remove(empTypeForm.getId());
				empType.setIsActive(true);
				empType.setId(Integer.parseInt(empTypeForm.getId()));
			}
		}
		if(empType!=null){
			if(empTypeForm.getId()==null ){
				empType.setIsActive(true);
				if(empTypeForm.getUserId()!=null && !empTypeForm.getUserId().isEmpty()){
					empType.setCreatedBy(empTypeForm.getUserId());
					empType.setLastModifiedBy(empTypeForm.getUserId());
				}
			}
		empType.setCreatedDate(new Date());
		empType.setLastModifiedDate(new Date());
		flag=empTypeTransaction.addEmpType(empType);
		}
		return flag;
	}

	/**
	 * @param empTypeForm
	 * @return
	 * @throws Exception
	 */
	public List<EmpTypeTo> getEmpTypeList(EmpTypeForm empTypeForm)throws Exception {
		// TODO Auto-generated method stub
		List<EmpTypeTo> empTypeTo=null;
		 Map<String, EmpType> empTypeMap = empTypeTransaction.getEmpTypeMap(empTypeForm);
		if(empTypeMap!=null && empTypeMap.size()!=0){
			empTypeForm.setEmpTypeMaps(empTypeMap);
			empTypeTo= EmpTypeHelper.getInstance().convertEmpTypeBoToempTypeTo(empTypeMap);
		}
		return empTypeTo;
	}

	/**
	 * @param empTypeForm
	 * @return
	 * @throws Exception 
	 */
	public void editEmpType(EmpTypeForm empTypeForm) throws Exception {
		EmpType empType=null;
		if(empTypeForm.getId()!=null){
			if(!empTypeForm.getId().isEmpty() && empTypeForm.getEmpTypeMaps()!=null){
				if(empTypeForm.getEmpTypeMaps().containsKey(empTypeForm.getId())){
					empType=empTypeForm.getEmpTypeMaps().get(empTypeForm.getId());
					EmpTypeHelper.getInstance().convetBoToForm(empType,empTypeForm);
				}
			}
		}
	}

	/**
	 * @param empTypeForm
	 * @return
	 * @throws Exception
	 */
	public Boolean deleteEmpType(EmpTypeForm empTypeForm)throws Exception {
		Boolean flag=false;
		EmpType empType=null;
		if(empTypeForm.getId()!=null ){
			if(!empTypeForm.getId().isEmpty() && empTypeForm.getEmpTypeMaps()!=null){
				if(empTypeForm.getEmpTypeMaps().containsKey(empTypeForm.getId())){
					empType=empTypeForm.getEmpTypeMaps().get(empTypeForm.getId());
//					empType=EmpTypeHelper.getInstance().convertFormToBo(empTypeForm,empType);
				}
			}
		}
		if(empType!=null){
			empType.setIsActive(false);
			empType.setLastModifiedBy(empTypeForm.getUserId());
			empType.setLastModifiedDate(new Date());
			flag=empTypeTransaction.addEmpType(empType);
		}
		return flag;
	}

	/**
	 * @param empTypeForm
	 * @throws Exception
	 */
	public void resetEmpType(EmpTypeForm empTypeForm)throws Exception {
		// TODO Auto-generated method stub
		Map<String,EmpType> list=new HashMap<String, EmpType>();
		Map<String,EmpType> resetMap= empTypeTransaction.getEmpType();
		if(resetMap!=null){
			empTypeForm.setResetMap(resetMap);
			List<EmpType> listType=new ArrayList<EmpType>(resetMap.values());
			if(listType!=null){
				Iterator< EmpType> iterator=listType.iterator();
				while(iterator.hasNext()){
					EmpType empType=iterator.next();
					if(empType!=null && empType.getName()!=null && !empType.getName().isEmpty()){
						list.put(empType.getName(),empType);
					}
				}
			}
			if(list!=null)
				empTypeForm.setEmpTypeList(list);
		}
			
	}

}
