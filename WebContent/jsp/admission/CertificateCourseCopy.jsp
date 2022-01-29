<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">





function resetCertificate(){
	document.getElementById("fromSemType").value="";
	document.getElementById("toSemType").value="";
	document.getElementById("fromYear").value="";
	 document.getElementById("toYear").value="";
	resetFieldAndErrMsgs();
	 }
	 

 
</script>
</head>
<body>
<html:form action="/copyCertificate">
<html:hidden property="formName" value="certificateCourseCopyForm"/>
<html:hidden property="pageType" value="2" />
<html:hidden property="method" styleId="method" value="certificateCourseCopy"/>
<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs">Admin 
			<span class="Bredcrumbs">&gt;&gt;Certificate Course Copy &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader">Certificate Course Copy</strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="6" align="left">
							<div align="right"><span class='MandatoryMark'>mandatoryfields</span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
					<br>
				</html:messages> </FONT></div>
							</td>
						</tr>
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
							
          
            <tr>
            	<td   class="row-odd" width="20%"><div align="right">&nbsp;<span class="Mandatory">*</span>From Academic Year:</div></td>
                <td  height="25" class="row-even" width="30%">
                	<table width="189" border="0" cellspacing="0" cellpadding="0">
                    	<tr>
                      		<td width="60">
                      			<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="certificateCourseCopyForm" property="fromAcademicYear" />"/>
                 							<!--<html:select property="fromAcademicYear" styleId="fromYear" styleClass="combo">
  	   											 <html:option value="">- Select -</html:option>
  	   											 <cms:renderAcademicYear></cms:renderAcademicYear>
   			  								 </html:select> 
   			  								 -->
   			  								 <select name="fromAcademicYear" id="fromYear" style="combo">
									<option value="">- Select -</option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</select>
                      		</td>
                      		
                    	</tr>
                	</table>
                </td>
			    <td  class="row-odd" width="20%"><div align="right">&nbsp;<span class="Mandatory">*</span>To Academic Year:</div></td>
                <td class="row-even" width="30%">
                	<table width="188" border="0" cellspacing="0" cellpadding="0">
                  		<tr>
                    		<td>
                    		<input type="hidden" id="tempyear1" name="tempyear" value="<bean:write name="certificateCourseCopyForm" property="toAcademicYear" />"/>
                 							<!--<html:select property="toAcademicYear" styleId="toYear" styleClass="combo">
  	   											 <html:option value="">- Select -</html:option>
  	   											 <cms:renderAcademicYear></cms:renderAcademicYear>
   			  								 </html:select>
   			  								 -->
   			  								 <select name="toAcademicYear" id="toYear" style="combo">
									<option value="">- Select -</option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</select>
   			  								 
   			  								 </td>
                  		</tr>
                	</table>
                </td>
			</tr>
			  <tr>
            	<td   class="row-odd" width="20%"><div align="right">&nbsp;<span class="Mandatory">*</span>From SemType:</div></td>
                <td  height="25" class="row-even" width="30%">
                	<table width="189" border="0" cellspacing="0" cellpadding="0">
                    	<tr>
                      		<td width="60">
                      		<input type="hidden" id="tempfromsemtype" name="tempyear" value="<bean:write name="certificateCourseCopyForm" property="fromSemType" />"/>
                 					<!--<html:select property="fromSemType" styleId="fromSemType" styleClass="combo">
										 <html:option value="">- Select -</html:option>
										<html:option value="ODD">ODD</html:option>
										<html:option value="EVEN">EVEN</html:option>
									</html:select>
                      		-->
                      		

<select name="fromSemType" id="fromSemType" style="combo">
									<option value="">- Select -</option>
										<option value="ODD">ODD</option>
										<option value="EVEN">EVEN</option>
									</select></td>
                      		
                    	</tr>
                	</table>
                </td>
			    <td  class="row-odd" width="20%"><div align="right">&nbsp;<span class="Mandatory">*</span>To SemType:</div></td>
                <td class="row-even" width="30%">
                	<table width="188" border="0" cellspacing="0" cellpadding="0">
                  		<tr>
                    		<td>
                    		<input type="hidden" id="temptosemtype" name="tempyear" value="<bean:write name="certificateCourseCopyForm" property="toSemType" />"/>
                 					<!--<html:select property="toSemType" styleId="toSemType" styleClass="combo">
										 <html:option value="">- Select -</html:option>
										<html:option value="ODD">ODD</html:option>
										<html:option value="EVEN">EVEN</html:option>
									</html:select>
   			  								 -->
   			  								 <select name="toSemType" id="toSemType" style="combo">
									<option value="">- Select -</option>
										<option value="ODD">ODD</option>
										<option value="EVEN">EVEN</option>
									</select>
   			  								 </td>
                  		</tr>
                	</table>
                </td>
			</tr>
           
		</table>
	</td>
	<td width="5" height="30" background="images/right.gif"></td>
</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
            <td width="49%" height="35" align="center">
            
						<html:submit  value="Copy" styleClass="formbutton" ></html:submit>
		&nbsp;
		&nbsp;
		&nbsp;
            <input name="Submit" type="reset" class="formbutton" value="Reset" onclick="resetCertificate()" />
        </td>
          </tr>

					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
						</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
					<tr>
					<td height="26" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news"></td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>	
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</html:form>
	<script type="text/javascript">
	document.getElementById("fromSemType").value="";
	document.getElementById("toSemType").value="";
	document.getElementById("fromYear").value="";
	 document.getElementById("toYear").value="";

		 var fromsem = document.getElementById("tempfromsemtype").value;
		if (fromsem.length != 0) {
			document.getElementById("fromSemType").value = fromsem;
		}

		 var tosem = document.getElementById("temptosemtype").value;
			if (tosem.length != 0) {
				document.getElementById("toSemType").value = tosem;
			}

			 var year = document.getElementById("tempyear").value;
		if (year.length != 0) {
			document.getElementById("fromYear").value = year;
		}

		var year1 = document.getElementById("tempyear1").value;
		if (year1.length != 0) {
			document.getElementById("toYear").value = year1;
		}
	</script>
</body>
</html>