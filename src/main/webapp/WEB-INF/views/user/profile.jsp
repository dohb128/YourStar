<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%@ include file="../layout/header.jsp"%>

<!--프로필 섹션-->
<section class="profile">
	<!--유저정보 컨테이너-->
	<div class="profileContainer">

		<!--유저이미지-->
		<div class="profile-left">
			<div class="profile-img-wrap story-border" <c:if test="${profileDto.id eq principal.id}"></c:if>>

				<form id="userProfileImageForm">
					<input type="file" name="profileImageFile" style="display: none;"
						id="userProfileImageInput" />
				</form>

				<img class="profile-image" src="/upload/${profileDto.profileImage}"
					onerror="this.src='/images/nico.png'" id="userProfileImage" />

			</div>
		</div>
		<!--유저이미지end-->

		<!--유저정보 및 사진등록 구독하기-->
		<div class="profile-right">
			<div class="name-group">
				<h2>${profileDto.name}</h2>

                <!-- 회원 id와 접속된 id가 같으면 사진 등록 -->
				<c:choose>
					<c:when test="${profileDto.id eq userId}">
						<button class="cta" onclick="location.href='/image/upload'">사진등록</button>
						<button class="modi" onclick="popup('.modal-info')">
						<i class="fas fa-cog"></i>
						</button>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${profileDto.subscribeState}">
								<button class="cta blue" onclick="toggleSubscribe(${profileDto.id}, this)">구독취소</button>
							</c:when>
							<c:otherwise>
								<button class="cta" onclick="toggleSubscribe(${profileDto.id}, this)">구독하기</button>
							</c:otherwise>
						</c:choose>

					</c:otherwise>
				</c:choose>
			</div>

			<div class="subscribe">
				<ul>
					<li><a href=""> 게시물<span>${profileDto.imageCount}</span>
					</a></li>
					<li><a href="javascript:subscribeInfoModalOpen(${profileDto.id}, ${principal.id});"> 구독정보<span>${profileDto.subscribeCount}</span>
					</a></li>
				</ul>
			</div>
			<div class="state">
				<h4>${profileDto.bio}</h4>
				<h4>${profileDto.website}</h4>
			</div>
		</div>
		<!--유저정보 및 사진등록 구독하기-->

	</div>
</section>


<!--게시물컨섹션-->
<section id="tab-content">
	<!--게시물컨컨테이너-->
	<div class="profileContainer">
		<!--그냥 감싸는 div (지우면이미지커짐)-->
		<div id="tab-1-content" class="tab-content-item show">
			<!--게시물컨 그리드배열-->
			<div class="tab-1-content-inner">

				<!--아이템들-->

				<c:forEach var="image" items="${profileDto.images}">
					<div class="img-box">
						<a href=""> <img src="/upload/${image.postImage}">
						</a>
						<div class="comment" <c:if test= "${principal.id eq profileDto.id }" > </c:if>>
							<a href="#" class=""> <i class="fas fa-heart"></i><span>0</span>
							</a>
						</div>
					</div>
				</c:forEach>


				<!--아이템들end-->
			</div>
		</div>
	</div>
</section>

<!--로그아웃, 회원정보변경 모달-->
<div class="modal-info" onclick="modalInfo()">
	<div class="modal">
		<button onclick="location.href='/user/update/${id}'">회원정보 변경</button>
		<button onclick="location.href='/logout'">로그아웃</button>
		<button onclick="closePopup('.modal-info')">취소</button>
	</div>
</div>
<!--로그아웃, 회원정보변경 모달 end-->

<!--프로필사진 바꾸기 모달-->
<div class="modal-image" onclick="modalImage()">
	<div class="modal">
		<p>프로필 사진 바꾸기</p>
		<button onclick="profileImageUpload(${profileDto.id}, ${principal.id})">사진 업로드</button>
		<button onclick="closePopup('.modal-image')">취소</button>
	</div>
</div>

<!--프로필사진 바꾸기 모달end-->

<!--게시글 수정, 삭제 모달-->
<div class="modal-post" onclick="modalPost()">
	<div class="modal">
		<button id= "postUpdate" onclick="location.href=''">게시글 수정</button>
		<button id= "postDelete" onclick="location.href=''">게시글 삭제</button>
		<button onclick="closePopup('.modal-post')">취소</button>
	</div>
</div>
<!--게시물 수정, 삭제 모달 end-->


<div class="modal-subscribe">
	<div class="subscribe">
		<div class="subscribe-header">
			<span>구독정보</span>
			<button onclick="modalClose()">
				<i class="fas fa-times"></i>
			</button>
		</div>

		<div class="subscribe-list" id="subscribeModalList">



		</div>
	</div>

</div>



<script src="/js/profile.js"></script>

<%@ include file="../layout/footer.jsp"%>