$(function() {
	var listUrl = '/schoolSell_boot/shopadmin/getproductcategorylist';
	var addUrl = '/schoolSell_boot/shopadmin/addproductcategorys';
	var deleteUrl='/schoolSell_boot/shopadmin/deleteproductcategory';
	getList();
	
	function getList() {
		$
				.getJSON(
						listUrl,
						function(data) {
							if (data.success) {
								var dataList = data.productCategoryList;
								$('.category-wrap').html('');
								var tempHtml = "";
								dataList
										.map(function(item, index) {
											tempHtml += '<div class="row row-product-category now">  <div class="col-33">'
													+ item.productCategoryName
													+ '</div> <div class="col-33">'
													+ item.priority
													+ '</div><div class="col-33"><a href="#" class="button delete" data-id="'
													+ item.productCategoryId
													+ '">删除</a></div></div>';
										});
								$('.category-wrap').html(tempHtml);
							}
						})
	}

	$('#new')
			.click(
					function() {
						var tempHtml = '<div class="row row-product-category temp">'
								+ '<div class="col-33"><input class="category-input category" type="text" placeholder="类别名"></div>'
								+ '<div class="col-33"><input class="category-input priority" type="number" placeholder="优先级"></div>'
								+ '<div class="col-33"><a href="#" class="button delete temp" >删除</a></div>'
								+ '</div>';
						$('.category-wrap').append(tempHtml);
					});

	$('#submit').click(function() {
		var tempArr = $('.temp');
		var productCategoryList = [];
		tempArr.map(function(index, item) {
			var tempObj = {};
			tempObj.productCategoryName = $(item).find('.category').val();
			tempObj.priority = $(item).find('.priority').val();
			if (tempObj.productCategoryName && tempObj.priority) {
				productCategoryList.push(tempObj);
			}
		});
		$.ajax({
			url : addUrl,
			type : 'POST',
			// 从一个对象中解析出字符串
			data : JSON.stringify(productCategoryList),
			contentType : 'application/json',
			success : function(data) {
				if (data.success) {
					$.toast('提交成功');
					getList();
				} else {
					$.toast('提交失败');
				}
			}
		});
	});
	//删除页面临时的空项（数据还未提交）
	$('.category-wrap').on('click', '.row-product-category.temp .delete',function(e){
		$(this).parent().parent().remove();
	})

	
	$('.category-wrap').on('click', '.row-product-category.now .delete',function(e){
		var target=e.currentTarget;
		$.confirm("確定嘛？",function(){
			$.ajax({
				url:deleteUrl,
				type:'POST',
				data:{
					productCategoryId:target.dataset.id
				},
				dataType : "json",
				success : function(data) {
					if (data.success) {
						$.toast('提交成功');
						getList();
					} else {
						$.toast('提交失败');
					}
				}
			});
		});
	});
})