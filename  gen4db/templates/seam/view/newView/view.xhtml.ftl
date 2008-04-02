<!DOCTYPE composition PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
                             "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#assign entityName = pojo.shortName>
<#assign componentName = util.lower(entityName)>
<#assign homeName = componentName + "Home">
<#assign masterPageName = entityName + "List">
<#assign editPageName = entityName + "Edit">

<ui:composition xmlns="http://www.w3.org/1999/xhtml"
                xmlns:s="http://jboss.com/products/seam/taglib"
                xmlns:ui="http://java.sun.com/jsf/facelets"
                xmlns:f="http://java.sun.com/jsf/core"
                xmlns:h="http://java.sun.com/jsf/html"
                xmlns:rich="http://richfaces.org/rich"
                template="/layout/template.xhtml">
                       
<ui:define name="body">
    
    <h:messages globalOnly="true" styleClass="message" id="globalMessages"/>
    
    <rich:panel>
        <f:facet name="header">${'#'}{messages['${componentName}']}${'#'}{messages['label.header.info']}</f:facet>
<#foreach property in pojo.allPropertiesIterator>
<#if !c2h.isCollection(property) && !c2h.isManyToOne(property) && property != pojo.versionProperty!>
<#include "viewproperty.xhtml.ftl">
</#if>
</#foreach>

        <div style="clear:both"/>
        
    </rich:panel>
    
    <div class="actionButtons">      

        <s:button view="/${module}/${editPageName}.xhtml" 
                    id="edit" 
                 value="${'#'}{messages['button.edit']}"/>

        <s:button view="/${module}/${'#'}{empty ${componentName}From ? '${masterPageName}' : ${componentName}From}.xhtml"
                    id="done"
                 value="${'#'}{messages['button.done']}"/>

    </div>
<#assign hasAssociations=false>
<#foreach property in pojo.allPropertiesIterator>
<#if c2h.isManyToOne(property) || c2h.isOneToManyCollection(property)>
<#assign hasAssociations=true>
</#if>
</#foreach>

</ui:define>

</ui:composition>
