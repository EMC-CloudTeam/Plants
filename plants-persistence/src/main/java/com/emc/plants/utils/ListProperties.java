//
// "This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own 
// instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use 
// or for redistribution by customer, as part of such an application, in customer's own products. " 
//
// Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2004,2004
// All Rights Reserved * Licensed Materials - Property of IBM
//
package com.emc.plants.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @author samcland
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 *  Utility class.
 */
@SuppressWarnings("unchecked")
public class ListProperties extends Properties {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Hashtable<String, Vector<String>> listProps = null;
    /* Method load
     * @param inStream
     */
    
	public void load(InputStream inStream) throws IOException {
        try {
            // Parse property file, remove comments, blank lines, and combine
            // continued lines.
            String propFile = "";
            BufferedReader inputLine = new BufferedReader(new InputStreamReader(inStream));
            String line = inputLine.readLine();
            boolean lineContinue = false;
            while (line != null) {
                Util.debug("ListProperties.load - Line read: " + line);
                line = line.trim();
                String currLine = "";
                if (line.startsWith("#")) {
                    // Skipping comment
                } else if (line.startsWith("!")) {
                    // Skipping comment
                } else if (line.equals("")) {
                    // Skipping blank lines
                } else {
                    if (!lineContinue) {
                        currLine = line;
                    } else {
                        // This is a continuation line.   Add to previous line.
                        currLine += line;
                    }
                    // Must be a property line
                    if (line.endsWith("\\")) {
                        // Next line is continued from the current one.
                        lineContinue = true;
                    } else {
                        // The current line is completed.   Parse the property.
                        propFile += currLine + "\n";
                        currLine = "";
                        lineContinue = false;
                    }
                }
                line = inputLine.readLine();
            }
            // Load Properties
            listProps = new Hashtable<String, Vector<String>>();
            // Now parse the Properties to create an array
            String[] props = readTokens(propFile, "\n");
            for (int index = 0; index < props.length; index++) {
                Util.debug("ListProperties.load() - props[" + index + "] = " + props[index]);
                // Parse the line to get the key,value pair
                String[] val = readTokens(props[index], "=");
                Util.debug("ListProperties.load() - val[0]: " + val[0] + " val[1]: " + val[1]);
                if (!val[0].equals("")) {
                    if (this.containsKey(val[0])) {
                        // Previous key,value was already created.
                        // Need an array
                        Vector<String> currList = (Vector) listProps.get(val[0]);
                        if ((currList == null) || currList.isEmpty()) {
                            currList = new Vector<String>();
                            String prevVal = this.getProperty(val[0]);
                            currList.addElement(prevVal);
                        }
                        currList.addElement(val[1]);
                        listProps.put(val[0], currList);
                    }
                    this.setProperty(val[0], val[1]);
                }
            }
        } catch (Exception e) {
            Util.debug("ListProperties.load(): Exception: " + e);
        }
    }
    /**
     * Method readTokens.
     * @param text
     * @param token
     * @return list
     */
    public String[] readTokens(String text, String token) {
        StringTokenizer parser = new StringTokenizer(text, token);
        int numTokens = parser.countTokens();
        String[] list = new String[numTokens];
        for (int i = 0; i < numTokens; i++) {
            list[i] = parser.nextToken();
        }
        return list;
    }
    /**
     * Method getProperties.
     * @param name
     * @return values
     */
    public String[] getProperties(String name) {
        String[] values = { "" };
        try {
            String value = this.getProperty(name);
            Util.debug("ListProperties.getProperties: property (" + name + ") -> " + value);
            if (listProps.containsKey(name)) {
                Vector list = (Vector) listProps.get(name);
                values = new String[list.size()];
                for (int index = 0; index < list.size(); index++) {
                    values[index] = (String) list.elementAt(index);
                }
            } else {
                values[0] = value;
            }
        } catch (Exception e) {
            Util.debug("ListProperties.getProperties(): Exception: " + e);
        }
        return (values);
    }
}
