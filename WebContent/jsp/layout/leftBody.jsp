
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<script type="text/javascript">
	var myMenu;
	window.onload = function toTest(){
		myMenu = new SDMenu("my_menu");
		myMenu.init();
		setClocks();
		if(document.getElementById("toggle").value=="X")
			document.getElementById("toggle").value="X";
		else
			document.getElementById("toggle").value=">";
	};
</script>

<script type="text/javascript">	
	var tog=true;
	function search(searchValue){
		var len=document.getElementById("menuCount").value;
		var searchValueLen = searchValue.length;
		for(var i=0; i<len; i++){				
			var id="link_"+i;
			var divId="div_"+i;
			var ele = document.getElementById(divId);
			if(searchValue!=null && searchValue!=""){
			if(((document.getElementById(id).value).toUpperCase().search(searchValue.toUpperCase())) >=0){
				ele.style.display = "block";
			}		
			else
			{
				ele.style.display = "none";				
			}		
			}
			else
			{
				ele.style.display = "block";
			}
	}
	}
	function openClose(){
		if(tog){
			myMenu.expandAll();
			document.getElementById("toggle").value="X";
			tog=false;
		}
		else
		{
			myMenu.collapseAll();
			document.getElementById("toggle").value=">";
			tog=true;
		}
	}

</script>
<script type="text/javascript">
	function openLink(link,name){
		var url=link+"&menuName="+name;
		document.location.href=url;
	}
		
</script>

<table cellpadding="7" width="165">
<tr>
<td class="heading" align="center" valign="bottom">
<img src="images/View_icon.gif" width="18" height="25" border="0" align="absmiddle">&nbsp;&nbsp;Find Menu...
</td>
</tr>
<tr>
<td class="td-back-image">
<html:text property="search" value="" name="loginform" styleId="search" onkeyup="search(this.value)" size="15"/>
<html:button styleId="toggle" property="" value="" onclick="openClose()" styleClass="formbutton" alt="Expand/Collapse All Menu"></html:button>
</td>
</tr>
</table>
<%int co=0;	%>
<html:hidden property="menuCount" styleId="menuCount" name="loginform"/>
<div style="float: left" id="my_menu" class="sdmenu">
	<logic:iterate name="loginform" property="moduleMenusList" id="id" indexId="count">
	<div class="collapsed" id="subMenu">
	 <span><bean:write name="id" property="moduleName"/></span>
	 <table border="0" cellpadding="0" cellspacing="0">	 
	 <logic:iterate name="id" property="menuTOList" id="menuId" type="com.kp.cms.bo.admin.Menus">
	 <%  	    
	 String link1="link_"+co;
	 String link2="div_"+co;
	 co++;
	 %>
	 <% String url = menuId.getUrl()+"&menuName="+menuId.getDisplayName(); %>
	 <input type="hidden" id="<%=link1 %>" value='<bean:write name="menuId" property="displayName"/>'/>
	 <tr id="<%=link2 %>" style="display: block">	
	 <td width="180"> 
	 <c:choose>
	<c:when  test="${menuId.newtab}">
	<a target="_blank" href="<bean:write name="menuId" property="url"/>" styleClass="menuLink"> <img src="images/smallArrow.gif" width="13" height="11" border="0" align="absmiddle"><bean:write name="menuId" property="displayName"/></a>
	</c:when>
	 <c:otherwise>
	 <a href="<%=url %>" styleClass="menuLink"> <img src="images/smallArrow.gif" width="13" height="11" border="0" align="absmiddle"><bean:write name="menuId" property="displayName"/></a>
	  </c:otherwise>
	  </c:choose>
	  </td>	
	 </tr>
	 </logic:iterate>
	 
	 </table>
	</div>
</logic:iterate>
</div>