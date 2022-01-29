function submitPurchaseForm(method){
	document.purchaseOrderForm.method.value=method;
	document.purchaseOrderForm.submit();
}


function submitQuotationForm(method){
	document.quotationForm.method.value=method;
	document.quotationForm.submit();
}

function submitQuoteAdditem(){
	document.quotationForm.method.value="addItemList";
	document.quotationForm.pageType.value=3;
	document.quotationForm.submit();
}

function submitQuotationCancel(){
	document.quotationForm.method.value="initQuotaion";
	document.quotationForm.submit();
}


function submitPurchaseReturn(method){
	document.purchaseReturnForm.method.value=method;
	document.purchaseReturnForm.submit();
}

function cancelPurchaseReturn(){
	document.purchaseReturnForm.method.value="initPurchaseReturn";
	document.purchaseReturnForm.submit();
}

function submitStockReceipt(method){
	document.stockReceiptForm.method.value=method;
	document.stockReceiptForm.submit();
}

function cancelStockReceipt(){
	document.stockReceiptForm.method.value="initStockReceipt";
	document.stockReceiptForm.submit();
}


function submitAdditem(){
	document.purchaseOrderForm.method.value="addItemList";
	document.purchaseOrderForm.pageType.value=3;
	document.purchaseOrderForm.submit();
}

function submitCancel(){
	document.purchaseOrderForm.method.value="initPurchaseOrder";
	document.purchaseOrderForm.submit();
}
function resetItemEntry(){
	if(document.getElementById('selectedItemId')!=null){
		document.getElementById('selectedItemId').selectedIndex = -1;  
	}
	if(document.getElementById('selectedItemQty')!=null){
		document.getElementById('selectedItemQty').value="";  
	}
	resetErrMsgs();
}

function searchSubject(searchValue){
	var sda = document.getElementById('selectedItemId');
	var len = sda.length;
	var searchValueLen = searchValue.length;
	for(var m =0; m<len; m++){
		sda.options[m].selected = false;		
	}
	for(var j=0; j<len; j++)
	{
		for(var i=0; i<len; i++){
			if(((sda.options[i].text).substring(0, searchValueLen).toUpperCase())  == (searchValue.toUpperCase())){
				sda.options[i].selected = true;
				break;
			}
		}
	}
}

function deletePurchaseItem(itemId){
	document.purchaseOrderForm.deleteItemId.value=itemId;
	document.purchaseOrderForm.method.value="deleteItemFormList";
	document.purchaseOrderForm.submit();
}

function deleteQuotePurchaseItem(itemId){
	document.quotationForm.deleteItemId.value=itemId;
	document.quotationForm.method.value="deleteItemFormList";
	document.quotationForm.submit();
}

 function updatetotalCost(){
	 var addncst="0.0";
	 var totcst="0.0";
	 var addncost=0;
	 var totalcost=0;
	 if(document.getElementById('addnCost')!=null){
		 addncst=document.getElementById('addnCost').value;  
		}
		if(document.getElementById('totalPurCost')!=null){
			totcst=document.getElementById('totalPurCost').value; 
			

		}
		
		if(addncst!=null && addncst.length>0 && IsNumeric(addncst))
			{
			addncost=parseFloat(addncst);
			}
		if(totcst!=null && totcst.length >0 && IsNumeric(totcst))
		{
			
			totalcost=parseFloat(totcst);
			
			totalcost=totalcost+addncost;
		}else{
			totalcost=addncost;
		}
		if(document.getElementById('totCost')!=null){

			document.getElementById('totCost').value=totalcost;  
		}	
		
 }
 
 function IsNumeric(sText)
 {
    var ValidChars = "0123456789.E";
    var IsNumber=true;
    var Char;
    for (i = 0; i < sText.length && IsNumber == true; i++) 
       { 
       Char = sText.charAt(i); 
       if (ValidChars.indexOf(Char) == -1) 
          {
          IsNumber = false;
          }
       }
    return IsNumber;
    
    }