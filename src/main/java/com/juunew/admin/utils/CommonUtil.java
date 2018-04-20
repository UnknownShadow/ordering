package com.juunew.admin.utils;

import com.juunew.admin.entity.ClubsEntity;
import com.juunew.admin.entity.api.WithdrawsRecordsResp;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 普通常用工具类
 */
public class CommonUtil {


    /**
     * 判断是否为数字
     *
     * @param str
     * @return
     */
    public static int isNumeric(String str) {
        if (str.length() > 8) return 0;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return 0;
            }
        }
        return Integer.parseInt(str);
    }


    /**
     * list中 根据 实体类中的日期进行排序
     *
     * @param list 需要排序的list
     */
    public static void listSort(List<WithdrawsRecordsResp> list) {

        Collections.sort(list, new Comparator<WithdrawsRecordsResp>() {
            @Override
            public int compare(WithdrawsRecordsResp o1, WithdrawsRecordsResp o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                try {
                    Date dt1 = format.parse(o1.getCreateTime());
                    Date dt2 = format.parse(o2.getCreateTime());
                    if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });

    }


    /**
     *  按实体类中的数值进行排序
     * @param list  需要排序的list
     */
    public static void listIntSort(List<ClubsEntity> list){

        Collections.sort(list, new Comparator<ClubsEntity>() {

            /*
             * int compare(Student o1, Student o2) 返回一个基本类型的整型，
             * 返回负数表示：o1 大于o2，
             * 返回0 表示：o1和o2相等，
             * 返回正数表示：o1大于o2。
             */
            @Override
            public int compare(ClubsEntity o1, ClubsEntity o2) {

                //进行降序排列
                if(o1.getToday_game_times() < o2.getToday_game_times()){
                    return 1;
                }
                if(o1.getToday_game_times() == o2.getToday_game_times()){
                    return 0;
                }
                return -1;
            }
        });
    }




    /**
     * 读取txt文件
     *
     * @param path
     * @return
     */
    public static String readTxt(String path) {
        String result = "";
        File file = new File(path);
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "gbk");
            BufferedReader br = new BufferedReader(reader);
            String s = null;
            while ((s = br.readLine()) != null) {
                result = result + s;
            }
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    /*try {
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));
        String s = null;
        while((s=br.readLine())!=null){
            result = result + "\n" + s;
        }
    } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }*/
        return result;
    }


    /**
     * 生成当天的日期
     *
     * @param pattern 日期格式，如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String creationDate(String pattern) {
        String date = new SimpleDateFormat(pattern).format(new Date());
        return date;
    }


    /**
     * 随机生成自义定长度的字符串（大写+小写+数字）
     *
     * @param length 需要生成的长度
     * @return
     */
    private static String getRandomString(int length) {
        //随机字符串的随机字符库
        String keyString = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int len = keyString.length();

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < length; i++) {
            buffer.append(keyString.charAt((int) (Math.round(Math.random() * (len - 1)))));
        }

        return buffer.toString();
    }



}