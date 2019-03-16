$(function() {
	var loading = false;
	var maxItems = 20;
	var pageSize = 10;

	var listUrl = '/schoolSell_boot/frontend/listuserproductmapsbycustomer';

	var pageIndex = 1;
	var productName = '';
	var commentUrl = '/schoolSell_boot/frontend/gotocomment?productName='

	addItems();

	function addItems() {
		// 生成新条目的HTML
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
				+ pageSize + '&productName=' + productName;
		loading = true;
		$
				.getJSON(
						url,
						function(data) {
							if (data.success) {
								maxItems = data.count;
								var html = '';
								data.userProductMapList
										.map(function(item, index) {
											html += ''
													+ '<div class="card" >'
													+ '<div class="card-header">'
													+ item.shop.shopName;
											if (item.isComment == 0) {
												html += '<a class="pull-right" id="comment" style="font-size:15px;font-weight:600" '
														+ ' href="'
														+ commentUrl
														+ item.product.productName
														+ '&shopName='
														+ item.shop.shopName
														+ '&shopId='
														+ item.shop.shopId
														+ '&productId='
														+ item.product.productId
														+ '&upmId='
														+ item.userProductId
														+ ' " >' + '评价/建议</a>';
											} else {
												html += '<span class="pull-right"  style="font-size:15px;color:gray;font-weight:600">已评价</span> '
											}

											html += '</div>'
													+ '<div class="card-content">'
													+ '<div class="list-block media-list">'
													+ '<ul>'
													+ '<li class="item-content">'
													+ '<div class="item-inner">'
													+ '<div class="item-subtitle">'
													+ item.product.productName
													+ '</div>'
													+ '</div>'
													+ '</li>'
													+ '</ul>'
													+ '</div>'
													+ '</div>'
													+ '<div class="card-footer">'
													+ '<p class="color-gray">消费时间:'
													+ new Date(item.createTime)
															.Format("yyyy-MM-dd hh:mm:ss")
													+ '</p>' + '<span>获取积分：'
													+ item.point + '</span>'
													+ '</div>' + '</div>';
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

	$('#search').on('input', function(e) {
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
