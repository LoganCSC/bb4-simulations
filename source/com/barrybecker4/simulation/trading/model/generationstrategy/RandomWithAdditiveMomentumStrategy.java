/*
 * Copyright by Barry G. Becker, 2015. Licensed under MIT License: http://www.opensource.org/license.MIT
 */

package com.barrybecker4.simulation.trading.model.generationstrategy;

import com.barrybecker4.ui.components.NumberInput;

import javax.swing.*;
import java.awt.*;

/**
 * @author Barry Becker
 */
public class RandomWithAdditiveMomentumStrategy extends AbstractGenerationStrategy {

    private static final double DEFAULT_PERCENT_INCREASE = 0.04;
    private static final double DEFAULT_PERCENT_DECREASE = 0.03;
    private static final double DEFAULT_MOMENTUM_FACTOR = 1.0;

    public double percentIncrease = DEFAULT_PERCENT_INCREASE;
    public double percentDecrease = DEFAULT_PERCENT_DECREASE;
    public double momentumFactor = DEFAULT_MOMENTUM_FACTOR;

    private double lastPercentChange = 0;

      /** Amount to increase after each time period if heads   */
    private NumberInput percentIncreaseField;

    /** Amount to decrease after each time period if tails  */
    private NumberInput percentDecreaseField;

    /** Amount to decrease after each time period if tails  */
    private NumberInput momentumFactorField;


    public String getName() {
        return "random additive momentum";
    }

    public String getDescription() {
        return "The random movement at each step is added to momentumFactor time the random movement at the last step.";
    }


    @Override
    public double calcNewPrice(double stockPrice) {
        double percentChange =
                Math.random() > 0.5 ? percentIncrease : -percentDecrease;

        double newPercentChange = Math.random() * percentChange;
        double actualPercentChange =
                momentumFactor * lastPercentChange + (1.0 - momentumFactor) * newPercentChange;

        stockPrice *= (1.0 + actualPercentChange);
        lastPercentChange = actualPercentChange;

        return stockPrice;
    }


    /** The UI to allow the user to configure the options */
    public JPanel getOptionsUI() {
        JPanel strategyPanel = new JPanel();
        strategyPanel.setLayout(new BoxLayout(strategyPanel, BoxLayout.Y_AXIS));
        strategyPanel.setBorder(BorderFactory.createEtchedBorder());

        percentIncreaseField =
                new NumberInput("% to increase each time period if heads (0 - 100): ",
                        100 * percentIncrease,
                        "Amount to increase after each time period if coin toss is heads.",
                        0, 100, false);
        percentDecreaseField =
                new NumberInput("% to decrease each time period if tails (0 - 100): ",
                        100 * percentDecrease,
                        "Amount to decrease after each time period if coin toss is tails.",
                        -100, 100, false);

        momentumFactorField =
                new NumberInput("Weight to give the last percent change (0, 1):",
                        momentumFactor,
                        "The amount of weight to give the last percent change when calculating the new one. " +
                         "Zero is no impact, 1 means the last value is used as is.",
                        0, 2, false);

        percentIncreaseField.setAlignmentX(Component.CENTER_ALIGNMENT);
        percentDecreaseField.setAlignmentX(Component.CENTER_ALIGNMENT);
        momentumFactorField.setAlignmentX(Component.CENTER_ALIGNMENT);

        strategyPanel.add(percentIncreaseField);
        strategyPanel.add(percentDecreaseField);
        strategyPanel.add(momentumFactorField);

        return strategyPanel;
    }

    /** Call when OK button is pressed to persist selections */
    public void acceptSelectedOptions() {

        this.percentDecrease = percentDecreaseField.getValue() / 100.0;
        this.percentIncrease = percentIncreaseField.getValue() / 100.0;
        this.momentumFactor = momentumFactorField.getValue();
    }
}
