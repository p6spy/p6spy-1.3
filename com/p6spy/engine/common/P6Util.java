/*
 *
 * ====================================================================
 *
 * The P6Spy Software License, Version 1.1
 *
 * This license is derived and fully compatible with the Apache Software
 * license, see http://www.apache.org/LICENSE.txt
 *
 * Copyright (c) 2001-2002 Andy Martin, Ph.D. and Jeff Goke
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in
 * the documentation and/or other materials provided with the
 * distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 * any, must include the following acknowlegement:
 * "The original concept and code base for P6Spy was conceived
 * and developed by Andy Martin, Ph.D. who generously contribued
 * the first complete release to the public under this license.
 * This product was due to the pioneering work of Andy
 * that began in December of 1995 developing applications that could
 * seamlessly be deployed with minimal effort but with dramatic results.
 * This code is maintained and extended by Jeff Goke and with the ideas
 * and contributions of other P6Spy contributors.
 * (http://www.p6spy.com)"
 * Alternately, this acknowlegement may appear in the software itself,
 * if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "P6Spy", "Jeff Goke", and "Andy Martin" must not be used
 * to endorse or promote products derived from this software without
 * prior written permission. For written permission, please contact
 * license@p6spy.com.
 *
 * 5. Products derived from this software may not be called "P6Spy"
 * nor may "P6Spy" appear in their names without prior written
 * permission of Jeff Goke and Andy Martin.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */


/**
 * Description: Utility classes
 *
 * $Author$
 * $Revision$
 * $Date$
 *
 * $Id$
 * $Log$
 * Revision 1.6  2003/01/15 22:09:41  aarvesen
 * Don't crap out if you can't find a context loader, press on
 *
 * Revision 1.5  2003/01/10 21:39:43  jeffgoke
 * removed p6util.warn and moved warn handling to logging.  this gives a consistent log file.
 *
 * Revision 1.4  2003/01/08 18:11:13  aarvesen
 * Trap the no more element exception to avoid a stupid crashing bug.
 *
 * Revision 1.3  2003/01/03 21:14:54  aarvesen
 * added the (unused) removeDots method
 * added the (widely used) forName method to implement better class loading
 *
 * Revision 1.2  2002/10/06 18:21:37  jeffgoke
 * no message
 *
 * Revision 1.1  2002/05/24 07:32:01  jeffgoke
 * version 1 rewrite
 *
 * Revision 1.7  2002/05/18 06:39:52  jeffgoke
 * Peter Laird added Outage detection.  Added junit tests for outage detection.
 * Fixed multi-driver tests.
 *
 * Revision 1.6  2002/05/16 04:58:40  jeffgoke
 * Viktor Szathmary added multi-driver support.
 * Rewrote P6SpyOptions to be easier to manage.
 * Fixed several bugs.
 *
 * Revision 1.5  2002/05/05 00:43:00  jeffgoke
 * Added Philip's reload code.
 *
 * Revision 1.4  2002/04/15 05:13:32  jeffgoke
 * Simon Sadedin added timing support.  Fixed bug where batch execute was not
 * getting logged.  Added result set timing.  Updated the log format to include
 * categories, and updated options to control the categories.  Updated
 * documentation.
 *
 * Revision 1.3  2002/04/10 06:49:26  jeffgoke
 * added more debug information and a new property for setting the log's date format
 *
 * Revision 1.2  2002/04/07 20:43:59  jeffgoke
 * fixed bug that caused null connection to return an empty connection instead of null.
 * added an option allowing the user to truncate.
 * added a release target to the build to create the release files.
 *
 * Revision 1.1.1.1  2002/04/07 04:52:26  jeffgoke
 * no message
 *
 * Revision 1.4  2001-08-05 09:16:03-05  andy
 * version on the website
 *
 * Revision 1.3  2001-08-02 07:52:43-05  andy
 * <>
 *
 * Revision 1.2  2001-07-30 23:37:24-05  andy
 * <>
 *
 * Revision 1.1  2001-07-30 23:03:32-05  andy
 * <>
 *
 * Revision 1.0  2001-07-30 17:49:09-05  andy
 * Initial revision
 *
 */

package com.p6spy.engine.common;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import java.beans.*;

public class P6Util {
    
    public static int parseInt(String i, int defaultValue) {
        if (i == null || i.equals("")) {
            return defaultValue;
        }
        try {
            return (Integer.parseInt(i));
        }
        catch(NumberFormatException nfe) {
            P6LogQuery.logError("NumberFormatException occured parsing value "+i);
            return defaultValue;
        }
    }
    
    public static long parseLong(String l, long defaultValue) {
        if (l == null || l.equals("")) {
            return defaultValue;
        }
        try {
            return (Long.parseLong(l));
        }
        catch(NumberFormatException nfe) {
            P6LogQuery.logError("NumberFormatException occured parsing value "+l);
            return defaultValue;
        }
    }
    
    public static boolean isTrue(String s, boolean defaultValue) {
        if (s == null) {
            return defaultValue;
        }
        return(s.equals("1") || s.trim().equalsIgnoreCase("true"));
    }
    
    public static int atoi(Object s) {
        int i = 0;
        
        if (s != null) {
            String n = s.toString();
            int dot = n.indexOf('.');
            if (dot != -1) {
                n = n.substring(0,dot);
            }
            
            try {
                i = new Integer(n).intValue();
            } catch (NumberFormatException e) {
                i = 0;
            }
        }
        
        return(i);
    }
    
    public static Properties loadProperties(String file) {
        Properties props = new Properties();
        try {
            String path = classPathFile(file);
            if (path == null) {
                P6LogQuery.logError("Can't find " + file + ", java.class.path = <" + System.getProperty("java.class.path") + ">");
            } else {
                FileInputStream in = new FileInputStream(path);
                props.load(in);
		//removeDots(props);
                in.close();
            }
        } catch (FileNotFoundException e1) {
            P6LogQuery.logError("Can't find find " + file + " " + e1);
        } catch (IOException e2) {
            P6LogQuery.logError("IO Error reading file " + file + " " + e2);
        }
        
        return props;
    }

    protected static void removeDots(Properties props) {
	Enumeration keys = props.keys();
	HashMap hash     = new HashMap();
	boolean done     = false;

	for (; keys.hasMoreElements();) {
	    String key = (String) keys.nextElement();
	    if (key.indexOf('.') != -1) {
		int len    = key.length();
		int newLen = 0;
		char[] car = new char[len];
		for (int i = 0; i < len; i++) {
		    char c = key.charAt(i);
		    if (c != '.') {
			car[newLen++] = c;
		    }
		}

		String out = new String(car, 0, newLen);
		hash.put(out, props.getProperty(key));
	    }
	}

	if (done) {
	    props.putAll(hash);
	}
    }

    
    // this is our own version, which we need to do to ensure the order is kept
    // in the property file
    public static ArrayList loadProperties(String file, String prefix) {
        ArrayList props = new ArrayList();
        FileReader in = null;
        BufferedReader reader = null;
        try {
            String path = classPathFile(file);
            if (path == null) {
                P6LogQuery.logError("Can't find " + file + ", java.class.path = <" + System.getProperty("java.class.path") + ">");
            } else {
                in = new FileReader(path);
                // read the file
                reader = new BufferedReader(in);
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith(prefix)) {
                        StringTokenizer st = new StringTokenizer(line, "=");
			try {
			    String name = st.nextToken();
			    String value = st.nextToken();
			    KeyValue kv = new KeyValue(name, value);
			    props.add(kv);
			} catch (NoSuchElementException e) {
			    // ignore; means that you have 
			    // something like:
			    // realdriver2=
			}
                    }
                }
            }
        } catch (FileNotFoundException e1) {
            P6LogQuery.logError("Can't find find " + file + " " + e1);
        } catch (IOException e2) {
            P6LogQuery.logError("IO Error reading file " + file + " " + e2);
        } finally {
            try {
                reader.close();
                in.close();
            } catch (IOException e) {
            }
        }
        return props;
    }
    
    public static ArrayList reverseArrayList(ArrayList arraylist) {
        ArrayList newlist = new ArrayList(arraylist.size());
        for (int i = arraylist.size()-1;i >= 0; i--) {
            newlist.add(arraylist.get(i));
        }
        return newlist;
    }
    
    /**
     * Here we attempt to find the file in the current dir and the classpath
     * If we can't find it then we return null
     */
    public static String classPathFile(String file) {
        File fp             = null;
        String path         = null;
        String separator    = System.getProperty("path.separator");
        String slash        = System.getProperty("file.separator");
        String classpath    = "." + separator + System.getProperty("java.class.path");
        String local        = System.getProperty("p6.home");
        
        if (local != null) {
            classpath = local + separator + classpath;
        }
        StringTokenizer tok = new StringTokenizer(classpath, separator);
        
        do {
            String dir = tok.nextToken();
            path = dir.equals(".") ? file : dir + slash + file;
            fp = new File(path);
        } while (!fp.exists() && tok.hasMoreTokens());
        
        return fp.exists() ? path : null;
    }
    
    public static void checkJavaProperties() {
        Collection list = P6SpyOptions.dynamicGetOptions();
        Iterator it = list.iterator();
        
        P6LogQuery.logInfo("Using properties file: "+P6SpyOptions.propertiesPath);
        
        while (it.hasNext()) {
            String opt = (String) it.next();
            String value = System.getProperty("p6" + opt);
            
            if (value != null) {
                P6LogQuery.logInfo("Found value in environment: "+opt+", setting to value: "+value);
                P6SpyOptions.dynamicSet(opt,value);
            } else {
                P6LogQuery.logInfo("No value in environment for: "+opt+", using: "+P6SpyOptions.dynamicGet(opt));
            }
        }
    }
    
    public static java.util.Date timeNow() {
        return(new java.util.Date());
    }
    
    public static PrintStream getPrintStream(String file, boolean append) throws IOException {
        PrintStream stream = null;
        FileOutputStream  fw  = new FileOutputStream(file, append);
        stream = new PrintStream(fw, P6SpyOptions.getAutoflush());
        return(stream);
    }
    
    public static String timeTaken(java.util.Date start, String msg) {
        double t = (double) elapsed(start) / (double) 1000;
        return "Time: " + msg + ": " + t;
    }
    
    public static long elapsed(java.util.Date start) {
        return(start == null) ? 0 : (timeNow().getTime() - start.getTime());
    }
    
    public static ArrayList findAllMethods(Class klass) throws IntrospectionException {
        ArrayList list = new ArrayList();
        
        Method[] methods = klass.getDeclaredMethods();
        
        for(int i=0; methods != null && i < methods.length; i++) {
            Method method = methods[i];
            String methodName = method.getName();
            if (methodName.startsWith("get")) {
                list.add(methodName);
            }
        }
        return list;
    }
    
    public static void set(Class klass, String method, Object[] args) throws IntrospectionException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Method m = klass.getDeclaredMethod(method, new Class[] {String.class});
        m.invoke(null,args);
    }
    
    public static Object get(Class klass, String method) throws IntrospectionException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Method m = klass.getDeclaredMethod(method, null);
        return (m.invoke(null,null));
    }


    /**
     * A utiltity for using the current class loader (rather than the
     * system class loader) when instantiating a new class.
     * <p>
     * The idea is that the thread's current loader might have an
     * obscure notion of what your class path is (e.g. an app server) that
     * will not be captured properly by the system class loader.
     * <p>
     * taken from http://sourceforge.net/forum/message.php?msg_id=1720229
     *
     * @param name class name to load
     * @return the newly loaded class
     */
    public static Class forName(String name) throws ClassNotFoundException { 
	ClassLoader ctxLoader = null; 
	try { 
	    ctxLoader = Thread.currentThread().getContextClassLoader(); 
	    return Class.forName(name, true, ctxLoader); 

	} catch(ClassNotFoundException ex) { 
	    // try to fall through and use the default
	    // Class.forName
	    //if(ctxLoader == null) { throw ex; } 
	} catch(SecurityException ex) { 
	} 
	return Class.forName(name); 
    } 
}
