<#if (v>0)>
    ${v}
</#if>
${a!"变量a没有值"}
<#list items[0..2] as item>
    ${item?cap_first}-${items?size}
</#list>

<#assign seq1 = 0..10>
<#list seq1 as num>
    ${num}
</#list>

<#function func p1>
    <#return p1>
</#function>

${func("aaa")}