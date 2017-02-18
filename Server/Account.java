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

