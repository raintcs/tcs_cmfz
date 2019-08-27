<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; utf-8" %>
<script>
    $(function () {
        $("#bannerList").jqGrid({
            url: "${pageContext.request.contextPath}/banner/getAll",
            datatype: "json",
            colNames: ["id", "标题", "状态", "描述", "创建日期", "图片"],
            colModel: [
                {name: "id"},
                //用来控制当前单元格是否可以点击编辑 必须配合初始化属性cellEdit:true使用
                {name: "title", editable: true},
                {name: "status", editable: true, edittype: "select", editoptions: {value: "展示:展示;不展示:不展示"}},
                {name: "describe", editable: true},
                {name: "create_date"},
                {
                    name: "image",
                    editable: true, edittype: "file",
                    formatter: function (cellvalues, options, rowObject) {
                        // console.log(cellvalues);
                        // console.log(options);
                        // console.log(rowObject);
                        return "<img style='height:90px;width:120px' src='${pageContext.request.contextPath}/banner/" + cellvalues + "'/>"
                    }
                }
            ],
            styleUI: "Bootstrap",
            autowidth: true,
            //cellEdit:true,
            height: "50%",
            pager: "#bannerPager",
            viewrecords: true,
            page: "1",
            rowNum: "2",
            rowList: [2, 4, 6],
            //用来控制是否显示checkbox
            multiselect: true,
            //用来指定编辑(增删改)时的url
            editurl: "${pageContext.request.contextPath}/banner/edit",
        }).jqGrid("navGrid", "#bannerPager",
            {
                //这个模块修改增删查按钮的状态等信息
                search: false
            },
            { //执行修改时进行的操作
                closeAfterEdit: true,//编辑完之后自动关闭窗口
                beforeShowForm: function (obj) {
                    obj.find("#title").attr("readonly", true);
                    obj.find("#describe").attr("readonly", true);
                    obj.find("#image").attr("disabled", true);
                }
            },
            {//执行添加时进行额外操作
                closeAfterAdd: true,
                afterSubmit: function (response) {
                    var bannerId = response.responseJSON.id;
                    // console.log(bannerId);
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/banner/upload",
                        fileElementId: "image",
                        //向后台传递的参数
                        data: {bannerId: bannerId},
                        success: function () {
                            $("#bannerList").trigger("reloadGrid");
                        }
                    })
                    return response;
                }
            },
            {//删除时。。。。

            }
        )
    })
</script>

<div class="page-header">
    <h3>轮播图管理</h3>
</div>
<div>
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab"
                                                  data-toggle="tab">轮播图信息</a></li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="home">
            <table id="bannerList" style="text-align: center"></table>
            <div style="height: 60px" id="bannerPager"></div>
        </div>
    </div>
</div>

