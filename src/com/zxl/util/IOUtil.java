package com.zxl.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

/**
 * IO������
 * @author zxl
 *
 */
public class IOUtil {
	//����ȡ�����ݰ���𱣴�
	public static void save(String str,File file) throws IOException{
		if(!file.exists()){
			file.createNewFile();
			FileWriter fWriter = new FileWriter(file.getAbsolutePath());
			BufferedWriter bWriter = new BufferedWriter(fWriter);
			bWriter.write(str);
			bWriter.close();
		}
	}
	//��ȡ��ȡ������
}
