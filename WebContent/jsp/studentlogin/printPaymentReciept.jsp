<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@ taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<style type="text/css">
	body
	{
		font-family: sans-serif;
		font-size: 12;
		
	}
	
	
          
     
</style>
<style type="text/css">

		body{
			 size: a5 landscape;
        	 margin: 0;
		}
</style>
<body>
<html:form action="/StudentLoginAction">

 	<html:hidden property="formName" value="loginform"/>
	<html:hidden property="pageType" value="1"/>
	
  <table width="100%" border="0">
              	
		       <tr><td  align="center" style="font-family: monospace;"> <bean:message key="knowledgepro.collegeName"/></td></tr>	
		          
	           <tr><td align="center"><font style="font-family: monospace;"><bean:message key="knowledgepro.collegeName.place"/></font></td></tr>
	           
	            <tr> <td align="center" style="font-family: monospace;"><bean:message key="knowledgepro.print.reciept"/></td></tr>
					
				  <tr>
					  <td>
					      <table width="100%" border="0">
					                  <tr>
					                  	<td align="left" style="font-size:11px; font-family:monospace;">
										<td align="right" style="font-size:11px; font-family:monospace;">Date:<bean:write name="loginform" property="txnDate"/></td>
									  </tr>
									
								  	  <tr>
									     <td align="left" style="font-size:11px; font-family:monospace;"><bean:message key="knowledgepro.fee.print.BillNumber"/>
										 <bean:write name="loginform" property="billNo"/>
									     </td>
									  </tr>
									  <tr>
									     <td align="left" style="font-size:11px; font-family:monospace;"><bean:message key="knowledgepro.fee.print.StudentName"/>
										 <bean:write name="loginform" property="studentName"/>
									     </td>
									  </tr>
									  <tr>
									     <td align="left" style="font-size:11px; font-family:monospace;"><bean:message key="knowledgepro.fee.print.registerNo"/>
										 <bean:write name="loginform" property="regNo"/>
									     </td>
									  </tr>
									  <tr>
									     <td align="left" style="font-size:11px; font-family:monospace;"><bean:message key="knowledgepro.fee.print.Course"/>
										 <bean:write name="loginform" property="courseName"/>
									     </td>
									  </tr>
									
							         
									  <tr>
										 <td align="left"  style="font-size:11px; font-family:monospace;"><bean:message key="knowledgepro.fee.print.txnId"/>
										 <bean:write name="loginform" property="txnRefNo"/></td>
									  </tr>
										
									  <tr height="15px">
										 <td class="row-pri-even" width="70%" align="left" style="border-bottom-style:dashed; border-color: black; font-size:11px; font-family:monospace;"></td>
										 <td class="row-pri-even" align="right" width="30%" style="border-bottom-style:dashed; border-color: black; font-size:11px; font-family:monospace;"></td>
									  </tr>
										
										
										 <tr>
										    <td style="font-size:11px; font-family:monospace;" align="left">Paid For</td>
											<td align="right" class="row-pri-odd" width="70%" style="font-size:11px; font-family:monospace;">PaidAmount</td>
										 </tr>
										
										<tr height="15px">
											<td class="row-pri-even" width="70%" align="left" style="border-bottom-style:dashed; border-color: black; font-size:11px; font-family:monospace;"></td>
											<td class="row-pri-even" align="right" width="30%" style="border-bottom-style:dashed; border-color: black; font-size:11px; font-family:monospace;"></td>
										</tr>
										      
								          <tr>
								            <td style="font-size:11px; font-family:monospace;" align="left"><bean:write name="loginform" property="heading"/></td>
								            <td style="font-size:11px; font-family:monospace;" align="right"><bean:write name="loginform" property="txnAmt"/></td>
								          </tr>	
										  
										
										
										<tr height="15px">
											<td class="row-pri-even" width="70%" align="left" style="border-bottom-style:dashed; border-color: black; font-size:11px; font-family:monospace;"></td>
											<td class="row-pri-even" align="right" width="30%" style="border-bottom-style:dashed; border-color: black; font-size:11px; font-family:monospace;"></td>
										</tr>
										
										<tr>
											<td align="right" width="70%" class="row-pri-odd"><font style="font-family: monospace; font-size: 13; font-weight: 5px">T O T A L :&nbsp;</font></td>	
											<td style="font-size:11px; font-family:monospace;" align="right">
										      <bean:write name="loginform" property="txnAmt"/>
										   </td>
										</tr>	
				      </table>
				  </td>
			  </tr>
  </table>
</html:form>
</body>
	<script type="text/javascript">
	window.print();
	</script>