package com.snl.blogbooster;

public class SynchTest {
    private static String mMessage;
    private static String keyword;

    public static void main(String[] agrs) {
        SynchTest temp1 = new SynchTest();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                temp1.callMe1("Thread1","banana");
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                temp1.callMe1("Thread2","apple");
            }
        }).start();
    }

    public void callMe1(String whoCallMe, String text) {
        setKeyword(text);
        mMessage = whoCallMe;
        try {
            long sleep = (long) (Math.random() * 100);
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!mMessage.equals(whoCallMe)) {
            System.out.println("callMe1> " + whoCallMe + " | " + mMessage + " | "+keyword);
        }
    }
    public static void setKeyword(String text)
    {
        keyword= text;
    }
  }

