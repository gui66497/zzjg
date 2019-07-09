package com.zzjz.zzjg.util;

import org.springframework.util.ResourceUtils;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author 房桂堂
 * @description FileUtil
 * @date 2019/7/9 9:39
 */
public class FileUtil {

    /**
     * 获取jar同级目录（并创建subdirectory目录）.
     * @param subdirectory 文件夹名称
     * @return 绝对路径
     */
    public static String getJarPath(String subdirectory){
        //获取跟目录---与jar包同级目录的upload目录下指定的子目录subdirectory
        File upload = null;
        try {
            //本地测试时获取到的是"工程目录/target/upload/subdirectory
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if(!path.exists()) {
                path = new File("");
            }
            upload = new File(path.getAbsolutePath(),subdirectory);
            if(!upload.exists()) {
                upload.mkdirs();//如果不存在则创建目录
            }
            String realPath = upload + File.separator;
            return realPath;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("获取服务器路径发生错误！");
        }
    }
}
