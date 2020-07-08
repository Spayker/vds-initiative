package com.vds.account.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "account")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO, generator = "native" )
	@GenericGenerator( name = "native", strategy = "native" )
	private long id;

	private String name;

	private String email;

	private Date createdDate;

	private Date modifiedDate;

	private int age;

	private Gender gender;

	private int weight;

	private int height;

}
