package org.zerock.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class SimpleRestcontroller {
    @GetMapping("/ajax")
    public String ajax(){
        return "rest/ajax";
    }

    @PostMapping("/send")
    @ResponseBody // 리턴객체를 응답http body에 담으라는 의미(view x)
    public Person test(@RequestBody Person p) { //Request - GET,POST // 요청http body에 오는 내용을 person p 에 받겠다는 의미
        System.out.println("p = " + p);
        p.setName("ABC");
        p.setAge(p.getAge()+10);

        return p;
    }
    //JSON을 이용해서 서버와 통신하는 방법 (ajax사용)
    //**ajax는 비동기 연결**(싱글쓰레드 동기화를 깨서 요청후 응답(콜백)이 올때까지 대기하는것이 아닌 다른 작업을 바로 시작할수 있음)
    //1.클라이언트에서 ajax를 이용해서 http요청(POST) 형식으로 바디에 js -> JSON(JSON.stringify)를 담아 서버에 전송
    //2.서버에서 해당 url로 맵핑된 컨트롤러에 전달
    //3.@RequestBody가 붙은 매개변수 객체에 jackson databind 라이브러리가 JSON->java객체 로 변환해줌
    //4.서버에서 작업후 해당 컨트롤러 메서드에 @ResponseBody가 붙은 경우 http응답 바디에 직접 리턴값을 전달할수 있음
    //5.리턴값으로 작업완료한 java객체를 리턴하면 jackson databind 라이브러리가 java객체->JSON으로 변환해줌
    //6.http응답 바디를 클라이언트에 리턴
}
