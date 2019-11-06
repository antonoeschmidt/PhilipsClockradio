package dk.dtu.philipsclockradio;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StateSetAlarm extends StateAdapter {

    private Date mTime;
    private Date oldTime;
    private Date alarm1;
    private Date alarm2;

    /*
    public StateSetAlarm() {
        this.time = new Date();

    }
     */

    @Override
    public void onEnterState(ContextClockradio context) {
        Calendar date = Calendar.getInstance();
        date.set(2019, 1, 1, context.getTime().getHours(), context.getTime().getMinutes());
        oldTime = date.getTime();

        mTime = context.getTime();
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
    public void onClick_AL1(ContextClockradio context) {
        alarm1 = mTime;
        String time = new SimpleDateFormat("HH:mm").format(alarm1);
        System.out.println("Alarm 1 sat to " + time);
        context.setState(new StateStandby(oldTime));
        context.ui.turnOnLED(2);
    }

    @Override
    public void onClick_AL2(ContextClockradio context) {
        alarm2 = mTime;
        String time = new SimpleDateFormat("HH:mm").format(alarm2);
        System.out.println("Alarm 2 sat to " + time);
        context.setState(new StateStandby(oldTime));
        context.ui.turnOnLED(5);
    }

    @Override
    public void onExitState(ContextClockradio context) {
        context.ui.turnOffTextBlink();
        context.setTime(oldTime);
        context.updateDisplayTime();
    }
}
