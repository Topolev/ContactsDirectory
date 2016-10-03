package by.topolev.contacts.qurtz;

import by.topolev.contacts.qurtz.jobs.SendBirthdayListViaEmailJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.*;


/**
 * Created by Vladimir on 20.09.2016.
 */
public class JobsFactory {

    private static final Logger LOG = LoggerFactory.getLogger(JobsFactory.class);

    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private static Scheduler sched;

    public static void initJobs()  throws SchedulerException{
        LOG.debug("initJobs()");

        sched = schedulerFactory.getScheduler();
        sched.start();

        JobDetail job = newJob(SendBirthdayListViaEmailJob.class)
                .withIdentity("sendBirthdayListViaEmail","group1")
                .build();

        Trigger trigger = newTrigger()
                .withIdentity("once_a_day_every_day","group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(24*60*60)
                        .repeatForever())
                .build();

        sched.scheduleJob(job, trigger);
    }

    public static void destroyJobs() throws SchedulerException {
        if(sched != null) {
            LOG.info("Start stopping the birthday notification job.");
            sched.shutdown(true);
            LOG.info("The birthday notification job is stopped.");
        } else {
            LOG.error("No running birthday notification job.");
        }

    }

}
