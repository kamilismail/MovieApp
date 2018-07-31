package com.KamilIsmail.MovieApp.scheduled;

import com.KamilIsmail.MovieApp.Constants;
import com.KamilIsmail.MovieApp.DAO.ReminderDao;
import com.KamilIsmail.MovieApp.entities.RemindersEntity;
import com.KamilIsmail.MovieApp.entities.UserEntity;
import com.KamilIsmail.MovieApp.repository.ReminderRepository;
import com.KamilIsmail.MovieApp.repository.UserRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class RemindersScheduler {
    private static final Logger log = LoggerFactory.getLogger(RemindersScheduler.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    @Autowired
    ReminderRepository reminderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReminderDao reminderDao;

    //wysyłanie powiadomień ok godzinę przed seansem w tv
    @Scheduled(cron = "0 0/10 * 1/1 * ?") //every 10 minutes
    public void checkReminders() {
        log.info("Checking reminders at: " + dateFormat.format(new Date()), dateFormat.format(new Date()));
        List<RemindersEntity> remindersEntityList = reminderRepository.findAll();
        for (RemindersEntity remindersEntity : remindersEntityList) {
            try {
                Date parsedDate = dateFormat.parse(dateFormat.format(new Date()));
                Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                if (remindersEntity.getData().after(timestamp) && remindersEntity.getReminded()) { //usuwanie powiadomienia, które już było
                    log.info("Removing past reminders: " + dateFormat.format(new Date()), dateFormat.format(new Date()));
                    reminderDao.deleteReminder(remindersEntity.getUserId(), remindersEntity.getMovieId());
                    log.info("Removed past reminders: " + dateFormat.format(new Date()), dateFormat.format(new Date()));
                }
            } catch (Exception e) {
            }
        }
        remindersEntityList = reminderRepository.findAll();
        Date parsedDate = null;
        try {
            Date data = new Date();
            parsedDate = dateFormat.parse(dateFormat.format(data));
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            data = DateUtils.addHours(data, 1);
            parsedDate = dateFormat.parse(dateFormat.format(data));
            Timestamp timestamp2 = new java.sql.Timestamp(parsedDate.getTime()); //data zwiększona o godzinę
            for (RemindersEntity remindersEntity : remindersEntityList) {
                if (remindersEntity.getData().after(timestamp) && remindersEntity.getData().before(timestamp2) && !remindersEntity.getReminded()) {
                    log.info("Sending reminder: " + dateFormat.format(new Date()), dateFormat.format(new Date()));
                    //TODO - wysyłanie powiadomień do użytkowników
                    UserEntity userEntity = userRepository.findByUserId(remindersEntity.getUserId());
                    try {
                        Constants constants = new Constants();
                        RestTemplate restTemplate = new RestTemplate();
                        HttpHeaders httpHeaders = new HttpHeaders();
                        httpHeaders.set("Authorization", "key=" + constants.getFcmKey());
                        httpHeaders.set("Content-Type", "application/json");
                        JSONObject msg = new JSONObject();
                        JSONObject json = new JSONObject();

                        msg.put("title", "Movie incoming!");
                        msg.put("station", remindersEntity.getTvstationsByTvstationId().getName());
                        msg.put("name", remindersEntity.getMoviesByMovieId().getMovieName());
                        msg.put("time", remindersEntity.getData().toString());

                        json.put("data", msg);
                        json.put("to", userEntity.getNotificationId());

                        HttpEntity<String> httpEntity = new HttpEntity<String>(json.toString(), httpHeaders);
                        String response = restTemplate.postForObject(constants.getFcmURL(), httpEntity, String.class);
                        log.info("Notification sent: " + json.toString() + "\n Response: " + response, dateFormat.format(new Date()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
