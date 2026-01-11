package org.ros.internal.node.topic;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Map;
import org.ros.namespace.GraphName;

public class TopicParticipantManager {
    private TopicParticipantManagerListener listener;
    private final Multimap<DefaultPublisher<?>, SubscriberIdentifier> publisherConnections = HashMultimap.create();
    private final Map<GraphName, DefaultPublisher<?>> publishers = Maps.newConcurrentMap();
    private final Multimap<DefaultSubscriber<?>, PublisherIdentifier> subscriberConnections = HashMultimap.create();
    private final Map<GraphName, DefaultSubscriber<?>> subscribers = Maps.newConcurrentMap();

    public void setListener(TopicParticipantManagerListener listener2) {
        this.listener = listener2;
    }

    public boolean hasSubscriber(GraphName topicName) {
        return this.subscribers.containsKey(topicName);
    }

    public boolean hasPublisher(GraphName topicName) {
        return this.publishers.containsKey(topicName);
    }

    public DefaultPublisher<?> getPublisher(GraphName topicName) {
        return this.publishers.get(topicName);
    }

    public DefaultSubscriber<?> getSubscriber(GraphName topicName) {
        return this.subscribers.get(topicName);
    }

    public void addPublisher(DefaultPublisher<?> publisher) {
        this.publishers.put(publisher.getTopicName(), publisher);
        TopicParticipantManagerListener topicParticipantManagerListener = this.listener;
        if (topicParticipantManagerListener != null) {
            topicParticipantManagerListener.onPublisherAdded(publisher);
        }
    }

    public void removePublisher(DefaultPublisher<?> publisher) {
        this.publishers.remove(publisher.getTopicName());
        TopicParticipantManagerListener topicParticipantManagerListener = this.listener;
        if (topicParticipantManagerListener != null) {
            topicParticipantManagerListener.onPublisherRemoved(publisher);
        }
    }

    public void addSubscriber(DefaultSubscriber<?> subscriber) {
        this.subscribers.put(subscriber.getTopicName(), subscriber);
        TopicParticipantManagerListener topicParticipantManagerListener = this.listener;
        if (topicParticipantManagerListener != null) {
            topicParticipantManagerListener.onSubscriberAdded(subscriber);
        }
    }

    public void removeSubscriber(DefaultSubscriber<?> subscriber) {
        this.subscribers.remove(subscriber.getTopicName());
        TopicParticipantManagerListener topicParticipantManagerListener = this.listener;
        if (topicParticipantManagerListener != null) {
            topicParticipantManagerListener.onSubscriberRemoved(subscriber);
        }
    }

    public void addSubscriberConnection(DefaultSubscriber<?> subscriber, PublisherIdentifier publisherIdentifier) {
        this.subscriberConnections.put(subscriber, publisherIdentifier);
    }

    public void removeSubscriberConnection(DefaultSubscriber<?> subscriber, PublisherIdentifier publisherIdentifier) {
        this.subscriberConnections.remove(subscriber, publisherIdentifier);
    }

    public void addPublisherConnection(DefaultPublisher<?> publisher, SubscriberIdentifier subscriberIdentifier) {
        this.publisherConnections.put(publisher, subscriberIdentifier);
    }

    public void removePublisherConnection(DefaultPublisher<?> publisher, SubscriberIdentifier subscriberIdentifier) {
        this.publisherConnections.remove(publisher, subscriberIdentifier);
    }

    public Collection<DefaultSubscriber<?>> getSubscribers() {
        return ImmutableList.copyOf(this.subscribers.values());
    }

    public Collection<PublisherIdentifier> getSubscriberConnections(DefaultSubscriber<?> subscriber) {
        return ImmutableList.copyOf(this.subscriberConnections.get(subscriber));
    }

    public Collection<DefaultPublisher<?>> getPublishers() {
        return ImmutableList.copyOf(this.publishers.values());
    }

    public Collection<SubscriberIdentifier> getPublisherConnections(DefaultPublisher<?> publisher) {
        return ImmutableList.copyOf(this.publisherConnections.get(publisher));
    }
}
