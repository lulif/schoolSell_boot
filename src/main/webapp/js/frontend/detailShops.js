$(function() {
	var loading = false;
	var maxItems = 100;
	var pageSize = 3;
	var pageIndex = 1;
	var shopId = getQueryString('shopid');
	var productCategoryId = '';
	var productName = '';

	getSearchDivData();
	addItems();

	function getSearchDivData() {
		var url = '/schoolSell_boot/frontend/shopdetailpageinfo?shopid='
				+ shopId;
		$
				.getJSON(
						url,
						function(data) {
							if (data.success) {
								var shop = data.shop;
								var productCategoryList = data.productCategoryList;
								$('#shop-cover-pic').attr('src',
										getContextPath() + shop.shopImg);
								$('#shop-update-time').html(
										"更新时间："+new Date(shop.lastEditTime)
												.Format("yyyy-MM-dd"));
								$('#shop-name').html(
										'&nbsp;&nbsp;' + shop.shopName);
								$('#shop-desc').html(
										'&nbsp;&nbsp;' + shop.shopDesc);
								$('#shop-addr').html("店铺地址：" + shop.shopAddr);
								$('#shop-phone').html(shop.phone);
								var html = '';
								productCategoryList
										.map(function(item, index) {
											html += '<a href="#" class="button" data-product-search-id='
													+ item.productCategoryId
													+ '>'
													+ item.productCategoryName
													+ '</a>';
										});
								$('#shopdetail-button-div').html(html);
							}
						});
	}

	function addItems() {
		var url = '/schoolSell_boot/frontend/productinfobyshop'
				+ '?enableStatus=1' + '&pageIndex=' + pageIndex + '&pageSize='
				+ pageSize + '&productCategoryId=' + productCategoryId
				+ '&productName=' + productName + '&shopid=' + shopId;
		loading = true;
		$
				.getJSON(
						url,
						function(data) {
							if (data.success) {
								maxItems = data.count;
								var html = '';
								data.productList
										.map(function(item, index) {
											html += ''
													+ '<div class="card"'
													+ '>'
													+ '<div class="card-header">'
													+ item.productName
													+ '<span class="pull-right" style="color:red">口碑评分：'
													+ item.avgPoint
													+ '</span>'
													+ '</div>'
													+ '<div class="card-content">'
													+ '<div class="list-block media-list">'
													+ '<ul>'
													+ '<li class="item-content">'
													+ '<div class="item-media">'
													+ '<img src="'
													+ getContextPath()
													+ item.imgAddr
													+ '" width="44">'
													+ '</div>'
													+ '<div class="item-inner">'
													+ '<div class="item-subtitle">'
													+ item.productDesc
													+ '</div>'
													+ '</div>'
													+ '</li>'
													+ '</ul>'
													+ '</div>'
													+ '</div>'
													+ '<div class="card-footer">'
													+ '<p class="color-gray">'
													+ new Date(
															item.lastEditTime)
															.Format("yyyy-MM-dd")
													+ '更新</p>'
													+ '<a class="look" '
													+ ' data-product-id="'
													+ item.productId
													+ '">点击查看</a>'
													+ '</div>'
													+ '</div>';
										});
								$('.list-div').append(html);
								var total = $('.list-div .card').length;
								if (total >= maxItems) {
									// // 加载完毕，则注销无限加载事件，以防不必要的加载
									// $.detachInfiniteScroll($('.infinite-scroll'));
									// 删除加载提示符
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
		addItems(pageSize, pageIndex);
	});

	$('#shopdetail-button-div').on(
			'click',
			'.button',
			function(e) {
				productCategoryId = e.target.dataset.productSearchId;
				if (productCategoryId) {
					if ($(e.target).hasClass('button-fill')) {
						$(e.target).removeClass('button-fill');
						productCategoryId = '';
					} else {
						$(e.target).addClass('button-fill').siblings()
								.removeClass('button-fill');
					}
					$('.list-div').empty();
					pageIndex = 1;
					addItems();
				}
			});

	$('.list-div')
			.on(
					'click',
					'.look',
					function(e) {
						var productId = e.currentTarget.dataset.productId;
						window.location.href = '/schoolSell_boot/frontend/gotodetailproducts?productid='
								+ productId;
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
	$("#exchangelist").attr('href',
			"/schoolSell_boot/frontend/gotoawardlist?shopid=" + shopId);

})