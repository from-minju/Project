package list;

import java.util.Scanner;

public class SelectClass {
    String id;
    int choice;
    Scanner scan = new Scanner(System.in);

    public void select() {

        SingletonClass s = SingletonClass.getInstance();
        System.out.print("검색할 학생의 학번을 입력하세요 : ");
        id = scan.next();
        for (int i = 0; i < s.mlist.size(); i++) {
            if (s.mlist.get(i).getId().equals(id)) {
                s.mlist.get(i).print();
            }
        }
    }

}
