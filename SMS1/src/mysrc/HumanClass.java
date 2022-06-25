package mysrc;

import list.LoopClass;
import list.SingletonClass;

import java.util.Scanner;

public abstract class HumanClass {
    Scanner sc = new Scanner(System.in);
    String name;
    String id;

    public void input() {
        String tempId;//임시로 학번을 담는다

        boolean isIdDup;//학번중복확인변수 true:중복있음, false:중복없음

        System.out.println("학번과 이름을 입력합니다.");
        System.out.print("이름 : ");
        name = sc.next();

        while (true) {
            isIdDup=false;//초기화

            System.out.print("학번 : ");
            tempId = sc.next();

            SingletonClass s = SingletonClass.getInstance();
            System.out.print("학번 중복을 검사합니다. ");

            for (int i = 0; i < s.mlist.size(); i++) {
                if (s.mlist.get(i).getId().equals(tempId)) {
                    System.out.println("중복된 학번입니다. 다시 입력해주세요.");
                    isIdDup=true;//중복있음
                }
            }

            if (isIdDup==false) { //중복이 없는 경우
                id = tempId;
                System.out.println("(검사 완료)");
                break;
            }

        }
    }

    public void print() {//이름과 학번을 출력
        System.out.println("--------------------------------");
        System.out.println("이름 : " + name);
        System.out.println("학번 : " + id);
    }

    public void setName(String name) {this.name = name;}
    public String getName() { return name;}

    public void setId(String id) { this.id = id;}
    public String getId() { return id; }

    public String output() {
        return name;
    }

}
