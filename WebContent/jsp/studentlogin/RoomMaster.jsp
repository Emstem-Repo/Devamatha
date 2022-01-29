<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<head>
<script type="text/javascript">
function setColumnAsEndSem() {
	document.getElementById("method").value="setColumnAsEndSem";
	document.roomMasterForm.submit();
}
function resetMode() {
	document.location.href = "RoomMaster.do?method=initRoomMaster";
}

function closeWindow(){
	document.location.href = "RoomMaster.do?method=initRoomMasterSearch";
}

function cancel() {
	document.location.href = "RoomMaster.do?method=initRoomMasterSearch";
}

function addRoomMaster() {
	document.getElementById("method").value="addRoomMaster";
	document.roomMasterForm.submit();
}

function editRoomMaster(id) {
	document.location.href = "RoomMaster.do?method=editRoomMaster&id="+id;
	}

function updateRoomMaster() {
	document.getElementById("method").value = "updateRoomMaster"; 
	document.roomMasterForm.submit();
}

function reActivate() {
	document.location.href="RoomMaster.do?method=reActivateRoomMaster";
}

function deleteRoomMaster(id) {
	deleteConfirm =confirm("Are you sure to delete this entry?");
	if(deleteConfirm)
	document.location.href = "RoomMaster.do?method=deleteRoomMaster&id="+id;
	}
function getBlockBo(locationId) {
	getBlockByLocation("blockMap", locationId, "blockId", updateClasses);
}
function updateClasses(req) {
	updateOptionsFromMap(req, "blockId", "- Select -");
}
function calculateTotalSeatEnd(){
	var totalColumn=document.getElementById("endSemTotalColumn").value;
	var totalValue=0;
	for(var i=0;i<totalColumn;i++){
		var multi=0;
		var noOfRows = document.getElementById("endNoRows_"+i).value;
		var noOfDesk = document.getElementById("endNoDesk_"+i).value;
		if(noOfRows!=null && noOfRows!='' && noOfDesk!=null && noOfDesk!=''){
			multi=noOfRows*noOfDesk;
		    }
		totalValue=totalValue+multi;
		}	
	if(totalValue>0){
	var htm= "<strong>Total Number Of Seats :</strong> &nbsp;&nbsp"+totalValue;
	document.getElementById("endAmountId").innerHTML = htm;
	}else{
		document.getElementById("endAmountId").innerHTML = '';
	}
	document.getElementById("totalEndSem").value=totalValue;
	document.getElementById("endHideForm").style.display = "none";
}
function calculateTotalSeatMid(){
	var totalColumn=document.getElementById("midSemTotalColumn").value;
	var totalValue=0;
	for(var i=0;i<totalColumn;i++){
		var multi=0;
		var noOfRows = document.getElementById("midNoRows_"+i).value;
		var noOfDesk = document.getElementById("midNoDesk_"+i).value;
		if(noOfRows!=null && noOfRows!='' && noOfDesk!=null && noOfDesk!=''){
			multi=noOfRows*noOfDesk;
		    }
		totalValue=totalValue+multi;
		}	
	if(totalValue>0){
	var htm= "<strong>Total Number Of Seats :</strong> &nbsp;&nbsp"+totalValue;
	document.getElementById("midAmountId").innerHTML = htm;
	}else{
		document.getElementById("midAmountId").innerHTML = '';
	}
	document.getElementById("totalMidSem").value=totalValue;
	document.getElementById("midHideForm").style.display = "none";
}
</script>
</head>
<html:form action="/RoomMaster" >
<html:hidden property="method" value="" styleId="method" />
<html:hidden property="formName" value="roomMasterForm" />
<html:hidden property="totalEndSem" styleId="totalEndSem" />
<html:hidden property="totalMidSem" styleId="totalMidSem" />
<html:hidden property="pageType" value="1" />

<table width="99%" border="0">
  
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.exam"/> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.exam.roomMaster"/> &gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.exam.roomMaster"/> </td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="right"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
			<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"><html:messages id="msg" property="messages" message="true">
					<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages> </FONT></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="44" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="100%" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              
              <tr >
                 <td width="20%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hlAdmission.location" />:</div></td>
				 <td width="30%" height="25" class="row-even"><span	class="star"> 
				 <html:select property="locationId" styleClass="comboLarge" styleId="locationId" onchange="getBlockBo(this.value)"><html:option value=""><bean:message key="knowledgepro.admin.select" />
				 </html:option><html:optionsCollection name="locationList" label="empLocationName" value="empLocationId" /></html:select></span></td>
                <td width="20%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.auditorium.block.name"/>:</div></td>
               <td align="left" width="30%" class="row-even">
               <html:select property="blockId" styleClass="combo" styleId="blockId"><html:option value=""><bean:message key="knowledgepro.select" />-</html:option>
               <c:choose>
               <c:when test="${roomMasterForm.blockMap!=null}">
               <html:optionsCollection name="roomMasterForm" property="blockMap" label="value" value="key" />
               </c:when>
               </c:choose>
			  </html:select></td>
               </tr>
               <tr>
               <td width="20%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hostel.floorno"/>:</div></td>
                <td width="30%" height="25" class="row-even"><span class="star">
                    <html:text property="floor" styleId="floor" size="20" maxlength="16" onkeypress="return isNumberKey(event)" />
                </span></td>
                <td width="20%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hostel.floorno.name"/>:</div></td>
                <td width="30%" height="25" class="row-even"><span class="star">
                    <html:text property="floorName" styleId="floorName" size="15" maxlength="28" />
                </span></td>
               </tr>
                 <tr>
                <td width="20%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hostel.roomno"/>:</div></td>
                <td width="30%" height="25" class="row-even"><span class="star">
                    <html:text property="roomNo" styleId="roomNo" size="15" maxlength="16" />
                </span></td>
                <td width="20%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.exam.assignStudentsToRoom.totalCapacity"/>:</div></td>
                <td width="30%" height="25" class="row-even"><span class="star">
                    <html:text property="totalCapacity" styleId="totalCapacity" size="10" maxlength="3" onkeypress="return isNumberKey(event)"  />
                </span></td>
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
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						   <tr>
					<td height="20" class="heading" align="left">
						
					</td>
					</tr>
					</table>
					</td>
					<td width="10" valign="top" colspan="2" background="images/Tright_3_3.gif" class="news"></td>
				  </tr>	
      
                   <tr>
             <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
			<td valign="top" class="news" colspan="3">
			  <table width="50%" border="0" align="left" cellpadding="0"	cellspacing="0">
				<tr>
					<td valign="top" class="news" colspan="3">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
					<td  class="heading" align="center">
					<bean:message key="knowledgepro.hlAdmission.end.sem"/>
					</td>
					</tr>
					</table>
					</td>
				</tr>	
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
                                  <td width="15%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.exam.roomcapacity"/>:</div></td>
                                  <td width="10%" height="25" class="row-even"><span class="star">
                                   <html:text property="endSemCapacity" name="roomMasterForm" styleId="endSemCapacity"  size="6" maxlength="3" onkeypress="return isNumberKey(event)"/>
                                  </span></td>
                                   <td width="15%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hlAdmission.total.Columns"/>:</div></td>
                                  <td width="10%" height="25" class="row-even"><span class="star">
                                  <html:text property="endSemTotalColumn" name="roomMasterForm" styleId="endSemTotalColumn" size="6" maxlength="3" onchange="setColumnAsEndSem()" onkeypress="return isNumberKey(event)"/>
                                   </span></td>
                                   </tr>
                                <tr>
                                 <td width="15%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.hlAdmission.seats.indesk"/>:</div></td>
                                 <td width="10%" height="25" class="row-even"><span class="star">
                                   <html:text property="endSemSeatInDesk" name="roomMasterForm" styleId="endSemSeatInDesk" size="6" maxlength="3" onkeypress="return isNumberKey(event)"/>
                                  </span></td>
                                 <td width="15%" height="25" class="row-odd"></td>
                                 <td width="10%" height="25" class="row-even"></td>
                                    </tr>
								<logic:notEmpty name="roomMasterForm" property="endSemList">
									      <tr>
											 <td height="15" class="row-white" align="center" colspan="4"></td>
										  </tr>
										    <tr >
                                             <td width="40%" height="25" class="row-odd" align="center" colspan="2">No of Rows in</td>
                                             <td width="40%" height="25" class="row-odd" align="center" colspan="2"><bean:message key="knowledgepro.hlAdmission.seats.indesk"/></td>
                                          </tr>
								 	<nested:iterate property="endSemList" name="roomMasterForm" indexId="countEnd" id="end">
								 		<tr>
								 			<td class="row-even" align="center" align="center" colspan="2">
								 				Column &nbsp;&nbsp;
								 				<% 
								 				String endNoRows= "endNoRows_"+countEnd;
								 				String endNoDesk= "endNoDesk_"+countEnd;
								 				%>
												<nested:write property="endColumnNumber"/>&nbsp;&nbsp;&nbsp;&nbsp;
												<nested:text property="endSemNoOfRows" styleId="<%=endNoRows %>" size="6" maxlength="3" onkeypress="return isNumberKey(event)" onchange="calculateTotalSeatEnd()"></nested:text>								 			
								 			</td>
								 			<td class="row-even" align="center" align="center" colspan="2">
												<nested:text property="endSemSeatInDesk" styleId="<%=endNoDesk%>" size="6" maxlength="3" onkeypress="return isNumberKey(event)" onchange="calculateTotalSeatEnd()"></nested:text>								 			
								 			</td>
								 		</tr>
								 	</nested:iterate>
								</logic:notEmpty>
								<tr>
								<td class="row-even" align="left" colspan="4">
								<div id="endAmountId"></div>
								<div id="endHideForm">
								<logic:notEmpty property="totalEndSem" name="roomMasterForm">
								Total Number Of Seats : <nested:write property="totalEndSem" name="roomMasterForm"></nested:write>
								</logic:notEmpty>
								</div>
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
						
			 <table width="50%" border="0" align="left" cellpadding="0"	cellspacing="0">
				<tr>
					<td valign="top" class="news" colspan="3">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						   <tr>
					<td  class="heading" align="center">
						<bean:message key="knowledgepro.hlAdmission.mid.sem"/>
					</td>
					</tr>
					</table>
					</td>
				  </tr>
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
                                  <td width="15%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.exam.roomcapacity"/>:</div></td>
                                  <td width="10%" height="25" class="row-even"><span class="star">
                                   <html:text property="midSemCapacity" name="roomMasterForm" styleId="midSemCapacity"  size="6" maxlength="3" onkeypress="return isNumberKey(event)"/>
                                  </span></td>
                                   <td width="15%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hlAdmission.total.Columns"/>:</div></td>
                                  <td width="10%" height="25" class="row-even"><span class="star">
                                  <html:text property="midSemTotalColumn" name="roomMasterForm" styleId="midSemTotalColumn" size="6" maxlength="3" onchange="setColumnAsEndSem()" onkeypress="return isNumberKey(event)"/>
                                   </span></td>
                                   </tr>
                                <tr>
                                 <td width="15%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.hlAdmission.seats.indesk"/>:</div></td>
                                 <td width="10%" height="25" class="row-even"><span class="star">
                                   <html:text property="midSemSeatInDesk" name="roomMasterForm" styleId="midSemSeatInDesk" size="6" maxlength="3" onkeypress="return isNumberKey(event)"/>
                                  </span></td>
                                 <td width="15%" height="25" class="row-odd"></td>
                                 <td width="10%" height="25" class="row-even"></td>
                                    </tr>
							  <logic:notEmpty name="roomMasterForm" property="midSemList">
									      <tr>
											 <td height="15" class="row-white" align="center" colspan="4"></td>
										  </tr>
										  <tr >
                                             <td width="40%" height="25" class="row-odd" align="center" colspan="2">No of Rows in</td>
                                             <td width="40%" height="25" class="row-odd" align="center" colspan="2"><bean:message key="knowledgepro.hlAdmission.seats.indesk"/></td>
                                          </tr>
								  <nested:iterate property="midSemList" name="roomMasterForm" id="CMEE" indexId="countt">
									<tr>
										 <td class="row-even" align="center" align="center" colspan="2">
										     Column &nbsp;&nbsp;  
										       <% 
								 				String midNoRows= "midNoRows_"+countt;
								 				String midNoDesk= "midNoDesk_"+countt;
								 				%>  
                   		                    <nested:write property="midColumnNumber" />&nbsp;&nbsp;&nbsp;&nbsp;
								            <nested:text  property="midSemNoOfRows" styleId="<%=midNoRows %>" size="6" maxlength="3" onkeypress="return isNumberKey(event)" onkeyup="calculateTotalSeatMid()"></nested:text>
										</td>
										<td class="row-even" align="center" align="center" colspan="2">
												<nested:text property="midSemSeatInDesk" styleId="<%=midNoDesk %>" size="6" maxlength="3" onkeypress="return isNumberKey(event)" onkeyup="calculateTotalSeatMid()"></nested:text>								 			
								 	   </td>
									</tr>
			                         </nested:iterate>
								</logic:notEmpty>
								<tr>
								<td class="row-even" align="left" colspan="4">
								<div id="midAmountId"></div>
								<div id="midHideForm">
								<logic:notEmpty property="totalMidSem" name="roomMasterForm">
								Total Number Of Seats : <nested:write property="totalMidSem" name="roomMasterForm"></nested:write>
								</logic:notEmpty>
								</div>
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
						</td>
						<td width="10" valign="top" colspan="2" background="images/Tright_3_3.gif" class="news"></td>
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
							<c:choose>
            	         	<c:when test="${roomMaster == 'edit'}">
              	   		    <html:submit property="" styleClass="formbutton" value="Update" onclick="updateRoomMaster()"></html:submit>&nbsp;&nbsp;
							<html:button property="" styleClass="formbutton" value="Close" onclick="cancel()"></html:button>
              		        </c:when>
              		        <c:otherwise>
                		    <html:submit property="" styleClass="formbutton" value="Submit" onclick="addRoomMaster()"></html:submit>&nbsp;&nbsp;
							<html:button property="" styleClass="formbutton" value="Reset" onclick="resetMode()"></html:button>&nbsp;&nbsp;	
							<html:button property="" styleClass="formbutton" value="Close" onclick="closeWindow()"></html:button>&nbsp;&nbsp;	
              		         </c:otherwise>
                        	</c:choose>
							</td>
                      </tr>
					
      
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"></td>
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
<script type="text/javascript">
document.getElementById("endHideForm").style.display = "block";
document.getElementById("midHideForm").style.display ="block";
</script>