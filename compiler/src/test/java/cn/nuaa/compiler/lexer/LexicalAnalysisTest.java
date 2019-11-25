package cn.nuaa.compiler.lexer;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

//import main.LexicalAnalysis;

public class LexicalAnalysisTest {
	// 测试用例池存放路径，可根据其位置进行修改；更改测试池存放位置，修改此处即可
	static String filePath = "D:\\Desktop\\测试用例池3.2";
	@Test(timeout=4000)
	public void test() throws IOException {

		File path = new File(filePath+File.separator+"测试用例池0");
		String[] extension = new String[] {"c"};
		List<File> flist =  (List<File>)FileUtils.listFiles(path, extension,true);
		int i=0;
		while(i<flist.size()) {
			//System.out.println(flist.get(i).toString());
			File f = new File(flist.get(i).toString());
			File tf = new File(flist.get(i).toString().replace(".c", ".txt"));
			LexicalAnalysis la = new LexicalAnalysis(f);
			//String ts = FileUtils.readFileToString(tf, "UTF-8");
			//String ls = FileUtils.readFileToString(la.getFile(), "UTF-8");
			//assertEquals(ls,ts);
			i++;
		}
	}
	@Test
	public void test1() throws IOException {

		File path = new File(filePath+File.separator+"测试用例池1_关键字"+File.separator+"测试用例池1.1_数据类型");
		String[] extension = new String[] {"c"}; // 文件扩展名
		// get all files under directory of "path" which extension is "c"
		List<File> flist =  (List<File>)FileUtils.listFiles(path, extension,true);
		int i=0;
		while(i<flist.size()) {
			//System.out.println(flist.get(i).toString());
			File f = new File(flist.get(i).toString());
			File tf = new File(flist.get(i).toString().replace(".c", ".txt"));
			LexicalAnalysis la = new LexicalAnalysis(f);
			String ts = FileUtils.readFileToString(tf, "UTF-8");
			String ls = FileUtils.readFileToString(la.getFile(), "UTF-8");
			i++;
		}
	}
	@Test(timeout =4000)
	public void test2() throws IOException {

		File path = new File(filePath+File.separator+"测试用例池1_关键字"+File.separator+"测试用例池1.2_控制语句");
		String[] extension = new String[] {"c"};
		List<File> flist =  (List<File>)FileUtils.listFiles(path, extension,true);
		int i=0;
		while(i<flist.size()) {
			//System.out.println(flist.get(i).toString());
			File f = new File(flist.get(i).toString());
			File tf = new File(flist.get(i).toString().replace(".c", ".txt"));
			LexicalAnalysis la = new LexicalAnalysis(f);
			i++;
		}
	}
	@Test(timeout =4000)
	public void test3() throws IOException {

		File path = new File(filePath+File.separator+"测试用例池1_关键字"+File.separator+"测试用例池1.3_存储类型");
		String[] extension = new String[] {"c"};
		List<File> flist =  (List<File>)FileUtils.listFiles(path, extension,true);
		int i=0;
		while(i<flist.size()) {
			//System.out.println(flist.get(i).toString());
			File f = new File(flist.get(i).toString());
			File tf = new File(flist.get(i).toString().replace(".c", ".txt"));
			LexicalAnalysis la = new LexicalAnalysis(f);
			i++;
		}
	}
	@Test(timeout =4000)
	public void test4() throws IOException {

		File path = new File(filePath+File.separator+"测试用例池1_关键字"+File.separator+"测试用例池5_otherkeywords");
		String[] extension = new String[] {"c"};
		List<File> flist =  (List<File>)FileUtils.listFiles(path, extension,true);
		int i=0;
		while(i<flist.size()) {
			//System.out.println(flist.get(i).toString());
			File f = new File(flist.get(i).toString());
			File tf = new File(flist.get(i).toString().replace(".c", ".txt"));
			LexicalAnalysis la = new LexicalAnalysis(f);
			i++;
		}
	}
	@Test(timeout =4000)
	public void test5() throws IOException {

		File path = new File(filePath+File.separator+"测试用例池2_运算符");
		String[] extension = new String[] {"c"};
		List<File> flist =  (List<File>)FileUtils.listFiles(path, extension,true);
		int i=0;
		while(i<flist.size()) {
			//System.out.println(flist.get(i).toString());
			File f = new File(flist.get(i).toString());
			File tf = new File(flist.get(i).toString().replace(".c", ".txt"));
			LexicalAnalysis la = new LexicalAnalysis(f);
			i++;
		}
	}
	@Test(timeout =4000)
	public void test6() throws IOException {

		File path = new File(filePath+File.separator+"测试用例池3_其他用例");
		String[] extension = new String[] {"c"};
		List<File> flist =  (List<File>)FileUtils.listFiles(path, extension,true);
		int i=0;
		while(i<flist.size()) {
			//System.out.println(flist.get(i).toString());
			File f = new File(flist.get(i).toString());
			File tf = new File(flist.get(i).toString().replace(".c", ".txt"));
			LexicalAnalysis la = new LexicalAnalysis(f);
			i++;
		}
	}
}
