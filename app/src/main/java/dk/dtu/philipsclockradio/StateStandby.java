package dk.dtu.philipsclockradio;

import android.os.Handler;
import java.util.Date;

public class StateStandby extends StateAdapter {

    private Date mTime;
    private static Handler mHandler = new Handler();
    private ContextClockradio mContext;

    StateStandby(Date time){
        mTime = time;
    }

    //Opdaterer hvert 60. sekund med + 1 min til tiden
    Runnable mSetTime = new Runnable() {

        @Override
        public void run() {
            try {
                long currentTime = mContext.getDate().getTime();
                mTime.setTime(currentTime + 60000);
                mContext.setTime(mTime);
            } finally {
                mHandler.postDelayed(mSetTime, 60000);
            }
        }
    };

    void startClock() {
        mSetTime.run();
        mContext.isClockRunning = true;
    }

    void stopClock() {
        mHandler.removeCallbacks(mSetTime);
        mContext.isClockRunning = false;
    }

    @Override
    public void onEnterState(ContextClockradio context) {
        //Lokal context oprettet for at Runnable kan få adgang
        mContext = context;

        context.updateDisplayTime();
        if(!context.isClockRunning){
            startClock();
        }

        if (context.ui.isMusicPlaying()) {
            context.ui.toggleRadioPlaying();
        }

    }

    @Override
    public void onLongClick_Preset(ContextClockradio context) {
        stopClock();
        context.setState(new StateSetTime());
    }

    @Override
    public void onClick_Power(ContextClockradio context) {
        context.setState(new StateFM());
        context.ui.turnOnLED(1);
    }

    @Override
    public void onClick_Sleep(ContextClockradio context) {
        context.setState(new StateSleeper());
    }

    @Override
    public void onLongClick_AL1(ContextClockradio context) {
        context.setState(new StateSetAlarm());
    }

    @Override
    public void onLongClick_AL2(ContextClockradio context) {
        context.setState(new StateSetAlarm());
    }
}
