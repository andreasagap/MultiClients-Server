/* 
 * The MIT License
 *
 * Copyright 2017 Andreas Agapitos.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
    private boolean firstMenu=true;
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
        out.write(0);
        out.println("Hello, you connected as a guest.");
        out.flush();
        String line="";
        while (isRunning) {   
            
            
            try {
                line = in.readLine();
                check(line);
            } catch (IOException ex) {
                Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
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
        if(line.toLowerCase().equalsIgnoreCase("login") && firstMenu)
        {
            login();    
        }
        else if(line.toLowerCase().equalsIgnoreCase("signin") && firstMenu)
        {
            register();
        }
        else if(line.toLowerCase().equalsIgnoreCase("newemail") && !firstMenu)
        {
            newemail();
        }
        else if(line.toLowerCase().equalsIgnoreCase("showemails") && !firstMenu)
        {
            showEmails();
        }
        else if(line.toLowerCase().equalsIgnoreCase("deleteemail") && !firstMenu)
        {
            deleteEmail();
        }
        else if(line.toLowerCase().equalsIgnoreCase("logout") && !firstMenu)
        {
            LogOut();
        }
        else if(line.toLowerCase().equalsIgnoreCase("reademail") && !firstMenu)
        {
            ReadEmail();
        }
        else
        {
            if(firstMenu)
            {
                out.println("Select Login, SignIn or Exit:");
                out.flush();
            }
            else
            {
                out.println("Select a valid option:");
                out.flush();
            }
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
        out.println("signup");
        out.println("Choose a username: " );
        user=in.readLine();
        while(CheckIfAccountExist(user))
        {
            out.println("This username exist - Please choose again:");
            out.flush();
            user=in.readLine();
        }
        out.println("Choose the password: " );
        String password=in.readLine();
        accounts.add(new Account(user,password));
        out.println("Welcome "+user +"!");
        out.println(1);
        out.flush();
        firstMenu=false;
        
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
            out.println("login");
            out.println("Write the username: " );
            Account a;
            user=in.readLine();
            a=getAccount(user);
            while(a==null)
            {
                 out.println("Wrong username:");
                 out.flush();
                 user=in.readLine();
                 a=getAccount(user);
            }
            out.println("Write the password: " );
            while(!in.readLine().equalsIgnoreCase(a.getpassword()))
            {
                 out.println("Wrong password:");
            }
            out.println("Welcome back "+user +"!");
            out.println(1);
            out.flush();
            firstMenu=false;
    }
    /**
     *LogOut function
     * 
     */
    private void LogOut()
    {
        out.println("logout");
        out.println("Goodbye "+user);
        user="";
        out.println(0);
        firstMenu=true;
        out.flush();
    }
    /**
     *
     * This function reads an email
     */
    private void ReadEmail() throws IOException
    {
        out.println("reademail");
        int userp=positionOfUser(user);
        if(accounts.get(userp).mailsize()==0)
        {
            out.println("No email");
        }
        else
        {
            out.println("Find");
            int ID=Integer.parseInt(in.readLine())-1;
            while(ID>=accounts.get(userp).mailsize() || ID<0)
            {
                out.println("Wrong ID");
                ID=Integer.parseInt(in.readLine())-1;
            }
            out.println("OK");
            out.println(accounts.get(userp).getEmail(ID).getSender());
            out.println(accounts.get(userp).getEmail(ID).getSubject());
            out.println(accounts.get(userp).getEmail(ID).getMain());
            accounts.get(userp).getEmail(ID).setNew(false);
        
        }
        out.write(1);
        out.flush();
    }
    /**
     *
     * This function writes a new email
     */
    private void newemail() throws IOException
    {
        out.println("newemail");
        String receiver=in.readLine();
        while(!CheckIfAccountExist(receiver))
        {
            out.println("No account");
            receiver=in.readLine();
        }
        out.println("Account find");
        
        String subject=in.readLine();
        String MainBody=in.readLine();
        out.println("The email has been sent successfully!");
        out.write(1);
        out.flush();
        accounts.get(positionOfUser(receiver)).addEmail(user, receiver, subject, MainBody);
        
    }
    /**
     *This function shows emails
     * 
     */
    private void showEmails() throws IOException
    {
        out.println("showemails");
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
            out.println(i+1+ ".   "+isNew+"     "+accounts.get(userp).getEmail(i).getSender()+"       "+accounts.get(userp).getEmail(i).getSubject());
        }
        out.write(1);
        out.flush();
        
    }
    /**
     *
     *  This function deletes an email
     */
    private void deleteEmail() throws IOException
    {
        out.println("deleteemail");
        int userp=positionOfUser(user);
        if(accounts.get(userp).mailsize()==0)
        {
            out.println("No email");
        }
        else
        {
            out.println("Find");
            int ID=Integer.parseInt(in.readLine())-1;
            while(ID>=accounts.get(userp).mailsize() || ID<0)
            {
                out.println("Wrong ID");
                ID=Integer.parseInt(in.readLine())-1;
            }
            out.println("OK");
            accounts.get(userp).deleteEmail(ID);
        }      
        out.write(1);
        out.flush();
    }
}
