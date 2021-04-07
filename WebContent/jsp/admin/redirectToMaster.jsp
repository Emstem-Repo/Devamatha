<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<html:form action="<%= request.getAttribute("actionClass").toString()%>">
<html:hidden property="mainPage" styleId="mainPage"/>
<html:hidden property="destinationMethod" styleId="destinationMethod"/>
<html:hidden property="addNewType" styleId="addNewType"/>
<html:hidden property="formName" value="baseActionForm"/>
<html:hidden property="superMainPage" styleId="superMainPage"/>
<html:hidden property="superAddNewType" styleId="superAddNewType"/>
</html:form>


<script type="text/javascript">
	var actionClass="<%= request.getAttribute("actionClass").toString()%>";
	if(actionClass=="quotationSubmit" || actionClass=="purchaseOrderSubmit"){
		var redirectPage=document.getElementById("superMainPage").value;
		 var masterPage=document.getElementById("superAddNewType").value;
		 var masterInitMethod=document.getElementById("destinationMethod").value;
		 document.location.href=masterPage+".do?method="+masterInitMethod+"&superMainPage="+redirectPage+"&mainPageExists=true";
	}
	else {
	 var redirectPage=document.getElementById("mainPage").value;
	 var masterPage=document.getElementById("addNewType").value;
	 var masterInitMethod=document.getElementById("destinationMethod").value;
	 document.location.href=masterPage+".do?method="+masterInitMethod+"&mainPage="+redirectPage+"&mainPageExists=true";
	}
</script>
</body>
</html>