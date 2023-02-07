package com.xzavier0722.mc.plugin.slimeglue;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GlueLogger {

    private final Logger logger;
    private boolean verbose = false;

    public GlueLogger(Logger logger) {
        this.logger = logger;
    }

    public void i(String msg) {
        logger.log(Level.INFO, msg);
    }

    public void w(String msg) {
        logger.log(Level.WARNING, msg);
    }

    public void e(String msg) {
        logger.log(Level.SEVERE, msg);
    }

    public void v(String msg) {
        if (!verbose) {
            return;
        }
        logger.log(Level.INFO, msg);
    }

    void setVerbose(boolean verbose) {
        // TODO: config this in somewhere....
        this.verbose = verbose;
    }

}
