<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
  <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
</head>
<body>
<h2>{name:"abc", age:10}</h2>
<button id="sendBtn" type="button">SEND</button>
<h2>Data From Server :</h2>
<div id="data"></div>
<script>
  $(document).ready(function(){
    let person = {name:"abc",age:10};
    let person2 = {};
    $("#sendBtn").click(function(){
      $.ajax({
        //http형식 지정
        url: '/send',
        type:'POST',
        headers: {"content-type":"application/json"}, //json 타입으로 http요청(POST) 바디에 담아서 보낸다
        data: JSON.stringify(person), // 서버에게 보내는 데이터 http요청바디
        dataType: 'text', // 서버에게 전송받을 데이터 http응답바디
        //http

        //callback함수
        success : function(result){
          person2 = JSON.parse(result);
          alert("received="+result);
          $("#data").html("name="+person2.name+", age="+person2.age);
        },
        error: function(){
          alert("error");
        }
        //callback
      }); // $.ajax()
      alert("the request is sent(비동기라 기다리지 않음)")
    })
  })
</script>
</body>
</html>