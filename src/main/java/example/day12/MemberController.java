package example.day12;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/member") 
@RequiredArgsConstructor 
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") // 도메인 다른 경우 allowCredentials  이용한 쿠키/세션 유지

public class MemberController {
     private final MemberService memberService;
     private final JwtUtil jwtUtil;

    // [1] 회원가입
    @PostMapping("/signup")
    public boolean signup( @RequestBody MemberDto memberDto ){
        return memberService.signup( memberDto );
    }
    @GetMapping("")
    public String test( HttpServletRequest request){

        // 1) HttpServletRequest : HTTP 요청이 들어오면 요청 정보가 담겨 있는 객체
            System.out.println(request.getRemoteAddr()); // 요청한 클라이언트의 IP
            System.out.println(request.getHeader("User-Agent")); // 요청한 클라이언트 브라우저 정보
            System.out.println(request.getSession()); // 요청한 클라이언트의 세션객체 정보

        // 2) 세션객체 : 톰캣 서버내 브라우저 마다 독립적인 저장소
        // 주로 : 로그인 성공 정보 , 인증번호 , 비회원제 장바구니 등등 일시적인 휘발성 메모리
            HttpSession session = request.getSession(); // 세션객테내 여러개 정보 저장 가능
            System.out.println( session.getId() ); // 세션 식별번호
            System.out.println( session.getCreationTime() ); // 세션 생성시간
            System.out.println( session.getLastAccessedTime()); // 세션 마지막 접근 시간
            System.out.println( session.getMaxInactiveInterval()); // 세션 생명주기

        // 3) 세션정보 저장 = 로그인 / 호출 = 마이페이지 / 삭제 = 로그아웃
            session.setAttribute("data", "사과"); // map(key: value) 구조로
        
            // data 이름(key) 으로 사과(data) 저장
            System.out.println(session.getAttribute("data")); // key 이용한 value 호출
            
            // session.invalidate(); // 세션 초기화
            return session.getId();
    }


    private  final RedisTokenService redisTokenService;
      // [2] 로그인 + 쿠키변경( 회원 식별(번호) 쿠키에 담아 클라이언트에 전송 )
    @PostMapping("/login")
    public MemberDto login( @RequestBody MemberDto memberDto , HttpServletResponse response ){
        
        // 1. 서비스 에게 인증/로그인 확인 (기존 유지)
        MemberDto result = memberService.login(memberDto);
        if( result == null ) return null; // 로그인 실패시 

        // 4. 토큰(token) 발급 요청
        String accessToken = jwtUtil.createAccessToken( result.getMno() ); // mno --> jwt
        String refreshToken = jwtUtil.createRefreshToken(result.getMno());
        
        // 5. refreshToken만 레디스에 저장
        redisTokenService.setRefreshToken(result.getMno() , refreshToken);

        // 2. 로그인 성공시 쿠키 생성/발급 , 쿠키만료기간 == 토큰만료기간 
        ResponseCookie cookie1 = ResponseCookie.from( "accessToken" , accessToken )
                                .path("/").maxAge(Duration.ofMinutes(30)) // 쿠키 사용할 경로 , "/" 도메인내 전체
                                .httpOnly(true).secure(false).sameSite("Lax").build();

        ResponseCookie cookie2 = ResponseCookie.from("refreshToken" , refreshToken)
                                .path("/").maxAge(Duration.ofDays(7))
                                .httpOnly(true).secure(false).sameSite("Lax").build();

        // 3. 응답 헤더에 쿠키 등록 , response.setHeader( )
        response.addHeader( HttpHeaders.SET_COOKIE  , cookie1.toString() );
        response.addHeader( HttpHeaders.SET_COOKIE  , cookie2.toString() );
        return result;
    }


  // [3] 내정보조회 + 쿠키
    @GetMapping("/me")
    public MemberDto getMyInfo( 

        // @CookieValue( value="쿠키명") ){ // 요청한 브라우저의 쿠키 가져오기 
        @CookieValue (value="accessToken" , required = false ) String token ){

        //1. 만약에 token 가 없다면 비로그인
        if( token == null ) return  null;

        // ********* 쿠키에 저장된 token 이용하여 회원번호 찾기 ************
        Long loginMno = jwtUtil.getMnoFromToken(token);

        // 2. 로그인 중이면 서비스에게 회원정보 요청
        return memberService.getMyInfo( loginMno );
    }


    // [4] 로그아웃 + 쿠키 
    @PostMapping ("/logout")
    public boolean logout(@CookieValue(value = "accessToken" , required = false) String accessToken,
    HttpServletResponse response){

        // 1. 만약에 accessToken이 존재하면 회원번호 조회
        if (accessToken != null) {
            Long mno = jwtUtil.getMnoFromToken(accessToken);

            // 2. 만약에 회원번호 조회 되면 레디스내 refresh 토큰 삭제
            redisTokenService.deleteRefreshToken(mno);
        }

        // 3. 쿠키 2개 삭제
        ResponseCookie cookie1 = ResponseCookie.from( "accessToken" , "" )
                                .path("/").maxAge(Duration.ofMinutes(0)) 
                                .httpOnly(true).secure(false).build();

        ResponseCookie cookie2 = ResponseCookie.from("refreshToken" , "")
                                .path("/").maxAge(Duration.ofDays(0))
                                .httpOnly(true).secure(false).build();

        response.setHeader( HttpHeaders.SET_COOKIE  , cookie1.toString() );
        response.setHeader( HttpHeaders.SET_COOKIE  , cookie2.toString() );
        return true;

    }

    // [5] access 토큰 만료될 때 Refresh 검증 후 토늨 재발급
    @PostMapping("/reissue")
    public MemberDto reissue(@CookieValue (value = "refreshToken" , required = false) String refreshToken, 
    HttpServletResponse response){ 

        // 1. refresh 토큰 가져온다 -> 존재 여부 확인
        if( refreshToken == null )return null;

        // 2. refresh 토큰내 검증하여 회원번호 조회
        Long mno = jwtUtil.getMnoFromToken(refreshToken);

        // 3. 레디스에 저장된 refresh 토큰 꺼내기
        String savedRefreshToken = redisTokenService.getRefreshToken(mno);

        // 4. 만약에 레디스가 없거나 전달받은 토큰과 다르면 -> 문제발생
        if( savedRefreshToken == null || !refreshToken.equals(savedRefreshToken) ){
            redisTokenService.deleteRefreshToken(mno);
        }

        // 5. 새로운 accessToken 가 RefreshToken 재발급
        String newAccessToken = jwtUtil.createAccessToken(mno); // mno --> jwt
        String newrefreshToken = jwtUtil.createRefreshToken(mno);

        // 6. 레디스에 새로운 refresh 토큰 저장
        redisTokenService.setRefreshToken(mno , refreshToken);

        // 7. 쿠키 설정
        ResponseCookie cookie1 = ResponseCookie.from( "accessToken" , newAccessToken )
                                .path("/").maxAge(Duration.ofMinutes(30)) // 쿠키 사용할 경로 , "/" 도메인내 전체
                                .httpOnly(true).secure(false).sameSite("Lax").build();

        ResponseCookie cookie2 = ResponseCookie.from("refreshToken" , newrefreshToken)
                                .path("/").maxAge(Duration.ofDays(7))
                                .httpOnly(true).secure(false).sameSite("Lax").build();


        // 8. header 쿠키 포함 : 2개 이상 쿠키 포한하는 경우 .addHeader()
        response.addHeader( HttpHeaders.SET_COOKIE  , cookie1.toString() );
        response.addHeader( HttpHeaders.SET_COOKIE  , cookie2.toString() );
        
        // 9. 토큰 재발급 회원정보 반환
        return memberService.getMyInfo(mno);
    }


}
