//
//"This program may be used, executed, copied, modified and distributed without royalty for the 
//purpose of developing, using, marketing, or distributing."

//Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001, 2002
//All Rights Reserved * Licensed Materials - Property of IBM
// 

var i = navigator.appVersion.indexOf('MSIE 6');


if((navigator.appName == "Microsoft Internet Explorer") && (parseInt(navigator.appVersion) >= 4 ))
  {
document.write('<link rel="stylesheet" href="/PlantsByWebSphere/PlantMaster.css" type="text/css"/>')}
    else
    {document.write('<link rel="stylesheet" href="/PlantsByWebSphere/PlantMaster_ns.css" type="text/css"/>')}