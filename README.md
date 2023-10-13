# week1 : UI 컴포넌트의 명칭과 사용법 익히고 개발환경을 구축

## 개발환경
* Android Studio : Android Studio Giraffe | 2022.3.1 Patch 1
* Build configuration language : Kotlin DSL
* Language : Kotlin
* Target SDK : API 33 Tiramisu, Android 13 
* Minimum SDK : API 27 Oreo, Android 8.1
  
[Screen_recording_20231012_190505.webm](https://github.com/KakaoTechCamp-Android-Step2-2023/week1/assets/24618293/57b65fea-1d8a-44c5-9db5-62082afa86ce)

## 1. 학습목표

- XML 을 이용하여 원하는 UI 를 그릴 수 있다
- Compose 를 이용하여 원하는 UI 를 그릴 수 있다
- Intent 를 이용하여 Activity 간 데이터 전달을 할 수 있다

## 2. 구현기능

- 연락처 리스트
- 연락처 추가
- 연락처 상세 보기

## 3. 사용기술

- 데이터 전달 : Intent, registerForActivityResult
- UI (xml) : ConstraintLayout, LinearLayout, TextView, EditText, ImageView, RadioButton, FloatingActionButton, DatePickerDialog, AlertDialog
- UI (compose) : Box, Row, Column, Text, Editable, Image, RadioButton,

## 4. 한 걸음 더

- 수정하기 기능 추가해보기
- 연락처 리스트 화면을 RecyclerView 또는 LazyRow 로 구현해보기
- 이외에 다양한 컴포넌트 많이 사용해보기

## 5. 과제 예시

- 카카오맵 기능 명세는, 형식은 상관 없으니 아래 예시는 참고만 하시기 바랍니다.
   1. 지도 화면
      - 지도 핀 : 지도의 특정 위치를 클릭하면 바텀 시트에 해당 위치에 대한 간략 정보 노출
      - 줌인 : 사용자가 더블 클릭을 하거나, 두 손가락을 터치 후 넓히면 지도가 확대됨
      - 줌아웃 : 사용자가 두 손가락을 터치 후 좁히면 지도가 축소됨
      - 내 위치로 포커스 : 지도 하단 오른쪽에 플로팅 아이콘 클릭 시, 현재 위치로 지도가 포커스 됨
   3. 검색 화면 ...
   4. 길찾기 화면 ...
  
  
- 패키지 구조는, java 이하에 추가될 클래스/파일을 어떤 규칙으로 분리 하면 좋을지 고민하여 디렉터리 구조를 작성해보세요.
```
new-structure
├─ library-foobar
├─ app
│  ├─ libs
│  ├─ src
│  │  ├─ androidTest
│  │  │  └─ java
│  │  │     └─ com/futurice/project (이 폴더 이하의 구조를 생각해보세요)
│  │  └─ main
│  │     ├─ java
│  │     │  └─ com/futurice/project
│  │     ├─ res
│  │     └─ AndroidManifest.xml
│  ├─ build.gradle
│  └─ proguard-rules.pro
├─ build.gradle
└─ settings.gradle
```
