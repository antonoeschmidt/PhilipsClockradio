package dk.dtu.philipsclockradio;

import java.util.ArrayList;

import javax.xml.transform.Source;

public class StateSetRadioChannel extends StateAdapter {

    private double frequency;
    private State previousState;
    private int currentChannel = 0;

    public StateSetRadioChannel(double frequency, State previousState) {
        this.frequency = frequency;
        this.previousState = previousState;
    }


    @Override
    public void onClick_Hour(ContextClockradio context) {
        currentChannel--;

        if (previousState.getClass().equals(StateFM.class)) {
            if (currentChannel < 0) {
                currentChannel = StateFM.autoFMChannels.size();
                System.out.println("New channel selected at " + (currentChannel + 1));
            } else {
                System.out.println("Selected channel " + (currentChannel+1));
            }
        } else if (previousState.getClass().equals(StateAM.class)) {
            if (currentChannel < 0) {
                currentChannel = StateAM.autoAMChannels.size();
                System.out.println("New channel selected at " + (currentChannel + 1));
            } else {
                System.out.println("Selected channel " + (currentChannel+1));
            }
        }



    }

    @Override
    public void onClick_Min(ContextClockradio context) {
        currentChannel++;

        if (previousState.getClass().equals(StateFM.class)) {
            if (currentChannel > StateFM.autoFMChannels.size()) {
                currentChannel = 0;
                System.out.println("Selected channel " + (currentChannel+1));
            } else if (currentChannel == StateFM.autoFMChannels.size()) {
                System.out.println("New channel selected at " + (currentChannel + 1));
            } else {
                System.out.println("Selected channel " + (currentChannel+1));
            }
        } else if (previousState.getClass().equals(StateAM.class)) {
            if (currentChannel > StateAM.autoAMChannels.size()) {
                currentChannel = 0;
                System.out.println("Selected channel " + (currentChannel+1));
            } else if (currentChannel == StateAM.autoAMChannels.size()) {
                System.out.println("New channel selected at " + (currentChannel + 1));
            } else {
                System.out.println("Selected channel " + (currentChannel+1));
            }
        }
    }

    @Override
    public void onClick_Preset(ContextClockradio context) {
        if (previousState.getClass().equals(StateFM.class)) {
            if (currentChannel == StateFM.autoFMChannels.size()) {
                StateFM.autoFMChannels.add(frequency);
            } else {
                StateFM.autoFMChannels.set(currentChannel, frequency);
            }
            System.out.println("Channel saved at " + (currentChannel+1));
        } else if (previousState.getClass().equals(StateAM.class)) {
            if (currentChannel == StateFM.autoFMChannels.size()) {
                StateAM.autoAMChannels.add(frequency);
            } else {
                StateAM.autoAMChannels.set(currentChannel, frequency);
            }
            System.out.println("Channel saved at " + (currentChannel+1));
        }
        context.setState(previousState);
    }

    @Override
    public void onExitState(ContextClockradio context) {
        context.ui.turnOffLEDBlink();
    }
}
