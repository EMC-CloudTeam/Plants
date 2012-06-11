var isNav4, isIE
var coll = ""
var styleObj = ""
if (parseInt(navigator.appVersion) >= 4) {
   if (navigator.appName == "Netscape") {
      isNav4 = true
   } else {
      isIE = true
      coll = "all."
      styleObj = ".style"
   }
}

function refresh()
{
   if(refreshTree.value=="true")
         parent.navigation_tree.location.reload(true);
}


var numchecks = 0;
var allchecked = false;
var multiall = new Array();
function updateCheckAll(theForm,chkname)   {
           var temp;
           var alltemp = 0;
           var formlen = theForm.length;
           if (chkname != null) {
                   var allchkname = chkname.substring(0,chkname.indexOf("CheckBox"));
                   allchkname = "allchecked"+allchkname;
           }
           for (var i=0;i<formlen;i++) {
                var theitem = theForm.elements[i].name;
                var ischeck = theitem.indexOf("selectedObjectIds",0) + 1;   /* simple string search on checkbox consistent name, you change it to deleteID or whatever */
                var allcurcheck = theitem.indexOf(allchkname,0) +1;
                if (allcurcheck > 0) {
                        alltemp = i;
                }
                if (chkname == null) {  
                
                        if (ischeck > 0) {
                                if (allchecked != true) {
                                        theForm.elements[i].checked = true;
                                        temp = true;
                                }
                                else {  
                                        theForm.elements[i].checked = false;
                                        temp = false;
                                        
                                }
                        }
                        
                        var appitem = theForm.elements[i].name;        
                        var appcheck = appitem.indexOf("checkBoxes",0) + 1;
                         if ((appitem == "checkBoxes1") || (appitem == "checkBoxes2")) {
                                appcheck = 0;        
                        }
                        
                        if (appcheck > 0) {
                                if (allchecked != true) {
                                        theForm.elements[i].checked = true;
                                        temp = true;
                                                                
                                }
                                else {  
                                        theForm.elements[i].checked = false;
                                        temp = false;
                                        
                                }
                        }
        
 
                 } else {  
                
                                var curitem = theForm.elements[i].name;        
                                //var curcheck = curitem.indexOf(chkname[0].name,0) + 1;
                                
                                var curcheck = curitem.indexOf(chkname,0) + 1;
                                
                                
                                if (curcheck > 0) {
                                        if ((allchecked != true) && (multiall[allchkname] != true)) {
                                                theForm.elements[i].checked = true;
                                                temp = true;

                                                                        
                                        }
                                        else {  
                                                theForm.elements[i].checked = false;
                                                temp = false;
                                                
                                        }
                                }
                        
                     
                     }  
       
        
        
        
           }
           

           if (temp == true) { 
                if (chkname == null) {
                        allchecked = true; 
                        theForm.allchecked.checked = true;                
                } else {
                        multiall[allchkname] = true; 
                        theForm.elements[alltemp].checked = true;
                }
           }
           else { 
                if (chkname == null) {
                        allchecked = false; 
                        theForm.allchecked.checked = false;                
                } else {           
                        multiall[allchkname] = false; 
                        theForm.elements[alltemp].checked = false;
                }         
           }
           
       
}


function checkChecks(theForm,chkname)
{
   var checkednum = 0;
   var uncheckednum = 0;
   var formlen = theForm.length;
   


   for (var i=0;i<formlen;i++){
        var theitem = theForm.elements[i].name;
        var ischeck = theitem.indexOf("selectedObjectIds",0) + 1;                        
        var appitem = theForm.elements[i].name;        
        var appcheck = appitem.indexOf("checkBoxes",0) + 1;
        if ((appitem == "checkBoxes1") || (appitem == "checkBoxes2")) {
                appcheck = 0;        
        }
        
        if (chkname != null) {
                var curcheck = theitem.indexOf(chkname.name,0) + 1;
                
        }
        

        if (ischeck > 0) {
                if (theForm.elements[i].checked == true) {
                          checkednum += 1;                        
                }
                else {
                        uncheckednum += 1;
                }
        }
        if (curcheck > 0) {
                if (theForm.elements[i].checked == true) {
                          checkednum += 1; 
                }
                else {
                        uncheckednum += 1;
                        
                }
               
        }
        if (appcheck > 0) {
                if (theForm.elements[i].checked == true) {
                          checkednum += 1;                        
                }
                else {
                        uncheckednum += 1;
                }
             
        }
        
   }
   
   

   if (allchecked == true) {
   
           if (uncheckednum > 0) { 
                allchecked = false; 
                theForm.allchecked.checked = false;
           }
   }
   else {
           if (uncheckednum == 0) { 
                allchecked = true; 
                theForm.allchecked.checked = true;

           }
   }

}   
