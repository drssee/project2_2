<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
  <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
</head>
<body>

  <!--댓글작성/수정-->
  <div>
    댓글내용 작성/수정 = <input type="text" name="comment" id="comment"><br>
    <button id="writeBtn" type="button">작성</button>
    <button id="modBtn" type="button">수정</button>
  </div>

  <hr>

  <!--댓글리스트-->
  <div>
    <h2>Data From Server :</h2>
    <div id="commentList"></div>
  </div>


  <!--대댓글작성-->
  <div id="replyForm" style="display:none">
    <input type="text" name="replyComment">
    <button type="submit" id="repBtn">등록</button>
  </div>

<script>
  let bno = 45; // 임시 테스트용 // 글목록 들어갈때 bno 같이 넘겨줘서 얻어 올 수 있음

  $(document).ready(function(){

    //댓글목록 불러오기
    showList(bno);

    //댓글작성/수정
    $("#writeBtn").click(function(){

      let commentVO = {comment : $("#comment").val()}
      alert(commentVO+' = commentVO 내용');
      writeComment(bno,commentVO);
    })

    //댓글삭제
    //동적 생성 요소(작성된 html엔 없던 태그들)에 이벤트 거는 방법
    $("#commentList").on("click",".delBtn",function(){
      alert(this);
      let cno = $(this).parent().attr("data-cno");
      let bno = $(this).parent().attr("data-bno");
      deleteComment(cno,bno);
    })

    //댓글수정1(댓글수정 모드변경)
    $("#commentList").on("click",".modBtn",function(){
      alert(this);
      let cno = $(this).parent().attr("data-cno");
      let bno = $(this).parent().attr("data-bno");
      let comment = $(this).prev().prev().text();
      alert(comment);
      $("input[name=comment]").val(comment);
      $("#modBtn").attr("data-cno",cno);
      $("#modBtn").attr("data-bno",bno);
    })

    //댓글수정2(입력폼에 수정할 댓글 입력 후 전송)
    $("#modBtn").click(function(){
      let cno = $(this).attr("data-cno");
      let bno = $(this).attr("data-bno");
      let commentVO = {bno:$(this).attr("data-bno"),comment:$("#comment").val()}
      updateComment(cno,bno,commentVO);
    });

    //대댓글작성1(대댓글작성 모드변경)
    $("#commentList").on("click",".repBtn",function(){ // 대댓글 //동적 생성 요소에 이벤트 거는 방법
      //1.대댓글 인풋창을 옮기고 (버튼 부모 li의 뒤에)
      $("#replyForm").appendTo($(this).parent());
      //2.대댓글을 입력할 폼을 보여주고,
      $("#replyForm").css("display","block");
      //3.태그에 값을 저장?

      let cno = $(this).parent().attr("data-cno");
      let bno = $(this).parent().attr("data-bno");
      let pcno = $(this).parent().attr("data-pcno"); //부모 cno 를 만듦 // cno==pcno 칼럼들이 부모

      $("#replyForm").attr("data-cno",cno);
      $("#replyForm").attr("data-bno",bno);
      $("#replyForm").attr("data-pcno",pcno);
    })


    //대댓글작정2(입력폼에 작성할 댓글 입력 후 전송)
    /////////////////////////////// 대댓글 **selectAll if(pcno=0,cno,pcno) order by pcno , cno**////////
    //댓글을 insert할때 pcno 입력값이 없으면 0으로 입력 <-부모댓글
    //댓글 목록을 selectAll할때 pcno가 0인 칼럼은 pcno=cno값을 넣어줌 즉 cno==pcno면 부모댓글
    //////////////////////////////////////////////////////////////////////////////////////////////////
    //댓글을 insert할때 pcno 입력값이 있으면(댓글 입력 js객체에만 값이 있음) 같은 pcno 대댓글끼리 같은 그룹화
    $("#repBtn").click(function(){
      // let cno = $(this).parent().attr("data-cno");
      let bno = $(this).parent().attr("data-bno");
      let pcnoStr = $(this).parent().attr("data-pcno");
      let pcnoInt = Number(pcnoStr);
      alert(typeof pcnoInt)
      let commentVO = {pcno: Number(pcnoStr) ,comment : $("input[name=replyComment]").val()}
      // let commentVO = {pcno: $(this).attr("data-pcno") ,comment : $("input[name=replyComment]").val()}
      alert(commentVO+' = commentVO 내용');
      writeReply(bno,pcnoInt,commentVO);
    })

  }) //document.ready




///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




  //댓글리스트가져오는 함수
  let showList = function(bno) {
    $.ajax({
      //http형식 지정
      url: '/comments?bno='+bno,
      type:'GET',
      headers: {"content-type":"application/json"}, //json 타입으로 http요청(POST) 바디에 담아서 보낸다
      //http

      //callback함수
      success : function(result){
        $("#commentList").html(toHtml(result));
      },
      error: function(){
        alert("error");
      }
      //callback
    }); // $.ajax()
    alert("the request is sent(비동기라 기다리지 않음)")
  }

  //댓글작성 함수
  let writeComment = function(bno,commentVO) {
    $.ajax({
      url: '/comments?bno='+bno,
      type: 'POST',
      headers: {"content-type":"application/json"},
      data: JSON.stringify(commentVO), // 서버에게 보내는 데이터 http요청바디


      success : function(result){
        alert(result+' 작성 성공');
        showList(bno);
      },
      error: function() {
        alert("error")
      }
    })
  }

  //댓글삭제 함수
  let deleteComment = function(cno,bno) {
    alert(cno);
    alert(bno);
    $.ajax({
      url: '/comments/'+cno+'?bno='+bno,
      type: 'DELETE',
      headers: {"content-type":"application/json"},

      success : function(result){
        alert(result);
        $("#commentList").html(toHtml(showList(bno)));
      },
      error: function(){
        alert("error");
      }
    })
  }

  //댓글수정 함수
  let updateComment = function(cno,bno,commentVO) {
    alert(cno);
    alert(commentVO);
    $.ajax({
      url: '/comments/'+cno,
      type: 'PATCH',
      headers: {"content-type":"application/json"},
      data: JSON.stringify(commentVO), // 서버에게 보내는 데이터 http요청바디

      success : function(result) {
        alert(result);
        $("#commentList").html(toHtml(showList(bno)));
      },
      error: function () {
        alert("error");
      }
    })
  }


  //대댓글(답글)작성 함수
  let writeReply = function(bno,pcno,commentVO) {
    alert('bno:'+bno+' pcno:'+pcno+' commentVO:'+commentVO);
    $.ajax({
      url: '/comments?bno='+bno,
      type: 'POST',
      headers: {"content-type":"application/json"},
      data: JSON.stringify(commentVO), // 서버에게 보내는 데이터 http요청바디


      success : function(result){
        alert(result+' 작성 성공');
        showList(bno);
      },
      error: function() {
        alert("error");
      }
    })
    //대댓글 입력폼 위치 초기화
    $("#replyForm").css("display","none");
    $("input[name=replyComment]").val('');
    $("#replyForm").appendTo("body");
  }


  //배열로 들어온 (js 객체를 html 문자로) 바꿔주는 함수
  let toHtml = function(comments) {
    let tmp = "<ul>";

    comments.forEach(function(comments) {
      tmp += '<li data-cno=' +comments.cno
      tmp += ' data-pcno='+comments.pcno
      tmp += ' data-bno='+comments.bno +'>' //사용자 정의 data-x 속성

      if(comments.cno!=comments.pcno){ // cno!=pcno가 다른 대댓글들 구분위함
        tmp += 'ㄴ'
      }

      tmp += ' commenter=<span class="commenter">'+comments.commenter + '</span>'
      tmp += ' comment=<span class="comment">' + comments.comment + '</span>'
      tmp += ' reg_date='+comments.regDate
      tmp += ' up_date='+comments.up_date
      tmp += '<button class = "delBtn">delete</button>'
      tmp += '<button class = "modBtn">modify</button>'
      tmp += '<button class = "repBtn">대댓글</button>'
      tmp += '</li>'
    })
    return tmp + "</ul>";
  }

  // if($("#comment").val().trim()==''){ // 입력이 없을때 검증용
  //   alert('댓글을 입력해주세요');
  //   $("#comment").focus();
  //   return;
  // }

</script>
</body>
</html>