package com.kp.cms.helpers.admin;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.FreeFoodIssueBo;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.FreeFoodIssueForm;
import com.kp.cms.to.admin.FreeFoodIssueTo;

public class FreeFoodIssueHelper {
	private static final Log log = LogFactory.getLog(FreeFoodIssueHelper.class);	
	public static volatile FreeFoodIssueHelper freeFoodIssueHelper = null;
	
	public static FreeFoodIssueHelper getInstance() {
		if (freeFoodIssueHelper == null) {
			freeFoodIssueHelper = new FreeFoodIssueHelper();
		}
		return freeFoodIssueHelper;
	}
	public List<FreeFoodIssueTo> convertBotoTo(List<Object[]> boList,FreeFoodIssueForm form)throws Exception{
		List<FreeFoodIssueTo> toList=new ArrayList<FreeFoodIssueTo>();
		if(boList!=null && !boList.isEmpty()){
			Iterator<Object[]> itr=boList.iterator();
			while(itr.hasNext()){
				Object[] bo=(Object[])itr.next();
		
		
		Properties prop=new Properties();
		InputStream stream=FreeFoodIssueHelper.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		prop.load(stream);
		String fatherIncomeRange =prop.getProperty("knowledgepro.admin.free.food.issue.father.income.range");
		String motherIncomeRange =prop.getProperty("knowledgepro.admin.free.food.issue.mother.income.range");
		String[] str = null;
		String fatherIncome=(String.valueOf(bo[4]));
		String motherIncome=(String.valueOf(bo[5]));
		String fatherTitle=String.valueOf(bo[2]);
		String motherrTitle=String.valueOf(bo[3]);
		
		
			FreeFoodIssueTo to=new FreeFoodIssueTo();
			if(!fatherTitle.equalsIgnoreCase("null") && !fatherTitle.isEmpty()){
						if(!fatherTitle.equalsIgnoreCase("Late")){
							
						if(!fatherIncome.equalsIgnoreCase("null") && !fatherIncome.isEmpty()){	
							str = fatherIncomeRange.split(",");
							for (int x = 0; x < str.length; x++) {
								if (str[x] != null && str[x].length() > 0) {
									if(str[x].equalsIgnoreCase(fatherIncome)){
										to.setIsElijible(true);
										form.setIsEligible(true);
										form.setFlag(false);
										form.setFlag1(false);
										break;
									}else{
										to.setIsElijible(false);
										form.setIsEligible(false);
										form.setFlag(true);
										form.setFlag1(true);
									}
								}
							}
						}else{
							if(!motherIncome.equalsIgnoreCase("null") && !motherIncome.isEmpty()){
							str = motherIncomeRange.split(",");
							for (int x = 0; x < str.length; x++) {
								if (str[x] != null && str[x].length() > 0) {
									if(str[x].equalsIgnoreCase(motherIncome)){
										to.setIsElijible(true);
										form.setIsEligible(true);
										form.setFlag(false);
										form.setFlag1(false);
										break;
									}else{
										to.setIsElijible(false);
										form.setIsEligible(false);
										form.setFlag(true);
										form.setFlag1(true);
									}
								}
							}
						 }else{
							 to.setIsElijible(false);
								form.setIsEligible(false);
								form.setFlag(true);
								form.setFlag1(true);
						 }
						}
							to.setStudentId(String.valueOf(bo[0]));
							form.setStudentId(Integer.parseInt(String.valueOf(bo[0])));
							to.setStudentName(String.valueOf(bo[1]));
							
						}else if(!motherrTitle.equalsIgnoreCase("Late")){
							
							
							if(!motherIncome.equalsIgnoreCase("null") && !motherIncome.isEmpty()){
							str = motherIncomeRange.split(",");
							for (int x = 0; x < str.length; x++) {
								if (str[x] != null && str[x].length() > 0) {
									if(str[x].equalsIgnoreCase(motherIncome)){
										to.setIsElijible(true);
										form.setIsEligible(true);
										form.setFlag(false);
										form.setFlag1(false);
										break;
									}else{
										to.setIsElijible(false);
										form.setIsEligible(false);
										form.setFlag(true);
										form.setFlag1(true);
									}
								}
							}
							}else{
								to.setIsElijible(false);
								form.setIsEligible(false);
								form.setFlag(true);
								form.setFlag1(true);
							}
							to.setStudentId(String.valueOf(bo[0]));
							form.setStudentId(Integer.parseInt(String.valueOf(bo[0])));
							to.setStudentName(String.valueOf(bo[1]));
						}else{
							to.setIsElijible(true);
							form.setIsEligible(true);
							form.setFlag1(false);
							to.setStudentId(String.valueOf(bo[0]));
							form.setStudentId(Integer.parseInt(String.valueOf(bo[0])));
							to.setStudentName(String.valueOf(bo[1]));
						}
			
				
				toList.add(to);
			}else{
				if(fatherTitle.equalsIgnoreCase("null") || fatherTitle.isEmpty()){
					
					if(!fatherIncome.equalsIgnoreCase("null") && !fatherIncome.isEmpty()){
						str = fatherIncomeRange.split(",");
						for (int x = 0; x < str.length; x++) {
							if (str[x] != null && str[x].length() > 0) {
								if(str[x].equalsIgnoreCase(fatherIncome)){
									to.setIsElijible(true);
									form.setIsEligible(true);
									form.setFlag(false);
									form.setFlag1(false);
									break;
								}else{
									to.setIsElijible(false);
									form.setIsEligible(false);
									form.setFlag(true);
									form.setFlag1(true);
								}
							}
						}
					}else{
						if(!motherIncome.equalsIgnoreCase("null") && !motherIncome.isEmpty()){
						str = motherIncomeRange.split(",");
						for (int x = 0; x < str.length; x++) {
							if (str[x] != null && str[x].length() > 0) {
								if(str[x].equalsIgnoreCase(motherIncome)){
									to.setIsElijible(true);
									form.setIsEligible(true);
									form.setFlag(false);
									form.setFlag1(false);
									break;
								}else{
									to.setIsElijible(false);
									form.setIsEligible(false);
									form.setFlag(true);
									form.setFlag1(true);
								}
							}
						}
					}else{
						to.setIsElijible(false);
						form.setIsEligible(false);
						form.setFlag(true);
						form.setFlag1(true);
					}
					}
					
					to.setStudentId(String.valueOf(bo[0]));
					form.setStudentId(Integer.parseInt(String.valueOf(bo[0])));
					to.setStudentName(String.valueOf(bo[1]));
					to.setClassName(String.valueOf(bo[6]));
					toList.add(to);
				}
			}
		}
		
		
		}
		return toList;
	}
	public FreeFoodIssueBo convertFormToBo(FreeFoodIssueForm form){
		FreeFoodIssueBo bo=new FreeFoodIssueBo();
		Student student=new Student();
		student.setId(form.getStudentId());
		bo.setStudentId(student);
		if(form.getIsEligible()){
			bo.setIsEligible("Eligible");
		}else{
			bo.setIsEligible("Not Eligible");
		}
		bo.setDateAndTime(new Date());
		bo.setCreatedBy(form.getUserId());
		bo.setCreatedDate(new Date());
		bo.setModifiedBy(form.getUserId());
		bo.setLastModifiedDate(new Date());
		bo.setIsActive(true);
		return bo;
	}
}
