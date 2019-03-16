$(function() {
	var loading = false;
	var maxItems = 999;
	// 一页几项
	var pageSize = 5;
	// 第几页
	var pageIndex = 1;
	var canProceed = false;
	var listAwardUrl = '/schoolSell_boot/frontend/listawardsbyshop';
	var exchangeUrl = '/schoolSell_boot/frontend/adduserawardmap';
	var shopId = getQueryString('shopid');
	var awardName = '';
	addItems();

	function addItems() {
		// 生成新条目的HTML
		var url = listAwardUrl + '?shopId=' + shopId + '&pageIndex='
				+ pageIndex + '&pageSize=' + pageSize + '&awardName='
				+ awardName;
		loading = true;
		$.getJSON(url, function(data) {
			if (data.success) {
				maxItems = data.count;
				var html = '';
				data.awardList.map(function(item, index) {
					html += '' + '<div class="card" data-award-id="'
							+ item.awardId + '">' + '<div class="card-header">'
							+ item.awardName + '<span class="pull-right">所需积分:'
							+ item.point + '</span></div>'
							+ '<div class="card-content">'
							+ '<div class="list-block media-list">' + '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-media">' + '<img src="'
							+ getContextPath() + item.awardImg
							+ '" width="50">' + '</div>'
							+ '<div class="item-inner">'
							+ '<div class="item-subtitle">' + item.awardDesc
							+ '</div>' + '</div>' + '</li>' + '</ul>'
							+ '</div>' + '</div>' + '<div class="card-footer">'
							+ '<p class="color-gray">兑换至'
							+ new Date(item.expireTime).Format("yyyy-MM-dd")
							+ '失效</p>';
					if (data.totalPoint != undefined) {
						html += '<a class="exchange" ' + 'data-award-id="'
								+ item.awardId + '" data-point="' + item.point
								+ '">点击兑换</a> </div></div>'
					} else {
						html += '</div></div>';
					}
				});
				$('.list-div').append(html);
				if (data.totalPoint != undefined) {
					canProceed = true;
					$('.title').text('当前积分：' + data.totalPoint);
					totalPoint = data.totalPoint;
				}
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
	$('.award-list')
			.on(
					'click',
					'.exchange',
					function(e) {
						if (canProceed
								&& (totalPoint >= e.currentTarget.dataset.point)) {
							$.confirm('需要消耗' + e.currentTarget.dataset.point
									+ '积分，确定要操作吗', function() {
								var url = exchangeUrl + "?awardId="
										+ e.currentTarget.dataset.awardId;
								$.getJSON(url, function(data) {
									if (data.success) {
										$.toast("兑换成功");
										setTimeout(function() {
											window.location.reload()
										}, 1000);
									}
								})
							})

						} else {
							$.toast("您的积分不足，兑换失败")
						}
					})
	$('.award-list')
			.on(
					'click',
					'.card',
					function() {
						window.location.href = '/schoolSell_boot/frontend/detailaward?awardId='
								+ this.dataset.awardId + '&ispre=1';
					})

	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading) {
			return;
		}
		addItems();
	});

	$('#search').on('change', function(e) {
		awardName = e.target.value;
		$('.list-div').empty();
		pageIndex = 1;
		addItems();
	});

	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
	$.init();
});
