<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<link type="text/css"
	href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
<style type="text/css">
.ui-datepicker {
	background: #6B8E23;
	border: 1px solid #555;
	color: #6B8E23;
	font-family: Arial;
	font-size: 11px;
	margin-left: 10px
}
</style>
<script type="text/javascript"><!--
		
	function cancelAction(){
		document.getElementById("method").value="initExamSchedul";
	}
	function getVenueDetails(){
		document.getElementById("method").value="assignVenueAndInvigilator";
		document.examScheduleForm.submit();
	}
	


	function moveoutid(count) {
		var mapFrom = document.getElementById("mapTeachers_"+count); 
		var len = mapFrom.length;
		var mapTo = document.getElementById("selsubMap_"+count);

		if (mapTo.length == 0) {
			document.getElementById("moveIn_"+count).disabled = false;
		}
		for ( var j = 0; j < len; j++) {
			if (mapFrom[j].selected) {

				//listClasses.push(mapFrom[j].value);
				var tmp = mapFrom.options[j].text;
				var tmp1 = mapFrom.options[j].value;
				mapFrom.remove(j);
				len--;
				j--;
				if (j < 0) {
					document.getElementById("moveOut_"+count).disabled = true;
					document.getElementById("moveIn_"+count).disabled = false;
				}
				if (mapFrom.length <= 0)
					document.getElementById("moveOut_"+count).disabled = true;
				else
					document.getElementById("moveOut_"+count).disabled = false;
				var y = document.createElement('option');

				y.text = tmp;
				y.value = tmp1;
				y.setAttribute("class", "comboBig");
				try {
					mapTo.add(y, null);
				} catch (ex) {
					mapTo.add(y);
				}
			}
		}
		document.getElementById("selsubMap_"+count).value=mapTo;
	}

	function moveinid(count) {
		var mapFrom = document.getElementById("mapTeachers_"+count);
		var mapTo = document.getElementById("selsubMap_"+count);
		var len = mapTo.length;

		for ( var j = 0; j < len; j++) {
			if (mapTo[j].selected) {
				
				var tmp = mapTo.options[j].text;
				var tmp1 = mapTo.options[j].value;
				mapTo.remove(j);
				len--;
				j--;
				if (j < 0) {
					document.getElementById("moveIn_"+count).disabled = true;
					document.getElementById("moveOut_"+count).disabled = false;
				}
				if (mapTo.length != 0) {
					document.getElementById("moveOut_"+count).disabled = false;
					document.getElementById("moveIn_"+count).disabled = false;
				} else
					document.getElementById("moveOut_"+count).disabled = false;
				var y = document.createElement('option');
				y.setAttribute("class", "comboBig");
				y.text = tmp;
				y.value = tmp1;
				try {
					mapFrom.add(y, null);
				} catch (ex) {
					mapFrom.add(y);
				}
			}
		}

	}
	function getVenue(workLocationId,count){
		c=count;
		var venue="venue_"+c;
		getVenueByWorkLocation("venueMap",workLocationId,venue,updateVenue);
		}
	function updateVenue(req) {
		updateOptionsFromMap(req,"venue_"+c,"- Select -");
	}
	function addMore(){
		var toList=document.getElementById("toSize").value;
		
		for ( var j = 0; j <toList; j++) {
		var sda1 = document.getElementById("mapTeachers_"+j);
		for(var i=0;i<sda1.length;i++) {
			sda1[i].selected = true;
		}	
		var sda2 = document.getElementById("selsubMap_"+j);
		for(var k=0;k<sda2.length;k++) {
			sda2[k].selected = true;
		}
		}
		document.getElementById("method").value="addMoreVenueAndInvigilator";
		document.examScheduleForm.submit();
	}
	
	function saveDetails(){
		var toList=document.getElementById("toSize").value;
		
		for ( var j = 0; j <toList; j++) {
			var sda1 = document.getElementById("mapTeachers_"+j);
				for(var i=0;i<sda1.length;i++) {
					sda1[i].selected = true;
				}	
				var sda2 = document.getElementById("selsubMap_"+j);
				for(var k=0;k<sda2.length;k++) {
					sda2[k].selected = true;
				}
		}
		document.getElementById("method").value="saveVenueAndInvigilator";
		document.examScheduleForm.submit();
	}

	function UpDateDetails(){
		var toList=document.getElementById("toSize").value;
		
		for ( var j = 0; j <toList; j++) {
			var sda1 = document.getElementById("mapTeachers_"+j);
				for(var i=0;i<sda1.length;i++) {
					sda1[i].selected = true;
				}	
				var sda2 = document.getElementById("selsubMap_"+j);
				for(var k=0;k<sda2.length;k++) {
					sda2[k].selected = true;
				}
		}
		document.getElementById("method").value="updateVenueAndInvigilatorDetails";
		document.examScheduleForm.submit();
	}

	function deleteExamSchedule(count){
		var agree=confirm("Are you sure you want to delete?");
		if (agree){
			var toList=document.getElementById("toSize").value;
			
			for ( var j = 0; j <toList; j++) {
				var sda1 = document.getElementById("mapTeachers_"+j);
					for(var i=0;i<sda1.length;i++) {
						sda1[i].selected = true;
					}	
					var sda2 = document.getElementById("selsubMap_"+j);
					for(var k=0;k<sda2.length;k++) {
						sda2[k].selected = true;
					}
			}
			document.getElementById("deleteCount").value=count;
			document.getElementById("method").value="removeToFromToList";
			document.examScheduleForm.submit();
			 
		}else{
			document.getElementById("formbutton").style.display="block";
		}
	}
	
</script>
<html:form action="/examSchedule" method="post" >
	<html:hidden property="formName" value="examScheduleForm" />
	<html:hidden property="method" styleId="method" value=""/>
	<html:hidden property="pageType" value="2" />
	<html:hidden property="examScheduleToListSize" styleId="toSize" />
	<html:hidden property="deleteCount" styleId="deleteCount" />
<table width="100%" border="0">
	  <tr>
	   <td><span class="Bredcrumbs">Sap <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.sap.examSchedule"/> &gt;&gt;</span></span></td>
	  </tr>
	
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key = "knowledgepro.sap.examSchedule"/></td>
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
	        <td class="heading">
	        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	        	<tr>
              				<td class="row-odd" width="10%"> 
									<div align="right"><span class="Mandatory">*</span>
									<bean:message key="knowledgepro.admin.template.Date" />:</div>
								</td>
								
								<td class="row-even" width="10%" >
									<html:text name="examScheduleForm" property="examDate" styleId="examDate" size="10" maxlength="16" />
									<script language="JavaScript">
 													$(function(){
					 					var pickerOpts = {
					        			dateFormat:"dd/mm/yy"
					       				};  
					  					$.datepicker.setDefaults(
					   					$.extend($.datepicker.regional[""])
					  					);
					  					$("#examDate").datepicker(pickerOpts);
										});
                                				</script>
								</td> 
				 				<td height="25" class="row-odd" width="10%">
									<div align="right"><span class="Mandatory">*</span>
									<bean:message key="knowledgepro.admin.session" />:</div>
								</td>
								<td class="row-even" width="10%" >
									<html:text name="examScheduleForm" property="session" styleId="session" />
								</td>
								<td height="25" class="row-odd" width="10%">
									<div align="right"><span class="Mandatory">*</span>
									<bean:message key="knowledgepro.admin.session.order" />:</div>
								</td>
								<td class="row-even" width="10%" >
									<html:text name="examScheduleForm" property="sessionOrder" styleId="sessionOrder"  size="2" maxlength="2" onkeypress="return isNumberKey(event)"/>
								</td>
	        	</tr>
	        </table>
	        </td>
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	        </tr>
	      <tr>
	        <td valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading">
	        &nbsp;
	        </td>
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
	                <td valign="top">
	                <table width="100%" cellspacing="1" cellpadding="2">
	                <tr class="row-odd">
	                <td ><span class="Mandatory">*</span><bean:message key="employee.info.job.workLocationName" /></td>
	                <td ><span class="Mandatory">*</span><bean:message key="knowledgepro.interview.Venue" /></td>
	                <td ><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.examSchedule.priority" /></td>
	                <td ><bean:message key="knowledgepro.admin.examSchedule.invigilators" /></td>
	                <c:if test="${examScheduleForm.examSchTo}"> 
	                	<td class="row-odd" width="15%"><div align="center"><bean:message key="knowledgepro.delete" /></div></td>
	                </c:if>
	                </tr>
	                	<logic:notEmpty name="examScheduleForm" property="examSchToList">
	                	<nested:iterate id="to" name="examScheduleForm" property="examSchToList" indexId="count">
	                	<c:if test="${to.isActive}">
	                	<tr class="row-even">
						<td width="25%"  class="row-even" >
						<% 
							String getVenue="getVenue(this.value,"+count+")";
							String venueId="venue_"+count;
							String teachersMap="mapTeachers_"+count;
							String selectedSubMap="selsubMap_"+count;
							String MoveOut="moveoutid("+count+")";
							String MoveIN="moveinid("+count+")";
							String MoveOutId="moveOut_"+count;
							String MoveINID="moveIn_"+count;
						%>
			        						<label>
												<nested:select property="workLocation" styleId="workLocation" onchange="<%=getVenue%>">
									 			<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
									 			<logic:notEmpty property="workLocationMap" name="to">
									   			<html:optionsCollection name="to" property="workLocationMap" label="value" value="key" />
									   			</logic:notEmpty>
									   	 	</nested:select>
											</label>
										</td>
									<td width="15%"  class="row-even" >
			        						<label>
												<nested:select property="venue" styleClass="comboMediumBig" styleId="<%=venueId%>">
									 			<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
									 			<logic:notEmpty property="venueMap" name="to">
									   			<html:optionsCollection name="to" property="venueMap" label="value" value="key" />
									   			</logic:notEmpty>
									   	 	</nested:select>
											</label>
										</td>
						
						<td class="row-even" width="5%" align="center">
							<nested:text  property="priority" styleId="priority" size="2" maxlength="2" styleClass="Timings" onkeypress="return isNumberKey(event)"/>
						</td>
										
						<td width="10%" height="25"   class="row-even">
						  <table width="200" border="0">
				             <tr>
				             <td width="118">
				                <label>
							       <nested:select
										property="teachesFrom" styleClass="body" 
										multiple="multiple" size="4" styleId="<%=teachersMap%>"
										style="width:260px">
											<logic:notEmpty name="to" property="teachersMap">
											<nested:optionsCollection  property="teachersMap" label="value" value="key"
												styleClass="comboBig" />
											</logic:notEmpty>
									</nested:select>
				                  </label>
			                  </td>
				                  <td width="52">
				                  <table border="0">
				                  	<tr>
					                  	<td>
					                  		<input type="button" onClick="<%=MoveOut%>"  id="<%=MoveOutId%>" value=">>">
										</td>
									</tr>
				                    <tr>
					                  <td>
					                  	<input type="button" value="<<" id="<%=MoveINID%>" onclick="<%=MoveIN%>" >
									</td>
								  </tr>
								</table>
								</td>
				                  <td width="256">
				                    <label>
				                      <nested:select  property="teachesTo" styleId="<%=selectedSubMap%>" styleClass="body" 
											multiple="multiple" size="4" style="width:250px;">
											<logic:notEmpty name="to" property="selectedTeachersMap">
											<nested:optionsCollection property="selectedTeachersMap" label="value" value="key"
												styleClass="comboBig" />
											</logic:notEmpty>
									</nested:select> 
				                    </label>
				                    </td>
				              </tr>
						  </table>
                 				</td>
                 				 <c:if test="${examScheduleForm.examSchTo}"> 
                 				<td width="30%" height="25" class="row-even">
										<div align="center"><img src="images/delete_icon.gif" width="30%" height="25" style="cursor: pointer"
												onclick="deleteExamSchedule('<bean:write name="to" property="deleteCount" />')"/></div>
								</td>
								</c:if>
                 				</tr>
                 				</c:if>
                 				</nested:iterate>
                 		</logic:notEmpty>		
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
				<tr>
				<td width="45%" height="35">
					<div align="right">
					<c:choose>
						<c:when test="${Operation == 'edit'}">
							<html:button property="" value="Update" styleClass="formbutton" onclick="UpDateDetails()"></html:button>&nbsp;&nbsp;
						</c:when>
						<c:otherwise>
							<html:button property="" value="Submit" styleClass="formbutton" onclick="saveDetails()"></html:button>&nbsp;&nbsp;
						</c:otherwise>
					</c:choose>
					</div>
				</td>
	            
				<td width="55%"> 
				<html:button  property="" value="Add More" styleClass="formbutton" onclick="addMore()"></html:button>&nbsp;&nbsp;
				<html:submit value="Close" styleClass="formbutton" onclick="cancelAction()"></html:submit>
				</td>
			</tr>
				</table>
			</td>
	          </tr>

	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading"></td>      
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

