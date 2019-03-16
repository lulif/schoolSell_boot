$(function() {
	var productName = getQueryString('productName');
	var shopName = getQueryString('shopName');
	var shopId = getQueryString('shopId');
	var productId = getQueryString('productId');
	var productId = getQueryString('productId');
	var upmId = getQueryString('upmId');

	var commentUrl = '/schoolSell_boot/frontend/addcomment';

	commentInfo();
	function commentInfo() {
		$('#shop-name').val(shopName);
		$('#product-name').val(productName);
		$('#shop-name').attr('disabled', 'disabled');
		$('#product-name').attr('disabled', 'disabled');
	}
	$('#submit')
			.click(
					function() {
						var customerComment = {};
						var shop = {};
						var product = {};
						shop.shopId = shopId;
						shop.shopName = shopName;
						product.productId = productId;
						product.productName = productName;
						customerComment.shop = shop;
						customerComment.product = product;
						customerComment.commentPoint = $('#comment-point')
								.val();
						customerComment.commentContent = $('#comment-content')
								.val();
						customerComment.userProductId = upmId;
						var formData = new FormData();
						formData.append('customerComment', JSON
								.stringify(customerComment));
						$
								.ajax({
									url : commentUrl,
									type : 'POST',
									data : formData,
									contentType : false,
									processData : false,
									cache : false,
									success : function(data) {
										if (data.success) {
											$.toast('评价成功');
											setTimeout(
													function() {
														window.location.href = '/schoolSell_boot/frontend/buyrecord';
													}, 800);
										} else {
											$.toast('评价失败');
										}
									}
								});
					})

})