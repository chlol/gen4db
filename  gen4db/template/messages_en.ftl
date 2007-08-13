<#foreach entity in c2j.getPOJOIterator(cfg.classMappings)>

########## for ${entity.shortName} ##########
<#assign lowerShortName = util.lower(entity.shortName)>
<#foreach field in entity.getAllPropertiesIterator()>
<#assign upperFieldName = util.upper(field.name)>
${lowerShortName}.${field.name}=${upperFieldName}
</#foreach>
</#foreach> 