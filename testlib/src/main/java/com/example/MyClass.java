package com.example;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MyClass {
    public static void main(String[] args) throws IOException {
        writeToFile();
        readFromFile();
    }

    private static void readFromFile() {
        InputStream inputStream = null;

        try {
            inputStream = new BufferedInputStream(new FileInputStream(new File("test.txt")));

            // 判断该输入流是否支持mark操作
            if (!inputStream.markSupported()) {
                System.out.println("mark/reset not supported!");
                return;
            }

            int ch;
            int count = 0;
            boolean marked = false;
            while ((ch = inputStream.read()) != -1) {
                System.out.print("." + ch);

                if ((ch == 4) && !marked) {
                    // 在4的地方标记位置
                    inputStream.mark(6);
                    marked = true;
                }

                if (ch == 8 && count < 2) {
                    // 重设位置到4
                    inputStream.reset();
                    count++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void writeToFile() {
        OutputStream output = null;
        try {
            output = new BufferedOutputStream(new FileOutputStream(new File("test.txt")));
            byte[] b = new byte[20];
            for (int i = 0; i < 20; i++)
                b[i] = (byte) i;
            // 写入从0到19的20个字节到文件中
            output.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
