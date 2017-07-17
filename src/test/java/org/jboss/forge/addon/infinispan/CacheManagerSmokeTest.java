/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.forge.addon.infinispan;

import static org.junit.Assert.assertNotNull;

import javax.cache.CacheManager;
import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
@RunWith(Arquillian.class)
public class CacheManagerSmokeTest
{
   @Inject
   CacheManager cacheManager;

   @Test
   public void cacheManagerNotNull()
   {
      assertNotNull(cacheManager);
   }

}
