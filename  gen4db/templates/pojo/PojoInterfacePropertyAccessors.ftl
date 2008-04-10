<#-- if interface -->
<#-- Property accessors for interface -->
<#foreach property in pojo.getAllPropertiesIterator()><#if pojo.getMetaAttribAsBool(property, "gen-property", true)>   /**
   ${c2j.toJavaDoc(c2j.getMetaAsString(property, "field-description"), 4)} */
   <#assign javaTypeName = util.getObjectType(pojo.getJavaTypeName(property, jdk5))>
   ${pojo.getPropertyGetModifiers(property)} ${javaTypeName} ${pojo.getGetterSignature(property)}();
    
   ${pojo.getPropertySetModifiers(property)} void set${pojo.getPropertyName(property)}(${javaTypeName} ${property.name});
</#if></#foreach>