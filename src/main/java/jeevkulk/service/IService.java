package jeevkulk.service;

public interface IService {

    /**
     * Default method called before doProcess which may be overridden at service level
     */
    public default void preProcess() {
    }

    /**
     * Main processor where the logic resides - needs to be implemented at service level
     */
    public Object doProcess() throws Exception;

    /**
     * Default method called after doProcess which may be overridden at service level
     */
    public default void postProcess() {
    }

    /**
     * execute method will be called by the framework - which in turn calls the lifecycle methods
     */
    public default void execute() throws Exception {
        preProcess();
        doProcess();
        postProcess();
    }

}
