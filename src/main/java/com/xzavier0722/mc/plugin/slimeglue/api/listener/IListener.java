package com.xzavier0722.mc.plugin.slimeglue.api.listener;

import java.util.HashSet;
import java.util.Set;

public interface IListener {

    /**
     *
     * Define the subscription type of the listener
     *
     * @see SubscriptionType
     * @return the subscription type
     */
    default SubscriptionType getSubscriptionType() {
        return SubscriptionType.SUBSCRIBE_TYPE_ALL;
    }

    /**
     *
     * Define the subscribed id.
     * If the subscription type is ALL, the result of this will be ignored.
     *
     * @return Set of subscribed id
     */
    default Set<String> getSubscribedId() {
        return new HashSet<>();
    }

}
