package com.aspiresys.task1.controller.DataFromWebSite;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aspiresys.task1.beans.Channel.ChannelBean;
import com.aspiresys.task1.beans.Channel.ChannelContentBean;
import com.aspiresys.task1.beans.Channel.ChannelContentHistoryBean;
import com.aspiresys.task1.beans.response.Task1Response;
import com.aspiresys.task1.model.service.channelContent.ChannelContentService;
import com.aspiresys.task1.model.service.dataFromWeb.DataFromWebService;

@RestController
@RequestMapping("/data")
public class TvsDataController {

	@Autowired
	private DataFromWebService service;

	@Autowired
	private ChannelContentService channelContentService;

	private static Logger log = LoggerFactory.getLogger(TvsDataController.class);

//	@RequestMapping("/tvsdata")
	@Scheduled(cron = "0 0 6,18 * * *")
//	@Scheduled(initialDelay = 10000, fixedDelay = 30000)
	public void getData() {

		Document channelUrl;
		try {
			channelUrl = Jsoup.connect("https://tvscheduleindia.com/channel").get();// to connect with website and to
																					// gvet data
			String channelString = channelUrl.select("div.card-body a").text();
			String[] channels = channelString.split("  ");// to split channels based on spaces
			Predicate<String> isEmpty = str -> str.equals(""); // predicate to check the channel is empty or not
			Function<String, String> f = (channel) -> channel.replace(" ", "-").toLowerCase(); // function to create url
																								// of inner channel
																								// content by replacing
																								// spaces with '-' and
																								// making lowercase
			for (String channel : channels) {

				if (!isEmpty.test(channel)) {

					if (service.isPresent(channel, "ChannelBean")) {

						ChannelBean channelBean = new ChannelBean();
						channelBean.setActiveFlag(1);
						channelBean.setChannelName(channel);
						int id = service.save(channelBean);
						log.info("Saved Successfully & Saved Appointment Id is: " + id);

						if (id != 0) {
							channelBean.setChannelId(id);
							System.out.println("**************&&&&&&" + channel + "&&&&&&&**********************");
							Document channelContent = Jsoup
									.connect("https://tvscheduleindia.com/channel" + "/" + f.apply(channel)).get(); // to
																													// get
																													// the
																													// channel
																													// content
//							Function<String,String> f1= (desc)->desc.substring(0, 300)

							for (org.jsoup.nodes.Element row : channelContent.select("table tr")) {

								ChannelContentBean ccBean = new ChannelContentBean();

								ccBean.setProgramee(row.select("td:nth-of-type(1)").text());
								ccBean.setStart(StringToTimeConverter(row.select("td:nth-of-type(2)").text()));
								ccBean.setEnd(StringToTimeConverter(row.select("td:nth-of-type(3)").text()));
								ccBean.setDescription(row.select("td:nth-of-type(4)").text());
								ccBean.setChannelId(channelBean);

								int ids = channelContentService.save(ccBean);
								System.out.println("Programee : " + row.select("td:nth-of-type(1)").text());
								System.out
										.println("==================================================================");

//								To check content is already there or not
//								if (channelContentService.isContentPresent(row.select("td:nth-of-type(1)").text(),
//										channelBean, "ChannelContentBean")) {} else {
//									System.out.println("the program is already there for channel : "
//											+ channelBean.getChannelName());
//								}
							}
						}
					} else {
						log.info("Channel is already Present ");
					}

				} else {
					log.info("Empty channel");
				}
			}
			printNextTriggerTime("0 0 10,16 * * *", LocalDateTime.of(2021, 8, 31, 15, 30, 0));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void printNextTriggerTime(String cronExpression, LocalDateTime currentTime) {
		CronSequenceGenerator generator = new CronSequenceGenerator(cronExpression);
		Date d = Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant());
		for (int i = 0; i < 10; i++) {
			d = generator.next(d);
			System.out.println(d);
		}
	}

//	This Api will run once in a week i.e, on sunday 10am, And this Api is used to move existing data in 
//	channel content table to channel content history table, The main in tention of this table is to 
//	increase the performance by reducing data in channel content table

//	@RequestMapping("/weeklyBackUp")
	@Scheduled(cron = "0 0 10 * * SUN")
	@SuppressWarnings("unchecked")
	public Task1Response weeklyBackUp() {
		Task1Response response = new Task1Response();

//		to get all existing data from channel content table
		List<ChannelContentBean> channelContent = (List<ChannelContentBean>) channelContentService
				.getAll("ChannelContentBean");
		if (channelContent.size() > 0) {

			for (ChannelContentBean content : channelContent) {

				ChannelContentHistoryBean historyBean = new ChannelContentHistoryBean();

				historyBean.setChannelId(content.getChannelId());
				historyBean.setDescription(content.getDescription());
				historyBean.setEnd(content.getEnd());
				historyBean.setProgramee(content.getProgramee());
				historyBean.setStart(content.getStart());
				historyBean.setBackUpDate(LocalDateTime.now());

				int id = channelContentService.save(historyBean); // saving channel content in history table
				if (id != 0) {
//					After saving successfully deleting that content from channel content table
					if (channelContentService.deleteChannelContent(content)) {
						response.setMessage("Successfully Deleted id : " + content.getChannelContentId()
								+ "in channel content table");
						log.info("Successfully Deleted id : " + content.getChannelContentId()
								+ "in channel content table");

					} else {
						response.setMessage("Something went wrong !, Could not delete id: "
								+ content.getChannelContentId() + "in channel content table");
						log.info("Something went wrong !, Could not delete id: " + content.getChannelContentId()
								+ "in channel content table");
					}
					response.setMessage("Successfully Moved id : " + content.getChannelContentId()
							+ "in channel content history table");
					log.info("Successfully Moved id : " + content.getChannelContentId()
							+ "in channel content history table");
				} else {
					response.setMessage("Could not save Channel Content in history Table");
					log.info("Could not save Channel Content in history Table");
				}
			}
			response.setListObject(channelContent);
		} else {
			response.setSuccess(false);
			response.setMessage("channelContent List is Empty");
			log.info("channelContent List is Empty");
		}
		return response;
	}

// this method id used to convert the string to time, and it accepts string as a argument as returns Date.
	public Date StringToTimeConverter(String time) {
		if (time != null && !time.isEmpty()) {
			String myTime = time;
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
			Date date = null;
			try {
				date = sdf.parse(myTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String formattedTime = sdf.format(date);
			return date;
		}
		return null;
	}

//	Api to get All Channels
	@SuppressWarnings("unchecked")
	@GetMapping(path = "/channelList", produces = MediaType.APPLICATION_JSON_VALUE)
	public Task1Response getAllChannels(Task1Response response) {
		List<ChannelBean> channelList = (List<ChannelBean>) service.getAll("ChannelBean");
		if (channelList.size() > 0) {
			response.setListObject(channelList);
			response.setSuccess(true);
		} else {
			response.setSuccess(false);
			response.setMessage("List is empty");
		}
		return response;
	}

//	Api to get All Channel contents
	@SuppressWarnings("unchecked")
	@GetMapping(path = "/channelContentList", produces = MediaType.APPLICATION_JSON_VALUE)
	public Task1Response getAllChannelContents(Task1Response response) {
		List<ChannelContentBean> channelContentList = (List<ChannelContentBean>) channelContentService
				.getAll("ChannelContentBean");
		if (channelContentList.size() > 0) {
			response.setListObject(channelContentList);
			response.setSuccess(true);
		} else {
			response.setSuccess(false);
			response.setMessage("List is empty");
		}
		return response;
	}

//	Delete Channel
	@PutMapping(path = "/deleteChannel", produces = MediaType.APPLICATION_JSON_VALUE)
	public Task1Response deleteChannel(@RequestParam("channelId") int channelId, Task1Response response) {
		ChannelBean channelBean = (ChannelBean) service.getById("ChannelBean", channelId);
		if (channelBean != null) {
			channelBean.setActiveFlag(0);
			if (service.update(channelBean)) {
				response.setSuccess(true);
				response.setMessage("Deleted Successfully");
			} else {
				response.setSuccess(false);
				response.setMessage("Deletion Failed");
			}
		} else {
			response.setSuccess(false);
			response.setMessage("This Channel is Not Exist");
		}
		return response;
	}

//	Get ChannelContent By  Channel id
	@SuppressWarnings("unchecked")
	@GetMapping(path = "/getChannelContentByChannelId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Task1Response getChannelContentByChannelId(@PathVariable int id, Task1Response response) {
		List<ChannelContentBean> channelContent = (List<ChannelContentBean>) channelContentService
				.getContentByChannelId("ChannelContentBean", id);
		if (channelContent != null) {
			response.setSuccess(true);
			response.setListObject(channelContent);
		} else {
			response.setSuccess(false);
			response.setMessage("There is no content for this  channel");
		}
		return response;
	}

//	Get ChannelContent between program start time.
	@SuppressWarnings("unchecked")
	@GetMapping(path = "/getChannelContentBtwnTime/{fromTime}/{toTime}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Task1Response getChannelContentBtwnTime(@PathVariable String fromTime, @PathVariable String toTime,
			Task1Response response) {
		List<ChannelContentBean> channelContent = (List<ChannelContentBean>) channelContentService
				.getChannelContentBtwnTime("ChannelContentBean", StringToTimeConverter(fromTime),
						StringToTimeConverter(toTime));
		if (channelContent != null) {
			response.setSuccess(true);
			response.setListObject(channelContent);
		} else {
			response.setSuccess(false);
			response.setMessage("There is no content for this  channel");
		}
		return response;
	}

}
