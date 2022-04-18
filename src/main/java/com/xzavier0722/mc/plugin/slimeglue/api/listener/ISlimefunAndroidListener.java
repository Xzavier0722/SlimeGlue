package com.xzavier0722.mc.plugin.slimeglue.api.listener;

import io.github.thebusybiscuit.slimefun4.api.events.AndroidFarmEvent;
import io.github.thebusybiscuit.slimefun4.api.events.AndroidMineEvent;

public interface ISlimefunAndroidListener extends IListener{

    /**
     *
     * Fired when {@link AndroidFarmEvent} is called. LOWEST priority.
     * Any modification of the incoming event will affect the actual action.
     *
     * @param e: the original AndroidFarmEvent event instance
     */
    default void onFarm(AndroidFarmEvent e) { }

    /**
     *
     * Fired when {@link AndroidMineEvent} is called. LOWEST priority.
     * Any modification of the incoming event will affect the actual action.
     *
     * @param e: the original AndroidMineEvent event instance
     */
    default void onMine(AndroidMineEvent e) { }

    /**
     *
     * Fired when {@link AndroidFarmEvent} is called. MONITOR priority.
     * You cannot change the event here. If you want to modify the event, use {@link #onFarm}
     *
     * @param e: the copy of AndroidFarmEvent event instance
     */
    default void afterFarm(AndroidFarmEvent e) { }

    /**
     *
     * Fired when {@link AndroidMineEvent} is called. MONITOR priority.
     * You cannot change the event here. If you want to modify the event, use {@link #onMine}
     *
     * @param e: the copy of AndroidFarmEvent event instance
     */
    default void afterMine(AndroidMineEvent e) { }

}
