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
    </tr>
    <#list appartments as appartment>
    <tr>
        <td><a href="${appartment.url}">${appartment.url}</a></td>
        <td>${appartment.description}</td>
        <td>${appartment.address}</td>
        <td>${appartment.area}</td>
        <td>${appartment.price}</td>
        <td>${appartment.noOfRooms}</td>
        <td>${appartment.price / appartment.area}</td>
    </tr>
    </#list>
</table>
</body>
</html>