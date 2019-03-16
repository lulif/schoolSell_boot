/*
 * 进入这个页面分为注册店铺和修改店铺两种操作
 */
$(function() {
	// 判断是店铺注册还是店铺修改
	var edit = getQueryString('edit');
	var isEdit = edit ? true : false;
	var shopId = null;
	// 获取区域和店铺分类url
	var initUrl = '/schoolSell_boot/shopadmin/getshopinitinfo';
	// 店铺注册url
	var registerShopUrl = '/schoolSell_boot/shopadmin/registershop';
	// 获取店铺信息
	var shopInfoUrl = '/schoolSell_boot/shopadmin/getshopbyid';
	// 店铺修改url
	var editShopUrl = '/schoolSell_boot/shopadmin/modifyshop';

	if (!isEdit) {
		getShopInitInfo();
	} else {
		$('#tt').html("店铺信息");
		$('#submit').html("修改");
		getInfo();
	}
	// 修改页面得到的店铺信息
	function getInfo() {
		$.getJSON(shopInfoUrl, function(data) {
			if (data.success) {
				var shop = data.shop;
				shopId = shop.shopId;
				$('#shop-name').val(shop.shopName);
				$('#shop-addr').val(shop.shopAddr);
				$('#shop-phone').val(shop.phone);
				$('#shop-desc').val(shop.shopDesc);
				var shopCategory = '<option data-id="'
						+ shop.shopCategory.shopCategoryId + '" selected>'
						+ shop.shopCategory.shopCategoryName + '</option>';

				var tempAreaHtml = '';
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
							+ item.areaName + '</option>';
				});
				$('#shop-category').html(shopCategory);
				$('#shop-category').attr('disabled', 'disabled');
				$('#area').html(tempAreaHtml);
				$('#area').attr('data-id', shop.areaId);
			}
		});
	}

	// 从后台获取 店铺分类/区域信息 ，将其填充至选择下拉栏
	function getShopInitInfo() {
		$.getJSON(initUrl, function(data) {
			if (data.success) {
				var tempHtml = "";
				var tempAreaHtml = "";
				data.shopCategoryList.map(function(item, index) {
					tempHtml += '<option data-id="' + item.shopCategoryId
							+ '">' + item.shopCategoryName + '</option>';
				});
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
							+ item.areaName + '</option>';
				});
				$('#shop-category').html(tempHtml);
				$('#area').html(tempAreaHtml);

			}
		});
	}
	// 店铺注册submit
	$('#submit').click(function() {
		var shop = {};
		if (isEdit) {
			shop.shopId = shopId;
		}
		shop.shopName = $('#shop-name').val();
		shop.shopAddr = $('#shop-addr').val();
		shop.phone = $('#shop-phone').val();
		shop.shopDesc = $('#shop-desc').val();
		shop.shopCategory = {
			shopCategoryId : $('#shop-category').find('option').not(function() {
				return !this.selected;
			}).data('id')
		};

		shop.area = {
			areaId : $('#area').find('option').not(function() {
				return !this.selected;
			}).data('id')
		};

		var shopImg = $('#shop-img')[0].files[0];

		var formData = new FormData();
		formData.append('shopImg', shopImg);
		formData.append('shopStr', JSON.stringify(shop));
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('输入验证码');
			return;
		}
		formData.append('verifyCodeActual', verifyCodeActual);
		$.ajax({
			url : (isEdit ? editShopUrl : registerShopUrl),
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast('提交成功!');
					setTimeout(function(){
						window.location.href="/schoolSell_boot/shopadmin/shopmanager";
					},800);
				} else {
					$.toast('提交失败!  ' + data.errMsg);
				}
				$('#captcha_img').click();
			}
		});
	});
})