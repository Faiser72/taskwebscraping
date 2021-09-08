package com.aspiresys.task1.beans.Channel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "channel")
public class ChannelBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CHANNEL_ID")
	private int channelId;

	@Column(name = "CHANNEL_NAME")
	private String channelName;

	@Column(name = "ACTIVE_FLAG")
	private int activeFlag;

}
