#include <stdio.h>
#define songs 51

char norae_name[songs][200] = //노래제목 저장함수(노래 제목은 최대 200)
{
	"효과음",
	"001. 불장난 - 블랙핑크",
	"002. 사뿐사뿐 - AOA",
	"003. LUV - Apink",
	"004. 으르렁(Growl) - EXO",
	"005. 첫사랑니(Rum Pum Pum Pum) - f(x)",
	"006. 이게 무슨 일이야 - B1A4",
	"007. Let It Go(겨울왕국 OST) - Indina Menzel",
	"008. 어떻게 이별까지 사랑하겠어, 널 사랑하는 거지 - 악동뮤지션",
	"009. 한숨 - 이하이",
	"010. 와 - 이정현",
	"011. 숨겨진 세상(겨울왕국2 OST) - 태연(Tae Yeon)",
	"012. Into the Unknown(Frozen 2) - Indina Menzel, Aurora",
	"013. Show Yourself(Frozen 2) - Indina Menzel, Evan Rachel Wood",
	"014. 첫눈처럼 너에게 가겠다(도깨비 OST) - 에일리",
	"015. 이별공식 - VIXX",
	"016. 떨어지는 낙엽까지도 - 헤이즈",
	"017. 미리 메리크리스마스 (Feat. 천둥 of MBLAQ)  - 아이유",
	"018. Love poem - 아이유",
	"019. 불티 - 태연",
	"020. Santa Tell Me - ariana grande",
	"021. Blueming - 아이유",
	"022. 포장마차 - 황인욱",
	"023. 모든날, 모든 순간 - 폴킴",
	"024. 너를 만나 - 폴킴",
	"025. 안아줘 - 정준일",
	"026. 가을 타나봐 - 바이브",
	"027. 노래방에서 - 장범준",
	"028. 흔들리는 꽃들 속에서 네 샴푸향이 느껴진 거야 - 장범준",
	"029. 워커홀릭 - 볼빨간사춘기",
	"030. 외톨이 - 아웃사이더",
	"031. 겁- 송민호 (Feat.태양)",
	"032. 죽일놈 - Dynamic Duo",
	"033. 사랑이 식었다고 말해도 돼 - 먼데이키즈",
	"034. 옥탑방 - 엔플라잉",
	"035. 오랜날 오랜밤 - 악동뮤지션",
	"036. 네모의 꿈 - 화이트",
	"037. 비행기 - 거북이",
	"038. tears - 소찬휘",
	"039. 사계 - 태연",
	"040. 천년의 사랑 - 박완규",
	"041. 그리워하다 - 비투비",
	"042. 너 없인 안 된다 - 비투비",
	"043. 사랑했지만 - 김길중",
	"044. 그대를 사랑한 10가지 이유 - 이석훈",
	"045. BAND - 창모, 해쉬스완, ASH ISLAND, 김효은",
	"046. 한국을 빛낸 100명의 위인들 - 노래를 사랑하는 사람들",
	"047. 짝사랑 - B1A4 (산들 Solo)",
	"048. 시든 꽃을 물을 주듯 - HYNN (박혜원)",
	"049. 잔소리 - 아이유, 임슬옹",
	"050. 걱정말아요 그대 - 이적",


};

void open_open_txt(int song_num)
{
	FILE * fp000 = fopen("C:\\Fmod Sound\\가사\\노래 목록.txt", "rt");//포인터변수, 파일 경로 설정
	FILE * fp001 = fopen("C:\\Fmod Sound\\가사\\001. 불장난 - 블랙핑크.txt", "rt");
	FILE * fp002 = fopen("C:\\Fmod Sound\\가사\\002. 사뿐사뿐 - AOA.txt", "rt");
	FILE * fp003 = fopen("C:\\Fmod Sound\\가사\\003. LUV - Apink.txt", "rt");
	FILE * fp004 = fopen("C:\\Fmod Sound\\가사\\004. 으르렁(Growl) - EXO.txt", "rt");
	FILE * fp005 = fopen("C:\\Fmod Sound\\가사\\005. 첫사랑니(Rum Pum Pum Pum) - f(x).txt", "rt");
	FILE * fp006 = fopen("C:\\Fmod Sound\\가사\\006. 이게 무슨 일이야 - B1A4.txt", "rt");
	FILE * fp007 = fopen("C:\\Fmod Sound\\가사\\007. Let It Go(겨울왕국 OST) - Indina Menzel.txt", "rt");
	FILE * fp008 = fopen("C:\\Fmod Sound\\가사\\008. 어떻게 이별까지 사랑하겠어, 널 사랑하는 거지 - 악동뮤지션.txt", "rt");
	FILE * fp009 = fopen("C:\\Fmod Sound\\가사\\009. 한숨 - 이하이.txt", "rt");
	FILE * fp010 = fopen("C:\\Fmod Sound\\가사\\010. 와 - 이정현.txt", "rt");
	FILE * fp011 = fopen("C:\\Fmod Sound\\가사\\011. 숨겨진 세상(겨울왕국2 OST) - 태연(Tae Yeon).txt", "rt");
	FILE * fp012 = fopen("C:\\Fmod Sound\\가사\\012. Into the Unknown(Frozen 2) - Indina Menzel, Aurora.txt", "rt");
	FILE * fp013 = fopen("C:\\Fmod Sound\\가사\\013. Show Yourself(Frozen 2) - Indina Menzel, Evan Rachel Wood.txt", "rt");
	FILE * fp014 = fopen("C:\\Fmod Sound\\가사\\014. 첫눈처럼 너에게 가겠다(도깨비 OST) - 에일리.txt", "rt");
	FILE * fp015 = fopen("C:\\Fmod Sound\\가사\\015. 이별공식 - VIXX.txt", "rt");
	FILE * fp016 = fopen("C:\\Fmod Sound\\가사\\016. 떨어지는 낙엽까지도 - 헤이즈.txt", "rt");
	FILE * fp017 = fopen("C:\\Fmod Sound\\가사\\017. 미리 메리크리스마스 (Feat. 천둥 of MBLAQ)  - 아이유.txt", "rt");
	FILE * fp018 = fopen("C:\\Fmod Sound\\가사\\018. Love poem - 아이유.txt", "rt");
	FILE * fp019 = fopen("C:\\Fmod Sound\\가사\\019. 불티 - 태연.txt", "rt");
	FILE * fp020 = fopen("C:\\Fmod Sound\\가사\\020. Santa Tell Me - ariana grande.txt", "rt");
	FILE * fp021 = fopen("C:\\Fmod Sound\\가사\\021. Blueming - 아이유.txt", "rt");
	FILE * fp022 = fopen("C:\\Fmod Sound\\가사\\022. 포장마차 - 황인욱.txt", "rt");
	FILE * fp023 = fopen("C:\\Fmod Sound\\가사\\023. 모든날, 모든 순간 - 폴킴.txt", "rt");
	FILE * fp024 = fopen("C:\\Fmod Sound\\가사\\024. 너를 만나 - 폴킴.txt", "rt");
	FILE * fp025 = fopen("C:\\Fmod Sound\\가사\\025. 안아줘 - 정준일.txt", "rt");
	FILE * fp026 = fopen("C:\\Fmod Sound\\가사\\026. 가을 타나봐 - 바이브.txt", "rt");
	FILE * fp027 = fopen("C:\\Fmod Sound\\가사\\027. 노래방에서 - 장범준.txt", "rt");
	FILE * fp028 = fopen("C:\\Fmod Sound\\가사\\028. 흔들리는 꽃들 속에서 네 샴푸향이 느껴진 거야 - 장범준.txt", "rt");
	FILE * fp029 = fopen("C:\\Fmod Sound\\가사\\029. 워커홀릭 - 볼빨간사춘기.txt", "rt");
	FILE * fp030 = fopen("C:\\Fmod Sound\\가사\\030. 외톨이 - 아웃사이더.txt", "rt");
	FILE * fp031 = fopen("C:\\Fmod Sound\\가사\\031. 겁- 송민호 (Feat.태양).txt", "rt");
	FILE * fp032 = fopen("C:\\Fmod Sound\\가사\\032. 죽일놈 - Dynamic Duo.txt", "rt");
	FILE * fp033 = fopen("C:\\Fmod Sound\\가사\\033. 사랑이 식었다고 말해도 돼 - 먼데이키즈.txt", "rt");
	FILE * fp034 = fopen("C:\\Fmod Sound\\가사\\034. 옥탑방 - 엔플라잉.txt", "rt");
	FILE * fp035 = fopen("C:\\Fmod Sound\\가사\\035. 오랜날 오랜밤 - 악동뮤지션.txt", "rt");
	FILE * fp036 = fopen("C:\\Fmod Sound\\가사\\036. 네모의 꿈 - 화이트.txt", "rt");
	FILE * fp037 = fopen("C:\\Fmod Sound\\가사\\037. 비행기 - 거북이.txt", "rt");
	FILE * fp038 = fopen("C:\\Fmod Sound\\가사\\038. tears - 소찬휘.txt", "rt");
	FILE * fp039 = fopen("C:\\Fmod Sound\\가사\\039. 사계 - 태연.txt", "rt");
	FILE * fp040 = fopen("C:\\Fmod Sound\\가사\\040. 천년의 사랑 - 박완규.txt", "rt");
	FILE * fp041 = fopen("C:\\Fmod Sound\\가사\\041. 그리워하다 - 비투비.txt", "rt");
	FILE * fp042 = fopen("C:\\Fmod Sound\\가사\\042. 너 없인 안 된다 - 비투비.txt", "rt");
	FILE * fp043 = fopen("C:\\Fmod Sound\\가사\\043. 사랑했지만 - 김길중.txt", "rt");
	FILE * fp044 = fopen("C:\\Fmod Sound\\가사\\044. 그대를 사랑한 10가지 이유 - 이석훈.txt", "rt");
	FILE * fp045 = fopen("C:\\Fmod Sound\\가사\\045. BAND - 창모, 해쉬스완, ASH ISLAND, 김효은.txt", "rt");
	FILE * fp046 = fopen("C:\\Fmod Sound\\가사\\046. 한국을 빛낸 100명의 위인들 - 노래를 사랑하는 사람들.txt", "rt");
	FILE * fp047 = fopen("C:\\Fmod Sound\\가사\\047. 짝사랑 - B1A4 (산들 Solo).txt", "rt");
	FILE * fp048 = fopen("C:\\Fmod Sound\\가사\\048. 시든 꽃에 물을 주듯 - HYNN (박혜원).txt", "rt");
	FILE * fp049 = fopen("C:\\Fmod Sound\\가사\\049. 잔소리 - 아이유, 임슬옹.txt", "rt");
	FILE * fp050 = fopen("C:\\Fmod Sound\\가사\\050. 걱정말아요 그대 - 이적.txt", "rt");

	//FILE * fp00 = fopen("C:\\Fmod Sound\\가사\\.txt", "rt");

	switch (song_num) {
	case 0:
	{
		open_txt(fp000);//노래 목록 출력
		break;
	}
	case 1:
	{
		open_txt(fp001);
		break;
	}
	case 2:
	{
		open_txt(fp002);
		break;
	}
	case 3:
	{
		open_txt(fp003);
		break;
	}
	case 4:
	{
		open_txt(fp004);
		break;
	}
	case 5:
	{
		open_txt(fp005);
		break;
	}
	case 6:
	{
		open_txt(fp006);
		break;
	}
	case 7:
	{
		open_txt(fp007);
		break;
	}
	case 8:
	{
		open_txt(fp008);
		break;
	}
	case 9:
	{
		open_txt(fp009);
		break;
	}
	case 10:
	{
		open_txt(fp010);
		break;
	}
	case 11:
	{
		open_txt(fp011);
		break;
	}
	case 12:
	{
		open_txt(fp012);
		break;
	}
	case 13:
	{
		open_txt(fp013);
		break;
	}
	case 14:
	{
		open_txt(fp014);
		break;
	}
	case 15:
	{
		open_txt(fp015);
		break;
	}
	case 16:
	{
		open_txt(fp016);
		break;
	}
	case 17:
	{
		open_txt(fp017);
		break;
	}
	case 18:
	{
		open_txt(fp018);
		break;
	}
	case 19:
	{
		open_txt(fp019);
		break;
	}
	case 20:
	{
		open_txt(fp020);
		break;
	}
	case 21:
	{
		open_txt(fp021);
		break;
	}
	case 22:
	{
		open_txt(fp022);
		break;
	}
	case 23:
	{
		open_txt(fp023);
		break;
	}
	case 24:
	{
		open_txt(fp024);
		break;
	}
	case 25:
	{
		open_txt(fp025);
		break;
	}
	case 26:
	{
		open_txt(fp026);
		break;
	}
	case 27:
	{
		open_txt(fp027);
		break;
	}
	case 28:
	{
		open_txt(fp028);
		break;
	}
	case 29:
	{
		open_txt(fp029);
		break;
	}
	case 30:
	{
		open_txt(fp030);
		break;
	}
	case 31:
	{
		open_txt(fp031);
		break;
	}
	case 32:
	{
		open_txt(fp032);
		break;
	}
	case 33:
	{
		open_txt(fp033);
		break;
	}
	case 34:
	{
		open_txt(fp034);
		break;
	}
	case 35:
	{
		open_txt(fp035);
		break;
	}
	case 36:
	{
		open_txt(fp036);
		break;
	}
	case 37:
	{
		open_txt(fp037);
		break;
	}
	case 38:
	{
		open_txt(fp038);
		break;
	}
	case 39:
	{
		open_txt(fp039);
		break;
	}
	case 40:
	{
		open_txt(fp040);
		break;
	}
	case 41:
	{
		open_txt(fp041);
		break;
	}
	case 42:
	{
		open_txt(fp042);
		break;
	}
	case 43:
	{
		open_txt(fp043);
		break;
	}
	case 44:
	{
		open_txt(fp044);
		break;
	}
	case 45:
	{
		open_txt(fp045);
		break;
	}
	case 46:
	{
		open_txt(fp046);
		break;
	}
	case 47:
	{
		open_txt(fp047);
		break;
	}
	case 48:
	{
		open_txt(fp048);
		break;
	}
	case 49:
	{
		open_txt(fp049);
		break;
	}
	case 50:
	{
		open_txt(fp050);
		break;
	}



	}//switch문 닫는 괄호

}