package keshe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import static Print.Print.print;
import static Print.Print.println;

/**
 * Created by zyf on 2016/10/26.
 */
public class LLRW {
    public  static ArrayList<String> nodes = new ArrayList<>();
    public  static ArrayList<String> alpha = new ArrayList<>();
    public  static String startNode;
    public  static ArrayList<String> endNode=new ArrayList<>();
    public  static String[][] ponits;
    public static void remove(){
        nodes.clear();
        alpha.clear();
        endNode.clear();
    }
    public static void readLLRW(String filePath)throws IOException{

        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
        String string;
        if ((string=bufferedReader.readLine())!=null){
            String str =string.split("\\(|\\)")[1];
//            println(string.split("\\(|\\)")[1]);
            String[] nodedatas=str.split(",δ,")[0].split("\\},\\{")[0].trim().split("\\{|,");
            String[] alphas=str.split(",δ,")[0].split("\\},\\{")[1].split(",|\\}");
            for (String item:nodedatas) {
//                println(item);
                if (item.equals(""))
                    continue;
                nodes.add(item);
            }
            for (String item:alphas) {
//                println(item);
                if (item.equals(""))
                    continue;
                alpha.add(item);
            }
//            println(str.split(",δ,")[1].substring(0,1));
            startNode=str.split(",δ,")[1].substring(0,1);
//            println(str.split(",δ,")[1].substring(2,str.split(",δ,")[1].length()).split("\\{|\\}")[1]);
            String[] endNodeX = str.split(",δ,")[1].substring(2,str.split(",δ,")[1].length()).split("\\{|\\}")[1].split(",");
            for (String item:endNodeX) {
//                println(item);
                endNode.add(item);
            }
        }
        ponits=new String[nodes.size()][alpha.size()];
        while ((string=bufferedReader.readLine())!=null){
            String ENdNode = string.split("->")[0];
            String[] llr = string.split("->")[1].split("\\|");
            for (String item :
                    llr) {
                int indexY;
                int indexX;
                if (!nodes.contains(String.valueOf(item.charAt(0)))){
                    throw new IOException("节点"+String.valueOf(item.charAt(0))+"不在节点集合里");
                }
                else {
                    indexY=nodes.indexOf(String.valueOf(item.charAt(0)));
//                    println("indexY:"+indexY);
                }
                if (!alpha.contains(String.valueOf(item.charAt(1)))){
                    throw new IOException("字母"+String.valueOf(item.charAt(0))+"不在字母集合里");
                }else {
                    indexX=alpha.indexOf(String.valueOf(item.charAt(1)));
//                    println("indexX:"+indexX);
                }
                ponits[indexY][indexX]=ENdNode;
//                String a =String.valueOf(item.charAt(0));
            }
//            println(string);
        }
        println(" "+" "+alpha.get(0)+" "+alpha.get(1));
        for (int i =0;i<ponits.length;i++){
            print(nodes.get(i)+" ");
            for (int j=0;j<ponits[i].length;j++)
                print(ponits[i][j]+" ");
            println();
        }
        bufferedReader.close();
    }
    public static void main(String[] args)throws IOException{
        readLLRW("src/keshe/LLRW.txt");
    }
}
