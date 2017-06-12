<html>
<body>
<table style="border: 2px solid black; border-collapse: collapse;">
    <tr>
        <th style="border-bottom: 1px solid black; width: 200px;">URL</th>
        <th style="border-bottom: 1px solid black; width: 200px;">Description</th>
        <th style="border-bottom: 1px solid black;">Area</th>
        <th style="border-bottom: 1px solid black;">Price per square meter</th>
    </tr>
<#list apartments as apartment>
    <tr>
        <td style="border-bottom: 1px solid black; width: 200px;"><a href="${apartment.url!}">${apartment.url!}</a></td>
        <td style="border-bottom: 1px solid black; width: 200px;">${apartment.description!}</td>
        <td style="border-bottom: 1px solid black;">${apartment.area!}</td>
        <td style="border-bottom: 1px solid black;">${apartment.price / apartment.area}</td>
    </tr>
</#list>
</table>
</body>
</html>