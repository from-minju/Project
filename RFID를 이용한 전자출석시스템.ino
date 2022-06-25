#include <SPI.h>
#include <MFRC522.h>
#include <LiquidCrystal_I2C.h>

LiquidCrystal_I2C lcd(0x27, 16, 2);

#define SS_PIN 10 //SPI통신을 위한 SS(chip select)핀 설정
#define RST_PIN 9 //리셋 핀 설정
#define BUZZER 6 //버저가 연결된 핀
 
MFRC522 rfid(SS_PIN, RST_PIN); // rfid 이름으로 클래스 객체 선언

MFRC522::MIFARE_Key key; 

byte nuidPICC[4];//카드 ID들을 저장(비교)하기 위한 배열(변수)선언

void setup() { 
  Serial.begin(9600);
  SPI.begin(); // Init SPI bus
  rfid.PCD_Init(); // Init MFRC522 
  pinMode(BUZZER, OUTPUT); //버저 핀 설정

  lcd.init();
  lcd.backlight();
  lcd.clear();
  lcd.setCursor(1,0);
  lcd.print("Hello, world!");

  for (byte i = 0; i < 6; i++) { //ID값 초기화
    key.keyByte[i] = 0xFF;
  }

  //plx-daq명령어
  Serial.println("CLEARDATA");
  Serial.println("LABEL, time, studentID, major, name"); 

}


 
void loop() {
  lcd.setCursor(0,0);
  lcd.print("Tag your card");
  
  // 새 카드 접촉이 있을 때만 다음 단계로 넘어감
  if ( ! rfid.PICC_IsNewCardPresent())
    return;

  // 카드 읽힘이 제대로 되면 다음으로 넘어감
  if ( ! rfid.PICC_ReadCardSerial())
    return;

  //현재 접촉되는 카드 타입을 읽어와 모니터에 표시
  Serial.print(F("PICC type: "));
  MFRC522::PICC_Type piccType = rfid.PICC_GetType(rfid.uid.sak);
  Serial.println(rfid.PICC_GetTypeName(piccType));

  // MIFARE 방식의 카드인지 확인
  if (piccType != MFRC522::PICC_TYPE_MIFARE_MINI &&  
    piccType != MFRC522::PICC_TYPE_MIFARE_1K &&
    piccType != MFRC522::PICC_TYPE_MIFARE_4K) {
    Serial.println(F("Your tag is not of type MIFARE Classic."));
    return;
  }

  // 새 카드가 인식된 경우
  if (rfid.uid.uidByte[0] != nuidPICC[0] || 
    rfid.uid.uidByte[1] != nuidPICC[1] || 
    rfid.uid.uidByte[2] != nuidPICC[2] || 
    rfid.uid.uidByte[3] != nuidPICC[3] ) {
      Serial.println(F("A new card has been detected."));
      tone(BUZZER, 1500, 100);

      String id;
      
     // 고유아이디(UID)값을 저장
      for (byte i = 0; i < 4; i++) {
        nuidPICC[i] = rfid.uid.uidByte[i];
        id += String(rfid.uid.uidByte[i]); 
      };
      
      Serial.println("id: " + id);


      // 학생정보
      if(id.compareTo("105217123143")==0)
        printOnExcel("201904005", "ComputerSoftware", "KimSumin");
      else if(id.compareTo("93122138174")==0)
        printOnExcel("201904001", "ComputerSoftware", "Kang Minju");
       else if(id.compareTo("1015024663")==0)
        printOnExcel("201704025", "ComputerSoftware", "Jung Jiwon");
      else{
        Serial.println("등록되지 않은 카드입니다.");
        lcd.clear();
        lcd.setCursor(1,0);
        lcd.print("Unregistered");
        lcd.setCursor(1,1);
        lcd.print("card");
        delay(2000);
        lcd.clear();
      }
            
  }
  //연속으로 동일한 카드를 접촉하면 다른 처리 없이 '이미 인식된 카드'라는 메세지를 출력
  else {
    Serial.println(F("Card read previously."));

    //에러음 출력
    tone(BUZZER, 523, 100);
    delay(300);
    tone(BUZZER, 523, 100);

    //이미 등록된 카드라고 lcd에 출력
    lcd.clear();
    lcd.setCursor(1,0);
    lcd.print("Card read");
    lcd.setCursor(1,1);
    lcd.print("previously");
    delay(2000);
    lcd.clear();
  }

  // Halt PICC
  rfid.PICC_HaltA();

  // Stop encryption on PCD
  rfid.PCD_StopCrypto1();
}

//엑셀에 출석정보를 등록시키는 명령어를 출력하고 lcd에 환영문구를 띄움
void printOnExcel(String studentID, String major, String sname){
  Serial.print("DATA,TIME,");
  Serial.print(studentID + ",");
  Serial.print(major + ",");
  Serial.println(sname + ",");

  lcd.clear();
  lcd.setCursor(1,0);
  lcd.print("Welcome");
  lcd.setCursor(0,1);
  lcd.print(sname);
  delay(2000);
  lcd.clear();
  
}
