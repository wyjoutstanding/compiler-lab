package cn.nuaa.compiler.syntax;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class Production {
	private Map<String,List<List<String>>> Production;
	private List<String> Vn;
	private List<String> Vt;
	private String P;
	Production(File f) throws IOException{
		Vn = new ArrayList<String>();
		Vt = new ArrayList<String>();
		Production = new HashMap<String, List<List<String>>>();
		P = FileUtils.readFileToString(f, "UTF-8").replace("\r\n", "\n");
		Init();
		CreatePro();
	}
	
	//初始化终结符与非终结符
	private void Init() {
		String [] temp = P.split("\\n");
		for(int i=0;i<temp.length;i++) {
			String [] pro = temp[i].split("->");
			Vn.add(pro[0]);
		}
		for(int i=0;i<temp.length;i++) {
			String [] pros = temp[i].split("->");
			String [] pro = pros[1].split("\\|");
			for(int j=0;j<pro.length;j++)
				getVt(pro[j]);
		}
	}
	
	//获取终结符
	private void getVt(String str) {
		boolean isVt = true;
		int sub = -1;
		for(int i=0;i<Vn.size();i++) {
			if(str.contains(Vn.get(i))) {
				isVt = false;
				if(sub == -1)sub = i;
				else sub = Vn.get(sub).length()>Vn.get(i).length()?sub:i;
			}
		}
		if(isVt) {
			String[] vt = str.split(" ");
			for(int i=0;i<vt.length;i++) {
				if(!Vt.contains(vt[i]))
				Vt.add(vt[i]);
			}
		}
		else {
			String [] pro = str.split(Vn.get(sub));
			for(int i = 0;i<pro.length;i++) {
				if(!pro[i].equals(" ")&&!pro[i].equals(""))
				getVt(pro[i]);
			}
		}
	}
	
	//构造产生式
	private void CreatePro() {
		String [] temp = P.split("\\n");
		for(int i =0 ;i<temp.length;i++) {
			
			String [] pro =  temp[i].split("->");
			
			List<List<String>> LL = new ArrayList<List<String>>();
			String [] rpro = pro[1].split("\\|");
			//单个非终结符的所有产生式
			for(int j =0 ;j<rpro.length;j++) {
				List<String> L = new ArrayList<>();
				rpro[j] = ChangePro(rpro[j]);
				String []num = rpro[j].split("\\|");
				for(int k = 0;k<num.length;k++) {
					if(num[k].equals("")||num[k].equals(" "))
						continue;
					int t = Integer.parseInt(num[k]);
					if(t>=1000) L.add(Vt.get(t%1000));
					else L.add(Vn.get(t));
				}
				LL.add(L);
			}
			Production.put(pro[0], LL);
		}	
	}
	
	//判断单个产生式
	private String ChangePro(String pro) {
		int sub = -1;
		boolean hasVn = true;
		boolean hasVt = true;
		//判断非终结符
		while(hasVn) {
			sub = -1;
			for(int i=0;i<Vn.size();i++) {
				if(pro.contains(Vn.get(i))) {
					if(sub == -1)sub = i;
					else sub = Vn.get(sub).length()>Vn.get(i).length()?sub:i;
				}
			}
			if(sub!=-1)
			{
				pro = pro.replace(Vn.get(sub),"|"+sub+"|");
			}
			else hasVn = false;
		}
		
		//判断终结符
		while(hasVt) {
			sub = -1;
			for(int i=0;i<Vt.size();i++) {
				if(pro.contains(Vt.get(i))) {
					if(sub == -1)sub = i;
					else sub = Vt.get(sub).length()>Vt.get(i).length()?sub:i;
				}
			}
			if(sub!=-1) {
				int temp = sub+1000;
				pro = pro.replace(Vt.get(sub),"|"+temp+"|");
			}
			else hasVt = false;
		}
		
		return pro;
	}
	
	public String getStr() {
		return P;
	}
	
	public Map<String,List<List<String>>> getPro(){
		return Production;
	}
	
	public List<String> getVt(){
		return Vt;
	}
	
	public List<String> getVn(){
		return Vn;
	}
}
