$(function() {
	var loading = false;
	var maxItems = 999;
	// 一页几项
	var pageSize = 5;
	// 第几页
	var pageIndex = 1;

	var listCollectionUrl = '/schoolSell_boot/frontend/listusercollectionmap';
	var cancelCollectionUrl = '/schoolSell_boot/frontend/deleteusercollectionurl';
	var productName = '';
	addItems();

	function addItems() {
		// 生成新条目的HTML
		var url = listCollectionUrl + '?pageIndex=' + pageIndex + '&pageSize='
				+ pageSize + '&productName=' + productName;
		loading = true;
		$
				.getJSON(
						url,
						function(data) {
							if (data.success) {
								maxItems = data.count;
								var html = '';
								data.userCollectionMapList
										.map(function(item, index) {
											html += ''
													+ '<div class="card" >' 
													+ '<div class="card-header">'
													+ item.shop.shopName
													+ '<span style="color:gray;font-size:15px" class="pull-right">购买可获取'
													+ item.product.point
													+ '积分</span></div>'
													+ '<div class="card-content" data-product-id='
													+ item.product.productId
													+'>'
													+ '<div class="list-block media-list">'
													+ '<ul>'
													+ '<li class="item-content">'
													+ '<div class="item-media">'
													+ '<img src="'
													+ getContextPath()
													+ item.product.imgAddr
													+ '" width="44">'
													+ '</div>'
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
													+ '<p class="color-gray">收藏时间:'
													+ new Date(item.createTime)
															.Format("yyyy-MM-dd")
													+ '</p>'
													+ '<a class="cancel" style="font-size:15px;font-weight:800" class="pull-right"'
													+ ' data-user-collection-id="'
													+ item.userCollectionId
													+ '">取消收藏</a>'
													+ '</div></div>'
										});
								$('.collection-list')
										.on(
												'click',
												'.cancel',
												function(e) {
													$
															.confirm(
																	"您将不再收藏此商品?",
																	function() {
																		$
																				.getJSON(
																						cancelCollectionUrl
																								+ "?userCollectionId="
																								+ e.currentTarget.dataset.userCollectionId,
																						function() {
																							if (data.success) {
																								setTimeout(
																										function() {
																											window.location
																													.reload()
																										},
																										800);
																							} else {
																								$
																										.toast("操作失败");
																							}
																						});
																	})
												});

								$('.collection-list')
										.on(
												'click',
												'.card-content',
												function(e) {
													window.location.href = '/schoolSell_boot/frontend/gotodetailproducts?productid='
															+ e.currentTarget.dataset.productId;
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
		if (loading) {
			return;
		}
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
