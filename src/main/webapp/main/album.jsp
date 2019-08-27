<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="path"></c:set>
<script>
    $(function () {
        $("#albumList").jqGrid({
            url: "${pageContext.request.contextPath}/album/queryByPage",
            datatype: "json",
            colNames: ["id", "标题", "分数", "作者", "播音员", "章节数", "专辑简介", "状态", "发行时间", "上传时间", "封面"],
            colModel: [
                {name: "id"},
                {name: "title", editable: true},
                {name: "score", editable: true},
                {name: "author", editable: true},
                {name: "announcer", editable: true},
                {name: "count", editable: true},
                {name: "brief", editable: true},
                {name: "status", editable: true, edittype: "select", editoptions: {value: "激活:激活;未激活:未激活"}},
                {name: "publish_date"},
                {name: "upload_date"},
                {
                    name: "cover",
                    editable: true, edittype: "file",
                    formatter: function (cellvalues, options, rowObject) {
                        return "<img style='height:60px;width:90px' src='${pageContext.request.contextPath}/album/" + cellvalues + "'/>"
                    }
                }
            ],
            styleUI: "Bootstrap",
            autowidth: true,
            height: "50%",
            pager: "#albumPager",
            viewrecords: true,
            page: "1",
            rowNum: "2",
            rowList: [2, 4, 6],
            //用来控制是否显示checkbox
            multiselect: true,
            //用来指定编辑(增删改)时的url
            editurl: "${pageContext.request.contextPath}/album/edit",
            subGrid: true,
            subGridRowExpanded: function (subgrid_id, row_id) {
                // console.log(subgrid_id);
                // console.log(row_id);
                addSubGrid(subgrid_id, row_id);
            },
        }).jqGrid("navGrid", "#albumPager",
            {
                //这个模块修改增删查按钮的状态等信息
                search: false
            },
            { //执行修改时进行的操作
                closeAfterEdit: true,//编辑完之后自动关闭窗口
                beforeShowForm: function (obj) {
                    obj.find("#title").attr("readonly", true);
                    obj.find("#author").attr("readonly", true);
                    obj.find("#announcer").attr("readonly", true);
                    obj.find("#cover").attr("disabled", true);
                }
            },
            {//执行添加时进行额外操作
                closeAfterAdd: true,
                afterSubmit: function (response) {
                    var albumId = response.responseJSON.id;
                    // console.log(albumId);
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/album/upload",
                        fileElementId: "cover",
                        //向后台传递的参数
                        data: {albumId: albumId},
                        success: function () {
                            $("#albumList").trigger("reloadGrid");
                        }
                    })
                    return response;
                }

            },
            {//删除时。。。。

            }
        )
    })

    //创建专辑里面的章节子表格
    function addSubGrid(subgrid_id, row_id) {
        //创建专辑里面的章节子表格
        var tableId = subgrid_id + "table";
        var pagerId = subgrid_id + "div";
        //动态添加标签
        $("#" + subgrid_id).html(
            "<table id='" + tableId + "' style='text-align: center'></table>" +
            "<div id='" + pagerId + "'></div>"
        );

        $("#" + tableId).jqGrid({
            url: "${pageContext.request.contextPath}/chapter/findByPage?id=" + $("#albumList").getCell(row_id, 'id'),
            datatype: "json",
            colNames: ["id", "标题", "大小", "时长", "上传日期", "音频", "状态", "操作"],
            colModel: [
                {name: "id"},
                {name: "title", editable: true},
                {name: "size"},
                {name: "duration"},
                {name: "upload_date"},
                {name: "video", editable: true, edittype: "file"},
                {
                    name: "status", editable: true,
                    edittype: "select",
                    editoptions: {value: "可播放:可播放;不可播放:不可播放"},
                },
                {
                    name: "video",
                    formatter: function (cellvalues, options, rowObject) {
                        // console.log(cellvalues);
                        //console.log(options);
                        console.log(rowObject);
                        if (rowObject.status == "可播放") {
                            return "&nbsp;&nbsp;&nbsp;&nbsp;<a onclick=\"playAudio('" + cellvalues + "')\" href='#'><span class='glyphicon glyphicon-play'></span></a>" + "      " +
                                "<a onclick=\"downloadAudio('" + cellvalues + "')\" href='#'><span class='glyphicon glyphicon-circle-arrow-down'></span></a>"
                        } else {
                            return "&nbsp;&nbsp;&nbsp;&nbsp;<a  href='#'><span class='glyphicon glyphicon-stop'></span></a>" + "      " +
                                "<a href='#'><span class='glyphicon glyphicon-ban-circle'></span></a>";
                        }
                    }
                }
            ],
            styleUI: "Bootstrap",
            editurl: "${path}/chapter/edit?chapterId=" + row_id,
            autowidth: true,
            height: "60%",
            pager: "#" + pagerId,
            viewrecords: true,
            page: "1",
            rowNum: "2",
            rowList: [2, 4, 6],
            multiselect: true
        }).jqGrid("navGrid", "#" + pagerId,
            {
                //这个模块修改增删查按钮的状态等信息
                search: false,
                del: false,
                edit: false
            },
            { //执行修改时进行的操作

            },
            {//执行添加时进行额外操作
                closeAfterAdd: true,
                afterSubmit: function (response) {
                    var chapterId = response.responseText;
                    $.ajaxFileUpload({
                        url: "${path}/chapter/upload",
                        fileElementId: "video",
                        data: {chapterId: chapterId, albumId: $("#albumList").getCell(row_id, 'id'),},
                        success: function () {
                            $("#" + tableId).trigger("reloadGrid");
                        }
                    })
                    return response;
                }
            },
            {//删除时。。。。

            }
        )
    }

    /*音频播放*/
    function playAudio(cellvalues) {
        $("#audioModel").modal("show");
        $("#audioId").attr("src", "${path}/audio/" + cellvalues);
    }

    /*音频下载*/
    function downloadAudio(cellvalues) {
        location.href = "${path}/chapter/download?audioName=" + cellvalues;
    }


</script>

<div id="audioModel" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document" id="banner">
        <audio id="audioId" src="" controls></audio>
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->


<div class="page-header">
    <h3>专辑与章节管理</h3>
</div>
<div>
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab"
                                                  data-toggle="tab">专辑与章节信息</a></li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="home">
            <table id="albumList" class="table table-hover table-condensed" style="text-align: center"></table>
            <div style="height: 50px" id="albumPager"></div>
        </div>
    </div>
</div>
