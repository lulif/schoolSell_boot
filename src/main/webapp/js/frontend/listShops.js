$(function() {
	var loading = false;
	var maxItems = 999;
	// 一页几项
	var pageSize = 3;
	// 第几页
	var pageIndex = 1;
	var parentId = getQueryString('parentId');
	var index = getQueryString('index');
	var listShopsUrl = '/schoolSell_boot/frontend/listshops';
	var searchDivUrl = '/schoolSell_boot/frontend/shopspageinfo';
	var areaId = '';
	var shopCategoryId = '';
	var shopName = '';

	getSearchDiv();
	addItems();

	function getSearchDiv() {
		var url = searchDivUrl + '?parentId=' + parentId;
		$
				.getJSON(
						url,
						function(data) {
							if (data.success) {
								var shopCategoryList = data.shopCategoryList;
								var areaList = data.areaList;
								var html = '';
								html += '<a href="#" class="button" data-category-id=""> 全部类别  </a>';
								shopCategoryList
										.map(function(item, index) {
											html += '<a href="#" class="button" data-category-id='
													+ item.shopCategoryId
													+ '>'
													+ item.shopCategoryName
													+ '</a>';
										});
								$('#shoplist-search-div').html(html);

								var selectOptions = '<option value="">全部区域</option>';
								areaList.map(function(item, index) {
									selectOptions += '<option value="'
											+ item.areaId + '">'
											+ item.areaName + '</option>';
								});
								$('#area-search').html(selectOptions);
							}
						});
	}

	function addItems() {
		var url = listShopsUrl + '?' + 'enableStatus=1' + '&pageIndex='
				+ pageIndex + '&pageSize=' + pageSize + '&parentId=' + parentId
				+ '&areaId=' + areaId + '&shopCategoryId=' + shopCategoryId
				+ '&shopName=' + shopName;
		loading = true;
		$.getJSON(url, function(data) {
			if (data.success) {
				maxItems = data.count;
				var html = '';
				data.shopList.map(function(item, index) {
					html += '' + '<div class="card" ' + '>'
							+ '<div class="card-header">' + item.shopName
							+ '<span class="pull-right" style="color:red">';
					if (item.sAvgPoint != undefined) {
						html += '口碑评分：' + item.sAvgPoint;
					} else {
						html += '暂无顾客评分';
					}

					html += '</span></div>' + '<div class="card-content">'
							+ '<div class="list-block media-list">' + '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-media">' + '<img src="'
							+ getContextPath()
							+ item.shopImg
							+ '" width="50">'
							+ '</div>'
							+ '<div class="item-inner">'
							+ '<div class="item-subtitle">'
							+ item.shopDesc
							+ '</div>'
							+ '</div>'
							+ '</li>'
							+ '</ul>'
							+ '</div>'
							+ '</div>'
							+ '<div class="card-footer">'
							+ '<p class="color-gray">'
							+ new Date(item.lastEditTime).Format("yyyy-MM-dd")
							+ '更新</p>'
							+ '<a class="look"'
							+ ' data-shop-id="'
							+ item.shopId + '">点击查看</a>' + '</div>' + '</div>';
				});
				$('.list-div').append(html);

				var total = $('.list-div .card').length;
				if (total >= maxItems) {
					//					
					/*
					 * // 加载完毕，则注销无限加载事件，以防不必要的加载 不用注销加载事件，你这一但注销掉， 那用其他搜索条件来搜时
					 * 就不能下滑加载了 $.detachInfiniteScroll($('.infinite-scroll'));
					 */
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
		if (loading) {
			return;
		}
		addItems();
	});
	// 进详情页
	$('.shop-list')
			.on(
					'click',
					'.look',
					function(e) {
						var shopId = e.currentTarget.dataset.shopId;
						window.location.href = '/schoolSell_boot/frontend/gotodetailshops?shopid='
								+ shopId;
					});

	$('#shoplist-search-div').on(
			'click',
			'.button',
			function(e) {
				if (parentId && index) {// 有parentId 点中的是一个小分类,index判断是否从index页面进来的
												// 那么按照小分类的种类来分
					shopCategoryId = e.target.dataset.categoryId;
					if ($(e.target).hasClass('button-fill')) {// 点了变黑了，再点一下去掉黑嘛
						$(e.target).removeClass('button-fill');
						shopCategoryId = ''; // 此时 种类也清零
					} else { // 不是黑的点一下，变黑 其他去掉这样的样式
						$(e.target).addClass('button-fill').siblings()
								.removeClass('button-fill');
					}
					$('.list-div').empty();
					pageIndex = 1;
					addItems();
				} else {// 没有parentId 说明点的是一个大类 那么按照大类的种类分 此时就需要parentId传给后台了

					parentId = e.target.dataset.categoryId;
					if ($(e.target).hasClass('button-fill')) {
						$(e.target).removeClass('button-fill');
						parentId = '';
					} else {
						$(e.target).addClass('button-fill').siblings()
								.removeClass('button-fill');
					}
					$('.list-div').empty();
					pageIndex = 1;
					addItems();
				}
			})

	$('#search').on('change', function(e) {
		shopName = e.target.value;
		$('.list-div').empty();
		pageIndex = 1;
		addItems();
	});

	$('#area-search').on('change', function() {
		areaId = $('#area-search').val();
		$('.list-div').empty();
		pageIndex = 1;
		addItems();
	});

	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});

	$.init();

})