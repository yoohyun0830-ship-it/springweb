package example.day06;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

public class Exam1 {
   public static void main(String[] args) {
     // [1] 리터럴
    int a = 3; // a변수 -> 3참조
    int b = 3; // b변수 -> 3참조
        // 두 변수가 참조하는 값 : 총 1개

    // [2] 참조 : 어떠한 값의 위치 , 인스턴스(객체) 1개당 참조값 1개
    String c = new String("유재석");
    String d = new String("강호동");
        // 두 변수가 참조하는 값 : 총 2개
    Test t = new Test();
    t.name = new String("유재석");
        // t변수가 참조하는 값 : 총 1개(1개) , t -> Test -> name

    // 자바 참조
    // (1) '자유' 카테고리 등록
    Category c1 = new Category(1, "자유" , new ArrayList<>());
        // c1의 참조 : 총 1개 , c1 -> category

    // (2) '자유' 카테고리에 게시물 작성
    Board b1 = new Board(1, "제목1", c1);
        // b1의 참조 : 총 1개 , b1 -> Board -> Category -> String
    // (*) b1을 통해 c1 참조 가능? : 가능 , Board에 category 존재
        // => db 연관관계(join) 가능(단방향참조 FK)
    // (*) c1을 통해 b1 참조 가능? : 불가능 , category에 board 존재 x
    

    // (3) category에 board 삽입
    c1.getList().add( b1 );
        // c1 -> category -> list( board )
        // (*) c1을 통해 b1 참조 가능? : 가능
        // (*) JPA 서로 참조 가능한 구조 : 양방향참조

    // System.out.println( b1 );  
        // b1 -> c1 -> b1 -> c1 -> b1 ~ 무한참조 , <순환참조>
        // 양방향쪽에 @ToString.Exclude 주입
        // toString( ) : Object(슈퍼) 클래스의 객체의 주소값 반환 함수
        // + 오버라이딩 : 객체 주소겂 대신에 문자열로 반환 함수
    System.out.println( b1 );

        // 데이터베이스 단방향 : 참조[FK]테이블에 PK테이블의 PK값을 저장
        // 데이터베이스 양방향 : X , 참조/매핑 테이블( 실무적으로 권장 x )
        // 결론 : JPA에서만 양방향 , db에서는 양방향 X 
        // => 실무에서 양방향 비권장 , 불필요한 자료들까지 참조가능성
    }
}

@Data @AllArgsConstructor
class Board{
    private int bno;
    private String btitle;
    private Category category; // 참조FK
}
@Data @AllArgsConstructor
class Category{
    private int cno;
    private String cname;
    @ToString.Exclude // toString 사용금지
    private List<Board>list = new ArrayList<>();
}

class Test{
    String name;

}