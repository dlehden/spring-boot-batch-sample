spring batch 를 이용한
선사 크롤링 샘플

reader -> 크롤링 read
Processor -> read 한 스케줄 데이터 비교 (작업중)
writer ->jpa insert


<img src="https://user-images.githubusercontent.com/22138152/107337374-9be9e800-6afd-11eb-8313-14cb7186568a.JPG"  width="700" height="370">


JOB 마다 각각의 선사를 부여하며
실행할때 
멀티 스레드 형식으로 JOB마다 따로 진행을 하는 프로젝트.

기존 그냥 batchconfig 에서 job 여러개로 구동을 할시
1번째 job 에서 셀레니움 크롤링을 한다면 2번째 프로그램이 멈춘 상태로
대기 중에 있다...
그렇기에 분리 작업을 함.
