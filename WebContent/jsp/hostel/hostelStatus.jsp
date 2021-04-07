<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

   <script type="text/javascript" src="js/auditorium/jquery-1.9.1.min.js"></script>
    <!-- <script type="text/javascript" src="js/auditorium/spinners.min.js"></script> optional
    <script type="text/javascript" src="js/auditorium/tipped.js"></script>
    <link rel="stylesheet" type="text/css" href="css/auditorium/tipped.css" /> -->
    <link rel="stylesheet" type="text/css" href="css/tooltipster.css" />
    <script type="text/javascript" src="js/jquery.tooltipster.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    // Tooltip only Text
    $('.statusColor').tooltipster();
    $('.statusColor1').tooltipster();
});

	function getBlocks(hostal){
		getBlocksByHostel(hostal, "blockId", updateBlocks);
	}
	function updateBlocks(req){
		updateOptionsFromMap(req, "blockId", "- Select -");
	}
	function getUnits(blockId){
		getUnitsByBlocks(blockId, "unitId", updateUnits);
	}
	function updateUnits(req){
		updateOptionsFromMap(req, "unitId", "- Select -");
	}
	function searchForHostelStatus(){
		var academicYear = document.getElementById("academicYear").value;
		var hostelId = document.getElementById("hostelId").value;
		alert(academicYear+","+hostelId);
		var blockId = document.getElementById("blockId").value;
		var unitId = document.getElementById("unitId").value;
		document.location.href = "hostelStatusInfo.do?method=hostelStatusDisplay&hostelId="+hostelId;
		//document.getElementById("method").value="hostelStatusDisplay";
		//document.hostelStatusInfoForm.submit();
	}
	function hides(count){
		document.getElementById("messageBox_"+count).style.display="none";
	}
	function clearData(){
		document.location.href = "hostelStatusInfo.do?method=initHostelStatus";
	}
</script>

<style>
  .statusColor {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 11px;
	color: #00CD00;
	background-color: #00CD00;
}
 .statusColor1 {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 11px;
	color: #CD5555;
	background-color: #CD5555;
}
#td{
 background-color : #CD5555;
}
#td1{
 background-color : #00CD00;
}
</style>
<html:form action="/hostelStatusInfo">
	<html:hidden property="method" styleId="method"	value="hostelStatusDisplay"/>
	<html:hidden property="formName" value="hostelStatusInfoForm" />
	<html:hidden property="pageType" value="1" />
	<table width="99%" border="0">
		<tr>
			<td>
				<span class="Bredcrumbs">
					<bean:message key="knowledgepro.hostel"/>
					<span class="Bredcrumbs">
						&gt;&gt;<bean:message key = "knowledgepro.hostel.hostel.status"/> &gt;&gt;
					</span>
				</span>
			</td>	  
		</tr>
	  	<tr>	
	    	<td valign="top">
	    		<table width="100%" border="0" cellpadding="0" cellspacing="0">
	      			<tr>
	        			<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        			<td colspan="2" background="images/Tcenter.gif" class="heading_white"><bean:message key="knowledgepro.hostel.hostel.status"/></td>
				        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
	      			</tr>
	      			<tr>
	        			<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        			<td colspan="2" class="news">
	        				<div align="right">
	        					<FONT color="red">
	        						<span class='MandatoryMark'>
	        							<bean:message key="knowledgepro.mandatoryfields"/>
	        						</span>
	        					</FONT>
	        				</div>
							<div id="errorMessage">
								<FONT color="red">
									<html:errors />
								</FONT>
								<FONT color="green">
									<html:messages id="msg"	property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out>
										<br>	
									</html:messages>
								</FONT>
							</div>
						</td>
	        			<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      			</tr>	
	      			<tr>
				        <td height="42" valign="top" background="images/Tright_03_03.gif"></td>
				        <td colspan="2" valign="top" class="news">
				        	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
	                  						<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year"/> :</div></td>
									<td width="16%" class="row-even" valign="top">
									<input type="hidden" name="year" value='<bean:write name="hostelStatusInfoForm" property="academicYear"/>' id="year">
									<html:select 
										property="academicYear" styleId="academicYear"
										styleClass="combo" name="hostelStatusInfoForm">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
									</html:select></td>
	                    						<td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.hostel.entry.hostel.name"/></div></td>
	                    						<td width="25%" height="25" class="row-even">
	                    							<html:select property="hostelId" styleClass="comboLarge" styleId="hostelId" onchange="getBlocks(this.value)" name="hostelStatusInfoForm">
														<html:option value="">
															<bean:message key="knowledgepro.admin.select" />
														</html:option>
														<logic:notEmpty name="HostelList" scope="session">
															<html:optionsCollection name="HostelList" label="name" value="id" />
														</logic:notEmpty>
													</html:select>
												</td></tr>
												<tr>
												<td width="25%" class="row-odd"><div align="right">Block</div></td>
	                    						<td width="25%" class="row-even">
								                    <html:select name="hostelStatusInfoForm" property="blockId" styleId="blockId" styleClass="combo" onchange="getUnits(this.value)">
														<html:option value="">- Select -</html:option>
														<logic:notEmpty name="hostelStatusInfoForm" property="blockMap">
															<html:optionsCollection name="hostelStatusInfoForm" property="blockMap" label="value" value="key" />
														</logic:notEmpty>
													</html:select>
												</td>
												<td width="25%" class="row-odd"><div align="right">Unit</div></td>
	                    						<td width="25%" class="row-even">
								                    <html:select name="hostelStatusInfoForm" property="unitId" styleId="unitId" styleClass="combo">
														<html:option value="">- Select -</html:option>
														<logic:notEmpty name="hostelStatusInfoForm" property="unitMap">
															<html:optionsCollection name="hostelStatusInfoForm" property="unitMap" label="value" value="key" />
														</logic:notEmpty>
													</html:select>
												</td>
	                  						</tr>
	              						</table>
	                					<table width="100%" cellspacing="1" cellpadding="2">
	                						<tr ></tr>
							            </table>
									</td>
	              					<td width="5" height="30"  background="images/right.gif"></td>
	            				</tr>
	            				<tr>
	              					<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
	              					<td background="images/05.gif"></td>
	              					<td><img src="images/06.gif" /></td>
	            				</tr>
	        				</table>
	        			</td>
				        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      			</tr>
	      			<tr>
	        			<td height="36" valign="top" background="images/Tright_03_03.gif"></td>
	        			<td colspan="2" valign="top" class="news">
	        				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	          					<tr>
	           						<td width="2%"></td>
	           						<td height="35" align="right">
	                					<html:submit  styleClass="formbutton" value="Search" property=""></html:submit>
	           						</td>
									<td width="48%">
									<c:if test="${hostelStatusInfoForm.hlAdmissionMap!=null}">
									<table width="50%" border="0" align="right" cellpadding="0" cellspacing="0">
	            				       <tr>
	            				      <td id="td" width="50%" height="25" align="center"><font color="black" size="2px">Bed Allocated</font></td>
	            				      <td id="td1" width="50%" height="25" align="center"><font color="black" size="2px">Available Beds</font></td>
	            				       </tr>
									</table>
									</c:if>
									</td>			
	          					</tr>
	        				</table>
	        			</td>
	        			<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      			</tr>
	      			<%int countForMessage = 0; %>
	      			<c:if test="${hostelStatusInfoForm.hlAdmissionMap!=null}">
	      			
										<c:forEach items="${hostelStatusInfoForm.hlAdmissionMap}" var="blockMap">
										
										<c:forEach items="${blockMap.value}" var="unitMap">
										<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
										
										<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr height="2"></tr>
								<tr>
								<td align="center" class="heading" colspan="3" height="4">
								&nbsp;&nbsp;&nbsp;<b><font size="2px"><c:out value="${blockMap.key}"></c:out>&nbsp;&nbsp;<c:out value="${unitMap.key}"></c:out></font></b>
								</td>
								</tr>
								<tr height="2"></tr>
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									
									<table width="100%" cellspacing="2" cellpadding="2">
									<c:forEach items="${unitMap.value}" var="floorMap">
									
									<tr height="2"></tr>
									<tr>
								<td align="left" class="heading" colspan="3" height="4">
								<b><font size="2px">Floor &nbsp;&nbsp;<c:out value="${floorMap.key}"></c:out></font></b>
								</td>
								</tr>
									<tr height="2"></tr>
									
									<tr>
									<td width="5" ></td>
									<td valign="top">
									<table width="100%" cellspacing="2" cellpadding="2">
										<%int count =0;
									String colorType="";%>
										<c:forEach items="${floorMap.value}" var="toList">
										<%String messageBox = "messageBox_"+countForMessage;
										String contents = "contents_"+countForMessage; %>
										<c:choose>
										<c:when test="${toList.checkingStudent==true}">
										<% colorType = "statusColor1"; %>
										</c:when>
										<c:otherwise>
										<% colorType = "statusColor"; %>
										</c:otherwise>
										</c:choose>
										<%if(count==0){count++;
										%>
										<tr>
										<td class="<%=colorType %>" height="35" align="center" width="1%" title="Room Type: <c:out value="${toList.roomTypeName}"/>" id="status">
										<!--<img src="images/bed3.jpg" width="30%" height="30" align="left">
										--><FONT color="black" size="1px">
										<B><c:out value="${toList.roomName}"></c:out> - <c:out value="${toList.bedNo}"></c:out>
										<br></br><c:out value="${toList.regNo}"></c:out></B>
										</FONT>
										<div id='<%=messageBox %>'>
	                              		<div id='<%=contents %>'></div></div>
										</td>
										<%}else if(count<10){count++;
										%>
										<td class="<%=colorType %>" height="35" align="center" width="1%" title="Room Type: <c:out value="${toList.roomTypeName}"/>" id="status">
										<!--<img src="images/bed3.jpg" width="30%" height="30" align="left">
										--><FONT color="black" size="1px">
										<B><c:out value="${toList.roomName}"></c:out> - <c:out value="${toList.bedNo}"></c:out>
										<br></br><c:out value="${toList.regNo}"></c:out></B>
										</FONT>
										<div id='<%=messageBox %>'>
	                              		<div id='<%=contents %>'></div></div>
										</td>
										<%if(count==10)
											{%>
										</tr>
										<%count = 0;}}
										%>
										<%countForMessage++; %>
										</c:forEach>
										</table>
										</td>
										</tr>
										</c:forEach>
										</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news" colspan="2"></td>
						</tr>
										</c:forEach>
										</c:forEach></c:if>
										<c:if test="${hostelStatusInfoForm.hlAdmissionMap!=null}">
										<tr>
	        			<td height="36" valign="top" background="images/Tright_03_03.gif"></td>
	        			<td colspan="2" valign="top" class="news">
	        				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	          					<tr>
	           						<td width="2%"></td>
	           						<td height="35" align="center">
	                					<html:button  styleClass="formbutton" value="Close" property="" onclick="clearData()"></html:button>
	           						</td>
	          					</tr>
	        				</table>
	        			</td>
	        			<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      			</tr></c:if>
	      			<tr>
	        			<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
	        			<td colspan="2" background="images/TcenterD.gif"></td>
				        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
	      			</tr>
	    		</table>
	    	</td>
	  </tr>
	</table>
</html:form>
<script>
var year = document.getElementById("year").value;
if(year!=null && year!=""){
	document.getElementById("academicYear").value = year;
}
</script>
