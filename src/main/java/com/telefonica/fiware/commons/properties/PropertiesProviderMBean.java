/**
 * Copyright 2014 Telefonica Investigación y Desarrollo, S.A.U <br>
 * This file is part of FI-WARE project.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License.
 * </p>
 * <p>
 * You may obtain a copy of the License at:<br>
 * <br>
 * http://www.apache.org/licenses/LICENSE-2.0
 * </p>
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * </p>
 * <p>
 * See the License for the specific language governing permissions and limitations under the License.
 * </p>
 * <p>
 * For those usages not covered by the Apache version 2.0 License please contact with opensource@tid.es
 * </p>
 */

package com.telefonica.fiware.commons.properties;

import java.util.Iterator;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.ReflectionException;

/**
 * Utility MBean that exposes a PropertiesUtil through JMX.
 */
public class PropertiesProviderMBean implements DynamicMBean {

    private final String namespace;
    private final PropertiesProvider propUtil;
    private Properties properties;

    /**
     * Constructor of the class.
     * 
     * @param namespace
     *            The namespace
     * @param propUtil
     *            The PropertiesUtil to expose through JMX
     */
    public PropertiesProviderMBean(final String namespace, final PropertiesProvider propUtil) {
        this.namespace = namespace;
        this.properties = propUtil.load(namespace);
        this.propUtil = propUtil;
    }

    /**
     * @see javax.management.DynamicMBean#getAttribute(String)
     */
    public synchronized String getAttribute(String name) throws AttributeNotFoundException {
        String value = properties.getProperty(name);
        if (value != null) {
            return value;
        } else {
            throw new AttributeNotFoundException("No such property: " + name);
        }
    }

    /**
     * @see javax.management.DynamicMBean#setAttribute(javax.management.Attribute)
     */
    public synchronized void setAttribute(Attribute attribute) throws InvalidAttributeValueException, MBeanException,
            AttributeNotFoundException {
        String name = attribute.getName();
        if (properties.getProperty(name) == null) {
            throw new AttributeNotFoundException(name);
        }
        Object value = attribute.getValue();
        if (!(value instanceof String)) {
            throw new InvalidAttributeValueException("Attribute value not a string: " + value);
        }

        // Store the current property value. It is needed if there is a
        // validation problem
        String currentValue = properties.getProperty(name);

        properties.setProperty(name, (String) value);

        try {
            save();
        } catch (Exception e) {

            // If the current value is not restored next calls to this method
            // will launch a validation exception, because this.properties
            // contains an invalid value (the one that launched this exception)
            properties.setProperty(name, currentValue);
            throw new InvalidAttributeValueException(e.getMessage());
        }
    }

    /**
     * @see javax.management.DynamicMBean#getAttributes(String[])
     */
    public synchronized AttributeList getAttributes(String[] names) {
        AttributeList list = new AttributeList();
        for (String name : names) {
            String value = properties.getProperty(name);
            if (value != null) {
                list.add(new Attribute(name, value));
            }
        }
        return list;
    }

    /**
     * @see javax.management.DynamicMBean#setAttributes(javax.management.AttributeList)
     */
    public synchronized AttributeList setAttributes(AttributeList list) {
        Attribute[] attrs = (Attribute[]) list.toArray(new Attribute[0]);
        AttributeList retlist = new AttributeList();
        for (Attribute attr : attrs) {
            String name = attr.getName();
            Object value = attr.getValue();
            if (properties.getProperty(name) != null && value instanceof String) {
                properties.setProperty(name, (String) value);
                retlist.add(new Attribute(name, value));
            }
        }

        save();
        return retlist;
    }

    /**
     * @see javax.management.DynamicMBean#invoke(String, Object[], String[])
     */
    public Object invoke(String name, Object[] args, String[] sig) throws MBeanException, ReflectionException {
        if (name.equals("reload") && (args == null || args.length == 0) && (sig == null || sig.length == 0)) {
            load();
            return null;
        }
        throw new ReflectionException(new NoSuchMethodException(name));
    }

    /**
     * @see javax.management.DynamicMBean#getMBeanInfo()
     */
    public synchronized MBeanInfo getMBeanInfo() {
        SortedSet<String> names = new TreeSet<String>();
        for (Object name : properties.keySet()) {
            names.add((String) name);
        }
        MBeanAttributeInfo[] attrs = new MBeanAttributeInfo[names.size()];
        Iterator<String> it = names.iterator();
        for (int i = 0; i < attrs.length; i++) {
            String name = it.next();
            attrs[i] = new MBeanAttributeInfo(name, "java.lang.String", "Property " + name, true,   // isReadable
                    true,   // isWritable
                    false); // isIs
        }
        MBeanOperationInfo[] opers = { new MBeanOperationInfo("reload", "Reload properties from file", null,   // no
                                                                                                             // parameters
                "void", MBeanOperationInfo.ACTION) };
        return new MBeanInfo(this.getClass().getName(), "Property Manager MBean", attrs, null,  // constructors
                opers, null); // notifications
    }

    /**
     * Load a properties util.
     */
    private void load() {
        properties = propUtil.load(namespace);
    }

    /**
     * Stores a properties util.
     */
    private void save() {
        propUtil.store(properties, namespace);
    }

}
