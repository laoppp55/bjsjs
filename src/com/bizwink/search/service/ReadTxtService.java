package com.bizwink.search.service;


import com.bizwink.search.domain.SearchWord;
import com.bizwink.search.persistence.SearchWordMapper;
import com.bizwink.search.util.PinyinDemo;
import com.bizwink.search.util.SearchConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.List;

@Service
public class ReadTxtService {

    @Autowired
    private SearchWordMapper searchWordMapper;
    private static SearchWordMapper searchWordMapperstatic;

    @PostConstruct
    public void init(){
        searchWordMapperstatic = searchWordMapper;
    }

    private static String txtPath=SearchConfig.getInstance().getTxtpathConfig();
    private static String txtName=SearchConfig.getInstance().getTxtnameConfig();

    private static String tabootxtName=SearchConfig.getInstance().getTabooTxtnameConfig();

    /**
     * 读入TXT文件
     */
    public static void readFile() {
        String pathname = txtPath+txtName;
        try (InputStreamReader isr = new InputStreamReader(new FileInputStream(pathname), "UTF-8");
             BufferedReader br = new BufferedReader(isr);// 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String line;
            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                System.out.println("启动读取关键词文件");
                insertSearchWord(line);
               // System.out.println(line);
            }
            FileWriter fileWriter =new FileWriter(pathname);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void insertSearchWord(String cname){
        //System.out.println("cname="+cname);
        SearchWord searchWord= searchWordMapperstatic.getSearchWord(cname);
        if(searchWord ==null){
            String ename = PinyinDemo.ToPinyin(cname);
            String sname = PinyinDemo.ToFirstChar(cname);
            searchWord = new SearchWord();
            searchWord.setCname(cname);
            searchWord.setEname(ename);
            searchWord.setSname(sname);
            searchWord.setNum(1);
            searchWordMapperstatic.insertSearchWord(searchWord);
            //System.out.println("插入新数据");
        }else{
            searchWordMapperstatic.updateSearchWordNum(searchWord.getId());
            //System.out.println("更新数据，次数+1");
        }

    }

    /**
     * 写入TXT文件
     */
    public static void writeFile(String word) {
        try {

            File writeNamedir = new File(txtPath);
            if (!writeNamedir.exists()) {
                writeNamedir.mkdirs();
            }
            File writeName = new File(txtPath+txtName);
            if(writeName.exists())
                writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            try

            {
                System.out.println("写入TXT文件");
                //String path = "D:\\test.txt";
                //out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "GBK"));
                BufferedWriter out = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(writeName,true),"UTF-8"));
                out.write(word+"\r\n");
                out.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //写入敏感词文件
    public static void writetabooFile(List<SearchWord> list) {
        try {
            File writeNamedir = new File(txtPath);
            if (!writeNamedir.exists()) {
                writeNamedir.mkdirs();
            }
            File writeName = new File(txtPath+tabootxtName);
            if(writeName.exists())
                writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            try
            {
                FileWriter fileWriter =new FileWriter(txtPath+tabootxtName);
                fileWriter.write("");
                fileWriter.flush();
                fileWriter.close();
                //String path = "D:\\test.txt";

                BufferedWriter out = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(writeName,true),"UTF-8"));
                for(int i=0;i<list.size();i++){
                    SearchWord searchWord=(SearchWord)list.get(i);
                    out.write(searchWord.getCname()+"\r\n");
                }
                //out.write(word+"\r\n");
                out.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //查找敏感词
    public static void getTabooWord(){
        System.out.println("读取敏感词");
        List<SearchWord> searchWordList= searchWordMapperstatic.getTabooWord();
        writetabooFile(searchWordList);
        /*for(int i=0; i<searchWordList.size();i++){
            SearchWord searchWord = (SearchWord)searchWordList.get(i);
            writetabooFile(searchWord.getCname());
        }*/
    }


    //读取敏感词
    public static boolean readtabooFile(String word) {
        String pathname = txtPath+tabootxtName;
        boolean flag = false;
        try (InputStreamReader isr = new InputStreamReader(new FileInputStream(pathname), "UTF-8");
             BufferedReader br = new BufferedReader(isr);// 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                System.out.println("读取敏感词文件");
                if(line.equals(word)  || word.indexOf(line)!=-1){
                    return true;
                }
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static void main(String args[]) {
        // writeFile("石景山222");
        //readFile();
        //getTabooWord();

    }

}
