package com.lmdestiny.serveypack.test;

import java.io.File;

/**
 *renameTo的使用方式
 */
public class TFileName {
	public static void main(String[] args) {
		File file = new File("d://1.txt");
		File newFile = new File("d://b.txt");
		File file2 = new File("d://a.txt");
		file.renameTo(file2);
		System.out.println("complent!!!");
		
		
	}
}
