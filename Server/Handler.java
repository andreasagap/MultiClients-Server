
package server;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static server.Server.accounts;
/**
 *
 * @author Andreas Agapitos
 */
public class Handler extends Thread{
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String user;
    private volatile boolean isRunning = true;

    /**
     *
     * @param clientSocket
     */
    public Handler(Socket clientSocket) {
        try {
            socket = clientSocket;
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

        } catch (IOException e) {
            System.out.println(e);
        }
    }

   /**
     *
     * 
     */
    @Override
    public void run(){
        String line="";
        while (isRunning) {   
            
            
            try {
                line = in.readLine();
                check(line);
            } catch (IOException ex) {
                LogOut();
            }
            if (line.toLowerCase().equalsIgnoreCase("exit"))
            {
                     
                     System.out.println("Disconnect");
                     break;
             
            }
        }
        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }
    /**
     *Check the options
     * 
     */
    private void check(String line) throws IOException
    {
        if(line.toLowerCase().equalsIgnoreCase("login"))
        {
            login();    
        }
        else if(line.toLowerCase().equalsIgnoreCase("signin") )
        {
            register();
        }
        else if(line.toLowerCase().equalsIgnoreCase("newemail") )
        {
            newemail();
        }
        else if(line.toLowerCase().equalsIgnoreCase("showemails"))
        {
            showEmails();
        }
        else if(line.toLowerCase().equalsIgnoreCase("deleteemail") )
        {
            deleteEmail();
        }
        else if(line.toLowerCase().equalsIgnoreCase("logout"))
        {
            LogOut();
        }
        else if(line.toLowerCase().equalsIgnoreCase("reademail") )
        {
            ReadEmail();
        }
        else
        {
            isRunning=false;
        }
        
    }

    /**
     *
     * @param u
     * @return the account object
     */
    public Account getAccount(String u)
    {
        for(int i=0;i<accounts.size();i++)
        {
            if(accounts.get(i).getusername().equalsIgnoreCase(u))
            {
                return accounts.get(i);
            }
        }
        return null;
    }
    /**
     *
     * Sign Up function
     */
    private void register() throws IOException
    {

        user=in.readLine();
        String password=in.readLine();
        if(CheckIfAccountExist(user))
        {
            out.println("Wrong");
            out.flush();
            
        }
        else{
            accounts.add(new Account(user,password));
            out.println("Done");
            out.flush();
        }
        
    }
    /**
     *Check if the account exists
     * 
     */
    private boolean CheckIfAccountExist(String u)
    {
        if(u==null)
        {
            return false;
        }
        for(int i=0;i<accounts.size();i++)
        {
                if(accounts.get(i).getusername().equalsIgnoreCase(u))
                {
                    return true;
                }
        }
        return false;
    }
    /**
     *
     * Find the position of an user
     */
    private int positionOfUser(String receiver)
    {
        for(int i=0;i<accounts.size();i++)
        {
                if(accounts.get(i).getusername().equalsIgnoreCase(receiver))
                {
                    return i;
                }
        }
        return -1;
    }
    /**
     *Login Function
     * 
     */
    private void login() throws IOException
    {
           
            Account a;
            user=in.readLine();
            a=getAccount(user);
            if(a==null)
            {
                 out.println("Wrong");
                 out.flush();
            }
            else if(!in.readLine().equalsIgnoreCase(a.getpassword()))
            {
                 out.println("Wrong");
                 out.flush();
            }
            else{
                out.println("Done");
                out.flush();
            }
            
    }
    /**
     *LogOut function
     * 
     */
    private void LogOut()
    {
        user="";
    }
    /**
     *
     * This function reads an email
     */
    private void ReadEmail() throws IOException
    {
        int userp=positionOfUser(user);
        if(accounts.get(userp).mailsize()==0)
        {
            //out.println("No email");
        }
        else
        {
            int ID=Integer.parseInt(in.readLine())-1;
            out.println(accounts.get(userp).getEmail(ID).getSender());
            out.println(accounts.get(userp).getEmail(ID).getSubject());
            out.println(accounts.get(userp).getEmail(ID).getMain());
            accounts.get(userp).getEmail(ID).setNew(false);
        
        }
        out.flush();
    }
    /**
     *
     * This function writes a new email
     */
    private void newemail() throws IOException
    {
        
        String receiver=in.readLine();
            if(!CheckIfAccountExist(receiver))
            {
                out.println("No account");
                out.flush();

            }
            else{
                out.println("Done");
                out.flush();
                String subject=in.readLine();
                String MainBody=in.readLine();
                accounts.get(positionOfUser(receiver)).addEmail(user, receiver, subject, MainBody);
            }
        
    }
    /**
     *This function shows emails
     * 
     */
    private void showEmails() throws IOException
    {
        int userp=positionOfUser(user);
        out.write(accounts.get(userp).mailsize());
        String isNew;
        for(int i=0;i<accounts.get(userp).mailsize();i++)
        {
            isNew="    ";
            if(accounts.get(userp).getEmail(i).getNew())
            {
                isNew="[NEW]";
            }
            out.println(i+1+ " .   "+isNew+"     "+accounts.get(userp).getEmail(i).getSender()+"       "+accounts.get(userp).getEmail(i).getSubject());
        }
        out.flush();
        
    }
    /**
     *
     *  This function deletes an email
     */
    private void deleteEmail() throws IOException
    {
        int userp=positionOfUser(user);
        if(accounts.get(userp).mailsize()==0)
        {
            out.println("No email");
        }
        else
        {
            int ID=Integer.parseInt(in.readLine())-1;
            accounts.get(userp).deleteEmail(ID);
        }      
        out.flush();
    }
}
