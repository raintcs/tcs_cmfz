<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="path"></c:set>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Echarts</title>
    <link rel="icon" href="${path}/img/favicon.ico">
    <script src="${path}/echarts/js//echarts.js"></script>
    <script src="${path}/boot/js/jquery-2.2.1.min.js"></script>
    <script src="${path}/echarts/js/china.js"></script>
    <script src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>
    <script>
        $(function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));
            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '用户分布图',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ['用户']
                },
                visualMap: {
                    min: 0,
                    max: 10,
                    left: 'left',
                    top: 'bottom',
                    text: ['高', '低'],           // 文本，默认为数值文本
                    calculable: true
                },
                toolbox: {
                    show: true,
                    orient: 'vertical',
                    left: 'right',
                    top: 'center',
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                series: [
                    {
                        name: '用户所在省',
                        type: 'map',
                        mapType: 'china',
                        roam: false,
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        }
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
            $.ajax({
                url: "${pageContext.request.contextPath}/user/testMap",
                datatype: "json",
                type: "post",
                success: function (result) {
                    console.log(result);
                    myChart.setOption({
                        series: [{
                            data: result
                        }]
                    });
                }
            })

            /*=====================================================================================*/
            var goEasy = new GoEasy({
                appkey: "BS-4c66f7037cd8435da704698da0f2f81f"
            });
            goEasy.subscribe({
                channel: "my_channel",
                onMessage: function (message) {
                    console.log(message);
                    var result = JSON.parse(message.content);
                    myChart.setOption({
                        series: [{
                            data: result
                        }]
                    });
                }
            });
            /*====================================================================================*/
        })

    </script>

</head>
<body>
<body>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 500px;height:400px;"></div>
</body>
</body>
</html>