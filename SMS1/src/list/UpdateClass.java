package list;

import mysrc.HumanClass;
import java.util.Scanner;

public class UpdateClass {
    String id;
    Scanner scan = new Scanner(System.in);
    HumanClass h;

    public void update() {
        SingletonClass s = SingletonClass.getInstance();
        System.out.print("수정할 학생의 학번을 입력하세요 : ");



        id = scan.next();
        for (int i=0; i<s.mlist.size(); i++) {
            if (s.mlist.get(i).getId().equals(id)) {
                s.mlist.get(i).setId("");//학번을 리셋시켜서 중복안되게 바꿈
                s.mlist.get(i).input();
            }
        }

    }
}
