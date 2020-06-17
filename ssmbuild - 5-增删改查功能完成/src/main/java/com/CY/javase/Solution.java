package com.CY.javase;

import java.util.Scanner;

public class Solution {
    public static void main(String[] args){
        Scanner input= new Scanner(System.in);
        String handAndFoot =input.next();
        System.out.println(doCalc(handAndFoot));

    }

    public static String doCalc (String headAndFoot) {
        // write code here
        int head=Integer.parseInt(String.valueOf(headAndFoot.charAt(1)));
        int foot=Integer.parseInt(String.valueOf(headAndFoot.charAt(3)));
        int x,y;
        System.out.println(headAndFoot.charAt(1)+"---"+headAndFoot.charAt(3));
        System.out.println(head+"---"+foot);
        if(foot%2==0){
            x=2*head-foot/2;
            y=head-x;
            if(x<1||y<1){
                return "NODATA";
            }
            return "\""+x+","+y+"\"";
        }

        return "NODATA";
    }


}

