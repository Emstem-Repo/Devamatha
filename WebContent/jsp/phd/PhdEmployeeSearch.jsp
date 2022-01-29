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

	function closeWindow(){
		document.location.href = "PhdEmployeeApplication.do?method=initPhdEmployeesearch";
	}
	function addEmpDetails(){
		document.location.href = "PhdEmployeeApplication.do?method=initPhdEmployee";
	}
	
	function updatePhdEmployee(){
	  document.getElementById("method").value="updatePhdEmployee";
	  document.phdEmployeeForms.submit();
	  }

	function editPhdemployee(id) {
		document.location.href = "PhdEmployeeApplication.do?method=editPhdemployee&id="+id;
		}
	
	   function deletePhdemployee(id) {
		  	deleteConfirm = confirm("Are you sure you want to delete this entry?");
			if (deleteConfirm) {
				document.location.href = "PhdEmployeeApplication.do?method=deletePhdemployee&id="+id;
			}
		}
		
    function searchdetails(){
		document.getElementById("method").value="searchPhdDetails";
		document.phdEmployeeForms.submit();
	}
			
</script>
</head>
<body>
<table width="100%" border="0">
<html:form action="/PhdEmployeeApplication" enctype="multipart/form-data">
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" value="" styleId="method" />
	<html:hidden property="formName" value="phdEmployeeForms" />
	<html:hidden property="mode" styleId="mode" value="" />
	<html:hidden property="focusValue" styleId="focusValue"/>
	<input type="hidden" id="count"/>
       <tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.phd" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.Phd.info.label" /> &gt;&gt;</span></span></td>
		</tr>
   <tr>
    <td valign="top">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"/></td>
        <td width="1271" background="images/Tcenter.gif" class="body" >
			<div align="left">
				<strong class="boxheader"><bean:message key="knowledgepro.Phd.info.label"/></strong>
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
									 <td class="row-odd" width="20%">
									 <div align="right">
									     <bean:message key="knowledgepro.admin.name"/> :
									  </div>
									  </td>
										<td  class="row-even" width="30%">
											<html:text property="nameSearch" styleId="nameSearch" size="40" maxlength="50" style="text-transform:uppercase;"></html:text>
										</td>
							        	<td class="row-odd" width="20%"><div align="right">
									  <bean:message key="knowledgepro.Phd.subject.guide.ship" /> :</div>
							  		  </td>
							  		 <td  class="row-even" width="30%">
								 	 <html:select property="guideShipSearch">
								   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
								   	<logic:notEmpty property="guideShipMap" name="phdEmployeeForms">
								   	<html:optionsCollection property="guideShipMap" label="value" value="key"/>
								   </logic:notEmpty>
							       </html:select> 
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
						    <html:button property="" styleClass="formbutton" value="Add" onclick="addEmpDetails()"></html:button>&nbsp;&nbsp;
							<html:button property="" styleClass="formbutton" value="Close" onclick="closeWindow()"></html:button>&nbsp;&nbsp;	
							<html:button property="" styleClass="formbutton" value="Search" onclick="searchdetails()"></html:button>	
              		        </td>
                           </tr>
                
                </table></td>
              </tr>
          
            </table></td>
          </tr>
					
					
               
          <logic:notEmpty name="phdEmployeeForms" property="phdEmployeeDetails">
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
                    <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                     <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.admin.name"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.phd.empanelmentNo"/></td>
                    <td height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.employee.gender.required"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.admin.dateofbirth"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="admissionForm.studentinfo.birthplace.label"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.Phd.domicile.status"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="admissionFormForm.emailId"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.employee.panNo"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.Phd.dateof.award.phd"/></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.edit"/></div></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                  </tr>
                <c:set var="temp" value="0"/>
                <logic:iterate id="CME" name="phdEmployeeForms" property="phdEmployeeDetails" indexId="count">
                <c:choose>
                   <c:when test="${temp == 0}">
                   	<tr>
                   		<td width="5%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td width="10%" height="25" class="row-even" align="center"><bean:write name="CME" property="name"/></td>
                   		<td width="5%"  height="25"  class="row-even" align="center"><bean:write name="CME" property="empanelmentNo"/></td>
                   		<td width="10%" height="25" class="row-even" align="center"><bean:write name="CME" property="gender"/></td>
                   		<td width="10%" height="25" class="row-even" align="center"><bean:write name="CME" property="dateOfBirth"/></td>
                   		<td width="10%" height="25" class="row-even" align="center"><bean:write name="CME" property="placeOfBirth"/></td>
                   		<td width="10%" height="25" class="row-even" align="center"><bean:write name="CME" property="domicialStatus"/></td>
                   		<td width="10%" height="25" class="row-even" align="center"><bean:write name="CME" property="email"/></td>
                   		<td width="10%" height="25" class="row-even" align="center"><bean:write name="CME" property="panNo"/></td>
                   		<td width="10%" height="25" class="row-even" align="center"><bean:write name="CME" property="dateOfAward"/></td>
			            <td width="5%" height="25" class="row-even" ><div align="center">
			        		<img src="images/edit_icon.gif" width="16" height="18" style="cursor:pointer" onclick="editPhdemployee('<bean:write name="CME" property="id"/>')"></div></td>
                   		<td width="5%" height="25" class="row-even" ><div align="center">
                   			<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deletePhdemployee('<bean:write name="CME" property="id"/>')"></div></td>
					</tr>
                    <c:set var="temp" value="1"/>
                   	</c:when>
                    <c:otherwise>
		            <tr>
               			<td width="5%" height="25" class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
               			<td width="10%" height="25" class="row-white" align="center"><bean:write name="CME" property="name"/></td>
               			<td width="5%" height="25" class="row-white" align="center"><bean:write name="CME" property="empanelmentNo"/></td>
               			<td width="10%" height="25" class="row-white" align="center"><bean:write name="CME" property="gender"/></td>
               			<td width="10%" height="25" class="row-white" align="center"><bean:write name="CME" property="dateOfBirth"/></td>
               			<td width="10%" height="25" class="row-white" align="center"><bean:write name="CME" property="placeOfBirth"/></td>
               			<td width="10%" height="25" class="row-white" align="center"><bean:write name="CME" property="domicialStatus"/></td>
               			<td width="10%" height="25" class="row-white" align="center"><bean:write name="CME" property="email"/></td>
               			<td width="10%" height="25" class="row-white" align="center"><bean:write name="CME" property="panNo"/></td>
               			<td width="10%" height="25" class="row-white" align="center"><bean:write name="CME" property="dateOfAward"/></td>
               			<td width="5%" height="25" class="row-white" ><div align="center">
               				<img src="images/edit_icon.gif" width="16" height="18" style="cursor:pointer" onclick="editPhdemployee('<bean:write name="CME" property="id"/>')"></div></td>
               			<td width="5%" height="25" class="row-white" ><div align="center">
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
			<script type="text/javascript">

			
			var focusField=document.getElementById("focusValue").value;
		    if(focusField != 'null'){  
			    if(document.getElementById(focusField)!=null)      
		            document.getElementById(focusField).focus();
			}
		</script>
	</html>
			
		
			
	


