package list;

import java.util.Scanner;

public class DeleteClass {
    String id;
    Scanner scan = new Scanner(System.in);

    public void delete() {
        SingletonClass s = SingletonClass.getInstance();
        System.out.print("삭제할 학번을 입력하세요 : ");
        id = scan.next();
        for (int i=0; i<s.mlist.size(); i++) {
            if (s.mlist.get(i).getId().equals(id)) {
                s.mlist.remove(i);
                break;
            }
        }
    }
}
