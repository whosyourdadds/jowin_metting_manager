package cn.jowin.util;

public class ReturnIdList {
	public static String[] RestoreIdList(String[] ids){
		for(int j = 0; j<ids.length;j++){
			ids[j]=ids[j].replace("[", "");
			ids[j]=ids[j].replace("]", "");
			ids[j]=ids[j].replace("\"", "");
		}
		return ids;
	}
	
}
