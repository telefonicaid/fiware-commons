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

package com.telefonica.fiware.commons.properties.mbeans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Properties;

import javax.management.Attribute;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;

import org.junit.Test;

import com.telefonica.fiware.commons.properties.PropertiesProvider;
import com.telefonica.fiware.commons.properties.PropertiesProviderMBean;
import com.telefonica.fiware.commons.properties.impl.PropertiesDAO;
import com.telefonica.fiware.commons.properties.impl.PropertiesProviderImpl;

/**
 * Tests for class MBeanUtils
 */
public class MBeanUtilsTest {

    /**
     * Should register MBean
     * 
     * @throws NotCompliantMBeanException
     * @throws InstanceAlreadyExistsException
     * @throws MalformedObjectNameException
     * @throws MBeanRegistrationException
     */
    @Test
    public void shouldRegisterMBean() throws NotCompliantMBeanException, InstanceAlreadyExistsException,
            MalformedObjectNameException, MBeanException, AttributeNotFoundException, InvalidAttributeValueException {
        // given
        PropertiesDAO propertiesDAO = mock(PropertiesDAO.class);
        PropertiesProvider propertiesProvider = new PropertiesProviderImpl(propertiesDAO);

        Properties properties = new Properties();
        properties.setProperty("test1.key", "value1");
        when(propertiesDAO.load("/test2.properties")).thenReturn(properties);
        PropertiesProviderMBean mbean = new PropertiesProviderMBean("/test2.properties", propertiesProvider);
        // when
        MBeanUtils.register(mbean, "/servlet:service=SystemConfiguration-filename.properties");

        mbean.setAttribute(new Attribute("test2.key", "value 2"));

        // then
        verify(propertiesDAO).load("/test2.properties");
        assertNotNull(mbean);
        assertEquals("value1", mbean.getAttribute("test1.key"));
        assertEquals("value 2", mbean.getAttribute("test2.key"));
        assertNotNull(mbean.getAttributes(new String[] { "test1.key", "test2.key" }));
    }

    /**
     * Should unregister MBean
     */
    @Test
    public void shouldUnregisterMBean() throws MalformedObjectNameException, InstanceNotFoundException,
            MBeanRegistrationException {
        // given

        // when
        MBeanUtils.unregister("/servlet:service=SystemConfiguration-filename.properties");

        // then
    }
}
