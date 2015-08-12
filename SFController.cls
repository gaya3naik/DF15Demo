/* Copyright (c) 2013 Gainsight Software, Inc.
*
* Purpose                       : This class is used to do CRUD operations given the object name from Salesforce
.
*
* Objects updated               :
*
* Objects referenced            :
*
* Operations                    :
*
* Other comments (optional)     :
*
*****/

global with sharing class SFController {


   String[] options = new String[];

   public List<SelectOption> getOptions(){
    List<SelectOption> options = new List<SelectOption>();
    options.add(new SelectOption('MM','Measure VS Measure'));
    options.add(new SelectOption('GM','Growth Metrics'));
    //options.add(new SelectOption('MvsM','Measure VS Measure'));
    return options;

   }

}