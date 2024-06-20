package com.startup.yourstar.entity;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter 	//lombok에 의해 getter setter 생성
@Setter
@Entity
@org.hibernate.annotations.DynamicUpdate // 나중에 필요해서 미리 설정 / 수정가능하게 해줌
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // 주요키로 사용되는 id 필드. 데이터베이스에서 자동으로 증가함

	public User(int id) {
		this.id = id;
	}

	@Column(length = 50, unique = true, nullable = false)
	private String username; // 사용자의 이름으로, 길이는 50자로 제한되며 중복되지 않도록 설정되어 있음

	//@JsonIgnore
	@Column(nullable = false)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password; // 사용자의 암호. JSON 직렬화 시 무시되며, 역직렬화 시에도 액세스가 제한되도록 설정됨

	@Column(nullable = false)
	private String name; // 사용자의 실제 이름

	private String email; // 사용자의 이메일 주소

	private String phone; // 사용자의 전화번호

	private String gender; // 사용자의 성별

	@Column(name = "profile_image")
	private String profileImage; // 사용자의 프로필 이미지 URL 또는 파일 경로

	//@ColumnDefault("'USER'")
	private String role; // 사용자의 역할을 나타내는 필드. 기본값은 'USER'로 설정되어 있음

	@CreationTimestamp
	@Column(name = "create_date")
	private Timestamp createDate; // 엔터티가 생성된 시간을 나타내는 필드. 데이터베이스에 삽입될 때 자동으로 현재 시간이 설정됨

	private String website;

	private String bio;

	public void update(String password, String name, String phone, String gender, String website, String bio, String email) {

		this.password = password;
		this.name = name;
		this.phone = phone;
		this.gender = gender;
		this.website = website;
		this.bio = bio;
		this.email = email;

	}

	public void updateProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@OrderBy("id DESC")
	private List<Image> images;
}