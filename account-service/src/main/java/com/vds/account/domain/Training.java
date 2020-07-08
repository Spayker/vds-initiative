package com.vds.account.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "training")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Training {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO, generator = "native" )
    @GenericGenerator( name = "native", strategy = "native" )
    private long id;

    private String deviceName;

    private String deviceFirmware;

    private Date createdDate;

    private int time;

    private String type;

    private int calories;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="ACCOUNT_ID")
    private Account account;

}
