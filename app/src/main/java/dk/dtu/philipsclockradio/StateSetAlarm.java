package dk.dtu.philipsclockradio;

import android.os.Handler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StateSetAlarm extends StateAdapter {

    private static final long ONE_DAY_IN_MS = 86400000;
    private Date mTime;
    private Date oldTime;
    private Date alarm1;
    private Date alarm2;
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    public void onEnterState(ContextClockradio context) {
        Calendar date = Calendar.getInstance();
        date.set(2019, 1, 1, context.getDate().getHours(), context.getDate().getMinutes());
        oldTime = date.getTime();

        mTime = context.getDate();
        context.ui.turnOnTextBlink();
        context.updateDisplayTime();
    }

    @Override
    public void onClick_Hour(ContextClockradio context) {
        mTime.setTime(mTime.getTime() + 3600000);
        context.setTime(mTime);
        context.updateDisplayTime();
    }

    @Override
    public void onClick_Min(ContextClockradio context) {
        mTime.setTime(mTime.getTime() + 60000);
        context.setTime(mTime);
        context.updateDisplayTime();
    }

    @Override
    public void onClick_AL1(final ContextClockradio context) {
        alarm1 = mTime;
        String time = new SimpleDateFormat("HH:mm").format(alarm1);
        System.out.println("Alarm 1 sat to " + time);
        context.setState(new StateStandby(oldTime));
        context.ui.turnOnLED(2);
        setAlarm(context,alarm1);
    }

    @Override
    public void onClick_AL2(ContextClockradio context) {
        alarm2 = mTime;
        String time = new SimpleDateFormat("HH:mm").format(alarm2);
        System.out.println("Alarm 2 sat to " + time);
        context.setState(new StateStandby(oldTime));
        context.ui.turnOnLED(5);
        setAlarm(context,alarm2);
    }

    @Override
    public void onExitState(ContextClockradio context) {
        context.ui.turnOffTextBlink();
        context.setTime(oldTime);
        context.updateDisplayTime();
    }

    private void setAlarm(final ContextClockradio context, final Date alarm) {
        long diff = mTime.getTime() - oldTime.getTime();
        //this fixes if a date is picked for the day after
        if (diff < 0) {
            diff = diff + ONE_DAY_IN_MS;
        }

        runnable = new Runnable() {
            @Override
            public void run() {
                context.setState(new StateAlarmPlaying(alarm));
            }
        };
        handler.postDelayed(runnable, diff);
    }
}
