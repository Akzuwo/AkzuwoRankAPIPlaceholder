package ch.ksrminecraft.akzuwoRankAPIPlaceholder;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import org.slf4j.Logger;

@Plugin(id = "akzuworankapiplaceholder", name = "AkzuwoRankAPIPlaceholder", version = "1.0")
public class AkzuwoRankAPIPlaceholder {

    @Inject
    private Logger logger;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
    }
}
