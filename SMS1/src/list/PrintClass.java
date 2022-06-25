package list;

import mysrc.BasicClass;
import mysrc.HumanClass;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

public class PrintClass {
    int choice;
    Scanner scan = new Scanner(System.in);

    public void print() {
        SingletonClass s = SingletonClass.getInstance();
        for (int i=0; i<s.mlist.size(); i++) {
            s.mlist.get(i).print();
        }
    }

    public void sortScoreAvg() {//성적순
        LinkedList<BasicClass> mlist = new LinkedList<>();
        SingletonClass s = SingletonClass.getInstance();
        System.out.println("성적평균순으로 정렬합니다.");
        for (int i = 0; i < s.mlist.size(); i++) {
            mlist.add((BasicClass) s.mlist.get(i));
        }
        Collections.sort(mlist, (o1, o2) -> {
            BasicClass e1 = (BasicClass) o1;
            BasicClass e2 = (BasicClass) o2;
            return ((Comparable<Double>) e2.getAverage()).compareTo(e1.getAverage());
        });
        for (int j =0; j < mlist.size(); j++) {
            mlist.get(j).print();
        }
    }

    public void sortName() { //이름순
        SingletonClass s = SingletonClass.getInstance();
        System.out.println("이름순으로 정렬합니다.");

        Collections.sort(s.mlist, new Comparator<HumanClass>() {
            @Override
            public int compare(HumanClass o1, HumanClass o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        for (int i=0; i<s.mlist.size(); i++) {
            s.mlist.get(i).print();
        }

    }

    public void sortId() { //학번순
        SingletonClass s = SingletonClass.getInstance();
        System.out.println("학번순으로 정렬합니다.");

        Collections.sort(s.mlist, new Comparator<HumanClass>() {
            @Override
            public int compare(HumanClass o1, HumanClass o2) {
                return o1.getId().compareToIgnoreCase(o2.getId());
            }
        });
        for (int i=0; i<s.mlist.size(); i++) {
            s.mlist.get(i).print();
        }

    }

    public void menu() {
        SingletonClass s = SingletonClass.getInstance();
        System.out.println("1. 이름순  2. 학번순  3. 성적평균순");
        choice = scan.nextInt();

        if(choice == 1) {
            sortName();
        } else if (choice == 2) {
            sortId();
        } else if (choice == 3) {
            sortScoreAvg();
        }
    }
}
