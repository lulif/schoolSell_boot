$(function() {
	var listUrl = '/schoolSell_boot/shopadmin/listawardsbyshop?pageIndex=1&pageSize=999';
	var modifyUrl = '/schoolSell_boot/shopadmin/modifyaward';

	
	getList();
	
	function getList() {
		$.getJSON(listUrl, function(data) {
			if (data.success) {
				var awardList = data.awardList;
				var tempHtml = '';
				awardList.map(function(item, index) {
					var textOp = "下架";
					var contraryStatus = 0;
					if (item.enableStatus == 0) {
						textOp = "上架";
						contraryStatus = 1;
					} else {
						contraryStatus = 0;
					}
					tempHtml += '' + '<div class="row row-award">'
							+ '<div class="col-40">'
							+ item.awardName
							+ '</div>'
							+ '<div class="col-10">'
							+ item.point
							+ '</div>'
							+ '<div class="col-50">'
							+ '<a href="#" class="edit" data-id="'
							+ item.awardId
							+ '" data-status="'
							+ item.enableStatus
							+ '">编辑</a>'
							+ '<a href="#" class="delete" data-id="'
							+ item.awardId
							+ '" data-status="'
							+ contraryStatus
							+ '">'
							+ textOp
							+ '</a>'
							+ '<a href="#" class="preview" data-id="'
							+ item.awardId
							+ '" data-status="'
							+ item.enableStatus
							+ '">预览</a>'
							+ '</div>'
							+ '</div>';
				});
				$('.award-wrap').html(tempHtml);
			}
		});
	}

	

	function modifyItem(awardId, enableStatus) {
		var award = {};
		award.awardId = awardId;
		award.enableStatus = enableStatus;
		$.confirm('确定么?', function() {
			$.ajax({
				url : modifyUrl,
				type : 'POST',
				data : {
					awardStr : JSON.stringify(award),
					statusChange : true
				},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$.toast('操作成功！');
						getList();
					} else {
						$.toast('操作失败！');
					}
				}
			});
		});
	}

	$('.award-wrap')
			.on(
					'click',
					'a',
					function(e) {
						var target = $(e.currentTarget);
						if (target.hasClass('edit')) {
							window.location.href = '/schoolSell_boot/shopadmin/awardoperate?awardId='
									+ e.currentTarget.dataset.id;
						} else if (target.hasClass('delete')) {
							modifyItem(e.currentTarget.dataset.id,
									e.currentTarget.dataset.status);
						} else if (target.hasClass('preview')) {
							window.location.href = '/schoolSell_boot/frontend/detailaward?awardId='
									+ e.currentTarget.dataset.id
									+'&ispre=1';
						}
					});

	$('#new').click(function() {
		window.location.href = '/schoolSell_boot/shopadmin/awardoperate';
	});
});