package biz.netcentric.cq.tools.actool.history;

import org.apache.jackrabbit.vault.fs.api.ProgressTrackerListener;
import org.apache.jackrabbit.vault.packaging.PackageException;

public class ProgressTrackerListenerInstallationLogger extends PersistableInstallationLogger {

    private final ProgressTrackerListener listener;

    public ProgressTrackerListenerInstallationLogger(ProgressTrackerListener listener) {
        super();
        this.listener = listener;
    }

    @Override
    protected void addWarning(String warning) {
        listener.onMessage(ProgressTrackerListener.Mode.TEXT, MSG_IDENTIFIER_WARNING + warning, "");
        super.addWarning(warning);
    }

    @Override
    protected void addMessage(String message) {
        listener.onMessage(ProgressTrackerListener.Mode.TEXT, message, "");
        super.addMessage(message);
    }

    @Override
    public void addError(String error) {
        listener.onError(ProgressTrackerListener.Mode.TEXT, MSG_IDENTIFIER_ERROR + error, null);
        super.addError(error);
    }

    @Override
    public void addError(String error, Throwable t) {
        Exception e;
        if (t instanceof Exception) {
            e = (Exception) t;
        } else {
            e = new PackageException("Unexpected low-level error during AC Tool installation: " + t.getMessage(), t);
        }
        listener.onError(ProgressTrackerListener.Mode.TEXT, error, e);
        super.addError(error, e);
    }

    @Override
    protected void addVerboseMessage(String message) {
        // no logging of verbose log in installation log (verbose log can be looked up via JMX)
        super.addVerboseMessage(message);
    }

}
