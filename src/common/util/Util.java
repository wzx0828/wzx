package common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
@SuppressWarnings("all")
public class Util {
	Map<String,Object> map = new HashMap<String,Object>();
	List<Map<String,Object>> list = new ArrayList();
	public  void note(){
		this.map.put("a", 1);
		this.map.put("d", 2);
		this.map.put("b", 3);
		this.list = Arrays.asList(map);
		for(int i=0;i<this.list.size();i++){
			System.out.println(this.list.get(i).get("d"));
		}
	}
	/**
	 * 
	 * @desc 返回对象大小 String/List
	 * @author Snake^_^
	 * @param obj
	 * @return
	 */
	public int size(Object obj) {
		if (obj instanceof String) {
			return obj.toString().length();
		} else if (obj instanceof List) {
			return ((List) obj).size();
		}
		return 0;
	}

	/***
	 * 
	 * @desc bean 转map
	 * @author Snake^_^
	 * @param obj
	 * @return
	 */
	public Map<String, Object> bean2Map(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Field> list = new ArrayList<Field>();
		list.addAll(Arrays.asList(obj.getClass().getDeclaredFields()));
		list.addAll(Arrays.asList(obj.getClass().getSuperclass().getDeclaredFields()));
		// Field[] f = obj.getClass().getSuperclass().getDeclaredFields();

		for (Field fd : list) {
			fd.setAccessible(true);
			try {
				map.put(this.strFormat(fd.getName(),1), fd.get(obj));
				// System.out.println(fd.getName()+"="+fd.get(obj));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	/**
	 * 
	 * @desc 
	 * @author Snake^_^
	 * @param str
	 * @param type 1-驼峰转下划线    else - 下划线转为java驼峰
	 * @return
	 */
	public String strFormat(String str, int type) {
		StringBuffer sb = new StringBuffer();
		if (type == 1) {//驼峰转下划线
			for (int i = 0; i < str.length(); i++) {
				if (Character.isUpperCase(str.charAt(i))) {
					sb.append("_");
				}
				sb.append(str.charAt(i));
			}
			return sb.toString().toLowerCase();
		} else {// 下划线转为java驼峰命名格式
			while (str.contains("_")) {
				String first = str.substring(0, str.lastIndexOf("_"));
				String mid = str.substring(str.lastIndexOf("_") + 1, str.lastIndexOf("_") + 2).toUpperCase();
				String tail = str.substring(str.lastIndexOf("_") + 2, str.length());
				str = first + mid + tail;
			}
			return str;
		}
	}
	/**
	 * 
	 * @desc map转bean
	 * @author Snake^_^
	 * @param map
	 * @param obj
	 * @param isCamelCase
	 */
	public void map2Bean(Map<String, Object> map, Object obj, boolean isCamelCase) {
		if (map == null || obj == null) {
			return;
		}
		Map<String, Object> newMap = new HashMap<String,Object>();
		if (isCamelCase) {
			for(Map.Entry entry:map.entrySet()){
				newMap.put(this.strFormat((String)entry.getKey(), 2), entry.getValue());
			}
		}else{
			newMap = map;
		}
		try {
			BeanUtils.populate(obj, newMap);
		} catch (Exception e) {
		}
	}
	/**
	 * 
	 * @desc 比较两个数值是否相等  int long
	 * @author Snake^_^
	 * @param k
	 * @param args
	 * @return
	 */
	public boolean contain(Object k, Object... args) {
		boolean flag = false;
		for (Object obj : args) {
			if (k instanceof Integer && obj instanceof Integer) {
				System.out.println("int,int");
				flag = ((int) k == (int) obj );
				if(flag){
					break;
				}
			} else if (k instanceof Integer && obj instanceof Long) {
				System.out.println("int,long");
				flag = ((int) k == (long) obj );
				if(flag){
					break;
				}
			} else if (k instanceof Long && obj instanceof Integer) {
				System.out.println("long,int");
				flag = ((long) k == (int) obj);
				if(flag){
					break;
				}
			} else if (k instanceof Long && obj instanceof Long) {
				System.out.println("long,long");
				flag = ((long) k == (long) obj) ;
				if(flag){
					break;
				}
			}else if(k instanceof String && obj instanceof String){
				flag =	k.equals(obj);
				if(flag){
					break;
				}
			}else{
				//.... 抛出异常
				System.out.println("不支持该类型！");
			}
		}
		return flag;
	}
	/**
	 * 
	 * @desc 字符串是否为全数字
	 * @author Snake^_^
	 * @param str
	 * @return
	 */
	public boolean isNumber(String str){
		String regex = "[0-9]*";
		Pattern pattern =Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	/**
	 * 
	 * @desc 将String 按照 固定大小分割成若干块放到list中
	 * @author Snake^_^
	 * @param str
	 * @param size  10的倍数
	 * @return
	 */
	public List<String> splitStringToList(String str,int size){
		int k = str.length();
		int count = (k % size != 0) ? k / size + 1 : k / size;// 判断分成几个list
		System.out.println("-------k:"+k+"------count:"+count);
		List<String> list = new ArrayList<>();
		//String tmpStr =null;
		for (int i = 0; i < count; i=i+1) {
		//	tmpStr=str.substring(i*size,(i==(count-1) && (k % size != 0))?(i*size+k%size):(i+1)*size);
			list.add(str.substring(i*size,(i==(count-1) && (k % size != 0))?(i*size+k%size):(i+1)*size));
		}
		System.out.println(list);
		return list;
	}
	
	/**
	 * 
	 * @desc  n个List<String>  按照固定大小分割 ，大List分割成几个小List
	 * @author Snake^_^
	 * @param list
	 * @param size
	 * @return
	 */
	public <T> List<List<T>> splitList(List<T> list, int size) {
		int k = list.size();
		int count = (k % size != 0) ? k / size + 1 : k / size;// 判断分成几个list
		List<List<T>> tmpList = new ArrayList<>();
		for (int i = 0; i < count; i = i + 1) {
			if ((i == (count - 1) && (k % size != 0))) {
				tmpList.add(list.subList(i * size, i * size + k % size));
			} else {
				tmpList.add(list.subList(i * size, (i + 1) * size));
			}
		}
		System.out.println(tmpList + "," + tmpList.size());
		return tmpList;
	}

	
	public static void main(String[] args) {
		Util u = new Util();
		String str = "7×7-3+5";
	}
}
