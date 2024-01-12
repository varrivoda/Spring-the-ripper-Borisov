package org.example.quoters;

public class ProfilingController implements ProfilingControllerMBean {
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private boolean enabled;
}
