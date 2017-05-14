package org.agile4tyro.nytcoder.db;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;

/**
 */
@Entity
@Getter
@Setter
@Builder
@ToString
public class User {

	@Id
	@GeneratedValue
	private long Id;
	private String username;
	private String password;

	@Builder.Default
	private Date createdOn = new Date();

	@Builder.Default
	private Boolean deleted = Boolean.FALSE;
@Tolerate
	public User() {
	}
}
