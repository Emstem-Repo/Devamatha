package com.kp.cms.utilities.jms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;



public class Core5 {


	
	public static void main(String[] args) {
		
	/*Map<Integer,String> m=new HashMap<Integer, String>();
	Map<Integer,String> m1=new HashMap<Integer, String>();
	List<Integer> list=new ArrayList<Integer>();
	List<String> list1=new ArrayList<String>();
	
	m.put(3, "rfjkhgiore");
	m.put(4, "rfjkhgio");
	m.put(5, "rkhgiore");
	m.put(5, "rkhgi");
	
	m.put(3, "rfjkhgiore");
	m.put(4, "rfjkhgio");
	m.put(5, "rkhgiore");
	m.put(5, "rkhgi");
	
	System.out.println(m);
	
	Set<Integer> l= m.keySet();
	Iterator<Integer> l1=l.iterator();
	while(l1.hasNext()){
		Integer n=l1.next();
		System.out.println(n);
	}*/
	
	/*Set<Entry<Integer,String>> s=m.entrySet();
	Iterator<Entry<Integer,String>> l=s.iterator();
	while(l.hasNext()){
		Entry<Integer,String> n=l.next();
		System.out.println(n.getValue());
		System.out.println(n.getKey());
		list.add(n.getKey());
		list1.add(n.getValue());
	}
	
	
	
	Iterator<Integer> l1=list.iterator();
	while(l1.hasNext()){
		Integer n=l1.next();
		System.out.println(n);
	}
	
	
	Iterator<String> l2=list1.iterator();
	while(l2.hasNext()){
		String n=l2.next();
		System.out.println(n);
	}
	
	*/
		
		
		
			 
				// Core5 c=new Core5(); 
				// c.readIt();
		List<Integer> semList = new ArrayList<Integer>();
		Map<Integer,Integer> semisterMap = new HashMap<Integer,Integer>();
		//semList.add(1);
		//semList.add(2);
		//semList.add(3);
		semList.add(5);
		
		
		if(false){
			for(Integer i:semList){
				semisterMap.put(i.intValue(), i.intValue());
			}
		}else{
			int i = Collections.min(semList);
			for(int j=0;j<2;j++){
				if((i-1)!=0){
					semisterMap.put(i-1, i-1);
					i--;
				}
				if(i==0){
					break;
				}
			}
		}
		
		
	System.out.println(semisterMap);
	}
	
	//anonymus inner class
	Core1 pInstance = new    Core1() {
		  public void read() {
		   System.out.println("anonymous ProgrammerInterview");
		  }
		 };
	
	Core1 pInstance1 = new    Core1() {
			  public void read() {
			   System.out.println("anonymous ProgrammerInterview");
			  }
			  public void learn() {
				  System.out.println("anonymous, learn ProgrammerInterview");
			  }
			 };
		
	
	public void readIt()  {
		/*
		This is legal:
		*/
		
		pInstance1.read();
		/*
		Compiler error, the learn method is 
		not also defined inside the ProgrammerInterview
		class:
		*/

		//pInstance.learn();

		}
	
}










