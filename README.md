## Mini-Project

# 목적
* 컨텐츠 추가 목록이 있다.
* 초기 화면에는 세개의 탭이 구성 되어있다.
  * 첫번째 탭은 컨텐츠의 목록
  * 두번째 탭은 지도
  * 세번째 탭은 저장했던 컨텐츠 중 사진만 모아 볼 수 있다.
* 컨텐츠를 누르게 되면 컨텐츠에 대한 자세한 정보와 삭제 및 공유를 할 수 있다.

# 제작
* 데이터베이스

  > 모든 것은 전부 다 이제까지의 응용으로 해결할 수 있었지만 안드로이드 내부 데이터베이스를 작동하는 것은 새로운 과제였다.
  구글링을 하던 중 쓰기 편한 Realm이라는 것을 알게 되었고 그것을 사용하고자 하였다.
  먼저 기본 환경 설정 및 Realm을 불러주기 위한 설정을 해주었다. 그 후에 Realm에 해당하는 RealmHelper을 자바 형식으로 만들어주었다.
  그곳 안에는 먼저 생성자를 만들어 주었다.
  ```java
  public RealmHelper(Realm realm){
        this.realm=realm;
   }
   ```
  >그 후에는 각 해당하는 기능을 만들어주었는데 먼저 save라는 함수이다.
   ```java
   public void save(final Posts posts){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Posts co = realm.copyToRealm(posts);
            }
        });

    }
    ```
    >이 코드를 보게 되면 copyToRealm을 볼 수 있는데 객체를 생성할 때 불편한 점을 보완해주기 위해 사용하게 된다. Realm은 객체의 내용을 바꾸어도
    내용이 바뀌지 않게 된다. 그러므로 객체 자체를 바꿔줘야 Realm에 있는 데이터가 바뀌게 되는것이다.
    그 다음은 삭제 기능인데 이것은 입력 값으로 들어온 position 즉 위치를 찾아 그에 해당하는 내용을 삭제해주는 것이다. 코드는 아래와 같다.
    ```java
     public void delete(final int position){
        containers = realm.where(Posts.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Posts po = containers.get(position);
                po.deleteFromRealm();
            }
        });
    }
    ```

* 나머지는 이제까지의 어플을 만든 것과 기본 기능을 사용하였다.

# 결과 화면
 * 첫번째 탭의 목록 화면이다.
 
 ![목록 보여줌](https://user-images.githubusercontent.com/46989868/61268985-30804d00-a7d8-11e9-803c-4a0ddeedcbd7.jpg)
 
 
 * 두번째 탭의 추가했던 위치 정보를 보여주는 곳이다.
 
 ![각 정보 등록한 위치](https://user-images.githubusercontent.com/46989868/61269040-54dc2980-a7d8-11e9-9eac-294ef24c43cb.jpg)
 
 
 * 세번째 탭의 추가했던 사진만 보여주는 곳이다.
 
 ![등록한 사진 어플로 보기](https://user-images.githubusercontent.com/46989868/61269068-71786180-a7d8-11e9-9179-29f990920d15.jpg)
 
 
 * 첫번째 탭의 밑에 플러스 모양을 눌렀을 때 나오는 추가 화면이다.
 
 ![사진과 정보 추가](https://user-images.githubusercontent.com/46989868/61269105-8a811280-a7d8-11e9-9849-ee630d513eb4.jpg)
 
 
 * 목록에서 컨텐츠를 눌렀을 때 보이는 세부정보 화면이다.
 
 ![등록한 것에 대한 세부정보 보기](https://user-images.githubusercontent.com/46989868/61269139-9ff63c80-a7d8-11e9-91d7-eb9063f805c3.jpg)
 
 
 * share 버튼을 눌렀을 때 카톡 공유되는 화면이다.
 
 ![share을 눌렀을 시 카톡 공유](https://user-images.githubusercontent.com/46989868/61269163-af758580-a7d8-11e9-99a6-2f7fe259b058.png)
 
 
 * 위치보기를 눌렀을 때 등록한 위치가 지도에 보이게 된다.
 
 ![위치보기를 눌럿을 때 처음한 등록한 위치 뜨기](https://user-images.githubusercontent.com/46989868/61269199-c3b98280-a7d8-11e9-813c-654e486b8ec3.jpg)
    
    
   
