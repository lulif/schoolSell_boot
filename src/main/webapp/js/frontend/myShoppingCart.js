$(function() {
	var loading = false;
	var maxItems = 999;
	// 一页几项
	var pageSize = 5;
	// 第几页
	var pageIndex = 1;

	var listCartProductUrl = '/schoolSell_boot/frontend/listcartproduct';
	var productFromCart = '/schoolSell_boot/frontend/deletefromcart';
	var productName = '';
	addItems();

	function addItems() {
		// 生成新条目的HTML
		var url = listCartProductUrl + '/' + pageIndex + '/' + pageSize
				+ '?productName=' + productName;
		loading = true;
		$
				.getJSON(
						url,
						function(data) {
							if (data.success) {
								maxItems = data.count;
								var html = '';
								data.cartProductList
										.map(function(item, index) {
											html += ''
													+ '<div class="card" >'
													+ '<div class="card-header">'
													+ item.shop.shopName
													+ '<input type="checkbox" class="pull-right checkit" data-product-id="'
													+ item.product.productId
													+ '" value="'
													+ item.product.promotionPrice
													+ '" style="width:15px;height:15px;"/></div>'
													+ '<div class="card-content" >'
													+ '<div class="list-block media-list">'
													+ '<ul>'
													+ '<li class="item-content">'
													+ '<div class="item-media" data-product-id="'
													+ item.product.productId
													+ '">'
													+ '<img src="'
													+ getContextPath()
													+ item.product.imgAddr
													+ '" width="44">'
													+ '</div>'
													+ '<div class="item-inner">'
													+ '<div class="item-subtitle"> <span>'
													+ item.product.productName
													+ '</span>'
													+ '<div class="pull-right" style="display:inline-block;"> 购买数量：<button id="sub">-</button>'
													+ '&nbsp;&nbsp;<input id="number" style="width:25px;height:30px;" value="1"/>&nbsp;&nbsp;<button id="add">+</button></div>'
													+ '</div>'
													+ '</div>'
													+ '</li>'
													+ '</ul>'
													+ '</div>'
													+ '</div>'
													+ '<div class="card-footer">'
													+ '<p class="color-gray">购买可获取'
													+ item.product.point
													+ '积分&nbsp;&nbsp;&nbsp;<span style="font-size:20px;font-weight:800;color:red">￥</span>'
													+ '<span style="font-size:20px;font-weight:600;color:red">'
													+ item.product.promotionPrice
													+ '</span>'
													+ '</p>'
													+ '<span style="font-size:15px;font-weight:800;" class="cancel pull-right delete icon icon-remove"'
													+ ' data-id="'
													+ item.id
													+ '"></span>'
													+ '</div></div>';
										});

								$('.collection-list')
										.on(
												'click',
												'.cancel',
												function(e) {
													$
															.confirm(
																	"确定从购物车中移除此商品?",
																	function() {
																		$
																				.getJSON(
																						productFromCart
																								+ "/"
																								+ e.currentTarget.dataset.id,
																						function() {
																							if (data.success) {
																								$
																										.toast("操作成功");
																								setTimeout(
																										function() {
																											window.location
																													.reload()
																										},
																										400);
																							} else {
																								$
																										.toast("操作失败");
																							}
																						});
																	});
												});

								$('.collection-list')
										.on(
												'click',
												'.item-media',
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
	setInterval(function() {
		var mon = 0;
		var num = 0;
		var tt = 0;
		var checkones = $('.list-div').find('.checkit');
		for (var i = 0; i < checkones.length; i++) {
			if (checkones[i].checked == true) {
				tt++;
				var parent = checkones[i].parentNode;
				var nextBrother = parent.nextSibling;
				var it = $(nextBrother).find("#number");
				mon += Number(checkones[i].value * Number(it.val()));
				num += Number(it.val());
			}
		}
		$('#numProduct').val("结算(" + num + ")");
		$('#numMoney').text('￥' + mon);
		if (tt == checkones.length) {
			$('.checkall').prop("checked", true);
		} else {
			$('.checkall').prop("checked", false);
		}
	}, 250);

	$('.checkall').change(function() {
		var checkones = $('.list-div').find('.checkit');
		if (this.checked) {
			for (var i = 0; i < checkones.length; i++) {
				checkones[i].checked = true;
			}
		} else {
			for (var i = 0; i < checkones.length; i++) {
				checkones[i].checked = false;
			}
		}
	});

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

	$('.collection-list').on('click', '#add', function(e) {
		var number = $(e.currentTarget).siblings()[1];
		number.value++;
	})
	$('.collection-list').on('click', '#sub', function(e) {
		var number = $(e.currentTarget).siblings()[0];
		if (number.value > 1) {
			number.value--;
		}
	})
	$("#numProduct").click(
			function() {
				var message = '';
				var checkones = $('.list-div').find('.checkit');
				for (var i = 0; i < checkones.length; i++) {
					if (checkones[i].checked == true) {
						message += checkones[i].dataset.productId + '-';
						message += $(checkones[i]).parents(".card-header")
								.next().find("#number").val()
								+ ',';
					}
				}
				 localStorage.setItem('cartMsg', message);
						window.location.href = '/schoolSell_boot/frontend/addorder?iscart=1';
			});

	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
	$.init();
});
