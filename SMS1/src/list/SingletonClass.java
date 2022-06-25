package list;

import mysrc.HumanClass;
import java.util.LinkedList;

public class SingletonClass {

    static SingletonClass single;
    public LinkedList<HumanClass> mlist;

    private SingletonClass() {
        mlist = new LinkedList<>();
    }

    public static SingletonClass getInstance() {
        if (single == null)
            single = new SingletonClass();
        return single;
    }
}

