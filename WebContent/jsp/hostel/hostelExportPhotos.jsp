<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<head>
<script type="text/javascript">



function exportImages(){
	if(validateTC()){
		
	var academicYear=document.getElementById("academicYear").value;
	var hostelId=document.getElementById("hostelId").value;
	var blockId=document.getElementById("blockId").value;
	var unitId=document.getElementById("unitId").value;
	document.location.href = "exportHostelImages.do?method=getStreamInfo&academicYear="+academicYear+"&hostelId="+hostelId+
							"&blockId="+blockId+"&unitId="+unitId+"&formName=hostelExportPhotosForm&pageType=1";
	}
}
function getBlock(hostelId){
	getBlockByHostel("blockMap",hostelId,"blockId",updateBlock);
	resetOption("unitId");
	}
function updateBlock(req) {
	updateOptionsFromMap(req,"blockId","- Select -");
}
function getUnit(blockId){
	getUnitByBlock("unitMap",blockId,"unitId",updateUnit);
	}
function updateUnit(req) {
	updateOptionsFromMap(req,"unitId","- Select -");
}
function validateTC()
{
	var error="";
	if(document.getElementById("academicYear").value==" "){
		error="Academic Year is Required <br>";
	} 
	if(document.getElementById("hostelId").value=="")
	{
		error+="Class is Required<br>";
	}	
	if(error=="")
	{
		return true;
	}
	else
	{
		document.getElementById("err").innerHTML=error;
		return false;
	}	
}
function resetFields(){
	document.location.href="hostelExportPhotos.do?method=initHostelExportPhotos";
}
</script>	
</head>
<html:form action="/hostelExportPhotos" >
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="hostelExportPhotosForm" />
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.hostel.fees"/>
			<span class="Bredcrumbs">&gt;&gt; Export Photos&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader">Export Photos</strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="6">
							<div id="err" style="color:red;font-family:arial;font-size:11px;"></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
							<div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
							
							</td>
						</tr>
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
					                <td width="25%" class="row-odd"><div align="right"><span class="Mandatory">*</span>Academic Year</div></td>
					                <td width="25%" class="row-even">
					                <html:select property="academicYear" styleId="academicYear" styleClass="combo">
											<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
										</html:select>
					                </td>
					                <td width="25%" class="row-odd" ><div align="right">
					                <span class="Mandatory">*</span>
							        <bean:message key="knowledgepro.hostel.hostel.entry.name" /></div></td>
							        <td width="25%" class="row-even" >
							        <label>
										<html:select property="hostelId" styleId="hostelId" onchange="getBlock(this.value)">
													 	<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
													   		<html:optionsCollection property="hostelMap" label="value" value="key" />
													   	 </html:select>
										</label></td>
					                
					            </tr>
								<tr>
					                <td width="25%" class="row-odd"><div align="right">Block</div></td>
					                <td width="25%" class="row-even">
					                <html:select property="blockId" styleId="blockId" styleClass="combo" onchange="getUnit(this.value)">
											<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>
											<c:choose>
             			 									<c:when test="${blockMap != null}">
             			 										<html:optionsCollection name="blockMap" label="value" value="key" />
															</c:when>
							 							</c:choose>
										</html:select>
					                </td>
					                <td width="25%" class="row-odd" ><div align="right">
							        Unit</div></td>
							        <td width="25%" class="row-even" >
							        <label>
										<html:select property="unitId" styleId="unitId" >
													 	<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
													   		<c:choose>
             			 								<c:when test="${unitMap != null}">
             			 									<html:optionsCollection name="unitMap" label="value" value="key" />
														</c:when>
							 						</c:choose>
													   	 </html:select>
										</label></td>
					                
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
					<div align="center">
					<table width="100%" height="106" border="0" cellpadding="1"
						cellspacing="2">
						<tr>
							<td width="100%" height="46" class="heading">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="center"><html:button  property="submit" styleClass="formbutton"  onclick="exportImages()" value="Export Photos">
									</html:button>
									 &nbsp;&nbsp;<html:button property="" styleClass="formbutton"
										onclick="resetFields()">
										<bean:message key="knowledgepro.admin.reset" />
									</html:button>
									</div>
									</td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td height="10"></td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif"></td>
					<td background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
