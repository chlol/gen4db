<#assign entityName = pojo.shortName>
<#assign componentName = util.lower(entityName)>
<#if !c2h.isCollection(property) && !c2h.isManyToOne(property)>
<#assign propertyIsId = property.equals(pojo.identifierProperty)>
<#if !propertyIsId>
<#if pojo.isComponent(property)>
<#foreach componentProperty in property.value.propertyIterator>
<#assign column = componentProperty.columnIterator.next()>
<#assign propertyType = componentProperty.value.typeName>

            <s:decorate id="${componentProperty.name}Decoration" template="/layout/edit.xhtml">
                <ui:define name="label">${'#'}{messages['${componentName}.${componentProperty.name}']}</ui:define>
<#if propertyType == "date">
				<rich:calendar id=${componentProperty.name}"  styleClass="calendar"  direction="auto" zindex="3000"
<#if propertyIsId>
                       disabled="${'#'}{${homeName}.managed}"
</#if>
<#if !column.nullable>
                       required="true"
</#if>
                       value="${'#'}{${homeName}.instance.${property.name}.${componentProperty.name}}" 
                       datePattern="${'#'}{messages['constant.dateFormat']}" 
                       event="onblur"  
                       bypassUpdates="true"/>
<#elseif propertyType == "time">
                <h:inputText id="${componentProperty.name}" 
                           size="5"
<#if !column.nullable>
                       required="true"
</#if>
                          value="${'#'}{${homeName}.instance.${property.name}.${componentProperty.name}}">
                    <s:convertDateTime type="time"/>
                    <a4j:support event="onblur" reRender="${componentProperty.name}Decoration" bypassUpdates="true"/>
                </h:inputText>
<#elseif propertyType == "timestamp">
				<rich:calendar id=${componentProperty.name}"  styleClass="calendar"  direction="auto" zindex="3000"
<#if propertyIsId>
                       disabled="${'#'}{${homeName}.managed}"
</#if>
<#if !column.nullable>
                       required="true"
</#if>
                       value="${'#'}{${homeName}.instance.${property.name}.${componentProperty.name}}" 
                       datePattern="${'#'}{messages['constant.dateFormat']}" 
                       event="onblur"
                       bypassUpdates="true"/>

<#elseif propertyType == "big_decimal">
                <h:inputText id="${componentProperty.name}" 
<#if !column.nullable>
                       required="true"
</#if>
                          value="${'#'}{${homeName}.instance.${property.name}.${componentProperty.name}}"
                           size="${column.precision+7}">
                    <a4j:support event="onblur" reRender="${componentProperty.name}Decoration" bypassUpdates="true"/>
                </h:inputText>
<#elseif propertyType == "big_integer">
                <h:inputText id="${componentProperty.name}" 
<#if propertyIsId>
                       disabled="${'#'}{${homeName}.managed}"
</#if>
<#if !column.nullable>
                       required="true"
</#if>
                          value="${'#'}{${homeName}.instance.${property.name}.${componentProperty.name}}"
                           size="${column.precision+6}">
                    <a4j:support event="onblur" reRender="${componentProperty.name}Decoration" bypassUpdates="true"/>
                </h:inputText>
<#elseif propertyType == "boolean" || propertyType == "yes_no" || propertyType == "true_false">
                 <h:selectBooleanCheckbox id="${componentProperty.name}"
<#if !column.nullable>
                                    required="true"
</#if>
<#if propertyIsId>
                                    disabled="${'#'}{${homeName}.managed}"
</#if>
                                       value="${'#'}{${homeName}.instance.${property.name}.${componentProperty.name}}"/>
<#elseif propertyType == "string">
<#if column.length gt 160>
<#if column.length gt 800>
<#assign rows = 10>
<#else>
<#assign rows = (column.length/80)?int>
</#if>
                <h:inputTextarea id="${componentProperty.name}"
                               cols="80"
                               rows="${rows}"
<#if propertyIsId>
                           disabled="${'#'}{${homeName}.managed}"
</#if>
<#if !column.nullable>
                           required="true"
</#if>
                              value="${'#'}{${homeName}.instance.${property.name}.${componentProperty.name}}"/>
<#else>
<#if column.length gt 100>
<#assign size = 100>
<#else>
<#assign size = column.length>
</#if>
                <h:inputText id="${componentProperty.name}" 
<#if propertyIsId>
                       disabled="${'#'}{${homeName}.managed}"
</#if>
<#if !column.nullable>
                      required="true"
</#if>
                          size="${size}"
                     maxlength="${column.length}"
                         value="${'#'}{${homeName}.instance.${property.name}.${componentProperty.name}}">
                    <a4j:support event="onblur" reRender="${componentProperty.name}Decoration" bypassUpdates="true"/>
                </h:inputText>
</#if>
<#else>
                <h:inputText id="${componentProperty.name}"
<#if !column.nullable>
                       required="true"
</#if>
<#if propertyIsId>
                       disabled="${'#'}{${homeName}.managed}"
</#if>
                          value="${'#'}{${homeName}.instance.${property.name}.${componentProperty.name}}">
                    <a4j:support event="onblur" reRender="${componentProperty.name}Decoration" bypassUpdates="true"/>
                </h:inputText>
</#if>
            </s:decorate>
</#foreach>
<#else>
<#assign column = property.columnIterator.next()>
<#assign propertyType = property.value.typeName>

            <s:decorate id="${property.name}Decoration" template="/layout/edit.xhtml">
                <ui:define name="label">${'#'}{messages['${componentName}.${property.name}']}</ui:define>
<#if propertyType == "date">
				<rich:calendar id="${property.name}"  styleClass="calendar"  direction="auto" zindex="3000"
<#if propertyIsId>
                       disabled="${'#'}{${homeName}.managed}"
</#if>
<#if !column.nullable>
                       required="true"
</#if>
                       value="${'#'}{${homeName}.instance.${property.name}}" 
                       datePattern="${'#'}{messages['constant.dateFormat']}" 
                       event="onblur"  
                       bypassUpdates="true"/>
<#elseif propertyType == "time">
                <h:inputText id="${property.name}" 
                           size="5"
<#if !column.nullable>
                       required="true"
</#if>
                          value="${'#'}{${homeName}.instance.${property.name}}">
                    <s:convertDateTime type="time"/>
                    <a4j:support event="onblur" reRender="${property.name}Decoration" bypassUpdates="true"/>
                </h:inputText>
<#elseif propertyType == "timestamp">
				<rich:calendar id="${property.name}"  styleClass="calendar"  direction="auto" zindex="3000"
<#if propertyIsId>
                       disabled="${'#'}{${homeName}.managed}"
</#if>
<#if !column.nullable>
                       required="true"
</#if>
                       value="${'#'}{${homeName}.instance.${property.name}}" 
                       datePattern="${'#'}{messages['constant.dateFormat']}" 
                       event="onblur"  
                       bypassUpdates="true"/>
<#elseif propertyType == "big_decimal">
                <h:inputText id="${property.name}" 
<#if !column.nullable>
                       required="true"
</#if>
                          value="${'#'}{${homeName}.instance.${property.name}}"
                           size="${column.precision+7}">
                    <a4j:support event="onblur" reRender="${property.name}Decoration" bypassUpdates="true"/>
                </h:inputText>
<#elseif propertyType == "big_integer">
                <h:inputText id="${property.name}" 
<#if propertyIsId>
                       disabled="${'#'}{${homeName}.managed}"
</#if>
<#if !column.nullable>
                       required="true"
</#if>
                          value="${'#'}{${homeName}.instance.${property.name}}"
                           size="${column.precision+6}">
                    <a4j:support event="onblur" reRender="${property.name}Decoration" bypassUpdates="true"/>
                </h:inputText>
<#elseif propertyType == "boolean" || propertyType == "yes_no" || propertyType == "true_false">
                <h:selectBooleanCheckbox id="${property.name}"
<#if !column.nullable>
                                   required="true"
</#if>
<#if propertyIsId>
                                   disabled="${'#'}{${homeName}.managed}"
</#if>
                                      value="${'#'}{${homeName}.instance.${property.name}}"/>
<#elseif propertyType == "string">
<#if column.length gt 160>
<#if column.length gt 800>
<#assign rows = 10>
<#else>
<#assign rows = (column.length/80)?int>
</#if>
                <h:inputTextarea id="${property.name}"
                               cols="80"
                               rows="${rows}"
<#if propertyIsId>
                           disabled="${'#'}{${homeName}.managed}"
</#if>
<#if !column.nullable>
                           required="true"
</#if>
                              value="${'#'}{${homeName}.instance.${property.name}}"/>
<#else>
<#if column.length gt 100>
<#assign size = 100>
<#else>
<#assign size = column.length>
</#if>
                <h:inputText id="${property.name}" 
<#if propertyIsId>
                       disabled="${'#'}{${homeName}.managed}"
</#if>
<#if !column.nullable>
                       required="true"
</#if>
                           size="${size}"
                      maxlength="${column.length}"
                          value="${'#'}{${homeName}.instance.${property.name}}">
                    <a4j:support event="onblur" reRender="${property.name}Decoration" bypassUpdates="true"/>
                </h:inputText>
</#if>
<#else>
                <h:inputText id="${property.name}"
<#if !column.nullable>
                       required="true"
</#if>
<#if propertyIsId>
                       disabled="${'#'}{${homeName}.managed}"
</#if>
                          value="${'#'}{${homeName}.instance.${property.name}}">
                    <a4j:support event="onblur" reRender="${property.name}Decoration" bypassUpdates="true"/>
                </h:inputText>
</#if>
            </s:decorate>
</#if>
</#if>
</#if>
