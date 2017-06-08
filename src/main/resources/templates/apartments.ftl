<html>
<body>
<table>
    <tr>
        <th>URL</th>
        <th>Description</th>
        <th>Address</th>
        <th>Area</th>
        <th>Price</th>
        <th>Rooms</th>
        <th>Price per square meter</th>
        <th>ExternalId</th>
    </tr>
    <#list apartments as apartment>
    <tr>
        <td><a href="${apartment.url!}">${apartment.url!}</a></td>
        <td>${apartment.description!}</td>
        <td>${apartment.address!}</td>
        <td>${apartment.area!}</td>
        <td>${apartment.price!}</td>
        <td>${apartment.noOfRooms!}</td>
        <td>${apartment.price / apartment.area}</td>
        <td>${apartment.externalId!}</td>
    </tr>
    </#list>
</table>
</body>
</html>