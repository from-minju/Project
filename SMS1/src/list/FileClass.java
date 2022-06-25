package list;

import mysrc.BasicClass;
import mysrc.HumanClass;

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class FileClass {
    File newfile = new File("C:\\temp\\SMS1.txt"); // 파일 포인터 생성
    Scanner scan = new Scanner(System.in);
    String number, name, id;
    double korean, english, math, average;
    int age;
    HumanClass h;


    public void menu() {
        System.out.println("1. 파일 저장 2. 파일 불러오기");
        int i = scan.nextInt();
        if (i == 1)
        {
            save();
        } else if (i == 2)
        {
            load();
        }
    }

    public void save() {
        SingletonClass s = SingletonClass.getInstance();
        try
        {
            FileWriter fwriter = new FileWriter(newfile);
            for (int i = 0; i < s.mlist.size(); i++)
            {
                String str = s.mlist.get(i).output();
                fwriter.write(str);
            }
            fwriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public void load() {
        SingletonClass s = SingletonClass.getInstance();
        newfile.setWritable(true);
        int count = 0;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(newfile));
            String str;
            while ((str = br.readLine()) != null)
            {
                count++;
            }
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        String str[] = new String[count];
        for (int i = 0; i < str.length; i++)
        {
            str[i] = new String();
        }
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(newfile));
            for (int i = 0; i < str.length; i++)
            {
                try
                {
                    if ((str[i] = br.readLine()) != null)
                    {
                        StringTokenizer st = new StringTokenizer(str[i], " ");
                        String[] subStr = new String[st.countTokens()];
                        for (int j = 0; j < subStr.length; j++)
                        {
                            subStr[j] = st.nextToken();
                            if (j == 0)//학번
                            {
                                id = subStr[j];
                            } else if (j == 1) //이름
                            {
                                name = subStr[j];
                            } else if (j == 2)//생년월일
                            {
                                number = subStr[j];
                            } else if (j == 3)//나이
                            {
                                age = Integer.parseInt(subStr[j]);
                            } else if (j == 4)//국어성적
                            {
                                korean = Double.parseDouble(subStr[j]);
                            } else if (j == 5)//영어성적
                            {
                                english = Double.parseDouble(subStr[j]);
                            } else if (j == 6)//수학성적
                            {
                                math = Double.parseDouble(subStr[j]);
                            }
                            else if (j == 7)//성적평균
                            {
                                average = Double.parseDouble(subStr[j]);
                            }
                        }
                        h = new BasicClass();
                        BasicClass b = (BasicClass)h;
                        b.setId(id);
                        b.setName(name);
                        b.setNumber(number);
                        b.setAge(age);
                        b.setKorean(korean);
                        b.setEnglish(english);
                        b.setMath(math);
                        b.setAverage(average);
                        s.mlist.add(b);

                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch (FileNotFoundException e) {

        }
        for (int i = 0; i < str.length; i++) {
            System.out.println(str[i]);
        }
    }

}
