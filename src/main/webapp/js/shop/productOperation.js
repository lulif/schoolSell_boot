$(function() {
	var productId = getQueryString('productid');
	// 根据商品id获取商品信息
	var infoUrl = '/schoolSell_boot/shopadmin/getproductbyid?productid=' + productId;
	// 获取商品的类别
	var categoryUrl = '/schoolSell_boot/shopadmin/getproductcategorylist';
	// post的url默认为更新商品
	var productPostUrl = '/schoolSell_boot/shopadmin/modifyproduct';
	var isEdit = false;
	
	if (productId) {
		// 修改操作
		getInfo(productId);
		isEdit = true;
	} else {
		getCategory()
		// 新增操作
		productPostUrl = '/schoolSell_boot/shopadmin/addproduct';
	}
	// 获取店铺的信息
	function getInfo(id) {
		$
				.getJSON(
						infoUrl,
						function(data) {
							if (data.success) {
								var product = data.product;
								$('#product-name').val(product.productName);
								$('#product-desc').val(product.productDesc);
								$('#priority').val(product.priority);
								$('#point').val(product.point);
								$('#normal-price').val(product.normalPrice);
								$('#promotion-price').val(
										product.promotionPrice);
								var	optionHtml='';
								var optionArr =data.productCategoryList;
								var optionSelected = product.productCategory.productCategoryId;
								optionArr.map(function(item, index) {
											var isSelect = optionSelected === item.productCategoryId ? 'selected'
													: '';
											optionHtml+= '<option data-value="'
													+ item.productCategoryId
													+ '"'
													+ isSelect
													+ '>'
													+ item.productCategoryName
													+ '<option>';
										});
								$('#category').html(optionHtml);
							}
						});
	}
	// 获取商品种类
	function getCategory() {
		$.getJSON(categoryUrl, function(data) {
			if (data.success) {
				var productCategoryList = data.productCategoryList;
				console.log(data.productCategoryList);
				var optionHtml = '';
				productCategoryList.map(function(item, index) {
					optionHtml += '<option data-value="'
							+ item.productCategoryId + '">'
							+ item.productCategoryName + '</option>';
				});
				$('#category').html(optionHtml);
			}
		});		
	}
	// 商品详情图按钮append
	$('.detail-img-div').on('change', '.detail-img:last-child', function() {
		if ($('.detail-img').length < 6) {
			$('#detail-img').append('<input type="file" class="detail-img">');
		}
	});

	$('#submit').click(
			function() {
				var product = {};
				// product的基本信息
				product.productName = $('#product-name').val();
				product.productDesc = $('#product-desc').val();
				product.priority = $('#priority').val();
				product.point = $('#point').val();
				product.normalPrice = $('#normal-price').val();
				product.promotionPrice = $('#promotion-price').val();
				product.productCategory = {
						productCategoryId : $('#category').find('option').not(
								function() {
									return !this.selected;
								}).data('value')
					};
				product.productId = productId;
				// 商品图片
				var thumbnail = $('#small-img')[0].files[0];
				var formData = new FormData();
				formData.append('productImg', thumbnail);
				// 商品详情图片
				$('.detail-img').map(
						function(index, item) {
							if ($('.detail-img')[index].files.length > 0) {
								formData.append('productDetailImg' + index,
										$('.detail-img')[index].files[0]);
							}
						});

				formData.append('productStr', JSON.stringify(product));
				// 验证码
				var verifyCodeActual = $('#j_captcha').val();
				if (!verifyCodeActual) {
					$.toast('请输入验证码！');
					return;
				}
				formData.append("verifyCodeActual", verifyCodeActual);
				// 提交给后台
				$.ajax({
					url : productPostUrl,
					type : 'POST',
					data : formData,
					contentType : false,
					processData : false,
					cache : false,
					success : function(data) {
						if (data.success) {
							$.toast('提交成功！');
							$('#captcha_img').click();
							setTimeout(function(){
								window.location.href="/schoolSell_boot/shopadmin/productmanager";
							},800);
						} else {
							$.toast('提交失败！');
							$('#captcha_img').click();
						}
					}
				});
			});

})