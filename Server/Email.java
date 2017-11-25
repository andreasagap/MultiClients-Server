
package server;

/**
 *
 * @author Andreas Agapitos
 */
public class Email {
    private boolean isNew;
    private String sender,receiver,subject,mainbody;

    /**
     *
     * @param N
     * @param s
     * @param r
     * @param su
     * @param m
     */
    public Email (boolean N,String s,String r,String su,String m)
    {
        isNew=N;
        sender=s;
        receiver=r;
        subject=su;
        mainbody=m;
    }

    /**
     *
     * @return isNew
     */
    public boolean getNew()
    {
        return isNew;
    }

    /**
     *
     * @return sender
     */
    public String getSender()
    {
        return sender;
    }

    /**
     *
     * @return subject
     */
    public String getSubject()
    {
        return subject;
    }

    /**
     *
     * @return main body
     */
    public String getMain()
    {
        return mainbody;
    }

    /**
     *
     * @param N
     */
    public void setNew(boolean N)
    {
        isNew=N;
    }
}
