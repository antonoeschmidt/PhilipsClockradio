package dk.dtu.philipsclockradio;

import java.util.ArrayList;

public class StateFM extends StateAdapter {
    private double frequency;
    private int currentChannel = 0;
    static ArrayList autoFMChannels = new ArrayList<Double>() {
        {
            add(91.5);
            add(93.0);
            add(98.5);
            add(102.5);
            add(107.0);
        }
    };

    public StateFM() {
        this.frequency = 88;
    }

    @Override
    public void onClick_Hour(ContextClockradio context) {
        //change FM frequency up
        frequency -= 0.5;
        if (frequency < 88) {
            frequency = 108;
        }
        System.out.println("Current FM frequency: " + frequency + " MHz");
    }

    @Override
    public void onClick_Min(ContextClockradio context) {
        //change FM frequency down
        frequency += 0.5;
        if (frequency > 108) {
            frequency = 88;
        }
        System.out.println("Current FM frequency: " + frequency + " MHz");
    }

    @Override
    public void onClick_Power(ContextClockradio context) {
        //setState AM
        context.setState(new StateAM());
        context.ui.turnOffLED(1);
        context.ui.turnOnLED(4);
    }

    @Override
    public void onLongClick_Power(ContextClockradio context) {
        //setState Standby
        context.setState(new StateStandby(context.getTime()));
        context.ui.turnOffLED(1);
    }

    @Override
    public void onLongClick_Hour(ContextClockradio context) {
        //autotunes to previous station
        currentChannel--;
        if (currentChannel < 0) {
            currentChannel = autoFMChannels.size()-1;
        }
        frequency = (double) autoFMChannels.get(currentChannel);
        System.out.println("FM channel " + (currentChannel+1) +" at " + frequency + " MHz");
    }

    @Override
    public void onLongClick_Min(ContextClockradio context) {
        //autotunes to next station
        currentChannel++;
        if (currentChannel > autoFMChannels.size()-1) {
            currentChannel = 0;
        }
        frequency = (double) autoFMChannels.get(currentChannel);
        System.out.println("FM channel " + (currentChannel+1) +" at " + frequency + " MHz");
    }

    @Override
    public void onLongClick_Preset(ContextClockradio context) {
        context.setState(new StateSetRadioChannel(frequency,this));
        context.ui.turnOnLEDBlink(1);
    }
}