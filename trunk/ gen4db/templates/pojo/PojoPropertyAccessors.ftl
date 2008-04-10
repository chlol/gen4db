<#-- // Property accessors -->
<#foreach property in pojo.getAllPropertiesIterator()>
<#if pojo.getMetaAttribAsBool(property, "gen-property", true)>
 <#if pojo.hasFieldJavaDoc(property)>    
    /**       
     * ${pojo.getFieldJavaDoc(property, 4)}
     */
</#if>
    <#include "GetPropertyAnnotation.ftl"/>
    <#assign javaTypeName = util.getObjectType(pojo.getJavaTypeName(property, jdk5))>
    ${pojo.getPropertyGetModifiers(property)} ${javaTypeName} ${pojo.getGetterSignature(property)}() {
        return this.${property.name};
    }
    
    ${pojo.getPropertySetModifiers(property)} void set${pojo.getPropertyName(property)}(${javaTypeName} ${property.name}) {
        this.${property.name} = ${property.name};
    }
</#if>
</#foreach>
