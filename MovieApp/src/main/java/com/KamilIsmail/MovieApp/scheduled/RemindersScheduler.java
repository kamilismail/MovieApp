package com.KamilIsmail.MovieApp.scheduled;

import com.KamilIsmail.MovieApp.Constants;
import com.KamilIsmail.MovieApp.DAO.ReminderDao;
import com.KamilIsmail.MovieApp.DTO.firebase.FirebaseDTO;
import com.KamilIsmail.MovieApp.DTO.firebase.FirebaseResult;
import com.KamilIsmail.MovieApp.DTO.firebase.Notification;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class RemindersScheduler {

    public interface FirebaseApi {
        @Headers({
                "Content-Type: application/json",
                "Authorization: key=" + "AAAAcPwCoSg:APA91bFTSpK9EoM4Tcyu57lu3NvkSzdZCLFxyuQs48lKznzViG4kiK-TAp4nqvm_KHmbt8qQLswSR4UUCveG0LE_adNyH4qn-2zZw56rFB5fMBS2pzeSt-ZlmQea82LLZQCEB7RXrQxS_CTyvAO4dL3RqlyOFP9zWw"
        })
        @POST("send")
        Call<FirebaseResult> sendReminder(@Body FirebaseDTO firebaseDTO);
    }

    private static final Logger log = LoggerFactory.getLogger(RemindersScheduler.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

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
        Date parsedDate;
        try {
            Date data = new Date();
            parsedDate = dateFormat.parse(dateFormat.format(data));
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            data = DateUtils.addHours(data, 1);
            parsedDate = dateFormat.parse(dateFormat.format(data));
            Timestamp timestamp2 = new java.sql.Timestamp(parsedDate.getTime()); //data zwiększona o godzinę
            for (RemindersEntity remindersEntity : remindersEntityList) {
                Timestamp reminderDate = remindersEntity.getData();
                Date date2 = dateFormat.parse(dateFormat.format(reminderDate));
                if (dateFormat.parse(dateFormat.format(remindersEntity.getData())).after(dateFormat.parse(dateFormat.format(timestamp)))
                        && dateFormat.parse(dateFormat.format(remindersEntity.getData())).before(data) && !remindersEntity.getReminded()) {
                    log.info("Sending reminder: " + dateFormat.format(new Date()), dateFormat.format(new Date()));
                    //TODO - wysyłanie powiadomień do użytkowników
                    UserEntity userEntity = userRepository.findByUserId(remindersEntity.getUserId());

                    String body = remindersEntity.getMoviesByMovieId().getMovieName() + " - "
                            + remindersEntity.getTvstationsByTvstationId().getName() + " at "
                            + remindersEntity.getData().toString().substring(11,16);

                    log.info("Json body: " + body, dateFormat.format(new Date()));
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Constants.getFcmURL())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    FirebaseApi firebaseApi = retrofit.create(FirebaseApi.class);
                    FirebaseDTO firebaseDTO = new FirebaseDTO(new Notification("Movie incoming!", body), userEntity.getNotificationId());
                    Call<FirebaseResult> call = firebaseApi.sendReminder(firebaseDTO);
                    call.enqueue(new Callback<FirebaseResult>() {
                        @Override
                        public void onResponse(Call<FirebaseResult> call, Response<FirebaseResult> response) {
                            FirebaseResult resp = response.body();
                        }

                        @Override
                        public void onFailure(Call<FirebaseResult> call, Throwable t) {
                        }
                    });
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
