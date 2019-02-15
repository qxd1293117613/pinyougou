package com.pinyougou.shop.controller;
import com.pinyougou.FastDFSClient;
import com.pinyougou.entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@RestController
public class UploadController {
    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;
    @RequestMapping("/upload")
        public Result upload(MultipartFile file){	 //接收多媒体解析器解析出来的图片
            //1、取文件的扩展名  a.jpj-->jpj
            String originalFilename = file.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);//lastIndexOf(".")表示.jpj
            try {
                //2、创建一个 FastDFS 的客户端 加载配置文件
                FastDFSClient fastDFSClient
                        = new FastDFSClient("classpath:config/fdfs_client.conf");
                //3、执行上传处理
                String path = fastDFSClient.uploadFile(file.getBytes(), extName);
                //4、拼接返回的 url 和 ip 地址，拼装成完整的 url(图片地址)
                String url = FILE_SERVER_URL + path;
                return new Result(true,url);	//图片地址返回页面
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false, "上传失败");
            }
        }
    }


