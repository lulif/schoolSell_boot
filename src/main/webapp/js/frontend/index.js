$(function(){
	var mainPageInfoUrl='/schoolSell_boot/frontend/mainpageinfo';
	getPageInfo();
	function getPageInfo(){
		$.getJSON(mainPageInfoUrl,function(data){
		if(data.success){
			var headLineList=data.headLineList;
			var shopCategoryList=data.shopCategoryList;
			var swiperHtml = '';
			var categoryHtml='';
			
			headLineList.map(function(item,index){
				swiperHtml += ''
                    + '<div class="swiper-slide img-wrap">'
                    +      '<img class="banner-img" src="'+getContextPath()+ item.lineImg +'" alt="'+ item.lineName +'">'
                    + '</div>';
			});
			 $('.swiper-wrapper').html(swiperHtml);
			 $(".swiper-container").swiper({
	                autoplay: 2500,
	                autoplayDisableOnInteraction: true
	            });
			 
			 shopCategoryList.map(function(item,index){
				 categoryHtml += ''
                     +  '<div class="col-50 shop-classify" data-category='+ item.shopCategoryId +'>'
                     +      '<div class="word">'
                     +          '<p class="shop-title">'+ item.shopCategoryName +'</p>'
                     +          '<p class="shop-desc">'+ item.shopCategoryDesc +'</p>'
                     +      '</div>'
                     +      '<div class="shop-classify-img-warp">'
                     +          '<img class="shop-img" src="'+getContextPath()+ item.shopCategoryImg +'">'
                     +      '</div>'
                     +  '</div>';
			 });
			 $('.row').html(categoryHtml);
		}	
		});
	}
	
	  $('#me').click(function () {
	        $.openPanel('#panel-left-demo');
	    });

	  $('.row').on('click', '.shop-classify', function (e) {
	        var shopCategoryId = e.currentTarget.dataset.category;
	        var newUrl = '/schoolSell_boot/frontend/gotolistshops?parentId=' + shopCategoryId+'&index=1';
	        window.location.href = newUrl;
	    });
})