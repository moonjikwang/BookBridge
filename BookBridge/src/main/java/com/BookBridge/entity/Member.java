package com.BookBridge.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class Member extends BaseEntity{

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id; //사용자에게 고유하게 부여되는 ID
	private String email;
	@Column(nullable = false)
	private String nickName;
	@Column(nullable = false)
	private String password;
}
