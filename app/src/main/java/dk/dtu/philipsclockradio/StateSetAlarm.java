package dk.dtu.philipsclockradio;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StateSetAlarm extends StateAdapter {

    private Date mTime;
    private Date alarm1;
    private Date alarm2;

    @Override
    public void onEnterState(ContextClockradio context) {
        mTime = context.getTime();
        context.ui.turnOnTextBlink();
        context.updateDisplayTime();
    }

    @Override
    public void onClick_Hour(ContextClockradio context) {
        //Gets current timestamp (Date)

        mTime.setTime(mTime.getTime() + 3600000);
        context.updateDisplayTime();

        //tid virker ikke helt rigtigt her + lys mangler
        String time = new SimpleDateFormat("HH:mm").format(context.getTime());
        System.out.println(time);
    }

    @Override
    public void onClick_Min(ContextClockradio context) {
        //Gets current timestamp (Date)
        mTime.setTime(mTime.getTime() + 60000);
        context.updateDisplayTime();
    }

    @Override
    public void onClick_AL1(ContextClockradio context) {
        alarm1 = mTime;
        String time = new SimpleDateFormat("HH:mm").format(alarm1);
        System.out.println("Alarm 1 sat to " + time);
        context.setState(new StateStandby(context.getTime()));
    }

    @Override
    public void onClick_AL2(ContextClockradio context) {
        alarm2 = mTime;
        String time = new SimpleDateFormat("HH:mm").format(alarm2);
        System.out.println("Alarm 2 sat to " + time);
        context.setState(new StateStandby(context.getTime()));
    }

    @Override
    public void onExitState(ContextClockradio context) {
        context.ui.turnOffTextBlink();
        context.updateDisplayTime();
    }
}
