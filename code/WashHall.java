public class WashHall
{

    // Returns true on success initialize
    // Returns false if the initialize failed
    public boolean initializeWash()
    {
        System.out.println("WashHall > initializeWash()");
        return(Math.random()* 101 < 100);
    }

    // Returns true on diagnostics success
    // Returns false if the diagnostics failed
    public boolean runDiagnostics()
    {
        System.out.println("WashHall > runDiagnostics()");
        return(Math.random()* 1001 < 1000);
    }
}
