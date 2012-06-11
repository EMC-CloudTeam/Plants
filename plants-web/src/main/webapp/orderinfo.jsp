<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0//EN">

<!--
"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use or for redistribution by customer, as part of such an application, in customer's own products."

Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001, 2004
All Rights Reserved * Licensed Materials - Property of IBM
-->

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="DC.LANGUAGE" scheme="rfc1766"/>

<link href="/plants-web/PlantMaster.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="/plants-web/applycss.js" language="JavaScript"></script>
<script>
function useBill()
{
   var billship = document.billship;
   if (billship.shipisbill.checked == true)
   {
	  billship.sname.value = billship.bname.value;
	  billship.saddr1.value = billship.baddr1.value;
	  billship.saddr2.value = billship.baddr2.value;
	  billship.scity.value = billship.bcity.value;
	  billship.sstate.value = billship.bstate.value;
	  billship.szip.value = billship.bzip.value;
	  billship.sphone.value = billship.bphone.value;
   }
   else
   {
      billship.sname.value = '';
      billship.saddr1.value = '';
      billship.saddr2.value = '';
      billship.scity.value = '';
      billship.sstate.value = '';
      billship.szip.value = '';
      billship.sphone.value = '';
   }
}

function verifyForm(billship)
{
    if ((!exists(billship.bname.value)) ||
		  (!exists(billship.baddr1.value)) ||
		  (!exists(billship.bcity.value)) ||
		  (!exists(billship.bstate.value)) ||
		  (!exists(billship.bzip.value)) ||
		  (!exists(billship.bphone.value)) ||
		  (!exists(billship.sname.value)) ||
		  (!exists(billship.saddr1.value)) ||
		  (!exists(billship.scity.value)) ||
		  (!exists(billship.sstate.value)) ||
		  (!exists(billship.szip.value)) ||
		  (!exists(billship.sphone.value)) ||
  		  (!exists(billship.ccardnum.value)) ||
		  (!exists(billship.ccholdername.value)))
	{
		alert("All required fields must be filled in.");
		return false;
	}
	else if (!verifyNum(billship.bzip.value)) {
	    alert("Billing Zip Code is not valid.");
	    return false;
	}
	else if (!verifyNum(billship.szip.value)) {
	    alert("Shipping Zip Code is not valid.");
	    return false;
	}
	else if (!verifyPhone(billship.bphone.value)) {
	    alert("Billing Phone is not valid.");
	    return false;
	}
	else if (!verifyPhone(billship.sphone.value)) {
	    alert("Shipping Phone is not valid.");
	    return false;
	}
	else if (!verifyNum(billship.ccardnum.value)) {
	    alert("Credit Card Number is not valid.");
	    return false;
	}
	return true;
}

function exists(inputVal)
{
	 var result = false;
    for (var i = 0; i <= inputVal.length; i++) {
        if ((inputVal.charAt(i) != " ") && (inputVal.charAt(i) != "")) {
            result = true;
            break;
		  }
	 }
    return result;
}

function verifyNum(numVal)
{
	 var result = false;
    for (var i = 0; i < numVal.length; i++) {
        if (parseFloat(numVal.charAt(i))) {
            result = true;
            break;
		  }
	 }
    return result;
}

function verifyPhone(phoneVal)
{
	 var result = false;
	 var cnt = 0;
    for (var i = 0; i < phoneVal.length; i++) {
        if (parseFloat(phoneVal.charAt(i))) {
            cnt++;
            if (cnt >= 7) {
					 result = true;
					 break;
				}
		  }
	 }
    return result;
}
</script>

</head>
<body class="work" onload="top.deselectMenu('menu1');top.deselectMenu('menu2');top.deselectMenu('menu3');top.deselectMenu('menu4')">
<%
	com.emc.plants.pojo.beans.CustomerInfo customerInfo =
      (com.emc.plants.pojo.beans.CustomerInfo)
      session.getAttribute(com.emc.plants.utils.Util.ATTR_CUSTOMER);
String[] shippingMethods = com.emc.plants.utils.Util.getFullShippingMethodStrings();
%>

<form onsubmit="return verifyForm(this);" target="_self" name="billship" method="post" action="/plants-web/servlet/ShoppingServlet?action=orderinfodone">
<table border="0" cellpadding="0" cellspacing="5" width="100%">
  <tr>
    <td><p class="trail"><a class="trail" class="footer" href="/plants-web/promo.html" target="work">Home</a> &gt; <a class="trail" class="footer" href="/plants-web/servlet/ShoppingServlet?action=gotocart" target="work">Shopping Cart</a></p></td>
  </tr>
  <tr>
    <td width="100%">
      <table cellpadding="5" cellspacing="5" border="0" width="600">
        <tr>
          <td width="100%" valign="middle"><H1>Checkout</H1></td>
          <td align="right" valign="middle" nowrap></td>
	      <td valign="middle"></td>
	     </tr>
        <tr>
          <td colspan="3"><p>Enter the billing and shipping information for your order below. Select 'Continue' to review and place your final order.</p><br></td>
        </tr>
        <tr>
          <td colspan="3">
<table border="0" cellpadding="0" cellspacing="0">
<caption>Billing Address</caption>
<colgroup class="label">
<colgroup>
<colgroup>
<tbody>
  <TR>
    <TD nowrap width="120"><P><label for="bname">Full Name&nbsp;</label></TD>
    <TD><IMG border="0" src="/plants-web/images/required.gif" width="10" height="15" alt="Required indicator"></TD>
    <TD width="100%"><P><input type="text" name="bname" size="20" maxlength="80" value="<%=customerInfo.getFirstName() + " " + customerInfo.getLastName()%>"></P></TD>
  </TR>
  <tr>
    <td nowrap width="120"><p><label for="baddr1">Address Line 1&nbsp;</label></td>
    <td><img border="0" src="/plants-web/images/required.gif" width="10" height="15" alt="Required indicator"></td>
    <td width="100%"><p><input type="text" name="baddr1" size="20" maxlength="80" value="<%=customerInfo.getAddr1()%>"></p></td>
  </tr>
  <tr>
    <td nowrap><p><label for="baddr2">Address Line 2&nbsp;</label></td>
    <td></td>
    <td><p><input type="text" name="baddr2" size="20" maxlength="80" value="<%=customerInfo.getAddr2()%>"></p></td>
  </tr>
  <tr>
    <td nowrap><p><label for="bcity">City&nbsp;</label></td>
    <td><img border="0" src="/plants-web/images/required.gif" width="10" height="15" alt="Required indicator"></td>
    <td><p><input type="text" name="bcity" size="20" maxlength="80" value="<%=customerInfo.getAddrCity()%>"></p></td>
  </tr>
  <tr>
    <td nowrap><p><label for="bstate">State&nbsp;</label></td>
    <td><img border="0" src="/plants-web/images/required.gif" width="10" height="15" alt="Required indicator"></td>
    <td><p><input type="text" name="bstate" size="20" maxlength="30" value="<%=customerInfo.getAddrState()%>"></p></td>
  </tr>
  <tr>
    <td nowrap><p><label for="bzip">Zip Code&nbsp;</label></td>
    <td><img border="0" src="/plants-web/images/required.gif" width="10" height="15" alt="Required indicator"></td>
    <td><p><input type="text" name="bzip" size="10" maxlength="10" value="<%=customerInfo.getAddrZip()%>"></p></td>
  </tr>
  <tr>
    <td nowrap><p><label for="bphone">Phone (daytime)&nbsp;</label></td>
    <td><img border="0" src="/plants-web/images/required.gif" width="10" height="15" alt="Required indicator"></td>
    <td><p><input type="text" name="bphone" size="14" maxlength="14" value="<%=customerInfo.getPhone()%>"></p></td>
  </tr>
  <tr>
    <td colspan="3">&nbsp;</td>
  </tr>
  </tbody>
</table></td>    <!-- end of Billing Address table -->
      </tr>
        <tr>
          <td colspan="3">
<table border="0" cellpadding="0" cellspacing="5">
<caption>Shipping Information</caption>
<colgroup class="label">
<colgroup>
<colgroup>
<tbody>
  <tr>
    <td colspan="3">
      <table border="0" cellpadding="5" cellspacing="5">
        <tr>
          <td valign="middle"><p><input name="shipisbill" type="checkbox" onClick="javascript:useBill();">&nbsp;</p></td>
          <td valign="middle"><p>Check here if the shipping address is the same as the billing address.</p></td>
        </tr>
      </table>
    </td>
  </tr>
  <TR>
    <TD nowrap width="120"><P><label for="sname">Full Name&nbsp;</label></TD>
    <TD><IMG border="0" src="/plants-web/images/required.gif" width="10" height="15" alt="Required indicator"></TD>
    <TD width="100%"><P><input type="text" name="sname" size="20"></P></TD>
  </TR>
  <tr>
    <td nowrap><p><label for="saddr1">Address Line 1&nbsp;</label></td>
    <td><img border="0" src="/plants-web/images/required.gif" width="10" height="15" alt="Required indicator"></td>
    <td width="100%"><p><input type="text" name="saddr1" size="20"></p></td>
  </tr>
  <tr>
    <td nowrap width="120"><p><label for="saddr2">Address Line 2&nbsp;</label></td>
    <td></td>
    <td><p><input type="text" name="saddr2" size="20"></p></td>
  </tr>
  <tr>
    <td nowrap><p><label for="scity">City&nbsp;</label></td>
    <td><img border="0" src="/plants-web/images/required.gif" width="10" height="15" alt="Required indicator"></td>
    <td><p><input type="text" name="scity" size="20"></p></td>
  </tr>
  <tr>
    <td nowrap><p><label for="sstate">State&nbsp;</label></td>
    <td><img border="0" src="/plants-web/images/required.gif" width="10" height="15" alt="Required indicator"></td>
    <td><p><input type="text" name="sstate" size="20"> </p></td>
<!--
    <td><p><select size="1" name="sstate">
       <OPTION value="AL">Alabama</OPTION>
       <OPTION value="AK">Alaska</OPTION>
       <OPTION value="AZ">Arizona</OPTION>
       <OPTION value="AR">Arkansas</OPTION>
       <OPTION value="CA">California</OPTION>
       <OPTION value="CO">Colorado</OPTION>
       <OPTION value="CT">Connecticut</OPTION>
       <OPTION value="DE">Delaware</OPTION>
       <OPTION value="DC">District of Columbia</OPTION>
       <OPTION value="FL">Florida</OPTION>
       <OPTION value="GA">Georgia</OPTION>
       <OPTION value="HI">Hawaii</OPTION>
       <OPTION value="ID">Idaho</OPTION>
       <OPTION value="IL">Illinois</OPTION>
       <OPTION value="IN">Indiana</OPTION>
       <OPTION value="IA">Iowa</OPTION>
       <OPTION value="KS">Kansas</OPTION>
       <OPTION value="KY">Kentucky</OPTION>
       <OPTION value="LA">Louisiana</OPTION>
       <OPTION value="ME">Maine</OPTION>
       <OPTION value="MD">Maryland</OPTION>
       <OPTION value="MA">Massachusetts</OPTION>
       <OPTION value="MI">Michigan</OPTION>
       <OPTION value="MN">Minnesota</OPTION>
       <OPTION value="MS">Mississippi</OPTION>
       <OPTION value="MO">Missouri</OPTION>
       <OPTION value="MT">Montana</OPTION>
       <OPTION value="NE">Nebraska</OPTION>
       <OPTION value="NV">Nevada</OPTION>
       <OPTION value="NH">New Hampshire</OPTION>
       <OPTION value="NJ">New Jersey</OPTION>
       <OPTION value="NM">New Mexico</OPTION>
       <OPTION value="NY">New York</OPTION>
       <OPTION value="NC">North Carolina</OPTION>
       <OPTION value="ND">North Dakota</OPTION>
       <OPTION value="OH">Ohio</OPTION>
       <OPTION value="OK">Oklahoma</OPTION>
       <OPTION value="OR">Oregon</OPTION>
       <OPTION value="PA">Pennsylvania</OPTION>
       <OPTION value="RI">Rhode Island</OPTION>
       <OPTION value="SC">South Carolina</OPTION>
       <OPTION value="SD">South Dakota</OPTION>
       <OPTION value="TN">Tennessee</OPTION>
       <OPTION value="TX">Texas</OPTION>
       <OPTION value="UT">Utah</OPTION>
       <OPTION value="VT">Vermont</OPTION>
       <OPTION value="VA">Virginia</OPTION>
       <OPTION value="WA">Washington</OPTION>
       <OPTION value="WV">West Virginia</OPTION>
       <OPTION value="WI">Wisconsin</OPTION>
       <OPTION value="WY">Wyoming</OPTION>
    </select></p></td>
    -->
  </tr>
  <tr>
    <td nowrap><p><label for="szip">Zip Code&nbsp;</label></td>
    <td><img border="0" src="/plants-web/images/required.gif" width="10" height="15" alt="Required indicator"></td>
    <td><p><input type="text" name="szip" size="5" maxlength="10"></p></td>
  </tr>
  <tr>
    <td nowrap><p><label for="sphone">Phone (daytime)&nbsp;</label></td>
    <td><img border="0" src="/plants-web/images/required.gif" width="10" height="15" alt="Required indicator"></td>
    <td><p><input type="text" name="sphone" size="14" maxlength="14"></p></td>
  </tr>



  </tbody>
</table>    <!-- end of Shipping Address table -->
  </tr>
  <tr>
     <td colspan="3">
<table border="0" cellspacing="5" Cellpadding="0">
<caption>Shipping Method</caption>
  <tr>
   <td colspan="3"><p>Select a shipping method below. Your order total will be updated on the next page. </p><br></td>
  </tr>
  <tr>
   <td width="120" nowrap><p><label for="shippingMethod">Shipping Method&nbsp;</label></p></td>
   <td><img border="0" src="/plants-web/images/required.gif" width="10" height="15" alt="Required indicator"></td>
   <td width="100%"><select name="shippingMethod">
   <%for (int i = 0; i < shippingMethods.length; i++)
     {
        if (i == 0)
        {%> <option selected value="<%=i%>"><%=shippingMethods[i]%>
      <%}
        else
        {%> <option value="<%=i%>"><%=shippingMethods[i]%>
      <%}
     } %>
     </select>
    </td>
  </tr>
  <tr>
    <td colspan="3">&nbsp;</td>
  </tr>

  <tr>
    <td nowrap width="120"><p><label for="cc">Credit Card&nbsp;</label></td>
    <td><img border="0" src="/plants-web/images/required.gif" width="10" height="15" alt="required indicator"></td>
    <!--
    <td width="100%"><p><input type="text" name="ccardname" size="20"></p></td>
    -->
    <td><p><select size="1" name="ccardname">
        <option value="American Express">American Express</option>
        <option value="Discover">Discover</option>
        <option value="MasterCard">MasterCard</option>
        <option value="VISA">VISA</option></select></p></td>
  </tr>
  <tr>
    <td nowrap><p><label for="cn">Card Number&nbsp;</label></td>
    <td><img border="0" src="/plants-web/images/required.gif" width="10" height="15" alt="required indicator"></td>
    <td><input type="text" name="ccardnum" size="20"></td>
  </tr>
  <tr>
    <td nowrap><p><label for="em">Expiration Month&nbsp;</label></td>
    <td><img border="0" src="/plants-web/images/required.gif" width="10" height="15" alt="required indicator"></td>
    <td><p><select size="1" name="ccexpiresmonth">
        <option value="01">01</option>
        <option value="02">02</option>
        <option value="03">03</option>
        <option value="04">04</option>
        <option value="05">05</option>
        <option value="06">06</option>
        <option value="07">07</option>
        <option value="08">08</option>
        <option value="09">09</option>
        <option value="10">10</option>
        <option value="11">11</option>
        <option value="12">12</option></select></p></td>
  </tr>
  <tr>
    <td nowrap><p><label for="ey">Expiration Year&nbsp;</label></td>
    <td><img border="0" src="/plants-web/images/required.gif" width="10" height="15" alt="required indicator"></td>
    <td><p><select size="1" name="ccexpiresyear">
		<%
			java.util.Calendar cal = java.util.Calendar.getInstance();
			int year = cal.get(java.util.Calendar.YEAR);
		%>
		<%
			for (int y=year;y<=year+5;y++){
		%>
        	<option value="<%=y%>"><%=y%></option>
		<%  }	%>
        </select></p></td>
  </tr>
  <tr>
    <td nowrap><p><label for="cn">Cardholder Name&nbsp;</label></td>
    <td><img border="0" src="/plants-web/images/required.gif" width="10" height="15" alt="required indicator"></td>
    <td><p><input type="text" name="ccholdername" size="20" value=""></p></td>
  </tr>
  <TR><TD><P>&nbsp;</P></TD></TR>
  <TR>
    <td><p>&nbsp;</p></td>
    <td></td>
    <td>
        <input type="image" alt="Continue" src="/plants-web/images/button_continue.gif"><a href="javascript:verifyForm(this);"></a>
    </td>
  </tr>
</table></td>
  </tr>
    </table></td>    <!-- end of Checkout table -->
  </tr>
</table><br>    <!-- end of Home table -->
<table bgcolor="#669966" border="0" cellpadding="0" cellspacing="0" width="100%">

  <tr>
    <td width="1"><img border="0" src="/plants-web/images/1x1_trans.gif" width="1" height="1" alt=""></td>
  </tr>
</table>
</form>
<br>
<table border="0" cellpadding="5" cellspacing="0" width="100%">
  <tr>
    <td>
      <img src="/plants-web/images/poweredby_WebSphere.gif" alt="Powered by WebSphere">
    </td>
    <td>
      <p class="footer"><a class="footer" href="/plants-web/servlet/ShoppingServlet?action=shopping&category=0" target="work">Flowers</a>&nbsp;&nbsp;:&nbsp;
                  <a class="footer" href="/plants-web/servlet/ShoppingServlet?action=shopping&category=1" target="work">Fruits &amp; Vegetables</a>&nbsp;&nbsp;:&nbsp;
                  <a class="footer" href="/plants-web/servlet/ShoppingServlet?action=shopping&category=2" target="work">Trees</a>&nbsp;&nbsp;:&nbsp;
                  <a class="footer" href="/plants-web/servlet/ShoppingServlet?action=shopping&category=3" target="work">Accessories</a><br>
                  <a class="footer" href="/plants-web/index.html" target="_top">Home</a>&nbsp;&nbsp;:&nbsp;
                  <a class="footer" href="/plants-web/cart.jsp" target="work">Shopping Cart</a>&nbsp;&nbsp;:&nbsp;
                  <a class="footer" href="/plants-web/servlet/AccountServlet?action=account" target="work">My Account</a>&nbsp;&nbsp;:&nbsp;
                  <a class="footer" href="/plants-web/login.jsp" target="work">Login</a>&nbsp;&nbsp;:&nbsp;
                  <a class="footer" href="/plants-web/help.jsp" target="_blank">Help</a></p>
    </td>
  </tr>
</table>
</body>
</html>