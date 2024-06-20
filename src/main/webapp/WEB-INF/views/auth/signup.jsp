<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Your Star</title>
<link rel="stylesheet" href="/css/style.css">
<link rel="stylesheet"
	href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
	integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p"
	crossorigin="anonymous" />
</head>
<body>
	<div class="container">
		<main class="loginMain">
			<!--회원가입섹션-->
			<section class="login">
				<article class="login__form__container">

					<!--회원가입 폼-->
					<div class="login__form">
						<!--로고-->
						<h1>
							<img src="/images/logo.jpg" alt="">
						</h1>
						<!--로고end-->

						 <!--회원가입 인풋-->
                        <form class="login__input"  action="/auth/signup" method="post">
                            <input type="text" name="username" value="${signupDto.username}" placeholder="유저네임" required="required"  maxlength="30"/>
                            <span>${valid_username}</span>
                            <input type="password" name="password" value="${signupDto.password}" placeholder="패스워드" required="required" />
                            <span>${valid_password}</span>
                            <input type="text" name="name" value="${signupDto.name}" placeholder="이름" required="required" />
                            <span>${valid_name}</span>
                            <input type="email" name="email" value="${signupDto.email}" placeholder="이메일" required="required" />
                            <span>${valid_email}</span>
                            <button>가입</button>
                        </form>
                        <!--회원가입 인풋end-->
					</div>
					<!--회원가입 폼end-->

					<!--계정이 있으신가요?-->
					<div class="login__register">
						<span>계정이 있으신가요?</span> <a href="/auth/signin">로그인</a>
					</div>
					<!--계정이 있으신가요?end-->

				</article>
			</section>
		</main>
	</div>
</body>
</html>