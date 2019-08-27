<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="path"></c:set>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>goeasy</title>
    <script src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>
</head>
<body>
<script>
    var goEasy = new GoEasy({
        appkey: "BS-4c66f7037cd8435da704698da0f2f81f"
    });
    goEasy.subscribe({
        channel: "my_channel",
        onMessage: function (message) {
            alert("Channel:" + message.channel + " content:" + message.content);
        }
    });
</script>

</body>
</html>