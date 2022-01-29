package com.kp.cms.utilities;

import java.util.HashMap;
import java.util.Map;

public class NumberToWordConvertor {

	/**
	 * @param args
	 */
	private static Map<Integer, String> dateMap = null;
	
	static {
		dateMap = new HashMap<Integer, String>();
		dateMap.put(1,"FIRST");
		dateMap.put(2,"SECOND");
		dateMap.put(3,"THIRD");
		dateMap.put(4,"FOURTH");
		dateMap.put(5,"FIFTH");
		dateMap.put(6,"SIXTH");
		dateMap.put(7,"SEVENTH");
		dateMap.put(8,"EIGHTH");
		dateMap.put(9,"NINTH");
		dateMap.put(10,"TENTH");
		dateMap.put(11,"ELEVENTH");
		dateMap.put(12,"TWELFTH");
		dateMap.put(13,"THIRTEENTH");
		dateMap.put(14,"FOURTEENTH");
		dateMap.put(15,"FIFTEENTH");
		dateMap.put(16,"SIXTEENTH");
		dateMap.put(17,"SEVENTEENTH");
		dateMap.put(18,"EIGHTEENTH");
		dateMap.put(19,"NINETEENTH");
		dateMap.put(20,"TWENTIETH");
		dateMap.put(21,"TWENTY FIRST");
		dateMap.put(22,"TWENTY SECOND");
		dateMap.put(23,"TWENTY THIRD");
		dateMap.put(24,"TWENTY FOURTH");
		dateMap.put(25,"TWENTY FIFTH");
		dateMap.put(26,"TWENTY SIXTH");
		dateMap.put(27,"TWENTY SEVENTH");
		dateMap.put(28,"TWENTY EIGHTH");
		dateMap.put(29,"TWENTY NINTH");
		dateMap.put(30,"THIRTIETH");
		dateMap.put(31,"THIRTY FIRST");


		
	}
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		NumberToWordConvertor cv = new NumberToWordConvertor();
//		System.out.println(cv.convertNumber("1996"));
//	}
	
	private static double getPlace( String number ){
		switch( number.length() ){
		case 1:
		return DefinePlace.UNITS;
		case 2:
		return DefinePlace.TENS;
		case 3:
		return DefinePlace.HUNDREDS;
		case 4:
		return DefinePlace.THOUSANDS;
		case 5:
		return DefinePlace.TENTHOUSANDS;
		case 6:
		return DefinePlace.LAKHS;
		case 7:
		return DefinePlace.TENLAKHS;
		case 8:
		return DefinePlace.CRORES;
		case 9:
		return DefinePlace.TENCRORES;
		}//switch
		return 0.0;
		}// getPlace

		private static String getWord( int number ){
		switch( number ){
		case 1:
		return "One";
		case 2:
		return "Two";
		case 3:
		return "Three";
		case 4:
		return "Four";
		case 5:
		return "Five";
		case 6:
		return "Six";
		case 7:
		return "Seven";
		case 8:
		return "Eight";
		case 9:
		return "Nine";
		case 0:
		return "Zero";
		case 10:
		return "Ten";
		case 11:
		return "Eleven";
		case 12:
		return "Tweleve";
		case 13:
		return "Thirteen";
		case 14:
		return "Fourteen";
		case 15:
		return "Fifteen";
		case 16:
		return "Sixteen";
		case 17:
		return "Seventeen";
		case 18:
		return "Eighteen";
		case 19:
		return "Nineteen";
		case 20:
		return "Twenty";
		case 30:
		return "Thirty";
		case 40:
		return "Fourty";
		case 50:
		return "Fifty";
		case 60:
		return "Sixty";
		case 70:
		return "Seventy";
		case 80:
		return "Eighty";
		case 90:
		return "Ninety";
		case 100:
		return "Hundred";
		} //switch
		return "";
		} //getWord

		private static String cleanNumber( String number )
		{
			String cleanedNumber = "";
			cleanedNumber = number.replace( '.', ' ' ).replaceAll( " ", "" );
			cleanedNumber = cleanedNumber.replace( ',', ' ' ).replaceAll( " ", "");
			if( cleanedNumber.startsWith( "0" ))
				cleanedNumber = cleanedNumber.replaceFirst( "0", "" );
			return cleanedNumber;
		} //cleanNumber

		public static String convertNumber( String number )
		{
			number = cleanNumber( number );
			double num = 0.0;
			try
			{
				num = Double.parseDouble( number );
			}
			catch( Exception e )
			{
				return "Invalid Number Sent to Convert";
			} //catch
			String returnValue = "";
			while( num > 0 )
			{
				number = "" + (int)num;
				double place = getPlace(number);
				if( place == DefinePlace.TENS || place == DefinePlace.TENTHOUSANDS || place == DefinePlace.TENLAKHS || place == DefinePlace.TENCRORES )
				{
					int subNum = Integer.parseInt( number.charAt(0) + "" + number.charAt(1) );
					if( subNum >= 21 && (subNum%10) != 0 )
					{
						returnValue += getWord( Integer.parseInt( "" + number.charAt(0) ) * 10 ) + " " + getWord( subNum%10 ) ;
					}
					//if
					else
					{
						returnValue += getWord(subNum);
					}//else
					if( place == DefinePlace.TENS )
					{
						num = 0;
					}//if
					else if( place == DefinePlace.TENTHOUSANDS )
					{
						num -= subNum * DefinePlace.THOUSANDS;
						returnValue += " Thousands ";
					}
					//if
					else if( place == DefinePlace.TENLAKHS )
					{
						num -= subNum * DefinePlace.LAKHS;
						returnValue += " Lakhs ";
					}//if
					else if( place == DefinePlace.TENCRORES )
					{
						num -= subNum * DefinePlace.CRORES;
						returnValue += " Crores ";
					}//if
				}//if
				else
				{
					int subNum = Integer.parseInt( "" + number.charAt(0) );
					returnValue += getWord( subNum );
					if( place == DefinePlace.UNITS )
					{
						num = 0;
					}//if
					else if( place == DefinePlace.HUNDREDS )
					{
						num -= subNum * DefinePlace.HUNDREDS;
						returnValue += " Hundred ";
					}
					//if
					else if( place == DefinePlace.THOUSANDS )
					{
						num -= subNum * DefinePlace.THOUSANDS;
						returnValue += " Thousand ";
					}//if
					else if( place == DefinePlace.LAKHS )
					{
						num -= subNum * DefinePlace.LAKHS;
						returnValue += " Lakh ";
					}//if
					else if( place == DefinePlace.CRORES )
					{
						num -= subNum * DefinePlace.CRORES;
						returnValue += " Crore ";
					}//if
				}//else
			}//while
			return returnValue;
		}//convert number
		
		public static String getDate(int date)
		{
			return dateMap.get(date); 
		}
	} //class

	class DefinePlace
	{
		public static final double UNITS = 1;
		public static final double TENS = 10 * UNITS;
		public static final double HUNDREDS = 10 * TENS;
		public static final double THOUSANDS = 10 * HUNDREDS;
		public static final double TENTHOUSANDS = 10 * THOUSANDS;
		public static final double LAKHS = 10 * TENTHOUSANDS;
		public static final double TENLAKHS = 10 * LAKHS;
		public static final double CRORES = 10 * TENLAKHS;
		public static final double TENCRORES = 10 * CRORES;
	} //class



