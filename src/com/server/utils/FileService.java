package com.server.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import com.server.constant.Constant;

public class FileService {

    public void fileChannelCopy(File s, File t) {

        FileInputStream fi = null;

        FileOutputStream fo = null;

        FileChannel in = null;

        FileChannel out = null;

        try {

            fi = new FileInputStream(s);

            fo = new FileOutputStream(t);

            in = fi.getChannel();// �õ���Ӧ���ļ�ͨ��

            out = fo.getChannel();// �õ���Ӧ���ļ�ͨ��

            in.transferTo(0, in.size(), out);// ��������ͨ�������Ҵ�inͨ����ȡ��Ȼ��д��outͨ��

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                fi.close();

                in.close();

                fo.close();

                out.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }
    
    public List<String> readFileForLines(String fileName){
    	List<String> lines = new ArrayList<String>();
    	File file = new File(fileName);  
        BufferedReader reader = null;  
        try {  
            //System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");  
            reader = new BufferedReader(new FileReader(file));  
            String tempString = null;  
            int line = 1;  
            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����  
            while ((tempString = reader.readLine()) != null) {  
                // ��ʾ�к�  
                line++;  
                lines.add(tempString);
            }  
            reader.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
        }
        
        return lines;
    }
    
    public void write2html(String filein, String des_path, String inp_labels, String lab_sep, String inp_head, String inp_title){
    	List<String> lines = readFileForLines(filein);
    	List<String> datas = readFileForLines(Constant.HOME_URL+"/label_base.html");
    	System.out.println(datas.size());
    	String html = "";
    	List<String> htmls = StringUtils.getInstance().getLocalLabeledHtml(lines, inp_labels, lab_sep, inp_head, inp_title);
        try {
        	//д����Ӧ���ļ�
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(des_path),"UTF8"));
            for(String l : datas){
        		out.append(l.trim()+"\n");
        	}
			for(String l : htmls){
				out.append(l.trim()+"\n");
			}
			
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    
}