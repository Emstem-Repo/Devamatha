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
<title>Employee Information</title>
<LINK REL=StyleSheet HREF= "css/styles.css" TYPE="text/css">
<script language="JavaScript" src="js/admissionform.js"></script>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script type="text/javascript">

function shows1(obj,msg){
	document.getElementById("messageBox1").style.top=obj.offsetTop;
	document.getElementById("messageBox1").style.left=obj.offsetLeft+obj.offsetWidth+5;
	document.getElementById("contents1").innerHTML=msg;
	document.getElementById("messageBox1").style.display="block";
	}
function hides1(){
	document.getElementById("messageBox1").style.display="none";
}


	var destId;
	function closeWindow(){
		document.getElementById("method").value="getDeptSearchedEmployee";
		document.EmployeeInfoViewForm.submit();
	}

	
	
</script>
</head>
<body>
<table width="100%" border="0">
<html:form action="/EmployeeInfoViewDisplay" enctype="multipart/form-data">
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" value="" styleId="method" />
	<html:hidden property="formName" value="EmployeeInfoViewForm" />
	<html:hidden property="mode" styleId="mode" value="" />
       <tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.sec.EmployeeCategory" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.employee.View" /> &gt;&gt;</span></span></td>
		</tr>
 
 
  <tr>
    <td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"/></td>
        <td width="1271" background="images/Tcenter.gif" class="body" >
			<div align="left">
				<strong class="boxheader"><bean:message key="knowledgepro.employee.View"/></strong>
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
								<td width="914" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
 
 
 							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
								<table width="100%" cellspacing="1" cellpadding="2">
									<tr>
								    <td class="row-odd" width="100%" align="center">
								    
									 <bean:write name="EmployeeInfoViewForm" property="teachingStaff" />
									
									</td>
								  							   	 
								  </tr>
							</table>			
								</td>
								<td width="5" height="30" background="images/left.gif"></td>
							</tr>
							<tr>
								<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
								<td background="images/05.gif"></td>
								<td><img src="images/06.gif" /></td>
							</tr>
						</table>
						</td>
					</tr>
					
					<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="left" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="914" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							
					
					<tr>
									<td width="5" background="images/left.gif"></td>
									<td width="100%" height="22" align="left" valign="top">
									<table width="100%" height="22" border="0" cellpadding="0">
										<tr class="row-white">
											<!--  <td colspan="2" class="row-odd">
											<div align="left">Photo:</div>
											</td>-->

											<td width="100%" height="50" align="center" class="row-even">
												<%if(CMSConstants.LINK_FOR_CJC){ %>
													<img src='<%=request.getContextPath()%>/PhotoServlet' height="186Px" width="144Px" />
												<%} else{%>
													<img src='<%=request.getAttribute("EMP_IMAGE")%>' height="186Px" width="144Px" />
												<%} %>
											</td>
										</tr>
									</table>
									</td>
									<td background="images/left.gif" width="5" height="22"></td>
								</tr>
							<tr>
								<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
								<td background="images/05.gif"></td>
								<td><img src="images/06.gif" /></td>
							</tr>
						</table>
						</td>
					</tr>
					
					
					
					
					
					
					<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>
					
					
					<tr> 
					  	<td colspan="2" class="heading" align="left" height="25">
							<bean:message key="knowledgepro.employee.personal.details"/>
						</td>
					</tr>
					
					<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="left" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="914" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									 <td class="row-odd" width="15%" height="25">
									 <div align="left">
									     <bean:message key="knowledgepro.admin.name"/>
									  </div>
									  </td>
										<td  class="row-even" height="25" width="35%">
											<bean:write name="EmployeeInfoViewForm" property="name"  />
										</td>
										 <td class="row-odd" width="15%" height="25">
									<div align="left">
									     <bean:message key="knowledgepro.admin.uId"/>
									  </div>
									  </td>
									<td  class="row-even" height="25" width="35%">
											<bean:write name="EmployeeInfoViewForm" property="uId" />
									</td>
							</tr>
								<tr>
									 <td class="row-odd" width="15%" height="25">
									 <div align="left">
									     <bean:message key="knowledgepro.admin.code"/>
									 </div>
									  </td>
							
									<td  class="row-even" height="25" width="35%">
										<bean:write name="EmployeeInfoViewForm" property="code" />
									</td>
									<td class="row-odd" width="15%" height="25">
									 <div align="left">
									     <bean:message key="knowledgepro.admin.fingerprintid"/>
									 </div>
									  </td>
							
									<td  class="row-even" height="25" width="35%">
											<bean:write name="EmployeeInfoViewForm" property="fingerPrintId" />
									</td>
								</tr>
								<tr> 
							  	 <td class="row-odd" height="25" width="15%">
							  	 <div align="left">
							      	<bean:message key="knowledgepro.admin.nationality"/>
							     </div>
							     </td>
								 <td  class="row-even" height="25" width="35%">
								 	 <bean:write name="EmployeeInfoViewForm" property="nationalityId" />
								   
								 </td>
							  	<td class="row-odd" height="25" width="15%"> 
									<div align="left">
									<bean:message key="knowledgepro.employee.gender.required" /></div>
							  	</td>
								<td width="35%" class="row-even" align="left" height="25" >
									<bean:write name="EmployeeInfoViewForm" property="gender" />
								</td> 
							  </tr>
							  
							  <tr> 
							  	 <td class="row-odd" height="25" width="15%"> 
									<div align="left">
									<bean:message key="employee.info.personal.MaritalSts" /></div>
							  	</td>
								 <td  class="row-even" height="25" width="35%">
								 	 <bean:write name="EmployeeInfoViewForm" property="maritalStatus"/>
								 	 
								 </td>
							  	<td class="row-odd" height="25" width="15%"> 
									<div align="left">
									<bean:message key="knowledgepro.admin.dateofbirth" /></div>
							  	</td>
								<td   class="row-even" height="25" width="35%">
									<bean:write name="EmployeeInfoViewForm" property="dateOfBirth" />
										
								
								 	
								 </td>
							  </tr>
							   <tr> 
							  <td height="25" class="row-odd" width="15%"><div align="left">Blood Group:</div></td>
								<td height="25" class="row-even" width="35%">
							<bean:write name="EmployeeInfoViewForm" property="bloodGroup" />
							</td>
							<td class="row-odd" height="25" width="15%">
							<div align="left" >
							<bean:message key="admissionForm.studentinfo.religion.label" /></div></td>
								<td width="35%" class="row-even" height="25" >
							<bean:write name="EmployeeInfoViewForm" property="religionId" />
									
				  </td>
				  </tr>
					  <tr>
					  	<td class="row-odd" height="25" width="15%"><div align="left" ><bean:message key="knowledgepro.employee.panNo" /></div></td>
                  		<td class="row-even" height="25" width="35%"><span class="star">
						<bean:write name="EmployeeInfoViewForm" property="panno" /></span></td>
					
				 		 <td class="row-odd" height="25" width="15%"> 
								<div align="left">
									<bean:message key="knowledgepro.employee.officialEmailId" /></div>
							  	</td>
								 <td  class="row-even" height="25" width="35%">
								 	<bean:write name="EmployeeInfoViewForm" property="officialEmail"/>
								 </td>
					</tr>
					<tr> 
							  	 <td class="row-odd" height="25" width="15%"> 
									<div align="left">
									<bean:message key="admissionFormForm.emailId" /></div>
							  	</td>
								 <td  class="row-even" height="25" width="35%">
								 	<bean:write name="EmployeeInfoViewForm" property="email"/>
								 </td>
							  		<td class="row-odd" height="25" width="15%"> 
									<div align="left">
									<bean:message key="knowledgepro.employee.reservation.category" /></div>
							  		</td>
									<td class="row-even" height="25" width="35%">
										
									<bean:write name="EmployeeInfoViewForm" property="reservationCategory" />
						</td> 
				 </tr>
							  
				<tr>
									
									<td class="row-odd" width="15%" height="25"> 
									<div align="left">
									<bean:message key="knowledgepro.employee.mobile" /></div>
							  		</td>
									<td class="row-even" width="35%" height="25"> 
									<bean:write name="EmployeeInfoViewForm" property="mobileNo1" />
									</td>
								
									<td class="row-odd" width="15%" height="25"> 
									<div align="left">
									<bean:message key="knowledgepro.employee.bankAccNo" /></div>
							  		</td>
									<td class="row-even" width="35%" height="25"> 
									<bean:write name="EmployeeInfoViewForm" property="bankAccNo"/>
									</td>
									</tr>
					<tr>
									<td class="row-odd" width="15%" height="25"> 
									<div align="left">
									<bean:message key="knowledgepro.employee.PfAccNo" /></div>
							  		</td>
									<td class="row-even" width="35%" height="25"> 
									<bean:write name="EmployeeInfoViewForm" property="pfNo"/>
									</td>
								
									<td class="row-odd" width="15%" height="25"> 
									<div align="left" >
									<bean:message key="knowledgepro.employee.fourWheelerNo" /></div>
							  		</td>
									<td class="row-even" width="35%" height="25"> 
									<bean:write name="EmployeeInfoViewForm" property="fourWheelerNo"/>
									</td>
									</tr>
							<tr>
									<!-- <td class="row-odd" width="13%"> 
									<div align="left">
									<bean:message key="knowledgepro.employee.vehicleNo" /></div>
							  		</td>
									<td class="row-even" width="37%"> 
									<html:text property="vehicleNo"></html:text>
									</td>-->
									<td class="row-odd" width="15%" height="25"> 
									<div align="left" >
									<bean:message key="knowledgepro.employee.twoWheelerNo" /></div>
							  		</td>
									<td class="row-even" width="35%" height="25"> 
									<bean:write name="EmployeeInfoViewForm" property="twoWheelerNo"/>
									</td>
								<td class="row-odd" width="15%"> 
									<div align="left">
									<bean:message key="knowledgepro.employee.smartCardNo" /></div>
							  		</td>
									<td class="row-even" width="35%"> 
									<bean:write name="EmployeeInfoViewForm" property="smartCardNo"/>
									</td>
										
								</tr>	
								<tr>
									<td class="row-odd" width="15%" height="25"> 
									<div align="left" >
									Extension Number:</div>
							  		</td>
									<td class="row-even" width="35%" height="25" colspan="3"> 
									<bean:write name="EmployeeInfoViewForm" property="extensionNumber"/>
									</td>
								</tr>	
								<tr  class="row-odd">
									<td height="25"> 
									<bean:message key="knowledgepro.employee.Home.Telephone" />
									
							  		</td>
							  		<td height="25"> 
									<bean:message key="knowledgepro.employee.county" />
									<bean:message key="knowledgepro.employee.state.code" />
									<bean:message key="knowledgepro.employee.Telephone" />
							  	</td>
							  	<td height="25"> 
									<bean:message key="knowledgepro.employee.Work.Telephone" />
									
							  		</td>
							  		<td height="25"> 
									<bean:message key="knowledgepro.employee.county" />
									<bean:message key="knowledgepro.employee.state.code" />
									<bean:message key="knowledgepro.employee.Telephone" />
							  		</td>
							  	</tr>
							  	<tr class="row-even">
							  	<td></td>
									<td  height="25"> 
									<bean:write name="EmployeeInfoViewForm" property="homePhone1" />
									<bean:write name="EmployeeInfoViewForm" property="homePhone2" />
									<bean:write name="EmployeeInfoViewForm" property="homePhone3" />
									</td>
									
									<td></td>
									
									
									<td height="25"> 
									<bean:write name="EmployeeInfoViewForm" property="workPhNo1" />
									<bean:write name="EmployeeInfoViewForm" property="workPhNo2" />
									<bean:write name="EmployeeInfoViewForm" property="workPhNo3" />
									</td>
								</tr>
								
							</table>			
								
								</td>
								<td width="5" height="30" background="images/left.gif"></td>
							</tr>
							<tr>
								<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
								<td background="images/05.gif"></td>
								<td><img src="images/06.gif" /></td>
							</tr>
						</table>
						</td>
					</tr>
					
					
					<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>
					
					
					<tr> 
					  	<td colspan="2" class="heading" align="left">
							<bean:message key="admissionForm.studentinfo.currAddr.label"/>
						</td>
					</tr>
					
					
					
					<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="left" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="914" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
								<table width="100%" cellspacing="1" cellpadding="2">
									
									  	<tr>
									   	 	<td class="row-odd" width="15%" height="25">
									   	 		<div align="left">
									     	 	<bean:message key="admissionForm.studentinfo.addrs1.label"/>
									     	 	</div>
									    	</td>
										 	<td  class="row-even" width="35%" height="25">
												 <bean:write name="EmployeeInfoViewForm" property="currentAddressLine1" />
											</td>
											
											<td class="row-odd" width="15%" height="25"> 
												<div align="left">
												<bean:message key="admissionForm.studentinfo.addrs2.label"/>
												</div>
											</td>
											<td class="row-even" width="35%" height="25">
												 <bean:write name="EmployeeInfoViewForm" property="currentAddressLine2" />
										 	</td>
										</tr>
										 
										<tr>
										 
										 <td class="row-odd" height="25" width="15%">
									  	 <div align="left">
									      	<bean:message key="employee.info.contact.City"/>
									      </div>
									     </td>
										 <td  class="row-even" height="25" width="35%">
										 	<bean:write name="EmployeeInfoViewForm" property="currentCity" />
										 </td>	
									  	 <td class="row-odd" align="left" height="25" width="15%">
									      	<bean:message key="knowledgepro.usermanagement.userinfo.pincode"/>
									     </td>
										 <td  class="row-even" height="25" width="35%">
										 	<bean:write name="EmployeeInfoViewForm" property="currentZipCode" />
									  	</tr>
										
									   <tr>
									   	 <td class="row-odd" height="25" width="15%">
									   	 	<div align="left">
											  <bean:message key="knowledgepro.admin.country.report"/>
											</div>
									     </td>
									     
									     <td  class="row-even" height="25" width="35%">
											<bean:write name="EmployeeInfoViewForm" property="currentCountryId" />
									    
										</td>
										
									   	 <td class="row-odd" height="25" width="15%">
									   	 <div align="left">
											  <bean:message key="knowledgepro.admin.state.report"/>
										</div>
									     </td>
										 <td  class="row-even" height="25" width="35%">
										 <bean:write name="EmployeeInfoViewForm" property="currentState" />
									     <input type="hidden" id="tempState" name="tempState" value='<bean:write name="EmployeeInfoViewForm" property="currentState"/>' />
									    
									     <div id="otehrState">
									     	<bean:write name="EmployeeInfoViewForm" property="otherCurrentState" />
									     </div>
									     </td>
									  </tr>
									  	
									 
								</table>			
								
								</td>
								<td width="5" height="30" background="images/left.gif"></td>
							</tr>
							<tr>
								<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
								<td background="images/05.gif"></td>
								<td><img src="images/06.gif" /></td>
							</tr>
						</table>
						</td>
					</tr>
					
						
					<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>
									
						<tr>
							<td colspan="2" class="heading" align="left" height="25">&nbsp;
							<div id="currLabel">
							<bean:message
								key="admissionForm.studentinfo.permAddr.label" />
							</div>
							</td>
						</tr>
						
						
						<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="left" cellpadding="0"
							cellspacing="0" id="currTable">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="914" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
								<table width="100%" cellspacing="1" cellpadding="2">
								
									  	<tr>
									   	 	<td class="row-odd" width="15%" height="25">
									   	 		<div align="left">
									      		<bean:message key="admissionForm.studentinfo.addrs1.label"/>
									     		</div>
									    	</td>
										 	<td  class="row-even" width="35%" height="25">
												 <bean:write name="EmployeeInfoViewForm" property="addressLine1" />
											</td>
											<td class="row-odd" width="15%" height="25"> 
												<div align="left">
									      		<bean:message key="admissionForm.studentinfo.addrs2.label"/>
									     		</div>
											</td>
											<td class="row-even" width="35%" height="25">
												 <bean:write name="EmployeeInfoViewForm" property="addressLine2" />
										 	</td>
										</tr>
										 
										<tr>
										 	 <td class="row-odd" height="25">
									  	 <div align="left">
									      	<bean:message key="employee.info.contact.City"/>
									      </div>
									     </td>
										 <td  class="row-even" height="25">
										 	<bean:write name="EmployeeInfoViewForm" property="city" />
										 </td>
									  	 <td class="row-odd" align="left" height="25">
									      	<bean:message key="knowledgepro.usermanagement.userinfo.pincode"/>
									     </td>
										 <td  class="row-even" height="25">
										 	<bean:write name="EmployeeInfoViewForm" property="permanentZipCode" />
										 </td>
									  	</tr>
										
									   <tr>
									   	 <td class="row-odd" height="25">
									   	 	<div align="left">
											  <bean:message key="knowledgepro.admin.country.report"/>
											</div>
									     </td>
										 <td  class="row-even" height="25">
											<bean:write name="EmployeeInfoViewForm" property="countryId" />
										</td>
										
									   	 <td class="row-odd" height="25">
									   	 <div align="left">
											  <bean:message key="knowledgepro.admin.state.report"/>
										</div>
									     </td>
										 <td  class="row-even" height="25">
										 <bean:write name="EmployeeInfoViewForm" property="stateId" />
									     <input type="hidden" id="tempPermanentState" name="tempPermanentState" value='<bean:write name="EmployeeInfoViewForm" property="stateId"/>' />
									    
									     <div id="otehrPermState" height="25">
									     	<bean:write name="EmployeeInfoViewForm" property="otherPermanentState" />
									     </div>
									     </td>
									     </tr></table>
									    	
								
								</td>
								<td width="5" height="30" background="images/left.gif"></td>
							</tr>
							<tr>
								<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
								<td background="images/05.gif"></td>
								<td><img src="images/06.gif" /></td>
							</tr>
						</table>
						</td>
					</tr>
					
					
						
					
						
					<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>
					
				   	 	<tr>
					<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.employee.Job"/>
					</td>
					</tr>
					
					<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="left" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="914" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
       
        				 <table width="100%" cellspacing="1" cellpadding="2">
              <tr >
                <td width="15%" height="25" class="row-odd" height="25"><div align="left" ><bean:message key="knowledgepro.employee.empType"/></div></td>
                <td width="35%" class="row-even" height="25">
                 <bean:write name="EmployeeInfoViewForm" property="emptypeId" />
                              </td>
                <td width="15%" class="row-odd" height="25"><div align="left" ><bean:message key="knowledgepro.employee.joinedDate"/> 
                   </div></td>
                <td width="35%" class="row-even" height="25"><span class="star">
                 <bean:write name="EmployeeInfoViewForm" property="dateOfJoining" />
                </span> </td>
              </tr>
              <tr >
                <td height="25" class="row-odd" ><div align="left" ><bean:message key="knowledgepro.employee.rejoinDate"/> </div></td>
                <td class="row-even" height="25"><span class="star">
                 <bean:write name="EmployeeInfoViewForm" property="rejoinDate" />
                </span></td>
                <td class="row-odd" height="25"><div align="left"  ><bean:message key="knowledgepro.employee.DateOfRetirement"/>  </div></td>
                <td height="25" class="row-even" height="25"> <bean:write name="EmployeeInfoViewForm" property="retirementDate" />
                              </td>
              </tr>
              <tr >
                <td height="25" class="row-odd" height="25"><div align="left" ><bean:message key="knowledgepro.employee.active"/> </div></td>
                <td class="row-even" height="25"><span class="star">
                 <bean:write name="EmployeeInfoViewForm" property="active" />
                 </span></td>
                <td width="100" class="row-odd" height="25"><div align="left" ><bean:message key="knowledgepro.employee.streamDetails"/></div></td>
                <td height="25" class="row-even" height="25"><span class="star">
                 <bean:write name="EmployeeInfoViewForm" property="streamId" />
                </span>				
                </td>
              </tr>
              <tr>
                <td width="100" class="row-odd" height="25"><div align="left" ><bean:message key="knowledgepro.employee.worklocation"/></div></td>
                <td height="25" class="row-even" height="25"><span class="star">
                <bean:write name="EmployeeInfoViewForm" property="workLocationId" />
                </span></td>
                <td width="100" class="row-odd" height="25"><div align="left" ><bean:message key="knowledgepro.employee.designation"/> </div></td>
                <td width="22%" class="row-even" height="25">
				<bean:write name="EmployeeInfoViewForm" property="designationPfId"/>
				   
						</td>
              </tr>
             <tr >
			    <td width="100"  class="row-odd" height="25"><div align="left" ><bean:message key="knowledgepro.employee.Department"/>  </div></td>
                <td width="25%" class="row-even" height="25">
                <bean:write name="EmployeeInfoViewForm" property="departmentId" />
				</td>
				
				<!-- <td width="100" class="row-odd" height="25"><div align="left" ><bean:message key="knowledgepro.employee.grade"/> </div></td>
                <td width="22%" class="row-even" height="25">
                               
				<bean:write name="EmployeeInfoViewForm" property="grade" />
							</td>-->
				<!--  <td width="100"  class="row-odd" height="25"><div align="left" ><bean:message key="knowledgepro.employee.ReportTo"/>  </div></td>
                <td width="25%" class="row-even" height="25">
                
				<bean:write name="EmployeeInfoViewForm" property="reportToId" />
				</td>-->
				
				<td width="100"  class="row-odd" height="25"><div align="left" ><bean:message key="knowledgepro.employee.title"/>  </div></td>
                <td width="25%" class="row-even" height="25" >
                <bean:write name="EmployeeInfoViewForm" property="titleId" />
				</td>
								
			   
		             
                
              
                
              </tr>
 </table></td>
								<td width="5" height="30" background="images/left.gif"></td>
							</tr>
							<tr>
								<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
								<td background="images/05.gif"></td>
								<td><img src="images/06.gif" /></td>
							</tr>
						</table>
						</td>
					</tr>
<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
				</tr>	






	<tr>
					<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.employee.professional.exp"/>
					</td>
					</tr>
					
					<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="left" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="914" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
									<!-- <tr>
									  	<td class="row-odd" width="75"> 
											<div align="left"><span class="Mandatory">*</span>
											<bean:message key="knowledgepro.employee.previous.working" /></div>
								  		</td>
										<td class="row-even" width="100" colspan="4">
											<nested:radio property="currentlyWorking" value='YES'>
												<bean:message key="knowledgepro.yes" />
												</nested:radio> 
												<nested:radio property="currentlyWorking" value='NO'>
												<bean:message key="knowledgepro.no" />
											</nested:radio>									
										</td> 
									</tr>-->
									
									<tr>
									        <td class="row-odd" align="left" width="100" height="25">  
											
											<bean:message key="knowledgepro.usermanagement.userinfo.designation" />
									  		</td>
									       <td  class="row-even" colspan="2" height="25">
									      		 <bean:write  name="EmployeeInfoViewForm"  property="designation" />
									       
									     </td>
										<td class="row-odd" align="left" width="100" height="25"> 
											
											<bean:message key="knowlegepro.employee.address.organization" />
									  		</td>
										<td class="row-even" height="25" colspan="2">
												<bean:write  name="EmployeeInfoViewForm"  property="orgAddress" />
										</td>
									</tr>
									
									<tr>
										<td class="row-even" width="12%" height="25"> 
										</td>
										<td class="row-odd" width="22%">
									  	 	<div align="left">
									      	<bean:message key="knowledgepro.employee.subject.area"/>
									      	</div>
									     </td>
										<td class="row-odd" width="12%" height="25" align="left"> 
											<bean:message key="knowledgepro.employee.years" />
									  		</td>
										
										<td class="row-odd" width="12%" align="left"> 
											<bean:message key="knowledgepro.employee.months"/>
										</td>
										<td class="row-odd" width="22%" align="left"> 
											<bean:message key="knowledgepro.admin.sec.Designation"/>
										</td>
										<td class="row-odd" width="22%" align="left" > 
											<bean:message key="knowledgepro.institution.col"/>
										</td>
									</tr>
									<html:hidden property="teachingExpLength" name="EmployeeInfoViewForm" styleId="teachingExpLength"/>
									<html:hidden property="industryExpLength" name="EmployeeInfoViewForm" styleId="industryExpLength"/>
									<logic:notEmpty property="employeeInfoTONew.teachingExperience" name="EmployeeInfoViewForm">     
									<nested:iterate property="employeeInfoTONew.teachingExperience" name="EmployeeInfoViewForm" id="teachingExperience" indexId="count" >
					<tr>
									<c:choose>
										<c:when test="${count==0}">
											<td class="row-odd"> 
												
												<bean:message key="knowledgepro.employee.teaching.experience" />
										  	</td>
										  	
										  	<td  class="row-even">
										  		<bean:write  name="EmployeeInfoViewForm"  property="empSubjectAreaId"/>
										</c:when>
										<c:otherwise>
											<td class="row-odd" height="20"> 
									  	</td>
									  	<td class="row-even" height="20"> 
									  	</td>
										</c:otherwise>
									</c:choose>
									<%String teaching="teach_"+String.valueOf(count);%>
			                            <td class="row-even" align="left" height="25"> 
			                             		<bean:write  name="teachingExperience"  property="teachingExpYears" />
										</td>
									<%String teachingMonth="teach_month_"+String.valueOf(count);%>	
										<td class="row-even" align="left" height="25">
												<bean:write  name="teachingExperience"  property="teachingExpMonths" />
										</td>
										<td class="row-even" align="left" height="25">
												<bean:write  name="teachingExperience"  property="currentTeachnigDesignation" />
								         </td>
										
										<td class="row-even" align="left" height="25"> 
												<bean:write  name="teachingExperience"  property="currentTeachingOrganisation" />
										</td>
							 </tr>
			                         </nested:iterate>
			      
			                         <tr>
			                         	<td  class="row-even" align="left" colspan="6" height="25">
										</td> 
			                         </tr>
									</logic:notEmpty>
									
									
									<logic:notEmpty property="employeeInfoTONew.experiences" name="EmployeeInfoViewForm">          
									<nested:iterate property="employeeInfoTONew.experiences" name="EmployeeInfoViewForm" id="experiences" indexId="count1" >
									<tr>
									
									<c:choose>
										<c:when test="${count1==0}">
											<td class="row-odd"> 
												
												<bean:message key="knowledgepro.employee.industry.exp" />
										  	</td>
										  	<td  class="row-even">
										  		 <bean:write  name="EmployeeInfoViewForm"  property="industryFunctionalArea" />
										</c:when>
										<c:otherwise>
											<td class="row-odd" height="20"> 
									  	</td>
									  	<td class="row-even" height="20"> 
									  	</td>
										</c:otherwise>
									</c:choose>
																		
									 
			                           
									
			                            <td class="row-even" align="left" height="25">
			                           		 <bean:write  name="experiences"  property="industryExpYears" />
										</td>
									
										<td class="row-even" align="left" height="25">
											<bean:write  name="experiences"  property="industryExpMonths" />
										</td>
										
										<td class="row-even" align="left" height="25">
											<bean:write  name="experiences"  property="currentDesignation" />
										</td>
										
										<td class="row-even" align="left" height="25">
											<bean:write  name="experiences"  property="currentOrganisation" />
										</td>
									</tr>
			                         </nested:iterate>
			                        
									</logic:notEmpty>
									
									
									<tr>
									  	<td class="row-odd" width="100" height="25"> 
									  		
											<bean:message key="knowledgepro.employee.total.exp"/>
											
									  	</td>
										<td class="row-even" colspan="2">
											 <bean:write  name="EmployeeInfoViewForm"  property="expYears" />&nbsp;&nbsp;Years&nbsp;&nbsp;
											 <bean:write  name="EmployeeInfoViewForm"  property="expMonths" />&nbsp;&nbsp;Months
										</td> 
										<td class="row-odd" width="100" height="25"> 
									  		
											<bean:message key="knowledgepro.employee.Relevanttotal.exp"/>
											
									  	</td>
										<td class="row-even" colspan="2">
											 <bean:write  name="EmployeeInfoViewForm"  property="relevantExpYears" />&nbsp;&nbsp;Years&nbsp;&nbsp;
										 	 <bean:write  name="EmployeeInfoViewForm"  property="relevantExpMonths" />&nbsp;&nbsp;Months
										</td> 
									  </tr>
									  <tr> 
									  	
									  	<!-- <td class="row-odd" width="100" height="25">
									  	 	<div align="left" >
									      	<bean:message key="knowledgepro.employee.subject.area"/>
									      	</div>
									     </td>
										  <td  class="row-even" height="25" colspan="2">
											  <bean:write  name="EmployeeInfoViewForm"  property="empSubjectAreaId" />
										 </td>-->
									 
									    <td class="row-odd"> 
									  			<div align="left">
												<bean:message key="knowledgepro.employee.eligibility.test"/>
												</div>
									  		</td>
											<td  class="row-even" colspan="4" height="25" colspan="6">
											<bean:write  name="EmployeeInfoViewForm"  property="eligibilityTestdisplay" />
											 <!--<nested:checkbox property="eligibilityTestNET" value="NET" disabled="true">NET</nested:checkbox>
												<nested:checkbox property="eligibilityTestSLET" value="SLET" disabled="true">SLET</nested:checkbox>
												<nested:checkbox property="eligibilityTestSET" value="SET" disabled="true">SET</nested:checkbox>
												<nested:checkbox property="eligibilityTestNone" value="None" disabled="true">None</nested:checkbox>-->
										 </td>
									  </tr>
									</table>
								</td>
								<td width="5" height="30" background="images/left.gif"></td>
							</tr>
							<tr>
								<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
								<td background="images/05.gif"></td>
								<td><img src="images/06.gif" /></td>
							</tr>
						</table>
						</td>
					</tr>
							
				<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
		</tr>	
		<tr>
					<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.employye.educational.details"/>
					</td>
				</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="left" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="914" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
								<table width="100%" cellspacing="1" cellpadding="2">
								 <tr>
								  <td class="row-odd" width="100" height="25">
									  	 	<div align="left">
									      	<bean:message key="knowledgepro.employee.education.qualification.level"/>
									      	</div>
									     </td>
										 <td  class="row-even" colspan="2" height="25">
										 	<bean:write  name="EmployeeInfoViewForm"  property="qualificationId" />
										
										 </td>
										  <td width="100"  class="row-odd" height="25"><div align="left"><bean:message key="knowledgepro.employee.HighQyalForAlbum"/></div></td>
                						<td width="22%" class="row-even" colspan="2">
                							<bean:write  name="EmployeeInfoViewForm"  property="highQualifForAlbum" />
               							
								</td>
								 </tr>
								
									<tr >
									<td class="row-odd" width="15%" height="25">
										<bean:message key="knowledgepro.employee.education.qualification"/>
									</td>
									<td class="row-odd" width="15%">
										<bean:message key="knowledgepro.exam.course"/>
									</td>
									<td class="row-odd" width="15%" height="25">
										<bean:message key="knowledgepro.exam.specialization"/>
									</td>
									<td class="row-odd" width="15%" height="25">
										<bean:message key="knowledgepro.employee.yeat.completion"/>
									</td>
									<td class="row-odd" width="15%" height="25">
										<bean:message key="knowledgepro.employee.grade"/>
									</td>
									<td class="row-odd" width="15%" height="25">
										<bean:message key="knowledgepro.employee.institute.univetsity"/>
									</td>
									
								</tr>
								<logic:notEmpty property="employeeInfoTONew.empQualificationFixedTo" name="EmployeeInfoViewForm">
									<nested:iterate id="qualificationTo" property="employeeInfoTONew.empQualificationFixedTo" name="EmployeeInfoViewForm" indexId="yr">
									<tr>
									<td class="row-odd" width="15%" height="25">
									<div align="left">
										<bean:write name="qualificationTo"  property="qualification"/>
									</div>
									</td>
									<td class="row-even" width="15%" height="25">
										<bean:write  name="qualificationTo"  property="course" />
										
									</td>
									<td class="row-even" width="15%" height="25">
										<bean:write  name="qualificationTo"  property="specialization" />
									</td>
									<%String dynaYearId="YOP"+yr; 
									%>
									<td class="row-even" width="15%" height="25">
										<bean:write  name="qualificationTo"  property="yearOfComp" />
									</td>
									<td class="row-even" width="15%">
										<bean:write  name="qualificationTo"  property="grade" />
									</td>
									<td class="row-even" width="15%" height="25">
										<bean:write  name="qualificationTo"  property="institute" />
									</td>
									
								</tr>
								</nested:iterate>
								</logic:notEmpty>
								
								<logic:notEmpty property="employeeInfoTONew.empQualificationLevelTos" name="EmployeeInfoViewForm">
									<nested:iterate id="levelTo" property="employeeInfoTONew.empQualificationLevelTos" name="EmployeeInfoViewForm" indexId="yrs">
									<tr>
									<td class="row-odd" height="25">
									<div align="left">
										<bean:write name="levelTo"  property="qualification"/>
									</div>
									</td>
									<td class="row-even" height="25">
										<bean:write  name="levelTo"  property="course" />
									</td>
									<td class="row-even" height="25">
										<bean:write  name="levelTo"  property="specialization" />
										
									</td>
									<%String dynaYrId="YEAR"+yrs;%>
									 <td class="row-even" height="25">
										<bean:write  name="levelTo"  property="yearOfComp" />
										
									</td>
									<td class="row-even">
										<bean:write  name="levelTo"  property="grade" />
										
										
									</td>
									<td class="row-even" height="25">
										<bean:write  name="levelTo"  property="institute" />
									</td>
									
								</tr>
								</nested:iterate>
								</logic:notEmpty>
								
								
								
								 <tr>
							  		<td class="row-odd" height="25"> 
							  		<div align="left" >
										<bean:message key="knowledgepro.employee.publications.refered"/>:
									</div>		
									</td>
									<td class="row-even" height="25">
										<bean:write  name="EmployeeInfoViewForm"  property="noOfPublicationsRefered" />
									</td>
									<td class="row-odd" height="25"> 
							  		<div align="left">
										<bean:message key="knowledgepro.employee.non.refered"/>:
									</div>		
									</td>
									<td class="row-even">
										<bean:write  name="EmployeeInfoViewForm"  property="noOfPublicationsNotRefered" />
									</td>
									<td class="row-odd"> 
							  		<div align="left">
										<bean:message key="knowledgepro.employee.books"/>:
									</div>		
									</td>
									<td class="row-even" height="25">
										<bean:write  name="EmployeeInfoViewForm"  property="books" />
									</td>
								</tr>	  
								</table>			
								
								</td>
								<td width="5" height="30" background="images/left.gif"></td>
							</tr>
							<tr>
								<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
								<td background="images/05.gif"></td>
								<td><img src="images/06.gif" /></td>
							</tr>
						</table>
						</td>
					</tr>
					

<!-- Education -->





	
				
							
					
			
					

				
				<tr>
					<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.employee.LeaveType"/>
					</td>
				</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="left" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="914" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
				<table width="100%" cellspacing="1" cellpadding="2">
            	<tr>
											<!-- <td height="25" class="row-odd">
											<div align="left"><bean:message key="knowledgepro.employee.Leave.slno"/></div>
											</td>-->
											<td class="row-odd" height="25"><bean:message key="knowledgepro.employee.Leave.leaveType"/>
											</td>
											<td class="row-odd"><bean:message key="knowledgepro.employee.Leave.leaveAllocated"/></td>
											<td class="row-odd"><bean:message key="knowledgepro.employee.Leave.leaveSanctioned"/></td>
											<td height="25" class="row-odd"><bean:message key="knowledgepro.employee.Leave.leaveRemaining"/> 	</td>
			  </tr>
			  
			   	<logic:notEmpty property="employeeInfoTONew.empLeaveToList" name="EmployeeInfoViewForm">
				<nested:iterate id="empLeaveToList" property="employeeInfoTONew.empLeaveToList" name="EmployeeInfoViewForm" >
								
									 <tr>							
									<td class="row-even" height="25"> <bean:write  name="empLeaveToList" property="leaveType" /></td>
									<td class="row-even"> <bean:write  name="empLeaveToList"  property="allottedLeave" /></td>
									<td class="row-even"><bean:write  name="empLeaveToList"  property="sanctionedLeave" /></td>
									<td class="row-even"><bean:write  name="empLeaveToList"  property="remainingLeave" /></td>
									 </tr>							
														  
					</nested:iterate>
					</logic:notEmpty>
								
                
             
 </table></td>
								<td width="5" height="30" background="images/left.gif"></td>
							</tr>
							<tr>
								<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
								<td background="images/05.gif"></td>
								<td><img src="images/06.gif" /></td>
							</tr>
						</table>
						</td>
					</tr>


	
				
					
					
					<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
				
</tr>					
<tr>
							<td align="center" colspan="6"> 
							
								<html:button property="" styleClass="formbutton" value="Close" onclick="closeWindow()"></html:button>		
							</td>
</tr>
	<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					
					</table>
					</td>
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
			var sameAddr= document.getElementById("sameAddr").checked;

			if(sameAddr==true){
				disableAddress();
			}
			if(sameAddr==false){
				enableAddress();
			}
			countryId=document.getElementById("currentCountryId").value;
			if(countryId!=''){
				setTimeout("getCurrentStateByCountry(countryId,'currentState')",1000); 
				setTimeout("setData1()",1800); 
			}
			
			cId=document.getElementById("countryId").value;
			if(cId!=''){
				setTimeout("getStateByCountry(cId,'stateId')",3000); 
				setTimeout("setData2()",3500); 
			}
			function setData1(){
				stateId=document.getElementById("tempState").value;
				document.getElementById('currentState').value=stateId;
			}
			function setData2(){ 
				var stId=document.getElementById("tempPermanentState").value;
				document.getElementById('stateId').value=stId;
			}

			function getOtherCurrentState(){
				other=document.getElementById("currentState").value;
				if(other=="Other"){
					document.getElementById("otehrState").style.display="block";
				}else{
					document.getElementById("otehrState").style.display="none";
				}
			}

			var tempOther=document.getElementById("tempState").value;
			if(tempOther=="Other"){
				document.getElementById("otehrState").style.display="block";
			}else{
				document.getElementById("otehrState").style.display="none";
			}

			function getOtherPermanentState(){
				other=document.getElementById("stateId").value;
				if(other=="Other"){
					document.getElementById("otehrPermState").style.display="block";
				}else{
					document.getElementById("otehrPermState").style.display="none";
				}
			}

			var tempPermOther=document.getElementById("tempPermanentState").value;
			if(tempPermOther=="Other"){
				document.getElementById("otehrPermState").style.display="block";
			}else{
				document.getElementById("otehrPermState").style.display="none";
			}
			
		</script>
	</html>
			
		
			
	


