package SerializeAndDeserialize;

import java.util.*;

/** 描述：
 * 为了能够将map结构保存到mysql，我们需要先将map转化为string类型，再保存到数据库。
 * 请实现两个方法：
            （1）一个为将key,value都是string类型的map转化为string；
            （2）另一个为将被转化的string反转为key,value都是string类型的map;
 * 要求：string的标准自由定义，但不允许直接使用json的标准.
 */

/**
 * Definition for a map struct.
 * struct Map {
 *     String key;
 *     String value;
 * };
 */
public class SerializeDeserialize {
    /**
     * 序列化：对map（String，String类型）转换为string，方便保存到数据库
     * @param map  入参，要序列化的map数组
     * @param str  序列化的字符串结果
     * @return
     */
    public static String rserialize(Map map, String str) {
        if (map == null) {
            str += "None,";
        } else {
            Iterator it = map.keySet().iterator();  // Set类型的key值集合，并转换为迭代器
            while(it.hasNext()){
                String key = (String) it.next();   // 获取 key 值
                str += key + ",";
                str += map.get(key)+",";
            }
        }
        return str;
    }
    public  static String serialize(Map map) {
        return rserialize(map, "");
    }

    /**
     * 反序列化：将上述序列化后的string还原为map(string，string类型)
     * @param strl  入参，序列化后的字符串
     * @return
     */
    public static Map rdeserialize(List<String> strl) {
        if (strl.get(0).equals("None")) {
            strl.remove(0);
            return null;
        }
        Map map = new HashMap();
        for(int i=0;i<strl.size();i+=2){
            map.put(strl.get(i),strl.get(i+1));
        }
        return map;
    }
    public static Map deserialize(String data) {
        String[] data_array = data.split(",");
        List<String> data_list = new LinkedList<String>(Arrays.asList(data_array));
        return rdeserialize(data_list);
    }

    //测试
    public static void main(String[] args){
        Map map=new HashMap<Integer,String>();
        map.put(1,"test01");
        map.put("2","test02");
        map.put("3","test03");

        String str=serialize(map);
        System.out.println(str);
        Map outmap = deserialize(str);
        System.out.println(outmap);
    }
};