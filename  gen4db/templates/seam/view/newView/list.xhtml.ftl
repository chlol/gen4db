<!DOCTYPE composition PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
                             "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#include "../../../util/TypeInfo.ftl">
<#assign entityName = pojo.shortName>
<#assign componentName = util.lower(entityName)>
<#assign listName = componentName + "List">
<#assign pageName = entityName>
<#assign editPageName = entityName + "Edit">
<#assign listPageName = entityName + "List">

<ui:composition xmlns="http://www.w3.org/1999/xhtml"
                xmlns:s="http://jboss.com/products/seam/taglib"
                xmlns:ui="http://java.sun.com/jsf/facelets"
                xmlns:f="http://java.sun.com/jsf/core"
                xmlns:h="http://java.sun.com/jsf/html"
                xmlns:rich="http://richfaces.org/rich"
                template="/layout/template.xhtml">
                       
<ui:define name="body">
    
    <h:messages globalOnly="true" styleClass="message" id="globalMessages"/>
    
    <h:form id="${componentName}Search" styleClass="edit">
    
        <rich:simpleTogglePanel label="${'#'}{messages['${componentName}']}${'#'}{messages['label.searchCondition.title']}" switchType="ajax">
        
<#foreach property in pojo.allPropertiesIterator>
<#if !c2h.isCollection(property) && !c2h.isManyToOne(property) && property != pojo.versionProperty!>
<#if c2j.isComponent(property)>
<#foreach componentProperty in property.value.propertyIterator>
<#if isString(componentProperty)>
            <s:decorate template="/layout/display.xhtml">
                <ui:define name="label">${'#'}{messages['${componentName}.${componentProperty.name}']}</ui:define>
                <h:inputText id="${componentProperty.name}" value="${'#'}{${listName}.${componentName}.${property.name}.${componentProperty.name}}"/>
            </s:decorate>

</#if>
</#foreach>
<#else>
<#if isString(property)>
            <s:decorate template="/layout/display.xhtml">
                <ui:define name="label">${'#'}{messages['${componentName}.${property.name}']}</ui:define>
                <h:inputText id="${property.name}" value="${'#'}{${listName}.${componentName}.${property.name}}"/>
            </s:decorate>

</#if>
</#if>
</#if>
</#foreach>
        
        </rich:simpleTogglePanel>
        
        <div class="actionButtons">
            <h:commandButton id="search" value="${'#'}{messages['button.search']}" action="/${module}/${listPageName}.xhtml"/>
        </div>
        
    </h:form>
    <h:form id="${listName}Form">
    <rich:panel>
        <f:facet name="header">${'#'}{messages['${componentName}']}${'#'}{messages['label.searchResult.title']}</f:facet>
    <div class="results" id="${componentName}ListResults">

    <h:outputText value="${'#'}{messages['${componentName}']}${'#'}{messages['label.searchResult.notExist']}" 
               rendered="${'#'}{empty ${listName}.resultList}"/>
    <rich:datascroller align="${'#'}{messages['constant.datascroller.align']}"  for="${listName}" maxPages="${'#'}{messages['constant.datascroller.maxPages']}" rendered="${'#'}{not empty roleList.resultList}"/>           
    <rich:dataTable id="${listName}" 
                var="${componentName}"
              value="${'#'}{${listName}.resultList}" 
               rows="${'#'}{messages['constant.pageSize']}"
           rendered="${'#'}{not empty ${listName}.resultList}">
<#foreach property in pojo.allPropertiesIterator>
<#if !c2h.isCollection(property) && !c2h.isManyToOne(property) && property != pojo.versionProperty!>
<#if pojo.isComponent(property)>
<#foreach componentProperty in property.value.propertyIterator>
        <h:column>
            <f:facet name="header">${componentProperty.name}</f:facet>
            ${'#'}{${componentName}.${property.name}.${componentProperty.name}}
        </h:column>
</#foreach>
<#else>
        <h:column>
            <f:facet name="header">
                <s:link styleClass="columnHeader"
                             value="${'#'}{messages['${componentName}.${property.name}']} ${'#'}{${listName}.orderColumn=='${property.name}' ? (${listName}.orderDirection=='desc' ? messages.down : messages.up)  : ''}">                   
                    <f:param name="sort" value="${property.name}" />
                    <f:param name="dir" value="${'#'}{${listName}.orderDirection=='asc' ? 'desc' : 'asc'}"/>
                </s:link>
            </f:facet>
            <#if isTimestamp(property) >
            	<h:outputText value="${'#'}{${componentName}.${property.name}}">
            		<s:convertDateTime type="both" dateStyle="short" pattern="${'#'}{messages['constant.dateLongFormat']}"/>
            	</h:outputText>
            <#elseif isDate(property)>
            	<h:outputText value="${'#'}{${componentName}.${property.name}}">
            		<s:convertDateTime type="both" dateStyle="short" pattern="${'#'}{messages['constant.dateFormat']}"/>
            	</h:outputText>
            <#else>
            	${'#'}{${componentName}.${property.name}}
            </#if>	
        </h:column>
</#if>
</#if>
<#if c2h.isManyToOne(property)>
<#assign parentPojo = c2j.getPOJOClass(cfg.getClassMapping(property.value.referencedEntityName))>
<#if parentPojo.isComponent(parentPojo.identifierProperty)>
<#foreach componentProperty in parentPojo.identifierProperty.value.propertyIterator>
        <h:column>
            <f:facet name="header">
<#assign propertyPath = property.name + '.' + parentPojo.identifierProperty.name + '.' + componentProperty.name>
                <s:link styleClass="columnHeader"
                             value="${property.name} ${componentProperty.name} ${'#'}{${listName}.order=='${propertyPath} asc' ? messages.down : ( ${listName}.order=='${propertyPath} desc' ? messages.up : '' )}">
                    <f:param name="order" value="${'#'}{${listName}.order=='${propertyPath} asc' ? '${propertyPath} desc' : '${propertyPath} asc'}"/>
                </s:link>
            </f:facet>
            ${'#'}{${componentName}.${propertyPath}}
        </h:column>
</#foreach>
<#else>
        <h:column>
            <f:facet name="header">
<#assign propertyPath = property.name + '.' + parentPojo.identifierProperty.name>
                <s:link styleClass="columnHeader"
                             value="${property.name} ${parentPojo.identifierProperty.name} ${'#'}{${listName}.order=='${propertyPath} asc' ? messages.down : ( ${listName}.order=='${propertyPath} desc' ? messages.up : '' )}">
                    <f:param name="order" value="${'#'}{${listName}.order=='${propertyPath} asc' ? '${propertyPath} desc' : '${propertyPath} asc'}"/>
                </s:link>
            </f:facet>
            ${'#'}{${componentName}.${propertyPath}}
        </h:column>
</#if>
</#if>
</#foreach>
        <h:column>
            <f:facet name="header">${'#'}{messages['label.header.action']}</f:facet>
            <s:link view="/${module}/${'#'}{empty from ? '${pageName}' : from}.xhtml" 
                   value="${'#'}{messages['button.edit']}" 
                      id="${componentName}">
<#if pojo.isComponent(pojo.identifierProperty)>
<#foreach componentProperty in pojo.identifierProperty.value.propertyIterator>
                <f:param name="${componentName}${util.upper(componentProperty.name)}" 
                        value="${'#'}{${componentName}.${pojo.identifierProperty.name}.${componentProperty.name}}"/>
</#foreach>
<#else>
                <f:param name="${componentName}${util.upper(pojo.identifierProperty.name)}" 
                        value="${'#'}{${componentName}.${pojo.identifierProperty.name}}"/>
</#if>
            </s:link>
        </h:column>
    </rich:dataTable>

    </div>
    </rich:panel>
    </h:form>
    
    
    <s:div styleClass="actionButtons" rendered="${'#'}{empty from}">
        <s:button view="/${module}/${editPageName}.xhtml"
                    id="create" 
                 value="${'#'}{messages['button.create']}${'#'}{messages['${componentName}']}">
<#assign idName = componentName + util.upper(pojo.identifierProperty.name)>
<#if c2j.isComponent(pojo.identifierProperty)>
<#foreach componentProperty in pojo.identifierProperty.value.propertyIterator>
<#assign cidName = componentName + util.upper(componentProperty.name)>
            <f:param name="${cidName}"/>
</#foreach>
<#else>
            <f:param name="${idName}"/>
</#if>
        </s:button>
    </s:div>
    
</ui:define>

</ui:composition>

