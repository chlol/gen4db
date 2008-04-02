<#include "../../util/TypeInfo.ftl">

<#assign entityName = pojo.shortName>
<#assign componentName = util.lower(entityName)>
<#if !property.equals(pojo.identifierProperty) || property.value.identifierGeneratorStrategy == "assigned">
<#if c2j.isComponent(property)>
<#foreach componentProperty in property.value.propertyIterator>

        <s:decorate id="${componentProperty.name}" template="/layout/display.xhtml">
            <ui:define name="label">${'#'}{messages['${componentName}.${componentProperty.name}']}</ui:define>
<#if isDate(componentProperty)>
            <h:outputText value="${'#'}{${homeName}.instance.${property.name}.${componentProperty.name}}">
                <s:convertDateTime type="date" dateStyle="short" pattern="${'#'}{messages['constant.dateFormat']}"/>
            </h:outputText>
<#elseif isTime(componentProperty)>
            <h:outputText value="${'#'}{${homeName}.instance.${property.name}.${componentProperty.name}}">
                <s:convertDateTime type="time"/>
            </h:outputText>
<#elseif isTimestamp(componentProperty)>
            <h:outputText value="${'#'}{${homeName}.instance.${property.name}.${componentProperty.name}}">
                <s:convertDateTime type="both" dateStyle="short" pattern="${'#'}{messages['constant.dateLongFormat']}"/>
            </h:outputText>
<#elseif isBigDecimal(componentProperty)>
            <h:outputText value="${'#'}{${homeName}.instance.${property.name}.${componentProperty.name}}">
                <f:convertNumber/>
            </h:outputText>
<#elseif isBigInteger(componentProperty)>
            <h:outputText value="${'#'}{${homeName}.instance.${property.name}.${componentProperty.name}}">
                <f:convertNumber integerOnly="true"/>
            </h:outputText>
<#else>
            ${'#'}{${homeName}.instance.${property.name}.${componentProperty.name}}
</#if>
        </s:decorate>
</#foreach>
<#else>
        <s:decorate id="${property.name}" template="/layout/display.xhtml">
            <ui:define name="label">${'#'}{messages['${componentName}.${property.name}']}</ui:define>
<#if isDate(property)>
            <h:outputText value="${'#'}{${homeName}.instance.${property.name}}">
                <s:convertDateTime type="date" dateStyle="short" pattern="${'#'}{messages['constant.dateFormat']}"/>
            </h:outputText>
<#elseif isTime(property)>
            <h:outputText value="${'#'}{${homeName}.instance.${property.name}}">
                <s:convertDateTime type="time"/>
            </h:outputText>
<#elseif isTimestamp(property)>
            <h:outputText value="${'#'}{${homeName}.instance.${property.name}}">
                <s:convertDateTime type="both" dateStyle="short" pattern="${'#'}{messages['constant.dateLongFormat']}"/>
            </h:outputText>
<#elseif isBigDecimal(property)>
            <h:outputText value="${'#'}{${homeName}.instance.${property.name}}">
                <f:convertNumber/>
            </h:outputText>
<#elseif isBigInteger(property)>
            <h:outputText value="${'#'}{${homeName}.instance.${property.name}}">
                <f:convertNumber integerOnly="true"/>
            </h:outputText>
<#else>
            ${'#'}{${homeName}.instance.${property.name}}
</#if>
        </s:decorate>
</#if>
</#if>
