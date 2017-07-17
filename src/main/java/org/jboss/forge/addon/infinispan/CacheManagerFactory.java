/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.forge.addon.infinispan;

import java.util.function.Supplier;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;

import org.jboss.forge.furnace.container.cdi.events.Local;
import org.jboss.forge.furnace.event.PreShutdown;

/**
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
@ApplicationScoped
public class CacheManagerFactory
{
   private CacheManager cacheManager;

   @Produces
   @ApplicationScoped
   public CacheManager getCacheManager()
   {
      if (cacheManager == null)
      {
         Caching.setDefaultClassLoader(getClass().getClassLoader());
         CachingProvider jcacheProvider = Caching.getCachingProvider();
         cacheManager = jcacheProvider.getCacheManager();
      }
      return cacheManager;
   }

   public <K, V, C extends Configuration<K, V>> Cache<K, V> createOrGetCache(String name, Supplier<C> configuration)
   {
      Cache<K, V> cache = getCacheManager().getCache(name);
      if (cache == null)
      {
         cache = getCacheManager().createCache(name, configuration.get());
      }
      return cache;
   }

   void destroyCacheManager(@Observes @Local PreShutdown evt)
   {
      if (cacheManager != null)
      {
         cacheManager.close();
         cacheManager = null;
      }
   }
}
