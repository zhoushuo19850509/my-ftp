package com.nbcb.athena.ftpdemo.test;

public class StringTest {
    public static void main(String[] args) {
        String currentName = "���仯 / ��\"�˿�ӯ��\"��\"�˿ڿ���\" _ Lao ling hua / cong \"ren kou ying li\" dao \"ren kou kui sun\"";


        currentName = currentName.replace("\\","-");
        currentName = currentName.replace("/","-");
        currentName = currentName.replace(":","-");
        currentName = currentName.replace("\"","-");
        currentName = currentName.replace("?","-");
        currentName = currentName.replace("<","-");
        currentName = currentName.replace(">","-");
        currentName = currentName.replace("|","-");

        System.out.println(currentName);

    }
}
