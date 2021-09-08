package com.aspiresys.task1.beans.Channel;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Data
@Table(name = "channelContentHistory")
public class ChannelContentHistoryBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CHANNEL_CONTENT_ID")
	private int channelContentId;

	@Column(name = "PROGRAMEE")
	private String programee;

	@Column(name = "START")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm:ss")
	@Temporal(TemporalType.TIME)
	private Date start;

	@Column(name = "END")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm:ss")
	@Temporal(TemporalType.TIME)
	private Date end;

	@Column(name = "DESCRIPTION")
	private String description;

	@ManyToOne
	@JoinColumn(name = "CHANNEL_ID")
	private ChannelBean channelId;

	@Column(name = "BACK_UP_DATE")
	private LocalDateTime backUpDate;

}
