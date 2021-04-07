<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
	function cancelAction() {
		document.location.href ="telephoneDictonary.do?method=initTelephoneDirectoryRestricted";
	}
	function winOpen() {
		var url = "telephoneDictonary.do?method=getExtensionNumbers";
		myRef = window.open(url, "viewExtensionNumbers",
						"left=20,top=20,width=600,height=500,toolbar=1,resizable=0,scrollbars=1");
	}
	</script>
<html:form action="/telephoneDictonary" enctype="multipart/form-data">
	<html:hidden property="formName" value="telephoneDirectoryForm" />
	 <html:hidden property="method" styleId="method" value="searchDetailsRestricted" />
	<table width="100%" border="0">
	  <tr>
	    <td><span class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.sec.EmployeeCategory"/> &gt;&gt; Telephone Directory &gt;&gt;</span></span></td>
	  </tr>
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> Telephone Directory</strong></td>
	
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
	
	        <td height="30" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	          <tr>
	            <td ><img src="images/01.gif" width="5" height="5" /></td>
	            <td width="914" background="images/02.gif"></td>
	            <td><img src="images/03.gif" width="5" height="5" /></td>
	          </tr>
	          <tr>
	            <td width="5"  background="images/left.gif"></td>
	
	            <td valign="top">
	            <table width="100%" cellspacing="1" cellpadding="2">
	              <tr >
	                <td width="24%" height="25" class="row-odd"><div align="right" > Department:</div></td>
	                <td width="26%" class="row-even">
	                <html:select property="departmentId" styleId="departmentId" name="telephoneDirectoryForm"  >
	                <html:option value="">- <bean:message key="knowledgepro.select" /> -</html:option>
					<html:optionsCollection property="deptListTO" name="telephoneDirectoryForm" label="name" value="id" />
	                </html:select>
					</td>
	                <td width="17%" class="row-odd"><div align="right">Employee Name:</div></td>
	                <td width="33%" class="row-even">
	                  <html:text property="employeeName" styleId="employeeName" name="telephoneDirectoryForm"/>
	                </td>
	              </tr>
	              <tr>
	                <td width="24%" class="row-odd"><div align="right">Employee Id:</div></td>
	                <td width="26%" class="row-even">
	                  <html:text property="fingerPrintId" styleId="fingerPrintId" name="telephoneDirectoryForm"/>
	                </td>
	                <td width="17%" class="row-odd"><div align="right">Extension No.:</div></td>
	                <td width="33%" class="row-even">
	                  <html:text property="extNo" styleId="extNo" name="telephoneDirectoryForm"/>
	                </td>
	              </tr>
	            </table></td>
	
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
            	<td width="50%" height="35" align="center"><input name="Submit" type="Submit" class="formbutton" value="Search" onclick="getDetails()" /></td>
            	<!-- start by giri -->  		
          		<td width="25%" >
					<a href="javascript:winOpen()"	class="navmenu"><b>Other Administrative Offices Extensions</b> </a>
				</td>
				<!-- end by giri --> 
          	</tr>
	        </table> </td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	       <logic:notEmpty  property="empList" name="telephoneDirectoryForm">
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
	            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
	           
	              <tr>
	                <td height="5" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
	                <td height="20" class="row-odd" align="center">Name</td>
	                <td height="25" class="row-odd" align="center">Department</td>
	                <td height="20" class="row-odd" align="center">Email</td>
					 <td height="10" class="row-odd" align="center">Extension Number</td>
					 <!--<td height="15" class="row-odd" align="center">Photo</td>
	              --></tr>
	              <logic:iterate id="id" property="empList" name ="telephoneDirectoryForm" indexId="count">
	              <bean:define id="reg" name="id" property="id"></bean:define>
				<tr>
						<c:choose>
							<c:when test="${count%2 == 0}">
								<tr class="row-even">
							</c:when>
							<c:otherwise>
								<tr class="row-white">
							</c:otherwise>
						</c:choose>				
			                <td width="5%" height="25"><div align="center"><c:out value="${count + 1}"/></div></td>
			                <td width="20%" height="25" align="center"><bean:write name = "id" property="firstName"/></td>
			                <td width="25%" align="center"><bean:write name = "id" property="departmentName"/></td>
			                <td width="20%" align="center"><bean:write name = "id" property="workEmail"/></td>
			                <td width="10%" align="center"><bean:write name = "id" property="extensionNumber"/></td><!--
			               <td width="15%" align="center">
								<img src="<%=request.getContextPath()%>/MarksCardPhotoServlet?studentPhoto=<%=reg %>"  height="70" width="60" />
								</td>
			              --></tr>
					</logic:iterate>
	            </table></td>
	            <td width="5" height="30"  background="images/right.gif"></td>
	          </tr>
	          <tr>
	            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
	
	            <td background="images/05.gif"></td>
	            <td><img src="images/06.gif" /></td>
	          </tr>
	        </table></td>
	        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      
	      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" height="35" align="center">
			<html:button property="" styleClass="formbutton" value="Close"  onclick="cancelAction()"></html:button></td>
          </tr>
        </table> </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      </logic:notEmpty>
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