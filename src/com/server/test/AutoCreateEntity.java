package com.server.test;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;  
import com.jfinal.plugin.activerecord.DbKit;
 
import com.jfinal.plugin.c3p0.C3p0Plugin;  

import java.io.BufferedWriter;  
import java.io.File;  
import java.io.FileWriter;  
import java.util.List;  
  
/** 
 * 姝ゅ伐鍏风被锛屽彲浠ユ牴鎹暟鎹簱琛ㄨ嚜鍔ㄧ敓鎴愬疄浣撶被锛岀敓鎴愮殑瀹炰綋绫诲寘鍚潤鎬乨ao寮曠敤锛屼互鍙婂悇鍒楀瓧娈碉紝 鐢熸垚鐨勫垪瀛楁瀵瑰簲浜唈ava鐨勬暟鎹被鍨�Created on : 
 * 2013-5-6, 21:58:11 
 * 
 * @author piaohao 
 */  
public class AutoCreateEntity {  
  
    private static final String url = "jdbc:mysql://139.224.134.33/schms";  
    private static final String name = "root";  
    private static final String password = "123456";  
    private static final String dbname = "schms";
  
    /**娉ㄥ唽jfinal鏁版嵁搴撴彃浠�/  
    static {  
        C3p0Plugin cp = new C3p0Plugin(url, name, password);  
        cp.start();//鍚姩鎻掍欢  
        
        //DbKit.
//        DbKit.setDataSource(cp.getDataSource());  
    }  
  
    /** 
     * 鏍规嵁鏁版嵁搴撹〃锛岃嚜鍔ㄧ敓鎴愬疄浣撴枃浠�
     * 
     * @param path 鏂囦欢瀛樻斁璺緞 
     * @param packageName 鍖呭悕 
     * @param withField 鏄惁鐢熸垚灞炴�瀛楁 
     */  
    public static void process(String path, String packageName, boolean withField,boolean withGet) {  
        try {  
            List<Object> objs = Db.query("select table_name from information_schema.tables where table_schema=?",  
                    dbname);//鏌ヨ鎵�湁琛ㄥ悕  
            
            System.out.println(objs.size());
            for (int i = 0; i < objs.size(); i++) {  
                String tableName = (String) objs.get(i);  
                String className = StrKit.firstCharToUpperCase(tableName);  
                String result = "package " + packageName + ";\n\n";  
                result += "import com.jfinal.plugin.activerecord.Model; \n\n";  
                result += "public class " + className + " extends Model<" + className + ">{\n\n";  
                result += "    public static final " + className + " dao = new " + className + "();\n\n";  
                if (withField) {  
                    result += "    /**琛ㄥ悕**/ \n";  
                    result += "    public static String TABLE = \"" + tableName + "\";\n";  
                    List<Object> records = Db.query("select * from information_schema.columns where table_schema=? and table_name=?",  
                            dbname, tableName);//鏌ヨ琛ㄤ腑鎵�湁鍒楀悕  
                    for (int j = 0; j < records.size(); j++) {  
                        String fieldName = (String) ((Object[]) records.get(j))[3];//鏁扮粍鐨勭4涓厓绱犱负鍒楀悕  
                        String fieldType = (String) ((Object[]) records.get(j))[7];//鏁扮粍鐨勭8涓厓绱犱负鍒楃被鍨� 
                        fieldType = convert(fieldType);  
                        result += "    private " + fieldType + " " + StrKit.firstCharToLowerCase(fieldName) + ";\n";  
                    }  
                }  
                
                if (withGet) {  
                    List<Object> records = Db.query("select * from information_schema.columns where table_schema=? and table_name=?",  
                            dbname, tableName);//鏌ヨ琛ㄤ腑鎵�湁鍒楀悕  
                    for (int j = 0; j < records.size(); j++) {  
                        String fieldName = (String) ((Object[]) records.get(j))[3];//鏁扮粍鐨勭4涓厓绱犱负鍒楀悕  
                        String fieldType = (String) ((Object[]) records.get(j))[7];//鏁扮粍鐨勭8涓厓绱犱负鍒楃被鍨� 
                        fieldType = convert(fieldType);
                        
                        result +="\n\t"+overRideGet(fieldType,fieldName)+"\n";
                    }  
                } 
                
                
                result += "\n }";  
                
                //鍐欐枃浠� 
                File f = new File(path, className + ".java");  
                
                System.out.println(f.getAbsolutePath());
                if (!f.exists()) {  
                    f.createNewFile();  
                }  
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));  
                bw.write(result);  
                bw.close();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
    private static String overRideGet(String fieldType,String Colname) {  
    	String res = "";
        if (fieldType.equalsIgnoreCase("varchar") || fieldType.equalsIgnoreCase("char")  
                || fieldType.equalsIgnoreCase("blob") || fieldType.equalsIgnoreCase("text")) {  
            res+="public static String "+"get"+StrKit.firstCharToUpperCase(Colname)+"(){\n"+"\t\t return dao.getStr(\""+Colname+"\");\n\t}";
            return res;
            
            
        } else if (fieldType.equalsIgnoreCase("int") || fieldType.equalsIgnoreCase("smallint")) {  
            res+="public static int "+"get"+StrKit.firstCharToUpperCase(Colname)+"(){\n"+"\t\t return dao.getInt(\""+Colname+"\");\n\t}";
            return res;
        } else if (fieldType.equalsIgnoreCase("bit")) {  
        	res+="public static Boolean "+"get"+StrKit.firstCharToUpperCase(Colname)+"(){\n"+"\t\t return dao.getBoolean(\""+Colname+"\");\n\t}";
            return res;
        	
        } else if (fieldType.equalsIgnoreCase("float") || fieldType.equalsIgnoreCase("double")) {  
        	res+="public static Double "+"get"+StrKit.firstCharToUpperCase(Colname)+"(){\n"+"\t\t return dao.getDouble(\""+Colname+"\");\n\t}";
            return res;  
        } else if (fieldType.equalsIgnoreCase("bigint")) {  
            return "Long";  
        } else if (fieldType.equalsIgnoreCase("datetime")) {  
        	res+="public static java.utils.Date "+"get"+StrKit.firstCharToUpperCase(Colname)+"(){\n"+"\t\t return dao.getDouble(\""+Colname+"\");\n\t}";
            return res;  
        } else {  
        	res+="public static String "+"get"+StrKit.firstCharToUpperCase(Colname)+"(){\n"+"\t\t return dao.getStr(\""+Colname+"\");\n\t}";
            return res;
        }  
    }  
  
    private static String convert(String fieldType) {  
        if (fieldType.equalsIgnoreCase("varchar") || fieldType.equalsIgnoreCase("char")  
                || fieldType.equalsIgnoreCase("blob") || fieldType.equalsIgnoreCase("text")) {  
            return "String";  
        } else if (fieldType.equalsIgnoreCase("int") || fieldType.equalsIgnoreCase("smallint")) {  
            return "int";  
        } else if (fieldType.equalsIgnoreCase("bit")) {  
            return "Boolean";  
        } else if (fieldType.equalsIgnoreCase("float") || fieldType.equalsIgnoreCase("double")) {  
            return "Double";  
        } else if (fieldType.equalsIgnoreCase("bigint")) {  
            return "Long";  
        } else if (fieldType.equalsIgnoreCase("datetime")) {  
            return "java.util.Date";  
        } else {  
            return "String";  
        }  
    }  
  
    /** 
     * 
     * @param args 
     */  
    public static void main(String[] args) {  
        AutoCreateEntity.process("d:\\MyEclipse_2015_Workspaces\\SchoolManagerServer\\src", "com.server.bean", true,true);  
    }  
}
