# CS496

## 팀원
[김수효], [이희우]

## ABSTRACT
swipe 으로 이동할 수 있는 3 개의 탭으로 이루어진 앱.

TAB 1. Contact

TAB 2. Image Gallery

TAB 3. Tool box

## TAB 1. Contact

### FEATURES
1. 저장된 연락처 불러오기
2. 연락처 추가하기
3. 연락처 삭제하기
4. 연락처로 문자, 전화하기 

#### 1. 저장된 연락처 불러오기
연락처를 Array list로 불러와 Recycler View 로 나타내었다. 각 아이템이 전화 버튼, 문자 버튼, 삭제 버튼을 가지고 있다. 
#### 2. 연락처 추가하기
Floating Button 을 클릭하면 Alert Dialog 이 나와 사용자 이름과 번호 정보를 받는다. 주어진 정보는 intent를 통해 원래의 연락처 앱에 저장이 된다. 
#### 3. 연락처 삭제하기
휴지통 버튼을 누르면 목록에서 목록이 삭제된다. 연락처 앱에 연동이 되도록 만들고 싶었으나, getContentResolver().delete()에 문제가 있는 것으로 보인다. 
#### 4. 연락처로 문자, 전화하기 
전화 혹은 문자 아이콘을 누르면 사용자의 전화번호 정보를 가지고 전화 및 문자창으로 연결된다. 연동은 intent를 이용하고 각각 ACTION_DIAL, ACTION_VIEW를 이용한다. 
## TAB 2. Image Gallery

### FEATURES
1. 저장소에서 모든 이미지 자동으로 불러오기
2. 스크롤을 통해 여러 장의 이미지 보여주기
3. 사용자의 기기 환경에 맞추어 격자 크기 자동으로 조절하기
4. 사진 선택시 전체화면으로 원본 사진 보여주기
5. 아래로 당겨 갤러리 새로고침하기

#### 1. 저장소에서 모든 이미지 자동으로 불러오기
ImageAdapter의 getAllImages()에서 내부에 저장된 모든 이미지들의 파일 경로를 탐색하고, 이를 ArrayList에 저장한다.

getView()에서 이 ArrayList를 이용해 사진을 View에 Glide한다.

Fragment 2의 onCreateView()에서 ImageAdapter를 호출해 GridView에 사진을 표시한다.

#### 2. 스크롤을 통해 여러 장의 이미지 보여주기
GridView에 내장된 스크롤 기능을 통해 여러 장의 이미지를 스크롤을 통해 표시한다.

#### 3. 사용자의 기기 환경에 맞추어 격자 크기 자동으로 조절하기
displayMetrics를 가져와 사용자의 기기 환경을 파악한다.

default width를 250px로 설정하고, 그 주변의 값 중 기기의 display width를 꽉 채울 수 있는 가장 적절한 값을 계산해 optimalWidth로 설정한다.

Column의 갯수는 auto_fill로 설정해 optimalWidth에 적합한 갯수를 찾는다.

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
