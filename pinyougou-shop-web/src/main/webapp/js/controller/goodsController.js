 //控制层 
app.controller('goodsController' ,function($scope,$controller,$location,goodsService,uploadService,itemCatService,typeTemplateService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(){
        var id=$location.search()['id'];
        if(id==null){
            return
        }
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;
				editor.html($scope.entity.goodsDesc.introduction);
                //将字符串转化为对象
                $scope.entity.goodsDesc.itemImages=JSON.parse($scope.entity.goodsDesc.itemImages)
                $scope.entity.goodsDesc.customAttributeItems=JSON.parse($scope.entity.goodsDesc.customAttributeItems)
                $scope.entity.goodsDesc.specificationItems=JSON.parse($scope.entity.goodsDesc.specificationItems)
                //转换sku中的规格对象,集合
                for(var i=0;i<$scope.entity.itemList.length;i++){
				    $scope.entity.itemList[i].spec=JSON.parse($scope.entity.itemList[i].spec)
                }


			}
		);
	};

	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.ids ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.ids=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
    //增加商品
    $scope.add=function(){
        $scope.entity.goodsDesc.introduction=editor.html();

        goodsService.add($scope.entity).success(
            function(response){   alert(response.message);
                if(response.success){
                    alert("新增成功");
                    $scope.entity={};
                    editor.html("");//清空富文本编辑器
                }else{

                }
            }
        );
    }

    //上传图片
    $scope.uploadFile=function(){
        uploadService.uploadFile().success(
            function(response){
                if(response.success){
                    $scope.image_entity.url= response.message;
                }else{
                    alert(response.message);
                }
            }
        );


    };
    //将当前图片加入图片列表
    $scope.entity={ goodsDesc:{itemImages:[],specificationItems:[]}};
    $scope.addimage=function(){
     $scope.entity.goodsDesc.itemImages.push($scope.image_entity)
	};
	//删除图片
    $scope.delete=function(index){
		$scope.entity.goodsDesc.itemImages.splice(index,1)
	};
	$scope.itemList1=function(){
        itemCatService.findByParent(0).success(function(data){
            $scope.itemslist1=data;
        })

	};
	//变量监控方法:
	$scope.$watch('entity.goods.category1Id',function(newvalue,oldvalue){
        itemCatService.findByParent(newvalue).success(function(data){
            $scope.itemslist2=data;
        })


	});
	//三级分类
    $scope.$watch('entity.goods.category2Id',function(newvalue,oldvalue){
        itemCatService.findByParent(newvalue).success(function(data){
            $scope.itemslist3=data;
        })


    });
    //根据三级id查询模板

   $scope.$watch('entity.goods.category3Id',function(newvalue,oldvalue){
        itemCatService.findOne(newvalue).success(function(data){
            $scope.entity.goods.typeTemplateId=data.typeId;
        })

    });
	//扩展属性
    $scope.$watch('entity.goods.typeTemplateId',function(newvalue,oldvalue){
      typeTemplateService.findOne(newvalue).success(function(data){
      	$scope.template=data;
      	$scope.template.brandIds=JSON.parse($scope.template.brandIds);
      	if($location.search()['id']==null){
            $scope.entity.goodsDesc.customAttributeItems=JSON.parse($scope.template.customAttributeItems);
        }

    });
        typeTemplateService.findsepclist(newvalue).success(function (data) {
            $scope.specList=data;
        })



    })
	//根据表的结构，来定义变量
     //object的格式就是[{"attributeName":"网络制式","attributeValue":["移动3G","移动4G"]},{"attributeName":"屏幕尺寸","attributeValue":["6寸","5寸"]}]
    //$scope.entity={ goodsDesc:{itemImages:[],}};
    //1当我们勾选后，将规格名称和规格值添加到集合中,在添加前,进行判断。
    // $scope.findlist=function ($event,name,value) {
    //     var object=$scope.listener($scope.entity.goodsDesc.specificationItems,"attributeName",name);
    //           if(object!=null){
    //           	//如果集合不为空，说明存在存在对应的键值，只需要在后面添加value即可。
		// 		  //当勾选后
    //               if($event.target.checked){
    //               	//只添加value
    //                   object.attributeValue.push(value);
    //
		// 		  }
		// 		  else{
    //               	//取消勾选后，移除包括key值和value的集合
    //                  object.attributeValue.splice( object.attributeValue.indexOf(value),1)
		// 			  if( object.attributeValue.length==0){
    //                  	//当取消所有勾选后，清空数据
    //                       $scope.entity.goodsDesc.specificationItems.splice($scope.entity.goodsDesc.specificationItems.indexOf(object),1)
		// 			  }
		// 		  }
    //
		// 	  }
		// 	  else {
    //           	//为空就不必判断是否勾选
    //               $scope.entity.goodsDesc.specificationItems.push({"attributeName":name,"attributeValue":[value]})
    //
    //
    //
    //
    //
    //
    //           }
    // };
    $scope.findlist=function($event,name,value){

        var object= $scope.listener($scope.entity.goodsDesc.specificationItems ,'attributeName', name);

        if(object!=null){
            if($event.target.checked ){
                object.attributeValue.push(value);
            }else{//取消勾选
                object.attributeValue.splice( object.attributeValue.indexOf(value ) ,1);//移除选项
                //如果选项都取消了，将此条记录移除
                if(object.attributeValue.length==0){
                    $scope.entity.goodsDesc.specificationItems.splice(
                        $scope.entity.goodsDesc.specificationItems.indexOf(object),1);
                }

            }
        }else{
            $scope.entity.goodsDesc.specificationItems.push({"attributeName":name,"attributeValue":[value]});
        }

    }
    //创建SKU列表
    $scope.createItemList=function(){

        $scope.entity.itemList=[{spec:{},price:0,num:99999,status:'0',isDefault:'0'} ];//列表初始化

        var item= $scope.entity.goodsDesc.specificationItems;

        for(var i=0;i<item.length;i++){
            $scope.entity.itemList= addrow( $scope.entity.itemList, item[i].attributeName,item[i].attributeValue );
        }

    }

    addrow=function(list,columnName,columnValues){

        var newList=[];
        for(var i=0;i< list.length;i++){
            var oldRow=list[i];
            for(var j=0;j<columnValues.length;j++){
                var newRow=  JSON.parse( JSON.stringify(oldRow)  );//深克隆
                newRow.spec[columnName]=columnValues[j];
                newList.push(newRow);
            }
        }
        return newList;
    }
    $scope.itemsList=[]
    $scope.one=['未审核','已审核','审核未通过','已关闭','审核已提交']
    $scope.selectlist=function(){
        itemCatService.findAll().success(function(data){
            for(var i=0;i<data.length;i++){
                $scope.itemsList[data[i].id]=data[i].name
            }
        })
    }
    //勾选返回true，判断规格与规格选项是否被勾选
$scope.checkvalue=function(specname,optionname){
        //先判断有没有规格名称
        var items=$scope.entity.goodsDesc.specificationItems;
    var b=$scope.listener( items,'attributeName',specname);
    if(b!=null){
        //如果有规格名称的话，判断规格属性值，如果不为0.则返回true，表示被勾选
        //否则返回false，不被勾选
        if(b.attributeValue.indexOf(optionname)>=0){
            return true;
        }else{
            return false;
        }
        return true;
    }else{
        return false;
    }

}//商品的审核
    $scope.shenhe=function(status){
       goodsService.shenhe($scope.ids,status).success(function(response){
            if(response.success){
                alert('提交成功')
                 $scope.ids=[]
                $scope.reloadList()
            }else{
                  alert(response.message)
            }

        })
    }
// $scope.findcatlist=function(){
//     contentCategoryService.findAll().success(function(reponse){
//        $scope.findcatlist=reponse;
//     })
//
// }







    
});

 // $scope.updateSpecAttribute=function($event,name,value){
 //     var object= $scope.searchObjectByKey(
 //         $scope.entity.goodsDesc.specificationItems ,'attributeName', name);
 //     if(object!=null){
 //         if($event.target.checked ){
 //             object.attributeValue.push(value);
 //         }else{//取消勾选
 //             object.attributeValue.splice( object.attributeValue.indexOf(value ) ,1);//移除选
 //
 //             //如果选项都取消了，将此条记录移除
 //             if(object.attributeValue.length==0){
 //                 $scope.entity.goodsDesc.specificationItems.splice(
 //                     $scope.entity.goodsDesc.specificationItems.indexOf(object),1);
 //             }
 //         }
 //     }else{
 //         $scope.entity.goodsDesc.specificationItems.push(
 //             {"attributeName":name,"attributeValue":[value]});
 //     }
 // }
