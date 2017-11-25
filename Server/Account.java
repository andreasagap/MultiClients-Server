
package server;

import java.util.ArrayList;

/**
 * The class of account
 * @author Andreas Agapitos
 */
public class Account {
    private String username,password;
    private ArrayList<Email> mailbox;
    
    /**
     *
     * @param u
     * @param p
     */
    public Account(String u,String p)
    {
        username=u;
        password=p;
        mailbox=new ArrayList<Email>();
    }

    /**
     *
     * @return username
     */
    public String getusername()
    {
        return username;
    }

    /**
     *
     * @return password
     */
    public String getpassword()
    {
        return password;
    }

    /**
     *
     * @param u
     */
    public void setusername(String u)
    {
        username=u;
    }

    /**
     *
     * @param p
     */
    public void setpassword(String p)
    {
       password=p;
    }

    /**
     *
     * @param sender
     * @param receiver
     * @param subject
     * @param Mainbody
     */
    public void addEmail(String sender,String receiver,String subject,String Mainbody)
    {
        mailbox.add(new Email(true,sender,receiver,subject,Mainbody));
    }

    /**
     *
     * @param i
     * @return
     */
    public Email getEmail(int i)
    {
        return mailbox.get(i);
    }

    /**
     *
     * @return
     */
    public int mailsize()
    {
        return mailbox.size();
    }

    /**
     *
     * @param ID
     */
    public void deleteEmail(int ID)
    {
        mailbox.remove(ID);
    }
    
}

