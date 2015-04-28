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
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Properties;

import javax.management.Attribute;
import javax.management.AttributeList;

import org.junit.Test;

import com.telefonica.fiware.commons.properties.PropertiesProvider;

/**
 * Test for class PropertiesProviderMBean
 */
public class PropertiesProviderMBeanTest {

    /**
     * Should set attributes from list
     */
    @Test
    public void shouldSetAttributes() {
        // given
        PropertiesProvider propUtil = mock(PropertiesProvider.class);
        Properties properties = new Properties();
        properties.put("name123", "value1");
        when(propUtil.load(anyString())).thenReturn(properties);
        PropertiesProviderMBean propertiesProviderMBean = new PropertiesProviderMBean("namespace", propUtil);

        // when
        AttributeList list = new AttributeList();
        list.add(new Attribute("name123", "value123"));
        AttributeList resultList = propertiesProviderMBean.setAttributes(list);

        // then
        assertNotNull(resultList);
        assertEquals(1, resultList.size());
        assertEquals(new Attribute("name123", "value123").toString(), resultList.get(0).toString());
    }
}
