// Copyright by Barry G. Becker, 2012. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.simulation.verhulst;

import com.barrybecker4.ui.sliders.SliderGroup;
import com.barrybecker4.ui.sliders.SliderGroupChangeListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Dynamic controls for the RD simulation that will show on the right.
 * They change the behavior of the simulation while it is running.
 * @author Barry Becker
 */
public class DynamicOptions extends JPanel
                            implements SliderGroupChangeListener {

    private List<CreatureSliderGroup> sliderGroups_;

    /**
     * Constructor
     */
    public DynamicOptions(VerhulstSimulator simulator) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEtchedBorder());
        setPreferredSize(new Dimension(300, 300));

        sliderGroups_ = new ArrayList<CreatureSliderGroup>();
        for (Population creaturePop : simulator.getCreatures()) {
            CreatureSliderGroup group = new CreatureSliderGroup(creaturePop);
            group.addSliderChangeListener(this);
            sliderGroups_.add(group);
            add(group);
        }

        JPanel fill = new JPanel();
        fill.setPreferredSize(new Dimension(1, 1000));
        add(fill);
    }

    public void reset() {
        for (SliderGroup group : sliderGroups_)  {
            group.reset();
        }
    }

    /**
     * One of the sliders was moved.
     */
    public void sliderChanged(int sliderIndex, String sliderName, double value) {

        for (CreatureSliderGroup group : sliderGroups_)  {
            group.checkSliderChanged(sliderName, value);
        }
    }

}
