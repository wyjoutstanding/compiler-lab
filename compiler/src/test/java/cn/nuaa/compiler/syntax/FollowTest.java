package cn.nuaa.compiler.syntax;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class FollowTest {

	@Test
	public void test() {
		Map<String,List<String>> f = new HashMap<String,List<String>>();
		List<String> Ls0 = new ArrayList<String>();
		Ls0.add("(");
		Ls0.add("i");
		f.put("E", Ls0);
		List<String> Ls1 = new ArrayList<String>();
		Ls1.add("+");
		Ls1.add("ɛ");
		f.put("E'",Ls1);
		List<String> Ls2 = new ArrayList<String>();
		Ls2.add("(");
		Ls2.add("i");
		f.put("T",Ls2);
		List<String> Ls3 = new ArrayList<String>();
		Ls3.add("*");
		Ls3.add("ɛ");
		f.put("T'", Ls3);
		List<String> Ls4 = new ArrayList<String>();
		Ls4.add("(");
		Ls4.add("i");
		f.put("F", Ls4);
		
		Map<String,List<List<String>>> p = new HashMap<String,List<List<String>>>();
		List<List<String>> LLs0 = new ArrayList<List<String>>();
		List<String> Ls5 = new ArrayList<String>();
		Ls5.add("T");
		Ls5.add("E'");
		LLs0.add(Ls5);
		p.put("E", LLs0);
		List<List<String>> LLs1 = new ArrayList<List<String>>();
		List<String> Ls6 = new ArrayList<String>();
		Ls6.add("+");
		Ls6.add("T");
		Ls6.add("E'");
		LLs1.add(Ls6);
		List<String> Ls7 = new ArrayList<String>();
		Ls7.add("ɛ");
		LLs1.add(Ls7);
		p.put("E'", LLs1);
		List<List<String>> LLs2 = new ArrayList<List<String>>();
		List<String> Ls8 = new ArrayList<String>();
		Ls8.add("F");
		Ls8.add("T'");
		LLs2.add(Ls8);
		p.put("T", LLs2);
		List<List<String>> LLs3 = new ArrayList<List<String>>();
		List<String> Ls9 = new ArrayList<String>();
		Ls9.add("*");
		Ls9.add("F");
		Ls9.add("T'");
		LLs3.add(Ls9);
		List<String> Ls10 = new ArrayList<String>();
		Ls10.add("ɛ");
		LLs3.add(Ls10);
		p.put("T'", LLs3);
		List<List<String>> LLs4 = new ArrayList<List<String>>();
		List<String> Ls11 = new ArrayList<String>();
		Ls11.add("(");
		Ls11.add("E");
		Ls11.add(")");
		LLs4.add(Ls11);
		List<String> Ls12 = new ArrayList<String>();
		Ls12.add("i");
		LLs4.add(Ls12);
		p.put("F", LLs4);
	//	System.out.println(p.toString());
		
		Follow ff = new Follow(f,p);
		System.out.println(ff.getFollow().toString());
	}

}
