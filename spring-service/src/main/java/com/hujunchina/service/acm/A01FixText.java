package com.hujunchina.service.acm;

import com.sun.codemodel.internal.JForEach;

import java.util.Scanner;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/16 9:23 下午
 * @Version 1.0
 * https://www.nowcoder.com/test/question/42852fd7045c442192fa89404ab42e92?pid=16516564&tid=34833039
 * AABBCCDD => AABCCD
 * WOOOOW => WOOW
 */
public class A01FixText {

    public static String fix(String str){
        StringBuilder sb = new StringBuilder();
        char tmp = str.charAt(0);
        int idx = 0;
        boolean first = false;
        boolean second = false;
        char[] arr = str.toCharArray();
        for(int i=0, j=0; j<str.length()-1;){
            if(first){
                if(arr[j]==arr[j+1]){
                    second=true;
                    for(int k=i; k<=j; k++){
                        sb.append(arr[k]);
                    }
                    first = false;
                    i=j+2;
                }else {
                    second=false;
                    j++;
                }
            }else{
                if(arr[j]==arr[j+1]){
                    first=true;
                }
                j++;
            }
        }
        if(arr[str.length()-2]!=arr[str.length()-1] || first==false){
            sb.append(arr[str.length()-1]);
        }
        return sb.toString();
    }

    public static String flipTriple(String str){
        char arr[] = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        int idx = 0;
        for (int i=0; i<str.length()-1; i++){
            if (arr[i] == arr[i+1]){
                idx++;
                if (idx>=3){
                    continue;
                }else{
                    sb.append(arr[i]);
                }
            }else{
                if (idx<2) {
                    sb.append(arr[i]);
                }
                idx = 0;
            }
            if (i == str.length()-2 && idx<2) {
                sb.append(arr[i+1]);
            }
        }
        return sb.toString();
    }

    public static String fix2(String str){
        // 只要处理两个的情况
        str = flipTriple(str);
        char arr[] = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        int i, j;
        char tmp;
        boolean clip = false;
        for(i=0; i<str.length()-1; i++){
            if (arr[i] == arr[i+1]){
                if (clip) { //上一组重复过了，这一组不能再重复
                    sb.append(arr[i]);
                    i++;
                    clip = false;
                }else {
                    sb.append(arr[i]);
                    clip = true;
                }
            }else{
                sb.append(arr[i]);
            }
        }
        if ( i==str.length()-1 && clip){
            sb.append(arr[i]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        int num;
        Scanner scanner = new Scanner(System.in);
        num = scanner.nextInt();
        for (int i = 0; i < num; i++) {
            String str = scanner.next();
            String fixStr = fix2(str);
            System.out.println(fixStr);
        }   
    }
}
