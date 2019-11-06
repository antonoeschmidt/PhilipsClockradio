package dk.dtu.philipsclockradio;

import android.os.Handler;

import java.util.Date;

public class StateAlarmPlaying extends StateAdapter {
    private static final long NINE_MINS_IN_MS = 540000;
    private Date alarm;
    private Runnable runnable;
    private Handler handler = new Handler();

    public StateAlarmPlaying(Date alarm) {
        this.alarm = alarm;
    }

    @Override
    public void onEnterState(ContextClockradio context) {
        System.out.println("*** ALARM RINGING ***");
        context.ui.turnOffLEDBlink();
        context.ui.turnOffTextBlink();
    }

    @Override
    public void onClick_Snooze(final ContextClockradio context) {
        runnable = new Runnable() {
            @Override
            public void run() {
                context.setState(new StateAlarmPlaying(alarm));
            }
        };
        handler.postDelayed(runnable, NINE_MINS_IN_MS);
        System.out.println("Alarm Snoozed");
        context.setState(new StateStandby(context.getDate()));
    }
}
