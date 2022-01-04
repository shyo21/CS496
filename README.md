# CS496 Project 1

## 팀원
[김수효], [이희우]


## ABSTRACT
swipe 으로 이동할 수 있는 3 개의 탭으로 이루어진 앱.

TAB 1. Contact

TAB 2. Image Gallery

TAB 3. Tool box


## Basic Structure
각각의 탭을 위한 3개의 Fragment를 생성하고, Viewpager를 이용해 스와이프로 탭 사이를 이동할 수 있다.

또한 상단에는 Tablayout을 이용한 탭 그룹을 생성해 터치를 통한 이동 역시 지원한다.

Themes.xml 파일을 수정해 Primary color를 바꾸었으며, TitleBar를 삭제하고 StatusBar를 투명하게 수정하였다.


## TAB 1. Contact

### FEATURES
1. 저장된 연락처 불러오기
2. 연락처 추가하기
3. 연락처 삭제하기
4. 연락처로 문자, 전화하기 

#### 1. 저장된 연락처 불러오기
연락처를 Array list로 불러와 Recycler View 로 나타내었다. 
#### 2. 연락처 추가하기
Floating Button 을 클릭하면 Alert Dialog 이 나와 사용자 이름과 번호 정보를 받는다. 
#### 3. 연락처 삭제하기
휴지통 버튼을 누르면 목록에서 목록이 삭제된다. 
#### 4. 연락처로 문자, 전화하기 
전화 혹은 문자 아이콘을 누르면 사용자의 전화번호 정보를 가지고 전화 및 문자창으로 연결된다.

## TAB 2. Image Gallery

### FEATURES
1. 저장소에 저장된 모든 이미지 파일 자동으로 불러오기
2. 스크롤을 통해 모든 이미지 파일 보여주기
3. 사용자의 기기 환경에 맞추어 격자 크기 자동으로 최적화하기
4. 사진 클릭시 전체화면으로 확대해 원본 이미지 보여주기
5. 아래로 스와이프해 새로고침하기

#### Basic Structure
가장 바깥에 SwipeRefreshLayout을 배치해 새로고침 기능을 지원한다.

그 안에 FrameLayout을 배치하고 GridView와 ImageView를 삽입해 visibility 조절을 통해 어떤 View를 보여줄 지 선택할 수 있다.

ImageView에는 원본 사진을 Glide를 통해 직접 표시한다.

GridView에는 GridViewAdapter를 통해 불러온 이미지들을 표시한다.

#### 1. 저장소에 저장된 모든 이미지 파일 자동으로 불러오기


## TAB 3. Tool box

### FEATURES
1. 손전등 
2. 다크모드
3. 음성녹음
4. 화면녹화
5. QR코드 리더기 

#### 1. 손전등 
디바이스에 손전등이 있는지 확인한 후, toggle 하여 손전등을 조절한다. 
#### 2. 다크모드
화면을 클릭하여 다크모드를 조정하고 
sharedPreferences에 설정값을 저장한다. 
#### 3. 음성녹음
#### 4. 화면녹화
#### 5. QR코드 리더기 
