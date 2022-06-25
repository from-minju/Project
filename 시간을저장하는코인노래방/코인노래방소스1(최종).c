
#define _CRT_SECURE_NO_WARNINGS    // fopen 보안 경고로 인한 컴파일 에러 방지
#define songs 51 //(songs)개의 음악파일 재생가능
#include <stdio.h>
#include <string.h>//문자열 함수
#include <time.h>//타이머
#include <windows.h>//화면클리어
#include <conio.h> //kbhit(), getch()
#include <fmod.h>//소리재생


FMOD_SYSTEM *g_System; //시스템 포인터 변수
FMOD_SOUND *g_Sound[songs]; //사운드 포인터 변수, 사운드 갯수만큼 사운드 포인터 선언(1대 1대응)
FMOD_CHANNEL *g_Channel[2]; //채널 포인터 변수
FMOD_BOOL IsPlaying;//변수 전역변수로 선언

void Init();//FMOD초기화 함수
void Update(char ch, int channel_num);//데이터 갱신 함수
void Release();//FMOD해제 함수
void gotoxy(int x, int y);//좌표이동 함수
void gotoxyprint(int x, int y, char* s);//좌표이동한 자리에 문자열 출력해주는 함수
void print_coin_noraebang(void);//노래방 시작 화면 출력해주는 함수
void open_txt(fp);//메모장 내용을 출력해주는 함수
extern void open_open_txt(int song_num);//메모장 경로 저장, 노래번호에 맞는 가사 파일을 open함
void timerf(void);//시간측정함수(타이머기능, 사용자가 노래부른 시간을 저장)

int result;//측정된 시간(초 단위), result=(시간측정종료시간 - 시간측정시작시간)
int timer = 0;//측정된 시간(분 단위), result를 60으로 나누어 분단위로 저장함


time_t start, end;//전역변수로 선언(다른 함수에서도 이 변수 사용하기 위해서)

int main(void)
{
	//fmod
	char ch = 0;

	//타이머 기능
	int timer = 0;
	double result;


	//변수 선언
	int total_money = 0; //잔액(임시)
	int song = total_money / 1000;//song: 몫
	int coin = 0; //coin: 충전한 돈
	static int coin_change = 4;  //coin_change: 관리자가바꿀수있음(1000원에 3곡인지 4곡인지...)
	int isn = 0;//index of song_num
	char member_name[10] = { 0 }; //회원 이름
	char member_id[20] = { 0 }; //회원 아이디
	char member_pw[20] = { 0 }; //회원 비밀번호   
	char user_id[20] = { 0 }; //입력받은 회원 아이디
	char user_pw[20] = { 0 }; //입력받은 회원 비밀번호
	char ad_id[20] = "1234abcd";//관리자 아이디
	char ad_pw[20] = "1234abcd";//관리자 비밀번호
	int login_counting = 0;//로그인 시도 횟수
	int menu_choice;//노래시작,충전,내정보열람 선택하는 메뉴
	int ad_menu_choice;//관리자로 로그인 후 선택하는 메뉴
	int song_num[300] = { 0 };//사용자가 선택한 노래 번호를 저장하는 배열, 300은 한번 실행으로 최대 부를 수 있는 노래 개수
	int menu3_choice;//내정보열람 종료할때 입력받는 변수
	int remain_song = 0;//남아있는 곡 수
	extern char norae_name[songs][200];//노래방 노래목록저장된 배열 
	//변수 선언


	Init();//FMOD 초기화 함수

	while (1) {//첫번째 while문

	menu://go to문

		print_coin_noraebang();////노래방 시작 화면 출력해주는 함수

		int first_choice = getch();    // 입력 버퍼를 사용하지 않음, 화면에 키의 입력을 보여주지 않음

		switch (first_choice) {
		case '1'://로그인
		{
			while (1)
			{

				system("cls");
				printf("=======================\n");
				printf("      <로그인>\n");
				printf("=======================\n");

				printf(" 아이디 : ");
				scanf("%s", &user_id);//아이디를 입력받음
				printf(" 비밀번호 : ");
				scanf("%s", &user_pw);//비밀번호를 입력받음


				if ((strcmp(member_id, user_id) == 0) && (strcmp(member_pw, user_pw) == 0))//아이디와 비번이 모두 일치할 경우
				{
					system("cls");
					gotoxy(2, 2);//좌표이동
					printf(" 로그인에 성공하였습니다.\n");
					Sleep(1000);
					system("cls");

					while (1) {
						system("cls");

						printf("=======================\n");
						printf("  %s님 어서오세요~\n", member_name);
						printf("  코인 : %d곡\n", remain_song);
						printf("=======================\n");
						printf("  1. 노래 시작\n  2. 충전\n  3. 내 정보 열람\n  4. 노래 목록 보기\n  5. 로그 아웃\n");
						int menu_choice = getch();// 입력 버퍼를 사용하지 않음, 화면에 키의 입력을 보여주지 않음

						switch (menu_choice) {
						case '1'://노래 시작
						{
							system("cls");

							if (remain_song >= 1)//코인이 1개이상 있는 경우(노래부를 수 있는 경우)
							{
								printf("=======================\n");
								printf(" 노래번호 입력 : ");
								printf("\n=======================\n");
								gotoxy(17, 1);//커서이동, 좌표이동
								scanf("%d", &song_num[isn]);
								start = time(NULL); // 수행 시간 측정 시작(시간측정: 노래 시작~종료버튼 누를때까지)
								FMOD_System_PlaySound(g_System, FMOD_CHANNEL_FREE, g_Sound[song_num[isn]], 0, &g_Channel[song_num[isn]]);//특정 채널에 사운드를 출력해주는 함수


								printf("\nq: 종료, a:효과음(환호소리), u:볼륨업, d:볼륨다운\n\n");

								open_open_txt(song_num[isn]);//가사 출력


								ch = 0;

								while (ch != 'q')
								{
									if (_kbhit()) //입력했다면 실행(사용자가 키보드 눌렀는지 검사하는 함수, 누르면 true반환, 안누르면 false반환
									{
										ch = _getch();//getch():키보드의 입력을 받는 함수, 입력한 내용이 화면에 보이지 않는다.
										Update(ch, song_num[isn]);//소리 발생, 음량조절 함수
									}
									FMOD_System_Update(g_System);//스트리밍방식
								}
								if (ch = 'q')//종료버튼을 눌렀을 때
								{
									FMOD_Channel_Stop(g_Channel[song_num[isn]]);//음악 정지
									end = time(NULL); // 시간 측정 끝
									system("cls");
								}




								remain_song = remain_song - 1;//남은 곡 계산
								isn++;//배열 인덱스 증가시킨다.
							}
							else//코인이 없는 경우
							{
								printf("\n돈이 부족합니다. 코인을 충전해주세요.\n");
								Sleep(1000);
								system("cls");
							}
							break;
						}
						case '2'://충전
						{

							while (1)
							{
								system("cls");
								printf("=======================\n");
								printf("        <충전>\n");
								printf("=======================\n");
								printf(" 충전할 금액을 입력하세요(1000원 단위로):");
								scanf_s("%d", &coin);//충전할 금액을 입력받음

								if (coin % 1000 == 0)//입력받은 금액이 1000원으로 나누어 떨어진다면(1000원 단위라면)
								{
									song = (coin / 1000);//몫=충전금액/1000
									remain_song = remain_song + song * coin_change;//충전 후 곡 수=몫 * coin_change(3곡인지 4곡인지)
									printf(" 충전에 성공하였습니다.\n");
									Sleep(1000);

									break;
								}
								else//1000원 단위로 입력하지 않았을 경우
								{
									printf("\n1000원 단위로 다시 입력해주세요\n");
									Sleep(1000);

								}
							}
							break;
						}
						case '3'://내 정보 열람
						{
							system("cls");

							printf("\n b : 메뉴로 돌아가기\n\n");
							printf("=======================\n");
							printf("      <회원정보>\n");
							printf("=======================\n");
							printf(" 이름 : %s (%s)\n 코인 : %d곡\n", member_name, member_id, remain_song);//이름과 남은 곡 수를 출력한다.
							timerf();// 이용 시간 출력 함수

							printf("\n <현재까지 부른 곡 목록>\n");
							printf("==========================================================================\n");

							for (int a = 0; a < 300; a++)//지금까지 부른 곡 목록 출력
							{
								if (song_num[a] > 0)//노래를 불렀을 경우만 출력(나머지는 0으로 초기화되어있기때문에 0이상만 출력)
								{
									printf("%3d | %s \n", a + 1, norae_name[song_num[a]]);//ex)  1| 001. 불장난-블랙핑크
								}
							}


							//메뉴로 돌아가기 기능
							menu3_choice = getch();
							switch (menu3_choice) {
							case 'b'://
							{
								system("cls");//화면 지우기
								break; //다시 메뉴로 돌아간다.
							}
							}//siwtch문 닫는 괄호

							break;
						}
						case '4'://노래 목록 보기
						{
							system("cls");
							printf("\n\n b : 메뉴로 돌아가기\n");

							printf("=======================\n");
							printf("      <노래 목록>\n");
							printf("=======================\n");

							open_open_txt(0);//노래 목록을 불러와 출력한다

							menu3_choice = getch();
							switch (menu3_choice) {
							case 'b':
							{
								system("cls");//화면 지우기
								break; //다시 메뉴로 돌아간다.
							}
							}//siwtch문 닫는 괄호

							break;
						}
						case '5'://로그아웃
						{
							system("cls");
							goto menu;//menu로 돌아감
						}
						default://1,2,3,외에 다른 수를 입력했을경우
						{
							printf("\n다시 입력해주세요.\n");
							Sleep(1000);
							system("cls");
							break;
						}
						}//switch문 닫는 괄호


					}//while문 닫는 괄호


				}
				else if ((strcmp(ad_id, user_id) == 0) && (strcmp(ad_pw, user_pw) == 0))//관리자로 로그인했을경우
				{
					system("cls");
					printf("\n  관리자로 로그인하였습니다.\n");
					Sleep(1000);
					system("cls");

					printf("\n b : 메뉴로 돌아가기\n\n");
					printf("=======================\n");
					printf("      <요금변경>\n");
					printf("=======================\n");
					printf("  1. 1000원에 2곡\n");
					printf("  2. 1000원에 3곡\n");
					printf("  3. 1000원에 4곡\n");
					printf("  4. 1000원에 5곡\n");

					ad_menu_choice = getch();//선택을 입력받는다.

					switch (ad_menu_choice) {
					case '1':
					{
						coin_change = 2;
						printf(" 요금을 1000원에 2곡으로 변경하였습니다.\n");
						Sleep(1000);
						system("cls");
						break;
					}
					case '2':
					{
						coin_change = 3;
						printf(" 요금을 1000원에 3곡으로 변경하였습니다.\n");
						Sleep(1000);
						system("cls");
						break;
					}
					case '3':
					{
						coin_change = 4;
						printf(" 요금을 1000원에 4곡으로 변경하였습니다.\n");
						Sleep(1000);
						system("cls");
						break;
					}
					case '4':
					{
						coin_change = 5;
						printf(" 요금을 1000원에 5곡으로 변경하였습니다.\n");
						Sleep(1000);
						system("cls");
						break;
					}
					case 'b':
					{
						system("cls");//화면 지우기
						break; //다시 메뉴로 돌아간다.
					}
					}//switch문 닫는 괄호
					break;
				}
				else
				{

					printf("\n아이디 또는 비밀번호를 잘못 입력했습니다.");
					login_counting++;
					printf("(로그인 오류 %d회)\n", login_counting);
					Sleep(1000);

					if (login_counting >= 5)
					{
						printf("로그인 시도횟수를 넘어 프로그램을 종료합니다.\n");
						return 0;
					}

				}


			}
			break;
		}
		case '2'://회원가입
		{
			system("cls");
			printf("=======================\n");
			printf("      <회원가입>\n");
			printf("=======================\n");
			printf(" 이 름 : ");
			scanf("%s", &member_name);
			printf(" 아이디 : ");
			scanf("%s", &member_id);
			printf(" 비밀번호 : ");
			scanf("%s", &member_pw);
			system("cls");


			printf("\n  회원가입을 완료하였습니다.\n");
			Sleep(1000);
			system("cls");
			break;
		}
		case '3'://종료
		{
			Release();//FMOD해제 함수
			return 0;
		}
		default:
		{
			printf("\n  입력 오류! 다시 입력해주세요\n");
			Sleep(1000);
			system("cls");
			break;
		}
		}//switch문 닫는 괄호
	}//첫번째 while문 닫는 괄호


}


//fmod함수
void Init()
{
	FMOD_System_Create(&g_System); //사운드 시스템을 만들어주는 함수
	FMOD_System_Init(g_System, 2, FMOD_INIT_NORMAL, NULL); //사운드 시스템을 초기화 해주는 함수

	//사운드를 메모리로 읽어오는 함수
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\001. 불장난 - 블랙핑크.mp3", FMOD_DEFAULT, 0, &g_Sound[1]);//한번만 재생
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\002. 사뿐사뿐 - AOA.mp3", FMOD_DEFAULT, 0, &g_Sound[2]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\003. LUV - Apink.mp3", FMOD_DEFAULT, 0, &g_Sound[3]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\004. 으르렁(Growl) - EXO.mp3", FMOD_DEFAULT, 0, &g_Sound[4]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\005. 첫사랑니(Rum Pum Pum Pum) - f(x).mp3", FMOD_DEFAULT, 0, &g_Sound[5]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\006. 이게 무슨 일이야 - B1A4.mp3", FMOD_DEFAULT, 0, &g_Sound[6]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\007. Let It Go(겨울왕국 OST) - Indina Menzel.mp3", FMOD_DEFAULT, 0, &g_Sound[7]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\008. 어떻게 이별까지 사랑하겠어, 널 사랑하는 거지 - 악동뮤지션.mp3", FMOD_DEFAULT, 0, &g_Sound[8]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\009. 한숨 - 이하이.mp3", FMOD_DEFAULT, 0, &g_Sound[9]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\010. 와 - 이정현.mp3", FMOD_DEFAULT, 0, &g_Sound[10]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\011. 숨겨진 세상(겨울왕국2 OST) - 태연(Tae Yeon).mp3", FMOD_DEFAULT, 0, &g_Sound[11]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\012. Into the Unknown(Frozen 2) - Indina Menzel, Aurora.mp3", FMOD_DEFAULT, 0, &g_Sound[12]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\013. Show Yourself(Frozen 2) - Indina Menzel, Evan Rachel Wood.mp3", FMOD_DEFAULT, 0, &g_Sound[13]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\014. 첫눈처럼 너에게 가겠다(도깨비 OST) - 에일리.mp3", FMOD_DEFAULT, 0, &g_Sound[14]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\015. 이별공식 - VIXX.mp3", FMOD_DEFAULT, 0, &g_Sound[15]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\016. 떨어지는 낙엽까지도 - 헤이즈.mp3", FMOD_DEFAULT, 0, &g_Sound[16]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\017. 미리 메리크리스마스 (Feat. 천둥 of MBLAQ)  - 아이유.mp3", FMOD_DEFAULT, 0, &g_Sound[17]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\018. Love poem - 아이유.mp3", FMOD_DEFAULT, 0, &g_Sound[18]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\019. 불티 - 태연.mp3", FMOD_DEFAULT, 0, &g_Sound[19]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\020. Santa Tell Me - ariana grande.mp3", FMOD_DEFAULT, 0, &g_Sound[20]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\021. Blueming - 아이유.mp3", FMOD_DEFAULT, 0, &g_Sound[21]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\022. 포장마차 - 황인욱.mp3", FMOD_DEFAULT, 0, &g_Sound[22]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\023. 모든날, 모든 순간 - 폴킴.mp3", FMOD_DEFAULT, 0, &g_Sound[23]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\024. 너를 만나 - 폴킴.mp3", FMOD_DEFAULT, 0, &g_Sound[24]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\025. 안아줘 - 정준일.mp3", FMOD_DEFAULT, 0, &g_Sound[25]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\026. 가을 타나봐 - 바이브.mp3", FMOD_DEFAULT, 0, &g_Sound[26]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\027. 노래방에서 - 장범준.mp3", FMOD_DEFAULT, 0, &g_Sound[27]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\028. 흔들리는 꽃들 속에서 네 샴푸향이 느껴진 거야 - 장범준.mp3", FMOD_DEFAULT, 0, &g_Sound[28]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\029. 워커홀릭 - 볼빨간사춘기.mp3", FMOD_DEFAULT, 0, &g_Sound[29]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\030. 외톨이 - 아웃사이더.mp3", FMOD_DEFAULT, 0, &g_Sound[30]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\031. 겁- 송민호 (Feat.태양).mp3", FMOD_DEFAULT, 0, &g_Sound[31]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\032. 죽일놈 - Dynamic Duo.mp3", FMOD_DEFAULT, 0, &g_Sound[32]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\033. 사랑이 식었다고 말해도 돼 - 먼데이키즈.mp3", FMOD_DEFAULT, 0, &g_Sound[33]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\034. 옥탑방 - 엔플라잉.mp3", FMOD_DEFAULT, 0, &g_Sound[34]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\035. 오랜날 오랜밤 - 악동뮤지션.mp3", FMOD_DEFAULT, 0, &g_Sound[35]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\036. 네모의 꿈 - 화이트.mp3", FMOD_DEFAULT, 0, &g_Sound[36]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\037. 비행기 - 거북이.mp3", FMOD_DEFAULT, 0, &g_Sound[37]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\038. tears - 소찬휘.mp3", FMOD_DEFAULT, 0, &g_Sound[38]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\039. 사계 - 태연.mp3", FMOD_DEFAULT, 0, &g_Sound[39]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\040. 천년의 사랑 - 박완규.mp3", FMOD_DEFAULT, 0, &g_Sound[40]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\041. 그리워하다 - 비투비.mp3", FMOD_DEFAULT, 0, &g_Sound[41]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\042. 너 없인 안 된다 - 비투비.mp3", FMOD_DEFAULT, 0, &g_Sound[42]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\043. 사랑했지만 - 김길중.mp3", FMOD_DEFAULT, 0, &g_Sound[43]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\044. 그대를 사랑한 10가지 이유 - 이석훈.mp3", FMOD_DEFAULT, 0, &g_Sound[44]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\045. BAND - 창모, 해쉬스완, ASH ISLAND, 김효은.mp3", FMOD_DEFAULT, 0, &g_Sound[45]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\046. 한국을 빛낸 100명의 위인들 - 노래를 사랑하는 사람들.mp3", FMOD_DEFAULT, 0, &g_Sound[46]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\047. 짝사랑 - B1A4 (산들 Solo).mp3", FMOD_DEFAULT, 0, &g_Sound[47]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\048. 시든 꽃에 물을 주듯 - HYNN (박혜원).mp3", FMOD_DEFAULT, 0, &g_Sound[48]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\049. 잔소리 - 아이유, 임슬옹.mp3", FMOD_DEFAULT, 0, &g_Sound[49]);
	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\050. 걱정말아요 그대 - 이적.mp3", FMOD_DEFAULT, 0, &g_Sound[50]);


	FMOD_System_CreateSound(g_System, "C:\\Fmod Sound\\환호소리.mp3", FMOD_DEFAULT, 0, &g_Sound[0]);


	

}

void Update(char ch, int channel_num)//데이터 갱신 함수
{
	float volume = 0.5f;
	//FMOD_BOOL IsPlaying;//변수에 1을 아니면 0을 대입해준다.

	switch (ch) {
	case 'a': // 효과음
		FMOD_System_PlaySound(g_System, FMOD_CHANNEL_FREE, g_Sound[0], 0, &g_Channel[0]);//효과음 재생
		break;
		/*case 's': // 배경음 멈춤 or 시작
			FMOD_Channel_IsPlaying(g_Channel[channel_num], &IsPlaying);
			if (IsPlaying == 1)
				FMOD_Channel_Stop(g_Channel[channel_num]);
			else
				FMOD_System_PlaySound(g_System, FMOD_CHANNEL_FREE, g_Sound[channel_num], 0, &g_Channel[channel_num]);
			break;*/
	case 'u': // 배경 볼륨 업
		if (volume < 1.0f)
			volume += 0.2f;
		FMOD_Channel_SetVolume(g_Channel[channel_num], volume);//해당 채널의 볼륨을 조절해 주는 함수, 볼륨은 실수값으로 0.0f ~ 1.0f까지 조절가능(그 이상이나 이하는 안된다)
		break;
	case 'd': // 배경 볼륨 다운
		if (volume > 0.0f)
			volume -= 0.2f;
		FMOD_Channel_SetVolume(g_Channel[channel_num], volume); // 해당 채널의 볼륨을 조절해 주는 함수
		break;
	}
}

void Release()//FMOD시스템 해제
{
	for (int i = 0; i < songs; i++)//노래 개수만큼 FMOD 시스템을 해제한다.
	{
		FMOD_Sound_Release(g_Sound[i]);//사운드 해제
	}

	FMOD_System_Close(g_System);//시스템 종료
	FMOD_System_Release(g_System);//시스템 해제
}


void gotoxy(int x, int y)//좌표이동 함수
{

	COORD pos = { x,y };

	SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), pos);

}

void gotoxyprint(int x, int y, char* s)//좌표이동한 후 print하는 함수
{ //x값을 2x로 변경, 좌표값에 바로 문자열을 입력할 수 있도록 printf함수 삽입  
	COORD pos = { 2 * x,y };
	SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), pos);
	printf("%s", s);
}

void print_coin_noraebang(void)//노래방 시작 화면 출력하는 함수
{
	printf("\n\n");
	printf("   ===================================\n");
	printf("             코 인 노 래 방\n");
	printf("   ===================================\n");
	printf("   \n\n");
	printf("              1. 로그인\n\n");
	printf("              2. 회원가입\n\n");
	printf("              3. 종료\n\n\n\n");
}

void open_txt(fp)//메모장 내용을 출력해주는 함수
{
	printf("\n");
	char ch;

	if (fp == NULL)//파일이 없는 경우
	{
		printf("파일 오픈 실패 !\n");
		return -1;//오류
	}

	while (1)
	{
		ch = fgetc(fp);//파일을 열어 ch에 저장한다.
		if (ch == EOF)
			break;

		putchar(ch);//ch에 저장된 문자를 출력한다.
	}
	fclose(fp);//파일을 닫는다.

	gotoxy(20, 0);//커서 이동

}



void timerf(void)//노래방 이용 시간 출력 함수
{

	result = result + (double)(end - start); // 결과 출력, 노래방 이용 시간 += (시간측정종료시간) - (시간측정시작시간) 

	if (result >= 60)//측정 시간이 60초 이상일 때
	{
		timer = result / 60;//result를 분단위로 바꿈, result를 60초로 나눈다.
		printf(" 이용 시간 : %d분 \n", timer);
	}
	else//60초가 지나지 않았을 때
	{
		printf(" 이용 시간 : 1분미만입니다.\n");
	}
}

