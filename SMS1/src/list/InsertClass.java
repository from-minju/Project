package list;

import mysrc.BasicClass;
import mysrc.HumanClass;

import java.util.Scanner;

public class InsertClass {
    HumanClass h = null;
    int num;
    Scanner scan = new Scanner(System.in);

    public void insert() {
        SingletonClass s = SingletonClass.getInstance();

        System.out.println("학생기본정보를 입력합니다.");
        h = new BasicClass();
        h.input();

        s.mlist.add(h);


    }
}
