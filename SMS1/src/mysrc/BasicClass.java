package mysrc;

import java.util.Calendar;

public class BasicClass extends HumanClass {

    Calendar cal = Calendar.getInstance();
    String birth;
    int year, month, date;
    int age;

    double korean;
    double english;
    double math;
    double average;

    public String getBirth() { return birth; }
    public void setBirth(String birth )
    {
        this.birth = birth;
    }

    public void setting()//나이 설정
    {
        year = Integer.parseInt(birth.substring(0, 2));
        month = Integer.parseInt(birth.substring(2, 4));
        date = Integer.parseInt(birth.substring(4, 6));
        if (year > 20)
        {
            age = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt("19" + birth.substring(0, 2)) + 1;
        } else
        {
            age = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt("20" + birth.substring(0, 2)) + 1;
        }
    }

    public void input() {
        super.input();

        System.out.println("기본정보를 입력합니다.");
        System.out.print("생년월일(YYMMDD) : ");
        birth = sc.next();
        setting();//나이 설정

        System.out.println("성적을 입력합니다.");
        System.out.print("국어 : ");
        korean = sc.nextDouble();
        System.out.print("영어 : ");
        english = sc.nextDouble();
        System.out.print("수학 : ");
        math = sc.nextDouble();
        average = (korean + english + math) / 3;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age )
    {
        this.age = age;
    }
    public double getAverage() { return average;}
    public String getNumber() { return birth; }
    public void setNumber(String number )
    {
        this.birth = number;
    }
    public String convert(int x )
    {
        return x < 10 ? "0" + x : "" + x;
    }
    public void setKorean(double korean) {this.korean = korean;}
    public void setEnglish(double english) {this.english = english;}
    public void setMath(double math) {this.math = math;}
    public void setAverage(double average) {this.average = average;}

    public void print()
    {
        super.print();
        setting();//나이 설정
        System.out.println("생년월일 : "+convert(year )+"년 " + convert(month) + "월 "+convert( date)+"일" + " (" + age + "세)");

        System.out.println("국어 : " + korean);
        System.out.println("영어 : " + english);
        System.out.println("수학 : " + math);
        System.out.println("평균 : " + Math.round(average*100)/100.0);

    }

    public String output() {
        String str = (id + " " + name + " " + birth + " " + age + " " + korean + " " + english + " " + math + " " + average + "\n" );
        return str;
    }


}
