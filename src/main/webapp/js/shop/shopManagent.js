$(function(){
	 // 点击'进入'的时候url会带有shopid
	var shopId=getQueryString('shopid');
	var shopInfoUrl='/schoolSell_boot/shopadmin/getshopmanagementinfo?shopid='+shopId;
	
	$.getJSON(shopInfoUrl,function(data){
		if(data.redirect){
			window.location.herf=data.url;
		}else{
			if(data.shopId!=undefined&&data.shopId!=null){
				shopId=data.shopId;
			}
		}
	})
})
