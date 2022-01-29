<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@page import="com.kp.cms.utilities.CommonUtil"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<%@page import="java.util.Date"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Employee Information Submission</title>
<LINK REL=StyleSheet HREF= "css/styles.css" TYPE="text/css">
<script language="JavaScript" src="js/admissionform.js"></script>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script type="text/javascript">

  function getEmployeeByprogramType(programId) {
	  var args ="method=getEmployeeByprogram&programId="+programId;
	  var url = "AjaxRequest.do";
	  requestOperation(url, args, updateEmployee);
    }
     function updateEmployee(req) {
	   updateOptionsFromMap(req, "employeeId", "- Select -");
      }
	function closeWindow(){
		document.location.href = "PhdInternalGuide.do?method=initInternalGuide";
	}
	function addEmpDetails(){
		document.getElementById("method").value="submitEmpDetails";
		document.internalGuideForm.submit();
	}
	
    function searchdetails(){
		document.getElementById("method").value="searchPhdEmployeeDetails";
		document.internalGuideForm.submit();
	}

	function editPhdemployee(id) {
		document.location.href = "PhdInternalGuide.do?method=editPhdemployee&id="+id;
		} 
	
	function updatePhdEmployee(){
	  document.getElementById("method").value="updatePhdEmployee";
	  document.phdEmployeeForms.submit();
	  }
	
   function deletePhdemployee(id) {
		  	deleteConfirm = confirm("Are you sure you want to delete this entry?");
			if (deleteConfirm) {
				document.location.href = "PhdInternalGuide.do?method=deletePhdemployee&id="+id;
			}
		}

			
</script>
</head>
<body>
<table width="100%" border="0">
<html:form action="/PhdInternalGuide" method="post">
	<html:hidden property="method" value="" styleId="method" />
	<html:hidden property="formName" value="internalGuideForm" />
	<html:hidden property="pageType" value="1" />
	<c:choose>
	<c:when test="${empInternal == 'edit'}">
		<html:hidden property="method" styleId="method" value="updatePhdEmployee" />
	</c:when>
	<c:otherwise>
		<html:hidden property="method" styleId="method" value="submitEmpDetails" />
	</c:otherwise>
   </c:choose>
	
	
	<input type="hidden" id="count"/>
       <tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.phd" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.phd.internal.guide" /> &gt;&gt;</span></span></td>
		</tr>
   <tr>
    <td valign="top">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"/></td>
        <td width="1271" background="images/Tcenter.gif" class="body" >
			<div align="left">
				<strong class="boxheader"><bean:message key="knowledgepro.phd.internal.guide"/></strong>
			</div>
		</td>
       <td width="15" >
	   <img src="images/Tright_1_01.gif" width="9" height="29"/>	   </td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
              <table width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
	                 <tr>
	               	    <td height="20" colspan="6" align="left">
	               	    <div align="right" style="color:red"> <span class='MandatoryMark'>* Mandatory fields</span></div>
	               	    <div id="err" style="color:red;font-family:arial;font-size:11px;"></div>
	               	    <div id="errorMessage">
	                       <FONT color="red"><html:errors/></FONT>
	                       <FONT color="green">
								<html:messages id="msg" property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out><br>
								</html:messages>
						  </FONT>
						  </div>
	               	    </td>
	                 </tr>
					
					<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="100%" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
							<tr>
								 <td class="row-odd" width="20%"><div align="right"><bean:message key="knowledgepro.usermanagement.userinfo.department" /> :</div></td>
							  	 <td class="row-even" width="30%">
								 	<html:select property="departmentId" styleId="departmentId" onchange="getEmployeeByprogramType(this.value)">
								   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
								   	<logic:notEmpty property="guideShipMap" name="internalGuideForm">
								   	<html:optionsCollection property="departmentMap" label="value" value="key"/>
								   </logic:notEmpty>
							       </html:select> 
								   </td>
									<td class="row-odd" width="20%"><div align="right"><span class="Mandatory">*</span><bean:message key="employee.info.reportto.empnm"/> :</div></td>
									 <td  class="row-even" width="30%">
								 	 <html:select property="employeeId" styleId="employeeId">
								     <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
								   	<logic:notEmpty property="employeeMap" name="internalGuideForm">
								   	<html:optionsCollection property="employeeMap" label="value" value="key"/>
								   </logic:notEmpty>
							       </html:select> 
								   </td>
							</tr>
							<tr>
								  <td width="20%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.phd.empanelmentNo"/>:</div></td>
                                   <td width="30%" class="row-even"><div align="left"> <span class="star">
                                   <html:text property="empanelmentNo" styleId="empanelmentNo" size="20" maxlength="16"/>
                                   </span></div></td>

							      <td class="row-odd" width="20%"><div align="right">
									<bean:message key="knowledgepro.Phd.dateof.award.phd" /></div></td>
								  <td class="row-even" width="30%">
									<html:text name="internalGuideForm" property="dateOfAward" styleId="dateOfAward" size="10"/>
										<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'internalGuideForm',
												// input name
												'controlname' :'dateOfAward'
												});
										</script>
								</td>
							</tr>
							<tr>
							       <td width="20%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.Phd.subject.guide.ship" /> :</div></td>
							  		<td class="row-even" width="30%">
								 	 <html:select property="disciplineId" styleId="disciplineId">
								   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
								   	<logic:notEmpty property="guideShipMap" name="internalGuideForm">
								   	<html:optionsCollection property="guideShipMap" label="value" value="key"/>
								   </logic:notEmpty>
							       </html:select> 
								   </td>
							<td width="20%" height="25" class="row-odd"><div align="right"><bean:message key="KnowledgePro.phd.noMphilScolars.guides"/>:</div></td>
                                   <td width="30%" class="row-even"><div align="left"> <span class="star">
                                   <html:text property="noMphilScolars" styleId="noMphilScolars" size="20" maxlength="9" onkeypress="return isNumberKey(event)"/>
                                   </span></div></td>
							</tr>
							<tr>
							  <td width="20%" height="25" class="row-odd"><div align="right"><bean:message key="KnowledgePro.phd.noPhdScolars.guides"/>:</div></td>
                                   <td width="30%" class="row-even"><div align="left"> <span class="star">
                                   <html:text property="noPhdScolars" styleId="noPhdScolars" size="20" maxlength="9" onkeypress="return isNumberKey(event)"/>
                                   </span></div></td>
                              <td width="20%" height="25" class="row-odd"><div align="right"><bean:message key="KnowledgePro.phd.noPhdScolarOutside.guides"/>:</div></td>
                                   <td width="30%" class="row-even"><div align="left"> <span class="star">
                                   <html:text property="noPhdScolarOutside" styleId="noPhdScolarOutside" size="20" maxlength="9" onkeypress="return isNumberKey(event)"/>
                                   </span></div></td>
							
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
							<tr>
					</tr>
						</table>
						</td>
					</tr>
				
						</table>
						</td>
					</tr>
					
					<tr>
            <td height="45" colspan="4" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      
              <tr>
                <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
				    <tr>
							<td align="center" colspan="6"> 
							
							 <c:choose>
            		<c:when test="${empInternal == 'edit'}">
              	   		<html:submit property="" styleClass="formbutton" onclick="updatePhdEmployee()"><bean:message key="knowledgepro.update"/></html:submit>&nbsp;&nbsp;
              		    <html:button property="" styleClass="formbutton" value="Close" onclick="closeWindow()"></html:button>
              		</c:when>
              		<c:otherwise>
                		<html:button property="" styleClass="formbutton" value="Submit" onclick="addEmpDetails()"><bean:message key="knowledgepro.submit"/></html:button>
                		<html:button property="" styleClass="formbutton" value="Close" onclick="closeWindow()"></html:button>	
						<html:button property="" styleClass="formbutton" value="Search" onclick="searchdetails()"></html:button>	
              		</c:otherwise>
              	</c:choose>
              		        </td>
                           </tr>
                
                </table></td>
              </tr>
          
            </table></td>
          </tr>
					
					
               
          <logic:notEmpty name="internalGuideForm" property="internalGuideList">
          <tr>
            <td height="45" colspan="4" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td ><img src="images/01.gif" width="5" height="5" /></td>
                <td width="914" background="images/02.gif"></td>
                <td><img src="images/03.gif" width="5" height="5" /></td>
              </tr>
              <tr>
                <td width="5"  background="images/left.gif"></td>
                <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                  <tr >
                    <td width="5" height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                    <td width="15" height="25" class="row-odd" align="center"><bean:message key="employee.info.reportto.empnm"/></td>
                    <td width="10" height="25" class="row-odd" align="center"><bean:message key="knowledgepro.phd.empanelmentNo"/></td>
                    <td width="10" height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.Phd.subject.guide.ship"/></td>
                    <td width="10" height="25" class="row-odd" align="center"><bean:message key="knowledgepro.Phd.dateof.award.phd"/></td>
                    <td width="12" height="25" class="row-odd" align="center"><bean:message key="KnowledgePro.phd.noMphilScolars.guides"/></td>
                    <td width="13" height="25" class="row-odd" align="center"><bean:message key="KnowledgePro.phd.noPhdScolars.guides"/></td>
                    <td width="15" height="25" class="row-odd" align="center"><bean:message key="KnowledgePro.phd.noPhdScolarOutside.guides"/></td>
                    <td width="5" class="row-odd"><div align="center"><bean:message key="knowledgepro.edit"/></div></td>
                    <td width="5" class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                  </tr>
                <c:set var="temp" value="0"/>
                <logic:iterate id="CME" name="internalGuideForm" property="internalGuideList" indexId="count">
                <c:choose>
                   <c:when test="${temp == 0}">
                   	<tr>
                   		<td height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td height="25" class="row-even" align="center"><bean:write name="CME" property="employeeName"/></td>
                   		<td height="25" class="row-even" align="center"><bean:write name="CME" property="empanelmentNo"/></td>
                   		<td height="25" class="row-even" align="center"><bean:write name="CME" property="disciplineName"/></td>
                   		<td height="25" class="row-even" align="center"><bean:write name="CME" property="dateOfAward"/></td>
                   		<td height="25" class="row-even" align="center"><bean:write name="CME" property="noMphilScolars"/></td>
                   		<td height="25" class="row-even" align="center"><bean:write name="CME" property="noPhdScolars"/></td>
                   		<td height="25" class="row-even" align="center"><bean:write name="CME" property="noPhdScolarOutside"/></td>
			            <td height="25" class="row-even" ><div align="center">
			        		<img src="images/edit_icon.gif" width="16" height="18" style="cursor:pointer" onclick="editPhdemployee('<bean:write name="CME" property="id"/>')"></div></td>
                   		<td height="25" class="row-even" ><div align="center">
                   			<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deletePhdemployee('<bean:write name="CME" property="id"/>')"></div></td>
					</tr>
                    <c:set var="temp" value="1"/>
                   	</c:when>
                    <c:otherwise>
		            <tr>
               			<td height="25" class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
               			<td height="25" class="row-white" align="center"><bean:write name="CME" property="employeeName"/></td>
               			<td height="25" class="row-white" align="center"><bean:write name="CME" property="empanelmentNo"/></td>
               			<td height="25" class="row-white" align="center"><bean:write name="CME" property="disciplineName"/></td>
               			<td height="25" class="row-white" align="center"><bean:write name="CME" property="dateOfAward"/></td>
               			<td height="25" class="row-white" align="center"><bean:write name="CME" property="noMphilScolars"/></td>
               			<td height="25" class="row-white" align="center"><bean:write name="CME" property="noPhdScolars"/></td>
               			<td height="25" class="row-white" align="center"><bean:write name="CME" property="noPhdScolarOutside"/></td>
               			<td height="25" class="row-white" ><div align="center">
               				<img src="images/edit_icon.gif" width="16" height="18" style="cursor:pointer" onclick="editPhdemployee('<bean:write name="CME" property="id"/>')"></div></td>
               			<td height="25" class="row-white" ><div align="center">
               				<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deletePhdemployee('<bean:write name="CME" property="id"/>')"></div></td>
               		</tr>
                    <c:set var="temp" value="0"/>
				  	</c:otherwise>
                  </c:choose>
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
          </tr>
          </logic:notEmpty>
                
	             <tr>
				<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>		
				<tr>
			        <td><img src="images/Tright_03_05.gif" width="9" height="29"/></td>
			        <td width="100%" background="images/TcenterD.gif"></td>
			        <td><img src="images/Tright_02.gif" width="9" height="29"/></td>
			        </tr>
			        
			        
	</table>		 
	</html:form>
			
			</table>
			
			</body>
	</html>
