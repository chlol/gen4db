<!DOCTYPE composition PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
                             "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#assign entityName = pojo.shortName>
<#assign componentName = util.lower(entityName)>
<#assign homeName = componentName + "Home">
<#assign masterPageName = entityName + "List">
<#assign pageName = entityName>

<ui:composition xmlns="http://www.w3.org/1999/xhtml"
                xmlns:s="http://jboss.com/products/seam/taglib"
                xmlns:ui="http://java.sun.com/jsf/facelets"
                xmlns:f="http://java.sun.com/jsf/core"
                xmlns:h="http://java.sun.com/jsf/html"
                xmlns:a="http://richfaces.org/a4j"
                xmlns:rich="http://richfaces.org/rich"
                template="/layout/template.xhtml">
                       
<ui:define name="body">
    
    <h:messages globalOnly="true" styleClass="message" id="globalMessages"/>

    <h:form id="${componentName}" styleClass="edit">
    
        <rich:panel>
            <f:facet name="header">${'#'}{messages['lable.header.edit']}${'#'}{messages['${componentName}']}</f:facet>
<#foreach property in pojo.allPropertiesIterator>
<#include "editproperty.xhtml.ftl">
</#foreach>
        
            <div style="clear:both">
                <span class="required">*</span> 
                ${'#'}{messages['label.hint.required']}
            </div>
            
        </rich:panel>
                
        <div class="actionButtons">

            <h:commandButton id="save" 
                          value="${'#'}{messages['button.save']}" 
                         action="${'#'}{${homeName}.persist}"
                       disabled="${'#'}{!${homeName}.wired}"
                       rendered="${'#'}{!${homeName}.managed}"/>  
                          			  
            <h:commandButton id="update" 
                          value="${'#'}{messages['button.save']}" 
                         action="${'#'}{${homeName}.update}"
                       rendered="${'#'}{${homeName}.managed}"/>
                        			  
            <h:commandButton id="delete" 
                          value="${'#'}{messages['button.delete']}" 
                         action="${'#'}{${homeName}.remove}"
                       rendered="${'#'}{${homeName}.managed}"/>
                    
            <s:button id="done" 
                   value="${'#'}{messages['button.done']}"
             propagation="end"
                    view="/${module}/${pageName}.xhtml"
                rendered="${'#'}{${homeName}.managed}"/>
                
            <s:button id="cancel" 
                   value="${'#'}{messages['button.cancel']}"
             propagation="end"
                    view="/${module}/${'#'}{empty ${componentName}From ? '${masterPageName}' : ${componentName}From}.xhtml"
                rendered="${'#'}{!${homeName}.managed}"/>

        </div>
    </h:form>

</ui:define>

</ui:composition>
