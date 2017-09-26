<div class="modal-dialog modal-lg">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
            <h4 class="modal-title">图片空间</h4>
        </div>
        <div class="modal-body">

            <div class="cs-picture-space">
                <div class="cs-space-tool">
                    <div class="pull-right">
                        <button id="selectfiles" class="btn btn-info" type="button"><i class="icon icon-cloud-upload"></i>上传图片</button>
                    </div>
                </div>
                <div class="cs-space-body">
                    <div class="cs-space-left col-md-2">
					<#list folderList as folder>
						<#if folder_index==0>
                            <a href="#" data-id="${folder.albumId?c}" class="list-navbar active">${folder.albumName}<span class="label label-badge pull-right">${folder.picNum}</span></a>
						<#else>
                            <a href="#" data-id="${folder.albumId?c}" class="list-navbar">${folder.albumName}<span class="label label-badge pull-right">${folder.picNum}</span></a>
						</#if>
					</#list>
                    </div>
                    <div class="cards col-md-10">

                    </div>
                    <div class="clearfix"></div>
                    <div id="page" class="cs-space-footer"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary choosePicture">确认</button>
    </div>
</div>
<script type="text/javascript" src="/js/page/pictureSpace.js"></script>