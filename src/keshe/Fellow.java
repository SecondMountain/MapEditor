package keshe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Print.Print.print;
import static Print.Print.println;

/**
 * Created by zyf on 2016/10/26.
 */
public class Fellow {
    public static ArrayList<String> read()throws IOException{
        ArrayList<String> list = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader( new FileReader(new File("src/keshe/llr.txt")));
        String str;
        while ((str=bufferedReader.readLine())!=null){
            list.add(str);
        }
        return list;
    }
    public static Map<String,ArrayList<String>> readF()throws IOException{
        Map<String,ArrayList<String>> map =new HashMap<String,ArrayList<String>>();

        BufferedReader bufferedReader = new BufferedReader( new FileReader(new File("src/keshe/first.txt")));
        String str;
        while ((str=bufferedReader.readLine())!=null){
            ArrayList<String> list = new ArrayList<>();
            list.add(str.split(",")[1]);
            list.add(str.split(",")[2]);
            map.put(str.split(",")[0],list);
        }
        return map;
    }
    public static void main(String[] args)throws IOException{
        Map<String,ArrayList<String>> map = readF();
        String[] keys={"E","E'","T","T'","F"};
        map.values().forEach((item)->{
            println(item);
//            map.get(item).stream().forEach((items)->{println(items);});
        });
        map.keySet().forEach((item)->{
            print(item);
            map.get(item).stream().forEach((items)->{print(items);});
            println();
        });
        ArrayList<String> list=new Fellow().read();
        list.stream().forEach((String item)->{
            println(item);
        });
    }
}
