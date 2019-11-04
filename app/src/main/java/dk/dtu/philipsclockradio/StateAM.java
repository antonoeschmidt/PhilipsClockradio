package dk.dtu.philipsclockradio;

import java.util.ArrayList;

public class StateAM extends StateAdapter {
    private double frequency;
    private int currentChannel = 0;
    private ArrayList autoChannels = new ArrayList<Double>() {
        {
            add(91.5);
            add(93.0);
            add(98.5);
            add(102.5);
            add(107.0);
        }
    };

    public StateAM() {
        this.frequency = 88;
    }

    @Override
    public void onClick_Hour(ContextClockradio context) {
        //change AM frequency up
        frequency -= 0.5;
        if (frequency < 88) {
            frequency = 108;
        }
        System.out.println("Current AM frequency: " + frequency + " kHz");
    }

    @Override
    public void onClick_Min(ContextClockradio context) {
        //change AM frequency down
        frequency += 0.5;
        if (frequency > 108) {
            frequency = 88;
        }
        System.out.println("Current AM frequency: " + frequency + " kHz");
    }

    @Override
    public void onClick_Power(ContextClockradio context) {
        //setState AM
        context.setState(new StateFM());
        context.ui.turnOffLED(4);
        context.ui.turnOnLED(1);
    }

    @Override
    public void onLongClick_Power(ContextClockradio context) {
        //setState Standby
        context.setState(new StateStandby(context.getTime()));
        context.ui.turnOffLED(4);
    }

    @Override
    public void onLongClick_Hour(ContextClockradio context) {
        //autotunes to previous station
        currentChannel--;
        if (currentChannel < 0) {
            currentChannel = 4;
        }
        frequency = (double) autoChannels.get(currentChannel);
        System.out.println("AM channel " + (currentChannel+1) +" at " + frequency + " kHz");
    }

    @Override
    public void onLongClick_Min(ContextClockradio context) {
        //autotunes to next station
        currentChannel++;
        if (currentChannel > 4) {
            currentChannel = 0;
        }
        frequency = (double) autoChannels.get(currentChannel);
        System.out.println("AM channel " + (currentChannel+1) +" at " + frequency + " kHz");
    }
}
