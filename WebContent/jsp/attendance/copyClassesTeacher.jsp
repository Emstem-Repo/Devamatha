
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested" %>
<script type="text/javascript">
function resetClassEntry(){
	document.location.href="CopyClassTeacher.do?method=initCopyClassTeacher";
}
</script>
<html:form action="/CopyClassTeacher" method="post">
	<html:hidden property="formName" value="copyClassTeacherForm" />
	<html:hidden property="method" styleId="method"
				value="addTeachers" />
	<html:hidden property="id" styleId="id" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
	  <tr>
	   <td><span class="Bredcrumbs"><bean:message key="knowledgepro.attendance" /> <span class="Bredcrumbs">&gt;&gt;
			Copy Class Teacher &gt;&gt;</span></span></td>
	  </tr>
	
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key = "knowledgepro.attn.copy.class.teacher"/></td>
	        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
	      </tr>
	      <tr>
	
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news">
				<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
					<br>
				</html:messages> </FONT></div></td>
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	      <tr>
	        <td valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td align="center"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	
	              <tr>
	                <td ><img src="images/01.gif" width="5" height="5" /></td>
	                <td width="914" background="images/02.gif"></td>
	                <td><img src="images/03.gif" width="5" height="5" /></td>
	              </tr>
	              <tr>
	                <td width="5"  background="images/left.gif"></td>
	                <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
	                    <tr >
	                      <td height="25" class="row-odd" valign="top"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.from.Year"/></div></td>
	                      <td class="row-even" align="left">
	                   	<input type="hidden" id="tempyear" name="tempyear" value='<bean:write name="copyClassTeacherForm" property="fromAcademicYear"/>' />
							<html:select property="fromAcademicYear" styleClass="combo" styleId="academicYear">
								<html:option value="">
									<bean:message key="knowledgepro.select" />-</html:option>
								<cms:renderEmployeeYear></cms:renderEmployeeYear>
							</html:select></td>	
						<td class="row-odd" valign="top" ><div align="right">
	                      <span class="Mandatory">*</span>
	                      <bean:message key = "knowledgepro.to.Year"/></div></td>
	                     <td class="row-even" align="left">
	                   	<input type="hidden" id="tempyear" name="tempyear" value='<bean:write name="copyClassTeacherForm" property="toAcademicYear"/>' />
							<html:select property="toAcademicYear" styleClass="combo" styleId="academicYear" >
								<html:option value="">
									<bean:message key="knowledgepro.select" />-</html:option>
								<cms:renderEmployeeYear></cms:renderEmployeeYear>
							</html:select></td>				
	                    </tr>
	                    <tr>
						 <td  height="25" class="row-odd" valign="top">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.attn.sem.type" /></div>
							</td>
							<td class="row-even">
                			<html:select name="copyClassTeacherForm" styleId="semType" property="semType"  
	                       style="width:170px"> 
	                       <html:option value=""><bean:message key="knowledgepro.select" />-</html:option>
	                      <html:option value="EVEN">EVEN</html:option>
	                       <html:option value="ODD">ODD</html:option>
                  		   </html:select> 
							</td>
	                    </tr>
	                  </table>
	                  </td>
	                <td width="5" height="30"  background="images/right.gif"></td>
	              </tr>
	              <tr>
	                <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
	                <td background="images/05.gif"></td>
	                <td><img src="images/06.gif" /></td>
	              </tr>
	            </table></td>
	          </tr>
	          <tr>
			<td>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr><td style="height: 10px" align="center" colspan="1"></td></tr>
				<tr>
	            <td  height="35"><div align="center">
                		<html:submit property="" styleClass="formbutton" onclick="addTeachers()"><bean:message key="knowledgepro.attn.copy"/></html:submit>
						<html:button property="" styleClass="formbutton" value="Reset" onclick="resetClassEntry()">
										<bean:message key="knowledgepro.admin.reset"/></html:button>
				</div></td>
			</tr>				
			<tr><td style="height: 10px" align="center" colspan="1"></td></tr>			
				</table>
			</td>
	          </tr>
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading">   </td>   
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading">    </td>  
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	
	      <tr>
	        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
	        <td width="0" background="images/TcenterD.gif"></td>
	        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
	      </tr>
	    </table></td>
	  </tr>
	</table>
</html:form>
<script type="text/javascript">
	var yearId = document.getElementById("tempyear").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("academicYear").value = yearId;
	}
</script>	