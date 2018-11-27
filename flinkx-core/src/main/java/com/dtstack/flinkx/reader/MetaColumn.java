/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dtstack.flinkx.reader;

import com.dtstack.flinkx.util.DateUtil;
import org.apache.commons.lang3.time.FastDateFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jiangbo
 * @date 2018/11/26
 */
public class MetaColumn implements Serializable {

    private String name;

    private String type;

    private Integer index;

    private String value;

    private FastDateFormat timeFormat;

    private String splitter;

    public String getSplitter() {
        return splitter;
    }

    public void setSplitter(String splitter) {
        this.splitter = splitter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public FastDateFormat getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(FastDateFormat timeFormat) {
        this.timeFormat = timeFormat;
    }

    public static List<MetaColumn> getMetaColumns(List columns){
        List<MetaColumn> metaColumns = new ArrayList<>();
        if(columns != null && columns.size() > 0) {
            if (columns.get(0) instanceof Map) {
                for (int i = 0; i < columns.size(); i++) {
                    Map sm = (Map) columns.get(i);
                    MetaColumn mc = new MetaColumn();
                    mc.setName((String) sm.get("name"));
                    mc.setIndex((Integer) sm.get("index"));
                    mc.setType((String) sm.get("type"));
                    mc.setValue((String) sm.get("value"));
                    mc.setSplitter((String) sm.get("splitter"));

                    if(sm.get("format") != null){
                        mc.setTimeFormat(DateUtil.getDateFormatter((String) sm.get("format")));
                    }

                    metaColumns.add(mc);
                }
            } else if (columns.get(0) instanceof String) {
                if(columns.size() == 1 && columns.get(0).equals("*")){
                    MetaColumn mc = new MetaColumn();
                    mc.setName("*");
                    metaColumns.add(mc);
                } else {
                    for (Object column : columns) {
                        MetaColumn mc = new MetaColumn();
                        mc.setName(String.valueOf(column));
                        metaColumns.add(mc);
                    }
                }
            } else {
                throw new IllegalArgumentException("column argument error");
            }
        }

        return metaColumns;
    }
}
