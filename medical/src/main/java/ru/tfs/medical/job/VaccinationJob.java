package ru.tfs.medical.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import ru.tfs.medical.service.VaccinationJobService;

@Component
@Slf4j
@RequiredArgsConstructor
public class VaccinationJob implements Job {

    private final VaccinationJobService vaccinationJobService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        vaccinationJobService.executeVaccinationJob();
    }
}
