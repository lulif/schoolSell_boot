$(function() {
	var loading = false;
	var maxItems = 20;
	var pageSize = 10;

	var listUrl = '/schoolSell_boot/frontend/listuserawardmapsbyshop';

	var pageIndex = 1;
	var awardName = '';
	var nowDate = new Date();
	addItems();
	//
	function addItems() {
		// 生成新条目的HTML
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
				+ pageSize + '&awardName=' + awardName;
		loading = true;
		$
				.getJSON(
						url,
						function(data) {
							if (data.success) {
								maxItems = data.count;
								var html = '';
								data.userAwardMapList
										.map(function(item, index) {
											html += ''
													+ '<div class="card" data-award-id="'
													+ item.award.awardId
													+ '" data-used-status="'
													+ item.usedStatus
													+ '" data-user-award-id="'
													+ item.userAwardId
													+ '">'
													+ '<div class="card-header">'
													+ item.shop.shopName

											if (item.usedStatus != 1) {
												if (item.award.expireTime < nowDate
														.getTime()) {
													html += '<span class="pull-right" style="color:gray; font-size:15px;font-weight:800">已失效 </span>'
												} else {
													html += '<span class="pull-right" style="color:green; font-size:15px;font-weight:800">未领取 </span>'
												}
											} else {
												html += '<span class="pull-right" style="color:red; font-size:15px;font-weight:800">已领取 </span>'
											}

											html += '</div>'
													+ '<div class="card-content">'
													+ '<div class="list-block media-list">'
													+ '<ul>'
													+ '<li class="item-content">'
													+ '<div class="item-inner">'
													+ '<div class="item-subtitle">'
													+ item.award.awardName
													+ '</div>'
													+ '</div>'
													+ '</li>'
													+ '</ul>'
													+ '</div>'
													+ '</div>'
													+ '<div class="card-footer">'
													+ '<p class="color-gray">';
											if (item.usedStatus != 1) {
												html += '兑换时间:'
											} else {
												html += '领取时间:'
											}
											html += new Date(item.createTime)
													.Format("yyyy-MM-dd hh:mm:ss")
													+ '</p>'
													+ '<span>已消耗积分:'
													+ item.point
													+ '</span>'
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
	// 点击进入奖品详情页，扫二维码领取
	$('.list-div')
			.on(
					'click',
					'.card',
					function(e) {
						var awardId = e.currentTarget.dataset.awardId;
						var usedStatus = e.currentTarget.dataset.usedStatus;
						var userAwardId = e.currentTarget.dataset.userAwardId;
						if (usedStatus == 0) {
							window.location.href = '/schoolSell_boot/frontend/detailaward?awardId='
									+ awardId + '&userAwardId=' + userAwardId;
						}

					})

	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading)
			return;
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
