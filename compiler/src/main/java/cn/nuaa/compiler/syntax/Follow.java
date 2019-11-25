package cn.nuaa.compiler.syntax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Follow {
	private Map<String,List<String>> First;
	private Set<Map.Entry<String,List<List<String>>>> Production;
	private Map<String,List<String>> Follow;
	private List<String> Vn;
	//判断是否增大
	private boolean state;
	private String eps = "ɛ";
	
	Follow(Map<String,List<String>> f ,Map<String,List<List<String>>> p ){
		Follow = new HashMap<String,List<String>>();
		Vn = new ArrayList<String>();
		First = f;
		Production  = p.entrySet();
		state = true;
		Init();
		CreateFollow();
	}
	
	//建立初始Follow
	private void Init() {
		Iterator<Map.Entry<String,List<List<String>>>> it = Production.iterator();
		Map.Entry<String, List<List<String>>> en = it.next();
		List<String> Ls = new ArrayList<String>();
		List<String> Ls1 = new ArrayList<String>();
		Ls.add("#");
		Follow .put(en.getKey(), Ls1);
		Vn.add(en.getKey());
		
		while(it.hasNext()){
			Ls1 = new ArrayList<String>();
			en = it.next();
			if(en.getKey().equals("E"))Follow.put("E", Ls);
			else Follow .put(en.getKey(), Ls1);
			Vn.add(en.getKey());
		}
	}
	
	//构造Follow
	private void CreateFollow() {	
		while(state){
			state = false;
			Iterator<Map.Entry<String,List<List<String>>>> it = Production.iterator();
			//Map.Entry<String, List<List<String>>> en = it.next();
			//判断每个产生式
			while(it.hasNext()) {
				Map.Entry<String, List<List<String>>> en = it.next();
				List<List<String>> LLs = en.getValue();
				for(int i=0;i<LLs.size();i++) {
					List<String> Ls = LLs.get(i);
					JudgeProduction(en.getKey(),Ls);
				}	
			}
		}
	}
	
	//判断产生式
	private void JudgeProduction(String key,List<String> Ls) {
		Ls.isEmpty();
		for(int i=0;i<Ls.size();i++) {
			if(Vn.contains(Ls.get(i)))
			{
				if(i==(Ls.size()-1)) {
					AddFollow(Ls.get(i),Follow.get(key));
				}
				else {
					List<String> temp = Ls.subList(i+1, Ls.size());
					List<String> res = getFirst(temp);
					if(res.contains(eps)){
						AddFollow(Ls.get(i),Follow.get(key));
					}
					AddFollow(Ls.get(i),res);
				}
			}
		}
	}
	
	//获得First
	private List<String> getFirst(List<String> str) {
		List<String> Ls = new ArrayList<String>();
		boolean alleps = true;
		
		for(int i=0;i<str.size();i++) {
			String temp = str.get(i);
			if(Vn.contains(temp)){
				if(alleps) {
					alleps = false;
					List<String> fs = First.get(temp);
					if(fs.contains(eps))
					{
						alleps = true;
					}
					Ls.addAll(fs);
					Ls.remove(eps);
				}
			}
			else {
				if(alleps) {
					Ls.add(temp);
					alleps = false;
				}
			}
		}
		if(alleps)
		{
			Ls.add(eps);
		}
			
		return Ls;
	}
	
	//增加Follow
	private void AddFollow(String key,List<String> Ls) {
		List<String> temp = new ArrayList<String>();
		temp = Follow.get(key);
		if(Ls.contains(eps)) {
			Ls.remove(Ls.indexOf(eps));
		}
		for(int i=0;i<Ls.size();i++) {
			if(temp.contains(Ls.get(i)))
					continue;
			state = true;
			temp.add(Ls.get(i));
		}
	}
	
	//返回Follow
	public Map<String,List<String>> getFollow(){
		return Follow;
	}
	
}
