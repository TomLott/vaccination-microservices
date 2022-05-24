package ru.tfs.medical.config;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tfs.medical.job.VaccinationJob;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@Configuration
public class QuartzConfig {

    public static final int SCHEDULER_TIME_INTERVAL = 5;

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob().ofType(VaccinationJob.class)
                .storeDurably()
                .withIdentity("Vaccination_Job")
                .withDescription("Sending vaccination information to qrcode-service")
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail jobDetail) {
        return TriggerBuilder.newTrigger().forJob(jobDetail)
                .withIdentity("Vaccination_Trigger")
                .withSchedule(simpleSchedule().repeatForever().withIntervalInSeconds(SCHEDULER_TIME_INTERVAL))
                .build();
    }
}
