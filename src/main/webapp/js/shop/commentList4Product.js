$(function() {
	var loading = false;
	var maxItems = 999;
	var pageSize = 5;
	var listUrl = '/schoolSell_boot/frontend/listcustomercomment';
	var pageIndex = 1;
	var productName = '';

	addItems();

	function addItems() {
		// 生成新条目的HTML
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
				+ pageSize + '&isback=1' + '&productName=' + productName;
		loading = true;
		$.getJSON(url, function(data) {
			if (data.success) {
				maxItems = data.count;
				var html = '';
				data.customerCommentList.map(function(item, index) {
					html += '' + '<div class="card" >'
							+ '<div class="card-header">'
							+ item.product.productName
							+ '👈'
							+'<span class="pull-right" style="color:orange ;font-size:20px; font-weight:600" >评价顾客：'
							+item.customer.userName
							+'</span>'
							+ '</div>'
							+ '<div class="card-content">'
							+ '<div class="list-block media-list">'
							+ '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-inner">'
							+ '<div class="item-subtitle">评价内容：'
							+ item.commentContent
							+ '</div>'
							+ '</div>'
							+ '</li>'
							+ '</ul>'
							+ '</div>'
							+ '</div>'
							+ '<div class="card-footer">'
							+ '<p style="color:green">评价分数：'
							+ item.commentPoint
							+ '</p>'
							+ '<span class="pull-right color-gray" >评价时间:'
							+ new Date(item.createTime)
									.Format("yyyy-MM-dd hh:mm:ss")
							+ '</div>'
							+ '</div>';
				});
				$('.list-div').append(html);
				var total = $('.list-div .card').length;
				if (total >= maxItems) {
					$('.infinite-scroll-preloader').hide();
				} else {
					$('.infinite-scroll-preloader').show();
				}
				pageIndex += 1;
				loading = false;
				$.refreshScroller();
			}
		});
	}

	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading)
			return;
		addItems();
	});

	$('#search').on('change', function(e) {
		productName = e.target.value;
		$('.list-div').empty();
		pageIndex = 1;
		addItems();
	});

	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});

	$.init();
});
