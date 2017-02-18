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
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;



/**
 *
 * @author Andreas Agapitos
 */
public class Client {
    private final BufferedReader inFromUser;
    private final Socket clientSocket; 
    private final PrintWriter out;
    private final BufferedReader inFromServer;

    /**
     *
     * @param ip
     * @param port
     * @throws IOException
     */
    public Client(String ip,int port) throws IOException //Constructor
    {
        inFromUser = new BufferedReader(new InputStreamReader(System.in));
        clientSocket = new Socket(ip,port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    /**
     *
     * @throws IOException
     */
    public void start() throws IOException
    {
        boolean flag=true;
        int i=inFromServer.read();
        MailServer();
        System.out.println(inFromServer.readLine());
        ShowMenu(i);
        String line=null,ServerAnswer=null;
        while(flag)
        {
            line=inFromUser.readLine();
            out.println(line);
            out.flush();
    
            if(line.toLowerCase().equals("exit"))
            {
                flag=false;
            }
            else
            {
                MailServer();
                ServerAnswer=inFromServer.readLine();
                if(ServerAnswer.equalsIgnoreCase("signup") ) //Sign Up
                {
                    register();
                    ShowMenu(Integer.parseInt(inFromServer.readLine()));
                }
                else if(ServerAnswer.equalsIgnoreCase("login")) //Login
                {
                    login();
                    ShowMenu(Integer.parseInt(inFromServer.readLine()));
                }
                else if(ServerAnswer.equalsIgnoreCase("newemail")) // New Email
                {
                    NewEmail();
                    MailServer();
                    System.out.println(inFromServer.readLine());
                    ShowMenu(inFromServer.read());
                }
                else if(ServerAnswer.equalsIgnoreCase("showemails")) // Show all emails
                {
                    ShowEmails();
                    ShowMenu(inFromServer.read());
                }
                else if(ServerAnswer.equalsIgnoreCase("deleteemail")) // Delete email
                {
                    deleteEmail();
                    ShowMenu(inFromServer.read());
                }
                else if(ServerAnswer.equalsIgnoreCase("logout")) //Log out
                {
                    System.out.println(inFromServer.readLine());
                    ShowMenu(Integer.parseInt(inFromServer.readLine()));
                }
                else if(ServerAnswer.equalsIgnoreCase("reademail")) // Read email
                {
                    readEmail();
                    ShowMenu(inFromServer.read());
                }
                else 
                {
                    System.out.println(ServerAnswer);
                }
            }
            
        }
        inFromUser.close();
        inFromServer.close();
        out.close();
        clientSocket.close();
    }
    /**
     *
     * This function writes an email
     */
    private void NewEmail() throws IOException 
    {  
         System.out.println("Write the receiver's username:");
         String account=inFromUser.readLine();
         out.println(account);
         while(inFromServer.readLine().equalsIgnoreCase("No account"))
         {
             System.out.println("This username doesn't exist! Please write again:");
             account=inFromUser.readLine();
             out.println(account);
         }
        System.out.println("Write the subject:");
        String subject=inFromUser.readLine();
        out.println(subject);
        System.out.println("Write the Mainbody:");
        String mainbody=inFromUser.readLine();
        out.println(mainbody);
        out.flush();
    }
    /**
     *
     * This function reads an email
     */
    private void readEmail() throws IOException
    {
        String serveranswer=inFromServer.readLine();
        if(serveranswer.equalsIgnoreCase("No email"))
        {
            System.out.println("There aren't emails!");
        }
        else
        {
            System.out.print("Write the email's ID:  ");
            int ID=Integer.parseInt(inFromUser.readLine());
            out.println(ID);
            serveranswer=inFromServer.readLine();
            while(serveranswer.equalsIgnoreCase("Wrong ID"))
            {
                System.out.println("There isn't this ID");
                ID=Integer.parseInt(inFromUser.readLine());
                out.println(ID);
                serveranswer=inFromServer.readLine();
            }
            MailServer();
            System.out.println("The Sender:\n"+inFromServer.readLine());
            MailServer();
            System.out.println("Subject:\n"+inFromServer.readLine());
            MailServer();
            System.out.println("Main body: \n"+inFromServer.readLine());
            out.flush();
        }
    }
    /**
     *
     * This function deletes an email
     */
    private void deleteEmail() throws IOException
    {
        out.flush();
        String serveranswer=inFromServer.readLine();
        if(serveranswer.equalsIgnoreCase("No email"))
        {
            System.out.println("There aren't emails!");
        }
        else
        {
            System.out.print("Write the email's ID:  ");
            int ID=Integer.parseInt(inFromUser.readLine());
            out.println(ID);
            serveranswer=inFromServer.readLine();
            while(serveranswer.equalsIgnoreCase("Wrong ID"))
            {
                System.out.println("Wrong ID");
                ID=Integer.parseInt(inFromUser.readLine());
                out.println(ID);
                serveranswer=inFromServer.readLine();
            }
            System.out.println("The e-mail was deleted");
            out.flush();
        }
    }
    /**
     *
     * This function shows emails
     */
    private void ShowEmails() throws IOException
    {
        System.out.println("ID            From           Subject");
        int n=inFromServer.read();
        for(int i=0;i<n;i++)
        {
           System.out.println(inFromServer.readLine());
        }
        
        
    }
    /**
     *
     * Login
     */
    private void login() throws IOException
    {
        System.out.print(inFromServer.readLine());
        out.println(inFromUser.readLine());
        while(inFromServer.readLine().equalsIgnoreCase("Wrong username:"))
        {
                System.out.print("Wrong username: ");
                out.println(inFromUser.readLine());
        }
        System.out.print("Write the password: ");
        out.println(inFromUser.readLine());
        String answer=inFromServer.readLine();
        while(answer.equalsIgnoreCase("Wrong password:"))
        {
            System.out.print("Wrong password: ");
            out.println(inFromUser.readLine());
            answer=inFromServer.readLine();
        }
        System.out.println(answer);
    }
    /**
     *
     * Sign Up
     */
    private void register() throws IOException
    {
        System.out.print(inFromServer.readLine());
        out.println(inFromUser.readLine());
        while(inFromServer.readLine().equalsIgnoreCase("This username exist - Please choose again:"))
        {
             System.out.print("This username exist - Please choose again: ");
             out.println(inFromUser.readLine());
        }
        System.out.print("Choose the password: ");
        out.println(inFromUser.readLine());
        System.out.println(inFromServer.readLine());    
        
    }
    /**
     *
     * This function shows menu
     */
    private void ShowMenu(int i)
    {
        if(i==0)
        {
            System.out.print("==========\n > LogIn\n > SignIn\n > Exit\n==========");
            System.out.println();
        }
        else if(i==1)
        {
            System.out.println("==========\n > NewEmail\n > ShowEmails\n > ReadEmail\n > DeleteEmail\n > LogOut\n > Exit\n==========");
        }
        
        
    }
    private void MailServer()
    {
        System.out.println("------------");
        System.out.println("MailServer:");
        System.out.println("------------");
    }
}