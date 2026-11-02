final class CLink
        implements Runnable
{
    private static boolean isRunning = false;
    private static String s_url = "";
    public static void startVisit(String url)
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CLink.jpp
        // create the server
        s_url = url;
        new Thread(new CLink()).start();
        isRunning = true;
    }

    public void accessNetwork()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CLink.jpp
        try
        {
            IMARPG22.s_midlet.platformRequest(s_url);
        }
        catch (Exception ex)
        {
            CGame.Destroy();
        }
        CGame.Destroy();
    }

    public void run()
    { //@in X:\RPG22_CMCC\trunk\SourceCode\Common\CLink.jpp
        while (isRunning)
        {
            try
            {
                accessNetwork();
                Thread.sleep(1000);
            }
            catch (Exception e)
            {
            }
        }
    }
}
