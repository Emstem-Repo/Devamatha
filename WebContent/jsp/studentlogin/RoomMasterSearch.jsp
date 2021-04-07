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
<LINK REL=StyleSheet HREF= "css/styles.css" TYPE="text/css">
<script language="JavaScript" src="js/admissionform.js"></script>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script type="text/javascript">

	function closeWindow(){
		document.location.href = "RoomMaster.do?method=initRoomMasterSearch";
	}
	function addRoomMasterSearch(){
		document.getElementById("method").value="initRoomMaster";
		document.roomMasterForm.submit();
	}
	
	function editRoomMasterSearch(id) {
		document.location.href = "RoomMaster.do?method=editRoomMaster&id="+id;
		}
	
	   function deleteRoomMaster(id) {
		  	deleteConfirm = confirm("Are you sure you want to delete this entry?");
			if (deleteConfirm) {
				document.location.href = "RoomMaster.do?method=deleteRoomMaster&id="+id;
			}
		}
		
    function searchdetails(){
		document.getElementById("method").value="searchRoomMaster";
		document.roomMasterForm.submit();
	}
    function getBlockBo(locationId) {
    	getBlockByLocation("blockMap", locationId, "blockId", updateClasses);
    }
    function updateClasses(req) {
    	updateOptionsFromMap(req, "blockId", "- Select -");
    }		
</script>
</head>
<body>
<table width="100%" border="0">
<html:form action="/RoomMaster" enctype="multipart/form-data">
	<html:hidden property="pageType" value="2" />
	<html:hidden property="method" value="" styleId="method" />
	<html:hidden property="formName" value="roomMasterForm" />
	<input type="hidden" id="count"/>
       <tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.exam" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.exam.roomMaster" /> &gt;&gt;</span></span></td>
		</tr>
   <tr>
    <td valign="top">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"/></td>
        <td width="1271" background="images/Tcenter.gif" class="body" >
			<div align="left">
				<strong class="boxheader"><bean:message key="knowledgepro.exam.roomMaster"/></strong>
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
				<tr >
                 <td width="20%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.hlAdmission.location" />:</div></td>
				 <td width="30%" height="25" class="row-even"><span	class="star"> 
				 <html:select property="locationId" styleClass="comboLarge" styleId="locationId" onchange="getBlockBo(this.value)"><html:option value=""><bean:message key="knowledgepro.admin.select" />
				 </html:option><html:optionsCollection name="locationList" label="empLocationName" value="empLocationId" /></html:select></span></td>
                <td width="20%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.auditorium.block.name"/>:</div></td>
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
               <td width="20%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.floorno"/>:</div></td>
                <td width="30%" height="25" class="row-even"><span class="star">
                    <html:text property="floor" styleId="floor" size="20" maxlength="16" onkeypress="return isNumberKey(event)" />
                </span></td>
                <td width="20%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.roomno"/>:</div></td>
                <td width="30%" height="25" class="row-even"><span class="star">
                    <html:text property="roomNo" styleId="roomNo" size="15" maxlength="16" />
                </span></td>
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
						    <html:button property="" styleClass="formbutton" value="Add" onclick="addRoomMasterSearch()"></html:button>&nbsp;&nbsp;
							<html:button property="" styleClass="formbutton" value="Reset" onclick="closeWindow()"></html:button>&nbsp;&nbsp;	
							<html:button property="" styleClass="formbutton" value="Search" onclick="searchdetails()"></html:button>	
              		        </td>
                           </tr>
                
                </table></td>
              </tr>
          
            </table></td>
          </tr>
					
					
               
          <logic:notEmpty name="roomMasterForm" property="roomMasterList">
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
                     <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.hlAdmission.location"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.auditorium.block.name"/></td>
                    <td height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.hostel.floorno"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.hostel.roomno"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.exam.assignStudentsToRoom.totalCapacity"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.exam.roomcapacity.end"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.hlAdmission.total.Columns.end"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.exam.roomcapacity.mid"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.hlAdmission.total.Columns.mid"/></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.edit"/></div></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                  </tr>
                <c:set var="temp" value="0"/>
                <logic:iterate id="CME" name="roomMasterForm" property="roomMasterList" indexId="count">
                <c:choose>
                   <c:when test="${temp == 0}">
                   	<tr>
                   		<td width="5%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td width="10%" height="25" class="row-even" align="center"><bean:write name="CME" property="locationName"/></td>
                   		<td width="5%"  height="25"  class="row-even" align="center"><bean:write name="CME" property="blockName"/></td>
                   		<td width="10%" height="25" class="row-even" align="center"><bean:write name="CME" property="floor"/></td>
                   		<td width="10%" height="25" class="row-even" align="center"><bean:write name="CME" property="roomNo"/></td>
                   		<td width="10%" height="25" class="row-even" align="center"><bean:write name="CME" property="totalCapacity"/></td>
                   		<td width="10%" height="25" class="row-even" align="center"><bean:write name="CME" property="endSemCapacity"/></td>
                   		<td width="10%" height="25" class="row-even" align="center"><bean:write name="CME" property="endSemTotalColumn"/></td>
                   		<td width="10%" height="25" class="row-even" align="center"><bean:write name="CME" property="midSemCapacity"/></td>
                   		<td width="10%" height="25" class="row-even" align="center"><bean:write name="CME" property="midSemTotalColumn"/></td>
			            <td width="5%" height="25" class="row-even" ><div align="center">
			        		<img src="images/edit_icon.gif" width="16" height="18" style="cursor:pointer" onclick="editRoomMasterSearch('<bean:write name="CME" property="id"/>')"></div></td>
                   		<td width="5%" height="25" class="row-even" ><div align="center">
                   			<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteRoomMaster('<bean:write name="CME" property="id"/>')"></div></td>
					</tr>
                    <c:set var="temp" value="1"/>
                   	</c:when>
                    <c:otherwise>
		            <tr>
               			<td width="5%" height="25" class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
               			<td width="10%" height="25" class="row-white" align="center"><bean:write name="CME" property="locationName"/></td>
               			<td width="5%" height="25" class="row-white" align="center"><bean:write name="CME" property="blockName"/></td>
               			<td width="10%" height="25" class="row-white" align="center"><bean:write name="CME" property="floor"/></td>
               			<td width="10%" height="25" class="row-white" align="center"><bean:write name="CME" property="roomNo"/></td>
               			<td width="10%" height="25" class="row-white" align="center"><bean:write name="CME" property="totalCapacity"/></td>
               			<td width="10%" height="25" class="row-white" align="center"><bean:write name="CME" property="endSemCapacity"/></td>
               			<td width="10%" height="25" class="row-white" align="center"><bean:write name="CME" property="endSemTotalColumn"/></td>
               			<td width="10%" height="25" class="row-white" align="center"><bean:write name="CME" property="midSemCapacity"/></td>
               			<td width="10%" height="25" class="row-white" align="center"><bean:write name="CME" property="midSemTotalColumn"/></td>
               			<td width="5%" height="25" class="row-white" ><div align="center">
               				<img src="images/edit_icon.gif" width="16" height="18" style="cursor:pointer" onclick="editRoomMasterSearch('<bean:write name="CME" property="id"/>')"></div></td>
               			<td width="5%" height="25" class="row-white" ><div align="center">
               				<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteRoomMaster('<bean:write name="CME" property="id"/>')"></div></td>
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
