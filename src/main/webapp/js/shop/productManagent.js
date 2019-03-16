$(function() {
	var getProductListUrl = '/schoolSell_boot/shopadmin/getproductlistbyshop?pageIndex=0&&pageSize=15';
	//商品  上|下 架
	var downUrl='/schoolSell_boot/shopadmin/modifyproduct';
	getList();
	function getList() {
		$.getJSON(getProductListUrl, function(data) {
			if (data.success) {
				var productList = data.productList;
				var tempHtml = '';
				productList.map(function(item, index) {
					var textStatus = '下架';
					var productStatus = 0;
					if (item.enableStatus == 0) {
						textStatus = "上架";
						productStatus = 1;
					} else {
						productStatus = 0;
					}
					tempHtml += '' + '<div class="row row-product">'
							+ '<div class="col-40">'
							+ item.productName
							+ '</div>'
							+ '<div class="col-10">'
							+ item.point
							+ '</div>'
							+ '<div class="col-50">'
							+ '<a href="#" class="edit" data-id="'
							+ item.productId
							+ '" data-status="'
							+ item.enableStatus
							+ '">编辑</a>'
							+ '<a href="#" class="delete" data-id="'
							+ item.productId
							+ '" data-status="'
							+ productStatus
							+ '">'
							+ textStatus
							+ '</a>'
							+ '<a href="#" class="preview" data-id="'
							+ item.productId
							+ '" data-status="'
							+ item.enableStatus
							+ '">预览</a>'
							+ '</div>'
							+ '</div>';
				});
				$('.product-wrap').html(tempHtml);
			}
		});
	}
	
	$('.product-wrap')
	.on(
			'click',
			'a',
			function(e) {
				var target = $(e.currentTarget);
				if (target.hasClass('edit')) {
					window.location.href = '/schoolSell_boot/shopadmin/productoperation?productid='
							+ e.currentTarget.dataset.id;
				} else if (target.hasClass('delete')) {
					deleteItem(e.currentTarget.dataset.id,
							e.currentTarget.dataset.status);
				} else if (target.hasClass('preview')) {
					window.location.href = '/schoolSell_boot/frontend/gotodetailproducts?productid='
							+ e.currentTarget.dataset.id;
				}
			});
	
	function deleteItem(id, enableStatus) {
		var product = {};
		product.productId = id;
		product.enableStatus = enableStatus;
		$.confirm('确定么?', function() {
			$.ajax({
				url : downUrl,
				type : 'POST',
				data : {
					productStr : JSON.stringify(product),
					statusChange : true
				},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$.toast('操作成功！');
						getList();
					} else {
						$.toast('操作失败！');
					}
				}
			});
		});
	}
	
});