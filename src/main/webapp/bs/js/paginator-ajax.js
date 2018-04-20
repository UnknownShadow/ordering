	
	(function($){
		var carId=1;
		var pageCount;
		var currentPage;
		$.ajax({
			type:"post",
			url:"<%=path %>/ajaxmfen",
			data:{"carId":carId},
			success:function(data){
				if(data!=null){
					var json = eval(data);
					
					$.each(json,function(index,items){
						$("#list").append(items.name+"<br>");
						pageCount = items.pageCount;		//��ҳ��
						currentPage = items.CurrentPage;	//�õ���ǰҳ
					});
							
					var options = {
							bootstrapMajorVersion:3,
							currentPage:currentPage,
							totalPages: pageCount,
							numberOfPages: 4,
							itemTexts:function(type,page,current){
								switch(type){
								case "first":
									return "��ҳ";
								case "prev":
									return "��һҳ";
								case "next":
									return "��һҳ";
								case "last":
									return "ĩҳ";
								case "page":
									return page;
									
								}
							},	//����¼�
							onPageClicked:function(event,originalEvent,type,page){
								$("#list").empty();
								$.ajax({
									type:"post",
									url:"<%=path %>/ajaxOnclick",
									data:{"page":page},
									success:function(data1){
										if(data1!=null){
											var json = eval(data1);
											
											$.each(json,function(index,items){
												$("#list").append(items.name+"<br>");
											});
										}
									}
								});
							}
					}
					$(".examlpe").bootstrapPaginator(options);
				}
			}
		});
		
	})(jQuery);
