<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<head>
<script src="js/timetable/studentTimeTable.js" type="text/javascript"></script>
</head>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested" %>
<head>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
</head>
<html:form action="/viewMyTimeTable" method="post">
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="viewTeacherWiseTimeTableForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
	<tr>
			<td colspan="3"><span > Attendance
			<span >&gt;&gt; View my Time Table &gt;&gt;</span></span></td>
				</tr>
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/st_Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/st_Tcenter.gif"  ><BIG><b>View my Time Table</b></BIG></td>
	        <td width="10" ><img src="images/st_Tright_1_01.gif" width="9" height="29"></td>
	      </tr>
	     
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading"></td>      
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	<logic:notEmpty name="viewTeacherWiseTimeTableForm" property="timeTableList">
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td >
	        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" id="display">
	          <tr>
	            <td ><img src="images/st_01.gif" width="5" height="5"></td>
	            <td width="100%" background="images/st_02.gif"></td>
	            <td><img src="images/st_03.gif" width="5" height="5"></td>
	          </tr>
	          <tr>
	
	            <td width="5"  background="images/st_left.gif"></td>
	            <td height="54" valign="top">
	            <table width="100%" cellspacing="1" cellpadding="2" class="table table-striped table-bordered table-condensed">
	            <tr  height="25px">
	            	<th width="10%" class="text-danger"><div align="center">Day </div></th>
	            	<logic:notEmpty name="viewTeacherWiseTimeTableForm" property="periodList">
	            		<logic:iterate id="bo" name="viewTeacherWiseTimeTableForm" property="periodList" type="com.kp.cms.bo.admin.Period">
	            		<% String startTime = bo.getStartTime().substring(0,5) ;
	            			String endTime = bo.getEndTime().substring(0,5);
	            		%>
	            		<th width="10%" height="10%" class="text-danger"><div align="left"><bean:write name="bo" property="periodName"/>&nbsp;(<%=startTime %>-<%=endTime %>)</div></th>
	            		
	            		</logic:iterate>
	            	</logic:notEmpty>
	            </tr>
	            <logic:iterate id="to" name="viewTeacherWiseTimeTableForm" property="timeTableList">
	            <tr   height="50px">
	            	
					<td  width="10%"   align="center" >
					<table width="100%">
					<tr height="20"></tr>
					<tr height="10">
					<td class="text-danger"  align="center">
					<b><bean:write name="to" property="week"/></b>
					</td>
					</tr>
					</table>
					</td>
					<logic:notEmpty name="to" property="timeTablePeriodTos">
	            		<logic:iterate id="pto" name="to" property="timeTablePeriodTos">
	            		<td width="10%"  align="center" >
	            		<table cellpadding="0" cellspacing="0" width="100%">
	            		<logic:notEmpty name="pto" property="roomNo">
	            		<tr height="10%"><td >
	            		<bean:write name="pto" property="roomNo"/><br></br></td>
	            		</tr>
	            		</logic:notEmpty>
	            		<tr height="10%">
	            		<td ><bean:write name="pto" property="subjectNames"/>
	            		</td>
	            		</tr>
	            		<tr>
	            		<td><b><bean:write name="pto" property="subjectCode"/></b>
	            		</td>
	            		</tr>
	            		</table>
	            		 </td>
	            		</logic:iterate>
	            	</logic:notEmpty>						            
	            </tr>
	            </logic:iterate>	
	            
								
	            </table>
	            </td>
	            
	           <td  background="images/st_right.gif" width="5" height="54"></td>
	          </tr>
	          
	          
	          
	          <tr>
	            <td height="5"><img src="images/st_04.gif" width="5" height="5"></td>
	            <td background="images/st_05.gif"></td>
	            <td><img src="images/st_06.gif" ></td>
	          </tr>
	          <tr height="10"></tr>
				<tr>
	         
	          <td height="54" valign="top" colspan="3">
	            
	           
	            <table width="100%"  cellspacing="1" cellpadding="3">
	                    <tr>
								<td>
								<table width="100%"  cellspacing="0" cellpadding="0">
								
								
								<tr>
								<td width="70%" height="35"><div align="center">
								 <html:button property="" value="Close" styleClass="btn btn-primary" onclick="cancel()"></html:button>
								 </div>
								</tr>
								</table>
								</td>
								</tr>
								</table>	
								
	            
	            </td>
	            
	            </tr>
	        </table>   </td>   
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	      </logic:notEmpty>
	      <logic:empty name="viewTeacherWiseTimeTableForm" property="timeTableList">
	      <tr>
	      <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	      <td align="center"  style="color:red;">No TimeTable Found</td>
	      <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	      </logic:empty>
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	
	      <tr>
	        <td><img src="images/st_Tright_03_05.gif" width="9" height="29"></td>
	        <td width="0" background="images/st_TcenterD.gif"></td>
	        <td><img src="images/st_Tright_02.gif" width="9" height="29"></td>
	      </tr>
	    </table></td>
	  </tr>
	</table>
</html:form>
