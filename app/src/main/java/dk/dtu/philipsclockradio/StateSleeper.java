package dk.dtu.philipsclockradio;

import android.os.Handler;

import java.util.ArrayList;

public class StateSleeper extends StateAdapter {
    private Handler handler = new Handler();
    private Runnable runnable;
    private int chosenTime = 0;
    private ArrayList sleeperTime = new ArrayList<String>() {
        {
            add("Off");
            add("15");
            add("30");
            add("60");
            add("90");
            add("120");
        }
    };

    @Override
    public void onClick_Sleep(final ContextClockradio context) {
        chosenTime++;

        if (chosenTime > sleeperTime.size()-1) {
            chosenTime = 0;
        }
        if (!sleeperTime.get(chosenTime).equals("Off")) {
            context.ui.turnOnLED(3);
        } else {
            context.ui.turnOffLED(3);
        }
        System.out.println("Sleeper sat to " + sleeperTime.get(chosenTime));

        if (runnable == null) {
            runnable = new Runnable() {
                public void run() {
                    context.setState(new StateStandby(context.getTime()));
                    if (sleeperTime.get(chosenTime).equals("Off")) {
                        context.ui.turnOffLED(3);
                    }

                }
            };
        }
        //stops the runnable if button is pressed multiple times
        //only the last button press will be handled, and the state will change 5 secs after the last press
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 5000);
    }
}
