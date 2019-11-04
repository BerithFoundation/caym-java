/*
 * Modifications Copyright 2019 The Berith foundation authors.
 * Copyright 2019 Web3 Labs Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package berith.caym.protocol.request;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.List;
import org.web3j.protocol.core.methods.request.Filter;

public abstract class FilterRequest<T extends FilterRequest> {

    private T thisObj;
    private List<Filter.FilterTopic> topics;

    FilterRequest() {
        thisObj = getThis();
        topics = new ArrayList<>();
    }

    public T addSingleTopic(String topic) {
        topics.add(new Filter.SingleTopic(topic));
        return getThis();
    }

    public T addNullTopic() {
        topics.add(new Filter.SingleTopic());
        return getThis();
    }

    // how to pass in null topic?
    public T addOptionalTopics(String... optionalTopics) {
        topics.add(new Filter.ListTopic(optionalTopics));
        return getThis();
    }

    public List<Filter.FilterTopic> getTopics() {
        return topics;
    }

    abstract T getThis();

    public interface FilterTopic<T> {

        @JsonValue
        T getValue();
    }

    public static class SingleTopic implements Filter.FilterTopic<String> {

        private String topic;

        public SingleTopic() {
            this.topic = null;  // null topic
        }

        public SingleTopic(String topic) {
            this.topic = topic;
        }

        @Override
        public String getValue() {
            return topic;
        }
    }

    public static class ListTopic implements Filter.FilterTopic<List<Filter.SingleTopic>> {

        private List<Filter.SingleTopic> topics;

        public ListTopic(String... optionalTopics) {
            topics = new ArrayList<>();
            for (String topic : optionalTopics) {
                if (topic != null) {
                    topics.add(new Filter.SingleTopic(topic));
                } else {
                    topics.add(new Filter.SingleTopic());
                }
            }
        }

        @Override
        public List<Filter.SingleTopic> getValue() {
            return topics;
        }
    }
}
