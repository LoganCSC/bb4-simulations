/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.simulation.reactiondiffusion;

import com.barrybecker4.common.format.FormatUtil;
import com.barrybecker4.simulation.common.Profiler;


/**
 * Singleton for RD profiling.
 *
 * @author Barry Becker
 */
public class RDProfiler extends Profiler {

    protected static final String CONCURRENT_CALCULATION = "concurrent_calculation";
    protected static final String COMMIT_CHANGES = "commit_changes";

    private static RDProfiler instance;

    private int numFrames;


    /**
     * @return singleton instance.
     */
    public static RDProfiler getInstance() {
        if (instance == null) {
            instance = new RDProfiler();
        }
        return instance;
    }

    /**
     * Private constructor. Use getInstance instead.
     */
    protected RDProfiler() {
        super();
        add(CONCURRENT_CALCULATION, CALCULATION);
        add(COMMIT_CHANGES, CALCULATION);

    }

    @Override
    public void print() {

        super.print();
        double calcTime = getCalcTime();
        double renderingTime = getRenderingTime();
        printMessage("Number of Frames: " + FormatUtil.formatNumber(numFrames));
        printMessage("Calculation time per frame (sec):" + FormatUtil.formatNumber(calcTime / numFrames));
        printMessage("Rendering time per frame   (sec):" + FormatUtil.formatNumber(renderingTime / numFrames));
        super.print();
    }

    @Override
    public void resetAll()  {
        super.resetAll();
        numFrames = 0;
    }

    public void startConcurrentCalculationTime() {
        this.start(CONCURRENT_CALCULATION);
    }

    public void stopConcurrentCalculationTime() {
        this.stop(CONCURRENT_CALCULATION);
    }
    public void startCommitChangesTime() {
        this.start(COMMIT_CHANGES);
    }

    public void stopCommitChangesTime() {
        this.stop(COMMIT_CHANGES);
    }

}
