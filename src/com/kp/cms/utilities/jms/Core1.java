package com.kp.cms.utilities.jms;

import java.math.BigDecimal;


public class Core1 {


	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
		
		//a1();
		//int k=1;
		//if(k<=0)
			//k=-(k);
		//System.out.println(k);
		
		int x=2;
		x+=++x*x--+--x*x++;
		System.out.println(x);
		
		BigDecimal b=new BigDecimal(5000);
		BigDecimal b1=new BigDecimal(50000);
		b=b.add(b1);
		System.out.println(b.add(b1));
		System.out.println(b);
		System.out.println(b1);
		
	}
	
	
/*	static{
		int i=8;
		int j=9;
		System.out.println(i*j);
	}
*/	
	
	
	
	
	
	static void a() {
		int i=0;

		for(;i++<3;)
			System.out.println("before    "+i);
			System.out.println("middle    "+i);

			for(;i-->1;)
			System.out.println("after    "+i);
			System.out.println("last    "+(--i+i));



		
		
	}

	
	static void a1() {
		
		int a=0,b=0,m=0,m1=10,m2=40,m3=20;

		if(m3<m1){
			a=m1-m3;
			System.out.println("a=first"+a);
		}else{
			a=m3-m1;
			System.out.println("a=second"+a);
		}
		
		
		if(m3<m2){
			b=m2-m3;
			System.out.println("b=first"+b);
		}else{
			b=m3-m2;
			System.out.println("b=second"+b);
		}
		
		if(a==b){
			
			if(m2<=m1){
				m=(m3+m1)/2;
			}else{
				m=(m3+m2)/2;
			}
			System.out.println("m=first"+m);
			
		}else if(a<b){
			
			m=(m1+m3)/2;
			System.out.println("m=second"+m);
		}else{
			m=(m2+m3)/2;
			System.out.println("m=second"+m);
		}
		
	}
	
	
	
	public void read() {
		 System.out.println("Programmer Interview!");
		 }
	
}










