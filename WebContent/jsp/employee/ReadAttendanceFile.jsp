<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<html:form action="/ReadAttendanceFile" method="post" enctype="multipart/form-data">
	<html:hidden property="method" styleId="method" value="readAttendanceFile" />
	<html:hidden property="formName" value="ReadAttendanceFileForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
	  <tr>
	    <td height="15"><span class="heading"><bean:message key="knowledgepro.employee.read.attendance.file"/> <span class="Bredcrumbs"></span></span></td>
	  </tr>
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	
	        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key="knowledgepro.employee.read.attendance.file"/> </strong></td>
	        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
	      </tr>
   	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td  class="news">
			<div align="right">
				<FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
				<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"> <html:messages id="msg"
				property="messages" message="true">
				<c:out value="${msg}" escapeXml="false"></c:out>
				<br>
				</html:messages> </FONT></div></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>

	
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	          <tr>
	            <td ><img src="images/01.gif" width="5" height="5" /></td>
	            <td width="914" background="images/02.gif"></td>
	            <td><img src="images/03.gif" width="5" height="5" /></td>
	          </tr>
	          <tr>
	
	            <td width="5"  background="images/left.gif"></td>
	            <td valign="top">
	            	<table width="100%" border="0" cellpadding="0" cellspacing="1">
	            		<tr>
	            			<td height="25" class="row-odd" width="25%">
								<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.read.attendance.file.upload.file" />:</div>
	            			</td>
	            			<td height="25" class="row-even" width="75%">
	            				<html:file property="thefile" styleId="thefile" size="35" maxlength="30"/>
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
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	          <tr>
	
	            <td width="45%" height="35"><div align="right">
	              <html:submit styleClass="formbutton" value="Submit"/>
	            </div></td>
	            <td width="2%"></td>
	            <td width="53%"> <html:button property="" styleClass="formbutton"  value="Reset" onclick="resetFieldAndErrMsgs()"></html:button>
				</td>
	          </tr>
	        </table> </td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	
	      </tr>
	      
	      <tr>
	        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news">&nbsp;</td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
