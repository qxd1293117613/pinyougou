app.controller('baseController',function ($scope) {

    //分页控件配置
    $scope.paginationConf = {
        currentPage: 1,  //当前页
        totalItems: 10,  //总记录数
        itemsPerPage: 10, //每页记录数
        perPageOptions: [10, 20, 30, 40, 50],  //分页选项
        onChange: function(){   //页码更改后,会自动触发的方法，也就是会执行reloadlist的方法
            $scope.reloadList();//重新加载
        }
    };
    $scope.reloadList=function(){
        $scope.search($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
};

        $scope.ids=[];//定义id数组,接受用户勾选的id
    $scope.select=function ($event,id) {
        if($event.target.checked){
            $scope.ids.push(id);
        }else{
            var index=$scope.ids.indexOf(id);
            $scope.ids.splice(index,1);
        }


    };
    $scope.jsontostring=function (jsonstring,key) {
        var json=JSON.parse(jsonstring);
        var value="";
        for(var i=0;i<json.length;i++){
            value+=","+json[i][key]  //每一个text值
        }

       return value;
    }
    //判断规格名称是否存在,list是存储规格对象的集合
    $scope.listener=function(list,key,keyValue){

        for(var i=0;i<list.length;i++){
            if(list[i][key]==keyValue){
                return list[i];
            }
        }
        return null;
    }


});
