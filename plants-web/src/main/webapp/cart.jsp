<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0//EN">

<!-- 
"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use or for redistribution by customer, as part of such an application, in customer's own products."

Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001, 2002
All Rights Reserved * Licensed Materials - Property of IBM
--> 

<html>
<head>   
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="DC.LANGUAGE" scheme="rfc1766"/>

<link rel="stylesheet" href="/plants-web/PlantMaster.css" type="text/css"/>

<script type="text/javascript" src="/plants-web/applycss.js" language="JavaScript"></script>
<script>

function verifyForm()
{
    var result = true;
    var form = document.forms[0];
    for (var i = 0; i < form.elements.length; i++) {
        if (form.elements[i].name.substring(0,7) == "itemqty")
        {
            result = verifyQty(form.elements[i]);
            if (!result) {
                break;
            }
        }
    }
    return result;
}

function verifyQty(qtyfield)
{
    var result = true;
    if (isNaN(parseInt(qtyfield.value)))
    {
        result = false;
    }
    else
    {
        for (i=0; i < qtyfield.value.length; i++)
        {
           if ( isNaN(parseInt(qtyfield.value.charAt(i))) )
           {
               result = false;
               break;
           }
        }
    }               

    if (!result) 
    {
        alert("Quantity must be a valid number.");
    }

    return result;
}
</script>

</head>
<body marginwidth="0" leftmargin="0" onload="top.deselectMenu('menu1');top.deselectMenu('menu2');top.deselectMenu('menu3');top.deselectMenu('menu4')">

<%@ page import="com.emc.plants.pojo.beans.ShoppingCartItem,java.util.*" session="true" isThreadSafe="true" isErrorPage="false" %>

<%
	com.emc.plants.service.interfaces.ShoppingCart shoppingCart = 
      (com.emc.plants.service.interfaces.ShoppingCart)
      session.getAttribute(com.emc.plants.utils.Util.ATTR_CART);
Collection cartitems = null;
float  total     = 0;
if (shoppingCart != null)
{
   cartitems = shoppingCart.getItems();
   total = shoppingCart.getTotalCost();
}
%>
             
<!--<FORM method="post" action="main.asp">-->
<form onsubmit="return verifyForm();" target="_self" name="cartform" method="post" action="/plants-web/servlet/ShoppingServlet">
<table border="0" cellpadding="4" cellspacing="0" width="100%">
  <tr>
    <td class="trail"><p class="trail"><a class="trail" class="footer" href="/plants-web/promo.html" target="work">Home</a></p></td>
  </tr>
  <tr>
    <td width="100%">
      <table cellpadding="5" cellspacing="5" border="0" width="600">
        <tr>
          <td width="100%" valign="middle"><h1>Shopping Cart</h1></td>
          <td align="right" valign="middle" nowrap></td>
          <td valign="middle"></td>
        </tr>
        <tr>
          <td colspan="3"><p>Here are the items you have selected. To recalculate your total after
          changing the quantity of an item, select the 'Recalculate' button. To remove an item from
          your cart, enter "0" as the quantity. Select 'Checkout Now' to begin the checkout process.</p><br></td>
        </tr>
        <tr>
          <td colspan="3">
            <table width="600" border="0" cellpadding="2" cellspacing="0">
              <tr bgcolor="#eeeecc">
                <th><img src="/plants-web/images/1x1_trans.gif" width="4" height="4" align="left" border="0" alt=""></th>
                <th class="item" align="left">ITEM #</th>
                <th class="item" align="left" nowrap>ITEM DESCRIPTION</th>
                <th class="item" align="left">PACKAGING</th>
                <th class="item" align="left">QUANTITY</th>
                <th class="item" align="right">PRICE</th>
                <th class="item" align="right">SUBTOTAL</th>
                <th><img src="/plants-web/images/1x1_trans.gif" width="4" height="4" align="left" border="0" alt=""></th>
              </tr>
           
      <%
          if (cartitems != null)
          {
             int cnt = 0;
              for (Object o : cartitems)
              {
               ShoppingCartItem    storeItem = (ShoppingCartItem) o;

               String id      =  storeItem.getID();
               String name    =  storeItem.getName();
               int quantity   =  storeItem.getQuantity();
               float price    =  storeItem.getPrice();
               String pkginfo =  storeItem.getPkginfo();

               String priceString = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.US).format(new Float(price));
               String subtotalPriceString = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.US).format(new Float(price * quantity));
           %>
               <tr bgcolor="#ffffdd">
                 <td valign="top"><img src="/plants-web/images/1x1_trans.gif" width="2" height="22" align="left" border="0" alt=""></td>
                 <td valign="top" nowrap><p><%= id %> </p></td>
                 <td valign="top" nowrap><p><b><%= name %></b></p></td>
                 <td valign="top"><p><%= pkginfo%></p></td>
                 <td valign="top"><p><input type="text" name="itemqty<%=cnt%>" size=3 maxlength=3 value=<%= quantity%> ></p></td>
                 <td align="right" valign="top"><p><%= priceString %></p></td>
                 <td align="right" valign="top"><p><%= subtotalPriceString %></p></td>
                 <td valign="top"><img src="/plants-web/images/1x1_trans.gif" width="2" height="22" align="left" border="0" alt=""></td>
               </tr>

           <%
               cnt++;
              }
           }
         %>
            
            <tr bgcolor="#ffffdd">
              <td align="right" valign="top" colspan="8">
              <hr size="1" noshade></td>
            </tr>
            <tr bgcolor="#ffffdd">
              <td align="right" valign="top" colspan="7">
              <p><b>Order Subtotal:<%= java.text.NumberFormat.getCurrencyInstance(java.util.Locale.US).format(new Float(total))%></b></p></td>
               <td valign="top"><img src="/plants-web/images/1x1_trans.gif" width="2" height="22" align="left" border="0" alt=""></td>
            </tr>
            <tr>
              <td colspan="6" valign="top"> </td>
              <td valign="top"><img src="/plants-web/images/1x1_trans.gif" width="2" height="22" align="left" border="0" alt=""></td>
            </tr>
            <tr>
              <td valign="top" colspan="6">
                <table cellpadding="0" cellspacing="0" border="0">
                  <tr>
                    <td nowrap>
                      <a href="/plants-web/servlet/ShoppingServlet?action=shopping" target="work">
                      <img src="/plants-web/images/button_continue_shopping.gif" alt="Continue Shopping" width="113" height="27" border="0"></a>
                    </td>
<%if (cartitems != null)
{
%>                    
                    <td>
                        <input type="image" name="Recalculate" value="updatequantity" alt="Recalculate" src="/plants-web/images/button_recalculate.gif">
                        <input type="hidden" name="action" value="updatequantity">
                    </td>
                    <td>
                      <a href="/plants-web/servlet/ShoppingServlet?action=initcheckout" target="work">
                      <img src="/plants-web/images/button_checkout_now.gif" alt="Checkout Now" width="95" height="27" border="0"></a>
                    </td>
<%
}
%>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table></td>
  </tr>
</table>
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
                  <a class="footer" href="/plants-web/servlet/ShoppingServlet?action=gotocart" target="work">Shopping Cart</a>&nbsp;&nbsp;:&nbsp;
                  <a class="footer" href="/plants-web/servlet/AccountServlet?action=account" target="work">My Account</a>&nbsp;&nbsp;:&nbsp;
                  <a class="footer" href="/plants-web/login.jsp" target="work">Login</a>&nbsp;&nbsp;:&nbsp;
                  <a class="footer" href="/plants-web/help.jsp" target="_blank">Help</a></p>
    </td>
  </tr>
</table>
</body>
</html>
