package com.lmdestiny.serveypack.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FindAllTarg {

	public static void main(String[] args) throws IOException {
		File f = new File("C:\\Users\\14539\\Desktop\\day-9\\lsn_surveypark001\\src\\com\\atguigu\\surveypark");
		String str = "calculateRightSum";
		find(f,str);
	}
	
	public static void find(File f,String str) throws IOException{
		if(f.isDirectory()){
			File[] fs = f.listFiles();
			for(File ff:fs){
				find(ff,str);
			}
		}else if(f.isFile()){
			try {
				FileInputStream fis = new FileInputStream(f);
				byte[] b = new byte[1024];
				int l;
				while((l=fis.read(b))!=-1){
					String s = new String(b);
					if(s.lastIndexOf(str)!=-1){
						System.out.println(f.getName());
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

}
