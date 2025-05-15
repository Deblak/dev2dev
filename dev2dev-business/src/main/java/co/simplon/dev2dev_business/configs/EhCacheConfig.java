package co.simplon.dev2dev_business.configs;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.CacheManager;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EhCacheConfig {

    @Bean
    public CacheManager  ehCacheManager() {
        CacheConfigurationBuilder<String, String> cacheConfigurationBuilder = CacheConfigurationBuilder.
                newCacheConfigurationBuilder(String.class,String.class,
                ResourcePoolsBuilder
                .heap(1000)
                .offheap(10, MemoryUnit.MB));

        return CacheManagerBuilder.
                newCacheManagerBuilder().
                withCache("entityCache", cacheConfigurationBuilder)
                .build(true);
    }

}
