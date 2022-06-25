package list;

import java.util.Scanner;

public class LoopClass {

    int choice;
    Scanner scan = new Scanner(System.in);

    InsertClass insert = new InsertClass();
    SelectClass select = new SelectClass();
    UpdateClass update = new UpdateClass();
    DeleteClass delete = new DeleteClass();
    PrintClass print = new PrintClass();
    FileClass file = new FileClass();

    public void loop() {
        while(true) {
            System.out.println("1. 추가  2. 검색  3. 수정  4. 삭제  5. 출력  6. 파일  7. 나가기");
            choice = scan.nextInt();
            if(choice == 1) {//학생 추가
                insert.insert();
            }else if (choice == 2) {//학생 검색
                select.select();
            }else if (choice == 3) {//학생 수정
                update.update();
            }else if (choice == 4) {//학생 삭제
                delete.delete();
            }else if (choice == 5) {//학생 출력
                print.menu();
            }else if (choice == 6) {//파일
                file.menu();
            }else if (choice == 7) {// 나가기
                break;
            }else {
                System.out.println("다시 입력해주세요.");
            }
       }
    }
}
