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
