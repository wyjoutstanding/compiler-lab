package cn.nuaa.compiler.lexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class LexicalAnalysis {
	private StringBuilder fstr;
	private String fin;
	private File ff;
	private TypeTable tt;
	public LexicalAnalysis(File f) throws IOException {
		if(f==null) 
			throw new IllegalArgumentException("File is empty");
		else if(f.isDirectory())
			throw new FileNotFoundException("Path is Directory");
		else if(FilenameUtils.getExtension(f.getPath()).compareTo(new String(" c"))==0) 
			throw new FileNotFoundException("Extension is illegal");
		
		int i = 0;
		fin = "";
		tt = new TypeTable();
		String str = FileUtils.readFileToString(f, "UTF-8");
		fstr = new StringBuilder(str);
		ff = FileUtils.getFile(FilenameUtils.getFullPath(f.getPath()), FilenameUtils.getBaseName(f.getPath())+"-output.txt");
		ff.createNewFile();
		
		RemoveAnnotation(); // remove 
		BeginWord();
		
		FileUtils.write(ff, fin, "utf-8");
	}
	
	//消除注释
	private void RemoveAnnotation() {
		int i=0;
		int j=0;
		int t = 0;
		while(i<fstr.length()) {
			if(fstr.charAt(i)=='"') {
				j++;
				i++;
			}
			else if(j%2==0&&fstr.charAt(i) == '/'&&fstr.charAt(i+1) == '*') {
				t=0;
				while(fstr.charAt(i+t)!='*'||fstr.charAt(i+t+1)!='/') {
					t++;
				}
				fstr.replace(i,i+t+2, "");
			}
			else if(j%2==0&&fstr.charAt(i) == '/'&&fstr.charAt(i+1) == '/') {
				t=0;
				while(fstr.charAt(i+t)!='\r')t++;
				fstr.replace(i, i+t+1, "");
			}
			else i++;
		}
	}
	
	//判断宏定义
	private int isMacro(int i) {
		int j = i+1;
		String str = "";
		while(j<fstr.length()) {
			if(fstr.charAt(j)=='\r')
				break;
			else j++;
		}
		str = fstr.substring(i, j);
		fin+=str+" 宏定义\r\n";
		return j+1;
	}
	
	//读取一串字符进行判断
	private int ReadWord(int i) {
		int j = i;
		char ch = 0;
		String str = "";
		while(j<fstr.length()) {
			ch = fstr.charAt(j);
			if(ch>='0'&&ch<='9') {
				j++;
			}
			else if(ch>='a'&&ch<='z') {
				j++;
			}
			else if(ch>='A'&&ch<='Z') {
				j++;
			}
			else if(ch =='_') {
				j++;
			}
			else{
				
				str = fstr.substring(i, j);
				fin += str;
				if(str.compareTo("true")==0||str.compareTo("false")==0)fin +=" 布尔\r\n"; 
				else if(tt.getType().get(str)==null)fin += " 标识符\r\n";
				else fin+=" 关键字\r\n";
				return j;
			}
			
		}
		str = fstr.substring(i, j);
		fin += str+"  关键字\r\n";
		return j;
	}
	
	//读取字符串
	private int ReadStr(int i) {
		int j = i+1;
		char ch = 0;
		String str = "";
		while(j<fstr.length()) {
			ch = fstr.charAt(j);
			if(ch=='\\') {
				j+=2;
			}
			else if(ch=='"') {
				str = fstr.substring(i+1, j);
				fin+="\" 界符\r\n"+str+" 字符串\r\n\" 界符\r\n";

				return j+1;
			}
			else j++;
		}
		
		assert(false);
		
		return i;
	}
	
	//读取字符
	private int ReadCh(int i) {
		int j = i+1;
		char ch = 0;
		String str = "";
		while(j<fstr.length()) {
			ch = fstr.charAt(j);
			if(ch=='\\') {
				j+=2;
			}
			else if(ch=='\'') {
				str = fstr.substring(i+1, j);
				fin+="\' 界符\r\n"+str+" 字符\r\n\' 界符\r\n";

				return j+1;
			}
			else j++;
		}
		
		assert(false);
		
		return i;
	}
	
	//判断注意
	private int ReadNotice(int i) {
		char ch = fstr.charAt(i);
		if(ch == '+') {
			if(fstr.charAt(i+1)=='=') {
				fin += "+= 运算符\r\n";
				return i+2;
			}
			else if(fstr.charAt(i+1)=='+') {
				fin += "++ 运算符\r\n";
				return i+2;
			}
			else {
				fin += "+ 运算符\r\n";
				return i+1;
			}
		}
		if(ch == '-') {
			if(fstr.charAt(i+1)=='=') {
				fin += "-= 运算符\r\n";
				return i+2;
			}
			else if(fstr.charAt(i+1)=='-') {
				fin += "-- 运算符\r\n";
				return i+2;
			}
			else {
				fin += "- 运算符\r\n";
				return i+1;
			}
		}
		if(ch == '*') {
			if(fstr.charAt(i+1)=='=') {
				fin += "*= 运算符\r\n";
				return i+2;
			}
			else {
				fin += "* 运算符\r\n";
				return i+1;
			}
		}
		if(ch == '/') {
			if(fstr.charAt(i+1)=='=') {
				fin += "/= 运算符\r\n";
				return i+2;
			}
			else {
				fin += "/ 运算符\r\n";
				return i+1;
			}
		}
		if(ch == '<') {
			if(fstr.charAt(i+1)=='=') {
				fin += "<= 运算符\r\n";
				return i+2;
			}
			else if(fstr.charAt(i+1)=='<') {
				if(fstr.charAt(i+2)=='=') {
					fin += "<<= 运算符\r\n";
				    return i+3;
				}
				else {
					fin += "<< 运算符\r\n";
				    return i+2;
				}
			}
			else {
				fin += "< 运算符\r\n";
				return i+1;
			}
		}
		if(ch == '>') {
			if(fstr.charAt(i+1)=='=') {
				fin += ">= 运算符\r\n";
				return i+2;
			}
			else if(fstr.charAt(i+1)=='>') {
				if(fstr.charAt(i+2)=='=') {
					fin += ">>= 运算符\r\n";
				    return i+3;
				}
				else {
					fin += ">> 运算符\r\n";
				    return i+2;
				}
			}
			else {
				fin += "> 运算符\r\n";
				return i+1;
			}
		}
		if(ch == '=') {
			if(fstr.charAt(i+1)=='=') {
				fin += "== 运算符\r\n";
				return i+2;
			}
			else {
				fin += "= 运算符\r\n";
				return i+1;
			}
		}
		if(ch == '!') {
			if(fstr.charAt(i+1)=='=') {
				fin += "!= 运算符\r\n";
				return i+2;
			}
			else {
				fin += "! 运算符\r\n";
				return i+1;
			}
		}
		if(ch == '&') {
			if(fstr.charAt(i+1)=='=') {
				fin += "&= 运算符\r\n";
				return i+2;
			}
			else if(fstr.charAt(i+1)=='&') {
				fin += "&& 运算符\r\n";
				return i+2;
			}
			else {
				fin += "& 运算符\r\n";
				return i+1;
			}
		}
		if(ch == '|') {
			if(fstr.charAt(i+1)=='=') {
				fin += "|= 运算符\r\n";
				return i+2;
			}
			else if(fstr.charAt(i+1)=='|') {
				fin += "|| 运算符\r\n";
				return i+2;
			}
			else {
				fin += "| 运算符\r\n";
				return i+1;
			}
		}
			
		return 0;
	}

	
	//判断数字
	private int ReadDigit(int i) {
		int j=i;
		char ch = 0;
		boolean f = false;
		while(j<fstr.length()) {
			ch = fstr.charAt(j);
			if(Character.isDigit(ch)) {
				j++;
			}
			else if(ch == '.') {
				if(f)assert(false);
				f=true;
				j++;
			}
			else {
				fin += fstr.substring(i, j);
				if(f)fin+=" 实数\r\n";
				else fin+=" 整数\r\n";
				
				return j;
			}
		}
		assert(false);
		return 0;
	}
	
	private void  BeginWord() {
		int i = 0;
		char ch = 0;
		while(i<fstr.length()) {
			ch = fstr.charAt(i);
			if(ch == '"') {
				i=ReadStr(i);
			}
			else if(ch == '\'') {
				i=ReadCh(i);
			}
			else if(ch == '#') {
				i=isMacro(i);
			}
			else if(ch == ' '||ch == '\r'||ch=='\t'||ch=='\n') {
				i++;
			}
			else if(ch>='a'&&ch<='z') {
				i=ReadWord(i);
			}
			else if(ch>='A'&&ch<='Z') {
				i=ReadWord(i);
			}
			else if(ch =='_') {
				i=ReadWord(i);
			}
			else if(ch == '+'||ch=='-'||ch=='*'||ch=='/'||ch=='='||ch=='<'||ch=='>'||ch=='!'||ch=='&'||ch=='|') {
				i = ReadNotice(i);
 			}		
			else if(tt.getType().get(String.valueOf(ch))!=null) {
				if(tt.getType().get(String.valueOf(ch))>=62){
					fin+=ch+" 界符\r\n";
				}
				else fin+=ch+" 运算符\r\n";
				i++;
			}
			else if(Character.isDigit(ch)) {
				i=ReadDigit(i);
			}
			else {
				System.out.println(ch);
				assert(false);
			}
		}
		
	} 
	
	public String getStr() {
		return fin;
	}
	
	public File getFile() {
		return ff;
	}
}
