package com.zxl.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

/**
 * IO工具类
 * @author zxl
 *
 */
public class IOUtil {
	//将爬取的数据按类别保存
	public static void save(String str,File file) throws IOException{
		if(!file.exists()){
			file.createNewFile();
			FileWriter fWriter = new FileWriter(file.getAbsolutePath());
			BufferedWriter bWriter = new BufferedWriter(fWriter);
			bWriter.write(str);
			bWriter.close();
		}
	}
	//读取爬取的数据
}
