
/*************************************************************************
 *  Compilation:  javac Stopwatch.java
 *
 *
 *************************************************************************/

/**
 *  The <tt>Stopwatch</tt> data type is for measuring
 *  the time that elapses between the start and end of a
 *  programming task (wall-clock time).
 *
 *  See {@link StopwatchCPU} for a version that measures CPU time.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */


public class Stopwatch { 

    private final long start;

    /**
     * Initialize a stopwatch object.
     */
    public Stopwatch() {
        start = System.currentTimeMillis();
    } 


    /**
     * Returns the elapsed time (in seconds) since this object was created.
     */
    public double elapsedTime() {
        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;
    }
    public String toTime(){
        double time = elapsedTime();
        String seconds = "" + (int) (time%60);
        String minutes = "" + (int)((time%3600)/60);
        String hours = "" + (int)(time/3600);
        if (seconds.length() == 1)
            seconds = "0" + seconds;
        if (minutes.length() == 1)
            minutes = " 0" + minutes;
        if (hours.length() == 1)
            hours = "0" + hours;
        return "Time: " + hours + ":" + minutes + ":" + seconds;
    }
} 
